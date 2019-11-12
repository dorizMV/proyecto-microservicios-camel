package com.passport.camel.processor;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.util.CollectionUtils;

import com.passport.camel.exception.PersonNotValidException;
import com.passport.camel.model.Persona;

public class ValidatePersonProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<Persona>> set = validator.validate(exchange.getIn().getBody(Persona.class));
		if (!CollectionUtils.isEmpty(set)) {
			throw new PersonNotValidException();
		}
	}

}