import java.util.ArrayList;
import java.util.List;

public class User extends People {
    private String userSubscription;
    private List<String> savedItemIds;
    private List<String> recentlyPlayedItemIds;

    public User(String userId, String displayName, String phoneNumber, String userSubscription) {
        super(userId, displayName, phoneNumber);
        this.userSubscription = userSubscription;
        this.savedItemIds = new ArrayList<>();
        this.recentlyPlayedItemIds = new ArrayList<>();
    }

    public void saveItem(String itemId) {
        savedItemIds.add(itemId);
    }
    public void unsaveItem(String itemId) {
        savedItemIds.remove(itemId);
    }
}
