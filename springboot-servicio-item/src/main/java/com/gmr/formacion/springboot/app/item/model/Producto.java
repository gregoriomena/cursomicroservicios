package com.gmr.formacion.springboot.app.item.model;

import java.util.Date;

public class Producto {

	private Long id;
	private String nombre;
	private Double precio;
	private Date createAt;
	private Integer port;

	public Producto() {
	}

	public Producto(Long id, String nombre, Double precio, Date createAt, Integer port) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.precio = precio;
		this.createAt = createAt;
		this.port = port;
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

	public Integer getPort() {
		return port;
	}
}
