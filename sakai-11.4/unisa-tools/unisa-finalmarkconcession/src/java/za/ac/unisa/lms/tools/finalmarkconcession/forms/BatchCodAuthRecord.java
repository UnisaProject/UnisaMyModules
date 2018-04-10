package za.ac.unisa.lms.tools.finalmarkconcession.forms;

public class BatchCodAuthRecord {
	private Integer studentNumber;
	private String studentName;
	private ResultRecord result;
	private AlternativeExamOpportunityRecord altExamOpt;
	
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
	
	
	
}
