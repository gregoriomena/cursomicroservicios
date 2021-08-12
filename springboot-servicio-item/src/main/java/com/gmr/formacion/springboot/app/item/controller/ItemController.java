package com.gmr.formacion.springboot.app.item.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.gmr.formacion.springboot.app.item.model.Item;
import com.gmr.formacion.springboot.app.item.model.Producto;
import com.gmr.formacion.springboot.app.item.model.service.ItemService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class ItemController {

	@Autowired
	@Qualifier("itemServiceRestTemplate")
	private ItemService itemService;

	@GetMapping("listar")
	public List<Item> listar(){
		return itemService.findAll();
	}

	@HystrixCommand(fallbackMethod = "metodoAlternativo")
	@GetMapping("detalle/{id}/{cantidad}")
	public Item detalle(@PathVariable Long id, @PathVariable Integer cantidad){
		return itemService.findById(id, cantidad);
	}

	public Item metodoAlternativo(Long id, Integer cantidad){

		// Podría intentar comunicar con otro servicio


		Producto producto = new Producto(id, "Test error", 0.0, new Date(), 9999);
		Item item = new Item(producto, cantidad);


		return item;
	}


}
