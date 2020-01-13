//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.mdadm.forms;
import za.ac.unisa.lms.domain.general.Person;

import java.util.List;
import za.ac.unisa.lms.dao.Gencod;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.util.LabelValueBean;
import org.apache.struts.validator.ValidatorForm;

/**
 * MyEclipse Struts
 * Creation date: 08-18-2005
 *
 * XDoclet definition:
 * @struts:form name="acadHistoryDisplayForm"
 */
public class MdAdmissionForm extends ValidatorForm {

	// --------------------------------------------------------- Instance Variables

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Student student = new Student();
	private MdAdmissionApplication application = new MdAdmissionApplication();
	private String userCode = "";
	private String recommendation = "";
	private boolean haveToSetRecommendation = false;
	private String searchNo = "";
	private String searchSurname = "";
	private String searchResult = "";
	private int searchListIdentifier = 0;
	private boolean externalStaff = false;
	private ArrayList<UniflowFile> documentList = new ArrayList<UniflowFile>();
	private Map<String, MdAdmissionApplication> studentApplicationList = new HashMap<String, MdAdmissionApplication>();
	private Staff loggeInUser =  new Staff();
	private String selectedStudent = "";
	private List<Gencod> recommendationShortList;
	private List<Gencod> recommendationLongList;
	private String currentPage;
	private String errorMessage;
	private TrackRecord latestTrackRecord;
	private TrackRecord newTrackRecord;	
	private List<TrackRecord> signOffStatusList;
	private List<SignOffRouteRecord> admSignOffRoute;
	private List<SignOffRouteRecord> appealSignOffRoute;
	private List<Person> signoffStaffList;
	private List<LabelValueBean> responseList;
	private String selectedResponse;
	private String selectedAssignTo;
	private String appAccess="View";
	
	
	public List<SignOffRouteRecord> getAppealSignOffRoute() {
		return appealSignOffRoute;
	}
	public void setAppealSignOffRoute(List<SignOffRouteRecord> appealSignOffRoute) {
		this.appealSignOffRoute = appealSignOffRoute;
	}
	public String getAppAccess() {
		return appAccess;
	}
	public void setAppAccess(String appAccess) {
		this.appAccess = appAccess;
	}
	public List<LabelValueBean> getResponseList() {
		return responseList;
	}
	public void setResponseList(List<LabelValueBean> responseList) {
		this.responseList = responseList;
	}
	public String getSelectedAssignTo() {
		return selectedAssignTo;
	}
	public void setSelectedAssignTo(String selectedAssignTo) {
		this.selectedAssignTo = selectedAssignTo;
	}
	public List<Person> getSignoffStaffList() {
		return signoffStaffList;
	}
	public void setSignoffStaffList(List<Person> signoffStaffList) {
		this.signoffStaffList = signoffStaffList;
	}
	public String getSelectedResponse() {
		return selectedResponse;
	}
	public void setSelectedResponse(String selectedResponse) {
		this.selectedResponse = selectedResponse;
	}
	public List<TrackRecord> getSignOffStatusList() {
		return signOffStatusList;
	}
	public void setSignOffStatusList(List<TrackRecord> signOffStatusList) {
		this.signOffStatusList = signOffStatusList;
	}
	public List<SignOffRouteRecord> getAdmSignOffRoute() {
		return admSignOffRoute;
	}
	public void setAdmSignOffRoute(List<SignOffRouteRecord> admSignOffRoute) {
		this.admSignOffRoute = admSignOffRoute;
	}
	public TrackRecord getLatestTrackRecord() {
		return latestTrackRecord;
	}
	public void setLatestTrackRecord(TrackRecord latestTrackRecord) {
		this.latestTrackRecord = latestTrackRecord;
	}
	public TrackRecord getNewTrackRecord() {
		return newTrackRecord;
	}
	public void setNewTrackRecord(TrackRecord newTrackRecord) {
		this.newTrackRecord = newTrackRecord;
	}
	public String getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}
	public List<Gencod> getRecommendationShortList() {
		return recommendationShortList;
	}
	public void setRecommendationShortList(List<Gencod> recommendationShortList) {
		this.recommendationShortList = recommendationShortList;
	}
	public List<Gencod> getRecommendationLongList() {
		return recommendationLongList;
	}
	public void setRecommendationLongList(List<Gencod> recommendationLongList) {
		this.recommendationLongList = recommendationLongList;
	}
	public boolean isExternalStaff() {
		return externalStaff;
	}
	public void setExternalStaff(boolean externalStaff) {
		this.externalStaff = externalStaff;
	}
	
	public String getSearchResult() {
		return searchResult;
	}

	public void setSearchResult(String searchResult) {
		this.searchResult = searchResult;
	}

	
	public int getSearchListIdentifier() {
		return searchListIdentifier;
	}

	public void setSearchListIdentifier(int searchListIdentifier) {
		this.searchListIdentifier = searchListIdentifier;
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

	public boolean isHaveToSetRecommendation() {
		return haveToSetRecommendation;
	}

	public void setHaveToSetRecommendation(boolean haveToSetRecommendation) {
		this.haveToSetRecommendation = haveToSetRecommendation;
	}
	
	public String getRecommendation() {
		return recommendation;
	}

	public void setRecommendation(String recommendation) {
		this.recommendation = recommendation;
	}
	
	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public MdAdmissionApplication getApplication() {
		return application;
	}

	public void setApplication(MdAdmissionApplication application) {
		this.application = application;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public ArrayList<UniflowFile> getDocumentList() {
		return documentList;
	}
	public void setDocumentList(ArrayList<UniflowFile> documentList) {
		this.documentList = documentList;
	}
	public Map<String, MdAdmissionApplication> getStudentApplicationList() {
		return studentApplicationList;
	}
	public void setStudentApplicationList(
			Map<String, MdAdmissionApplication> studentApplicationList) {
		this.studentApplicationList = studentApplicationList;
	}
	public Staff getLoggeInUser() {
		return loggeInUser;
	}
	public void setLoggeInUser(Staff loggeInUser) {
		this.loggeInUser = loggeInUser;
	}
	public String getSelectedStudent() {
		return selectedStudent;
	}
	public void setSelectedStudent(String selectedStudent) {
		this.selectedStudent = selectedStudent;
	}


	// --------------------------------------------------------- Methods

	
}

