# Music-System-CS151-Fall-2025
## 1. Overview
Welcome to our music streaming system! 👋🎵

Our project designs and implements a music streaming system for Artists (content creators) and Listeners (consumers). We include user authentication, media management (songs and podcasts), and playback controls (play, pause, stop).

## 2. Design 
We aim to have a clear separate of concerns across authentication, catalog, media items, and the CLI application. 

### 2.1 Architecture:
- Our `Catalog` acts as the single source of truth for all content, centralizing search functionality and simplifying read-mostly access
- Inheritance allows common fields to reside in abstract classes, with subclasses adding unique attributes
  - `MediaItem` is extended by concrete types such as Song and Podcast, with shared fields like itemId, title, and durationSec defined in MediaItem
  - `User` class is extended by Artist and Listener, each inheriting base fields such as userId and displayName
- `Searchable` interface allows different media objects to share a common search behavior across the system
- State management via the `PlayerState` enum controls player states `PLAYING`, `PAUSED`, and `STOPPED` for clear playback transitions

### 2.2 Class Structure
- `auth`:
    - `AuthService`: Registers accounts, verifies credentials, validates and creates sessions
    - `Session`: Holds session metadata (sessionId, accountId, role, linkedId, active flag)
    - `Account`: User credentials and role binding 
- `catalog`:
    - `Catalog`: content of all MediaItems; add/list/get and search entry points
    - `Library`: per-listener collection of saved item IDs
    - `DataLoader`: Initializes clibrary/catalog information from files
- `domain`:
    - `MediaItem` (abstract): itemId, title, durationSec, common behavior
    - `Song`: concrete implmentation of `MediaItem`
    - `Podcast`: concrete implmentation of `MediaItem`
    - `Searchable` (interface): contract for matching queries (title)
- `user`:
    - `User` (abstract): base user identity 
    - `Artist`: can create/publish items to Catalog
    - `Listener`: maintains a Library, playback state
    - `Role`: ARTIST, LISTENER for authorization checks in CLI
- `app`:
    - `MusicSystemCLI`: text UI, handles auth -> catalog/library -> playback flows
    - `ConsoleUI`: utility class providing ANSI color-coded console output (success, error, info, warning messages) and formatted input prompts to create a beautified and colorful user experience
### 2.3 Key Design Considerations
- Our **single source of truth** allows all content to live in the Catalog. Library stores only item IDs (references)
- We designed with **extensibility** in mind, making implementing new media types and adding more search capabilities easier
- We considered **security**, deciding our AuthService handles the credential checks, while session carries the role
- We intentionally decided on hashmaps and sets as we considered **performance**. We have a read-mostly catalog with simple indexing (such as in-memory mapping by itemId and title), and library operations are O(1) on item IDs

### 2.4 UML Diagram
![UML Diagram](images/Music-System%20UML%20class.png)

### 2.5 Exception Handling
1. **NumberFormatException** - Catches invalid number input (built-in)
2. **InvalidCredentialsException** - Catches login failures (custom)
3. **ItemNotFoundException** - Catches missing songs (custom)
4. **File I/O** (`DataLoader.java`) - Catches file-loading errors (e.g., IOException) during CSV reads

### 2.6 Future Work
- Current file-based persistence via DataLoader keeps setup simple but isn’t realistic for account creation. A future improvement would be migrating to a database to support live updates
- Introduce indexing and metadata filters (e.g., genre, album, artist) to enable more search capabilities
- Implement better authentication practice by implementing password hashing

## 3. Installation Instructions
1. Clone the repository
```
git clone https://github.com/juliahusainzada/Music-System-CS151-Fall-2025.git
cd Music-System-CS151-Fall-2025/src
```

2. Compile the file
```
javac app/MusicSystemCLI.java
```

3. Launch the CLI
```
java app/MusicSystemCLI.java
```
4. Enjoy the music player!

## 4. Usage
Once you've launched the CLI, you'll be greeted with the Main Menu, where you can register or log in as a user (User)

### 4.1 Main Menu Options:
1. Register - Create an account as either an Artist or Listener
2. Login - Access your exist account and session
3. Exit - Close the application

### 4.2.1 Artist Menu:
If you register/log in as an Artist, you will see the Artist Menu, where you can:
1. Publish Media - Add new music to global Catalog
2. View Published Songs - List all media uploaded by you
3. Remove song - Delete the media from Catalog

### 4.2.2 Listener Menu
If you register/log in as a Listener, you will see the Listener Menu, where you can:
1. Search Songs - Query the Catalog for available media by title 
2. Save/Unsave Songs - Add or remove items in personal Library
3. Play Song - Start playback
4. Pause/ Stop - Manage your playback state
5. View Saved Songs - Display songs saved to your personal library 

### 4.3 Example Workflows:
**Artist:**
1. Register as Artist
2. Log in → “Publish Song” → Enter title & duration
3. You can verify it appears in the catalog!

**Listener:**
1. Register as Listener (logs you in)
2. “Search Songs” → View results from catalog
3. “Save Song” to add to your Library
4. “Play Song” → “Pause” → “Stop” and see state transitions
5. “View Saved Songs”

## 5. Contributions
Together, we designed the overall architecture, high-level design, and project choice.

| Team Member | Areas of Contribution |
|--------------|------------------------|
| **Julia Husainzada** | Designed package structure; implemented `AuthService`, `Session`, and CLI flow; developed `Catalog` and `DataLoader` |
| **Dushan Siriwardana** | Implemented `Library`, `Artist`, `Podcast` functionality; added playback features (`Play`, `Pause`, `Stop`); managed player state transitions with `PlayerState` enum |
| **Houmei Li** | Implemented `Searchable` interface, `User` abstract class |
