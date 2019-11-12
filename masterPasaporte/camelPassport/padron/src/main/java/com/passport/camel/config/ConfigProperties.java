package com.passport.camel.config;

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
	
}
