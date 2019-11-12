package com.passport.altaDeVisa.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.passport.altaDeVisa.request.GeneralRequest;

public class VisaOrquestadorProcessor implements Processor {
	@Override
	public void process(Exchange exchange) throws Exception {
		GeneralRequest gralRequest= exchange.getProperty("objGralRequest", GeneralRequest.class);

		String country= gralRequest.getCountryOrig();
		String passport= exchange.getProperty("auxPassport", String.class);

		exchange.getOut().setHeader("country", country);
		exchange.getOut().setHeader("passport", passport);
		exchange.getOut().setBody(null);
	}
}





