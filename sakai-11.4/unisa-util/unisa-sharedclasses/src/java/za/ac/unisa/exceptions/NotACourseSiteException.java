package za.ac.unisa.exceptions;

public class NotACourseSiteException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4350565300700882134L;
	private String siteId;
	
	public NotACourseSiteException(String siteId) {
		this.siteId = siteId;
	}
	
	public String getSiteId() {
		return siteId;
	}

	public String getMessage() {
		return siteId + " is not a valid course site";
	}
	
	
	
}
