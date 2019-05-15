package de.htw.ai.kbe.songsServlet;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;



public class mySongServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	List<OurSong> readSongs;	
	
	private String uriToDB = null;

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
	    // Beispiel: Laden eines Konfigurationsparameters aus der web.xml
		this.uriToDB = servletConfig.getInitParameter("uriToDBComponent");
	}
	
	public mySongServlet() 
	{
    	try 
    	{
    		readSongs = readXMLToSongs("songs.xml");
        	ObjectMapper mapper = new ObjectMapper();
        	
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
	}
	
	@Override
	public void doGet(HttpServletRequest request, 
	        HttpServletResponse response) throws IOException {
		// alle Parameter (keys)
		
		
		ObjectMapper mapper = new ObjectMapper();
		OurSong responseObj = null;
		String jsonString = "";
		
		
		Enumeration<String> paramNames = request.getParameterNames();
		List<String> param = new ArrayList();
		while (paramNames.hasMoreElements()) {
			param.add(paramNames.nextElement());
		}
		
		Enumeration<String> headerNames = request.getHeaderNames();
		List<String> header = new ArrayList<>();
		while (headerNames.hasMoreElements()) {
			header.add(headerNames.nextElement());
		}
		
		if(header.isEmpty() || (header.contains("Accept") && request.getHeader("Accept").equals("application/json")))
		{
			if(param.isEmpty())
			{
				try(PrintWriter out = response.getWriter()) 
				{
					for(OurSong song : readSongs)
					{
				    	jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(song);
				    	out.println(jsonString);
					}
				}
			}
			else if(param.contains("songId"))
			{
				if(request.getAttribute("songId") != null)
				{
					try(PrintWriter out = response.getWriter()) 
					{
				    	responseObj = readSongs.get((int)request.getAttribute("songId") - 1);
				    	jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseObj);
				    	out.println(jsonString);
					}
					catch(ClassCastException e)
					{
						//falscher value
					}
					catch(IndexOutOfBoundsException e)
					{
						//kein Song mit der nummer vorhanden
					}
					catch(Exception e)
					{
						System.out.println(e.getMessage());
					}
				}
				else
				{
					System.out.println();
				}
			}
			else
			{
				//falsche/unbekannte parameter angabe
				response.setStatus(400);
				PrintWriter pr = response.getWriter();
				pr.print(response.getStatus());
			}
		}
		else
		{
			//nicht unterstützter Accept-Header-Wert
			response.setStatus(406);
			PrintWriter pr = response.getWriter();
			pr.print(response.getStatus());
		}
		
		
		
		/*response.setContentType("text/plain");
		try (PrintWriter out = response.getWriter()) {
			out.println(jsonString);
		}*/
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/plain");
		ServletInputStream inputStream = request.getInputStream();
		byte[] inBytes = IOUtils.toByteArray(inputStream);
		try (PrintWriter out = response.getWriter()) {
			out.println(new String(inBytes));
		}
	}
	
	protected String getUriToDB () {
		return this.uriToDB;
	}
	
	private List<OurSong> unmarshal(Unmarshaller unmarshaller, Class<OurSong> clazz, String xmlLocation)throws JAXBException 
	{
	    StreamSource xml = new StreamSource(xmlLocation);
	    SongsWrapper wrapper = (SongsWrapper) unmarshaller.unmarshal(xml, SongsWrapper.class).getValue();
	    return wrapper.getSongs();
	}
	
	private List<OurSong> readXMLToSongs(String filename) throws JAXBException, FileNotFoundException, IOException 
	{
	    JAXBContext context = JAXBContext.newInstance(SongsWrapper.class, OurSong.class);
	    Unmarshaller unmarshaller = context.createUnmarshaller();
	    try (InputStream is = new BufferedInputStream(new FileInputStream(filename))) {
	        List<OurSong> songs = unmarshal(unmarshaller, OurSong.class, filename);
	        return songs;
	    }
	}
	
	private void readJSONToSongs(String jsonObj) 
	{
        ObjectMapper objectMapper = new ObjectMapper();
        try {
			OurSong o = objectMapper.readValue(jsonObj, OurSong.class);
			
			int i = readSongs.size() +1;
			boolean b = true;
			while(b)
			{
				for(OurSong os : readSongs)
				{
					if(os.getId() == i)
					{
						b = false;
					}
				}
				if(!b) 
				{
					i++;
					b = true;
				}
				else
				{
					o.setId(i);
				}
				
			}
			
			readSongs.add(o);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
