package com.gmr.formacion.springboot.app.item.model;

import java.util.Date;

public class Producto {

	private Long id;
	private String nombre;
	private Double precio;
	private Date createAt;

	public Producto() {
	}

	public Producto(Long id, String nombre, Double precio, Date createAt) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.precio = precio;
		this.createAt = createAt;
	}
	
	public Long getId() {
		return id;
	}
	public String getNombre() {
		return nombre;
	}
	public Double getPrecio() {
		return precio;
	}
	public Date getCreateAt() {
		return createAt;
	}
}
