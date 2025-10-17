package user;

abstract class User {
    protected String userId;
    protected String displayName;

    public User(String userId, String displayName) {
        this.userId = userId;
        this.displayName = displayName;
    }

    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    // validate non-empty display name and change display name
    public void setDisplayName(String displayName) {
        if (displayName == null || displayName.isEmpty()) {
            throw new IllegalArgumentException("Display name cannot be null or empty");
        } else {
            this.displayName = displayName;
        }
    }
}
