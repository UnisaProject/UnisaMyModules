package za.ac.unisa.lms.tools.maintainstaff.forms;

import org.apache.struts.validator.ValidatorForm;

public class HeadingForm extends ValidatorForm {
	
	private String periodDesc; // registration/examination Period description
	private String acadYear; // academic year
	private String acadPeriod; // semester period
	private String expand; // expanded
	private String course; // course code
	private boolean remove = false; // indicator if period is marked for removal 
	
	public boolean isRemove() {
		return remove;
	}
	public void setRemove(boolean remove) {
		this.remove = remove;
	}
	public String getPeriodDesc() {
		return periodDesc;
	}
	public void setPeriodDesc(String periodDesc) {
		this.periodDesc = periodDesc;
	}
	public String getAcadYear() {
		return acadYear;
	}
	public void setAcadYear(String acadYear) {
		this.acadYear = acadYear;
	}
	public String getAcadPeriod() {
		return acadPeriod;
	}
	public void setAcadPeriod(String acadPeriod) {
		this.acadPeriod = acadPeriod;
	}
	public String getExpand() {
		return expand;
	}
	public void setExpand(String expand) {
		this.expand = expand;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}

}
