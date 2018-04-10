package za.ac.unisa.lms.tools.examscriptstats.forms;

import java.util.List;

import org.apache.struts.validator.ValidatorActionForm;

public class ExamScriptStatsForm extends ValidatorActionForm {
	
	private static final long serialVersionUID = 1L;
	private String currentPage;
	private String novellUserId;
	private String examYear;
	private String examPeriod;
	private String studyUnit;
	private String paperNr;
	private Integer totalScriptsExpected;
	private Integer totalScriptsReceived;
	private Integer	totalScriptsOutstanding;
	private Integer totalStudentsAbsent;
	private Integer totalMCQReceived;
	private Integer totalMCQOutstanding;
	private String selectedVenue;
	private String selectedVenueDesc;
	private String pageDownFlag;
	private String pageUpFlag;	
	private String firstVenue;
	private String lastVenue;
	private String firstStudentNumber;
	private String lastStudentNumber;
	private String studentPageDownFlag;
	private String studentPageUpFlag;
	private String displayAction;
	private String displayStudentAction;
	private String note;
	private VenueStatsRecord selectedVenueStatsRecord;
	private List listVenueStats;
	private List listStudentScriptsOutstanding;
	private List listExamPeriods;
	
	public List getListExamPeriods() {
		return listExamPeriods;
	}
	public void setListExamPeriods(List listExamPeriods) {
		this.listExamPeriods = listExamPeriods;
	}
	public String getSelectedVenue() {
		return selectedVenue;
	}
	public void setSelectedVenue(String selectedVenue) {
		this.selectedVenue = selectedVenue;
	}
	public String getSelectedVenueDesc() {
		return selectedVenueDesc;
	}
	public void setSelectedVenueDesc(String selectedVenueDesc) {
		this.selectedVenueDesc = selectedVenueDesc;
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
	public String getExamYear() {
		return examYear;
	}
	public void setExamYear(String examYear) {
		this.examYear = examYear;
	}
	public String getExamPeriod() {
		return examPeriod;
	}
	public void setExamPeriod(String examPeriod) {
		this.examPeriod = examPeriod;
	}
	public String getStudyUnit() {
		return studyUnit;
	}
	public void setStudyUnit(String studyUnit) {
		this.studyUnit = studyUnit;
	}
	public String getPaperNr() {
		return paperNr;
	}
	public void setPaperNr(String paperNr) {
		this.paperNr = paperNr;
	}
	public Integer getTotalScriptsExpected() {
		return totalScriptsExpected;
	}
	public void setTotalScriptsExpected(Integer totalScriptsExpected) {
		this.totalScriptsExpected = totalScriptsExpected;
	}
	public Integer getTotalScriptsReceived() {
		return totalScriptsReceived;
	}
	public void setTotalScriptsReceived(Integer totalScriptsReceived) {
		this.totalScriptsReceived = totalScriptsReceived;
	}
	public Integer getTotalScriptsOutstanding() {
		return totalScriptsOutstanding;
	}
	public void setTotalScriptsOutstanding(Integer totalScriptsOutstanding) {
		this.totalScriptsOutstanding = totalScriptsOutstanding;
	}
	public Integer getTotalStudentsAbsent() {
		return totalStudentsAbsent;
	}
	public void setTotalStudentsAbsent(Integer totalStudentsAbsent) {
		this.totalStudentsAbsent = totalStudentsAbsent;
	}
	public Integer getTotalMCQReceived() {
		return totalMCQReceived;
	}
	public void setTotalMCQReceived(Integer totalMCQReceived) {
		this.totalMCQReceived = totalMCQReceived;
	}
	public Integer getTotalMCQOutstanding() {
		return totalMCQOutstanding;
	}
	public void setTotalMCQOutstanding(Integer totalMCQOutstanding) {
		this.totalMCQOutstanding = totalMCQOutstanding;
	}
	public List getListVenueStats() {
		return listVenueStats;
	}
	public void setListVenueStats(List listVenueStats) {
		this.listVenueStats = listVenueStats;
	}
	public VenueStatsRecord getSelectedVenueStatsRecord() {
		return selectedVenueStatsRecord;
	}
	public void setSelectedVenueStatsRecord(
			VenueStatsRecord selectedVenueStatsRecord) {
		this.selectedVenueStatsRecord = selectedVenueStatsRecord;
	}
	public List getListStudentScriptsOutstanding() {
		return listStudentScriptsOutstanding;
	}
	public void setListStudentScriptsOutstanding(List listStudentScriptsOutstanding) {
		this.listStudentScriptsOutstanding = listStudentScriptsOutstanding;
	}
	public String getPageUpFlag() {
		return pageUpFlag;
	}
	public void setPageUpFlag(String pageUpFlag) {
		this.pageUpFlag = pageUpFlag;
	}
	public String getPageDownFlag() {
		return pageDownFlag;
	}
	public void setPageDownFlag(String pageDownFlag) {
		this.pageDownFlag = pageDownFlag;
	}
	public String getFirstVenue() {
		return firstVenue;
	}
	public void setFirstVenue(String firstVenue) {
		this.firstVenue = firstVenue;
	}
	public String getLastVenue() {
		return lastVenue;
	}
	public void setLastVenue(String lastVenue) {
		this.lastVenue = lastVenue;
	}
	public String getDisplayAction() {
		return displayAction;
	}
	public void setDisplayAction(String displayAction) {
		this.displayAction = displayAction;
	}
	public String getFirstStudentNumber() {
		return firstStudentNumber;
	}
	public void setFirstStudentNumber(String firstStudentNumber) {
		this.firstStudentNumber = firstStudentNumber;
	}
	public String getLastStudentNumber() {
		return lastStudentNumber;
	}
	public void setLastStudentNumber(String lastStudentNumber) {
		this.lastStudentNumber = lastStudentNumber;
	}
	public String getStudentPageDownFlag() {
		return studentPageDownFlag;
	}
	public void setStudentPageDownFlag(String studentPageDownFlag) {
		this.studentPageDownFlag = studentPageDownFlag;
	}
	public String getStudentPageUpFlag() {
		return studentPageUpFlag;
	}
	public void setStudentPageUpFlag(String studentPageUpFlag) {
		this.studentPageUpFlag = studentPageUpFlag;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getDisplayStudentAction() {
		return displayStudentAction;
	}
	public void setDisplayStudentAction(String displayStudentAction) {
		this.displayStudentAction = displayStudentAction;
	}
	
	
}
