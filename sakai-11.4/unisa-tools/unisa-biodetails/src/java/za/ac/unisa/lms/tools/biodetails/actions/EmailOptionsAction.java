//Created by MyEclipse Struts
//XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.biodetails.actions;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.LookupDispatchAction;
import org.sakaiproject.event.api.UsageSession;
import org.sakaiproject.event.api.EventTrackingService;
import org.sakaiproject.event.api.UsageSessionService;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.tool.api.ToolManager;
import org.sakaiproject.user.api.UserDirectoryService;
import org.sakaiproject.component.cover.ComponentManager;

import za.ac.unisa.lms.constants.EventTrackingTypes;
import za.ac.unisa.lms.tools.biodetails.dao.EmailOptionDAO;
import za.ac.unisa.lms.tools.biodetails.forms.BioDetailsForm;

/**
 * MyEclipse Struts
 * Creation date: 11-28-2005
 *
 * XDoclet definition:
 * @struts:action path="smsOptionAction" name="bioDetailsForm" parameter="action" scope="request" validate="true"
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class EmailOptionsAction extends LookupDispatchAction {

	// --------------------------------------------------------- Instance Variables
	private SessionManager sessionManager;
	private UserDirectoryService userDirectoryService;
	private EventTrackingService eventTrackingService;
	private ToolManager toolManager;
	private UsageSessionService usageSessionService;

	// --------------------------------------------------------- Methods

	protected Map getKeyMethodMap() {
		Map map = new HashMap();
		map.put("display", "display");
		map.put("button.submit", "submit");
		map.put("button.cancel", "cancel");
		return map;
	}

	public ActionForward display(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BioDetailsForm bioDetailsForm = (BioDetailsForm) form;
		EmailOptionDAO emailoptiondao = new EmailOptionDAO();
		sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
		userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
		// Get user details, set in default action
		//User user = null;

		Session currentSession = sessionManager.getCurrentSession();
		String userID = currentSession.getUserId();
		if (userID != null) {
			//user = UserDirectoryService.getUser(userID);
			bioDetailsForm.setNumber(userDirectoryService.getUserEid(userID));
		}
		emailoptiondao.getSmsOptions(bioDetailsForm);
		return mapping.findForward("display");
	}

	public ActionForward cancel(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{

		return mapping.findForward("cancel");
	}

	public ActionForward submit(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BioDetailsForm bioDetailsForm = (BioDetailsForm) form;
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
		toolManager = (ToolManager) ComponentManager.get(ToolManager.class);
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
		UsageSession usageSession = usageSessionService.getSession();
		ActionMessages messages = new ActionMessages();

		EmailOptionDAO emailoptiondao = new EmailOptionDAO();
		emailoptiondao.updateSmsOption(bioDetailsForm);
		emailoptiondao.updateExamBlockedOption(bioDetailsForm);

		eventTrackingService.post(
				eventTrackingService.newEvent(EventTrackingTypes.EVENT_BIODETAILS_CORRESPONDENCECHANGE, toolManager.getCurrentPlacement().getContext()+"/"+bioDetailsForm.getNumber(), false),usageSession);

		messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", "The correspondence options have been successfully updated."));
		addMessages(request, messages);
		return mapping.findForward("cancel");
	}

	public ActionForward unspecified(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response){

		log.info("EmailOptionsAction: unspecified method call -no value for parameter in request");

		return mapping.findForward("cancel");

	}

}

