package za.ac.unisa.lms.tools.finalmarkconcession.forms;

import za.ac.unisa.lms.domain.general.Person;

public class AlternativeExamOpportunityRecord {
	
	private String alternativeAssessOpt;	
	private String alternativeAssessOptDesc;
	private String alternativeAssessOtherDesc;
	private String alternativeAssessOtherShortDesc;
	private String academicSupportOpt;
	private String academicSupportDesc;
	private String academicSupportOtherDesc;
	private String academicSupportOtherShortDesc;
	private String adminDeclineOpt;
	private String adminDeclineOptDesc;
	private String adminDeclineOther;
	private Person responsibleLecturer;
	private String finalMark;
	private String concessionMark;
	private String zeroMarkReason;
	private String zeroMarkReasonDesc;
	private String status;
	private String StatusDesc;
	private String authResponseEmail;

	public String getStatusDesc() {
		return StatusDesc;
	}
	public void setStatusDesc(String statusDesc) {
		StatusDesc = statusDesc;
	}
	public String getConcessionMark() {
		return concessionMark;
	}
	public void setConcessionMark(String concessionMark) {
		this.concessionMark = concessionMark;
	}
	public String getZeroMarkReason() {
		return zeroMarkReason;
	}
	public void setZeroMarkReason(String zeroMarkReason) {
		this.zeroMarkReason = zeroMarkReason;
	}
	public String getZeroMarkReasonDesc() {
		return zeroMarkReasonDesc;
	}
	public void setZeroMarkReasonDesc(String zeroMarkReasonDesc) {
		this.zeroMarkReasonDesc = zeroMarkReasonDesc;
	}
	public String getAcademicSupportOtherShortDesc() {
		if (this.academicSupportOtherDesc!=null && this.academicSupportOtherDesc.length() > 20){
			academicSupportOtherShortDesc = this.academicSupportOtherDesc.substring(0,20) + "   ...more";
		}else{
			academicSupportOtherShortDesc = this.academicSupportOtherDesc;
		}		
		return academicSupportOtherShortDesc;
	}
	public void setAcademicSupportOtherShortDesc(String academicSupportOtherShortDesc) {
		this.academicSupportOtherShortDesc = academicSupportOtherShortDesc;
	}	
	public String getAlternativeAssessOtherShortDesc() {
		if (this.alternativeAssessOtherDesc!=null && this.alternativeAssessOtherDesc.length() > 20){
			alternativeAssessOtherShortDesc = this.alternativeAssessOtherDesc.substring(0,20) + "   ...more";
		}else{
			alternativeAssessOtherShortDesc = this.alternativeAssessOtherDesc;
		}		
		return alternativeAssessOtherShortDesc;
	}
	public void setAlternativeAssessOtherShortDesc(String alternativeAssessOtherShortDesc) {
		this.alternativeAssessOtherShortDesc = alternativeAssessOtherShortDesc;
	}	
	public String getAuthResponseEmail() {
		return authResponseEmail;
	}
	public void setAuthResponseEmail(String authResponseEmail) {
		this.authResponseEmail = authResponseEmail;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAlternativeAssessOpt() {
		return alternativeAssessOpt;
	}
	public void setAlternativeAssessOpt(String alternativeAssessOpt) {
		this.alternativeAssessOpt = alternativeAssessOpt;
	}
	public String getAcademicSupportOpt() {
		return academicSupportOpt;
	}
	public void setAcademicSupportOpt(String academicSupportOpt) {
		this.academicSupportOpt = academicSupportOpt;
	}
	public String getAlternativeAssessOtherDesc() {
		return alternativeAssessOtherDesc;
	}
	public void setAlternativeAssessOtherDesc(String alternativeAssessOtherDesc) {
		this.alternativeAssessOtherDesc = alternativeAssessOtherDesc;
	}
	public String getAcademicSupportOtherDesc() {
		return academicSupportOtherDesc;
	}
	public void setAcademicSupportOtherDesc(String academicSupportOtherDesc) {
		this.academicSupportOtherDesc = academicSupportOtherDesc;
	}	
	public Person getResponsibleLecturer() {
		return responsibleLecturer;
	}
	public void setResponsibleLecturer(Person responsibleLecturer) {
		this.responsibleLecturer = responsibleLecturer;
	}
	public String getFinalMark() {
		return finalMark;
	}
	public void setFinalMark(String finalMark) {
		this.finalMark = finalMark;
	}
	public String getAlternativeAssessOptDesc() {
		return alternativeAssessOptDesc;
	}
	public void setAlternativeAssessOptDesc(String alternativeAccessOptDesc) {
		this.alternativeAssessOptDesc = alternativeAccessOptDesc;
	}
	public String getAcademicSupportDesc() {
		return academicSupportDesc;
	}
	public void setAcademicSupportDesc(String academicSupportDesc) {
		this.academicSupportDesc = academicSupportDesc;
	}
	public String getAdminDeclineOpt() {
		return adminDeclineOpt;
	}
	public void setAdminDeclineOpt(String adminDeclineOpt) {
		this.adminDeclineOpt = adminDeclineOpt;
	}
	public String getAdminDeclineOther() {
		return adminDeclineOther;
	}
	public void setAdminDeclineOther(String adminDeclineOther) {
		this.adminDeclineOther = adminDeclineOther;
	}
	public String getAdminDeclineOptDesc() {
		return adminDeclineOptDesc;
	}
	public void setAdminDeclineOptDesc(String adminDeclineOptDesc) {
		this.adminDeclineOptDesc = adminDeclineOptDesc;
	}

}
