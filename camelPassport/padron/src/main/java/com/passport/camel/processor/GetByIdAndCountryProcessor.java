package com.passport.camel.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class GetByIdAndCountryProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		DBObject filter = new BasicDBObject();
		filter.put("document", exchange.getIn().getHeader("document"));
		filter.put("country", exchange.getIn().getHeader("country"));
		exchange.getIn().setBody(new DBObject[] {filter});
	}

}