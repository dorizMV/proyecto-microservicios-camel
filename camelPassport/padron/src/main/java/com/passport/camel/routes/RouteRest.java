package com.passport.camel.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.passport.camel.config.ConfigProperties;
import com.passport.camel.config.DirectRoutes;
import com.passport.camel.model.Persona;
import com.passport.camel.response.GeneralResponse;
import com.passport.camel.util.ObjectMapper;

@Component
public class RouteRest extends RouteBuilder {

	private static final String JSON = "application/json";
	
	@Autowired
	ConfigProperties config;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Override
	public void configure() throws Exception {
		restConfiguration().
			component("restlet").
			port(8080).
			bindingMode(RestBindingMode.json).
			dataFormatProperty("prettyPrint", "true").
			apiContextPath("/swagger").
			apiContextRouteId("swagger").
			apiProperty("api.title", "User API").
			apiProperty("api.version", "1.2.3").
            apiProperty("cors", "true").
            apiProperty("host", "").
            apiProperty("port", "8081");
		
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		rest("/personas").
			post("/crear").
				consumes(JSON).
				produces(JSON).
				type(Persona[].class).
				outType(GeneralResponse[].class).
				to(DirectRoutes.CREAR).
			post("crearJms").
				consumes(JSON).
				produces(JSON).
				type(Persona.class).
				outType(Persona.class).
				to(DirectRoutes.CREAR_JMS).
			post("getHttp").
				consumes(JSON).
				produces(JSON).
				type(Persona.class).
				outType(Persona.class).
				to(DirectRoutes.GET_HTTP).
			get("/getPersona?document={document}&country={country}").
				//swagger
				param().
					name("document").type(RestParamType.path).required(true).dataType("string").
				endParam().
				param().
					name("country").type(RestParamType.path).required(true).dataType("string").
				endParam().
				produces(JSON).
				outType(Persona.class).
				//Ruta
				to(DirectRoutes.GET_ID)
			.get("/getPersonaFile?document={document}&country={country}").
				produces(JSON).
				to(DirectRoutes.GET_ID_FILE)
			;
	}
	
}
