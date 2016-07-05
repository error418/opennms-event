package com.github.error418.opennms.client.parameter;

import com.github.error418.opennms.client.exception.ParameterValidationException;

public class FailParameterCollection extends ParameterCollection {

	public FailParameterCollection() {
		this.configureParameter("test");
		this.configureParameter("test");
	}
	
	@Override
	protected void validate() throws ParameterValidationException {
	}

}
