package za.ac.unisa.lms.tools.tutorprebooks.dao;

public class CourseDetailsBean {

	private String coursecode;
	private String userId;
	private String tutorNr;
	private String numberOfTutors;
	private String lastModifiedBy;
	private String department;
	
	
	
	public String getNumberOfTutors() {
		return numberOfTutors;
	}
	public void setNumberOfTutors(String numberOfTutors) {
		this.numberOfTutors = numberOfTutors;
	}
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
	public String getTutorNr() {
		return tutorNr;
	}
	public void setTutorNr(String tutorNr) {
		this.tutorNr = tutorNr;
	}
	public String getCoursecode() {
		return coursecode;
	}
	public void setCoursecode(String coursecode) {
		this.coursecode = coursecode;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	
}
