package app;
import java.util.Scanner;

/**
 * Text formatting and colors!
 */
public class ConsoleUI {
    private static final Scanner scanner = new Scanner(System.in);
    
    // ANSI color codes
    private static final String RESET = "\033[0m";
    private static final String GREEN = "\033[32m";
    private static final String RED = "\033[31m";
    private static final String CYAN = "\033[36m";
    private static final String YELLOW = "\033[33m";
    
    // Formatting
    public static void printHeader(String text) {
        System.out.println("\n" + text);
        System.out.println("─".repeat(text.length()));
    }
    
    public static void printSuccess(String message) {
        System.out.println(GREEN + message + RESET);
    }
    
    public static void printError(String message) {
        System.out.println(RED + message + RESET);
    }
    
    public static void printInfo(String message) {
        System.out.println(CYAN + message + RESET);
    }
    
    public static void printWarning(String message) {
        System.out.println(YELLOW + message + RESET);
    }
    public static String prompt(String message) {
        System.out.print(CYAN + message + " > " + RESET);
        return scanner.nextLine().trim();
    }

    // Prompt user for a number input
    public static int promptInt(String message) {
        System.out.print(CYAN + message + " > " + RESET);
        while (!scanner.hasNextInt()) {
            printError("Please enter a valid number!");
            System.out.print(CYAN + message + " > " + RESET);
            scanner.next();
        }
        return scanner.nextInt();
    }
}
