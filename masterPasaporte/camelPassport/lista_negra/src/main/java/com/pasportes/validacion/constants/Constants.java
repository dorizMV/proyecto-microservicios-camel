package com.pasportes.validacion.constants;

public class Constants {

	public static final String MONGODB = "mongodb:mongoBean";
	public static final String COLLECTION_ANS = "&collection=antecedentes";
	public static final String DATABASE = "?database=local";
	
	
	
	public static final String HTTP_PERSONA = "direct:buscarPersona";
	public static final String REST_ = "direct:";
	
	public static final String GET_ANS = "direct:findAllAntecedentes";
	public static final String POST_ANS = "direct:postAntecedentes";
	public static final String GET_ANS_DOC = "direct:antecedentesPorDocumento";
	public static final String PUT_ANS = "direct:update";
	public static final String DELETE_ANS = "direct:remove";

}
