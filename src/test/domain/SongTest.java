package test.domain;

import domain.Song;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Getter/setter, display methods unit tests 
 */
public class SongTest {
    
    private Song testSong;
    
    @BeforeEach
    void setUp() {
        // Create a test song before each test
        testSong = new Song("song_123", "Test Song", 180, "artist_1", "Test Album", "Pop");
    }
        
    @Test
    void testSongConstructorAndGetters() {
        // Test that constructor properly initializes all fields
        assertEquals("song_123", testSong.getItemId());
        assertEquals("Test Song", testSong.getTitle());
        assertEquals(180, testSong.getDurationSec());
        assertEquals("artist_1", testSong.getOwnerArtistId());
        assertEquals("Test Album", testSong.getAlbum());
        assertEquals("Pop", testSong.getGenre());
    }
        
    @Test
    void testSetters() {
        testSong.setAlbum("Revised Album");
        testSong.setGenre("Jazz");

        assertEquals("Revised Album", testSong.getAlbum());
        assertEquals("Jazz", testSong.getGenre());
    }
    
    @Test
    void testDisplayInfo() {
        String info = testSong.displayInfo();
        testSong = new Song("song_123", "Test Song", 180, "artist_1", "Test Album", "Pop");

        assertTrue(info.equals("Test Song • Test Album • Pop • 180s"));
    }
}
