package com.pasportes.validacion.entities;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="pasaportes")
public class Pasaporte {

	@Id
	private String id;
	private Persona Persona;
	private String fechaDeCreación;
	
	public Pasaporte() {
	}
	
	public Persona getPersona() {
		return Persona;
	}
	public void setPersona(Persona persona) {
		Persona = persona;
	}
	public String getFechaDeCreación() {
		return fechaDeCreación;
	}
	public void setFechaDeCreación(String fechaDeCreación) {
		this.fechaDeCreación = fechaDeCreación;
	}
}
