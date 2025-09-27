import java.util.HashSet;
import java.util.Set;

public abstract class MediaItem implements Playable, Savable, Searchable {
    protected final String itemId;
    protected String title;
    protected int durationSec;
    protected String ownerArtistID;
    protected final Set<String> savedByUserIds = new HashSet<>();

    private enum PlaybackState { STOPPED, PLAYING, PAUSED }
    private PlaybackState state = PlaybackState.STOPPED;

    public MediaItem(String itemId, String title, int durationSec, String ownerArtistID) {
        this.itemId = itemId;
        this.title = title;
        this.durationSec = durationSec;
        this.ownerArtistID = ownerArtistID;
    }

    public String getItemId() {
        return itemId;
    }

    public String getTitle() {
        return title;
    }

    public int getDurationSec() {
        return durationSec;
    }

    public String getOwnerArtistId() {
        return ownerArtistID;
    }

    public Set<String> getSavedByUserIds() {
        return savedByUserIds;
    }

    @Override
    public void play() {
        state = PlaybackState.PLAYING;
    }

    @Override
    public void pause() {
        state = PlaybackState.PAUSED;
    }

    @Override
    public void stop() {
        state = PlaybackState.STOPPED;
    }

    // TODO: Savable defaults

    // TODO: Searchable default

}
