package com.gmr.formacion.springboot.app.oauth.service;

import com.gmr.formacion.springboot.app.commons.usuarios.models.entity.Usuario;

public interface IUsuarioService {

	Usuario findByUsername(String username);
	Usuario update(Usuario usuario, Long id);
}
