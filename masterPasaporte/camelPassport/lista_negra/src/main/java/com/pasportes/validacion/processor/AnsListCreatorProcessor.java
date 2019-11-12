package com.pasportes.validacion.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mongodb.BasicDBObject;
import com.pasportes.validacion.entities.Antecedente;
import com.pasportes.validacion.util.GeneralResponse;
import com.pasportes.validacion.util.ObjectMapper;

@Component
public class AnsListCreatorProcessor implements Processor {
	

	@Autowired
	private ObjectMapper mapper;
	
	
	@Override
	public void process(Exchange exchange) throws Exception {
	
		GeneralResponse gral = new GeneralResponse();
		BasicDBObject[] obj = exchange.getIn().getBody(BasicDBObject[].class);
		Antecedente[] ans = mapper.convertValue(obj, Antecedente[].class);
		
		List<String> list = new ArrayList();
		Map<String, Object> body = new HashMap<String, Object>();
		
			for (Antecedente an : ans) {
				list.add(an.getDescripcion());
			}
		body.put("persona", ans[0].getPersona());
		body.put("antecedentes",list);

			
		gral.setCode("1");
		gral.setMsg("exito");
		gral.setBody(body);
		exchange.getIn().setBody(gral);
		
	}

}
