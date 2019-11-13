package com.passport.altaDeVisa.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.passport.altaDeVisa.entity.Pasaporte;
import com.passport.altaDeVisa.request.GeneralRequest;

public class OrquestadorAntecedentesProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		Pasaporte passport= exchange.getIn().getBody(Pasaporte.class);
		GeneralRequest gralRequest= exchange.getProperty("objGralRequest", GeneralRequest.class);

		String document= gralRequest.getDocument();
		String country= gralRequest.getCountryOrig();

		exchange.getOut().setHeader("document", document);
		exchange.getOut().setHeader("country", country);
		exchange.setProperty("auxPassport", passport.getPassportNumber());
		exchange.getOut().setBody(null);
	}

}
