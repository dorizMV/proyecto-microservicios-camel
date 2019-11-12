package com.passport.altaDeVisa.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.passport.altaDeVisa.response.GeneralResponse;

public class GenericErrorProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		
		if(exchange.getIn().getBody() == null) {	
			GeneralResponse<String> response= new GeneralResponse<String>();
			
			response.setCode("500");
			response.setMessage("Ups! La accion que deseas realizar no se puede realizar, vuelve a intentarlo");
			
			exchange.getOut().getHeader("ERROR", "500");
			exchange.getOut().setBody(response);
		}
	}

}
