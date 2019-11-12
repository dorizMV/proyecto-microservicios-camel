package com.accenture.galicia.processes;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.accenture.galicia.exception.PersonDoesNotExistException;
import com.accenture.galicia.pojos.Person;

public class PersonComparingProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws PersonDoesNotExistException{
		if (!exchange.getProperty("personOld", Person.class).equals(exchange.getIn().getBody()))
			throw new PersonDoesNotExistException();
	}

}
