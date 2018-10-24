//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.studentemail.actions;


import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.LookupDispatchAction;
import org.apache.struts.util.MessageResources;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.email.api.EmailService;
import org.sakaiproject.event.api.UsageSession;
import org.sakaiproject.event.api.EventTrackingService;
import org.sakaiproject.event.api.UsageSessionService;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.tool.api.ToolManager;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserDirectoryService;

import za.ac.unisa.lms.constants.EventTrackingTypes;
import za.ac.unisa.lms.tools.studentemail.Student;
import za.ac.unisa.lms.tools.studentemail.dao.SakaiQueryDAO;
import za.ac.unisa.lms.tools.studentemail.dao.StudentQueryDao;
import za.ac.unisa.lms.tools.studentemail.forms.DisplayemailForm;
import za.ac.unisa.utils.CoursePeriod;
import za.ac.unisa.utils.CoursePeriodLookup;


/**
 * MyEclipse Struts
 * Creation date: 12-27-2005
 *
 * XDoclet definition:
 * @struts:action path="/displayemail" name="displayemailForm"
 * @struts:action-forward name="displayStudent" path="/WEB-INF/jsp/displaystudent.jsp"
 * @struts:action-forward name="displayLectuer" path="/WEB-INF/jsp/displayLecturer"
 */
public class DisplayemailAction extends LookupDispatchAction {
	
	private EmailService emailService;
	private EventTrackingService eventTrackingService;
	private UsageSessionService usageSessionService;
	private SessionManager sessionManager;
	private ToolManager toolManager;
	private UserDirectoryService userDirectoryService;
	


	protected Map<String, String> getKeyMethodMap() {
	      Map<String, String> map = new HashMap<String, String>();
	      map.put("input","input");
	      map.put("displayStudent","displayStudent");
	      map.put("displayLecturer","displayLecturer");
	      map.put("lecturer.submit","submitlecturer");
	      map.put("student.send","sendmessage");
	      map.put("student.preview","previewmessage");
	      map.put("student.clear","clear");
	      map.put("student.submit","sendmessage");
	      map.put("student.back","back");
	      return map;
	  }

	public ActionForward input(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
		userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
		toolManager = (ToolManager) ComponentManager.get(ToolManager.class);

		DisplayemailForm displayemailform = (DisplayemailForm) form;
		StudentQueryDao db = new StudentQueryDao();

		// Get user
		Session currentSession = sessionManager.getCurrentSession();
		String userId = currentSession.getUserId();
		User user = null;

		//the user object is set in default action
		if(null != request.getAttribute("user")) {
			user = (User)request.getAttribute("user");
		}

		if (userId != null) {
			user = userDirectoryService.getUser(userId);
			request.setAttribute("user", user);
		}
		
		UsageSession usageSession = usageSessionService.getSession();
		eventTrackingService.post(
				eventTrackingService.newEvent(EventTrackingTypes.EVENT_STUDENTEMAIL_VIEW, toolManager.getCurrentPlacement().getContext(), true),usageSession);

		Student student = new Student();
		if ("Student".equalsIgnoreCase(user.getType())) {
			/*
			 * Student
			 */
			student.setNumber(user.getEid());
			student.setName(user.getDisplayName());
			try {
				student.setSemail(db.getStudentEmail(student.getNumber()));
			} catch (Exception ex) {
				throw ex;
			}
			displayemailform.setStudent(student);
			displayemailform.setStudentUser(true);
			return displayStudentForm(mapping, form, request, response);
		} else if ("Instructor".equalsIgnoreCase(user.getType())) {
			/*
			 * Lecturer Get student nr input
			 */
			displayemailform.setStudentUser(false);
			return displayLecturerForm(mapping, form, request, response);
		} else if ("admin".equalsIgnoreCase(user.getType())){
			/*
			 * Admin Get student nr input
			 */
			displayemailform.setStudentUser(false);
			return displayLecturerForm(mapping, form, request, response);
		} else {
			/*
			 * TO DO !!!! User unknown Go to error screen
			 */

			throw new Exception("Payment : User type unknown");
		}
	}
	public ActionForward displayStudentForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DisplayemailForm displayemailform = (DisplayemailForm) form;
		SakaiQueryDAO db = new SakaiQueryDAO();
		displayemailform.setNoemail(db.checkEmail(toolManager.getCurrentPlacement().getContext()));
		return mapping.findForward("displayStudent");

	}
	public ActionForward displayLecturerForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		toolManager = (ToolManager) ComponentManager.get(ToolManager.class);
		DisplayemailForm displayemailForm = (DisplayemailForm) form;
		SakaiQueryDAO db1 = new SakaiQueryDAO();
		try {
			if (db1.checkEmail(toolManager.getCurrentPlacement().getContext())){
				try {
					String tempemail = db1.getEmail(toolManager.getCurrentPlacement().getContext());
					if((toolManager.getCurrentPlacement().getContext()+"@unisa.ac.za").equals(tempemail)) {
						displayemailForm.setChoice("2");
					} else {
						displayemailForm.setChoice("3");
						displayemailForm.setEmailaddres(tempemail);
					}
				} catch (Exception ex) {
					throw ex;
				}
			} else {
				displayemailForm.setChoice("1");
			}
		 } catch (Exception ex) {
	            throw ex;
		 }

		 displayemailForm.setCourse(toolManager.getCurrentPlacement().getContext());

		 return mapping.findForward("displayLecturer");
	}
	public ActionForward submitlecturer(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
		toolManager = (ToolManager) ComponentManager.get(ToolManager.class);
		/** SITE ID **/
		ActionMessages message = new ActionMessages();


		/* for testing only the following statments
		String siteId = "APC101D-05-S1";
		CoursePeriod coursePeriod = CoursePeriodLookup.getCoursePeriod(siteId);*/
		/*Until here  if not testing un-comment next line */
		 CoursePeriod coursePeriod = CoursePeriodLookup.getCoursePeriod();
		String email="";
		DisplayemailForm displayemailform = (DisplayemailForm) form;
		SakaiQueryDAO db = new SakaiQueryDAO();
		if ("3".equals(displayemailform.getChoice())){
			message = (ActionMessages) displayemailform.validate(mapping, request);
			email = displayemailform.getEmailaddres();
		}
		if ("1".equals(displayemailform.getChoice())||"2".equals(displayemailform.getChoice())){
			displayemailform.setEmailaddres("");
		}

		if (!message.isEmpty()){
			addErrors(request,message);
		} else {
			UsageSession usageSession = usageSessionService.startSession(sessionManager.getCurrentSession().getUserEid(), request.getRemoteAddr(), request.getHeader("user-agent"));
	        eventTrackingService.post(
					eventTrackingService.newEvent(EventTrackingTypes.EVENT_STUDENTEMAIL_CHANGEEMAIL, toolManager.getCurrentPlacement().getContext()+"/"+displayemailform.getChoice(), true),usageSession);

			db.setEmail(displayemailform.getChoice(),email,coursePeriod.getCourseCode(),coursePeriod.getYear(),coursePeriod.getSemesterPeriod());
			message.add("sucess",new ActionMessage("message.sucess"));
			addMessages(request,message);
		}


		return mapping.findForward("displayLecturer");


	}
	public ActionForward sendmessage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
		userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
		toolManager = (ToolManager) ComponentManager.get(ToolManager.class);
		emailService = (EmailService) ComponentManager.get(EmailService.class);
		
		DisplayemailForm displayemailform = (DisplayemailForm) form;
		ActionMessages message = new ActionMessages();
		MessageResources messageResources = getResources(request);


		if (displayemailform.getSubject()==null || "".equals(displayemailform.getSubject())){
			message.add("Subject", new ActionMessage("errors.required", messageResources.getMessage("errors.subject",request)));
		}
		if (displayemailform.getMessage()==null || "".equals(displayemailform.getMessage())){
			message.add("Message", new ActionMessage("errors.required", messageResources.getMessage("errors.message",request)));
		}
		if (!message.isEmpty()){
			addErrors(request,message);
		} else {
			// Get user

			Session currentSession = sessionManager.getCurrentSession();
			String userId = currentSession.getUserId();
			User user = null;
			if (userId != null) {
				user = userDirectoryService.getUser(userId);
			}
			String activatePath = ServerConfigurationService.getServerName();
			String email = toolManager.getCurrentPlacement().getContext()+"@"+activatePath;
			String body = displayemailform.getMessage();
			body = "Student number : " + displayemailform.getStudent().getNumber()+"\n" +
			          "Name           : " + displayemailform.getStudent().getName()+"\n\n"+ body;
			try {
				String tmpEmailFrom = user.getEmail();
				InternetAddress toEmail = new InternetAddress(email);
				InternetAddress iaTo[] = new InternetAddress[1];
				iaTo[0] = toEmail;
				InternetAddress iaHeaderTo[] = new InternetAddress[1];
				iaHeaderTo[0] = toEmail;
				InternetAddress iaReplyTo[] = new InternetAddress[1];
				iaReplyTo[0] = new InternetAddress(tmpEmailFrom);
				emailService.sendMail(iaReplyTo[0],iaTo,displayemailform.getSubject(),body,iaHeaderTo,iaReplyTo,null);
				UsageSession usageSession = usageSessionService.startSession(user.getEid(), request.getRemoteAddr(), request.getHeader("user-agent"));
		        eventTrackingService.post(
						eventTrackingService.newEvent(EventTrackingTypes.EVENT_STUDENTEMAIL_SENDMAIL, toolManager.getCurrentPlacement().getContext(), true),usageSession);
			} catch (Exception ex){
				throw ex;
			}
			message.add("Sendemail", new ActionMessage("student.sendEmail"));
			addMessages(request,message);
		}
		return clear(mapping, form, request,response);

	}
	public ActionForward previewmessage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DisplayemailForm displayemailform = (DisplayemailForm) form;
		ActionMessages message = new ActionMessages();
		MessageResources messageResources = getResources(request);
		if (displayemailform.getSubject()==null || "".equals(displayemailform.getSubject())){
			message.add("Subject", new ActionMessage("errors.required", messageResources.getMessage("errors.subject",request)));
		}
		if (displayemailform.getMessage()==null || "".equals(displayemailform.getMessage())){
			message.add("Message", new ActionMessage("errors.required", messageResources.getMessage("errors.message",request)));
		}
		if (!message.isEmpty()){
			addErrors(request,message);
			return mapping.findForward("displayStudent");
		} else {
			return mapping.findForward("displayPreview");
		}

	}
	public ActionForward clear(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DisplayemailForm displayemailForm = (DisplayemailForm) form;
		displayemailForm.setMessage("");
		displayemailForm.setSubject("");
		return input(mapping, form, request, response);
	}

	public ActionForward back(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("displayStudent");
	}

}

