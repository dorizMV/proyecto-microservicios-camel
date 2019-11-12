package com.accenture.galicia.processes;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.accenture.galicia.exception.PersonAlreadyExistsException;
import com.accenture.galicia.persistance.GeneralResponse;

public class PrintPersonAlreadyExistException implements Processor{

	@Override
	public void process(Exchange exchange) throws Exception {
		final Throwable ex = new  PersonAlreadyExistsException();
		GeneralResponse<?> grl = new GeneralResponse<>();
		grl.setCode("Status: "+exchange.getIn().getHeader("CamelHttpResponseCode"));
		grl.setMessage(ex.getMessage());
		exchange.getOut().setBody(grl);
		
	}

}
