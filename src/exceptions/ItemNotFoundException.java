package exceptions;

/**
 * Custom exception thrown when a requested item (song, podcast, etc.) 
 * cannot be found in the catalog
 */
public class ItemNotFoundException extends Exception {
    
    public ItemNotFoundException(String message) {
        super(message);
    }
    
    public ItemNotFoundException(String itemType, String itemIdentifier) {
        super(itemType + " '" + itemIdentifier + "' not found");
    }
}
