	package com.passport.camel.routes;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.file.GenericFileExist;
import org.apache.camel.dataformat.csv.CsvDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.passport.camel.config.ConfigProperties;
import com.passport.camel.config.DirectRoutes;
import com.passport.camel.processor.DBObjectToPersonProcessor;
import com.passport.camel.processor.GetByIdAndCountryProcessor;

@Component
public class RouteGetByDocAndCountryFile extends RouteBuilder {

	@Autowired
	private ConfigProperties config;
	
	@Autowired
	ApplicationContext appContext;
	
	String[] header = {"Document", "Name", "lastname", "Country", "Birthday", "Gender"};
	
	@Override
	public void configure() throws Exception {
		from(DirectRoutes.GET_ID_FILE).process(new GetByIdAndCountryProcessor()).
			to(config.getDatabase() + config.getPersonaCollection() + "&operation=findAll").
			process(appContext.getBean(DBObjectToPersonProcessor.class)).
			marshal(getFormat()).
			to("file:personaDir?fileName=unArchivo.txt&fileExist=" + GenericFileExist.Append).
			log(LoggingLevel.INFO, "Respuesta: ${body}");
		
	}
	
	public CsvDataFormat getFormat() {
		CsvDataFormat df = new CsvDataFormat();
		df.setHeader(this.header);
		return df;
	}
	
}
