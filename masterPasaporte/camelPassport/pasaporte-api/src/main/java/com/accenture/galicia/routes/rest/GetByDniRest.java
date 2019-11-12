package com.accenture.galicia.routes.rest;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.accenture.galicia.persistance.Passport;
import com.accenture.galicia.utils.DirectRoutesShortcuts;
import com.accenture.galicia.utils.ObjectMapper;

@Component
public class GetByDniRest extends RouteBuilder {

	@Autowired
	ObjectMapper mapper;

	@Override
	public void configure() throws Exception {
		rest("/pasaporte").get("/getPersonaDni?document={document}")
		.produces(DirectRoutesShortcuts.JSON)
				.outType(Passport.class).
				// Ruta
				to(DirectRoutesShortcuts.GET_PASSPORT_DNI);
	}

}

//"/getPersonaDni?document={document}&country={country}"