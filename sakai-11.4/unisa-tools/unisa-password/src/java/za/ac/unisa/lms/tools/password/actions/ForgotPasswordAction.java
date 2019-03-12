//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.password.actions;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.naming.OperationNotSupportedException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.LookupDispatchAction;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.email.api.EmailService;
import org.sakaiproject.event.api.UsageSession;
import org.sakaiproject.event.api.EventTrackingService;
import org.sakaiproject.event.api.UsageSessionService;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.tool.api.ToolManager;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserNotDefinedException;
import org.sakaiproject.user.api.UserDirectoryService;

import Srcds01h.Abean.Srcds01sMntStudContactDetail;
import Srsms01h.Abean.Srsms01sSendSingleSms;

import za.ac.unisa.lms.constants.EventTrackingTypes;
/*import za.ac.unisa.lms.providers.api.IdVaultProvisioner;
import za.ac.unisa.lms.providers.api.IdVaultUser;*/
import za.ac.unisa.lms.ad.SaveStudentToAD;
import za.ac.unisa.lms.ad.IdVaultUser;
import za.ac.unisa.lms.tools.join.dao.MyUnisaJoinSakaiDAO;
import za.ac.unisa.lms.tools.password.dao.ForgetPasswordSakaiDAO;
import za.ac.unisa.lms.tools.password.dao.ForgetPasswordStudentSystemDAO;
import za.ac.unisa.lms.tools.password.dao.PasswordDetails;
import za.ac.unisa.lms.tools.password.forms.ForgetPasswordForm;
import za.ac.unisa.lms.tools.password.forms.dateUtil;
import za.ac.unisa.lms.dao.JoinActivationDAO;
import za.ac.unisa.lms.dao.JoinActivationStuDetails;
import za.ac.unisa.lms.dao.IdvaltStudentDetailsDAO;
import za.ac.unisa.lms.dao.IdvaltStudentDetails;
import za.ac.unisa.lms.tools.password.dao.ForgetPasswordStudentSystemDAO;

/**
 * MyEclipse Struts Creation date: 12-28-2005
 *
 * XDoclet definition:
 * 
 * @struts:action validate="true"
 */
public class ForgotPasswordAction extends LookupDispatchAction {

	// --------------------------------------------------------- Instance Variables
	private SessionManager sessionManager;
	private UserDirectoryService userDirectoryService;
	private EventTrackingService eventTrackingService;
	private ToolManager toolManager;
	private UsageSessionService usageSessionService;
	private EmailService emailService;

	// --------------------------------------------------------- Methods
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response, ActionMapping mapping,
			ActionForm form) throws Exception {
		if (request.getParameter("action") == null)
			return nextstep(mapping, form, request, response);
		return super.execute(mapping, form, request, response);
	}
	
	protected Map getKeyMethodMap() {
		Map map = new HashMap();
		map.put("studentnrstep", "studentnrstep");
		map.put("inputValidate", "inputValidate");
		map.put("button.continue", "nextstep");
		map.put("button.back", "back");
		map.put("button.clear", "clear");
		map.put("button.view", "nextstep");
		map.put("sendForgetPasswordEmail", "sendForgetPasswordEmail");

		return map;
	}

	public ActionForward studentnrstep(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
		ForgetPasswordForm forgetPasswordForm = (ForgetPasswordForm) form;
		forgetPasswordForm.setStudentNr("");
		forgetPasswordForm.setDeliveryOption("");
		forgetPasswordForm.setForeignChoiceDelivery("");
		forgetPasswordForm.seMylifeInitPageTracker(0);

		eventTrackingService.post(eventTrackingService.newEvent(EventTrackingTypes.EVENT_PASSWORD_VIEW, null, false));

		return mapping.findForward("studnostep");
	}

	public ActionForward nextstep(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
		ActionMessages messages = new ActionMessages(); // class that encapsulates messages.
		ForgetPasswordForm forgetPasswordForm = (ForgetPasswordForm) form;
		String gotoStepValidate = "";

		if ("validatestunr".equalsIgnoreCase(request.getParameter("atstep"))) {
			gotoStepValidate = validateStudentNr(mapping, form, request, response);
		}
		if ("personneldetailstep".equalsIgnoreCase(request.getParameter("atstep"))) {
			gotoStepValidate = validatePersonnelDetails(mapping, form, request, response);
		}
		if ("processnewstudent".equalsIgnoreCase(request.getParameter("atstep"))) {
			gotoStepValidate = confirm(mapping, form, request, response);
		}
		if ("updateCellNumber".equalsIgnoreCase(request.getParameter("atstep"))) {
			gotoStepValidate = processNoCellStudent(mapping, form, request, response);
		}
		if ("validateEmail".equalsIgnoreCase(request.getParameter("atstep"))) {
			gotoStepValidate = validateEmail(mapping, form, request, response);
		}

		return mapping.findForward(gotoStepValidate);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String validateStudentNr(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// class that encapsulates messages.
		ActionMessages messages = new ActionMessages();
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
		toolManager = (ToolManager) ComponentManager.get(ToolManager.class);
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
		UsageSession usageSession = usageSessionService.getSession();

		ForgetPasswordForm forgetPasswordForm = (ForgetPasswordForm) form;
		ForgetPasswordSakaiDAO forgetPasswordSakaiDAO = new ForgetPasswordSakaiDAO();
		/*
		 * joinActivationDAO is from shared classes used to validate the student
		 * activation status
		 */
		JoinActivationDAO joinActivationDAO = new JoinActivationDAO();

		String regex = "^0*";
		String studentNr = forgetPasswordForm.getStudentNr().replaceAll(regex, "");
		forgetPasswordForm.setStudentNr(studentNr);
		JoinActivationStuDetails joinActivationStuDetails = new JoinActivationStuDetails();
		// IdvaltStudentDetailsDAO is from shared classes to validate student
		// registration details
		IdvaltStudentDetailsDAO idvaltStudentDetailsDAO = new IdvaltStudentDetailsDAO();
		IdvaltStudentDetails studentDetails = new IdvaltStudentDetails();

		String helpdeskEmail = ServerConfigurationService.getString("helpdeskEmail");
		// Validate Student number -Student number must be entered
		if (forgetPasswordForm.getStudentNr().length() == 0) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please enter the student number"));
			addErrors(request, messages);
			return ("studnostep");
		}

		// Student number must be numeric
		try {
			int x = Integer.parseInt(forgetPasswordForm.getStudentNr());
		} catch (NumberFormatException nFE) {
			log.error(this + ": " + forgetPasswordForm.getStudentNr() + " is not numeric.");
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student number must be numeric"));
			addErrors(request, messages);
			return ("studnostep");
		}

		studentDetails = idvaltStudentDetailsDAO.getStudentDetails(forgetPasswordForm.getStudentNr());

		// validate Student number against data base
		try {
			if (studentDetails.getStudentNr().equals("")) {
				log.info(this + ": " + forgetPasswordForm.getStudentNr() + " not valid student number ");
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.generalmessage",
						"Student number entered is not valid. Please enter a valid student number. "));
				addErrors(request, messages);
				eventTrackingService.post(eventTrackingService.newEvent(EventTrackingTypes.EVENT_PASSWORD_ERROR,
						"Student nr not valid", false), usageSession);
				return ("studnostep");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		joinActivationStuDetails = joinActivationDAO.getStudentDetails(forgetPasswordForm.getStudentNr());
		forgetPasswordForm.setMyLifeActiveStatus(joinActivationStuDetails.getMylife_act_status());
		// check student is active- claimed unisa login
		boolean studentClaimedStatus = false;
		if (joinActivationStuDetails.getAct_status().equals("Y")) {
			studentClaimedStatus = true;
		} else {
			studentClaimedStatus = false;
		}

		try {
			if (studentClaimedStatus == false) {
				log.info(this + ": " + forgetPasswordForm.getStudentNr() + " is not claimed unisa login ");
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.generalmessage",
						"You do not have an active UNISA account. Please claim UNISA Login, by clicking on the appropriate link on the navigation menu."));
				addErrors(request, messages);
				eventTrackingService.post(eventTrackingService.newEvent(EventTrackingTypes.EVENT_PASSWORD_ERROR,
						"Student myLife account not created", false), usageSession);
				return ("studnostep");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// select the student blocked status
		if (joinActivationStuDetails.getStatus_flag().equals("BL")) {
			log.info(this + ": " + forgetPasswordForm.getStudentNr() + " is blocked.");
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.generalmessage",
					"A new myUnisa password cannot be provided for this student number. "));
			addErrors(request, messages);
			eventTrackingService.post(
					eventTrackingService.newEvent(EventTrackingTypes.EVENT_PASSWORD_ERROR, "Student is blocked", false),
					usageSession);
			return ("studnostep");
		}

		// passwordDetails gives the student last accessed password details
		PasswordDetails previousPasswordRequest = new PasswordDetails();
		previousPasswordRequest = forgetPasswordSakaiDAO.getPasswordDetails(studentNr);
		String lastRequestedTime = previousPasswordRequest.getRequestDate();
		if (previousPasswordRequest.getChangePwd().equals("")) {
			forgetPasswordForm.setPasswordChnaged(false);
		} else if (previousPasswordRequest.getChangePwd().equals("Y")) {
			forgetPasswordForm.setPasswordChnaged(true);
		} else {
			forgetPasswordForm.setPasswordChnaged(false);
		}

		/* student not allowed to request the password less than an hour */
		boolean ckeckLastRequested = getLastRequestedTime(forgetPasswordForm.getStudentNr(), lastRequestedTime);
		if (ckeckLastRequested == false) {
			log.info(this + ": " + forgetPasswordForm.getStudentNr() + " trying password in less than an hour.");
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.generalmessage",
					"You requested a new Unisa password less than an hour ago. Please wait a while to receive your SMS or try again later."));
			addErrors(request, messages);
			eventTrackingService.post(eventTrackingService.newEvent(EventTrackingTypes.EVENT_PASSWORD_ERROR,
					"Student trying password in less than an hour", false), usageSession);
			return ("studnostep");
		}

		forgetPasswordForm.setPassword(studentDetails.getPassword());
		forgetPasswordForm.setCellNum(studentDetails.getCellNumber());
		forgetPasswordForm.setEmail(studentDetails.getEmailaddress());// this is ADRPH email address

		return ("personneldetailstep");
	}

	public String validatePersonnelDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionMessages messages = new ActionMessages();
		ForgetPasswordForm forgetPasswordForm = (ForgetPasswordForm) form;
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
		UsageSession usageSession = usageSessionService.startSession(forgetPasswordForm.getStudentNr(),
				request.getRemoteAddr(), request.getHeader("user-agent"));
		request.getSession().setAttribute("UsageSession", usageSession);
		String helpdeskEmail = ServerConfigurationService.getString("helpdeskEmail");
		String studentNr = forgetPasswordForm.getStudentNr();
		String studentCountryCode = "";
		/**
		 * Call validate.xml to validate the input
		 */
		messages = (ActionMessages) forgetPasswordForm.validate(mapping, request);
		if (!messages.isEmpty()) {
			addErrors(request, messages);
			return ("personneldetailstep");
		} else {// end if (!messages.isEmpty())

			/*
			 * *********************************************************************
			 * Validate that id number or passport number was entered
			 * *********************************************************************
			 */
			String enteredIdnr = "";
			if (forgetPasswordForm.getId_nr().length() > 0) {
				enteredIdnr = ltrim(forgetPasswordForm.getId_nr());
				enteredIdnr = rtrim(enteredIdnr);
			}
			String enteredPassport = "";
			if (forgetPasswordForm.getPassport_nr().length() > 0) {
				enteredPassport = ltrim(forgetPasswordForm.getPassport_nr());
				enteredPassport = rtrim(enteredPassport);
			}

			if (((null == enteredIdnr) || (enteredIdnr.length() == 0))
					&& ((null == enteredPassport) || (enteredPassport.length() == 0))) {
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.generalmessage",
						"Id number or passport number must be entered please. "));
				addErrors(request, messages);
				return ("personneldetailstep");
			}

			// This proxy is used to validate the student personnel details
			Srcds01sMntStudContactDetail op = new Srcds01sMntStudContactDetail();
			operListener opl = new operListener();
			op.addExceptionListener(opl);
			op.clear();

			op.setInSecurityWsUserNumber(9999);
			op.setInCsfClientServerCommunicationsClientVersionNumber((short) 3);
			op.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
			op.setInCsfClientServerCommunicationsAction("D");
			op.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
			op.setInWsStudentNr(Integer.parseInt(forgetPasswordForm.getStudentNr()));
			op.execute();

			String Errormsg = op.getOutCsfStringsString500();
			if ((Errormsg != null) && (!Errormsg.equals(""))) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("error.coolgenerror", "Data Access Error"));
				addErrors(request, messages);

				return ("personneldetailstep");
			}
			// tmp variables to select students Personal information from student database
			// (proxt)
			String surnameInRecords = "";
			String fullNamesInRecords = "";
			String dateOfBirthInRecords = "";
			String idNumberInRecords = "";
			String passportNumberInRecords = "";

			// set the birth date
			forgetPasswordForm.setDateOfBirth(forgetPasswordForm.getBirthYear() + "-"
					+ forgetPasswordForm.getBirthMonth() + "-" + forgetPasswordForm.getBirthDay());

			surnameInRecords = ltrim(op.getOutWsStudentSurname());
			surnameInRecords = rtrim(surnameInRecords);
			fullNamesInRecords = ltrim(op.getOutWsStudentFirstNames());
			fullNamesInRecords = rtrim(fullNamesInRecords);
			idNumberInRecords = ltrim(op.getOutWsStudentIdentityNr());
			idNumberInRecords = rtrim(idNumberInRecords);
			passportNumberInRecords = ltrim(op.getOutWsStudentPassportNo());
			passportNumberInRecords = rtrim(passportNumberInRecords);
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			dateOfBirthInRecords = dateFormat.format(op.getOutWsStudentBirthDate().getTime());
			String enteredSurname = ltrim(forgetPasswordForm.getSurname());
			enteredSurname = rtrim(enteredSurname);
			String enteredFullNames = ltrim(forgetPasswordForm.getFullNames());
			enteredFullNames = rtrim(enteredFullNames);

			eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);

			if (!enteredSurname.equalsIgnoreCase(surnameInRecords)) {
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.generalmessage",
						"The surname you entered does not correspond with our records. Please check and try again."
								+ " If you are convinced you have entered the correct information, please contact the <A HREF=\"mailto:"
								+ helpdeskEmail
								+ "\">myUnisaHelp@unisa.ac.za</A> to have your student records checked."));
				addErrors(request, messages);
				log.info(this + ": " + forgetPasswordForm.getStudentNr() + " surname not matches");
				eventTrackingService.post(eventTrackingService.newEvent(EventTrackingTypes.EVENT_PASSWORD_ERROR,
						"Surname doesn't match (User: " + enteredSurname.toUpperCase() + ", System: " + surnameInRecords
								+ ")",
						false), usageSession);

				return ("personneldetailstep");
			}
			if (!enteredFullNames.equalsIgnoreCase(fullNamesInRecords)) {
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.generalmessage",
						"The full names you entered do not correspond with our records. Please check and try again."
								+ " If you are convinced you have entered the correct information, please contact the <A HREF=\"mailto:"
								+ helpdeskEmail
								+ "\">myUnisaHelp@unisa.ac.za</A> to have your student records checked."));
				addErrors(request, messages);

				eventTrackingService
						.post(eventTrackingService.newEvent(
								EventTrackingTypes.EVENT_PASSWORD_ERROR, "Full names don't match (User: "
										+ enteredFullNames.toUpperCase() + ", System: " + fullNamesInRecords + ")",
								false), usageSession);

				return ("personneldetailstep");
			}
			if (!forgetPasswordForm.getDateOfBirth().equalsIgnoreCase(dateOfBirthInRecords)) {
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.generalmessage",
						"The date of birth you entered does not correspond with our records. Please check and try again."
								+ " If you are convinced you have entered the correct information, please contact the <A HREF=\"mailto:"
								+ helpdeskEmail
								+ "\">myUnisaHelp@unisa.ac.za</A> to have your student records checked."));
				addErrors(request, messages);

				log.info(this + ": " + forgetPasswordForm.getStudentNr() + " date of birth not matches");
				eventTrackingService.post(eventTrackingService.newEvent(
						EventTrackingTypes.EVENT_PASSWORD_ERROR, "Date of birth doesn't match (User: "
								+ forgetPasswordForm.getDateOfBirth() + ", System: " + dateOfBirthInRecords + ")",
						false), usageSession);
				return ("personneldetailstep");
			}
			if ((enteredIdnr.length() > 0) && (enteredPassport.length() > 0)) {
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.generalmessage",
						"Enter Id number or passport number, But not the both "));
				addErrors(request, messages);
				return ("personneldetailstep");
			}

			if ((enteredIdnr.length() > 0) && (!enteredIdnr.equalsIgnoreCase(idNumberInRecords))) {
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.generalmessage",
						"The Id number you entered does not correspond with our records. Please check and try again."
								+ " If you are convinced you have entered the correct information, please contact the <A HREF=\"mailto:"
								+ helpdeskEmail
								+ "\">myUnisaHelp@unisa.ac.za</A> to have your student records checked."));
				addErrors(request, messages);

				eventTrackingService.post(eventTrackingService.newEvent(EventTrackingTypes.EVENT_PASSWORD_ERROR,
						"Id number doesn't match (User: " + enteredIdnr.toUpperCase() + ", System: " + idNumberInRecords
								+ ")",
						false), usageSession);
				return ("personneldetailstep");
			}
			if ((enteredPassport.length() > 0) && (!enteredPassport.equalsIgnoreCase(passportNumberInRecords))) {
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.generalmessage",
						"The passport number you entered does not correspond with our records. Please check and try again."
								+ " If you are convinced you have entered the correct information, please contact the <A HREF=\"mailto:"
								+ helpdeskEmail
								+ "\">myUnisaHelp@unisa.ac.za</A> to have your student records checked."));
				addErrors(request, messages);

				eventTrackingService
						.post(eventTrackingService.newEvent(
								EventTrackingTypes.EVENT_PASSWORD_ERROR, "Passport number doesn't match (User: "
										+ enteredPassport.toUpperCase() + ", System: " + passportNumberInRecords + ")",
								false), usageSession);
				return ("personneldetailstep");

			}

			eventTrackingService.post(eventTrackingService.newEvent(EventTrackingTypes.EVENT_PASSWORD_PERSDETAILSTEP,
					toolManager.getCurrentPlacement().getContext(), false), usageSession);

			forgetPasswordForm.setMylifePath(ServerConfigurationService.getString("mylife.path"));
			forgetPasswordForm.setMyUnisaPath(ServerConfigurationService.getString("serverUrl"));
			/*
			 * if the student cell number in the records is empty then student have to send
			 * a request to contact centre to update his cell number
			 */
			if (forgetPasswordForm.getCellNum().equals("")) {
				return ("updateCellNumber");
			}

			ForgetPasswordStudentSystemDAO forgetPasswordStudentSystemDAO = new ForgetPasswordStudentSystemDAO();
			studentCountryCode = forgetPasswordStudentSystemDAO.getStudentCountryCode(studentNr);
			forgetPasswordForm.setForeign(false);

			// if the student is foreign student give an option to enter the email address
			// to get his new password
			if (!studentCountryCode.trim().equals("1015")) {
				forgetPasswordForm.setForeign(true);
			}
			forgetPasswordStudentSystemDAO = null;

			return procecssMyUnisaRequest(forgetPasswordForm, studentNr, request, usageSession);
		}
	}

	// validate email
	public String validateEmail(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		boolean isValidate = false;
		ActionMessages messages = new ActionMessages();
		ForgetPasswordForm forgetPasswordForm = (ForgetPasswordForm) form;
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
		UsageSession usageSession = usageSessionService.startSession(forgetPasswordForm.getStudentNr(),
				request.getRemoteAddr(), request.getHeader("user-agent"));
		request.getSession().setAttribute("UsageSession", usageSession);
		String stuNum = forgetPasswordForm.getStudentNr();
		String emailAddressEntered = null;

		try {
			if ("email".equalsIgnoreCase(forgetPasswordForm.getForeignChoiceDelivery())) {
				emailAddressEntered = forgetPasswordForm.getPersonalEmail();
				forgetPasswordForm.setIsEmail(true);

				EmailFormatValidator emailValidate = new EmailFormatValidator();
				isValidate = emailValidate.validate(emailAddressEntered);
				emailValidate = null;

				if (isValidate && forgetPasswordForm.getForeignChoiceDelivery().length() > 0) {
					forgetPasswordForm.setEmail(emailAddressEntered);

				} else {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Please enter the valid email address"));
					addErrors(request, messages);
					return ("emailNotValid");
				}

			} else {
				forgetPasswordForm.setIsEmail(false);
			}
			return "confirm";
		} catch (Exception ex) {

			return "emailNotValid";

		}
	}

	private String procecssMyUnisaRequest(ForgetPasswordForm forgetPasswordForm, String stuNum,
			HttpServletRequest request, UsageSession usageSession) throws Exception {
		/*
		 * The students who joined before 17 Jan 2013 are old students. Implementation
		 * date will differentiates the two processes, before the implementation student
		 * can not see the initial password and after the implementation of the new
		 * claim and forgot password we are checking the initial password changed field
		 * The new implementation does not deletes the Unisa_password records and no
		 * activation email sent
		 */
		ForgetPasswordSakaiDAO dao = new ForgetPasswordSakaiDAO();
		ForgetPasswordStudentSystemDAO stuDao = new ForgetPasswordStudentSystemDAO();
		ActionMessages messages = new ActionMessages();
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
		userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
		if (isOldStudent(Integer.parseInt(stuNum))) {
			/*
			 * if the student registered before 2009 (before myLife email process
			 * implementation) have no myLife email address and he can still use myUnisa
			 * account
			 */
			if (stuDao.getStudentLastAcadYear(stuNum) < 2009) {
				return forceNewPassword(forgetPasswordForm, stuNum);
			}
			// the student academic year from 2009 should have the active myLife email
			// account
			if (forgetPasswordForm.getMyLifeActiveStatus().equals("Y")) {
				if (forgetPasswordForm.getEmail().length() > 0) {
					return forceNewPassword(forgetPasswordForm, stuNum);
				} else {
					// student must have email address in Adrph table to get the new password
					return emailError(forgetPasswordForm, request, usageSession);
				}
			} else {
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.generalmessage",
						"You have not claimed your UNISA Login yet. Please click on the appropriate link on the navigation menu to claim your Login."));
				addErrors(request, messages);
				return ("studnostep");	
			}
		} else {
			boolean initialPasswordChanged = forgetPasswordForm.isPasswordChnaged();
			/*
			 * if the student initial password changed then generate the new password else
			 * display the initial password on the screen
			 */
			if (initialPasswordChanged == true) {
				if (forgetPasswordForm.getEmail().length() > 0) {
					return forceNewPassword(forgetPasswordForm, stuNum);
				} else {
					return emailError(forgetPasswordForm, request, usageSession);
				}
			} else {
				eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
				toolManager = (ToolManager) ComponentManager.get(ToolManager.class);
				usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);

				eventTrackingService.post(eventTrackingService.newEvent(EventTrackingTypes.EVENT_PASSWORD_MYLIFEPWDVIEW,
						"Initial Unisa pwd viewed", false), usageSession);
				// Re-save the student again to AD
				log.info(this + " FORGOT PASSWORD: re-saving the initial password "+ forgetPasswordForm.getPassword()+" for the student number " + stuNum);
				User user = null;
				try {
					// Update the student details to Active directory
					user = userDirectoryService.getUserByEid(stuNum);
					SaveStudentToAD saveStudentToAD = new SaveStudentToAD();
					IdVaultUser idVaultuser = new IdVaultUser();
					// set all variables in class idVaultUser.
					idVaultuser.setId(user.getEid());
					idVaultuser.setEmail(user.getEmail());
					idVaultuser.setFirstName(user.getFirstName());
					idVaultuser.setLastName(user.getLastName());
					idVaultuser.setType(user.getType());
					idVaultuser.setPassword(forgetPasswordForm.getPassword());
					
					try {
						// if(!((IdVaultProvisioner)
						// ComponentManager.get("org.sakaiproject.user.api.UserDirectoryProvider")).saveUser(idVaultuser))
						// {
						if (!saveStudentToAD.saveUser(idVaultuser)) {
							log.error(this + " procecssMyUnisaRequest: Re-save the password to AD for   "
									+ forgetPasswordForm.getStudentNr() + " failed ");
							eventTrackingService
									.post(eventTrackingService.newEvent(EventTrackingTypes.EVENT_PASSWORD_ERROR,
											"Save to provider failed", false), usageSession);
							messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.generalmessage",
									"Unfortunately we could not reset your password, please try again."));
							addErrors(request, messages);
							return ("studnostep");
						}
						log.info(this + " FORGOT PASSWORD: re-saved the old password by student " + stuNum);
					} catch (OperationNotSupportedException e) {
						log.error(this + " FORGOT PASSWORD: (OperationNotSupportedException) (" + stuNum + ") " + e);
						return ("studnostep");
					} catch (Exception e) {
						log.error(this + ": FORGOT PASSWORD: Unhandled exception (" + e + "): " + e.getMessage());
						return ("studnostep");
					}
				} catch (UserNotDefinedException e) {
					log.error(
							" Error occured on action=ForgotPasswordAction; method=confirm (UserNotDefinedException): set of password"
									+ e);
				}
				log.info(this + " FORGOT PASSWORD: re-saved the password "+ forgetPasswordForm.getPassword()+" for the student number " + stuNum);
				sendSms(forgetPasswordForm, forgetPasswordForm.getPassword(), request);
				eventTrackingService
						.post(eventTrackingService.newEvent("forgotpassword.send.sms", "SMS Unisa pwd to cell", false));

				return ("passwordToOldStudents");
			}
		}
	}

	private String forceNewPassword(ForgetPasswordForm forgetPasswordForm, String stuNum) throws Exception {
		forgetPasswordForm.getCellNum();
		forgetPasswordForm.setMyLifemail(forgetPasswordForm.getEmail());

		if (forgetPasswordForm.getIsForeign()) {
			return ("processforeignStudent");
		} else {
			return ("processNewStudent"); // deliverymethod
		}
	}

	private String emailError(ForgetPasswordForm forgetPasswordForm, HttpServletRequest request,
			UsageSession usageSession) {

		try {
			ActionMessages messages = new ActionMessages();
			log.info(this + ": " + forgetPasswordForm.getStudentNr() + " email is not exist.");
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.generalmessage",
					"A new Unisa password cannot be provided for this student number. E-mail myUnisaHelp@unisa.ac.za with all your details and request assistance with your email address."));
			addErrors(request, messages);
			eventTrackingService.post(eventTrackingService.newEvent(EventTrackingTypes.EVENT_PASSWORD_ERROR,
					"Student email not exists", false), usageSession);

		} catch (Exception e) {
			e.printStackTrace();
		}

		eventTrackingService.post(eventTrackingService.newEvent(EventTrackingTypes.EVENT_PASSWORD_STUDENTNRSTEP,
				toolManager.getCurrentPlacement().getContext(), false), usageSession);
		return ("studnostep");
	}

	private boolean isOldStudent(int stuno) throws Exception {
		ForgetPasswordSakaiDAO forgetPasswordSakaiDAO = new ForgetPasswordSakaiDAO();
		String date = forgetPasswordSakaiDAO.getJoinDate("" + stuno);
		boolean isOldStu = false;
		if (!date.equals("")) {
			int year = Integer.parseInt(date.substring(0, 4));
			int month = Integer.parseInt(date.substring(5, 7));
			int day = Integer.parseInt(date.substring(8));
			/*
			 * Implementation date will differentiates the two processes, before the
			 * implementation student can not see the initial password and after the
			 * implementation of the new claim and forgot password we are checking the
			 * initial password changed field The new implementation does not deletes the
			 * Unisa_password records and no activation email sent
			 */
			if (year == 2013) {
				if ((month == 1) && (day < 17)) {
					isOldStu = true;
				} else {
					isOldStu = false;
				}
			}
			if (year < 2013) {
				isOldStu = true;
			}
		}
		return isOldStu;
	}

	public String processNoCellStudent(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ForgetPasswordForm forgetPasswordForm = (ForgetPasswordForm) form;
		ActionMessages messages = new ActionMessages();
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
		userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
		UsageSession usageSession = usageSessionService.getSession();
		ForgetPasswordSakaiDAO forgetPasswordSakaiDAO = new ForgetPasswordSakaiDAO();
		SaveStudentToAD saveStudentToAD = new SaveStudentToAD();
		forgetPasswordForm.setConfirm("nocell");

		String selectRequest = forgetPasswordForm.getSelectRequest();
		String message = forgetPasswordForm.getMessage();
		String altContact = forgetPasswordForm.getAltContactDetails();
		if (message.length() == 0) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.generalmessage",
					"Message field may not be empty, please add a message."));
			addErrors(request, messages);
			return ("updateCellNumber");
		}
		if (altContact.length() == 0) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.generalmessage",
					"Contact details field may not be empty, please add a cell number or email address"));
			addErrors(request, messages);
			return ("updateCellNumber");
		}

		// store password on idVault
		String studentNr = forgetPasswordForm.getStudentNr();
		String password = randomPassword();

		User user = null;
		try {
			// get the user
			user = userDirectoryService.getUserByEid(studentNr);
			IdVaultUser idVaultuser = new IdVaultUser();
			// set all variables in class idVaultUser.
			idVaultuser.setId(user.getEid());
			idVaultuser.setEmail(user.getEmail());
			idVaultuser.setFirstName(user.getFirstName());
			idVaultuser.setLastName(user.getLastName());
			idVaultuser.setType(user.getType());
			idVaultuser.setPassword(password);
			log.info(this + " FORGOT PASSWORD: try save new password " + studentNr);
			try {
				// if(!((IdVaultProvisioner)
				// ComponentManager.get("org.sakaiproject.user.api.UserDirectoryProvider")).saveUser(idVaultuser))
				// {
				if (!saveStudentToAD.saveUser(idVaultuser)) {

					log.error(this + " FORGOT PASSWORD:method:updatePassword  " + forgetPasswordForm.getStudentNr()
							+ "was not saved");
					eventTrackingService.post(eventTrackingService.newEvent(EventTrackingTypes.EVENT_PASSWORD_ERROR,
							"Save to provider failed", false), usageSession);

					messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.generalmessage",
							"Unfortunately we could not reset this student password, please try again."));
					addErrors(request, messages);
					return ("studnostep");
				}
			} catch (OperationNotSupportedException e) {
				log.error(this + " FORGOT PASSWORD: (OperationNotSupportedException) (" + studentNr + ") " + e);
				return ("studnostep");
			} catch (Exception e) {
				log.error(this + ": FORGOT PASSWORD: Unhandled exception (" + e + "): " + e.getMessage());
				return ("studnostep");
			}
		} catch (UserNotDefinedException e) {
			log.error(
					" Error occured on action=ForgotPasswordAction; method=updatePassword (UserNotDefinedException): set of password"
							+ e);
		}

		/*
		 * update unisa_password table if student claimed unisa but password not changed
		 * if student claimed unisa and also changed the initial password
		 * 
		 */
		boolean initialPasswordChanged = forgetPasswordForm.isPasswordChnaged();
		if (initialPasswordChanged == true) {
			forgetPasswordSakaiDAO.updatePassword(studentNr);
		}

		try {
			// send an email to contact centre so that they can call the student to assist
			sendUpdateCellNrEmail(forgetPasswordForm.getStudentNr(), selectRequest, message, altContact,
					forgetPasswordForm.getSurname(), forgetPasswordForm.getFullNames(),
					forgetPasswordForm.getDateOfBirth(), forgetPasswordForm.getId_nr(),
					forgetPasswordForm.getPassport_nr(), password);
			eventTrackingService.post(eventTrackingService.newEvent("forgotpassword.send.email",
					"email new UNISA pwd to contact center", false));

		} catch (Exception e) {
			log.error(
					"Error occured on action=ForgotPasswordAction; method=processNoCellStudent- send email failed" + e);
		}
		return "confirm";
	}

	public String confirm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ForgetPasswordForm forgetPasswordForm = (ForgetPasswordForm) form;
		String studentNr = forgetPasswordForm.getStudentNr();
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
		userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
		UsageSession usageSession = usageSessionService.getSession();

		ActionMessages messages = new ActionMessages(); // class that encapsulates messages.

		if (forgetPasswordForm.getIsForeign()) {
			if (forgetPasswordForm.getForeignChoiceDelivery().length() == 0) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Please choose the delevery option "));
				addErrors(request, messages);
				return ("processforeignStudent");
			}

			if ("emailNotValid".equalsIgnoreCase(validateEmail(mapping, form, request, response))) {
				return ("processforeignStudent");
			}
		}

		forgetPasswordForm.setMylifePath(ServerConfigurationService.getString("mylife.path"));
		forgetPasswordForm.setMyUnisaPath(ServerConfigurationService.getString("serverUrl"));
		forgetPasswordForm.setConfirm("confirm");

		// Generate the random password
		String password = randomPassword();
		forgetPasswordForm.setPassword(password);

		String studentnr = forgetPasswordForm.getStudentNr();
		User user = null;
		ForgetPasswordSakaiDAO forgetPasswordSakaiDAO = new ForgetPasswordSakaiDAO();
		SaveStudentToAD saveStudentToAD = new SaveStudentToAD();

		boolean mayChange = false;
		// store password on idVaultuser that active directory service
		try {
			// Update the student details to Active directory
			user = userDirectoryService.getUserByEid(studentnr);
			IdVaultUser idVaultuser = new IdVaultUser();
			// set all variables in class idVaultUser.
			idVaultuser.setId(user.getEid());
			idVaultuser.setEmail(user.getEmail());
			idVaultuser.setFirstName(user.getFirstName());
			idVaultuser.setLastName(user.getLastName());
			idVaultuser.setType(user.getType());
			idVaultuser.setPassword(password);
			log.info(this + " FORGOT PASSWORD: try save new password " + studentnr);
			try {
				// if(!((IdVaultProvisioner)
				// ComponentManager.get("org.sakaiproject.user.api.UserDirectoryProvider")).saveUser(idVaultuser))
				// {
				if (!saveStudentToAD.saveUser(idVaultuser)) {
					log.error(this + " FORGOT PASSWORD:method:updatePassword  " + forgetPasswordForm.getStudentNr()
							+ "was not saved");
					eventTrackingService.post(eventTrackingService.newEvent(EventTrackingTypes.EVENT_PASSWORD_ERROR,
							"Save to provider failed", false), usageSession);
					messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.generalmessage",
							"Unfortunately we could not reset your password, please try again."));
					addErrors(request, messages);
					return ("studnostep");
				}
			} catch (OperationNotSupportedException e) {
				log.error(this + " FORGOT PASSWORD: (OperationNotSupportedException) (" + studentnr + ") " + e);
				return ("studnostep");
			} catch (Exception e) {
				log.error(this + ": FORGOT PASSWORD: Unhandled exception (" + e + "): " + e.getMessage());
				return ("studnostep");
			}
		} catch (UserNotDefinedException e) {
			log.error(
					" Error occured on action=ForgotPasswordAction; method=confirm (UserNotDefinedException): set of password"
							+ e);
		}

		try {
			// if the student changed the initial password using change password on student
			// workspace, update the unisa_password table
			String emailAddress = forgetPasswordForm.getEmail();
			boolean initialPasswordChanged = forgetPasswordForm.isPasswordChnaged();
			if (initialPasswordChanged == true) {
				forgetPasswordSakaiDAO.updatePassword(studentNr);
			}

			if (forgetPasswordForm.getIsForeign()) {
				if (forgetPasswordForm.getForeignChoiceDelivery().equals("EMAIL")) {
					sendForgetPasswordEmail(studentNr, emailAddress, password);
				} else {
					sendSms(forgetPasswordForm, password, request);
				}
			} else {
				sendSms(forgetPasswordForm, password, request);
				// Send an back up email to student to the mylife email address
				sendForgetPasswordEmail(studentNr, emailAddress, password);
			}
		} catch (Exception e) {
			log.error(" Error occured on action=ForgotPasswordAction; method=updatePassword(): Sending SMS/email failed"
					+ e);
		}
		return "confirm";
	}

	public ActionForward back(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionMessages messages = new ActionMessages();
		ForgetPasswordForm forgetPasswordForm = (ForgetPasswordForm) form;
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
		UsageSession usageSession = usageSessionService.startSession(forgetPasswordForm.getStudentNr(),
				request.getRemoteAddr(), request.getHeader("user-agent"));
		request.getSession().setAttribute("UsageSession", usageSession);
		return mapping.findForward("personneldetailstep");
	}

	public ActionForward clear(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionMessages messages = new ActionMessages();
		ForgetPasswordForm forgetPasswordForm = (ForgetPasswordForm) form;
		forgetPasswordForm.setBirthDay("");
		forgetPasswordForm.setBirthMonth("");
		forgetPasswordForm.setBirthYear("");
		forgetPasswordForm.setFullNames("");
		forgetPasswordForm.setSurname("");
		forgetPasswordForm.setDateOfBirth("");
		forgetPasswordForm.setId_nr("");
		forgetPasswordForm.setPassport_nr("");
		return mapping.findForward("personneldetailstep");
	}

	public void sendForgetPasswordEmail(String stno, String emailAddress, String passwrd) throws AddressException {

		emailService = (EmailService) ComponentManager.get(EmailService.class);
		String subject = "Request for New Unisa Account";
		String body = "<html>" + "<body>" + "Dear student <br><br>"
				+ "NB: This is an automated response - do not reply to this e-mail. <br><br>"
				+ "You've received this e-mail in response to having selected the \"Forgotten UNISA Password?\" link and requesting a <b>new Unisa password.</b> <br> <br>"
				+ "Your new myUnisa user account details: <br> " + "Student number : " + stno + "<br>"
				+ "New password : " + passwrd + "<br><br>"
				+ "Your new password is now active on myUnisa AND  on the myLife email account."
				+ "You may change the password by selecting the appropriate link under \"My Admin\" once you have logged in.<br><br>"
				+ "Click here to access the<b> myUnisa learning management system </b>at http://my.unisa.ac.za<br><b>"
				+ "</body>" + "</html>";

		String tmpEmailFrom = ServerConfigurationService.getString("noReplyEmailFrom");
		InternetAddress toEmail = new InternetAddress(emailAddress);
		InternetAddress iaTo[] = new InternetAddress[1];
		iaTo[0] = toEmail;
		InternetAddress iaHeaderTo[] = new InternetAddress[1];
		iaHeaderTo[0] = toEmail;
		InternetAddress iaReplyTo[] = new InternetAddress[1];
		iaReplyTo[0] = new InternetAddress(tmpEmailFrom);
		List contentList = new ArrayList();
		contentList.add("Content-Type: text/html");

		emailService.sendMail(iaReplyTo[0], iaTo, subject, body, iaHeaderTo, iaReplyTo, contentList);

		eventTrackingService.post(eventTrackingService.newEvent("forgotpassword.send.email",
				"Send password to email " + emailAddress, false));
		log.info("sending mail from " + iaReplyTo[0] + " to " + toEmail.toString());
	}

	// end of sendEmail
	public void sendUpdateCellNrEmail(String stno, String selectRequest, String message, String altContact,
			String surName, String fullNames, String dob, String idNr, String passport, String newPassword)
			throws AddressException {

		String emailAddress = "myLifeHelp@unisa.ac.za";
		// String emailAddress="udcsweb@unisa.ac.za";
		emailService = (EmailService) ComponentManager.get(EmailService.class);
		String subject = "Request for New Password for student " + stno;
		String body = "<html>" + "<body>" + "Dear myUnisa support team <br><br>"
				+ "The student below requested a New UNISA Password but there is no Cell number reflecting on our records.<br><br>"
				+ "Student Request:" + selectRequest + "<br>" + "Student Message:" + message + "<br>"
				+ "Student <b>Alternative Contact details</b>:" + altContact + "<br><br>"
				+ "The student personnel details: <br> " + "Student Number : " + stno + "<br>" + "Student Surname : "
				+ surName + "<br>" + "Student Full names : " + fullNames + "<br>" + "Student Date of Birth : " + dob
				+ "<br>" + "Student Id/passport number : " + idNr + " " + passport + "<br> <br>"
				+ "The new Password is <b>" + newPassword + "</b> <br><br>"
				+ "Please contact the student and inform them of their new UNISA password." + "</body>" + "</html>";

		String tmpEmailFrom = ServerConfigurationService.getString("noReplyEmailFrom");
		InternetAddress toEmail = new InternetAddress(emailAddress);
		InternetAddress iaTo[] = new InternetAddress[1];
		iaTo[0] = toEmail;
		InternetAddress iaHeaderTo[] = new InternetAddress[1];
		iaHeaderTo[0] = toEmail;
		InternetAddress iaReplyTo[] = new InternetAddress[1];
		iaReplyTo[0] = new InternetAddress(tmpEmailFrom);
		List contentList = new ArrayList();
		contentList.add("Content-Type: text/html");

		emailService.sendMail(iaReplyTo[0], iaTo, subject, body, iaHeaderTo, iaReplyTo, contentList);
		log.info("sending mail from " + iaReplyTo[0] + " to " + toEmail.toString());
	}
	// end of sendEmail

	public boolean getLastRequestedTime(String studentNr, String lastRequestedTime) throws Exception {
		boolean lessthanhour = true;

		if (lastRequestedTime.length() > 1) {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			String currentTime = dateFormat.format(date);
			Date d1 = null;
			Date d2 = null;
			try {
				d1 = dateFormat.parse(currentTime);
				d2 = dateFormat.parse(lastRequestedTime);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			// Get msec from each, and subtract.
			long diff = d1.getTime() - d2.getTime();
			long diffHours = diff / (60 * 60 * 1000);
			/*
			 * long diffSeconds = diff / 1000 % 60; long diffMinutes = diff / (60 * 1000) %
			 * 60;
			 */
			if (diffHours == 0) {
				lessthanhour = false;
			} else {
				lessthanhour = true;
			}
		}
		return lessthanhour;
	}

	public String randomPassword() throws AddressException {
		String password = ""; /* Stores the letters + digits */
		try {
			String[] pool_lower = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q",
					"r", "s", "t", "u", "v", "w", "x", "y", "z" };
			String[] pool_upper = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q",
					"R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
			String[] pool_numeric = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "0" };

			/** Variables to store the end results */
			String lowerLetters = ""; /* Stores the random letters */
			String digits = ""; /* Stores the random digits */
			String upperLetters = ""; /* Stores the random digits */

			/**
			 * Random number generators randLet is for randomizing the pool array If you
			 * increase the size of either array simply increase the limitation of these
			 * generators
			 */
			int randLet = (int) (Math.random() * 26);
			int randDig = (int) (Math.random() * 10);

			int count1 = 0; /* Counter for the lower case */
			int count2 = 0; /* Counter for the digits */
			int count3 = 0; /* Counter for the upper case */

			while (count1 != 3) {
				randLet = (int) (Math.random() * 26);
				lowerLetters += pool_lower[randLet];
				count1++;
			} // End while
			while (count2 != 2) {
				randDig = (int) (Math.random() * 10);
				digits += pool_numeric[randDig];
				count2++;
			} // End while
			while (count3 != 3) {
				randLet = (int) (Math.random() * 26);
				upperLetters += pool_upper[randLet];
				count3++;
			} // End while
			password = lowerLetters + digits + upperLetters;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return password;

	}

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

	/* remove leading whitespace */
	public static String ltrim(String source) {
		return source.replaceAll("^\\s+", "");
	}

	/* remove trailing whitespace */
	public static String rtrim(String source) {
		return source.replaceAll("\\s+$", "");
	}

	private void sendSms(ForgetPasswordForm forgetPasswordForm, String password, HttpServletRequest request) {
		ForgetPasswordStudentSystemDAO dao = new ForgetPasswordStudentSystemDAO();
		String stuNum = forgetPasswordForm.getStudentNr();
		Srsms01sSendSingleSms op = new Srsms01sSendSingleSms();
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
		UsageSession usageSession = usageSessionService.startSession(stuNum, request.getRemoteAddr(),
				request.getHeader("user-agent"));
		request.getSession().setAttribute("UsageSession", usageSession);
		DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date1 = new Date();
		String date = dateFormat1.format(date1);

		String cellNum = "";
		try {
			cellNum = forgetPasswordForm.getCellNum().trim();
			if (cellNum.length() >= 1) {
				// proxy for single sms

				operListener op1 = new operListener();
				op.addExceptionListener(op1);
				op.clear();

				// op.setInSecurityWsUserNumber(32460503);
				op.setInNovellCodeWsStaffEmailAddress("ARLOWM");
				op.setInSmsQueueMessage("Confirmation: Your initial UNISA password was requested on " + date
						+ ". Your password is " + password);
				op.setInWsSmsToSmsTo("S");
				op.setInCsfClientServerCommunicationsClientVersionNumber((short) 3);
				op.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
				op.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
				op.setInCsfClientServerCommunicationsClientIsGuiFlag("Y");
				op.setInCsfClientServerCommunicationsAction("ML");
				op.setAsStringInSmsQueueReferenceNr(stuNum);
				op.setInSmsQueueCellNr(cellNum);
				op.execute();

				if (op.getOutErrorFlagCsfStringsString1().equals("Y")) {
					eventTrackingService.post(
							eventTrackingService.newEvent("forgotpassword.intialpwd.view",
									"JavaProxy error: " + op.getOutCsfStringsString500() + "(" + cellNum + ")", false),
							usageSession);

				}
			}
		} catch (Exception ex) {
			eventTrackingService.post(eventTrackingService.newEvent("forgotpassword.intialpwd.view",
					"There was an error sending sms : " + op.getOutCsfStringsString500() + "(" + cellNum + ")", false),
					usageSession);

		}
		eventTrackingService
				.post(eventTrackingService.newEvent("forgotpassword.send.sms", "SMS new Unisa pwd to cell", false));
	}

	private void updatePassword(ForgetPasswordForm forgetPasswordForm, String password, HttpServletRequest request) {

		userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
		UsageSession usageSession = usageSessionService.getSession();
		ActionMessages messages = new ActionMessages();

		String tmpStno = forgetPasswordForm.getStudentNr();
		User user = null;
		ForgetPasswordSakaiDAO dao = new ForgetPasswordSakaiDAO();
		SaveStudentToAD saveStudentToAD = new SaveStudentToAD();
		boolean mayChange = false;
		// store password on idVault
		try {
			// get the user
			user = userDirectoryService.getUserByEid(tmpStno);
			IdVaultUser idVaultuser = new IdVaultUser();
			// set all variables in class idVaultUser.
			idVaultuser.setId(user.getEid());
			idVaultuser.setEmail(user.getEmail());
			idVaultuser.setFirstName(user.getFirstName());
			idVaultuser.setLastName(user.getLastName());
			idVaultuser.setType(user.getType());
			idVaultuser.setPassword(password);
			log.error(this + " FORGOT PASSWORD: try save new password " + tmpStno);
			try {
				// if(!((IdVaultProvisioner)
				// ComponentManager.get("org.sakaiproject.user.api.UserDirectoryProvider")).saveUser(idVaultuser))
				// {
				if (!saveStudentToAD.saveUser(idVaultuser)) {
					log.error(this + " FORGOT PASSWORD:method:updatePassword  " + forgetPasswordForm.getStudentNr()
							+ "was not saved");
					messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.generalmessage",
							"Unfortunately your claiming UNISA Login was unsuccessful, please try again."));
					addErrors(request, messages);
					return;
				}
			} catch (OperationNotSupportedException e) {
				log.error(this + " FORGOT PASSWORD: (OperationNotSupportedException) (" + tmpStno + ") " + e);
				return;
			} catch (Exception e) {
				log.error(this + ": FORGOT PASSWORD: Unhandled exception (" + e + "): " + e.getMessage());
				return;
			}
		} catch (UserNotDefinedException e) {
			log.error(
					" Error occured on action=ForgotPasswordAction; method=updatePassword (UserNotDefinedException): set of password"
							+ e);
		}

	}

}
