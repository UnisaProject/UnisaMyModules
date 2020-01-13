//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.biodetails.actions;

//import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserDirectoryService;

//import za.ac.unisa.lms.providers.api.IdVaultProvisioner;
//import za.ac.unisa.lms.providers.api.IdVaultUser;
import za.ac.unisa.lms.ad.SaveStudentToAD;
import za.ac.unisa.lms.ad.IdVaultUser;
import za.ac.unisa.lms.tools.biodetails.dao.ContactDetailsDAO;


/**
 * MyEclipse Struts
 * Creation date: 12-30-2005
 *
 * XDoclet definition:
 * @struts:action validate="true"
 */
public class VerifyEmailAddressAction extends DispatchAction {

	// --------------------------------------------------------- Instance Variables
	private UserDirectoryService userDirectoryService;

	public ActionForward verify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String studentNr = "";
		String activationCode = "";
		userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);

		if (request.getParameter("studno")==null || "".equalsIgnoreCase(request.getParameter("studno"))){
			/* student number missing : verification unsuccessful */
			request.setAttribute("verified","0");
			return mapping.findForward("verified");
		}else{
			studentNr = request.getParameter("studno").toString();
		}
		if (request.getParameter("code")==null || "".equalsIgnoreCase(request.getParameter("code"))){
			/* activation number missing : verification unsuccessful */
			request.setAttribute("verified","0");
			return mapping.findForward("verified");
		}else{
			activationCode = request.getParameter("code").toString();
		}

		ContactDetailsDAO dao = new ContactDetailsDAO();
		if(activationCode.equalsIgnoreCase(dao.getActivationCode(studentNr))){
			/* email has been verified by student*/

	  	/* read student email address from database */
	  	String email = dao.getStudentEmail(studentNr);

	  	/* write new email address to IDVAULT */

	  		/* get the user and write changed email address to IDVAULT*/
      		User user = null;
  			IdVaultUser idVaultUser = new IdVaultUser();
  			user = userDirectoryService.getUserByEid(studentNr);
  			// set all variables in class idVaultUser.
  			idVaultUser.setId(user.getEid());
  			idVaultUser.setEmail(email);
  			idVaultUser.setFirstName(user.getFirstName());
  			idVaultUser.setLastName(user.getLastName());
  			idVaultUser.setDisplayName(user.getFirstName()+" "+user.getLastName());
  			idVaultUser.setType(user.getType());
  			SaveStudentToAD saveStudentToAD = new SaveStudentToAD();
  			
  			// attempt to save user
  			//if(!((IdVaultProvisioner) ComponentManager.get("org.sakaiproject.user.api.UserDirectoryProvider")).saveUser(idVaultUser)) {
  			if(!saveStudentToAD.saveUser(idVaultUser)) {
  				request.setAttribute("verified","0");
  				return mapping.findForward("verified");
  			}


  			/* Set verified flag = Y */
  			dao.writeEmailVerifiedFlag(studentNr,"Y");
			request.setAttribute("verified","1");

		}else{
			/* email has NOT been verified */
			request.setAttribute("verified","0");
		}

		return mapping.findForward("verified");
	}

}

