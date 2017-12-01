package exception;

/**
 * Signals that users input is incorrect.
 */
public class WrongInputFormatException extends Exception {
    public WrongInputFormatException(String message) {
        super(message);
    }
}
