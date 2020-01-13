package za.ac.unisa.lms.tools.studentlist.dao;

public class StudentListSites {
	
	private String studyUnitCode;
	private String academicYear;
	private String semesterPeriod;
	private String lecturerSites;
	
	
	public String getAcademicyear() {
		return academicYear;
	}
	public void setAcademicyear(String academicYear) {
		this.academicYear = academicYear;
	}
	public String getSemesterperiod() {
		return semesterPeriod;
	}
	public void setSemesterperiod(String semesterPeriod) {
		this.semesterPeriod = semesterPeriod;
	}
	public String getStudyUnitCode() {
		return studyUnitCode;
	}
	public void setStudyUnitCode(String studyUnitCode) {
		this.studyUnitCode = studyUnitCode;
	}
	public String getLecturerSites() {
		return lecturerSites;
	}
	public void setLecturerSites(String lecturerSites) {
		this.lecturerSites = lecturerSites;
	}
	
	
}
