package com.passport.altaDeVisa.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.passport.altaDeVisa.response.GeneralResponse;

public class VerificarListaNegraProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		Object auxObj= exchange.getIn().getBody();
		GeneralResponse<String> response= new GeneralResponse<String>();
		
		if(auxObj == null) {
			response.setCode("200");
			response.setMessage("Sin Antecedentes!");
			
			exchange.getOut().setHeader("OK", "OK");
			exchange.getOut().setBody(response);
		} else {			
			exchange.getIn().setBody(auxObj);
		}
		
	}

}
