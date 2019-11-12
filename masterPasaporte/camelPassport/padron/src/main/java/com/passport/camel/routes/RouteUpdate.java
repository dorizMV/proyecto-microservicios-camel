package com.passport.camel.routes;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.passport.camel.config.ConfigProperties;
import com.passport.camel.config.DirectRoutes;
import com.passport.camel.processor.UpdateProcessor;

@Component
public class RouteUpdate extends RouteBuilder {

	@Autowired
	private ConfigProperties config;
	
	@Autowired
	ApplicationContext appContext;
	
	@Override
	public void configure() throws Exception {
		from(DirectRoutes.UPDATE).process(appContext.getBean(UpdateProcessor.class)).
			to(config.getDatabase() + config.getPersonaCollection() + "&operation=update").
			log(LoggingLevel.INFO, "Respuesta: ${body}");
	}
	
}
