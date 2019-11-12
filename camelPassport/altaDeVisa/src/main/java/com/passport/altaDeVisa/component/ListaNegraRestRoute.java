package com.passport.altaDeVisa.component;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.stereotype.Component;

@Component
public class ListaNegraRestRoute extends RouteBuilder {
	
	private static final String JSON = "application/json";

	@Override
	public void configure() throws Exception {
		rest("/listaNegra").
		get("/verificar?document={document}&country={country}").
			description("Servicio para validar los antecedentes de una persona").
				param().description("Numero de documento de la persona").
					name("document").type(RestParamType.path).required(true).dataType("string").
				endParam().
				param().description("Pais de residencia").
					name("country").type(RestParamType.path).required(true).dataType("string").
				endParam().			
			consumes(JSON).
			produces(JSON).
			to("direct:blackList");
	}

}
