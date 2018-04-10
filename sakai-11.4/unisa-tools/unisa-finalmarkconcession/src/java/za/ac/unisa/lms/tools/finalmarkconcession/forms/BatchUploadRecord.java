package za.ac.unisa.lms.tools.finalmarkconcession.forms;

public class BatchUploadRecord {	
		
	private Integer studentNumber;
	private String studentName;
	private ResultRecord result;
	private AlternativeExamOpportunityRecord altExamOpt;
	private int gradebookPercentage;
	private String status;	
	private boolean upload;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getStudentNumber() {
		return studentNumber;
	}
	public void setStudentNumber(Integer studentNumber) {
		this.studentNumber = studentNumber;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public ResultRecord getResult() {
		return result;
	}
	public void setResult(ResultRecord result) {
		this.result = result;
	}
	public AlternativeExamOpportunityRecord getAltExamOpt() {
		return altExamOpt;
	}
	public void setAltExamOpt(AlternativeExamOpportunityRecord altExamOpt) {
		this.altExamOpt = altExamOpt;
	}
	public int getGradebookPercentage() {
		return gradebookPercentage;
	}
	public void setGradebookPercentage(int gradebookPercentage) {
		this.gradebookPercentage = gradebookPercentage;
	}
	public boolean isUpload() {
		return upload;
	}
	public void setUpload(boolean upload) {
		this.upload = upload;
	}
	
}
