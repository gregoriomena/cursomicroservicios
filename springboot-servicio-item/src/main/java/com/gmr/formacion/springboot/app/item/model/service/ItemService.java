package com.gmr.formacion.springboot.app.item.model.service;

import java.util.List;

import com.gmr.formacion.springboot.app.item.model.Item;
import com.gmr.formacion.springboot.app.commons.models.entity.Producto;

public interface ItemService {

	public List<Item> findAll();
	public Item findById(Long id, Integer cantidad);

	public Producto save(Producto producto);
	public Producto update(Producto producto, Long id);
	public void delete(Long id);
}
