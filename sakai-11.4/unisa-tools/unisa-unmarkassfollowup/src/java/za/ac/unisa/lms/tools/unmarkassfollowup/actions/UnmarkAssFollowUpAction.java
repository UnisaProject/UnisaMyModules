package za.ac.unisa.lms.tools.unmarkassfollowup.actions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collections;

import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletOutputStream;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.LookupDispatchAction;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.email.api.EmailService;
import org.sakaiproject.event.api.EventTrackingService;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserDirectoryService;

import za.ac.unisa.lms.dao.Gencod;
import za.ac.unisa.lms.dao.StudentSystemGeneralDAO;
import za.ac.unisa.lms.dao.general.DepartmentDAO;
import za.ac.unisa.lms.dao.general.ModuleDAO;
import za.ac.unisa.lms.dao.general.PersonnelDAO;
import za.ac.unisa.lms.dao.general.UserDAO;
import za.ac.unisa.lms.domain.general.Department;
import za.ac.unisa.lms.domain.general.Person;
import za.ac.unisa.lms.domain.general.StudyUnit;
import za.ac.unisa.lms.tools.unmarkassfollowup.forms.*;
import za.ac.unisa.lms.tools.unmarkassfollowup.dao.*;
import za.ac.unisa.utils.CoursePeriodLookup;

public class UnmarkAssFollowUpAction extends LookupDispatchAction{
	
	private SessionManager sessionManager;
	private UserDirectoryService userDirectoryService;
	private EmailService emailService;
	private EventTrackingService eventTrackingService;
	
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

		@Override
	protected Map getKeyMethodMap() {
		// TODO Auto-generated method stub
		Map map = new HashMap();
		map.put("initial", "initial");
		map.put("extractFile", "extractFile");
		map.put("drilDetailList", "drilDetailList");
		map.put("drilRangeList", "drilRangeList");
		map.put("button.detail","detailList");
		map.put("detailList", "detailList");
		map.put("button.summary", "summaryList");
		map.put("summaryList", "summaryList");
		map.put("button.display", "display");
		map.put("button.cancel", "cancel");
		map.put("button.back", "back");
		map.put("button.sendEmail", "sendEmail");
		map.put("emailList", "emailList");
		return map;
	}
		
		
		public ActionForward display(
				ActionMapping mapping,
				ActionForm form,
				HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			
			UnmarkAssFollowUpForm followUpForm = (UnmarkAssFollowUpForm) form;
			ActionMessages messages = new ActionMessages();	
			
			if (followUpForm.getCurrentPage().equalsIgnoreCase("summary")){
				List summaryList = new ArrayList();	
				if (!followUpForm.getSummaryListSummariseOn().equalsIgnoreCase(followUpForm.getSelectedSummariseOn())){
					summaryList = getSummaryListData(mapping, followUpForm, request, response);
				} else {
					summaryList = sortSummaryList(followUpForm.getSelectedSummarySortOn(), followUpForm.getListSummary());
				}
				followUpForm.setListSummary(summaryList);
				return mapping.findForward("summary");
			}else if (followUpForm.getCurrentPage().equalsIgnoreCase("detail")){
				List detailList = new ArrayList();	
				detailList = getDetailListData(mapping, followUpForm, request, response);
				request.setAttribute("detailList", detailList);
				return mapping.findForward("detail");
			}else{
				return mapping.findForward("input");
			}				
		}
		
		public ActionForward sendEmail(
				ActionMapping mapping,
				ActionForm form,
				HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			
			UnmarkAssFollowUpForm followUpForm = (UnmarkAssFollowUpForm) form;
			ActionMessages messages = new ActionMessages();	
			RecipientRecord recipient = new RecipientRecord();
			
			if (followUpForm.getSelectedRecipientIndex().equalsIgnoreCase("")){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"Please select a recipient to which email must be send."));
			}else {				
				recipient = (RecipientRecord) followUpForm.getListRecipient().get(Short.parseShort(followUpForm.getSelectedRecipientIndex()));					
			}
			if (followUpForm.getEmailMessage()==null || followUpForm.getEmailMessage().trim().equalsIgnoreCase("")){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"Message is required."));
			}
			
			if (!messages.isEmpty()) {
				addErrors(request,messages);
				return mapping.findForward("email");		
			}
			
			String studyUnit="";
			String level="";
			DepartmentRecord department = new DepartmentRecord();
			Person marker = new Person();
			AssignmentRecord assignment = new AssignmentRecord();
			String days="";
			String sort="";
			String toDays="";
			
			if (followUpForm.getCurrentReport().equalsIgnoreCase("summary")){
				studyUnit = followUpForm.getSummaryStudyUnit();
				level = followUpForm.getSelectedSummaryBreakdownLevel();
				department = followUpForm.getSummaryDepartment();
				marker = followUpForm.getSummaryMarker();
				assignment = followUpForm.getSummaryAssignment();
				days=followUpForm.getSummaryDaysOutstanding();
				sort=followUpForm.getSelectedSummarySortOn();
				
			}
			if (followUpForm.getCurrentReport().equalsIgnoreCase("detail")){
				studyUnit = followUpForm.getDetailStudyUnit();
				level = followUpForm.getSelectedDetailBreakdownLevel();
				department = followUpForm.getDetailDepartment();
				marker = followUpForm.getDetailMarker();
				assignment = followUpForm.getDetailAssignment();
				days=followUpForm.getDetailDaysOutstanding();
				toDays="0";
				if (followUpForm.getDetailDaysOutstanding()!=null && !followUpForm.getDetailDaysOutstanding().equalsIgnoreCase("")){
					toDays=followUpForm.getDetailToDaysOutstanding();
				}
				sort=followUpForm.getSelectedDetailSortOn();
			}
			
			String serverpath = ServerConfigurationService.getServerUrl();
			String link = "";
			if (serverpath.contains("mydev") || serverpath.contains("localhost")){
				link = "https://mydev.int.unisa.ac.za/unisa-findtool/default.do?sharedTool=unisa.unmarkassfollowup&System=Email";
			} else if (serverpath.contains("myqa")){
				link = "https://myqa.int.unisa.ac.za/unisa-findtool/default.do?sharedTool=unisa.unmarkassfollowup&System=Email";
			}else {
				link = "https://my.unisa.ac.za/unisa-findtool/default.do?sharedTool=unisa.unmarkassfollowup&System=Email";
			}			
			link = link.trim() + "&UserId=" + recipient.getPerson().getNovellUserId();
			link = link.trim() + "&report=" + followUpForm.getCurrentReport();
			link = link.trim() + "&acadYear=" + followUpForm.getAcadYear();
			link = link.trim() + "&semester=" + followUpForm.getSemester();
			link = link.trim() + "&break=" + level;
			link = link.trim() + "&days=" + days;
			link = link.trim() + "&assType=" + followUpForm.getSelectedAssignmentType();
			link = link.trim() + "&modeSub=" + followUpForm.getSelectedModeOfSubmission();
			link = link.trim() + "&modeMark=" + followUpForm.getSelectedModeOfMarking();
			link = link.trim() + "&sortOn=" + sort;
			if (followUpForm.getCurrentReport().equalsIgnoreCase("summary")){
				link = link.trim() + "&summariseOn=" + followUpForm.getSelectedSummariseOn();
			}
			if (followUpForm.getCurrentReport().equalsIgnoreCase("detail")){
				link = link.trim() + "&toDays=" + toDays;
			}
			
			if (level.equalsIgnoreCase("COLLEGE")){
				link = link.trim() + "&collegeCode=" + followUpForm.getSelectedCollegeCode();				
			}
			
			if (level.equalsIgnoreCase("SCHOOL")){
				link = link.trim() + "&collegeCode=" + followUpForm.getSelectedCollegeCode();
				link = link.trim() + "&schoolCode=" + followUpForm.getSelectedSchoolCode();
			}
			
			if (level.equalsIgnoreCase("DEPARTMENT")){
				link = link.trim() + "&dptCode=" + department.getCode().toString();				
			}
			if (level.equalsIgnoreCase("STUDYUNIT")){
				link = link.trim() + "&studyUnit=" + studyUnit.trim();				
			}
			if (level.equalsIgnoreCase("ASSIGNMENT")){
				link = link.trim() + "&studyUnit=" + assignment.getStudyUnit();
				link = link.trim() + "&&assNumber=" + assignment.getAssNumber();
			}
			if (level.equalsIgnoreCase("MARKER")){
				link = link.trim() + "&markerPersno=" + marker.getPersonnelNumber();
			}
			
			//get current date
			Calendar calendar = Calendar.getInstance();	
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date(calendar.getTime().getTime());
			String stringDate=formatter.format(date);			
			java.sql.Timestamp timestamp = new java.sql.Timestamp(calendar.getTime().getTime());
			String tempTimestamp = timestamp.toString();	
			
			String toAddress=recipient.getPerson().getEmailAddress();
			//do not send email on localhost,dev or qa -default email to pretoj@unisa.ac.za	
			if (serverpath.contains("mydev") || serverpath.contains("myqa") || serverpath.contains("localhost")){
				toAddress="pretoj@unisa.ac.za";
			}
			link = link.trim() + "&key1=" + toAddress.trim();
			
			link = link.trim() + "&key2=" + tempTimestamp.substring(0, 10) + tempTimestamp.substring(11);
			String subject = "Outstanding unmark Assignment follow-up";			
			
			try {
				String tmpEmailFrom = ServerConfigurationService.getString("noReplyEmailFrom");
				String serverPath = ServerConfigurationService.getToolUrl();
				InternetAddress toEmail = new InternetAddress(toAddress);
				InternetAddress iaTo[] = new InternetAddress[1];
				iaTo[0] = toEmail;
				InternetAddress iaHeaderTo[] = new InternetAddress[1];
				iaHeaderTo[0] = toEmail;
				InternetAddress iaReplyTo[] = new InternetAddress[1];
				iaReplyTo[0] = new InternetAddress(tmpEmailFrom);

				/* Setup path to contact details action addressee */
				String myUnisaPath = ServerConfigurationService.getServerUrl();
				
				String body = "<html> "+
			    "<body> "+ recipient.getPerson().getName() + "<br/><br/>"+
				"NB: This is an automated response - please do not reply to this e-mail.  <br/><br/> "+
				"The " + followUpForm.getCurrentReport() + " list of outstanding unmarked assignments has been send to you from <br/>" +
				followUpForm.getUser().getName() + ". " + "Click on link below to open list. <br/>" +
				"<A href='"+link+"' target='_blank'><b>Outstanding unmark assignments</b></A>";
				if (followUpForm.getEmailMessage()!=null && !followUpForm.getEmailMessage().equalsIgnoreCase("")){
					body = body.trim() + "<br/>Message: " + followUpForm.getEmailMessage() + "<br/><br/>";
				}
				body = body.trim() + "The outstanding assignment information is also available on myUnisa. <br/>" +
				"On myUnisa, select the 'Course Admin' site from the orange navigation bar at the top. <br/>" +
				"Select 'Follow-up of outstanding unmarked assignments' in the left navigation column to access the tool.";
				body = body.trim() + "</body></html>";
				
				List contentList = new ArrayList();
				contentList.add("Content-Type: text/html");
			  				
				emailService = (EmailService)ComponentManager.get(EmailService.class); 
				emailService.sendMail(iaReplyTo[0],iaTo,subject,body,iaHeaderTo,iaReplyTo,contentList);
				log.info("sending mail from "+iaReplyTo[0]+" to "+toEmail.toString());
				
				EmailLogRecord log = new EmailLogRecord();
				
				log.setEmailAddress(toAddress);
				log.setDateSent(tempTimestamp);
				log.setRecipient(recipient.getPerson().getNovellUserId().toUpperCase());
				log.setRecipientType(recipient.getRoleAbbrv().toUpperCase());
				log.setProgram("ASSFOLLOWUPONLINE");
				log.setEmailType(followUpForm.getCurrentReport().toUpperCase());
				log.setSubject("Outstanding unmark Assignment follow-up");
				String logEmailBody = "Year:" + followUpForm.getAcadYear() + ";" +
				"Semester:" + followUpForm.getSemester() + ";" +
				"Break:" + level  + ";" ;
				if (level.equalsIgnoreCase("COLLEGE")){
					logEmailBody = logEmailBody.trim() + "College:" + followUpForm.getSelectedCollegeCode()+ ";" ;
				}
				if (level.equalsIgnoreCase("SCHOOL")){
					logEmailBody = logEmailBody.trim() + "College:" + followUpForm.getSelectedCollegeCode()+ ";" ;
					logEmailBody = logEmailBody.trim() + "School:" + followUpForm.getSelectedSchoolCode()+ ";" ;
				}
				if (level.equalsIgnoreCase("DEPARTMENT")){
					logEmailBody = logEmailBody.trim() + "Dpt:" + department.getCode().toString()+ ";" ;
				}
				if (level.equalsIgnoreCase("STUDYUNIT")){
					logEmailBody = logEmailBody.trim() + "StudyUnit:" + studyUnit.trim() + ";" ;
				}
				if (level.equalsIgnoreCase("ASSIGNMENT")){
					logEmailBody = logEmailBody.trim() + "StudyUnit:" + assignment.getStudyUnit().trim()+ ";" ;
					logEmailBody = logEmailBody.trim() + "AssNr:" + assignment.getAssNumber().trim() + ";" ;
				}
				if (level.equalsIgnoreCase("MARKER")){
					logEmailBody = logEmailBody.trim() +  "Marker:" + marker.getPersonnelNumber();
				}
				
				logEmailBody = logEmailBody.trim() + "DaysOutstanding:" + days + ";" +
				"AssType:" + followUpForm.getSelectedAssignmentType() + ";" +
				"SubMode:" + followUpForm.getSelectedModeOfSubmission() + ";" +
				"MarkMode:" + followUpForm.getSelectedModeOfMarking()  + ";";
				log.setBody(logEmailBody);		
				
				UnmarkAssFollowUpDAO dao = new UnmarkAssFollowUpDAO();
				
				dao.insertEmailLog(log);				
				
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"Email has been sent."));
				addMessages(request,messages);
				
				} catch (Exception e) {
						log.error("UnmarkAssFollowUpAction: send of email unmark outstanding assignment list failed "+e+" "+e.getMessage());
							e.printStackTrace();
				}
				
			return mapping.findForward("email");			
		}
		
		public ActionForward emailList(
				ActionMapping mapping,
				ActionForm form,
				HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			
			UnmarkAssFollowUpForm followUpForm = (UnmarkAssFollowUpForm) form;
			ActionMessages messages = new ActionMessages();	
			
			//create recipient list
			List listRecipient = new ArrayList();
			List list = new ArrayList();
			String studyUnit="";
			String level="";
			DepartmentRecord department = new DepartmentRecord();
			Person marker = new Person();
			AssignmentRecord assignment = new AssignmentRecord();
			
			if (followUpForm.getCurrentReport().equalsIgnoreCase("summary")){
				studyUnit = followUpForm.getSummaryStudyUnit();
				level = followUpForm.getSelectedSummaryBreakdownLevel();
				department = followUpForm.getSummaryDepartment();
				marker = followUpForm.getSummaryMarker();
				assignment = followUpForm.getSummaryAssignment();
			}
			if (followUpForm.getCurrentReport().equalsIgnoreCase("detail")){
				studyUnit = followUpForm.getDetailStudyUnit();
				level = followUpForm.getSelectedDetailBreakdownLevel();
				department = followUpForm.getDetailDepartment();
				marker = followUpForm.getDetailMarker();
				assignment = followUpForm.getDetailAssignment();
			}
			
			if (level.equalsIgnoreCase("COLLEGE")){
				listRecipient = getDeansForCollege(followUpForm.getSelectedCollegeCode());
			}
			if (level.equalsIgnoreCase("SCHOOL")){
				listRecipient = getDeansForCollege(followUpForm.getSelectedCollegeCode());
				list = getDirectorsForSchool(followUpForm.getSelectedCollegeCode(), followUpForm.getSelectedSchoolCode());
				for (int i=0; i < list.size(); i++){
					RecipientRecord recipient = new RecipientRecord();
					recipient = (RecipientRecord)list.get(i);
					listRecipient.add(recipient);
				}
			}
			if (level.equalsIgnoreCase("DEPARTMENT")){
				listRecipient = getDeansForCollege(department.getCollegeCode());
				list = getDirectorsForSchool(department.getCollegeCode(), department.getSchoolCode());
				for (int i=0; i < list.size(); i++){
					RecipientRecord recipient = new RecipientRecord();
					recipient = (RecipientRecord)list.get(i);
					listRecipient.add(recipient);
				}
				list = new ArrayList();
				list = getCodsForDepartment(department.getCode());
				for (int i=0; i < list.size(); i++){
					RecipientRecord recipient = new RecipientRecord();
					recipient = (RecipientRecord)list.get(i);
					listRecipient.add(recipient);
				}
			}
			if (level.equalsIgnoreCase("STUDYUNIT")){
				ModuleDAO daoModule = new ModuleDAO();
				StudyUnit studyUnitRec =  daoModule.getStudyUnit(studyUnit);	
				listRecipient = getDeansForCollege(studyUnitRec.getCollegeCode());
				list = getDirectorsForSchool(studyUnitRec.getCollegeCode(), studyUnitRec.getSchoolCode());
				for (int i=0; i < list.size(); i++){
					RecipientRecord recipient = new RecipientRecord();
					recipient = (RecipientRecord)list.get(i);
					listRecipient.add(recipient);
				}
				list = new ArrayList();
				list = getCodsForDepartment(studyUnitRec.getDepartmentCode());
				for (int i=0; i < list.size(); i++){
					RecipientRecord recipient = new RecipientRecord();
					recipient = (RecipientRecord)list.get(i);
					listRecipient.add(recipient);
				}
				list = new ArrayList();
				list = getTeachersForStudyUnit(Short.parseShort(followUpForm.getAcadYear()), 
						Short.parseShort(followUpForm.getSemester()), 
						studyUnitRec.getCode());
				for (int i=0; i < list.size(); i++){
					RecipientRecord recipient = new RecipientRecord();
					recipient = (RecipientRecord)list.get(i);
					listRecipient.add(recipient);
				}
			}
			if (level.equalsIgnoreCase("ASSIGNMENT")){
				ModuleDAO daoModule = new ModuleDAO();
				StudyUnit studyUnitRec =  daoModule.getStudyUnit(assignment.getStudyUnit());	
				listRecipient = getDeansForCollege(studyUnitRec.getCollegeCode());
				list = getDirectorsForSchool(studyUnitRec.getCollegeCode(), studyUnitRec.getSchoolCode());
				for (int i=0; i < list.size(); i++){
					RecipientRecord recipient = new RecipientRecord();
					recipient = (RecipientRecord)list.get(i);
					listRecipient.add(recipient);
				}
				list = new ArrayList();
				list = getCodsForDepartment(studyUnitRec.getDepartmentCode());
				for (int i=0; i < list.size(); i++){
					RecipientRecord recipient = new RecipientRecord();
					recipient = (RecipientRecord)list.get(i);
					listRecipient.add(recipient);
				}
				list = new ArrayList();
				list = getTeachersForStudyUnit(Short.parseShort(followUpForm.getAcadYear()), 
						Short.parseShort(followUpForm.getSemester()), 
						studyUnitRec.getCode());
				for (int i=0; i < list.size(); i++){
					RecipientRecord recipient = new RecipientRecord();
					recipient = (RecipientRecord)list.get(i);
					listRecipient.add(recipient);
				}
			}	
			if (level.equalsIgnoreCase("MARKER")){
				Department dpt = new Department();
				DepartmentDAO dptDAO = new DepartmentDAO();
				UnmarkAssFollowUpDAO followUpDao = new UnmarkAssFollowUpDAO();
				
				dpt = dptDAO.getDepartment(Short.parseShort(marker.getDepartmentCode()));
				RecipientRecord recipient = new RecipientRecord();
				String collegeAbbrev = followUpDao.getCollegeAbbreviation(Short.parseShort(dpt.getCollege()));
				if (dpt.getDean().getPersonnelNumber()!=null){
					recipient.setRole("Dean");
					recipient.setRoleAbbrv("DEAN");
					recipient.setPerson(dpt.getDean());
					recipient.setRoleOn(collegeAbbrev);
					listRecipient.add(recipient);
				}					
				for (int i=0; i < dpt.getDeputyDeanList().size(); i++){
					recipient = new RecipientRecord();
					recipient.setRole("Deputy dean");
					recipient.setRoleAbbrv("DEAN");
					recipient.setPerson(((Person)dpt.getDeputyDeanList().get(i)));
					recipient.setRoleOn(collegeAbbrev);
					listRecipient.add(recipient);
				}
				if (dpt.getCod().getPersonnelNumber()!=null){
					recipient = new RecipientRecord();
					recipient.setRole("Cod");
					recipient.setRoleAbbrv("COD");
					recipient.setPerson(dpt.getCod());
					recipient.setRoleOn(dpt.getDescription());
					listRecipient.add(recipient);
				}
				for (int i=0; i < dpt.getActingCodList().size(); i++){
					recipient = new RecipientRecord();
					recipient.setRole("Stand-in COD");
					recipient.setRoleAbbrv("COD");
					recipient.setPerson(((Person)dpt.getActingCodList().get(i)));
					recipient.setRoleOn(dpt.getDescription());
					listRecipient.add(recipient);
				}
				recipient = new RecipientRecord();
				recipient.setRole("Marker");
				recipient.setRoleAbbrv("MARKER");
				recipient.setPerson(marker);	
				recipient.setRoleOn("");
				listRecipient.add(recipient);
			}
			
			followUpForm.setListRecipient(listRecipient);
			
			return mapping.findForward("email");
			
		}
		
		public List getDeansForCollege(Short collegeCode) throws Exception{
			List listRecipient = new ArrayList();			
			UnmarkAssFollowUpDAO dao = new UnmarkAssFollowUpDAO();	
			//dean list is always added
			List deanList = new ArrayList();
			deanList = dao.getDeanList(collegeCode);
			listRecipient = deanList;
			
			return listRecipient;
		}
		
		public List getDirectorsForSchool(Short collegeCode, Short schoolCode) throws Exception{
			List listRecipient = new ArrayList();			
			UnmarkAssFollowUpDAO dao = new UnmarkAssFollowUpDAO();	
			//school director list
			List directorList = new ArrayList();				
			directorList = dao.getDirectorList(collegeCode,schoolCode);	
			listRecipient = directorList;
			
			return listRecipient;
		}
	
		public List getCodsForDepartment(Short dptCode) throws Exception{
			
			List listRecipient = new ArrayList();
			RecipientRecord recipient = new RecipientRecord();
			Department dpt = new Department();
			DepartmentDAO dptDAO = new DepartmentDAO();
			dpt = dptDAO.getDepartment(dptCode);			
			recipient = new RecipientRecord();
			recipient.setRole("COD");
			recipient.setRoleAbbrv("COD");
			recipient.setPerson(dpt.getCod());
			recipient.setRoleOn(dpt.getDescription());
			listRecipient.add(recipient);
			for (int i=0; i <dpt.getActingCodList().size(); i++){
				recipient = new RecipientRecord();
				recipient.setRole("Stand-in COD");
				recipient.setRoleAbbrv("COD");
				recipient.setPerson((Person)dpt.getActingCodList().get(i));
				recipient.setRoleOn(dpt.getDescription());
				listRecipient.add(recipient);
			}
			return listRecipient;
		}
		
		public List getTeachersForStudyUnit(Short year, Short semester, String studyUnit) throws Exception{
			List listRecipient = new ArrayList();
			UnmarkAssFollowUpDAO dao = new UnmarkAssFollowUpDAO();	
			listRecipient = dao.getUSRSUNTeachingRoles(year, semester, studyUnit);			
			return listRecipient;
		}
		
		public ActionForward initial(
				ActionMapping mapping,
				ActionForm form,
				HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			
			UnmarkAssFollowUpForm followUpForm = (UnmarkAssFollowUpForm) form;
			ActionMessages messages = new ActionMessages();	
			
			//Get user details		
			User user = null;
			
			sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
		    userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
			Session currentSession = sessionManager.getCurrentSession();
			String userID  = currentSession.getUserId();
			followUpForm.setUserId("");
			followUpForm.setFromSystem("myUnisa");
			
			if (request.getParameter("SYSTEM")!=null && request.getParameter("SYSTEM").equalsIgnoreCase("Email")){
				followUpForm.setFromSystem("email");
				if (request.getParameter("userId")!=null){
					followUpForm.setUserId(request.getParameter("userId"));
				}
			}else{
				if (userID != null) {
					user = userDirectoryService.getUser(userID);
					followUpForm.setUserId(user.getEid().toUpperCase());
				}
			}
			
			if (followUpForm.getUserId().equalsIgnoreCase("")){
				return mapping.findForward("userUnknown");
			}
			Person person = new Person();
			UserDAO userdao = new UserDAO();
			person = userdao.getPerson(followUpForm.getUserId());			
			
			followUpForm.setUser(person);
			
			//Get semester list
			List list = new ArrayList();
			StudentSystemGeneralDAO dao = new StudentSystemGeneralDAO();
			for (int i=0; i < dao.getGenCodes((short)54,1).size(); i++) {
				list.add(i, (Gencod)(dao.getGenCodes((short)54,1).get(i)));
			}
			followUpForm.setListSemester(list);
			
			//Get breakdown level list
			list = new ArrayList();
			for (int i=0; i < dao.getGenCodes((short)142,3).size(); i++) {
				list.add(i, (Gencod)(dao.getGenCodes((short)142,3).get(i)));
			}
			followUpForm.setListBreakdownLevel(list);
			
			//Get assignment type list
			list = new ArrayList();
			for (int i=0; i < dao.getGenCodes((short)143,1).size(); i++) {
				list.add(i, (Gencod)(dao.getGenCodes((short)143,1).get(i)));
			}
			followUpForm.setListAssignmentType(list);
			

			//Get mode of submission list
			list = new ArrayList();
			for (int i=0; i < dao.getGenCodes((short)144,1).size(); i++) {
				list.add(i, (Gencod)(dao.getGenCodes((short)144,1).get(i)));
			}
			followUpForm.setListModeOfSubmission(list);
			
			//Get mode of marking list
			list = new ArrayList();
			for (int i=0; i < dao.getGenCodes((short)145,1).size(); i++) {
				list.add(i, (Gencod)(dao.getGenCodes((short)145,1).get(i)));
			}			
			followUpForm.setListModeOfMarking(list);					
			
			//Get userType - Dean,Director,Cod,Admin,Academic
			UnmarkAssFollowUpDAO followUpDao = new UnmarkAssFollowUpDAO();
			List collegeList = new ArrayList();
			List dptList = new ArrayList();
			List schoolList = new ArrayList();			
			followUpForm.setUserType("");			
			if (followUpForm.getUser()==null || 
					followUpForm.getUser().getPersonnelNumber()==null || 
					!isInteger(followUpForm.getUser().getPersonnelNumber())){
				if (followUpForm.getUserId().equalsIgnoreCase("DSAA")){
					//email to DSAA - link	
					followUpForm.setUserType("dsaaUser");
				}else if (followUpDao.isAcademic(followUpForm.getUserId())){
					//check if academic				
					followUpForm.setUserType("teaching");
				}				
			}else{				
				//check if dsaaUser
				if (followUpDao.isDsaaUser(followUpForm.getUserId())){
					followUpForm.setUserType("dsaaUser");
				}
				//check if dean
				if (followUpForm.getUserType().equalsIgnoreCase("")){
					collegeList = followUpDao.getCollegeList(Integer.parseInt(followUpForm.getUser().getPersonnelNumber()));
					if (collegeList.size()>0){
					  followUpForm.setUserType("dean");
					}
				}
				//check if director of school
				if (followUpForm.getUserType().equalsIgnoreCase("")){
					schoolList = followUpDao.getSchoolList(Integer.parseInt(followUpForm.getUser().getPersonnelNumber()),null);
					if (schoolList.size()>0){
					  followUpForm.setUserType("director");
					}
				}
				//check if cod
				if (followUpForm.getUserType().equalsIgnoreCase("")){
					dptList = followUpDao.getDepartmentList(Integer.parseInt(followUpForm.getUser().getPersonnelNumber()),null,null); 
					if (dptList.size()>0){
						followUpForm.setUserType("cod");
					}
				}
				//check if academic
				if (followUpForm.getUserType().equalsIgnoreCase("")){
					if (followUpDao.isAcademic(followUpForm.getUserId())){
						followUpForm.setUserType("teaching");
					}
				}
			}
			//if dsaaUser
			//get college list
			//get school list
			//get department list
			if (followUpForm.getUserType().equalsIgnoreCase("dsaaUser")){
				collegeList = followUpDao.getCollegeList(null);
				schoolList = followUpDao.getSchoolList(null,null);
				dptList = followUpDao.getDepartmentList(null,null,null);
//				followUpForm.setSelectedBreakdownLevel("DEPARTMENT");
			}			 
			
			//if dean -
			//get school list
			//get department list
			if (followUpForm.getUserType().equalsIgnoreCase("dean")){
//				if (followUpForm.getSelectedBreakdownLevel()==null ||
//						followUpForm.getSelectedBreakdownLevel().equalsIgnoreCase("")){
//					followUpForm.setSelectedBreakdownLevel("COLLEGE");
//				}	
				List tempDptList = new ArrayList();
				List tempSchoolList = new ArrayList();
				for (int i=0; i < collegeList.size(); i++){
					tempDptList = followUpDao.getDepartmentList(null,((CollegeRecord)collegeList.get(i)).getCode(),null);
					tempSchoolList = followUpDao.getSchoolList(null,((CollegeRecord)collegeList.get(i)).getCode());
					dptList.addAll(tempDptList);
					schoolList.addAll(tempSchoolList);
				}
			}
						
			//if director -
			//get deparment list
			if (followUpForm.getUserType().equalsIgnoreCase("director")){
//				if (followUpForm.getSelectedBreakdownLevel()==null ||
//						followUpForm.getSelectedBreakdownLevel().equalsIgnoreCase("")){
//					followUpForm.setSelectedBreakdownLevel("SCHOOL");
//				}	
				List tempDptList = new ArrayList();
				for (int i=0; i < schoolList.size(); i++){
					tempDptList = followUpDao.getDepartmentList(null,((SchoolRecord)schoolList.get(i)).getCollegeCode(),((SchoolRecord)schoolList.get(i)).getCode());
					dptList.addAll(tempDptList);
				}
			}
			
			//if cod -
//			if (followUpForm.getUserType().equalsIgnoreCase("cod")){
//				if (followUpForm.getSelectedBreakdownLevel()==null ||
//						followUpForm.getSelectedBreakdownLevel().equalsIgnoreCase("")){
//					followUpForm.setSelectedBreakdownLevel("DEPARTMENT");
//				}	
//			}
//			
//			if (followUpForm.getSelectedBreakdownLevel()==null ||
//					followUpForm.getSelectedBreakdownLevel().equalsIgnoreCase("")){
//				followUpForm.setSelectedBreakdownLevel("STUDYUNIT");
//			}	
			
			Collections.sort(dptList, new Comparator() {
				
				public int compare (Object o1, Object o2){
					DepartmentRecord d1 = (DepartmentRecord) o1;
					DepartmentRecord d2 = (DepartmentRecord) o2;
					
					return d1.getDescription().compareToIgnoreCase(d2.getDescription());
					
				}			
			});
			
			Collections.sort(schoolList, new Comparator() {
				
				public int compare (Object o1, Object o2){
					SchoolRecord s1 = (SchoolRecord) o1;
					SchoolRecord s2 = (SchoolRecord) o2;
					
					return s1.getDescription().compareToIgnoreCase(s2.getDescription());
					
				}			
			});			
			
			if (followUpForm.getUserType().equalsIgnoreCase("")){
				new ActionMessage("message.generalmessage",
				"You are not authorised to use this tool.");
				addErrors(request,messages);
			}
			
			followUpForm.setListCollege(collegeList);
			followUpForm.setListSchool(schoolList);			
			followUpForm.setListDepartment(dptList);
			initialiseForm(form);			
			if (followUpForm.getFromSystem().equalsIgnoreCase("email")){
				//record that user has open email
				EmailLogRecord log = new EmailLogRecord();
				String timestampStr = request.getParameter("key2");
				log.setDateSent(timestampStr.substring(0, 10) + " " + timestampStr.substring(10));
				log.setEmailAddress(request.getParameter("key1"));
				//insert record emlapp to indicate that user has opened email				
				followUpDao.insertEmailApp(log, followUpForm.getUserId());
				followUpForm.setAcadYear(request.getParameter("acadYear"));
				followUpForm.setSemester(request.getParameter("semester"));
				followUpForm.setSelectedBreakdownLevel(request.getParameter("break"));
				followUpForm.setDaysOutstanding(request.getParameter("days"));
				followUpForm.setSelectedAssignmentType(request.getParameter("assType"));
				followUpForm.setSelectedModeOfSubmission(request.getParameter("modeSub"));
				followUpForm.setSelectedModeOfMarking(request.getParameter("modeMark"));
//				followUpForm.setAcadYear("2011");
//				followUpForm.setSemester("1");
//				followUpForm.setSelectedBreakdownLevel("ASSIGNMENT");
//				followUpForm.setDaysOutstanding("20");
//				followUpForm.setSelectedAssignmentType("ALL");
//				followUpForm.setSelectedModeOfSubmission("ALL");
//				followUpForm.setSelectedModeOfMarking("ALL");
//				//followUpForm.setSelectedCollegeCode(Short.parseShort("5"));//
//				followUpForm.setSelectedSchool("5~5");
//				return mapping.findForward("summaryDirect");
				if (followUpForm.getSelectedBreakdownLevel().equalsIgnoreCase("COLLEGE")){
					followUpForm.setSelectedCollegeCode(Short.parseShort(request.getParameter("collegeCode")));					
				}
				if (followUpForm.getSelectedBreakdownLevel().equalsIgnoreCase("SCHOOL")){
					followUpForm.setSelectedSchool(request.getParameter("collegeCode").trim()+"~"+request.getParameter("schoolCode").trim());
				}
				if (followUpForm.getSelectedBreakdownLevel().equalsIgnoreCase("DEPARTMENT")){
					followUpForm.setSelectedDptCode(Short.parseShort(request.getParameter("dptCode")));					
				}
				if (followUpForm.getSelectedBreakdownLevel().equalsIgnoreCase("STUDYUNIT")){
					followUpForm.setStudyUnit(request.getParameter("studyUnit"));					
				}
				if (followUpForm.getSelectedBreakdownLevel().equalsIgnoreCase("MARKER")){
					followUpForm.setMarkerPersno(request.getParameter("markerPersno"));					
				}
				if (followUpForm.getSelectedBreakdownLevel().equalsIgnoreCase("ASSIGNMENT")){
					AssignmentRecord assignment = new AssignmentRecord();
					assignment.setStudyUnit(request.getParameter("studyUnit"));
					assignment.setAssNumber(request.getParameter("assNumber"));
//					assignment.setStudyUnit("HMA3703");
//					assignment.setAssNumber("2");
					followUpForm.setAssignment(assignment);					
				}
//				followUpForm.setCurrentPage("email");
//				followUpForm.setFromPage("email");	
//				followUpForm.setSelectedDetailSortOn("daysFirst");
//				followUpForm.setDetailToDaysOutstanding("0");
//				return mapping.findForward("detailDirect");
				
				if (request.getParameter("report").equalsIgnoreCase("detail")){
					/*add code later*/	
					followUpForm.setCurrentPage("email");
					followUpForm.setFromPage("email");	
					followUpForm.setSelectedDetailSortOn(request.getParameter("sortOn"));
					followUpForm.setDetailToDaysOutstanding(request.getParameter("toDays").trim());
					return mapping.findForward("detailDirect");
				}
				if (request.getParameter("report").equalsIgnoreCase("summary")){
					/*add code later*/				
					followUpForm.setCurrentPage("email");
					followUpForm.setFromPage("email");	
					followUpForm.setSelectedSummarySortOn(request.getParameter("sortOn"));
					followUpForm.setSelectedSummariseOn(request.getParameter("summariseOn"));
					return mapping.findForward("summaryDirect");
				}				
			}		
			followUpForm.setCurrentPage("input");
			followUpForm.setFromPage("input");	
			return mapping.findForward("input");
		}
		
		public ActionForward cancel(
				ActionMapping mapping,
				ActionForm form,
				HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			
			UnmarkAssFollowUpForm followUpForm = (UnmarkAssFollowUpForm) form;	
			
			initialiseForm(form);		
							
			return mapping.findForward("input");	
		}
		
		public void initialiseForm(ActionForm form) {
			
			UnmarkAssFollowUpForm followUpForm = (UnmarkAssFollowUpForm) form;	
			
			//initialise fields
			followUpForm.setAcadYear("");
			followUpForm.setSemester("1");
			followUpForm.setSelectedBreakdownLevel("");
			AssignmentRecord assignment = new  AssignmentRecord();
			followUpForm.setAssignment(assignment);
			Person primLecturer = new Person();
			Person marker = new Person();
			followUpForm.setStudyUnit("");
			followUpForm.setStudyUnitDesc("");
			followUpForm.setMarker(marker);
			followUpForm.setSelectedSortOn("");
			followUpForm.setSelectedSummariseOn("");
			followUpForm.setSelectedDetailSortOn("");
			followUpForm.setDaysOutstanding("");
			followUpForm.setSelectedAssignmentType("All");
			followUpForm.setSelectedModeOfSubmission("All");
			followUpForm.setSelectedModeOfMarking("All");
			followUpForm.setMarkerPersno("");	
			CollegeRecord college = new CollegeRecord();
			followUpForm.setCollege(college);
		}
		
		public ActionForward back(
				ActionMapping mapping,
				ActionForm form,
				HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			
			UnmarkAssFollowUpForm followUpForm = (UnmarkAssFollowUpForm) form;	
			
			if (followUpForm.getCurrentPage().equalsIgnoreCase("detail")){
				if(followUpForm.getFromPage().equalsIgnoreCase("summary")){
					followUpForm.setCurrentPage("summary");
					followUpForm.setCurrentReport("summary");
					followUpForm.setFromPage("detail");
					return mapping.findForward("summary");	
				}
			}
			if (followUpForm.getCurrentPage().equalsIgnoreCase("email")){
				if(followUpForm.getCurrentReport().equalsIgnoreCase("summary")){
					followUpForm.setCurrentPage("summary");			
					return mapping.findForward("summary");		
				}
			}
			if (followUpForm.getCurrentPage().equalsIgnoreCase("email")){
				if(followUpForm.getCurrentReport().equalsIgnoreCase("detail")){
					List detailList = new ArrayList();				
					detailList = getDetailListData(mapping, followUpForm, request, response);									
					request.setAttribute("detailList", detailList);
					followUpForm.setCurrentPage("detail");
					return mapping.findForward("detail");		
				}
			}
							
			return mapping.findForward("input");	
		}
		
		public ActionForward drilRangeList(
				ActionMapping mapping,
				ActionForm form,
				HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			
			UnmarkAssFollowUpForm followUpForm = (UnmarkAssFollowUpForm) form;
			ActionMessages messages = new ActionMessages();				
			
			if (!request.getParameter("assNr").equalsIgnoreCase("")){
				followUpForm.setSelectedDetailBreakdownLevel("ASSIGNMENT");
				AssignmentRecord assignment = new AssignmentRecord();
				assignment.setStudyUnit(request.getParameter("studyUnit"));
				assignment.setAssNumber(request.getParameter("assNr"));
				followUpForm.setDetailAssignment(assignment);
				followUpForm.setDetailStudyUnit(request.getParameter("studyUnit"));
			}else if (!request.getParameter("studyUnit").equalsIgnoreCase("")){
				followUpForm.setSelectedDetailBreakdownLevel("STUDYUNIT");
				followUpForm.setDetailStudyUnit(request.getParameter("studyUnit"));
			}else {
				followUpForm.setSelectedDetailBreakdownLevel("DEPARTMENT");
				followUpForm.setSelectedDetailDptCode(Short.parseShort(request.getParameter("dptCode")));
			}
			followUpForm.setDetailDaysOutstanding(request.getParameter("firstValue"));
			if (request.getParameter("lastValue").equalsIgnoreCase("999")){
				followUpForm.setDetailToDaysOutstanding("0");
			}else{
				followUpForm.setDetailToDaysOutstanding(request.getParameter("lastValue"));
			}	
			
			if (followUpForm.getSelectedDetailBreakdownLevel().equalsIgnoreCase("STUDYUNIT")||
				followUpForm.getSelectedDetailBreakdownLevel().equalsIgnoreCase("ASSIGNMENT")){
					ModuleDAO daoModule = new ModuleDAO();
					StudyUnit studyUnit =  daoModule.getStudyUnit(followUpForm.getStudyUnit().trim());				
					if (studyUnit.getCode()==null){
						studyUnit.setCode(followUpForm.getDetailStudyUnit());
						studyUnit.setEngLongDesc("");
					}else{
						followUpForm.setDetailStudyUnitDesc(studyUnit.getEngLongDesc());
					}
			}			
			
			if (followUpForm.getSelectedDetailBreakdownLevel().equalsIgnoreCase("DEPARTMENT")){
				UnmarkAssFollowUpDAO dao = new UnmarkAssFollowUpDAO();
				DepartmentRecord dpt = new DepartmentRecord();
				dpt = dao.getDepartment(followUpForm.getSelectedDetailDptCode());
				followUpForm.setDetailDepartment(dpt);
			}			
			
			List detailList = new ArrayList();				
			detailList = getDetailListData(mapping, followUpForm, request, response);
							
			request.setAttribute("detailList", detailList);
			
			followUpForm.setCurrentReport("detail");
			followUpForm.setCurrentPage("detail");
			followUpForm.setFromPage("summary");
			
			return mapping.findForward("detail");			
		}
		
		public ActionForward drilDetailList(
				ActionMapping mapping,
				ActionForm form,
				HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			
			UnmarkAssFollowUpForm followUpForm = (UnmarkAssFollowUpForm) form;
			ActionMessages messages = new ActionMessages();		
			if (!request.getParameter("assNr").equalsIgnoreCase("")){
				followUpForm.setSelectedDetailBreakdownLevel("ASSIGNMENT");
				AssignmentRecord assignment = new AssignmentRecord();
				assignment.setStudyUnit(request.getParameter("studyUnit"));
				assignment.setAssNumber(request.getParameter("assNr"));
				followUpForm.setDetailAssignment(assignment);
				followUpForm.setDetailStudyUnit(request.getParameter("studyUnit"));
			}else if (!request.getParameter("studyUnit").equalsIgnoreCase("")){
				followUpForm.setSelectedDetailBreakdownLevel("STUDYUNIT");
				followUpForm.setDetailStudyUnit(request.getParameter("studyUnit"));
			}else {
				followUpForm.setSelectedDetailBreakdownLevel("DEPARTMENT");
				followUpForm.setSelectedDetailDptCode(Short.parseShort(request.getParameter("dptCode")));
			}
			followUpForm.setDetailDaysOutstanding(request.getParameter("firstValue"));
			followUpForm.setDetailToDaysOutstanding("0");	
			
			if (followUpForm.getSelectedDetailBreakdownLevel().equalsIgnoreCase("STUDYUNIT")||
				followUpForm.getSelectedDetailBreakdownLevel().equalsIgnoreCase("ASSIGNMENT")){
					ModuleDAO daoModule = new ModuleDAO();
					StudyUnit studyUnit =  daoModule.getStudyUnit(followUpForm.getStudyUnit().trim());				
					if (studyUnit.getCode()==null){
						studyUnit.setCode(followUpForm.getDetailStudyUnit());
						studyUnit.setEngLongDesc("");
					}else{
						followUpForm.setDetailStudyUnitDesc(studyUnit.getEngLongDesc());
					}
				}
			
			if (followUpForm.getSelectedDetailBreakdownLevel().equalsIgnoreCase("DEPARTMENT")){
				UnmarkAssFollowUpDAO dao = new UnmarkAssFollowUpDAO();
				DepartmentRecord dpt = new DepartmentRecord();
				dpt = dao.getDepartment(followUpForm.getSelectedDetailDptCode());
				followUpForm.setDetailDepartment(dpt);
			}			
			
			List detailList = new ArrayList();				
			detailList = getDetailListData(mapping, followUpForm, request, response);
							
			request.setAttribute("detailList", detailList);
			
			followUpForm.setCurrentReport("detail");
			followUpForm.setCurrentPage("detail");
			followUpForm.setFromPage("summary");
			
			return mapping.findForward("detail");
		}
		
		public ActionForward detailList(
				ActionMapping mapping,
				ActionForm form,
				HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			
			UnmarkAssFollowUpForm followUpForm = (UnmarkAssFollowUpForm) form;
			ActionMessages messages = new ActionMessages();				
			
			String nextPage = validateInput(mapping, followUpForm, request, response);
			if (!nextPage.equalsIgnoreCase("")){
				return mapping.findForward(nextPage);
			}
			
			followUpForm.setSelectedDetailBreakdownLevel(followUpForm.getSelectedBreakdownLevel());
			followUpForm.setSelectedDetailDptCode(followUpForm.getSelectedDptCode());
			followUpForm.setDetailDepartment(followUpForm.getDepartment());
			followUpForm.setDetailStudyUnit(followUpForm.getStudyUnit());
			followUpForm.setDetailStudyUnitDesc(followUpForm.getStudyUnitDesc());
			followUpForm.setDetailAssignment(followUpForm.getAssignment());
			followUpForm.setDetailDaysOutstanding(followUpForm.getDaysOutstanding());
			followUpForm.setSelectedDetailSubBreakMarkerName("");
			if (followUpForm.getFromPage().equalsIgnoreCase("input")){
				followUpForm.setDetailToDaysOutstanding("0");
			}
			followUpForm.setDetailMarkerPersno(followUpForm.getMarkerPersno());
			followUpForm.setDetailMarker(followUpForm.getMarker());	
			
			List detailList = new ArrayList();
				
			detailList = getDetailListData(mapping, followUpForm, request, response);
								
			request.setAttribute("detailList", detailList);
			
			followUpForm.setCurrentReport("detail");
			followUpForm.setCurrentPage("detail");			
			
			return mapping.findForward("detail");
		}
		
		public List getDetailListData(
				ActionMapping mapping,
				ActionForm form,
				HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			
			UnmarkAssFollowUpForm followUpForm = (UnmarkAssFollowUpForm) form;
			ActionMessages messages = new ActionMessages();	
			
			UnmarkAssFollowUpDAO dao = new UnmarkAssFollowUpDAO();
			if (followUpForm.getSelectedDetailSortOn()==null || 
					followUpForm.getSelectedDetailSortOn().equalsIgnoreCase("")){
				followUpForm.setSelectedDetailSortOn("daysFirst");
			}
			
			List detailList = new ArrayList();
			if (followUpForm.getSelectedDetailBreakdownLevel().equalsIgnoreCase("COLLEGE")){
				detailList=dao.getDetailList(Short.parseShort(followUpForm.getAcadYear()), 
						Short.parseShort(followUpForm.getSemester()), 
						followUpForm.getSelectedCollegeCode(), 
						null, 
						null, 
						null, 
						null,
						null,
						Integer.parseInt(followUpForm.getDetailDaysOutstanding()),
						followUpForm.getSelectedAssignmentType(),
						followUpForm.getSelectedModeOfSubmission(),
						followUpForm.getSelectedModeOfMarking(),
						Integer.parseInt(followUpForm.getDetailToDaysOutstanding())); 
			}
			if (followUpForm.getSelectedDetailBreakdownLevel().equalsIgnoreCase("SCHOOL")){
				detailList=dao.getDetailList(Short.parseShort(followUpForm.getAcadYear()), 
						Short.parseShort(followUpForm.getSemester()), 
						followUpForm.getSelectedCollegeCode(), 
						followUpForm.getSelectedSchoolCode(), 
						null, 
						null, 
						null,
						null,
						Integer.parseInt(followUpForm.getDetailDaysOutstanding()),
						followUpForm.getSelectedAssignmentType(),
						followUpForm.getSelectedModeOfSubmission(),
						followUpForm.getSelectedModeOfMarking(),
						Integer.parseInt(followUpForm.getDetailToDaysOutstanding()));  
			}
			if (followUpForm.getSelectedDetailBreakdownLevel().equalsIgnoreCase("DEPARTMENT")){
				detailList=dao.getDetailList(Short.parseShort(followUpForm.getAcadYear()), 
						Short.parseShort(followUpForm.getSemester()), 
						null, 
						null, 
						followUpForm.getSelectedDetailDptCode(), 
						null, 
						null,
						null,
						Integer.parseInt(followUpForm.getDetailDaysOutstanding()),
						followUpForm.getSelectedAssignmentType(),
						followUpForm.getSelectedModeOfSubmission(),
						followUpForm.getSelectedModeOfMarking(),
						Integer.parseInt(followUpForm.getDetailToDaysOutstanding())); 
				//flow from summary List - if marker specified only extract data for that marker
				
				if (request.getParameter("markerPersno")== null ||
						request.getParameter("markerPersno").equalsIgnoreCase("") ||
						request.getParameter("markerPersno").equalsIgnoreCase("null")){
					//nothing
				}else {
					List tempList = new ArrayList();
					String marker = request.getParameter("markerPersno");
					if (followUpForm.getSummaryMarker()!=null){
						if (followUpForm.getSummaryMarker().getName()!=null){
							followUpForm.setSelectedDetailSubBreakMarkerName(followUpForm.getSummaryMarker().getName());
						}			
					}
					for (int i=0; i < detailList.size(); i++){
						DetailedRecord record = new DetailedRecord();
						record = (DetailedRecord)detailList.get(i);
						if (marker.equalsIgnoreCase(record.getMarkerPersno())){
							tempList.add(record);
						}						
					}
					detailList = tempList;
				}
			}
			if (followUpForm.getSelectedDetailBreakdownLevel().equalsIgnoreCase("STUDYUNIT")){
				detailList=dao.getDetailList(Short.parseShort(followUpForm.getAcadYear()), 
						Short.parseShort(followUpForm.getSemester()), 
						null, 
						null, 
						null, 
						followUpForm.getDetailStudyUnit(), 
						null,
						null,
						Integer.parseInt(followUpForm.getDetailDaysOutstanding()),
						followUpForm.getSelectedAssignmentType(),
						followUpForm.getSelectedModeOfSubmission(),
						followUpForm.getSelectedModeOfMarking(),
						Integer.parseInt(followUpForm.getDetailToDaysOutstanding())); 
				//flow from summary List - if marker specified only extract data for that marker
				if (request.getParameter("markerPersno")== null ||
						request.getParameter("markerPersno").equalsIgnoreCase("") ||
						request.getParameter("markerPersno").equalsIgnoreCase("null")){
					//nothing
				}else {
					List tempList = new ArrayList();
					String marker = request.getParameter("markerPersno");
					if (followUpForm.getSummaryMarker()!=null){
						if (followUpForm.getSummaryMarker().getName()!=null){
							followUpForm.setSelectedDetailSubBreakMarkerName(followUpForm.getSummaryMarker().getName());
						}			
					}
					for (int i=0; i < detailList.size(); i++){
						DetailedRecord record = new DetailedRecord();
						record = (DetailedRecord)detailList.get(i);
						if (marker.equalsIgnoreCase(record.getMarkerPersno())){
							tempList.add(record);
						}						
					}
					detailList = tempList;
				}
			}
			if (followUpForm.getSelectedDetailBreakdownLevel().equalsIgnoreCase("MARKER")){
				detailList=dao.getDetailList(Short.parseShort(followUpForm.getAcadYear()), 
						Short.parseShort(followUpForm.getSemester()), 
						null, 
						null, 
						null, 
						followUpForm.getDetailStudyUnit(), 
						followUpForm.getMarker().getPersonnelNumber(),
						followUpForm.getDetailAssignment(),
						Integer.parseInt(followUpForm.getDetailDaysOutstanding()),
						followUpForm.getSelectedAssignmentType(),
						followUpForm.getSelectedModeOfSubmission(),
						followUpForm.getSelectedModeOfMarking(),
						Integer.parseInt(followUpForm.getDetailToDaysOutstanding())); 
				
				if (followUpForm.getUserType().equalsIgnoreCase("dean") || 
						followUpForm.getUserType().equalsIgnoreCase("director") ||
						followUpForm.getUserType().equalsIgnoreCase("cod")){
				//only extract data in dean's colleges
					List tempList = new ArrayList();
					for (int i =0 ; i < detailList.size(); i++){
						DetailedRecord record = (DetailedRecord) detailList.get(i);
						for (int j=0 ; j < followUpForm.getListDepartment().size(); j++){
							DepartmentRecord dpt = (DepartmentRecord) followUpForm.getListDepartment().get(j);
							if (record.getDptCode().compareTo(((DepartmentRecord) followUpForm.getListDepartment().get(j)).getCode())==0){
								tempList.add(record);
								j = followUpForm.getListDepartment().size();								
							}
						}
					}
					detailList = tempList;
				}	
				if (followUpForm.getUserType().equalsIgnoreCase("teaching")){
				//only extract data for studyunits to which the user is linked
					List studyUnitList = new ArrayList();
					studyUnitList = dao.getUserStudyUnitList(Short.parseShort(followUpForm.getAcadYear()), 
						Short.parseShort(followUpForm.getSemester()),followUpForm.getUserId());
					List tempList = new ArrayList();
					for (int i =0 ; i < detailList.size(); i++){
						DetailedRecord record = (DetailedRecord) detailList.get(i);
						for (int j=0 ; j < studyUnitList.size(); j++){
							if (record.getStudyUnit().equalsIgnoreCase(studyUnitList.get(j).toString().trim())){
								tempList.add(record);
								j = studyUnitList.size();								
							}
						}
					}
					detailList = tempList;
				}
			}
			if (followUpForm.getSelectedDetailBreakdownLevel().equalsIgnoreCase("ASSIGNMENT")){
				detailList=dao.getDetailList(Short.parseShort(followUpForm.getAcadYear()), 
						Short.parseShort(followUpForm.getSemester()), 
						null, 
						null, 
						null, 
						followUpForm.getDetailStudyUnit(), 
						null,
						followUpForm.getDetailAssignment(),
						Integer.parseInt(followUpForm.getDetailDaysOutstanding()),
						followUpForm.getSelectedAssignmentType(),
						followUpForm.getSelectedModeOfSubmission(),
						followUpForm.getSelectedModeOfMarking(),
						Integer.parseInt(followUpForm.getDetailToDaysOutstanding())); 
				//flow from summary List - if marker specified only extract data for that marker
				if (request.getParameter("markerPersno")== null ||
						request.getParameter("markerPersno").equalsIgnoreCase("") ||
						request.getParameter("markerPersno").equalsIgnoreCase("null")){
					//nothing
				}else {
					List tempList = new ArrayList();
					String marker = request.getParameter("markerPersno");
					if (followUpForm.getSummaryMarker()!=null){
						if (followUpForm.getSummaryMarker().getName()!=null){
							followUpForm.setSelectedDetailSubBreakMarkerName(followUpForm.getSummaryMarker().getName());
						}			
					}
					for (int i=0; i < detailList.size(); i++){
						DetailedRecord record = new DetailedRecord();
						record = (DetailedRecord)detailList.get(i);
						if (marker.equalsIgnoreCase(record.getMarkerPersno())){
							tempList.add(record);
						}						
					}
					detailList = tempList;
				}
			}
			
			sortDetailList(followUpForm.getSelectedDetailSortOn(), detailList);
			
			//write ~ delimited file
			String fileName="";
			if (detailList.size()>0){
				String path = getServlet().getServletContext().getInitParameter("applicationFullPath")+"/";
				String fileDir = path +"/";
				String time = (new java.text.SimpleDateFormat("yyyyMMddhhmmssss").format(new java.util.Date()).toString());
				fileName = fileDir + followUpForm.getUserId() +"_detailList_"+ time +".txt";			
				writeDetailFile(detailList, fileName);
				followUpForm.setDownloadDetailFile(fileName);
			}	
				
			return detailList;
		}
		
		public String validateInput(
				ActionMapping mapping,
				ActionForm form,
				HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			
			UnmarkAssFollowUpForm followUpForm = (UnmarkAssFollowUpForm) form;
			ActionMessages messages = new ActionMessages();	
			
			UnmarkAssFollowUpDAO dao = new UnmarkAssFollowUpDAO();
			
			//validate input
			if (followUpForm.getAcadYear()==null || followUpForm.getAcadYear().trim().equalsIgnoreCase("")){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"Academic Year is required"));
			} else {
				if (!isInteger(followUpForm.getAcadYear())){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"Academic Year must be numeric"));
				}
			}
			if (followUpForm.getSemester()==null || followUpForm.getSemester().trim().equalsIgnoreCase("")){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"Semester is required"));
			}else {
				if (!isInteger(followUpForm.getSemester())){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"Semester must be numeric"));
				}
			}	
			if (followUpForm.getDaysOutstanding()==null || followUpForm.getDaysOutstanding().trim().equalsIgnoreCase("")){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"Days Outstanding is required"));
			}else {
				if (!isInteger(followUpForm.getDaysOutstanding())){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"Days Outstanding must be numeric"));
				}
			}
			//breakdown level - studyUnit 
			if (followUpForm.getSelectedBreakdownLevel().equalsIgnoreCase("STUDYUNIT")){
				if (followUpForm.getStudyUnit()==null || followUpForm.getStudyUnit().trim().equalsIgnoreCase("")){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"Study Unit is required"));
				}else{
					//check if valid study unit
					//check if user has access to study unit
					followUpForm.setStudyUnit(followUpForm.getStudyUnit().trim().toUpperCase());
					followUpForm.setStudyUnitDesc("");
					ModuleDAO daoModule = new ModuleDAO();
					StudyUnit studyUnit =  daoModule.getStudyUnit(followUpForm.getStudyUnit().trim());	
					if (studyUnit.getCode()==null){
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage",
											"Study Unit not found"));
					}else {
						if (!accessToModule(studyUnit, 
								Short.parseShort(followUpForm.getAcadYear()), 
								Short.parseShort(followUpForm.getSemester()), 
								followUpForm.getUserId(), 
								followUpForm.getUserType(), 
								followUpForm.getListCollege(),
								followUpForm.getListSchool(),
								followUpForm.getListDepartment())){
								messages.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("message.generalmessage",
												"You do not have access to this module"));
						}else{
							followUpForm.setStudyUnitDesc(studyUnit.getEngLongDesc());
						}
					}
				}
			}
			
			//Breakdown level - Assignment
			if (followUpForm.getSelectedBreakdownLevel().equalsIgnoreCase("ASSIGNMENT")){
				if (followUpForm.getAssignment().getStudyUnit()==null || followUpForm.getAssignment().getStudyUnit().trim().equalsIgnoreCase("")){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"Assignment Study Unit is required"));
				}else {
					//check if user has access to module
					//check if valid study unit
					//check if user has access to study unit
					ModuleDAO daoModule = new ModuleDAO();
					followUpForm.getAssignment().setStudyUnit(followUpForm.getAssignment().getStudyUnit().toUpperCase().trim());
					StudyUnit studyUnit =  daoModule.getStudyUnit(followUpForm.getAssignment().getStudyUnit());						
					if (studyUnit.getCode()==null){
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage",
											"Study Unit not found"));	
					} else {
						if (!accessToModule(studyUnit, 
								Short.parseShort(followUpForm.getAcadYear()), 
								Short.parseShort(followUpForm.getSemester()), 
								followUpForm.getUserId(), 
								followUpForm.getUserType(), 
								followUpForm.getListCollege(),
								followUpForm.getListSchool(),
								followUpForm.getListDepartment())){
								messages.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("message.generalmessage",
												"You do not have access to the assignment's module"));
						}
					}
				}
				if (followUpForm.getAssignment().getAssNumber()==null || followUpForm.getAssignment().getAssNumber().trim().equalsIgnoreCase("")){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"Assignment Number is required"));
				}else{
					if(!isInteger(followUpForm.getAssignment().getAssNumber().trim())){
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage",
											"Assignment Number must be numeric"));
					}
				}				
			}		
			
			//Breakdown level - Marker
			if (followUpForm.getSelectedBreakdownLevel().equalsIgnoreCase("MARKER")){
				if (followUpForm.getMarkerPersno()==null || followUpForm.getMarkerPersno().trim().equalsIgnoreCase("")){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"Marker's personnel number is required"));
				}else{
					if (isInteger(followUpForm.getMarkerPersno())){
						Person person = new Person();
						PersonnelDAO personDAO = new PersonnelDAO();
						person = personDAO.getPersonnelFromSTAFF(Integer.parseInt(followUpForm.getMarkerPersno()));
						if (person.getPersonnelNumber()==null){
							messages.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("message.generalmessage",
												"Invalid personnel number"));
						}else {
							followUpForm.setMarker(person);
						}
					}else {
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage",
											"Marker's personnel number must be numeric"));
					}	
				}
			}
						
			if (!messages.isEmpty()) {
				addErrors(request,messages);
				return "input";			
			}
			

			CoursePeriodLookup periodLookup = new CoursePeriodLookup();
			followUpForm.setSemesterType(periodLookup.getSemesterPeriodAsString(Short.parseShort(followUpForm.getSemester())));
			
			
			if (followUpForm.getSelectedBreakdownLevel().equalsIgnoreCase("COLLEGE")){
				boolean collegeFound = false;
				for (int i=0; i< followUpForm.getListCollege().size(); i++){
					CollegeRecord college = (CollegeRecord)followUpForm.getListCollege().get(i);
					if (followUpForm.getSelectedCollegeCode().compareTo(college.getCode())==0){
						followUpForm.setCollege(college);
						collegeFound = true;
						i = followUpForm.getListCollege().size();						
					}
				}
				if (!collegeFound){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"You do not have access to this college"));
				}
			}
			if (followUpForm.getSelectedBreakdownLevel().equalsIgnoreCase("SCHOOL")){
				boolean schoolFound = false;
				for (int i=0; i < followUpForm.getListSchool().size(); i++){
					SchoolRecord school = (SchoolRecord)followUpForm.getListSchool().get(i);
					if (followUpForm.getSelectedSchool().trim().equalsIgnoreCase(school.getCollegeSchoolCode())){
						followUpForm.setSelectedCollegeCode(school.getCollegeCode());
						followUpForm.setSelectedSchoolCode(school.getCode());
						followUpForm.setSchool(school);
						schoolFound = true;
						i = followUpForm.getListSchool().size();
					}
				}
				if (!schoolFound){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"You do not have access to this school"));
				}
			}
			if (followUpForm.getSelectedBreakdownLevel().equalsIgnoreCase("DEPARTMENT")){
				boolean dptFound = false;
				for (int i=0; i< followUpForm.getListDepartment().size(); i++){
					DepartmentRecord dpt = (DepartmentRecord )followUpForm.getListDepartment().get(i);
					if (followUpForm.getSelectedDptCode().compareTo(dpt.getCode())==0){
						followUpForm.setDepartment(dpt);
						dptFound=true;
						i=followUpForm.getListDepartment().size();
					}
				}
				if (!dptFound){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"You do not have access to this department"));
				}
			}	
			
			if (!messages.isEmpty()) {
				addErrors(request,messages);
				return "input";			
			}
			
			return "";
			
		}
		
		public ActionForward summaryList(
				ActionMapping mapping,
				ActionForm form,
				HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			
			UnmarkAssFollowUpForm followUpForm = (UnmarkAssFollowUpForm) form;
			ActionMessages messages = new ActionMessages();	
			
			String nextPage = validateInput(mapping, followUpForm, request, response);
			if (!nextPage.equalsIgnoreCase("")){
				return mapping.findForward(nextPage);
			}
			
			followUpForm.setSelectedSummaryBreakdownLevel(followUpForm.getSelectedBreakdownLevel());
			followUpForm.setSelectedSummaryDptCode(followUpForm.getSelectedDptCode());
			followUpForm.setSummaryDepartment(followUpForm.getDepartment());
			followUpForm.setSummaryStudyUnit(followUpForm.getStudyUnit());
			followUpForm.setSummaryStudyUnitDesc(followUpForm.getStudyUnitDesc());
			followUpForm.setSummaryAssignment(followUpForm.getAssignment());
			followUpForm.setSummaryDaysOutstanding(followUpForm.getDaysOutstanding());
			followUpForm.setSummaryMarkerPersno(followUpForm.getMarkerPersno());
			followUpForm.setSummaryMarker(followUpForm.getMarker());
			followUpForm.setSelectedDetailSubBreakMarkerName("");
						
			List summaryList = new ArrayList();
			
			summaryList = getSummaryListData(mapping, followUpForm, request, response);	
//			request.setAttribute("summaryList", summaryList);
			followUpForm.setListSummary(summaryList);
						
			followUpForm.setCurrentReport("summary");
			followUpForm.setCurrentPage("summary");
						
			return mapping.findForward("summary");
		}
		
		public List getSummaryListData(
				ActionMapping mapping,
				ActionForm form,
				HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			
			UnmarkAssFollowUpForm followUpForm = (UnmarkAssFollowUpForm) form;
			ActionMessages messages = new ActionMessages();		
							
			UnmarkAssFollowUpDAO dao = new UnmarkAssFollowUpDAO();
			List summaryList = new ArrayList();
			
			if (followUpForm.getSelectedSummaryBreakdownLevel().equalsIgnoreCase("COLLEGE")){
				if (followUpForm.getSelectedSummariseOn()==null ||
						followUpForm.getSelectedSummariseOn().equalsIgnoreCase("")){
					followUpForm.setSelectedSummariseOn("DEPARTMENT");
				}
				if (followUpForm.getSelectedSummarySortOn()==null ||
						followUpForm.getSelectedSummarySortOn().equalsIgnoreCase("")){
					followUpForm.setSelectedSummarySortOn("daysFirst");
				}
				summaryList=dao.getSummaryList(Short.parseShort(followUpForm.getAcadYear()), 
						Short.parseShort(followUpForm.getSemester()), 
						followUpForm.getSelectedCollegeCode(), 
						null, 
						null, 
						null, 
						null,
						null,
						Integer.parseInt(followUpForm.getSummaryDaysOutstanding()),
						followUpForm.getSelectedAssignmentType(),
						followUpForm.getSelectedModeOfSubmission(),
						followUpForm.getSelectedModeOfMarking(),
						followUpForm.getSelectedSummariseOn(),
						followUpForm.getSelectedSummarySortOn()); 
			}
			if (followUpForm.getSelectedSummaryBreakdownLevel().equalsIgnoreCase("SCHOOL")){
				if (followUpForm.getSelectedSummariseOn()==null ||
						followUpForm.getSelectedSummariseOn().equalsIgnoreCase("")){
					followUpForm.setSelectedSummariseOn("DEPARTMENT");
				}
				if (followUpForm.getSelectedSummarySortOn()==null ||
						followUpForm.getSelectedSummarySortOn().equalsIgnoreCase("")){
					followUpForm.setSelectedSummarySortOn("daysFirst");
				}
				summaryList=dao.getSummaryList(Short.parseShort(followUpForm.getAcadYear()), 
						Short.parseShort(followUpForm.getSemester()), 
						followUpForm.getSelectedCollegeCode(), 
						followUpForm.getSelectedSchoolCode(), 
						null, 
						null, 
						null,
						null,
						Integer.parseInt(followUpForm.getSummaryDaysOutstanding()),
						followUpForm.getSelectedAssignmentType(),
						followUpForm.getSelectedModeOfSubmission(),
						followUpForm.getSelectedModeOfMarking(),
						followUpForm.getSelectedSummariseOn(),
						followUpForm.getSelectedSummarySortOn()); 
			}
			if (followUpForm.getSelectedSummaryBreakdownLevel().equalsIgnoreCase("DEPARTMENT")){
				if (followUpForm.getSelectedSummariseOn()==null ||
						followUpForm.getSelectedSummariseOn().equalsIgnoreCase("")){
					followUpForm.setSelectedSummariseOn("STUDYUNIT");
				}
				if (followUpForm.getSelectedSummarySortOn()==null ||
						followUpForm.getSelectedSummarySortOn().equalsIgnoreCase("")){
					followUpForm.setSelectedSummarySortOn("daysFirst");
				}
				summaryList=dao.getSummaryList(Short.parseShort(followUpForm.getAcadYear()), 
						Short.parseShort(followUpForm.getSemester()), 
						null, 
						null, 
						followUpForm.getSelectedSummaryDptCode(), 
						null, 
						null,
						null,
						Integer.parseInt(followUpForm.getSummaryDaysOutstanding()),
						followUpForm.getSelectedAssignmentType(),
						followUpForm.getSelectedModeOfSubmission(),
						followUpForm.getSelectedModeOfMarking(),
						followUpForm.getSelectedSummariseOn(),
						followUpForm.getSelectedSummarySortOn()); 
			}
			if (followUpForm.getSelectedSummaryBreakdownLevel().equalsIgnoreCase("STUDYUNIT")){
				if (followUpForm.getSelectedSummariseOn()==null ||
						followUpForm.getSelectedSummariseOn().equalsIgnoreCase("")){
					followUpForm.setSelectedSummariseOn("ASSIGNMENT");
				}
				if (followUpForm.getSelectedSummarySortOn()==null ||
						followUpForm.getSelectedSummarySortOn().equalsIgnoreCase("")){
					followUpForm.setSelectedSummarySortOn("daysFirst");
				}
				summaryList=dao.getSummaryList(Short.parseShort(followUpForm.getAcadYear()), 
						Short.parseShort(followUpForm.getSemester()), 
						null, 
						null, 
						null, 
						followUpForm.getSummaryStudyUnit(), 
						null,
						null,
						Integer.parseInt(followUpForm.getDaysOutstanding()),
						followUpForm.getSelectedAssignmentType(),
						followUpForm.getSelectedModeOfSubmission(),
						followUpForm.getSelectedModeOfMarking(),
						followUpForm.getSelectedSummariseOn(),
						followUpForm.getSelectedSummarySortOn()); 
			}
			if (followUpForm.getSelectedSummaryBreakdownLevel().equalsIgnoreCase("MARKER")){
				if (followUpForm.getSelectedSummariseOn()==null ||
						followUpForm.getSelectedSummariseOn().equalsIgnoreCase("")){
					followUpForm.setSelectedSummariseOn("ASSIGNMENT");
				}
				if (followUpForm.getSelectedSummarySortOn()==null ||
						followUpForm.getSelectedSummarySortOn().equalsIgnoreCase("")){
					followUpForm.setSelectedSummarySortOn("daysFirst");
				}
				//Narrow search down to improve performance if user linked to one college, one school or one dpt
				Short inputCollege = null;
				Short inputSchool = null;
				Short inputDpt = null;
				String inputStudyUnit = null;
				if (followUpForm.getListCollege().size()==1){
					inputCollege = ((CollegeRecord)followUpForm.getListCollege().get(0)).getCode();
				}
				if (followUpForm.getListSchool().size()==1){
					inputCollege = ((SchoolRecord)followUpForm.getListSchool().get(0)).getCollegeCode();
					inputSchool = ((SchoolRecord)followUpForm.getListSchool().get(0)).getCode();
				}
				if (followUpForm.getListDepartment().size()==1){
					inputDpt = ((DetailedRecord)followUpForm.getListDepartment().get(0)).getDptCode();
				}
				if (followUpForm.getUserType().equalsIgnoreCase("teaching")){
					List studyUnitList = new ArrayList();
					studyUnitList = dao.getUserStudyUnitList(Short.parseShort(followUpForm.getAcadYear()), 
						Short.parseShort(followUpForm.getSemester()),followUpForm.getUserId());
					if (studyUnitList.size()==1){
						inputStudyUnit = studyUnitList.get(0).toString();
					}else{
						inputDpt = Short.parseShort(followUpForm.getUser().getDepartmentCode());
					}					
				}
				summaryList=dao.getSummaryList(Short.parseShort(followUpForm.getAcadYear()), 
						Short.parseShort(followUpForm.getSemester()), 
						inputCollege, 
						inputSchool, 
						inputDpt, 
						inputStudyUnit,										 //followUpForm.getSummaryStudyUnit(),
						null,
						Integer.parseInt(followUpForm.getSummaryMarkerPersno()),
						Integer.parseInt(followUpForm.getDaysOutstanding()),
						followUpForm.getSelectedAssignmentType(),
						followUpForm.getSelectedModeOfSubmission(),
						followUpForm.getSelectedModeOfMarking(),
						followUpForm.getSelectedSummariseOn(),
						followUpForm.getSelectedSummarySortOn()); 
				
				if (followUpForm.getUserType().equalsIgnoreCase("dean") || 
						followUpForm.getUserType().equalsIgnoreCase("director") ||
						followUpForm.getUserType().equalsIgnoreCase("cod")){
				//only extract data in dean's colleges
					List tempList = new ArrayList();
					for (int i =0 ; i < summaryList.size(); i++){
						SummaryRecord record = (SummaryRecord) summaryList.get(i);
						for (int j=0 ; j < followUpForm.getListDepartment().size(); j++){
							DepartmentRecord dpt = (DepartmentRecord) followUpForm.getListDepartment().get(j);
							if (record.getDepartment().getCode().compareTo(((DepartmentRecord) followUpForm.getListDepartment().get(j)).getCode())==0){
								tempList.add(record);
								j = followUpForm.getListDepartment().size();								
							}
						}
					}
					summaryList = tempList;
				}	
				if (followUpForm.getUserType().equalsIgnoreCase("teaching")){
				//only extract data for studyunits to which the user is linked
					List studyUnitList = new ArrayList();
					studyUnitList = dao.getUserStudyUnitList(Short.parseShort(followUpForm.getAcadYear()), 
						Short.parseShort(followUpForm.getSemester()),followUpForm.getUserId());
					List tempList = new ArrayList();
					for (int i =0 ; i < summaryList.size(); i++){
						SummaryRecord record = (SummaryRecord) summaryList.get(i);
						for (int j=0 ; j < studyUnitList.size(); j++){
							if (record.getStudyUnit().equalsIgnoreCase(studyUnitList.get(j).toString().trim())){
								tempList.add(record);
								j = studyUnitList.size();								
							}
						}
					}
					summaryList = tempList;
				}
				
			}
			if (followUpForm.getSelectedSummaryBreakdownLevel().equalsIgnoreCase("ASSIGNMENT")){
				if (followUpForm.getSelectedSummariseOn()==null ||
						followUpForm.getSelectedSummariseOn().equalsIgnoreCase("")){
					followUpForm.setSelectedSummariseOn("ASSIGNMENT");
				}
				if (followUpForm.getSelectedSummarySortOn()==null ||
						followUpForm.getSelectedSummarySortOn().equalsIgnoreCase("")){
					followUpForm.setSelectedSummarySortOn("daysFirst");
				}
				summaryList=dao.getSummaryList(Short.parseShort(followUpForm.getAcadYear()), 
						Short.parseShort(followUpForm.getSemester()), 
						null, 
						null, 
						null, 
						followUpForm.getSummaryStudyUnit(), 
						followUpForm.getAssignment(),
						null,
						Integer.parseInt(followUpForm.getDaysOutstanding()),
						followUpForm.getSelectedAssignmentType(),
						followUpForm.getSelectedModeOfSubmission(),
						followUpForm.getSelectedModeOfMarking(),
						followUpForm.getSelectedSummariseOn(),
						followUpForm.getSelectedSummarySortOn()); 
			}
			
			for (int i=0; i < summaryList.size(); i++){
				int total =0;
				SummaryRecord rec_i = new SummaryRecord();
				rec_i = (SummaryRecord)summaryList.get(i);
				total = Integer.parseInt(rec_i.getTotal());
				for (int j=0; j < summaryList.size(); j++){
					SummaryRecord rec_j = new SummaryRecord();
					rec_j = (SummaryRecord)summaryList.get(j);
					if (followUpForm.getSelectedSummariseOn().equalsIgnoreCase("DEPARTMENT")){
						if (rec_i.getSchoolAbbreviation().equalsIgnoreCase(rec_j.getSchoolAbbreviation())&&
								rec_i.getDepartment().getCode().compareTo(rec_j.getDepartment().getCode())==0){
							if (rec_i.getFirstValueInRange().compareTo(rec_j.getFirstValueInRange())<0){
								total = total + Integer.parseInt(rec_j.getTotal());
							}
						}
					}
					if (followUpForm.getSelectedSummariseOn().equalsIgnoreCase("STUDYUNIT")){
						if (rec_i.getSchoolAbbreviation().equalsIgnoreCase(rec_j.getSchoolAbbreviation())&&
								rec_i.getDepartment().getCode().compareTo(rec_j.getDepartment().getCode())==0 &&
								rec_i.getStudyUnit().equalsIgnoreCase(rec_j.getStudyUnit())){
							if (rec_i.getFirstValueInRange().compareTo(rec_j.getFirstValueInRange())<0){
								total = total + Integer.parseInt(rec_j.getTotal());
							}
						}						
					}
					if (followUpForm.getSelectedSummariseOn().equalsIgnoreCase("ASSIGNMENT")){
						if (rec_i.getSchoolAbbreviation().equalsIgnoreCase(rec_j.getSchoolAbbreviation())&&
								rec_i.getDepartment().getCode().compareTo(rec_j.getDepartment().getCode())==0 &&
								rec_i.getStudyUnit().equalsIgnoreCase(rec_j.getStudyUnit())&&
								rec_i.getAssignmentNr().equalsIgnoreCase(rec_j.getAssignmentNr())){
							if (rec_i.getFirstValueInRange().compareTo(rec_j.getFirstValueInRange())<0){
								total = total + Integer.parseInt(rec_j.getTotal());
							}
						}		
					}
				}
				((SummaryRecord)summaryList.get(i)).setAccumalate(String.valueOf(total));
				
				int value=rec_i.getFirstValueInRange().intValue();
				switch (value){
				case 0: ((SummaryRecord)summaryList.get(i)).setDaysOutstanding(">0");break;
				case 11: ((SummaryRecord)summaryList.get(i)).setDaysOutstanding(">10");break;
				case 21: ((SummaryRecord)summaryList.get(i)).setDaysOutstanding(">20");break;
				case 31: ((SummaryRecord)summaryList.get(i)).setDaysOutstanding(">30");break;
				case 41: ((SummaryRecord)summaryList.get(i)).setDaysOutstanding(">40");break;
				case 51: ((SummaryRecord)summaryList.get(i)).setDaysOutstanding(">50");break;
				case 61: ((SummaryRecord)summaryList.get(i)).setDaysOutstanding(">60");break;
				case 71: ((SummaryRecord)summaryList.get(i)).setDaysOutstanding(">70");break;
				case 81: ((SummaryRecord)summaryList.get(i)).setDaysOutstanding(">80");break;
				case 91: ((SummaryRecord)summaryList.get(i)).setDaysOutstanding(">90");break;
				default : ((SummaryRecord)summaryList.get(i)).setDaysOutstanding(">100");break;
				}				
			}
			sortSummaryList(followUpForm.getSelectedSummarySortOn(), summaryList);
			
			//write ~ delimited file
			String fileName="";
			if (summaryList.size()>0){
				String path = getServlet().getServletContext().getInitParameter("applicationFullPath")+"/";
				String fileDir = path +"/";
				String time = (new java.text.SimpleDateFormat("yyyyMMddhhmmssss").format(new java.util.Date()).toString());
				fileName = fileDir + followUpForm.getUserId() +"_SummaryList_"+ time +".txt";			
				writeSummaryFile(summaryList, fileName);
				followUpForm.setDownloadSummaryFile(fileName);
			}		
			
			followUpForm.setSummaryListSummariseOn(followUpForm.getSelectedSummariseOn());			
			
			return summaryList;
			
		}
		
		public ActionForward extractFile(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			
				UnmarkAssFollowUpForm followUpForm = (UnmarkAssFollowUpForm) form;
			 
				//tell browser program going to return an application file 
			        //instead of html page
			        response.setContentType("application/octet-stream");
			        response.setHeader("Pragma", "private");
			        response.setHeader("Cache-Control", "private, must-revalidate");
			        if (followUpForm.getCurrentPage().equalsIgnoreCase("detail")){
			        	response.setHeader("Content-Disposition","attachment;filename=detailList.txt");
			        }else{
			        	response.setHeader("Content-Disposition","attachment;filename=summaryList.txt");
			        }
			        
			 
				try 
				{
					ServletOutputStream out = response.getOutputStream();	
					String fileName = "";
					 if (followUpForm.getCurrentPage().equalsIgnoreCase("detail")){
						 fileName = followUpForm.getDownloadDetailFile();
					 }else{
						 fileName = followUpForm.getDownloadSummaryFile();
					 }
					File file = new File(fileName);
					BufferedInputStream is = new BufferedInputStream(new FileInputStream(file));
					byte[] buf = new byte[4 * 1024]; // 4K buffer
					int bytesRead;
					while ((bytesRead = is.read(buf)) != -1) {
					out.write(buf, 0, bytesRead);
					}
					is.close();
					out.close(); 			 
				  }catch (Exception e) {e.printStackTrace();}	
				  return null;
			}
		
		public List sortSummaryList(String sortOrder, List summaryList){			
			
			if (sortOrder.equalsIgnoreCase("daysLast")){
				Collections.sort(summaryList, new Comparator() {
					
					public int compare (Object o1, Object o2){
						SummaryRecord m1 = (SummaryRecord) o1;
						SummaryRecord m2 = (SummaryRecord) o2;
						return m1.getSchoolAbbreviation().compareToIgnoreCase(m2.getSchoolAbbreviation());	
					}
				});
				
				Collections.sort(summaryList, new Comparator() {
					
					public int compare (Object o1, Object o2){
						SummaryRecord m1 = (SummaryRecord) o1;
						SummaryRecord m2 = (SummaryRecord) o2;
						if (m1.getSchoolAbbreviation().equalsIgnoreCase(m2.getSchoolAbbreviation())){
							return m1.getDepartment().getDescription().compareToIgnoreCase(m2.getDepartment().getDescription());							
						}else{
							return -1;
						}
					}
				});
				Collections.sort(summaryList, new Comparator() {
					
					public int compare (Object o1, Object o2){
						SummaryRecord m1 = (SummaryRecord) o1;
						SummaryRecord m2 = (SummaryRecord) o2;
						if (m1.getSchoolAbbreviation().equalsIgnoreCase(m2.getSchoolAbbreviation())&&
								m1.getDepartment().getDescription().equalsIgnoreCase(m2.getDepartment().getDescription())){
							return m1.getStudyUnit().compareToIgnoreCase(m2.getStudyUnit());							
						}else{
							return -1;
						}
					}
				});
				Collections.sort(summaryList, new Comparator() {
					
					public int compare (Object o1, Object o2){
						SummaryRecord m1 = (SummaryRecord) o1;
						SummaryRecord m2 = (SummaryRecord) o2;
						if (isInteger(m1.getAssignmentNr())&& isInteger(m2.getAssignmentNr())){
							if (m1.getSchoolAbbreviation().equalsIgnoreCase(m2.getSchoolAbbreviation())&&
									m1.getDepartment().getDescription().equalsIgnoreCase(m2.getDepartment().getDescription())&&
									m1.getStudyUnit().equalsIgnoreCase(m2.getStudyUnit())){
								Short assNr1 = Short.parseShort(m1.getAssignmentNr());
								Short assNr2 = Short.parseShort(m2.getAssignmentNr());							
								if (assNr1.compareTo(assNr2)==0){
									return 0;
								}else if(assNr1.compareTo(assNr2)<0){							
									return -1;
								} else {
									return 1;
								}												
							}else{
								return -1;
							}
						}else{
							return 0;
						}
					}
				});
				Collections.sort(summaryList, new Comparator() {
					
					public int compare (Object o1, Object o2){
						SummaryRecord m1 = (SummaryRecord) o1;
						SummaryRecord m2 = (SummaryRecord) o2;
						if (m1.getSchoolAbbreviation().equalsIgnoreCase(m2.getSchoolAbbreviation())&&
								m1.getDepartment().getDescription().equalsIgnoreCase(m2.getDepartment().getDescription())&&
								m1.getStudyUnit().equalsIgnoreCase(m2.getStudyUnit())){
							if (m1.getFirstValueInRange().compareTo(m2.getFirstValueInRange())==0){
								return 0;
							}else if(m1.getFirstValueInRange().compareTo(m2.getFirstValueInRange())<0){							
								return 1;
							} else {
								return -1;
							}							
						}else{
							return -1;
						}
					}
				});
			}
			if (sortOrder.equalsIgnoreCase("daysFirst")){
				Collections.sort(summaryList, new Comparator() {
					
					public int compare (Object o1, Object o2){
						SummaryRecord m1 = (SummaryRecord) o1;
						SummaryRecord m2 = (SummaryRecord) o2;
						if (m1.getFirstValueInRange().compareTo(m2.getFirstValueInRange())==0){
							return 0;
						}else if(m1.getFirstValueInRange().compareTo(m2.getFirstValueInRange())<0){							
							return 1;
						} else {
							return -1;
						}
					}
				});
				Collections.sort(summaryList, new Comparator() {
					
					public int compare (Object o1, Object o2){
						SummaryRecord m1 = (SummaryRecord) o1;
						SummaryRecord m2 = (SummaryRecord) o2;
						if (m1.getFirstValueInRange().compareTo(m2.getFirstValueInRange())==0){
							return m1.getSchoolAbbreviation().compareToIgnoreCase(m2.getSchoolAbbreviation());							
						}else{
							return -1;
						}
					}
				});
				Collections.sort(summaryList, new Comparator() {
					
					public int compare (Object o1, Object o2){
						SummaryRecord m1 = (SummaryRecord) o1;
						SummaryRecord m2 = (SummaryRecord) o2;
						if (m1.getFirstValueInRange().compareTo(m2.getFirstValueInRange())==0){
							return m1.getSchoolAbbreviation().compareToIgnoreCase(m2.getSchoolAbbreviation());							
						}else{
							return -1;
						}
					}
				});
				Collections.sort(summaryList, new Comparator() {
					
					public int compare (Object o1, Object o2){
						SummaryRecord m1 = (SummaryRecord) o1;
						SummaryRecord m2 = (SummaryRecord) o2;
						if (m1.getFirstValueInRange().compareTo(m2.getFirstValueInRange())==0 &&
								m1.getSchoolAbbreviation().equalsIgnoreCase(m2.getSchoolAbbreviation())){
							return m1.getDepartment().getDescription().compareToIgnoreCase(m2.getDepartment().getDescription());							
						}else{
							return -1;
						}
					}
				});
				Collections.sort(summaryList, new Comparator() {
					
					public int compare (Object o1, Object o2){
						SummaryRecord m1 = (SummaryRecord) o1;
						SummaryRecord m2 = (SummaryRecord) o2;
						if (m1.getFirstValueInRange().compareTo(m2.getFirstValueInRange())==0 &&
								m1.getSchoolAbbreviation().equalsIgnoreCase(m2.getSchoolAbbreviation())&&
								m1.getDepartment().getDescription().equalsIgnoreCase(m2.getDepartment().getDescription())){
							return m1.getStudyUnit().compareToIgnoreCase(m2.getStudyUnit());							
						}else{
							return -1;
						}
					}
				});
				Collections.sort(summaryList, new Comparator() {
					
					public int compare (Object o1, Object o2){
						SummaryRecord m1 = (SummaryRecord) o1;
						SummaryRecord m2 = (SummaryRecord) o2;
						if (isInteger(m1.getAssignmentNr())&& isInteger(m2.getAssignmentNr())){
							if (m1.getFirstValueInRange().compareTo(m2.getFirstValueInRange())==0 &&
									m1.getSchoolAbbreviation().equalsIgnoreCase(m2.getSchoolAbbreviation())&&
									m1.getDepartment().getDescription().equalsIgnoreCase(m2.getDepartment().getDescription()) &&
									m1.getStudyUnit().equalsIgnoreCase(m2.getStudyUnit())){
								Short assNr1 = Short.parseShort(m1.getAssignmentNr());
								Short assNr2 = Short.parseShort(m2.getAssignmentNr());							
								if (assNr1.compareTo(assNr2)==0){
									return 0;
								}else if(assNr1.compareTo(assNr2)<0){							
									return -1;
								} else {
									return 1;
								}												
							}else{
								return -1;
							}
						}else{
							return 0;
						}
					}
				});
			}
			
			return summaryList;
		}
		
		public List sortDetailList(String sortOrder, List detailList){			
			
			if (sortOrder.equalsIgnoreCase("daysLast")){
				Collections.sort(detailList, new Comparator() {
					
					public int compare (Object o1, Object o2){
						DetailedRecord m1 = (DetailedRecord) o1;
						DetailedRecord m2 = (DetailedRecord) o2;
						return m1.getDptDesc().compareToIgnoreCase(m2.getDptDesc());	
					}
				});
				
				Collections.sort(detailList, new Comparator() {
					
					public int compare (Object o1, Object o2){
						DetailedRecord m1 = (DetailedRecord) o1;
						DetailedRecord m2 = (DetailedRecord) o2;
						if (m1.getDptDesc().equalsIgnoreCase(m2.getDptDesc())){
							return m1.getStudyUnit().compareToIgnoreCase(m2.getStudyUnit());							
						}else{
							return -1;
						}
					}
				});
				Collections.sort(detailList, new Comparator() {
					
					public int compare (Object o1, Object o2){
						DetailedRecord m1 = (DetailedRecord) o1;
						DetailedRecord m2 = (DetailedRecord) o2;
						if (isInteger(m1.getAssignmentNr())&& isInteger(m2.getAssignmentNr())){
							if (m1.getDptDesc().equalsIgnoreCase(m2.getDptDesc())&&
									m1.getStudyUnit().equalsIgnoreCase(m2.getStudyUnit())){
								Short assNr1 = Short.parseShort(m1.getAssignmentNr());
								Short assNr2 = Short.parseShort(m2.getAssignmentNr());							
								if (assNr1.compareTo(assNr2)==0){
									return 0;
								}else if(assNr1.compareTo(assNr2)<0){							
									return -1;
								} else {
									return 1;
								}												
							}else{
								return -1;
							}
						}else{
							return 0;
						}
					}
				});
				Collections.sort(detailList, new Comparator() {
					
					public int compare (Object o1, Object o2){
						DetailedRecord m1 = (DetailedRecord) o1;
						DetailedRecord m2 = (DetailedRecord) o2;
						Short assNr1 = Short.parseShort(m1.getAssignmentNr());
						Short assNr2 = Short.parseShort(m2.getAssignmentNr());	
						if (m1.getDptDesc().equalsIgnoreCase(m2.getDptDesc())&&
								m1.getStudyUnit().equalsIgnoreCase(m2.getStudyUnit()) &&
								assNr1.compareTo(assNr2)==0){
							if (Integer.parseInt(m1.getDaysOutstanding()) == Integer.parseInt(m2.getDaysOutstanding())){
								return 0;
							}else if(Integer.parseInt(m1.getDaysOutstanding()) < Integer.parseInt(m2.getDaysOutstanding())){							
								return 1;
							} else {
								return -1;
							}							
						}else{
							return -1;
						}
					}
				});
			}
			if (sortOrder.equalsIgnoreCase("daysFirst")){
				Collections.sort(detailList, new Comparator() {
					
					public int compare (Object o1, Object o2){
						DetailedRecord m1 = (DetailedRecord) o1;
						DetailedRecord m2 = (DetailedRecord) o2;
						if (Integer.parseInt(m1.getDaysOutstanding()) == Integer.parseInt(m2.getDaysOutstanding())){
							return 0;
						}else if(Integer.parseInt(m1.getDaysOutstanding()) < Integer.parseInt(m2.getDaysOutstanding())){							
							return 1;
						} else {
							return -1;
						}
					}
				});
				
				Collections.sort(detailList, new Comparator() {
					
					public int compare (Object o1, Object o2){
						DetailedRecord m1 = (DetailedRecord) o1;
						DetailedRecord m2 = (DetailedRecord) o2;
						if (Integer.parseInt(m1.getDaysOutstanding()) == Integer.parseInt(m2.getDaysOutstanding())){
							return m1.getDptDesc().compareToIgnoreCase(m2.getDptDesc());							
						}else{
							return -1;
						}
					}
				});
				Collections.sort(detailList, new Comparator() {
					
					public int compare (Object o1, Object o2){
						DetailedRecord m1 = (DetailedRecord) o1;
						DetailedRecord m2 = (DetailedRecord) o2;
						if (Integer.parseInt(m1.getDaysOutstanding()) == Integer.parseInt(m2.getDaysOutstanding()) &&
								m1.getDptDesc().equalsIgnoreCase(m2.getDptDesc())){
							return m1.getStudyUnit().compareToIgnoreCase(m2.getStudyUnit());							
						}else{
							return -1;
						}
					}
				});
				Collections.sort(detailList, new Comparator() {
					
					public int compare (Object o1, Object o2){
						DetailedRecord m1 = (DetailedRecord) o1;
						DetailedRecord m2 = (DetailedRecord) o2;
						if (Integer.parseInt(m1.getDaysOutstanding()) == Integer.parseInt(m2.getDaysOutstanding()) &&
								m1.getDptDesc().equalsIgnoreCase(m2.getDptDesc()) &&
								m1.getStudyUnit().equalsIgnoreCase(m2.getStudyUnit())){
							Short assNr1 = Short.parseShort(m1.getAssignmentNr());
							Short assNr2 = Short.parseShort(m2.getAssignmentNr());							
							if (assNr1.compareTo(assNr2)==0){
								return 0;
							}else if(assNr1.compareTo(assNr2)<0){							
								return -1;
							} else {
								return 1;
							}										
						}else{
							return -1;
						}
					}
				});
			}
			
			return detailList;
		}

		
		public boolean accessToModule(StudyUnit studyUnit, Short year, Short semester, String userId, String userType, List listCollege, List listSchool, List listDepartment) throws Exception {
			
			UnmarkAssFollowUpDAO dao = new UnmarkAssFollowUpDAO();
			
			//check if user has access to study unit
				
			boolean access = false;
				if (userType.equalsIgnoreCase("dsaaUser")){
					access = true;
				}
				if (userType.equalsIgnoreCase("dean")){
					for (int i=0; i < listCollege.size(); i++){
						CollegeRecord college = new CollegeRecord();
						college = (CollegeRecord)listCollege.get(i);
						if (college.getCode().compareTo(studyUnit.getCollegeCode())==0){
							access = true;
						}
					}
				}
				if (userType.equalsIgnoreCase("director")){
					for (int i=0; i < listSchool.size(); i++){
						SchoolRecord school = new SchoolRecord();
						school = (SchoolRecord)listSchool.get(i);
						String studyUnitSchool=studyUnit.getCollegeCode().toString().trim() + "~" + studyUnit.getSchoolCode().toString().trim();
						if (school.getCollegeSchoolCode().equalsIgnoreCase(studyUnitSchool)){
							access = true;
						}
					}						
				}
				if (userType.equalsIgnoreCase("cod")){
					for (int i=0; i < listDepartment.size(); i++){
						DepartmentRecord dpt = new DepartmentRecord();
						dpt = (DepartmentRecord)listDepartment.get(i);
						if (dpt.getCode().compareTo(studyUnit.getDepartmentCode())==0){
							access = true;
						}
					}
				}
				if (userType.equalsIgnoreCase("teaching")){
					if(dao.isValidAcademic(userId,studyUnit.getCode().trim(),year,semester)){
						access = true;
					}
				}
				//could be dean, director or cod and also be academic in other module
				if (access==false && !userType.equalsIgnoreCase("teaching")){
					if(dao.isValidAcademic(userId,studyUnit.getCode().trim(),year,semester)){
						access = true;
					}
				}
			return access;
		}
		
		public static void writeDetailFile(List records, String fileName){
			 //Create file 
			 try {
				 FileOutputStream out = new FileOutputStream(new File(fileName));
				 PrintStream ps = new PrintStream(out);
				 String headingLine = "Days~Dpt~Study Unit~Ass Nr~Student Nr~Received~Ass Type~Sub Mode~Marking Mode~PrimL~Marker~DocketNr";				 
				 ps.print(headingLine);
				 ps.println();
				 for (int i=0; i<records.size(); i++){
					 DetailedRecord record = new DetailedRecord();
					 record = (DetailedRecord)records.get(i);
					 String line = record.getDaysOutstanding() + '~' + record.getDptDesc() + '~' + record.getStudyUnit() + 
					 '~' + record.getAssignmentNr() + '~' +	record.getStudentNr() + '~' + record.getDateReceived() + '~' + record.getAssignmentType() + 
					 '~' + record.getSubmissionMode() + '~' + record.getMarkingMode() + '~' + record.getPrimLecturer() + '~' + record.getMarker() + 
					 '~' + record.getDocketNr();				 
					 ps.print(line);
					 ps.println(); 		
				 }
				 ps.close();
			 } catch (Exception e) {}		
		}
		public static void writeSummaryFile(List records, String fileName){
			 //Create file 
			 try {
				 FileOutputStream out = new FileOutputStream(new File(fileName));
				 PrintStream ps = new PrintStream(out);
				 String headingLine = "Days Outstanding~Accum Total~Range~Total~College~School~Department~Study Unit~PrimL~Ass Nr";				 
				 ps.print(headingLine);
				 ps.println();
				 for (int i=0; i<records.size(); i++){
					 SummaryRecord record = new SummaryRecord();
					 record = (SummaryRecord)records.get(i);
					 String line = record.getDaysOutstanding() + '~' + record.getAccumalate() + '~' + record.getDaysOutstandingRange() + '~' + record.getTotal() + '~' + record.getCollegeAbbreviation() + '~' + record.getSchoolAbbreviation() + 
					 '~' + record.getDepartment().getDescription() + '~' + record.getStudyUnit() + '~' +  record.getPrimLecturer().getName() + '~' +  record.getAssignmentNr();
					 ps.print(line);
					 ps.println(); 		
				 }
				 ps.close();
			 } catch (Exception e) {}		
		}
		
		public boolean isInteger(String stringValue) {
			try
			{
				Integer i = Integer.parseInt(stringValue);
				return true;
			}	
			catch(NumberFormatException e)
			{}
			return false;
		}


}
