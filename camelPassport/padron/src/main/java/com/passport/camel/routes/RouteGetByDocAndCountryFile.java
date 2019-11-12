	package com.passport.camel.routes;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.passport.camel.config.ConfigProperties;
import com.passport.camel.config.DirectRoutes;
import com.passport.camel.model.Persona;
import com.passport.camel.processor.DBObjectToPersonProcessor;
import com.passport.camel.processor.GetByIdAndCountryProcessor;

@Component
public class RouteGetByDocAndCountryFile extends RouteBuilder {

	@Autowired
	private ConfigProperties config;
	
	@Autowired
	ApplicationContext appContext;
	
	@Override
	public void configure() throws Exception {
		from(DirectRoutes.GET_ID_FILE).process(new GetByIdAndCountryProcessor()).
			to(config.getDatabase() + config.getPersonaCollection() + "&operation=findOneByQuery").
			process(appContext.getBean(DBObjectToPersonProcessor.class)).
			marshal().json(JsonLibrary.Jackson, Persona.class).
			to("file:personaDir?fileName=unArchivo.txt").
			process(new Processor() {
				
				@Override
				public void process(Exchange exchange) throws Exception {
					exchange.getCreated();
				}
			}).
			log(LoggingLevel.INFO, "Respuesta: ${body}");
	}
	
}
