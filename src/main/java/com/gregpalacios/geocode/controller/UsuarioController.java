package com.gregpalacios.geocode.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gregpalacios.geocode.model.ResetToken;
import com.gregpalacios.geocode.model.Rol;
import com.gregpalacios.geocode.model.Usuario;
import com.gregpalacios.geocode.service.ILoginService;
import com.gregpalacios.geocode.service.IResetTokenService;
import com.gregpalacios.geocode.service.IRolService;
import com.gregpalacios.geocode.service.IUsuarioService;
import com.gregpalacios.geocode.util.EmailUtil;
import com.gregpalacios.geocode.util.Mail;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
	
	@Autowired
	private IUsuarioService service;
	
	@Autowired
	private IRolService rolService;
	
	@Autowired
	private ILoginService loginService;
	
	@Autowired	
	private IResetTokenService tokenService;
	
	@Autowired
	private BCryptPasswordEncoder bcrypt;
	
	@Autowired
	private EmailUtil emailUtil;
	
	@GetMapping("/verificar/correo/{correo}")
	public @ResponseBody Map<String, Object> obtenerPorCorreo(@PathVariable("correo") String correo) throws Exception {
		Usuario obj = loginService.verificarCorreoUsuario(correo);
		
		HashMap<String, Object> respuesta = new HashMap<>();

		if (obj == null) {
			respuesta.put("existe", "0");
		} else {
			respuesta.put("existe", "1");
		}

		return respuesta;
	}
	
	@GetMapping("/verificar/usuario/{usuario}")
	public @ResponseBody Map<String, Object> obtenerPorUsuario(@PathVariable("usuario") String usuario) throws Exception {
		Usuario obj = loginService.verificarNombreUsuario(usuario);
		
		HashMap<String, Object> respuesta = new HashMap<>();

		if (obj == null) {
			respuesta.put("existe", "0");
		} else {
			respuesta.put("existe", "1");
		}

		return respuesta;
	}
	
	@PostMapping
	public ResponseEntity<Usuario> registrar(@Valid @RequestBody Usuario u) throws Exception {
		Usuario usu = u;
		usu.setPassword(bcrypt.encode(u.getPassword()));
		usu.setEnabled(true);
		
		Rol rol = rolService.listarPorId(2);
		List<Rol> roles = new ArrayList<Rol>();
		roles.add(rol);
		usu.setRoles(roles);
		
		Usuario obj = service.registrar(usu);
		obj.setPassword(null);
		
		return new ResponseEntity<Usuario>(obj, HttpStatus.CREATED);
	}
	
	@PostMapping("/enviar/correo")
	public ResponseEntity<Integer> enviarCorreo(HttpServletRequest request) throws Exception {
		int rpta = 0;
		
		String correo = request.getParameter("correo");
		String webUrl = request.getParameter("webUrl");
		
		Usuario us = loginService.verificarCorreoUsuario(correo);
		
		if(us != null && us.getIdUsuario() > 0) {
			ResetToken token = new ResetToken();
			token.setToken(UUID.randomUUID().toString());
			token.setUser(us);
			token.setExpiracion(10);
			tokenService.guardar(token);
			
			Mail mail = new Mail();
			mail.setFrom("GeoCode");
			mail.setTo(us.getCorreo());
			mail.setSubject("GeoCode - Restablecer Contraseña");
			
			Map<String, Object> model = new HashMap<>();
			String resetUrl = webUrl + "/#/recuperar/" + token.getToken();
			model.put("user", token.getUser().getNombres());
			model.put("resetUrl", resetUrl);
			model.put("webUrl", webUrl);
			mail.setModel(model);				
			
			emailUtil.enviarMail(mail);
			
			rpta = 1;		
		}
		
		return new ResponseEntity<Integer>(rpta, HttpStatus.OK);
	}
	
	@GetMapping("/restablecer/verificar/{token}")
	public ResponseEntity<Integer> verificarToken(@PathVariable("token") String token) {
		int rpta = 0;
		
		try {
			if (token != null && !token.isEmpty()) {
				ResetToken rt = tokenService.findByToken(token);
				if (rt != null && rt.getId() > 0) {
					if (!rt.estaExpirado()) {
						rpta = 1;
					}
				}
			}
			
		} catch (Exception e) {
			return new ResponseEntity<Integer>(rpta, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<Integer>(rpta, HttpStatus.OK);
	}
	
	@PostMapping("/restablecer/{token}")
	public ResponseEntity<Integer> restablecerClave(@PathVariable("token") String token, HttpServletRequest request) {
		int rpta = 0;
		
		String clave = request.getParameter("clave");
		
		try {
			ResetToken rt = tokenService.findByToken(token);			
			loginService.cambiarClaveUsuario(clave, rt.getUser().getUsername());
			tokenService.eliminar(rt);
			
			rpta = 1;
			
		} catch (Exception e) {
			return new ResponseEntity<Integer>(rpta, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<Integer>(rpta, HttpStatus.OK);
	}
}