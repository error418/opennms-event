# OpenNMS Event Client for Java

A Java OpenNMS client for creating events via ONMS *eventd* daemon.

[![Build Status](https://travis-ci.org/error418/opennms-event.svg?branch=master)](https://travis-ci.org/error418/opennms-event)
[![Code Climate](https://codeclimate.com/github/error418/opennms-event/badges/gpa.svg)](https://codeclimate.com/github/error418/opennms-event)
[![Maven Central](https://img.shields.io/maven-central/v/com.github.error418.opennms/event-client.svg?maxAge=2502000)](http://search.maven.org/#search%7Cga%7C1%7Ccom.github.error418.opennms.event-client)

## Notice

[A REST interface](http://docs.opennms.org/opennms/releases/17.1.1/guide-development/guide-development.html#_events) for creating events exists out-of-the-box since OpenNMS 17.1.1. You may want to use the REST interface in favor of the eventd daemon, if you are running 17.1.1 or a later version of OpenNMS.

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
## Properties

This section covers handling and setting Event properties

### Supported Properties

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


### Required Properties

Following properties need to be set for most of the Event types in order to be processed by the `eventd` daemon:

* uei
* source
* time

Make sure these properties are set before sending the event with `send()`

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
	
	@Override
	protected void validate() throws ParameterValidationException {

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

After writing your custom `ParameterCollection` class, setting parameters can for example look like this:

```java
// Using a custom ParameterCollection
OpenNmsEventBuilder.create()
	.parameter(
		new CustomParameterCollection()
			.setSecondParameter(2)
			.setFirstParameter("1")
	);
```

## Responses

Keep in mind that the OpenNMS `eventd` service does not return responses on success or error. You need to ensure your transmitted event data complies to the event schema you have configured on your OpenNMS server.

If you are running in any issues (e.g. events are not created) take a look into the `eventd` service log file.

## Get it

Using Maven

```xml
<dependency>
  <groupId>com.github.error418.opennms</groupId>
  <artifactId>event-client</artifactId>
  <version>0.2.1</version>
</dependency>
```
