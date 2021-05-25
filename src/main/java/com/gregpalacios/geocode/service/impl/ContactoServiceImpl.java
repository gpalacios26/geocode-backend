package com.gregpalacios.geocode.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gregpalacios.geocode.model.Contacto;
import com.gregpalacios.geocode.repo.IContactoRepo;
import com.gregpalacios.geocode.repo.IGenericRepo;
import com.gregpalacios.geocode.service.IContactoService;

@Service
public class ContactoServiceImpl extends CRUDImpl<Contacto, Integer> implements IContactoService {

	@Autowired
	private IContactoRepo repo;
	
	@Override
	protected IGenericRepo<Contacto, Integer> getRepo() {
		return repo;
	}

	@Override
	public List<Contacto> listarPorUsuario(String usuario) throws Exception {
		return repo.listarPorUsuario(usuario);
	}

	@Override
	public Page<Contacto> listarPorUsuarioPaginado(String usuario, Pageable pageable) throws Exception {
		return repo.listarPorUsuarioPaginado(usuario, pageable);
	}
	
	@Transactional
	@Override
	public void registrarTransaccional(List<Contacto> contactos) throws Exception {
		for(Contacto contacto: contactos) {
			contacto.setFecha(LocalDateTime.now());
			repo.save(contacto);
		}
	}


}
