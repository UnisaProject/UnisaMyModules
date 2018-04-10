package za.ac.unisa.lms.domain.processDrivenSms;

public class SmsRecipientResult {
	private int studentNr;
	private String message;
	private String cellNr;
	private String errMsg;
	
	public int getStudentNr() {
		return studentNr;
	}
	public void setStudentNr(int studentNr) {
		this.studentNr = studentNr;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getCellNr() {
		return cellNr;
	}
	public void setCellNr(String cellNr) {
		this.cellNr = cellNr;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	
	
}
