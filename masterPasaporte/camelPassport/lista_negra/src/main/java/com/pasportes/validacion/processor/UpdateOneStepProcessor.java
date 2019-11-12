package com.pasportes.validacion.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.pasportes.validacion.entities.Antecedente;

public class UpdateOneStepProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		
		Antecedente an = exchange.getIn().getBody(Antecedente.class);
		
		DBObject filter = new BasicDBObject();
		filter.put("persona.document", exchange.getIn().getHeader("document"));
		DBObject updateObj = new BasicDBObject("$set", new BasicDBObject("descripcion", an.getDescripcion()));
		
		exchange.getIn().setBody(new DBObject[] {filter,updateObj});
	}

}
