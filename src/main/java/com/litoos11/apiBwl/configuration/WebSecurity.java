package com.litoos11.apiBwl.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.litoos11.apiBwl.service.Impl.UsuarioDetailServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter{
	
	@Autowired
	@Qualifier("usuarioDetailServiceImpl")
	private UsuarioDetailServiceImpl usuarioDetailService;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(usuarioDetailService).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
		.antMatchers("/login", "/usuario/registro").permitAll() //permitir ruta de login a cualquiera
		.anyRequest().authenticated() //cualquier otra ruta necesita autenticación
		.and()
		//peticiones de (login deben pasar por este filtro
		.addFilterBefore(new LoginFilter("/login", authenticationManager())
				, UsernamePasswordAuthenticationFilter.class)
		//Otras peticiones pasan por este otro filtro
		.addFilterBefore(new JwtFilter(), 
				UsernamePasswordAuthenticationFilter.class);
	}

}
