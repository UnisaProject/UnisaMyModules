package za.ac.unisa.lms.tools.tpustudentplacement.forms;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorActionForm;
import za.ac.unisa.lms.domain.general.Person;
import  za.ac.unisa.lms.tools.tpustudentplacement.model.*;
import za.ac.unisa.lms.tools.tpustudentplacement.model.Mentor;

public class StudentPlacementForm extends ValidatorActionForm {
	
	//initial input
	private String acadYear;
	private String semester;
	private String semesterType;
	private String studentNr;
	private String personnelNumber;
	private String userId;
	private Person user;
	private List listSemester;
	private List listSchoolType;
	private List listFilterSchoolType;
	private List listSchoolCategory;
	private List listFilterSchoolCategory;
	private List listCountry;
	private List listProvince;
	private List listFilterProvince;
	private List listDistrict;
	private List listFilterSchoolDistrict;
	private List listFilterSupervisorDistrict;
	private List listFilterPlacementDistrict;
	private List listSchool;
	private List listSupervisor;
	private List listProvSupervisor;
	private List listStudentPlacement;	
	private List listPlacement;
	private List listPracticalModules;
	private List listMentors;
	private String previousPage;
	private String schoolCalledFrom;
	private String supervisorCalledFrom;
	private String districtCalledFrom;
	private String postalCalledFrom;
	private String managementCalledFrom;
	private String mentorCalledFrom;
	
	
	private Integer communicationSchool;
	private String communicationTo;
	private List communicationList;
	private String communicationEmailAddress;
	private String communicationCellNumber;
	private String letterDate;
	
	//District
	private String districtAction;
	private Short districtCode;
	private String districtName;
	private Short districtProvince;
	private String districtInUse;	
	private String districtFilter;
	private Short districtFilterProvince;
	
	//School
	private String schoolAction;
	private School school;
	private String schoolFilterType;
	private String schoolFilterCategory;
	private String schoolFilterCountry="1015";
	private Short schoolFilterProvince;
	private Short schoolFilterDistrict;
	private String SchoolFilterDistrictValue;
	private String schoolFilterDistrictDesc;
	private String schoolFilter;
	
	//Supervisor
	private Supervisor supervisor;
	private String supervisorAction;
	private String supervisorFilterCountry;
	private Short supervisorFilterProvince;
	private Short supervisorFilterDistrict;
	private String supervisorFilterDistrictDesc;
	private String supervisorFilterDistrictValue;
	private String supervisorFilter;
	private SupervisorAreaRecord supervisorArea;
	private String contractStatus="All";
	private String daysToExpiry;
	private String supervisorFullName;
	private String studentsAllowed;
	private int supervisorCode;
	
	//Management
	private String placementFilterCountry="1015";
	private Short placementFilterProvince;
	private Short placementFilterDistrict;
	private Integer placementFilterSchool;
	private String placementFilterSchoolDesc;
	private Integer placementFilterSupervisor;
	private String placementFilterSupervisorDesc;
	private String placementFilterModule;
	private String placementFilterDistrictValue;
	private String placementSortOn;
	private String downloadFile;
	private String firstPlacementReached="N";
	private String lastPlacementReached="N";
	
	
	//EditPlacement
	private PlacementListRecord originalPrelimPlacement;
	private int posOfCurrPrelimPlacement=0;
	private int canSaveEdits=1;//
	private String placementFilterTrained;
		
	//Postal code
	private String searchType;
	private String postalBoxType;
	private String searchSuburb;
	private String searchPostalCode;
	private String searchResult;
	private String emptyStr="";
	
	//Student
	private Student student;
	private String studentPlacementAction="";
	private StudentPlacement studentPlacement;
	
	private String[] indexNrSelected;
	private String[] indexNrSupAreaSelected;
	private String[] indexNrPlacementSelected;
	private String[] districtSelection;
	
	//WS Coordinator
	private String coordinatorActive="Y";
	private List listCoordinator;
	private String persno="";
	private String workstation;
	private String sadecInt="Y";
	private String networkCode="";
	private String coordName="";
	private String staffInfoStatus="";
	private String coordinatorForProv;
	private Coordinator coordinator;
	
	//Placement Log
	private StudentPlacementLog studentPlacementLog; 
	private StudentPlacementLog placementLogDetails;
	private StudentPlacementListRecord studentPlacementListRecord;
	private List listLogs;
	private String logButtonTracker;
	private boolean enteredPlacementListScreen=false;
	private String justEnteredPlacementLogs="no";
	private String updatedOn;
	private String updatedBy;
	private String placementImage;
	
	//SupervisorEmail
	List supervisorEmailLogList;
	String currProv;
	//mentor
	private MentorModel mentorModel;
	private Mentor mentor;
	private List listMentorFilterDistrict;
	private List listMentorTrained;
	private List listMentor;
	private String addMentorScreen="N";
	private MentorFilteringData mentorFilterData;
	public void setCurrProv(String currProv){
		this.currProv=currProv;
	}
	public String getCurrProv(){
		            return currProv;
	}
    private String saveAction="Save";
    public void setSaveAction(String saveAction){
    	         this.saveAction=saveAction;
    }
    public String  getSaveAction(){
                     return saveAction;
    }
	public int getCanSaveEdits(){
		return canSaveEdits;
	}
	public void setCanSaveEdits(int canSaveEdits){
		          this.canSaveEdits=canSaveEdits;
	}
	public void setPosOfCurrPrelimPlacement(int posOfCurrPrelimPlacement){
		          this.posOfCurrPrelimPlacement=posOfCurrPrelimPlacement;
	}
	public int getPosOfCurrPrelimPlacement(){
		        return posOfCurrPrelimPlacement;
	}
	public void setOriginalPrelimPlacement(PlacementListRecord originalPrelimPlacement){
		 this.originalPrelimPlacement=originalPrelimPlacement;
	}
	public PlacementListRecord getOriginalPrelimPlacement(){
		      return originalPrelimPlacement;
	}
	public void setFirstPlacementReached(String firstPlacementReached){
		            this.firstPlacementReached=firstPlacementReached;
	}
	public String getFirstPlacementReached(){
		            return firstPlacementReached;
	}
	public void setLastPlacementReached(String lastPlacementReached){
		           this.lastPlacementReached=lastPlacementReached;
	}
	public String getLastPlacementReached(){
		       return lastPlacementReached;
	}
	public void setSupervisorCode(int supervisorCode){
		      this.supervisorCode=supervisorCode;
	}
	public int  getSupervisorCode(){
		    return supervisorCode;
	}
	public void setSupervisorEmailLogList(List supervisorEmailLogList){
		this.supervisorEmailLogList=supervisorEmailLogList;
	}
	public List getSupervisorEmailLogList(){
		return supervisorEmailLogList;
	}
	
	public  void setUpdatedBy(String updatedBy){
		this.updatedBy=updatedBy;
	}
	public String getUpdatedBy(){
		return updatedBy;
	}
	public  void setUpdatedOn(String updatedOn){
		this.updatedOn=updatedOn;
	}
	public String getUpdatedOn(){
		return updatedOn;
	}
	public String getPlacementImage(){
		return placementImage;
		
	}
	public void setPlacementImage(String placementImage){
		this.placementImage=placementImage;
	}
	public String getJustEnteredPlacementLogs(){
	    return justEnteredPlacementLogs;
    }
    public void  setJustEnteredPlacementLogs(String justEnteredPlacementLogs){
           this.justEnteredPlacementLogs=justEnteredPlacementLogs;
    }
	public StudentPlacementLog getPlacementLogDetails(){
		    return placementLogDetails;
	}
	public void  setPlacementLogDetails(StudentPlacementLog placementLogDetails){
	    this.placementLogDetails=placementLogDetails;
}
	public String getLogButtonTracker(){
		return logButtonTracker;
	}
	public void setLogButtonTracker(String logButtonTracker){
		this.logButtonTracker=logButtonTracker;
	}
	
	public void setStudentPlacementLog(StudentPlacementLog  studentPlacementLog){
		this.studentPlacementLog= studentPlacementLog;
	}
	public StudentPlacementLog getStudentPlacementLog(){
		return  studentPlacementLog;
	}
	public void setStudentsAllowed(String  studentsAllowed){
		this. studentsAllowed= studentsAllowed;
	}
	public String getStudentsAllowed(){
		return  studentsAllowed;
	}
	public void setSupervisorFullName(String supervisorFullName){
		this.supervisorFullName=supervisorFullName;
	}
	public String getSupervisorFullName(){
		return supervisorFullName;
	}
	public void setCoordinatorForProv(String coordinatorForProv){
		     this.coordinatorForProv=coordinatorForProv;
	}
	public String getCoordinatorForProv(){
		return coordinatorForProv;
	}
	public void setDaysToExpiry(String daysToExpiry){
		 this.daysToExpiry=daysToExpiry;
	}
	public String getDaysToExpiry(){
		  return daysToExpiry;
	}
	public String getContractStatus() {
		return contractStatus;
	}
	public void setContractStatus(String contractStatus) {
		this.contractStatus = contractStatus;
	}
	public void setCoordinator(Coordinator coordinator){
		          this.coordinator= coordinator;
	}
	public Coordinator getCoordinator(){
		return coordinator;
	}
	public void setStaffInfoStatus(String staffInfoStatus){
		 this.staffInfoStatus=staffInfoStatus;
	}
	public String getStaffInfoStatus(){
		return staffInfoStatus;
	}
	public void setCoordName(String coordName){
		this.coordName=coordName;
	}
	public String getCoordName(){
		return coordName;
	}
	public void setNetworkCode(String networkCode){
		this.networkCode=networkCode;
	}
	public String getNetworkCode(){
		return networkCode;
	}
	public void setPersno(String persno){
		this.persno=persno;
	}
	public String getPersno(){
		return persno;
	}
	public void setWorkstation(String workstation){
		this.workstation=workstation;
	}
	public String getWorkstation(){
		return workstation;
	}
	public void setSadecInt(String sadecInt){
		   this.sadecInt=sadecInt;
	}
    public String getSadecInt(){
		   return sadecInt;
	}
	public void setCoordinatorActive(String  coordinatorActive) {
		 this.coordinatorActive = coordinatorActive;
	}
	public String getCoordinatorActive() {
		 return coordinatorActive;
	}
	public void setListCoordinator(List listCoordinator) {
		this.listCoordinator = listCoordinator;
	}
	public List getListCoordinator() {
		return listCoordinator;
	}
	public String getEmptyStr() {
		return emptyStr;
	}
	public void setEmptyStr(String emptyStr) {
		this.emptyStr = emptyStr;
	}
	public String getPostalBoxType() {
		return postalBoxType;
	}
	public void setPostalBoxType(String postalBoxType) {
		this.postalBoxType = postalBoxType;
	}
	public Person getUser() {
		return user;
	}
	public void setUser(Person user) {
		this.user = user;
	}
	public String getLetterDate() {
		return letterDate;
	}
	public void setLetterDate(String letterDate) {
		this.letterDate = letterDate;
	}
	public List getListFilterSchoolDistrict() {
		return listFilterSchoolDistrict;
	}
	public void setListFilterSchoolDistrict(List listFilterSchoolDistrict) {
		this.listFilterSchoolDistrict = listFilterSchoolDistrict;
	}
	public List getListFilterSupervisorDistrict() {
		return listFilterSupervisorDistrict;
	}
	public void setListFilterSupervisorDistrict(List listFilterSupervisorDistrict) {
		this.listFilterSupervisorDistrict = listFilterSupervisorDistrict;
	}
	public List getListFilterPlacementDistrict() {
		return listFilterPlacementDistrict;
	}
	public void setListFilterPlacementDistrict(List listFilterPlacementDistrict) {
		this.listFilterPlacementDistrict = listFilterPlacementDistrict;
	}
	public String getCommunicationCellNumber() {
		return communicationCellNumber;
	}
	public void setCommunicationCellNumber(String communicationCellNumber) {
		this.communicationCellNumber = communicationCellNumber;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getDownloadFile() {
		return downloadFile;
	}
	public void setDownloadFile(String downloadFile) {
		this.downloadFile = downloadFile;
	}
	public List getListPlacement() {
		return listPlacement;
	}
	public void setListPlacement(List listPlacement) {
		this.listPlacement = listPlacement;
	}
	public String getPlacementSortOn() {
		return placementSortOn;
	}
	public void setPlacementSortOn(String placementSortOn) {
		this.placementSortOn = placementSortOn;
	}
	public String getManagementCalledFrom() {
		return managementCalledFrom;
	}
	public void setManagementCalledFrom(String managementCalledFrom) {
		this.managementCalledFrom = managementCalledFrom;
	}
	public String getPlacementFilterCountry() {
		return placementFilterCountry;
	}
	public void setPlacementFilterCountry(String placementFilterCountry) {
		this.placementFilterCountry = placementFilterCountry;
	}
	public Short getPlacementFilterProvince() {
		return placementFilterProvince;
	}
	public void setPlacementFilterProvince(Short placementFilterProvince) {
		this.placementFilterProvince = placementFilterProvince;
	}
	public Short getPlacementFilterDistrict() {
		return placementFilterDistrict;
	}
	public void setPlacementFilterDistrict(Short placementFilterDistrict) {
		this.placementFilterDistrict = placementFilterDistrict;
	}
	public Integer getPlacementFilterSchool() {
		return placementFilterSchool;
	}
	public void setPlacementFilterSchool(Integer placementFilterSchool) {
		this.placementFilterSchool = placementFilterSchool;
	}
	public String getPlacementFilterSchoolDesc() {
		return placementFilterSchoolDesc;
	}
	public void setPlacementFilterSchoolDesc(String placementFilterSchoolDesc) {
		this.placementFilterSchoolDesc = placementFilterSchoolDesc;
	}
	public Integer getPlacementFilterSupervisor() {
		return placementFilterSupervisor;
	}
	public void setPlacementFilterSupervisor(Integer placementFilterSupervisor) {
		this.placementFilterSupervisor = placementFilterSupervisor;
	}
	public String getPlacementFilterSupervisorDesc() {
		return placementFilterSupervisorDesc;
	}
	public void setPlacementFilterSupervisorDesc(
			String placementFilterSupervisorDesc) {
		this.placementFilterSupervisorDesc = placementFilterSupervisorDesc;
	}
	public String getPlacementFilterModule() {
		return placementFilterModule;
	}
	public void setPlacementFilterModule(String placementFilterModule) {
		this.placementFilterModule = placementFilterModule;
	}
	public String getPlacementFilterDistrictValue() {
		return placementFilterDistrictValue;
	}
	public void setPlacementFilterDistrictValue(String placementFilterDistrictValue) {
		this.placementFilterDistrictValue = placementFilterDistrictValue;
	}
	public List getListStudentPlacement() {
		return listStudentPlacement;
	}
	public void setListStudentPlacement(List listStudentPlacement) {
		this.listStudentPlacement = listStudentPlacement;
	}
	public String getStudentPlacementAction() {
		return studentPlacementAction;
	}
	public void setStudentPlacementAction(String studentPlacementAction) {
		this.studentPlacementAction = studentPlacementAction;
	}
	public StudentPlacement getStudentPlacement() {
		return studentPlacement;
	}
	public void setStudentPlacement(StudentPlacement studentPlacement) {
		this.studentPlacement = studentPlacement;
	}
	public StudentPlacementListRecord getStudentPlacementListRecord() {
		return studentPlacementListRecord;
	}
	public void setStudentPlacementListRecord(StudentPlacementListRecord studentPlacementListRecord) {
		this.studentPlacementListRecord = studentPlacementListRecord;
	}
	public List getListPracticalModules() {
		return listPracticalModules;
	}
	public void setListPracticalModules(List listPracticalModules) {
		this.listPracticalModules = listPracticalModules;
	}	
	public Integer getCommunicationSchool() {
		return communicationSchool;
	}
	public void setCommunicationSchool(Integer communicationSchool) {
		this.communicationSchool = communicationSchool;
	}
	public String getCommunicationTo() {
		return communicationTo;
	}
	public void setCommunicationTo(String communicationTo) {
		this.communicationTo = communicationTo;
	}
	public List getCommunicationList() {
		return communicationList;
	}
	public void setCommunicationList(List communicationList) {
		this.communicationList = communicationList;
	}
	public String getCommunicationEmailAddress() {
		return communicationEmailAddress;
	}
	public void setCommunicationEmailAddress(String communicationEmailAddress) {
		this.communicationEmailAddress = communicationEmailAddress;
	}
	public String[] getDistrictSelection() {
		return districtSelection;
	}
	public void setDistrictSelection(String[] districtSelection) {
		this.districtSelection = districtSelection;
	}	
	public String getSupervisorFilterDistrictValue() {
		return supervisorFilterDistrictValue;
	}
	public void setSupervisorFilterDistrictValue(
			String supervisorFilterDistrictValue) {
		this.supervisorFilterDistrictValue = supervisorFilterDistrictValue;
	}
	public String getSchoolFilterDistrictValue() {
		return SchoolFilterDistrictValue;
	}
	public void setSchoolFilterDistrictValue(String schoolFilterDistrictValue) {
		SchoolFilterDistrictValue = schoolFilterDistrictValue;
	}
	public String[] getIndexNrPlacementSelected() {
		return indexNrPlacementSelected;
	}
	public void setIndexNrPlacementSelected(String[] indexNrPlacementSelected) {
		this.indexNrPlacementSelected = indexNrPlacementSelected;
	}
	public String getSchoolCalledFrom() {
		return schoolCalledFrom;
	}
	public void setSchoolCalledFrom(String schoolCalledFrom) {
		this.schoolCalledFrom = schoolCalledFrom;
	}
	public String getSupervisorCalledFrom() {
		return supervisorCalledFrom;
	}
	public void setSupervisorCalledFrom(String supervisorCalledFrom) {
		this.supervisorCalledFrom = supervisorCalledFrom;
	}
	public String getDistrictCalledFrom() {
		return districtCalledFrom;
	}
	public void setDistrictCalledFrom(String districtCalledFrom) {
		this.districtCalledFrom = districtCalledFrom;
	}
	public String getPostalCalledFrom() {
		return postalCalledFrom;
	}
	public void setPostalCalledFrom(String postalCalledFrom) {
		this.postalCalledFrom = postalCalledFrom;
	}
	public String getPreviousPage() {
		return previousPage;
	}
	public void setPreviousPage(String PreviousPage) {
		this.previousPage = PreviousPage;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public String[] getIndexNrSupAreaSelected() {
		return indexNrSupAreaSelected;
	}
	public void setIndexNrSupAreaSelected(String[] indexNrSupAreaSelected) {
		this.indexNrSupAreaSelected = indexNrSupAreaSelected;
	}
	public SupervisorAreaRecord getSupervisorArea() {
		return supervisorArea;
	}
	public void setSupervisorArea(SupervisorAreaRecord supervisorArea) {
		this.supervisorArea = supervisorArea;
	}
	public String getSchoolFilterDistrictDesc() {
		return schoolFilterDistrictDesc;
	}
	public void setSchoolFilterDistrictDesc(String schoolFilterDistrictDesc) {
		this.schoolFilterDistrictDesc = schoolFilterDistrictDesc;
	}
	public void setSchoolFilterDistrict(Short schoolFilterDistrict) {
		this.schoolFilterDistrict = schoolFilterDistrict;
	}
	public String getSupervisorFilterDistrictDesc() {
		return supervisorFilterDistrictDesc;
	}
	public void setSupervisorFilterDistrictDesc(String supervisorFilterDistrictDesc) {
		this.supervisorFilterDistrictDesc = supervisorFilterDistrictDesc;
	}
	public Supervisor getSupervisor() {
		return supervisor;
	}
	public void setSupervisor(Supervisor supervisor) {
		this.supervisor = supervisor;
	}
	public List getListSupervisor() {
		return listSupervisor;
	}
	public void setListSupervisor(List listSupervisor) {
		this.listSupervisor = listSupervisor;
	}
	public List getListProvSupervisor() {
		return listProvSupervisor;
	}
	public void setListProvSupervisor(List listProvSupervisor) {
		this.listProvSupervisor = listProvSupervisor;
	}
	public String getSupervisorAction() {
		return supervisorAction;
	}
	public void setSupervisorAction(String supervisorAction) {
		this.supervisorAction = supervisorAction;
	}
	public String getSupervisorFilterCountry() {
		return supervisorFilterCountry;
	}
	public void setSupervisorFilterCountry(String supervisorFilterCountry) {
		this.supervisorFilterCountry = supervisorFilterCountry;
	}
	public Short getSupervisorFilterProvince() {
		return supervisorFilterProvince;
	}
	public void setSupervisorFilterProvince(Short supervisorFilterProvince) {
		this.supervisorFilterProvince = supervisorFilterProvince;
	}	
	public Short getSupervisorFilterDistrict() {
		return supervisorFilterDistrict;
	}
	public void setSupervisorFilterDistrict(Short supervisorFilterDistrict) {
		this.supervisorFilterDistrict = supervisorFilterDistrict;
	}
	public String getSupervisorFilter() {
		return supervisorFilter;
	}
	public void setSupervisorFilter(String supervisorFilter) {
		this.supervisorFilter = supervisorFilter;
	}
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public String getSearchSuburb() {
		return searchSuburb;
	}
	public void setSearchSuburb(String searchSuburb) {
		this.searchSuburb = searchSuburb;
	}
	public String getSearchPostalCode() {
		return searchPostalCode;
	}
	public void setSearchPostalCode(String searchPostalCode) {
		this.searchPostalCode = searchPostalCode;
	}
	public String getSearchResult() {
		return searchResult;
	}
	public void setSearchResult(String searchResult) {
		this.searchResult = searchResult;
	}
	public String getSchoolAction() {
		return schoolAction;
	}
	public void setSchoolAction(String schoolAction) {
		this.schoolAction = schoolAction;
	}
	public School getSchool() {
		return school;
	}
	public void setSchool(School school) {
		this.school = school;
	}
	public List getListSchool() {
		return listSchool;
	}
	public void setListSchool(List listSchool) {
		this.listSchool = listSchool;
	}
	public List getListFilterSchoolType() {
		return listFilterSchoolType;
	}
	public void setListFilterSchoolType(List listFilterSchoolType) {
		this.listFilterSchoolType = listFilterSchoolType;
	}
	public List getListFilterSchoolCategory() {
		return listFilterSchoolCategory;
	}
	public void setListFilterSchoolCategory(List listFilterSchoolCategory) {
		this.listFilterSchoolCategory = listFilterSchoolCategory;
	}
	public List getListFilterProvince() {
		return listFilterProvince;
	}
	public void setListFilterProvince(List listFilterProvince) {
		this.listFilterProvince = listFilterProvince;
	}
	public Short getDistrictCode() {
		return districtCode;
	}
	public void setDistrictCode(Short districtCode) {
		this.districtCode = districtCode;
	}
	public String[] getIndexNrSelected() {
		return indexNrSelected;
	}
	public void setIndexNrSelected(String[] indexNrSelected) {
		this.indexNrSelected = indexNrSelected;
	}
	public String getDistrictName() {
		return districtName;
	}
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	public Short getDistrictProvince() {
		return districtProvince;
	}
	public void setDistrictProvince(Short districtProvince) {
		this.districtProvince = districtProvince;
	}
	public String getDistrictInUse() {
		return districtInUse;
	}
	public void setDistrictInUse(String districtInUse) {
		this.districtInUse = districtInUse;
	}
	public String getDistrictAction() {
		return districtAction;
	}
	public void setDistrictAction(String districtAction) {
		this.districtAction = districtAction;
	}		
	public List getListDistrict() {
		return listDistrict;
	}
	public void setListDistrict(List listDistrict) {
		this.listDistrict = listDistrict;
	}
	public Short getDistrictFilterProvince() {
		return districtFilterProvince;
	}
	public void setDistrictFilterProvince(Short districtFilterProvince) {
		this.districtFilterProvince = districtFilterProvince;
	}
	public String getDistrictFilter() {
		return districtFilter;
	}
	public void setDistrictFilter(String districtFilter) {
		this.districtFilter = districtFilter;
	}
	public String getSchoolFilterType() {
		return schoolFilterType;
	}
	public void setSchoolFilterType(String schoolFilterType) {
		this.schoolFilterType = schoolFilterType;
	}
	public String getSchoolFilterCategory() {
		return schoolFilterCategory;
	}
	public void setSchoolFilterCategory(String schoolFilterCategory) {
		this.schoolFilterCategory = schoolFilterCategory;
	}
	public String getSchoolFilterCountry() {
		return schoolFilterCountry;
	}
	public void setSchoolFilterCountry(String schoolFilterCountry) {
		this.schoolFilterCountry = schoolFilterCountry;
	}
	public Short getSchoolFilterProvince() {
		return schoolFilterProvince;
	}
	public void setSchoolFilterProvince(Short schoolFilterProvince) {
		this.schoolFilterProvince = schoolFilterProvince;
	}	
	public Short getSchoolFilterDistrict() {
		return schoolFilterDistrict;
	}
	public String getSchoolFilter() {
		return schoolFilter;
	}
	public void setSchoolFilter(String schoolFilter) {
		this.schoolFilter = schoolFilter;
	}
	public List getListSchoolType() {
		return listSchoolType;
	}
	public void setListSchoolType(List listSchoolType) {
		this.listSchoolType = listSchoolType;
	}
	public List getListSchoolCategory() {
		return listSchoolCategory;
	}
	public void setListSchoolCategory(List listSchoolCategory) {
		this.listSchoolCategory = listSchoolCategory;
	}
	public List getListCountry() {
		return listCountry;
	}
	public void setListCountry(List listCountry) {
		this.listCountry = listCountry;
	}
	public List getListProvince() {
		return listProvince;
	}
	public void setListProvince(List listProvince) {
		this.listProvince = listProvince;
	}
	private String currentPage;
	
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
	public List getListLogs() {
		return listLogs;
	}
	public void setListLogs(List listLogs) {
		this.listLogs = listLogs;
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
	public void setPersonnelNumber(String personnelNumber){
          this.personnelNumber=personnelNumber;
    }
    public String getPersonnelNumber(){
                  return personnelNumber;
    }
	public void reset (ActionMapping mapping, HttpServletRequest request) {
		//If you deselect all items in a multibox and submit the form
		//nothing happens: the previous selected items are still selected.
		//The Form is therefore not updated. To correct this the control arrays 
		//are set to empty arrays.
		
		if (this.currentPage != null && (this.currentPage.equalsIgnoreCase("listDistrict")||
				this.currentPage.equalsIgnoreCase("listSchool") ||
				this.currentPage.equalsIgnoreCase("listSupervisor")||
				this.currentPage.equalsIgnoreCase("listCoordinator"))){
				this.indexNrSelected = new String[0];
				
		}
		if (this.currentPage != null && this.currentPage.equalsIgnoreCase("editSupervisor")){
			this.indexNrSupAreaSelected = new String[0];
		}
		if (this.currentPage != null && this.currentPage.equalsIgnoreCase("listStudentPlacement")){
			this.indexNrPlacementSelected = new String[0];
		}
		
	}
	public String getMentorCalledFrom() {
		return mentorCalledFrom;
	}
	public void setMentorCalledFrom(String mentorCalledFrom) {
		this.mentorCalledFrom = mentorCalledFrom;
	}
	public List getListMentors() {
		return listMentors;
	}
	public void setListMentors(List listMentors) {
		this.listMentors = listMentors;
	}
	public Mentor getMentor() {
		return mentor;
	}
	public void setMentor(Mentor mentor) {
		this.mentor = mentor;
	}
	public MentorFilteringData getMentorFilterData() {
		return mentorFilterData;
	}
	public void setMentorFilterData(MentorFilteringData mentorFilterData) {
		this.mentorFilterData = mentorFilterData;
	}
	public MentorModel getMentorModel() {
		return mentorModel;
	}
	public void setMentorModel(MentorModel mentorModel) {
		this.mentorModel = mentorModel;
	}
	public List getListMentorFilterDistrict() {
		return listMentorFilterDistrict;
	}
	public void setListMentorFilterDistrict(List listMentorFilterDistrict) {
		this.listMentorFilterDistrict = listMentorFilterDistrict;
	}
	public List getListMentor() {
		return listMentor;
	}
	public void setListMentor(List listMentor) {
		this.listMentor = listMentor;
	}
	public List getListMentorTrained() {
		return listMentorTrained;
	}
	public void setListMentorTrained(List listMentorTrained) {
		this.listMentorTrained = listMentorTrained;
	}
	public String getAddMentorScreen() {
		return addMentorScreen;
	}
	public void setAddMentorScreen(String addMentorScreen) {
		this.addMentorScreen = addMentorScreen;
	}
	public String getPlacementFilterTrained() {
		return placementFilterTrained;
	}
	public void setPlacementFilterTrained(String placementFilterTrained) {
		this.placementFilterTrained = placementFilterTrained;
	}
}
