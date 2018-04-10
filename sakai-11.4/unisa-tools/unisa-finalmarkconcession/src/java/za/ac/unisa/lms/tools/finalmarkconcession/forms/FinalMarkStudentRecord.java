package za.ac.unisa.lms.tools.finalmarkconcession.forms;

import java.util.List;

import za.ac.unisa.lms.domain.general.Person;
import za.ac.unisa.lms.domain.registration.Qualification;

public class FinalMarkStudentRecord {
	
	private Integer studentNumber;
	private String name;
	private String studyUnit;
	private Short academicYear;
	private Short semesterPeriod;
	private String semesterType;
	private Short finalMark;
	private Short resultCode;
	private String resultDesc;
	private String resultAbbr;
	private Person primaryLecturer;
	private String status;
	private String dptDesc;	
	private String dptShortDesc;
	private String schoolAbbreviation;
	private String accessLevel;
	private StudentContactRecord contactDetails;
	private Qualification qualification;
	private String assessmentAbbr;
	private String assessmentDesc;
	private String assessmentOtherDesc;
	private String supportAbbr;
	private String supportDesc;
	private String supportOtherDesc;
	private String concessionMark;	
	private String revisedFinalMark;
	private String academicLevel;
	private String yearMarkWeight="-1";
	private String yearMarkEarned="-1";
	//private String examPeriod;	
	//private String examYear;  
	
	public String getRevisedFinalMark() {
		return revisedFinalMark;
	}
	public void setRevisedFinalMark(String revisedFinalMark) {
		this.revisedFinalMark = revisedFinalMark;
	}
	public void setYearMarkWeight(String yearMarkWeight){
		this.yearMarkWeight=yearMarkWeight;
	}
	public String  getYearMarkWeight(){
		return yearMarkWeight;
	}
	public void setYearMarkEarned(String yearMarkEarned){
		this.yearMarkEarned=yearMarkEarned;
	}
	public String  getYearMarkEarned(){
		return yearMarkEarned;
	}
//	public void setExamYear(String examYear){
//		this.examYear=examYear;
//	}
//	public String  getExamYear(){
//		return examYear;
//	}
//	public void setExamPeriod(String examPeriod){
//		this.examPeriod=examPeriod;
//	}
//	public String  getExamPeriod(){
//		return examPeriod;
//	}
	public void setAcademicLevel(String academicLevel){
		this.academicLevel=academicLevel;
	}
	public String getAcademicLevel(){
		return academicLevel;
	}
	public String getAssessmentOtherDesc() {
		return assessmentOtherDesc;
	}
	public void setAssessmentOtherDesc(String assessmentOtherDesc) {
		this.assessmentOtherDesc = assessmentOtherDesc;
	}
	public String getSupportOtherDesc() {
		return supportOtherDesc;
	}
	public void setSupportOtherDesc(String supportOtherDesc) {
		this.supportOtherDesc = supportOtherDesc;
	}
	public String getAssessmentDesc() {
		return assessmentDesc;
	}
	public void setAssessmentDesc(String assessmentDesc) {
		this.assessmentDesc = assessmentDesc;
	}
	public String getSupportDesc() {
		return supportDesc;
	}
	public void setSupportDesc(String supportDesc) {
		this.supportDesc = supportDesc;
	}
	public String getAssessmentAbbr() {
		return assessmentAbbr;
	}
	public void setAssessmentAbbr(String assessmentAbbr) {
		this.assessmentAbbr = assessmentAbbr;
	}
	public String getSupportAbbr() {
		return supportAbbr;
	}
	public void setSupportAbbr(String supportAbbr) {
		this.supportAbbr = supportAbbr;
	}
	public String getConcessionMark() {
		return concessionMark;
	}
	public void setConcessionMark(String concessionMark) {
		this.concessionMark = concessionMark;
	}
	public String getResultAbbr() {
		return resultAbbr;
	}
	public void setResultAbbr(String resultAbbr) {
		this.resultAbbr = resultAbbr;
	}
	public String getDptDesc() {
		return dptDesc;
	}
	public void setDptDesc(String dptDesc) {
		this.dptDesc = dptDesc;
	}	
	public String getDptShortDesc() {
		if (this.dptDesc!=null && this.dptDesc.length() > 10){
			dptShortDesc = this.dptDesc.substring(0,10) + "...";
		}else{
			dptShortDesc = this.dptDesc;
		}
		return dptShortDesc;
	}
	public void setDptShortDesc(String dptShortDesc) {
		this.dptShortDesc = dptShortDesc;
	}
	public StudentContactRecord getContactDetails() {
		return contactDetails;
	}
	public void setContactDetails(StudentContactRecord contactDetails) {
		this.contactDetails = contactDetails;
	}
	public String getSchoolAbbreviation() {
		return schoolAbbreviation;
	}
	public void setSchoolAbbreviation(String schoolAbbreviation) {
		this.schoolAbbreviation = schoolAbbreviation;
	}
	public Integer getStudentNumber() {
		return studentNumber;
	}
	public void setStudentNumber(Integer studentNumber) {
		this.studentNumber = studentNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStudyUnit() {
		return studyUnit;
	}
	public void setStudyUnit(String studyUnit) {
		this.studyUnit = studyUnit;
	}
	public Short getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(Short academicYear) {
		this.academicYear = academicYear;
	}
	public Short getSemesterPeriod() {
		return semesterPeriod;
	}
	public void setSemesterPeriod(Short semesterPeriod) {
		this.semesterPeriod = semesterPeriod;
	}
	public Short getFinalMark() {
		return finalMark;
	}
	public void setFinalMark(Short finalMark) {
		this.finalMark = finalMark;
	}
	public Short getResultCode() {
		return resultCode;
	}
	public void setResultCode(Short resultCode) {
		this.resultCode = resultCode;
	}
	public Person getPrimaryLecturer() {
		return primaryLecturer;
	}
	public void setPrimaryLecturer(Person primaryLecturer) {
		this.primaryLecturer = primaryLecturer;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getResultDesc() {
		return resultDesc;
	}
	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}
	public String getSemesterType() {
		return semesterType;
	}
	public void setSemesterType(String semesterType) {
		this.semesterType = semesterType;
	}
	public Qualification getQualification() {
		return qualification;
	}
	public void setQualification(Qualification qualification) {
		this.qualification = qualification;
	}
	public String getAccessLevel() {
		return accessLevel;
	}
	public void setAccessLevel(String accessLevel) {
		this.accessLevel = accessLevel;
	}
//	public List getListLecturer() {
//		return listLecturer;
//	}
//	public void setListLecturer(List listLecturer) {
//		this.listLecturer = listLecturer;
//	}
//	public String getStatusDesc() {
//		return statusDesc;
//	}
}
