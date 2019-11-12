package com.passport.altaDeVisa.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.passport.altaDeVisa.entity.Visa;
import com.passport.altaDeVisa.response.GeneralResponse;


public class CrearVisaProcessor implements Processor {

	@Override
	  public void process(Exchange exchange) throws Exception {
		 Object auxObj= exchange.getIn().getBody();
		GeneralResponse<String> response = (GeneralResponse<String>) auxObj;		 					 
	      
	      Visa visaAux= (Visa) exchange.getIn().getHeader("impDatos");					      
	      exchange.getIn().setBody(visaAux);
	      
	      if(!response.getCode().equals("404") ){
	    	response.setCode("406");
	    	response.setMessage("Visa ya existe!");	
	    	exchange.getIn().setBody(response);
	    	
	    	throw new Exception("Error");
	      }					      
		}					
	}
