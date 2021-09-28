package com.gmr.formacion.springboot.app.oauth.security.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import com.gmr.formacion.springboot.app.commons.usuarios.models.entity.Usuario;
import com.gmr.formacion.springboot.app.oauth.service.IUsuarioService;

import brave.Tracer;
import feign.FeignException;

@Component
public class AuthenticationSuccessErrorHandler implements AuthenticationEventPublisher {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private IUsuarioService usuarioService;

	@Autowired
	private Tracer tracer;

	@Override
	public void publishAuthenticationSuccess(Authentication authentication) {

		if (authentication.getDetails() instanceof WebAuthenticationDetails) {
			return;
		}

		Usuario usuario = usuarioService.findByUsername(authentication.getName());
		usuario.setIntentos(0);
		usuarioService.update(usuario, usuario.getId());

		UserDetails user = (UserDetails) authentication.getPrincipal();
		logger.info("Login success: {}", user.getUsername());
	}

	@Override
	public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {

		StringBuilder errors = new StringBuilder();

		try {
			logger.error("Login error: {}", exception.getMessage());
			errors.append("Error en el login para ").append(authentication.getName()).append(": ").append(exception.getMessage());

			Usuario usuario = usuarioService.findByUsername(authentication.getName());

			Integer intentos = usuario.getIntentos();
			if (intentos == null) {
				intentos = 0;
			}

			intentos++;
			usuario.setIntentos(intentos);
			errors.append("Intentos tras el login: ").append(intentos);

			if (intentos >= 3) {
				usuario.setEnabled("F");
				logger.error("El usuario {} deshabilitado por superar los intentos de login", usuario.getUsername());
				errors.append("El usuario ha sido deshabilitado por superar los intentos de login");
			}

			usuarioService.update(usuario, usuario.getId());
		} catch (FeignException e) {
			logger.error("El usuario no existe", e);
			errors.append("El usuario no existe");
		}

		tracer.currentSpan().tag("error.mensaje", errors.toString());
	}
}
