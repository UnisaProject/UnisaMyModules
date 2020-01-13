package za.ac.unisa.lms.tools.tutorprebooks.dao;

public class TutorPreBooksDetails {
	
	private String collegeName;
	private String schoolName;
	private String departmentName;
	private String module;
	private int noOfTutorBooks;
	private String modifiedBy;
	
	public String getCollegeName() {
		return collegeName;
	}

	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}
	
	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	
	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	
	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}
	
	public int getNoOfTutorBooks() {
		return noOfTutorBooks;
	}

	public void setNoOfTutorBooks(int noOfTutorBooks) {
		this.noOfTutorBooks = noOfTutorBooks;
	}
	
	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
}
