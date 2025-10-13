package domain;

import config.Constants;

public class Song extends MediaItem {
    private static int instanceCount = 0;
    
    private String album;
    private String genre;

    public Song(String songId, String title, int durationSec, String ownerArtistID, String album, String genre) {
        super(songId, title, durationSec, ownerArtistID);
        
        if (instanceCount >= Constants.MAXIMUM_INSTANCES) {
            throw new IllegalStateException(
                "Cannot create Song: Maximum instance limit of " + 
                Constants.MAXIMUM_INSTANCES + " reached"
            );
        }
        instanceCount++;
        
        this.album = album;
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public String displayInfo() {
        return getTitle() + " • " + album + " • " + genre + " • " + getDurationSec() + "s";
    }
    
    @Override
    public String toString() {
        return "Song{id='" + itemId + "', title='" + title + "', duration=" + durationSec + 
               "s, artist='" + ownerArtistID + "', album='" + album + "', genre='" + genre + "'}";
    }
}
