package za.ac.unisa.lms.tools.tutorstudentgrouping.actions;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Calendar;
import java.util.Date;

import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
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
import org.sakaiproject.event.api.UsageSessionService;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.tool.api.ToolManager;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserDirectoryService;

import za.ac.unisa.lms.dao.Gencod;
import za.ac.unisa.lms.dao.StudentSystemGeneralDAO;
import za.ac.unisa.lms.tools.tutorstudentgrouping.dao.TutorStudentGroupingDAO;
import za.ac.unisa.lms.tools.tutorstudentgrouping.forms.*;
import za.ac.unisa.lms.dao.general.ModuleDAO;
import za.ac.unisa.lms.dao.general.PersonnelDAO;
import za.ac.unisa.lms.dao.general.UserDAO;
import za.ac.unisa.lms.domain.general.*;
import za.ac.unisa.utils.CellPhoneVerification;
import za.ac.unisa.utils.CoursePeriodLookup;
import za.ac.unisa.utils.ProcessDrivenSMS;
import za.ac.unisa.lms.domain.processDrivenSms.*;

public class TutorStudentGroupingAction extends LookupDispatchAction {
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
		map.put("button.continue","nextStep");
		map.put("button.back", "prevStep");
		map.put("button.cancel", "cancel");
		map.put("button.uploadFile", "uploadFile");
		//map.put("button.uploadGroup", "uploadGroup");
		map.put("uploadGroup", "uploadGroup");
		map.put("button.viewInvalidRecords", "viewInvalidRecords");
		map.put("button.tutorGroupInfo", "selectNewGroupReassignStudent");
		//map.put("button.reassignStudent", "reassignStudent");
		map.put("reassignStudent", "reassignStudent");
		map.put("button.tutorGroups", "selectNewTutor");
		map.put("button.replace","confirmReplaceTutor");
		//map.put("button.replaceTutor","replaceTutor");
		map.put("replaceTutor","replaceTutor");
		map.put("button.switch","confirmSwitchTutor");
		//map.put("button.switchTutor","switchTutor");
		map.put("switchTutor","switchTutor");
			
		return map;
	}
	
	public ActionForward selectNewTutor(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
		TutorStudentGroupForm mainForm = (TutorStudentGroupForm) form;
		String nextPage="";
		
		
		if (mainForm.getCurrentPage().equalsIgnoreCase("inputReplaceTutor")){
			nextPage =selectNewTutorReplaceTutor(mapping,form,request,response);
		}
		if (mainForm.getCurrentPage().equalsIgnoreCase("inputSwitchTutor")){
			nextPage =nextPage =selectNewTutorSwitchTutor(mapping,form,request,response);
		}
		
		return mapping.findForward(nextPage);
	}	
	
	public ActionForward initial(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
		TutorStudentGroupForm mainForm = (TutorStudentGroupForm) form;
		ActionMessages messages = new ActionMessages();
				
		mainForm.setAcadYear("");		
		mainForm.setSemester("");		
		mainForm.setSelectedAction("");
		mainForm.setInputStudyUnit("");
		resetMainForm(mainForm);
		
		//Get user details
		mainForm.setUserId(null);
		User user = null;
		
		sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
	    userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
		Session currentSession = sessionManager.getCurrentSession();
		String userID  = currentSession.getUserId();
				
		if (userID != null) {
			user = userDirectoryService.getUser(userID);
			mainForm.setUserId(user.getEid().toUpperCase());
		}
		//assessmentCritForm.setNovellUserId("PRETOJ"); //debug
		
		Person person = new Person();
		UserDAO userdao = new UserDAO();
		person = userdao.getPerson(mainForm.getUserId());	
		
		if (person.getPersonnelNumber()==null){
			//user does not have access to this function 
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"User not found"));
			addErrors(request,messages);
			return mapping.findForward("errorPage");
		}		
		mainForm.setUser(person);		
		//Get semester list
		List list = new ArrayList();
		StudentSystemGeneralDAO dao = new StudentSystemGeneralDAO();
		for (int i=0; i < dao.getGenCodes((short)54,1).size(); i++) {
			list.add(i, (Gencod)(dao.getGenCodes((short)54,1).get(i)));
		}
		mainForm.setListSemester(list);	
		
		//Get action list
		list = new ArrayList();
		for (int i=0; i < dao.getGenCodes((short)187,3).size(); i++) {
			list.add(i, (Gencod)(dao.getGenCodes((short)187,3).get(i)));
		}
		mainForm.setListAction(list);	
		
		//Get tutoring mode list
		list = new ArrayList();
		for (int i=0; i < dao.getGenCodes((short)181,3).size(); i++) {
			list.add(i, (Gencod)(dao.getGenCodes((short)181,3).get(i)));
		}
		mainForm.setListTutoringMode(list);	
		
		return mapping.findForward("input");    
	}	
	
	public ActionForward cancel(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
			TutorStudentGroupForm mainForm = (TutorStudentGroupForm) form;
						
			resetMainForm(mainForm);
			return mapping.findForward("cancel");
			
	}
	
	
	public ActionForward prevStep(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
			TutorStudentGroupForm mainForm = (TutorStudentGroupForm) form;
			String nextPage="";
			
			if (mainForm.getCurrentPage().equalsIgnoreCase("inputUploadGroup")){
				nextPage="input";
			}
			if (mainForm.getCurrentPage().equalsIgnoreCase("verifyUploadGroup")){
				nextPage="inputUploadGroup";
			}
			if (mainForm.getCurrentPage().equalsIgnoreCase("viewInvalidRecords")){
				nextPage="verifyUploadGroup";
			}
			if (mainForm.getCurrentPage().equalsIgnoreCase("inputReassignStudent")){
				nextPage="input";
			}
			if (mainForm.getCurrentPage().equalsIgnoreCase("selectNewGroupReassignStudent")){
				nextPage="inputReassignStudent";
			}
			if (mainForm.getCurrentPage().equalsIgnoreCase("inputReplaceTutor")){
				nextPage="input";
			}
			if (mainForm.getCurrentPage().equalsIgnoreCase("selectNewTutorReplaceTutor")){
				nextPage="inputReplaceTutor";
			}
			if (mainForm.getCurrentPage().equalsIgnoreCase("confirmReplaceTutor")){
				nextPage="selectNewTutorReplaceTutor";
			}
			if (mainForm.getCurrentPage().equalsIgnoreCase("inputSwitchTutor")){
				nextPage="input";
			}
			if (mainForm.getCurrentPage().equalsIgnoreCase("selectNewTutorSwitchTutor")){
				nextPage="inputSwitchTutor";
			}
			if (mainForm.getCurrentPage().equalsIgnoreCase("confirmSwitchTutor")){
				nextPage="selectNewTutorSwitchTutor";
			}
			
			return mapping.findForward(nextPage);
			
	}
	
	public ActionForward nextStep(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
			TutorStudentGroupForm mainForm = (TutorStudentGroupForm) form;
			ActionMessages messages = new ActionMessages();
			
			String nextPage="input";					
			if("inputAction".equalsIgnoreCase(mainForm.getCurrentPage())){	
				if (isInputActionOk(mapping, form, request, response)==false){
					nextPage="input";
				}					
				else {
					if (mainForm.getSelectedAction().equalsIgnoreCase("UPLOAD")){				
						nextPage="inputUploadGroup";
					}
					if (mainForm.getSelectedAction().equalsIgnoreCase("REPLACE")){
						nextPage="inputReplaceTutor";
					}
					if (mainForm.getSelectedAction().equalsIgnoreCase("SWITCH")){
						nextPage="inputSwitchTutor";
					}
					if (mainForm.getSelectedAction().equalsIgnoreCase("REASSIGN")){
						nextPage="inputReassignStudent";
					}
				}
			}
			return mapping.findForward(nextPage);
			
	}
	
	private Boolean isInputActionOk(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		TutorStudentGroupForm mainForm = (TutorStudentGroupForm) form;
		ActionMessages messages = new ActionMessages();
		resetMainForm(mainForm);
		
		if (mainForm.getAcadYear()==null || mainForm.getAcadYear().trim().equalsIgnoreCase("")){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"Academic Year is required."));
		} else {
			if (!isInteger(mainForm.getAcadYear())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"Academic Year must be numeric"));
			}
		}
		if (mainForm.getSemester()==null || mainForm.getSemester().trim().equalsIgnoreCase("")){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"Semester is required."));
		}
		if (mainForm.getInputStudyUnit()==null || mainForm.getInputStudyUnit().trim().equalsIgnoreCase("")){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"Study Unit is required."));
		}		
		if (mainForm.getSelectedAction()==null || mainForm.getSelectedAction().trim().equalsIgnoreCase("")){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"Action is required."));
		}
		if (!messages.isEmpty()) {
			addErrors(request,messages);
			return false;
		}
		ModuleDAO daoMod = new ModuleDAO();
		StudyUnit studyUnit = new StudyUnit();
		studyUnit = daoMod.getStudyUnit(mainForm.getInputStudyUnit());			
		if (studyUnit.getEngLongDesc()==null){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"Study Unit not found."));
		}else{
			mainForm.setStudyUnit(studyUnit);
			TutorStudentGroupingDAO tutDao = new TutorStudentGroupingDAO();
			if (!tutDao.isSunpdtExist(Short.parseShort(mainForm.getAcadYear()), Short.parseShort(mainForm.getSemester()), studyUnit.getCode())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"The action is not allowed, the information on AIMS is not complete."));
			}				
			String rcCode = tutDao.getDepartmentRcCode(studyUnit.getDepartmentCode());
			if (rcCode==null || rcCode.equalsIgnoreCase("")){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"Rc-code not available for the sending of sms's, either the department linked to the study unit, is not in use or there is no rc-code linked to this department."));
			}else{
				mainForm.setRcCode(rcCode);
			}
		}
		if (!messages.isEmpty()) {
			addErrors(request,messages);
			return false;
		}
		if (mainForm.getSelectedAction().equalsIgnoreCase("UPLOAD") ||
				mainForm.getSelectedAction().equalsIgnoreCase("REPLACE") ||
				mainForm.getSelectedAction().equalsIgnoreCase("SWITCH") ||
				mainForm.getSelectedAction().equalsIgnoreCase("REASSIGN")){
			boolean authorised = false;
			TutorStudentGroupingDAO dao = new TutorStudentGroupingDAO();	
			authorised = dao.isAuthorised(mainForm.getUserId(), mainForm.getStudyUnit().getCode(), Short.parseShort(mainForm.getAcadYear()), Short.parseShort(mainForm.getSemester()));
			if (!authorised){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"You are not authorised to perform this action"));
							addErrors(request,messages);
							return false;
			}
			if (mainForm.getSemester().equalsIgnoreCase("2")){
				Regdat regdat = new Regdat();
				//Confirm with Clifford, on which year to read date? --- ask for examples
				regdat = dao.getRegDat(Short.parseShort(mainForm.getAcadYear()), Short.parseShort("1"), "NOSTMISS");
				if (regdat.getToDate()!=null){
					SimpleDateFormat formatter=new SimpleDateFormat("yyyyMMdd");
					String strRegdat =formatter.format(regdat.getToDate());
					Calendar currentDate = Calendar.getInstance();
					Date nowDate = currentDate.getTime();
					 if (!nowDate.after(regdat.getToDate())){
						 messages.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("message.generalmessage",
												"Allocation of groups for semester 2 is only allowed after " + strRegdat));
										addErrors(request,messages);
										return false;
				        }
				}else{
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"Date for the postponement of the allocation of groups for semester 2 has not yet been set."));
								addErrors(request,messages);
								return false;
				}				
			}
			
		}			
		//get Tutoring modes available for module		
		List availableTutorModesList = new ArrayList<TutoringMode>();
		TutorStudentGroupingDAO tutorDao = new TutorStudentGroupingDAO();
		availableTutorModesList = tutorDao.getAvailableTutorModeList(mainForm.getStudyUnit().getCode(), Short.parseShort(mainForm.getAcadYear()), Short.parseShort(mainForm.getSemester()));
		mainForm.setListAvailableTutorMode(availableTutorModesList);
		if (availableTutorModesList.size()==0){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"No Tutoring modes available for this module."));
		}else{			
			if (availableTutorModesList.size()==1){
				TutoringMode tutoringMode = new TutoringMode();
				tutoringMode = (TutoringMode)availableTutorModesList.get(0);
				mainForm.setSelectedTutoringMode(tutoringMode.getTutorMode());
				mainForm.setTutoringMode(tutoringMode);				
			}
		}
		String inclLectStaff = "N";
		for (int i=0; i < availableTutorModesList.size(); i++){
			TutoringMode tutoringMode = new TutoringMode();
			tutoringMode=(TutoringMode)availableTutorModesList.get(i);
			if (tutoringMode.getIncludeLectAsTutors().equalsIgnoreCase("Y")){
				inclLectStaff = "Y";
			}
		}
		
		TutorStudentGroupingDAO dao = new TutorStudentGroupingDAO();
		List tutorList = new ArrayList();
		tutorList = dao.getTutorList(mainForm.getStudyUnit().getCode(), Short.parseShort(mainForm.getAcadYear()), Short.parseShort(mainForm.getSemester()), inclLectStaff);
		mainForm.setListTutor(tutorList);		
		if (tutorList.size()==0){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"No Tutors available for this module."));
		}
			
		if (!messages.isEmpty()) {
			addErrors(request,messages);
			return false;
		}
		CoursePeriodLookup periodLookup = new CoursePeriodLookup();
		mainForm.setSemesterType(periodLookup.getCourseTypeAsString(mainForm.getSemester()));
		
		return true;
	}
	
	public ActionForward selectNewGroupReassignStudent(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
			TutorStudentGroupForm mainForm = (TutorStudentGroupForm) form;
			ActionMessages messages = new ActionMessages();
			TutorStudentGroupingDAO dao = new TutorStudentGroupingDAO();
			
			if (mainForm.getStudentNr()==null || mainForm.getStudentNr().trim().equalsIgnoreCase("")){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"Student number is required."));
			} else {
				if (!isInteger(mainForm.getStudentNr())){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"Student number must be numeric"));
				}else{
					//Check if valid student
					//Check student is registered for module
					//Get student detail
					//Get student grouping detail
					Student stu = new Student();
					stu = dao.getStudent(Integer.parseInt(mainForm.getStudentNr()));
					if (stu.getNumber()==null){
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage",
											"Invalid student number, student not found."));
					}else{
						//check student is registered
						mainForm.setStudent(stu);
						boolean studentRegistered = false;
						studentRegistered = dao.isStudentRegisteredForModule(mainForm.getStudyUnit().getCode(),Short.parseShort(mainForm.getAcadYear()), Short.parseShort(mainForm.getSemester()), Integer.parseInt(mainForm.getStudentNr()));
						if (!studentRegistered){
							messages.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("message.generalmessage",
												"Student is not registered for this module."));
						}
					}
					
				}
			}
			if (!messages.isEmpty()) {
				addErrors(request,messages);
			 	return mapping.findForward("inputReassignStudent");			 	
			}
			
			//Get student physical address
			Address address = new Address();
			address = dao.getAddress(mainForm.getStudent().getNumber(), 1, 1);
			mainForm.getStudent().setPhysicalAddress(address);
			String studentAddress = formatAddress(address);
			request.setAttribute("studentAddress", studentAddress);			
			
			//Get student current group and tutor
			TutorStudentGroup studentGroup = new TutorStudentGroup();
			studentGroup = dao.getStudentGroup(mainForm.getStudyUnit().getCode(),Short.parseShort(mainForm.getAcadYear()), Short.parseShort(mainForm.getSemester()), Integer.parseInt(mainForm.getStudentNr()));
			mainForm.setStudentGroup(studentGroup);
			
			//Get available groups for this Module and Tutoring Mode
			List tutorStudentGroupList = new ArrayList<TutorStudentGroup>();
			tutorStudentGroupList = dao.getAvailableTutorStudentGroups(mainForm.getStudyUnit().getCode(),Short.parseShort(mainForm.getAcadYear()), Short.parseShort(mainForm.getSemester()));
			for(int i =0; i < tutorStudentGroupList.size(); i++){
				TutorStudentGroup group = new TutorStudentGroup();
				group = (TutorStudentGroup)tutorStudentGroupList.get(i);
				if (group.getTotalStudentsAllocated() >= group.getTutoringMode().getTutorStuRatio()){
					tutorStudentGroupList.remove(i);
					i = i - 1;
				}					
			}
			if (studentGroup!=null && studentGroup.getGroupNumber()!=0){				
				//Remove student group from available group list
				for (int i=0; i < tutorStudentGroupList.size(); i++){
					TutorStudentGroup group = new TutorStudentGroup();
					group = (TutorStudentGroup)tutorStudentGroupList.get(i);
					if (group.getGroupNumber()== studentGroup.getGroupNumber() && group.getTutoringMode().getTutorMode().equalsIgnoreCase(studentGroup.getTutoringMode().getTutorMode())){
						tutorStudentGroupList.remove(i);
						i = i - 1;
					}
				}
			}
			
			mainForm.setListAvailableTutorGroup(tutorStudentGroupList);
			mainForm.setSelectedGroup(0);
			return mapping.findForward("selectNewGroupReassignStudent");
			
	}
	
	public String selectNewTutorSwitchTutor(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
			TutorStudentGroupForm mainForm = (TutorStudentGroupForm) form;
			ActionMessages messages = new ActionMessages();
			TutorStudentGroupingDAO dao = new TutorStudentGroupingDAO();		
			Person person= new Person();
			
			if (mainForm.getTutorStaffNumber()==null || mainForm.getTutorStaffNumber().trim().equalsIgnoreCase("")){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"Tutor staff number is required."));
			} else {	
				//check staff number numeric
				if (!isInteger(mainForm.getTutorStaffNumber())){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"Tutor staff number must be numeric."));
				}else {
					//check staff record exists
					PersonnelDAO personDao = new PersonnelDAO();				
					person= personDao.getPerson(Integer.parseInt(mainForm.getTutorStaffNumber().trim()));
					if (person.getPersonnelNumber()==null || person.getPersonnelNumber().equalsIgnoreCase("") 
							|| person.getPersonnelNumber().equalsIgnoreCase("0")){
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage",
											"Invalid staff number, staff record does not exists."));
					}
				}
				
			}
			if (mainForm.getTutorNetworkCode()==null || mainForm.getTutorNetworkCode().trim().equalsIgnoreCase("")){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"Tutor network code is required."));
			} else {
				if (person.getNovellUserId()!=null && 
						!person.getNovellUserId().trim().equalsIgnoreCase(mainForm.getTutorNetworkCode())){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"Invalid network code, for staff number entered."));
				}
			}
			if (messages.isEmpty()){
				Tutor tutor = new Tutor();
				tutor.setPerson(person);
				mainForm.setTutor(tutor);
				List<TutorStudentGroup> tutorGroupList = new ArrayList<TutorStudentGroup>();
				tutorGroupList=dao.getGroupsAllocatedToTutor(mainForm.getStudyUnit().getCode(),Short.parseShort(mainForm.getAcadYear()),Short.parseShort(mainForm.getSemester()),tutor.getPerson());
				mainForm.setListTutorStudentGroup(tutorGroupList);
				if (tutorGroupList.size()==0){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"No student groups are located to this tutor."));
				}
				tutorGroupList = new ArrayList<TutorStudentGroup>();
				tutorGroupList = dao.getAvailableTutorStudentGroups(mainForm.getStudyUnit().getCode(),Short.parseShort(mainForm.getAcadYear()), Short.parseShort(mainForm.getSemester()));
				//remove from list group with same as tutor
				//remove from list if tutoring mode but different				
				for (int i=0; i < tutorGroupList.size(); i++){
					if (mainForm.getTutor().getPerson().getPersonnelNumber().equalsIgnoreCase(((TutorStudentGroup)tutorGroupList.get(i)).getTutor().getPersonnelNumber())){
						tutorGroupList.remove(i);
						i= i - 1;
					}else{
						if (((TutorStudentGroup)tutorGroupList.get(i)).getTutoringMode()!=null && ((TutorStudentGroup)tutorGroupList.get(i)).getTutoringMode().getTutorMode()!=null && !((TutorStudentGroup)tutorGroupList.get(i)).getTutoringMode().getTutorMode().equalsIgnoreCase("")){
							boolean modeFound=false;
							for (int j=0; j < mainForm.getListTutorStudentGroup().size(); j++){
								if (((TutorStudentGroup)tutorGroupList.get(i)).getTutoringMode().getTutorMode().equalsIgnoreCase(((TutorStudentGroup)mainForm.getListTutorStudentGroup().get(j)).getTutoringMode().getTutorMode())){
									modeFound=true;
								}
							}
							if (modeFound==false){
								tutorGroupList.remove(i);
								i = i - 1;
							}							
						}
					}
				}	
				if (tutorGroupList.size()==0){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"No other groups available to switch tutor with."));
				}
				mainForm.setListAvailableTutorGroup(tutorGroupList);
			}
			
		
			if (!messages.isEmpty()){
				addErrors(request,messages);
			 	return "inputSwitchTutor";
			}
			
			mainForm.setIndexNrSelectedGroups(null);
			mainForm.setSelectedTutor(null);
			
			return "selectNewTutorSwitchTutor";
			
	}
	
	public ActionForward confirmSwitchTutor(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
			TutorStudentGroupForm mainForm = (TutorStudentGroupForm) form;
			ActionMessages messages = new ActionMessages();
			SwitchGroupTutor switchGroup = new SwitchGroupTutor();
			
			if (mainForm.getIndexNrselectedTutorGroupSwitch1()==null ||
					mainForm.getIndexNrselectedTutorGroupSwitch1().equalsIgnoreCase("")){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"Please select a group for which you want to switch the tutor."));
			}else{
				switchGroup.setTutorStudentGroup1((TutorStudentGroup)mainForm.getListTutorStudentGroup().get(Integer.parseInt(mainForm.getIndexNrselectedTutorGroupSwitch1())));
			}
			if (mainForm.getIndexNrselectedTutorGroupSwitch2()==null ||
					mainForm.getIndexNrselectedTutorGroupSwitch2().equalsIgnoreCase("")){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"Please select a group with whom you want to switch the tutor."));
			}else{
				switchGroup.setTutorStudentGroup2((TutorStudentGroup)mainForm.getListAvailableTutorGroup().get(Integer.parseInt(mainForm.getIndexNrselectedTutorGroupSwitch2())));
			}
			
			if (!switchGroup.getTutorStudentGroup1().getTutoringMode().getTutorMode().equalsIgnoreCase(switchGroup.getTutorStudentGroup2().getTutoringMode().getTutorMode())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"Switch of tutors between groups with different Tutoring modes is not allowed."));
			}
			
		
			if (!messages.isEmpty()){
				addErrors(request,messages);
			 	return mapping.findForward("selectNewTutorSwitchTutor");
			}
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"Are you sure you want to switch the tutors of these two groups?"));
			addMessages(request, messages);
			
			mainForm.setSwitchGroup(switchGroup);
			
			return mapping.findForward("confirmSwitchTutor");
			
	}
	
	public ActionForward switchTutor(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
			TutorStudentGroupForm mainForm = (TutorStudentGroupForm) form;
			ActionMessages messages = new ActionMessages();
			TutorStudentGroupingDAO dao = new TutorStudentGroupingDAO();
			
			dao.switchTutor(mainForm.getStudyUnit().getCode(), 
					Short.parseShort(mainForm.getAcadYear()), 
					Short.parseShort(mainForm.getSemester()), 
					mainForm.getSwitchGroup(),mainForm.getUserId());
			
			SmsRequest smsRequest = new SmsRequest();
			SmsRequestResult smsResult = new SmsRequestResult();		
			smsRequest.setMessage("");
			smsRequest.setRcCode(mainForm.getRcCode());
			smsRequest.setReasonCodeGC27("18");
			smsRequest.setProgramName("TUTSTULINK:MYUNISA");
			smsRequest.setPersNo(Integer.parseInt(mainForm.getUser().getPersonnelNumber()));
			smsRequest.setCriteria("");	
			
			//send email and sms to each student in group 1 informing him/her of new tutor
			List<Integer> studentList = new ArrayList<Integer>();
			studentList = dao.getStudentsInGroup(Short.parseShort(mainForm.getAcadYear()),Short.parseShort(mainForm.getSemester()),mainForm.getStudyUnit().getCode(),mainForm.getSwitchGroup().getTutorStudentGroup1());
			List<Student> groupStudentList = new ArrayList<Student>(); 
			List<SmsRecipient> listSms = new ArrayList<SmsRecipient>();			
			for (int j=0; j < studentList.size(); j++ ){
				int studentNr=0;
				studentNr = (Integer)studentList.get(j);
				Student student = new Student();
				student = dao.getStudent(studentNr);
				if (student.getContactInfo()!=null && student.getContactInfo().getEmailAddress()!=null && !student.getContactInfo().getEmailAddress().trim().equalsIgnoreCase("")){
					sendNewTutorNotificationToStudent(mainForm.getStudyUnit().getCode(), mainForm.getAcadYear(), Short.parseShort(mainForm.getSemester()), mainForm.getSemesterType(), mainForm.getSwitchGroup().getTutorStudentGroup1().getTutoringMode(), mainForm.getSwitchGroup().getTutorStudentGroup2().getTutor(), mainForm.getSwitchGroup().getTutorStudentGroup1().getGroupNumber(), student);						
				}
				groupStudentList.add(student);
				SmsRecipient smsIn = new SmsRecipient();
				smsIn.setStudentNr(studentNr);
				String modeAbbr = getTutorModeAbbreviation(mainForm.getSwitchGroup().getTutorStudentGroup1().getTutoringMode().getTutorMode());
				String message = "UNISA Group Info for " + mainForm.getStudyUnit().getCode() + "-" + mainForm.getAcadYear().substring(2, 4) + "-" + mainForm.getSemesterType() + ". Stu# " + student.getNumber() + ". New Tutor assigned to Group " + mainForm.getSwitchGroup().getTutorStudentGroup1().getGroupNumber() + modeAbbr + ". " + mainForm.getSwitchGroup().getTutorStudentGroup1().getTutoringMode().getTutorModeDesc() + ": " + mainForm.getSwitchGroup().getTutorStudentGroup2().getTutor().getName() + ". Visit group site on myUnisa" ;
				if (message.length()> 160){
					message = "UNISA Group Info for " + mainForm.getStudyUnit().getCode() + "-" + mainForm.getAcadYear().substring(2, 4) + "-" + mainForm.getSemesterType() + ". Stu# " + student.getNumber() + ". New Tutor assigned to Group " + mainForm.getSwitchGroup().getTutorStudentGroup1().getGroupNumber() + modeAbbr + ". " + mainForm.getSwitchGroup().getTutorStudentGroup1().getTutoringMode().getTutorModeDesc() + ": " + mainForm.getSwitchGroup().getTutorStudentGroup2().getTutor().getName() + "." ;
				}
				smsIn.setMessage(message);
				listSms.add(smsIn);					
			}
			
			//send email to new tutor of group 1
			sendUpdatedGroupAllocationNotificationToTutor(mainForm.getStudyUnit().getCode(), mainForm.getAcadYear(), Short.parseShort(mainForm.getSemester()),  mainForm.getSemesterType(), groupStudentList, mainForm.getSwitchGroup().getTutorStudentGroup1().getTutoringMode(),mainForm.getSwitchGroup().getTutorStudentGroup2().getTutor(), mainForm.getSwitchGroup().getTutorStudentGroup1().getGroupNumber());
            //send a sms to all students with valid cellphone numbers.
			smsRequest.setListRecipient(listSms);
			ProcessDrivenSMS processDrivenSms = new ProcessDrivenSMS();
			processDrivenSms.sendSMSRequest(smsRequest);	
			
			//send email and sms to each student in group 1 informing him/her of new tutor
			studentList = new ArrayList<Integer>();
			studentList = dao.getStudentsInGroup(Short.parseShort(mainForm.getAcadYear()),Short.parseShort(mainForm.getSemester()),mainForm.getStudyUnit().getCode(),mainForm.getSwitchGroup().getTutorStudentGroup2());
			groupStudentList = new ArrayList<Student>(); 
			listSms = new ArrayList<SmsRecipient>();			
			for (int j=0; j < studentList.size(); j++ ){
				int studentNr=0;
				studentNr = (Integer)studentList.get(j);
				Student student = new Student();
				student = dao.getStudent(studentNr);
				if (student.getContactInfo()!=null && student.getContactInfo().getEmailAddress()!=null && !student.getContactInfo().getEmailAddress().trim().equalsIgnoreCase("")){
					sendNewTutorNotificationToStudent(mainForm.getStudyUnit().getCode(), mainForm.getAcadYear(), Short.parseShort(mainForm.getSemester()), mainForm.getSemesterType(), mainForm.getSwitchGroup().getTutorStudentGroup2().getTutoringMode(), mainForm.getSwitchGroup().getTutorStudentGroup1().getTutor(), mainForm.getSwitchGroup().getTutorStudentGroup2().getGroupNumber(), student);						
				}
				groupStudentList.add(student);
				SmsRecipient smsIn = new SmsRecipient();
				smsIn.setStudentNr(studentNr);
				String modeAbbr = getTutorModeAbbreviation(mainForm.getSwitchGroup().getTutorStudentGroup2().getTutoringMode().getTutorMode());
				String message = "UNISA Group Info for " + mainForm.getStudyUnit().getCode() + "-" + mainForm.getAcadYear().substring(2, 4) + "-" + mainForm.getSemesterType() + ". Stu# " + student.getNumber() + ". New Tutor assigned to Group " + mainForm.getSwitchGroup().getTutorStudentGroup2().getGroupNumber() + modeAbbr + ". " + mainForm.getSwitchGroup().getTutorStudentGroup2().getTutoringMode().getTutorModeDesc() + ": " + mainForm.getSwitchGroup().getTutorStudentGroup1().getTutor().getName() + ". Visit group site on myUnisa" ;
				if (message.length()> 160){
					message = "UNISA Group Info for " + mainForm.getStudyUnit().getCode() + "-" + mainForm.getAcadYear().substring(2, 4) + "-" + mainForm.getSemesterType() + ". Stu# " + student.getNumber() + ". New Tutor assigned to Group " + mainForm.getSwitchGroup().getTutorStudentGroup2().getGroupNumber() + modeAbbr + ". " + mainForm.getSwitchGroup().getTutorStudentGroup2().getTutoringMode().getTutorModeDesc() + ": " + mainForm.getSwitchGroup().getTutorStudentGroup1().getTutor().getName() + ".";
				}
				smsIn.setMessage(message);
				listSms.add(smsIn);					
			}
			//send email to new tutor of group 2
			sendUpdatedGroupAllocationNotificationToTutor(mainForm.getStudyUnit().getCode(), mainForm.getAcadYear(), Short.parseShort(mainForm.getSemester()),  mainForm.getSemesterType(), groupStudentList, mainForm.getSwitchGroup().getTutorStudentGroup2().getTutoringMode(),mainForm.getSwitchGroup().getTutorStudentGroup1().getTutor(), mainForm.getSwitchGroup().getTutorStudentGroup2().getGroupNumber());
            //send a sms to all students with valid cellphone numbers.
			smsRequest.setListRecipient(listSms);
			processDrivenSms = new ProcessDrivenSMS();
			processDrivenSms.sendSMSRequest(smsRequest);
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"Tutors of selected groups have been successfully switched."));
			addMessages(request, messages);
			
			return mapping.findForward("input");
			
	}
	
	public String selectNewTutorReplaceTutor(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
			TutorStudentGroupForm mainForm = (TutorStudentGroupForm) form;
			ActionMessages messages = new ActionMessages();
			TutorStudentGroupingDAO dao = new TutorStudentGroupingDAO();		
			Person person= new Person();
			
			if (mainForm.getTutorStaffNumber()==null || mainForm.getTutorStaffNumber().trim().equalsIgnoreCase("")){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"Tutor staff number is required."));
			} else {
				if (!isInteger(mainForm.getTutorStaffNumber())){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"Tutor staff number must be numeric."));
				}else{
					PersonnelDAO personDao = new PersonnelDAO();				
					person= personDao.getPerson(Integer.parseInt(mainForm.getTutorStaffNumber().trim()));
					if (person.getPersonnelNumber()==null || person.getPersonnelNumber().equalsIgnoreCase("") 
							|| person.getPersonnelNumber().equalsIgnoreCase("0")){
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage",
											"Invalid staff number, staff record does not exists."));
					}
				}				
			}
			if (mainForm.getTutorNetworkCode()==null || mainForm.getTutorNetworkCode().trim().equalsIgnoreCase("")){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"Tutor network code is required."));
			} else {
				if (person.getNovellUserId()!=null && 
						!person.getNovellUserId().trim().equalsIgnoreCase(mainForm.getTutorNetworkCode())){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"Invalid network code, for staff number entered."));
				}
			}
			if (messages.isEmpty()){
				Tutor tutor = new Tutor();
				tutor.setPerson(person);
				mainForm.setTutor(tutor);
				List<TutorStudentGroup> tutorGroupList = new ArrayList<TutorStudentGroup>();
				tutorGroupList=dao.getGroupsAllocatedToTutor(mainForm.getStudyUnit().getCode(),Short.parseShort(mainForm.getAcadYear()),Short.parseShort(mainForm.getSemester()),tutor.getPerson());
				mainForm.setListTutorStudentGroup(tutorGroupList);
				if (tutorGroupList.size()==0){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"No student groups are located to this tutor."));
				}
				String inclLectStaff = "N";
				for (int j=0; j < tutorGroupList.size(); j++){
					TutorStudentGroup group = new TutorStudentGroup();
					group=(TutorStudentGroup)tutorGroupList.get(j);
					if (group.getTutoringMode().getIncludeLectAsTutors().equalsIgnoreCase("Y")){
						inclLectStaff = "Y";
					}
				}	
					
				List<Tutor> tempList = new ArrayList<Tutor>();
				tempList=dao.getTutorList(mainForm.getStudyUnit().getCode(),Short.parseShort(mainForm.getAcadYear()),Short.parseShort(mainForm.getSemester()), inclLectStaff);
				for (int i=0; i < tempList.size(); i++){
					if (mainForm.getTutor().getPerson().getPersonnelNumber().equalsIgnoreCase(((Tutor)tempList.get(i)).getPerson().getPersonnelNumber())){
						tempList.remove(i);
						i = i - 1;
					}
				}
				mainForm.setListTutor(tempList);
				if (tempList.size()==0){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"No tutors are available for this module and period to replace this tutor with."));
				}
					
				}
		
			if (!messages.isEmpty()){
				addErrors(request,messages);
			 	return "inputReplaceTutor";
			}
			
			mainForm.setIndexNrSelectedGroups(null);
			mainForm.setSelectedTutor(null);
			
			return "selectNewTutorReplaceTutor";
			
	}
	
	public ActionForward confirmReplaceTutor(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
			TutorStudentGroupForm mainForm = (TutorStudentGroupForm) form;
			ActionMessages messages = new ActionMessages();
			TutorStudentGroupingDAO dao = new TutorStudentGroupingDAO();
			String tutoringMode="";
			List<TutorStudentGroup> selectedTutorGroupList = new ArrayList<TutorStudentGroup>();
			Tutor newTutor = new Tutor();
			
			if (mainForm.getIndexNrSelectedGroups()==null ||
					mainForm.getIndexNrSelectedGroups().length==0){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"Please select one or more groups for which you want to replace the tutor."));
			}else{
				for(int i=0; i < mainForm.getIndexNrSelectedGroups().length; i++){
					selectedTutorGroupList.add((TutorStudentGroup)mainForm.getListTutorStudentGroup().get(Integer.parseInt(mainForm.getIndexNrSelectedGroups()[i])));
				}
				tutoringMode = ((TutorStudentGroup)selectedTutorGroupList.get(0)).getTutoringMode().getTutorMode();
				for (int i=0; i < selectedTutorGroupList.size(); i++){
					if (!tutoringMode.equalsIgnoreCase(((TutorStudentGroup)selectedTutorGroupList.get(0)).getTutoringMode().getTutorMode())){
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage",
											"Groups selected for tutor replacement must be of the same tutoring Mode"));
					}
				}
			}
			if (mainForm.getSelectedTutor()==null || mainForm.getSelectedTutor().trim().equalsIgnoreCase("")){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"Please select a tutor to replace the current one with."));
			}else{
				for (int i =0; i < mainForm.getListTutor().size(); i++){
					newTutor = (Tutor)mainForm.getListTutor().get(i);
					if (mainForm.getSelectedTutor().equalsIgnoreCase(newTutor.getPerson().getPersonnelNumber())){
						i = mainForm.getListTutor().size();
					}
				}
				if (newTutor.getRole().equalsIgnoreCase("TUTOR")){
					boolean allocatedToDifferentModule = dao.tutorAllocatedToDifferentModule(Short.parseShort(mainForm.getAcadYear()), 
							Short.parseShort(mainForm.getSemester()), mainForm.getStudyUnit().getCode(), newTutor.getPerson());
					if (allocatedToDifferentModule==true){
						messages.add(ActionMessages.GLOBAL_MESSAGE, 
								new ActionMessage("message.generalmessage", 
									"Please select another tutor, tutor has already been allocated to a different module for the same period, tutor only allowed to be a tutor for one module during a period. Tutor also cannot tutor a semester and year module simultaneously."));
					}		
				}	
				if (!tutoringMode.equalsIgnoreCase("") && !tutoringMode.equalsIgnoreCase(newTutor.getTutoringMode())&& !newTutor.getTutoringMode().equalsIgnoreCase("")){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"New tutor selected have another group with a different tutoring mode assigned to it, tutor can only tutor in one mode."));
				}else{
					TutoringMode tutorMode = new TutoringMode();
					tutorMode=dao.getTutoringMode(mainForm.getStudyUnit().getCode(), Short.parseShort(mainForm.getAcadYear()), Short.parseShort(mainForm.getSemester()), tutoringMode);
					int numberGroups=newTutor.getNumberGroupsAllocated() + mainForm.getIndexNrSelectedGroups().length;
					if (numberGroups > tutorMode.getMaxGroupsPerTutor()){
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage",
											"After assigning the selected groups to the selected tutor, the tutor will have more than the allowed number of groups. Allowed number of groups: " + tutorMode.getMaxGroupsPerTutor()));
					}
					if (tutorMode.getTutorContact().equalsIgnoreCase("EMAILADR")){
						//Check if tutor's email address available as contact detail to students.
						if (newTutor.getPerson().getEmailAddress()==null || newTutor.getPerson().getEmailAddress().trim().equalsIgnoreCase("")){
							messages.add(ActionMessages.GLOBAL_MESSAGE, 
									new ActionMessage("message.generalmessage", 
										"Tutor contact details not available, no email address for tutor on the system, to make available to students."));
						}
					}
					if (tutorMode.getTutorContact().equalsIgnoreCase("CELLNR")){
						boolean validCellNumber=true;
						if (newTutor.getPerson().getContactNumber()!=null && !newTutor.getPerson().getContactNumber().trim().equalsIgnoreCase("")){
							CellPhoneVerification verifyCell = new CellPhoneVerification();
							validCellNumber = verifyCell.isCellNumber(newTutor.getPerson().getContactNumber());
							if (validCellNumber){
								if (verifyCell.isSaCellNumber(newTutor.getPerson().getContactNumber())){
									if (!verifyCell.validSaCellNumber(newTutor.getPerson().getContactNumber())){
										validCellNumber=false;
									}
								}
							}
							if (!validCellNumber){
								messages.add(ActionMessages.GLOBAL_MESSAGE, 
										new ActionMessage("message.generalmessage", 
											"Tutor contact details not available, no valid cell phone number for tutor on the system, to make available to students."));
							}
						}else{
							messages.add(ActionMessages.GLOBAL_MESSAGE, 
									new ActionMessage("message.generalmessage", 
										"Tutor contact details not available, no valid cell phone number for tutor on the system, to make available to students."));
						}
					}
				}
			}	
			
		
			if (!messages.isEmpty()){
				addErrors(request,messages);
			 	return mapping.findForward("selectNewTutorReplaceTutor");
			}
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"Are you sure you want to assign a new tutor to these groups?"));
			addMessages(request, messages);
			List<ChangeGroupTutor> changeGroupTutorList = new ArrayList<ChangeGroupTutor>();
			for (int i=0; i < selectedTutorGroupList.size(); i++){
				ChangeGroupTutor group = new ChangeGroupTutor();
				group.setNewTutor(newTutor);
				group.setTutorStudentGroup(((TutorStudentGroup)selectedTutorGroupList.get(i)));
				changeGroupTutorList.add(group);
			}
			mainForm.setListChangeGroupTutor(changeGroupTutorList);
			
			return mapping.findForward("confirmReplaceTutor");
			
	}
	
	public ActionForward replaceTutor(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
			TutorStudentGroupForm mainForm = (TutorStudentGroupForm) form;
			ActionMessages messages = new ActionMessages();
			TutorStudentGroupingDAO dao = new TutorStudentGroupingDAO();

			dao.replaceTutor(mainForm.getStudyUnit().getCode(), 
					Short.parseShort(mainForm.getAcadYear()), 
					Short.parseShort(mainForm.getSemester()), 
					mainForm.getListChangeGroupTutor(),mainForm.getUserId());	
			
			SmsRequest smsRequest = new SmsRequest();
			SmsRequestResult smsResult = new SmsRequestResult();		
			smsRequest.setMessage("");
			smsRequest.setRcCode(mainForm.getRcCode());
			smsRequest.setReasonCodeGC27("18");
			smsRequest.setProgramName("TUTSTULINK:MYUNISA");
			smsRequest.setPersNo(Integer.parseInt(mainForm.getUser().getPersonnelNumber()));
			smsRequest.setCriteria("");			
			
			for (int i=0; i < mainForm.getListChangeGroupTutor().size(); i++){
				ChangeGroupTutor changeGroup = new ChangeGroupTutor();
				changeGroup = (ChangeGroupTutor)mainForm.getListChangeGroupTutor().get(i);
				List<Integer> studentList = new ArrayList<Integer>();
				studentList = dao.getStudentsInGroup(Short.parseShort(mainForm.getAcadYear()),Short.parseShort(mainForm.getSemester()),mainForm.getStudyUnit().getCode(),changeGroup.getTutorStudentGroup());
				List<Student> groupStudentList = new ArrayList<Student>(); 
				List<SmsRecipient> listSms = new ArrayList<SmsRecipient>();
				//send email to each student informing him/her of new tutor
				for (int j=0; j < studentList.size(); j++ ){
					int studentNr=0;
					studentNr = (Integer)studentList.get(j);
					Student student = new Student();
					student = dao.getStudent(studentNr);
					if (student.getContactInfo()!=null && student.getContactInfo().getEmailAddress()!=null && !student.getContactInfo().getEmailAddress().trim().equalsIgnoreCase("")){
						sendNewTutorNotificationToStudent(mainForm.getStudyUnit().getCode(), mainForm.getAcadYear(), Short.parseShort(mainForm.getSemester()), mainForm.getSemesterType(), changeGroup.getTutorStudentGroup().getTutoringMode(), changeGroup.getNewTutor().getPerson(), changeGroup.getTutorStudentGroup().getGroupNumber(), student);						
					}
					groupStudentList.add(student);
					SmsRecipient smsIn = new SmsRecipient();
					smsIn.setStudentNr(studentNr);
					String modeAbbr = getTutorModeAbbreviation(changeGroup.getTutorStudentGroup().getTutoringMode().getTutorMode());
					String message = "UNISA Group Info for " + mainForm.getStudyUnit().getCode() + "-" + mainForm.getAcadYear().substring(2, 4) + "-" + mainForm.getSemesterType() + ". Stu# " + student.getNumber() + ". New Tutor assigned to Group " + changeGroup.getTutorStudentGroup().getGroupNumber() + modeAbbr + ". " + changeGroup.getTutorStudentGroup().getTutoringMode().getTutorModeDesc() + ": " + changeGroup.getNewTutor().getPerson().getName() + ". Visit group site on myUnisa" ;
					if (message.length()> 160){
						message = "UNISA Group Info for " + mainForm.getStudyUnit().getCode() + "-" + mainForm.getAcadYear().substring(2, 4) + "-" + mainForm.getSemesterType() + ". Stu# " + student.getNumber() + ". New Tutor assigned to Group " + changeGroup.getTutorStudentGroup().getGroupNumber() + modeAbbr + ". " + changeGroup.getTutorStudentGroup().getTutoringMode().getTutorModeDesc() + ": " + changeGroup.getNewTutor().getPerson().getName() + ".";
					}
					smsIn.setMessage(message);
					listSms.add(smsIn);					
				}
				//send email to new tutor
				sendUpdatedGroupAllocationNotificationToTutor(mainForm.getStudyUnit().getCode(), mainForm.getAcadYear(), Short.parseShort(mainForm.getSemester()),  mainForm.getSemesterType(), groupStudentList, changeGroup.getTutorStudentGroup().getTutoringMode(),changeGroup.getNewTutor().getPerson(), changeGroup.getTutorStudentGroup().getGroupNumber());
	            //send a sms to all students with valid cellphone numbers.
				smsRequest.setListRecipient(listSms);
				ProcessDrivenSMS processDrivenSms = new ProcessDrivenSMS();
				processDrivenSms.sendSMSRequest(smsRequest);				
			}
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"Tutor group(s) has/have been successfully allocated to the new selected tutor."));
			addMessages(request, messages);
			
			return mapping.findForward("input");		
	}
	
	
	public ActionForward reassignStudent(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
			TutorStudentGroupForm mainForm = (TutorStudentGroupForm) form;
			ActionMessages messages = new ActionMessages();
			TutorStudentGroupingDAO dao = new TutorStudentGroupingDAO();
			
			if (mainForm.getSelectedGroup()==0){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"Please select a new Group to assign student to."));
			} 
			if (!messages.isEmpty()) {
				addErrors(request,messages);
				String studentAddress = formatAddress(mainForm.getStudent().getPhysicalAddress());
				request.setAttribute("studentAddress", studentAddress);		
			 	return mapping.findForward("selectNewGroupReassignStudent");		
			}
			TutorStudentGroup newStudentGroup = new TutorStudentGroup();
			for (int i=0; i < mainForm.getListAvailableTutorGroup().size(); i++) {
				newStudentGroup = (TutorStudentGroup)mainForm.getListAvailableTutorGroup().get(i);
				if (newStudentGroup.getGroupNumber()==mainForm.getSelectedGroup()){
					i = mainForm.getListAvailableTutorGroup().size();
				}
			}
			String message ="";
			String modeAbbr = getTutorModeAbbreviation(newStudentGroup.getTutoringMode().getTutorMode());
			
			//Send sms to student informing him/her of group allocation
			SmsRequest smsRequest = new SmsRequest();
			SmsRequestResult smsResult = new SmsRequestResult();			
			smsRequest.setRcCode(mainForm.getRcCode());
			smsRequest.setReasonCodeGC27("18");
			smsRequest.setProgramName("TUTSTULINK:MYUNISA");
			smsRequest.setPersNo(Integer.parseInt(mainForm.getUser().getPersonnelNumber()));
			smsRequest.setCriteria("");	
			smsRequest.setMessage("");
			List<SmsRecipient> listSms = new ArrayList<SmsRecipient>();			
			SmsRecipient smsIn = new SmsRecipient();
			
			if (mainForm.getStudentGroup()==null || mainForm.getStudentGroup().getGroupNumber()==0){
				message = "UNISA Group Info for " + mainForm.getStudyUnit().getCode() + "-" + mainForm.getAcadYear().substring(2, 4) + "-" + mainForm.getSemesterType() + ". Stu# " +  mainForm.getStudent().getNumber() + 
				". Added to Group " + newStudentGroup.getGroupNumber() + modeAbbr + ". Led by " + newStudentGroup.getTutoringMode().getTutorModeDesc() + ": " + newStudentGroup.getTutor().getName() + ". Visit group site on myUnisa";
				if (message.length() > 160){
					message = "UNISA Group Info for " + mainForm.getStudyUnit().getCode() + "-" + mainForm.getAcadYear().substring(2, 4) + "-" + mainForm.getSemesterType() + ". Stu# " +  mainForm.getStudent().getNumber() + 
					". Added to Group " + newStudentGroup.getGroupNumber() + modeAbbr + ". Led by " + newStudentGroup.getTutoringMode().getTutorModeDesc() + ": " + newStudentGroup.getTutor().getName() + ".";
				}
			}else {
				message = "UNISA Group info for " + mainForm.getStudyUnit().getCode() + "-" + mainForm.getAcadYear().substring(2,4) + "-" + mainForm.getSemesterType() + ". stu# " + mainForm.getStudent().getNumber() + ". Moved to Group " + newStudentGroup.getGroupNumber() + modeAbbr + message + 
				". Led by " + newStudentGroup.getTutoringMode().getTutorModeDesc() + ": " + newStudentGroup.getTutor().getName() + ". Visit group site on myUnisa";
				if (message.length() > 160){
					message = "UNISA Group info for " + mainForm.getStudyUnit().getCode() + "-" + mainForm.getAcadYear().substring(2,4) + "-" + mainForm.getSemesterType() + ". stu# " + mainForm.getStudent().getNumber() + ". Moved to Group " + newStudentGroup.getGroupNumber() + modeAbbr + message + 
					". Led by " + newStudentGroup.getTutoringMode().getTutorModeDesc() + ": " + newStudentGroup.getTutor().getName() + ".";
				}
			}
			smsIn.setStudentNr(mainForm.getStudent().getNumber());
			smsIn.setMessage(message);
			listSms.add(smsIn);
			smsRequest.setListRecipient(listSms);
			
			ProcessDrivenSMS processDrivenSms = new ProcessDrivenSMS();
			smsResult = processDrivenSms.validateSMSRequest(smsRequest);
			if (smsResult.getErrMsg()!=null && !smsResult.getErrMsg().equalsIgnoreCase("")){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage","Error: Sms could not be send to student. " + smsResult.getErrMsg()));
				addErrors(request,messages);
				String studentAddress = formatAddress(mainForm.getStudent().getPhysicalAddress());
				request.setAttribute("studentAddress", studentAddress);		
			 	return mapping.findForward("selectNewGroupReassignStudent");
			}	
			
			if (mainForm.getStudentGroup()==null || mainForm.getStudentGroup().getGroupNumber()==0){
				//assign student to a group
				dao.assignStudentToNewGroup(mainForm.getStudyUnit().getCode(),Short.parseShort(mainForm.getAcadYear()), Short.parseShort(mainForm.getSemester()), Integer.parseInt(mainForm.getStudentNr()),newStudentGroup);
				if (mainForm.getStudent().getContactInfo()!=null && mainForm.getStudent().getContactInfo().getEmailAddress()!=null && !mainForm.getStudent().getContactInfo().getEmailAddress().trim().equalsIgnoreCase("")){
					sendGroupAllocationNotificationToStudent(mainForm.getStudyUnit().getCode(), mainForm.getAcadYear(), Short.parseShort(mainForm.getSemester()), mainForm.getSemesterType(), mainForm.getStudent(), newStudentGroup.getTutoringMode(), newStudentGroup.getTutor(), newStudentGroup.getGroupNumber());					
				}	
			}else{
				//re-assign student to a new group
				dao.reassignStudentToNewGroup(mainForm.getStudyUnit().getCode(),Short.parseShort(mainForm.getAcadYear()), Short.parseShort(mainForm.getSemester()), Integer.parseInt(mainForm.getStudentNr()),mainForm.getStudentGroup(),newStudentGroup);
				if (mainForm.getStudent().getContactInfo()!=null && mainForm.getStudent().getContactInfo().getEmailAddress()!=null && !mainForm.getStudent().getContactInfo().getEmailAddress().trim().equalsIgnoreCase("")){
					sendNewGroupNotificationToStudent(mainForm.getStudyUnit().getCode(), mainForm.getAcadYear(), Short.parseShort(mainForm.getSemester()), mainForm.getSemesterType(), newStudentGroup.getTutoringMode(), newStudentGroup.getTutor(), newStudentGroup.getGroupNumber(),mainForm.getStudent());				
				}	
			}		
			
			processDrivenSms.sendSMSRequest(smsRequest);
			
			//Send email to tutor to inform him/her of new student assigned
			int groupSize = 0;
			groupSize = dao.getNrOfStudentsInGroup(mainForm.getStudyUnit().getCode(), Short.parseShort(mainForm.getAcadYear()),Short.parseShort(mainForm.getSemester()), newStudentGroup);
			sendNewStudentAllocationNotificationToTutor(mainForm.getStudyUnit().getCode(), mainForm.getAcadYear(), Short.parseShort(mainForm.getSemester()), mainForm.getSemesterType(), newStudentGroup.getTutoringMode(), newStudentGroup.getTutor(), newStudentGroup.getGroupNumber(), groupSize, mainForm.getStudent());
			
			//Send email to tutor informing him/her of students that became inactive (has been removed) in groups allocated to him/her
			
			//Only if student was previously assigned to a group
			if (mainForm.getStudentGroup() != null  && mainForm.getStudentGroup().getGroupNumber() != 0){
				//Only send email if tutor has valid employment date	
				if (mainForm.getStudentGroup() !=null && !mainForm.getStudentGroup().getTutor().getResignDate().equalsIgnoreCase("")){
					Calendar currentDate = Calendar.getInstance();
					Date nowDate = currentDate.getTime();
					SimpleDateFormat formatter=new SimpleDateFormat("yyyyMMdd");
					String str_date=mainForm.getStudentGroup().getTutor().getResignDate();
			        Date resignDate = (Date)formatter.parse(str_date); 
			        if (resignDate.after(nowDate)){
			        	//tutor still active send email, resign date after current date
			        	sendStudentInactiveNotificationToTutor(mainForm.getStudyUnit().getCode(), mainForm.getAcadYear(), Short.parseShort(mainForm.getSemester()), mainForm.getSemesterType(), mainForm.getStudentGroup().getTutoringMode(), mainForm.getStudentGroup().getTutor(), mainForm.getStudentGroup().getGroupNumber(), mainForm.getStudent());
			        }
				}else{
					//tutor still active no resign date set
					sendStudentInactiveNotificationToTutor(mainForm.getStudyUnit().getCode(), mainForm.getAcadYear(), Short.parseShort(mainForm.getSemester()), mainForm.getSemesterType(), mainForm.getStudentGroup().getTutoringMode(), mainForm.getStudentGroup().getTutor(), mainForm.getStudentGroup().getGroupNumber(), mainForm.getStudent());
				}
			}			
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"Student has successfully been assigned/reassigned to his/her new group."));
			addMessages(request, messages);
			return mapping.findForward("input");		
	}
	public ActionForward uploadFile(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response)
			throws Exception {
		
		TutorStudentGroupForm mainForm = (TutorStudentGroupForm) form;
		ActionMessages messages = new ActionMessages();
		
		if (mainForm.getSelectedTutoringMode()==null || mainForm.getSelectedTutoringMode().trim().equalsIgnoreCase("")){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"Please select a tutoring mode."));
		}else {
			for (int i=0; i < mainForm.getListAvailableTutorMode().size(); i++) {
				TutoringMode tutoringMode = new TutoringMode();
				tutoringMode = (TutoringMode) mainForm.getListAvailableTutorMode().get(i);
				if (tutoringMode.getTutorMode().trim().equalsIgnoreCase(mainForm.getSelectedTutoringMode().trim())){
					mainForm.setTutoringMode(tutoringMode);
					i = mainForm.getListAvailableTutorMode().size();
				}				
			}			
		}
		if (mainForm.getSelectedTutor()==null || mainForm.getSelectedTutor().trim().equalsIgnoreCase("")){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"Please select a tutor."));
		}else {
			for (int i=0; i < mainForm.getListTutor().size(); i++) {
				Tutor tutor = new Tutor();
				tutor = (Tutor) mainForm.getListTutor().get(i);
				if (tutor.getPerson().getPersonnelNumber().trim().equalsIgnoreCase(mainForm.getSelectedTutor().trim())){
					mainForm.setTutor(tutor);
					i = mainForm.getListTutor().size();
				}
				
			}
		}
		/* check file */
		FileItem item = (FileItem)request.getAttribute("theFile");	
		if (item.getName() == null ||
				item.getName().equalsIgnoreCase("")){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"message.generalmessage", "Please select a file to upload."));
		}
		if (!messages.isEmpty()) {
			addErrors(request,messages);
		 	return mapping.findForward("inputUploadGroup");
		}		
		if (!mainForm.getTutor().getRole().equalsIgnoreCase("TUTOR") && mainForm.getTutoringMode().getIncludeLectAsTutors().equalsIgnoreCase("N")){
			messages.add(ActionMessages.GLOBAL_MESSAGE, 
					new ActionMessage("message.generalmessage", 
						"Please select another tutor, lecturing staff is not allowed as tutors for this tutoring mode."));
		}
		//Tutor not allowed to be a tutor for different tutoring modes
		if (mainForm.getTutor().getTutoringMode()!=null && !mainForm.getTutor().getTutoringMode().equalsIgnoreCase("") && !mainForm.getTutor().getTutoringMode().equalsIgnoreCase(mainForm.getTutoringMode().getTutorMode())){
			messages.add(ActionMessages.GLOBAL_MESSAGE, 
					new ActionMessage("message.generalmessage", 
						"Please select another tutor, tutor has already been allocated to a group with a different tutoring mode, tutor only allowed to tutor in one tutoring mode."));
		}
		//Tutor not allowed to be linked to more than one module per period
		//Tutor not allowed to be linked to a semester and year period simultaneously
		if (mainForm.getTutor().getRole().equalsIgnoreCase("TUTOR")){
			TutorStudentGroupingDAO dao = new TutorStudentGroupingDAO();
			boolean allocatedToDifferentModule = dao.tutorAllocatedToDifferentModule(Short.parseShort(mainForm.getAcadYear()), 
					Short.parseShort(mainForm.getSemester()), mainForm.getStudyUnit().getCode(), mainForm.getTutor().getPerson());
			if (allocatedToDifferentModule==true){
				messages.add(ActionMessages.GLOBAL_MESSAGE, 
						new ActionMessage("message.generalmessage", 
							"Please select another tutor, tutor has already been allocated to a different module for the same period, tutor only allowed to be a tutor for one module during a period. Tutor also cannot tutor a semester and year module simultaneously."));
			}		
		}		
		//Tutor not allowed more than the max nr of groups as set on the tutoring plan.
		if (mainForm.getTutor().getNumberGroupsAllocated() >= mainForm.getTutoringMode().getMaxGroupsPerTutor()){
			messages.add(ActionMessages.GLOBAL_MESSAGE, 
					new ActionMessage("message.generalmessage", 
						"Please select another tutor, tutor has already been allocated the maximum number of groups allowed for this tutoring mode."));
		}
		if (mainForm.getTutoringMode().getTutorContact().equalsIgnoreCase("EMAILADR")){
			//Check if tutor's email address available as contact detail to students.
			if (mainForm.getTutor().getPerson().getEmailAddress()==null || mainForm.getTutor().getPerson().getEmailAddress().trim().equalsIgnoreCase("")){
				messages.add(ActionMessages.GLOBAL_MESSAGE, 
						new ActionMessage("message.generalmessage", 
							"Tutor contact details not available, no email address for tutor on the system, to make available to students."));
			}
		}
		if (mainForm.getTutoringMode().getTutorContact().equalsIgnoreCase("CELLNR")){
			//Check if tutor's email address available as contact detail to students.
			boolean validCellNumber=true;
			if (mainForm.getTutor().getPerson().getContactNumber()!=null && !mainForm.getTutor().getPerson().getContactNumber().trim().equalsIgnoreCase("")){
				CellPhoneVerification verifyCell = new CellPhoneVerification();
				validCellNumber = verifyCell.isCellNumber(mainForm.getTutor().getPerson().getContactNumber());
				if (validCellNumber){
					if (verifyCell.isSaCellNumber(mainForm.getTutor().getPerson().getContactNumber())){
						if (!verifyCell.validSaCellNumber(mainForm.getTutor().getPerson().getContactNumber())){
							validCellNumber=false;
						}
					}
				}
				if (!validCellNumber){
					messages.add(ActionMessages.GLOBAL_MESSAGE, 
							new ActionMessage("message.generalmessage", 
								"Tutor contact details not available, no valid cell phone number for tutor on the system, to make available to students."));
				}
			}else{
				messages.add(ActionMessages.GLOBAL_MESSAGE, 
						new ActionMessage("message.generalmessage", 
							"Tutor contact details not available, no valid cell phone number for tutor on the system, to make available to students."));
			}
		}
		if (!messages.isEmpty()) {
			addErrors(request,messages);
		 	return mapping.findForward("inputUploadGroup");
		}		
					
		long fileSize = item.getSize();
		String tempstr = item.getString();
		String contentType = item.getContentType();
		String fileName = item.getName();	
		byte[] array = item.get();		
		
		String[] recordArray = tempstr.split("\\s");	
				
		String[] remove = {""};
		List temp = new ArrayList(Arrays.asList(recordArray));
		temp.removeAll(new ArrayList(Arrays.asList(remove)));
		recordArray  = (String[]) temp.toArray(new String[temp.size()]);
		
		if (!contentType.equalsIgnoreCase("text/plain")){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage","You can only upload txt file types."));
		}
		
		if (fileSize > 1048576) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage","You cannot upload files greater than 1 MB (1024 KB)"));
		 }
				
		if (!messages.isEmpty()) {
			addErrors(request,messages);
			return mapping.findForward("inputUploadGroup");
		}
		
		TutorStudentGroupingDAO dao = new TutorStudentGroupingDAO();		
		String strStudentNumbers="";
		String strErrors="";
		int validRecords = 0;	
		int invalidRecords = 0;
		List studentNumbersInFile = new ArrayList<String>();
		String studentNumber="";
		String errMsg = "";
		for (int i=0 ;i < recordArray.length; i++){
			errMsg = "";
			String recValue = recordArray[i];			
			//file with student numbers
			studentNumber=recValue.trim();			
			boolean duplicate = false;
			//Check duplicate student number in file
			for (int j=0; j <studentNumbersInFile.size(); j++){
				if (studentNumber.equalsIgnoreCase((String)studentNumbersInFile.get(j))){
					duplicate = true;
				}
			}
			if (duplicate==true){
				errMsg = "Duplicate entry in file";
			}
			studentNumbersInFile.add(studentNumber);
			//Not duplicate number
			if (duplicate==false){
				if (isInteger(recValue.trim())){
				   	  boolean validStudentNr = false;
				   	  boolean registeredStudent = false;
				   	  registeredStudent = dao.isStudentRegisteredForModule(mainForm.getStudyUnit().getCode(), Short.parseShort(mainForm.getAcadYear()), 
				   			  Short.parseShort(mainForm.getSemester()), Integer.parseInt(recValue.trim()));
				   	  if (!registeredStudent){			    		  
				   		  validStudentNr = dao.isValidStudentNr(Integer.parseInt(recValue.trim()));
				   		  if (!validStudentNr){
				   			  errMsg = "Invalid student number.";
				   		  }else{
				   			  errMsg = "Student not registered for module.";
				   		  }			   		  
				   	  }else{
				   		//check student already allocated to a group
				   		  boolean studentAllocatedToGroup = false;
				   		  studentAllocatedToGroup = dao.isStudentAllocatedToATutorGroup(mainForm.getStudyUnit().getCode(), Short.parseShort(mainForm.getAcadYear()), 
					   			  Short.parseShort(mainForm.getSemester()), Integer.parseInt(recValue.trim()));
				   		  if (studentAllocatedToGroup){
				   			  errMsg = "Student already allocated to a tutor-student group.";
				   		  }
				   	  }
				   	  Contact studentContactDetails = new Contact();
				   	  studentContactDetails = dao.getContactDetails(Integer.parseInt(recValue.trim()), 1);
				   	  if ((studentContactDetails.getCellNumber()==null || studentContactDetails.getCellNumber().trim().equalsIgnoreCase("")) &&
				   			  (studentContactDetails.getEmailAddress()==null || studentContactDetails.getEmailAddress().trim().equalsIgnoreCase(""))){
				   		errMsg = "Student with no email and no cell phone number.";
				   			  }
				     }else{
				   	  errMsg = "Student number not numeric.";
				   }
			}	
			
			if (!errMsg.equalsIgnoreCase("")){
				strErrors = strErrors + recordArray[i] + ":" + errMsg + "~";
				invalidRecords = invalidRecords + 1;
		    }else {		    	
		    	validRecords = validRecords + 1;
		    	if (strStudentNumbers.equalsIgnoreCase("")){
		    		strStudentNumbers = recValue.trim();
		    	}else{
		    		strStudentNumbers = strStudentNumbers + "~"  + recValue.trim();
		    	}	
		    }
		}	
		
		if (strStudentNumbers.trim().equalsIgnoreCase("")){			
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage","Request can not be processed. No valid records in file."));
			addErrors(request,messages);
			return mapping.findForward("inputUploadGroup");
		}		
		
		String[] studentArray = strStudentNumbers.split("~");		
		String[] errorsArray = strErrors.split("~");
		
		//write error file
		String errorFileName="";
		if (errorsArray.length>0){
			String path = getServlet().getServletContext().getInitParameter("applicationFullPath")+"/";
			String fileDir = path +"/";
			String time = (new java.text.SimpleDateFormat("yyyyMMddhhmmssss").format(new java.util.Date()).toString());
			errorFileName = fileDir + mainForm.getUserId() +"_ManualTutorStudentGroupError_"+ time +".txt";			
			writeErrorFile(errorsArray, errorFileName);
		}		
		
		mainForm.setErrorFileName(errorFileName);
		mainForm.setValidRecords(validRecords);
		mainForm.setInvalidRecords(invalidRecords);
		mainForm.setFileSize(String.valueOf(fileSize) + " Bytes");
		mainForm.setFileName(fileName);
		mainForm.setStudentArray(studentArray);
		
		if(mainForm.getTutor().getNumberGroupsAllocated()==0){
			boolean tutorWithLessThanMaxGroups=false;
			for (int i=0; i < mainForm.getListTutor().size(); i++) {
				Tutor tutor = new Tutor();
				tutor = (Tutor) mainForm.getListTutor().get(i);
				//only test for tutors not for lecturers.
				if (tutor.getTutoringMode().equalsIgnoreCase(mainForm.getTutoringMode().getTutorMode()) && tutor.getNumberGroupsAllocated() < mainForm.getTutoringMode().getMaxGroupsPerTutor() && mainForm.getTutor().getRole().equalsIgnoreCase("TUTOR")){
					tutorWithLessThanMaxGroups=true;
					i = mainForm.getListTutor().size();
				}
			}
			if (tutorWithLessThanMaxGroups){
				messages.add(ActionMessages.GLOBAL_MESSAGE, 
						new ActionMessage("message.generalmessage", 
							"Please re-consider using a tutor to whom groups have already been allocated, but who has less groups than the max allowed, instead of using a tutor not yet allocated to a group. Max groups allowed for the selected tutoring mode: " + mainForm.getTutoringMode().getMaxGroupsPerTutor() + ". Click on 'Back' to select a different tutor."));
				addErrors(request, messages);
			}
		}
		
		return mapping.findForward("verifyUploadGroup");
	}
	
	public ActionForward uploadGroup(
			ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response)
			throws Exception {
		
		TutorStudentGroupForm mainForm = (TutorStudentGroupForm) form;
		ActionMessages messages = new ActionMessages();
		
		if (mainForm.getStudentArray().length > mainForm.getTutoringMode().getTutorStuRatio()){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage","Number of valid student numbers in file, exceeds the tutor student ratio per group as specified in the tutoring plan for this tutoring mode."));
			addErrors(request,messages);
			return mapping.findForward("verifyUploadGroup");
		}
		int newGroupNr=0;
		//verify sms's request to send sms's before updating database
		SmsRequest smsRequest = new SmsRequest();
		SmsRequestResult smsResult = new SmsRequestResult();		
		smsRequest.setMessage("");
		smsRequest.setRcCode(mainForm.getRcCode());
		smsRequest.setReasonCodeGC27("18");
		smsRequest.setProgramName("TUTSTULINK:MYUNISA");
		smsRequest.setPersNo(Integer.parseInt(mainForm.getUser().getPersonnelNumber()));
		//smsRequest.setCriteria(mainForm.getAcadYear() + "/" + mainForm.getSemester() + "/" + mainForm.getStudyUnit().getCode() + "/" + mainForm.getTutoringMode().getTutorMode() + "/" + newGroupNr);
		smsRequest.setCriteria("");
		
		List<SmsRecipient> listSms = new ArrayList<SmsRecipient>();
		for (int i=0; i < mainForm.getStudentArray().length; i++ ){
			SmsRecipient smsIn = new SmsRecipient();
			smsIn.setStudentNr(Integer.parseInt(mainForm.getStudentArray()[i].toString()));
			smsIn.setMessage("dummy message to validate");
			listSms.add(smsIn);
		}
		smsRequest.setListRecipient(listSms);
		ProcessDrivenSMS processDrivenSms = new ProcessDrivenSMS();
		smsResult = processDrivenSms.validateSMSRequest(smsRequest);
		
		if (smsResult.getErrMsg()!=null && !smsResult.getErrMsg().equalsIgnoreCase("")){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage","Upload failed, sms's could not be send to students. Error: " + smsResult.getErrMsg()));
			addErrors(request,messages);
			return mapping.findForward("verifyUploadGroup");
		}		
		
		TutorStudentGroupingDAO dao = new TutorStudentGroupingDAO();		
		newGroupNr = dao.createTutorStudentGroup(mainForm.getStudentArray(),mainForm.getStudyUnit().getCode(), Short.parseShort(mainForm.getAcadYear()), 
	   			  Short.parseShort(mainForm.getSemester()), mainForm.getTutoringMode().getTutorMode(), mainForm.getTutor(), mainForm.getTutoringMode().getAllocationCriteria(),mainForm.getUserId());
		
		//Send sms's for all students in group informing them of tutor details
		//message = "Assign to group: " + newGroupNr + ", tutor: " + mainForm.getTutor().getPerson().getName().trim() + ", email: " + mainForm.getTutor().getPerson().getEmailAddress();
		for (int i=0; i < listSms.size(); i++){
			SmsRecipient smsIn = new SmsRecipient();
			smsIn = (SmsRecipient)listSms.get(i);
			String modeAbbr = getTutorModeAbbreviation(mainForm.getTutoringMode().getTutorMode());
			String studentMessage =  "UNISA Group Info for " + mainForm.getStudyUnit().getCode() + "-" + mainForm.getAcadYear().substring(2, 4) + "-" + mainForm.getSemesterType() + ". Stu# " +  smsIn.getStudentNr() + 
			". Added to Group " + newGroupNr + modeAbbr + ". Led by " + mainForm.getTutoringMode().getTutorModeDesc() + ": " + mainForm.getTutor().getPerson().getName() + ". Visit group site on myUnisa";
			if (studentMessage.length() > 160) {
				studentMessage =  "UNISA Group Info for " + mainForm.getStudyUnit().getCode() + "-" + mainForm.getAcadYear().substring(2, 4) + "-" + mainForm.getSemesterType() + ". Stu# " +  smsIn.getStudentNr() + 
				". Added to Group " + newGroupNr + modeAbbr + ". Led by " + mainForm.getTutoringMode().getTutorModeDesc() + ": " + mainForm.getTutor().getPerson().getName() + ".";
			}
			smsIn.setMessage(studentMessage);
		}
		smsRequest.setMessage("");
		processDrivenSms.sendSMSRequest(smsRequest);	
		
		//Send email for all students in group informing them of tutor details
		List<Student> groupStudentList = new ArrayList<Student>();
		for (int i=0; i < mainForm.getStudentArray().length; i++ ){
			int studentNr=0;
			studentNr = Integer.parseInt(mainForm.getStudentArray()[i].toString());
			Student student = new Student();
			student = dao.getStudent(studentNr);
			if (student.getContactInfo()!=null && student.getContactInfo().getEmailAddress()!=null && !student.getContactInfo().getEmailAddress().trim().equalsIgnoreCase("")){
				sendGroupAllocationNotificationToStudent(mainForm.getStudyUnit().getCode(), mainForm.getAcadYear(), Short.parseShort(mainForm.getSemester()), mainForm.getSemesterType(), student, mainForm.getTutoringMode(), mainForm.getTutor().getPerson(),newGroupNr );
			}
			groupStudentList.add(student);
		}
		
		sendGroupAllocationNotificationToTutor(mainForm.getStudyUnit().getCode(), mainForm.getAcadYear(), Short.parseShort(mainForm.getSemester()),  mainForm.getSemesterType(),groupStudentList, mainForm.getTutoringMode(),mainForm.getTutor(), newGroupNr);
		
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
				"message.generalmessage",
				"Tutor-student group " + newGroupNr + " was processed successfully."));
		addMessages(request, messages);
		
		request.setAttribute("newGroupNr", newGroupNr);
		
		return mapping.findForward("confirmUploadGroup");
	}	
	
	public ActionForward viewInvalidRecords(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		TutorStudentGroupForm mainForm = (TutorStudentGroupForm) form;
		ActionMessages messages = new ActionMessages();
		
		return mapping.findForward("viewInvalidRecords");
			
	}
	
	public static void writeErrorFile(String[] errMessages, String fileName){
		 //Create file 
		 try {
			 FileOutputStream out = new FileOutputStream(new File(fileName));
			 PrintStream ps = new PrintStream(out);
			 for (int i=0; i<errMessages.length; i++){
				 ps.print(errMessages[i]);
				 ps.println(); 		
			 }
			 ps.close();
		 } catch (Exception e) {}		
	}
	
	private String formatAddress(Address address){
		String addressStr="";
		
		
		if (address.getLine1()!=null && !address.getLine1().trim().equalsIgnoreCase("")){
			addressStr=address.getLine1();
		}
		if (address.getLine2()!=null && !address.getLine2().trim().equalsIgnoreCase("")){
			addressStr=addressStr + ", " + address.getLine2();
		}
		if (address.getLine3()!=null && !address.getLine3().trim().equalsIgnoreCase("")){
			addressStr=addressStr + ", " + address.getLine3();
		}
		if (address.getLine4()!=null && !address.getLine4().trim().equalsIgnoreCase("")){
			addressStr=addressStr + ", " + address.getLine4();
		}
		if (address.getLine5()!=null && !address.getLine5().trim().equalsIgnoreCase("")){
			addressStr=addressStr + ", " + address.getLine5();
		}
		if (address.getLine6()!=null && !address.getLine6().trim().equalsIgnoreCase("")){
			addressStr=addressStr+ ", " + address.getLine6();
		}
		if (address.getPostalCode()!=null && !address.getPostalCode().equalsIgnoreCase("")){
			addressStr=addressStr+ ", " + address.getPostalCode();
		}
		
		return addressStr;
	}
	
	public void sendGroupAllocationNotificationToStudent(String studyUnit, String acadYear, Short semester, String semesterType, Student student, TutoringMode tutorMode,Person tutor, int groupNr) {

		try {

			String tmpEmailFrom = ServerConfigurationService.getString("noReplyEmailFrom");
			String serverPath = ServerConfigurationService.getToolUrl();
			String toAddress=student.getContactInfo().getEmailAddress();
			//do not send email on localhost,dev or qa -default email to pretoj@unisa.ac.za	
			if (serverPath.contains("mydev") || serverPath.contains("myqa") || serverPath.contains("localhost")){
					toAddress="pretoj@unisa.ac.za";
			}	
			InternetAddress toEmail = new InternetAddress(toAddress);			
			InternetAddress iaTo[] = new InternetAddress[1];
			iaTo[0] = toEmail;
			InternetAddress iaHeaderTo[] = new InternetAddress[1];
			iaHeaderTo[0] = toEmail;
			InternetAddress iaReplyTo[] = new InternetAddress[1];
			iaReplyTo[0] = new InternetAddress(tmpEmailFrom);		

			/* send activation email to student */
			String modeAbbr = getTutorModeAbbreviation(tutorMode.getTutorMode());
			String subject = studyUnit + "-" + acadYear.substring(2, 4) + "-" + semesterType + ": Group Allocation Details";
			String body = "<html> "+
		    "<body>Dear student," + "<br/><br/>"+
			//"Your registration for " + studyUnit + " " + acadYear + "/" + semesterType + " has been finalised.  " +
			"The tuition for module " + studyUnit + " will take place via a group site which can be found on myUnisa.<br/>" +
			"You have been allocated to <b>Group " + groupNr + modeAbbr + "</b> " +
			"and the " + tutorMode.getTutorModeDesc() + " for this group is " + tutor.getName() + ".<br/><br/> " +
			"Please visit myUnisa (https://my.unisa.ac.za) to access your particular group site which is <b>" + studyUnit + "-" + acadYear.substring(2, 4) + "-" + semesterType + "-" + groupNr + modeAbbr + "</b>.<br/><br/> " +
			"Should you have any further queries regarding your group allocation, please post these within the<br/>" +
			"group site on myUnisa.<br/><br/>" +
			"Good luck with your studies,<br/>" +
			"myUnisa Support Team" +
			"</body>"+
			"</html>";
		
		  List<String> contentList = new ArrayList<String>();
		  contentList.add("Content-Type: text/html");  
		  
		 emailService = (EmailService)ComponentManager.get(EmailService.class); 
		 emailService.sendMail(iaReplyTo[0],iaTo,subject,body,iaHeaderTo,iaReplyTo,contentList);
		 log.info("sending mail from "+iaReplyTo[0]+" to "+toEmail.toString());
		 EmailLogRecord log = new EmailLogRecord();
			log.setRecipient(String.valueOf(student.getNumber()));
			log.setRecipientType("STUDENT");
			log.setEmailAddress(toAddress);
			log.setProgram("TUTSTULINK:MYUNISA");
			log.setSubject(subject);
			log.setEmailType("ALLOCATION");
			log.setBody("Group " + groupNr + modeAbbr + ", tutor: " + tutor.getName());
			TutorStudentGroupingDAO dao = new TutorStudentGroupingDAO();
			dao.insertEmailLog(log);
		 } catch (Exception e) {
		 	log.error("TutorStudentGrouping: send of email informing student of group allocation failed. "+e+" "+e.getMessage());
			e.printStackTrace();
		 }
	}
	
	public void sendGroupAllocationNotificationToTutor(String studyUnit, String acadYear, Short semester, String semesterType,List<Student> groupStudentList, TutoringMode tutorMode,Tutor tutor, int groupNr) {

		try {

			String tmpEmailFrom = ServerConfigurationService.getString("noReplyEmailFrom");
			String serverPath = ServerConfigurationService.getToolUrl();
			String toAddress=tutor.getPerson().getEmailAddress();
			//do not send email on localhost,dev or qa -default email to pretoj@unisa.ac.za	
			if (serverPath.contains("mydev") || serverPath.contains("myqa") || serverPath.contains("localhost")){
					toAddress="pretoj@unisa.ac.za";
			}
			InternetAddress toEmail = new InternetAddress(toAddress);
			InternetAddress iaTo[] = new InternetAddress[1];
			iaTo[0] = toEmail;
			InternetAddress iaHeaderTo[] = new InternetAddress[1];
			iaHeaderTo[0] = toEmail;
			InternetAddress iaReplyTo[] = new InternetAddress[1];
			iaReplyTo[0] = new InternetAddress(tmpEmailFrom);		

			/* send activation email to student */
			String modeAbbr = getTutorModeAbbreviation(tutorMode.getTutorMode());
			String subject = studyUnit + "-" + acadYear.substring(2, 4) + "-" + semesterType + ": Group Allocation Details";
			String body = "<html> "+
		    "<body>Good day, " + tutor.getPerson().getName() + "<br/><br/>"+
			"A new group allocation for <b>" + studyUnit + "-" + acadYear.substring(2, 4) + "-" + semesterType + "</b> has been uploaded (made). You are assigned to <b>Group " + groupNr + modeAbbr + ".</b> Please<br/>" +
			"verify that you have access to this group site on myUnisa and that the tools are functioning properly.<br/><br/>" +
			"Your group details are:<br/><br/>" +
			"<b>Group " + groupNr + " </b><br/><br/><table>";
			
			
			for (int i=0; i < groupStudentList.size(); i++){
				Student student = new Student();
				student = (Student) groupStudentList.get(i);
				if (student.getContactInfo()!=null && student.getContactInfo().getEmailAddress()!=null && !student.getContactInfo().getEmailAddress().equalsIgnoreCase("")){
					//body = body + "Student " + student.getNumber() + " " + student.getPrintName() + " " + student.getContactInfo().getEmailAddress() + "<br/>";
					body = body + "<tr><td>Student " + student.getNumber() + "</td><td>&nbsp;</td><td>" + student.getPrintName() + "</td><td>" + student.getContactInfo().getEmailAddress() + "</td></tr>";
				}else{
					//body = body + "Student " + student.getNumber() + " " + student.getPrintName() + " No email<br/>";
					body = body + "<tr><td>Student " + student.getNumber() + "</td><td>&nbsp;</td><td>" + student.getPrintName() + "</td><td>[No email]</td></tr>";
				}				
			}			
			
			body = body + "</table><br/>Good luck with your teaching activities,<br/>" +
			"myUnisa Support Team" +
			"</body>"+
			"</html>";
		
		  List<String> contentList = new ArrayList<String>();
		  contentList.add("Content-Type: text/html");  
		  
		 emailService = (EmailService)ComponentManager.get(EmailService.class); 
		 emailService.sendMail(iaReplyTo[0],iaTo,subject,body,iaHeaderTo,iaReplyTo,contentList);
		 log.info("sending mail from "+iaReplyTo[0]+" to "+toEmail.toString());
		 EmailLogRecord log = new EmailLogRecord();
			log.setRecipient(tutor.getPerson().getNovellUserId());
			log.setRecipientType("TUTOR");
			log.setEmailAddress(toAddress);
			log.setProgram("TUTSTULINK:MYUNISA");
			log.setSubject(subject);
			log.setEmailType("ALLOCATION");
			log.setBody("Group " + groupNr + modeAbbr);
		TutorStudentGroupingDAO dao = new TutorStudentGroupingDAO();
		dao.insertEmailLog(log);
		 } catch (Exception e) {
		 	log.error("TutorStudentGrouping: send of email informing tutor of group allocation failed. "+e+" "+e.getMessage());
			e.printStackTrace();
		 }
	}
	
	public void sendUpdatedGroupAllocationNotificationToTutor(String studyUnit, String acadYear, Short semester, String semesterType,List<Student> groupStudentList, TutoringMode tutorMode,Person tutor, int groupNr) {

		try {

			String tmpEmailFrom = ServerConfigurationService.getString("noReplyEmailFrom");
			String serverPath = ServerConfigurationService.getToolUrl();
			String toAddress=tutor.getEmailAddress();
			//do not send email on localhost,dev or qa -default email to pretoj@unisa.ac.za	
			if (serverPath.contains("mydev") || serverPath.contains("myqa") || serverPath.contains("localhost")){
					toAddress="pretoj@unisa.ac.za";
			}
			InternetAddress toEmail = new InternetAddress(toAddress);
			InternetAddress iaTo[] = new InternetAddress[1];
			iaTo[0] = toEmail;
			InternetAddress iaHeaderTo[] = new InternetAddress[1];
			iaHeaderTo[0] = toEmail;
			InternetAddress iaReplyTo[] = new InternetAddress[1];
			iaReplyTo[0] = new InternetAddress(tmpEmailFrom);		

			/* send activation email to student */
			String modeAbbr = getTutorModeAbbreviation(tutorMode.getTutorMode());
			String subject = studyUnit + "-" + acadYear.substring(2, 4) + "-" + semesterType + ": Group Allocation Details";
			String body = "<html> "+
		    "<body>Good day, " + tutor.getName() + "<br/><br/>"+
			"Your group allocation(s) for <b>" + studyUnit + "-" + acadYear.substring(2, 4) + "-" + semesterType + "</b> have been updated.  You are now assigned to <b>Group " + groupNr + modeAbbr + "</b>.<br/>" +
			"Please verify that you have access to this group site on myUnisa and that the tools are functioning<br/>" +
			"properly.<br/><br/>" +
			"Your group details are:<br/><br/>" +
			"<b>Group " + groupNr + " </b><br/><br/><table>";
			
			
			for (int i=0; i < groupStudentList.size(); i++){
				Student student = new Student();
				student = (Student) groupStudentList.get(i);
				if (student.getContactInfo()!=null && student.getContactInfo().getEmailAddress()!=null && !student.getContactInfo().getEmailAddress().equalsIgnoreCase("")){
					//body = body + "Student " + student.getNumber() + " " + student.getPrintName() + " " + student.getContactInfo().getEmailAddress() + "<br/>";
					body = body + "<tr><td>Student " + student.getNumber() + "</td><td>&nbsp;</td><td>" + student.getPrintName() + "</td><td>" + student.getContactInfo().getEmailAddress() + "</td></tr>";
				}else{
					//body = body + "Student " + student.getNumber() + " " + student.getPrintName() + " No email<br/>";
					body = body + "<tr><td>Student " + student.getNumber() + "</td><td>&nbsp;</td><td>" + student.getPrintName() + "</td><td>[No email]</td></tr>";
				}				
			}			
			
			body = body + "</table><br/>Good luck with your teaching activities,<br/>" +
			"myUnisa Support Team" +
			"</body>"+
			"</html>";
		
		  List<String> contentList = new ArrayList<String>();
		  contentList.add("Content-Type: text/html");  
		  
		 emailService = (EmailService)ComponentManager.get(EmailService.class); 
		 emailService.sendMail(iaReplyTo[0],iaTo,subject,body,iaHeaderTo,iaReplyTo,contentList);
		 log.info("sending mail from "+iaReplyTo[0]+" to "+toEmail.toString());
		 EmailLogRecord log = new EmailLogRecord();
			log.setRecipient(tutor.getNovellUserId());
			log.setRecipientType("TUTOR");
			log.setEmailAddress(toAddress);
			log.setProgram("TUTSTULINK:MYUNISA");
			log.setSubject(subject);
			log.setEmailType("ALLOCATION");
			log.setBody("Group " + groupNr + modeAbbr);
		TutorStudentGroupingDAO dao = new TutorStudentGroupingDAO();
		dao.insertEmailLog(log);
		 } catch (Exception e) {
		 	log.error("TutorStudentGrouping: send of email informing tutor of group allocation failed. "+e+" "+e.getMessage());
			e.printStackTrace();
		 }
	}
	
	public void sendNewStudentAllocationNotificationToTutor(String studyUnit, String acadYear, Short semester, String semesterType,TutoringMode tutorMode,Person tutor, int groupNr, int groupSize, Student student) {

		try {

			String tmpEmailFrom = ServerConfigurationService.getString("noReplyEmailFrom");
			String serverPath = ServerConfigurationService.getToolUrl();
			String toAddress=tutor.getEmailAddress();
			//do not send email on localhost,dev or qa -default email to pretoj@unisa.ac.za	
			if (serverPath.contains("mydev") || serverPath.contains("myqa") || serverPath.contains("localhost")){
					toAddress="pretoj@unisa.ac.za";
			}
			InternetAddress toEmail = new InternetAddress(toAddress);
			InternetAddress iaTo[] = new InternetAddress[1];
			iaTo[0] = toEmail;
			InternetAddress iaHeaderTo[] = new InternetAddress[1];
			iaHeaderTo[0] = toEmail;
			InternetAddress iaReplyTo[] = new InternetAddress[1];
			iaReplyTo[0] = new InternetAddress(tmpEmailFrom);		

			/* email to Tutor informing him/her of new student assigned to his/her group */
			String modeAbbr = getTutorModeAbbreviation(tutorMode.getTutorMode());
			String subject = studyUnit + "-" + acadYear.substring(2, 4) + "-" + semesterType + "-" + groupNr + modeAbbr + ": New Student Added to Group";
			String body = "<html> "+
		    "<body>Good day, " + tutor.getName() + "<br/><br/>"+
		    "Please note that a new student has been added to <b>Group " + groupNr + modeAbbr + "</b> for " + studyUnit + "-" + acadYear.substring(2, 4) + "-" + semesterType + ". This group now<br/>" +
		    "consist of " + groupSize + " students.<br/><br/>" +
		    "New student details are:<br/><br/><table>";
			
		    if (student.getContactInfo()!=null && student.getContactInfo().getEmailAddress()!=null && !student.getContactInfo().getEmailAddress().equalsIgnoreCase("")){
				//body = body + "Student " + student.getNumber() + " " + student.getPrintName() + " " + student.getContactInfo().getEmailAddress() + "<br/>";
				body = body + "<tr><td>Student " + student.getNumber() + "</td><td>&nbsp;</td><td>" + student.getPrintName() + "</td><td>" + student.getContactInfo().getEmailAddress() + "</td></tr>";
			}else{
				//body = body + "Student " + student.getNumber() + " " + student.getPrintName() + " No email<br/>";
				body = body + "<tr><td>Student " + student.getNumber() + "</td><td>&nbsp;</td><td>" + student.getPrintName() + "</td><td>[No email]</td></tr>";
			}
			
			body = body + "</table><br/>Good luck with your teaching activities,<br/>" +
			"myUnisa Support Team" +
			"</body>"+
			"</html>";
		
			List<String> contentList = new ArrayList<String>();
			contentList.add("Content-Type: text/html");  
			  
			emailService = (EmailService)ComponentManager.get(EmailService.class); 
			emailService.sendMail(iaReplyTo[0],iaTo,subject,body,iaHeaderTo,iaReplyTo,contentList);
			log.info("sending mail from "+iaReplyTo[0]+" to "+toEmail.toString());
			EmailLogRecord log = new EmailLogRecord();
			log.setRecipient(tutor.getNovellUserId());
			log.setRecipientType("TUTOR");
			log.setEmailAddress(toAddress);
			log.setProgram("TUTSTULINK:MYUNISA");
			log.setSubject(subject);
			log.setEmailType("REASSIGN");
			log.setBody("Group " + groupNr + modeAbbr + ", Student: " + student.getNumber());
			TutorStudentGroupingDAO dao = new TutorStudentGroupingDAO();
			dao.insertEmailLog(log);
		 } catch (Exception e) {
		 	log.error("TutorStudentGrouping: send of email informing tutor of new student assigned. "+e+" "+e.getMessage());
			e.printStackTrace();
		 }
	}
	
	public void sendStudentInactiveNotificationToTutor(String studyUnit, String acadYear, Short semester, String semesterType,TutoringMode tutorMode,Person tutor, int groupNr, Student student){
		try {

			String tmpEmailFrom = ServerConfigurationService.getString("noReplyEmailFrom");
			String serverPath = ServerConfigurationService.getToolUrl();
			String toAddress=tutor.getEmailAddress();
			//do not send email on localhost,dev or qa -default email to pretoj@unisa.ac.za	
			if (serverPath.contains("mydev") || serverPath.contains("myqa") || serverPath.contains("localhost")){
					toAddress="pretoj@unisa.ac.za";
			}
			InternetAddress toEmail = new InternetAddress(toAddress);
			InternetAddress iaTo[] = new InternetAddress[1];
			iaTo[0] = toEmail;
			InternetAddress iaHeaderTo[] = new InternetAddress[1];
			iaHeaderTo[0] = toEmail;
			InternetAddress iaReplyTo[] = new InternetAddress[1];
			iaReplyTo[0] = new InternetAddress(tmpEmailFrom);		

			/* email to Tutor informing him/her of new student assigned to his/her group */
			String modeAbbr = getTutorModeAbbreviation(tutorMode.getTutorMode());
			String subject = studyUnit + "-" + acadYear.substring(2, 4) + "-" + semesterType + ": Student removed from Group "+ groupNr + modeAbbr;
			String body = "<html> "+
		    "<body>Good day, " + tutor.getName() + "<br/><br/>"+
		    "Please note that the following student is no longer part of <b>Group " + groupNr + modeAbbr + "</b> for module <b>" + studyUnit + "-" + acadYear.substring(2, 4) + "-" + semesterType + "</b>. The<br/>" +
		    "academic department will advise you in due course if you need to process unmarked assignment<br/>" +
		    "contributions submitted by this student in Group" + groupNr + modeAbbr + ".<br/><br/>" +
			"Student details are:<br/><br/><table>" +
			"<tr><td>Student " + student.getNumber() + "</td><td>&nbsp;</td><td>" + student.getPrintName() + "</td></tr></table><br/>" +
			"Regards,<br/>" +
			"myUnisa Support Team" +
			"</body>"+
			"</html>";
		
			List<String> contentList = new ArrayList<String>();
			contentList.add("Content-Type: text/html");  
			  
			emailService = (EmailService)ComponentManager.get(EmailService.class); 
			emailService.sendMail(iaReplyTo[0],iaTo,subject,body,iaHeaderTo,iaReplyTo,contentList);
			log.info("sending mail from "+iaReplyTo[0]+" to "+toEmail.toString());
			EmailLogRecord log = new EmailLogRecord();
			log.setRecipient(tutor.getNovellUserId());
			log.setRecipientType("TUTOR");
			log.setEmailAddress(toAddress);
			log.setProgram("TUTSTULINK:MYUNISA");
			log.setSubject(subject);
			log.setEmailType("INACTIVE");
			log.setBody("Group " + groupNr + modeAbbr + ", Student: " + student.getNumber());
			TutorStudentGroupingDAO dao = new TutorStudentGroupingDAO();
			dao.insertEmailLog(log);
		 } catch (Exception e) {
		 	log.error("TutorStudentGrouping: send of email informing tutor of new student assigned. "+e+" "+e.getMessage());
			e.printStackTrace();
		 }
	}
	
	public void sendNewGroupNotificationToStudent(String studyUnit, String acadYear, Short semester, String semesterType,TutoringMode tutorMode,Person tutor, int groupNr, Student student){
		try {

			String tmpEmailFrom = ServerConfigurationService.getString("noReplyEmailFrom");
			String serverPath = ServerConfigurationService.getToolUrl();
			String toAddress=student.getContactInfo().getEmailAddress();
			//do not send email on localhost,dev or qa -default email to pretoj@unisa.ac.za	
			if (serverPath.contains("mydev") || serverPath.contains("myqa") || serverPath.contains("localhost")){
					toAddress="pretoj@unisa.ac.za";
			}	
			InternetAddress toEmail = new InternetAddress(toAddress);			
			InternetAddress iaTo[] = new InternetAddress[1];
			iaTo[0] = toEmail;
			InternetAddress iaHeaderTo[] = new InternetAddress[1];
			iaHeaderTo[0] = toEmail;
			InternetAddress iaReplyTo[] = new InternetAddress[1];
			iaReplyTo[0] = new InternetAddress(tmpEmailFrom);		

			/* Email to student informing him/her of re-assignment*/
			String modeAbbr = getTutorModeAbbreviation(tutorMode.getTutorMode());
			String subject = studyUnit + "-" + acadYear.substring(2, 4) + "-" + semesterType + ": New Group Allocation Details";
			String body = "<html> "+
		    "<body>Dear student," + "<br/><br/>"+
			"Your group details for " + studyUnit + " " + acadYear + "/" + semesterType + " has changed.  You have now been allocated to<br/> " +
			"<b>Group " + groupNr + modeAbbr + "</b> and tuition for this group is led by " + tutor.getName() + ".<br/><br/> " +
			"Please visit myUnisa (https://my.unisa.ac.za) to access your new group site which is <b>" + studyUnit + "-" + acadYear.substring(2, 4) + "-" + semesterType + "-" + groupNr + modeAbbr + "</b>.<br/><br/> " +
			"Should you have any further queries regarding your new group allocation, please post these within the<br/>" +
			"group site on myUnisa.<br/><br/>" +
			"Good luck with your studies,<br/>" +
			"myUnisa Support Team" +
			"</body>"+
			"</html>";
		
		  List<String> contentList = new ArrayList<String>();
		  contentList.add("Content-Type: text/html");  
		  
		 emailService = (EmailService)ComponentManager.get(EmailService.class); 
		 emailService.sendMail(iaReplyTo[0],iaTo,subject,body,iaHeaderTo,iaReplyTo,contentList);
		 log.info("sending mail from "+iaReplyTo[0]+" to "+toEmail.toString());
		 EmailLogRecord log = new EmailLogRecord();
			log.setRecipient(String.valueOf(student.getNumber()));
			log.setRecipientType("STUDENT");
			log.setEmailAddress(toAddress);
			log.setProgram("TUTSTULINK:MYUNISA");
			log.setSubject(subject);
			log.setEmailType("REASSIGN");
			log.setBody("Group " + groupNr + modeAbbr);
		TutorStudentGroupingDAO dao = new TutorStudentGroupingDAO();
		dao.insertEmailLog(log);
		 } catch (Exception e) {
		 	log.error("TutorStudentGrouping: send of email informing student of new group alloction details. "+e+" "+e.getMessage());
			e.printStackTrace();
		 }	
	}
	
	public void sendNewTutorNotificationToStudent(String studyUnit, String acadYear, Short semester, String semesterType,TutoringMode tutorMode,Person tutor, int groupNr, Student student){
		try {

			String tmpEmailFrom = ServerConfigurationService.getString("noReplyEmailFrom");
			String serverPath = ServerConfigurationService.getToolUrl();
			String toAddress=student.getContactInfo().getEmailAddress();
			//do not send email on localhost,dev or qa -default email to pretoj@unisa.ac.za	
			if (serverPath.contains("mydev") || serverPath.contains("myqa") || serverPath.contains("localhost")){
					toAddress="pretoj@unisa.ac.za";
			}	
			InternetAddress toEmail = new InternetAddress(toAddress);			
			InternetAddress iaTo[] = new InternetAddress[1];
			iaTo[0] = toEmail;
			InternetAddress iaHeaderTo[] = new InternetAddress[1];
			iaHeaderTo[0] = toEmail;
			InternetAddress iaReplyTo[] = new InternetAddress[1];
			iaReplyTo[0] = new InternetAddress(tmpEmailFrom);		

			/* Email to student informing him/her of re-assignment*/
			String modeAbbr = getTutorModeAbbreviation(tutorMode.getTutorMode());
			String subject = studyUnit + "-" + acadYear.substring(2, 4) + "-" + semesterType + ": New Tutor Details";
			String body = "<html> "+
		    "<body>Dear student," + "<br/><br/>"+
		    "The " + tutorMode.getTutorModeDesc() + " for " + studyUnit + " " + acadYear + "/" + semesterType + " has changed. Tuition for <b>Group " + groupNr + modeAbbr + "</b>" +
		    " is now led by " + tutor.getName() + ".<br/><br/>" +			
			"Please visit myUnisa (https://my.unisa.ac.za) to access your group site which is <b>" + studyUnit + "-" + acadYear.substring(2, 4) + "-" + semesterType + "-" + groupNr + modeAbbr + "</b>.<br/><br/> " +
			"Should you have any further queries regarding your group allocation, please post these within the<br/>" +
			"group site on myUnisa.<br/><br/>" +
			"Good luck with your studies,<br/>" +
			"myUnisa Support Team" +
			"</body>"+
			"</html>";
		
		  List<String> contentList = new ArrayList<String>();
		  contentList.add("Content-Type: text/html");  
		  
		 emailService = (EmailService)ComponentManager.get(EmailService.class); 
		 emailService.sendMail(iaReplyTo[0],iaTo,subject,body,iaHeaderTo,iaReplyTo,contentList);
		 log.info("sending mail from "+iaReplyTo[0]+" to "+toEmail.toString());
		 EmailLogRecord log = new EmailLogRecord();
			log.setRecipient(String.valueOf(student.getNumber()));
			log.setRecipientType("STUDENT");
			log.setEmailAddress(toAddress);
			log.setProgram("TUTSTULINK:MYUNISA");
			log.setSubject(subject);
			log.setEmailType("NEWTUTOR");
			log.setBody("Group " + groupNr + modeAbbr + ", Tutor :" + tutor.getPersonnelNumber());
		TutorStudentGroupingDAO dao = new TutorStudentGroupingDAO();
		dao.insertEmailLog(log);
		 } catch (Exception e) {
		 	log.error("TutorStudentGrouping: send of email informing student of new tutor details. "+e+" "+e.getMessage());
			e.printStackTrace();
		 }	
	}
	
	public void sendEmail(String toAddress, String subject, String body, String logMessage) {

		try {

			String tmpEmailFrom = ServerConfigurationService.getString("noReplyEmailFrom");
			String serverPath = ServerConfigurationService.getToolUrl();
			//do not send email on localhost,dev or qa -default email to pretoj@unisa.ac.za	
			if (serverPath.contains("mydev") || serverPath.contains("myqa") || serverPath.contains("localhost")){
					toAddress="pretoj@unisa.ac.za";
			}
			InternetAddress toEmail = new InternetAddress(toAddress);
			InternetAddress iaTo[] = new InternetAddress[1];
			iaTo[0] = toEmail;
			InternetAddress iaHeaderTo[] = new InternetAddress[1];
			iaHeaderTo[0] = toEmail;
			InternetAddress iaReplyTo[] = new InternetAddress[1];
			iaReplyTo[0] = new InternetAddress(tmpEmailFrom);
			
		  List<String> contentList = new ArrayList<String>();
		  contentList.add("Content-Type: text/html");  
		  
		 emailService = (EmailService)ComponentManager.get(EmailService.class); 
		 emailService.sendMail(iaReplyTo[0],iaTo,subject,body,iaHeaderTo,iaReplyTo,contentList);
		 log.info("sending mail from "+iaReplyTo[0]+" to "+toEmail.toString());
		 } catch (Exception e) {		 
		 	log.error("TutorStudentGrouping: " + logMessage +e+" "+e.getMessage());
		 	e.printStackTrace();
		 }
	}
	
	private void resetMainForm(TutorStudentGroupForm mainForm)
	throws Exception {	
		
		mainForm.setStudentNr("");			
		mainForm.setSemesterType("");
		mainForm.setSelectedTutor("");
		mainForm.setTutorStaffNumber("");
		mainForm.setTutorNetworkCode("");
		mainForm.setStudyUnit(null);
		mainForm.setTutor(null);
		mainForm.setTutoringMode(null);		
		mainForm.setListAvailableTutorMode(null);
		mainForm.setListAvailableTutorGroup(null);
		mainForm.setListTutorStudentGroup(null);
		mainForm.setListTutor(null);
		mainForm.setListChangeGroupTutor(null);
		mainForm.setIndexNrSelectedGroups(null);
		mainForm.setIndexNrselectedTutorGroupSwitch1(null);
		mainForm.setIndexNrselectedTutorGroupSwitch2(null);
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
	
	public String getTutorModeAbbreviation(String tutorMode){
		String abbreviation = "";
		abbreviation = tutorMode.substring(0, 1);
		
//		if (tutorMode.equalsIgnoreCase("ETUTOR")){
//			abbreviation="E";
//		}
//		if (tutorMode.equalsIgnoreCase("TEACH")){
//			abbreviation="T";
//		}
//		if (tutorMode.equalsIgnoreCase("FACE")){
//			abbreviation="F";
//		}
//		if (tutorMode.equalsIgnoreCase("SCIENCE")){
//			abbreviation="S";
//		}
		
		return abbreviation;
	}

}
