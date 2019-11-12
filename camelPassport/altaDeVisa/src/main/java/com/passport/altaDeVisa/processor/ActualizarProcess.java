package com.passport.altaDeVisa.processor;
import java.util.HashMap;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.passport.altaDeVisa.entity.Visa;


public class ActualizarProcess implements Processor {
		@Override		
			public void process(Exchange exchange) throws Exception {
				//en el body tengo los valores actualizados
				Visa updatedValue= exchange.getIn().getBody(Visa.class);					
				DBObject filter = new BasicDBObject();
				
				// en el header los valores o filtros viejos
				filter.put("passportNumber", exchange.getIn().getHeader("passport"));
				filter.put("country", exchange.getIn().getHeader("country"));
				
				//HashMap me permite modificar varios campos juntos
				HashMap<String, String> auxMap= new HashMap<String, String>();
				
				if (updatedValue.getPassportNumber() != null) {
					auxMap.put("passportNumber", updatedValue.getPassportNumber());
				}				
				if (updatedValue.getCountry() != null){
				    auxMap.put("country", updatedValue.getCountry());
				}				
				if (updatedValue.getStatus() != null){
				    auxMap.put("status", updatedValue.getStatus());
				}				
				DBObject updateObj = new BasicDBObject("$set", new BasicDBObject(auxMap));					
				exchange.getIn().setBody(new DBObject[] {filter, updateObj});
		    			
		}}