package za.ac.unisa.lms.tools.mdadm.forms;

import za.ac.unisa.lms.domain.general.*;
import za.ac.unisa.lms.dao.Gencod;

public class TrackRecord {
	
	private int studentNumber;
	private int seqNr;
	private String date;	
	private Gencod status;
	private Person currentPerson;
	private String currentLevel;
	private Person assignToPerson;
	private String assignToLevel;
	private Gencod recommendation;
	private String recommendationComment;
	private String signoffComment;	
	
	public int getStudentNumber() {
		return studentNumber;
	}
	public void setStudentNumber(int studentNumber) {
		this.studentNumber = studentNumber;
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
	public Person getAssignToPerson() {
		return assignToPerson;
	}
	public void setAssignToPerson(Person assignToPerson) {
		this.assignToPerson = assignToPerson;
	}
	public String getAssignToLevel() {
		return assignToLevel;
	}
	public void setAssignToLevel(String assignToLevel) {
		this.assignToLevel = assignToLevel;
	}	
	public Gencod getRecommendation() {
		return recommendation;
	}
	public void setRecommendation(Gencod recommendation) {
		this.recommendation = recommendation;
	}
	public String getRecommendationComment() {
		return recommendationComment;
	}
	public void setRecommendationComment(String recommendationComment) {
		this.recommendationComment = recommendationComment;
	}
	public String getSignoffComment() {
		return signoffComment;
	}
	public void setSignoffComment(String signoffComment) {
		this.signoffComment = signoffComment;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}	
	
	public int getSeqNr() {
		return seqNr;
	}
	public void setSeqNr(int seqNr) {
		this.seqNr = seqNr;
	}
	public Gencod getStatus() {
		return status;
	}
	public void setStatus(Gencod status) {
		this.status = status;
	}

	

}
