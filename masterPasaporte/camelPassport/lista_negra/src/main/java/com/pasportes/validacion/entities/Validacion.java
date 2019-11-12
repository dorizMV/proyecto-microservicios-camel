package com.pasportes.validacion.entities;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="validaciones")
public class Validacion {


	private String pais;
	private String validacion;

	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	public String getValidacion() {
		return validacion;
	}
	public void setValidacion(String validacion) {
		this.validacion = validacion;
	}
	
}
