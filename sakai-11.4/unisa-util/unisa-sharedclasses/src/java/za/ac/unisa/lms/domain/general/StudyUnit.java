package za.ac.unisa.lms.domain.general;

public class StudyUnit {
	private String code;
	private String engLongDesc; 
	private Short departmentCode;
	private Short collegeCode;
	private Short schoolCode;
	private String formalTuitionFlag;
	private String academicLevel;
	private Short experiental_duration;
	private String researchFlag;
	private String nqfCategory;
	private Short from_year;
	private Short to_year;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getEngLongDesc() {
		return engLongDesc;
	}
	public void setEngLongDesc(String engLongDesc) {
		this.engLongDesc = engLongDesc;
	}
	public Short getDepartmentCode() {
		return departmentCode;
	}
	public void setDepartmentCode(Short departmentCode) {
		this.departmentCode = departmentCode;
	}
	public Short getCollegeCode() {
		return collegeCode;
	}
	public void setCollegeCode(Short collegeCode) {
		this.collegeCode = collegeCode;
	}
	public Short getSchoolCode() {
		return schoolCode;
	}
	public void setSchoolCode(Short schoolCode) {
		this.schoolCode = schoolCode;
	}
	public String getFormalTuitionFlag() {
		return formalTuitionFlag;
	}
	public void setFormalTuitionFlag(String formalTuitionFlag) {
		this.formalTuitionFlag = formalTuitionFlag;
	}
	public String getAcademicLevel() {
		return academicLevel;
	}
	public void setAcademicLevel(String academicLevel) {
		this.academicLevel = academicLevel;
	}		
	public Short getExperiental_duration() {
		return experiental_duration;
	}
	public void setExperiental_duration(Short experientalDuration) {
		experiental_duration = experientalDuration;
	}
	public String getResearchFlag() {
		return researchFlag;
	}
	public void setResearchFlag(String researchFlag) {
		this.researchFlag = researchFlag;
	}
	public String getNqfCategory() {
		return nqfCategory;
	}
	public void setNqfCategory(String nqfCategory) {
		this.nqfCategory = nqfCategory;
	}
	public Short getFrom_year() {
		return from_year;
	}
	public void setFrom_year(Short fromYear) {
		from_year = fromYear;
	}
	public Short getTo_year() {
		return to_year;
	}
	public void setTo_year(Short toYear) {
		to_year = toYear;
	}		
}

