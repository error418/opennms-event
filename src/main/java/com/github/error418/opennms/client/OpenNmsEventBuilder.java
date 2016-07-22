package com.github.error418.opennms.client;

import java.io.IOException;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.error418.opennms.client.configuration.Configuration;
import com.github.error418.opennms.client.connection.Connector;
import com.github.error418.opennms.client.connection.ConnectorException;
import com.github.error418.opennms.client.connection.OnmsConnectionType;
import com.github.error418.opennms.client.connection.TcpConnector;
import com.github.error418.opennms.client.connection.UdpConnector;
import com.github.error418.opennms.client.exception.OpenNmsEventException;
import com.github.error418.opennms.client.exception.RequiredPropertyException;
import com.github.error418.opennms.client.parameter.ParameterCollection;
import com.github.error418.opennms.client.transfer.Event;
import com.github.error418.opennms.client.transfer.Log;
import com.github.error418.opennms.client.transfer.LogMessage;
import com.github.error418.opennms.client.transfer.LogMessageDestination;
import com.github.error418.opennms.client.transfer.Parameter;
import com.github.error418.opennms.client.transfer.ParameterValue;
import com.github.error418.opennms.client.transfer.Severity;

/**
 * The Event Builder assists you in building and sending an OpenNMS Event to a
 * OpenNMS server.
 * <p>
 * Events will be sent by calling the eventd daemon of OpenNMS by using a Socket
 * connection.
 * </p>
 * 
 * <p>
 * <b>OpenNMS Documentation</b>
 * </p>
 * <blockquote> The eventd process listens on port
 * 5817, so other processes, even those external to OpenNMS, can send events to
 * the system. The &lt;security&gt; tag is there so that these events cannot override
 * the actions defined in the eventconf.xml file. This way, no one with access
 * to the OpenNMS machine could send in an "autoaction" to open, say, a root
 * window on their machine. </blockquote>
 * 
 */
public class OpenNmsEventBuilder {

	private static final Logger logger = LoggerFactory.getLogger(OpenNmsEventBuilder.class);
	private static final Class<?>[] BOUND_CLASSES = new Class<?>[] { Log.class, Event.class, Parameter.class, ParameterValue.class };
	
	/**
	 * The OpenNMS standard port: {@value #ONMS_STANDARD_PORT}
	 */
	public static final int ONMS_STANDARD_PORT = 5817;

	private Log model;
	private Event event;

	/**
	 * Creates a new {@link OpenNmsEventBuilder} for easier and prettier
	 * chaining.
	 * 
	 * <p>
	 * If you do not prefer chaining you can also use the Default Constructor.
	 * </p>
	 * 
	 * @see #OpenNmsEventBuilder()
	 * @return new {@link OpenNmsEventBuilder} instance
	 */
	public static OpenNmsEventBuilder create() {
		return new OpenNmsEventBuilder();
	}

	/**
	 * Constructs a new {@link OpenNmsEventBuilder} instance.
	 * 
	 * <p>
	 * 	Sets default values for time (current time) and Severity ( {@link Severity#INDETERMINATE}}
	 * </p>
	 */
	public OpenNmsEventBuilder() {
		this.event = new Event();
		
		// set event default values for required fields
		this.event.setTime(new Date());
		this.event.setSeverity(Severity.INDETERMINATE);
		
		this.model = new Log(event);
	}

	/**
	 * Builds the XML of the current instance.
	 * 
	 * @return XML String representing the state of the current instance.
	 * @throws JAXBException if jax-b fails to marshal the message
	 */
	public String getXmlString() throws JAXBException {
		JAXBContext jc = JAXBContext.newInstance(BOUND_CLASSES);
		Marshaller m = jc.createMarshaller();

		StringWriter writer = new StringWriter();
		m.marshal(this.model, writer);

		return writer.getBuffer().toString();
	}

	/**
	 * Sends the current state of the Event to the given OpenNMS Server using
	 * the default port {@value #ONMS_STANDARD_PORT}.
	 * 
	 * @param onmsAddress
	 *            address of the OpenNMS server
	 * 
	 * @throws OpenNmsEventException
	 *             on message building exceptions
	 * @throws IOException
	 *             on socket related exceptions
	 * @throws UnknownHostException
	 *             on network/addressing errors
	 */
	public void send(InetAddress onmsAddress) throws OpenNmsEventException, ConnectorException {
		this.send(onmsAddress, ONMS_STANDARD_PORT);
	}

	/**
	 * Sends the current state of the Event to the given OpenNMS Server using
	 * the given port.
	 * 
	 * @param onmsAddress
	 *            address of the OpenNMS server
	 * @param port
	 *            port of the OpenNMS server event handling interface
	 * 
	 * @throws OpenNmsEventException
	 *             on message building exceptions, e.g. missing required event properties
	 * @throws ConnectorException
	 *             on connector related exceptions
	 * @throws UnknownHostException
	 *             on network/addressing errors
	 */
	public void send(InetAddress onmsAddress, int port) throws OpenNmsEventException, ConnectorException {
		// check if required fields have values and throw exceptions, if this is the not the case
		if (this.event.getUei() == null) {
			throw new RequiredPropertyException("UEI");
		}
		
		if (this.event.getSource() == null) {
			throw new RequiredPropertyException("source");
		}
		
		if (this.event.getTime() == null) {
			throw new RequiredPropertyException("time");
		}

		OnmsConnectionType connectionType = Configuration.instance().getConnectionType();
		try {
			Connector connector = null;
			switch(connectionType) {
				case TCP: connector = new TcpConnector(onmsAddress, port);
									break;
				case UDP: connector = new UdpConnector(onmsAddress, port);
									break;
			}
			
			connector.send(getXmlString());
		} catch (ConnectorException e) {
			logger.error("could not send event.", e);
			throw e;
		} catch (JAXBException e) {
			logger.error("failed building eventd message", e);
			throw new OpenNmsEventException("could not build eventd message", e);
		}
	}
	
	/**
	 * This Constructor is used for testing purposes.
	 * 
	 * @param model
	 */
	OpenNmsEventBuilder(Event event) {
		this.model = new Log(event);
		this.event = event;
	}

	/**
	 * Sets the OpenNMS Unique Event Identifier.
	 * <p>
	 * This property is <b>required</b>
	 * </p>
	 * 
	 * <p>
	 * <b>OpenNMS Documentation</b>
	 * </p>
	 * <blockquote> The
	 * "Universal Event Identifier" is simply a label to uniquely identify the
	 * event. The original intent was that this would be some sort of XML
	 * namespace, hence the "http://", but it really is just a label. In version
	 * 1.1 and beyond, the "http://" has been removed. Note: for internal
	 * OpenNMS events, the UEI is generated directly by the code and cannot be
	 * changed without modifying the source. </blockquote>
	 * 
	 * @param uei
	 *            OpenNMS Unique Event Identifier
	 * @return current Builder instance
	 */
	public OpenNmsEventBuilder uei(String uei) {
		this.event.setUei(uei);
		return this;
	}

	/**
	 * Sets the source NodeId of the event.
	 * 
	 * @param nodeId
	 *            The NodeId of the device that caused the event
	 * @return current Builder instance
	 */
	public OpenNmsEventBuilder nodeId(int nodeId) {
		this.event.setNodeId(nodeId);
		return this;
	}

	/**
	 * Sets the source of the event (what process).
	 * <p>
	 * This property is <b>required</b>
	 * </p>
	 * 
	 * @param source
	 *            The source of the event (what process).
	 * @return current Builder instance
	 */
	public OpenNmsEventBuilder source(String source) {
		this.event.setSource(source);
		return this;
	}

	/**
	 * Sets the source host of the event.
	 * 
	 * @param host
	 *            the source host of the event
	 * @return current Builder instance
	 */
	public OpenNmsEventBuilder host(String host) {
		this.event.setHost(host);
		return this;
	}

	/**
	 * Sets the time of the event.
	 * <p>
	 * This property is <b>required</b>
	 * </p>
	 * 
	 * @param time
	 *            the time of the event
	 * @return current Builder instance
	 */
	public OpenNmsEventBuilder time(Date time) {
		this.event.setTime(time);
		return this;
	}

	/**
	 * Sets the severity of the event.
	 * 
	 * @param severity
	 *            the severity of the event
	 * @return current Builder instance
	 */
	public OpenNmsEventBuilder severity(Severity severity) {
		this.event.setSeverity(severity);
		return this;
	}

	/**
	 * Sets the description of the event.
	 * 
	 * <b>OpenNMS Documentation</b> <blockquote> You can embed HTML entities
	 * should you wish to format the description more fully. Note that there are
	 * replacement tokens such as %interface% that you can place in the event
	 * description and log message. For more information on replacement tokens
	 * see the OpenNMS Documentation/Wiki. </blockquote>
	 * 
	 * @param description
	 *            Description of the event
	 * @return current Builder instance
	 */
	public OpenNmsEventBuilder description(String description) {
		this.event.setDescription(description);
		return this;
	}

	/**
	 * Sets the service associated with the event
	 * 
	 * @param service
	 *            the service associated with the event
	 * @return current Builder instance
	 */
	public OpenNmsEventBuilder service(String service) {
		this.event.setService(service);
		return this;
	}

	/**
	 * Sets the interface index associated with the event.
	 * 
	 * @param ifIndex the interface index
	 * @return current Builder instance
	 */
	public OpenNmsEventBuilder ifIndex(Integer ifIndex) {
		this.event.setIfIndex(ifIndex);
		return this;
	}

	/**
	 * Sets the interface associated with the event.
	 * 
	 * Consider using {@link #interfaceAddress(InetAddress)} instead of this
	 * method.
	 * 
	 * @see #interfaceAddress(InetAddress)
	 * 
	 * @param interfaceAddress
	 *            interface associated with the event.
	 * @return current Builder instance
	 * 
	 * @throws UnknownHostException
	 *             if the method was not able to resolve the supplied host name
	 */
	public OpenNmsEventBuilder interfaceAddress(String interfaceAddress) throws UnknownHostException {
		InetAddress address = InetAddress.getByName(interfaceAddress);

		this.event.setInterfaceAddress(address);
		return this;
	}

	/**
	 * Sets the interface associated with the event.
	 * 
	 * <p>
	 * Maps to the event attribute
	 * </p>
	 * <pre>
	 * interface
	 * </pre>
	 * 
	 * 
	 * @param interfaceAddress
	 *            interface associated with the event.
	 * @return current Builder instance
	 */
	public OpenNmsEventBuilder interfaceAddress(InetAddress interfaceAddress) {
		this.event.setInterfaceAddress(interfaceAddress);
		return this;
	}

	/**
	 * Sets a instructions for the NMS operator when the event occurs.
	 * 
	 * @param operationInstruction
	 *            instructions for the NMS operator when the event occurs
	 * @return current Builder instance
	 */
	public OpenNmsEventBuilder operationInstruction(String operationInstruction) {
		this.event.setOperationInstruction(operationInstruction);
		return this;
	}

	/**
	 * Is a short description or summary of the event.
	 * 
	 * @param logMessage
	 *            a short description or summary of the event
	 * @return current Builder instance
	 */
	public OpenNmsEventBuilder logMessage(String logMessage) {
		this.event.setLogMessage(new LogMessage(logMessage));
		return this;
	}

	/**
	 * Is a short description or summary of the event.
	 * 
	 * @param logMessage
	 *            a short description or summary of the event
	 * @param destination
	 *            the destination of the log message.
	 * @return current Builder instance
	 */
	public OpenNmsEventBuilder logMessage(String logMessage, LogMessageDestination destination) {
		this.event.setLogMessage(new LogMessage(logMessage, destination));
		return this;
	}

	/**
	 * Adds a parameter.
	 * 
	 * <p>
	 * <b>OpenNMS Documentation</b>
	 * </p>
	 * 
	 * <blockquote> Some events, especially SNMP
	 * traps, have additional information sent with them called
	 * "variable bindings" or "varbinds" for short. They can be accessed using
	 * the parm replacement token. Each parameter consists of a name and a
	 * value. </blockquote>
	 * 
	 * @param name
	 *            parameter name to add
	 * @param value
	 *            parameter value to add
	 * @return current Builder instance
	 */
	public OpenNmsEventBuilder parameter(String name, String value) {
		if (this.event.getParameterList() == null) {
			this.event.setParameterList(new LinkedList<Parameter>());
		}

		List<Parameter> list = this.event.getParameterList();

		Parameter parameter = new Parameter();
		parameter.setParameterName(name);
		parameter.setParameterValue(new ParameterValue(value));

		list.add(parameter);

		return this;
	}

	/**
	 * Adds a numeric parameter.
	 * <p>
	 * Note: This parameter method will use type="string" encoding="text".
	 * </p>
	 * 
	 * @see #parameter(String, String)
	 * 
	 * @param name
	 *            name of the parameter to add
	 * @param value
	 *            value of the parameter to add
	 * @return current Builder instance
	 */
	public OpenNmsEventBuilder parameter(String name, Number value) {
		return parameter(name, String.valueOf(value));
	}
	
	/**
	 * Replaces the Parameter List with the list built from a {@link ParameterCollection}.
	 * <p>
	 *  Be aware that previous configured Parameters will be lost!
	 * </p>
	 * 
	 * @param parameterCollection parameter collection
	 * @return current Builder instance
	 */
	public OpenNmsEventBuilder parameter(ParameterCollection parameterCollection) {
		LinkedList<Parameter> params = parameterCollection.buildParameterList();
		this.event.setParameterList(params);
		
		return this;
	}

}
