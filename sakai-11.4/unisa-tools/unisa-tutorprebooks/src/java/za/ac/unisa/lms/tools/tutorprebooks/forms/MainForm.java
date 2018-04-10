package za.ac.unisa.lms.tools.tutorprebooks.forms;

import org.apache.struts.validator.ValidatorForm;

import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainForm extends ValidatorForm{
	
	private String year; // academic year
	private ArrayList yearOptions; // list of year options
	//private String acadPeriod; // registration period
	private ArrayList periodOptions; // list of period options
	private String acadPeriodDesc; // registration period descriptions
	private String acadPeriod;  //registration period
	private ArrayList courseList;
	private ArrayList completedPrescribedBooksList;
	private ArrayList outstandingPrescribedBooksList;
	private boolean completedButtonClick;
	private boolean outstandingButtonClick;
	private String tutorNr;
	private String clearOption="";	
	private String userId;
	
	
		
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTutorNr() {
		return tutorNr;
	}

	public void setTutorNr(String tutorNr) {
		this.tutorNr = tutorNr;
	}

	public ArrayList getCourseList() {
		return courseList;
	}

	public void setCourseList(ArrayList courseList) {
		this.courseList = courseList;
	}

	public ArrayList getYearOptions() {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		
		String stringYear = sdf.format(d);
		Integer i = new Integer(stringYear);
		int currentYear = i.intValue();
		int nextYear = currentYear +1;
		
		String currentYearStr = Integer.toString(currentYear);
		String nextYearStr = Integer.toString(nextYear);

		yearOptions= new ArrayList();
		yearOptions.add(new org.apache.struts.util.LabelValueBean("Select Year", "-1"));
		yearOptions.add(new org.apache.struts.util.LabelValueBean(currentYearStr, currentYearStr));
		yearOptions.add(new org.apache.struts.util.LabelValueBean(nextYearStr, nextYearStr));
		
		return yearOptions;
	}

	public void setYearOptions(ArrayList yearOptions) {
		this.yearOptions = yearOptions;
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

	public ArrayList getPeriodOptions() {
		periodOptions = new ArrayList();
		periodOptions.add(new org.apache.struts.util.LabelValueBean("Select Academic Period","-1"));
		periodOptions.add(new org.apache.struts.util.LabelValueBean("0","0"));
		periodOptions.add(new org.apache.struts.util.LabelValueBean("1","1"));
		periodOptions.add(new org.apache.struts.util.LabelValueBean("2","2"));
			
		return periodOptions;
	}

	public void setPeriodOptions(ArrayList periodOptions) {
		this.periodOptions = periodOptions;
	}
	
	public ArrayList getCompletedPrescribedBooksList() {
		return completedPrescribedBooksList;
	}
	
	public void setCompletedPrescribedBooksList(ArrayList completedPrescribedBooksList) {
		this.completedPrescribedBooksList = completedPrescribedBooksList;
	}
	
	public ArrayList getOutstandingPrescribedBooksList() {
		return outstandingPrescribedBooksList;
	}
	
	public void setOutstandingPrescribedBooksList(ArrayList outstandingPrescribedBooksList) {
		this.outstandingPrescribedBooksList = outstandingPrescribedBooksList;
	}
	
	public boolean isCompletedButtonClick() {
		return completedButtonClick;
	}
	public void setCompletedButtonClick(boolean completedButtonClick) {
		this.completedButtonClick = completedButtonClick;
	}
	
	public boolean isOutstandingButtonClick() {
		return outstandingButtonClick;
	}
	public void setOutstandingButtonClick(boolean outstandingButtonClick) {
		this.outstandingButtonClick = outstandingButtonClick;
	}

	public String getClearOption() {
		return clearOption;
	}

	public void setClearOption(String clearOption) {
		this.clearOption = clearOption;
	}
	public Object getRecordIndexed(int index) {
		return courseList.get(index);
	}
	
}