package com.pasportes.validacion.processor;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.pasportes.validacion.entities.Antecedente;

public class UpdateTwoStepProcessor implements Processor{
	

	@Override
	public void process(Exchange exchange) throws Exception {
		Antecedente an = exchange.getIn().getBody(Antecedente.class);
		
		Map<String,Object> update = new HashMap<String, Object>();
		update.put("persona.name",an.getPersona().getName());
		update.put("persona.lastName",an.getPersona().getLastName());
		update.put("persona.country",an.getPersona().getCountry());
		update.put("persona.gender",an.getPersona().getGender());
		update.put("descripcion",an.getDescripcion());
		
		DBObject filter = new BasicDBObject();
		filter.put("persona.document", exchange.getIn().getHeader("document"));
		DBObject updateObj = new BasicDBObject("$set", update);
		
		exchange.getIn().setBody(new DBObject[] {filter,updateObj});
	}
	
	
}
