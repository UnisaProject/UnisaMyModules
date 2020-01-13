package za.ac.unisa.lms.tools.mylifesupport.forms;

import org.apache.struts.validator.ValidatorForm;


public class MyLifeSupportForm extends ValidatorForm{
	/**
	 * 
	 */
	private String studentNr;
	private String surName;
	private String studentID;
	private String birthDate;
	private String firstName;
	private String contactNr;
	private String mylifEmail;
	private String  mylifePwd;
	private String studentAddress;
	private String regStatus;
	private String regDate;
	private String joinDate;
	private String statusFlag;
	private String cellNumber;
	private String homeNumber;
	private String passportNr;
	private String mylifeStatus;
	private String highestQual;
	private String specialityCode;
	

	

	public String getMylifeStatus() {
		return mylifeStatus;
	}

	public void setMylifeStatus(String mylifeStatus) {
		this.mylifeStatus = mylifeStatus;
	}

	public String getPassportNr() {
		return passportNr;
	}

	public void setPassportNr(String passportNr) {
		this.passportNr = passportNr;
	}

	public String getCellNumber() {
		return cellNumber;
	}

	public void setCellNumber(String cellNumber) {
		this.cellNumber = cellNumber;
	}

	

	public String getHomeNumber() {
		return homeNumber;
	}

	public void setHomeNumber(String homeNumber) {
		this.homeNumber = homeNumber;
	}

	public String getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}

	public String getStatusFlag() {
		return statusFlag;
	}

	public void setStatusFlag(String statusFlag) {
		this.statusFlag = statusFlag;
	}

	/*public String getSemType() {
		return semType;
	}

	public void setSemType(String semType) {
		this.semType = semType;
	}*/

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getRegStatus() {
		return regStatus;
	}

	public void setRegStatus(String regStatus) {
		this.regStatus = regStatus;
	}

	public String getStudentAddress() {
		return studentAddress;
	}

	public void setStudentAddress(String studentAddress) {
		this.studentAddress = studentAddress;
	}

	
	public String getStudentID() {
		return studentID;
	}

	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}

	public String getContactNr() {
		return contactNr;
	}

	public void setContactNr(String contactNr) {
		this.contactNr = contactNr;
	}

	public String getStudentNr() {
		return studentNr;
	}

	public void setStudentNr(String studentNr) {
		this.studentNr = studentNr;
	}

	public String getMylifEmail() {
		return mylifEmail;
	}

	public void setMylifEmail(String mylifEmail) {
		this.mylifEmail = mylifEmail;
	}

	public String getMylifePwd() {
		return mylifePwd;
	}

	public void setMylifePwd(String mylifePwd) {
		this.mylifePwd = mylifePwd;
	}

	public String getSurName() {
		return surName;
	}

	public void setSurName(String surName) {
		this.surName = surName;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getHighestQual() {
		return highestQual;
	}

	public void setHighestQual(String highestQual) {
		this.highestQual = highestQual;
	}

	public String getSpecialityCode() {
		return specialityCode;
	}

	public void setSpecialityCode(String specialityCode) {
		this.specialityCode = specialityCode;
	}
	

}
