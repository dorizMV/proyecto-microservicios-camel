package com.accenture.galicia.routes.rest;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import com.accenture.galicia.utils.DirectRoutesShortcuts;

@Component
public class GetAllRest extends RouteBuilder {
	

		// ***************Configuracion de rutas****************//
	@Override
	public void configure() throws Exception {
		rest("/pasaporte")
			// El post sera llamado en http://localhost:8081/pasaporte/getAll
			.get("/getAll")
			.produces(DirectRoutesShortcuts.JSON)
			.to(DirectRoutesShortcuts.GET_ALL_PASSPORTS);
		
		
	}

}
