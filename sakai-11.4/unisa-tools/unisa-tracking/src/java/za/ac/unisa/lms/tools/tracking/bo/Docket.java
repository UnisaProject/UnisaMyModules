package za.ac.unisa.lms.tools.tracking.bo;

import java.util.List;

import za.ac.unisa.lms.tools.tracking.bo.Assignment;

public class Docket {

	 /**
	 * 
	 */
	private String docketID;
	private String docketNumber;

    /**
     * List of Assignments collection
     */
	private List<Assignment> assignments;
	
	//constructors
	public Docket(){}
	public Docket(String docketID, String docketNumber){
		this.docketID = docketID;
		this.docketNumber = docketNumber;
	}
	
	public String getDocketID() {
		return docketID;
	}
	public void setDocketID(String docketID) {
		this.docketID = docketID;
	}
	
	public String getDocketNumber() {
		return docketNumber;
	}
	 
	public void setDocketNumber(String docketNumber) {
		this.docketNumber = docketNumber;
	}
	
	public List<Assignment> getAssignments() {
		return assignments;
	}

	public void setAssignments(List<Assignment> assignments) {
		this.assignments = assignments;
	}
}
