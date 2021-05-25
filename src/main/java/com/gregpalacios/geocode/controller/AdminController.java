package com.gregpalacios.geocode.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gregpalacios.geocode.exception.ModeloNotFoundException;
import com.gregpalacios.geocode.model.Rol;
import com.gregpalacios.geocode.model.Usuario;
import com.gregpalacios.geocode.service.IRolService;
import com.gregpalacios.geocode.service.IUsuarioService;
import com.gregpalacios.geocode.util.ExcelGenerator;

@RestController
@RequestMapping("/api/admin/usuarios")
public class AdminController {

	@Autowired
	private IUsuarioService service;
	
	@Autowired
	private IRolService rolService;
	
	@Autowired
	private BCryptPasswordEncoder bcrypt;
	
	@GetMapping
	public ResponseEntity<List<Usuario>> listar() throws Exception {
		List<Usuario> lista = service.listar();
		return new ResponseEntity<List<Usuario>>(lista, HttpStatus.OK);
	}
	
	@GetMapping("/pageable")
	public ResponseEntity<Page<Usuario>> listarPageable(Pageable pageable) throws Exception {
		Page<Usuario> lista = service.listarPageable(pageable);
		return new ResponseEntity<Page<Usuario>>(lista, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Usuario> listarPorId(@PathVariable("id") Integer id) throws Exception {
		Usuario obj = service.listarPorId(id);

		if (obj == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
		}

		return new ResponseEntity<Usuario>(obj, HttpStatus.OK);
	}
	
	@PostMapping("/registrar")
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
	
	@PostMapping("/actualizar")
	public ResponseEntity<Integer> actualizarEstado(@Valid @RequestBody Usuario u) throws Exception {
		int rpta = 0;
		
		try {
			service.cambiarEstadoUsuario(u.isEnabled(), u.getUsername());
			rpta = 1;
			
		} catch (Exception e) {
			return new ResponseEntity<Integer>(rpta, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<Integer>(rpta, HttpStatus.OK);
	}
	
	@GetMapping("/descargar")
	public ResponseEntity<InputStreamResource> descargar() throws Exception, IOException {
		List<Usuario> lista = service.listar();
    
	    ByteArrayInputStream in = ExcelGenerator.usuariosToExcel(lista);
	    
	    HttpHeaders headers = new HttpHeaders();
	    headers.add("Content-Disposition", "attachment; filename=usuarios.xlsx");
	    
	    return ResponseEntity.ok().headers(headers).body(new InputStreamResource(in));
    }
}
