package za.ac.unisa.lms.tools.libmaint.forms;

import java.util.ArrayList;

import org.apache.struts.validator.ValidatorForm;

public class ResourceForm extends ValidatorForm{
	
	private int resourceId;
	private String resourceName;
	private String resourceDesc;
	private String onCampusURL;
	private String offCampusURL;
	private int vendorId;
	private String vendorDesc;
	private String textId;
	private String textDesc;
	private String cdRom;
	private String passwordExist;
	private String login;
	private String password;
	private String trainingURL;
	private String enabled;
	private String alert;
	private String refManagementURL;

	private boolean checked;
	private boolean inUse;
	private ArrayList placements = new ArrayList();
	private ArrayList subjects = new ArrayList();
	
	public String getAlert() {
		return alert;
	}
	public void setAlert(String alert) {
		this.alert = alert;
	}
	
	public ArrayList getPlacements() {
		return placements;
	}
	public void setPlacements(ArrayList placements) {
		this.placements = placements;
	}
	public ArrayList getSubjects() {
		return subjects;
	}
	public void setSubjects(ArrayList subjects) {
		this.subjects = subjects;
	}
	public int getResourceId() {
		return resourceId;
	}
	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public String getResourceDesc() {
		return resourceDesc;
	}
	public void setResourceDesc(String resourceDesc) {
		this.resourceDesc = resourceDesc;
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
	public int getVendorId() {
		return vendorId;
	}
	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}
	public String getVendorDesc() {
		return vendorDesc;
	}
	public void setVendorDesc(String vendorDesc) {
		this.vendorDesc = vendorDesc;
	}
	public String getTextId() {
		return textId;
	}
	public void setTextId(String textId) {
		this.textId = textId;
	}
	public String getTextDesc() {
		return textDesc;
	}
	public void setTextDesc(String textDesc) {
		this.textDesc = textDesc;
	}
	public String getCdRom() {
		return cdRom;
	}
	public void setCdRom(String cdRom) {
		this.cdRom = cdRom;
	}
	public String getPasswordExist() {
		return passwordExist;
	}
	public void setPasswordExist(String passwordExist) {
		this.passwordExist = passwordExist;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getTrainingURL() {
		return trainingURL;
	}
	public void setTrainingURL(String trainingURL) {
		this.trainingURL = trainingURL;
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

	public String getRefManagementURL() {
		return refManagementURL;
	}
	public void setRefManagementURL(String refManagementURL) {
		this.refManagementURL = refManagementURL;
	}
}
