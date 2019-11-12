package com.pasportes.validacion.exceptions;

public class PersonMissingException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public PersonMissingException() {
		super("No such of person found");
	}

}
