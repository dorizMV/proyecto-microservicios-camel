package com.passport.altaDeVisa.component;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import com.passport.altaDeVisa.processor.OrquestadorPaisProcessor;
import com.passport.altaDeVisa.processor.OrquestadorPasaporteProcessor;
import com.passport.altaDeVisa.processor.VisaOrquestadorProcessor;
import com.passport.altaDeVisa.request.GeneralRequest;

@Component
public class OrquestadorRestRoute extends RouteBuilder {

	private static final String JSON = "application/json";

	@Override
	public void configure() throws Exception {

		rest("/orquestador").
			post().
				consumes(JSON).
				produces(JSON).
				type(GeneralRequest.class).
			to("direct:orquestarTodo");

		from("direct:orquestarTodo").		
		doTry().
		//enviarle los parametros en el header
		//to("direct:findPasaporte").
		//proceso que verifique si dio OK
			process(new OrquestadorPasaporteProcessor()).
				to("direct:findPasaporte").	
			process(new OrquestadorAntecedentesProcessor()).
				to("direct:blackList").
			process(new OrquestadorPaisProcessor()).
				to("direct:findConvenio").
			process(new VisaOrquestadorProcessor()).
				to("direct:findVisa").						
			doCatch(Exception.class).
				log(LoggingLevel.ERROR, "${exception.message} ${exception.stacktrace}").
			end().
		end();
	}

}
