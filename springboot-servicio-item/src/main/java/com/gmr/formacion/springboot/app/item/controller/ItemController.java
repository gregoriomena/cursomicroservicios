package com.gmr.formacion.springboot.app.item.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.gmr.formacion.springboot.app.item.model.Item;
import com.gmr.formacion.springboot.app.item.model.Producto;
import com.gmr.formacion.springboot.app.item.model.service.ItemService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

@RestController
public class ItemController {

	private static Logger logger = org.slf4j.LoggerFactory.getLogger(ItemController.class);

	// Usado sólo para poder ver el número de llamada correspondiente y así
	// facilitar las pruebas para generar aciertos/fallos y probar CircuitBreaker
	private static int idLlamada = 0;

	@Autowired
	private CircuitBreakerFactory cbFactory;

	@Autowired
	@Qualifier("itemServiceRestTemplate")
	private ItemService itemService;

	@Value("${configuracion.texto}")
	private String configuracionTexto;

	@GetMapping("listar")
	public List<Item> listar() {
		return itemService.findAll();
	}

	// @HystrixCommand(fallbackMethod = "metodoAlternativo")
	@GetMapping("detalle/{id}/{cantidad}")
	public Item detalle(@PathVariable Long id, @PathVariable Integer cantidad) {
		idLlamada++;
		return cbFactory.create("items").run(() -> {
			Item item = itemService.findById(id, cantidad);
			item.setIdLlamada(idLlamada);
			return item;
		}, e -> metodoAlternativo(id, cantidad, e));
	}

	@CircuitBreaker(name = "items", fallbackMethod = "metodoAlternativo")
	@GetMapping("detalle2/{id}/{cantidad}")
	public Item detalleAnotado(@PathVariable Long id, @PathVariable Integer cantidad) {
		idLlamada++;
		Item item = itemService.findById(id, cantidad);
		item.setIdLlamada(idLlamada);
		return item;
	}

	// @TimeLimiter produce una Excepción, pero no hace que se entre en
	// cortocircuito. Para que entre en cortocircuito por peticiones lentas se debe
	// combinar con @CircuitBreaker
	// En este caso el fallbackMethod debe ir en CircuitBreaker
	@CircuitBreaker(name = "items", fallbackMethod = "metodoAlternativoTimelimiter")
	@TimeLimiter(name = "items")
	@GetMapping("detalle3/{id}/{cantidad}")
	public CompletableFuture<Item> detalleAnotadoTimelimiter(@PathVariable Long id, @PathVariable Integer cantidad) {

		return CompletableFuture.supplyAsync(() -> {
			idLlamada++;
			Item item = itemService.findById(id, cantidad);
			item.setIdLlamada(idLlamada);
			return item;
		});

	}

	@GetMapping("/obtener-config")
	public ResponseEntity<?> obtenerConfigurarion(@Value("${server.port}") String puerto){
		Map<String, String> json = new HashMap<String, String>();
		json.put("texto", configuracionTexto);
		json.put("port", puerto);
		return new ResponseEntity<Map<String, String>>(json, HttpStatus.OK);
	}

	public Item metodoAlternativo(Long id, Integer cantidad, Throwable e) {

		// Podría intentar comunicar con otro servicio

		logger.info("Ejecutando método alternativo " + e.getMessage());

		Producto producto = new Producto(id, "Test error", 0.0, new Date(), 9999);
		Item item = new Item(producto, cantidad);
		item.setIdLlamada(idLlamada);

		return item;
	}

	public CompletableFuture<Item> metodoAlternativoTimelimiter(Long id, Integer cantidad, Throwable e) {

		// Podría intentar comunicar con otro servicio

		return CompletableFuture.supplyAsync(() -> {

			logger.info("Ejecutando método alternativo TimeLimiter " + e.getMessage());

			idLlamada++;
			Producto producto = new Producto(id, "Test error", 0.0, new Date(), 9999);
			Item item = new Item(producto, cantidad);
			item.setIdLlamada(idLlamada);

			return item;
		});
	}

}
