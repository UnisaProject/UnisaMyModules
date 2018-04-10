package za.ac.unisa.lms.tools.studentregistration.forms;

public class Staff {

	// --------------------------------------------------------- Instance Variables

	//Login Variables for new Applicant
	private String number;
	private String password;
	private String surname;
	private String firstnames;
	private String adminAdmission;
	private String admission;
	private String studentType;
	private boolean admin; 

	
	/**
	 * Returns the Staff Number.
	 * @return String
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * Set the Staff number.
	 * @param number The number to set
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstnames() {
		return firstnames;
	}

	public void setFirstnames(String firstnames) {
		firstnames = firstnames.replaceAll("'","`");
		this.firstnames = firstnames;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		surname = surname.replaceAll("'","`");
		this.surname = surname;
	}

	public String getAdminAdmission() {
		return adminAdmission;
	}
	public void setAdminAdmission(String adminAdmission) {
		this.adminAdmission = adminAdmission;
	}

	public String getAdmission() {
		return admission;
	}

	public void setAdmission(String admission) {
		this.admission = admission;
	}

	public String getStudentType() {
		return studentType;
	}

	public void setStudentType(String studentType) {
		this.studentType = studentType;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
}

