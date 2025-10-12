package catalog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domain.MediaItem;
import user.Artist;

public final class Catalog {
    // Only one catalog exists
    private static final Catalog INSTANCE = new Catalog();
    
    // key = itemId, value = MediaItem object
    private final Map<String, MediaItem> itemsById = new HashMap<>();
    // key = artistID, value = Artist object
    private final Map<String, Artist> artistsById = new HashMap<>();

    // Constructor
    private Catalog() {}

    // Same catalog object returned
    public static Catalog getInstance() {
        return INSTANCE;
    }

    public void addArtist(Artist artist) {
        artistsById.put(artist.getUserId(), artist);
    }

    public void addItem(MediaItem item) {
        itemsById.put(item.getItemId(), item);
    }

    public MediaItem getItem(String id) {
        return itemsById.get(id);
    }

    public Artist getArtist(String artistId) {
        return artistsById.get(artistId);
    }

    public boolean removeItem(String itemId) {
        MediaItem removed = itemsById.remove(itemId);
        if (removed == null) {
            return false;
        }
        return true;
    }

    // Takes search word (query), returns list of items that match it
    public List<String> search(String query) {
        List<String> results = new ArrayList<>();
        // Go through every item in itemsById (values gives MediaItem object)
        for (MediaItem item : itemsById.values()) {
            if (item.matches(query)) {
                results.add(item.getItemId());
            }
        }
        return results;
    }
}
