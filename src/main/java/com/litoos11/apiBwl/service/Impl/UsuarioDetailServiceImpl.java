package com.litoos11.apiBwl.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.litoos11.apiBwl.entity.UsuarioEntity;
import com.litoos11.apiBwl.service.IUsuarioService;

@Service("usuarioDetailServiceImpl")
public class UsuarioDetailServiceImpl implements UserDetailsService{
	
	@Autowired
	@Qualifier("usuarioServiceImpl")
	private IUsuarioService usuarioService;
	
	private static final Log LOG = LogFactory.getLog(UsuarioDetailServiceImpl.class);

	@Override
	public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
		
		UsuarioEntity usuario = usuarioService.findByCorreo(correo);
		
		if(usuario == null) {
			LOG.error("Error login: no existen el usuario con correo ' " + correo +"'");
			throw new UsernameNotFoundException("Usuario con correo " + correo  + " no existe en el sistema!");
		}
		
		return new User(usuario.getCorreo(), usuario.getContrasenia(), usuario.isActivo(), 
				usuario.isActivo(), usuario.isActivo(), usuario.isActivo(), buildGrante(usuario.getRol()));
	}
	
	public List<GrantedAuthority> buildGrante(byte rol){
		String[] roles = { "USUARIO", "ADMIN" };
		List<GrantedAuthority> auth = new ArrayList<>();
		
		for(int i = 0; i < rol; i++) {
			auth.add(new SimpleGrantedAuthority(roles[i]));
		}
		
		return auth;
	}

}
