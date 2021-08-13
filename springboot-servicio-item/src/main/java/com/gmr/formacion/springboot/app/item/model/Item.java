package com.gmr.formacion.springboot.app.item.model;

public class Item {

	private Producto producto;
	private Integer cantidad;

	// Usado sólo para poder ver el número de llamada correspondiente y así facilitar las pruebas para generar aciertos/fallos y probar CircuitBreaker
	private int idLlamada;

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

	public int getIdLlamada() {
		return idLlamada;
	}

	public void setIdLlamada(int idLlamada) {
		this.idLlamada = idLlamada;
	}
}
