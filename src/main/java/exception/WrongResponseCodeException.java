package exception;

/**
 * Signals that host didn`t return success code.
 */
public class WrongResponseCodeException extends Exception{
    public WrongResponseCodeException(String message){
        super(message);
    }
}
