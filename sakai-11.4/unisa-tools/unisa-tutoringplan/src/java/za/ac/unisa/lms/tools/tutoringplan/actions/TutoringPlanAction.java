package za.ac.unisa.lms.tools.tutoringplan.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.LookupDispatchAction;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.email.api.EmailService;
import org.sakaiproject.event.api.EventTrackingService;
import org.sakaiproject.event.api.UsageSessionService;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.tool.api.ToolManager;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserDirectoryService;

import za.ac.unisa.lms.dao.Gencod;
import za.ac.unisa.lms.dao.StudentSystemGeneralDAO;
import za.ac.unisa.lms.dao.general.ModuleDAO;
import za.ac.unisa.lms.dao.general.UserDAO;
import za.ac.unisa.lms.domain.general.Person;
import za.ac.unisa.lms.domain.general.StudyUnit;
import za.ac.unisa.lms.tools.tutoringplan.dao.TutoringPlanDao;
import za.ac.unisa.lms.tools.tutoringplan.forms.*;
import za.ac.unisa.utils.CoursePeriodLookup;

public class TutoringPlanAction extends LookupDispatchAction {
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
		map.put("displayTutoringMode", "displayTutoringMode");
		map.put("editTutoringMode", "editTutoringMode");
		map.put("button.back", "prevStep");
		map.put("button.continue","nextStep");
		map.put("button.cancel", "cancel");
		map.put("button.addTutorMode","addTutoringMode");
		map.put("button.removeTutorMode", "removeTutoringMode");
		map.put("button.submit", "saveTutoringMode");
		map.put("button.auditTrail", "displayAuditTrail");
			
		return map;
	}
	
	public ActionForward initial(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
		TutoringPlanForm tutPlanForm = (TutoringPlanForm) form;
		ActionMessages messages = new ActionMessages();
		
		tutPlanForm.setAcadYear("");		
		tutPlanForm.setSemester("");	
		tutPlanForm.setInputStudyUnit("");		
		//Get user details
		tutPlanForm.setUserId(null);
		User user = null;
		
		sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
	    userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
		Session currentSession = sessionManager.getCurrentSession();
		String userID  = currentSession.getUserId();
				
		if (userID != null) {
			user = userDirectoryService.getUser(userID);
			tutPlanForm.setUserId(user.getEid().toUpperCase());
		}

		Person person = new Person();
		UserDAO userdao = new UserDAO();
		person = userdao.getPerson(tutPlanForm.getUserId());	
		
		if (person.getPersonnelNumber()==null){
			//user does not have access to this function 
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"User not found"));
			addErrors(request,messages);
			return mapping.findForward("errorPage");
		}		
		tutPlanForm.setUser(person);		
		
		//Get semester list
		List list = new ArrayList();
		StudentSystemGeneralDAO dao = new StudentSystemGeneralDAO();
		for (int i=0; i < dao.getGenCodes((short)54,1).size(); i++) {
			list.add(i, (Gencod)(dao.getGenCodes((short)54,1).get(i)));
		}
		tutPlanForm.setListSemester(list);	
		
		//Get tutoring mode list
		list = new ArrayList();
		for (int i=0; i < dao.getGenCodes((short)181,3).size(); i++) {
			Gencod gencod = new Gencod();
			gencod = (Gencod)(dao.getGenCodes((short)181,3).get(i));
			if (gencod.getCode().equalsIgnoreCase("TEACH")){
				gencod.setEngDescription(gencod.getEngDescription().trim() + " (only for Signature Courses)");
			}
			list.add(i,gencod );
		}		
		tutPlanForm.setListTutoringMode(list);	
		
		//Get grouping method
		list = new ArrayList();
		for (int i=0; i < dao.getGenCodes((short)182,3).size(); i++) {
			list.add(i, (Gencod)(dao.getGenCodes((short)182,3).get(i)));
		}
		tutPlanForm.setListGroupingMethod(list);	
		
		//Get Yes No
		list = new ArrayList();
		for (int i=0; i < dao.getGenCodes((short)51,3).size(); i++) {
			list.add(i, (Gencod)(dao.getGenCodes((short)51,3).get(i)));
		}
		tutPlanForm.setListYesNo(list);	
		
		//Get tutor contact details
		list = new ArrayList();
		for (int i=0; i < dao.getGenCodes((short)184,3).size(); i++) {
			//list.add(i, (Gencod)(dao.getGenCodes((short)184,3).get(i)));
			Gencod gencod = new Gencod();
			gencod = (Gencod)(dao.getGenCodes((short)184,3).get(i));
			if (gencod.getCode().equalsIgnoreCase("CELLNR")){
				gencod.setEngDescription(gencod.getEngDescription().trim() + " (not yet available)");
			}
			list.add(i,gencod );
		}
		tutPlanForm.setListContactDetail(list);	
		
		//Get allocation criteria
		list = new ArrayList();
		for (int i=0; i < dao.getGenCodes((short)183,3).size(); i++) {
			Gencod gencod = new Gencod();
			gencod = (Gencod)(dao.getGenCodes((short)183,3).get(i));
			if (gencod.getCode().equalsIgnoreCase("GEOGRAPH")){
				gencod.setEngDescription(gencod.getEngDescription().trim() + " (not yet available)");
			}
			list.add(i,gencod );
		}
		tutPlanForm.setListAllocCriteria(list);	
				
		return mapping.findForward("inputTutoringPlan");
	}
	
	public ActionForward prevStep(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
		TutoringPlanForm tutPlanForm = (TutoringPlanForm) form;
			String nextPage="";
			
			if (tutPlanForm.getCurrentPage().equalsIgnoreCase("displayTutoringPlan")){
				nextPage="inputTutoringPlan";
			}
			if (tutPlanForm.getCurrentPage().equalsIgnoreCase("displayTutoringMode")){
				nextPage="displayTutoringPlan";
			}
			if (tutPlanForm.getCurrentPage().equalsIgnoreCase("displayAuditTrail")){
				nextPage="displayTutoringPlan";
			}
			
			
			return mapping.findForward(nextPage);
			
	}
	
	public ActionForward nextStep(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
		TutoringPlanForm tutPlanForm = (TutoringPlanForm) form;
			
			String nextPage="inputTutoringPlan";
			
			if (tutPlanForm.getCurrentPage().equalsIgnoreCase("inputTutoringPlan")){
				nextPage = displayTutoringPlan(mapping,form,request,response);
			}
			
			return mapping.findForward(nextPage);
			
	}
	
	public ActionForward cancel(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
			TutoringPlanForm tutPlanForm = (TutoringPlanForm) form;
			tutPlanForm.setAcadYear("");
			tutPlanForm.setSemester("");
			tutPlanForm.setSemesterType("");
			tutPlanForm.setInputStudyUnit("");
						
			return mapping.findForward("cancel");
			
	}
	
	public ActionForward displayTutoringMode(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
		TutoringPlanForm tutPlanForm = (TutoringPlanForm) form;
		
		for (int i=0; i < tutPlanForm.getTutoringPlan().getListTutoringMode().size();i++){
			TutoringMode tutoringMode = new TutoringMode();
			tutoringMode=(TutoringMode)tutPlanForm.getTutoringPlan().getListTutoringMode().get(i);
			if (tutoringMode.getTutorMode().equalsIgnoreCase(tutPlanForm.getSelectTutoringMode())){
				tutPlanForm.setTutoringMode(tutoringMode);
				i = tutPlanForm.getTutoringPlan().getListTutoringMode().size();
			}
		}
		tutPlanForm.setEditMode("view");
				
		return mapping.findForward("displayTutoringMode");
			
	}
	
	public ActionForward editTutoringMode(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
		TutoringPlanForm tutPlanForm = (TutoringPlanForm) form;
		
		for (int i=0; i < tutPlanForm.getTutoringPlan().getListTutoringMode().size();i++){
			TutoringMode tutoringMode = new TutoringMode();
			tutoringMode=(TutoringMode)tutPlanForm.getTutoringPlan().getListTutoringMode().get(i);
			if (tutoringMode.getTutorMode().equalsIgnoreCase(tutPlanForm.getSelectTutoringMode())){
				tutPlanForm.setTutoringMode(tutoringMode);
				i = tutPlanForm.getTutoringPlan().getListTutoringMode().size();
			}
		}
		tutPlanForm.setEditMode("edit");
				
		return mapping.findForward("displayTutoringMode");
			
	}
	
	public ActionForward saveTutoringMode(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
		TutoringPlanForm tutPlanForm = (TutoringPlanForm) form;
		ActionMessages messages = new ActionMessages();
		
		if (tutPlanForm.getTutoringMode().getTutorMode()==null || tutPlanForm.getTutoringMode().getTutorMode().trim().equalsIgnoreCase("")){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"Please select a tutoring mode."));
		}
		if (tutPlanForm.getTutoringMode().getGroupChoice()==null || tutPlanForm.getTutoringMode().getGroupChoice().trim().equalsIgnoreCase("")){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"Please select a grouping method."));
		}
		if (tutPlanForm.getTutoringMode().getTutorStuRatio()==null || tutPlanForm.getTutoringMode().getTutorStuRatio().trim().equalsIgnoreCase("")){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"Tutor-student ratio per group is required."));
		} else {
			if (!isInteger(tutPlanForm.getTutoringMode().getTutorStuRatio())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"Tutor-student ratio per group must be numeric."));
			}
		}
		if (tutPlanForm.getTutoringMode().getMaxGroupsPerTutor()==null || tutPlanForm.getTutoringMode().getMaxGroupsPerTutor().trim().equalsIgnoreCase("")){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"Maximum number of groups per tutor is required."));
		} else {
			if (!isInteger(tutPlanForm.getTutoringMode().getMaxGroupsPerTutor())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"Maximum number of groups per tutor must be numeric."));
			}
		}
		if (tutPlanForm.getTutoringMode().getIncludeLectAsTutors()==null || tutPlanForm.getTutoringMode().getIncludeLectAsTutors().trim().equalsIgnoreCase("")){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"Please indicate whether lecturing staff should be included as tutors."));
		}
		if (tutPlanForm.getTutoringMode().getTutorContact()==null || tutPlanForm.getTutoringMode().getTutorContact().trim().equalsIgnoreCase("")){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"Please indicate the tutor contact detials to be supplied to the students."));
		}
		if (tutPlanForm.getTutoringMode().getAllocationCriteria()==null || tutPlanForm.getTutoringMode().getAllocationCriteria().trim().equalsIgnoreCase("")){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"Please select the group allocation criteria."));
		}
		if (!messages.isEmpty()) {
			addErrors(request,messages);
			return mapping.findForward("displayTutoringMode");
		}
		TutoringPlanDao dao = new TutoringPlanDao();
		TutoringPlan tutoringPlan = new TutoringPlan();
		tutoringPlan = dao.getTutoringPlan(tutPlanForm.getStudyUnit().getCode(),Short.parseShort(tutPlanForm.getAcadYear()),Short.parseShort(tutPlanForm.getSemester()));
		
		if (Integer.parseInt(tutPlanForm.getTutoringMode().getTutorStuRatio()) < Integer.parseInt(tutPlanForm.getTutoringMode().getMaxGroupsPerTutor())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"The maximum number of groups per tutor may not exceed the Tutor-student ratio per group."));
		}
		
		if (tutPlanForm.getEditMode().equalsIgnoreCase("add")){
			if (tutoringPlan.getListTutoringMode().size()>0){
				boolean groupMethodNotManual = false;
				for(int i=0; i < tutoringPlan.getListTutoringMode().size(); i++){
					TutoringMode tutorMode = new TutoringMode();
					tutorMode = (TutoringMode)tutoringPlan.getListTutoringMode().get(i);
					if (tutPlanForm.getTutoringMode().getTutorMode().equalsIgnoreCase(tutorMode.getTutorMode())){
						messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
							"Tutoring mode already exists."));
						i = tutoringPlan.getListTutoringMode().size();
					}
					if (!tutorMode.getGroupChoice().equalsIgnoreCase("MANUAL")){
						groupMethodNotManual=true;
					}
				}
				if (groupMethodNotManual){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
							"Only MANUAL grouping is allowed, if more than one tutoring mode per module. First set the grouping method of all existing tutoring modes to 'MANUAL' before adding a new one."));
				}
				if (!groupMethodNotManual && !tutPlanForm.getTutoringMode().getGroupChoice().equalsIgnoreCase("MANUAL")){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
						"Only MANUAL grouping is allowed, if more than one tutoring mode per module."));
				}
			}
		}
		if (tutPlanForm.getEditMode().equalsIgnoreCase("edit")){
			if (tutoringPlan.getListTutoringMode().size()>1){
				if (!tutPlanForm.getTutoringMode().getGroupChoice().equalsIgnoreCase("MANUAL")){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
							"Only MANUAL grouping is allowed, if more than one tutoring mode per module."));
				}
				
			}
		}	
		
		if (tutPlanForm.getTutoringMode().getTutorMode().equalsIgnoreCase("ETUTOR")){
			if ((Integer.parseInt(tutPlanForm.getTutoringMode().getTutorStuRatio()) * Integer.parseInt(tutPlanForm.getTutoringMode().getMaxGroupsPerTutor())) > 200){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
					"The maximum number of students per e-tutor is 200. The 'Tutor-Student ratio per group' multipied by 'maximum number of groups per Tutor' should not exceed 200"));
			}
		}
		if (tutPlanForm.getTutoringMode().getTutorMode().equalsIgnoreCase("SCIENCE")){
			if (Integer.parseInt(tutPlanForm.getTutoringMode().getTutorStuRatio()) * Integer.parseInt(tutPlanForm.getTutoringMode().getMaxGroupsPerTutor()) > 200){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
					"The maximum number of students per SFP tutor is 200. The 'Tutor-Student ratio per group' multipied by 'maximum number of groups per Tutor' should not exceed 200"));
			}			
		}
		if (tutPlanForm.getTutoringMode().getTutorMode().equalsIgnoreCase("TEACH")){
			if (Integer.parseInt(tutPlanForm.getTutoringMode().getTutorStuRatio()) * Integer.parseInt(tutPlanForm.getTutoringMode().getMaxGroupsPerTutor()) > 200){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
					"The maximum number of students per teaching assistant is 200. The 'Tutor-Student ratio per group' multiplied by 'maximum number of groups per Tutor' should not exceed 200"));
			}
		}
		if (tutPlanForm.getTutoringMode().getTutorMode().equalsIgnoreCase("FACE")){
			if (!tutPlanForm.getTutoringMode().getGroupChoice().equalsIgnoreCase("MANUAL")){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
					"Only MANUAL grouping is allowed for face-to-face tutoring mode."));
			}
		}			
		
		if (!messages.isEmpty()) {
			addErrors(request,messages);
			return mapping.findForward("displayTutoringMode");
		}
		
		Gencod gencod = new Gencod();
		StudentSystemGeneralDAO studentDao = new StudentSystemGeneralDAO();
		gencod = studentDao.getGenCode("181", tutPlanForm.getTutoringMode().getTutorMode());
		if (gencod!=null && gencod.getEngDescription()!=null){
			tutPlanForm.getTutoringMode().setTutorModeDesc(gencod.getEngDescription());
		}else {
			tutPlanForm.getTutoringMode().setTutorModeDesc(tutPlanForm.getTutoringMode().getTutorMode());
		}
		
		if (tutPlanForm.getEditMode().equalsIgnoreCase("add")){
			dao.addTutoringMode(tutPlanForm.getStudyUnit().getCode(),Short.parseShort(tutPlanForm.getAcadYear()),Short.parseShort(tutPlanForm.getSemester()),tutPlanForm.getTutoringMode(),tutPlanForm.getUserId());
		}
		if (tutPlanForm.getEditMode().equalsIgnoreCase("edit")){
			dao.updateTutoringMode(tutPlanForm.getStudyUnit().getCode(),Short.parseShort(tutPlanForm.getAcadYear()),Short.parseShort(tutPlanForm.getSemester()),tutPlanForm.getTutoringMode(),tutPlanForm.getUserId());
		}
		
		tutoringPlan = new TutoringPlan();
		tutoringPlan = dao.getTutoringPlan(tutPlanForm.getStudyUnit().getCode(),Short.parseShort(tutPlanForm.getAcadYear()),Short.parseShort(tutPlanForm.getSemester()));
		tutPlanForm.setTutoringPlan(tutoringPlan);
		
				
		return mapping.findForward("displayTutoringPlan");
			
	}
	
	public ActionForward removeTutoringMode(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
		TutoringPlanForm tutPlanForm = (TutoringPlanForm) form;
		ActionMessages messages = new ActionMessages();
		
		//do validation
		if (tutPlanForm.getIndexSelectedRemoveTutorMode()==null ||
				tutPlanForm.getIndexSelectedRemoveTutorMode().length == 0) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
							"At least on tutoring mode must be selected to be removed."));				
		}
		if (!messages.isEmpty()) {
			addErrors(request,messages);
			return mapping.findForward("displayTutoringPlan");
		}
		
		TutoringPlanDao dao = new TutoringPlanDao();
		
		String errorStr = "";
		int errorNr=0;
		for(int i=0; i < tutPlanForm.getIndexSelectedRemoveTutorMode().length; i++){
			TutoringMode tutorMode = new TutoringMode();
			int index = Integer.parseInt(tutPlanForm.getIndexSelectedRemoveTutorMode()[i]);
			tutorMode = (TutoringMode) tutPlanForm.getTutoringPlan().getListTutoringMode().get(index);
			if (!dao.removeTutoringMode(tutPlanForm.getStudyUnit().getCode(),Short.parseShort(tutPlanForm.getAcadYear()),Short.parseShort(tutPlanForm.getSemester()),tutorMode ,tutPlanForm.getUserId())){
				
				errorNr=errorNr +1;
				if (errorStr.equalsIgnoreCase("")){
					errorStr=tutorMode.getTutorModeDesc();
				}else {
					errorStr=tutorMode.getTutorModeDesc() + ", " + tutorMode.getTutorModeDesc();
				}				 
			}
		}
		if (errorNr>0){
			if (errorNr==1){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
								"Error removing Tutoring mode: " + errorStr + " , because groups have already been assigned using this tutoring mode."));
			}else{
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
								"Error removing Tutoring modes: " + errorStr + ", because groups have already been assigned using this tutoring modes."));
			}			
			addErrors(request,messages);		
		} 
				
		TutoringPlan tutoringPlan = new TutoringPlan();
		tutoringPlan = dao.getTutoringPlan(tutPlanForm.getStudyUnit().getCode(),Short.parseShort(tutPlanForm.getAcadYear()),Short.parseShort(tutPlanForm.getSemester()));
		tutPlanForm.setTutoringPlan(tutoringPlan);
		tutPlanForm.setIndexSelectedRemoveTutorMode(new String[0]);
				
		return mapping.findForward("displayTutoringPlan");
			
	}
	
	public ActionForward addTutoringMode(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
		TutoringPlanForm tutPlanForm = (TutoringPlanForm) form;
		
		TutoringMode tutorMode = new TutoringMode();
		//initialise tutoring mode
		tutorMode.setAllocationCriteria("RANDOM");
		tutorMode.setTutorContact("EMAILADR");
		tutPlanForm.setTutoringMode(tutorMode);
		tutPlanForm.setEditMode("add");
				
		return mapping.findForward("displayTutoringMode");
			
	}
	
	public ActionForward displayAuditTrail(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
		TutoringPlanForm tutPlanForm = (TutoringPlanForm) form;
		
		List<AuditTrail> listAudit = new ArrayList<AuditTrail>();
		TutoringPlanDao dao = new TutoringPlanDao();
		
		listAudit = dao.getAuditTrail(tutPlanForm.getStudyUnit().getCode(),Short.parseShort(tutPlanForm.getAcadYear()),Short.parseShort(tutPlanForm.getSemester()));
		
		request.setAttribute("listAudit", listAudit);
				
		return mapping.findForward("displayAuditTrail");
			
	}
	
	public String displayTutoringPlan(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
		TutoringPlanForm tutPlanForm = (TutoringPlanForm) form;
		ActionMessages messages = new ActionMessages();
		
		if (tutPlanForm.getAcadYear()==null || tutPlanForm.getAcadYear().trim().equalsIgnoreCase("")){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"Academic Year is required."));
		} else {
			if (!isInteger(tutPlanForm.getAcadYear())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"Academic Year must be numeric."));
			}
		}
		if (tutPlanForm.getSemester()==null || tutPlanForm.getSemester().trim().equalsIgnoreCase("")){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"Semester is required."));
		}
		if (tutPlanForm.getInputStudyUnit()==null || tutPlanForm.getInputStudyUnit().trim().equalsIgnoreCase("")){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"Study Unit is required."));
		}else{
			ModuleDAO dao = new ModuleDAO();
			StudyUnit studyUnit = new StudyUnit();
			studyUnit = dao.getStudyUnit(tutPlanForm.getInputStudyUnit());			
			if (studyUnit.getEngLongDesc()==null){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"Study Unit not found."));
			}else{
				tutPlanForm.setStudyUnit(studyUnit);
				CoursePeriodLookup periodLookup = new CoursePeriodLookup();
				tutPlanForm.setSemesterType(periodLookup.getCourseTypeAsString(tutPlanForm.getSemester()));
				TutoringPlanDao tutDao = new TutoringPlanDao();
				if (!tutDao.isSunpdtExist(Short.parseShort(tutPlanForm.getAcadYear()), Short.parseShort(tutPlanForm.getSemester()), studyUnit.getCode())){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"Study Unit period details not found."));
				}
			}
			
		}
		
		if (!messages.isEmpty()) {
			addErrors(request,messages);
			return "inputTutoringPlan";
		}
		
		//check if user is authorised to update tutoring plan
		//update only to PRIML/SECDL or CADMN
		boolean updateAllowed = false;
		TutoringPlanDao dao = new TutoringPlanDao();
		updateAllowed = dao.isAuthorised(tutPlanForm.getUserId(),tutPlanForm.getStudyUnit().getCode(),Short.parseShort(tutPlanForm.getAcadYear()),Short.parseShort(tutPlanForm.getSemester()));
		if (updateAllowed==false){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"message.generalmessage",
					"You are not authorised to update the Tutoring Plan."));
			addMessages(request, messages);
		}
		
		//only allowed to update tutoring plan if no tutoring groups have been assigned to this academic year/semester and module
		if (updateAllowed==true && dao.tutoringStudentGroupsAssigned(tutPlanForm.getStudyUnit().getCode(),Short.parseShort(tutPlanForm.getAcadYear()),Short.parseShort(tutPlanForm.getSemester()))){
			updateAllowed=false;
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"message.generalmessage",
					"Updating Tutoring Plan not allowed, tutor-student-groups have already been assigned for this module and period."));
			addMessages(request, messages);
		}
		
		tutPlanForm.setUpdateAllowed(updateAllowed);
		TutoringPlan tutoringPlan = new TutoringPlan();
		tutoringPlan = dao.getTutoringPlan(tutPlanForm.getStudyUnit().getCode(),Short.parseShort(tutPlanForm.getAcadYear()),Short.parseShort(tutPlanForm.getSemester()));
		tutPlanForm.setTutoringPlan(tutoringPlan);
		
		return "displayTutoringPlan";
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
