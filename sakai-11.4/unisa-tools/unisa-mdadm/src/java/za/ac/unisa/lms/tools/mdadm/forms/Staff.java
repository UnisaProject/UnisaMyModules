package za.ac.unisa.lms.tools.mdadm.forms;

import java.util.List;
import za.ac.unisa.lms.domain.general.Person;

public class Staff {
	
	private Person person;
	private List <SignOffLevel> signOffLevel;
	
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public List<SignOffLevel> getSignOffLevelList() {
		return signOffLevel;
	}
	public void setSignOffLevelList(List<SignOffLevel> signOffLevel) {
		this.signOffLevel = signOffLevel;
	}
	
	
}
