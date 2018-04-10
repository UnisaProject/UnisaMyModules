package za.ac.unisa.lms.tools.unmarkassfollowup.forms;

import org.apache.struts.validator.ValidatorActionForm;

import java.util.List;
import org.apache.struts.validator.ValidatorActionForm;
import za.ac.unisa.lms.domain.general.Person;

public class UnmarkAssFollowUpForm extends ValidatorActionForm{
	
	private static final long serialVersionUID = 1L;	
	private String acadYear;
	private String daysOutstanding;	
	private String selectedBreakdownLevel;	
	private Short selectedCollegeCode;
	private Short selectedSchoolCode;
	private String selectedSchool;
	private Short selectedDptCode;
	private String selectedAssignmentType;
	private String selectedModeOfSubmission;
	private String selectedModeOfMarking;
	private String selectedSortOn;
	
	private String semester;
	private String semesterType;
	private CollegeRecord college;
	private DepartmentRecord department;
	private SchoolRecord school;	
	private String markerPersno;
	private Person marker;
	private Person user;
	private String userId;
	private String studyUnit;
	private String studyUnitDesc;
	private AssignmentRecord assignment;	
	//detail
	private String selectedDetailBreakdownLevel;
	private String selectedDetailSubBreakMarkerName;	
	private DepartmentRecord detailDepartment;
	private Short selectedDetailDptCode;
	private String selectedDetailSortOn;
	private String detailStudyUnit;
	private String detailStudyUnitDesc;
	private AssignmentRecord detailAssignment;	
	private String detailDaysOutstanding;
	private String detailToDaysOutstanding;	
	private String detailMarkerPersno;
	private Person detailMarker;
	private String downloadDetailFile;
	
	//summary	
	private String selectedSummaryBreakdownLevel;
	private DepartmentRecord summaryDepartment;	
	private Short selectedSummaryDptCode;
	private String selectedSummariseOn;
	private String selectedSummarySortOn;
	private String summaryStudyUnit;
	private String summaryStudyUnitDesc;
	private AssignmentRecord summaryAssignment;	
	private String summaryDaysOutstanding;
	private String summaryMarkerPersno;
	private Person summaryMarker;
	private String downloadSummaryFile;
	private String summaryListSummariseOn;
	
	private List listSemester;
	private List listAssignmentType;
	private List listModeOfSubmission;
	private List listModeOfMarking;
	private List listCollege;
	private List listDepartment;
	private List listSchool;
	private List listStudyUnit;
	private List listBreakdownLevel;
	private List listSummary;
	private List listRecipient;
	private String currentPage;
	private String currentReport;
	private String fromPage;
	private String userType;
	private String fromSystem;
	private String emailRecipient;
	private String emailMessage;
	private String recipientEmailAddress;
	private String selectedRecipientIndex;
	
	public String getSelectedDetailSubBreakMarkerName() {
		return selectedDetailSubBreakMarkerName;
	}
	public void setSelectedDetailSubBreakMarkerName(
			String selectedDetailSubBreakMarkerName) {
		this.selectedDetailSubBreakMarkerName = selectedDetailSubBreakMarkerName;
	}
	public String getCurrentReport() {
		return currentReport;
	}
	public void setCurrentReport(String currentReport) {
		this.currentReport = currentReport;
	}
	public String getSelectedRecipientIndex() {
		return selectedRecipientIndex;
	}
	public void setSelectedRecipientIndex(String selectedRecipientIndex) {
		this.selectedRecipientIndex = selectedRecipientIndex;
	}
	public String getRecipientEmailAddress() {
		return recipientEmailAddress;
	}
	public void setRecipientEmailAddress(String recipientEmailAddress) {
		this.recipientEmailAddress = recipientEmailAddress;
	}
	public List getListRecipient() {
		return listRecipient;
	}
	public void setListRecipient(List listRecipient) {
		this.listRecipient = listRecipient;
	}
	public String getEmailRecipient() {
		return emailRecipient;
	}
	public void setEmailRecipient(String emailRecipient) {
		this.emailRecipient = emailRecipient;
	}
	public String getEmailMessage() {
		return emailMessage;
	}
	public void setEmailMessage(String emailMessage) {
		this.emailMessage = emailMessage;
	}
	public String getFromSystem() {
		return fromSystem;
	}
	public void setFromSystem(String fromSystem) {
		this.fromSystem = fromSystem;
	}
	public List getListStudyUnit() {
		return listStudyUnit;
	}
	public void setListStudyUnit(List listStudyUnit) {
		this.listStudyUnit = listStudyUnit;
	}
	public String getSummaryListSummariseOn() {
		return summaryListSummariseOn;
	}
	public void setSummaryListSummariseOn(String summaryListSummariseOn) {
		this.summaryListSummariseOn = summaryListSummariseOn;
	}
	public String getDownloadDetailFile() {
		return downloadDetailFile;
	}
	public void setDownloadDetailFile(String downloadDetailFile) {
		this.downloadDetailFile = downloadDetailFile;
	}
	public String getDownloadSummaryFile() {
		return downloadSummaryFile;
	}
	public void setDownloadSummaryFile(String downloadSummaryFile) {
		this.downloadSummaryFile = downloadSummaryFile;
	}
	public String getDetailMarkerPersno() {
		return detailMarkerPersno;
	}
	public void setDetailMarkerPersno(String detailMarkerPersno) {
		this.detailMarkerPersno = detailMarkerPersno;
	}
	public Person getDetailMarker() {
		return detailMarker;
	}
	public void setDetailMarker(Person detailMarker) {
		this.detailMarker = detailMarker;
	}
	public String getSummaryMarkerPersno() {
		return summaryMarkerPersno;
	}
	public void setSummaryMarkerPersno(String summaryMarkerPersno) {
		this.summaryMarkerPersno = summaryMarkerPersno;
	}
	public Person getSummaryMarker() {
		return summaryMarker;
	}
	public void setSummaryMarker(Person summaryMarker) {
		this.summaryMarker = summaryMarker;
	}
	public List getListSummary() {
		return listSummary;
	}
	public void setListSummary(List listSummary) {
		this.listSummary = listSummary;
	}
	public String getFromPage() {
		return fromPage;
	}
	public void setFromPage(String fromPage) {
		this.fromPage = fromPage;
	}
	public String getSelectedDetailBreakdownLevel() {
		return selectedDetailBreakdownLevel;
	}
	public void setSelectedDetailBreakdownLevel(String selectedDetailBreakdownLevel) {
		this.selectedDetailBreakdownLevel = selectedDetailBreakdownLevel;
	}
	public DepartmentRecord getDetailDepartment() {
		return detailDepartment;
	}
	public void setDetailDepartment(DepartmentRecord detailDepartment) {
		this.detailDepartment = detailDepartment;
	}
	public Short getSelectedDetailDptCode() {
		return selectedDetailDptCode;
	}
	public void setSelectedDetailDptCode(Short selectedDetailDptCode) {
		this.selectedDetailDptCode = selectedDetailDptCode;
	}
	public String getSelectedDetailSortOn() {
		return selectedDetailSortOn;
	}
	public void setSelectedDetailSortOn(String selectedDetailSortOn) {
		this.selectedDetailSortOn = selectedDetailSortOn;
	}
	public String getDetailStudyUnit() {
		return detailStudyUnit;
	}
	public void setDetailStudyUnit(String detailStudyUnit) {
		this.detailStudyUnit = detailStudyUnit;
	}
	public String getDetailStudyUnitDesc() {
		return detailStudyUnitDesc;
	}
	public void setDetailStudyUnitDesc(String detailStudyUnitDesc) {
		this.detailStudyUnitDesc = detailStudyUnitDesc;
	}
	public AssignmentRecord getDetailAssignment() {
		return detailAssignment;
	}
	public void setDetailAssignment(AssignmentRecord detailAssignment) {
		this.detailAssignment = detailAssignment;
	}
	public String getDetailDaysOutstanding() {
		return detailDaysOutstanding;
	}
	public void setDetailDaysOutstanding(String detailDaysOutstanding) {
		this.detailDaysOutstanding = detailDaysOutstanding;
	}
	public String getDetailToDaysOutstanding() {
		return detailToDaysOutstanding;
	}
	public void setDetailToDaysOutstanding(String detailToDaysOutstanding) {
		this.detailToDaysOutstanding = detailToDaysOutstanding;
	}
	public String getSelectedSummaryBreakdownLevel() {
		return selectedSummaryBreakdownLevel;
	}
	public void setSelectedSummaryBreakdownLevel(
			String selectedSummaryBreakdownLevel) {
		this.selectedSummaryBreakdownLevel = selectedSummaryBreakdownLevel;
	}
	public DepartmentRecord getSummaryDepartment() {
		return summaryDepartment;
	}
	public void setSummaryDepartment(DepartmentRecord summaryDepartment) {
		this.summaryDepartment = summaryDepartment;
	}
	public Short getSelectedSummaryDptCode() {
		return selectedSummaryDptCode;
	}
	public void setSelectedSummaryDptCode(Short selectedSummaryDptCode) {
		this.selectedSummaryDptCode = selectedSummaryDptCode;
	}
	public String getSelectedSummarySortOn() {
		return selectedSummarySortOn;
	}
	public void setSelectedSummarySortOn(String selectedSummarySortOn) {
		this.selectedSummarySortOn = selectedSummarySortOn;
	}
	public String getSummaryStudyUnit() {
		return summaryStudyUnit;
	}
	public void setSummaryStudyUnit(String summaryStudyUnit) {
		this.summaryStudyUnit = summaryStudyUnit;
	}
	public String getSummaryStudyUnitDesc() {
		return summaryStudyUnitDesc;
	}
	public void setSummaryStudyUnitDesc(String summaryStudyUnitDesc) {
		this.summaryStudyUnitDesc = summaryStudyUnitDesc;
	}
	public AssignmentRecord getSummaryAssignment() {
		return summaryAssignment;
	}
	public void setSummaryAssignment(AssignmentRecord summaryAssignment) {
		this.summaryAssignment = summaryAssignment;
	}
	public String getSummaryDaysOutstanding() {
		return summaryDaysOutstanding;
	}
	public void setSummaryDaysOutstanding(String summaryDaysOutstanding) {
		this.summaryDaysOutstanding = summaryDaysOutstanding;
	}
	public String getSelectedSortOn() {
		return selectedSortOn;
	}
	public void setSelectedSortOn(String selectedSortOn) {
		this.selectedSortOn = selectedSortOn;
	}	
	public String getSelectedSummariseOn() {
		return selectedSummariseOn;
	}
	public void setSelectedSummariseOn(String selectedSummariseOn) {
		this.selectedSummariseOn = selectedSummariseOn;
	}	
	public String getMarkerPersno() {
		return markerPersno;
	}
	public void setMarkerPersno(String markerPersno) {
		this.markerPersno = markerPersno;
	}
	public String getStudyUnitDesc() {
		return studyUnitDesc;
	}
	public void setStudyUnitDesc(String studyUnitDesc) {
		this.studyUnitDesc = studyUnitDesc;
	}
	public String getSemesterType() {
		return semesterType;
	}
	public void setSemesterType(String semesterType) {
		this.semesterType = semesterType;
	}
	public String getSelectedAssignmentType() {
		return selectedAssignmentType;
	}
	public void setSelectedAssignmentType(String selectedAssignmentType) {
		this.selectedAssignmentType = selectedAssignmentType;
	}
	public String getSelectedModeOfSubmission() {
		return selectedModeOfSubmission;
	}
	public void setSelectedModeOfSubmission(String selectedModeOfSubmission) {
		this.selectedModeOfSubmission = selectedModeOfSubmission;
	}
	public String getSelectedModeOfMarking() {
		return selectedModeOfMarking;
	}
	public void setSelectedModeOfMarking(String selectedModeOfMarking) {
		this.selectedModeOfMarking = selectedModeOfMarking;
	}
	public String getSelectedBreakdownLevel() {
		return selectedBreakdownLevel;
	}
	public void setSelectedBreakdownLevel(String selectedBreakdownLevel) {
		this.selectedBreakdownLevel = selectedBreakdownLevel;
	}
	public Short getSelectedCollegeCode() {
		return selectedCollegeCode;
	}
	public void setSelectedCollegeCode(Short selectedCollegeCode) {
		this.selectedCollegeCode = selectedCollegeCode;
	}
	public Short getSelectedSchoolCode() {
		return selectedSchoolCode;
	}
	public void setSelectedSchoolCode(Short selectedSchoolCode) {
		this.selectedSchoolCode = selectedSchoolCode;
	}
	public String getSelectedSchool() {
		return selectedSchool;
	}
	public void setSelectedSchool(String selectedSchool) {
		this.selectedSchool = selectedSchool;
	}
	public Short getSelectedDptCode() {
		return selectedDptCode;
	}
	public void setSelectedDptCode(Short selectedDptCode) {
		this.selectedDptCode = selectedDptCode;
	}
	public List getListDepartment() {
		return listDepartment;
	}
	public void setListDepartment(List listDepartment) {
		this.listDepartment = listDepartment;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getCurrentPage() {
		return currentPage;
	}	
	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}
	public String getAcadYear() {
		return acadYear;
	}
	public void setAcadYear(String acadYear) {
		this.acadYear = acadYear;
	}
	public String getDaysOutstanding() {
		return daysOutstanding;
	}
	public void setDaysOutstanding(String daysOutstanding) {
		this.daysOutstanding = daysOutstanding;
	}	
	public String getSemester() {
		return semester;
	}
	public void setSemester(String semester) {
		this.semester = semester;
	}
	public CollegeRecord getCollege() {
		return college;
	}
	public void setCollege(CollegeRecord college) {
		this.college = college;
	}
	public DepartmentRecord getDepartment() {
		return department;
	}
	public void setDepartment(DepartmentRecord department) {
		this.department = department;
	}
	public SchoolRecord getSchool() {
		return school;
	}
	public void setSchool(SchoolRecord school) {
		this.school = school;
	}	
	public Person getMarker() {
		return marker;
	}
	public void setMarker(Person marker) {
		this.marker = marker;
	}
	public Person getUser() {
		return user;
	}
	public void setUser(Person user) {
		this.user = user;
	}
	public String getStudyUnit() {
		return studyUnit;
	}
	public void setStudyUnit(String studyUnit) {
		this.studyUnit = studyUnit;
	}
	public AssignmentRecord getAssignment() {
		return assignment;
	}
	public void setAssignment(AssignmentRecord assignment) {
		this.assignment = assignment;
	}
	public List getListSemester() {
		return listSemester;
	}
	public void setListSemester(List listSemester) {
		this.listSemester = listSemester;
	}
	public List getListAssignmentType() {
		return listAssignmentType;
	}
	public void setListAssignmentType(List listAssignmentType) {
		this.listAssignmentType = listAssignmentType;
	}
	public List getListModeOfSubmission() {
		return listModeOfSubmission;
	}
	public void setListModeOfSubmission(List listModeOfSubmission) {
		this.listModeOfSubmission = listModeOfSubmission;
	}
	public List getListModeOfMarking() {
		return listModeOfMarking;
	}
	public void setListModeOfMarking(List listModeOfMarking) {
		this.listModeOfMarking = listModeOfMarking;
	}
	public List getListCollege() {
		return listCollege;
	}
	public void setListCollege(List listCollege) {
		this.listCollege = listCollege;
	}
	public List getListSchool() {
		return listSchool;
	}
	public void setListSchool(List listSchool) {
		this.listSchool = listSchool;
	}
	public List getListBreakdownLevel() {
		return listBreakdownLevel;
	}
	public void setListBreakdownLevel(List listBreakdownLevel) {
		this.listBreakdownLevel = listBreakdownLevel;
	}
	
}
