package za.ac.unisa.lms.tools.canceluser.dao;

public class StudentInfo {
	private String studentNr;
	private String studentName;
	private String joinDate;
	private String surname;
	private String currentlyRegistered;

	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getStudentNr() {
		return studentNr;
	}
	public void setStudentNr(String studentNr) {
		this.studentNr = studentNr;
	}
	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}
	public String getJoinDate() {
		return joinDate;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getCurrentlyRegistered() {
		return currentlyRegistered;
	}
	public void setCurrentlyRegistered(String currentlyRegistered) {
		this.currentlyRegistered = currentlyRegistered;
	}

	
}
