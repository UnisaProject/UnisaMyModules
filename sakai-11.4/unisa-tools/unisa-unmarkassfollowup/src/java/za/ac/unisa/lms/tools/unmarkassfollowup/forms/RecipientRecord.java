package za.ac.unisa.lms.tools.unmarkassfollowup.forms;

import za.ac.unisa.lms.domain.general.Person;

public class RecipientRecord {
	private String role;
	private String roleAbbrv;
	private Person person;
	private String roleOn;
	
	public String getRoleAbbrv() {
		return roleAbbrv;
	}
	public void setRoleAbbrv(String roleAbbrv) {
		this.roleAbbrv = roleAbbrv;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}	
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public String getRoleOn() {
		return roleOn;
	}
	public void setRoleOn(String roleOn) {
		this.roleOn = roleOn;
	}
	
	
}
