package com.litoos11.apiBwl.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.litoos11.apiBwl.model.UsuarioConfigurationModel;

public class LoginFilter extends AbstractAuthenticationProcessingFilter{

	public LoginFilter(String url, AuthenticationManager authManager) {
		super(new AntPathRequestMatcher(url));
		setAuthenticationManager(authManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		
		InputStream body = request.getInputStream();
		
		UsuarioConfigurationModel usuario = new ObjectMapper().readValue(body, UsuarioConfigurationModel.class);
		
		return getAuthenticationManager().authenticate(
				new UsernamePasswordAuthenticationToken(
					usuario.getCorreo(),
					usuario.getContrasenia(),
					Collections.emptyList()
				)
			);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		//Si la authenticación es exitosa agrega el token a la respuesta
		JwtUtil.addAuthentication(response, authResult.getName());
		
	}
	
	
	
	

}
