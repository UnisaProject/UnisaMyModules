package za.ac.unisa.lms.tools.honsadmission.forms;

public class Referral {
	ApplicationPeriod applicationPeriod;
	private String qualCode;
	private String specCode;
	private Student student;
	private String referDate;
	private String referUser;
	private String type;
	private String level;
	private String noteToAcademic;
	private Short choice;
	private Short seqNr;
		
	public String getReferUser() {
		return referUser;
	}
	public void setReferUser(String referUser) {
		this.referUser = referUser;
	}
	public ApplicationPeriod getApplicationPeriod() {
		return applicationPeriod;
	}
	public void setApplicationPeriod(ApplicationPeriod applicationPeriod) {
		this.applicationPeriod = applicationPeriod;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public String getQualCode() {
		return qualCode;
	}
	public void setQualCode(String qualCode) {
		this.qualCode = qualCode;
	}
	public String getSpecCode() {
		return specCode;
	}
	public void setSpecCode(String specCode) {
		this.specCode = specCode;
	}	
	public String getReferDate() {
		return referDate;
	}
	public void setReferDate(String referDate) {
		this.referDate = referDate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}	
	public Short getChoice() {
		return choice;
	}
	public void setChoice(Short choice) {
		this.choice = choice;
	}
	public Short getSeqNr() {
		return seqNr;
	}
	public void setSeqNr(Short seqNr) {
		this.seqNr = seqNr;
	}
	public String getNoteToAcademic() {
		return noteToAcademic;
	}
	public void setNoteToAcademic(String noteToAcademic) {
		this.noteToAcademic = noteToAcademic;
	}
	
}
