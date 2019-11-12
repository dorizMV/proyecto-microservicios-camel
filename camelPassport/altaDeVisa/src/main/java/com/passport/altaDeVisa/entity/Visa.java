package com.passport.altaDeVisa.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Visa implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2720731561561541560L;



	@JsonIgnore
	private Object _id;
	
	private String country;
	
	private String passportNumber;
	
	private Object emissionDate;
	
	private Object expirationDate;
	
	private String status;

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPassportNumber() {
		return passportNumber;
	}

	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}

	public Object getEmissionDate() {
		return emissionDate;
	}

	public void setEmissionDate(Object emissionDate) {
		this.emissionDate = emissionDate;
	}

	public Object getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Object expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
