package za.ac.unisa.lms.tools.adobedownload.forms;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.struts.validator.ValidatorForm;

public class CourseForm extends ValidatorForm {
	
	
	private String year; // academic year
	private ArrayList yearOptions; // list of year options
	private String acadPeriod; // registration period
	private ArrayList periodOptions; // list of period options
	private String acadPeriodDesc; // registration period descriptions
	private ArrayList reasonsOptions;//dropdown options of reasons
	private String course; // course code
	
	private String emailSubject;
	private String emailSalutation;
	private String emailMessage;
	private String emailClosing;
	
	private String downloadReason;
	private String downloadOtherReason;
	private boolean downloadCount;
	private String userId;
	private String name;
	private String personnelNr;
	
	private String agreeTerms;//radio button I agree with terms
	private String dontAgreeTerms;//radio button I do not agree with terms
	
		

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPersonnelNr() {
		return personnelNr;
	}

	public void setPersonnelNr(String personnelNr) {
		this.personnelNr = personnelNr;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public boolean isDownloadCount() {
		return downloadCount;
	}

	public void setDownloadCount(boolean downloadCount) {
		this.downloadCount = downloadCount;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public String getAcadPeriod() {
		return acadPeriod;
	}

	public void setAcadPeriod(String acadPeriod) {
		this.acadPeriod = acadPeriod;
	}

	public String getAcadPeriodDesc() {
		return acadPeriodDesc;
	}

	public void setAcadPeriodDesc(String acadPeriodDesc) {
		this.acadPeriodDesc = acadPeriodDesc;
	}

	public ArrayList getYearOptions() {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		
		String stringYear = sdf.format(d);
		Integer i = new Integer(stringYear);
		int currentYear = i.intValue();
		int prevYear = currentYear -1;
		
		String currentYearStr = Integer.toString(currentYear);
		String prevYearStr = Integer.toString(prevYear);
		
		yearOptions = new ArrayList();
		yearOptions.add(new org.apache.struts.util.LabelValueBean("Select Year", ""));
		yearOptions.add(new org.apache.struts.util.LabelValueBean(currentYearStr, currentYearStr));
		yearOptions.add(new org.apache.struts.util.LabelValueBean(prevYearStr, prevYearStr));
		
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

	public ArrayList getPeriodOptions() {
		return periodOptions;
	}

	public void setPeriodOptions(ArrayList periodOptions) {
		this.periodOptions = periodOptions;
	}

	public String getEmailSubject() {
		return emailSubject;
	}

	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}

	public String getEmailSalutation() {
		return emailSalutation;
	}

	public void setEmailSalutation(String emailSalutation) {
		this.emailSalutation = emailSalutation;
	}

	public String getEmailMessage() {
		return emailMessage;
	}

	public void setEmailMessage(String emailMessage) {
		this.emailMessage = emailMessage;
	}

	public String getEmailClosing() {
		return emailClosing;
	}

	public void setEmailClosing(String emailClosing) {
		this.emailClosing = emailClosing;
	}

	public String getDownloadReason() {
		return downloadReason;
	}

	public void setDownloadReason(String downloadReason) {
		this.downloadReason = downloadReason;
	}

	public String getDownloadOtherReason() {
		return downloadOtherReason;
	}

	public void setDownloadOtherReason(String downloadOtherReason) {
		this.downloadOtherReason = downloadOtherReason;
	}
	
	public ArrayList getReasonsOptions() {
		
		reasonsOptions = new ArrayList();
		reasonsOptions.add(new org.apache.struts.util.LabelValueBean("Computer Crashed","Computer Crashed"));
		reasonsOptions.add(new org.apache.struts.util.LabelValueBean("PC Upgrade","PC Upgrade"));
		reasonsOptions.add(new org.apache.struts.util.LabelValueBean("New PC","New PC"));
		reasonsOptions.add(new org.apache.struts.util.LabelValueBean("Insufficient Internet","Insufficient Internet"));
		reasonsOptions.add(new org.apache.struts.util.LabelValueBean("Other","Other"));

		return reasonsOptions;
	}
	
	public void setReasonsOptions(ArrayList reasonsOptions) {
		this.reasonsOptions = reasonsOptions;
	}		
	
	public String getAgreeTerms() {
		return agreeTerms;
	}

	public void setAgreeTerms(String agreeTerms) {
		this.agreeTerms = agreeTerms;
	}

	public String getDontAgreeTerms() {
		return dontAgreeTerms;
	}

	public void setDontAgreeTerms(String dontAgreeTerms) {
		this.dontAgreeTerms = dontAgreeTerms;
	}

		
}
