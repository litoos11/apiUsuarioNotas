package com.litoos11.apiBwl.service;

import java.util.List;
import java.util.Optional;

import com.litoos11.apiBwl.entity.NotaEntity;

public interface INotaService {
	
	public Optional<NotaEntity> findById(Long id);
	
	public List<NotaEntity> findAllByUsuarioId(Long usuarioId);
	
	public NotaEntity save(NotaEntity usuario);
	
	public void deleteById(Long id);
}
