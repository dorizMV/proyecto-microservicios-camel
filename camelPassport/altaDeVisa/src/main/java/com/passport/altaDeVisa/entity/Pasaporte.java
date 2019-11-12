package com.passport.altaDeVisa.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Pasaporte implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 188614773304263362L;
	
	@JsonIgnore
	private Object _id;
	
	private Persona person;
	
	private String passportNumber;
	
	public Persona getPerson() {
		return person;
	}

	public void setPerson(Persona person) {
		this.person = person;
	}

	public String getPassportNumber() {
		return passportNumber;
	}

	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}
}
