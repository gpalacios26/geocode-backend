package com.gregpalacios.geocode.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gregpalacios.geocode.model.Usuario;
import com.gregpalacios.geocode.repo.IGenericRepo;
import com.gregpalacios.geocode.repo.IUsuarioRepo;
import com.gregpalacios.geocode.service.IUsuarioService;

@Service
public class UsuarioServiceImpl extends CRUDImpl<Usuario, Integer> implements IUsuarioService, UserDetailsService {

	@Autowired
	private IUsuarioRepo repo;
	
	@Override
	protected IGenericRepo<Usuario, Integer> getRepo() {
		return repo;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Usuario usuario = repo.findOneByUsername(username);
		
		if(usuario == null) {
			throw new UsernameNotFoundException(String.format("Usuario no existe", username));
		}
		
		if(!usuario.isEnabled()) {
			throw new UsernameNotFoundException(String.format("Usuario no se encuentra activo", username));
		}
		
		List<GrantedAuthority> roles = new ArrayList<>();
		
		usuario.getRoles().forEach(rol -> {
			roles.add(new SimpleGrantedAuthority(rol.getNombre()));
		});
		
		UserDetails ud = new User(usuario.getUsername(), usuario.getPassword(), roles);
		
		return ud;
	}

	@Override
	public Page<Usuario> listarPageable(Pageable pageable) {
		return repo.listarPaginado(pageable);
	}
	
	@Override
	public void cambiarEstadoUsuario(Boolean estado, String usuario) throws Exception {
		repo.cambiarEstadoUsuario(estado, usuario);
	}

}