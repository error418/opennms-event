package com.github.error418.opennms.client.transfer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name = "value")
@XmlAccessorType(XmlAccessType.NONE)
public class ParameterValue {

	@XmlAttribute(name = "type")
	private String type = "string";

	@XmlAttribute(name = "encoding")
	private String encoding = "text";

	@XmlValue
	private String value;
	
	
	public ParameterValue() {
	}
	
	public ParameterValue(String value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
