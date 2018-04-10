package za.ac.unisa.lms.tools.libmaint.forms;

import org.apache.struts.validator.ValidatorForm;

public class TextForm extends ValidatorForm{
	
	private int txtId;
	private String txtDesc;
	private String enabled = "Y";
	private boolean checked;
	private boolean inUse;

	
	/** Is the text record in use by a subject resource? */
	public boolean isInUse() {
		return inUse;
	}
	public void setInUse(boolean inUse) {
		this.inUse = inUse;
	}
	/** Checked for removal or update */
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	/** txtId (primary key */
	public int getTxtId() {
		return txtId;
	}
	public void setTxtId(int txtId) {
		this.txtId = txtId;
	}
	
	/** txtDesc Description of txt */
	public String getTxtDesc() {
		return txtDesc;
	}
	public void setTxtDesc(String txtDesc) {
		this.txtDesc = txtDesc;
	}
	
	/** enabled Y=enabled; N=disabled */
	public String getEnabled() {
		return enabled;
	}
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

}
