package org.sakaiproject.struts.example.forms;

import org.apache.struts.validator.ValidatorForm;

@SuppressWarnings("serial")
public class NewHtmlAreaTestForm extends ValidatorForm {

	private String title;
	private String description;
	private String content;
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
}
