package com.gmr.formacion.springboot.app.productos.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.gmr.formacion.springboot.app.productos.models.entity.Producto;
import com.gmr.formacion.springboot.app.productos.models.service.IProductoService;

@RestController
public class ProductoController {

	@Autowired
	private IProductoService productoService;

	@Autowired
	private Environment environment;

	@GetMapping("/listar")
	public List<Producto> listar() {
		return productoService.finAll().stream().map(iterProducto -> {
			iterProducto.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
			return iterProducto;
		}).collect(Collectors.toList());
	}

	@GetMapping("/detalle/{id}")
	public Producto detalle(@PathVariable Long id) {
		Producto producto = productoService.findById(id);
		producto.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
		return producto;
	}
}
