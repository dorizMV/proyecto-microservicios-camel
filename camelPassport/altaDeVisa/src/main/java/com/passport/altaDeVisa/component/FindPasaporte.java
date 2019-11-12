package com.passport.altaDeVisa.component;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.passport.altaDeVisa.entity.Pasaporte;
import com.passport.altaDeVisa.processor.GenericErrorProcessor;
import com.passport.altaDeVisa.processor.VerificarPasaporteProcessor;

@Component
public class FindPasaporte extends RouteBuilder {
	
	@Value("${servicio.pasaporte}")
	private String urlPasaporte;
	
	@Override
	public void configure() throws Exception {		
		from("direct:findPasaporte").
			choice().
			when(simple("${header.document} == null or ${header.country} == null")).
				process(new GenericErrorProcessor()).
			otherwise().
				log(LoggingLevel.INFO, "Buscar | Validar Pasaporte | docu -> ${header.document} cou-> ${header.country}").
				doTry().
					setHeader(Exchange.HTTP_METHOD, constant("GET")).
					setHeader(Exchange.HTTP_URI, constant("http://" + urlPasaporte)).
					setHeader(Exchange.HTTP_QUERY, simple("document=${headers.document}&country=${headers.country}")).
					to("http://query").
						unmarshal().json(JsonLibrary.Jackson, Pasaporte.class).
							process(new VerificarPasaporteProcessor()).
					doCatch(Exception.class).
						log(LoggingLevel.ERROR, "${exception.message} ${exception.stacktrace}").
							process(new VerificarPasaporteProcessor()).
					end().
				end().
		log("intento buscar pasaporte");
	}

}
