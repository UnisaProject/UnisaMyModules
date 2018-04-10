//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.examtimetable.actions;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.Enumeration;
import java.util.ArrayList;

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
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.Tool;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.tool.api.ToolManager;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserDirectoryService;

import za.ac.unisa.lms.dao.StudentSystemGeneralDAO;
import za.ac.unisa.lms.tools.examtimetable.dao.ExamTimetableDao;
import za.ac.unisa.lms.tools.examtimetable.forms.Coordinates;
import za.ac.unisa.lms.tools.examtimetable.forms.ExamTimetableDisplayForm;
import za.ac.unisa.lms.tools.examtimetable.forms.ExamTimetableRecord;
import za.ac.unisa.lms.tools.examtimetable.forms.Student;
import za.ac.unisa.lms.tools.examtimetable.utils.ExamUtilities;
import Exsug07h.Abean.Exsug07sPrtAdmissionLetter;



/**
 * MyEclipse Struts
 * Creation date: 03-31-2006
 *
 * XDoclet definition:
 * @struts:action path="/displayExamTimetable" name="ExamTimetableDisplayForm" input="/WEB-INF/jsp/examtimetableinput.jsp" parameter="action" validate="true"
 * @struts:action-forward name="examtimetabledisplayforward" path="/WEB-INF/jsp/examtimetabledisplay.jsp"
 * @struts:action-forward name="examtimetableinputforward" path="/WEB-INF/jsp/examtimetableinput.jsp"
 */
public class DisplayExamTimetableAction extends LookupDispatchAction{

	// --------------------------------------------------------- Instance Variables

	public static Log log = LogFactory.getLog(DisplayExamTimetableAction.class);

	// --------------------------------------------------------- Methods
	
	private SessionManager sessionManager;
	private UserDirectoryService userDirectoryService;
	private ToolManager toolManager;

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
		// TODO Auto-generated method stub
		Map map = new HashMap();
		map.put("input", "input");
		map.put("displayMap", "displayMap");
		map.put("button.display", "display");
		map.put("button.back", "prevStep");
		map.put("button.sendStudentEmail", "sendStudentEmail");
		return map;
	}
	
	public ActionForward prevStep(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
			String nextpage="";

			ExamTimetableDisplayForm examTimetableDisplayForm = (ExamTimetableDisplayForm) form;
			nextpage = displayTimeTable(mapping, form, request, response);
			
			return mapping.findForward(nextpage);
		}
	
	public ActionForward display(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
			String nextpage="";

			ExamTimetableDisplayForm examTimetableDisplayForm = (ExamTimetableDisplayForm) form;
			nextpage = displayTimeTable(mapping, form, request, response);
			
			return mapping.findForward(nextpage);
		}
	
	public ActionForward displayMap(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
			ExamTimetableDisplayForm examTimetableDisplayForm = (ExamTimetableDisplayForm) form;
			
			request.setAttribute("latitude", examTimetableDisplayForm.getLatitude());
			request.setAttribute("longitude", examTimetableDisplayForm.getLongitude());
			request.setAttribute("venueAddress", examTimetableDisplayForm.getVenueAddress());
									
			return mapping.findForward("venuemap");
		}


	public ActionForward input(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) throws Exception {

		ExamTimetableDisplayForm examTimetableDisplayForm = (ExamTimetableDisplayForm) form;
		log.debug("ExamTimeTable: input method");		

        // Get user
		String userId = "";
		String userEid = "";
		User user = (User)request.getAttribute("user");
		sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
	    userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
		if(user == null) {
			Session currentSession = sessionManager.getCurrentSession();
			userId = currentSession.getUserId();
			if (userId != null) {
				user = userDirectoryService.getUser(userId);
				userEid = user.getEid();
				request.setAttribute("user",user);
			}
		} else {
			request.setAttribute("user",user);
			userEid = user.getEid();
		}
		if (examTimetableDisplayForm.getExamYear() == 0) {
			ExamUtilities util = new ExamUtilities();
			examTimetableDisplayForm.setExamYear(new Integer(util.getDefaultExamYear()).shortValue());
		}
		examTimetableDisplayForm.setDatabaseError(false);
		List examPeriods=null;
		StudentSystemGeneralDAO dao = new StudentSystemGeneralDAO();
	   try{
		      examPeriods= dao.getExamPeriods();
		 }catch(Exception ex){
    	        examTimetableDisplayForm.setDatabaseError(true);
        	    return mapping.findForward("inputforward");
    	}
		request.setAttribute("examPeriods", examPeriods);

		if (examTimetableDisplayForm.getExamPeriod() == 0){
			   ExamUtilities util = new ExamUtilities();
			   examTimetableDisplayForm.setExamPeriod(new Integer(util.getDefaultExamPeriod()).shortValue());
		}
        
		List examPracTypes =null;
		try{
			 examPracTypes =dao.getGenCodes((short)28,2);
		}catch(Exception ex){
			       examTimetableDisplayForm.setDatabaseError(true);
    	           return mapping.findForward("inputforward");
		}
		request.setAttribute("examPracTypes",examPracTypes);
		if (examTimetableDisplayForm.getPracticalType()== null){
			    examTimetableDisplayForm.setPracticalType("N");
		}
        Student student = new Student();
		//debug start
//		examTimetableDisplayForm.setStudentUser(false);
//		examTimetableDisplayForm.setStudent(student);
		//debug end		
		if (user != null) {
			if ("Student".equalsIgnoreCase(user.getType())){
				// Student
				student.setNumber(user.getEid());
			    examTimetableDisplayForm.setStudent(student);
			    examTimetableDisplayForm.setStudentUser(true);
			}
			else if("Instructor".equalsIgnoreCase(user.getType())){
				// Instructor
				examTimetableDisplayForm.setStudentUser(false);
				examTimetableDisplayForm.setStudent(student);
			}
			else {
				throw new Exception ("ExamTimetable : User type unknown");
			}
		}else {
			examTimetableDisplayForm.setStudentUser(false);
			examTimetableDisplayForm.setStudent(student);
		}			
		//examTimetableDisplayForm.setStudent(null);
		return mapping.findForward("inputforward");
	}

	String displayTimeTable(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

			ActionMessages messages = new ActionMessages();
			ExamTimetableDisplayForm examTimetableDisplayForm = (ExamTimetableDisplayForm) form;
			log.debug("ExamTimeTable: display method");

			StudentSystemGeneralDAO dao = new StudentSystemGeneralDAO();
			List examPeriods=null;
			try{
			      examPeriods= dao.getExamPeriods();
			}catch(Exception ex){
	    	        examTimetableDisplayForm.setDatabaseError(true);
	        	    return "inputforward";
	    	}
			request.setAttribute("examPeriods", examPeriods);

			List examPracTypes =null;
			try{
				 examPracTypes =dao.getGenCodes((short)28,2);
			     }catch(Exception ex){
				       examTimetableDisplayForm.setDatabaseError(true);
	    	           return "inputforward";
			}           
			request.setAttribute("examPracTypes",examPracTypes);

			ExamUtilities util = new ExamUtilities();
			String studentNr = examTimetableDisplayForm.getStudent().getNumber();
			if(studentNr.equals("") || null == studentNr) {
				User user = (User)request.getAttribute("user");
				sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
			    userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
				if(user == null) {
					Session currentSession = sessionManager.getCurrentSession();
					String userId = currentSession.getUserId();
					if (userId != null) {
						user = userDirectoryService.getUser(userId);
					}
				}
				if(user != null) {
					if ("Student".equalsIgnoreCase(user.getType())){
						studentNr = user.getEid();
					}
					request.setAttribute("user",user);
				}
			}
			
			//test student number numeric
			if (!testInteger(studentNr)){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
	        			new ActionMessage("error.coolgenerror", "Student number must be numeric"));
				addErrors(request, messages);

				return "inputforward";
			}	

			if (!util.validStudentNr(studentNr)){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
	        			new ActionMessage("error.coolgenerror", "Student number invalid"));
				addErrors(request, messages);

				return "inputforward";

			}

			Exsug07sPrtAdmissionLetter op = new Exsug07sPrtAdmissionLetter();
	        operListener opl = new operListener();
	        op.addExceptionListener(opl);
	        op.clear();

			op.setInSecurityWsWorkstationCode("internet");
	        op.setInCsfClientServerCommunicationsClientVersionNumber((short) 3);
	        op.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
	        op.setInCsfClientServerCommunicationsAction("D");
	        op.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
	        op.setInSecurityWsUserNumber(1);
	        op.setInStudentStudyUnitExamYear(examTimetableDisplayForm.getExamYear());
	        op.setInStudentStudyUnitMkExamPeriod(examTimetableDisplayForm.getExamPeriod());
	        op.setInPracticalFlagWsPracticalsValuesString1(examTimetableDisplayForm.getPracticalType());
	        op.setInStudentAnnualRecordMkStudentNr(Integer.parseInt(studentNr));
	        op.execute();
	        if (opl.getException() != null) throw opl.getException();
	        if (op.getExitStateType() < 3) throw new Exception(op.getExitStateMsg());
	        int count = op.getOutTimetableGroupCount();

	        String Errormsg = op.getOutCsfStringsString500();
	        if ((Errormsg != null) && (!Errormsg.equals(""))) {
	        	messages.add(ActionMessages.GLOBAL_MESSAGE,
	        			new ActionMessage("error.coolgenerror", Errormsg));
	        	addErrors(request, messages);
				return "inputforward";
	        }
	        
	        /*
	         * Set student details
	         */
	        Student student= new Student();
	        student.setNumber(String.valueOf(op.getOutWsStudentNr()));
	        student.setName(op.getOutWsStudentSurname());
	        student.setInitials(op.getOutWsStudentInitials());
	        student.setTitle(op.getOutWsStudentMkTitle());
	        examTimetableDisplayForm.setStudent(student);	       
	        
	        examTimetableDisplayForm.setTimetableStatus(op.getOutPrelimOrFinalTimetableCsfStringsString1());
	        examTimetableDisplayForm.setTimetableStatusDesc(op.getOutPrelimOrFinalTimetableCsfStringsString40());
	        examTimetableDisplayForm.setVenueCode(op.getOutWsExamVenueCode());
	        examTimetableDisplayForm.setVenueDesc(op.getOutWsExamVenueEngName());
	        examTimetableDisplayForm.setInvigTelephone(op.getOutInvigilatorContactWsAddressHomeNumber());
	        ExamTimetableDao timetableDao = new ExamTimetableDao();
	        Coordinates coordinates = new Coordinates();
	        coordinates = timetableDao.getVenueCoordinates(examTimetableDisplayForm.getVenueCode());
	        examTimetableDisplayForm.setLatitude(coordinates.getLatitude());
	        examTimetableDisplayForm.setLongitude(coordinates.getLongitude());
//	        if (examTimetableDisplayForm.getVenueCode().equalsIgnoreCase("3601013")){
//	        	examTimetableDisplayForm.setLatitude("-25.7520"); //South(-) and North(+)
//	        	examTimetableDisplayForm.setLongitude("28.1690"); //East(+) and West(-)   
//	        } else if (examTimetableDisplayForm.getVenueCode().equalsIgnoreCase("0011010")){
//	        	examTimetableDisplayForm.setLatitude("-33.9067");
//	        	examTimetableDisplayForm.setLongitude("18.5960");
//	        } else  {
//	        	examTimetableDisplayForm.setLatitude("");
//	        	examTimetableDisplayForm.setLongitude("");  
//	        }
	        Vector address = new Vector();

	        if ("P".equalsIgnoreCase(op.getOutPrelimOrFinalTimetableCsfStringsString1())){
	        	/* preliminary examination timetable*/
                
	        	Vector records = new Vector();
	 	        examTimetableDisplayForm.setCalcTextFlag("N");
	 	        for (int i=0; i<count; i++) {	        	
	 	        	ExamTimetableRecord timetableRecord = new ExamTimetableRecord();
	 	        	timetableRecord.setStudyUnit(op.getOutGcWsStudyUnitCode(i));
	 	        	timetableRecord.setPaperNo(op.getOutGcPaperNrCsfStringsString1(i));
	 	        	timetableRecord.setExamDate(op.getOutGcDateTimeCsfStringsString25(i));
	 	        	timetableRecord.setCalcString(op.getOutGcCalcCsfStringsString1(i));
	 	        	if (!timetableRecord.getCalcString().equalsIgnoreCase("")){
	 	        		examTimetableDisplayForm.setCalcTextFlag("Y");
	 	        	}
	 	            records.add(timetableRecord);        
	 	           
	 	        }
	 	       examTimetableDisplayForm.setExamTimetableRecord(records);
	 	       examTimetableDisplayForm.setCurrentPage("prelimforward");
	 	       
	 	       return "prelimforward";
	        } else  {	       	
	        	/* final examination timetable */
	        	if (!"".equalsIgnoreCase(op.getOutVenueWsAddressAddressLine1())){
			       	address.add(op.getOutVenueWsAddressAddressLine1());
			    }
			    if (!"".equalsIgnoreCase(op.getOutVenueWsAddressAddressLine2())){
			      	address.add(op.getOutVenueWsAddressAddressLine2());
			    }
			    if (!"".equalsIgnoreCase(op.getOutVenueWsAddressAddressLine3())){
			      	address.add(op.getOutVenueWsAddressAddressLine3());
			    }
			    if (!"".equalsIgnoreCase(op.getOutVenueWsAddressAddressLine4())){
			       	address.add(op.getOutVenueWsAddressAddressLine4());
			    }
			    if (!"".equalsIgnoreCase(op.getOutVenueWsAddressAddressLine5())){
			     	address.add(op.getOutVenueWsAddressAddressLine5());
			    }
			    if (!"".equalsIgnoreCase(op.getOutVenueWsAddressAddressLine6())){
			      	address.add(op.getOutVenueWsAddressAddressLine6());
			    }
			    if (!"".equalsIgnoreCase(op.getOutVenueDirectionsWsAddressAddressLine1())){
			     	address.add(op.getOutVenueDirectionsWsAddressAddressLine1());
			    }
			    if (!"".equalsIgnoreCase(op.getOutVenueDirectionsWsAddressAddressLine2())){
			       	address.add(op.getOutVenueDirectionsWsAddressAddressLine2());
			    }
			    if (!"".equalsIgnoreCase(op.getOutVenueDirectionsWsAddressAddressLine3())){
			      	address.add(op.getOutVenueDirectionsWsAddressAddressLine3());
			    }
			    if (!"".equalsIgnoreCase(op.getOutVenueDirectionsWsAddressAddressLine4())){
			       	address.add(op.getOutVenueDirectionsWsAddressAddressLine4());
			    }
			    if (!"".equalsIgnoreCase(op.getOutVenueDirectionsWsAddressAddressLine5())){
			      	address.add(op.getOutVenueDirectionsWsAddressAddressLine5());
			    }
			    if (!"".equalsIgnoreCase(op.getOutVenueDirectionsWsAddressAddressLine6())){
			      	address.add(op.getOutVenueDirectionsWsAddressAddressLine6());
			    }
			    request.setAttribute("addresslines", address);
			    			    
			    String venueAddress ="";
			    for (int i=0 ; i<address.size(); i++){
			    	venueAddress=venueAddress + address.get(i).toString() + " ";
			    }
			    examTimetableDisplayForm.setVenueAddress(venueAddress);
			    
			    op = new Exsug07sPrtAdmissionLetter();
		        opl = new operListener();
		        op.addExceptionListener(opl);
		        op.clear();

				op.setInSecurityWsWorkstationCode("internet");
				op.setInInternetFlagCsfStringsString1("Y");	
		        op.setInCsfClientServerCommunicationsClientVersionNumber((short) 3);
		        op.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
		        op.setInCsfClientServerCommunicationsAction("P");
		        op.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
		        op.setInSecurityWsUserNumber(1);
		        op.setInStudentStudyUnitExamYear(examTimetableDisplayForm.getExamYear());
		        op.setInStudentStudyUnitMkExamPeriod(examTimetableDisplayForm.getExamPeriod());
		        op.setInPracticalFlagWsPracticalsValuesString1(examTimetableDisplayForm.getPracticalType());
		        op.setInStudentAnnualRecordMkStudentNr(Integer.parseInt(studentNr));
		        op.execute();
		        if (opl.getException() != null) throw opl.getException();
		        if (op.getExitStateType() < 3) throw new Exception(op.getExitStateMsg());
		        Errormsg = "";
		        Errormsg = op.getOutCsfStringsString500();
		        if ((Errormsg != null) && (!Errormsg.equals(""))) {
		        	messages.add(ActionMessages.GLOBAL_MESSAGE,
		        			new ActionMessage("error.coolgenerror", Errormsg));
		        	addErrors(request, messages);
					return "inputforward";
		        }
		        
		        ExamTimetableDao examDao = new ExamTimetableDao();
		        examTimetableDisplayForm.setParText4130(examDao.getFinalTimetableParText(4130));
		        examTimetableDisplayForm.setParText4136(examDao.getFinalTimetableParText(4136));
		        examTimetableDisplayForm.setParText4137(examDao.getFinalTimetableParText(4137));
		        examTimetableDisplayForm.setParText4138(examDao.getFinalTimetableParText(4138));
		        examTimetableDisplayForm.setParText4125(examDao.getFinalTimetableParText(4125));
		        
		        examTimetableDisplayForm.setAdmissionFlag("N");
		        examTimetableDisplayForm.setNoAdmissionFlag("N");
		        examTimetableDisplayForm.setFcFlag("N");
		        //Admission List
		        count = op.getOutGroupAdmissionCount();
		        
		        List list = new ArrayList();
		        String text = "";
		        for (int i=0; i<count; i++) {
		        	ExamTimetableRecord timetableRecord = new ExamTimetableRecord();
		        	timetableRecord.setStudyUnitDesc((op.getOutGAdmissionWizfuncReportDetailAttr(i).substring(0, 43)).trim());
		        	timetableRecord.setStudyUnit(op.getOutGAdmissionWizfuncReportDetailAttr(i).substring(52,66));
		        	timetableRecord.setExamDate(op.getOutGAdmissionWizfuncReportDetailAttr(i).substring(66));
		        	list.add(timetableRecord);
//		        	text = "";
//		        	text = op.getOutGAdmissionWizfuncReportDetailAttr(i);
//		        	list.add(text);
		        	examTimetableDisplayForm.setAdmissionFlag("Y");		        	
		        }
		        
		        examTimetableDisplayForm.setAdmissionList(list);
		        
		        //No Admission List
		        count = op.getOutGroupNoAdmissionCount();
		        
		        list = new ArrayList();
		        text = "";
		        
		        for (int i=0; i<count; i++) {
		        	text = "";
		        	text = op.getOutGNoAdmissionWsStudyUnitCode(i);
		        	list.add(text);
		        	examTimetableDisplayForm.setNoAdmissionFlag("Y");	
		        }
		        
		        examTimetableDisplayForm.setNoAdmissionList(list);
		        
		        //Fc List		        
		        count = op.getOutGroupFcCount();
		        
		        list = new ArrayList();
		        text = "";
		        
		        for (int i=0; i<count; i++) {
		        	text = "";
		        	text = op.getOutGFcWsStudyUnitCode(i);
		        	list.add(text);
		        	examTimetableDisplayForm.setFcFlag("Y");	
		        }
		        
		        examTimetableDisplayForm.setFcList(list);
		        
		        //Admission Paragraph List to include
		        count = op.getOutAdmParGroupCount();
		        
		        list = new ArrayList();
		        ArrayList textList = new ArrayList();
		        Short parCode;
		        
		        for (int i=0; i<count; i++) {
		        	parCode = op.getOutGWsDocumentParagraphCode(i);
		        	if (parCode == 9999){
		        		int countSuppParLines = op.getOutSupParGroupCount();
		        		ArrayList suppParLines = new ArrayList();
		        		
		        		for (int j=0; j < countSuppParLines; j++){
		        			suppParLines.add(op.getOutGWsDocumentParagraphLineText(j));
		        		}
		        		textList.add(suppParLines);
		        	}else {
		        		list=examDao.getFinalTimetableParText(parCode);
			        	textList.add(list);	
		        	}		        	
		        }
		        
		        examTimetableDisplayForm.setAdmParTextList(textList);		
		        //setup URL back to calling page
		        	        
		        toolManager = (ToolManager)ComponentManager.get(ToolManager.class);
		        String toolUrl = ServerConfigurationService.getToolUrl();
		        String toolId = toolManager.getCurrentPlacement().getId();
		        examTimetableDisplayForm.setPageUrl(toolUrl + "/" + toolId + "/" + "displayExamTimetable.do?action=Display");
		        
		        //setup link to map URL  
		        String venueAddressParam = "";
		        venueAddressParam = examTimetableDisplayForm.getVenueAddress().replace("&", "%26");
		        //String urlString = "https://www2.unisa.ac.za/aol/asp/map.asp?latitude=" + examTimetableDisplayForm.getLatitude() + "&longitude=" + examTimetableDisplayForm.getLongitude() + "&markerText="  + examTimetableDisplayForm.getVenueAddress();
		        //String urlString = "https://www2.unisa.ac.za/aol/asp/map.asp?latitude=" + examTimetableDisplayForm.getLatitude() + "&longitude=" + examTimetableDisplayForm.getLongitude() + "&markerText="  + examTimetableDisplayForm.getVenueAddress() +  "&mapHeader=" + examTimetableDisplayForm.getVenueDesc() + "&referrer=" + examTimetableDisplayForm.getPageUrl();
		        String urlString = "https://stratus.unisa.ac.za/aol/asp/map.asp?latitude=" + examTimetableDisplayForm.getLatitude() + "&longitude=" + examTimetableDisplayForm.getLongitude() + "&markerText="  + venueAddressParam +  "&mapHeader=" + examTimetableDisplayForm.getVenueDesc() + "&referrer=" + examTimetableDisplayForm.getPageUrl();
		        //String urlString = "https://mydev.unisa.ac.za/unisa-exampaperonline/test/displayMap.jsp?latitude=" + examTimetableDisplayForm.getLatitude() + "&longitude=" + examTimetableDisplayForm.getLongitude() + "&markerText="  + examTimetableDisplayForm.getVenueAddress() + "&referrer=" + examTimetableDisplayForm.getPageUrl();
		        request.setAttribute("urlString", urlString);
		        examTimetableDisplayForm.setCurrentPage("finalforward");
		        
			    return "finalforward";
	        } 
	}
	
	String sendEmail(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

			ActionMessages messages = new ActionMessages();
			ExamTimetableDisplayForm examTimetableDisplayForm = (ExamTimetableDisplayForm) form;
			StudentSystemGeneralDAO dao = new StudentSystemGeneralDAO();
			
			List examPeriods=null;
			try{
			      examPeriods= dao.getExamPeriods();
			}catch(Exception ex){
	    	        examTimetableDisplayForm.setDatabaseError(true);
	        	    return "inputforward";
	    	}
			request.setAttribute("examPeriods", examPeriods);

			List examPracTypes =null;
			try{
				 examPracTypes =dao.getGenCodes((short)28,2);
			     }catch(Exception ex){
				       examTimetableDisplayForm.setDatabaseError(true);
	    	           return "inputforward";
			}           
			request.setAttribute("examPracTypes",examPracTypes);

			ExamUtilities util = new ExamUtilities();
			String studentNr = examTimetableDisplayForm.getStudent().getNumber();
			
			if(studentNr.equals("") || null == studentNr) {
				User user = (User)request.getAttribute("user");
				sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
			    userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
				if(user == null) {
					Session currentSession = sessionManager.getCurrentSession();
					String userId = currentSession.getUserId();
					if (userId != null) {
						user = userDirectoryService.getUser(userId);
					}
				}
				if(user != null) {
					if ("Student".equalsIgnoreCase(user.getType())){
						studentNr = user.getEid();
					}
					request.setAttribute("user",user);
				}
			}
			
			log.debug("ExamTimeTable: send email method");
			
			Exsug07sPrtAdmissionLetter op = new Exsug07sPrtAdmissionLetter();
	        operListener opl = new operListener();
	        op.addExceptionListener(opl);
	        op.clear();

			op.setInSecurityWsWorkstationCode("internet");
			op.setInInternetFlagCsfStringsString1("Y");
	        op.setInCsfClientServerCommunicationsClientVersionNumber((short) 3);
	        op.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
	        op.setInCsfClientServerCommunicationsAction("E");
	        op.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
	        op.setInSecurityWsUserNumber(1);
	        op.setInStudentStudyUnitExamYear(examTimetableDisplayForm.getExamYear());
	        op.setInStudentStudyUnitMkExamPeriod(examTimetableDisplayForm.getExamPeriod());
	        op.setInPracticalFlagWsPracticalsValuesString1(examTimetableDisplayForm.getPracticalType());
	        op.setInStudentAnnualRecordMkStudentNr(Integer.parseInt(studentNr));
	        op.setInSecurityWsPrinterCode("lp14");
	        op.setInFromEmailAddressCsfStringsString132("no-reply@unisa.ac.za");
	        op.execute();
	        
	        if (opl.getException() != null) throw opl.getException();
	        
	        if (op.getExitStateType() < 3) throw new Exception(op.getExitStateMsg());
	        
			if ("P".equalsIgnoreCase(op.getOutPrelimOrFinalTimetableCsfStringsString1())){
				
				examTimetableDisplayForm.setCurrentPage("prelimforward");
			}else{
				
				/* final examination timetable */
				Vector address = new Vector();
				
	        	if (!"".equalsIgnoreCase(op.getOutVenueWsAddressAddressLine1())){
			       	address.add(op.getOutVenueWsAddressAddressLine1());
			    }
			    if (!"".equalsIgnoreCase(op.getOutVenueWsAddressAddressLine2())){
			      	address.add(op.getOutVenueWsAddressAddressLine2());
			    }
			    if (!"".equalsIgnoreCase(op.getOutVenueWsAddressAddressLine3())){
			      	address.add(op.getOutVenueWsAddressAddressLine3());
			    }
			    if (!"".equalsIgnoreCase(op.getOutVenueWsAddressAddressLine4())){
			       	address.add(op.getOutVenueWsAddressAddressLine4());
			    }
			    if (!"".equalsIgnoreCase(op.getOutVenueWsAddressAddressLine5())){
			     	address.add(op.getOutVenueWsAddressAddressLine5());
			    }
			    if (!"".equalsIgnoreCase(op.getOutVenueWsAddressAddressLine6())){
			      	address.add(op.getOutVenueWsAddressAddressLine6());
			    }
			    if (!"".equalsIgnoreCase(op.getOutVenueDirectionsWsAddressAddressLine1())){
			     	address.add(op.getOutVenueDirectionsWsAddressAddressLine1());
			    }
			    if (!"".equalsIgnoreCase(op.getOutVenueDirectionsWsAddressAddressLine2())){
			       	address.add(op.getOutVenueDirectionsWsAddressAddressLine2());
			    }
			    if (!"".equalsIgnoreCase(op.getOutVenueDirectionsWsAddressAddressLine3())){
			      	address.add(op.getOutVenueDirectionsWsAddressAddressLine3());
			    }
			    if (!"".equalsIgnoreCase(op.getOutVenueDirectionsWsAddressAddressLine4())){
			       	address.add(op.getOutVenueDirectionsWsAddressAddressLine4());
			    }
			    if (!"".equalsIgnoreCase(op.getOutVenueDirectionsWsAddressAddressLine5())){
			      	address.add(op.getOutVenueDirectionsWsAddressAddressLine5());
			    }
			    if (!"".equalsIgnoreCase(op.getOutVenueDirectionsWsAddressAddressLine6())){
			      	address.add(op.getOutVenueDirectionsWsAddressAddressLine6());
			    }
			    request.setAttribute("addresslines", address);
			    
				//setup URL back to calling page		        	        
		        toolManager = (ToolManager)ComponentManager.get(ToolManager.class);
		        String toolUrl = ServerConfigurationService.getToolUrl();
		        String toolId = toolManager.getCurrentPlacement().getId();
		        examTimetableDisplayForm.setPageUrl(toolUrl + "/" + toolId + "/" + "displayExamTimetable.do?action=Display");
				
				log.info("toolUrl "+toolUrl);
				log.info("toolId "+toolId);
				log.info("longitude "+examTimetableDisplayForm.getLongitude());
				log.info("latitude "+examTimetableDisplayForm.getLatitude());
		        
		        //setup link to map URL  
		        String venueAddressParam = "";
		        venueAddressParam = examTimetableDisplayForm.getVenueAddress().replace("&", "%26");
		        
		        String urlString = "https://stratus.unisa.ac.za/aol/asp/map.asp?latitude=" + examTimetableDisplayForm.getLatitude() + "&longitude=" + examTimetableDisplayForm.getLongitude() + "&markerText="  + venueAddressParam +  "&mapHeader=" + examTimetableDisplayForm.getVenueDesc() + "&referrer=" + examTimetableDisplayForm.getPageUrl();
		        request.setAttribute("urlString", urlString);
				
				examTimetableDisplayForm.setCurrentPage("finalforward");
			}
	        
	        String nextPage=examTimetableDisplayForm.getCurrentPage();
	        String Errormsg = op.getOutCsfStringsString500();
	        
	        if ((Errormsg != null) && (!Errormsg.equals(""))) {
	        	messages.add(ActionMessages.GLOBAL_MESSAGE,
	        			new ActionMessage("error.coolgenerror", Errormsg));
	        	addErrors(request, messages);
		    }
	         return nextPage;
	}
	
	public ActionForward sendStudentEmail(ActionMapping mapping,
			                    ActionForm form,
			                    HttpServletRequest request,
			                    HttpServletResponse response) throws Exception {
                                 
		                            log.info("ExamTimeTable: send email event handler");
		                            ExamTimetableDisplayForm examTimetableDisplayForm = (ExamTimetableDisplayForm) form;
		                            sendEmail(mapping,form,request,response);
		                            String nextPage=examTimetableDisplayForm.getCurrentPage();
		                   return mapping.findForward(nextPage);
   }
	
	public boolean testInteger(String stringValue) {
		try
		{
			Integer i = Integer.parseInt(stringValue);
			return true;
		}	
		catch(NumberFormatException e)
		{}
		return false;
	}

	public ActionForward unspecified(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response){

		log.info("ExamTimeTable: unspecified method call -no value for parameter in request");

		return mapping.findForward("home");

	}

}

