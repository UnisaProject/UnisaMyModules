package za.ac.unisa.lms.tools.mdrpmresults.actions;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.internet.InternetAddress;
import javax.servlet.ServletException;
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
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.email.api.EmailService;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.user.api.UserDirectoryService;

import za.ac.unisa.lms.tools.mdrpmresults.Constants;
import za.ac.unisa.lms.tools.mdrpmresults.MdRPMResultsBr;
import za.ac.unisa.lms.tools.mdrpmresults.dao.MdRPMResultsQueryDAO;
import za.ac.unisa.lms.tools.mdrpmresults.forms.MdRPMResultsForm;
import za.ac.unisa.lms.tools.mdrpmresults.forms.Qualification;
import za.ac.unisa.lms.tools.mdrpmresults.forms.Staff;
import za.ac.unisa.lms.tools.mdrpmresults.forms.Student;
import za.ac.unisa.lms.tools.mdrpmresults.forms.StudyUnit;

public class MdRPMResultsAction extends LookupDispatchAction {


	public static Log log = LogFactory.getLog(MdRPMResultsAction.class);

	private SessionManager 		 sessionManager;
	private UserDirectoryService userDirectoryService;
	private EmailService emailService;
	private static final String REFERRAL_EMAIL_PATH = "za/ac/unisa/lms/tools/mdrpmresults/templates/referral-email.vm";

	protected Map getKeyMethodMap() {

		Map<String, String> map = new HashMap<String, String>();
		map.put(Constants.ENTER_STUDENT_KEY, Constants.ENTER_STUDENT_VALUE);
		map.put(Constants.DISPLAY_KEY, Constants.DISPLAY_VALUE);
		map.put(Constants.BUTTON_SIGNOFF_KEY, Constants.BUTTON_SIGNOFF_VALUE);
		map.put(Constants.BUTTON_CANCEL_KEY, Constants.BUTTON_CANCEL_VALUE);
		return map;
	}

	public ActionForward enterstudent(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		sessionManager 				= (SessionManager) ComponentManager.get(SessionManager.class);
		userDirectoryService 		= (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);	

		MdRPMResultsForm mdForm 	=  (MdRPMResultsForm) form;
		Session currentSession 		= sessionManager.getCurrentSession();
		String userID 				= currentSession.getUserId();

		log.debug("Check if theres a userId on the current session");
		if (userID != null) {
			log.debug("userId code: "+ userDirectoryService.getUserEid(userID));
			mdForm.setUserCode(userDirectoryService.getUserEid(userID));
		}

		return mapping.findForward("enterstudentforward");
	}


	public ActionForward display(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		ActionMessages messages		= new ActionMessages();
		MdRPMResultsForm mdForm		=  (MdRPMResultsForm) form;
		MdRPMResultsQueryDAO dao 	= new MdRPMResultsQueryDAO();		
		
		Staff loggedInUser = dao.getStaffRoutDet(mdForm.getUserCode());
		//Staff loggedInUser = dao.getStaffRoutDet("KRUGEGJ");
		//Staff loggedInUser = dao.getStaffRoutDet("COUSISA");		

		mdForm.setLoggedInUser(loggedInUser);
		
		if (mdForm.getStudent() == null || 
			mdForm.getStudent().getStudentNumber() == Constants.EMPTY_STRING)
		{
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
						"Please enter a student number."));
			addErrors(request, messages);

			return mapping.findForward("enterstudentforward");
		}		
		
		Student student =  dao.getStudentRecord(
									mdForm.getStudent().getStudentNumber(), 
									String.valueOf(
										Calendar.getInstance().get(Calendar.YEAR)),
									Constants.REGISTERED_STATUS_CODE,
									Constants.RPM_FLAG,
									Constants.FK_ADRCATCODE_STU);
		
		if (student == null)
		{
			messages.add(ActionMessages.GLOBAL_MESSAGE,
			new ActionMessage("message.generalmessage",
				"The student is not registered for Reaserach Proposal Module"));
			addErrors(request, messages);
		
			return mapping.findForward("enterstudentforward");
		}
		
		student.setStudentNumber(mdForm.getStudent().getStudentNumber());
		mdForm.setStudent(student);

		//Populate results checkboxes
		mdForm.setResults(MdRPMResultsBr.getResults());
		
		//Populate review or final sign-off checkboxes
		mdForm.setReviewOrFinalValues(MdRPMResultsBr.getReviewOrFinalValues());
		//The current logged in user
		Staff loggedInuser 		= mdForm.getLoggedInUser();
		
		//If the logged in user is flagged as final sign-off 
		//set Final Sing-off checked
		if (MdRPMResultsBr.isFinalSignOff(
				loggedInuser.getStaffNr(), 
				student.getQualification()))
		{
			String [] selectedReviewOrFinalValue = {Constants.FINAL_SIGNOFF_CODE};
			mdForm.setSelectedReviewOrFinalValue(selectedReviewOrFinalValue);
		}
		
		StudyUnit studyUnit 	= student.getStudyUnit();
		//Populate supervisors
		mdForm.setSupervisors(dao.getSupervisorList(
										student.getStudentNumber(), 
										studyUnit.getCode()));
		
		//populate title
		mdForm.setTitle(dao.getTitle(
								student.getStudentNumber(), 
								studyUnit.getCode()));
	
		//Populate the sign-off list
		mdForm.setSignOffList(dao.getSignoffList(
										student.getQualification(), 
										student.getStudentNumber(), 
										student.getApplSequenceNr()));
		
		//Populate the routing list
		mdForm.setRoutingList(dao.getRoutingList(student.getQualification()));
		
		//Set previous sign-off record
		mdForm.setPrevSignOffRecord(
				dao.getPrevSignOffRecord(
								loggedInuser.getStaffNr(),
								student.getQualification(), 
								student.getStudentNumber(), 
								student.getApplSequenceNr()));
		//Set previ
		if (mdForm.getPrevSignOffRecord() != null &&
			mdForm.getPrevSignOffRecord().getSignOffStatus() != null)
		{
			String status = mdForm.getPrevSignOffRecord().getSignOffStatus();
			String [] selectedResult = {status};
			mdForm.setSelectedResults(selectedResult);
		}
		
		if (!dao.signOffRecordExists(
				student.getStudentNumber(), 
				student.getApplSequenceNr()))
		{		
			mdForm.setFirstSignOff(true);
			
			if (!MdRPMResultsBr.isStudentSupervisor(
										mdForm.getSupervisors(),
										loggedInuser.getStaffNr()))
			{
				messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage",
					"You are not indicated as the current supervisor for this student." +
					"Please contact the M&D section to rectify any omissions " +
					"and/or errors in this regard."));
				addErrors(request, messages);
				
				//TODO implement display-only on the page.
				mdForm.setCanSignOff(false);
				return mapping.findForward("displayforward");
			}
		}
		
		if (!MdRPMResultsBr.isUserInRoutingList(
						loggedInuser.getStaffNr(), 
						student.getQualification()) && 
			!MdRPMResultsBr.isStudentSupervisor(
								mdForm.getSupervisors(),
								loggedInuser.getStaffNr()))
		{
			//TODO implement display-only on the page.
			mdForm.setCanSignOff(false);
			
		}		
		
		return mapping.findForward("displayforward");
	}

	public ActionForward signoff(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		ActionMessages messages		= new ActionMessages();
		MdRPMResultsForm mdForm		=  (MdRPMResultsForm) form;
		MdRPMResultsQueryDAO dao 	= new MdRPMResultsQueryDAO();
		
		try{
			updateCheckBoxValues(mapping,form, request, response);
			
			String [] selectedResults = mdForm.getSelectedResults();
			
			if (selectedResults == null || selectedResults.length==0 )
			{
				messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage",
					"Please note that you cannot sign-off without indicating a result. " +
					"Record your decision by clicking in the tick box under the " +
					"<strong><i>Select the appropriate result for the Research Proposal</i></strong>" +
					" heading before signing-off or click CANCEL to escape."));
				addErrors(request, messages);
						
				return mapping.findForward("displayforward");
			}
			
			if (selectedResults != null && selectedResults.length > 1 )
			{
				messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage",
					"You may only select one of the results under the " +
					"</strong><i>Select the appropriate result for the Research Proposal</i></strong> " +
					"heading. Please remove the incorrect selection."));
				addErrors(request, messages);
						
				return mapping.findForward("displayforward");
			}
			
			if (selectedResults != null && 
				Constants.FAILED_RESULTS_CODE.equalsIgnoreCase(selectedResults[0]) &&
				(mdForm.getComment() == null || 
				 mdForm.getComment().trim() == Constants.EMPTY_STRING ))
			{
				messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage",
					"You have selected a fail result for this candidate. " +
					"Please indicate your reason and explication in the comment box below."));
				addErrors(request, messages);
						
				return mapping.findForward("displayforward");
			}
			
			if (mdForm.getTitle() == null ||
				Constants.EMPTY_STRING.equalsIgnoreCase(mdForm.getTitle()))
			{
				messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage",
					"Please update the title on the title tool, refresh this " +
					"page and then continue to sign-off."));
				addErrors(request, messages);
						
				return mapping.findForward("displayforward");
			}
			
			if (mdForm.getSupervisors() == null || mdForm.getSupervisors().size() == 0)
			{
				messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage",
					"At least one supervisor must be captured before you can sign-off. " +
					"Click on the <strong>Amend title and/or supervisor</strong> " +
					"button to capture the supervisor."));
				addErrors(request, messages);
						
				return mapping.findForward("displayforward");
			}
			
			Staff prevSignOffRecord 	= mdForm.getPrevSignOffRecord();		
			String reviewoption 		= mdForm.getReviewoption();	
			boolean isReviewSelected 	= MdRPMResultsBr.isReviewSelected(reviewoption);
			String finaloption 			= mdForm.getFinaloption();			
			boolean isFinalSelected 	= MdRPMResultsBr.isFinalSelected(finaloption);
			
			if (!isReviewSelected && !isFinalSelected && mdForm.isFinalSignOff())
			{
				messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage",
					"Please click on the appropriate final decision under the heading " +
					"<strong> Refer back for Review or Final Sign-off </strong>and " +
					"then click on the Sign-off button."));
				addErrors(request, messages);
						
				return mapping.findForward("displayforward");
			}
			
			if (isReviewSelected && isFinalSelected)
			{
				messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage",
					"Please note that you cannot select the Review tick box together " +
					"with a Final Sign-off decision. Either review the initial " +
					"decision or do the final sign-off."));
				addErrors(request, messages);
						
				return mapping.findForward("displayforward");
			}
			
			if (isReviewSelected &&!MdRPMResultsBr.isPrevResultsExists(prevSignOffRecord))
			{
				messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage",
					"You can only select the review tick box if you are reviewing a " +
					"previous decision. No decision has been recorded for this student. " +
					"Please click on the review box to remove the tick."));
				addErrors(request, messages);
						
				return mapping.findForward("displayforward");
			}
			
			Staff loggedInuser 	= mdForm.getLoggedInUser();
			if (isReviewSelected && 
				MdRPMResultsBr.isUserSameAsPrevSignee(prevSignOffRecord, loggedInuser))
			{
				messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage",
					"You are not allowed to review your own decision. Remove the " +
					"review button by clicking on the review tick box. If you " +
					"want to amend your initial decision simply change the decision " +
					"and sign-off."));
				addErrors(request, messages);
						
				return mapping.findForward("displayforward");
			}
			
			boolean isCurrentResultSameAsPrevResults = 
							MdRPMResultsBr.isCurrentResultSameAsPrevResults(
													prevSignOffRecord, 
													selectedResults[0]);
			
			if (isReviewSelected && isCurrentResultSameAsPrevResults)
			{
					messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
						"You are not allowed to review your own decision. Remove the " +
						"review button by clicking on the review tick box. If you " +
						"want to amend your initial decision simply change the decision " +
						"and sign-off."));
					addErrors(request, messages);
							
					return mapping.findForward("displayforward");
			}
			
			if(mdForm.getComment()!= null && !"".equals(mdForm.getComment().trim())){
				int commentLen = mdForm.getComment().trim().length();
				if(commentLen > 500){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
									"The sign-off comment cannot exceed 500 characters. " +
									"It is currently "+(commentLen)+" characters."));
					addErrors(request, messages);
					return mapping.findForward("displayforward");
				}
			}

			if(loggedInuser ==null || 
			   loggedInuser.getStaffNr() == null || 
			   "".equals(loggedInuser.getStaffNr())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
								"Sign-off error: The logged in user's("+ mdForm.getUserCode() +") staff number is empty."));
				addErrors(request, messages);
				return mapping.findForward("displayforward");
			}
			
			if (isReviewSelected)
			{
				dao.writeSignOff(
						Constants.REVIEW_CODE, 
						mdForm.getStudent().getApplSequenceNr(), 
						mdForm.getStudent().getStudentNumber(), 
						loggedInuser);
				
			}else if (mdForm.isFinalSignOff())
			{
				String finalSignOffCode = 
					MdRPMResultsBr.getFinalSignOffCode(selectedResults[0]);
				
				dao.writeSignOff(
						finalSignOffCode, 
						mdForm.getStudent().getApplSequenceNr(), 
						mdForm.getStudent().getStudentNumber(), 
						loggedInuser);
			} 
			
			//Insert a result record if review is not selected
			if (!isReviewSelected)
			{
				dao.writeSignOff(
						selectedResults[0], 
						mdForm.getStudent().getApplSequenceNr(), 
						mdForm.getStudent().getStudentNumber(), 
						loggedInuser);
			}
			
			Student student = mdForm.getStudent();
			
			//Insert M&D activity record
			if (!isReviewSelected)
			{				
				if (Constants.COMPLIED_RESULTS_CODE.equals(selectedResults[0]) ||
					Constants.NOT_COMPLIED_RESULTS_CODE.equals(selectedResults[0])){
					
					String mdActivity = null;
					
					if (Constants.COMPLIED_RESULTS_CODE.equals(selectedResults[0])){	
			
						mdActivity = Constants.MD_ACTIVITY_10;
					}
					else if (Constants.NOT_COMPLIED_RESULTS_CODE.equals(selectedResults[0])){
			
						mdActivity = Constants.MD_ACTIVITY_9;
					}
		
					dao.insertMDActivity(
							student.getStudentNumber(), 
							student.getStudyUnit().getCode(),
							mdForm.getLoggedInUser().getStaffNr(), 
							mdActivity,
							mdForm.getComment());
				}
			}
			
			if (!mdForm.isFinalSignOff())
			{
				ArrayList<Staff> routingList = dao.getRoutingList(
													student.getQualification());
				Staff nextReceipient = MdRPMResultsBr.getNextRecipient(
															routingList, 
															isReviewSelected, 
															mdForm.getLoggedInUser());
				
				sendEmail(mdForm, nextReceipient.getEmailAddress());
			}
						
			messages.add(ActionMessages.GLOBAL_MESSAGE,
        			new ActionMessage("message.generalmessage", "Sign-off completed successfully."));
	        addMessages(request, messages);
	        
			return mapping.findForward("displayforward");
		}catch (Exception e){
			throw new Exception("MdAdmissionAction(signoff): / "+e.getMessage(), e);
		}
	}

	public ActionForward cancel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		MdRPMResultsForm mdForm	=  (MdRPMResultsForm) form;
			
		reset(mdForm);
		return mapping.findForward("cancelforward");
	}

	public ActionForward unspecified(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response){

		log.info("MdRPMResultsAction: unspecified method call -no value for parameter in request");
		return mapping.findForward("home");

	}

	public void reset(MdRPMResultsForm form) {
		
		form.setStudent(new Student());
		
		String[] selectedResults = {};
		form.setSelectedResults(selectedResults) ;
		form.setResults(new ArrayList<LabelValueBean>()) ;
		form.setTitle(null) ;
		form.setSupervisors(new ArrayList<Staff> ()) ;
		
		String[] selectedReviewOrFinalValue = {};
		form.setSelectedReviewOrFinalValue(selectedReviewOrFinalValue) ;
		form.setReviewOrFinalValues(new ArrayList<LabelValueBean> ());
		form.setSignOffList(new ArrayList<Staff> ()); 
		form.setRoutingList(new ArrayList<Staff> ());
		form.setComment(null);
		form.setFirstSignOff(false);
		form.setCanSignOff(true);
		form.setFinaloption(null);
		form.setReviewoption(null);
		form.setPrevSignOffRecord(new Staff()) ;
	}
	
	/**
	 * Update the current values of the checkboxes.
	 * The form cannot seem to handle the update from the web page hence a 
	 *  manual update is required.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	private void updateCheckBoxValues(ActionMapping mapping, 
									 ActionForm form,
									 HttpServletRequest request, 
									 HttpServletResponse response)
	{		
		MdRPMResultsForm mdForm = (MdRPMResultsForm) form;
		String [] selectedReviewOrFinal = {};
		
		if (request.getParameterValues("selectedReviewOrFinalValue") != null)
		{
			selectedReviewOrFinal = request.getParameterValues("selectedReviewOrFinalValue");
		}
		//WARN:Struts 1 doesn't handle checkboxes and forms well. 
		//      For updated results use request.getparameter method and updated the form
		mdForm.setFinaloption(request.getParameter("finaloption"));
		mdForm.setReviewoption(request.getParameter("reviewoption"));
		mdForm.setSelectedReviewOrFinalValue(selectedReviewOrFinal);
	}
	
    /**
     * Send email to the next recipient on the routing list
     * @param dao
     * 			Data access object
     * @param mdForm
     * 			Data form
     * @throws Exception
     */
    public void sendEmail(MdRPMResultsForm mdForm, String recipientEmail) throws Exception
    {
    	Staff loggedInUser = mdForm.getLoggedInUser();
    	//Staff nextStaff = getNextRoutingRecipient(routingList, loggedInUser.getFinalFlag());
    	//, ArrayList<Staff> routingList 
		
    	Student student = mdForm.getStudent();
    	
    	emailService = (EmailService) ComponentManager.get(EmailService.class);
    	
		String emailFrom = loggedInUser.getEmailAddress(); 
		String emailTo = recipientEmail;
		
    	//do not send email on local machine, dev & qa -default email to tnonyack@unisa.ac.za(Developer)
		String serverpath = ServerConfigurationService.getServerUrl();
		if (serverpath.contains("mydev") || serverpath.contains("myqa") || serverpath.contains("localhost")){
			emailTo = loggedInUser.getEmailAddress();//"tnonyack@unisa.ac.za";
		}
		else{    	
			emailFrom = loggedInUser.getEmailAddress(); 
			emailTo = recipientEmail;
		}

		String subject = "Research Proposal Module Results for " + student.getStudentNumber() + " "+ student.getName();
		String body = null;
		
		List<String> contentlist = new ArrayList<String>();
		contentlist.add("Content-Type: text/html");
		try{
			body = constructEmailBody(mdForm);
			InternetAddress toEmail = new InternetAddress(emailTo);
			InternetAddress iaTo[] = new InternetAddress[1];
			iaTo[0] = toEmail;
			InternetAddress iaHeaderTo[] = new InternetAddress[1];
			iaHeaderTo[0] = toEmail;
			InternetAddress iaReplyTo[] = new InternetAddress[1];
			iaReplyTo[0] = new InternetAddress(emailFrom);
			emailService.sendMail(iaReplyTo[0],iaTo,subject,body,iaHeaderTo,iaReplyTo,contentlist);

		} catch (Exception ex){
			
			throw new Exception ("MdRPMResultsAction(sendEmail) /" + ex.getMessage(), ex);
		}
    }
    
    /**
     * Returns the next staff on the routing list to refer the application to.
     * 
     * @param routingList
     * 			The Staff routing list	
     * @param currentFinalFlag
     * 			The logged in user finalFlag
     * @return Staff
     * 		Next staff to refer the application to 
     */
    private Staff getNextRoutingRecipient(ArrayList<Staff> routingList, String currentFinalFlag)
    {   
    	String nextFinalFlag = getNextFinalFlagValue(routingList, currentFinalFlag);
    	
    	Staff nextStaff = null;
    	for (Staff staff: routingList)
    	{
    		if (staff.getFinalFlag().equals(nextFinalFlag))
    		{    			
    			nextStaff = staff;    			
    			break;
    		}
    	}    
    	
    	//To accommodate the scenario where qsprout is not configured properly
    	//Fix this 
    	if (nextStaff == null  && isFinalSignOff(nextFinalFlag , routingList))
    	{
        	for (Staff staff: routingList)
        	{
        		if (staff.getFinalFlag().equals(Constants.QSPROUT_FINAL_FLAG_F))
        		{    			
        			nextStaff = staff;    			
        			break;
        		}
        	} 
    	}
    	return nextStaff;
    }
    
    /**
     * Construct email body from the referral-email.vm template
     * 
     * @param mdForm
     * 			MdAdmissionForm data
     * @return
     * 		HTML string
     * @throws ServletException
     */
    private String constructEmailBody(MdRPMResultsForm mdForm) throws ServletException
    {
        try
        {
    		Properties properties = new Properties();
    		properties.setProperty(
    				Constants.RESOURCE_LOADER, 
    				Constants.RESOURCE_LOADER_NAME);
    		properties.setProperty(
    				Constants.RESOURCE_LOADER_CLASSPATH_CLASS,
    				ClasspathResourceLoader.class.getName());	
    		properties.setProperty( 
    				RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS, 
    				Constants.RUNTIME_LOG_LOGSYSTEM_CLASS_VALUE );
    		properties.setProperty(
    				Constants.RUNTIME_LOG_LOGGER_LOGSYSTEM_LOG4J_LOGGER,
    				Constants.RUNTIME_LOG_LOGGER_LOGSYSTEM_LOG4J_LOGGER_NAME);
    		
    		// null logger that will prevent velocity.log from being generated
    		//properties.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS,
    		//NullLogChute.class.getName());

            VelocityEngine ve = new VelocityEngine(properties);
        	ve.init();

	        Qualification qualification = mdForm.getStudent().getQualification();
	        
	        Map<String, String> params = new HashMap<String, String>();
	        params.put(Student.STUDENT_NUMBER, mdForm.getStudent().getStudentNumber());
	        params.put(Student.STUDENT_NAME, mdForm.getStudent().getName());
			params.put(Qualification.QUAL_CODE, qualification.getQualCode());
	        params.put(Qualification.QUAL_DESC, qualification.getQualDesc());
	        params.put(Staff.STAFF_NAME, mdForm.getLoggedInUser().getName());
	        
	        Template t = ve.getTemplate(REFERRAL_EMAIL_PATH);
	        VelocityContext context = new VelocityContext(params);
	        StringWriter writer = new StringWriter();
	        t.merge(context, writer);
	
	        return writer.toString();

		}
		catch (Exception ex)
		{
			throw new ServletException("MdRPMResultsAction(constructEmailBody) /" + ex.getMessage(), ex);
		}
    }
    
    /**
     * Checks if it is the final routing flag <F>
     * 
     * @param finalFlag
     * 			Routing flag
     * @return
     * 		true/false
     */
    private boolean isFinalSignOff(String finalFlag, ArrayList<Staff> routingList)
    {
    	if (Constants.QSPROUT_FINAL_FLAG_F.equals(finalFlag) ||
    		Constants.QSPROUT_FINAL_FLAG_F.equals(
    				getNextFinalFlagValue(routingList, finalFlag)))
    	{
    		return true;
    	}
    	return false;
    } 
    
    /**
     * Retrieves the next final flag value based on the current logged in user's 
     * final flag value 
     * @param routingList
     * 			The Staff routing list	
     * @param currentFinalFlag
     * 			The logged in user final flag value
     * @return
     * 		Next final flag value
     */
    private String getNextFinalFlagValue(ArrayList<Staff> routingList, String currentFinalFlag)
    {    	
    	int val = -1;
    	
		try{
			val = Integer.parseInt(currentFinalFlag);
			val +=1;
			
			if (val > routingList.size())
			{
				return Constants.QSPROUT_FINAL_FLAG_F;
			}
		} catch (NumberFormatException e) {
			return Constants.QSPROUT_FINAL_FLAG_F;
		}			
		
		return String.valueOf(val);
    }

}
