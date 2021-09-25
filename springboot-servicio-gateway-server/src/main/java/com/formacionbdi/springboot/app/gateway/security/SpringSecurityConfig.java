package com.formacionbdi.springboot.app.gateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
public class SpringSecurityConfig {

	@Bean
	public SecurityWebFilterChain configure(ServerHttpSecurity http) {
		return http.authorizeExchange()
				.anyExchange().authenticated() // Cualquier ruta debe estar autentificada
				.and().csrf().disable() // Esto sería para vistas-formularios y nosotros sólo trabajamos con API REST
				.build();
	}
}
