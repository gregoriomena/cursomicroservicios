package com.gmr.formacion.springboot.app.productos.controllers;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.gmr.formacion.springboot.app.commons.models.entity.Producto;
import com.gmr.formacion.springboot.app.productos.models.service.IProductoService;

@RestController
public class ProductoController {

	private static Logger log = org.slf4j.LoggerFactory.getLogger(ProductoController.class);

	@Autowired
	private IProductoService productoService;

	@Value("${server.port}")
	private Integer port;

	@GetMapping("/listar")
	public List<Producto> listar() {
		return productoService.finAll().stream().map(iterProducto -> {
			iterProducto.setPort(port);
			return iterProducto;
		}).collect(Collectors.toList());
	}

	@GetMapping("/detalle/{id}")
	public Producto detalle(@PathVariable Long id) throws InterruptedException {

		// Para probar el CircuitBreaker
		if (id.equals(10L)) {
			throw new IllegalStateException("Producto no encontrado");
		}
		if (id.equals(20L)) {
			TimeUnit.SECONDS.sleep(2L);
			id = 2L;
		}

		Producto producto = productoService.findById(id);
		producto.setPort(port);

		return producto;
	}

	@PostMapping("/crear")
	@ResponseStatus(code = HttpStatus.CREATED)
	public Producto crear(@RequestBody Producto producto) {

		return productoService.save(producto);
	}

	@PutMapping("/editar/{id}")
	@ResponseStatus(code = HttpStatus.CREATED)
	public Producto editar(@RequestBody Producto producto, @PathVariable Long id) {

		Producto productoDb = productoService.findById(id);

		productoDb.setPrecio(producto.getPrecio());
		productoDb.setNombre(producto.getNombre());

		return productoService.save(productoDb);
	}

	@DeleteMapping("/eliminar/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void eliminar(@PathVariable Long id) {
		productoService.deleteById(id);
	}

}
