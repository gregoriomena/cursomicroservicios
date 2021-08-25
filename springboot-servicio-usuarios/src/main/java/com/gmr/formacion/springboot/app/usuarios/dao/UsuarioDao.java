package com.gmr.formacion.springboot.app.usuarios.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.gmr.formacion.springboot.app.usuarios.models.entity.Usuario;

public interface UsuarioDao extends PagingAndSortingRepository<Usuario, Long> {

	Usuario findByUsername(String username);

}
