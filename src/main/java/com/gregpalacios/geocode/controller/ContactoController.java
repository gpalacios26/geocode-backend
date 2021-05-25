package com.gregpalacios.geocode.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gregpalacios.geocode.exception.ModeloNotFoundException;
import com.gregpalacios.geocode.model.Contacto;
import com.gregpalacios.geocode.service.IContactoService;
import com.gregpalacios.geocode.util.ExcelGenerator;
import com.gregpalacios.geocode.util.ExcelReader;

@RestController
@RequestMapping("/api/contactos")
public class ContactoController {

	private static final Logger logger = LoggerFactory.getLogger(ContactoController.class);
	
	private String pathContactos = "D:\\VAR\\FILES\\CONTACTO\\";
	
	@Autowired
	private IContactoService service;
	
	@GetMapping
	public ResponseEntity<List<Contacto>> listar() throws Exception {
		List<Contacto> lista = service.listar();
		return new ResponseEntity<List<Contacto>>(lista, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Contacto> listarPorId(@PathVariable("id") Integer id) throws Exception {
		Contacto obj = service.listarPorId(id);

		if (obj == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
		}

		return new ResponseEntity<Contacto>(obj, HttpStatus.OK);
	}
	
	@GetMapping("/usuario/{usuario}")
	public ResponseEntity<List<Contacto>> listarPorUsuario(@PathVariable("usuario") String usuario) throws Exception {
		List<Contacto> lista = service.listarPorUsuario(usuario);
		return new ResponseEntity<List<Contacto>>(lista, HttpStatus.OK);
	}
	
	@GetMapping("/usuario/{usuario}/pageable")
	public ResponseEntity<Page<Contacto>> listarPorUsuarioPaginado(@PathVariable("usuario") String usuario, Pageable pageable) throws Exception {
		Page<Contacto> lista = service.listarPorUsuarioPaginado(usuario, pageable);
		return new ResponseEntity<Page<Contacto>>(lista, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Contacto> registrar(@Valid @RequestBody Contacto c) throws Exception {
		c.setFecha(LocalDateTime.now());
		
		Contacto obj = service.registrar(c);
		return new ResponseEntity<Contacto>(obj, HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<Contacto> modificar(@Valid @RequestBody Contacto c) throws Exception {
		c.setFecha(LocalDateTime.now());
		
		Contacto obj = service.modificar(c);
		return new ResponseEntity<Contacto>(obj, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) throws Exception {
		Contacto obj = service.listarPorId(id);

		if (obj == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
		}

		service.eliminar(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/descargar/{usuario}")
	public ResponseEntity<InputStreamResource> descargar(@PathVariable("usuario") String usuario) throws Exception, IOException {
		List<Contacto> lista = service.listarPorUsuario(usuario);
    
	    ByteArrayInputStream in = ExcelGenerator.contactosToExcel(lista);
	    
	    HttpHeaders headers = new HttpHeaders();
	    headers.add("Content-Disposition", "attachment; filename=contactos.xlsx");
	    
	    return ResponseEntity.ok().headers(headers).body(new InputStreamResource(in));
    }
	
	@GetMapping("/formato")
	public ResponseEntity<InputStreamResource> formato() throws Exception, IOException {
		
		String filePath = pathContactos+"FORMATO\\FormatoContacto.xlsx";
		File file = new File(filePath);
		InputStream in = new FileInputStream(file.getPath());
	    
	    HttpHeaders headers = new HttpHeaders();
	    headers.add("Content-Disposition", "attachment; filename=formato.xlsx");
	    
	    return ResponseEntity.ok().headers(headers).body(new InputStreamResource(in));
    }
	
	@PostMapping(value = "/cargar", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public @ResponseBody Map<String, Object> cargar(@RequestParam("adjunto") MultipartFile file) throws Exception, IOException {
		
		boolean valido = true;
		HashMap<String, Object> respuesta = new HashMap<>();
		List<Contacto> lista = new ArrayList<Contacto>();
		
		try {
			byte[] archivo = file.getBytes();
			String nombreArchivo = crearArchivo(archivo);
			String filePath = pathContactos+nombreArchivo;
			
			lista = ExcelReader.leerContactos(filePath);
			
			File fileTemp = new File(filePath);
			fileTemp.delete();
			
		} catch (IOException e){
			logger.error(e.getMessage());
			
			valido = false;
		}
	
		if(valido) {
			respuesta.put("estado", "1");
			respuesta.put("contactos", lista);
		} else {
			respuesta.put("estado", "0");
		}
		
		return respuesta;
	}
	
	@PostMapping("/procesar")
	public @ResponseBody Map<String, Object> procesar(@Valid @RequestBody List<Contacto> contactos) throws Exception {
		
		boolean valido = true;
		HashMap<String, Object> respuesta = new HashMap<>();
		
		try {
			service.registrarTransaccional(contactos);
			
		} catch (Exception e){
			logger.error(e.getMessage());
			
			valido = false;
		}
	
		if(valido) {
			respuesta.put("estado", "1");
		} else {
			respuesta.put("estado", "0");
		}
		
		return respuesta;
	}
	
	public String crearArchivo(byte[] archivoByte) throws IOException {
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String fileName = "file-"+timestamp.getTime()+".xlsx";
        String filePath = pathContactos+fileName;
        
        Path path = Paths.get(filePath);
        Files.createFile(path);
        
        OutputStream out = new FileOutputStream(filePath);
        out.write(archivoByte);
        out.close();
        
        return fileName;
    }
}
