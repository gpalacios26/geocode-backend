package com.gregpalacios.geocode.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregpalacios.geocode.model.Evento;
import com.gregpalacios.geocode.repo.IEventoRepo;
import com.gregpalacios.geocode.repo.IGenericRepo;
import com.gregpalacios.geocode.service.IEventoService;

@Service
public class EventoServiceImpl extends CRUDImpl<Evento, Integer> implements IEventoService {

	@Autowired
	private IEventoRepo repo;
	
	@Override
	protected IGenericRepo<Evento, Integer> getRepo() {
		return repo;
	}

}
