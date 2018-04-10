package za.ac.unisa.lms.tools.nosite.actions;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.actions.LookupDispatchAction;

import org.sakaiproject.event.api.UsageSession;
import org.sakaiproject.event.api.EventTrackingService;
import org.sakaiproject.event.api.UsageSessionService;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.tool.api.ToolManager;

//import org.sakaiproject.tool.api.Session;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserDirectoryService;
import org.sakaiproject.component.cover.ComponentManager;

public class DefaultAction extends DispatchAction {

	// --------------------------------------------------------- Instance Variables
	private SessionManager sessionManager;
	private UserDirectoryService userDirectoryService;
	private UsageSessionService usageSessionService;
	private EventTrackingService eventTrackingService;
	private ToolManager toolManager;


	// --------------------------------------------------------- Methods

	/**
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */


	public ActionForward execute(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response
	) throws Exception {
		sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
		userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
		toolManager = (ToolManager) ComponentManager.get(ToolManager.class);
		Session currentSession = sessionManager.getCurrentSession();
		String userID = currentSession.getUserId();

		UsageSession usageSession = usageSessionService.getSession();
		if (userID != null) {
			User user = userDirectoryService.getUser(userID);
			request.setAttribute("user", user);
		}
		eventTrackingService.post(
				eventTrackingService.newEvent("nosite.view", toolManager.getCurrentPlacement().getContext(), false),usageSession);

		return mapping.findForward("default");
	}
}