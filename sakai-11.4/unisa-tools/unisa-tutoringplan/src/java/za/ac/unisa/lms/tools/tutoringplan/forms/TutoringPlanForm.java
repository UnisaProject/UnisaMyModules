package za.ac.unisa.lms.tools.tutoringplan.forms;

import java.util.List;

import org.apache.struts.validator.ValidatorActionForm;

import za.ac.unisa.lms.domain.general.Person;
import za.ac.unisa.lms.domain.general.StudyUnit;

public class TutoringPlanForm  extends ValidatorActionForm {
	
	private static final long serialVersionUID = 1L;
	private String acadYear;
	private String semester;
	private String semesterType;
	private String studentNr;
	private StudyUnit studyUnit;
	private String inputStudyUnit;
	private String userId;	
	private Person user;
	private List listSemester;
	private List listTutoringMode;
	private List listGroupingMethod;
	private List listAllocCriteria;
	private List listContactDetail;
	private List listYesNo;
	private List<DepartmentRecord> listDepartment;	
	private List<SchoolRecord> listSchool;
	private CollegeRecord userCollege;
	private TutoringPlan tutoringPlan;
	private String currentPage;
	private boolean updateAllowed;
	private String selectTutoringMode;
	private TutoringMode tutoringMode;
	private String[] indexSelectedRemoveTutorMode;
	private String editMode;	
	private String searchCriteria;
	private Short selectedDepartment;
	private Short selectedSchool;
	
	public Short getSelectedDepartment() {
		return selectedDepartment;
	}
	public void setSelectedDepartment(Short selectedDepartment) {
		this.selectedDepartment = selectedDepartment;
	}
	public Short getSelectedSchool() {
		return selectedSchool;
	}
	public void setSelectedSchool(Short selectedSchool) {
		this.selectedSchool = selectedSchool;
	}
	public String getSearchCriteria() {
		return searchCriteria;
	}
	public void setSearchCriteria(String searchCriteria) {
		this.searchCriteria = searchCriteria;
	}
	public List<DepartmentRecord> getListDepartment() {
		return listDepartment;
	}
	public void setListDepartment(List<DepartmentRecord> listDepartment) {
		this.listDepartment = listDepartment;
	}
	public List<SchoolRecord> getListSchool() {
		return listSchool;
	}
	public void setListSchool(List<SchoolRecord> listSchool) {
		this.listSchool = listSchool;
	}	
	public CollegeRecord getUserCollege() {
		return userCollege;
	}
	public void setUserCollege(CollegeRecord userCollege) {
		this.userCollege = userCollege;
	}
	public String getEditMode() {
		return editMode;
	}
	public void setEditMode(String editMode) {
		this.editMode = editMode;
	}
	public String[] getIndexSelectedRemoveTutorMode() {
		return indexSelectedRemoveTutorMode;
	}
	public void setIndexSelectedRemoveTutorMode(
			String[] indexSelectedRemoveTutorMode) {
		this.indexSelectedRemoveTutorMode = indexSelectedRemoveTutorMode;
	}
	public List getListYesNo() {
		return listYesNo;
	}
	public void setListYesNo(List listYesNo) {
		this.listYesNo = listYesNo;
	}
	public TutoringMode getTutoringMode() {
		return tutoringMode;
	}
	public void setTutoringMode(TutoringMode tutoringMode) {
		this.tutoringMode = tutoringMode;
	}
	public String getSelectTutoringMode() {
		return selectTutoringMode;
	}
	public void setSelectTutoringMode(String selectTutoringMode) {
		this.selectTutoringMode = selectTutoringMode;
	}
	public boolean isUpdateAllowed() {
		return updateAllowed;
	}
	public void setUpdateAllowed(boolean updateAllowed) {
		this.updateAllowed = updateAllowed;
	}
	public List getListTutoringMode() {
		return listTutoringMode;
	}
	public void setListTutoringMode(List listTutoringMode) {
		this.listTutoringMode = listTutoringMode;
	}
	public List getListGroupingMethod() {
		return listGroupingMethod;
	}
	public void setListGroupingMethod(List listGroupingMethod) {
		this.listGroupingMethod = listGroupingMethod;
	}
	public List getListAllocCriteria() {
		return listAllocCriteria;
	}
	public void setListAllocCriteria(List listAllocCriteria) {
		this.listAllocCriteria = listAllocCriteria;
	}
	public List getListContactDetail() {
		return listContactDetail;
	}
	public void setListContactDetail(List listContactDetail) {
		this.listContactDetail = listContactDetail;
	}
	public TutoringPlan getTutoringPlan() {
		return tutoringPlan;
	}
	public void setTutoringPlan(TutoringPlan tutoringPlan) {
		this.tutoringPlan = tutoringPlan;
	}
	public String getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;	}
	
	public String getAcadYear() {
		return acadYear;
	}
	public void setAcadYear(String acadYear) {
		this.acadYear = acadYear;
	}
	public String getSemester() {
		return semester;
	}
	public void setSemester(String semester) {
		this.semester = semester;
	}
	public String getSemesterType() {
		return semesterType;
	}
	public void setSemesterType(String semesterType) {
		this.semesterType = semesterType;
	}
	public String getStudentNr() {
		return studentNr;
	}
	public void setStudentNr(String studentNr) {
		this.studentNr = studentNr;
	}
	public StudyUnit getStudyUnit() {
		return studyUnit;
	}
	public void setStudyUnit(StudyUnit studyUnit) {
		this.studyUnit = studyUnit;
	}
	public String getInputStudyUnit() {
		return inputStudyUnit;
	}
	public void setInputStudyUnit(String inputStudyUnit) {
		this.inputStudyUnit = inputStudyUnit;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Person getUser() {
		return user;
	}
	public void setUser(Person user) {
		this.user = user;
	}
	public List getListSemester() {
		return listSemester;
	}
	public void setListSemester(List listSemester) {
		this.listSemester = listSemester;
	}
}
