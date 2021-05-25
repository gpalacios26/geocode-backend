package com.gregpalacios.geocode.service;

import java.util.List;

import com.gregpalacios.geocode.model.Farmacia;

public interface IFarmaciaService extends ICRUD<Farmacia, Integer> {

	void registrarTransaccional(List<Farmacia> farmacias) throws Exception;
}
