//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.faqs.actions;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.event.api.UsageSession;
import org.sakaiproject.event.api.EventTrackingService;
import org.sakaiproject.event.api.UsageSessionService;
import org.sakaiproject.exception.IdUnusedException;

import org.sakaiproject.site.api.Site;
import org.sakaiproject.site.api.SiteService;

import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.tool.api.ToolManager;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserNotDefinedException;
import org.sakaiproject.user.api.UserDirectoryService;


import za.ac.unisa.lms.constants.EventTrackingTypes;
import za.ac.unisa.lms.tools.faqs.dao.FaqCategory;
import za.ac.unisa.lms.tools.faqs.dao.FaqContent;
import za.ac.unisa.lms.tools.faqs.dao.FaqDAO;
import za.ac.unisa.lms.tools.faqs.forms.FAQListForm;
import za.ac.unisa.lms.tools.faqs.forms.FAQCategoryForm;
import za.ac.unisa.security.SecurityServices;

public class FAQListAction extends DispatchAction {
	private SessionManager sessionManager;
	private ToolManager toolManager;
	private UserDirectoryService userDirectoryService;
	private UsageSessionService usageSessionService;
	private Log log;


	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)  {

		SecurityServices ss = new SecurityServices();
		FaqDAO dao =new FaqDAO();
		//FAQCategoryForm faqcategoryform = (FAQCategoryForm)form;
		FAQListForm listForm= (FAQListForm)form;

		userDirectoryService=(UserDirectoryService)ComponentManager.get(UserDirectoryService.class);
		toolManager=(ToolManager)ComponentManager.get(ToolManager.class);
		sessionManager = (SessionManager)ComponentManager.get(SessionManager.class);
		usageSessionService=(UsageSessionService)ComponentManager.get(UsageSessionService.class);
		Session currentSession = sessionManager.getCurrentSession();
   		String userID = currentSession.getUserId();
		listForm.setAddFAQContent(ss.checkPermission(ss.getSiteReference(), "faqs.add"));
		listForm.setEditFAQContent(ss.checkPermission(ss.getSiteReference(), "faqs.contentedit"));
		listForm.setDeleteFAQContent(ss.checkPermission(ss.getSiteReference(), "faqs.contentdelete"));

		//faqcategoryform.getCategory()
		listForm.setAddFAQCategory(ss.checkPermission(ss.getSiteReference(), "faqs.createcategory"));
		listForm.setEditFAQCategory(ss.checkPermission(ss.getSiteReference(), "faqs.editcatergory"));
		listForm.setDeleteFAQCategory(ss.checkPermission(ss.getSiteReference(), "faqs.categorydelete"));

		User user = null;
		boolean userKnown = true;



		if (userID != null) {
			if (userID.equalsIgnoreCase("admin")) {
				request.getSession().setAttribute("usertype", "Instructor");
			} else {
				try {
					
					user = userDirectoryService.getUser(userID);
					request.getSession().setAttribute("usertype", user.getType());
				} catch (UserNotDefinedException idu) {
					request.getSession().setAttribute("usertype", "none");
				}
			}

		}else {
			//user unknown - gateway user
			userKnown = false;
		}

		log = LogFactory.getLog(this.getClass());
		//FAQListForm listForm = (FAQListForm) form;
		//ActionMessages msg = listForm.validate(mapping, request);

		if (listForm.getExpandedCategories() == null) {
			listForm.setExpandedCategories(new Vector());
		}
		String siteId = toolManager.getCurrentPlacement().getContext();
		String siteTitle="";
		SiteService siteservice=(SiteService)ComponentManager.get(SiteService.class);
		try{siteTitle = siteservice.getSite(siteId).getTitle().toString();}
		catch(IdUnusedException e){System.out.println(e.getLocalizedMessage());}

		boolean faqExist=listForm.isFaqExist();
		boolean faqbuttonExists=listForm.isFaqbuttonExists();//reffers to the remove button in the jsp
		listForm.setSiteId(siteTitle);
		List categories = dao.getFaqCategories(siteId);
		Iterator i = categories.iterator();
		int countExpandedCategories=0;
		while (i.hasNext()) {
			FaqCategory c = (FaqCategory) i.next();
			//FaqContent  cont = FaqContent
			Vector ids = listForm.getExpandedCategories();
			Iterator idsi = ids.iterator();


			while (idsi.hasNext()) {
				Integer id = (Integer) idsi.next();
				if (id.equals(c.getCategoryId())) {
					log.debug("Setting the expanded value for "+c.getCategoryId());
					c.setExpanded(true);
					//if (c.getContents().size()>0)
					//{faqExist=( true|faqExist);}

					List contents = dao.getFaqContents(c.getCategoryId());

					if (contents .size()>0)
					{faqExist=faqExist|((contents .size()>0)&&c.isExpanded()==true );
					 	if (c.isExpanded()){countExpandedCategories++;}

					}
					else
					{faqExist=faqExist&&(countExpandedCategories>1);}
					c.setContents(contents);
					continue;
				}
			}


			//System.out.println(" The value of the column is "+listForm.isFaqExist()+" and the value of content is "+listForm.isDeleteFAQContent());

		}

		listForm.setFaqExist(faqExist);
		listForm.setCategories(categories);
		if(userKnown) {
			UsageSession usageSession = usageSessionService.getSession();
			EventTrackingService eventTrackingService=(EventTrackingService)ComponentManager.get(EventTrackingService.class);
			eventTrackingService.post(
				eventTrackingService.newEvent(EventTrackingTypes.EVENT_FAQS_VIEW,toolManager.getCurrentPlacement().getContext(), false),usageSession);
		}
		return (mapping.findForward("view"));

	}

	public ActionForward expand(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		log = LogFactory.getLog(this.getClass());
		FAQListForm listForm = (FAQListForm) form;

		log.debug("Expanding category "+listForm.getExpandCategoryId());

		Vector ids = listForm.getExpandedCategories();
		Iterator i = ids.iterator();

		while (i.hasNext()) {
			Integer id = (Integer) i.next();
			if (id.equals(listForm.getExpandCategoryId())) {
				return view(mapping, form, request, response);
			}
		}

		ids.add(listForm.getExpandCategoryId());

		return view(mapping, form, request, response);
	}

	public ActionForward collapse(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		log = LogFactory.getLog(this.getClass());
		FAQListForm listForm = (FAQListForm) form;
		listForm.setFaqExist(false);

		log.debug("Collapsing category "+listForm.getExpandCategoryId());

		Vector ids = listForm.getExpandedCategories();
		Iterator i = ids.iterator();

		while (i.hasNext()) {
			Integer id = (Integer) i.next();
			if (id.equals(listForm.getExpandCategoryId())) {
				ids.remove(id);
				return view(mapping, form, request, response);
			}
		}

		return view(mapping, form, request, response);
	}

	public ActionForward removeSpecific(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		FAQListForm listForm = (FAQListForm) form;

		List categories = listForm.getCategories();

		Iterator i = categories.iterator();

		while (i.hasNext()) {
			FaqCategory c = (FaqCategory) i.next();
			if (c.getContents() != null) {
				Iterator qi = c.getContents().iterator();
				while (qi.hasNext()) {
					FaqContent q = (FaqContent) qi.next();
					if (q.getContentId().equals(listForm.getSpecificContentId())) {
						q.setRemove(true);
					}
				}
			}
		}

		return confirmRemove(mapping, form, request, response);

	}

	public ActionForward confirmRemove(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {


		if (isCancelled(request)) {
			return view(mapping, form, request, response);
		}

		log = LogFactory.getLog(this.getClass());
		FAQListForm listForm = (FAQListForm) form;
		int removeCount = 0;

		List categories = listForm.getCategories();

		Iterator i = categories.iterator();
		while (i.hasNext()) {
			FaqCategory c = (FaqCategory) i.next();
			if (c.isRemove()) {
				removeCount++;
				log.debug("Category "+c.getCategoryId()+" scheduled for removal.");
			} else {
				if (c.getContents() != null) {
					Iterator qi = c.getContents().iterator();
					while (qi.hasNext()) {
						FaqContent q = (FaqContent) qi.next();
						if (q.isRemove()) removeCount++;
					}

					//if (==removeCount)
				}
			}
		}

		if (removeCount == 0) {
			return view(mapping, form, request, response);
		}

		return mapping.findForward("confirmremove");
	}

	public ActionForward remove(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		EventTrackingService eventTrackingService=(EventTrackingService)ComponentManager.get(EventTrackingService.class);
		userDirectoryService=(UserDirectoryService)ComponentManager.get(UserDirectoryService.class);
		usageSessionService=(UsageSessionService)ComponentManager.get(UsageSessionService.class);
		toolManager=(ToolManager)ComponentManager.get(ToolManager.class);
		if (isCancelled(request)) return view(mapping, form, request, response);

		sessionManager = (SessionManager)ComponentManager.get(SessionManager.class);	
		Session currentSession = sessionManager.getCurrentSession();
		String userId = currentSession.getUserId();
		User user = null;
		if (userId != null) {
			user = userDirectoryService.getUser(userId);
		}
    	UsageSession usageSession = usageSessionService.getSession();
     	log = LogFactory.getLog(this.getClass());
		FAQListForm listForm = (FAQListForm) form;
		FaqDAO dao = new FaqDAO();
		List categories = listForm.getCategories();
		Iterator i = categories.iterator();
		while (i.hasNext()) {
			FaqCategory c = (FaqCategory) i.next();
			if (c.isRemove()) {
				dao.deleteFaqCategory(c.getCategoryId());
			} else {
				if (c.getContents() != null) {
					Iterator ci = c.getContents().iterator();
					while (ci.hasNext()) {
						FaqContent q = (FaqContent) ci.next();
						if (q.isRemove()) {
							dao.deleteFaqContent(q.getContentId());
						}
					}
				}
			}
		}
		
		eventTrackingService.post(
				eventTrackingService.newEvent(EventTrackingTypes.EVENT_FAQS_CATEGORYDELETE,toolManager.getCurrentPlacement().getContext(), false),usageSession);
		return view(mapping, form, request, response);
	}

}
//

