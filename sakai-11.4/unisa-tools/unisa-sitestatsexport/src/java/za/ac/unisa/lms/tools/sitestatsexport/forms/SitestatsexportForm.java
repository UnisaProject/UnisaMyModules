package za.ac.unisa.lms.tools.sitestatsexport.forms;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.struts.action.ActionForm;

public class SitestatsexportForm extends ActionForm{

	private String college;
	private String school="-1";
	private String department="-1";
	private String CollegeCode;
	private String courseCode="-1";
	private int currentyear;
	private int nextyear;
	private String semister;
	private String year;
	private String role;
	private String person;
	private String month;
	private String year1;
	private String networkId;
	private ArrayList yearsList;
	private ArrayList yearsList1;
	public ArrayList getYearsList1() {
		yearsList1 = new ArrayList();
		 Calendar cal=Calendar.getInstance();
		 int max=cal.get(Calendar.YEAR);
		for (int i=2010; i <= max; i++) {
			yearsList1.add(new org.apache.struts.util.LabelValueBean(Integer.toString(i),Integer.toString(i)));
		}
		
		return yearsList1;
	}
	public void setYearsList1(ArrayList yearsList1) {
		this.yearsList1 = yearsList1;
	}
	private List exportList;
	public List getExportList() {
		return exportList;
	}
	public void setExportList(List exportList) {
		this.exportList = exportList;
	}
	public String getNetworkId() {
		return networkId;
	}
	public void setNetworkId(String networkId) {
		this.networkId = networkId;
	}
	public String getYear1() {
		return year1;
	}
	public void setYear1(String year1) {
		this.year1 = year1;
	}
	private String courseCode1;
	public String[] getMonths() {
		return months;
	}
	public void setMonths(String[] months) {
		this.months = months;
	}
	private String[] months;	
	public String getCourseCode1() {
		return courseCode1;
	}
	public void setCourseCode1(String courseCode1) {
		this.courseCode1 = courseCode1;
	}
	public List getSitestatsInfo() {
		return sitestatsInfo;
	}
	public void setSitestatsInfo(List sitestatsInfo) {
		this.sitestatsInfo = sitestatsInfo;
	}
	private String event;
	private List sitestatsInfo;
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getPerson() {
		return person;
	}
	public void setPerson(String person) {
		this.person = person;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	
	public String getSemister() {
		return semister;
	}
	public void setSemister(String semister) {
		this.semister = semister;
	}
	public int getCurrentyear() {
		return currentyear;
	}
	public void setCurrentyear(int currentyear) {
		this.currentyear = currentyear;
	}
	public int getNextyear() {
		return nextyear;
	}
	public void setNextyear(int nextyear) {
		this.nextyear = nextyear;
	}
	public String getCourseCode() {
		return courseCode;
	}
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	public String getCollegeCode() {
		return CollegeCode;
	}
	public void setCollegeCode(String collegeCode) {
		CollegeCode = collegeCode;
	}
	public String getCollege() {
		return college;
	}
	public void setCollege(String college) {
		this.college = college;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public ArrayList getYearsList() {
		yearsList = new ArrayList();
		 Calendar cal=Calendar.getInstance();
		 int max=cal.get(Calendar.YEAR)+1;
		for (int i=2010; i <= max; i++) {
			yearsList.add(new org.apache.struts.util.LabelValueBean(Integer.toString(i),Integer.toString(i)));
		}
		
		return yearsList;
	}
	public void setYearsList(ArrayList yearsList) {
		this.yearsList = yearsList;
	}

	
}
