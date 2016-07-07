package com.github.error418.opennms.client.exception;

/**
 * Is thrown when a required OpenNMS Event property is not set.
 * 
 */
public class RequiredPropertyException extends OpenNmsEventException {

	private static final long serialVersionUID = 1L;
	private final String propertyName;

	public RequiredPropertyException(String propertyName) {
		super("An event needs to have the property '" + propertyName + "' specified.");
		this.propertyName = propertyName;
	}
	
	/**
	 * Returns the property name of the missing field
	 * 
	 * @return property name of the missing field
	 */
	public String getPropertyName() {
		return propertyName;
	}
}
