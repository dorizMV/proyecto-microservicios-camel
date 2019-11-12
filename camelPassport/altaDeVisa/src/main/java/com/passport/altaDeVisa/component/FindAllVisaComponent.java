package com.passport.altaDeVisa.component;


import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.passport.altaDeVisa.config.ConfigProperties;

@Component
public class FindAllVisaComponent extends RouteBuilder {
	@Autowired
	private ConfigProperties config;
	
	@Override
	public void configure() throws Exception {
		from("direct:findAllVisa").
			doTry().
				to(config.getDatabase() + config.getVisaCollection() + "&operation=findAll").				
				doCatch(Exception.class).
					log(LoggingLevel.ERROR, "${exception.message} ${exception.stacktrace}").
				end().
			end().			
		log("intento buscar visa");			
	}	
}