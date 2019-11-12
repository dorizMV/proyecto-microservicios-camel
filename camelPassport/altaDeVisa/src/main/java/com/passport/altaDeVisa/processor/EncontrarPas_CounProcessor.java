package com.passport.altaDeVisa.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import com.passport.altaDeVisa.entity.Visa;

public class EncontrarPas_CounProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		//agarramos el country y el passport del header y se lo enviamos a la base de datos
		Visa insertValue= exchange.getIn().getBody(Visa.class);
		String encPass= insertValue.getPassportNumber();
		String encCoun= insertValue.getCountry();
		
		exchange.getIn().setHeader("passport", encPass);
		exchange.getIn().setHeader("country", encCoun);
		//lo enviamos al header y lo incluimos en una variable
		exchange.getIn().setHeader("impDatos", insertValue);
	}
	}