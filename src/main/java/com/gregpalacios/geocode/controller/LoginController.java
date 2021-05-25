package com.gregpalacios.geocode.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gregpalacios.geocode.exception.ModeloNotFoundException;
import com.gregpalacios.geocode.model.Usuario;
import com.gregpalacios.geocode.service.ILoginService;

@RestController
@RequestMapping("/api/login")
public class LoginController {
	
	@Autowired
	private ILoginService service;
	
	@GetMapping("/perfil/correo/{correo}")
	public ResponseEntity<Usuario> obtenerPorCorreo(@PathVariable("correo") String correo) throws Exception {
		Usuario obj = service.verificarCorreoUsuario(correo);

		if (obj == null) {
			throw new ModeloNotFoundException("CORREO NO ENCONTRADO " + correo);
		}

		return new ResponseEntity<Usuario>(obj, HttpStatus.OK);
	}
	
	@GetMapping("/perfil/usuario/{usuario}")
	public ResponseEntity<Usuario> obtenerPorUsuario(@PathVariable("usuario") String usuario) throws Exception {
		Usuario obj = service.verificarNombreUsuario(usuario);

		if (obj == null) {
			throw new ModeloNotFoundException("USUARIO NO ENCONTRADO " + usuario);
		}

		return new ResponseEntity<Usuario>(obj, HttpStatus.OK);
	}
	
	@PostMapping("/perfil/actualizar")
	public ResponseEntity<Integer> actualizarDatos(@Valid @RequestBody Usuario u) throws Exception {
		int rpta = 0;
		
		try {
			service.cambiarDatosUsuario(u.getNombres(), u.getApellidos(), u.getUsername());
			rpta = 1;
			
		} catch (Exception e) {
			return new ResponseEntity<Integer>(rpta, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<Integer>(rpta, HttpStatus.OK);
	}
	
	@PostMapping("/perfil/actualizar/clave")
	public ResponseEntity<Integer> actualizarClave(@Valid @RequestBody Usuario u) throws Exception {
		int rpta = 0;
		
		try {
			service.cambiarClaveUsuario(u.getPassword(), u.getUsername());
			rpta = 1;
			
		} catch (Exception e) {
			return new ResponseEntity<Integer>(rpta, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<Integer>(rpta, HttpStatus.OK);
	}
}