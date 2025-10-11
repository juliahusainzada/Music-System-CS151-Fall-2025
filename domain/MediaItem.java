package domain;
/**
 * Abstract base class for Song and Podcast
 * Implements common behavior like playback, saving/unsaving, and searching
 */
public abstract class MediaItem implements Searchable {
    protected final String itemId;
    protected String title;
    protected int durationSec;
    protected String ownerArtistID;

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
        return title != null && title.toLowerCase().contains(query.toLowerCase());
    }

    public abstract String displayInfo();
}
