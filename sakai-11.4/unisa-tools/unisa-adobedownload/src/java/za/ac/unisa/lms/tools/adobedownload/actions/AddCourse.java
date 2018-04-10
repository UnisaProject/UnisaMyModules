package za.ac.unisa.lms.tools.adobedownload.actions;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

import java.net.*;
import java.util.*;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.LookupDispatchAction;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.email.api.Attachment;
import org.sakaiproject.email.api.EmailService;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.event.api.EventTrackingService;
import org.sakaiproject.event.api.UsageSession;
import org.sakaiproject.event.api.UsageSessionService;

import za.ac.unisa.lms.tools.adobedownload.dao.DownloadStaffSakaiDAO;
import za.ac.unisa.lms.tools.adobedownload.dao.DownloadStaffStudentDAO;
import za.ac.unisa.lms.tools.adobedownload.forms.CourseForm;
import za.ac.unisa.lms.constants.EventTrackingTypes;



public class AddCourse extends LookupDispatchAction{

	private EmailService emailService;
	private SessionManager sessionManager;
	private EventTrackingService eventTrackingService;
	private UsageSessionService usageSessionService;

	protected Map getKeyMethodMap() {

		Map map = new HashMap();

		map.put("verifyCourse", "verifyCourse");
		// map.put("email.button.send", "sendEmail");
		// map.put("email.button.cancel","clearForm");
		map.put("course.button.continue", "validateCourse");
		map.put("course.button.cancel", "cancel");
		map.put("licence.button.back", "cancel");
		map.put("licence.button.accept", "acceptTermsAndConditions");
		map.put("licence.button.cancel", "cancel");
		map.put("reason.button.ok", "confirmReason");


		return map;
	}

	public ActionForward verifyCourse(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		CourseForm courseForm = (CourseForm) form;
		
		DownloadStaffStudentDAO db1 = new DownloadStaffStudentDAO();

		// List of previous year and current year
		HttpSession session = request.getSession(true);
		session.setAttribute("yearOptions", courseForm.getYearOptions());
		// academic periods
		courseForm.setPeriodOptions(db1.selectRegPeriodOptions());
		session.setAttribute("periodOptions", courseForm.getPeriodOptions());
		request.setAttribute("reasonList", courseForm.getReasonsOptions());

		return mapping.findForward("verifyCourse");
	}

	public ActionForward validateCourse(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
		UsageSession usageSession = usageSessionService.getSession();	
		
		CourseForm courseForm = (CourseForm) form;
		DownloadStaffStudentDAO db1 = new DownloadStaffStudentDAO();
		DownloadStaffSakaiDAO sakaidb = new DownloadStaffSakaiDAO();
		ActionMessages messages = new ActionMessages();

		sessionManager = (SessionManager) ComponentManager
				.get(SessionManager.class);
		Session currentSession = sessionManager.getCurrentSession();
		String userId = currentSession.getUserEid();
		courseForm.setUserId(userId);

		// verify if year was entered
		if (courseForm.getYear().equals("")) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"message.generalmessage", "Please enter the Year"));
			addErrors(request, messages);
			return mapping.findForward("verifyCourse");

		}

		// verify that period was chosen
		if (courseForm.getAcadPeriod().equals("")) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"message.generalmessage", "Please choose the Period"));
			addErrors(request, messages);

			return mapping.findForward("verifyCourse");

		}

		// remove all leading and trailing spaces
		courseForm.setCourse(ltrim(courseForm.getCourse()));
		courseForm.setCourse(rtrim(courseForm.getCourse()));
		courseForm.setCourse(courseForm.getCourse().replace('\'', ' '));

		// verify that course code was entered
		if ((null == courseForm.getCourse())
				|| (courseForm.getCourse().length() == 0)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"message.generalmessage",
					"Course information is not completed."));
			addErrors(request, messages);
			return mapping.findForward("verifyCourse");
		}

		// verify that the course code is valid
		courseForm.setCourse(courseForm.getCourse().toUpperCase());
		boolean courseValid = db1.courseExist(courseForm.getCourse(),
				courseForm.getYear(), courseForm.getAcadPeriod());
		if (courseValid == false) {
			messages
					.add(
							ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage(
									"message.generalmessage",
									"Course code ("
											+ courseForm.getCourse()
											+ ") is not valid.  Please verify correct code and re-enter."));
			addErrors(request, messages);
			courseForm.setCourse("");
			return mapping.findForward("verifyCourse");
		}

		// validate that course is still active
		boolean courseActive = db1.courseExpired(courseForm.getCourse(),
				courseForm.getYear(), courseForm.getAcadPeriod());
		if (courseActive == false) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"message.generalmessage",
					"Course and academic offering type is not active"));
			addErrors(request, messages);
			return mapping.findForward("verifyCourse");
		}

		request.setAttribute("yearOptions", courseForm.getYearOptions());
		request.setAttribute("periodOptions", courseForm.getPeriodOptions());
		request.setAttribute("reasonsOptions", courseForm.getReasonsOptions());

		// validate if logged user linked to a course entered
		boolean validCourse = db1.personLinked(courseForm.getYear(), courseForm.getAcadPeriod(), courseForm.getCourse(), userId);
		
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
		eventTrackingService.post(
				eventTrackingService.newEvent(EventTrackingTypes.EVENT_ADOBEDOWNLOAD_VERIFY_COURSE,"Adobe download verified course "+courseForm.getCourse()+" by "+userId,false),usageSession);
			
						
		if (validCourse == true) 
		{
			// get count against user
			boolean checkdownloaded = sakaidb.checkDownloads(userId);
			courseForm.setDownloadCount(checkdownloaded);

			if (checkdownloaded == true) 
			{

				return mapping.findForward("viewreasons");

			} 
			else 
			{

				return mapping.findForward("licenceagreement");
			}

		} 
		else 
		{
			messages
					.add(
							ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage(
									"message.generalmessage",
									userId
											+ " is not linked to this course site.Please contact departmental linker"));
			addErrors(request, messages);

			return mapping.findForward("verifyCourse");
		}
	}

	public ActionForward cancel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		CourseForm courseForm = (CourseForm) form;
		DownloadStaffStudentDAO db1 = new DownloadStaffStudentDAO();

		courseForm.setYear("");
		courseForm.setAcadPeriod("");
		courseForm.setCourse("");
		courseForm.setDownloadReason("");
		courseForm.setDownloadOtherReason("");

		return mapping.findForward("verifyCourse");
	}

	public ActionForward confirmReason(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		CourseForm courseForm = (CourseForm) form;
		
		ActionMessages messages = new ActionMessages();
		
		if(courseForm.getDownloadReason().equals("Other") && (null == courseForm.getDownloadOtherReason() || courseForm.getDownloadOtherReason().length() == 0))
		{
			
			messages.add(
						ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
								"Please enter the reason"));
		addErrors(request, messages);
		
		request.setAttribute("reasonsOptions", courseForm.getReasonsOptions());
		courseForm.setDownloadOtherReason("");
		return mapping.findForward("viewreasons");
		
			
		}
		else
		{
			courseForm.setDownloadReason(courseForm.getDownloadReason());
			return mapping.findForward("licenceagreement");
		}
		
	}

	public ActionForward acceptTermsAndConditions(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		CourseForm courseForm = (CourseForm) form;
		DownloadStaffSakaiDAO sakaidb = new DownloadStaffSakaiDAO();
		String userId = courseForm.getUserId();
		DownloadStaffStudentDAO db1 = new DownloadStaffStudentDAO();
		ActionMessages messages = new ActionMessages();
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
		UsageSession usageSession = usageSessionService.getSession();	
		

		if (null == courseForm.getAgreeTerms()) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"message.generalmessage",
					"Please choose at least one option"));
			addErrors(request, messages);

			return mapping.findForward("licenceagreement");
		}
		if (courseForm.getAgreeTerms().equals("N")) {
			messages
					.add(
							ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
									"You must accept the terms and conditions to download the software"));
			addErrors(request, messages);

			return mapping.findForward("licenceagreement");
		}

		if (courseForm.isDownloadCount() == true) {

			//int downloadsOccured = sakaidb.getDownloadOcc(userId);
			//sakaidb.updateUserRecord(userId, courseForm.getCourse(),downloadsOccured, courseForm.getDownloadReason(),courseForm.getDownloadOtherReason());
			sakaidb.insertUserRecord(userId, courseForm.getCourse(), courseForm.getDownloadReason(), courseForm.getDownloadOtherReason());
		} else {

			// NB :when it's first download present the terms and conditions
			// then when user accept then send email to the Lecture
			db1.getUserNameAndPersNo(courseForm);

			String primaryLectureEmail = db1.selectPrimaryLecturer(courseForm
					.getYear(), courseForm.getAcadPeriod(), courseForm
					.getCourse());
			String subject = "Adobe professional online download";
			String message = "This is to notify you that "
					+ courseForm.getName()
					+ " ,Personnel number "
					+ courseForm.getPersonnelNr()
					+ " has downloaded adobe  professional  software version 10,  for the module "
					+ courseForm.getCourse();

			// courseForm.setEmailMessage("This is to notify you that [TK
			// Mathebula] ,Personnel number [3211023] downloaded adobe
			// professional software [version 10], for the module [CSC 231 S]");

			String body = "<html> " + "<body>Dear Sir/Madam<br>" + "<br>"
					+ message + "<br> "
					+ "<b><br>Regards<br>ICT Department</b>" + "</body>"
					+ "</html>";

			sendEmail(subject, body, primaryLectureEmail);
			// insert record to db
			sakaidb.insertUserRecord(userId, courseForm.getCourse(), courseForm.getDownloadReason(), courseForm.getDownloadOtherReason());
		}
		String secret = ServerConfigurationService.getString("adobe.base"); // Same as AuthTokenSecret
		String protectedPath = "/adobe/"; // Same as AuthTokenPrefix
		// boolean ipLimitation=false; // Same as AuthTokenLimitByIp
		long time = (new Date()).getTime(); // Time in decimal
		time = (time / 1000) + (60 * 5); // timestamp of java is longer than PHP
		String hexTime = Long.toHexString(time); // hexTime in Hexadecimal
		String filePathName = "/Adobe.exe";
		String token = getMD5((secret + filePathName  + hexTime).getBytes());
		String url = protectedPath + token + "/" + hexTime + filePathName;
		
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
		eventTrackingService.post(
				eventTrackingService.newEvent(EventTrackingTypes.EVENT_ADOBEDOWNLOAD_TERMS,"Accepted terms and Downloaded on "+courseForm.getCourse()+" by "+userId,false),usageSession);
		
		
		//System.out.println(ServerConfigurationService.getString("cmsys.base")+ url);
		//response.sendRedirect(ServerConfigurationService.getString("cmsys.base")+ url);
		response.sendRedirect(ServerConfigurationService.getString("adobe.file.path")+ url);
		
			
		 
		/*next line is the actual  path where the actual adobe installer is located and it is to be taken to the webserver http://www3dev.unisa.ac.za 
		\\umkn-sms01\DSL\Adobe\OnLineMarkingTools\OLD\AdobeAIRInstaller_Ver1_5.exe"
	   */		
			
		return null;
	}

	public String getMD5(byte[] source) {
		String s = null;
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			java.security.MessageDigest md = java.security.MessageDigest
					.getInstance("MD5");
			md.update(source);
			byte tmp[] = md.digest();
			char str[] = new char[16 * 2];
			int k = 0;
			for (int i = 0; i < 16; i++) {
				byte byte0 = tmp[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			s = new String(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

	public void sendEmail(String subject, String body,
			String primaryLectureEmail) throws AddressException {

		emailService = (EmailService) ComponentManager.get(EmailService.class);
		String tmpEmailFrom = ServerConfigurationService
				.getString("noReplyEmailFrom");

		InternetAddress toEmail = null;
		try {
			toEmail = new InternetAddress(primaryLectureEmail);
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		InternetAddress iaTo[] = new InternetAddress[1];
		iaTo[0] = toEmail;
		InternetAddress iaHeaderTo[] = new InternetAddress[1];
		iaHeaderTo[0] = toEmail;
		InternetAddress iaReplyTo[] = new InternetAddress[1];
		iaReplyTo[0] = new InternetAddress(tmpEmailFrom);
		List contentList = new ArrayList();
		contentList.add("Content-Type: text/html");

		emailService.sendMail(iaReplyTo[0], iaTo, subject, body, iaHeaderTo,
				iaReplyTo, contentList);
		log.info("sending mail from " + iaReplyTo[0] + " to "
				+ toEmail.toString());
	} // end of sendEmail

	/* remove leading whitespace */
	public static String ltrim(String source) {
		return source.replaceAll("^\\s+", "");
	}

	/* remove trailing whitespace */
	public static String rtrim(String source) {
		return source.replaceAll("\\s+$", "");
	}

}
