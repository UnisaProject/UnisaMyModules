package za.ac.unisa.lms.tools.changepassword.actions;

import javax.naming.OperationNotSupportedException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.event.api.EventTrackingService;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.tool.api.ToolManager;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserDirectoryProvider;
import org.sakaiproject.user.api.UserDirectoryService;
import org.sakaiproject.component.cover.ComponentManager;

import za.ac.unisa.lms.constants.EventTrackingTypes;
/*import za.ac.unisa.lms.providers.api.IdVaultProvisioner;
import za.ac.unisa.lms.providers.api.IdVaultUser;*/
import za.ac.unisa.lms.ad.SaveStudentToAD;
import za.ac.unisa.lms.ad.IdVaultUser;
import za.ac.unisa.lms.tools.changepassword.dao.ChangePasswordSakaiDAO;
import za.ac.unisa.lms.tools.changepassword.forms.ChangePasswordForm;
import za.ac.unisa.lms.tools.join.dao.MyUnisaJoinSakaiDAO;

public class ChangePasswordAction extends DispatchAction {

	private SessionManager sessionManager;
	private UserDirectoryService userDirectoryService;
	private EventTrackingService eventTrackingService;
	private ToolManager toolManager;
	
	public ActionForward input(
		ActionMapping mapping, 
		ActionForm form,
		HttpServletRequest request, 
		HttpServletResponse response
	) throws Exception {
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
		toolManager = (ToolManager) ComponentManager.get(ToolManager.class);
		
		ChangePasswordForm changePasswordForm = (ChangePasswordForm) form;
		changePasswordForm.setMylifePath(ServerConfigurationService.getString("mylife.path")); 
		
		eventTrackingService.post(
				eventTrackingService.newEvent(
				EventTrackingTypes.EVENT_CHANGEPASSWORD_VIEW, 
				toolManager.getCurrentPlacement().getContext(), 
				false
			)
		);
		return mapping.findForward("display");
	}

	public ActionForward validate(
		ActionMapping mapping, 
		ActionForm form,
		HttpServletRequest request, 
		HttpServletResponse response
	) throws Exception {
		ChangePasswordForm changePasswordForm = (ChangePasswordForm) form;
		ActionMessages message = new ActionMessages();
		sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
		userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
		toolManager = (ToolManager) ComponentManager.get(ToolManager.class);
		ChangePasswordSakaiDAO sakaiDb = new ChangePasswordSakaiDAO();
		
		Session currentSession = sessionManager.getCurrentSession();
		String userID = currentSession.getUserId();
		String eId=currentSession.getUserEid();
		message = (ActionMessages) changePasswordForm.validate(mapping, request);
		if (!message.isEmpty()) {
			addErrors(request, message);
			return mapping.findForward("display");
		} else {
			if (userID != null) {
				User user = userDirectoryService.getUser(userID);
				request.setAttribute("user", user);
				UserDirectoryProvider provider = (UserDirectoryProvider) ComponentManager.get("org.sakaiproject.user.api.UserDirectoryProvider");
				
				if (!provider.authenticateUser(userDirectoryService.getUserEid(userID), null, changePasswordForm.getOldpassword())) {
					message.add("InvalidOldPassword", new ActionMessage("errors.oldpassword"));
					changePasswordForm.setOldpassword(null);
					addErrors(request, message);
					return mapping.findForward("display");
				}

				if (!changePasswordForm.getNewpassword().equals(changePasswordForm.getConfirmpassword())) {
					message.add("NottheSame", new ActionMessage("errors.notthesame"));
				}
				if (changePasswordForm.getNewpassword().equals(changePasswordForm.getOldpassword())) {
					message.add("OldNewtheSame", new ActionMessage("errors.oldnewthesame"));
				}
				if (!message.isEmpty()) {
					addErrors(request, message);
					return mapping.findForward("display");
				} else {
					try {
						
						user = userDirectoryService.getUser(userID);
						IdVaultUser idVaultuser = new IdVaultUser();
						idVaultuser.setId(userDirectoryService.getUserEid(userID));
						idVaultuser.setEmail(user.getEmail());
						idVaultuser.setFirstName(user.getFirstName());
						idVaultuser.setLastName(user.getLastName());
						idVaultuser.setDn("CN="+userDirectoryService.getUserEid(userID)+","+ServerConfigurationService.getString("studentBasePath"));
						log.debug(this+"DN set as "+idVaultuser.getDn());
						idVaultuser.setType(user.getType());
						idVaultuser.setPassword(changePasswordForm.getNewpassword());
						SaveStudentToAD saveStudentToAD = new SaveStudentToAD();
						try {
							if(!saveStudentToAD.saveUser(idVaultuser)) {
								message.add("FailedPassword",new ActionMessage("errors.failledset"));
								addErrors(request, message);
								return mapping.findForward("display");
							}else{
								log.debug(this + "Student "+eId+" password changed successfully");	
							}
							/*if (!((IdVaultProvisioner)ComponentManager
									.get("org.sakaiproject.user.api.UserDirectoryProvider"))
									.saveUser(idVaultuser)) {
								message.add("FailedPassword",new ActionMessage("errors.failledset"));
								addErrors(request, message);
								return mapping.findForward("display");
							}*/
							
						} catch (OperationNotSupportedException e) {
							message.add("FailedPassword", new ActionMessage("errors.failledset"));
							addErrors(request, message);
							return mapping.findForward("display");
						} catch (Exception e) {
							log.error(this + ": Unhandled exception (" + e + "): " + e.getMessage());
							return mapping.findForward("display");
						}
						//update or insert to UNISA-PASSWORD
						sakaiDb.updateChangePasswordStatus(eId);
						
						message.add("Sucessfull", new ActionMessage("message.success"));
						addMessages(request, message);
						

						eventTrackingService.post(
							eventTrackingService.newEvent(
								EventTrackingTypes.EVENT_CHANGEPASSWORD_CHANGE,
								toolManager.getCurrentPlacement().getContext(), 
								false
							)
						);

						user = userDirectoryService.authenticate(currentSession.getUserEid(), changePasswordForm.getNewpassword());
						Session authSession = sessionManager.getCurrentSession();
						authSession.setUserId(user.getId());
						authSession.setUserEid(user.getEid());
						changePasswordForm.setConfirmpassword("");
						changePasswordForm.setNewpassword("");
						changePasswordForm.setOldpassword("");
						log.debug(this+"Successfully changes password for student "+user.getEid());
						return mapping.findForward("display");
					} catch (Exception e) {
						log.error(this+"Error occured on action=ChangePasswordAction; method=validate (IdUnusedException): set of password"
										+ e);
					}
				}
			}
			message.add("invalidUser", new ActionMessage("errors.user"));
			addErrors(request, message);
			return mapping.findForward("display");
		}
	}
}
