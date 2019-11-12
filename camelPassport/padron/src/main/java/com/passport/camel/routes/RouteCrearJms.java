package com.passport.camel.routes;

import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.passport.camel.config.ConfigProperties;
import com.passport.camel.config.DirectRoutes;
import com.passport.camel.model.Persona;
import com.passport.camel.processor.BodyToHeaderProcessor;
import com.passport.camel.processor.CreateProcessor;
import com.passport.camel.processor.CreateProcessorError;
import com.passport.camel.processor.GetByIdAndCountryProcessor;
import com.passport.camel.processor.PersonExistsProcessor;
import com.passport.camel.util.ObjectMapper;

@Component
public class RouteCrearJms extends RouteBuilder {

	@Autowired
	private ConfigProperties config;
	
	@Autowired
	ApplicationContext appContext;
	
	@Autowired
	ObjectMapper mapper;
	
	@Override
	public void configure() throws Exception {
		from(DirectRoutes.CREAR_JMS).
			process(new Processor() {
				@Override
				public void process(Exchange exchange) throws Exception {
					exchange.getContext().setAllowUseOriginalMessage(true);
				}
			}).
			doTry().
				process(new BodyToHeaderProcessor()).
				process(new GetByIdAndCountryProcessor()).
				to(config.getDatabase() + config.getPersonaCollection() + "&operation=findOneByQuery").
				process(new PersonExistsProcessor()).
				marshal().json(JsonLibrary.Jackson, Persona.class).
				to(ExchangePattern.InOnly, "activemq:unacola").
			doCatch(Exception.class).
				log(LoggingLevel.ERROR, "${exception.message} ${exception.stacktrace}").
				process(appContext.getBean(CreateProcessorError.class)).
			end();
		JmsComponent c = new JmsComponent();
		c.setAcceptMessagesWhileStopping(true);
		
		from("activemq:unacola").unmarshal().json(JsonLibrary.Jackson, Persona.class).
		to(config.getDatabase() + config.getPersonaCollection() + "&operation=insert").
		process(appContext.getBean(CreateProcessor.class));
	}
	
}
