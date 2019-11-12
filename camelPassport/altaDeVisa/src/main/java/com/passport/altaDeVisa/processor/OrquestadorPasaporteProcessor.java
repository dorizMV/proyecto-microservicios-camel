package com.passport.altaDeVisa.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.passport.altaDeVisa.request.GeneralRequest;

public class OrquestadorPasaporteProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		GeneralRequest gralRequest= exchange.getIn().getBody(GeneralRequest.class);
		String document= gralRequest.getDocument();
		String country= gralRequest.getCountryOrig();

		exchange.getOut().setHeader("document", document);
		exchange.getOut().setHeader("country", country);
		exchange.setProperty("objGralRequest", gralRequest);
		exchange.getOut().setBody(null);
	}
}
