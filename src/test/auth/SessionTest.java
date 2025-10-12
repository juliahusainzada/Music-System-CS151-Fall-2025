package test.auth;

import auth.Session;
import user.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Session class
 */
public class SessionTest {
    
    private Session session;
    
    @BeforeEach
    void setUp() {
        session = new Session("account_1", Role.LISTENER, "listener_1");
    }
    
    
    @Test
    void testSessionConstructorAndGetters() {
        // Test constructor initializes all fields correctly
        assertNotNull(session.getSessionId());
        assertTrue(session.getSessionId().startsWith("sess_"));
        assertEquals("account_1", session.getAccountId());
        assertEquals(Role.LISTENER, session.getRole());
        assertEquals("listener_1", session.getLinkedId());
        assertTrue(session.isActive());
    }
    
    @Test
    void testDeactivate() {
        assertTrue(session.isActive());

        session.deactivate();
        assertFalse(session.isActive());
    }
    
    @Test
    void testArtistSession() {
        Session session2 = new Session("account_2", Role.ARTIST, "artist_1");
        assertEquals(Role.ARTIST, session2.getRole());
    }   
}