package za.ac.unisa.lms.tools.assmarkerreallocation.forms;

import org.apache.struts.validator.ValidatorActionForm;
import java.util.List;

import za.ac.unisa.lms.domain.assessmentCriteria.Assignment;
import za.ac.unisa.lms.domain.general.StudyUnit;

public class AssMarkerReallocationForm extends ValidatorActionForm {

	//initial input
	private String acadYear;
	private String semester;
	private String semesterType;
	private String studyUnit;
	private String assignmentNr;
	private String uniqueNr;
	private List listSemester;
	private List listYear;
	private List assessmentList;
	private StudyUnit studyUnitRecord;
	private String fileFormats;
	private int studentsRegistered;
	private List listMarkerDetail;
	private String userId;
	private boolean updateAllowed;	
	private boolean langDisplayed=false;;	
	private Assignment assignment;
	private boolean displayLang;
	
	public List getListYear() {
		return listYear;
	}
	public void setListYear(List listYear) {
		this.listYear = listYear;
	}
	public void setDisplayLang(boolean displayLang){
		this.displayLang=displayLang;
	}
	public boolean isDisplayLang(){
		return displayLang;
	}
	public void setAssignment(Assignment assignment){
		           this.assignment=assignment;
	}
	public Assignment getAssignment(){
		      return assignment;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public boolean isUpdateAllowed() {
		return updateAllowed;
	}
	public void setUpdateAllowed(boolean updateAllowed) {
		this.updateAllowed = updateAllowed;
	}
	public String getSemesterType() {
		return semesterType;
	}
	public void setSemesterType(String semesterType) {
		this.semesterType = semesterType;
	}
	public String getUniqueNr() {
		return uniqueNr;
	}
	public void setUniqueNr(String uniqueNr) {
		this.uniqueNr = uniqueNr;
	}
	public List getListMarkerDetail() {
		return listMarkerDetail;
	}
	public void setListMarkerDetail(List listMarkerDetail) {
		this.listMarkerDetail = listMarkerDetail;
	}
	public List getAssessmentList() {
		return assessmentList;
	}
	public void setAssessmentList(List assessmentList) {
		   this.assessmentList = assessmentList;
	}
	public String getFileFormats() {
		return fileFormats;
	}
	public void setFileFormats(String fileFormats) {
		this.fileFormats = fileFormats;
	}
	public int getStudentsRegistered() {
		return studentsRegistered;
	}
	public void setStudentsRegistered(int studentsRegistered) {
		this.studentsRegistered = studentsRegistered;
	}
	public StudyUnit getStudyUnitRecord() {
		return studyUnitRecord;
	}
	public void setStudyUnitRecord(StudyUnit studyUnitRecord) {
		this.studyUnitRecord = studyUnitRecord;
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
	public String getStudyUnit() {
		return studyUnit;
	}
	public void setStudyUnit(String studyUnit) {
		this.studyUnit = studyUnit;
	}
	public String getAssignmentNr() {
		return assignmentNr;
	}
	public void setAssignmentNr(String assignmentNr) {
		this.assignmentNr = assignmentNr;
	}
	public List getListSemester() {
		return listSemester;
	}
	public void setListSemester(List listSemester) {
		this.listSemester = listSemester;
	}
	private List markingLanguageList;
	public List getMarkingLanguageList() {
		return markingLanguageList;
	}
	public void setMarkingLanguageList(List markingLanguageList) {
		this.markingLanguageList = markingLanguageList;
	}
	private String  markingLanguageListAsString;
	public  String getMarkingLanguageListAsString() {
		return markingLanguageListAsString;
	}
	public void setMarkingLanguageListAsString(List markingLanguageList) {
		         String   languages="";
		         if((markingLanguageList!=null)&&(!markingLanguageList.isEmpty())){
		              for(int x=0;x< markingLanguageList.size();x++){
		            	  if(x==0){
		        	          languages=((MarkingLangModel) markingLanguageList.get(x)).getLanguage();
		            	  }else{
		            		  languages=languages+","+((MarkingLangModel) markingLanguageList.get(x)).getLanguage();
		            	  }
		              }
		         }
		         markingLanguageListAsString = languages;
    }
}
