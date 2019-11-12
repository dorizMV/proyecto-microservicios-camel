package com.pasportes.validacion.routes;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.InvalidPayloadException;

import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import com.pasportes.validacion.util.GeneralResponse;
import com.pasportes.validacion.util.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.pasportes.validacion.constants.Constants;
import com.pasportes.validacion.entities.Antecedente;
import com.pasportes.validacion.entities.DeleteRequest;
import com.pasportes.validacion.entities.Persona;
import com.pasportes.validacion.exceptions.PersonMissingException;
import com.pasportes.validacion.processor.HeaderToQueryProcessor;
import com.pasportes.validacion.processor.SimpleListProcessor;
import com.pasportes.validacion.processor.UpdateOneStepProcessor;
import com.pasportes.validacion.processor.UpdateProcessor;
import com.pasportes.validacion.processor.UpdateThreeStepProcessor;
import com.pasportes.validacion.processor.UpdateTwoStepProcessor;
import com.pasportes.validacion.processor.AnsListCreatorProcessor;
import com.pasportes.validacion.processor.FromPersonaToAntecedenteProcessor;


@Component
public class ValidationRoutes extends RouteBuilder {
	
	@Autowired
	private ObjectMapper mapper;
	@Autowired
	private AnsListCreatorProcessor ansListProcessor; 
	
	private List<Antecedente> ans = new ArrayList<Antecedente>();
	
	
	@Override
	public void configure() throws Exception {
	
		from(Constants.GET_ANS)
	    	.to( Constants.MONGODB + Constants.DATABASE + Constants.COLLECTION_ANS + "&operation=findAll")
	    	//.split().body()
	    		.process(new SimpleListProcessor());
	    	//.end()
	    
		from(Constants.POST_ANS).
			to(Constants.HTTP_PERSONA).
			doTry()
				.process(new FromPersonaToAntecedenteProcessor())
				.doCatch(Exception.class)
					.to("direct:alerta")
					.end()
				.to(Constants.MONGODB + Constants.DATABASE + Constants.COLLECTION_ANS +"&operation=insert");
		
		from(Constants.GET_ANS_DOC)
			.process(new HeaderToQueryProcessor())
			.to(Constants.MONGODB + Constants.DATABASE + Constants.COLLECTION_ANS + "&operation=findAll")
//			.split().body()
			.process(ansListProcessor);


		from("direct:alerta").log("this is Esparta!");
		
		from(Constants.PUT_ANS)
			.choice()
				.when(simple("${body.persona} == null"))
					.process(new UpdateOneStepProcessor())
				.when(simple("${body.persona.document} == null || ${body.persona.document} == ${header.document}"))
					.process(new UpdateTwoStepProcessor())
				.when(simple("${body.persona} != ${header.document} && ${body.descripcion} == null"))
					.process(new UpdateThreeStepProcessor()).end()
			 .to(Constants.MONGODB + Constants.DATABASE + Constants.COLLECTION_ANS +"&operation=update");
		
		from(Constants.DELETE_ANS).choice().when(simple("${body.reason} == null"))
		 .process(new Processor() {
			
			@Override
			public void process(Exchange exchange) throws Exception {
				GeneralResponse gral = new GeneralResponse();
				gral.setBody(null);
				gral.setCode("204");
				gral.setMsg("you need to set a reason to delete Antecedentes");
				
				exchange.getIn().setBody(gral);
			}
		}).otherwise().process(new Processor() {
			
			@Override
			public void process(Exchange exchange) throws Exception {
				DBObject filter = new BasicDBObject();
				if(exchange.getIn().getBody(DeleteRequest.class).getAntecedente() != null) {
					filter.put("persona.document", exchange.getIn().getBody(DeleteRequest.class).getAntecedente().getPersona().getDocument());
					filter.put("descripcion",  exchange.getIn().getBody(DeleteRequest.class).getAntecedente().getDescripcion());
				}
				
				exchange.getIn().setBody(filter);
			}
		})
		 .to(Constants.MONGODB + Constants.DATABASE + Constants.COLLECTION_ANS +"&operation=remove").end();
		
	}
	

}
