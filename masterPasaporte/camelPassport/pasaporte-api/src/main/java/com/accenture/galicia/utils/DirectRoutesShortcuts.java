package com.accenture.galicia.utils;

public class DirectRoutesShortcuts {
	
	public static final String JSON = "application/json";
	public final static String TEXT_CSV = "text/csv";
	public static final String DIRECT = "direct:";
	// -------**-------------RUTAS-----------**-------------//

	// --------------Validacion de Person------------------//
	public static final String GET_ID = DIRECT + "getId";

	//----------------Validacion de Pasaportes-------------//
	public static final String GET_ALL_PASSPORTS = DIRECT + "getAll";
	// --------------Checkeo de prioridad------------------//
	public static final String PRIORITY_CHECKER = DIRECT + "checkPriority";

	// ------------Ruta priority de activemq----------------//
	public static final String PRIORITY_QUEUE = "activemq:priority";
	
	//-------------Ruta de creacion-------------------------//
	public static final String CREATE = DIRECT + "create";
	
	//-------------Ruta de creacion-------------------------//
	public static final String UPDATE = DIRECT + "update";

	public static final String DELETE_PASSPORT_DNI = DIRECT + "deletebydni";
	
	public static final String GET_PASSPORT_DNI = DIRECT + "getpassportdni";
	
	public static final String GET_PASSPORT_DNI_AND_COUNTRY = DIRECT + "getpassportdniandcountry";
	
	public static final String GET_PASSPORT_DATE = DIRECT + "getpassportdate";
	

}
