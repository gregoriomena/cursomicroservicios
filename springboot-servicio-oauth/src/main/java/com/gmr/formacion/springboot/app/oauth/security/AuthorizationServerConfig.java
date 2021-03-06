package com.gmr.formacion.springboot.app.oauth.security;

import java.util.Arrays;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import com.gmr.formacion.springboot.app.oauth.service.UsuarioService;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private InfoAdicionalToken infoAdicionalToken;

	@Autowired
	private Environment env;

	@Autowired
	private UsuarioService usuarioService;

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security
		.tokenKeyAccess("permitAll()") // Acceso a la autentificación inicial
		.checkTokenAccess("isAuthenticated()") // Acceso a la validación de un token
		;
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory().withClient(env.getProperty("config.security.oauth.client.id"))
		.secret(passwordEncoder.encode(env.getProperty("config.security.oauth.client.secret")))
		.scopes("read", "write")
		.authorizedGrantTypes("password", "refresh_token").accessTokenValiditySeconds(3600)
		.refreshTokenValiditySeconds(3600)
		// podría añadir todos los clientes que necesite
		// .and().withClient("movilapp").secret(passwordEncoder.encode("123456")).scopes("read",
		// "write")
		// .authorizedGrantTypes("password",
		// "refresh_token").accessTokenValiditySeconds(3600)
		// .refreshTokenValiditySeconds(3600)
		;
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(infoAdicionalToken, accesTokenConverter()));

		endpoints.authenticationManager(authenticationManager).tokenStore(tokenStore())
		.accessTokenConverter(accesTokenConverter())
		.tokenEnhancer(tokenEnhancerChain)
		.userDetailsService(usuarioService);

		super.configure(endpoints);
	}

	@Bean
	public JwtTokenStore tokenStore() {
		return new JwtTokenStore(accesTokenConverter());
	}

	@Bean
	public JwtAccessTokenConverter accesTokenConverter() {
		JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
		tokenConverter.setSigningKey(Base64.getEncoder().encodeToString(env.getProperty("config.security.oauth.jwt.key").getBytes()));
		return tokenConverter;
	}

}
