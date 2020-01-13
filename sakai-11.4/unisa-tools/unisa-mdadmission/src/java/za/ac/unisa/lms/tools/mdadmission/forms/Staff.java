package za.ac.unisa.lms.tools.mdadmission.forms;

import java.util.ArrayList;

public class Staff {
	
	private String staffNr="";
	private String name="";
	private String telephone="";
	private String emailAddress="";
	private String signOffDate="";
	private String signOffComment="";
	private String finalFlag="";
	private ArrayList <SignOffLevel> signOffLevel;
	
	public static final String STAFF_NAME = "staffName";
	
	public String getStaffNr() {
		return staffNr;
	}
	public void setStaffNr(String staffNr) {
		this.staffNr = staffNr;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getSignOffDate() {
		return signOffDate;
	}
	public void setSignOffDate(String signOffDate) {
		this.signOffDate = signOffDate;
	}
	public String getSignOffComment() {
		return signOffComment;
	}
	public void setSignOffComment(String signOffComment) {
		this.signOffComment = signOffComment;
	}
	public String getFinalFlag() {
		return finalFlag;
	}
	public void setFinalFlag(String finalFlag) {
		this.finalFlag = finalFlag;
	}
	public ArrayList<SignOffLevel> getSignOffLevelList() {
		return signOffLevel;
	}
	public void setSignOffLevelList(ArrayList<SignOffLevel> signOffLevel) {
		this.signOffLevel = signOffLevel;
	}
	
	
}
