//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.faqs.forms;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

import za.ac.unisa.lms.tools.faqs.dao.FaqCategory;

/** 
 * MyEclipse Struts
 * Creation date: 11-24-2005
 * 
 * XDoclet definition:
 * @struts:form name="faqCategoryForm"
 */
public class FAQCategoryForm extends ValidatorForm {

	// --------------------------------------------------------- Instance Variables

	private static final long serialVersionUID = 1L;
	
	private FaqCategory category;
	
	public FAQCategoryForm() {
		super();
		category = new FaqCategory();
	}

	public void reset(ActionMapping mapping, HttpServletRequest request) {

		// TODO Auto-generated method stub
	}

	public FaqCategory getCategory() {
		return category;
	}

	public void setCategory(FaqCategory category) {
		this.category = category;
	}
	
	
	
}

