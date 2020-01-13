package za.ac.unisa.lms.tools.nosite.dao;

import java.util.List;

public class NositeDetails {
	private String college;
	private String college_code;
	private String school;
	private String school_code;
	private String department;
	private String department_code;

	private String total;//
	private String siteId;
	private String acadyear;
	private String period;
	private String studyunit;
	private List Inactivesites;
	private List Inactivesiteswithstudents;


	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public String getCollege() {
		return college;
	}
	public void setCollege(String college) {
		this.college = college;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getAcadyear() {
		return acadyear;
	}
	public void setAcadyear(String acadyear) {
		this.acadyear = acadyear;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public String getStudyunit() {
		return studyunit;
	}
	public void setStudyunit(String studyunit) {
		this.studyunit = studyunit;
	}

	public List getInactivesites() {
		return Inactivesites;
	}
	public void setInactivesites(List inactivesites) {
		Inactivesites = inactivesites;
	}
	public String getDepartment_code() {
		return department_code;
	}
	public void setDepartment_code(String department_code) {
		this.department_code = department_code;
	}
	public String getSchool_code() {
		return school_code;
	}
	public void setSchool_code(String school_code) {
		this.school_code = school_code;
	}
	public String getCollege_code() {
		return college_code;
	}
	public void setCollege_code(String college_code) {
		this.college_code = college_code;
	}
	public List getInactivesiteswithstudents() {
		return Inactivesiteswithstudents;
	}
	public void setInactivesiteswithstudents(List inactivesiteswithstudents) {
		Inactivesiteswithstudents = inactivesiteswithstudents;
	}
}
