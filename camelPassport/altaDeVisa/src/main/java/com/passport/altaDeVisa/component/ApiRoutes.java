package com.passport.altaDeVisa.component;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiRoutes extends RouteBuilder {
	
    @Override
    public void configure() {         
		restConfiguration().
		component("restlet").
		//restlet - servlet
		port("8084").
		apiContextPath("/swagger").
		apiProperty("api.title", "Alta de Visa").
		apiProperty("api.description", "CRUD de alta de visa con todas las validaciones").
        enableCORS(true).
        corsAllowCredentials(true).
		bindingMode(RestBindingMode.json).
		dataFormatProperty("prettyPrint", "true");
    }
      
}
