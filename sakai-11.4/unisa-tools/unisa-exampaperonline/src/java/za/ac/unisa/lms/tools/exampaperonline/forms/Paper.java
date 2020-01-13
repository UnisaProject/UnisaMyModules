package za.ac.unisa.lms.tools.exampaperonline.forms;

import java.util.List;

public class Paper {
	private Short examYear;
	private Short examPeriod;
	private String StudyUnit;
	private Short paperNo;
	private String docType;	
	private String fromUser;
	private String fromRole;
	private String dateSent;
	private String senderResponse;
	private String senderResponseText;
	private List documentList;
	
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
		return StudyUnit;
	}
	public void setStudyUnit(String studyUnit) {
		StudyUnit = studyUnit;
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
	public String getFromUser() {
		return fromUser;
	}
	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}
	public String getFromRole() {
		return fromRole;
	}
	public void setFromRole(String fromRole) {
		this.fromRole = fromRole;
	}
	public String getDateSent() {
		return dateSent;
	}
	public void setDateSent(String dateSent) {
		this.dateSent = dateSent;
	}
	public String getSenderResponse() {
		return senderResponse;
	}
	public void setSenderResponse(String senderResponse) {
		this.senderResponse = senderResponse;
	}
	public String getSenderResponseText() {
		return senderResponseText;
	}
	public void setSenderResponseText(String senderResponseText) {
		this.senderResponseText = senderResponseText;
	}
	public List getDocumentList() {
		return documentList;
	}
	public void setDocumentList(List documentList) {
		this.documentList = documentList;
	}	
}
