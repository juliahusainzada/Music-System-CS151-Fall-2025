package user;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import catalog.Catalog;
import config.Constants;
import domain.Song;

public class Artist extends User {
    private static int instanceCount = 0;
    
    private String stageName;
    private Set<String> ownedItemIds;

    /**
     * userId = artist uniqueID for User
     * stageName
     * ownedItemIds = IDs of media artist owns (songs published)
     */
    public Artist(String userId, String displayName, String stageName) {
        super(userId, displayName);
        
        if (instanceCount >= Constants.MAXIMUM_INSTANCES) {
            throw new IllegalStateException(
                "Cannot create Artist: Maximum instance limit of " + 
                Constants.MAXIMUM_INSTANCES + " reached"
            );
        }
        instanceCount++;
        
        this.stageName = stageName;
        this.ownedItemIds = new HashSet<>();

        // auto-register artist in catalog
        Catalog.getInstance().addArtist(this);
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public Set<String> getOwnedItemIds() {
        return ownedItemIds;
    }

    /**
     * artist creates song object
     * this.getUserId() - link for ownerArtistId
     * add to catalog, which stores it in itemsById
     */
    public void publishSong(Catalog catalog, String title, int durationSec, String album, String genre) {
        String itemId = "song_" + UUID.randomUUID().toString(); // simple ID generation
        Song song = new Song(itemId, title, durationSec, this.getUserId(), album, genre);
        catalog.addItem(song);
        ownedItemIds.add(itemId);
    }

    /**
    public void publishPodcast(Library library, String title, int durationSec, String seriesName, int episodeNumber) {
        String itemId = "podcast_" + System.currentTimeMillis();
        Podcast podcast = new Podcast(itemId, title, durationSec, personId, seriesName, episodeNumber);
        library.addPodcast(this, podcast);
    }
    **/

    public void removeOwnedItem(Catalog catalog, String itemId) {
        if (ownedItemIds.contains(itemId)) {
            catalog.removeItem(itemId);
            ownedItemIds.remove(itemId);
        }
    }
    
    @Override
    public String toString() {
        return "Artist{userId='" + userId + "', displayName='" + displayName + 
               "', stageName='" + stageName + "', songsPublished=" + ownedItemIds.size() + "}";
    }
}
