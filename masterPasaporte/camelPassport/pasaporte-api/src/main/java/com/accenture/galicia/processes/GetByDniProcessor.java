package com.accenture.galicia.processes;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class GetByDniProcessor implements Processor {
	
	
	@Override
	public void process(Exchange exchange) throws Exception {
		DBObject filter = new BasicDBObject();
		System.out.println("Header Persona: " + exchange.getIn().getHeader("document"));
		filter.put("person.document", exchange.getIn().getHeader("document"));
		System.out.println("Filtro: " + filter);
		exchange.getIn().setBody(filter);

		System.out.println("Something: " + filter.get("document"));
		System.out.println("GetByDniProcess - Ultimo body: "+ exchange.getIn().getBody(DBObject.class));
	}

}