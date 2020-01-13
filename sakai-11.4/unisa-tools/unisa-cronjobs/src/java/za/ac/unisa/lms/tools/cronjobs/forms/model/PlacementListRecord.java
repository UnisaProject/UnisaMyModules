package za.ac.unisa.lms.tools.cronjobs.forms.model;

public class PlacementListRecord {
	private String studentName;
	private Integer studentNumber;
	private String studentContactNumber;
	private String module;
	private Integer schoolCode;
	private String schoolDesc;
	private String schoolContactNumber;
	private Integer supervisorCode;
	private String supervisorName;
	private String supervisorContactNumber;
	private String startDate;
	private String endDate;
	private String numberOfWeeks;
	private String evaluationMark;
	private Short districtCode;
	private String districtDesc;
	private String town;
	private String emailSendDate;
	private String provinceDescr;
	private String countryDescr;
	
	public String  getCountryDescr(){
		    return countryDescr;
	}
	public void setCountryDescr(String countryDescr){
		    this.countryDescr=countryDescr;
	}
	public String getProvinceDescr(){
		return provinceDescr;
	}
	public void setProvinceDescr(String provinceDescr){
		this.provinceDescr=provinceDescr;
	}
	
	public void setTown(String town){
		this.town=town;
	}
	public String getTown(){
		return town;
	}
	public void setEmailSendDate(String emailSendDate){
		this.emailSendDate=emailSendDate;
	}
	public String getEmailSendDate(){
		return emailSendDate;
	}
	public String getDistrictDesc() {
		return districtDesc;
	}
	public Short getDistrictCode() {
		return districtCode;
	}
	public void setDistrictCode(Short districtCode) {
		this.districtCode = districtCode;
	}
	public void setDistrictDesc(String districtDesc) {
		this.districtDesc = districtDesc;
	}
	public String getStudentName() {
		return studentName;
	}	
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public Integer getStudentNumber() {
		return studentNumber;
	}
	public void setStudentNumber(Integer studentNumber) {
		this.studentNumber = studentNumber;
	}
	public String getStudentContactNumber() {
		return studentContactNumber;
	}
	public void setStudentContactNumber(String studentContactNumber) {
		this.studentContactNumber = studentContactNumber;
	}
	public String getSchoolContactNumber() {
		return schoolContactNumber;
	}
	public void setSchoolContactNumber(String schoolContactNumber) {
		this.schoolContactNumber = schoolContactNumber;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public Integer getSchoolCode() {
		return schoolCode;
	}
	public void setSchoolCode(Integer schoolCode) {
		this.schoolCode = schoolCode;
	}
	public String getSchoolDesc() {
		return schoolDesc;
	}
	public void setSchoolDesc(String schoolDesc) {
		this.schoolDesc = schoolDesc;
	}
	public Integer getSupervisorCode() {
		return supervisorCode;
	}
	public void setSupervisorCode(Integer supervisorCode) {
		this.supervisorCode = supervisorCode;
	}
	public String getSupervisorName() {
		return supervisorName;
	}
	public void setSupervisorName(String supervisorName) {
		this.supervisorName = supervisorName;
	}
	public String getSupervisorContactNumber() {
		return supervisorContactNumber;
	}
	public void setSupervisorContactNumber(String supervisorContactNumber) {
		this.supervisorContactNumber = supervisorContactNumber;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getNumberOfWeeks() {
		return numberOfWeeks;
	}
	public void setNumberOfWeeks(String numberOfWeeks) {
		this.numberOfWeeks = numberOfWeeks;
	}
	public String getEvaluationMark() {
		return evaluationMark;
	}
	public void setEvaluationMark(String evaluationMark) {
		this.evaluationMark = evaluationMark;
	}
}
