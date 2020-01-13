package za.ac.unisa.lms.tools.cronjobs.locks;

public class LockTimeoutException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8129218162122798096L;

	public LockTimeoutException(String message) {
		super(message);
	}
	
	public LockTimeoutException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public LockTimeoutException(Throwable cause) {
		super(cause);
	}
}
