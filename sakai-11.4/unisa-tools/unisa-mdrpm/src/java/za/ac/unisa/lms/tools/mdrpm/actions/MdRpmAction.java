package za.ac.unisa.lms.tools.mdrpm.actions;

import java.util.ArrayList;
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
import org.apache.struts.util.LabelValueBean;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.tool.api.ToolManager;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserDirectoryService;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.email.api.EmailService;

import za.ac.unisa.lms.tools.mdrpm.dao.MdRpmDao;
import za.ac.unisa.lms.tools.mdrpm.forms.*;
import za.ac.unisa.lms.dao.Gencod;
import za.ac.unisa.lms.dao.StudentSystemGeneralDAO;
import za.ac.unisa.lms.dao.general.UserDAO;
import za.ac.unisa.lms.dao.general.PersonnelDAO;
import za.ac.unisa.lms.dao.general.ModuleDAO;
import za.ac.unisa.lms.domain.general.*;
import za.ac.unisa.utils.CoursePeriod;

public class MdRpmAction extends LookupDispatchAction {

	private SessionManager sessionManager;
	private UserDirectoryService userDirectoryService;
	private ToolManager toolManager;
	private EmailService emailService;

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
		map.put("initial", "initial");
		map.put("rpmAction", "rpmAction");
		map.put("titleMnt", "titleMnt");
		map.put("supervisorMnt", "supervisorMnt");
		map.put("button.display", "displayRpmList");
		map.put("button.proposeResult", "proposeResult");
		map.put("button.back", "prevStep");
		map.put("button.continue", "nextStep");
		map.put("button.addSupervisor", "searchSupervisor");
		map.put("button.addAssistant", "searchAssistant");		
		map.put("button.delete", "deleteSupervisor");
		map.put("saveSupervisors", "saveSupervisors");
		map.put("button.list", "searchStaff");
		map.put("signoff", "signoff");
		map.put("button.supevisors","getSupervisors");
		map.put("saveTitle", "saveTitle");
		map.put("supervisorHistory", "supervisorHistory");
		return map;
	}

	public ActionForward initial(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		MdRpmForm mdRpmForm = (MdRpmForm) form;
		ActionMessages messages = new ActionMessages();

		// Get user details, set in default action
		User user = null;

		sessionManager = (SessionManager) ComponentManager
				.get(SessionManager.class);
		userDirectoryService = (UserDirectoryService) ComponentManager
				.get(UserDirectoryService.class);

		Session currentSession = sessionManager.getCurrentSession();
		String userID = currentSession.getUserId();

		if (userID != null) {
			user = userDirectoryService.getUser(userID);
			mdRpmForm.setUserCode(userDirectoryService.getUserEid(userID));
		} else {
			return mapping.findForward("unknownuser");
		}

		if (!"lecturer".equalsIgnoreCase(user.getType())) {
			return mapping.findForward("userInvalid");
		}

		Person person = new Person();
		UserDAO userDao = new UserDAO();
		person = userDao.getUserFromSTAFF(mdRpmForm.getUserCode());  //debug remove comment
		//person = userDao.getUserFromSTAFF("WINGAHC");
		person = userDao.getUserFromSTAFF("VDPOLHM");
		//person = userDao.getUserFromSTAFF("VHEERB");

		if (person.getPersonnelNumber() == null) {
			return mapping.findForward("userInvalid");
		}
		
		mdRpmForm.setMyUnisaUser(person);
		College college = new College();
		MdRpmDao dao = new MdRpmDao();
		college = dao.getCollege(Short.parseShort(person.getDepartmentCode()));

		// Get RPM result types
		List<Gencod> list = new ArrayList<Gencod>();
		StudentSystemGeneralDAO genDao = new StudentSystemGeneralDAO();
		for (int i = 0; i < genDao.getGenCodes((short) 214, 1).size(); i++) {
			Gencod result = new Gencod();
			result = (Gencod) (genDao.getGenCodes((short) 214, 1).get(i));
			if (result.getCode().equalsIgnoreCase("NC")) {
				result.setEngDescription(result.getEngDescription()
						+ " - may re-register");
			}
			if (result.getCode().equalsIgnoreCase("DF")
					|| result.getCode().equalsIgnoreCase("NA")) {
				result.setEngDescription(result.getEngDescription()
						+ " - may not re-register");
			}

			list.add(i, result);
		}
		mdRpmForm.setResultTypeList(list);

		mdRpmForm.setCollege(college);
		mdRpmForm.setCriteriaList(initializeSearchCriteria());
		mdRpmForm.setResponseList(initializeReviewResponseOptions());
		//initialise values
		Qualification qualification = new Qualification();
		mdRpmForm.setQualification(qualification);
		StudyUnit studyUnit = new StudyUnit();
		mdRpmForm.setStudyUnit(studyUnit);
		List<LabelValueBean> supervisorList = new ArrayList<LabelValueBean>();
		supervisorList = dao.getSupervisors(college.getCode(), null,null,null);
		mdRpmForm.setSupervisorList(supervisorList);
		//set default list criteria
		if (mdRpmForm.getCriteriaSelected()==null || mdRpmForm.getCriteriaSelected().trim().equalsIgnoreCase("")){
			mdRpmForm.setCriteriaSelected("FORME");
			List<RpmListRecord> rpmList = new ArrayList<RpmListRecord>();
			rpmList = dao.getRpmList(mdRpmForm.getCollege().getCode(), mdRpmForm.getStudyUnit().getCode(), mdRpmForm.getQualification().getCode(),mdRpmForm.getQualification().getSpeciality().getCode(), mdRpmForm.getCriteriaSelected(),mdRpmForm.getStudentNr(),mdRpmForm.getSupervisor().getPersonnelNumber(),mdRpmForm.getMyUnisaUser().getPersonnelNumber());
			mdRpmForm.setRpmList(rpmList);

			return mapping.findForward("displayRpmList");
		}

		return mapping.findForward("inputRpmList");
	}
	
	public ActionForward searchStaff(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			
		MdRpmForm mdRpmForm = (MdRpmForm) form;
		ActionMessages messages = new ActionMessages();
		
		if ("".equals(mdRpmForm.getSearchNo()) && "".equals(mdRpmForm.getSearchSurname())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
						"Enter either a staff number or a surname."));
			addErrors(request, messages);
			return mapping.findForward("searchForward");
		}
		
		//add test to check for a numberic staff number
		if (!mdRpmForm.getSearchNo().trim().equalsIgnoreCase("")){
			if (!isInteger(mdRpmForm.getSearchNo())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
							"The staff number must be numeric."));
				addErrors(request, messages);
				return mapping.findForward("searchForward");
			}
		}
		
		MdRpmDao dao = new MdRpmDao();
		ArrayList<LabelValueBean> list = new ArrayList<LabelValueBean>();
		list=dao.getStaffList(mdRpmForm.getSearchNo(), mdRpmForm.getSearchSurname());
		if(list.isEmpty()){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
						"No staff members were found for the search criteria."));
			addErrors(request, messages);
			return mapping.findForward("searchForward");
		}else{
			//Add  list to request
			request.setAttribute("staffList", list);
		}
		
		return mapping.findForward("searchForward");
	}
	
	public ActionForward searchSupervisor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			
		MdRpmForm mdRpmForm = (MdRpmForm) form;		
		
		mdRpmForm.setAddSupervisorType("Supervisor");
		mdRpmForm.setSearchNo("");
		mdRpmForm.setSearchSurname("");
		
		return mapping.findForward("searchStaff");
	}
	
	public ActionForward searchAssistant(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			
		MdRpmForm mdRpmForm = (MdRpmForm) form;
		
		mdRpmForm.setAddSupervisorType("Assistant");
		mdRpmForm.setSearchNo("");
		mdRpmForm.setSearchSurname("");
		
		return mapping.findForward("searchStaff");
	}
	
	public ActionForward deleteSupervisor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			
		MdRpmForm mdRpmForm = (MdRpmForm) form;
		ActionMessages messages = new ActionMessages();		
	
		if (mdRpmForm.getIndexSelectedRemoveSupervisor()==null || mdRpmForm.getIndexSelectedRemoveSupervisor().length==0){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"message.generalmessage", "Please select a supervisor to delete."));
		}

		if (!messages.isEmpty()) {
			addErrors(request, messages);
			return mapping.findForward("supervisorMnt");
		}
		
		
		List<String> deleteList = new ArrayList<String>();
		for (int i=0; i < mdRpmForm.getIndexSelectedRemoveSupervisor().length; i++){
			Promotor promotor = new Promotor();
			promotor = (Promotor)mdRpmForm.getRpm().getPromotorList().get(Integer.parseInt(mdRpmForm.getIndexSelectedRemoveSupervisor()[i]));
			deleteList.add(promotor.getPerson().getPersonnelNumber());			
		}
		
		
		for (int i=0; i < deleteList.size(); i++){
		String persno = (String)deleteList.get(i);
		int listSize = mdRpmForm.getRpm().getPromotorList().size();
			for (int j=0; j < listSize; j++) {
				Promotor promotor = new Promotor();
				promotor = (Promotor)mdRpmForm.getRpm().getPromotorList().get(j);
				if (promotor.getPerson().getPersonnelNumber().equalsIgnoreCase(persno)){
					mdRpmForm.getRpm().getPromotorList().remove(j);	
					j=listSize;
				}
			}
		}			
		
		mdRpmForm.setIndexSelectedRemoveSupervisor(null);
		
		mdRpmForm.setSupervisorAmendmentHistoryAvailable("Y");
		
		return mapping.findForward("supervisorMnt");
	}
	
	public void addSupervisor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			
		MdRpmForm mdRpmForm = (MdRpmForm) form;		
		
		Promotor promotor = new Promotor();
		if (mdRpmForm.getAddSupervisorType().equalsIgnoreCase("Supervisor")){
			promotor.setIsSupervisor(true);
		}else{
			promotor.setIsSupervisor(false);
		}
		
		Person person = new Person();
		PersonnelDAO dao = new PersonnelDAO();
		String persNr = mdRpmForm.getSearchResult().substring(0, 8);
		person = dao.getPerson(Integer.parseInt(persNr));
		promotor.setPerson(person);
		
		mdRpmForm.getRpm().getPromotorList().add(promotor);
	}
	
	public ActionForward nextStep(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
		MdRpmForm mdRpmForm = (MdRpmForm) form;
		
			boolean passValidation=false;
			
			if (mdRpmForm.getCurrentPage().equalsIgnoreCase("proposeResult")){
				passValidation= confirmSignoff(mapping,form,request,response);
				if (passValidation){
					return mapping.findForward("confirmSignoff");
				}else{
					return mapping.findForward("proposeResult");
				}
			} else if (mdRpmForm.getCurrentPage().equalsIgnoreCase("reviewResult")){
				passValidation= confirmSignoff(mapping,form,request,response);
				if (passValidation){
					return mapping.findForward("confirmSignoff");
				}else{
					return mapping.findForward("reviewResult");
				}
			} else if (mdRpmForm.getCurrentPage().equalsIgnoreCase("reviewReferBack")){
				passValidation= confirmSignoff(mapping,form,request,response);
				if (passValidation){
					return mapping.findForward("confirmSignoff");
				}else{
					return mapping.findForward("reviewReferBack");
				}
			} else if (mdRpmForm.getCurrentPage().equalsIgnoreCase("searchStaff")){
				addSupervisor(mapping, mdRpmForm, request, response);
				return mapping.findForward("supervisorMnt");
			}
			
			return mapping.findForward("displayRpmList");			
			
	}
	
	public ActionForward prevStep(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
		MdRpmForm mdRpmForm = (MdRpmForm) form;
		
			String prevPage="";
			
			if (mdRpmForm.getCurrentPage().equalsIgnoreCase("proposeResult")  || 
					mdRpmForm.getCurrentPage().equalsIgnoreCase("reviewResult") || 
					mdRpmForm.getCurrentPage().equalsIgnoreCase("viewRpm") ||
					mdRpmForm.getCurrentPage().equalsIgnoreCase("reviewReferBack")){
				prevPage = "displayRpmList"	;
			}	
			if (mdRpmForm.getCurrentPage().equalsIgnoreCase("titleMnt") ||
			mdRpmForm.getCurrentPage().equalsIgnoreCase("supervisorMnt")){				
				if (!mdRpmForm.getMntCalledFrom().equalsIgnoreCase("displayRpmList")){
					MdRpmDao dao = new MdRpmDao();
					String title = dao.getDissertationTitle(mdRpmForm.getRpm().getStudent().getNumber(), mdRpmForm.getRpm().getStudyunit().getCode());
					mdRpmForm.getRpm().setTitle(title);
					//get promotor detail
					List<Promotor> promotorList = new ArrayList<Promotor>();
					promotorList = dao.getPromotorList(mdRpmForm.getRpm().getStudent().getNumber(),mdRpmForm.getRpm().getStudyunit().getCode());		
					mdRpmForm.getRpm().setPromotorList(promotorList);
					
				}
				prevPage=mdRpmForm.getMntCalledFrom();	
				
			}
			if (mdRpmForm.getCurrentPage().equalsIgnoreCase("confirmSignoff")){
				if (mdRpmForm.getTracking().getCurrentLevel().equalsIgnoreCase("Sup")){
					if (mdRpmForm.getTracking().getStatusCode()!=null && mdRpmForm.getTracking().getStatusCode().equalsIgnoreCase("RW")){
						prevPage="reviewReferBack";
					}else{
						prevPage = "proposeResult"	;
					}
				}else{
					prevPage = "reviewResult"	;
				}				
			}	
			if (mdRpmForm.getCurrentPage().equalsIgnoreCase("searchStaff") || 
					mdRpmForm.getCurrentPage().equalsIgnoreCase("supervisorHistory")){
				prevPage = "supervisorMnt"	;
			}
			return mapping.findForward(prevPage);
	}
	
	Boolean confirmSignoff(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		boolean passValidation=true;
		
		MdRpmForm mdRpmForm = (MdRpmForm) form;
		ActionMessages messages = new ActionMessages();
		
		boolean supervisorFound=false;
		for (int i=0; i < mdRpmForm.getRpm().getPromotorList().size(); i++){
			Promotor promotor = new Promotor();
			promotor = (Promotor) mdRpmForm.getRpm().getPromotorList().get(i);
			if (promotor.getIsSupervisor()==true){
				supervisorFound=true;
			}
		}
		if (mdRpmForm.getRpm().getTitle().isEmpty()){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"message.generalmessage", 
					"The title must be captured before you can sign-off. The initial title must be entered on function 168 together with the student dissertation information. Please contact your M & D coordinator for assistance."));
			passValidation=false;
		}
		if (!supervisorFound){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"message.generalmessage", 
					"At least one supervisor must be captured before you can signoff. Please click on Add supervisor(s) link or on one of the assistant supervisors to capture the supervisor."));
			passValidation=false;
		}
		if (mdRpmForm.getCurrentPage().equalsIgnoreCase("proposeResult") || mdRpmForm.getCurrentPage().equalsIgnoreCase("reviewReferBack")){
			if (mdRpmForm.getTracking().getProposedResult().getCode()==null || mdRpmForm.getTracking().getProposedResult().getCode().trim().equalsIgnoreCase("")){
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"message.generalmessage", 
						"Please note that you cannot continue without indicating a result. Select the appropriate result before continuing or click on back to return to the list."));
				passValidation=false;
			}else{
				if (mdRpmForm.getTracking().getProposedResult().getCode().equalsIgnoreCase("DF")){
					if (mdRpmForm.getTracking().getComment()==null || mdRpmForm.getTracking().getComment().trim().equalsIgnoreCase("")){
						messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
								"message.generalmessage", "You have selected a fail result for this candidate. Please indicate your motivation in the comment box below."));
						passValidation=false;
					}				
				}
			}	
		}
		if (mdRpmForm.getCurrentPage().equalsIgnoreCase("reviewResult")){
			if (mdRpmForm.getSelectedResponse()==null || mdRpmForm.getSelectedResponse().trim().equalsIgnoreCase("")){
					messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"message.generalmessage", "Please indicate your response to the proposed result."));
					passValidation=false;
			}else if (mdRpmForm.getSelectedResponse().equalsIgnoreCase("DISAGREE")){
				if (mdRpmForm.getTracking().getComment()==null || mdRpmForm.getTracking().getComment().trim().equalsIgnoreCase("")){
					messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"message.generalmessage", "You have indicated that your disagree with the proposed result, please indicate your motivation in the comment box below."));
					passValidation=false;
				}else{
					//refer back to initiating staff
					mdRpmForm.getTracking().setAssignedToLevel("Sup");
				}
			}
		}
		
		MdRpmDao dao = new MdRpmDao();
		List<LabelValueBean> supervisorList = new ArrayList<LabelValueBean>();
		supervisorList = dao.getStudentSupervisors(mdRpmForm.getRpm().getStudent().getNumber(), mdRpmForm.getRpm().getStudyunit().getCode());		
		
		List<Person> signoffStaffList = new ArrayList<Person>();
		
		if (mdRpmForm.getTracking().getCurrentLevel().equalsIgnoreCase("F") && mdRpmForm.getSelectedResponse().equalsIgnoreCase("AGREE")){
			//Final signoff - no assign to level or signoff staff
		}else{	
			if (mdRpmForm.getSelectedResponse()!=null && mdRpmForm.getSelectedResponse().equalsIgnoreCase("DISAGREE")){
				//refer back to supervisor for signoff
				//default to originator
				PersonnelDAO personDao = new PersonnelDAO();
				for (int i = 0; i < supervisorList.size(); i ++){
					Person supervisor = new Person();
					supervisor.setPersonnelNumber(((LabelValueBean)supervisorList.get(i)).getValue());
					supervisor =  personDao.getPerson(Integer.parseInt(supervisor.getPersonnelNumber()));
					signoffStaffList.add(supervisor);					
				}	
				if (signoffStaffList.size()==0){
					messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"message.generalmessage", "Their is no supervisor assigned to this student to refer this result back to for review."));
					passValidation=false; 
				}
			}else{
				//refer forward for signoff
				signoffStaffList = getSignOffStaffForLevel(mdRpmForm.getTracking().getAssignedToLevel(), mdRpmForm.getRpm().getRoutingList());	
				if (signoffStaffList.size()==0){
					messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"message.generalmessage", "Their is no person on the qualification signoff route defined to signoff this request."));
					passValidation=false; 
				}
			}
			
			
		}		
		
		
		if (passValidation){			
			String supervisors ="";			
			for (int j=0; j < supervisorList.size(); j++){
				if (supervisors.equalsIgnoreCase("")){
					supervisors = ((LabelValueBean)supervisorList.get(j)).getLabel();
				}else{
					supervisors = supervisors + ", " + ((LabelValueBean)supervisorList.get(j)).getLabel();
				}					
			}		
			
			if (mdRpmForm.getTracking().getProposedResult().getCode()!=null || mdRpmForm.getTracking().getProposedResult().getCode().trim().equalsIgnoreCase("")){
				for (int i=0; i < mdRpmForm.getResultTypeList().size();i++){
					Gencod result = new Gencod();
					result = (Gencod) mdRpmForm.getResultTypeList().get(i);
					if (result.getCode().equalsIgnoreCase(mdRpmForm.getTracking().getProposedResult().getCode())){
						mdRpmForm.getTracking().getProposedResult().setEngDescription(result.getEngDescription());
						i = mdRpmForm.getResultTypeList().size();
					}
				}
			}
			
			
//			request.setAttribute("proposedResult", proposedResult);	
//			request.setAttribute("supervisors", supervisors);
			mdRpmForm.setSupervisorsStr(supervisors);
			mdRpmForm.setSignoffStaffList(signoffStaffList);
//			request.setAttribute("signoffStaffList", signoffStaffList);
			
			if (signoffStaffList.size()==1){
				mdRpmForm.setSelectedApprover(((Person)(signoffStaffList.get(0))).getPersonnelNumber());
			}
			
		}else{
			addErrors(request, messages);	
		}
					
		return passValidation;
	}
	
	public ActionForward signoff(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
		MdRpmForm mdRpmForm = (MdRpmForm) form;
		ActionMessages messages = new ActionMessages();
		
		MdRpmDao dao = new MdRpmDao();
		List<RpmListRecord> rpmList = new ArrayList<RpmListRecord>();
		Person person = new Person();
		
		//Do validation
		if (mdRpmForm.getTracking().getCurrentLevel().equalsIgnoreCase("F") && mdRpmForm.getSelectedResponse().equalsIgnoreCase("AGREE")){
			//Final signoff - no further signoff - no notification email to be sent.
		}else{
			if (mdRpmForm.getSelectedApprover()==null || mdRpmForm.getSelectedApprover().trim().equalsIgnoreCase("")){
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"message.generalmessage", 
						"Please select a person to whom a notification of the signoff request must be send."));
			}else{
				PersonnelDAO personDao = new PersonnelDAO();
				person= personDao.getPersonnelFromSTAFF(Integer.parseInt(mdRpmForm.getSelectedApprover()));
			}
		}
		//re-read and check if supervisor did not change before update - at least one supervisor must exists. 
		//re-read title and check if title did not change since previous display.
		//get current title
		String title = dao.getDissertationTitle(mdRpmForm.getRpm().getStudent().getNumber(), mdRpmForm.getRpm().getStudyunit().getCode());
		if (title == null){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"message.generalmessage", 
					"The title has change since last read from database, the title must be captured before you can signoff."));
		}else if (!mdRpmForm.getRpm().getTitle().trim().equalsIgnoreCase(title)){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"message.generalmessage", 
					"The title has change since last read from database, please confirm that you still agree with changed title before signing off."));
			mdRpmForm.getRpm().setTitle(title);
		}
		if (!messages.isEmpty()) {
			addErrors(request, messages);			
			return mapping.findForward("confirmSignoff");
		}
		
		//create mdtrac record
		//create mdworklist record		
		mdRpmForm.getTracking().setAssignedToPerson(person);
		
		if (mdRpmForm.getTracking().getCurrentLevel().equalsIgnoreCase("Sup")){
			mdRpmForm.getTracking().setStatusCode("RS");
		}else{
			if (mdRpmForm.getSelectedResponse().equalsIgnoreCase("AGREE")){
				if (mdRpmForm.getTracking().getCurrentLevel().equals("F")){
					mdRpmForm.getTracking().setStatusCode("FS");
				}else{
					mdRpmForm.getTracking().setStatusCode("RS");
				}				
			}else{
				mdRpmForm.getTracking().setStatusCode("RW");
			}
		}
		
		dao.recordSignoff(mdRpmForm.getRpm(),mdRpmForm.getTracking());
		
		if (mdRpmForm.getTracking().getStatusCode().equalsIgnoreCase("RS")){
			sendSignOffNotificationEmail(mdRpmForm.getRpm(),mdRpmForm.getMyUnisaUser(),mdRpmForm.getTracking().getAssignedToPerson());
		}
		if (mdRpmForm.getTracking().getStatusCode().equalsIgnoreCase("RW")){
			sendReviewNotificationEmail(mdRpmForm.getRpm(),mdRpmForm.getMyUnisaUser(),mdRpmForm.getTracking().getAssignedToPerson());
		}
		
		rpmList = dao.getRpmList(mdRpmForm.getCollege().getCode(), mdRpmForm.getStudyUnit().getCode(), mdRpmForm.getQualification().getCode(),mdRpmForm.getQualification().getSpeciality().getCode(),mdRpmForm.getCriteriaSelected(),mdRpmForm.getStudentNr(),mdRpmForm.getSupervisor().getPersonnelNumber(),mdRpmForm.getMyUnisaUser().getPersonnelNumber());
		mdRpmForm.setRpmList(rpmList);

		return mapping.findForward("displayRpmList");
	}
	
	public ActionForward saveTitle(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
		MdRpmForm mdRpmForm = (MdRpmForm) form;
		ActionMessages messages = new ActionMessages();
		
		MdRpmDao dao = new MdRpmDao();	
		
		//get current title
		String title = dao.getDissertationTitle(mdRpmForm.getRpm().getStudent().getNumber(), mdRpmForm.getRpm().getStudyunit().getCode());
		
		//title may not be spaces
		if (mdRpmForm.getRpm().getTitle() == null || mdRpmForm.getRpm().getTitle().trim().equalsIgnoreCase("")){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"message.generalmessage", 
					"Please enter the title, the title is mandatory."));
			addErrors(request, messages);
			return mapping.findForward("titleMnt");
		}
		
		//check that title differs from current title
		if (title.trim().equalsIgnoreCase(mdRpmForm.getRpm().getTitle())){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"message.generalmessage", 
					"The title did not change, click on back if you do not want to change the title."));
			addErrors(request, messages);
			return mapping.findForward("titleMnt");
		}
				
		dao.recordTitleChange(mdRpmForm.getRpm(),mdRpmForm.getTracking());
		
		List<RpmListRecord> rpmList = new ArrayList<RpmListRecord>();
		rpmList = dao.getRpmList(mdRpmForm.getCollege().getCode(), mdRpmForm.getStudyUnit().getCode(), mdRpmForm.getQualification().getCode(),mdRpmForm.getQualification().getSpeciality().getCode(),mdRpmForm.getCriteriaSelected(),mdRpmForm.getStudentNr(),mdRpmForm.getSupervisor().getPersonnelNumber(),mdRpmForm.getMyUnisaUser().getPersonnelNumber());
		mdRpmForm.setRpmList(rpmList);

		return mapping.findForward(mdRpmForm.getMntCalledFrom());
	}
	
	public ActionForward titleMnt(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		MdRpmForm mdRpmForm = (MdRpmForm) form;	
		ActionMessages messages = new ActionMessages();
		
		MdRpmDao dao = new MdRpmDao();
		String calledFrom = request.getParameter("calledFrom").trim();
		mdRpmForm.setMntCalledFrom(calledFrom);
		
		if(mdRpmForm.getMntCalledFrom().equalsIgnoreCase("displayRpmList")){
			String studyUnitCode=(request.getParameter("module")).trim();		
			String qualCode=(request.getParameter("qualCode")).trim();
			String specCode=(request.getParameter("specCode")).trim();
			int studentNr =Integer.parseInt((request.getParameter("studentNumber")).trim());			
			
			RpmRecord rpm = new RpmRecord();
			TrackRecord tracking = new TrackRecord();
			
			//get student detail
			Student student = new Student();
			student = dao.getStudent(studentNr);
			rpm.setStudent(student);	
			
			//get qualification detail
			Qualification qualification = new Qualification();
			qualification = dao.getQualification(qualCode,specCode);
			rpm.setQualification(qualification);
			
			//get module detail
			ModuleDAO moduleDao = new ModuleDAO();
			StudyUnit studyunit = new StudyUnit();
			studyunit = moduleDao.getStudyUnit(studyUnitCode);
			rpm.setStudyunit(studyunit);
			
			//get promotor detail
			List<Promotor> promotorList = new ArrayList<Promotor>();
			promotorList = dao.getPromotorList(studentNr,studyUnitCode);		
			rpm.setPromotorList(promotorList);
			
			tracking.setComment("");
			tracking.setCurrentPerson(mdRpmForm.getMyUnisaUser());			
			
			String title = dao.getDissertationTitle(studentNr,studyUnitCode);			
			rpm.setTitle(title);
			
			mdRpmForm.setRpm(rpm);
			mdRpmForm.setTracking(tracking);
		}
		
		//check if studis exists
		if (!dao.existsStudis(mdRpmForm.getRpm().getStudent().getNumber(),mdRpmForm.getRpm().getStudyunit().getCode())){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"message.generalmessage", 
					"The initial title must be entered on function 168 together with the student dissertation information. Please contact your M & D coordinator for assistance."));
			addErrors(request, messages);
			return mapping.findForward(mdRpmForm.getMntCalledFrom());
		}
		
		List<TitleHistoryRecord> titleHistoryList = new ArrayList<TitleHistoryRecord>();
		titleHistoryList = dao.getTitleHistory(mdRpmForm.getRpm().getStudent().getNumber(),mdRpmForm.getRpm().getStudyunit().getCode());
		
		request.setAttribute("titleHistoryList", titleHistoryList);
		return mapping.findForward("titleMnt");
		
	}	
	
	public ActionForward supervisorMnt(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		MdRpmForm mdRpmForm = (MdRpmForm) form;
		ActionMessages messages = new ActionMessages();
		
		MdRpmDao dao = new MdRpmDao();
		String calledFrom = request.getParameter("calledFrom").trim();
		mdRpmForm.setMntCalledFrom(calledFrom);
		if(mdRpmForm.getMntCalledFrom().equalsIgnoreCase("displayRpmList")){
			String studyUnitCode=(request.getParameter("module")).trim();		
			String qualCode=(request.getParameter("qualCode")).trim();
			String specCode=(request.getParameter("specCode")).trim();
			int studentNr =Integer.parseInt((request.getParameter("studentNumber")).trim());		

			RpmRecord rpm = new RpmRecord();
			TrackRecord tracking = new TrackRecord();
			
			//get student detail
			Student student = new Student();
			student = dao.getStudent(studentNr);
			rpm.setStudent(student);	
			
			//get qualification detail
			Qualification qualification = new Qualification();
			qualification = dao.getQualification(qualCode,specCode);
			rpm.setQualification(qualification);
			
			//get module detail
			ModuleDAO moduleDao = new ModuleDAO();
			StudyUnit studyunit = new StudyUnit();
			studyunit = moduleDao.getStudyUnit(studyUnitCode);
			rpm.setStudyunit(studyunit);
			
			//get promotor detail
			List<Promotor> promotorList = new ArrayList<Promotor>();
			promotorList = dao.getPromotorList(studentNr,studyUnitCode);		
			rpm.setPromotorList(promotorList);
			
			tracking.setComment("");
			tracking.setCurrentPerson(mdRpmForm.getMyUnisaUser());
			
			mdRpmForm.setRpm(rpm);
			mdRpmForm.setTracking(tracking);
		}		
		
		List<SupervisorHistoryRecord> supervisorHistoryList = new ArrayList<SupervisorHistoryRecord>();
		supervisorHistoryList = dao.getSupervisorHistory(mdRpmForm.getRpm().getStudent().getNumber(),mdRpmForm.getRpm().getStudyunit().getCode());
		
		mdRpmForm.setSupervisorAmendmentHistoryAvailable("N");
		if (!supervisorHistoryList.isEmpty()){		
			mdRpmForm.setSupervisorAmendmentHistoryAvailable("Y");
		}
	
		return mapping.findForward("supervisorMnt");
		
	}	
	
	public ActionForward supervisorHistory(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		MdRpmForm mdRpmForm = (MdRpmForm) form;
		ActionMessages messages = new ActionMessages();
		
		MdRpmDao dao = new MdRpmDao();
		List<SupervisorHistoryRecord> supervisorHistoryList = new ArrayList<SupervisorHistoryRecord>();
		supervisorHistoryList = dao.getSupervisorHistory(mdRpmForm.getRpm().getStudent().getNumber(),mdRpmForm.getRpm().getStudyunit().getCode());
		
		request.setAttribute("supervisorHistoryList", supervisorHistoryList);
		return mapping.findForward("supervisorHistory");
		
	}	
	
	public ActionForward saveSupervisors(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
		MdRpmForm mdRpmForm = (MdRpmForm) form;
		ActionMessages messages = new ActionMessages();
		
		MdRpmDao dao = new MdRpmDao();	
						
		//get current supervisors
		List<Promotor> promotorList = new ArrayList<Promotor>();
		promotorList = dao.getPromotorList(mdRpmForm.getRpm().getStudent().getNumber(), mdRpmForm.getRpm().getStudyunit().getCode());
		
		boolean studentSupervisor = false;
		//check that at least one supervisor has been specified.	
		if (mdRpmForm.getRpm().getPromotorList()==null || mdRpmForm.getRpm().getPromotorList().isEmpty()){
			//nothing
		}else{
			for (int i=0; i < mdRpmForm.getRpm().getPromotorList().size(); i++){
				Promotor promotor = new Promotor();
				promotor = (Promotor)mdRpmForm.getRpm().getPromotorList().get(i);
				if (promotor.getIsSupervisor()==true){
					studentSupervisor=true;
				}
			}
		}
		
		if (!studentSupervisor) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"message.generalmessage", 
					"At least one supervisor must be specified for this student."));
		}
			
		//check that supervisors differ from current supervisors
		if (promotorList.size()==mdRpmForm.getRpm().getPromotorList().size()){			
			//possibility exists that lists has the same value
			boolean promotorListsEqual = true;
			for (int i=0; i < mdRpmForm.getRpm().getPromotorList().size(); i++){
				Promotor newPromotor = new Promotor();
				newPromotor = (Promotor)mdRpmForm.getRpm().getPromotorList().get(i);
				boolean promotorFound = false;
				for(int j=0; j < promotorList.size(); j++) {
					//check if promotor is in current promotor list
					Promotor currentPromotor = new Promotor();
					currentPromotor = (Promotor)promotorList.get(j);					
					if (newPromotor.getPerson().getPersonnelNumber().equalsIgnoreCase(currentPromotor.getPerson().getPersonnelNumber()) 
							&&  newPromotor.getIsSupervisor()==currentPromotor.getIsSupervisor()){
						promotorFound = true;
						j = promotorList.size();
					}					
				}
				if (!promotorFound){
					promotorListsEqual = false;
					i = mdRpmForm.getRpm().getPromotorList().size();
				}
			}
			if(promotorListsEqual){
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"message.generalmessage", 
						"The student's supervisors did not change, click on back if you do not want to change the supervisors for the student."));
			}
		}	
		
		if (!messages.isEmpty()) {
			addErrors(request, messages);
			return mapping.findForward("supervisorMnt");
		}	
		
		dao.recordSupervisorChange(mdRpmForm.getRpm(),mdRpmForm.getTracking());
	
		List<RpmListRecord> rpmList = new ArrayList<RpmListRecord>();
		rpmList = dao.getRpmList(mdRpmForm.getCollege().getCode(), mdRpmForm.getStudyUnit().getCode(), mdRpmForm.getQualification().getCode(),mdRpmForm.getQualification().getSpeciality().getCode(),mdRpmForm.getCriteriaSelected(),mdRpmForm.getStudentNr(),mdRpmForm.getSupervisor().getPersonnelNumber(),mdRpmForm.getMyUnisaUser().getPersonnelNumber());
		mdRpmForm.setRpmList(rpmList);
		
		mdRpmForm.setSupervisorAmendmentHistoryAvailable("Y");

		return mapping.findForward(mdRpmForm.getMntCalledFrom());
	}
	
	public ActionForward rpmAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		MdRpmForm mdRpmForm = (MdRpmForm) form;
		ActionMessages messages = new ActionMessages();
		
		String studyUnitCode=(request.getParameter("module")).trim();		
		String qualCode=(request.getParameter("qualCode")).trim();
		String specCode=(request.getParameter("specCode")).trim();
		int studentNr =Integer.parseInt((request.getParameter("studentNumber")).trim());
		
		MdRpmDao dao = new MdRpmDao();
		RpmRecord rpm = new RpmRecord();
		TrackRecord tracking = new TrackRecord();
		
		//get student detail
		Student student = new Student();
		student = dao.getStudent(studentNr);
		rpm.setStudent(student);	
		
		//get qualification detail
		Qualification qualification = new Qualification();
		qualification = dao.getQualification(qualCode,specCode);
		rpm.setQualification(qualification);
		
		//get module detail
		ModuleDAO moduleDao = new ModuleDAO();
		StudyUnit studyunit = new StudyUnit();
		studyunit = moduleDao.getStudyUnit(studyUnitCode);
		rpm.setStudyunit(studyunit);
		
		//get signoff route for qualification
		List<Qsprout> routingList = new ArrayList<Qsprout>();
		routingList = dao.getRoutingList(qualCode,specCode);
		rpm.setRoutingList(routingList);
		
		//get promotor detail
		List<Promotor> promotorList = new ArrayList<Promotor>();
		promotorList = dao.getPromotorList(studentNr,studyUnitCode);		
		rpm.setPromotorList(promotorList);
		
		tracking.setComment("");
		
		String title = dao.getDissertationTitle(studentNr,studyUnitCode);
		rpm.setTitle(title);
		
		for (int i=0; i < mdRpmForm.getRpmList().size(); i++){
			RpmListRecord listItem = new RpmListRecord();
			listItem = (RpmListRecord) mdRpmForm.getRpmList().get(i);
			if (listItem.getStudentNr()==studentNr && listItem.getStudyUnit().trim().equalsIgnoreCase(studyUnitCode)){
				mdRpmForm.setSelectedListItem(listItem);
				i = mdRpmForm.getRpmList().size();
			}
		}
		
		tracking.setComment("");
		tracking.setCurrentPerson(mdRpmForm.getMyUnisaUser());
		
		if (mdRpmForm.getSelectedListItem().getWorkListItem()==null ||
				mdRpmForm.getSelectedListItem().getWorkListItem().getStatus()==null || 
						mdRpmForm.getSelectedListItem().getWorkListItem().getStatus().trim().equalsIgnoreCase("")){		
			//no worklist item exists
			Gencod proposedResult = new Gencod();
			tracking.setProposedResult(proposedResult);
			tracking.setCurrentLevel("Sup");			
			tracking.setAssignedToLevel("1");
			tracking.setSeqNr(1);
		}else{
			//existing worklist item with tracking details
			String resultCode = mdRpmForm.getSelectedListItem().getProposedResult();
			for (int i=0; i < mdRpmForm.getResultTypeList().size();i++){
				Gencod gencod = new Gencod();
				gencod = (Gencod)mdRpmForm.getResultTypeList().get(i);
				if (gencod.getCode().equalsIgnoreCase(resultCode)){
					Gencod proposedResult = new Gencod();
					proposedResult.setCode(gencod.getCode());
					proposedResult.setEngDescription(gencod.getEngDescription());
					tracking.setProposedResult(proposedResult);
					i=mdRpmForm.getResultTypeList().size();
				}
			}
			tracking.setCurrentLevel(mdRpmForm.getSelectedListItem().getWorkListItem().getReferToLevel());
			String nextLevel="";
			if (isInteger(tracking.getCurrentLevel())){
				nextLevel = getNextSignOffLevel(Integer.parseInt(tracking.getCurrentLevel()),routingList);
			}else if (mdRpmForm.getSelectedListItem().getWorkListItem().getReferToLevel().equalsIgnoreCase("Sup")){
				nextLevel = "1";
			}			
			tracking.setAssignedToLevel(nextLevel);
			tracking.setSeqNr(mdRpmForm.getSelectedListItem().getWorkListItem().getSeqNr());
			tracking.setStatusCode(mdRpmForm.getSelectedListItem().getWorkListItem().getTrackingStatus());
			//get tracking records
			List<TrackRecord> trackingList = new ArrayList<TrackRecord>();
			trackingList = dao.getRPMTrackingList(studentNr,studyUnitCode,tracking.getSeqNr(), mdRpmForm.getResultTypeList());
			
			rpm.setTrackingList(trackingList);
			
		}
		mdRpmForm.setRpm(rpm);
		mdRpmForm.setTracking(tracking);
		boolean userOnCurrentLevel = false;
		
		if (tracking.getCurrentLevel().equalsIgnoreCase("Sup")){
			//check if myUnisa user is a supervisor for this rpm
			//check if user is a supervisor for student
			for (int i=0; i < promotorList.size(); i ++){
				Promotor promotor = new Promotor();
				promotor = (Promotor)promotorList.get(i);
				if (promotor.getIsSupervisor() && promotor.getPerson().getPersonnelNumber().equalsIgnoreCase(mdRpmForm.getMyUnisaUser().getPersonnelNumber())){
					userOnCurrentLevel=true;
				}
			}			
		}else{
			//check user is on routinglist for the current level
			for (int i=0; i < mdRpmForm.getRpm().getRoutingList().size(); i++){
				Qsprout qsprout = new Qsprout();
				qsprout = (Qsprout) mdRpmForm.getRpm().getRoutingList().get(i);
				if (qsprout.getLevel().equalsIgnoreCase(tracking.getCurrentLevel())){
					if (qsprout.getPerson().getPersonnelNumber().equalsIgnoreCase(mdRpmForm.getMyUnisaUser().getPersonnelNumber())){
						userOnCurrentLevel = true;
						i = mdRpmForm.getRpm().getRoutingList().size();
					}
				}
			}
		}
		
		
		if (userOnCurrentLevel){
			if (tracking.getCurrentLevel().equalsIgnoreCase("Sup")){
				if (tracking.getStatusCode()!= null && tracking.getStatusCode().equalsIgnoreCase("RW")){ //review refer back result
					mdRpmForm.setCurrentResult(mdRpmForm.getTracking().getProposedResult());
					return mapping.findForward("reviewReferBack");
				}else{
					return mapping.findForward("proposeResult");
				}				
			}else{
				mdRpmForm.setSelectedResponse("");
				return mapping.findForward("reviewResult");
			}
		}else{
			//Determine info message to be displayed
			mdRpmForm.setMntAllowed("Y");
			if (mdRpmForm.getSelectedListItem().getWorkListItem().getStatus()!=null && mdRpmForm.getSelectedListItem().getWorkListItem().getStatus().equalsIgnoreCase("FINAL")){
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"message.generalmessage", 
						"This student's RPM result has been finalized."));
				addMessages(request, messages);
				mdRpmForm.setMntAllowed("N");
			}
			if (tracking.getCurrentLevel()!=null && tracking.getCurrentLevel().equalsIgnoreCase("Sup") && (tracking.getStatusCode()==null || tracking.getStatusCode().trim().equalsIgnoreCase(""))){
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"message.generalmessage", 
						"Only the student's supervisor may propose the initial RPM result."));
				addMessages(request, messages);
			} else if (tracking.getCurrentLevel()!=null && tracking.getCurrentLevel().equalsIgnoreCase("Sup") && tracking.getStatusCode().trim().equalsIgnoreCase("RW")){
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"message.generalmessage", 
						"Only the student's supervisor may review a RPM result referred back by a lecturer."));
				addMessages(request, messages);
			} else if (tracking.getCurrentLevel()!=null && tracking.getCurrentLevel().equalsIgnoreCase("F")){
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"message.generalmessage", 
						"This RPM result is awaiting final signoff, only persons on the Final level of the RPM signoff route may do the final signoff."));
				addMessages(request, messages);
			} else if (tracking.getCurrentLevel()!=null && !tracking.getCurrentLevel().trim().equalsIgnoreCase("")){
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"message.generalmessage", 
						"This RPM result is awaiting signoff on level " + tracking.getCurrentLevel() + ", only persons on level " + tracking.getCurrentLevel() + " of the RPM signoff route may signoff on this level."));
				addMessages(request, messages);
			}
			return mapping.findForward("viewRpm");
		}
		
		
	}	
	
	private String getNextSignOffLevel(int level, List<Qsprout> routingList){
		String signOffLevel="";
		
		int nextLevel = level + 1;
		//check if nextLevel in routingList
		boolean levelInRouting=false;
		for (int i=1; i < routingList.size();i++){
			Qsprout routeItem = new Qsprout();
			routeItem = (Qsprout) routingList.get(i);
			if (routeItem.getLevel().equalsIgnoreCase(String.valueOf(nextLevel))){
				levelInRouting=true;
			}
		}
		if (levelInRouting) {
			signOffLevel=String.valueOf(nextLevel);
		}else{
			signOffLevel="F";
		}			
		
		return signOffLevel;
	}
	
	private List<Person> getSignOffStaffForLevel(String level, List<Qsprout> routingList)throws Exception {
		List staffList = new ArrayList<Person>();
		
		for (int i=0; i < routingList.size();i++){
			Qsprout routeItem = new Qsprout();
			routeItem = (Qsprout) routingList.get(i);
			if (routeItem.getLevel().equalsIgnoreCase(level)){
				Person person = new Person();
				person = routeItem.getPerson();			
				staffList.add(person);				
			}
		}
		
		return staffList;
	}

	public ActionForward displayRpmList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		MdRpmForm mdRpmForm = (MdRpmForm) form;
		ActionMessages messages = new ActionMessages();

		MdRpmDao dao = new MdRpmDao();
				
		if (mdRpmForm.getStudyUnit().getCode() != null
				&& !mdRpmForm.getStudyUnit().getCode().trim()
						.equalsIgnoreCase("")) {
			// validate input studyUnit code
			mdRpmForm.getStudyUnit().setCode(mdRpmForm.getStudyUnit().getCode().trim().toUpperCase());
			ModuleDAO moduleDao = new ModuleDAO();
			StudyUnit studyUnit = new StudyUnit();
			studyUnit = moduleDao.getStudyUnit(mdRpmForm.getStudyUnit()
					.getCode());
			if (studyUnit ==null || studyUnit.getCode() == null) {
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"message.generalmessage", "Invalid Study Unit."));
			} else {
				mdRpmForm.setStudyUnit(studyUnit);
			}
		}else{
			StudyUnit studyUnit = new StudyUnit();
			mdRpmForm.setStudyUnit(studyUnit);
		}
		
		Qualification qualification = new Qualification();
		if (mdRpmForm.getQualification().getCode()!=null 
				&& !mdRpmForm.getQualification().getCode().trim().equalsIgnoreCase("")){
			//validate input Qualification code
			mdRpmForm.getQualification().setCode(mdRpmForm.getQualification().getCode().trim().toUpperCase());			
			qualification = dao.getQualification(mdRpmForm.getQualification().getCode(),null);
			if (qualification == null || qualification.getCode()==null){
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"message.generalmessage", "Invalid Qualification code"));
			} 
			
		}else{
			qualification = new Qualification();
		}
		
		if (mdRpmForm.getQualification().getSpeciality().getCode() != null && !mdRpmForm.getQualification().getSpeciality().getCode().trim().equalsIgnoreCase("")){
			mdRpmForm.getQualification().getSpeciality().setCode(mdRpmForm.getQualification().getSpeciality().getCode().trim().toUpperCase());
			mdRpmForm.getQualification().setCode(mdRpmForm.getQualification().getCode().trim().toUpperCase());
			qualification = dao.getQualification(mdRpmForm.getQualification().getCode(),mdRpmForm.getQualification().getSpeciality().getCode());
			if (qualification == null || qualification.getCode()==null){
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"message.generalmessage", "Invalid Speciality code"));
			} 
		}else{
			Speciality speciality = new Speciality();
			qualification.setSpeciality(speciality);			
		}
		
		if (!mdRpmForm.getCriteriaSelected().equalsIgnoreCase("FORME")){
			if ((mdRpmForm.getQualification().getCode()==null || mdRpmForm.getQualification().getCode().trim().equalsIgnoreCase("")) &&
					(mdRpmForm.getStudyUnit().getCode()==null || mdRpmForm.getStudyUnit().getCode().trim().equalsIgnoreCase("")) && 
					(mdRpmForm.getStudentNr() == null || mdRpmForm.getStudentNr().trim().equalsIgnoreCase("")) &&
					(mdRpmForm.getSupervisor().getPersonnelNumber()==null || mdRpmForm.getSupervisor().getPersonnelNumber().equalsIgnoreCase(""))){
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"message.generalmessage", "Either the qualification code, study unit, supervisor or student number must be supplied before a research proposal module list can be drawn except if you list only results rever to yourself for signoff or review."));
			}
		}		

		if (mdRpmForm.getStudentNr()!=null && !mdRpmForm.getStudentNr().trim().equalsIgnoreCase("")){
			if (isInteger(mdRpmForm.getStudentNr())){
				Student student = new Student();
				student =	dao.getStudent(Integer.parseInt(mdRpmForm.getStudentNr()));
				if (student ==null || student.getNumber()==null){
					messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"message.generalmessage", "Invalid student number.  Student not found."));
				}
			}else{
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"message.generalmessage", "The student number must be numberic."));
			}
		}

		if (!messages.isEmpty()) {
			addErrors(request, messages);
			return mapping.findForward("inputRpmList");
		}

		mdRpmForm.setQualification(qualification);
		List<RpmListRecord> rpmList = new ArrayList<RpmListRecord>();
		rpmList = dao.getRpmList(mdRpmForm.getCollege().getCode(), mdRpmForm.getStudyUnit().getCode(), mdRpmForm.getQualification().getCode(),mdRpmForm.getQualification().getSpeciality().getCode(), mdRpmForm.getCriteriaSelected(),mdRpmForm.getStudentNr(),mdRpmForm.getSupervisor().getPersonnelNumber(),mdRpmForm.getMyUnisaUser().getPersonnelNumber());
		mdRpmForm.setRpmList(rpmList);

		return mapping.findForward("displayRpmList");
	}
	
	public ActionForward getSupervisors(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		MdRpmForm mdRpmForm = (MdRpmForm) form;
		ActionMessages messages = new ActionMessages();		
		
		MdRpmDao dao = new MdRpmDao();		
		return mapping.findForward("displayRpmList");
	}

	public ActionForward proposeResult(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		MdRpmForm mdRpmForm = (MdRpmForm) form;
		ActionMessages messages = new ActionMessages();

		MdRpmDao dao = new MdRpmDao();
		RpmRecord rpm = new RpmRecord();
		
		Student student = new Student();
		student = dao.getStudent(49919180);
		rpm.setStudent(student);		
		
		List<Promotor> promotorList = new ArrayList<Promotor>();
		promotorList = dao.getPromotorList(49919180,"MPEMS92");		
		rpm.setPromotorList(promotorList);
		
		TrackRecord tracking = new TrackRecord();
		
		tracking.setComment("");
		
		String title = dao.getDissertationTitle(49919180,"MPEMS92");
		rpm.setTitle(title);
		
		Gencod proposedResult = new Gencod();
		tracking.setProposedResult(proposedResult);
		
		List<Qsprout> routingList = new ArrayList<Qsprout>();
		routingList = dao.getRoutingList("98587","null");
		rpm.setRoutingList(routingList);

		return mapping.findForward("proposeResult");
	}		
	
	public void sendSignOffNotificationEmail(RpmRecord rpm, Person myUnisaUser, Person addressee) {

		try {

			String tmpEmailFrom = ServerConfigurationService.getString("noReplyEmailFrom");
			String serverpath = ServerConfigurationService.getToolUrl();
		
			if (serverpath.contains("mydev") || serverpath.contains("myqa") || serverpath.contains("localhost")){
				//overwrite addressee e-mail
				addressee.setEmailAddress("pretoj@unisa.ac.za");
			}
			
			InternetAddress toEmail = new InternetAddress(addressee.getEmailAddress());
			InternetAddress iaTo[] = new InternetAddress[1];
			iaTo[0] = toEmail;
			InternetAddress iaHeaderTo[] = new InternetAddress[1];
			iaHeaderTo[0] = toEmail;
			InternetAddress iaReplyTo[] = new InternetAddress[1];
			iaReplyTo[0] = new InternetAddress(tmpEmailFrom);

			/* Setup path to myUnisa */
			String myUnisaPath = ServerConfigurationService.getServerUrl();

			/* send activation email to student */
			String subject = "RPM result referral for " + rpm.getStudent().getNumber() + " " + rpm.getStudent().getPrintName();
			String body = "<html> "+
		    "<body> "+
			"Dear " + addressee.getName() + "<br/><br/>"+
			"The research proposal module result has been recorded for " + rpm.getStudent().getNumber() + " " + rpm.getStudent().getPrintName() + "<br/>"+
			"for the " + rpm.getQualification().getCode() + " " + rpm.getQualification().getDescription() + "<br/>"+
			"and has been referred to you for ratification.<br/><br/>" +
			"Please log on to myUnisa (M and D Research Proposal Module) and follow the steps to the sign-off. <br/><br/>" +
			"I thank you kindly <br/><br/>"+
			"Regards<br/><br/>" +
			myUnisaUser.getName() +
			"</body>"+
			"</html>";

		  List contentList = new ArrayList();
		  contentList.add("Content-Type: text/html");

		  emailService = (EmailService)ComponentManager.get(EmailService.class);
		  
		  emailService.sendMail(iaReplyTo[0],iaTo,subject,body,iaHeaderTo,iaReplyTo,contentList);
		 log.info("sending mail from "+iaReplyTo[0]+" to "+toEmail.toString());
		 } catch (Exception e) {
		 	log.error("M and D research proposal, sign-off notification email "+e+" "+e.getMessage());
			e.printStackTrace();
		 }
	}
	
	public void sendReviewNotificationEmail(RpmRecord rpm, Person myUnisaUser, Person addressee) {

		try {

			String tmpEmailFrom = ServerConfigurationService.getString("noReplyEmailFrom");
			String serverpath = ServerConfigurationService.getToolUrl();
		
			if (serverpath.contains("mydev") || serverpath.contains("myqa") || serverpath.contains("localhost")){
				//overwrite addressee e-mail
				addressee.setEmailAddress("pretoj@unisa.ac.za");
			}
			
			InternetAddress toEmail = new InternetAddress(addressee.getEmailAddress());
			InternetAddress iaTo[] = new InternetAddress[1];
			iaTo[0] = toEmail;
			InternetAddress iaHeaderTo[] = new InternetAddress[1];
			iaHeaderTo[0] = toEmail;
			InternetAddress iaReplyTo[] = new InternetAddress[1];
			iaReplyTo[0] = new InternetAddress(tmpEmailFrom);

			/* Setup path to myUnisa */
			String myUnisaPath = ServerConfigurationService.getServerUrl();

			/* send activation email to student */
			String subject = "RPM result review for " + rpm.getStudent().getNumber() + " " + rpm.getStudent().getPrintName();
			String body = "<html> "+
		    "<body> "+
			"Dear " + addressee.getName() + "<br/><br/>"+
			"The research proposal module result has been recorded for " + rpm.getStudent().getNumber() + " " + rpm.getStudent().getPrintName() + "<br/>"+
			"for the " + rpm.getQualification().getCode() + " " + rpm.getQualification().getDescription() + ".<br/><br/>"+
			"Please review your initial decision.<br/><br/>" +
			"Please log on to myUnisa (M and D Research Proposal Module) and follow the steps to the sign-off. <br/><br/>" +
			"I thank you kindly <br/><br/>"+
			"Regards<br/><br/>" +
			myUnisaUser.getName() +
			"</body>"+
			"</html>";

		  List contentList = new ArrayList();
		  contentList.add("Content-Type: text/html");

		  emailService = (EmailService)ComponentManager.get(EmailService.class);
		  
		  emailService.sendMail(iaReplyTo[0],iaTo,subject,body,iaHeaderTo,iaReplyTo,contentList);
		 log.info("sending mail from "+iaReplyTo[0]+" to "+toEmail.toString());
		 } catch (Exception e) {
		 	log.error("M and D research proposal, sign-off notification email "+e+" "+e.getMessage());
			e.printStackTrace();
		 }
	}

	private List<LabelValueBean> initializeSearchCriteria() {

		ArrayList<LabelValueBean> list = new ArrayList<LabelValueBean>();
		LabelValueBean searchitem = new LabelValueBean();

		searchitem.setValue("ALL");
		searchitem.setLabel("ALL");
		list.add(searchitem);
		searchitem = new LabelValueBean();
		searchitem.setValue("NOTSTARTED");
		searchitem.setLabel("No Result Proposed");
		list.add(searchitem);
		searchitem = new LabelValueBean();
		searchitem.setValue("INPROGRESS");
		searchitem.setLabel("Result in Progress (Refer for signoff/refer back for review");
		list.add(searchitem);
		searchitem = new LabelValueBean();
		searchitem.setValue("REVIEW");
		searchitem.setLabel("Result refer back for review");
		list.add(searchitem);
		searchitem = new LabelValueBean();
		searchitem.setValue("REFER");
		searchitem.setLabel("Result refer for signoff");
		list.add(searchitem);
		searchitem = new LabelValueBean();
		searchitem.setValue("FORME");
		searchitem.setLabel("Result refer to me for signoff/review");
		list.add(searchitem);
		searchitem = new LabelValueBean();
		searchitem.setValue("FINAL");
		searchitem.setLabel("Result finalized");
		list.add(searchitem);

		return list;

	}
	
	private List<LabelValueBean> initializeReviewResponseOptions() {

		ArrayList<LabelValueBean> list = new ArrayList<LabelValueBean>();
		LabelValueBean responseItem = new LabelValueBean();

		responseItem.setValue("AGREE");
		responseItem.setLabel("Agree with proposed result (including the title and supervisor)");
		list.add(responseItem);
		responseItem = new LabelValueBean();
		responseItem.setValue("DISAGREE");
		responseItem.setLabel("Refer back for review. (If you do not agree with the proposed result, including the title and supervisor, refer back to the initiating staff member)");
		list.add(responseItem);
		

		return list;

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
