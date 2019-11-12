package com.passport.altaDeVisa.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConfigProperties {

	@Value("${mongo.url}")
	private String dbHost;
	
	@Value("${mongo.port}")
	private Integer dbPort;
	
	@Value("${mongo.database}")
	private String database;
	
	@Value("${mongo.collection.persona}")
	private String personaCollection;
	
	@Value("${mongo.collection.listaNegra}")
	private String listaNegraCollection;
	
	@Value("${mongo.collection.visa}")
	private String visaCollection;
	
	@Value("${mongo.collection.pasaporte}")
	private String pasaporteCollection;
	
	@Value("${mongo.collection.paises}")
	private String paisCollection;

	public String getDbHost() {
		return dbHost;
	}

	public void setDbHost(String dbHost) {
		this.dbHost = dbHost;
	}

	public Integer getDbPort() {
		return dbPort;
	}

	public void setDbPort(Integer dbPort) {
		this.dbPort = dbPort;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getPersonaCollection() {
		return personaCollection;
	}

	public void setPersonaCollection(String personaCollection) {
		this.personaCollection = personaCollection;
	}

	public String getListaNegraCollection() {
		return listaNegraCollection;
	}

	public void setListaNegraCollection(String listaNegraCollection) {
		this.listaNegraCollection = listaNegraCollection;
	}

	public String getVisaCollection() {
		return visaCollection;
	}

	public void setVisaCollection(String visaCollection) {
		this.visaCollection = visaCollection;
	}

	public String getPasaporteCollection() {
		return pasaporteCollection;
	}

	public void setPasaporteCollection(String pasaporteCollection) {
		this.pasaporteCollection = pasaporteCollection;
	}

	public String getPaisCollection() {
		return paisCollection;
	}

	public void setPaisCollection(String paisCollection) {
		this.paisCollection = paisCollection;
	}
}
