package za.ac.unisa.lms.tools.assessmentcriteriaauth.forms;

import java.util.List;

public class AssessmentCriteriaStatusRecord {
	
	private String studyUnit;
	private String status;
	private String lastActionDate;
	private String primaryLecturer;
	
	public String getStudyUnit() {
		return studyUnit;
	}
	public void setStudyUnit(String studyUnit) {
		this.studyUnit = studyUnit;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getLastActionDate() {
		return lastActionDate;
	}
	public void setLastActionDate(String lastActionDate) {
		this.lastActionDate = lastActionDate;
	}
	public String getPrimaryLecturer() {
		return primaryLecturer;
	}
	public void setPrimaryLecturer(String primaryLecturer) {
		this.primaryLecturer = primaryLecturer;
	}

}
