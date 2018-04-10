package za.ac.unisa.lms.tools.honsadmission.forms;

public class StuAdmRef {
	private String referType;
	private Short seqNr;	
	private Qualification qualification;
	private String referDate;
	private String referLevel;
	private String referUser;
	private String referEmail;
	private String noteToAcademic;
	private String recommendation;
	private String recommendationComment;
	private String signOffPersno;
	private String signOffName;
	private String signOffDate;
	private String SignOffComment;
	
	public String getReferUser() {
		return referUser;
	}
	public void setReferUser(String referUser) {
		this.referUser = referUser;
	}
	public String getReferType() {
		return referType;
	}
	public void setReferType(String referType) {
		this.referType = referType;
	}
	public Short getSeqNr() {
		return seqNr;
	}
	public void setSeqNr(Short seqNr) {
		this.seqNr = seqNr;	
	}
	public Qualification getQualification() {
		return qualification;
	}
	public void setQualification(Qualification qualification) {
		this.qualification = qualification;
	}
	public String getReferLevel() {
		return referLevel;
	}
	public void setReferLevel(String referLevel) {
		this.referLevel = referLevel;
	}	
	public String getReferDate() {
		return referDate;
	}
	public void setReferDate(String referDate) {
		this.referDate = referDate;
	}
	public String getReferEmail() {
		return referEmail;
	}
	public void setReferEmail(String referEmail) {
		this.referEmail = referEmail;
	}
	public String getNoteToAcademic() {
		return noteToAcademic;
	}
	public void setNoteToAcademic(String noteToAcademic) {
		this.noteToAcademic = noteToAcademic;
	}
	public String getRecommendation() {
		return recommendation;
	}
	public void setRecommendation(String recommendation) {
		this.recommendation = recommendation;
	}
	public String getRecommendationComment() {
		return recommendationComment;
	}
	public void setRecommendationComment(String recommendationComment) {
		this.recommendationComment = recommendationComment;
	}
	public String getSignOffPersno() {
		return signOffPersno;
	}
	public void setSignOffPersno(String signOffPersno) {
		this.signOffPersno = signOffPersno;
	}
	public String getSignOffName() {
		return signOffName;
	}
	public void setSignOffName(String signOffName) {
		this.signOffName = signOffName;
	}	
	public String getSignOffDate() {
		return signOffDate;
	}
	public void setSignOffDate(String signOffDate) {
		this.signOffDate = signOffDate;
	}
	public String getSignOffComment() {
		return SignOffComment;
	}
	public void setSignOffComment(String signOffComment) {
		SignOffComment = signOffComment;
	}
	
	
}
