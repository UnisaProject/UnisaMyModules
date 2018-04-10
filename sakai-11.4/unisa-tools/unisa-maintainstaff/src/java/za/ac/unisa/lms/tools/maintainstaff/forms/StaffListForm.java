package za.ac.unisa.lms.tools.maintainstaff.forms;

import java.util.ArrayList;

public class StaffListForm {
	
	private String persNo; // personnel number
	private String novellUserCode; // user network code
	private ArrayList details; // user details
	
	public ArrayList getDetails() {
		return details;
	}
	public void setDetails(ArrayList details) {
		details = details;
	}
	public String getPersNo() {
		return persNo;
	}
	public void setPersNo(String persNo) {
		this.persNo = persNo;
	}
	public String getNovellUserCode() {
		return novellUserCode;
	}
	public void setNovellUserCode(String novellUserCode) {
		this.novellUserCode = novellUserCode;
	}

}
