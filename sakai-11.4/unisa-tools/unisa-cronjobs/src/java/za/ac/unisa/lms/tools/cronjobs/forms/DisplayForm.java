//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.cronjobs.forms;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

/** 
 * MyEclipse Struts
 * Creation date: 08-08-2005
 * 
 * XDoclet definition:
 * @struts:form name="display"
 */
public class DisplayForm extends ActionForm {

	// --------------------------------------------------------- Instance Variables

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** searchString property */
	private String searchString;

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

		ActionErrors errors = new ActionErrors();
		
		if ((searchString == null) || (searchString.equals(""))) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("test"));
		}
		
		return errors;
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
	 * Returns the searchString.
	 * @return String
	 */
	public String getSearchString() {
		return searchString;
	}

	/** 
	 * Set the searchString.
	 * @param searchString The searchString to set
	 */
	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

}

