package com.pasportes.validacion.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.restlet.routing.Template;

import com.pasportes.validacion.entities.Antecedente;

public class DocumentAndCountryProcess implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		Antecedente a = exchange.getIn().getBody(Antecedente.class);
		exchange.getIn().setHeader("persona", a.getPersona());
		exchange.getIn().setHeader("descripcion", a.getDescripcion());
		exchange.getIn().setBody(null);	

	}

}
