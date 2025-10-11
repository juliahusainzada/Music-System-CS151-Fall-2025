public class Song extends MediaItem {
    private String album;
    private String genre;

    public Song(String songId, String title, int durationSec, String ownerArtistID, String album, String genre) {
        super(songId, title, durationSec, ownerArtistID);
        this.album = album;
        this.genre = genre;
    }

    public String getAlbum() { return album; }
    public void setAlbum(String album) { this.album = album; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    // Explicitly satisfy interfaces here (delegating to MediaItem) to avoid any ambiguity
    @Override public void play()  { super.play(); }
    @Override public void pause() { super.pause(); }
    @Override public void stop()  { super.stop(); }

    @Override public void saveForLater(User user) { super.saveForLater(user); }
    @Override public void unsave(User user)       { super.unsave(user); }
}