package com.accenture.galicia.exception;

@SuppressWarnings("serial")
public class PersonNotEqualException extends Exception{
	public PersonNotEqualException () {
		super("Las personas comparadas no son iguales");
	}
}
