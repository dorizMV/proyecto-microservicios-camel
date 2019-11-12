package com.passport.altaDeVisa.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.passport.altaDeVisa.request.GeneralRequest;

public class OrquestadorPaisProcessor implements Processor {
	@Override
	public void process(Exchange exchange) throws Exception {
		GeneralRequest gralRequest= exchange.getProperty("objGralRequest", GeneralRequest.class);

		String paisOrig= gralRequest.getCountryOrig();
		String paisDest= gralRequest.getCountryDest();

		exchange.getOut().setHeader("paisOrig", paisOrig);
		exchange.getOut().setHeader("paisDest", paisDest);
		exchange.getOut().setBody(null);
	}

}
