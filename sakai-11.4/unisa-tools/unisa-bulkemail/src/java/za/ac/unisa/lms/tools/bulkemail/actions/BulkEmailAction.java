package za.ac.unisa.lms.tools.bulkemail.actions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.Iterator;
import java.util.regex.Pattern;

import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.event.api.UsageSession;
import org.sakaiproject.event.api.EventTrackingService;
import org.sakaiproject.event.api.UsageSessionService;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.event.api.UsageSessionService;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.tool.api.ToolManager;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserDirectoryService;
import org.sakaiproject.event.api.EventTrackingService;
import org.sakaiproject.tool.api.ToolManager;

import za.ac.unisa.lms.constants.EventTrackingTypes;
import za.ac.unisa.lms.tools.bulkemail.dao.BulkEmailDAO;
import za.ac.unisa.lms.tools.bulkemail.dao.StudentListSites;
import za.ac.unisa.lms.tools.bulkemail.forms.BulkEmailForm;

public class BulkEmailAction extends DispatchAction  {
	
	private SessionManager sessionManager;
	private UserDirectoryService userDirectoryService;
	private UsageSessionService usageSessionService;
	private EventTrackingService eventTrackingService;
	private ToolManager toolManager;
	private Log log;
	

	public ActionForward step1(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		sessionManager=(SessionManager) ComponentManager.get(SessionManager.class);
		userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
		toolManager = (ToolManager) ComponentManager.get(ToolManager.class);

		BulkEmailDAO bulkEmailDAO = new BulkEmailDAO();
		BulkEmailForm bulkEmailForm = (BulkEmailForm)form;

		bulkEmailForm.setUserName(request.getAttribute("user")+"");
		bulkEmailForm.setMessageSubject("");
		bulkEmailForm.setMessageText("");
		bulkEmailForm.setIndexNrOfSelectedSite(null);
		ArrayList listofsites = new ArrayList();
		ActionMessages messages = new ActionMessages();
		bulkEmailForm.setNoSitesExists(false);

		Session currentSession = sessionManager.getCurrentSession();
		String userId = currentSession.getUserId();
		User user = null;
		if (userId != null) {
			user = userDirectoryService.getUser(userId);
		}

		UsageSession usageSession = usageSessionService.getSession();

		try {
			listofsites = bulkEmailDAO.getSitesPerLecturer(bulkEmailForm.getUserName());
			if(!listofsites.isEmpty()) {
				bulkEmailForm.setSites(listofsites);
			}else {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Only lecturer roles can use this tool."));
				addErrors(request, messages);
				bulkEmailForm.setNoSitesExists(true);
				return mapping.findForward("step1");
			}
		}catch(Exception ex) {
			throw ex;
		}

		request.setAttribute("sitesPerLecturer", bulkEmailForm.getSites());

		eventTrackingService.post(
				eventTrackingService.newEvent(EventTrackingTypes.EVENT_BULKEMAIL_VIEW, toolManager.getCurrentPlacement().getContext(), false),usageSession);

		return mapping.findForward("step1");
	}


	public ActionForward step2(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BulkEmailForm bulkEmailForm = (BulkEmailForm)form;
		BulkEmailDAO bulkEmailDAO = new BulkEmailDAO();
		ActionMessages messages = new ActionMessages();

		if(isCancelled(request)) {
			bulkEmailForm.setIndexNrOfSelectedSite(null);
			return mapping.findForward("step1");
		}

		if(bulkEmailForm.getIndexNrOfSelectedSite() == null) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "You have not selected any subjects"));
			addErrors(request, messages);
			request.setAttribute("sitesPerLecturer", bulkEmailForm.getSites());
			return mapping.findForward("step1");
		}

		try {
			addConfinements2RequestObject(request,bulkEmailDAO);
		}catch(Exception ex) {
			throw ex;
		}

		return mapping.findForward("step2");
	}


	public ActionForward step3(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		sessionManager=(SessionManager) ComponentManager.get(SessionManager.class);
		userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
		toolManager = (ToolManager) ComponentManager.get(ToolManager.class);

		BulkEmailForm bulkEmailForm = (BulkEmailForm)form;
		BulkEmailDAO bulkEmailDAO = new BulkEmailDAO();
		ActionMessages messages = new ActionMessages();
		int studentsWhomReceivedMailCount = 0;

		Session currentSession = sessionManager.getCurrentSession();
		String userId = currentSession.getUserId();
		User user = null;
		if (userId != null) {
			user = userDirectoryService.getUser(userId);
		}
		UsageSession usageSession = usageSessionService.getSession();
		 ArrayList allsites = bulkEmailForm.getSites();
		 ArrayList mysites = new ArrayList();

		 if(bulkEmailForm.getIndexNrOfSelectedSite() != null)
		 {String[] arry =bulkEmailForm.getIndexNrOfSelectedSite();

			String s ="";
			for(int j = 0; j < arry.length; j++) {
			s = ((StudentListSites)(allsites.get(Integer.parseInt(arry[j])))).getLecturerSites();
			//System.out.println("The selected sistes are:- "+s);
			mysites.add(s);
			}
			bulkEmailForm.setMysites(mysites);
		 }

		if(isCancelled(request)) {
			bulkEmailForm.setIndexNrOfSelectedSite(null);
			bulkEmailForm.setMessageSubject("");
			bulkEmailForm.setMessageText("");
			bulkEmailForm.setConfineGender(null);
			bulkEmailForm.setConfineHomeLanguage(null);
			bulkEmailForm.setConfineCountry(null);
			bulkEmailForm.setConfineProvince(null);
			bulkEmailForm.setConfineDistrict(null);
			bulkEmailForm.setConfineStudentsregion(null);
			bulkEmailForm.setConfineStudentsResidentialregion(null);
			bulkEmailForm.setConfineRace(null);

			request.setAttribute("sitesPerLecturer", bulkEmailForm.getSites());
			return mapping.findForward("step1");

		}
		log = LogFactory.getLog(this.getClass());

		if(request.getParameter("button") != null) {

			if((request.getParameter("button")).equals("Back")) {
				request.setAttribute("sitesPerLecturer", bulkEmailForm.getSites());
				addConfinements2RequestObject(request,bulkEmailDAO);
				return mapping.findForward("step2");
			}
		}

		if(request.getParameter("button") != null) {
			log.debug("bulk e-mail: index of selected sites before using the insertToForm : " + bulkEmailForm.getIndexNrOfSelectedSite());
			insertIntoForm(mapping, bulkEmailForm, request, response, bulkEmailForm.getIndexNrOfSelectedSite());
			
			
			if((request.getParameter("button")).equals("Preview")) {
				if(bulkEmailForm.getReply().equalsIgnoreCase("dont")) {
					bulkEmailForm.setReplyAddress("No reply address selected");
				
					
					
				}
				
				if (bulkEmailForm.getMessageText().length() > 4000) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Message text may not be greater than 4000 characters"));
					addErrors(request, messages);
					return mapping.findForward("step3");
				}
				
				return mapping.findForward("preview");
			}

			else if (request.getParameter("button").equalsIgnoreCase("Send mail")){
				if(bulkEmailForm.getMessageSubject().trim().equalsIgnoreCase("") & bulkEmailForm.getMessageText().trim().equalsIgnoreCase("")) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "You need to supply a message subject and message text"));
					addErrors(request, messages);
					return mapping.findForward("step3");
				}
                
				if(bulkEmailForm.getMessageSubject().trim().equalsIgnoreCase("") | bulkEmailForm.getMessageSubject().trim().equalsIgnoreCase("")) {
	 				   messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage", "You need to insert text in the subject field"));
						addErrors(request, messages);
						request.setAttribute("sitesPerLecturer", bulkEmailForm.getSites());
						return mapping.findForward("step3");
					}
				if(bulkEmailForm.getMessageText() == null | bulkEmailForm.getMessageText().trim().equalsIgnoreCase("")) {
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage", "You need to insert text in the message field"));
						addErrors(request, messages);
						request.setAttribute("sitesPerLecturer", bulkEmailForm.getSites());
						return mapping.findForward("step3");
					}
				if (bulkEmailForm.getMessageText().length() > 4000) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Message text may not be greater than 4000 characters"));
					addErrors(request, messages);
					return mapping.findForward("step3");
				}
				if(!validateEmail(bulkEmailForm) & bulkEmailForm.getReply().equalsIgnoreCase("do")) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Please insert a valid reply e-mail address"));
					addErrors(request, messages);
					//nie seker nie
					request.setAttribute("sitesPerLecturer", bulkEmailForm.getSites());
					return mapping.findForward("step3");
				}

				if(bulkEmailForm.getReply().equalsIgnoreCase("dont")) {
					bulkEmailForm.setReplyAddress("No reply address selected");
				}


				List list = (List)bulkEmailForm.getSites();
				Vector sites = new Vector();
				String array[] = bulkEmailForm.getIndexNrOfSelectedSite();
				Integer index = null;

				for(int j = 0; j < array.length; j++ ) {
					index = new Integer(array[j]);
					sites.add(((StudentListSites)(list.get(index.intValue()))).getLecturerSites());
				}

			try{
				studentsWhomReceivedMailCount = bulkEmailDAO.sendMail(bulkEmailForm.getConfineGender(),
						bulkEmailForm.getConfineHomeLanguage(),
						bulkEmailForm.getConfineCountry(),
						bulkEmailForm.getConfineProvince(),
						bulkEmailForm.getConfineStudentsregion(),
						bulkEmailForm.getConfineStudentsResidentialregion(),
						bulkEmailForm.getConfineRace(),
						sites,bulkEmailForm, bulkEmailForm.getReplyAddress());
				}
				catch(AddressException AdreEx)
				  { System.out.println("The problem is :"+AdreEx.getLocalizedMessage());}

				eventTrackingService.post(
						eventTrackingService.newEvent(EventTrackingTypes.EVENT_BULKEMAIL_SEND, toolManager.getCurrentPlacement().getContext(), false),usageSession);

				//display the message. Logic to NOT insert into the DB resides in the sendMail method of BulkEmailDAO
				if(!bulkEmailForm.isStudentExists()) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "No students exist for your specific query."));
					addErrors(request, messages);
				}else {
	    				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Your e-mail has been sent to " + studentsWhomReceivedMailCount +  " student(s). Thank you."));
						addErrors(request, messages);
				}

				request.setAttribute("sitesPerLecturer", bulkEmailForm.getSites());
				bulkEmailForm.setIndexNrOfSelectedSite(null);
				bulkEmailForm.setStudentExists(false);
				return mapping.findForward("step1");
			}

	}
	return mapping.findForward("step3");
}
		
	public ActionForward preview(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BulkEmailForm bulkEmailForm = (BulkEmailForm)form;
		ActionMessages messages = new ActionMessages();
		BulkEmailDAO bulkEmailDAO = new BulkEmailDAO();
		int studentsWhomReceivedMailCount = 0;

		if(isCancelled(request)) {
			bulkEmailForm.setIndexNrOfSelectedSite(null);
			bulkEmailForm.setMessageSubject("");
			bulkEmailForm.setMessageText("");
			bulkEmailForm.setConfineGender(null);
			bulkEmailForm.setConfineHomeLanguage(null);
			bulkEmailForm.setConfineCountry(null);
			bulkEmailForm.setConfineProvince(null);
			bulkEmailForm.setConfineDistrict(null);
			bulkEmailForm.setConfineStudentsregion(null);
			bulkEmailForm.setConfineStudentsResidentialregion(null);
			bulkEmailForm.setConfineRace(null);
			request.setAttribute("sitesPerLecturer", bulkEmailForm.getSites());

			return mapping.findForward("step1");

		}

		if(request.getParameter("button") != null) {
			if((request.getParameter("button")).equalsIgnoreCase("Revise")) {

				return mapping.findForward("step3");
			}else if (request.getParameter("button").equalsIgnoreCase("Send mail")){
				
				if(bulkEmailForm.getMessageSubject().trim().equalsIgnoreCase("") & bulkEmailForm.getMessageText().trim().equalsIgnoreCase("")) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "You need to supply a message subject and message text"));
					addErrors(request, messages);
					request.setAttribute("sitesPerLecturer", bulkEmailForm.getSites());
					return mapping.findForward("step3");
					}				
				 if(bulkEmailForm.getMessageSubject() == null | bulkEmailForm.getMessageSubject().trim().equalsIgnoreCase("")) {
 				   messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "You need to insert text in the subject field"));
					addErrors(request, messages);
					request.setAttribute("sitesPerLecturer", bulkEmailForm.getSites());
					return mapping.findForward("step3");
				}
				if(bulkEmailForm.getMessageText() == null | bulkEmailForm.getMessageText().trim().equalsIgnoreCase("")) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "You need to insert text in the message field"));
					addErrors(request, messages);
					request.setAttribute("sitesPerLecturer", bulkEmailForm.getSites());
					return mapping.findForward("step3");
				}
				 
				if(!validateEmail(bulkEmailForm) & bulkEmailForm.getReply().equalsIgnoreCase("do")) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "You need to insert a valid reply e-mail address"));
					addErrors(request, messages);
					//nie seker nie
					request.setAttribute("sitesPerLecturer", bulkEmailForm.getSites());
					return mapping.findForward("step3");
				}

				if(bulkEmailForm.getReply().equalsIgnoreCase("dont")) {
					bulkEmailForm.setReplyAddress("no reply address selected");
				}

				List list = (List)bulkEmailForm.getSites();
				Vector sites = new Vector();
				String array[] = bulkEmailForm.getIndexNrOfSelectedSite();
				Integer index = null;

				for(int j = 0; j < array.length; j++ ) {
					index = new Integer(array[j]);
					sites.add(((StudentListSites)(list.get(index.intValue()))).getLecturerSites());
				}
			try{
				studentsWhomReceivedMailCount = bulkEmailDAO.sendMail(bulkEmailForm.getConfineGender(),
						bulkEmailForm.getConfineHomeLanguage(),
						bulkEmailForm.getConfineCountry(),
						bulkEmailForm.getConfineProvince(),
						bulkEmailForm.getConfineStudentsregion(),
						bulkEmailForm.getConfineStudentsResidentialregion(),
						bulkEmailForm.getConfineRace(),
						sites, bulkEmailForm, bulkEmailForm.getReplyAddress());
			}

			 catch(AddressException AdreEx)
			  { System.out.println(AdreEx.getLocalizedMessage());}

				//display the message. Logic to NOT insert into the DB resides in the sendMail method of BulkEmailDAO
				if(!bulkEmailForm.isStudentExists()) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "No students exist for your specific query"));
					addErrors(request, messages);
				}else {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Your e-mail has been sent to " + studentsWhomReceivedMailCount + " student(s). Thank you."));
						addErrors(request, messages);
				}

				request.setAttribute("sitesPerLecturer", bulkEmailForm.getSites());
				bulkEmailForm.setIndexNrOfSelectedSite(null);
				bulkEmailForm.setStudentExists(false);
				return mapping.findForward("step1");
			}
		}

		//check to see if the user has input any text for the subject and the message
		if(bulkEmailForm.getMessageSubject() == null | bulkEmailForm.getMessageSubject().equalsIgnoreCase("")) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please insert text in the subject field."));
			addErrors(request, messages);
			return mapping.findForward("preview");
		}
		if(bulkEmailForm.getMessageText() == null | bulkEmailForm.getMessageText().equalsIgnoreCase("")) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please insert text in the message field."));
			addErrors(request, messages);
			return mapping.findForward("preview");
		}

		return mapping.findForward("");

	}


	private ArrayList getGenderList(){
		//Construct value list for gender -- NOT using a look up table
		ArrayList gender = new ArrayList();
		gender.add(new org.apache.struts.util.LabelValueBean("Not specified", "notspecified"));
		gender.add(new org.apache.struts.util.LabelValueBean("Male", "M"));
		gender.add(new org.apache.struts.util.LabelValueBean("Female", "F"));

		return gender;
	}


	private void addConfinements2RequestObject(HttpServletRequest request, BulkEmailDAO bulkEmailDAO) throws Exception{
		request.setAttribute("confineGenderList", getGenderList());
		request.setAttribute("homeLanguages", bulkEmailDAO.getHomeLanguages());
		request.setAttribute("countries", bulkEmailDAO.getCountries());
		request.setAttribute("provinces", bulkEmailDAO.getProvinces());
		request.setAttribute("races", bulkEmailDAO.getRaces());
		request.setAttribute("regions", bulkEmailDAO.getRegions());
		request.setAttribute("resregions",bulkEmailDAO.getRegions());
	}

	public void insertIntoForm(
			ActionMapping mapping,
			BulkEmailForm bulkEmailForm,
			HttpServletRequest request,
			HttpServletResponse response, String[] sitesSelected) throws Exception {



		ArrayList mySites = new ArrayList();
		ArrayList allSites = new ArrayList();

		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		bulkEmailForm.setFrom(bulkEmailForm.getUserName());
		bulkEmailForm.setDate(sdf.format(d));
		bulkEmailForm.setMessageText(escapeQuotes(bulkEmailForm.getMessageText()));
		allSites = bulkEmailForm.getSites();
		String s = "";

		for(int j = 0; j < sitesSelected.length; j++) {
			s = ((StudentListSites)(allSites.get(Integer.parseInt(sitesSelected[j])))).getLecturerSites();
			mySites.add(s);
		}
		request.setAttribute("mySites", mySites);
	}

	private String escapeQuotes(String message) {
		message.replaceAll("\'", "\\\\\'");
		return message;
	}

	private boolean validateEmail(BulkEmailForm form) {
		String replyAddress = form.getReplyAddress();
		if(!Pattern.matches(".+@.+\\.[a-z]+",replyAddress)) {
			form.setReplyAddress("enter reply -email address");
			return false;
		}

		return true;
	}
}
