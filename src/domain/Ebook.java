package domain;

import config.Constants;

public class Ebook extends MediaItem {
    private static int instanceCount = 0;
    
    protected String author;
    protected String publication;
    protected String year;

    public Ebook(String itemId, String title, int durationSec, String author, String publication, String year) {
        super(itemId, title, durationSec, author);
        if (instanceCount >= Constants.MAXIMUM_INSTANCES) {
            throw new IllegalStateException(
                "Cannot create Podcast: Maximum instance limit of " + 
                Constants.MAXIMUM_INSTANCES + " reached"
            );
        }
        instanceCount++;
        this.publication = publication;
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }
    public String getPublication() {
        return publication;
    }
    
    @Override
    public String displayInfo() {
        return getTitle() + " • " + author + " • " + publication + " • " + getDurationSec() + "s";
    }

    @Override
    public String toString() {
        return "Ebook{id='" + itemId + "', title='" + title + "', duration=" + durationSec + 
               "s, author='" + author + "', publication='" + publication + "', year='" + year + "'}";
    }
}
    

