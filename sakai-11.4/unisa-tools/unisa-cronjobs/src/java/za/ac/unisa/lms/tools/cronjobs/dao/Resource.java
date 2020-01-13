package za.ac.unisa.lms.tools.cronjobs.dao;

public class Resource {
	private String url;
	private String type;
	private String title;
	private String description;
	private String dateCreated;
	private boolean collection;
	private int startAcademicYear;
	private int endAcademicYear;
	private String language;
	private String refNo;
	private int webid;
	
	public int getWebid() {
		return webid;
	}
	public void setWebid(int webid) {
		this.webid = webid;
	}
	public boolean isCollection() {
		return collection;
	}
	public void setCollection(boolean collection) {
		this.collection = collection;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getEndAcademicYear() {
		return endAcademicYear;
	}
	public void setEndAcademicYear(int endAcademicYear) {
		this.endAcademicYear = endAcademicYear;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getRefNo() {
		return refNo;
	}
	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}
	public int getStartAcademicYear() {
		return startAcademicYear;
	}
	public void setStartAcademicYear(int startAcademicYear) {
		this.startAcademicYear = startAcademicYear;
	}
}