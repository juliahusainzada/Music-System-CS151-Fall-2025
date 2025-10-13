package config;

/**
 * Global constants for the Music System
 */
public final class Constants {
    /**
     * Maximum number of instances allowed for each class in the system
     */
    public static final int MAXIMUM_INSTANCES = 100;
    
    // Private constructor to prevent instantiation
    private Constants() {
        throw new AssertionError("Cannot instantiate Constants class");
    }
}
