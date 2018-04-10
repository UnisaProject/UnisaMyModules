//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.satbook.forms;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.struts.validator.ValidatorForm;

import za.ac.unisa.lms.tools.satbook.dao.SatbookDAO;
import za.ac.unisa.lms.tools.satbook.dao.SatbookOracleDAO;

/**
 * MyEclipse Struts
 * Creation date: 09-14-2006
 *
 * XDoclet definition:
 * @struts:form name="adminForm"
 */
public class AdminForm extends ValidatorForm {

	// --------------------------------------------------------- Instance Variables
	private String actionStatus;
	private ArrayList institutionList;
	private ArrayList assistantTypeList;
	private String selectedAssType;
	private String selectedAssTypeForReport;
	private ArrayList materialList;
	private String selectedMaterial;
	private ArrayList classroomList;
	private String selectedClassroom;
	private ArrayList instDaysList;
	private ArrayList lecturerList;
	private String selectedLecturer;
	private String selectedInstitution;
	private ArrayList assistantList;
	private String selectedAssistant;
	private ArrayList yearList;
	private String selectedYear;
	private ArrayList monthList;
	private String selectedMonth;
	private String selectedMonthDesc; // month description 0 = January; 1=february
	private ArrayList regionList;
	private ArrayList adminList;
	private String administrator;
	private String system;
	private String systemID;
	private ArrayList systemList;
	private String bookingTypeId;
	private String bookingTypeDesc;
	private ArrayList bkngTypeList;
	private String selectedBkngType;
	private String formToUse;
	private String regionDesc;
	
	
	//private String setClassroomList;
	

	// EMAIL variables
	private String[] selectedAssistantTypes;
	private String emailHeading;
	private String emailMessage;
	private String emailFromDate;
	private String emailFromDateDD;
	private String emailFromDateMM;
	private String emailFromDateYYYY;
	private String emailToDate;
	private String emailToDateDD;
	private String emailToDateMM;
	private String emailToDateYYYY;
	private final String satbook="1";
	private final String venbook="2";
	
	public String getSatbook() {
		return satbook;
	}
	
	public String getVenbook() {
		return venbook;
	}
	

	// --------------------------------------------------------- Methods
	
	public Object getRecordIndexed(int index){
		return adminList.get(index);
	}
	
		
	/**
	 * Returns the actionStatus.
	 * @return String
	 */
	public String getActionStatus() {
		return actionStatus;
	}

	/**
	 * Set the actionStatus.
	 * @param actionStatus The actionStatus to set
	 */
	public void setActionStatus(String actionStatus) {
		this.actionStatus = actionStatus;
	}

	/**
	 * Returns the institutionList.
	 * @return ArrayList
	 */
	public ArrayList getInstitutionList() {
		return institutionList;
	}

	/**
	 * Set the institutionList.
	 * @param institutionList The institutionList to set
	 */
	public void setInstitutionList(ArrayList institutionList) {
		this.institutionList = institutionList;
	}

	/**
	 * Returns the assistantTypeList.
	 * @return Arraylist
	 */
	public ArrayList getAssistantTypeList() {
		return assistantTypeList;
	}

	/**
	 * Set the assistantTypeList.
	 * @param assistantTypeList The assistantTypeList to set
	 */
	public void setAssistantTypeList(ArrayList assistantTypeList) {
		this.assistantTypeList = assistantTypeList;
	}

	/**
	 * Returns the materialList.
	 * @return ArrayList
	 */
	public ArrayList getMaterialList() {
		return materialList;
	}

	/**
	 * Set the materialList.
	 * @param materialList The materialList to set
	 */
	public void setMaterialList(ArrayList materialList) {
		this.materialList = materialList;
	}

	/**
	 * Returns the classroomList.
	 * @return ArrayList
	 */
	public ArrayList getClassroomList() {
		return classroomList;
	}

	/**
	 * Set the classroomList.
	 * @param classroomList The classroomList to set
	 */
	public void setClassroomList(ArrayList classroomList) {
		this.classroomList = classroomList;
	}

	/**
	 * Returns the instDatesList.
	 * @return String
	 */
	public ArrayList getInstDaysList() {
		return instDaysList;
	}

	/**
	 * Set the instDatesList.
	 * @param instDatesList The instDatesList to set
	 */
	public void setInstDaysList(ArrayList instDaysList) {
		this.instDaysList = instDaysList;
	}

	public InstDaysRecord getInstDaysRecord(int index) {
		return (InstDaysRecord)instDaysList.get(index);
	}

	public void setInstDaysRecord(InstDaysRecord instDaysRecord, int index) {
		this.instDaysList.set(index,instDaysRecord);
	}

	/**
	 * Returns the lecturerList.
	 * @return ArrayList
	 */
	public ArrayList getLecturerList() {
		return lecturerList;
	}

	/**
	 * Set the lecturerList.
	 * @param lecturerList The lecturerList to set
	 */
	public void setLecturerList(ArrayList lecturerList) {
		this.lecturerList = lecturerList;
	}

	/**
	 * Returns the assistantList.
	 * @return ArrayList
	 */
	public ArrayList getAssistantList() {
		return assistantList;
	}

	/**
	 * Set the assistantList.
	 * @param assistantList The assistantList to set
	 */
	public void setAssistantList(ArrayList assistantList) {
		this.assistantList = assistantList;
	}


	/**
	 * Returns the selectedLecturer.
	 * @return String
	 */
	public String getSelectedLecturer() {
		return selectedLecturer;
	}

	/**
	 * Set the selectedLecturer.
	 * @param selectedLecturer The selectedLecturer to set
	 */
	public void setSelectedLecturer(String selectedLecturer) {
		this.selectedLecturer = selectedLecturer;
	}

	/**
	 * Returns the selectedInstitution.
	 * @return String
	 */
	public String getSelectedInstitution() {
		return selectedInstitution;
	}

	/**
	 * Set the selectedInstitution.
	 * @param selectedInstitution The selectedInstitution to set
	 */
	public void setSelectedInstitution(String selectedInstitution) {
		this.selectedInstitution = selectedInstitution;
	}

	public ArrayList getYearList() {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");

		String stringYear = sdf.format(d);

		Integer i = new Integer(stringYear);
		int currentYear = i.intValue();
		int prevYear = currentYear -1;
		int nextYear = currentYear + 1;
		String currentYearStr = Integer.toString(currentYear);
		String prevYearStr = Integer.toString(prevYear);
		String nextYearStr = Integer.toString(nextYear);

		yearList = new ArrayList();
		yearList.add(new org.apache.struts.util.LabelValueBean(prevYearStr, prevYearStr));
		yearList.add(new org.apache.struts.util.LabelValueBean(currentYearStr, currentYearStr));
		yearList.add(new org.apache.struts.util.LabelValueBean(nextYearStr, nextYearStr));

		return yearList;
	}

	public void setYearList(ArrayList yearList) {
		this.yearList = yearList;
	}

	/**
	 * Set the selectedYear.
	 * @param selectedLecturer The selectedLecturer to set
	 */
	public void setSelectedYear(String selectedYear) {
		this.selectedYear = selectedYear;
	}

	/**
	 * Returns the selectedYear.
	 * @return String
	 */
	public String getSelectedYear() {
		return selectedYear;
	}

	public ArrayList getMonthList() {

		monthList = new ArrayList();
		monthList.add(new org.apache.struts.util.LabelValueBean("January","1"));
		monthList.add(new org.apache.struts.util.LabelValueBean("February","2"));
		monthList.add(new org.apache.struts.util.LabelValueBean("March","3"));
		monthList.add(new org.apache.struts.util.LabelValueBean("April","4"));
		monthList.add(new org.apache.struts.util.LabelValueBean("May","5"));
		monthList.add(new org.apache.struts.util.LabelValueBean("June","6"));
		monthList.add(new org.apache.struts.util.LabelValueBean("July","7"));
		monthList.add(new org.apache.struts.util.LabelValueBean("August","8"));
		monthList.add(new org.apache.struts.util.LabelValueBean("September","9"));
		monthList.add(new org.apache.struts.util.LabelValueBean("October","10"));
		monthList.add(new org.apache.struts.util.LabelValueBean("November","11"));
		monthList.add(new org.apache.struts.util.LabelValueBean("December","12"));

		return monthList;
	}

	public void setMonthList(ArrayList monthList) {
		this.monthList = monthList;
	}

	/**
	 * Set the selectedMonth.
	 */
	public void setSelectedMonth(String selectedMonth) {
		this.selectedMonth = selectedMonth;
	}

	/**
	 * Returns the selectedMonth.
	 * @return String
	 */
	public String getSelectedMonth() {
		return selectedMonth;
	}

	/**
	 * Set the selectedMonthDesc.
	 */
	public void setSelectedMonthDesc(String selectedMonthDesc) {
		this.selectedMonthDesc = selectedMonthDesc;
	}

	/**
	 * Returns the selectedMonthDesc.
	 * @return String
	 */
	public String getSelectedMonthDesc() {

		if (selectedMonth.equals("1")) {
			selectedMonthDesc = "January";
		} else if (selectedMonth.equals("2")) {
			selectedMonthDesc = "February";
		} else if (selectedMonth.equals("3")) {
			selectedMonthDesc = "March";
		} else if (selectedMonth.equals("4")) {
			selectedMonthDesc = "April";
		} else if (selectedMonth.equals("5")) {
			selectedMonthDesc = "May";
		} else if (selectedMonth.equals("6")) {
			selectedMonthDesc = "June";
		} else if (selectedMonth.equals("7")) {
			selectedMonthDesc = "July";
		} else if (selectedMonth.equals("8")) {
			selectedMonthDesc = "August";
		} else if (selectedMonth.equals("9")) {
			selectedMonthDesc = "September";
		} else if (selectedMonth.equals("10")) {
			selectedMonthDesc = "October";
		} else if (selectedMonth.equals("11")) {
			selectedMonthDesc = "November";
		} else if (selectedMonth.equals("12")) {
			selectedMonthDesc = "December";
		}

		return selectedMonthDesc;
	}

	/**
	 * Returns the regionList.
	 * @return ArrayList
	 */
	public ArrayList getRegionList() {

		SatbookOracleDAO db = new SatbookOracleDAO();
		regionList = new ArrayList();
		try {
			regionList = db.selectRegions();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return regionList;
	}

	/**
	 * Set the regionList.
	 * @param regionList The regionList to set
	 */
	public void setRegionList(ArrayList regionList) {

		this.regionList = regionList;
	}

	/**
	 * Set the selectedClassroom.
	 */
	public void setSelectedClassroom(String selectedClassroom) {
		this.selectedClassroom = selectedClassroom;
	}

	/**
	 * Returns the selectedClassroom.
	 * @return String
	 */
	public String getSelectedClassroom() {
		return selectedClassroom;
	}

	/**
	 * Set the selectedMaterial.
	 */
	public void setSelectedMaterial(String selectedMaterial) {
		this.selectedMaterial = selectedMaterial;
	}

	/**
	 * Returns the selectedMaterial.
	 * @return String
	 */
	public String getSelectedMaterial() {
		return selectedMaterial;
	}

	/**
	 * Set the selectedAssType.
	 */
	public void setSelectedAssType(String selectedAssType) {
		this.selectedAssType = selectedAssType;
	}

	/**
	 * Returns the selectedAssType.
	 * @return String
	 */
	public String getSelectedAssType() {
		return selectedAssType;
	}


	/**
	 * Set the selectedAssTypeForReport.
	 */
	public void setSelectedAssTypeForReport(String selectedAssTypeForReport) {
		this.selectedAssTypeForReport = selectedAssTypeForReport;
	}

	/**
	 * Returns the selectedAssTypeForReport.
	 * @return String
	 */
	public String getSelectedAssTypeForReport() {
		return selectedAssTypeForReport;
	}

	/**
	 * Set the selectedAssistant.
	 */
	public void setSelectedAssistant(String selectedAssistant) {
		this.selectedAssistant = selectedAssistant;
	}

	/**
	 * Returns the selectedAssistant.
	 * @return String
	 */
	public String getSelectedAssistant() {
		return selectedAssistant;
	}

	public String[] getSelectedAssistantTypes() {
		return selectedAssistantTypes;
	}

	public void setSelectedAssistantTypes(String[] selectedAssistantTypes) {
		this.selectedAssistantTypes = selectedAssistantTypes;
	}

	public String getEmailHeading() {
		return emailHeading;
	}

	public void setEmailHeading(String emailHeading) {
		this.emailHeading = emailHeading;
	}

	public String getEmailMessage() {
		return emailMessage;
	}

	public void setEmailMessage(String emailMessage) {
		this.emailMessage = emailMessage;
	}

	public String getEmailFromDate() {
		return emailFromDate;
	}

	public void setEmailFromDate(String emailFromDate) {
		this.emailFromDate = emailFromDate;
	}

	public String getEmailFromDateDD() {
		return emailFromDateDD;
	}

	public void setEmailFromDateDD(String emailFromDateDD) {
		this.emailFromDateDD = emailFromDateDD;
	}

	public String getEmailFromDateMM() {
		return emailFromDateMM;
	}

	public void setEmailFromDateMM(String emailFromDateMM) {
		this.emailFromDateMM = emailFromDateMM;
	}

	public String getEmailFromDateYYYY() {
		return emailFromDateYYYY;
	}

	public void setEmailFromDateYYYY(String emailFromDateYYYY) {
		this.emailFromDateYYYY = emailFromDateYYYY;
	}

	public String getEmailToDate() {
		return emailToDate;
	}

	public void setEmailToDate(String emailToDate) {
		this.emailToDate = emailToDate;
	}

	public String getEmailToDateDD() {
		return emailToDateDD;
	}

	public void setEmailToDateDD(String emailToDateDD) {
		this.emailToDateDD = emailToDateDD;
	}

	public String getEmailToDateMM() {
		return emailToDateMM;
	}

	public void setEmailToDateMM(String emailToDateMM) {
		this.emailToDateMM = emailToDateMM;
	}

	public String getEmailToDateYYYY() {
		return emailToDateYYYY;
	}

	public void setEmailToDateYYYY(String emailToDateYYYY) {
		this.emailToDateYYYY = emailToDateYYYY;
	}

	public ArrayList getAdminList() {
		return adminList;
	}

	public void setAdminList(ArrayList adminList) {
		this.adminList = adminList;
	}

	public String getAdministrator() {
		return administrator;
	}

	public void setAdministrator(String administrator) {
		this.administrator = administrator;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public String getSystemID() {
		return systemID;
	}

	public void setSystemID(String systemID) {
		this.systemID = systemID;
	}

	public ArrayList getSystemList() {
	
		SatbookDAO satbookdao=new SatbookDAO();	
		try {
			systemList = satbookdao.selectSystemList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return systemList;
	}

	public void setSystemList(ArrayList systemList) {
		this.systemList = systemList;
	}

	public ArrayList getBkngTypeList() {
		return bkngTypeList;
	}

	public void setBkngTypeList(ArrayList bkngTypeList) {
		this.bkngTypeList = bkngTypeList;
	}

	public String getSelectedBkngType() {
		return selectedBkngType;
	}

	public void setSelectedBkngType(String selectedBkngType) {
		this.selectedBkngType = selectedBkngType;
	}

	public String getBookingTypeId() {
		return bookingTypeId;
	}

	public void setBookingTypeId(String bookingTypeId) {
		this.bookingTypeId = bookingTypeId;
	}

	public String getFormToUse() {
		return formToUse;
	}

	public void setFormToUse(String formToUse) {
		this.formToUse = formToUse;
	}

	public String getRegionDesc() {
		return regionDesc;
	}

	public void setRegionDesc(String regionDesc) {
		this.regionDesc = regionDesc;
	}

	public String getBookingTypeDesc() {
		return bookingTypeDesc;
	}

	public void setBookingTypeDesc(String bookingTypeDesc) {
		this.bookingTypeDesc = bookingTypeDesc;
	}
}

