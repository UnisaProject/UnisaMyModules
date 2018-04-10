package za.ac.unisa.lms.tools.assessmentcriteria.actions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.mail.internet.InternetAddress;
import javax.management.openmbean.OpenDataException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.LookupDispatchAction;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.tool.api.ToolManager;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserDirectoryService;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.email.api.EmailService;
import org.sakaiproject.event.api.UsageSession;
import org.sakaiproject.event.api.EventTrackingService;
import org.sakaiproject.event.api.UsageSessionService;

import sun.util.calendar.CalendarUtils;
import za.ac.unisa.lms.constants.EventTrackingTypes;
import za.ac.unisa.lms.dao.Gencod;
import za.ac.unisa.lms.dao.StudentSystemGeneralDAO;
import za.ac.unisa.lms.dao.general.UserDAO;
import za.ac.unisa.lms.domain.general.Person;
import za.ac.unisa.lms.dao.general.DepartmentDAO;
import za.ac.unisa.lms.domain.general.Department;
import za.ac.unisa.lms.dao.assessmentCriteria.AssessmentCriteriaDAO;
import za.ac.unisa.lms.domain.assessmentCriteria.*;
import za.ac.unisa.lms.tools.assessmentcriteria.forms.*;
import za.ac.unisa.lms.tools.assessmentcriteria.DAO.*;
import za.ac.unisa.utils.CoursePeriod;
import za.ac.unisa.utils.CoursePeriodLookup;
import Saacq01h.Abean.*;

public class AssessmentCriteriaAction extends LookupDispatchAction{
	
	private SessionManager sessionManager;
	private UserDirectoryService userDirectoryService;
	private ToolManager toolManager;
	private EmailService emailService;
	private EventTrackingService eventTrackingService;
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

	@Override
	protected Map getKeyMethodMap() {
		// TODO Auto-generated method stub
		Map map = new HashMap();
		map.put("initial", "initial");
		map.put("displayRequestAuthorisation", "displayRequestAuthorisation");
		map.put("displayCancelAuthorisationRequest", "displayCancelAuthorisationRequest");
		map.put("displayAssignment", "displayAssignment");
		map.put("button.continue","nextStep");
		map.put("button.back", "prevStep");
		map.put("button.cancel", "cancel");
		map.put("button.no", "cancel");
		map.put("button.yes", "saveData");
		map.put("button.add", "addAssignment");
		map.put("button.add.survey", "addSurvey");
		map.put("button.remove", "deleteAssignments");
		map.put("button.save", "saveData");
		map.put("button.request", "saveData");
		map.put("saveData","saveData");
		map.put("button.moreLanguage", "addLanguageInputField");
		map.put("button.moreFormats", "addFileFormatInputField");
		return map;
	}
	
	public ActionForward addLanguageInputField(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		ActionMessages messages = new ActionMessages();
		AssessmentCriteriaForm assessmentCritForm = (AssessmentCriteriaForm) form;
		
		request.setAttribute("dueDateYear", request.getParameter("yearSelect"));
		request.setAttribute("dueDateMonth", request.getParameter("monthSelect"));
		request.setAttribute("dueDateDay", request.getParameter("daySelect"));
		
		request.setAttribute("releaseDateYear", "0");
		request.setAttribute("releaseDateMonth", "0");
		request.setAttribute("releaseDateDay", "0");
		
		//Get month list
		List monthList = new ArrayList();
		StudentSystemGeneralDAO dao = new StudentSystemGeneralDAO();
		monthList = new ArrayList();
		dao = new StudentSystemGeneralDAO();
		for (int i=0; i < dao.getGenCodes((short)2,0).size(); i++) {
			monthList.add(i, (Gencod)(dao.getGenCodes((short)2,0).get(i)));
		}
		request.setAttribute("monthList", monthList);
		
		List otherLanguages = new ArrayList<String>();
		String language = "";
		otherLanguages = assessmentCritForm.getListOtherLanguage();
		otherLanguages.add(language);
		assessmentCritForm.setListOtherLanguage(otherLanguages);		
		
		assessmentCritForm.setResetOnscreenMarkingArrays(true);
		assessmentCritForm.setCurrentPage("onscreenMarking");
		return mapping.findForward("displayOnscreenMarking");
		
	}
	
	public ActionForward addFileFormatInputField(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		ActionMessages messages = new ActionMessages();
		AssessmentCriteriaForm assessmentCritForm = (AssessmentCriteriaForm) form;
		
		request.setAttribute("dueDateYear", request.getParameter("yearSelect"));
		request.setAttribute("dueDateMonth", request.getParameter("monthSelect"));
		request.setAttribute("dueDateDay", request.getParameter("daySelect"));
		
		request.setAttribute("releaseDateYear", "0");
		request.setAttribute("releaseDateMonth", "0");
		request.setAttribute("releaseDateDay", "0");
		
		//Get month list
		List monthList = new ArrayList();
		StudentSystemGeneralDAO dao = new StudentSystemGeneralDAO();
		monthList = new ArrayList();
		dao = new StudentSystemGeneralDAO();
		for (int i=0; i < dao.getGenCodes((short)2,0).size(); i++) {
			monthList.add(i, (Gencod)(dao.getGenCodes((short)2,0).get(i)));
		}
		request.setAttribute("monthList", monthList);				
		
		List otherFileFormats = new ArrayList<FileFormat>();
		FileFormat fileFormat = new FileFormat();
		fileFormat.setExtention("");
		fileFormat.setDescription("");
		otherFileFormats = assessmentCritForm.getListOtherFileFormat();
		otherFileFormats.add(fileFormat);
		assessmentCritForm.setListOtherFileFormat(otherFileFormats);
		
		assessmentCritForm.setResetOnscreenMarkingArrays(true);
		assessmentCritForm.setCurrentPage("onscreenMarking");
		return mapping.findForward("displayOnscreenMarking");
		
	}
	
	public ActionForward saveData(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
		    ActionMessages messages = new ActionMessages();
			AssessmentCriteriaForm assessmentCritForm = (AssessmentCriteriaForm) form;
			usageSessionService = (UsageSessionService)ComponentManager.get(UsageSessionService.class);			
			UsageSession usageSession = usageSessionService.getSession();
			//remove leading and trailing spaces
			assessmentCritForm.getFinalMarkComp().setYearMarkComponent(assessmentCritForm.getFinalMarkComp().getYearMarkComponent().trim());
			assessmentCritForm.getFinalMarkComp().setExamComponent(assessmentCritForm.getFinalMarkComp().getExamComponent().trim());
			assessmentCritForm.getFinalMarkComp().setPortfolioComponent(assessmentCritForm.getFinalMarkComp().getPortfolioComponent().trim());
			if ("assessmentCriteria".equalsIgnoreCase(assessmentCritForm.getCurrentPage())) {
				//save Final Mark Composition data
				//validate Final Mark Composition data
				assessmentCritForm.setFinalMarkComp(validateFinalMarkComp(assessmentCritForm.getFinalMarkComp(),assessmentCritForm.getStudyUnit()));
				AssessmentCriteriaDAO daoAssessCrit = new AssessmentCriteriaDAO();
								
				if (!assessmentCritForm.isSunpdtExists()) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"Final mark composition part of the assessment plan will only be available after the July Senate approval of the Academic Structure."));
				} else{
					if (assessmentCritForm.getFirstExamination().getYear()==null || assessmentCritForm.getFirstExamination().getYear()==0){
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage",
											"First examination period not set for this course. Please contact Examination department to correct period details for this course (012 429 2932)"));
						addErrors(request,messages);
						return mapping.findForward("displayAssessmentCriteria");
					}
				}
				
				if (!isInteger(assessmentCritForm.getFinalMarkComp().getYearMarkComponent())){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"Year Mark component must be numeric"));
				}
				if (!isInteger(assessmentCritForm.getFinalMarkComp().getPortfolioComponent())){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"Non-venue Examination component must be numeric"));
				}
				if (!isInteger(assessmentCritForm.getFinalMarkComp().getExamComponent())){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"Venue Examination component must be numeric"));
				}				
				if (!messages.isEmpty()) {
					addErrors(request,messages);
					return mapping.findForward("displayAssessmentCriteria");
				}
				if (Integer.parseInt(assessmentCritForm.getFinalMarkComp().getYearMarkComponent()) < 0 ||
						Integer.parseInt(assessmentCritForm.getFinalMarkComp().getYearMarkComponent()) > 100)  {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"Year Mark component must be a value from 0 to 100"));	
				}
				if (Integer.parseInt(assessmentCritForm.getFinalMarkComp().getPortfolioComponent()) < 0 ||
						Integer.parseInt(assessmentCritForm.getFinalMarkComp().getPortfolioComponent()) > 100)  {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"Non-venue Examination component must be a value from 0 to 100"));	
				}
				if (Integer.parseInt(assessmentCritForm.getFinalMarkComp().getExamComponent()) < 0 ||
						Integer.parseInt(assessmentCritForm.getFinalMarkComp().getExamComponent()) > 100)  {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"Venue Examination component must be a value from 0 to 100"));	
				}
				if (assessmentCritForm.getExamBase().equalsIgnoreCase("CONT")){
					if (Integer.parseInt(assessmentCritForm.getFinalMarkComp().getYearMarkComponent())<100){
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage",
										"Module is flagged for continuous assessment, year mark component of the final mark composition must be 100%"));
				}							
				}else{
					//Venue and Non Venue Base exam
					if (Integer.parseInt(assessmentCritForm.getFinalMarkComp().getYearMarkComponent()) < 20 ||
							Integer.parseInt(assessmentCritForm.getFinalMarkComp().getYearMarkComponent()) > 49){
						if ((assessmentCritForm.getStudyUnit().getFormalTuitionFlag().equalsIgnoreCase("F")&&
								!assessmentCritForm.getStudyUnit().getResearchFlag().equalsIgnoreCase("N") &&
								(assessmentCritForm.getStudyUnit().getAcademicLevel().equalsIgnoreCase("H")||
								assessmentCritForm.getStudyUnit().getAcademicLevel().equalsIgnoreCase("M")||
								assessmentCritForm.getStudyUnit().getAcademicLevel().equalsIgnoreCase("D")))||
								(assessmentCritForm.getStudyUnit().getFormalTuitionFlag().equalsIgnoreCase("F")&&
										assessmentCritForm.getStudyUnit().getExperiental_duration()>1)||
								assessmentCritForm.getStudyUnit().getFormalTuitionFlag().equalsIgnoreCase("R")||
								assessmentCritForm.getStudyUnit().getFormalTuitionFlag().equalsIgnoreCase("P")||
								assessmentCritForm.getStudyUnit().getFormalTuitionFlag().equalsIgnoreCase("T")||
								assessmentCritForm.getStudyUnit().getFormalTuitionFlag().equalsIgnoreCase("L")||
								assessmentCritForm.getStudyUnit().getFormalTuitionFlag().equalsIgnoreCase("E")){
							//ignore
						}else{
							//messages.add(ActionMessages.GLOBAL_MESSAGE,
							//		new ActionMessage("message.generalmessage",
							//					"Yearmark component must be at least 10 percent"));
							messages.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("message.generalmessage",
											"Year mark component of the final mark composition must be between 20 and 49%. Any value outside this range need Senate approval and must be affected by DSAA."));
						}
					}
				}				
				if (Integer.parseInt(assessmentCritForm.getFinalMarkComp().getYearMarkComponent()) +
						Integer.parseInt(assessmentCritForm.getFinalMarkComp().getPortfolioComponent()) +
						Integer.parseInt(assessmentCritForm.getFinalMarkComp().getExamComponent())!=100) {
							messages.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("message.generalmessage",
												"Final mark composition components must accumulate to 100 percent"));	
						}
				if (Integer.parseInt(assessmentCritForm.getFinalMarkComp().getExamComponent())==0){
					CourseDAO courseDAO = new CourseDAO();
					List examDates = new ArrayList();
					examDates = courseDAO.getExamDates(assessmentCritForm.getFirstExamination().getYear(), assessmentCritForm.getFirstExamination().getPeriod(), assessmentCritForm.getStudyUnit().getCode());
					String examDateString ="";
				
					for (int i=0; i < examDates.size(); i++ ){
						if (!examDates.get(i).toString().equalsIgnoreCase("19030303") &&
								!examDates.get(i).toString().equalsIgnoreCase("19010101")){
								if (examDateString.equalsIgnoreCase("")){
									examDateString = examDates.get(i).toString();
								}else{
									examDateString = examDateString + ", " + examDates.get(i).toString();
								}							    
						}
					}	
					if (!examDateString.equalsIgnoreCase("")){
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage",
											"The final mark composition's, Venue Examination component cannot be 0. The following exam dates are scheduled for this module: " + examDateString + 
											". Please follow up with Adrian Teixeira (012 429 8855) or Wayne Cyster(012 429 2336) to correct."));	
					}
				}
				//Change 2015 BR
				if (assessmentCritForm.getFinalMarkComp().getExamAdmissionMethod()==null || assessmentCritForm.getFinalMarkComp().getExamAdmissionMethod().trim().equalsIgnoreCase("")){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"Please indicate which examination admission method must be used."));
				}else{
					if (!assessmentCritForm.getStudyUnit().getAutoExamAdmission().equalsIgnoreCase("Y")){
						if (assessmentCritForm.getStudyUnit().getAcademicLevel().equalsIgnoreCase("H")||
								assessmentCritForm.getStudyUnit().getAcademicLevel().equalsIgnoreCase("M")||
								assessmentCritForm.getStudyUnit().getAcademicLevel().equalsIgnoreCase("D")||
								assessmentCritForm.getStudyUnit().getFormalTuitionFlag().equalsIgnoreCase("C")){
							if (assessmentCritForm.getFinalMarkComp().getExamAdmissionMethod().equalsIgnoreCase("AM")){
								if (assessmentCritForm.getFinalMarkComp().getExamAdmissionNrAss()==null || assessmentCritForm.getFinalMarkComp().getExamAdmissionNrAss().trim().equalsIgnoreCase("")){
									messages.add(ActionMessages.GLOBAL_MESSAGE,
											new ActionMessage("message.generalmessage",
														"Please indicate the number of assignments to be submitted for examination admission"));
								} else if (!isInteger(assessmentCritForm.getFinalMarkComp().getExamAdmissionNrAss())){
									messages.add(ActionMessages.GLOBAL_MESSAGE,
											new ActionMessage("message.generalmessage",
														"The number of assignments to be submitted for examination admission must be numeric"));
								} else if (Integer.parseInt(assessmentCritForm.getFinalMarkComp().getExamAdmissionNrAss()) <= 1){
									messages.add(ActionMessages.GLOBAL_MESSAGE,
											new ActionMessage("message.generalmessage",
														"The number of assignments to be submitted for examination admission must be greater than 1"));
								}
							}
							if (assessmentCritForm.getFinalMarkComp().getExamAdmissionMethod().equalsIgnoreCase("YM")){
								if (assessmentCritForm.getFinalMarkComp().getExamAdmissionYearMarkSubMin()==null || assessmentCritForm.getFinalMarkComp().getExamAdmissionYearMarkSubMin().equalsIgnoreCase("")) {
									messages.add(ActionMessages.GLOBAL_MESSAGE,
											new ActionMessage("message.generalmessage",
														"Please enter the year mark subminimum to be achieved"));
								} else if (!isInteger(assessmentCritForm.getFinalMarkComp().getExamAdmissionYearMarkSubMin())){
									messages.add(ActionMessages.GLOBAL_MESSAGE,
											new ActionMessage("message.generalmessage",
														"The year mark subminimum to be achieved for examination admission must be numeric"));
								} else if (Integer.parseInt(assessmentCritForm.getFinalMarkComp().getExamAdmissionYearMarkSubMin()) < 1 || 
										Integer.parseInt(assessmentCritForm.getFinalMarkComp().getExamAdmissionYearMarkSubMin()) > 99) {
									messages.add(ActionMessages.GLOBAL_MESSAGE,
											new ActionMessage("message.generalmessage",
														"The year mark subminimum to be achieved, minimum value is 1% and maximum value is 99%"));	
								}
							} 
						}
						if (!assessmentCritForm.getStudyUnit().getAcademicLevel().equalsIgnoreCase("H") && 
								!assessmentCritForm.getStudyUnit().getAcademicLevel().equalsIgnoreCase("M") &&
								!assessmentCritForm.getStudyUnit().getAcademicLevel().equalsIgnoreCase("D") && 
								!assessmentCritForm.getStudyUnit().getFormalTuitionFlag().equalsIgnoreCase("C")){
							if (!assessmentCritForm.getFinalMarkComp().getExamAdmissionMethod().equalsIgnoreCase("A1")){
								messages.add(ActionMessages.GLOBAL_MESSAGE,
										new ActionMessage("message.generalmessage",
													"Please change the Examination Admission method to 'Submission of at least one (any one) assignment'. Only modules at NQF levels 8,9,10 or SLP's may choose alternative methods."));
							}
						}
					}
					
				}
							
				if (!messages.isEmpty()) {
					addErrors(request,messages);
					return mapping.findForward("displayAssessmentCriteria");
				}				
				Saacq01sAssessmentCriteriaMnt op = new Saacq01sAssessmentCriteriaMnt();
				operListener opl = new operListener();
				op.clear();
				
				op.setInCsfClientServerCommunicationsAction("UF");
				op.setInFinalMarkCalculationExamYear(assessmentCritForm.getDummyFirstExamination().getYear());
				op.setInFinalMarkCalculationMkExamPeriod(assessmentCritForm.getDummyFirstExamination().getPeriod());
				op.setInFinalMarkCalculationMkStudyUnitCode(assessmentCritForm.getStudyUnit().getCode());
				op.setInFinalMarkCalculationYearMarkWeight(Float.parseFloat(assessmentCritForm.getFinalMarkComp().getYearMarkComponent()));
				op.setInFinalMarkCalculationExaminationWeight(Float.parseFloat(assessmentCritForm.getFinalMarkComp().getExamComponent()));
				op.setInFinalMarkCalculationPortfolioWeight(Float.parseFloat(assessmentCritForm.getFinalMarkComp().getPortfolioComponent()));
				op.setInFinalMarkCalculationPfSubminimum(Short.parseShort(assessmentCritForm.getFinalMarkComp().getPortfolioSubminimum()));
				op.setInFinalMarkCalculationXamSubminimum(Short.parseShort(assessmentCritForm.getFinalMarkComp().getExamSubminimum()));
				op.setInFinalMarkCalculationYmSubminimum(Short.parseShort(assessmentCritForm.getFinalMarkComp().getYearMarkSubminimum()));
				op.setInFinalMarkCalculationExamAdmMethGc224(assessmentCritForm.getFinalMarkComp().getExamAdmissionMethod());
				if (assessmentCritForm.getFinalMarkComp()!=null && 
						assessmentCritForm.getFinalMarkComp().getExamAdmissionMethod()!=null){
					if	(assessmentCritForm.getFinalMarkComp().getExamAdmissionMethod().equalsIgnoreCase("A1")){
						assessmentCritForm.getFinalMarkComp().setExamAdmissionNrAss("0");
						assessmentCritForm.getFinalMarkComp().setExamAdmissionYearMarkSubMin("0");
					}	
					if	(assessmentCritForm.getFinalMarkComp().getExamAdmissionMethod().equalsIgnoreCase("AM")){
						assessmentCritForm.getFinalMarkComp().setExamAdmissionYearMarkSubMin("0");
					}	
					if	(assessmentCritForm.getFinalMarkComp().getExamAdmissionMethod().equalsIgnoreCase("YM")){
						assessmentCritForm.getFinalMarkComp().setExamAdmissionNrAss("0");
					}	
				}						
				op.setInFinalMarkCalculationExamAdmNrAss(Short.parseShort(assessmentCritForm.getFinalMarkComp().getExamAdmissionNrAss()));
				op.setInFinalMarkCalculationExamAdmYrSubmin(Short.parseShort(assessmentCritForm.getFinalMarkComp().getExamAdmissionYearMarkSubMin()));
				//Set log data
				op.setInAssessmentLogYear(Short.parseShort(assessmentCritForm.getAcademicYear()));
				op.setInAssessmentLogPeriod(Short.parseShort(assessmentCritForm.getSemester()));
				op.setInAssessmentLogMkStudyUnitCode(assessmentCritForm.getStudyUnit().getCode());
				op.setInAssessmentLogUpdatedBy(assessmentCritForm.getNovellUserId());
				op.setInAssessmentLogActionGc53("UPDATE");
				op.setInAssessmentLogComments("Update Final Mark Composition");
				
				op.execute();
				if (opl.getException() != null)
					throw opl.getException();
				if (op.getExitStateType() < 3)
					throw new Exception(op.getExitStateMsg());
				if (op.getOutCsfStringsString500() != "") {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
									op.getOutCsfStringsString500()));	
					addErrors(request,messages);
					return mapping.findForward("displayAssessmentCriteria");
				}
				toolManager = (ToolManager)ComponentManager.get(ToolManager.class);
				eventTrackingService = (EventTrackingService)ComponentManager.get(EventTrackingService.class);					
				eventTrackingService.post(
						eventTrackingService.newEvent(EventTrackingTypes.EVENT_ASSESSMENT_CRITERIA_FINMRK_UPDATE, toolManager.getCurrentPlacement().getContext(), false),usageSession);
			}
			
			if ("mcqMemorandum".equalsIgnoreCase(assessmentCritForm.getCurrentPage())||
					"writtenMarking".equalsIgnoreCase(assessmentCritForm.getCurrentPage())||
					"noMarking".equalsIgnoreCase(assessmentCritForm.getCurrentPage())) {
				
				Saacq01sAssessmentCriteriaMnt op = new Saacq01sAssessmentCriteriaMnt();
				operListener opl = new operListener();
				op.clear();
				
				//save Assignment Data
				//do validation - MCQ memorandum
				if ("mcqMemorandum".equalsIgnoreCase(assessmentCritForm.getCurrentPage())){
					List listDisplayAnswer = new ArrayList();
					String strMemo="";
					String errorMsg="";
					String allAnswersIgnoreQues="Y";
					for (int i=0; i<assessmentCritForm.getAssignment().getListAnswer().size();i++){
						String answer = ((Memo)(assessmentCritForm.getAssignment().getListAnswer().get(i))).getAnswer();
						listDisplayAnswer.add(i,answer);
						if ("".equalsIgnoreCase(answer)) {
							errorMsg="Select a answer for each question.";
							listDisplayAnswer.add(i,"");
						}
						if (!"0".equalsIgnoreCase(answer)){
							allAnswersIgnoreQues="N";
						}
						strMemo = strMemo.trim() + answer.trim();						
					}
					assessmentCritForm.setListDisplayAnswer(listDisplayAnswer);
					if (!"".equalsIgnoreCase(errorMsg)){
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage",
										errorMsg));
					}
					if(allAnswersIgnoreQues.equalsIgnoreCase("Y") && !assessmentCritForm.isSurvey()){
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage",
										"Not all the answers can be set to Ignore question."));
					}
					if (!messages.isEmpty()) {
						addErrors(request,messages);
						assessmentCritForm.setCurrentPage("mcqMemorandum");
						return mapping.findForward("displayMCQMemorandum");
					}
					//set memorandum data (stumra)
					op.setInStudentMarkReadingSheetAnswers(strMemo);
					op.setInUniqueAssignmentNrOfQuestions(Short.parseShort(assessmentCritForm.getAssignment().getNumberQuestions()));
					op.setInUniqueAssignmentNegativeMarkFactor(Short.parseShort(assessmentCritForm.getAssignment().getNegativeMarkingFactor()));
					op.setInUniqueAssignmentStuSpecifyLang("N");
					op.setInUniqueAssignmentFileReleaseDate(null);
					op.setInUniqueAssignmentPfOpenDate(null);
					op.setInUniqueAssignmentPfOpenTime(null);
					if (assessmentCritForm.getAssignment().getGroup().equalsIgnoreCase("F") &&
							!assessmentCritForm.getAssignment().getType().equalsIgnoreCase("T") &&
							(assessmentCritForm.getFinalMarkComp().getExamAdmissionMethod().equalsIgnoreCase("AM") ||
							(assessmentCritForm.getFinalMarkComp().getExamAdmissionMethod().equalsIgnoreCase("YM") &&
							assessmentCritForm.getYearMarkContributionYesNoFlag().equalsIgnoreCase("Y")))){
						Calendar calendar = Calendar.getInstance();
						SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
						Date finalSubmissionDate = formatter.parse(assessmentCritForm.getExamAdmissionDate());						
						calendar.setTime(finalSubmissionDate);
						calendar.add(Calendar.DATE, -1);
						op.setInUniqueAssignmentFinalSubmitDate(calendar);						
					}else{
						op.setInUniqueAssignmentFinalSubmitDate(null);
					}
					op.setInUniqueAssignmentReleaseResults("Y");
				}				
				if (assessmentCritForm.getCurrentPage().equalsIgnoreCase("writtenMarking")){
					//do validation - markers
					//remove leading and trailing spaces
					for (int i=0; i < assessmentCritForm.getAssignment().getListMarkers().size(); i++) {
						Marker marker = new Marker();
						marker = (Marker)(assessmentCritForm.getAssignment().getListMarkers().get(i));
						marker.setMarkPercentage(marker.getMarkPercentage().trim());
						if (marker.getMarkPercentage().equalsIgnoreCase("")){
							marker.setMarkPercentage("0");
						}
						assessmentCritForm.getAssignment().getListMarkers().set(i, marker);
					}
					//mark percentage must be numeric
					for (int i=0; i < assessmentCritForm.getAssignment().getListMarkers().size(); i++) {
						Marker marker = new Marker();
						marker = (Marker)assessmentCritForm.getAssignment().getListMarkers().get(i);
						if (!isInteger(marker.getMarkPercentage())){
							messages.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("message.generalmessage",
											"Mark percentage must be numeric."));
							i = assessmentCritForm.getAssignment().getListMarkers().size();
						}
						
					}						
					if (!messages.isEmpty()) {
						addErrors(request,messages);
						assessmentCritForm.setCurrentPage("writtenMarking");
						return mapping.findForward("displayWrittenMarkingDetails");
					}
					//inactive marker may not have a mark percentage > 0
					String markPercentageAssignToInactiveLecturer="N";
					String markedLanguageToInactiveLecturer="N";
					Integer markPercentageTotal=0;
					for (int i=0; i < assessmentCritForm.getAssignment().getListMarkers().size(); i++) {
						Marker marker = (Marker)(assessmentCritForm.getAssignment().getListMarkers()).get(i);
						if (Integer.parseInt(marker.getMarkPercentage()) > 0 && 
								marker.getStatus().equalsIgnoreCase("inactive")){
							markPercentageAssignToInactiveLecturer="Y";
						}
						//remove Language selection from marker if markpercentage set to 0
						if (Integer.parseInt(marker.getMarkPercentage())==0){
							marker.setLanguageSelection(null);
						}
						if (assessmentCritForm.getAssignment().getStudentSpecifyLang()!=null 
								&& assessmentCritForm.getAssignment().getStudentSpecifyLang().equalsIgnoreCase("Y")
								&& marker.getLanguageSelection()!=null && marker.getLanguageSelection().length>0){
							if (Integer.parseInt(marker.getMarkPercentage()) == 0 || 
									marker.getStatus().equalsIgnoreCase("inactive")){
								markedLanguageToInactiveLecturer="Y";
							}
						}
						if (((Marker)(assessmentCritForm.getAssignment().getListMarkers().get(i))).getStatus().equalsIgnoreCase("active")){
							markPercentageTotal = markPercentageTotal + Integer.parseInt(((Marker)(assessmentCritForm.getAssignment().getListMarkers().get(i))).getMarkPercentage());
						}
					}
					if ("Y".equalsIgnoreCase(markPercentageAssignToInactiveLecturer)){
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage",
										"A mark percentage may not be assigned to an inactive marker"));
					}
					if ("Y".equalsIgnoreCase(markedLanguageToInactiveLecturer)){
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage",
										"Marking languages may not be assigned to an inactive marker or a marker with no mark percentage."));
					}
					if (markPercentageTotal > 100){
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage",
										"Active markers mark percentage exceeds 100%"));
					}
					if (assessmentCritForm.getAssignment().getStudentSpecifyLang()!=null 
							&& assessmentCritForm.getAssignment().getStudentSpecifyLang().equalsIgnoreCase("Y")){
						for (int i=0; i < assessmentCritForm.getAssignment().getListMarkers().size(); i++) {
							Marker marker = new Marker();
							marker = (Marker)assessmentCritForm.getAssignment().getListMarkers().get(i);
							if (Integer.parseInt(marker.getMarkPercentage())>0){
								//Johanet - change && to or in if below
								if (marker.getLanguageSelection()==null || marker.getLanguageSelection().length==0){
									messages.add(ActionMessages.GLOBAL_MESSAGE,
											new ActionMessage("message.generalmessage",
													"Select at least one marking language per marker assigned a mark percentage."));
									i = assessmentCritForm.getAssignment().getListMarkers().size();
								}
							}
						}
					}
					
					if (!messages.isEmpty()) {
						addErrors(request,messages);
						assessmentCritForm.setCurrentPage("writtenMarking");
						return mapping.findForward("displayWrittenMarkingDetails");
					}
					//assignment formats
					for (int i=0;i < assessmentCritForm.getAssignment().getListFormat().size();i++){
						FileFormat fileFormat = new FileFormat();
						fileFormat = (FileFormat)assessmentCritForm.getAssignment().getListFormat().get(i);
						op.setInGrpOnlineAssignmentFormatsMkStudyUnitCode(i, assessmentCritForm.getStudyUnit().getCode().trim());
						op.setInGrpOnlineAssignmentFormatsExtention(i, fileFormat.getExtention().trim());
						op.setInGrpOnlineAssignmentFormatsDescription(i, fileFormat.getDescription().trim());
					}
					op.setInAssFormatGrpCount(Short.parseShort(String.valueOf(assessmentCritForm.getAssignment().getListFormat().size())));
					//assignment languages
					for(int i=0;i <assessmentCritForm.getAssignment().getListLanguage().size(); i++){
						SubmissionLanguage language = new SubmissionLanguage();
						language = (SubmissionLanguage)assessmentCritForm.getAssignment().getListLanguage().get(i);
						op.setInGrpOnlineAssignmentLangLanguageGc203(i, language.getLangangeGc203().trim());
						if (language.getLangangeGc203().equalsIgnoreCase("OTHER")){
							op.setInGrpOnlineAssignmentLangOtherLangDesc(i, language.getOtherLangDesc().trim());
						}else{
							op.setInGrpOnlineAssignmentLangOtherLangDesc(i, " ");
						}
											
					}
					
					op.setInAssLanguageGrpCount(Short.parseShort(String.valueOf(assessmentCritForm.getAssignment().getListLanguage().size())));
					
					int countMarkersLang = 0;
					for (int i=0; i < assessmentCritForm.getAssignment().getListMarkers().size(); i++) {
						Marker marker = new Marker();
						marker = (Marker)assessmentCritForm.getAssignment().getListMarkers().get(i);
						op.setInMarkerGrpAssignmentMarkingDetailsMarkPercentage(i, Short.parseShort(marker.getMarkPercentage()));
						op.setInMarkerGrpAssignmentMarkingDetailsMkLecturerNr(i, Integer.parseInt(marker.getPerson().getPersonnelNumber()));
						if (marker.getLanguageSelection()!=null && marker.getLanguageSelection().length > 0){							
							for (int j=0; j < marker.getLanguageSelection().length; j++){
								String markerLang = (String) marker.getLanguageSelection()[j];
								boolean found = false;
								Gencod gencod = new Gencod();
								for (int k=0; k < assessmentCritForm.getListLanguage().size(); k++){
									gencod = new Gencod();
									gencod = (Gencod)assessmentCritForm.getListLanguage().get(k);
									if (gencod.getEngDescription().equalsIgnoreCase(markerLang)){
										found = true;
										k=assessmentCritForm.getListLanguage().size();
									}
								}								
								op.setInGrpAssignmentMarkerLangMkLecturerNr(countMarkersLang, Integer.parseInt(marker.getPerson().getPersonnelNumber()));
								if (found){
									op.setInGrpAssignmentMarkerLangLanguageGc203(countMarkersLang, gencod.getCode().trim());	
									op.setInGrpAssignmentMarkerLangOtherLangDesc(countMarkersLang, " ");
								}else{
									op.setInGrpAssignmentMarkerLangLanguageGc203(countMarkersLang, "OTHER");	
									op.setInGrpAssignmentMarkerLangOtherLangDesc(countMarkersLang, markerLang.trim());
								}	
								countMarkersLang = countMarkersLang + 1;
							}				
						}
					}
					op.setInAssMarkerLanguageGrpCount(Short.parseShort(String.valueOf(countMarkersLang)));
					op.setInMarkerGroupCount(Short.parseShort(String.valueOf(assessmentCritForm.getAssignment().getListMarkers().size())));
					op.setInUniqueAssignmentNrOfQuestions(Short.parseShort("0"));
					op.setInUniqueAssignmentNegativeMarkFactor(Short.parseShort("0"));
					op.setInUniqueAssignmentStuSpecifyLang(assessmentCritForm.getAssignment().getStudentSpecifyLang());
					op.setInUniqueAssignmentFileReleaseDate(null);
					op.setInUniqueAssignmentPfOpenDate(null);
					op.setInUniqueAssignmentPfOpenTime(null);
					//Change BRD 2016	
					op.setInUniqueAssignmentFinalSubmitDate(null); 
					op.setInUniqueAssignmentFileSizeLimit(Short.parseShort("0"));
					if (assessmentCritForm.getAssignment().getGroup().equalsIgnoreCase("S")) {
							if (assessmentCritForm.getStudyUnit().getPostGraduateFlag().equalsIgnoreCase("Y") && 
									assessmentCritForm.getStudyUnit().getResearchFlag().equalsIgnoreCase("P")){
								op.setInUniqueAssignmentReleaseResults(assessmentCritForm.getAssignment().getReleaseFlag());
							}else{
								op.setInUniqueAssignmentReleaseResults("N");
							}						
					}else{
						op.setInUniqueAssignmentReleaseResults("Y");
					}					
				} //end Written marking
				if (assessmentCritForm.getCurrentPage().equalsIgnoreCase("noMarking")){
					op.setInUniqueAssignmentStuSpecifyLang("N");
					op.setInUniqueAssignmentFileReleaseDate(null);
					op.setInUniqueAssignmentPfOpenDate(null);
					op.setInUniqueAssignmentPfOpenTime(null);
					if (assessmentCritForm.getAssignment().getGroup().equalsIgnoreCase("S")){
						op.setInUniqueAssignmentReleaseResults("N");
					}else{
						op.setInUniqueAssignmentReleaseResults("Y");
					}	
					//2018 Changes - default finalsubmission date for module with exam admission on YM or AM to 1 day before exam admission date
					if (assessmentCritForm.getAssignment().getGroup().equalsIgnoreCase("F") &&
							!assessmentCritForm.getAssignment().getType().equalsIgnoreCase("T") &&
							(assessmentCritForm.getFinalMarkComp().getExamAdmissionMethod().equalsIgnoreCase("AM") ||
							(assessmentCritForm.getFinalMarkComp().getExamAdmissionMethod().equalsIgnoreCase("YM") &&
							assessmentCritForm.getYearMarkContributionYesNoFlag().equalsIgnoreCase("Y")))){
						Calendar calendar = Calendar.getInstance();
						SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
						Date finalSubmissionDate = formatter.parse(assessmentCritForm.getExamAdmissionDate());						
						calendar.setTime(finalSubmissionDate);
						calendar.add(Calendar.DATE, -1);
						op.setInUniqueAssignmentFinalSubmitDate(calendar);						
					}else{
						op.setInUniqueAssignmentFinalSubmitDate(null);
					}						
					op.setInUniqueAssignmentFileSizeLimit(Short.parseShort("0"));
				} //end no Marking
				
				if (assessmentCritForm.getAssignmentAction().equalsIgnoreCase("add")){
					op.setInCsfClientServerCommunicationsAction("CA");
				} else {
					op.setInCsfClientServerCommunicationsAction("UA");
					op.setInUniqueAssignmentUniqueNr(Integer.parseInt(assessmentCritForm.getAssignment().getUniqueNumber()));
				}
				
				//Set assignment data
				op.setInUniqueAssignmentAssignmentNr(Short.parseShort(assessmentCritForm.getAssignment().getNumber()));
				op.setInUniqueAssignmentYear(Short.parseShort(String.valueOf(assessmentCritForm.getDummyAcademicYear())));
				op.setInUniqueAssignmentPeriod(Short.parseShort(assessmentCritForm.getSemester()));
				op.setInUniqueAssignmentMkStudyUnitCode(assessmentCritForm.getStudyUnit().getCode());
				if (assessmentCritForm.getAssignment().getFormat().equalsIgnoreCase("BL")||
						assessmentCritForm.getAssignment().getFormat().equalsIgnoreCase("SA") ||
						assessmentCritForm.getAssignment().getFormat().equalsIgnoreCase("DF") ||
						assessmentCritForm.getAssignment().getFormat().equalsIgnoreCase("XA") ||
						assessmentCritForm.getAssignment().getFormat().equalsIgnoreCase("MS") ||
						assessmentCritForm.getAssignment().getFormat().equalsIgnoreCase("OR")){
					op.setInUniqueAssignmentType("H");
					op.setInUniqueAssignmentOnlineTypeGc176(assessmentCritForm.getAssignment().getFormat());
				}else{
					op.setInUniqueAssignmentType(assessmentCritForm.getAssignment().getFormat());
				}
				//Change BRD 2016	
				op.setInUniqueAssignmentAssessGroupGc230(assessmentCritForm.getAssignment().getGroup());
				op.setInUniqueAssignmentCompulsory(assessmentCritForm.getAssignment().getSubsidyAssignment());
				Calendar calendar = Calendar.getInstance();
				SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd");
				Date dueDate = dateFormatter.parse(assessmentCritForm.getAssignment().getDueDate());
				calendar.setTime(dueDate);
				op.setInUniqueAssignmentClosingDate(calendar);
				op.setInUniqueAssignmentOnscreenMarkFlag(assessmentCritForm.getAssignment().getOnscreenMarkFlag());
				
				if (assessmentCritForm.getAssignment().getOnscreenMarkFlag().equalsIgnoreCase("Y")){					
					if (assessmentCritForm.getAssignment().getFileReleaseDate().equalsIgnoreCase("")){
						op.setInUniqueAssignmentFileReleaseDate(null);
					}else{
						Date fileReleaseDate = dateFormatter.parse(assessmentCritForm.getAssignment().getFileReleaseDate());
						calendar.setTime(fileReleaseDate);
						op.setInUniqueAssignmentFileReleaseDate(calendar);
					}
					//Change BRD 2017
					op.setInUniqueAssignmentPfOpenDate(null);
					op.setInUniqueAssignmentPfOpenTime(null);
					op.setInUniqueAssignmentFinalSubmitDate(null);
					if (!assessmentCritForm.getSubmissionDateIndicator().equalsIgnoreCase("na")) {
						if (assessmentCritForm.getSubmissionDateIndicator().equalsIgnoreCase("date")){								
							if (!assessmentCritForm.getAssignment().getPfOpenDate().equalsIgnoreCase("")){
								Date pfOpenDate = dateFormatter.parse(assessmentCritForm.getAssignment().getPfOpenDate());
								calendar.setTime(pfOpenDate);
								op.setInUniqueAssignmentPfOpenDate(calendar);
								op.setInUniqueAssignmentPfOpenTime(null);
							}
							if(!assessmentCritForm.getAssignment().getFinalSubmissionDate().equalsIgnoreCase("")){
								Date finalSubDate = dateFormatter.parse(assessmentCritForm.getAssignment().getFinalSubmissionDate());
								calendar.setTime(finalSubDate);
								op.setInUniqueAssignmentFinalSubmitDate(calendar);
							}
							
						}
						if (assessmentCritForm.getSubmissionDateIndicator().equalsIgnoreCase("dateTime")){
							SimpleDateFormat dateTimeformatter = new SimpleDateFormat("yyyyMMdd HH:mm");
							if (!assessmentCritForm.getAssignment().getPfOpenDate().equalsIgnoreCase("")){
								Date pfOpenDate = dateTimeformatter.parse(assessmentCritForm.getAssignment().getPfOpenDate());
								calendar.setTime(pfOpenDate);
								op.setInUniqueAssignmentPfOpenDate(calendar);
								op.setInUniqueAssignmentPfOpenTime(calendar);
								}
							if(!assessmentCritForm.getAssignment().getFinalSubmissionDate().equalsIgnoreCase("")){
								Date finalSubDate = dateTimeformatter.parse(assessmentCritForm.getAssignment().getFinalSubmissionDate());
								calendar.setTime(finalSubDate);
								op.setInUniqueAssignmentFinalSubmitDate(calendar);
							}
						}	
					}
					
					//Change BRD 2016
					if (assessmentCritForm.getAssignment().getMaxFileSize()==null 
							|| assessmentCritForm.getAssignment().getMaxFileSize().equalsIgnoreCase("")){
						op.setInUniqueAssignmentFileSizeLimit(Short.parseShort("0"));
					}else{
						op.setInUniqueAssignmentFileSizeLimit(Short.parseShort(assessmentCritForm.getAssignment().getMaxFileSize()));
					}
					
				}
				//Set year mark data
				op.setInYearMarkCalculationStudentAssignmentNr(assessmentCritForm.getAssignment().getNumber());
				op.setInYearMarkCalculationNormalWeight(Float.parseFloat(assessmentCritForm.getAssignment().getNormalWeight()));
				op.setInYearMarkCalculationRepeatWeight(Float.parseFloat(assessmentCritForm.getAssignment().getRepeatWeight()));
				op.setInYearMarkCalculationSupplWeight(Integer.parseInt(assessmentCritForm.getAssignment().getAegrotatWeight()));
				if (assessmentCritForm.getAssignment().getOptionality().equalsIgnoreCase("")){
					op.setInYearMarkCalculationOptionalityGc3("M");
				}else{
					op.setInYearMarkCalculationOptionalityGc3(assessmentCritForm.getAssignment().getOptionality());
				}
				if (assessmentCritForm.getAssignment().getType().equalsIgnoreCase("PF") ||
						assessmentCritForm.getAssignment().getType().equalsIgnoreCase("PJ") ||
						assessmentCritForm.getAssignment().getType().equalsIgnoreCase("PC")){
					op.setInYearMarkCalculationType("P");
					if (assessmentCritForm.getAssignment().getType().equalsIgnoreCase("PF")){
						op.setInYearMarkCalculationSubTypeGc206("PORTFOLIO");
					}
					if (assessmentCritForm.getAssignment().getType().equalsIgnoreCase("PJ")){
						op.setInYearMarkCalculationSubTypeGc206("PROJECT");
					}
					if (assessmentCritForm.getAssignment().getType().equalsIgnoreCase("PC")){
						op.setInYearMarkCalculationSubTypeGc206("PRACTICAL");
					}
				
				}else{
					op.setInYearMarkCalculationType(assessmentCritForm.getAssignment().getType());
					op.setInYearMarkCalculationSubTypeGc206("");					
				}				
				//Self assessment assignment
				if (op.getInYearMarkCalculationOptionalityGc3().equalsIgnoreCase("O")){
					op.setInUniqueAssignmentCreditSystem(Short.parseShort("5"));
				}else{					
					if (assessmentCritForm.isSurvey()){
						op.setInUniqueAssignmentCreditSystem(Short.parseShort("5"));
					}else{
						op.setInUniqueAssignmentCreditSystem(Short.parseShort("1"));
					}					
				}	
				//Changes 2018 - On the Assessment Plan, set flag to "Block myUnisa Submission" when users create either formative or summative assessments for any module linked to the SBL
				//Set block myUnisa Submission
				if (assessmentCritForm.getStudyUnit()!=null && 
						assessmentCritForm.getStudyUnit().getDepartment()!= null &&
						assessmentCritForm.getStudyUnit().getDepartment().equalsIgnoreCase("5")){
					op.setInUniqueAssignmentBlockEsol("Y");
					op.setInUniqueAssignmentBlockSol("Y");
				}else{
					op.setInUniqueAssignmentBlockEsol("N");
					op.setInUniqueAssignmentBlockSol("N");
				}
				
				//Set log data
				op.setInAssessmentLogYear(Short.parseShort(assessmentCritForm.getAcademicYear()));
				op.setInAssessmentLogPeriod(Short.parseShort(assessmentCritForm.getSemester()));
				op.setInAssessmentLogMkStudyUnitCode(assessmentCritForm.getStudyUnit().getCode());
				op.setInAssessmentLogUpdatedBy(assessmentCritForm.getNovellUserId());
				op.setInAssessmentLogActionGc53("UPDATE");
				String comment="";
				if (assessmentCritForm.getAssignmentAction().equalsIgnoreCase("add")){
					comment= "Create assignment: " + assessmentCritForm.getAssignment().getNumber();
				} else {
					comment= "Update assignment: " + assessmentCritForm.getAssignment().getNumber();
				}
				op.setInAssessmentLogComments(comment);
				
				op.execute();
				if (opl.getException() != null)
					throw opl.getException();
				if (op.getExitStateType() < 3)
					throw new Exception(op.getExitStateMsg());
				if (op.getOutCsfStringsString500() != "") {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
									op.getOutCsfStringsString500()));	
					addErrors(request,messages);
					if ("mcqMemorandum".equalsIgnoreCase(assessmentCritForm.getCurrentPage())){
						assessmentCritForm.setCurrentPage("mcqMemorandum");
						return mapping.findForward("displayMCQMemorandum");
					}else if ("writtenMarking".equalsIgnoreCase(assessmentCritForm.getCurrentPage())){
						assessmentCritForm.setCurrentPage("writtenMarking");
						return mapping.findForward("displayWrittenMarkingDetails");
					}else{
						assessmentCritForm.setCurrentPage("noMarking");
						return mapping.findForward("displayNoMarkingDetails");
					}
				}	
				eventTrackingService = (EventTrackingService)ComponentManager.get(EventTrackingService.class);
	        	toolManager = (ToolManager)ComponentManager.get(ToolManager.class);
				if (assessmentCritForm.getAssignmentAction().equalsIgnoreCase("add")){
					eventTrackingService.post(
							eventTrackingService.newEvent(EventTrackingTypes.EVENT_ASSESSMENT_CRITERIA_ASS_ADD, toolManager.getCurrentPlacement().getContext(), false),usageSession);
				} else {
					eventTrackingService.post(
							eventTrackingService.newEvent(EventTrackingTypes.EVENT_ASSESSMENT_CRITERIA_ASS_UPDATE, toolManager.getCurrentPlacement().getContext(), false),usageSession);
				}				
			}
			
			if ("requestAuthorisation".equalsIgnoreCase(assessmentCritForm.getCurrentPage())){
				//validate Request Authorisation data
				//Final mark composition record must exist
				//at least 2 Mandatory assignments or 1 mandatory assignment and 2 Elective assignments
				//inactive marker may not have a mark percentage assigned
				
				List codList = new ArrayList();
				codList.add(assessmentCritForm.getDepartment().getCod());
				request.setAttribute("codList",codList);
				//person was selected to notify that authorisation request was submitted
				if (assessmentCritForm.getSelectedAuthoriser()==null){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"Please select a person to notify that an authorisation request was submitted for this course."));
				}
				//get final mark composition
				FinalMarkComposition finalMarkComp = new FinalMarkComposition();
				AssessmentCriteriaDAO daoAssessCrit = new AssessmentCriteriaDAO();
				finalMarkComp = daoAssessCrit.getFinalMarkComposition(assessmentCritForm.getDummyFirstExamination().getYear(), assessmentCritForm.getFirstExamination().getPeriod(), assessmentCritForm.getStudyUnit().getCode());
				if (finalMarkComp.getExamYear()==null){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"The assessment plan must include Final mark composition and examination admission method information before authorisation can be requested."));
				}else {
					if (assessmentCritForm.getExamBase().equalsIgnoreCase("CONT")){
						if (Integer.parseInt(assessmentCritForm.getFinalMarkComp().getYearMarkComponent())!=100){
							messages.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("message.generalmessage",
											"Module is flagged for continuous assessment, year mark component of the final mark composition must be 100%"));
						}							
					} 
					if ((assessmentCritForm.getExamBase().equalsIgnoreCase("VENUE") || assessmentCritForm.getExamBase().equalsIgnoreCase("NONVENUE")) &&
							(Integer.parseInt(assessmentCritForm.getFinalMarkComp().getYearMarkComponent()) < 20 ||
							Integer.parseInt(assessmentCritForm.getFinalMarkComp().getYearMarkComponent()) > 49)){
						if ((assessmentCritForm.getStudyUnit().getFormalTuitionFlag().equalsIgnoreCase("F")&&
								!assessmentCritForm.getStudyUnit().getResearchFlag().equalsIgnoreCase("N") &&
								(assessmentCritForm.getStudyUnit().getAcademicLevel().equalsIgnoreCase("H")||
								assessmentCritForm.getStudyUnit().getAcademicLevel().equalsIgnoreCase("M")||
								assessmentCritForm.getStudyUnit().getAcademicLevel().equalsIgnoreCase("D")))||
								(assessmentCritForm.getStudyUnit().getFormalTuitionFlag().equalsIgnoreCase("F")&&
										assessmentCritForm.getStudyUnit().getExperiental_duration()>1)||
								assessmentCritForm.getStudyUnit().getFormalTuitionFlag().equalsIgnoreCase("R")||
								assessmentCritForm.getStudyUnit().getFormalTuitionFlag().equalsIgnoreCase("P")||
								assessmentCritForm.getStudyUnit().getFormalTuitionFlag().equalsIgnoreCase("T")||
								assessmentCritForm.getStudyUnit().getFormalTuitionFlag().equalsIgnoreCase("L")||
								assessmentCritForm.getStudyUnit().getFormalTuitionFlag().equalsIgnoreCase("E")){
							//ignore
						}else{
							//messages.add(ActionMessages.GLOBAL_MESSAGE,
							//		new ActionMessage("message.generalmessage",
							//					"Yearmark component must be at least 10 percent"));
							messages.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("message.generalmessage",
											"Year mark component of the final mark composition must be between 20 and 49%. Any value outside this range need Senate approval and must be affected by DSAA."));
						}
					}
					//Johanet - change 2015 BR
					if ((assessmentCritForm.getStudyUnit().getAutoExamAdmission().equalsIgnoreCase("Y")) || 
							(!assessmentCritForm.getStudyUnit().getFormalTuitionFlag().equalsIgnoreCase("C") && 
							!assessmentCritForm.getStudyUnit().getAcademicLevel().equalsIgnoreCase("H") &&
							!assessmentCritForm.getStudyUnit().getAcademicLevel().equalsIgnoreCase("M") &&
							!assessmentCritForm.getStudyUnit().getAcademicLevel().equalsIgnoreCase("D"))){
						if (assessmentCritForm.getFinalMarkComp().getExamAdmissionMethod().equalsIgnoreCase("AM") ||
								assessmentCritForm.getFinalMarkComp().getExamAdmissionMethod().equalsIgnoreCase("YM")){
							messages.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("message.generalmessage",
												"The criteria that determine what examination admission method is allowed had changed, please ensure that the correct examination admission method is selected and click on the 'Save' button, before requesting authorisation."));
						}
					}
				}
				
				if (!messages.isEmpty()) {
					addErrors(request,messages);
					return mapping.findForward("requestAuthorisation");
				}	
				int nrMandatoryAss = 0;
				int nrFormativeAss =0;
				int nrElectiveAss=0;
				int nrAss=0;
				int nrOfAssignmentsBeforeSecondDueDate=0;
				int nrFormativeAssAfterExamAdmDate=0;
				int nrFormativeAssFinalSubAfterExamAdmDate=0;
				int nrOfPortfolios=0;
				int totalAssignmentNormalWeight=0;
				int totalAssignmentRepeatWeight=0;
				int totalAssignmentAegrotatWeight=0;
				int totalPortfolioNormalWeight=0;
				int totalPortfolioRepeatWeight=0;
				int totalPortfolioAegrotatWeight=0;
				boolean existAssignmentwithNormalWeights=false;
				boolean existAssignmentwithRepeatWeights=false;
				boolean existAssignmentwithAegrotatWeights=false;
				boolean existAssignmentBeforeFirstDueDate=false;
				boolean portfolioBeforeFirstDueDate=false;				
				Calendar calendar = Calendar.getInstance();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
				Date firstControlDate = formatter.parse(assessmentCritForm.getFirstAssDueControlDate());
				Date secondControlDate = formatter.parse(assessmentCritForm.getSecondAssDueControlDate());
				Date emptyDate = formatter.parse("00010101 00:00");
				//20160622 - change for 2017
				//Verify that all due dates for formative assessments with admission system AM(Submission of more than one assignment) are set before exam admission date for that academic period
				//Verify that all due dates for formative assessments with admission system YM(Year mark subminimum achieved) are set at least 7 days before exam admission date for that academic period
				Date examAdmissionDate = formatter.parse(assessmentCritForm.getExamAdmissionDate());
				//20170605 - 2018 Changes
				//if a module chose Admission system AM or YM the cutoff dates must be at least one calendar day earlier than the exam admission date
				Date finalSubmissionVerifyDate = formatter.parse(assessmentCritForm.getExamAdmissionDate());
//				if (assessmentCritForm.getFinalMarkComp().getExamAdmissionMethod().equalsIgnoreCase("AM") ||
//						assessmentCritForm.getFinalMarkComp().getExamAdmissionMethod().equalsIgnoreCase("YM")){
//					calendar.setTime(finalSubmissionVerifyDate);
//					calendar.add(Calendar.DATE, -1);
//					finalSubmissionVerifyDate = calendar.getTime();
//				}
				if (assessmentCritForm.getFinalMarkComp().getExamAdmissionMethod().equalsIgnoreCase("YM")){
					calendar.setTime(examAdmissionDate);
					calendar.add(Calendar.DATE, -6);
					examAdmissionDate = calendar.getTime();
				}
				//20170605 - change for 2018
				//Verify that all due dates for formative assessments with admission system AM(Submission of more than one assignment) are set at least 3 days before exam admission date for that academic period
				//Verify that all due dates for formative assessments with admission system YM(Year mark subminimum achieved) are set at least 7 days before exam admission date for that academic period
				if (assessmentCritForm.getFinalMarkComp().getExamAdmissionMethod().equalsIgnoreCase("AM")){
					calendar.setTime(examAdmissionDate);
					calendar.add(Calendar.DATE, -2);
					examAdmissionDate = calendar.getTime();
				}			
				
				for (int i=0; i < assessmentCritForm.getListAssignment().size();i++){
					Date dueDate = formatter.parse(((Assignment)(assessmentCritForm.getListAssignment().get(i))).getDueDate());		
					calendar.setTime(dueDate);
					
					Integer assNumber = Integer.parseInt((((Assignment)(assessmentCritForm.getListAssignment().get(i))).getNumber()));
					if (assNumber >= 91 && assNumber <= 95){
						//surveys 
					}else{
						if (dueDate.before(firstControlDate) || dueDate.equals(firstControlDate)){
							existAssignmentBeforeFirstDueDate=true;
//							if ("PF".equalsIgnoreCase(((Assignment)(assessmentCritForm.getListAssignment().get(i))).getType()) ||
//									"PJ".equalsIgnoreCase(((Assignment)(assessmentCritForm.getListAssignment().get(i))).getType()) ||
//									"PC".equalsIgnoreCase(((Assignment)(assessmentCritForm.getListAssignment().get(i))).getType())){
							if ("S".equalsIgnoreCase(((Assignment)(assessmentCritForm.getListAssignment().get(i))).getGroup())) {
								portfolioBeforeFirstDueDate=true;
							}
						}
//						if ("PF".equalsIgnoreCase(((Assignment)(assessmentCritForm.getListAssignment().get(i))).getType()) ||
//								"PJ".equalsIgnoreCase(((Assignment)(assessmentCritForm.getListAssignment().get(i))).getType()) ||
//								"PC".equalsIgnoreCase(((Assignment)(assessmentCritForm.getListAssignment().get(i))).getType())){
						if ("S".equalsIgnoreCase(((Assignment)(assessmentCritForm.getListAssignment().get(i))).getGroup())) {
							nrOfPortfolios = nrOfPortfolios + 1;
						}
						if (dueDate.before(secondControlDate) || dueDate.equals(secondControlDate)){
							nrOfAssignmentsBeforeSecondDueDate = nrOfAssignmentsBeforeSecondDueDate + 1;
						}						
						if ((Integer.parseInt(((Assignment)(assessmentCritForm.getListAssignment().get(i))).getNormalWeight())>0)) {
//							if ("PF".equalsIgnoreCase(((Assignment)(assessmentCritForm.getListAssignment().get(i))).getType()) ||
//									"PJ".equalsIgnoreCase(((Assignment)(assessmentCritForm.getListAssignment().get(i))).getType()) ||
//									"PC".equalsIgnoreCase(((Assignment)(assessmentCritForm.getListAssignment().get(i))).getType())){
							if ("S".equalsIgnoreCase(((Assignment)(assessmentCritForm.getListAssignment().get(i))).getGroup())) {
								totalPortfolioNormalWeight = totalPortfolioNormalWeight + Integer.parseInt(((Assignment)(assessmentCritForm.getListAssignment().get(i))).getNormalWeight());
							}else{
								totalAssignmentNormalWeight = totalAssignmentNormalWeight + Integer.parseInt(((Assignment)(assessmentCritForm.getListAssignment().get(i))).getNormalWeight());
							}
							existAssignmentwithNormalWeights=true;
						}
						if ((Integer.parseInt(((Assignment)(assessmentCritForm.getListAssignment().get(i))).getAegrotatWeight())>0)){
//							if ("PF".equalsIgnoreCase(((Assignment)(assessmentCritForm.getListAssignment().get(i))).getType()) ||
//									"PJ".equalsIgnoreCase(((Assignment)(assessmentCritForm.getListAssignment().get(i))).getType()) ||
//									"PC".equalsIgnoreCase(((Assignment)(assessmentCritForm.getListAssignment().get(i))).getType())){
							if ("S".equalsIgnoreCase(((Assignment)(assessmentCritForm.getListAssignment().get(i))).getGroup())) {
								totalPortfolioAegrotatWeight = totalPortfolioAegrotatWeight + Integer.parseInt(((Assignment)(assessmentCritForm.getListAssignment().get(i))).getAegrotatWeight());
							}else{
								totalAssignmentAegrotatWeight = totalAssignmentAegrotatWeight + Integer.parseInt(((Assignment)(assessmentCritForm.getListAssignment().get(i))).getAegrotatWeight());
							}
							existAssignmentwithAegrotatWeights=true;
						}
						if ((Integer.parseInt(((Assignment)(assessmentCritForm.getListAssignment().get(i))).getRepeatWeight())>0)){
//							if ("PF".equalsIgnoreCase(((Assignment)(assessmentCritForm.getListAssignment().get(i))).getType())||
//									"PJ".equalsIgnoreCase(((Assignment)(assessmentCritForm.getListAssignment().get(i))).getType()) ||
//									"PC".equalsIgnoreCase(((Assignment)(assessmentCritForm.getListAssignment().get(i))).getType())){
							if ("S".equalsIgnoreCase(((Assignment)(assessmentCritForm.getListAssignment().get(i))).getGroup())) {
								totalPortfolioRepeatWeight = totalPortfolioRepeatWeight + Integer.parseInt(((Assignment)(assessmentCritForm.getListAssignment().get(i))).getRepeatWeight());
							}else{
								totalAssignmentRepeatWeight = totalAssignmentRepeatWeight + Integer.parseInt(((Assignment)(assessmentCritForm.getListAssignment().get(i))).getRepeatWeight());
							}
							existAssignmentwithRepeatWeights=true;
						}
						if (((Assignment)(assessmentCritForm.getListAssignment().get(i))).getOptionality().equalsIgnoreCase("M")){
							nrMandatoryAss=nrMandatoryAss + 1;
						}
						if (((Assignment)(assessmentCritForm.getListAssignment().get(i))).getOptionality().equalsIgnoreCase("E")){
							nrElectiveAss=nrElectiveAss + 1;
						}
						if (((Assignment)(assessmentCritForm.getListAssignment().get(i))).getGroup().equalsIgnoreCase("F") &&
								!((Assignment)(assessmentCritForm.getListAssignment().get(i))).getType().equalsIgnoreCase("T")){
							nrFormativeAss = nrFormativeAss + 1;							
							if (assessmentCritForm.getFinalMarkComp().getExamAdmissionMethod().equalsIgnoreCase("AM") ||
									(assessmentCritForm.getFinalMarkComp().getExamAdmissionMethod().equalsIgnoreCase("YM") &&
											(Integer.parseInt(((Assignment)(assessmentCritForm.getListAssignment().get(i))).getNormalWeight())>0 ||
													Integer.parseInt(((Assignment)(assessmentCritForm.getListAssignment().get(i))).getRepeatWeight())>0 ||
													Integer.parseInt(((Assignment)(assessmentCritForm.getListAssignment().get(i))).getAegrotatWeight())>0))) {
								if (dueDate.after(examAdmissionDate) || dueDate.equals(examAdmissionDate)){
									nrFormativeAssAfterExamAdmDate = nrFormativeAssAfterExamAdmDate + 1;
								}
								//Only test for onscreen mark final submission cut-off dates default the other dates if exam admission ym or am to 1 day before exam admission  
								//Set final submission default date in gen server SAACQ01H							
								if (((Assignment)(assessmentCritForm.getListAssignment().get(i))).getFinalSubmissionDate()==null ||
											((Assignment)(assessmentCritForm.getListAssignment().get(i))).getFinalSubmissionDate().equalsIgnoreCase("")){
										//if Final submission not filled in, then submission is allowed until exam commence, this is later than the exam admission date
										nrFormativeAssFinalSubAfterExamAdmDate = nrFormativeAssFinalSubAfterExamAdmDate + 1;
									}else{
										Date finalSubmissionDate = formatter.parse(((Assignment)(assessmentCritForm.getListAssignment().get(i))).getFinalSubmissionDate());
										calendar.setTime(finalSubmissionDate);
										if (finalSubmissionDate.after(finalSubmissionVerifyDate) || finalSubmissionDate.compareTo(emptyDate)==0) {
											nrFormativeAssFinalSubAfterExamAdmDate = nrFormativeAssFinalSubAfterExamAdmDate + 1;
										}
									}
							}
							
						}
						nrAss=nrAss + 1;
					}					
				}
				if((assessmentCritForm.getStudyUnit().getFormalTuitionFlag().equalsIgnoreCase("F")&&
						!assessmentCritForm.getStudyUnit().getResearchFlag().equalsIgnoreCase("N") &&
						(assessmentCritForm.getStudyUnit().getAcademicLevel().equalsIgnoreCase("H")||
						assessmentCritForm.getStudyUnit().getAcademicLevel().equalsIgnoreCase("M")||
						assessmentCritForm.getStudyUnit().getAcademicLevel().equalsIgnoreCase("D")))||
						(assessmentCritForm.getStudyUnit().getFormalTuitionFlag().equalsIgnoreCase("F")&&
								assessmentCritForm.getStudyUnit().getExperiental_duration()>1)||
						assessmentCritForm.getStudyUnit().getFormalTuitionFlag().equalsIgnoreCase("R")||
						assessmentCritForm.getStudyUnit().getFormalTuitionFlag().equalsIgnoreCase("P")||
						assessmentCritForm.getStudyUnit().getFormalTuitionFlag().equalsIgnoreCase("T")||
						assessmentCritForm.getStudyUnit().getFormalTuitionFlag().equalsIgnoreCase("L")||
						assessmentCritForm.getStudyUnit().getFormalTuitionFlag().equalsIgnoreCase("E")){
					//do nothing
				} else{
					if (nrFormativeAss < 1) {
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage",
											"The assessment plan must at least include 1 formative assessment."));
					}
					if (!assessmentCritForm.getStudyUnit().getFormalTuitionFlag().equalsIgnoreCase("C")){
						if (!existAssignmentBeforeFirstDueDate){
							messages.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("message.generalmessage",
												"The due date of one of the assessments must be on or before " + assessmentCritForm.getFirstAssDueControlDate() + ".  This is required for examination admission and subsidy purposes."));
						}	
//						if(nrOfAssignmentsBeforeSecondDueDate < 2){
//							if (portfolioBeforeFirstDueDate && nrOfPortfolios > 1){
//								//this is OK
//							} else if (!portfolioBeforeFirstDueDate && nrOfPortfolios >=1){
//								//this is OK
//							} else {
//								messages.add(ActionMessages.GLOBAL_MESSAGE,
//										new ActionMessage("message.generalmessage",
//													"A second assignment must be due on or before " + assessmentCritForm.getSecondAssDueControlDate() + ".  A second assignment may be a portfolio, then the second due date rule will not apply."));
//							}	
//						}			
					}
					
				}
				if (assessmentCritForm.getExamBase().equalsIgnoreCase("CONT") && nrOfPortfolios > 0){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"Module is flagged for continuous assessment, summative assessments are not allowed"));
				}
				if (assessmentCritForm.getExamBase().equalsIgnoreCase("CONT") && 
						assessmentCritForm.getFinalMarkComp().getYearMarkComponent()!=null &&
						Integer.parseInt(assessmentCritForm.getFinalMarkComp().getYearMarkComponent()) < 100){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"Module is flagged for continuous assessment, final mark component, year mark should be 100%."));
				}
				//if exam admission method - Admission granted if student submitted a specified number of of prescribed assignments
				//Check on authorisation if the assessment plan number of assignments at least equals the number of assignments to be submitted for exam admission
				if (!assessmentCritForm.getStudyUnit().getAutoExamAdmission().equalsIgnoreCase("Y") &&
						assessmentCritForm.getStudyUnit().getFormalTuitionFlag().equalsIgnoreCase("C") ||
						assessmentCritForm.getStudyUnit().getAcademicLevel().equalsIgnoreCase("H") ||
						assessmentCritForm.getStudyUnit().getAcademicLevel().equalsIgnoreCase("M") ||
						assessmentCritForm.getStudyUnit().getAcademicLevel().equalsIgnoreCase("D")){
					//20160622 - change for 2017
					//Verify that all due dates for formative assessments with admission system AM(Submission of more than one assignment) are set before exam admission date for that academic period
					//Verify that all due dates for formative assessments with admission system YM(Year mark subminimum achieved) are set at least 7 days before exam admission date for that academic period
				if (assessmentCritForm.getFinalMarkComp().getExamAdmissionMethod().equalsIgnoreCase("AM")){
						if (Integer.parseInt(assessmentCritForm.getFinalMarkComp().getExamAdmissionNrAss()) > nrFormativeAss) {
							messages.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("message.generalmessage",
												"The assessment plan must at least include the number of formative assessments that needs to be submitted for examination admission."));
						}
						if (nrFormativeAssAfterExamAdmDate > 0) {	
							//20160605 - 2018 Changes
//							messages.add(ActionMessages.GLOBAL_MESSAGE,
//									new ActionMessage("message.generalmessage",
//												"If examination admission is given on the submission of more than one assessment then all formative assessment due dates must be before the examination admission date(" + assessmentCritForm.getExamAdmissionDate() + "). Not all formative assessments satisfy this requirement." ));
							messages.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("message.generalmessage",
												"If examination admission is given on the 'Submission of more than one assessment' then all formative assessments due dates must be at least 3 days before the examination admission date(" + assessmentCritForm.getExamAdmissionDate() + "). Not all formative assessments satisfy this requirement." ));
						}
						if (nrFormativeAssFinalSubAfterExamAdmDate > 0){
							messages.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("message.generalmessage",
												"If examination admission is given on the 'Submission of more than one assessment' or on a 'Year mark sub-minimum achieved' then all formative assessments, submission cut-off dates must be set to at least 1 day before the examination admission date(" + assessmentCritForm.getExamAdmissionDate() + "). Not all formative assessments satisfy this requirement." ));
						}
					}
					if (assessmentCritForm.getFinalMarkComp().getExamAdmissionMethod().equalsIgnoreCase("YM")){
							if (nrFormativeAssAfterExamAdmDate > 0) {
								messages.add(ActionMessages.GLOBAL_MESSAGE,
										new ActionMessage("message.generalmessage",
													"If examination admission is given on a 'Year mark sub-minimum achieved' then all formative assessments due dates must be at least 7 days before the examination admission date(" + assessmentCritForm.getExamAdmissionDate() + "). Not all formative assessments satisfy this requirement." ));
							}
							if (nrFormativeAssFinalSubAfterExamAdmDate > 0){
								messages.add(ActionMessages.GLOBAL_MESSAGE,
										new ActionMessage("message.generalmessage",
													"If examination admission is given on a 'Year mark sub-minimum achieved' then all formative assessments submission cut-off dates must be at least 1 day before the examination admission date(" + assessmentCritForm.getExamAdmissionDate() + "). Not all formative assessments satisfy this requirement." ));
							}
					}
				}				
				
				if (totalAssignmentNormalWeight > 0 || totalAssignmentRepeatWeight > 0 || totalAssignmentAegrotatWeight > 0) {
					if (Integer.parseInt(assessmentCritForm.getFinalMarkComp().getYearMarkComponent()) == 0) {
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage",
											"If an assessment weight had been allocated to a formative assessment than the year mark component must be greater than 0"));
					}
				}
				
				if (totalPortfolioNormalWeight > 0 || totalPortfolioRepeatWeight > 0 || totalPortfolioAegrotatWeight > 0) {
					if (Integer.parseInt(assessmentCritForm.getFinalMarkComp().getPortfolioComponent()) == 0) {
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage",
											"If an assessment weight had been allocated to a summative assessment than the Non-venue Examination(previously Portfolio) component must be greater than 0"));
					}
				}
					
				//if no elective assignments are defined and year mark component is greater than 0, then the year mark weights must add up to 100.
				if (nrElectiveAss == 0) {
					if (Integer.parseInt(assessmentCritForm.getFinalMarkComp().getYearMarkComponent())> 0 && (totalAssignmentNormalWeight!=100 || totalAssignmentRepeatWeight!=100 || totalAssignmentAegrotatWeight!=100)){
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage",
											"If year mark component is greater than 0 than the normal assessment weight of all formative assessments must accumulate to a 100, the repeat assignment weight of all formative assessments must accumulate to a 100, the aegrotat assessment weight of all formative assessments must accumulate to a 100."));
					}					
				}
				//if portfolio mark component is greater than 0, then the portfolio weights must add up to 100.
				//make the assumption a portfolio can not be elective
				if (Integer.parseInt(assessmentCritForm.getFinalMarkComp().getPortfolioComponent())> 0 && (totalPortfolioNormalWeight!=100 || totalPortfolioRepeatWeight!=100 || totalPortfolioAegrotatWeight!=100)){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"If the Non-venue Examination(previously Portfolio) component is greater than 0 than the normal assessment weight of all summative assessments must accumulate to a 100, the repeat assessment weight of all summative assessments must accumulate to a 100, the aegrotat assessment weight of all summative assessments must accumulate to a 100"));
				}	
						
//				for (int i=0; i < assessmentCritForm.getListAssignment().size(); i++){
//					List markers = new ArrayList();
//					markers = ((Assignment)assessmentCritForm.getListAssignment().get(i)).getListMarkers();	
//					if (markers!=null){
//						for (int j=0; j < markers.size(); j++){
//							if (((Marker)markers.get(j)).getStatus().equalsIgnoreCase("inactive")){
//								if (((Marker)markers.get(j)).getMarkPercentage()==null ||
//										((Marker)markers.get(j)).getMarkPercentage().trim()=="" ){								
//								}else{
//									if (Integer.parseInt(((Marker)markers.get(j)).getMarkPercentage().trim()) > 0) {
//										messages.add(ActionMessages.GLOBAL_MESSAGE,
//												new ActionMessage("message.generalmessage",
//															"A mark percentage may not be assigned to an inactive marker, mark percentage assigned to an inactive marker on assignment " +
//															((Assignment)assessmentCritForm.getListAssignment().get(i)).getNumber()));
//									}
//								}									
//							}
//						}
//					}					
//				}
				List errorList = new ArrayList();
				errorList = validateAssignments(assessmentCritForm);
				for (int i=0; i < errorList.size(); i++){
					String msg = (String)errorList.get(i);
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										msg));
				}
				if (!messages.isEmpty()) {
					addErrors(request,messages);
					return mapping.findForward("requestAuthorisation");
				}	
				//test mandatory fields
				if (assessmentCritForm.getResponseEmailAddress()==null &&
						assessmentCritForm.getResponseEmailAddress().equalsIgnoreCase("")){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("errors.required",
										"Email address to which authorisation response must be send"));
				}
				if (!messages.isEmpty()) {
					addErrors(request,messages);
					return mapping.findForward("requestAuthorisation");
				}
				//flow to coolgen server to write entry to log
				Saacq01sAssessmentCriteriaMnt op = new Saacq01sAssessmentCriteriaMnt();
				operListener opl = new operListener();
				op.clear();
				
				op.setInCsfClientServerCommunicationsAction("CL");
				//op.setInFinalMarkCalculationExamYear(assessmentCritForm.getFirstExamination().getYear());
				//op.setInFinalMarkCalculationMkExamPeriod(assessmentCritForm.getFirstExamination().getPeriod());
				//op.setInFinalMarkCalculationMkStudyUnitCode(assessmentCritForm.getStudyUnit().getCode());
				op.setInAssessmentLogYear(Short.parseShort(assessmentCritForm.getAcademicYear()));
				op.setInAssessmentLogPeriod(Short.parseShort(assessmentCritForm.getSemester()));
				op.setInAssessmentLogMkStudyUnitCode(assessmentCritForm.getStudyUnit().getCode());
				op.setInAssessmentLogUpdatedBy(assessmentCritForm.getNovellUserId());
				op.setInAssessmentLogReturnEmailAddress(assessmentCritForm.getResponseEmailAddress());
				op.setInAssessmentLogRequestActionFrom(assessmentCritForm.getSelectedAuthoriser());
				op.setInAssessmentLogActionGc53("AUTHREQ");
				op.execute();
				if (opl.getException() != null)
					throw opl.getException();
				if (op.getExitStateType() < 3)
					throw new Exception(op.getExitStateMsg());
				if (op.getOutCsfStringsString500() != "") {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
									op.getOutCsfStringsString500()));	
					addErrors(request,messages);
					return mapping.findForward("requestAuthorisation");
				}
				toolManager = (ToolManager)ComponentManager.get(ToolManager.class);
				eventTrackingService = (EventTrackingService)ComponentManager.get(EventTrackingService.class);
				eventTrackingService.post(
						eventTrackingService.newEvent(EventTrackingTypes.EVENT_ASSESSMENT_CRITERIA_REQUEST_AUTH, toolManager.getCurrentPlacement().getContext(), false),usageSession);
				
						
				//email notification of authorisation request to cod or acting cod
				String toAddress="";
				String addressee="";
				String course=assessmentCritForm.getStudyUnit().getCode() + " " + assessmentCritForm.getAcademicYear() + "/" + assessmentCritForm.getSemesterType();
				if (assessmentCritForm.getSelectedAuthoriser().equalsIgnoreCase(assessmentCritForm.getDepartment().getCod().getPersonnelNumber())){
					toAddress=assessmentCritForm.getDepartment().getCod().getEmailAddress();
					addressee=assessmentCritForm.getDepartment().getCod().getName();
				} else {
					for (int i = 0; i < assessmentCritForm.getDepartment().getActingCodList().size(); i++){
						String actingCod = ((Person)(assessmentCritForm.getDepartment().getActingCodList().get(i))).getPersonnelNumber();
						if (assessmentCritForm.getSelectedAuthoriser().equalsIgnoreCase(actingCod)){
							toAddress=((Person)(assessmentCritForm.getDepartment().getActingCodList().get(i))).getEmailAddress();
							addressee=((Person)(assessmentCritForm.getDepartment().getActingCodList().get(i))).getName();
						}
					}
				}
				//do not send email on dev & qa -default email to pretoj@unisa.ac.za
				String serverpath = ServerConfigurationService.getServerUrl();
				if (serverpath.contains("mydev") || serverpath.contains("myqa")){
					toAddress="pretoj@unisa.ac.za";
				}	
				sendNotifyEmail(toAddress, addressee, course);				
			}
			
			if ("cancelAuthorisationRequest".equalsIgnoreCase(assessmentCritForm.getCurrentPage())){				
				//flow to coolgen server to write entry to log
				Saacq01sAssessmentCriteriaMnt op = new Saacq01sAssessmentCriteriaMnt();
				operListener opl = new operListener();
				op.clear();
				
				op.setInCsfClientServerCommunicationsAction("CL");
				//op.setInFinalMarkCalculationExamYear(assessmentCritForm.getFirstExamination().getYear());
				//op.setInFinalMarkCalculationMkExamPeriod(assessmentCritForm.getFirstExamination().getPeriod());
				//op.setInFinalMarkCalculationMkStudyUnitCode(assessmentCritForm.getStudyUnit().getCode());
				op.setInAssessmentLogYear(Short.parseShort(assessmentCritForm.getAcademicYear()));
				op.setInAssessmentLogPeriod(Short.parseShort(assessmentCritForm.getSemester()));
				op.setInAssessmentLogMkStudyUnitCode(assessmentCritForm.getStudyUnit().getCode());
				op.setInAssessmentLogUpdatedBy(assessmentCritForm.getNovellUserId());
				op.setInAssessmentLogActionGc53("REQCAN");
				op.execute();
				if (opl.getException() != null)
					throw opl.getException();
				if (op.getExitStateType() < 3)
					throw new Exception(op.getExitStateMsg());
				if (op.getOutCsfStringsString500() != "") {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
									op.getOutCsfStringsString500()));	
					addErrors(request,messages);
					//get assessmentcriteria status may have changed
					getAssessmentCriteria(assessmentCritForm);	
					return mapping.findForward("cancelAuthorisationRequest");
				}
				toolManager = (ToolManager)ComponentManager.get(ToolManager.class);
				eventTrackingService = (EventTrackingService)ComponentManager.get(EventTrackingService.class);
				eventTrackingService.post(
						eventTrackingService.newEvent(EventTrackingTypes.EVENT_ASSESSMENT_CRITERIA_CANCEL_REQUEST, toolManager.getCurrentPlacement().getContext(), false),usageSession);
			}
			
			getAssessmentCriteria(assessmentCritForm);	
			
			return mapping.findForward("displayAssessmentCriteria");
	}
	
	public ActionForward cancel(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) throws Exception {
		
		AssessmentCriteriaForm assessmentCritForm = (AssessmentCriteriaForm) form;
		String nextPage="";
		
		if(assessmentCritForm.getCurrentPage().equalsIgnoreCase("assessmentCriteria")){
			nextPage="cancel";
		} else {		
			if (assessmentCritForm.getAssignmentAction().equalsIgnoreCase("add")){
				assessmentCritForm.setAssignmentAction("edit");
			}
			getAssessmentCriteria(assessmentCritForm);
			nextPage = "displayAssessmentCriteria";
		}
							
		return mapping.findForward(nextPage);
		
		}
	
	public ActionForward nextStep(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
		    AssessmentCriteriaForm assessmentCritForm = (AssessmentCriteriaForm) form;
			String nextPage="";
			if("input".equalsIgnoreCase(assessmentCritForm.getCurrentPage())){
				nextPage = displayAssessmentCriteria(mapping, form, request, response);
			} else if("assignment".equalsIgnoreCase(assessmentCritForm.getCurrentPage())) {
				nextPage = displayAssignmentMarkingDetails(mapping, form, request, response);
			} else if("onscreenMarking".equalsIgnoreCase(assessmentCritForm.getCurrentPage())){
				nextPage = displayMarkPercentages(mapping, form, request, response);
			} else if("survey".equalsIgnoreCase(assessmentCritForm.getCurrentPage())) {
				nextPage = displayAssignmentMarkingDetails(mapping, form, request, response);
			} else if("mcqMarking".equalsIgnoreCase(assessmentCritForm.getCurrentPage())){
				nextPage = displayMCQMemorandum(mapping, form, request, response);
			} 			
			
			return mapping.findForward(nextPage);
			
			}
	
	public ActionForward prevStep(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
		    AssessmentCriteriaForm assessmentCritForm = (AssessmentCriteriaForm) form;
			String prevPage="";			
			
			if("assignment".equalsIgnoreCase(assessmentCritForm.getCurrentPage()) && assessmentCritForm.getFromSystem().equalsIgnoreCase("STUDENT")){
				prevPage = "displayAssessmentCriteria";
			} else if ("mcqMemorandum".equalsIgnoreCase(assessmentCritForm.getCurrentPage())){
				assessmentCritForm.setCurrentPage("mcqMarking");
				prevPage = "displayMCQMarkingDetails";
			} else if ("writtenMarking".equalsIgnoreCase(assessmentCritForm.getCurrentPage())){
				if (!assessmentCritForm.getAssignment().getOnscreenMarkFlag().equalsIgnoreCase("Y")){
					//Set request variables on page
					//Get month list
					List monthList = new ArrayList();
					StudentSystemGeneralDAO dao = new StudentSystemGeneralDAO();
					for (int i=0; i < dao.getGenCodes((short)2,2).size(); i++) {
						monthList.add(i, (Gencod)(dao.getGenCodes((short)2,2).get(i)));
					}
					request.setAttribute("monthList", monthList);
					
					//get year, month and day of due date
					int dueDateYear = Integer.parseInt((assessmentCritForm.getAssignment().getDueDate().substring(0, 4)));
					int dueDateMonth = Integer.parseInt((assessmentCritForm.getAssignment().getDueDate().substring(4, 6)));
					int dueDateDay =Integer.parseInt((assessmentCritForm.getAssignment().getDueDate().substring(6)));
								
					request.setAttribute("dueDateYear", String.valueOf(dueDateYear));
					request.setAttribute("dueDateMonth", String.valueOf(dueDateMonth));
					request.setAttribute("dueDateDay", String.valueOf(dueDateDay));	
					assessmentCritForm.setCurrentPage("assignment");
					prevPage = "displayAssignment";
				}else{
					assessmentCritForm.setResetOnscreenMarkingArrays(true);
					assessmentCritForm.setCurrentPage("onscreenMarking");
					prevPage = "displayOnscreenMarking";
				}				
			} else if ("mcqMarking".equalsIgnoreCase(assessmentCritForm.getCurrentPage()) ||				
					"onscreenMarking".equalsIgnoreCase(assessmentCritForm.getCurrentPage()) ||
					"noMarking".equalsIgnoreCase(assessmentCritForm.getCurrentPage())
					) {
				//Disabled fields do not submit - change fields set by javascript
				if (assessmentCritForm.getAssignment().getReleaseFlag()!=null && assessmentCritForm.getAssignment().getReleaseFlag().equalsIgnoreCase("N")){
					assessmentCritForm.getAssignment().setFileReleaseDate("");
				}
				//Set request variables on page
				//Get month list
				List monthList = new ArrayList();
				StudentSystemGeneralDAO dao = new StudentSystemGeneralDAO();
				for (int i=0; i < dao.getGenCodes((short)2,2).size(); i++) {
					monthList.add(i, (Gencod)(dao.getGenCodes((short)2,2).get(i)));
				}
				request.setAttribute("monthList", monthList);
				
				//get year, month and day of due date
				int dueDateYear = Integer.parseInt((assessmentCritForm.getAssignment().getDueDate().substring(0, 4)));
				int dueDateMonth = Integer.parseInt((assessmentCritForm.getAssignment().getDueDate().substring(4, 6)));
				int dueDateDay =Integer.parseInt((assessmentCritForm.getAssignment().getDueDate().substring(6)));
							
				request.setAttribute("dueDateYear", String.valueOf(dueDateYear));
				request.setAttribute("dueDateMonth", String.valueOf(dueDateMonth));
				request.setAttribute("dueDateDay", String.valueOf(dueDateDay));		
				
				assessmentCritForm.setCurrentPage("assignment");
				prevPage="displayAssignment";
			}
//			if ("writtenMarking".equalsIgnoreCase(assessmentCritForm.getCurrentPage())) {
//				//Set request variables on page
//				//Get month list
//				List monthList = new ArrayList();
//				StudentSystemGeneralDAO dao = new StudentSystemGeneralDAO();
//				for (int i=0; i < dao.getGenCodes((short)2,2).size(); i++) {
//					monthList.add(i, (Gencod)(dao.getGenCodes((short)2,2).get(i)));
//				}
//				request.setAttribute("monthList", monthList);
//				
//				//get year, month and day of due date
//				int dueDateYear = Integer.parseInt((assessmentCritForm.getAssignment().getDueDate().substring(0, 4)));
//				int dueDateMonth = Integer.parseInt((assessmentCritForm.getAssignment().getDueDate().substring(4, 6)));
//				int dueDateDay =Integer.parseInt((assessmentCritForm.getAssignment().getDueDate().substring(6)));
//							
//				request.setAttribute("dueDateYear", String.valueOf(dueDateYear));
//				request.setAttribute("dueDateMonth", String.valueOf(dueDateMonth));
//				request.setAttribute("dueDateDay", String.valueOf(dueDateDay));	
//				prevPage="displayAssignment";
//			}
			return mapping.findForward(prevPage);
			
			}
	
	public ActionForward displayRequestAuthorisation(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
		AssessmentCriteriaForm assessmentCritForm = (AssessmentCriteriaForm) form;
		ActionMessages messages = new ActionMessages();
		
		//Authorisation only allowed if sunpdt record exists
		if (!assessmentCritForm.isSunpdtExists()) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"Authorisation request not allowed, the information on AIMS is not complete."));
		}
		if (!messages.isEmpty()) {
			addErrors(request,messages);
			return mapping.findForward("displayAssessmentCriteria");
		}
				
		//validate if assessment criteria satisfy authorisation criteria
		//at least 2 mandatory assignments
		//no mark percentage assign to inactive markers
		//mark percentage must accumulate to 100%
		
		//get department info		
		//get cod		
		//get acting cod's		
		DepartmentDAO deptDAO = new DepartmentDAO();
		Department department = new Department();
		department = deptDAO.getDepartment(Short.parseShort((assessmentCritForm.getStudyUnit().getDepartment())));
		//department.getCod().setEmailAddress("pretoj@unisa.ac.za");//Just for testing - remove before PROD
		assessmentCritForm.setDepartment(department);
		List codList = new ArrayList();
		if (assessmentCritForm.getDepartment().getCod().getPersonnelNumber()!=null){
			codList.add(department.getCod());
		}
		request.setAttribute("codList",codList);
		
		if (assessmentCritForm.getDepartment().getCod().getPersonnelNumber()==null &&
				assessmentCritForm.getDepartment().getActingCodList().size()==0){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"Authorisation request not allowed, no COD or stand in COD's have been defined for department to send authorisation request to."));
		}
		if (!messages.isEmpty()) {
			addErrors(request,messages);
			return mapping.findForward("displayAssessmentCriteria");
		}
		
		//get myUnisa user's email address
		Person user = new Person();
		UserDAO userdao = new UserDAO();
		user = userdao.getPerson(assessmentCritForm.getNovellUserId());
		assessmentCritForm.setResponseEmailAddress(user.getEmailAddress());	
				
		return mapping.findForward("requestAuthorisation");
	}
	
	public ActionForward displayCancelAuthorisationRequest(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
		AssessmentCriteriaForm assessmentCritForm = (AssessmentCriteriaForm) form;
				
		return mapping.findForward("cancelAuthorisationRequest");
	}
	
	public ActionForward initial(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
		AssessmentCriteriaForm assessmentCritForm = (AssessmentCriteriaForm) form;
				
		resetFormFields(assessmentCritForm);
		
		//Get user details
		//default system to logged into myUnisa
		String fromSystem="";
	
		if (request.getParameter("SYSTEM")==null){
			//logged onto myUnisa - called from within myUnisa
			
			assessmentCritForm.setNovellUserId(null);
			User user = null;
		
			sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
			userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
			Session currentSession = sessionManager.getCurrentSession();
			String userID  = currentSession.getUserId();
				
			if (userID != null) {
				user = userDirectoryService.getUser(userID);
				assessmentCritForm.setNovellUserId(user.getEid().toUpperCase());
			}
		}		
		else{
			//called from outside myUnisa - F69 - can only view authorised assessment plans - 
			fromSystem=request.getParameter("SYSTEM");
			String year = request.getParameter("YEAR");
			String period = request.getParameter("PERIOD");
			String module = request.getParameter("MODULE");
			assessmentCritForm.setAcademicYear(year);
			assessmentCritForm.setSemester(period);
			assessmentCritForm.getStudyUnit().setCode(module);
			
		}
		
		assessmentCritForm.setFromSystem(fromSystem);
		//Get semester list
		List<Gencod> list = new ArrayList<Gencod>();
		StudentSystemGeneralDAO dao = new StudentSystemGeneralDAO();
		for (int i=0; i < dao.getGenCodes((short)54,1).size(); i++) {
			list.add(i, (Gencod)(dao.getGenCodes((short)54,1).get(i)));
		}
		assessmentCritForm.setListSemester(list);
		
		//Get assignment format list
		list = new ArrayList<Gencod>();
		dao = new StudentSystemGeneralDAO();
		for (int i=0; i < dao.getGenCodes((short)176,3).size(); i++) {
			Gencod gencod = new Gencod();
			gencod = (Gencod)(dao.getGenCodes((short)176,3).get(i));			
			if (gencod.getCode().equalsIgnoreCase("XA")){
				gencod.setEngDescription(gencod.getEngDescription().trim() + " (No submission allowed)");
			}else if (!gencod.getCode().equalsIgnoreCase("OR") && !gencod.getCode().equalsIgnoreCase("SA")){
				gencod.setEngDescription(gencod.getEngDescription().trim() + " (Online submission only)");
			}
			
			list.add(i,gencod );
		}
		for (int i=0; i < dao.getGenCodes((short)55,1).size(); i++) {
			Gencod gencod = new Gencod();
			gencod = (Gencod)(dao.getGenCodes((short)55,1).get(i));
			if (gencod.getCode().equalsIgnoreCase("H")){
				gencod.setEngDescription(gencod.getEngDescription().trim() + " (Includes Portfolio, Practical, Project, Peer, Group, Take-Home)");
			}
			list.add(i,gencod );
		}		
		assessmentCritForm.setListAssignmentFormat(list);
		
		//Get assignment type list
		list = new ArrayList<Gencod>();
		//dao = new StudentSystemGeneralDAO();
		for (int i=0; i < dao.getGenCodes((short)56,3).size(); i++) {
			Gencod gencod = new Gencod();
			gencod = (Gencod)(dao.getGenCodes((short)56,3).get(i));
			if (gencod.getCode().equalsIgnoreCase("T")){
				gencod.setEngDescription(gencod.getEngDescription().trim() + " (Venue based, students not allowed to submit assessment via myUnisa)");
			}
			list.add(i,gencod);
		}
		assessmentCritForm.setListAssignmentType(list);
		
		//Get assignment optionality list
		list = new ArrayList<Gencod>();
		//dao = new StudentSystemGeneralDAO();
		for (int i=0; i < dao.getGenCodes((short)57,1).size(); i++) {
			Gencod optionality = new Gencod();
			optionality = (Gencod)(dao.getGenCodes((short)57,1).get(i));
			if (optionality.getCode().equalsIgnoreCase("E")){
				optionality.setEngDescription("Assessment is part of a group where best awarded marks apply. " +
						"(Contact assignment department for grouping of assessments)");
			}
			if (optionality.getCode().equalsIgnoreCase("M")){
				optionality.setEngDescription("Assessment will always contribute to the year mark/portfolio component of the final mark according to the weights.");
			}
			//list.add(i, (Gencod)(dao.getGenCodes((short)57,1).get(i)));
			list.add(i, optionality);
		}
		assessmentCritForm.setListAssignmentOptionality(list);
		
		//Get Assessment group		
		list = new ArrayList<Gencod>();
		list = dao.getGenCodes((short)230, 1);
		assessmentCritForm.setListAssignmentGroup(list);
		
		//Get MCQ possible answers
		list = new ArrayList<Gencod>();
//		//dao = new StudentSystemGeneralDAO();
//		for (int i=0; i < dao.getGenCodes((short)58,1).size(); i++) {
//			list.add(i, (Gencod)(dao.getGenCodes((short)58,1).get(i)));
//		}
		list = dao.getGenCodes((short)58, 1);
		assessmentCritForm.setListPossibleMCQAnswers(list);
		
		//Get MCQ negative marking list - YesNoFlag
		list = new ArrayList<Gencod>();
		//dao = new StudentSystemGeneralDAO();
//		for (int i=0; i < dao.getGenCodes((short)51,0).size(); i++) {
//			list.add(i, (Gencod)(dao.getGenCodes((short)51,0).get(i)));
//		}
		list = dao.getGenCodes((short)51, 1);
		assessmentCritForm.setListNegativeMarking(list);
		assessmentCritForm.setListYesNo(list);
		assessmentCritForm.setListSelfAssessment(list);
		
		//Get file format list
		list = new ArrayList<Gencod>();		
//		for (int i=0; i < dao.getGenCodes((short)204,3).size(); i++) {
//			list.add(i, (Gencod)(dao.getGenCodes((short)204,3).get(i)));
//		}
		list = dao.getGenCodes((short)204, 1);
		assessmentCritForm.setListFileFormat(list);
		
		//Get language list
		list = new ArrayList<Gencod>();		
		for (int i=0; i < dao.getGenCodes((short)203,3).size(); i++) {
			Gencod gencod = new Gencod();
			gencod = (Gencod)dao.getGenCodes((short)203,3).get(i);
			gencod.setEngDescription(gencod.getEngDescription().toUpperCase());
			list.add(i,gencod);
			//list.add(i, (Gencod)(dao.getGenCodes((short)203,3).get(i)));
		}
		assessmentCritForm.setListLanguage(list);
		
		//Change 2015 BR
		//Get exam admission methods
		list = new ArrayList<Gencod>();
		for (int i=0; i < dao.getGenCodes((short)224,3).size(); i++) {
			Gencod gencod = new Gencod();
			gencod = (Gencod)dao.getGenCodes((short)224,3).get(i);
			if (gencod.getCode().equalsIgnoreCase("A1")){
				gencod.setEngDescription("Submission of at least one (any one) assessment");
			}
			gencod.setEngDescription(gencod.getEngDescription());
			list.add(i,gencod);			
		}
		assessmentCritForm.setListExamAdmissionMethod(list);
		
		//StudyUnit studyUnit = new StudyUnit();
		//assessmentCritForm.setStudyUnit(studyUnit);
		FinalMarkComposition finalMarkComp = new FinalMarkComposition();
		assessmentCritForm.setFinalMarkComp(finalMarkComp);
		
		if (assessmentCritForm.getFromSystem().equalsIgnoreCase("STUDENT")){
			return mapping.findForward(displayAssessmentCriteria(mapping, form, request, response));
		}
		
		
		//get site	
		toolManager = (ToolManager)ComponentManager.get(ToolManager.class);
		String siteId ="";
		siteId = toolManager.getCurrentPlacement().getContext(); 
		//start test
		CoursePeriod course = new CoursePeriod();
    	CoursePeriodLookup daocourse = new CoursePeriodLookup();
    	assessmentCritForm.setSiteId(siteId);
		try {
			course=daocourse.getCoursePeriod(siteId);
		} catch (Exception e){
			assessmentCritForm.setSiteId("Course Admin");
			return mapping.findForward("input");			
		}
		assessmentCritForm.setAcademicYear(String.valueOf(course.getYear()));
    	assessmentCritForm.setSemester(String.valueOf(course.getSemesterPeriod()));
    	assessmentCritForm.setSemesterType(course.getSemesterType());
    	assessmentCritForm.getStudyUnit().setCode(course.getCourseCode());
    	return mapping.findForward(displayAssessmentCriteria(mapping, form, request, response));
		//end test
		//siteId="ACN101M-08-S2";
		//siteId="Course Admin";
    	//comment out
//        assessmentCritForm.setSiteId(siteId);
//        if (siteId.equalsIgnoreCase("Course Admin")){   
//        	return mapping.findForward("input");
//        }else{
//        	CoursePeriod course = new CoursePeriod();
//        	CoursePeriodLookup daocourse = new CoursePeriodLookup();
//        	course=daocourse.getCoursePeriod(siteId);
//        	assessmentCritForm.setAcademicYear(String.valueOf(course.getYear()));
//        	assessmentCritForm.setSemester(String.valueOf(course.getSemesterPeriod()));
//        	assessmentCritForm.setSemesterType(course.getSemesterType());
//        	assessmentCritForm.getStudyUnit().setCode(course.getCourseCode());
//        	return mapping.findForward(displayAssessmentCriteria(mapping, form, request, response));
//        }
		
	}		
	
	public String displayAssessmentCriteria(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		ActionMessages messages = new ActionMessages();
		AssessmentCriteriaForm assessmentCritForm = (AssessmentCriteriaForm) form;
		
		CourseDAO dao = new CourseDAO();
		//determine if study unit exists, get study unit description and department code
		StudyUnit studyUnit = new StudyUnit();
		studyUnit = dao.getStudyUnit(assessmentCritForm.getStudyUnit().getCode());
		//assessmentCritForm.setStudyUnit(dao.getStudyUnit(assessmentCritForm.getStudyUnit().getCode()));
		//validate input
		if (assessmentCritForm.getAcademicYear()==null || assessmentCritForm.getAcademicYear().trim().equalsIgnoreCase("")){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"Academic Year is required"));
		} else {
			if (!isInteger(assessmentCritForm.getAcademicYear())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"Academic Year must be numeric"));
			}
		}	
		if (assessmentCritForm.getSemester()==null || assessmentCritForm.getSemester().trim().equalsIgnoreCase("")){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"Semester is required"));
		}else{
			if (!isInteger(assessmentCritForm.getSemester())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"Semester must be numeric"));
			}else{
				CoursePeriodLookup periodLookup = new CoursePeriodLookup();
				assessmentCritForm.setSemesterType(periodLookup.getCourseTypeAsString(assessmentCritForm.getSemester()));
			}			
		}
		if (assessmentCritForm.getStudyUnit().getCode()==null || assessmentCritForm.getStudyUnit().getCode().trim().equalsIgnoreCase("")){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"Study Unit is required"));
		}
		String messageToCalingProgram = "";
		if (!messages.isEmpty()) {
			if (assessmentCritForm.getFromSystem().equalsIgnoreCase("STUDENT")){
				addErrors(request,messages);
				messageToCalingProgram = "Error : invalid input parameters";
				request.setAttribute("messageToCalingProgram", messageToCalingProgram);
				return "invalidCall";
			}else{
				addErrors(request,messages);
				return "input";
			}			
		}
		if (studyUnit.getCode()==null){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"Study Unit not found"));
			messageToCalingProgram = "Error : invalid input parameters";
		} else {
			assessmentCritForm.setStudyUnit(studyUnit);
		}
		AssessmentCriteriaDAO daoAssessCrit = new AssessmentCriteriaDAO();
		assessmentCritForm.setSunpdtExists(dao.isSunpdtExist(Short.parseShort(assessmentCritForm.getAcademicYear()), Short.parseShort(assessmentCritForm.getSemester()), assessmentCritForm.getStudyUnit().getCode()));
		if (assessmentCritForm.getFromSystem().equalsIgnoreCase("STUDENT")){
			//called from F69(Student system)
			getAssessmentCriteria(assessmentCritForm);
			if (!assessmentCritForm.getStatus().equals("AUTHORISED")){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"Assesment plan unavailable for viewing, assessment plan has not yet been finalized."));
			}else{
				assessmentCritForm.setAssignmentAction("view");
				assessmentCritForm.setEnableDelete(false);
				return "displayAssessmentCriteria";
			}
		}	
		if (!messages.isEmpty()) {
			if (assessmentCritForm.getFromSystem().equalsIgnoreCase("STUDENT")){
				addErrors(request,messages);
				request.setAttribute("messageToCalingProgram", messageToCalingProgram);
				return "invalidCall";
			}else{
				addErrors(request,messages);
				return "input";
			}			
		}	

		//20140605 - change to check due dates agains first exam period - sunpdt needs to exist before capturing of assessment criteria
		if (!assessmentCritForm.isSunpdtExists()) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"The Assessment Plan for this module cannot be completed as the (year) academic information record has not been updated. Please contact DPAR."));
		}
		
		if (!dao.isValid(assessmentCritForm.getNovellUserId(),
				assessmentCritForm.getStudyUnit().getCode(),
				Short.parseShort(assessmentCritForm.getAcademicYear()),
				Short.parseShort(assessmentCritForm.getSemester()))){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"You are not authorised to enter the assessment plan for this module"));
		}
		if (!messages.isEmpty()) {
			addErrors(request,messages);
			return "input";
		}
		
		//check control dates(AC) - if assessment criteria may be entered
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String stringControlDate = daoAssessCrit.getControlDueDate(Integer.parseInt(assessmentCritForm.getAcademicYear()),
				Short.parseShort(assessmentCritForm.getSemester()), "AC", "FROM");
		if ("".equalsIgnoreCase(stringControlDate)){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"The assessment plan for this registration period has not yet been opened for capturing."));
		}else{
			Date controlDate = formatter.parse(stringControlDate);
			calendar.setTime(controlDate);
			Date today = new Date();
			calendar.setTime(today);
			if (today.before(controlDate)){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"The assessment plan for this registration period has not yet been opened for capturing."));
			}
		}
		if (!messages.isEmpty()) {
			addErrors(request,messages);
			return "input";
		}		

		
		//get Control dates for Registration period
		//if year course determine if post graduate - post graduates different due dates for year courses	
		//Johanet to change
		Sunpdt sunpdt = new Sunpdt();
		sunpdt=daoAssessCrit.getSunpdt(Short.parseShort(assessmentCritForm.getAcademicYear()), Short.parseShort(assessmentCritForm.getSemester()), assessmentCritForm.getStudyUnit().getCode());
		if (sunpdt.getFirstExam() == null || sunpdt.getFirstExam().getYear()==null || sunpdt.getFirstExam().getYear()==0){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"First examination period not set for this course. Please contact Examination department to correct period details for this course (012 429 2932)"));				
		}	
		if (!messages.isEmpty()) {
			addErrors(request,messages);
			return "input";
		}
		Short janExamPeriod = 1;
		if (sunpdt.getFirstExam().getPeriod().compareTo(janExamPeriod)==0){
				assessmentCritForm.setFirstAssDueControlDate(daoAssessCrit.getControlDueDate(Integer.parseInt(assessmentCritForm.getAcademicYear()),
						Short.parseShort(assessmentCritForm.getSemester()),
						"A1P", "TO"));	
				assessmentCritForm.setSecondAssDueControlDate(daoAssessCrit.getControlDueDate(Integer.parseInt(assessmentCritForm.getAcademicYear()),
						Short.parseShort(assessmentCritForm.getSemester()),
						"A2P", "TO"));
				assessmentCritForm.setLastAssDueControlDate(daoAssessCrit.getControlDueDate(Integer.parseInt(assessmentCritForm.getAcademicYear()),
						Short.parseShort(assessmentCritForm.getSemester()),
						"ALP", "TO"));
				assessmentCritForm.setLastPortfolioDueControlDate(daoAssessCrit.getControlDueDate(Integer.parseInt(assessmentCritForm.getAcademicYear()),
							Short.parseShort(assessmentCritForm.getSemester()),
						"PLP", "TO"));
				assessmentCritForm.setExamAdmissionDate(daoAssessCrit.getControlDueDate(Integer.parseInt(assessmentCritForm.getAcademicYear()),
						Short.parseShort(assessmentCritForm.getSemester()),
					"EXAM_ADM_P", "TO"));
				}else{
					assessmentCritForm.setFirstAssDueControlDate(daoAssessCrit.getControlDueDate(Integer.parseInt(assessmentCritForm.getAcademicYear()),
							Short.parseShort(assessmentCritForm.getSemester()),
							"A1", "TO"));	
					assessmentCritForm.setSecondAssDueControlDate(daoAssessCrit.getControlDueDate(Integer.parseInt(assessmentCritForm.getAcademicYear()),
							Short.parseShort(assessmentCritForm.getSemester()),
							"A2", "TO"));
					assessmentCritForm.setLastAssDueControlDate(daoAssessCrit.getControlDueDate(Integer.parseInt(assessmentCritForm.getAcademicYear()),
							Short.parseShort(assessmentCritForm.getSemester()),
							"AL", "TO"));
					assessmentCritForm.setLastPortfolioDueControlDate(daoAssessCrit.getControlDueDate(Integer.parseInt(assessmentCritForm.getAcademicYear()),
							Short.parseShort(assessmentCritForm.getSemester()),
							"PL", "TO"));
					assessmentCritForm.setExamAdmissionDate(daoAssessCrit.getControlDueDate(Integer.parseInt(assessmentCritForm.getAcademicYear()),
							Short.parseShort(assessmentCritForm.getSemester()),
						"EXAM_ADM", "TO"));
				}			
										
		if ("".equalsIgnoreCase(assessmentCritForm.getFirstAssDueControlDate())||
			"".equalsIgnoreCase(assessmentCritForm.getSecondAssDueControlDate())||
			"".equalsIgnoreCase(assessmentCritForm.getLastAssDueControlDate())||
			"".equalsIgnoreCase(assessmentCritForm.getLastPortfolioDueControlDate())||
			"".equalsIgnoreCase(assessmentCritForm.getExamAdmissionDate())){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
							"Assessment due, control dates for this registration period have not been set. Please contact Assignment department (012 429 2986)"));
		}
		
		//Check that the Exam Admission Date has been set for this period
				
		//Check that public holidays is set for the academic year
		CourseDAO courseDAO = new CourseDAO();
		boolean publicHolidaysSet = courseDAO.isPublicHolidaysSetForAcademicYear(Short.parseShort(assessmentCritForm.getAcademicYear()));
		if (publicHolidaysSet==false && Integer.parseInt(assessmentCritForm.getAcademicYear())>2012){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"Public holidays not set for Academic Year. Please contact Assignment department (012 429 2986)"));
		}
		if (!messages.isEmpty()) {
			addErrors(request,messages);
			return "input";
		}
						
		getAssessmentCriteria(assessmentCritForm);	
		
		usageSessionService =(UsageSessionService)ComponentManager.get(UsageSessionService.class);
		UsageSession usageSession = usageSessionService.getSession();	
		eventTrackingService = (EventTrackingService)ComponentManager.get(EventTrackingService.class);
    	toolManager = (ToolManager)ComponentManager.get(ToolManager.class);    		
		eventTrackingService.post(
				eventTrackingService.newEvent(EventTrackingTypes.EVENT_ASSESSMENT_CRITERIA_VIEW, toolManager.getCurrentPlacement().getContext(), false),usageSession);
		
		return "displayAssessmentCriteria";
	}
	
	public ActionForward addAssignment(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
			AssessmentCriteriaForm assessmentCritForm = (AssessmentCriteriaForm) form;
			
			assessmentCritForm.setSurvey(false);
			//initialise assignment values
			Assignment assignment = new Assignment();
			assessmentCritForm.setAssignment(assignment);
			assessmentCritForm.setAssignmentAction("add");
			assessmentCritForm.getAssignment().setSubsidyAssignment("N");
			assessmentCritForm.setNegativeMarkingYesNoFlag("N");
			assessmentCritForm.getAssignment().setNegativeMarkingFactor("0");
			assessmentCritForm.getAssignment().setNormalWeight("0");
			assessmentCritForm.getAssignment().setRepeatWeight("0");
			assessmentCritForm.getAssignment().setAegrotatWeight("0");
			assessmentCritForm.setYearMarkContributionYesNoFlag("");
			assessmentCritForm.setSelfAssesmentYesNoFlag("");
			assessmentCritForm.setIndexNrSelectedFormat(new String[0]);
			assessmentCritForm.setIndexNrSelectedLanguage(new String[0]);
			assessmentCritForm.getAssignment().setFileReleaseDate("");
			assessmentCritForm.getAssignment().setReleaseFlag("N");
			assessmentCritForm.getAssignment().setPfOpenDate("");
			/*Assessment Plan Changes 2016 - 20150601 */
			assessmentCritForm.getAssignment().setGroup("F");
			assessmentCritForm.getAssignment().setFinalSubmissionDate("");
			assessmentCritForm.getAssignment().setMaxFileSize("");
			//default due date to last assignment due control date + 1
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
			String stringControlDate = assessmentCritForm.getLastAssDueControlDate();
			Date controlDate = formatter.parse(stringControlDate);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(controlDate);
			calendar.add(calendar.DATE, 1);
			String strDate = null;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			strDate = sdf.format(calendar.getTime());
						
			//request.setAttribute("dueDateYear", strDate.substring(0, 4));
			//request.setAttribute("dueDateMonth", strDate.substring(4, 6));
			//request.setAttribute("dueDateDay", strDate.substring(6));
			assessmentCritForm.getAssignment().setDueDate(strDate);
			
			//initialise file format selection
			String [] defaultSelectedFileFormats = new String [assessmentCritForm.getListFileFormat().size()];
			
			for (int i=0; i < assessmentCritForm.getListFileFormat().size(); i++){
				if ("PDF".equalsIgnoreCase(((Gencod)assessmentCritForm.getListFileFormat().get(i)).getCode())){
					defaultSelectedFileFormats[i]=String.valueOf(i);
				}
			}	
				
			assessmentCritForm.setIndexNrSelectedFormat(defaultSelectedFileFormats);
			
			List otherLanguages = new ArrayList<String>();
			String language = "";
			otherLanguages.add(language);
			assessmentCritForm.setListOtherLanguage(otherLanguages);
			
			List otherFileFormats = new ArrayList<FileFormat>();
			FileFormat fileFormat = new FileFormat();
			fileFormat.setExtention("");
			fileFormat.setDescription("");
			otherFileFormats.add(fileFormat);
			assessmentCritForm.setListOtherFileFormat(otherFileFormats);
			
			assessmentCritForm.setCurrentPage("assignment");
			return mapping.findForward("displayAssignment");			
			}
	
	public ActionForward addSurvey(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
			AssessmentCriteriaForm assessmentCritForm = (AssessmentCriteriaForm) form;
			ActionMessages messages = new ActionMessages();
			
			//initialise assignment values
			assessmentCritForm.setSurvey(true);
			Assignment assignment = new Assignment();
			assessmentCritForm.setAssignment(assignment);
			assessmentCritForm.setAssignmentAction("add");
			assessmentCritForm.getAssignment().setSubsidyAssignment("N");
			assessmentCritForm.getAssignment().setType("I");
			assessmentCritForm.getAssignment().setFormat("A");
			assessmentCritForm.setNegativeMarkingYesNoFlag("N");
			assessmentCritForm.getAssignment().setNegativeMarkingFactor("0");
			assessmentCritForm.getAssignment().setNormalWeight("0");
			assessmentCritForm.getAssignment().setRepeatWeight("0");
			assessmentCritForm.getAssignment().setAegrotatWeight("0");
			assessmentCritForm.setYearMarkContributionYesNoFlag("N");
			assessmentCritForm.setSelfAssesmentYesNoFlag("");
			assessmentCritForm.getAssignment().setGroup("F");
			
			//default survey number - number between 91-95
			Integer surveyNr = 0;
			for (int n=91; n < 96; n++){
				boolean found = false;
				for (int i=0; i <assessmentCritForm.getListAssignment().size();i++){
					if (((Assignment)(assessmentCritForm.getListAssignment().get(i))).getNumber().equalsIgnoreCase(String.valueOf(n))){
						i = assessmentCritForm.getListAssignment().size();	
						found = true;
					}						
				}
				if (!found){
					surveyNr=n;
					n=96;
				}				
			}
			if (surveyNr==0){
				//maximum number of surveys allowed per course
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
								"You have reached your maximum number of surveys allowed per course, you cannot add another survey."));				
			}else{
				assessmentCritForm.getAssignment().setNumber(String.valueOf(surveyNr));
			}
			
			if (!messages.isEmpty()) {
				addErrors(request,messages);
				return mapping.findForward("displayAssessmentCriteria");
			}			
						
			//default due date to last assignment due control date + 1
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
			String stringControlDate = assessmentCritForm.getLastAssDueControlDate();
			Date controlDate = formatter.parse(stringControlDate);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(controlDate);
			calendar.add(calendar.DATE, 1);
			String strDate = null;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			strDate = sdf.format(calendar.getTime());
						
			assessmentCritForm.getAssignment().setDueDate(strDate);
			
			assessmentCritForm.setCurrentPage("assignment");
			return mapping.findForward("displayAssignment");			
			}
	
	public ActionForward deleteAssignments(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
			ActionMessages messages = new ActionMessages();
			AssessmentCriteriaForm assessmentCritForm = (AssessmentCriteriaForm) form;
			
			//do validation
			if (assessmentCritForm.getIndexSelectedRemoveAssignment()==null ||
					assessmentCritForm.getIndexSelectedRemoveAssignment().length == 0) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
								"At least on assessment must be selected to be removed"));				
			}
			if (!messages.isEmpty()) {
				addErrors(request,messages);
				return mapping.findForward("displayAssessmentCriteria");
			}
			
			Saacq01sAssessmentCriteriaMnt op = new Saacq01sAssessmentCriteriaMnt();
			operListener opl = new operListener();
			op.clear();
			
			op.setInCsfClientServerCommunicationsAction("DA");
			String comment="Delete assignment(s):";
			for (int i=0; i < assessmentCritForm.getIndexSelectedRemoveAssignment().length; i++) {
				String array[] = assessmentCritForm.getIndexSelectedRemoveAssignment();
				op.setInAssGrpUniqueAssignmentYear(i, Short.parseShort(String.valueOf(assessmentCritForm.getDummyAcademicYear())));
				op.setInAssGrpUniqueAssignmentPeriod(i, Short.parseShort(assessmentCritForm.getSemester()));
				op.setInAssGrpUniqueAssignmentUniqueNr(i, Integer.parseInt(((Assignment)(assessmentCritForm.getListAssignment().get(Integer.parseInt(array[i])))).getUniqueNumber()));
				if (i==0){
					comment = comment + " " + ((Assignment)(assessmentCritForm.getListAssignment().get(Integer.parseInt(array[i])))).getNumber();
				}else{
					comment = comment + " & " + ((Assignment)(assessmentCritForm.getListAssignment().get(Integer.parseInt(array[i])))).getNumber();
				}
				
			}	
			op.setInAssignmentGroupCount((Short.parseShort(String.valueOf(assessmentCritForm.getIndexSelectedRemoveAssignment().length))));
			//Set log data
			op.setInAssessmentLogYear(Short.parseShort(assessmentCritForm.getAcademicYear()));
			op.setInAssessmentLogPeriod(Short.parseShort(assessmentCritForm.getSemester()));
			op.setInAssessmentLogMkStudyUnitCode(assessmentCritForm.getStudyUnit().getCode());
			op.setInAssessmentLogUpdatedBy(assessmentCritForm.getNovellUserId());
			op.setInAssessmentLogActionGc53("UPDATE");
			op.setInAssessmentLogComments(comment);
			
			op.execute();
			if (opl.getException() != null)
				throw opl.getException();
			if (op.getExitStateType() < 3)
				throw new Exception(op.getExitStateMsg());
			if (op.getOutCsfStringsString500() != "") {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
								op.getOutCsfStringsString500()));				
			}
			if (!messages.isEmpty()) {
				addErrors(request,messages);
				return mapping.findForward("displayAssessmentCriteria");
			}
			toolManager = (ToolManager)ComponentManager.get(ToolManager.class);
			usageSessionService = (UsageSessionService)ComponentManager.get(UsageSessionService.class);	
			UsageSession usageSession = usageSessionService.getSession();
			eventTrackingService = (EventTrackingService)ComponentManager.get(EventTrackingService.class);
			eventTrackingService.post(
					eventTrackingService.newEvent(EventTrackingTypes.EVENT_ASSESSMENT_CRITERIA_ASS_DELETE, toolManager.getCurrentPlacement().getContext(), false),usageSession);
			
			getAssessmentCriteria(assessmentCritForm);
			
			assessmentCritForm.setIndexSelectedRemoveAssignment(null);
		
			return mapping.findForward("displayAssessmentCriteria");	
			}
	
	public ActionForward displayAssignment(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
			AssessmentCriteriaForm assessmentCritForm = (AssessmentCriteriaForm) form;
			//get assignment to display
			Assignment assignment_i = new Assignment();
			for (int i=0; i < assessmentCritForm.getListAssignment().size(); i++){				
				assignment_i = (Assignment)(assessmentCritForm.getListAssignment().get(i));	
				if (assignment_i.getNumber().equalsIgnoreCase(assessmentCritForm.getSelectAssignment())){
					Assignment assignment = new Assignment();					
					assignment.setAegrotatWeight(assignment_i.getAegrotatWeight());
					assignment.setDueDate(assignment_i.getDueDate());
					assignment.setFinalMarkingDate(assignment_i.getFinalMarkingDate());
					assignment.setGroup(assignment_i.getGroup());
					assignment.setFormat(assignment_i.getFormat());	
					assignment.setNegativeMarkingFactor(assignment_i.getNegativeMarkingFactor());
					assignment.setNormalWeight(assignment_i.getNormalWeight());
					assignment.setNumber(assignment_i.getNumber());
					assignment.setNumberQuestions(assignment_i.getNumberQuestions());
					assignment.setOptionality(assignment_i.getOptionality());
					assignment.setPeriod(assignment_i.getPeriod());
					assignment.setPrelimMarkingDate(assignment_i.getPrelimMarkingDate());
					assignment.setRepeatWeight(assignment_i.getRepeatWeight());
					assignment.setSubsidyAssignment(assignment_i.getSubsidyAssignment());
					assignment.setType(assignment_i.getType());
					assignment.setUniqueNumber(assignment_i.getUniqueNumber());
					assignment.setYear(assignment_i.getYear());
					assignment.setCaptureOnStudentSystem(assignment_i.isCaptureOnStudentSystem());
					assignment.setCreditSystem(assignment_i.getCreditSystem());
					assignment.setStudentSpecifyLang(assignment_i.getStudentSpecifyLang());
					assignment.setOnscreenMarkFlag(assignment_i.getOnscreenMarkFlag());	
					assignment.setReleaseFlag(assignment_i.getReleaseFlag());	
					assignment.setPfOpenDate(assignment_i.getPfOpenDate());
					//Change BRD 2016 2015/06/07
					assignment.setFinalSubmissionDate(assignment_i.getFinalSubmissionDate());
					assignment.setMaxFileSize(assignment_i.getMaxFileSize());
					assignment.setGroup(assignment_i.getGroup());
					if (assignment_i.getPfOpenDate()==null ||
							assignment_i.getPfOpenDate().equalsIgnoreCase("00010101")){
							assignment.setPfOpenDate("");
							}
					if (assignment_i.getFinalSubmissionDate()==null ||
							assignment_i.getFinalSubmissionDate().equalsIgnoreCase("00010101")){
							assignment.setFinalSubmissionDate("");
							}
					assignment.setFileReleaseDate(assignment_i.getFileReleaseDate());
					if (assignment_i.getFileReleaseDate()==null ||
							assignment_i.getFileReleaseDate().equalsIgnoreCase("00010101")){
							assignment.setFileReleaseDate("");
							}
					Integer assNumber = Integer.parseInt(assignment.getNumber());
					if (assNumber >= 91 && assNumber <= 95){
						assessmentCritForm.setSurvey(true);
					}else{
						assessmentCritForm.setSurvey(false);
					}
					if (assignment_i.getListAnswer()==null){
					}else{
							List listAnswer = new ArrayList();
							for (int j=0; j<assignment_i.getListAnswer().size(); j++){
								Memo memo = new Memo();
								memo.setQuestion(((Memo)(assignment_i.getListAnswer().get(j))).getQuestion());
								memo.setAnswer(((Memo)(assignment_i.getListAnswer().get(j))).getAnswer());
							    listAnswer.add(memo);
							}
							assignment.setListAnswer(listAnswer);
						}
					if (assignment_i.getListMarkers()==null){
					}else{
//						List listMarkers = new ArrayList();
//						for (int j=0; j<assignment_i.getListMarkers().size(); j++){
//							Person person = new Person();
//							Marker marker = new Marker();
//							marker.setDepartmentDesc(((Marker)(assignment_i.getListMarkers().get(j))).getDepartmentDesc());
//							marker.setMarkPercentage(((Marker)(assignment_i.getListMarkers().get(j))).getMarkPercentage());
//							marker.setStatus(((Marker)(assignment_i.getListMarkers().get(j))).getStatus());
//							person.setContactNumber(((Marker)(assignment_i.getListMarkers().get(j))).getPerson().getContactNumber());
//							person.setDepartmentCode(((Marker)(assignment_i.getListMarkers().get(j))).getPerson().getDepartmentCode());
//							person.setEmailAddress(((Marker)(assignment_i.getListMarkers().get(j))).getPerson().getEmailAddress());
//							person.setName(((Marker)(assignment_i.getListMarkers().get(j))).getPerson().getName());
//							person.setNovellUserId(((Marker)(assignment_i.getListMarkers().get(j))).getPerson().getNovellUserId());
//							person.setPersonnelNumber(((Marker)(assignment_i.getListMarkers().get(j))).getPerson().getPersonnelNumber());
//							marker.setPerson(person);
//							listMarkers.add(marker);
//						}
//						assignment.setListMarkers(listMarkers);
						assignment.setListMarkers(assignment_i.getListMarkers());
					}
					List listOtherFormat = new ArrayList<FileFormat>();
					if (assignment_i.getListFormat()==null){						
						//initialise file format selection
						String [] defaultSelectedFileFormats = new String [assessmentCritForm.getListFileFormat().size()];
												
						for (int j=0; j < assessmentCritForm.getListFileFormat().size(); j++){
							if ("PDF".equalsIgnoreCase(((Gencod)assessmentCritForm.getListFileFormat().get(j)).getCode())){
								defaultSelectedFileFormats[j]=String.valueOf(j);
							}
						}
						assessmentCritForm.setIndexNrSelectedFormat(defaultSelectedFileFormats);						
					}else{	
						List listFormat = new ArrayList<FileFormat>();	
						listFormat = assignment_i.getListFormat();
						assignment.setListFormat(listFormat);						
						String arrayFormat[] = new String[listFormat.size()];
						int index=0;
						for (int j=0; j < listFormat.size(); j++){
							FileFormat fileFormat = new FileFormat();
							fileFormat = (FileFormat)listFormat.get(j);					
							boolean found=false;
							for (int k=0; k < assessmentCritForm.getListFileFormat().size(); k++){	
								 Gencod fileFormatGc = new Gencod();
								 fileFormatGc = (Gencod)assessmentCritForm.getListFileFormat().get(k);
								if (fileFormat.getExtention().equalsIgnoreCase(fileFormatGc.getCode())){
									found=true;
									arrayFormat[index]=String.valueOf(k);		
									index=index + 1;
									k = assessmentCritForm.getListFileFormat().size();
								}						
							}
							if (found==false){
								listOtherFormat.add(fileFormat);						
							}
						}
						//Delete null value array references
						String[] remove = {null};
						List listTemp = new ArrayList(Arrays.asList(arrayFormat));
						listTemp.removeAll(new ArrayList(Arrays.asList(remove)));
						arrayFormat = (String[]) listTemp.toArray(new String[listTemp.size()]);
						assessmentCritForm.setIndexNrSelectedFormat(arrayFormat);											
					}
					if (listOtherFormat.isEmpty()){
						//initialise other formats
						FileFormat fileFormat = new FileFormat();
						fileFormat.setExtention("");
						fileFormat.setDescription("");
						listOtherFormat.add(fileFormat);						
					}
					assessmentCritForm.setListOtherFileFormat(listOtherFormat);	
					List listOtherLang = new ArrayList<String>();
					if (assignment_i.getListLanguage()==null){	
						//initialise language list to default values
						assessmentCritForm.setIndexNrSelectedLanguage(new String[assessmentCritForm.getListLanguage().size()]);						
						//Johanet code to initialise other languages
						if (listOtherLang.isEmpty()){
							//initialise other language list							
							String otherLanguage = "";
							listOtherLang.add(otherLanguage);
						}
						assessmentCritForm.setListOtherLanguage(listOtherLang);
					}else{
						List listLanguage = new ArrayList<SubmissionLanguage>();
						listLanguage = assignment_i.getListLanguage();		
						List listAssLang = new ArrayList<SubmissionLanguage>();
						assignment.setListLanguage(assignment_i.getListLanguage());
						String arrayLanguage[] = new String[listLanguage.size()];
						int index = 0;
						for (int j=0; j < listLanguage.size(); j++){
							SubmissionLanguage subLang = new SubmissionLanguage();
							subLang = (SubmissionLanguage)listLanguage.get(j);	
							if (subLang.getLangangeGc203().equalsIgnoreCase("OTHER")){
								listAssLang.add(subLang);
							}else{
								Gencod gencod = new Gencod();
								StudentSystemGeneralDAO dao = new StudentSystemGeneralDAO();
								gencod = dao.getGenCode("203", subLang.getLangangeGc203());
								subLang.setOtherLangDesc(gencod.getEngDescription());
								listAssLang.add(subLang);
							}
							boolean found=false;
							for (int k=0; k < assessmentCritForm.getListLanguage().size();k++){
								Gencod languageGc = new Gencod();
								languageGc = (Gencod)assessmentCritForm.getListLanguage().get(k);
								if (subLang.getLangangeGc203().equalsIgnoreCase(languageGc.getCode())){
									found=true;
									arrayLanguage[index]=String.valueOf(k);		
									index=index + 1;
									k = assessmentCritForm.getListLanguage().size();
								}
							}
							if (found==false){
								listOtherLang.add(subLang.getOtherLangDesc());
							}
							
						}
						if (listOtherLang.isEmpty()){
							//initialise other language list							
							String otherLanguage = "";
							listOtherLang.add(otherLanguage);
						}
						assessmentCritForm.setListOtherLanguage(listOtherLang);
						assignment.setListLanguage(listAssLang);
						//Delete null value array references
						String[] remove = {null};
						List listTemp = new ArrayList(Arrays.asList(arrayLanguage));
						listTemp.removeAll(new ArrayList(Arrays.asList(remove)));
						arrayLanguage = (String[]) listTemp.toArray(new String[listTemp.size()]);
						assessmentCritForm.setIndexNrSelectedLanguage(arrayLanguage);
						
					}
//					if (assessmentCritForm.getListOtherFileFormat()==null || 
//							assessmentCritForm.getListOtherFileFormat().size()==0){
//						List otherFileFormats = new ArrayList<FileFormat>();
//						FileFormat fileFormat = new FileFormat();
//						fileFormat.setExtention("");
//						fileFormat.setDescription("");
//						otherFileFormats.add(fileFormat);
//						assessmentCritForm.setListOtherFileFormat(otherFileFormats);
//					}
//					if (assessmentCritForm.getListOtherLanguage() == null ||
//							assessmentCritForm.getListOtherLanguage().size()==0){
//						List otherLanguages = new ArrayList<String>();
//						String language = "";
//						otherLanguages.add(language);
//						assessmentCritForm.setListOtherLanguage(otherLanguages);
//					}
					assessmentCritForm.setAssignment(assignment);
					i = assessmentCritForm.getListAssignment().size();
				}
			}
			
			//set Year mark contribution flag
			if (assessmentCritForm.getAssignment().getAegrotatWeight().equalsIgnoreCase("0") &&
					assessmentCritForm.getAssignment().getNormalWeight().equalsIgnoreCase("0") &&
					assessmentCritForm.getAssignment().getRepeatWeight().equalsIgnoreCase("0")){
				assessmentCritForm.setYearMarkContributionYesNoFlag("N");					
			} else{
				assessmentCritForm.setYearMarkContributionYesNoFlag("Y");
				assessmentCritForm.setSelfAssesmentYesNoFlag("");
			}
						
			//set Self assessment flag
//			if (assessmentCritForm.getYearMarkContributionYesNoFlag().equalsIgnoreCase("N")){
//				if (assessmentCritForm.getAssignment().getOptionality().equalsIgnoreCase("O") &&
//						assessmentCritForm.getAssignment().getCreditSystem().equalsIgnoreCase("5")){
//					//reset self assessment flag not longer used
//							assessmentCritForm.setSelfAssesmentYesNoFlag("");
//					//not used any more comment out
////							assessmentCritForm.setSelfAssesmentYesNoFlag("Y");
//				}
//				if (assessmentCritForm.getAssignment().getOptionality().equalsIgnoreCase("M")){
//						assessmentCritForm.getAssignment().setOptionality("");
//				}				
//			}			
			
			//Get month list
//			List monthList = new ArrayList();
//			StudentSystemGeneralDAO dao = new StudentSystemGeneralDAO();
//			for (int i=0; i < dao.getGenCodes((short)2,2).size(); i++) {
//				monthList.add(i, (Gencod)(dao.getGenCodes((short)2,2).get(i)));
//			}
//			request.setAttribute("monthList", monthList);
//			
//			//get year, month and day of due date
//			int dueDateYear = Integer.parseInt((assessmentCritForm.getAssignment().getDueDate().substring(0, 4)));
//			int dueDateMonth = Integer.parseInt((assessmentCritForm.getAssignment().getDueDate().substring(4, 6)));
//			int dueDateDay =Integer.parseInt((assessmentCritForm.getAssignment().getDueDate().substring(6)));
//						
//			request.setAttribute("dueDateYear", String.valueOf(dueDateYear));
//			request.setAttribute("dueDateMonth", String.valueOf(dueDateMonth));
//			request.setAttribute("dueDateDay", String.valueOf(dueDateDay));	
			
			assessmentCritForm.setCurrentPage("assignment");
			return mapping.findForward("displayAssignment");			
			}
	
	public String displayMarkPercentages(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) throws Exception {
	
	ActionMessages messages = new ActionMessages();
	AssessmentCriteriaForm assessmentCritForm = (AssessmentCriteriaForm) form;
	
	if (assessmentCritForm.getAssignmentAction().equalsIgnoreCase("view")){
		//do nothing
	}else{		
		//do validation for onscreen marking
		//if submission dates applicable
		//validate submission dates
		if (!assessmentCritForm.getSubmissionDateIndicator().equalsIgnoreCase("na")){			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
			Calendar calDueDate = Calendar.getInstance();
			Date dueDate = formatter.parse(assessmentCritForm.getAssignment().getDueDate());
			calDueDate.setTime(dueDate);	
			
			//Validate Opening Date
			assessmentCritForm.getAssignment().setPfOpenDate(assessmentCritForm.getAssignment().getPfOpenDate().trim());
			boolean validOpenDate = true;
			if (!assessmentCritForm.getAssignment().getPfOpenDate().equalsIgnoreCase("")){
				if (assessmentCritForm.getSubmissionDateIndicator().equalsIgnoreCase("date")){
					if (assessmentCritForm.getAssignment().getPfOpenDate().length()!=8 ||
							!isValidDate(assessmentCritForm.getAssignment().getPfOpenDate(),"yyyyMMdd")){
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage",
									"Invalid submission opening date"));	
						validOpenDate = false;
					}				
				}
				if (assessmentCritForm.getSubmissionDateIndicator().equalsIgnoreCase("dateTime")){				
						if (assessmentCritForm.getAssignment().getPfOpenDate().length()!=14 ||
								!isValidDate(assessmentCritForm.getAssignment().getPfOpenDate(),"yyyyMMdd HH:mm")){
							messages.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("message.generalmessage",
										"Invalid submission opening date"));
							validOpenDate = false;
						}
				}
				if (validOpenDate){					
					
					Calendar calReleaseDate = Calendar.getInstance();
					Date pfOpenDate = formatter.parse(assessmentCritForm.getAssignment().getPfOpenDate());
					calReleaseDate.setTime(pfOpenDate);
								
                    if (assessmentCritForm.getAssignment().getGroup().equalsIgnoreCase("F")){
                    	if (!pfOpenDate.before(dueDate)){
    						messages.add(ActionMessages.GLOBAL_MESSAGE,
    								new ActionMessage("message.generalmessage",
    									"Submission opening date should be before the due date."));	
    					}
                    } else if (assessmentCritForm.getAssignment().getGroup().equalsIgnoreCase("S")) {
                    	if (pfOpenDate.after(dueDate)){
    						messages.add(ActionMessages.GLOBAL_MESSAGE,
    								new ActionMessage("message.generalmessage",
    									"Submission opening date should be on or before the due date."));	
    					}
                    }					
				}	
			}
			//Validate Closing Date				
			assessmentCritForm.getAssignment().setFinalSubmissionDate(assessmentCritForm.getAssignment().getFinalSubmissionDate().trim());
			//Change 20170607 - 2018 Changes
			if (assessmentCritForm.getAssignment().getFinalSubmissionDate().equalsIgnoreCase("") &&	
					assessmentCritForm.getAssignment().getGroup().equalsIgnoreCase("F") &&								
						(assessmentCritForm.getFinalMarkComp().getExamAdmissionMethod().equalsIgnoreCase("AM") ||
								(assessmentCritForm.getFinalMarkComp().getExamAdmissionMethod().equalsIgnoreCase("YM") &&
										assessmentCritForm.getYearMarkContributionYesNoFlag().equalsIgnoreCase("Y")))){
								messages.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("message.generalmessage",
											"If examination admission is given on the 'Submission of more than one assessment' or on a 'Year mark sub-minimum achieved', then all formative assessments submission cut-off dates must be set to at least 1 day before the examination admission date(" + assessmentCritForm.getExamAdmissionDate() + ")."));									
			}		
			boolean validCloseDate = true;
			if (!assessmentCritForm.getAssignment().getFinalSubmissionDate().equalsIgnoreCase("")){
				if (assessmentCritForm.getSubmissionDateIndicator().equalsIgnoreCase("date")){
					if (assessmentCritForm.getAssignment().getFinalSubmissionDate().length()!=8 ||
							!isValidDate(assessmentCritForm.getAssignment().getFinalSubmissionDate(),"yyyyMMdd")){
							messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage",
									"Invalid submission cut-off date"));	
							validCloseDate=false;	
					}		
				}
				if (assessmentCritForm.getSubmissionDateIndicator().equalsIgnoreCase("dateTime")){	
					if (assessmentCritForm.getAssignment().getFinalSubmissionDate().length()!=14 ||
							!isValidDate(assessmentCritForm.getAssignment().getFinalSubmissionDate(),"yyyyMMdd HH:mm")){
								messages.add(ActionMessages.GLOBAL_MESSAGE,
										new ActionMessage("message.generalmessage",
											"Invalid submission cut-off date"));	
							validCloseDate=false;	
					}
				}	
				
				if (validCloseDate) {
					Calendar calFinalSubDate = Calendar.getInstance();
					Date finalSubmissionDate = formatter.parse(assessmentCritForm.getAssignment().getFinalSubmissionDate());
					calFinalSubDate.setTime(finalSubmissionDate);
						
					Calendar calFinalDueDate = Calendar.getInstance();
					String strFinalDueDate = "";
	
					if(assessmentCritForm.getAssignment().getGroup().equalsIgnoreCase("S")) {						
						strFinalDueDate = assessmentCritForm.getLastPortfolioDueControlDate();
					} else {
						strFinalDueDate = assessmentCritForm.getLastAssDueControlDate();
					}
					Date finalDueDate = formatter.parse(strFinalDueDate);
					calFinalDueDate.setTime(finalDueDate);				
	
					if (calFinalSubDate.after(calFinalDueDate)){
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage",
									"Submission cut-off date may not be later than the last assessment submission date(" + strFinalDueDate + ")"));	
					}
						
					if (assessmentCritForm.getAssignment().getGroup().equalsIgnoreCase("F")){
						if (!calFinalSubDate.after(calDueDate)){
							messages.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("message.generalmessage",
										"Submission cut-off date must be after the due date."));	
						}
					} else if (assessmentCritForm.getAssignment().getGroup().equalsIgnoreCase("S")){
						if (calFinalSubDate.before(calDueDate)){
							messages.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("message.generalmessage",
										"Submission cut-off date must be on or after the due date."));	
						}
	
					}
					//20170605 - 2018 Changes
					//if a module chose Admission system AM or YM the cutoff dates must be at least one calendar day earlier than the exam admission date
					Date finalSubmissionVerifyDate = formatter.parse(assessmentCritForm.getExamAdmissionDate());

					if (assessmentCritForm.getAssignment().getGroup().equalsIgnoreCase("F") &&
							(assessmentCritForm.getFinalMarkComp().getExamAdmissionMethod().equalsIgnoreCase("AM") ||
									(assessmentCritForm.getFinalMarkComp().getExamAdmissionMethod().equalsIgnoreCase("YM") &&
											assessmentCritForm.getYearMarkContributionYesNoFlag().equalsIgnoreCase("Y")))){
						if (finalSubmissionDate.before(finalSubmissionVerifyDate)) {
								//ok
							} else {
								messages.add(ActionMessages.GLOBAL_MESSAGE,
										new ActionMessage("message.generalmessage",
												"If examination admission is given on the 'Submission of more than one assessment' or on a 'Year mark sub-minimum achieved', then all formative assessments submission cut-off dates must be set to at least 1 day before the examination admission date(" + assessmentCritForm.getExamAdmissionDate() + ")."));									
								
							}
					}
				} //end valid closing date	
			} //final submission cut-off date not empty
		}//end submission dates applicable	

		//Disabled fields do not submit - change fields set by javascript
		if (assessmentCritForm.getAssignment().getReleaseFlag()!=null && assessmentCritForm.getAssignment().getReleaseFlag().equalsIgnoreCase("N")){
			assessmentCritForm.getAssignment().setFileReleaseDate("");
		}		
		
		//Change 2017 BR check on for summative assessments and not for portfolio group
		//allow research proposal modules on academic levels H, M and D with non-venue based examinations, to indicate if marked assessment files 
		//should be returned to the student after marking		
		if ((assessmentCritForm.getAssignment().getGroup().equalsIgnoreCase("S")) &&
				assessmentCritForm.getStudyUnit().getPostGraduateFlag().equalsIgnoreCase("Y") && 
				assessmentCritForm.getStudyUnit().getResearchFlag().equalsIgnoreCase("P")){
			if (assessmentCritForm.getAssignment().getReleaseFlag() == null || assessmentCritForm.getAssignment().getReleaseFlag().trim().equalsIgnoreCase("")){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
							"Please indicate whether marked assessment files should be released to students."));	
			} else if (assessmentCritForm.getAssignment().getReleaseFlag().equalsIgnoreCase("Y") && 
					(assessmentCritForm.getAssignment().getFileReleaseDate().equalsIgnoreCase("") || assessmentCritForm.getAssignment().getFileReleaseDate()==null)){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
							"Please indicate when marked assessment files should be released to students."));	
			}			
		}
		
		if (!assessmentCritForm.getAssignment().getFileReleaseDate().equalsIgnoreCase("")){
			if (!isValidDate(assessmentCritForm.getAssignment().getFileReleaseDate(),"yyyyMMdd")){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
								"Invalid assessment file release date"));	
			}else{
				Calendar calReleaseDate = Calendar.getInstance();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
				Date fileReleaseDate = formatter.parse(assessmentCritForm.getAssignment().getFileReleaseDate());
				calReleaseDate.setTime(fileReleaseDate);
				Calendar calDueDate = Calendar.getInstance();
				Date dueDate = formatter.parse(assessmentCritForm.getAssignment().getDueDate());
				calDueDate.setTime(dueDate);
				calDueDate.add(calDueDate.DATE, 21);
					
					if (calReleaseDate.after(calDueDate)){
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage",
									"Assessment file release date may not be more than 21 days after the due date."));				
					}
			}
		}	
			
			if (!messages.isEmpty()) {
				addErrors(request,messages);
				assessmentCritForm.setResetOnscreenMarkingArrays(true);
				assessmentCritForm.setCurrentPage("onscreenMarking");
				return "displayOnscreenMarking";
			}
			
			// set file formats for assignment
			List fileFormatList = new ArrayList<Gencod>();
			for (int i=0; i <assessmentCritForm.getIndexNrSelectedFormat().length; i++) {
				String array[] = assessmentCritForm.getIndexNrSelectedFormat();
				Gencod gencod = new Gencod();
				gencod = (Gencod)assessmentCritForm.getListFileFormat().get(Integer.parseInt(array[i]));
				FileFormat fileFormat = new FileFormat();
				fileFormat.setExtention(gencod.getCode());
				fileFormat.setDescription(gencod.getEngDescription());
				fileFormatList.add(fileFormat);
			}
			
			for (int i=0; i < assessmentCritForm.getListOtherFileFormat().size(); i++){
				FileFormat otherFormat = new FileFormat();
				otherFormat=(FileFormat)assessmentCritForm.getListOtherFileFormat().get(i);
				//remove all spaces from other file format list	
				if (otherFormat!=null && 
						otherFormat.getExtention()!=null &&
						otherFormat.getExtention().trim().equalsIgnoreCase("")&&
						i > 0){
					assessmentCritForm.getListOtherFileFormat().remove(i);
				}else{
					//other format must have a description
					if (otherFormat!=null && 
							otherFormat.getExtention()!=null &&
							!otherFormat.getExtention().trim().equalsIgnoreCase("")){
						 	if (!isAlphanumeric(otherFormat.getExtention())){
						 		messages.add(ActionMessages.GLOBAL_MESSAGE,
					 					new ActionMessage("message.generalmessage",
										"Invalid file format: '" + otherFormat.getExtention().trim() + "'. A file format may only contain alphanumeric characters."));
						 	}
						 	if (otherFormat.getDescription()!=null &&
							otherFormat.getDescription().trim().equalsIgnoreCase("")){
						 			messages.add(ActionMessages.GLOBAL_MESSAGE,
						 					new ActionMessage("message.generalmessage",
											"A description is required for file format: '" + otherFormat.getExtention().trim() + "'."));
						 	}						
					}
					//add otherFile formats to acceptable file submission formats for assignment
					if (otherFormat!=null && 
							otherFormat.getExtention()!=null && 
							!otherFormat.getExtention().trim().equalsIgnoreCase("") &&
							isAlphanumeric(otherFormat.getExtention()) &&
							otherFormat.getDescription()!=null &&
							!otherFormat.getDescription().trim().equalsIgnoreCase("")){
						//check that other format is not already on selection list
						for (int j=0; j < assessmentCritForm.getListFileFormat().size(); j++){
							Gencod gencod = new Gencod();
							gencod = (Gencod)assessmentCritForm.getListFileFormat().get(j);
							if (otherFormat.getExtention().trim().equalsIgnoreCase(gencod.getCode())){
								messages.add(ActionMessages.GLOBAL_MESSAGE,
										new ActionMessage("message.generalmessage",
													"Select file format: " + otherFormat.getExtention() + " from selection list. Remove " + gencod.getCode() + " from other file formats.")); 
							}
						}
						boolean found = false;
						//check for duplicate file formats
						found = false;
						for (int j =0; j < fileFormatList.size(); j++){
							FileFormat format = new FileFormat();
							format = (FileFormat)fileFormatList.get(j);
							if (otherFormat.getExtention().trim().equalsIgnoreCase(format.getExtention().trim())){
								found =true;
								j=fileFormatList.size();
							}
						}
						if (found){
							messages.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("message.generalmessage",
												"Duplicate file format.             "));
						}else{
							fileFormatList.add(otherFormat);
						}
							
					}	
				}	
			}	
			assessmentCritForm.getAssignment().setListFormat(fileFormatList);
			// at least one format should be selected
			if (assessmentCritForm.getAssignment().getListFormat().isEmpty()){		
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"At least one submission file format should be specified for this assessment."));
			}
			
			//Assessment Plan changes 2016 - 20150601
			//Max File size : User define file size may not exceed 50Mb.
			if (assessmentCritForm.getAssignment().getMaxFileSize()==null || 
					assessmentCritForm.getAssignment().getMaxFileSize().trim().equalsIgnoreCase("")) {
				// nothing - default value apply //
			} else {
					if (!isInteger(assessmentCritForm.getAssignment().getMaxFileSize())) {
						messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
									"Maximum file size that should be allowed per file during submission should be an integer."));
					} else {
						    if (Integer.parseInt(assessmentCritForm.getAssignment().getMaxFileSize()) > 50) {
								messages.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("message.generalmessage",
										"The maximum file size may not exceed 50Mb"));
							}
						    if (Integer.parseInt(assessmentCritForm.getAssignment().getMaxFileSize()) < 1) {
								messages.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("message.generalmessage",
										"The maximum file size may not be less than 1Mb"));
							}
					}	
			}
			
			//set assignment languages
			List languageList = new ArrayList<Gencod>();
			for (int i=0; i <assessmentCritForm.getIndexNrSelectedLanguage().length; i++) {
				String array[] = assessmentCritForm.getIndexNrSelectedLanguage();
				SubmissionLanguage language = new SubmissionLanguage();
				Gencod languageGc = new Gencod();
				languageGc = (Gencod)assessmentCritForm.getListLanguage().get(Integer.parseInt(array[i]));
				language.setLangangeGc203(languageGc.getCode());
				language.setOtherLangDesc(languageGc.getEngDescription());
				languageList.add(language);
			}
			
			//remove all spaces from other language list
			for (int i=0; i < assessmentCritForm.getListOtherLanguage().size(); i++){
				String otherLanguage="";
				otherLanguage=(String)assessmentCritForm.getListOtherLanguage().get(i);
				if (otherLanguage!=null && otherLanguage.trim().equalsIgnoreCase("") &&
						i > 0){
					assessmentCritForm.getListOtherLanguage().remove(i);
				}
			}	
			
			for (int i=0; i < assessmentCritForm.getListOtherLanguage().size(); i++){
				String otherLanguage="";
				otherLanguage=((String)assessmentCritForm.getListOtherLanguage().get(i)).toUpperCase();
				assessmentCritForm.getListOtherLanguage().set(i, otherLanguage);
				if (otherLanguage!=null && !otherLanguage.trim().equalsIgnoreCase("")){
					SubmissionLanguage language = new SubmissionLanguage();
					language.setLangangeGc203("OTHER");
					language.setOtherLangDesc(otherLanguage);
					if (!isAlphabetic(otherLanguage)){
						messages.add(ActionMessages.GLOBAL_MESSAGE,
			 					new ActionMessage("message.generalmessage",
								"Invalid language: '" + otherLanguage + "'. Language field may only contain alphabetic characters."));
					}
					//check that other language is not already in selection list			
					for (int j=0; j < assessmentCritForm.getListLanguage().size();j++){
						Gencod gencod = new Gencod();
						gencod = (Gencod)assessmentCritForm.getListLanguage().get(j);
						if (language.getOtherLangDesc().trim().equalsIgnoreCase(gencod.getEngDescription().trim())){
							messages.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("message.generalmessage",
												"Select language: " + language.getOtherLangDesc() + " from selection list. Remove " + language.getOtherLangDesc() + " from ohter languages.")); 
						j= assessmentCritForm.getListLanguage().size();
						}
					}
					//check for duplicate languages
					boolean found=false;
					found = false;
					for (int j=0; j < languageList.size(); j++){
						SubmissionLanguage lang = new SubmissionLanguage();
						lang = (SubmissionLanguage)languageList.get(j);
						if (lang.getOtherLangDesc().trim().equalsIgnoreCase(language.getOtherLangDesc().trim())){
							found=true;
							j=languageList.size();
						}
					}
					if (found){
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage",
											"Duplicate language."));   
					}else{
						languageList.add(language);
					}			
				}
			}			
			assessmentCritForm.getAssignment().setListLanguage(languageList);
			if (assessmentCritForm.getAssignment().getStudentSpecifyLang()==null || assessmentCritForm.getAssignment().getStudentSpecifyLang().trim().equalsIgnoreCase("")){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"Please indicate whether the student should specify the language used in the assessment file on submission."));
			}else{
				if (assessmentCritForm.getAssignment().getStudentSpecifyLang().equalsIgnoreCase("Y")){
					if (assessmentCritForm.getAssignment().getListLanguage().isEmpty()){
						new ActionMessage("message.generalmessage",
						"At least one language must be accommodated by the markers of the assessment.");				
					}
				}
			}
			
			if (!messages.isEmpty()) {
				addErrors(request,messages);
				assessmentCritForm.setResetOnscreenMarkingArrays(true);
				assessmentCritForm.setCurrentPage("onscreenMarking");
				return "displayOnscreenMarking";
			}	
			//get potential markers - primary and secondary lecturers on usrsun
			List listPotentialMarkers = new ArrayList();
			AssessmentCriteriaDAO daoAssessCrit = new AssessmentCriteriaDAO();
			listPotentialMarkers = daoAssessCrit.getPotentialMarkers(Integer.parseInt(assessmentCritForm.getAcademicYear()),
					Short.parseShort(assessmentCritForm.getSemester()),
					assessmentCritForm.getStudyUnit().getCode());
			//add new assignment - display only potential markers
			//all users(PRIML,SECDL) on USRSUN 
			if (assessmentCritForm.getAssignmentAction().equalsIgnoreCase("add")){
				assessmentCritForm.getAssignment().setListMarkers(listPotentialMarkers);
			} else {
				//edit assignment - display markers as read from assmrk + potential markers not on assmrk
				List listMarkers = new ArrayList();
				listMarkers = assessmentCritForm.getAssignment().getListMarkers();
				if (listMarkers==null){
					assessmentCritForm.getAssignment().setListMarkers(listPotentialMarkers);
				}else {
					for (int i=0 ; i < listPotentialMarkers.size(); i++){
						String markerFound ="N";
						for (int j=0; j < listMarkers.size(); j++){
							if (((Marker)listMarkers.get(j)).getPerson().getPersonnelNumber()!=null) {
								if (((Marker)listPotentialMarkers.get(i)).getPerson().getPersonnelNumber().trim().equalsIgnoreCase(((Marker)listMarkers.get(j)).getPerson().getPersonnelNumber().trim())){
									markerFound="Y";
									j=listMarkers.size();
								}	
							}												
						}
						if (markerFound.equalsIgnoreCase("N")){
							listMarkers.add(listPotentialMarkers.get(i));
						}					
					}
					assessmentCritForm.getAssignment().setListMarkers(listMarkers);
				}				
			}
		}	
	
		assessmentCritForm.setCurrentPage("writtenMarking");
		return "displayWrittenMarkingDetails";
	}
	
	public String displayAssignmentMarkingDetails(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		ActionMessages messages = new ActionMessages();
		AssessmentCriteriaForm assessmentCritForm = (AssessmentCriteriaForm) form;
		
		//depending on the Yearmark/portfolio contribution certain fields are disabled.
		//Disabled fields do not submit - repeat javascrit default field settings
		if (assessmentCritForm.getYearMarkContributionYesNoFlag().equalsIgnoreCase("N")){
			assessmentCritForm.getAssignment().setNormalWeight("0");
			assessmentCritForm.getAssignment().setRepeatWeight("0");
			assessmentCritForm.getAssignment().setAegrotatWeight("0");
			assessmentCritForm.getAssignment().setOptionality("");
		}		
		
		//Only do validation on add & update
		if (!assessmentCritForm.getAssignmentAction().equalsIgnoreCase("view")){
			//remove leading and trailing spaces
			assessmentCritForm.getAssignment().setNumber(assessmentCritForm.getAssignment().getNumber().trim());
			assessmentCritForm.getAssignment().setNormalWeight(assessmentCritForm.getAssignment().getNormalWeight().trim());
			assessmentCritForm.getAssignment().setRepeatWeight(assessmentCritForm.getAssignment().getRepeatWeight().trim());
			assessmentCritForm.getAssignment().setAegrotatWeight(assessmentCritForm.getAssignment().getAegrotatWeight().trim());
			
			if (assessmentCritForm.getAssignment().getNormalWeight().equalsIgnoreCase("")){
				assessmentCritForm.getAssignment().setNormalWeight("0");
			}
			if (assessmentCritForm.getAssignment().getRepeatWeight().equalsIgnoreCase("")){
				assessmentCritForm.getAssignment().setRepeatWeight("0");
			}
			if (assessmentCritForm.getAssignment().getAegrotatWeight().equalsIgnoreCase("")){
				assessmentCritForm.getAssignment().setAegrotatWeight("0");
			}
			
			//Do validation assignment data input
			if (assessmentCritForm.getAssignment().getNumber()==null || assessmentCritForm.getAssignment().getNumber().equalsIgnoreCase("")){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"Assessment Number is required"));
			} else {
				if(assessmentCritForm.getAssignment().getNumber().equalsIgnoreCase("0")){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"Assessment Number may not be 0"));
				}
				if (!isInteger(assessmentCritForm.getAssignment().getNumber())){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
									"Assessment Number must be numeric"));
				}
			}
			
			if (messages.isEmpty()){
				Integer assNumber=Integer.parseInt(assessmentCritForm.getAssignment().getNumber());
				assessmentCritForm.getAssignment().setNumber(String.valueOf(assNumber));
				if (assessmentCritForm.isSurvey()){					
					if (assNumber < 91 || assNumber > 95){
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage",
										"Only Assessment numbers 91 till 95 is reserved for surveys."));
					}
				} else {
					if (assNumber >= 91 && assNumber <= 95){
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage",
										"Assessment numbers 91 till 95 is reserved for surveys."));
					}
				}
			}
						
			//Check assignment number does not already exist on add assignment
			for (int i=0; i <assessmentCritForm.getListAssignment().size();i++){
				if (((Assignment)(assessmentCritForm.getListAssignment().get(i))).getNumber().equalsIgnoreCase(assessmentCritForm.getAssignment().getNumber()) &&
						assessmentCritForm.getAssignmentAction().equalsIgnoreCase("add")){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
									"Assessment Number already used"));
				}
				
			}
			

			//Validate assessment group is required
			if (assessmentCritForm.getAssignment().getGroup()==null || assessmentCritForm.getAssignment().getGroup().trim().equalsIgnoreCase("")){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"Assessment Group is required"));
			} else if (assessmentCritForm.getAssignment().getGroup().equalsIgnoreCase("S")) {
				if (assessmentCritForm.getExamBase().equalsIgnoreCase("VENUE")){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"Examination is set to venue based on AIMS. Summative assessments are not allowed."));
				}
				//20170607 - 2018 Changes Continuous assessment - summative assessments not allowed
				if (assessmentCritForm.getExamBase().equalsIgnoreCase("CONT")){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"Module is flagged for continuous assessment, summative assessments are not allowed."));
				}
			}
						
			if (assessmentCritForm.getAssignment().getFormat()==null || assessmentCritForm.getAssignment().getFormat().equalsIgnoreCase("")){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"Assessment Format is required"));
			}else{
				/*Assessment Plan Changes 2016 - 20150601 */
				/*Remove test - Allow all modules to choose online formative assessment methods whether AIMS flag is set to online or mixed*/
//				if (assessmentCritForm.isSunpdtExists()){
//					//can only perform test if sunpdt exists, test will be performed again on authorisation
//					if ((assessmentCritForm.getAssignment().getFormat().equalsIgnoreCase("DF") ||
//							assessmentCritForm.getAssignment().getFormat().equalsIgnoreCase("BL") ||
//							assessmentCritForm.getAssignment().getFormat().equalsIgnoreCase("SA") ||
//							assessmentCritForm.getAssignment().getFormat().equalsIgnoreCase("XA") ||
//							assessmentCritForm.getAssignment().getFormat().equalsIgnoreCase("MS"))){
//						if (!assessmentCritForm.getFormativeAssessInd().equalsIgnoreCase("ONLINE"))
//						messages.add(ActionMessages.GLOBAL_MESSAGE,
//								new ActionMessage("message.generalmessage",
//											"Assignment formats: Discussion Forum, Blog, SAmigo Assessment, External Assessment and myUnisa File Submission only, is only allowed if the Formative Assessment flag on AIMS is set to ONLINE."));
//					}
//				}
				if (assessmentCritForm.isSurvey()){
					if (!assessmentCritForm.getAssignment().getFormat().equalsIgnoreCase("A")){
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage",
											"A survey may only be a Multiple choice questionnaire(MCQ)."));
					}
				}
			}
		
			if (!isValidDate(assessmentCritForm.getAssignment().getDueDate(),"yyyyMMdd")){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"Invalid due date"));			
			}
			if (!messages.isEmpty()) {
				addErrors(request,messages);	
				assessmentCritForm.setCurrentPage("assignment");
				return "displayAssignment";				
			}			
			Calendar calDueDate = Calendar.getInstance();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
			Date dueDate = formatter.parse(assessmentCritForm.getAssignment().getDueDate());		
			calDueDate.setTime(dueDate);	
			
			Calendar calendar = Calendar.getInstance();
			Date startAcademicYear = formatter.parse(assessmentCritForm.getAcademicYear() + "0101");
			calendar.setTime(startAcademicYear);
			
			if (dueDate.before(startAcademicYear)){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"Assessment cannot be due before start of the academic year."));	
			}
		
			//Validate due date against assignment due date control dates 
			//Do not validate due date for tests
			if ("T".equalsIgnoreCase(assessmentCritForm.getAssignment().getType())){
				//do nothing
			}else{
				//do not validate due dates against due date control dates for:
				//F - Postgrade Research (Including Proposals, Limited and Full Resuearch)
				//F - Formal Tuition Experiental Learning Roll Over courses
				//R - RPL courses
				//P - Pre-Admission courses
				//T - Temporary
				//L - Tut Letters
				//due dates may not fall on a weekend still apply for above mention courses
				if ((assessmentCritForm.getStudyUnit().getFormalTuitionFlag().equalsIgnoreCase("F")&&
						!assessmentCritForm.getStudyUnit().getResearchFlag().equalsIgnoreCase("N") &&
						(assessmentCritForm.getStudyUnit().getAcademicLevel().equalsIgnoreCase("H")||
						assessmentCritForm.getStudyUnit().getAcademicLevel().equalsIgnoreCase("M")||
						assessmentCritForm.getStudyUnit().getAcademicLevel().equalsIgnoreCase("D")))||
						(assessmentCritForm.getStudyUnit().getFormalTuitionFlag().equalsIgnoreCase("F")&&
								assessmentCritForm.getStudyUnit().getExperiental_duration()>1)||
						assessmentCritForm.getStudyUnit().getFormalTuitionFlag().equalsIgnoreCase("R")||
						assessmentCritForm.getStudyUnit().getFormalTuitionFlag().equalsIgnoreCase("P")||
						assessmentCritForm.getStudyUnit().getFormalTuitionFlag().equalsIgnoreCase("T")||
						assessmentCritForm.getStudyUnit().getFormalTuitionFlag().equalsIgnoreCase("L")||
						assessmentCritForm.getStudyUnit().getFormalTuitionFlag().equalsIgnoreCase("E")||
						assessmentCritForm.isSurvey()){
					//do not test against control due dates
				} else {
					if (assessmentCritForm.getAssignment().getGroup()==null || assessmentCritForm.getAssignment().getGroup().equalsIgnoreCase("")){
						//Do nothing type not known, do not no against which date to test
//					} else if ("PF".equalsIgnoreCase(assessmentCritForm.getAssignment().getType()) ||
//							"PJ".equalsIgnoreCase(assessmentCritForm.getAssignment().getType()) ||
//							"PC".equalsIgnoreCase(assessmentCritForm.getAssignment().getType()) ){
					} else if (assessmentCritForm.getAssignment().getGroup().equalsIgnoreCase("S")) {
						//Test porfolio's, practical's and projects against last due date
						Date lastControlDate = formatter.parse(assessmentCritForm.getLastPortfolioDueControlDate());
						if (dueDate.after(lastControlDate)){
							messages.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("message.generalmessage",
												"All summative assessments must be due on or before " + assessmentCritForm.getLastPortfolioDueControlDate()));
						}	
					}else {
						//20160622 changes for 2017
						//Verify that all due dates for formative assessments with admission system AM(Submission of more than one assignment) are set before exam admission date for that academic period
						//Verify that all due dates for formative assessments with admission system YM(Year mark subminimum achieved) are set at least 7 days before exam admission date for that academic period
						calendar = Calendar.getInstance();
						Date examAdmissionDate = formatter.parse(assessmentCritForm.getExamAdmissionDate());						
						if (assessmentCritForm.getFinalMarkComp().getExamAdmissionMethod().equalsIgnoreCase("AM") ||
							assessmentCritForm.getFinalMarkComp().getExamAdmissionMethod().equalsIgnoreCase("YM")){
							if (assessmentCritForm.getFinalMarkComp().getExamAdmissionMethod().equalsIgnoreCase("YM") &&
									assessmentCritForm.getYearMarkContributionYesNoFlag().equalsIgnoreCase("Y")){
								calendar.setTime(examAdmissionDate);
								calendar.add(Calendar.DATE, -6);
								examAdmissionDate = calendar.getTime();
								if (dueDate.before(examAdmissionDate)) {
									//ok
								} else {
									messages.add(ActionMessages.GLOBAL_MESSAGE,
											new ActionMessage("message.generalmessage",
													"If examination admission is given on a 'Year mark sub-minimum achieved', then all formative assessment due dates must be set to at least 7 days before the examination admission date(" + assessmentCritForm.getExamAdmissionDate() + ")."));									
									
								}
							} 
							//20170605 - change for 2018
							//Verify that all due dates for formative assessments with admission system AM(Submission of more than one assignment) are set at least 3 days before exam admission date for that academic period
							if (assessmentCritForm.getFinalMarkComp().getExamAdmissionMethod().equalsIgnoreCase("AM")){
								calendar.setTime(examAdmissionDate);
								calendar.add(Calendar.DATE, -2);
								examAdmissionDate = calendar.getTime();
								if (dueDate.before(examAdmissionDate)) {
									//ok
								} else {
									messages.add(ActionMessages.GLOBAL_MESSAGE,
											new ActionMessage("message.generalmessage",
													"If examination admission is given on the 'Submission of more than one assessment', then all formative assessment due dates must be set to at least 3 days before the examination admission date(" + assessmentCritForm.getExamAdmissionDate() + ")."));									
									
								}
							}							
//							} else {
//								if (dueDate.before(examAdmissionDate)) {
//									//ok
//								} else {
//									messages.add(ActionMessages.GLOBAL_MESSAGE,
//											new ActionMessage("message.generalmessage",
//													"If examination admission is given on the 'Submission of more than one assessment', then all formative assessment due dates must be before the examination admission date(" + assessmentCritForm.getExamAdmissionDate() + ")."));									
//								}
//							}
						}
						
						Date lastControlDate = formatter.parse(assessmentCritForm.getLastAssDueControlDate());
						if (assessmentCritForm.getFinalMarkComp().getExamAdmissionMethod().equalsIgnoreCase("A1") ||
								examAdmissionDate.after(lastControlDate)){
							if (dueDate.after(lastControlDate)){
								messages.add(ActionMessages.GLOBAL_MESSAGE,
										new ActionMessage("message.generalmessage",
													"All formative assignments must be due on or before " + assessmentCritForm.getLastAssDueControlDate()));
							}
						}
						
					}
				}
				if (assessmentCritForm.getAssignment().getFormat()!=null && !assessmentCritForm.getAssignment().getFormat().equalsIgnoreCase("XA")){
					//Validate that due date do not fall on a weekend					
					if (calDueDate.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY || calDueDate.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY){
						if (assessmentCritForm.getAssignment().getDueDate().equalsIgnoreCase("20090801") && assessmentCritForm.getSemester().equalsIgnoreCase("0")){
							//allow 1 August as due date even though it falls on a Saturday
						}else{
							messages.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("message.generalmessage",
												"Due date may not fall on a weekend, except for tests and external assessments."));
						}					
					}				
					//Validate that due date do not fall on a public holiday				
					CourseDAO courseDAO = new CourseDAO();
					boolean dueDateOnPublicHoliday = courseDAO.isDueDateOnPublicHoliday(assessmentCritForm.getAssignment().getDueDate(),Short.parseShort(assessmentCritForm.getAcademicYear()));
					if (dueDateOnPublicHoliday==true){
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage",
											"Due date may not fall on a public holiday, except for tests and external assessments."));
					}
				}
				
			}
			
			if (assessmentCritForm.getAssignment().getFormat()!=null && !assessmentCritForm.getAssignment().getFormat().equalsIgnoreCase("XA")){
				//project number of assignments for this due date, if number exceeds 250000 ask user to choose another due date
				int projectedAssDueDay = 0;
				int projectedAssPrevDay =0;
				int projectedAssModule=0;
				CourseDAO courseDAO = new CourseDAO();
				projectedAssDueDay = courseDAO.getProjectedNumberOfAss(assessmentCritForm.getAssignment().getDueDate(),assessmentCritForm.getStudyUnit().getCode(),Short.parseShort(assessmentCritForm.getAcademicYear()),assessmentCritForm.getDummyAcademicYear(), Short.parseShort(assessmentCritForm.getSemester()));
				//project number of assignments due for the previous day
				Date nextDay = formatter.parse(assessmentCritForm.getAssignment().getDueDate());
				calendar = Calendar.getInstance();
				calendar.setTime(nextDay);
				calendar.add(calendar.DATE, -1);
				String strPrevDay = null;
				strPrevDay = formatter.format(calendar.getTime());
				projectedAssPrevDay = courseDAO.getProjectedNumberOfAss(strPrevDay,assessmentCritForm.getStudyUnit().getCode(),Short.parseShort(assessmentCritForm.getAcademicYear()),assessmentCritForm.getDummyAcademicYear(), Short.parseShort(assessmentCritForm.getSemester()));
				projectedAssModule = courseDAO.getNrStudentsRegistered(assessmentCritForm.getStudyUnit().getCode(), Short.parseShort(assessmentCritForm.getAcademicYear()), Short.parseShort(assessmentCritForm.getSemester()));
				projectedAssPrevDay = (int)((float)projectedAssPrevDay + ((float)projectedAssModule * ((float)1 / (float)3)));
				projectedAssDueDay = (int)((float)projectedAssDueDay + ((float)projectedAssModule * ((float)2 / (float)3)));
				
				//Projection must include this modules volumes
				
				if (projectedAssDueDay > 250000 || projectedAssPrevDay > 250000){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"Total number of assessment due exceeds 250 000 for the chosen due date.  Please choose another due date, at least two days away from the current due date."));
				}
			}			
			
			//Validate assignment type is required
			if (assessmentCritForm.getAssignment().getType()==null || assessmentCritForm.getAssignment().getType().equalsIgnoreCase("")){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"Assessment Type is required"));
			}else{

				//Validate Assessment Formats/Types for Summative assessment Group
				if (assessmentCritForm.getAssignment().getGroup().equalsIgnoreCase("F")){
					if ((assessmentCritForm.getAssignment().getFormat().equalsIgnoreCase("A") && (assessmentCritForm.getAssignment().getType().equalsIgnoreCase("I") ||
																								  assessmentCritForm.getAssignment().getType().equalsIgnoreCase("T") ||
							                                                                      assessmentCritForm.getAssignment().getType().equalsIgnoreCase("G"))) ||
				    (assessmentCritForm.getAssignment().getFormat().equalsIgnoreCase("DF") && assessmentCritForm.getAssignment().getType().equalsIgnoreCase("I")) ||
					(assessmentCritForm.getAssignment().getFormat().equalsIgnoreCase("BL") && assessmentCritForm.getAssignment().getType().equalsIgnoreCase("I")) ||
					(assessmentCritForm.getAssignment().getFormat().equalsIgnoreCase("SA") && assessmentCritForm.getAssignment().getType().equalsIgnoreCase("I")) ||
					(assessmentCritForm.getAssignment().getFormat().equalsIgnoreCase("XA") && assessmentCritForm.getAssignment().getType().equalsIgnoreCase("I")) ||					
					(assessmentCritForm.getAssignment().getFormat().equalsIgnoreCase("H") && (assessmentCritForm.getAssignment().getType().equalsIgnoreCase("G") ||
							                                                                 assessmentCritForm.getAssignment().getType().equalsIgnoreCase("I") ||
							                                                                 assessmentCritForm.getAssignment().getType().equalsIgnoreCase("T"))) ||
					(assessmentCritForm.getAssignment().getFormat().equalsIgnoreCase("MS") && (assessmentCritForm.getAssignment().getType().equalsIgnoreCase("G") ||
									                                                                 assessmentCritForm.getAssignment().getType().equalsIgnoreCase("I") ||									                                                              
									                                                                 assessmentCritForm.getAssignment().getType().equalsIgnoreCase("R") ||
									                                                                 assessmentCritForm.getAssignment().getType().equalsIgnoreCase("S")))){
						//Do nothing : Valid Assessment Format and Type combination  for Formative Assessment Group					
					}else{                                                                 
						//Assessment Format and Type combination not valid for Formative Assessment Group	
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage",
											"The Assessment Format and Type combination that you selected is not allowed for formative assessment.<br/>" +
											"The following combinations are allowed for formative assessment:<br/>" +
											"<table>" +
											"<tr><td><b>Format</b></td><td>&nbsp;</td><td><b>Type</b></td></tr>" +
											"<tr><td>MCQ</td><td>-</td><td>Individual, Group, Test</td></tr>" +
											"<tr><td>Written</td><td>-</td><td>Individual, Group, Test</td></tr>" +
											"<tr><td>Discussion Forum</td><td>-</td><td>Individual</td></tr>" +
											"<tr><td>Blog</td><td>-</td><td>Individual</td></tr>" +
											"<tr><td>Samigo</td><td>-</td><td>Individual</td></tr>" +
											"<tr><td>External Assessment</td><td>-</td><td>Individual</td></tr>" +
											"<tr><td>myUnisa File Submission</td><td>-</td><td>Individual, Group, Peer Assessment Report, Peer Assessment</td></tr>" +
											"</table>"));
					}
				}
				//Validate Assessment Formats/Types for Summative assessment Group
				if (assessmentCritForm.getAssignment().getGroup().equalsIgnoreCase("S")){
					if ((assessmentCritForm.getAssignment().getFormat().equalsIgnoreCase("SA") && assessmentCritForm.getAssignment().getType().equalsIgnoreCase("O")) ||
					(assessmentCritForm.getAssignment().getFormat().equalsIgnoreCase("XA") && assessmentCritForm.getAssignment().getType().equalsIgnoreCase("O")) ||					
					(assessmentCritForm.getAssignment().getFormat().equalsIgnoreCase("H") && (assessmentCritForm.getAssignment().getType().equalsIgnoreCase("PF") ||
							                                                                 assessmentCritForm.getAssignment().getType().equalsIgnoreCase("PJ") ||
							                                                                 assessmentCritForm.getAssignment().getType().equalsIgnoreCase("PC"))) ||
					(assessmentCritForm.getAssignment().getFormat().equalsIgnoreCase("MS") && (assessmentCritForm.getAssignment().getType().equalsIgnoreCase("PF") ||
									                                                                 assessmentCritForm.getAssignment().getType().equalsIgnoreCase("PJ") ||
									                                                                 assessmentCritForm.getAssignment().getType().equalsIgnoreCase("PC") ||
									                                                                 assessmentCritForm.getAssignment().getType().equalsIgnoreCase("R") ||
									                                                                 assessmentCritForm.getAssignment().getType().equalsIgnoreCase("S") ||
									                                                                 assessmentCritForm.getAssignment().getType().equalsIgnoreCase("O")))){
						//Do nothing : Valid Assessment Format and Type combination  for Summative Assessment Group					
					}else{                                                                 
						//Assessment Format and Type combination not valid for Summative Assessment Group	
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage",
											"The Assessment Format and Type combination that you selected is not allowed for summative assessment.<br/>" +
											"The following combinations are allowed for summative assessment:<br/>" +
											"<table>" +
											"<tr><td><b>Format</b></td><td>&nbsp;</td><td><b>Type</b></td></tr>" +									
											"<tr><td>Written</td><td>-</td><td>PortFolio, Practical, Project</td></tr>" +
											"<tr><td>Samigo</td><td>-</td><td>Online Examination</td></tr>" +
											"<tr><td>External Assessment</td><td>-</td><td>Online Examination</td></tr>" +
											"<tr><td>myUnisa File Submission</td><td>-</td><td>Portfolio, Practical, Project, Peer Assessment Report, Peer Assessment, Online Examination</td></tr>" +
											"</table>"));
					}
				}
					
			}
			//Change BRD 2016
			if (assessmentCritForm.getAssignment().getGroup().equalsIgnoreCase("S") && 
					(assessmentCritForm.getSummativeAssessInd().equalsIgnoreCase("MIXED") && 
							assessmentCritForm.getExamBase().equalsIgnoreCase("NONVENUE"))) {
				if (assessmentCritForm.getAssignment().getFormat().equalsIgnoreCase("H") &&
						(assessmentCritForm.getAssignment().getType().equalsIgnoreCase("PF") ||
								assessmentCritForm.getAssignment().getType().equalsIgnoreCase("PC") ||
								assessmentCritForm.getAssignment().getType().equalsIgnoreCase("PJ"))) {
					//Ok do nothing
				} else {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"The Assessment Format and Type combination that you selected is not allowed.  If the Summative Assessment flag on AIMS is set to MIXED and " +
										"the examination is set to non-venue based, only the following Summative Assessment Format and Type combination is valid:<br/>" +
										"Written - Portfolio, Practical, Project "));
				}
			}
			
			if (assessmentCritForm.getStudyUnit().getDepartment().equalsIgnoreCase("5") &&
					assessmentCritForm.getAssignment().getFormat().equalsIgnoreCase("MS")){
				
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"SBL assessment, myUnisa file submission is not allowed."));
			}
			
			//Validate assignment contribution is required
			if ("".equalsIgnoreCase(assessmentCritForm.getYearMarkContributionYesNoFlag())||
					assessmentCritForm.getYearMarkContributionYesNoFlag()==null){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"Please indicate if assessment contributes to year mark/non-venue Examination component of student's final mark or not."));
			}

			if ("S".equalsIgnoreCase(assessmentCritForm.getAssignment().getGroup())) {
					if ("N".equalsIgnoreCase(assessmentCritForm.getYearMarkContributionYesNoFlag())){
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage",
											"A summative assessment must always contribute to the non venue examination component."));
					} else if ("E".equalsIgnoreCase(assessmentCritForm.getAssignment().getOptionality())){
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage",
											"A summative assessment may not be part of a group where best awarded marks apply."));
					}
			}
								
			if (!messages.isEmpty()) {
				addErrors(request,messages);
				//Get month list
				assessmentCritForm.setCurrentPage("assignment");
				return "displayAssignment";				
			}			

			if (assessmentCritForm.getYearMarkContributionYesNoFlag().equalsIgnoreCase("Y")){
				//Set flag to determine if a weight was set for the assignment
				String weightSet = "N";
				if (assessmentCritForm.getAssignment().getNormalWeight()==null || assessmentCritForm.getAssignment().getNormalWeight().equalsIgnoreCase("")){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"Assessment Normal Weight is required"));
				} else {
					if (!isInteger(assessmentCritForm.getAssignment().getNormalWeight())){
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage",
											"Assessment Normal Weight must be numeric"));
					} else {
						if (Integer.parseInt(assessmentCritForm.getAssignment().getNormalWeight()) < 0 ||
								Integer.parseInt(assessmentCritForm.getAssignment().getNormalWeight()) > 100)  {
							messages.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("message.generalmessage",
												"Assessment Normal Weight must be a value from 0 to 100"));	
						}else{
							if (Integer.parseInt(assessmentCritForm.getAssignment().getNormalWeight())!=0){
								weightSet="Y";
							}
						}
					}
				}	
				if (assessmentCritForm.getAssignment().getRepeatWeight()==null || assessmentCritForm.getAssignment().getRepeatWeight().equalsIgnoreCase("")){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"Assessment Repeat Weight is required"));
				} else {
					if (!isInteger(assessmentCritForm.getAssignment().getRepeatWeight())){
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage",
											"Assessment Repeat Weight must be numeric"));
					} else {
						if (Integer.parseInt(assessmentCritForm.getAssignment().getRepeatWeight()) < 0 ||
								Integer.parseInt(assessmentCritForm.getAssignment().getRepeatWeight()) > 100)  {
							messages.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("message.generalmessage",
												"Assessment Repeat Weight must be a value from 0 to 100"));	
						}else{
							if (Integer.parseInt(assessmentCritForm.getAssignment().getRepeatWeight())!=0){
								weightSet="Y";
							}
						}
					}
				}	
				if (assessmentCritForm.getAssignment().getAegrotatWeight()==null || assessmentCritForm.getAssignment().getAegrotatWeight().equalsIgnoreCase("")){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"Assessment Aegrotat Weight is required"));
				} else {
					if (!isInteger(assessmentCritForm.getAssignment().getAegrotatWeight())){
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage",
											"Assessment Aegrotat Weight must be numeric"));
					}else {
						if (Integer.parseInt(assessmentCritForm.getAssignment().getAegrotatWeight()) < 0 ||
								Integer.parseInt(assessmentCritForm.getAssignment().getAegrotatWeight()) > 100)  {
							messages.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("message.generalmessage",
												"Assessment Aegrotat Weight must be a value from 0 to 100"));	
						}else{
							if (Integer.parseInt(assessmentCritForm.getAssignment().getAegrotatWeight())!=0){
								weightSet="Y";
							}
						}
					}
				}	
				if (!weightSet.equalsIgnoreCase("Y")){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"At least one of either the normal, repeat or aegrotat weight must be set"));
				}
				if (assessmentCritForm.getAssignment().getOptionality()==null || assessmentCritForm.getAssignment().getOptionality().equalsIgnoreCase("")){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"Optionality is required"));
				}							
			}	
			
			if (!messages.isEmpty()) {
				addErrors(request,messages);
				assessmentCritForm.setCurrentPage("assignment");
				return "displayAssignment";
			}			
		}
		
		//Change BRD 2017 -20170623
		//initialise values depending if formative or summative assessent
		//release flag is only applicable for a research post-graduate module if it is a summative assessment 
		//set research flag = 'N', release_date=" " if summative asssesssment for which the module is a not a research post-graduate module
		//set research flag = 'Y', release_date=due if formative assessment	
		if (assessmentCritForm.getAssignment().getReleaseFlag()== null || assessmentCritForm.getAssignment().getReleaseFlag().trim().equalsIgnoreCase("")){
			assessmentCritForm.getAssignment().setReleaseFlag("N");
		}
		if (assessmentCritForm.getAssignment().getFileReleaseDate()==null || assessmentCritForm.getAssignment().getFileReleaseDate().trim().equalsIgnoreCase("")){
			assessmentCritForm.getAssignment().setFileReleaseDate("");
		}
		if (assessmentCritForm.getAssignment().getGroup().equalsIgnoreCase("F")) {
			//Formative Assessment
			//Results always released
			//May indicate a file date for release if not file release immediately after marking
			assessmentCritForm.getAssignment().setReleaseFlag("Y");
		} else if (assessmentCritForm.getAssignment().getGroup().equalsIgnoreCase("S")){
			//Summative Assessment
			//Marked file not release if date not set
			if (assessmentCritForm.getStudyUnit().getPostGraduateFlag().equalsIgnoreCase("Y") && 
					assessmentCritForm.getStudyUnit().getResearchFlag().equalsIgnoreCase("P")){
				//Do nothing leave values as is - are allowed to release results and file
			}else{
				//Results and File may never be released.
				assessmentCritForm.getAssignment().setReleaseFlag("N");
				assessmentCritForm.getAssignment().setFileReleaseDate("");
			}
		}
		//Debug Johanet 2017
		assessmentCritForm.setSubmissionDateIndicator("na");
		if ((assessmentCritForm.getAssignment().getFormat().equalsIgnoreCase("MS") &&    //myUnisa Submission only
				(assessmentCritForm.getAssignment().getType().equalsIgnoreCase("PF") || //Portfolio
				assessmentCritForm.getAssignment().getType().equalsIgnoreCase("PJ") ||  //Project 
				assessmentCritForm.getAssignment().getType().equalsIgnoreCase("PC") ||  //Practical
				assessmentCritForm.getAssignment().getType().equalsIgnoreCase("I") ||   //Individual
				assessmentCritForm.getAssignment().getType().equalsIgnoreCase("G") ||   //Group
				assessmentCritForm.getAssignment().getType().equalsIgnoreCase("S") ||   //Peer assignment 
				assessmentCritForm.getAssignment().getType().equalsIgnoreCase("R") ||   //Peer assignment Report
				assessmentCritForm.getAssignment().getType().equalsIgnoreCase("O"))) ||
				(assessmentCritForm.getAssignment().getFormat().equalsIgnoreCase("H") &&
				(assessmentCritForm.getAssignment().getType().equalsIgnoreCase("PF") || //Portfolio
				assessmentCritForm.getAssignment().getType().equalsIgnoreCase("PJ") ||  //Project 
				assessmentCritForm.getAssignment().getType().equalsIgnoreCase("PC") ||  //Practical
				assessmentCritForm.getAssignment().getType().equalsIgnoreCase("I") ||   //Individual
				assessmentCritForm.getAssignment().getType().equalsIgnoreCase("G")))) {    //Group
			assessmentCritForm.setSubmissionDateIndicator("date");
			if (assessmentCritForm.getAssignment().getGroup().equalsIgnoreCase("S") && 
					assessmentCritForm.getAssignment().getFormat().equalsIgnoreCase("MS") &&
					assessmentCritForm.getAssignment().getType().equalsIgnoreCase("O")) {
				assessmentCritForm.setSubmissionDateIndicator("dateTime");
			}
		}
		//submission open date
		if (assessmentCritForm.getSubmissionDateIndicator().equalsIgnoreCase("na")){
			//Initialise Opening/Final submission date if not applicable for assignment type
			assessmentCritForm.getAssignment().setPfOpenDate("");
			assessmentCritForm.getAssignment().setFinalSubmissionDate("");
		} else {
			if (assessmentCritForm.getAssignment().getPfOpenDate()== null || 
					assessmentCritForm.getAssignment().getPfOpenDate().trim().equalsIgnoreCase("") ||
					assessmentCritForm.getAssignment().getPfOpenDate().substring(0, 8).equalsIgnoreCase("00010101")){						
				assessmentCritForm.getAssignment().setPfOpenDate("");
			} else {
				if (assessmentCritForm.getSubmissionDateIndicator().equalsIgnoreCase("date") && assessmentCritForm.getAssignment().getPfOpenDate().length() >= 8){
					assessmentCritForm.getAssignment().setPfOpenDate(assessmentCritForm.getAssignment().getPfOpenDate().substring(0, 8));
				} else if (assessmentCritForm.getSubmissionDateIndicator().equalsIgnoreCase("dateTime") && assessmentCritForm.getAssignment().getPfOpenDate().length() >= 14 ){
					assessmentCritForm.getAssignment().setPfOpenDate(assessmentCritForm.getAssignment().getPfOpenDate().substring(0, 14));
				}
			}
			//final submission date
			if (assessmentCritForm.getAssignment().getFinalSubmissionDate()== null || 
					assessmentCritForm.getAssignment().getFinalSubmissionDate().trim().equalsIgnoreCase("") ||
					assessmentCritForm.getAssignment().getFinalSubmissionDate().substring(0, 8).equalsIgnoreCase("00010101")){						
				assessmentCritForm.getAssignment().setFinalSubmissionDate("");
			} 
			else {
				if (assessmentCritForm.getSubmissionDateIndicator().equalsIgnoreCase("date") && assessmentCritForm.getAssignment().getFinalSubmissionDate().length() > 8){
					assessmentCritForm.getAssignment().setFinalSubmissionDate(assessmentCritForm.getAssignment().getFinalSubmissionDate().substring(0, 8));
				} else if (assessmentCritForm.getSubmissionDateIndicator().equalsIgnoreCase("dateTime") && assessmentCritForm.getAssignment().getFinalSubmissionDate().length() >= 14) {
					assessmentCritForm.getAssignment().setFinalSubmissionDate(assessmentCritForm.getAssignment().getFinalSubmissionDate().substring(0, 14));
				}
			}
		}
		
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		Date dueDate = formatter.parse(assessmentCritForm.getAssignment().getDueDate());		
		calendar.setTime(dueDate);	
				
		if (assessmentCritForm.getAssignment().getFormat().equalsIgnoreCase("A")){
			
			//Determine prelim & final date marking			
			//Set Preliminary marking date - 1 weeks after due date
			calendar.add(Calendar.DATE, 7);
			Date tempDate = new Date();
			formatter.applyPattern("d MMMMM yyyy");
			tempDate = calendar.getTime();
			assessmentCritForm.getAssignment().setPrelimMarkingDate(formatter.format(tempDate));
				
			//Set Final marking date - 2 weeks after due date
			calendar.setTime(dueDate);
			calendar.add(Calendar.DATE,14);
			tempDate = calendar.getTime();
			assessmentCritForm.getAssignment().setFinalMarkingDate(formatter.format(tempDate));
			
			//Determine if negative marking or not
			if (Integer.parseInt(assessmentCritForm.getAssignment().getNegativeMarkingFactor())> 0){
				assessmentCritForm.setNegativeMarkingYesNoFlag("Y");
			}else{
				assessmentCritForm.setNegativeMarkingYesNoFlag("N");
			}
			assessmentCritForm.getAssignment().setOnscreenMarkFlag("N");	
			assessmentCritForm.setCurrentPage("mcqMarking");
			return "displayMCQMarkingDetails";
		
		  //Johanet added 20140701 - test included test - no marking details	
		} else if ((assessmentCritForm.getAssignment().getFormat().equalsIgnoreCase("H") &&
				!assessmentCritForm.getAssignment().getType().equalsIgnoreCase("T")) ||
				(assessmentCritForm.getAssignment().getFormat().equalsIgnoreCase("MS") &&
						!assessmentCritForm.getAssignment().getType().equalsIgnoreCase("S") &&
						!assessmentCritForm.getAssignment().getType().equalsIgnoreCase("R"))){	
			
			if (assessmentCritForm.getAssignmentAction().equalsIgnoreCase("view")){
				if (!assessmentCritForm.getAssignment().getOnscreenMarkFlag().equalsIgnoreCase("Y")){
					assessmentCritForm.setCurrentPage("writtenMarking");
					return "displayWrittenMarkingDetails";
				}
			}
			if (assessmentCritForm.getAssignmentAction().equalsIgnoreCase("add")) {
				if (assessmentCritForm.getAssignment().getFileReleaseDate().trim().equalsIgnoreCase("")){
					if (assessmentCritForm.getAssignment().getGroup().equalsIgnoreCase("F")){
						assessmentCritForm.getAssignment().setFileReleaseDate(assessmentCritForm.getAssignment().getDueDate());						
					}else {
						assessmentCritForm.getAssignment().setFileReleaseDate("");
					}
					
				}
			}
				
			assessmentCritForm.getAssignment().setOnscreenMarkFlag("Y");	
			assessmentCritForm.setResetOnscreenMarkingArrays(true);
			assessmentCritForm.setCurrentPage("onscreenMarking");
			return "displayOnscreenMarking";			
		
		}else {
			assessmentCritForm.getAssignment().setOnscreenMarkFlag("N");	
			assessmentCritForm.setCurrentPage("noMarking");
			return "displayNoMarkingDetails";
		}
	}
	
	public String displayMCQMemorandum(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		ActionMessages messages = new ActionMessages();
		AssessmentCriteriaForm assessmentCritForm = (AssessmentCriteriaForm) form;
		
		//remove leading and trailing spaces
		assessmentCritForm.getAssignment().setNumberQuestions(assessmentCritForm.getAssignment().getNumberQuestions().trim());
		//Only do validation on add & update
		if (!assessmentCritForm.getAssignmentAction().equalsIgnoreCase("view")){
			//do validation - first page MCQ marking details
			//Number of questions
			if (assessmentCritForm.getAssignment().getNumberQuestions()==null || assessmentCritForm.getAssignment().getNumberQuestions().equalsIgnoreCase("")){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"Number of questions is required"));
			} else {
				if (!isInteger(assessmentCritForm.getAssignment().getNumberQuestions())){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"Number of questions must be numeric"));
				} else {
					if (Integer.parseInt(assessmentCritForm.getAssignment().getNumberQuestions()) < 1 ||
							Integer.parseInt(assessmentCritForm.getAssignment().getNumberQuestions()) > 140)  {
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage",
											"Number of questions must be a value from 1 to 140"));	
					}
				}
			}
			//Negative marking
			if ("Y".equalsIgnoreCase(assessmentCritForm.getNegativeMarkingYesNoFlag())){
				if (assessmentCritForm.getAssignment().getNegativeMarkingFactor()==null || assessmentCritForm.getAssignment().getNegativeMarkingFactor().equalsIgnoreCase("")){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"If negative marking, number of wrong answer(s) for which a mark must be deducted is required."));
				} else {
					if (!isInteger(assessmentCritForm.getAssignment().getNegativeMarkingFactor())){
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage",
											"Number of wrong answer(s) for which a mark must be deducted must be numeric"));
					} else {
						if (Integer.parseInt(assessmentCritForm.getAssignment().getNegativeMarkingFactor()) < 1 ||
								Integer.parseInt(assessmentCritForm.getAssignment().getNegativeMarkingFactor()) > 8)  {
							messages.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("message.generalmessage",
												"Number of wrong answer(s) for which a mark must be deducted must be a value from 1 to 8"));	
						}
					}
				}
			}
			
			if (!messages.isEmpty()) {
				addErrors(request,messages);
				assessmentCritForm.setCurrentPage("mcqMarking");
				return "displayMCQMarkingDetails";
			}
		}
				
		List list = new ArrayList();
		List listDisplayAnswer = new ArrayList();
			
		for(int i=0; i < Integer.parseInt(assessmentCritForm.getAssignment().getNumberQuestions()); i++) {
			Memo memo = new Memo();
			String answer="";
			memo.setQuestion(String.valueOf(i + 1));
			if (!assessmentCritForm.getAssignmentAction().equalsIgnoreCase("add")){
				try {
					memo.setAnswer(((Memo)(assessmentCritForm.getAssignment().getListAnswer().get(i))).getAnswer());
					answer=((Memo)(assessmentCritForm.getAssignment().getListAnswer().get(i))).getAnswer();
				} catch (Exception ex){
					memo.setAnswer("");
					answer="";
				}				
			} else {
				if (assessmentCritForm.isSurvey()){
					memo.setAnswer("0");
					answer="0";
				}else{
					memo.setAnswer("");
					answer="";
				}				
			}
			list.add(i, memo);
			listDisplayAnswer.add(i,answer);
		}
						
		assessmentCritForm.getAssignment().setListAnswer(list);		
		assessmentCritForm.setListDisplayAnswer(listDisplayAnswer);
		
		assessmentCritForm.setCurrentPage("mcqMemorandum");
		return "displayMCQMemorandum";
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
	
	public boolean isAlphanumeric(String str) {
        for (int i=0; i<str.length(); i++) {
            char c = str.charAt(i);
            if (!Character.isDigit(c) && !Character.isLetter(c))
                return false;
        }

        return true;
    }
	
	public boolean isAlphabetic(String str) {
        for (int i=0; i<str.length(); i++) {
            char c = str.charAt(i);
            if (!Character.isLetter(c))
                return false;
        }

        return true;
    }
	
	public boolean isValidDate(String dateString, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		formatter.setLenient(false);
		try
		{
			Date date = formatter.parse(dateString);			
		}	
		catch(ParseException e)
		{return false;}
 		return true;
	}
	
	public FinalMarkComposition validateFinalMarkComp(FinalMarkComposition finalMarkComp, StudyUnit studyUnit) {
		
		if (finalMarkComp.getExamComponent()==null || finalMarkComp.getExamComponent().equals("")){
			finalMarkComp.setExamComponent("0");
		}
		if (finalMarkComp.getYearMarkComponent()==null || finalMarkComp.getYearMarkComponent().equals("")){
			finalMarkComp.setYearMarkComponent("0");
		}
		if (finalMarkComp.getPortfolioComponent()==null || finalMarkComp.getPortfolioComponent().equals("")){
			finalMarkComp.setPortfolioComponent("0");
		}
		if (finalMarkComp.getExamSubminimum()==null || finalMarkComp.getExamSubminimum().equals("")){
			finalMarkComp.setExamSubminimum("40");
		}
		if (finalMarkComp.getYearMarkSubminimum()==null || finalMarkComp.getYearMarkSubminimum().equals("")){
			finalMarkComp.setYearMarkSubminimum("0");
		}
		if (finalMarkComp.getPortfolioSubminimum()==null || finalMarkComp.getPortfolioSubminimum().equals("")){
			finalMarkComp.setPortfolioSubminimum("40");
		}	
		//Change 2015 BR
		//Only allow admission criteria if auto admission No and academic levels H, M, D or N - otherwise set to default values
		if (finalMarkComp.getExamAdmissionMethod()==null || finalMarkComp.getExamAdmissionMethod().trim().equalsIgnoreCase("")){
			finalMarkComp.setExamAdmissionMethod("A1");
			finalMarkComp.setExamAdmissionNrAss("0");
			finalMarkComp.setExamAdmissionYearMarkSubMin("0");
		}
		
		return finalMarkComp;
	}	
	
	public void sendNotifyEmail(String toAddress, String addressee, String course) {

		try {

			String tmpEmailFrom = ServerConfigurationService.getString("noReplyEmailFrom");
			//String serverPath = ServerConfigurationService.getToolUrl();
			InternetAddress toEmail = new InternetAddress(toAddress);
			InternetAddress iaTo[] = new InternetAddress[1];
			iaTo[0] = toEmail;
			InternetAddress iaHeaderTo[] = new InternetAddress[1];
			iaHeaderTo[0] = toEmail;
			InternetAddress iaReplyTo[] = new InternetAddress[1];
			iaReplyTo[0] = new InternetAddress(tmpEmailFrom);

			/* Setup path to myUnisa */
			String myUnisaPath = ServerConfigurationService.getServerUrl();

			/* send activation email to student */
			String subject = "Assessment plan - authorisation request";
			String body = "<html> "+
		    "<body> "+
				"Dear " + addressee + ",  <br/><br/>"+
			"NB: This is an automated response - do not reply to this e-mail.  <br/><br/> "+
			"You are receiving this e-mail in response to a request to authorise the assessment plan for " + course + ".<br/>"+
			"To authorise the assessment plan log onto myUnisa. <br/>" +
			"Select the Course Admin site from the orange navigation bar at the top. <br/>"+
			"Select Authorise Assessment Plan in the left navigation column to access the authorisation tool. <A href='"+myUnisaPath+"' target='_blank'><b>myUnisa</b></A>"+
			//"<A href='"+serverPath+"' target='_blank'><b>myUnisa</b></A>"+
			"</body>"+
			"</html>";

		  List contentList = new ArrayList();
		  contentList.add("Content-Type: text/html");

		  emailService = (EmailService)ComponentManager.get(EmailService.class); 	
		  
		  emailService.sendMail(iaReplyTo[0],iaTo,subject,body,iaHeaderTo,iaReplyTo,contentList);
		  log.info("sending mail from "+iaReplyTo[0]+" to "+toEmail.toString());
		 } catch (Exception e) {
		 	log.error("AssessmentCriteriaAction: send of email authorisation request notification failed "+e+" "+e.getMessage());
			e.printStackTrace();
		 }
	}
	
	private void resetFormFields(AssessmentCriteriaForm assessmentCriteriaForm)
	throws Exception {		
		assessmentCriteriaForm.setAcademicYear(null);
		assessmentCriteriaForm.setAssignment(null);
		assessmentCriteriaForm.setAssignmentAction(null);
		assessmentCriteriaForm.setCurrentPage(null);
		assessmentCriteriaForm.setDepartment(null);
		assessmentCriteriaForm.setDummyAcademicYear(null);
		assessmentCriteriaForm.setDummyFirstExamination(null);
		assessmentCriteriaForm.setFinalMarkComp(null);
		assessmentCriteriaForm.setFirstExamination(null);
		assessmentCriteriaForm.setIndexSelectedRemoveAssignment(null);
		assessmentCriteriaForm.setIndexNrSelectedFormat(new String[0]);
		assessmentCriteriaForm.setIndexNrSelectedLanguage(new String[0]);
		assessmentCriteriaForm.setListAssignment(null);
		assessmentCriteriaForm.setListAssignmentFormat(null);
		assessmentCriteriaForm.setListAssignmentOptionality(null);
		assessmentCriteriaForm.setListAssignmentType(null);
		assessmentCriteriaForm.setListNegativeMarking(null);
		assessmentCriteriaForm.setListPossibleMCQAnswers(null);
		assessmentCriteriaForm.setListSemester(null);
		assessmentCriteriaForm.setListOtherFileFormat(null);
		assessmentCriteriaForm.setListOtherLanguage(null);
		assessmentCriteriaForm.setMonthSelected(null);
		assessmentCriteriaForm.setNovellUserId(null);
		assessmentCriteriaForm.setResponseEmailAddress(null);
		assessmentCriteriaForm.setSelectAssignment(null);
		assessmentCriteriaForm.setSelectedAuthoriser(null);
		assessmentCriteriaForm.setSemester(null);
		assessmentCriteriaForm.setStatus(null);
		assessmentCriteriaForm.setStatusDesc(null);
		StudyUnit studyUnit = new StudyUnit();
		assessmentCriteriaForm.setStudyUnit(studyUnit);
		//assessmentCriteriaForm.setStudyUnit(null);
		assessmentCriteriaForm.setYearSelected(null);
		assessmentCriteriaForm.setNegativeMarkingYesNoFlag(null);
		assessmentCriteriaForm.setYearMarkContributionYesNoFlag(null);
		assessmentCriteriaForm.setSelfAssesmentYesNoFlag(null);
		assessmentCriteriaForm.setListDisplayAnswer(null);
		assessmentCriteriaForm.setFirstAssDueControlDate(null);
		assessmentCriteriaForm.setSecondAssDueControlDate(null);
		assessmentCriteriaForm.setSemesterType(null);
		assessmentCriteriaForm.setSunpdtExists(false);
		assessmentCriteriaForm.setResetOnscreenMarkingArrays(false);
    } 
	
	private void getAssessmentCriteria(AssessmentCriteriaForm assessmentCritForm)
	throws Exception {	
		
		Integer dummyFactor = 1000;
		Integer academicYear = 0;
		assessmentCritForm.setStatus("");
				
		FinalMarkComposition finalMarkComp = new FinalMarkComposition();
		AssessmentCriteriaDAO daoAssessCrit = new AssessmentCriteriaDAO();
		AssLog asslog = new AssLog();
		List listAssignment = new ArrayList();
		
		//determine assessment criteria status
		assessmentCritForm.setStatus("UPDATE");
		
		//get latest status from log
		asslog = daoAssessCrit.getLatestLogEntry(Short.parseShort(assessmentCritForm.getAcademicYear()),
				Short.parseShort(assessmentCritForm.getSemester()),
				assessmentCritForm.getStudyUnit().getCode());
		
		//No log entry
		//determine if final assessment criteria exist
		if (asslog.getAction()==null||"".equalsIgnoreCase(asslog.getAction())){
					listAssignment=daoAssessCrit.getAssignments(Short.parseShort(assessmentCritForm.getAcademicYear()),
					Short.parseShort(assessmentCritForm.getSemester()),
					assessmentCritForm.getStudyUnit().getCode());
			if (listAssignment.size()>0){
				assessmentCritForm.setStatus("AUTHORISED");
				assessmentCritForm.setListAssignment(listAssignment);
			}			
		}else{
			assessmentCritForm.setStatus(asslog.getAction());			
		}
		
		//Get status description
		assessmentCritForm.setStatusDesc(assessmentCritForm.getStatus());
		StudentSystemGeneralDAO daoGenCod = new StudentSystemGeneralDAO();
		for (int i=0; i < daoGenCod.getGenCodes((short)53,1).size(); i++) {
			if (((Gencod)(daoGenCod.getGenCodes((short)53,1).get(i))).getCode().equalsIgnoreCase(assessmentCritForm.getStatus())){
				assessmentCritForm.setStatusDesc(((Gencod)(daoGenCod.getGenCodes((short)53,1).get(i))).getEngDescription());
				i = daoGenCod.getGenCodes((short)53,1).size();
			}
		}
		
		//set academic year
		if (assessmentCritForm.getStatus().equalsIgnoreCase("AUTHORISED")){
			assessmentCritForm.setDummyAcademicYear(Integer.parseInt(assessmentCritForm.getAcademicYear()));
		}else{
			assessmentCritForm.setDummyAcademicYear(Integer.parseInt(assessmentCritForm.getAcademicYear()) + 1000);
		}
		
		//set first examination period
		if (assessmentCritForm.isSunpdtExists()){
			//assessmentCritForm.setFirstExamination(daoAssessCrit.getFirstExamination(Short.parseShort(assessmentCritForm.getAcademicYear()), Short.parseShort(assessmentCritForm.getSemester()), assessmentCritForm.getStudyUnit().getCode()));
			Sunpdt sunpdt = new Sunpdt();
			Gencod gencod = new Gencod();
			StudentSystemGeneralDAO dao = new StudentSystemGeneralDAO();
			sunpdt=daoAssessCrit.getSunpdt(Short.parseShort(assessmentCritForm.getAcademicYear()), Short.parseShort(assessmentCritForm.getSemester()), assessmentCritForm.getStudyUnit().getCode());
			assessmentCritForm.setFirstExamination(sunpdt.getFirstExam());
			assessmentCritForm.setFormativeAssessInd(sunpdt.getFormativeAssessInd().trim());
			assessmentCritForm.setSummativeAssessInd(sunpdt.getSummativeAssessInd().trim());
			//Change 20160629 - AP Brd 2017
			//Change 20170607 - New ExamBase CONT
			assessmentCritForm.setExamBase("VENUE");
			if (sunpdt.getNonVenueBaseExam()!=null && !sunpdt.getNonVenueBaseExam().trim().equalsIgnoreCase("")){
				if (sunpdt.getNonVenueBaseExam().equalsIgnoreCase("CONT ASS")){
					assessmentCritForm.setExamBase("CONT");
				}else{
					assessmentCritForm.setExamBase("NONVENUE");
				}	
			}

			if (assessmentCritForm.getFormativeAssessInd()==null || 
					assessmentCritForm.getFormativeAssessInd().equalsIgnoreCase("") ||
					assessmentCritForm.getSummativeAssessInd()==null ||
					assessmentCritForm.getSummativeAssessInd().equalsIgnoreCase("")	){
				assessmentCritForm.setOnlineMethod("Unavailable:  Information on AIMS is not complete.");
			}else{
				gencod = dao.getGenCode("197", assessmentCritForm.getFormativeAssessInd());
				if (gencod.getEngDescription()!=null && gencod.getEngDescription()!=""){
					assessmentCritForm.setOnlineMethod(gencod.getEngDescription());
				}else{
					assessmentCritForm.setOnlineMethod(sunpdt.getFormativeAssessInd());
				}
				gencod = dao.getGenCode("198", assessmentCritForm.getSummativeAssessInd());
				if (gencod.getEngDescription()!=null && gencod.getEngDescription()!=""){
					assessmentCritForm.setOnlineMethod(assessmentCritForm.getOnlineMethod() + ", " + gencod.getEngDescription());
				}else{
					assessmentCritForm.setOnlineMethod(assessmentCritForm.getOnlineMethod() + ", " + sunpdt.getSummativeAssessInd());
				}
				//Change 20160629 - AP Brd 2017
				gencod = dao.getGenCode("195", sunpdt.getNonVenueBaseExam());
				if (gencod.getEngDescription()!=null && gencod.getEngDescription()!=""){
					assessmentCritForm.setOnlineMethod(assessmentCritForm.getOnlineMethod() + ", Non-venue Based Exam (" + gencod.getEngDescription() + ")");
				} else {
					//assessmentCritForm.setOnlineMethod(assessmentCritForm.getOnlineMethod() + ", Venue Base Examination");
				}
			}			
			if (assessmentCritForm.getFirstExamination().getYear()!=null || assessmentCritForm.getFirstExamination().getYear()==0){
				Examination dummyFirstExamination = new Examination();
				if (assessmentCritForm.getStatus().equalsIgnoreCase("AUTHORISED")){
					dummyFirstExamination.setYear(assessmentCritForm.getFirstExamination().getYear());
				}else{
					dummyFirstExamination.setYear(Short.parseShort((String.valueOf(assessmentCritForm.getFirstExamination().getYear().intValue() + 1000))));
				}
				dummyFirstExamination.setPeriod(assessmentCritForm.getFirstExamination().getPeriod());
				assessmentCritForm.setDummyFirstExamination(dummyFirstExamination);
				
				//get final mark composition
				finalMarkComp = daoAssessCrit.getFinalMarkComposition(assessmentCritForm.getDummyFirstExamination().getYear(), assessmentCritForm.getFirstExamination().getPeriod(), assessmentCritForm.getStudyUnit().getCode());		
			}	
		}else{
			assessmentCritForm.setOnlineMethod("Unavailable:  Information on AIMS is not complete.");
		}				
				
		//get assignments
		if (listAssignment.size()==0){
			listAssignment = daoAssessCrit.getAssignments(Short.parseShort(String.valueOf(assessmentCritForm.getDummyAcademicYear())), Short.parseShort(assessmentCritForm.getSemester()), assessmentCritForm.getStudyUnit().getCode());
			assessmentCritForm.setListAssignment(listAssignment);
		}				
		
		boolean enableDelete=false;
		for (int i=0; i < assessmentCritForm.getListAssignment().size(); i++){
			//Test if there is an assignment that can be deleted - assignment not created on student system
			if (((Assignment)(assessmentCritForm.getListAssignment().get(i))).isCaptureOnStudentSystem()==false){
				enableDelete=true;
			}			
					
			Integer uniqueNr = Integer.parseInt(((Assignment)(assessmentCritForm.getListAssignment().get(i))).getUniqueNumber());
			if ("A".equalsIgnoreCase(((Assignment)assessmentCritForm.getListAssignment().get(i)).getFormat())){
				String answers = "";
				List list = new ArrayList();
				answers = daoAssessCrit.getAnswers(assessmentCritForm.getDummyAcademicYear(),Short.parseShort(assessmentCritForm.getSemester()),uniqueNr).trim();
				//split memo up into arraylist values
				for (int j=0; j < answers.length(); j++){
					Memo memo = new Memo();
					memo.setQuestion(String.valueOf(j + 1));
					memo.setAnswer(String.valueOf(answers.substring(j, j+1)));
					list.add(j,memo);
				}
				((Assignment)(assessmentCritForm.getListAssignment().get(i))).setListAnswer(list);
			}	
			//get markers for written assignment			
			if ("H".equalsIgnoreCase(((Assignment)assessmentCritForm.getListAssignment().get(i)).getFormat())||
					("MS".equalsIgnoreCase(((Assignment)assessmentCritForm.getListAssignment().get(i)).getFormat())&&
							!"S".equalsIgnoreCase(((Assignment)assessmentCritForm.getListAssignment().get(i)).getType()) &&
							!"R".equalsIgnoreCase(((Assignment)assessmentCritForm.getListAssignment().get(i)).getType()))){
				List listMarkers = new ArrayList();
				listMarkers = daoAssessCrit.getMarkers(assessmentCritForm.getDummyAcademicYear(),
					Integer.parseInt(assessmentCritForm.getAcademicYear()),
					Short.parseShort(assessmentCritForm.getSemester()),
					uniqueNr,
					assessmentCritForm.getStudyUnit().getCode());
				((Assignment)(assessmentCritForm.getListAssignment().get(i))).setListMarkers(listMarkers);
			}	
			//get file formats for assignment
			if("Y".equalsIgnoreCase(((Assignment)assessmentCritForm.getListAssignment().get(i)).getOnscreenMarkFlag())){
				List listFormat = new ArrayList<FileFormat>();				
				listFormat = daoAssessCrit.getFileFormats(uniqueNr);				
				((Assignment)(assessmentCritForm.getListAssignment().get(i))).setListFormat(listFormat);
							
				//get submission languages for assignment
				List listLanguage = new ArrayList<SubmissionLanguage>();				
				listLanguage = daoAssessCrit.getSubmissionLanguages(uniqueNr);
				((Assignment)(assessmentCritForm.getListAssignment().get(i))).setListLanguage(listLanguage);
			}	
		}
		//determine if data may be updated or just viewed
		if (assessmentCritForm.getStatus().equalsIgnoreCase("AUTHORISED")||
				assessmentCritForm.getStatus().equalsIgnoreCase("AUTHREQ")){
			assessmentCritForm.setAssignmentAction("view");
			enableDelete=false;
		}else{
			assessmentCritForm.setAssignmentAction("edit");
		}
		//Set field use to determine whether to display remove column & remove button
		assessmentCritForm.setEnableDelete(enableDelete);
		assessmentCritForm.setFinalMarkComp(validateFinalMarkComp(finalMarkComp,assessmentCritForm.getStudyUnit()));
		
		for (int i=0; i < assessmentCritForm.getListAssignment().size(); i++){
			Assignment assignment = new Assignment();
			assignment = (Assignment)assessmentCritForm.getListAssignment().get(i);
			if (assignment.getType()!= null && assignment.getType().equalsIgnoreCase("P")){
				if (assignment.getSubType()==null || assignment.getSubType().equalsIgnoreCase("") || assignment.getSubType().equals("PORTFOLIO")){
					((Assignment)assessmentCritForm.getListAssignment().get(i)).setType("PF");
				}else{
					if (assignment.getSubType().equals("PROJECT")){
						((Assignment)assessmentCritForm.getListAssignment().get(i)).setType("PJ");
					}
					if (assignment.getSubType().equals("PRACTICAL")){
						((Assignment)assessmentCritForm.getListAssignment().get(i)).setType("PC");
					}
				}				
			}			
		}
	} 
	
	private String replaceNull(Object object){
		String stringValue="";
		if (object==null){			
		}else{
			stringValue=object.toString();
		}			
		return stringValue;
	}
	
	private List validateAssignments(AssessmentCriteriaForm assessmentCritForm) throws Exception {
		List errorList = new ArrayList();
		
		for (int i=0; i < assessmentCritForm.getListAssignment().size(); i++){
			
			Assignment assignment = new Assignment();
			assignment = (Assignment)assessmentCritForm.getListAssignment().get(i);
			Integer assNumber = Integer.parseInt(assignment.getNumber());
			boolean survey = false;
			if (assNumber >= 91 && assNumber <=95){
				survey=true;
			}
			String errorFlag="N";
			
			while ("N".equalsIgnoreCase(errorFlag)){
				Calendar calendar = Calendar.getInstance();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
				Date dueDate = formatter.parse(assignment.getDueDate());		
				calendar.setTime(dueDate);
				//Validate due date against assignment due date control dates
				//Do not validate due date for tests
				if ("T".equalsIgnoreCase(assignment.getType())){
					//do nothing
				}else{
					//do not validate due dates against due date control dates for:
					//F - Formal Doctoral
					//F - Masters (coursework excluded)
					//F - Formal Tuition Experiental Learning Roll Over courses
					//R - RPL courses
					//P - Pre-Admission courses
					//T - Temporary
					//L - Tut Letters
					//due dates may not fall on a weekend still apply for above mention courses	
					
					if ((assessmentCritForm.getStudyUnit().getFormalTuitionFlag().equalsIgnoreCase("F")&&
							!assessmentCritForm.getStudyUnit().getResearchFlag().equalsIgnoreCase("N") &&
							(assessmentCritForm.getStudyUnit().getAcademicLevel().equalsIgnoreCase("H")||
							assessmentCritForm.getStudyUnit().getAcademicLevel().equalsIgnoreCase("M")||
							assessmentCritForm.getStudyUnit().getAcademicLevel().equalsIgnoreCase("D")))||
							(assessmentCritForm.getStudyUnit().getFormalTuitionFlag().equalsIgnoreCase("F")&&
									assessmentCritForm.getStudyUnit().getExperiental_duration()>1)||
							assessmentCritForm.getStudyUnit().getFormalTuitionFlag().equalsIgnoreCase("R")||
							assessmentCritForm.getStudyUnit().getFormalTuitionFlag().equalsIgnoreCase("P")||
							assessmentCritForm.getStudyUnit().getFormalTuitionFlag().equalsIgnoreCase("T")||
							assessmentCritForm.getStudyUnit().getFormalTuitionFlag().equalsIgnoreCase("L")||
							assessmentCritForm.getStudyUnit().getFormalTuitionFlag().equalsIgnoreCase("E")||
							survey){
						//do not test against control due dates
					} else {
						if (assignment.getGroup()==null || assignment.getGroup().equalsIgnoreCase("")){
							//Do nothing type not known, do not no against which date to test
//						} else if ("PF".equalsIgnoreCase(assignment.getType()) ||
//								"PJ".equalsIgnoreCase(assignment.getType()) ||
//								"PC".equalsIgnoreCase(assignment.getType())){
						} else if ("S".equalsIgnoreCase(assignment.getGroup())) {	
							//Test porfolio's, practical's and projects against last due date
							Date lastControlDate = formatter.parse(assessmentCritForm.getLastPortfolioDueControlDate());
							if (dueDate.after(lastControlDate)){
								errorFlag="Y";
								break;
							}	
						}else {
							Date lastControlDate = formatter.parse(assessmentCritForm.getLastAssDueControlDate());
							if (dueDate.after(lastControlDate)){
								errorFlag="Y";
								break;
							}
						}
					}
				
					//Validate that due date do not fall on a weekend
					if (assignment.getFormat()!=null && !assignment.getFormat().equalsIgnoreCase("XA")){
						if (calendar.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY || calendar.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY){
							String stringDueDate="";
							stringDueDate=formatter.format(dueDate);
							if (stringDueDate.equalsIgnoreCase("20090801") && assessmentCritForm.getSemester().equalsIgnoreCase("0")){
								//allow 1 August as duedate even though it falls on a Saturday
							}else{
								errorFlag="Y";
								break;
							}							
						}
					}
					
				}
				//Validate assignment type
				if (assignment.getType()==null || assignment.getType().equalsIgnoreCase("")){
					errorFlag="Y";
					break;
				}
				//Assessment plan changes for 2016
				//Johanet added 20130702 - can only perform test if sunpdt exists
//				if ((assignment.getFormat().equalsIgnoreCase("DF") ||
//						assignment.getFormat().equalsIgnoreCase("BL") ||
//						assignment.getFormat().equalsIgnoreCase("SA") ||
//						assignment.getFormat().equalsIgnoreCase("XA") ||
//						assignment.getFormat().equalsIgnoreCase("MS"))){
//					if (!assessmentCritForm.getFormativeAssessInd().equalsIgnoreCase("ONLINE"))
//						errorFlag="Y";
//						break;
//				}
				
				if (assignment.getGroup().equalsIgnoreCase("S") && 
						(assessmentCritForm.getSummativeAssessInd().equalsIgnoreCase("MIXED") && 
						assessmentCritForm.getExamBase().equalsIgnoreCase("NONVENUE"))) {
					if (assignment.getFormat().equalsIgnoreCase("H") &&
							(assignment.getType().equalsIgnoreCase("PF") ||
									assignment.getType().equalsIgnoreCase("PC") ||
									assignment.getType().equalsIgnoreCase("PJ"))) {
						//Ok do nothing
					} else {
						errorFlag="Y";
						break;
					}	
				}
				String weightSet="N";
				//Validate normal weight
				if (assignment.getNormalWeight()==null || assignment.getNormalWeight().trim().equalsIgnoreCase("")){
					errorFlag="Y";
					break;
				} else {
					if (!isInteger(assignment.getNormalWeight())){
						errorFlag="Y";
						break;
					} else {
						if (Integer.parseInt(assignment.getNormalWeight()) < 0 ||
								Integer.parseInt(assignment.getNormalWeight()) > 100)  {
							errorFlag="Y";
							break;
						}else{
							if (Integer.parseInt(assignment.getNormalWeight())!=0){
								weightSet="Y";
							}
						}
					}
				}
				//Validate repeat weight
				if (assignment.getRepeatWeight()==null || assignment.getRepeatWeight().trim().equalsIgnoreCase("")){
					errorFlag="Y";
					break;
				} else {
					if (!isInteger(assignment.getRepeatWeight())){
						errorFlag="Y";
						break;
					} else {
						if (Integer.parseInt(assignment.getRepeatWeight()) < 0 ||
								Integer.parseInt(assignment.getRepeatWeight()) > 100)  {
							errorFlag="Y";
							break;
						}else{
							if (Integer.parseInt(assignment.getRepeatWeight())!=0){
								weightSet="Y";
							}
						}
					}
				}	
				//Validate aegrotat weight
				if (assignment.getAegrotatWeight()==null || assignment.getAegrotatWeight().trim().equalsIgnoreCase("")){
					errorFlag="Y";
					break;
				} else {
					if (!isInteger(assignment.getAegrotatWeight())){
						errorFlag="Y";
						break;
					}else {
						if (Integer.parseInt(assignment.getAegrotatWeight()) < 0 ||
								Integer.parseInt(assignment.getAegrotatWeight()) > 100)  {
							errorFlag="Y";
							break;
						}else{
							if (Integer.parseInt(assignment.getAegrotatWeight())!=0){
								weightSet="Y";
							}
						}
					}
				}	
				//validate optionality
				if (assignment.getOptionality()==null || assignment.getOptionality().trim().equalsIgnoreCase("")){
					errorFlag="Y";
					break;
				}
				//Assignment weights must be 0 for self assessment assignments
				if ("O".equalsIgnoreCase(assignment.getOptionality())&& weightSet=="Y"){
					errorFlag="Y";
					break;
				}
				//Validate release flag - Yes/No choise to return marked portfolio - only research proposal modules on academic level H,M and D may use this option
				//Johanet - change 2015 BR
				//Johanet - debug
				if (assignment.getGroup().equalsIgnoreCase("S")) {
					if (assessmentCritForm.getStudyUnit().getResearchFlag().equalsIgnoreCase("P") &&
							(assessmentCritForm.getStudyUnit().getAcademicLevel().equalsIgnoreCase("H") ||
							assessmentCritForm.getStudyUnit().getAcademicLevel().equalsIgnoreCase("M") ||
							assessmentCritForm.getStudyUnit().getAcademicLevel().equalsIgnoreCase("D"))){
						//release flag must be set
						if (assignment.getReleaseFlag() == null || assignment.getReleaseFlag().trim().equalsIgnoreCase("")){
							errorFlag="Y";
							break;
						}
					}else{
						//release flag may not be Yes
						if (assignment.getReleaseFlag().equalsIgnoreCase("Y")){
							errorFlag="Y";
							break;
						}
					}
				}
//				if (assessmentCritForm.getStudyUnit().getResearchFlag().equalsIgnoreCase("P") &&
//						(assessmentCritForm.getStudyUnit().getAcademicLevel().equalsIgnoreCase("H") ||
//						assessmentCritForm.getStudyUnit().getAcademicLevel().equalsIgnoreCase("M") ||
//						assessmentCritForm.getStudyUnit().getAcademicLevel().equalsIgnoreCase("D"))){
//					//release flag must be set
//					if (assignment.getReleaseFlag() == null || assignment.getReleaseFlag().trim().equalsIgnoreCase("")){
//						errorFlag="Y";
//						break;
//					}
//				}else{
//					//release flag may not be Yes
//					if (assignment.getReleaseFlag().equalsIgnoreCase("Y")){
//						errorFlag="Y";
//						break;
//					}
//				}

				//if MCQ
				if ("A".equalsIgnoreCase(assignment.getFormat())){
					//Validate number of questions
					if (assignment.getNumberQuestions()==null || assignment.getNumberQuestions().trim().equalsIgnoreCase("")){
						errorFlag="Y";
						break;
					} else {
						if (!isInteger(assignment.getNumberQuestions())){
							errorFlag="Y";
							break;
						} else {
							if (Integer.parseInt(assignment.getNumberQuestions()) < 1 ||
									Integer.parseInt(assignment.getNumberQuestions()) > 140)  {
								errorFlag="Y";
								break;
							}
						}
					}
					//Validate memorandum - answer must be selected for each question
					//Not all answers may be set to Ignore question accept for surveys
					if (assignment.getListAnswer().size()!=Integer.parseInt(assignment.getNumberQuestions())){
						errorFlag="Y";
						break;
					}
					String allAnswersIgnoreQues="Y";
					for (int j=0; j<assignment.getListAnswer().size();j++){
						String answer = ((Memo)(assignment.getListAnswer().get(j))).getAnswer();
						if (answer.equalsIgnoreCase("")) {
							errorFlag="Y";
							break;							
						}
						if(!"0".equalsIgnoreCase(answer)){
							allAnswersIgnoreQues="N";
						}
					}
					if ("Y".equalsIgnoreCase(allAnswersIgnoreQues) && !survey){
						errorFlag="Y";
						break;	
					}
					//Negative marking
					if (assignment.getNegativeMarkingFactor()==null || assignment.getNegativeMarkingFactor().equalsIgnoreCase("")){
						errorFlag="Y";
						break;
						} else {
							if (!isInteger(assignment.getNegativeMarkingFactor())){
								errorFlag="Y";
								break;
							} else {
								if (Integer.parseInt(assignment.getNegativeMarkingFactor()) < 0 ||
										Integer.parseInt(assignment.getNegativeMarkingFactor()) > 8)  {
									errorFlag="Y";
									break;
								}
							}
					    }
				}
				//validate markers
				if ("H".equalsIgnoreCase(assignment.getFormat()) ||
						("MS".equalsIgnoreCase(assignment.getFormat()) &&
								!"R".equalsIgnoreCase(assignment.getType())&&
								!"S".equalsIgnoreCase(assignment.getType()))){
					List markers = new ArrayList();
					markers = assignment.getListMarkers();	
					if (markers!=null){
						for (int j=0; j < markers.size(); j++){
							if (((Marker)markers.get(j)).getStatus().equalsIgnoreCase("inactive")){
								if (((Marker)markers.get(j)).getMarkPercentage()==null ||
										((Marker)markers.get(j)).getMarkPercentage().trim()=="" ){								
								}else{
									if (Integer.parseInt(((Marker)markers.get(j)).getMarkPercentage().trim()) > 0) {
										errorFlag="Y";
										break;
									}
								}									
							}
						}
					}					
				}
				break;
			}		
			if ("Y".equalsIgnoreCase(errorFlag)){
				errorList.add("Assignment " + assignment.getNumber() + " does not satisfy required criteria, please review assessment details.");
			}			
		}
		
		return errorList;
	}
	
	public ActionForward unspecified(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response){

		log.info("AssessmentCriteria: unspecified method call -no value for parameter in request");

		return mapping.findForward("home");

	}

}
