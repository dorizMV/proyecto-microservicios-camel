package com.passport.camel.routes;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mongodb.MongoDbConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.mongodb.BasicDBObject;
import com.mongodb.client.model.Sorts;
import com.passport.camel.config.ConfigProperties;
import com.passport.camel.config.DirectRoutes;
import com.passport.camel.model.Persona;
import com.passport.camel.processor.GetByIdAndCountryProcessor;
import com.passport.camel.util.ObjectMapper;
import com.sun.xml.bind.v2.schemagen.xmlschema.List;

@Component
public class RouteGetAll extends RouteBuilder {

	@Autowired
	private ConfigProperties config;
	
	@Autowired
	ApplicationContext appContext;
	
	@Autowired
	ObjectMapper mapper;
	
	@Override
	public void configure() throws Exception {
		from(DirectRoutes.GET_ALL).process(new GetByIdAndCountryProcessor()).
			setHeader(MongoDbConstants.SORT_BY).constant(Sorts.descending("document")).
			to(config.getDatabase() + config.getPersonaCollection() + "&operation=findAll").
			process(new Processor() {
				
				@Override
				public void process(Exchange exchange) throws Exception {
					BasicDBObject[] obj = exchange.getIn().getBody(BasicDBObject[].class);
					Persona[] p = mapper.convertValue(obj, Persona[].class);
					exchange.getCreated();
				}
			}).
			log(LoggingLevel.INFO, "Respuesta: ${body}");
	}
	
}
