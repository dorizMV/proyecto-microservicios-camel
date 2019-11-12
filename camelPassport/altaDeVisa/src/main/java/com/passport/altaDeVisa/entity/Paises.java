package com.passport.altaDeVisa.entity;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Paises implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7829737248428564850L;

	@JsonIgnore
	private Object _id;
	
	private String name;
	
	private List<String> agreement;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getAgreement() {
		return agreement;
	}

	public void setAgreement(List<String> agreement) {
		this.agreement = agreement;
	}
}
