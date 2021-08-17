package com.gmr.formacion.springboot.app.productos.models.service;

import java.util.List;

import com.gmr.formacion.springboot.app.productos.models.entity.Producto;

public interface IProductoService {

	public List<Producto> finAll();
	public Producto findById(Long id);

	public Producto save(Producto producto);
	public void deleteById(Long id);
}

