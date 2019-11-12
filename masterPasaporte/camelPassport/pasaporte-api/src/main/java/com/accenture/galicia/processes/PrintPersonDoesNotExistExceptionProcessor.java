package com.accenture.galicia.processes;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.accenture.galicia.exception.PersonDoesNotExistException;
import com.accenture.galicia.persistance.GeneralResponse;

public class PrintPersonDoesNotExistExceptionProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		final Throwable ex = new  PersonDoesNotExistException();
		GeneralResponse<?> grl = new GeneralResponse<>();
		grl.setCode("Status: "+exchange.getIn().getHeader("CamelHttpResponseCode"));
		grl.setMessage(ex.getMessage());
		exchange.getOut().setBody(grl);
	}

}
