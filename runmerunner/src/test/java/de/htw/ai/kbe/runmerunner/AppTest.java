package de.htw.ai.kbe.runmerunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
    
{
	Class testClass;
	AnnotationFinderTest afTest;

	/**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }

    @Test()
    public void testGetRunMeMethods()
    {
    	afTest = new AnnotationFinderTest();
    	
    	AnnotationFinder af = new AnnotationFinder(afTest.getClass());
    	assertTrue(!af.getRunMeMethods().isEmpty());
    }
    
//    @Test()
//    public void testGetOtherMethods()
//    {
//    	afTest = new AnnotationFinderTest();
//    	
//    	AnnotationFinder af = new AnnotationFinder(afTest.getClass());
//    	assertTrue(!af.getOtherMethods().isEmpty());
//    }
    
//    @Test()
//    public void testNonInvocableMethods()
//    {
//    	
//    }
    
}
