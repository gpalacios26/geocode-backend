package com.gregpalacios.geocode.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gregpalacios.geocode.model.Incidencia;

public interface IIncidenciaService extends ICRUD<Incidencia, Integer> {

	Page<Incidencia> listarPageable(Pageable pageable);
}
