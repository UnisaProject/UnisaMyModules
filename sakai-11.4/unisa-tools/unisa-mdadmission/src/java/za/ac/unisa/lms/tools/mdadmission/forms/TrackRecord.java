package za.ac.unisa.lms.tools.mdadmission.forms;

import za.ac.unisa.lms.domain.general.*;
import za.ac.unisa.lms.dao.Gencod;

public class TrackRecord {
	
	private int seqNr;
	private String date;	
	private String statusCode;	
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}	
	
	public int getSeqNr() {
		return seqNr;
	}
	public void setSeqNr(int seqNr) {
		this.seqNr = seqNr;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}	
	

}
