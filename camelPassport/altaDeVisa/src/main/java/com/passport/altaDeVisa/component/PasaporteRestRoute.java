package com.passport.altaDeVisa.component;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.stereotype.Component;

@Component
public class PasaporteRestRoute extends RouteBuilder {
	
	private static final String JSON = "application/json";

	@Override
	public void configure() throws Exception {
		rest("/pasaporte").
		get("/getByDocAndCountry?document={docu}&country={country}").
			description("Servicio para validar el pasaporte").
				param().description("Numero de documento de la persona").
					name("docu").type(RestParamType.path).required(true).dataType("string").
				endParam().
				param().description("Pais de residencia").
					name("country").type(RestParamType.path).required(true).dataType("string").
				endParam().
			consumes(JSON).
			produces(JSON).
			to("direct:findPasaporte");
	}

}
