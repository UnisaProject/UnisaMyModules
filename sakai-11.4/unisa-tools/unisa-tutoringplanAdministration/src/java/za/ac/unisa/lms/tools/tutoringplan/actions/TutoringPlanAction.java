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
import za.ac.unisa.lms.dao.general.UserDAO;
import za.ac.unisa.lms.domain.general.Person;
import za.ac.unisa.lms.tools.tutoringplan.dao.TutoringModeDAO;
import za.ac.unisa.lms.tools.tutoringplan.dao.TutoringPlanDao;
import za.ac.unisa.lms.tools.tutoringplan.forms.*;
import za.ac.unisa.lms.tools.tutoringplan.impl.StudyUnitImpl;
import za.ac.unisa.lms.tools.tutoringplan.impl.TutoringModeImpl;
import za.ac.unisa.lms.tools.tutoringplan.impl.TutoringPlan;
import za.ac.unisa.lms.tools.tutoringplan.impl.TutoringplanUtils;
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
			HttpServletResponse response)throws Exception{
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
			HttpServletResponse response){
			
		TutoringPlanForm tutPlanForm = (TutoringPlanForm) form;
			String nextPage="";
				try{
			if (tutPlanForm.getCurrentPage().equalsIgnoreCase("displayTutoringPlan")){
				nextPage="inputTutoringPlan";
			}
			if (tutPlanForm.getCurrentPage().equalsIgnoreCase("displayTutoringMode")){
				nextPage="displayTutoringPlan";
			}
			if (tutPlanForm.getCurrentPage().equalsIgnoreCase("displayAuditTrail")){
				nextPage="displayTutoringPlan";
			}
				}catch(Exception ex){
					nextPage="inputTutoringPlan";
				}
			
			return mapping.findForward(nextPage);
			
	}
	
	public ActionForward nextStep(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {
		        String nextPage="inputTutoringPlan";
		    
		          try{
			
		                TutoringPlanForm tutPlanForm = (TutoringPlanForm) form;
		                TutoringPlan tutoringPlan=new TutoringPlan();
		                ActionMessages messages = new ActionMessages();
		        		if (tutPlanForm.getCurrentPage().equalsIgnoreCase("inputTutoringPlan")){
				                List errMsgList=tutoringPlan.validateInput(tutPlanForm.getInputStudyUnit().toUpperCase(),tutPlanForm.getAcadYear(), tutPlanForm.getSemester());
					            if(!errMsgList.isEmpty()){
						              TutoringplanUtils.addErrorMessages(messages,errMsgList);
						              addErrors(request,messages);
					                  return mapping.findForward("inputTutoringPlan");
					            }
					            TutoringplanUtils.setAcademicYear(Short.parseShort(tutPlanForm.getAcadYear()));
					            TutoringplanUtils.setSemester(Short.parseShort(tutPlanForm.getSemester()));
					            TutoringplanUtils.setStudyUnit(tutPlanForm.getInputStudyUnit().toUpperCase());
					            TutoringplanUtils.setNetworkCode(tutPlanForm.getUserId());
					            nextPage = displayTutoringPlan(mapping,form,request,response);
			         }  
		         }catch(Exception ex){
			            nextPage="inputTutoringPlan";
		         }
	         return mapping.findForward(nextPage);
	}
	public ActionForward cancel(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
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
			HttpServletResponse response) throws Exception{
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
			HttpServletResponse response)  throws Exception{
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
	
	public ActionForward saveTutoringMode (
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		TutoringPlanForm tutPlanForm = (TutoringPlanForm) form;
		ActionMessages messages = new ActionMessages();
		TutoringMode tutoringMode=tutPlanForm.getTutoringMode();
        TutoringModeImpl tutoringModeImpl=new TutoringModeImpl(tutoringMode);
        List validationErrorMsgList=new ArrayList();
     	  validationErrorMsgList=tutoringModeImpl.validateInput(tutoringMode);
		          if (!validationErrorMsgList.isEmpty()) {
			            TutoringplanUtils.addErrorMessages(messages,validationErrorMsgList);
			            addErrors(request,messages);
			            return mapping.findForward("displayTutoringMode");
		}
       TutoringPlan  tutoringPlan=new TutoringPlan();
        String studyUnitCode=TutoringplanUtils.getStudyUnit();
        short academicYear=TutoringplanUtils.getAcademicYear();
        short semester=TutoringplanUtils.getSemester();
		tutoringPlan=tutoringPlan.getTutoringPlan(studyUnitCode,academicYear,semester);
		List tutoringModeList=tutoringPlan.getListTutoringMode();
		String editMode=tutPlanForm.getEditMode();
		if (editMode.equalsIgnoreCase("add")){
			    tutoringModeImpl.validateNewMode(tutoringModeList,tutoringMode,validationErrorMsgList);
		}
		if (editMode.equalsIgnoreCase("edit")){
			   tutoringModeImpl.validateEditedMode(tutoringModeList,tutoringMode,validationErrorMsgList);
		}	
		tutoringModeImpl.validateTutorMode(tutoringMode,validationErrorMsgList);	
		if (!validationErrorMsgList.isEmpty()) {
			    TutoringplanUtils.addErrorMessages(messages,validationErrorMsgList);
			    addErrors(request,messages);
			    return mapping.findForward("displayTutoringMode");
		}
		 tutoringModeImpl.saveTutoringMode(studyUnitCode, academicYear, semester,editMode);
		tutoringPlan =tutoringPlan.getTutoringPlan(studyUnitCode,academicYear, semester);
		tutPlanForm.setTutoringPlan(tutoringPlan);
		return mapping.findForward("displayTutoringPlan");
	}
   public ActionForward removeTutoringMode(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)  throws Exception{
		TutoringPlanForm tutPlanForm = (TutoringPlanForm) form;
		ActionMessages messages = new ActionMessages();
		
		//do validation
		String studyUnitCode=TutoringplanUtils.getStudyUnit();
	    short academicYear=TutoringplanUtils.getAcademicYear();
	    short semester=TutoringplanUtils.getSemester();
		TutoringPlan  tutoringPlan=new TutoringPlan();
		tutoringPlan=tutoringPlan.getTutoringPlan(studyUnitCode,academicYear, semester);
		TutoringModeImpl  tutoringModeImpl=new TutoringModeImpl(tutoringPlan);
		String[] selectedIndexList=tutPlanForm.getIndexSelectedRemoveTutorMode();
		String errMsg=tutoringModeImpl.validateSelectedIndex(selectedIndexList);
		if (!errMsg.equals("")) {
			 TutoringplanUtils.addErrorMessage(messages, errMsg);
			    addErrors(request,messages);
			return mapping.findForward("displayTutoringPlan");
		}
		 errMsg= tutoringModeImpl.removeTutoringMode(selectedIndexList, studyUnitCode, academicYear, semester);		
		if (!errMsg.equals("")) {
			    TutoringplanUtils.addErrorMessage(messages, errMsg);
		        addErrors(request,messages);
		        return mapping.findForward("displayTutoringPlan");
		}
		 tutoringPlan =tutoringPlan.getTutoringPlan(studyUnitCode,academicYear, semester);
		tutPlanForm.setTutoringPlan(tutoringPlan);
		tutPlanForm.setIndexSelectedRemoveTutorMode(new String[0]);
		return mapping.findForward("displayTutoringPlan");
			
	}
	
	public ActionForward addTutoringMode(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
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
			HttpServletResponse response)  throws Exception{
		            TutoringPlanForm tutPlanForm = (TutoringPlanForm) form;
		            List<AuditTrail> listAudit = new ArrayList<AuditTrail>();
		            TutoringModeDAO dao = new TutoringModeDAO();
		            String studyUnitCode=TutoringplanUtils.getStudyUnit();
		    	    short academicYear=TutoringplanUtils.getAcademicYear();
		    	    short semester=TutoringplanUtils.getSemester();
		    		listAudit = dao.getAuditTrail(studyUnitCode,academicYear,semester);
		            request.setAttribute("listAudit", listAudit);
	    mapping.findForward("inputTutoringPlan");
	
		return mapping.findForward("displayAuditTrail");
			
	}
	
	public String displayTutoringPlan(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)throws Exception{
		TutoringPlanForm tutPlanForm = (TutoringPlanForm) form;
		ActionMessages messages = new ActionMessages();
		TutoringPlan tutoringPlan=new TutoringPlan();
		 String studyUnitCode=TutoringplanUtils.getStudyUnit();
 	    short academicYear=TutoringplanUtils.getAcademicYear();
 	    short semester=TutoringplanUtils.getSemester();
 		StudyUnitImpl studyUnitImpl=new StudyUnitImpl();
		tutPlanForm.setStudyUnit(studyUnitImpl.getStudyUnit(studyUnitCode));
		CoursePeriodLookup periodLookup = new CoursePeriodLookup();
		tutPlanForm.setSemesterType(periodLookup.getCourseTypeAsString(tutPlanForm.getSemester()));
		TutoringPlanDao dao = new TutoringPlanDao();
		tutoringPlan = dao.getTutoringPlan(studyUnitCode,academicYear,semester);
		tutPlanForm.setTutoringPlan(tutoringPlan);
		return "displayTutoringPlan";
	
	}
}
