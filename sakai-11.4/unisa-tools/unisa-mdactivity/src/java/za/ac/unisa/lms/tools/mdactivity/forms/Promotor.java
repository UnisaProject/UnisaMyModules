package za.ac.unisa.lms.tools.mdactivity.forms;

public class Promotor {

	private int staffNr = 0;
	private String name = "";
	private String department = "";
	private String supervisor = "";
	private String departmentDesc = "";
	
	public String getDepartmentDesc() {
		return departmentDesc;
	}
	public void setDepartmentDesc(String departmentDesc) {
		this.departmentDesc = departmentDesc;
	}
	public int getStaffNr() {
		return staffNr;
	}
	public void setStaffNr(int staffNr) {
		this.staffNr = staffNr;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getSupervisor() {
		return supervisor;
	}
	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}
}
