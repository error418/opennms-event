package com.github.error418.opennms.client.parameter;

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
	
	public void setFirstParameter(String value) {
		this.setValue(FIRST_PARAM, value);
	}

	public void setSecondParameter(Integer value) {
		this.setValue(SECOND_PARAM, value);
	}

	public void setThirdParameter(String value) {
		this.setValue(THIRD_PARAM, value);
	}
}
