package com.github.error418.opennms.client;

import java.nio.charset.Charset;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.io.Resources;

public class OpenNmsEventBuilderTest {

	@Test
	public void testBuild() throws Exception {
		OpenNmsEventBuilder builder = OpenNmsEventBuilder.create();
		
		GregorianCalendar cal = new GregorianCalendar(TimeZone.getTimeZone("CEST"));
		
		cal.setTimeInMillis(1465499072221L); //  -- Thu Jun 09 21:04:32 CEST 2016
		
		builder.host("examplehost.example")
				.nodeId("Node ID")
				.parameter("parameterA", "1")
				.parameter("parameterB", "2")
				.parameter("parameterC", "3")
				.source("source")
				.time(cal.getTime()) 
				.uei("uei");
		
		String result = builder.getXmlString();
		
		String expected = Resources.toString(ClassLoader.class.getResource("/response-result.xml"), Charset.forName("utf-8"));
		
		Assert.assertEquals(expected.trim(), result.trim());
	}
}
