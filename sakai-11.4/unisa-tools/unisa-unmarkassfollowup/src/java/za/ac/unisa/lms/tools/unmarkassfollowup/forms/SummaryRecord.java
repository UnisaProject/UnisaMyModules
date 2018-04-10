package za.ac.unisa.lms.tools.unmarkassfollowup.forms;

import za.ac.unisa.lms.domain.general.Person;

public class SummaryRecord {
	
	private String daysOutstandingRange;
	private String daysOutstanding;
	private String accumalate;
	private String total;
	private String collegeAbbreviation;
	private String schoolAbbreviation;
	private DepartmentRecord department;
	private String studyUnit;
	private String assignmentNr;
	private Person primLecturer;
	private Short firstValueInRange;
	private Short lastValueInRange;
	
	public Short getLastValueInRange() {
		return lastValueInRange;
	}
	public void setLastValueInRange(Short lastValueInRange) {
		this.lastValueInRange = lastValueInRange;
	}
	public String getDaysOutstanding() {
		return daysOutstanding;
	}
	public void setDaysOutstanding(String daysOutstanding) {
		this.daysOutstanding = daysOutstanding;
	}
	public Short getFirstValueInRange() {
		return firstValueInRange;
	}
	public void setFirstValueInRange(Short firstValueInRange) {
		this.firstValueInRange = firstValueInRange;
	}
	public String getCollegeAbbreviation() {
		return collegeAbbreviation;
	}
	public void setCollegeAbbreviation(String collegeAbbreviation) {
		this.collegeAbbreviation = collegeAbbreviation;
	}
	public String getSchoolAbbreviation() {
		return schoolAbbreviation;
	}
	public void setSchoolAbbreviation(String schoolAbbreviation) {
		this.schoolAbbreviation = schoolAbbreviation;
	}
	public DepartmentRecord getDepartment() {
		return department;
	}
	public void setDepartment(DepartmentRecord department) {
		this.department = department;
	}	
	public String getDaysOutstandingRange() {
		return daysOutstandingRange;
	}
	public void setDaysOutstandingRange(String daysOutstandingRange) {
		this.daysOutstandingRange = daysOutstandingRange;
	}
	public String getAccumalate() {
		return accumalate;
	}
	public void setAccumalate(String accumalate) {
		this.accumalate = accumalate;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getStudyUnit() {
		return studyUnit;
	}
	public void setStudyUnit(String studyUnit) {
		this.studyUnit = studyUnit;
	}
	public String getAssignmentNr() {
		return assignmentNr;
	}
	public void setAssignmentNr(String assignmentNr) {
		this.assignmentNr = assignmentNr;
	}
	public Person getPrimLecturer() {
		return primLecturer;
	}
	public void setPrimLecturer(Person primLecturer) {
		this.primLecturer = primLecturer;
	}	
	
}
