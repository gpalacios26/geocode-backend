package com.gregpalacios.geocode.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregpalacios.geocode.model.Comisaria;
import com.gregpalacios.geocode.repo.IComisariaRepo;
import com.gregpalacios.geocode.repo.IGenericRepo;
import com.gregpalacios.geocode.service.IComisariaService;

@Service
public class ComisariaServiceImpl extends CRUDImpl<Comisaria, Integer> implements IComisariaService {

	@Autowired
	private IComisariaRepo repo;
	
	@Override
	protected IGenericRepo<Comisaria, Integer> getRepo() {
		return repo;
	}

}
