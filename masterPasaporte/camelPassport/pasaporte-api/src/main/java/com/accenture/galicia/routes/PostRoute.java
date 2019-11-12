package com.accenture.galicia.routes;

import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.accenture.galicia.exception.PersonNotEqualException;
import com.accenture.galicia.pojos.Person;
import com.accenture.galicia.processes.DBObjectToPassportProcessor;
import com.accenture.galicia.processes.DniCountryProcessor;
import com.accenture.galicia.processes.GeneralResponseProcessor;
import com.accenture.galicia.processes.GetByDniAndCountryProcessor;
import com.accenture.galicia.processes.PassportCreationProcessor;
import com.accenture.galicia.processes.PersonComparingProcessor;
import com.accenture.galicia.processes.PrintPersonAlreadyExistException;
import com.accenture.galicia.processes.PrintPersonDoesNotExistExceptionProcessor;
import com.accenture.galicia.processes.PrintStatusExceptionProcessor;
import com.accenture.galicia.utils.DatabaseConfig;
import com.accenture.galicia.utils.DirectRoutesShortcuts;


@Component
public class PostRoute extends RouteBuilder {
	
	@Autowired
	ApplicationContext appContext;
	
	@Autowired
	DatabaseConfig dbC;

	@Override
	public void configure() throws Exception {
		from(DirectRoutesShortcuts.GET_ID)
		.log(LoggingLevel.INFO, "Se va a crear el pasaporte a esta persona: ${body}")
		.process(new DniCountryProcessor())
		//Tenemos guardada a la person old, ahora chequearemos si estamos por crear un duplicado
				.process(new GetByDniAndCountryProcessor())
				.to(dbC.getDatabase() + dbC.getPassportCollection() + "&operation=findOneByQuery")
				.log(LoggingLevel.INFO, "Pasaporte que trajo la base de datos: ${body}")
			.choice()
				.when(simple("${body} != null"))
				.log(LoggingLevel.ERROR, "La persona ya tiene creado su papasorte ${body}")
				.process(new PrintPersonAlreadyExistException())
			.otherwise()
				.setHeader(Exchange.HTTP_METHOD, constant("GET"))
				.setHeader(Exchange.HTTP_URI, constant("http://localhost:8080/personas/getPersona"))
				.setHeader(Exchange.HTTP_QUERY, simple("document=${header.document}&country=${header.country}"))
				.to("http://query")
		// Tras el llamado del servicio nos retorna un json con la persona encontrada
				.choice()
				.when(simple("${header.CamelHttpResponseCode} == 204"))
					.process(new PrintPersonDoesNotExistExceptionProcessor())
				.otherwise()
					.doTry()
					.unmarshal()
					.json(JsonLibrary.Jackson, Person.class)
					.process(new PersonComparingProcessor())
					.to(DirectRoutesShortcuts.PRIORITY_CHECKER)
					.doCatch(PersonNotEqualException.class)
					.log(LoggingLevel.ERROR, "Las personas comparadas no son iguales")
					.process(new PrintStatusExceptionProcessor()).end()
					.endChoice();

		from(DirectRoutesShortcuts.CREATE)
		.process(new PassportCreationProcessor())
				.to(dbC.getDatabase() + dbC.getPassportCollection() + "&operation=insert")
				.log(LoggingLevel.INFO,"Pasaporte creado: ${body}")
				.process(appContext.getBean(DBObjectToPassportProcessor.class))
				.process(new GeneralResponseProcessor());

		from(DirectRoutesShortcuts.PRIORITY_CHECKER)
		.choice()
		.when(simple("${exchangeProperty.personOld.priority}"))
			.to(DirectRoutesShortcuts.CREATE)
		.otherwise().marshal().json(JsonLibrary.Jackson, Person.class)
			.to(ExchangePattern.InOnly, DirectRoutesShortcuts.PRIORITY_QUEUE).endChoice();

		from(DirectRoutesShortcuts.PRIORITY_QUEUE)
		.unmarshal().json(JsonLibrary.Jackson, Person.class)
				.to(DirectRoutesShortcuts.CREATE).log(LoggingLevel.INFO,"Create PriorityQueue: ${body}")
				.log(LoggingLevel.INFO,"${body} esperando a ser procesado en PriorityQueue");
	}

}
