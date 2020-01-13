package za.ac.unisa.lms.tools.tracking.forms;

public class DocketNumberDetails {

	 private int docketID;
	 private String docketNumber;
	 private String studentNumber ;
	 private String uniqueAssignmentNumber ;
	 public String getStudentNumber() {
		return studentNumber;
	}

	public void setStudentNumber(String studentNumber) {
		this.studentNumber = studentNumber;
	}

	public String getUniqueAssignmentNumber() {
		return uniqueAssignmentNumber;
	}

	public void setUniqueAssignmentNumber(String uniqueAssignmentNumber) {
		this.uniqueAssignmentNumber = uniqueAssignmentNumber;
	}

	private boolean remove =false;
	private boolean remove1 = false;

	public int getDocketID() {
		return docketID;
	}

	public void setDocketID(int docketID) {
		this.docketID = docketID;
	}
	
	public String getDocketNumber() {
		return docketNumber;
	}

	public void setDocketNumber(String docketNumber) {
		this.docketNumber = docketNumber;
	}

	public boolean isRemove() {
		return remove;
	}

	public void setRemove(boolean remove) {
		this.remove = remove;
	}

	public boolean isRemove1() {
		return remove1;
	}

	public void setRemove1(boolean remove1) {
		this.remove1 = remove1;
	}

	
}
