package app;
import java.util.List;
import java.util.Scanner;

import auth.AuthService;
import auth.Session;
import catalog.Catalog;
import domain.MediaItem;
import domain.Song;
import user.Artist;
import user.Listener;
import user.Role;

import catalog.DataLoader;

public class MusicSystemCLI {
    private AuthService auth;
    private Catalog catalog;
    private Scanner scanner;
    private Session currentSession; //track who is logged in

    public MusicSystemCLI() {
        this.auth = new AuthService();
        this.catalog = Catalog.getInstance();
        this.scanner = new Scanner(System.in);
        this.currentSession = null;

        // Load data from CSV files
        String base = "catalog/data/";
        DataLoader.loadArtists(base + "artists.csv", auth);
        DataLoader.loadSongs(base + "songs.csv", catalog);
        DataLoader.loadListeners(base + "listeners.csv", auth);
    }

    public void run() {
        System.out.println("🎵 Welcome to the Music System! 🎵\\n");

        boolean running = true;

        while (running) {
            if (currentSession == null) {
                showMainMenu();
                int choice = getIntInput("Enter choice: ");
                running = handleMainMenu(choice);
            } else {
                if (currentSession.getRole() == Role.ARTIST) {
                    showArtistMenu();
                    int choice = getIntInput("Enter choice: ");
                    running = handleArtistMenu(choice);
                } else {
                    Listener listener = (Listener) auth.getUserBySession(currentSession.getSessionId());
                    showListenerMenu(listener);
                    int choice = getIntInput("Enter choice: ");
                    running = handleListenerMenu(choice);
                }
            }
        }

        System.out.println("Thanks for using the Music System!");
        scanner.close();
    }

    private void showMainMenu() {
        System.out.println("\n===== MAIN MENU =====");
        System.out.println("1. Register as Artist");
        System.out.println("2. Register as Listener");
        System.out.println("3. Login");
        System.out.println("4. Exit");
    }

    private void showArtistMenu() {
        System.out.println("\n===== ARTIST MENU =====");
        System.out.println("1. Publish Song");
        System.out.println("2. View My Songs");
        System.out.println("3. Remove Song");
        System.out.println("4. Logout");
    }

    private void showListenerMenu(Listener listener) {
        System.out.println("\n===== LISTENER MENU =====");
        System.out.println("1. Search Songs");
        System.out.println("2. Save Song");
        System.out.println("3. Unsave Song");
        System.out.println("4. View Saved Songs");
        System.out.println("5. Play Song");
        if (listener.isPlaying()) {
            System.out.println("6. Pause");
        }
        if (listener.isPlaying() || listener.isPaused()) {
            System.out.println("7. Stop");
        }
        System.out.println("8. View Playback Info");
        System.out.println("9. Logout");   
    }

    private boolean handleMainMenu(int choice) {
        switch (choice) {
            case 1:
                registerArtist();
                return true;
            case 2:
                registerListener();
                return true;
            case 3:
                login();
                return true;
            case 4:
                return false; // exit
            default:
                System.out.println("Invalid choice!");
                return true;
        }
    }

    private void registerArtist() {
        System.out.println("\n--- Register as Artist ---");
        String accountId = getStringInput("Username: ");
        String password = getStringInput("Password: ");
        String displayName = getStringInput("Display Name: ");
        String stageName = getStringInput("Stage Name: ");   
        
        currentSession = auth.registerArtist(accountId, password, displayName, stageName);

        if (currentSession != null) {
            System.out.println("✓ Registration successful! Welcome, " + displayName + "!");
        } else {
            System.out.println("✗ Username already taken!");
        }
    }

    private void registerListener() {
        System.out.println("\n--- Register as Listener ---");
        String accountId = getStringInput("Username: ");
        String password = getStringInput("Password: ");
        String displayName = getStringInput("Display Name: ");
        
        currentSession = auth.registerListener(accountId, password, displayName);

        if (currentSession != null) {
            System.out.println("✓ Registration successful! Welcome, " + displayName + "!");
        } else {
            System.out.println("✗ Username already taken!");
        }
    }

    private void login() {
        System.out.println("\n--- Login ---");
        String accountId = getStringInput("Username: ");
        String password = getStringInput("Password: ");

        currentSession = auth.login(accountId, password);

        if (currentSession != null) {
            System.out.println("✓ Login successful!");
        } else {
            System.out.println("✗ Invalid username or password!");
        }
    }

    private boolean handleArtistMenu(int choice) {
        Artist artist = (Artist) auth.getUserBySession(currentSession.getSessionId());

        switch (choice) {
            case 1:
                publishSong(artist);
                return true;
            case 2:
                viewMySongs(artist);
                return true;
            case 3:
                removeSong(artist);
                return true;
            case 4:
                logout();
                return true;
            default:
                System.out.println("Invalid choice!");
                return true;
        }
    }

    public void publishSong(Artist artist) {
        System.out.println("\n--- Publish Song ---");
        String title = getStringInput("Title: ");
        String album = getStringInput("Album: ");
        String genre = getStringInput("Genre: ");
        int duration = getIntInput("Duration (seconds): ");

        artist.publishSong(catalog, title, duration, album, genre);
        System.out.println("✓ Song published successfully!");
    }

    private void viewMySongs(Artist artist) {
        System.out.println("\n--- My Published Songs ---");
        if (artist.getOwnedItemIds().isEmpty()) {
            System.out.println("You have no published songs yet!");
        }
        for (String itemId : artist.getOwnedItemIds()) {
            MediaItem item = catalog.getItem(itemId);
            if (item != null) {
                System.out.println("  ♪ " + item.displayInfo());
            }
        }
    }

    public void removeSong(Artist artist) {
        System.out.println("\n--- Remove Song ---");
        viewMySongs(artist);

        if (artist.getOwnedItemIds().isEmpty()) {
            System.out.println("You haven't published any songs yet.");
            return; // exit, no songs
        }

        System.out.println("Your published songs:");
        for (String itemId : artist.getOwnedItemIds()) {
            MediaItem item = catalog.getItem(itemId);
            if (item != null) {
                System.out.println("  ♪ " + item.displayInfo());
            }
        }

        String title = getStringInput("\n Enter song title to remove: ");
        
        for (String itemId : artist.getOwnedItemIds()) {
            MediaItem item = catalog.getItem(itemId);
            if (item instanceof Song) {
                Song song = (Song) item;
                if (song.getTitle().equalsIgnoreCase(title)) {
                    artist.removeOwnedItem(catalog, itemId);
                    System.out.println("✓ Song removed!");
                    return;
                }
            }
        }
        System.out.println("✗ Song not found!");
    }

    private void logout(){
        auth.logout(currentSession.getSessionId());
        currentSession = null;
        System.out.println("✓ Logged out successfully!");
    }

    private boolean handleListenerMenu(int choice) {
        Listener listener = (Listener) auth.getUserBySession(currentSession.getSessionId());

        switch (choice) {
            case 1:
                searchSongs();
                return true;
            case 2:
                saveSong(listener);
                return true;
            case 3:
                unsaveSong(listener);
                return true;
            case 4:
                viewSavedSongs(listener);
                return true;
            case 5:
                playSong(listener);
                return true;
            case 6:
                if (listener.isPlaying()) {
                    listener.pause(catalog);
                    System.out.println("⏸ Paused");
                    System.out.println(listener.getPlaybackInfo(catalog));
                    System.out.println("Tip: Choose option 5 (Play Song) and enter the same title to resume.");
                } else {
                    System.out.println("Nothing is playing to pause");
                }
                return true;
            case 7:
                if (listener.isPlaying() || listener.isPaused()) {
                    listener.stop();
                    System.out.println("⏹ Stopped");
                } else {
                    System.out.println("Nothing is playing to stop."); 
                }
                return true;
            case 8:
                System.out.println(listener.getPlaybackInfo(catalog));
                return true;
            case 9:
                logout();
                return true;
            default:
                System.out.println("Invalid choice!");
                return true;
        }
    }

    private void searchSongs() {
        System.out.println("\n--- Search Songs ---");
        String query = getStringInput("Search: ");

        List<String> results = catalog.search(query);
        if (results.isEmpty()) {
            System.out.println("No songs found.");
        } else {
            System.out.println("Results: ");
            for (String itemId : results) {
                MediaItem item = catalog.getItem(itemId);
                System.out.println("  ♪ " + item.displayInfo());
            }
        }
    }

    private void saveSong(Listener listener) {
        String title = getStringInput("Enter song title to save: ");
        String itemId = listener.saveByExactTitle(catalog, title);

        if (itemId != null) {
            System.out.println("✓ Song saved!");
        } else {
            System.out.println("✗ Song not found!");
        }
    }

    private void viewSavedSongs(Listener listener) {
        System.out.println("\n--- Your Saved Songs ---");

        List<Song> savedSongs = listener.getSavedSongs(catalog);
        
        if (savedSongs.isEmpty()) {
            System.out.println("You haven't saved any songs yet.");
        } else {
            for (Song song : savedSongs) {
                System.out.println("  ♪ " + song.displayInfo());
            }
            System.out.println("\nTotal: " + savedSongs.size() + " song(s)");
        }
    }

    private void unsaveSong(Listener listener) {
        System.out.println("\n--- Unsave Song ---");

        String title = getStringInput("Enter song title to unsave: ");

        String itemId = listener.unsaveByExactTitle(catalog, title);
        if (itemId != null) {
            System.out.println("✓ Song removed from your library!");
        } else {
            System.out.println("✗ Song not found!");
        }
    }

    private void playSong(Listener listener) {
        String title = getStringInput("Enter song title to play: ");
        boolean success = listener.playByExactTitle(catalog, title);
        if (success) {
            System.out.println("▶ Playing: " + title);
        } else {
            System.out.println("✗ Song not found!");
        }
    }

    private String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = Integer.parseInt(scanner.nextLine().trim());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
            }
        }
    }

    public static void main(String[] args) {
        MusicSystemCLI cli = new MusicSystemCLI();
        cli.run();
    }
}
