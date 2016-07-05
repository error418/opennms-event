package com.github.error418.opennms.client.parameter;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.github.error418.opennms.client.transfer.Parameter;


public class ParameterCollectionTest {
	
	private ParameterCollection parameters;
	
	@Before
	public void init() {
		parameters = new CustomParameterCollection();
	}
	
	@Test
	public void testParameterOrder() throws Exception {
		List<Parameter> params = parameters.buildParameterList();
		
		Assert.assertEquals(params.get(0).getParameterName(), CustomParameterCollection.FIRST_PARAM);
		Assert.assertEquals(params.get(1).getParameterName(), CustomParameterCollection.SECOND_PARAM);
		Assert.assertEquals(params.get(2).getParameterName(), CustomParameterCollection.THIRD_PARAM);
	}
}
