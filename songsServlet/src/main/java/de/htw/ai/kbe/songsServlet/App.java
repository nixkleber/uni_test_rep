package de.htw.ai.kbe.songsServlet;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import com.fasterxml.jackson.databind.ObjectMapper;

public class App {

	private static final String PERSISTENCE_UNIT_NAME = "songDB";
	private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("songDB");
	
	public static void main(String[] args) {

		List<OurSong> readSongs;
		
    	try 
    	{
    		readSongs = readXMLToSongs("resources/songs.xml");
        	ObjectMapper mapper = new ObjectMapper();
    		System.out.println(readSongs.toString());

        	
        } 
    	catch(FileNotFoundException e)
    	{
    		readSongs = null;
    		System.out.println(e);
    	}
    	catch (Exception e) 
    	{
    		System.out.println(e);
    	}
    	
    	addSong(20,"Sunny","Stanley Turrentine","The Sp", 2003);
    	

	}
	
	
    public static void addSong(int id, String title, String artist, String album, int released) {
        // The EntityManager class allows operations such as create, read, update, delete
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        // Used to issue transactions on the EntityManager
        EntityTransaction et = null;
 
        try {
            // Get transaction and start
            et = em.getTransaction();
            et.begin();
 
            // Create and set values for new customer
            OurSong song = new OurSong();
            song.setId(id);
            song.setTitle(title);
            song.setAlbum(album);
            song.setArtist(artist);
            song.setReleased(released);
 
            // Save the customer object
            em.persist(song);
            et.commit();
        } catch (Exception ex) {
            // If there is an exception rollback changes
            if (et != null) {
                et.rollback();
            }
            ex.printStackTrace();
        } finally {
            // Close EntityManager
            em.close();
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
