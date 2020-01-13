package za.ac.unisa.lms.tools.mdrpmresults.forms;

public class Staff {

	private String staffNr;
	private String emailAddress;
	private String telephone;
	private String name;
	private String signOffDate;
	private String signOffComment;
	private String signOffStatus;
	private String finalFlag;
	private String isSupervisor;
	public static final String STAFF_NAME = "staffName";

	public String getStaffNr() {
		return staffNr;
	}

	public void setStaffNr(String staffNr) {
		this.staffNr = staffNr;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getSignOffStatus() {
		return signOffStatus;
	}

	public void setSignOffStatus(String signOffStatus) {
		this.signOffStatus = signOffStatus;
	}

	public String getFinalFlag() {
		return finalFlag;
	}

	public void setFinalFlag(String finalFlag) {
		this.finalFlag = finalFlag;
	}

	public String getIsSupervisor() {
		return isSupervisor;
	}

	public void setIsSupervisor(String isSupervisor) {
		this.isSupervisor = isSupervisor;
	}

}
