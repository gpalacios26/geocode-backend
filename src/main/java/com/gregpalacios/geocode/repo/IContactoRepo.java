package com.gregpalacios.geocode.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gregpalacios.geocode.model.Contacto;

public interface IContactoRepo extends IGenericRepo<Contacto, Integer> {

	@Query("SELECT co FROM Contacto co WHERE co.usuario.username =:usuario")
	List<Contacto> listarPorUsuario(@Param("usuario")String usuario);
	
	@Query("SELECT co FROM Contacto co WHERE co.usuario.username =:usuario ORDER BY co.idContacto DESC")
	Page<Contacto> listarPorUsuarioPaginado(@Param("usuario")String usuario, Pageable pageable);
}
