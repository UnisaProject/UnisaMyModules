package za.ac.unisa.lms.tools.smsenquiry.domains;

public class SmsRequest {
	private String batchNr;
	private String requestDate;
	private String requestTime;
	private String messageCount;
	private String budgetAmount;
	private String message;
	public String getBatchNr() {
		return batchNr;
	}
	public void setBatchNr(String batchNr) {
		this.batchNr = batchNr;
	}
	public String getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(String requestDate) {
		this.requestDate = requestDate;
	}
	public String getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(String requestTime) {
		this.requestTime = requestTime;
	}
	public String getMessageCount() {
		return messageCount;
	}
	public void setMessageCount(String messageCount) {
		this.messageCount = messageCount;
	}
	public String getBudgetAmount() {
		return budgetAmount;
	}
	public void setBudgetAmount(String budgetAmount) {
		this.budgetAmount = budgetAmount;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

}
