package com.accenture.galicia.persistance;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.accenture.galicia.pojos.Person;

public class Passport {
	@NotNull
	private Person person;

	@NotNull
	private Date creationDate;
	
	private String passportNumber;
	
	public String getPassportNumber() {
		return passportNumber;
	}

	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Date getFechaCreacion() {
		return creationDate;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.creationDate = fechaCreacion;
	}

	@Override
	public String toString() {
		return "Passport: \n" + person + ",\nCreation date: " + creationDate + ",\nPassport number: " + passportNumber;
	}

	

}
