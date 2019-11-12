package com.passport.altaDeVisa.component;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.stereotype.Component;

import com.passport.altaDeVisa.entity.Visa;

@Component
public class VisaRestRoute extends RouteBuilder {
	
	private static final String JSON = "application/json";

	@Override
	public void configure() throws Exception {
		rest("/visa").
		post("/crear").
			description("Servicio para crear una visa").
				param().description("Datos de la visa").
					name("body").type(RestParamType.body).required(true).
				endParam().
			consumes(JSON).
			produces(JSON).
			type(Visa.class).
			to("direct:crearVisa").		     
	     get("/findVisa?country={country}&passport={passport}").
			description("Servicio para validar la visa").
				param().description("Pais de residencia").
					name("country").type(RestParamType.path).required(true).dataType("string").
				endParam().
				param().description("Numero de pasaporte de la persona").
					name("passport").type(RestParamType.path).required(true).dataType("string").
				endParam().
	        consumes(JSON).
	        produces(JSON).
	        to("direct:findVisa").
	     get("/findAll").
	     	description("Servicio para traer todas las visas").
	        consumes(JSON).
			produces(JSON).
			to("direct:findAllVisa").	     
		post("/actualizar?country={country}&passport={passport}").
			description("Servicio para actualizar la visa").
				param().description("Pais de residencia").
					name("country").type(RestParamType.path).required(true).dataType("string").
				endParam().
				param().description("Numero de pasaporte de la persona").
					name("passport").type(RestParamType.path).required(true).dataType("string").
				endParam().
				param().description("Datos de la visa").
					name("body").type(RestParamType.body).required(true).
				endParam().
			consumes(JSON).
			produces(JSON).
			type(Visa.class).
			to("direct:actualizarVisa").				
		delete("/borrar?country={country}&passport={passport}").
			description("Servicio para eliminar la visa").
				param().description("Pais de residencia").
					name("country").type(RestParamType.path).required(true).dataType("string").
				endParam().
				param().description("Numero de pasaporte de la persona").
					name("passport").type(RestParamType.path).required(true).dataType("string").
				endParam().
			consumes(JSON).
			produces(JSON).
			to("direct:borrarVisa");		
	}

}
