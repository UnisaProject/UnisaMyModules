package za.ac.unisa.lms.tools.broadbandrequest.actions;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.LookupDispatchAction;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.tool.api.ToolManager;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserDirectoryService;
import org.sakaiproject.component.cover.ServerConfigurationService;

import Sfbbb10h.Abean.Sfbbb10sMnt3gTransactions;

import za.ac.unisa.lms.tools.broadbandrequest.dao.BroadbandRequestDao;
import za.ac.unisa.lms.tools.broadbandrequest.forms.BroadbandRequestForm;
import za.ac.unisa.lms.tools.broadbandrequest.forms.PackageRequest;
import za.ac.unisa.lms.tools.broadbandrequest.forms.ServiceProviderCost;
import za.ac.unisa.lms.tools.broadbandrequest.forms.Student;

import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.dao.Gencod;
import za.ac.unisa.lms.dao.StudentSystemGeneralDAO;

public class BroadbandRequestAction extends LookupDispatchAction{
	
	private SessionManager sessionManager;
	private UserDirectoryService userDirectoryService;
	private ToolManager toolManager;

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
		// TODO Auto-generated method stub
		Map map = new HashMap();
		map.put("initial", "initial");	
		map.put("viewRequest", "viewRequest");
		map.put("confirmCancellation", "confirmCancellation");
		map.put("saveRequest","saveRequest");		
		map.put("requestPackageStep1","requestPackageStep1");
		map.put("cancelRequest", "cancelRequest");
		map.put("button.back", "prevStep");
		map.put("button.continue", "nextStep");
		map.put("button.acceptConfirm", "saveRequest");
		map.put("button.cancelRequest", "initial");
		map.put("button.ok", "initial");
		return map;
	}
	
	public ActionForward initial(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		BroadbandRequestForm requestForm = (BroadbandRequestForm) form;
		ActionMessages messages = new ActionMessages();
		
		 // Get user
		String userId = "";
		String userEid = "";
		User user = (User)request.getAttribute("user");
		sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
	    userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
		if(user == null) {
			Session currentSession = sessionManager.getCurrentSession();
			userId = currentSession.getUserId();
			if (userId != null) {
				user = userDirectoryService.getUser(userId);
				userEid = user.getEid();
				request.setAttribute("user",user);
			}
		} else {
			request.setAttribute("user",user);
			userEid = user.getEid();
		}
		
		requestForm.setUserAccess("view");
		BroadbandRequestDao dao = new BroadbandRequestDao();
		
		if (user != null) {
			if ("Student".equalsIgnoreCase(user.getType())){
				// Student
				if (!isInteger(user.getEid())){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"You are not authorised to access this tool, invalid student number."));
				}
				requestForm.setStudentNr(Integer.parseInt(user.getEid()));
				Student student = new Student();		
				student = dao.getStudent(requestForm.getStudentNr());
				if (student.getNumber()==null){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"You are not authorised to access this tool, invalid student number."));
				}else{
					requestForm.setUserAccess("update");
					requestForm.setStudent(student);
				}
			}else{
				return mapping.findForward("inputStudent");
			}
		}
		else {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"You are not authorised to access this tool, unknown user."));
		}
		
		if (!messages.isEmpty()) {
			addErrors(request,messages);
			return mapping.findForward("errorPage");
		}
		
		//get current/previous request and Activications
		List<PackageRequest> listRequest = new ArrayList<PackageRequest>();
		listRequest=dao.getStudentRequestList(requestForm.getStudentNr(),"all");
		requestForm.setListPackageRequest(listRequest);	
		requestForm.setCancelMessage("");
		if (listRequest.size() > 0){
			if (listRequest.get(0).getServiceProviderCost().getSimFeeDbl()==0){
				requestForm.setCancelMessage("*** You can cancel your request until your information has been forwarded to the service provider.");
			}
			if (listRequest.get(0).getServiceProviderCost().getSimFeeDbl()!=0){
				requestForm.setCancelMessage("*** You can cancel your request until payment has been received.");
			}
		}
		
		//get all cell phone ranges
		List<Gencod> cellRanges = new ArrayList<Gencod>();
		StudentSystemGeneralDAO daoStudent = new StudentSystemGeneralDAO();
		cellRanges = daoStudent.getGenCodes((short) 65, 1);
		requestForm.setCellRanges(cellRanges);
		
		return mapping.findForward("requestList");
	}
	
	public ActionForward viewRequest(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		BroadbandRequestForm requestForm = (BroadbandRequestForm) form;
		ActionMessages messages = new ActionMessages();
		
		String year = (request.getParameter("requestYear")).trim();
		String seqNr = (request.getParameter("requestSeqNr")).trim();
		
		String found="N";
		for(int i=0; i < requestForm.getListPackageRequest().size(); i++){
			PackageRequest record = new PackageRequest();
			record = (PackageRequest)requestForm.getListPackageRequest().get(i);
			if (record.getYear()==Integer.parseInt(year) &&
				record.getSequenceNr()==Integer.parseInt(seqNr)){
				requestForm.setRequest(record);
				found="Y";
				i = requestForm.getListPackageRequest().size();
			}
		}
		
		if (found.equalsIgnoreCase("N")){
			mapping.findForward("cancel"); //reset - relist requests
		}else{
			requestForm.setAmountDueDbl(requestForm.getRequest().getServiceProviderCost().getSimFeeDbl());
			if (requestForm.getRequest().getModemFlag().equalsIgnoreCase("Y")){
				requestForm.setAmountDueDbl(requestForm.getAmountDueDbl() + requestForm.getRequest().getServiceProviderCost().getModemFeeDbl());
			}
			requestForm.setAmountDueStr(String.format("R%.2f", requestForm.getAmountDueDbl()));	
		}
		//check if T and C document exists - only display if exists.
		String path = getServlet().getServletContext().getInitParameter("applicationFullPath")+"/";
		String spFilename = requestForm.getRequest().getServiceProviderCost().getSpCode() + "TermsAndConditions.htm";
		String filename = path + spFilename;
		String serverPath = ServerConfigurationService.getToolUrl();
		String url="";
		if (serverPath.contains("myqa")){			
			url = "https://myqa.int.unisa.ac.za/broadband/" + spFilename;
		}else if (serverPath.contains("mydev") || serverPath.contains("localhost")){
			url = "https://mydev.int.unisa.ac.za/broadband/" + spFilename;
		}else{
			url = "https://my.unisa.ac.za/broadband/" + spFilename;
		}
		
		requestForm.setTcsUrl(url);
		String tcsExists="N";
		try{
			FileInputStream input = new FileInputStream(new File(filename));
			tcsExists="Y";
			input.close();
		}catch (Exception ex) {
			tcsExists="N";			
		}	
		
		request.setAttribute("tcsExists", "tcsExists");
		return mapping.findForward("viewRequest");
	}
	
	public ActionForward confirmCancellation(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		BroadbandRequestForm requestForm = (BroadbandRequestForm) form;
		ActionMessages messages = new ActionMessages();
		
		String year = (request.getParameter("requestYear")).trim();
		String seqNr = (request.getParameter("requestSeqNr")).trim();
		
		String found="N";
		for(int i=0; i < requestForm.getListPackageRequest().size(); i++){
			PackageRequest record = new PackageRequest();
			record = (PackageRequest)requestForm.getListPackageRequest().get(i);
			if (record.getYear()==Integer.parseInt(year) &&
				record.getSequenceNr()==Integer.parseInt(seqNr)){
				requestForm.setRequest(record);
				found="Y";
				i = requestForm.getListPackageRequest().size();
			}
		}
		
		if (found.equalsIgnoreCase("N")){
			mapping.findForward("cancel"); //reset - relist requests
		}else{
			requestForm.setAmountDueDbl(requestForm.getRequest().getServiceProviderCost().getSimFeeDbl());
			if (requestForm.getRequest().getModemFlag().equalsIgnoreCase("Y")){
				requestForm.setAmountDueDbl(requestForm.getAmountDueDbl() + requestForm.getRequest().getServiceProviderCost().getModemFeeDbl());
			}
			requestForm.setAmountDueStr(String.format("R%.2f", requestForm.getAmountDueDbl()));	
		}
		//check if T and C document exists - only display if exists.
		String path = getServlet().getServletContext().getInitParameter("applicationFullPath")+"/";
		String spFilename = requestForm.getRequest().getServiceProviderCost().getSpCode() + "TermsAndConditions.htm";
		String filename = path + spFilename;
		String serverPath = ServerConfigurationService.getToolUrl();
		String url="";
		if (serverPath.contains("myqa")){			
			url = "https://myqa.int.unisa.ac.za/broadband/" + spFilename;
		}else if (serverPath.contains("mydev") || serverPath.contains("localhost")){
			url = "https://mydev.int.unisa.ac.za/broadband/" + spFilename;
		}else{
			url = "https://my.unisa.ac.za/broadband/" + spFilename;
		}
				
		requestForm.setTcsUrl(url);
		String tcsExists="N";
		try{
			FileInputStream input = new FileInputStream(new File(filename));
			tcsExists="Y";
			input.close();
		}catch (Exception ex) {
			tcsExists="N";			
		}	
				
		request.setAttribute("tcsExists", "tcsExists");
		return mapping.findForward("confirmCancellation");
	}
	
	public ActionForward cancelRequest(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		BroadbandRequestForm requestForm = (BroadbandRequestForm) form;
		ActionMessages messages = new ActionMessages();
		
		BroadbandRequestDao dao = new BroadbandRequestDao();
		
		dao.cancelRequest(requestForm.getStudentNr() , requestForm.getRequest());
		
		//get current/previous request and Activications
		List<PackageRequest> listRequest = new ArrayList<PackageRequest>();
		listRequest=dao.getStudentRequestList(requestForm.getStudentNr(),"all");
		requestForm.setListPackageRequest(listRequest);	
		requestForm.setCancelMessage("");
		if (listRequest.get(0).getServiceProviderCost().getSimFeeDbl()==0){
			requestForm.setCancelMessage("*** You can cancel your request until your information has been forwarded to the service provider.");
		}
		if (listRequest.get(0).getServiceProviderCost().getSimFeeDbl()!=0){
			requestForm.setCancelMessage("*** You can cancel your request until payment has been received.");
		}
		return mapping.findForward("requestList");
	}
	
	public ActionForward saveRequest(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		BroadbandRequestForm requestForm = (BroadbandRequestForm) form;
		ActionMessages messages = new ActionMessages();
		
		
		if (requestForm.getRequest().getServiceProviderCost().getSpCode().equalsIgnoreCase("XXX")){
			if (requestForm.getRequest().getRegionalCentre()==null ||
					requestForm.getRequest().getRegionalCentre().getCode()==null ||
					requestForm.getRequest().getRegionalCentre().getCode().equalsIgnoreCase("") ||
					requestForm.getRequest().getRegionalCentre().getCode().endsWith("None")){
						messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"Please select a regional centre from where you wish to collect your sim card and/or modem."));
					}else{
						//get the regional centre selected
						for(int i=0; i < requestForm.getListRegionalCentre().size(); i++){
							Gencod gencod= new Gencod();
							gencod = (Gencod)requestForm.getListRegionalCentre().get(i);
							if (gencod.getCode().equalsIgnoreCase(requestForm.getRequest().getRegionalCentre().getCode())){
								requestForm.getRequest().setRegionalCentre(gencod);
								i= requestForm.getListRegionalCentre().size();
							}
						}
					}
		}
		
		boolean testConfirmationNumber=true;
		//change SR998003 (20140714) - Telkom process should work the same as vodacom
		if (requestForm.getRequest().getServiceProviderCost().getSpCode().equalsIgnoreCase("VODACOM") || requestForm.getRequest().getServiceProviderCost().getSpCode().equalsIgnoreCase("TELKOM")){
			if (requestForm.getRequest().getCardMobileNr()==null || requestForm.getRequest().getCardMobileNr().trim().equalsIgnoreCase("")){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"The activation mobile number is required, you have to enter your prepaid mobile number before you can proceed."));
				testConfirmationNumber=false;
			}else{
				//Validate Activation Mobile Number				
				if (requestForm.getRequest().getCardMobileNr().trim().length() != 10 || !requestForm.getRequest().getCardMobileNr().trim().substring(0, 1).equalsIgnoreCase("0")){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"Invalid activation mobile number, the activation mobile number must be 10 digits long and start with a '0'."));
					testConfirmationNumber=false;
				}else {
					//Validate cell phone range
					boolean validCellRange=false;
					for (int i=0; i < requestForm.getCellRanges().size(); i++) {
						Gencod range = new Gencod();
						range = (Gencod)requestForm.getCellRanges().get(i);
						if (requestForm.getRequest().getCardMobileNr().substring(1,3).equalsIgnoreCase(range.getCode())){
							validCellRange = true;
							i = requestForm.getCellRanges().size();
						}
					}					
					if (!validCellRange){
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage",
											"Invalid activiation mobile number, the cell phone range, "  + requestForm.getRequest().getCardMobileNr().substring(0,3) +  " is invalid"));
						testConfirmationNumber=false;
					//validate that mobile numbers match	
					}else if(requestForm.getConfirmActivationMobileNr()==null || requestForm.getConfirmActivationMobileNr().trim().equalsIgnoreCase("")){
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage",
											"Please re-type your activation mobile number for verification purposes."));
						testConfirmationNumber=false;
					}
				}
//				if (requestForm.getRequest().getCardMobileNr().trim().length() != 10 || !requestForm.getRequest().getCardMobileNr().trim().substring(0, 1).equalsIgnoreCase("0")){
//					messages.add(ActionMessages.GLOBAL_MESSAGE,
//							new ActionMessage("message.generalmessage",
//										"Invalid activation mobile number, the activation mobile number must be 10 digits long and start with a '0'."));
//					testConfirmationNumber=false;
//				}else if(requestForm.getConfirmActivationMobileNr()==null || requestForm.getConfirmActivationMobileNr().trim().equalsIgnoreCase("")){
//					messages.add(ActionMessages.GLOBAL_MESSAGE,
//							new ActionMessage("message.generalmessage",
//										"Please re-type your activation mobile number for verification purposes."));
//					testConfirmationNumber=false;
//				}
			}	
			if (testConfirmationNumber) {
				if (!requestForm.getRequest().getCardMobileNr().trim().equalsIgnoreCase(requestForm.getConfirmActivationMobileNr().trim())){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"The activation mobile numbers entered do not match."));
				}
				
			}
		}
		
		if (requestForm.getRequest().getTermsConditionFlag()==null || 
				!requestForm.getRequest().getTermsConditionFlag().equalsIgnoreCase("Y")){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"You have to accept the terms and conditions before you can proceed."));
		}
		
		if (requestForm.getRequest().getContactDetialFlag()==null || 
				!requestForm.getRequest().getContactDetialFlag().equalsIgnoreCase("Y")){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"You have to agree that your cell phone number and personal details can be provided to the service provider before you can proceed."));
		}
		
		if (!messages.isEmpty()) {
			addErrors(request,messages);
			requestForm.setResetArraysStep2(true);
			return mapping.findForward("requestPackageStep2");
		}
		
		//create STU3G record and audit trail records
		//Add code if student manage to click on Accept and Confirm twice - Start
		//get latest request record 
		PackageRequest latestRequest = new PackageRequest();
		List<PackageRequest> list = new ArrayList<PackageRequest>();
		BroadbandRequestDao dao = new BroadbandRequestDao();
		list = dao.getStudentRequestList(requestForm.getStudentNr(),"latest");
		if (list.size()> 0){
			latestRequest = (PackageRequest)list.get(0);
			if (latestRequest.getStatus().equalsIgnoreCase("NC")){
				//request not cancelled
				if (latestRequest.getPaymentDate()==null ||
						latestRequest.getPaymentDate().equalsIgnoreCase("") ||
						latestRequest.getPaymentDate().equalsIgnoreCase("00010101")){
					//not yet paid
					if (latestRequest.getServiceProviderCost().getSimFeeDbl()==0){
						return mapping.findForward("paymentAtSpInfo");
					}else{
						return mapping.findForward("paymentInformation");
					}				
				}else{
					//payment received
					if (latestRequest.getInfoToSpDate()==null ||
							latestRequest.getInfoToSpDate().equalsIgnoreCase("") ||
							latestRequest.getInfoToSpDate().equalsIgnoreCase("00010101")){
						//not send to Service Provider
								messages.add(ActionMessages.GLOBAL_MESSAGE,
										new ActionMessage("message.generalmessage",
											"Your request has already been paid."));
						//get current/previous request and Activications
						requestForm.setSubmitCounter(requestForm.getSubmitCounter() + 1);		
						List<PackageRequest> listRequest = new ArrayList<PackageRequest>();
						listRequest=dao.getStudentRequestList(requestForm.getStudentNr(),"all");
						requestForm.setListPackageRequest(listRequest);	
						requestForm.setCancelMessage("");
						if (listRequest.size() > 0){
							if (listRequest.get(0).getServiceProviderCost().getSimFeeDbl()==0){
								requestForm.setCancelMessage("*** You can cancel your request until your information has been forwarded to the service provider.");
							}
							if (listRequest.get(0).getServiceProviderCost().getSimFeeDbl()!=0){
								requestForm.setCancelMessage("*** You can cancel your request until payment has been received.");
							}
						}				
					}
				}							
			}
		}			
		if (!messages.isEmpty()) {
			addErrors(request,messages);
			requestForm.setResetArraysStep2(true);
			return mapping.findForward("requestPackageStep2");
		}		
		//Add code if student manage to click on Accept and Confirm twice - End
		if (requestForm.getRequest().getTransferFundsFlag()!=null && requestForm.getRequest().getTransferFundsFlag().equalsIgnoreCase("Y")){
			boolean busy = dao.isBatchWorkBusy();
			if (busy){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
							"System updates are being run so no transactions are possible at this moment - please try again later."));
				addErrors(request,messages);
				requestForm.setResetArraysStep2(true);
				return mapping.findForward("requestPackageStep2");
			}
		}
		//double check student click submit button twice - if previous check fail
		requestForm.setSubmitCounter(requestForm.getSubmitCounter() + 1);
		if (requestForm.getSubmitCounter() > 1){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
						"Your request has already been processed, you press the Accept and Confirm button twice."));
			addErrors(request,messages);
			requestForm.setResetArraysStep2(true);
			return mapping.findForward("requestPackageStep2");
		}
		
		int calendar_period = dao.createStudentPackageRequest(requestForm.getStudentNr(), requestForm.getRequest());
		
		//get current year
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy");		
		Calendar calendar = Calendar.getInstance();	
		Date currentDate=calendar.getTime();					
		int year = Integer.parseInt(formatter.format(currentDate));
		
		if (requestForm.getRequest().getTransferFundsFlag()!=null && requestForm.getRequest().getTransferFundsFlag().equalsIgnoreCase("Y")){
			//transfer money from students account to pay for package
			//get student study fee account balance
			Sfbbb10sMnt3gTransactions op = new Sfbbb10sMnt3gTransactions();		
	        operListener opl = new operListener();
	        op.addExceptionListener(opl);
	        op.clear();

		    op.setInCsfClientServerCommunicationsClientVersionNumber((short) 3);
	        op.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
	        op.setInCsfClientServerCommunicationsAction("D");
	        op.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
	        op.setInSecurityWsUserNumber(99998);
	        op.setAsStringInStudent3gMkStudentNr(String.valueOf(requestForm.getStudentNr()));
	        op.setInActionCsfStringsString3("UPD");
	        op.setAsStringInStudent3gCalendarPeriod(String.valueOf(calendar_period));
	        op.setAsStringInStudent3gMkAcademicYear(String.valueOf(year)); //Change 20140120 (Johanet)
	        
	        op.execute();
	        if (opl.getException() != null) throw opl.getException();
	        if (op.getExitStateType() < 3) throw new Exception(op.getExitStateMsg());

	        String Errormsg = op.getOutCsfStringsString500();
	        if ((Errormsg != null) && (!Errormsg.equals(""))) {
	        	//messages.add(ActionMessages.GLOBAL_MESSAGE,
	        	//		new ActionMessage("errors.coolgenerror", Errormsg));
	        	messages.add(ActionMessages.GLOBAL_MESSAGE,
        			new ActionMessage("message.generalmessage", Errormsg));
	        	addErrors(request, messages);
	        	requestForm.setResetArraysStep2(true);
	        	return mapping.findForward("requestPackageStep2");
	        }
	        
	        if (op.getOutWsMethodResultReturnCode().trim().equalsIgnoreCase("")){
	        	request.setAttribute("paymentReference", op.getOutPaymentReferenceCsfStringsString17());	        	
	        	String balanceAfterTransfer = String.format("R%.2f",op.getOutAvailableBalanceStudentAccountBalance());
	        	request.setAttribute("balanceAfterTransfer", balanceAfterTransfer);
	        	return mapping.findForward("paymentConfirmation");
	        }
		}		
		
		if (requestForm.getRequest().getServiceProviderCost().getSimFeeDbl()==0){
			return mapping.findForward("paymentAtSpInfo");
		}else{
			return mapping.findForward("paymentInformation");
		}
		
	}
	
	public ActionForward requestPackageStep1(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		BroadbandRequestForm requestForm = (BroadbandRequestForm) form;
		ActionMessages messages = new ActionMessages();	
		
		BroadbandRequestDao dao = new BroadbandRequestDao();
		
		//Offer only available to registered students for formal courses
		boolean isRegistered = false;
		isRegistered = dao.isStudentRegisteredForFormalCourse(requestForm.getStudentNr());
		if (!isRegistered){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"This offer is only available to students registered for formal courses."));
		}
		
		//Offer only available for students in SA
		if (!requestForm.getStudent().getCountryCode().equalsIgnoreCase("1015")){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"This offer is not available to foreign students."));
		}		
		
		//A renewal or new contract can only be requested 5 months after activation of the previous request.
		if (requestForm.getListPackageRequest()!=null && !requestForm.getListPackageRequest().isEmpty()){
			//get latest request record
			PackageRequest latestRequest = new PackageRequest();
			List<PackageRequest> list = new ArrayList<PackageRequest>();
			list = dao.getStudentRequestList(requestForm.getStudentNr(),"latest");
			if (list.size()> 0){
				latestRequest = (PackageRequest)list.get(0);
				if (latestRequest.getStatus().equalsIgnoreCase("NC")){
					//request not cancelled
					if (latestRequest.getPaymentDate()==null ||
							latestRequest.getPaymentDate().equalsIgnoreCase("") ||
							latestRequest.getPaymentDate().equalsIgnoreCase("00010101")){
						//not yet paid
						if (latestRequest.getInfoToSpDate()==null ||
								latestRequest.getInfoToSpDate().equalsIgnoreCase("") ||
								latestRequest.getInfoToSpDate().equalsIgnoreCase("00010101")){
							//not forwarded to the service provider
							messages.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("message.generalmessage",
										"A current request is in progress.  See Current/Previous requests and Activation list below. If you want to change your 3G Package request you first have to cancel your current request. Click on the cancel link next to the current request to cancel the request, then click on the Request/Renew 3G Package link to create a new request."));	
						}else{
							//not paid
							//forwarded to the service provider
							messages.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("message.generalmessage",
										"Your request has been forwarded to the service provider."));												
						}	
					}else{
						//payment received
						if (latestRequest.getInfoToSpDate()==null ||
								latestRequest.getInfoToSpDate().equalsIgnoreCase("") ||
								latestRequest.getInfoToSpDate().equalsIgnoreCase("00010101")){
							//not send to Service Provider
									messages.add(ActionMessages.GLOBAL_MESSAGE,
											new ActionMessage("message.generalmessage",
												"Your request is in progress and will be forwarded to the service provider."));
							
						}else{
							//info send to Service provider
							if (latestRequest.getActivationDate()==null ||
									latestRequest.getActivationDate().equalsIgnoreCase("") ||
									latestRequest.getActivationDate().equalsIgnoreCase("00010101")){
								//not yet activated
								messages.add(ActionMessages.GLOBAL_MESSAGE,
										new ActionMessage("message.generalmessage",
											"Your request has been forwarded to the service provider."));
							}else{
								//package activated
								//not activated yet
								SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
								Date activationDate = formatter.parse(latestRequest.getActivationDate());
								Calendar calendar = Calendar.getInstance();
								calendar.setTime(activationDate);
								calendar.add(Calendar.MONTH,5);
								Date renewalDate = calendar.getTime();
								calendar = Calendar.getInstance();
								Date currentDate = calendar.getTime();
								if (currentDate.before(renewalDate)){
									messages.add(ActionMessages.GLOBAL_MESSAGE,
											new ActionMessage("message.generalmessage",
												"A renewal or new contract can only be requested 5 months after activation of the previous request.  Your previous request was activated on " + latestRequest.getActivationDate()));
								}
							}
						}
						
					}
				}
				
				//validation if student request a new package
				//request cancelled
				//changes Johanet 20130819 - requested by Sphiwe - allow student to request again
				//After cancelling the previously paid request, the system should provide for a week before the new request can be allowed.
				//No waiting period if request not yet send to SP
				if (latestRequest.getStatus().equalsIgnoreCase("CA")){					
					if (latestRequest.getPaymentDate()==null ||
							latestRequest.getPaymentDate().equalsIgnoreCase("") ||
							latestRequest.getPaymentDate().equalsIgnoreCase("00010101")){
						//allow student to continue
						//not yet paid
					}else if(latestRequest.getInfoToSpDate()==null ||
							latestRequest.getInfoToSpDate().equalsIgnoreCase("") ||
							latestRequest.getInfoToSpDate().equalsIgnoreCase("00010101")){	
						//allow student to continue
						//not yet send to service provider 
					}else{
						//info send to the service provider
						//allow new request after a week of cancellation if not activated
						if (latestRequest.getActivationDate()==null ||
							latestRequest.getActivationDate().equalsIgnoreCase("") ||
							latestRequest.getActivationDate().equalsIgnoreCase("00010101")){
							//allow new request a week after cancellation date
							Date cancellationDate = null;
							Date cutoffDate = null;
							SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
							cancellationDate = formatter.parse(latestRequest.getCancellationDate());
							Calendar calendar = Calendar.getInstance();
							calendar.setTime(cancellationDate);
							calendar.add(Calendar.DATE,7);					
							cutoffDate=calendar.getTime();		
							calendar = Calendar.getInstance();
							Date currentDate = calendar.getTime();
							if (currentDate.before(cutoffDate)){	
								//cannot request new package within a week of cancellation if request has already been sent to SP
								messages.add(ActionMessages.GLOBAL_MESSAGE,
										new ActionMessage("message.generalmessage",
											"If you cancelled your request after your information was sent to the service provider, a waiting period of a week is required before you can request a new package. Your request was cancelled on " + latestRequest.getCancellationDate()));
							}		
						}else{
						//package has been activated
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage",
									"Your request has been cancelled."));
						}
					}
				}			
			}	
		}	

		
		//Get all service providers
		List<Gencod> listSP = new ArrayList<Gencod>();
		List<ServiceProviderCost> listSPCost = new ArrayList<ServiceProviderCost>();
		StudentSystemGeneralDAO daoStudent = new StudentSystemGeneralDAO();
		listSP = daoStudent.getGenCodes((short) 202, 3);
		
		//get costs and populate service provider list for display
		String spOfferModem="N";
		for (int i= 0; i < listSP.size();i++){
			Gencod gencod = new Gencod();
			gencod = (Gencod)listSP.get(i);
			ServiceProviderCost spCost = new ServiceProviderCost();
			spCost = dao.getServiceProviderCost(gencod.getCode());
			if (spCost.getSpCode() != null &&
					!spCost.getSpCode().equalsIgnoreCase("")){ //&& spCost.getSimFeeDbl()!=0.00 ){  //&& spCost.getModemFeeDbl()!=0.00 ){
				//only add Service providers with available terms and conditions documents
				//check if T and C document exists if not give and error else set document name and continue
				String path = getServlet().getServletContext().getInitParameter("applicationFullPath")+"/";
				String spFilename = spCost.getSpCode() + "TermsAndConditions.htm";
				String filename = path + spFilename;
				
				try{
					FileInputStream input = new FileInputStream(new File(filename));
					listSPCost.add(spCost);
					if (spCost.getModemFeeDbl()>0){
						spOfferModem="Y";
					}
					input.close();
				}catch (Exception ex) {
					//do not at as an available Service provider					
				}			
//				if (spCost.getSpCode().equalsIgnoreCase("TELKOM") ||
//						spCost.getSpCode().equalsIgnoreCase("CELLC") ||
//						spCost.getSpCode().equalsIgnoreCase("XXX")||
//						spCost.getSpCode().equalsIgnoreCase("VODACOM")){
//				listSPCost.add(spCost);
//				}
			}
		}
		requestForm.setSpOfferModem(spOfferModem);
		requestForm.setListServiceProviderCost(listSPCost);
		
		if (!messages.isEmpty()) {
			addErrors(request,messages);
			return mapping.findForward("requestList");
		}	
		
		//reset request
		PackageRequest packageRequest = new PackageRequest();
		ServiceProviderCost serviceProviderCost = new ServiceProviderCost();
		Gencod gencod = new Gencod();
		packageRequest.setServiceProviderCost(serviceProviderCost);
		packageRequest.setRegionalCentre(gencod);
		requestForm.setSelectedServiceProviderCode(null);
		requestForm.setSelectedModemFlag("N");
		requestForm.setConfirmActivationMobileNr("");
		packageRequest.setModemFlag("N");
		packageRequest.setTermsConditionFlag("N");
		packageRequest.setTransferFundsFlag("N");		
		requestForm.setRequest(packageRequest);
		
		requestForm.setResetArraysStep1(true);
		return mapping.findForward("requestPackageStep1");
	}
	
	public ActionForward prevStep(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
		BroadbandRequestForm requestForm = (BroadbandRequestForm) form;
			String prevPage="";
			
			if (requestForm.getCurrentPage().equalsIgnoreCase("viewRequest")){
				prevPage="requestList";
			}
			if (requestForm.getCurrentPage().equalsIgnoreCase("cancelRequest")){
				prevPage="requestList";
			}
			if (requestForm.getCurrentPage().equalsIgnoreCase("requestPackageStep1")){
				prevPage="requestList";
			}	
			if (requestForm.getCurrentPage().equalsIgnoreCase("requestPackageStep2")){
				requestForm.setResetArraysStep1(true);
				prevPage="requestPackageStep1";
			}
			
			return mapping.findForward(prevPage);
			
	}
	
	public ActionForward nextStep(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
		BroadbandRequestForm requestForm = (BroadbandRequestForm) form;
			String nextPage="";
			
			if (requestForm.getCurrentPage().equalsIgnoreCase("requestPackageStep1")){
				nextPage = requestPackageStep2(mapping,form,request,response);
			}			
			
			return mapping.findForward(nextPage);
			
	}
	
	public String requestPackageStep2(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
		BroadbandRequestForm requestForm = (BroadbandRequestForm) form;
		ActionMessages messages = new ActionMessages();	
		
		if (requestForm.getSelectedServiceProviderCode()==null || requestForm.getSelectedServiceProviderCode()==null){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
						"Please select a Service Provider of choice."));		
		}
		
		if (!messages.isEmpty()) {
			addErrors(request,messages);
			requestForm.setResetArraysStep1(true);
			return "requestPackageStep1";
		}	
		//get select service provider
		for (int i=0; i<requestForm.getListServiceProviderCost().size();i++){
			ServiceProviderCost record = new ServiceProviderCost();
			record = (ServiceProviderCost)requestForm.getListServiceProviderCost().get(i);
			if (requestForm.getSelectedServiceProviderCode().equalsIgnoreCase(record.getSpCode())){
				//If the user changes the service provider, reset the 'I accept the terms and condition' checkbox and 
				//the 'I agree that my current cell phone number ...' checkbox
				if (requestForm.getRequest().getServiceProviderCost()!=null && 
						requestForm.getRequest().getServiceProviderCost().getSpCode()!=null &&
						!requestForm.getRequest().getServiceProviderCost().getSpCode().equalsIgnoreCase("") &&
						!requestForm.getRequest().getServiceProviderCost().getSpCode().equalsIgnoreCase(requestForm.getSelectedServiceProviderCode())){
					requestForm.getRequest().setTermsConditionFlag("N");
					requestForm.getRequest().setContactDetialFlag("N");
					
				}
				requestForm.getRequest().setServiceProviderCost(record);
				i = requestForm.getListServiceProviderCost().size();
			}
		}
		
		if (requestForm.getRequest().getServiceProviderCost().getModemFeeDbl()==0 && requestForm.getSelectedModemFlag().equalsIgnoreCase("Y")){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
						"The Sevice Provider that you selected do not offer the option to purchase a modem through Unisa."));		
		}
		
		//check if T and C document exists if not give and error else set document name and continue
		String path = getServlet().getServletContext().getInitParameter("applicationFullPath")+"/";
		String spFilename = requestForm.getRequest().getServiceProviderCost().getSpCode() + "TermsAndConditions.htm";
		String filename = path + spFilename;
		String serverPath = ServerConfigurationService.getToolUrl();
		String url="";
		if (serverPath.contains("myqa")){			
			url = "https://myqa.int.unisa.ac.za/broadband/" + spFilename;
		}else if (serverPath.contains("mydev") || serverPath.contains("localhost")){
			url = "https://mydev.int.unisa.ac.za/broadband/" + spFilename;
		}else{
			url = "https://my.unisa.ac.za/broadband/" + spFilename;
		}
		
		requestForm.setTcsUrl(url);
				
		
		try{
			FileInputStream input = new FileInputStream(new File(filename));
			input.close();
		}catch (Exception ex) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
						"The Terms and Condition file for this Service Provider does not exists."));		
			
		}			
		
		
		if (!messages.isEmpty()) {
			addErrors(request,messages);
			requestForm.setResetArraysStep1(true);
			return "requestPackageStep1";
		}	
		
		//If the user changes the option to purchase a modem, reset the 'I accept the terms and condition' checkbox
		if (requestForm.getRequest().getModemFlag()!=null && !requestForm.getRequest().getModemFlag().equalsIgnoreCase(requestForm.getSelectedModemFlag())){
			requestForm.getRequest().setTermsConditionFlag("N");
		}
		requestForm.getRequest().setModemFlag(requestForm.getSelectedModemFlag());
		
		requestForm.setAmountDueDbl(requestForm.getRequest().getServiceProviderCost().getSimFeeDbl());
		if (requestForm.getRequest().getModemFlag().equalsIgnoreCase("Y")){
			requestForm.setAmountDueDbl(requestForm.getAmountDueDbl() + requestForm.getRequest().getServiceProviderCost().getModemFeeDbl());
		}
		requestForm.setAmountDueStr(String.format("R%.2f", requestForm.getAmountDueDbl()));	
		
		//if Service provider is MTN get the regional offices
		if (requestForm.getRequest().getServiceProviderCost().getSpCode().equalsIgnoreCase("XXX")){
			List<Gencod> listRegionalCentre = new ArrayList<Gencod>();
			StudentSystemGeneralDAO daoStudent = new StudentSystemGeneralDAO();
			listRegionalCentre = daoStudent.getGenCodes((short) 207, 3);
			requestForm.setListRegionalCentre(listRegionalCentre);			
			if (requestForm.getRequest().getRegionalCentre().getCode()==null ||
				requestForm.getRequest().getRegionalCentre().getCode().equalsIgnoreCase("")){
				Gencod gencod = new Gencod();
				gencod.setCode("None");
				gencod.setEngDescription("None");
				requestForm.getListRegionalCentre().add(0, gencod);
				requestForm.getRequest().getRegionalCentre().setCode("None");
			}
		}				
		
		//get student study fee account balance
		//only get the student study fee account balance if student has to pay unisa
		if (requestForm.getRequest().getServiceProviderCost().getSimFeeDbl()>0){
			Sfbbb10sMnt3gTransactions op = new Sfbbb10sMnt3gTransactions();		
	        operListener opl = new operListener();
	        op.addExceptionListener(opl);
	        op.clear();

		    op.setInCsfClientServerCommunicationsClientVersionNumber((short) 3);
	        op.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
	        op.setInCsfClientServerCommunicationsAction("D");
	        op.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
	        op.setInSecurityWsUserNumber(99998);
	        op.setAsStringInStudent3gMkStudentNr(String.valueOf(requestForm.getStudentNr()));
	        op.setInActionCsfStringsString3("BAL");
	        op.execute();
	        if (opl.getException() != null) throw opl.getException();
	        if (op.getExitStateType() < 3) throw new Exception(op.getExitStateMsg());

	        String Errormsg = op.getOutCsfStringsString500();
	        if ((Errormsg != null) && (!Errormsg.equals(""))) {
//	        	messages.add(ActionMessages.GLOBAL_MESSAGE,
//	        			new ActionMessage("errors.coolgenerror", Errormsg));
	        	messages.add(ActionMessages.GLOBAL_MESSAGE,
	        			new ActionMessage("message.generalmessage", Errormsg));
	        	addErrors(request, messages);
	        	requestForm.setResetArraysStep1(true);
				return "requestPackageStep1";
	        }else{
	        	requestForm.setStudentAccountBalanceDbl(op.getOutAvailableBalanceStudentAccountBalance());
	        	String balance = String.format("R%.2f",requestForm.getStudentAccountBalanceDbl());
	        	requestForm.setStudentAccountBalanceStr(balance);
	        }        
		}		
        
		requestForm.setSubmitCounter(0);
        requestForm.setResetArraysStep2(true);
		return "requestPackageStep2";
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
