# OpenNMS Event Client for Java

[![Build Status](https://travis-ci.org/error418/opennms-event.svg?branch=master)](https://travis-ci.org/error418/opennms-event)

A Java OpenNMS client for creating events via ONMS *eventd* daemon.

## Functional Description

This library is intended to support the developer when sending ONMS Events in Java. The library utilizes
JAX-B to generate the XML message.


### Usage

```java
OpenNmsEventBuilder.create()
	.host("host")
	.source("source")
	.time(new Date())
	.service("service")
	.description("description")
	.uei("uei")
	.send("my.opennms.server");
```

## Supported Properties

Following Event properties are supported by this project:

* uei
* source
* nodeid
* time
* host
* interface
* service
* ifIndex
* descr
* logmsg
* severity
* operinstruct
* parms

Note that the ONMS Event configuration of your OpenNMS Server may have restrictions on overriding event properties.

## Parameters

In some situations OpenNMS `parm` entries need to be supplied in a specific order. To encapsulate this knowledge from the
user of the library, a custom Parameter Collection can be used.

A custom `ParameterCollection` can look like this:

```java
public class CustomParameterCollection extends ParameterCollection {

	private final static String FIRST_PARAM = "firstParameter";
	private final static String SECOND_PARAM = "secondParameter";

	private String[] parameterOrder = { FIRST_PARAM, SECOND_PARAM };

	public CustomParameterCollection() {
		for (String parameterName : parameterOrder) {
			this.configureParameter(parameterName);
		}
	}
	
	public CustomParameterCollection setFirstParameter(String value) {
		this.setValue(FIRST_PARAM, value);
		return this;
	}

	public CustomParameterCollection setSecondParameter(Integer value) {
		this.setValue(SECOND_PARAM, value);
		return this;
	}
}
```

After writing your custom `ParameterCollection` class by extending from `ParameterCollection`, setting parameters
can for example look like this:

```java
// Using a custom ParameterCollection
OpenNmsEventBuilder.create()
	.parameter(
		new CustomParameterCollection()
			.setSecondParameter(2)
			.setFirstParameter("1")
	);
```
