//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.faqs.actions;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserNotDefinedException;
import org.sakaiproject.user.api.UserDirectoryService;
import org.sakaiproject.component.cover.ComponentManager;

import za.ac.unisa.lms.tools.faqs.dao.FaqCategory;
import za.ac.unisa.lms.tools.faqs.dao.FaqContent;
import za.ac.unisa.lms.tools.faqs.dao.FaqDAO;
import za.ac.unisa.lms.tools.faqs.forms.FAQCopyForm;

/**
 * MyEclipse Struts
 * Creation date: 07-06-2006
 *
 * XDoclet definition:
 * @struts:action parameter="action" validate="true"
 */
public class FaqCopy extends Action {
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
		HttpServletResponse response) {
		sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
		userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);

		Session currentSession = sessionManager.getCurrentSession();
		String userID = currentSession.getUserId();

		if (userID.equalsIgnoreCase("admin")) {
			request.getSession().setAttribute("usertype", "Instructor");
		} else {
			try {
				User user = userDirectoryService.getUser(userID);
				request.getSession().setAttribute("usertype", user.getType());
			} catch (UserNotDefinedException idu) {
				request.getSession().setAttribute("usertype", "none");
			}
		}

		FAQCopyForm faqCopyForm = (FAQCopyForm) form;

		String fromSiteId = faqCopyForm.getCopyfrom();
		String toSiteId = faqCopyForm.getCopyto();
		
		FaqDAO faqDAO = new FaqDAO();
		faqDAO.copy(fromSiteId, toSiteId);

		return mapping.findForward("copiedforward");
	}




	}

