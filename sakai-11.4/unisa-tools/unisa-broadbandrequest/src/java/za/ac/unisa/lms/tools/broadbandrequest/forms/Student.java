package za.ac.unisa.lms.tools.broadbandrequest.forms;

public class Student {
	
	private String name;
	private String printName;
	private Integer number;
	private String birthDate; 
	private String countryCode;
	private Contact contactInfo;
	
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	public String getPrintName() {
		return printName;
	}
	public void setPrintName(String printName) {
		this.printName = printName;
	}	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}	
	public Contact getContactInfo() {
		return contactInfo;
	}
	public void setContactInfo(Contact contactInfo) {
		this.contactInfo = contactInfo;
	}
}
