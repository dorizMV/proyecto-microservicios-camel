package com.pasportes.validacion.routes;

import java.io.InputStream;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pasportes.validacion.constants.Constants;
import com.pasportes.validacion.entities.Antecedente;
import com.pasportes.validacion.entities.Persona;
import com.pasportes.validacion.processor.AllowUseOriginalMessageProcessor;
import com.pasportes.validacion.processor.DocumentAndCountryProcess;



@Component
public class ConsumerRoutes extends RouteBuilder {
	
	@Override
	public void configure() throws Exception {
		from(Constants.HTTP_PERSONA).wireTap("direct:logBody")
		.log("Searching for person with this body: ${body}")
		
			.process(new AllowUseOriginalMessageProcessor())
			.process(new DocumentAndCountryProcess()).
			setHeader(Exchange.HTTP_METHOD,constant("GET")).
			setHeader(Exchange.HTTP_URI,constant("http://localhost:8080/personas/getPersona")).
			setHeader(Exchange.HTTP_QUERY,simple("document=${header.persona.document}&country=${header.persona.country}")).log("${headers}").
			to("http://query").
			unmarshal().json(JsonLibrary.Jackson, Persona.class)
		.end();
		
		
	}
	

}
