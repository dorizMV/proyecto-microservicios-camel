package com.passport.altaDeVisa.entity;

import java.io.Serializable;

public class Antecedente implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1353890283816052512L;
	
	private String descripcion;
	private Persona persona;	

	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public Persona getPersona() {
		return persona;
	}
	
	public void setPersona(Persona persona) {
		this.persona = persona;
	}
}
