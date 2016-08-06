package com.github.error418.opennms.client.connection;

public enum OnmsConnectionType {
	UDP("UDP"),
	TCP("TCP");
	
	private final String identifier;
	private OnmsConnectionType(String identifier) {
		this.identifier = identifier;
	}
	
	
	/**
	 * Tries to resolve a {@link OnmsConnectionType} by its identifier.
	 * 
	 * @param identifier identifier to look for
	 * @return {@link OnmsConnectionType} with the given identifier; otherwise: null
	 */
	public static OnmsConnectionType getByIdentifier(String identifier) {
		for(OnmsConnectionType type : OnmsConnectionType.values()) {
			if(type.identifier.equalsIgnoreCase(identifier)) {
				return type;
			}
		}
		
		return null;
	}
}
