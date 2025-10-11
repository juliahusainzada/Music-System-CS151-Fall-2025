package catalog;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import auth.AuthService;
import domain.Song;

public class DataLoader {
    
    public static void loadArtists(String filename, AuthService auth) {
        try (BufferedReader br = Files.newBufferedReader(Paths.get(filename))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                
                String accountId = parts[0].trim();
                String password = parts[1].trim();
                String userId = parts[2].trim();
                String displayName = parts[3].trim();
                String stageName = parts[4].trim();

                auth.addPreloadedArtist(accountId, password, userId, displayName, stageName);
            }
            System.out.println("✅ Artists loaded successfully!");
        } catch (IOException e) {
            System.out.println("Error loading artists: " + e.getMessage());
        }
    }
    
    public static void loadSongs(String filename, Catalog catalog) {
        try (BufferedReader br = Files.newBufferedReader(Paths.get(filename))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                
                String songId = parts[0].trim();
                String title = parts[1].trim();
                int durationSec = Integer.parseInt(parts[2].trim());
                String ownerArtistId = parts[3].trim();
                String album = parts[4].trim();
                String genre = parts[5].trim();

                Song song = new Song(songId, title, durationSec, ownerArtistId, album, genre);
                catalog.addItem(song);
                
                // Add song to artist's owned items
                user.Artist artist = catalog.getArtist(ownerArtistId);
                if (artist != null) {
                    artist.getOwnedItemIds().add(songId);
                }
            }
            System.out.println("✅ Songs loaded successfully!");
        } catch (IOException e) {
            System.out.println("Error loading songs: " + e.getMessage());
        }
    }
    
    public static void loadListeners(String filename, AuthService auth) {
        try (BufferedReader br = Files.newBufferedReader(Paths.get(filename))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                String accountId = parts[0].trim();
                String password = parts[1].trim();
                String userId = parts[2].trim();
                String displayName = parts[3].trim();

                auth.addPreloadedListener(accountId, password, userId, displayName);
            }
            System.out.println("✅ Listeners loaded successfully!");
        } catch (IOException e) {
            System.out.println("Error loading listeners: " + e.getMessage());
        }
    }
}
