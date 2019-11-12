package com.accenture.galicia.routes.rest;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import com.accenture.galicia.persistance.Passport;
import com.accenture.galicia.utils.DirectRoutesShortcuts;

@Component
public class GetByDocumentAndCountryRest extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		rest("/pasaporte").get("/getPersonaDniAndCountry?document={document}&country={country}")
		.produces(DirectRoutesShortcuts.JSON)
				.outType(Passport.class).
				// Ruta
				to(DirectRoutesShortcuts.GET_PASSPORT_DNI_AND_COUNTRY);
	}

}

