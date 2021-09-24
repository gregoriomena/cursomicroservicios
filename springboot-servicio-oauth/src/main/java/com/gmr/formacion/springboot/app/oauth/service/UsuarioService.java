package com.gmr.formacion.springboot.app.oauth.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gmr.formacion.springboot.app.commons.usuarios.models.entity.Usuario;
import com.gmr.formacion.springboot.app.oauth.clients.UsuarioFeignClient;

@Service
public class UsuarioService implements UserDetailsService, IUsuarioService {

	private Logger logger = LoggerFactory.getLogger(UsuarioService.class);

	@Autowired
	private UsuarioFeignClient usuarioClient;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioClient.findByUsername(username);

		if (usuario == null) {
			logger.error("El usuario " + username + " no existe en BD");
			throw new UsernameNotFoundException("El usuario " + username + " no existe en BD");
		}

		List<GrantedAuthority> authorities = usuario.getRoles().stream()
				.map(rol -> new SimpleGrantedAuthority(rol.getNombre()))
				.peek(authority -> logger.info(authority.getAuthority()))
				.collect(Collectors.toList());

		logger.info("Usuario autentificado " + username);

		return new User(username, usuario.getPassword(), usuario.getEnabled().equals("Y"), true, true, true,
				authorities);
	}

	@Override
	public Usuario findByUsername(String username) {
		return usuarioClient.findByUsername(username);
	}

	@Override
	public Usuario update(Usuario usuario, Long id) {

		return usuarioClient.update(usuario, id);
	}

}
