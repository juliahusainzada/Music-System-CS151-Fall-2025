package test.catalog;

import catalog.Catalog;
import domain.Song;
import domain.MediaItem;
import user.Artist;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

/**
 * Catalog operations and search functionality tests
 */
public class CatalogTest {
    
    private Catalog catalog;
    
    @BeforeEach
    void setUp() {
        catalog = Catalog.getInstance();
        
        // Add test data
        catalog.addItem(new Song("song_1", "Beautiful Day", 180, "artist_1", "Album", "Pop"));
        catalog.addItem(new Song("song_2", "Rainy Night", 200, "artist_2", "Album", "Jazz"));
    }
    
    @AfterEach
    void cleanUp() {
        // Clean up test data by removing all items added during tests
        // Get all item IDs that might exist from any test
        String[] testItemIds = {"song_1", "song_2", "song_3", "song_4", "song_5", "test_song_1"};
        
        for (String itemId : testItemIds) {
            catalog.removeItem(itemId);
        }
    }
        
    @Test
    void testGetInstanceReturnsSameObject() {
        // Test that getInstance() always returns the same singleton instance
        Catalog instance1 = Catalog.getInstance();
        Catalog instance2 = Catalog.getInstance();
        
        assertSame(instance1, instance2);
    }
        
    @Test
    void testAddAndGetItem() {
        // Set up
        Song song = new Song(
            "test_song_1",         
            "Test Song",         
            180, 
            "a12345",
            "Test Album",
            "Pop" 
        );

        catalog.addItem(song);

        // Verify
        MediaItem retrievedItem = catalog.getItem("test_song_1");
        assertNotNull(retrievedItem);
        assertEquals("test_song_1", retrievedItem.getItemId());
    }
    
    @Test
    void testGetNonExistentItem() {
        assertNull(catalog.getItem("fake_id"));
    }
    
    @Test
    void testRemoveItem() {
        // Set up
        boolean removeItem = catalog.removeItem("song_1");

        // Verify 
        assertNull(catalog.getItem("song_1"));
        assertTrue(removeItem);
    }
    
    @Test
    void testRemoveNonExistentItem() {
        // Set up
        boolean removeItem = catalog.removeItem("fake_id");
        
        // Verify 
        assertFalse(removeItem);
    }
    
    @Test
    void testSearch() {
        List<String> results = catalog.search("Beautiful");

        assertTrue(results.contains("song_1"));
        assertFalse(results.contains("song_2"));

        assertEquals(1, results.size());
    }
    
    @Test
    void testSearchCaseInsensitive() {
        assertTrue(catalog.search("beautiful").contains("song_1"));
        assertTrue(catalog.search("BEAUTIFUL").contains("song_1"));
        assertTrue(catalog.search("bEauTiFul").contains("song_1"));
    }
    
    @Test
    void testSearchPartialMatch() {
        assertTrue(catalog.search("bea").contains("song_1"));
        assertTrue(catalog.search("day").contains("song_1"));
        assertTrue(catalog.search("BEAU").contains("song_1"));
    }
    
    @Test
    void testSearchNoResults() {
        List<String> results = catalog.search("Nonexistent query");
        assertEquals(0, results.size());
    }
    
    @Test
    void testSearchMultipleResults() {
        // Set up
        catalog.addItem(new Song("song_3", "Beautiful Night", 180, "artist_1", "Album", "Pop"));
        catalog.addItem(new Song("song_4", "Life is beautiful", 180, "artist_1", "Album", "Jazz"));
        catalog.addItem(new Song("song_5", "That's what makes you beautiful", 180, "one_direction", "Album", "Pop"));

        List<String> results = catalog.search("beautiful");
        assertEquals(4, results.size());
    }
    
    @Test
    void testAddArtist() {
        // Set up
        Artist taylor = new Artist("taylorid", "Taylor S", "Taylor");

        // Verify same artist
        assertEquals(taylor, catalog.getArtist("taylorid"));
    }
    
    @Test
    void testGetNonExistentArtist() {
        assertNull(catalog.getArtist("taylorid"));
    }
}
