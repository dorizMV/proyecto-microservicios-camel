package com.passport.camel.routes;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mongodb.MongoDbConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.passport.camel.config.ConfigProperties;
import com.passport.camel.config.DirectRoutes;
import com.passport.camel.processor.GetByIdAndCountryProcessor;

@Component
public class RouteGetByDocAndCountry extends RouteBuilder {

	@Autowired
	private ConfigProperties config;
	
	@Autowired
	ApplicationContext appContext;
	
	@Override
	public void configure() throws Exception {
		from(DirectRoutes.GET_ID).
		process(new GetByIdAndCountryProcessor()).
		setHeader(MongoDbConstants.FIELDS_FILTER, constant("{_id:0}")).
		to(config.getDatabase() + config.getPersonaCollection() + "&operation=findOneByQuery").
		log(LoggingLevel.INFO, "Respuesta: ${body}").
		end();
		
		from("direct:probando").process(new Processor() {
			
			@Override
			public void process(Exchange exchange) throws Exception {
				throw new Exception("PASO POR ACA");
				
			}
		});
	}
	
}
