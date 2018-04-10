package za.ac.unisa.lms.tools.finalmarkconcession.forms;

import za.ac.unisa.lms.domain.general.Person;

public class AuthRequestRecord {
	
	private String studyUnit;
	private Short examYear;
	private Short examPeriod;
	private Integer studentNumber;
	private String status;
	private DepartmentRecord department;
	private AlternativeExamOpportunityRecord fiConcession ;
	private ResultRecord result;	
	
	public ResultRecord getResult() {
		return result;
	}
	public void setResult(ResultRecord result) {
		this.result = result;
	}
	public DepartmentRecord getDepartment() {
		return department;
	}
	public void setDepartment(DepartmentRecord department) {
		this.department = department;
	}
	public AlternativeExamOpportunityRecord getFiConcession() {
		return fiConcession;
	}
	public void setFiConcession(AlternativeExamOpportunityRecord fiConcession) {
		this.fiConcession = fiConcession;
	}
	public String getStudyUnit() {
		return studyUnit;
	}
	public void setStudyUnit(String studyUnit) {
		this.studyUnit = studyUnit;
	}
	public Short getExamYear() {
		return examYear;
	}
	public void setExamYear(Short examYear) {
		this.examYear = examYear;
	}
	public Short getExamPeriod() {
		return examPeriod;
	}
	public void setExamPeriod(Short examPeriod) {
		this.examPeriod = examPeriod;
	}
	public Integer getStudentNumber() {
		return studentNumber;
	}
	public void setStudentNumber(Integer studentNumber) {
		this.studentNumber = studentNumber;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
