package za.ac.unisa.lms.tools.mdrpm.forms;

import java.util.List;

import za.ac.unisa.lms.domain.general.*;
import za.ac.unisa.lms.dao.Gencod;

import org.apache.struts.util.LabelValueBean;
import org.apache.struts.validator.ValidatorActionForm;

public class MdRpmForm extends ValidatorActionForm {

	private static final long serialVersionUID = 1L;
	private String studentNr;
	private Qualification qualification = new Qualification();
	private StudyUnit studyUnit = new StudyUnit();
	private College college = new College();
	private String userCode;
	private Person myUnisaUser = new Person();
	private Person supervisor = new Person();
	private List<Gencod> resultTypeList;
	private List<LabelValueBean> criteriaList;
	private List<RpmListRecord> rpmList;
	private List<LabelValueBean> supervisorList;
	private List<LabelValueBean> responseList;
	private RpmRecord rpm;	
	private RpmListRecord selectedListItem;
	private String selectedApprover;
	private String selectedResponse;
	private String criteriaSelected;
	private String currentPage;
	private TrackRecord tracking;
	private String[] indexSelectedRemoveSupervisor;
	private String searchNo = "";
	private String searchSurname = "";
	private String searchResult = "";
	private String addSupervisorType="";
	private String supervisorAmendmentHistoryAvailable="";
	private String mntCalledFrom="";
	private String mntAllowed="";	
	private Gencod currentResult;	
	private String supervisorsStr="";
	private List<Person> signoffStaffList;
	
	
	public List<Person> getSignoffStaffList() {
		return signoffStaffList;
	}

	public void setSignoffStaffList(List<Person> signoffStaffList) {
		this.signoffStaffList = signoffStaffList;
	}

	public String getSupervisorsStr() {
		return supervisorsStr;
	}

	public void setSupervisorsStr(String supervisorsStr) {
		this.supervisorsStr = supervisorsStr;
	}

	public Gencod getCurrentResult() {
		return currentResult;
	}

	public void setCurrentResult(Gencod currentResult) {
		this.currentResult = currentResult;
	}

	public String getMntAllowed() {
		return mntAllowed;
	}

	public void setMntAllowed(String mntAllowed) {
		this.mntAllowed = mntAllowed;
	}

	public String getMntCalledFrom() {
		return mntCalledFrom;
	}

	public void setMntCalledFrom(String mntCalledFrom) {
		this.mntCalledFrom = mntCalledFrom;
	}

	public String getSupervisorAmendmentHistoryAvailable() {
		return supervisorAmendmentHistoryAvailable;
	}

	public void setSupervisorAmendmentHistoryAvailable(
			String supervisorAmendmentHistoryAvailable) {
		this.supervisorAmendmentHistoryAvailable = supervisorAmendmentHistoryAvailable;
	}

	public String getAddSupervisorType() {
		return addSupervisorType;
	}

	public void setAddSupervisorType(String addSupervisorType) {
		this.addSupervisorType = addSupervisorType;
	}

	public String getSearchNo() {
		return searchNo;
	}

	public void setSearchNo(String searchNo) {
		this.searchNo = searchNo;
	}

	public String getSearchSurname() {
		return searchSurname;
	}

	public void setSearchSurname(String searchSurname) {
		this.searchSurname = searchSurname;
	}

	public String getSearchResult() {
		return searchResult;
	}

	public void setSearchResult(String searchResult) {
		this.searchResult = searchResult;
	}

	public String[] getIndexSelectedRemoveSupervisor() {
		return indexSelectedRemoveSupervisor;
	}

	public void setIndexSelectedRemoveSupervisor(
			String[] indexSelectedRemoveSupervisor) {
		this.indexSelectedRemoveSupervisor = indexSelectedRemoveSupervisor;
	}

	public List<LabelValueBean> getResponseList() {
		return responseList;
	}

	public void setResponseList(List<LabelValueBean> responseList) {
		this.responseList = responseList;
	}

	public String getSelectedResponse() {
		return selectedResponse;
	}

	public void setSelectedResponse(String selectedResponse) {
		this.selectedResponse = selectedResponse;
	}

	public List<LabelValueBean> getSupervisorList() {
		return supervisorList;
	}

	public void setSupervisorList(List<LabelValueBean> supervisorList) {
		this.supervisorList = supervisorList;
	}

	public TrackRecord getTracking() {
		return tracking;
	}

	public void setTracking(TrackRecord tracking) {
		this.tracking = tracking;
	}

	public String getSelectedApprover() {
		return selectedApprover;
	}

	public void setSelectedApprover(String selectedApprover) {
		this.selectedApprover = selectedApprover;
	}

	public RpmListRecord getSelectedListItem() {
		return selectedListItem;
	}

	public void setSelectedListItem(RpmListRecord selectedListItem) {
		this.selectedListItem = selectedListItem;
	}

	public Person getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(Person supervisor) {
		this.supervisor = supervisor;
	}

	public RpmRecord getRpm() {
		return rpm;
	}

	public void setRpm(RpmRecord rpm) {
		this.rpm = rpm;
	}

	public String getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}

	public List<RpmListRecord> getRpmList() {
		return rpmList;
	}

	public void setRpmList(List<RpmListRecord> rpmList) {
		this.rpmList = rpmList;
	}

	public College getCollege() {
		return college;
	}

	public void setCollege(College college) {
		this.college = college;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getStudentNr() {
		return studentNr;
	}

	public void setStudentNr(String studentNr) {
		this.studentNr = studentNr;
	}

	public Qualification getQualification() {
		return qualification;
	}

	public void setQualification(Qualification qualification) {
		this.qualification = qualification;
	}

	public StudyUnit getStudyUnit() {
		return studyUnit;
	}

	public void setStudyUnit(StudyUnit studyUnit) {
		this.studyUnit = studyUnit;
	}

	public Person getMyUnisaUser() {
		return myUnisaUser;
	}

	public void setMyUnisaUser(Person myUnisaUser) {
		this.myUnisaUser = myUnisaUser;
	}	

	public List<Gencod> getResultTypeList() {
		return resultTypeList;
	}

	public void setResultTypeList(List<Gencod> resultTypeList) {
		this.resultTypeList = resultTypeList;
	}
	
	public List<LabelValueBean> getCriteriaList() {
		return criteriaList;
	}

	public void setCriteriaList(List<LabelValueBean> criteriaList) {
		this.criteriaList = criteriaList;
	}

	public String getCriteriaSelected() {
		return criteriaSelected;
	}

	public void setCriteriaSelected(String criteriaSelected) {
		this.criteriaSelected = criteriaSelected;
	}

}
