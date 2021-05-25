package com.gregpalacios.geocode.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gregpalacios.geocode.model.Usuario;

public interface IUsuarioService extends ICRUD<Usuario, Integer> {

	Page<Usuario> listarPageable(Pageable pageable);
	
	void cambiarEstadoUsuario(Boolean estado, String usuario) throws Exception;
}
