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

## Configuration

Some repeating values can be set in a seperate configuration file, i.e. server address or the connection type.

Just place a file named `onms-event.properties` in the classpath and set the values inside.
For example:

	onmsclient.connection.type = UDP
	onmsclient.connection.address = 192.168.0.1
	onmsclient.connection.port = 5817
	
By using a configuration file you are able to use the parameterless `send()` method signature.
	
### onmsclient.connection.type

Can have the values `UDP` or `TCP`.

### onmsclient.connection.address

Configures the server address to use.