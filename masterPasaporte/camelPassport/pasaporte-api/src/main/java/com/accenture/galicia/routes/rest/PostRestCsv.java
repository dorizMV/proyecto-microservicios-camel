package com.accenture.galicia.routes.rest;

import org.apache.camel.builder.RouteBuilder;

import com.accenture.galicia.pojos.Person;
import com.accenture.galicia.utils.DirectRoutesShortcuts;

public class PostRestCsv extends RouteBuilder{

	@Override
	public void configure() throws Exception {

		rest("/pasaporte")
		//El post sera llamado en http://localhost:8081/pasaporte/crear
		.post("/crearCsv").consumes(DirectRoutesShortcuts.TEXT_CSV).produces(DirectRoutesShortcuts.JSON).type(Person.class).to(DirectRoutesShortcuts.GET_ID); 
				
	}

}
