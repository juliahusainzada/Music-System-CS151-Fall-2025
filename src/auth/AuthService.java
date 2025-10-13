package auth;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import exceptions.InvalidCredentialsException;
import user.Account;
import user.Artist;
import user.Listener;
import user.Role;

/**
 * Handles all user account operations
 * Registering users, login/logout, managing session
 * Gives access to user profiles
 */
public class AuthService {
    // Stores registered accounts by accountId
    private final Map<String, Account> accounts = new HashMap<>();
    
    // Stores currently logged-in sessions by sessionId
    private final Map<String, Session> sessions = new HashMap<>();

    // Each store registered users by userId
    private final Map<String, Listener> listeners = new HashMap<>();
    private final Map<String, Artist> artists = new HashMap<>();
    
    public Session registerListener(String accountId, String password, String displayName) {
        // Validate input - reject empty fields
        if (accountId == null || accountId.trim().isEmpty() ||
            password == null || password.trim().isEmpty() ||
            displayName == null || displayName.trim().isEmpty()) {
            return null;
        }

        // Normalize username to lowercase for case-insensitive comparison
        accountId = accountId.toLowerCase();

        // Check if username already taken
        if (accounts.containsKey(accountId)) {
            return null;
        }

        // Create Listener object, generate unique userId, save to listeners
        String userId = "u" + UUID.randomUUID().toString().substring(0, 8);
        Listener listener = new Listener(userId, displayName);
        listeners.put(userId, listener);

        // Create Account linking to userId
        Account account = new Account(accountId, password, Role.LISTENER, userId);
        accounts.put(accountId, account);

        // Create new session
        Session session = new Session(accountId, Role.LISTENER, userId);
        sessions.put(session.getSessionId(), session);
    
        return session;
    }

    public Session registerArtist(String accountId, String password, String displayName, String stageName) {        
        // Validate input - reject empty fields
        if (accountId == null || accountId.trim().isEmpty() ||
            password == null || password.trim().isEmpty() ||
            displayName == null || displayName.trim().isEmpty() ||
            stageName == null || stageName.trim().isEmpty()) {

            return null;
        }
        
        // Normalize username to lowercase for case-insensitive comparison
        accountId = accountId.toLowerCase();
        
        // Check if username already taken
        if (accounts.containsKey(accountId)) {
            return null;
        }

        // Create Listener object, generate unique userId, save to artists
        String userId = "a" + UUID.randomUUID().toString().substring(0, 8);
        Artist artist = new Artist(userId, displayName, stageName);
        artists.put(userId, artist);

        // Create Account linking to userId
        Account account = new Account(accountId, password, Role.ARTIST, userId);
        accounts.put(accountId, account);

        // Create new session
        Session session = new Session(accountId, Role.ARTIST, userId);
        sessions.put(session.getSessionId(), session);

        return session;
    }

    public Session login(String accountId, String password) throws InvalidCredentialsException {
        // Validate input - reject empty fields
        if (accountId == null || accountId.trim().isEmpty() ||
            password == null || password.trim().isEmpty()) {
            throw new InvalidCredentialsException("Username and password cannot be empty");
        }
        
        // Normalize username to lowercase for case-insensitive comparison
        accountId = accountId.toLowerCase();
        
        Account account = accounts.get(accountId);
        
        // Check if account exists
        if (account == null) {
            throw new InvalidCredentialsException("Invalid username or password");
        }

        // Verify password
        if (!account.getPassword().equals(password)) {
            throw new InvalidCredentialsException("Invalid username or password");
        }

        // Create new session
        Session session = new Session(account.getAccountId(), account.getRole(), account.getLinkedId());
        sessions.put(session.getSessionId(), session);
        
        return session;
    }

    public boolean logout(String sessionId) {
        Session session = sessions.get(sessionId);

        // Check if session doesnt exist or user already logged out 
        if (session == null || !session.isActive()) {
            return false; 
        }
        session.deactivate();
        return true;
    }

    // Given session ID, return currently logged-in user object
    public Object getUserBySession(String sessionId) {
        Session session = sessions.get(sessionId);
        if (session == null || !session.isActive()) {
            return null; // Invalid session ex. user not logged in
        }

        if (session.getRole() == Role.ARTIST) {
            return artists.get(session.getLinkedId());
        } else {
            return listeners.get(session.getLinkedId());
        }
    }
    
    /**
     * Check if a username is available for registration
     * @return true if username is available, false if already taken
     */
    public boolean isUsernameAvailable(String accountId) {
        if (accountId == null || accountId.trim().isEmpty()) {
            return false;
        }
        // Normalize username to lowercase for case-insensitive comparison
        accountId = accountId.toLowerCase();
        return !accounts.containsKey(accountId);
    }
    
    /**
     * Helper method for loading pre-existing artists from CSV files
     * Does not create a session or auto-generate userId
     */
    public void addPreloadedArtist(String accountId, String password, String userId, 
                                   String displayName, String stageName) {
        // Normalize username to lowercase for case-insensitive comparison
        accountId = accountId.toLowerCase();
        
        Artist artist = new Artist(userId, displayName, stageName);
        artists.put(userId, artist);
        
        Account account = new Account(accountId, password, Role.ARTIST, userId);
        accounts.put(accountId, account);
    }

    /**
     * Helper method for loading pre-existing listeners from CSV files
     * Does not create a session or auto-generate userId
     */
    public void addPreloadedListener(String accountId, String password, String userId,
                                     String displayName) {
        // Normalize username to lowercase for case-insensitive comparison
        accountId = accountId.toLowerCase();
        
        Listener listener = new Listener(userId, displayName);
        listeners.put(userId, listener);
        
        Account account = new Account(accountId, password, Role.LISTENER, userId);
        accounts.put(accountId, account);
    }
}
