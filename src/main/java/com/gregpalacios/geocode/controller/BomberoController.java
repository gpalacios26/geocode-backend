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
import com.gregpalacios.geocode.model.Bombero;
import com.gregpalacios.geocode.service.IBomberoService;

@RestController
@RequestMapping("/api/bomberos")
public class BomberoController {

	@Autowired
	private IBomberoService service;
	
	@GetMapping
	public ResponseEntity<List<Bombero>> listar() throws Exception {
		List<Bombero> lista = service.listar();
		return new ResponseEntity<List<Bombero>>(lista, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Bombero> listarPorId(@PathVariable("id") Integer id) throws Exception {
		Bombero obj = service.listarPorId(id);

		if (obj == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
		}

		return new ResponseEntity<Bombero>(obj, HttpStatus.OK);
	}
}