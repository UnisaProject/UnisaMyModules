package za.ac.unisa.lms.tools.exampaperonline.forms;

import java.util.List;

public class PrintInstructions {

	private Integer quantity;
	private String dateToExams;
	private String dateToPP;
	private List annexures;
	private String dateCompleted;
	
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public String getDateToExams() {
		return dateToExams;
	}
	public void setDateToExams(String dateToExams) {
		this.dateToExams = dateToExams;
	}
	public String getDateToPP() {
		return dateToPP;
	}
	public void setDateToPP(String dateToPP) {
		this.dateToPP = dateToPP;
	}
	public List getAnnexures() {
		return annexures;
	}
	public void setAnnexures(List annexures) {
		this.annexures = annexures;
	}
	public String getDateCompleted() {
		return dateCompleted;
	}
	public void setDateCompleted(String dateCompleted) {
		this.dateCompleted = dateCompleted;
	}
	
	
}
