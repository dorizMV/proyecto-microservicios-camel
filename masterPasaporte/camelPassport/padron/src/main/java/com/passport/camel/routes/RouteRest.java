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
			port(8084).
			bindingMode(RestBindingMode.json).
			dataFormatProperty("prettyPrint", "true").
			apiContextPath("/swagger").
			apiContextRouteId("swagger").
			apiProperty("api.title", "User API").
			apiProperty("api.version", "1.2.3").
            apiProperty("cors", "true").
            apiProperty("host", "").
            apiProperty("port", "8084");/*
			params.put("base.path", "http://localhost/8084");
	        params.put("api.title", "my api title");
	        params.put("api.description", "my api description");
	        params.put("api.termsOfServiceUrl", "termsOfServiceUrl");
	        params.put("api.license", "license");
	        params.put("api.licenseUrl", "licenseUrl");*/
		
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
			post("update").
				consumes(JSON).
				produces(JSON).
				type(Persona.class).
				outType(Persona.class).
				to(DirectRoutes.UPDATE).
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
				route().
					to(DirectRoutes.GET_ID).
					log(simple("Operacion: GetById - Persona - Documento: ${header.document} , País: ${header.country}").getText()).
				endRest().
			get("/getPersonaFile?document={document}&country={country}").
				produces(JSON).
				to(DirectRoutes.GET_ID_FILE).
			get("/getAll?country={country}").
				produces(JSON).
				outType(Persona.class).
				to(DirectRoutes.GET_ALL).
			get("index.html").
				produces("text/html").
				to("direct:swagger");
		
		from("direct:swagger").
			setBody().simple("resource:classpath:/public/swagger/index.html");
	}
	
}
