package com.gmr.formacion.springboot.app.item.model;

public class Item {

	private Producto producto;
	private Integer cantidad;

	public Item() {
	}

	public Item(Producto producto, Integer cantidad) {
		this.producto = producto;
		this.cantidad = cantidad;
	}
	
	public Double total() {
		return producto.getPrecio() * cantidad.doubleValue();
	}

	public Producto getProducto() {
		return producto;
	}

	public Integer getCantidad() {
		return cantidad;
	}
}
