package com.pasportes.validacion.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;

import com.mongodb.BasicDBObject;
import com.pasportes.validacion.entities.Antecedente;
import com.pasportes.validacion.util.ObjectMapper;

public class SimpleListProcessor implements Processor {

	@Autowired
	ObjectMapper mapper;
	
	@Override
	public void process(Exchange exchange) throws Exception {
		
		BasicDBObject[] obj = exchange.getIn().getBody(BasicDBObject[].class);
		Antecedente[] ans = mapper.convertValue(obj, Antecedente[].class);
		exchange.getIn().setBody(ans);
		
	}

}
