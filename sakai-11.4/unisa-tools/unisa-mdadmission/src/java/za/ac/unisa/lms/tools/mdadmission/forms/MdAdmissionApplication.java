//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.mdadmission.forms;

import java.util.ArrayList;


/** 
 * MyEclipse Struts
 * Creation date: 08-18-2005
 * 
 * XDoclet definition:
 */
public class MdAdmissionApplication {

	// --------------------------------------------------------- Instance Variables

	private String applicationNr ="";
	private String status ="";
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	private Qualification qualification = new Qualification();
	private ArrayList<PreviousQualification> prevQualification = new ArrayList<PreviousQualification>();
	private String areaOfInterest ="";
	private String title ="";
	private String recommendation = ""; // store radio button value
	
	private String advisorComment = "";
	private String structuredComment = "";
	private String requirementComment = "";
	private String notAdmittedComment = "";
	private String signOffComment = "";
	private String lecturerConsulted = " ";
	private Staff contactPerson = new Staff();
	private Staff supervisor = new Staff();
	private Staff jointSupervisor = new Staff();
	private String previousRecommendation = "";
	private String previousRecommendationComment = "";

	// --------------------------------------------------------- Methods
	
	
	public String getApplicationNr() {
		return applicationNr;
	}	
	public String getPreviousRecommendation() {
		return previousRecommendation;
	}
	public void setPreviousRecommendation(String previousRecommendation) {
		this.previousRecommendation = previousRecommendation;
	}
	public String getPreviousRecommendationComment() {
		return previousRecommendationComment;
	}
	public void setPreviousRecommendationComment(
			String previousRecommendationComment) {
		this.previousRecommendationComment = previousRecommendationComment;
	}
	public String getLecturerConsulted() {
		return lecturerConsulted;
	}
	public void setLecturerConsulted(String lecturerConsulted) {
		this.lecturerConsulted = lecturerConsulted;
	}
	public Staff getContactPerson() {
		return contactPerson;
	}
	public void setContactPerson(Staff contactPerson) {
		this.contactPerson = contactPerson;
	}
	public Staff getSupervisor() {
		return supervisor;
	}
	public void setSupervisor(Staff supervisor) {
		this.supervisor = supervisor;
	}
	public Staff getJointSupervisor() {
		return jointSupervisor;
	}
	public void setJointSupervisor(Staff jointSupervisor) {
		this.jointSupervisor = jointSupervisor;
	}
	public void setApplicationNr(String applicationNr) {
		this.applicationNr = applicationNr;
	}
	public Qualification getQualification() {
		return qualification;
	}
	public void setQualification(Qualification qualification) {
		this.qualification = qualification;
	}
	public ArrayList<PreviousQualification> getPrevQualification() {
		return prevQualification;
	}
	public void setPrevQualification(
			ArrayList<PreviousQualification> prevQualification) {
		this.prevQualification = prevQualification;
	}
	public String getAreaOfInterest() {
		return areaOfInterest;
	}
	public void setAreaOfInterest(String areaOfInterest) {
		this.areaOfInterest = areaOfInterest;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getRecommendation() {
		return recommendation;
	}
	public void setRecommendation(String recommendation) {
		this.recommendation = recommendation;
	}
	public String getAdvisorComment() {
		return advisorComment;
	}
	public void setAdvisorComment(String advisorComment) {
		this.advisorComment = advisorComment;
	}
	public String getStructuredComment() {
		return structuredComment;
	}
	public void setStructuredComment(String structuredComment) {
		this.structuredComment = structuredComment;
	}
	public String getRequirementComment() {
		return requirementComment;
	}
	public void setRequirementComment(String requirementComment) {
		this.requirementComment = requirementComment;
	}
	public String getNotAdmittedComment() {
		return notAdmittedComment;
	}
	public void setNotAdmittedComment(String notAdmittedComment) {
		this.notAdmittedComment = notAdmittedComment;
	}
	public String getSignOffComment() {
		return signOffComment;
	}
	public void setSignOffComment(String signOffComment) {
		this.signOffComment = signOffComment;
	}
	

	
}

