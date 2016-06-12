package com.github.error418.opennms.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.error418.opennms.client.transfer.Event;
import com.github.error418.opennms.client.transfer.EventList;
import com.github.error418.opennms.client.transfer.Log;
import com.github.error418.opennms.client.transfer.Parameter;
import com.github.error418.opennms.client.transfer.ParameterList;
import com.github.error418.opennms.client.transfer.ParameterValue;

public class OpenNmsEventBuilder {

	private static final Logger logger = LoggerFactory.getLogger(OpenNmsEventBuilder.class);
	private static final Class<?>[] BOUND_CLASSES = new Class<?>[] { Log.class, EventList.class, Event.class,
			Parameter.class, ParameterList.class, ParameterValue.class };

	private Log model;
	private Event event;

	public static OpenNmsEventBuilder create() {
		return new OpenNmsEventBuilder();
	}

	public String getXmlString() throws JAXBException {
		JAXBContext jc = JAXBContext.newInstance(BOUND_CLASSES);
		Marshaller m = jc.createMarshaller();

		StringWriter writer = new StringWriter();
		m.marshal(this.model, writer);

		return writer.getBuffer().toString();
	}

	public void send(String targetAddress) throws OpenNmsEventException, IOException, UnknownHostException {
		this.send(targetAddress, 5817);
	}

	public void send(String targetAddress, int port) throws OpenNmsEventException, IOException, UnknownHostException {
		Socket socket = null;

		try {
			String data = getXmlString();
			logger.debug("open socket to {}:{}", targetAddress, port);
			socket = new Socket(targetAddress, port);
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			out.println(data);
		} catch (UnknownHostException e) {
			logger.error("Could not create socket. The host is unknown.", e);
			throw e;
		} catch (IOException e) {
			logger.error("Could not create socket", e);
			throw e;
		} catch (JAXBException e) {
			logger.error("Exception while creating event xml data", e);
			throw new OpenNmsEventException("Exception while creating event xml data", e);
		} finally {
			try {
				if (socket != null) {
					socket.close();
				}
			} catch (IOException e) {
				logger.error("Exception while closing socket", e);
				throw e;
			}
		}
	}

	private OpenNmsEventBuilder() {
		this.model = new Log();
		this.event = new Event();

		EventList eventList = new EventList();
		eventList.getEvents().add(event);

		this.model.setEvents(eventList);
	}

	public OpenNmsEventBuilder uei(String uei) {
		this.event.setUei(uei);
		return this;
	}

	public OpenNmsEventBuilder nodeId(String nodeId) {
		this.event.setNodeId(nodeId);
		return this;
	}

	public OpenNmsEventBuilder source(String source) {
		this.event.setNodeId(source);
		return this;
	}

	public OpenNmsEventBuilder host(String host) {
		this.event.setHost(host);
		return this;
	}

	public OpenNmsEventBuilder time(Date time) {
		this.event.setTime(time);
		return this;
	}

	public OpenNmsEventBuilder severity(String severity) {
		this.event.setSeverity(severity);
		return this;
	}

	public OpenNmsEventBuilder description(String description) {
		this.event.setDescription(description);
		return this;
	}

	public OpenNmsEventBuilder service(String service) {
		this.event.setService(service);
		return this;
	}

	public OpenNmsEventBuilder ifIndex(Integer ifIndex) {
		this.event.setIfIndex(ifIndex);
		return this;
	}

	public OpenNmsEventBuilder interfaceName(String interfaceName) {
		this.event.setInterfaceName(interfaceName);
		return this;
	}

	public OpenNmsEventBuilder operationInstruction(String operationInstruction) {
		this.event.setOperationInstruction(operationInstruction);
		return this;
	}

	public OpenNmsEventBuilder logMessage(String logMessage) {
		this.event.setLogMessage(logMessage);
		return this;
	}

	public OpenNmsEventBuilder parameter(String name, String value) {
		if (this.event.getParameterList() == null) {
			this.event.setParameters(new ParameterList());
		}

		List<Parameter> list = this.event.getParameterList().getParameters();

		Parameter parameter = new Parameter();
		parameter.setParameterName(name);
		parameter.setValue(new ParameterValue(value));

		list.add(parameter);

		return this;
	}
}
