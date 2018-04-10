package za.ac.unisa.lms.tools.maintainstaff.forms;

public class DepartmentRecordForm {
	private String userCode; // user network code
	private String pnumber;	 // user personnel number
	private String depertmentId; // department id	
	private String title; // user title
	private String initials; // user initials
	private String surName; // user surname
  

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getInitials() {
		return initials;
	}
	public void setInitials(String initials) {
		this.initials = initials;
	}
	public String getSurName() {
		return surName;
	}
	public void setSurName(String surName) {
		this.surName = surName;
	}
	public String getDepertmentId() {
		return depertmentId;
	}
	public void setDepertmentId(String depertmentId) {
		this.depertmentId = depertmentId;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getPnumber() {
		return pnumber;
	}
	public void setPnumber(String pnumber) {
		this.pnumber = pnumber;
	}

	
}
