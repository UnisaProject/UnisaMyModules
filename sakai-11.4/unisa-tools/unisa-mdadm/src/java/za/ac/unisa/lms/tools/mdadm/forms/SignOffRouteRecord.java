package za.ac.unisa.lms.tools.mdadm.forms;

import java.util.List;

import za.ac.unisa.lms.domain.general.Person;

public class SignOffRouteRecord {
	private Person person;
	private SignOffLevel signOffLevel;
	
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public SignOffLevel getSignOffLevel() {
		return signOffLevel;
	}
	public void setSignOffLevel(SignOffLevel signOffLevel) {
		this.signOffLevel = signOffLevel;
	}

}
