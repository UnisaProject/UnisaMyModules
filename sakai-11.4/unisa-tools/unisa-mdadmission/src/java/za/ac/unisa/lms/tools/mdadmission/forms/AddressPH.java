package za.ac.unisa.lms.tools.mdadmission.forms;

public class AddressPH {
	
	public String getHomeNumber() {
		return homeNumber;
	}
	public void setHomeNumber(String homeNumber) {
		this.homeNumber = homeNumber;
	}
	public String getCellNumber() {
		return cellNumber;
	}
	public void setCellNumber(String cellNumber) {
		this.cellNumber = cellNumber;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	private String homeNumber;
	private String cellNumber;
	private String email;

}
