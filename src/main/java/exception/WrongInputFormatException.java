package exception;

import java.io.IOException;

/**
 * Signals that users input is incorrect.
 */
public class WrongInputFormatException extends IOException {
    public WrongInputFormatException(String message) {
        super(message);
    }
}
