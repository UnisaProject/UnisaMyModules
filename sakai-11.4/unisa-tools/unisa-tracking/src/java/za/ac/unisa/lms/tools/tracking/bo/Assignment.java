package za.ac.unisa.lms.tools.tracking.bo;

public class Assignment {

	private String uniqueAssignmentID ;
	private String studentNumber ;
	private String uniqueAssignmentNumber ;

	//constructors
	public Assignment(){}
	public Assignment(String studentNumber, String uniqueAssignmentNumber){
		this.studentNumber = studentNumber;
		this.uniqueAssignmentNumber = uniqueAssignmentNumber;
	}

	 public String getUniqueAssignmentID() {
		return uniqueAssignmentID;
	}

	public void setUniqueAssignmentID(String uniqueAssignmentID) {
		this.uniqueAssignmentID = uniqueAssignmentID;
	}
	
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
	
}
