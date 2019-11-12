package com.pasportes.validacion.entities;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="antecedentes")
public class Antecedente implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
