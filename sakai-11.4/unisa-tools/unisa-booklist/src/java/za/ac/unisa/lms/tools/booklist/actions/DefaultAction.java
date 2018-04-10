package za.ac.unisa.lms.tools.booklist.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.event.api.UsageSession;
import org.sakaiproject.event.api.EventTrackingService;
import org.sakaiproject.event.api.UsageSessionService;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.tool.api.ToolManager;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserDirectoryService;

public class DefaultAction extends DispatchAction {
	
	private EventTrackingService eventTrackingService;
	private UsageSessionService usageSessionService;
    private SessionManager sessionManager;
    private UserDirectoryService userDirectoryService;
    private ToolManager toolManager;
	// --------------------------------------------------------- Instance Variables

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
		
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
		sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
		userDirectoryService= (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
		toolManager= (ToolManager) ComponentManager.get(ToolManager.class);
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

