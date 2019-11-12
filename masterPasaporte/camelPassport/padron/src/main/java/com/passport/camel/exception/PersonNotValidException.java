package com.passport.camel.exception;

public class PersonNotValidException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PersonNotValidException() {
		super("Person has invalid fields");
	}
	
}
