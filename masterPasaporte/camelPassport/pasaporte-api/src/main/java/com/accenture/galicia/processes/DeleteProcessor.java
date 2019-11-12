package com.accenture.galicia.processes;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class DeleteProcessor implements Processor{

	@Override
	public void process(Exchange exchange) throws Exception {
		DBObject toRemove = new BasicDBObject();
		toRemove.put("person.document", exchange.getIn().getHeader("document"));
		exchange.getIn().setBody(toRemove);		
	}

}
