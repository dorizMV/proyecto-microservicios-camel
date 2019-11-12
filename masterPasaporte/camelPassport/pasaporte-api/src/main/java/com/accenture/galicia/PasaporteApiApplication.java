package com.accenture.galicia;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

@SpringBootApplication
public class PasaporteApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PasaporteApiApplication.class, args);
	}
	@Bean("activemq")
	public ActiveMQConnectionFactory mq() throws Exception {
		ActiveMQConnectionFactory mq = new ActiveMQConnectionFactory("tcp://localhost:61616");
		mq.setUserName("admin");
		mq.setPassword("admin");
		mq.setTrustAllPackages(true);
		return mq;
	}
	
	@Bean("mongoClientBean")
	public MongoClient mongoClient() {
		ServerAddress server = new ServerAddress("localhost", 27017);
		return new MongoClient(server);
	}
}
