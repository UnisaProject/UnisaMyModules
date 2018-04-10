package za.ac.unisa.lms.tools.exampapers.dao;

public class Course {
	private String code;
	private Short academicYear;
	private Short semester;
	private String errorMsg;
	
	public Short getSemester() {
		return semester;
	}
	public void setSemester(Short semester) {
		this.semester = semester;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Short getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(Short academicYear) {
		this.academicYear = academicYear;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
}
