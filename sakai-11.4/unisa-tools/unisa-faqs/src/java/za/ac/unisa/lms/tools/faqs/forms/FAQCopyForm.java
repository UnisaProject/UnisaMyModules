//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.faqs.forms;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

/** 
 * MyEclipse Struts
 * Creation date: 07-07-2006
 * 
 * XDoclet definition:
 * @struts:form name="faqCopyForm"
 */
public class FAQCopyForm extends ValidatorForm {

	// --------------------------------------------------------- Instance Variables


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** copyto property */
	private String copyto;

	/** copyfrom property */
	private String copyfrom;

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
	 * Returns the copyto.
	 * @return String
	 */
	public String getCopyto() {
		return copyto;
	}

	/** 
	 * Set the copyto.
	 * @param copyto The copyto to set
	 */
	public void setCopyto(String copyto) {
		this.copyto = copyto;
	}

	/** 
	 * Returns the copyfrom.
	 * @return String
	 */
	public String getCopyfrom() {
		return copyfrom;
	}

	/** 
	 * Set the copyfrom.
	 * @param copyfrom The copyfrom to set
	 */
	public void setCopyfrom(String copyfrom) {
		this.copyfrom = copyfrom;
	}

}

