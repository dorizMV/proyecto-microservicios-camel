package com.passport.altaDeVisa;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.passport.altaDeVisa.config.ConfigProperties;

@SpringBootApplication
public class AltaDeVisaApplication {
	
	@Autowired
	private ConfigProperties config;

	public static void main(String[] args) {
		SpringApplication.run(AltaDeVisaApplication.class, args);
	}
	
	@Bean("mongoClientBean")
    public MongoClient mongoClient() {
        ServerAddress server = new ServerAddress(config.getDbHost(), config.getDbPort());
        return new MongoClient(server);
    }
	
	@Bean("activemq")
	public ActiveMQConnectionFactory mq() throws Exception {
		ActiveMQConnectionFactory mq = new ActiveMQConnectionFactory("tcp://localhost:61616");
		mq.setUserName("admin");
		mq.setPassword("admin");
		mq.setTrustAllPackages(true);
		return mq;
	}
}
