package za.ac.unisa.lms.tools.exampaperonline.forms;

public class CoverDocket {
	private Short examYear;
	private Short examPeriod;
	private String StudyUnit;
	private Short paperNo;
	private String paperColour;
	private Short nrDocsSubmitted;
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
	public String getPaperColour() {
		return paperColour;
	}
	public void setPaperColour(String paperColour) {
		this.paperColour = paperColour;
	}
	public Short getNrDocsSubmitted() {
		return nrDocsSubmitted;
	}
	public void setNrDocsSubmitted(Short nrDocsSubmitted) {
		this.nrDocsSubmitted = nrDocsSubmitted;
	}	
	
}
