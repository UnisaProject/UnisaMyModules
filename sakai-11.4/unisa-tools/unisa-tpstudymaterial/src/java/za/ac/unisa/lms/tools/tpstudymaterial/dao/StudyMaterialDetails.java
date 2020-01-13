package za.ac.unisa.lms.tools.tpstudymaterial.dao;

public class StudyMaterialDetails {

	private String courseCode;
	private String academicYear;
	private String semister;
	private String discription;
	private String implementationDate;
	private String webId;
	private String path;
	private String filesize;
	private boolean blockedStatus=false;
	private String shortDescription;
	
	public String getFilesize() {
		return filesize;
	}
	public void setFilesize(String filesize) {
		this.filesize = filesize;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getCourseCode() {
		return courseCode;
	}
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public String getSemister() {
		return semister;
	}
	public void setSemister(String semister) {
		this.semister = semister;
	}
	public String getDiscription() {
		return discription;
	}
	public void setDiscription(String discription) {
		this.discription = discription;
	}
	public String getImplementationDate() {
		return implementationDate;
	}
	public void setImplementationDate(String implementationDate) {
		this.implementationDate = implementationDate;
	}
	public String getWebId() {
		return webId;
	}
	public void setWebId(String webId) {
		this.webId = webId;
	}
	public boolean isBlockedStatus(){
		return blockedStatus;
	}
	public void setBlockedStatus(boolean blockedStatus){
		this.blockedStatus = blockedStatus;
	}
	/**
	 * @return the shortDescription
	 */
	public String getShortDescription() {
		return shortDescription;
	}
	/**
	 * @param shortDescription the shortDescription to set
	 */
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	
	
}
