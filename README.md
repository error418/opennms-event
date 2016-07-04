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

In some situations OpenNMS needs to be supplied `parms` in a specific order. To encapsulate the knowledge of the correct order
you can use a custom Parameter Collection by extending from `ParameterCollection`.

View the class `CustomParameterCollection` for a better understanding.

After writing your custom `ParameterCollection` class, setting parameters can for example look like this:

```java
openNmsBuilder.parameter(
		new CustomParameterCollection()
			.setFirstParameter("1")
			.setThirdParameter("3")
			.setSecondParameter(2)
);
		
```