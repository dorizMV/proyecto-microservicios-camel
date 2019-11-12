package com.pasportes.validacion;

import org.apache.camel.Main;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

import com.mongodb.MongoClient;
import com.pasportes.validacion.routes.ValidationRoutes;

@SpringBootApplication
public class ListaNegraApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(ListaNegraApplication.class, args);
		
	}

}
