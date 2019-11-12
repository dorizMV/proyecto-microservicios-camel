package com.passport.camel.routes;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.passport.camel.aggregator.CreationStrategy;
import com.passport.camel.config.ConfigProperties;
import com.passport.camel.config.DirectRoutes;
import com.passport.camel.processor.BodyToHeaderProcessor;
import com.passport.camel.processor.CreateProcessor;
import com.passport.camel.processor.CreateProcessorError;
import com.passport.camel.processor.GetByIdAndCountryProcessor;
import com.passport.camel.processor.PersonExistsProcessor;

@Component
public class RouteCreateList extends RouteBuilder {

	@Autowired
	private ConfigProperties config;
	
	@Autowired
	ApplicationContext appContext;
	
	@Override
	public void configure() throws Exception {
		from(DirectRoutes.CREAR).
			process(new Processor() {
				
				@Override
				public void process(Exchange exchange) throws Exception {
					exchange.getContext().setAllowUseOriginalMessage(true);
				}
			}).
			split(body(), new CreationStrategy()).parallelProcessing().
				doTry().
					//process(new ValidatePersonProcessor()).
					process(new BodyToHeaderProcessor()).
					process(new GetByIdAndCountryProcessor()).
					to(config.getDatabase() + config.getPersonaCollection() + "&operation=findOneByQuery").
					process(new PersonExistsProcessor()).
					to(config.getDatabase() + config.getPersonaCollection() + "&operation=insert").
					process(appContext.getBean(CreateProcessor.class)).
				doCatch(Exception.class).
					log(LoggingLevel.ERROR, "${exception.message} ${exception.stacktrace}").
					process(appContext.getBean(CreateProcessorError.class)).
				end().
			end();
	}
	
}
