package com.accenture.galicia.routes;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.accenture.galicia.processes.UpdateProcessor;
import com.accenture.galicia.utils.DatabaseConfig;
import com.accenture.galicia.utils.DirectRoutesShortcuts;
@Component
public class UpdateRoute extends RouteBuilder{
	@Autowired
	DatabaseConfig dbC;
	@Override
	public void configure() throws Exception {
		from(DirectRoutesShortcuts.UPDATE).
		process(new UpdateProcessor())
		.to(dbC.getDatabase() + dbC.getPassportCollection() + "&operation=update");
		//.log(LoggingLevel.INFO, "La persona ${body.name} con documento &{body.document} fue modificada");
	}

}
