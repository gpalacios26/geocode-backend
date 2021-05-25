package com.gregpalacios.geocode.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.gregpalacios.geocode.model.Usuario;
import com.gregpalacios.geocode.repo.ILoginRepo;
import com.gregpalacios.geocode.service.ILoginService;

@Service
public class LoginServiceImpl implements ILoginService {

	@Autowired
	private BCryptPasswordEncoder bcrypt;
	
	@Autowired
	private ILoginRepo repo;
	
	@Override
	public Usuario verificarCorreoUsuario(String correo) throws Exception {
		return repo.verificarCorreoUsuario(correo);
	}
	
	@Override
	public Usuario verificarNombreUsuario(String usuario) throws Exception {
		return repo.verificarNombreUsuario(usuario);
	}

	@Override
	public void cambiarClaveUsuario(String clave, String usuario) throws Exception {
		repo.cambiarClaveUsuario(bcrypt.encode(clave), usuario);
	}

	@Override
	public void cambiarDatosUsuario(String nombres, String apellidos, String usuario) throws Exception {
		repo.cambiarDatosUsuario(nombres, apellidos, usuario);
	}

}
