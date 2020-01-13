package za.ac.unisa.lms.tools.mdrpm.forms;

import za.ac.unisa.lms.domain.general.*;
import za.ac.unisa.lms.dao.Gencod;

public class TrackRecord {
	
	private int seqNr;
	private String date;
	private Gencod proposedResult;
	private String statusCode;
	private String comment;
	private Person currentPerson;
	private String currentLevel;
	private Person assignedToPerson;
	private String assignedToLevel;
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}	
	public Gencod getProposedResult() {
		return proposedResult;
	}
	public void setProposedResult(Gencod proposedResult) {
		this.proposedResult = proposedResult;
	}
	public int getSeqNr() {
		return seqNr;
	}
	public void setSeqNr(int seqNr) {
		this.seqNr = seqNr;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Person getCurrentPerson() {
		return currentPerson;
	}
	public void setCurrentPerson(Person currentPerson) {
		this.currentPerson = currentPerson;
	}
	public String getCurrentLevel() {
		return currentLevel;
	}
	public void setCurrentLevel(String currentLevel) {
		this.currentLevel = currentLevel;
	}
	public Person getAssignedToPerson() {
		return assignedToPerson;
	}
	public void setAssignedToPerson(Person assignedToPerson) {
		this.assignedToPerson = assignedToPerson;
	}
	public String getAssignedToLevel() {
		return assignedToLevel;
	}
	public void setAssignedToLevel(String assignedToLevel) {
		this.assignedToLevel = assignedToLevel;
	}
	
	

}
