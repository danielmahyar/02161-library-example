package dtu.library.app.exceptions;

public class TooManyBookException extends Exception{


    /**
     *
     */
    private static final long serialVersionUID = 5644804383994321392L;

    /**
     * A new exception is constructed with error message errorMessage.
     * @param errorMessage the error message of the exception
     */
    public TooManyBookException(String errorMessage) {
        super(errorMessage);
    }
}
