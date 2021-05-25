package com.gregpalacios.geocode.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "comisaria")
public class Comisaria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idComisaria;
	
	@NotNull
	@Column(name = "imagen", nullable = false)
	private String imagen;
	
	@NotNull
	@Column(name = "titulo", nullable = false)
	private String titulo;
	
	@NotNull
	@Column(name = "latitud", nullable = false)
	private Double latitud;
	
	@NotNull
	@Column(name = "longitud", nullable = false)
	private Double longitud;

	public Integer getIdComisaria() {
		return idComisaria;
	}

	public void setIdComisaria(Integer idComisaria) {
		this.idComisaria = idComisaria;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Double getLatitud() {
		return latitud;
	}

	public void setLatitud(Double latitud) {
		this.latitud = latitud;
	}

	public Double getLongitud() {
		return longitud;
	}

	public void setLongitud(Double longitud) {
		this.longitud = longitud;
	}

	@Override
	public String toString() {
		return "Comisaria [idComisaria=" + idComisaria + ", imagen=" + imagen + ", titulo=" + titulo + ", latitud="
				+ latitud + ", longitud=" + longitud + "]";
	}
}
