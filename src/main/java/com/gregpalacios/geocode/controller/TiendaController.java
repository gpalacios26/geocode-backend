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
import com.gregpalacios.geocode.model.Tienda;
import com.gregpalacios.geocode.service.ITiendaService;

@RestController
@RequestMapping("/api/tiendas")
public class TiendaController {
	
	private static final Logger logger = LoggerFactory.getLogger(TiendaController.class);
	
	@Autowired
	private ITiendaService service;
	
	@GetMapping
	public ResponseEntity<List<Tienda>> listar() throws Exception {
		List<Tienda> lista = service.listar();
		return new ResponseEntity<List<Tienda>>(lista, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Tienda> listarPorId(@PathVariable("id") Integer id) throws Exception {
		Tienda obj = service.listarPorId(id);

		if (obj == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
		}

		return new ResponseEntity<Tienda>(obj, HttpStatus.OK);
	}
	
	@GetMapping("/cargar")
	public @ResponseBody Map<String, Object> cargar() throws Exception {
		
		boolean valido = true;
		HashMap<String, Object> respuesta = new HashMap<>();
		
		List<Tienda> lista = service.listar();
		
		if(lista.isEmpty()) {
			String fileName = "data/store-db.json";
			
			try {
	            ObjectMapper mapper = new ObjectMapper();
				TypeReference<List<Tienda>> typeReference = new TypeReference<List<Tienda>>(){};
				
				ClassLoader classLoader = getClass().getClassLoader();
		        InputStream inputStream = classLoader.getResourceAsStream(fileName);
		        
				List<Tienda> tiendas = mapper.readValue(inputStream,typeReference);
				
				service.registrarTransaccional(tiendas);
	
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
