# opennms-event

[![Build Status](https://travis-ci.org/error418/opennms-event.svg?branch=feature%2Fci-support)](https://travis-ci.org/error418/opennms-event)

A Java OpenNMS client for creating events via ONMS service interfaces.

## Functional Description

This library is intended to support the developer when sending ONMS Events. The library utilizes
JAX-B to generate the XML message.

A Builder class instance can be used to create a OpenNmsEvent object instance and send the data to an
OpenNMS Server in the network.

´´´java
builder.host("examplehost.example")
		.nodeId("Node ID")
		.parameter("parameterA", "1")
		.parameter("parameterB", "2")
		.parameter("parameterC", "3")
		.source("source")
		.time(new Date(1465499072221L)) // 1465499072221 -- Thu Jun 09 21:04:32 CEST 2016
		.uei("uei");
´´´