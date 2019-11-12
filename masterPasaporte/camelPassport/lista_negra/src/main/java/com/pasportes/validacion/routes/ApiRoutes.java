package com.pasportes.validacion.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.pasportes.validacion.constants.Constants;
import com.pasportes.validacion.entities.Antecedente;
import com.pasportes.validacion.entities.DeleteRequest;
import com.pasportes.validacion.entities.Pais;
import com.pasportes.validacion.entities.Persona;
import com.pasportes.validacion.util.GeneralResponse;

@Component
public class ApiRoutes extends RouteBuilder {

	@Override
	public void configure() throws Exception {		
			
		rest("/pais")
		.get("/traerTodos")
		.produces(MediaType.APPLICATION_JSON_VALUE)
		.outType(Pais[].class)
		.to("direct:findAllCountry")
			.post("/crearPais")
			.produces(MediaType.APPLICATION_JSON_VALUE)
			.outType(Pais.class)
			.to("direct:postACountry");
		
		rest("/validacion")
			.post("/crearAntecedente")
				.consumes(MediaType.APPLICATION_JSON_VALUE)
				.type(Antecedente.class)
				.outType(Antecedente.class)
				.produces(MediaType.APPLICATION_JSON_VALUE)
				.to(Constants.POST_ANS)
			.get("/antecedentes")
				.produces(MediaType.APPLICATION_JSON_VALUE)
				.outType(Antecedente[].class)
				.to(Constants.GET_ANS)
			.get("/antecedentes_documento?document={document}&country={country}")
				.outType(Antecedente[].class)
				.produces(MediaType.APPLICATION_JSON_VALUE)
				.to(Constants.GET_ANS_DOC)
			.put("/modificarAntecedente?document={document}")
				.consumes(MediaType.APPLICATION_JSON_VALUE)
				.type(Antecedente.class)
				.outType(Antecedente.class)
			.to(Constants.PUT_ANS)
			.delete("/eliminarAntecedente")
				.consumes(MediaType.APPLICATION_JSON_VALUE)
				.type(DeleteRequest.class)
				.outType(GeneralResponse.class)
			.to(Constants.DELETE_ANS);

	}
	
	
}
