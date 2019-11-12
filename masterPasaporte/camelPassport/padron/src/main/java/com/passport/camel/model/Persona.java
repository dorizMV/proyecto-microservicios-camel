package com.passport.camel.model;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Persona implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3491238272104611439L;

	@NotEmpty
	private String document;

	@NotNull
	private String name;
	
	@NotNull
	private String lastName;
	
	@NotEmpty
	private String country;
	
	@NotNull
	private Date birthday;
	
	@NotNull
	private String gender;

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

}