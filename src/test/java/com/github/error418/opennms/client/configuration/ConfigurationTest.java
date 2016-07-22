package com.github.error418.opennms.client.configuration;

import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

import com.github.error418.opennms.client.connection.OnmsConnectionType;

public class ConfigurationTest {

	@Test
	public void testConfigurationRead() throws Exception {
		Configuration config = Configuration.instance();
		
		Assert.assertEquals(OnmsConnectionType.UDP, config.getConnectionType());
		Assert.assertEquals("1.2.3.4", config.getOnmsServerAddress());
	}
	
	@Test
	public void testConnectionConfig() throws Exception {
		Properties prop = new Properties();
		
		prop.setProperty(Configuration.KEY_CONNECTION_TYPE, "NONSENSE");
		Configuration config = new Configuration(prop);
		Assert.assertEquals(Configuration.DEFAULT_CONNECTION_TYPE, config.getConnectionType());
		
		prop.setProperty(Configuration.KEY_CONNECTION_TYPE, "UDP");
		config = new Configuration(prop);
		Assert.assertEquals(OnmsConnectionType.UDP, config.getConnectionType());
		
		prop.setProperty(Configuration.KEY_CONNECTION_TYPE, "TCP");
		config = new Configuration(prop);
		Assert.assertEquals(OnmsConnectionType.TCP, config.getConnectionType());
	}
}
