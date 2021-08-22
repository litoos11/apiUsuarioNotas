package com.litoos11.apiBwl.service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.litoos11.apiBwl.entity.NotaEntity;
import com.litoos11.apiBwl.repository.INotaRepository;
import com.litoos11.apiBwl.service.INotaService;

@Service("notaServiceImpl")
public class NotaServiceImpl implements INotaService {
	
	@Autowired
	@Qualifier("notaRepository")
	private INotaRepository notaRepository;

	@Override
	public Optional<NotaEntity> findById(Long id) {
		return notaRepository.findById(id);
	}

	@Override
	public List<NotaEntity> findAllByUsuarioId(Long usuarioId) {
		return notaRepository.findByIdAndUsuarioId(usuarioId);
	}

	@Override
	public NotaEntity save(NotaEntity usuario) {
		return notaRepository.save(usuario);
	}

	@Override
	public void deleteById(Long id) {
		notaRepository.deleteById(id);		
	}

}
