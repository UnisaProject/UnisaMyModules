//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.registrationstatus.forms;

import java.util.ArrayList;

import org.apache.struts.validator.ValidatorActionForm;


public class RegistrationStatusForm extends ValidatorActionForm {

	// --------------------------------------------------------- Instance Variables

	private static final long serialVersionUID = 1L;
	private static final String version = "2018001";
	
	private String applyType = "";
	private String registrationType ="";// U = undergrad, P = postgrad, S = Short learning programme
	private Student student = new Student();
	private Qualification qual = new Qualification();
	private String registrationDate = "";
	//  var used to test session throughout process
	private String fromPage;
	//	 Application for registration list
	private ArrayList applyForStudyUnits;
	//outstanding documents
	private ArrayList flagDocuments = new ArrayList();

	//Finances
	/** totalFee property */
	private double totalFee;
	/** paymentDue property */
	private double paymentDue;

	// Workflow reason
	private String reasonDate;
	private String reasonDesc;

	public double getPaymentDue() {
		return paymentDue;
	}

	public void setPaymentDue(double paymentDue) {
		this.paymentDue = paymentDue;
	}

	public double getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(double totalFee) {
		this.totalFee = totalFee;
	}

	public ArrayList getApplyForStudyUnits() {
		return applyForStudyUnits;
	}

	public void setApplyForStudyUnits(ArrayList applyForStudyUnits) {
		this.applyForStudyUnits = applyForStudyUnits;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public String getVersion() {
		return version;
	}

	public String getApplyType() {
		return applyType;
	}

	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}

	public String getRegistrationType() {
		return registrationType;
	}

	public void setRegistrationType(String registrationType) {
		this.registrationType = registrationType;
	}

	public Qualification getQual() {
		return qual;
	}

	public void setQual(Qualification qual) {
		this.qual = qual;
	}

	public String getFromPage() {
		return fromPage;
	}

	public void setFromPage(String fromPage) {
		this.fromPage = fromPage;
	}

	public ArrayList getFlagDocuments() {
		return flagDocuments;
	}

	public void setFlagDocuments(ArrayList flagDocuments) {
		this.flagDocuments = flagDocuments;
	}

	public String getReasonDesc() {
		return reasonDesc;
	}

	public void setReasonDesc(String reasonDesc) {
		this.reasonDesc = reasonDesc;
	}

	public String getReasonDate() {
		return reasonDate;
	}

	public void setReasonDate(String reasonDate) {
		this.reasonDate = reasonDate;
	}

	public String getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(String registrationDate) {
		this.registrationDate = registrationDate;
	}

}

