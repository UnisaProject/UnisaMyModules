//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.faqs.forms;

import java.util.List;
import java.util.Vector;

import org.apache.struts.validator.ValidatorForm;

public class FAQListForm extends ValidatorForm {

	// --------------------------------------------------------- Instance Variables

	private static final long serialVersionUID = 1L;

	private List categories;
	private Vector expandedCategories;

	private Integer specificContentId;

	private Integer expandCategoryId;

	private String siteId;

	private boolean addFAQContent;
	private boolean editFAQContent;
	private boolean deleteFAQContent;

	private boolean addFAQCategory;
	private boolean editFAQCategory;
	private boolean deleteFAQCategory;

	private boolean faqbuttonExists =false;
	private boolean faqExist=false;

	//private boolean isColumn;




	public List getCategories() {
		return categories;
	}

	public Object getCategoryIndexed(int index) {
		return categories.get(index);
	}

	public void setCategories(List categories) {
		this.categories = categories;
	}

	public Vector getExpandedCategories() {
		return expandedCategories;
	}

	public void setExpandedCategories(Vector expandedCategories) {
		this.expandedCategories = expandedCategories;
	}

	public Integer getExpandCategoryId() {
		return expandCategoryId;
	}

	public void setExpandCategoryId(Integer expandCategoryId) {
		this.expandCategoryId = expandCategoryId;
	}

	public Integer getSpecificContentId() {
		return specificContentId;
	}

	public void setSpecificContentId(Integer specificContentId) {
		this.specificContentId = specificContentId;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public boolean isAddFAQContent() {
		return addFAQContent;
	}

	public void setAddFAQContent(boolean addFAQContent) {
		this.addFAQContent = addFAQContent;
	}

	public boolean isAddFAQCategory() {
		return addFAQCategory;
	}

	public void setAddFAQCategory(boolean addFAQCategory) {
		this.addFAQCategory = addFAQCategory;
	}

	public boolean isDeleteFAQCategory() {
		return deleteFAQCategory;
	}

	public void setDeleteFAQCategory(boolean deleteFAQCategory) {
		this.deleteFAQCategory = deleteFAQCategory;
	}

	public boolean isDeleteFAQContent() {
		return deleteFAQContent;
	}

	public void setDeleteFAQContent(boolean deleteFAQContent) {
		this.deleteFAQContent = deleteFAQContent;
	}

	public boolean isEditFAQCategory() {
		return editFAQCategory;
	}

	public void setEditFAQCategory(boolean editFAQCategory) {
		this.editFAQCategory = editFAQCategory;
	}

	public boolean isEditFAQContent() {
		return editFAQContent;
	}

	public void setEditFAQContent(boolean editFAQContent) {
		this.editFAQContent = editFAQContent;
	}

	public boolean isFaqExist() {
		return faqExist;
	}

	public void setFaqExist(boolean faqExist) {
		this.faqExist = faqExist;
	}

	public boolean isFaqbuttonExists() {
		return faqbuttonExists;
	}

	public void setFaqbuttonExists(boolean faqbuttonExists) {
		this.faqbuttonExists = faqbuttonExists;
	}

}

