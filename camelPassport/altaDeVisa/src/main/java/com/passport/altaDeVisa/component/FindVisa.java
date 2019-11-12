package com.passport.altaDeVisa.component;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.passport.altaDeVisa.config.ConfigProperties;
import com.passport.altaDeVisa.entity.Visa;

import com.passport.altaDeVisa.processor.VisaProcessor;
import com.passport.altaDeVisa.response.GeneralResponse;

@Component
public class FindVisa extends RouteBuilder {
	
	@Autowired
	private ConfigProperties config;

	@Override
	public void configure() throws Exception {
		from("direct:findVisa").process(new VisaProcessor()).
			doTry().			
				to(config.getDatabase() + config.getVisaCollection() + "&operation=findOneByQuery").
				process(new Processor() {
					@Override
					public void process(Exchange exchange) throws Exception {
					  Object auxObj= exchange.getIn().getBody();
				      GeneralResponse<String> response= new GeneralResponse<String>();
				      
				      if(auxObj == null) { 
				    	response.setCode("404");
						response.setMessage("Visa No encontrada!");
				      } else {
				    	  Visa auxVisa = new ObjectMapper().readValue(auxObj.toString(), Visa.class);
				    	  
				    	  if(!auxVisa.getStatus().equals("1") || auxVisa.getStatus().equals(null))  {
				    		  response.setCode("409");
				    		  response.setMessage("Estado de la visa en conflicto!");
				    		  response.setBody(auxVisa.getStatus());
				    	  } else {
				    		  response.setCode("201");
				    		  response.setMessage("Genial! Bienvenido al pais");
				    	  }				    	  
				      }				      
				      
				      exchange.getIn().setBody(response);
					}					
				}).
 				doCatch(Exception.class).
					log(LoggingLevel.ERROR, "${exception.message} ${exception.stacktrace}").
				end().
			end().			
		log("intento buscar visa");
	}
	
}