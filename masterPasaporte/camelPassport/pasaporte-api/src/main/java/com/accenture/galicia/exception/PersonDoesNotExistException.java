package com.accenture.galicia.exception;

@SuppressWarnings("serial")
public class PersonDoesNotExistException extends Exception {
	public PersonDoesNotExistException () {
		super("Esta persona no existe en el padron");
	}
}
