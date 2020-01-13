package za.ac.unisa.lms.tools.signoff.forms;

public class StaffDeatils {
	private String management_level;
	private String name;
	private String personnelNumber;
	private boolean remove;
	private int sno;
	
	
	public int getSno() {
		return sno;
	}
	public void setSno(int sno) {
		this.sno = sno;
	}
	public boolean isRemove() {
		return remove;
	}
	public void setRemove(boolean remove) {
		this.remove = remove;
	}
	public String getPersonnelNumber() {
		return personnelNumber;
	}
	public void setPersonnelNumber(String personnelNumber) {
		this.personnelNumber = personnelNumber;
	}
	public String getManagement_level() {
		return management_level;
	}
	public void setManagement_level(String managementLevel) {
		management_level = managementLevel;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
