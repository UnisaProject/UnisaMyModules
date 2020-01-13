package za.ac.unisa.lms.tools.cronjobs.dao;

public class UnipooleMapping {
	private String toSiteId;
	private String fromSiteId;
	private String toModuleId;
	private String fromModuleId;
	private String title;
	
	public String getToSiteId() {
		return toSiteId;
	}
	public void setToSiteId(String toSiteId) {
		this.toSiteId = toSiteId;
	}
	
	
	public String getFromSiteId() {
		return fromSiteId;
	}
	public void setFromSiteId(String fromSiteId) {
		this.fromSiteId = fromSiteId;
	}
	
	public String getToModuleId() {
		return toModuleId;
	}	
	public void setToModuleId(String toModuleId) {
		this.toModuleId = toModuleId;
	}
	
	
	public String getFromModuleId() {
		return fromModuleId;
	}
	public void setFromModuleId(String fromModuleId) {
		this.fromModuleId = fromModuleId;
	}
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
} // end of public class UnipooleMapper