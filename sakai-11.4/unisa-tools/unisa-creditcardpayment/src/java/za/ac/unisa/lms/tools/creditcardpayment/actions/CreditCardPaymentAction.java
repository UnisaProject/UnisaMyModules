//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.creditcardpayment.actions;

import za.ac.unisa.lms.tools.creditcardpayment.utils.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.sakaiproject.event.api.UsageSession;
import org.sakaiproject.event.api.EventTrackingService;
import org.sakaiproject.event.api.UsageSessionService;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.tool.api.ToolManager;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserDirectoryService;
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.component.cover.ComponentManager;
import za.ac.unisa.lms.constants.EventTrackingTypes;
import za.ac.unisa.lms.tools.creditcardpayment.dao.StudentQueryDAO;
import za.ac.unisa.lms.tools.creditcardpayment.forms.CreditCardPaymentForm;
import za.ac.unisa.lms.tools.creditcardpayment.forms.Student;
import Sfrrf03h.Abean.Sfrrf03sMntOnlineCcPayments;

public class CreditCardPaymentAction extends LookupDispatchAction {

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

	// ----------------------------------- Instance Variables
	public static Log log = LogFactory.getLog(CreditCardPaymentAction.class);
	private SessionManager sessionManager;
	private UserDirectoryService userDirectoryService;
	private EventTrackingService eventTrackingService;
	private ToolManager toolManager;
	private UsageSessionService usageSessionService;
	CreditcardPaymentValidator creditcardPaymentValidator;

	// --------------------------------------------------------- Methods

	protected Map getKeyMethodMap() {
		Map map = new HashMap();
		map.put("button.back", "back");
		map.put("button.cancel", "cancel");
		map.put("button.continue", "nextStep");
		map.put("button.paynow", "nextStep");
		map.put("confirm", "confirm");
		map.put("studentInput", "studentInput");
		map.put("button.payanother", "cancel");
		map.put("button.payment", "cancel");
		map.put("logout","logout");
		map.put("button.logout","logout");
		return map;
	}
	/**
	 * 
	 * Reset form and forward to student nr input page
	 * 
	 */	
	public ActionForward studentInput(ActionMapping mapping, ActionForm form,
		 	                 HttpServletRequest request, HttpServletResponse response){
		                     try{
		                           log.debug("CreditCardPayment: studentInput");
		                           CreditCardPaymentForm creditForm = (CreditCardPaymentForm) form;
                          		   sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
		                           userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
		                           reset(creditForm);
		                           // Check for already logged in student
		                           creditForm.setStudentUser(false);
		                           Session currentSession = sessionManager.getCurrentSession();
		                           String userId = currentSession.getUserId();
		                           User user = null;
		                           if (userId != null) {
			                            user = userDirectoryService.getUser(userId);
			                            if ("Student".equalsIgnoreCase(user.getType())){
				                               creditForm.setStudentUser(true);
				                               creditForm.getStudent().setStudentNumber(user.getEid());
				                               if((creditForm.getStudent().getStudentNumber()==null)||(
				                            		   creditForm.getStudent().getStudentNumber().equals(""))){
				                            	            return mapping.findForward("studentInput");
				                               }
				                               // read student and go directly to qual input
				                               return mapping.findForward(qualificationInput(mapping, form, request, response));
			                            }
		                          }
	                          }catch(Exception ex){
	                        	                return mapping.findForward("studentInput");
	                         }
		                   return mapping.findForward("studentInput");
	}
	
		/**
	 * 
	 * Validate and forward to qualification input page
	 * 
	 */	
	public String qualificationInput(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		CreditCardPaymentForm creditForm = (CreditCardPaymentForm) form;
		ActionMessages messages = new ActionMessages();

		//--------------------------------
		//   Check input
		//--------------------------------
		if(creditcardPaymentValidator==null){
		     creditcardPaymentValidator= new CreditcardPaymentValidator();
		}
		String errorMsg = creditcardPaymentValidator.checkStudentNr(creditForm.getStudent().getStudentNumber());
		if (!"".equals(errorMsg)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"errors.message", errorMsg));
			addErrors(request, messages);
			return "studentInput";
		}else{
			creditForm.getStudent().setStudentNumber(creditForm.getStudent().getStudentNumber().trim());
		}

	// -------------------------------------------------------------------------
		log.debug("CreditCardPayment: qualificationInput();" + creditForm.toStringStudent());
	//--------------------------------------------------------------------------

		/*
		 * Check if system is up and get student information
		 */
		boolean result = isStudentValid(creditForm, request, response);
		if (result) {
			if ("AP".equals(creditForm.getRegStatus())){
				// if student number applicant, go directly to payment page
				//------------------------------------------------------------------------------
					log.debug("CreditCardPayment: tpPaymentInput(); "+ creditForm.toStringStudent());
				//------------------------------------------------------------------------------
				// load lists
				creditForm.setMonthList(setupMonthList());
				creditForm.setYearList(setupYearList());
				creditForm.setBudgetList(setupBudgetOptionList());
				// SET APPLICATION COST
				NumberFormat formatter = new DecimalFormat("#0.00");
				formatter = new DecimalFormat("#0.00");
				//creditForm.setApplyAmount(150);
				//creditForm.setApplyAmountInput(formatter.format(150));
				creditForm.setApplyAmountInput(formatter.format(creditForm.getApplyAmount()));
				creditForm.setCcTotalAmountInput(creditForm.getApplyAmountInput());
				
				return "applyPayment";
			}else{
				return "qualInput";
			}
		} else {
			return "studentInput";
		}

	}
	
	/**
	 * 
	 * Validate and forward to non tp payment input page
	 * 
	 */		
	public String nonTpPaymentInput(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		CreditCardPaymentForm creditForm = (CreditCardPaymentForm) form;
		StudentQueryDAO dao = new StudentQueryDAO();
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
		toolManager = (ToolManager) ComponentManager.get(ToolManager.class);
		
	//------------------------------------------------------------------------------
		log.debug("CreditCardPayment: tpPaymentInput(); "+ creditForm.toStringStudent());
	//------------------------------------------------------------------------------
	
		
		// check qualification
		if (!isQualCodeValid(creditForm, request, response))  {
			return "qualInput";
		}		
		
		// load lists
		creditForm.setMonthList(setupMonthList());
		creditForm.setYearList(setupYearList());
		creditForm.setBudgetList(setupBudgetOptionList());
		//screditForm.setPayThreeGDataBundleFee("");

		eventTrackingService.post(
				eventTrackingService.newEvent(EventTrackingTypes.EVENT_CREDITCARD_DISPLAY, toolManager.getCurrentPlacement().getContext(), false));
		
		if("TN".equalsIgnoreCase(creditForm.getRegStatus())){
			// if student tp registered and smartcard = W, student can cancel the request
			if("W".equalsIgnoreCase(dao.getSmartCardValue(creditForm.getStudent().getStudentNumber()))){
				creditForm.setSmartCardChoice(true);
			}else{
				creditForm.setSmartCardChoice(false);
			}
			return "tpPayment";
		}else{
			return "nonTpPayment";
		}
	}
	
	/**
	 * 
	 * Validate and forward to tp payment input page
	 * 
	 */	
	public String tpPaymentInput(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		CreditCardPaymentForm creditForm = (CreditCardPaymentForm) form;
		
	//------------------------------------------------------------------------------
		log.debug("CreditCardPayment: tpPaymentInput(); "+ creditForm.toStringStudent());
	//------------------------------------------------------------------------------
		
		// check qualification
		if (!isQualCodeValid(creditForm, request, response))  {
			return "qualInput";
		}
		
		// load lists
		creditForm.setMonthList(setupMonthList());
		creditForm.setYearList(setupYearList());
		creditForm.setBudgetList(setupBudgetOptionList());

		return "ppPayment";
	}
	
	/**
	 * 
	 * Setup numbers drop down list
	 * 
	 */
	private List<LabelValueBean> setupMonthList(){

		ArrayList<LabelValueBean> list = new ArrayList<LabelValueBean>();
		for (int i = 1; i <13; i++) {
			if(i < 10){
				list.add(new LabelValueBean("0"+Integer.toString(i),"0"+Integer.toString(i)));
			}else{
				list.add(new LabelValueBean(Integer.toString(i),Integer.toString(i)));
			}
		}
		return list;
	}
	
	/**
	 * 
	 * Setup year drop down list
	 * 
	 */
	private List<LabelValueBean> setupYearList(){

		ArrayList<LabelValueBean> list = new ArrayList<LabelValueBean>();
		int currentYear = new GregorianCalendar().get(Calendar.YEAR);
		int toYear = new GregorianCalendar().get(Calendar.YEAR) + 11;
		for (int i = currentYear; i <toYear ; i++) {
			list.add(new LabelValueBean(Integer.toString(i),Integer.toString(i)));
		}
		return list;
	}
	
	/**
	 * 
	 *  Setup budget option drop down list
	 * 
	 */
	private ArrayList <LabelValueBean> setupBudgetOptionList(){

		ArrayList<LabelValueBean> answer = new ArrayList<LabelValueBean>();
		//answer.add(new org.apache.struts.util.LabelValueBean("Select from menu", ""));
		answer.add(new org.apache.struts.util.LabelValueBean("None", "0"));
		answer.add(new org.apache.struts.util.LabelValueBean("6 Months", "6"));
		answer.add(new org.apache.struts.util.LabelValueBean("12 Months", "12"));
		answer.add(new org.apache.struts.util.LabelValueBean("18 Months", "18"));
		answer.add(new org.apache.struts.util.LabelValueBean("24 Months", "24"));
		return answer;
	}
	
	/**
	 * 
	 * Read student information and return qualification, fees and registration status
	 * 
	 */
	public boolean isStudentValid(CreditCardPaymentForm creditForm,HttpServletRequest request, HttpServletResponse response ) throws Exception{		

		ActionMessages messages = new ActionMessages();
		boolean result = true;
		
		Sfrrf03sMntOnlineCcPayments op = new Sfrrf03sMntOnlineCcPayments();
		operListener opl = new operListener();
		op.addExceptionListener(opl);
		op.clear();
		
		op.setInCsfClientServerCommunicationsClientVersionNumber((short) 3);
		op.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
		op.setInCsfClientServerCommunicationsAction("DS");
		op.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
		op.setInSecurityWsUserNumber(99998);
		op.setInWsWorkstationCode("internet");
		String stuNum=creditForm.getStudent().getStudentNumber();
		if((stuNum==null)||(stuNum.equals(""))||(stuNum.length()>8)){
			  return false;
		}
		op.setInWsStudentNr(Integer.parseInt(creditForm.getStudent().getStudentNumber()));
		
		op.execute();
		if (opl.getException() != null)
			return false;
		log.error("CreditCardPayment- Exception was thrown; "+ opl.getException());
			//throw opl.getException();
		if (op.getExitStateType() < 3){
			log.error("CreditCardPayment- CoolGEN error; action: DS; "+ creditForm.toStringStudent());
			return false;
			//throw new Exception(op.getExitStateMsg());
		}

		// Check error flag
		if ("Y".equalsIgnoreCase(op.getOutErrorIefSuppliedFlag())){
			String errormsg = op.getOutCsfStringsString500();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.coolgenerror", errormsg));
			addErrors(request, messages);
			return false;
		}else{	
			// set student info
			creditForm.getStudent().setName(op.getOutNameCsfStringsString40().trim());
			creditForm.getQual().setQualCode(op.getOutWsQualificationCode());
			creditForm.getQual().setQualDesc(op.getOutWsQualificationEngDescription().trim());
			creditForm.getStudent().setEmailAddress(op.getOutWsAddressV2EmailAddress().trim());
			creditForm.setRegStatus(op.getOutWsStudentAnnualRecordStatusCode());
			if("RG".equalsIgnoreCase(creditForm.getRegStatus())){
				creditForm.setRegStatusDescription("Registered");
			} else if ("TN".equalsIgnoreCase(creditForm.getRegStatus())){
				creditForm.setRegStatusDescription("Registration Pending");
			} else if ("CA".equalsIgnoreCase(creditForm.getRegStatus())){
				creditForm.setRegStatusDescription("Registration cancelled");
			} else {
				creditForm.setRegStatusDescription("Not Registered");
			}
			// set study fees  for output in form
			NumberFormat formatter = new DecimalFormat("#0.00");
			creditForm.setBalance(formatter.format(op.getOutDueStudentAccountBalance()));			
			if (!"0.00".equals(creditForm.getBalance())){
				creditForm.setCreditDebitIndicator(op.getOutDtCrIndCsfStringsString6());
				// replace minus sign
				creditForm.setBalance(creditForm.getBalance().replace("-", ""));
			}
			creditForm.setLibrayFineBalance(formatter.format(op.getOutLdDueStudentAccountBalance()));			
			if (!"0.00".equals(creditForm.getLibrayFineBalance())){
				creditForm.setLibCreditDebitIndicator(op.getOutLdDtCrIndCsfStringsString6());
				// replace minus sign
				creditForm.setLibrayFineBalance(creditForm.getLibrayFineBalance().replace("-", ""));
			}
			// set NON-TP matric +lib fees
			creditForm.setLibraryFeeText("R"+Double.toString(op.getOutSmartcardAndApplCostWsAcademicYearSmartcardCost())+"0");
			creditForm.setLibraryFee(op.getOutSmartcardAndApplCostWsAcademicYearSmartcardCost());
			creditForm.setLibraryFineFeeText("R"+Double.toString(op.getOutLdDueStudentAccountBalance())+"0");
			creditForm.setLibraryFineFee(op.getOutLdDueStudentAccountBalance());
			creditForm.setThreeGDataBundleFeeText("R"+Double.toString(op.getOut3gBundleDocumentTotalAmount())+"0"); 
			creditForm.setThreeGDataBundleFee(op.getOut3gBundleDocumentTotalAmount());
			creditForm.setMatricFirstAppFeeText("R"+op.getOutMrFirstApplicationCostMrFlagAmount() + "0");
			creditForm.setMatricFirstAppFee(op.getOutMrFirstApplicationCostMrFlagAmount());
			// set TP matric and lib fees as calculated by server
			creditForm.setLibraryFeeForStudent(op.getOutSmartcardAndApplDueWsAcademicYearSmartcardCost());
			creditForm.setLibraryFeeAmount(op.getOutSmartcardAndApplDueWsAcademicYearSmartcardCost());
			creditForm.setLibraryFineFeeAmount(op.getOutLdDueStudentAccountBalance());
			creditForm.setFormattedLibraryFeeForStudent(formatter.format(op.getOutSmartcardAndApplDueWsAcademicYearSmartcardCost()));
			//creditForm.setThreeGDataBundleForStudent(op.getOut3gBundleDocumentTotalAmount());//
			//creditForm.setThreeGDataBundleFeeAmount(op.getOut3gBundleDocumentTotalAmount());
			creditForm.setFormattedThreeGDataBundleFeeForStudent(formatter.format(op.getIn3gBundleDocumentTotalAmount()));
			creditForm.setMatricFeeForStudent(op.getOutMrDueIefSuppliedAverageCurrency());
			creditForm.setFormattedMatricFeeForStudent(formatter.format(op.getOutMrDueIefSuppliedAverageCurrency()));
			// more TP fees
			creditForm.setFullAccount(op.getOutDueImmediatelyIefSuppliedAverageCurrency());
			creditForm.setDueImmediately(op.getOutDueStudentAccountDueImmediately());
			creditForm.setMinimumStudyFee(op.getOutStudyDueIefSuppliedAverageCurrency());
			creditForm.setMinimumForReg(op.getOutTempSfCalculatedStudyFeesMinimum());
			// non-tp: if smartcard balance > 0, dont show
			if (op.getOutWsSmartCardDataBalance()>0){
				creditForm.setCanChooseLibraryCard(false);
			}
			// non-tp: if mr balance > min mr fee, dont show
			if (op.getOutMrStudentAccountBalance()>0 && op.getOutMrStudentAccountBalance()>=op.getOutMrDueIefSuppliedAverageCurrency()){
				creditForm.setCanChooseMatric(false);
			}
			// non-tp: if 3G DataBundle balance > min 3G DataBundle fee, dont show
			if (op.getOut3gBundleDocumentTotalAmount()==0){
				creditForm.setCanChooseThreeGDataBundle(false);
			}
			//student number application fee jul 2010
			creditForm.setApplyAmount(op.getOutSmartcardAndApplCostWsAcademicYearApplicationCost());
						
			//------------------------------------------------------------------------------
			log.debug("CreditCardPayment: isStudentValid(); "+ creditForm.toStringStudent());
			//------------------------------------------------------------------------------
			
		}
		
		return result;
	} 
	
	
	/**
	 * 
	 * Validate qualification code and get qual description plus re-read 
	 * all student, fee etc information
	 * 
	 */
	public boolean isQualCodeValid(CreditCardPaymentForm creditForm,HttpServletRequest request, HttpServletResponse response ) throws Exception{		

		if(creditForm.getStudent().getStudentNumber()==null){
			 return false;
		}else{
			
		ActionMessages messages = new ActionMessages();
		boolean result = true;
		if(creditForm.getQual().getQualCode()==null||creditForm.getQual().getQualCode().equals("")){
			return false;
		}
		Sfrrf03sMntOnlineCcPayments op = new Sfrrf03sMntOnlineCcPayments();
		operListener opl = new operListener();
		op.addExceptionListener(opl);
		op.clear();
		/* op.setTracing(Trace.MASK_ALL); */
		// op.setInIpAddressCsfStringsString15(request.getRemoteAddr());
		op.setInCsfClientServerCommunicationsClientVersionNumber((short) 3);
		op.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
		op.setInCsfClientServerCommunicationsAction("GD");
		op.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
		op.setInSecurityWsUserNumber(99998);
		op.setInWsWorkstationCode("internet");
		op.setInWsStudentNr(Integer.parseInt(creditForm.getStudent().getStudentNumber()));
		op.setInWsQualificationCode(creditForm.getQual().getQualCode());
		
		op.execute();
		if (opl.getException() != null)
			return false;
			//throw opl.getException();
		if (op.getExitStateType() < 3)
			return false;
			//throw new Exception(op.getExitStateMsg());

		// Check error flag
		if ("Y".equalsIgnoreCase(op.getOutErrorIefSuppliedFlag())){
			String errormsg = op.getOutCsfStringsString500();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.coolgenerror", errormsg));
			addErrors(request, messages);
			creditForm.getQual().setQualDesc("");
			return false;
		}else{	
			creditForm.getQual().setQualCode(op.getOutWsQualificationCode());
			creditForm.getQual().setQualDesc(op.getOutWsQualificationEngDescription());	
			// set fees
			NumberFormat formatter = new DecimalFormat("#0.00");
			creditForm.setBalance(formatter.format(op.getOutDueStudentAccountBalance()));			
			if (!"0.00".equals(creditForm.getBalance())){
				creditForm.setCreditDebitIndicator(op.getOutDtCrIndCsfStringsString6());
				// replace minus sign
				creditForm.setBalance(creditForm.getBalance().replace("-", ""));
			}
			creditForm.setLibrayFineBalance(formatter.format(op.getOutLdDueStudentAccountBalance()));			
			if (!"0.00".equals(creditForm.getLibrayFineBalance())){
				creditForm.setLibCreditDebitIndicator(op.getOutLdDtCrIndCsfStringsString6());
				// replace minus sign
				creditForm.setLibrayFineBalance(creditForm.getLibrayFineBalance().replace("-", ""));
			}
			// more TP fees
			creditForm.setFullAccount(op.getOutDueImmediatelyIefSuppliedAverageCurrency());
			creditForm.setDueImmediately(op.getOutDueStudentAccountDueImmediately());
			creditForm.setMinimumStudyFee(op.getOutStudyDueIefSuppliedAverageCurrency());
			creditForm.setMinimumForReg(op.getOutTempSfCalculatedStudyFeesMinimum());
		}
		
		return result;
		}
	} 
	
	/**
	 * 
	 * Validate non-tp payment input
	 * 
	 */
	public String validateNonTpPaymentInfo(CreditCardPaymentForm creditForm,ActionMapping mapping,HttpServletRequest request, HttpServletResponse response ) throws Exception{
		
		           log.debug("CreditCardPayment: validateNonTpPaymentinfo(); " +creditForm.toStringStudent());
		           if(creditcardPaymentValidator==null){
		                 creditcardPaymentValidator= new CreditcardPaymentValidator();
		           }
		           Payments nonTpPayments=new Payments(creditcardPaymentValidator);
		           return nonTpPayments.validatenNonTpPaymentInfo(creditForm, mapping, request, response) ;
	} 
	
	/**
	 * 
	 * Validate tp payment input
	 * 
	 */
	public String validateTpPaymentInfo(CreditCardPaymentForm creditForm,ActionMapping mapping,HttpServletRequest request, HttpServletResponse response ) throws Exception{
		
		log.debug("CreditCardPayment: validateTpPaymentinfo(); " +creditForm.toStringStudent());
		if(creditcardPaymentValidator==null){
		     creditcardPaymentValidator= new CreditcardPaymentValidator();
		}
		
		Payments tpPayments=new Payments(creditcardPaymentValidator);
		return tpPayments.validateTpPaymentInfo(creditForm, mapping, request, response);
	} 
	
	/**
	 * 
	 * Validate non-tp payment applyinput
	 * 
	 */
	public String validateApplyPaymentInfo(CreditCardPaymentForm creditForm,ActionMapping mapping,HttpServletRequest request, HttpServletResponse response ) throws Exception{
		
		Double totalamount = new Double(0);
		
		
		log.debug("CreditCardPayment: validateApplyPaymentinfo(); " +creditForm.toStringStudent());
		if(creditcardPaymentValidator==null){
		     creditcardPaymentValidator= new CreditcardPaymentValidator();
		}
		// student nr
		String stuNumErrMsg=creditcardPaymentValidator.checkStudentNr(creditForm.getStudent().getStudentNumber());
		if(!stuNumErrMsg.equals("")){
			return stuNumErrMsg;
		}
		// email addr
		String emailErrorMsg=creditcardPaymentValidator.validateEmail(creditForm);
		if(!emailErrorMsg.equals("")){
			return emailErrorMsg;
		}
		// credit card nr
		String creditcardNumErrorMsg=creditcardPaymentValidator.validateCredicardNum(creditForm);
		if(!creditcardNumErrorMsg.equals("")){
			return creditcardNumErrorMsg;
		}
		
		//  card holders name
		String creditcardHolderNameErrorMsg=creditcardPaymentValidator.validateCreditcardHolderName(creditForm);
		if(!creditcardHolderNameErrorMsg.equals("")){
			return creditcardHolderNameErrorMsg;
		}
		// cvv number
		String cvvNumErrorMsg= creditcardPaymentValidator.validateCvvNum(creditForm);
		if(!cvvNumErrorMsg.equals("")){
			return cvvNumErrorMsg;
		}
		// total cc amount
		if("".equals(creditForm.getCcTotalAmountInput().trim())){
			return "Invalid total amount being paid.";
		}
		
		creditForm.setCcTotalAmount(Double.parseDouble(creditForm.getCcTotalAmountInput()));
		
		return "";
	} 

	/**
	 * 
	 * Process non-tp payment
	 * 
	 */
	public String processNonTpPayment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		CreditCardPaymentForm creditForm = (CreditCardPaymentForm) form;
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
		toolManager = (ToolManager) ComponentManager.get(ToolManager.class);
		UsageSession usageSession = usageSessionService.getSession();
		ActionMessages messages = new ActionMessages();
		log.debug("CreditCardPayment: processNonTpPayment()");
				
		// set card number from request
		creditForm.setCardNumber((String) request.getParameter("cnumber"));
		//set cvv nr from request
		creditForm.setCvvNo((String) request.getParameter("cvvnumber"));
		
		// validate input
		String errMsg = validateNonTpPaymentInfo(creditForm,mapping, request, response);
		if(!"".equals(errMsg)){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.coolgenerror", errMsg));
			addErrors(request, messages);
			request.setAttribute("cnumber", creditForm.getCardNumber());
			request.setAttribute("cvvnumber", creditForm.getCvvNo());
			return nonTpPaymentInput(mapping, form, request, response);
		}
		
		// Process payment
		Sfrrf03sMntOnlineCcPayments op = new Sfrrf03sMntOnlineCcPayments();
		operListener opl = new operListener();
		op.addExceptionListener(opl);
		op.clear();
		/* op.setTracing(Trace.MASK_ALL); */
		// op.setInIpAddressCsfStringsString15(request.getRemoteAddr());
		op.setInCsfClientServerCommunicationsClientVersionNumber((short) 3);
		op.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
		op.setInCsfClientServerCommunicationsAction("A");
		op.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
		// user nr		
		op.setInSecurityWsUserNumber(99998);
		op.setInWsWorkstationCode("internet");
		// student nr
		op.setInWsStudentNr(Integer.parseInt(creditForm.getStudent().getStudentNumber()));
		// qual
		op.setInWsQualificationCode(creditForm.getQual().getQualCode());
		// email
		op.setInWsAddressV2EmailAddress(creditForm.getStudent().getEmailAddress());
		// lib card
		if ("on".equalsIgnoreCase(creditForm.getPayLibraryFee())){
			op.setInSmarctcardBundleDocumentTotalAmount(creditForm.getLibraryFee());
		}
		// MR fee
		if ("on".equalsIgnoreCase(creditForm.getPayMatricFirstAppFee())){
			op.setInMrBundleDocumentTotalAmount(creditForm.getMatricFirstAppFee());
		}
		//3G data bundle
		if ("on".equalsIgnoreCase(creditForm.getPayThreeGDataBundleFee())){
			op.setIn3gBundleDocumentTotalAmount(creditForm.getThreeGDataBundleFee());
		}
		// Study fee
		op.setInStudyFeeBundleDocumentTotalAmount(creditForm.getStudyFeeAmount());
		//lib fine
		op.setInLdBundleDocumentTotalAmount(creditForm.getLibraryFineFeeAmount());
		// credit card nr
		op.setInBundleDocumentAccountNr(creditForm.getCardNumber());
		// cvv nr
		op.setInCvvWsPostilionTranStartCvvNr(creditForm.getCvvNo());
		// card expiry date
		op.setInBundleDocumentCreditCardExpiryDate(Integer.parseInt(creditForm.getExpiryYear()+creditForm.getExpiryMonth()));
		// budget period
		op.setInBundleDocumentCreditCardBudgetPeriod(Short.parseShort(creditForm.getBudgetPeriod()));
		// card holder
		op.setInBundleDocumentAccountHolder(creditForm.getCardHolder());
		//total card amount
		op.setInBundleDocumentAmountCreditCard(creditForm.getCcTotalAmount());
		
		//--------------------------------------------------------------------------------
		log.debug("CreditCardPayment: processNonTpPayment(); " +creditForm.toStringStudent()+creditForm.toStringFees());
		//--------------------------------------------------------------------------------
		
		op.execute();
		boolean exceptionHappened=false;
		if (opl.getException() != null)
			exceptionHappened=true;
			//throw opl.getException();
		if (op.getExitStateType() < 3)
			exceptionHappened=true;
			//throw new Exception(op.getExitStateMsg());
		if(exceptionHappened){
			String errmsg = "Technical Error happened, check the data you entered and try again";
		    messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.coolgenerror", errmsg));
		    addErrors(request, messages);
		    return "nonTpPayment";
		}else{
		
		      // Check error flag
		      if ("Y".equalsIgnoreCase(op.getOutRetryXtnIefSuppliedFlag())){
			          String errormsg = op.getOutCsfStringsString500();
			          messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.coolgenerror", errormsg));
			          addErrors(request, messages);
			          return "nonTpPayment";
		      }else{	
			          creditForm.setSummaryMessage(op.getOutCsfStringsString500());
			          if("Y".equals(op.getOutErrorIefSuppliedFlag())){
				          creditForm.setErrorOccured(true);
			          }else{
				            creditForm.setErrorOccured(false);
			          }
         			  //clear form vars
			          reset(creditForm);
		      }
		}
		
		eventTrackingService.post(
				eventTrackingService.newEvent(EventTrackingTypes.EVENT_CREDITCARD_PAYMENT, toolManager.getCurrentPlacement().getContext(), false));
		//--------------------------------------------------------------------------------
		log.debug("CreditCardPayment: processNonTpPayment() result for stud: " +creditForm.getStudent().getStudentNumber()+"; "+creditForm.getSummaryMessage());
		//--------------------------------------------------------------------------------
		
		return ("summary");
	} 
	
	/**
	 * 
	 * Method to validate and create work flow for the credit card payment
	 * 
	 */
	public String processTpPayment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		CreditCardPaymentForm creditForm = (CreditCardPaymentForm) form;
		ActionMessages messages = new ActionMessages();
		StudentQueryDAO dao = new StudentQueryDAO();
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
		toolManager = (ToolManager) ComponentManager.get(ToolManager.class);
		log.debug("CreditCardPayment: processTpPayment()");
				
		// set card number from request
		creditForm.setCardNumber((String) request.getParameter("cnumber"));
		//set cvv nr from request
		creditForm.setCvvNo((String) request.getParameter("cvvnumber"));
		
	  // student indicated that he/she wants to cancel the library fee
		if (!creditForm.isCancel() && creditForm.isSmartCardChoice()){
			creditForm.setCancelSmartCard("");
			// reset to original amount
			creditForm.setLibraryFeeForStudent(creditForm.getLibraryFeeAmount());
			// update database field
			dao.requestSmartCard(creditForm.getStudent().getStudentNumber());
		}else if (creditForm.isCancel() && creditForm.isSmartCardChoice()){
		creditForm.setCancelSmartCard("Y");
			// update database field
			dao.cancelSmartCard(creditForm.getStudent().getStudentNumber());
		}
	//------------------
	// validate input
	//------------------
		String errMsg = validateTpPaymentInfo(creditForm,mapping, request, response);
		if(!"".equals(errMsg)){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.coolgenerror", errMsg));
			addErrors(request, messages);
			request.setAttribute("cnumber", creditForm.getCardNumber());
			request.setAttribute("cvvnumber", creditForm.getCvvNo());
			return nonTpPaymentInput(mapping, form, request, response);
		}
		
		// Process payment
		Sfrrf03sMntOnlineCcPayments op = new Sfrrf03sMntOnlineCcPayments();
		operListener opl = new operListener();
		op.addExceptionListener(opl);
		op.clear();
		/* op.setTracing(Trace.MASK_ALL); */
		// op.setInIpAddressCsfStringsString15(request.getRemoteAddr());
		op.setInCsfClientServerCommunicationsClientVersionNumber((short) 3);
		op.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
		op.setInCsfClientServerCommunicationsAction("A");
		op.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
		// user nr		
		op.setInSecurityWsUserNumber(99998);
		op.setInWsWorkstationCode("internet");
		// student nr
		op.setInWsStudentNr(Integer.parseInt(creditForm.getStudent().getStudentNumber()));
		// qual
		op.setInWsQualificationCode(creditForm.getQual().getQualCode());
		// email
		op.setInWsAddressV2EmailAddress(creditForm.getStudent().getEmailAddress());
		// lib card
		op.setInSmarctcardBundleDocumentTotalAmount(creditForm.getLibraryFeeForStudent());
		// MR fee
		op.setInMrBundleDocumentTotalAmount(creditForm.getMatricFeeForStudent());
		//lib fine
		op.setInLdBundleDocumentTotalAmount(creditForm.getLibraryFineFeeAmount());
		// 3G fee
		//op.setIn3gBundleDocumentTotalAmount(creditForm.getThreeGDataBundleFeeForStudent());
		// Study fee
		op.setInStudyFeeBundleDocumentTotalAmount(creditForm.getStudyFeeAmount());
		// credit card nr
		op.setInBundleDocumentAccountNr(creditForm.getCardNumber());
		// cvv nr
		op.setInCvvWsPostilionTranStartCvvNr(creditForm.getCvvNo());
		// card expiry date
		op.setInBundleDocumentCreditCardExpiryDate(Integer.parseInt(creditForm.getExpiryYear()+creditForm.getExpiryMonth()));
		// budget period
		op.setInBundleDocumentCreditCardBudgetPeriod(Short.parseShort(creditForm.getBudgetPeriod()));
		// card holder
		op.setInBundleDocumentAccountHolder(creditForm.getCardHolder());
		//total card amount
		op.setInBundleDocumentAmountCreditCard(creditForm.getCcTotalAmount());
		
		//--------------------------------------------------------------------------------
		log.debug("CreditCardPayment: processTpPayment(); " +creditForm.toStringStudent()+creditForm.toStringFees());
		//--------------------------------------------------------------------------------
		
		op.execute();
		boolean exceptionHappened=false;
		if (opl.getException() != null)
			exceptionHappened=true;
			//throw opl.getException();
		if (op.getExitStateType() < 3)
			exceptionHappened=true;
			//throw new Exception(op.getExitStateMsg());
		if(exceptionHappened){
			String errmsg = "Technical Error happened, check the data you entered and try again";
		    messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.coolgenerror", errmsg));
		    addErrors(request, messages);
		    return "tpPayment";
		}else{
		
		      // Check error flag
		      if ("Y".equalsIgnoreCase(op.getOutRetryXtnIefSuppliedFlag())){
			          String errormsg = op.getOutCsfStringsString500();
			          messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.coolgenerror", errormsg));
			          addErrors(request, messages);
			          return "tpPayment";
		      }else{	
			          creditForm.setSummaryMessage(op.getOutCsfStringsString500());
			          if("Y".equals(op.getOutErrorIefSuppliedFlag())){
				          creditForm.setErrorOccured(true);
			          }else{
				            creditForm.setErrorOccured(false);
			          }
         			  //clear form vars
			          reset(creditForm);
		      }
		}
		
		eventTrackingService.post(
				eventTrackingService.newEvent(EventTrackingTypes.EVENT_CREDITCARD_PAYMENT, toolManager.getCurrentPlacement().getContext(), false));
		//--------------------------------------------------------------------------------
		log.debug("CreditCardPayment: processTpPayment() result for stud: " +creditForm.getStudent().getStudentNumber()+"; "+creditForm.getSummaryMessage());
		//--------------------------------------------------------------------------------
		
		return ("summary");
	} 
	
	/**
	 * 
	 * Method to validate and create work flow for the credit card payment
	 * for student numbers application payments
	 * 
	 */
	public String processApplyPayment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		CreditCardPaymentForm creditForm = (CreditCardPaymentForm) form;
		ActionMessages messages = new ActionMessages();
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
		toolManager = (ToolManager) ComponentManager.get(ToolManager.class);
		log.debug("CreditCardPayment: processApplyPayment()");
				
		// set card number from request
		creditForm.setCardNumber((String) request.getParameter("cnumber"));
		//set cvv nr from request
		creditForm.setCvvNo((String) request.getParameter("cvvnumber"));
		
	//------------------
	// validate input
	//------------------
		String errMsg = validateApplyPaymentInfo(creditForm,mapping, request, response);
		if(!"".equals(errMsg)){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.coolgenerror", errMsg));
			addErrors(request, messages);
			request.setAttribute("cnumber", creditForm.getCardNumber());
			request.setAttribute("cvvnumber", creditForm.getCvvNo());
			return nonTpPaymentInput(mapping, form, request, response);
		}
		
		// Process payment
		Sfrrf03sMntOnlineCcPayments op = new Sfrrf03sMntOnlineCcPayments();
		operListener opl = new operListener();
		op.addExceptionListener(opl);
		op.clear();
		/* op.setTracing(Trace.MASK_ALL); */
		// op.setInIpAddressCsfStringsString15(request.getRemoteAddr());
		op.setInCsfClientServerCommunicationsClientVersionNumber((short) 3);
		op.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
		op.setInCsfClientServerCommunicationsAction("A");
		op.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
		// user nr		
		op.setInSecurityWsUserNumber(99998);
		// student nr
		op.setInWsStudentNr(Integer.parseInt(creditForm.getStudent().getStudentNumber()));
		// email
		op.setInWsAddressV2EmailAddress(creditForm.getStudent().getEmailAddress());
		// applyForAmount
		//
		// credit card nr
		op.setInBundleDocumentAccountNr(creditForm.getCardNumber());
		// cvv nr
		op.setInCvvWsPostilionTranStartCvvNr(creditForm.getCvvNo());
		// card expiry date
		op.setInBundleDocumentCreditCardExpiryDate(Integer.parseInt(creditForm.getExpiryYear()+creditForm.getExpiryMonth()));
		// budget period
		op.setInBundleDocumentCreditCardBudgetPeriod(Short.parseShort(creditForm.getBudgetPeriod()));
		// card holder
		op.setInBundleDocumentAccountHolder(creditForm.getCardHolder());
		//total card amount
		op.setInBundleDocumentAmountCreditCard(creditForm.getCcTotalAmount());
		op.setInApplicationBundleDocumentTotalAmount(creditForm.getApplyAmount());
		
		//--------------------------------------------------------------------------------
		log.debug("CreditCardPayment: processApplyPayment(); " +creditForm.toStringStudent()+creditForm.toStringFees());
		//--------------------------------------------------------------------------------
		
		op.execute();
		boolean exceptionHappened=false;
		if (opl.getException() != null)
			exceptionHappened=true;
			//throw opl.getException();
		if (op.getExitStateType() < 3)
			exceptionHappened=true;
			//throw new Exception(op.getExitStateMsg());
		if(exceptionHappened){
			String errmsg = "Technical Error happened, payment not processed";
			creditForm.setSummaryMessage(errmsg);
			creditForm.setErrorOccured(true);
		}else{
			// Check error flag
		
		     if ("Y".equalsIgnoreCase(op.getOutRetryXtnIefSuppliedFlag())){
			       String errormsg = op.getOutCsfStringsString500();
			       messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.coolgenerror", errormsg));
			       addErrors(request, messages);
			       return "applyPayment";
		     }else{	
			           creditForm.setSummaryMessage(op.getOutCsfStringsString500());
			           if("Y".equals(op.getOutErrorIefSuppliedFlag())){
				             creditForm.setErrorOccured(true);
			           }else{
				              creditForm.setErrorOccured(false);
			          }
  			         // clear form vars
			          reset(creditForm);
		     }
		}
		eventTrackingService.post(
				eventTrackingService.newEvent(EventTrackingTypes.EVENT_CREDITCARD_PAYMENT, toolManager.getCurrentPlacement().getContext(), false));
		//--------------------------------------------------------------------------------
		log.debug("CreditCardPayment: processApplyPayment() result for stud: " +creditForm.getStudent().getStudentNumber()+"; "+creditForm.getSummaryMessage());
		//--------------------------------------------------------------------------------
		
		return ("summary");
	} 

	public ActionForward nextStep(ActionMapping mapping, ActionForm form,
		                	HttpServletRequest request, HttpServletResponse response){
		                         String nextPage = "";
		                        try{
		                                 log.debug("CreditCardPayment: nextStep(); page:"+request.getParameter("page"));
		        		                 if ("studentinput".equalsIgnoreCase(request. getParameter("page"))) {
			                                      nextPage = qualificationInput(mapping, form, request, response);
		                                 } else if ("qualinput".equalsIgnoreCase(request.getParameter("page"))) {
			                                          nextPage = nonTpPaymentInput(mapping,form,request, response);
		                                 } else if ("nontpinput".equalsIgnoreCase(request.getParameter("page"))) {
			                                         nextPage = processNonTpPayment(mapping,form,request, response);
		                                 } else if ("tpinput".equalsIgnoreCase(request.getParameter("page"))) {
			                                          nextPage = processTpPayment(mapping,form,request, response);
		                                 }else if ("applyinput".equalsIgnoreCase(request.getParameter("page"))) {
			                                        nextPage = processApplyPayment(mapping,form,request, response);
		                                 }
		                       }catch(Exception ex){
		                    	                return mapping.findForward("studentInput");
		                      }
		            return mapping.findForward(nextPage);
	}
		

	/**
	 * Method when user wants to go back to credit payment information
	 */
	public ActionForward back(ActionMapping mapping, ActionForm form,
			              HttpServletRequest request, HttpServletResponse response){
		                        String nextPage = "";	
	                        	try{
		                                log.debug("CreditCardPayment: back(); page:"+request.getParameter("page"));
		                        			
		                                if ("qualinput".equalsIgnoreCase(request.getParameter("page"))) {
			                                     return studentInput(mapping, form, request, response);
		                                } else if ("nontpinput".equalsIgnoreCase(request.getParameter("page"))) {
                                           			nextPage = "qualInput";
		                                } else if ("tpinput".equalsIgnoreCase(request.getParameter("page"))) {
			                                         nextPage = "qualInput";
		                                }else if ("applyinput".equalsIgnoreCase(request.getParameter("page"))) {
			                                        return studentInput(mapping, form, request, response);
		                                }
	                           }catch(Exception ex){
	                        	             return mapping.findForward("studentInput");
                              }
		                      return mapping.findForward(nextPage);

	}
	
	/**
	 * Method to reset form
	 */
	public void reset(CreditCardPaymentForm form){

		log.debug("CreditCardPayment: reset(form)");
		
		form.setBalance("");
		form.setCreditDebitIndicator("");
		form.setStudent(new Student());
		form.setBudgetList(new ArrayList<String>());
		form.setBudgetPeriod("");
		form.setCardHolder("");
		form.setCardNumber("");
		form.setCcTotalAmountInput("0.00");
		form.setCcTotalAmount(0);
		form.setStudyFeeAmount(0);
		form.setStudyFeeAmountInput("0.00");
		form.setLibraryFineFeeAmount(0);
		form.setLibraryFineFeeText("");
		form.setLibraryFineFeeAmountInput("0.00");
		form.setCvvNo("");
		form.setExpiryDate("");
		form.setExpiryMonth("");
		form.setExpiryYear("");
		form.setFullAccount(0);
		form.setCanChooseLibraryCard(true);
		form.setCanChooseMatric(true);
		form.setLibraryFee(0);
		form.setLibraryFeeText("");
		form.setMatricFee(0);
		form.setMatricFirstAppFee(0);
		form.setMatricFirstAppFeeText("");
		form.setMatricReApp("");
		form.setMatricReAppFee("");
		form.setPayLibraryFee("");
		form.setPayMatricFirstAppFee("");
		form.setFormattedLibraryFeeForStudent("");
		form.setFormattedMatricFeeForStudent("");
		form.setRegStatus("");
		form.setMinimumStudyFee(0);
		form.setYearList(new ArrayList<String>());
		form.setApplyAmountInput("0.00");
		form.setApplyAmount(0);
		form.setSmartCardChoice(false);
		form.setCancel(false);
		form.setCanChooseThreeGDataBundle(true);
		form.setThreeGDataBundleFee(0);
		form.setThreeGDataBundleFeeText("");
		form.setFormattedThreeGDataBundleFeeForStudent("");
		form.setPayThreeGDataBundleFee("");
		//form.setErrorOccured(false);

	}

	/**
	 * User cancelled 
	 */
	public ActionForward cancel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
                 try{
		             log.debug("CreditCardPayment: cancel()");
		             sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
		             userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
		             CreditCardPaymentForm creditForm = (CreditCardPaymentForm) form;
		
             		 // Check for already logged in student
		             Session currentSession = sessionManager.getCurrentSession();
		             String userId = currentSession.getUserId();
		             User user = null;
		             if (userId != null) {
			               user = userDirectoryService.getUser(userId);
			               if ("Student".equalsIgnoreCase(user.getType())){
				                 creditForm.getStudent().setStudentNumber(user.getEid());
				                 reset(creditForm);
                				// go back to finance tool
				                 ActionForward af = new ActionForward(ServerConfigurationService.getServerUrl()+
				            		             "/unisa-findtool/default.do?sharedTool=unisa.payments",true);
				                 return af;
			               }
		             }
                 }catch(Exception ex){
                	                    return mapping.findForward("studentInput");
                 }
		return studentInput(mapping, form, request, response);

	}
	public ActionForward logout(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response){
		            CreditCardPaymentForm creditForm = (CreditCardPaymentForm) form;
		            try{
		            	     reset(creditForm);
		            	     ActionForward af = new ActionForward();
		            		 if(creditForm.isStudentUser()){
		            		       af =new ActionForward(ServerConfigurationService.getServerUrl()+
		            			      "/unisa-findtool/default.do?sharedTool=unisa.payments",true);
		            		 }else{
		            			    
		            			    af= new ActionForward("http://www.unisa.ac.za", true);
		            		 }
		            		return af;
		                }catch(Exception  ex){
		                	 return mapping.findForward("studentInput");
		                }
   }
   public ActionForward unspecified(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response){
		           try{
		                    log.debug("CreditcardPaymentAction: unspecified method call -no value for parameter in request");
		           }catch(Exception ex){
		        	                     return mapping.findForward("studentInput");
                   }  
		return mapping.findForward("home");

	}
}
