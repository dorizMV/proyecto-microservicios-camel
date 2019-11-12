package com.accenture.galicia.routes.rest;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import com.accenture.galicia.pojos.Person;
import com.accenture.galicia.utils.DirectRoutesShortcuts;

@Component
public class UpdateRest extends RouteBuilder {
	

	@Override
	public void configure() throws Exception {
		rest("/pasaporte")
				// El post sera llamado en http://localhost:8081/pasaporte/
				.put("/update?nropasaporte={nropasaporte}").consumes(DirectRoutesShortcuts.JSON).type(Person.class).
				to(DirectRoutesShortcuts.UPDATE);

		
	}

}
