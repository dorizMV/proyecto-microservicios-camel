package com.accenture.galicia.routes.rest;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import com.accenture.galicia.utils.DirectRoutesShortcuts;

@Component
public class DeleteByDocumentAndCountryRest extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		rest("/pasaporte").delete("/deleteByDni?document={document}&country={country}").produces("application/json")
				.to(DirectRoutesShortcuts.DELETE_PASSPORT_DNI);

	}

}