package com.pasportes.validacion.processor;



import org.apache.camel.Exchange;
import org.apache.camel.Processor;



import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;


public class HeaderToQueryProcessor implements Processor{
	

	@Override
	public void process(Exchange exchange) throws Exception {
		
		String d = exchange.getIn().getHeader("document",String.class);
		String c = exchange.getIn().getHeader("country",String.class);
		DBObject filters = new BasicDBObject();
		filters.put("persona.document", d);
		filters.put("persona.country", c);		
		exchange.getIn().setBody(filters);	
	
	}
	
	

}
