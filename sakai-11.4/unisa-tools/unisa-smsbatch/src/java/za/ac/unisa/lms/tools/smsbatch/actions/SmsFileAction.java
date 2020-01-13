package za.ac.unisa.lms.tools.smsbatch.actions;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.fileupload.FileItem;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.LookupDispatchAction;
import org.apache.struts.util.LabelValueBean;

import Srsms02h.Abean.Srsms02sSendSmsToStudList;

import za.ac.unisa.lms.tools.smsbatch.dao.GeneralItem;
import za.ac.unisa.lms.tools.smsbatch.dao.SmsBatchDAO;
import za.ac.unisa.lms.tools.smsbatch.dao.SmsFileDAO;
import za.ac.unisa.lms.tools.smsbatch.forms.SmsBatchForm;
import za.ac.unisa.lms.domain.general.Person;
import za.ac.unisa.lms.dao.general.UserDAO;
import za.ac.unisa.utils.CellPhoneVerification;
import za.ac.unisa.lms.dao.Gencod;

public class SmsFileAction extends LookupDispatchAction {

	// --------------------------------------------------------- Instance
	// Variables

	// --------------------------------------------------------- Methods
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
	protected Map getKeyMethodMap() {
		Map map = new HashMap();
		map.put("button.back", "back");
		map.put("button.cancel", "cancel");
		map.put("button.continue", "nextStep");
		//map.put("button.send", "send");
		map.put("send", "send");
		map.put("nextStep", "nextStep");
		map.put("step1", "step1");
		map.put("button.upload", "upload");
		map.put("button.viewErrors", "viewErrors");
		map.put("testMultiAdd", "testMultiAdd");

		return map;
	}
	
	public ActionForward back(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		SmsBatchForm smsForm = (SmsBatchForm) form;

		String prevPage = "";
		if ("fileStep2".equalsIgnoreCase(request.getParameter("page"))) {
			setLists(smsForm, request);
			if (smsForm.getErrorFileName()!=null && !smsForm.getErrorFileName().trim().equalsIgnoreCase("")){
				File f1 = new File(smsForm.getErrorFileName());
			    f1.delete();
			}	
			prevPage = "step1forward";
		} else if ("viewErrors".equalsIgnoreCase(request.getParameter("page"))) {			
		   	prevPage = "step2forward";
		} 
		
		return mapping.findForward(prevPage);
	}
	
	public ActionForward step1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// Check user
		SmsBatchForm smsForm = (SmsBatchForm) form;
		Cookie[] mySiteCookies = request.getCookies();
		smsForm.setNovellUserCode("");
		if (mySiteCookies != null) {
			for (int i = 0; i < mySiteCookies.length; i++) {
				Cookie co = mySiteCookies[i];
				if ("novelluser".equalsIgnoreCase(co.getName().toString()
						.trim())) {
					smsForm.setNovellUserCode(co.getValue());
				}
			}
			if ("".equals(smsForm.getNovellUserCode())) {
				/*
				 * NB NB NB NB NB NB NB NB set user code for local dev only
				 */
				//smsForm.setNovellUserCode("DLAMIW");
				//smsForm.setNovellUserCode("PRETOJ");
				//smsForm.setNovellUserCode("PENZHE");
				return mapping.findForward("userunknown");
			}
		} else {
			return mapping.findForward("userunknown");
		}

		ActionMessages messages = new ActionMessages();
		
		Person person = new Person();
		UserDAO dao = new UserDAO();
		
		person = dao.getPerson(smsForm.getNovellUserCode());
		
		if (person.getPersonnelNumber()==null){
			return mapping.findForward("userunknown");
		}			
		
		smsForm.setPersonnelNr(Integer.parseInt(person.getPersonnelNumber()));
		smsForm.setDeptCode(Short.parseShort(person.getDepartmentCode()));		
		
		//determine if  user is a DCM user with special rights.
		//changes 20180301
		//DCM - department 804
		SmsFileDAO fileDao = new SmsFileDAO();
		smsForm.setDcmUser(false);
		smsForm.setDcmUser(fileDao.isDCMUser(smsForm.getNovellUserCode().toUpperCase()));

		/** Do validation */
		if ("-1".equals(smsForm.getRcCode()) || "".equals(smsForm.getRcCode().trim())) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"message.generalmessage",
					"Select a Responsibility code from the list."));
			addErrors(request, messages);
			return mapping.findForward("firstpage");
		}

		// Set default values
		smsForm.setReasonGc27("2 : Learner Support");
		setLists(smsForm, request);
		List controlNumberList = new ArrayList<String>();
		String controlNumber="";
		for (int i=0; i <=2; i++){
			controlNumberList.add(controlNumber);
		}
		smsForm.setControlCellNumberList(controlNumberList);

		return mapping.findForward("step1forward");
	}
		
	public ActionForward send(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		SmsBatchForm smsForm = (SmsBatchForm) form;
		if ("".equals(smsForm.getNovellUserCode())
				|| smsForm.getNovellUserCode() == null) {
			return mapping.findForward("userunknown");
		}
		ActionMessages messages = new ActionMessages();
		
		//check budget
		Srsms02sSendSmsToStudList op = new Srsms02sSendSmsToStudList();
		operListener opl = new operListener();
		op.addExceptionListener(opl);
		op.clear();

		op.setInCsfClientServerCommunicationsClientVersionNumber((short) 3);
		op.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
		op.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
		op.setInNovellCodeWsStaffEmailAddress(smsForm.getNovellUserCode()
				.toUpperCase());
		op.setInCsfClientServerCommunicationsAction("GB");
		op.setInNovellCodeWsStaffEmailAddress(smsForm.getNovellUserCode().trim().toUpperCase());
		op.setInSmsRequestMkPersNr(smsForm.getPersonnelNr());
		op.setInSmsRequestMkRcCode(smsForm.getRcCode());
		op.setInSmsRequestMessageCnt(smsForm.getMessageCount());

		/* goto coolgen server */
		op.execute();
		if (opl.getException() != null)
			throw opl.getException();
		if (op.getExitStateType() < 3)
			throw new Exception(op.getExitStateMsg());
		
		smsForm.setTotalCost(op.getOutTotalCostIefSuppliedTotalCurrency());
		smsForm.setCostPerSms(op.getOutSmsCostCostPerSms());
		if (!op.getOutErrorFlagCsfStringsString1().trim().equalsIgnoreCase("")){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"message.generalmessage",
					op.getOutCsfStringsString500()));
			addErrors(request, messages);

			return mapping.findForward("step2forward");
		}
		
		SmsFileDAO dao = new SmsFileDAO();
				
		GeneralItem genItem = new GeneralItem();
		genItem = getItem(smsForm.getReasonGc27());
		int batchNr=0;
			
		batchNr=dao.createBatch(smsForm.getStudentArray(), smsForm.getCellArray(), smsForm.getMessage(), smsForm.getPersonnelNr(), smsForm.getRcCode(),
				smsForm.getDeptCode(), smsForm.getControllCellNr(), 
				smsForm.getMessageCount(), smsForm.getTotalCost(), Integer.parseInt(genItem.getCode()),
				"FILE:" + smsForm.getFileContentType(),smsForm.getControlCellNumberList());		
		
		//update budget
		if (Integer.parseInt(genItem.getCode())==0 ||
				Integer.parseInt(genItem.getCode())==9999){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"message.generalmessage",
					"SMS batch request number " + batchNr + "  was processed successfully.  Request reason (" +
					smsForm.getReasonGc27() + ") is used for testing purposes.  SMS's will not be sent!"));
			addErrors(request, messages);
		}else{
			//update budget
			op = new Srsms02sSendSmsToStudList();
			opl = new operListener();
			op.addExceptionListener(opl);
			op.clear();

			op.setInCsfClientServerCommunicationsClientVersionNumber((short) 3);
			op.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
			op.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
			op.setInNovellCodeWsStaffEmailAddress(smsForm.getNovellUserCode().trim().toUpperCase());
			op.setInCsfClientServerCommunicationsAction("UB");			
			op.setInSmsRequestBatchNr(batchNr);
			op.setInSmsRequestMkPersNr(smsForm.getPersonnelNr());
			op.setInSmsRequestMkRcCode(smsForm.getRcCode());
			op.setInSmsRequestMessageCnt(smsForm.getMessageCount());

			op.execute();
			if (opl.getException() != null)
				throw opl.getException();
			if (op.getExitStateType() < 3)
				throw new Exception(op.getExitStateMsg());
			
			if (!op.getOutErrorFlagCsfStringsString1().trim().equalsIgnoreCase("")){
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"message.generalmessage",
						op.getOutCsfStringsString500()));
				//addErrors(request, messages);
			}
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"message.generalmessage",
					"SMS batch request number " + batchNr + " was processed successfully. Please verify the delivery status of this SMS using the Query SMS system link on the first page. Note down the SMS batch request number as this will be required as input. A SMS batch can take up to 3 hours to be sent out. "));
			addErrors(request, messages);
		}
		if (smsForm.getErrorFileName()!=null && !smsForm.getErrorFileName().trim().equalsIgnoreCase("")){
			File f1 = new File(smsForm.getErrorFileName());
		    f1.delete();
		}		
	    
		return mapping.findForward("step3forward");
	}
	
	public ActionForward upload(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		SmsBatchForm smsForm = (SmsBatchForm) form;
		if ("".equals(smsForm.getNovellUserCode())
				|| smsForm.getNovellUserCode() == null) {
			return mapping.findForward("userunknown");
		}
		ActionMessages messages = new ActionMessages();
		//Johanet 20150219 - Get valid cell phone ranges
		CellPhoneVerification verifyCell = new CellPhoneVerification();
		List<Gencod> saCellRanges = new ArrayList<Gencod>();
		saCellRanges = verifyCell.getSaCellPhoneRanges(); 
		
		for (int i=0; i < smsForm.getControlCellNumberList().size(); i++){
			String ControllCellNumber = smsForm.getControlCellNumberList().get(i).toString().trim();
			if (ControllCellNumber==null || ControllCellNumber.trim().equalsIgnoreCase("")){			
			}else{
				if (!verifyCell.isCellNumber(ControllCellNumber)){
					messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"message.generalmessage", "Invalid control cell number."));
					i = smsForm.getControlCellNumberList().size();
				}else{
					//Johanet 20150219 - Get valid cell phone ranges - check valid cell number from ranges returned
					//Johanet 20150319 - Only build in check for valid sa numbers if sa cell number
					if (verifyCell.isSaCellNumber(ControllCellNumber)){
						boolean found = false;
						for (int j=0; j < saCellRanges.size(); j++){
							if (ControllCellNumber.substring(3,5).equalsIgnoreCase(((Gencod)saCellRanges.get(j)).getCode())){
								found =true;
								j = saCellRanges.size();
							}						
						}
						if (!found){
							messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
									"message.generalmessage", "Invalid control cell number."));
							i = smsForm.getControlCellNumberList().size();
						}
					}
//					if (verifyCell.isSaCellNumber(ControllCellNumber)){
//						if (!verifyCell.validSaCellNumber(ControllCellNumber)){
//							messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
//									"message.generalmessage", "Invalid control cell number."));
//							i = smsForm.getControlCellNumberList().size();
//						}						
//					}
				}			
			}
		}
		
		
		/* check message */
		if (smsForm.getMessage() == null
				|| "".equals(smsForm .getMessage().trim())) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"message.generalmessage", "SMS message can not be empty."));			
		} else {
			smsForm.setMessage(smsForm.getMessage().trim());
		}
		if (smsForm.getMessage().length() > 160){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"message.generalmessage", "SMS message may not exceed 160 characters."));			
		}
		/* check file content */
		if (smsForm.getFileContentType() == null 
				|| "".equalsIgnoreCase(smsForm.getFileContentType())){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"message.generalmessage", "Please select the file content."));
		}
		/* check file */
		FileItem item = (FileItem)request.getAttribute("theFile");	
		if (item.getName() == null ||
				item.getName().equalsIgnoreCase("")){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"message.generalmessage", "Please select a file to upload."));
		}
		if (!messages.isEmpty()) {
			addErrors(request,messages);
			smsForm.setReasonGc27("2 : Learner Support");
		 	setLists(smsForm, request);
		 	return mapping.findForward("step1forward");
		}		
					
		long fileSize = item.getSize();
		String tempstr = item.getString();
		String contentType = item.getContentType();
		String fileName = item.getName();	
		byte[] array = item.get();		
		
		String[] recordArray = tempstr.split("\\s");	
		
		String[] remove = {""};
		List temp = new ArrayList(Arrays.asList(recordArray));
		temp.removeAll(new ArrayList(Arrays.asList(remove)));
		recordArray  = (String[]) temp.toArray(new String[temp.size()]);
		
		//changes Johanet Pretorius - 20180305
		if (smsForm.getFileContentType().equalsIgnoreCase("STUDNUM")){
			if (recordArray.length > 10000){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage","You can upload a file containing 10 000 or less student numbers."));
			}
		}
		if (smsForm.getFileContentType().equalsIgnoreCase("CELLNUM")){
			if (smsForm.isDcmUser()) {
				if (recordArray.length > 100000){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage","You can upload a file containing 100 000 or less cell phone numbers."));
				}
			}else {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage","You are not allowed to upload a file of cell phone numbers."));
			}
		}
				
		if (!contentType.equalsIgnoreCase("text/plain")){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage","You can only upload txt file types."));
		}
		
		///if (fileSize > 1048576) {
		if (fileSize > 2097152) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage","You cannot upload files greater than 2 MB (2048 KB)"));
		 }
				
		if (!messages.isEmpty()) {
			addErrors(request,messages);
			smsForm.setReasonGc27("2 : Learner Support");
		 	setLists(smsForm, request);
		 	return mapping.findForward("step1forward");
		}
		
		SmsFileDAO dao = new SmsFileDAO();
		String strCellNumbers="";
		String strStudentNumbers="";
		String strErrors="";
		int smsCount = 0;		
		int recordNr = 0;
		int invalidRecordNr = 0;
		String cellNumber="";
		String errMsg = "";
		for (int i=0 ;i < recordArray.length; i++){
			errMsg = "";
			cellNumber="";
			String recValue = recordArray[i];
			if (smsForm.getFileContentType().equalsIgnoreCase("STUDNUM")){
				//file with student numbers
				if (isInteger(recValue.trim())){
			    	  boolean validStudent = false;
			    	  validStudent = dao.isStudentNr(Integer.parseInt(recValue.trim()));
			    	  if (validStudent){
			    		 cellNumber = dao.getCellNumber(Integer.parseInt(recValue.trim()));
			    		 cellNumber = cellNumber.trim();
			    		
			    	  }else {
			    		  errMsg = "Invalid student number.";
			    	  }
			      }else{
			    	  errMsg = "Student number not numeric.";
			    }
			}else{
				//file with cell phone numbers
				cellNumber=recValue.trim();
				recValue = "0";
			}
			if (errMsg.equalsIgnoreCase("")){
				if (cellNumber==null || cellNumber.trim().equalsIgnoreCase("")){	
					errMsg = "No cell phone number.";
	    		}else{
//	   				CellPhoneVerification verifyCell = new CellPhoneVerification();		    					
	   				if (!verifyCell.isCellNumber(cellNumber)){
	   					errMsg =  "Invalid cell number.";
	   				}else{
	   					//Johanet 20150219 - Get valid cell phone ranges - check valid cell number from ranges returned
	   					//Johanet 20150319 - Only build in check for valid sa numbers if sa cell number
	   					if (verifyCell.isSaCellNumber(cellNumber)){
		   					boolean found = false;
							for (int j=0; j < saCellRanges.size(); j++){
								if (cellNumber.substring(3,5).equalsIgnoreCase(((Gencod)saCellRanges.get(j)).getCode())){
									found =true;
									j = saCellRanges.size();
								}						
							}
							if (!found){
								errMsg =  "Invalid cell number.";							
							}
	//	   					if (verifyCell.isSaCellNumber(cellNumber)){
	//	   						if (!verifyCell.validSaCellNumber(cellNumber)){
	//	   							errMsg =  "Invalid cell number.";
	//	   						}
	//	   					}
	   					}	
	   				}			
	    		}	
			}
			
			recordNr = recordNr + 1;			
			if (!errMsg.equalsIgnoreCase("")){
				strErrors = strErrors + recordArray[i] + ":" + errMsg + "~";
				invalidRecordNr = invalidRecordNr + 1;
		    }else {
		    	smsCount = smsCount + 1;
				if (strCellNumbers.equalsIgnoreCase("")){
					strStudentNumbers = recValue.trim();
					strCellNumbers = cellNumber.trim();		    					  
				}else{
					  strStudentNumbers = strStudentNumbers + "~"  + recValue.trim();
					  strCellNumbers = strCellNumbers + "~"  + cellNumber.trim();
				}		
		    }
		}		
		
		if (strCellNumbers.trim().equalsIgnoreCase("")){			
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage","Batch request can not be processed. No valid records in file."));
			addErrors(request,messages);
			smsForm.setReasonGc27("2 : Learner Support");
		 	setLists(smsForm, request);
		 	return mapping.findForward("step1forward");
		}
		
		//add control cell number
//		if (smsForm.getControllCellNr()!=null && !smsForm.getControllCellNr().trim().equalsIgnoreCase("")){
//			strCellNumbers=strCellNumbers + "~" + smsForm.getControllCellNr();
//			strStudentNumbers=strStudentNumbers + "~" + "0";
//		}
		for (int i=0; i < smsForm.getControlCellNumberList().size(); i++){
			String ControllCellNumber = smsForm.getControlCellNumberList().get(i).toString().trim();
			if (ControllCellNumber==null || ControllCellNumber.trim().equalsIgnoreCase("")){
			}else{
				strCellNumbers=strCellNumbers + "~" + ControllCellNumber;
				strStudentNumbers=strStudentNumbers + "~" + "0";
			}
			
		}
		
		String[] cellArray = strCellNumbers.split("~");	
		String[] studentArray = strStudentNumbers.split("~");		
		String[] errorsArray = strErrors.split("~");
		
		//write error file
		String errorFileName="";
		if (errorsArray.length>0){
			String path = getServlet().getServletContext().getInitParameter("applicationFullPath")+"/";
			String fileDir = path +"/";
			String time = (new java.text.SimpleDateFormat("yyyyMMddhhmmssss").format(new java.util.Date()).toString());
			errorFileName = fileDir + smsForm.getNovellUserCode() +"_"+ smsForm.getFileContentType() + "_"+ time +".txt";			
			writeErrorFile(errorsArray, errorFileName);
		}		
		
		smsForm.setErrorFileName(errorFileName);
		smsForm.setMessageCount(cellArray.length);
		smsForm.setRecordNr(recordNr);
		smsForm.setInvalidRecordNr(invalidRecordNr);
		smsForm.setFileSize(String.valueOf(fileSize) + " Bytes");
		smsForm.setFileType(contentType);
		smsForm.setFileName(fileName);
		smsForm.setCellArray(cellArray);
		smsForm.setStudentArray(studentArray);
		
		//check budget and get total cost
		Srsms02sSendSmsToStudList op = new Srsms02sSendSmsToStudList();
		operListener opl = new operListener();
		op.addExceptionListener(opl);
		op.clear();

		op.setInCsfClientServerCommunicationsClientVersionNumber((short) 3);
		op.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
		op.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
		op.setInNovellCodeWsStaffEmailAddress(smsForm.getNovellUserCode().trim().toUpperCase());
		op.setInCsfClientServerCommunicationsAction("GB");
		op.setInSmsRequestMkPersNr(smsForm.getPersonnelNr());
		op.setInSmsRequestMkRcCode(smsForm.getRcCode());
		op.setInSmsRequestMessageCnt(smsForm.getMessageCount());

		/* goto coolgen server */
		op.execute();
		if (opl.getException() != null)
			throw opl.getException();
		if (op.getExitStateType() < 3)
			throw new Exception(op.getExitStateMsg());
		
		smsForm.setTotalCost(op.getOutTotalCostIefSuppliedTotalCurrency());
		smsForm.setCostPerSms(op.getOutSmsCostCostPerSms());
		
		if (!op.getOutErrorFlagCsfStringsString1().trim().equalsIgnoreCase("")){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"message.generalmessage",
					op.getOutCsfStringsString500()));
			addErrors(request,messages);
			smsForm.setReasonGc27("2 : Learner Support");
		 	setLists(smsForm, request);
		 	return mapping.findForward("step1forward");
		}
		/*Add test to check if enough money to send sms's*/
		if(op.getOutAvailableBudgetAmountIefSuppliedTotalCurrency() < op.getOutTotalCostIefSuppliedTotalCurrency()){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
			          "message.generalmessage",
			          "SMS request cannot be processed, insufficient funds available."));
			addErrors(request, messages);
			smsForm.setReasonGc27("2 : Learner Support");
		 	setLists(smsForm, request);
		 	return mapping.findForward("step1forward");
		}

		return mapping.findForward("step2forward");
	}
	
	public ActionForward viewErrors(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
			
        return mapping.findForward("viewerrors");
    }
	
	public ActionForward cancel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		SmsBatchForm smsBatchForm = (SmsBatchForm) form;
		String prevPage = "";

		// Clear all fields
		smsBatchForm.setCanSendSms(false);
		smsBatchForm.setSmsWasSend(false);
		smsBatchForm.setRegCriteriaType("");
		smsBatchForm.setGeoCriteriaType("");
		smsBatchForm.setRegList(new ArrayList());
		smsBatchForm.setMessageCount(0);
		smsBatchForm.setControllCellNr("");
		smsBatchForm.setDataFileName("");
		smsBatchForm.setDeptCode(Short.parseShort("0"));
		smsBatchForm.setGeoSelection(new String[15]);
		smsBatchForm.setMessage("");
		smsBatchForm.setSearchCode("");
		smsBatchForm.setSearchDescription("");
		smsBatchForm.setSelectionString("");
		smsBatchForm.setRegistrationPeriod("");
		smsBatchForm.setSelectedItems("");
		smsBatchForm.setReasonGc27("2 : Learner Support");
		smsBatchForm.setFileContentType("");
		if (smsBatchForm.getErrorFileName()!=null && !smsBatchForm.getErrorFileName().trim().equalsIgnoreCase("")){
			File f1 = new File(smsBatchForm.getErrorFileName());
		    f1.delete();
		}
		prevPage = "firstpage";

		return mapping.findForward(prevPage);
	}
	
	private void setLists(SmsBatchForm smsForm, HttpServletRequest request)
	throws Exception {
		/** Set reason code list */
		SmsBatchDAO dao = new SmsBatchDAO();
		GeneralItem genItem = new GeneralItem();
		List tempReasonList = new ArrayList();
		List reasonList = new ArrayList();
		tempReasonList = dao.getGeneralCodesList(request,"27", 1);
		for (int i=0; i< tempReasonList.size();i++){
			genItem = getItem(((LabelValueBean)(tempReasonList.get(i))).getValue());
			if (genItem.getCode().equals("2") || genItem.getCode().equals("9999")) {
				reasonList.add(tempReasonList.get(i));
			}
		}
		request.setAttribute("reasonList", reasonList);
	}
	
	private GeneralItem getItem(String inputStr) {
		GeneralItem item = new GeneralItem();
		int pos = 0;

		pos = inputStr.indexOf(":");
		item.setDesc(inputStr.substring(pos + 2, inputStr.length()));
		item.setCode(inputStr.substring(0, pos - 1));
		return item;
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
	
	public static void writeErrorFile(String[] errMessages, String fileName){
		 //Create file 
		 try {
			 FileOutputStream out = new FileOutputStream(new File(fileName));
			 PrintStream ps = new PrintStream(out);
			 for (int i=0; i<errMessages.length; i++){
				 ps.print(errMessages[i]);
				 ps.println(); 		
			 }
			 ps.close();
		 } catch (Exception e) {}		
	}
}
