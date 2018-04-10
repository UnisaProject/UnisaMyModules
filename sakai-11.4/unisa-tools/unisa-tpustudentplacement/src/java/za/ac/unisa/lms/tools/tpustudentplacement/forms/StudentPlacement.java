package za.ac.unisa.lms.tools.tpustudentplacement.forms;
import za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.studentPlacementImpl.StudentPlacementImpl;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.DateUtil;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.StudentPlacementValidator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class StudentPlacement extends StudentPlacementImpl{
	protected String module;
	protected Integer schoolCode;
	private Integer mentorCode;
	protected String schoolDesc;
	protected Integer supervisorCode;
	protected String supervisorName;
	protected String startDate;
	protected String endDate;
	protected String numberOfWeeks;
	protected String evaluationMark;
	protected String stuNum;
	protected String name;
	private String studentContactNumber;
	private String schoolContactNumber;
	private Short districtCode;
	private String districtDescr;
	private String countryDescr;
	private String provinceDescr;
	private Short provinceCode;
	private Short countryCode;
	private String town;
	private  StudentPlacementValidator studentPlacementValidator;
	private String stuFullTime;
	private String mentorName;
	
	
	public Short getCountryCode(){
	    return countryCode;
    }
    public void setCountryCode(Short countryCode){
	    this.countryCode=countryCode;
    }
	public Short getProvinceCode(){
		return provinceCode;
	}
	public void  setProvinceCode(Short provinceCode){
		    this.provinceCode=provinceCode;
	}
	StudentPlacementImpl  studentplacementImlp;
	public  StudentPlacement(){
		       studentplacementImlp=new StudentPlacementImpl();	
		       studentPlacementValidator=new StudentPlacementValidator();
	}
	public  List validateStuPlacement( StudentPlacement  studentPlacement,int academicYear){
		             return studentPlacementValidator.validateStudentPlacement(studentPlacement,academicYear);
	}
    public String getDistrictDescr() {
		return districtDescr;
	}
	public Short getDistrictCode() {
		return districtCode;
	}
	public void setDistrictCode(Short districtCode) {
		this.districtCode = districtCode;
	}
	public void setDistrictDescr(String districtDescr) {
		this.districtDescr = districtDescr;
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
	public String  getName(){
		return name;
	}
	public void setName(String name){
		  this.name=name;
	}
	
	public void setStuNum(String stuNum){
		this.stuNum=stuNum;
	}
	public String getStuNum(){
		return stuNum;
	}
	public String getModule() {
		return module;
	}
	
	public Integer getSchoolCode() {
		return schoolCode;
	}

	public void setSchoolCode(Integer schoolCode) {
		this.schoolCode = schoolCode;
	}

	public Integer getSupervisorCode() {
		return supervisorCode;
	}

	public void setSupervisorCode(Integer supervisorCode) {
		this.supervisorCode = supervisorCode;
	}

	public void setModule(String module) {
		this.module = module;
	}	
	public String getSchoolDesc() {
		return schoolDesc;
	}
	public void setSchoolDesc(String schoolDesc) {
		this.schoolDesc = schoolDesc;
	}	
	public String getSupervisorName() {
		return supervisorName;
	}
	public void setSupervisorName(String supervisorName) {
		this.supervisorName = supervisorName;
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
	public String getTown() {
		return town;
	}
	public void setTown(String town) {
		this.town = town;
	}
	public Integer getMentorCode() {
		return mentorCode;
	}
	public void setMentorCode(Integer mentorCode) {
		this.mentorCode = mentorCode;
	}
	public String getStuFullTime() {
		return stuFullTime;
	}
	public void setStuFullTime(String stuFullTime) {
		this.stuFullTime = stuFullTime;
	}
    public void setMentorName(String mentorName) {
		this.mentorName = mentorName;
	}
    public String  getMentorName() {
		             return this.mentorName;
	}
    public void setDatesToRequest(HttpServletRequest request){
                   setDatesToRequest( request,this);
	 }
}
