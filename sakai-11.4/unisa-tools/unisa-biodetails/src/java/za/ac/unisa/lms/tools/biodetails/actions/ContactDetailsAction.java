//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.biodetails.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
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
import org.sakaiproject.tool.api.ToolManager;
import org.sakaiproject.component.cover.ComponentManager;

import za.ac.unisa.lms.constants.EventTrackingTypes;
import za.ac.unisa.lms.tools.biodetails.forms.BioDetailsForm;
import za.ac.unisa.lms.tools.biodetails.forms.EmailUpdaterClass;
import za.ac.unisa.utils.EmailAddressVerification;
import za.ac.unisa.utils.WorkflowFile;
import Srcds01h.Abean.Srcds01sMntStudContactDetail;

/**
 * MyEclipse Struts
 * Creation date: 09-14-2005
 *
 * XDoclet definition:
 * @struts:action path="/ContactDetailsAction" name="bioDetailsForm" parameter="action" validate="true"
 */
public class ContactDetailsAction extends DispatchAction {

	public static Log log = LogFactory.getLog(EmailAddressVerification.class);

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
	// --------------------------------------------------------- Instance Variables
	private EventTrackingService eventTrackingService;
	private ToolManager toolManager;
	private UsageSessionService usageSessionService;

	// --------------------------------------------------------- Methods

	/**
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward display(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {

		if (isCancelled(request)) {
			return (mapping.findForward("cancel"));
		}

		return mapping.findForward("display");
	}

	private boolean isValidNumber(String number){
		boolean result = false;
		String test = number;

		if ( number != null && !"".equals(number)){
			test = test.replaceAll("-","");
			test = test.replaceAll("\\+","");
			try{
				Long.parseLong(test);
				result = true;
			} catch (NumberFormatException e) {
				result = false;
			}
		}else{
			result = true;
		}
		return result;
	}

	public ActionForward submit(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)throws Exception{

			//go to first screen on cancel
			if (isCancelled(request)) {
				return (mapping.findForward("cancel"));
			}

			ActionMessages messages = new ActionMessages();
			BioDetailsForm bioDetailsForm = (BioDetailsForm) form;
			usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
			UsageSession usageSession = usageSessionService.getSession();
			eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
			toolManager = (ToolManager) ComponentManager.get(ToolManager.class);

			//Validate input
			if (bioDetailsForm.getEmail()!= null){
				bioDetailsForm.setEmail(bioDetailsForm.getEmail().replaceAll(" ",""));
			}
			if (bioDetailsForm.getCellNr()!= null){
				bioDetailsForm.setCellNr(bioDetailsForm.getCellNr().replaceAll(" ",""));
			}
			messages = (ActionMessages) bioDetailsForm.validate(mapping, request);
			if (!messages.isEmpty()) {
	       addErrors(request, messages);
	       return mapping.findForward("display");
	    }
			if(bioDetailsForm.getHomeNr()== null || "".equals(bioDetailsForm.getHomeNr())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
	        			new ActionMessage("message.generalmessage", "Your home phone number is required."));
				addErrors(request, messages);
				return (mapping.findForward("display"));
			}
			bioDetailsForm.setHomeNr(bioDetailsForm.getHomeNr().replaceAll(" ",""));
			if(!isValidNumber(bioDetailsForm.getHomeNr())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
	        			new ActionMessage("message.generalmessage", "Your home phone number may consist of a dash or +, the rest must be numeric."));
				addErrors(request, messages);
				return (mapping.findForward("display"));
			}
			if (bioDetailsForm.getWorkNr()!= null){
				bioDetailsForm.setWorkNr(bioDetailsForm.getWorkNr().replaceAll(" ",""));
			}
			if(!isValidNumber(bioDetailsForm.getWorkNr())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
	        			new ActionMessage("message.generalmessage", "Your work phone number may consist of a dash or +, the rest must be numeric."));
				addErrors(request, messages);
				return (mapping.findForward("display"));
			}
			if (bioDetailsForm.getFaxNr()!= null){
				bioDetailsForm.setFaxNr(bioDetailsForm.getFaxNr().replaceAll(" ",""));
			}
			if(!isValidNumber(bioDetailsForm.getFaxNr())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
      			new ActionMessage("message.generalmessage", "Your fax number may consist of a dash or +, the rest must be numeric."));
				addErrors(request, messages);
				return (mapping.findForward("display"));
			}
//		Check that the student did in fact make changes to their contact details
			if(! wereChangesMade(bioDetailsForm)){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
	        			new ActionMessage("message.generalmessage", "You did not change your contact details; we consider it to be correct."));
				addMessages(request, messages);
				return (mapping.findForward("cancel"));
			}

			if (messages.isEmpty()) {
				EmailUpdaterClass emailUpdater=new EmailUpdaterClass();
				Srcds01sMntStudContactDetail op = new Srcds01sMntStudContactDetail();
		        operListener opl = new operListener();
		        op.addExceptionListener(opl);
		        op.clear();

		        /* op.setTracing(Trace.MASK_ALL); */
		       // op.setInIpAddressCsfStringsString15(request.getRemoteAddr());
		        op.setInSecurityWsUserNumber(9999);
		        op.setInCsfClientServerCommunicationsClientVersionNumber((short) 3);
		        op.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
		        op.setInCsfClientServerCommunicationsAction("U");
		        op.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
		        op.setInWsStudentNr(Integer.parseInt(bioDetailsForm.getNumber()));
		        op.setInWsAddressV2HomeNumber(bioDetailsForm.getHomeNr());
		        op.setInWsAddressV2WorkNumber(bioDetailsForm.getWorkNr());
		        op.setInWsAddressV2FaxNumber(bioDetailsForm.getFaxNr());
		        op.setInWsAddressV2CellNumber(bioDetailsForm.getCellNr());
		        emailUpdater.setEmailAddressForContactDetailsActionProxy(bioDetailsForm,op,bioDetailsForm.getEmail());
		        op.setInWsAddressV2CourierContactNo(bioDetailsForm.getContactNr());
		        op.execute();
		        emailUpdater.updateEmailAddress(bioDetailsForm);

		        if (opl.getException() != null) throw opl.getException();
		        if (op.getExitStateType() < 3) throw new Exception(op.getExitStateMsg());

		        String Errormsg = op.getOutCsfStringsString500();
		        if ((Errormsg != null) && (!Errormsg.equals(""))) {
		        	messages.add(ActionMessages.GLOBAL_MESSAGE,
		        			new ActionMessage("error.coolgenerror", Errormsg));
		        	addErrors(request, messages);
		        	return mapping.findForward("display");
		        }

		    		eventTrackingService.post(
		    				eventTrackingService.newEvent(EventTrackingTypes.EVENT_BIODETAILS_CONTACTDETAILSCHANGE, toolManager.getCurrentPlacement().getContext()+"/"+bioDetailsForm.getNumber(), false),usageSession);

		        /* No errors occured, continue ... */

		        /* Write workflow */
		        writeWorkflow(bioDetailsForm);

		        /* Set old contact details to new contact details */
		        bioDetailsForm.setOldHomeNr(op.getOutContactsWsAddressV2HomeNumber());
		        bioDetailsForm.setOldWorkNr(op.getOutContactsWsAddressV2WorkNumber());
		        bioDetailsForm.setOldFaxNr(op.getOutContactsWsAddressV2FaxNumber());
		        bioDetailsForm.setOldCellNr(op.getOutContactsWsAddressV2CellNumber());
		        emailUpdater.updateOldEmailAddressOnForm(bioDetailsForm,op);
		        emailUpdater.resetNonMylifeEmailAddress(bioDetailsForm);
		        
		        messages.add(ActionMessages.GLOBAL_MESSAGE,
	        			new ActionMessage("message.generalmessage", "Your contact details were updated successfully."));
		        addMessages(request, messages);
				return (mapping.findForward("home"));
			}

			return mapping.findForward("display");
		}

	private boolean wereChangesMade(BioDetailsForm bioDetailsForm){

		boolean change = false;

		if (! bioDetailsForm.getOldHomeNr().equalsIgnoreCase(bioDetailsForm.getHomeNr())){
			return true;
		}else if (! bioDetailsForm.getOldWorkNr().equalsIgnoreCase(bioDetailsForm.getWorkNr())){
			return true;
		}else if (! bioDetailsForm.getOldFaxNr().equalsIgnoreCase(bioDetailsForm.getFaxNr())){
			return true;
		}else if (! bioDetailsForm.getOldCellNr().equalsIgnoreCase(bioDetailsForm.getCellNr())){
			return true;
		}else if (! bioDetailsForm.getOldEmail().equalsIgnoreCase(bioDetailsForm.getEmail())){
			return true;
		}

		return change;

	}

	private void writeWorkflow(BioDetailsForm bioDetailsForm) throws Exception{

		String date = (new java.text.SimpleDateFormat("EEEEE dd MMMMM yyyy hh:mm:ss").format(new java.util.Date()).toString());
		String time = (new java.text.SimpleDateFormat("hhmmss").format(new java.util.Date()).toString());
		WorkflowFile file = new WorkflowFile("email_"+ bioDetailsForm.getNumber()+"_"+ time);
		file.add("Subject: Contact details \r\n");
		file.add("Date received: " + date + "\r\n");
		file.add("The following changes were done through the Unisa Web Server.\r\n");
		file.add("Details:\r\n");
		file.add(" 1. Student Number      = " + bioDetailsForm.getNumber() + "\r\n");
		file.add(" 2. Name and Initials   = " + bioDetailsForm.getSurname()+" " +bioDetailsForm.getInitials() + "\r\n");
		if (! bioDetailsForm.getOldHomeNr().equalsIgnoreCase(bioDetailsForm.getHomeNr())){
			file.add(" 3. Home number - new   = " + bioDetailsForm.getHomeNr() + "\r\n");
			file.add("                - old   = " + bioDetailsForm.getOldHomeNr() + "\r\n");
		}else{
			file.add(" 3. Home number         = " + bioDetailsForm.getHomeNr() + "\r\n");
		}
		if (! bioDetailsForm.getOldWorkNr().equalsIgnoreCase(bioDetailsForm.getWorkNr())){
			file.add(" 4. Work number - new   = " + bioDetailsForm.getWorkNr() + "\r\n");
			file.add("                - old   = " + bioDetailsForm.getOldWorkNr() + "\r\n");
		}else{
			file.add(" 4. Work number         = " + bioDetailsForm.getWorkNr() + "\r\n");
		}
		if (! bioDetailsForm.getOldFaxNr().equalsIgnoreCase(bioDetailsForm.getFaxNr())){
			file.add(" 5. Fax number - new    = " + bioDetailsForm.getFaxNr() + "\r\n");
			file.add("               - old    = " + bioDetailsForm.getOldFaxNr() + "\r\n");
		}else{
			file.add(" 5. Fax number          = " + bioDetailsForm.getFaxNr() + "\r\n");
		}
		if (! bioDetailsForm.getOldCellNr().equalsIgnoreCase(bioDetailsForm.getCellNr())){
			file.add(" 6. Cell number - new   = " + bioDetailsForm.getCellNr() + "\r\n");
			file.add("                - old   = " + bioDetailsForm.getOldCellNr() + "\r\n");
		}else{
			file.add(" 6. Cell number         = " + bioDetailsForm.getCellNr() + "\r\n");
		}
		if (! bioDetailsForm.getOldEmail().equalsIgnoreCase(bioDetailsForm.getEmail())){
			file.add(" 7. Email address - new = " + bioDetailsForm.getEmail() + "\r\n");
			file.add("                  - old = " + bioDetailsForm.getOldEmail() + "\r\n");
		}else{
			file.add(" 7. Email address       = " + bioDetailsForm.getEmail() + "\r\n");
		}
		file.close();

	}

	public ActionForward unspecified(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response){

		log.info("ContactDetailsAction: unspecified method call -no value for parameter in request");

		return mapping.findForward("home");

	}
}

