package za.ac.unisa.lms.tools.tutorstudentgrouping.forms;

import za.ac.unisa.lms.domain.general.Person;

public class TutorStudentGroup {
	private int studentNr;
	private Person tutor;
	private String role;
	private TutoringMode tutoringMode;
	private int groupNumber;
	private String assignDate;
	private String inactiveDate;
	private int totalStudentsAllocated;
	
	public Person getTutor() {
		return tutor;
	}
	public void setTutor(Person tutor) {
		this.tutor = tutor;
	}
	public int getStudentNr() {
		return studentNr;
	}
	public void setStudentNr(int studentNr) {
		this.studentNr = studentNr;
	}
	public String getAssignDate() {
		return assignDate;
	}
	public void setAssignDate(String assignDate) {
		this.assignDate = assignDate;
	}
	public String getInactiveDate() {
		return inactiveDate;
	}
	public void setInactiveDate(String inactiveDate) {
		this.inactiveDate = inactiveDate;
	}
	public TutoringMode getTutoringMode() {
		return tutoringMode;
	}
	public void setTutoringMode(TutoringMode tutoringMode) {
		this.tutoringMode = tutoringMode;
	}	
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public int getGroupNumber() {
		return groupNumber;
	}
	public void setGroupNumber(int groupNumber) {
		this.groupNumber = groupNumber;
	}
	public int getTotalStudentsAllocated() {
		return totalStudentsAllocated;
	}
	public void setTotalStudentsAllocated(int totalStudentsAllocated) {
		this.totalStudentsAllocated = totalStudentsAllocated;
	}
	
	
}
