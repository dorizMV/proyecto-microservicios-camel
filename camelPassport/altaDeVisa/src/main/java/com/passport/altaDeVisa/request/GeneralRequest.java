package com.passport.altaDeVisa.request;

public class GeneralRequest {
	
	private String document;
	
	private String countryOrig;
	
	private String countryDest;

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public String getCountryOrig() {
		return countryOrig;
	}

	public void setCountryOrig(String countryOrig) {
		this.countryOrig = countryOrig;
	}

	public String getCountryDest() {
		return countryDest;
	}

	public void setCountryDest(String countryDest) {
		this.countryDest = countryDest;
	}
}
