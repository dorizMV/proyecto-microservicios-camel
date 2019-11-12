package com.accenture.galicia.processes;

import java.util.Date;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import com.accenture.galicia.persistance.GeneralResponse;
import com.accenture.galicia.persistance.Passport;
import com.accenture.galicia.pojos.Person;

@Component
public class PassportCreationProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		Person person= exchange.getIn().getBody(Person.class);
		Passport pasaporte = new Passport();

		pasaporte.setPerson(person);
		pasaporte.setPassportNumber(person.getCountry().substring(0, 3) + pasaporte.getPerson().getDocument());
		pasaporte.setFechaCreacion(new Date());

		exchange.getIn().setBody(pasaporte);
	}

}
