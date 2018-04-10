//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.contactus.actions;

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
/** 
 * MyEclipse Struts
 * Creation date: 08-18-2005
 * 
 * XDoclet definition:
 * @struts:action parameter="action" validate="true"
 */
public class DefaultAction extends DispatchAction {

	// --------------------------------------------------------- Instance Variables
		private SessionManager sessionManager;
		private UserDirectoryService userDirectoryService;
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
		HttpServletResponse response) throws Exception {

		sessionManager=(SessionManager) ComponentManager.get(SessionManager.class);
		userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
		Session currentSession = sessionManager.getCurrentSession();	
		String userID = currentSession.getUserId();
		
		if (userID != null) {
			User user = userDirectoryService.getUser(userID);
			request.setAttribute("user", user);
		}
		return mapping.findForward("default");
		
	}

}

