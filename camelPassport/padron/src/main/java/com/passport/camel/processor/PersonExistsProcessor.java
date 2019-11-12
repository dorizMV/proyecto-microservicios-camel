package com.passport.camel.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;

import com.passport.camel.exception.PersonAlreadyExistsException;
import com.passport.camel.model.Persona;

public class PersonExistsProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		
		Object persona = exchange.getIn().getBody();
		if (persona == null) {
			try {
				Message e = exchange.getUnitOfWork().getOriginalInMessage();
				exchange.getIn().setBody(e.getBody(Persona.class));
			} catch (Exception e) {
				exchange.getIn().setBody(exchange.getProperty("person", Persona.class));
			}
		} else {
			throw new PersonAlreadyExistsException();
		}
		
	}

}