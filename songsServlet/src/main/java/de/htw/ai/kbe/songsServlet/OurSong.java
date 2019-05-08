package de.htw.ai.kbe.songsServlet;


import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "song")
public class OurSong {

    private Integer id;
	private String title;
	private String artist;
	private String album;
	private Integer released;

	public OurSong() {    
	}

	public OurSong(Integer id, String title, String artist, String album, Integer released) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.released = released;
    }
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
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

	public Integer getReleased() {
		return released;
	}

	public void setReleased(Integer released) {
        this.released = released;
    }
    
    @Override
    public String toString() {
        return "OurSong [id=" + id + ", title=" + title + ", artist=" + artist + ", album=" + album + ", released="
                + released + "]";
    }
}
