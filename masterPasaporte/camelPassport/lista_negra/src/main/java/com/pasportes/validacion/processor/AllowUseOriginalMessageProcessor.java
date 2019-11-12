package com.pasportes.validacion.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class AllowUseOriginalMessageProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		exchange.getContext().setAllowUseOriginalMessage(true);
	}

}
