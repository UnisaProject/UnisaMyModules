package za.ac.unisa.lms.tools.exampaperonline.forms;

import java.util.List;

import za.ac.unisa.lms.domain.general.Person;

public class ExamPaperOnlineLog {
	
	private Short examYear;
	private Short examPeriod;
	private String studyUnit;
	private Short paperNo;
	private String docType;
	private Integer seqNr;
	private String updatedOn;
	private String currentUser;
	private String currentRole;
	private String actionedUser;
	private String actionedRole;
	private String action;
	private String approvalStatus;
	private String message;
	private String shortMessage;
	private String resetFlag;
	private LinkedPaper linkedPaper;		
	
	public LinkedPaper getLinkedPaper() {
		return linkedPaper;
	}
	public void setLinkedPaper(LinkedPaper linkedPaper) {
		this.linkedPaper = linkedPaper;
	}
	public String getResetFlag() {
		return resetFlag;
	}
	public void setResetFlag(String resetFlag) {
		this.resetFlag = resetFlag;
	}
	public String getShortMessage() {
		if (this.message!=null && this.message.length() > 30){
			shortMessage = this.message.substring(0,30) + "   ...more";
		}else{
			shortMessage = this.message;
		}		
		return shortMessage;
	}
	public void setShortMessage(String shortMessage) {
		this.shortMessage = shortMessage;
	}
	public Short getExamYear() {
		return examYear;
	}
	public void setExamYear(Short examYear) {
		this.examYear = examYear;
	}
	public Short getExamPeriod() {
		return examPeriod;
	}
	public void setExamPeriod(Short examPeriod) {
		this.examPeriod = examPeriod;
	}	
	public String getStudyUnit() {
		return studyUnit;
	}
	public void setStudyUnit(String studyUnit) {
		this.studyUnit = studyUnit;
	}
	public Short getPaperNo() {
		return paperNo;
	}
	public void setPaperNo(Short paperNo) {
		this.paperNo = paperNo;
	}
	public String getDocType() {
		return docType;
	}
	public void setDocType(String docType) {
		this.docType = docType;
	}
	public Integer getSeqNr() {
		return seqNr;
	}
	public void setSeqNr(Integer seqNr) {
		this.seqNr = seqNr;
	}
	public String getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(String updatedOn) {
		this.updatedOn = updatedOn;
	}
	public String getCurrentUser() {
		return currentUser;
	}
	public void setCurrentUser(String currentUser) {
		this.currentUser = currentUser;
	}
	public String getCurrentRole() {
		return currentRole;
	}
	public void setCurrentRole(String currentRole) {
		this.currentRole = currentRole;
	}
	public String getActionedUser() {
		return actionedUser;
	}
	public void setActionedUser(String actionedUser) {
		this.actionedUser = actionedUser;
	}
	public String getActionedRole() {
		return actionedRole;
	}
	public void setActionedRole(String actionedRole) {
		this.actionedRole = actionedRole;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getApprovalStatus() {
		return approvalStatus;
	}
	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}		

}
