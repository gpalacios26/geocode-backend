package com.gregpalacios.geocode.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gregpalacios.geocode.model.Tienda;
import com.gregpalacios.geocode.repo.IGenericRepo;
import com.gregpalacios.geocode.repo.ITiendaRepo;
import com.gregpalacios.geocode.service.ITiendaService;

@Service
public class TiendaServiceImpl extends CRUDImpl<Tienda, Integer> implements ITiendaService {

	@Autowired
	private ITiendaRepo repo;
	
	@Override
	protected IGenericRepo<Tienda, Integer> getRepo() {
		return repo;
	}

	@Transactional
	@Override
	public void registrarTransaccional(List<Tienda> tiendas) throws Exception {
		for(Tienda tienda: tiendas) {
			repo.save(tienda);
		}
	}

}
