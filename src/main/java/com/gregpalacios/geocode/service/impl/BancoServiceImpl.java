package com.gregpalacios.geocode.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gregpalacios.geocode.model.Banco;
import com.gregpalacios.geocode.repo.IBancoRepo;
import com.gregpalacios.geocode.repo.IGenericRepo;
import com.gregpalacios.geocode.service.IBancoService;

@Service
public class BancoServiceImpl extends CRUDImpl<Banco, Integer> implements IBancoService {

	@Autowired
	private IBancoRepo repo;

	@Override
	protected IGenericRepo<Banco, Integer> getRepo() {
		return repo;
	}
	
	@Transactional
	@Override
	public void registrarTransaccional(List<Banco> bancos) throws Exception {
		for(Banco banco: bancos) {
			repo.save(banco);
		}
	}
	
}
