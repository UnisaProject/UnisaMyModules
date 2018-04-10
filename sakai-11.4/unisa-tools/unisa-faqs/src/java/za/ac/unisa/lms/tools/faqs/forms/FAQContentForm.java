//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.faqs.forms;

import org.apache.struts.validator.ValidatorForm;

import za.ac.unisa.lms.tools.faqs.dao.FaqCategory;
import za.ac.unisa.lms.tools.faqs.dao.FaqContent;

/** 
 * MyEclipse Struts
 * Creation date: 11-24-2005
 * 
 * XDoclet definition:
 * @struts:form name="faqContentForm"
 */
public class FAQContentForm extends ValidatorForm {

	// --------------------------------------------------------- Instance Variables

	private static final long serialVersionUID = 1L;

	private FaqContent content;
	private FaqCategory category;
	private Integer previousId;
	private Integer nextId;
	
	public FAQContentForm() {
		super();
		content = new FaqContent();
		category = new FaqCategory();
	}

	public FaqCategory getCategory() {
		return category;
	}

	public void setCategory(FaqCategory category) {
		this.category = category;
	}

	public FaqContent getContent() {
		return content;
	}

	public void setContent(FaqContent content) {
		this.content = content;
	}

	public Integer getNextId() {
		return nextId;
	}

	public void setNextId(Integer nextId) {
		this.nextId = nextId;
	}

	public Integer getPreviousId() {
		return previousId;
	}

	public void setPreviousId(Integer previousId) {
		this.previousId = previousId;
	}

}

