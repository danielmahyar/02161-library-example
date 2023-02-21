package dtu.library.app.exceptions;

public class MissingPaymentException extends Exception {

    public MissingPaymentException(String error_message){
        super(error_message);
    }
}
