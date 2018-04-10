//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.mdadmission.forms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
	private boolean oldUniflowInUse = true;
	
	public boolean isOldUniflowInUse() {
		return oldUniflowInUse;
	}
	public void setOldUniflowInUse(boolean oldUniflowInUse) {
		this.oldUniflowInUse = oldUniflowInUse;
	}

	private String errorMessage;
	
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

	private ArrayList<Staff> signOffList = new ArrayList<Staff>();
	
	public String getRecommendation() {
		return recommendation;
	}

	public void setRecommendation(String recommendation) {
		this.recommendation = recommendation;
	}


	public ArrayList<Staff> getSignOffList() {
		return signOffList;
	}

	public void setSignOffList(ArrayList<Staff> signOffList) {
		this.signOffList = signOffList;
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

