//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.faqs.actions;

import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.sakaiproject.event.api.UsageSession;
import org.sakaiproject.event.api.EventTrackingService;
import org.sakaiproject.event.api.UsageSessionService;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.tool.api.ToolManager;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserDirectoryService;
import org.sakaiproject.component.cover.ComponentManager;

import za.ac.unisa.lms.constants.EventTrackingTypes;
import za.ac.unisa.lms.tools.faqs.dao.FaqCategory;
import za.ac.unisa.lms.tools.faqs.dao.FaqDAO;
import za.ac.unisa.lms.tools.faqs.forms.FAQCategoryForm;
import za.ac.unisa.lms.tools.faqs.forms.FAQContentForm;

/**
 * MyEclipse Struts
 * Creation date: 11-11-2005
 *
 * XDoclet definition:
 * @struts:action path="createCategory" name="faqCategoryForm" parameter="action" scope="request" validate="true"
 * @struts:action-forward name="createCategory" path="/WEB-INF/jsp/createcategory.jsp" contextRelative="true"
 */
public class FAQCategoryAction extends DispatchAction {
	private SessionManager sessionManager;
	private UserDirectoryService userDirectoryService;
	private UsageSessionService usageSessionService;
	private EventTrackingService eventTrackingService;
	private ToolManager  toolManager;

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



	public ActionForward editcategory(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		FaqDAO dao = new FaqDAO();

		FAQCategoryForm faqCategoryForm = (FAQCategoryForm) form;
		ActionMessages errors = faqCategoryForm.validate(mapping, request);

		if (errors.isEmpty()) {
			saveErrors(request, errors);
		//	System.out.print("The errors are "+errors.size()+" "+errors.toString());
			return saveeditcategory(mapping, form, request, response);			
		}

		faqCategoryForm.setCategory(
				dao.getFaqCategory(faqCategoryForm.getCategory().getCategoryId()));
		faqCategoryForm.getCategory().setModifiedOn(new Timestamp(new Date().getTime()));

		return mapping.findForward("editcategory");
	}


	public ActionForward inputcategory(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		FAQCategoryForm faqCategoryForm = (FAQCategoryForm) form;
		faqCategoryForm.setCategory(new FaqCategory());

		return mapping.findForward("inputcategory");
	}



	public ActionForward saveCategory(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
		toolManager=(ToolManager)ComponentManager.get(ToolManager.class);
		userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
		usageSessionService=(UsageSessionService)ComponentManager.get(UsageSessionService.class);
		Session currentSession = sessionManager.getCurrentSession();
		String userId = currentSession.getUserId();
		String siteId = "";
		User user = null;
		if (userId != null) {
			user = userDirectoryService.getUser(userId);
		}
		
		UsageSession usageSession = usageSessionService.getSession();
		//		go to default on cancel
		if (isCancelled(request)) {
			return (mapping.findForward("cancels"));
		}

		FaqDAO dao = new FaqDAO();
		FAQCategoryForm faqCategoryForm = (FAQCategoryForm) form;

		ActionMessages errors = faqCategoryForm.validate(mapping, request);
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return inputcategory(mapping, form, request, response);
		}

		if( (faqCategoryForm.getCategory().getDescription()==null) ||
				  (faqCategoryForm.getCategory().getDescription().length()<1 ))
			 {errors
				.add(
						ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
								"Please provide a discription of the category "));
		     addErrors(request, errors);
		return inputcategory(mapping, form, request, response);
			 }

        //change to pass the siteId here to fix the bug in site import
		toolManager=(ToolManager)ComponentManager.get(ToolManager.class);
		siteId = toolManager.getCurrentPlacement().getContext();

		dao.insertFaqCategory(faqCategoryForm.getCategory(),siteId);
		eventTrackingService =(EventTrackingService)ComponentManager.get(EventTrackingService.class);
		eventTrackingService.post(
				eventTrackingService.newEvent(EventTrackingTypes.EVENT_FAQS_CATEGORYADD,toolManager.getCurrentPlacement().getContext(), false),usageSession);
		return mapping.findForward("save");
	}

	public ActionForward saveeditcategory(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception {

		log = LogFactory.getLog(this.getClass());
		sessionManager = (SessionManager)ComponentManager.get(SessionManager.class);	
		Session currentSession = sessionManager.getCurrentSession();
		userDirectoryService = (UserDirectoryService)ComponentManager.get(UserDirectoryService.class);
		String userId = currentSession.getUserId();
		User user = null;
		if (userId != null) {
			user = userDirectoryService.getUser(userId);
		}
		usageSessionService=(UsageSessionService)ComponentManager.get(UsageSessionService.class);
		UsageSession usageSession = usageSessionService.getSession();
		if (isCancelled(request)) {
			return (mapping.findForward("canceleditcategory"));
		}

		FaqDAO dao = new FaqDAO();
		FAQCategoryForm faqCategoryForm = (FAQCategoryForm) form;

		log.debug("Category ID on form: " + faqCategoryForm.getCategory().getCategoryId());

		ActionMessages errors = faqCategoryForm.validate(mapping, request);
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return editcategory(mapping, form, request, response);
		}

		if( (faqCategoryForm.getCategory().getDescription()==null) ||
				  (faqCategoryForm.getCategory().getDescription().length()<1 ))
			 {errors
				.add(
						ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
								"Please provide a discription of the category "));
		     addErrors(request, errors);
		return inputcategory(mapping, form, request, response);
			 }

		dao.updateFaqCategory(faqCategoryForm.getCategory());
		eventTrackingService =(EventTrackingService)ComponentManager.get(EventTrackingService.class);
		toolManager=(ToolManager)ComponentManager.get(ToolManager.class);
		eventTrackingService.post(
				eventTrackingService.newEvent(EventTrackingTypes.EVENT_FAQS_CATEGORYDEDIT,toolManager.getCurrentPlacement().getContext(), false),usageSession);
		return mapping.findForward("saveeditcategory");

	}


}

