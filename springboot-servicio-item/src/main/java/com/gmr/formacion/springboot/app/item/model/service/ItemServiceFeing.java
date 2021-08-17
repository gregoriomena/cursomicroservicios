package com.gmr.formacion.springboot.app.item.model.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.gmr.formacion.springboot.app.item.clientes.ProductoClienteRest;
import com.gmr.formacion.springboot.app.item.model.Item;
import com.gmr.formacion.springboot.app.item.model.Producto;

@Service("itemServiceFeing")
@Primary
public class ItemServiceFeing implements ItemService {

	@Autowired
	private ProductoClienteRest clienteFaing;

	@Override
	public List<Item> findAll() {
		return clienteFaing.listar().stream().map(iterProducto -> new Item(iterProducto, 10)).collect(Collectors.toList());
	}

	@Override
	public Item findById(Long id, Integer cantidad) {
		return new Item(clienteFaing.findById(id), cantidad);
	}

	@Override
	public Producto save(Producto producto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Producto update(Producto producto, Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

}
