package com.passport.camel.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mongodb.BasicDBObject;
import com.passport.camel.model.Persona;
import com.passport.camel.util.ObjectMapper;

@Component
public class DBObjectToPersonProcessor implements Processor {

	@Autowired
	ObjectMapper mapper;
	
	@Override
	public void process(Exchange exchange) throws Exception {
		BasicDBObject obj = exchange.getIn().getBody(BasicDBObject.class);
		Persona p = mapper.convertValue(obj, Persona.class);
		exchange.getIn().setBody(p);
	}
	
}
