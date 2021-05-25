package com.gregpalacios.geocode.service;

import com.gregpalacios.geocode.model.Usuario;

public interface ILoginService {

	Usuario verificarCorreoUsuario(String correo) throws Exception;
	
	Usuario verificarNombreUsuario(String usuario) throws Exception;
	
	void cambiarClaveUsuario(String clave, String usuario) throws Exception;
	
	void cambiarDatosUsuario(String nombres, String apellidos, String usuario) throws Exception;
}
