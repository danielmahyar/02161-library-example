package dtu.library.app.exceptions;

public class OverdueMediaException extends Exception {

    /**
     * A new exception is constructed with error message errorMessage.
     * @param errorMessage the error message of the exception
     */
    public OverdueMediaException(String errorMessage) {
        super(errorMessage);
    }
}
