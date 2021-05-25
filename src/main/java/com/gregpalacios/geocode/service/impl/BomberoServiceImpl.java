package com.gregpalacios.geocode.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregpalacios.geocode.model.Bombero;
import com.gregpalacios.geocode.repo.IBomberoRepo;
import com.gregpalacios.geocode.repo.IGenericRepo;
import com.gregpalacios.geocode.service.IBomberoService;

@Service
public class BomberoServiceImpl extends CRUDImpl<Bombero, Integer> implements IBomberoService {

	@Autowired
	private IBomberoRepo repo;
	
	@Override
	protected IGenericRepo<Bombero, Integer> getRepo() {
		return repo;
	}

}
