//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.satbook.forms;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

/** 
 * MyEclipse Struts
 * Creation date: 09-20-2006
 * 
 * XDoclet definition:
 * @struts:form name="institutionRecord"
 */
public class InstitutionRecord extends ValidatorForm {

	// --------------------------------------------------------- Instance Variables

	/** instName property */
	private String instName;

	/** instId property */
	private String instId;

	// --------------------------------------------------------- Methods

	/** 
	 * Method validate
	 * @param mapping
	 * @param request
	 * @return ActionErrors
	 */
	public ActionErrors validate(
		ActionMapping mapping,
		HttpServletRequest request) {

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
	 * Returns the instName.
	 * @return String
	 */
	public String getInstName() {
		return instName;
	}

	/** 
	 * Set the instName.
	 * @param instName The instName to set
	 */
	public void setInstName(String instName) {
		this.instName = instName;
	}

	/** 
	 * Returns the instId.
	 * @return String
	 */
	public String getInstId() {
		return instId;
	}

	/** 
	 * Set the instId.
	 * @param instId The instId to set
	 */
	public void setInstId(String instId) {
		this.instId = instId;
	}

}

