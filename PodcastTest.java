public class PodcastTest {
    public static void main(String[] args) {
        // Create a test user
        User user = new User("U1", "Alice", "555-1234", "Premium");

        // Create a podcast instance
        Podcast podcast = new Podcast("P1", "Tech Talk Today", 1800, "John Doe", "AI Weekly", 5);

        // Test playback
        System.out.println("🎧 Testing Podcast Playback:\n");
        podcast.play();
        podcast.pause();
        podcast.stop();

        // Test saving & unsaving
        podcast.saveForLater(user);
        podcast.unsave(user);

        System.out.println("\n✅ Podcast test completed successfully!");
    }
}