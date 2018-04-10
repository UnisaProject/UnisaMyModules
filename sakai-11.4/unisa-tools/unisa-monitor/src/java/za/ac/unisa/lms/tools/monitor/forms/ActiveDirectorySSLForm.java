//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.monitor.forms;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/** 
 * MyEclipse Struts
 * Creation date: 07-10-2006
 * 
 * XDoclet definition:
 * @struts:form name="activeDirectorySSLForm"
 */
public class ActiveDirectorySSLForm extends ActionForm {

	// --------------------------------------------------------- Instance Variables

	/** SSLOkay property */
	private boolean SSLOkay;
	public static final long serialVersionUID = 1;

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
	 * Returns the SSLOkay.
	 * @return Boolean
	 */
	public boolean getSSLOkay() {
		return SSLOkay;
	}

	/** 
	 * Set the SSLOkay.
	 * @param SSLOkay The SSLOkay to set
	 */
	public void setSSLOkay(boolean SSLOkay) {
		this.SSLOkay = SSLOkay;
	}

}

