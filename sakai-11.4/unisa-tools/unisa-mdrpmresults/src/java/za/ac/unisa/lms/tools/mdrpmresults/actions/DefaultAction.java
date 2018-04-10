package za.ac.unisa.lms.tools.mdrpmresults.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserDirectoryService;

public class DefaultAction extends DispatchAction {

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
		
		SessionManager sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
		UserDirectoryService userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
		Session currentSession = sessionManager.getCurrentSession();	
		String userID = currentSession.getUserId();
		
		if (userID != null) {
			User user = userDirectoryService.getUser(userID);
			request.setAttribute("user", user);
		}
		return mapping.findForward("default");
	}
}