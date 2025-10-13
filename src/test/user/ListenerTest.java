package test.user;

import user.Listener;
import catalog.Catalog;
import domain.Song;
import exceptions.ItemNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Save/unsave functionality, music playback unit tests
 */
public class ListenerTest {
    
    private Listener listener;
    private Catalog catalog;
    
    @BeforeEach
    void setUp() {
        listener = new Listener("listener_1", "Test Listener");
        catalog = Catalog.getInstance();
        
        // Add a test song to catalog
        Song testSong = new Song("song_1", "Test Song", 180, "artist_1", "Album", "Pop");
        catalog.addItem(testSong);
    }
    
    @Test
    void testIsSongSavedByTitle() {
        // Set up
        listener.saveItem("song_1");
        
        // Verify
        boolean saved = listener.isSongSavedByTitle(catalog, "Test song");
        assertTrue(saved);

        // Test an item not saved 
        catalog.addItem(new Song("other_song", "Other song", 200, "artist_2", "Album", "Rock"));
        boolean notSaved = listener.isSongSavedByTitle(catalog, "Other song");
        assertFalse(notSaved);
    }
    
    @Test
    void testSaveByExactTitle() throws ItemNotFoundException {
        // Exact title - should work
        String savedId = listener.saveByExactTitle(catalog, "Test Song");
        assertEquals("song_1", savedId);
        
        // Title not in catalog - should throw exception
        try {
            listener.saveByExactTitle(catalog, "Bla bla bla");
            fail("Expected ItemNotFoundException to be thrown");
        } catch (ItemNotFoundException e) {
            assertTrue(e.getMessage().contains("not found"));
        }

        // Partial Title - should throw exception
        try {
            listener.saveByExactTitle(catalog, "Test");
            fail("Expected ItemNotFoundException to be thrown");
        } catch (ItemNotFoundException e) {
            assertTrue(e.getMessage().contains("not found"));
        }
    }
    
    @Test
    void testUnsaveByExactTitle() throws ItemNotFoundException {
        String savedId = listener.saveByExactTitle(catalog, "Test Song");
        String unsaveId = listener.unsaveByExactTitle(catalog, "Test Song");
        
        assertEquals(unsaveId, "song_1");

        // Non-existent title - should throw exception
        try {
            listener.unsaveByExactTitle(catalog, "Test");
            fail("Expected ItemNotFoundException to be thrown");
        } catch (ItemNotFoundException e) {
            assertTrue(e.getMessage().contains("not found"));
        }
    }
    
    @Test
    void testGetSaveActionForTitle() {
        // Set up
        catalog.addItem(new Song("song_1", "Beautiful Day", 180, "artist_1", "Album", "Pop"));
        catalog.addItem(new Song("song_2", "Hello", 200, "Adele", "Album", "Pop"));

        // Unsaved song should return "save"
        String action1 = listener.getSaveActionForTitle(catalog, "Beautiful Day");
        assertEquals("save", action1);

        // Saved song should return "unsave"
        listener.saveItem("song_1");
        String action2 = listener.getSaveActionForTitle(catalog, "Beautiful Day");
        assertEquals("unsave", action2);

        // Non-existent song should return "not-found"
        String action3 = listener.getSaveActionForTitle(catalog, "Non-existent Song");
        assertEquals("not-found", action3);
    }
    
    @Test
    void testPlayByExactTitle() throws ItemNotFoundException {
        listener.playByExactTitle(catalog, "Test Song");
        assertTrue(listener.isPlaying());

        Song currentSong = listener.getCurrentSong(catalog);
        assertNotNull(currentSong);
        assertEquals("song_1", currentSong.getItemId());
        assertEquals("Test Song", currentSong.getTitle());

        assertFalse(listener.isPaused());
        assertFalse(listener.isStopped());
        assertEquals("PLAYING", listener.getPlayerState());
    }
    
    @Test
    void testPlayNonExistentSong() {
        // Non-existent song - should throw exception
        try {
            listener.playByExactTitle(catalog, "Non Existent Song");
            fail("Expected ItemNotFoundException to be thrown");
        } catch (ItemNotFoundException e) {
            assertTrue(e.getMessage().contains("not found"));
        }
    }
    
    @Test
    void testPause() throws ItemNotFoundException {
        listener.playByExactTitle(catalog, "Test Song");
        listener.pause(catalog);
        
        assertTrue(listener.isPaused());

        Song currentSong = listener.getCurrentSong(catalog);
        assertNotNull(currentSong);

        assertFalse(listener.isPlaying());
        assertFalse(listener.isStopped());

        assertEquals("PAUSED", listener.getPlayerState());
    }
    
    @Test
    void testStop() throws ItemNotFoundException {
        listener.playByExactTitle(catalog, "Test Song");
        listener.stop();
        
        assertTrue(listener.isStopped());

        Song currentSong = listener.getCurrentSong(catalog);
        assertNull(currentSong);

        assertFalse(listener.isPlaying());
        assertFalse(listener.isPaused());

        assertEquals("STOPPED", listener.getPlayerState());
    }
    
    @Test
    void testGetPlaybackInfo() throws ItemNotFoundException {
        listener.playByExactTitle(catalog, "Test Song");
        String info = listener.getPlaybackInfo(catalog);

        assertNotNull(info);
        assertTrue(info.contains("Test Song"));
        assertTrue(info.contains("Album"));
        assertTrue(info.contains("PLAYING"));
        assertTrue(info.contains("Position:"));
    
        // Test when no song is playing
        listener.stop();
        String noSongInfo = listener.getPlaybackInfo(catalog);
        assertEquals("No song loaded", noSongInfo);
    }
}
