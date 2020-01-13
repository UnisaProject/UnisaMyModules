package za.ac.unisa.lms.tools.contactus.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.util.MessageResources;
import za.ac.unisa.lms.tools.contactus.dao.ContactUsDAO;
import za.ac.unisa.lms.tools.contactus.dao.Qualification;
import za.ac.unisa.lms.tools.contactus.forms.ContactUsForm;
import za.ac.unisa.utils.SendMail;

import java.text.SimpleDateFormat;
import java.util.regex.*;
import java.util.*;
import za.ac.unisa.lms.constants.*;

public class ContactUsAction extends DispatchAction {

	/****************** Mappings to jsp display ************************
	 *** studentviewa:   Type of enquiry page
	 *** studentviewb:   myUnisaEnquiries
	 *** studentviewb2:	 Library Enquiries
	 *** studentviewc1:  Forgotten student number
	 *** studentviewc2a: Forgotten student number
	 *** studentviewc2b: Forgotten student number
	 *** studentviewc2c: Forgotten student number
	 *** studentviewc3:  Forgotten student number
	 *** studentviewc4:  Check email address
	 *** studentviewd:   Student details
	 *** studentviewe:   confirm email address
	 *** thankyou:       Thank you for your query
	 ******************************************************************/

	public ActionForward chooseCategory(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {

		return mapping.findForward("studentviewa");

	}

	public ActionForward categorySelected(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {

		ContactUsForm contactUsForm = (ContactUsForm) form;

		ActionMessages messages = new ActionMessages();

		if(contactUsForm.getCategorySelected() == null || contactUsForm.getCategorySelected().equals("")) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please select your type of enquiry"));
			addErrors(request, messages);
			return mapping.findForward("studentviewa");
		}

		if(contactUsForm.getCategorySelected().equalsIgnoreCase("myUnisa")) {
			return mapping.findForward("studentviewb");
		}else if(contactUsForm.getCategorySelected().equalsIgnoreCase("forgot")) {
			return mapping.findForward("studentviewc1");
		}else if(contactUsForm.getCategorySelected().equalsIgnoreCase("webRegistration")){
			return mapping.findForward("registration.unisa");
		}else if(contactUsForm.getCategorySelected().equalsIgnoreCase("currentRegistration")){
			return mapping.findForward("studentviewd");
		}else if(contactUsForm.getCategorySelected().equalsIgnoreCase("account")){
			return mapping.findForward("studentviewd");
		}else if(contactUsForm.getCategorySelected().equalsIgnoreCase("assignments")){
			return mapping.findForward("studentviewd");
		}else if(contactUsForm.getCategorySelected().equalsIgnoreCase("examinations")){
			return mapping.findForward("studentviewd");
		}else if(contactUsForm.getCategorySelected().equalsIgnoreCase("despatch")){
			return mapping.findForward("studentviewd");
		}else if(contactUsForm.getCategorySelected().equalsIgnoreCase("fellowStudents")){
			return mapping.findForward("studentviewd");
		}else if(contactUsForm.getCategorySelected().equalsIgnoreCase("graduations")){
			return mapping.findForward("studentviewd");
		}else if(contactUsForm.getCategorySelected().equalsIgnoreCase("prescribedBooks")){
			return mapping.findForward("studentviewd");
		}else if(contactUsForm.getCategorySelected().equalsIgnoreCase("library")){
			return mapping.findForward("studentviewb2");
		}else if(contactUsForm.getCategorySelected().equalsIgnoreCase("other")){
			return mapping.findForward("studentviewd");
		}else {
			return mapping.findForward("studentviewa");
		}
	}

	public ActionForward myUnisaCategorySelected(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {

		ContactUsForm contactUsForm = (ContactUsForm)form;
		ActionMessages messages = new ActionMessages();

		String buttonClicked = request.getParameter("button");
		if(buttonClicked.equalsIgnoreCase("back")) {
			return mapping.findForward("studentviewa");
		}

		if(contactUsForm.getMyUnisaCategorySelected() == null || contactUsForm.getMyUnisaCategorySelected().equals("")) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please select your type of enquiry"));
			addErrors(request, messages);
			return mapping.findForward("studentviewb");
		}

		if(buttonClicked.equalsIgnoreCase("continue")) {
			return mapping.findForward("studentviewd");
		}

		return null;
	}

	public ActionForward myUnisaEnquiry(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {

		return mapping.findForward("studentviewe");
	}

	public ActionForward processMessage(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {

		ContactUsForm contactUsForm = (ContactUsForm)form;
		String buttonClicked = request.getParameter("button");

		if(buttonClicked.equalsIgnoreCase("back")) {
			return mapping.findForward("studentviewc");
		}
		if(buttonClicked.equalsIgnoreCase("clear")) {
			clearForgottenStudentNumberForm(contactUsForm);
			return mapping.findForward("studentviewc");
		}
		if(buttonClicked.equalsIgnoreCase("submit")) {
			generateEmailForStudentEnquiry(request, contactUsForm);
			return mapping.findForward("thankyou");
		}

		return null;

	}

	public ActionForward libraryCategorySelected(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {

		ContactUsForm contactUsForm = (ContactUsForm)form;
		String buttonClicked = request.getParameter("button");
		ActionMessages messages = new ActionMessages();


		if(buttonClicked.equalsIgnoreCase("back")) {
			return mapping.findForward("studentviewa");
		}

		if(null == contactUsForm.getLibraryCategorySelected() | "".equalsIgnoreCase(contactUsForm.getLibraryCategorySelected())) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please select your type of enquiry"));
			addErrors(request, messages);
			return mapping.findForward("studentviewb2");
		}

		if(buttonClicked.equalsIgnoreCase("continue")) {
			return mapping.findForward("studentviewd");
		}


		return null;
	}

	public ActionForward processMyUnisaRequest(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ContactUsForm contactUsForm = (ContactUsForm)form;
		String buttonClicked = request.getParameter("button");
		ActionMessages messages = new ActionMessages();

		if(buttonClicked.equalsIgnoreCase("cancel")) {
			return mapping.findForward("studentviewa");
		}

		if(buttonClicked.equalsIgnoreCase("back")) {
			return mapping.findForward("studentviewe");
		}

		if(request.getParameter("button").equalsIgnoreCase("clear")) {
			contactUsForm.setMyUnisaStudentNumber("");
			contactUsForm.setMyUnisaContactNumber("");
			contactUsForm.setMyUnisaEmailAddress("");
			contactUsForm.setMyUnisaEnquiryMessage("");
			return mapping.findForward("studentviewd");
		}

		if(buttonClicked.equalsIgnoreCase("submit")) {
			if(!validateStudentViewD(contactUsForm, messages, mapping, request)) {
				return mapping.findForward("studentviewd");
			}else {
				try {
					ContactUsDAO dao = new ContactUsDAO();
					if(dao.doesStudentExist(contactUsForm.getMyUnisaStudentNumber())) {
						return mapping.findForward("studentviewe");
					}else {
						String message = "The student number you entered does not correspond with our records. Please check and try again. If you have forgotten your student number, please complete the following <a href=\"contactusaction.do?action=forgotStudentNumber&form=complete\">form</a>";
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								 new ActionMessage("message.numbernotfound", message));
						 addErrors(request, messages);
						return mapping.findForward("studentviewd");
					}
				}catch(Exception e) {
					throw new Exception();
				}
			}
		}

		return null;

	}


	public ActionForward processMyUnisaRequestConfirmation(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {

		ContactUsForm contactUsForm = (ContactUsForm)form;
		String buttonClicked = request.getParameter("button");
		ActionMessages messages = new ActionMessages();

		if(buttonClicked.equalsIgnoreCase("submit")) {
			//current registration - administrative enquiries
			if(contactUsForm.getCategorySelected().equalsIgnoreCase("currentRegistration")) {
				ContactUsDAO dao = new ContactUsDAO();
				try {
					ArrayList degree = new ArrayList();
					String countryCode = "";
					boolean registeredForCurrentYear = dao.registered(contactUsForm.getMyUnisaStudentNumber());
					if(registeredForCurrentYear) {
						degree = dao.getDegree(contactUsForm.getMyUnisaStudentNumber());
						countryCode = dao.getCountryCode(contactUsForm.getMyUnisaStudentNumber());
						generateEmailForCurrentRegistration(request, contactUsForm, degree, countryCode);

						return mapping.findForward("thankyou");
					}else {
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage", "You are not registered for the current financial year. Please select another <a href=\"contactusaction.do?action=chooseCategory\">option</a>"));
						addErrors(request, messages);
						return mapping.findForward("studentviewd");
					}
				}catch(Exception e) {
					System.out.println("Exception: " + e.getMessage());
				}
			}else {
				generateEmailForStudentEnquiry(request, contactUsForm);
			}
			return mapping.findForward("thankyou");
		}
		if(buttonClicked.equalsIgnoreCase("back")) {
			return mapping.findForward("studentviewd");
		}

		return null;

	}

	public ActionForward processForgotStudentNumberForm(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {

		ContactUsForm contactUsForm = (ContactUsForm)form;

		String buttonClicked = request.getParameter("button");
		if(buttonClicked.equalsIgnoreCase("Submit")) {
			//sending the email
			generateEmailForForgottenStudentNumber(request, contactUsForm);
			return mapping.findForward("thankyou");
		}
		if(buttonClicked.equalsIgnoreCase("Back")) {
			return mapping.findForward("studentviewc3");
		}

		return mapping.findForward("studentviewc4");
	}

	public ActionForward forgotStudentNumber(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {

		ContactUsForm contactUsForm = (ContactUsForm)form;
		String buttonClicked = request.getParameter("button");
		ActionMessages messages = new ActionMessages();

		if("complete".equalsIgnoreCase(request.getParameter("form"))) {
			messages = null;
			return mapping.findForward("studentviewc1");
		}

		if(buttonClicked.equalsIgnoreCase("Back")) {
			return mapping.findForward("studentviewa");
		}
		if(buttonClicked.equalsIgnoreCase("Clear")) {
			contactUsForm.setForgotSurname("");
			contactUsForm.setForgotName("");
			contactUsForm.setForgotdoby("");
			contactUsForm.setForgotdobm("");
			contactUsForm.setForgotdobd("");
			return mapping.findForward("studentviewc1");
		}
		if(buttonClicked.equalsIgnoreCase("Submit")) {

			if(!validateStudentViewC1(contactUsForm, messages, mapping, request)) {
				return mapping.findForward("studentviewc1");
			}else {
				String dob = contactUsForm.getForgotdoby() + contactUsForm.getForgotdobm() + contactUsForm.getForgotdobd();

				ContactUsDAO dao = new ContactUsDAO();
				try {
					Vector list = dao.retrieveStudentNumber(contactUsForm.getForgotName(), contactUsForm.getForgotSurname(), dob, contactUsForm);
					if(list.isEmpty()) {
						return mapping.findForward("studentviewc2c");
					}else {
						for(int i = 0; i < list.size(); i++) {
							String number = (String)list.elementAt(i);
							if(number.startsWith("7") & number.length()==8) {
								contactUsForm.setStudentNumberNonFormal((String)list.elementAt(i));
							}else {
								contactUsForm.setStudentNumberFormal((String)list.elementAt(i));
							}
						}
					}
				} catch (Exception e) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", e.getMessage()));
					addErrors(request, messages);
					return mapping.findForward("studentviewc1");
				}

				//EventTrackingService.post(
				//		EventTrackingService.newEvent(EventTrackingTypes.EVENT_CONTACTUS_DISPLAYNUMBER, PortalService.getCurrentSiteId(), false));
				return mapping.findForward("studentviewc2a");
			}
		}
		return null;
	}

	public ActionForward studentNumberNotFound(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {

		String buttonClicked = request.getParameter("button");
		if(buttonClicked.equalsIgnoreCase("Back")) {
			return mapping.findForward("studentviewc1");
		}
		if(buttonClicked.equalsIgnoreCase("Advisor Assistance")) {
			return mapping.findForward("studentviewc3");
		}

		return null;
	}

	public ActionForward forgotStudentNumberForm(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {

		ContactUsForm contactUsForm = (ContactUsForm)form;
		String buttonClicked = request.getParameter("button");
		ActionMessages messages = new ActionMessages();

		if(buttonClicked.equalsIgnoreCase("Continue")) {
			if(!validateStudentViewC3(contactUsForm)) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", contactUsForm.getInvalidMessage()));
				addErrors(request, messages);
				return mapping.findForward("studentviewc3");
			}
		}
		if(buttonClicked.equalsIgnoreCase("Cancel")) {
			return mapping.findForward("studentviewc1");
		}
		if(buttonClicked.equalsIgnoreCase("Clear")) {
			System.out.println("OK, clear button clicked...");
			clearForgottenStudentNumberForm(contactUsForm);
			return mapping.findForward("studentviewc3");
		}

		return mapping.findForward("studentviewc4");
	}

	public ActionForward studentNumberFound(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {

		if(request.getParameter("button").equalsIgnoreCase("Back")) {
			return mapping.findForward("studentviewa");
		}

		return null;
	}

	public ActionForward studentNumbersFound(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {

		if(request.getParameter("button").equalsIgnoreCase("Back")) {
			return mapping.findForward("studentviewa");
		}

		return null;

	}

	private boolean validateStudentViewD(ContactUsForm contactUsForm, ActionMessages messages, ActionMapping mapping, HttpServletRequest request) {
		String studentNumber = contactUsForm.getMyUnisaStudentNumber();
		String email = contactUsForm.getMyUnisaEmailAddress();

		if(null == studentNumber | studentNumber.equalsIgnoreCase("")) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please complete all mandatory fields"));
			addErrors(request, messages);
			return false;
		}
		if(studentNumber.length() < 7) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Your student number can't be less than 7 characters long."));
			addErrors(request, messages);
			return false;
		}
		if(null == email | email.equalsIgnoreCase("")) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please complete all mandatory fields"));
			addErrors(request, messages);
			return false;
		}
		if(!Pattern.matches(".+@.+\\.[a-z]+",email)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Incorrect e-mail address format. Please re-type your e-mail address."));
			addErrors(request, messages);
			return false;
		}

		boolean isDigit = false;
		for(int j=0; j<studentNumber.length(); j++) {
		  char alphaa = studentNumber.charAt(j);
		   isDigit = Character.isDigit(alphaa);
		   if(!isDigit) {
			 messages.add(ActionMessages.GLOBAL_MESSAGE,
					 new ActionMessage("message.generalmessage", "Please enter only numeric characters in the student number field"));
			 addErrors(request, messages);
			 return false;
		  }
		}


		return true;

	}

	private boolean validateStudentViewC1(ContactUsForm contactUsForm, ActionMessages messages, ActionMapping mapping, HttpServletRequest request) {
		String surname = contactUsForm.getForgotSurname();
		String name = contactUsForm.getForgotName();
		String dobY = contactUsForm.getForgotdoby();
		String dobM = contactUsForm.getForgotdobm();
		String dobD = contactUsForm.getForgotdobd();

		if(null == surname | surname.equalsIgnoreCase("")) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please complete all mandatory fields"));
			addErrors(request, messages);
			return false;
		}
		if(null == name | name.equalsIgnoreCase("")) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please complete all mandatory fields"));
			addErrors(request, messages);
			return false;
		}
		if(null == dobY | dobY.equalsIgnoreCase("")) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please complete the 'year' field"));
			addErrors(request, messages);
			return false;
		}
		if(dobY.length() <= 3) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "The 'year' field should be 4 digits)"));
			addErrors(request, messages);
			return false;
		}
		if(dobM.length() == 1) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "The 'month' field should be 2 digits"));
			addErrors(request, messages);
			return false;
		}

		if(dobD.length() == 1) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "The 'day' field should be 2 digits"));
			addErrors(request, messages);
			return false;
		}

		//check if any alpha characters was entered
		String dateString = dobY + dobM + dobD;
		boolean isDigit = false;
		for(int j=0; j<dateString.length(); j++) {
		  char alphaa = dateString.charAt(j);
		   isDigit = Character.isDigit(alphaa);
		   if(!isDigit) {
			 messages.add(ActionMessages.GLOBAL_MESSAGE,
					 new ActionMessage("message.generalmessage", "Please enter only numeric characters in the date field"));
			 addErrors(request, messages);
			 return false;
		  }
		}

		/**
		int d = Integer.parseInt(dobD);
		int m = Integer.parseInt(dobM);
		if(d > 31) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					 new ActionMessage("message.generalmessage", "Day of birth can't be more than 31"));
			 addErrors(request, messages);
			 return false;
		}
		if(m > 12) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					 new ActionMessage("message.generalmessage", "Month of birth can't be more than 12"));
			 addErrors(request, messages);
			 return false;
		}
		**/
		if(null == dobM | dobM.equalsIgnoreCase("")) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please complete the 'month' field"));
			addErrors(request, messages);
			return false;
		}
		if(null == dobD | dobD.equalsIgnoreCase("")) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please complete the 'day' field"));
			addErrors(request, messages);
			return false;
		}

		return true;
	}

	private boolean validateStudentViewC3(ActionForm form){
		ContactUsForm contactUsForm = (ContactUsForm)form;
		String surname = contactUsForm.getSurname();
		String firstNames = contactUsForm.getFirstNames();
		String email = contactUsForm.getEmail();
		String birthYear = contactUsForm.getBirthDateY();
		String birthMonth= contactUsForm.getBirthDateM();
		String birthDay = contactUsForm.getBirthDateD();
		String id = contactUsForm.getId();
		String passport = contactUsForm.getPassport();
		String postalAddress = contactUsForm.getPostalAddress();
		boolean valid=true;

		//validate the registration page part ONLY
		if(postalAddress == null || postalAddress.equals("")) {
			contactUsForm.setInvalidMessage("Please complete all mandatory fields");
			valid = false;
			return valid;
		}

		if(surname == null || surname.equalsIgnoreCase("")) {
				contactUsForm.setInvalidMessage("Please complete all mandatory fields. (Surname field)");
				valid=false;
				return valid;
		}
		if(firstNames == null || firstNames.equalsIgnoreCase("")) {
			contactUsForm.setInvalidMessage("Please complete all mandatory fields. (First name(s) field)");
			valid=false;
			return valid;
		}
		if(email == null || email.equalsIgnoreCase("") || !Pattern.matches(".+@.+\\.[a-z]+",email)) {
			contactUsForm.setInvalidMessage("Please complete all mandatory fields. (The email address entered is invalid)");
			valid=false;
			return valid;
		}

		if(birthYear == null || birthYear.equalsIgnoreCase("") ||
				!Pattern.matches("[0-9]{4}", birthYear)) {
			contactUsForm.setInvalidMessage("Please complete all mandatory fields. Birth year is invalid. (It should be 4 digits)");
			valid=false;
			return valid;
		}

		if(birthMonth == null || birthMonth.equalsIgnoreCase("") ||
				!Pattern.matches("[0-9]{2}", birthMonth)) {
			contactUsForm.setInvalidMessage("Please complete all mandatory fields. Birth month is invalid. (It should be 2 digits)");
			valid=false;
			return valid;
		}
		if(Integer.parseInt(birthMonth) == 0) {
			contactUsForm.setInvalidMessage("Month value can't be 00." );
			valid=false;
			return valid;
		}

		if(birthDay == null || birthDay.equalsIgnoreCase("") ||
				!Pattern.matches("[0-9]{2}",birthDay)) {
			contactUsForm.setInvalidMessage("Please complete all mandatory fields. Day of birth is invalid. (It should be 2 digits)");
			valid=false;
			return valid;
		}

		if(birthMonth.equalsIgnoreCase("01") || birthMonth.equalsIgnoreCase("03") || birthMonth.equalsIgnoreCase("05") || birthMonth.equalsIgnoreCase("07")
				|| birthMonth.equalsIgnoreCase("08") || birthMonth.equalsIgnoreCase("10") || birthMonth.equalsIgnoreCase("12")) {
			if(Integer.parseInt(birthDay) > 31) {
				contactUsForm.setInvalidMessage("Day of month can't be more that 31");
				valid=false;
				return valid;
			}
			if(Integer.parseInt(birthDay) == 0) {
				contactUsForm.setInvalidMessage("Day of month can't be 00." );
				valid=false;
				return valid;
			}
		}

		if(birthMonth.equalsIgnoreCase("04") || birthMonth.equalsIgnoreCase("06") || birthMonth.equalsIgnoreCase("09")
				|| birthMonth.equalsIgnoreCase("11")) {
			if(Integer.parseInt(birthDay) > 30) {
				contactUsForm.setInvalidMessage("Day of month can't be more that 30");
				valid=false;
				return valid;
			}
			if(Integer.parseInt(birthMonth) == 0) {
				contactUsForm.setInvalidMessage("Month value can't be 00." );
				valid=false;
				return valid;
			}

		}

		if(birthMonth.equalsIgnoreCase("02")  && ((Integer.parseInt(birthYear) % 4) == 0)) {
			if(Integer.parseInt(birthDay) > 29 || Integer.parseInt(birthDay) == 0) {
				contactUsForm.setInvalidMessage("Day of month can't be more that 29. " + birthYear + " was a leap year.");
				valid=false;
				return valid;
			}
		}

		if(birthMonth.equalsIgnoreCase("02") && ((Integer.parseInt(birthYear) % 4) != 0)) {
			if(Integer.parseInt(birthDay) > 28) {
				contactUsForm.setInvalidMessage("Day of month can't be more that 28. " + birthYear + " was NOT leap year.");
				valid=false;
				return valid;
			}
			if(Integer.parseInt(birthDay) == 0) {
				contactUsForm.setInvalidMessage("Day of month can't be 00." );
				valid=false;
				return valid;
			}
		}

		if(Integer.parseInt(birthMonth) > 12 || Integer.parseInt(birthMonth) == 00) {
			contactUsForm.setInvalidMessage("Month value can't be more that 12 or 00");
			valid = false;
			return valid;
		}

		if(Integer.parseInt(birthYear) < 1900) {
			contactUsForm.setInvalidMessage("Are you really older than 100? <br/> Let's pretend your less than a 100 years only for today....A little piece of my dry humor....chuckle");
			valid = false;
			return valid;
		}

		if(postalAddress == null || postalAddress.equals("")) {
			contactUsForm.setInvalidMessage("Please complete all mandatory fields. (Postal address)");
			valid = false;
			return valid;
		}

		if((null == id | id.equalsIgnoreCase("")) && (null == passport) | passport.equalsIgnoreCase("")) {
			contactUsForm.setInvalidMessage("Please complete all mandatory fields. (ID/Passport field)");
			valid = false;
			return valid;
		}
		return valid;
	}

	private void clearForgottenStudentNumberForm(ContactUsForm contactUsForm) {
		contactUsForm.setPostalAddress("");
		contactUsForm.setFirstNames("");
		contactUsForm.setSurname("");
		contactUsForm.setMaiden("");
		contactUsForm.setEmail("");
		contactUsForm.setId("");
		contactUsForm.setPassport("");
		contactUsForm.setBirthDateY("");
		contactUsForm.setBirthDateM("");
		contactUsForm.setBirthDateD("");

		contactUsForm.setForgotdobd("");
		contactUsForm.setForgotdobm("");
		contactUsForm.setForgotdoby("");
		contactUsForm.setForgotName("");
		contactUsForm.setForgotSurname("");
	}

	private String sendToAdministrative(ContactUsForm contactUsForm) {
		String sendTo = "";
		if(contactUsForm.getCategorySelected().equalsIgnoreCase("myUnisa")) {
			sendTo = sendToMyUnisa(contactUsForm);
		}

		if(contactUsForm.getCategorySelected().equalsIgnoreCase("currentRegistration")) {
			sendTo = "forgotsn@unisa.ac.za"; //| xxx@unisa.ac.za;


		}

		if(!(null==contactUsForm.getMyUnisaStudentNumber())) {
			if((contactUsForm.getMyUnisaStudentNumber()).startsWith("7", 0) & contactUsForm.getMyUnisaStudentNumber().length()==8) {
				sendTo = "forgotsn.unisa.ac.za";

			}
		}

		if(contactUsForm.getCategorySelected().equalsIgnoreCase("account")) {
			sendTo = "finan@unisa.ac.za";

		}
		if(contactUsForm.getCategorySelected().equalsIgnoreCase("assignments")) {
		  sendTo = "assign@unisa.ac.za";

		}
		if(contactUsForm.getCategorySelected().equalsIgnoreCase("examinations")) {
			sendTo = "exams@unisa.ac.za";

		}
		if(contactUsForm.getCategorySelected().equalsIgnoreCase("despatch")) {
			sendTo = "despatch@unisa.ac.za";

		}
		if(contactUsForm.getCategorySelected().equalsIgnoreCase("fellowStudents")) {
			sendTo = "condiscipuli@unisa.ac.za";

		}
		if(contactUsForm.getCategorySelected().equalsIgnoreCase("graduations")) {
			sendTo = "gaudeamus@unisa.ac.za";

		}
		if(contactUsForm.getCategorySelected().equalsIgnoreCase("prescribedBooks")) {
			sendTo = "vospresc@unisa.ac.za";

		}
		if(contactUsForm.getCategorySelected().equalsIgnoreCase("library")) {
			sendTo = sendToLibrary(contactUsForm);
		}
		if(contactUsForm.getCategorySelected().equalsIgnoreCase("other")) {
			sendTo = "study-info@unisa.ac.za";

		}
		if(contactUsForm.getCategorySelected().equalsIgnoreCase("forgot")) {
			sendTo = "undergrad@unisa.ac.za";

		}
		return sendTo;
	}

	private String sendToMyUnisa(ContactUsForm contactUsForm) {
		String sendTo = "";

		if(null==contactUsForm.getStudentNumberFormal()) {
		}
		if(null==contactUsForm.getStudentNumberNonFormal()) {
		}
		if(!(null==contactUsForm.getMyUnisaStudentNumber())) {
			if((contactUsForm.getMyUnisaStudentNumber()).startsWith("7", 0) & contactUsForm.getMyUnisaStudentNumber().length()==8) {
				sendTo = "forgotsn@unisa.ac.za";

			}
		}

		if(contactUsForm.getMyUnisaCategorySelected().equalsIgnoreCase("myUnisa_joining")) {
			sendTo = "myUnisaHelp@unisa.ac.za";

		}
		if(contactUsForm.getMyUnisaCategorySelected().equalsIgnoreCase("myUnisa_accountactivation")) {
			sendTo = "myUnisaHelp@unisa.ac.za";

		}
		if(contactUsForm.getMyUnisaCategorySelected().equalsIgnoreCase("myUnisa_login")) {
			sendTo = "myUnisaHelp@unisa.ac.za";

		}
		if(contactUsForm.getMyUnisaCategorySelected().equalsIgnoreCase("myUnisa_assignments")) {
			sendTo = "assign@unisa.ac.za";

		}
		if(contactUsForm.getMyUnisaCategorySelected().equalsIgnoreCase("myUnisa_examinations")) {
			sendTo = "exams@unisa.ac.za";

		}
		if(contactUsForm.getMyUnisaCategorySelected().equalsIgnoreCase("myUnisa_prescribedbooks")) {
			sendTo = "vospresc@unisa.ac.za";

		}
		if(contactUsForm.getMyUnisaCategorySelected().equalsIgnoreCase("myUnisa_resources")) {
			sendTo = "myUnisaHelp@unisa.ac.za";

		}
		if(contactUsForm.getMyUnisaCategorySelected().equalsIgnoreCase("myUnisa_suggestions")) {
			sendTo = "bugmaster@unisa.ac.za";

		}
		if(contactUsForm.getMyUnisaCategorySelected().equalsIgnoreCase("myUnisa_other")) {
			sendTo = "bugmaster@unisa.ac.za";

		}

		if(contactUsForm.getMyUnisaCategorySelected().equalsIgnoreCase("myUnisa_accountpayment")) {
			sendTo = "finan@unisa.ac.za";

		}

		return sendTo;
	}

	private String sendToLibrary(ContactUsForm contactUsForm) {
		String sendTo = "";
		String catSelected = contactUsForm.getLibraryCategorySelected();
		if(catSelected.equalsIgnoreCase("pin")) {
			sendTo = "bib-pin@unisa.ac.za";

		}
		if(catSelected.equalsIgnoreCase("articles")) {
			sendTo = "bib-oasis@unisa.ac.za";

		}
		if(catSelected.equalsIgnoreCase("oasisaccount")) {
			sendTo = "bib-circ@unisa.ac.za";

		}
		if(catSelected.equalsIgnoreCase("oasiscatalogue")) {
			sendTo = "bib-circ@unisa.ac.za";

		}
		if(catSelected.equalsIgnoreCase("books")) {
			sendTo = "bib-circ@unisa.ac.za";

		}
		if(catSelected.equalsIgnoreCase("general")) {
			sendTo = "ask@unisa.ac.za";

		}
		if(catSelected.equalsIgnoreCase("access")) {
			sendTo = "ask@unisa.ac.za";

		}
		if(catSelected.equalsIgnoreCase("database")) {
			sendTo = "bib-dbase@unisa.ac.za";

		}
		
		return sendTo;
	}


	private void generateEmailForForgottenStudentNumber(HttpServletRequest request, ContactUsForm contactUsForm) {
		MessageResources messages = getResources(request);
		String sendTo = sendToAdministrative(contactUsForm);
		SimpleDateFormat sdf = new SimpleDateFormat("EEEE dd MMM yyyy hh:mm");
		Date d = new Date();
		String dob = contactUsForm.getBirthDateY() + "/" + contactUsForm.getBirthDateM() + "/" + contactUsForm.getBirthDateD();

		String sendFrom = contactUsForm.getEmail();
		String subject=messages.getMessage("email.forgot.subject");
		String body = messages.getMessage("email.forgot.body.type") + "Forgot student number\n\n";
		body += messages.getMessage("email.forgot.body.date") + " " + sdf.format(d) + "\n\n";
		body += messages.getMessage("email.forgot.body.surname") + contactUsForm.getSurname() + "\n\n";
		body += messages.getMessage("email.forgot.body.maidenname") + contactUsForm.getMaiden() + "\n\n";
		body += messages.getMessage("email.forgot.body.firstnames") + contactUsForm.getFirstNames() + "\n\n";
		body += messages.getMessage("email.forgot.body.dob") + dob + "\n\n";
		body += messages.getMessage("email.forgot.body.id") + contactUsForm.getId() + "\n\n";
		body += messages.getMessage("email.forgot.body.passport") + contactUsForm.getPassport() + "\n\n";
		body += messages.getMessage("email.forgot.body.postaladdress") + "\n" + contactUsForm.getPostalAddress() + "\n";
		body += messages.getMessage("email.forgot.body.emailme")+ "\n\n";
		body += contactUsForm.getEmail();

		SendMail mailObject = new SendMail(sendTo,sendFrom, subject, body);
		mailObject.sendMail();

		//EventTrackingService.post(
		//		EventTrackingService.newEvent(EventTrackingTypes.EVENT_CONTACTUS_EMAILDEPARTMENT, PortalService.getCurrentSiteId(), false));
	}

	private void generateEmailForStudentEnquiry(HttpServletRequest request, ContactUsForm contactUsForm) {
		MessageResources messages = getResources(request);
		String sendTo="";
		sendTo = sendToAdministrative(contactUsForm);

		SimpleDateFormat sdf = new SimpleDateFormat("EEEE dd MMM yyyy hh:mm");
		Date d = new Date();
		String sendFrom = contactUsForm.getEmail();
		if(null == sendFrom | "".equals(sendFrom)) {
			sendFrom = contactUsForm.getMyUnisaEmailAddress();
		}
		String type=contactUsForm.getCategorySelected();
		if("myUnisa".equals(type)) {
			type = contactUsForm.getMyUnisaCategorySelected();
		}
		if("library".equals(type)) {
			 type = "Library - " + contactUsForm.getLibraryCategorySelected();
		}

		String subject = messages.getMessage("email.studentdetails.subject") + contactUsForm.getMyUnisaStudentNumber();
		String body = messages.getMessage("email.studentdetails.body.type") + type + "\n\n";
		body += messages.getMessage("email.studentdetails.body.date") + sdf.format(d) + "\n\n";
		body += messages.getMessage("email.studentdetails.body.studentnumber") + contactUsForm.getMyUnisaStudentNumber() + "\n\n";
		body += messages.getMessage("email.studentdetails.body.emailaddress") + contactUsForm.getMyUnisaEmailAddress() + "\n\n";
		body += messages.getMessage("email.studentdetails.body.contactnumber") + contactUsForm.getMyUnisaContactNumber() + "\n\n";
		body += messages.getMessage("email.studentdetails.body.enquiry") + "\n" + contactUsForm.getMyUnisaEnquiryMessage();

		SendMail mailObject = new SendMail(sendTo,sendFrom, subject, body);
		mailObject.sendMail();

		//EventTrackingService.post(
		//		EventTrackingService.newEvent(EventTrackingTypes.EVENT_CONTACTUS_EMAILDEPARTMENT, PortalService.getCurrentSiteId(), false));
	}

	private void generateEmailForCurrentRegistration(HttpServletRequest request, ContactUsForm contactUsForm, ArrayList degree, String countryCode) {
		MessageResources messages = getResources(request);
		String sendTo = sendToAdministrative(contactUsForm);
		SimpleDateFormat sdf = new SimpleDateFormat("EEEE dd MMM yyyy hh:mm");
		Date d = new Date();

		String sendFrom = contactUsForm.getEmail();
		if(null == sendFrom | "".equals(sendFrom)) {
			sendFrom = contactUsForm.getMyUnisaEmailAddress();
		}

		String subject = messages.getMessage("email.currentregistration.subject") + contactUsForm.getMyUnisaStudentNumber();
		String body = messages.getMessage("email.currentregistration.body.type") + "My current registration - administrative enquiry \n\n";
		body += messages.getMessage("email.currentregistration.body.date") + sdf.format(d) + "\n\n";
		body += messages.getMessage("email.currentregistration.body.studentnumber") + contactUsForm.getMyUnisaStudentNumber() + "\n\n";
		body += messages.getMessage("email.currentregistration.body.degree") + ((Qualification)(degree.get(0))).getQualificationCode() + " - " + ((Qualification)(degree.get(0))).getQualificationDescription() + "\n\n";
		body += messages.getMessage("email.currentregistration.body.countrycode") + countryCode + "\n\n";
		body += messages.getMessage("email.currentregistration.body.email") + sendFrom + "\n\n";
		body += messages.getMessage("email.currentregistration.body.contactnumber") + contactUsForm.getMyUnisaContactNumber() + "\n\n";
		body += messages.getMessage("email.currentregistration.body.message") + "\n" + contactUsForm.getMyUnisaEnquiryMessage();

		SendMail mailObject = new SendMail(sendTo,sendFrom, subject, body);
		mailObject.sendMail();

		//EventTrackingService.post(
		//		EventTrackingService.newEvent(EventTrackingTypes.EVENT_CONTACTUS_EMAILDEPARTMENT, PortalService.getCurrentSiteId(), false));
	}
}
