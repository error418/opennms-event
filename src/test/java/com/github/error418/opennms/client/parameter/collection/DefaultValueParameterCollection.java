package com.github.error418.opennms.client.parameter.collection;

import com.github.error418.opennms.client.exception.ParameterValidationException;
import com.github.error418.opennms.client.parameter.ParameterCollection;

public class DefaultValueParameterCollection extends ParameterCollection {

	public DefaultValueParameterCollection() {
		this.configureParameter("one", 1);
		this.configureParameter("two", "2");
		this.configureParameter("three");
	}
	
	@Override
	protected void validate() throws ParameterValidationException {

	}

}
