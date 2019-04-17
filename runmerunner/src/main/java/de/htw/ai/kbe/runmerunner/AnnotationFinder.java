package de.htw.ai.kbe.runmerunner;

import static org.junit.Assert.assertNotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.junit.Test;

public class AnnotationFinder {

	private ArrayList<Method> otherMethods = new ArrayList();
	private ArrayList<Method> runMeMethods = new ArrayList();
	
	
	
	public AnnotationFinder(Class someClass) 
	{
		for(Method method : someClass.getDeclaredMethods())
		{
			Annotation[] annotations = method.getAnnotations();
			for(Annotation annotation: annotations)
			{
				if(annotation instanceof RunMe)
				{
					runMeMethods.add(method);
				}
				else
				{
					otherMethods.add(method);
				}
			}
		}
	}
	
	
	public ArrayList<Method> getRunMeMethods() 
	{
        return runMeMethods;
	}
	
	public ArrayList<Method> getOtherMethods() 
	{
        return otherMethods;
	}
}
