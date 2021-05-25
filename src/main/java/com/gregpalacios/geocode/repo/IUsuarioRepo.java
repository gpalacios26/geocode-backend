package com.gregpalacios.geocode.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.gregpalacios.geocode.model.Usuario;

public interface IUsuarioRepo extends IGenericRepo<Usuario, Integer> {

	Usuario findOneByUsername(String username);
	
	@Query("SELECT us FROM Usuario us ORDER BY us.idUsuario DESC")
	Page<Usuario> listarPaginado(Pageable pageable);
	
	@Transactional
	@Modifying
	@Query("UPDATE Usuario us SET us.enabled =:estado WHERE us.username =:usuario")
	void cambiarEstadoUsuario(@Param("estado") Boolean estado, @Param("usuario") String usuario);
}
