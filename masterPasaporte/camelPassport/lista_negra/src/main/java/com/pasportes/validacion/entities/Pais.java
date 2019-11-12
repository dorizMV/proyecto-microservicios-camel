package com.pasportes.validacion.entities;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="pais")
public class Pais {
	
	private String nombre;
	private List<?> validaciones;
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public List<?> getValidaciones() {
		return validaciones;
	}
	public void setValidaciones(List<?> validaciones) {
		this.validaciones = validaciones;
	}
			
}
