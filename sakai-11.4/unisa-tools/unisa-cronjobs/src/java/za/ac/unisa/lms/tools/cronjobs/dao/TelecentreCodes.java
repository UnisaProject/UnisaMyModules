package za.ac.unisa.lms.tools.cronjobs.dao;

public class TelecentreCodes {

	private String teleId;
	private String centreProvince;
	private String centreName;
	private String emailId;
	private String monthlyHrs;
	private String active;
	private String teleCode;
	
	public String getTeleId() {
		return teleId;
	}
	public void setTeleId(String teleId) {
		this.teleId = teleId;
	}
	public String getCentreProvince() {
		return centreProvince;
	}
	public void setCentreProvince(String centreProvince) {
		this.centreProvince = centreProvince;
	}
	public String getCentreName() {
		return centreName;
	}
	public void setCentreName(String centreName) {
		this.centreName = centreName;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getMonthlyHrs() {
		return monthlyHrs;
	}
	public void setMonthlyHrs(String monthlyHrs) {
		this.monthlyHrs = monthlyHrs;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getTeleCode() {
		return teleCode;
	}
	public void setTeleCode(String teleCode) {
		this.teleCode = teleCode;
	}

}
