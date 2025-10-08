abstract class People {
    protected String personId;
    protected String displayName;
    protected String phoneNumber;

    public People(String personId, String displayName, String phoneNumber) {
        this.personId = personId;
        this.displayName = displayName;
        this.phoneNumber = phoneNumber;
    }

    public String getPersonId() {
        return personId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}




