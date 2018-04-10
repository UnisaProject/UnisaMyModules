package za.ac.unisa.lms.tools.results.actions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.event.api.EventTrackingService;
import org.sakaiproject.event.api.UsageSession;
import org.sakaiproject.event.api.UsageSessionService;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.tool.api.ToolManager;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserDirectoryService;

import za.ac.unisa.lms.constants.EventTrackingTypes;
import za.ac.unisa.lms.tools.results.dao.ResultsDAO;
import za.ac.unisa.lms.tools.results.dao.Xamprd;
import za.ac.unisa.lms.tools.results.forms.CourseResult;
import za.ac.unisa.lms.tools.results.forms.ResultsForm;
import za.ac.unisa.lms.tools.results.util.ExamUtilities;
import Exerp01h.Abean.Exerp01sPrtResultsAndTimetab;

public class DisplayResultsAction extends DispatchAction {
	
	private SessionManager sessionManager;
	private UserDirectoryService userDirectoryService;	
	private UsageSessionService usageSessionService;
	private EventTrackingService eventTrackingService;
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

	public ActionForward input(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ResultsForm resultsForm = (ResultsForm) form;
		if (resultsForm.getExamYear() == null) {
			ExamUtilities util = new ExamUtilities();
			resultsForm.setExamYear(new Integer(util.getDefaultExamYear())
					.toString());
		}
		
		//default system to logged into myUnisa
		String fromSystem="";
	
		if (request.getParameter("SYSTEM")==null){
			//logged onto myUnisa - called from within myUnisa
			fromSystem="";
			sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
		    userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
			Session currentSession = sessionManager.getCurrentSession();
			String userID = currentSession.getUserId();
			if (userID != null) {
				User user = userDirectoryService.getUser(userID);
				if (user.getType().equalsIgnoreCase("student")) {
					//resultsForm.setStudentNr(user.getEid());
					resultsForm.setStudentUser(true);
				}else if (user.getType().equalsIgnoreCase("Instructor")){
					resultsForm.setStudentUser(false);
					resultsForm.setLecturerUser(true);
				}
			}else{
				resultsForm.setStudentUser(false);
				resultsForm.setLecturerUser(false);
			}
		}else{
			//called from outside myUnisa - not logged onto myUnisa
			fromSystem=request.getParameter("SYSTEM");
		}
		
		resultsForm.setStaffOnlineUser(false);	
		if (fromSystem!=""){
			resultsForm.setStudentNr("");
			resultsForm.setStudentUser(false);
			resultsForm.setLecturerUser(false);
		}
				
		request.setAttribute("fromSystem", "IMU");
		
		if("STF".equalsIgnoreCase(request.getParameter("SYSTEM"))){
			// indicates that request is from staff online
			resultsForm.setStaffOnlineUser(true);		
		}
	
		request.setAttribute("fromSystem", fromSystem);

		ResultsDAO dao = new ResultsDAO();
		List examPeriods = dao.getExamPeriods();

		request.setAttribute("examPeriods", examPeriods);
		//resultsForm.setExampPeriods(examPeriods);
		if (resultsForm.getExamPeriod()== null) {
			ExamUtilities util = new ExamUtilities();
			resultsForm.setExamPeriod(new Short(util.getDefaultExamPeriod()));
		}

		return mapping.findForward("input");
	}

	public ActionForward display(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			{
		       ActionMessages errors = new ActionMessages();
		       ResultsForm resultsForm = (ResultsForm) form;
 		    try{
		
		
		//reset result list
		List results = new ArrayList();
		resultsForm.setListResults(results);		
		
		ResultsDAO dao = new ResultsDAO();
		List examPeriods = dao.getExamPeriods();
		Iterator n = examPeriods.iterator();
		while (n.hasNext()) {
			Xamprd xamprd = (Xamprd) n.next();
			if (xamprd.getCode().equals(resultsForm.getExamPeriod())) {
				resultsForm.setXamprd(xamprd);
			}
		}
		
		request.setAttribute("examPeriods", examPeriods);
		//resultsForm.setExampPeriods(examPeriods);
		String fromSystem="";
		fromSystem = request.getParameter("fromSystem");
		request.setAttribute("fromSystem", fromSystem);
		String studentNr = "";

		String loggedInStudentNr = null;
		UsageSession usageSession = null;
		sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
		Session currentSession = sessionManager.getCurrentSession();
		String userID = currentSession.getUserId();
		//if from staff online or in the outside environment of myUnisa - not logged in
		if (!fromSystem.equalsIgnoreCase("")){
			loggedInStudentNr=null;
			resultsForm.setStudentUser(false);
			resultsForm.setLecturerUser(false);
			// set student nr to input from form			
			if ("".equals(resultsForm.getStudentNr())){
				
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.coolgenerror","Student number can not be empty."));
				addErrors(request, errors);
				if (resultsForm.getCurrentPage().equalsIgnoreCase("input")){
					return mapping.findForward("input");
				}else{
					return mapping.findForward("display");
				}
	        }else{
				studentNr = resultsForm.getStudentNr();
			}			
		}
		//logged onto myUnisa
		if (userID != null & fromSystem.equalsIgnoreCase("")) {
			userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
			User user = userDirectoryService.getUser(userID);
			usageSessionService = (UsageSessionService)ComponentManager.get(UsageSessionService.class);
			usageSession = usageSessionService.getSession();
				//UsageSessionService.startSession(currentUserID,request.getRemoteAddr(),request.getHeader("user-agent"));
			if (user.getType().equalsIgnoreCase("student")) {
				loggedInStudentNr = user.getEid();
				// set student number to logged in user as it is not an input on
				// jsp anymore
				studentNr = loggedInStudentNr;
				resultsForm.setStudentNr(loggedInStudentNr);
				resultsForm.setStudentUser(true);
			}else if (user.getType().equalsIgnoreCase("Instructor")){
				// set student nr to input from form
				loggedInStudentNr=null;
				resultsForm.setStudentUser(false);
				resultsForm.setLecturerUser(true);
				if ("".equals(resultsForm.getStudentNr())){
					errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.coolgenerror","Student number can not be empty."));
					addErrors(request, errors);
					if (resultsForm.getCurrentPage().equalsIgnoreCase("input")){
						return mapping.findForward("input");
					}else{
						return mapping.findForward("display");
					}
		        }else{
					studentNr = resultsForm.getStudentNr();
				}
			} 
		}

 		ActionMessages formErrors = resultsForm.validate(mapping, request);
		if (formErrors != null) errors = formErrors;


		if (!errors.isEmpty()) {
        	addErrors(request, errors);
        	if (resultsForm.getCurrentPage().equalsIgnoreCase("input")){
				return mapping.findForward("input");
			}else{
				return mapping.findForward("display");
			}
        }

        //studentNr = Integer.parseInt(resultsForm.getStudentNr());
        short examYear = Short.parseShort(resultsForm.getExamYear());

		ExamUtilities util = new ExamUtilities();
		try {
			if (!util.validStudentNr(studentNr)) {
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.coolgenerror","Student number invalid!"));
			}

			if (loggedInStudentNr == null){
				if (resultsForm.isLecturerUser() || resultsForm.isStaffOnlineUser()){
					//dont do any tests for lecturers or staff online at the moment
				}
				else
				{	if (dao.blockStudentResults(Integer.parseInt(studentNr))){
					errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.coolgenerror", "Results can only be accessed by logging onto MyUnisa"));
					} else if (examYear < Calendar.getInstance().get(Calendar.YEAR) - 1) {
						errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.coolgenerror", "Results for previous examinations can be obtained by logging in, from the menu 'my admin/exam results'"));
					} else if ((examYear==Calendar.getInstance().get(Calendar.YEAR) - 1 ) &&
						(Calendar.getInstance().get(Calendar.MONTH)>=3)) {
						errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.coolgenerror", "Results for previous examinations can be obtained by logging in, from the menu 'my admin/exam results'"));
					}
				}
			}else {
				if (!loggedInStudentNr.equalsIgnoreCase(resultsForm.getStudentNr())) {
					if (dao.blockStudentResults(Integer.parseInt(studentNr))){
						errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.coolgenerror", "Results can only be accessed by logging onto MyUnisa with the student number for which results are required"));
						} else if (examYear < Calendar.getInstance().get(Calendar.YEAR) - 1) {
						errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.coolgenerror", "Results for previous examinations can be obtained by logging in, from the menu 'my admin/exam results'"));
					} else if ((examYear==Calendar.getInstance().get(Calendar.YEAR) - 1 ) &&
						(Calendar.getInstance().get(Calendar.MONTH)>=3)) {
						errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.coolgenerror", "Results for previous examinations can be obtained by logging in, from the menu 'my admin/exam results'"));
					}
				}
			}
		} catch(NumberFormatException nfe) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.coolgenerror","Student number invalid!"));
		}


		if (errors.isEmpty()) {
			Exerp01sPrtResultsAndTimetab op = new Exerp01sPrtResultsAndTimetab();
	        operListener opl = new operListener();
	        op.addExceptionListener(opl);
	        op.clear();
	        /* op.setTracing(Trace.MASK_ALL); */
	        op.setInIpAddressCsfStringsString15(request.getRemoteAddr());
	        op.setInSecurityWsWorkstationCode("intjsp");
	        op.setInSecurityWsFunctionNumber(373);
	        op.setInCsfClientServerCommunicationsClientVersionNumber((short) 3);
	        op.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
	        op.setInCsfClientServerCommunicationsAction("D");
	        op.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
	        op.setInExamDetailStudentAnnualRecordMkStudentNr(Integer.parseInt(studentNr));
	        op.setInExamDetailStudentAnnualRecordMkAcademicPeriod(resultsForm.getExamPeriod().shortValue());
	        op.setInExamDetailStudentAnnualRecordMkAcademicYear(examYear);
	        op.execute();
	        if (opl.getException() != null) throw opl.getException();
	        if (op.getExitStateType() < 3) throw new Exception(op.getExitStateMsg());
	        int count = op.getOutResultGroupCount();

	        String Errormsg = op.getOutCsfStringsString500();
	        if ((Errormsg != null) && (!Errormsg.equals(""))) {
	        	errors.add(ActionMessages.GLOBAL_MESSAGE,
	        			new ActionMessage("error.coolgenerror", Errormsg));	        
	        }
	        //Get sysdate to display query date on result window
	    	Calendar calendar = Calendar.getInstance();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			resultsForm.setQueryDate(formatter.format(calendar.getTime()));
			            
	        for (int i=0; i<count; i++) {
	        	CourseResult result = new CourseResult();
	        	result.setCourse(op.getOutGAcademicRecordStudyUnitMkStudyUnitCode(i));
	            result.setFinalMark(op.getOutGFinalMarkCsfStringsString3(i));
	            result.setDescription(op.getOutGRemarksCsfStringsString50(i));
	            result.setExamMark(op.getOutGStudentExamResultExamMark(i));
	            result.setYearMark(op.getOutGStudentExamResultYearMark(i));
	            result.setPortfolioMark(op.getOutGStudentExamResultPortfolioMark(i));
	            Double temp = Double.parseDouble(String.valueOf(op.getOutGWsFinalMarkCalculationYearMarkWeight(i)));
	            result.setYearMarkWeight(temp.intValue());
	            temp = Double.parseDouble(String.valueOf(op.getOutGWsFinalMarkCalculationExaminationWeight(i)));
	            result.setExamMarkWeight(temp.intValue());
	            temp = Double.parseDouble(String.valueOf(op.getOutGWsFinalMarkCalculationPortfolioWeight(i)));
	            result.setPortfolioWeight(temp.intValue());
	            result.setMessage(op.getOutGMessageCsfStringsString100(i));
	            result.setPaper1Mark(op.getOutGP1StudentExamPaperResultPaperMark(i));
	            temp = Double.parseDouble(String.valueOf(op.getOutGP1ExamPeriodDatePaperWeight(i)));
	            result.setPaper1Weight(temp.intValue());
	            temp = Double.parseDouble(String.valueOf(op.getOutGP2ExamPeriodDatePaperWeight(i)));
	            result.setPaper2Weight(temp.intValue());
	            result.setPaper2Mark(op.getOutGP2StudentExamPaperResultPaperMark(i));
	            result.setShowDetails(op.getOutGShowDetailsIefSuppliedFlag(i));
	            results.add(result);
	        }

	        resultsForm.setListResults(results);
	        
	        resultsForm.setTextLine1("");
	        resultsForm.setTextLine2("");
	        resultsForm.setTextLine3("");
	        resultsForm.setTextLine4("");
	        resultsForm.setTextLine5("");
	        if (op.getOutFinalOrProvCsfStringsString1().equalsIgnoreCase("P")){
	        resultsForm.setTextLine1(op.getOutLine1CsfStringsString100());
	        resultsForm.setTextLine2(op.getOutLine2CsfStringsString100());
	        resultsForm.setTextLine3(op.getOutLine3CsfStringsString100());
	        resultsForm.setTextLine4(op.getOutLine4CsfStringsString100());
	        resultsForm.setTextLine5(op.getOutLine5CsfStringsString100());
	        }
		}

        if (!errors.isEmpty()) {
        	addErrors(request, errors);
        	if (resultsForm.getCurrentPage().equalsIgnoreCase("input")){
        		return mapping.findForward("input");
        	}else{
        		return mapping.findForward("display");
        	}
//        	return input(mapping, form, request, response);
        }

        if (usageSession != null){
        	eventTrackingService = (EventTrackingService)ComponentManager.get(EventTrackingService.class);
        	toolManager = (ToolManager)ComponentManager.get(ToolManager.class);
        	eventTrackingService.post(
				eventTrackingService.newEvent(EventTrackingTypes.EVENT_RESULTS_VIEW, toolManager.getCurrentPlacement().getContext(), false),usageSession);
        }
	}catch(Exception ex){
	  	                   errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.coolgenerror", "An unexpected  error happened.Please try again"));
	                       addErrors(request,errors);
	                       addExamPeriod(resultsForm,request);
		                   return mapping.findForward("display");
		
	}
		return mapping.findForward("display");
	}
	
	public ActionForward displayDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			 {
		        ResultsForm resultsForm = (ResultsForm) form;
		        ActionMessages errors = new ActionMessages();
		        try {
		        
		        	 addExamPeriod(resultsForm,request);
           String fromSystem="";
		fromSystem = request.getParameter("fromSystem");
		request.setAttribute("fromSystem", fromSystem);		
		CourseResult result_i = new CourseResult();
		for (int i=0; i < resultsForm.getListResults().size(); i++){
			result_i =(CourseResult)resultsForm.getListResults().get(i);
			if (result_i.getCourse().equalsIgnoreCase(resultsForm.getSelectCourse())){
				CourseResult result = new CourseResult();
				result.setCourse(result_i.getCourse());
				result.setFinalMark(result_i.getFinalMark());
				result.setDescription(result_i.getDescription());
				result.setExamMark(result_i.getExamMark());
				result.setExamMarkWeight(result_i.getExamMarkWeight());
				result.setYearMark(result_i.getYearMark());
				result.setYearMarkWeight(result_i.getYearMarkWeight());
				result.setPortfolioMark(result_i.getPortfolioMark());
				result.setPortfolioWeight(result_i.getPortfolioWeight());
				result.setPaper1Mark(result_i.getPaper1Mark());
				result.setPaper1Weight(result_i.getPaper1Weight());
				result.setPaper2Mark(result_i.getPaper2Mark());
				result.setPaper2Weight(result_i.getPaper2Weight());	
				result.setMessage(result_i.getMessage());
				resultsForm.setSelectResult(result);
				i = resultsForm.getListResults().size();
			}			
		}
		}catch(Exception ex){
			                  errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.coolgenerror", "An unexpected error happened.Please try again"));
		                      addErrors(request,errors);
		                      addExamPeriod(resultsForm,request);
		                      return mapping.findForward("display");
		}
		        if(resultsForm.getSelectResult()==null){
		        	errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.coolgenerror", "An unexpected error happened  while accessing Exam Result details.Please try again"));
                    addErrors(request,errors);
               }
		return mapping.findForward("displayDetails");
		
	}
	
	private void addExamPeriod(ResultsForm resultsForm,HttpServletRequest request){
		                 ResultsDAO dao = new ResultsDAO();
			             List examPeriods = dao.getExamPeriods();
			             Iterator n = examPeriods.iterator();
			             while (n.hasNext()) {
				              Xamprd xamprd = (Xamprd) n.next();
				              if (xamprd.getCode().equals(resultsForm.getExamPeriod())) {
					                 resultsForm.setXamprd(xamprd);
				              }
			            }
			            request.setAttribute("examPeriods", examPeriods);
	}
    public ActionForward unspecified(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response){

		log.info("DisplayResults: unspecified method call -no value for parameter in request");

		return mapping.findForward("home");

	}

}