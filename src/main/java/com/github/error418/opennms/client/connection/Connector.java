package com.github.error418.opennms.client.connection;

/**
 * Provides a connection to the OpenNms EventD service
 * 
 */
public interface Connector {
	
	/**
	 * Sends the data to the target and performs any necessary cleanup operations.
	 * 
	 * @param data data to send to the connection target
	 * @throws ConnectorException thrown on connector related exceptions
	 */
	public void send(String data) throws ConnectorException;
}
