package com.passport.altaDeVisa.component;

import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.passport.altaDeVisa.config.ConfigProperties;
import com.passport.altaDeVisa.processor.CrearVisaProcessor;
import com.passport.altaDeVisa.processor.DateVisa;
import com.passport.altaDeVisa.processor.EncontrarPas_CounProcessor;
import com.passport.altaDeVisa.response.GeneralResponse;

@Component
public class AltaDeVisaComponent extends RouteBuilder {
	
	@Autowired
	private ConfigProperties config;
	
	@Value("${visa.vencimiento}")
	private int visaVencimiento;

	@Override
	public void configure() throws Exception {
		from("direct:crearVisa").process(new EncontrarPas_CounProcessor()).
			doTry().
		 		to("direct:findVisa").process(new CrearVisaProcessor()).process(new DateVisa(visaVencimiento)).
		 		marshal().
		 		json(JsonLibrary.Jackson, Document.class).to(ExchangePattern.InOnly, "activemq:visaQueue").				
			//generar las respuestas
				process(new Processor() {
					@Override
					public void process(Exchange exchange) throws Exception {
						Object auxObj = exchange.getIn().getBody();
						GeneralResponse<String> response = new GeneralResponse<String>();

						if (auxObj == null) {
							// si los datos son correctos
							response.setCode("201");
							response.setMessage("Persona ya creada");
							exchange.getIn().setBody(response);
						} else {
							// si los datos son correctos
							response.setCode("201");
							response.setMessage("Tramite en proceso");
							exchange.getIn().setBody(response);
						}
					}
				}).				
				doCatch(Exception.class).
					log(LoggingLevel.ERROR, "${exception.message} ${exception.stacktrace}").				
		         end().
		    end().	
		log("Felicidades!");
		
		from("activemq:visaQueue").unmarshal().json(JsonLibrary.Jackson, Document.class).doTry()
		.to(config.getDatabase() + config.getVisaCollection() + "&operation=insert");
	}

}