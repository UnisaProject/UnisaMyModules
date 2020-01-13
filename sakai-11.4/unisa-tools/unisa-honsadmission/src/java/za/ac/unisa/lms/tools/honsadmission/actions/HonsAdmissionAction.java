package za.ac.unisa.lms.tools.honsadmission.actions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.LookupDispatchAction;
import org.apache.struts.util.LabelValueBean;
import org.datacontract.schemas._2004._07.UniflowFindAndGetService.RetrievalResult;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.email.api.EmailService;
import org.sakaiproject.event.api.EventTrackingService;
import org.sakaiproject.event.api.UsageSessionService;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.tool.api.ToolManager;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserDirectoryService;
import org.springframework.mail.javamail.ConfigurableMimeFileTypeMap;

import za.ac.unisa.lms.dao.Gencod;
import za.ac.unisa.lms.dao.StudentSystemGeneralDAO;
import za.ac.unisa.lms.dao.general.UserDAO;
import za.ac.unisa.lms.dao.general.PersonnelDAO;
import za.ac.unisa.lms.domain.general.Person;
import za.ac.unisa.lms.tools.honsadmission.dao.HonsAdmissionDAO;
import za.ac.unisa.lms.tools.honsadmission.dao.UniflowDAO;
import za.ac.unisa.lms.tools.honsadmission.forms.*;
import za.ac.unisa.lms.tools.honsadmission.exception.*;

public class HonsAdmissionAction  extends LookupDispatchAction {
	private SessionManager sessionManager;
	private UserDirectoryService userDirectoryService;
	private ToolManager toolManager;
	private EmailService emailService;
	private EventTrackingService eventTrackingService;
	private UsageSessionService usageSessionService;
	
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
		// TODO Auto-generated method stub
		Map map = new HashMap();
		map.put("initial", "initial");
		map.put("button.display","displayReviewList");	
		map.put("button.displayStudentReferrals","displayStudentReferrals");	
		map.put("button.viewStudentReferrals","displayStudentReferrals");
		map.put("button.cancel","cancel");	
		map.put("reviewApplication", "reviewApplication");
		map.put("viewApplication", "viewApplication");
		map.put("button.back","prevPage");
		map.put("signOff", "signOff");
		map.put("getFile","getFile");		
		map.put("viewRecommendation","viewRecommendation");	
		map.put("displayStudentReferrals","displayStudentReferrals");
			
		return map;
	}
	
	public ActionForward prevPage(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
	
		HonsAdmissionForm mainForm = (HonsAdmissionForm) form;
				
		String prevPage="input";	
		
		if (mainForm.getCurrentPage().equalsIgnoreCase("reviewApplication")){
			prevPage="input";
		}
		if (mainForm.getCurrentPage().equalsIgnoreCase("reviewRecommendation")){
			prevPage="reviewApplication";
		}
		if (mainForm.getCurrentPage().equalsIgnoreCase("viewRecommendation")){
			prevPage="viewApplication";
		}
		if (mainForm.getCurrentPage().equalsIgnoreCase("viewApplication")){
			prevPage="displayStudentReferrals";
		}
		if (mainForm.getCurrentPage().equalsIgnoreCase("displayStudentReferrals")){
			prevPage="input";
		}
		
		mainForm.setCurrentPage(prevPage);
		return mapping.findForward(prevPage);
	}
	
	
	public ActionForward signOff(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
	
		HonsAdmissionForm mainForm = (HonsAdmissionForm) form;
		ActionMessages messages = new ActionMessages();		
	
		if (mainForm.getApplication().getSignOff().getRecommendation()==null || mainForm.getApplication().getSignOff().getRecommendation().equalsIgnoreCase("")){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"Please indicate your recommendation."));
		} else {
			//Application declined
			if (mainForm.getApplication().getSignOff().getRecommendation().equalsIgnoreCase("DCL")){
				if (mainForm.getRecommendationDCLComment()==null || mainForm.getRecommendationDCLComment().equalsIgnoreCase("")){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"Please indicate the reason why the student's application has been declined."));
				}else{
					int commentLen = mainForm.getRecommendationDCLComment().trim().length();
					if(commentLen > 500){
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage",
										"The comment regarding the reason the student's " +
										"application is decline, cannot exceed 500 characters. " +
										"It is currently "+(commentLen)+" characters."));
					}
				}
			}
			//Application approved (with restrictions
			if (mainForm.getApplication().getSignOff().getRecommendation().equalsIgnoreCase("AWR")){
				if (mainForm.getRecommendationAWRComment()==null || mainForm.getRecommendationAWRComment().equalsIgnoreCase("")){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"Please indicate with which restriction the application has been approved."));
				}else{
					int commentLen = mainForm.getRecommendationAWRComment().trim().length();
					if(commentLen > 500){
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage",
										"The comment regarding the restrictions with which the student's " +
										"application is approve, cannot exceed 500 characters. " +
										"It is currently "+(commentLen)+" characters."));
					}
				}
			}
		}
		if(mainForm.getApplication().getSignOff().getComment()!=null && !mainForm.getApplication().getSignOff().getComment().trim().equalsIgnoreCase("")){
			int commentLen = mainForm.getApplication().getSignOff().getComment().trim().length();
			if(commentLen > 500){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
								"The sign-off comment cannot exceed 500 characters. " +
								"It is currently "+(commentLen)+" characters."));
			}
		}
		
		if (!messages.isEmpty()) {
			addErrors(request,messages);
			return mapping.findForward("reviewApplication");
		}
		
		mainForm.getApplication().getSignOff().setRecommendationComment("");
		if (mainForm.getApplication().getSignOff().getRecommendation().trim().equalsIgnoreCase("DCL")){
			mainForm.getApplication().getSignOff().setRecommendationComment(mainForm.getRecommendationDCLComment().trim());
		}
		if (mainForm.getApplication().getSignOff().getRecommendation().trim().equalsIgnoreCase("AWR")){
			mainForm.getApplication().getSignOff().setRecommendationComment(mainForm.getRecommendationAWRComment().trim());
		}
		
		//Get Recommendation description
		StudentSystemGeneralDAO systemDao = new StudentSystemGeneralDAO();		
		Gencod gencod = new Gencod();
		gencod = systemDao.getGenCode("274", mainForm.getApplication().getSignOff().getRecommendation());
		if (gencod==null || gencod.getEngDescription()==null || gencod.getEngDescription().trim().equalsIgnoreCase("")){
			mainForm.getApplication().getSignOff().setRecommendationDesc(mainForm.getApplication().getSignOff().getRecommendation());
		}else{
			mainForm.getApplication().getSignOff().setRecommendationDesc(gencod.getEngDescription());
		}
		
		HonsAdmissionDAO dao = new HonsAdmissionDAO();
		try{
			dao.signOffApplication(mainForm.getApplication(),Integer.parseInt(mainForm.getUser().getPersonnelNumber()));
		}catch(SignOffException e){
			mainForm.setAccess("view");
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
							e.getMessage()));			
			return mapping.findForward("reviewApplication");
		}
			
		
		resetFormFields(mainForm);
		
		List<Referral> reviewList = new ArrayList<Referral>();
		
		for (int i=0; i < mainForm.getListUserSignOffAccess().size();i++){
			SignOffAccess accessRecord = (SignOffAccess) mainForm.getListUserSignOffAccess().get(i);
			reviewList.addAll(dao.getUserAppReviewList(accessRecord,mainForm.getAppPeriod()));
		}	
		mainForm.setListReview(reviewList);
		mainForm.setAccess("view");
		
		mainForm.setCurrentPage("input");
		return mapping.findForward("input");    
	}
	
	public ActionForward displayReviewList(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
	HonsAdmissionForm mainForm = (HonsAdmissionForm) form;	
	
	resetFormFields(mainForm);
	
	List<Referral> reviewList = new ArrayList<Referral>();
	HonsAdmissionDAO dao = new HonsAdmissionDAO();
	
	for (int i=0; i< mainForm.getListUserSignOffAccess().size();i++){
		SignOffAccess accessRecord = (SignOffAccess) mainForm.getListUserSignOffAccess().get(i);
		reviewList.addAll(dao.getUserAppReviewList(accessRecord,mainForm.getAppPeriod()));
	}	
	mainForm.setListReview(reviewList);
	
	mainForm.setCurrentPage("input");
	return mapping.findForward("input");   
	
	}
	
	public ActionForward reviewApplication(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		HonsAdmissionForm mainForm = (HonsAdmissionForm) form;	
		ActionMessages messages = new ActionMessages();		
		
		Referral referral = new Referral();
		int stuNr=0;
		Short choice=0;
		Short seqNr=0;		
		String type="";
		
		choice = Short.parseShort(request.getParameter("choice"));
		seqNr = Short.parseShort(request.getParameter("seqNr"));
		stuNr = Integer.parseInt(request.getParameter("stuNr"));	
		type = request.getParameter("referType").trim();
		
		for (int i=0; i < mainForm.getListReview().size();i++){
			referral = (Referral) mainForm.getListReview().get(i);
			
			if (referral.getStudent().getNumber()==stuNr
					&& referral.getChoice().compareTo(choice) ==0
					&& referral.getSeqNr().compareTo(seqNr)==0
					&& referral.getType().equalsIgnoreCase(type) ) {
					mainForm.setSelectedApplication(referral);
					i = mainForm.getListReview().size();
			}
		}			
		
		Application appl = new Application();
		appl = getApplication(mainForm.getSelectedApplication());
		mainForm.setApplication(appl);		
		
		//determine access given to user
		if (appl.getSignOff().getUser()==null || 
				appl.getSignOff().getUser().trim().equalsIgnoreCase("") ||
				appl.getSignOff().getUser().trim().equalsIgnoreCase("0")){
			mainForm.setAccess("update");
		}else{
			mainForm.setAccess("view");
			if (appl.getSignOff().getRecommendation().equalsIgnoreCase("DCL")){
				mainForm.setRecommendationDCLComment(appl.getSignOff().getRecommendationComment());
			}
			if (appl.getSignOff().getRecommendation().equalsIgnoreCase("AWR")){
				mainForm.setRecommendationAWRComment(appl.getSignOff().getRecommendationComment());
			}
		}
		
		// get unifow document list
		mainForm.getDocumentList().clear();
		ArrayList <UniflowFile> docList = new ArrayList<UniflowFile>();

//Comment out to test on local
		try {
			docList = getDocumentList(appl.getStudent().getNumber(),mainForm.isOldUniflowInUse());
		 }catch (UniflowException uex){
		    	if (mainForm.isOldUniflowInUse()){
		    		messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
								"The system containing the student documents is currently unavailable. Please take note, the old Uniflow system will be restarted daily during the following times : 09H40-10H00 and 12H40-13H00. Student M&D documents will not be available during these times."));
		    	}else{
		    		messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
								"The system containing the student documents is currently unavailable."));				
		    	}			
				if (mainForm.getUserId()!=null && mainForm.getUserId().equalsIgnoreCase("PRETOJ")){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
								"Connection exception: " + uex));
				}
				addErrors(request, messages);
				
				log.debug(uex);
				mainForm.setCurrentPage("reviewApplication");
				return mapping.findForward("reviewApplication"); 
			}
			catch (Exception e){
				throw new Exception("getDocumentList: / "+e.getMessage(), e);
			}	
		
		mainForm.setDocumentList(docList);		
		String acadHistUrl= ServerConfigurationService.getServerUrl();	
		acadHistUrl = acadHistUrl.trim() + "/unisa-findtool/default.do?sharedTool=unisa.acadhistory&SYSTEM=FIC&studNr=" + appl.getStudent().getNumber();
		request.setAttribute("acadHistUrl", acadHistUrl);
		mainForm.setPrevUnisaQuals(false);
		if (appl.getListPrevUnisaQual().size()>0){
			mainForm.setPrevUnisaQuals(true);
		}
		
		mainForm.setCurrentPage("reviewApplication");
		return mapping.findForward("reviewApplication"); 
	
	}	
	
	
	public ActionForward viewApplication(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		HonsAdmissionForm mainForm = (HonsAdmissionForm) form;
		ActionMessages messages = new ActionMessages();		
		
		mainForm.setAccess("view");
		Referral referral = new Referral();
		int stuNr=0;
		Short choice=0;
		Short seqNr=0;		
		String type="";
		
		choice = Short.parseShort(request.getParameter("choice"));
		seqNr = Short.parseShort(request.getParameter("seqNr"));
		stuNr = Integer.parseInt(request.getParameter("stuNr"));	
		type = request.getParameter("referType").trim();
		
		for (int i=0; i < mainForm.getListStudentReferrals().size();i++){
			referral = (Referral) mainForm.getListStudentReferrals().get(i);
			
			if (referral.getStudent().getNumber()==stuNr
					&& referral.getChoice().compareTo(choice) ==0
					&& referral.getSeqNr().compareTo(seqNr)==0
					&& referral.getType().equalsIgnoreCase(type) ) {
					mainForm.setSelectedApplication(referral);
					i = mainForm.getListStudentReferrals().size();
			}
		}			
		
		Application appl = new Application();
		appl = getApplication(mainForm.getSelectedApplication());
		mainForm.setApplication(appl);	
		
		if (appl.getSignOff().getRecommendation().equalsIgnoreCase("DCL")){
			mainForm.setRecommendationDCLComment(appl.getSignOff().getRecommendationComment());
		}
		if (appl.getSignOff().getRecommendation().equalsIgnoreCase("AWR")){
			mainForm.setRecommendationAWRComment(appl.getSignOff().getRecommendationComment());
		}
		
		// get unifow document list
		mainForm.getDocumentList().clear();
		ArrayList <UniflowFile> docList = new ArrayList<UniflowFile>();

//Comment out to test on local
		try {
			docList = getDocumentList(appl.getStudent().getNumber(),mainForm.isOldUniflowInUse());
		 }catch (UniflowException uex){
		    	if (mainForm.isOldUniflowInUse()){
		    		messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
								"The system containing the student documents is currently unavailable. Please take note, the old Uniflow system will be restarted daily during the following times : 09H40-10H00 and 12H40-13H00. Student M&D documents will not be available during these times."));
		    	}else{
		    		messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
								"The system containing the student documents is currently unavailable."));				
		    	}			
				if (mainForm.getUserId()!=null && mainForm.getUserId().equalsIgnoreCase("PRETOJ")){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
								"Connection exception: " + uex));
				}
				addErrors(request, messages);
				
				log.debug(uex);
				mainForm.setCurrentPage("viewApplication");
				return mapping.findForward("viewApplication"); 
			}
			catch (Exception e){
				throw new Exception("getDocumentList: / "+e.getMessage(), e);
			}	
		
		mainForm.setDocumentList(docList);	
		String acadHistUrl= ServerConfigurationService.getServerUrl();	
		acadHistUrl = acadHistUrl.trim() + "/unisa-findtool/default.do?sharedTool=unisa.acadhistory&SYSTEM=FIC&studNr=" + appl.getStudent().getNumber();
		request.setAttribute("acadHistUrl", acadHistUrl);
		mainForm.setPrevUnisaQuals(false);
		if (appl.getListPrevUnisaQual().size()>0){
			mainForm.setPrevUnisaQuals(true);
		}
		
		mainForm.setCurrentPage("viewApplication");
		return mapping.findForward("viewApplication"); 
	
	}		
	

	private ArrayList<UniflowFile> getDocumentList(int stuNr,boolean isOldUniflowInUse) throws Exception {
		
		ArrayList<UniflowFile> documentList = new ArrayList<UniflowFile>();
		List <UniflowFile> newDoclist = new ArrayList<UniflowFile>();
		List <UniflowFile> oldDoclist = new ArrayList<UniflowFile>();		
		
		UniflowDAO uniflowDao = new UniflowDAO();
		String studentNr = String.valueOf(stuNr);
		
		//mdForm.setDocumentList(dao.getDocsList(mdForm.getStudent().getStudentNumber())); //debug johanet		
		//Johanet 20131112 change start - 7 number student numbers.  Files exists on uniflow with a 7 number as well as with a 7 number padded with a 0 - change start
		if (studentNr.length()==7){
			//mdForm.setDocumentList(dao.getDocsList(mdForm.getStudent().getStudentNumber()));
			
			//Read old uniflow machine with 7 digits
			if (isOldUniflowInUse){				
				oldDoclist = uniflowDao.getDocsList(studentNr); 
				documentList.addAll(oldDoclist); 
			}
			
			//Read new uniflow machine with 7 digits			
			newDoclist = uniflowDao.getNewDocsList(studentNr);  		                           
			documentList.addAll(newDoclist);  
			String paddedStudentNumber = "";
			paddedStudentNumber = "0"+ studentNr;
			
			//Read old uniflow machine padded with a 0
			if (isOldUniflowInUse){				
				oldDoclist = uniflowDao.getDocsList(paddedStudentNumber); 
				documentList.addAll(oldDoclist); 
			}
			
			//Read new uniflow machine padded with a 0			
			newDoclist = uniflowDao.getNewDocsList(paddedStudentNumber);  		                           
			documentList.addAll(newDoclist); 
		}else{			
			Boolean readOnlyNewUniflow = false;
			
			if (!isOldUniflowInUse){
				readOnlyNewUniflow = true;
			} else {
				//If new student number do not search old uniflow machine
				if (stuNr >= 60542280 &&
						stuNr < 70000000) {
					readOnlyNewUniflow = true;
				}
			}
			
			if (!readOnlyNewUniflow) {				
				oldDoclist = uniflowDao.getDocsList(studentNr);    
				documentList.addAll(oldDoclist);                            
			}			
			
			newDoclist = uniflowDao.getNewDocsList(studentNr);  		                           
			documentList.addAll(newDoclist); 
			
		}
		//change end!
		return documentList;	
	}
	
	public ActionForward getFile(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception{


		ActionMessages messages = new ActionMessages();
		HonsAdmissionForm mainForm = (HonsAdmissionForm) form;
		UniflowDAO dao = new UniflowDAO();
		String fileName = "";
		UniflowFile uFile = null;
		
		try
		{
			// get file name
			if (request.getParameter("docUniqueId")!= null && request.getParameter("uniflowVersion")!=null){
				for (int i = 0; i < mainForm.getDocumentList().size(); i++) {
					uFile = mainForm.getDocumentList().get(i);
					//String testUniqueId = request.getParameter("docUniqueId");
					//String testVersion = request.getParameter("uniflowVersion");
					
					if (uFile.getUniqueId().equalsIgnoreCase(
							request.getParameter("docUniqueId").toString()) && uFile.getUniflowVersion().equalsIgnoreCase(request.getParameter("uniflowVersion"))){
						fileName = uFile.getShortDesc();
						break;
					}
				}
				// get specific file from uniflow
				RetrievalResult retrievalResult =  
					dao.getFileFromUniflow(
							mainForm.getDocumentList(),
							request.getParameter("docUniqueId").toString(),request.getParameter("uniflowVersion"),"");
				
				String docName = uFile.getShortDesc() +" - " +uFile.getDisplayName();
				if (retrievalResult.getDocument() == null)
				{								
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
								"Unable to read document \"" + docName + "\" from " +
								"the current location on the document system. "));
					addErrors(request, messages);
					
					log.debug("Document could not be retrieved ");
					return mapping.findForward(mainForm.getCurrentPage());
				}
				
				ConfigurableMimeFileTypeMap mimeMap = new ConfigurableMimeFileTypeMap();
				// add extension to filename
				fileName = fileName+"."+retrievalResult.getDocument().getExtension();
				// get MIME type
				String contentType = "";
				if ("IMG".equalsIgnoreCase(retrievalResult.getDocument().getExtension())){
					// set img file to tiff
				  contentType = ("image/tiff");
				}else{
				  contentType = mimeMap.getContentType(fileName);//defaults to application/octet-stream
				}
				// set header info				
			    response.reset();
				response.setContentType(contentType);
				log.debug(response.getContentType());
				response.setHeader("Pragma", "");
			    response.setHeader("Cache-control", "no-store,no-cache");  
				response.setHeader("Content-disposition","attachment; filename=" +fileName );
				log.debug("filename:"+ fileName);
			    response.setContentLength(retrievalResult.getDocument().getContents().length); 
				// write byte array to response
				ServletOutputStream out = response.getOutputStream();
				out.write(retrievalResult.getDocument().getContents());
				out.flush();
				out.close();
			}
		}
		catch (UniflowException uex)
		{
			String docName = uFile.getShortDesc() +" - " +uFile.getDisplayName();
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
						"The document " + 
						(uFile == null ? " \"Not defined\"" : ("\"" + docName + "\"")) + " cannot be retrieved currently ."));
			addErrors(request, messages);
			
			log.debug("Document could not be retrieved /" +  uex.getMessage());
			return mapping.findForward(mainForm.getCurrentPage());
		}
		
		return null;
	}
	
	public ActionForward initial(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
	HonsAdmissionForm mainForm = (HonsAdmissionForm) form;
	ActionMessages messages = new ActionMessages();
	
	//Get user details
		mainForm.setUser(null);
		User user = null;
		
		sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
	    userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
		Session currentSession = sessionManager.getCurrentSession();
		String userID  = currentSession.getUserId();
				
		if (userID != null) {
			user = userDirectoryService.getUser(userID);
			mainForm.setUserId(user.getEid().toUpperCase());
		}else{
			return mapping.findForward("notAuthorised");
		}
		
		if (user.getType()!=null && !"Instructor".equalsIgnoreCase(user.getType())){
			return mapping.findForward("notAuthorised");
		}	
				
		Person person = new Person();
		UserDAO userdao = new UserDAO();
		person = userdao.getPerson(mainForm.getUserId());	
		
		if (person.getPersonnelNumber()==null){
			//user does not have access to this function 
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"User id not found on staff record"));
			addErrors(request,messages);
			return mapping.findForward("notAuthorised");
		}		
		mainForm.setUser(person);
		//
		ArrayList<LabelValueBean> periodList = new ArrayList<LabelValueBean>();
		periodList.add(new LabelValueBean("1","1"));
		periodList.add(new LabelValueBean("2","2"));
		mainForm.setListPeriod(periodList); 
		
		//determine default year and period
		if (mainForm.getAppPeriod()==null) {
			ApplicationPeriod appPeriod = new ApplicationPeriod();
			appPeriod = getDefaultApplicationPeriod();
					
			mainForm.setAppPeriod(appPeriod);
		}	
		
		StudentSystemGeneralDAO systemDao = new StudentSystemGeneralDAO();		
		Gencod gencod = new Gencod();
		gencod = systemDao.getGenCode("251", "OLD_IN_USE");		
			
		if (gencod!=null && gencod.getEngDescription()!=null && gencod.getEngDescription().equalsIgnoreCase("Y")){
			mainForm.setOldUniflowInUse(true);
		}else{
			mainForm.setOldUniflowInUse(false);
		}
		
		HonsAdmissionDAO dao = new HonsAdmissionDAO();
		List<SignOffAccess> accessList = new ArrayList<SignOffAccess>();
		
		accessList = dao.getUserSignOffAccessList(Integer.parseInt(person.getPersonnelNumber()));
		mainForm.setListUserSignOffAccess(accessList);
		
		resetFormFields(mainForm);
		
		List<Referral> reviewList = new ArrayList<Referral>();
		
		for (int i=0; i<accessList.size();i++){
			SignOffAccess accessRecord = (SignOffAccess) accessList.get(i);
			reviewList.addAll(dao.getUserAppReviewList(accessRecord,mainForm.getAppPeriod()));
		}	
		mainForm.setListReview(reviewList);
		mainForm.setAccess("view");
		
		mainForm.setCurrentPage("input");
		return mapping.findForward("input");    
	
	}
	
	private void resetFormFields(HonsAdmissionForm mainform) 	throws Exception {		
		mainform.setStudentNr("");
		mainform.setStudentName("");
		mainform.setListStudentReferrals(null);
		mainform.setListReview(null);
		mainform.setDocumentList(new ArrayList<UniflowFile>());
		mainform.setAccess("view");
		mainform.setApplication(new Application());
		mainform.setRecommendation("");
		mainform.setRecommendationANRComment("");
		mainform.setRecommendationAWRComment("");
		mainform.setRecommendationDCLComment("");
		mainform.setSignOffComment("");
		mainform.setSelectedApplication(new Referral());
		mainform.setSelectedSignOffIndex(null);
	}
	
	public ActionForward cancel(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		return mapping.findForward("home");	
	}
	
	public ActionForward viewRecommendation(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
	HonsAdmissionForm mainForm = (HonsAdmissionForm) form;	
	
	if (mainForm.getCurrentPage().equalsIgnoreCase("viewApplication")){
		mainForm.setCurrentPage("viewRecommendation");
	}else{
		mainForm.setCurrentPage("reviewRecommendation");
	}
	return mapping.findForward("viewRecommendation");  	
	}
	
	
	public ActionForward displayStudentReferrals(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
	HonsAdmissionForm mainForm = (HonsAdmissionForm) form;
	ActionMessages messages = new ActionMessages();	
	HonsAdmissionDAO dao = new HonsAdmissionDAO();
	
	
	if (mainForm.getAppPeriodView()==null){
		ApplicationPeriod appPeriod = new ApplicationPeriod();
		appPeriod.setAcademicYear(mainForm.getAppPeriod().getAcademicYear());
		appPeriod.setPeriod(mainForm.getAppPeriod().getPeriod());
		mainForm.setAppPeriodView(appPeriod);		
	}
	
	if (mainForm.getStudentNr()!=null && 
			!mainForm.getStudentNr().trim().equalsIgnoreCase("") && 
			!mainForm.getStudentNr().equalsIgnoreCase("0")) {
		
		//get student
		mainForm.setStudentNr(mainForm.getStudentNr().trim());
		Student student = new Student();		
		
		if (isInteger(mainForm.getStudentNr())){		
			
			student = dao.getStudent(Integer.parseInt(mainForm.getStudentNr()));
			if (student==null || student.getNumber()==0){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"Student not found."));
			}
		}else{
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"Student number must be numeric."));
		}
		if (!messages.isEmpty()) {
			addErrors(request,messages); 
			mainForm.setCurrentPage("displayStudentReferrals");
			return mapping.findForward("displayStudentReferrals");   
		}		
		//get student referrals
		mainForm.setStudentName(student.getName());
		mainForm.setListStudentReferrals(dao.getStudentReferrals(mainForm.getAppPeriodView(),student.getNumber()));
		
	}
	
	mainForm.setCurrentPage("displayStudentReferrals");
	return mapping.findForward("displayStudentReferrals");   
	
	}
	
	
	private Application getApplication(Referral referral) throws Exception {
		
		Application appl = new Application();
		HonsAdmissionDAO dao = new HonsAdmissionDAO();		
		
		appl.setStudent(referral.getStudent());
		appl.setApplicationPeriod(referral.getApplicationPeriod());	
		appl.setChoice(referral.getChoice());
		
//		List<PrevUnisaQual> prevUnisaQualList = new ArrayList<PrevUnisaQual>();
//		prevUnisaQualList = dao.xxxgetPrevUnisaQualifications(stuNr);
//		appl.setListPrevUnisaQual(prevUnisaQualList);
		
		List<PrevQual> prevUnisaQualList = new ArrayList<PrevQual>();		
		prevUnisaQualList = dao.getPrevUnisaQualifications(referral.getStudent().getNumber());
		appl.setListPrevUnisaQual(prevUnisaQualList);
		
		List<PrevQual> prevOtherQualList = new ArrayList<PrevQual>();
		prevOtherQualList = dao.getPrevOtherQualifications(referral.getStudent().getNumber());
		appl.setListPrevOtherQual(prevOtherQualList);
		
		appl.setListPrevQual(prevUnisaQualList);
		appl.getListPrevQual().addAll(prevOtherQualList);
		
		List<StuAdmRef> stuAdmRefList = new ArrayList<StuAdmRef>();
		stuAdmRefList = dao.getAcademicRefferals(referral);	
		
		List<SignOff> signOffList = new ArrayList<SignOff>();
		List<Referral> referList = new ArrayList<Referral>();
		StuAdmRef stuAdmRef = new StuAdmRef();	
		
		for (int i=0; i < stuAdmRefList.size(); i++ ) {		
			
			stuAdmRef = (StuAdmRef)stuAdmRefList.get(i);		
			Referral refer = new Referral();
			SignOff signOff = new SignOff();
			Qualification qual = new Qualification();
			
			if (stuAdmRef.getSignOffPersno()!=null && !stuAdmRef.getSignOffPersno().equalsIgnoreCase("") && !stuAdmRef.getSignOffPersno().equalsIgnoreCase("0")){
				signOff = new SignOff();
				signOff.setUser(stuAdmRef.getSignOffName());
				signOff.setDate(stuAdmRef.getSignOffDate());
				signOff.setComment(stuAdmRef.getSignOffComment());
				signOff.setRecommendation(stuAdmRef.getRecommendation());
				//Get Recommendation description
				StudentSystemGeneralDAO systemDao = new StudentSystemGeneralDAO();		
				Gencod gencod = new Gencod();
				gencod = systemDao.getGenCode("274", stuAdmRef.getRecommendation());
				if (gencod==null || gencod.getEngDescription()==null || gencod.getEngDescription().trim().equalsIgnoreCase("")){
					signOff.setRecommendationDesc(stuAdmRef.getRecommendation());
				}else{
					signOff.setRecommendationDesc(gencod.getEngDescription());
				}
				signOff.setRecommendationComment(stuAdmRef.getRecommendationComment());				
				signOff.setUser(stuAdmRef.getSignOffName());
				signOff.setLevel(stuAdmRef.getReferLevel());
				signOff.setReferType(stuAdmRef.getReferType());				
				signOff.setQualification(stuAdmRef.getQualification());
				
			} else {
				signOff.setComment("");
				signOff.setDate("");			
				signOff.setRecommendation("");
				signOff.setRecommendationComment("");
				signOff.setComment("");
				signOff.setUser("");
				signOff.setLevel("");
				signOff.setReferType("");				
				signOff.setQualification(new Qualification());
			}		
			
			refer.setSeqNr(stuAdmRef.getSeqNr());
			refer.setNoteToAcademic(stuAdmRef.getNoteToAcademic());
			refer.setLevel(stuAdmRef.getReferLevel());
			refer.setType(stuAdmRef.getReferType());
			refer.setReferUser(stuAdmRef.getReferUser());
			if (isInteger(refer.getReferUser())){   
				PersonnelDAO persDao = new PersonnelDAO();
				Person person = new Person();
				person = persDao.getPersonnelFromSTAFF(Integer.parseInt(refer.getReferUser()));
				if (person!=null && person.getName()!=null){
					refer.setReferUser(person.getName());
				}else{
					refer.setReferUser("Admin Advisor");
				}
			}
			refer.setReferDate(stuAdmRef.getReferDate());
			refer.setQualCode(stuAdmRef.getQualification().getQualCode());
			refer.setSpecCode(stuAdmRef.getQualification().getSpecCode());
			qual.setQualCode(stuAdmRef.getQualification().getQualCode());
			qual.setQualDesc(stuAdmRef.getQualification().getQualDesc());
			qual.setSpecCode(stuAdmRef.getQualification().getSpecCode());
			qual.setSpecDesc(stuAdmRef.getQualification().getSpecDesc());
			appl.setQualification(qual);
			appl.setRefferal(refer);
			referList.add(refer);
			appl.setSignOff(signOff);
			signOffList.add(signOff);
		}	
			
		appl.setListReferral(referList);		
		appl.setListSignOff(signOffList);
		return appl;
	}
	
	
	public ApplicationPeriod getDefaultApplicationPeriod() {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		int month;
		ApplicationPeriod applPeriod = new ApplicationPeriod();
		month = Calendar.getInstance().get(Calendar.MONTH);
		month++; //Calender - January = 0
		switch (month) {
		case 1:			
			applPeriod.setAcademicYear(Short.parseShort(String.valueOf(year)));
			applPeriod.setPeriod(Short.parseShort("1"));
			break;
		case 2:
			applPeriod.setAcademicYear(Short.parseShort(String.valueOf(year)));
			applPeriod.setPeriod(Short.parseShort("1"));
			break;
		case 3:
			applPeriod.setAcademicYear(Short.parseShort(String.valueOf(year)));
			applPeriod.setPeriod(Short.parseShort("1"));
			break;
		case 4:
			applPeriod.setAcademicYear(Short.parseShort(String.valueOf(year)));
			applPeriod.setPeriod(Short.parseShort("2"));
			break;
		case 5:
			applPeriod.setAcademicYear(Short.parseShort(String.valueOf(year)));
			applPeriod.setPeriod(Short.parseShort("2"));
			break;
		case 6:
			applPeriod.setAcademicYear(Short.parseShort(String.valueOf(year)));
			applPeriod.setPeriod(Short.parseShort("2"));
			break;
		case 7:
			applPeriod.setAcademicYear(Short.parseShort(String.valueOf(year)));
			applPeriod.setPeriod(Short.parseShort("2"));
			break;
		case 8:	
			year=year + 1;
			applPeriod.setAcademicYear(Short.parseShort(String.valueOf(year)));
			applPeriod.setPeriod(Short.parseShort("1"));
			break;
		case 9:
			year=year + 1;
			applPeriod.setAcademicYear(Short.parseShort(String.valueOf(year)));
			applPeriod.setPeriod(Short.parseShort("1"));
			break;
		case 10:
			year=year + 1;
			applPeriod.setAcademicYear(Short.parseShort(String.valueOf(year)));
			applPeriod.setPeriod(Short.parseShort("1"));
			break;
		case 11:
			year=year + 1;
			applPeriod.setAcademicYear(Short.parseShort(String.valueOf(year)));
			applPeriod.setPeriod(Short.parseShort("1"));
			break;
		case 12:
			year=year + 1;
			applPeriod.setAcademicYear(Short.parseShort(String.valueOf(year)));
			applPeriod.setPeriod(Short.parseShort("1"));
			break;
		}
		return applPeriod;
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
