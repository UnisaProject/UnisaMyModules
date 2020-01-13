package za.ac.unisa.lms.tools.mdadm.actions;

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

import org.apache.bcel.generic.GETSTATIC;
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
import za.ac.unisa.lms.dao.Gencod;
import za.ac.unisa.lms.dao.StudentSystemGeneralDAO;
import za.ac.unisa.lms.dao.general.PersonnelDAO;
import za.ac.unisa.lms.dao.general.UserDAO;
import za.ac.unisa.lms.domain.general.Person;
import za.ac.unisa.lms.tools.mdadm.Constants;
import za.ac.unisa.lms.tools.mdadm.dao.MdAdmissionQueryDAO;
import za.ac.unisa.lms.tools.mdadm.exception.UniflowException;
import za.ac.unisa.lms.tools.mdadm.forms.MdAdmissionApplication;
import za.ac.unisa.lms.tools.mdadm.forms.MdAdmissionForm;
import za.ac.unisa.lms.tools.mdadm.forms.Qualification;
import za.ac.unisa.lms.tools.mdadm.forms.SignOffLevel;
import za.ac.unisa.lms.tools.mdadm.forms.SignOffRouteRecord;
import za.ac.unisa.lms.tools.mdadm.forms.Staff;
import za.ac.unisa.lms.tools.mdadm.forms.Student;
import za.ac.unisa.lms.tools.mdadm.forms.TrackRecord;
import za.ac.unisa.lms.tools.mdadm.forms.UniflowFile;

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
		map.put("button.referBack","signOff");
		map.put("display", "display");
	    map.put("button.list", "listStaff");
	    map.put("button.continue", "nextpage");
	    map.put("button.back", "prevpage");
		map.put("inputstep1", "inputstep1");
		map.put("button.external", "listExternal");
		map.put("getFile", "getFile");
		return map;
	}

	public ActionForward inputstep1(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception{

		MdAdmissionForm mdForm = (MdAdmissionForm) form;
		ActionMessages messages = new ActionMessages();
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

		if (user.getType()!=null && !"lecturer".equalsIgnoreCase(user.getType())){
			//Johanet added test not null
			return mapping.findForward("invaliduser");
		}	
		mdForm.setUserCode("MARXH");
		
		Person person = new Person();
		UserDAO userdao = new UserDAO();
		person = userdao.getPerson(mdForm.getUserCode());	
		if (person.getPersonnelNumber()==null || person.getPersonnelNumber().trim().equalsIgnoreCase("")){		
			//user network code on on staff or usr table 
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"User network code not on student system, staff or user table"));
				addErrors(request,messages);
				return mapping.findForward("displayErrorPage");
		}
		
		Staff loggedInUser = new Staff();
		List<SignOffLevel> signOffLevelList= new ArrayList<SignOffLevel>();
		signOffLevelList = dao.getSignOffLevelList(person.getPersonnelNumber());
		loggedInUser.setPerson(person);
		loggedInUser.setSignOffLevelList(signOffLevelList);		
		
		mdForm.setLoggeInUser(loggedInUser);		
		
		//Get M and Admission recommendation types
		List<Gencod> shortList = new ArrayList<Gencod>();
		List<Gencod> longList = new ArrayList<Gencod>();
		StudentSystemGeneralDAO genDao = new StudentSystemGeneralDAO();
		for (int i = 0; i < genDao.getGenCodes((short) 223, 3).size(); i++) {
			Gencod result = new Gencod();
			result = (Gencod) (genDao.getGenCodes((short) 223, 3).get(i));
			shortList.add(i, result);
				if (result.getCode().equalsIgnoreCase("LN")) {
					result.setEngDescription("Recommendation cannot be made. Refer back to M and D Admin.");
				}
				if (result.getCode().equalsIgnoreCase("LF")) {
					result.setEngDescription("Student may register.");
				}				
				if (result.getCode().equalsIgnoreCase("LT")) {
					result.setEngDescription("Student not admitted.");
				}
				if (result.getCode().equalsIgnoreCase("LE")) {
					result.setEngDescription("Direct admission not approved, student may register subjected to the completion of the entrance qualification; e.g. Hons");
				}
			longList.add(i,result);
		}
		mdForm.setRecommendationShortList(shortList);
		mdForm.setRecommendationLongList(longList);
		mdForm.setResponseList(initializeReviewResponseOptions());
		
		//Re-populate students linked to lecturer
		reLoadStudentLookup(mdForm, dao, request);		
		return mapping.findForward("step1forward");
	}
	public ActionForward nextpage(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
		MdAdmissionForm mdForm = (MdAdmissionForm) form;
		
			boolean passValidation=false;
			
			if (mdForm.getCurrentPage().equalsIgnoreCase("recommendation")){
				passValidation= validateRecommendation(mapping,form,request,response);
				if (passValidation){
					return mapping.findForward("signOffConfirmation");
				}else{
					return mapping.findForward("recommendation");
				}
			} 
			if (mdForm.getCurrentPage().equalsIgnoreCase("reviewRecommendation")){
				passValidation= validateRecommendation(mapping,form,request,response);
				if (passValidation){
					return mapping.findForward("signOffConfirmation");
				}else{
					return mapping.findForward("reviewRecommendation");
				}
			} 
			if (mdForm.getCurrentPage().equalsIgnoreCase("searchStaff")){
				return mapping.findForward(staffSelected(mapping, form, request, response));
			}
			if (mdForm.getCurrentPage().equalsIgnoreCase("appealRecommendation")){
				passValidation=validateAppeal(mapping,form,request,response);
				if (passValidation){
					return mapping.findForward("appealSignOffConfirmation");
				}else{
					return mapping.findForward("appealRecommendation");
				}
			}				

			//Re-populate students linked to lecturer
			MdAdmissionQueryDAO dao = new MdAdmissionQueryDAO();
			reLoadStudentLookup(mdForm, dao, request);
			return mapping.findForward("step1forward");			
			
	}
	
	public ActionForward prevpage(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
		MdAdmissionForm mdForm = (MdAdmissionForm) form;		
					
			if (mdForm.getCurrentPage().equalsIgnoreCase("signOffConfirmation")){
				if (mdForm.getApplication().getStatus().equalsIgnoreCase("R")){
					//admission referral
					if (mdForm.getLatestTrackRecord().getAssignToLevel()!=null && 
							!mdForm.getLatestTrackRecord().getAssignToLevel().equalsIgnoreCase("") &&
							mdForm.getLatestTrackRecord().getAssignToLevel().equalsIgnoreCase("1")){
						if (mdForm.getLatestTrackRecord().getStatus().getCode().equalsIgnoreCase("R")){
							return mapping.findForward("recommendation");
						}else if (mdForm.getLatestTrackRecord().getStatus().getCode().equalsIgnoreCase("RB")){
							return mapping.findForward("reviewReferredBack");					
						}
						
					} else if (mdForm.getLatestTrackRecord().getAssignToLevel()!=null && 
							!mdForm.getLatestTrackRecord().getAssignToLevel().equalsIgnoreCase("") && 
							!mdForm.getLatestTrackRecord().getAssignToLevel().equalsIgnoreCase("A")){
						return mapping.findForward("reviewRecommendation");
					}			
				}
			} 
			if (mdForm.getCurrentPage().equalsIgnoreCase("appealSignOffConfirmation")){
				return mapping.findForward("appealRecommendation");
			}
			
			//Re-populate students linked to lecturer
			MdAdmissionQueryDAO dao = new MdAdmissionQueryDAO();
			reLoadStudentLookup(mdForm, dao, request);
			//mdForm.setStudent(new Student());
			return mapping.findForward("step1forward");			
			
	}
	
	Boolean validateRecommendation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		boolean passValidation=true;
		
		MdAdmissionForm mdForm = (MdAdmissionForm) form;
		ActionMessages messages = new ActionMessages();
		
		if(mdForm.getCurrentPage().equalsIgnoreCase("recommendation")){
			if (mdForm.getNewTrackRecord().getRecommendation().getCode() == null || mdForm.getNewTrackRecord().getRecommendation().getCode().trim().equalsIgnoreCase("")){
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"message.generalmessage", 
						"Please select the appropriate recommendation before continuing."));
				passValidation=false;
			}else{
				for (int i=0; i < mdForm.getRecommendationLongList().size(); i ++){
					Gencod gencod = new Gencod();
					gencod = (Gencod)mdForm.getRecommendationLongList().get(i);
					if (gencod.getCode().equalsIgnoreCase(mdForm.getNewTrackRecord().getRecommendation().getCode())){
						mdForm.getNewTrackRecord().getRecommendation().setEngDescription(gencod.getEngDescription());
						i=mdForm.getRecommendationLongList().size();
					}
				}
			}
		} else if (mdForm.getCurrentPage().equalsIgnoreCase("reviewRecommendation")){
			if (mdForm.getSelectedResponse() == null || mdForm.getSelectedResponse().trim().equalsIgnoreCase("")){
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"message.generalmessage", 
						"Please indicate your response to the proposed recommendation"));
				passValidation=false;
			}
		}
		if (passValidation==false){
			addErrors(request, messages);
			return passValidation;
		}
		
		//determine next assign_to_level
		mdForm.getNewTrackRecord().setAssignToLevel("");
		//academic on level 1 cannot make a decision refer back to admin
		if(mdForm.getLatestTrackRecord().getAssignToLevel().equalsIgnoreCase("1") && mdForm.getNewTrackRecord().getRecommendation().getCode().equalsIgnoreCase("LN")){
			mdForm.getNewTrackRecord().setAssignToLevel("A");
			mdForm.getNewTrackRecord().getStatus().setCode("RB");
		}
		//if lecturer disagree always referred back to the first level
		if (mdForm.getSelectedResponse().equalsIgnoreCase("DISAGREE")){
			mdForm.getNewTrackRecord().setAssignToLevel("1");
			mdForm.getNewTrackRecord().getStatus().setCode("RB");
		}
		//forward to the next level
		//after recommendation of academic on first level forward to the next level
		//if academic agree with first level forward to next sign-off
		if(mdForm.getNewTrackRecord().getAssignToLevel().equalsIgnoreCase("")){
			mdForm.getNewTrackRecord().setAssignToLevel(getNextSignOffLevel(mdForm.getAdmSignOffRoute(), mdForm.getLatestTrackRecord().getAssignToLevel()));
			if (mdForm.getNewTrackRecord().getCurrentLevel().equalsIgnoreCase("F")){
				mdForm.getNewTrackRecord().getStatus().setCode("FS");
			}else{
				mdForm.getNewTrackRecord().getStatus().setCode("RS");
			}
		}
		
		//get a list of staff that may sign-off on the next level
		List<Person> signoffStaffList = new ArrayList<Person>();
		if (!mdForm.getNewTrackRecord().getAssignToLevel().equalsIgnoreCase("A")){			
			signoffStaffList = getSignOffStaffForLevel(mdForm.getNewTrackRecord().getAssignToLevel(), mdForm.getAdmSignOffRoute());	
			if (signoffStaffList.size()==0){
				if (mdForm.getNewTrackRecord().getAssignToLevel().equalsIgnoreCase("1")){
					messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"message.generalmessage", "Their is no person on the first level for this qualification signoff route to refer this recommendation back for review to."));
				}else{
					messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"message.generalmessage",  "Their is no person on the qualification signoff route defined to signoff on the next level"));						
				}
				passValidation=false;
			}
		}
		
		//if only one person on sign-off list, select person
		//if disagree on recommendation, get originating 1st person and default this person.
		mdForm.setSelectedAssignTo("");
		if (signoffStaffList.size()==1){
			for (int i = 0; i < signoffStaffList.size(); i++){
				Person person = new Person();
				person = (Person)signoffStaffList.get(0);
				if (person.getPersonnelNumber()!=null && !person.getPersonnelNumber().equalsIgnoreCase("")){
					mdForm.setSelectedAssignTo(person.getPersonnelNumber());
				}			
			}			
		}
		if (mdForm.getSelectedAssignTo().equalsIgnoreCase("") && mdForm.getSelectedResponse()!=null && mdForm.getSelectedResponse().equalsIgnoreCase("DISAGREE")){
			//get the person from which the recommendation originate and set this person as the default
			for (int i = mdForm.getSignOffStatusList().size(); i > 0; i--){
				TrackRecord record = new TrackRecord();
				record = (TrackRecord) mdForm.getSignOffStatusList().get(i-1);
				if (record.getStatus().getCode().equalsIgnoreCase("RS") && record.getCurrentLevel().equalsIgnoreCase("1")){
					//if person still on sign-off list
					for (int j=0; j < mdForm.getAdmSignOffRoute().size(); j++){
						SignOffRouteRecord route = new SignOffRouteRecord();
						route = (SignOffRouteRecord)mdForm.getAdmSignOffRoute().get(j);
						if (route.getSignOffLevel().getLevel().equalsIgnoreCase("1") && route.getPerson().getPersonnelNumber().equalsIgnoreCase(record.getCurrentPerson().getPersonnelNumber())){
							mdForm.setSelectedAssignTo(record.getCurrentPerson().getPersonnelNumber());
							j = mdForm.getAdmSignOffRoute().size();
						}
					}
					i=0;
				}
			}			
		}
		
		if (passValidation==true){
			mdForm.setSignoffStaffList(signoffStaffList);
		}		
		return passValidation;
	}
	
	Boolean validateAppeal(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		boolean passValidation=true;
		
		MdAdmissionForm mdForm = (MdAdmissionForm) form;
		ActionMessages messages = new ActionMessages();
		
		if(mdForm.getCurrentPage().equalsIgnoreCase("appealRecommendation")){
			if (mdForm.getNewTrackRecord().getRecommendation().getCode() == null || mdForm.getNewTrackRecord().getRecommendation().getCode().trim().equalsIgnoreCase("")){
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"message.generalmessage", 
						"Please select the appropriate recommendation before continuing."));
				passValidation=false;
			}else{
				for (int i=0; i < mdForm.getRecommendationLongList().size(); i ++){
					Gencod gencod = new Gencod();
					gencod = (Gencod)mdForm.getRecommendationLongList().get(i);
					if (gencod.getCode().equalsIgnoreCase(mdForm.getNewTrackRecord().getRecommendation().getCode())){
						mdForm.getNewTrackRecord().getRecommendation().setEngDescription(gencod.getEngDescription());
						i=mdForm.getRecommendationLongList().size();
					}
				}
			}
		} 
		
		if (passValidation==false){
			addErrors(request, messages);
			return passValidation;
		}
		
		//determine next assign_to_level
		mdForm.getNewTrackRecord().setAssignToLevel("");
		//academic on level 1(executive dean) cannot make a decision refer back to admin		
		//if not approved forward to Intercollege Postgraduate Studies Committee
		if (mdForm.getLatestTrackRecord().getAssignToLevel().equalsIgnoreCase("E")){ /*executive dean*/
			if (mdForm.getNewTrackRecord().getRecommendation().getCode().equalsIgnoreCase("LN")){
				mdForm.getNewTrackRecord().setAssignToLevel("A");
				mdForm.getNewTrackRecord().getStatus().setCode("AB");
			}
			if	(mdForm.getNewTrackRecord().getRecommendation().getCode().equalsIgnoreCase("LT") ||
						mdForm.getNewTrackRecord().getRecommendation().getCode().equalsIgnoreCase("LE")){
				mdForm.getNewTrackRecord().setAssignToLevel("I");
				mdForm.getNewTrackRecord().getStatus().setCode("RS");
			}
			//if approved do final sign-off and forward to admin section for finalisation
			if (mdForm.getNewTrackRecord().getRecommendation().getCode().equalsIgnoreCase("LF")){
				mdForm.getNewTrackRecord().setAssignToLevel("A");
				mdForm.getNewTrackRecord().getStatus().setCode("FS");
			}
		}
		//Final route back to Admin for finalisation
		if(mdForm.getLatestTrackRecord().getAssignToLevel().equalsIgnoreCase("I")){
			mdForm.getNewTrackRecord().setAssignToLevel("A");
			mdForm.getNewTrackRecord().getStatus().setCode("FS");
		}		
		
		
		//get a list of staff that may sign-off on the next level
		List<Person> signoffStaffList = new ArrayList<Person>();
		mdForm.setSelectedAssignTo("");
		if (!mdForm.getNewTrackRecord().getAssignToLevel().equalsIgnoreCase("A")){			
			signoffStaffList = getSignOffStaffForLevel(mdForm.getNewTrackRecord().getAssignToLevel(), mdForm.getAppealSignOffRoute());	
			if (signoffStaffList.size()==0){
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"message.generalmessage",  "Their is no one defined on the appeal signoff route to signoff on the Intercollege postgraduate studies committee level"));	
				addErrors(request, messages);
				passValidation=false;
			}else if (signoffStaffList.size()==1){
				for (int i = 0; i < signoffStaffList.size(); i++){
					Person person = new Person();
					person = (Person)signoffStaffList.get(0);
					if (person.getPersonnelNumber()!=null && !person.getPersonnelNumber().equalsIgnoreCase("")){
						mdForm.setSelectedAssignTo(person.getPersonnelNumber());
					}			
				}			
			}
		}
		
		if (passValidation==true){
			mdForm.setSignoffStaffList(signoffStaffList);
		}		
		return passValidation;
	}
		
	private List<Person> getSignOffStaffForLevel(String level, List<SignOffRouteRecord> routingList)throws Exception {
			List<Person> staffList = new ArrayList<Person>();
			
			for (int i=0; i < routingList.size();i++){
				SignOffRouteRecord routeItem = new SignOffRouteRecord();
				routeItem = (SignOffRouteRecord) routingList.get(i);
				if (routeItem.getSignOffLevel().getLevel().equalsIgnoreCase(level)){
					Person person = new Person();
					person = routeItem.getPerson();			
					staffList.add(person);				
				}
			}
			
			return staffList;
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
			if (request.getParameter("selectedFile")!= null){
				for (int i = 0; i < mdForm.getDocumentList().size(); i++) {
					uFile = mdForm.getDocumentList().get(i);
					if (uFile.getUniqueId().equalsIgnoreCase(
							request.getParameter("selectedFile").toString())){
						fileName = uFile.getShortDesc();
						break;
					}
				}
				// get specific file from uniflow
				RetrievalResult retrievalResult =  
					dao.getFileFromUniflow(
							mdForm.getDocumentList(),
							request.getParameter("selectedFile").toString(),"");
				
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
			if (mdForm.getStudent().getStudentNumber()== null || "".equals(mdForm.getStudent().getStudentNumber())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
							"Please enter a student number or select an M and D admission referral from the list"));
				
				//Re-populate students linked to lecturer
				reLoadStudentLookup(mdForm, dao, request); 
			}else{
				if (!isNumeric(mdForm.getStudent().getStudentNumber())){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
								"Student number must be numeric."));
					
					//Re-populate students linked to lecturer
					reLoadStudentLookup(mdForm, dao, request); 	
				}
			}
			if (!messages.isEmpty()) {
				addErrors(request,messages);
				return mapping.findForward("step1forward");			
			}
		
		// get application record
		mdForm.setAppAccess("View");	
		if (!mdForm.getStudentApplicationList().isEmpty()&& 
			!(mdForm.getSelectedStudent() == null || 
			  "-1".equals(mdForm.getSelectedStudent()) || 
			  "".equals(mdForm.getSelectedStudent())))
		{
			mdForm.setApplication(mdForm.getStudentApplicationList().get(mdapplKey));
			mdForm.setAppAccess("Update");
		}
		else
		{
			mdForm.setApplication(dao.getLatestApplicationRecordForStudent(mdForm.getStudent().getStudentNumber(),mdForm.getRecommendationLongList()));  //need to change - more than on Application on student number - Johanet 20140214 
			//check if update or view access only
			MdAdmissionApplication app = new MdAdmissionApplication();
			app = mdForm.getStudentApplicationList().get(mdapplKey);	
			if (app == null){
				mdForm.setAppAccess("View");
			}else{
				mdForm.setAppAccess("Update");
			}
		}
		
		if ("".equals(mdForm.getApplication().getApplicationNr())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
						"No Referral or Appeal admission application found for the student."));  //Todo: wording may need to change....
			addErrors(request, messages);

			//Re-populate students linked to lecturer
			reLoadStudentLookup(mdForm, dao, request); 
			return mapping.findForward("step1forward");
		}			
		
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
		toolManager = (ToolManager) ComponentManager.get(ToolManager.class);
  		eventTrackingService.post(
  				eventTrackingService.newEvent(EventTrackingTypes.EVENT_MDADMISSION_VIEW, toolManager.getCurrentPlacement().getContext()+"/"+mdForm.getUserCode(), false),usageSession);

  		//change Johanet 20131107 - to ensure browser refresh button works
  		Student student = new Student();
  		student=dao.getStudent(Integer.parseInt(mdForm.getStudent().getStudentNumber()));
  		mdForm.setStudent(student);
  		
		// get previous qualification
		mdForm.getApplication().setPrevQualification(
				dao.getPreviousQualification(
						mdForm.getStudent().getStudentNumber(), 
						mdForm.getApplication().getApplicationNr()));		
			
//		// get sign-offs
//		mdForm.setSignOffList(dao.getSignOffList(
//				mdForm.getStudent().getStudentNumber(), 
//				mdForm.getApplication().getApplicationNr(), 
//				mdForm.getApplication().getStatus()));

		
		
		// get unifow document list
		//mdForm.setDocumentList(dao.getDocsList(mdForm.getStudent().getStudentNumber())); //debug johanet		
		//Johanet 20131112 change start - 7 number student numbers.  Files exists on uniflow with a 7 number as well as with a 7 number padded with a 0 - change start
		boolean skipUniflow = true;
		if (skipUniflow){
			
		}else{
			if (mdForm.getStudent().getStudentNumber().length()==7){
				mdForm.setDocumentList(dao.getDocsList(mdForm.getStudent().getStudentNumber()));
				String paddedStudentNumber = "";
				paddedStudentNumber = "0"+ mdForm.getStudent().getStudentNumber();
				List <UniflowFile> list = new ArrayList<UniflowFile>();
				list = dao.getDocsList(paddedStudentNumber);
				mdForm.getDocumentList().addAll(list);
			}else{
				mdForm.setDocumentList(dao.getDocsList(mdForm.getStudent().getStudentNumber()));
			}
		}
		//change end!
		
		//get tracking records linked to application
		List<TrackRecord> trackingList; 
		trackingList = dao.getApplTrackingList(Integer.parseInt(mdForm.getStudent().getStudentNumber()), Integer.parseInt(mdForm.getApplication().getApplicationNr()),mdForm.getRecommendationShortList());
		mdForm.setSignOffStatusList(trackingList);
		
		List<SignOffRouteRecord> admSignOffRoute = new ArrayList<SignOffRouteRecord>();
		List<SignOffRouteRecord> appealSignOffRoute = new ArrayList<SignOffRouteRecord>();
		
		admSignOffRoute = dao.getSignOffRoute(mdForm.getApplication().getQualification().getQualCode(),mdForm.getApplication().getQualification().getSpecCode(),"ADM");
		appealSignOffRoute = dao.getSignOffRoute(mdForm.getApplication().getQualification().getQualCode(),mdForm.getApplication().getQualification().getSpecCode(),"APL");		
		
		mdForm.setAdmSignOffRoute(admSignOffRoute);
		mdForm.setAppealSignOffRoute(appealSignOffRoute);
		
		//get the latest tracking record to determine the jsp to call.
		TrackRecord latestTrackRecord = dao.getLatestTrackRecord(Integer.parseInt(mdForm.getStudent().getStudentNumber()), Integer.parseInt(mdForm.getApplication().getApplicationNr()));
		mdForm.setLatestTrackRecord(latestTrackRecord);				
		
		//if view access
		if(mdForm.getAppAccess().equalsIgnoreCase("View")){
			return mapping.findForward("viewAdmission");
		}		
		
		//initialise new trackrecord
		TrackRecord newTrackRecord = new TrackRecord();
		newTrackRecord.setStudentNumber(Integer.parseInt(mdForm.getStudent().getStudentNumber()));
		newTrackRecord.setSeqNr(Integer.parseInt(mdForm.getApplication().getApplicationNr()));
		newTrackRecord.setCurrentLevel(mdForm.getLatestTrackRecord().getAssignToLevel());
		newTrackRecord.setCurrentPerson(mdForm.getLoggeInUser().getPerson());	
		newTrackRecord.setAssignToLevel("");
		newTrackRecord.setDate("");
		Person person = new Person();
		newTrackRecord.setAssignToPerson(person);
		Gencod gencod = new Gencod();
		newTrackRecord.setRecommendation(gencod);
		newTrackRecord.setRecommendationComment("");
		gencod = new Gencod();
		newTrackRecord.setStatus(gencod);
		newTrackRecord.setSignoffComment("");
		mdForm.setNewTrackRecord(newTrackRecord);		
		
		mdForm.setSelectedResponse("");
		
//		TrackRecord newTrackRecord = new TrackRecord();
//		Gencod recommendation = new Gencod();
//		newTrackRecord.setRecommendation(recommendation);
//		mdForm.setNewTrackRecord(newTrackRecord);
		
		if (mdForm.getApplication().getStatus().equalsIgnoreCase("R")){
			//admission referral
			if (latestTrackRecord.getAssignToLevel()!=null && 
					!latestTrackRecord.getAssignToLevel().equalsIgnoreCase("") && 
					latestTrackRecord.getAssignToLevel().equalsIgnoreCase("1")){
				if (latestTrackRecord.getStatus().getCode().equalsIgnoreCase("R")){
					return mapping.findForward("recommendation");
				}else if (latestTrackRecord.getStatus().getCode().equalsIgnoreCase("RB")){
					return mapping.findForward("reviewReferredBack");					
				}
				
			} else if (latestTrackRecord.getAssignToLevel()!=null && 
					!latestTrackRecord.getAssignToLevel().equalsIgnoreCase("") && 
					!latestTrackRecord.getAssignToLevel().equalsIgnoreCase("A")){
				return mapping.findForward("reviewRecommendation");
			}			
		}
		if (mdForm.getApplication().getStatus().equalsIgnoreCase("A")){
			//admission appeal
			return mapping.findForward("appealRecommendation");
		}
		
		return mapping.findForward("viewAdmission");
	    }catch (UniflowException uex){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
						"The system containing the student documents is currently unavailable. "));
			addErrors(request, messages);
			
			log.debug(uex);
			return mapping.findForward("displayforward");
		}
		catch (Exception e){
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
			//validate input
			if(mdForm.getNewTrackRecord().getRecommendation().getCode()!=null &&
					!mdForm.getNewTrackRecord().getRecommendation().getCode().trim().equalsIgnoreCase("")){ 
				if (mdForm.getNewTrackRecord().getRecommendation().getCode().equalsIgnoreCase("LT")){
					//not approved
					if (mdForm.getNewTrackRecord().getRecommendationComment()==null || mdForm.getNewTrackRecord().getRecommendationComment().trim().equalsIgnoreCase("")){
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage",
								"Please indicate the reason why the student has not been admitted."));					
					}else{
						int commentLen = mdForm.getNewTrackRecord().getRecommendationComment().trim().length();
						if(commentLen > 500){
							messages.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("message.generalmessage",
											"The comment regarding the reason the student " +
											"will not be admitted, cannot exceed 500 characters. " +
											"It is currently "+(commentLen)+" characters."));						
						}
					}
				}
				if (mdForm.getNewTrackRecord().getRecommendation().getCode().equalsIgnoreCase("LE")){
					//further requirements needed
					if (mdForm.getNewTrackRecord().getRecommendationComment()==null || mdForm.getNewTrackRecord().getRecommendationComment().trim().equalsIgnoreCase("")){
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage",
								"Please indicate further requirements e.g. 60% aveverage for Hons degree."));					
					}else{
						int commentLen = mdForm.getNewTrackRecord().getRecommendationComment().trim().length();
						if(commentLen > 500){
							messages.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("message.generalmessage",
											"The comment indicating further requirements needed, " +
											"cannot exceed 500 characters. " +
											"It is currently "+(commentLen)+" characters."));						
						}
					}
				}	
				if (mdForm.getNewTrackRecord().getRecommendation().getCode().equalsIgnoreCase("LN")){
					//further requirements needed
					if (mdForm.getNewTrackRecord().getSignoffComment()==null || mdForm.getNewTrackRecord().getSignoffComment().trim().equalsIgnoreCase("")){
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage",
								"Please provide a reason why a recommendation cannot be made."));					
					}
				}	
				if (mdForm.getNewTrackRecord().getRecommendation().getCode().equalsIgnoreCase("LF")){
					if ((mdForm.getApplication().getContactPerson()==null || 
							mdForm.getApplication().getContactPerson().getPersonnelNumber()==null || 
							mdForm.getApplication().getContactPerson().getPersonnelNumber().trim().equalsIgnoreCase("")) && 
							(mdForm.getApplication().getSupervisor()==null || 
							mdForm.getApplication().getSupervisor().getPersonnelNumber()==null || 
							mdForm.getApplication().getSupervisor().getPersonnelNumber().trim().equalsIgnoreCase(""))&& 
							(mdForm.getApplication().getJointSupervisor()==null || 
							mdForm.getApplication().getJointSupervisor().getPersonnelNumber()==null || 
							mdForm.getApplication().getJointSupervisor().getPersonnelNumber().trim().equalsIgnoreCase(""))){
						//specify either a contact person or a supervisor
						if (mdForm.getNewTrackRecord().getSignoffComment()==null || mdForm.getNewTrackRecord().getSignoffComment().trim().equalsIgnoreCase("")){
							messages.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("message.generalmessage",
									"Please select a contact person or supervisor."));					
						}
					}	
				}				
			}
			if (mdForm.getNewTrackRecord().getSignoffComment()!=null && !mdForm.getNewTrackRecord().getSignoffComment().trim().equalsIgnoreCase("")){
				int commentLen = mdForm.getNewTrackRecord().getSignoffComment().trim().length();
				if(commentLen > 500){
					if (mdForm.getNewTrackRecord().getRecommendation()!=null && 
							mdForm.getNewTrackRecord().getRecommendation().getCode()!=null &&
							mdForm.getNewTrackRecord().getRecommendation().getCode().equalsIgnoreCase("LN")){
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage",
										"The reason cannot exceed 500 characters. " +
										"It is currently "+(commentLen)+" characters."));
					}else{
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage",
										"The sign-off comment cannot exceed 500 characters. " +
										"It is currently "+(commentLen)+" characters."));					
					}
				}					
			}
			if (mdForm.getSelectedResponse()!=null && mdForm.getSelectedResponse().equalsIgnoreCase("DISAGREE")){
				if (mdForm.getNewTrackRecord().getSignoffComment()==null || mdForm.getNewTrackRecord().getSignoffComment().trim().equalsIgnoreCase("")){
					messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"message.generalmessage", 
							"Please provide a reason for referring this application back for review."));
				}				
			}
			if(!mdForm.getNewTrackRecord().getAssignToLevel().equalsIgnoreCase("A")){
				//not assigned to admin
				//check that next sign-off person is selected
				if (mdForm.getSelectedAssignTo()==null || mdForm.getSelectedAssignTo().trim().equalsIgnoreCase("")){
					messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"message.generalmessage", 
							"Please select a person to whom a notification of the sign-off request must be send."));
				}else{
					//get selected person 
					for (int i=0; i < mdForm.getSignoffStaffList().size(); i++){
						Person person = new Person();
						person = (Person)mdForm.getSignoffStaffList().get(i);
						if (person.getPersonnelNumber().trim().equalsIgnoreCase(mdForm.getSelectedAssignTo())){
							mdForm.getNewTrackRecord().setAssignToPerson(person);
						}
					}
				}
			}
			if (!messages.isEmpty()) {
				addErrors(request,messages);
				if (mdForm.getApplication().getStatus().equalsIgnoreCase("R")){
					return mapping.findForward("signOffConfirmation");	
				}else{
					return mapping.findForward("appealSignOffConfirmation");	
				}						
			}
			
			dao.recordSignOff(mdForm.getNewTrackRecord(),mdForm.getApplication());
		
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
		toolManager = (ToolManager) ComponentManager.get(ToolManager.class);
  		eventTrackingService.post(
  				eventTrackingService.newEvent(
  						EventTrackingTypes.EVENT_MDADMISSION_SIGNOFF, 
  						toolManager.getCurrentPlacement().getContext()+"/"+mdForm.getUserCode(), 
  						false),
  				usageSession);
  		

		//Re-populate students linked to lecturer
		reLoadStudentLookup(mdForm, dao, request); 
		return mapping.findForward("step1forward");

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
	private List<SignOffRouteRecord> getRoutingList(MdAdmissionForm mdForm, MdAdmissionQueryDAO dao) {
    	MdAdmissionApplication application = mdForm.getApplication();
    	Qualification qualification =  application.getQualification();
    	List<SignOffRouteRecord> routingList = new ArrayList<SignOffRouteRecord>();
    	
    	//routingList = dao.getRoutingList(qualification.getQualCode(), 
    	//									qualification.getSpecCode());
		return routingList;
	}

	public ActionForward cancel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		MdAdmissionForm mdForm = (MdAdmissionForm) form;

		if (mdForm.getCurrentPage().equalsIgnoreCase("searchStaff")){
			return mapping.findForward("signOffConfirmation");
		}
			MdAdmissionQueryDAO dao = new MdAdmissionQueryDAO();
			
			reset(mdForm);
			
			//Re-populate students linked to lecturer
			ArrayList<LabelValueBean> studentLookupList = 
				loadStudentLookup(mdForm, dao);
			request.setAttribute("studentLookupList", studentLookupList);
			
			return mapping.findForward("cancel");
	}

	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response, int searchField)
			throws Exception {
			
		MdAdmissionForm mdForm = (MdAdmissionForm) form;
		mdForm.setSearchListIdentifier(searchField);
		
		return mapping.findForward("searchStaff");
	}

	public ActionForward clear(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response, int clearField)
			throws Exception {
			
		MdAdmissionForm mdForm = (MdAdmissionForm) form;
		if(clearField==1){
			mdForm.getApplication().setContactPerson(new Person());
		}else if(clearField==2){
			mdForm.getApplication().setSupervisor(new Person());
		}if(clearField==3){
			mdForm.getApplication().setJointSupervisor(new Person());
		}
		
		if (mdForm.getApplication().getStatus().equalsIgnoreCase("R")){
			return mapping.findForward("signOffConfirmation");
		}else{
			return mapping.findForward("appealSignOffConfirmation");
		}
	
	}
	
	public String staffSelected(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			
		MdAdmissionForm mdForm = (MdAdmissionForm) form;
		MdAdmissionQueryDAO dao = new MdAdmissionQueryDAO();
		PersonnelDAO persnoDao = new PersonnelDAO();
		
		if (mdForm.getSearchListIdentifier() ==1){			
				// get staff details
				mdForm.getApplication().setContactPerson(persnoDao.getPersonnelFromSTAFF(Integer.parseInt(mdForm.getSearchResult().substring(0,8))));
		}else if (mdForm.getSearchListIdentifier() ==2){			
				// get staff details
				mdForm.getApplication().setSupervisor(persnoDao.getPersonnelFromSTAFF(Integer.parseInt(mdForm.getSearchResult().substring(0,8))));
		}else if (mdForm.getSearchListIdentifier() ==3){			
				// get staff details				
				mdForm.getApplication().setJointSupervisor(persnoDao.getPersonnelFromSTAFF(Integer.parseInt(mdForm.getSearchResult().substring(0,8))));
		}
		
		mdForm.setSearchNo("");
		mdForm.setSearchSurname("");
		mdForm.setSearchResult("");
		if (mdForm.getApplication().getStatus().equalsIgnoreCase("R")){
			return "signOffConfirmation";
		}else{
			return "appealSignOffConfirmation";
		}
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
			return mapping.findForward("searchStaff");
		}
		
		//add test to check for a numberic staff number
		if (!mdForm.getSearchNo().trim().equalsIgnoreCase("")){
			if (!isNumeric(mdForm.getSearchNo())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
							"The staff number must be numeric."));
				addErrors(request, messages);
				return mapping.findForward("searchStaff");
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
			return mapping.findForward("searchStaff");
		}else{
			//Add  list to request
			request.setAttribute("staffList", list);
			mdForm.setExternalStaff(false);
		}
		
		return mapping.findForward("searchStaff");
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
			return mapping.findForward("searchStaff");
		}
		
		MdAdmissionQueryDAO dao = new MdAdmissionQueryDAO();
		ArrayList<LabelValueBean> list = new ArrayList<LabelValueBean>();
		list=dao.getExternalList(mdForm.getSearchNo(), mdForm.getSearchSurname());
		if(list.isEmpty()){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
						"No staff members were found for the search criteria."));
			addErrors(request, messages);
			return mapping.findForward("searchStaff");
		}else{
			//Add  list to request
			request.setAttribute("staffList", list);
			mdForm.setExternalStaff(true);
		}
		
		return mapping.findForward("searchStaff");
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
    										MdAdmissionForm mdForm, MdAdmissionQueryDAO dao) 
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
    	      loggedInUser.getPerson().getPersonnelNumber() == null || 
    	      "".equals(loggedInUser.getPerson().getPersonnelNumber())))
    	{	    	
	    	//Get SignOffLevel list
	    	List<SignOffLevel> signOffLevelList = loggedInUser.getSignOffLevelList();
	    	
	    	Iterator<SignOffLevel> it = signOffLevelList.iterator();
	    	while(it.hasNext()) {    		
	    		SignOffLevel signOffLevel = it.next();	
	    		String type ="";
	    		if (signOffLevel.getType().equalsIgnoreCase("ADM")){
	    			type="R";
	    		}
	    		if (signOffLevel.getType().equalsIgnoreCase("APL")){
	    			type="A";
	    		}
	    			    		
	        	studentListPerQualification = 
	    			dao.getApplicationRecordList(
	    					loggedInUser.getPerson().getPersonnelNumber(), 
	    					studentApplicationList, 
	    					signOffLevel.getLevel(), 
	    					signOffLevel.getQualCode(),
	    					signOffLevel.getSpecCode(),
	    					type,
	    					mdForm.getRecommendationLongList());
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
    	//Staff nextStaff = getNextRoutingRecipient(routingList, loggedInUser.getFinalFlag());
		
    	Student student = mdForm.getStudent();
    	
    	emailService = (EmailService) ComponentManager.get(EmailService.class);
    	
		String emailFrom = loggedInUser.getPerson().getEmailAddress(); 
		//String emailTo = nextStaff.getPerson().getEmailAddress();
		String emailTo="";
		
    	//do not send email on local machine, dev & qa -default email to tnonyack@unisa.ac.za(Developer)
		String serverpath = ServerConfigurationService.getServerUrl();
		if (serverpath.contains("mydev") || serverpath.contains("localhost")){
			emailTo = "pretoj@unisa.ac.za";//loggedInUser.getEmailAddress();//"tnonyack@unisa.ac.za";
		}
		else{    	
			emailFrom = loggedInUser.getPerson().getEmailAddress();
			//emailTo = nextStaff.getPerson().getEmailAddress();
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
    	//Staff nextStaff = getNextRoutingRecipient(routingList, loggedInUser.getFinalFlag());
		
    	Student student = mdForm.getStudent();
    	
    	emailService = (EmailService) ComponentManager.get(EmailService.class);
    	
		String emailFrom = loggedInUser.getPerson().getEmailAddress(); 
		//String emailTo = nextStaff.getPerson().getEmailAddress();
		String emailTo = "";
		
		String body = "<html><body>";
		String vbCrLf = "<br/>";
		String paraOpen = "<p>";
		String paraClose = "</p>";
		
    	//do not send email on local machine, dev & qa -default email to tnonyack@unisa.ac.za(Developer)
		String serverpath = ServerConfigurationService.getServerUrl();
		if (serverpath.contains("mydev") || serverpath.contains("myqa") || serverpath.contains("localhost")){
			emailTo = loggedInUser.getPerson().getEmailAddress(); ;//"tnonyack@unisa.ac.za";
		}
		else{    	
			emailFrom = loggedInUser.getPerson().getEmailAddress(); ; 
			//emailTo = nextStaff.getPerson().getEmailAddress();
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
			body = body + "Regards" + vbCrLf + mdForm.getLoggeInUser().getPerson().getName() + vbCrLf ;
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
	        params.put("studentNumber", mdForm.getStudent().getStudentNumber());
	        params.put("studentName", mdForm.getStudent().getName());
			params.put(Qualification.QUAL_CODE, qualification.getQualCode());
	        params.put(Qualification.QUAL_DESC, qualification.getQualDesc());
	        params.put("staffName", mdForm.getLoggeInUser().getPerson().getName());
	        
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
//    private Staff getNextRoutingRecipient(ArrayList<Staff> routingList, String currentFinalFlag)
//    {   
//    	String nextFinalFlag = getNextFinalFlagValue(routingList, currentFinalFlag);
//    	
//    	Staff nextStaff = null;
//    	for (Staff staff: routingList)
//    	{
//    		if (staff.getFinalFlag().equals(nextFinalFlag))
//    		{    			
//    			nextStaff = staff;    			
//    			break;
//    		}
//    	}    
//    	
//    	//To accommodate the scenario where qsprout is not configured properly
//    	//Fix this 
//    	if (nextStaff == null  && isFinalSignOff(nextFinalFlag , routingList))
//    	{
//        	for (Staff staff: routingList)
//        	{
//        		if (staff.getFinalFlag().equals(Constants.QSPROUT_FINAL_FLAG_F))
//        		{    			
//        			nextStaff = staff;    			
//        			break;
//        		}
//        	} 
//    	}
//    	return nextStaff;
//    }
//    
//    
//
//    /**
//     * Returns the previous staff on the routing list to refer the application to.
//     * 
//     * @param routingList
//     * 			The Staff routing list	
//     * @param currentFinalFlag
//     * 			The logged in user finalFlag
//     * @return Staff
//     * 		Next staff to refer the application to 
//     */
//    private Staff getPrevRoutingRecipient(ArrayList<Staff> routingList, String currentFinalFlag)
//    {   
//    	String nextFinalFlag = getPrevFinalFlagValue(routingList, currentFinalFlag);
//    	
//    	Staff nextStaff = null;
//    	for (Staff staff: routingList)
//    	{
//    		if (staff.getFinalFlag().equals(nextFinalFlag))
//    		{    			
//    			nextStaff = staff;    			
//    			break;
//    		}
//    	}    
//    	
//    	//To accommodate the scenario where qsprout is not configured properly
//    	//Fix this 
//    	if (nextStaff == null  && isFinalSignOff(nextFinalFlag , routingList))
//    	{
//        	for (Staff staff: routingList)
//        	{
//        		if (staff.getFinalFlag().equals(Constants.QSPROUT_FINAL_FLAG_F))
//        		{    			
//        			nextStaff = staff;    			
//        			break;
//        		}
//        	} 
//    	}
//    	return nextStaff;
//    }
    
   
    private String getNextSignOffLevel(List<SignOffRouteRecord> signOffRoute, String currentLevel)
    {    	
    	int val = -1;
    	
		try{
			val = Integer.parseInt(currentLevel);
			val +=1;
			
			//check if level in admission sign-off level
			for (int i=0; i < signOffRoute.size(); i++){
				SignOffRouteRecord record = (SignOffRouteRecord)signOffRoute.get(i);
				if (record.getSignOffLevel().getLevel().equalsIgnoreCase(String.valueOf(val))) {
					return String.valueOf(val);
				}
			}
			return "F";
		} catch (NumberFormatException e) {
			return "A";
		}	
    }
    
    private List<LabelValueBean> initializeReviewResponseOptions() {

		ArrayList<LabelValueBean> list = new ArrayList<LabelValueBean>();
		LabelValueBean responseItem = new LabelValueBean();

		responseItem.setValue("AGREE");
		responseItem.setLabel("Agree with proposed recommendation (including student's contact person and supervisors)");
		list.add(responseItem);
		responseItem = new LabelValueBean();
		responseItem.setValue("DISAGREE");
		responseItem.setLabel("Refer back for review. (If you do not agree with the proposed recommendation, including the contact person/supervisors, refer back to level 1 for review)");
		list.add(responseItem);
		

		return list;

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
