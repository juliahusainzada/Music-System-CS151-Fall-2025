package test.auth;

import auth.AuthService;
import auth.Session;
import user.Listener;
import user.Artist;
import user.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Authentication, registration, and session management unit tests
 */
public class AuthServiceTest {
    
    private AuthService authService;
    
    @BeforeEach
    void setUp() {
        authService = new AuthService();
    }
        
    @Test
    void testRegisterListener() {
        // Test registering a new listener account
        Session session = authService.registerListener("user1", "password123", "John Doe");
        
        assertNotNull(session);
        assertEquals("user1", session.getAccountId());
        assertEquals(Role.LISTENER, session.getRole());
        assertTrue(session.isActive());
        
        // Verify user object can be retrieved
        Object user = authService.getUserBySession(session.getSessionId());
        assertNotNull(user);
        assertTrue(user instanceof Listener);
    }
        
    @Test
    void testRegisterArtist() {
        // Register artist
        Session session = authService.registerArtist("taylorswift", "pass123", "Taylor Swift", "Taylor");
        
        // Verify session is created correctly
        assertEquals("taylorswift", session.getAccountId());
        assertEquals(session.getRole(), Role.ARTIST);
        
        // Verify user is an Artist
        Object user = authService.getUserBySession(session.getSessionId());
        assertNotNull(user);
        assertTrue(user instanceof Artist);
    }
    
    @Test
    void testRegisterDuplicateAccount() {
        // Set up
        Session session1 = authService.registerListener("user1", "password123", "John Doe");
        Session session2 = authService.registerListener("user1", "pass321", "Harry Potter");

        // Verify
        assertNull(session2);
    }
    
    @Test
    void testLogin() {
        // Set up
        Session registrationSession = authService.registerListener("just_bob", "pass123", "Bob Best");
        Session loginSession = authService.login("just_bob", "pass123");

        // Verify
        assertNotNull(loginSession);
        assertEquals("just_bob", loginSession.getAccountId());
        assertEquals(Role.LISTENER, loginSession.getRole());
        assertTrue(loginSession.isActive());

        assertNotEquals(registrationSession.getSessionId(), loginSession.getSessionId());
        assertNotEquals(registrationSession, loginSession);
    }
    
    @Test
    void testLoginWrongPassword() {
        // Set up
        Session registrationSession = authService.registerListener("just_bob", "pass123", "Bob Best");
        Session loginSession = authService.login("just_bob", "wrong_pass");

        // Verify
        assertNull(loginSession);
    }
    
    @Test
    void testLoginNonExistentAccount() {
        Session loginSession = authService.login("just_bob", "pass123");
        assertNull(loginSession);
    }
    
    @Test
    void testLogout() {
        // Set up
        Session registrationSession = authService.registerListener("just_bob", "pass123", "Bob Best");
        boolean logoutResult = authService.logout(registrationSession.getSessionId());
        
        // Verify
        assertTrue(logoutResult);
        assertFalse(registrationSession.isActive());
        assertNull(authService.getUserBySession(registrationSession.getSessionId()));
    }
    
    @Test
    void testLogoutTwice() {
        Session session = authService.registerListener("just_bob", "pass123", "Bob Best");
        // logout once
        boolean logoutResult = authService.logout(session.getSessionId());
        assertTrue(logoutResult);

        // logout again
        boolean logoutResult2 = authService.logout(session.getSessionId());
        assertFalse(logoutResult2);
    }

    @Test
    void logoutLogin() {
        Session session = authService.registerListener("just_bob", "pass123", "Bob Best");
        boolean logoutResult = authService.logout(session.getSessionId());
        assertTrue(logoutResult);
        assertFalse(session.isActive());

        Session session2 = authService.login("just_bob", "pass123");
        assertTrue(session2.isActive());
    }
    
    @Test
    void testGetUserBySession() {
        // Set up
        Session session = authService.registerListener("just_bob", "pass123", "Bob Best");
        Object user = authService.getUserBySession(session.getSessionId());
        Listener bob = (Listener) user;
        
        // Verify
        assertTrue(user instanceof Listener);
        assertTrue(bob.getUserId().startsWith("u"));
    }
    
    @Test
    void testGetArtistBySession() {
        // Set up
        Session session = authService.registerArtist("taylorswift", "pass123", "Taylor Swift", "Taylor");
        Object user = authService.getUserBySession(session.getSessionId());
        Artist taylor = (Artist) user;
        
        // Verify
        assertTrue(user instanceof Artist);
        assertTrue(taylor.getUserId().startsWith("a"));
    }
    
    @Test
    void testAddPreloadedListener() {
        // Set up
        authService.addPreloadedListener(
            "csv_user",        
            "csv_pass",    
            "u12345678", //internal random
            "CSV User" 
        );

        Session session = authService.login("csv_user", "csv_pass");

        // Verify
        assertTrue(session.isActive());
        assertEquals(session.getAccountId(), "csv_user");
    }
    
    @Test
    void testMultipleSessionsSameAccount() {
        // Set up
        Session session1 = authService.registerListener("bob", "pass", "Bob");
        Session session2 = authService.login("bob", "pass");

        // Verify
        assertNotEquals(session1.getSessionId(), session2.getSessionId());
        assertTrue(session1.isActive());
        assertTrue(session2.isActive());

        authService.logout(session1.getSessionId());
        assertFalse(session1.isActive());
        assertTrue(session2.isActive());

        assertNotNull(authService.getUserBySession(session2.getSessionId()));
        assertNull(authService.getUserBySession(session1.getSessionId()));
    }
}
