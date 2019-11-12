package com.passport.camel.util;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;

@Component
public class ObjectMapper extends com.fasterxml.jackson.databind.ObjectMapper {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	ObjectMapper() {
		configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}
	
}
