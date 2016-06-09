package com.github.error418.opennms.client;

import java.nio.charset.Charset;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.io.Resources;

public class OpenNmsEventBuilderTest {

	@Test
	public void testBuild() throws Exception {
		OpenNmsEventBuilder builder = OpenNmsEventBuilder.create();
		
		builder.host("host")
		.nodeId("nodeid")
		.parameter("test", "teeeest")
		.source("source")
		.time(new Date(1465499072221L)) // 1465499072221 -- Thu Jun 09 21:04:32 CEST 2016
		.uei("uei");
		
		String result = builder.getXmlString();
		
		String expected = Resources.toString(ClassLoader.class.getResource("/response-result.xml"), Charset.forName("utf-8"));
		
		Assert.assertEquals(expected.trim(), result.trim());
	}
}
