package com.litoos11.apiBwl.service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import com.litoos11.apiBwl.entity.UsuarioEntity;

public interface IUsuarioService {
	
	public List<UsuarioEntity> findAll();
	
	public UsuarioEntity findByCorreo(String correo);
	
	public Optional<UsuarioEntity> findById(Long id);
	
	public UsuarioEntity saveUsuario(UsuarioEntity usuario);
	
	public void udateUsuario(Calendar fecha, Long id);

}
