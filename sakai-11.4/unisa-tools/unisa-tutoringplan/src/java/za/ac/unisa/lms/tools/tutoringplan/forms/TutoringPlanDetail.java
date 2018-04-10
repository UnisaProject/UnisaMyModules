package za.ac.unisa.lms.tools.tutoringplan.forms;

public class TutoringPlanDetail {
	private String departmentCode;
	private String departmentDesc;
	private String schoolCode;
	private String schoolAbbrev;
	private String schoolDesc;
	private String studyUnit;
	private TutoringMode tutoringMode;
	
	public String getDepartmentCode() {
		return departmentCode;
	}
	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}
	public String getDepartmentDesc() {
		return departmentDesc;
	}
	public void setDepartmentDesc(String departmentDesc) {
		this.departmentDesc = departmentDesc;
	}
	public String getSchoolCode() {
		return schoolCode;
	}
	public void setSchoolCode(String schoolCode) {
		this.schoolCode = schoolCode;
	}
	public String getSchoolAbbrev() {
		return schoolAbbrev;
	}
	public void setSchoolAbbrev(String schoolAbbrev) {
		this.schoolAbbrev = schoolAbbrev;
	}
	public String getSchoolDesc() {
		return schoolDesc;
	}
	public void setSchoolDesc(String schoolDesc) {
		this.schoolDesc = schoolDesc;
	}
	public String getStudyUnit() {
		return studyUnit;
	}
	public void setStudyUnit(String studyUnit) {
		this.studyUnit = studyUnit;
	}
	public TutoringMode getTutoringMode() {
		return tutoringMode;
	}
	public void setTutoringMode(TutoringMode tutoringMode) {
		this.tutoringMode = tutoringMode;
	}

}
