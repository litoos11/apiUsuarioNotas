package com.litoos11.apiBwl.service.Impl;

import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.litoos11.apiBwl.entity.UsuarioEntity;
import com.litoos11.apiBwl.repository.IUsuarioRepository;
import com.litoos11.apiBwl.service.IUsuarioService;

@Service("usuarioServiceImpl")
public class UsuarioServiceImpl implements IUsuarioService{
	
	@Autowired
	@Qualifier("usuarioRepository")
	private IUsuarioRepository usuarioRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	private static final Log LOG = LogFactory.getLog(UsuarioServiceImpl.class);

	@Override
	public List<UsuarioEntity> findAll() {
		return usuarioRepository.findAll();
	}

	@Override
	@Transactional(readOnly = false)
	public UsuarioEntity findByCorreo(String correo) {
		UsuarioEntity usuario = usuarioRepository.findByCorreo(correo);
		
		LOG.info("Usuario ultimo login: " +  usuario.getUltimoLogin());
		if(usuario != null) {
			Calendar calendarDate = Calendar.getInstance(TimeZone.getTimeZone(ZoneId.systemDefault()));
			calendarDate.setTime(new Date());
			this.udateUsuario(calendarDate, usuario.getId());
		}
		
		return usuario;
	}
	
	public Optional<UsuarioEntity> findById(Long id) {
		return usuarioRepository.findById(id);
	}

	@Override
	public UsuarioEntity saveUsuario(UsuarioEntity usuario) {
		usuario.setContrasenia(passwordEncoder.encode(usuario.getContrasenia()));
		return usuarioRepository.save(usuario);		
	}

	@Override
	public void udateUsuario(Calendar fecha, Long id) {		
		usuarioRepository.setUltimoLoginFromUsuario(fecha, id);
	}
}
