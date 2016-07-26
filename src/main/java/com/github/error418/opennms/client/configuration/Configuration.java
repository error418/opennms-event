package com.github.error418.opennms.client.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.error418.opennms.client.connection.OnmsConnectionType;

/**
 * Loads and contains the configuration of onms-client.
 * 
 */
public class Configuration {
	private static final Logger logger = LoggerFactory.getLogger(Configuration.class);
	
	public static final String ONMS_CLIENT_PROPERTY_FILE = "/onms-event.properties";
	public static final String KEY_CONNECTION_TYPE = "onmsclient.connection.type";
	public static final String KEY_SERVER_ADDRESS = "onmsclient.connection.address";
	public static final String KEY_SERVER_PORT = "onmsclient.connection.port";
	
	static final OnmsConnectionType DEFAULT_CONNECTION_TYPE = OnmsConnectionType.TCP;
	
	/**
	 * The OpenNMS standard port: {@value #ONMS_STANDARD_PORT}
	 */
	static final int DEFAULT_CONNECTION_PORT = 5817;

	private OnmsConnectionType connectionType;
	private String onmsServerAddress;
	private int onmsPort;

	private static Configuration instance;

	public static Configuration instance() {
		if (instance == null) {
			InputStream propertyStream = ClassLoader.class.getResourceAsStream(ONMS_CLIENT_PROPERTY_FILE);
			
			if (propertyStream != null) {
				Properties properties = new Properties();
				
				try {
					properties.load(propertyStream);
					instance = new Configuration(properties);
				} catch (IOException e) {
					logger.error("Could not load onms-client property file. Using default values.", e);
				}
			}
			
		}

		return instance;
	}

	Configuration(Properties properties) {
		// set connection type
		connectionType = OnmsConnectionType.getByIdentifier(properties.getProperty(KEY_CONNECTION_TYPE));
		if (connectionType == null) {
			logger.warn("invalid value '{}' for property {} using default ({})", properties.getProperty(KEY_CONNECTION_TYPE), KEY_CONNECTION_TYPE, DEFAULT_CONNECTION_TYPE);
			connectionType =  DEFAULT_CONNECTION_TYPE;
		}

		// set server address
		onmsServerAddress = properties.getProperty(KEY_SERVER_ADDRESS);
		
		onmsPort = Integer.parseInt(properties.getProperty(KEY_SERVER_PORT, String.valueOf(DEFAULT_CONNECTION_PORT)));
	}

	public OnmsConnectionType getConnectionType() {
		return connectionType;
	}

	public String getOnmsServerAddress() {
		return onmsServerAddress;
	}

	public int getOnmsPort() {
		return onmsPort;
	}

}
