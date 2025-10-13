package domain;

public class Podcast extends MediaItem {
    private String host;
    private String seriesName;
    private int episodeNumber;

    public Podcast(String itemId, String title, int durationSec, String host, String seriesName, int episodeNumber) {
        super(itemId, title, durationSec, host);
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
}