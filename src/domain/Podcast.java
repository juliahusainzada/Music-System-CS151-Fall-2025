package domain;

import config.Constants;

public class Podcast extends MediaItem {
    private static int instanceCount = 0;
    
    private String host;
    private String seriesName;
    private int episodeNumber;

    public Podcast(String itemId, String title, int durationSec, String host, String seriesName, int episodeNumber) {
        super(itemId, title, durationSec, host);
        
        if (instanceCount >= Constants.MAXIMUM_INSTANCES) {
            throw new IllegalStateException(
                "Cannot create Podcast: Maximum instance limit of " + 
                Constants.MAXIMUM_INSTANCES + " reached"
            );
        }
        instanceCount++;
        
        this.host = host;
        this.seriesName = seriesName;
        this.episodeNumber = episodeNumber;
    }

    public String getHost() {
        return host;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public int getEpisodeNumber() {
        return episodeNumber;
    }

    @Override
    public String displayInfo() {
        return getTitle() + " • " + getSeriesName() + " • " + getEpisodeNumber() + " • " + getDurationSec() + "s";
    }
    
    @Override
    public String toString() {
        return "Podcast{id='" + itemId + "', title='" + title + "', duration=" + durationSec + 
               "s, host='" + host + "', series='" + seriesName + "', episode=" + episodeNumber + "}";
    }
}
