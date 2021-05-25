package com.gregpalacios.geocode.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gregpalacios.geocode.exception.ModeloNotFoundException;
import com.gregpalacios.geocode.model.Incidencia;
import com.gregpalacios.geocode.service.IIncidenciaService;
import com.gregpalacios.geocode.util.PdfGenerator;

@RestController
@RequestMapping("/api/incidencias")
public class IncidenciaController {

	@Autowired
	private IIncidenciaService service;
	
	@GetMapping
	public ResponseEntity<List<Incidencia>> listar() throws Exception {
		List<Incidencia> lista = service.listar();
		return new ResponseEntity<List<Incidencia>>(lista, HttpStatus.OK);
	}
	
	@GetMapping("/pageable")
	public ResponseEntity<Page<Incidencia>> listarPageable(Pageable pageable) throws Exception {
		Page<Incidencia> lista = service.listarPageable(pageable);
		return new ResponseEntity<Page<Incidencia>>(lista, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Incidencia> listarPorId(@PathVariable("id") Integer id) throws Exception {
		Incidencia obj = service.listarPorId(id);

		if (obj == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
		}

		return new ResponseEntity<Incidencia>(obj, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Incidencia> registrar(@Valid @RequestBody Incidencia i) throws Exception {
		i.setFecha(LocalDateTime.now());
		
		Incidencia obj = service.registrar(i);
		return new ResponseEntity<Incidencia>(obj, HttpStatus.CREATED);
	}
	
	@GetMapping("/descargar/{id}")
	public ResponseEntity<InputStreamResource> descargar(@PathVariable("id") Integer id) throws Exception, IOException {
		Incidencia obj = service.listarPorId(id);
		
		if (obj == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
		}
    
	    ByteArrayInputStream in = PdfGenerator.incidenciaToPdf(obj);
	    
	    HttpHeaders headers = new HttpHeaders();
	    headers.add("Content-Disposition", "attachment; filename=incidencia.pdf");
	    
	    return ResponseEntity.ok().headers(headers).body(new InputStreamResource(in));
    }
}