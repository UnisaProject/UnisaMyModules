//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.regdetails.actions;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.mail.internet.InternetAddress;
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
import org.apache.struts.util.LabelValueBean;
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.email.api.EmailService;
import org.sakaiproject.event.api.UsageSession;
import org.sakaiproject.event.api.EventTrackingService;
import org.sakaiproject.event.api.UsageSessionService;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.tool.api.ToolManager;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserDirectoryService;

import Srcds01h.Abean.Srcds01sMntStudContactDetail;
import Sruaf01h.Abean.Sruaf01sStudyUnitAddition;
import za.ac.unisa.lms.constants.EventTrackingTypes;
import za.ac.unisa.lms.studyquotation.StudyQuotation;
import za.ac.unisa.lms.studyquotation.StudyQuotationService;
import za.ac.unisa.lms.tools.regdetails.dao.AdditionQueryDAO;
import za.ac.unisa.lms.tools.regdetails.dao.RegQueryDAO;
import za.ac.unisa.lms.tools.regdetails.forms.Address;
import za.ac.unisa.lms.tools.regdetails.forms.Qualification;
import za.ac.unisa.lms.tools.regdetails.forms.RegDetailsForm;
import za.ac.unisa.lms.tools.regdetails.forms.StudyUnit;
import za.ac.unisa.utils.WorkflowFile;

import org.sakaiproject.component.cover.ComponentManager;

/**
 * MyEclipse Struts
 * Creation date: 10-14-2005
 *
 * XDoclet definition:
 * @struts:action path="/additions" name="regDetailsForm" parameter="action"
 */
@SuppressWarnings({"rawtypes", "unused", "unchecked"})
public class AdditionsAction extends LookupDispatchAction {


	public static Log log = LogFactory.getLog(AdditionsAction.class);
		// --------------------------------------------------------- Instance Variables
	private SessionManager sessionManager;
	private UserDirectoryService userDirectoryService;
	private EmailService emailService;
	private EventTrackingService eventTrackingService;
	private ToolManager toolManager;
	private UsageSessionService usageSessionService;
	
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

	// --------------------------------------------------------- Methods
	protected Map<String, String> getKeyMethodMap() {
	      Map<String, String> map = new HashMap<String, String>();
	      map.put("button.back", "back");
	      map.put("button.cancel", "cancel");
	      map.put("button.continue", "nextStep");
	      map.put("button.save", "submitAdditions");
	      map.put("button.creditcard", "creditCardPayment");
	      map.put("button.otherpayment", "otherPayment");
	      map.put("button.proceed", "step1");
	      map.put("step1", "step1");
	      map.put("step2", "step2");
	      map.put("step2c", "step2c");
	      map.put("speciality", "speciality");
	      map.put("step3", "step3");
	      map.put("step4", "step4");
	      map.put("step4a", "step4a");
	      map.put("step5", "step5");
	      map.put("nextStep", "nextStep");
	      map.put("button.regdetails", "cancel");
	      map.put("button.studentaffairs", "save");
	      map.put("displayContactDetails", "displayContactDetails");
	      map.put("button.updatedetails", "updateAddressDetails");

	      return map;
	  }
	
	/**
	 * This method processes the details to be displayed on the Address/Contact
	 * details page
	 * @return
	 * @throws Exception
	 */
	public ActionForward displayContactDetails(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) throws Exception {

		ActionMessages messages = new ActionMessages();		
		RegDetailsForm regDetailsForm = (RegDetailsForm) form;
		AdditionQueryDAO dao = new AdditionQueryDAO();
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
		if (messages.isEmpty()) {

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
	        op.execute();
	        if (opl.getException() != null) throw opl.getException();
	        if (op.getExitStateType() < 3) throw new Exception(op.getExitStateMsg());

	        String Errormsg = op.getOutCsfStringsString500();
	        if ((Errormsg != null) && (!Errormsg.equals(""))) {
	        	messages.add(ActionMessages.GLOBAL_MESSAGE,
	        			new ActionMessage("error.coolgenerror", Errormsg));
	        }

	        // Postal address
	        Vector postalRecords = getPostalAddress(regDetailsForm, op);
	        // Physical address
	        Vector physicalRecords = getPhysicalAddress(regDetailsForm, op);
	        // Courier address
	        Vector courierRecords = getCourierAddress(regDetailsForm, op);
	        //Contact numbers
	        regDetailsForm.setHomeNumber(op.getOutContactsWsAddressV2HomeNumber().trim());
	        regDetailsForm.setWorkNumber(op.getOutContactsWsAddressV2WorkNumber().trim());
	        regDetailsForm.setFaxNumber(op.getOutContactsWsAddressV2FaxNumber().trim());
	        regDetailsForm.setCellNumber(op.getOutContactsWsAddressV2CellNumber().trim());
	        regDetailsForm.setEmail(op.getOutContactsWsAddressV2EmailAddress().trim());
	        regDetailsForm.setContactNumber(op.getOutContactsWsAddressV2CourierContactNo().trim());

	    }
        if (!messages.isEmpty()) {
        	addErrors(request, messages);
        }

    	eventTrackingService.post(
    				eventTrackingService.newEvent(
    						EventTrackingTypes.EVENT_REGDETAILS_ADDITION, 
    						toolManager.getCurrentPlacement().getContext()+"/"+
    						regDetailsForm.getStudentNr(), false),
    				usageSession);

    	/** 2015 Edmund - Check if Student Addresses line 1 exists before student can continue to additions **/
    	regDetailsForm.setCheckPostal(dao.hasValidAddress(regDetailsForm.getStudentNr(), "Postal"));
    	//log.debug("AdditionsAction - displayContactDetails - Student has Postal Address: " + regDetailsForm.isCheckPostal());
    	
    	if (!regDetailsForm.isCheckPostal()){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "We do not have a postal address for you. Please go to Biographical Details or click on &lsquo;Change Address/Contact Details&rsquo; to update your postal address before continuing."));
			addErrors(request, messages);
			return mapping.findForward("displayContactDetails");
    	}
    	regDetailsForm.setCheckPhysical(dao.hasValidAddress(regDetailsForm.getStudentNr(), "Physical"));
    	//log.debug("AdditionsAction - displayContactDetails - Student has Physical Address: " + regDetailsForm.isCheckPhysical());
    	
    	if (!regDetailsForm.isCheckPhysical()){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "We do not have a physical address for you. Please go to Biographical Details or click on &lsquo;Change Address/Contact Details&rsquo; to update your physical address before continuing."));
			addErrors(request, messages);
			return mapping.findForward("displayContactDetails");
    	}
    	regDetailsForm.setCheckCourier(dao.hasValidAddress(regDetailsForm.getStudentNr(), "Courier"));
    	//log.debug("AdditionsAction - displayContactDetails - Student has Courier Address: " + regDetailsForm.isCheckCourier());
    	
    	if (!regDetailsForm.isCheckCourier()){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "We do not have a courier address for you. Please go to Biographical Details or click on &lsquo;Change Address/Contact Details&rsquo; to update your courier address before continuing."));
			addErrors(request, messages);
			return mapping.findForward("displayContactDetails");
    	}
    	
		String country = "";
		country = dao.getCountry(regDetailsForm.getStudentNr());
		
		if ("1015".equalsIgnoreCase(country)){
			/** 2015 Edmund - Check Address validity before student can continue to additions **/
	    	//check if addresses are valid - only for South African "1015" addresses
			regDetailsForm.setPostalSubCodeValid(false);
			regDetailsForm.setPhysicalSubCodeValid(false);
			regDetailsForm.setCourierSubCodeValid(false);
			String postalBoxOrStreet = "B";
			String physicalBoxOrStreet = "S";
			String courierBoxOrStreet = "S";
			for (int i = 0; i < regDetailsForm.getPostal().size(); i++) {
				if (i == 0){
					postalBoxOrStreet = getBoxOrStreet(regDetailsForm.getPostal().get(i).toString().trim());
				}
				if (!regDetailsForm.isPostalSubCodeValid()) {
					regDetailsForm.setPostalSubCodeValid(dao.isAddressSubCodeValid(regDetailsForm.getStudentNr(), regDetailsForm.getPostal().get(i).toString().trim(),regDetailsForm.getPostalAddress().getPostalCode() ,postalBoxOrStreet, "Postal"));
				} 
	    	}
	    	if (regDetailsForm.isPostalSubCodeValid()){
	    		//log.debug("AdditionsAction - displayContactDetails - Student has Valid Postal Address: " + regDetailsForm.isPostalSubCodeValid());
	    	}else{
	    		//log.debug("AdditionsAction - displayContactDetails - Student Postal Address suburb does not match postal code: " + regDetailsForm.isPostalSubCodeValid());
				messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "You have not entered a postal suburb that corresponds with the postal code or the suburb has been entered incorrectly. Please go to Biographical Details or click on &lsquo;Change Address/Contact Details&rsquo; to update your postal address before continuing.<BR/>Note to only enter the suburb and not the town/city."));
				addErrors(request, messages);
				return mapping.findForward("displayContactDetails");
			}
	    	for (int i = 0; i < regDetailsForm.getPhysical().size(); i++) {
	    		if (i == 0){
					physicalBoxOrStreet = getBoxOrStreet(regDetailsForm.getPhysical().get(i).toString().trim());
					if ("B".equalsIgnoreCase(physicalBoxOrStreet)){
						//log.debug("AdditionsAction - displayContactDetails - Student Physical Address contains P O BOX etc, not Street=" + physicalBoxOrStreet);
						messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "The physical address may not be a postal address. Please go to Biographical Details or click on &lsquo;Change Address/Contact Details&rsquo; to update your physical address before continuing."));
						addErrors(request, messages);
						return mapping.findForward("displayContactDetails");
					}
				}
				if (!regDetailsForm.isPhysicalSubCodeValid()) {
					regDetailsForm.setPhysicalSubCodeValid(dao.isAddressSubCodeValid(regDetailsForm.getStudentNr(), regDetailsForm.getPhysical().get(i).toString().trim(),regDetailsForm.getPhysicalAddress().getPostalCode() ,"S", "Physical"));
				} 
	    	}
	    	if (regDetailsForm.isPhysicalSubCodeValid()){
	    		//log.debug("AdditionsAction - displayContactDetails - Student has Valid Physical Address: " + regDetailsForm.isPhysicalSubCodeValid());
	    	}else{
	    		//log.debug("AdditionsAction - displayContactDetails - Student Physical Address suburb does not match postal code: " + regDetailsForm.isPhysicalSubCodeValid());
				messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "You have not entered a physical suburb that corresponds with the postal code or the suburb has been entered incorrectly. Please go to Biographical Details or click on &lsquo;Change Address/Contact Details&rsquo; to update your physical address before continuing."));
				addErrors(request, messages);
				return mapping.findForward("displayContactDetails");
			}
	    	for (int i = 0; i < regDetailsForm.getCourier().size(); i++) {
	    		if (i == 0){
					courierBoxOrStreet = getBoxOrStreet(regDetailsForm.getCourier().get(i).toString().trim());
					if ("B".equalsIgnoreCase(courierBoxOrStreet)){
						//log.debug("AdditionsAction - displayContactDetails - Student Courier Address contains P O BOX etc, not Street=" + courierBoxOrStreet);
						messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "The courier address may not be a postal address. Please go to Biographical Details or click on &lsquo;Change Address/Contact Details&rsquo; to update your courier address before continuing."));
						addErrors(request, messages);
						return mapping.findForward("displayContactDetails");
					}
				}
				if (!regDetailsForm.isCourierSubCodeValid()) {
					regDetailsForm.setCourierSubCodeValid(dao.isAddressSubCodeValid(regDetailsForm.getStudentNr(), regDetailsForm.getCourier().get(i).toString().trim(),regDetailsForm.getCourierAddress().getPostalCode() ,"S", "Courier"));
				} 
	    	}
	    	if (regDetailsForm.isCourierSubCodeValid()){
	    		//log.debug("AdditionsAction - displayContactDetails - Student has Valid Courier Address: " + regDetailsForm.isCourierSubCodeValid());
	    	}else{
	    		//log.debug("AdditionsAction - displayContactDetails - Student Courier Address suburb does not match postal code: " + regDetailsForm.isCourierSubCodeValid());
				messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "You have not entered a courier suburb that corresponds with the postal code or the suburb has been entered incorrectly. Please go to Biographical Details or click on &lsquo;Change Address/Contact Details&rsquo; to update your courier address before continuing."));
				addErrors(request, messages);
				return mapping.findForward("displayContactDetails");
			}
	    	//Check for Suburb on first Courier Line
			boolean isFirstSubCheck = false;
			//Check for duplication in Courier Address
			boolean isDoubleSubCheck = false;
			String cAddress1 = "";
			String cAddress2 = "";
			String cAddress3 = "";
			String cAddress4 = "";
			String cAddress5 = "";
			String cAddress6 = "";
			
			for (int i = 0; i < regDetailsForm.getCourier().size(); i++) {
				if (i == 0 && regDetailsForm.getCourier().get(i).toString().trim() != null){
					cAddress1 = regDetailsForm.getCourier().get(i).toString().trim();
					isFirstSubCheck = dao.isLineSub(cAddress1);
				}else if (i == 1 && regDetailsForm.getCourier().get(i).toString().trim() != null){
					cAddress2 = regDetailsForm.getCourier().get(i).toString().trim();
				}else if (i == 2 && regDetailsForm.getCourier().get(i).toString().trim() != null){
					cAddress3 = regDetailsForm.getCourier().get(i).toString().trim();
				}else if (i == 3 && regDetailsForm.getCourier().get(i).toString().trim() != null){
					cAddress4 = regDetailsForm.getCourier().get(i).toString().trim();
				}else if (i == 4 && regDetailsForm.getCourier().get(i).toString().trim() != null){
					cAddress5 = regDetailsForm.getCourier().get(i).toString().trim();
				}else if (i == 5 && regDetailsForm.getCourier().get(i).toString().trim() != null){
					cAddress6 = regDetailsForm.getCourier().get(i).toString().trim();
				}
			}
			if(cAddress1 != null && !"".equalsIgnoreCase(cAddress1)){
				if(cAddress1.equalsIgnoreCase(cAddress2) || cAddress1.equalsIgnoreCase(cAddress3) || cAddress1.equalsIgnoreCase(cAddress4) || cAddress1.equalsIgnoreCase(cAddress5) || cAddress1.equalsIgnoreCase(cAddress6)){
					isDoubleSubCheck = true;
					//log.debug("AdditionsAction - displayContactDetails - Student Courier Address - isDoubleSubCheck="+isDoubleSubCheck+", Check: 1=" + cAddress1+", 2="+ cAddress2+", 3="+ cAddress3+", 4="+ cAddress4+", 5="+ cAddress5+", 6="+ cAddress6);
				}
			}
			if(cAddress2 != null && !"".equalsIgnoreCase(cAddress2)){
				if(cAddress2.equalsIgnoreCase(cAddress3) || cAddress2.equalsIgnoreCase(cAddress4) || cAddress2.equalsIgnoreCase(cAddress5) || cAddress2.equalsIgnoreCase(cAddress6)){
					isDoubleSubCheck = true;
					//log.debug("AdditionsAction - displayContactDetails - Student Courier Address - isDoubleSubCheck="+isDoubleSubCheck+", Check: 2="+ cAddress2+", 3="+ cAddress3+", 4="+ cAddress4+", 5="+ cAddress5+", 6="+ cAddress6);
				}
			}
			if(cAddress3 != null && !"".equalsIgnoreCase(cAddress3)){
				if(cAddress3.equalsIgnoreCase(cAddress4) || cAddress3.equalsIgnoreCase(cAddress5) || cAddress3.equalsIgnoreCase(cAddress6)){
					isDoubleSubCheck = true;
					//log.debug("AdditionsAction - displayContactDetails - Student Courier Address - isDoubleSubCheck="+isDoubleSubCheck+", Check: 3="+ cAddress3+", 4="+ cAddress4+", 5="+ cAddress5+", 6="+ cAddress6);
				}
			}
			if(cAddress4 != null && !"".equalsIgnoreCase(cAddress4)){
				if(cAddress4.equalsIgnoreCase(cAddress5) || cAddress4.equalsIgnoreCase(cAddress6)){
					isDoubleSubCheck = true;
					//log.debug("AdditionsAction - displayContactDetails - Student Courier Address - isDoubleSubCheck="+isDoubleSubCheck+", Check: 4="+ cAddress4+", 5="+ cAddress5+", 6="+ cAddress6);
				}
			}
			if(cAddress5 != null && !"".equalsIgnoreCase(cAddress5)){
				if(cAddress5.equalsIgnoreCase(cAddress6)){
					isDoubleSubCheck = true;
					//log.debug("AdditionsAction - displayContactDetails - Student Courier Address - isDoubleSubCheck="+isDoubleSubCheck+", Check: 5="+ cAddress5+", 6="+ cAddress6);
				}
			}
			if(isFirstSubCheck){
				//log.debug("AdditionsAction - displayContactDetails - Student Courier Address  - isFirstSub: " + isFirstSubCheck);
				messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Courier address: The first line may not be a suburb or town. Please include a street name or additional information for the courier delivery."));
				addErrors(request, messages);
				return mapping.findForward("displayContactDetails");
			}
			if(isDoubleSubCheck){
				//log.debug("AdditionsAction - displayContactDetails - Student Courier Address  - isDoubleSub: " + isDoubleSubCheck);
				messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "You have a duplication in your courier address. Please go to Biographical Details or click on &lsquo;Change Address/Contact Details&rsquo; to update your courier address before continuing."));
				addErrors(request, messages);
				return mapping.findForward("displayContactDetails");
			}
			
		}
    	return mapping.findForward("displayContactDetails");
	}
	
	public ActionForward updateAddressDetails(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String serverpath = ServerConfigurationService.getServerUrl();
		return new ActionForward(serverpath+"/unisa-findtool/default.do?sharedTool=unisa.biodetails&originatedFrom=unisa.regdetails",true);
	}

	public ActionForward step1 (
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) throws Exception {

    	ActionMessages messages = new ActionMessages();
		RegDetailsForm regDetailsForm = (RegDetailsForm) form;
		AdditionQueryDAO dao = new AdditionQueryDAO();
		try{

			//log.debug("AdditionsAction - Step1");
			// if licensee student, block addition
			if (dao.isLicenseeStudent(regDetailsForm.getStudentNr())){
				//log.debug("AdditionsAction - Step1 - Student is Licensee Student");
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						//new ActionMessage("message.generalmessage", "Please contact your Licensee to assist with the addition of study units."));
						new ActionMessage("message.generalmessage", "You have been registered by a Licensee. To add additional modules, please send/take your request to the Licensee that did your registration."));

				addErrors(request, messages);
				return mapping.findForward("home");
			}else{
				//log.debug("AdditionsAction - Step1 - Student NOT Licensee Student");
			}
			// if staff member or staff member dependent, block addition
			if (dao.isStaffStudent(regDetailsForm.getStudentNr())){
				//log.debug("AdditionsAction - Step1 - Student is Staff Student");
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "You have been registered as a Unisa staff member or Unisa staff member dependent. To add additional modules, please follow the same procedure as for registrations and send the request to formalstaffreg@unisa.ac.za"));
				addErrors(request, messages);
				return mapping.findForward("home");
			}else{
				//log.debug("AdditionsAction - Step1 - Student NOT Staff Student");
			}
						
			reset(regDetailsForm);
			
			//Write version number to log to check all servers
			log.info("ADDITION Version="+regDetailsForm.getVersion());
			
			if ("H".equalsIgnoreCase(regDetailsForm.getQual().getQualType())){
				regDetailsForm.setAmendQual("0");
			}
			/* Check for at least one valid additions period and set valid periods to be open */
			if (!dao.isAdditionsPeriodValid(regDetailsForm.getQual(), regDetailsForm)){
				// Additions closed
				//log.debug("AdditionsAction - Step1 - Additions closed");
				return mapping.findForward("closed");
			}

			/* Can not submit more than once in 30 day period */
			/*if (!db.canSubmitAddition(regDetailsForm.getStudentNr())){
				return (mapping.findForward("oneSubmission"));
			}*/
			
			// read data from sruaf01
			//log.debug("AdditionsAction - Step1 - Read data from sruaf01");
			readSruaf01(regDetailsForm);
			
			// non formal must be workflow
			// Nov 2009
			if (regDetailsForm.getStudentNr().length() == 8
					&& "7".equals(regDetailsForm.getStudentNr().substring(0, 1))) {
				regDetailsForm.setSelfhelp(false);
				regDetailsForm.setSelfHelpReason("AD7");
			}
			
			String country = "";
			country = dao.getCountry(regDetailsForm.getStudentNr());
			
			/** 2015 Edmund - Check if Student Addresses line 1 exists before student can continue to additions **/
			regDetailsForm.setCheckPostal(dao.hasValidAddress(regDetailsForm.getStudentNr(), "Postal"));
	    	//log.debug("AdditionsAction - Step1 - Student has Postal Address: " + regDetailsForm.isCheckPostal());
	    	
	    	if (!regDetailsForm.isCheckPostal()){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "We do not have a postal address for you. Please go to Biographical Details or click on &lsquo;Change Address/Contact Details&rsquo; to update your postal address before continuing."));
				addErrors(request, messages);
				return mapping.findForward("displayContactDetails");
	    	}
	    	regDetailsForm.setCheckPhysical(dao.hasValidAddress(regDetailsForm.getStudentNr(), "Physical"));
	    	//log.debug("AdditionsAction - Step1 - Student has Courier Address: " + regDetailsForm.isCheckPhysical());
	    	
	    	if (!regDetailsForm.isCheckPhysical()){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "We do not have a physical address for you. Please go to Biographical Details or click on &lsquo;Change Address/Contact Details&rsquo; to update your physical address before continuing."));
				addErrors(request, messages);
				return mapping.findForward("displayContactDetails");
	    	}
	    	regDetailsForm.setCheckCourier(dao.hasValidAddress(regDetailsForm.getStudentNr(), "Courier"));
	    	//log.debug("AdditionsAction - Step1 - Student has Courier Address: " + regDetailsForm.isCheckCourier());
	    	
	    	if (!regDetailsForm.isCheckCourier()){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "We do not have a courier address for you. Please go to Biographical Details or click on &lsquo;Change Address/Contact Details&rsquo; to update your courier address before continuing."));
				addErrors(request, messages);
				return mapping.findForward("displayContactDetails");
	    	}
	    	
	    	//log.debug("AdditionsAction - Step1 - Student Country: " + country);
			if ("1015".equalsIgnoreCase(country)){
				/** 2015 Edmund - Check Address validity before student can continue to additions **/
				//check if addresses are valid - only for South African "1015" addresses
				regDetailsForm.setPostalSubCodeValid(false);
				regDetailsForm.setPhysicalSubCodeValid(false);
				regDetailsForm.setCourierSubCodeValid(false);
				String postalBoxOrStreet = "B";
				String physicalBoxOrStreet = "S";
				String courierBoxOrStreet = "S";
				for (int i = 0; i < regDetailsForm.getPostal().size(); i++) {
					if (i == 0){
						postalBoxOrStreet = getBoxOrStreet(regDetailsForm.getPostal().get(i).toString().trim());
					}
					if (!regDetailsForm.isPostalSubCodeValid()) {
						regDetailsForm.setPostalSubCodeValid(dao.isAddressSubCodeValid(regDetailsForm.getStudentNr(), regDetailsForm.getPostal().get(i).toString().trim(),regDetailsForm.getPostalAddress().getPostalCode() ,postalBoxOrStreet, "Postal"));
					} 
					if (regDetailsForm.isPostalSubCodeValid()) {
						break;
					}
				}
				if (regDetailsForm.isPostalSubCodeValid()){
					//log.debug("AdditionsAction - Step1 - Student has Valid Postal Address: " + regDetailsForm.isPostalSubCodeValid());
				}else{
					//log.debug("AdditionsAction - Step1 - Student Postal Address suburb does not match postal code: " + regDetailsForm.isPostalSubCodeValid());
					messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "You have not entered a postal suburb that corresponds with the postal code or the suburb has been entered incorrectly. Please go to Biographical Details or click on &lsquo;Change Address/Contact Details&rsquo; to update your postal address before continuing.<BR/>Note to only enter the suburb and not the town/city."));
					addErrors(request, messages);
					return mapping.findForward("displayContactDetails");
				}
				for (int i = 0; i < regDetailsForm.getPhysical().size(); i++) {
					if (i == 0){
						physicalBoxOrStreet = getBoxOrStreet(regDetailsForm.getPhysical().get(i).toString().trim());
						if ("B".equalsIgnoreCase(physicalBoxOrStreet)){
							//log.debug("AdditionsAction - Step1 - Student Physical Address contains P O BOX etc, not Street=" + physicalBoxOrStreet);
							messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage", "The physical address may not be a postal address. Please go to Biographical Details or click on &lsquo;Change Address/Contact Details&rsquo; to update your physical address before continuing."));
							addErrors(request, messages);
							return mapping.findForward("displayContactDetails");
						}
					}
					if (!regDetailsForm.isPhysicalSubCodeValid()) {
						regDetailsForm.setPhysicalSubCodeValid(dao.isAddressSubCodeValid(regDetailsForm.getStudentNr(), regDetailsForm.getPhysical().get(i).toString().trim(),regDetailsForm.getPhysicalAddress().getPostalCode() ,"S", "Physical"));
					} 
					if (regDetailsForm.isPhysicalSubCodeValid()) {
						break;
					}
				}
				if (regDetailsForm.isPhysicalSubCodeValid()){
					//log.debug("AdditionsAction - Step1 - Student has Valid Physical Address: " + regDetailsForm.isPhysicalSubCodeValid());
				}else{
					//log.debug("AdditionsAction - Step1 - Student Physical Address suburb does not match postal code: " + regDetailsForm.isPhysicalSubCodeValid());
					messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "You have not entered a physical suburb that corresponds with the postal code or the suburb has been entered incorrectly. Please go to Biographical Details or click on &lsquo;Change Address/Contact Details&rsquo; to update your physical address before continuing."));
					addErrors(request, messages);
					return mapping.findForward("displayContactDetails");
				}
				
				for (int i = 0; i < regDetailsForm.getCourier().size(); i++) {
					if (i == 0){
						courierBoxOrStreet = getBoxOrStreet(regDetailsForm.getCourier().get(i).toString().trim());
						if ("B".equalsIgnoreCase(courierBoxOrStreet)){
							//log.debug("AdditionsAction - Step1 - Student Courier Address contains P O BOX etc, not Street=" + courierBoxOrStreet);
							messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage", "The courier address may not be a postal address. Please go to Biographical Details or click on &lsquo;Change Address/Contact Details&rsquo; to update your courier address before continuing."));
							addErrors(request, messages);
							return mapping.findForward("displayContactDetails");
						}
					}
					if (!regDetailsForm.isCourierSubCodeValid()) {
						regDetailsForm.setCourierSubCodeValid(dao.isAddressSubCodeValid(regDetailsForm.getStudentNr(), regDetailsForm.getCourier().get(i).toString().trim(),regDetailsForm.getCourierAddress().getPostalCode() ,"S", "Courier"));
					} 
					if (regDetailsForm.isCourierSubCodeValid()) {
						break;
					}
				}
				
				if (regDetailsForm.isCourierSubCodeValid()){
					//log.debug("AdditionsAction - Step1 - Student has Valid Courier Address: " + regDetailsForm.isCourierSubCodeValid());
				}else{
					//log.debug("AdditionsAction - Step1 - Student Courier Address suburb does not match postal code: " + regDetailsForm.isCourierSubCodeValid());
					messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "You have not entered a courier suburb that corresponds with the postal code or the suburb has been entered incorrectly. Please go to Biographical Details or click on &lsquo;Change Address/Contact Details&rsquo; to update your courier address before continuing."));
					addErrors(request, messages);
					return mapping.findForward("displayContactDetails");
				}
				//Check for Suburb on first Courier Line
				boolean isFirstSubCheck = false;
				//Check for duplication in Courier Address
				boolean isDoubleSubCheck = false;
				String cAddress1 = "";
				String cAddress2 = "";
				String cAddress3 = "";
				String cAddress4 = "";
				String cAddress5 = "";
				String cAddress6 = "";
				
				for (int i = 0; i < regDetailsForm.getCourier().size(); i++) {
					if (i == 0 && regDetailsForm.getCourier().get(i).toString().trim() != null){
						cAddress1 = regDetailsForm.getCourier().get(i).toString().trim();
						isFirstSubCheck = dao.isLineSub(cAddress1);
					}else if (i == 1 && regDetailsForm.getCourier().get(i).toString().trim() != null){
						cAddress2 = regDetailsForm.getCourier().get(i).toString().trim();
					}else if (i == 2 && regDetailsForm.getCourier().get(i).toString().trim() != null){
						cAddress3 = regDetailsForm.getCourier().get(i).toString().trim();
					}else if (i == 3 && regDetailsForm.getCourier().get(i).toString().trim() != null){
						cAddress4 = regDetailsForm.getCourier().get(i).toString().trim();
					}else if (i == 4 && regDetailsForm.getCourier().get(i).toString().trim() != null){
						cAddress5 = regDetailsForm.getCourier().get(i).toString().trim();
					}else if (i == 5 && regDetailsForm.getCourier().get(i).toString().trim() != null){
						cAddress6 = regDetailsForm.getCourier().get(i).toString().trim();
					}
				}
				if(cAddress1 != null && !"".equalsIgnoreCase(cAddress1)){
					if(cAddress1.equalsIgnoreCase(cAddress2) || cAddress1.equalsIgnoreCase(cAddress3) || cAddress1.equalsIgnoreCase(cAddress4) || cAddress1.equalsIgnoreCase(cAddress5) || cAddress1.equalsIgnoreCase(cAddress6)){
						isDoubleSubCheck = true;
						//log.debug("AdditionsAction - Step1 - Student Courier Address - isDoubleSubCheck="+isDoubleSubCheck+", Check: 1=" + cAddress1+", 2="+ cAddress2+", 3="+ cAddress3+", 4="+ cAddress4+", 5="+ cAddress5+", 6="+ cAddress6);
					}
				}
				if(cAddress2 != null && !"".equalsIgnoreCase(cAddress2)){
					if(cAddress2.equalsIgnoreCase(cAddress3) || cAddress2.equalsIgnoreCase(cAddress4) || cAddress2.equalsIgnoreCase(cAddress5) || cAddress2.equalsIgnoreCase(cAddress6)){
						isDoubleSubCheck = true;
						//log.debug("AdditionsAction - Step1 - Student Courier Address - isDoubleSubCheck="+isDoubleSubCheck+", Check: 2="+ cAddress2+", 3="+ cAddress3+", 4="+ cAddress4+", 5="+ cAddress5+", 6="+ cAddress6);
					}
				}
				if(cAddress3 != null && !"".equalsIgnoreCase(cAddress3)){
					if(cAddress3.equalsIgnoreCase(cAddress4) || cAddress3.equalsIgnoreCase(cAddress5) || cAddress3.equalsIgnoreCase(cAddress6)){
						isDoubleSubCheck = true;
						//log.debug("AdditionsAction - Step1 - Student Courier Address - isDoubleSubCheck="+isDoubleSubCheck+", Check: 3="+ cAddress3+", 4="+ cAddress4+", 5="+ cAddress5+", 6="+ cAddress6);
					}
				}
				if(cAddress4 != null && !"".equalsIgnoreCase(cAddress4)){
					if(cAddress4.equalsIgnoreCase(cAddress5) || cAddress4.equalsIgnoreCase(cAddress6)){
						isDoubleSubCheck = true;
						//log.debug("AdditionsAction - Step1 - Student Courier Address - isDoubleSubCheck="+isDoubleSubCheck+", Check: 4="+ cAddress4+", 5="+ cAddress5+", 6="+ cAddress6);
					}
				}
				if(cAddress5 != null && !"".equalsIgnoreCase(cAddress5)){
					if(cAddress5.equalsIgnoreCase(cAddress6)){
						isDoubleSubCheck = true;
						//log.debug("AdditionsAction - Step1 - Student Courier Address - isDoubleSubCheck="+isDoubleSubCheck+", Check: 5="+ cAddress5+", 6="+ cAddress6);
					}
				}
				if(isFirstSubCheck){
					//log.debug("AdditionsAction - Step1 - Student Courier Address  - isFirstSub: " + isFirstSubCheck);
					messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Courier address: The first line may not be a suburb or town. Please include a street name or additional information for the courier delivery."));
					addErrors(request, messages);
					return mapping.findForward("displayContactDetails");
				}
				if(isDoubleSubCheck){
					//log.debug("AdditionsAction - Step1 - Student Courier Address  - isDoubleSub: " + isDoubleSubCheck);
					messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "You have a duplication in your courier address. Please go to Biographical Details or click on &lsquo;Change Address/Contact Details&rsquo; to update your courier address before continuing."));
					addErrors(request, messages);
					return mapping.findForward("displayContactDetails");
				}
			}
			
			if (!regDetailsForm.getApplyForStudyUnits().isEmpty()){
				/* go to view screen if additions already exist */
				//log.debug("AdditionsAction - Step1 - Return - step1a");
				return mapping.findForward("step1a");
			}else{
				//log.debug("AdditionsAction - Step1 - Return - step1");
				return mapping.findForward("step1");
			}
			
		} catch(Exception e){
			throw e;
		}
		
	}

	public String displaySpecList (
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		//log.info("ADDITION - IN displaySpecList");
		
		/** 2014 Edmund - New code for Qual and Spec **/
		try{
			RegDetailsForm regDetailsForm = (RegDetailsForm) form;
			ActionMessages messages = new ActionMessages();
			AdditionQueryDAO dao = new AdditionQueryDAO();
			
			Qualification qual = new Qualification();
			int pos = 0;
			String qualStr = regDetailsForm.getSelectedQual();
			//log.info("ADDITION - displaySpecList - qualStr="+qualStr);
			// test for valid qualification
			if(qualStr.indexOf("@")!=-1){
				//log.debug("There is '@' in qualStr string");
				pos = qualStr.indexOf("@");
				qual.setQualCode(qualStr.substring(0,pos));
				qual.setQualDesc(dao.getQualDesc(qual.getQualCode()));
				regDetailsForm.setNewQual(qual);
			}else{
				//log.debug("There is no '@' in qualStr string");
				//log.info("ADDITION - displaySpecList - studnr="+regDetailsForm.getStudentNr() + ", QualSpec="+qualStr);
				messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "The qualification returned empty or an error occured, please try again or if you need advice, kindly contact Unisa at study-info@unisa.ac.za"));
					addErrors(request, messages);
					return "step1";
			}
				
			//log.info("ADDITION - displaySpecList - qualCode="+qual.getQualCode());
			//log.info("ADDITION - displaySpecList - qualDesc="+qual.getQualDesc());
	
			String specStr = (qualStr.substring(pos+1,qualStr.length()));
	  		if ("N/A".equalsIgnoreCase(specStr)){
	  			regDetailsForm.setSelectedSpec("");
	  			regDetailsForm.getNewQual().setSpecCode("");
				regDetailsForm.getNewQual().setSpecDesc("No specialisation for this qualification");
	  		}else{
	  			regDetailsForm.setSelectedSpec(specStr);
	  			regDetailsForm.getNewQual().setSpecCode(specStr);
				regDetailsForm.getNewQual().setSpecDesc(dao.getSpecDesc(qual.getQualCode(), specStr));
	  		}
	  		/**
	  		log.info("ADDITION - displaySpecList - specCode="+regDetailsForm.getNewQual().getSpecCode());
			log.info("ADDITION - displaySpecList - specDesc="+regDetailsForm.getNewQual().getSpecDesc());
	  		
	  		// test for valid qualification
	  		String errorMsg = checkQualificationChangeViaSruaf01(regDetailsForm);
	  		if (!"".equals(errorMsg)){
	  			messages.add(ActionMessages.GLOBAL_MESSAGE,
	  			new ActionMessage("message.generalmessage", errorMsg));
	  			addErrors(request, messages);
	  			return "step1";
	  		}
	  		**/
	  		
	  		/** 2017 Edmund - New code for Flag 281 **/
	  		if ("98231".equalsIgnoreCase(regDetailsForm.getNewQual().getQualCode()) || "98255".equalsIgnoreCase(regDetailsForm.getNewQual().getQualCode())){
	  			if (dao.getQualFlag281(regDetailsForm.getNewQual().getQualCode())){
	  				return "step2d";
	  			}
	  		}
	  		
			/**2014 Edmund Phased Out **/
			//Set if repeaters and we need to display phased out warning
			if ((dao.isQualPhasedOut(regDetailsForm.getStudentNr(), regDetailsForm.getNewQual().getQualCode())) || (dao.isSpecPhasedOut(regDetailsForm.getStudentNr(), regDetailsForm.getNewQual().getQualCode(),regDetailsForm.getNewQual().getSpecCode()))){
				regDetailsForm.getNewQual().setQualPhasedOut(true);
				//log.info("AdditionAction - displaySpecList for studnr="+regDetailsForm.getStudentNr() + " - setQualPhasedOut: " + regDetailsForm.getNewQual().getQualPhasedOut());
				//log.info("AdditionAction - displaySpecList for studnr="+regDetailsForm.getStudentNr() + " - goto to Step2c");
				return "step2c";
			}else{
				regDetailsForm.getNewQual().setQualPhasedOut(false);
				//log.info("AdditionAction - displaySpecList for studnr="+regDetailsForm.getStudentNr() + " - setQualPhasedOut: " + regDetailsForm.getNewQual().getQualPhasedOut());
				//log.info("AdditionAction - displaySpecList for studnr="+regDetailsForm.getStudentNr() + " - goto to Step2b");
				setupNumbersList(request);
				return "step2b";
			}
		} catch(Exception e){
			throw e;
		}

	}
	/**
	private boolean setupSpecList(RegDetailsForm regDetailsForm,HttpServletRequest request) throws Exception{

		boolean result;
		result = true;

		ArrayList specList = new ArrayList();

		/* Add specialities list to request
		AdditionQueryDAO db = new AdditionQueryDAO();
		if (! "0".equalsIgnoreCase(regDetailsForm.getAmendQual())){
			specList = db.getSpecList(regDetailsForm.getNewQual().getQualCode());
		}
		if (specList.isEmpty()){
			specList.add(0,new LabelValueBean("No specialisation for this qualification",""));
			result = false;
		}else if (specList.size()==1){
			/* if only one spec and it is equal to one space  show no spec for this degree
			LabelValueBean bean = new LabelValueBean();
			bean =(LabelValueBean) specList.get(0);
			int pos = bean.getValue().indexOf("-");
			String spec = bean.getValue().substring(0,pos-1);
  		if (" ".equalsIgnoreCase(spec)){
				regDetailsForm.getNewQual().setSpecCode(" ");
				regDetailsForm.getNewQual().setSpecDesc("");
				specList.clear();
				specList.add(0,new LabelValueBean("No specialisation for this qualification",""));
				result = false;
			}
		}
		if (regDetailsForm.getQual().getQualCode().equalsIgnoreCase(regDetailsForm.getNewQual().getQualCode())){
			/* Add student's current speciality to first position in list if degree did not change 
			if (regDetailsForm.getQual().getSpecCode() != null && !"".equalsIgnoreCase(regDetailsForm.getQual().getSpecCode())){
				specList.add(0,new LabelValueBean(regDetailsForm.getQual().getSpecCode() + " - " + regDetailsForm.getQual().getSpecDesc(), regDetailsForm.getQual().getSpecCode() +" - "+ regDetailsForm.getQual().getSpecDesc()));
			}
		}else {
			/* add select from list to dopdown 
			specList.add(0,new LabelValueBean("Select a specialisation","Select a specialisation"));
		}
	request.setAttribute("specList", specList);
	return result;

}
	**/

	public String step2a (
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ArrayList qualList = new ArrayList();
		RegDetailsForm regDetailsForm = (RegDetailsForm) form;
		ActionMessages messages = new ActionMessages();

		try{
			//log.info("ADDITION step2a for studnr="+regDetailsForm.getStudentNr());
			/* clear selection vars used on page 3 for study unit lists*/
			regDetailsForm.setValueReg0("");
			regDetailsForm.setValueReg1("");
			regDetailsForm.setValueReg2("");
			regDetailsForm.setValueReg6("");

			/* Validate input */
			/** 2014 Edmund - No longer required as Spec is now part of Qualification Dropdown
			//Can not change spec code if qual doesn't have one
			if ("2".equals(regDetailsForm.getAmendQual()) && "".equals(regDetailsForm.getQual().getSpecCode())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "No specialisation for this qualification"));
				addErrors(request, messages);
				return "step1";
			}
			**/
			/* check email-address */
			if (regDetailsForm.getEmail() == null || "".equalsIgnoreCase(regDetailsForm.getEmail()) ){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "No e-mail address on record. Go to Biographical Details to update your e-mail address before continuing."));
				addErrors(request, messages);
				return "step1";
			}

			//log.info("AdditionsAction - step2a for studnr="+regDetailsForm.getStudentNr() + " - getQualList2014  QualType: " + regDetailsForm.getQual().getQualType() + ", Institution: " + regDetailsForm.getQual().getInstitution());

			if ("H".equalsIgnoreCase(regDetailsForm.getQual().getQualType())){
				/* hons student */
				/* setup numbers list */
				return step2b(mapping,form,request, response);
			} else {
				if (regDetailsForm.getAmendQual() == null || "".equalsIgnoreCase(regDetailsForm.getAmendQual()) ){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Select one of the options"));
					addErrors(request, messages);
					return "step1";
				} else if ("1".equalsIgnoreCase(regDetailsForm.getAmendQual())){
					/* Add qualification list to request*/
					AdditionQueryDAO db = new AdditionQueryDAO();
					qualList = db.getQualList2014(regDetailsForm.getQual().getQualType(),regDetailsForm.getQual().getInstitution(), regDetailsForm.getStudentNr());
					/* Add student's current qual to first position in list*/
					String tmpSpec = "N/A";
					if (!" ".equals(regDetailsForm.getQual().getSpecCode()) && !"".equals(regDetailsForm.getQual().getSpecCode()) && regDetailsForm.getQual().getSpecCode() != null ){
						tmpSpec=regDetailsForm.getQual().getSpecCode();
					}
					qualList.add(0,new org.apache.struts.util.LabelValueBean(regDetailsForm.getQual().getQualCode() + " - " + regDetailsForm.getQual().getQualDesc() + " - Speciality: " + tmpSpec, regDetailsForm.getQual().getQualCode() + "@" + tmpSpec));
					request.setAttribute("qualList", qualList);
				} else {
					return step2b(mapping,form,request, response);
				}
			}
			return "step2a";

		} catch(Exception e){
			throw e;
		}
	}

//xxx	
	private void setupNumbersList(HttpServletRequest request){
		/* setup numbers list */
		ArrayList list = new ArrayList();
		for (int i = 1; i <12; i++) {
			list.add(new LabelValueBean(Integer.toString(i),Integer.toString(i)));
			request.setAttribute("numbers", list);
		}
	}

	public String step2b (
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		RegDetailsForm regDetailsForm = (RegDetailsForm) form;
		ActionMessages messages = new ActionMessages();
		AdditionQueryDAO dao = new AdditionQueryDAO();
		
		boolean result;

		try{
			//log.info("ADDITION step2b for studnr="+regDetailsForm.getStudentNr());
			/* clear selection vars used on page 3 for study unit lists*/
			regDetailsForm.setValueReg0("");
			regDetailsForm.setValueReg1("");
			regDetailsForm.setValueReg2("");
			regDetailsForm.setValueReg6("");

			/* setup numbers list */
			setupNumbersList(request);
			
			//log.info("AdditionsAction - step2b for studnr="+regDetailsForm.getStudentNr() + " - AmendQual: " + regDetailsForm.getAmendQual());
			//log.info("AdditionsAction - step2b for studnr="+regDetailsForm.getStudentNr() + " - SelectedQual: " + regDetailsForm.getSelectedQual());
				
			/** 2014 Edmund - New Code for Qual and Spec **/
			if ("1".equalsIgnoreCase(regDetailsForm.getAmendQual())){
				Qualification qual = new Qualification();
				int pos = 0;
				String qualStr = regDetailsForm.getSelectedQual();
				//log.info("ADDITION - step2b - qualStr="+qualStr);
				// test for valid qualification
				if(qualStr.indexOf("@")!=-1){
					//log.debug("There is '@' in qualStr string");
					pos = qualStr.indexOf("@");
					qual.setQualCode(qualStr.substring(0,pos));
					qual.setQualDesc(dao.getQualDesc(qual.getQualCode()));
					regDetailsForm.setNewQual(qual);
				}else{
					//log.debug("There is no '@' in qualStr string");
					//log.info("ADDITION - displaySpecList - studnr="+regDetailsForm.getStudentNr() + ", QualSpec="+qualStr);
					messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "The qualification returned empty or an error occured, please try again or if you need advice, kindly contact Unisa at study-info@unisa.ac.za"));
						addErrors(request, messages);
						return displaySpecList(mapping,form,request, response);
				}
		
				String specStr = (qualStr.substring(pos+1,qualStr.length()));
		  		if ("N/A".equalsIgnoreCase(specStr)){
		  			regDetailsForm.setSelectedSpec("");
			  		regDetailsForm.getNewQual().setSpecCode("");
					regDetailsForm.getNewQual().setSpecDesc("No specialisation for this qualification");
			  	}else{
			  		regDetailsForm.setSelectedSpec(specStr);
			  		regDetailsForm.getNewQual().setSpecCode(specStr);
					regDetailsForm.getNewQual().setSpecDesc(dao.getSpecDesc(qual.getQualCode(), specStr));
			  	}
			  		
			}else{
				regDetailsForm.getNewQual().setQualCode(regDetailsForm.getQual().getQualCode());
				regDetailsForm.getNewQual().setSpecCode(regDetailsForm.getQual().getSpecCode());
				//log.info("ADDITION - step2b - ELSE");
				//log.info("ADDITION - step2b - oldQual="+regDetailsForm.getNewQual().getQualCode() + ", oldSpec="+regDetailsForm.getNewQual().getSpecCode());
			}
			
			//Only set SelfHelp false if Qualification and Specialisation has actually changed. Previously did it even if student selected change radio button, but didn't select a new Qualification or Specialisation
			if(regDetailsForm.getNewQual().getQualCode()!= null && regDetailsForm.getQual().getQualCode() != null && 
					regDetailsForm.getNewQual().getSpecCode() != null && regDetailsForm.getQual().getSpecCode() != null && 
							!regDetailsForm.getQual().getQualCode().equals(regDetailsForm.getNewQual().getQualCode()) && 
							!regDetailsForm.getQual().getSpecCode().trim().equals(regDetailsForm.getNewQual().getSpecCode().trim())){
				regDetailsForm.setSelfhelp(false);
				regDetailsForm.setSelfHelpReason("ADT");
			}else{
				//Only set SelfHelp false if Qualification has actually changed. Previously did it even if student selected change radio button, but didn't select a new Qualification
				if(regDetailsForm.getNewQual().getQualCode()!= null && regDetailsForm.getQual().getQualCode() != null && !regDetailsForm.getQual().getQualCode().equals(regDetailsForm.getNewQual().getQualCode())){
					regDetailsForm.setSelfhelp(false);
					regDetailsForm.setSelfHelpReason("ADQ");
				}
	
				//Only set SelfHelp false if Specialisation has actually changed. Previously did it even if student selected change radio button, but didn't select a new Specialisation
				if(regDetailsForm.getNewQual().getSpecCode() != null && regDetailsForm.getQual().getSpecCode() != null && !regDetailsForm.getQual().getSpecCode().trim().equals(regDetailsForm.getNewQual().getSpecCode().trim())){
					regDetailsForm.setSelfhelp(false);
					regDetailsForm.setSelfHelpReason("ADS");
				}
			}
			
			//clean selected study units list
			regDetailsForm.setSelectedAdditionalStudyUnits(new ArrayList());
			
			/**
			log.info("ADDITION - step2b - qualCode="+regDetailsForm.getNewQual().getQualCode());
			log.info("ADDITION - step2b - qualDesc="+regDetailsForm.getNewQual().getQualDesc());
			
			log.info("ADDITION - step2b - specCode="+regDetailsForm.getNewQual().getSpecCode());
			log.info("ADDITION - step2b - specDesc="+regDetailsForm.getNewQual().getSpecDesc());
			
			// test for valid qualification
	  		String errorMsg = checkQualificationChangeViaSruaf01(regDetailsForm);
			if (!"".equals(errorMsg)){
	  			messages.add(ActionMessages.GLOBAL_MESSAGE,
	  			new ActionMessage("message.generalmessage", errorMsg));
	  			addErrors(request, messages);
	  			return "step1";
	  		}
			**/
			
	  		/** 2017 Edmund - New code for Flag 281 **/
	  		if ("98231".equalsIgnoreCase(regDetailsForm.getNewQual().getQualCode()) || "98255".equalsIgnoreCase(regDetailsForm.getNewQual().getQualCode())){
	  			if (dao.getQualFlag281(regDetailsForm.getNewQual().getQualCode())){
	  				return "step2d";
	  			}
	  		}
			
			/** 2014 Edmund Phased Out **/
			//Set if repeaters and we need to display phased out warning
			if ((dao.isQualPhasedOut(regDetailsForm.getStudentNr(), regDetailsForm.getNewQual().getQualCode())) || (dao.isSpecPhasedOut(regDetailsForm.getStudentNr(), regDetailsForm.getNewQual().getQualCode(),regDetailsForm.getNewQual().getSpecCode()))){
				regDetailsForm.getNewQual().setQualPhasedOut(true);
				//log.info("AdditionAction - step2b for studnr="+regDetailsForm.getStudentNr() + " - setQualPhasedOut: " + regDetailsForm.getNewQual().getQualPhasedOut());
			}else{
				regDetailsForm.getNewQual().setQualPhasedOut(false);
				//log.info("AdditionAction - step2b for studnr="+regDetailsForm.getStudentNr() + " - setQualPhasedOut: " + regDetailsForm.getNewQual().getQualPhasedOut());
			}
			
		} catch(Exception e){
			throw e;
		}
		
		//log.info("AdditionAction - step2b for studnr="+regDetailsForm.getStudentNr() + " - Qual: " + regDetailsForm.getNewQual().getQualCode() + ", Spec: " + regDetailsForm.getNewQual().getSpecCode() + ", getQualPhasedOut: " + regDetailsForm.getNewQual().getQualPhasedOut());
		if(regDetailsForm.getNewQual().getQualPhasedOut()){
			//log.debug("AdditionAction - step2b - goto to Step2c");
			return "step2c";
		}else{
			//log.debug("AdditionAction - step2b - goto to Step2b");
			setupNumbersList(request);
			return "step2b";
		}
	}

	public String step2c (
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		//log.debug("AdditionAction - step2c - Entering Step2C");
		
		RegDetailsForm regDetailsForm = (RegDetailsForm) form;
		ActionMessages messages = new ActionMessages();

		try{
			//log.info("ADDITION step2c for studnr="+regDetailsForm.getStudentNr() + ", Declare=" + regDetailsForm.getPhasedOutDeclare());
			if (!"true".equalsIgnoreCase(regDetailsForm.getPhasedOutDeclare())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", "Please confirm that you have read the phased out notice."));
				addErrors(request, messages);
			return "step2c";
			}
		} catch(Exception e){
			throw e;
		}
		setupNumbersList(request);
		return "step2b";
	}
	
	
	private ArrayList setStudyUnitLists(ArrayList list, int index, HttpServletRequest request){
		StudyUnit su = new StudyUnit();
		ArrayList resultList = new ArrayList();
		String displayValue = "";
		//HashMap studyUnitMap = new HashMap(500);

		for (int i = 0; i < list.size(); i++) {
			su = (StudyUnit)list.get(i);

			//setup list for screen display
			if ("Y".equalsIgnoreCase(su.getHEQF())){
				displayValue = su.getCode()+" - "+su.getDesc()+" - "+su.getCredits();
			}else{
				displayValue = su.getCode()+" - "+su.getDesc();
			}
			resultList.add(new org.apache.struts.util.LabelValueBean(displayValue,su.getCode()+"@"+su.getCredits()+"@"+su.getHEQF()));
		}

		return resultList;
	}

	private void setRegPeriodForScreenDisplay(RegDetailsForm form, HttpServletRequest request,boolean postGrad) throws Exception{
		// setup messages for closed reg periods on screen
		RegQueryDAO db = new RegQueryDAO();
		String type = "IA"; /* undergrad */
		if (postGrad) {
			type = "HA"; /* postgrad */
		}
		if (db.isRegPeriodValid(type, "0")) {
			form.setRegPeriodOpen0("Y");
		}else{
			form.setRegPeriodOpen0("");
		}
		if (db.isRegPeriodValid(type, "1")) {
			form.setRegPeriodOpen1("Y");
		}else{
			form.setRegPeriodOpen1("");
		}
		if (db.isRegPeriodValid(type, "2")) {
			form.setRegPeriodOpen2("Y");
		}else{
			form.setRegPeriodOpen2("");
		}
		if (db.isRegPeriodValid(type, "6")) {
			form.setRegPeriodOpen6("Y");
		}else{
			form.setRegPeriodOpen6("");
		}
		//log.debug("AdditionsAction - setRegPeriodForScreenDisplay - Type="+type+", RegPeriodOpen0="+form.getRegPeriodOpen0());
		//log.debug("AdditionsAction - setRegPeriodForScreenDisplay - Type="+type+", RegPeriodOpen1="+form.getRegPeriodOpen1());
		//log.debug("AdditionsAction - setRegPeriodForScreenDisplay - Type="+type+", RegPeriodOpen2="+form.getRegPeriodOpen2());
		//log.debug("AdditionsAction - setRegPeriodForScreenDisplay - Type="+type+", RegPeriodOpen6="+form.getRegPeriodOpen6());
	}

	/* Setup the lists to display on the screen according to:
	 *  Undergrad/Postgrad - U/P
	 *  Type of list: S-specific, A-All, T-Type in*/
	private ArrayList addList (RegDetailsForm form,HttpServletRequest request) throws Exception{
		AdditionQueryDAO db = new AdditionQueryDAO();
		ArrayList list = new ArrayList();
		StringBuilder userSU = new StringBuilder();
		
		//log.info("ADDITION addList for studnr="+form.getStudentNr() + ", QualType: " + form.getQual().getQualType() + ", ListType: " + form.getListType() + ", QualCode: " + form.getNewQual().getQualCode() + ", SpecCode: " + form.getNewQual().getSpecCode() + ", OrderType: " + form.getOrderType());

		//Build list of Alreeady registered units
		/*
		for (int t = 0; t < form.getStudyUnits().size(); t++) {
			StudyUnit su = new StudyUnit();
			su = (StudyUnit) form.getStudyUnits().get(t);
			if("Registered".equalsIgnoreCase(su.getStatus())){
				userSU = (new StringBuilder()).append(userSU).append(",").append(su.getCode());
			}
		}
		for (int t = 0; t < form.getSupplementStudyUnits().size(); t++) {
			StudyUnit su = new StudyUnit();
			su = (StudyUnit) form.getSupplementStudyUnits().get(t);
			userSU = (new StringBuilder()).append(userSU).append(",").append(su.getCode());
		}
		*/
		
		if ("U".equalsIgnoreCase(form.getQual().getQualType())){
			// setup open periods for screen display
			setRegPeriodForScreenDisplay(form,request,false);

			/* SPECIFIC for UNDERGRAD */
			if ("S".equalsIgnoreCase(form.getListType())){
				//log.info("ADDITION addList for studnr="+form.getStudentNr() + ", In Qual specific SU List - ListType: " + form.getListType());
				if (request.getAttribute("list") == null){
					list = db.getSpecificSUList(form.getNewQual().getQualCode(),form.getNewQual().getSpecCode(),form.getOrderType(), false);
				}
			/* ALL for UNDERGRAD */
			}else if ("A".equalsIgnoreCase(form.getListType())){
				//log.info("ADDITION addList for studnr="+form.getStudentNr() + ", In Qual ALL SU List - ListType: " + form.getListType());
				if (request.getAttribute("list") == null){
					list = db.getAllUnderRegSUList(form.getOrderType());
				}
			}
		} else if("H".equalsIgnoreCase(form.getQual().getQualType())){
			//setup open periods for screen display
			setRegPeriodForScreenDisplay(form,request,true);

			/* ALL for POSTGRADUATE */
			if ("A".equalsIgnoreCase(form.getListType())){
				list = db.getAllHonsRegSUList(form.getOrderType());
			/* SPECIFIC for POSTGRADUATE */
			}else if ("S".equalsIgnoreCase(form.getListType())){
				list = db.getSpecificSUList(form.getNewQual().getQualCode(),form.getNewQual().getSpecCode(),form.getOrderType(), true);
			}
		}
		return list;
	}

	public String step3 (
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		RegDetailsForm regDetailsForm = (RegDetailsForm) form;
		ActionMessages messages = new ActionMessages();
		// Setup array to size of selected number of study units to be added
		ArrayList<StudyUnit> list = new ArrayList<StudyUnit>();

		try{
			//log.info("ADDITION step3 for studnr="+regDetailsForm.getStudentNr());
			/* Validate input */
			if (regDetailsForm.getListType()== null || "".equalsIgnoreCase(regDetailsForm.getListType()) ){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Select the study unit display method."));
				addErrors(request, messages);
				return step2b(mapping,form,request, response);
			}else if (regDetailsForm.getListType()!= null && !"T".equalsIgnoreCase(regDetailsForm.getListType())
					  && (regDetailsForm.getOrderType()== null || "".equals(regDetailsForm.getOrderType()))){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Select the order of the study units by code or description."));
				addErrors(request, messages);
				return step2b(mapping,form,request, response);
			}
			if ("T".equalsIgnoreCase(regDetailsForm.getListType())){
				// Type in study units
				if (regDetailsForm.getNumberOfUnits()>0 ){
					if (regDetailsForm.getSelectedAdditionalStudyUnits() ==null || regDetailsForm.getSelectedAdditionalStudyUnits().isEmpty()){
						//for (int i = 0; i < regDetailsForm.getNumberOfUnits(); i++) {
						//	list.addAll("");
						//}
						regDetailsForm.setSelectedAdditionalStudyUnits(list);
					}
				}
			}else{
				// Add lists for screen display as well as hash map for later reference
				list = addList(regDetailsForm,request);
				// if no study units were added to the picklist, give error
				if(list == null || list.isEmpty() || list.size() <= 0){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "No open registration periods for your qualification."));
					addErrors(request, messages);
					return step2b(mapping,form,request, response);
				}

				if (regDetailsForm.getNumberOfUnits()>0 ){
					if (regDetailsForm.getSelectedAdditionalStudyUnits() ==null || regDetailsForm.getSelectedAdditionalStudyUnits().isEmpty()){
						regDetailsForm.setListStudyUnits(list);
					}
				}
			}
			/* default complete qual to N */
			regDetailsForm.setCompleteQual("N");

		} catch(Exception e){
			throw e;
		}
		return "step3";
	}

	/**
	 * This method returns true if an exam clash does exist between
	 * the specific input study unit and the rest of the study units
	 * in the list.
	 * There can be up to a ridiculous 6 exams per study unit
	 *
	 * @param studyUnit         The newly selected study unit
	 * @param selectedList		The already selected study unit list
	 * @param registeredList	The registered study unit list
	*/
	private String testForExamClash(StudyUnit studyUnit, ArrayList selectedList,ArrayList registeredList)
			throws Exception {

		AdditionQueryDAO db = new AdditionQueryDAO();
		String errMessage = "";
		boolean clash = false;
		String testDate = "";

		try {
			examClashTest:
			for (int i = 0; i < studyUnit.getExamDates().size(); i++) {
			// Loop all exam dates of the input module that is being checked
				String inputTestDate = (studyUnit.getExamDates().get(i)).toString();
				for (int m = 0; m < selectedList.size(); m++) {
					// Loop through rest of modules in list of already selected modules
					StudyUnit su = new StudyUnit();
					su = (StudyUnit) selectedList.get(m);
					if (!su.getCode().equalsIgnoreCase(studyUnit.getCode())) {
						for (int s = 0; s < su.getExamDates().size(); s++) {
							// Loop all exam dates for the specific module in list
							testDate = (su.getExamDates().get(s).toString());
							if (testDate != null
								&& !"Departmental requirement"
										.equalsIgnoreCase(testDate)
								&& !"Will be informed".equalsIgnoreCase(testDate)) {
								if (testDate.equals(inputTestDate)) {
									clash = true;
								}
							}
							if (clash) {
								/* Add error message */
								errMessage = studyUnit.getCode() + " and "
									+ su.getCode()
									+ " - Examination date clash.";
								break examClashTest;
							}
						}
					}
				}
				for (int p = 0; p < registeredList.size(); p++) {
					// Loop through modules in list of already registered modules
					StudyUnit su = new StudyUnit();
					// Loop through current registered modules for student
					su = (StudyUnit) registeredList.get(p);
					if (!su.getCode().equalsIgnoreCase(studyUnit.getCode())) {
						for (int s = 0; s < su.getExamDates().size(); s++) {
							// Loop all exam dates for the specific module in list
							testDate = (su.getExamDates().get(s).toString());
							if (testDate != null
									&& !"Departmental requirement"
									.equalsIgnoreCase(testDate)
									&& !"Will be informed".equalsIgnoreCase(testDate)) {
								if (testDate.equals(inputTestDate)) {
									clash = true;
								}
							}
							if (clash) {
								/* Add error message */
								errMessage = studyUnit.getCode() + " and "
								+ su.getCode()
								+ " - Examination date clash.";
								break examClashTest;
							}
						}
					}
				}
			}
		} catch (Exception e) {
			throw e;
		}

		return errMessage;
	}

	public String step4 (
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		RegDetailsForm regDetailsForm = (RegDetailsForm) form;
		ActionMessages messages = new ActionMessages();
		AdditionQueryDAO db = new AdditionQueryDAO();
		ArrayList list = new ArrayList();
				
		try{
			//log.info("ADDITION step4 for studnr="+regDetailsForm.getStudentNr());
			String fromPage = request.getParameter("goto");

			if ("5".equals(fromPage)){
				// attempted to go to confirmation screen
				if (regDetailsForm.getAdditionalStudyUnits().isEmpty() && "true".equals(request.getAttribute("remove"))){
					return "step3";
				}
				if (regDetailsForm.getAdditionalStudyUnits().isEmpty()){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "X1 - No additional study units selected."));
					addErrors(request, messages);
					return "step4";
				}else{
					list = regDetailsForm.getAdditionalStudyUnits();
					if (request.getParameter("CreditTotal") != null && request.getParameter("CreditTotal") != "0"){
						regDetailsForm.setCreditTotal(Integer.parseInt(request.getParameter("CreditTotal")));
		        	}
				}
			}else{
				try{
					boolean isSelected = false;
					ArrayList newList = new ArrayList();
					//log.debug("Step4 - NumberOfUnits="+regDetailsForm.getNumberOfUnits());
					for (int i = 0; i < regDetailsForm.getNumberOfUnits(); i++) {
						String selUnits = request.getParameter("selectedAdditionalStudyUnits"+i);
						//log.debug("Step4 - selectedAdditionalStudyUnits"+i+"="+selUnits);
						if (selUnits != null && !"".equals(selUnits) && !"-1".equals(selUnits)){
							isSelected = true;
							if (selUnits.indexOf("@") >= 0){
								String []  splitSUStr  = selUnits.split("@");
								selUnits = splitSUStr[0];
								//log.debug("Step4 - selUnits="+selUnits);
							}
							newList.add(selUnits);
						}
					}
					regDetailsForm.setSelectedAdditionalStudyUnits(newList);
					//log.debug("Step4 - setSelectedAdditionalStudyUnits Set");
					if (regDetailsForm.getSelectedAdditionalStudyUnits() == null || regDetailsForm.getSelectedAdditionalStudyUnits().isEmpty()){
						log.info("ADDITION step4 for studnr="+regDetailsForm.getStudentNr() + " - getSelectedAdditionalStudyUnits is empty" );
						messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "X2 - No additional study units selected."));
						addErrors(request, messages);
						return step3(mapping,form,request, response);
					}else{
						
							// Check for duplicates
							for (int i = 0; i < regDetailsForm.getSelectedAdditionalStudyUnits().size(); i++) {
								if (!"".equals(regDetailsForm.getSelectedAdditionalStudyUnits().get(i).toString().trim()) && !"-1".equals(regDetailsForm.getSelectedAdditionalStudyUnits().get(i).toString().trim())){
									for (int t = 0; t < regDetailsForm.getSelectedAdditionalStudyUnits().size(); t++) {
										if (i!=t && regDetailsForm.getSelectedAdditionalStudyUnits().get(i).toString().equalsIgnoreCase(regDetailsForm.getSelectedAdditionalStudyUnits().get(t).toString())){
											messages.add(ActionMessages.GLOBAL_MESSAGE,
												new ActionMessage("message.generalmessage", "Duplicate study unit: "+ regDetailsForm.getSelectedAdditionalStudyUnits().get(i).toString().toUpperCase()));
											addErrors(request, messages);
											return step3(mapping,form,request, response);
										}
									}
								}
							}
							// Check against already registered study units
							for (int i = 0; i < regDetailsForm.getSelectedAdditionalStudyUnits().size(); i++) {
								if (!"".equals(regDetailsForm.getSelectedAdditionalStudyUnits().get(i).toString().trim()) && !"-1".equals(regDetailsForm.getSelectedAdditionalStudyUnits().get(i).toString().trim())){
									for (int t = 0; t < regDetailsForm.getStudyUnits().size(); t++) {
										StudyUnit su = new StudyUnit();
										su = (StudyUnit) regDetailsForm.getStudyUnits().get(t);
										String selSUCode = regDetailsForm.getSelectedAdditionalStudyUnits().get(i).toString();
										if (selSUCode.indexOf("@") >= 0){
											String []  splitSUStr  = regDetailsForm.getSelectedAdditionalStudyUnits().get(i).toString().trim().split("@");
											selSUCode = splitSUStr[0];
										}
										if (selSUCode.equalsIgnoreCase(su.getCode()) && "Registered".equalsIgnoreCase(su.getStatus())){
											messages.add(ActionMessages.GLOBAL_MESSAGE,
											new ActionMessage("message.generalmessage", selSUCode.toUpperCase()+ " - Already registered"));
											addErrors(request, messages);
											return step3(mapping,form,request, response);
										}
									}
								}
							}
							// Check against supplement study units
							for (int i = 0; i < regDetailsForm.getSelectedAdditionalStudyUnits().size(); i++) {
								if (!"".equals(regDetailsForm.getSelectedAdditionalStudyUnits().get(i).toString().trim()) && !"-1".equals(regDetailsForm.getSelectedAdditionalStudyUnits().get(i).toString().trim())){
									for (int t = 0; t < regDetailsForm.getSupplementStudyUnits().size(); t++) {
										StudyUnit su = new StudyUnit();
										su = (StudyUnit) regDetailsForm.getSupplementStudyUnits().get(t);
										String selSUCode = regDetailsForm.getSelectedAdditionalStudyUnits().get(i).toString();
										if (selSUCode.indexOf("@") >= 0){
											String []  splitSUStr  = regDetailsForm.getSelectedAdditionalStudyUnits().get(i).toString().trim().split("@");
											selSUCode = splitSUStr[0];
										}
											
										if (selSUCode.equalsIgnoreCase(su.getCode())){
											messages.add(ActionMessages.GLOBAL_MESSAGE,
											new ActionMessage("message.generalmessage", selSUCode.toUpperCase()+ " - Already registered"));
											addErrors(request, messages);
											return step3(mapping,form,request, response);
										}
									}
								}
							}
							//Check if already has credit on acasun
							for (int i = 0; i < regDetailsForm.getSelectedAdditionalStudyUnits().size(); i++) {
								String selSUCode = regDetailsForm.getSelectedAdditionalStudyUnits().get(i).toString();
								if (!"".equals(selSUCode) && !"-1".equals(selSUCode)){
									if (selSUCode.indexOf("@") >= 0){
										String []  splitSUStr  = regDetailsForm.getSelectedAdditionalStudyUnits().get(i).toString().trim().split("@");
										selSUCode = splitSUStr[0];
									}
									if (db.hasCredit(regDetailsForm.getStudentNr(),selSUCode)){
										messages.add(ActionMessages.GLOBAL_MESSAGE,
										new ActionMessage("message.generalmessage", selSUCode.toUpperCase()+ " - Already credit"));
										addErrors(request, messages);
										return step3(mapping,form,request, response);
									}
								}
							}
						
	
					}
				} catch(Exception e){
					//throw e;
					log.info("ADDITION step4 for studnr="+regDetailsForm.getStudentNr() + " - getSelectedAdditionalStudyUnits Exception occurred" );
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "An unexpected error occured while processing your request. Please try again later or try another browser or contact Unisa at bugmaster@unisa.ac.za should the problem persist."));
						addErrors(request, messages);
						return step3(mapping,form,request, response);
				} 
				
				// Setup studyUnits for Type in list
				if("T".equals(regDetailsForm.getListType())){

					for (int i = 0; i < regDetailsForm.getSelectedAdditionalStudyUnits().size(); i++) {
						if(!"".equals(regDetailsForm.getSelectedAdditionalStudyUnits().get(i).toString().trim())){
							regDetailsForm.getSelectedAdditionalStudyUnits().set(i,regDetailsForm.getSelectedAdditionalStudyUnits().get(i).toString().toUpperCase());
							if(!db.isStudyUnitValid(regDetailsForm.getSelectedAdditionalStudyUnits().get(i).toString())){
								messages.add(ActionMessages.GLOBAL_MESSAGE,
										new ActionMessage("message.generalmessage", "Invalid study unit: "+ regDetailsForm.getSelectedAdditionalStudyUnits().get(i).toString().toUpperCase()));
								addErrors(request, messages);
								return step3(mapping,form,request, response);
							}
							StudyUnit su = new StudyUnit();
							su = db.getStudyUnitInfoForOpenRegPeriod(regDetailsForm.getSelectedAdditionalStudyUnits().get(i).toString(),regDetailsForm.getRegPeriodOpen1(),regDetailsForm.getRegPeriodOpen2(),regDetailsForm.getRegPeriodOpen6(),regDetailsForm.getRegPeriodOpen0());
							if (!su.isRegPeriod0()&&!su.isRegPeriod1()&&!su.isRegPeriod2()&&!su.isRegPeriod6()){
								su.setCode(regDetailsForm.getSelectedAdditionalStudyUnits().get(i).toString());
								su.setRegPeriod("closed");
								su.setNdp("N");
								su.setLanguage(db.getStudyUnitLanguage(su.getCode()));
								su = db.getStudyUnitInformation(su);
								list.add(su);
							}else{
								su.setLanguage(db.getStudyUnitLanguage(su.getCode()));
								su = db.getStudyUnitInformation(su);
								su.setNdp("N");
								list.add(su);
							}
						}
					}
				}else{
					// Setup studyUnits for rest of lists
					for (int i = 0; i < regDetailsForm.getSelectedAdditionalStudyUnits().size(); i++) {
						if(!"-1".equals(regDetailsForm.getSelectedAdditionalStudyUnits().get(i).toString())){
							String selSUCode = regDetailsForm.getSelectedAdditionalStudyUnits().get(i).toString();
							if (selSUCode.indexOf("@") >= 0){
								String []  splitSUStr  = regDetailsForm.getSelectedAdditionalStudyUnits().get(i).toString().trim().split("@");
								selSUCode = splitSUStr[0];
							}
							//log.debug("Setup studyUnits for rest of lists"+regDetailsForm.getSelectedAdditionalStudyUnits().get(i).toString().trim()+" - Split="+selSUCode);
							StudyUnit su = new StudyUnit();
							su = db.getStudyUnitInfoForOpenRegPeriod(selSUCode,regDetailsForm.getRegPeriodOpen1(),regDetailsForm.getRegPeriodOpen2(),regDetailsForm.getRegPeriodOpen6(),regDetailsForm.getRegPeriodOpen0());
							su.setLanguage(db.getStudyUnitLanguage(su.getCode()));
							su = db.getStudyUnitInformation(su);
							su.setNdp("N");
							list.add(su);
						}
					}
				}
			}
			if (list.isEmpty()){
				if("T".equals(regDetailsForm.getListType())){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "No additional study units were typed in."));
						addErrors(request, messages);
						return step3(mapping,form,request, response);
				}else{
					messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "X3 - No additional study units selected."));
					addErrors(request, messages);
					return step3(mapping,form,request, response);
				}
			}else{
				// if odl and wil information is relevant, set indicator
				if (askWilQuestion(list)){
					regDetailsForm.setWil(true);
				}else{
					regDetailsForm.setWil(false);
				}
				if (askOdlQuestion(list)){
					regDetailsForm.setOdl(true);
				}else{
					regDetailsForm.setOdl(false);
				}
				regDetailsForm.setAdditionalStudyUnits(list);
				if (request.getParameter("CreditTotal") != null && request.getParameter("CreditTotal") != "0"){
					regDetailsForm.setCreditTotal(Integer.parseInt(request.getParameter("CreditTotal")));
	        	}
			}

		} catch(Exception e){
			throw e;
		}
		for (int i = 0; i < list.size(); i++) {
			StudyUnit su = new StudyUnit();
			su = (StudyUnit) list.get(i);
			//log.debug("Code="+su.getCode()+", Desc="+su.getDesc()+", Credits="+su.getCredits()+", HEQF="+su.getHEQF()+", Language="+su.getLanguage()+", Ndp="+su.getNdp()+", OdlAnswer="+su.getOdlAnswer());
		}
		
		return "step4";
	}

private boolean askWilQuestion(ArrayList<StudyUnit> suList ){
	for (int i = 0; i < suList.size(); i++) {
		if(suList.get(i).isWil()){
			return true;
		}
	}
	
	return false;	
}

private boolean askOdlQuestion(ArrayList<StudyUnit> suList ){
	for (int i = 0; i < suList.size(); i++) {
		if(suList.get(i).isOdl()){
			return true;
		}
	}
	
	return false;	
}
	
	public ActionForward remove (
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response, int index) throws Exception {

			RegDetailsForm regDetailsForm = (RegDetailsForm) form;
			try{
				//log.info("ADDITION remove: "+((StudyUnit)(regDetailsForm.getAdditionalStudyUnits().get(index))).getCode()+" for studnr="+regDetailsForm.getStudentNr());
				String studyUnitCode = ((StudyUnit)(regDetailsForm.getAdditionalStudyUnits().get(index))).getCode();
				regDetailsForm.getAdditionalStudyUnits().remove(index);
				for (int i = 0; i < regDetailsForm.getSelectedAdditionalStudyUnits().size(); i++) {
					String selSUCode = regDetailsForm.getSelectedAdditionalStudyUnits().get(i).toString().trim();
					if (selSUCode.indexOf("@") >= 0){
						String []  splitSUStr  = selSUCode.split("@");
						if(splitSUStr[0] != null && !splitSUStr[0].isEmpty()){
							selSUCode = splitSUStr[0];
						}
					}
					if(selSUCode!=null && studyUnitCode.equalsIgnoreCase(selSUCode)){
						regDetailsForm.getSelectedAdditionalStudyUnits().remove(i);
						request.setAttribute("remove","true");
					}
				}
			} catch(Exception e){
				throw e;
			}
			String result = step4(mapping, form, request, response);
			if("step3".equalsIgnoreCase(result)){
				return mapping.findForward(step3(mapping,form,request,response));
			}else{
				return mapping.findForward(result);
			}
		}

	public ActionForward back(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		RegDetailsForm regDetailsForm = (RegDetailsForm) form;
		AdditionQueryDAO db = new AdditionQueryDAO();
		String prevPage = "";
		
		//log.info("ADDITION back - goto: " + request.getParameter("goto"));

		if ("3".equalsIgnoreCase(request.getParameter("goto"))){
			if ("1".equalsIgnoreCase(regDetailsForm.getAmendQual())){
				prevPage = step2a(mapping,form,request, response);
			} else{
				prevPage = "step1";
			}
		}else if ("2b".equalsIgnoreCase(request.getParameter("goto"))){
			if ("1".equalsIgnoreCase(regDetailsForm.getAmendQual())){
				prevPage = step2a(mapping,form,request, response);
			}else {
				prevPage = "step1";
			}
		}else if ("speciality".equalsIgnoreCase(request.getParameter("goto"))){
			prevPage = "step1";
		}else if ("2a".equalsIgnoreCase(request.getParameter("goto"))){
			prevPage = "displayContactDetails";
		}else if ("2c".equalsIgnoreCase(request.getParameter("goto"))){
			prevPage = "step1";
		}else if ("2d".equalsIgnoreCase(request.getParameter("goto"))){
			prevPage = "step1";
		}else if ("4".equalsIgnoreCase(request.getParameter("goto"))){
			prevPage = step2b(mapping,form,request, response);
		}else if ("5".equalsIgnoreCase(request.getParameter("goto"))){
			prevPage = step3(mapping,form,request, response);
		}else if ("6".equalsIgnoreCase(request.getParameter("goto"))){
			prevPage = "step4";
		}else if ("7".equalsIgnoreCase(request.getParameter("goto"))){
			prevPage = "step4";
		}else if ("home".equalsIgnoreCase(request.getParameter("goto"))){
			prevPage = "home";
		}else if ("save".equalsIgnoreCase(request.getParameter("goto"))){
			prevPage = "confirm";
		}
		else if ("1".equalsIgnoreCase(request.getParameter("goto"))){
			prevPage = "displayContactDetails";
		}
		return mapping.findForward(prevPage);
	}

	public ActionForward nextStep(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String nextPage = "";
 		if ("1".equalsIgnoreCase(request.getParameter("goto"))){
			return mapping.findForward("step1");
		}else if ("1a".equalsIgnoreCase(request.getParameter("goto"))){
			return mapping.findForward("step1");
		}else if ("2a".equalsIgnoreCase(request.getParameter("goto"))){
			 nextPage = step2a(mapping,form,request, response);
		}else if ("2b".equalsIgnoreCase(request.getParameter("goto"))){
			 nextPage = step2b(mapping,form,request, response);
		}else if ("2c".equalsIgnoreCase(request.getParameter("goto"))){
			 nextPage = step2c(mapping,form,request, response);
		}else if ("speciality".equalsIgnoreCase(request.getParameter("goto"))){
			nextPage = displaySpecList(mapping,form,request, response);
		}else if ("3".equalsIgnoreCase(request.getParameter("goto"))){
			 nextPage = step3(mapping,form,request, response);
		}else if ("4".equalsIgnoreCase(request.getParameter("goto"))){
			 nextPage = step4(mapping,form,request, response);
		}else if ("4a".equalsIgnoreCase(request.getParameter("goto"))){
			 nextPage = step4a(mapping,form,request, response);
		}else if ("5".equalsIgnoreCase(request.getParameter("goto"))){
			 nextPage = confirm(mapping,form,request, response);
		}else if ("6".equalsIgnoreCase(request.getParameter("goto"))){
			 nextPage = "additionalSuInfo";
		}else if ("step1".equalsIgnoreCase(request.getParameter("goto"))){
			 return step1(mapping,form,request, response);
		}

		return mapping.findForward(nextPage);
		}
	
	
	public ActionForward creditCardPayment(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String serverpath = ServerConfigurationService.getServerUrl();
		return new ActionForward(serverpath+"/unisa-findtool/default.do?sharedTool=unisa.creditcardpayment",true);
	}
	
	public ActionForward otherPayment(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ActionForward("http://registration.unisa.ac.za/info/download/payment/payment.pdf",true);
	}

	public ActionForward cancel(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		RegDetailsForm regDetailsForm = (RegDetailsForm) form;
		if (regDetailsForm.getValueReg0()!=null || regDetailsForm.getValueReg1()!=null || regDetailsForm.getValueReg2()!=null|| regDetailsForm.getValueReg6()!=null){
			if ("5".equalsIgnoreCase(request.getParameter("goto")) || "save".equalsIgnoreCase(request.getParameter("goto"))){
				//Due to overriding the execute method of super class on add button
				//press, cancel doesn't work
				reset(regDetailsForm);
				return mapping.findForward("exit");
			}
		}

		reset(regDetailsForm);
		return mapping.findForward("cancel");
		
	}

	public String confirm(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ActionMessages messages = new ActionMessages();
		RegDetailsForm regDetailsForm = (RegDetailsForm) form;
		AdditionQueryDAO dao = new AdditionQueryDAO();

		log.info("ADDITION confirm for studnr="+regDetailsForm.getStudentNr());


		if (regDetailsForm.getAdditionalStudyUnits().size()==0){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "X5 - No additional study units selected."));
				addErrors(request, messages);
				return "step4";
		}
		if("T".equals(regDetailsForm.getListType())){
			for (int i = 0; i < regDetailsForm.getAdditionalStudyUnits().size(); i++) {
				StudyUnit su = new StudyUnit();
				su = (StudyUnit) regDetailsForm.getAdditionalStudyUnits(i);
				if ("closed".equalsIgnoreCase(su.getRegPeriod())){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Remove all study units with an invalid registration period before continuing."));
					addErrors(request, messages);
					return "step4";
				}
			}
		}
		if (regDetailsForm.getDeliveryType()==null || "".equals(regDetailsForm.getDeliveryType())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please indicate how you would like your study material delivered."));
				addErrors(request, messages);
				return "step4";
		}else if ("O".equalsIgnoreCase(regDetailsForm.getDeliveryType())){
			// check for courier address
			if(!dao.hasValidAddress(regDetailsForm.getStudentNr(), "Courier")){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "We do not have a courier address for you. Please go to Biographical Details or click on &lsquo;Change Address/Contact Details&rsquo; to update your courier address before continuing."));
					addErrors(request, messages);
					return "step4";
			}
			// check if courier address valid - only for South African "1015" addresses
			String country = "";
			country = dao.getCountry(regDetailsForm.getStudentNr());
			if ("1015".equalsIgnoreCase(country)){
				regDetailsForm.setCourierSubCodeValid(false);
				String courierBoxOrStreet = "S";
		    	for (int i = 0; i < regDetailsForm.getCourier().size(); i++) {
		    		if (i == 0){
						courierBoxOrStreet = getBoxOrStreet(regDetailsForm.getCourier().get(i).toString().trim());
						if ("B".equalsIgnoreCase(courierBoxOrStreet)){
							//log.debug("AdditionsAction - confirm - Student Courier Address contains P O BOX etc, not Street=" + courierBoxOrStreet);
							messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage", "The courier address may not be a postal address. Please go to Biographical Details or click on &lsquo;Change Address/Contact Details&rsquo; to update your courier address before continuing."));
							addErrors(request, messages);
							return "step4";
						}
					}
					if (!regDetailsForm.isCourierSubCodeValid()) {
						regDetailsForm.setCourierSubCodeValid(dao.isAddressSubCodeValid(regDetailsForm.getStudentNr(), regDetailsForm.getCourier().get(i).toString().trim(),regDetailsForm.getCourierAddress().getPostalCode() ,"S", "Courier"));
					} 
		    	}
		    	if (regDetailsForm.isCourierSubCodeValid()){
		    		//log.debug("AdditionsAction - confirm - Student has Valid Courier Address: " + regDetailsForm.isCourierSubCodeValid());
		    	}else{
		    		//log.debug("AdditionsAction - confirm - Student Courier Address suburb does not match postal code: " + regDetailsForm.isCourierSubCodeValid());
					messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "You have not entered a courier suburb that corresponds with the postal code or the suburb has been entered incorrectly. Please go to Biographical Details or click on &lsquo;Change Address/Contact Details&rsquo; to update your courier address before continuing."));
					addErrors(request, messages);
					return "step4";
				}
			}
		}

		/*
		 * check for exam clashes after semester has been selected
		 * */
		// 1. Read exam dates for each study unit
		AdditionQueryDAO db = new AdditionQueryDAO();
		for (int i = 0; i < regDetailsForm.getAdditionalStudyUnits().size(); i++) {
			StudyUnit su = new StudyUnit();
			su = (StudyUnit) regDetailsForm.getAdditionalStudyUnits(i);
			regDetailsForm.getAdditionalStudyUnits(i).setExamDates(db.getExamDatesForModule(su.getCode(),su.getRegPeriod()));
		}
		// 2. Read exam dates for already registered study units
		for (int i = 0; i < regDetailsForm.getStudyUnits().size(); i++) {
			StudyUnit su = new StudyUnit();
			su = (StudyUnit) regDetailsForm.getStudyUnits().get(i);
			su.setExamDates(db.getExamDatesForModule(su.getCode(),su.getRegPeriod()));
			regDetailsForm.getStudyUnits().set(i,su);
		}
		// 3. check for exam clashes
		for (int i = 0; i < regDetailsForm.getAdditionalStudyUnits().size(); i++) {
			StudyUnit su = new StudyUnit();
			su = (StudyUnit) regDetailsForm.getAdditionalStudyUnits().get(i);
			String errMsg = testForExamClash(su,regDetailsForm.getAdditionalStudyUnits(), regDetailsForm.getStudyUnits());
				if (!"".equals(errMsg)){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",errMsg));
					addErrors(request, messages);
					break;
				}
			}
		if(messages != null && messages.size()>0){
			return "step4";
		}

		// if qual code and spec code changes display extra message on screen
		if (regDetailsForm.getQual().getQualCode()!= null && regDetailsForm.getNewQual().getQualCode()!= null){
			if(!regDetailsForm.getQual().getQualCode().equalsIgnoreCase(regDetailsForm.getNewQual().getQualCode())){
				request.setAttribute("amendqual","true");
			}
		}

		/** 2014 Edmund - Added check for SAPO message **/
		//log.info("ADDITION confirm for studnr="+regDetailsForm.getStudentNr() + ", deliveryType=" + regDetailsForm.getDeliveryType());
		if ("P".equalsIgnoreCase(regDetailsForm.getDeliveryType())){
			return "step4a";
		}else{
			return "confirm";
		}
	}

	public String step4a (
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		RegDetailsForm regDetailsForm = (RegDetailsForm) form;
		/** 2014 Edmund - Added check for SAPO message **/
		//log.info("ADDITION step4a for studnr="+regDetailsForm.getStudentNr() + ", deliveryType=" + regDetailsForm.getDeliveryType());

		return "confirm";
	}
	
	public ActionForward save(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		//log.debug("AdditionsAction - Save - Start");
		
		RegDetailsForm regDetailsForm = (RegDetailsForm) form;
		AdditionQueryDAO dao = new AdditionQueryDAO();
		RegQueryDAO db = new RegQueryDAO();
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
		toolManager = (ToolManager) ComponentManager.get(ToolManager.class);
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);

		if (regDetailsForm.getStudentNr()==null || "".equals(regDetailsForm.getStudentNr())){
			/* not suppose to happen, except if session expired? */
			log.error("ADDITION save error, studnr empty");
			return (mapping.findForward("step1"));
		}
		UsageSession usageSession = usageSessionService.getSession();
			//UsageSessionService.startSession(regDetailsForm.getStudentNr(),request.getRemoteAddr(),request.getHeader("user-agent"));

		log.info("ADDITION save for studnr="+regDetailsForm.getStudentNr());

		/* Can not submit more than once in 30 day period

		if (!dao.canSubmitAddition(regDetailsForm.getStudentNr())){
			log.info("ADDITION one submission only for studnr="+regDetailsForm.getStudentNr());
			return (mapping.findForward("oneSubmission"));
		}*/

		if (regDetailsForm.getAdditionalStudyUnits().isEmpty()){
			/* not suppose to happen */
			log.error("ADDITION save error, no s-units for studnr="+regDetailsForm.getStudentNr());
			return (mapping.findForward("step1"));
		}

	    /* Update stuaca spec code on database */
		//log.debug("AdditionsAction - Save - AmendQual="+regDetailsForm.getAmendQual());
		if(regDetailsForm.getNewQual().getQualCode()!= null && regDetailsForm.getQual().getQualCode() != null && !regDetailsForm.getQual().getQualCode().equals(regDetailsForm.getNewQual().getQualCode())){
			RegQueryDAO regDao = new RegQueryDAO();
			regDao.writeAuditTrail(regDetailsForm.getStudentNr(), "IA", "Qualupdate");    	  
			//dao.updateAcadRecordQual(regDetailsForm.getStudentNr(),regDetailsForm.getQual().getQualCode(), regDetailsForm.getQual().getSpecCode(), regDetailsForm.getNewQual().getQualCode(),regDetailsForm.getNewQual().getSpecCode(), regDetailsForm.getAcadYear());
		}
			
		if(regDetailsForm.getNewQual().getSpecCode() != null && regDetailsForm.getQual().getSpecCode() != null && !regDetailsForm.getQual().getSpecCode().trim().equals(regDetailsForm.getNewQual().getSpecCode().trim())){
			RegQueryDAO regDao = new RegQueryDAO();
			regDao.writeAuditTrail(regDetailsForm.getStudentNr(), "IA", "Specupdate");
			//dao.updateAcadRecordSpec(regDetailsForm.getStudentNr(),regDetailsForm.getNewQual().getQualCode(),regDetailsForm.getNewQual().getSpecCode());
		}
	      
		if(request.getParameter("button.studentaffairs") != null || request.getParameter("studentaffairs") != null){
			regDetailsForm.setSelfhelp(false);
			regDetailsForm.setSelfHelpReason("ADA");
		}
		/* 1.Update studyunit records on database solsun, stusun
		 * 2. Write audit trail*/
		updateDatabaseStudyUnits(regDetailsForm);

		// Set submission timestamp
		String date = (new java.text.SimpleDateFormat("EEEEE dd MMMMM yyyy hh:mm:ss").format(new java.util.Date()).toString());
		regDetailsForm.setSubmissionTimeStamp(date);

		/* Write Workflow Queue - STUREGQ */
		updateWorkflowQueue(regDetailsForm);
		
		/* Write workflow */
		writeWorkflow(regDetailsForm, request);
		
		/* Write sakai event */
		eventTrackingService.post(
				eventTrackingService.newEvent(EventTrackingTypes.EVENT_REGDETAILS_ADDITION,toolManager.getCurrentPlacement().getContext()+"/"+regDetailsForm.getStudentNr(), false),usageSession);
		/* Write flag 99 */
		db.writeStudentFlag(regDetailsForm.getStudentNr(),"99");

		/* setup quotation input*/
		StudyQuotation studyQuotation = new StudyQuotation();
		studyQuotation = setupQuotation(regDetailsForm);
		/* calculate quotation */
		StudyQuotationService studyQuotationService = new StudyQuotationService();
		studyQuotationService.setStudyQuotation(studyQuotation);
		int result = studyQuotationService.calculateStudyQuotation();

		if (result == StudyQuotationService.QUAL_CODE_MISSING) {
			/* do not show quotation */
			regDetailsForm.setShowQuote(false);
		} else if (result == StudyQuotationService.COOLGEN_ERROR) {
			/* do not show quotation */
			regDetailsForm.setShowQuote(false);
		}else {
			/* Add quotation to request */
			request.setAttribute("quote",studyQuotation);
			/* Send confirmation email */
			sendConfirmationEmail(regDetailsForm.getStudentNr(),regDetailsForm.getEmail(), studyQuotation, regDetailsForm.getSubmissionTimeStamp());
			regDetailsForm.setShowQuote(true);
		}

		reset(regDetailsForm);
		return mapping.findForward("saved");
	}

	/**
	 * This method sets the input quotation variables for the quotation proxy
	 *
	 * @param regDetailsForm       The form associated with the action
	 * @throws Exception
	*/

	private StudyQuotation setupQuotation(RegDetailsForm regDetailsForm) throws Exception{
		/* setup Quotation input */
		StudyQuotation studyQuotation = new StudyQuotation();
		studyQuotation.setAcademicYear(Short.parseShort(regDetailsForm.getAcadYear()));
		//Read country from db
		String country = "1015";
		AdditionQueryDAO dao = new AdditionQueryDAO();
		country = dao.getCountry(regDetailsForm.getStudentNr());
		if ("".equalsIgnoreCase(country)){
			country="1015";
		}
		studyQuotation.setCountryCode(country);
		studyQuotation.setLibraryCard("N");
		studyQuotation.setMatricExemption("N");
		studyQuotation.setQualificationCode(regDetailsForm.getQual().getQualCode());
		studyQuotation.setQualification(regDetailsForm.getQual().getQualCode());
		studyQuotation.setStudyCode1("");
		studyQuotation.setStudyCode2("");
		studyQuotation.setStudyCode3("");
		studyQuotation.setStudyCode4("");
		studyQuotation.setStudyCode5("");
		studyQuotation.setStudyCode6("");
		studyQuotation.setStudyCode7("");
		studyQuotation.setStudyCode8("");
		studyQuotation.setStudyCode9("");
		studyQuotation.setStudyCode10("");
		studyQuotation.setStudyCode11("");
		studyQuotation.setStudyCode12("");
		studyQuotation.setStudyCode13("");
		studyQuotation.setStudyCode14("");
		studyQuotation.setStudyCode15("");
		studyQuotation.setStudyCode16("");
		studyQuotation.setStudyCode17("");
		studyQuotation.setStudyCode18("");
		for (int i = 0; i < regDetailsForm.getAdditionalStudyUnits().size(); i++) {
			StudyUnit sUnit =  (StudyUnit)regDetailsForm.getAdditionalStudyUnits().get(i);
			if (i==0){
				studyQuotation.setStudyCode1(sUnit.getCode());
			}else if (i==1){
				studyQuotation.setStudyCode2(sUnit.getCode());
			}else if (i==2){
				studyQuotation.setStudyCode3(sUnit.getCode());
			}else if (i==3){
				studyQuotation.setStudyCode4(sUnit.getCode());
			}else if (i==4){
				studyQuotation.setStudyCode5(sUnit.getCode());
			}else if (i==5){
				studyQuotation.setStudyCode6(sUnit.getCode());
			}else if (i==6){
				studyQuotation.setStudyCode7(sUnit.getCode());
			}else if (i==7){
				studyQuotation.setStudyCode8(sUnit.getCode());
			}else if (i==8){
				studyQuotation.setStudyCode9(sUnit.getCode());
			}else if (i==9){
				studyQuotation.setStudyCode10(sUnit.getCode());
			}else if (i==10){
				studyQuotation.setStudyCode11(sUnit.getCode());
			}else if (i==11){
				studyQuotation.setStudyCode12(sUnit.getCode());
			}else if (i==12){
				studyQuotation.setStudyCode13(sUnit.getCode());
			}else if (i==13){
				studyQuotation.setStudyCode14(sUnit.getCode());
			}else if (i==14){
				studyQuotation.setStudyCode15(sUnit.getCode());
			}else if (i==15){
				studyQuotation.setStudyCode16(sUnit.getCode());
			}else if (i==16){
				studyQuotation.setStudyCode17(sUnit.getCode());
			}else if (i==17){
				studyQuotation.setStudyCode18(sUnit.getCode());
			}
		}
		return studyQuotation;
	}

	private void reset(RegDetailsForm regDetailsForm){
		regDetailsForm.setAmendQual("0");
		regDetailsForm.setSelectedQual("");
		regDetailsForm.setNewQual(new Qualification());
		regDetailsForm.setListType("S");
		regDetailsForm.setOrderType("C");
		regDetailsForm.setNumberOfUnits(0);
		regDetailsForm.setCompleteQual("N");
		regDetailsForm.setAdditionalStudyUnits(new ArrayList());
		regDetailsForm.setListStudyUnits(new ArrayList());
		regDetailsForm.setSelectedSpec("");
		regDetailsForm.setValueReg0("");
		regDetailsForm.setValueReg1("");
		regDetailsForm.setValueReg2("");
		regDetailsForm.setValueReg6("");
		regDetailsForm.setSubmissionTimeStamp("");
		regDetailsForm.setSelfhelp(true);
		regDetailsForm.setSelfHelpReason("");
		regDetailsForm.setDeliveryType("");
		regDetailsForm.setQualStatus("");
		regDetailsForm.setSelfhelpErrorMsg("");
		regDetailsForm.setOdl(false);
		regDetailsForm.setWil(false);
		regDetailsForm.setPhasedOutDeclare("");
	}


	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		/** Edmund Enumerate through all parameters received**/
	    //getAllRequestParamaters(request, response);

		//	if (request.getParameter("action.add0") != null) return add(mapping, form, request, response, 0);
		//	if (request.getParameter("action.add1") != null) return add(mapping, form, request, response, 1);
		//	if (request.getParameter("action.add2") != null) return add(mapping, form, request, response, 2);
		//	if (request.getParameter("action.add6") != null) return add(mapping, form, request, response, 6);

		Iterator i = request.getParameterMap().keySet().iterator();

		while (i.hasNext()) {
			String name = (String) i.next();
			if (name.startsWith("action.remove[")) {
				int startpos = name.indexOf("[");
				int endpos = name.indexOf("]");
				String number = name.substring(startpos + 1, endpos);
				int index = Integer.parseInt(number);

				return remove(mapping, form, request, response, index);
			}
		}

		return super.execute(mapping, form, request, response);
	}

	/**
	 * This method writes the addition workflow file to the path specified
	 * in the following file:
	 * sakai/jakarta-tomcat-5.5.9/sakai/sakai.properties
	 *
	 * @param regDetailsForm       The form associated with the action
	*/
	private void writeWorkflow(RegDetailsForm regDetailsForm, HttpServletRequest request) throws Exception{

		String time = (new java.text.SimpleDateFormat("hhmmss").format(new java.util.Date()).toString());
		/* add date and time to request to display on page */
		request.setAttribute("stamp",regDetailsForm.getSubmissionTimeStamp());

		String type = "L"; /* L = local F=Foreign */
		/* set local or foreign */
		RegQueryDAO db = new RegQueryDAO();
		if (!db.isStudentLocal(regDetailsForm.getStudentNr())){
			type = "F";
		}

		/* write to file */
		WorkflowFile file = new WorkflowFile("addunits_"+ type + "_"+ regDetailsForm.getStudentNr()+"_"+ time);
		file.add(" The following request was received through myUnisa: \r\n");
		file.add(" Type of request: Application for Additional Study Units \r\n");
		file.add(" Request received on " + regDetailsForm.getSubmissionTimeStamp() + "\r\n\r\n");
		if (!"".equals(regDetailsForm.getSelfhelpErrorMsg())){// attempted a F139 but returned error
			file.add(" Error from F139: " + regDetailsForm.getSelfhelpErrorMsg()+ "\r\n\r\n");
		}
		file.add(" -------------------------------------------------------------------- \r\n");
		file.add(" Student number          = " + regDetailsForm.getStudentNr() + "\r\n");
		file.add(" Student name            = " + regDetailsForm.getStudentName() + "\r\n");
		file.add(" -------------------------------------------------------------------- \r\n");
		file.add(" Qualification code      = " + regDetailsForm.getQual().getQualCode()+ " "+ regDetailsForm.getQual().getQualDesc() + "\r\n");
		file.add(" Speciality code         = " + regDetailsForm.getQual().getSpecCode()+ " "+ regDetailsForm.getQual().getSpecDesc() + "\r\n");
		if(regDetailsForm.getNewQual().getQualCode()!= null && regDetailsForm.getQual().getQualCode() != null && !regDetailsForm.getQual().getQualCode().equals(regDetailsForm.getNewQual().getQualCode())){
			file.add(" -------------------------------------------------------------------- \r\n");
			file.add(" Amended qualification   = " + regDetailsForm.getNewQual().getQualCode()+ " "+ regDetailsForm.getNewQual().getQualDesc() + "\r\n");
		}	
		if(regDetailsForm.getNewQual().getSpecCode() != null && regDetailsForm.getQual().getSpecCode() != null && !regDetailsForm.getQual().getSpecCode().trim().equals(regDetailsForm.getNewQual().getSpecCode().trim())){
			file.add(" -------------------------------------------------------------------- \r\n");
			file.add(" Amended speciality      = " + regDetailsForm.getNewQual().getSpecCode()+ " "+ regDetailsForm.getNewQual().getSpecDesc() + "\r\n");
		}
		if ("true".equalsIgnoreCase(regDetailsForm.getPhasedOutDeclare())){
			file.add(" -------------------------------------------------------------------- \r\n");
			file.add(" Phased Out declared     = Please note that you are registering for a qualification that is phasing out. \r\n");
			file.add(" 							 Ensure that you will be able to complete this qualification before the phase-out date. \r\n");
			file.add(" 							 If not, take note that you could forfeit credits if you change to another qualification. \r\n");
			file.add(" 							 Unisa will not grant any extension on phased-out qualifications. \r\n");
		}
	
		//Address Details
		//Postal address
		file.add(" -------------------------------------------------------------------- \r\n");
		Vector postal = regDetailsForm.getPostal();
				
		if (postal != null && !postal.isEmpty())
		{
			file.add(" Postal Address          = " + postal.get(0)+ "\r\n");
		}
		
		for (int i = 1; i < postal.size(); i++)
		{
			file.add("                         = " + postal.get(i)+ "\r\n");
		}
		file.add(" Postal Code             = " + regDetailsForm.getPostalAddress().getPostalCode()+ "\r\n");
		
		//Physical address
		Vector physical = regDetailsForm.getPhysical();
		
		if (physical != null && !physical.isEmpty())
		{
			file.add(" Physical Address        = " + physical.get(0)+ "\r\n");
		}
		
		for (int i = 1; i < physical.size(); i++)
		{
			file.add("                         = " + physical.get(i)+ "\r\n");
		}
		file.add(" Postal Code             = " + regDetailsForm.getPhysicalAddress().getPostalCode()+ "\r\n");
		
		//Courier address
		Vector courier = regDetailsForm.getCourier();
		
		if (courier != null && !courier.isEmpty())
		{
			file.add(" Courier Address         = " + courier.get(0)+ "\r\n");
		}
		
		for (int i = 1; i < courier.size(); i++)
		{
			file.add("                         = " + courier.get(i)+ "\r\n");
		}
		file.add(" Postal Code             = " + regDetailsForm.getCourierAddress().getPostalCode()+ "\r\n");
				
		//Contact Details	
		file.add(" -------------------------------------------------------------------- \r\n");
		file.add(" Contact Number          = " + regDetailsForm.getContactNumber()+ "\r\n");
		file.add(" Home Telephone Number   = " + regDetailsForm.getHomeNumber()+ "\r\n");
		file.add(" Work Telephone Number   = " + regDetailsForm.getWorkNumber()+ "\r\n");
		file.add(" Cell Number             = " + regDetailsForm.getCellNumber()+ "\r\n");
		file.add(" Fax Number              = " + regDetailsForm.getFaxNumber()+ "\r\n");
		
		file.add(" Email address:          = " + regDetailsForm.getEmail() + "\r\n");
		file.add(" -------------------------------------------------------------------- \r\n");
		file.add(" Add the following study unit(s) to the students current registration: \r\n\r\n");
		file.add(" Study unit\t Period\t Language\t NDP \r\n");
		log.info("ADDITION uniflow file for studnr="+regDetailsForm.getStudentNr());
		for (int i = 0; i < regDetailsForm.getAdditionalStudyUnits().size(); i++) {
			StudyUnit su = new StudyUnit();
			String lang = "";
			su = (StudyUnit) regDetailsForm.getAdditionalStudyUnits().get(i);

			/* Remove this if statement - only temporary */
			//log.info("ADDITION " + su.toString() + " i=" + i);
			if (su.getLanguage()== null || "".equals(su.getLanguage())){
				//log.error("ADDITION lang empty " +su.toString());
				su.setLanguage("E");
			}

			if ("A".equalsIgnoreCase(su.getLanguage().substring(0,1))){
				lang = "Afr";
			}else {
				lang = "Eng";
			}
			file.add(" "+su.getCode() +"\t " + su.getRegPeriod()+"\t " + lang +"\t\t " + su.getNdp()+"\r\n");
		}
		// print wil study units
		file.add(" -------------------------------------------------------------------- \r\n");
		boolean printHeading = true;
		for (int i = 0; i < regDetailsForm.getAdditionalStudyUnits().size(); i++) {
			StudyUnit su = (StudyUnit) regDetailsForm.getAdditionalStudyUnits().get(i);
			if (su.isWil()){
				if(printHeading){
				  printHeading= false;
				  file.add("\r\n WIL study unit(s) \r\n");
				}
				String wilAnswer="";
				if("Y".equalsIgnoreCase(su.getWilAnswer())) wilAnswer = "Yes";
				else if("N".equalsIgnoreCase(su.getWilAnswer())) wilAnswer = "No";
				else if("U".equalsIgnoreCase(su.getWilAnswer())) wilAnswer = "Don't know";
			    file.add(" "+su.getCode() +"\t " + wilAnswer+"\r\n");
			}
		}
		//		 print odl study units
		file.add(" -------------------------------------------------------------------- \r\n");
		printHeading = true;
		for (int i = 0; i < regDetailsForm.getAdditionalStudyUnits().size(); i++) {
			StudyUnit su = (StudyUnit) regDetailsForm.getAdditionalStudyUnits().get(i);
			if (su.isOdl()){
				if(printHeading){
				  printHeading= false;
				  file.add("\r\n ODL study unit(s) \r\n");
				}
				String odlAnswer="";
				if("F".equalsIgnoreCase(su.getOdlAnswer())) odlAnswer = "Face to face";
				else if("O".equalsIgnoreCase(su.getOdlAnswer())) odlAnswer = "Online";
				else if("N".equalsIgnoreCase(su.getOdlAnswer())) odlAnswer = "I do not need support";
			    file.add(" "+su.getCode() +"\t " + odlAnswer+"\r\n");
			}
		}
		file.add(" -------------------------------------------------------------------- \r\n");
		if ("Y".equalsIgnoreCase(regDetailsForm.getCompleteQual())){
			file.add("\r\n Can complete            = Yes\r\n");
		}else{
			file.add("\r\n Can complete            = No\r\n");
		}
		file.add(" -------------------------------------------------------------------- \r\n");
        if ("O".equalsIgnoreCase(regDetailsForm.getDeliveryType())){
        	file.add(" Study material delivery = Courier\r\n");            
        }else if ("P".equalsIgnoreCase(regDetailsForm.getDeliveryType())){
        	file.add(" Study material delivery = Postal\r\n");
        }else if ("C".equalsIgnoreCase(regDetailsForm.getDeliveryType())){
        	file.add(" Study material delivery = Counter @ Sunnyside\r\n");
        }else if ("N".equalsIgnoreCase(regDetailsForm.getDeliveryType())){
        	file.add(" Study material delivery = Counter @ Florida\r\n");
        }else if ("D".equalsIgnoreCase(regDetailsForm.getDeliveryType())){
        	file.add(" Study material delivery = Counter @ Durban\r\n");
        }else if ("M".equalsIgnoreCase(regDetailsForm.getDeliveryType())){
        	file.add(" Study material delivery = Counter @ Pietermaritzburg\r\n");
        }
        file.add(" ==================================================================== \r\n");
		file.close();

	}

	/**
	 * This method sends an addition confirmation e-mail to the student.
	 *
	 * @param studentNr     			The student's number
	 * @param studentEmailAddress	    The student's email address
	 * @param studyQuotation			The populated StudyQuotation object
	 * @param submissionTimestamp		The date and time of the addition request
	 */

	public void sendConfirmationEmail(String studentNr,String studentEmailAddress, StudyQuotation studyQuotation, String submissionTimestamp) {

		try {
			NumberFormat formatter = new DecimalFormat("0.00");
			String tmpEmailFrom = ServerConfigurationService.getString("noReplyEmailFrom");
			InternetAddress toEmail = new InternetAddress(studentEmailAddress);
			InternetAddress iaTo[] = new InternetAddress[1];
			iaTo[0] = toEmail;
			InternetAddress iaHeaderTo[] = new InternetAddress[1];
			iaHeaderTo[0] = toEmail;
			InternetAddress iaReplyTo[] = new InternetAddress[1];
			iaReplyTo[0] = new InternetAddress(tmpEmailFrom);
			emailService = (EmailService) ComponentManager.get(EmailService.class);

			/* send confirmation email to student */
			String subject = "Registration of Additional Study Units "+studentNr;
			String body1 = "<html> "+
			"<body> "+
			"Dear student ("+studentNr+"),  <br/><br/><strong>"+
			"Your application for registration of additional study units was successfully submitted.  <br/><br/> "+
			"Date and time of submission:&nbsp; "+submissionTimestamp+"<br/><br/>"+
			"NB: If you re-submit this application, any former application will be replaced in total. "+
			"Once your application has been finalised (payment received), any amendments thereto may have financial implications.</strong> <br/><br/>"+
			"<strong>Study fees and payment</strong><br/><br/>"+
			"<strong>Study fees</strong></strong><br/><br/>";
			body1=body1 +"<strong>IMPORTANT:</strong>&nbsp;</br>"+
			"Your application will only be processed on receipt of the prescribed study fees before the appropriate "+
			"<a HREF='http://www.unisa.ac.za/default.asp?cmd=ViewContent&ContentID=17231' onClick=\"window.open('http://www.unisa.ac.za/default.asp?cmd=ViewContent&ContentID=17231', 'nextPage', 'height=640,width=630,directories=no,scrollbars=yes,resizable=yes,menubar=no'); return false;\" target=\"_blank\">"+
			"closing date</a> for registration.<br/><br/>";

			String body2 ="<table><tr><td colspan='2'><strong>Study fees for additional study units:</strong></td></tr><tr><td colspan='2'>&nbsp;</td></tr>";
			Vector studyUnits = new Vector();
			studyUnits = studyQuotation.getStudyUnits();
			for (int i = 0; i < studyUnits.size() ; i++) {
				za.ac.unisa.lms.studyquotation.StudyUnit studyUnit = new za.ac.unisa.lms.studyquotation.StudyUnit();
				studyUnit = (za.ac.unisa.lms.studyquotation.StudyUnit) studyUnits.get(i);
				body2 = body2 +"<tr><td>"+ studyUnit.getStudyUnitcode()+"&nbsp;-&nbsp;"+ studyUnit.getDescription()+"</td>";
				String stringFee = formatter.format(studyUnit.getFee()) ;
				body2 = body2 +"<td align='right'>R&nbsp;"+ stringFee+"</td></tr>";
			}

			String stringAmount = "";

			// Prescribed books
			if (studyQuotation.getPrescribedBooks() > 0){
				body2 = body2 +"<tr><td>Prescribed text books</td>";
			    stringAmount = formatter.format(studyQuotation.getPrescribedBooks());
				body2 = body2 +"<td align='right'>R&nbsp;"+ stringAmount +"</td></tr>";
			}
			// Total amount
			body2 = body2 +"<tr><td><br/></td><td align='right'>-----------</td></tr>";
			body2 = body2 +"<tr><td align='right'><strong>Total amount&nbsp;</strong></td>";
		    stringAmount = formatter.format(studyQuotation.getTotalFee());
		    body2 = body2 +"<td align='right'><strong>R&nbsp;"+ stringAmount +"</strong></td></tr>";

			// Payment due
			body2 = body2 +"<tr><td><br/></td><td align='right'>-----------</td></tr>";
			body2 = body2 +"<tr><td align='right'><strong>Minimum amount due&nbsp;</strong></td>";
		    stringAmount = formatter.format(studyQuotation.getPaymentDue());
			body2 = body2 +"<td align='right'><strong>R&nbsp;"+ stringAmount+"</strong></td></tr>";
			body2 = body2 +"<tr><td><br/></td><td align='right'>-----------</td></tr>";

			body2 = body2 +"</table>";

			// Last paragraph
			body2 = body2 +"<p><strong>Payment</strong><br/><br/>";
			body2 = body2 +"For credit card payments, go to Financial Details and select 'Request credit card payments'.<br/>"+
			"For general information regarding payment, "+
			"<a HREF='http://www.unisa.ac.za/default.asp?cmd=ViewContent&ContentID=17193' onClick=\"window.open('http://www.unisa.ac.za/default.asp?cmd=ViewContent&ContentID=17193', 'nextPage', 'height=640,width=630,directories=no,scrollbars=yes,resizable=yes,menubar=no'); return false;\" target=\"_blank\">"+
			"<u>Click here</u></a>.</p>";

			String body3 = "</body>"+
					   "</html>";

			List contentList = new ArrayList();
			contentList.add("Content-Type: text/html");

			body1 = body1 + body2 + body3;
			try{
				emailService.sendMail(iaReplyTo[0],iaTo,subject,body1,iaHeaderTo,iaReplyTo,contentList);
				//log.info("sending mail from "+iaReplyTo[0]+" to "+toEmail.toString());
			}catch (Exception e){
				// Don't fail additions if email could not be send
        		log.error("AdditionsAction : send of additions email failed: "+e+" "+e.getMessage());
        	}

		} catch (Exception e) {
			log.error("Unhandled exception: "+e+" "+e.getMessage());
			e.printStackTrace();
		}
	}

	private void updateDatabaseStudyUnits(RegDetailsForm regDetailsForm){
		/* Update solsun *
		 * 1. Delete any solsun + write audit trail IA
		 * 2. Insert solsun
		 * 3. Delete TP stusun + write audit trail IA
		 */
		boolean resultTP;
		boolean resultSolsun;
		String changeValue ="";
		AdditionQueryDAO dao = new AdditionQueryDAO();

		resultTP = dao.deleteTPStusun(regDetailsForm.getStudentNr());
		resultSolsun = dao.deleteSolSun(regDetailsForm.getStudentNr());
		dao.writeSolSun(regDetailsForm.getStudentNr(),regDetailsForm.getAdditionalStudyUnits());

		/* write audit trail */
		if (resultTP && resultSolsun){
			changeValue = "TPSolDel";
		}else if (resultTP){
			changeValue = "TPDelete";
		}else if (resultSolsun){
			changeValue = "SolsunDel";
		}else{
			changeValue = "";
		}
		RegQueryDAO regDao = new RegQueryDAO();
		regDao.writeAuditTrail(regDetailsForm.getStudentNr(),"IA",changeValue);
	}
	
	private void updateWorkflowQueue(RegDetailsForm regDetailsForm){
		/* Update STUREGQ */
		
		AdditionQueryDAO dao = new AdditionQueryDAO();

		/* Write New Registration Workflow Queue - STUREGQ */
		int prevSeq = dao.getQueue(regDetailsForm.getStudentNr(), regDetailsForm.getAcadYear(), regDetailsForm.getAcadPeriod());
		prevSeq = prevSeq + 1;
		
		for (int i = 0; i < regDetailsForm.getAdditionalStudyUnits().size(); i++) {
			StudyUnit su = new StudyUnit();
			su = (StudyUnit) regDetailsForm.getAdditionalStudyUnits().get(i);
			if (su.getCode() != null && !"".equals(su.getCode())) {
				dao.writeQueue(regDetailsForm.getStudentNr(), regDetailsForm.getAcadYear(), regDetailsForm.getAcadPeriod(), su.getCode(), regDetailsForm.getQual().getQualCode(), regDetailsForm.getSelfHelpReason(), prevSeq);
			}
		}
	}

	/**
	 * This method submits the additions via student system function 139, Sruaf01c
	 *
	 * @throws Exception
	*/

	public ActionForward submitAdditions(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		//log.debug("AdditionsAction - submitAdditions - Start");
		
		RegDetailsForm regDetailsForm = (RegDetailsForm) form; 
		AdditionQueryDAO dao = new AdditionQueryDAO();
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
		toolManager = (ToolManager) ComponentManager.get(ToolManager.class);
		
		//		 check wil and odl if required
		if(regDetailsForm.isOdl()|| regDetailsForm.isWil()){
			for (int i = 0; i < regDetailsForm.getAdditionalStudyUnits().size(); i++) {
				if ("E".equalsIgnoreCase(regDetailsForm.getAdditionalStudyUnits(i).getWilAnswer())||"E".equalsIgnoreCase(regDetailsForm.getAdditionalStudyUnits(i).getOdlAnswer())){
					ActionMessages messages = new ActionMessages();
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage","Please select an option from the list."));
						addErrors(request, messages);
						return (mapping.findForward("additionalSuInfo"));
				}
			}
			
		}
		
		//log.debug("AdditionsAction - Selfhelp - Test 1 - isSelfhelp="+regDetailsForm.isSelfhelp());
		
		// does Qaul & Spec combination allow selfhelp?
		String specCode = " ";
		if(regDetailsForm.getNewQual().getSpecCode() != null){
			if(!"".equals(regDetailsForm.getNewQual().getSpecCode().trim())){
				specCode = regDetailsForm.getNewQual().getSpecCode();
			}
		}
		//Check Qualification and Specialisation Selfhelp Flag
		boolean shResult = dao.canGoToSelfhelp(regDetailsForm.getNewQual().getQualCode(), specCode);
		//log.debug("AdditionsAction - Selfhelp - Test 2 - Testing Qualification for Selfhelp Flag - NewQual="+regDetailsForm.getNewQual().getQualCode()+" - NewSpec="+specCode+" - shResult="+shResult);

		if (!shResult){
			regDetailsForm.setSelfhelp(false);
			regDetailsForm.setSelfHelpReason("ADN");
		}
		
		if (!regDetailsForm.isSelfhelp()){
			//log.debug("AdditionsAction - Selfhelp - Test 3 - Selfhelp False? - Goto Workflow - SelfHelp="+regDetailsForm.isSelfhelp()+", SelfHelpReason="+regDetailsForm.getSelfHelpReason());
			return save(mapping, form, request, response);
		}

		UsageSession usageSession = usageSessionService.getSession();

		if (regDetailsForm.getStudentNr()==null || "".equals(regDetailsForm.getStudentNr())){
			/* not suppose to happen, except if session expired? */
			log.error("ADDITION submit error, studnr empty");
			return (mapping.findForward("step1"));
		}
		
		log.info("ADDITION submit for studnr="+regDetailsForm.getStudentNr());

		if (regDetailsForm.getAdditionalStudyUnits().isEmpty()){
			/* not suppose to happen */
			log.error("ADDITION submit error, no s-units for studnr="+regDetailsForm.getStudentNr());
			return (mapping.findForward("step1"));
		}
		
		Sruaf01sStudyUnitAddition op = new Sruaf01sStudyUnitAddition();
        operListener opl = new operListener();
        op.addExceptionListener(opl);
        op.clear();

        op.setInCsfClientServerCommunicationsClientVersionNumber((short) 3);
        op.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
        op.setInCsfClientServerCommunicationsAction("A");
        op.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");

        op.setInWsWorkstationCode("internet");
        op.setInWsUserNumber(99998);
        op.setInStudentAnnualRecordMkStudentNr(Integer.parseInt(regDetailsForm.getStudentNr()));// from screen
        op.setInStudentAnnualRecordMkAcademicYear(Short.valueOf(Integer.toString(RegQueryDAO.getCurrentYear())));
        op.setInStudentAnnualRecordMkAcademicPeriod(Short.parseShort("1"));
        if ("1".equalsIgnoreCase(regDetailsForm.getAmendQual()) || "2".equalsIgnoreCase(regDetailsForm.getAmendQual()) ){
        	// qual or speciality changed
          op.setInStudentAcademicRecordMkQualificationCode(regDetailsForm.getNewQual().getQualCode());// from screen
          op.setInQualificationSpecialityTypeSpecialityCode(regDetailsForm.getNewQual().getSpecCode());// from screen
        }else{
        	op.setInStudentAcademicRecordMkQualificationCode(regDetailsForm.getQual().getQualCode());// from screen
            op.setInQualificationSpecialityTypeSpecialityCode(regDetailsForm.getQual().getSpecCode());// from screen
        }
        if("Y".equalsIgnoreCase(regDetailsForm.getCompleteQual())){
        	//final year students status code must be set to FI
        	op.setInStudentAcademicRecordStatusCode("FI");
        }else{
        	op.setInStudentAcademicRecordStatusCode(regDetailsForm.getQualStatus());
        }
        op.setInFinalYearIefSuppliedFlag(regDetailsForm.getCompleteQual());// from screen
        op.setInStudentAnnualRecordDespatchMethodCode(regDetailsForm.getDeliveryType());//from screen
        op.setInStudentAnnualRecordRegistrationMethodCode("P");
        /* ----------------------------------------------------
         * start populating final study unit list
         * -----------------------------------------------------*/
        // combine student selection with study units retrieved from sruaf
        ArrayList<StudyUnit> finalList = new ArrayList<StudyUnit>();
        for (int i = 0; i < regDetailsForm.getAdditionalStudyUnits().size(); i++) {
			// loop through student selected modules
        	StudyUnit su = (StudyUnit) regDetailsForm.getAdditionalStudyUnits().get(i);
			if ("A".equalsIgnoreCase(su.getLanguage().substring(0,1))){
				su.setLanguage("A");
			}else {
				su.setLanguage("E");
			}
        	for (int j = 0; j < regDetailsForm.getStudyUnitsFromSruaf01().size(); j++) {
        		// loop through previously selected modules from sruaf01
        		StudyUnit sruafSu = new StudyUnit();
        		sruafSu = (StudyUnit)regDetailsForm.getStudyUnitsFromSruaf01().get(j);
				if(sruafSu.getCode().equalsIgnoreCase(su.getCode())){
					su.setAction("M");
				}else{
					su.setAction("I");
				}
			}
        	finalList.add(su);
		}
        // add all units from sruaf not selected by student as 'R' to be removed
        for (int i = 0; i < regDetailsForm.getStudyUnitsFromSruaf01().size(); i++) {
    		// loop through previously selected modules from sruaf01
    		StudyUnit sruafSu = (StudyUnit) regDetailsForm.getStudyUnitsFromSruaf01().get(i);
    		boolean found = false;
        	for (int j = 0; j < finalList.size(); j++) {
        		// loop through final modules list
            	StudyUnit su = (StudyUnit) finalList.get(j);
				if(su.getCode().equalsIgnoreCase(sruafSu.getCode())){
					found = true;
				}
			}
        	if(!found){
    			sruafSu.setAction("R");
    			finalList.add(sruafSu);
        	}
        }
        	
        	// populate F139 study units with final study units list
        	for (int t = 0; t < finalList.size(); t++) {
            	StudyUnit su = (StudyUnit) finalList.get(t);
    			op.setInGSuStudentStudyUnitMkCourseStudyUnitCode(t+1, su.getCode());
    			op.setInGSuStudentStudyUnitSemesterPeriod(t+1, Short.parseShort(su.getRegPeriod()));
    			op.setInGSuStudentStudyUnitLanguageCode(t+1, su.getLanguage());
    			op.setInGSuNdpIefSuppliedFlag(t+1, su.getNdp());
    			op.setInGSuStudentStudyUnitMkStudyUnitOptionCode(t+1, su.getWilAnswer());
    			op.setInGSuStudentStudyUnitTutorialFlag(t+1, su.getOdlAnswer());
    			op.setInGSuCsfLineActionBarAction(t+1,su.getAction());
        	}
        op.setInDoTempRegistrationIefSuppliedFlag("Y");
		op.execute();
		
        if (opl.getException() != null) throw opl.getException();
        if (op.getExitStateType() < 3) throw new Exception(op.getExitStateMsg());
        

    	String msg = op.getOutCsfStringsString500();
        if (op.getOutCsfClientServerCommunicationsReturnCode() == Short.parseShort("2028")){
            // temp reg not enough money
        	request.setAttribute("successMsg", msg);
        }else if (op.getOutCsfClientServerCommunicationsReturnCode() == Short.parseShort("2029")){
            // temp reg enough money
        	request.setAttribute("successMsg", msg);          
        }else {
        	// error occured
        	if ((msg != null) && (!msg.equals(""))) {
        		regDetailsForm.setSelfhelpErrorMsg(msg);
        		return mapping.findForward("selfhelpError");
        	}
        }
        
        /* Write sakai event */
		eventTrackingService.post(
				eventTrackingService.newEvent(EventTrackingTypes.EVENT_REGDETAILS_ADDITION,toolManager.getCurrentPlacement().getContext()+"/"+regDetailsForm.getStudentNr(), false),usageSession);
		/* Write flag 99 */
		RegQueryDAO db = new RegQueryDAO();
		db.writeStudentFlag(regDetailsForm.getStudentNr(),"99");

		reset(regDetailsForm);
		//log.debug("AdditionsAction - Selfhelp - Selfhelp Complete - Goto selfhelpSuccess");
		return mapping.findForward("selfhelpSuccess");

	}
	
	/**
	 * This method does a READ action via student system function 139, Sruaf01c
	 *
	 * @param regDetailsForm       The form associated with the action
	 * @throws Exception
	*/

	private String readSruaf01(RegDetailsForm regDetailsForm) throws Exception{
		
		AdditionQueryDAO dao = new AdditionQueryDAO();
		Sruaf01sStudyUnitAddition op = new Sruaf01sStudyUnitAddition();
        operListener opl = new operListener();
        op.addExceptionListener(opl);
        op.clear();

        op.setInCsfClientServerCommunicationsClientVersionNumber((short) 3);
        op.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
        op.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
        op.setInCsfClientServerCommunicationsAction("D");

        op.setInStudentAnnualRecordMkStudentNr(Integer.parseInt(regDetailsForm.getStudentNr()));// from screen
        op.setInStudentAnnualRecordMkAcademicYear(Short.valueOf(Integer.toString(RegQueryDAO.getCurrentYear())));
        op.setInStudentAnnualRecordMkAcademicPeriod(Short.parseShort("1"));
        
		op.execute();
		
        if (opl.getException() != null) throw opl.getException();
        if (op.getExitStateType() < 3) throw new Exception(op.getExitStateMsg());

        String errormsg = op.getOutCsfStringsString500();
        if ((errormsg != null) && (!errormsg.equals(""))) {
        	return errormsg;
        }

        int count = op.getOutSuGroupCount();
        int totalCredit = 0;
        ArrayList<StudyUnit>  studyUnits = new ArrayList<StudyUnit>();
        // skip first index of group which is -new-
       for(int i=1; i < count ; i++){              
          StudyUnit studyUnit = new StudyUnit();
          if (op.getOutGSuStudentStudyUnitMkCourseStudyUnitCode(i) != null && !"".equals(op.getOutGSuStudentStudyUnitMkCourseStudyUnitCode(i).trim())){
            studyUnit.setCode(op.getOutGSuStudentStudyUnitMkCourseStudyUnitCode(i));
            studyUnit.setRegPeriod(Short.toString(op.getOutGSuStudentStudyUnitSemesterPeriod(i)));
            studyUnit.setLanguage(op.getOutGSuStudentStudyUnitLanguageCode(i));
            studyUnit.setNdp(op.getOutGSuNdpIefSuppliedFlag(i));
            studyUnit.setWilAnswer(op.getOutGSuStudentStudyUnitMkStudyUnitOptionCode(i));
            studyUnit.setOdlAnswer(op.getOutGSuStudentStudyUnitTutorialFlag(i));
            studyUnit.setAction("M");
            int credit = 0;
            credit = dao.getCredits(regDetailsForm.getQual().getQualCode(), op.getOutGSuStudentStudyUnitMkCourseStudyUnitCode(i));
            if (credit >0){
            	totalCredit = totalCredit + credit;
            }
            		
            //res?

            studyUnits.add(studyUnit);
          }
       }
       regDetailsForm.setStudyUnitsFromSruaf01(studyUnits);
       regDetailsForm.setQualStatus(op.getOutStudentAcademicRecordStatusCode());
       regDetailsForm.setCreditTotal(totalCredit);
       //log.debug("Set Proxy Credits="+totalCredit);
       
       return "";

	}

	/**
	 * This method does a qualification check via student system function 139, Sruaf01c
	 *
	 * @param regDetailsForm       The form associated with the action
	 * @throws Exception
	*/

	private String checkQualificationChangeViaSruaf01(RegDetailsForm regDetailsForm) throws Exception{
		
		Sruaf01sStudyUnitAddition op = new Sruaf01sStudyUnitAddition();
        operListener opl = new operListener();
        op.addExceptionListener(opl);
        op.clear();

        op.setInCsfClientServerCommunicationsClientVersionNumber((short) 3);
        op.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
        op.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
        op.setInCsfClientServerCommunicationsAction("Q");

        op.setInStudentAnnualRecordMkStudentNr(Integer.parseInt(regDetailsForm.getStudentNr()));// from screen
        op.setInStudentAnnualRecordMkAcademicYear(Short.valueOf(Integer.toString(RegQueryDAO.getCurrentYear())));
        op.setInStudentAnnualRecordMkAcademicPeriod(Short.parseShort("1"));
        op.setInStudentAcademicRecordMkQualificationCode(regDetailsForm.getNewQual().getQualCode());// from screen
        if(regDetailsForm.getNewQual().getSpecCode()!=null){
        	op.setInQualificationSpecialityTypeSpecialityCode(regDetailsForm.getNewQual().getSpecCode());// from screen
        }
        
		op.execute();
		
        if (opl.getException() != null) throw opl.getException();
        if (op.getExitStateType() < 3) throw new Exception(op.getExitStateMsg());

        String errormsg = op.getOutCsfStringsString500();
        if ((errormsg != null) && (!errormsg.equals(""))) {
        	return errormsg;
        }

		return errormsg;

	}

	public ActionForward unspecified(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response){

		log.info("Additions: unspecified method call -no value for parameter in request");
		return mapping.findForward("step1");

	}
	
	private Vector getPostalAddress(RegDetailsForm regDetailsForm, Srcds01sMntStudContactDetail op)
	{
        Vector postalRecords = new Vector();
		Address postalAddress = new Address();
        if (!"".equalsIgnoreCase(op.getOutPostalWsAddressV2AddressLine1().trim())){
        	postalRecords.add(op.getOutPostalWsAddressV2AddressLine1().toString().toUpperCase().trim());
        	postalAddress.setAddressLine1(op.getOutPostalWsAddressV2AddressLine1().toString().toUpperCase().trim());
        }
        if (!"".equalsIgnoreCase(op.getOutPostalWsAddressV2AddressLine2().trim())){
        	postalRecords.add(op.getOutPostalWsAddressV2AddressLine2().toString().toUpperCase().trim());
        	postalAddress.setAddressLine2(op.getOutPostalWsAddressV2AddressLine2().toString().toUpperCase().trim());
        }
        if (!"".equalsIgnoreCase(op.getOutPostalWsAddressV2AddressLine3().trim())&&
        	(op.getOutPostalWsAddressV2AddressLine3().trim() != null)){
        	postalRecords.add(op.getOutPostalWsAddressV2AddressLine3().toString().toUpperCase().trim());
        	postalAddress.setAddressLine3(op.getOutPostalWsAddressV2AddressLine3().toString().toUpperCase().trim());
        }
        if (!"".equalsIgnoreCase(op.getOutPostalWsAddressV2AddressLine4().trim())){
        	postalRecords.add(op.getOutPostalWsAddressV2AddressLine4().toString().toUpperCase().trim());
        	postalAddress.setAddressLine4(op.getOutPostalWsAddressV2AddressLine4().toString().toUpperCase().trim());
        }
        if (!"".equalsIgnoreCase(op.getOutPostalWsAddressV2AddressLine5().trim())){
        	postalRecords.add(op.getOutPostalWsAddressV2AddressLine5().toString().toUpperCase().trim());
        	postalAddress.setAddressLine5(op.getOutPostalWsAddressV2AddressLine5().toString().toUpperCase().trim());
        }
        if (!"".equalsIgnoreCase(op.getOutPostalWsAddressV2AddressLine6().trim())){
        	postalRecords.add(op.getOutPostalWsAddressV2AddressLine6().toString().toUpperCase().trim());
        	postalAddress.setAddressLine6(op.getOutPostalWsAddressV2AddressLine6().toString().toUpperCase().trim());
        }
        postalAddress.setPostalCode(Short.toString(op.getOutPostalWsAddressV2PostalCode()));
        if (postalAddress.getPostalCode().length()!= 4){
        	if (postalAddress.getPostalCode().length()== 3){
        		postalAddress.setPostalCode("0" + postalAddress.getPostalCode());
        	}else if (postalAddress.getPostalCode().length()== 2){
        		postalAddress.setPostalCode("00" + postalAddress.getPostalCode());
        	}if (postalAddress.getPostalCode().length()== 1){
        		postalAddress.setPostalCode("000" + postalAddress.getPostalCode());
        	}
        }
        regDetailsForm.setPostal(postalRecords);
        regDetailsForm.setPostalAddress(postalAddress);
		return postalRecords;
		
		
	}
	
	private Vector getPhysicalAddress(RegDetailsForm regDetailsForm, Srcds01sMntStudContactDetail op)
	{
        Vector physicalRecords = new Vector();
        Address physicalAddress = new Address();
        
        if (!"".equalsIgnoreCase(op.getOutPhysicalWsAddressV2AddressLine1().trim())){
        	physicalRecords.add(op.getOutPhysicalWsAddressV2AddressLine1().toString().toUpperCase().trim());
        	physicalAddress.setAddressLine1(op.getOutPhysicalWsAddressV2AddressLine1().toString().toUpperCase().trim());
        }
        if (!"".equalsIgnoreCase(op.getOutPhysicalWsAddressV2AddressLine2().trim())){
        	physicalRecords.add(op.getOutPhysicalWsAddressV2AddressLine2().toString().toUpperCase().trim());
        	physicalAddress.setAddressLine2(op.getOutPhysicalWsAddressV2AddressLine2().toString().toUpperCase().trim());
        }
        if (!"".equalsIgnoreCase(op.getOutPhysicalWsAddressV2AddressLine3().trim())){
        	physicalRecords.add(op.getOutPhysicalWsAddressV2AddressLine3().toString().toUpperCase().trim());
        	physicalAddress.setAddressLine3(op.getOutPhysicalWsAddressV2AddressLine3().toString().toUpperCase().trim());
        }
        if (!"".equalsIgnoreCase(op.getOutPhysicalWsAddressV2AddressLine4().trim())){
        	physicalRecords.add(op.getOutPhysicalWsAddressV2AddressLine4().toString().toUpperCase().trim());
        	physicalAddress.setAddressLine4(op.getOutPhysicalWsAddressV2AddressLine4().toString().toUpperCase().trim());
        }
        if (!"".equalsIgnoreCase(op.getOutPhysicalWsAddressV2AddressLine5().trim())){
        	physicalRecords.add(op.getOutPhysicalWsAddressV2AddressLine5().toString().toUpperCase().trim());
        	physicalAddress.setAddressLine5(op.getOutPhysicalWsAddressV2AddressLine5().toString().toUpperCase().trim());
        }
        if (!"".equalsIgnoreCase(op.getOutPhysicalWsAddressV2AddressLine6().trim())){
        	physicalRecords.add(op.getOutPhysicalWsAddressV2AddressLine6().toString().toUpperCase().trim());
        	physicalAddress.setAddressLine6(op.getOutPhysicalWsAddressV2AddressLine6().toString().toUpperCase().trim());
        }
        
        physicalAddress.setPostalCode(Short.toString(op.getOutPhysicalWsAddressV2PostalCode()));
        if (physicalAddress.getPostalCode().length()!= 4){
        	if (physicalAddress.getPostalCode().length()== 3){
	        	physicalAddress.setPostalCode("0" + physicalAddress.getPostalCode());
        	}else if (physicalAddress.getPostalCode().length()== 2){
	        	physicalAddress.setPostalCode("00" + physicalAddress.getPostalCode());
        	}if (physicalAddress.getPostalCode().length()== 1){
	        	physicalAddress.setPostalCode("000" + physicalAddress.getPostalCode());
        	}
        }
        regDetailsForm.setPhysical(physicalRecords);
        regDetailsForm.setPhysicalAddress(physicalAddress);
        return physicalRecords;
	}

	private Vector getCourierAddress(RegDetailsForm regDetailsForm, Srcds01sMntStudContactDetail op)
	{
        Vector courierRecords = new Vector();
        Address courierAddress = new Address();
        
        if (!"".equalsIgnoreCase(op.getOutCourierWsAddressV2AddressLine1().trim())){
        	courierRecords.add(op.getOutCourierWsAddressV2AddressLine1().toString().toUpperCase().trim());
        	courierAddress.setAddressLine1(op.getOutCourierWsAddressV2AddressLine1().toString().toUpperCase().trim());
        }
        if (!"".equalsIgnoreCase(op.getOutCourierWsAddressV2AddressLine2().trim())){
        	courierRecords.add(op.getOutCourierWsAddressV2AddressLine2().toString().toUpperCase().trim());
        	courierAddress.setAddressLine2(op.getOutCourierWsAddressV2AddressLine2().toString().toUpperCase().trim());
        }
        if (!"".equalsIgnoreCase(op.getOutCourierWsAddressV2AddressLine3().trim())){
        	courierRecords.add(op.getOutCourierWsAddressV2AddressLine3().toString().toUpperCase().trim());
        	courierAddress.setAddressLine3(op.getOutCourierWsAddressV2AddressLine3().toString().toUpperCase().trim());
        }
        if (!"".equalsIgnoreCase(op.getOutCourierWsAddressV2AddressLine4().trim())){
        	courierRecords.add(op.getOutCourierWsAddressV2AddressLine4().toString().toUpperCase().trim());
        	courierAddress.setAddressLine4(op.getOutCourierWsAddressV2AddressLine4().toString().toUpperCase().trim());
        }
        if (!"".equalsIgnoreCase(op.getOutCourierWsAddressV2AddressLine5().trim())){
        	courierRecords.add(op.getOutCourierWsAddressV2AddressLine5().toString().toUpperCase().trim());
        	courierAddress.setAddressLine5(op.getOutCourierWsAddressV2AddressLine5().toString().toUpperCase().trim());
        }
        if (!"".equalsIgnoreCase(op.getOutCourierWsAddressV2AddressLine6().trim())){
        	courierRecords.add(op.getOutCourierWsAddressV2AddressLine6().toString().toUpperCase().trim());
        	courierAddress.setAddressLine6(op.getOutCourierWsAddressV2AddressLine6().toString().toUpperCase().trim());
        }
        
        courierAddress.setPostalCode(Short.toString(op.getOutCourierWsAddressV2PostalCode()));
        if (courierAddress.getPostalCode().length()!= 4){
        	if (courierAddress.getPostalCode().length()== 3){
        		courierAddress.setPostalCode("0" + courierAddress.getPostalCode());
        	}else if (courierAddress.getPostalCode().length()== 2){
        		courierAddress.setPostalCode("00" + courierAddress.getPostalCode());
        	}if (courierAddress.getPostalCode().length()== 1){
        		courierAddress.setPostalCode("000" + courierAddress.getPostalCode());
        	}
        }
        regDetailsForm.setCourier(courierRecords);
        regDetailsForm.setCourierAddress(courierAddress);
        
        return courierRecords;
	}
	
	private String getBoxOrStreet(String addressLine){

		String result = "S";

		if (addressLine.toUpperCase().contains("POSTNET SUITE")){
			result= "B";
		}else if (addressLine.toUpperCase().contains("POSNET SUITE")){
			result= "B";
		}else if (addressLine.toUpperCase().contains("PRIVATE BAG")){
			result= "B";
		}else if (addressLine.toUpperCase().contains("PRIVATEBAG")){
			result= "B";
		}else if (addressLine.toUpperCase().contains("BAG ")){
			result= "B";
		}else if (addressLine.toUpperCase().contains("POSBUS")){
			result= "B";
		}else if (addressLine.toUpperCase().contains("BUS ")){
			result= "B";
		}else if (addressLine.toUpperCase().contains("P O BOX")){
			result= "B";
		}else if (addressLine.toUpperCase().contains("BOX ")){
			result= "B";
		}else if (addressLine.toUpperCase().contains("POSBUS")){
			result= "B";
		}else if (addressLine.toUpperCase().contains("POSTE RESTANTE ")){
			result= "B";
		}else if (addressLine.toUpperCase().contains("POST RESTANTE ")){
			result= "B";
		}

		return result;

	}

	  public void getAllRequestParamaters(HttpServletRequest req, HttpServletResponse res) throws Exception { 

		    Enumeration<String> parameterNames = req.getParameterNames(); 

		    while (parameterNames.hasMoreElements()) { 
		        String paramName = parameterNames.nextElement(); 
		        //log.debug("param: " + paramName); 

		        String[] paramValues = req.getParameterValues(paramName); 
		        for (int i = 0; i < paramValues.length; i++) { 
		            String paramValue = paramValues[i]; 
		            //log.debug("value: " + paramValue); 
		        } 
		    } 
		} 
	  
}

