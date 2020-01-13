package za.ac.unisa.lms.tools.mdrpmresults.forms;

import java.util.ArrayList;

import org.apache.struts.util.LabelValueBean;
import org.apache.struts.validator.ValidatorForm;

public class MdRPMResultsForm extends ValidatorForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8455793526096870151L;
	private Student student =  new Student();
	private String userCode;
	private Staff loggedInUser = new Staff();
	private String [] selectedResults={};
	private ArrayList<LabelValueBean> results = new ArrayList<LabelValueBean>(); 
	private String title;
	private ArrayList<Staff> supervisors = new ArrayList<Staff>();
	private String [] selectedReviewOrFinalValue={};
	private ArrayList<LabelValueBean> reviewOrFinalValues = new ArrayList<LabelValueBean>(); 
	private ArrayList<Staff> signOffList = new ArrayList<Staff>();
	private ArrayList<Staff> routingList = new ArrayList<Staff>();
	private String comment;
	private boolean isFirstSignOff = false;
	private boolean finalSignOff = false;
	private Staff prevSignOffRecord = new Staff();
	private boolean canSignOff = false;

	private String reviewoption;
	private String finaloption;
	
	public String getReviewoption() {
		return reviewoption;
	}

	public void setReviewoption(String reviewoption) {
		this.reviewoption = reviewoption;
	}

	public String getFinaloption() {
		return finaloption;
	}

	public void setFinaloption(String finaloption) {
		this.finaloption = finaloption;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public Staff getLoggedInUser() {
		return loggedInUser;
	}

	public void setLoggedInUser(Staff loggedInUser) {
		this.loggedInUser = loggedInUser;
	}

	public String[] getSelectedResults() {
		return selectedResults;
	}

	public void setSelectedResults(String[] selectedResults) {
		this.selectedResults = selectedResults;
	}

	public ArrayList<LabelValueBean> getResults() {
		return results;
	}

	public void setResults(ArrayList<LabelValueBean> results) {
		this.results = results;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ArrayList<Staff> getSupervisors() {
		return supervisors;
	}

	public void setSupervisors(ArrayList<Staff> supervisors) {
		this.supervisors = supervisors;
	}

	public String[] getSelectedReviewOrFinalValue() {
		return selectedReviewOrFinalValue;
	}

	public void setSelectedReviewOrFinalValue(String[] selectedReviewOrFinalValue) {
		this.selectedReviewOrFinalValue = selectedReviewOrFinalValue;
	}

	public ArrayList<LabelValueBean> getReviewOrFinalValues() {
		return reviewOrFinalValues;
	}

	public void setReviewOrFinalValues(ArrayList<LabelValueBean> reviewOrFinalValues) {
		this.reviewOrFinalValues = reviewOrFinalValues;
	}

	public ArrayList<Staff> getSignOffList() {
		return signOffList;
	}

	public void setSignOffList(ArrayList<Staff> signOffList) {
		this.signOffList = signOffList;
	}

	public ArrayList<Staff> getRoutingList() {
		return routingList;
	}

	public void setRoutingList(ArrayList<Staff> routingList) {
		this.routingList = routingList;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public boolean isFirstSignOff() {
		return isFirstSignOff;
	}

	public void setFirstSignOff(boolean isFirstSignOff) {
		this.isFirstSignOff = isFirstSignOff;
	}

	public boolean isFinalSignOff() {
		return finalSignOff;
	}

	public void setFinalSignOff(boolean finalSignOff) {
		this.finalSignOff = finalSignOff;
	}

	public Staff getPrevSignOffRecord() {
		return prevSignOffRecord;
	}

	public void setPrevSignOffRecord(Staff prevSignOffRecord) {
		this.prevSignOffRecord = prevSignOffRecord;
	}

	public boolean isCanSignOff() {
		return canSignOff;
	}

	public void setCanSignOff(boolean canSignOff) {
		this.canSignOff = canSignOff;
	}

}
