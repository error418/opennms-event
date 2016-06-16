package com.github.error418.opennms.client.transfer;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "log")
@XmlAccessorType(XmlAccessType.NONE)
public class Log {
	@XmlElementWrapper(name = "events")
	@XmlElement(name = "event")
	private List<Event> events;

	public Log() {
	}

	public Log(Event event) {
		this.events = new LinkedList<Event>();
		this.events.add(event);
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

}
