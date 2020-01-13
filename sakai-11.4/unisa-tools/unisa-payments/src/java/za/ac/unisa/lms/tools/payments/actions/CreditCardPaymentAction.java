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
import org.sakaiproject.event.api.UsageSession;
import org.sakaiproject.event.api.EventTrackingService;
import org.sakaiproject.event.api.UsageSessionService;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.ToolManager;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserDirectoryService;
import org.sakaiproject.component.cover.ComponentManager;

import za.ac.unisa.lms.constants.EventTrackingTypes;
import za.ac.unisa.lms.tools.payments.dao.degreeQueryDAO;
import za.ac.unisa.lms.tools.payments.forms.CreditCardPaymentForm;
import za.ac.unisa.utils.WorkflowFile;
import Srcds01h.Abean.Srcds01sMntStudContactDetail;

/**
 * MyEclipse Struts
 * Creation date: 09-15-2005
 *
 * XDoclet definition:
 * @struts:action validate="true"
 * @struts:action-forward name="input" path="/WEB-INF/jsp/creditform.jsp" contextRelative="true"
 */
public class CreditCardPaymentAction extends LookupDispatchAction  {

	private double minAmount = 200;
	private SessionManager sessionManager;
	private UserDirectoryService userDirectoryService;
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

	// --------------------------------------------------------- Instance Variables

	// --------------------------------------------------------- Methods

	protected Map getKeyMethodMap() {
	      Map map = new HashMap();
	      map.put("button.back", "back");
	      map.put("button.cancel", "cancel");
	      map.put("button.continue", "continue");
	      map.put("button.save", "save");
	      map.put("input","input");
	      map.put("confirm","confirm");
	      map.put("other","other");
	      return map;
	  }

	/**
	 * Retrieve student data to be displayed when student chose credit card payment
	 */
	public ActionForward input(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) throws Exception {

	   	request.setAttribute("answer", getBudgetOptionsDropList());

		ActionMessages messages = new ActionMessages();
		CreditCardPaymentForm creditCardPaymentsForm1 = (CreditCardPaymentForm) form;
		sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
		userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);

		// Get user details, set in default action
		User user = null;

		Session currentSession = sessionManager.getCurrentSession();
		String userID = currentSession.getUserId();

		if (userID != null) {
			user =userDirectoryService.getUser(userID);
			creditCardPaymentsForm1.setStno(user.getEid());
		}

		if ((creditCardPaymentsForm1.getStno() == null)||(creditCardPaymentsForm1.getStno().equals(""))) {
			return mapping.findForward("invaliduser");
		}

		if (!"student".equalsIgnoreCase(user.getType())){
			return mapping.findForward("invaliduser");
		}
		if (messages.isEmpty()) {

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
			op.setInWsStudentNr(Integer.parseInt(creditCardPaymentsForm1.getStno()));
		    op.execute();
		    if (opl.getException() != null) {
		      	throw opl.getException();
		    }
		    if (op.getExitStateType() < 3) {
		       	throw new Exception(op.getExitStateMsg());
		    }

		    String Errormsg = op.getOutCsfStringsString500();
		    if ((Errormsg != null) && (!Errormsg.equals(""))) {
		       	messages.add(ActionMessages.GLOBAL_MESSAGE,
		    	new ActionMessage("error.coolgenerror", Errormsg));
		    }

		    //Personal information
		    creditCardPaymentsForm1.setTitle(op.getOutWsStudentMkTitle());
		    creditCardPaymentsForm1.setInitials(op.getOutWsStudentInitials());
		    creditCardPaymentsForm1.setSurname(op.getOutWsStudentSurname());
		    creditCardPaymentsForm1.setFirstNames(op.getOutWsStudentFirstNames());
		    DateFormat strDate = new SimpleDateFormat( "dd-MMM-yyyy" );
		    creditCardPaymentsForm1.setBirthDate(strDate.format(op.getOutWsStudentBirthDate().getTime()));

		    // Postal address
		    Vector postalRecords = new Vector();
		    if (!"".equalsIgnoreCase(op.getOutPostalWsAddressV2AddressLine1())){
		      	postalRecords.add(op.getOutPostalWsAddressV2AddressLine1());
		    }
		    if (!"".equalsIgnoreCase(op.getOutPostalWsAddressV2AddressLine2())){
		      	postalRecords.add(op.getOutPostalWsAddressV2AddressLine2());
		    }
		    if (!"".equalsIgnoreCase(op.getOutPostalWsAddressV2AddressLine3())){
		       	postalRecords.add(op.getOutPostalWsAddressV2AddressLine3());
		    }
		    if (!"".equalsIgnoreCase(op.getOutPostalWsAddressV2AddressLine4())){
		      	postalRecords.add(op.getOutPostalWsAddressV2AddressLine4());
		    }
		    if (!"".equalsIgnoreCase(op.getOutPostalWsAddressV2AddressLine5())){
		       	postalRecords.add(op.getOutPostalWsAddressV2AddressLine5());
		    }
		    if (!"".equalsIgnoreCase(op.getOutPostalWsAddressV2AddressLine6())){
		       	postalRecords.add(op.getOutPostalWsAddressV2AddressLine6());
		    }
		    creditCardPaymentsForm1.setPostal(postalRecords);
		    creditCardPaymentsForm1.setPostalCode(Short.toString(op.getOutPostalWsAddressV2PostalCode()));
		    if (creditCardPaymentsForm1.getPostalCode().length()!= 4){
		       	creditCardPaymentsForm1.setPostalCode("0" + creditCardPaymentsForm1.getPostalCode());
		    }

		    //Contact numbers
		    creditCardPaymentsForm1.setHomePhone(op.getOutContactsWsAddressV2HomeNumber());
		    creditCardPaymentsForm1.setCell(op.getOutContactsWsAddressV2CellNumber());
		    creditCardPaymentsForm1.setEmailAddress(op.getOutContactsWsAddressV2EmailAddress());
		    creditCardPaymentsForm1.setPrevEmailAddress(op.getOutContactsWsAddressV2EmailAddress());

		    // get the students degree
			degreeQueryDAO db = new degreeQueryDAO();
			creditCardPaymentsForm1.setDegree(db.getStudentDegree(creditCardPaymentsForm1.getStno()));

		} // End of if (messages.isEmpty())

		//		 Load budget options list
		request.setAttribute("answer", getBudgetOptionsDropList());


		if (!messages.isEmpty()) {
		   	addErrors(request, messages);
		} // end of if (!messages.isEmpty())

		return mapping.findForward("paymentInput");
	}

	/**
	 * Method to validate and create workflow for the credit card payment
	 */
	public ActionForward save(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)throws Exception{

		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
		toolManager = (ToolManager) ComponentManager.get(ToolManager.class);
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);

		// Load budget options list
		request.setAttribute("answer", getBudgetOptionsDropList());

		//go to first screen on cancel
		if (isCancelled(request)) {
			return (mapping.findForward("paymentCancel"));
		}

		// reference the form
		CreditCardPaymentForm creditCardPaymentForm = (CreditCardPaymentForm) form;
		UsageSession usageSession = usageSessionService.getSession();

		if ((creditCardPaymentForm.getStno() == null)||(creditCardPaymentForm.getStno().equals(""))) {
			return mapping.findForward("invaliduser");
		}

		/**
		 * If no errors occurred create the workflow document
		 */
		writeWorkflow(creditCardPaymentForm);
		eventTrackingService.post(
				eventTrackingService.newEvent(EventTrackingTypes.EVENT_PAYMENTS_CCPAYMENT, toolManager.getCurrentPlacement().getContext(), false),usageSession);
		return (mapping.findForward("paymentSuccess"));
	} //end of method save

	/**
	 * Method for student to confirm the payment detail.
	 */
	public ActionForward confirm(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)throws Exception{

	   	// Load budget options list
	   	request.setAttribute("answer", getBudgetOptionsDropList());

		//go to first screen on cancel
		if (isCancelled(request)) {
			return (mapping.findForward("paymentCancel"));
		}

		// reference the form
		ActionMessages messages = new ActionMessages(); // class that encapsulates messages.
		CreditCardPaymentForm creditCardPaymentForm = (CreditCardPaymentForm) form;

		// Credit number card are displayed as 4 parts on the form,
		// put them together to make 1 credit card number and set the field in the form class.
		String ccno1 = " ";
		String ccno2 = " ";
		String ccno3 = " ";
		String ccno4 = " ";
		if (creditCardPaymentForm.getCreditCardNumber1().length() > 0) {
			ccno1 = creditCardPaymentForm.getCreditCardNumber1();
		}
		if (creditCardPaymentForm.getCreditCardNumber2().length() > 0) {
			ccno2 = creditCardPaymentForm.getCreditCardNumber2();
		}
		if (creditCardPaymentForm.getCreditCardNumber3().length() > 0) {
			ccno3 = creditCardPaymentForm.getCreditCardNumber3();
		}
		if (creditCardPaymentForm.getCreditCardNumber4().length() > 0) {
			ccno4 = creditCardPaymentForm.getCreditCardNumber4();
		}
		//String tmpCreditCardNr = creditCardPaymentForm.getCreditCardNumber1() + creditCardPaymentForm.getCreditCardNumber2()+ creditCardPaymentForm.getCreditCardNumber3()+ creditCardPaymentForm.getCreditCardNumber4();
		String tmpCreditCardNr = ccno1+ccno2+ccno3+ccno4;
		creditCardPaymentForm.setCreditCardNumber(tmpCreditCardNr);

		// Confirmation Credit number card are displayed as 4 parts on the form,
		// put them together to make 1 credit card number and set the field in the form class.
		String confno1 = " ";
		String confno2 = " ";
		String confno3 = " ";
		String confno4 = " ";
		if (creditCardPaymentForm.getConfCreditCard1().length() > 0) {
			confno1 = creditCardPaymentForm.getConfCreditCard1();
		}
		if (creditCardPaymentForm.getConfCreditCard2().length() > 0) {
			confno2 = creditCardPaymentForm.getConfCreditCard2();
		}
		if (creditCardPaymentForm.getConfCreditCard3().length() > 0) {
			confno3 = creditCardPaymentForm.getConfCreditCard3();
		}
		if (creditCardPaymentForm.getConfCreditCard4().length() > 0) {
			confno4 = creditCardPaymentForm.getConfCreditCard4();
		}
		//String tmpConfCreditCardNr = creditCardPaymentForm.getConfCreditCard1() + creditCardPaymentForm.getConfCreditCard2()+ creditCardPaymentForm.getConfCreditCard3()+ creditCardPaymentForm.getConfCreditCard4();
		String tmpConfCreditCardNr = confno1+confno2+confno3+confno4;
		creditCardPaymentForm.setConfCreditCard(tmpConfCreditCardNr);

		/**
		 * Call validate.xml to validate the credit card payment input
		 */
		//Validate credit card payment input
		messages = (ActionMessages) creditCardPaymentForm.validate(mapping, request);
		if (!messages.isEmpty()) {
			addErrors(request, messages);
			return mapping.findForward("paymentInput");
		} else {// end if (!messages.isEmpty())
			/**
			 * if NO ERRORS occured in validation.xml:
			 */

			double tmpStudyFees = 0.00;
			double tmpMatricExempFees = 0.00;
			double tmpTotalAmount = 0.00;

			if (creditCardPaymentForm.getStudyFees() <= 0) {
				tmpStudyFees = 0.00;
				creditCardPaymentForm.setStudyFees(tmpStudyFees);
			} else {
				tmpStudyFees = creditCardPaymentForm.getStudyFees();
			}
			if (creditCardPaymentForm.getMatricExempFees() <= 0) {
				tmpMatricExempFees = 0.00;
				creditCardPaymentForm.setMatricExempFees(tmpMatricExempFees);
			} else {
				tmpMatricExempFees = creditCardPaymentForm.getMatricExempFees();
			}
			//double tmpTotalAmount = Double.parseDouble(creditCardPaymentForm.getStudyFees())+ Double.parseDouble(creditCardPaymentForm.getMatricExempFees());
			tmpTotalAmount = tmpStudyFees + tmpMatricExempFees;

			//int tmpTotalAmount = Integer.parseInt(creditCardPaymentForm.getStudyFees())+Integer.parseInt(creditCardPaymentForm.getMatricExempFees());
			creditCardPaymentForm.setTotal(tmpTotalAmount);

			/**
			 * Validate that only total amount may not by 0
			 */
			double totalAmount = creditCardPaymentForm.getTotal();
			if (totalAmount <= 0){
			    messages.add(ActionMessages.GLOBAL_MESSAGE,
				        new ActionMessage("message.generalmessage", "Please enter a payment amount for Matric exemption fees, Study fees, or both."));
				addErrors(request, messages);
				return mapping.findForward("paymentInput");
			} // end if

			/**
			 * Validate the cellphone number
			 * - must start with + (tested in validator.xml)
			 * - if SA number (starts with +27):
			 * 		= 4th character may not be 0
			 * 		= 4th and 5th char must be combination (82,83,84,72,73,74,76, 78,79)
			 * 		= total length must be 12 characters.
			 */
			String tmpCell = creditCardPaymentForm.getCell();

			if (tmpCell.length() > 0) {
				if ((tmpCell.substring(0,3)).equals("+27")) {
					String tmpBegin =  tmpCell.substring(3,5);

					if (tmpBegin.equals("82")||tmpBegin.equals("83")||tmpBegin.equals("84")||
							tmpBegin.equals("72")||tmpBegin.equals("73")||tmpBegin.equals("74")||
							tmpBegin.equals("76")||tmpBegin.equals("78")||tmpBegin.equals("79")) {
						//	valid
					} else {
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage", "Invalid cell phone number: cell number must begin with 82, 83, 84, 72, 73, 74, 76, 78 or 79 after +27"));
						addErrors(request, messages);
						return mapping.findForward("paymentInput");
					}// if (tmpBegin.equals("82")

					if (tmpCell.length() != 12) {
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage", "Invalid cell phone number: cell number must be 12 characters."));
						addErrors(request, messages);
						return mapping.findForward("paymentInput");
					}

				} // end if ((tmpCell.substring(0,3)).equals("+27"))
			} // end if (tmpCell.length() > 0)

			/**
			 * Validate that the entered credit card number and confirm credit card are the same
			 */
			if (!creditCardPaymentForm.getCreditCardNumber().equals(creditCardPaymentForm.getConfCreditCard())) {
			    messages.add(ActionMessages.GLOBAL_MESSAGE,
				        new ActionMessage("message.generalmessage", "Invalid credit card number confirmation."));
				addErrors(request, messages);
				return mapping.findForward("paymentInput");
			}

			/**
			 * validate that the cv number and confirm cv number are the same.
			 */
			if (!creditCardPaymentForm.getCvNo().equals(creditCardPaymentForm.getConfCVno())) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
				        new ActionMessage("message.generalmessage", "CV numbers do not match."));
				addErrors(request, messages);
				return mapping.findForward("paymentInput");
			}

			/**
			 *  Validate that credit card expiry date is not less than today's date.
			 */
			// Get the current year and month from the Calendar class.
			Calendar calendar1 = Calendar.getInstance();
			int currentMonth = calendar1.get(Calendar.MONTH);
			int currentYear = calendar1.get(Calendar.YEAR);

			// Get the YY characters of returned YYYY
			String currentYearYY = Integer.toString(currentYear);
			currentYearYY = currentYearYY.substring(2);
			// convert YY back to int
			currentYear = Integer.parseInt(currentYearYY);

			// get expiry year and month and convert to integer
			int tmpExpiryYear = Integer.parseInt(creditCardPaymentForm.getExpiryYear());
			int tmpExpiryMonth = Integer.parseInt(creditCardPaymentForm.getExpiryMonth()) + 1; // add one as Calendar.MONTH, january is 0.

			// Validate the current year with the expiry year
			if (currentYear > tmpExpiryYear) {
				// if currentYear is greater than the expiry year
			    messages.add(ActionMessages.GLOBAL_MESSAGE,
				        new ActionMessage("message.generalmessage", "Your credit card seems to have expired."));
				addErrors(request, messages);
				return mapping.findForward("paymentInput");
			} else if (currentYear == tmpExpiryYear) {
				// if current year is the same is the expiry year then
				// compary the months
				if (currentMonth > tmpExpiryMonth) {
				    messages.add(ActionMessages.GLOBAL_MESSAGE,
					        new ActionMessage("message.generalmessage", "Your credit card seems to have expired."));
					addErrors(request, messages);
					return mapping.findForward("paymentInput");
				}
			} // end of if (currentYear > tmpExpiryYear)


			/**
			 * Validate that only straight budget option can be selected
			 * for amounts less than minAmount
			 */
			if ((totalAmount < minAmount)&&
				(!creditCardPaymentForm.getBudgetOption().equalsIgnoreCase("Straight"))) {
			    messages.add(ActionMessages.GLOBAL_MESSAGE,
				        new ActionMessage("message.generalmessage", "Only the Straight credit card option can be selected for amounts less than R200."));
				addErrors(request, messages);
				return mapping.findForward("paymentInput");
			} // end if
		} // end if (messages.isEmpty())


		return (mapping.findForward("paymentConfirm"));
	}

	/**
	 * Method when user wants to go back to credit payment information
	 */
	public ActionForward back(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)throws Exception{

	   	// Load budget options list
	   	request.setAttribute("answer", getBudgetOptionsDropList());

		return (mapping.findForward("paymentInput"));

	}

	/**
	 * Method when user wants to cancel an action
	 */
	public ActionForward cancel(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)throws Exception{

		return (mapping.findForward("paymentCancel"));

	}

	/**
	 * Method for other payments.
	 */
	public ActionForward other(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)throws Exception{

		return (mapping.findForward("paymentOther"));

	}

	/**
	 * Method to create the budget options drop down list
	 *
	 * @return list
	 */
	private ArrayList getBudgetOptionsDropList(){
		//Construct dropdown for credit card budget options.
		ArrayList answer = new ArrayList();
		answer.add(new org.apache.struts.util.LabelValueBean("Select from menu", ""));
		answer.add(new org.apache.struts.util.LabelValueBean("Straight", "Straight"));
		answer.add(new org.apache.struts.util.LabelValueBean("6 Months", "6 Months"));
		answer.add(new org.apache.struts.util.LabelValueBean("12 Months", "12 Months"));
		answer.add(new org.apache.struts.util.LabelValueBean("18 Months", "18 Months"));
		answer.add(new org.apache.struts.util.LabelValueBean("24 Months", "24 Months"));
		return answer;
	}

	/**
	 * method to create actual workflow document.
	 *
	 * @param creditCardPaymentForm
	 * @throws Exception
	 */
	private void writeWorkflow(CreditCardPaymentForm creditCardPaymentForm) throws Exception{
		// declare and initiate the filename
		String fileName = "payment_"+creditCardPaymentForm.getStno();

		// if creditcard number = 15 characters add leading 0 (american express cards)
		String tmpCC = creditCardPaymentForm.getCreditCardNumber();
		if (tmpCC.length() == 15) {
			tmpCC = "0"+tmpCC;
			creditCardPaymentForm.setCreditCardNumber(tmpCC);
		}

		// select the filepath
		String date = (new java.text.SimpleDateFormat("EEEEE dd MMMMM yyyy hh:mm:ss").format(new java.util.Date()).toString());
		WorkflowFile wfFile = new WorkflowFile(fileName);

		// create the content of the workflow file
		wfFile.add("Credit Card Payment for Study Fees or Matric Exemption Fees \r\n");
		wfFile.add("Date received: " + date + "\r\n");
		wfFile.add("The following credit card payment was received through the Unisa Web Server. \r\n");
		wfFile.add("Details: \r\n");
		wfFile.add("Student number = "+creditCardPaymentForm.getStno() + " \r\n");
		wfFile.add("Degree = \r\n"+creditCardPaymentForm.getDegree() + " \r\n");
		wfFile.add("Surname = "+ creditCardPaymentForm.getSurname() +"\r\n");
		wfFile.add("First Names = "+ creditCardPaymentForm.getFirstNames() + " \r\n");
		wfFile.add("Initials = " + creditCardPaymentForm.getInitials()+ "\r\n");
		wfFile.add("Title = " + creditCardPaymentForm.getTitle() + "\r\n");
		wfFile.add("Date of Birth = " + creditCardPaymentForm.getBirthDate() + "\r\n");
		wfFile.add("Home Telephone number = " + creditCardPaymentForm.getHomePhone() + "\r\n");
		wfFile.add("Cell number = " + creditCardPaymentForm.getCell() + "\r\n");
		// if email address has changed show in workflow file
		if (creditCardPaymentForm.getEmailAddress().equalsIgnoreCase(creditCardPaymentForm.getPrevEmailAddress())) {
			wfFile.add("Email address = " + creditCardPaymentForm.getEmailAddress() + "\r\n");
		} else {
			wfFile.add("Email address = " + creditCardPaymentForm.getEmailAddress() + " <<<< Changed >>>> \r\n");
		}
		wfFile.add("Postal Address = " + creditCardPaymentForm.getPostalAddress()+ "\r\n");
		wfFile.add("------------------------------------------------------------------- \r\n");
		wfFile.add("Study Fees = "+ creditCardPaymentForm.getStudyFees() + "\r\n");
		wfFile.add("Matric Exemption Fees = "+ creditCardPaymentForm.getMatricExempFees() + "\r\n");
		wfFile.add("Total Fees = "+ creditCardPaymentForm.getTotal() + "\r\n");
		wfFile.add("Credit Card Number = "+ creditCardPaymentForm.getCreditCardNumber() + "\r\n");
		wfFile.add("CV Number = "+ creditCardPaymentForm.getCvNo() + "\r\n");
		wfFile.add("Expiry Date = "+ creditCardPaymentForm.getExpiryMonth() +"\\" + creditCardPaymentForm.getExpiryYear() + "\r\n");
		wfFile.add("Budget Option = "+ creditCardPaymentForm.getBudgetOption() + "\r\n");
		wfFile.add("Card Holder Name = "+ creditCardPaymentForm.getCardholderSurname() + "\r\n");
		wfFile.add("Card Holder ID Number = "+ creditCardPaymentForm.getCardHolderId() + "\r\n");

		// Close the workflow file
		wfFile.close();
	}
}

