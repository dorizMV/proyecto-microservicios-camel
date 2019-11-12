package com.accenture.galicia.processes;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.accenture.galicia.persistance.Passport;
import com.accenture.galicia.utils.ObjectMapper;
import com.mongodb.BasicDBObject;

@Component
public class DBObjectToPassportProcessor implements Processor {

	
	private static final Logger log = LoggerFactory.getLogger(DBObjectToPassportProcessor.class);

	@Autowired
	ObjectMapper mapper;

	@Override
	public void process(Exchange exchange) throws Exception {
		BasicDBObject obj = exchange.getIn().getBody(BasicDBObject.class);
		System.out.println(obj);
		Passport p = mapper.convertValue(obj, Passport.class);
		//System.out.println("Valor convertido: " + p);
		//System.out.println("Exchange de DBObjectToPersonProccesor: " + obj);
		exchange.getIn().setBody(p);
		log.error("Exhange DBO: " + exchange.getIn().getBody());
	}

}