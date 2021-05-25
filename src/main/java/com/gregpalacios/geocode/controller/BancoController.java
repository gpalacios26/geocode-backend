package com.gregpalacios.geocode.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gregpalacios.geocode.exception.ModeloNotFoundException;
import com.gregpalacios.geocode.model.Banco;
import com.gregpalacios.geocode.service.IBancoService;

@RestController
@RequestMapping("/api/bancos")
public class BancoController {

	private static final Logger logger = LoggerFactory.getLogger(BancoController.class);
	
	@Autowired
	private IBancoService service;
	
	@GetMapping
	public ResponseEntity<List<Banco>> listar() throws Exception {
		List<Banco> lista = service.listar();
		return new ResponseEntity<List<Banco>>(lista, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Banco> listarPorId(@PathVariable("id") Integer id) throws Exception {
		Banco obj = service.listarPorId(id);

		if (obj == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
		}

		return new ResponseEntity<Banco>(obj, HttpStatus.OK);
	}
	
	@GetMapping("/cargar")
	public @ResponseBody Map<String, Object> cargar() throws Exception {
		
		boolean valido = true;
		HashMap<String, Object> respuesta = new HashMap<>();
		
		List<Banco> lista = service.listar();
		
		if(lista.isEmpty()) {
			String fileName = "data/bank-db.json";
			
			try {
	            ObjectMapper mapper = new ObjectMapper();
				TypeReference<List<Banco>> typeReference = new TypeReference<List<Banco>>(){};
				
				ClassLoader classLoader = getClass().getClassLoader();
		        InputStream inputStream = classLoader.getResourceAsStream(fileName);
		        
				List<Banco> bancos = mapper.readValue(inputStream,typeReference);
				
				service.registrarTransaccional(bancos);
	
			} catch (IOException e){
				logger.error(e.getMessage());
				
				valido = false;
			}
		}
		
		if(valido) {
			respuesta.put("estado", "1");
			respuesta.put("mensaje", "Datos registrados correctamente");
		} else {
			respuesta.put("estado", "0");
			respuesta.put("mensaje", "Error al registrar los datos");
		}
		
		return respuesta;
	}
}
