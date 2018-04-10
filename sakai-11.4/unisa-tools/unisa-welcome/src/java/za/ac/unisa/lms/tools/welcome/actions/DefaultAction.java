//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.welcome.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.sakaiproject.tool.api.Session;
/*mport org.sakaiproject.tool.cover.SessionManager;*/
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserDirectoryService;

import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.tool.api.SessionManager;

/**
 * MyEclipse Struts
 * Creation date: 08-18-2005
 *
 * XDoclet definition:
 * @struts:action parameter="action" validate="true"
 */
public class DefaultAction extends DispatchAction {
	public static Log log = LogFactory.getLog(DefaultAction.class);
	
	private SessionManager sessionManager;

	private UserDirectoryService userDirectoryService;
	


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
		HttpServletResponse response) throws Exception {

		sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
		userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);

	/*	log.info("Welcome (Default) Before Get Current Session ");*/
		Session currentSession = sessionManager.getCurrentSession();
		/*log.info("Welcome (Default) Before Get User "+ );*/
		String userID = currentSession.getUserId();
		/*log.info("Welcome Got User : "+currentSession.getUserEid());*/



		if (userID != null) {
			/*log.info("Welcome Before USERDIRSERVICE "+ currentSession.getUserEid());*/
			User user = userDirectoryService.getUser(userID);
			/*log.info("Welcome After USERDIRSERVICE "+currentSession.getUserEid());*/
			request.setAttribute("user", user);
			/*log.info("Welcome AFTER REQUEST SET "+currentSession.getUserEid());*/
		}

		/*log.info("Welcome DefaultForward "+currentSession.getUserEid());*/
		return mapping.findForward("defaultForward");
	}

}

