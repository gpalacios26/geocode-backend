package com.gregpalacios.geocode.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gregpalacios.geocode.model.Farmacia;
import com.gregpalacios.geocode.repo.IFarmaciaRepo;
import com.gregpalacios.geocode.repo.IGenericRepo;
import com.gregpalacios.geocode.service.IFarmaciaService;

@Service
public class FarmaciaServiceImpl extends CRUDImpl<Farmacia, Integer> implements IFarmaciaService {

	@Autowired
	private IFarmaciaRepo repo;

	@Override
	protected IGenericRepo<Farmacia, Integer> getRepo() {
		return repo;
	}
	
	@Transactional
	@Override
	public void registrarTransaccional(List<Farmacia> farmacias) throws Exception {
		for(Farmacia farmacia: farmacias) {
			repo.save(farmacia);
		}
	}

}
