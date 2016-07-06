package com.github.error418.opennms.client.parameter.collection;

import com.github.error418.opennms.client.exception.ParameterValidationException;
import com.github.error418.opennms.client.parameter.ParameterCollection;

public class FailParameterCollection extends ParameterCollection {

	public FailParameterCollection() {
		this.configureParameter("test");
		this.configureParameter("test");
	}
	
	@Override
	protected void validate() throws ParameterValidationException {
	}

}
