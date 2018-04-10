package za.ac.unisa.lms.tools.mdrpm.forms;

import za.ac.unisa.lms.dao.Gencod;

public class RpmListRecord {

	private int studentNr;
	private String studentName;
	private String qualCode;
	private String specCode;
	private String studyUnit;
	private String proposedResult;
	private String supervisor;
	private String title;	
	private WorklistRecord workListItem;	
	
	public WorklistRecord getWorkListItem() {
		return workListItem;
	}

	public void setWorkListItem(WorklistRecord workListItem) {
		this.workListItem = workListItem;
	}

	public String getProposedResult() {
		return proposedResult;
	}

	public void setProposedResult(String proposedResult) {
		this.proposedResult = proposedResult;
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

	public String getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}

	public int getStudentNr() {
		return studentNr;
	}

	public void setStudentNr(int studentNr) {
		this.studentNr = studentNr;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getStudyUnit() {
		return studyUnit;
	}

	public void setStudyUnit(String studyUnit) {
		this.studyUnit = studyUnit;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
