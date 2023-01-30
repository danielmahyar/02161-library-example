package dtu.library.app;

/**
 * The class represents an exception that is thrown to indicate that the 
 * intended operation is not allowed. In most cases, it is thrown, when the
 * administrator is not logged in, but the operation requires administrator authorization.
 * @author huba
 *
 */
public class OperationNotAllowedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5644804383994321392L;

	/**
	 * A new exception is constructed with error message errorMessage.
	 * @param errorMessage the error message of the exception
	 */
	public OperationNotAllowedException(String errorMessage) {
		super(errorMessage);
	}

}
