package za.ac.unisa.lms.tools.mylife.forms;

import org.apache.struts.validator.ValidatorForm;

/**
 * @author UdcsWeb
 *
 */


public class MyUnisaMylifeForm extends ValidatorForm{
	private String studentNr;
	private String email;
	private String myLifePwd;
	
	private String surname;
	private String dateOfBirth;
	private String birthYear;
	private String birthMonth;
	private String birthDay;
	private String fullNames;
	private String id_nr;
	private String passport_nr;
	private boolean agreeCheck1 = false;
	private boolean agreeCheck2 = false;
	private boolean agreeCheck3 = false;
	private boolean agreeCheck4 = false;
	private boolean agreeCheck5 = false;
	private String cellNr;
	private String mylifePath;
	private String myUnisaPath;
	
	
	
	public String getMyUnisaPath() {
		return myUnisaPath;
	}

	public void setMyUnisaPath(String myUnisaPath) {
		this.myUnisaPath = myUnisaPath;
	}

	public boolean isAgreeCheck5() {
		return agreeCheck5;
	}

	public void setAgreeCheck5(boolean agreeCheck5) {
		this.agreeCheck5 = agreeCheck5;
	}
	public String getMylifePath() {
		return mylifePath;
	}

	public void setMylifePath(String mylifePath) {
		this.mylifePath = mylifePath;
	}

	public String getPassport_nr() {
		return passport_nr;
	}

	public void setPassport_nr(String passport_nr) {
		this.passport_nr = passport_nr;
	}

	public String getId_nr() {
		return id_nr;
	}

	public void setId_nr(String id_nr) {
		this.id_nr = id_nr;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getBirthYear() {
		return birthYear;
	}

	public void setBirthYear(String birthYear) {
		this.birthYear = birthYear;
	}

	public String getBirthMonth() {
		return birthMonth;
	}

	public void setBirthMonth(String birthMonth) {
		this.birthMonth = birthMonth;
	}

	public String getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}

	public String getFullNames() {
		return fullNames;
	}

	public void setFullNames(String fullNames) {
		this.fullNames = fullNames;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}



	public String getStudentNr() {
		return studentNr;
	}

	public void setStudentNr(String studentNr) {
		this.studentNr = studentNr;
	}

	public String getMyLifePwd() {
		return myLifePwd;
	}

	public void setMyLifePwd(String myLifePwd) {
		this.myLifePwd = myLifePwd;
	}

	public boolean isAgreeCheck1() {
		return agreeCheck1;
	}

	public void setAgreeCheck1(boolean agreeCheck1) {
		this.agreeCheck1 = agreeCheck1;
	}

	public boolean isAgreeCheck2() {
		return agreeCheck2;
	}

	public void setAgreeCheck2(boolean agreeCheck2) {
		this.agreeCheck2 = agreeCheck2;
	}

	public boolean isAgreeCheck3() {
		return agreeCheck3;
	}

	public void setAgreeCheck3(boolean agreeCheck3) {
		this.agreeCheck3 = agreeCheck3;
	}

	public boolean isAgreeCheck4() {
		return agreeCheck4;
	}

	public void setAgreeCheck4(boolean agreeCheck4) {
		this.agreeCheck4 = agreeCheck4;
	}

	public String getCellNr() {
		return cellNr;
	}

	public void setCellNr(String cellNr) {
		this.cellNr = cellNr;
	}

}
