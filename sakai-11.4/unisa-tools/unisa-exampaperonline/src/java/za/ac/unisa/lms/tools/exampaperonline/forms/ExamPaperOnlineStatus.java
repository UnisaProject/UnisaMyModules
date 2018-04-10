package za.ac.unisa.lms.tools.exampaperonline.forms;

public class ExamPaperOnlineStatus {
	private Short examYear;
	private Short examPeriod;
	private String StudyUnit;
	private Short paperNo;
	private String docType;	
	private String owner;
	private String updatedOn;
	private String atUser;
	private String atRole;
	private String fromUser;
	private String fromRole;
	private String lastAction;
	private String status;
	private Integer lastSeqNr;
	
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
	public String getLastAction() {
		return lastAction;
	}
	public void setLastAction(String lastAction) {
		this.lastAction = lastAction;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
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
	public String getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(String updatedOn) {
		this.updatedOn = updatedOn;
	}
	public String getAtUser() {
		return atUser;
	}
	public void setAtUser(String atUser) {
		this.atUser = atUser;
	}
	public String getAtRole() {
		return atRole;
	}
	public void setAtRole(String atRole) {
		this.atRole = atRole;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getLastSeqNr() {
		return lastSeqNr;
	}
	public void setLastSeqNr(Integer lastSeqNr) {
		this.lastSeqNr = lastSeqNr;
	}
	
	
}
