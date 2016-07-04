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

After writing your custom `ParameterCollection` class by extending from `ParameterCollection`, setting parameters
can for example look like this:

```java
OpenNmsEventBuilder.create()
	.parameter(
		new CustomParameterCollection()
			.setFirstParameter("1")
			.setThirdParameter("3")
			.setSecondParameter(2)
	);
		
```

In case you need some examples, the class `CustomParameterCollection` can be viewed for a better understanding.
