package za.ac.unisa.lms.tools.telecentre.actions;

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
	
	private UserDirectoryService userDirectoryService;
	private SessionManager sessionManager;
	
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
		/*userDirectoryService= (UserDirectoryService)ComponentManager.get(UserDirectoryService.class);
		sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
		//ComponentManager sakaiCompMgr = org.sakaiproject.component.cover.ComponentManager.getInstance();
		//SessionManager sakaiSessionManager = (SessionManager) sakaiCompMgr.get("org.sakaiproject.api.kernel.session.SessionManager");
		//UserDirectoryService userDirService = (UserDirectoryService) sakaiCompMgr.get("org.sakaiproject.service.legacy.user.UserDirectoryService");
		Session currentSession = sessionManager.getCurrentSession();	
		String userID = currentSession.getUserEid();
		if (userID != null) {
			User user = userDirectoryService.getUserByEid(userID);
			request.setAttribute("user", user);
		}*/
		return mapping.findForward("default");
	}
}