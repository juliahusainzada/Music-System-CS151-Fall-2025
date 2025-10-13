package exceptions;

/**
 * Custom exception thrown when login fails due to invalid credentials
 */
public class InvalidCredentialsException extends Exception {
    
    public InvalidCredentialsException(String message) {
        super(message);
    }
    
    public InvalidCredentialsException() {
        super("Invalid username or password");
    }
}
