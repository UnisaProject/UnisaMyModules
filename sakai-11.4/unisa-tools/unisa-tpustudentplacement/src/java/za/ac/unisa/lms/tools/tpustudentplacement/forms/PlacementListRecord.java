package za.ac.unisa.lms.tools.tpustudentplacement.forms;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.PlacementListRecComparator;
import java.util.List;
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
	private Short provinceCode;
	private Short countryCode;
	private int indexInList;
	private PlacementListRecComparator placementListRecComparator;
	private String stuFullTime;
	private String mentorName;
	private Integer mentorCode;
	
	
	public  PlacementListRecord(){
		          placementListRecComparator=new PlacementListRecComparator(); 
	}
	public boolean comparePlacementListRecords(PlacementListRecord first,PlacementListRecord  sec){
		                return placementListRecComparator.PlacementListRecsEquals(first,sec);
	}
	public int comparePlacementListRecords(PlacementListRecord first,List listPlacement){
            return placementListRecComparator.PlacementListRecsEquals(first,listPlacement);
    }
	public int getIndexInList(){
		return indexInList;
	}
	public void setIndexInList(int indexInList){
		      this.indexInList=indexInList;
	}
	public Short getCountryCode(){
	    return countryCode;
    }
    public void setCountryCode(Short countryCode){
	    this.countryCode=countryCode;
    }
	public Short getProvinceCode(){
		return provinceCode;
	}
	public void setProvinceCode(Short provinceCode){
		this.provinceCode=provinceCode;
	}
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
	public String getStuFullTime() {
		return stuFullTime;
	}
	public void setStuFullTime(String stuFullTime) {
		this.stuFullTime = stuFullTime;
	}
	public String getMentorName() {
		return mentorName;
	}
	public void setMentorName(String mentorName) {
		this.mentorName = mentorName;
	}
	
        public void setMentorCode(Integer mentorCode) {
	             this.mentorCode=mentorCode;
       }
        public Integer getMentorCode() {
            return mentorCode;
         }
	}
