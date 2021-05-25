package com.gregpalacios.geocode.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "bombero")
public class Bombero {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idBombero;
	
	@NotNull
	@Column(name = "imagen", nullable = false)
	private String imagen;
	
	@NotNull
	@Column(name = "titulo", nullable = false)
	private String titulo;
	
	@NotNull
	@Column(name = "direccion", nullable = false)
	private String direccion;
	
	@NotNull
	@Column(name = "latitud", nullable = false)
	private Double latitud;
	
	@NotNull
	@Column(name = "longitud", nullable = false)
	private Double longitud;

	public Integer getIdBombero() {
		return idBombero;
	}

	public void setIdBombero(Integer idBombero) {
		this.idBombero = idBombero;
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

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
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
		return "Bombero [idBombero=" + idBombero + ", imagen=" + imagen + ", titulo=" + titulo + ", direccion="
				+ direccion + ", latitud=" + latitud + ", longitud=" + longitud + "]";
	}
}
