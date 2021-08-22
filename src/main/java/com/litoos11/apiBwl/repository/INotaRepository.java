package com.litoos11.apiBwl.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.litoos11.apiBwl.entity.NotaEntity;

@Repository("notaRepository")
public interface INotaRepository extends JpaRepository<NotaEntity, Serializable>{

	@Query("select n from NotaEntity n join fetch n.usuario u where u.id=?1")
	public List<NotaEntity> findByIdAndUsuarioId(Long usuarioId);

}
