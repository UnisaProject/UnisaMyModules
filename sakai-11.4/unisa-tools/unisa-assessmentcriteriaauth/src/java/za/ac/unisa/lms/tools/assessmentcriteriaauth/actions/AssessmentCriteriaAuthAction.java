package za.ac.unisa.lms.tools.assessmentcriteriaauth.actions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.tool.api.ToolManager;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserDirectoryService;

import Saacq01h.Abean.Saacq01sAssessmentCriteriaMnt;
import za.ac.unisa.lms.constants.EventTrackingTypes;
import za.ac.unisa.lms.dao.Gencod;
import za.ac.unisa.lms.dao.StudentSystemGeneralDAO;
import za.ac.unisa.lms.dao.general.UserDAO;
import za.ac.unisa.lms.domain.general.Person;
import za.ac.unisa.lms.domain.assessmentCriteria.*;
import za.ac.unisa.lms.dao.assessmentCriteria.AssessmentCriteriaDAO;
import za.ac.unisa.lms.tools.assessmentcriteriaauth.DAO.*;
import za.ac.unisa.lms.tools.assessmentcriteriaauth.forms.*;
import za.ac.unisa.utils.CoursePeriod;

public class AssessmentCriteriaAuthAction extends LookupDispatchAction{
	
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
		Map map = new HashMap();
		map.put("initial", "initial");
		map.put("inputStatusList", "inputStatusList");
		map.put("displayAssignment", "displayAssignment");
		map.put("button.continue","nextStep");
		map.put("button.cancel", "cancel");
		map.put("button.list", "displayStatusList");
		map.put("button.authorise","authorise");
		map.put("button.sendBack", "rejectRequest");
		map.put("button.back", "prevStep");
		return map;
	}
	
	public ActionForward initial(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
		ActionMessages messages = new ActionMessages();
		AssessmentCriteriaAuthForm assessmentCritAuthForm = (AssessmentCriteriaAuthForm) form;
		
		resetFormFields(assessmentCritAuthForm);
		//Get user details
		assessmentCritAuthForm.setNovellUserId(null);
		User user = null;
		
		sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
	    userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
		Session currentSession = sessionManager.getCurrentSession();
		String userID  = currentSession.getUserId();
				
		if (userID != null) {
			user = userDirectoryService.getUser(userID);
			assessmentCritAuthForm.setNovellUserId(user.getEid().toUpperCase());
		}
		//assessmentCritAuthForm.setNovellUserId("PRETOJ"); //debug
		
		AssessmentCriteriaAuthDAO authDao = new AssessmentCriteriaAuthDAO();
		
//		List deptList = new ArrayList();
//		List actingDeptList = new ArrayList();
		Person person = new Person();
		UserDAO userdao = new UserDAO();
		person = userdao.getPerson(assessmentCritAuthForm.getNovellUserId());
		
		if (person.getPersonnelNumber()==null){
			//user does not have access to this function 
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"User not found"));
			addErrors(request,messages);
			return mapping.findForward("displayErrorPage");
		}
		
		
		assessmentCritAuthForm.setListAuthDepartment(authDao.getDepartmentList(Integer.parseInt(person.getPersonnelNumber()),null,null)); 
		List <DepartmentRecord> deptList = new ArrayList<DepartmentRecord>();
		deptList.addAll(assessmentCritAuthForm.getListAuthDepartment());
		
		//Check if user is a Dean or Deputy Dean
		//Add College Departments to Department list on which user can extract a status list		
		List <CollegeRecord> collegeList = new ArrayList<CollegeRecord>();
		collegeList = authDao.getCollegeList(Integer.parseInt(person.getPersonnelNumber()));
		List <SchoolRecord> schoolList = new ArrayList<SchoolRecord>();
		schoolList = authDao.getSchoolList(Integer.parseInt(person.getPersonnelNumber()), null);
 		
		List <DepartmentRecord> tempDptList = new <DepartmentRecord> ArrayList();
		if (collegeList.size() > 0) {			
			for (int i=0; i < collegeList.size(); i++){
				tempDptList.addAll(authDao.getDepartmentList(null,((CollegeRecord)collegeList.get(i)).getCode(),null));
			}	
		}		
		if (tempDptList.size() > 0){
			if (deptList.size() > 0){
				for (int i=0; i < tempDptList.size(); i++){
					String inDeptListFlag="N";
					for (int j=0; j < deptList.size(); j++){
						if (((DepartmentRecord)tempDptList.get(i)).getCode().compareTo(((DepartmentRecord)deptList.get(j)).getCode())==0){
							inDeptListFlag="Y";
							j=deptList.size();
						}				
					}
					if (inDeptListFlag.equalsIgnoreCase("N")){
						deptList.add((DepartmentRecord)tempDptList.get(i));
					}
				}				
			}else {
				deptList.addAll(tempDptList);
			}
		}
		if (schoolList.size() > 0) {
			tempDptList = new <DepartmentRecord> ArrayList();
			for (int i=0; i < schoolList.size(); i++){
				tempDptList.addAll(authDao.getDepartmentList(null,((SchoolRecord)schoolList.get(i)).getCollegeCode(),((SchoolRecord)schoolList.get(i)).getCode()));
			}
		}
		if (tempDptList.size() > 0){
			if (deptList.size() > 0){
				for (int i=0; i < tempDptList.size(); i++){
					String inDeptListFlag="N";
					for (int j=0; j < deptList.size(); j++){
						if (((DepartmentRecord)tempDptList.get(i)).getCode().compareTo(((DepartmentRecord)deptList.get(j)).getCode())==0){
							inDeptListFlag="Y";
							j=deptList.size();
						}				
					}
					if (inDeptListFlag.equalsIgnoreCase("N")){
						deptList.add((DepartmentRecord)tempDptList.get(i));
					}
				}				
			}else {
				deptList.addAll(tempDptList);
			}
		}
		
		if (deptList.size()==0){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
			new ActionMessage("message.generalmessage","You are not authorised to use this tool"));
						addErrors(request,messages);
						return mapping.findForward("displayErrorPage");
		}
		

//		deptList = authDao.getCODDeptList(Integer.parseInt(person.getPersonnelNumber()));
//		actingDeptList=authDao.getActingCODDeptList(Integer.parseInt(person.getPersonnelNumber()));
//		
//		if (deptList.size()==0 && actingDeptList.size()==0){
//			//user not HOD
//			//determine if user is acting as HOD
//			//deptList = authDao.getActingCODDeptList(assessmentCritAuthForm.getNovellUserId());
//			if (deptList.size()==0){
//				//user does not have access to this function 
//				messages.add(ActionMessages.GLOBAL_MESSAGE,
//						new ActionMessage("message.generalmessage",
//									"You are not authorised to use this tool"));
//				addErrors(request,messages);
//				return mapping.findForward("displayErrorPage");
//			}
//		}
//		for (int i=0; i < actingDeptList.size(); i++){
//			String inDeptListFlag="N";
//			for (int j=0; j < deptList.size(); j++){
//				if (((LabelValueBean)actingDeptList.get(i)).getValue().equalsIgnoreCase(((LabelValueBean)deptList.get(j)).getValue())){
//					inDeptListFlag="Y";
//					j=deptList.size();
//				}				
//			}
//			if (inDeptListFlag.equalsIgnoreCase("N")){
//				deptList.add((LabelValueBean)actingDeptList.get(i));
//			}
//		}
			
		//Sort Department dropdown list for status report 		
		
		Collections.sort(deptList, new Comparator() {
					
			public int compare (Object o1, Object o2){
				DepartmentRecord m1 = (DepartmentRecord) o1;
				DepartmentRecord m2 = (DepartmentRecord) o2;
				return m1.getDescription().compareToIgnoreCase(m2.getDescription());	
				}
		});
		assessmentCritAuthForm.setListStatusDepartment(deptList);
		
		//set first department as default
		assessmentCritAuthForm.setSelectedStatusDepartment(((DepartmentRecord)assessmentCritAuthForm.getListStatusDepartment().get(0)).getCode());
		
		List courseList = new ArrayList();
		for (int i=0; i < assessmentCritAuthForm.getListAuthDepartment().size(); i++){
			List tempList = new ArrayList();
			tempList = authDao.getAuthoriseRequestList(((DepartmentRecord)assessmentCritAuthForm.getListAuthDepartment().get(i)).getCode());
			courseList.addAll(tempList);
		}
		
		assessmentCritAuthForm.setRequestList(courseList);
		
		//Get semester list
		List list = new ArrayList();
		StudentSystemGeneralDAO dao = new StudentSystemGeneralDAO();
		for (int i=0; i < dao.getGenCodes((short)54,1).size(); i++) {
			list.add(i, (Gencod)(dao.getGenCodes((short)54,1).get(i)));
		}
		assessmentCritAuthForm.setListSemester(list);
		
		//Get log status list
		list = new ArrayList();
		dao = new StudentSystemGeneralDAO();
		for (int i=0; i < dao.getGenCodes((short)53,1).size(); i++) {
			list.add(i, (Gencod)(dao.getGenCodes((short)53,1).get(i)));
		}
		assessmentCritAuthForm.setListGCStatus(list);
				
		return mapping.findForward("displayAuthRequestList");
	}		
	
	
	public ActionForward prevStep(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		AssessmentCriteriaAuthForm assessmentCritAuthForm = (AssessmentCriteriaAuthForm) form;
		String prevPage="";
		if("assignment".equalsIgnoreCase(assessmentCritAuthForm.getCurrentPage())){
			prevPage = "authorise";
		}
		return mapping.findForward(prevPage);
	}
			
		   
	public ActionForward nextStep(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
		AssessmentCriteriaAuthForm assessmentCritAuthForm = (AssessmentCriteriaAuthForm) form;
		
			String nextPage="";
			if("requestList".equalsIgnoreCase(assessmentCritAuthForm.getCurrentPage())){
				nextPage = displayAuthorisationPage(mapping, form, request, response);
			} 			
			return mapping.findForward(nextPage);			
		}
	
	public String displayAuthorisationPage(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		ActionMessages messages = new ActionMessages();
		AssessmentCriteriaAuthForm assessmentCritAuthForm = (AssessmentCriteriaAuthForm) form;
		
		if (assessmentCritAuthForm.getSelectedCourseIndex()==null ||
				assessmentCritAuthForm.getSelectedCourseIndex().length()==0){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"Please select a course to authorise."));
		}
		if (!messages.isEmpty()) {
			addErrors(request,messages);
			return "displayAuthRequestList";					
		}
		
		eventTrackingService = (EventTrackingService)ComponentManager.get(EventTrackingService.class);
    	toolManager = (ToolManager)ComponentManager.get(ToolManager.class);
    	usageSessionService =(UsageSessionService)ComponentManager.get(UsageSessionService.class);
		UsageSession usageSession = usageSessionService.getSession();		
		eventTrackingService.post(
				eventTrackingService.newEvent(EventTrackingTypes.EVENT_ASSESSCRIT_AUTH_VIEW, toolManager.getCurrentPlacement().getContext(), false),usageSession);
		
		AssessmentCriteriaDAO daoAssessCrit = new AssessmentCriteriaDAO();
		AssessmentCriteriaAuthDAO daoAuth = new AssessmentCriteriaAuthDAO();
		StudentSystemGeneralDAO dao = new StudentSystemGeneralDAO();
		
		CoursePeriod course = new CoursePeriod();
		
		course = (CoursePeriod)assessmentCritAuthForm.getRequestList().get(Integer.parseInt(assessmentCritAuthForm.getSelectedCourseIndex()));
		assessmentCritAuthForm.setSelectedCourse(course);
		assessmentCritAuthForm.setComment(null);
		
		//get study unit description
		//assessmentCritAuthForm.setCourseDesc(daoAuth.getStudyUnitDesc(assessmentCritAuthForm.getSelectedCourse().getCourseCode()));
		//get study unit
		StudyUnit studyUnit = daoAuth.getStudyUnit(assessmentCritAuthForm.getSelectedCourse().getCourseCode());
		assessmentCritAuthForm.setStudyUnit(studyUnit);
		
		//get final mark composition
		FinalMarkComposition finalMarkComp = new FinalMarkComposition();
		Examination firstExam = new Examination();
		firstExam = daoAssessCrit.getFirstExamination(Short.parseShort(String.valueOf(assessmentCritAuthForm.getSelectedCourse().getYear())),
				assessmentCritAuthForm.getSelectedCourse().getSemesterPeriod(),
				assessmentCritAuthForm.getSelectedCourse().getCourseCode());
		
		finalMarkComp = daoAssessCrit.getFinalMarkComposition(Short.parseShort(String.valueOf(firstExam.getYear()+1000)), firstExam.getPeriod(), assessmentCritAuthForm.getSelectedCourse().getCourseCode());
		assessmentCritAuthForm.setFinalMarkComp(finalMarkComp);
		
		assessmentCritAuthForm.setAdmissionMethod("");	
		if (finalMarkComp.getExamAdmissionMethod()!=null && !finalMarkComp.getExamAdmissionMethod().trim().equalsIgnoreCase("")){
			assessmentCritAuthForm.setAdmissionMethod(finalMarkComp.getExamAdmissionMethod());			
			Gencod gencod = new Gencod();			
			gencod = dao.getGenCode("224", finalMarkComp.getExamAdmissionMethod());
			gencod.setCode(finalMarkComp.getExamAdmissionMethod());
			if (gencod.getCode().equalsIgnoreCase("A1")){
				gencod.setEngDescription("Submission of at least one (any one) assessment");
			}
			assessmentCritAuthForm.setAdmissionMethod(gencod.getEngDescription());
		}
		if (studyUnit.getAutoExamAdmission().equalsIgnoreCase("Y")){
			assessmentCritAuthForm.setAdmissionMethod("Automatic Admission");
		}		
		
		//get assignments
		List listAssignment = new ArrayList();
		listAssignment = daoAssessCrit.getAssignments(Short.parseShort(String.valueOf(assessmentCritAuthForm.getSelectedCourse().getYear()+1000)), 
				assessmentCritAuthForm.getSelectedCourse().getSemesterPeriod(),
				assessmentCritAuthForm.getSelectedCourse().getCourseCode());
		for (int i=0; i < listAssignment.size(); i++){
			Assignment assignment = new Assignment();
			assignment = (Assignment)listAssignment.get(i);
			if("Y".equalsIgnoreCase(assignment.getOnscreenMarkFlag())){
				//get file formats for assignment
				List listFormat = new ArrayList<FileFormat>();				
				listFormat = daoAssessCrit.getFileFormats(Integer.parseInt(assignment.getUniqueNumber()));				
				((Assignment)(listAssignment.get(i))).setListFormat(listFormat);
							
				//get submission languages for assignment
				List listLanguage = new ArrayList<SubmissionLanguage>();				
				listLanguage = daoAssessCrit.getSubmissionLanguages(Integer.parseInt(assignment.getUniqueNumber()));
				((Assignment)(listAssignment.get(i))).setListLanguage(listLanguage);
			}	
			if (assignment.getType()!= null && assignment.getType().equalsIgnoreCase("P")){
				if (assignment.getSubType()==null || assignment.getSubType().equalsIgnoreCase("") || assignment.getSubType().equals("PORTFOLIO")){
					((Assignment)listAssignment.get(i)).setType("PF");
				}else{
					if (assignment.getSubType().equals("PROJECT")){
						((Assignment)listAssignment.get(i)).setType("PJ");
					}
					if (assignment.getSubType().equals("PRACTICAL")){
						((Assignment)listAssignment.get(i)).setType("PC");
					}
				}				
			}		
		}		
		
		assessmentCritAuthForm.setListAssignment(listAssignment);
		
		//Change BRD 2016
		//get default last submission dates
//		Short janExamPeriod = 1;
//		if (assessmentCritAuthForm.getFinalMarkComp().getExamPeriod().compareTo(janExamPeriod)==0){						
//			assessmentCritAuthForm.setLastAssDueControlDate(daoAssessCrit.getControlDueDate(Integer.parseInt(assessmentCritAuthForm.getAcademicYear()),
//					Short.parseShort(assessmentCritAuthForm.getSemester()),
//					"ALP", "TO"));
//			assessmentCritAuthForm.setLastPortfolioDueControlDate(daoAssessCrit.getControlDueDate(Integer.parseInt(assessmentCritAuthForm.getAcademicYear()),
//						Short.parseShort(assessmentCritAuthForm.getSemester()),
//					"PLP", "TO"));
//			}else{							
//				assessmentCritAuthForm.setLastAssDueControlDate(daoAssessCrit.getControlDueDate(Integer.parseInt(assessmentCritAuthForm.getAcademicYear()),
//						Short.parseShort(assessmentCritAuthForm.getSemester()),
//						"AL", "TO"));
//				assessmentCritAuthForm.setLastPortfolioDueControlDate(daoAssessCrit.getControlDueDate(Integer.parseInt(assessmentCritAuthForm.getAcademicYear()),
//						Short.parseShort(assessmentCritAuthForm.getSemester()),
//						"PL", "TO"));
//		}
				
		//get memorandum for multiple choice question paper
		for (int i=0; i < assessmentCritAuthForm.getListAssignment().size(); i++){
			if ("A".equalsIgnoreCase(((Assignment)assessmentCritAuthForm.getListAssignment().get(i)).getFormat())){
				String answers = "";
				List list = new ArrayList();
				Integer uniqueNr = Integer.parseInt(((Assignment)(assessmentCritAuthForm.getListAssignment().get(i))).getUniqueNumber());
				answers = daoAssessCrit.getAnswers(assessmentCritAuthForm.getSelectedCourse().getYear()+1000,
						assessmentCritAuthForm.getSelectedCourse().getSemesterPeriod(),uniqueNr).trim();
				//split memo up into arraylist values
				for (int j=0; j < answers.length(); j++){
					Memo memo = new Memo();
					memo.setQuestion(String.valueOf(j + 1));
					memo.setAnswer(String.valueOf(answers.substring(j, j+1)));
					list.add(j,memo);
				}
				((Assignment)(assessmentCritAuthForm.getListAssignment().get(i))).setListAnswer(list);
			}			
		}
		
		return "authorise";
	}
	
	public ActionForward inputStatusList(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
		//ActionMessages messages = new ActionMessages();
		//AssessmentCriteriaAuthForm assessmentCritAuthForm = (AssessmentCriteriaAuthForm) form;
				
		return mapping.findForward("displayInputStatusList");
	}
	
	public ActionForward authorise(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
		ActionMessages messages = new ActionMessages();
		AssessmentCriteriaAuthForm assessmentCritAuthForm = (AssessmentCriteriaAuthForm) form;
		AssLog asslog = new AssLog();
		AssessmentCriteriaDAO dao = new AssessmentCriteriaDAO();
		asslog = dao.getLatestLogEntry(Short.parseShort(String.valueOf(assessmentCritAuthForm.getSelectedCourse().getYear())),
				assessmentCritAuthForm.getSelectedCourse().getSemesterPeriod(),
				assessmentCritAuthForm.getSelectedCourse().getCourseCode());
		
		if("AUTHORISED".equalsIgnoreCase(asslog.getAction())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"The assessment plan has already been authorised."));
		}
		if("REQCAN".equalsIgnoreCase(asslog.getAction())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"The assessment plan authorisation request was cancelled."));
		}	
		int nrElectiveAss = 0;
		for (int i=0; i < assessmentCritAuthForm.getListAssignment().size(); i++){
			//determine the number of elective assignments.  If more than 2 elective assignments are defined on
			//the assessment plan, sent an email to vgassma@unisa.ac.za
			if ("E".equalsIgnoreCase(((Assignment)assessmentCritAuthForm.getListAssignment().get(i)).getOptionality())){
				nrElectiveAss = nrElectiveAss + 1;
			}
			List markers = new ArrayList();
			markers = ((Assignment)assessmentCritAuthForm.getListAssignment().get(i)).getListMarkers();	
			if (markers!=null){
				for (int j=0; j < markers.size(); j++){
					if (((Marker)markers.get(j)).getStatus().equalsIgnoreCase("inactive")){
						if (((Marker)markers.get(j)).getMarkPercentage()==null ||
								((Marker)markers.get(j)).getMarkPercentage().trim()=="" ){								
						}else{
							if (Integer.parseInt(((Marker)markers.get(j)).getMarkPercentage().trim()) > 0) {
								messages.add(ActionMessages.GLOBAL_MESSAGE,
										new ActionMessage("message.generalmessage",
													"Can not authorise. A mark percentage may not be assigned to an inactive marker. Mark percentage assigned to an inactive marker on assignment " +
													((Assignment)assessmentCritAuthForm.getListAssignment().get(i)).getNumber()));
							}
						}									
					}
				}
			}					
		}
		
		if (assessmentCritAuthForm.getComment().length()>250){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"Comment may not exceed 250 characters."));
		}
		if (!messages.isEmpty()) {
			addErrors(request,messages);
			return mapping.findForward("authorise");					
		}
		//get first examination 
		Examination firstExam = new Examination();
		firstExam = dao.getFirstExamination(Short.parseShort(String.valueOf(assessmentCritAuthForm.getSelectedCourse().getYear())),
				assessmentCritAuthForm.getSelectedCourse().getSemesterPeriod(),
				assessmentCritAuthForm.getSelectedCourse().getCourseCode());
		//flow to coolgen server to release assessment criteria on student system & write entry to log
		Saacq01sAssessmentCriteriaMnt op = new Saacq01sAssessmentCriteriaMnt();
		operListener opl = new operListener();
		op.clear();
		
		op.setInCsfClientServerCommunicationsAction("AA");
		op.setInFinalMarkCalculationExamYear(firstExam.getYear());
		op.setInFinalMarkCalculationMkExamPeriod(firstExam.getPeriod());
		op.setInFinalMarkCalculationMkStudyUnitCode(assessmentCritAuthForm.getSelectedCourse().getCourseCode());
		op.setInAssessmentLogYear(Short.parseShort(String.valueOf(assessmentCritAuthForm.getSelectedCourse().getYear())));
		op.setInAssessmentLogPeriod(assessmentCritAuthForm.getSelectedCourse().getSemesterPeriod());
		op.setInAssessmentLogMkStudyUnitCode(assessmentCritAuthForm.getSelectedCourse().getCourseCode());
		op.setInAssessmentLogUpdatedBy(assessmentCritAuthForm.getNovellUserId());
		op.setInAssessmentLogComments(assessmentCritAuthForm.getComment());
		op.setInAssessmentLogActionGc53("AUTHORISED");
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
			return mapping.findForward("authorise");
		}
		eventTrackingService = (EventTrackingService)ComponentManager.get(EventTrackingService.class);
    	toolManager = (ToolManager)ComponentManager.get(ToolManager.class);
    	usageSessionService =(UsageSessionService)ComponentManager.get(UsageSessionService.class);
		UsageSession usageSession = usageSessionService.getSession();		
		eventTrackingService.post(
				eventTrackingService.newEvent(EventTrackingTypes.EVENT_ASSESSCRIT_AUTH_AUTHORISE, toolManager.getCurrentPlacement().getContext(), false),usageSession);
		//email notification of assessment criteria approved
		String serverpath = ServerConfigurationService.getServerUrl();
		String toAddress=asslog.getReturnEmailAddr();
		//do not send email on dev & qa -default email to pretoj@unisa.ac.za	
		if (serverpath.contains("mydev") || serverpath.contains("myqa")){
			toAddress="pretoj@unisa.ac.za";
		}			
		String addressee="To Whom it May Concern:";
		UserDAO daoUser = new UserDAO();
		Person person = new Person();
		person=daoUser.getPerson(assessmentCritAuthForm.getNovellUserId());
		
		String responseText = "approved by " + person.getName();
		String course=assessmentCritAuthForm.getSelectedCourse().getCourseCode() + " " + String.valueOf(assessmentCritAuthForm.getSelectedCourse().getYear()) + "/" + assessmentCritAuthForm.getSelectedCourse().getSemesterType();
		sendNotifyEmail(toAddress, addressee, course, assessmentCritAuthForm.getComment(), responseText);			
		
		//If more than 2 elective assignments are defined on
		//the assessment plan, sent an email to vgassma@unisa.ac.za
		if (nrElectiveAss > 2) {
			toAddress="ramonpt@unisa.ac.za";
			//do not send email on dev & qa -default email to pretoj@unisa.ac.za			
			if (serverpath.contains("mydev") || serverpath.contains("myqa")){
				toAddress="pretoj@unisa.ac.za";
			}		
			addressee="Dear Tshepo, please note";
			sendNotifyEmail(toAddress, addressee, course, assessmentCritAuthForm.getComment(), "approved with more than 2 electives");			
		}
		
		AssessmentCriteriaAuthDAO authDao = new AssessmentCriteriaAuthDAO();
		List courseList = new ArrayList();
		for (int i=0; i < assessmentCritAuthForm.getListAuthDepartment().size(); i++){
			List tempList = new ArrayList();
			tempList = authDao.getAuthoriseRequestList(((DepartmentRecord)assessmentCritAuthForm.getListAuthDepartment().get(i)).getCode());
			courseList.addAll(tempList);
		}		
		assessmentCritAuthForm.setRequestList(courseList);
		
		return mapping.findForward("displayAuthRequestList");
	}
	
	public ActionForward displayAssignment(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
		ActionMessages messages = new ActionMessages();
		AssessmentCriteriaAuthForm assessmentCritAuthForm = (AssessmentCriteriaAuthForm) form;
	
		//get assignment to display
		Assignment assignment_i = new Assignment();
		Gencod gencod = new Gencod();
		StudentSystemGeneralDAO dao = new StudentSystemGeneralDAO();
		for (int i=0; i < assessmentCritAuthForm.getListAssignment().size(); i++){				
		assignment_i = (Assignment)(assessmentCritAuthForm.getListAssignment().get(i));	
			if (assignment_i.getNumber().equalsIgnoreCase(assessmentCritAuthForm.getSelectAssignment())){
				
				Assignment assignment = new Assignment();				
				
				assignment.setNumber(assignment_i.getNumber());
				assignment.setUniqueNumber(assignment_i.getUniqueNumber());
				assignment.setNormalWeight(assignment_i.getNormalWeight());
				assignment.setAegrotatWeight(assignment_i.getAegrotatWeight());
				assignment.setRepeatWeight(assignment_i.getRepeatWeight());
				assignment.setNumberQuestions(assignment_i.getNumberQuestions());
				assignment.setNegativeMarkingFactor(assignment_i.getNegativeMarkingFactor());				
				//Due date
				assignment.setDueDate(assignment_i.getDueDate());
				//set to test for MCQ AssignmentType ='A' store the code
				assessmentCritAuthForm.setAssFormatCode(assignment_i.getFormat());
				if (assignment_i.getFormat().equalsIgnoreCase("BL")||
						assignment_i.getFormat().equalsIgnoreCase("SA")||
						assignment_i.getFormat().equalsIgnoreCase("DF")||
						assignment_i.getFormat().equalsIgnoreCase("MS")||
						assignment_i.getFormat().equalsIgnoreCase("XA")||
						assignment_i.getFormat().equalsIgnoreCase("OR")){
					gencod = dao.getGenCode("176", assignment_i.getFormat());
				}else{
					gencod = dao.getGenCode("55", assignment_i.getFormat());
				}				
				//store the format description
				assignment.setFormat(gencod.getEngDescription());
				//Negative marking factor		
				//gencod = dao.getGenCode("60", assignment_i.getNegativeMarkingFactor());
				//.setNegativeMarkingFactor(gencod.getEngDescription());
				//Optionality
				gencod=dao.getGenCode("57", assignment_i.getOptionality());
				assignment.setOptionality(gencod.getEngDescription());
				if (assignment_i.getOptionality().equalsIgnoreCase("O") &&
						assignment_i.getCreditSystem().equalsIgnoreCase("5")){
					assignment.setOptionality("Self assessment");
				}
				if (assignment_i.getOptionality().equalsIgnoreCase("E")){
					assignment.setOptionality("Assessment is part of a group where best awarded marks apply.");
				}
				if (assignment_i.getOptionality().equalsIgnoreCase("M")){
					assignment.setOptionality("Assessment will always contribute to the year mark according to the weights.");
				}				
				//Type
				assessmentCritAuthForm.setAssTypeCode(assignment_i.getType());
				gencod=dao.getGenCode("56", assignment_i.getType());
				assignment.setType(gencod.getEngDescription());
				//Subsidy assignment
				if ("Y".equalsIgnoreCase(assignment_i.getSubsidyAssignment())){
					assignment.setSubsidyAssignment("Yes");
				}else{
					assignment.setSubsidyAssignment("No");
				}
				assignment.setYear(assignment_i.getYear());
				assignment.setPeriod(assignment_i.getPeriod());
				assignment.setOnscreenMarkFlag(assignment_i.getOnscreenMarkFlag());
				assignment.setStudentSpecifyLang(assignment_i.getStudentSpecifyLang());
				assignment.setFileReleaseDate(assignment_i.getFileReleaseDate());				
				assignment.setMaxFileSize(assignment_i.getMaxFileSize());
				if (assignment.getMaxFileSize()==null || assignment.getMaxFileSize().trim().equalsIgnoreCase("") || assignment.getMaxFileSize().equalsIgnoreCase("0")){
					assignment.setMaxFileSize("Default of 10Mb per file apply");  //Only display for Written assignments
				}				
				assignment.setGroup(assignment_i.getGroup());
				if (assignment.getGroup()==null || assignment.getGroup().trim().equalsIgnoreCase("")){
					assignment.setGroup("Assessment Group is only applicable for 2016 Assessment Plans onwards.");
				}else{
					gencod=new Gencod();
					gencod = dao.getGenCode("230", assignment.getGroup());
					assignment.setGroup(gencod.getEngDescription());
				}
				
				if (assignment.getFileReleaseDate()==null || assignment.getFileReleaseDate().trim().equalsIgnoreCase("") || 
						assignment.getFileReleaseDate().equalsIgnoreCase("00010101")){
					assignment.setFileReleaseDate("File will be released immediately after marking");  //Summative will not be released 2016 Changes
				}
				
				//Change BRD AP 2017
				assignment.setPfOpenDate(assignment_i.getPfOpenDate());
				assignment.setFinalSubmissionDate(assignment_i.getFinalSubmissionDate());
				if (assignment_i.getFinalSubmissionDate()==null ||
						assignment_i.getFinalSubmissionDate().substring(0, 8).equalsIgnoreCase("00010101")){						
							assignment.setFinalSubmissionDate("Assessment will be excepted until examination commence.");	
				} else {
					if (assignment_i.getGroup().equalsIgnoreCase("S") &&
							assignment_i.getFormat().equalsIgnoreCase("MS") &&
							assignment_i.getType().equalsIgnoreCase("O")){
						//already set
					} else {						
						if (assignment.getFinalSubmissionDate().length() > 8) {
							assignment.setFinalSubmissionDate(assignment.getFinalSubmissionDate().substring(0, 8));
						}
					}
				}
				if (assignment.getPfOpenDate()==null || assignment.getPfOpenDate().trim().equalsIgnoreCase("") || 
						assignment.getPfOpenDate().substring(0, 8).equalsIgnoreCase("00010101")){
					assignment.setPfOpenDate("Submission open from start of registration.");
				} else {
					if (assignment_i.getGroup().equalsIgnoreCase("S") &&
							assignment_i.getFormat().equalsIgnoreCase("MS") &&
							assignment_i.getType().equalsIgnoreCase("O")){
						//already set
					} else {
						if (assignment.getPfOpenDate().length() > 8) {
							assignment.setPfOpenDate(assignment.getPfOpenDate().substring(0, 8));
						}						
					}
				}
				assignment.setReleaseFlag(assignment_i.getReleaseFlag());
				
				if((assignment_i.getFormat().equalsIgnoreCase("H") && !assignment_i.getType().equalsIgnoreCase("T"))||
						assignment_i.getFormat().equalsIgnoreCase("MS") && !assignment_i.getType().equalsIgnoreCase("S") && !assignment_i.getType().equalsIgnoreCase("R")){
					//get markers
					AssessmentCriteriaDAO daoassesscrit = new AssessmentCriteriaDAO();
					List listMarkers = new ArrayList();
					listMarkers = daoassesscrit.getMarkers(assessmentCritAuthForm.getSelectedCourse().getYear()+1000, 
							assessmentCritAuthForm.getSelectedCourse().getYear(),
							assessmentCritAuthForm.getSelectedCourse().getSemesterPeriod(), 
							Integer.parseInt(assignment_i.getUniqueNumber()),
							assessmentCritAuthForm.getSelectedCourse().getCourseCode());
					assignment.setListMarkers(listMarkers);
				}
				List listFormat = new ArrayList<FileFormat>();
				if (assignment_i.getListFormat()!=null){
					listFormat = assignment_i.getListFormat();
				}
				assignment.setListFormat(listFormat);
				List listLang = new ArrayList<String>();
				if (assignment_i.getListLanguage()!=null){		
					for (int j=0; j < assignment_i.getListLanguage().size();j++){
						SubmissionLanguage subLang = new SubmissionLanguage();
						subLang = (SubmissionLanguage)assignment_i.getListLanguage().get(j);
						if (subLang.getLangangeGc203().trim().equalsIgnoreCase("OTHER")){
							listLang.add(subLang.getOtherLangDesc());
						}else{
							Gencod languageGC = new Gencod();
							languageGC = dao.getGenCode("203", subLang.getLangangeGc203());
							listLang.add(languageGC.getEngDescription().toUpperCase());
						}
					}					
				}	
				assignment.setListLanguage(listLang);
				assessmentCritAuthForm.setAssignment(assignment);
				i = assessmentCritAuthForm.getListAssignment().size();
			}
		}
		return mapping.findForward("displayAssignment");
	}
	
	public ActionForward rejectRequest(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
		ActionMessages messages = new ActionMessages();
		AssessmentCriteriaAuthForm assessmentCritAuthForm = (AssessmentCriteriaAuthForm) form;
		AssLog asslog = new AssLog();
		AssessmentCriteriaDAO dao = new AssessmentCriteriaDAO();
		asslog = dao.getLatestLogEntry(Short.parseShort(String.valueOf(assessmentCritAuthForm.getSelectedCourse().getYear())),
				assessmentCritAuthForm.getSelectedCourse().getSemesterPeriod(),
				assessmentCritAuthForm.getSelectedCourse().getCourseCode());
		
		if("AUTHORISED".equalsIgnoreCase(asslog.getAction())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"The assessment plan has already been authorised."));
		}
		if("AUTHREJ".equalsIgnoreCase(asslog.getAction())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"The assessment plan authorisation has already been rejected."));
		}
		if("REQCAN".equalsIgnoreCase(asslog.getAction())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"The assessment plan authorisation request was cancelled."));
		}	
		if("".equalsIgnoreCase(assessmentCritAuthForm.getComment())||assessmentCritAuthForm.getComment()==null){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("errors.required",
								"Comment"));
		}else{
			if (assessmentCritAuthForm.getComment().length()>250){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"Comment may not exceed 250 characters."));
			}
		}
		
		if (!messages.isEmpty()) {
			addErrors(request,messages);
			return mapping.findForward("authorise");					
		}
		//flow to coolgen server to write entry to log
		Saacq01sAssessmentCriteriaMnt op = new Saacq01sAssessmentCriteriaMnt();
		operListener opl = new operListener();
		op.clear();
		
		op.setInCsfClientServerCommunicationsAction("CL");
		op.setInAssessmentLogYear(Short.parseShort(String.valueOf(assessmentCritAuthForm.getSelectedCourse().getYear())));
		op.setInAssessmentLogPeriod(assessmentCritAuthForm.getSelectedCourse().getSemesterPeriod());
		op.setInAssessmentLogMkStudyUnitCode(assessmentCritAuthForm.getSelectedCourse().getCourseCode());
		op.setInAssessmentLogUpdatedBy(assessmentCritAuthForm.getNovellUserId());
		op.setInAssessmentLogComments(assessmentCritAuthForm.getComment());
		op.setInAssessmentLogActionGc53("AUTHREJ");
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
			return mapping.findForward("authorise");
		}
		eventTrackingService = (EventTrackingService)ComponentManager.get(EventTrackingService.class);
    	toolManager = (ToolManager)ComponentManager.get(ToolManager.class);
    	usageSessionService =(UsageSessionService)ComponentManager.get(UsageSessionService.class);
		UsageSession usageSession = usageSessionService.getSession();		
		eventTrackingService.post(
				eventTrackingService.newEvent(EventTrackingTypes.EVENT_ASSESSCRIT_AUTH_REJECT, toolManager.getCurrentPlacement().getContext(), false),usageSession);
		//email notification of assessment criteria rejected
		String serverpath = ServerConfigurationService.getServerUrl();
		//do not send email on dev & qa -default email to pretoj@unisa.ac.za	
		String toAddress=asslog.getReturnEmailAddr();
		if (serverpath.contains("mydev") || serverpath.contains("myqa")){
			toAddress="pretoj@unisa.ac.za";
		}	
		UserDAO daoUser = new UserDAO();
		Person person = new Person();
		person=daoUser.getPerson(assessmentCritAuthForm.getNovellUserId());
		
		String responseText = "rejected by " + person.getName();
		String addressee="To Whom it May Concern:";
		String course=assessmentCritAuthForm.getSelectedCourse().getCourseCode() + " " + String.valueOf(assessmentCritAuthForm.getSelectedCourse().getYear()) + "/" + assessmentCritAuthForm.getSelectedCourse().getSemesterType();
		sendNotifyEmail(toAddress, addressee, course, assessmentCritAuthForm.getComment(), responseText);			
		
		AssessmentCriteriaAuthDAO authDao = new AssessmentCriteriaAuthDAO();
		List courseList = new ArrayList();
		for (int i=0; i < assessmentCritAuthForm.getListAuthDepartment().size(); i++){
			List tempList = new ArrayList();
			tempList = authDao.getAuthoriseRequestList(((DepartmentRecord)assessmentCritAuthForm.getListAuthDepartment().get(i)).getCode());
			courseList.addAll(tempList);
		}		
		assessmentCritAuthForm.setRequestList(courseList);
		
		return mapping.findForward("displayAuthRequestList");
	}
	
	public ActionForward cancel(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
		AssessmentCriteriaAuthForm assessmentCritAuthForm = (AssessmentCriteriaAuthForm) form;
							
		return mapping.findForward("displayAuthRequestList");
			
		}
	
	public ActionForward displayStatusList(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
		ActionMessages messages = new ActionMessages();
		AssessmentCriteriaAuthForm assessmentCritAuthForm = (AssessmentCriteriaAuthForm) form;
				
		//Do validation
		if (assessmentCritAuthForm.getAcademicYear()==null || assessmentCritAuthForm.getAcademicYear().trim().equalsIgnoreCase("")){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"Academic Year is required"));
		} else {
			if (!testInteger(assessmentCritAuthForm.getAcademicYear())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"Academic Year must be numeric"));
			}
		}	
		if (assessmentCritAuthForm.getSemester()==null || assessmentCritAuthForm.getSemester().trim().equalsIgnoreCase("")){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"Semester is required"));
		}
		if (!messages.isEmpty()) {
			addErrors(request,messages);
			return mapping.findForward("displayInputStatusList");					
		}	
		
		List statusList = new ArrayList();
		List studyUnitList = new ArrayList();
		String primaryLecturer="";
		AssessmentCriteriaAuthDAO dao = new AssessmentCriteriaAuthDAO();
		//get all study units linked to department
		studyUnitList=dao.getDeptStudyUnitList(Short.parseShort(assessmentCritAuthForm.getAcademicYear()),
				Short.parseShort(assessmentCritAuthForm.getSemester()),
				assessmentCritAuthForm.getSelectedStatusDepartment());
		for (int i=0; i < studyUnitList.size(); i++){
			AssessmentCriteriaStatusRecord statusRecord = new AssessmentCriteriaStatusRecord();
			statusRecord.setStudyUnit(studyUnitList.get(i).toString().trim());
			//get primary lecturer
			primaryLecturer=dao.getPrimaryLecturer(Short.parseShort(assessmentCritAuthForm.getAcademicYear()),
				Short.parseShort(assessmentCritAuthForm.getSemester()),
				statusRecord.getStudyUnit());
			if (!"".equalsIgnoreCase(primaryLecturer)){
				UserDAO daoUser = new UserDAO();
				Person person = new Person();
				person=daoUser.getPerson(primaryLecturer);
				statusRecord.setPrimaryLecturer(person.getName());				
			} 
			//get status
			statusRecord.setStatus("Not Started");
			AssessmentCriteriaDAO daoAssessCrit = new AssessmentCriteriaDAO();
			//determine if sunpdt record exists
			if (!dao.existSUNPDT(Short.parseShort(assessmentCritAuthForm.getAcademicYear()),
					Short.parseShort(assessmentCritAuthForm.getSemester()),
					statusRecord.getStudyUnit())) {
				statusRecord.setStatus("Not Started: Period detail record does not exist");
			} else {
				AssLog logEntry = new AssLog();
				//get latest status entry in log 
				logEntry=daoAssessCrit.getLatestLogEntry(Short.parseShort(assessmentCritAuthForm.getAcademicYear()),
						Short.parseShort(assessmentCritAuthForm.getSemester()),
						statusRecord.getStudyUnit());
				if (logEntry.getStudyUnit()==null){
					//no log entry exists for course
					//determine if UNQASS records exists for year
					List listUNQASS = new ArrayList();
					listUNQASS = daoAssessCrit.getAssignments(Short.parseShort(assessmentCritAuthForm.getAcademicYear()),
							Short.parseShort(assessmentCritAuthForm.getSemester()),
							statusRecord.getStudyUnit());
					if (listUNQASS.size()>0){
						statusRecord.setStatus("Authorised");
					}
					//determine if FINMARK or UNQASS records exists for dummy year(year + 1000)
					//Examination exam = new Examination();
					//exam = daoAssessCrit.getFirstExamination(Short.parseShort(assessmentCritAuthForm.getAcademicYear()),
					//		Short.parseShort(assessmentCritAuthForm.getSemester()),
					//		statusRecord.getStudyUnit());
					//Integer dummyExamYear= exam.getYear() + 1000;
					//FinalMarkComposition finmrk = new FinalMarkComposition();
					//finmrk = daoAssessCrit.getFinalMarkComposition(Short.parseShort((dummyExamYear.toString())),
					//			exam.getPeriod(),
					//			statusRecord.getStudyUnit());
					//if (finmrk.getExamYear()==null){
					//	listUNQASS = new ArrayList();
					//	Integer dummyYear = Integer.parseInt(assessmentCritAuthForm.getAcademicYear()) + 1000;
					//	listUNQASS = daoAssessCrit.getAssignments(Short.parseShort(dummyYear.toString()), Short.parseShort(assessmentCritAuthForm.getSemester()), statusRecord.getStudyUnit());
					//	if (listUNQASS.size()>0){
					//		statusRecord.setStatus("In Progress");
					//	}
					//}else{
					//	statusRecord.setStatus("In Progress");
					//}
					
				}else{
					//Get status description					
					statusRecord.setStatus(logEntry.getAction());
					StudentSystemGeneralDAO daoGenCod = new StudentSystemGeneralDAO();
					for (int j=0; j < assessmentCritAuthForm.getListGCStatus().size(); j++) {
						if (((Gencod)(assessmentCritAuthForm.getListGCStatus().get(j))).getCode().equalsIgnoreCase(logEntry.getAction())){
							statusRecord.setStatus(((Gencod)(assessmentCritAuthForm.getListGCStatus().get(j))).getEngDescription());
							j = assessmentCritAuthForm.getListGCStatus().size();
						}
					}						
					statusRecord.setLastActionDate(logEntry.getUpdatedOn().substring(0, 16));
				}
				statusList.add(statusRecord);
			}			
		}
		request.setAttribute("statusList", statusList);
		eventTrackingService = (EventTrackingService)ComponentManager.get(EventTrackingService.class);
    	toolManager = (ToolManager)ComponentManager.get(ToolManager.class);
    	usageSessionService =(UsageSessionService)ComponentManager.get(UsageSessionService.class);
		UsageSession usageSession = usageSessionService.getSession();		
		eventTrackingService.post(
				eventTrackingService.newEvent(EventTrackingTypes.EVENT_ASSESSCRIT_AUTH_VIEWLIST, toolManager.getCurrentPlacement().getContext(), false),usageSession);
		
		return mapping.findForward("displayStatusList");
	}
	
	public void sendNotifyEmail(String toAddress, String addressee, String course, String comment, String response) {

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

			/* send activation email to student */
			String subject = "Assessment plan - " + course + "-" + response;
			String body = "<html> "+
		    "<body> "+ addressee + "<br/><br/>"+
			"NB: This is an automated response - do not reply to this e-mail.  <br/><br/> "+
			"The assessment plan for " + course + " was " + response + ".";
			
			if (comment==null || comment.equalsIgnoreCase("")){
				body = body + 
				"</body>"+
				"</html>";
			}else{
				body = body + 
				"<br/><br/>Comment: " + comment +
				"</body>"+
				"</html>";
			}			

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
	
	private void resetFormFields(AssessmentCriteriaAuthForm assessmentCritAuthForm)
	throws Exception {		
		assessmentCritAuthForm.setAcademicYear(null);
		assessmentCritAuthForm.setAssignment(null);
		assessmentCritAuthForm.setAssFormatCode(null);
		assessmentCritAuthForm.setAssTypeCode(null);
		assessmentCritAuthForm.setComment(null);
		assessmentCritAuthForm.setCurrentPage(null);
		assessmentCritAuthForm.setFinalMarkComp(null);
		assessmentCritAuthForm.setListAssignment(null);
		assessmentCritAuthForm.setListAuthDepartment(null);
		assessmentCritAuthForm.setListStatusDepartment(null);
		assessmentCritAuthForm.setListGCStatus(null);
		assessmentCritAuthForm.setListSemester(null);
		assessmentCritAuthForm.setNovellUserId(null);
		assessmentCritAuthForm.setRequestList(null);
		assessmentCritAuthForm.setSelectAssignment(null);
		assessmentCritAuthForm.setSelectedCourse(null);
		assessmentCritAuthForm.setSelectedCourseIndex(null);
		assessmentCritAuthForm.setSelectedAuthDepartment(null);
		assessmentCritAuthForm.setSelectedStatusDepartment(null);
		assessmentCritAuthForm.setSemester(null);
		assessmentCritAuthForm.setAdmissionMethod("");
		assessmentCritAuthForm.setStudyUnit(new StudyUnit());
		}
}
