package com.passport.camel.routes;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.passport.camel.config.ConfigProperties;
import com.passport.camel.config.DirectRoutes;
import com.passport.camel.processor.DBObjectToPersonProcessor;
import com.passport.camel.processor.GetByIdAndCountryProcessor;

@Component
public class RouteGetByDocAndCountry extends RouteBuilder {

	@Autowired
	private ConfigProperties config;
	
	@Autowired
	ApplicationContext appContext;
	
	@Override
	public void configure() throws Exception {
		from(DirectRoutes.GET_ID).process(new GetByIdAndCountryProcessor()).
			to(config.getDatabase() + config.getPersonaCollection() + "&operation=findOneByQuery").
			process(appContext.getBean(DBObjectToPersonProcessor.class)).
			log(LoggingLevel.INFO, "Respuesta: ${body}");
	}
	
}
