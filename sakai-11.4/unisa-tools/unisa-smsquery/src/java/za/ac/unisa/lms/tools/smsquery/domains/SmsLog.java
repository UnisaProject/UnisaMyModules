package za.ac.unisa.lms.tools.smsquery.domains;

public class SmsLog {
	private String batchNr;
	private String sequenceNr;
	private String referenceNr;
	private String cellNr;
	private String sendOn;
	private String message;
	private String messageStatus;
	
	public String getBatchNr() {
		return batchNr;
	}
	public void setBatchNr(String batchNr) {
		this.batchNr = batchNr;
	}
	public String getSequenceNr() {
		return sequenceNr;
	}
	public void setSequenceNr(String sequenceNr) {
		this.sequenceNr = sequenceNr;
	}
	public String getReferenceNr() {
		return referenceNr;
	}
	public void setReferenceNr(String referenceNr) {
		this.referenceNr = referenceNr;
	}
	public String getCellNr() {
		return cellNr;
	}
	public void setCellNr(String cellNr) {
		this.cellNr = cellNr;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getMessageStatus() {
		return messageStatus;
	}
	public void setMessageStatus(String messageStatus) {
		this.messageStatus = messageStatus;
	}
	public String getSendOn() {
		return sendOn;
	}
	public void setSendOn(String sendOn) {
		this.sendOn = sendOn;
	}
}
