package za.ac.unisa.lms.tools.maintainstaff.forms;

import java.util.List;

import org.apache.struts.validator.ValidatorForm;

import za.ac.unisa.lms.tools.maintainstaff.DAO.MaintainStaffStudentDAO;

public class RecordForm extends ValidatorForm{

	private String course; // course code
	private String acadYear; // academic year
	private String selectedView; // current selected view
	private String semesterPeriod; // semester period
	private String acadPeriodDesc; // academic period description
	private String staffNo; // staff number
	private String networkCode; // network code
	private String name; // staff name
	private String role; // role/function linked to staff member
	private String prevRole; // previous role
	private String roleDesc; // role description
	private String status; // status active/inactive
	private int personSequence; // user sequence
	private boolean remove = false; // indicator if record was marked for removal
	private String heading; // course site heading
	private String courseSite; // course site
	private String paperNo; // Paper Number
	private int personelNo; // Personnel Number
	private List logsList;
	private String percentage;//Y if there are outstanding assignments OR N if there are no outstanding assign
	
	public int getPersonelNo() {
		return personelNo;
	}
	public void setPersonelNo(int personelNo) {
		this.personelNo = personelNo;
	}
	public String getPaperNo() {
		return paperNo;
	}
	public void setPaperNo(String paperNo) {
		this.paperNo = paperNo;
	}
	public String getCourseSite() {
		return courseSite;
	}
	public void setCourseSite(String systemType) {
		if (systemType.equals("L") || systemType.equals("J")) {
			if (semesterPeriod.equals("1")) {
				courseSite = course.toUpperCase()+"-"+acadYear.substring(2,4)+"-"+"S1";
			}
			if (semesterPeriod.equals("2")) {
				courseSite = course.toUpperCase()+"-"+acadYear.substring(2,4)+"-"+"S2";
			}
			if (semesterPeriod.equals("0")) {
				courseSite = course.toUpperCase()+"-"+acadYear.substring(2,4)+"-"+"Y1";;
			}
			if (semesterPeriod.equals("6")) {
				courseSite = course.toUpperCase()+"-"+acadYear.substring(2,4)+"-"+"Y1";;
			}
		} else {
			MaintainStaffStudentDAO db1 = new MaintainStaffStudentDAO();
			try {
				courseSite = course.toUpperCase()+"/"+acadYear+"/"+db1.selectExamPeriodDesc(semesterPeriod);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		this.courseSite = courseSite;
	}
	
	public String getHeading() {
		return heading;
	}
	public void setHeading(String heading) {
		this.heading = heading;
	}
	
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	
	public String getAcadYear() {
		return acadYear;
	}
	public void setAcadYear(String acadYear) {
		this.acadYear = acadYear;
	}
	public String getSemesterPeriod() {
		return semesterPeriod;
	}
	public void setSemesterPeriod(String semesterPeriod) {
		this.semesterPeriod = semesterPeriod;
	}
	public String getAcadPeriodDesc() {
		return acadPeriodDesc;
	}
	public void setAcadPeriodDesc(String acadPeriodDesc) {
		this.acadPeriodDesc = acadPeriodDesc;
	}
	
	public String getAcadPeriodDescription(String systemType) {
		if (systemType.equals("L") || systemType.equals("J")) {
			if (semesterPeriod.equals("1")) {
				acadPeriodDesc = acadYear+"/First Semester";
			}
			if (semesterPeriod.equals("2")) {
				acadPeriodDesc = acadYear+"/Second Semester";
			}
			if (semesterPeriod.equals("0")) {
				acadPeriodDesc = acadYear+"/Year course";
			}
			if (semesterPeriod.equals("6")) {
				acadPeriodDesc = acadYear+"/Mid year course";
			}
		} else {
			MaintainStaffStudentDAO db1 = new MaintainStaffStudentDAO();
			try {
				acadPeriodDesc = acadYear+"/"+db1.selectExamPeriodDesc(semesterPeriod);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return acadPeriodDesc;
	}
	
	public String getStaffNo() {
		return staffNo;
	}
	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
	}
	public String getNetworkCode() {
		return networkCode;
	}
	public void setNetworkCode(String networkCode) {
		this.networkCode = networkCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRoleDesc() {
		return roleDesc;
	}
	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}
	public boolean isRemove() {
		return remove;
	}
	public void setRemove(boolean remove) {
		this.remove = remove;
	}
	public int getPersonSequence() {
		return personSequence;
	}

	public void setPersonSequence(int personSequence) {
		this.personSequence = personSequence;
	}
	public String getPrevRole() {
		return prevRole;
	}
	public void setPrevRole(String prevRole) {
		this.prevRole = prevRole;
	}
	public String getSelectedView() {
		return selectedView;
	}
	public void setSelectedView(String selectedView) {
		this.selectedView = selectedView;
	}
	public List getLogsList() {
		return logsList;
	}
	public void setLogsList(List logsList) {
		this.logsList = logsList;
	}
	public String getPercentage() {
		return percentage;
	}
	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}
	
}
