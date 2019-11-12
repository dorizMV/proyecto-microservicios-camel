package com.accenture.galicia.routes;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.accenture.galicia.processes.DBObjectToPassportProcessor;
import com.accenture.galicia.processes.GetByCreationDateProcessor;
import com.accenture.galicia.utils.DatabaseConfig;
import com.accenture.galicia.utils.DirectRoutesShortcuts;

@Component
public class GetByCreationDateRoute extends RouteBuilder{
		
	@Autowired
	ApplicationContext appContext;
	
	@Autowired
	private DatabaseConfig config;
	
	@Override
	public void configure() throws Exception {

		from(DirectRoutesShortcuts.GET_PASSPORT_DATE).
		log(LoggingLevel.INFO, "Filtro por cual se busca: ${header.creationDate}").
		process(new GetByCreationDateProcessor()).
		to(config.getDatabase() + config.getPassportCollection() + "&operation=findOneByQuery").
			process(appContext.getBean(DBObjectToPassportProcessor.class)).
			log(LoggingLevel.INFO, "Pasaporte: ${body}");

	}

}