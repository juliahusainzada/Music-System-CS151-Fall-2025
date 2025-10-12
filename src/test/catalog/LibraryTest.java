package test.catalog;

import catalog.Library;
import catalog.Catalog;
import domain.MediaItem;
import domain.Song;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

/**
 * Save/unsave and library management unit tests 
 */
public class LibraryTest {
    
    private Library library;
    private Catalog catalog;
    
    @BeforeEach
    void setUp() {
        library = new Library("user_1");
        catalog = Catalog.getInstance();
        
        // Add test songs to catalog
        catalog.addItem(new Song("song_1", "Song One", 180, "artist_1", "Album", "Pop"));
        catalog.addItem(new Song("song_2", "Song Two", 200, "artist_1", "Album", "Rock"));
    }
        
    @Test
    void testSaveAndListSongs() {
        // Test saving songs and retrieving them
        library.save("song_1");
        library.save("song_2");
        
        List<Song> savedSongs = library.listSavedSongs(catalog);
        assertEquals(2, savedSongs.size());
    }
    
    @Test
    void testUnsaveItem() {
        // Set up
        library.save("song_1");
        library.unsave("song_1");

        List<Song> savedSongs = library.listSavedSongs(catalog);
        
        // Verify
        assert(savedSongs.isEmpty());
    }
    
    @Test
    void testIsSaved() {
        // Before saved
        boolean saved = library.isSaved("song_1");
        assertFalse(saved);

        // After saved
        library.save("song_1");
        boolean saved2 = library.isSaved("song_1");

        // Verify now saved
        assertTrue(saved2);
    }
    
    @Test
    void testSaveDuplicate() {
        // Set up
        library.save("song_1");
        library.save("song_1");

        List<Song> savedSongs = library.listSavedSongs(catalog);

        // Verify
        assertEquals(1, savedSongs.size());
    }
    
    @Test
    void testSaveOrder() {
        // Set up
        library.save("song_2");
        library.save("song_1");

        List<Song> savedSongs = library.listSavedSongs(catalog);

        // Verify (LinkedHashSet preserves insertion order)
        assertEquals("song_2", savedSongs.get(0).getItemId());
        assertEquals("song_1", savedSongs.get(1).getItemId());
    }
    
    @Test
    void testListSavedSongsFiltersCorrectly() {
        // Set up
        library.save("song_1");
        library.save("song_2");

        List<Song> savedSongs = library.listSavedSongs(catalog);

        // Verify returned items are songs
        assertEquals(2, savedSongs.size());
        for (Song song : savedSongs) {
            assertNotNull(song);
            assertTrue(song instanceof Song);
            assertTrue(song instanceof MediaItem);
        }
    }
}
