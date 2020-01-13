package za.ac.unisa.lms.tools.mylife.actions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.naming.OperationNotSupportedException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import Saaal40j.Abean.Saaal40jGetStudentCourses;
import Srcds01h.Abean.Srcds01sMntStudContactDetail;
import Srsms01h.Abean.Srsms01sSendSingleSms;

import za.ac.unisa.lms.constants.EventTrackingTypes;
import za.ac.unisa.lms.dao.MyUnisaJoinDAO;

import za.ac.unisa.lms.dao.IdvaltStudentDetailsDAO;
import za.ac.unisa.lms.dao.IdvaltStudentDetails;
import za.ac.unisa.lms.dao.IdvaltRowMapper;
import za.ac.unisa.lms.dao.JoinActivationDAO;
import za.ac.unisa.lms.dao.JoinActivationStuDetails;
import za.ac.unisa.lms.dao.JoinActivationRowMapper;

/*import za.ac.unisa.lms.providers.api.IdVaultProvisioner;
import za.ac.unisa.lms.providers.api.IdVaultUser;*/
import za.ac.unisa.lms.ad.SaveStudentToAD;
import za.ac.unisa.lms.ad.IdVaultUser;
import za.ac.unisa.lms.tools.join.dao.MyUnisaJoinQueryDAO;
import za.ac.unisa.lms.tools.mylife.dao.MyLifesakaiDAO;
import za.ac.unisa.lms.tools.mylife.dao.MyUnisaMylifeStudentDAO;
import za.ac.unisa.lms.tools.mylife.forms.MyUnisaMylifeForm;


public class MyUnisaMylifeAction extends LookupDispatchAction {
	
	private EventTrackingService eventTrackingService;
	private UsageSessionService usageSessionService;
	private ToolManager toolManager;
	private SessionManager sessionManager;
	private EmailService emailService;
	//the code for removing white space problem when you use ENTER button instead of mouse .
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (request.getParameter("action") == null) return nextStep(mapping, form, request, response);
		return super.execute(mapping, form, request, response);		
	}
	Logger log = LoggerFactory.getLogger(MyUnisaMylifeAction.class);
	

	/**
	 * Method: getKeyMethodMap
	 */
		protected Map getKeyMethodMap() {
	      Map map = new HashMap();
	      map.put("studnostep", "studnostep");
	      map.put("button.continue", "nextStep");
	      map.put("button.acknowledge", "nextStep");
	      map.put("button.clear", "clear");
	      map.put("button.back", "back");	     
	    
	      return map;
	}
		
	/**
	 * Method studnostep
	 * 	Forward to the jsp for student to enter his student number.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward studnostep(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {
		
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
		toolManager=(ToolManager) ComponentManager.get(ToolManager.class);
		
		MyUnisaMylifeForm myUnisaMylifeForm = (MyUnisaMylifeForm) form;
		log.info(this+": "+myUnisaMylifeForm.getStudentNr()+" student nr step");	
		eventTrackingService.post(
				eventTrackingService.newEvent(EventTrackingTypes.EVENT_UNISALOGIN_VIEW, toolManager.getCurrentPlacement().getContext(), false));
 
		return mapping.findForward("studnostep");
	}
	
	/**
	 * Method nextStep
	 * 		to decide what is the next step of the mylife to go to
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward nextStep(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)throws Exception {
		
		MyUnisaMylifeForm myUnisaMylifeForm = (MyUnisaMylifeForm) form;
		String gotoStepValidate = "";
		if ("stnostep".equalsIgnoreCase(request.getParameter("atstep"))) {
			gotoStepValidate = stepStnoValidate(mapping,form,request,response);
		}
		else if ("persstep".equalsIgnoreCase(request.getParameter("atstep"))){
			myUnisaMylifeForm.setStudentNr(myUnisaMylifeForm.getStudentNr());
     			gotoStepValidate = stepPersValidate(mapping,form,request, response);
		}else if("stepUnisaAcknowledge".equalsIgnoreCase(request.getParameter("atstep"))){
			gotoStepValidate = stepUnisaAcknowledge(mapping,form,request, response);
		
		}
		return mapping.findForward(gotoStepValidate);
	}
	
	/**
	 * Method stepStnoValidate
	 * 	validate if student email account was created.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public String stepStnoValidate(
		ActionMapping mapping,	
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) throws Exception {

		// reference the form
	    ActionMessages messages = new ActionMessages(); // class that encapsulates messages.		
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
		toolManager = (ToolManager) ComponentManager.get(ToolManager.class);
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
		UsageSession usageSession = usageSessionService.getSession();	
		
		MyUnisaMylifeStudentDAO dao = new MyUnisaMylifeStudentDAO();
		JoinActivationDAO joinActivationDao = new JoinActivationDAO();
		MyUnisaMylifeForm myUnisaMylifeForm = (MyUnisaMylifeForm) form;
		
		String tempstudentNr=myUnisaMylifeForm.getStudentNr();
		String regex = "^0*";
		String studentNr=tempstudentNr.replaceAll(regex, "");
		myUnisaMylifeForm.setStudentNr(studentNr);
		    		
		
	    String helpdeskEmail = ServerConfigurationService.getString("helpdeskEmail");
	    //Validate Student number -Student number must be entered 
	    if(myUnisaMylifeForm.getStudentNr().length()==0){
	    	messages.add(ActionMessages.GLOBAL_MESSAGE,
			        new ActionMessage("message.generalmessage", "Please enter the student number"));
			addErrors(request, messages);	
			return ("studnostep");
	    }
	    //Student number must be numeric
    	try {
    		int x = Integer.parseInt(myUnisaMylifeForm.getStudentNr());
	    }catch(NumberFormatException nFE) {
	    	log.error(this+": "+myUnisaMylifeForm.getStudentNr()+" is not numeric.");
			messages.add(ActionMessages.GLOBAL_MESSAGE,
			        new ActionMessage("message.generalmessage", "Student number must be numeric"));
			addErrors(request, messages);	
			return ("studnostep");
	    }
	    //Validate student number against data base
	    boolean studentExist = dao.getStudentExist(myUnisaMylifeForm.getStudentNr());
	    if (studentExist == false){
	    	log.error(this+": "+myUnisaMylifeForm.getStudentNr()+" is not valid.");
			messages.add(ActionMessages.GLOBAL_MESSAGE,
			        new ActionMessage("message.generalmessage", "Invalid student number"));
			addErrors(request, messages);	
			return ("studnostep");
	    }
	        
		//Validate duplicate student number
		String newStudentNr = ""; // if there was duplicate student number this is correct student nr
		String oldStudentNr = ""; // if there was duplicate student nr, keep incorrect one for error message
		myUnisaMylifeForm.setStudentNr(myUnisaMylifeForm.getStudentNr());

	    newStudentNr = dao.getDuplicateStudent(myUnisaMylifeForm.getStudentNr());
	    if ((newStudentNr.length() > 0) &&(!newStudentNr.equals(myUnisaMylifeForm.getStudentNr()))) {
	    	oldStudentNr = myUnisaMylifeForm.getStudentNr();	    	
	    	myUnisaMylifeForm.setStudentNr(newStudentNr);
	    }	 
		if (oldStudentNr.length()> 0) {    	
		    	
		    	messages.add(ActionMessages.GLOBAL_MESSAGE,
				        new ActionMessage("message.generalmessage", "The student number you entered is not correct.  " +
				        		"We found a duplicate record and the correct student number you must use is: " + newStudentNr+
				        		" Please try again with the correct student number."));
				addErrors(request, messages);

				eventTrackingService.post(
						eventTrackingService.newEvent(EventTrackingTypes.EVENT_UNISALOGIN_ERROR, "Duplicate student (old stno: "+oldStudentNr+", new stno: "+newStudentNr+")", false), usageSession);

				myUnisaMylifeForm.setStudentNr(oldStudentNr);
				return ("persstep");		    	
		} // end of if (oldStudentNr.length()>0) {
	    
		  /*
		 * *********************************************************************
		 *  Validate - is current Unisa student (mylife only applicable from 2009 and more)
		 * *********************************************************************
		 */
	    Calendar calendar = Calendar.getInstance();
		Integer currentYear = new Integer(calendar.get(Calendar.YEAR));
		int lastAcadYear = dao.getStudentLastAcadYear(myUnisaMylifeForm.getStudentNr());
		if(lastAcadYear==0){
			 messages.add(ActionMessages.GLOBAL_MESSAGE,
				        new ActionMessage("message.generalmessage", "Please note newly registered Unisa students will only be able to claim their myUnisa and myLife accounts once the registration for the current academic year was successfully processed. Ensure that the minimum payment was received by UNISA, and confirmation thereof has been sent to you."));
				addErrors(request, messages);	
				eventTrackingService.post(
						eventTrackingService.newEvent(EventTrackingTypes.EVENT_UNISALOGIN_ERROR, "Last academic year is Zero", false), usageSession);

				return ("studnostep"); 
		}
		else if (lastAcadYear < 2009) {
			 messages.add(ActionMessages.GLOBAL_MESSAGE,
				        new ActionMessage("message.generalmessage", "UNISA logins for students registered prior to 2008 will only become available once your registration for the current academic year has been successfully processed and you received confirmation of registered modules."));
				addErrors(request, messages);	
				eventTrackingService.post(
						eventTrackingService.newEvent(EventTrackingTypes.EVENT_UNISALOGIN_ERROR, "Last academic year is less than 2009", false), usageSession);

				return ("studnostep"); 
		}

		/*
		 * *********************************************************************
		 * Validate that student have active courses
		 * *********************************************************************
		 */
		
		String courses = "";
		Srcds01sMntStudContactDetail op = new Srcds01sMntStudContactDetail();
		operListener opl = new operListener();
		op.addExceptionListener(opl);
		op.clear();
		Saaal40jGetStudentCourses op2 = new Saaal40jGetStudentCourses();
		operListener op3 = new operListener();
		op2.addExceptionListener(op3);
		op2.clear();
		op2.setAsStringInWsStudentAnnualRecordMkStudentNr(myUnisaMylifeForm
				.getStudentNr());
		op2.execute();
		if (opl.getException() != null)
			throw opl.getException();
		if (op2.getExitStateType() < 3)
			throw new Exception(op.getExitStateMsg());

		for (int i = 0; i < op2.getOutStusunCount(); i++) {
			if (!courses.equals(""))
				courses += "|";
			String course = op2.getOutGWsStudentStudyUnitMkStudyUnitCode(i);
			courses += course;
		}
		boolean mayCont = true;
		if (op2.getOutWsMethodResultReturnCode().equalsIgnoreCase("RG")) {
			mayCont = true;
		} else {
			mayCont = false;
		}

		// if courses is empty student have no valid courses
		if (courses.equals("") && (mayCont == false)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
									"You are not currently registered for any courses. You will not be able to Claim UNISA Login"));
			addErrors(request, messages);
			log.info(this+": "+myUnisaMylifeForm.getStudentNr()+" has no active courses.");
			eventTrackingService.post(eventTrackingService.newEvent(
					EventTrackingTypes.EVENT_UNISALOGIN_ERROR,
					"Not registered for any courses", false), usageSession);

			return ("studnostep");
		}
				
		// Get student join_activation details
		JoinActivationStuDetails joinActRecord = new JoinActivationStuDetails();
	    joinActRecord = joinActivationDao.getStudentDetails(myUnisaMylifeForm.getStudentNr());
	    // validate if student may claim Unisa Login
	    /* Sonette MyUnisaJoinDAO db1 = new MyUnisaJoinDAO();
	    String studentStatus = db1.studentStatusFlag(myUnisaMylifeForm.getStudentNr());*/
	    if (joinActRecord.getStatus_flag().equals("BL")) {
	    	log.info(this+": "+myUnisaMylifeForm.getStudentNr()+" is blocked.");
	    	messages.add(ActionMessages.GLOBAL_MESSAGE,
			        new ActionMessage("message.generalmessage","Claiming UNISA Login is not allowed for this student number."));
			addErrors(request, messages);
    	   	eventTrackingService.post(
    	   			eventTrackingService.newEvent(EventTrackingTypes.EVENT_UNISALOGIN_ERROR, "Student is blocked", false), usageSession);
		addErrors(request, messages);
			return ("studnostep");
	    }	
	    
	    /*
		 * *********************************************************************
		 *  Validate if myLife email account and password was generated in IDVALT
		 * *********************************************************************
		 
		try {
			dao.getStudentEmail(myUnisaMylifeForm);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if ((null == myUnisaMylifeForm.getEmail())||(myUnisaMylifeForm.getEmail().equals(" "))||(myUnisaMylifeForm.getEmail().equals(""))||(myUnisaMylifeForm.getMyLifePwd().equals(" "))||(myUnisaMylifeForm.getMyLifePwd().equals(""))) {
			log.debug(this+": "+myUnisaMylifeForm.getStudentNr()+" myLife email and pwd not ready.");
			messages.add(ActionMessages.GLOBAL_MESSAGE,
			        new ActionMessage("message.generalmessage", "Unfortunately your UNISA account has not been created yet; you will not be able to complete your UNISA Login. Please try again in 48 hours."));
			addErrors(request, messages);	
			eventTrackingService.post(eventTrackingService.newEvent(
					EventTrackingTypes.EVENT_UNISALOGIN_ERROR,
					"myLife email account not created", false), usageSession);
			return ("studnostep");
		}*/
		
		/*  
		 * Already  already claimed Unisa Login
		*/
	    /* Sonette JoinActivationStuDetails joinActRecord = new JoinActivationStuDetails();
	    joinActRecord = joinActivationDao.getStudentDetails(myUnisaMylifeForm.getStudentNr());*/
	    boolean myLifeClaimed = false;
	    if (null != joinActRecord) {
	    	if (joinActRecord.getAct_status().equals("Y") &&
	    	joinActRecord.getMylife_act_status().equals("Y")) {
	    		myLifeClaimed = true;
			} else {
				myLifeClaimed = false;
	    	}
	    	
	    	if (myLifeClaimed == true) {
		    	messages.add(ActionMessages.GLOBAL_MESSAGE,
				        new ActionMessage("message.generalmessage", "The initial UNISA login for this student number has already been claimed. If you cannot remember your password, please use the Forgotten UNISA Password feature to obtain a new password. "));
		    	   	eventTrackingService.post(
		    	   			eventTrackingService.newEvent(EventTrackingTypes.EVENT_UNISALOGIN_ERROR, "Already claimed Unisa Login", false), usageSession);
				addErrors(request, messages);
				return ("studnostep");
		    }
	    }
		
	    eventTrackingService.post(
		    		eventTrackingService.newEvent(EventTrackingTypes.EVENT_UNISALOGIN_STEP1, toolManager.getCurrentPlacement().getContext(), false), usageSession);
		return ("persstep");
	}
	
	/**
	 * Method stepPersValidate
	 * 	validations for the personal details step of the mylife.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public String stepPersValidate(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) throws Exception {

		ActionMessages messages = new ActionMessages(); 
		MyUnisaMylifeForm myUnisaMylifeForm = (MyUnisaMylifeForm) form;
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
		UsageSession usageSession = usageSessionService.startSession(myUnisaMylifeForm.getStudentNr(), request.getRemoteAddr(), request.getHeader("user-agent"));
	    request.getSession().setAttribute("UsageSession", usageSession);
	    String helpdeskEmail = ServerConfigurationService.getString("helpdeskEmail");

		/**
		 * Call validate.xml to validate the input
		 */
		messages = (ActionMessages) myUnisaMylifeForm.validate(mapping, request);
		if (!messages.isEmpty()) {
			addErrors(request, messages);
			return ("persstep");
		} else {// end if (!messages.isEmpty())

		    /*
			 * *********************************************************************
			 *  Validate that id number  or passport number was entered
			 * *********************************************************************
			 */
			String enteredIdnr = "";
			if (myUnisaMylifeForm.getId_nr().length() > 0) {
				enteredIdnr = ltrim(myUnisaMylifeForm.getId_nr());
				enteredIdnr = rtrim(enteredIdnr);
			}
			String enteredPassport = "";
			if (myUnisaMylifeForm.getPassport_nr().length() > 0) {
				enteredPassport = ltrim(myUnisaMylifeForm.getPassport_nr());
				enteredPassport = rtrim(enteredPassport);
			}
		    if (((null == enteredIdnr)||(enteredIdnr.length()==0))&&
		    		((null==enteredPassport)||(enteredPassport.length()==0))) {
		    	messages.add(ActionMessages.GLOBAL_MESSAGE,
				        new ActionMessage("message.generalmessage","Id number or passport number must be entered please. "));
				addErrors(request, messages);
				return ("persstep");
		    }		    		

			Srcds01sMntStudContactDetail op = new Srcds01sMntStudContactDetail();
		    operListener opl = new operListener();
		    op.addExceptionListener(opl);
		    op.clear();

		    op.setInSecurityWsUserNumber(9999);
		    op.setInCsfClientServerCommunicationsClientVersionNumber((short) 3);
		    op.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
		    op.setInCsfClientServerCommunicationsAction("D");
		    op.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
			op.setInWsStudentNr(Integer.parseInt(myUnisaMylifeForm.getStudentNr()));
		    op.execute();
		   

		    String Errormsg = op.getOutCsfStringsString500();
		    if ((Errormsg != null) && (!Errormsg.equals(""))) {
		       	messages.add(ActionMessages.GLOBAL_MESSAGE,
		    	new ActionMessage("error.coolgenerror", Errormsg));
		       	addErrors(request, messages);
			 
		       	return ("persstep");
		    }

		    //tmp variables to select students Personal information from student database
		    String tmpSurname = "";
		    String tmpFullNames = "";
		    String tmpDateOfBirth = "";
		    String tmpIdnr = "";
		    String tmpPassport = "";

		    // set the birth date
		    myUnisaMylifeForm.setDateOfBirth(myUnisaMylifeForm.getBirthYear()+"-"+myUnisaMylifeForm.getBirthMonth()+"-"+myUnisaMylifeForm.getBirthDay());

		    tmpSurname = ltrim(op.getOutWsStudentSurname());
		    tmpSurname = rtrim(tmpSurname);
		    tmpFullNames = ltrim(op.getOutWsStudentFirstNames());
		    tmpFullNames = rtrim(tmpFullNames);
		    tmpIdnr = ltrim(op.getOutWsStudentIdentityNr());
		    tmpIdnr = rtrim(tmpIdnr);
		    tmpPassport = ltrim(op.getOutWsStudentPassportNo());
		    tmpPassport = rtrim(tmpPassport);
		    DateFormat strDate = new SimpleDateFormat( "yyyy-MM-dd" );
		    tmpDateOfBirth = strDate.format(op.getOutWsStudentBirthDate().getTime());
		 	String enteredSurname = ltrim(myUnisaMylifeForm.getSurname());
			enteredSurname = rtrim(enteredSurname);
			String enteredFullNames = ltrim(myUnisaMylifeForm.getFullNames());
			enteredFullNames = rtrim(enteredFullNames);

			eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);

		    if (!enteredSurname.equalsIgnoreCase(tmpSurname)) {
		    	messages.add(ActionMessages.GLOBAL_MESSAGE,
				        new ActionMessage("message.generalmessage", "The surname you entered does not correspond with our records. Please check and try again." +
				        		" If you are convinced you have entered the correct information, please contact the <A HREF=\"mailto:"+helpdeskEmail+"\">myUnisaHelp@unisa.ac.za</A> to have your student records checked."));
				addErrors(request, messages);
				log.info(this+": "+myUnisaMylifeForm.getStudentNr()+" surname not matches");			
				eventTrackingService.post(
						eventTrackingService.newEvent(EventTrackingTypes.EVENT_UNISALOGIN_ERROR, "Surname doesn't match (User: "+enteredSurname.toUpperCase()+", System: "+tmpSurname+")", false), usageSession);

				return ("persstep");
		    }
		    if (!enteredFullNames.equalsIgnoreCase(tmpFullNames)) {
		    	messages.add(ActionMessages.GLOBAL_MESSAGE,
				        new ActionMessage("message.generalmessage", "The full names you entered do not correspond with our records. Please check and try again." +
				        		" If you are convinced you have entered the correct information, please contact the <A HREF=\"mailto:"+helpdeskEmail+"\">myUnisaHelp@unisa.ac.za</A> to have your student records checked."));
				addErrors(request, messages);
				
				eventTrackingService.post(
						eventTrackingService.newEvent(EventTrackingTypes.EVENT_UNISALOGIN_ERROR, "Full names don't match (User: "+enteredFullNames.toUpperCase()+", System: "+tmpFullNames+")", false), usageSession);
				
				return ("persstep");
		    }
		    if (!myUnisaMylifeForm.getDateOfBirth().equalsIgnoreCase(tmpDateOfBirth)) {
		    	messages.add(ActionMessages.GLOBAL_MESSAGE,
				        new ActionMessage("message.generalmessage", "The date of birth you entered does not correspond with our records. Please check and try again." +
				        		" If you are convinced you have entered the correct information, please contact the <A HREF=\"mailto:"+helpdeskEmail+"\">myUnisaHelp@unisa.ac.za</A> to have your student records checked."));
				addErrors(request, messages);

				log.info(this+": "+myUnisaMylifeForm.getStudentNr()+" date of birth not matches");
				eventTrackingService.post(
						eventTrackingService.newEvent(EventTrackingTypes.EVENT_UNISALOGIN_ERROR, "Date of birth doesn't match (User: "+myUnisaMylifeForm.getDateOfBirth()+", System: "+tmpDateOfBirth+")", false), usageSession);
				return ("persstep");
		    }
		    
		    if ((enteredIdnr.length()>0) && (enteredPassport.length()>0)) {
		    	messages.add(ActionMessages.GLOBAL_MESSAGE,
				        new ActionMessage("message.generalmessage","Enter Id number or passport number, But not the both "));
				addErrors(request, messages);
				return ("persstep");
		    }	
		    if ((enteredIdnr.length()>0)&&(!enteredIdnr.equalsIgnoreCase(tmpIdnr))) {
		    	messages.add(ActionMessages.GLOBAL_MESSAGE,
				        new ActionMessage("message.generalmessage", "The Id number you entered does not correspond with our records. Please check and try again." +
				        		" If you are convinced you have entered the correct information, please contact the <A HREF=\"mailto:"+helpdeskEmail+"\">myUnisaHelp@unisa.ac.za</A> to have your student records checked."));
				addErrors(request, messages);
				
				eventTrackingService.post(
						eventTrackingService.newEvent(EventTrackingTypes.EVENT_UNISALOGIN_ERROR, "Id number doesn't match (User: "+enteredIdnr.toUpperCase()+", System: "+tmpIdnr+")", false), usageSession);
				return ("persstep");
		    }
		    if ((enteredPassport.length()>0)&&(!enteredPassport.equalsIgnoreCase(tmpPassport))) {
		    	messages.add(ActionMessages.GLOBAL_MESSAGE,
				        new ActionMessage("message.generalmessage", "The passport number you entered does not correspond with our records. Please check and try again." +
				        		" If you are convinced you have entered the correct information, please contact the <A HREF=\"mailto:"+helpdeskEmail+"\">myUnisaHelp@unisa.ac.za</A> to have your student records checked."));
				addErrors(request, messages);
    
				eventTrackingService.post(
						eventTrackingService.newEvent(EventTrackingTypes.EVENT_UNISALOGIN_ERROR, "Passport number doesn't match (User: "+enteredPassport.toUpperCase()+", System: "+tmpPassport+")", false), usageSession);
				return ("persstep");
		 
			}
			
		    toolManager=(ToolManager) ComponentManager.get(ToolManager.class);
		    eventTrackingService.post(
		    		eventTrackingService.newEvent(EventTrackingTypes.EVENT_UNISALOGIN_STEP2, toolManager.getCurrentPlacement().getContext(), false), usageSession);
		
			return ("mylifeaccept");
		}
	}
	
	public String stepUnisaAcknowledge(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		ActionMessages messages = new ActionMessages(); 
		MyUnisaMylifeForm myUnisaMylifeForm = (MyUnisaMylifeForm) form;
		
		MyUnisaMylifeStudentDAO dao = new MyUnisaMylifeStudentDAO();
		MyLifesakaiDAO db_sakai = new MyLifesakaiDAO(); // connect to sakai database
		IdvaltStudentDetailsDAO idvaltDAO = new IdvaltStudentDetailsDAO();
		JoinActivationDAO joinActDAO = new JoinActivationDAO();
		
		// create idvalt student details object
		IdvaltStudentDetails idvaltStudentDetails = new IdvaltStudentDetails();
		JoinActivationStuDetails joinDetails = new JoinActivationStuDetails();
		boolean idvaltExists = false;
		boolean joinActivationExist = false;
			
		usageSessionService=(UsageSessionService) ComponentManager.get(UsageSessionService.class);
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
		UsageSession usageSession = usageSessionService.startSession(myUnisaMylifeForm.getStudentNr(), request.getRemoteAddr(), request.getHeader("user-agent"));
	    request.getSession().setAttribute("UsageSession", usageSession);
	    String helpdeskEmail = ServerConfigurationService.getString("helpdeskEmail");
		
		if (myUnisaMylifeForm.isAgreeCheck1()&&myUnisaMylifeForm.isAgreeCheck2()&&
				myUnisaMylifeForm.isAgreeCheck3()&&myUnisaMylifeForm.isAgreeCheck4()){
			
			myUnisaMylifeForm.setCellNr(dao.getCellNr(myUnisaMylifeForm));			
			myUnisaMylifeForm.setMylifePath(ServerConfigurationService.getString("mylife.path")); 
			myUnisaMylifeForm.setMyUnisaPath(ServerConfigurationService.getString("serverUrl"));
			
			idvaltStudentDetails = idvaltDAO.getStudentDetails(myUnisaMylifeForm.getStudentNr());
			if (idvaltStudentDetails.getStudentNr().equals("")) {
				idvaltStudentDetails.setStudentNr(myUnisaMylifeForm.getStudentNr());
				idvaltExists=false;
			} else {
				idvaltExists=true;
			}
			
			// check email
			if (idvaltStudentDetails.getExchangemail().equals("")) {
				idvaltStudentDetails.setExchangemail(myUnisaMylifeForm.getStudentNr()+"@mylife.unisa.ac.za");
				myUnisaMylifeForm.setEmail(idvaltStudentDetails.getExchangemail());
			} else {
				myUnisaMylifeForm.setEmail(idvaltStudentDetails.getExchangemail());
			}
			
			// check password (password is for both myUnisa & myLife)
			if (idvaltStudentDetails.getPassword().equals("")) {
				myUnisaMylifeForm.setMyLifePwd(randomPassword());
				idvaltStudentDetails.setPassword(myUnisaMylifeForm.getMyLifePwd());
			} else {
				myUnisaMylifeForm.setMyLifePwd(idvaltStudentDetails.getPassword());
			}
			 dao.updateADRPH(myUnisaMylifeForm.getStudentNr(), myUnisaMylifeForm.getEmail());
			 dao.updateIdvalt(myUnisaMylifeForm.getStudentNr(), myUnisaMylifeForm.getEmail(),myUnisaMylifeForm.getMyLifePwd());
		 
	         
	         // store password on idVault - This password save will also updates the myLife email account 				
				//IdVaultProvisioner idVaultProvisioner = (IdVaultProvisioner) ComponentManager.get("org.sakaiproject.user.api.UserDirectoryProvider");
				IdVaultUser idVaultuser = new IdVaultUser();
				// idVault user properties.
				idVaultuser.setId(myUnisaMylifeForm.getStudentNr());
				idVaultuser.setFirstName(myUnisaMylifeForm.getFullNames());
				idVaultuser.setLastName(myUnisaMylifeForm.getSurname());
				idVaultuser.setEmail(myUnisaMylifeForm.getEmail());
				idVaultuser.setDisplayName(myUnisaMylifeForm.getStudentNr());
				idVaultuser.setDn("CN="+myUnisaMylifeForm.getStudentNr()+","+ServerConfigurationService.getString("basePath@za.ac.unisa.lms.providers.user.ad.ADDirectoryProvider"));
				log.info("DN set as "+idVaultuser.getDn());
				idVaultuser.setType("student");
				idVaultuser.setPassword(myUnisaMylifeForm.getMyLifePwd());
				
 
				try {
					SaveStudentToAD saveStudentToAD = new SaveStudentToAD();
					if(!saveStudentToAD.saveUser(idVaultuser)) {
						log.error("action: MyUnisaMylifeAction method=stepUnisaAcknowledge: student="+myUnisaMylifeForm.getStudentNr()+" was not saved");
						messages.add(ActionMessages.GLOBAL_MESSAGE,
						        new ActionMessage("message.generalmessage", "Unfortunately your claiming UNISA Login was unsuccessful, please try again."));
						addErrors(request, messages);
						log.info(this+": "+myUnisaMylifeForm.getStudentNr()+" save to AD failed during claim");
						eventTrackingService.post(
								eventTrackingService.newEvent(EventTrackingTypes.EVENT_JOIN_ERROR, "Save to provider failed", false), usageSession);
						return ("studnostep");
					}
			
				} catch (OperationNotSupportedException e) {
					log.error("action: MyUnisaMylifeAction method=stepUnisaAcknowledge: Failed to Save the student to AD (OperationNotSupportedException) student="+myUnisaMylifeForm.getStudentNr()+" e="+e);
					messages.add(ActionMessages.GLOBAL_MESSAGE,
					        new ActionMessage("message.generalmessage", "Unfortunately your claiming UNISA Login was unsuccessful, please try again."));
					addErrors(request, messages);
					eventTrackingService.post(
							eventTrackingService.newEvent(EventTrackingTypes.EVENT_JOIN_ERROR, "Save to provider failed: "+e, false), usageSession);
					return ("studnostep");
				} catch (Exception e) {
					log.error(this+"action: MyUnisaMylifeAction method=stepUnisaAcknowledge:Failed to Save the student to AD Unhandled exception ("+e+"): "+e.getMessage());
					messages.add(ActionMessages.GLOBAL_MESSAGE,
					        new ActionMessage("message.generalmessage", "Unfortunately your claiming UNISA Login was unsuccessful, please try again."));
					addErrors(request, messages);
					eventTrackingService.post(
							eventTrackingService.newEvent(EventTrackingTypes.EVENT_JOIN_ERROR, "Save to AD failed: Unknown error: "+e, false), usageSession);
					return ("studnostep");
				}
				
				
	         // update stuann (set sol_user_flag = 'Y')
	         dao.updateSTUANN(myUnisaMylifeForm.getStudentNr());
	         
	         // insert student nr ans sysdate into librec table
	         dao.insertLibrec(myUnisaMylifeForm.getStudentNr());
	         
	         //insert default email options into EMLOPT 
	         dao.insertEMLOPT(myUnisaMylifeForm.getStudentNr(),"Y","N","N");
	         
	 	
			joinDetails = joinActDAO.getStudentDetails(myUnisaMylifeForm.getStudentNr());
			if (joinDetails.getStudentNr().equals("")) {
				joinActivationExist=false;
			} else {
				joinActivationExist=true;
			}
			db_sakai.updateJoinActivation(myUnisaMylifeForm.getStudentNr(),"OT","0","Y","S","Y",joinActivationExist);
			
			//send mylife password to student cell
			
			if (myUnisaMylifeForm.getCellNr().length()>= 1) { 
		        	// proxy for single sms
				    Srsms01sSendSingleSms op = new Srsms01sSendSingleSms();
						operListener op1 = new operListener();
				    op.addExceptionListener(op1);
				    op.clear();
				    
					//op.setInSecurityWsUserNumber(32460503);
					op.setInNovellCodeWsStaffEmailAddress("ARLOWM");
					op.setInSmsQueueMessage("Welcome to the UNISA online community. Your initial UNISA password is: "+myUnisaMylifeForm.getMyLifePwd()+
							". Keep it safe!");
					op.setInWsSmsToSmsTo("S");
					op.setInCsfClientServerCommunicationsClientVersionNumber((short) 3);
					op.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
					op.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
					op.setInCsfClientServerCommunicationsClientIsGuiFlag("Y");
					op.setInCsfClientServerCommunicationsAction("ML");
					op.setAsStringInSmsQueueReferenceNr(myUnisaMylifeForm.getStudentNr());
					op.setInSmsQueueCellNr(myUnisaMylifeForm.getCellNr());
					op.execute();					
										
					if (op.getOutErrorFlagCsfStringsString1().equals("Y")) {
						eventTrackingService.post(
								eventTrackingService.newEvent(EventTrackingTypes.EVENT_UNISALOGIN_SMSNO, "JavaProxy error: "+op.getOutCsfStringsString500()+ "("+myUnisaMylifeForm.getCellNr()+")", false), usageSession);
						    
											 
					} else {
						eventTrackingService.post(
								eventTrackingService.newEvent(EventTrackingTypes.EVENT_UNISALOGIN_SMSYES, "SMS initial Unisa pwd to cell "+myUnisaMylifeForm.getCellNr(), false), usageSession);
						
					}
			       }
			eventTrackingService.post(
					eventTrackingService.newEvent(EventTrackingTypes.EVENT_UNISALOGIN_ACKNOWACCEPT, "Unisa Login Claimed", false),usageSession);
			 
			return ("claimmyLife");
		} else {
			log.info(this+": "+myUnisaMylifeForm.getStudentNr()+" at step unisa acknoledge");
	    	messages.add(ActionMessages.GLOBAL_MESSAGE,
			        new ActionMessage("message.generalmessage","Please read the statements below and click in the check boxes to indicate that you have read them. "));
			addErrors(request, messages);
			return ("mylifeaccept");
		}
		
		
		
	}
	/**
	 * Method clear
	 * 	clear the screen
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward clear(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {

		MyUnisaMylifeForm myUnisaJoinForm = (MyUnisaMylifeForm) form;		
			myUnisaJoinForm.setSurname("");			
			myUnisaJoinForm.setFullNames("");
			myUnisaJoinForm.setBirthDay("");
			myUnisaJoinForm.setBirthMonth("");
			myUnisaJoinForm.setBirthYear("");
			myUnisaJoinForm.setDateOfBirth("");
			myUnisaJoinForm.setPassport_nr("");
			myUnisaJoinForm.setId_nr("");
		
		return mapping.findForward("persstep");
	}
	/**
	 * Method back
	 * 	go back to persstep of the mylife.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward back(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {

		return mapping.findForward("persstep");
	}
	
		/* remove leading whitespace */
	    public static String ltrim(String source) {
	        return source.replaceAll("^\\s+", "");
	    }

	    /* remove trailing whitespace */
	    public static String rtrim(String source) {
	        return source.replaceAll("\\s+$", "");
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
	    
	    public String randomPassword() throws AddressException {
	    	 String password = ""; /* Stores the letters + digits */
           try{
           String[] pool_lower = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n","o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};                                            
           String[] pool_upper= {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N","O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};    
           String[] pool_numeric = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};
          
           /** Variables to store the end results */           
           String lowerLetters = ""; /* Stores the random letters */
           String digits = ""; /* Stores the random digits */
           String upperLetters = ""; /* Stores the random digits */
          
           /** Random number generators
            * randLet is for randomizing the pool array
            * If you increase the size of either array
            * simply increase the limitation of these
            * generators */
           int randLet = (int)(Math.random() * 26);
           int randDig = (int)(Math.random() * 10);
          
           int count1 = 0; /* Counter for the lower case */
           int count2 = 0; /* Counter for the digits*/
           int count3 = 0; /* Counter for the upper case*/
          
           while(count1 != 3)
           {
                   randLet = (int)(Math.random() * 26);
                   lowerLetters += pool_lower[randLet];
                   count1++;
           }//End while
           while(count2 != 2)
           {
                   randDig = (int)(Math.random() * 10);
                   digits += pool_numeric[randDig];
                   count2++;
           }//End while
           while(count3 != 3)
           {
                   randLet = (int)(Math.random() * 26);
                   upperLetters += pool_upper[randLet];
                   count3++;
           }//End while
           password = lowerLetters+digits+upperLetters;
           }catch(Exception e){
           	// TODO Auto-generated catch block
    	 		e.printStackTrace();
           }
           
           return password;

		} 
	    
	    public void sendEmail(String subject, String body, String emailAddress) throws AddressException {

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
			//contentList.add("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
			emailService = (EmailService) ComponentManager.get(EmailService.class);
			emailService.sendMail(iaReplyTo[0],iaTo,subject,body,iaHeaderTo,iaReplyTo,contentList);		
			log.info("sending mail from "+iaReplyTo[0]+" to "+toEmail.toString());
		} // end of sendEmail
	    
	   /* 
	    * to go the URL and to go the method
	    * <sakai:actions>
		<html:submit property="action" onclick="windowOpen	();">
			<fmt:message key="button.claim"/>
		</html:submit>
		</sakai:actions>*/
}
