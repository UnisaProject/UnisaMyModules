//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.biodetails.actions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

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
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.tool.api.ToolManager;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserDirectoryService;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.component.cover.ServerConfigurationService;

import za.ac.unisa.lms.constants.EventTrackingTypes;
import za.ac.unisa.lms.tools.biodetails.dao.AddressDetailsDAO;
import za.ac.unisa.lms.tools.biodetails.dao.ContactDetailsDAO;
import za.ac.unisa.lms.tools.biodetails.dao.EmailOptionDAO;
import za.ac.unisa.lms.tools.biodetails.forms.BioDetailsForm;
import Srcds01h.Abean.Srcds01sMntStudContactDetail;

/**
 * MyEclipse Struts
 * Creation date: 09-14-2005
 *
 * XDoclet definition:
 * @struts:action validate="true"
 */
public class BioDetailsAction extends DispatchAction {

	// --------------------------------------------------------- Instance Variables
	private SessionManager sessionManager;
	private UserDirectoryService userDirectoryService;
	private EventTrackingService eventTrackingService;
	private ToolManager toolManager;
	private UsageSessionService usageSessionService;

	// --------------------------------------------------------- Methods
	public static Log log = LogFactory.getLog(BioDetailsAction.class);
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
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected Map getKeyMethodMap() {
	      Map map = new HashMap();
	      map.put("button.continue", "nextStep");

	      return map;
	  }
	

	/**
	 * Method displayBioDetails
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ActionForward displayBioDetails(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) throws Exception {

		ActionMessages messages = new ActionMessages();
		BioDetailsForm bioDetailsForm = (BioDetailsForm)form;
		AddressDetailsDAO dao = new AddressDetailsDAO();
		
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
		toolManager = (ToolManager) ComponentManager.get(ToolManager.class);
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
		UsageSession usageSession = usageSessionService.getSession();
		//startSession(bioDetailsForm.getNumber(),request.getRemoteAddr(),request.getHeader("user-agent"));
		EmailOptionDAO smsoptiondao = new EmailOptionDAO();

		String originatedFrom =  request.getParameter("originatedFrom");
		if (originatedFrom != null)
		{
			bioDetailsForm.setOriginatedFrom(originatedFrom);
		}
		
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
				bioDetailsForm.setNumber(userEid);
			}else{
				messages.add(ActionMessages.GLOBAL_MESSAGE, 
						new ActionMessage("message.generalmessage","User ID is empty."));
				return mapping.findForward("home");
			}
		}

		if (user.getType() != null){
			if (!"student".equalsIgnoreCase(user.getType())){
				return mapping.findForward("invaliduser");
			}
		}else{
			messages.add(ActionMessages.GLOBAL_MESSAGE, 
					new ActionMessage("message.generalmessage","User Account Type is empty."));
			return mapping.findForward("home");
		}
		
		if (messages.isEmpty()) {

			resetAll(bioDetailsForm);
			Srcds01sMntStudContactDetail op = new Srcds01sMntStudContactDetail();
	        operListener opl = new operListener();
	        op.addExceptionListener(opl);
	        op.clear();

	        /* op.setTracing(Trace.MASK_ALL); */
	       // op.setInIpAddressCsfStringsString15(request.getRemoteAddr());
	        op.setInSecurityWsUserNumber(9999);
	        op.setInCsfClientServerCommunicationsClientVersionNumber((short) 3);
	        op.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
	        op.setInCsfClientServerCommunicationsAction("D");
	        op.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
	        op.setInWsStudentNr(Integer.parseInt(bioDetailsForm.getNumber()));
	        op.execute();
	        if (opl.getException() != null) throw opl.getException();
	        if (op.getExitStateType() < 3) throw new Exception(op.getExitStateMsg());

	        String Errormsg = op.getOutCsfStringsString500();
	        if ((Errormsg != null) && (!Errormsg.equals(""))) {
	        	messages.add(ActionMessages.GLOBAL_MESSAGE,
	        			new ActionMessage("error.coolgenerror", Errormsg));
	        }
	        //Personal information
	        bioDetailsForm.setTitle(op.getOutWsStudentMkTitle());
	        bioDetailsForm.setInitials(op.getOutWsStudentInitials());
	        bioDetailsForm.setSurname(op.getOutWsStudentSurname());
	        bioDetailsForm.setFirstNames(op.getOutWsStudentFirstNames());
	        bioDetailsForm.setPrevSurname(op.getOutWsStudentPreviousSurname());
	        if ("F".equalsIgnoreCase(op.getOutWsStudentGender())){
	        	bioDetailsForm.setGender("FEMALE");
	        }	else if ("M".equalsIgnoreCase(op.getOutWsStudentGender())){
	        	bioDetailsForm.setGender("MALE");
	        } else {
	        	bioDetailsForm.setGender("UNKNOWN");
	        }
	        DateFormat strDate = new SimpleDateFormat( "dd-MMM-yyyy" );
	        bioDetailsForm.setBirthDate(strDate.format(op.getOutWsStudentBirthDate().getTime()));
	        if (!"".equalsIgnoreCase(op.getOutWsStudentIdentityNr())){
	        	bioDetailsForm.setIdentityNr(op.getOutWsStudentIdentityNr());
	        }else if (!"".equalsIgnoreCase(op.getOutWsStudentPassportNo())){
	        	bioDetailsForm.setIdentityNr(op.getOutWsStudentPassportNo());
	        }
	        bioDetailsForm.setNationality(op.getOutNationalityWsCountryV2EngDescription());
	        bioDetailsForm.setCountryDescription(op.getOutWsCountryV2EngDescription());
	        bioDetailsForm.setCountryCode(op.getOutWsCountryV2Code());
	        if ("1015".equalsIgnoreCase(op.getOutWsCountryV2Code())){
	        	bioDetailsForm.setForeign("L");//local
	        } else {
	        	bioDetailsForm.setForeign("F");//Foreign
	        }
	        bioDetailsForm.setEthnicGroup(op.getOutWsEthnicGroupEngDescription());
	        
	        if ("A".equalsIgnoreCase(op.getOutWsStudentMkHomeLanguage().trim())){
	        	bioDetailsForm.setHomeLang("AFRIKAANS");
	        }else if ("E".equalsIgnoreCase(op.getOutWsStudentMkHomeLanguage().trim())){
	        	bioDetailsForm.setHomeLang("ENGLISH");
	        } else {
	        	bioDetailsForm.setHomeLang("UNKNOWN");
	        }
	        
        	bioDetailsForm.setCorrespondLang("ENGLISH");
	        
	        bioDetailsForm.setDisability(op.getOutWsDisabilityTypeEngDescription().toUpperCase());
	        bioDetailsForm.setRegRegion(op.getOutWsRegionalOfficeEngDescription().toUpperCase());

	        // Postal address
	        Vector postalRecords = new Vector();
	        if (!"".equalsIgnoreCase(op.getOutPostalWsAddressV2AddressLine1().trim()) && (op.getOutPostalWsAddressV2AddressLine1().trim() != null)){
	        	postalRecords.add(op.getOutPostalWsAddressV2AddressLine1().toString().toUpperCase().trim());
	        	bioDetailsForm.setPostalAddressType(getBoxOrStreet(op.getOutPostalWsAddressV2AddressLine1().trim()));
	        	if ("B".equalsIgnoreCase(bioDetailsForm.getPostalAddressType())){
	        		bioDetailsForm.setPostalLine1(op.getOutPostalWsAddressV2AddressLine1().toString().toUpperCase().trim());
	        	}else{
	        		bioDetailsForm.setPostalStreetLine1(op.getOutPostalWsAddressV2AddressLine1().toString().toUpperCase().trim());
	        	}
	        }
	        if (!"".equalsIgnoreCase(op.getOutPostalWsAddressV2AddressLine2().trim()) && (op.getOutPostalWsAddressV2AddressLine2().trim() != null)){
	        	if (dao.isSuburbValid(op.getOutPostalWsAddressV2AddressLine2().trim(), "Postal", bioDetailsForm.getPostalAddressType())){
	        		if ("B".equalsIgnoreCase(bioDetailsForm.getPostalAddressType())){
	        			bioDetailsForm.setPostalSuburb(op.getOutPostalWsAddressV2AddressLine2().toString().toUpperCase().trim());
		        	}else{
		        		bioDetailsForm.setPostalStreetSuburb(op.getOutPostalWsAddressV2AddressLine2().toString().toUpperCase().trim());
		        	}
	        	}else{
		        	postalRecords.add(op.getOutPostalWsAddressV2AddressLine2().toString().toUpperCase().trim());
		        	if ("B".equalsIgnoreCase(bioDetailsForm.getPostalAddressType())){
		        		bioDetailsForm.setPostalLine2(op.getOutPostalWsAddressV2AddressLine2().toString().toUpperCase().trim());
		        	}else{
		        		bioDetailsForm.setPostalStreetLine2(op.getOutPostalWsAddressV2AddressLine2().toString().toUpperCase().trim());
		        	}
	        	}
	        }
	        if (!"".equalsIgnoreCase(op.getOutPostalWsAddressV2AddressLine3().trim()) && (op.getOutPostalWsAddressV2AddressLine3().trim() != null)){
	        	if (dao.isSuburbValid(op.getOutPostalWsAddressV2AddressLine3().trim(), "Postal", bioDetailsForm.getPostalAddressType())){
	        		if ("B".equalsIgnoreCase(bioDetailsForm.getPostalAddressType())){
	        			bioDetailsForm.setPostalSuburb(op.getOutPostalWsAddressV2AddressLine3().toString().toUpperCase().trim());
		        	}else{
		        		bioDetailsForm.setPostalStreetSuburb(op.getOutPostalWsAddressV2AddressLine3().toString().toUpperCase().trim());
		        	}
	        	}else{
		        	postalRecords.add(op.getOutPostalWsAddressV2AddressLine3().toString().toUpperCase().trim());
		        	if ("B".equalsIgnoreCase(bioDetailsForm.getPostalAddressType())){
		        		bioDetailsForm.setPostalLine3(op.getOutPostalWsAddressV2AddressLine3().toString().toUpperCase().trim());
		        	}else{
		        		bioDetailsForm.setPostalStreetLine3(op.getOutPostalWsAddressV2AddressLine3().toString().toUpperCase().trim());
		        	}
	        	}
	        }
	        if (!"".equalsIgnoreCase(op.getOutPostalWsAddressV2AddressLine4().trim()) && (op.getOutPostalWsAddressV2AddressLine4().trim() != null)){
	        	if (dao.isSuburbValid(op.getOutPostalWsAddressV2AddressLine4().trim(), "Postal", bioDetailsForm.getPostalAddressType())){
	        		if ("B".equalsIgnoreCase(bioDetailsForm.getPostalAddressType())){
	        			bioDetailsForm.setPostalSuburb(op.getOutPostalWsAddressV2AddressLine4().toString().toUpperCase().trim());
		        	}else{
		        		bioDetailsForm.setPostalStreetSuburb(op.getOutPostalWsAddressV2AddressLine4().toString().toUpperCase().trim());
		        	}
	        	}else{
		        	postalRecords.add(op.getOutPostalWsAddressV2AddressLine4().toString().toUpperCase().trim());
		        	if ("B".equalsIgnoreCase(bioDetailsForm.getPostalAddressType())){
		        		bioDetailsForm.setPostalLine4(op.getOutPostalWsAddressV2AddressLine4().toString().toUpperCase().trim());
		        	}else{
		        		bioDetailsForm.setPostalStreetLine4(op.getOutPostalWsAddressV2AddressLine4().toString().toUpperCase().trim());
		        	}
	        	}
	        }
	        if (!"".equalsIgnoreCase(op.getOutPostalWsAddressV2AddressLine5().trim()) && (op.getOutPostalWsAddressV2AddressLine5().trim() != null)){
	        	if (dao.isSuburbValid(op.getOutPostalWsAddressV2AddressLine5().trim(), "Postal", bioDetailsForm.getPostalAddressType())){
	        		if ("B".equalsIgnoreCase(bioDetailsForm.getPostalAddressType())){
	        			bioDetailsForm.setPostalSuburb(op.getOutPostalWsAddressV2AddressLine5().toString().toUpperCase().trim());
		        	}else{
		        		bioDetailsForm.setPostalStreetSuburb(op.getOutPostalWsAddressV2AddressLine5().toString().toUpperCase().trim());
		        	}
	        	}else{
		        	postalRecords.add(op.getOutPostalWsAddressV2AddressLine5().toString().toUpperCase().trim());
		        	if ("B".equalsIgnoreCase(bioDetailsForm.getPostalAddressType())){
		        		bioDetailsForm.setPostalLine5(op.getOutPostalWsAddressV2AddressLine5().toString().toUpperCase().trim());
		        	}else{
		        		bioDetailsForm.setPostalStreetLine5(op.getOutPostalWsAddressV2AddressLine5().toString().toUpperCase().trim());
		        	}
	        	}
	        }
	        if (!"".equalsIgnoreCase(op.getOutPostalWsAddressV2AddressLine6().trim()) && (op.getOutPostalWsAddressV2AddressLine6().trim() != null)){
	        	if (dao.isSuburbValid(op.getOutPostalWsAddressV2AddressLine6().trim(), "Postal", bioDetailsForm.getPostalAddressType())){
	        		if ("B".equalsIgnoreCase(bioDetailsForm.getPostalAddressType())){
	        			bioDetailsForm.setPostalSuburb(op.getOutPostalWsAddressV2AddressLine6().toString().toUpperCase().trim());
		        	}else{
		        		bioDetailsForm.setPostalStreetSuburb(op.getOutPostalWsAddressV2AddressLine6().toString().toUpperCase().trim());
		        	}
	        	}else{
		        	postalRecords.add(op.getOutPostalWsAddressV2AddressLine6().toString().toUpperCase().trim());
		        	if ("B".equalsIgnoreCase(bioDetailsForm.getPostalAddressType())){
		        		bioDetailsForm.setPostalLine6(op.getOutPostalWsAddressV2AddressLine6().toString().toUpperCase().trim());
		        	}else{
		        		bioDetailsForm.setPostalStreetLine6(op.getOutPostalWsAddressV2AddressLine6().toString().toUpperCase().trim());
		        	}
	        	}
	        }
	        bioDetailsForm.setPostal(postalRecords);
	        String pCode = Short.toString(op.getOutPostalWsAddressV2PostalCode());
	        
	        if (pCode.length()!= 4){
	        	if (pCode.length()== 3){
	        		pCode = "0" + pCode;
	        	}else if (pCode.length()== 2){
	        		pCode = "00" + pCode;
	        	}if (pCode.length()== 1){
	        		pCode = "000" + pCode;
	        	}
	        }
	        if ("B".equalsIgnoreCase(bioDetailsForm.getPostalAddressType())){
	        	bioDetailsForm.setPostalCode(pCode);
        	}else{
        		bioDetailsForm.setPostalStreetCode(pCode);
        	}
	        
	        
	        log.debug("BioDetailsAction - displayBioDetails - PostalAddressType="+bioDetailsForm.getPostalAddressType());
	        log.debug("BioDetailsAction - displayBioDetails - PostalSuburb="+bioDetailsForm.getPostalSuburb());
	        log.debug("BioDetailsAction - displayBioDetails - PostalCode="+bioDetailsForm.getPostalCode());
	        log.debug("BioDetailsAction - displayBioDetails - PostalStreetSuburb="+bioDetailsForm.getPostalStreetSuburb());
	        log.debug("BioDetailsAction - displayBioDetails - PostalStreetCode="+bioDetailsForm.getPostalStreetCode());
	        
	        // Physical address
	        Vector physicalRecords = new Vector();
	        boolean isPhysicalSuburbSet = false;
	        boolean isPhysicalTownSet = false;
	        if (!"".equalsIgnoreCase(op.getOutPhysicalWsAddressV2AddressLine1().trim())){
	        	physicalRecords.add(op.getOutPhysicalWsAddressV2AddressLine1().toString().toUpperCase().trim());
	        	bioDetailsForm.setPhysicalLine1(op.getOutPhysicalWsAddressV2AddressLine1().toString().toUpperCase().trim());
	        }
	        if (!"".equalsIgnoreCase(op.getOutPhysicalWsAddressV2AddressLine2().trim())){
	        	if (!isPhysicalSuburbSet && (dao.isSuburbValid(op.getOutPhysicalWsAddressV2AddressLine2().trim(), "Physical", "S") || dao.isSuburbValid(op.getOutPhysicalWsAddressV2AddressLine2().trim(), "Physical", "B"))){
	        		bioDetailsForm.setPhysicalSuburb(op.getOutPhysicalWsAddressV2AddressLine2().toString().toUpperCase().trim());
	        		isPhysicalSuburbSet = true;
	        	}else if (isPhysicalSuburbSet && !isPhysicalTownSet && (dao.isTownValid(op.getOutPhysicalWsAddressV2AddressLine2().trim(), "Physical", "S") || dao.isTownValid(op.getOutPhysicalWsAddressV2AddressLine2().trim(), "Physical", "B"))){
	        		bioDetailsForm.setPhysicalTown(op.getOutPhysicalWsAddressV2AddressLine2().toString().toUpperCase().trim());
	        		isPhysicalTownSet = true;
	        	}else{
		        	physicalRecords.add(op.getOutPhysicalWsAddressV2AddressLine2().toString().toUpperCase().trim());
		        	bioDetailsForm.setPhysicalLine2(op.getOutPhysicalWsAddressV2AddressLine2().toString().toUpperCase().trim());
	        	}
	        }
	        if (!"".equalsIgnoreCase(op.getOutPhysicalWsAddressV2AddressLine3().trim())){
	        	if (!isPhysicalSuburbSet && (dao.isSuburbValid(op.getOutPhysicalWsAddressV2AddressLine3().trim(), "Physical", "S") || dao.isSuburbValid(op.getOutPhysicalWsAddressV2AddressLine3().trim(), "Physical", "B"))){
	        		bioDetailsForm.setPhysicalSuburb(op.getOutPhysicalWsAddressV2AddressLine3().toString().toUpperCase().trim());
	        		isPhysicalSuburbSet = true;
	        	}else if (isPhysicalSuburbSet && !isPhysicalTownSet && (dao.isTownValid(op.getOutPhysicalWsAddressV2AddressLine3().trim(), "Physical", "S") || dao.isTownValid(op.getOutPhysicalWsAddressV2AddressLine3().trim(), "Physical", "B"))){
	        		bioDetailsForm.setPhysicalTown(op.getOutPhysicalWsAddressV2AddressLine3().toString().toUpperCase().trim());
	        		isPhysicalTownSet = true;
	        	}else{
		        	physicalRecords.add(op.getOutPhysicalWsAddressV2AddressLine3().toString().toUpperCase().trim());
		        	bioDetailsForm.setPhysicalLine3(op.getOutPhysicalWsAddressV2AddressLine3().toString().toUpperCase().trim());
	        	}
	        }
	        if (!"".equalsIgnoreCase(op.getOutPhysicalWsAddressV2AddressLine4().trim())){
	        	if (!isPhysicalSuburbSet && (dao.isSuburbValid(op.getOutPhysicalWsAddressV2AddressLine4().trim(), "Physical", "S") || dao.isSuburbValid(op.getOutPhysicalWsAddressV2AddressLine4().trim(), "Physical", "B"))){
	        		bioDetailsForm.setPhysicalSuburb(op.getOutPhysicalWsAddressV2AddressLine4().toString().toUpperCase().trim());
	        		isPhysicalSuburbSet = true;
	        	}else if (isPhysicalSuburbSet && !isPhysicalTownSet && (dao.isTownValid(op.getOutPhysicalWsAddressV2AddressLine4().trim(), "Physical", "S") || dao.isTownValid(op.getOutPhysicalWsAddressV2AddressLine4().trim(), "Physical", "B"))){
	        		bioDetailsForm.setPhysicalTown(op.getOutPhysicalWsAddressV2AddressLine4().toString().toUpperCase().trim());
	        		isPhysicalTownSet = true;
	        	}else{
		        	physicalRecords.add(op.getOutPhysicalWsAddressV2AddressLine4().toString().toUpperCase().trim());
		        	bioDetailsForm.setPhysicalLine4(op.getOutPhysicalWsAddressV2AddressLine4().toString().toUpperCase().trim());
	        	}
	        }
	        if (!"".equalsIgnoreCase(op.getOutPhysicalWsAddressV2AddressLine5().trim())){
	        	if (!isPhysicalSuburbSet && (dao.isSuburbValid(op.getOutPhysicalWsAddressV2AddressLine5().trim(), "Physical", "S") || dao.isSuburbValid(op.getOutPhysicalWsAddressV2AddressLine5().trim(), "Physical", "B"))){
	        		bioDetailsForm.setPhysicalSuburb(op.getOutPhysicalWsAddressV2AddressLine5().toString().toUpperCase().trim());
	        		isPhysicalSuburbSet = true;
	        	}else if (isPhysicalSuburbSet && !isPhysicalTownSet && (dao.isTownValid(op.getOutPhysicalWsAddressV2AddressLine5().trim(), "Physical", "S") || dao.isTownValid(op.getOutPhysicalWsAddressV2AddressLine5().trim(), "Physical", "B"))){
	        		bioDetailsForm.setPhysicalTown(op.getOutPhysicalWsAddressV2AddressLine5().toString().toUpperCase().trim());
	        		isPhysicalTownSet = true;
	        	}else{
		        	physicalRecords.add(op.getOutPhysicalWsAddressV2AddressLine5().toString().toUpperCase().trim());
		        	bioDetailsForm.setPhysicalLine5(op.getOutPhysicalWsAddressV2AddressLine5().toString().toUpperCase().trim());
	        	}
	        }
	        if (!"".equalsIgnoreCase(op.getOutPhysicalWsAddressV2AddressLine6().trim())){
	        	if (!isPhysicalSuburbSet && (dao.isSuburbValid(op.getOutPhysicalWsAddressV2AddressLine6().trim(), "Physical", "S") || dao.isSuburbValid(op.getOutPhysicalWsAddressV2AddressLine6().trim(), "Physical", "B"))){
	        		bioDetailsForm.setPhysicalSuburb(op.getOutPhysicalWsAddressV2AddressLine6().toString().toUpperCase().trim());
	        		isPhysicalSuburbSet = true;
	        	}else if (isPhysicalSuburbSet && !isPhysicalTownSet && (dao.isTownValid(op.getOutPhysicalWsAddressV2AddressLine6().trim(), "Physical", "S") || dao.isTownValid(op.getOutPhysicalWsAddressV2AddressLine6().trim(), "Physical", "B"))){
	        		bioDetailsForm.setPhysicalTown(op.getOutPhysicalWsAddressV2AddressLine6().toString().toUpperCase().trim());
	        		isPhysicalTownSet = true;
	        	}else{
		        	physicalRecords.add(op.getOutPhysicalWsAddressV2AddressLine6().toString().toUpperCase().trim());
		        	bioDetailsForm.setPhysicalLine6(op.getOutPhysicalWsAddressV2AddressLine6().toString().toUpperCase().trim());
	        	}
	        }
	        bioDetailsForm.setPhysical(physicalRecords);
	        if ((bioDetailsForm.getPhysicalTown() == null || bioDetailsForm.getPhysicalTown().equals("")) && bioDetailsForm.getPhysicalSuburb() != null && !bioDetailsForm.getPhysicalSuburb().trim().equals("")){
	        	String townName = dao.getTownName(bioDetailsForm.getPhysicalSuburb(),"Physical", "S");
	        	if (townName == null || townName.trim().equals("")){
	        		townName = dao.getTownName(bioDetailsForm.getPhysicalSuburb(),"Physical", "B");
	        	}
	        	if (townName == null || townName.trim().equals("")){
	        		townName = "";
	        	}
	        	bioDetailsForm.setPhysicalTown(townName);
	        }
	        String PhysCode = Short.toString(op.getOutPhysicalWsAddressV2PostalCode());
	        log.debug("BioDetailsAction - displayBioDetails - PhysicalPostalCode Source="+PhysCode);
	        if (PhysCode.length()!= 4){
	        	if (PhysCode.length()== 3){
	        		PhysCode = "0" + PhysCode;
	        	}else if (PhysCode.length()== 2){
	        		PhysCode = "00" + PhysCode;
	        	}if (PhysCode.length()== 1){
	        		PhysCode = "000" + PhysCode;
	        	}
	        }
	        bioDetailsForm.setPhysicalPostalCode(PhysCode);
	        log.debug("BioDetailsAction - displayBioDetails - PhysicalPostalCode Final="+PhysCode);
	        
	     // Courier address
	        Vector courierRecords = new Vector();
	        boolean isCourierSuburbSet = false;
	        boolean isCourierTownSet = false;
	        if (!"".equalsIgnoreCase(op.getOutCourierWsAddressV2AddressLine1().trim())){
	        	courierRecords.add(op.getOutCourierWsAddressV2AddressLine1().toString().toUpperCase().trim());
	        	bioDetailsForm.setCourierLine1(op.getOutCourierWsAddressV2AddressLine1().toString().toUpperCase().trim());
	        }
	        if (!"".equalsIgnoreCase(op.getOutCourierWsAddressV2AddressLine2().trim())){
	        	if (!isCourierSuburbSet && (dao.isSuburbValid(op.getOutCourierWsAddressV2AddressLine2().trim(), "Courier", "S") || dao.isSuburbValid(op.getOutCourierWsAddressV2AddressLine2().trim(), "Courier", "B"))){
	        		bioDetailsForm.setCourierSuburb(op.getOutCourierWsAddressV2AddressLine2().toString().toUpperCase().trim());
	        		isCourierSuburbSet = true;
	        	}else if (isCourierSuburbSet && !isCourierTownSet && (dao.isTownValid(op.getOutCourierWsAddressV2AddressLine2().trim(), "Courier", "S") || dao.isTownValid(op.getOutCourierWsAddressV2AddressLine2().trim(), "Courier", "B"))){
	        		bioDetailsForm.setCourierTown(op.getOutCourierWsAddressV2AddressLine2().toString().toUpperCase().trim());
	        		isCourierTownSet = true;
	        	}else{
		        	courierRecords.add(op.getOutCourierWsAddressV2AddressLine2().toString().toUpperCase().trim());
		        	bioDetailsForm.setCourierLine2(op.getOutCourierWsAddressV2AddressLine2().toString().toUpperCase().trim());
	        	}
	        }
	        if (!"".equalsIgnoreCase(op.getOutCourierWsAddressV2AddressLine3().trim())){
	        	if (!isCourierSuburbSet && (dao.isSuburbValid(op.getOutCourierWsAddressV2AddressLine3().trim(), "Courier", "S") || dao.isSuburbValid(op.getOutCourierWsAddressV2AddressLine3().trim(), "Courier", "B"))){
	        		bioDetailsForm.setCourierSuburb(op.getOutCourierWsAddressV2AddressLine3().toString().toUpperCase().trim());
	        		isCourierSuburbSet = true;
	        	}else if (isCourierSuburbSet && !isCourierTownSet && (dao.isTownValid(op.getOutCourierWsAddressV2AddressLine3().trim(), "Courier", "S") || dao.isTownValid(op.getOutCourierWsAddressV2AddressLine3().trim(), "Courier", "B"))){
	        		bioDetailsForm.setCourierTown(op.getOutCourierWsAddressV2AddressLine3().toString().toUpperCase().trim());
	        		isCourierTownSet = true;
	        	}else{
		        	courierRecords.add(op.getOutCourierWsAddressV2AddressLine3().toString().toUpperCase().trim());
		        	bioDetailsForm.setCourierLine3(op.getOutCourierWsAddressV2AddressLine3().toString().toUpperCase().trim());
	        	}
	        }
	        if (!"".equalsIgnoreCase(op.getOutCourierWsAddressV2AddressLine4().trim())){
	        	if (!isCourierSuburbSet && (dao.isSuburbValid(op.getOutCourierWsAddressV2AddressLine4().trim(), "Courier", "S") || dao.isSuburbValid(op.getOutCourierWsAddressV2AddressLine4().trim(), "Courier", "B"))){
	        		bioDetailsForm.setCourierSuburb(op.getOutCourierWsAddressV2AddressLine4().toString().toUpperCase().trim());
	        		isCourierSuburbSet = true;
	        	}else if (isCourierSuburbSet && !isCourierTownSet && (dao.isTownValid(op.getOutCourierWsAddressV2AddressLine4().trim(), "Courier", "S") || dao.isTownValid(op.getOutCourierWsAddressV2AddressLine4().trim(), "Courier", "B"))){
	        		bioDetailsForm.setCourierTown(op.getOutCourierWsAddressV2AddressLine4().toString().toUpperCase().trim());
	        		isCourierTownSet = true;
	        	}else{
		        	courierRecords.add(op.getOutCourierWsAddressV2AddressLine4().toString().toUpperCase().trim());
		        	bioDetailsForm.setCourierLine4(op.getOutCourierWsAddressV2AddressLine4().toString().toUpperCase().trim());
	        	}
	        }
	        if (!"".equalsIgnoreCase(op.getOutCourierWsAddressV2AddressLine5().trim())){
	        	if (!isCourierSuburbSet && (dao.isSuburbValid(op.getOutCourierWsAddressV2AddressLine5().trim(), "Courier", "S") || dao.isSuburbValid(op.getOutCourierWsAddressV2AddressLine5().trim(), "Courier", "B"))){
	        		bioDetailsForm.setCourierSuburb(op.getOutCourierWsAddressV2AddressLine5().toString().toUpperCase().trim());
	        		isCourierSuburbSet = true;
	        	}else if (isCourierSuburbSet && !isCourierTownSet && (dao.isTownValid(op.getOutCourierWsAddressV2AddressLine5().trim(), "Courier", "S") || dao.isTownValid(op.getOutCourierWsAddressV2AddressLine5().trim(), "Courier", "B"))){
	        		bioDetailsForm.setCourierTown(op.getOutCourierWsAddressV2AddressLine5().toString().toUpperCase().trim());
	        		isCourierTownSet = true;
	        	}else{
		        	courierRecords.add(op.getOutCourierWsAddressV2AddressLine5().toString().toUpperCase().trim());
		        	bioDetailsForm.setCourierLine5(op.getOutCourierWsAddressV2AddressLine5().toString().toUpperCase().trim());
	        	}
	        }
	        if (!"".equalsIgnoreCase(op.getOutCourierWsAddressV2AddressLine6().trim())){
	        	if (!isCourierSuburbSet && (dao.isSuburbValid(op.getOutCourierWsAddressV2AddressLine6().trim(), "Courier", "S") || dao.isSuburbValid(op.getOutCourierWsAddressV2AddressLine6().trim(), "Courier", "B"))){
	        		bioDetailsForm.setCourierSuburb(op.getOutCourierWsAddressV2AddressLine6().toString().toUpperCase().trim());
	        		isCourierSuburbSet = true;
	        	}else if (isCourierSuburbSet && !isCourierTownSet && (dao.isTownValid(op.getOutCourierWsAddressV2AddressLine6().trim(), "Courier", "S") || dao.isTownValid(op.getOutCourierWsAddressV2AddressLine6().trim(), "Courier", "B"))){
	        		bioDetailsForm.setCourierTown(op.getOutCourierWsAddressV2AddressLine6().toString().toUpperCase().trim());
	        		isCourierTownSet = true;
	        	}else{
		        	courierRecords.add(op.getOutCourierWsAddressV2AddressLine6().toString().toUpperCase().trim());
		        	bioDetailsForm.setCourierLine6(op.getOutCourierWsAddressV2AddressLine6().toString().toUpperCase().trim());
	        	}
	        }
	        bioDetailsForm.setCourier(courierRecords);
	        if ((bioDetailsForm.getCourierTown() == null || bioDetailsForm.getCourierTown().equals("")) && bioDetailsForm.getCourierSuburb() != null && !bioDetailsForm.getCourierSuburb().trim().equals("")){
	        	String townName = dao.getTownName(bioDetailsForm.getCourierSuburb(),"Courier", "S");
	        	if (townName == null || townName.trim().equals("")){
	        		townName = dao.getTownName(bioDetailsForm.getCourierSuburb(),"Courier", "B");
	        	}
	        	if (townName == null || townName.trim().equals("")){
	        		townName = "";
	        	}
	        	bioDetailsForm.setCourierTown(townName);
	        }
	        String CourCode = Short.toString(op.getOutCourierWsAddressV2PostalCode());
	        log.debug("BioDetailsAction - displayBioDetails - CourierPostalCode Source="+CourCode);
	        if (CourCode.length()!= 4){
	        	if (CourCode.length()== 3){
	        		CourCode = "0" + CourCode;
	        	}else if (CourCode.length()== 2){
	        		CourCode = "00" + CourCode;
	        	}if (CourCode.length()== 1){
	        		CourCode = "000" + CourCode;
	        	}
	        }
	        bioDetailsForm.setCourierPostalCode(CourCode);
	        log.debug("BioDetailsAction - displayBioDetails - CourierPostalCode Final="+CourCode);
	        
	        //Contact numbers
	        bioDetailsForm.setHomeNr(op.getOutContactsWsAddressV2HomeNumber().trim());
	        bioDetailsForm.setOldHomeNr(op.getOutContactsWsAddressV2HomeNumber().trim());
	        bioDetailsForm.setWorkNr(op.getOutContactsWsAddressV2WorkNumber().trim());
	        bioDetailsForm.setOldWorkNr(op.getOutContactsWsAddressV2WorkNumber().trim());
	        bioDetailsForm.setFaxNr(op.getOutContactsWsAddressV2FaxNumber().trim());
	        bioDetailsForm.setOldFaxNr(op.getOutContactsWsAddressV2FaxNumber().trim());
	        bioDetailsForm.setCellNr(op.getOutContactsWsAddressV2CellNumber().trim());
	        bioDetailsForm.setOldCellNr(op.getOutContactsWsAddressV2CellNumber().trim());
	        bioDetailsForm.setEmail(op.getOutContactsWsAddressV2EmailAddress().trim());
	        bioDetailsForm.setOldEmail(op.getOutContactsWsAddressV2EmailAddress().trim());
	        bioDetailsForm.setContactNr(op.getOutContactsWsAddressV2CourierContactNo().trim());

	        //Exam Centres
	        bioDetailsForm.setExamCentreCode(op.getOutWsExamCentreCode());
	        bioDetailsForm.setExamCentre(op.getOutWsExamCentreEngDescription());
	        //SMS Options
	        smsoptiondao.getSmsOptions(bioDetailsForm);
	        // blocked exam option
	        smsoptiondao.getBlockedExamResults(bioDetailsForm);
	        /* Email verified on solact */
	        ContactDetailsDAO contactDao = new ContactDetailsDAO();
	        bioDetailsForm.setEmailStatus(contactDao.getEmailVerified(bioDetailsForm.getNumber()));
	        /* Read student qualification*/
	    	bioDetailsForm.setQual(dao.getQualification(bioDetailsForm.getNumber()));
	    	// email updatable from web
	    	if(op.getOutWsStudentMkLastAcademicYear()<2009){
	    		bioDetailsForm.setEmailUpdatableFromWeb(true);
	    	}

	    }
        if (!messages.isEmpty()) {
        	addErrors(request, messages);
        }

    	eventTrackingService.post(
    				eventTrackingService.newEvent(EventTrackingTypes.EVENT_BIODETAILS_VIEW, toolManager.getCurrentPlacement().getContext()+"/"+bioDetailsForm.getNumber(), false),usageSession);

    	if("1015".equals(bioDetailsForm.getCountryCode())){
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
			
			for (int i = 0; i < bioDetailsForm.getCourier().size(); i++) {
				if (i == 0 && bioDetailsForm.getCourier().get(i).toString().trim() != null){
					cAddress1 = bioDetailsForm.getCourier().get(i).toString().trim();
					isFirstSubCheck = dao.isLineSub(cAddress1);
				}else if (i == 1 && bioDetailsForm.getCourier().get(i).toString().trim() != null){
					cAddress2 = bioDetailsForm.getCourier().get(i).toString().trim();
				}else if (i == 2 && bioDetailsForm.getCourier().get(i).toString().trim() != null){
					cAddress3 = bioDetailsForm.getCourier().get(i).toString().trim();
				}else if (i == 3 && bioDetailsForm.getCourier().get(i).toString().trim() != null){
					cAddress4 = bioDetailsForm.getCourier().get(i).toString().trim();
				}else if (i == 4 && bioDetailsForm.getCourier().get(i).toString().trim() != null){
					cAddress5 = bioDetailsForm.getCourier().get(i).toString().trim();
				}else if (i == 5 && bioDetailsForm.getCourier().get(i).toString().trim() != null){
					cAddress6 = bioDetailsForm.getCourier().get(i).toString().trim();
				}
			}
			if(cAddress1 != null && !"".equalsIgnoreCase(cAddress1)){
				if(cAddress1.equalsIgnoreCase(cAddress2) || cAddress1.equalsIgnoreCase(cAddress3) || cAddress1.equalsIgnoreCase(cAddress4) || cAddress1.equalsIgnoreCase(cAddress5) || cAddress1.equalsIgnoreCase(cAddress6)){
					isDoubleSubCheck = true;
					//log.debug("BioDetailsAction - Courier - Student Courier Address - isDoubleSubCheck="+isDoubleSubCheck+", Check: 1=" + cAddress1+", 2="+ cAddress2+", 3="+ cAddress3+", 4="+ cAddress4+", 5="+ cAddress5+", 6="+ cAddress6);
				}
			}
			if(cAddress2 != null && !"".equalsIgnoreCase(cAddress2)){
				if(cAddress2.equalsIgnoreCase(cAddress3) || cAddress2.equalsIgnoreCase(cAddress4) || cAddress2.equalsIgnoreCase(cAddress5) || cAddress2.equalsIgnoreCase(cAddress6)){
					isDoubleSubCheck = true;
					//log.debug("BioDetailsAction - Courier - Student Courier Address - isDoubleSubCheck="+isDoubleSubCheck+", Check: 2="+ cAddress2+", 3="+ cAddress3+", 4="+ cAddress4+", 5="+ cAddress5+", 6="+ cAddress6);
				}
			}
			if(cAddress3 != null && !"".equalsIgnoreCase(cAddress3)){
				if(cAddress3.equalsIgnoreCase(cAddress4) || cAddress3.equalsIgnoreCase(cAddress5) || cAddress3.equalsIgnoreCase(cAddress6)){
					isDoubleSubCheck = true;
					//log.debug("BioDetailsAction - Courier - Student Courier Address - isDoubleSubCheck="+isDoubleSubCheck+", Check: 3="+ cAddress3+", 4="+ cAddress4+", 5="+ cAddress5+", 6="+ cAddress6);
				}
			}
			if(cAddress4 != null && !"".equalsIgnoreCase(cAddress4)){
				if(cAddress4.equalsIgnoreCase(cAddress5) || cAddress4.equalsIgnoreCase(cAddress6)){
					isDoubleSubCheck = true;
					//log.debug("BioDetailsAction - Courier - Student Courier Address - isDoubleSubCheck="+isDoubleSubCheck+", Check: 4="+ cAddress4+", 5="+ cAddress5+", 6="+ cAddress6);
				}
			}
			if(cAddress5 != null && !"".equalsIgnoreCase(cAddress5)){
				if(cAddress5.equalsIgnoreCase(cAddress6)){
					isDoubleSubCheck = true;
					//log.debug("BioDetailsAction - Courier - Student Courier Address - isDoubleSubCheck="+isDoubleSubCheck+", Check: 5="+ cAddress5+", 6="+ cAddress6);
				}
			}
			if(isFirstSubCheck){
				//log.debug("AddressDetailsAction - Courier - Student Courier Address  - isFirstSub: " + isFirstSubCheck);
				messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Courier address: The first line may not be a suburb or town. Please include a street name or additional information for the courier delivery."));
				addErrors(request, messages);
				return mapping.findForward("display");
			}
			if(isDoubleSubCheck){
				//log.debug("BioDetailsAction - Courier - Student Courier Address  - isDoubleSub: " + isDoubleSubCheck);
				messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "You have a duplication in your courier address. Please go to Biographical Details or click on &lsquo;Change Address/Contact Details&rsquo; to update your courier address before continuing."));
				addErrors(request, messages);
				return mapping.findForward("display");
			}
		}
    	
		return mapping.findForward("display");
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
	
	/**
	 * This method returns to the tool that initiated the Biographical details call
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward nextStep(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		BioDetailsForm bioDetailsForm = (BioDetailsForm) form;
		String originatedFrom =  bioDetailsForm.getOriginatedFrom();
		
		if (originatedFrom != null && 
			"unisa.regdetails".equalsIgnoreCase(originatedFrom))
		{
			String serverpath = ServerConfigurationService.getServerUrl();
			return new ActionForward(serverpath+"/unisa-findtool/default.do?sharedTool=unisa.regdetails&returnedFrom=unisa.biodetails",true);
		}
		
		return mapping.findForward("home");
	}
	
	//Reset search fields
	private void resetAll(BioDetailsForm bioDetailsForm) throws Exception {
		
		log.debug("BioDetailsAction - resetSearchFields");
		
		bioDetailsForm.setPostalType("");
		bioDetailsForm.setAddressType("");
		bioDetailsForm.setSearchType("");
		bioDetailsForm.setSearchSuburb("");
		bioDetailsForm.setSearchPostalCode("");
		bioDetailsForm.setSearchResult("");
		bioDetailsForm.setPostalLine1("");
		bioDetailsForm.setPostalLine2("");
		bioDetailsForm.setPostalLine3("");
		bioDetailsForm.setPostalLine4("");
		bioDetailsForm.setPostalLine5("");
		bioDetailsForm.setPostalLine6("");
		bioDetailsForm.setPostalSuburb("");
		bioDetailsForm.setPostalCode("");
		bioDetailsForm.setPostalStreetLine1("");
		bioDetailsForm.setPostalStreetLine2("");
		bioDetailsForm.setPostalStreetLine3("");
		bioDetailsForm.setPostalStreetLine4("");
		bioDetailsForm.setPostalStreetLine5("");
		bioDetailsForm.setPostalStreetLine6("");
		bioDetailsForm.setPostalStreetSuburb("");
		bioDetailsForm.setPostalStreetCode("");
		bioDetailsForm.setPhysicalLine1("");
		bioDetailsForm.setPhysicalLine2("");
		bioDetailsForm.setPhysicalLine3("");
		bioDetailsForm.setPhysicalLine4("");
		bioDetailsForm.setPhysicalLine5("");
		bioDetailsForm.setPhysicalLine6("");
		bioDetailsForm.setPhysicalSuburb("");
		bioDetailsForm.setPhysicalPostalCode("");
		bioDetailsForm.setCourierLine1("");
		bioDetailsForm.setCourierLine2("");
		bioDetailsForm.setCourierLine3("");
		bioDetailsForm.setCourierLine4("");
		bioDetailsForm.setCourierLine5("");
		bioDetailsForm.setCourierLine6("");
		bioDetailsForm.setCourierSuburb("");
		bioDetailsForm.setCourierPostalCode("");
		bioDetailsForm.setContactNr("");
		
		bioDetailsForm.setAdvisorPostalLine1("");
		bioDetailsForm.setAdvisorPostalLine2("");
		bioDetailsForm.setAdvisorPostalLine3("");
		bioDetailsForm.setAdvisorPostalLine4("");
		bioDetailsForm.setAdvisorPostalLine5("");
		bioDetailsForm.setAdvisorPostalLine6("");
		bioDetailsForm.setAdvisorPostalSuburb("");
		bioDetailsForm.setAdvisorPostalCode("");
		bioDetailsForm.setAdvisorPhysicalLine1("");
		bioDetailsForm.setAdvisorPhysicalLine2("");
		bioDetailsForm.setAdvisorPhysicalLine3("");
		bioDetailsForm.setAdvisorPhysicalLine4("");
		bioDetailsForm.setAdvisorPhysicalLine5("");
		bioDetailsForm.setAdvisorPhysicalLine6("");
		bioDetailsForm.setAdvisorPhysicalSuburb("");
		bioDetailsForm.setAdvisorPhysicalPostalCode("");
		bioDetailsForm.setAdvisorCourierLine1("");
		bioDetailsForm.setAdvisorCourierLine2("");
		bioDetailsForm.setAdvisorCourierLine3("");
		bioDetailsForm.setAdvisorCourierLine4("");
		bioDetailsForm.setAdvisorCourierLine5("");
		bioDetailsForm.setAdvisorCourierLine6("");
		bioDetailsForm.setAdvisorCourierSuburb("");
		bioDetailsForm.setAdvisorCourierPostalCode("");
		bioDetailsForm.setAdvisorContactNr("");
	}
	
	public ActionForward unspecified(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response){

		log.info("BioDetailsAction: unspecified method call -no value for parameter in request");

		return mapping.findForward("home");

	}
}

