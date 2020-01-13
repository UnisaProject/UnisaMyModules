package za.ac.unisa.lms.tools.smsquery.SmsQueryDAO;

public class SmsBatch {

	private String batchNr;
	private String requestDate;
	private String messageCount,budgetAmount;
	private String message;
	private String fromDate,toDate;
	private String responsibilityCode,personnelNumber;
	
	
	public String getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(String requestDate) {
		this.requestDate = requestDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getPersonnelNumber() {
		return personnelNumber;
	}
	public void setPersonnelNumber(String personnelNumber) {
		this.personnelNumber = personnelNumber;
	}
	public String getBatchNr() {
		return batchNr;
	}
	public void setBatchNr(String batchNr) {
		this.batchNr = batchNr;
	}
	public String getResponsibilityCode() {
		return responsibilityCode;
	}
	public void setResponsibilityCode(String responsibilityCode) {
		this.responsibilityCode = responsibilityCode;
	}
	public void setMessageCount(String messageCount) {
		this.messageCount = messageCount;
	}
	public String getMessageCount() {
		return messageCount;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getBudgetAmount() {
		return budgetAmount;
	}
	public void setBudgetAmount(String budgetAmount) {
		this.budgetAmount = budgetAmount;
	}
	
}
