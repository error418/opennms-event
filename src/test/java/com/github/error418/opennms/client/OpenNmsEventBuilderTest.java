package com.github.error418.opennms.client;

import java.nio.charset.Charset;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.github.error418.opennms.client.transfer.Severity;
import com.google.common.io.Resources;

public class OpenNmsEventBuilderTest {

	@Test
	public void testBuild() throws Exception {
		OpenNmsEventBuilder builder = OpenNmsEventBuilder.create();
		
//		<event xmlns="http://xmlns.opennms.org/xsd/event">
//		   <uei>uei.opennms.org/vendor/3Com/traps/a3ComFddiMACDuplicateAddressCondition</uei>
//		   <source>Web UI</source>
//		   <time>Wednesday, June 15, 2016 7:11:20 PM GMT</time>
//		</event>
		
		builder
				.host("abcde")
				.source("Web UI")
				.time(new Date())
				.service("SERVICE")
				.interfaceName("192.168.0.1")
				.description("EXTERNAL2 %host%")
				.logMessage("EXTERNAL %host%")
				.severity(Severity.CRITICAL)
				.uei("uei.opennms.org/event")
				.send("192.168.99.100");
		
		System.out.println(builder.getXmlString());
		
	}
}
