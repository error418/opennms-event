package com.github.error418.opennms.client.transfer;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Deprecated
@XmlRootElement(name = "events")
@XmlAccessorType(XmlAccessType.NONE)
public class EventList {
	@XmlElement(name = "event")
	private List<Event> events;

	public EventList() {
		this.events = new LinkedList<Event>();
	}

	public EventList(List<Event> events) {
		this.events = events;
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

}
