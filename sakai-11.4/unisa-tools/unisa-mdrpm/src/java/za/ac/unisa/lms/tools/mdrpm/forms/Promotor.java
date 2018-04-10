package za.ac.unisa.lms.tools.mdrpm.forms;

import za.ac.unisa.lms.domain.general.Person;

public class Promotor {
	private Person person;
	private boolean isSupervisor;
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public boolean getIsSupervisor() {
		return isSupervisor;
	}
	public void setIsSupervisor(boolean isSupervisor) {
		this.isSupervisor = isSupervisor;
	}	

	
}
