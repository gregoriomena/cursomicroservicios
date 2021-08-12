package com.gmr.formacion.springboot.app.productos.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.gmr.formacion.springboot.app.productos.models.entity.Producto;
import com.gmr.formacion.springboot.app.productos.models.service.IProductoService;

@RestController
public class ProductoController {

	@Autowired
	private IProductoService productoService;
	
	@GetMapping("/listar")
	public List<Producto> listar(){
		return productoService.finAll();
	}
	
	@GetMapping("/detalle/{id}")
	public Producto detalle(@PathVariable Long id) {
		return productoService.findById(id);
	}
}

