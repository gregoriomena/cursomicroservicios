package com.gmr.formacion.springboot.app.productos.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.gmr.formacion.springboot.app.commons.models.entity.Producto;

public interface ProductoDao extends CrudRepository<Producto, Long>{

}

