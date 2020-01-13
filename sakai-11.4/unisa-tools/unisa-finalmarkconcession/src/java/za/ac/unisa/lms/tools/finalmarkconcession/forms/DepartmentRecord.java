package za.ac.unisa.lms.tools.finalmarkconcession.forms;

public class DepartmentRecord {
	private Short code;
	private String description;
	private Short collegeCode;
	
	public Short getCollegeCode() {
		return collegeCode;
	}
	public void setCollegeCode(Short collegeCode) {
		this.collegeCode = collegeCode;
	}
	public Short getCode() {
		return code;
	}
	public void setCode(Short code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
