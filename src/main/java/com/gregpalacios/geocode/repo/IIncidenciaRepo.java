package com.gregpalacios.geocode.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import com.gregpalacios.geocode.model.Incidencia;

public interface IIncidenciaRepo extends IGenericRepo<Incidencia, Integer> {

	@Query("SELECT i FROM Incidencia i ORDER BY i.idIncidencia DESC")
	Page<Incidencia> listarPaginado(Pageable pageable);
}
