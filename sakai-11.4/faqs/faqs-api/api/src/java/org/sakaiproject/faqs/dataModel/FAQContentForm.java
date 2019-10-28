package org.sakaiproject.faqs.dataModel;

import java.util.List;

public class FAQContentForm {
	private List content;
	private FaqCategory category;
	private Integer previousId;
	private Integer nextId;
	
	
 
	public List getContent() {
		return content;
	}
	public void setContent(List content) {
		this.content = content;
	}
	public FaqCategory getCategory() {
		return category;
	}
	public void setCategory(FaqCategory category) {
		this.category = category;
	}
	public Integer getPreviousId() {
		return previousId;
	}
	public void setPreviousId(Integer previousId) {
		this.previousId = previousId;
	}
	public Integer getNextId() {
		return nextId;
	}
	public void setNextId(Integer nextId) {
		this.nextId = nextId;
	}
	
	
	
}
