package za.ac.unisa.lms.tools.libmaint.forms;

import org.apache.struts.validator.ValidatorForm;

public class NewsTitleForm extends ValidatorForm {

	private int newsTitleId;
	private String newsTitleDesc;
	private String enabled;
	private boolean checked;
	private boolean inUse;
	
	public int getNewsTitleId() {
		return newsTitleId;
	}
	public void setNewsTitleId(int newsTitleId) {
		this.newsTitleId = newsTitleId;
	}
	public String getNewsTitleDesc() {
		return newsTitleDesc;
	}
	public void setNewsTitleDesc(String newsTitleDesc) {
		this.newsTitleDesc = newsTitleDesc;
	}
	public String getEnabled() {
		return enabled;
	}
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public boolean isInUse() {
		return inUse;
	}
	public void setInUse(boolean inUse) {
		this.inUse = inUse;
	}
	
}
