package com.passport.camel.routes;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.mongodb.BasicDBObject;
import com.passport.camel.config.ConfigProperties;
import com.passport.camel.config.DirectRoutes;
import com.passport.camel.model.Persona;
import com.passport.camel.processor.BodyToHeaderProcessor;
import com.passport.camel.processor.CreateProcessorError;
import com.passport.camel.processor.GetByIdAndCountryProcessor;
import com.passport.camel.util.ObjectMapper;

@Component
public class RouteLlamadaHttp extends RouteBuilder {

	@Autowired
	private ConfigProperties config;
	
	@Autowired
	ApplicationContext appContext;
	
	@Autowired
	ObjectMapper mapper;
	
	@Override
	public void configure() throws Exception {
		from(DirectRoutes.GET_HTTP).
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
				process(new Processor() {
					
					@Override
					public void process(Exchange exchange) throws Exception {
						BasicDBObject obj = exchange.getIn().getBody(BasicDBObject.class);
						Persona p = mapper.convertValue(obj, Persona.class);
						exchange.getIn().setHeader("document", p.getDocument());
						exchange.getIn().setHeader("country", p.getCountry());
						exchange.getIn().setBody(null);
					}
				}).
				setHeader(Exchange.HTTP_METHOD,constant("GET")).
				setHeader(Exchange.HTTP_URI,constant("http://localhost:8080/personas/getPersona")).
				setHeader(Exchange.HTTP_QUERY,simple("document=${header.document}&country=${header.country}")).
				to("http://query").unmarshal().json(JsonLibrary.Jackson, Persona.class).
				process(new Processor() {
					@Override
					public void process(Exchange exchange) throws Exception {
						Persona p = exchange.getIn().getBody(Persona.class);
					}
				}).
			doCatch(Exception.class).
				log(LoggingLevel.ERROR, "${exception.message} ${exception.stacktrace}").
				process(appContext.getBean(CreateProcessorError.class)).
			end();
	}
	
}
