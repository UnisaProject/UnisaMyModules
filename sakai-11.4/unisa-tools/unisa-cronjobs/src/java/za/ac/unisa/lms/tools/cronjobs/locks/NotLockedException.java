package za.ac.unisa.lms.tools.cronjobs.locks;

public class NotLockedException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5649011685010021381L;

	public NotLockedException(String message) {
		super(message);
	}
	
	public NotLockedException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public NotLockedException(Throwable cause) {
		super(cause);
	}
}
