//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.studentnosearch.forms;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

/** 
 * MyEclipse Struts
 * Creation date: 01-13-2006
 * 
 * XDoclet definition:
 * @struts:form name="studentNumber"
 */
public class StudentNoSearchForm extends ValidatorForm {

	// --------------------------------------------------------- Instance Variables


	private String unisaStudentNumber;
	private Student student = new Student();	
	private boolean input = true;
	private String messageToStudent = "";
	private String searchType = "";

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getMessageToStudent() {
		return messageToStudent;
	}

	public void setMessageToStudent(String messageToStudent) {
		this.messageToStudent = messageToStudent;
	}

	public boolean isInput() {
		return input;
	}

	public void setInput(boolean input) {
		this.input = input;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}


	// --------------------------------------------------------- Methods

	/** 
	 * Method validate
	 * @param mapping
	 * @param request
	 * @return ActionErrors
	 */
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {

		// TODO Auto-generated method stub
		return null;
	}

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {

		// TODO Auto-generated method stub
	}

	/** 
	 * Returns the unisaStudentNumber.
	 * @return String
	 */
	public String getUnisaStudentNumber() {
		return unisaStudentNumber;
	}

	/** 
	 * Set the unisaStudentNumber.
	 * @param unisaStudentNumber The unisaStudentNumber to set
	 */
	public void setUnisaStudentNumber(String unisaStudentNumber) {
		this.unisaStudentNumber = unisaStudentNumber;
	}

}

