package com.pasportes.validacion.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

@Configuration
public class MongoConfig {
	
	@Bean("mongoBean")
    public MongoClient mongoClient() {
        ServerAddress server = new ServerAddress("localhost", 27017);
        return new MongoClient(server);
    }
}
