package com.passport.altaDeVisa.component;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.passport.altaDeVisa.processor.GenericErrorProcessor;
import com.passport.altaDeVisa.processor.VerificarListaNegraProcessor;
import com.passport.altaDeVisa.response.GeneralResponse;

@Component
public class FindListaNegraComponent extends RouteBuilder {
	
	@Value("${servicio.listaNegra}")
	private String urlListaNegra;
	
	@Override
	public void configure() throws Exception {
		from("direct:blackList").
			choice().
			when(simple("${header.document} == null or ${header.country} == null")).
				process(new GenericErrorProcessor()).
			otherwise().
				log(LoggingLevel.INFO, "Buscar | Validar Antecendentes | docu -> ${header.document} cou-> ${header.country}").				
				doTry().
					setHeader(Exchange.HTTP_METHOD, constant("GET")).
					setHeader(Exchange.HTTP_URI, constant("http://" + urlListaNegra)).
					setHeader(Exchange.HTTP_QUERY, simple("document=${headers.document}&country=${headers.country}")).
					to("http://query").
						unmarshal().json(JsonLibrary.Jackson, GeneralResponse.class).
							process(new VerificarListaNegraProcessor()).
					doCatch(Exception.class).
						log(LoggingLevel.ERROR, "${exception.message} ${exception.stacktrace}").
							process(new VerificarListaNegraProcessor()).
					end().
				end().
		log("busco los antecedentes");		
	}
}
