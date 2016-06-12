package com.github.error418.opennms.client.transfer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "parm")
@XmlAccessorType(XmlAccessType.NONE)
public class Parameter {

	@XmlElement(name = "parmName")
	private String parameterName;

	@XmlElement(name = "value")
	private ParameterValue value;

	public String getParameterName() {
		return parameterName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

	public ParameterValue getValue() {
		return value;
	}

	public void setValue(ParameterValue value) {
		this.value = value;
	}

}
