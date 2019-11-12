package com.passport.camel.aggregator;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import com.passport.camel.model.Persona;
import com.passport.camel.response.GeneralResponse;

public class CreationStrategy implements AggregationStrategy {

	@Override
	public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
		List<GeneralResponse<Persona>> response;
		Exchange ex;
		if (oldExchange == null) {
			response = new ArrayList<GeneralResponse<Persona>>();
			ex = newExchange;
		} else {
			response = oldExchange.getIn().getBody(List.class);
			ex = oldExchange;
		}
		 
		GeneralResponse<Persona> newRes = newExchange.getIn().getBody(GeneralResponse.class);
		response.add(newRes);
		ex.getIn().setBody(response);
		return ex;
	}
	
}
