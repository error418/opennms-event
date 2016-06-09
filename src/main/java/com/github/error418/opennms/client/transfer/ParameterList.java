package com.github.error418.opennms.client.transfer;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "parms")
@XmlAccessorType(XmlAccessType.NONE)
public class ParameterList {
	@XmlElement(name = "parm")
	private List<Parameter> parameters;

	public ParameterList() {
		this.parameters = new LinkedList<Parameter>();
	}

	public ParameterList(List<Parameter> parameters) {
		this.parameters = parameters;
	}

	public List<Parameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}

}
