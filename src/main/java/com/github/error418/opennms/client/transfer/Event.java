package com.github.error418.opennms.client.transfer;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.github.error418.opennms.client.adapter.DateAdapter;

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
	@XmlJavaTypeAdapter(DateAdapter.class)
	private Date time;

	@XmlElement(name = "host")
	private String host;

	@XmlElement(name = "parms")
	private ParameterList parameterList;

	@XmlElement(name = "interface")
	private String interfaceName;

	@XmlElement(name = "service")
	private String service;

	@XmlElement(name = "ifIndex")
	private Integer ifIndex;

	@XmlElement(name = "descr")
	private String description;

	@XmlElement(name = "logmsg")
	private LogMessage logMessage;

	@XmlElement(name = "severity")
	private Severity severity;

	@XmlElement(name = "operinstruct")
	private String operationInstruction;

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

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public Integer getIfIndex() {
		return ifIndex;
	}

	public void setIfIndex(Integer ifIndex) {
		this.ifIndex = ifIndex;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LogMessage getLogMessage() {
		return logMessage;
	}

	public void setLogMessage(LogMessage logMessage) {
		this.logMessage = logMessage;
	}

	public Severity getSeverity() {
		return severity;
	}

	public void setSeverity(Severity severity) {
		this.severity = severity;
	}

	public String getOperationInstruction() {
		return operationInstruction;
	}

	public void setOperationInstruction(String operationInstruction) {
		this.operationInstruction = operationInstruction;
	}

	public void setParameterList(ParameterList parameterList) {
		this.parameterList = parameterList;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

}
