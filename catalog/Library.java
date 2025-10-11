package catalog;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import domain.MediaItem;
import domain.Song;

public class Library {
    // Each library belongs to one user
    private final String ownerId;
    // Each library only tracks what that person has saved
    // Set: no duplicates
    // LinkedHashSet: order is remembered in which added
    private final Set<String> savedItemIds = new LinkedHashSet<>();

    public Library(String ownerId) {
        this.ownerId = ownerId;
    }

    // adds ID to users saved set
    public void save(String itemId) {
        savedItemIds.add(itemId);
    }

    // removed ID from users saved set
    public void unsave(String itemId) {
        savedItemIds.remove(itemId);
    }
    // Displaying

    // Return only saved songs
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

    // library must tell us if something already saved
    // This lets the listener decide whether to show Save or Unsave after a search.
    public boolean isSaved(String itemId) {
        return savedItemIds.contains(itemId);
    }
}
