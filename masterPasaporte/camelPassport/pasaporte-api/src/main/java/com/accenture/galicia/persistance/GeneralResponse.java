package com.accenture.galicia.persistance;

public class GeneralResponse<T> {

	private String code;
	
	private String message;
	
	private T body;
	
	public GeneralResponse() {}
	public GeneralResponse(String code, String message, T body) {
		super();
		this.code = code;
		this.message = message;
		this.body = body;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public T getBody() {
		return body;
	}

	public void setBody(T body) {
		this.body = body;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
