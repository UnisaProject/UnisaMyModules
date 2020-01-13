package za.ac.unisa.lms.tools.maintainstaff.forms;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

import za.ac.unisa.lms.tools.maintainstaff.DAO.MaintainStaffStudentDAO;

public class MainForm extends ValidatorForm {
	
	private String course; // course code
	private String course1; // course code
	private String person; // person network code
	private String person1; // person network code
	private String personName; // person name
	private String userPermission; // permission for user to use maintain staff tool
	
	// variables for copy of course site
	private String fromYear; // copy from year
	private String toYear; // copy to year
	private String fromPeriod; // copy from period
	private String toPeriod; // copy to period
	private String fromPeriodDesc; // from period description
	private String toPeriodDesc; // to period description
	private ArrayList courseList = new ArrayList(); // list of courses
	private String selectedCourses;	 // selected course
	private ArrayList periodOptions = new ArrayList(); // list of period options
	private ArrayList yearOptions = new ArrayList(); // list of year options
	private String selectedView; // current selected view
	private ArrayList viewOptionList; // view options (L = teaching roles/ X = examination functions 
	
	// variables for display management information
	private ArrayList departmentOptions = new ArrayList(); // list of department options
	private String selectedDepartment; // current selected department
	private String dean; // dean
	private String director; // director 
	private String chair; // chair
	private ArrayList standInChairs; // list of stand in chairs
	private String displayDate; // display date
	
	/**
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void resetFormbean(ActionMapping mapping, HttpServletRequest request) {
		this.course = "";
		this.course1 = "";
		this.person = "";
		this.person1 = "";
	}

	public String getDisplayDate() {
		return displayDate;
	}
	public void setDisplayDate(String displayDate) {
		this.displayDate = displayDate;
	}
	public String getSelectedView() {
		return selectedView;
	}
	public void setSelectedView(String selectedView) {
		this.selectedView = selectedView;
	}
	
	public ArrayList getViewOptionList() {
		viewOptionList = new ArrayList();
		viewOptionList.add(new org.apache.struts.util.LabelValueBean("myUnisa Teaching Roles","L"));	
		viewOptionList.add(new org.apache.struts.util.LabelValueBean("Examination Functions","E"));
		//marking functions commented and waiting for the jrouter implementation
		viewOptionList.add(new org.apache.struts.util.LabelValueBean("Marking Functions","J"));
		return viewOptionList;
	}
	
	public void setViewOptionList(ArrayList viewOptionList) {
		
		this.viewOptionList = viewOptionList;
	}
	public ArrayList getCourseList() {
		return courseList;
	}
	public void setCourseList(ArrayList courseList) {
		this.courseList = courseList;
	}
	
	public String getSelectedCourses() {
		return selectedCourses;
	}
	public void setSelectedCourses(String selectedCourses) {
		this.selectedCourses = selectedCourses;
	}
	public String getFromYear() {
		return fromYear;
	}
	public void setFromYear(String fromYear) {
		this.fromYear = fromYear;
	}
	public String getToYear() {
		return toYear;
	}
	public void setToYear(String toYear) {
		this.toYear = toYear;
	}
	public String getFromPeriod() {
		return fromPeriod;
	}
	public void setFromPeriod(String fromPeriod) {
		this.fromPeriod = fromPeriod;
	}
	public String getToPeriod() {
		return toPeriod;
	}
	public void setToPeriod(String toPeriod) {
		this.toPeriod = toPeriod;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public String getPerson() {
		return person;
	}
	public void setPerson(String person) {
		this.person = person;
	}
	public String getCourse1() {
		return course1;
	}
	public void setCourse1(String course1) {
		this.course1 = course1;
	}
	public String getPerson1() {
		return person1;
	}
	public void setPerson1(String person1) {
		this.person1 = person1;
	}
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	
	public ArrayList getPeriodOptions() {
		return periodOptions;
	}
	public void setPeriodOptions(ArrayList periodOptions) {
		this.periodOptions = periodOptions;
	}
	
	public ArrayList getYearOptions() {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");

		String stringYear = sdf.format(d);

		Integer i = new Integer(stringYear);
		int currentYear = i.intValue();
		int prevYear = currentYear -1;
		int prevYear2 = currentYear -2;
		int nextYear = currentYear + 1;
		int nextYear2 = currentYear + 2;
		String currentYearStr = Integer.toString(currentYear);
		String prevYear2Str = Integer.toString(prevYear2); 
		String prevYearStr = Integer.toString(prevYear);
		String nextYearStr = Integer.toString(nextYear);
		String nextYear2Str = Integer.toString(nextYear2);

		yearOptions = new ArrayList();
		yearOptions.add(new org.apache.struts.util.LabelValueBean("..", ""));
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
	public String getFromPeriodDesc() {
		return fromPeriodDesc;
	}
	public void setFromPeriodDesc(String systemType) {
		if (systemType.equals("L") || systemType.equals("J")) {
			if (fromPeriod.equals("1")) {
				fromPeriodDesc = fromYear+"/First Semester";
			}
			if (fromPeriod.equals("2")) {
				fromPeriodDesc = fromYear+"/Second Semester";
			}
			if (fromPeriod.equals("0")) {
				fromPeriodDesc = fromYear+"/Year course";
			}
			if (fromPeriod.equals("6")) {
				fromPeriodDesc = fromYear+"/Mid year course";
			}
		} else {
			MaintainStaffStudentDAO db1 = new MaintainStaffStudentDAO();
			try {
				fromPeriodDesc = fromYear+"/"+db1.selectExamPeriodDesc(fromPeriod);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.fromPeriodDesc = fromPeriodDesc;
	}
	
	
	public String getToPeriodDesc() {
		return toPeriodDesc;
	}
	public void setToPeriodDesc(String systemType) {
		if (systemType.equals("L") || systemType.equals("J")) {
			if (toPeriod.equals("1")) {
				toPeriodDesc = toYear+"/First Semester";
			}
			if (toPeriod.equals("2")) {
				toPeriodDesc = toYear+"/Second Semester";
			}
			if (toPeriod.equals("0")) {
				toPeriodDesc = toYear+"/Year course";
			}
			if (toPeriod.equals("6")) {
				toPeriodDesc = toYear+"/Mid year course";
			}
		} else {
			MaintainStaffStudentDAO db1 = new MaintainStaffStudentDAO();
			try {
				toPeriodDesc = toYear+"/"+db1.selectExamPeriodDesc(toPeriod);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.toPeriodDesc = toPeriodDesc;
	}
	public ArrayList getDepartmentOptions() {
		return departmentOptions;
	}
	public void setDepartmentOptions(ArrayList departmentOptions) {
		this.departmentOptions = departmentOptions;
	}
	public String getSelectedDepartment() {
		return selectedDepartment;
	}
	public void setSelectedDepartment(String selectedDepartment) {
		this.selectedDepartment = selectedDepartment;
	}
	public String getDean() {
		return dean;
	}
	public void setDean(String dean) {
		this.dean = dean;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public String getChair() {
		return chair;
	}
	public void setChair(String chair) {
		this.chair = chair;
	}
	public ArrayList getStandInChairs() {
		return standInChairs;
	}
	public void setStandInChairs(ArrayList standInChairs) {
		this.standInChairs = standInChairs;
	}

	public String getUserPermission() {
		return userPermission;
	}

	public void setUserPermission(String userPermission) {
		this.userPermission = userPermission;
	}

}
