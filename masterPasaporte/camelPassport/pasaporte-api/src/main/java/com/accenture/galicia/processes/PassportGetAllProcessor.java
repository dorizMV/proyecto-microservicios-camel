package com.accenture.galicia.processes;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.accenture.galicia.persistance.Passport;
import com.accenture.galicia.utils.ObjectMapper;
import com.mongodb.BasicDBObject;

@Component
public class PassportGetAllProcessor implements Processor {

	@Autowired
	ObjectMapper mapper;

	@Override
	public void process(Exchange exchange) throws Exception {

		BasicDBObject[] bdb = exchange.getIn().getBody(BasicDBObject[].class);
		Passport[] passport = mapper.convertValue(bdb, Passport[].class);

		for (Passport pass : passport) {
			System.out.println(pass);
		}
		exchange.getIn().setBody(passport);
	}
}
