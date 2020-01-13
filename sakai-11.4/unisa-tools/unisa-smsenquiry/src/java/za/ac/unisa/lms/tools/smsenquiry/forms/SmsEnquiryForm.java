package za.ac.unisa.lms.tools.smsenquiry.forms;

import java.util.List;
import java.util.Calendar;
import java.util.HashMap;

import org.apache.struts.validator.ValidatorForm;

public class SmsEnquiryForm extends ValidatorForm {	
		
		private static final long serialVersionUID = 1L;
		private String currentPage;
		private String novellUserId;
		private String batchNumber;
		private String studentNumber;
		private String cellNumber;
		private String dateSent;
		private String message;
		private List listSmsLog;
		private List listSmsRequest;
		private List listMessageStatus;
		private String selectedMessageStatus;
		private String searchBatchNumber;
		private String searchStudentNumber;
		private String searchCellNumber;
		private String searchFromDate;
		private String searchToDate;
		private String searchResponsibilityCode;
		private String searchPersonnelNumber;
		private String senderName;
		private String senderEmail;
		private String senderPhoneNumber;
		private String smsStatus;
		private String smsStatusDesc;
		private String pageUpFlag;
		private String pageDownFlag;
		private String lastSmsLogReferenceNumber;
		private String firstSmsLogReferenceNumber;
		private String action;
		private String receiverName;
		private String currentList;
		private HashMap logEntryMap;
		//SMS log list
		private Calendar firstSmsLogSentDate;
		private Calendar lastSmsLogSentDate;
		//SMS request list
		private Calendar firstSmsBatchReqDate;
		private Calendar lastSmsBatchReqDate;
		private Calendar smsBatchReqFromDate;
		private Calendar smsBatchReqToDate;
		
		public String getReceiverName() {
			return receiverName;
		}
		public void setReceiverName(String receiverName) {
			this.receiverName = receiverName;
		}
		public String getAction() {
			return action;
		}
		public void setAction(String action) {
			this.action = action;
		}
		public String getLastSmsLogReferenceNumber() {
			return lastSmsLogReferenceNumber;
		}
		public void setLastSmsLogReferenceNumber(String lastSmsLogReferenceNumber) {
			this.lastSmsLogReferenceNumber = lastSmsLogReferenceNumber;
		}
		public String getFirstSmsLogReferenceNumber() {
			return firstSmsLogReferenceNumber;
		}
		public void setFirstSmsLogReferenceNumber(String firstSmsLogReferenceNumber) {
			this.firstSmsLogReferenceNumber = firstSmsLogReferenceNumber;
		}
		public String getPageUpFlag() {
			return pageUpFlag;
		}
		public void setPageUpFlag(String pageUpFlag) {
			this.pageUpFlag = pageUpFlag;
		}		
		public List getListSmsRequest() {
			return listSmsRequest;
		}
		public void setListSmsRequest(List listSmsRequest) {
			this.listSmsRequest = listSmsRequest;
		}
		public String getSearchFromDate() {
			return searchFromDate;
		}
		public void setSearchFromDate(String searchFromDate) {
			this.searchFromDate = searchFromDate;
		}
		public String getSearchToDate() {
			return searchToDate;
		}
		public void setSearchToDate(String searchToDate) {
			this.searchToDate = searchToDate;
		}
		public String getSearchResponsibilityCode() {
			return searchResponsibilityCode;
		}
		public void setSearchResponsibilityCode(String searchResponsibilityCode) {
			this.searchResponsibilityCode = searchResponsibilityCode;
		}
		public String getSearchPersonnelNumber() {
			return searchPersonnelNumber;
		}
		public void setSearchPersonnelNumber(String searchPersonnelNumber) {
			this.searchPersonnelNumber = searchPersonnelNumber;
		}
		public String getBatchNumber() {
			return batchNumber;
		}
		public void setBatchNumber(String batchNumber) {
			this.batchNumber = batchNumber;
		}
		public String getStudentNumber() {
			return studentNumber;
		}
		public void setStudentNumber(String studentNumber) {
			this.studentNumber = studentNumber;
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
		public String getCellNumber() {
			return cellNumber;
		}
		public void setCellNumber(String cellNumber) {
			this.cellNumber = cellNumber;
		}
		public String getDateSent() {
			return dateSent;
		}
		public void setDateSent(String dateSent) {
			this.dateSent = dateSent;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public List getListSmsLog() {
			return listSmsLog;
		}
		public void setListSmsLog(List listSmsLog) {
			this.listSmsLog = listSmsLog;
		}
		public List getListMessageStatus() {
			return listMessageStatus;
		}
		public void setListMessageStatus(List listMessageStatus) {
			this.listMessageStatus = listMessageStatus;
		}
		public String getSelectedMessageStatus() {
			return selectedMessageStatus;
		}
		public void setSelectedMessageStatus(String selectedMessageStatus) {
			this.selectedMessageStatus = selectedMessageStatus;
		}
		public String getSearchBatchNumber() {
			return searchBatchNumber;
		}
		public void setSearchBatchNumber(String searchBatchNumber) {
			this.searchBatchNumber = searchBatchNumber;
		}
		public String getSearchStudentNumber() {
			return searchStudentNumber;
		}
		public void setSearchStudentNumber(String searchStudentNumber) {
			this.searchStudentNumber = searchStudentNumber;
		}
		public String getSearchCellNumber() {
			return searchCellNumber;
		}
		public void setSearchCellNumber(String searchCellNumber) {
			this.searchCellNumber = searchCellNumber;
		}
		public String getSenderName() {
			return senderName;
		}
		public void setSenderName(String senderName) {
			this.senderName = senderName;
		}
		public String getSenderEmail() {
			return senderEmail;
		}
		public void setSenderEmail(String senderEmail) {
			this.senderEmail = senderEmail;
		}
		public String getSenderPhoneNumber() {
			return senderPhoneNumber;
		}
		public void setSenderPhoneNumber(String senderPhoneNumber) {
			this.senderPhoneNumber = senderPhoneNumber;
		}
		public String getSmsStatus() {
			return smsStatus;
		}
		public void setSmsStatus(String smsStatus) {
			this.smsStatus = smsStatus;
		}
		public String getSmsStatusDesc() {
			return smsStatusDesc;
		}
		public void setSmsStatusDesc(String smsStatusDesc) {
			this.smsStatusDesc = smsStatusDesc;
		}
		public String getPageDownFlag() {
			return pageDownFlag;
		}
		public void setPageDownFlag(String pageDownFlag) {
			this.pageDownFlag = pageDownFlag;
		}
		public Calendar getFirstSmsLogSentDate() {
			return firstSmsLogSentDate;
		}
		public void setFirstSmsLogSentDate(Calendar firstSmsLogSentDate) {
			this.firstSmsLogSentDate = firstSmsLogSentDate;
		}
		public Calendar getLastSmsLogSentDate() {
			return lastSmsLogSentDate;
		}
		public void setLastSmsLogSentDate(Calendar lastSmsLogSentDate) {
			this.lastSmsLogSentDate = lastSmsLogSentDate;
		}
		public Calendar getLastSmsBatchReqDate() {
			return lastSmsBatchReqDate;
		}
		public void setLastSmsBatchReqDate(Calendar lastSmsBatchReqDate) {
			this.lastSmsBatchReqDate = lastSmsBatchReqDate;
		}
		public Calendar getFirstSmsBatchReqDate() {
			return firstSmsBatchReqDate;
		}
		public void setFirstSmsBatchReqDate(Calendar firstSmsBatchReqDate) {
			this.firstSmsBatchReqDate = firstSmsBatchReqDate;
		}
		public Calendar getSmsBatchReqToDate() {
			return smsBatchReqToDate;
		}
		public void setSmsBatchReqToDate(Calendar smsBatchReqToDate) {
			this.smsBatchReqToDate = smsBatchReqToDate;
		}
		public Calendar getSmsBatchReqFromDate() {
			return smsBatchReqFromDate;
		}
		public void setSmsBatchReqFromDate(Calendar smsBatchReqFromDate) {
			this.smsBatchReqFromDate = smsBatchReqFromDate;
		}
		public HashMap getLogEntryMap() {
			return logEntryMap;
		}
		public void setLogEntryMap(HashMap logEntryMap) {
			this.logEntryMap = logEntryMap;
		}
		public String getCurrentList() {
			return currentList;
		}
		public void setCurrentList(String currentList) {
			this.currentList = currentList;
		}
				
}
