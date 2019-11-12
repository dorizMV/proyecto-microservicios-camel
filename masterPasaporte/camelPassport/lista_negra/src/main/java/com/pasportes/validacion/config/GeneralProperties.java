package com.pasportes.validacion.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GeneralProperties {

	@Value("rest.personas")
	private String personasUrl;
	
	
}
