package com.gregpalacios.geocode.service;

import java.util.List;

import com.gregpalacios.geocode.model.Banco;

public interface IBancoService extends ICRUD<Banco, Integer> {

	void registrarTransaccional(List<Banco> bancos) throws Exception;
}
