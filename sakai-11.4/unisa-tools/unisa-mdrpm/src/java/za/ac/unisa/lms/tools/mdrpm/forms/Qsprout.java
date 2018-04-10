package za.ac.unisa.lms.tools.mdrpm.forms;

import za.ac.unisa.lms.domain.general.Person;

public class Qsprout {
	private Person person;
	private int seqNr;
	private String level;
	
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public int getSeqNr() {
		return seqNr;
	}
	public void setSeqNr(int seqNr) {
		this.seqNr = seqNr;
	}
	
	
}
