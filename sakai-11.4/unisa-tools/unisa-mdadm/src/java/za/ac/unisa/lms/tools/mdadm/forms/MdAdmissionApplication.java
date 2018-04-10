//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.mdadm.forms;

import java.util.ArrayList;
import za.ac.unisa.lms.domain.general.Person;
import za.ac.unisa.lms.dao.Gencod;


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
	private Gencod recommendation = new Gencod(); 
	private String advisorComment = "";
	private String structuredComment = "";
	private String requirementComment = "";
	private String notAdmittedComment = "";
	private String signOffComment = "";
	private String lecturerConsulted = " ";
	private Person contactPerson = new Person();
	private Person supervisor = new Person();
	private Person jointSupervisor = new Person();

	// --------------------------------------------------------- Methods
	
	
	public String getApplicationNr() {
		return applicationNr;
	}
	public String getLecturerConsulted() {
		return lecturerConsulted;
	}
	public void setLecturerConsulted(String lecturerConsulted) {
		this.lecturerConsulted = lecturerConsulted;
	}
	public Person getContactPerson() {
		return contactPerson;
	}
	public void setContactPerson(Person contactPerson) {
		this.contactPerson = contactPerson;
	}
	public Person getSupervisor() {
		return supervisor;
	}
	public void setSupervisor(Person supervisor) {
		this.supervisor = supervisor;
	}
	public Person getJointSupervisor() {
		return jointSupervisor;
	}
	public void setJointSupervisor(Person jointSupervisor) {
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
	public Gencod getRecommendation() {
		return recommendation;
	}
	public void setRecommendation(Gencod recommendation) {
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

