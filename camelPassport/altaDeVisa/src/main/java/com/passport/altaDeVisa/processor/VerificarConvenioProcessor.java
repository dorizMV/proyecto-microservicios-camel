package com.passport.altaDeVisa.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.passport.altaDeVisa.entity.Paises;
import com.passport.altaDeVisa.response.GeneralResponse;

public class VerificarConvenioProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		GeneralResponse<String> response= new GeneralResponse<String>();
		
		if(exchange.getIn().getBody() == null) {
			
			response.setCode("404");
			response.setMessage("Los paises ingresados no existen!");
			
			exchange.getIn().setBody(response);
			
			throw new Exception("Sin Datos!");
		}
		
		Paises paisOrig= (Paises) exchange.getIn().getHeader("auxPaisOrig");
		String auxString= exchange.getIn().getBody().toString();
		Paises paisDest= new Paises();
		boolean bandera= false;
		
		paisDest= new ObjectMapper().readValue(auxString, Paises.class);
		
		for(int x= 0; x < paisOrig.getAgreement().size(); x++) {								
			for(int y= 0; y < paisDest.getAgreement().size(); y++) {
				
				if(paisOrig.getAgreement().get(x).equals(paisDest.getAgreement().get(y))) {
					bandera= true;
					break;
				}
			}
		}
		
		response.setCode("200");
		response.setMessage("Puede ingresar con DNI!");
		
		if(!bandera) {
			response.setCode("204");
			response.setMessage("Tramitar Visa!");
		}
		
		exchange.getIn().setBody(response);	
	}

}
