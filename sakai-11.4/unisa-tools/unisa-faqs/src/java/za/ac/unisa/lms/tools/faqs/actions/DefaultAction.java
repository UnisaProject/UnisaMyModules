package za.ac.unisa.lms.tools.faqs.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.sakaiproject.tool.api.Session;

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

		/*Session currentSession = SessionManager.getCurrentSession();	
		String userID = currentSession.getUserId();
		
		if (userID != null) {
			User user = UserDirectoryService.getUser(userID);
			request.setAttribute("user", user);
		}
		*/
		return mapping.findForward("default");
	}
}