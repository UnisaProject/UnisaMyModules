package za.ac.unisa.lms.tools.maintainstaff.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.LookupDispatchAction;
import org.sakaiproject.authz.api.SecurityService;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.event.api.UsageSession;
import org.sakaiproject.event.api.EventTrackingService;
import org.sakaiproject.event.api.UsageSessionService;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import za.ac.unisa.lms.constants.EventTrackingTypes;
import za.ac.unisa.lms.tools.maintainstaff.DAO.MaintainStaffSakaiDAO;
import za.ac.unisa.lms.tools.maintainstaff.DAO.MaintainStaffStudentDAO;
import za.ac.unisa.lms.tools.maintainstaff.forms.CourseForm;
import za.ac.unisa.lms.tools.maintainstaff.forms.HeadingForm;
import za.ac.unisa.lms.tools.maintainstaff.forms.MainForm;
import za.ac.unisa.lms.tools.maintainstaff.forms.PersonForm;
import za.ac.unisa.lms.tools.maintainstaff.forms.RecordForm;

public class PersonAction extends LookupDispatchAction {

	private EventTrackingService eventTrackingService;
	private UsageSessionService usageSessionService;
    private SessionManager sessionManager;
    private SecurityService securityService;
    private final static String OBSERVER = "OBSRV";
    private final static String NULLVALUEERROR = "Unexpected error occurred please try again.";
    
    
	@Override
	protected Map getKeyMethodMap() {
		
	    Map map = new HashMap();
	    map.put("personDisplay","personDisplay");
	    map.put("expand","expand");
	    map.put("collapse", "collapse");
	    map.put("button.remove","removeRoles");
	    map.put("button.updateperson","updateRoles");
	    map.put("button.cancel","cancel");
	    map.put("button.back","back");
	    map.put("addPerson", "addPerson");
	    map.put("button.submit","addPersonVerify");
	    map.put("button.finish","addPersonSave");
	    //map.put("personLogs", "personLogs");
	    //map.put("button.personLogsDisplay","displayLogs");
	    map.put("selectAll","selectAll");
	    map.put("unSelectAll","unSelectAll");
	    map.put("selectAllVisible","selectAllVisible");

		return map;
	}
	
	protected ActionForward unspecified(ActionMapping mapping,
            ActionForm form,
            javax.servlet.http.HttpServletRequest request,
            javax.servlet.http.HttpServletResponse response)
     	throws java.lang.Exception{
		
		PersonForm personForm = (PersonForm) form;
		MaintainStaffStudentDAO db1 = new MaintainStaffStudentDAO();
		MainForm mainForm = (MainForm) request.getSession().getAttribute("mainForm");
		
		personForm.setPersonNetworkCode(mainForm.getPerson());
		personForm.setPersonName(mainForm.getPersonName());
		
		if ((personForm.getSelectedView()==null)||(personForm.getSelectedView().equals(""))) {
			personForm.setSelectedView("L");
		}
		// select data for that course
		db1.selectCourseList(personForm);
		personForm.setPrevAcadPeriod("x");
		
		request.setAttribute("viewOptionList", personForm.getViewOptionList());
		request.setAttribute("courseList", personForm.getCourseList());
		request.setAttribute("roleOptions",personForm.getRoleOptions());
		
		return mapping.findForward("personDisplay");
		
	}
	
	/** Select detail for person and go to person view */
	public ActionForward personDisplay(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		PersonForm personForm = (PersonForm) form;
		MaintainStaffStudentDAO db1 = new MaintainStaffStudentDAO();
		MaintainStaffSakaiDAO db2 = new MaintainStaffSakaiDAO();
		MainForm mainForm = (MainForm) request.getSession().getAttribute("mainForm");
		sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
		// get user permission
		Session currentSession = sessionManager.getCurrentSession();
		String userID = currentSession.getUserId();
		String userEID = currentSession.getUserEid();
		try {
			personForm.setUserPermission(db2.getUserRights(userID,userEID));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		personForm.setPersonNetworkCode(mainForm.getPerson());
		personForm.setPersonName(mainForm.getPersonName());
		
		if ((personForm.getSelectedView()==null)||(personForm.getSelectedView().equals(""))) {
			personForm.setSelectedView("L");
		}
		personForm.setRoleOptions(personForm.getRoleOptions());
		try {
		// select data for that course
		db1.selectCourseList(personForm);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		personForm.setPrevAcadPeriod("x");
		
		request.setAttribute("viewOptionList", personForm.getViewOptionList());
		request.setAttribute("courseList", personForm.getCourseList());
		request.setAttribute("roleOptions",personForm.getRoleOptions());
		
		return mapping.findForward("personDisplay");
	}
	
	
	/** Go to add person to existing course screen */
	public ActionForward addPerson(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		PersonForm personForm = (PersonForm) form;
		MaintainStaffStudentDAO db1 = new MaintainStaffStudentDAO();
		ArrayList tmp = new ArrayList();
		ArrayList courseList = new ArrayList();
		
		if (personForm.getNoOfRecords() == 0) {
			personForm.setCourseList(tmp);
		} else {
			int tmpCount = 0;
			if (courseList.size() >= 1) {
				tmpCount = courseList.size();
			} else {
				
			}
			for (int i=tmpCount; i < personForm.getNoOfRecords(); i++) {
				RecordForm record = new RecordForm();
				record.setName(personForm.getPersonName());
				record.setNetworkCode(personForm.getPersonNetworkCode());
				if (personForm.getSelectedView().equals("L")) {
					//for teaching roles
					record.setHeading("Add Course ("+(i+1)+" of "+personForm.getNoOfRecords()+")");
				}else if (personForm.getSelectedView().equals("J")) {
					//for Marking functions
					record.setHeading("Add Marking Details ("+(i+1)+" of "+personForm.getNoOfRecords()+")");
				}else {
					//for examination function
					record.setHeading("Add Examination Details ("+(i+1)+" of "+personForm.getNoOfRecords()+")");
				}
				
				courseList.add(record);
			}
			
			personForm.setCourseList(courseList);
		}
		
		// Get period dropdown list
		if (personForm.getSelectedView().equals("L") || personForm.getSelectedView().equals("J")) {
			// academic periods
			personForm.setPeriodList(db1.selectRegPeriodOptions());
			request.setAttribute("periodList", personForm.getPeriodList());
		} else {
			// examination periods
			personForm.setPeriodList(db1.selectExamPeriodOptions());
			request.setAttribute("periodList", personForm.getPeriodList());
		}
		
		// get Paper number options
		if (personForm.getSelectedView().equals("E")) {
			personForm.setPaperNrOptions(personForm.getPaperNrOptions());
			request.setAttribute("paperNrOptions", personForm.getPaperNrOptions());
		}
		
		
		request.setAttribute("yearList", personForm.getYearList());
		request.setAttribute("noOfRecordsOptions", personForm.getNoOfRecordsOptions());
		request.setAttribute("roleOptions", personForm.getRoleOptions());
		
		return mapping.findForward("addPerson");
	}
	
	/** Verify person & course detail, and display confirmation screen */
	public ActionForward addPersonVerify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		PersonForm personForm = (PersonForm) form;
		MaintainStaffStudentDAO studentSystemDAO = new MaintainStaffStudentDAO();
		MaintainStaffSakaiDAO maintainStaffSakaiDAO = new MaintainStaffSakaiDAO();
		ActionMessages messages = new ActionMessages();
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
		UsageSession usageSession = usageSessionService.getSession();
		securityService = (SecurityService) ComponentManager.get(SecurityService.class);
		String userEID = usageSession.getUserEid();
		String userId= usageSession.getUserId();
		String inactiveCodes = "";
		String invalidCodes="";
		boolean collegeCodeMatch=false; 
		
		request.setAttribute("periodList", personForm.getPeriodList());
		request.setAttribute("yearList", personForm.getYearList());
		request.setAttribute("noOfRecordsOptions", personForm.getNoOfRecordsOptions());
		request.setAttribute("roleOptions", personForm.getRoleOptions());
		
		// get Paper number options
		if (personForm.getSelectedView().equals("E")) {
			personForm.setPaperNrOptions(personForm.getPaperNrOptions());
			request.setAttribute("paperNrOptions", personForm.getPaperNrOptions());
		}
		if (personForm.getNoOfRecords() == 0){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Select number of courses to add"));
			addErrors(request, messages);
			return mapping.findForward("addPerson");
		}
		ArrayList courseList = personForm.getCourseList();
		for (int i=0; i < courseList.size(); i++) {
			RecordForm record = (RecordForm) courseList.get(i);
			
			record.setCourse(ltrim(record.getCourse()));
			record.setCourse(rtrim(record.getCourse()));
			record.setCourse(record.getCourse().toUpperCase());
				
			// verify that course code was entered
			if ((null == record.getCourse())||(record.getCourse().length()==0)) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Course information is not completed."));
				addErrors(request, messages);
				return mapping.findForward("addPerson");
			}
			
			// Verify that year was entered
			if ((null == record.getAcadYear())||(record.getAcadYear().length()==0)) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Please enter the Year"));
				addErrors(request, messages);
				return mapping.findForward("addPerson");
			}
			
			// Verify that year was entered
			if ((null == record.getSemesterPeriod())||(record.getSemesterPeriod().length()==0)) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Please choose the Period"));
				addErrors(request, messages);
				return mapping.findForward("addPerson");
			}	
			// Verify that year was entered
			if ((null == record.getRole())||(record.getRole().length()==0)) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Please choose the Role"));
				addErrors(request, messages);
				return mapping.findForward("addPerson");
			}	
			
			// verify that the course code is valid
			boolean courseValid = studentSystemDAO.courseExist(record.getCourse());
			if (courseValid == false) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Course code is not valid.  Please verify correct code and re-enter."));
				addErrors(request, messages);
				return mapping.findForward("addPerson");
			}
			
			//extra permission to Tracy and other people
			String superAccess = maintainStaffSakaiDAO.getUserPerms(userId,userEID);
	    	// Ristrict the linker to link people only to their colleges
			 try {
				 if (!securityService.isSuperUser(userId)  ) {
			    	  if(!superAccess.equalsIgnoreCase("MAINTAIN")){
		     
					collegeCodeMatch = studentSystemDAO.checkCollegeCodeMatch(record.getCourse().toUpperCase(), userEID.toUpperCase());
					log.info("MaintainStaff PersonAction: addPersonVerify .checkCollegeCodeMatch ");

			       if (collegeCodeMatch == false) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "You are not allowed to link staff to this course as it is not part of your department's current academic responsibilities."));
					addErrors(request, messages);
					return mapping.findForward("addPerson");
				   }
			      }
				 }
			     }catch (Exception e) {
			    	 log.error("MaintainStaff PersonAction: addPersonVerify Failed to check the admin permissions or checkCollegeCodeMatch "+userEID.toUpperCase());
			    	 e.printStackTrace();
			   }
		
			// verify that the course/year/semester combination is valid
			if (personForm.getSelectedView().equals("L") || personForm.getSelectedView().equals("J")){
				// validate acad period + course combination
				boolean courseCombinationValid = studentSystemDAO.courseValid(record.getCourse(), record.getAcadYear(), record.getSemesterPeriod(),personForm.getSelectedView());
				if (courseCombinationValid == false) {
					if (invalidCodes.length() >0) {
						invalidCodes = invalidCodes+", "+record.getCourse();
					} else {
						invalidCodes = record.getCourse();
					}
				}
				
				// validate that course is still active
				boolean courseActive = studentSystemDAO.courseExpired(record.getCourse(), record.getAcadYear(), record.getSemesterPeriod());
				if (courseActive == false) {
					if (inactiveCodes.length() >0) {
						inactiveCodes = inactiveCodes+", "+record.getCourse();
					} else {
						inactiveCodes = record.getCourse();
					}
				}
			} else if (personForm.getSelectedView().equals("E")) {
				// validate exam period
				boolean isValid = studentSystemDAO.examPeriodExist(record.getCourse(), record.getAcadYear(), record.getSemesterPeriod());
				if (isValid == false) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Invalid Examination period, please choose correct examination year and period"));
					addErrors(request, messages);
					return mapping.findForward("addPerson");
				}
			}
			
			if (invalidCodes.length()>0) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Academic Period is not valid for "+invalidCodes+".  Please change to correct academic offering type."));
			}
			if (inactiveCodes.length()>0) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Linking can not be done as course[s] not active "+inactiveCodes+"."));
			}
			if ((invalidCodes.length()>0)||(inactiveCodes.length()>0)) {
				addErrors(request, messages);
				return mapping.findForward("addPerson");
			}
			
			record.setCourse(record.getCourse().toUpperCase());
			record.setAcadPeriodDesc(record.getAcadPeriodDescription(personForm.getSelectedView()));
			
			// verify that course site was created (only for teaching roles)
			if (personForm.getSelectedView().equals("L")){
				boolean courseSiteExist = studentSystemDAO.courseExist(record.getCourse(), record.getAcadYear(),
						record.getSemesterPeriod(), personForm.getSelectedView());
				if (courseSiteExist == false) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Person cannot be linked to course site, course site ("+record.getCourse()+" "+record.getAcadPeriodDesc()+
									") does not exist."));
					addErrors(request, messages);
					return mapping.findForward("addPerson");
				}
			}
			
			// verify that person is still active
			studentSystemDAO.selectPersonName(record);
			// is person active, if not he may not be linked to a course site.
			if (record.getStatus().equals("Inactive")) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", record.getNetworkCode()+" may not be linked as he/she is not an active staff member."));
				addErrors(request, messages);
				return mapping.findForward("addPerson");
			}
			if (personForm.getSelectedView().equals("E")&&(record.getStaffNo().equals("0"))) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", record.getNetworkCode()+" may not be linked as he/she does not have a valid staff number."));
				addErrors(request, messages);
				return mapping.findForward("addPerson");				
			}else if (personForm.getSelectedView().equals("J")&&(record.getStaffNo().equals("0"))) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", record.getNetworkCode()+" may not be linked as he/she does not have a valid staff number."));
				addErrors(request, messages);
				return mapping.findForward("addPerson");				
			}
			//set paper number to 1 when user has chosen "0" on exam function
			if (personForm.getSelectedView().equals("E")) {
				if(record.getPaperNo().equals("0")){
					record.setPaperNo("1");
				} else {
					record.setPaperNo(record.getPaperNo());
				}
				
				boolean isDuplicate = studentSystemDAO.isDuplicatePerson(record.getCourse(), record.getAcadYear(), record.getSemesterPeriod(), 
						record.getNetworkCode(), personForm.getSelectedView(),record.getPaperNo());
				String roleDesc = studentSystemDAO.selectRoleDescription(record.getRole(), 80);
				record.setRoleDesc(roleDesc);
//				if record duplicate in Examination functions			
				if (isDuplicate == true){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", record.getNetworkCode()+" already listed as "+record.getRoleDesc()+" "+"on"+" "+record.getAcadPeriodDesc()+" for "+record.getCourse()+".  " +
									"Correct function or return to previous function."));
					addErrors(request, messages);
					return mapping.findForward("addPerson");	
				}
					
			}
			
			// verify selected paper number
			if (personForm.getSelectedView().equals("E")) {
				//validPaperNr(String examYear, String examPeriod, String course, String paperNr
				boolean validPaperNr = studentSystemDAO.validPaperNr(record.getAcadYear(), record.getSemesterPeriod(), record.getCourse(), record.getPaperNo());
				if (validPaperNr == false) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Examination paper ("+record.getPaperNo()+") does not exist for "+record.getCourse()+" "+record.getAcadPeriodDesc()+".  " +
									"Please contact DSAA."));
					addErrors(request, messages);
					return mapping.findForward("addPerson");
				}
			}
			
			// verify that person is not already linked to this course
			if (personForm.getSelectedView().equals("L") || personForm.getSelectedView().equals("J")) {
				record.setPaperNo("");
				
				boolean isDuplicate = studentSystemDAO.isDuplicatePerson(record.getCourse(), record.getAcadYear(), record.getSemesterPeriod(), 
						record.getNetworkCode(), personForm.getSelectedView(),record.getPaperNo());
				if (isDuplicate == true) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "The person is already linked to "+record.getCourse()+" "+record.getAcadPeriodDesc()+".  " +
									"Cannot create duplicate entry."));
					addErrors(request, messages);
					return mapping.findForward("addPerson");
				}
			}
			
			if (personForm.getSelectedView().equals("L")) {
				record.setRoleDesc(studentSystemDAO.selectRoleDescription(record.getRole(), 24));
			}else if(personForm.getSelectedView().equals("J")) {
				record.setRoleDesc(studentSystemDAO.selectRoleDescription(record.getRole(), 217));
			}else {
				record.setRoleDesc(studentSystemDAO.selectRoleDescription(record.getRole(), 80));
			}
			
			
			record.setAcadPeriodDesc(record.getAcadPeriodDescription(personForm.getSelectedView()));
			
			record.setCourseSite(personForm.getSelectedView());
						
		}
	
		if (personForm.getSelectedView().equals("L")) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Are you sure you want to add the following persons to create this course site?  "));
		}else if(personForm.getSelectedView().equals("J")) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Are you sure you want to add the following persons to this Marking period? "));
		}else {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Are you sure you want to add the following persons to this Examination period?  "));
		}
		addMessages(request, messages);
		
		return mapping.findForward("addPersonConfirm");
	}
	
	/** Save person course site detail */
	public ActionForward addPersonSave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		PersonForm personForm = (PersonForm) form;
		MaintainStaffStudentDAO db1 = new MaintainStaffStudentDAO();
		ActionMessages messages = new ActionMessages();
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
		UsageSession usageSession = usageSessionService.getSession();
		//logged in user
		sessionManager = (SessionManager)ComponentManager.get(SessionManager.class);
		Session currentSession = sessionManager.getCurrentSession();
   		String userID = currentSession.getUserEid();
		String msg = "";
		
		// Insert other roles
		ArrayList courseList = personForm.getCourseList();
		// for each person added do the following validations
		for (int i=0; i < courseList.size(); i++) {
			RecordForm record = (RecordForm) courseList.get(i);
			
			if (personForm.getSelectedView().equals("L") || personForm.getSelectedView().equals("J")){
				String errorOccurred = db1.insertRole(record.getNetworkCode(), record.getStaffNo(), record.getCourse(),
						record.getAcadYear(), record.getSemesterPeriod(), record.getRole(), personForm.getSelectedView(),"");
				if (errorOccurred.length() > 0) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", NULLVALUEERROR));
					addErrors(request, messages);
					
					request.setAttribute("periodList", personForm.getPeriodList());
					request.setAttribute("yearList", personForm.getYearList());
					request.setAttribute("noOfRecordsOptions", personForm.getNoOfRecordsOptions());
					request.setAttribute("roleOptions", personForm.getRoleOptions());
					
					// get Paper number options
					if (personForm.getSelectedView().equals("E")) {
						personForm.setPaperNrOptions(personForm.getPaperNrOptions());
						request.setAttribute("paperNrOptions", personForm.getPaperNrOptions());
					}
					
					return mapping.findForward("addPerson");
				}
				//Role/Function added: user=+network code+ role=+added role name+ paper number=+paper number
				//insert Audit log
				String logTrail="Role/Function added: user= "+record.getNetworkCode()+ "new role= "+record.getRole()+"(changed by  " + userID +")";
				db1.insertAuditLog(userID,record.getCourse(),personForm.getSelectedView(),record.getAcadYear(),record.getSemesterPeriod(),logTrail);
				
				if (msg.length() == 0) {
					msg = record.getCourseSite();
				} else {
					msg = msg+"; "+record.getCourseSite();
				}
				
				eventTrackingService.post(
						eventTrackingService.newEvent(EventTrackingTypes.EVENT_MAINTAINSTAFF_ROLE_ADD, "Role added. Course: "+record.getCourseSite()+" "+
								" Person: "+record.getNetworkCode().toUpperCase()+" Role: "+record.getRole(), false), usageSession);
			}
			
				
		// for each person added do the following validations for Examination Function
		if (personForm.getSelectedView().equals("E")) {
			
			if ((null == record.getNetworkCode())||(record.getNetworkCode().length() == 0)) {
		
			} else {
				String errorOccurred = db1.insertRole(record.getNetworkCode(), record.getStaffNo(), record.getCourse(),
						record.getAcadYear(), record.getSemesterPeriod(), record.getRole(), personForm.getSelectedView(),record.getPaperNo());
				if (errorOccurred.length()> 0) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", NULLVALUEERROR));
					addErrors(request, messages);
					
					request.setAttribute("periodList", personForm.getPeriodList());
					request.setAttribute("yearList", personForm.getYearList());
					request.setAttribute("noOfRecordsOptions", personForm.getNoOfRecordsOptions());
					request.setAttribute("roleOptions", personForm.getRoleOptions());
					
					// get Paper number options
					if (personForm.getSelectedView().equals("E")) {
						personForm.setPaperNrOptions(personForm.getPaperNrOptions());
						request.setAttribute("paperNrOptions", personForm.getPaperNrOptions());
					}
					
					return mapping.findForward("addPerson");
				}
				
				//insert Audit log
				String logTrail="Role/Function added: user= "+record.getNetworkCode()+ "new role= "+record.getRole()+"paper number= "+record.getPaperNo()+"(changed by  " + userID +")";
				db1.insertAuditLog(userID,record.getCourse(),personForm.getSelectedView(),record.getAcadYear(),record.getSemesterPeriod(),logTrail);
									
				if (msg.length() == 0) {
					msg = record.getCourseSite();
				} else {
					msg = msg+"; "+record.getCourseSite();
				}
				
				eventTrackingService.post(
						eventTrackingService.newEvent(EventTrackingTypes.EVENT_MAINTAINSTAFF_ROLE_ADD, "Role added. Course: "+record.getCourse()+"-"+record.getAcadPeriodDesc()+" "+record.getPaperNo()+
							 " Role: "+record.getRole(), false), usageSession);
				
			}
		}
	  }
		
		messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", "Person was successfully added to course sites/exam/marking period "+msg));
		addMessages(request, messages);
		
		// select data for that course
		db1.selectCourseList(personForm);
		personForm.setPrevAcadPeriod("x");
		personForm.setNoOfRecords(0);
		
		/*//set paper number to 1 when user has chosen 0
		if(personForm.getPaperNo().equals("0")){
			personForm.setPaperNo("1");
		} **/

		
		request.setAttribute("viewOptionList", personForm.getViewOptionList());
		request.setAttribute("courseList", personForm.getCourseList());
		request.setAttribute("roleOptions", personForm.getRoleOptions());
		request.setAttribute("paperNo", personForm.getPaperNo());
		
		return mapping.findForward("personDisplay");
	}
	
	
	/*
	//View person audit Logs 
	public ActionForward personLogs(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PersonForm personForm = (PersonForm) form;
		MaintainStaffStudentDAO db1 = new MaintainStaffStudentDAO();
		
		request.setAttribute("yearOptions", personForm.getYearList());
		
		// Get period dropdown list
		if (personForm.getSelectedView().equals("L")) {
			// academic periods
			personForm.setPeriodList(db1.selectRegPeriodOptions());
			request.setAttribute("periodOptions", personForm.getPeriodList());
		} else {
			// examination periods
			personForm.setPeriodList(db1.selectExamPeriodOptions());
			request.setAttribute("periodOptions", personForm.getPeriodList());
		}
		
		return mapping.findForward("personLogs");
		
	}*/
	
	/*
	 Verify availability of logs and display 
	public ActionForward displayLogs(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PersonForm personForm = (PersonForm) form;
		MaintainStaffStudentDAO db1 = new MaintainStaffStudentDAO();
		ActionMessages messages = new ActionMessages();
		
		request.setAttribute("yearOptions", personForm.getYearList());
		
		// Get period dropdown list
		if (personForm.getSelectedView().equals("L")) {
			// academic periods
			personForm.setPeriodList(db1.selectRegPeriodOptions());
			request.setAttribute("periodOptions", personForm.getPeriodList());
		} else {
			// examination periods
			personForm.setPeriodList(db1.selectExamPeriodOptions());
			request.setAttribute("periodOptions", personForm.getPeriodList());
		}
		
		boolean logsExist= true;
		ArrayList courseList = personForm.getCourseList();
			for (int i=0; i < courseList.size(); i++) {
				RecordForm record = (RecordForm) courseList.get(i);
				try {
					logsExist=db1.verifyLogsExist(record.getCourse(),record.getSelectedView(),record.getAcadYear(),record.getSemesterPeriod());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
				System.out.println("logsExist "+logsExist );
				if (logsExist == false) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "No information available for the selected period"));
					addErrors(request, messages);
			
				}else{
					record.setLogsList(db1.getEventLogs(record.getCourse(), record.getSelectedView(), record.getAcadYear(), record.getSemesterPeriod()));
				}
		
			}
			return mapping.findForward("personlogs");
	}
 **/
	
	/* Expand all periods */
	public ActionForward expand(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		PersonForm personForm = (PersonForm) form;
		MaintainStaffStudentDAO db1 = new MaintainStaffStudentDAO();
		MainForm mainForm = (MainForm) request.getSession().getAttribute("mainForm");
		
		request.setAttribute("viewOptionList",personForm.getViewOptionList());
		request.setAttribute("roleOptions",personForm.getRoleOptions());
		
		// select data for that course
		//db1.selectCourseList(courseForm);
		personForm.setPrevAcadPeriod("x");
		
		if (personForm.getExpandedPeriods() == null) {
			personForm.setExpandedPeriods(new ArrayList());
		}
		
		request.setAttribute("prevAcadPeriod", personForm.getPrevAcadPeriod());
		
		String tmp = (String) request.getParameter("periodHeadId");
		ArrayList periodHeadings = personForm.getPeriodHeadings();
		for (int i=0; i < periodHeadings.size(); i++) {
			HeadingForm headingForm = (HeadingForm) periodHeadings.get(i);
			
			if (headingForm.getPeriodDesc().equals(tmp)) {
				headingForm.setExpand("YES");
			}
		}
		personForm.setPeriodHeadings(periodHeadings);
			
		
		return mapping.findForward("personDisplay");
		
	}
	
	/* Collapse all periods */
	public ActionForward collapse(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		PersonForm personForm = (PersonForm) form;
		MaintainStaffStudentDAO db1 = new MaintainStaffStudentDAO();
		MainForm mainForm = (MainForm) request.getSession().getAttribute("mainForm");
			
		request.setAttribute("viewOptionList",personForm.getViewOptionList());
		request.setAttribute("roleOptions",personForm.getRoleOptions());
		
		// select data for that course
		//db1.selectCourseList(courseForm);
		personForm.setPrevAcadPeriod("x");
		
		if (personForm.getExpandedPeriods() == null) {
			personForm.setExpandedPeriods(new ArrayList());
		}
		
		request.setAttribute("prevAcadPeriod", personForm.getPrevAcadPeriod());
			
		String tmp = (String) request.getParameter("periodHeadId");
		ArrayList periodHeadings = personForm.getPeriodHeadings();
		for (int i=0; i < periodHeadings.size(); i++) {
			HeadingForm headingForm = (HeadingForm) periodHeadings.get(i);
			
			if (headingForm.getPeriodDesc().equals(tmp)) {
				headingForm.setExpand("NO");
			}
		}
		personForm.setPeriodHeadings(periodHeadings);
		
		
		return mapping.findForward("personDisplay");
		
	}
	
	/** go to remove person from a course site screen */ 
	public ActionForward removeRoles(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PersonForm personForm = (PersonForm) form;
		MaintainStaffStudentDAO studentSystemDAO = new MaintainStaffStudentDAO();
		MaintainStaffSakaiDAO maintainStaffSakaiDAO = new MaintainStaffSakaiDAO();
		MainForm mainForm = (MainForm) request.getSession().getAttribute("mainForm");
		ActionMessages messages = new ActionMessages();
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
		UsageSession usageSession = usageSessionService.getSession();
 	
   		securityService = (SecurityService) ComponentManager.get(SecurityService.class);
		String userId= usageSession.getUserId();
		
		//logged in user
		sessionManager = (SessionManager)ComponentManager.get(SessionManager.class);
		Session currentSession = sessionManager.getCurrentSession();
   		String userID = currentSession.getUserEid();
   		String userEID = currentSession.getUserEid();
   		boolean collegeCodeMatch=false; 
		
		if ((personForm.getSelectedView()==null)||(personForm.getSelectedView().equals(""))) {
			personForm.setSelectedView("L");
		}	
		
		request.setAttribute("viewOptionList",personForm.getViewOptionList());
		request.setAttribute("roleOptions",personForm.getRoleOptions());
		
		personForm.setPrevAcadPeriod("x");
		
		String tmpAtStep = request.getParameter("atstep").toString();
		if (tmpAtStep.equals("remove")) {
			// remove roles			
			ArrayList courseList = personForm.getCourseList();
			for (int i=0; i < courseList.size(); i++) {			
				RecordForm record = (RecordForm) courseList.get(i);
				
				if (record.isRemove()) {
/*					if (personForm.getSelectedView().equals("E")){
						// Validate outstanding assignments 
						boolean outAssign = false;
						try {
							outAssign = db1.validateAssignOut(record.getNetworkCode(),record.getAcadYear());
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						if (outAssign == true) {
							messages.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("message.generalmessage",personForm.getPersonName()+ "Person cannot be removed.There are outstanding assignments."));
							addErrors(request, messages);

							return mapping.findForward("personDisplay");
						} 		
					}*/ 
					
					
					// remove data on Teaching Roles ("L")
					studentSystemDAO.deleteRoles(record.getNetworkCode(), record.getCourse(), record.getAcadYear(), 
							record.getSemesterPeriod(), record.getRole(), record.getPersonSequence(),record.getPaperNo(),personForm);
										
					eventTrackingService.post(
							eventTrackingService.newEvent(EventTrackingTypes.EVENT_MAINTAINSTAFF_ROLE_REMOVE, "Role removed. Course: "+record.getCourse()+"-"+record.getAcadPeriodDesc()+" "+
									" Person: "+record.getNetworkCode()+" Role: "+record.getRole(), false), usageSession);
					
				}
			}
			
			studentSystemDAO.selectCourseList(personForm);
			request.setAttribute("courseList", personForm.getCourseList());
			
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", personForm.getPersonName()+" successfully removed from site(s)"));
			addMessages(request, messages);		
					
			return mapping.findForward("personDisplay");	
		} else {
			
			// go to confirm remove screen
			int count = 0;
			
			String courseSitesRemove = "";
			
			ArrayList courseList = personForm.getCourseList();
			//extra permission to Tracy and other people
			String superAccess = maintainStaffSakaiDAO.getUserPerms(userId,userEID);
			
			for (int i=0; i < courseList.size(); i++) {			
				RecordForm record = (RecordForm) courseList.get(i);
				if (record.isRemove()) {
					count++;
					
					try {
						if (!securityService.isSuperUser(userId)  ) {
					    	  if(!superAccess.equalsIgnoreCase("MAINTAIN")){
				         	// Ristrict the linker to link people only to their colleges
							collegeCodeMatch = studentSystemDAO.checkCollegeCodeMatch(record.getCourse().toUpperCase(), userEID.toUpperCase());

					       if (collegeCodeMatch == false) {
							messages.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("message.generalmessage", "You are not allowed to remove staff to this course as it is not part of your department's current academic responsibilities."));
							addErrors(request, messages);
							return mapping.findForward("personDisplay");
						  }
					      }
						}
					     }catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
					   }
					
					/* 22 Jul 2011 - Sy
					  *	add validation for all teaching roles except observer.
					  * if the user has outstanding assignments then the user may not be removed from the course
					 */
					if (personForm.getSelectedView().equals("L")) {
						//vijay: Do not check the marking percentage for the courseware admin and tutor roles along with oberver 
						if (!record.getRole().equals(OBSERVER) ||!record.getRole().equals("CADMIN") || !record.getRole().equals("TUTOR")) {
							// Validate outstanding assignments 
							if (record.isRemove()) {
								boolean outAssign = false;
								try {
									outAssign = studentSystemDAO.validatecurrentAssignOut(personForm.getPersonNetworkCode(),record.getAcadYear(),record.getCourse(),record.getSemesterPeriod());
								} catch (Exception e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								if (outAssign == true) {
									messages.add(ActionMessages.GLOBAL_MESSAGE,
											new ActionMessage("message.generalmessage",record.getCourse()+ " cannot be removed. There is an assignment mark percentage allocated to this person."));
									addErrors(request, messages);
			
									return mapping.findForward("personDisplay");
								}
							}	
							
						}
					}
				}
			}
			
			if (count == 0) {
				if (personForm.getSelectedView().equals("L")) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "No Course sites have been marked for removal.  Please click the check box next " +
									"to the course code you want to remove."));
				}else if (personForm.getSelectedView().equals("J")) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "No marking periods have been marked for removal.  Please click the check box next " +
									"to the course code you want to remove."));
				}
				else {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "No examination periods have been marked for removal.  Please click the check box next " +
									"to the course code you want to remove."));
				}
				addErrors(request, messages);
				return mapping.findForward("personDisplay");
			}
	 			
			request.setAttribute("prevAcadPeriod", personForm.getPrevAcadPeriod());
			
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Are you sure you want to remove this person from the following course(s)?"));
			addErrors(request, messages);
			
			request.setAttribute("courseList", personForm.getCourseList());
			
			return mapping.findForward("personConfirmRemove");
		}
	}
	
	/** Update roles of a person for a course site */
	public ActionForward updateRoles(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PersonForm personForm = (PersonForm) form;
		//CourseForm courseForm = (CourseForm) form;
		MaintainStaffStudentDAO studentSystemDAO = new MaintainStaffStudentDAO();
		MaintainStaffSakaiDAO maintainStaffSakaiDAO = new MaintainStaffSakaiDAO();
		MainForm mainForm = (MainForm) request.getSession().getAttribute("mainForm");
		ActionMessages messages = new ActionMessages();
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
		UsageSession usageSession = usageSessionService.getSession();
		//logged in user
		sessionManager = (SessionManager)ComponentManager.get(SessionManager.class);
		Session currentSession = sessionManager.getCurrentSession();
   		String userEID = currentSession.getUserEid();
   		securityService = (SecurityService) ComponentManager.get(SecurityService.class);
		String userId= usageSession.getUserId();
		String periodUpdated = "";
		int updatedRecords = 0;
		boolean collegeCodeMatch=false; 
		
		if ((personForm.getSelectedView()==null)||(personForm.getSelectedView().equals(""))) {
			personForm.setSelectedView("L");
		}	
		
		request.setAttribute("viewOptionList",personForm.getViewOptionList());
		request.setAttribute("roleOptions",personForm.getRoleOptions());
		
		personForm.setPrevAcadPeriod("x");
		
		ArrayList courseList = personForm.getCourseList();
		
		for (int i=0; i < courseList.size(); i++) {			
			RecordForm record = (RecordForm) courseList.get(i);
			
			if ((null == record.getRole())||(record.getRole().length()==0)) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Role may not be empty for "+record.getNetworkCode()));
				addErrors(request, messages);
				return mapping.findForward("personDisplay");
			}
			// verify that person is still active
			studentSystemDAO.selectPersonName(record);
			// is person active, if not he may not be linked to a course site.
			if (record.getStatus().equals("Inactive")) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", record.getNetworkCode()+" may not be linked as he/she is not an active staff member."));
				addErrors(request, messages);
				return mapping.findForward("personDisplay");
			}
			
			if (personForm.getSelectedView().equals("E")&&(record.getStaffNo().equals("0"))) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", record.getNetworkCode()+" may not be linked as he/she does not have a valid staff number."));
				addErrors(request, messages);
				return mapping.findForward("addPerson");				
			}
			
			//extra permission to Tracy and other people
			String superAccess = maintainStaffSakaiDAO.getUserPerms(userId,userEID);
			try {
				if (!securityService.isSuperUser(userId)  ) {
			    	  if(!superAccess.equalsIgnoreCase("MAINTAIN")){
		         	// Ristrict the linker to link people only to their colleges
					collegeCodeMatch = studentSystemDAO.checkCollegeCodeMatch(record.getCourse().toUpperCase(), userEID.toUpperCase());

			       if (collegeCodeMatch == false) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "You are not allowed to update staff to this course as it is not part of your department's current academic responsibilities."));
					addErrors(request, messages);
					return mapping.findForward("personDisplay");
				  }
			    	  }
			      }
			     }catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
			   }

			// update each record
			
			if (!record.getRole().equals(record.getPrevRole())) {
				studentSystemDAO.updateRoles(record.getNetworkCode(),record.getCourse(), record.getAcadYear(), record.getSemesterPeriod(),record.getRole(),record.getPersonSequence(),record.getPaperNo(),personForm,userEID,record.getPrevRole());
				
				if (periodUpdated.equals("")) {
					periodUpdated = record.getAcadPeriodDesc();
				} else {
					periodUpdated = periodUpdated+", "+record.getAcadPeriodDesc(); 
				}
				/*
				//insert Audit log
				String logTrail="Role/Function update: user= "+record.getNetworkCode()+ " new role= "+record.getRole()+" prev role= "+record.getPrevRole();
				db1.insertAuditLog(userID,record.getCourse(),personForm.getSelectedView(),record.getAcadYear(),record.getSemesterPeriod(),logTrail);
				**/
				updatedRecords++;
				
				eventTrackingService.post(
						eventTrackingService.newEvent(EventTrackingTypes.EVENT_MAINTAINSTAFF_ROLE_UPDATE, "Role update. Course: "+record.getCourse()+"-"+record.getAcadPeriodDesc()+" "+
								" Person: "+record.getNetworkCode()+" New role: "+record.getRole()+" prev role: "+record.getPrevRole(), false), usageSession);
				record.setPrevRole(record.getRole());
			}
		}
		
		if (updatedRecords >= 1) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Roles updated for period(s) ["+periodUpdated+"]"));
			addErrors(request, messages);
		} else {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "0 Roles updated.  No roles were marked for update."));
			addErrors(request, messages);
		}
		
		request.setAttribute("courseList", personForm.getCourseList());
			
		return mapping.findForward("personDisplay");		
	}
	
	public ActionForward back(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PersonForm personForm = (PersonForm) form;
		MaintainStaffStudentDAO db1 = new MaintainStaffStudentDAO();
		
		String tmpAtStep = request.getParameter("atstep");
		if (tmpAtStep.equals("save")) {
			// Get period dropdown list
			if (personForm.getSelectedView().equals("L") || personForm.getSelectedView().equals("J")) {
				// academic periods
				personForm.setPeriodList(db1.selectRegPeriodOptions());
				request.setAttribute("periodList", personForm.getPeriodList());
			} else {
				// examination periods
				personForm.setPeriodList(db1.selectExamPeriodOptions());
				request.setAttribute("paperNrOptions", personForm.getPaperNrOptions());
				request.setAttribute("periodList", personForm.getPeriodList());
			}
			
			request.setAttribute("yearList", personForm.getYearList());
			request.setAttribute("noOfRecordsOptions", personForm.getNoOfRecordsOptions());
			request.setAttribute("roleOptions", personForm.getRoleOptions());
			
			return mapping.findForward("addPerson");
		} else {
			request.setAttribute("viewOptionList", personForm.getViewOptionList());
			request.setAttribute("courseList", personForm.getCourseList());
			request.setAttribute("roleOptions",personForm.getRoleOptions());
			if (personForm.getSelectedView().equals("E")) {
				request.setAttribute("paperNrOptions", personForm.getPaperNrOptions());	
			}
			personForm.setNoOfRecords(0);
		
			return mapping.findForward("personDisplay");
		}
	}
	
	public ActionForward cancel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PersonForm personForm = (PersonForm) form;
		MaintainStaffStudentDAO db1 = new MaintainStaffStudentDAO();
				
		String tmpAtStep = request.getParameter("atstep").toString();
		if ((tmpAtStep.equals("add"))||(tmpAtStep.equals("save"))) {
			// select data for that course
			db1.selectCourseList(personForm);
			personForm.setPrevAcadPeriod("x");
			
			request.setAttribute("viewOptionList", personForm.getViewOptionList());
			request.setAttribute("courseList", personForm.getCourseList());
			request.setAttribute("roleOptions",personForm.getRoleOptions());
			personForm.setNoOfRecords(0);
			
			return mapping.findForward("personDisplay");
		} else {
			MainForm mainForm = (MainForm) request.getSession().getAttribute("mainForm");
			request.setAttribute("roleOptions",personForm.getRoleOptions());
			
			mainForm.resetFormbean(mapping, request);
			return mapping.findForward("mainDisplay");
		}
	}

	public ActionForward selectAll(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PersonForm personForm = (PersonForm) form;
		MaintainStaffStudentDAO db1 = new MaintainStaffStudentDAO();
		MainForm mainForm = (MainForm) request.getSession().getAttribute("mainForm");
		ActionMessages messages = new ActionMessages();
		request.setAttribute("viewOptionList",personForm.getViewOptionList());
		request.setAttribute("roleOptions",personForm.getRoleOptions());
		
		personForm.setPrevAcadPeriod("x");
		
		ArrayList courseList = personForm.getCourseList();
		for (int i=0; i < courseList.size(); i++) {			
			RecordForm record = (RecordForm) courseList.get(i);
			if (!record.getRole().equals("PRIML")) {
				record.setRemove(true);
			}
		}
		return mapping.findForward("personDisplay");
		
	}
	
	public ActionForward unSelectAll(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PersonForm personForm = (PersonForm) form;
		MaintainStaffStudentDAO db1 = new MaintainStaffStudentDAO();
		MainForm mainForm = (MainForm) request.getSession().getAttribute("mainForm");
		ActionMessages messages = new ActionMessages();
		request.setAttribute("viewOptionList",personForm.getViewOptionList());
		request.setAttribute("roleOptions",personForm.getRoleOptions());
		
		personForm.setPrevAcadPeriod("x");
		
		ArrayList courseList = personForm.getCourseList();
		for (int i=0; i < courseList.size(); i++) {			
			RecordForm record = (RecordForm) courseList.get(i);
			record.setRemove(false);
		}
		return mapping.findForward("personDisplay");
		
	}
	
	
	public ActionForward selectAllVisible(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PersonForm personForm = (PersonForm) form;
		MaintainStaffStudentDAO db1 = new MaintainStaffStudentDAO();
		MainForm mainForm = (MainForm) request.getSession().getAttribute("mainForm");
		ActionMessages messages = new ActionMessages();
		request.setAttribute("viewOptionList",personForm.getViewOptionList());
		request.setAttribute("roleOptions",personForm.getRoleOptions());
		
		personForm.setPrevAcadPeriod("x");
		
		ArrayList periodHeadings = personForm.getPeriodHeadings();
		for (int i=0; i < periodHeadings.size(); i++) {
			//LabelValueBean periodHead = (LabelValueBean) periodHeadings.get(i);
			HeadingForm headingForm = (HeadingForm) periodHeadings.get(i);
			if (headingForm.getExpand().equals("YES")) {
				ArrayList courseList = personForm.getCourseList();
				for (int j=0; j < courseList.size(); j++) {
					RecordForm record = (RecordForm) courseList.get(j);
					if (record.getAcadPeriodDesc().equals(headingForm.getPeriodDesc())) {
						if (!record.getRole().equals("PRIML")) {
							record.setRemove(true);
						}
					}
				}
			}
		}
		
		return mapping.findForward("personDisplay");
		
	}
	
	/* remove leading whitespace */
    public static String ltrim(String source) {
        return source.replaceAll("^\\s+", "");
    }

    /* remove trailing whitespace */
    public static String rtrim(String source) {
        return source.replaceAll("\\s+$", "");
    }
}
