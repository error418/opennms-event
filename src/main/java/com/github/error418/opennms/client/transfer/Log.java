package com.github.error418.opennms.client.transfer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "log")
@XmlAccessorType(XmlAccessType.NONE)
public class Log {
	@XmlElement(name = "events")
	private EventList eventList;

	public EventList getEventList() {
		return eventList;
	}

	public void setEvents(EventList events) {
		this.eventList = events;
	}

}
