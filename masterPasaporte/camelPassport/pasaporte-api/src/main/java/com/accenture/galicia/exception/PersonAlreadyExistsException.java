package com.accenture.galicia.exception;

@SuppressWarnings("serial")
public class PersonAlreadyExistsException extends Exception{
	public PersonAlreadyExistsException() {
		super("That person has a passport already");
	}
}
