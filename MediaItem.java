import java.util.HashSet;
import java.util.Set;

/**
 * Abstract base class for Song and Podcast
 * Implements common behavior like playback, saving/unsaving, and searching
 */
public abstract class MediaItem implements Playable, Savable, Searchable {
    protected final String itemId;
    protected String title;
    protected int durationSec;
    protected String ownerArtistID;
    protected final Set<String> savedByUserIds = new HashSet<>();

    private enum PlaybackState { STOPPED, PLAYING, PAUSED }
    private PlaybackState state = PlaybackState.STOPPED;

    public MediaItem(String itemId, String title, int durationSec, String ownerArtistID) {
        this.itemId = itemId;
        this.title = title;
        this.durationSec = durationSec;
        this.ownerArtistID = ownerArtistID;
    }

    public String getItemId() {
        return itemId;
    }

    public String getTitle() {
        return title;
    }

    public int getDurationSec() {
        return durationSec;
    }

    public String getOwnerArtistId() {
        return ownerArtistID;
    }

    public Set<String> getSavedByUserIds() {
        return savedByUserIds;
    }

    // Playable interface implementation
    @Override
    public void play() {
        state = PlaybackState.PLAYING;
    }

    @Override
    public void pause() {
        state = PlaybackState.PAUSED;
    }

    @Override
    public void stop() {
        state = PlaybackState.STOPPED;
    }

    // Savable interface implementation
    @Override
    public void saveForLater(People user) {
        if (user != null) {
            savedByUserIds.add(user.getPersonId());
        }
    }

    @Override
    public void unsave(People user) {
        if (user != null) {
            savedByUserIds.remove(user.getPersonId());
        }
    }

    public interface Savable {
        void saveForLater(People user);
        void unsave(People user);
    }

    /**
     * Determines if MediaItem matches a given search query
     * @return true if the query matches the title or ownerArtistID, false otherwise
     * Will be used by another class to filter collections of MediaItem objects
     */
    @Override
    public boolean matches(String query) {
        if (query == null || query.isEmpty()) {
            return false;
        }
        String q = query.toLowerCase();

        // check if title exists and contains query
        boolean titleMatches = (title != null) && title.toLowerCase().contains(q);

        // check if artist id exists and contains query
        boolean artistMatches = (ownerArtistID != null) & ownerArtistID.toLowerCase().contains(q);
        
        // return true if either the title OR artist matches
        return titleMatches || artistMatches;
    }
}
