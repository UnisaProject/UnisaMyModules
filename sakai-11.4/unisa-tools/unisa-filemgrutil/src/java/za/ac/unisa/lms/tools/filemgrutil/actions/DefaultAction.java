package za.ac.unisa.lms.tools.filemgrutil.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;


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
		/*ComponentManager sakaiCompMgr = org.sakaiproject.component.cover.ComponentManager.getInstance();
		SessionManager sakaiSessionManager = (SessionManager) sakaiCompMgr.get("org.sakaiproject.api.kernel.session.SessionManager");
		UserDirectoryService userDirService = (UserDirectoryService) sakaiCompMgr.get("org.sakaiproject.service.legacy.user.UserDirectoryService");
		Session currentSession = sakaiSessionManager.getCurrentSession();	
		String userID = currentSession.getUserId();
		
		if (userID != null) {
			User user = userDirService.getUser(userID);
			request.setAttribute("user", user);
		}*/
		return mapping.findForward("default");
	}
}