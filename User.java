abstract class User {
    protected String userId;
    protected String displayName;
    protected String phoneNumber;

    public User(String userId, String displayName, String phoneNumber) {
        this.userId = userId;
        this.displayName = displayName;
        this.phoneNumber = phoneNumber;
    }

    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
