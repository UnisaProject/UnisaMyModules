package za.ac.unisa.lms.tools.exampaperonline.forms;

import za.ac.unisa.lms.domain.general.Person;

public class Recipient {
	
	private Person person;
	private String role;
	
	
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
}
