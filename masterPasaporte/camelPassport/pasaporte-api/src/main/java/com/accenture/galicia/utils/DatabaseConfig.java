package com.accenture.galicia.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DatabaseConfig {
	
	@Value("${mongo.url}")
	private String dbHost;
	
	@Value("${mongo.port}")
	private Integer dbPort;
	
	@Value("${mongo.database}")
	private String database;
	
	@Value("${mongo.collection.passport}")
	private String passportCollection;

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

	public String getPassportCollection() {
		return passportCollection;
	}

	public void setPassportCollection(String passportCollection) {
		this.passportCollection = passportCollection;
	}
	
	
}
