package za.ac.unisa.lms.tools.mdadmission.actions;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.internet.InternetAddress;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.LookupDispatchAction;
import org.apache.struts.util.LabelValueBean;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.datacontract.schemas._2004._07.UniflowFindAndGetService.RetrievalResult;

import za.ac.unisa.lms.constants.EventTrackingTypes;
import za.ac.unisa.lms.tools.mdadmission.Constants;
import za.ac.unisa.lms.tools.mdadmission.dao.MdAdmissionQueryDAO;
import za.ac.unisa.lms.tools.mdadmission.exception.*;
import za.ac.unisa.lms.tools.mdadmission.forms.MdAdmissionApplication;
import za.ac.unisa.lms.tools.mdadmission.forms.MdAdmissionForm;
import za.ac.unisa.lms.tools.mdadmission.forms.Qualification;
import za.ac.unisa.lms.tools.mdadmission.forms.SignOffLevel;
import za.ac.unisa.lms.tools.mdadmission.forms.Staff;
import za.ac.unisa.lms.tools.mdadmission.forms.Student;
import za.ac.unisa.lms.tools.mdadmission.forms.UniflowFile;

import org.sakaiproject.email.api.EmailService;
import org.sakaiproject.event.api.UsageSession;
import org.sakaiproject.event.api.EventTrackingService;
import org.sakaiproject.event.api.UsageSessionService;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.tool.api.ToolManager;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserNotDefinedException;
import org.sakaiproject.user.api.UserDirectoryService;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.springframework.mail.javamail.ConfigurableMimeFileTypeMap;

import za.ac.unisa.lms.dao.Gencod;
import za.ac.unisa.lms.dao.StudentSystemGeneralDAO;

public class DisplayMdAdmissionAction  extends LookupDispatchAction{


	public static Log log = LogFactory.getLog(DisplayMdAdmissionAction.class);
	private SessionManager sessionManager;
	private UserDirectoryService userDirectoryService;
	private EventTrackingService eventTrackingService;
	private ToolManager toolManager;
	private UsageSessionService usageSessionService;
	private EmailService emailService;
	private static final String REFERRAL_EMAIL_PATH = "za/ac/unisa/lms/tools/mdadmission/templates/referral-email.vm";
	
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
	    map.put("button.cancel", "cancel");
		map.put("button.display", "display");
		map.put("button.signoff", "signOff");
		map.put("signOff", "signOff");
		map.put("display", "display");
	    map.put("button.list", "listStaff");
	    map.put("button.continue", "staffSelected");
	    map.put("display", "display");
		map.put("inputstep1", "inputstep1");
		map.put("button.external", "listExternal");
		map.put("getFile", "getFile");
		return map;
	}

	public ActionForward inputstep1(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception{

		MdAdmissionForm mdForm = (MdAdmissionForm) form;
		MdAdmissionQueryDAO dao = new MdAdmissionQueryDAO();
		sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
		userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);

		reset(mdForm);	//Johanet reset on entering tool 20131108
		//Get user details, set in default action
		User user = null;

		Session currentSession = sessionManager.getCurrentSession();
		String userID = currentSession.getUserId();

		log.debug("test useriID");
		if (userID != null) {
			log.debug("useriID not null");
			user = userDirectoryService.getUser(userID);

			log.debug("useri code:"+userDirectoryService.getUserEid(userID));
			mdForm.setUserCode(userDirectoryService.getUserEid(userID));
		}else{
			//Johanet added else
			return mapping.findForward("invaliduser");
		}

		if (user.getType()!=null && !"Instructor".equalsIgnoreCase(user.getType())){
			//Johanet added test not null
			return mapping.findForward("invaliduser");
		}	
		
		StudentSystemGeneralDAO systemDao = new StudentSystemGeneralDAO();		
		Gencod gencod = new Gencod();
		gencod = systemDao.getGenCode("251", "OLD_IN_USE");
			
		if (gencod!=null && gencod.getEngDescription()!=null && gencod.getEngDescription().equalsIgnoreCase("Y")){
			mdForm.setOldUniflowInUse(true);
		}else{
			mdForm.setOldUniflowInUse(false);
		}
		
		//Staff loggedInUser = dao.getStaffFromNetworkCode(mdForm.getUserCode());
		Staff loggedInUser = dao.getStaffRoutDet(mdForm.getUserCode());
				
		if(loggedInUser ==null || 
		   loggedInUser.getStaffNr() == null || 
		   "".equals(loggedInUser.getStaffNr())){
			//Re-populate students linked to lecturer
			reLoadStudentLookup(mdForm, dao, request); 
			return mapping.findForward("step1forward");
		}
		
		mdForm.setLoggeInUser(loggedInUser);	
		
		//Re-populate students linked to lecturer
		reLoadStudentLookup(mdForm, dao, request); 
		
		return mapping.findForward("step1forward");
	}
	
	public ActionForward getFile(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception{


		ActionMessages messages = new ActionMessages();
		MdAdmissionForm mdForm = (MdAdmissionForm) form;
		MdAdmissionQueryDAO dao = new MdAdmissionQueryDAO();
		String fileName = "";
		UniflowFile uFile = null;
		
		try
		{
			// get file name
			if (request.getParameter("docUniqueId")!= null && request.getParameter("uniflowVersion")!=null){
				for (int i = 0; i < mdForm.getDocumentList().size(); i++) {
					uFile = mdForm.getDocumentList().get(i);
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
							mdForm.getDocumentList(),
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
					return mapping.findForward("displayforward");
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
			return mapping.findForward("displayforward");
		}
		//Re-populate students linked to lecturer
		//reLoadStudentLookup(mdForm, dao, request); Johanet commented out 20140108 - no need to re-populate lookuplist.
		//return mapping.findForward("step1forward"); Johanet commented out 20140108 - cannot commit response and after findforward.
		return null;
	}

	public ActionForward display(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception{

		MdAdmissionForm mdForm = (MdAdmissionForm) form;
		ActionMessages messages = new ActionMessages();
		MdAdmissionQueryDAO dao = new MdAdmissionQueryDAO();
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
		UsageSession usageSession = usageSessionService.getSession();		
		
		try{
			
			
		//Populate selected student number if student number not populated
			String mdapplKey="";
			if(mdForm.getStudent().getStudentNumber()== null || "".equals(mdForm.getStudent().getStudentNumber()))
			{
				if (mdForm.getSelectedStudent() != null && 
					!Constants.EMPTY_STRING.equals(mdForm.getSelectedStudent()) &&
					!"-1".equals(mdForm.getSelectedStudent()))
				{
					String[] stuDetails = mdForm.getSelectedStudent().split(" - ");
					mdapplKey = stuDetails[0];
					mdForm.getStudent().setStudentNumber(stuDetails[1]);
					mdForm.getStudent().setName(stuDetails[2]);
				}
			}
			else
			{
				mdForm.setSelectedStudent("");
			}
	
		// validate input
			messages = (ActionMessages)mdForm.validate(mapping, request);
			if (!messages.isEmpty()) {
				addErrors(request, messages);
				
				//Re-populate students linked to lecturer
				reLoadStudentLookup(mdForm, dao, request); 
				
				return mapping.findForward("step1forward");
			}
		if (mdForm.getStudent().getStudentNumber()== null || "".equals(mdForm.getStudent().getStudentNumber())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
						"Please enter a student number."));
			addErrors(request, messages);
			
			//Re-populate students linked to lecturer
			reLoadStudentLookup(mdForm, dao, request); 
			
			return mapping.findForward("step1forward");
		}		
		
		// get application record
		if (!mdForm.getStudentApplicationList().isEmpty()&& 
			!(mdForm.getSelectedStudent() == null || 
			  "-1".equals(mdForm.getSelectedStudent()) || 
			  "".equals(mdForm.getSelectedStudent())))
		{
			mdForm.setApplication(mdForm.getStudentApplicationList().get(mdapplKey));
//			mdForm.setApplication(mdForm.getStudentApplicationList().get(mdForm.getStudent().getStudentNumber()));
		}
		else
		{
			mdForm.setApplication(dao.getApplicationRecord(null,mdForm.getStudent().getStudentNumber()));  //need to change - more than on Application on student number - Johanet 20140214 
		}
		
		if ("".equals(mdForm.getApplication().getApplicationNr())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
						"No Referral or Appeal admission application found for the student."));
			addErrors(request, messages);

			//Re-populate students linked to lecturer
			reLoadStudentLookup(mdForm, dao, request); 
			return mapping.findForward("step1forward");
		}
		
		if ("".equals(mdForm.getApplication().getRecommendation())){
			mdForm.setHaveToSetRecommendation(true);
		}else{
			mdForm.setHaveToSetRecommendation(false);
		}
		
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
		toolManager = (ToolManager) ComponentManager.get(ToolManager.class);
  		eventTrackingService.post(
  				eventTrackingService.newEvent(EventTrackingTypes.EVENT_MDADMISSION_VIEW, toolManager.getCurrentPlacement().getContext()+"/"+mdForm.getUserCode(), false),usageSession);

		// get student name
//  		if (mdForm.getStudent().getName() == "" || 
//  			mdForm.getStudent().getName() == null)
//  		{
//  			mdForm.getStudent().setName(dao.getStudentName(mdForm.getStudent().getStudentNumber()));
//  		}
  		//change Johanet 20131107 - to ensure browser refresh button works
  		mdForm.getStudent().setName(dao.getStudentName(mdForm.getStudent().getStudentNumber()));
  		
		//get AddressPH
		mdForm.getStudent().setAddressPH(dao.getAddressPH(
											mdForm.getStudent().getStudentNumber(), 
											Constants.FK_ADRCATCODE_STU));
  		
		// get previous qualification
		mdForm.getApplication().setPrevQualification(
				dao.getPreviousQualification(
						mdForm.getStudent().getStudentNumber(), 
						mdForm.getApplication().getApplicationNr()));
		// get sign-offs
		mdForm.setSignOffList(dao.getSignOffList(
				mdForm.getStudent().getStudentNumber(), 
				mdForm.getApplication().getApplicationNr(), 
				mdForm.getApplication().getStatus()));
		// get unifow document list
		mdForm.getDocumentList().clear();
		List <UniflowFile> newDoclist = new ArrayList<UniflowFile>();
		List <UniflowFile> oldDoclist = new ArrayList<UniflowFile>();
		
		//mdForm.setDocumentList(dao.getDocsList(mdForm.getStudent().getStudentNumber())); //debug johanet		
		//Johanet 20131112 change start - 7 number student numbers.  Files exists on uniflow with a 7 number as well as with a 7 number padded with a 0 - change start
		if (mdForm.getStudent().getStudentNumber().length()==7){
			//mdForm.setDocumentList(dao.getDocsList(mdForm.getStudent().getStudentNumber()));
			
			//Read new uniflow machine with 7 digits			
			newDoclist = dao.getNewDocsList(mdForm.getStudent().getStudentNumber());  		                           
			mdForm.getDocumentList().addAll(newDoclist);  
			String paddedStudentNumber = "";
			paddedStudentNumber = "0"+ mdForm.getStudent().getStudentNumber();
			
			//Read new uniflow machine padded with a 0			
			newDoclist = dao.getNewDocsList(paddedStudentNumber);  		                           
			mdForm.getDocumentList().addAll(newDoclist); 
			
			
			if (mdForm.isOldUniflowInUse()){	
				//Read old uniflow machine with 7 digits
				oldDoclist = dao.getDocsList(mdForm.getStudent().getStudentNumber()); 
				mdForm.getDocumentList().addAll(oldDoclist); 
				
				//Read old uniflow machine padded with a 0
				oldDoclist = dao.getDocsList(paddedStudentNumber); 
				mdForm.getDocumentList().addAll(oldDoclist); 
			}
			
			
		}else{			
			Boolean readOnlyNewUniflow = false;
			
			if (!mdForm.isOldUniflowInUse()){
				readOnlyNewUniflow = true;
			} else {
				//If new student number do not search old uniflow machine
				if (isNumeric(mdForm.getStudent().getStudentNumber()) && 
						Integer.parseInt(mdForm.getStudent().getStudentNumber()) >= 60542280 &&
						Integer.parseInt(mdForm.getStudent().getStudentNumber()) < 70000000) {
					readOnlyNewUniflow = true;
				}
			}					
			
			newDoclist = dao.getNewDocsList(mdForm.getStudent().getStudentNumber());  		                           
			mdForm.getDocumentList().addAll(newDoclist); 
			

			if (!readOnlyNewUniflow) {				
				oldDoclist = dao.getDocsList(mdForm.getStudent().getStudentNumber());    
				mdForm.getDocumentList().addAll(oldDoclist);                            
			}	
			
		}
		//change end!

		return mapping.findForward("displayforward");
	    }catch (UniflowException uex){
	    	if (mdForm.isOldUniflowInUse()){
	    		messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
							"The old Uniflow system that may contain some of the student's documents is currently unavailable. Documents that have not yet been moved to the new Uniflow system will not be visible."));	    		
	    	}		
			if (mdForm.getUserCode()!=null && mdForm.getUserCode().equalsIgnoreCase("PRETOJ")){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
							"Connection exception: " + uex));
			}
			addErrors(request, messages);			
			log.debug(uex);
			return mapping.findForward("displayforward");
		} catch (NewUniflowException nuex){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
						"The system containing the student's documents is currently unavailable."));	
			if (mdForm.getUserCode()!=null && mdForm.getUserCode().equalsIgnoreCase("PRETOJ")){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
						"Connection exception: " + nuex));
			}	
			addErrors(request, messages);
			log.debug(nuex);
			return mapping.findForward("displayforward");
		} catch (Exception e){
			throw new Exception("DisplayMdAdmissionAction(display): / "+e.getMessage(), e);
		}
	}

	public ActionForward signOff(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception{

		MdAdmissionForm mdForm = (MdAdmissionForm) form;
		ActionMessages messages = new ActionMessages();
		MdAdmissionQueryDAO dao = new MdAdmissionQueryDAO();
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
		UsageSession usageSession = usageSessionService.getSession();

		try{
		if(mdForm.getUserCode() == null || "".equals(mdForm.getUserCode())){
			log.debug("user code error:" + mdForm.getUserCode());
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
						"User code empty."));
			addErrors(request, messages);

			//Re-populate students linked to lecturer
			reLoadStudentLookup(mdForm, dao, request); 
			return mapping.findForward("step1forward");
			//return mapping.findForward("home");
		}
		// validate input
		if (mdForm.getStudent().getStudentNumber()== null || 
			"".equals(mdForm.getStudent().getStudentNumber())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
						"Please enter a student number."));
			addErrors(request, messages);

			//Re-populate students linked to lecturer
			reLoadStudentLookup(mdForm, dao, request); 
			return mapping.findForward("step1forward");
		}
		if (mdForm.isHaveToSetRecommendation() == true && "".equals(mdForm.getApplication().getRecommendation())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
						"Please indicate your recommendation."));
			addErrors(request, messages);
			return mapping.findForward("displayforward");
		}
		if("yes".equalsIgnoreCase(mdForm.getApplication().getRecommendation()) && 
		   "".equals(mdForm.getApplication().getContactPerson().getStaffNr()) && 
		   "".equals(mdForm.getApplication().getSupervisor().getStaffNr())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
						"Please select a contact person or supervisor."));
			addErrors(request, messages);
			return mapping.findForward("displayforward");
		}
		if(!"yes".equalsIgnoreCase(mdForm.getApplication().getRecommendation()) && 
		   (!"".equals(mdForm.getApplication().getContactPerson().getStaffNr()) || 
		   !"".equals(mdForm.getApplication().getSupervisor().getStaffNr()) || 
		   !"".equals(mdForm.getApplication().getJointSupervisor().getStaffNr()))){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
						"Contact person, supervisor or joint supervisor can only " +
						"be specified if the recommendation 'Student my register' is selected."));
			addErrors(request, messages);
			return mapping.findForward("displayforward");
		}
		if("struct".equalsIgnoreCase(mdForm.getApplication().getRecommendation())) {
				if( "".equals(mdForm.getApplication().getStructuredComment().trim())){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
							"Please indicate the modules the student needs to register for."));
					addErrors(request, messages);
					return mapping.findForward("displayforward");
				}else{
					int commentLen = mdForm.getApplication().getStructuredComment().trim().length();
					if(commentLen > 500){
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage",
										"The comment regarding the modules that a " +
										"student needs to register for, cannot " +
										"exceed 500 characters. " +
										"It is currently "+(commentLen)+" characters."));
						addErrors(request, messages);
						return mapping.findForward("displayforward");
					}
				}
		}
		if("addreq".equalsIgnoreCase(mdForm.getApplication().getRecommendation())){
			if ("".equals(mdForm.getApplication().getRequirementComment().trim())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
						"Please indicate the additional requirements required."));
				addErrors(request, messages);
				return mapping.findForward("displayforward");
			}else{
				int commentLen = mdForm.getApplication().getRequirementComment().trim().length();
				if(commentLen > 500){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
									"The comment regarding the additional requirements, " +
									"cannot exceed 500 characters. " +
									"It is currently "+(commentLen)+" characters."));
					addErrors(request, messages);
					return mapping.findForward("displayforward");
				}
			}
		}
		if("no".equalsIgnoreCase(mdForm.getApplication().getRecommendation())){
			if ("".equals(mdForm.getApplication().getNotAdmittedComment().trim())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
						"Please indicate the reason why the student has not been admitted."));
				addErrors(request, messages);
				return mapping.findForward("displayforward");
			}else{
				int commentLen = mdForm.getApplication().getNotAdmittedComment().trim().length();
				if(commentLen > 500){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
									"The comment regarding the reason the student " +
									"will not be admitted, cannot exceed 500 characters. " +
									"It is currently "+(commentLen)+" characters."));
					addErrors(request, messages);
					return mapping.findForward("displayforward");
				}
			}
		}
		
		if(!"".equals(mdForm.getApplication().getSignOffComment().trim())){
				int commentLen = mdForm.getApplication().getSignOffComment().trim().length();
				if(commentLen > 500){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
									"The sign-off comment cannot exceed 500 characters. " +
									"It is currently "+(commentLen)+" characters."));
					addErrors(request, messages);
					return mapping.findForward("displayforward");
				}
		}

		Staff signOffStaff = mdForm.getLoggeInUser();
		if(signOffStaff ==null || signOffStaff.getStaffNr() == null || 
		   "".equals(signOffStaff.getStaffNr())){
//			messages.add(ActionMessages.GLOBAL_MESSAGE,
//					new ActionMessage("message.generalmessage",
//							"Sign-off error: The logged in user's("+ mdForm.getUserCode() +") staff number is empty."));
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
							"You are not part of the sign-off list for this qualification. Please contact your M & D coordinator for assistance."));
			addErrors(request, messages);
			return mapping.findForward("displayforward");
		}
		if(mdForm.isHaveToSetRecommendation() == true){
			// update recommendation
			dao.updateApplication(mdForm.getApplication(), mdForm.getStudent().getStudentNumber());
			// write track record
			dao.writeRecommendStatusTracking(
					mdForm.getApplication(), 
					mdForm.getStudent().getStudentNumber(), 
					signOffStaff,mdForm.getRecommendation());
		}
		// add signoff record		
		dao.writeSignOff(
				mdForm.getApplication(), 
				mdForm.getStudent().getStudentNumber(), 
				signOffStaff);
		
		//Get routing list
    	ArrayList<Staff> routingList = getRoutingList(mdForm, dao);
		
		// if final sign off, write track record
		if(isFinalSignOff(signOffStaff.getFinalFlag(), routingList)){
			dao.writeFinalSignOff(
					mdForm.getApplication(), 
					mdForm.getStudent().getStudentNumber(), 
					signOffStaff);
		}
		else //if not final sign off, send email to the next recipient
		{
			sendEmail(mdForm, routingList);
		}
		
		// re-read application
//		mdForm.setApplication(
//				dao.getApplicationRecord(mdForm.getStudent().getStudentNumber()));
// Johanet change 20140214 - Change code to read on application number as well, cannot get application only on student number - as more than one can exists.
		mdForm.setApplication(
				dao.getApplicationRecord(mdForm.getApplication().getApplicationNr(),mdForm.getStudent().getStudentNumber()));
		if ("".equals(mdForm.getApplication().getRecommendation())){
			mdForm.setHaveToSetRecommendation(true);
		}else{
			mdForm.setHaveToSetRecommendation(false);
		}
		// get student name
		mdForm.getStudent().setName(
				dao.getStudentName(mdForm.getStudent().getStudentNumber()));
		// get previous qualification
		mdForm.getApplication().setPrevQualification(
				dao.getPreviousQualification(
						mdForm.getStudent().getStudentNumber(),
						mdForm.getApplication().getApplicationNr()));
		// re-read sign-offs
		mdForm.setSignOffList(dao.getSignOffList(
									mdForm.getStudent().getStudentNumber(), 
									mdForm.getApplication().getApplicationNr(), 
									mdForm.getApplication().getStatus()));
		
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
		toolManager = (ToolManager) ComponentManager.get(ToolManager.class);
  		eventTrackingService.post(
  				eventTrackingService.newEvent(
  						EventTrackingTypes.EVENT_MDADMISSION_SIGNOFF, 
  						toolManager.getCurrentPlacement().getContext()+"/"+mdForm.getUserCode(), 
  						false),
  				usageSession);
  		
	    return mapping.findForward("displayforward");

		}catch (Exception e){
			throw new Exception("MdAdmissionAction(signoff): / "+e.getMessage(), e);
		}
	}

	/**
	 * Returns the staff routing list
     * 
     * @param mdForm MdAdmissionForm
     * 			Data Form
     * @param dao MdAdmissionQueryDAO
     * 			Data access object
	 * @return
	 * 		ArrayList<Staff> 
	 */		
	private ArrayList<Staff> getRoutingList(MdAdmissionForm mdForm, MdAdmissionQueryDAO dao) {
    	MdAdmissionApplication application = mdForm.getApplication();
    	Qualification qualification =  application.getQualification();
    	
    	ArrayList<Staff> routingList = dao.getRoutingList(
    										qualification.getQualCode(), 
    										qualification.getSpecCode());
		return routingList;
	}

	public ActionForward cancel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		if ("search".equalsIgnoreCase(request.getParameter("frompage"))){
			return mapping.findForward("displayforward");
		}
			MdAdmissionForm mdForm = (MdAdmissionForm)form;
			MdAdmissionQueryDAO dao = new MdAdmissionQueryDAO();
			
			reset(mdForm);
			
			//Re-populate students linked to lecturer
			ArrayList<LabelValueBean> studentLookupList = 
				loadStudentLookup(mdForm, dao);
			request.setAttribute("studentLookupList", studentLookupList);
			
			return mapping.findForward("cancelforward");
	}

	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response, int searchField)
			throws Exception {
			
		MdAdmissionForm mdForm = (MdAdmissionForm) form;
		mdForm.setSearchListIdentifier(searchField);
		
		return mapping.findForward("searchforward");
	}

	public ActionForward clear(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response, int clearField)
			throws Exception {
			
		MdAdmissionForm mdForm = (MdAdmissionForm) form;
		if(clearField==1){
			mdForm.getApplication().setContactPerson(new Staff());
		}else if(clearField==2){
			mdForm.getApplication().setSupervisor(new Staff());
		}if(clearField==3){
			mdForm.getApplication().setJointSupervisor(new Staff());
		}
		
		return mapping.findForward("displayforward");
	}
	
	public ActionForward staffSelected(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			
		MdAdmissionForm mdForm = (MdAdmissionForm) form;
		MdAdmissionQueryDAO dao = new MdAdmissionQueryDAO();
		
		if (mdForm.getSearchListIdentifier() ==1){
			if(mdForm.isExternalStaff()){
				// read external staff table
				mdForm.getApplication().setContactPerson(dao.getExternalStaffMemberDetail(mdForm.getSearchResult().substring(0,8)));
			}else{
				// get staff details
				mdForm.getApplication().setContactPerson(dao.getStaffMemberDetail(mdForm.getSearchResult().substring(0,8)));
			}
		}else if (mdForm.getSearchListIdentifier() ==2){
			if(mdForm.isExternalStaff()){
				// read external staff table
				mdForm.getApplication().setSupervisor(dao.getExternalStaffMemberDetail(mdForm.getSearchResult().substring(0,8)));
			}else{
				// get staff details
				mdForm.getApplication().setSupervisor(dao.getStaffMemberDetail(mdForm.getSearchResult().substring(0,8)));
			}
		}else if (mdForm.getSearchListIdentifier() ==3){
			if(mdForm.isExternalStaff()){
				// read external staff table
				mdForm.getApplication().setJointSupervisor(dao.getExternalStaffMemberDetail(mdForm.getSearchResult().substring(0,8)));
			}else{
				// get staff details
				mdForm.getApplication().setJointSupervisor(dao.getStaffMemberDetail(mdForm.getSearchResult().substring(0,8)));
			}
		}
		
		mdForm.setSearchNo("");
		mdForm.setSearchSurname("");
		mdForm.setSearchResult("");
		return mapping.findForward("displayforward");
	}

	public ActionForward listStaff(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			
		MdAdmissionForm mdForm = (MdAdmissionForm) form;
		ActionMessages messages = new ActionMessages();
		
		if ("".equals(mdForm.getSearchNo()) && "".equals(mdForm.getSearchSurname())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
						"Enter either a staff number or a surname."));
			addErrors(request, messages);
			return mapping.findForward("searchforward");
		}
		
		//add test to check for a numberic staff number
		if (!mdForm.getSearchNo().trim().equalsIgnoreCase("")){
			if (!isNumeric(mdForm.getSearchNo())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
							"The staff number must be numeric."));
				addErrors(request, messages);
				return mapping.findForward("searchforward");
			}
		}
		
		MdAdmissionQueryDAO dao = new MdAdmissionQueryDAO();
		ArrayList<LabelValueBean> list = new ArrayList<LabelValueBean>();
		list=dao.getStaffList(mdForm.getSearchNo(), mdForm.getSearchSurname());
		if(list.isEmpty()){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
						"No staff members were found for the search criteria."));
			addErrors(request, messages);
			return mapping.findForward("searchforward");
		}else{
			//Add  list to request
			request.setAttribute("staffList", list);
			mdForm.setExternalStaff(false);
		}
		
		return mapping.findForward("searchforward");
	}

	public ActionForward listExternal(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			
		MdAdmissionForm mdForm = (MdAdmissionForm) form;
		ActionMessages messages = new ActionMessages();
		
		if ("".equals(mdForm.getSearchNo()) && "".equals(mdForm.getSearchSurname())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
						"Enter either a staff number or a surname."));
			addErrors(request, messages);
			return mapping.findForward("searchforward");
		}
		
		MdAdmissionQueryDAO dao = new MdAdmissionQueryDAO();
		ArrayList<LabelValueBean> list = new ArrayList<LabelValueBean>();
		list=dao.getExternalList(mdForm.getSearchNo(), mdForm.getSearchSurname());
		if(list.isEmpty()){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
						"No staff members were found for the search criteria."));
			addErrors(request, messages);
			return mapping.findForward("searchforward");
		}else{
			//Add  list to request
			request.setAttribute("staffList", list);
			mdForm.setExternalStaff(true);
		}
		
		return mapping.findForward("searchforward");
	}

	public void reset(MdAdmissionForm form) {

		form.setStudent(new Student());
		form.setApplication(new MdAdmissionApplication());
		form.setErrorMessage("");
		form.setHaveToSetRecommendation(false);
		form.setSelectedStudent("");
	}

	public ActionForward unspecified(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response){

		log.info("MdActivityAction: unspecified method call -no value for parameter in request");
		return mapping.findForward("home");

	}
	
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

         if (request.getParameter("action.search1") != null) return search(mapping, form, request, response, 1);
         if (request.getParameter("action.search2") != null) return search(mapping, form, request, response, 2);
         if (request.getParameter("action.search3") != null) return search(mapping, form, request, response, 3);
         
         if (request.getParameter("action.clear1") != null) return clear(mapping, form, request, response, 1);
         if (request.getParameter("action.clear2") != null) return clear(mapping, form, request, response, 2);
         if (request.getParameter("action.clear3") != null) return clear(mapping, form, request, response, 3);

         return super.execute(mapping, form, request, response);
    }    

    /**
     * Checks if it is the first routing flag <F>
     * 
     * @param finalFlag
     * 			Routing flag
     * @return
     * 		true/false
     */
    private boolean isFinalSignOff(String finalFlag)
    {
    	if (Constants.QSPROUT_FINAL_FLAG_F.equals(finalFlag))
        {
        	return true;
        }
    	return false;
    }
    
    /**
     * Checks if it is the final routing flag <F>
     * 
     * @param finalFlag
     * 			Routing flag
     * @return
     * 		true/false
     */
    private boolean isFinalSignOff(String finalFlag, ArrayList<Staff> routingList)
    {
    	if (Constants.QSPROUT_FINAL_FLAG_F.equals(finalFlag) ||
    		Constants.QSPROUT_FINAL_FLAG_F.equals(
    				getNextFinalFlagValue(routingList, finalFlag)))
    	{
    		return true;
    	}
    	return false;
    } 
        
    /**
     * Checks if it is the first routing flag <F>
     * 
     * @param finalFlag
     * 			Routing flag
     * @return
     * 		true/false
     */
    private boolean isFirstSignOff(String finalFlag)
    {
    	if (isNumeric(finalFlag))
    	{        	
        	if (Constants.QSPROUT_FINAL_FLAG_1.equals(finalFlag))
        	{
        		return true;
        	}
    		
    	}
    	return false;
    }
        
	private boolean isNumeric(String testString){

		boolean result = true;

		try{
			Integer.parseInt(testString);
		} catch (NumberFormatException e) {
			result = false;
		}

		return result;

	}
	
    /**
     * Returns student look up list
     * 
     * @param mdForm
     * 			Data Form
     * @param dao
     * 			Data access object
     * @return ArrayList<LabelValueBean>
     * 			Student lookup list
     * @throws Exception
     */
    private void reLoadStudentLookup(
    		MdAdmissionForm mdForm, 
    		MdAdmissionQueryDAO dao, 
    		HttpServletRequest request) 
    throws Exception 
    {
    	
    	ArrayList<LabelValueBean> studentLookupList = 
			loadStudentLookup(mdForm, dao);
		request.setAttribute("studentLookupList", studentLookupList);
    }
    
    
    
    /**
     * Returns student look up list for each qualification linked to the 
     * logged in user
     * 
     * @param mdForm
     * 			Data Form
     * @param dao
     * 			Data access object
     * @return ArrayList<LabelValueBean>
     * 			Student lookup list
     * @throws Exception
     */
    private ArrayList<LabelValueBean> loadStudentLookup(
    										MdAdmissionForm mdForm, 
    										MdAdmissionQueryDAO dao) 
    throws Exception 
    {    	
		Map<String, MdAdmissionApplication> studentListPerQualification = null;
		Map<String, MdAdmissionApplication> studentApplicationList      = 
			mdForm.getStudentApplicationList();
		
		//Clear the previous student list
		if (studentApplicationList != null && !studentApplicationList.isEmpty())
		{
			studentApplicationList.clear();
		}
    	
    	Staff loggedInUser = mdForm.getLoggeInUser();
    	if (!(loggedInUser ==null || 
    	      loggedInUser.getStaffNr() == null || 
    	      "".equals(loggedInUser.getStaffNr())))
    	{
	    	String currentFinalFlag = null;  	    	
	    	//Staff prevSignoff = null; move down Johanet 20130905
	    	//ArrayList<Staff> routingList = null; move down Johanet 20130905
	    	
	    	//Get SignOffLevel list
	    	ArrayList<SignOffLevel> signOffLevelList = loggedInUser.getSignOffLevelList();
	    	
	    	Iterator<SignOffLevel> it = signOffLevelList.iterator();
	    	while(it.hasNext()) {    		
	    		SignOffLevel signOffLevel = it.next();
	    		Staff prevSignoff = null;
	    		ArrayList<Staff> routingList = null;
	    		currentFinalFlag = signOffLevel.getFinalFlag();  
	    		 
	        	if (!isFirstSignOff(currentFinalFlag))
	        	{
	        		routingList = 
	        			dao.getRoutingList(signOffLevel.getQualCode(), signOffLevel.getSpecCode());
	        		prevSignoff =  getPrevRoutingRecipient(routingList, currentFinalFlag);
	        	}
	    		
	        	studentListPerQualification = 
	    			dao.getApplicationRecordList(
	    					loggedInUser.getStaffNr(), 
	    					studentApplicationList, 
	    					prevSignoff, 
	    					signOffLevel.getQualCode(),
	    					signOffLevel.getSpecCode());
	        	studentApplicationList.putAll(studentListPerQualification);	        
	    	}
    	}
		mdForm.setStudentApplicationList(studentApplicationList);
		
		ArrayList<LabelValueBean> list = 
			dao.getStudentLookupList(mdForm.getStudentApplicationList());
		list.add(0,new LabelValueBean("Select a student","-1"));
		return list;
    }      
    
    /**
     * Send email to the next recipient on the routing list
     * @param dao
     * 			Data access object
     * @param mdForm
     * 			Data form
     * @throws Exception
     */
    public void sendEmail(MdAdmissionForm mdForm, ArrayList<Staff> routingList ) throws Exception
    {
    	Staff loggedInUser = mdForm.getLoggeInUser();
    	Staff nextStaff = getNextRoutingRecipient(routingList, loggedInUser.getFinalFlag());
		
    	Student student = mdForm.getStudent();
    	
    	emailService = (EmailService) ComponentManager.get(EmailService.class);
    	
		String emailFrom = loggedInUser.getEmailAddress(); 
		String emailTo = nextStaff.getEmailAddress();
		
    	//do not send email on local machine, dev & qa -default email to tnonyack@unisa.ac.za(Developer)
		String serverpath = ServerConfigurationService.getServerUrl();
		if (serverpath.contains("mydev") || serverpath.contains("myqa") || serverpath.contains("localhost")){
			emailTo = "pretoj@unisa.ac.za";//loggedInUser.getEmailAddress();//"tnonyack@unisa.ac.za";
		}
		else{    	
			emailFrom = loggedInUser.getEmailAddress(); 
			emailTo = nextStaff.getEmailAddress();
		}

		String subject = "Admission referral for " + student.getStudentNumber() + " "+ student.getName();
		String body = null;
		
		List<String> contentlist = new ArrayList<String>();
		contentlist.add("Content-Type: text/html");
		try{
			body = constructEmailBody(mdForm);
			InternetAddress toEmail = new InternetAddress(emailTo);
			InternetAddress iaTo[] = new InternetAddress[1];
			iaTo[0] = toEmail;
			InternetAddress iaHeaderTo[] = new InternetAddress[1];
			iaHeaderTo[0] = toEmail;
			InternetAddress iaReplyTo[] = new InternetAddress[1];
			iaReplyTo[0] = new InternetAddress(emailFrom);
			emailService.sendMail(iaReplyTo[0],iaTo,subject,body,iaHeaderTo,iaReplyTo,contentlist);

		} catch (Exception ex){
			
			throw new Exception ("DisplayMdAdmissionAction(sendEmail) /" + ex.getMessage(), ex);
		}
    }
    
    /**
     * Send email to the next recipient on the routing list
     * @param dao
     * 			Data access object
     * @param mdForm
     * 			Data form
     * @throws Exception
     */
    public void sendEmailBackup(MdAdmissionForm mdForm, ArrayList<Staff> routingList ) throws Exception
    {
    	Staff loggedInUser = mdForm.getLoggeInUser();
    	Staff nextStaff = getNextRoutingRecipient(routingList, loggedInUser.getFinalFlag());
		
    	Student student = mdForm.getStudent();
    	
    	emailService = (EmailService) ComponentManager.get(EmailService.class);
    	
		String emailFrom = loggedInUser.getEmailAddress(); 
		String emailTo = nextStaff.getEmailAddress();
		
		String body = "<html><body>";
		String vbCrLf = "<br/>";
		String paraOpen = "<p>";
		String paraClose = "</p>";
		
    	//do not send email on local machine, dev & qa -default email to tnonyack@unisa.ac.za(Developer)
		String serverpath = ServerConfigurationService.getServerUrl();
		if (serverpath.contains("mydev") || serverpath.contains("myqa") || serverpath.contains("localhost")){
			emailTo = loggedInUser.getEmailAddress();//"tnonyack@unisa.ac.za";
		}
		else{    	
			emailFrom = loggedInUser.getEmailAddress(); 
			emailTo = nextStaff.getEmailAddress();
		}

		String subject = "Admission referral for " + student.getStudentNumber() + " "+ student.getName();
		//String body = null;
		Qualification qualification = mdForm.getApplication().getQualification();
		List<String> contentlist = new ArrayList<String>();
		contentlist.add("Content-Type: text/html");
		try{
			body = body + "Dear Colleague" + vbCrLf + paraOpen;
			body = body + "The admission application from " + student.getStudentNumber() + " "+ student.getName() +
					" for the " + qualification.getQualCode()+ " " + qualification.getQualCode() +
					"has been referred to you for consideration." + paraClose + paraOpen;
			body = body + "Please log on to myUnisa and follow the steps to the sign-off." + paraClose + paraOpen;
			body = body + "I thank you kindly." + paraClose;
			body = body + "Regards" + vbCrLf + mdForm.getLoggeInUser().getName() + vbCrLf ;
			body = body + "</body></html>";
			InternetAddress toEmail = new InternetAddress(emailTo);
			InternetAddress iaTo[] = new InternetAddress[1];
			iaTo[0] = toEmail;
			InternetAddress iaHeaderTo[] = new InternetAddress[1];
			iaHeaderTo[0] = toEmail;
			InternetAddress iaReplyTo[] = new InternetAddress[1];
			iaReplyTo[0] = new InternetAddress(emailFrom);
			emailService.sendMail(iaReplyTo[0],iaTo,subject,body,iaHeaderTo,iaReplyTo,contentlist);

		} catch (Exception ex){
			
			throw new Exception ("DisplayMdAdmissionAction(sendEmail) /" + ex.getMessage(), ex);
		}
    }
    
    /**
     * Construct email body from the referral-email.vm template
     * 
     * @param mdForm
     * 			MdAdmissionForm data
     * @return
     * 		HTML string
     * @throws ServletException
     */
    private String constructEmailBody(MdAdmissionForm mdForm) throws ServletException
    {
        try
        {
    		Properties properties = new Properties();
    		properties.setProperty(
    				Constants.RESOURCE_LOADER, 
    				Constants.RESOURCE_LOADER_NAME);
    		properties.setProperty(
    				Constants.RESOURCE_LOADER_CLASSPATH_CLASS,
    				ClasspathResourceLoader.class.getName());	
    		properties.setProperty( 
    				RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS, 
    				Constants.RUNTIME_LOG_LOGSYSTEM_CLASS_VALUE );
    		properties.setProperty(
    				Constants.RUNTIME_LOG_LOGGER_LOGSYSTEM_LOG4J_LOGGER,
    				Constants.RUNTIME_LOG_LOGGER_LOGSYSTEM_LOG4J_LOGGER_NAME);
    		
    		// null logger that will prevent velocity.log from being generated
    		//properties.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS,
    		//NullLogChute.class.getName());

            VelocityEngine ve = new VelocityEngine(properties);
        	ve.init();

	        Qualification qualification = mdForm.getApplication().getQualification();
	        
	        Map<String, String> params = new HashMap<String, String>();
	        params.put(Student.STUDENT_NUMBER, mdForm.getStudent().getStudentNumber());
	        params.put(Student.STUDENT_NAME, mdForm.getStudent().getName());
			params.put(Qualification.QUAL_CODE, qualification.getQualCode());
	        params.put(Qualification.QUAL_DESC, qualification.getQualDesc());
	        params.put(Staff.STAFF_NAME, mdForm.getLoggeInUser().getName());
	        
	        Template t = ve.getTemplate(REFERRAL_EMAIL_PATH);
	        VelocityContext context = new VelocityContext(params);
	        StringWriter writer = new StringWriter();
	        t.merge(context, writer);
	
	        return writer.toString();

		}
		catch (Exception ex)
		{
			throw new ServletException("DisplayMdAdmissionAction(constructEmailBody) /" + ex.getMessage(), ex);
		}
    }

    /**
     * Returns the next staff on the routing list to refer the application to.
     * 
     * @param routingList
     * 			The Staff routing list	
     * @param currentFinalFlag
     * 			The logged in user finalFlag
     * @return Staff
     * 		Next staff to refer the application to 
     */
    private Staff getNextRoutingRecipient(ArrayList<Staff> routingList, String currentFinalFlag)
    {   
    	String nextFinalFlag = getNextFinalFlagValue(routingList, currentFinalFlag);
    	
    	Staff nextStaff = null;
    	for (Staff staff: routingList)
    	{
    		if (staff.getFinalFlag().equals(nextFinalFlag))
    		{    			
    			nextStaff = staff;    			
    			break;
    		}
    	}    
    	
    	//To accommodate the scenario where qsprout is not configured properly
    	//Fix this 
    	if (nextStaff == null  && isFinalSignOff(nextFinalFlag , routingList))
    	{
        	for (Staff staff: routingList)
        	{
        		if (staff.getFinalFlag().equals(Constants.QSPROUT_FINAL_FLAG_F))
        		{    			
        			nextStaff = staff;    			
        			break;
        		}
        	} 
    	}
    	return nextStaff;
    }
    
    

    /**
     * Returns the previous staff on the routing list to refer the application to.
     * 
     * @param routingList
     * 			The Staff routing list	
     * @param currentFinalFlag
     * 			The logged in user finalFlag
     * @return Staff
     * 		Next staff to refer the application to 
     */
    private Staff getPrevRoutingRecipient(ArrayList<Staff> routingList, String currentFinalFlag)
    {   
    	String nextFinalFlag = getPrevFinalFlagValue(routingList, currentFinalFlag);
    	
    	Staff nextStaff = null;
    	for (Staff staff: routingList)
    	{
    		if (staff.getFinalFlag().equals(nextFinalFlag))
    		{    			
    			nextStaff = staff;    			
    			break;
    		}
    	}    
    	
    	//To accommodate the scenario where qsprout is not configured properly
    	//Fix this 
    	if (nextStaff == null  && isFinalSignOff(nextFinalFlag , routingList))
    	{
        	for (Staff staff: routingList)
        	{
        		if (staff.getFinalFlag().equals(Constants.QSPROUT_FINAL_FLAG_F))
        		{    			
        			nextStaff = staff;    			
        			break;
        		}
        	} 
    	}
    	return nextStaff;
    }
    
    /**
     * Retrieves the next final flag value based on the current logged in user's 
     * final flag value 
     * @param routingList
     * 			The Staff routing list	
     * @param currentFinalFlag
     * 			The logged in user final flag value
     * @return
     * 		Next final flag value
     */
    private String getNextFinalFlagValue(ArrayList<Staff> routingList, String currentFinalFlag)
    {    	
    	int val = -1;
    	
		try{
			val = Integer.parseInt(currentFinalFlag);
			val +=1;
			
			if (val > routingList.size())
			{
				return Constants.QSPROUT_FINAL_FLAG_F;
			}
		} catch (NumberFormatException e) {
			return Constants.QSPROUT_FINAL_FLAG_F;
		}			
		
		return String.valueOf(val);
    }
    
    /**
     * Retrieves the previous final flag value based on the current logged in user's 
     * final flag value 
     * @param routingList
     * 			The Staff routing list	
     * @param currentFinalFlag
     * 			The logged in user final flag value
     * @return
     * 		Next final flag value
     */
    private String getPrevFinalFlagValue(ArrayList<Staff> routingList, String currentFinalFlag)
    {    	
    	int val = -1;
    	int listSize = routingList.size();
    	
		try{
			val = Integer.parseInt(currentFinalFlag);
			val -=1;
			
			if (val > routingList.size() || 
				val < Integer.parseInt(Constants.QSPROUT_FINAL_FLAG_1))
			{
				return String.valueOf(Constants.ZERO_VALUE);
			}
		} catch (NumberFormatException e) {
			return String.valueOf(listSize - 1);
		}			
		
		return String.valueOf(val);
    }

}
