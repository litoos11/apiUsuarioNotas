package com.litoos11.apiBwl.model;

import com.litoos11.apiBwl.component.Constantes.TIPO_MSG;

public class MensajeResponseModel {
	
	private String mensaje;
	private TIPO_MSG tipo;
	
	public MensajeResponseModel(String mensaje) {
		super();
		this.mensaje = mensaje;
	}	

	public MensajeResponseModel() {
		super();
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public TIPO_MSG getTipo() {
		return tipo;
	}

	public void setTipo(TIPO_MSG tipo) {
		this.tipo = tipo;
	}

}
