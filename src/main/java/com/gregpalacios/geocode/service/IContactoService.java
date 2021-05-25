package com.gregpalacios.geocode.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gregpalacios.geocode.model.Contacto;

public interface IContactoService extends ICRUD<Contacto, Integer> {

	List<Contacto> listarPorUsuario(String usuario) throws Exception;
	
	Page<Contacto> listarPorUsuarioPaginado(String usuario, Pageable pageable) throws Exception;
	
	void registrarTransaccional(List<Contacto> contactos) throws Exception;
}
