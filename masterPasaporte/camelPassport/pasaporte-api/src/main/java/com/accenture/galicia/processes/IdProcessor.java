package com.accenture.galicia.processes;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;
@Component
public class IdProcessor implements Processor{

	@Override
	public void process(Exchange exchange) throws Exception {
		Object id = exchange.getIn().getHeader("numero");
		exchange.getIn().setBody(id);
	}

}
