package com.accenture.galicia.routes.rest;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

import com.accenture.galicia.pojos.Person;
import com.accenture.galicia.utils.DirectRoutesShortcuts;

@Component
public class PostRest extends RouteBuilder {
	@Override
	public void configure() throws Exception {
		restConfiguration().component("restlet").port(8081).bindingMode(RestBindingMode.json)
				.dataFormatProperty("prettyPrint", "true").apiContextPath("/swagger").apiContextRouteId("swagger")
				.apiProperty("api.title", "User API").apiProperty("api.version", "1.2.3").apiProperty("cors", "true")
				.apiProperty("host", ""). // by default 0.0.0.0
				apiProperty("port", "8081");
		
		rest("/pasaporte")
		//El post sera llamado en http://localhost:8081/pasaporte/crear
		.post("/crear").consumes(DirectRoutesShortcuts.JSON).produces(DirectRoutesShortcuts.JSON).type(Person.class).to(DirectRoutesShortcuts.GET_ID); 
		
		
		
	}

		

}
