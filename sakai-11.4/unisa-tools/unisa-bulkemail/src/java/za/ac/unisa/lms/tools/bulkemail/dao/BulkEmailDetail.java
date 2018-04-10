package za.ac.unisa.lms.tools.bulkemail.dao;

public class BulkEmailDetail {
	
	private String emailAddress;
	private String studentNumber;

	//the search lookup tables
	private String homeLanguage;
	private String country;
	private String province;
	private String race;
	private String region;
	private String resregion;
	
	
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getHomeLanguage() {
		return homeLanguage;
	}
	public void setHomeLanguage(String homeLanguage) {
		this.homeLanguage = homeLanguage;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getRace() {
		return race;
	}
	public void setRace(String race) {
		this.race = race;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getStudentNumber() {
		return studentNumber;
	}
	public void setStudentNumber(String studentNumber) {
		this.studentNumber = studentNumber;
	}
	public String getResregion() {
		return resregion;
	}
	public void setResregion(String resregion) {
		this.resregion = resregion;
	}
}
