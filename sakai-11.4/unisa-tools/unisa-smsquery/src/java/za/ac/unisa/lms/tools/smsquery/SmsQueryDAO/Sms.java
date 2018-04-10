package za.ac.unisa.lms.tools.smsquery.SmsQueryDAO;

public class Sms{

	private String batchNr;
	private String sentOn;
	private String messageStatus;
	private String message;
	private String refNumber;
	private String cellNumber;
	private String sequenceNr;
	
	public String getSequenceNr() {
		return sequenceNr;
	}
	public void setSequenceNr(String sequenceNr) {
		this.sequenceNr = sequenceNr;
	}
	public String getBatchNr() {
		return batchNr;
	}
	public void setBatchNr(String batchNr) {
		this.batchNr = batchNr;
	}
	public String getSentOn(){
		return sentOn;
	}
	public void setSentOn(String sentOn) {
		this.sentOn = sentOn;
	}
    public String getMessage() {
		return message;
	}
	public void setMessageStatus(String messageStatus) {
		this.messageStatus = messageStatus;
	}
	public String getMessageStatus() {
		return messageStatus;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getRefNumber() {
		return refNumber;
	}
	public void setRefNumber(String refNumber) {
		this.refNumber = refNumber;
	}
	public String getCellNumber() {
		return cellNumber;
	}
	public void setCellNumber(String cellNumber) {
		this.cellNumber = cellNumber;
	}

}
