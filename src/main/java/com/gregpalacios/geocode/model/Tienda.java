package com.gregpalacios.geocode.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tienda")
public class Tienda {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idTienda;
	
	@NotNull
	@Column(name = "tienda", nullable = false)
	private String tienda;
	
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

	public Integer getIdTienda() {
		return idTienda;
	}

	public void setIdTienda(Integer idTienda) {
		this.idTienda = idTienda;
	}

	public String getTienda() {
		return tienda;
	}

	public void setTienda(String tienda) {
		this.tienda = tienda;
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
		return "Tienda [idTienda=" + idTienda + ", tienda=" + tienda + ", imagen=" + imagen + ", titulo=" + titulo
				+ ", direccion=" + direccion + ", latitud=" + latitud + ", longitud=" + longitud + "]";
	}
}
