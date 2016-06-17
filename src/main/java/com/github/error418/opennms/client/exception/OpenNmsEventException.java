package com.github.error418.opennms.client.exception;

public class OpenNmsEventException extends Exception {
	private static final long serialVersionUID = 1L;

	public OpenNmsEventException() {
		
	}
	
	public OpenNmsEventException(String message) {
		super(message);
	}
	
	public OpenNmsEventException(String message, Throwable cause) {
		super(message, cause);
	}
}
