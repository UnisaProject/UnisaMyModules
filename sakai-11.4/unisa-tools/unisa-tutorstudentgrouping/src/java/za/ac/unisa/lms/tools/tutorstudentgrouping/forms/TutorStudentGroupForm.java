package za.ac.unisa.lms.tools.tutorstudentgrouping.forms;

import java.util.List;

import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.ValidatorActionForm;

import za.ac.unisa.lms.domain.general.Person;
import za.ac.unisa.lms.domain.general.StudyUnit;

public class TutorStudentGroupForm extends ValidatorActionForm {
	
	private static final long serialVersionUID = 1L;
	private String acadYear;
	private String semester;
	private String semesterType;
	private String studentNr;
	private StudyUnit studyUnit;
	private String rcCode;
	private String inputStudyUnit;
	private String userId;
	private String selectedAction;
	private String selectedTutoringMode;
	private Student student;
	private TutoringMode tutoringMode;
	private TutorStudentGroup StudentGroup;
	private Tutor tutor;
	private Person user;
	private String selectedTutor;
	private String tutorNetworkCode;
	private String tutorStaffNumber;
	private List listSemester;
	private List listAction;
	private List listTutoringMode;
	private List listTutor;
	private List listAvailableTutorMode;
	private List<TutorStudentGroup> listAvailableTutorGroup;
	private List<ChangeGroupTutor> listChangeGroupTutor;
	private List<TutorStudentGroup> listTutorStudentGroup;
	private String currentPage; 
	private int selectedGroup;
	private String indexNrselectedTutorGroupSwitch1;
	private String indexNrselectedTutorGroupSwitch2;
	private String[]indexNrSelectedGroups;
	private SwitchGroupTutor switchGroup;
	
	//** File */
	private String fileContentType;
	private String fileName = "";
	private String fileType = "";
	private String fileSize = "";
	private int validRecords = 0;
	private int invalidRecords = 0;
	private String errorFileName="";	
	private String[] studentArray;
	private FormFile theFile;	
	
	
	public String getRcCode() {
		return rcCode;
	}
	public void setRcCode(String rcCode) {
		this.rcCode = rcCode;
	}
	public SwitchGroupTutor getSwitchGroup() {
		return switchGroup;
	}
	public void setSwitchGroup(SwitchGroupTutor switchGroup) {
		this.switchGroup = switchGroup;
	}
	public void setListAvailableTutorGroup(
			List<TutorStudentGroup> listAvailableTutorGroup) {
		this.listAvailableTutorGroup = listAvailableTutorGroup;
	}
	public String getIndexNrselectedTutorGroupSwitch1() {
		return indexNrselectedTutorGroupSwitch1;
	}
	public void setIndexNrselectedTutorGroupSwitch1(
			String indexNrselectedTutorGroupSwitch1) {
		this.indexNrselectedTutorGroupSwitch1 = indexNrselectedTutorGroupSwitch1;
	}
	public String getIndexNrselectedTutorGroupSwitch2() {
		return indexNrselectedTutorGroupSwitch2;
	}
	public void setIndexNrselectedTutorGroupSwitch2(
			String indexNrselectedTutorGroupSwitch2) {
		this.indexNrselectedTutorGroupSwitch2 = indexNrselectedTutorGroupSwitch2;
	}
	public List<ChangeGroupTutor> getListChangeGroupTutor() {
		return listChangeGroupTutor;
	}
	public void setListChangeGroupTutor(List<ChangeGroupTutor> listChangeGroupTutor) {
		this.listChangeGroupTutor = listChangeGroupTutor;
	}
	public String[] getIndexNrSelectedGroups() {
		return indexNrSelectedGroups;
	}
	public void setIndexNrSelectedGroups(String[] indexNrSelectedGroups) {
		this.indexNrSelectedGroups = indexNrSelectedGroups;
	}
	public List<TutorStudentGroup> getListTutorStudentGroup() {
		return listTutorStudentGroup;
	}
	public void setListTutorStudentGroup(
			List<TutorStudentGroup> listTutorStudentGroup) {
		this.listTutorStudentGroup = listTutorStudentGroup;
	}
	public String getTutorNetworkCode() {
		return tutorNetworkCode;
	}
	public void setTutorNetworkCode(String tutorNetworkCode) {
		this.tutorNetworkCode = tutorNetworkCode;
	}
	public String getTutorStaffNumber() {
		return tutorStaffNumber;
	}
	public void setTutorStaffNumber(String tutorStaffNumber) {
		this.tutorStaffNumber = tutorStaffNumber;
	}
	public int getSelectedGroup() {
		return selectedGroup;
	}
	public void setSelectedGroup(int selectedGroup) {
		this.selectedGroup = selectedGroup;
	}
	public List getListAvailableTutorGroup() {
		return listAvailableTutorGroup;
	}	
	public TutorStudentGroup getStudentGroup() {
		return StudentGroup;
	}
	public void setStudentGroup(TutorStudentGroup studentGroup) {
		StudentGroup = studentGroup;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public Tutor getTutor() {
		return tutor;
	}
	public void setTutor(Tutor tutor) {
		this.tutor = tutor;
	}
	public List getListAvailableTutorMode() {
		return listAvailableTutorMode;
	}
	public void setListAvailableTutorMode(List listAvailableTutorMode) {
		this.listAvailableTutorMode = listAvailableTutorMode;
	}
	public String getSelectedTutoringMode() {
		return selectedTutoringMode;
	}
	public void setSelectedTutoringMode(String selectedTutoringMode) {
		this.selectedTutoringMode = selectedTutoringMode;
	}
	public TutoringMode getTutoringMode() {
		return tutoringMode;
	}
	public void setTutoringMode(TutoringMode tutoringMode) {
		this.tutoringMode = tutoringMode;
	}
	public String getSelectedTutor() {
		return selectedTutor;
	}
	public void setSelectedTutor(String selectedTutor) {
		this.selectedTutor = selectedTutor;
	}
	public int getValidRecords() {
		return validRecords;
	}
	public void setValidRecords(int validRecords) {
		this.validRecords = validRecords;
	}
	public int getInvalidRecords() {
		return invalidRecords;
	}
	public void setInvalidRecords(int invalidRecords) {
		this.invalidRecords = invalidRecords;
	}
	public List getListTutor() {
		return listTutor;
	}
	public void setListTutor(List listTutor) {
		this.listTutor = listTutor;
	}	
	public FormFile getTheFile() {
		return theFile;
	}
	public void setTheFile(FormFile theFile) {
		this.theFile = theFile;
	}
	public String getFileContentType() {
		return fileContentType;
	}
	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getErrorFileName() {
		return errorFileName;
	}
	public void setErrorFileName(String errorFileName) {
		this.errorFileName = errorFileName;
	}	
	public String[] getStudentArray() {
		return studentArray;
	}
	public void setStudentArray(String[] studentArray) {
		this.studentArray = studentArray;
	}
	public List getListTutoringMode() {
		return listTutoringMode;
	}
	public void setListTutoringMode(List listTutoringMode) {
		this.listTutoringMode = listTutoringMode;
	}
	public String getInputStudyUnit() {
		return inputStudyUnit;
	}
	public void setInputStudyUnit(String inputStudyUnit) {
		this.inputStudyUnit = inputStudyUnit;
	}
	public StudyUnit getStudyUnit() {
		return studyUnit;
	}
	public void setStudyUnit(StudyUnit studyUnit) {
		this.studyUnit = studyUnit;
	}
	public String getSelectedAction() {
		return selectedAction;
	}
	public void setSelectedAction(String selectedAction) {
		this.selectedAction = selectedAction;
	}
	public List getListAction() {
		return listAction;
	}
	public void setListAction(List listAction) {
		this.listAction = listAction;
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
