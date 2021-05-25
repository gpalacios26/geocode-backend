package com.gregpalacios.geocode.repo;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.gregpalacios.geocode.model.Usuario;

public interface ILoginRepo extends IGenericRepo<Usuario, Integer> {

	@Query("SELECT us FROM Usuario us WHERE us.correo =:correo")
	Usuario verificarCorreoUsuario(@Param("correo")String correo);
	
	@Query("SELECT us FROM Usuario us WHERE us.username =:usuario")
	Usuario verificarNombreUsuario(@Param("usuario")String usuario);
	
	@Transactional
	@Modifying
	@Query("UPDATE Usuario us SET us.password =:clave WHERE us.username =:usuario")
	void cambiarClaveUsuario(@Param("clave") String clave, @Param("usuario") String usuario);
	
	@Transactional
	@Modifying
	@Query("UPDATE Usuario us SET us.nombres =:nombres, us.apellidos =:apellidos WHERE us.username =:usuario")
	void cambiarDatosUsuario(@Param("nombres") String nombres, @Param("apellidos") String apellidos, @Param("usuario") String usuario);
}
