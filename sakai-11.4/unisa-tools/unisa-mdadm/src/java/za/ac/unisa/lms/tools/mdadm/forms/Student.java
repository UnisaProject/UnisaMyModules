//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.mdadm.forms;

import za.ac.unisa.lms.tools.mdadm.forms.Contact;


/** 
 * MyEclipse Struts
 * Creation date: 08-18-2005
 * 
 * XDoclet definition:
 */
public class Student {

	// --------------------------------------------------------- Instance Variables

	
	public String getName() {
		return name;
	}	
	public String getStudentNumber() {
		return studentNumber;
	}

	public void setStudentNumber(String studentNumber) {
		this.studentNumber = studentNumber;
	}

	public String getPrintName() {
		return printName;
	}
	public void setPrintName(String printName) {
		this.printName = printName;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Qualification getQual() {
		return qual;
	}
	public void setQual(Qualification qual) {
		this.qual = qual;
	}	
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	public Contact getContactInfo() {
		return contactInfo;
	}
	public void setContactInfo(Contact contactInfo) {
		this.contactInfo = contactInfo;
	}

	private String studentNumber;
	private String name;
	private String printName;
	private String gender;
	private String birthDate;
	private String country;
	private Qualification qual;	
	private Contact contactInfo;
	

	// --------------------------------------------------------- Methods

	
}

