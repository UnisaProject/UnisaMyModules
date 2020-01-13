//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.contactus.forms;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class ContactUsForm extends ActionForm {

	private String categorySelected;
	private String libraryCategorySelected;
	private String studentNumberFormal;
	private String studentNumberNonFormal;

	//student view C3 variables
	private String firstNames;
	private String surname;
	private String maiden;
	private String email;
	private String birthDateY;
	private String birthDateM;
	private String birthDateD;
	private String postalAddress;
	private String id;
	private String passport;

	private String button;
	private String invalidMessage;

	//myUnisa enquiries
	private String myUnisaCategorySelected;
	private String myUnisaEmailAddress;
	private String myUnisaStudentNumber;
	private String myUnisaContactNumber;
	private String myUnisaEnquiryMessage;

	//forgot student number
	private String forgotSurname;
	private String forgotName;
	private String forgotdoby;
	private String forgotdobm;
	private String forgotdobd;

	private String dbError;


	public void reset(ActionMapping mapping, HttpServletRequest request) {

	}

	public String getCategorySelected() {
		return categorySelected;
	}

	public void setCategorySelected(String categorySelected) {
		this.categorySelected = categorySelected;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstNames() {
		return firstNames;
	}

	public void setFirstNames(String firstNames) {
		this.firstNames = firstNames;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}


	public String getButton() {
		return button;
	}

	public void setButton(String button) {
		this.button = button;
	}

	public String getInvalidMessage() {
		return invalidMessage;
	}

	public void setInvalidMessage(String invalidMessage) {
		this.invalidMessage = invalidMessage;
	}

	public String getBirthDateD() {
		return birthDateD;
	}

	public void setBirthDateD(String birthDateD) {
		this.birthDateD = birthDateD;
	}

	public String getBirthDateM() {
		return birthDateM;
	}

	public void setBirthDateM(String birthDateM) {
		this.birthDateM = birthDateM;
	}

	public String getBirthDateY() {
		return birthDateY;
	}

	public void setBirthDateY(String birthDateY) {
		this.birthDateY = birthDateY;
	}

	public String getMyUnisaCategorySelected() {
		return myUnisaCategorySelected;
	}

	public void setMyUnisaCategorySelected(String myUnisaCategorySelected) {
		this.myUnisaCategorySelected = myUnisaCategorySelected;
	}

	public String getMyUnisaContactNumber() {
		return myUnisaContactNumber;
	}

	public void setMyUnisaContactNumber(String myUnisaContactNumber) {
		this.myUnisaContactNumber = myUnisaContactNumber;
	}

	public String getMyUnisaEmailAddress() {
		return myUnisaEmailAddress;
	}

	public void setMyUnisaEmailAddress(String myUnisaEmailAddress) {
		this.myUnisaEmailAddress = myUnisaEmailAddress;
	}

	public String getMyUnisaStudentNumber() {
		return myUnisaStudentNumber;
	}

	public void setMyUnisaStudentNumber(String myUnisaStudentNumber) {
		this.myUnisaStudentNumber = myUnisaStudentNumber;
	}

	public String getMyUnisaEnquiryMessage() {
		return myUnisaEnquiryMessage;
	}

	public void setMyUnisaEnquiryMessage(String myUnisaEnquiryMessage) {
		this.myUnisaEnquiryMessage = myUnisaEnquiryMessage;
	}

	public String getForgotdobd() {
		return forgotdobd;
	}

	public void setForgotdobd(String forgotdobd) {
		this.forgotdobd = forgotdobd;
	}

	public String getForgotdobm() {
		return forgotdobm;
	}

	public void setForgotdobm(String forgotdobm) {
		this.forgotdobm = forgotdobm;
	}

	public String getForgotdoby() {
		return forgotdoby;
	}

	public void setForgotdoby(String forgotdoby) {
		this.forgotdoby = forgotdoby;
	}

	public String getForgotName() {
		return forgotName;
	}

	public void setForgotName(String forgotName) {
		this.forgotName = forgotName;
	}

	public String getForgotSurname() {
		return forgotSurname;
	}

	public void setForgotSurname(String forgotSurname) {
		this.forgotSurname = forgotSurname;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMaiden() {
		return maiden;
	}

	public void setMaiden(String maiden) {
		this.maiden = maiden;
	}

	public String getPassport() {
		return passport;
	}

	public void setPassport(String passport) {
		this.passport = passport;
	}

	public String getPostalAddress() {
		return postalAddress;
	}

	public void setPostalAddress(String postalAddress) {
		this.postalAddress = postalAddress;
	}

	public String getStudentNumberFormal() {
		return studentNumberFormal;
	}

	public void setStudentNumberFormal(String studentNumberFormal) {
		this.studentNumberFormal = studentNumberFormal;
	}

	public String getStudentNumberNonFormal() {
		return studentNumberNonFormal;
	}

	public void setStudentNumberNonFormal(String studentNumberNonFormal) {
		this.studentNumberNonFormal = studentNumberNonFormal;
	}

	public String getDbError() {
		return dbError;
	}

	public void setDbError(String dbError) {
		this.dbError = dbError;
	}

	public String getLibraryCategorySelected() {
		return libraryCategorySelected;
	}

	public void setLibraryCategorySelected(String libraryCategorySelected) {
		this.libraryCategorySelected = libraryCategorySelected;
	}


}

