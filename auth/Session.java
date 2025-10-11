package auth;
import java.util.UUID;

import user.Role;

public class Session {
    private final String sessionId;
    private final String accountId;
    private final Role role;
    private final String linkedId;
    private boolean active = true; // can be logged out

    public Session(String accountId, Role role, String linkedId) {
        this.sessionId = "sess_" + UUID.randomUUID();
        this.accountId = accountId;
        this.role = role;
        this.linkedId = linkedId;
    }

    public String getSessionId() { return sessionId; }
    public String getAccountId() { return accountId; }
    public Role getRole() { return role; }
    public String getLinkedId() { return linkedId; }
    
    public boolean isActive() { 
        return active; 
    }
    
    public void deactivate() { 
        this.active = false; 
    }


}
