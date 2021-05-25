package com.gregpalacios.geocode.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "banco")
public class Banco {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idBanco;
	
	@NotNull
	@Column(name = "banco", nullable = false)
	private String banco;
	
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

	public Integer getIdBanco() {
		return idBanco;
	}

	public void setIdBanco(Integer idBanco) {
		this.idBanco = idBanco;
	}

	public String getBanco() {
		return banco;
	}

	public void setBanco(String banco) {
		this.banco = banco;
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
		return "Banco [idBanco=" + idBanco + ", banco=" + banco + ", imagen=" + imagen + ", titulo=" + titulo
				+ ", direccion=" + direccion + ", latitud=" + latitud + ", longitud=" + longitud + "]";
	}
}
