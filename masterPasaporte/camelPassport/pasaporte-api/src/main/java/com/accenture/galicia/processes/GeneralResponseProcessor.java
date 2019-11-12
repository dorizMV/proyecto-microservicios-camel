package com.accenture.galicia.processes;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.accenture.galicia.persistance.GeneralResponse;
import com.accenture.galicia.persistance.Passport;

public class GeneralResponseProcessor implements Processor{

	
	private static final Logger log = LoggerFactory.getLogger(GeneralResponseProcessor.class);

	@Override
	public void process(Exchange exchange) throws Exception {
		log.info("Exhange del GeneralPresponseProcessor: "+exchange.getIn().getBody());
		GeneralResponse<Passport> grl = new GeneralResponse<Passport>(
		"Status: " + exchange.getIn().getHeader("CamelHttpResponseCode"), null, exchange.getIn().getBody(Passport.class));
		//log.info("GeneralResponse: Pasaporte: " + exchange.getIn().getBody(Passport.class).toString());
		exchange.getIn().setBody(grl);
	}

}
