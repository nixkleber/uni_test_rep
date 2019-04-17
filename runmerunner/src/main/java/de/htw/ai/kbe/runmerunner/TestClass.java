package de.htw.ai.kbe.runmerunner;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class TestClass {

	
	@RunMe
	public void test1()
	{
		
	}
	
	@Test
	public void testGetRunMeMethods()
	{
		assertNotNull("TEST_CASE_RUN_ME", new AnnotationFinder(Class.class).getRunMeMethods());
	}
}
