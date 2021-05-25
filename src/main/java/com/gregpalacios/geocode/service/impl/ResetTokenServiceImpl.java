package com.gregpalacios.geocode.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregpalacios.geocode.model.ResetToken;
import com.gregpalacios.geocode.repo.IResetTokenRepo;
import com.gregpalacios.geocode.service.IResetTokenService;

@Service
public class ResetTokenServiceImpl implements IResetTokenService {

	@Autowired
	private IResetTokenRepo repo;
	
	@Override
	public ResetToken findByToken(String token) throws Exception {
		return repo.findByToken(token);
	}

	@Override
	public void guardar(ResetToken token) throws Exception {
		repo.save(token);
	}

	@Override
	public void eliminar(ResetToken token) throws Exception {
		repo.delete(token);
	}

}
