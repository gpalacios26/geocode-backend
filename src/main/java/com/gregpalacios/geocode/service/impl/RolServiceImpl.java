package com.gregpalacios.geocode.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregpalacios.geocode.model.Rol;
import com.gregpalacios.geocode.repo.IGenericRepo;
import com.gregpalacios.geocode.repo.IRolRepo;
import com.gregpalacios.geocode.service.IRolService;

@Service
public class RolServiceImpl extends CRUDImpl<Rol, Integer> implements IRolService {

	@Autowired
	private IRolRepo repo;
	
	@Override
	protected IGenericRepo<Rol, Integer> getRepo() {
		return repo;
	}

}
