package de.htw.ai.kbe.runmerunner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class App 
{
	static CommandLine cmd = null;
	
    public static void main(String[] args )
    {
    	if(validate(args))
    		runner(args);
    }
    
    public static boolean validate(String[] args)
    {
    	Options options = new Options();
    	//OptionGroup optiongroup = new OptionGroup();
    	Option c = new Option("c", true, "class to look fo the Annotation");
    	Option o = new Option("o", true, "name for the report file");;
    	
    	c.setRequired(true);
    	o.setRequired(true);
    	
    	options.addOption(c);
    	options.addOption(o);
    	
    	CommandLineParser parser = new DefaultParser();
    	try {
			cmd = parser.parse( options, args);
		} catch (ParseException e) {
			new HelpFormatter().printHelp("java de.htw.ai.kbe.runmerunner.App","", options, "-c, -o bitte angeben"/*e.getMessage()*/, true);
			return false;
		}
    	return true;
    }
    
    public static void runner(String args[])
    {
//    	File f = new File("user.dir");
//    	URL[] cp = {f.toURI().toURL()};
//    	URLClassLoader urlcl = new URLClassLoader(cp);
//    	Class clazz = urlcl.loadClass("distantinterfaces.DistantClass");
    	
//    	try {
//			//Class clazz = Class.forName(args[0].substring(0, args[0].lastIndexOf('.')));
//    		String userDir = System.getProperty("user.dir");
//    		Class clazz = Class.forName(userDir.replace("\\", ".") + "/" + "annotations_test");
//		} catch (ClassNotFoundException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}catch(IndexOutOfBoundsException e2) {
//			e2.printStackTrace();
//		}
    	
    	
//     	String className = args[1];
//        Class<?> clazz = null;
//		try {
//			clazz = Class.forName("de.htw.ai.kbe.runmerunner." + className);
//		} catch (ClassNotFoundException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//        try {
//			Object obj = clazz.newInstance();
//		} catch (InstantiationException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		} catch (IllegalAccessException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//    	
//    	String classPath = System.getProperty("user.dir") + File.separator + args[0];
//        System.out.println("classPath:" + System.getProperty("user.dir") + File.separator + args[1]);
//    	int extensionIndex = args[0].lastIndexOf('.');
//    	System.out.println("extensionIndex"+ args[1].lastIndexOf('.'));
//        
//
//        //In case no extension was provided
//        if(extensionIndex == -1) {
//            System.out.println("Please provide a file with an extension.");
//            System.exit(0);
//        }
//
//        //Build class name
//        String className = args[0].substring(0, extensionIndex);
//
//        Class targetClass = null;
//        try {
//            targetClass = ClassLoader.loadClass(classPath, className);
//        } catch (ClassNotFoundException e) {
//            System.out.println("Class " + args[0] + " not found.");
//            System.exit(1);
//        } catch (MalformedURLException e) {
//            System.out.println("Malformed URL encountered. Exiting...");
//            System.exit(1);
//        } catch (FileNotFoundException e) {
//            System.out.println(e.getMessage());
//            System.exit(1);
//        }
  
    	Class clazz = null;
    	
    	try {
			clazz = Class.forName(args[1]);
		} catch (ClassNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
    	
    	AnnotationFinder af = new AnnotationFinder(clazz);
    	ArrayList<Method> notInvokeList = new ArrayList<Method>();
    	
    	if(cmd.hasOption("c") && cmd.hasOption("o")) {

        	// write report to file
        	PrintStream fileOut = null;
    		try {
    			fileOut = new PrintStream(args[3]);
    		} catch (FileNotFoundException e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    		}
        	System.setOut(fileOut);
        	
        	
        	System.out.println("Methodennamen ohne @RunMe:");
        	for (Method method : af.getOtherMethods()) {
    			System.out.println("    " + method.getName());
    		}
        	
        	System.out.println("Methodennamen mit @RunMe:");
        	for (Method method : af.getRunMeMethods()) {
    			System.out.println("    " + method);
    		}
        	
        	System.out.println("\"Nicht-invokierbare\" Methoden mit @RunMe");
        	for (Method method : notInvokeList) {
    			System.out.println("    " + method);
    		} 
        	
        	//invoke all RunME methods
        	for(Method m: af.getRunMeMethods())
        	{
        		Class c1 = m.getClass();
        		
    				try {
    					m.invoke(c1);
    				} catch (IllegalAccessException e) {
    					notInvokeList.add(m);
    				} catch (IllegalArgumentException e) {
    					// TODO Auto-generated catch block
    					notInvokeList.add(m);
    				} catch (InvocationTargetException e) {
    					// TODO Auto-generated catch block
    					notInvokeList.add(m);
    				}
        	}
    	}
    	
    	
    	
//    	//invoke all other methodsprinprint the dateprint the dateprint the datet the date
//    	for(Method m: af.getOtherMethods())
//    	{
//    		Class c1 = m.getClass();
//    		
//			try {
//				m.invoke(c1);
//			} catch (IllegalAccessException e) {
//				notInvokeList.add(m);
//			} catch (IllegalArgumentException e) {
//				// TODO Auto-generated catch block
//				notInvokeList.add(m);
//			} catch (InvocationTargetException e) {
//				// TODO Auto-generated catch block
//				notInvokeList.add(m);
//			}
//    	}
    	
    }
    
 
}
