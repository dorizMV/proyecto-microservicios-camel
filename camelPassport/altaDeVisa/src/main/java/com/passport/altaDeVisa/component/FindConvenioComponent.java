package com.passport.altaDeVisa.component;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.passport.altaDeVisa.config.ConfigProperties;
import com.passport.altaDeVisa.entity.Paises;
import com.passport.altaDeVisa.processor.GenericErrorProcessor;
import com.passport.altaDeVisa.processor.VerificarConvenioProcessor;
import com.passport.altaDeVisa.response.GeneralResponse;

@Component
public class FindConvenioComponent extends RouteBuilder {
	
	@Autowired
	private ConfigProperties config;
	
	@Override
	public void configure() throws Exception {
		
		from("direct:findConvenio").
			choice().
			when(simple("${header.paisOrig} == null or ${header.paisDest} == null")).
				process(new GenericErrorProcessor()).
			otherwise().
				process(new Processor() {
					@Override
					public void process(Exchange exchange) throws Exception {
						Document filter = new Document();
						filter.put("name", exchange.getIn().getHeader("paisOrig"));
						exchange.getIn().setBody(new Document[] {filter});
					}
				}).
			doTry().
				log(LoggingLevel.INFO, "Buscar | Validar Convenio | paisOrig -> ${header.paisOrig} paisDest-> ${header.paisDest}").
				to(config.getDatabase() + config.getPaisCollection() + "&operation=findOneByQuery").
					process(new Processor() {
						@Override
						public void process(Exchange exchange) throws Exception {
							
							if(exchange.getIn().getBody() == null) {
								GeneralResponse<String> error= new GeneralResponse<String>();
								
								error.setCode("404");
								error.setMessage("Los paises ingresados no existen!");
								
								exchange.getIn().setBody(error);
								
								throw new Exception("Sin Datos!");
							}
							
							String auxString= exchange.getIn().getBody().toString();
							
							Paises paisOrig= new ObjectMapper().readValue(auxString, Paises.class);
							
							Document filter = new Document();
							filter.put("name", exchange.getIn().getHeader("paisDest"));
							
							exchange.getIn().setHeader("auxPaisOrig", paisOrig);
							exchange.getIn().setBody(new Document[] {filter});
						}
					}).
				to(config.getDatabase() + config.getPaisCollection() + "&operation=findOneByQuery").
					process(new VerificarConvenioProcessor()).
				doCatch(Exception.class).
					log(LoggingLevel.ERROR, "${exception.message} ${exception.stacktrace}").
						process(new GenericErrorProcessor()).
				end().
			end().			
		log("intento buscar convenio");		
	}

}