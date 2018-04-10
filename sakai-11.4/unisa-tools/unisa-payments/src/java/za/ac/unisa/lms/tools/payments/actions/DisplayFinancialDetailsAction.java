//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.payments.actions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.apache.struts.util.LabelValueBean;
import org.apache.struts.util.MessageResources;
import org.sakaiproject.event.api.UsageSession;
import org.sakaiproject.event.api.EventTrackingService;
import org.sakaiproject.event.api.UsageSessionService;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.tool.api.ToolManager;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserDirectoryService;
import org.sakaiproject.component.cover.ComponentManager;

import za.ac.unisa.lms.constants.EventTrackingTypes;
import za.ac.unisa.lms.tools.payments.dao.StudenQueryDAO;
import za.ac.unisa.lms.tools.payments.forms.DisplayFinancialDetailsForm;
import za.ac.unisa.lms.tools.payments.forms.Statement;
import za.ac.unisa.lms.tools.payments.forms.Student;
import Sfstj09h.Abean.Sfstj09sDspStudentAccount;
import Sfstj18h.Abean.Sfstj18sLstAccountTran;

/**
 * MyEclipse Struts Creation date: 10-24-2005
 *
 * XDoclet definition:
 *
 * @struts:action path="/displayFinancialDetails"
 *                name="displayFinancialDetailsForm"
 *                input="/WEB-INF/jsp/inputFinancialDetails.jsp"
 *                parameter="action" scope="request" validate="true"
 */
public class DisplayFinancialDetailsAction extends LookupDispatchAction {
	
	private SessionManager sessionManager;
	private UserDirectoryService userDirectoryService;
	private EventTrackingService eventTrackingService;
	private ToolManager toolManager;
	private UsageSessionService usageSessionService;
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (request.getParameter("action") == null) return displayFinancialDetails(mapping, form, request, response);
		return super.execute(mapping, form, request, response);
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
	protected Map getKeyMethodMap() {
	      Map map = new HashMap();
	      map.put("button.cancel", "cancel");
	      map.put("button.clear","clear");
	      map.put("button.clearstno","clearInput");
	      map.put("button.displaypfd","displayPreviousFinancialDetails");
	      map.put("button.displayfd","displayFinancialDetails");
	      map.put("displayFinancialDetails","displayFinancialDetails");
	      map.put("displayPreviousFinancialDetails","displayPreviousFinancialDetails");
	      map.put("input","input");
	      map.put("displayPreviousFinancialDetailsInput","displayPreviousFinancialDetailsInput");
	      return map;
	  }

	public ActionForward displayFinancialDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ActionMessages messages = new ActionMessages();
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
		toolManager = (ToolManager) ComponentManager.get(ToolManager.class);
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
		
		DisplayFinancialDetailsForm displayFinancialDetailsForm = (DisplayFinancialDetailsForm) form;

		messages = (ActionMessages) displayFinancialDetailsForm.validate(
				mapping, request);

		if (!messages.isEmpty()) {
			addErrors(request, messages);
			return mapping.findForward("financialDetailsInput");
		}

		if (messages.isEmpty()) {
			/**
			 * Get Student Detail form SFSTJ09
			 */
			Sfstj09sDspStudentAccount op = new Sfstj09sDspStudentAccount();
			operListener opl = new operListener();
			op.addExceptionListener(opl);
			op.clear();
			/* op.setTracing(Trace.MASK_ALL); */
			// op.setInIpAddressCsfStringsString15(request.getRemoteAddr());
			op.setInWsWorkstationCode("internet");
			op.setInWsUserNumber(1);
			op.setInCsfClientServerCommunicationsClientVersionNumber((short) 3);
			op.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
			op.setInCsfClientServerCommunicationsAction("D");
			op.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
			short k = 0;
			op.setInWsDocumentParagraphCode(k);
			if (!("".equals(displayFinancialDetailsForm.getAccount_classifications()) | displayFinancialDetailsForm	.getAccount_classifications() == null)) {
				op.setInStudentAccountClassificationCode(displayFinancialDetailsForm.getAccount_classifications());
			} else {
				op.setInStudentAccountClassificationCode("SF");
			}
			StudenQueryDAO db = new StudenQueryDAO();
			try {
				op.setInStudentAccountHistoryMkAcademicYear(db.getFinGlobalYear());
				op.setInFinGlobalCurrentAcademicYear(db.getFinGlobalYear());
				displayFinancialDetailsForm.setYear(db.getFinGlobalYear());
				displayFinancialDetailsForm.setInputYear(new Integer(db.getFinGlobalYear()-1).shortValue());
			} catch (Exception ex) {
	            throw ex;
			}
			op.setInWsStudentNr(Integer.parseInt(displayFinancialDetailsForm.getStudent().getNumber()));
			op.setInStudentAccountHistoryMkStudentNr(Integer.parseInt(displayFinancialDetailsForm.getStudent().getNumber()));
			op.setInDisplayNameIefSuppliedFlag("Y");
			op.execute();
			if (opl.getException() != null)
				throw opl.getException();
			if (op.getExitStateType() < 3)
				throw new Exception(op.getExitStateMsg());
			if (op.getOutCsfClientServerCommunicationsReturnCode() == 20){
				messages.add("StudentNumber", new ActionMessage("error.studentnumber"));
			}

			String Errormsg = op.getOutCsfStringsString500();
			if ((Errormsg != null) && (!Errormsg.equals(""))) {
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.coolgenerror", Errormsg));
			}

			Student student = new Student();
			if (op.getOutCsfClientServerCommunicationsReturnCode() == 19){
				student = db.getStudent(displayFinancialDetailsForm.getStudent().getNumber());
			} else {
				student.setNumber(String.valueOf(op.getOutWsStudentNr()));
				student.setName(op.getOutWsStudentSurname());
				student.setInitials(op.getOutWsStudentInitials());
				student.setTitle(op.getOutWsStudentMkTitle());
			}
			displayFinancialDetailsForm.setStudent(student);
			/** Student account classifications * */
			int count = op.getOutClassGroupCount();
			ArrayList classifications = new ArrayList();

			for (int i = 0; i < count; i++) {
				if (!("".equals(op.getOutClassGStudentAccountClassificationCode(i)) | op.getOutClassGStudentAccountClassificationCode(i) == null)) {
					classifications.add(new LabelValueBean(op.getOutClassGStudentAccountClassificationDescription(i),op.getOutClassGStudentAccountClassificationCode(i)));
				}
			}
			request.setAttribute("classifications", classifications);
			if (("".equals(displayFinancialDetailsForm.getAccount_classifications()) | displayFinancialDetailsForm.getAccount_classifications() == null)) {
				displayFinancialDetailsForm.setAccount_classifications("SF");
			}

			/** Get Acctount Balance Form STSJ09H */
			Sfstj09sDspStudentAccount op2 = new Sfstj09sDspStudentAccount();
			operListener opl2 = new operListener();
			op2.addExceptionListener(opl2);

			/* op.setTracing(Trace.MASK_ALL); */
			// op.setInIpAddressCsfStringsString15(request.getRemoteAddr());
			op2.setInWsWorkstationCode("internet");
			op2.setInWsUserNumber(1);
			op2.setInCsfClientServerCommunicationsClientVersionNumber((short) 3);
			op2.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
			op2.setInCsfClientServerCommunicationsAction("D");
			op2.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
			k = 0;
			op2.setInWsDocumentParagraphCode(k);
			op2.setInHistoryIefSuppliedFlag("N");
			op2.setInStudentAccountHistoryMkAcademicYear(op.getOutStudentAccountHistoryMkAcademicYear());
			op2.setInStudentAccountHistoryMkStudentNr(op.getOutStudentAccountHistoryMkStudentNr());
			op2.setInFinGlobalCurrentAcademicYear(op.getOutFinGlobalCurrentAcademicYear());
			op2.setInWsStudentAnnualRecordLateRegistrationFlag(op.getOutWsStudentAnnualRecordLateRegistrationFlag());
			op2.setInWsStudentAnnualRecordMkHighestQualCode(op.getOutWsStudentAnnualRecordMkHighestQualCode());
			op2.setInWsStudentAnnualRecordMkStudentNr(op.getOutWsStudentAnnualRecordMkStudentNr());
			op2.setInWsStudentAnnualRecordMkAcademicYear(op.getOutWsStudentAnnualRecordMkAcademicYear());
			op2.setInWsStudentAnnualRecordMkAcademicPeriod(op.getOutWsStudentAnnualRecordMkAcademicPeriod());
			op2.setInStudentAccountClassificationCode(op.getOutStudentAccountClassificationCode());
			op2.setInWsStudentNr(op.getOutWsStudentNr());
			op2.setInWsStudentSurname(op.getOutWsStudentSurname());
			op2.setInWsStudentInitials(op.getOutWsStudentInitials());
			op2.setInWsStudentFinancialBlockFlag(op.getOutWsStudentFinancialBlockFlag());
			op2.setInWsStudentMkCorrespondenceLanguage(op.getOutWsStudentMkCorrespondenceLanguage());
			op2.setInWsStudentMkTitle(op.getOutWsStudentMkTitle());
			op2.setInWsStudentMkCountryCode(op.getOutWsStudentMkCountryCode());
			op2.setInWsStudentExamFeesDebtFlag(op.getOutWsStudentExamFeesDebtFlag());
			op2.setInWsStudentHandedOverFlag(op.getOutWsStudentHandedOverFlag());
			op2.execute();
			if (opl2.getException() != null)
				throw opl2.getException();
			if (op2.getExitStateType() < 3)
				throw new Exception(op.getExitStateMsg());
			if (op2.getOutCsfClientServerCommunicationsReturnCode() == 20){
				messages.add("StudentNumber", new ActionMessage("error.studentnumber"));
			}

			Errormsg = op2.getOutCsfStringsString500();
			if ((Errormsg != null) && (!Errormsg.equals(""))) {
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"error.coolgenerror", Errormsg));
			}



		/*	Sfstj10sLstStudentAccount op2 = new Sfstj10sLstStudentAccount();
			operListener opl2 = new operListener();
			op2.addExceptionListener(opl2);
			op2.clear();
			op2.setInWsWorkstationCode("internet");
			op2.setInCsfClientServerCommunicationsClientVersionNumber((short) 3);
			op2.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
			op2.setInStudentAccountHistoryMkAcademicYear(op.getOutStudentAccountHistoryMkAcademicYear());
			op2.setInHistoryIefSuppliedFlag("N");
			op2.setInFinGlobalCurrentAcademicYear(op.getOutFinGlobalCurrentAcademicYear());
			op2.setInStudentAccountHistoryMkAcademicYear(op.getOutStudentAccountHistoryMkAcademicYear());
			op2.setInStudentAccountHistoryMkStudentNr(op.getOutStudentAccountHistoryMkStudentNr());
			op2.setInWsAccountTypeCode(op.getOutWsAccountTypeCode());
			op2.setInWsStudentAnnualRecordLateRegistrationFlag(op.getOutWsStudentAnnualRecordLateRegistrationFlag());
			op2.setInWsStudentAnnualRecordMkHighestQualCode(op.getOutWsStudentAnnualRecordMkHighestQualCode());
			op2.setInWsStudentAnnualRecordMkStudentNr(op.getOutWsStudentAnnualRecordMkStudentNr());
			op2.setInWsStudentAnnualRecordMkAcademicYear(op.getOutWsStudentAnnualRecordMkAcademicYear());
			op2.setInWsStudentAnnualRecordMkAcademicPeriod(op.getOutWsStudentAnnualRecordMkAcademicPeriod());
			op2.setInStudentAccountClassificationCode(op.getOutStudentAccountClassificationCode());
			op2.execute(); */



			/** Next Year */
			displayFinancialDetailsForm.setNext_year(displayFinancialDetailsForm.getYear() + 1);

			/** Due Immediatly */
			displayFinancialDetailsForm.setDue_imm(op2.getOutStudentAccountDueImmediately());
				/** Due March */
			displayFinancialDetailsForm.setDue_march(op2.getOutMarchIefSuppliedTotalCurrency());
				/** Due may */
			displayFinancialDetailsForm.setDue_may(op2.getOutMayIefSuppliedTotalCurrency());
				/** Due August */
			displayFinancialDetailsForm.setDue_aug(op2.getOutAugustIefSuppliedTotalCurrency());
				/** Due November */
			displayFinancialDetailsForm.setDue_nov(op2.getOutNovemberIefSuppliedTotalCurrency());
				/** Due Next March */
			displayFinancialDetailsForm.setDue_next_march(op2.getOutNextMarchIefSuppliedTotalCurrency());
				/** Get Details of statement */
			Sfstj18sLstAccountTran op1 = new Sfstj18sLstAccountTran();
			operListener opl1 = new operListener();
			op1.addExceptionListener(opl1);
			op1.clear();
			/* op.setTracing(Trace.MASK_ALL); */
			//op.setInIpAddressCsfStringsString15(request.getRemoteAddr());
			op1.setInWsWorkstationCode("internet");
			op1.setInCsfClientServerCommunicationsClientVersionNumber((short) 3);
			op1.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
			op1.setInCsfClientServerCommunicationsAction("D");
			op1.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
			op1.setInWsStudentNr(Integer.parseInt(displayFinancialDetailsForm.getStudent().getNumber()));
			op1.setInStudentAccountClassificationCode(displayFinancialDetailsForm.getAccount_classifications());
			//Johanet 20160802 - Sent in the academic year
			op1.setInFinGlobalCurrentAcademicYear(db.getFinGlobalYear());		
			op1.setInStudentAccountHistoryMkAcademicYear(db.getFinGlobalYear());
			op1.execute();
			if (opl1.getException() != null)
				throw opl1.getException();
			if (op1.getExitStateType() < 3)
				throw new Exception(op1.getExitStateMsg());
			String Errormsg1 = op1.getOutCsfStringsString500();
			if ((Errormsg1 != null) && (!Errormsg1.equals(""))) {
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.coolgenerror", Errormsg1));
			}
			int count2 = op1.getOutGroupCount();
			Vector records = new Vector();
			if (count2 > 0){
				for (int i = 0; i < count2; i++) {
					Statement statement = new Statement();
					//DateFormat strDate = new SimpleDateFormat("dd-MM-yyyy");
					DateFormat strDate = new SimpleDateFormat("yyyy-MM-dd");
					statement.setDate(strDate.format(op1.getOutGDebitStudentFinancialTransactionBundleDate(i).getTime()));
					//Johanet Changes 20160810 - use longer description field
					//statement.setDescription(op1.getOutGScreenDescriptionCsfStringsString20(i));
					statement.setDescription(op1.getOutGTransTypeAllocationDescription(i));
					statement.setReference(op1.getOutGBundleNrDocCsfStringsString8(i));
					//Johanet Changes 20160729
					statement.setAllocation(op1.getOutGAllocCsfStringsString11(i));
					if (op1.getOutGDebitCreditCsfStringsString2(i).equalsIgnoreCase("DT")) {
						statement.setDebitAmount(op1.getOutGDebitStudentFinancialTransactionAmount(i)* -1);
					}
					if (op1.getOutGDebitCreditCsfStringsString2(i).equalsIgnoreCase("CR")) {
						statement.setCreditAmount(op1.getOutGDebitStudentFinancialTransactionAmount(i));
					}
					statement.setBalanceAmount(0);
					if (op1.getOutGTotalsWsTutorialRegistrationAmount(i)!=0){
						statement.setBalanceAmount(op1.getOutGTotalsWsTutorialRegistrationAmount(i)* -1);
					}					
					//statement.setAmount(op1.getOutGDebitStudentFinancialTransactionAmount(i)* -1);
					records.add(statement);
				}
				request.setAttribute("records", records);
				displayFinancialDetailsForm.setStatementRecords(records);

				/** Balance and set message */
				ActionMessages actionMessages = new ActionMessages();
				MessageResources messageResources = getResources(request);
               
				if (op.getOutStudentAccountBalance() >= 0) {
					//actionMessages.add("Balance", new ActionMessage("function.balance", messageResources.getMessage("function.balanceToUnisa")));
					saveMessages(request, actionMessages);
				} else {
					//actionMessages.add("Balance", new ActionMessage("function.balance", messageResources.getMessage("function.balanceToYou")));
					saveMessages(request, actionMessages);
				}
				if (op.getOutStudentAccountBalance() < 0) {
					displayFinancialDetailsForm.setBalance(op2.getOutStudentAccountBalance()* -1);
				} else {
					displayFinancialDetailsForm.setBalance(op2.getOutStudentAccountBalance());
				}
				
				if (displayFinancialDetailsForm.getBalance() > 0 ) {
					actionMessages.add("Balance", new ActionMessage("function.balance", messageResources.getMessage("function.balanceToUnisa")));
					saveMessages(request, actionMessages);
				} else if (displayFinancialDetailsForm.getBalance() == 0) {
					actionMessages.add("Balance", new ActionMessage("label.balance"));
					saveMessages(request, actionMessages);
				} else if (displayFinancialDetailsForm.getBalance() < 0) {
					actionMessages.add("Balance", new ActionMessage("function.balance", messageResources.getMessage("function.balanceToYou")));
					saveMessages(request, actionMessages);
				}
			} else {
				Statement statement = new Statement();
				records.add(statement);
				Vector empty = new Vector();
				displayFinancialDetailsForm.setStatementRecords(empty);
			}
		}

		UsageSession usageSession = usageSessionService.getSession();

		if (!messages.isEmpty()) {
			addErrors(request, messages);
			return mapping.findForward("financialDetailsInput");
		}
		eventTrackingService.post(
				eventTrackingService.newEvent(EventTrackingTypes.EVENT_PAYMENTS_VIEWCURRENT, toolManager.getCurrentPlacement().getContext(), false),usageSession);
		return mapping.findForward("displayFinancialDetails");

	}

	/**
	 * Method input Determine if user is lecturer or student A student will go
	 * directly to display screen A lecter will go to input screen
	 */
	public ActionForward input(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		DisplayFinancialDetailsForm displayFinancialDetailsForm = (DisplayFinancialDetailsForm) form;
		sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
		userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
		
		// Get user
		Session currentSession = sessionManager.getCurrentSession();
		String userId = currentSession.getUserId();
		User user = null;
		if (userId != null) {
			user = userDirectoryService.getUser(userId);
			request.setAttribute("user", user);
		}

		Student student = new Student();
		if ("Student".equalsIgnoreCase(user.getType())) {
			/*
			 * Student
			 */
			student.setNumber(user.getEid());
			displayFinancialDetailsForm.setStudent(student);
			displayFinancialDetailsForm.setStudentUser(true);
			Calendar calendar = Calendar.getInstance();
			displayFinancialDetailsForm.setInputYear(new Integer(calendar.get(Calendar.YEAR)-1).shortValue());
			return displayFinancialDetails(mapping, form, request, response);
		} else if ("Instructor".equalsIgnoreCase(user.getType())) {
			/*
			 * Lecturer Get student nr input
			 */
			displayFinancialDetailsForm.setStudent(student);
			displayFinancialDetailsForm.setStudentUser(false);
			Calendar calendar = Calendar.getInstance();
			displayFinancialDetailsForm.setInputYear(new Integer(calendar.get(Calendar.YEAR)-1).shortValue());
			return mapping.findForward("financialDetailsInput");
		} else if ("admin".equalsIgnoreCase(user.getType())){
			/*
			 * Administrator Get student nr input
			 */
			displayFinancialDetailsForm.setStudent(student);
			displayFinancialDetailsForm.setStudentUser(false);
			Calendar calendar = Calendar.getInstance();
			displayFinancialDetailsForm.setInputYear(new Integer(calendar.get(Calendar.YEAR)-1).shortValue());
			return mapping.findForward("financialDetailsInput");
		}else{
			/*
			 * TO DO !!!! User unknown Go to error screen
			 */

			throw new Exception("Payment : User type unknown");
		}
	}
	public ActionForward displayPreviousFinancialDetailsInput(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception{
			DisplayFinancialDetailsForm displayFinancialDetailsForm = (DisplayFinancialDetailsForm) form;
			StudenQueryDAO db = new StudenQueryDAO();
			try {
				displayFinancialDetailsForm.setInputYear(new Integer(db.getFinGlobalYear() - 1).shortValue());
			} catch (Exception ex) {
	            throw ex;
			}
			displayFinancialDetailsForm.setAccount_classifications("SF");
		return mapping.findForward("displayPFDI");
	}
	public ActionForward displayPreviousFinancialDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
		toolManager = (ToolManager) ComponentManager.get(ToolManager.class);

		DisplayFinancialDetailsForm displayFinancialDetailsForm = (DisplayFinancialDetailsForm) form;
		ActionMessages messages = new ActionMessages();
		messages = (ActionMessages) displayFinancialDetailsForm.validate(mapping, request);

		if (!messages.isEmpty()) {
			addErrors(request, messages);
			return mapping.findForward("displayPFDI");
		}

		if (displayFinancialDetailsForm.getInputYear()< 2002){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.inputYearMinValue"));
			addErrors(request,messages);
			return mapping.findForward("displayPFDI");
		}

		Sfstj09sDspStudentAccount op = new Sfstj09sDspStudentAccount();
		operListener opl = new operListener();
		op.addExceptionListener(opl);
		op.clear();
		/* op.setTracing(Trace.MASK_ALL); */
		// op.setInIpAddressCsfStringsString15(request.getRemoteAddr());
		op.setInWsWorkstationCode("internet");
		op.setInWsUserNumber(1);
		op.setInCsfClientServerCommunicationsClientVersionNumber((short) 3);
		op.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
		op.setInCsfClientServerCommunicationsAction("D");
		op.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
		op.setInRetrieveIefSuppliedFlag("N");
		op.setInStudentAccountHistoryMkAcademicYear(displayFinancialDetailsForm.getInputYear());
		short k = 0;
		op.setInWsDocumentParagraphCode(k);
		op.setInStudentAccountClassificationCode(displayFinancialDetailsForm.getAccount_classifications());
		op.setInWsStudentNr(Integer.parseInt(displayFinancialDetailsForm.getStudent().getNumber()));
		op.setInDisplayNameIefSuppliedFlag("N");
		op.execute();
		if (opl.getException() != null)
			throw opl.getException();
		if (op.getExitStateType() < 3)
			throw new Exception(op.getExitStateMsg());
		String Errormsg = op.getOutCsfStringsString500();
		if ((Errormsg != null) && (!Errormsg.equals(""))) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.coolgenerror", Errormsg));
		}
		/** Set Student Detail * */
		Student student = new Student();
		student.setNumber(String.valueOf(op.getOutWsStudentNr()));
		student.setName(op.getOutWsStudentSurname());
		student.setInitials(op.getOutWsStudentInitials());
		student.setTitle(op.getOutWsStudentMkTitle());
		displayFinancialDetailsForm.setStudent(student);

		/** Student account classifications * */
		int count = op.getOutClassGroupCount();
		ArrayList classifications = new ArrayList();

		for (int i = 0; i < count; i++) {
			if (!("".equals(op.getOutClassGStudentAccountClassificationCode(i)) | op.getOutClassGStudentAccountClassificationCode(i) == null)) {
				classifications.add(new LabelValueBean(op.getOutClassGStudentAccountClassificationDescription(i),op.getOutClassGStudentAccountClassificationCode(i)));
			}

		}
		request.setAttribute("classifications", classifications);
		if (("".equals(displayFinancialDetailsForm.getAccount_classifications()) | displayFinancialDetailsForm.getAccount_classifications() == null)) {
			displayFinancialDetailsForm.setAccount_classifications("SF");
		}

		/** Current Year */

		/** Get Details of statement */
		Sfstj18sLstAccountTran op1 = new Sfstj18sLstAccountTran();
		operListener opl1 = new operListener();
		op1.addExceptionListener(opl1);
		op1.clear();
		/* op.setTracing(Trace.MASK_ALL); */
		// op.setInIpAddressCsfStringsString15(request.getRemoteAddr());
		op1.setInWsWorkstationCode("internet");
		op1.setInWsUserNumber(1);
		op1.setInCsfClientServerCommunicationsClientVersionNumber((short) 3);
		op1.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
		op1.setInCsfClientServerCommunicationsAction("D");
		op1.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
		op1.setInWsStudentNr(Integer.parseInt(displayFinancialDetailsForm.getStudent().getNumber()));
		op1.setInStudentAccountClassificationCode(displayFinancialDetailsForm.getAccount_classifications());
		op1.setInHistoryIefSuppliedFlag(op.getOutHistoryIefSuppliedFlag());
		op1.setInStudentAccountHistoryMkAcademicYear(displayFinancialDetailsForm.getInputYear());
		op1.execute();
		if (opl1.getException() != null)
			throw opl1.getException();
		if (op1.getExitStateType() < 3)
			throw new Exception(op1.getExitStateMsg());
		String Errormsg1 = op1.getOutCsfStringsString500();
		if ((Errormsg1 != null) && (!Errormsg1.equals(""))) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"error.coolgenerror", Errormsg1));
		}
		int count2 = op1.getOutGroupCount();
		Vector records = new Vector();
		if (count2 > 0){

			for (int i = 0; i < count2; i++) {
				Statement statement = new Statement();
				//DateFormat strDate = new SimpleDateFormat("dd-MM-yyyy");
				DateFormat strDate = new SimpleDateFormat("yyyy-MM-dd");
				statement.setDate(strDate.format(op1.getOutGDebitStudentFinancialTransactionBundleDate(i).getTime()));
				//Johanet Changes 20160810 - use longer description field
				//statement.setDescription(op1.getOutGScreenDescriptionCsfStringsString20(i));
				statement.setDescription(op1.getOutGTransTypeAllocationDescription(i));
				statement.setReference(op1.getOutGBundleNrDocCsfStringsString8(i));
				//Johanet Changes 20160729
				statement.setAllocation(op1.getOutGAllocCsfStringsString11(i));
				if (op1.getOutGDebitCreditCsfStringsString2(i).equalsIgnoreCase("DT")) {
					statement.setDebitAmount(op1.getOutGDebitStudentFinancialTransactionAmount(i)* -1);
				}
				if (op1.getOutGDebitCreditCsfStringsString2(i).equalsIgnoreCase("CR")) {
					statement.setCreditAmount(op1.getOutGDebitStudentFinancialTransactionAmount(i));
				}
				statement.setBalanceAmount(0);
				if (op1.getOutGTotalsWsTutorialRegistrationAmount(i)!=0){
					statement.setBalanceAmount(op1.getOutGTotalsWsTutorialRegistrationAmount(i)* -1);
				}
				//statement.setAmount(op1.getOutGDebitStudentFinancialTransactionAmount(i)* -1);
				records.add(statement);
			}
			request.setAttribute("records", records);
			displayFinancialDetailsForm.setStatementRecords(records);
			ActionMessages actionMessages = new ActionMessages();
			MessageResources messageResources = getResources(request);

				actionMessages.add("Balance", new ActionMessage("function.balance", messageResources.getMessage("function.balanceToUnisa")));
				saveMessages(request, actionMessages);

		} else {
			Statement statement = new Statement();
			records.add(statement);
			Vector empty = new Vector();
			displayFinancialDetailsForm.setStatementRecords(empty);
		}

		UsageSession usageSession = usageSessionService.getSession();
	    request.getSession().setAttribute("UsageSession", usageSession);

		eventTrackingService.post(
				eventTrackingService.newEvent(EventTrackingTypes.EVENT_PAYMENTS_VIEWHISTORY, toolManager.getCurrentPlacement().getContext(), false),usageSession);
		return mapping.findForward("displayPFD");

	}
	public ActionForward clear(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception{
			DisplayFinancialDetailsForm displayFinancialDetailsForm = (DisplayFinancialDetailsForm) form;
			displayFinancialDetailsForm.setInputYear(new Integer(0).shortValue());

		return mapping.findForward("displayPFDI");
	}
	public ActionForward cancel(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		Calendar calendar = Calendar.getInstance();
		DisplayFinancialDetailsForm displayFinancialDetailsForm = (DisplayFinancialDetailsForm) form;
		displayFinancialDetailsForm.setInputYear(new Integer(calendar.get(Calendar.YEAR)).shortValue());
		displayFinancialDetailsForm.setAccount_classifications("SF");
		return displayFinancialDetails(mapping, form, request, response);
	}
	public ActionForward clearInput(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception{
			DisplayFinancialDetailsForm displayFinancialDetailsForm = (DisplayFinancialDetailsForm) form;
		Student student = new Student();
		student.setNumber(String.valueOf(0));
		displayFinancialDetailsForm.setStudent(student);
		Calendar calendar = Calendar.getInstance();
		displayFinancialDetailsForm.setInputYear(new Integer(calendar.get(Calendar.YEAR)-1).shortValue());
		return mapping.findForward("financialDetailsInput");
	}
}
