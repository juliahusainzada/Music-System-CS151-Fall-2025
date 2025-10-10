import java.util.*;

public class Library {
    private String libraryId;
    private Map<String, MediaItem> mediaItems;
    private Map<String, Artist> artists;
    private List<Song> savedSongs;
    // private List<Podcast> savedPodcasts;

    public Library(String libraryId) {
        this.libraryId = libraryId;
        this.mediaItems = new HashMap<>();
        this.artists = new HashMap<>();
        this.savedSongs = new ArrayList<>();
        // this.savedPodcasts = new ArrayList<>();
    }

    public String getLibraryId() {
        return libraryId;
    }

    public Map<String, MediaItem> getMediaItems() {
        return mediaItems;
    }

    public Map<String, Artist> getArtists() {
        return artists;
    }

    public List<Song> getSavedSongs() {
        return savedSongs;
    }

    /**
     * public List<Podcast> getSavedPodcasts() {
        return savedPodcasts;
    }
    **/

    public void addSong(Artist artist, Song song) {
        mediaItems.put(song.getItemId(), song);
        artist.getOwnedItemIds().add(song.getItemId());
        savedSongs.add(song);
    }

    /**
    public void addPodcast(Artist artist, Podcast podcast) {
        mediaItems.put(podcast.getItemId(), podcast);
        artist.getOwnedItemIds().add(podcast.getItemId());
        savedPodcasts.add(podcast);
    }
    **/

    public void removeItem(String itemId) {
        MediaItem item = mediaItems.remove(itemId);
        if (item != null) {
            Artist artist = artists.get(item.getOwnerArtistId());
            if (artist != null) {
                artist.getOwnedItemIds().remove(itemId);
            }
            /**
            if (item instanceof Song) {
                savedSongs.remove(item);
            } else if (item instanceof Podcast) {
                savedPodcasts.remove(item);
            }
            **/
        }
    }

    public List<MediaItem> search(String query) {
        List<MediaItem> results = new ArrayList<>();
        for (MediaItem item : mediaItems.values()) {
            if (item.matches(query)) {
                results.add(item);
            }
        }
        return results;
    }

    public List<Artist> listArtists() {
        return new ArrayList<>(artists.values());
    }

    public MediaItem getMediaItem(String itemId) {
        return mediaItems.get(itemId);
    }

    public Artist getArtist(String artistId) {
        return artists.get(artistId);
    }

    /**
    public void addArtist(Artist artist) {
        artists.put(artist.getPersonId(), artist);
    }
    **/
}
