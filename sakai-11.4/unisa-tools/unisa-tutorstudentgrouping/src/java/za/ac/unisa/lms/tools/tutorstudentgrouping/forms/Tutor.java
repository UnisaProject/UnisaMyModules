package za.ac.unisa.lms.tools.tutorstudentgrouping.forms;

import za.ac.unisa.lms.domain.general.Person;

public class Tutor {
	private Person person;
	private String role;
	private String tutoringMode;
	private String tutorModeDesc;
	private int numberGroupsAllocated;
	private int totalStudentsAllocated;
	
	public String getTutorModeDesc() {
		return tutorModeDesc;
	}
	public void setTutorModeDesc(String tutorModeDesc) {
		this.tutorModeDesc = tutorModeDesc;
	}
	public String getTutoringMode() {
		return tutoringMode;
	}
	public void setTutoringMode(String tutoringMode) {
		this.tutoringMode = tutoringMode;
	}
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public int getNumberGroupsAllocated() {
		return numberGroupsAllocated;
	}
	public void setNumberGroupsAllocated(int numberGroupsAllocated) {
		this.numberGroupsAllocated = numberGroupsAllocated;
	}
	public int getTotalStudentsAllocated() {
		return totalStudentsAllocated;
	}
	public void setTotalStudentsAllocated(int totalStudentsAllocated) {
		this.totalStudentsAllocated = totalStudentsAllocated;
	}
	
	
	
}
