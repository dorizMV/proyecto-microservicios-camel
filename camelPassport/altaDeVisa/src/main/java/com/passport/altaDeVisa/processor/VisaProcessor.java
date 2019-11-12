package com.passport.altaDeVisa.processor;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.bson.Document;

public class VisaProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		Document filter = new Document();
		filter.put("passportNumber", exchange.getIn().getHeader("passport"));
		filter.put("country", exchange.getIn().getHeader("country"));
		exchange.getIn().setBody(new Document[] {filter});
	}
}
