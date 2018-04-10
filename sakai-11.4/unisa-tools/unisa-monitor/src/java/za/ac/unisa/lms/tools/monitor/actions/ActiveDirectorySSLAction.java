//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.monitor.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.sakaiproject.component.api.ComponentManager;


/*import za.ac.unisa.lms.providers.api.AdProvisioner;*/
import za.ac.unisa.lms.tools.monitor.forms.ActiveDirectorySSLForm;
import za.ac.unisa.lms.ad.SaveStudentToAD;

/** 
 * MyEclipse Struts
 * Creation date: 07-10-2006
 * 
 * XDoclet definition:
 * @struts:action parameter="action" validate="true"
 */
public class ActiveDirectorySSLAction extends Action {

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
	private static Log log = LogFactory.getLog(ActiveDirectorySSLAction.class);
	public ActionForward execute(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {

		ActiveDirectorySSLForm adSSLForm = (ActiveDirectorySSLForm) form;
		SaveStudentToAD saveStudentToAD = new SaveStudentToAD();

		/*// TODO Auto-generated method stub
		ComponentManager sakaiCompMgr = (ComponentManager) org.sakaiproject.component.cover.ComponentManager.getInstance();
		AdProvisioner adProvisioner;
		Object adpObject =  sakaiCompMgr.get("org.sakaiproject.user.api.UserDirectoryProvider");
		if(adpObject instanceof AdProvisioner) {
			adProvisioner = (AdProvisioner) adpObject;
		} else {
			log.error(this+": Cannot cast "+adpObject.getClass()+" (org.sakaiproject.user.api.UserDirectoryProvider) as ADProvisioner");
			adSSLForm.setSSLOkay(false);
			return mapping.findForward("activedirectorysslforward");
		}*/
		
		try {
			log.debug(this+": about to test ssl by getting admin context...");
			
			boolean sslOkay = saveStudentToAD.checkAdmCtx(); 
			if(sslOkay) {
				adSSLForm.setSSLOkay(true);
				log.debug(this+": sslOkay...");
			} else {
				adSSLForm.setSSLOkay(false);
				log.debug(this+": ssl NOT Okay...");
			}
			log.debug(this+": return mapping activedirectorysslforward...");
			return mapping.findForward("activedirectorysslforward");
		} catch(Exception e) {
			log.debug(this+": ssl NOT Okay... caught Exception: "+e.getMessage());
			adSSLForm.setSSLOkay(false);
			return mapping.findForward("activedirectorysslforward");
		}finally{
			saveStudentToAD=null;
		}
	}

}

