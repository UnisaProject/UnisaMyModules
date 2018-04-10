package za.ac.unisa.lms.domain.processDrivenSms;

public class SmsRecipient {
	private int studentNr;
	private String message;
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
	
}
