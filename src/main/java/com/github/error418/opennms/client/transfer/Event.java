package com.github.error418.opennms.client.transfer;

import java.net.InetAddress;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.github.error418.opennms.client.adapter.DateAdapter;
import com.github.error418.opennms.client.adapter.InetAddressHostAdapter;

@XmlRootElement(name = "event")
@XmlAccessorType(XmlAccessType.NONE)
public class Event {

	@XmlElement(name = "uei")
	private String uei;

	@XmlElement(name = "source")
	private String source;

	@XmlElement(name = "nodeid")
	private int nodeId;

	@XmlElement(name = "time")
	@XmlJavaTypeAdapter(DateAdapter.class)
	private Date time;

	@XmlElement(name = "host")
	private String host;

	@XmlElement(name = "parms")
	@XmlElementWrapper(name = "parms")
	private List<Parameter> parameterList;

	@XmlElement(name = "interface")
	@XmlJavaTypeAdapter(InetAddressHostAdapter.class)
	private InetAddress interfaceAddress;

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

	public int getNodeId() {
		return nodeId;
	}

	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}

	/**
	 * Returns a clone of the Event's time instance
	 * 
	 * @return clone of the event's time instance
	 */
	public Date getTime() {
		if(this.time == null) {
			return null;
		} else {
			return (Date)this.time.clone();
		}
	}

	/**
	 * Sets the event's time by cloning the passed date instance.
	 * 
	 * @param time time of the event
	 */
	public void setTime(Date time) {
		if(time == null) {
			this.time = null;
		} else {
			this.time = (Date)time.clone();
		}
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
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

	public InetAddress getInterfaceAddress() {
		return interfaceAddress;
	}

	public void setInterfaceAddress(InetAddress interfaceName) {
		this.interfaceAddress = interfaceName;
	}

	public List<Parameter> getParameterList() {
		return parameterList;
	}

	public void setParameterList(List<Parameter> parameterList) {
		this.parameterList = parameterList;
	}

}
