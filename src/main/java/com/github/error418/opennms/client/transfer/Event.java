package com.github.error418.opennms.client.transfer;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "event")
@XmlAccessorType(XmlAccessType.NONE)
public class Event {

	@XmlElement(name = "uei")
	private String uei;

	@XmlElement(name = "source")
	private String source;

	@XmlElement(name = "nodeid")
	private String nodeId;

	@XmlElement(name = "time")
	private Date time;

	@XmlElement(name = "host")
	private String host;

	@XmlElement(name = "parms")
	private ParameterList parameterList;

	public Event() {
	}

	public String getUei() {
		return uei;
	}

	public void setUei(String uei) {
		this.uei = uei;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public ParameterList getParameterList() {
		return parameterList;
	}

	public void setParameters(ParameterList parameters) {
		this.parameterList = parameters;
	}

}
