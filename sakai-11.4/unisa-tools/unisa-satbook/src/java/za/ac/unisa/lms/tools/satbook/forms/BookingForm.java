//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.satbook.forms;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;
import za.ac.unisa.lms.tools.satbook.forms.AssistantTypeRecord;

/**
 * MyEclipse Struts
 * Creation date: 09-11-2006
 *
 * XDoclet definition:
 * @struts:form name="bookingForm"
 */
public class BookingForm extends ValidatorForm {
	// --------------------------------------------------------- Instance Variables
	private final String satbook="1";
	public String getSatbook() {
		return satbook;
	}
	private final String venbook="2";
	public String getVenbook() {
		return venbook;
	}
	
	//setting the system id "1" for sat_book & for "2" for venue booking
	private String systemID;
	
	private String userPermission;
	private String mayUpdate;
	private String headingMayUpdate;
	//general
	private ArrayList monthList;
	private ArrayList yearList;
	private ArrayList registrationPeriodList;
	private ArrayList bkngTypeList;

	private String currentDate;
	private String pageStatus;
	private String url; //url to print
	private String viewStatus;

	// variables for STEP1: MAIN BOOKING INFORMATION
	private String bkngId;
	private String heading;
	private String lecturerNovellId;
	private String networkId;
	private String lecturerName;
	private String lecturerNum1;
	private String lecturerNum2;
	private String email;
	private String startDate;
	private String startDay;
	private String startMonth;
	private String startHH;
	private String startMM;
	private String startYear;
	private String endDate;
	private String endDay;
	private String endMonth;
	private String endYear;
	private String endHH;
	private String endMM;
	private String registrationPeriod;
	private String registrationPeriodDesc;
	private String registrationYear;
	private String description;
	private String bkngConfirmed;
	private String duration;
	private String rebroadcast;
	private String rebroadcast1;
	private String rebroadDate;
	private String rebroadDay;
	private String rebroadMonth;
	private String rebroadYear;
	private String createdDate;
	private String createdBy;
	private String bkngType;
	private String bkngTypeName;
	private String dpt;
	
	private String subName;

	//  variables for STEP 2: Subjects to broadcast
	private ArrayList subjects = new ArrayList();
	private String[] selectedSubjects;
	private ArrayList selectedSubjectsAL;
	private ArrayList SelectedSubjectNamesAL;
	private ArrayList subCode;

	// variables for STEP 3: Materials needed
	private ArrayList materials = new ArrayList();
	private String[] selectedMaterials;
	private ArrayList selectedMaterialsAL;
	//private ArrayList adminMaterials;
	private ArrayList adminMaterials;
	private SatbookBkngMaterialRecord materialRecord;

	// variables for STEP 4: Visitors
	private ArrayList visitors = new ArrayList(4);
	private String visitor1;
	private String visitor2;
	private String visitor3;
	private String visitor4;

	// variables for STEP 5: Classrooms/regions/venues for broadcast
	private ArrayList classrooms = new ArrayList();
	private String[] selectedClassrooms;
	private ArrayList  selectedClassroomsAL = new ArrayList();
	private String classroomStr;

	// assistants
	private ArrayList assistantTypeList = new ArrayList();

	// technical error
	private String technicalError;
	
	private String selectedSubject;
	
	public String getDpt() {
		return dpt;
	}

	public void setDpt(String dpt) {
		this.dpt = dpt;
	}

	public String getBkngTypeName() {
		return bkngTypeName;
	}

	public void setBkngTypeName(String bkngTypeName) {
		this.bkngTypeName = bkngTypeName;
	}

	public String getBkngType() {
		return bkngType;
	}

	public void setBkngType(String bkngType) {
		this.bkngType = bkngType;
	}


	
	// --------------------------------------------------------- Methods

	public String getSelectedSubject() {
		return selectedSubject;
	}

	public void setSelectedSubject(String selectedSubject) {
		this.selectedSubject = selectedSubject;
	}

	/**
	 * Method validate
	 * @param mapping
	 * @param request
	 * @return ActionErrors
	 */
	public ActionErrors validate(
		ActionMapping mapping,
		HttpServletRequest request) {

		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {

		this.selectedMaterials = null;
		this.selectedSubjects = null;
		this.selectedClassrooms = null;
		/*
		this.bkngId = "";
		this.heading = "";
		this.lecturerNovellId = "";
		this.lecturerName = "";
		this.lecturerNum1 = "";
		this.lecturerNum2 = "";
		this.startDate = "";
		this.startDay = "";
		this.startHH = "";
		this.startMM = "";
		this.startMonth = "";
		this.startYear = "";
		this.endDate = "";
		this.endDay = "";
		this.endHH = "";
		this.endMM = "";
		this.endMonth = "";
		this.endYear = "";
		this.registrationPeriod = "";
		this.registrationPeriodDesc = "";
		this.registrationPeriodList = null;
		this.registrationYear = "";
		this.description = "";
		this.bkngConfirmed = "";
		this.duration = "";
		this.selectedClassrooms = null;
		this.selectedClassroomsAL = null;
		this.selectedMaterials = null;
		this.selectedMaterialsAL = null;
		this.selectedSubjects = null;
		this.selectedSubjectsAL = null;
*/
	}

	/**
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void resetFormbean(ActionMapping mapping, HttpServletRequest request) {

		this.bkngId = "";
		this.heading = "";
		this.lecturerNovellId = "";
		this.lecturerName = "";
		this.lecturerNum1 = "";
		this.lecturerNum2 = "";
		this.startDate = "";
		this.startDay = "";
		this.startHH = "";
		this.startMM = "";
		this.startMonth = "";
		this.startYear = "";
		this.endDate = "";
		this.endDay = "";
		this.endHH = "";
		this.endMM = "";
		this.endMonth = "";
		this.endYear = "";
		this.registrationPeriod = "";
		this.registrationPeriodDesc = "";
		this.registrationPeriodList = null;
		this.registrationYear = "";
		this.description = "";
		this.bkngConfirmed = "";
		this.duration = "";
		this.selectedClassrooms = null;
		this.selectedClassroomsAL = null;
		this.selectedMaterials = null;
		this.selectedMaterialsAL = null;
		this.selectedSubjects = null;
		this.selectedSubjectsAL = null;
		this.rebroadDay = "";
		this.rebroadMonth = "";
		this.rebroadYear = "";
		this.bkngType="";
	}

	/**
	 * Returns the visitors.
	 * @return ArrayList
	 */
	public ArrayList getVisitors() {
		return visitors;
	}

	/**
	 * Set the visitors.
	 * @param visitors The visitors to set
	 */
	public void setVisitors(ArrayList visitors) {
		this.visitors = visitors;
	}

	/**
	 * Returns the startDate.
	 * @return String
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * Set the startDate.
	 * @param startDate The startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getStartDay() {
		return startDay;
	}

	public void setStartDay(String startDay) {
		this.startDay = startDay;
	}

	public String getStartMonth() {
		return startMonth;
	}

	public void setStartMonth(String startMonth) {
		this.startMonth = startMonth;
	}

	public String getStartYear() {
		return startYear;
	}

	public void setStartYear(String startYear) {
		this.startYear = startYear;
	}

	public String getStartHH() {
		return startHH;
	}

	public void setStartHH(String startHH) {
		this.startHH = startHH;
	}

	public String getStartMM() {
		return startMM;
	}

	public void setStartMM(String startMM) {
		this.startMM = startMM;
	}

	/**
	 * Returns the classrooms.
	 * @return ArrayList
	 */
	public ArrayList getClassrooms() {
		return classrooms;
	}

	/**
	 * Set the classrooms.
	 * @param classrooms The classrooms to set
	 */
	public void setClassrooms(ArrayList classrooms) {
		this.classrooms = classrooms;
	}

	/**
	 * Returns the selectedClassroomsAL.
	 * @return ArrayList
	 */
	public ArrayList getSelectedClassroomsAL() {
		return selectedClassroomsAL;
	}

	/**
	 * Set the selectedClassroomsAL.
	 * @param selectedClassroomsAL The selectedClassroomsAL to set
	 */
	public void setSelectedClassroomsAL(ArrayList selectedClassroomsAL) {
		this.selectedClassroomsAL = selectedClassroomsAL;
	}


	/**
	 * Returns the selectedClassrooms
	 * @return ArrayList
	 */
	public String[] getSelectedClassrooms() {
		return selectedClassrooms;
	}

	/**
	 * Set the selectedClassrooms.
	 * @param selectedClassrooms The selectedClassrooms to set
	 */
	public void setSelectedClassrooms(String[] selectedClassrooms) {
		this.selectedClassrooms = selectedClassrooms;
	}

	/**
	 * Returns the description.
	 * @return String
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Set the description.
	 * @param description The description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Returns the lecturerNovellId.
	 * @return String
	 */
	public String getLecturerNovellId() {
		return lecturerNovellId;
	}

	/**
	 * Set the lecturerNovellId.
	 * @param lecturerNovellId The lecturerNovellId to set
	 */
	public void setLecturerNovellId(String lecturerNovellId) {
		this.lecturerNovellId = lecturerNovellId;
	}

	public String getLecturerName() {
		return lecturerName;
	}

	public void setLecturerName(String lecturerName) {
		this.lecturerName = lecturerName;
	}

	public String getLecturerNum1() {
		return lecturerNum1;
	}

	public void setLecturerNum1(String lecturerNum1) {
		this.lecturerNum1 = lecturerNum1;
	}

	public String getLecturerNum2() {
		return lecturerNum2;
	}

	public void setLecturerNum2(String lecturerNum2) {
		this.lecturerNum2 = lecturerNum2;
	}

	/**
	 * Returns the materials.
	 * @return ArrayList
	 */
	public ArrayList getMaterials() {
		return materials;
	}

	/**
	 * Set the materials.
	 * @param materials The materials to set
	 */
	public void setMaterials(ArrayList materials) {
		this.materials = materials;
	}

	/**
	 * Returns the selectedMaterialsAL.
	 * @return ArrayList
	 */
	public ArrayList getSelectedMaterialsAL() {
		return selectedMaterialsAL;
	}

	/**
	 * Set the selectedMaterialsAL.
	 * @param selectedMaterialsAL The selectedMaterialsAL to set
	 */
	public void setSelectedMaterialsAL(ArrayList selectedMaterialsAL) {
		this.selectedMaterialsAL = selectedMaterialsAL;
	}

	/**
	 * Returns the adminMaterials.
	 * @return ArrayList
	 */
	public ArrayList getAdminMaterials() {
		return adminMaterials;
	}

	/**
	 * Set the adminMaterials.
	 * @param adminMaterials The adminMaterials to set
	 */
	public void setAdminMaterials(ArrayList adminMaterials) {
		this.adminMaterials = adminMaterials;
	}

	public SatbookBkngMaterialRecord getMaterialRecord(int index) {
		return (SatbookBkngMaterialRecord)adminMaterials.get(index);
		//return (InstDaysRecord)instDaysList.get(index);
	}

	public void setMaterialRecord(SatbookBkngMaterialRecord materialRecord, int index) {
		this.adminMaterials.set(index,materialRecord);
	}

	/**
	 * Returns the selectedMaterials
	 * @return ArrayList
	 */
	public String[] getSelectedMaterials() {
		return selectedMaterials;
	}

	/**
	 * Set the selectedMaterials.
	 * @param selectedMaterials The selectedMaterials to set
	 */
	public void setSelectedMaterials(String[] selectedMaterials) {
		this.selectedMaterials = selectedMaterials;
	}

	/**
	 * Returns the subjects.
	 * @return ArrayList
	 */
	public ArrayList getSubjects() {
		return subjects;
	}

	/**
	 * Set the subjects.
	 * @param subjects The subjects to set
	 */
	public void setSubjects(ArrayList subjects) {
		this.subjects = subjects;
	}

	/**
	 * Returns the selectedSubjectsAL.
	 * @return ArrayList
	 */
	public ArrayList getSelectedSubjectsAL() {
		return selectedSubjectsAL;
	}

	/**
	 * Set the selectedSubjectsAL.
	 * @param selectedSubjectsAL The selectedSubjectsAL to set
	 */
	public void setSelectedSubjectsAL(ArrayList selectedSubjectsAL) {
		this.selectedSubjectsAL = selectedSubjectsAL;
	}

	/**
	 * Returns the selectedSubjectsAL.
	 * @return ArrayList
	 */
	public ArrayList getAssistantTypeList() {
		return assistantTypeList;
	}

	public void setAssistantTypeList(ArrayList assistantTypeList) {
		this.assistantTypeList = assistantTypeList;
	}

	public AssistantTypeRecord getAssistantTypeRecord(int index) {
		return (AssistantTypeRecord)assistantTypeList.get(index);
	}

	public void setAssistantTypeRecord(AssistantTypeRecord assistantTypeRecord, int index) {
		this.assistantTypeList.set(index,assistantTypeRecord);
	}

	/**
	 * Returns the selectedSubjects.
	 * @return ArrayList
	 */
	public String[] getSelectedSubjects() {
		return selectedSubjects;
	}

	/**
	 * Set the selectedSubjects.
	 * @param selectedSubjects The selectedSubjects to set
	 */
	public void setSelectedSubjects(String[] selectedSubjects) {
		this.selectedSubjects = selectedSubjects;
	}

	/**
	 * Returns the registrationPeriod.
	 * @return String
	 */
	public String getRegistrationPeriod() {

		return registrationPeriod;
	}

	/**
	 * Set the registrationPeriod.
	 * @param registrationPeriod The registrationPeriod to set
	 */
	public void setRegistrationPeriod(String registrationPeriod) {
		this.registrationPeriod = registrationPeriod;
	}

	/**
	 * Returns the registrationPeriodDesc.
	 * @return String
	 */
	public String getRegistrationPeriodDesc() {

		/*
		 * 		registrationPeriodList.add(new org.apache.struts.util.LabelValueBean("First Semester","1"));
		registrationPeriodList.add(new org.apache.struts.util.LabelValueBean("Second Semester","2"));
		registrationPeriodList.add(new org.apache.struts.util.LabelValueBean("Year course","0"));
		registrationPeriodList.add(new org.apache.struts.util.LabelValueBean("Mid year course","6"));
		 */
		if (registrationPeriod.equals("1")) {
			registrationPeriodDesc = "First Semester";
		}
		if (registrationPeriod.equals("2")) {
			registrationPeriodDesc = "Second Semester";
		}
		if (registrationPeriod.equals("0")) {
			registrationPeriodDesc = "Year course";
		}
		if (registrationPeriod.equals("6")) {
			registrationPeriodDesc = "Mid year course";
		}

		return registrationPeriodDesc;
	}

	/**
	 * Set the registrationPeriodDesc.
	 * @param registrationPeriodDesc The registrationPeriodDesc to set
	 */
	public void setRegistrationPeriodDesc(String registrationPeriodDesc) {
		this.registrationPeriodDesc = registrationPeriodDesc;
	}

	/**
	 * Returns the endDate.
	 * @return String
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * Set the endDate.
	 * @param endDate The endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getEndDay() {
		return endDay;
	}

	public void setEndDay(String endDay) {
		this.endDay = endDay;
	}

	public String getEndMonth() {
		return endMonth;
	}

	public void setEndMonth(String endMonth) {
		this.endMonth = endMonth;
	}

	public String getEndYear() {
		return endYear;
	}

	public void setEndYear(String endYear) {
		this.endYear = endYear;
	}

	public String getEndHH() {
		return endHH;
	}

	public void setEndHH(String endHH) {
		this.endHH = endHH;
	}

	public String getEndMM() {
		return endMM;
	}

	public void setEndMM(String endMM) {
		this.endMM = endMM;
	}

	/**
	 * Returns the registrationYear.
	 * @return String
	 */
	public String getRegistrationYear() {
		return registrationYear;
	}

	/**
	 * Set the registrationYear.
	 * @param registrationYear The registrationYear to set
	 */
	public void setRegistrationYear(String registrationYear) {
		this.registrationYear = registrationYear;
	}

	/**
	 * Returns the heading.
	 * @return String
	 */
	public String getHeading() {
		return heading;
	}

	/**
	 * Set the heading.
	 * @param heading The heading to set
	 */
	public void setHeading(String heading) {
		this.heading = heading;
	}

	public String getBkngId() {
		return bkngId;
	}

	public void setBkngId(String bkngId) {
		this.bkngId = bkngId;
	}


	public ArrayList getMonthList() {

		monthList = new ArrayList();
		monthList.add(new org.apache.struts.util.LabelValueBean("..","0"));
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

	public ArrayList getYearList() {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");

		String stringYear = sdf.format(d);

		Integer i = new Integer(stringYear);
		int currentYear = i.intValue();
		int prevYear = currentYear -1;
		int nextYear = currentYear + 1;
		int nextYear2 = currentYear + 2;
		String currentYearStr = Integer.toString(currentYear);
		String prevYearStr = Integer.toString(prevYear);
		String nextYearStr = Integer.toString(nextYear);
		String nextYear2Str = Integer.toString(nextYear2);

		yearList = new ArrayList();
		yearList.add(new org.apache.struts.util.LabelValueBean("..", "0"));
		yearList.add(new org.apache.struts.util.LabelValueBean(prevYearStr, prevYearStr));
		yearList.add(new org.apache.struts.util.LabelValueBean(currentYearStr, currentYearStr));
		yearList.add(new org.apache.struts.util.LabelValueBean(nextYearStr, nextYearStr));
		yearList.add(new org.apache.struts.util.LabelValueBean(nextYear2Str, nextYear2Str));

		return yearList;
	}

	public void setYearList(ArrayList yearList) {
		this.yearList = yearList;
	}

	public ArrayList getRegistrationPeriodList() {

		registrationPeriodList = new ArrayList();
		registrationPeriodList.add(new org.apache.struts.util.LabelValueBean("..","."));
		registrationPeriodList.add(new org.apache.struts.util.LabelValueBean("First Semester","1"));
		registrationPeriodList.add(new org.apache.struts.util.LabelValueBean("Second Semester","2"));
		registrationPeriodList.add(new org.apache.struts.util.LabelValueBean("Year course","0"));
		registrationPeriodList.add(new org.apache.struts.util.LabelValueBean("Mid year course","6"));

		return registrationPeriodList;
	}

	public void setRegistrationPeriod(ArrayList registrationPeriodList) {
		this.registrationPeriodList = registrationPeriodList;
	}

	public String getVisitor1() {
		return visitor1;
	}

	public void setVisitor1(String visitor1) {
		this.visitor1 = visitor1;
	}

	public String getVisitor2() {
		return visitor2;
	}

	public void setVisitor2(String visitor2) {
		this.visitor2 = visitor2;
	}

	public String getVisitor3() {
		return visitor3;
	}

	public void setVisitor3(String visitor3) {
		this.visitor3 = visitor3;
	}

	public String getVisitor4() {
		return visitor4;
	}

	public void setVisitor4(String visitor4) {
		this.visitor4 = visitor4;
	}

	public String getBkngConfirmed() {
		return bkngConfirmed;
	}

	public void setBkngConfirmed(String bkngConfirmed) {
		this.bkngConfirmed = bkngConfirmed;
	}

	public String getPageStatus() {
		return pageStatus;
	}

	public void setPageStatus(String pageStatus) {
		this.pageStatus = pageStatus;
	}

	public String getTechnicalError() {
		return technicalError;
	}

	public void setTechnicalError(String technicalError) {
		this.technicalError = technicalError;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getCurrentDate() {

		GregorianCalendar calCurrent = new GregorianCalendar();
		int currentDay = calCurrent.get(Calendar.DAY_OF_MONTH);
		int currentMonth = calCurrent.get(Calendar.MONTH) + 1;
		int currentYear = calCurrent.get(Calendar.YEAR);

		String currentDayStr = "";
		String currentMonthStr = "";
		if (currentDay <= 9) {
			currentDayStr = "0"+currentDay;
		}else {
			currentDayStr = Integer.toString(currentDay);
		}
		if (currentMonth <= 9) {
			currentMonthStr = "0"+currentMonth;
		}else {
			currentMonthStr = Integer.toString(currentMonth);
		}

		String currentDate = currentDayStr+"-"+currentMonthStr+"-"+currentYear;
		System.out.println("Current date: "+currentDate);

		return currentDate;
	}

	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}

	public String getUserPermission() {
		return userPermission;
	}

	public void setUserPermission(String userPermission) {
		this.userPermission = userPermission;
	}

	public String getMayUpdate() {
		return mayUpdate;
	}

	public void setMayUpdate(String mayUpdate) {
		this.mayUpdate = mayUpdate;
	}

	public String getHeadingMayUpdate() {
		return headingMayUpdate;
	}

	public void setHeadingMayUpdate(String headingMayUpdate) {
		this.headingMayUpdate = headingMayUpdate;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getViewStatus() {
		return viewStatus;
	}

	public void setViewStatus(String viewStatus) {
		this.viewStatus = viewStatus;
	}

	public String getRebroadcast() {
		return rebroadcast;
	}

	public void setRebroadcast(String rebroadcast) {
		this.rebroadcast = rebroadcast;
	}

	public String getSubName() {
		return subName;
	}

	public void setSubName(String subName) {
		this.subName = subName;
	}

	public ArrayList getSelectedSubjectNamesAL() {
		return SelectedSubjectNamesAL;
	}

	public void setSelectedSubjectNamesAL(ArrayList selectedSubjectNamesAL) {
		SelectedSubjectNamesAL = selectedSubjectNamesAL;
	}

	public ArrayList getSubCode() {
		return subCode;
	}

	public void setSubCode(ArrayList subCode) {
		this.subCode = subCode;
	}

	public String getRebroadDate() {
		return rebroadDate;
	}

	public void setRebroadDate(String rebroadDate) {
		this.rebroadDate = rebroadDate;
	}

	public String getRebroadDay() {
		return rebroadDay;
	}

	public void setRebroadDay(String rebroadDay) {
		this.rebroadDay = rebroadDay;
	}

	public String getRebroadMonth() {
		return rebroadMonth;
	}

	public void setRebroadMonth(String rebroadMonth) {
		this.rebroadMonth = rebroadMonth;
	}

	public String getRebroadYear() {
		return rebroadYear;
	}

	public void setRebroadYear(String rebroadYear) {
		this.rebroadYear = rebroadYear;
	}

	public String getNetworkId() {
		return networkId;
	}

	public void setNetworkId(String networkId) {
		this.networkId = networkId;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRebroadcast1() {
		return rebroadcast1;
	}

	public void setRebroadcast1(String rebroadcast1) {
		this.rebroadcast1 = rebroadcast1;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getSystemID() {
		return systemID;
	}

	public void setSystemID(String systemID) {
		this.systemID = systemID;
	}
	
	public ArrayList getBkngTypeList() {
		return bkngTypeList;
	}

	public void setBkngTypeList(ArrayList bkngTypeList) {
		this.bkngTypeList = bkngTypeList;
	}
	
	public String getClassroomStr() {
		return classroomStr;
	}

	public void setClassroomStr(String classroomStr) {
		this.classroomStr = classroomStr;
	}
}

