package za.ac.unisa.lms.tools.maintainstaff.forms;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts.validator.ValidatorForm;

import za.ac.unisa.lms.tools.maintainstaff.DAO.MaintainStaffStudentDAO;

public class PersonForm extends ValidatorForm {

	private String userPermission; // user permission to use this maintain staff tool
	
	private ArrayList viewOptionList; // list of view options (L = teaching view/ X = examination functions/ J = Marking functions)
	private String selectedView; // current selected view
	private String personNetworkCode; // person network code
	private String personName; // person name
	private ArrayList courseList; // list of courses
	private String prevAcadPeriod; // previous academic period
	private ArrayList expandedPeriods; // list of periods mark as expanded
	private ArrayList periodHeadings; // list of period headings
	private boolean removeRole; // indicator if role was marked for removal
	private ArrayList roleOptions; // list of role options
	private String periodDesc; // description of period
	private int persNumber; // Personnel number
	private String paperNo; // Paper Number 
	private List logsList;
	
	// variables for additional entries for a person
	private int noOfRecords = 0; // no of records user wants to add
	private ArrayList noOfRecordsOptions; // list of options 1 - 10 for no of records
	private ArrayList yearList; // list of years
	private ArrayList periodList; // list of periods
	private ArrayList paperNrOptions; // list of paper Number options
	
	
	public Object getRecordIndexed(int index) {
		return courseList.get(index);
	}
	
	public Object getRecordIndexed2(int index) {
		return periodHeadings.get(index);
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
	
	public ArrayList getRoleOptions() {
		
		MaintainStaffStudentDAO db1 = new MaintainStaffStudentDAO();
		
		if (selectedView.equals("L")) {
			// if teaching view get roles for category 24.
			try {
				roleOptions = db1.selectRoleOptions(24,"'PRIML','HOD'");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if (selectedView.equals("J")) {
			// if Marking view get roles for category 217.
			try {
				roleOptions = db1.selectRoleOptions(217,"");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			// if teaching view get roles for category 24.
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
	public ArrayList getViewOptionList() {
		viewOptionList = new ArrayList();
		viewOptionList.add(new org.apache.struts.util.LabelValueBean("myUnisa Teaching Roles","L"));
		viewOptionList.add(new org.apache.struts.util.LabelValueBean("Examination Functions","E"));
		//marker and jrouter roles option waiting for Jrouter function to be ready
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
	public String getPersonNetworkCode() {
		return personNetworkCode;
	}
	public void setPersonNetworkCode(String personNetworkCode) {
		this.personNetworkCode = personNetworkCode;
	}
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	public boolean isRemoveRole() {
		return removeRole;
	}
	public void setRemoveRole(boolean removeRole) {
		this.removeRole = removeRole;
	}

	public int getNoOfRecords() {
		return noOfRecords;
	}

	public void setNoOfRecords(int noOfRecords) {
		this.noOfRecords = noOfRecords;
	}

	public ArrayList getYearList() {
		
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
		String prevYear3Str = Integer.toString(prevYear3); 
		String prevYear2Str = Integer.toString(prevYear2); 
		String prevYearStr = Integer.toString(prevYear);
		String nextYearStr = Integer.toString(nextYear);
		String nextYear2Str = Integer.toString(nextYear2);

		yearList = new ArrayList();
		yearList.add(new org.apache.struts.util.LabelValueBean("..", ""));
		yearList.add(new org.apache.struts.util.LabelValueBean(prevYear3Str, prevYear3Str));
		yearList.add(new org.apache.struts.util.LabelValueBean(prevYear2Str, prevYear2Str));
		yearList.add(new org.apache.struts.util.LabelValueBean(prevYearStr, prevYearStr));
		yearList.add(new org.apache.struts.util.LabelValueBean(currentYearStr, currentYearStr));
		yearList.add(new org.apache.struts.util.LabelValueBean(nextYearStr, nextYearStr));
		yearList.add(new org.apache.struts.util.LabelValueBean(nextYear2Str, nextYear2Str));
		
		return yearList;
	}

	public void setYearList(ArrayList yearList) {
		this.yearList = yearList;
	}

	public ArrayList getPeriodList() {
		
		if (selectedView.equals("L")) {
			// Teaching View
			
		} else {
			// examination view
		}
		
		return periodList;
	}

	public void setPeriodList(ArrayList periodList) {
		this.periodList = periodList;
	}

	public String getUserPermission() {
		return userPermission;
	}

	public void setUserPermission(String userPermission) {
		this.userPermission = userPermission;
	}

	public String getPaperNo() {
		return paperNo;
	}

	public void setPaperNo(String paperNo) {
		this.paperNo = paperNo;
	}

	public int getPersNumber() {
		return persNumber;
	}

	public void setPersNumber(int persNumber) {
		this.persNumber = persNumber;
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

}
