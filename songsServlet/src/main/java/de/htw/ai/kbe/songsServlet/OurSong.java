package de.htw.ai.kbe.songsServlet;


import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "song")
@Entity
@Table(name = "song")
public class OurSong implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
    private int id;
	private String title;
	private String artist;
	private String album;
	private int released;

	public OurSong() {    
	}

	public OurSong(int id, String title, String artist, String album, int released) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.released = released;
    }
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
		return title;
	}
	
    public void setTitle(String title) {
        this.title = title;
    }

	public String getArtist() {
		return artist;
	}
	
	public void setArtist(String artist) {
        this.artist = artist;
    }

	public String getAlbum() {
		return album;
	}
	
	public void setAlbum(String album) {
        this.album = album;
    }

	public int getReleased() {
		return released;
	}

	public void setReleased(int released) {
        this.released = released;
    }
    
    @Override
    public String toString() {
        return "OurSong [id=" + id + ", title=" + title + ", artist=" + artist + ", album=" + album + ", released="
                + released + "]";
    }
}
