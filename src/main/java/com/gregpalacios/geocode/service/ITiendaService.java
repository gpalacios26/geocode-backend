package com.gregpalacios.geocode.service;

import java.util.List;

import com.gregpalacios.geocode.model.Tienda;

public interface ITiendaService extends ICRUD<Tienda, Integer> {

	void registrarTransaccional(List<Tienda> tiendas) throws Exception;
}
