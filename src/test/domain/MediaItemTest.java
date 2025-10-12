package test.domain;

import domain.MediaItem;
import domain.Song;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Search/match unit tests 
 */
public class MediaItemTest {
    
    private MediaItem testItem;
    
    @BeforeEach
    void setUp() {
        // Since mediaItem is abstract, we test through Song
        testItem = new Song("song_456", "Beautiful Day", 240, "artist_2", "Great Album", "Rock");
    }
        
    @Test
    void testMatchesWithValidQuery() {
        assertTrue(testItem.matches("Beautiful"));
        assertTrue(testItem.matches("beau"));
        assertTrue(testItem.matches("AY"));
        assertTrue(testItem.matches("Day"));
        assertTrue(testItem.matches("beautiful day")); // case insensitive
    }
        
    @Test
    void testMatchesWithEmptyQuery() {
        assertFalse(testItem.matches(""));
    }
    
    @Test
    void testMatchesWithNoMatch() {
        assertFalse(testItem.matches("happy"));
    }
    
    @Test
    void testGetters() {
        testItem = new Song("song_456", "Beautiful Day", 240, "artist_2", "Great Album", "Rock");

        assertEquals("song_456", testItem.getItemId());
        assertEquals("Beautiful Day", testItem.getTitle());
        assertEquals(240, testItem.getDurationSec());
        assertEquals("artist_2", testItem.getOwnerArtistId());
    }
}
