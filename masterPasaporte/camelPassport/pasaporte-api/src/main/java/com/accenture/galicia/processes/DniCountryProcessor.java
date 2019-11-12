package com.accenture.galicia.processes;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.accenture.galicia.pojos.Person;

public class DniCountryProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		
		//Seteamos el body en nuestro pojo persona
		Person p = exchange.getIn().getBody(Person.class);
		
		//Seteamos header con los datos requeridos por el servicio de padron
		exchange.getIn().setHeader("country", p.getCountry());
		exchange.getIn().setHeader("document", p.getDocument());
		
		//Guardamos en el exchange la persona que ingresamos
		exchange.setProperty("personOld", p);
		
		exchange.getIn().setBody(null);
		
			}

}
