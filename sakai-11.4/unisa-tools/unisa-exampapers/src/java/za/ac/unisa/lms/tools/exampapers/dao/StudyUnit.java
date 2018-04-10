package za.ac.unisa.lms.tools.exampapers.dao;

public class StudyUnit {
	
	private String code;
	private String description;
	private String department;
	private String tuitionType;
	private String academicLevel;
	private String errorMsg;
	
	
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}	
	public String getTuitionType() {
		return tuitionType;
	}
	public void setTuitionType(String tuitionType) {
		this.tuitionType = tuitionType;
	}
	public String getAcademicLevel() {
		return academicLevel;
	}
	public void setAcademicLevel(String academicLevel) {
		this.academicLevel = academicLevel;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

}
