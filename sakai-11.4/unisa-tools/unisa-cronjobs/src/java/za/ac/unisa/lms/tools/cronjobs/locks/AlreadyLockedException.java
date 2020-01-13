package za.ac.unisa.lms.tools.cronjobs.locks;

public class AlreadyLockedException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4339549705037018243L;

	public AlreadyLockedException(String message) {
		super(message);
	}
	
	public AlreadyLockedException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public AlreadyLockedException(Throwable cause) {
		super(cause);
	}
}
