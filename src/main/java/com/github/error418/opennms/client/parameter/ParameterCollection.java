package com.github.error418.opennms.client.parameter;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map.Entry;

import com.github.error418.opennms.client.transfer.Parameter;
import com.github.error418.opennms.client.transfer.ParameterValue;

/**
 * Superclass for Parameter Collections.
 * 
 *  This class ensures the correct order of parameters inside a parameter collection.
 *  
 */
public abstract class ParameterCollection {
	
	private LinkedHashMap<String, Object> parameters = new LinkedHashMap<String, Object>();
	
	public ParameterCollection() {
	}
	
	/**
	 * Adds a parameter to the parameter collection.
	 * 
	 * Is commonly used in the constructor. Keep in mind that the order of the {@link #configureParameter(String)} calls
	 * needs to match the order of the OpenNMS parameters.
	 * 
	 * @param key name of the parameter
	 */
	protected final void configureParameter(String key) {
		parameters.put(key, null);
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
	
	protected final void setValue(String key, Number value) {
		this.setValue(key, String.valueOf(value));
	}
	
	/**
	 * Builds the parameter list.
	 * 
	 * @return parameter list with configured order.
	 */
	public LinkedList<Parameter> buildParameterList() {
		LinkedList<Parameter> result = new LinkedList<Parameter>();
		
		for(Entry<String, Object> entry : parameters.entrySet()) {
			Parameter p = new Parameter();
			p.setParameterName(entry.getKey());
			p.setParameterValue(new ParameterValue(String.valueOf(entry.getValue())));
			
			result.add(p);
		}
		
		return result;
	}
	
}
