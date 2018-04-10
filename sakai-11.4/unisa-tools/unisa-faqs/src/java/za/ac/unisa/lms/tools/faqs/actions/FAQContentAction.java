//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.faqs.actions;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.LookupDispatchAction;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.event.api.UsageSession;
import org.sakaiproject.event.api.EventTrackingService;
import org.sakaiproject.event.api.UsageSessionService;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.tool.api.ToolManager;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserNotDefinedException;
import org.sakaiproject.user.api.UserDirectoryService;
import za.ac.unisa.security.SecurityServices;

import za.ac.unisa.lms.constants.EventTrackingTypes;
import za.ac.unisa.lms.tools.faqs.dao.FaqContent;
import za.ac.unisa.lms.tools.faqs.dao.FaqDAO;
import za.ac.unisa.lms.tools.faqs.forms.FAQContentForm;

/**
 * MyEclipse Struts
 * Creation date: 11-22-2005
 *
 * XDoclet definition:
 * @struts:action parameter="action" validate="true"
 */
public class FAQContentAction extends LookupDispatchAction {

	private Log log;

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
	private SessionManager sessionManager;
	private UserDirectoryService userDirectoryService;
	private UsageSessionService usageSessionService;
	private EventTrackingService eventTrackingService;
	private ToolManager  toolManager;

	// retrieve categories
	public ActionForward input(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
			toolManager=(ToolManager)ComponentManager.get(ToolManager.class);
			String siteId = toolManager.getCurrentPlacement().getContext();
		FaqDAO dao = new FaqDAO();
		request.setAttribute("siteId", siteId);
		request.setAttribute("categories", dao.getFaqCategories(siteId));
		return mapping.findForward("input");
	}

	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		SecurityServices ss = new SecurityServices();

		boolean userKnown = true;
		if (isCancelled(request)) {
			return mapping.findForward("cancel");
		}
		setServices();
		sessionManager = (SessionManager)ComponentManager.get(SessionManager.class);	
		Session currentSession = sessionManager.getCurrentSession();
		toolManager=(ToolManager)ComponentManager.get(ToolManager.class);
		String userId = currentSession.getUserId();
		User user = null;
		if (userId != null) {
			user = userDirectoryService.getUser(userId);
		}else {
			userKnown = false;
		}

		FaqDAO dao = new FaqDAO();
		FAQContentForm faqContentForm = (FAQContentForm) form;

		faqContentForm.setContent(dao.getFaqContent(faqContentForm.getContent()
				.getContentId()));

		List contents = dao.getFaqContentIds(faqContentForm.getContent()
				.getCategoryId());
		Iterator i = contents.iterator();
		Integer previousId = null;
		Integer nextId = null;

		while (i.hasNext()) {
			FaqContent q = (FaqContent) i.next();
			if (q.getContentId().equals(
					faqContentForm.getContent().getContentId())) {
				if (i.hasNext()) {
					q = (FaqContent) i.next();
					nextId = q.getContentId();
				}
				continue;
			}
			previousId = q.getContentId();
		}

		faqContentForm.setPreviousId(previousId);
		faqContentForm.setNextId(nextId);

		faqContentForm.setCategory(dao.getFaqCategory(faqContentForm
				.getContent().getCategoryId()));
		if(userKnown) {
			UsageSession usageSession = usageSessionService.getSession();
			eventTrackingService.post(
				eventTrackingService.newEvent(EventTrackingTypes.EVENT_FAQS_CONTENTVIEW, toolManager.getCurrentPlacement().getContext(), false),usageSession);
		}
		return mapping.findForward("view");

	}

	// saving FAQ to the DB
  private void setServices(){
	  eventTrackingService =(EventTrackingService)ComponentManager.get(EventTrackingService.class);
		sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
		usageSessionService=(UsageSessionService)ComponentManager.get(UsageSessionService.class);
		toolManager=(ToolManager)ComponentManager.get(ToolManager.class);
		userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
  }
	public ActionForward inputSave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		log = LogFactory.getLog(this.getClass());
		if (isCancelled(request)) {

			return (mapping.findForward("cancel"));
		}
		setServices();
		sessionManager = (SessionManager)ComponentManager.get(SessionManager.class);	
		Session currentSession = sessionManager.getCurrentSession();
		toolManager=(ToolManager)ComponentManager.get(ToolManager.class);
		String userId = currentSession.getUserId();
		String siteId ="";
		
		User user = null;
		if (userId != null) {
			user = userDirectoryService.getUser(userId);
		}
		UsageSession usageSession = usageSessionService.getSession();

		FaqDAO dao = new FaqDAO();
		FAQContentForm faqContentForm = (FAQContentForm) form;

		log.debug("Category ID on form: "
				+ faqContentForm.getContent().getCategoryId());

		ActionMessages errors = faqContentForm.validate(mapping, request);
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return input(mapping, form, request, response);
		}

		if ((faqContentForm.getContent().getCategoryId().intValue() == -1)
				& (faqContentForm.getCategory().getDescription().length() == 0)) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"message.generalmessage",
					"Please select Category or enter new Category."));
			addErrors(request, errors);
			return input(mapping, form, request, response);
		}
		if ((faqContentForm.getContent().getCategoryId().intValue() != -1)
				& (faqContentForm.getCategory().getDescription().length() > 0)) {
			errors
					.add(
							ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
									"You cannot select a Category and enter a new Category, only one of them is required."));
			addErrors(request, errors);
			return input(mapping, form, request, response);
		}

		System.out.println(" The quesion title value is : "+faqContentForm.getContent().getQuestion());
	  if( (faqContentForm.getContent().getQuestion()==null) ||
			  (faqContentForm.getContent().getQuestion().length()<1 ))
		 {errors
			.add(
					ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
							"You cannot add an FAQ without a Question Title "));
	     addErrors(request, errors);
	return input(mapping, form, request, response);

		}

	    //change to pass the siteId here to fix the bug in site import
			toolManager=(ToolManager)ComponentManager.get(ToolManager.class);
			siteId = toolManager.getCurrentPlacement().getContext();

		if ((faqContentForm.getContent().getCategoryId().intValue() == -1)) {
			dao.insertFaqCategory(faqContentForm.getCategory(),siteId);
			
			faqContentForm.getContent().setCategoryId(
					faqContentForm.getCategory().getCategoryId());
		}

		dao.insertFaqContent(faqContentForm.getContent());

		//if (faqContentForm.getCategory().getDescription().length() == 0){

		if ((faqContentForm.getContent().getCategoryId().intValue() != -1)
				& (faqContentForm.getCategory().getDescription().length() != 0)) {

		eventTrackingService.post(
			eventTrackingService.newEvent(EventTrackingTypes.EVENT_FAQS_CONTENTADD,toolManager.getCurrentPlacement().getContext(), false),usageSession);

		eventTrackingService.post(
		eventTrackingService.newEvent(EventTrackingTypes.EVENT_FAQS_CATEGORYADD,toolManager.getCurrentPlacement().getContext(), false),usageSession);

		return mapping.findForward("save");
		}
		else
		{
		eventTrackingService.post(
				eventTrackingService.newEvent(EventTrackingTypes.EVENT_FAQS_CONTENTADD,toolManager.getCurrentPlacement().getContext(), false),usageSession);
		return mapping.findForward("save");
        }
	}
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		FaqDAO dao = new FaqDAO();
		FAQContentForm faqContentForm = (FAQContentForm) form;

		faqContentForm.setContent(dao.getFaqContent(faqContentForm.getContent()
				.getContentId()));

		faqContentForm.setCategory(dao.getFaqCategory(faqContentForm
				.getContent().getCategoryId()));

		return mapping.findForward("edit");

	}

	public ActionForward editSave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		FaqDAO dao = new FaqDAO();
		FAQContentForm faqContentForm = (FAQContentForm) form;
		setServices();
		sessionManager = (SessionManager)ComponentManager.get(SessionManager.class);	
		Session currentSession = sessionManager.getCurrentSession();
		toolManager=(ToolManager)ComponentManager.get(ToolManager.class);
		String userId = currentSession.getUserId();
		User user = null;
		if (userId != null) {
			user = userDirectoryService.getUser(userId);
		}
		UsageSession usageSession = usageSessionService.getSession();


		ActionMessages errors = faqContentForm.validate(mapping, request);
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return edit(mapping, form, request, response);
		}

		if (faqContentForm.getContent().getQuestion()==null)
		{errors
			.add(
					ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
							"Please provide a Question Title "));
	    addErrors(request, errors);
	return input(mapping, form, request, response);

		}
		 if( (faqContentForm.getContent().getQuestion()==null) ||
				  (faqContentForm.getContent().getQuestion().length()<1 ))
			 {errors
				.add(
						ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
								"Please provide a Question Title "));
		     addErrors(request, errors);
		return input(mapping, form, request, response);
			 }
		dao.updateFaqContent(faqContentForm.getContent());




		eventTrackingService.post(
				eventTrackingService.newEvent(EventTrackingTypes.EVENT_FAQS_CONTENTEDIT,toolManager.getCurrentPlacement().getContext(), false),usageSession);
		return mapping.findForward("save");
	}

	public ActionForward returnToList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		return mapping.findForward("cancel");
	}

	public ActionForward previous(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		FAQContentForm faqContentForm = (FAQContentForm) form;
		faqContentForm.getContent()
				.setContentId(faqContentForm.getPreviousId());
		return view(mapping, form, request, response);
	}

	public ActionForward next(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		FAQContentForm faqContentForm = (FAQContentForm) form;
		faqContentForm.getContent().setContentId(faqContentForm.getNextId());
		return view(mapping, form, request, response);
	}

	protected Map getKeyMethodMap() {
		Map map = new HashMap();
		map.put("button.return", "returnToList");
		map.put("button.previous", "previous");
		map.put("button.next", "next");
		map.put("method.edit", "edit");
		map.put("method.editSave", "editSave");
		map.put("method.input", "input");
		map.put("method.inputSave", "inputSave");
		map.put("method.view", "view");
		return map;
	}
}
//

