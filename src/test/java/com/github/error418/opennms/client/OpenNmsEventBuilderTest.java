package com.github.error418.opennms.client;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.github.error418.opennms.client.exception.RequiredPropertyException;
import com.github.error418.opennms.client.parameter.collection.CustomParameterCollection;
import com.github.error418.opennms.client.transfer.Event;
import com.github.error418.opennms.client.transfer.LogMessage;
import com.github.error418.opennms.client.transfer.LogMessageDestination;
import com.github.error418.opennms.client.transfer.Severity;
import com.github.error418.opennms.client.transfer.adapter.DateAdapter;

public class OpenNmsEventBuilderTest {

	Event event;

	@Before
	public void buildSpy() {
		event = Mockito.spy(new Event());
	}

	@Test
	public void testBuilderMappings() throws Exception {
		OpenNmsEventBuilder builder = new OpenNmsEventBuilder(event);

		builder.host("host");
		Mockito.verify(event, Mockito.times(1)).setHost(Mockito.eq("host"));

		builder.source("source");
		Mockito.verify(event, Mockito.times(1)).setSource(Mockito.eq("source"));

		Date now = new Date();
		builder.time(now);
		Mockito.verify(event, Mockito.times(1)).setTime(Mockito.eq(now));
		Assert.assertEquals(now, event.getTime());
		Assert.assertNotSame(now, event.getTime());

		builder.service("service");
		Mockito.verify(event, Mockito.times(1)).setService(Mockito.eq("service"));

		builder.interfaceAddress("127.0.0.1");
		Mockito.verify(event, Mockito.times(1)).setInterfaceAddress(Mockito.any(InetAddress.class));
		Assert.assertEquals("127.0.0.1", event.getInterfaceAddress().getHostAddress());

		builder.description("description");
		Mockito.verify(event, Mockito.times(1)).setDescription(Mockito.eq("description"));

		builder.logMessage("logmsg");
		Mockito.verify(event, Mockito.times(1)).setLogMessage(Mockito.any(LogMessage.class));
		Assert.assertEquals("logmsg", event.getLogMessage().getValue());

		builder.logMessage("logmsg", LogMessageDestination.DISCARD_TRAPS);
		Assert.assertEquals(LogMessageDestination.DISCARD_TRAPS, event.getLogMessage().getDestination());

		builder.severity(Severity.INDETERMINATE);
		Mockito.verify(event, Mockito.times(1)).setSeverity(Mockito.eq(Severity.INDETERMINATE));

		builder.uei("uei");
		Mockito.verify(event, Mockito.times(1)).setUei(Mockito.eq("uei"));

		builder.nodeId(1);
		Mockito.verify(event, Mockito.times(1)).setNodeId(Mockito.eq(1));

		builder.operationInstruction("operinstruct");
		Mockito.verify(event, Mockito.times(1)).setOperationInstruction(Mockito.eq("operinstruct"));
	}
	
	@Test
	public void testParameterCollectionOrder() throws Exception {
		
		OpenNmsEventBuilder builder = new OpenNmsEventBuilder(event);
		builder.parameter(
				new CustomParameterCollection()
					.setThirdParameter("3")
					.setSecondParameter(2)
					.setFirstParameter("1")
		);
		
		Assert.assertEquals("1", event.getParameterList().get(0).getParameterValue().getValue());
		Assert.assertEquals("2", event.getParameterList().get(1).getParameterValue().getValue());
		Assert.assertEquals("3", event.getParameterList().get(2).getParameterValue().getValue());
	}


	//////////////////////////////////////////////////////////////////////////////////////////////
	// Validation Tests
	//////////////////////////////////////////////////////////////////////////////////////////////

	@Test(expected = RequiredPropertyException.class)
	public void testValidationOnDirectSend() throws Exception {
		OpenNmsEventBuilder.create().send(InetAddress.getLoopbackAddress());
	}

	@Test
	public void testValidationUeiIsNull() throws Exception {
		try {
		OpenNmsEventBuilder.create()
			.uei(null)
			.time(new Date())
			.source("some source")
			.send(InetAddress.getLoopbackAddress());
		
			Assert.fail();
		}
		catch (RequiredPropertyException e) {
			Assert.assertEquals(e.getPropertyName(), "UEI");
		}
	}
	
	@Test
	public void testValidationTimeIsNull() throws Exception {
		try {
		OpenNmsEventBuilder.create()
			.uei("some uei")
			.source("some source")
			.time(null)
			.send(InetAddress.getLoopbackAddress());
		
			Assert.fail();
		}
		catch (RequiredPropertyException e) {
			Assert.assertEquals(e.getPropertyName(), "time");
		}
	}
	
	@Test
	public void testValidationSourceIsNull() throws Exception {
		try {
		OpenNmsEventBuilder.create()
			.uei("some uei")
			.source(null)
			.time(new Date())
			.send(InetAddress.getLoopbackAddress());
		
			Assert.fail();
		}
		catch (RequiredPropertyException e) {
			Assert.assertEquals(e.getPropertyName(), "source");
		}
	}

	//////////////////////////////////////////////////////////////////////////////////////////////
	// XML Tests
	//////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testXml() throws Exception {

		final String host = "HOST";
		final String source = "SOURCE";
		final String service = "SERVICE";
		final Date time = new Date();
		final String interfaceAddress = "127.0.0.1";
		final String description = "DESCRIPTION";
		final String logMessage = "LOGMESSAGE";
		final String uei = "UEI";
		final int nodeId = 1337;
		final String operInstruct = "OPERINSTRUCT";

		final String parameterName1 = "testNumber";
		final int parameterValue1 = 1337;
		final String parameterName2 = "anotherTestNumber";
		final double parameterValue2 = 23.0;

		DateAdapter dateAdapter = new DateAdapter();

		String xml = OpenNmsEventBuilder.create()
				.host(host)
				.source(source)
				.time(time)
				.service(service)
				.interfaceAddress(interfaceAddress)
				.description(description)
				.logMessage(logMessage, LogMessageDestination.DISCARD_TRAPS)
				.severity(Severity.INDETERMINATE)
				.uei(uei)
				.nodeId(nodeId)
				.operationInstruction(operInstruct)
				.parameter(parameterName1, parameterValue1)
				.parameter(parameterName2, parameterValue2)
				.getXmlString();

		Assert.assertNotNull(xml);
		Assert.assertThat(xml.length(), Matchers.greaterThan(0));
		Assert.assertThat(xml, Matchers.startsWith("<?xml"));

		Assert.assertThat(xml, Matchers.containsString(host));
		Assert.assertThat(xml, Matchers.containsString(source));
		Assert.assertThat(xml, Matchers.containsString(service));
		Assert.assertThat(xml, Matchers.containsString(dateAdapter.marshal(time)));
		Assert.assertThat(xml, Matchers.containsString(interfaceAddress));
		Assert.assertThat(xml, Matchers.containsString(description));
		Assert.assertThat(xml, Matchers.containsString(logMessage));
		Assert.assertThat(xml, Matchers.containsString(uei));
		Assert.assertThat(xml, Matchers.containsString(String.valueOf(nodeId)));
		Assert.assertThat(xml, Matchers.containsString(operInstruct));

		Assert.assertThat(xml, Matchers.containsString(parameterName1));
		Assert.assertThat(xml, Matchers.containsString(parameterName2));

		Assert.assertThat(xml, Matchers.containsString(String.valueOf(parameterValue1)));
		Assert.assertThat(xml, Matchers.containsString(String.valueOf(parameterValue2)));
	}

	@Test(expected = UnknownHostException.class)
	public void testInterfaceAddressException() throws Exception {
		OpenNmsEventBuilder
			.create()
			.interfaceAddress("192.168.0.500");
	}
	
}
