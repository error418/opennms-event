package com.github.error418.opennms.client.parameter;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.github.error418.opennms.client.exception.ParameterValidationException;
import com.github.error418.opennms.client.parameter.collection.CustomParameterCollection;
import com.github.error418.opennms.client.parameter.collection.DefaultValueParameterCollection;
import com.github.error418.opennms.client.parameter.collection.FailParameterCollection;
import com.github.error418.opennms.client.transfer.Parameter;


public class ParameterCollectionTest {
	
	private CustomParameterCollection parameters;
	
	@Before
	public void init() {
		parameters = new CustomParameterCollection();
		parameters.setFirstParameter("this is a required parameter");
	}
	
	@Test
	public void testParameterOrder() throws Exception {
		List<Parameter> params = parameters.buildParameterList();
		
		Assert.assertEquals(params.get(0).getParameterName(), CustomParameterCollection.FIRST_PARAM);
		Assert.assertEquals(params.get(1).getParameterName(), CustomParameterCollection.SECOND_PARAM);
		Assert.assertEquals(params.get(2).getParameterName(), CustomParameterCollection.THIRD_PARAM);
	}
	
	@Test
	public void testDefaultValuesAndAccessors() throws Exception {
		DefaultValueParameterCollection collection = new DefaultValueParameterCollection();
		
		Assert.assertEquals("1", collection.getValue("one"));
		Assert.assertEquals("2", collection.getValue("two"));
		Assert.assertTrue(collection.isParameterNull("tree"));
	}
	
	@Test(expected=ParameterValidationException.class)
	public void testValidationException() throws Exception {
		parameters.setFirstParameter(null);
		parameters.buildParameterList();
	}
	
	@Test
	public void testWithoutValidationException() throws Exception {
		parameters.setFirstParameter("it's okay now");
		parameters.buildParameterList();
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testDuplicateConfiguration() throws Exception {
		new FailParameterCollection();
	}
	
	@Test
	public void testIsNull() throws Exception {
		parameters.setFirstParameter(null);
		Assert.assertTrue(parameters.isParameterNull(CustomParameterCollection.FIRST_PARAM));
		
		parameters.setFirstParameter("not null anymore!");
		Assert.assertFalse(parameters.isParameterNull(CustomParameterCollection.FIRST_PARAM));
	}
	
	@Test
	public void testGetValue() throws Exception {
		parameters.setFirstParameter("Some value");
		Assert.assertEquals("Some value", parameters.getValue(CustomParameterCollection.FIRST_PARAM));
		
		parameters.setFirstParameter("Another value");
		Assert.assertEquals("Another value", parameters.getValue(CustomParameterCollection.FIRST_PARAM));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testUnknownParameter() throws Exception {
		parameters.setValue("this is an unknown parameter", "fail");
	}
}
