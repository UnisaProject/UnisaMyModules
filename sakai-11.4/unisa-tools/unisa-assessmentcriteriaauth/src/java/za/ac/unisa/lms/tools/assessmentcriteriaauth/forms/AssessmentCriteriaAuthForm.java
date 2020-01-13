package za.ac.unisa.lms.tools.assessmentcriteriaauth.forms;

import java.util.List;

import org.apache.struts.util.LabelValueBean;
import org.apache.struts.validator.ValidatorForm;

import za.ac.unisa.lms.domain.assessmentCriteria.Assignment;
import za.ac.unisa.lms.domain.assessmentCriteria.FinalMarkComposition;
import za.ac.unisa.lms.tools.assessmentcriteriaauth.DAO.DepartmentRecord;
import za.ac.unisa.utils.CoursePeriod;

public class AssessmentCriteriaAuthForm extends ValidatorForm {
	
	private static final long serialVersionUID = 1L;
	private String currentPage;
	private String novellUserId;
	private String academicYear;
	private String semester;
	private String comment;
	private String assFormatCode;
	private String assTypeCode;	
	private String admissionMethod;
	private String selectedCourseIndex;
	private Short selectedAuthDepartment;
	private Short selectedStatusDepartment;
	private String selectAssignment;
	private CoursePeriod selectedCourse;
	private List listSemester;
	private List <DepartmentRecord> listAuthDepartment;
	private List <DepartmentRecord> listStatusDepartment;
	private List listAssignment;
	private List requestList;
	private List listGCStatus;
	private FinalMarkComposition finalMarkComp;
	private Assignment assignment;	
	private StudyUnit studyUnit;	
	private String lastAssDueControlDate;
	private String lastPortfolioDueControlDate;
	
	public Short getSelectedAuthDepartment() {
		return selectedAuthDepartment;
	}
	public void setSelectedAuthDepartment(Short selectedAuthDepartment) {
		this.selectedAuthDepartment = selectedAuthDepartment;
	}
	public Short getSelectedStatusDepartment() {
		return selectedStatusDepartment;
	}
	public void setSelectedStatusDepartment(Short selectedStatusDepartment) {
		this.selectedStatusDepartment = selectedStatusDepartment;
	}
	public List<DepartmentRecord> getListAuthDepartment() {
		return listAuthDepartment;
	}
	public void setListAuthDepartment(List<DepartmentRecord> listAuthDepartment) {
		this.listAuthDepartment = listAuthDepartment;
	}
	public List<DepartmentRecord> getListStatusDepartment() {
		return listStatusDepartment;
	}
	public void setListStatusDepartment(List<DepartmentRecord> listStatusDepartment) {
		this.listStatusDepartment = listStatusDepartment;
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
	public String getAssFormatCode() {
		return assFormatCode;
	}
	public void setAssFormatCode(String assFormatCode) {
		this.assFormatCode = assFormatCode;
	}	
	public String getAssTypeCode() {
		return assTypeCode;
	}
	public void setAssTypeCode(String assTypeCode) {
		this.assTypeCode = assTypeCode;
	}
	public String getAdmissionMethod() {
		return admissionMethod;
	}
	public void setAdmissionMethod(String admissionMethod) {
		this.admissionMethod = admissionMethod;
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
	public List getListSemester() {
		return listSemester;
	}
	public void setListSemester(List listSemester) {
		this.listSemester = listSemester;
	}
	public List getRequestList() {
		return requestList;
	}
	public void setRequestList(List requestList) {
		this.requestList = requestList;
	}
	public String getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}
	public String getNovellUserId() {
		return novellUserId;
	}
	public void setNovellUserId(String novellUserId) {
		this.novellUserId = novellUserId;
	}
	public FinalMarkComposition getFinalMarkComp() {
		return finalMarkComp;
	}
	public void setFinalMarkComp(FinalMarkComposition finalMarkComp) {
		this.finalMarkComp = finalMarkComp;
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
	public List getListGCStatus() {
		return listGCStatus;
	}
	public void setListGCStatus(List listGCStatus) {
		this.listGCStatus = listGCStatus;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getSelectedCourseIndex() {
		return selectedCourseIndex;
	}
	public void setSelectedCourseIndex(String selectedCourseIndex) {
		this.selectedCourseIndex = selectedCourseIndex;
	}
	public CoursePeriod getSelectedCourse() {
		return selectedCourse;
	}
	public void setSelectedCourse(CoursePeriod selectedCourse) {
		this.selectedCourse = selectedCourse;
	}
	public String getSelectAssignment() {
		return selectAssignment;
	}
	public void setSelectAssignment(String selectAssignment) {
		this.selectAssignment = selectAssignment;
	}	
		
}
