package com.github.error418.opennms.client.connection;

public class ConnectorException extends Exception {

	private static final long serialVersionUID = 1L;

	public ConnectorException() {
		super();
	}
	
	public ConnectorException(String message) {
		super(message);
	}
	
	public ConnectorException(String message, Throwable cause) {
		super(message, cause);
	}
}
