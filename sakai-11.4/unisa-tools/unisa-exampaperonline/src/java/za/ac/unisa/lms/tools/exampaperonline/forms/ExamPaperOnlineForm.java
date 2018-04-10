package za.ac.unisa.lms.tools.exampaperonline.forms;

import java.util.List;

import org.apache.struts.validator.ValidatorActionForm;
import za.ac.unisa.lms.domain.general.Person;

/**
 * @author pretoj
 *
 */
public class ExamPaperOnlineForm extends ValidatorActionForm{
	
	private String userId;	
	private List listUserId;
	private Person user;
	private String fromAction;
	private String xpoAction;
	private String xpoUrl;
	private String documentsToUploadShown;
	private boolean firstUpload;
	
	
	//initial input
	private String examYear;
	private String examPeriod;
	private String studyUnit;
	private String paperNo;
	private String paperContent;
	private String toolAction;	
	private Short department;
		
	//list criteria - list examination papers/retrieve examination paper
	private String listCriteriaExamYear;
	private String listCriteriaExamPeriod;
	private String listCriteriaStudyUnit;
	private String listCriteriaEquivalentStr;
	private String listCriteriaPaperContent;
	private String listCriteriaPaperNo;
	private String listCriteriaFromRole;
	
	//lists - page up and page down
	private String pageUpFlag;
	private String pageDownFlag;
	private String firstTimestamp;
	private String lastTimestamp;
	private String displayListAction;
	private Integer rowsInList;
	
	//heading
	private String studyUnitDesc;
	private String examPeriodDesc;	
	private String toolActionDesc;
	
	private List listUserRoles;			
	private String toAdr;
		
	//list boxes on windows
	private List listExamPeriods;
	private List listToolActions;
	private List listFromRoles;
	private List listPaperContentTypes;
	private List listSenderResponses;
	private List listRecipients;
	
	//upload action
	private String uploadDocument1;
	private String uploadDocument2;
	private String uploadDocument3;
	private String uploadDocument4;
	
	private String uploadNrOfDocs;
	private String uploadSendersResponse;
	private String uploadAdditionalText;
	private String uploadPrevRemark;
	private String uploadPrevSender;
		
	private String uploadSelectedRecipientIndex;
	
	private String uploadCurrentUserRole;
	private boolean uploadApprovalStatusRequired;
	private boolean uploadDocumentDetailRequired;
	private CoverDocket coverDocket;	
	
	//retrieve action
	private List availableDocList;
	private List retrievedDocList;
	
	private Paper selectedPaper;	
	private List equivalentList;
	private String equivalentStr;	
	private String resetReason;
	
	
	public String getListCriteriaEquivalentStr() {
		return listCriteriaEquivalentStr;
	}
	public void setListCriteriaEquivalentStr(String listCriteriaEquivalentStr) {
		this.listCriteriaEquivalentStr = listCriteriaEquivalentStr;
	}
	public String getEquivalentStr() {
		return equivalentStr;
	}
	public void setEquivalentStr(String equivalentStr) {
		this.equivalentStr = equivalentStr;
	}
	public List getEquivalentList() {
		return equivalentList;
	}
	public void setEquivalentList(List equivalentList) {
		this.equivalentList = equivalentList;
	}	
	public String getResetReason() {
		return resetReason;
	}
	public void setResetReason(String resetReason) {
		this.resetReason = resetReason;
	}
	public List getListFromRoles() {
		return listFromRoles;
	}
	public void setListFromRoles(List listFromRoles) {
		this.listFromRoles = listFromRoles;
	}
	public String getListCriteriaFromRole() {
		return listCriteriaFromRole;
	}
	public void setListCriteriaFromRole(String listCriteriaFromRole) {
		this.listCriteriaFromRole = listCriteriaFromRole;
	}
	public CoverDocket getCoverDocket() {
		return coverDocket;
	}
	public void setCoverDocket(CoverDocket coverDocket) {
		this.coverDocket = coverDocket;
	}
	public String getUploadPrevRemark() {
		return uploadPrevRemark;
	}
	public void setUploadPrevRemark(String uploadPrevRemark) {
		this.uploadPrevRemark = uploadPrevRemark;
	}
	public String getUploadPrevSender() {
		return uploadPrevSender;
	}
	public void setUploadPrevSender(String uploadPrevSender) {
		this.uploadPrevSender = uploadPrevSender;
	}
	public String getXpoUrl() {
		return xpoUrl;
	}
	public void setXpoUrl(String xpoUrl) {
		this.xpoUrl = xpoUrl;
	}
	public boolean isFirstUpload() {
		return firstUpload;
	}
	public void setFirstUpload(boolean firstUpload) {
		this.firstUpload = firstUpload;
	}
	public List getListUserId() {
		return listUserId;
	}
	public void setListUserId(List listUserId) {
		this.listUserId = listUserId;
	}
	public String getUploadCurrentUserRole() {
		return uploadCurrentUserRole;
	}
	public void setUploadCurrentUserRole(String uploadCurrentUserRole) {
		this.uploadCurrentUserRole = uploadCurrentUserRole;
	}
	public String getDocumentsToUploadShown() {
		return documentsToUploadShown;
	}
	public void setDocumentsToUploadShown(String documentsToUploadShown) {
		this.documentsToUploadShown = documentsToUploadShown;
	}
	public Short getDepartment() {
		return department;
	}
	public void setDepartment(Short department) {
		this.department = department;
	}
	public String getListCriteriaExamYear() {
		return listCriteriaExamYear;
	}
	public void setListCriteriaExamYear(String listCriteriaExamYear) {
		this.listCriteriaExamYear = listCriteriaExamYear;
	}
	public String getListCriteriaExamPeriod() {
		return listCriteriaExamPeriod;
	}
	public void setListCriteriaExamPeriod(String listCriteriaExamPeriod) {
		this.listCriteriaExamPeriod = listCriteriaExamPeriod;
	}
	public String getListCriteriaStudyUnit() {
		return listCriteriaStudyUnit;
	}
	public void setListCriteriaStudyUnit(String listCriteriaStudyUnit) {
		this.listCriteriaStudyUnit = listCriteriaStudyUnit;
	}		
	public String getListCriteriaPaperContent() {
		return listCriteriaPaperContent;
	}
	public void setListCriteriaPaperContent(String listCriteriaPaperContent) {
		this.listCriteriaPaperContent = listCriteriaPaperContent;
	}
	public String getListCriteriaPaperNo() {
		return listCriteriaPaperNo;
	}
	public void setListCriteriaPaperNo(String listCriteriaPaperNo) {
		this.listCriteriaPaperNo = listCriteriaPaperNo;
	}
	public String getFromAction() {
		return fromAction;
	}
	public void setFromAction(String fromAction) {
		this.fromAction = fromAction;
	}
	public boolean isUploadApprovalStatusRequired() {
		return uploadApprovalStatusRequired;
	}
	public void setUploadApprovalStatusRequired(boolean uploadApprovalStatusRequired) {
		this.uploadApprovalStatusRequired = uploadApprovalStatusRequired;
	}
	public boolean isUploadDocumentDetailRequired() {
		return uploadDocumentDetailRequired;
	}
	public void setUploadDocumentDetailRequired(boolean uploadDocumentDetailRequired) {
		this.uploadDocumentDetailRequired = uploadDocumentDetailRequired;
	}
	public Paper getSelectedPaper() {
		return selectedPaper;
	}
	public void setSelectedPaper(Paper selectedPaper) {
		this.selectedPaper = selectedPaper;
	}	
	public String getPaperContent() {
		return paperContent;
	}
	public void setPaperContent(String paperContent) {
		this.paperContent = paperContent;
	}
	public String getDisplayListAction() {
		return displayListAction;
	}
	public void setDisplayListAction(String displayListAction) {
		this.displayListAction = displayListAction;
	}
	public Integer getRowsInList() {
		return rowsInList;
	}
	public void setRowsInList(Integer rowsInList) {
		this.rowsInList = rowsInList;
	}
	public String getFirstTimestamp() {
		return firstTimestamp;
	}
	public void setFirstTimestamp(String firstTimestamp) {
		this.firstTimestamp = firstTimestamp;
	}
	public String getLastTimestamp() {
		return lastTimestamp;
	}
	public void setLastTimestamp(String lastTimestamp) {
		this.lastTimestamp = lastTimestamp;
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
	public String getXpoAction() {
		return xpoAction;
	}
	public void setXpoAction(String xpoAction) {
		this.xpoAction = xpoAction;
	}		
	public List getAvailableDocList() {
		return availableDocList;
	}
	public void setAvailableDocList(List availableDocList) {
		this.availableDocList = availableDocList;
	}
	public List getRetrievedDocList() {
		return retrievedDocList;
	}
	public void setRetrievedDocList(List retrievedDocList) {
		this.retrievedDocList = retrievedDocList;
	}
	public String getToolActionDesc() {
		return toolActionDesc;
	}
	public void setToolActionDesc(String toolActionDesc) {
		this.toolActionDesc = toolActionDesc;
	}	
	public String getStudyUnitDesc() {
		return studyUnitDesc;
	}
	public void setStudyUnitDesc(String studyUnitDesc) {
		this.studyUnitDesc = studyUnitDesc;
	}
	public String getExamPeriodDesc() {
		return examPeriodDesc;
	}
	public void setExamPeriodDesc(String examPeriodDesc) {
		this.examPeriodDesc = examPeriodDesc;
	}
	public Person getUser() {
		return user;
	}
	public void setUser(Person user) {
		this.user = user;
	}
	public String getUploadSelectedRecipientIndex() {
		return uploadSelectedRecipientIndex;
	}
	public void setUploadSelectedRecipientIndex(String uploadSelectedRecipientIndex) {
		this.uploadSelectedRecipientIndex = uploadSelectedRecipientIndex;
	}
	public String getPaperNo() {
		return paperNo;
	}
	public void setPaperNo(String paperNr) {
		this.paperNo = paperNr;
	}
	public String getUploadAdditionalText() {
		return uploadAdditionalText;
	}
	public void setUploadAdditionalText(String uploadAdditionalText) {
		this.uploadAdditionalText = uploadAdditionalText;
	}
	public String getUploadDocument1() {
		return uploadDocument1;
	}
	public void setUploadDocument1(String uploadDocument1) {
		this.uploadDocument1 = uploadDocument1;
	}
	public String getUploadDocument2() {
		return uploadDocument2;
	}
	public void setUploadDocument2(String uploadDocument2) {
		this.uploadDocument2 = uploadDocument2;
	}
	public String getUploadDocument3() {
		return uploadDocument3;
	}
	public void setUploadDocument3(String uploadDocument3) {
		this.uploadDocument3 = uploadDocument3;
	}
	public String getUploadDocument4() {
		return uploadDocument4;
	}
	public void setUploadDocument4(String uploadDocument4) {
		this.uploadDocument4 = uploadDocument4;
	}
	private String currentPage;
	
	public String getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}
	public List getListRecipients() {
		return listRecipients;
	}
	public void setListRecipients(List listRecipients) {
		this.listRecipients = listRecipients;
	}
	public List getListUserRoles() {
		return listUserRoles;
	}
	public void setListUserRoles(List listUserRoles) {
		this.listUserRoles = listUserRoles;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
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
		return studyUnit.toUpperCase();
	}
	public void setStudyUnit(String studyUnit) {
		this.studyUnit = studyUnit.toUpperCase();
	}
	public String getToolAction() {
		return toolAction;
	}
	public void setToolAction(String toolAction) {
		this.toolAction = toolAction;
	}	
	public String getUploadNrOfDocs() {
		return uploadNrOfDocs;
	}
	public void setUploadNrOfDocs(String uploadNrOfDocs) {
		this.uploadNrOfDocs = uploadNrOfDocs;
	}	
	public String getUploadSendersResponse() {
		return uploadSendersResponse;
	}
	public void setUploadSendersResponse(String uploadSendersResponse) {
		this.uploadSendersResponse = uploadSendersResponse;
	}
	public String getToAdr() {
		return toAdr;
	}
	public void setToAdr(String toAdr) {
		this.toAdr = toAdr;
	}
	public List getListExamPeriods() {
		return listExamPeriods;
	}
	public void setListExamPeriods(List listExamPeriods) {
		this.listExamPeriods = listExamPeriods;
	}
	public List getListToolActions() {
		return listToolActions;
	}
	public void setListToolActions(List listToolActions) {
		this.listToolActions = listToolActions;
	}
	public List getListPaperContentTypes() {
		return listPaperContentTypes;
	}
	public void setListPaperContentTypes(List listPaperContentTypes) {
		this.listPaperContentTypes = listPaperContentTypes;
	}
	public List getListSenderResponses() {
		return listSenderResponses;
	}
	public void setListSenderResponses(List listSenderResponses) {
		this.listSenderResponses = listSenderResponses;
	}
	
	
	
}
