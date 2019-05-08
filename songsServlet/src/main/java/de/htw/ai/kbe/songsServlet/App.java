package de.htw.ai.kbe.songsServlet;

import java.util.List;

import de.htw.ai.kbe.jaxbJacksonExamples.OurSong;
import de.htw.ai.kbe.jaxbJacksonExamples.SongsWrapper;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class App 
{
    public static void main( String[] args )
    {
    	try 
    	{
            List<OurSong> readSongs = readXMLToSongs("songs.xml");
            readSongs.forEach(s -> {
                System.out.println(s.getTitle());
            });
        } 
    	catch (Exception e) 
    	{
    		
    	}
    }

	private static List<OurSong> unmarshal(Unmarshaller unmarshaller, Class<OurSong> clazz, String xmlLocation)throws JAXBException 
	{
	    StreamSource xml = new StreamSource(xmlLocation);
	    SongsWrapper wrapper = (SongsWrapper) unmarshaller.unmarshal(xml, SongsWrapper.class).getValue();
	    return wrapper.getSongs();
	}
	
	private static List<OurSong> readXMLToSongs(String filename) throws JAXBException, FileNotFoundException, IOException 
	{
	    JAXBContext context = JAXBContext.newInstance(SongsWrapper.class, OurSong.class);
	    Unmarshaller unmarshaller = context.createUnmarshaller();
	    try (InputStream is = new BufferedInputStream(new FileInputStream(filename))) {
	        List<OurSong> songs = unmarshal(unmarshaller, OurSong.class, filename);
	        return songs;
	    }
	}
}