package test.user;

import user.Artist;
import catalog.Catalog;
import domain.Song;
import domain.MediaItem;
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
    
    @Test
    void testSetStageName() {
        artist.setStageName("JL");
        assertEquals("JL", artist.getStageName());
    }
    
    @Test
    void testPublishSong() {
        // Publish a song
        artist.publishSong(catalog, "Blinding Lights", 200, "After Hours", "Synthwave");
        
        // Verify ownedItemIds contains the song
        assertEquals(1, artist.getOwnedItemIds().size());
        
        // Get the published song ID
        String songId = artist.getOwnedItemIds().iterator().next();
        
        // Verify song is in catalog
        MediaItem item = catalog.getItem(songId);
        assertNotNull(item);
        assertTrue(item instanceof Song);
        
        Song song = (Song) item;
        assertEquals("Blinding Lights", song.getTitle());
        assertEquals("After Hours", song.getAlbum());
        assertEquals("Synthwave", song.getGenre());
        assertEquals(200, song.getDurationSec());
        assertEquals(artist.getUserId(), song.getOwnerArtistId());
    }
    
    @Test
    void testPublishMultipleSongs() {
        // Publish 2 different songs from same artist
        artist.publishSong(catalog, "Song 1", 180, "Album A", "Pop");
        artist.publishSong(catalog, "Song 2", 200, "Album B", "Rock");
        
        // Verify ownedItemIds size is 2
        assertEquals(2, artist.getOwnedItemIds().size());
        
        // Verify both are in catalog
        for (String songId : artist.getOwnedItemIds()) {
            assertNotNull(catalog.getItem(songId));
        }
    }
    
    @Test
    void testRemoveOwnedItem() {
        // Publish a song
        artist.publishSong(catalog, "Song to Remove", 150, "Album", "Genre");
        String songId = artist.getOwnedItemIds().iterator().next();
        
        // Verify it's in catalog and ownedItemIds
        assertNotNull(catalog.getItem(songId));
        assertTrue(artist.getOwnedItemIds().contains(songId));
        
        // Remove it using removeOwnedItem()
        artist.removeOwnedItem(catalog, songId);
        
        // Verify it's no longer in ownedItemIds
        assertFalse(artist.getOwnedItemIds().contains(songId));
        
        // Verify it's removed from catalog
        assertNull(catalog.getItem(songId));
    }
    
    @Test
    void testRemoveNonOwnedItem() {
        // Try to remove an item the artist doesn't own
        String nonOwnedId = "fake_song_id";
        
        // Should not cause errors
        artist.removeOwnedItem(catalog, nonOwnedId);
        
        // Verify ownedItemIds remains unchanged (empty)
        assertTrue(artist.getOwnedItemIds().isEmpty());
    }
    
    @Test
    void testArtistAutoRegisteredInCatalog() {
        // Create a new artist
        Artist newArtist = new Artist("artist_2", "Taylor Swift", "Taylor");
        
        // Verify catalog.getArtist() returns it
        Artist retrievedArtist = catalog.getArtist("artist_2");
        assertNotNull(retrievedArtist);
        assertEquals(newArtist.getUserId(), retrievedArtist.getUserId());
        assertEquals("Taylor Swift", retrievedArtist.getDisplayName());
    }
}