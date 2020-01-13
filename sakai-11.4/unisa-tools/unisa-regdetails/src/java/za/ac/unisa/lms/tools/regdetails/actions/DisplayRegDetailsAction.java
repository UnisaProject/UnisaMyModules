//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.regdetails.actions;

import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.event.api.UsageSession;
import org.sakaiproject.event.api.EventTrackingService;
import org.sakaiproject.event.api.UsageSessionService;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.tool.api.ToolManager;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserDirectoryService;

import za.ac.unisa.lms.constants.EventTrackingTypes;
import za.ac.unisa.lms.dao.Gencod;
import za.ac.unisa.lms.dao.StudentSystemGeneralDAO;
import za.ac.unisa.lms.tools.regdetails.dao.AdditionQueryDAO;
import za.ac.unisa.lms.tools.regdetails.dao.RegQueryDAO;
import za.ac.unisa.lms.tools.regdetails.forms.RegDetailsForm;
import za.ac.unisa.lms.tools.regdetails.forms.StudyUnit;

import org.sakaiproject.component.cover.ComponentManager;

/**
 * MyEclipse Struts
 * Creation date: 10-14-2005
 *
 * XDoclet definition:
 * @struts:action parameter="action"
 */
public class DisplayRegDetailsAction extends DispatchAction {

	private SessionManager sessionManager;
	private UserDirectoryService userDirectoryService;
	private EventTrackingService eventTrackingService;
	private ToolManager toolManager;
	private UsageSessionService usageSessionService;

	// --------------------------------------------------------- Methods

	public ActionForward display(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		boolean getStudentDetail = false;
		RegDetailsForm regDetailsForm = (RegDetailsForm) form;
		sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
		userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
		toolManager = (ToolManager) ComponentManager.get(ToolManager.class);
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
		HttpSession session = request.getSession();

		/* Get user details, set in default action */
		String userId = "";
		String userEid = "";
		User user = (User) request.getAttribute("user");
		if (user == null) {
			Session currentSession = sessionManager.getCurrentSession();
			userId = currentSession.getUserId();
			if (userId != null) {
				user = userDirectoryService.getUser(userId);
				userEid = user.getEid();
				request.setAttribute("user", user);
			}
		} else {
			request.setAttribute("user", user);
			userEid = user.getEid();
		}
		if (user.getType() == null || !"student".equalsIgnoreCase(user.getType())) {
			return mapping.findForward("invaliduser");
		} else {
			regDetailsForm.setStudentNr(userEid);
		}

		RegQueryDAO db = new RegQueryDAO();
				
		/* read student application for study units */
		regDetailsForm.setApplyForStudyUnits(db
				.getApplyForStudyUnits(regDetailsForm.getStudentNr()));
		/* read student supplement study units */
		regDetailsForm.setSupplementStudyUnits(db
				.getSupplStudyUnits(regDetailsForm.getStudentNr()));
		/* read student registered study units */
		regDetailsForm.setStudyUnits(db.getRegisteredStudyUnits(regDetailsForm
				.getStudentNr()));
		/* setup pick list for cancellations and exchanges */
		ArrayList list = new ArrayList();
		for (int i = 0; i < regDetailsForm.getStudyUnits().size(); i++) {
			StudyUnit su = new StudyUnit();
			su = (StudyUnit) regDetailsForm.getStudyUnits().get(i);
			//log.debug("DisplayRegDetailsAction - display - list getStudyUnits="+su.getCode()+", "+su.getStatus());
			if (Integer.parseInt(su.getSupplCode()) < 2
					&& "Registered".equalsIgnoreCase(su.getStatus())) {
				list.add(su);
			}
		}
		/* Check for stuann */
		regDetailsForm.setRegAnnual(db.existsStudentAnnual(regDetailsForm
				.getStudentNr()));

		//log.debug("DisplayRegDetailsAction - display - getStudyUnits="+regDetailsForm.getStudyUnits().size());
		//log.debug("DisplayRegDetailsAction - display - getApplyForStudyUnits="+regDetailsForm.getApplyForStudyUnits().size());
		//log.debug("DisplayRegDetailsAction - display - getSupplementStudyUnits="+regDetailsForm.getSupplementStudyUnits().size());
		//log.debug("DisplayRegDetailsAction - display - isRegAnnual="+regDetailsForm.isRegAnnual());
		//log.debug("DisplayRegDetailsAction - display - getAcadYear="+regDetailsForm.getAcadYear());
		//log.debug("DisplayRegDetailsAction - display - getAcadYear="+regDetailsForm.getAcadPeriod());

		if (regDetailsForm.getStudyUnits().isEmpty()
		  && regDetailsForm.getApplyForStudyUnits().isEmpty()
		  && regDetailsForm.getSupplementStudyUnits().isEmpty()
		  && !regDetailsForm.isRegAnnual()) {
			/* Student not registered */
			String regPath = ServerConfigurationService
					.getString("registration.path");
			request.setAttribute("regPath", regPath);
			request.setAttribute("regLink", "<a href='" + regPath
					+ "' target='_blank'>" + regPath + "</a>");
			session.setAttribute("showNotRegLink", "true");
			session.setAttribute("showLinks", "false");
			return mapping.findForward("notregistered");
		} else if (regDetailsForm.getStudyUnits().isEmpty()
				&& !regDetailsForm.getApplyForStudyUnits().isEmpty()
				&& !regDetailsForm.getSupplementStudyUnits().isEmpty()
				&& !regDetailsForm.isRegAnnual()) {
			/* No stuann
			 * No registered units
			 * No supplements
			 * Has application for study units only*/
			getStudentDetail = true;
			session.setAttribute("showNotRegLink", "true");
		} else if (regDetailsForm.getStudyUnits().isEmpty()
				&& regDetailsForm.getApplyForStudyUnits().isEmpty()
				&& !regDetailsForm.getSupplementStudyUnits().isEmpty()
				&& regDetailsForm.isRegAnnual()) {
			/* ONLY the following is true for student:
			 *  1. A Stuann (CA/RG) for acad year
			 * 	2. suppl/aegrotat/special exams */
			getStudentDetail = true;
			session.setAttribute("showNotRegLink", "true");
		} else if (regDetailsForm.getStudyUnits().isEmpty()
				&& regDetailsForm.getApplyForStudyUnits().isEmpty()
				&& regDetailsForm.getSupplementStudyUnits().isEmpty()
				&& regDetailsForm.isRegAnnual()) {
			/* ONLY the following is true for student:
			 *  1. A Stuann (CA/RG) for acad year */
			getStudentDetail = true;
			session.setAttribute("showNotRegLink", "true");
		} else {
			getStudentDetail = true;
			session.setAttribute("showNotRegLink", "false");
		}
		/*
		 * Determine whether links for additions, cancellations.
		 * etc should show
		 */
		if (regDetailsForm.getStudyUnits().isEmpty()
				&& regDetailsForm.getApplyForStudyUnits().isEmpty()
				&& regDetailsForm.getSupplementStudyUnits().isEmpty()
				&& !regDetailsForm.isRegAnnual()) {
			request.setAttribute("showLinks", "false");
		} else if (regDetailsForm.getStudyUnits().isEmpty()
				&& !regDetailsForm.getApplyForStudyUnits().isEmpty()
				&& regDetailsForm.getSupplementStudyUnits().isEmpty()
				&& !regDetailsForm.isRegAnnual()) {
			// TP or reg application study units only
			request.setAttribute("showLinks", "false");
		}
//		 check account type
		if (db.isWrongAccountType(regDetailsForm.getStudentNr())){
			request.setAttribute("showLinks", "false");
		}
		
		//Change 20181112 - myUnisa unbundling remove links Semester Exchange, Study Unit Cancellations and Request registration letter depending on gencod flag
		StudentSystemGeneralDAO dao = new StudentSystemGeneralDAO();
		request.setAttribute("showAddLinks", "true");
		Gencod gencod = new Gencod();
		gencod = dao.getGenCode("315", "REGDETAILS");
		if (gencod != null && gencod.getAfrDescription()!= null && !gencod.getAfrDescription().trim().equalsIgnoreCase("Y")){
			/*Temporary disable additional links*/
			request.setAttribute("showAddLinks", "false");
		}

		if (getStudentDetail) {
			regDetailsForm.setPickList(list);
			/* read student qualification */
			regDetailsForm.setQual(db.getStudentQualification(regDetailsForm.getStudentNr()));
			/* read student email */
			regDetailsForm.setEmail(db.getStudentEmail(regDetailsForm.getStudentNr()));
			/* get student name */
			regDetailsForm.setStudentName(db.getStudentName(regDetailsForm.getStudentNr()));
			/* set academic year */
			regDetailsForm.setAcadYear(Integer.toString(RegQueryDAO.getCurrentYear()));
			/* set academic period */
			regDetailsForm.setAcadPeriod(getCurrentPeriod());
		}

		UsageSession usageSession = usageSessionService.getSession();
		//UsageSessionService.startSession(regDetailsForm.getStudentNr(),request.getRemoteAddr(),request.getHeader("user-agent"));
		eventTrackingService.post(eventTrackingService.newEvent(
				EventTrackingTypes.EVENT_REGDETAILS_VIEW, toolManager
						.getCurrentPlacement().getContext()
						+ "/" + regDetailsForm.getStudentNr(), false),
				usageSession);

		
		 //Nov 2009 
		 /* non formal student
		  if (regDetailsForm.getStudentNr().length() == 8
				&& "7".equals(regDetailsForm.getStudentNr().substring(0, 1))) {
			regDetailsForm.setNonFormal(true);
		}*/

		AdditionQueryDAO additionQuery = new AdditionQueryDAO();

			// if licensee student, block addition
			if (additionQuery.isLicenseeStudent(regDetailsForm.getStudentNr())){
				ActionMessages messages = new ActionMessages();
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						//new ActionMessage("message.generalmessage", "Please contact your Licensee to assist with the addition of study units."));
						new ActionMessage("message.generalmessage", "You have been registered by a Licensee. To add additional modules, please send/take your request to the Licensee that did your registration."));

				addErrors(request, messages);
				session.setAttribute("showLinks", "false");
				return mapping.findForward("display");
			}
			// if staff member or staff member dependent, block addition
			if (additionQuery.isStaffStudent(regDetailsForm.getStudentNr())){
				ActionMessages messages = new ActionMessages();
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "You have been registered as a Unisa staff member or Unisa staff member dependent. To add additional modules, please follow the same procedure as for registrations and send the request to formalstaffreg@unisa.ac.za"));
				addErrors(request, messages);
				session.setAttribute("showLinks", "false");
				return mapping.findForward("display");
			}
			
		return mapping.findForward("display");
	}
	
	public String getCurrentPeriod() {

		Log log = LogFactory.getLog(RegQueryDAO.class);
		String currentPeriod = "1";
		/* jan = 0, Feb=1 , Nov=10, Dec=11 etc */
		
		if (Calendar.getInstance().get(Calendar.MONTH) > 8 || Calendar.getInstance().get(Calendar.MONTH) <= 3) {
			currentPeriod = "1";
	  	}else{
	  		currentPeriod = "2";
	  	}
		log.debug("Returning "+currentPeriod+" as the current period for registration");

		return currentPeriod;

	}

}

