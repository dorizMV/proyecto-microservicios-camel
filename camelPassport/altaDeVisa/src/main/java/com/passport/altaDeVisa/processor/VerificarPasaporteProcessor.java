package com.passport.altaDeVisa.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.passport.altaDeVisa.entity.Pasaporte;
import com.passport.altaDeVisa.response.GeneralResponse;

public class VerificarPasaporteProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		Object auxObj= exchange.getIn().getBody();
		GeneralResponse<String> response= new GeneralResponse<String>();
		
		if(auxObj == null) {
			response.setCode("404");
			response.setMessage("Debe tramitar el pasaporte!");
			
			exchange.getOut().setHeader("ERROR", "No existe!");
			exchange.getOut().setBody(response);
		} else {
			Pasaporte passport= (Pasaporte) auxObj;
			
			exchange.getIn().setBody(passport);			
		}
		
	}

}
