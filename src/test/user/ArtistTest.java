package test.user;

import user.Artist;
import catalog.Catalog;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Song publishing and artist management unit tests 
 */
public class ArtistTest {
    
    private Artist artist;
    private Catalog catalog;
    
    @BeforeEach
    void setUp() {
        catalog = Catalog.getInstance();
        artist = new Artist("artist_1", "John Lewis", "John");
    }
        
    @Test
    void testArtistConstructorAndGetters() {
        assertEquals("artist_1", artist.getUserId());
        assertEquals("John Lewis", artist.getDisplayName());
        assertEquals("John", artist.getStageName());
        assertNotNull(artist.getOwnedItemIds());
        assertTrue(artist.getOwnedItemIds().isEmpty());
    }
    
    // ========== TODO: Dushan ==========
    
    @Test
    void testSetStageName() {
        // TODO: Test setStageName()
    }
    
    @Test
    void testPublishSong() {
        // TODO: Test publishSong()
        // Publish a song
        // Verify ownedItemIds contains the song
        // Verify song is in catalog
        // You can use catalog.getItem() and check it's not null
    }
    
    @Test
    void testPublishMultipleSongs() {
        // TODO: Test publishing multiple songs
        // Publish 2 different songs from same artist
        // Verify ownedItemIds size is 2
    }
    
    @Test
    void testRemoveOwnedItem() {
        // TODO: Test removeOwnedItem()
        // Publish a song
        // Remove it using removeOwnedItem()
        // Verify it's no longer in ownedItemIds
        // Verify it's removed from catalog
    }
    
    @Test
    void testRemoveNonOwnedItem() {
        // TODO: Test removing an item the artist doesn't own
        // Should not cause errors
        // Verify ownedItemIds remains unchanged
    }
    
    @Test
    void testArtistAutoRegisteredInCatalog() {
        // TODO: Test that creating an artist auto-registers it
        // 1. Create a new artist
        // 2. Verify catalog.getArtist() returns it
    }
}
