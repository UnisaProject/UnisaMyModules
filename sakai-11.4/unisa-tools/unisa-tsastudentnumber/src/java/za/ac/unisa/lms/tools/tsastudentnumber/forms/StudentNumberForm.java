//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.tsastudentnumber.forms;

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
public class StudentNumberForm extends ValidatorForm {

	// --------------------------------------------------------- Instance Variables

	/** unisaStudentNumber property */
	private String unisaStudentNumber;

	/** tsaStudentNumber property */
	private String tsaStudentNumber;
	
	private boolean input = true;

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

	/** 
	 * Returns the tsaStudentNumber.
	 * @return String
	 */
	public String getTsaStudentNumber() {
		return tsaStudentNumber;
	}

	/** 
	 * Set the tsaStudentNumber.
	 * @param tsaStudentNumber The tsaStudentNumber to set
	 */
	public void setTsaStudentNumber(String tsaStudentNumber) {
		this.tsaStudentNumber = tsaStudentNumber;
	}

	public boolean isInput() {
		return input;
	}

	public void setInput(boolean input) {
		this.input = input;
	}

}

