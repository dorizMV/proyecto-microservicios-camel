package com.passport.altaDeVisa.component;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.stereotype.Component;

@Component
public class PaisRestRoute extends RouteBuilder {
	
	private static final String JSON = "application/json";

	@Override
	public void configure() throws Exception {
		rest("/pais").
		get("/verificarConvenio?paisOrig={paisOrig}&paisDest={paisDest}").
			description("Servicio para verificar el convenio entre paises").
				param().description("Pais de residencia").
					name("paisOrig").type(RestParamType.path).required(true).dataType("string").
				endParam().
				param().description("Pais destino").
					name("paisDest").type(RestParamType.path).required(true).dataType("string").
				endParam().			
			consumes(JSON).
			produces(JSON).
			to("direct:findConvenio");		
	}

}
