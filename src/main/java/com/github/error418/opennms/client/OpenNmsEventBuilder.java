package com.github.error418.opennms.client;

import java.io.StringWriter;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.github.error418.opennms.client.transfer.Event;
import com.github.error418.opennms.client.transfer.EventList;
import com.github.error418.opennms.client.transfer.Log;
import com.github.error418.opennms.client.transfer.Parameter;
import com.github.error418.opennms.client.transfer.ParameterList;
import com.github.error418.opennms.client.transfer.ParameterValue;

public class OpenNmsEventBuilder {

	private static final Class<?>[] BOUND_CLASSES = new Class<?>[]{Log.class, EventList.class, Event.class, Parameter.class, ParameterList.class, ParameterValue.class };
	
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

	public void send(String targetUrl, String username, String password) {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(targetUrl).path("/");

		String authHeaderName = "Authorization";
        String authHeaderValue = "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes() );
 
		
		target
			.request(MediaType.APPLICATION_JSON_TYPE)
			.header(authHeaderName, authHeaderValue)
			.post(Entity.entity(this.model, MediaType.APPLICATION_XML));
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
