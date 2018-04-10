//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.welcome.actions;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.event.api.UsageSession;
import org.sakaiproject.event.api.EventTrackingService;
import org.sakaiproject.event.api.UsageSessionService;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.tool.api.ToolManager;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserDirectoryService;

import za.ac.unisa.lms.tools.welcome.constants.EventTrackingTypes;
import za.ac.unisa.lms.tools.welcome.dao.WelcomeDisplayQueryDAO;
import za.ac.unisa.lms.tools.welcome.forms.WelcomeDisplayForm;
import za.ac.unisa.security.SecurityServices;


/**
 * MyEclipse Struts
 * Creation date: 11-16-2005
 *
 * XDoclet definition:
 * @struts:action path="/displayWelcome" name="displayWelcomeForm" input="/WEB-INF/jsp/displaywelcome.jsp" parameter="action" scope="request"
 * @struts:action-forward name="display" path="/WEB-INF/jsp/displaywelcome.jsp"
 * @struts:action-forward name="edit" path="/WEB-INF/jsp/edit.jsp"
 */
public class WelcomeDisplayAction extends LookupDispatchAction {
	
	public static Log log = LogFactory.getLog(WelcomeDisplayAction.class);

	private String siteId = null;
	
	private SessionManager sessionManager;
	private ToolManager toolManager;
	private UserDirectoryService userDirectoryService;
	private UsageSessionService usageSessionService;
	private EventTrackingService eventTrackingService;
	
	
	private String getSiteId(HttpServletRequest request) throws Exception {
		toolManager = (ToolManager) ComponentManager.get(ToolManager.class);
		siteId = toolManager.getCurrentPlacement().getContext();
		return siteId;
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		return super.execute(mapping, form, request, response);
	}

	protected Map<String, String> getKeyMethodMap() {
	      Map<String, String> map = new HashMap<String, String>();
	      map.put("button.cancel", "cancel");
	      map.put("display","display");
	      map.put("edit","edit");
	      map.put("button.submit","saveContent");
	      map.put("Submit","saveContent");
	      map.put("button.revert","revert");
	      return map;
	}

	/**
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward display(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) throws Exception {
		WelcomeDisplayForm welcomeDisplayForm = (WelcomeDisplayForm) form;
		welcomeDisplayForm.setSiteid(getSiteId(request));
		/*welcomeDisplayForm.setSiteid("INF206D-06-Y1");*/
		// Get user
		sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
		Session currentSession = sessionManager.getCurrentSession();
		String userId = currentSession.getUserId();
		
		User user = null;
		if (userId != null) {
			userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
			user = userDirectoryService.getUser(userId);
			request.setAttribute("user", user);
		}
		/*log.info("Welcome get user "+ user.getEid());*/


		/*welcomeDisplayForm.setMaintainUser(stdb.getMaintainLevel());*/
		SecurityServices ss = new SecurityServices();
		String siteReference = ss.getSiteReference();
		welcomeDisplayForm.setMaintainUser(ss.checkPermission(siteReference,"welcome.update"));


		WelcomeDisplayQueryDAO db = new WelcomeDisplayQueryDAO();
		try {
			/*log.info("Welcome getConent"+ user.getEid());*/
			welcomeDisplayForm.setContent(db.getContent(welcomeDisplayForm.getSiteid()));
		} catch (Exception ex) {
            throw ex;
		}
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
		UsageSession usageSession = usageSessionService.getSession();
		/*log.info("Welcome UsageSession"+ user.getEid());*/
/*		UsageSession usageSession = UsageSessionService.startSession(user.getEid(), request.getRemoteAddr(), request.getHeader("user-agent"));
		log.info("Welcome EventTracking"+ user.getEid());*/
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
		toolManager = (ToolManager) ComponentManager.get(ToolManager.class);
		eventTrackingService.post(
				eventTrackingService.newEvent(EventTrackingTypes.EVENT_WELCOME_VIEW, toolManager.getCurrentPlacement().getContext(), false),usageSession);
		/*EventTrackingService.post(
				EventTrackingService.newEvent(EventTrackingTypes.EVENT_WELCOME_VIEW, "INF206D-06-Y1", false));*/
		/*log.info("Welcome Before mapping.findforward"+ user.getEid());*/
		return mapping.findForward("welcomedisplayForward");
	}
	public ActionForward edit(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception  {
		WelcomeDisplayForm welcomeDisplayForm = (WelcomeDisplayForm) form;
		toolManager = (ToolManager) ComponentManager.get(ToolManager.class);
		welcomeDisplayForm.setSiteid(toolManager.getCurrentPlacement().getContext());
		/*welcomeDisplayForm.setSiteid("INF206D-06-Y1");*/

		WelcomeDisplayQueryDAO db = new WelcomeDisplayQueryDAO();
		try {
			welcomeDisplayForm.setContent(db.getContent(welcomeDisplayForm.getSiteid()));
		} catch (Exception ex) {
            throw ex;
		}


		return mapping.findForward("editForward");
	}
	public ActionForward saveContent(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		WelcomeDisplayForm welcomeDisplayForm = (WelcomeDisplayForm) form;
		toolManager = (ToolManager) ComponentManager.get(ToolManager.class);
		welcomeDisplayForm.setSiteid(toolManager.getCurrentPlacement().getContext());
		/*welcomeDisplayForm.setSiteid("INF206D-06-Y1");*/
		WelcomeDisplayQueryDAO db = new WelcomeDisplayQueryDAO();
		String content = welcomeDisplayForm.getContent();
		String regex = "<[^>]*>";
		Pattern p2 = Pattern.compile(regex);
		Matcher m2 = p2.matcher(content);
		log.info("String before sub :*"+content+"*");
		content = m2.replaceAll("");
		log.info("String after sub :*"+content+"*");
		regex = "([A-Za-z])";

		Pattern p = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
		int charcount = 0;
		Matcher m = p.matcher(content);
		while (m.find()){
			charcount++;
		}
		log.info("charcount "+charcount);
		if (charcount > 0){
			db.writeContent(welcomeDisplayForm.getSiteid(),welcomeDisplayForm.getContent());
			usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
			sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
			UsageSession usageSession = usageSessionService.startSession(sessionManager.getCurrentSession().getUserEid(), request.getRemoteAddr(), request.getHeader("user-agent"));
			eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
			toolManager = (ToolManager) ComponentManager.get(ToolManager.class);
			eventTrackingService.post(
					eventTrackingService.newEvent(EventTrackingTypes.EVENT_WELCOME_UPDATE, toolManager.getCurrentPlacement().getContext(), false),usageSession);
			/*EventTrackingService.post(
					EventTrackingService.newEvent(EventTrackingTypes.EVENT_WELCOME_UPDATE, "INF206D-06-Y1", false));*/
			return display(mapping, form, request, response);
		} else {
			ActionMessages messages = new ActionMessages();
			messages.add("InvalidContent", new ActionMessage("error.invalid"));
			addErrors(request,messages);
			return edit(mapping,form,request,response);
		}
	}

	public ActionForward cancel(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)throws Exception{
		return display(mapping, form, request, response);
	}
	public ActionForward revert(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)throws Exception{
		WelcomeDisplayQueryDAO db = new WelcomeDisplayQueryDAO();
		WelcomeDisplayForm welcomeDisplayForm = (WelcomeDisplayForm) form;
		toolManager = (ToolManager) ComponentManager.get(ToolManager.class);
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
		welcomeDisplayForm.setSiteid(toolManager.getCurrentPlacement().getContext());
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
		db.revertMessage(welcomeDisplayForm.getSiteid());
		UsageSession usageSession = usageSessionService.startSession(sessionManager.getCurrentSession().getUserEid(), request.getRemoteAddr(), request.getHeader("user-agent"));
		eventTrackingService.post(
				eventTrackingService.newEvent(EventTrackingTypes.EVENT_WELCOME_REVERT, toolManager.getCurrentPlacement().getContext(), false),usageSession);
		return display(mapping, form, request, response);
	}
}

