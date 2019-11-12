package com.passport.altaDeVisa.component;


import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.passport.altaDeVisa.config.ConfigProperties;
import com.passport.altaDeVisa.processor.VisaProcessor;
import com.passport.altaDeVisa.response.GeneralResponse;

@Component
public class BorrarVisa extends RouteBuilder {
	@Autowired
	private ConfigProperties config;
	
	@Override
	public void configure() throws Exception {
		from("direct:borrarVisa").process(new VisaProcessor()).
			doTry().
				to(config.getDatabase() + config.getVisaCollection() + "&operation=remove").
				//generar las respuestas
				process(new Processor() {
					@Override
					public void process(Exchange exchange) throws Exception {
						
						GeneralResponse<String> response = new GeneralResponse<String>();
						//si los datos son correctos
						response.setCode("200");
						response.setMessage("Datos borrados");					
						exchange.getIn().setBody(response);
					}
				}).
				doCatch(Exception.class).
					log(LoggingLevel.ERROR, "${exception.message} ${exception.stacktrace}").				
				end().
			end().			
		log("Visa Eliminada");			
	}	
}