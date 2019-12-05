package za.ac.unisa.lms.domain.processDrivenSms;

import java.util.List;

public class SmsRequest {
	
	private int persNo;	
	private String rcCode;
	private String message;
	private String reasonCodeGC27;
	private String programName;
	private String criteria;
	private List<SmsRecipient> listRecipient;
	
	public String getRcCode() {
		return rcCode;
	}
	public void setRcCode(String rcCode) {
		this.rcCode = rcCode;
	}
	public String getCriteria() {
		return criteria;
	}
	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}	
	public int getPersNo() {
		return persNo;
	}
	public void setPersNo(int persNo) {
		this.persNo = persNo;
	}	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}	
	public String getReasonCodeGC27() {
		return reasonCodeGC27;
	}
	public void setReasonCodeGC27(String reasonCodeGC27) {
		this.reasonCodeGC27 = reasonCodeGC27;
	}
	public String getProgramName() {
		return programName;
	}
	public void setProgramName(String programName) {
		this.programName = programName;
	}
	public List<SmsRecipient> getListRecipient() {
		return listRecipient;
	}
	public void setListRecipient(List<SmsRecipient> listRecipient) {
		this.listRecipient = listRecipient;
	}

}
