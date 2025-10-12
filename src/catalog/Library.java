package catalog;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import domain.MediaItem;
import domain.Song;

/**
 * Each library belongs to one user
 * Tracks what Listener saved
 */
public class Library {
    private final String ownerId;
    // Set: No duplicates
    // LinkedHashSet: Order is remembered in which added
    private final Set<String> savedItemIds = new LinkedHashSet<>();

    public Library(String ownerId) {
        this.ownerId = ownerId;
    }

    // Add media ID to saved set
    public void save(String itemId) {
        savedItemIds.add(itemId);
    }

    // Remove media from saved set
    public void unsave(String itemId) {
        savedItemIds.remove(itemId);
    }

    // Return saved songs
    public List<Song> listSavedSongs(Catalog catalog) {
        List<Song> out = new ArrayList<>();
        for (String id: savedItemIds) {
            MediaItem m = catalog.getItem(id);
            if (m instanceof Song) {
                out.add((Song) m);
            }
        }
        return out;
    }

    // Helps with save or unsaving items
    public boolean isSaved(String itemId) {
        return savedItemIds.contains(itemId);
    }
}
