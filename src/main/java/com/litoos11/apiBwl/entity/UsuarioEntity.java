package com.litoos11.apiBwl.entity;

import java.io.Serializable;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "usuarios")
public class UsuarioEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 60)
	@NotEmpty
	private String nombre;

	@Column(length = 150)
	private String apellidos;

	@Column(length = 512, unique = true)
	@NotEmpty
	@Email
	private String correo;

	@Column(length = 1024)
	@NotEmpty
	private String contrasenia;

	@Column(name = "activo", columnDefinition = "boolean default true")
	private boolean activo;

	@Column(name = "fecha_alta")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar fechaAlta;

	@Column(name = "ultimo_login")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar ultimoLogin;

	@Column(name = "rol", columnDefinition = "SMALLINT(1) default 0")
	private byte rol;
	
	@OneToMany(mappedBy = "usuario" ,fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonManagedReference
	//@JsonIgnoreProperties("usuario")
	//@JsonBackReference
	private List<NotaEntity> notas;

	@Override
	public String toString() {
		return nombre + " " + apellidos;
	}

	public UsuarioEntity() {
		super();
		this.notas = new ArrayList<NotaEntity>();
	}
	
	public UsuarioEntity(Long id, @NotEmpty String nombre, String apellidos, @NotEmpty @Email String correo,
			@NotEmpty String contrasenia, boolean activo, Calendar fechaAlta, Calendar ultimoLogin, byte rol,
			List<NotaEntity> notas) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.correo = correo;
		this.contrasenia = contrasenia;
		this.activo = activo;
		this.fechaAlta = fechaAlta;
		this.ultimoLogin = ultimoLogin;
		this.rol = rol;
		this.notas = notas;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public Calendar getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Calendar fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Calendar getUltimoLogin() {
		return ultimoLogin;
	}

	public void setUltimoLogin(Calendar ultimoLogin) {
		this.ultimoLogin = ultimoLogin;
	}

	public byte getRol() {
		return rol;
	}

	public void setRol(byte rol) {
		this.rol = rol;
	}
	
	public List<NotaEntity> getNotas() {
		return notas;
	}

	public void setNotas(List<NotaEntity> notas) {
		this.notas = notas;
	}

	@PrePersist
	public void prePersist() {
		Calendar calendarDate = Calendar.getInstance(
				  TimeZone.getTimeZone(ZoneId.systemDefault()));
				calendarDate.setTime(new Date());
		fechaAlta = calendarDate;				
	}

}
