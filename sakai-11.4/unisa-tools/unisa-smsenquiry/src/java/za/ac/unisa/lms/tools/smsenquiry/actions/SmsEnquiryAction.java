package za.ac.unisa.lms.tools.smsenquiry.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.LookupDispatchAction;

import Srslf02h.Abean.*;
import Srslf03h.Abean.*;
import Srslf04h.Abean.*;
import Srslf05h.Abean.*;

import za.ac.unisa.lms.dao.Gencod;
import za.ac.unisa.lms.dao.StudentSystemGeneralDAO;
import za.ac.unisa.lms.tools.smsenquiry.forms.*;
import za.ac.unisa.lms.tools.smsenquiry.domains.*;

public class SmsEnquiryAction extends LookupDispatchAction{
	
	private class operListener implements java.awt.event.ActionListener {
		
		private Exception exception = null;
	
		operListener() {
			exception = null;
		}
	
		public Exception getException() {
			return exception;
		}
	
		public void actionPerformed(java.awt.event.ActionEvent aEvent) {
			exception = new Exception(aEvent.getActionCommand());
		}
	}

		@Override
	protected Map getKeyMethodMap() {
		Map map = new HashMap();
		map.put("initial", "initial");
		map.put("button.display","displayList");
		map.put("button.first", "displayFirst");
		map.put("button.next", "displayNext");
		map.put("button.previous", "displayPrevious");
		map.put("inputSmsBatchSearch","inputSmsBatchSearch");
		map.put("getLogEntryDetail", "getLogEntryDetail");
		map.put("button.cancel", "cancel");
		return map;
	}
		
	public ActionForward initial(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
				
		ActionMessages messages = new ActionMessages();
		SmsEnquiryForm smsForm = (SmsEnquiryForm) form;
		
		smsForm.setSearchBatchNumber("");
		smsForm.setSearchCellNumber("");
		smsForm.setSearchStudentNumber("");	
		smsForm.setSearchPersonnelNumber("");
		smsForm.setSearchResponsibilityCode("");
		smsForm.setSearchFromDate("");
		smsForm.setSearchToDate("");
		
		//Get sms status list
		List list = new ArrayList();
		StudentSystemGeneralDAO dao = new StudentSystemGeneralDAO();
		for (int i=0; i < dao.getGenCodes((short)77,1).size(); i++) {
			list.add(i, (Gencod)(dao.getGenCodes((short)77,1).get(i)));
		}
		smsForm.setListMessageStatus(list);
		
		//smsForm.setNovellUserId("PRETOJ");
		smsForm.setCurrentPage("input");	
		return mapping.findForward("inputSmsEnquiry");
	}
	
	public ActionForward cancel(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
				
		ActionMessages messages = new ActionMessages();
		SmsEnquiryForm smsForm = (SmsEnquiryForm) form;	
		
		if (smsForm.getCurrentPage().equalsIgnoreCase("logEntryDetail")){
			if (smsForm.getCurrentList().equalsIgnoreCase("batchList")){
				return mapping.findForward("displaySmsLogBatchList");	
			}else if (smsForm.getCurrentList().equalsIgnoreCase("refCellList")){
				return mapping.findForward("displaySmsLogCellRefList");	
			}else {
				return mapping.findForward("displaySmsReqBatchList");	
			}
		}else{
			return mapping.findForward("inputSmsEnquiry");	
		}		
	}
	
	public ActionForward displayList(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
				
		ActionMessages messages = new ActionMessages();
		SmsEnquiryForm smsForm = (SmsEnquiryForm) form;
		
		String nextPage="";
		if (smsForm.getCurrentPage().equalsIgnoreCase("input") ||
				smsForm.getCurrentPage().equalsIgnoreCase("refCellList")||
				smsForm.getCurrentPage().equalsIgnoreCase("batchList")){
			nextPage = displaySMSLog(mapping, form, request, response);	
		}else if (smsForm.getCurrentPage().equalsIgnoreCase("inputBatchSearch")||
				smsForm.getCurrentPage().equalsIgnoreCase("batchNumberList")){
			nextPage = displaySmsReq(mapping, form, request, response);
		}
				
		return mapping.findForward(nextPage);	
	}
	
	public ActionForward displayFirst(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
				
		ActionMessages messages = new ActionMessages();
		SmsEnquiryForm smsForm = (SmsEnquiryForm) form;
		
		String nextPage="";
		if (smsForm.getCurrentPage().equalsIgnoreCase("refCellList")){
			nextPage = displaySMSLogFirst(mapping, form, request, response);
		}else if (smsForm.getCurrentPage().equalsIgnoreCase("batchList")){
			nextPage = displaySMSLogFirst(mapping, form, request, response);			
		}else if (smsForm.getCurrentPage().equalsIgnoreCase("batchNumberList")){
			nextPage = displaySMSReqFirst(mapping, form, request, response);
		}
		
		return mapping.findForward(nextPage);	
	}
	
	public ActionForward displayNext(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
				
		ActionMessages messages = new ActionMessages();
		SmsEnquiryForm smsForm = (SmsEnquiryForm) form;
		
		String nextPage="";
		if (smsForm.getCurrentPage().equalsIgnoreCase("refCellList")){
			nextPage = displaySMSLogNext(mapping, form, request, response);
		}else if (smsForm.getCurrentPage().equalsIgnoreCase("batchList")){
			nextPage = displaySMSLogNext(mapping, form, request, response);			
		}else if (smsForm.getCurrentPage().equalsIgnoreCase("batchNumberList")){
			nextPage = displaySMSReqNext(mapping, form, request, response);
		}
		
		return mapping.findForward(nextPage);	
	}
	
	public ActionForward displayPrevious(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
				
		ActionMessages messages = new ActionMessages();
		SmsEnquiryForm smsForm = (SmsEnquiryForm) form;
		
		String nextPage="";
		if (smsForm.getCurrentPage().equalsIgnoreCase("refCellList")){
			nextPage = displaySMSLogPrevious(mapping, form, request, response);
		}else if (smsForm.getCurrentPage().equalsIgnoreCase("batchList")){
			nextPage = displaySMSLogPrevious(mapping, form, request, response);			
		}else if (smsForm.getCurrentPage().equalsIgnoreCase("batchNumberList")){
			nextPage = displaySMSReqPrevious(mapping, form, request, response);
		}
		
		return mapping.findForward(nextPage);	
	}
	
	public ActionForward getLogEntryDetail(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
				
		ActionMessages messages = new ActionMessages();
		SmsEnquiryForm smsForm = (SmsEnquiryForm) form;
		
		String selectedBatchNumber="";
		String selectedSeqNumber="";
		selectedBatchNumber=request.getParameter("batchNr");
		selectedSeqNumber=request.getParameter("sequenceNr");
		
		Srslf05sDisplaySmsStaffInfo op = new Srslf05sDisplaySmsStaffInfo();
		operListener opl = new operListener();
		op.clear();
		//Client version
		op.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
		op.setInCsfClientServerCommunicationsClientVersionNumber((short) 3); 
		op.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
		
		op.setInCsfClientServerCommunicationsAction("D");	
		op.setInSmsLogMkBatchNr(Integer.parseInt(selectedBatchNumber));
		op.setInSmsLogSequenceNr(Integer.parseInt(selectedSeqNumber));
		
		op.execute();
		if (opl.getException() != null)
			throw opl.getException();
		if (op.getExitStateType() < 3)
			throw new Exception(op.getExitStateMsg());
		
		smsForm.setSenderName(op.getOutUserNameCsfStringsString30());
		smsForm.setSenderEmail(op.getOutUserWsStaffEmailAddress());
		smsForm.setSenderPhoneNumber(op.getOutUserWsStaffContactTelno());
		
		smsForm.setBatchNumber(String.valueOf(op.getOutSmsLogMkBatchNr()));
		smsForm.setMessage(op.getOutSmsLogMessage());
		
		smsForm.setSmsStatus(op.getOutSmsMtnStatusStatus());
		smsForm.setSmsStatusDesc(op.getOutSmsMtnStatusDescription());		
		
		return mapping.findForward("displayLogEntryDetail");
	}
	public ActionForward inputSmsBatchSearch(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
				
		ActionMessages messages = new ActionMessages();
		SmsEnquiryForm smsForm = (SmsEnquiryForm) form;
		
		//default to & from date
		//to date - current date
		//from date - the day before the current day
		Calendar rightNow = Calendar.getInstance();			
		Calendar fromDate = Calendar.getInstance();		
		
		smsForm.setSmsBatchReqToDate(rightNow);
		
		fromDate.set(rightNow.get(Calendar.YEAR), rightNow.get(Calendar.MONTH),
				rightNow.get(Calendar.DATE)-1 ,0 , 0, 0);
		smsForm.setSmsBatchReqFromDate(fromDate);
		
		//convert from & to date to a string	
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		Date tempDate = new Date();
		tempDate = fromDate.getTime();
		smsForm.setSearchFromDate(formatter.format(tempDate));
		tempDate = rightNow.getTime();
		smsForm.setSearchToDate(formatter.format(tempDate));
		
		return mapping.findForward("displayinputSmsBatchSearch");
	}
	
	public String displaySmsReq(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
				
		ActionMessages messages = new ActionMessages();
		SmsEnquiryForm smsForm = (SmsEnquiryForm) form;
		
		smsForm.setSearchBatchNumber("");
		smsForm.setSearchCellNumber("");
		smsForm.setSearchStudentNumber("");	
		
		smsForm.setSearchPersonnelNumber(smsForm.getSearchPersonnelNumber().trim());
		smsForm.setSearchResponsibilityCode(smsForm.getSearchResponsibilityCode().trim());
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		
		if (smsForm.getSearchFromDate().equalsIgnoreCase("")) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"From date is required."));
		}
		if (smsForm.getSearchToDate().equalsIgnoreCase("")) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"To date is required."));
		}
		if (!smsForm.getSearchPersonnelNumber().equalsIgnoreCase("") &&
				!smsForm.getSearchResponsibilityCode().equalsIgnoreCase("")) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"The search can only be restricted on the Responsibility code or the Personnel number not both."));
			}
		if (!smsForm.getSearchPersonnelNumber().equalsIgnoreCase("")){
			if (!isInteger(smsForm.getSearchPersonnelNumber())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"Personnel number must be numeric."));
			}else{smsForm.setSearchPersonnelNumber(String.valueOf((Integer.parseInt(smsForm.getSearchPersonnelNumber()))));}
		}		
		if (!messages.isEmpty()) {
				addErrors(request,messages);
				if (smsForm.getCurrentPage().equalsIgnoreCase("inputBatchSearch")){
					return "displayinputSmsBatchSearch";
				}else{
					return "displaySmsReqBatchList";
				}				
		}
		
		//From Date
		Calendar fromDate = Calendar.getInstance();
		Date tempDate = new Date();
		try {
			tempDate= formatter.parse(smsForm.getSearchFromDate());
			fromDate.setTime(tempDate);
			smsForm.setSmsBatchReqFromDate(fromDate);
		} catch (Exception e){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"From date : Invalid date format, date must be in format YYYYMMDD."));			
		}		
		
		//To Date
		Calendar toDate = Calendar.getInstance();
		tempDate = new Date();
		try {
			tempDate=formatter.parse(smsForm.getSearchToDate());
			toDate.setTime(tempDate);
			smsForm.setSmsBatchReqToDate(toDate);
		} catch (Exception e){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"To date : Invalid date format, date must be in format YYYYMMDD."));			
		}		
		
		
		if (!messages.isEmpty()) {
			addErrors(request,messages);
			if (smsForm.getCurrentPage().equalsIgnoreCase("inputBatchSearch")){
				return "displayinputSmsBatchSearch";
			}else{
				return "displaySmsReqBatchList";
			}				
		}
		
		//set coolgen null date	
		Calendar coolgenNullDate = Calendar.getInstance();
		Date emptyDate = formatter.parse("00010101");
		coolgenNullDate.setTime(emptyDate);
		smsForm.setFirstSmsBatchReqDate(coolgenNullDate);
		smsForm.setLastSmsBatchReqDate(coolgenNullDate);		
		
		smsForm.setAction("D");
		
		String nextPage="";
		nextPage = getSMSReqData(mapping, form, request, response);
		return nextPage;
		
//		//set coolgen null date	
//		Calendar coolgenNullDate = Calendar.getInstance();
//		Date emptyDate = formatter.parse("00010101");
//		coolgenNullDate.setTime(emptyDate);
//		smsForm.setFirstSmsBatchReqDate(coolgenNullDate);
//		smsForm.setLastSmsBatchReqDate(coolgenNullDate);		
//		
//		smsForm.setAction("D");
//		
//		String nextPage="";
//		nextPage = getSMSReqData(mapping, form, request, response);
//		return mapping.findForward(nextPage);
//		
//		if (smsForm.getCurrentPage().equalsIgnoreCase("batchNumberList")){
//			if (!smsForm.getSearchPersonnelNumber().equalsIgnoreCase("") &&
//					!smsForm.getSearchResponsibilityCode().equalsIgnoreCase("")) {
//					messages.add(ActionMessages.GLOBAL_MESSAGE,
//							new ActionMessage("message.generalmessage",
//										"The search can only be restricted on the Responsibility code or the Personnel number not both."));
//				}
//				if (!smsForm.getSearchPersonnelNumber().equalsIgnoreCase("")){
//					if (!isInteger(smsForm.getSearchPersonnelNumber())){
//						messages.add(ActionMessages.GLOBAL_MESSAGE,
//								new ActionMessage("message.generalmessage",
//											"Personnel number must be numeric."));
//					}else{smsForm.setSearchPersonnelNumber(String.valueOf((Integer.parseInt(smsForm.getSearchPersonnelNumber()))));}
//				}
//			if (smsForm.getSearchFromDate().equalsIgnoreCase("")) {
//					messages.add(ActionMessages.GLOBAL_MESSAGE,
//							new ActionMessage("message.generalmessage",
//										"From date is required."));
//				}
//			if (smsForm.getSearchToDate().equalsIgnoreCase("")) {
//				messages.add(ActionMessages.GLOBAL_MESSAGE,
//						new ActionMessage("message.generalmessage",
//									"To date is required."));
//			}
//				if (!messages.isEmpty()) {
//					addErrors(request,messages);
//					return mapping.findForward("displaySmsReqBatchList");
//				}
//			Calendar tempCalendar = Calendar.getInstance();
//			Date tempDate = new Date();
//			//From Date
//			tempDate= formatter.parse(smsForm.getSearchFromDate());
//			tempCalendar.setTime(tempDate);
//			smsForm.setSmsBatchReqFromDate(tempCalendar);
//			//To Date
//			tempDate=formatter.parse(smsForm.getSearchToDate());
//			tempCalendar.setTime(tempDate);
//			smsForm.setSmsBatchReqToDate(tempCalendar);
//		} else {
//			//default to & from date
//			//to date - current date
//			//from date - the day before the current day
//			Calendar rightNow = Calendar.getInstance();			
//			Calendar fromDate = Calendar.getInstance();		
//			
//			smsForm.setSmsBatchReqToDate(rightNow);
//			
//			fromDate.set(rightNow.get(Calendar.YEAR), rightNow.get(Calendar.MONTH),
//					rightNow.get(Calendar.DATE)-1 ,0 , 0, 0);
//			smsForm.setSmsBatchReqFromDate(fromDate);
//			
//			//convert from & to date to a string			
//			Date tempDate = new Date();
//			tempDate = fromDate.getTime();
//			smsForm.setSearchFromDate(formatter.format(tempDate));
//			tempDate = rightNow.getTime();
//			smsForm.setSearchToDate(formatter.format(tempDate));
//		}
//		
//		//set coolgen null date	
//		Calendar coolgenNullDate = Calendar.getInstance();
//		Date emptyDate = formatter.parse("00010101");
//		coolgenNullDate.setTime(emptyDate);
//		smsForm.setFirstSmsBatchReqDate(coolgenNullDate);
//		smsForm.setLastSmsBatchReqDate(coolgenNullDate);		
//		
//		smsForm.setAction("D");
//		
//		String nextPage="";
//		nextPage = getSMSReqData(mapping, form, request, response);
//		return mapping.findForward(nextPage);
							
		
	}
	
	public String displaySMSReqFirst(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
				
		ActionMessages messages = new ActionMessages();
		SmsEnquiryForm smsForm = (SmsEnquiryForm) form;
		
		//set coolgen null date	
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		Calendar coolgenNullDate = Calendar.getInstance();
		Date emptyDate = formatter.parse("00010101");
		coolgenNullDate.setTime(emptyDate);
		smsForm.setFirstSmsBatchReqDate(coolgenNullDate);
		smsForm.setLastSmsBatchReqDate(coolgenNullDate);	
		
		smsForm.setAction("D");
		
		String nextPage="";
		nextPage = getSMSReqData(mapping, form, request, response);
		return nextPage;
	}
	
	public String displaySMSReqNext(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
				
		ActionMessages messages = new ActionMessages();
		SmsEnquiryForm smsForm = (SmsEnquiryForm) form;
		
		smsForm.setAction("PD");
		
		String nextPage="";
		nextPage = getSMSReqData(mapping, form, request, response);
		return nextPage;
	}
	
	public String displaySMSReqPrevious(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
				
		ActionMessages messages = new ActionMessages();
		SmsEnquiryForm smsForm = (SmsEnquiryForm) form;
		
		smsForm.setAction("PU");
		
		String nextPage="";
		nextPage = getSMSReqData(mapping, form, request, response);
		return nextPage;
	}
	
	public String getSMSReqData(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
				
		ActionMessages messages = new ActionMessages();
		SmsEnquiryForm smsForm = (SmsEnquiryForm) form;		
		
		Srslf03sListSmsBatchno op = new Srslf03sListSmsBatchno();
		operListener opl = new operListener();
		op.clear();
		//Client version
		op.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
		op.setInCsfClientServerCommunicationsClientVersionNumber((short) 3); 
		op.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
		
		op.setInCsfClientServerCommunicationsAction(smsForm.getAction());
		//set time to midnight
		smsForm.getSmsBatchReqToDate().set(Calendar.HOUR_OF_DAY, 0);
		smsForm.getSmsBatchReqToDate().set(Calendar.SECOND,0);
		smsForm.getSmsBatchReqToDate().set(Calendar.MILLISECOND,0);
		smsForm.getSmsBatchReqToDate().add(Calendar.DATE, 1);
		op.setInToDateConversionParametersTimeStamp(smsForm.getSmsBatchReqToDate());
		op.setInFromDateConversionParametersTimeStamp(smsForm.getSmsBatchReqFromDate());
		op.setInStartingSmsRequestRequestTimestamp(smsForm.getFirstSmsBatchReqDate());
		op.setInLowSmsRequestRequestTimestamp(smsForm.getFirstSmsBatchReqDate());
		op.setInHighSmsRequestRequestTimestamp(smsForm.getLastSmsBatchReqDate());
		op.setInWsStaffMkRcCode(smsForm.getSearchResponsibilityCode());
		if (smsForm.getSearchPersonnelNumber().equalsIgnoreCase("")) {
			op.setInWsStaffPersno(0);	
		}else{
			op.setInWsStaffPersno(Integer.valueOf(smsForm.getSearchPersonnelNumber()));	
		}
		
		op.execute();
		if (opl.getException() != null)
			throw opl.getException();
		if (op.getExitStateType() < 3)
			throw new Exception(op.getExitStateMsg());
		if (op.getOutCsfStringsString500() != "") {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
							op.getOutCsfStringsString500()));	
			addErrors(request,messages);
			if (smsForm.getCurrentPage().equalsIgnoreCase("batchNumberList")){
				return "displaySmsReqBatchList";
			}else if (smsForm.getCurrentPage().equalsIgnoreCase("batchList")){
				return "displaySmsLogBatchList";
			} else if (smsForm.getCurrentPage().equalsIgnoreCase("refCellList")){
				return "displaySmsLogCellRefList";
			}else {				
				return "inputSmsEnquiry";		
			}
		}	
		int countReqEntries = op.getOutReqGroupCount();
		List listSmsRequest = new ArrayList();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		
		if (countReqEntries > 0) {
			smsForm.setFirstSmsBatchReqDate(op.getOutGSmsRequestRequestTimestamp(0));
			smsForm.setLastSmsBatchReqDate(op.getOutGSmsRequestRequestTimestamp(countReqEntries - 1));
		} else {
			//set coolgen null date	
			Calendar coolgenNullDate = Calendar.getInstance();
			Date emptyDate = formatter.parse("00010101");
			coolgenNullDate.setTime(emptyDate);
			smsForm.setFirstSmsBatchReqDate(coolgenNullDate);
			smsForm.setLastSmsBatchReqDate(coolgenNullDate);
		}
		
		for (int i=0; i < countReqEntries; i++){
		SmsRequest requestEntry = new SmsRequest();
		requestEntry.setBatchNr(String.valueOf(op.getOutGSmsRequestBatchNr(i)));
		String stringDate="";			
		Date tempDate = new Date();
		if (op.getOutGSmsRequestRequestTimestamp(i) instanceof Calendar) {
			formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			tempDate = op.getOutGSmsRequestRequestTimestamp(i).getTime();
			stringDate = formatter.format(tempDate);
		}	
		requestEntry.setRequestDate(stringDate);
		requestEntry.setMessageCount(String.valueOf(op.getOutGSmsRequestMessageCnt(i)));
		requestEntry.setBudgetAmount(String.valueOf(op.getOutGSmsRequestTotalCost(i)));
		requestEntry.setMessage(op.getOutGSmsRequestSmsMsg(i));
		listSmsRequest.add(requestEntry);
		}
		
		smsForm.setPageUpFlag(op.getOutCsfClientServerCommunicationsRgvScrollUpFlag());
		smsForm.setPageDownFlag(op.getOutCsfClientServerCommunicationsRgvScrollDownFlag());
		smsForm.setListSmsRequest(listSmsRequest);	
		smsForm.setCurrentList("batchNumberList");
			
		return "displaySmsReqBatchList";
	}
	
	public String displaySMSLog(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
				
		ActionMessages messages = new ActionMessages();
		SmsEnquiryForm smsForm = (SmsEnquiryForm) form;
		
		int numberOfSeachOptionsSet=0;
		smsForm.setSearchBatchNumber(smsForm.getSearchBatchNumber().trim());
		smsForm.setSearchCellNumber(smsForm.getSearchCellNumber().trim());
		smsForm.setSearchStudentNumber(smsForm.getSearchStudentNumber().trim());
		if (!smsForm.getSearchBatchNumber().trim().equalsIgnoreCase("")){
			numberOfSeachOptionsSet = numberOfSeachOptionsSet + 1;
			if (!isInteger(smsForm.getSearchBatchNumber())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"Batch Number must be numeric."));
			}else {smsForm.setSearchBatchNumber(String.valueOf(Integer.parseInt(smsForm.getSearchBatchNumber())));}
		} 
		if (!smsForm.getSearchCellNumber().trim().equalsIgnoreCase("")){
			numberOfSeachOptionsSet = numberOfSeachOptionsSet + 1;			
		}
		if (!smsForm.getSearchStudentNumber().trim().equalsIgnoreCase("")){
			numberOfSeachOptionsSet = numberOfSeachOptionsSet + 1;
			if (!isInteger(smsForm.getSearchStudentNumber())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"Student/Reference number must be numeric."));
			}else {smsForm.setSearchStudentNumber(String.valueOf(Integer.parseInt(smsForm.getSearchStudentNumber())));}	
		} 	
		if (numberOfSeachOptionsSet==0){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"Enter search data in one of the search option."));
		}
		if (numberOfSeachOptionsSet>1) {			
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"Enter search data in one search option only."));
			}
		if (!messages.isEmpty()) {
			addErrors(request,messages);
			if (smsForm.getCurrentPage().equalsIgnoreCase("batchList")){
				return "displaySmsLogBatchList";
			} else if (smsForm.getCurrentPage().equalsIgnoreCase("refCellList")){
				return "displaySmsLogCellRefList";
			}else {				
				return "inputSmsEnquiry";			
			}
		}
		
		smsForm.setSelectedMessageStatus("A");
		smsForm.setAction("D");
		smsForm.setFirstSmsLogReferenceNumber("0");
		smsForm.setLastSmsLogReferenceNumber("0");
		smsForm.setSearchPersonnelNumber("");
		smsForm.setSearchResponsibilityCode("");
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		Date emptyDate = formatter.parse("00010101");
		calendar.setTime(emptyDate);
		smsForm.setFirstSmsLogSentDate(calendar);
		smsForm.setLastSmsLogSentDate(calendar);
		
		String nextPage="";
		nextPage = getSMSLogData(mapping, form, request, response);
		return nextPage;
		
	}	
	
	public String displaySMSLogFirst(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
				
		ActionMessages messages = new ActionMessages();
		SmsEnquiryForm smsForm = (SmsEnquiryForm) form;
		
		smsForm.setAction("D");
		
		String nextPage="";
		nextPage = getSMSLogData(mapping, form, request, response);
		return nextPage;
	}
	
	public String displaySMSLogNext(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
				
		ActionMessages messages = new ActionMessages();
		SmsEnquiryForm smsForm = (SmsEnquiryForm) form;
		
		smsForm.setAction("PD");
		
		String nextPage="";
		nextPage = getSMSLogData(mapping, form, request, response);
		return nextPage;
	}
	
	public String displaySMSLogPrevious(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
				
		ActionMessages messages = new ActionMessages();
		SmsEnquiryForm smsForm = (SmsEnquiryForm) form;
		
		smsForm.setAction("PU");
		
		String nextPage="";
		nextPage = getSMSLogData(mapping, form, request, response);
		return nextPage;
	}
	
	public String getSMSLogData(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
				
		ActionMessages messages = new ActionMessages();
		SmsEnquiryForm smsForm = (SmsEnquiryForm) form;
		
		smsForm.setSearchPersonnelNumber(smsForm.getSearchPersonnelNumber().trim());
		smsForm.setSearchResponsibilityCode(smsForm.getSearchResponsibilityCode().trim());
				
		if (!smsForm.getSearchPersonnelNumber().equalsIgnoreCase("") &&
			!smsForm.getSearchResponsibilityCode().equalsIgnoreCase("")) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"The search can only be restricted on the Responsibility code or the Personnel number not both."));
		}
		if (!smsForm.getSearchPersonnelNumber().equalsIgnoreCase("")){
			if (!isInteger(smsForm.getSearchPersonnelNumber())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"Personnel number must be numeric."));
			}else{smsForm.setSearchPersonnelNumber(String.valueOf((Integer.parseInt(smsForm.getSearchPersonnelNumber()))));}
		}
		if (!messages.isEmpty()) {
			addErrors(request,messages);
			if (smsForm.getCurrentPage().equalsIgnoreCase("batchList")){
				return "displaySmsLogBatchList";
			} else if (smsForm.getCurrentPage().equalsIgnoreCase("refCellList")){
				return "displaySmsLogCellRefList";
			}else {				
				return "inputSmsEnquiry";			
			}
		}
		
		if (!smsForm.getSearchBatchNumber().equalsIgnoreCase("")){
			Srslf02sListSmsMessages op = new Srslf02sListSmsMessages();
			operListener opl = new operListener();
			op.clear();
			//Client version
			op.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
			op.setInCsfClientServerCommunicationsClientVersionNumber((short) 3); 
			op.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
			
			op.setInCsfClientServerCommunicationsAction(smsForm.getAction());
			op.setInSmsRequestBatchNr(Integer.parseInt(smsForm.getSearchBatchNumber()));
			op.setInStartingSmsLogMkBatchNr(Integer.parseInt(smsForm.getSearchBatchNumber()));
			op.setInStartingSmsLogReferenceNr(0);
			op.setInLowSmsLogReferenceNr(Integer.parseInt(smsForm.getFirstSmsLogReferenceNumber()));
			op.setInHighSmsLogReferenceNr(Integer.parseInt(smsForm.getLastSmsLogReferenceNumber()));
			op.setInWsSmsView(smsForm.getSelectedMessageStatus());
						
			op.execute();
			if (opl.getException() != null)
				throw opl.getException();
			if (op.getExitStateType() < 3)
				throw new Exception(op.getExitStateMsg());
			if (op.getOutCsfStringsString500() != "") {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
								op.getOutCsfStringsString500()));	
				addErrors(request,messages);
				if (smsForm.getCurrentPage().equalsIgnoreCase("batchList")){
					return "displaySmsLogBatchList";
				} else if (smsForm.getCurrentPage().equalsIgnoreCase("refCellList")){
					return "displaySmsLogCellRefList";
				}else {				
					return "inputSmsEnquiry";			
				}
			}	
			int countLogEntries = op.getOutLogGroupCount();
			List listLogEntries = new ArrayList();
			
			if (countLogEntries > 0) {
				smsForm.setFirstSmsLogReferenceNumber(String.valueOf(op.getOutGSmsLogReferenceNr(0)));
				smsForm.setLastSmsLogReferenceNumber(String.valueOf(op.getOutGSmsLogReferenceNr(countLogEntries - 1)));
			} else {
				smsForm.setFirstSmsLogReferenceNumber("0");
				smsForm.setLastSmsLogReferenceNumber("0");
			}
			
			for (int i=0; i < countLogEntries; i++){
			SmsLog logentry = new SmsLog();
			logentry.setBatchNr(String.valueOf(op.getOutGSmsLogMkBatchNr(i)));
			logentry.setReferenceNr(String.valueOf(op.getOutGSmsLogReferenceNr(i)));
			logentry.setCellNr(op.getOutGSmsLogCellNr(i));
			logentry.setMessage(op.getOutGSmsLogMessage(i));
			logentry.setSequenceNr(String.valueOf(op.getOutGSmsLogSequenceNr(i)));
			logentry.setMessageStatus(op.getOutGMessageStatusDescCsfStringsString30(i));			
			listLogEntries.add(logentry);
			}
			
			smsForm.setListSmsLog(listLogEntries);
			smsForm.setBatchNumber(String.valueOf(op.getOutSmsLogMkBatchNr()));
			
			String stringDate="";			
			Date tempDate = new Date();
			if (op.getOutSmsLogSentOn() instanceof Calendar) {
				SimpleDateFormat formatter = new SimpleDateFormat("d MMMMM yyyy");
				tempDate = op.getOutSmsLogSentOn().getTime();
				stringDate = formatter.format(tempDate);
			}		
			smsForm.setDateSent(stringDate);
			if (op.getOutSmsLogMessageStatus().equalsIgnoreCase("notsend")){
				smsForm.setDateSent("Not yet sent, requested on: ".concat(smsForm.getDateSent()));
			}
			
			smsForm.setMessage(op.getOutSmsLogMessage());
			smsForm.setPageUpFlag(op.getOutCsfClientServerCommunicationsRgvScrollUpFlag());
			smsForm.setPageDownFlag(op.getOutCsfClientServerCommunicationsRgvScrollDownFlag());
			smsForm.setCurrentList("batchList");
			
			return "displaySmsLogBatchList";
		}else{
			Srslf04sListSmsStudent op = new Srslf04sListSmsStudent();
			operListener opl = new operListener();
			op.clear();
			//Client version
			op.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
			op.setInCsfClientServerCommunicationsClientVersionNumber((short) 3); 
			op.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
			
			
			op.setInCsfClientServerCommunicationsAction(smsForm.getAction());
			op.setInPassSmsLogCellNr(smsForm.getSearchCellNumber());
			if (!smsForm.getSearchStudentNumber().equalsIgnoreCase("")){
				op.setInPassSmsLogReferenceNr(Integer.parseInt((smsForm.getSearchStudentNumber())));
			}else{op.setInPassSmsLogReferenceNr(0);}
			
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
			Date emptyDate = formatter.parse("00010101");
			calendar.setTime(emptyDate);
			op.setInPassSmsLogSentOn(calendar);
			op.setInLowSmsLogSentOn(smsForm.getFirstSmsLogSentDate());
			op.setInHighSmsLogSentOn(smsForm.getLastSmsLogSentDate());
			op.setInWsSmsView(smsForm.getSelectedMessageStatus());
			op.setInWsStaffMkRcCode(smsForm.getSearchResponsibilityCode());
			if (smsForm.getSearchPersonnelNumber().equalsIgnoreCase("")) {
				op.setInWsStaffPersno(0);	
			}else{
				op.setInWsStaffPersno(Integer.valueOf(smsForm.getSearchPersonnelNumber()));	
			}
					
			op.execute();
			if (opl.getException() != null)
				throw opl.getException();
			if (op.getExitStateType() < 3)
				throw new Exception(op.getExitStateMsg());
			if (op.getOutCsfStringsString500() != "") {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
								op.getOutCsfStringsString500()));	
				addErrors(request,messages);
				if (smsForm.getCurrentPage().equalsIgnoreCase("batchList")){
					return "displaySmsLogBatchList";
				} else if (smsForm.getCurrentPage().equalsIgnoreCase("refCellList")){
					return "displaySmsLogCellRefList";
				}else {				
					return "inputSmsEnquiry";			
				}
			}	
			int countLogEntries = op.getOutLogGroupCount();
			List listLogEntries = new ArrayList();
			
			if (countLogEntries > 0) {
				smsForm.setFirstSmsLogSentDate(op.getOutGSmsLogSentOn(0));
				smsForm.setLastSmsLogSentDate(op.getOutGSmsLogSentOn(countLogEntries - 1));
			} 
			
			for (int i=0; i < countLogEntries; i++){
			SmsLog logentry = new SmsLog();
			logentry.setBatchNr(String.valueOf(op.getOutGSmsLogMkBatchNr(i)));
			logentry.setReferenceNr(String.valueOf(op.getOutGSmsLogReferenceNr(i)));
			String stringDate="";			
			Date tempDate = new Date();
			if (op.getOutGSmsLogSentOn(i) instanceof Calendar) {
				formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				tempDate = op.getOutGSmsLogSentOn(i).getTime();
				stringDate = formatter.format(tempDate);
			}		
			logentry.setSendOn(stringDate);
			logentry.setCellNr(op.getOutGSmsLogCellNr(i));
			logentry.setMessage(op.getOutGSmsLogMessage(i));
			logentry.setSequenceNr(String.valueOf(op.getOutGSmsLogSequenceNr(i)));
			logentry.setMessageStatus(op.getOutGMessageStatusDescCsfStringsString30(i));			
			listLogEntries.add(logentry);
			}
			
			smsForm.setListSmsLog(listLogEntries);
			smsForm.setReceiverName(op.getOutStaffNameCsfStringsString30());
			smsForm.setStudentNumber(String.valueOf(op.getOutSmsLogReferenceNr()));
			smsForm.setCellNumber(op.getOutSmsLogCellNr());
			smsForm.setPageUpFlag(op.getOutCsfClientServerCommunicationsRgvScrollUpFlag());
			smsForm.setPageDownFlag(op.getOutCsfClientServerCommunicationsRgvScrollDownFlag());
			
			smsForm.setCurrentList("refCellList");
			
			return "displaySmsLogCellRefList";
		}
		
	}
	
	public boolean isInteger(String stringValue) {
		try
		{
			Integer i = Integer.parseInt(stringValue);
			return true;
		}	
		catch(NumberFormatException e)
		{}
		return false;
	}
		
}
