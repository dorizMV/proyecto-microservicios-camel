package com.accenture.galicia.routes;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.accenture.galicia.processes.GetByDniAndCountryProcessor;
import com.accenture.galicia.processes.PrintPersonDoesNotExistExceptionProcessor;
import com.accenture.galicia.utils.DatabaseConfig;
import com.accenture.galicia.utils.DirectRoutesShortcuts;

@Component
public class DeleteByDocumentAndCountryRoute extends RouteBuilder{
	
	@Autowired
	private DatabaseConfig config;	

	@Override
	public void configure() throws Exception {
	
	
		from(DirectRoutesShortcuts.DELETE_PASSPORT_DNI)
		.process(new GetByDniAndCountryProcessor())
		.to(config.getDatabase() + config.getPassportCollection() +"&operation=findOneByQuery")
		.log(LoggingLevel.INFO, "Eliminando el pasaporte ${body}")
		.choice()
		.when(simple("${body} == null"))

		.log(LoggingLevel.ERROR, "No existe en la BD un pasaporte con estos datos ${header.document}, ${header.country}")
		.process(new PrintPersonDoesNotExistExceptionProcessor()).otherwise()
		.to(config.getDatabase() + config.getPassportCollection() +"&operation=remove")
		.log(LoggingLevel.INFO, "Se elimino el pasaporte")
		.endChoice();
	}


}