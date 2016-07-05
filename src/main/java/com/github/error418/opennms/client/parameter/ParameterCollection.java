package com.github.error418.opennms.client.parameter;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map.Entry;

import com.github.error418.opennms.client.exception.ParameterValidationException;
import com.github.error418.opennms.client.transfer.Parameter;
import com.github.error418.opennms.client.transfer.ParameterValue;

/**
 * Superclass for Parameter Collections.
 * 
 *  This class ensures the correct order of parameters inside a parameter collection.
 *  
 *  <p>
 *  	By implementing {@link #validate()} you can also enforce parameter value constraints.
 *  </p>
 *  
 */
public abstract class ParameterCollection {
	
	private LinkedHashMap<String, String> parameters = new LinkedHashMap<String, String>();
	
	public ParameterCollection() {
	}
	
	/**
	 * Is executed before building the parm list with {@link #buildParameterList()}.
	 * 
	 * <p>
	 * Is used for parameter validation.
	 * </p>
	 * 
	 * @throws ParameterValidationException when parameter values are invalid
	 */
	protected abstract void validate() throws ParameterValidationException;
	
	private void addParameter(String parameterName, String value) {
		if(parameters.containsKey(parameterName)) {
			throw new IllegalArgumentException("Parameter with name '" + parameterName + "' is already configured.");
		}
		parameters.put(parameterName, value);
	}
	
	/**
	 * Adds a parameter to the parameter collection.
	 * 
	 * <p>
	 * Is commonly used in the constructor. Keep in mind that the order of the {@link #configureParameter(String)} calls
	 * needs to match the order of the OpenNMS parameters.
	 * </p>
	 * <p>
	 * Sets the initial value of the parameter to null
	 * </p>
	 * 
	 * @param parameterName name of the parameter
	 */
	protected final void configureParameter(String parameterName) {
		this.addParameter(parameterName, null);
	}
	
	/**
	 * Sets a initial default value for the parameter.
	 * 
	 * @param parameterName parameter name
	 * @param defaultValue default value
	 */
	protected final void configureParameter(String parameterName, String defaultValue) {
		this.addParameter(parameterName, defaultValue);
	}
	
	/**
	 * Sets a initial default value for the parameter.
	 * 
	 * @param parameterName parameter name
	 * @param defaultValue default value
	 */
	protected final void configureParameter(String parameterName, Number defaultValue) {
		this.addParameter(parameterName, String.valueOf(defaultValue));
	}
	
	/**
	 * Sets the value of a parameter.
	 * 
	 * @param key Parameter name
	 * @param value Parameter value
	 */
	protected final void setValue(String key, String value) {
		parameters.put(key, value);
	}
	
	/**
	 * Checks, if a parameter has the value null
	 * 
	 * @param parameterName parameter name
	 * @return true, if the parameter's value is null
	 */
	protected final boolean isParameterNull(String parameterName) {
		return parameters.get(parameterName) == null;
	}
	
	/**
	 * Retrieves the current parameter value for the given parameter name as String
	 * 
	 * @param parameterName parameter name
	 * @return parameter value
	 */
	protected final String getValue(String parameterName) {
		return parameters.get(parameterName);
	}

	/**
	 * Sets the value of a parameter.
	 * 
	 * @param key Parameter name
	 * @param value Parameter value
	 */
	protected final void setValue(String key, Number value) {
		this.setValue(key, String.valueOf(value));
	}
	
	/**
	 * Builds the parameter list.
	 * 
	 * @throws IllegalArgumentException if Parameter Validation considered parameter values as invalid.
	 * @return parameter list with configured order.
	 */
	public LinkedList<Parameter> buildParameterList() throws ParameterValidationException {
		this.validate();
		
		LinkedList<Parameter> result = new LinkedList<Parameter>();
		
		for(Entry<String, String> entry : parameters.entrySet()) {
			Parameter p = new Parameter();
			p.setParameterName(entry.getKey());
			p.setParameterValue(new ParameterValue(entry.getValue()));
			
			result.add(p);
		}
		
		return result;
	}
	
}
