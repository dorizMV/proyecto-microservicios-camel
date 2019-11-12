package com.accenture.galicia.routes;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.accenture.galicia.exception.PersonNotEqualException;
import com.accenture.galicia.processes.DBObjectToPassportProcessor;
import com.accenture.galicia.processes.GetByDniAndCountryProcessor;
import com.accenture.galicia.processes.PrintPersonDoesNotExistExceptionProcessor;
import com.accenture.galicia.processes.PrintStatusExceptionProcessor;
import com.accenture.galicia.utils.DatabaseConfig;
import com.accenture.galicia.utils.DirectRoutesShortcuts;

@Component
public class GetByDocumentAndCountryRoute extends RouteBuilder{
	@Autowired
	ApplicationContext appContext;
	
	@Autowired
	private DatabaseConfig config;
	
	@Override
	public void configure() throws Exception {
		from(DirectRoutesShortcuts.GET_PASSPORT_DNI_AND_COUNTRY)
		.choice()
		.when(simple("${header.CamelHttpResponseCode} == 204"))
		.log(LoggingLevel.ERROR, "No se encuentra en la BD un pasaporte con los datos: ${header.document}, ${header.country}")
		.process(new PrintPersonDoesNotExistExceptionProcessor())
		.otherwise()
			.doTry().
			process(new GetByDniAndCountryProcessor()).
			to(config.getDatabase() + config.getPassportCollection() + "&operation=findOneByQuery").
				process(appContext.getBean(DBObjectToPassportProcessor.class)).
					log(LoggingLevel.INFO, "Pasaporte: ${body}").
					doCatch(PersonNotEqualException.class).
					log(LoggingLevel.ERROR, "Las personas comparadas no son iguales")
				.process(new PrintStatusExceptionProcessor())
			.end()
	.endChoice();
	}

}
