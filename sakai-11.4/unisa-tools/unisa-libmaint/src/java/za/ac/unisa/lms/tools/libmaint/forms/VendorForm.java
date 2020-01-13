package za.ac.unisa.lms.tools.libmaint.forms;

import org.apache.struts.validator.ValidatorForm;

public class VendorForm extends ValidatorForm{

	private int vendorId;
	private String vendor;
	private String onCampusURL;
	private String offCampusURL;
	private String logoFileName;
	private String logoURL;
	private String fullLogoURL;

	private String enabled;
	private boolean checked;
	private boolean inUse;
	
	
	public int getVendorId() {
		return vendorId;
	}
	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}
	public String getVendor() {
		return vendor;
	}
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	public String getOnCampusURL() {
		return onCampusURL;
	}
	public void setOnCampusURL(String onCampusURL) {
		this.onCampusURL = onCampusURL;
	}
	public String getOffCampusURL() {
		return offCampusURL;
	}
	public void setOffCampusURL(String offCampusURL) {
		this.offCampusURL = offCampusURL;
	}
	public String getLogoFileName() {
		return logoFileName;
	}
	public void setLogoFileName(String logoFileName) {
		this.logoFileName = logoFileName;
	}
	public String getLogoURL() {
		return logoURL;
	}
	public void setLogoURL(String logoURL) {
		this.logoURL = logoURL;
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
	
	public String getFullLogoURL() {
		return fullLogoURL;
	}
	public void setFullLogoURL(String fullLogoURL) {
		this.fullLogoURL = fullLogoURL;
	}
	
}
