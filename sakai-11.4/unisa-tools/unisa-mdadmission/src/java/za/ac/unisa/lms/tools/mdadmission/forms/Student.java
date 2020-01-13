//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.mdadmission.forms;


/** 
 * MyEclipse Struts
 * Creation date: 08-18-2005
 * 
 * XDoclet definition:
 */
public class Student {

	// --------------------------------------------------------- Instance Variables

	public String getStudentNumber() {
		return studentNumber;
	}
	public void setStudentNumber(String studentNumber) {
		this.studentNumber = studentNumber;
	}
	public String getName() {
		return name;
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
	public AddressPH getAddressPH() {
		return addressPH;
	}
	public void setAddressPH(AddressPH addressPH) {
		this.addressPH = addressPH;
	}
	private String studentNumber;
	private String name;
	private Qualification qual;
	private AddressPH addressPH;
	public static final String STUDENT_NUMBER = "studentNumber";
	public static final String STUDENT_NAME = "studentName";
	

	// --------------------------------------------------------- Methods

	
}

