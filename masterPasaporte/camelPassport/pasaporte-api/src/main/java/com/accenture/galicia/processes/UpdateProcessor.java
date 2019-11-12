package com.accenture.galicia.processes;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.accenture.galicia.pojos.Person;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class UpdateProcessor implements Processor{

	
	private static final Logger log = LoggerFactory.getLogger(UpdateProcessor.class);

	
	@Override
	public void process(Exchange exchange) throws Exception {
		log.info("1er exchange del update: "+exchange.getIn().getBody(Person.class).toString());
		Person person = exchange.getIn().getBody(Person.class);
		
		Map<String, Object> updateMap = new HashMap<String, Object>();
		
		if(person.getName()!=null) {
			updateMap.put("person.name", person.getName());
		}
		if(person.getBirthday()!=null) {
			updateMap.put("person.birthday", person.getBirthday());
		}
		if(person.getLastName()!=null) {
			updateMap.put("person.lastName", person.getLastName());
		}
		if(person.getGender()!=null) {
			updateMap.put("person.gender", person.getGender());
		}
		if(person.getCountry()!=null) {
			updateMap.put("person.country", person.getCountry());
			updateMap.put("passportNumber", person.getCountry().substring(0,3) + exchange.getIn().getHeader("nropasaporte").toString().substring(3));
		}
		
				
		log.info("Map del update: " + updateMap.toString());
		
		DBObject filter = new BasicDBObject("passportNumber", exchange.getIn().getHeader("nropasaporte"));
		log.info("Filtro: " + filter);
		DBObject updateObj = new BasicDBObject ("$set",new BasicDBObject(updateMap));
		log.info("DBObject: " + updateObj);

		exchange.getIn().setBody(new DBObject[] {filter,updateObj});
		}

}
