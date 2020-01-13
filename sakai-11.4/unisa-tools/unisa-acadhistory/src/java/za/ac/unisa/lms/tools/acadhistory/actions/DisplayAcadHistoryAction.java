//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.acadhistory.actions;

import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.util.LabelValueBean;
import org.sakaiproject.event.api.UsageSession;
import org.sakaiproject.event.api.EventTrackingService;
import org.sakaiproject.event.api.UsageSessionService;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.tool.api.ToolManager;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserDirectoryService;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.component.cover.ServerConfigurationService;

import za.ac.unisa.lms.constants.EventTrackingTypes;
import za.ac.unisa.lms.dao.Gencod;
import za.ac.unisa.lms.dao.StudentSystemGeneralDAO;
import za.ac.unisa.lms.domain.assessmentCriteria.FileFormat;
import za.ac.unisa.lms.tools.acadhistory.dao.AcadHistoryDAO;
import za.ac.unisa.lms.tools.acadhistory.forms.AcadHistoryDisplayForm;
import za.ac.unisa.lms.tools.acadhistory.forms.AcadQualRecord;
import za.ac.unisa.lms.tools.acadhistory.forms.AcadRequestRecord;
import za.ac.unisa.lms.tools.acadhistory.forms.AcadStudyUnitRecord;
import za.ac.unisa.lms.tools.acadhistory.forms.InfoMessagesUtil;
import za.ac.unisa.lms.tools.acadhistory.forms.Student;
import Gigcf01h.Abean.Gigcf01sMaintainGenericCode;
import Srasa01h.Abean.Srasa01sLstAcademicRecordSun;
import Srsra01h.Abean.Srsra01sLstStudentAcademRec;
import Srsrj11h.Abean.Srsrj11sPrtNormalDeclaration;

/**
 * MyEclipse Struts
 * Creation date: 08-18-2005
 *
 * XDoclet definition:
 * @struts:action validate="true"
 */
public class DisplayAcadHistoryAction extends DispatchAction {

	// --------------------------------------------------------- Instance Variables
	private SessionManager sessionManager;
	private UserDirectoryService userDirectoryService;
	private EventTrackingService eventTrackingService;
	private ToolManager toolManager;
	private UsageSessionService usageSessionService;
	private static String unknownError="An  technical error has happenned,\nPlease enter your details again."+
			                           "\nIf this problem persists please log off  myUnisa and Log On again ";
	
	// --------------------------------------------------------- Methods

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
	    
	    
	    public ActionForward emailwithoutmarks(
				ActionMapping mapping,
				ActionForm form,
				HttpServletRequest request,
				HttpServletResponse response) throws Exception {
				
	    	AcadHistoryDisplayForm acadHistoryDisplayForm = (AcadHistoryDisplayForm) form;
			ActionMessages messages = new ActionMessages();	
			StudentSystemGeneralDAO ssDao = new StudentSystemGeneralDAO();
			
			ArrayList viewOptions = new ArrayList();
			viewOptions.add(new LabelValueBean("Display all study units","N"));
			viewOptions.add(new LabelValueBean("Display credits only","Y"));
			request.setAttribute("view",viewOptions);	
			//request.setAttribute("qualCode",acadrec.getQualShortDescription());			
			
			AcadRequestRecord requestRec = new AcadRequestRecord();
			
			requestRec.setMarksFlag("N");
			requestRec.setNqfCreditsFlag("N");
			requestRec.setNqfLevelFlag("N");
			requestRec.setCoverLetterFlag("Y");
			requestRec.setSupplementFlag("Y");
			
			String errMsg = emailAcademicRecord(acadHistoryDisplayForm.getStudent(),acadHistoryDisplayForm.getSelectedQualCode(), requestRec);
			
			if (errMsg.equalsIgnoreCase("")){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
	        			new ActionMessage("message.generalmessage", ssDao.getGenMessage("MAILOK","UNISA_ACADHISTORY")));
	        	 addMessages(request, messages);
			} else {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
	        			new ActionMessage("message.generalmessage", errMsg));
	        	 addErrors(request, messages);
			}			
		
			return mapping.findForward("displayAcadStudyUnit");
			
		}
	    
	    public ActionForward emailwithmarks(
				ActionMapping mapping,
				ActionForm form,
				HttpServletRequest request,
				HttpServletResponse response) throws Exception {
				
	    	AcadHistoryDisplayForm acadHistoryDisplayForm = (AcadHistoryDisplayForm) form;
			ActionMessages messages = new ActionMessages();	
			StudentSystemGeneralDAO ssDao = new StudentSystemGeneralDAO();
			
			ArrayList viewOptions = new ArrayList();
			viewOptions.add(new LabelValueBean("Display all study units","N"));
			viewOptions.add(new LabelValueBean("Display credits only","Y"));
			request.setAttribute("view",viewOptions);	
			//request.setAttribute("qualCode",acadrec.getQualShortDescription());
			
			AcadRequestRecord requestRec = new AcadRequestRecord();
			
			requestRec.setMarksFlag("Y");
			requestRec.setNqfCreditsFlag("Y");
			requestRec.setNqfLevelFlag("Y");
			requestRec.setCoverLetterFlag("Y");
			requestRec.setSupplementFlag("Y");
			
			String errMsg = emailAcademicRecord(acadHistoryDisplayForm.getStudent(),acadHistoryDisplayForm.getSelectedQualCode(), requestRec);
			
			if (errMsg.equalsIgnoreCase("")){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
	        			new ActionMessage("message.generalmessage", ssDao.getGenMessage("MAILOK","UNISA_ACADHISTORY")));
	        	 addMessages(request, messages);
			} else {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
	        			new ActionMessage("message.generalmessage", errMsg));
	        	addErrors(request, messages);
			}			
		
			return mapping.findForward("displayAcadStudyUnit");
			
		}	    
	    
	    public String emailAcademicRecord(Student student, String qualCode, AcadRequestRecord requestRec) throws Exception {
	    	
	    	String errMsg = "";
	    	
	    	AcadHistoryDAO dao = new AcadHistoryDAO();
			AcadQualRecord acadRec = new AcadQualRecord();		
			
			acadRec = dao.getAcademicRecord(Integer.parseInt(student.getNumber()),qualCode);
			StudentSystemGeneralDAO ssDao = new StudentSystemGeneralDAO();
			
			String functionType="";
			String emailAddress="";		
			
			if (student.getNsfasContractBlock() > 0){			
				errMsg = ssDao.getGenMessage("NSFASBLOCK","UNISA_ACADHISTORY");
				return errMsg;	
			}
			if (student.getNumberRestricted().equalsIgnoreCase("N")){
				errMsg = ssDao.getGenMessage("AUDITED","UNISA_ACADHISTORY");
				return errMsg;	
			}
			if (acadRec.getAuditFlag().equalsIgnoreCase("N")){
				errMsg = ssDao.getGenMessage("AUDITED","UNISA_ACADHISTORY");
				return errMsg;	
			}
			
			String emailType="SUBJDECLNM";
			if (requestRec.getMarksFlag().equalsIgnoreCase("Y")){
				emailType="SUBJDECLWM";
			}
			
			String lastRequestTime = dao.getLastAREmailRequest(student.getNumber(),emailType,qualCode);
			if (isLastEmailRequestWithinAHour(lastRequestTime)){
				errMsg = "You requested an Academic Record less than an hour ago. Please wait a while to receive your email or try again later.";
				return errMsg;	
			}
			
			//check valid mylife email address			
			emailAddress = dao.getEmailAddress(Integer.parseInt(student.getNumber()));
			if (emailAddress==null || emailAddress.equalsIgnoreCase("") || !emailAddress.toLowerCase().contains("mylife.unisa.ac.za")){
				errMsg = ssDao.getGenMessage("NOMYLIFE","UNISA_ACADHISTORY");				
	        	return errMsg;		
			}			
			
			
			
			//do not send email on dev & qa -default email to pretoj@unisa.ac.za
			String serverpath = ServerConfigurationService.getServerUrl();
			if (serverpath.contains("mydev") || serverpath.contains("localhost")){
				emailAddress="pretoj@unisa.ac.za";
			}	
			if (serverpath.contains("myqa")){
				emailAddress="uvosman@unisa.ac.za";
			}
			
			Date currentDate = new Date();	
			
			if (!acadRec.getStatus().equalsIgnoreCase("CO")){
				functionType="NORMAL";
			}
			
			if (functionType.equalsIgnoreCase("")){
				
				if (acadRec.getGraduationCeremonyDate()==null || acadRec.getGraduationCeremonyDate().equalsIgnoreCase("")) {
					acadRec.setGraduationCeremonyDate("00010101");
				}
				
				SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
				Date nullDate = formatter.parse("00010101");
				Date ceremonyDate = formatter.parse(acadRec.getGraduationCeremonyDate());						
										
				if (ceremonyDate.equals(nullDate)){
					//error - student needs to request academic record via admin section
					errMsg = ssDao.getGenMessage("GENMAILERR","UNISA_ACADHISTORY");	
					return errMsg;
				}else{
					if (ceremonyDate.before(currentDate)){
						//ceremony Date is in the passed use F140
						functionType="NORMAL";						
					}else{
						//ceremony Date is in the future with date specified F130
						functionType="PRELIM";
					}
				}
				
			}		
			
			Srsrj11sPrtNormalDeclaration op = new Srsrj11sPrtNormalDeclaration();
	        operListener opl = new operListener();
	        op.addExceptionListener(opl);
	        op.clear();

	        /* op.setTracing(Trace.MASK_ALL); */
	       // op.setInIpAddressCsfStringsString15(request.getRemoteAddr());
	        op.setInWsWorkstationCode("internet");
	        op.setInWizfuncReportingControlPrinterCode("MYUNISA");//use printer to send through system code
	        op.setInWsServiceFeesReasonCode("F");
	        op.setInWsProvSubjDeclarationOldDegree("N");
	        op.setInWsProvSubjDeclarationCoursesFlag("N");	      
	        op.setInWsProvSubjDeclarationMarksFlag(requestRec.getMarksFlag());
	        op.setInPrintAcademicSupplementIefSuppliedFlag(requestRec.getSupplementFlag());
	        op.setInWsProvSubjDeclarationCoverLetter(requestRec.getCoverLetterFlag());
	        op.setInNqfCreditsIefSuppliedFlag(requestRec.getNqfCreditsFlag());
	        op.setInNqfLevelIefSuppliedFlag(requestRec.getNqfLevelFlag());
	        op.setInStudentAnnualRecordMkStudentNr(Integer.parseInt(student.getNumber()));
	        op.setInActionCsfClientServerCommunicationsAction("A");;
	        op.setInEmailFromWsAddressEmailAddress("noreply@unisa.ac.za");
	        op.setInFunctionTypeCsfStringsString6(functionType);
	        op.setInWsUserNumber(99998); //Web User 
	        op.setInEmailAddressWsAddressEmailAddress(emailAddress);
	        op.setInSendByEmailIefSuppliedFlag("Y");
	        op.setInSendSmsIefSuppliedFlag("N");
	        op.setInWsQualificationCode(qualCode);	
	        op.execute();
	        if (opl.getException() != null) throw opl.getException();
	        if (op.getExitStateType() < 3) throw new Exception(op.getExitStateMsg());  
		        String Errormsg = op.getOutErrmsgCsfStringsString500();
		        if ((Errormsg != null) && (!Errormsg.equals(""))) {
		        	if (Errormsg.contains("Report completed successfully")){
		        		errMsg="";
		        	}else {
		        		errMsg=ssDao.getGenMessage("GENMAILERR","UNISA_ACADHISTORY");	;
		        	}	
	        }	
			
	    	return errMsg;
	    }
	    
	    public boolean isLastEmailRequestWithinAHour(String lastRequestedTime) throws Exception {
			
	    	boolean lessthanhour = false;
			
			if(lastRequestedTime.length()>1){
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
			 * long diffSeconds = diff / 1000 % 60; long diffMinutes = diff / (60 *
			 * 1000) % 60;
			 */
			if (diffHours == 0) {
				lessthanhour = true;			
			} else {
				lessthanhour = false;
			}
			}
			return lessthanhour;
		}
	    
	    public ActionForward xxxemailwithmarks(
				ActionMapping mapping,
				ActionForm form,
				HttpServletRequest request,
				HttpServletResponse response) throws Exception {
				
	    	AcadHistoryDisplayForm acadHistoryDisplayForm = (AcadHistoryDisplayForm) form;
			ActionMessages messages = new ActionMessages();					
					
			AcadHistoryDAO dao = new AcadHistoryDAO();
			AcadQualRecord acadrec = new AcadQualRecord();
			acadrec = dao.getAcademicRecord(Integer.parseInt(acadHistoryDisplayForm.getStudent().getNumber()), acadHistoryDisplayForm.getSelectedQualCode());
			
			String functionType="";
			String emailAddress="";		
			//request.setAttribute("qualCode",acadrec.getQualShortDescription());
			ArrayList viewOptions = new ArrayList();
			viewOptions.add(new LabelValueBean("Display all study units","N"));
			viewOptions.add(new LabelValueBean("Display credits only","Y"));
			request.setAttribute("view",viewOptions);	
			
			//check valid mylife email address
			emailAddress = dao.getEmailAddress(Integer.parseInt(acadHistoryDisplayForm.getStudent().getNumber()));
			if (emailAddress==null || emailAddress.equalsIgnoreCase("") || !emailAddress.contentEquals("mylife.unisa.ac.za")){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
	        			new ActionMessage("message.generalmessage", "An academic Record can only be e-mailed to you if you have a valid MyLife e-mail address on record."));
				addErrors(request, messages);
		        return mapping.findForward("displayAcadStudyUnit");
			}			
			
			Date currentDate = new Date();	
			
			if (!acadrec.getStatus().equalsIgnoreCase("CO")){
				functionType="NORMAL";
			}
			
			if (functionType.equalsIgnoreCase("")){
				
				if (acadrec.getGraduationCeremonyDate()==null || acadrec.getGraduationCeremonyDate().equalsIgnoreCase("")) {
					acadrec.setGraduationCeremonyDate("00010101");
				}
				
				SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
				Date nullDate = formatter.parse("00010101");
				Date ceremonyDate = formatter.parse(acadrec.getGraduationCeremonyDate());						
										
				if (ceremonyDate.equals(nullDate)){
					//error - student needs to request academic record via admin section
					messages.add(ActionMessages.GLOBAL_MESSAGE,
		        			new ActionMessage("message.generalmessage", "Your subject declaration or academic record cannot be issued at this time.  Please send an e-mail to gaudeamus@unisa.ac.za for assistance."));
				}else{
					if (ceremonyDate.before(currentDate)){
						//ceremony Date is in the passed use F140
						functionType="NORMAL";						
					}else{
						//ceremony Date is in the future with date specified F130
						functionType="PRELIM";
					}
				}
				
			}
			
			if (!messages.isEmpty()) {
		        addErrors(request, messages);
		        return mapping.findForward("displayAcadStudyUnit");
		   }
				
			
			Srsrj11sPrtNormalDeclaration op = new Srsrj11sPrtNormalDeclaration();
	        operListener opl = new operListener();
	        op.addExceptionListener(opl);
	        op.clear();

	        /* op.setTracing(Trace.MASK_ALL); */
	       // op.setInIpAddressCsfStringsString15(request.getRemoteAddr());
	        op.setInWsWorkstationCode("internet");
	        op.setInWsFunctionNumber(140);
	        op.setInWsProvSubjDeclarationMarksFlag("Y");
	        op.setInWsProvSubjDeclarationCoverLetter("Y");
	        op.setInNqfCreditsIefSuppliedFlag("Y");
	        op.setInNqfLevelIefSuppliedFlag("Y");
	        op.setInStudentAnnualRecordMkStudentNr(Integer.parseInt(acadHistoryDisplayForm.getStudent().getNumber()));
	        op.setInActionCsfClientServerCommunicationsAction("A");;
	        op.setInEmailFromWsAddressEmailAddress("noreply@unisa.ac.za");
	        op.setInFunctionTypeCsfStringsString6(functionType);
	        op.setInWsUserNumber(99998); //Web User
	        op.setInEmailAddressWsAddressEmailAddress("pretoj@unisa.ac.za");
	        op.setInWsPrinterCode("MSSFPRINT");
	        op.setInSendByEmailIefSuppliedFlag("Y");
	        op.setInSendSmsIefSuppliedFlag("N");
	        op.setInWsQualificationCode(acadHistoryDisplayForm.getSelectedQualCode());	
	        op.execute();
	        if (opl.getException() != null) throw opl.getException();
	        if (op.getExitStateType() < 3) throw new Exception(op.getExitStateMsg());  
		        String Errormsg = op.getOutErrmsgCsfStringsString500();
		        if ((Errormsg != null) && (!Errormsg.equals(""))) {
		        	if (Errormsg.contains("Report completed successfully")){
		        		messages.add(ActionMessages.GLOBAL_MESSAGE,
			        			new ActionMessage("message.generalmessage", "Your academic record will be emailed to your MyLife email address shortly.<BR/>This may take a few minutes."));
			        	 addMessages(request, messages);
		        	}else {
		        		messages.add(ActionMessages.GLOBAL_MESSAGE,
			        			new ActionMessage("message.generalmessage", "Your subject declaration or academic record cannot be issued at this time.  Please send an e-mail to gaudeamus@unisa.ac.za for assistance."));
			        	 addErrors(request, messages);
		        	}	        	
		        	
	        }			
					
			return mapping.findForward("displayAcadStudyUnit");
			
		}

	   public ActionForward displayAcadHistory(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			{
		   AcadHistoryDisplayForm acadHistoryDisplayForm = (AcadHistoryDisplayForm) form;
		   ActionMessages messages = new ActionMessages();
		   StudentSystemGeneralDAO ssDao = new StudentSystemGeneralDAO();
               try{
		
		messages = (ActionMessages) acadHistoryDisplayForm.validate(mapping, request);
		if (!messages.isEmpty()) {
        	addErrors(request, messages);
        	if (acadHistoryDisplayForm.isStudentUser()){
        		   if(acadHistoryDisplayForm.getStudent()==null){
   			           acadHistoryDisplayForm.setStudent(new Student());
   		          }
        		  return mapping.findForward("displayAcadHistory");
        	}else{
        		  if(acadHistoryDisplayForm.getStudent()==null){
        			      acadHistoryDisplayForm.setStudent(new Student());
        		     }
        		     return mapping.findForward("acadHistoryInput");
        	}
        }
		
		if (acadHistoryDisplayForm.isStudentUser()){
			Student student= new Student();
			AcadHistoryDAO dao = new AcadHistoryDAO();
			student = dao.getStudent(Integer.parseInt(acadHistoryDisplayForm.getStudent().getNumber()));
			if (student.getName()!=null || !student.getName().equalsIgnoreCase("")){
				acadHistoryDisplayForm.setStudent(student);
			} else {
				 acadHistoryDisplayForm.setStudent(new Student());
				 return mapping.findForward("displayAcadHistory");
			}
			
			if (student.getExamFeesDebtFlag().equalsIgnoreCase("Y") ||
					student.getFinancialBlockFlag().equalsIgnoreCase("Y")){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
	        			new ActionMessage("message.generalmessage", ssDao.getGenMessage("FEEBLOCK","UNISA_ACADHISTORY")));
	        }
			
			if(student.getLibraryBlackList()>0){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
	        			new ActionMessage("message.generalmessage", ssDao.getGenMessage("LIBRARY","UNISA_ACADHISTORY")));
	        }
			
			if(student.getDisciplinaryIncid()>0){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
	        			new ActionMessage("message.generalmessage", ssDao.getGenMessage("STUDISPL","UNISA_ACADHISTORY")));
	      	}
		}

		if (messages.isEmpty()) {
			/*
			 * Clear credits only input on form
			 */
			acadHistoryDisplayForm.setCreditsOnly("N");

			Srsra01sLstStudentAcademRec op = new Srsra01sLstStudentAcademRec();
	        operListener opl = new operListener();
	        op.addExceptionListener(opl);
	        op.clear();

	        /* op.setTracing(Trace.MASK_ALL); */
	       // op.setInIpAddressCsfStringsString15(request.getRemoteAddr());
	        op.setInWsWorkstationCode("internet");
	        op.setInWsFunctionNumber(135);
	        op.setInCsfClientServerCommunicationsClientVersionNumber((short) 3);
	        op.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
	        op.setInCsfClientServerCommunicationsAction("D");
	        op.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
	        op.setInWsStudentNr(Integer.parseInt(acadHistoryDisplayForm.getStudent().getNumber()));
	        op.execute();
	        if (opl.getException() != null) throw opl.getException();
	        if (op.getExitStateType() < 3) throw new Exception(op.getExitStateMsg());
	        int count = op.getOutGroupCount();

	        String Errormsg = op.getOutCsfStringsString500();
	        if ((Errormsg != null) && (!Errormsg.equals(""))) {
	        	messages.add(ActionMessages.GLOBAL_MESSAGE,
	        			new ActionMessage("error.coolgenerror", Errormsg));
	        }

	        Vector records = new Vector();
	        HashMap list = new HashMap();
	        // Get result type list from general code table
	        list = getResultTypeList();
	        for (int i=0; i<count; i++) {
	        	AcadQualRecord qualRecord = new AcadQualRecord();
	        	qualRecord.setQualCode(op.getOutGStudentAcademicRecordMkQualificationCode(i));
	            qualRecord.setQualShortDescription(op.getOutGWsQualificationShortDescription(i));
	            DateFormat strDate = new SimpleDateFormat( "yyyy" );
	            if(op.getOutGStudentAcademicRecordFirstRegistrationDate(i)!=null){
	            	qualRecord.setFirstRegistrationDate(strDate.format(op.getOutGStudentAcademicRecordFirstRegistrationDate(i).getTime()));
	            }else{
	            	qualRecord.setFirstRegistrationDate("");
	            }
	            qualRecord.setLastRegistrationYear(op.getOutGStudentAcademicRecordLastAcademicRegistrationYear(i));
	            qualRecord.setStatus(list.get(op.getOutGStudentAcademicRecordStatusCode(i)).toString());
	            records.add(qualRecord);
	        }
	        
	        if (list.isEmpty()){
	        	messages.add(ActionMessages.GLOBAL_MESSAGE,
		        			new ActionMessage("error.coolgenerror", "Academic record list is empty."));
	        }

	        request.setAttribute("records", records);
	        acadHistoryDisplayForm.setQualRecords(records);
	        
	    	if (!acadHistoryDisplayForm.isStudentUser()){
		        Student student= new Student();
		        student.setNumber(String.valueOf(op.getOutWsStudentNr()));
		        student.setName(op.getOutWsStudentSurname());
		        student.setInitials(op.getOutWsStudentInitials());
		        student.setTitle(op.getOutWsStudentMkTitle()); 
		        acadHistoryDisplayForm.setStudent(student);
	    	}

		}

        if (!messages.isEmpty()) {
        	addErrors(request, messages);
        	 if(acadHistoryDisplayForm.getStudent()==null){        		
        		   Student student= new Student();
        		   acadHistoryDisplayForm.setStudent(student);
        	 }
        	if (acadHistoryDisplayForm.isStudentUser()){
        		return mapping.findForward("displayAcadHistory");
        	}else{
        		return mapping.findForward("acadHistoryInput");
        	}
        }

        /* Write event log: Get user details, if not empty write event log with usagesession */
        sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
        userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
		String currentUserID = "";
		Session currentSession = sessionManager.getCurrentSession();
		String userID = currentSession.getUserId();
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
		toolManager = (ToolManager) ComponentManager.get(ToolManager.class);
		if (userID != null) {
			currentUserID = currentSession.getUserEid();
			usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
			UsageSession usageSession = usageSessionService.getSession();
			//UsageSessionService.startSession(currentUserID,request.getRemoteAddr(),request.getHeader("user-agent"));
			eventTrackingService.post(
					eventTrackingService.newEvent(EventTrackingTypes.EVENT_ACADHISTORY_VIEW, toolManager.getCurrentPlacement().getContext(), false),usageSession);
		}else{
			eventTrackingService.post(
					eventTrackingService.newEvent(EventTrackingTypes.EVENT_ACADHISTORY_VIEW, toolManager.getCurrentPlacement().getContext(), false));
		}
		return mapping.findForward("displayAcadHistory");
               
               }catch(Exception ex){
            	                      messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("error.coolgenerror",unknownError));
        	                          addErrors(request, messages);
        	                          acadHistoryDisplayForm.setCreditsOnly("N");
					        	      //20171026 correct Error -  in case of error - determine user type - return to correct jsp
					        	      if (acadHistoryDisplayForm.isStudentUser()){
					        	    	  return mapping.findForward("displayAcadHistory");
					        	      }else{
					        	    	  return mapping.findForward("acadHistoryInput");
					        	      }        	     
					        	      //return mapping.findForward("acadHistoryInput");
			 }
    }
	public ActionForward displayAcadStudyUnit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			 {
		ActionMessages messages = new ActionMessages();
        try{
		
		AcadHistoryDisplayForm acadHistoryDisplayForm = (AcadHistoryDisplayForm) form;

		if (messages.isEmpty()) {
			ArrayList viewOptions = new ArrayList();
			viewOptions.add(new LabelValueBean("Display all study units","N"));
			viewOptions.add(new LabelValueBean("Display credits only","Y"));
			request.setAttribute("view",viewOptions);

			/*
			 * Read credits only input from screen
			 */
			if (request.getParameter("credits")!= null ){
				acadHistoryDisplayForm.setCreditsOnly(request.getParameter("credits"));
	        }

			Srasa01sLstAcademicRecordSun op = new Srasa01sLstAcademicRecordSun();
	        operListener opl = new operListener();
	        op.addExceptionListener(opl);
	        op.clear();
	        
	        op.setInWsWorkstationCode("internet");
	        op.setInWsFunctionNumber(135);
	        op.setInCsfClientServerCommunicationsClientVersionNumber((short) 3);
	        op.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
	        op.setInCsfClientServerCommunicationsAction("D");
	        op.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
	        if(acadHistoryDisplayForm.getCreditsOnly()==null){
	        	throw new Exception();
	        }
	        op.setInCreditsOnlyIefSuppliedFlag(acadHistoryDisplayForm.getCreditsOnly());
	        if (request.getParameter("selectedQual")!= null){
	        	if(request.getParameter("selectedQual").length()>5){
	        		acadHistoryDisplayForm.setSelectedQualCode(request.getParameter("selectedQual").substring(0,5));
	        	}else{
	        		acadHistoryDisplayForm.setSelectedQualCode(request.getParameter("selectedQual"));
	        	}
	        }
	        if(acadHistoryDisplayForm.getSelectedQualCode()==null){
	        	   throw new Exception();
	        }
	        op.setInStudentAcademicRecordMkQualificationCode(acadHistoryDisplayForm.getSelectedQualCode());
	        if(acadHistoryDisplayForm.getStudent().getNumber()==null){
	        	  throw new Exception();
	        }
	        op.setInStudentAcademicRecordMkStudentNr(Integer.parseInt(acadHistoryDisplayForm.getStudent().getNumber()));
	        op.execute();
	        if (opl.getException() != null) throw opl.getException();
	        if (op.getExitStateType() < 3) throw new Exception(op.getExitStateMsg());
	        int count = op.getOutGroupCount();
	      
	        /*
	         * Add error messages from proxy
	         */
	        String Errormsg = op.getOutCsfStringsString500();
	        if ((Errormsg != null) && (!Errormsg.equals(""))) {
	        	messages.add(ActionMessages.GLOBAL_MESSAGE,
	        			new ActionMessage("error.coolgenerror", Errormsg));
	        }

	        /*
	         * Set student details
	         */
	        /*Johanet 20171027 - Student on Form - comment code out 
	        Student student= new Student();
	        student.setNumber(String.valueOf(op.getOutWsStudentNr()));
	        student.setName(op.getOutWsStudentSurname());
	        student.setInitials(op.getOutWsStudentInitials());
	        student.setTitle(op.getOutWsStudentMkTitle()); 
	        acadHistoryDisplayForm.setStudent(student);
	        /* add qual description to request for screen display*/
        	//request.setAttribute("qualCode",op.getOutWsQualificationEngDescription());
	        acadHistoryDisplayForm.setSelectedQualDesc(op.getOutWsQualificationEngDescription());
	        /*
	         * Add study units records to list
	         */
	        //Vector records = new Vector();
        	List<AcadStudyUnitRecord> records = new ArrayList<AcadStudyUnitRecord>();
        	
	        for (int i=0; i<count; i++) {
	        	AcadStudyUnitRecord studyUnitRecord = new AcadStudyUnitRecord();
	        	if (op.getOutGAcademicRecordStudyUnitResultDate(i)!= null){
	        		DateFormat strDate = new SimpleDateFormat( "dd-MM-yyyy" );
	        		studyUnitRecord.setExamDate(strDate.format(op.getOutGAcademicRecordStudyUnitResultDate(i).getTime()));;
	        	}
	        	studyUnitRecord.setStudyUnit(op.getOutGAcademicRecordStudyUnitMkStudyUnitCode(i));
	        	studyUnitRecord.setStudyUnitDescription(op.getOutGWsStudyUnitEngShortDescription(i));
	        	if(op.getOutGAcademicRecordStudyUnitFinalMark(i)==0){
	        		studyUnitRecord.setMark("");
	        	}else{
		        	studyUnitRecord.setMark(Short.toString(op.getOutGAcademicRecordStudyUnitFinalMark(i)));
	        	}
	        	studyUnitRecord.setResultTypeDescr(op.getOutGStudyUnitResultTypeEngDescription(i));
	            records.add(studyUnitRecord);
	            //records.add(studyUnitRecord);
	        }

	       /*
	       * If page down = Y read proxy again until list is completed
	       */
	        String pageDown = op.getOutCsfClientServerCommunicationsRgvScrollDownFlag();
	       while ("Y".equalsIgnoreCase(pageDown)){
	    	   // put this code in because for some reason the program executes too quickly 
	    	   // and coolgen doesn't return the correct data
	        	try {
	    			Thread.sleep(Long.parseLong("1000"));
	    		} catch (Exception e) {

	    		}
				Srasa01sLstAcademicRecordSun opList = new Srasa01sLstAcademicRecordSun();
				operListener oplList = new operListener();
		        opList.addExceptionListener(oplList);
		        opList.clear();
		        opList.setInWsWorkstationCode("internet");
		        opList.setInWsFunctionNumber(135);
		        opList.setInCsfClientServerCommunicationsClientVersionNumber((short) 3);
		        opList.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
		        opList.setInCsfClientServerCommunicationsAction("PD");
		        opList.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
		        opList.setInCreditsOnlyIefSuppliedFlag("N");
		        if (request.getParameter("credits")!= null){
		        	opList.setInCreditsOnlyIefSuppliedFlag(request.getParameter("credits").toUpperCase());//maak input
		        }
		        opList.setInStudentAcademicRecordMkQualificationCode(acadHistoryDisplayForm.getSelectedQualCode());
		        opList.setInStudentAcademicRecordMkStudentNr(Integer.parseInt(acadHistoryDisplayForm.getStudent().getNumber()));
		        opList.execute();

		        if (oplList.getException() != null) throw oplList.getException();
		        if (opList.getExitStateType() < 3) throw new Exception(opList.getExitStateMsg());
		        count = opList.getOutGroupCount();

		        /*
		         * Add error messages from proxy
		         */
		        Errormsg = opList.getOutCsfStringsString500();
		        if ((Errormsg != null) && (!Errormsg.equals(""))) {
		        	messages.add(ActionMessages.GLOBAL_MESSAGE,
		        			new ActionMessage("error.coolgenerror", Errormsg));
		        }
		        for (int i=0; i<count; i++) {
		        	AcadStudyUnitRecord studyUnitRecord = new AcadStudyUnitRecord();
		        	DateFormat strDate = new SimpleDateFormat( "dd-MMM-yyyy" );
		        	if (opList.getOutGAcademicRecordStudyUnitResultDate(i)==null){
		        		studyUnitRecord.setExamDate("");
		        	}else{
		        		studyUnitRecord.setExamDate(strDate.format(opList.getOutGAcademicRecordStudyUnitResultDate(i).getTime()));
		        	}
		        	studyUnitRecord.setStudyUnit(opList.getOutGAcademicRecordStudyUnitMkStudyUnitCode(i));
		        	studyUnitRecord.setStudyUnitDescription(opList.getOutGWsStudyUnitEngShortDescription(i));
		        	if(opList.getOutGAcademicRecordStudyUnitFinalMark(i)==0){
		        		studyUnitRecord.setMark("");
		        	}else{
			        	studyUnitRecord.setMark(Short.toString(opList.getOutGAcademicRecordStudyUnitFinalMark(i)));
		        	}
		        	studyUnitRecord.setResultTypeDescr(opList.getOutGStudyUnitResultTypeEngDescription(i));
		          records.add(studyUnitRecord);
		        }
		        pageDown = opList.getOutCsfClientServerCommunicationsRgvScrollDownFlag();
			}

	        //request.setAttribute("records", records);
	       acadHistoryDisplayForm.setStudyUnitList(records);
		}

        if (!messages.isEmpty()) {
        	addErrors(request, messages);
        }

        /* Write event log: Get user details, if not empty write event log with usagesession */
		String currentUserID = "";
		sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
        userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
		Session currentSession = sessionManager.getCurrentSession();
		String userID = currentSession.getUserId();
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
		toolManager = (ToolManager) ComponentManager.get(ToolManager.class);
		if (userID != null) {
			currentUserID = currentSession.getUserEid();
			usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
			UsageSession usageSession = usageSessionService.getSession();
				//UsageSessionService.startSession(currentUserID,request.getRemoteAddr(),request.getHeader("user-agent"));
			eventTrackingService.post(
				eventTrackingService.newEvent(EventTrackingTypes.EVENT_ACADHISTORY_VIEW, toolManager.getCurrentPlacement().getContext(), false),usageSession);
		}else{
			eventTrackingService.post(
					eventTrackingService.newEvent(EventTrackingTypes.EVENT_ACADHISTORY_VIEW, toolManager.getCurrentPlacement().getContext(), false));

		}
		return mapping.findForward("displayAcadStudyUnit");
        }catch(Exception ex){
        	                   messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("error.coolgenerror",unknownError)); 
        	                   addErrors(request, messages);
        	                   AcadHistoryDisplayForm acadHistoryDisplayForm = (AcadHistoryDisplayForm) form;
        	                   acadHistoryDisplayForm.setCreditsOnly("N");
        	                   //20171026 correct Error -  in case of error - determine user type - return to correct jsp
        	         	      if (acadHistoryDisplayForm.isStudentUser()){
        	         	    	  return mapping.findForward("displayAcadHistory");
        	         	      }else{
        	         	    	  return mapping.findForward("acadHistoryInput");
        	         	      }                 
        	         	      //return mapping.findForward("acadHistoryInput");        	     
			 }
	}
    public ActionForward back(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		return mapping.findForward("displayAcadHistory");
	}

	public ActionForward cancel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		AcadHistoryDisplayForm acadHistoryDisplayForm = (AcadHistoryDisplayForm) form;
		acadHistoryDisplayForm.setCreditsOnly("N");
		return mapping.findForward("displayAcadHistory");
	}

	/*
	 * Method : input
	 * Determine whether the user is a student or Instructor
	 * A Student will go directly to the display screen
	 * An Instructor will get the input screen
	 */
	public ActionForward input(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		AcadHistoryDisplayForm acadHistoryDisplayForm = (AcadHistoryDisplayForm) form;

		// Get user

        sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
        userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
		Session currentSession = sessionManager.getCurrentSession();
		String userId = currentSession.getUserId();
		User user = null;
		if (userId != null) {
			user = userDirectoryService.getUser(userId);
			request.setAttribute("user",user);
		}
		
		/*acadRecRequestButtonState values
		 * E - enable button
		 * D - disable button
		 * T - temporary disabled*/
		/*Enable Academic Request buttons*/
		acadHistoryDisplayForm.setAcadRecRequestButtonState("E");
		StudentSystemGeneralDAO dao = new StudentSystemGeneralDAO();
		Gencod gencod = new Gencod();
		gencod = dao.getGenCode("291", "ARMYUNISA");
		if (gencod != null && gencod.getAfrDescription()!= null && !gencod.getAfrDescription().trim().equalsIgnoreCase("Y")){
			/*Temporary disable Academic Request buttons*/
			acadHistoryDisplayForm.setAcadRecRequestButtonState("T");
		}
		Student student= new Student();
		
		if (request.getParameter("SYSTEM")!=null && request.getParameter("SYSTEM").equalsIgnoreCase("FIC")){
			/* Called from FI Concession tool*/
			student.setNumber(request.getParameter("studNr"));
		    acadHistoryDisplayForm.setStudent(student);
		    acadHistoryDisplayForm.setStudentUser(true);
		    /*Disable Academic Request buttons*/
		    acadHistoryDisplayForm.setAcadRecRequestButtonState("D");
			return displayAcadHistory(mapping, form, request, response);
		}else {
			if ("Student".equalsIgnoreCase(user.getType())){
				/*
				 * Student
				 */
				student.setNumber(user.getEid());
			    acadHistoryDisplayForm.setStudent(student);
			    acadHistoryDisplayForm.setStudentUser(true);
				return displayAcadHistory(mapping, form, request, response);
			}else if ("Instructor".equalsIgnoreCase(user.getType()) || "Lecturer".equalsIgnoreCase(user.getType())) {
				/*
				 * Instructor
				 * Get student nr input
				 */
			    acadHistoryDisplayForm.setStudent(student);
			    acadHistoryDisplayForm.setStudentUser(false);
			    /*Disable Academic Request buttons*/
			    acadHistoryDisplayForm.setAcadRecRequestButtonState("D");
				return mapping.findForward("acadHistoryInput");
			} else{
				/*
				 * TO DO !!!!
				 * User unknown
				 * Go to error screen
				 */

				throw new Exception("AcadHist : User type unknown");
			}
		}
		
		
		
	}

	/*
	 * Method : getResultTypeList
	 *
	 * Get a list of the result type descriptions using a proxy
	 * List will be used to display result type descriptions on the first sreen
	*/
	private HashMap getResultTypeList()	throws Exception {

		HashMap list = new HashMap();
		try{

		StudentSystemGeneralDAO	dao = new StudentSystemGeneralDAO();
		List daoList = dao.getGenCodes(Short.parseShort("23"),0);
		for (int i = 0; i < daoList.size(); i++) {
			Gencod gencod = new Gencod();
			gencod = (Gencod) daoList.get(i);
			list.put(gencod.getCode(),gencod.getEngDescription());
		}
		}catch (Exception e){
			throw new Exception("DisplayAcadHistoryAction : Error generating result type list / "+e.getMessage());
		}
		return list;
	}

	public ActionForward unspecified(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response){

		log.info("DisplayAcadHistory: unspecified method call -no value for parameter in request");

		return mapping.findForward("home");

	}

}