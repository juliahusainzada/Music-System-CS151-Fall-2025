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
    public void play() {
        System.out.println("🎙️ Playing podcast: " + title + " (Episode " + episodeNumber + ") hosted by " + host);
    }

    @Override
    public void pause() {
        System.out.println("⏸️ Podcast paused: " + title);
    }

    @Override
    public void stop() {
        System.out.println("⏹️ Podcast stopped: " + title);
    }

    @Override
    public void saveForLater(User user) {
        System.out.println("💾 Podcast '" + title + "' saved for user: " + user.getDisplayName());
    }

    @Override
    public void unsave(User user) {
        System.out.println("🗑️ Podcast '" + title + "' unsaved for user: " + user.getDisplayName());
    }
}