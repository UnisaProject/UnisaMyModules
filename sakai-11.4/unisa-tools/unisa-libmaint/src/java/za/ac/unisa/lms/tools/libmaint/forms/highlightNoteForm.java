package za.ac.unisa.lms.tools.libmaint.forms;

import org.apache.struts.validator.ValidatorForm;

public class highlightNoteForm extends ValidatorForm {

	private int highId;
	private String highlight;
	private String enabled;
	private boolean checked;
	private boolean inUse;
	
	public int getHighId() {
		return highId;
	}
	public void setHighId(int highId) {
		this.highId = highId;
	}
	public String getHighlight() {
		return highlight;
	}
	public void setHighlight(String highlight) {
		this.highlight = highlight;
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
