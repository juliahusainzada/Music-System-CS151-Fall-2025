public class Main {
    public static void main(String[] args) {
        System.out.println("🎵 Welcome to the Music System!");
        System.out.println("--------------------------------");

        // 1️⃣ Create a user (User constructor: id, displayName, phoneNumber, subscription)
        User user = new User("U1", "Alice", "555-1234", "Premium");

        //  Try to use the getter from People (usually getDisplayName)
        System.out.println("User created: " + user.getDisplayName());

        // 2️⃣ Create a library
        Library library = new Library("My Music Library");
        System.out.println("Library created: " + library);

        // 3️⃣ Create a few songs
        Song song1 = new Song("S1", "Imagine", 183, "John Lennon", "Imagine", "Rock");
        Song song2 = new Song("S2", "Shape of You", 240, "Ed Sheeran", "Divide", "Pop");
        Song song3 = new Song("S3", "Bohemian Rhapsody", 355, "Queen", "A Night at the Opera", "Rock");

        // 4️⃣ Simulate saving songs for later
        song1.saveForLater(user);
        song2.saveForLater(user);
        song3.saveForLater(user);

        // 5️⃣ Simulate playback
        System.out.println("\n▶️ Now playing songs:");
        song1.play();
        System.out.println("Playing: " + song1.getTitle());
        song1.pause();
        System.out.println("Paused: " + song1.getTitle());
        song1.stop();
        System.out.println("Stopped: " + song1.getTitle());

        // 6️⃣ Show song info
        System.out.println("\n📜 Song Library Summary:");
        System.out.println("- " + song1.getTitle() + " (" + song1.getGenre() + ")");
        System.out.println("- " + song2.getTitle() + " (" + song2.getGenre() + ")");
        System.out.println("- " + song3.getTitle() + " (" + song3.getGenre() + ")");

        System.out.println("\n✅ Test completed successfully!");
    }
}