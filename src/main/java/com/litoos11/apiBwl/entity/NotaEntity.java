package com.litoos11.apiBwl.entity;

import java.io.Serializable;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "notas")
public class NotaEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column()
	@NotEmpty
	private String titulo;

	@Column()
	private String contenido;

	@Column(name = "fecha_alta")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar fechaAlta;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference
	//@JsonManagedReference
	private UsuarioEntity usuario;

	public NotaEntity() {
		super();
	}

	public NotaEntity(Long id, @NotEmpty String titulo, String contenido, Calendar fechaAlta, UsuarioEntity usuario) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.contenido = contenido;
		this.fechaAlta = fechaAlta;
		this.usuario = usuario;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public Calendar getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Calendar fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public UsuarioEntity getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioEntity usuario) {
		this.usuario = usuario;
	}

	@PrePersist
	public void prePersist() {
		Calendar calendarDate = Calendar.getInstance(TimeZone.getTimeZone(ZoneId.systemDefault()));
		calendarDate.setTime(new Date());
		fechaAlta = calendarDate;
	}

}
