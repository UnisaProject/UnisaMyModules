package za.ac.unisa.lms.domain.general;

public class Person {
	private String personnelNumber;
	private String novellUserId;
	private String name;
	private String emailAddress;
	private String contactNumber;
	private String departmentCode;
	private String studentSystemUser;
	private String resignDate;
	private String nameReverse;
	private String rcCode;
	
	public String getRcCode() {
		return rcCode;
	}
	public void setRcCode(String rcCode) {
		this.rcCode = rcCode;
	}
	public String getNameReverse() {
		return nameReverse;
	}
	public void setNameReverse(String nameReverse) {
		this.nameReverse = nameReverse;
	}
	public String getResignDate() {
		return resignDate;
	}
	public void setResignDate(String resignDate) {
		this.resignDate = resignDate;
	}
	public String getStudentSystemUser() {
		return studentSystemUser;
	}
	public void setStudentSystemUser(String studentSystemUser) {
		this.studentSystemUser = studentSystemUser;
	}
	public String getDepartmentCode() {
		return departmentCode;
	}
	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}
	public String getPersonnelNumber() {
		return personnelNumber;
	}
	public void setPersonnelNumber(String personnelNumber) {
		this.personnelNumber = personnelNumber;
	}
	public String getNovellUserId() {
		return novellUserId;
	}
	public void setNovellUserId(String novellUserId) {
		this.novellUserId = novellUserId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}	
}
