package com.pasportes.validacion.config;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;

import org.springframework.context.annotation.Configuration;

@Configuration
public class CamelConfig extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		restConfiguration().
		component("restlet").
		port(8082).
		bindingMode(RestBindingMode.json).
		dataFormatProperty("prettyPrint", "true").
		apiContextPath("/swagger").
		apiContextRouteId("swagger").contextPath("/api").
		apiProperty("api.title", "ListaNegra API").
		apiProperty("api.version", "1.2.3").
	    apiProperty("cors", "true").
	    apiProperty("host", ""). //by default 0.0.0.0
	    apiProperty("port", "8081");
		
	}
}