//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.trackandtrace.forms;

import java.util.Vector;


import org.apache.struts.validator.ValidatorForm;

import za.ac.unisa.lms.tools.trackandtrace.Student;

/** 
 * MyEclipse Struts
 * Creation date: 11-09-2005
 * 
 * XDoclet definition:
 * @struts:form name="ParcelTrackingDisplayForm"
 */
public class ParcelTrackingDisplayForm extends ValidatorForm {

	// --------------------------------------------------------- Instance Variables

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** trackNumber property */
	private String trackNumber;

	/** dateSent property */
	private String dateSent;


	
	private Vector trackRecords;
	
	private boolean studentuser;
	
	/** Student Property **/
	private Student student;
	
    private boolean notvalid;
	

	
	public boolean getStudentuser() {
		return studentuser;
	}

	public void setStudentuser(boolean studentuser) {
		this.studentuser = studentuser;
	}

	/** 
	 * Method validate
	 * @param mapping
	 * @param request
	 * @return ActionErrors
	 */

	/** 
	 * Returns the trackNumber.
	 * @return String
	 */
	public String getTrackNumber() {
		return trackNumber;
	}

	/** 
	 * Set the trackNumber.
	 * @param trackNumber The trackNumber to set
	 */
	public void setTrackNumber(String trackNumber) {
		this.trackNumber = trackNumber;
	}

	public Vector getTrackRecords() {
		return trackRecords;
	}

	public void setTrackRecords(Vector records) {
		this.trackRecords = records;
	}	
	
	/** 
	 * Returns the dateSent.
	 * @return String
	 */
	public String getDateSent() {
		return dateSent;
	}

	/** 
	 * Set the dateSent.
	 * @param dateSent The dateSent to set
	 */
	public void setDateSent(String dateSent) {
		this.dateSent = dateSent;
	}

	/** 
	 * Returns the studNo.
	 * @return String
	 */


	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	/**
	 * @return Returns the notvalid.
	 */
	public boolean isNotvalid() {
		return notvalid;
	}

	/**
	 * @param notvalid The notvalid to set.
	 */
	public void setNotvalid(boolean notvalid) {
		this.notvalid = notvalid;
	}



}

