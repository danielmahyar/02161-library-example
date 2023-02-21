package dtu.library.app.exceptions;

public class TooManyMediaException extends Exception{


    /**
     *
     */
    private static final long serialVersionUID = 5644804383994321392L;

    /**
     * A new exception is constructed with error message errorMessage.
     * @param errorMessage the error message of the exception
     */
    public TooManyMediaException(String errorMessage) {
        super(errorMessage);
    }
}
