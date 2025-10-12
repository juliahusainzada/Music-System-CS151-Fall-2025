package user;
import java.util.ArrayDeque;
import java.util.List;

import catalog.Catalog;
import catalog.Library;
import domain.MediaItem;
import domain.Song;

public class Listener extends User {
    private final Library library;

    private String currentlyPlayingItemId;
    private final ArrayDeque<String> recentlyPlayedItemIds = new ArrayDeque<>(); // most-recent first

    public Listener(String userId, String displayName) {
        super(userId, displayName);
        this.library = new Library(userId);
    }

    private enum PlayerState { STOPPED, PLAYING, PAUSED }

    private PlayerState playerState = PlayerState.STOPPED;
    private long playStartedAtMs = 0L;
    private int pausedAtSec = 0; // position when paused

    // Saving and unsaving songs
    public void saveItem(String itemId) {
        library.save(itemId);
    }
    public void unsaveItem(String itemId) {
        library.unsave(itemId);
    }

    public List<Song> getSavedSongs(Catalog catalog) {
        return library.listSavedSongs(catalog);
    }

    // Search songs
    public Song searchSongByTitle(Catalog catalog, String title) {
        if (title == null || title.isEmpty()) return null;

        for (String id : catalog.search(title)) {
            MediaItem m = catalog.getItem(id);
            if (m instanceof Song) {
                Song s = (Song) m;
                if (s.getTitle() != null && s.getTitle().equalsIgnoreCase(title)) {
                    return s;
                }
            }
        }
        return null;
    }

    // Check if exact title song is already saved
    public boolean isSongSavedByTitle(Catalog catalog, String title) {
        Song s = searchSongByTitle(catalog, title);
        return (s != null) && library.isSaved(s.getItemId());
    }

    public String saveByExactTitle(Catalog catalog, String title) {
        Song s = searchSongByTitle(catalog, title);
        if (s == null) return null;
        library.save(s.getItemId());
        return s.getItemId();
    }

    public String unsaveByExactTitle(Catalog catalog, String title) {
        Song s = searchSongByTitle(catalog, title);
        if (s == null) return null;
        library.unsave(s.getItemId());
        return s.getItemId();
    }

    public String getSaveActionForTitle(Catalog catalog, String title) {
        Song s = searchSongByTitle(catalog, title);
        if (s == null) {
            return "not-found";
        }
        return library.isSaved(s.getItemId()) ? "unsave" : "save";
    }

    // Moving to playing

    public boolean playByExactTitle(Catalog catalog, String title) {
        Song s = searchSongByTitle(catalog, title);
        if (s == null) return false;
        Song current = getCurrentSong(catalog);
        this.currentlyPlayingItemId = s.getItemId();

        boolean resumingSame =
            (playerState == PlayerState.PAUSED) && 
            current != null && 
            current.getItemId().equals(s.getItemId());

        if (resumingSame) {
            playStartedAtMs = System.currentTimeMillis() - pausedAtSec * 1000L;
        } else {
            pausedAtSec = 0;
            playStartedAtMs = System.currentTimeMillis();
        }
        playerState = PlayerState.PLAYING;
        return true;
    }

    public void pause(Catalog catalog) {
        // Dont need pause if we arent playing
        if (playerState != PlayerState.PLAYING) {
            return;
        }
        // Record when we are pausing
        pausedAtSec = getCurrentPositionSec(catalog);
        // Update playerState
        playerState = PlayerState.PAUSED;
    }

    public void stop() {
        // Updates playerState 
        playerState = PlayerState.STOPPED;
        // Fully reset time
        pausedAtSec = 0;
        // No song playing anymore
        currentlyPlayingItemId = null;
    }

    /**
     * Get the currently playing/paused song
     */
    public Song getCurrentSong(Catalog catalog) {
        if (currentlyPlayingItemId == null) {
            return null;
        }
        MediaItem item = catalog.getItem(currentlyPlayingItemId);
        return (item instanceof Song) ? (Song) item : null;
    }

    /**
     * Calculate current playback position in seconds
     */
    public int getCurrentPositionSec(Catalog catalog) {
        if (playerState == PlayerState.STOPPED || currentlyPlayingItemId == null) {
            return 0;
        }
        
        if (playerState == PlayerState.PAUSED) {
            return pausedAtSec;
        }
        
        // PLAYING state: calculate elapsed time
        long elapsedMs = System.currentTimeMillis() - playStartedAtMs;
        int positionSec = (int) (elapsedMs / 1000);
        
        // Cap at song duration
        Song currentSong = getCurrentSong(catalog);
        if (currentSong != null) {
            int maxDuration = currentSong.getDurationSec();
            if (positionSec >= maxDuration) {
                // Song finished playing
                stop();
                return maxDuration;
            }
        }
        
        return positionSec;
    }

    /**
     * Get current player state
     */
    public String getPlayerState() {
        return playerState.name();
    }

    /**
     * Check if currently playing
     */
    public boolean isPlaying() {
        return playerState == PlayerState.PLAYING;
    }

    /**
     * Check if paused
     */
    public boolean isPaused() {
        return playerState == PlayerState.PAUSED;
    }

    /**
     * Check if stopped
     */
    public boolean isStopped() {
        return playerState == PlayerState.STOPPED;
    }

    /**
     * Get playback info as a formatted string
     */
    public String getPlaybackInfo(Catalog catalog) {
        Song song = getCurrentSong(catalog);
        if (song == null) {
            return "No song loaded";
        }
        
        int currentPos = getCurrentPositionSec(catalog);
        int duration = song.getDurationSec();
        
        return String.format("%s - %s | Position: %d:%02d / %d:%02d | State: %s",
            song.getTitle(),
            song.getAlbum(),
            currentPos / 60, currentPos % 60,
            duration / 60, duration % 60,
            playerState.name()
        );
    }
}
