package za.ac.unisa.lms.tools.smsbatch.forms;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.ValidatorForm;


/**
 * MyEclipse Struts
 * Creation date: 05-18-2006
 *
 * XDoclet definition:
 * @struts:form name="/smsbatch"
 */
public class SmsBatchForm extends ValidatorForm {

	// --------------------------------------------------------- Instance Variables

	private boolean canSendSms = false;
	private boolean smsWasSend = false;
	/** CriteriaType property */
	private String regCriteriaType;
	private String geoCriteriaType;
	private String prevRegCriteriaType;
	private String selectedItems;
	
	private short academicYear;
	private String registrationPeriod;
	private String novellUserCode;
	private String userName;
	private String department;
	/** SMS Request variables */
	private int batchNr;
	private String controllCellNr;
	private String fromSystemGc26;
	private int messageCount;
	private short deptCode;
	private int personnelNr;
	private String rcCode;
	private String rcDescription;
	private String reasonGc27;
	private String message;
	/** Costing */
	private double totalCost;
	private double availableBudgetAmount;
	private double budgetAmount;
	private double costPerSms;
	/** Data file name */
	private String dataFileName;
	/** Reason list*/
	private List regList;
	private String[] regSelection = new String[15];
	private String[] geoSelection = new String[15];
	private String selectionString;
	/** Search */
	private String searchType;
	private String searchCode;
	private String searchDescription;
	private String searchResult;
	private String[] selectedSearchList = new String[15];
	private short rcCount;
	private List rcList;
	private String smsEnvironment;
	private List controlCellNumberList;
	
	//** File type */
	private String fileContentType;
	private String fileName = "";
	private String fileType = "";
	private String fileSize = "";
	private int recordNr = 0;
	private int invalidRecordNr = 0;
	private String errorFileName="";	
	private String[] cellArray;	
	private String[] studentArray;
	private String selectionCriteria;
	private boolean dcmUser=false;	
	
	public boolean isDcmUser() {
		return dcmUser;
	}

	public void setDcmUser(boolean dcmUser) {
		this.dcmUser = dcmUser;
	}

	public String getSelectionCriteria() {
		return selectionCriteria;
	}

	public void setSelectionCriteria(String selectionCriteria) {
		this.selectionCriteria = selectionCriteria;
	}

	public List getControlCellNumberList() {
		return controlCellNumberList;
	}

	public void setControlCellNumberList(List controlCellNumberList) {
		this.controlCellNumberList = controlCellNumberList;
	}

	public String getErrorFileName() {
		return errorFileName;
	}

	public void setErrorFileName(String errorFileName) {
		this.errorFileName = errorFileName;
	}
	public String[] getCellArray() {
		return cellArray;
	}

	public void setCellArray(String[] cellArray) {
		this.cellArray = cellArray;
	}

	public String[] getStudentArray() {
		return studentArray;
	}

	public void setStudentArray(String[] studentArray) {
		this.studentArray = studentArray;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public int getRecordNr() {
		return recordNr;
	}

	public void setRecordNr(int recordNr) {
		this.recordNr = recordNr;
	}

	public int getInvalidRecordNr() {
		return invalidRecordNr;
	}

	public void setInvalidRecordNr(int invalidRecordNr) {
		this.invalidRecordNr = invalidRecordNr;
	}

	private FormFile theFile;
	
	// --------------------------------------------------------- Methods

	public String getSmsEnvironment() {
		return smsEnvironment;
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

	public void setSmsEnvironment(String smsEnvironment) {
		this.smsEnvironment = smsEnvironment;
	}

	public List getRcList() {
		return rcList;
	}

	public void setRcList(List rcList) {
		this.rcList = rcList;
	}

	public short getRcCount() {
		return rcCount;
	}

	public void setRcCount(short rcCount) {
		this.rcCount = rcCount;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}


	/**
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {

		// TODO Auto-generated method stub
	}

	/**
	 * Returns the regCriteriaType.
	 * @return String
	 */
	public String getRegCriteriaType() {
		return regCriteriaType;
	}

	/**
	 * Set the regCriteriaType.
	 * @param regCriteriaType The regCriteriaType to set
	 */
	public void setRegCriteriaType(String regCriteriaType) {
		this.regCriteriaType = regCriteriaType;
	}

	/**
	 * Returns the geoCriteriaType.
	 * @return String
	 */
	public String getGeoCriteriaType() {
		return geoCriteriaType;
	}

	/**
	 * Set the geoCriteriaType.
	 * @param geoCriteriaType The geoCriteriaType to set
	 */
	public void setGeoCriteriaType(String geoCriteriaType) {
		this.geoCriteriaType = geoCriteriaType;
	}

	public short getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(short academicYear) {
		this.academicYear = academicYear;
	}

	public double getAvailableBudgetAmount() {
		return availableBudgetAmount;
	}

	public void setAvailableBudgetAmount(double availableBudgetAmount) {
		this.availableBudgetAmount = availableBudgetAmount;
	}

	public int getBatchNr() {
		return batchNr;
	}

	public void setBatchNr(int batchNr) {
		this.batchNr = batchNr;
	}

	public double getBudgetAmount() {
		return budgetAmount;
	}

	public void setBudgetAmount(double budgetAmount) {
		this.budgetAmount = budgetAmount;
	}

	public String getControllCellNr() {
		return controllCellNr;
	}

	public void setControllCellNr(String controllCellNr) {
		this.controllCellNr = controllCellNr;
	}

	public double getCostPerSms() {
		return costPerSms;
	}

	public void setCostPerSms(double costPerSms) {
		this.costPerSms = costPerSms;
	}

	public String getDataFileName() {
		return dataFileName;
	}

	public void setDataFileName(String dataFileName) {
		this.dataFileName = dataFileName;
	}

	public short getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(short deptCode) {
		this.deptCode = deptCode;
	}

	public String getFromSystemGc26() {
		return fromSystemGc26;
	}

	public void setFromSystemGc26(String fromSystemGc26) {
		this.fromSystemGc26 = fromSystemGc26;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getMessageCount() {
		return messageCount;
	}

	public void setMessageCount(int messageCount) {
		this.messageCount = messageCount;
	}

	public String getNovellUserCode() {
		return novellUserCode;
	}

	public void setNovellUserCode(String novellUserCode) {
		this.novellUserCode = novellUserCode;
	}

	public int getPersonnelNr() {
		return personnelNr;
	}

	public void setPersonnelNr(int personnelNr) {
		this.personnelNr = personnelNr;
	}

	public String getRcCode() {
		return rcCode;
	}

	public void setRcCode(String rcCode) {
		this.rcCode = rcCode;
	}

	public String getRcDescription() {
		return rcDescription;
	}

	public void setRcDescription(String rcDescription) {
		this.rcDescription = rcDescription;
	}

	public String getReasonGc27() {
		return reasonGc27;
	}

	public void setReasonGc27(String reasonGc27) {
		this.reasonGc27 = reasonGc27;
	}

	public String getRegistrationPeriod() {
		return registrationPeriod;
	}

	public void setRegistrationPeriod(String registrationPeriod) {
		this.registrationPeriod = registrationPeriod;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	public List getRegList() {
		return regList;
	}

	public void setRegList(List regList) {
		this.regList = regList;
	}

	public String getSelectionString() {
		return selectionString;
	}

	public void setSelectionString(String selectionString) {
		this.selectionString = selectionString;
	}

	public String[] getGeoSelection() {
		return geoSelection;
	}

	public void setGeoSelection(String[] geoSelection) {
		this.geoSelection = geoSelection;
	}

	public String[] getRegSelection() {
		return regSelection;
	}

	public void setRegSelection(String[] regSelection) {
		this.regSelection = regSelection;
	}

	public boolean isCanSendSms() {
		return canSendSms;
	}

	public void setCanSendSms(boolean canSendSms) {
		this.canSendSms = canSendSms;
	}

	public String getSearchCode() {
		return searchCode;
	}

	public void setSearchCode(String searchCode) {
		this.searchCode = searchCode;
	}

	public String getSearchDescription() {
		return searchDescription;
	}

	public void setSearchDescription(String searchDescription) {
		this.searchDescription = searchDescription;
	}

	public String getSearchResult() {
		return searchResult;
	}

	public void setSearchResult(String searchResult) {
		this.searchResult = searchResult;
	}

	public String[] getSelectedSearchList() {
		return selectedSearchList;
	}

	public void setSelectedSearchList(String[] selectedSearchList) {
		this.selectedSearchList = selectedSearchList;
	}

	public String getPrevRegCriteriaType() {
		return prevRegCriteriaType;
	}

	public void setPrevRegCriteriaType(String prevRegCriteriaType) {
		this.prevRegCriteriaType = prevRegCriteriaType;
	}

	public boolean isSmsWasSend() {
		return smsWasSend;
	}

	public void setSmsWasSend(boolean smsWasSend) {
		this.smsWasSend = smsWasSend;
	}

	public String getSelectedItems() {
		return selectedItems;
	}

	public void setSelectedItems(String selectedItems) {
		this.selectedItems = selectedItems;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}

