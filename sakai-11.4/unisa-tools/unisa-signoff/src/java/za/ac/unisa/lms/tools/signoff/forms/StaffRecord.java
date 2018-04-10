package za.ac.unisa.lms.tools.signoff.forms;

import org.apache.struts.action.ActionForm;

public class StaffRecord extends ActionForm{
	
	private String networkCode;
	private String staffNumber;
	private String head;
	private String novelUsrCode;
	private String fullName;
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getNovelUsrCode() {
		return novelUsrCode;
	}
	public void setNovelUsrCode(String novelUsrCode) {
		this.novelUsrCode = novelUsrCode;
	}
	public String getPersonelNum() {
		return personelNum;
	}
	public void setPersonelNum(String personelNum) {
		this.personelNum = personelNum;
	}
	private String personelNum;
	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
	}
	public String getNetworkCode() {
		return networkCode;
	}
	public void setNetworkCode(String networkCode) {
		this.networkCode = networkCode;
	}
	public String getStaffNumber() {
		return staffNumber;
	}
	public void setStaffNumber(String staffNumber) {
		this.staffNumber = staffNumber;
	}


}
