package com.accenture.galicia.routes;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.accenture.galicia.processes.GeneralMultipleResponseProcessor;
import com.accenture.galicia.processes.PassportGetAllProcessor;
import com.accenture.galicia.utils.DatabaseConfig;
import com.accenture.galicia.utils.DirectRoutesShortcuts;
@Component
public class GetAllRoute extends RouteBuilder{
	
	@Autowired
	ApplicationContext appContext;
	
	@Autowired
	PassportGetAllProcessor passportGetAllProcessor;
	
	@Autowired
	DatabaseConfig dbC;
	
	@Override
	public void configure() throws Exception {
		from(DirectRoutesShortcuts.GET_ALL_PASSPORTS)
		.to(dbC.getDatabase() + dbC.getPassportCollection() + "&operation=findAll")
		.log(LoggingLevel.INFO, "Pasaportes: ${body}")
		.process(passportGetAllProcessor)
		.process(new GeneralMultipleResponseProcessor());


	}

}
