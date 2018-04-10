package za.ac.unisa.lms.tools.tracking.bo;

import java.util.Collection;
import java.util.List;

public class Consignment {

	private String consignmentNumber;
	private String date ;
	private String showHide;
	/**
     * List of Dockets collection
     */
	private List<Docket> dockets;
    
    /**
     * List of Assignments collection
     */
	private List<Assignment> assignments;

	//constructors
	public Consignment() {}

	public String getConsignmentNumber() {
		return consignmentNumber;
	}

	public void setConsignmentNumber(String consignmentNumber) {
		this.consignmentNumber = consignmentNumber;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	public String getShowHide() {
		return showHide;
	}

	public void setShowHide(String showHide) {
		//System.out.println("Consignment setShowHide="+showHide);
		this.showHide = showHide;
	}
	public List<Docket> getDockets() {
		return dockets;
	}

	public void setDockets(List<Docket> dockets) {
		this.dockets = dockets;
	}

	public List<Assignment> getAssignments() {
		return assignments;
	}

	public void setAssignments(List<Assignment> assignments) {
		this.assignments = assignments;
	}
}
