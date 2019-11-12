package com.accenture.galicia.processes;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;

import com.accenture.galicia.utils.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class GetByCreationDateProcessor implements Processor{
	
	
	@Autowired
	ObjectMapper mapper;
	
	
	@Override
	public void process(Exchange exchange) throws Exception {
		DBObject filter = new BasicDBObject();
		System.out.println("Header Persona: " + exchange.getIn().getHeader("creationDate"));
		String date = (String) exchange.getIn().getHeader("creationDate");
		filter.put("creationDate", Long.parseLong(date));
		System.out.println("Filtro: " + filter);
		exchange.getIn().setBody(filter);

		System.out.println("Something: " + filter.get("creationDate"));
		System.out.println("GetByDniProcess - Ultimo body: "+ exchange.getIn().getBody(DBObject.class));
	}

}