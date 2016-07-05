package com.github.error418.opennms.client.parameter;

import com.github.error418.opennms.client.exception.ParameterValidationException;

/**
 * A custom Parameter Collection.
 * 
 */
public class CustomParameterCollection extends ParameterCollection {

	final static String FIRST_PARAM = "firstParameter";
	final static String SECOND_PARAM = "secondParameter";
	final static String THIRD_PARAM = "thirdParameter";

	private String[] parameterOrder = { FIRST_PARAM, SECOND_PARAM, THIRD_PARAM };

	public CustomParameterCollection() {
		for (String parameterName : parameterOrder) {
			this.configureParameter(parameterName);
		}
	}
	
	public CustomParameterCollection setFirstParameter(String value) {
		this.setValue(FIRST_PARAM, value);
		return this;
	}

	public CustomParameterCollection setSecondParameter(Integer value) {
		this.setValue(SECOND_PARAM, value);
		return this;
	}

	public CustomParameterCollection setThirdParameter(String value) {
		this.setValue(THIRD_PARAM, value);
		return this;
	}

	@Override
	protected void validate() throws ParameterValidationException {
		if(this.isParameterNull(FIRST_PARAM)) {
			throw new ParameterValidationException();
		}
	}

}
