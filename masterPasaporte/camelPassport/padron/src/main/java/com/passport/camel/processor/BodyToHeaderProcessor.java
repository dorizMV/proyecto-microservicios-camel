package com.passport.camel.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.passport.camel.model.Persona;

public class BodyToHeaderProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		Persona p = exchange.getIn().getBody(Persona.class);
		exchange.getIn().setHeader("document", p.getDocument());
		exchange.getIn().setHeader("country", p.getCountry());
		exchange.setProperty("person", p);
	}

}