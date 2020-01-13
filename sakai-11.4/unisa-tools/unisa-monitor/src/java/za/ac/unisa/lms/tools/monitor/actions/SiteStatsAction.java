
package za.ac.unisa.lms.tools.monitor.actions;

import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.sakaiproject.component.cover.ServerConfigurationService;

import za.ac.unisa.lms.tools.monitor.dao.SiteStatsDAO;


/** 
 * MyEclipse Struts
 * Creation date: 12-12-2011
 * 
 * XDoclet definition:
 * @param <ActionForward>
 * @param <ActionForward>
 * @param <ActionMapping>
 * @param <ActionForm>
 * @param <HttpServletRequest>
 * @param <HttpServletResponse>
 * @param <SiteStatsDAO>
 * @struts:action parameter="action" validate="true"
 */
public class SiteStatsAction extends DispatchAction {

	// --------------------------------------------------------- Instance Variables

	// --------------------------------------------------------- Methods

	/** 
	 * Method execute
	 * @param <ActionMapping>
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
		
		SiteStatsDAO dao = new SiteStatsDAO();
		String dbUrl  = ServerConfigurationService.getString("url@org.sakaiproject.sitestats.externalDbDataSource");
		String username   = ServerConfigurationService.getString("username@org.sakaiproject.sitestats.externalDbDataSource");
		String passwd  = ServerConfigurationService.getString("password@org.sakaiproject.sitestats.externalDbDataSource");
		
		if (dao.dbConnect(dbUrl,username,passwd) == null)
		{
			return mapping.findForward("dbfailureforward");
		} else {
			return mapping.findForward("dbsuccessforward");
		} 
	}
}


