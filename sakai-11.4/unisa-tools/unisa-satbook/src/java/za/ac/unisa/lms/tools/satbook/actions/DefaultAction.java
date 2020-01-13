package za.ac.unisa.lms.tools.satbook.actions;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.user.api.User;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.event.api.UsageSession;
import org.sakaiproject.event.api.UsageSessionService;
import org.sakaiproject.user.api.UserDirectoryService;
import za.ac.unisa.lms.tools.satbook.dao.SatbookDAO;
public class DefaultAction extends DispatchAction {
	// --------------------------------------------------------- Instance Variables
		private SessionManager sessionManager;
		private UserDirectoryService userDirectoryService;
		private UsageSessionService usageSessionService;
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

    		sessionManager=(SessionManager) ComponentManager.get(SessionManager.class);
    		userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
    		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
    	
    		Session currentSession = sessionManager.getCurrentSession();
            String userID = currentSession.getUserId();
            String systemID="2";
            request.setAttribute("systemID",systemID);

            if (userID != null) {
            	User user = userDirectoryService.getUser(userID);
                  request.setAttribute("user", user.getEid());
            }else{
               /* to test venue booking locally comment out systemID =request.getParameter("systemID");
                 * and  userID = request.getParameter("UserID");
                 * AND set systemID = "2" and userID="use user id"
                 */
                systemID =request.getParameter("systemID");
                request.setAttribute("systemID",systemID);
                userID = request.getParameter("UserID");
           		//userID  = "syzelle";
                request.setAttribute("user",userID);
                if (systemID.equals("2")) {
                  //UsageSession usageSession = UsageSessionService.startSession(userID, request.getRemoteAddr(), request.getHeader("user-agent"));
                  UsageSession usageSession = usageSessionService.startSession(userID, request.getRemoteAddr(), request.getHeader("user-agent"));
                  request.getSession().setAttribute("UsageSession", usageSession);
                  /* set the user id and eid as it is being set through from staffdev
                   * and not retrieved from myUnisa login
                   */
                  currentSession.setUserEid(userID);
                  SatbookDAO db1 = new SatbookDAO();
                  currentSession.setUserId(db1.selectUserId(userID));
                }
            }
               return mapping.findForward("default");
      }
}
