//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.studentregistration.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.email.api.EmailService;

import za.ac.unisa.lms.tools.studentregistration.dao.StudentRegistrationQueryDAO;
import za.ac.unisa.lms.tools.studentregistration.forms.Student;
import za.ac.unisa.lms.tools.studentregistration.forms.StudentRegistrationForm;


public class StudentRegistrationAction extends LookupDispatchAction {

	//private statements add June 2011
	private EmailService emailService;
	
	public static Log log = LogFactory.getLog(StudentRegistrationAction.class);

	// --------------------------------------------------------- Methods

	protected Map<String, String> getKeyMethodMap() {
	      Map<String, String> map = new HashMap<String, String>();
	      map.put("button.cancel", "cancel");
	      map.put("button.continue", "nextStep");
	      map.put("button.request", "nextStep");
	      map.put("startPage", "startPage");
	      map.put("applyForStudentNr", "applyForStudentNr");
	      map.put("studentRegistration", "studentRegistration");
	      map.put("walkThroughStep1","walkThroughStep1a");

	      return map;
	  }

	public ActionForward startPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		resetForm((StudentRegistrationForm) form);
		resetUploadForm();

		log.debug("StudentRegistrationAction - startPage");
		return mapping.findForward("startpageforward");
	}

	public ActionForward applyForStudentNr(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		log.debug("StudentRegistrationAction - applyForStudentNr");
		return mapping.findForward("applyforward");
	}

	public String applyStep2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		log.debug("StudentRegistrationAction - applyStep2");
		
		StudentRegistrationForm stuRegForm = (StudentRegistrationForm) form;
		ActionMessages messages = new ActionMessages();

		// Check input
		if (stuRegForm.getApplyType()==null || "".equals(stuRegForm.getApplyType())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Indicate the appropriate option."));
			addErrors(request, messages);
			return "applyforward";
		}

		// goto ApplyForStudentNumberAction
		return "applyactionforward";
	}

	public String getStudentNumber(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		log.debug("StudentRegistrationAction - getStudentNumber");
		
		StudentRegistrationForm stuRegForm = (StudentRegistrationForm) form;
		ActionMessages messages = new ActionMessages();

		if (stuRegForm.getRegistrationType()==null || "".equalsIgnoreCase(stuRegForm.getRegistrationType())){
			return "home";
		}

		// -------- Remove ' characters from input
		stuRegForm.getStudent().setSurname(stuRegForm.getStudent().getSurname().replace("'",""));
		stuRegForm.getStudent().setFirstnames(stuRegForm.getStudent().getFirstnames().replace("'",""));
		// -------- Remove all spaces
		stuRegForm.getStudent().setSurname(stuRegForm.getStudent().getSurname().trim());
		stuRegForm.getStudent().setFirstnames(stuRegForm.getStudent().getFirstnames().trim());
		// -------- Validate birth date
		GeneralMethods gen = new GeneralMethods();
		String errorMessage = gen.validateDateOfBirth(stuRegForm.getStudent());
		if(!"".equals(errorMessage)){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage",
				errorMessage));
			addErrors(request, messages);
			return "walkthrough1cforward";
		}

		// -------- Read database
		StudentRegistrationQueryDAO dao = new StudentRegistrationQueryDAO();
		Student result = new Student();
		result = dao.getStudentNumberSearch(stuRegForm.getStudent(), stuRegForm.getRegistrationType());
		/*
		stuRegForm.setStudentNumberSearchAttemp(stuRegForm.getStudentNumberSearchAttemp()+1);
		if (result == null){
			request.setAttribute("found","false");
			if(stuRegForm.getStudentNumberSearchAttemp()>=2){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
						"Student number not found."));
				addErrors(request, messages);
				return "emailstudentnumberforward";
			}else{
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
						"Student number not found - please try again."));
				addErrors(request, messages);
			}
		}else{
			stuRegForm.setStudent(result);
			return "studentnumberdisplayforward";
		}
		*/

		return "walkthrough1cforward";
	}

	public ActionForward cancel(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		resetForm((StudentRegistrationForm) form);

		return mapping.findForward("cancelforward");
	}

	public String emailStudentNumberApplication(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		StudentRegistrationForm stuRegForm = (StudentRegistrationForm) form;
		ActionMessages messages = new ActionMessages();
		GeneralMethods gen = new GeneralMethods();

		// ------- Check input
		// Surname
		if (stuRegForm.getStudent().getSurname()==null || "".equals(stuRegForm.getStudent().getSurname().trim())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please complete all mandatory fields."));
			addErrors(request, messages);
			return "emailstudentnumberforward";
		}
		// Full first names
		if (stuRegForm.getStudent().getFirstnames()==null || "".equals(stuRegForm.getStudent().getFirstnames().trim())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please complete all mandatory fields."));
			addErrors(request, messages);
			return "emailstudentnumberforward";
		}
		// Date of birth
		String errorMessage = gen.validateDateOfBirth(stuRegForm.getStudent());
		if(!"".equals(errorMessage)){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage",
				errorMessage));
			addErrors(request, messages);
			return "emailstudentnumberforward";
		}
		// Id nr or passport nr
		if ((stuRegForm.getStudent().getIdNumber()==null || "".equals(stuRegForm.getStudent().getIdNumber().trim())) && (stuRegForm.getStudent().getPassportNumber()==null || "".equals(stuRegForm.getStudent().getPassportNumber().trim()))){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Identity number OR Foreign identity number/Passport number is mandatory."));
			addErrors(request, messages);
			return "emailstudentnumberforward";
		}else {
			if(stuRegForm.getStudent().getIdNumber()!=null && !"".equals(stuRegForm.getStudent().getIdNumber().trim())){
				if (!gen.isNumeric(stuRegForm.getStudent().getIdNumber())){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Identity number must be numeric."));
					addErrors(request, messages);
					return "emailstudentnumberforward";
				}else if(stuRegForm.getStudent().getIdNumber().trim().length()!= 13){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Identity number must consist of 13 digits."));
					addErrors(request, messages);
					return "emailstudentnumberforward";
				}
			}
		}
		// email
		if (stuRegForm.getStudent().getEmailAddress()==null || "".equals(stuRegForm.getStudent().getEmailAddress().trim())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please complete all mandatory fields."));
			addErrors(request, messages);
			return "emailstudentnumberforward";
		}else{
			//messages = (ActionMessages) stuRegForm.validate(mapping, request);
			if (!messages.isEmpty()) {
				addErrors(request, messages);
				return "emailstudentnumberforward";
			}
		}
		// postal
		if (stuRegForm.getStudent().getLastPostalAddress()==null || "".equals(stuRegForm.getStudent().getLastPostalAddress().trim())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please complete all mandatory fields."));
			addErrors(request, messages);
			return "emailstudentnumberforward";
		}else{
			//postal address length max=200 chars
			int postalLen = stuRegForm.getStudent().getLastPostalAddress().trim().length();
			if(postalLen + 2 > 200){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
								"Postal address can not exceed 200 characters. It is currently "+(postalLen)+" characters."));
				addErrors(request, messages);
				return "emailstudentnumberforward";
			}
		}

		sendStudentNumberEmail(stuRegForm.getStudent(), stuRegForm.getRegistrationType());
		messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage",
				"An e-mail has been sent to the relevant department - your student number will be e-mailed to you in due course."));
		addMessages(request, messages);

		return "emailstudentnumberforward";
	}

	/**
	 * This method sends an e-mail on behalf of a student to request their
	 * student number be emailed to them.
	 *
	 * @param student					The Student object of specific student
	 * @param submissionTimestamp		The date and time of the addition request
	 */

	public void sendStudentNumberEmail(Student student, String registrationType) {

		emailService = (EmailService) ComponentManager.get(EmailService.class);
		
		try {
			String tmpEmailFrom = "comms@unisa.ac.za";

			//InternetAddress toEmail = new InternetAddress(ServerConfigurationService.getString("undergradEmail"));
			InternetAddress toEmail = new InternetAddress("penzhe@unisa.ac.za");
			InternetAddress iaTo[] = new InternetAddress[1];
			iaTo[0] = toEmail;
			InternetAddress iaHeaderTo[] = new InternetAddress[1];
			iaHeaderTo[0] = toEmail;
			InternetAddress iaReplyTo[] = new InternetAddress[1];
			iaReplyTo[0] = new InternetAddress(tmpEmailFrom);
			String submissionTimestamp = (new java.text.SimpleDateFormat("EEEEE dd MMMMM yyyy hh:mm:ss").format(new java.util.Date()).toString());

			String bodyExtra = "Please e-mail my formal Unisa student number to the following e-mail address:";
			if (registrationType != null && "S".equalsIgnoreCase(registrationType)){
				bodyExtra = "Please e-mail my non-formal Unisa student number (7 series student number) to the following e-mail address:";
			}
			String subject = "Forgotten student number";
			String body1 = "<html> "+
			"<body> <strong>"+
			bodyExtra +"  <br/>"+
			student.getEmailAddress()+ " <br/></strong> <table>"+
			"<tr><td>Type of enquiry </td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;Forgotten student number<td></tr> "+
			"<tr><td>Date received </td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;"+ submissionTimestamp+"<td></tr>" +
			"<tr><td>Surname </td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;"+ student.getSurname()+"<td></tr>"+
			"<tr><td>Maiden name </td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;"+ student.getMaidenName()+"<td></tr>"+
			"<tr><td>Full first names </td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;"+ student.getFirstnames()+"<td></tr>"+
			"<tr><td>Date of birth </td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;"+ student.getBirthYear()+student.getBirthMonth()+student.getBirthDay()+"<td></tr>"+
			"<tr><td>ID number </td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;"+ student.getIdNumber()+"<td></tr>"+
			"<tr><td colspan = '2'>Foreign identity number</td></tr>"+
			"<tr><td>/passport number </td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;"+ student.getPassportNumber()+"<td></tr>"+
			"<tr><td>Last postal address </td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;"+ student.getLastPostalAddress()+"<td></tr>"+
			"</table>";

			String body2 = "</body>"+
					   "</html>";

			List<String> contentList = new ArrayList<String>();
			contentList.add("Content-Type: text/html");

			body1 = body1 + body2;
			try{
				emailService.sendMail(iaReplyTo[0],iaTo,subject,body1,iaHeaderTo,iaReplyTo,contentList);
				log.info("sending mail from "+iaReplyTo[0]+" to "+toEmail.toString());
			}catch (Exception e){
				// Don't fail additions if email could not be send
        		log.error("StudentRegistrationAction : walkthrough, send of student number email failed: "+e+" "+e.getMessage());
        	}

		} catch (Exception e) {
			log.error("Unhandled exception: "+e+" "+e.getMessage());
			e.printStackTrace();
		}
	}

	public ActionForward nextStep(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.debug("StudentRegistrationAction - nexStep:"+request.getParameter("page"));

		String nextPage = "";
 		if ("applystep2".equalsIgnoreCase(request.getParameter("page"))){
 			nextPage = applyStep2(mapping,form,request, response);
		}else if ("emailStuNr".equalsIgnoreCase(request.getParameter("page"))){
				nextPage = emailStudentNumberApplication(mapping,form,request, response);
		//}else if ("2b".equalsIgnoreCase(request.getParameter("goto"))){
		//	 return mapping.findForward("step1");
		}

		return mapping.findForward(nextPage);
		}

	public void resetForm(StudentRegistrationForm form)
			throws Exception {
		
		log.debug("StudentRegistrationAction - resetForm");

		// Clear fields
		form.setApplyType("");
		form.setRegistrationType("");
		form.setQuestion1("");
		form.setQuestion2("");
		form.setStudent(new Student());
		form.setAgree("");
	}
	
	public void resetUploadForm()
			throws Exception {

	}

	public ActionForward unspecified(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response){
		
		log.debug("StudentRegistrationAction - unspecified");

		log.info("StudentRegistration: unspecified method call -no value for parameter in request");
		return mapping.findForward("home");

	}

}

