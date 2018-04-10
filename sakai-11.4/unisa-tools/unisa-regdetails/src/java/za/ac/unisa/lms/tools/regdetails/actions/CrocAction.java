//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.regdetails.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.LookupDispatchAction;
import org.sakaiproject.email.api.EmailService;
import org.sakaiproject.event.api.UsageSession;
import org.sakaiproject.event.api.EventTrackingService;
import org.sakaiproject.event.api.UsageSessionService;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.tool.api.ToolManager;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserDirectoryService;
import org.sakaiproject.component.cover.ComponentManager;

import Srcds01h.Abean.Srcds01sMntStudContactDetail;
import Srclj01h.Abean.Srclj01sPrtCrokLetter;
import za.ac.unisa.lms.constants.EventTrackingTypes;
import za.ac.unisa.lms.tools.regdetails.dao.RegQueryDAO;
import za.ac.unisa.lms.tools.regdetails.forms.RegDetailsForm;

@SuppressWarnings("unused")
public class CrocAction extends LookupDispatchAction {

	// --------------------------------------------------------- Instance
	// Variables
	private SessionManager sessionManager;
	private UserDirectoryService userDirectoryService;
	private EmailService emailService;
	private EventTrackingService eventTrackingService;
	private ToolManager toolManager;
	private UsageSessionService usageSessionService;

	// --------------------------------------------------------- Methods

	private class operListener implements java.awt.event.ActionListener {
		private Exception exception = null;

		operListener() {
			exception = null;
		}

		public Exception getException() {
			return exception;
		}

		public void actionPerformed(java.awt.event.ActionEvent aEvent) {
			exception = new Exception(aEvent.getActionCommand());
		}
	}
	protected Map<String, String> getKeyMethodMap() {

		Map<String, String> map = new HashMap<String, String>();
		map.put("button.request", "nextStep");
		map.put("button.continue", "nextStep");
		map.put("button.back", "back");
		map.put("button.cancel", "cancel");
		map.put("button.save", "save");
		map.put("step1", "step1");
		map.put("step2", "step2 ");
		map.put("nextStep", "nextStep");
		return map;
	}

	public ActionForward step1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//log.debug("Action - step1 - ================================================================");
		//log.debug("Action - step1 - Start");
		
		RegDetailsForm regDetailsForm = (RegDetailsForm) form;
		ActionMessages messages = new ActionMessages();
		
		try {
			eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
			toolManager = (ToolManager) ComponentManager.get(ToolManager.class);
			usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
			UsageSession usageSession = usageSessionService.getSession();
		
			/* Get user details, set in default action */
			String userId = "";
			String userEid = "";
			sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
			userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
			User user = (User) request.getAttribute("user");
			if (user == null) {
				Session currentSession = sessionManager.getCurrentSession();
				userId = currentSession.getUserId();
				if (userId != null) {
					user = userDirectoryService.getUser(userId);
					userEid = user.getEid();
					request.setAttribute("user", user);
					regDetailsForm.setStudentNr(userEid);
				}
			}

			if (user.getType() == null || !"student".equalsIgnoreCase(user.getType())){
				return mapping.findForward("invaliduser");
			}
			
			/* do tests for valid period on REGDAT */
			RegQueryDAO db = new RegQueryDAO();
			if (db.isRegPeriodValid("CROCLetter", "0")) {
				regDetailsForm.setCrocDate(true);
			} else {
				regDetailsForm.setCrocDate(false);
			}
			
			//log.debug("Action - step1 - RegDate Check="+regDetailsForm.isCrocDate());
			
			if (!regDetailsForm.isCrocDate()) {
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"message.generalmessage",
						"The application period for Registration Letters is closed"));
				addErrors(request, messages);
				return mapping.findForward("cancel");
			}
			
		} catch (Exception e) {
			throw e;
		}

		//log.debug("CrocAction - step1 - End");
	    //log.debug("Action - step1 - ================================================================");

		return mapping.findForward("stepCROC1");
	}

	public String step2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

	    //log.debug("Action - step2  - ================================================================");

		//log.debug("Action - step2 - Start");
		
		RegDetailsForm regDetailsForm = (RegDetailsForm) form;
		ActionMessages messages = new ActionMessages();

		try {
			/* Get Student Email Address */
			Srcds01sMntStudContactDetail op = new Srcds01sMntStudContactDetail();
	        operListener opl = new operListener();
	        op.addExceptionListener(opl);
	        op.clear();

	        op.setInSecurityWsUserNumber(9999);
		    op.setInCsfClientServerCommunicationsClientVersionNumber((short) 3);
		    op.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
		    op.setInCsfClientServerCommunicationsAction("D");
		    op.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
		    op.setInWsStudentNr(Integer.parseInt(regDetailsForm.getStudentNr()));
		    
		    //log.debug("Action - step2 - (Srcds01sMntStudContactDetail) - Execute - Getting Email Address");
		    op.execute();
		    
		    if (opl.getException() != null) throw opl.getException();
		    if (op.getExitStateType() < 3) throw new Exception(op.getExitStateMsg());

		    String Errormsg = op.getOutCsfStringsString500();
		    if ((Errormsg != null) && (!Errormsg.equals(""))) {
		       	messages.add(ActionMessages.GLOBAL_MESSAGE,
		      			new ActionMessage("error.coolgenerror", Errormsg));
		       	addErrors(request, messages);
		       	return "cancel";
		    }

		    // Email address
		    regDetailsForm.setCrocMyLife(op.getOutContactsWsAddressV2EmailAddress().trim());
		    //log.debug("Action - step2 - (Srcds01sMntStudContactDetail) - CROC Email Address="+regDetailsForm.getCrocMyLife());
		    
		    if (regDetailsForm.getCrocMyLife().toUpperCase().contains("MYLIFE")){
		       	regDetailsForm.setCrocLetterRequest(true);
		    }else{
		       	regDetailsForm.setCrocLetterRequest(false);
		       /*	messages.add(ActionMessages.GLOBAL_MESSAGE,
		      			new ActionMessage("message.generalmessage", "myLife Email address not found. Please go to my.unisa.ac.za to claim your myLife email account"));
		       	addErrors(request, messages);*/
		       	//log.debug("Action - step2 - Return stepCROC1");
		       	return "stepCROC2";
		    }
		    
		    //log.debug("Action - step2 - isCrocLetterRequest="+regDetailsForm.isCrocLetterRequest());
		    if (regDetailsForm.isCrocLetterRequest()){
		    	/* Request Croc letter */
		    	Srclj01sPrtCrokLetter op2 = new Srclj01sPrtCrokLetter();
		        operListener opl2 = new operListener();
		        op2.addExceptionListener(opl2);
		        op2.clear();

			    op2.setInCsfClientServerCommunicationsAction("A");
			    op2.setInStudentAnnualRecordMkStudentNr(Integer.parseInt(regDetailsForm.getStudentNr()));
			    op2.setInStudentAnnualRecordMkAcademicYear((short) Integer.parseInt(regDetailsForm.getAcadYear()));
			    op2.setInStudentAnnualRecordMkAcademicPeriod((short) Integer.parseInt(regDetailsForm.getAcadPeriod()));
			    op2.setInWsUserNumber(99998);

			    op2.setInEmailToCsfStringsString132(regDetailsForm.getCrocMyLife());
			    op2.setInEmailFromCsfStringsString132("no-reply@unisa.ac.za");
			    op2.setInFaxOrEmailCsfStringsString1("E");
			    op2.setInNoOfCopiesCsfStringsString1("N");
			    
			    //log.debug("Action - step2 - (Srclj01sPrtCrokLetter) - Execute - ======================================================");
			    //log.debug("Action - step2 - (Srclj01sPrtCrokLetter) - Execute - Request CROC Letter");
			    //log.debug("Action - step2 - (Srclj01sPrtCrokLetter) - Execute - Student Number="+regDetailsForm.getStudentNr());
			    //log.debug("Action - step2 - (Srclj01sPrtCrokLetter) - Execute - Academic Year="+regDetailsForm.getAcadYear());
			    //log.debug("Action - step2 - (Srclj01sPrtCrokLetter) - Execute - Academic Period="+regDetailsForm.getAcadPeriod());
			    //log.debug("Action - step2 - (Srclj01sPrtCrokLetter) - Execute - myLife EmailAddress="+regDetailsForm.getCrocMyLife());
			    //log.debug("Action - step2 - (Srclj01sPrtCrokLetter) - Execute - InFaxOrEmail=E");
			    //log.debug("Action - step2 - (Srclj01sPrtCrokLetter) - Execute - InNoOfCopies=N");
			    //log.debug("Action - step2 - (Srclj01sPrtCrokLetter) - Execute - ======================================================");
			    
			    
			    
			    op2.execute();
			    
			    if (opl2.getException() != null) throw opl2.getException();
			    if (op2.getExitStateType() < 3) throw new Exception(op2.getExitStateMsg());

			    String Errormsg2 = op2.getOutErrmsgCsfStringsString500();
			    //log.debug("Action - step2 - (Srclj01sPrtCrokLetter) - Execute - Result="+op2.getOutErrmsgCsfStringsString500());

			    if (Errormsg2 != null && !Errormsg2.equals("") && !Errormsg2.toUpperCase().contains("AUDIT TRAIL OK")) {
			       	messages.add(ActionMessages.GLOBAL_MESSAGE,
			      			new ActionMessage("error.coolgenerror", Errormsg2));
			       	addErrors(request, messages);
			       	return "stepCROC1";
			    }
		    }

		} catch (Exception e) {
			throw new Exception("An Error occurred while processing Registration Letter request / " +e);
		}

		//log.debug("Action - step2 - End - GoTo stepCROC2");
	    //log.debug("Action - step2 - ================================================================");

		return "stepCROC2";
	}

	public ActionForward nextStep(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String nextPage = "";
		if ("CrocStep1".equalsIgnoreCase(request.getParameter("page"))) {
			nextPage = step2(mapping, form, request, response);;
		}

		return mapping.findForward(nextPage);
	}

	public ActionForward cancel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		RegDetailsForm regDetailsForm = (RegDetailsForm) form;
		reset(regDetailsForm);
		return mapping.findForward("cancel");
	}

	private void reset(RegDetailsForm regDetailsForm) {
		regDetailsForm.setCrocMyLife("");
		regDetailsForm.setCrocLetterRequest(false);
	}

}