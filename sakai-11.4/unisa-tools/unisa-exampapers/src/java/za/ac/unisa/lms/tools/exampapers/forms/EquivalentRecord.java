package za.ac.unisa.lms.tools.exampapers.forms;

public class EquivalentRecord {
	private String studyUnit;
	private String uniqueNo;	
	private String examDate;
	private String examTime;
	private String durationHours;
	private String durationMin;
	private String durationDays;
	
	public String getUniqueNo() {
		return uniqueNo;
	}
	public void setUniqueNo(String uniqueNo) {
		this.uniqueNo = uniqueNo;
	}
	public String getStudyUnit() {
		return studyUnit;
	}
	public void setStudyUnit(String studyUnit) {
		this.studyUnit = studyUnit;
	}
	public String getExamDate() {
		return examDate;
	}
	public void setExamDate(String examDate) {
		this.examDate = examDate;
	}
	public String getExamTime() {
		return examTime;
	}
	public void setExamTime(String examTime) {
		this.examTime = examTime;
	}
	public String getDurationHours() {
		return durationHours;
	}
	public void setDurationHours(String durationHours) {
		this.durationHours = durationHours;
	}
	public String getDurationMin() {
		return durationMin;
	}
	public void setDurationMin(String durationMin) {
		this.durationMin = durationMin;
	}
	public String getDurationDays() {
		return durationDays;
	}
	public void setDurationDays(String durationDays) {
		this.durationDays = durationDays;
	}

}
