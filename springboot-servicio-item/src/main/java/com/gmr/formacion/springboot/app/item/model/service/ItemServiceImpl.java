package com.gmr.formacion.springboot.app.item.model.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.gmr.formacion.springboot.app.item.model.Item;
import com.gmr.formacion.springboot.app.item.model.Producto;

@Service("itemServiceRestTemplate")
public class ItemServiceImpl implements ItemService {

	@Autowired
	private RestTemplate clienteRest;
	
	@Override
	public List<Item> findAll() {
		
		List<Producto> productos = Arrays.asList(clienteRest.getForObject("http://localhost:8001/listar", Producto[].class));
		
		return productos.stream().map(iterProducto -> new Item(iterProducto, 5)).collect(Collectors.toList());
	}

	@Override
	public Item findById(Long id, Integer cantidad) {
		
		Map<String, String> pathVariables = new HashMap<String, String>();
		pathVariables.put("id", id.toString());
		
		Producto producto = clienteRest.getForObject("http://localhost:8001/detalle/{id}", Producto.class, pathVariables);
		return new Item(producto, cantidad);
	}

}
