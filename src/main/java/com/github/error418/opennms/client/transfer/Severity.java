package com.github.error418.opennms.client.transfer;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * Represents OpenNMS Severity Levels. The severity level Enum JavaDocs are
 * taken from the OpenNMS documentation.
 */
@XmlType(name = "severity")
@XmlEnum
public enum Severity {
	/**
	 * This event means numerous devices on the network are affected by the
	 * event. Everyone who can should stop what they are doing and focus on
	 * fixing the problem.
	 */
	@XmlEnumValue("7") CRITICAL(7),

	/**
	 * A device is completely down or in danger of going down. Attention needs
	 * to be paid to this problem immediately.
	 */
	@XmlEnumValue("6") MAJOR(6),

	/**
	 * A part of a device (a service, and interface, a power supply, etc.) has
	 * stopped functioning. The device needs attention.
	 */
	@XmlEnumValue("5") MINOR(5),

	/**
	 * An event has occurred that may require action. This severity can also be
	 * used to indicate a condition that should be noted (logged) but does not
	 * require direct action.
	 */
	@XmlEnumValue("4") WARNING(4),

	/**
	 * Informational message. No action required.
	 */
	@XmlEnumValue("3") NORMAL(3),

	/**
	 * This severity is reserved for use in Alarms to indicate that an alarm
	 * describes a self-clearing error condition has been corrected and service
	 * is restored. This severity should never be used in event definitions.
	 * 
	 * Please use "Normal" severity for events that clear an alarm.
	 */
	@XmlEnumValue("2") CLEARED(2),

	/**
	 * No Severity could be associated with this event.
	 */
	@XmlEnumValue("1") INDETERMINATE(1);

	private int severityValue;

	Severity(int severityValue) {
		this.severityValue = severityValue;
	}

	/**
	 * Gets the level value of the Enum
	 * 
	 * @return OpenNMS level identifier
	 */
	public int value() {
		return this.severityValue;
	}
}