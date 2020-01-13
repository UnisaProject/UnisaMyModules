package za.ac.unisa.lms.tools.honsadmission.forms;

import java.util.List;

public class Application {
	private ApplicationPeriod applicationPeriod;
	private Student student;
	private Short choice;
	private Qualification qualification;	
	private SignOff signOff;
	private Referral refferal;
	private List<PrevQual> listPrevQual;
	private List<PrevQual> listPrevOtherQual;
	private List<PrevQual> listPrevUnisaQual;
	private List<SignOff> listSignOff;
	private List<Referral> listReferral;	
	
	public Short getChoice() {
		return choice;
	}
	public void setChoice(Short choice) {
		this.choice = choice;
	}
	public ApplicationPeriod getApplicationPeriod() {
		return applicationPeriod;
	}
	public void setApplicationPeriod(ApplicationPeriod applicationPeriod) {
		this.applicationPeriod = applicationPeriod;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public Qualification getQualification() {
		return qualification;
	}
	public void setQualification(Qualification qualification) {
		this.qualification = qualification;
	}	
	public List<PrevQual> getListPrevOtherQual() {
		return listPrevOtherQual;
	}
	public void setListPrevOtherQual(List<PrevQual> listPrevOtherQual) {
		this.listPrevOtherQual = listPrevOtherQual;
	}
	public List<PrevQual> getListPrevUnisaQual() {
		return listPrevUnisaQual;
	}
	public void setListPrevUnisaQual(List<PrevQual> listPrevUnisaQual) {
		this.listPrevUnisaQual = listPrevUnisaQual;
	}
	public List<PrevQual> getListPrevQual() {
		return listPrevQual;
	}
	public void setListPrevQual(List<PrevQual> listPrevQual) {
		this.listPrevQual = listPrevQual;
	}
	public List<SignOff> getListSignOff() {
		return listSignOff;
	}
	public void setListSignOff(List<SignOff> listSignOff) {
		this.listSignOff = listSignOff;
	}
	public SignOff getSignOff() {
		return signOff;
	}
	public void setSignOff(SignOff signOff) {
		this.signOff = signOff;
	}
	public Referral getRefferal() {
		return refferal;
	}
	public void setRefferal(Referral refferal) {
		this.refferal = refferal;
	}
	public List<Referral> getListReferral() {
		return listReferral;
	}
	public void setListReferral(List<Referral> listReferral) {
		this.listReferral = listReferral;
	}
	
 
		
}
