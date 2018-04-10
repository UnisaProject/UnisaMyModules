package za.ac.unisa.lms.tools.maintainstaff.forms;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.apache.struts.validator.ValidatorForm;

import za.ac.unisa.lms.tools.maintainstaff.DAO.MaintainStaffStudentDAO;

public class CourseForm extends ValidatorForm {
	
	private String userPermission; // user permission for Maintain staff toole
	
	private ArrayList viewOptionList; // list op view options (L = teaching roles; X = Examination functions 
	private String selectedView; // current selected view
	private ArrayList courseList; // List of all the roles/functions for a course
	private String course; // current course code
	private String prevAcadPeriod; // previous academic period
	private ArrayList expandedPeriods; // list of expanded periods 
	private ArrayList periodHeadings; // List of period headings
	private ArrayList roleOptions; // list of role/function options
	private boolean removeRole; // indicator if role was marked for removal
	private int totalSelected = 0;
	
	// variables for add of new course site
	private String year; // academic year
	private String acadPeriod; // semester period
	private String acadPeriodDesc; // semester period descriptions
	private int noOfRecords =0; // no of records user wants to add
	private String primlNetworkCode; // Primary lecturer network code
	private String primlStaffNo; // Primary Lecturer staff number
	private String primlName; // Primary lecturer name
	private String primlStatus;
	private ArrayList noOfRecordsOptions; // List of available options for no of records to add
	private ArrayList acadPeriodOptions; // list of academic period options
	private boolean displayCourse = false; // indicator if course is collapsed or expanded
	private String paperNo;// Paper Number
	
	
	// variables for add of persons to existing course
	private ArrayList yearOptions; // list of year options
	private ArrayList periodOptions; // list of period (registration/examination) options
	private ArrayList paperNrOptions; // list of paper Number options
	
	// variables for primary lecturer update
	private String selectedPeriod; // current selected period
	private String newPrimlNetworkCode; // new primary lecturer network code - text box
	private String newPrimlNetworkCode2; // new primary lecturer network code - drop down box
	private String newPrimlName; // new primary lecturer name
	private String newPrimlStaffNo; // new primary lecturer staff number
	private String newPrimlStatus; // new primary lecturer status (active/not active)
	private String courseSite; // course site course-Year-period
	private ArrayList primLecturerOptions; // available options for primary lecturer
	private String keepPriml; //options to keep primary lecture when updating
	private List logsList;
	private String displayCourseDetails;
	
	public String getDisplayCourseDetails() {
		return displayCourseDetails;
	}
	public void setDisplayCourseDetails(String displayCourseDetails) {
		this.displayCourseDetails = displayCourseDetails;
	}
	public ArrayList getPrimLecturerOptions() {
		return primLecturerOptions;
	}
	public void setPrimLecturerOptions(ArrayList primLecturerOptions) {
		this.primLecturerOptions = primLecturerOptions;
	}
	public String getNewPrimlStatus() {
		return newPrimlStatus;
	}
	public void setNewPrimlStatus(String newPrimlStatus) {
		this.newPrimlStatus = newPrimlStatus;
	}
	public String getCourseSite() {
		return courseSite;
	}
	public void setCourseSite(String systemType) {
		if (systemType.equals("L") || systemType.equals("J")) {
			if (acadPeriod.equals("1")) {
				courseSite = course.toUpperCase()+"-"+year.substring(2,4)+"-"+"S1";
			}
			if (acadPeriod.equals("2")) {
				courseSite = course.toUpperCase()+"-"+year.substring(2,4)+"-"+"S2";
			}
			if (acadPeriod.equals("0")) {
				courseSite = course.toUpperCase()+"-"+year.substring(2,4)+"-"+"Y1";;
			}
			if (acadPeriod.equals("6")) {
				courseSite = course.toUpperCase()+"-"+year.substring(2,4)+"-"+"Y1";;
			}
		} else {
			MaintainStaffStudentDAO db1 = new MaintainStaffStudentDAO();
			try {
				courseSite = course.toUpperCase()+"/"+db1.selectExamPeriodDesc(acadPeriod);
				//courseSite = year+"/"+db1.selectExamPeriodDesc(acadPeriod);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		this.courseSite = courseSite;
	}
	
	public String getSelectedPeriod() {
		return selectedPeriod;
	}

	public void setSelectedPeriod(String selectedPeriod) {
		this.selectedPeriod = selectedPeriod;
	}

	public String getNewPrimlNetworkCode() {
		return newPrimlNetworkCode;
	}

	public void setNewPrimlNetworkCode(String newPrimlNetworkCode) {
		this.newPrimlNetworkCode = newPrimlNetworkCode;
	}

	public String getNewPrimlName() {
		return newPrimlName;
	}

	public void setNewPrimlName(String newPrimlName) {
		this.newPrimlName = newPrimlName;
	}

	public String getNewPrimlStaffNo() {
		return newPrimlStaffNo;
	}

	public void setNewPrimlStaffNo(String newPrimlStaffNo) {
		this.newPrimlStaffNo = newPrimlStaffNo;
	}

	public ArrayList getYearOptions() {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");

		String stringYear = sdf.format(d);

		Integer i = new Integer(stringYear);
		int currentYear = i.intValue();
		int prevYear = currentYear -1;
		int prevYear2 = currentYear -2;
		int prevYear3 = currentYear -3;
		int nextYear = currentYear + 1;
		int nextYear2 = currentYear + 2;
		String currentYearStr = Integer.toString(currentYear);
		String prevYear2Str = Integer.toString(prevYear2); 
		String prevYear3Str = Integer.toString(prevYear3);
		String prevYearStr = Integer.toString(prevYear);
		String nextYearStr = Integer.toString(nextYear);
		String nextYear2Str = Integer.toString(nextYear2);

		yearOptions = new ArrayList();
		yearOptions.add(new org.apache.struts.util.LabelValueBean("..", ""));
		yearOptions.add(new org.apache.struts.util.LabelValueBean(prevYear3Str, prevYear3Str));
		yearOptions.add(new org.apache.struts.util.LabelValueBean(prevYear2Str, prevYear2Str));
		yearOptions.add(new org.apache.struts.util.LabelValueBean(prevYearStr, prevYearStr));
		yearOptions.add(new org.apache.struts.util.LabelValueBean(currentYearStr, currentYearStr));
		yearOptions.add(new org.apache.struts.util.LabelValueBean(nextYearStr, nextYearStr));
		yearOptions.add(new org.apache.struts.util.LabelValueBean(nextYear2Str, nextYear2Str));
		
		return yearOptions;
	}

	public void setYearOptions(ArrayList yearOptions) {
		this.yearOptions = yearOptions;
	}

	public ArrayList getPeriodOptions() {
		return periodOptions;
	}

	public void setPeriodOptions(ArrayList periodOptions) {
		this.periodOptions = periodOptions;
	}

	public ArrayList getNoOfRecordsOptions() {
		noOfRecordsOptions = new ArrayList();
		for (int i=0; i <= 10; i++) {
			noOfRecordsOptions.add(new org.apache.struts.util.LabelValueBean(Integer.toString(i),Integer.toString(i)));
		}
		return noOfRecordsOptions;
	}
	
	public void setNoOfRecordsOptions(ArrayList noOfRecordsOptions) {
		this.noOfRecordsOptions = noOfRecordsOptions;
	}
	
	public Object getRecordIndexed(int index) {
		return courseList.get(index);
	}
	
	public Object getRecordIndexed2(int index) {
		return periodHeadings.get(index);
	}
	
	public ArrayList getRoleOptions() {
		
		MaintainStaffStudentDAO db1 = new MaintainStaffStudentDAO();
		
		if (selectedView.equals("L")) {
			// if teaching view get roles for category 24.
			try {
				roleOptions = db1.selectRoleOptions(24,"'PRIML','HOD','AGENT'");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (selectedView.equals("J")) {
			// if teaching view get roles for category 217.
			try {
				roleOptions = db1.selectRoleOptions(217,"");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			// if teaching view get roles for category 80.
			try {
				roleOptions = db1.selectRoleOptions(80,"");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		
		return roleOptions;
	}

	public void setRoleOptions(ArrayList roleOptions) {
		this.roleOptions = roleOptions;
	}

	public ArrayList getViewOptionList() {
		viewOptionList = new ArrayList();
		viewOptionList.add(new org.apache.struts.util.LabelValueBean("myUnisa Teaching Roles","L"));
		viewOptionList.add(new org.apache.struts.util.LabelValueBean("Examination Functions","E"));
		//jrouter roles option waiting for Jrouter function to be ready
		viewOptionList.add(new org.apache.struts.util.LabelValueBean("Marking Functions","J"));
		return viewOptionList;
	}

	public void setViewOptionList(ArrayList viewOptionList) {
		this.viewOptionList = viewOptionList;
	}
	

	public String getSelectedView() {
		return selectedView;
	}

	public void setSelectedView(String selectedView) {
		this.selectedView = selectedView;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public ArrayList getCourseList() {
		return courseList;
	}

	public void setCourseList(ArrayList courseList) {
		this.courseList = courseList;
	}

	public String getPrevAcadPeriod() {
		return prevAcadPeriod;
	}

	public void setPrevAcadPeriod(String prevAcadPeriod) {
		this.prevAcadPeriod = prevAcadPeriod;
	}

	public ArrayList getExpandedPeriods() {
		return expandedPeriods;
	}

	public void setExpandedPeriods(ArrayList expandedPeriods) {
		this.expandedPeriods = expandedPeriods;
	}

	public ArrayList getPeriodHeadings() {
		return periodHeadings;
	}

	public void setPeriodHeadings(ArrayList periodHeadings) {
		this.periodHeadings = periodHeadings;
	}

	public boolean isRemoveRole() {
		return removeRole;
	}

	public void setRemoveRole(boolean removeRole) {
		this.removeRole = removeRole;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getAcadPeriod() {
		return acadPeriod;
	}
	public void setAcadPeriod(String acadPeriod) {
		this.acadPeriod = acadPeriod;
	}
	public int getNoOfRecords() {
		return noOfRecords;
	}
	public void setNoOfRecords(int noOfRecords) {
		this.noOfRecords = noOfRecords;
	}


	public ArrayList getAcadPeriodOptions() {
		
		return acadPeriodOptions;
	}

	public void setAcadPeriodOptions(ArrayList acadPeriodOptions) {
		this.acadPeriodOptions = acadPeriodOptions;
	}

	public String getPrimlNetworkCode() {
		return primlNetworkCode;
	}

	public void setPrimlNetworkCode(String primlNetworkCode) {
		this.primlNetworkCode = primlNetworkCode;
	}

	public String getPrimlStaffNo() {
		return primlStaffNo;
	}

	public void setPrimlStaffNo(String primlStaffNo) {
		this.primlStaffNo = primlStaffNo;
	}

	public String getPrimlName() {
		return primlName;
	}

	public void setPrimlName(String primlName) {
		this.primlName = primlName;
	}

	public String getAcadPeriodDesc() {
		return acadPeriodDesc;
	}

	public void setAcadPeriodDesc(String systemType) {
		if (systemType.equals("L") || systemType.equals("J")) {
			if (acadPeriod.equals("1")) {
				acadPeriodDesc = year+"/First Semester";
			}
			if (acadPeriod.equals("2")) {
				acadPeriodDesc = year+"/Second Semester";
			}
			if (acadPeriod.equals("0")) {
				acadPeriodDesc = year+"/Year course";
			}
			if (acadPeriod.equals("6")) {
				acadPeriodDesc = year+"/Mid year course";
			}
		} else {
			MaintainStaffStudentDAO db1 = new MaintainStaffStudentDAO();
			try {
				acadPeriodDesc = year+"/"+db1.selectExamPeriodDesc(acadPeriod);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		this.acadPeriodDesc = acadPeriodDesc;
	}
	public boolean isDisplayCourse() {
		return displayCourse;
	}
	public void setDisplayCourse(boolean displayCourse) {
		this.displayCourse = displayCourse;
	}
	public String getNewPrimlNetworkCode2() {
		return newPrimlNetworkCode2;
	}
	public void setNewPrimlNetworkCode2(String newPrimlNetworkCode2) {
		this.newPrimlNetworkCode2 = newPrimlNetworkCode2;
	}
	public String getUserPermission() {
		return userPermission;
	}
	public void setUserPermission(String userPermission) {
		this.userPermission = userPermission;
	}
	public int getTotalSelected() {
		return totalSelected;
	}
	public void setTotalSelected(int totalSelected) {
		this.totalSelected = totalSelected;
	}
	public String getPaperNo() {
		return paperNo;
	}
	public void setPaperNo(String paperNo) {
		this.paperNo = paperNo;
	}
	
	public ArrayList getPaperNrOptions() {
		paperNrOptions = new ArrayList();
		paperNrOptions.add(new org.apache.struts.util.LabelValueBean("0","0"));
		paperNrOptions.add(new org.apache.struts.util.LabelValueBean("1","1"));
		paperNrOptions.add(new org.apache.struts.util.LabelValueBean("2","2"));
		paperNrOptions.add(new org.apache.struts.util.LabelValueBean("3","3"));
		paperNrOptions.add(new org.apache.struts.util.LabelValueBean("4","4"));
		return paperNrOptions;
	}
	
	public void setPaperNrOptions(ArrayList paperNrOptions) {
		this.paperNrOptions = paperNrOptions;
	}
	public List getLogsList() {
		return logsList;
	}
	public void setLogsList(List logsList) {
		this.logsList = logsList;
	}
	public String getKeepPriml() {
		return keepPriml;
	}
	public void setKeepPriml(String keepPriml) {
		this.keepPriml = keepPriml;
	}
	public String getPrimlStatus() {
		return primlStatus;
	}
	public void setPrimlStatus(String primlStatus) {
		this.primlStatus = primlStatus;
	}

}
