package user;
public class Account {
    private final String accountId;
    private final String password;
    private final Role role; // LISTENER or ARTIST
    private final String linkedId; // Their id

    public Account(String accountId, String password, Role role, String linkedId) {
        this.accountId = accountId;
        this.password = password;
        this.role = role;
        this.linkedId = linkedId;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public String getLinkedId() {
        return linkedId;
    }
}
