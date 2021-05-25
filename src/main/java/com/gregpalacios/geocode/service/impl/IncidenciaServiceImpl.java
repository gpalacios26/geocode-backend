package com.gregpalacios.geocode.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gregpalacios.geocode.model.Incidencia;
import com.gregpalacios.geocode.repo.IGenericRepo;
import com.gregpalacios.geocode.repo.IIncidenciaRepo;
import com.gregpalacios.geocode.service.IIncidenciaService;

@Service
public class IncidenciaServiceImpl extends CRUDImpl<Incidencia, Integer> implements IIncidenciaService {

	@Autowired
	private IIncidenciaRepo repo;
	
	@Override
	protected IGenericRepo<Incidencia, Integer> getRepo() {
		return repo;
	}

	@Override
	public Page<Incidencia> listarPageable(Pageable pageable) {
		return repo.listarPaginado(pageable);
	}

}
