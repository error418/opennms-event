package com.github.error418.opennms.client.exception;

/**
 * Gets thrown when invalid parameters are detected. 
 * 
 */
public class ParameterValidationException extends IllegalArgumentException {
	private static final long serialVersionUID = 1L;

	public ParameterValidationException() {
	}
	
	public ParameterValidationException(String message) {
		super(message);
	}
	
	public ParameterValidationException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public ParameterValidationException(String parameterName, String cause) {
		super("Parameter '" + parameterName + "' is invalid due to: \"" + cause + "\"");
	}
}
