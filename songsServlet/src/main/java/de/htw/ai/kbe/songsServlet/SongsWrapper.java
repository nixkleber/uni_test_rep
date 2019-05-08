package de.htw.ai.kbe.songsServlet;

import java.util.List;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAnyElement;

import de.htw.ai.kbe.jaxbJacksonExamples.OurSong;
 
public class SongsWrapper {
 
    private List<OurSong> songs;
 
    public SongsWrapper() {
        songs = new ArrayList<OurSong>();
    }
 
    public SongsWrapper(List<OurSong> songs) {
        this.songs = songs;
    }
 
    @XmlAnyElement(lax=true)
    public List<OurSong> getSongs() {
        return songs;
    }
 
}