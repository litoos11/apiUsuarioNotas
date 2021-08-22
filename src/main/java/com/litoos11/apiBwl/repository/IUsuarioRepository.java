package com.litoos11.apiBwl.repository;

import java.io.Serializable;
import java.util.Calendar;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.litoos11.apiBwl.entity.UsuarioEntity;

@Repository("usuarioRepository")
public interface IUsuarioRepository extends JpaRepository<UsuarioEntity, Serializable>{	
	public UsuarioEntity findByCorreo(String correo);
	
	@Modifying
	@Query("UPDATE UsuarioEntity u SET u.ultimoLogin=?1 WHERE u.id=?2")
	public void setUltimoLoginFromUsuario(Calendar fecha, Long id);
}
