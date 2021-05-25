package com.gregpalacios.geocode.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gregpalacios.geocode.exception.ModeloNotFoundException;
import com.gregpalacios.geocode.model.Comisaria;
import com.gregpalacios.geocode.service.IComisariaService;

@RestController
@RequestMapping("/api/comisarias")
public class ComisariaController {

	@Autowired
	private IComisariaService service;
	
	@GetMapping
	public ResponseEntity<List<Comisaria>> listar() throws Exception {
		List<Comisaria> lista = service.listar();
		return new ResponseEntity<List<Comisaria>>(lista, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Comisaria> listarPorId(@PathVariable("id") Integer id) throws Exception {
		Comisaria obj = service.listarPorId(id);

		if (obj == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
		}

		return new ResponseEntity<Comisaria>(obj, HttpStatus.OK);
	}
}