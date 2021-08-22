package com.litoos11.apiBwl.configuration;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtil {
	
	private static final Log LOG = LogFactory.getLog(JwtUtil.class);
	
	static void addAuthentication(HttpServletResponse response, String username) {
		Date fecha = new Date(); 
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha); 
		calendar.add(Calendar.MINUTE, 2);
		//lo que más quieras sumar
		Date fechaSalida = calendar.getTime();
		LOG.info("FECHA: " + fechaSalida);
		
		String token = Jwts.builder()
				.setSubject(username)
				//Se asigna el tiempo de exiración de 1 minuto fecha.setMinutes(fecha.getMinutes() + sumarsesion
				.setExpiration(fechaSalida)
		
				//Hash para firmar la clave
				.signWith(SignatureAlgorithm.HS512, "3x4mpl3")
				.compact();
		
		//se agrega al encabezado el token
		response.addHeader("Authorization", "Bearer " + token);
	}
	
	static Authentication getAuthentication(HttpServletRequest request) {
		
		//obtenemos el token que viene en el encabezado de la petición
		String token = request.getHeader("Authorization");
		
		if(token != null) {
			String user = Jwts.parser()
					.setSigningKey("3x4mpl3")
					.parseClaimsJws(token.replace("Bearer", ""))
					.getBody()
					.getSubject();
			
			//
			return user != null 
					? new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList())
					: null;
		}
		return null;
	}

	
}
