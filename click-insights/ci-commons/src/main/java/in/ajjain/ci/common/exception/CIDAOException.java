package in.ajjain.ci.common.exception;

/**
 * The Class CIDAOException.
 */
public class CIDAOException extends RuntimeException {

	/**
	 * Instantiates a new CIDAO exception.
	 */
	public CIDAOException() {
		super();
	}

	/**
	 * Instantiates a new CIDAO exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 * @param enableSuppression the enable suppression
	 * @param writableStackTrace the writable stack trace
	 */
	public CIDAOException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * Instantiates a new CIDAO exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public CIDAOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new CIDAO exception.
	 *
	 * @param message the message
	 */
	public CIDAOException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new CIDAO exception.
	 *
	 * @param cause the cause
	 */
	public CIDAOException(Throwable cause) {
		super(cause);
	}

}
