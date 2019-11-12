package com.passport.camel.processor;

import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.passport.camel.exception.PersonAlreadyExistsException;
import com.passport.camel.model.Persona;
import com.passport.camel.util.ObjectMapper;

@Component
public class PersonExistsProcessor implements Processor {

	@Autowired
	ObjectMapper mapper;
	
	@Override
	public void process(Exchange exchange) throws Exception {
		
		Object persona = exchange.getIn().getBody();
		if (persona == null) {
			Persona p;
			try {
				Message e = exchange.getUnitOfWork().getOriginalInMessage();
				if (e == null || e.getBody(Persona.class) == null) {
					p = exchange.getProperty("person", Persona.class);
				}
				p = e.getBody(Persona.class);
			} catch (Exception e) {
				p = exchange.getProperty("person", Persona.class);
			}
			Document d = new Document(mapper.convertValue(p, Map.class));
			d.put("birthday", p.getBirthday());
			exchange.getIn().setBody(d);
		} else {
			throw new PersonAlreadyExistsException();
		}
		
	}

}