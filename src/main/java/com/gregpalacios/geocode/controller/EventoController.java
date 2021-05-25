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
import com.gregpalacios.geocode.model.Evento;
import com.gregpalacios.geocode.service.IEventoService;

@RestController
@RequestMapping("/api/eventos")
public class EventoController {

	@Autowired
	private IEventoService service;
	
	@GetMapping
	public ResponseEntity<List<Evento>> listar() throws Exception {
		List<Evento> lista = service.listar();
		return new ResponseEntity<List<Evento>>(lista, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Evento> listarPorId(@PathVariable("id") Integer id) throws Exception {
		Evento obj = service.listarPorId(id);

		if (obj == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
		}

		return new ResponseEntity<Evento>(obj, HttpStatus.OK);
	}
}