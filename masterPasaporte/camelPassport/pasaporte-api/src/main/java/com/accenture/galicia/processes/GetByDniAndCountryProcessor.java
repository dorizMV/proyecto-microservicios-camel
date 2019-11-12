package com.accenture.galicia.processes;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@Component
public class GetByDniAndCountryProcessor implements Processor{
	
	
	private static final Logger log = LoggerFactory.getLogger(GetByDniAndCountryProcessor.class);

	
	@Override
	public void process(Exchange exchange) throws Exception {
		DBObject filter = new BasicDBObject();
		
		log.info("headers que va a ser filtros para la busqueda en la BD: " + exchange.getIn().getHeader("document") + " y " + exchange.getIn().getHeader("country") );
		
		filter.put("person.document", exchange.getIn().getHeader("document"));
		filter.put("person.country", exchange.getIn().getHeader("country"));
		
		exchange.getIn().setBody(filter);
		
		log.info(exchange.getIn().getBody().toString());

	}

}
