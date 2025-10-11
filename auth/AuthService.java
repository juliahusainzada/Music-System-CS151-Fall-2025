package auth;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import user.Account;
import user.Artist;
import user.Listener;
import user.Role;

public class AuthService {
    private final Map<String, Account> accounts = new HashMap<>();
    private final Map<String, Session> sessions = new HashMap<>();

    private final Map<String, Listener> listeners = new HashMap<>();
    private final Map<String, Artist> artists = new HashMap<>();
    
    /**
     * - Creates unique userId ✓
        - Creates Listener object and stores it ✓
        - Creates Account linking to that Listener ✓
        - Auto-creates Session for immediate login ✓
        - Returns the session
     */
    public Session registerListener(String accountId, String password, String displayName) {
        if (accounts.containsKey(accountId)) {
            return null; // username is taken!
        }

        // Create Listener object
        String userId = "u" + UUID.randomUUID().toString().substring(0, 8);
        Listener listener = new Listener(userId, displayName);
        listeners.put(userId, listener);

        // Create Account
        Account account = new Account(accountId, password, Role.LISTENER, userId);
        accounts.put(accountId, account);

        // auto login creates a new session
        Session session = new Session(accountId, Role.LISTENER, userId);
        sessions.put(session.getSessionId(), session);

        return session;
    }

    public Session registerArtist(String accountId, String password, String displayName, String stageName) {
        if (accounts.containsKey(accountId)) {
            return null; // username is taken!
        }

        // Create Artist object
        String userId = "a" + UUID.randomUUID().toString().substring(0, 8);
        Artist artist = new Artist(userId, displayName, stageName);
        artists.put(userId, artist);

        // Create Account
        Account account = new Account(accountId, password, Role.ARTIST, userId);
        accounts.put(accountId, account);

        // auto login creates a new session
        Session session = new Session(accountId, Role.ARTIST, userId);
        sessions.put(session.getSessionId(), session);

        return session;
    }

    /**
     * - Finds the account ✓
     * Checks if account exists ✓
     * Verifies password matches ✓
     * Creates new session ✓
     * Stores and returns session ✓
     */
    public Session login(String accountId, String password) {
        // First, find account
        Account account = accounts.get(accountId);

        if (account == null) {
            return null; // acc doesnt exist
        }

        // Second, verify pass
        if (!account.getPassword().equals(password)) {
            return null; // wrong pass
        }

        // Third, create new session
        Session session = new Session(account.getAccountId(), account.getRole(), account.getLinkedId());

        // return session
        sessions.put(session.getSessionId(), session);
        return session;
    }

    public boolean logout(String sessionId) {
        Session session = sessions.get(sessionId);
        if (session == null || !session.isActive()) {
            return false; // session doesnt exist or already logged out 
        }
        session.deactivate();
        return true;
    }

    public Object getUserBySession(String sessionId) {
        Session session = sessions.get(sessionId);
        if (session == null || !session.isActive()) {
            return null; //invalid session
        }

        if (session.getRole() == Role.ARTIST) {
            return artists.get(session.getLinkedId());
        } else {
            return listeners.get(session.getLinkedId());
        }
    }

    /**
     * Helper method for loading pre-existing artists from CSV files
     * Does not create a session or auto-generate userId
     */
    public void addPreloadedArtist(String accountId, String password, String userId, 
                                   String displayName, String stageName) {
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
        Listener listener = new Listener(userId, displayName);
        listeners.put(userId, listener);
        
        Account account = new Account(accountId, password, Role.LISTENER, userId);
        accounts.put(accountId, account);
    }
}
