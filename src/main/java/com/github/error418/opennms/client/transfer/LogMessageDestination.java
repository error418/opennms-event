package com.github.error418.opennms.client.transfer;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * Represents the log destination 
 */
@XmlType(name = "destination")
@XmlEnum
public enum LogMessageDestination {

	/**
	 * Both log the event in the database and display it in the Web UI.
	 */
	@XmlEnumValue("logndisplay")
  LOG_AND_DISPLAY("logndisplay"),
  
  /**
   * Log the event in the database, but do not display it.
   */
  @XmlEnumValue("logonly")
  LOG_ONLY("logonly"),
  
  /**
   * Neither log the event in the database or display it.
   */
  @XmlEnumValue("suppress")
  SUPPRESS("suppress"), 

  /**
   * Do not log the event in the database, but still send it to OpenNMS daemons that are listening
   * for this type of event (e.g.: this can be used to generate notifications).
   * Note that the eventd and alarmd don't process this type of events.
   */
  @XmlEnumValue("donotpersist")
  DO_NOT_PERSIST("donotpersist"), 
  
  /**
   * This only applies to traps coming in via trapd. This will cause trapd to discard the trap without creating an event.
   * Other OpenNMS daemons that are listening for this type of event will not receive this event.
   * This feature was first available in OpenNMS 1.3.0.
   */
  @XmlEnumValue("discardtraps")
  DISCARD_TRAPS("discardtraps"); 
	
	private String value;

	private LogMessageDestination(String value) {
		this.value = value;
	}
	
	/**
	 * Gets the destination value of the Enum
	 * 
	 * @return OpenNMS destination identifier
	 */
	public String value() {
		return this.value;
	}
}
