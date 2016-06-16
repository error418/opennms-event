# opennms-event

[![Build Status](https://travis-ci.org/error418/opennms-event.svg?branch=feature%2Fci-support)](https://travis-ci.org/error418/opennms-event)

A Java OpenNMS client for creating events via ONMS service interfaces.

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