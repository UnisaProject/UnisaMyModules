package za.ac.unisa.lms.tools.unmarkassfollowup.forms;

public class SchoolRecord {
	private Short code;
	private String description;
	private Short collegeCode;
	private String abbreviation;
	private String collegeSchoolCode;
	
	public String getCollegeSchoolCode() {
//		collegeSchoolCode = this.collegeCode.toString().trim() + "~" + this.code.toString().trim();
		return collegeSchoolCode;
	}
	public void setCollegeSchoolCode(String collegeSchoolCode) {
		this.collegeSchoolCode = collegeSchoolCode;
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
	public Short getCollegeCode() {
		return collegeCode;
	}
	public void setCollegeCode(Short collegeCode) {
		this.collegeCode = collegeCode;
	}
	public String getAbbreviation() {
		return abbreviation;
	}
	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}
	
	
}
