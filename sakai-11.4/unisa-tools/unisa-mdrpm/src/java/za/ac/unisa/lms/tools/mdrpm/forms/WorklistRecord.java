package za.ac.unisa.lms.tools.mdrpm.forms;

public class WorklistRecord {
	
	private int seqNr;
	private String requestDate;
	private String requestor;
	private String status;
	private String trackingStatus;
	private String referToPersno;
	private String referToLevel;
	
	public int getSeqNr() {
		return seqNr;
	}
	public void setSeqNr(int seqNr) {
		this.seqNr = seqNr;
	}
	public String getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(String requestDate) {
		this.requestDate = requestDate;
	}
	public String getRequestor() {
		return requestor;
	}
	public void setRequestor(String requestor) {
		this.requestor = requestor;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTrackingStatus() {
		return trackingStatus;
	}
	public void setTrackingStatus(String trackingStatus) {
		this.trackingStatus = trackingStatus;
	}
	public String getReferToPersno() {
		return referToPersno;
	}
	public void setReferToPersno(String referToPersno) {
		this.referToPersno = referToPersno;
	}
	public String getReferToLevel() {
		return referToLevel;
	}
	public void setReferToLevel(String referToLevel) {
		this.referToLevel = referToLevel;
	}
	
	

}
