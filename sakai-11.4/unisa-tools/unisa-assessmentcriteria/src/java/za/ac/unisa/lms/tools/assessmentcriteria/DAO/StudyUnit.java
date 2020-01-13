package za.ac.unisa.lms.tools.assessmentcriteria.DAO;

public class StudyUnit {
	
	private String code;
	private String description;
	private String formalTuitionFlag;
	private String academicLevel;
	private Integer experiental_duration;
	private String researchFlag;
	private String department;
	private String nqfCategory;
	private String postGraduateFlag;
	private String autoExamAdmission;
	
	public String getAutoExamAdmission() {
		return autoExamAdmission;
	}
	public void setAutoExamAdmission(String autoExamAdmission) {
		this.autoExamAdmission = autoExamAdmission;
	}
	public String getPostGraduateFlag() {
		return postGraduateFlag;
	}
	public void setPostGraduateFlag(String postGraduateFlag) {
		this.postGraduateFlag = postGraduateFlag;
	}
	public String getNqfCategory() {
		return nqfCategory;
	}
	public void setNqfCategory(String nqfCategory) {
		this.nqfCategory = nqfCategory;
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
	public String getResearchFlag() {
		return researchFlag;
	}
	public void setResearchFlag(String researchFlag) {
		this.researchFlag = researchFlag;
	}
	public Integer getExperiental_duration() {
		return experiental_duration;
	}
	public void setExperiental_duration(Integer experiental_duration) {
		this.experiental_duration = experiental_duration;
	}
	

}
