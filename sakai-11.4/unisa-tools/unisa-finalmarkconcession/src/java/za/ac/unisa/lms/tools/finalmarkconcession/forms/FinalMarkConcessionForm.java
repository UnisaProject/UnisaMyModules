package za.ac.unisa.lms.tools.finalmarkconcession.forms;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import za.ac.unisa.lms.domain.general.Department;
import za.ac.unisa.lms.domain.general.Person;
import za.ac.unisa.lms.dao.Gencod;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorActionForm;

public class FinalMarkConcessionForm extends ValidatorActionForm {
	
	private static final long serialVersionUID = 1L;	
	private String novellUserid;
	private String examYear;
	private Short examPeriod;
	private List fiStudents;
	private FinalMarkStudentRecord recordSelected;
	private Integer studentNumberSelected;
	private String studyUnitSelected;
	private String statusSelected;
	private String studyUnitSearchCriteria;
	private String pageDownFlag;
	private String pageUpFlag;	
	private CollegeRecord userCollege;
	private DepartmentRecord userDepartment;
	private Department authDepartment;
	private Short selectedDepartment;
	private List examPeriodCodes;
	private List listAlternativeAssess;
	private List listAcademicSupport;
	private List listStatus;
	private List listDepartments;
	private List listAuthDepartments;
	private List listDeans;
	private List listDirectors;
	private List<String> listFiSites;
	private List<GradeBookObjectRecord> listFiAssignments;
	private List<Gencod> listZeroMarkReason;
	private List<Person> listLecturer;
	private List<Gencod> listAction;
	private String selectedAction;
	private String displayAction;
	private String searchCriteria;
	private String currentPage;
	private Person myUnisaUser;
	private String responseEmailAddress;
	private String selectedAuthoriser;
	private FinalMarkStudentRecord firstRecord;
	private FinalMarkStudentRecord lastRecord;
	private AlternativeExamOpportunityRecord alternativeExamOpportunity;	
	private AlternativeExamOpportunityRecord batchAlternativeAssessmentOpportunity;	
	private List authRequestList;	
	private String selectedRequestIndex;
	private String authorisationType;
	private String authComment;
	private AuthRequestRecord authrecordSelected;
	private String changeStatus;
	private String alternativeAssessListDesc;
	private String academicSupportListDesc;
	private String[] indexNrSelectedAuth;
	private String authListCriteriaExamYear;
	private Short authListCriteriaExamPeriod; 
	private Short authListCriteriaDepartment;	
	private String downLoadFile;
	private String zeroMarkReason;
	private boolean dataSaved=false;
	private String listAccess;	
	private boolean removeAssessOptOther;
	private String examPeriodDesc;
	private int totalRecords;
	private int totalStatusRecords;
	private int selectedGBObject;
	private List<BatchUploadRecord> listBatchVerifiedRecords;
	private List<BatchCodAuthRecord> listBatchCodAuthRecords;
	
	public List<BatchCodAuthRecord> getListBatchCodAuthRecords() {
		return listBatchCodAuthRecords;
	}
	public void setListBatchCodAuthRecords(
			List<BatchCodAuthRecord> listBatchCodAuthRecords) {
		this.listBatchCodAuthRecords = listBatchCodAuthRecords;
	}
	public List<BatchUploadRecord> getListBatchVerifiedRecords() {
		return listBatchVerifiedRecords;
	}
	public void setListBatchVerifiedRecords(
			List<BatchUploadRecord> listBatchVerifiedRecords) {
		this.listBatchVerifiedRecords = listBatchVerifiedRecords;
	}
	public int getSelectedGBObject() {
		return selectedGBObject;
	}
	public void setSelectedGBObject(int selectedGBObject) {
		this.selectedGBObject = selectedGBObject;
	}
	public List<GradeBookObjectRecord> getListFiAssignments() {
		return listFiAssignments;
	}
	public void setListFiAssignments(List<GradeBookObjectRecord> listFiAssignments) {
		this.listFiAssignments = listFiAssignments;
	}
	public List<String> getListFiSites() {
		return listFiSites;
	}
	public void setListFiSites(List<String> listFiSites) {
		this.listFiSites = listFiSites;
	}
	public int getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}
	public int getTotalStatusRecords() {
		return totalStatusRecords;
	}
	public void setTotalStatusRecords(int totalStatusRecords) {
		this.totalStatusRecords = totalStatusRecords;
	}
	public String getExamPeriodDesc() {
		return examPeriodDesc;
	}
	public void setExamPeriodDesc(String examPeriodDesc) {
		this.examPeriodDesc = examPeriodDesc;
	}
	public AlternativeExamOpportunityRecord getBatchAlternativeAssessmentOpportunity() {
		return batchAlternativeAssessmentOpportunity;
	}
	public void setBatchAlternativeAssessmentOpportunity(
			AlternativeExamOpportunityRecord batchAlternativeAssessmentOpportunity) {
		this.batchAlternativeAssessmentOpportunity = batchAlternativeAssessmentOpportunity;
	}
	public boolean isRemoveAssessOptOther() {
		return removeAssessOptOther;
	}
	public void setRemoveAssessOptOther(boolean removeAssessOptOther) {
		this.removeAssessOptOther = removeAssessOptOther;
	}
	public List<Person> getListLecturer() {
		return listLecturer;
	}
	public void setListLecturer(List<Person> listLecturer) {
		this.listLecturer = listLecturer;
	}
	public String getListAccess() {
		return listAccess;
	}
	public void setListAccess(String listAccess) {
		this.listAccess = listAccess;
	}
	public String getSelectedAction() {
		return selectedAction;
	}
	public void setSelectedAction(String selectedAction) {
		this.selectedAction = selectedAction;
	}
	public List<Gencod> getListAction() {
		return listAction;
	}
	public void setListAction(List<Gencod> listAction) {
		this.listAction = listAction;
	}
	public boolean isDataSaved(){
		 return dataSaved;
	 }
	 public void setDataSaved(boolean dataSaved){
		            this.dataSaved=dataSaved;
	 }
	public String getZeroMarkReason(){
		            return  zeroMarkReason;
	}
	public void setZeroMarkReason(String zeroMarkReason){
		          this.zeroMarkReason=zeroMarkReason;
	}
	public String getDownLoadFile() {
		return downLoadFile;
	}
	public void setDownLoadFile(String downLoadFile) {
		this.downLoadFile = downLoadFile;
	}
	public List getListDirectors() {
		return listDirectors;
	}
	public void setListDirectors(List listDirectors) {
		this.listDirectors = listDirectors;
	}
	public List getListDeans() {
		return listDeans;
	}
	public void setListDeans(List listDeans) {
		this.listDeans = listDeans;
	}
	public List getListAuthDepartments() {
		return listAuthDepartments;
	}
	public void setListAuthDepartments(List listAuthDepartments) {
		this.listAuthDepartments = listAuthDepartments;
	}
	public List getListDepartments() {
		return listDepartments;
	}
	public void setListDepartments(List listDepartments) {
		this.listDepartments = listDepartments;
	}
	public String getAuthListCriteriaExamYear() {
		return authListCriteriaExamYear;
	}
	public void setAuthListCriteriaExamYear(String authListCriteriaExamYear) {
		this.authListCriteriaExamYear = authListCriteriaExamYear;
	}
	public Short getAuthListCriteriaExamPeriod() {
		return authListCriteriaExamPeriod;
	}
	public void setAuthListCriteriaExamPeriod(Short authListCriteriaExamPeriod) {
		this.authListCriteriaExamPeriod = authListCriteriaExamPeriod;
	}
	public Short getAuthListCriteriaDepartment() {
		return authListCriteriaDepartment;
	}
	public void setAuthListCriteriaDepartment(Short authListCriteriaDepartment) {
		this.authListCriteriaDepartment = authListCriteriaDepartment;
	}
	public String[] getIndexNrSelectedAuth() {
		return indexNrSelectedAuth;
	}
	public void setIndexNrSelectedAuth(String[] indexNrSelectedAuth) {
		this.indexNrSelectedAuth = indexNrSelectedAuth;
	}
	public String getAlternativeAssessListDesc() {
		return alternativeAssessListDesc;
	}
	public void setAlternativeAssessListDesc(String alternativeAssessListDesc) {
		this.alternativeAssessListDesc = alternativeAssessListDesc;
	}
	public String getAcademicSupportListDesc() {
		return academicSupportListDesc;
	}
	public void setAcademicSupportListDesc(String academicSupportListDesc) {
		this.academicSupportListDesc = academicSupportListDesc;
	}
	public String getChangeStatus() {
		return changeStatus;
	}
	public void setChangeStatus(String changeStatus) {
		this.changeStatus = changeStatus;
	}
	public AuthRequestRecord getAuthrecordSelected() {
		return authrecordSelected;
	}
	public void setAuthrecordSelected(AuthRequestRecord authrecordSelected) {
		this.authrecordSelected = authrecordSelected;
	}
	public String getAuthComment() {
		return authComment;
	}
	public void setAuthComment(String authComment) {
		this.authComment = authComment;
	}
	public String getAuthorisationType() {
		return authorisationType;
	}
	public void setAuthorisationType(String authorisationType) {
		this.authorisationType = authorisationType;
	}
	public AlternativeExamOpportunityRecord getAlternativeExamOpportunity() {
		return alternativeExamOpportunity;
	}
	public void setAlternativeExamOpportunity(
			AlternativeExamOpportunityRecord alternativeExamOpportunity) {
		this.alternativeExamOpportunity = alternativeExamOpportunity;
	}	
	public FinalMarkStudentRecord getFirstRecord() {
		return firstRecord;
	}
	public void setFirstRecord(FinalMarkStudentRecord firstRecord) {
		this.firstRecord = firstRecord;
	}
	public FinalMarkStudentRecord getLastRecord() {
		return lastRecord;
	}
	public void setLastRecord(FinalMarkStudentRecord lastRecord) {
		this.lastRecord = lastRecord;
	}
	public String getDisplayAction() {
		return displayAction;
	}
	public void setDisplayAction(String displayAction) {
		this.displayAction = displayAction;
	}
	public String getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}
	public List getExamPeriodCodes() {
		return examPeriodCodes;
	}
	public void setExamPeriodCodes(List examPeriodCodes) {
		this.examPeriodCodes = examPeriodCodes;
	}
	public String getPageDownFlag() {
		return pageDownFlag;
	}
	public void setPageDownFlag(String pageDownFlag) {
		this.pageDownFlag = pageDownFlag;
	}
	public String getPageUpFlag() {
		return pageUpFlag;
	}
	public void setPageUpFlag(String pageUpFlag) {
		this.pageUpFlag = pageUpFlag;
	}
	public String getNovellUserid() {
		return novellUserid;
	}
	public void setNovellUserid(String novellUserid) {
		this.novellUserid = novellUserid;
	}
	public String getExamYear() {
		return examYear;
	}
	public void setExamYear(String examYear) {
		this.examYear = examYear;
	}
	public Short getExamPeriod() {
		return examPeriod;
	}
	public void setExamPeriod(Short examPeriod) {
		this.examPeriod = examPeriod;
	}
	public List getFiStudents() {
		return fiStudents;
	}
	public void setFiStudents(List fiStudents) {
		this.fiStudents = fiStudents;
	}
	public FinalMarkStudentRecord getRecordSelected() {
		return recordSelected;
	}
	public void setRecordSelected(FinalMarkStudentRecord recordSelected) {
		this.recordSelected = recordSelected;
	}
	public Integer getStudentNumberSelected() {
		return studentNumberSelected;
	}
	public void setStudentNumberSelected(Integer studentNumberSelected) {
		this.studentNumberSelected = studentNumberSelected;
	}
	public DepartmentRecord getUserDepartment() {
		return userDepartment;
	}
	public void setUserDepartment(DepartmentRecord userDepartment) {
		this.userDepartment = userDepartment;
	}
	public CollegeRecord getUserCollege() {
		return userCollege;
	}
	public void setUserCollege(CollegeRecord userCollege) {
		this.userCollege = userCollege;
	}
	public Short getSelectedDepartment() {
		return selectedDepartment;
	}
	public void setSelectedDepartment(Short selectedDepartment) {
		this.selectedDepartment = selectedDepartment;
	}
	public List getListAlternativeAssess() {
		return listAlternativeAssess;
	}
	public void setListAlternativeAssess(List listAlternativeAssess) {
		this.listAlternativeAssess = listAlternativeAssess;
	}
	public List getListAcademicSupport() {
		return listAcademicSupport;
	}
	public void setListAcademicSupport(List listAcademicSupport) {
		this.listAcademicSupport = listAcademicSupport;
	}
	public String getStudyUnitSelected() {
		return studyUnitSelected.toUpperCase();
	}
	public void setStudyUnitSelected(String studyUnitSelected) {
		this.studyUnitSelected = studyUnitSelected.toUpperCase();
	}
	public List getListStatus() {
		return listStatus;
	}
	public void setListStatus(List listStatus) {
		this.listStatus = listStatus;
	}
	public List<Gencod> getListZeroMarkReason() {
		return listZeroMarkReason;
	}
	public void setListZeroMarkReason(List<Gencod> listZeroMarkReason) {
		this.listZeroMarkReason = listZeroMarkReason;
	}
	public String getStatusSelected() {
		return statusSelected;
	}
	public void setStatusSelected(String statusSelected) {
		this.statusSelected = statusSelected;
	}
	
	public String getSearchCriteria() {
		return searchCriteria;
	}
	public void setSearchCriteria(String searchCriteria) {
		this.searchCriteria = searchCriteria;
	}
	public Department getAuthDepartment() {
		return authDepartment;
	}
	public void setAuthDepartment(Department authDepartment) {
		this.authDepartment = authDepartment;
	}
	public Person getMyUnisaUser() {
		return myUnisaUser;
	}
	public void setMyUnisaUser(Person myUnisaUser) {
		this.myUnisaUser = myUnisaUser;
	}
	public String getResponseEmailAddress() {
		return responseEmailAddress;
	}
	public void setResponseEmailAddress(String responseEmailAddress) {
		this.responseEmailAddress = responseEmailAddress;
	}
	public String getSelectedAuthoriser() {
		return selectedAuthoriser;
	}
	public void setSelectedAuthoriser(String selectedAuthoriser) {
		this.selectedAuthoriser = selectedAuthoriser;
	}
	public String getStudyUnitSearchCriteria() {
		return studyUnitSearchCriteria.toUpperCase();
	}
	public void setStudyUnitSearchCriteria(String studyUnitSearchCriteria) {
		this.studyUnitSearchCriteria = studyUnitSearchCriteria.toUpperCase();
	}
	public List getAuthRequestList() {
		return authRequestList;
	}
	public void setAuthRequestList(List authRequestList) {
		this.authRequestList = authRequestList;
	}
	public String getSelectedRequestIndex() {
		return selectedRequestIndex;
	}
	public void setSelectedRequestIndex(String selectedRequestIndex) {
		this.selectedRequestIndex = selectedRequestIndex;
	}
	public void reset (ActionMapping mapping, HttpServletRequest request) {
		//If you deselect all items in a multibox and submit the form
		//nothing happens: the previous selected items are still selected.
		//The Form is therefore not updated. To correct this the control arrays 
		//are set to empty arrays.
		
		if (this.currentPage != null && (this.currentPage.equalsIgnoreCase("authRequestList"))){
				this.indexNrSelectedAuth = new String[0];
				
		}	
		
	}
	
}
