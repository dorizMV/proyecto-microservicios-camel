package com.passport.altaDeVisa.processor;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.bson.Document;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.passport.altaDeVisa.entity.Visa;

public class DateVisa implements Processor {
	
	private int visaVencimiento;

	public DateVisa(int visaVencimiento) {
		this.visaVencimiento= visaVencimiento;
	}

	@Override
	public void process(Exchange exchange) throws Exception {
		Visa valueExp = exchange.getIn().getBody(Visa.class);
		
		Calendar cal= Calendar.getInstance();
		
		cal.add(Calendar.MONTH, visaVencimiento);
		
		Date encEmis= new Date();
		Date encExp= cal.getTime();
		
		ObjectMapper mapper= new ObjectMapper();
		Document auxDocu= new Document(mapper.convertValue(valueExp, Map.class));
		
		auxDocu.put("emissionDate", encEmis);
		auxDocu.put("expirationDate", encExp);
		
		exchange.getIn().setBody(auxDocu);
	}

}
