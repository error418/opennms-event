package com.github.error418.opennms.client.transfer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class LogMessage {
	@XmlValue
	private String message;

	@XmlAttribute(name = "dest")
	private LogMessageDestination destination;

	public LogMessage() {
	}

	public LogMessage(String message) {
		this.message = message;
	}

	public LogMessage(String message, LogMessageDestination destination) {
		this.message = message;
		this.destination = destination;
	}

	public String getValue() {
		return message;
	}

	public void setValue(String value) {
		this.message = value;
	}

	public LogMessageDestination getDestination() {
		return destination;
	}

	public void setDestination(LogMessageDestination destination) {
		this.destination = destination;
	}

}
