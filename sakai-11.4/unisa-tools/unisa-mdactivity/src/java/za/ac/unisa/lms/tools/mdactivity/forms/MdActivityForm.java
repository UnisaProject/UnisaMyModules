//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.mdactivity.forms;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;
import org.apache.struts.validator.ValidatorForm;
import za.ac.unisa.lms.domain.general.Person;

/**
 * MyEclipse Struts
 * Creation date: 08-18-2005
 *
 * XDoclet definition:
 * @struts:form name="acadHistoryDisplayForm"
 */
public class MdActivityForm extends ValidatorForm {

	// --------------------------------------------------------- Instance Variables

	private Student student;
	private String studyUnitCode;
	private Qualification qualification;
	private String firstRegistrationDate;
	private int yearsRegistered;
	private int yearsRegisteredForResearchProposal;
	//	private String qualificationCode;
//	private String qualificationDescr;
	private String disType;
	private String disTitle;
	private String promoter;
	private String supervisor;
	private String regPermission;
	private String inputPermission;
	private String regReason;
	private boolean readOnly;
	private String errorMessage;
	// This arraylist used to store the activity records for the specific student
	private ArrayList activityRecords;
	private ArrayList studyUnitRecords;
	private String selectedStudyUnit;
	private String selectedActivityRecord;
	private String selectedStudent = "";
	private ActivityRecord newActivity;
	private ActivityRecord updateActivity;
	// This string is used to store the value of the selected activity code on the add page
	private String selectedActivity;
	private String userCode;
	private int staffNumber;
	private String studentAnnualStatus;
	private Person user;
	// use to determine wether the feedback date of a record can be updated
	private boolean updateFeedbackDate;
	private ArrayList<Promotor> promotorList = new ArrayList<Promotor>();
	private ArrayList<LabelValueBean> studentLookupList = new ArrayList<LabelValueBean>();

	// --------------------------------------------------------- Methods	
	


	public String getInputPermission() {
		return inputPermission;
	}

	public void setInputPermission(String inputPermission) {
		this.inputPermission = inputPermission;
	}
	
	public ArrayList<LabelValueBean> getStudentLookupList() {
		return studentLookupList;
	}


	public void setStudentLookupList(ArrayList<LabelValueBean> studentLookupList) {
		this.studentLookupList = studentLookupList;
	}

	
	public String getFirstRegistrationDate() {
		return firstRegistrationDate;
	}
	

	
	public void setFirstRegistrationDate(String firstRegistrationDate) {
		this.firstRegistrationDate = firstRegistrationDate;
	}
	
	public int getYearsRegistered() {
		return yearsRegistered;
	}

	public void setYearsRegistered(int yearsRegistered) {
		this.yearsRegistered = yearsRegistered;
	}

	public int getYearsRegisteredForResearchProposal() {
		return yearsRegisteredForResearchProposal;
	}

	public void setYearsRegisteredForResearchProposal(int yearsRegisteredForResearchProposal) {
		this.yearsRegisteredForResearchProposal = yearsRegisteredForResearchProposal;
	}

	public Person getUser() {
		return user;
	}

	public String getRegReason() {
		return regReason;
	}

	public void setRegReason(String regReason) {
		this.regReason = regReason;
	}

	public Qualification getQualification() {
		return qualification;
	}

	public void setQualification(Qualification qualification) {
		this.qualification = qualification;
	}

	public void setUser(Person user) {
		this.user = user;
	}

	public String getSelectedStudent() {
		return selectedStudent;
	}

	public void setSelectedStudent(String selectedStudent) {
		this.selectedStudent = selectedStudent;
	}
	public ArrayList<Promotor> getPromotorList() {
		return promotorList;
	}

	public void setPromotorList(ArrayList<Promotor> promotorList) {
		this.promotorList = promotorList;
	}

	public boolean isUpdateFeedbackDate() {
		return updateFeedbackDate;
	}

	public void setUpdateFeedbackDate(boolean updateFeedbackDate) {
		this.updateFeedbackDate = updateFeedbackDate;
	}

	public String getStudentAnnualStatus() {
		return studentAnnualStatus;
	}

	public void setStudentAnnualStatus(String studentAnnualStatus) {
		this.studentAnnualStatus = studentAnnualStatus;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public ArrayList getActivityRecords() {
		return activityRecords;
	}

	public void setActivityRecords(ArrayList activityRecords) {
		this.activityRecords = activityRecords;
	}

	public String getDisTitle() {
		return disTitle;
	}

	public void setDisTitle(String disTitle) {
		this.disTitle = disTitle;
	}

	public String getDisType() {
		return disType;
	}

	public void setDisType(String disType) {
		this.disType = disType;
	}

//	public String getQualificationCode() {
//		return qualificationCode;
//	}
//
//	public void setQualificationCode(String qualificationCode) {
//		this.qualificationCode = qualificationCode;
//	}

	public String getStudyUnitCode() {
		return studyUnitCode;
	}

	public void setStudyUnitCode(String studyUnitCode) {
		this.studyUnitCode = studyUnitCode;
	}

	/**
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {

		// TODO Auto-generated method stub
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public String getSelectedStudyUnit() {
		return selectedStudyUnit;
	}

	public void setSelectedStudyUnit(String selectedStudyUnit) {
		this.selectedStudyUnit = selectedStudyUnit;
	}

	public ArrayList getStudyUnitRecords() {
		return studyUnitRecords;
	}

	public void setStudyUnitRecords(ArrayList studyUnitRecords) {
		this.studyUnitRecords = studyUnitRecords;
	}

	public String getSelectedActivityRecord() {
		return selectedActivityRecord;
	}

	public void setSelectedActivityRecord(String selectedActivityRecord) {
		this.selectedActivityRecord = selectedActivityRecord;
	}

	public ActivityRecord getNewActivity() {
		return newActivity;
	}

	public void setNewActivity(ActivityRecord newActivity) {
		this.newActivity = newActivity;
	}

	public ActivityRecord getUpdateActivity() {
		return updateActivity;
	}

	public void setUpdateActivity(ActivityRecord updateActivity) {
		this.updateActivity = updateActivity;
	}

	public String getSelectedActivity() {
		return selectedActivity;
	}

	public void setSelectedActivity(String selectedActivity) {
		this.selectedActivity = selectedActivity;
	}

	public int getStaffNumber() {
		return staffNumber;
	}

	public void setStaffNumber(int staffNumber) {
		this.staffNumber = staffNumber;
	}

	public String getPromoter() {
		return promoter;
	}

	public void setPromoter(String promoter) {
		this.promoter = promoter;
	}

	public String getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}

//	public String getQualificationDescr() {
//		return qualificationDescr;
//	}
//
//	public void setQualificationDescr(String qualificationDescr) {
//		this.qualificationDescr = qualificationDescr;
//	}

	public String getRegPermission() {
		return regPermission;
	}

	public void setRegPermission(String regPermission) {
		this.regPermission = regPermission;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}



}

