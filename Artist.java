import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Artist extends User {
    private String stageName;
    private List<String> ownedItemIds;
    private LocalDateTime joinedAt;
    private List<String> followers; // stretch

    public Artist(String userId, String displayName, String phoneNumber, String stageName) {
        super(userId, displayName, phoneNumber);
        this.stageName = stageName;
        this.ownedItemIds = new ArrayList<>();
        this.joinedAt = LocalDateTime.now();
        this.followers = new ArrayList<>();
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public List<String> getOwnedItemIds() {
        return ownedItemIds;
    }

    public LocalDateTime getJoinedAt() {
        return joinedAt;
    }

    public List<String> getFollowers() {
        return followers;
    }

    public void addFollower(String userId) {
        if (!followers.contains(userId)) {
            followers.add(userId);
        }
    }

    public void removeFollower(String userId) {
        followers.remove(userId);
    }

    public void publishSong(Library library, String title, int durationSec, String album, String genre) {
        String itemId = "song_" + System.currentTimeMillis(); // simple ID generation
        Song song = new Song(itemId, title, durationSec, userId, album, genre);
        library.addSong(this, song);
    }

    /**
    public void publishPodcast(Library library, String title, int durationSec, String seriesName, int episodeNumber) {
        String itemId = "podcast_" + System.currentTimeMillis();
        Podcast podcast = new Podcast(itemId, title, durationSec, personId, seriesName, episodeNumber);
        library.addPodcast(this, podcast);
    }
    **/

    public void removeOwnedItem(Library library, String itemId) {
        if (ownedItemIds.contains(itemId)) {
            library.removeItem(itemId);
        }
    }
}
