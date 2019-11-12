package com.pasportes.validacion.processor;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.pasportes.validacion.entities.Antecedente;

public class UpdateProcessor implements Processor {
	
	

	@Override
	public void process(Exchange exchange) throws Exception {
		
		Antecedente an = exchange.getIn().getBody(Antecedente.class);
		Map<String,Object> filter = new HashMap<String, Object>();
		Map<String,Object> update = new HashMap<String, Object>();
		
		filter.put("persona.document", exchange.getIn().getHeader("document"));
		filter.put("descripcion", "Locura en via publica");
		
			if( an.getPersona() == null) {
				
				update.put("descripcion", an.getDescripcion());
				
			} else if(an.getPersona().getDocument() == null ||
					an.getPersona().getDocument() == exchange.getIn().getHeader("document")) {
				
				update.put("persona.name",an.getPersona().getName());
				update.put("persona.lastName",an.getPersona().getLastName());
				update.put("persona.country",an.getPersona().getCountry());
				update.put("persona.gender",an.getPersona().getGender());
				update.put("descripcion",an.getDescripcion());
				
			} else {
				
				update.put("persona", an.getPersona());
				
			}
		
		DBObject filterField = new BasicDBObject(filter);
		DBObject updateObj = new BasicDBObject("$set", new BasicDBObject(update));
		
		exchange.getIn().setBody(new DBObject[] {filterField,updateObj});
		
		
		
	}

}
