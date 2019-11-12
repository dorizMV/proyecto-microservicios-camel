package com.pasportes.validacion.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.pasportes.validacion.entities.Antecedente;
import com.pasportes.validacion.entities.Persona;

public class FromPersonaToAntecedenteProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		Antecedente a = new Antecedente();
		
		a.setPersona(exchange.getIn().getBody(Persona.class));
		a.setDescripcion(exchange.getIn().getHeader("descripcion",String.class));
		
		exchange.getOut().setBody(null);
		exchange.getOut().setBody(a);

	}

}
