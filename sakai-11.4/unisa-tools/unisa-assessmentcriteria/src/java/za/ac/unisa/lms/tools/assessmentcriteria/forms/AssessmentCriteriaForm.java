package za.ac.unisa.lms.tools.assessmentcriteria.forms;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import za.ac.unisa.lms.tools.assessmentcriteria.DAO.*;
import za.ac.unisa.lms.domain.general.Department;
import za.ac.unisa.lms.domain.assessmentCriteria.*;
import za.ac.unisa.lms.dao.Gencod;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorActionForm;

public class AssessmentCriteriaForm extends ValidatorActionForm {
	
	private static final long serialVersionUID = 1L;
	private String currentPage;
	private String novellUserId;
	private Integer dummyAcademicYear;
	private String academicYear;
	private String semester;
	private String semesterType;
	private String siteId;
	private String status;
	private String statusDesc;
	private String negativeMarkingYesNoFlag;
	private String yearMarkContributionYesNoFlag;
	private String selfAssesmentYesNoFlag;
	private Examination dummyFirstExamination;
	private Examination firstExamination;
	private FinalMarkComposition finalMarkComp;
	private Assignment assignment;
	private StudyUnit studyUnit;
	private Department department;
	private String selectedAuthoriser;
	private String selectAssignment;
	private String assignmentAction;
	private List listDisplayAnswer;
	private List listAssignment;
	private List<Gencod> listSemester;
	private List<Gencod> listAssignmentGroup;	
	private List<Gencod> listAssignmentFormat;
	private List<Gencod> listAssignmentType;
	private List<Gencod> listAssignmentOptionality;
	private List<Gencod> listPossibleMCQAnswers;
	private List<Gencod> listNegativeMarking;
	private List<Gencod> listYesNo;
	private List<Gencod> listSelfAssessment;
	private List<Gencod> listFileFormat;
	private List<Gencod> listLanguage;
	private List listOtherLanguage;
	private List listOtherFileFormat;
	private List<Gencod> listExamAdmissionMethod; 
	private String[] indexSelectedRemoveAssignment;
	private String[] indexNrSelectedFormat;
	private String[] indexNrSelectedLanguage;
	private String monthSelected;
	private String yearSelected;
	private String daySelected;
	private String responseEmailAddress;
	private String firstAssDueControlDate;
	private String secondAssDueControlDate;
	private String lastAssDueControlDate;
	private String lastPortfolioDueControlDate;
	private String examAdmissionDate;
	private boolean enableDelete;
	private boolean sunpdtExists;
	private boolean survey;
	private String examBase;	
	private String onlineMethod;
	private String formativeAssessInd;
	private String summativeAssessInd;
	private String fromSystem;
	private boolean resetOnscreenMarkingArrays;	
	private String submissionDateIndicator;
	
	
	public String getExamBase() {
		return examBase;
	}
	public void setExamBase(String examBase) {
		this.examBase = examBase;
	}
	public String getSubmissionDateIndicator() {
		return submissionDateIndicator;
	}
	public void setSubmissionDateIndicator(String submissionDateIndicator) {
		this.submissionDateIndicator = submissionDateIndicator;
	}
	public String getExamAdmissionDate() {
		return examAdmissionDate;
	}
	public void setExamAdmissionDate(String examAdmissionDate) {
		this.examAdmissionDate = examAdmissionDate;
	}	
	public List<Gencod> getListAssignmentGroup() {
		return listAssignmentGroup;
	}
	public void setListAssignmentGroup(List<Gencod> listAssignmentGroup) {
		this.listAssignmentGroup = listAssignmentGroup;
	}
	public List<Gencod> getListExamAdmissionMethod() {
		return listExamAdmissionMethod;
	}
	public void setListExamAdmissionMethod(List<Gencod> listExamAdmissionMethod) {
		this.listExamAdmissionMethod = listExamAdmissionMethod;
	}
	public boolean isResetOnscreenMarkingArrays() {
		return resetOnscreenMarkingArrays;
	}
	public void setResetOnscreenMarkingArrays(boolean resetOnscreenMarkingArrays) {
		this.resetOnscreenMarkingArrays = resetOnscreenMarkingArrays;
	}
	public List getListOtherFileFormat() {
		return listOtherFileFormat;
	}
	public void setListOtherFileFormat(List listOtherFileFormat) {
		this.listOtherFileFormat = listOtherFileFormat;
	}
	public List getListOtherLanguage() {
		return listOtherLanguage;
	}
	public void setListOtherLanguage(List listOtherLanguage) {
		this.listOtherLanguage = listOtherLanguage;
	}
	public String[] getIndexNrSelectedLanguage() {
		return indexNrSelectedLanguage;
	}
	public void setIndexNrSelectedLanguage(String[] indexNrSelectedLanguage) {
		this.indexNrSelectedLanguage = indexNrSelectedLanguage;
	}
	public String[] getIndexNrSelectedFormat() {
		return indexNrSelectedFormat;
	}
	public void setIndexNrSelectedFormat(String[] indexNrSelectedFormat) {
		this.indexNrSelectedFormat = indexNrSelectedFormat;
	}
	public List getListFileFormat() {
		return listFileFormat;
	}
	public void setListFileFormat(List listFileFormat) {
		this.listFileFormat = listFileFormat;
	}
	public List getListLanguage() {
		return listLanguage;
	}
	public void setListLanguage(List listLanguage) {
		this.listLanguage = listLanguage;
	}
	public String getFromSystem() {
		return fromSystem;
	}
	public void setFromSystem(String fromSystem) {
		this.fromSystem = fromSystem;
	}
	public String getFormativeAssessInd() {
		return formativeAssessInd;
	}
	public void setFormativeAssessInd(String formativeAssessInd) {
		this.formativeAssessInd = formativeAssessInd;
	}
	public String getSummativeAssessInd() {
		return summativeAssessInd;
	}
	public void setSummativeAssessInd(String summativeAssessInd) {
		this.summativeAssessInd = summativeAssessInd;
	}
	public String getOnlineMethod() {
		return onlineMethod;
	}
	public void setOnlineMethod(String onlineMethod) {
		this.onlineMethod = onlineMethod;
	}
	public boolean isSurvey() {
		return survey;
	}
	public void setSurvey(boolean survey) {
		this.survey = survey;
	}
	public boolean isEnableDelete() {
		return enableDelete;
	}
	public void setEnableDelete(boolean enableDelete) {
		this.enableDelete = enableDelete;
	}
	public String getResponseEmailAddress() {
		return responseEmailAddress;
	}
	public void setResponseEmailAddress(String responseEmailAddress) {
		this.responseEmailAddress = responseEmailAddress;
	}
	public String getYearSelected() {
		return yearSelected;
	}
	public void setYearSelected(String yearSelected) {
		this.yearSelected = yearSelected;
	}
	public String getDaySelected() {
		return daySelected;
	}
	public void setDaySelected(String daySelected) {
		this.daySelected = daySelected;
	}
	public String getMonthSelected() {
		return monthSelected;
	}
	public void setMonthSelected(String monthSelected) {
		this.monthSelected = monthSelected;
	}
	public StudyUnit getStudyUnit() {
		return studyUnit;
	}
	public void setStudyUnit(StudyUnit studyUnit) {
		this.studyUnit = studyUnit;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public String getSemester() {
		return semester;
	}
	public void setSemester(String semester) {
		this.semester = semester;
	}	
	public String getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}	
	public List getListSemester() {
		return listSemester;
	}
	public void setListSemester(List listSemester) {
		this.listSemester = listSemester;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Assignment getAssignment() {
		return assignment;
	}
	public void setAssignment(Assignment assignment) {
		this.assignment = assignment;
	}
	public List getListAssignment() {
		return listAssignment;
	}
	public void setListAssignment(List listAssignment) {
		this.listAssignment = listAssignment;
	}
	public List getListAssignmentFormat() {
		return listAssignmentFormat;
	}
	public void setListAssignmentFormat(List listAssignmentFormat) {
		this.listAssignmentFormat = listAssignmentFormat;
	}
	public List getListAssignmentType() {
		return listAssignmentType;
	}
	public void setListAssignmentType(List listAssignmentType) {
		this.listAssignmentType = listAssignmentType;
	}
	public List getListAssignmentOptionality() {
		return listAssignmentOptionality;
	}
	public void setListAssignmentOptionality(List listAssignmentOptionality) {
		this.listAssignmentOptionality = listAssignmentOptionality;
	}
	public List getListPossibleMCQAnswers() {
		return listPossibleMCQAnswers;
	}
	public void setListPossibleMCQAnswers(List listPossibleMCQAnswers) {
		this.listPossibleMCQAnswers = listPossibleMCQAnswers;
	}
	public String getNovellUserId() {
		return novellUserId;
	}
	public void setNovellUserId(String novellUserId) {
		this.novellUserId = novellUserId;
	}
	public Examination getFirstExamination() {
		return firstExamination;
	}
	public void setFirstExamination(Examination firstExamination) {
		this.firstExamination = firstExamination;
	}
	public FinalMarkComposition getFinalMarkComp() {
		return finalMarkComp;
	}
	public void setFinalMarkComp(FinalMarkComposition finalMarkComp) {
		this.finalMarkComp = finalMarkComp;
	}
	public String getStatusDesc() {
		return statusDesc;
	}
	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}
	public Integer getDummyAcademicYear() {
		return dummyAcademicYear;
	}
	public void setDummyAcademicYear(Integer dummyAcademicYear) {
		this.dummyAcademicYear = dummyAcademicYear;
	}
	public Examination getDummyFirstExamination() {
		return dummyFirstExamination;
	}
	public void setDummyFirstExamination(Examination dummyFirstExamination) {
		this.dummyFirstExamination = dummyFirstExamination;
	}
	public String getSelectAssignment() {
		return selectAssignment;
	}
	public void setSelectAssignment(String selectAssignment) {
		this.selectAssignment = selectAssignment;
	}
	public String getAssignmentAction() {
		return assignmentAction;
	}
	public void setAssignmentAction(String assignmentAction) {
		this.assignmentAction = assignmentAction;
	}
	public String[] getIndexSelectedRemoveAssignment() {
		return indexSelectedRemoveAssignment;
	}
	public void setIndexSelectedRemoveAssignment(
			String[] indexSelectedRemoveAssignment) {
		this.indexSelectedRemoveAssignment = indexSelectedRemoveAssignment;
	}
	public void reset (ActionMapping mapping, HttpServletRequest request) {
		//If you deselect all items in a multibox and submit the form
		//nothing happens: the previous selected items are still selected.
		//The Form is therefore not updated. To correct this the control arrays 
		//are set to empty arrays.
		//This must be done after display of assessmentCriteria but before assessmentCriteria is submitted.
		if (this.currentPage != null && this.currentPage.equalsIgnoreCase("assessmentCriteria")){
				this.indexSelectedRemoveAssignment = new String[0];
		}
		if (this.isResetOnscreenMarkingArrays() &&
				!this.assignmentAction.equalsIgnoreCase("view")){
			this.indexNrSelectedLanguage = new String[0];	
			this.indexNrSelectedFormat = new String[0];
			this.setResetOnscreenMarkingArrays(false);
		}
		
		if (this.currentPage!= null && this.getAssignment()!=null && this.currentPage.equalsIgnoreCase("assignment") && !this.assignmentAction.equalsIgnoreCase("view")){
			this.getAssignment().setType("");
			this.getAssignment().setFormat("");
		}	
		
	}
	public List getListNegativeMarking() {
		return listNegativeMarking;
	}
	public void setListNegativeMarking(List listNegativeMarking) {
		this.listNegativeMarking = listNegativeMarking;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public String getSelectedAuthoriser() {
		return selectedAuthoriser;
	}
	public void setSelectedAuthoriser(String selectedAuthoriser) {
		this.selectedAuthoriser = selectedAuthoriser;
	}
	public String getSecondAssDueControlDate() {
		return secondAssDueControlDate;
	}
	public void setSecondAssDueControlDate(String secondAssDueControlDate) {
		this.secondAssDueControlDate = secondAssDueControlDate;
	}
	public String getFirstAssDueControlDate() {
		return firstAssDueControlDate;
	}
	public void setFirstAssDueControlDate(String firstAssDueControlDate) {
		this.firstAssDueControlDate = firstAssDueControlDate;
	}
	public String getSemesterType() {
		return semesterType;
	}
	public void setSemesterType(String semesterType) {
		this.semesterType = semesterType;
	}
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public List getListDisplayAnswer() {
		return listDisplayAnswer;
	}
	public void setListDisplayAnswer(List listDisplayAnswer) {
		this.listDisplayAnswer = listDisplayAnswer;
	}
	public String getNegativeMarkingYesNoFlag() {
		return negativeMarkingYesNoFlag;
	}
	public void setNegativeMarkingYesNoFlag(String negativeMarkingYesNoFlag) {
		this.negativeMarkingYesNoFlag = negativeMarkingYesNoFlag;
	}	
	public List getListYesNo() {
		return listYesNo;
	}
	public void setListYesNo(List listYesNo) {
		this.listYesNo = listYesNo;
	}
	public List getListSelfAssessment() {
		return listSelfAssessment;
	}
	public void setListSelfAssessment(List listSelfAssessment) {
		this.listSelfAssessment = listSelfAssessment;
	}
	public String getYearMarkContributionYesNoFlag() {
		return yearMarkContributionYesNoFlag;
	}
	public void setYearMarkContributionYesNoFlag(
			String yearMarkContributionYesNoFlag) {
		this.yearMarkContributionYesNoFlag = yearMarkContributionYesNoFlag;
	}
	public String getSelfAssesmentYesNoFlag() {
		return selfAssesmentYesNoFlag;
	}
	public void setSelfAssesmentYesNoFlag(String selfAssesmentYesNoFlag) {
		this.selfAssesmentYesNoFlag = selfAssesmentYesNoFlag;
	}
	public String getLastAssDueControlDate() {
		return lastAssDueControlDate;
	}
	public void setLastAssDueControlDate(String lastAssDueControlDate) {
		this.lastAssDueControlDate = lastAssDueControlDate;
	}
	public String getLastPortfolioDueControlDate() {
		return lastPortfolioDueControlDate;
	}
	public void setLastPortfolioDueControlDate(String lastPortfolioDueControlDate) {
		this.lastPortfolioDueControlDate = lastPortfolioDueControlDate;
	}
	public boolean isSunpdtExists() {
		return sunpdtExists;
	}
	public void setSunpdtExists(boolean sunpdtExists) {
		this.sunpdtExists = sunpdtExists;
	}
	
}
