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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import za.ac.unisa.lms.tools.maintainstaff.forms.RecordForm;

public class CourseAction extends LookupDispatchAction {

	private Log log = LogFactory.getLog(CourseAction.class.getName());
	private long timeInitialMillis = 0;
	private long timeExecuteMillis = 0;
	private long timeEndMillis = 0;
	
	private EventTrackingService eventTrackingService;
	private UsageSessionService usageSessionService;
	private SessionManager sessionManager;
	private SecurityService securityService;
	private final static String OBSERVER = "OBSRV";
	private final static String NULLVALUEERROR = "Unexpected error occurred please try again.";

	@Override
	protected Map getKeyMethodMap() {

		Map map = new HashMap();
		map.put("courseDisplay","courseDisplay");
		map.put("collapse","collapse");
		map.put("expand","expand");
		map.put("button.remove","removeRoles");
		map.put("button.update","updateRoles");
		map.put("button.cancel","cancel");
		map.put("button.back","back");
		map.put("addCourse", "addCourse");
		map.put("button.submit","submit");
		map.put("button.finish", "finish");
		map.put("addPersons","addPersons");
		map.put("addCourseVerify", "addCourseVerify");
		map.put("addPersonsVerify", "addPersonsVerify");
		map.put("addCourseSave", "addCourseSave");
		map.put("updatePrimLecturer", "updatePrimLecturer");
		map.put("viewLogs", "viewLogs");
		map.put("button.logsDisplay", "showLogs");
		map.put("selectAll","selectAll");
		map.put("unSelectAll","unSelectAll");
		map.put("selectAllVisible","selectAllVisible");
		map.put("totalSelected", "totalSelected");
		map.put("button.display","courseDisplay");

		return map;
	}

	public ActionForward execute(HttpServletRequest request,HttpServletResponse response,
			ActionMapping mapping,ActionForm form) throws Exception {
		if (request.getParameter("action") == null) return courseDisplay(mapping, form, request, response);
		return super.execute(mapping, form, request, response);
	}

	protected ActionForward unspecified(ActionMapping mapping,
			ActionForm form,javax.servlet.http.HttpServletRequest request,
			javax.servlet.http.HttpServletResponse response)
	throws java.lang.Exception{

		CourseForm courseForm = (CourseForm) form;
		MaintainStaffStudentDAO db1 = new MaintainStaffStudentDAO();
		MainForm mainForm = (MainForm) request.getSession().getAttribute("mainForm");
		if ((mainForm.getCourse() != null)&&(mainForm.getCourse().length() >= 1)) {
			courseForm.setCourse(mainForm.getCourse());
		}

		if ((courseForm.getSelectedView()==null)||(courseForm.getSelectedView().equals(""))) {
			courseForm.setSelectedView("L");
		}	

		request.setAttribute("viewOptionList",courseForm.getViewOptionList());
		request.setAttribute("roleOptions",courseForm.getRoleOptions());
        request.setAttribute("yearOptions", courseForm.getYearOptions());
		
		if(courseForm.getSelectedView().equalsIgnoreCase("L") ||courseForm.getSelectedView().equals("J")){
		request.setAttribute("acadPeriodOptions", courseForm.getAcadPeriodOptions());
		}
    	else {
			request.setAttribute("acadPeriodOptions", courseForm.getAcadPeriodOptions());
		}

		// select data for that course
		db1.selectCourseList(courseForm);

		courseForm.setPrevAcadPeriod("x");

		if (courseForm.getExpandedPeriods() == null) {
			courseForm.setExpandedPeriods(new ArrayList());
		}

		request.setAttribute("prevAcadPeriod", courseForm.getPrevAcadPeriod());
		courseForm.setDisplayCourse(false);

		return mapping.findForward("courseDisplay");

	}


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


	/**
	 * Method submit
	 * 		to decide what submit to perform
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward submit(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)throws Exception {

		String gotoMethod;
		String atStep = request.getParameter("atstep");
		if (atStep.equals("addpersonsconfirm")) {
			gotoMethod = addPersonsVerify(mapping,form,request, response);
			return mapping.findForward(gotoMethod);
		} else if (atStep.equals("updPrimLectConfirm")) {
			gotoMethod = updatePrimLecturerConfirm(mapping,form,request, response);
			return mapping.findForward(gotoMethod);
		}
		else {
			gotoMethod = addCourseVerify(mapping,form,request,response);
			return mapping.findForward(gotoMethod);
		}
	}

	/**
	 * Method finish
	 * 		to decide what finish to perform
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward finish(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)throws Exception {

		String gotoMethod;
		String atStep = request.getParameter("atstep");
		if (atStep.equals("addpersonssave")) {
			gotoMethod = addPersonsSave(mapping,form,request, response);
		} else {
			//return mapping.findForward("addCourseSave");
			gotoMethod = addCourseSave(mapping,form,request, response);
		}
		return mapping.findForward(gotoMethod);
	}


	/** Foward to course view + select of detail of course view */
	public ActionForward courseDisplay(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		this.timeEndMillis = System.currentTimeMillis();
		log.info("[MaintainStaff CourseAction: courseDisplay] [stamp="+timeEndMillis+"] [elapsed="+Long.toString(this.timeEndMillis - System.currentTimeMillis())+"]"); 
		
		CourseForm courseForm = (CourseForm) form;
		MaintainStaffStudentDAO db1 = new MaintainStaffStudentDAO();
		MaintainStaffSakaiDAO db2 = new MaintainStaffSakaiDAO();
		MainForm mainForm = (MainForm) request.getSession().getAttribute("mainForm");
		sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
		ActionMessages messages = new ActionMessages();
		// get user permission
		Session currentSession = sessionManager.getCurrentSession();
		log.info("[MaintainStaff CourseAction: courseDisplay sessionManager.getCurrentSession()] [stamp="+timeEndMillis+"] [elapsed="+Long.toString(this.timeEndMillis - System.currentTimeMillis())+"]"); 
		String userID = currentSession.getUserId();
		String userEID = currentSession.getUserEid();
		try {
			courseForm.setUserPermission(db2.getUserRights(userID,userEID));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if ((mainForm.getCourse() != null)&&(mainForm.getCourse().length() >= 1)) {
			courseForm.setCourse(mainForm.getCourse());
		}

		if ((courseForm.getSelectedView()==null)||(courseForm.getSelectedView().equals(""))) {
			courseForm.setSelectedView("L");
		}	

		courseForm.setCourse(courseForm.getCourse().toUpperCase());

		request.setAttribute("viewOptionList",courseForm.getViewOptionList());
		request.setAttribute("roleOptions",courseForm.getRoleOptions());
		request.setAttribute("yearOptions", courseForm.getYearOptions());
		
		if(courseForm.getSelectedView().equalsIgnoreCase("L") ||courseForm.getSelectedView().equals("J")){
		courseForm.setAcadPeriodOptions(db1.selectRegPeriodOptions());
		request.setAttribute("acadPeriodOptions", courseForm.getAcadPeriodOptions());
		} 
		else {
			courseForm.setAcadPeriodOptions(db1.selectExamPeriodOptions());
			request.setAttribute("acadPeriodOptions", courseForm.getAcadPeriodOptions());
			courseForm.setDisplayCourseDetails("");
		}
		
		String displaystep = request.getParameter("display");
		String changeview = request.getParameter("changeview");
		if(changeview.equals("")){
		if (displaystep.equals("displaycourse")) {
	
		// Verify that year was entered
		if ((null == courseForm.getYear())||(courseForm.getYear().length()==0)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please choose the Year"));
			addErrors(request, messages);
			courseForm.setDisplayCourseDetails("");
			return mapping.findForward("courseDisplay");
		}
		
		// Verify that year was entered
		if ((null == courseForm.getAcadPeriod())||(courseForm.getAcadPeriod().length()==0)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please choose the Period"));
			addErrors(request, messages);
			courseForm.setDisplayCourseDetails("");
			return mapping.findForward("courseDisplay");
		}	
		
		courseForm.setDisplayCourseDetails("displaycourse");
		// select data for that course
		db1.selectCourseList(courseForm);
		courseForm.setPeriodHeadings(courseForm.getPeriodHeadings());
		
		log.info("[MaintainStaff CourseAction: courseDisplay db1.selectCourseList] [stamp="+timeEndMillis+"] [elapsed="+Long.toString(this.timeEndMillis - System.currentTimeMillis())+"]");
		courseForm.setPrevAcadPeriod("x");
		//courseForm.setAcadPeriod("");
		courseForm.setPrimlNetworkCode("");
		courseForm.setPrimlStaffNo("");
		//courseForm.setPaperNo("");
		courseForm.setNoOfRecords(0);

		if (courseForm.getExpandedPeriods() == null) {
			log.info("[MaintainStaff CourseAction: courseDisplay getExpandedPeriods] [stamp="+timeEndMillis+"] [elapsed="+Long.toString(this.timeEndMillis - System.currentTimeMillis())+"]");
			courseForm.setExpandedPeriods(new ArrayList());
		}

		request.setAttribute("prevAcadPeriod", courseForm.getPrevAcadPeriod());
		courseForm.setDisplayCourse(false);
		log.info("[MaintainStaff CourseAction: END courseDisplay] [stamp="+timeEndMillis+"] [elapsed="+Long.toString(this.timeEndMillis - System.currentTimeMillis())+"]");
		}
		}else{
			courseForm.setAcadPeriod("");
			courseForm.setYear("");
			courseForm.setDisplayCourseDetails("");
		}
		return mapping.findForward("courseDisplay");
	}

	/** Forward to add course screen */
	public ActionForward addCourse(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		
		this.timeEndMillis = System.currentTimeMillis();
		log.info("[MaintainStaff CourseAction: addCourse] [stamp="+timeEndMillis+"] [elapsed="+Long.toString(this.timeEndMillis - System.currentTimeMillis())+"]"); 

		CourseForm courseForm = (CourseForm) form;
		MaintainStaffStudentDAO db1 = new MaintainStaffStudentDAO();
		String addMembersList = request.getParameter("addMembers");
		if(null==addMembersList){
		courseForm.setAcadPeriod("");
		courseForm.setYear("");
		}
		if ((courseForm.getSelectedView()==null)||(courseForm.getSelectedView().equals(""))) {
			courseForm.setSelectedView("L");
		}	

		if (courseForm.getCourse().equals("")) {
			courseForm.setDisplayCourse(true);
		}

		if (courseForm.getNoOfRecords() == 0) {
			ArrayList tmpArray = new ArrayList();
			courseForm.setCourseList(tmpArray);
		}

		// Get period dropdown list
		if (courseForm.getSelectedView().equals("L") ||courseForm.getSelectedView().equals("J")) {
			// academic periods
			courseForm.setAcadPeriodOptions(db1.selectRegPeriodOptions());
			log.info("[MaintainStaff CourseAction: addCourse selectRegPeriodOptions] [stamp="+timeEndMillis+"] [elapsed="+Long.toString(this.timeEndMillis - System.currentTimeMillis())+"]");
			request.setAttribute("acadPeriodOptions", courseForm.getAcadPeriodOptions());
		} else if (courseForm.getSelectedView().equals("J")) {
			//Marking function are same as academic periods
			courseForm.setAcadPeriodOptions(db1.selectRegPeriodOptions());
			log.info("[MaintainStaff CourseAction: addCourse selectRegPeriodOptions] [stamp="+timeEndMillis+"] [elapsed="+Long.toString(this.timeEndMillis - System.currentTimeMillis())+"]");
			request.setAttribute("acadPeriodOptions", courseForm.getAcadPeriodOptions());
		}else {
			// examination periods
			courseForm.setAcadPeriodOptions(db1.selectExamPeriodOptions());
			log.info("[MaintainStaff CourseAction: addCourse selectExamPeriodOptions] [stamp="+timeEndMillis+"] [elapsed="+Long.toString(this.timeEndMillis - System.currentTimeMillis())+"]");
			request.setAttribute("acadPeriodOptions", courseForm.getAcadPeriodOptions());
		}

		// get Role options
		request.setAttribute("roleOptions",courseForm.getRoleOptions());
		// get Year options
		request.setAttribute("yearOptions", courseForm.getYearOptions());

		// get Paper number options
		if (courseForm.getSelectedView().equals("E")) {
			log.info("[MaintainStaff CourseAction: addCourse getSelectedView] [stamp="+timeEndMillis+"] [elapsed="+Long.toString(this.timeEndMillis - System.currentTimeMillis())+"]");
			courseForm.setPaperNrOptions(courseForm.getPaperNrOptions());
		}

		request.setAttribute("paperNrOptions", courseForm.getPaperNrOptions());

		// set number of persons that's going to be added
		ArrayList courseList = new ArrayList();
		if ((null == courseForm.getCourseList())||(courseForm.getCourseList().size()==0)) {
			for (int i=0; i < courseForm.getNoOfRecords(); i++) {
				RecordForm record = new RecordForm();
				record.setAcadPeriodDesc(courseForm.getAcadPeriod());
				record.setAcadYear(courseForm.getYear());
				record.setCourse(courseForm.getCourse());
				record.setHeading("Add Staff Member ("+(i+1)+" of "+courseForm.getNoOfRecords()+")");

				courseList.add(record);
			}
		} else {
			if (courseForm.getCourseList().size() != courseForm.getNoOfRecords()) {
				for (int i=0; i < courseForm.getNoOfRecords(); i++) {
					RecordForm record = new RecordForm();
					record.setAcadPeriodDesc(courseForm.getAcadPeriod());
					record.setAcadYear(courseForm.getYear());
					record.setCourse(courseForm.getCourse());
					record.setHeading("Add Staff Member ("+(i+1)+" of "+courseForm.getNoOfRecords()+")");

					courseList.add(record);
				}
				for (int i=0; i < courseForm.getCourseList().size(); i++) {
					RecordForm record = (RecordForm) courseForm.getCourseList().get(i);
					if (i < courseForm.getNoOfRecords()) {
						RecordForm record2 = (RecordForm) courseList.get(i);
						record2.setStaffNo(record.getStaffNo());
						record2.setNetworkCode(record.getNetworkCode());

						courseList.set(i, record2);
					}
				}
			}
		}
		log.info("[MaintainStaff CourseAction: addCourse setCourseList] [stamp="+timeEndMillis+"] [elapsed="+Long.toString(this.timeEndMillis - System.currentTimeMillis())+"]");

		courseForm.setCourseList(courseList);

		request.setAttribute("noOfRecordsOptions", courseForm.getNoOfRecordsOptions());

		log.info("[MaintainStaff CourseAction: addCourse END] [stamp="+timeEndMillis+"] [elapsed="+Long.toString(this.timeEndMillis - System.currentTimeMillis())+"]");
		return mapping.findForward("addCourseSite");
	}

	/** Forward to Update primary lecturer screen */
	public ActionForward updatePrimLecturer(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
		UsageSession usageSession = usageSessionService.getSession();
		securityService = (SecurityService) ComponentManager.get(SecurityService.class);
		String userEID = usageSession.getUserEid();
		String userId = usageSession.getUserId();
		boolean collegeCodeMatch=false; 
		this.timeEndMillis = System.currentTimeMillis();
		log.info("[MaintainStaff CourseAction: updatePrimLecturer] [stamp="+timeEndMillis+"] [elapsed="+Long.toString(this.timeEndMillis - System.currentTimeMillis())+"]"); 
		
		CourseForm courseForm = (CourseForm) form;
		MaintainStaffStudentDAO studentSystemDAO = new MaintainStaffStudentDAO();
		MaintainStaffSakaiDAO maintainStaffSakaiDAO = new MaintainStaffSakaiDAO();
		ActionMessages messages = new ActionMessages();

		request.setAttribute("viewOptionList",courseForm.getViewOptionList());
		request.setAttribute("roleOptions",courseForm.getRoleOptions());
        request.setAttribute("yearOptions", courseForm.getYearOptions());
		
		if(courseForm.getSelectedView().equalsIgnoreCase("L")){
		request.setAttribute("acadPeriodOptions", courseForm.getAcadPeriodOptions());
		} else {
			request.setAttribute("acadPeriodOptions", courseForm.getAcadPeriodOptions());
		}
		
		//extra permission to Tracy and other people
		String superAccess = maintainStaffSakaiDAO.getUserPerms(userId,userEID);

		// Ristrict the linker to link people only to their colleges
		  try {
		      if (!securityService.isSuperUser(userId)  ) {
		    	  if(!superAccess.equalsIgnoreCase("MAINTAIN")){
		    	 // !(institution.equals("UNISA"))
	         	// Ristrict the linker to link people only to their colleges
				collegeCodeMatch = studentSystemDAO.checkCollegeCodeMatch(courseForm.getCourse().toUpperCase(), userEID.toUpperCase());
				log.info("MaintainStaff CourseAction: updatePrimLecturer.checkCollegeCodeMatch ");

		       if (collegeCodeMatch == false) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "  You are not allowed to update primary lecturer to this course as it is not part of your department's current academic responsibilities."));
				addErrors(request, messages);
				courseForm.setCourse("");
				return mapping.findForward("courseDisplay");	
			   }
		      }
		      }
		     }catch (Exception e) {
		    	 log.error("MaintainStaff CourseAction addCourseVerify: Failed to check the admin permissions or checkCollegeCodeMatch "+userEID.toUpperCase());
		    	 e.printStackTrace();
		   }

		if (courseForm.getSelectedPeriod() != null) {	
			log.info("[MaintainStaff CourseAction: getSelectedPeriod] [stamp="+timeEndMillis+"] [elapsed="+Long.toString(this.timeEndMillis - System.currentTimeMillis())+"]");
			if(courseForm.getSelectedPeriod().equals("-1")){

			}else{
				// select primary lecturer for this course

				String[] tmp = courseForm.getSelectedPeriod().split("#");
				courseForm.setYear(tmp[0]);
				courseForm.setAcadPeriod(tmp[1]);

				//selectPerson
				//db1.selectPrimPersonName(courseForm);
				studentSystemDAO.selectPerson(courseForm, "PRIML");
				log.info("[MaintainStaff CourseAction: selectPerson] [stamp="+timeEndMillis+"] [elapsed="+Long.toString(this.timeEndMillis - System.currentTimeMillis())+"]");

				// select all linked staff to this course site as options for new primary lecturer.
				courseForm.setPrimLecturerOptions(studentSystemDAO.selectPrimLecturerOptions(courseForm.getYear(), courseForm.getAcadPeriod(), courseForm.getCourse(), courseForm.getSelectedView()));
				log.info("[MaintainStaff CourseAction: db1.selectPrimLecturerOptions] [stamp="+timeEndMillis+"] [elapsed="+Long.toString(this.timeEndMillis - System.currentTimeMillis())+"]");

			}}

		// select period options that exist for selected course
		courseForm.setAcadPeriodOptions(studentSystemDAO.selectPeriodOptions(courseForm.getCourse(),courseForm.getSelectedView()));
		log.info("[MaintainStaff CourseAction: db1.selectPeriodOptions] [stamp="+timeEndMillis+"] [elapsed="+Long.toString(this.timeEndMillis - System.currentTimeMillis())+"]");
		if ((courseForm.getAcadPeriodOptions() == null)||(courseForm.getAcadPeriodOptions().size() == 0)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "No course sites for this course, primary lecturer cannot be updated."));
			addErrors(request, messages);
			log.info("[MaintainStaff CourseAction: db1.selectPeriodOptions Exception] [stamp="+timeEndMillis+"] [elapsed="+Long.toString(this.timeEndMillis - System.currentTimeMillis())+"]");
			return mapping.findForward("courseDisplay");		
		}

		request.setAttribute("acadPeriodOptions", courseForm.getAcadPeriodOptions());
		request.setAttribute("primLecturerOptions",courseForm.getPrimLecturerOptions());


		courseForm.setNewPrimlName("");
		courseForm.setNewPrimlNetworkCode("");
		courseForm.setNewPrimlStaffNo("");
		courseForm.setKeepPriml("No");
		
		log.info("[MaintainStaff CourseAction: END] [stamp="+timeEndMillis+"] [elapsed="+Long.toString(this.timeEndMillis - System.currentTimeMillis())+"]");
		
		return mapping.findForward("updatePrimLecturer");
	}

	/** Check data that must change and go to confirm update screen */
	public String updatePrimLecturerConfirm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		
		this.timeEndMillis = System.currentTimeMillis();
		log.info("[MaintainStaff CourseAction: updatePrimLecturerConfirm] [stamp="+timeEndMillis+"] [elapsed="+Long.toString(this.timeEndMillis - System.currentTimeMillis())+"]"); 
		
		CourseForm courseForm = (CourseForm) form;
		RecordForm record= new RecordForm();
		MaintainStaffStudentDAO db1 = new MaintainStaffStudentDAO();
		ActionMessages messages = new ActionMessages();
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
		UsageSession usageSession = usageSessionService.getSession();
		//logged in user
		sessionManager = (SessionManager)ComponentManager.get(SessionManager.class);
		Session currentSession = sessionManager.getCurrentSession();
		String userID = currentSession.getUserEid();
		log.info("[MaintainStaff CourseAction: updatePrimLecturerConfirm sessionManager.getCurrentSession()] [stamp="+timeEndMillis+"] [elapsed="+Long.toString(this.timeEndMillis - System.currentTimeMillis())+"]");

		request.setAttribute("primLecturerOptions",courseForm.getPrimLecturerOptions());
		request.setAttribute("viewOptionList",courseForm.getViewOptionList());
		request.setAttribute("roleOptions",courseForm.getRoleOptions());
        request.setAttribute("yearOptions", courseForm.getYearOptions());
        request.setAttribute("acadPeriodOptions", courseForm.getAcadPeriodOptions());


        /* ******************************************************************************************************** */
        /* Validate that registration period was selected */
		String def="-1";	
		if (courseForm.getSelectedPeriod() == null||courseForm.getSelectedPeriod().equals(def)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please select the registration period."));
			addErrors(request, messages);
			return "updatePrimLecturer";
		}

		/* ******************************************************************************************************** */
        /* Select current primary lecturer */
		if (courseForm.getSelectedPeriod() != null) {	

			String[] tmp = courseForm.getSelectedPeriod().split("#");
			courseForm.setYear(tmp[0]);
			courseForm.setAcadPeriod(tmp[1]);

			//selectPerson
			db1.selectPerson(courseForm, "PRIML");
			log.info("[MaintainStaff CourseAction: updatePrimLecturerConfirm db1.selectPerson PRIML] [stamp="+timeEndMillis+"] [elapsed="+Long.toString(this.timeEndMillis - System.currentTimeMillis())+"]");

		}

		courseForm.setCourseSite(courseForm.getSelectedView());
		// remove leading and trailing spaces from networkcode
		courseForm.setNewPrimlNetworkCode(ltrim(courseForm.getNewPrimlNetworkCode()));
		courseForm.setNewPrimlNetworkCode(rtrim(courseForm.getNewPrimlNetworkCode()));

		/* ******************************************************************************************************** */
        /* Was new primary lecturer code entered OR selected from existing drop down */
		if ((null == courseForm.getNewPrimlNetworkCode()||(courseForm.getNewPrimlNetworkCode().length()==0))
				&&(null == courseForm.getNewPrimlNetworkCode2()||(courseForm.getNewPrimlNetworkCode2().length()==0))) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Primary Lecturer information is not complete."));
			addErrors(request, messages);
			log.error("[MaintainStaff CourseAction: updatePrimLecturerConfirm Exception verify primary lecturer info was completed] [stamp="+timeEndMillis+"] [elapsed="+Long.toString(this.timeEndMillis - System.currentTimeMillis())+"]");
			return "updatePrimLecturer";
		}

		if (null == courseForm.getNewPrimlNetworkCode()) {
			courseForm.setNewPrimlNetworkCode("");
		}
		if (null == courseForm.getNewPrimlNetworkCode2()) {
			courseForm.setNewPrimlNetworkCode2("");
		}

		/* ******************************************************************************************************** */
		/* ONLY one may be selected/entered
        /* Was new primary lecturer code entered OR selected from existing drop down */
		if ((courseForm.getNewPrimlNetworkCode().length() > 1)&&(courseForm.getNewPrimlNetworkCode2().length()>1)) {
			if (!courseForm.getNewPrimlNetworkCode().equals(courseForm.getNewPrimlNetworkCode2())) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Please choose only one option for new primary lecturer, do not complete both."));
				log.error("[MaintainStaff CourseAction: updatePrimLecturerConfirm Exception choose only one option for new primary lecturer] [stamp="+timeEndMillis+"] [elapsed="+Long.toString(this.timeEndMillis - System.currentTimeMillis())+"]");
				addErrors(request, messages);
				return "updatePrimLecturer";			
			}
		}

		boolean existingUserUsed = false;
		if (courseForm.getNewPrimlNetworkCode2().length() > 0) {
			courseForm.setNewPrimlNetworkCode(courseForm.getNewPrimlNetworkCode2());
			existingUserUsed = true;
		}

		/* ******************************************************************************************************** *
		 * Validate new primary lecturer
         * 	Is the network code valid
         *  */
		String msg = db1.staffValidation(courseForm.getNewPrimlNetworkCode());
		log.info("[MaintainStaff CourseAction: updatePrimLecturerConfirm db1.staffValidation(courseForm.getNewPrimlNetworkCode())] [stamp="+timeEndMillis+"] [elapsed="+Long.toString(this.timeEndMillis - System.currentTimeMillis())+"]");
		if (msg.length() >=1) {
			if (msg.equals("Error reading staff record.")) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Staff information is not valid ("+courseForm.getNewPrimlNetworkCode()+").  " +
						"Verify network code was correctly entered."));
				addErrors(request, messages);
				log.error("[MaintainStaff CourseAction: updatePrimLecturerConfirm db1.staffValidation(courseForm.getNewPrimlNetworkCode() Exeption Error reading staff record)] [stamp="+timeEndMillis+"] [elapsed="+Long.toString(this.timeEndMillis - System.currentTimeMillis())+"]");
				courseForm.setNewPrimlNetworkCode("");
				courseForm.setNewPrimlNetworkCode2("");
				return "updatePrimLecturer";
			} else {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", msg));
				addErrors(request, messages);
				log.error("[MaintainStaff CourseAction: updatePrimLecturerConfirm db1.staffValidation(courseForm.getNewPrimlNetworkCode() Exeption)] [stamp="+timeEndMillis+"] [elapsed="+Long.toString(this.timeEndMillis - System.currentTimeMillis())+"]");
				courseForm.setNewPrimlNetworkCode("");
				courseForm.setNewPrimlNetworkCode2("");
				return "updatePrimLecturer";
			}
		} // end of if (msg.length() >=1) {

		/* ******************************************************************************************************** *
		 * retrieve new primary lecturer details
         *  active status will also be set (newPrimlStatus; 
         *  also returns the correct staff number - NewPrimlStaffNo
         *  */
		db1.selectPersonName(courseForm);
		log.info("[MaintainStaff CourseAction: updatePrimLecturerConfirm db1.selectPersonName] [stamp="+timeEndMillis+"] [elapsed="+Long.toString(this.timeEndMillis - System.currentTimeMillis())+"]");

		
		/* ******************************************************************************************************** *
		 * if the new primary lecturer is inactive he may not be linked to the module
		 * */	
		if (courseForm.getNewPrimlStatus().equals("Inactive")) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", courseForm.getNewPrimlNetworkCode()+" may not be used as he/she is not an active staff member."));
			addErrors(request, messages);
			log.error("[MaintainStaff CourseAction: updatePrimLecturerConfirm db1.selectPersonName Inactive] [stamp="+timeEndMillis+"] [elapsed="+Long.toString(this.timeEndMillis - System.currentTimeMillis())+"]");
			courseForm.setNewPrimlNetworkCode("");
			courseForm.setNewPrimlNetworkCode2("");
			return "updatePrimLecturer";
		} //end of if (courseForm.getNewPrimlStatus().equals("Inactive")) {

		//String pl=courseForm.getPrimlNetworkCode().toUpperCase();
		//String plNew=courseForm.getNewPrimlNetworkCode().toUpperCase();

		/* ******************************************************************************************************** *
		 * Check if the lecturer is not already linked as the primary lecturer
		 * */	
		if(courseForm.getPrimlNetworkCode().equals(courseForm.getNewPrimlNetworkCode())){

			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", " This network code ("+courseForm.getNewPrimlNetworkCode()+") already exists as primary lecturer for this registration period. Please choose another user. "));
			addErrors(request, messages);
			log.error("[MaintainStaff CourseAction: updatePrimLecturerConfirm db1.selectPersonName Exception Already exist] [stamp="+timeEndMillis+"] [elapsed="+Long.toString(this.timeEndMillis - System.currentTimeMillis())+"]");
			courseForm.setNewPrimlNetworkCode("");
			return "updatePrimLecturer";

		} // end of if(courseForm.getPrimlNetworkCode().equals(courseForm.getNewPrimlNetworkCode())){

		/* ******************************************************************************************************** *
		 * Verify that the new primary lecturer is not already linked to this site
		 * */
		boolean isDuplicate = db1.isDuplicatePerson(courseForm.getCourse(), courseForm.getYear(), courseForm.getAcadPeriod(), 
				courseForm.getNewPrimlNetworkCode(), courseForm.getSelectedView(),"");
		log.info("[MaintainStaff CourseAction: updatePrimLecturerConfirm  db1.isDuplicatePerson] [stamp="+timeEndMillis+"] [elapsed="+Long.toString(this.timeEndMillis - this.timeExecuteMillis)+"]");
		if ((isDuplicate == true)&&(existingUserUsed == false)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", " This network code ("+courseForm.getNewPrimlNetworkCode()+") is already linked to this module and registration period. Please select the username from the dropdown list. "));
			addErrors(request, messages);
			log.error("[MaintainStaff CourseAction: updatePrimLecturerConfirm db1.selectPersonName Exception Already linked to module] [stamp="+timeEndMillis+"] [elapsed="+Long.toString(this.timeEndMillis - System.currentTimeMillis())+"]");
			courseForm.setNewPrimlNetworkCode("");
			return "updatePrimLecturer";			
		}

		/* ******************************************************************************************************** *
		 * If option to keep current primary lecturer was chosen
		 * */
		if (courseForm.getKeepPriml().equals("Yes")){
			
			/* ******************************************************************************************************** *
			 * If option to keep current primary lecturer was chosen
			 * */
			db1.selectPrimPersonName(courseForm);
			log.info("[MaintainStaff CourseAction: updatePrimLecturerConfirm db1.selectPrimPersonName(courseForm)] [stamp="+timeEndMillis+"] [elapsed="+Long.toString(this.timeEndMillis - System.currentTimeMillis())+"]");

			/* ******************************************************************************************************** *
			 * If current primary lecturer is Inactive
			 * */
			if (courseForm.getPrimlStatus().equalsIgnoreCase("Inactive")){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage","The Current Primary Lecturer "+courseForm.getPrimlNetworkCode()+" is not an active staff member. You cannot keep him/her on this course site  "));

				addErrors(request, messages);

				return "updatePrimLecturer";

			} // end of if (courseForm.getPrimlStatus().equalsIgnoreCase("Inactive")){
			
			/* ******************************************************************************************************** *
			 * If current primary lecturer is Active
			 * */
			if (courseForm.getPrimlStatus().equalsIgnoreCase("Active")){
				// select sequence
				int sequence =db1.selectSpecificUserSequence(courseForm.getSelectedView(),courseForm.getPrimlNetworkCode(), courseForm.getCourse(), courseForm.getYear(),courseForm.getAcadPeriod());
				
				//update current current PRIML to SECDL
				if (!courseForm.getPrimlNetworkCode().equals("")) {
					db1.updateRoles(courseForm.getPrimlNetworkCode(), courseForm.getCourse(), courseForm.getYear(),courseForm.getAcadPeriod(), 
							"SECDL",sequence,courseForm.getPaperNo(),courseForm);
					log.info("[MaintainStaff CourseAction: updatePrimLecturerConfirm  db1.updateRoles] [stamp="+timeEndMillis+"] [elapsed="+Long.toString(this.timeEndMillis - this.timeExecuteMillis)+"]");
	
					// Insert Audit Log for updating existing PRIML to SECDL
					String logTrail1="Role/Function updated (Primary lecture change): user= "+courseForm.getPrimlNetworkCode()+ " prev role= "+record.getPrevRole() +" "+"new role="+ record.getRole() 
					+"(changed by  " + userID +")";
				} //end of if if (!courseForm.getPrimlNetworkCode().equals("")) {
			} // end of if (courseForm.getPrimlStatus().equalsIgnoreCase("Inactive")){
			
		} // end of if (courseForm.getKeepPriml().equals("Yes")){
		

		/* ******************************************************************************************************** *
		 * If option to delete current primary lecturer was chosen
		 * */
		if (courseForm.getKeepPriml().equals("No")){
			/* ******************************************************************************************************** *
			* is there outstanding assignments marking for the current primary lecturer 
			* */
			boolean outAssign = false;
			try {
				outAssign = db1.validatecurrentAssignOut(courseForm.getPrimlNetworkCode(),courseForm.getYear(),courseForm.getCourse(),courseForm.getAcadPeriod());
				log.info("[MaintainStaff CourseAction: updatePrimLecturerConfirm db1.validatecurrentAssignOut()] [stamp="+timeEndMillis+"] [elapsed="+Long.toString(this.timeEndMillis - System.currentTimeMillis())+"]");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} // end of try catch

			// if there is outstanding assignment he may not be removed
			if (outAssign == true) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",courseForm.getPrimlNetworkCode()+ " cannot be removed. There are assignment marking percentages allocated to this person."+ 
						"Use the Assignment Marker Reallocation tool to change marking responsibility for this person to zero for every assignment in this academic period."));
				addErrors(request, messages);
				log.error("[MaintainStaff CourseAction: updatePrimLecturerConfirm db1.validatecurrentAssignOut() Exception] [stamp="+timeEndMillis+"] [elapsed="+Long.toString(this.timeEndMillis - System.currentTimeMillis())+"]");
				return "updatePrimLecturer";
			} // end of if (outAssign == true) {
			
			// if there is no outstanding assignments remove the current primary lecturer
			if (outAssign == false) {
				if (!courseForm.getPrimlNetworkCode().equals("")) {
					db1.deleteRoles(courseForm.getPrimlNetworkCode(), courseForm.getCourse(), courseForm.getYear(), courseForm.getAcadPeriod(), "PRIML", -1,"",courseForm);
					log.info("[MaintainStaff CourseAction: updatePrimLecturerConfirm db1.deleteRoles] [stamp="+timeEndMillis+"] [elapsed="+Long.toString(this.timeEndMillis - this.timeExecuteMillis)+"]");
				} // end of if (!courseForm.getPrimlNetworkCode().equals(""))

			} // end of if (outAssign == true) {
			
		} // end of if (courseForm.getKeepPriml().equals("No")){
		
		/* ******************************************************************************************************** *
		 * If existing user is going to become the primary lecturer
		 * */
		if (existingUserUsed == true) {
			int sequence=db1.selectSpecificUserSequence(courseForm.getSelectedView(),courseForm.getNewPrimlNetworkCode2(), courseForm.getCourse(), courseForm.getYear(),courseForm.getAcadPeriod());
			log.info("[MaintainStaff CourseAction: updatePrimLecturerConfirm db1.selectSpecificUserSequence] [stamp="+timeEndMillis+"] [elapsed="+Long.toString(this.timeEndMillis - this.timeExecuteMillis)+"]");
			//update already existing site member
			db1.updateRoles(courseForm.getNewPrimlNetworkCode2(), courseForm.getCourse(), courseForm.getYear(),courseForm.getAcadPeriod(), 
					"PRIML",sequence,courseForm.getPaperNo(),courseForm);

			//Insert Audit Log for updating existing site member to PRML
			String logTrail="Role/Function update(site member change): user= "+courseForm.getNewPrimlNetworkCode2()+ " new role= "+record.getRole()+" prev role= "+record.getPrevRole()+"(changed by  " + userID +")";
			db1.insertAuditLog(userID,courseForm.getCourse(),courseForm.getSelectedView(),courseForm.getYear(),courseForm.getAcadPeriod(),logTrail);
			
		} // end of if (existingUserUsed == true) {
		
		
		/* ******************************************************************************************************** *
		 * If new entered user is going to become the primary lecturer
		 * */
		if (existingUserUsed == false) {			
			String errorOccurred = db1.insertRole(courseForm.getNewPrimlNetworkCode(), courseForm.getNewPrimlStaffNo(), courseForm.getCourse(),
					courseForm.getYear(), courseForm.getAcadPeriod(), "PRIML", courseForm.getSelectedView(),courseForm.getPaperNo());

			if (errorOccurred.length()>=1) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", NULLVALUEERROR));
				addErrors(request, messages);
				courseForm.setNewPrimlNetworkCode("");
				return "updatePrimLecturer";				
			} // if (errorOccurred.length()>=1) {

			//insert Audit log for new primary lecture when yes keep 
			String logTrail3="Role/Function added  (Primary lecture change): user= "+courseForm.getNewPrimlNetworkCode()+ " role= "+"PRIML " +"(changed by  " + userID +")";
			db1.insertAuditLog(userID,courseForm.getCourse(),courseForm.getSelectedView(), courseForm.getYear(),courseForm.getAcadPeriod(),logTrail3);
		
		} // end of if (existingUserUsed == true) {			
			

        /* ******************************************************************************************************** */
        /* new primary lecturer update, reset all values */
		courseForm.setAcadPeriodDesc(courseForm.getSelectedView());
		eventTrackingService.post(
				eventTrackingService.newEvent(EventTrackingTypes.EVENT_MAINTAINSTAFF_ROLE_UPDATE_PRIML, "Role updated. Course: "+courseForm.getCourse()+"-"+courseForm.getAcadPeriodDesc()+" "+
						" New Person: "+courseForm.getNewPrimlNetworkCode()+" Prev Person: "+ courseForm.getPrimlNetworkCode()+"Role: PRIML", false), usageSession);

		request.setAttribute("viewOptionList",courseForm.getViewOptionList());
		request.setAttribute("roleOptions",courseForm.getRoleOptions());
        request.setAttribute("yearOptions", courseForm.getYearOptions());
		
		if(courseForm.getSelectedView().equalsIgnoreCase("L") ||courseForm.getSelectedView().equals("J")){
		request.setAttribute("acadPeriodOptions", courseForm.getAcadPeriodOptions());
		} else {
			request.setAttribute("acadPeriodOptions", courseForm.getAcadPeriodOptions());
		}
		String[] tmp = courseForm.getSelectedPeriod().split("#");
		courseForm.setYear(tmp[0]);
		courseForm.setAcadPeriod(tmp[1]);

		// select data for that course
		db1.selectCourseList(courseForm);

		courseForm.setPrevAcadPeriod("x");

		if (courseForm.getExpandedPeriods() == null) {
			courseForm.setExpandedPeriods(new ArrayList());
		}

		request.setAttribute("prevAcadPeriod", courseForm.getPrevAcadPeriod());
		
	
		messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", "Primary Lecturer for "+courseForm.getCourseSite()+" successfully added."));
		addMessages(request, messages);
		courseForm.setAcadPeriodOptions(db1.selectRegPeriodOptions());
		request.setAttribute("acadPeriodOptions", courseForm.getAcadPeriodOptions());
		
		request.setAttribute("yearOptions", courseForm.getYearOptions());
		courseForm.setSelectedPeriod(null);
		courseForm.setPrimlNetworkCode("");
		courseForm.setPrimlName("");
		courseForm.setNewPrimlName("");
		courseForm.setNewPrimlNetworkCode("");
		courseForm.setNewPrimlNetworkCode2("");

		return "courseDisplay";
	}

	/** Verify detail that must be added for a new course and go to confirmation screen */
	public String addCourseVerify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CourseForm courseForm = (CourseForm) form;
		ActionMessages messages = new ActionMessages();
		MaintainStaffStudentDAO studentSystemDAO = new MaintainStaffStudentDAO();
		MaintainStaffSakaiDAO maintainStaffSakaiDAO = new MaintainStaffSakaiDAO();
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
		UsageSession usageSession = usageSessionService.getSession();
		securityService = (SecurityService) ComponentManager.get(SecurityService.class);
		String userEID = usageSession.getUserEid();
		String userId = usageSession.getUserId();
		boolean collegeCodeMatch=false; 
		
		String tmpAtStep = request.getParameter("atstep").toString();
		courseForm.setCourse(courseForm.getCourse().toUpperCase());
		
		// Get period dropdown list
		if (courseForm.getSelectedView().equals("L")) {
			// academic periodsVerify detail that must be added for a new course
			courseForm.setAcadPeriodOptions(studentSystemDAO.selectRegPeriodOptions());
			request.setAttribute("acadPeriodOptions", courseForm.getAcadPeriodOptions());
		} else {
			// examination periods
			courseForm.setAcadPeriodOptions(studentSystemDAO.selectExamPeriodOptions());
			request.setAttribute("acadPeriodOptions", courseForm.getAcadPeriodOptions());
		}
		
		// remove all leading and trailing spaces
		courseForm.setCourse(ltrim(courseForm.getCourse()));
		courseForm.setCourse(rtrim(courseForm.getCourse()));
		courseForm.setCourse(courseForm.getCourse().replace('\'',' '));
		courseForm.setPrimlNetworkCode(ltrim(courseForm.getPrimlNetworkCode()));
		courseForm.setPrimlNetworkCode(rtrim(courseForm.getPrimlNetworkCode()));
		courseForm.setPrimlNetworkCode(courseForm.getPrimlNetworkCode().replace('\'',' '));
		courseForm.setNewPrimlNetworkCode(courseForm.getPrimlNetworkCode());

		if ((courseForm.getSelectedView()==null)||(courseForm.getSelectedView().equals(""))) {
			courseForm.setSelectedView("L");
		}	
		
			// verify data entered for new course site
			
			request.setAttribute("acadPeriodOptions", courseForm.getAcadPeriodOptions());
			request.setAttribute("noOfRecordsOptions", courseForm.getNoOfRecordsOptions());
			request.setAttribute("roleOptions",courseForm.getRoleOptions());
			request.setAttribute("yearOptions", courseForm.getYearOptions());
			
			
			// verify that course code was entered
			if ((null == courseForm.getCourse())||(courseForm.getCourse().length()==0)) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Course information is not completed."));
				addErrors(request, messages);
				return "addCourseSite";
			}
			
			// Verify that year was entered
			if ((null == courseForm.getYear())||(courseForm.getYear().length()==0)) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Please choose the Year"));
				addErrors(request, messages);
				return "addCourseSite";
			}
			
			// Verify that year was entered
			if ((null == courseForm.getAcadPeriod())||(courseForm.getAcadPeriod().length()==0)) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Please choose the Period"));
				addErrors(request, messages);
				return "addCourseSite";
			}	
			
			// verify that the course code is valid
			courseForm.setCourse(courseForm.getCourse().toUpperCase());
			boolean courseValid = studentSystemDAO.courseExist(courseForm.getCourse());
			if (courseValid == false) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Course code ("+courseForm.getCourse()+") is not valid.  Please verify correct code and re-enter."));
				addErrors(request, messages);
				courseForm.setCourse("");
				return "addCourseSite";
			}		
			
			// verify that course code does not exist
			boolean courseExist = studentSystemDAO.courseExist(courseForm.getCourse(),courseForm.getYear(),
					courseForm.getAcadPeriod(),courseForm.getSelectedView());
			if (courseExist == true) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Course site already exist, cannot create duplicate site."));
				addErrors(request, messages);
				return "addCourseSite";			
			}
			
			//extra permission to Tracy and other people
			String superAccess = maintainStaffSakaiDAO.getUserPerms(userId,userEID);
			
			// Ristrict the linker to link people only to their colleges
			  try {
				  if (!securityService.isSuperUser(userId)  ) {
			    	  if(!superAccess.equalsIgnoreCase("MAINTAIN")){
		         	// Ristrict the linker to link people only to their colleges
					collegeCodeMatch = studentSystemDAO.checkCollegeCodeMatch(courseForm.getCourse().toUpperCase(), userEID.toUpperCase());
					log.info("MaintainStaff MainAction: addCourseVerify.checkCollegeCodeMatch ");

			       if (collegeCodeMatch == false) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "  You are not allowed to link staff to this course as it is not part of your department's current academic responsibilities."));
					addErrors(request, messages);
					courseForm.setCourse("");
					return "addCourseSite";
				   }
			      }
				  }
			     }catch (Exception e) {
			    	 log.error("MaintainStaff CourseAction addCourseVerify: Failed to check the admin permissions or checkCollegeCodeMatch "+userEID.toUpperCase());
			    	 e.printStackTrace();
			   }
			
			// verify that the course/year/semester combination is valid
			if (courseForm.getSelectedView().equals("L")){
				// validate acad period + course combination
				boolean courseCombinationValid = studentSystemDAO.courseValid(courseForm.getCourse(), courseForm.getYear(), courseForm.getAcadPeriod(),courseForm.getSelectedView());
				if (courseCombinationValid == false) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Academic Period is not valid.  Please change to correct academic offering type."));
					addErrors(request, messages);
					return "addCourseSite";	
				}
				
				// validate that course is still active
				boolean courseActive = studentSystemDAO.courseExpired(courseForm.getCourse(), courseForm.getYear(), courseForm.getAcadPeriod());
				if (courseActive == false) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Course and academic offering type is not active"));
					addErrors(request, messages);
					return "addCourseSite";	
				}
				
			} else {
				// validate exam periodek voe
			}
			
			if (courseForm.getSelectedView().equals("L")) {
				/** Primary lecturer only for teaching roles */
				
				// verify that primary lecturer info was completed
				if ((null == courseForm.getPrimlNetworkCode())||(courseForm.getPrimlNetworkCode().length()==0)) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Primary Lecturer information is not complete."));
					addErrors(request, messages);
					return "addCourseSite";
				}
							
				// validate correct network code
				courseForm.setPrimlNetworkCode(courseForm.getPrimlNetworkCode().toUpperCase());
				String msg = studentSystemDAO.staffValidation(courseForm.getPrimlNetworkCode());
				if (msg.length() >=1) {
					if (msg.equals("Error reading staff record.")) {
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage", "Staff information is not valid ("+courseForm.getPrimlNetworkCode()+").  " +
										"Verify network code was correctly entered and/or there is an active Unisa contract. " +
										"(active means person has a contract with current dates on the system,is not retired or resigned)" ));
						addErrors(request, messages);
						return "addCourseSite";
					} else {
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage", msg));
						addErrors(request, messages);
						return "addCourseSite";
					}
				}
							
				studentSystemDAO.selectPersonName(courseForm);
				// is person active, if not he may not be linked to a course site.
				if (courseForm.getNewPrimlStatus().equals("Inactive")) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", courseForm.getPrimlNetworkCode()+" may not be used as he/she is not an active staff member."));
					addErrors(request, messages);
					return "addCourseSite";
				}
					
			}
			
			ArrayList courseList = courseForm.getCourseList();
			// for each person added do the following validations
			for (int i=0; i < courseForm.getNoOfRecords(); i++) {
				RecordForm record = (RecordForm) courseList.get(i);
				record.setNetworkCode(ltrim(record.getNetworkCode()));
				record.setNetworkCode(rtrim(record.getNetworkCode()));
				record.setNetworkCode(record.getNetworkCode().replace('\'',' '));
				
				if ((null == record.getNetworkCode())&&(record.getNetworkCode().length() == 0)) {
					// ignore record - no data entered.
					
				} else {
									
					// validate correct network code
					record.setNetworkCode(record.getNetworkCode().toUpperCase());
					String msg = studentSystemDAO.staffValidation(record.getNetworkCode());
					if (msg.length() >=1) {
						if (msg.equals("Error reading staff record.")) {
							
							// CHECK IF CONTRACTOR
							String tmpName = studentSystemDAO.personExist(record.getNetworkCode());
							if (tmpName.equals("0")) {
								messages.add(ActionMessages.GLOBAL_MESSAGE,
										new ActionMessage("message.generalmessage", "Staff information is not valid ("+record.getNetworkCode()+").  " +
												"Verify network code was correctly entered and/or there is an active Unisa contract. " +
												"(active means person has a contract with current dates on the system,is not retired or resigned)" ));
								addErrors(request, messages);
								return "addCourseSite";			
							} else {
								record.setName(tmpName);
								record.setStatus("Active");
							}
							
						} else {
							messages.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("message.generalmessage", msg));
							addErrors(request, messages);
							return "addCourseSite";
						}
					}
								
					studentSystemDAO.selectPersonName(record);
					// is person active, if not he may not be linked to a course site.
					if (record.getStatus().equals("Inactive")) {
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage", record.getNetworkCode()+" may not be used as he/she is not an active staff member."));
						addErrors(request, messages);
						return "addCourseSite";
					}
					if (courseForm.getSelectedView().equals("E")&&(record.getStaffNo().equals("0"))) {
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage", record.getNetworkCode()+" may not be linked as he/she does not have a valid staff number."));
						addErrors(request, messages);
						return "addCourseSite";					
					}
					
					
					// verify that role was selected
					if ((null == record.getRole())||(record.getRole().equals(""))) {
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage", "Role must be selected for ("+record.getNetworkCode()+")."));
						addErrors(request, messages);
						return "addCourseSite";		
					} else {
						if (courseForm.getSelectedView().equals("L")) {
							record.setRoleDesc(studentSystemDAO.selectRoleDescription(record.getRole(), 24));
						} else {
							record.setRoleDesc(studentSystemDAO.selectRoleDescription(record.getRole(), 80));
						}
					} 
					
					// verify that person was not linked to 2 roles
					int tmpDuplicate = 0;
					for (int j=0; j< courseList.size(); j++) {
						RecordForm record1 = (RecordForm) courseList.get(j);
						if (record.getNetworkCode().equals(record1.getNetworkCode())) {
							tmpDuplicate++;
						}
					}
					if (courseForm.getSelectedView().equals("L")) { 
						if ((tmpDuplicate >= 2)||(record.getNetworkCode().equals(courseForm.getPrimlNetworkCode()))) {
							messages.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("message.generalmessage", "Person may only be linked to one role."));
							addErrors(request, messages);
							return "addCourseSite";						
						}
					} else {
						if (tmpDuplicate >= 2) {
							messages.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("message.generalmessage", "Person may only be linked to one role."));
							addErrors(request, messages);
							return "addCourseSite";						
						}
					}
					
					// check in database for existing course site.
					boolean isDuplicate = studentSystemDAO.isDuplicatePerson(courseForm.getCourse(), courseForm.getYear(), 
							courseForm.getAcadPeriod(), record.getNetworkCode(), courseForm.getSelectedView(),"");
					if (isDuplicate == true) {
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage", "Person may only be linked to one role."));
						addErrors(request, messages);
						return "addPersons";	
					}
					
				} // end else if
			}
			courseForm.setAcadPeriodDesc(courseForm.getSelectedView());
			request.setAttribute("acadPeriodDesc", courseForm.getAcadPeriodDesc());
			
			if (courseForm.getSelectedView().equals("L")) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Are you sure you want to add the following persons to create this course site?"));
			} else {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Are you sure you want to add the following persons to create this examination period?"));
			}
			addErrors(request, messages);
			
			return "addCourseSiteConfirm";		
	}
	/** Actual save of new course site detail */
	public String addCourseSave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		CourseForm courseForm = (CourseForm) form;
		ActionMessages messages = new ActionMessages();
		MaintainStaffStudentDAO db1 = new MaintainStaffStudentDAO();
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
		UsageSession usageSession = usageSessionService.getSession();

		//logged in user
		sessionManager = (SessionManager)ComponentManager.get(SessionManager.class);
		Session currentSession = sessionManager.getCurrentSession();
		String userID = currentSession.getUserEid();

		String tmpAtStep = request.getParameter("atstep").toString();

		if ((courseForm.getSelectedView()==null)||(courseForm.getSelectedView().equals(""))) {
			courseForm.setSelectedView("L");
		}	

		// save new course site
		String errorOccurred ="";
		if (courseForm.getSelectedView().equals("L")) { 
			// Insert primary lecturer
			errorOccurred = db1.insertRole(courseForm.getPrimlNetworkCode(), courseForm.getPrimlStaffNo(), courseForm.getCourse(),
					courseForm.getYear(), courseForm.getAcadPeriod(), "PRIML", courseForm.getSelectedView(),"");

			if (errorOccurred.length() > 0) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", NULLVALUEERROR));
				addErrors(request, messages);

				// Get period dropdown list
				if (courseForm.getSelectedView().equals("L")) {
					// academic periodsVerify detail that must be added for a new course
					courseForm.setAcadPeriodOptions(db1.selectRegPeriodOptions());
					request.setAttribute("acadPeriodOptions", courseForm.getAcadPeriodOptions());
				} else {
					// examination periods
					courseForm.setAcadPeriodOptions(db1.selectExamPeriodOptions());
					request.setAttribute("acadPeriodOptions", courseForm.getAcadPeriodOptions());
				}
				request.setAttribute("noOfRecordsOptions", courseForm.getNoOfRecordsOptions());
				request.setAttribute("roleOptions",courseForm.getRoleOptions());
				request.setAttribute("yearOptions", courseForm.getYearOptions());

				return "addCourseSite";
			}

			//insert Audit log
			String logTrail="Role/Function added : user= "+courseForm.getPrimlNetworkCode()+ " role= "+"PRIML "+"(changed by  " + userID +")";
			db1.insertAuditLog(userID,courseForm.getCourse(),courseForm.getSelectedView(), courseForm.getYear(),courseForm.getAcadPeriod(),logTrail);

			eventTrackingService.post(
					eventTrackingService.newEvent(EventTrackingTypes.EVENT_MAINTAINSTAFF_ROLE_ADD, "Role added. Course: "+courseForm.getCourse()+"-"+courseForm.getAcadPeriodDesc()+" "+
							" Person: "+courseForm.getPrimlNetworkCode()+" Role: PRIML", false), usageSession);
		} else {
			//add paper number for examination function
			errorOccurred= db1.insertRole(courseForm.getPrimlNetworkCode(), courseForm.getPrimlStaffNo(), courseForm.getCourse(),
					courseForm.getYear(), courseForm.getAcadPeriod(), "PRIML", courseForm.getSelectedView(),courseForm.getPaperNo());
			if (errorOccurred.length() > 0) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", NULLVALUEERROR));
				addErrors(request, messages);

				// Get period dropdown list
				if (courseForm.getSelectedView().equals("L")) {
					// academic periodsVerify detail that must be added for a new course
					courseForm.setAcadPeriodOptions(db1.selectRegPeriodOptions());
					request.setAttribute("acadPeriodOptions", courseForm.getAcadPeriodOptions());
				} else {
					// examination periods
					courseForm.setAcadPeriodOptions(db1.selectExamPeriodOptions());
					request.setAttribute("acadPeriodOptions", courseForm.getAcadPeriodOptions());
				}
				request.setAttribute("noOfRecordsOptions", courseForm.getNoOfRecordsOptions());
				request.setAttribute("roleOptions",courseForm.getRoleOptions());
				request.setAttribute("yearOptions", courseForm.getYearOptions());
				return "addCourseSite";
			}
			eventTrackingService.post(
					eventTrackingService.newEvent(EventTrackingTypes.EVENT_MAINTAINSTAFF_ROLE_ADD, "Role added. Course: "+courseForm.getCourse()+"-"+courseForm.getAcadPeriodDesc()+" "+
							" Person: "+courseForm.getPrimlNetworkCode()+" Role: PRIML", false), usageSession);
		}

		// Insert other roles
		ArrayList courseList = courseForm.getCourseList();
		// for each person added do the following validations
		for (int i=0; i < courseList.size(); i++) {
			if (courseForm.getSelectedView().equals("L")) {
				RecordForm record = (RecordForm) courseList.get(i);
				errorOccurred = db1.insertRole(record.getNetworkCode(), record.getStaffNo(), courseForm.getCourse(),
						courseForm.getYear(), courseForm.getAcadPeriod(), record.getRole(), courseForm.getSelectedView(),"");
				if (errorOccurred.length() > 0) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", NULLVALUEERROR));
					addErrors(request, messages);
					return "addCourseSite";
				}
				//insert Audit log
				//Role/Function added: user=+network code+ role=+added role name
				String logTrail="Role/Function added: user= "+record.getNetworkCode()+" "+"role= "+record.getRole()+"(changed by  " + userID +")";
				db1.insertAuditLog(userID,courseForm.getCourse(),courseForm.getSelectedView(),courseForm.getYear(),courseForm.getAcadPeriod(),logTrail);

				eventTrackingService.post(
						eventTrackingService.newEvent(EventTrackingTypes.EVENT_MAINTAINSTAFF_ROLE_ADD, "Role added. Course: "+courseForm.getCourse()+"-"+courseForm.getAcadPeriodDesc()+" "+
								" Person: "+record.getNetworkCode()+" Role: "+record.getRole(), false), usageSession);
			}
			// for examination function with added paper number
			if (courseForm.getSelectedView().equals("E")) {
				RecordForm record = (RecordForm) courseList.get(i);
				errorOccurred = db1.insertRole(record.getNetworkCode(), record.getStaffNo(), courseForm.getCourse(),
						courseForm.getYear(), courseForm.getAcadPeriod(), record.getRole(), courseForm.getSelectedView(),courseForm.getPaperNo());
				if (errorOccurred.length() > 0) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", NULLVALUEERROR));
					addErrors(request, messages);
					return "addCourseSite";
				}

				eventTrackingService.post(
						eventTrackingService.newEvent(EventTrackingTypes.EVENT_MAINTAINSTAFF_ROLE_ADD, "Role added. Course: "+courseForm.getCourse()+"-"+courseForm.getAcadPeriodDesc()+" "+
								" Person: "+record.getNetworkCode()+" Role: "+record.getRole(), false), usageSession);
			}
		} 
		request.setAttribute("viewOptionList",courseForm.getViewOptionList());
		request.setAttribute("roleOptions",courseForm.getRoleOptions());
        request.setAttribute("yearOptions", courseForm.getYearOptions());
		
		if(courseForm.getSelectedView().equalsIgnoreCase("L")){
		request.setAttribute("acadPeriodOptions", courseForm.getAcadPeriodOptions());
		} else {
			request.setAttribute("acadPeriodOptions", courseForm.getAcadPeriodOptions());
		}

		// select data for that course
		db1.selectCourseList(courseForm);
		courseForm.setPrevAcadPeriod("x");

		if (courseForm.getExpandedPeriods() == null) {
			courseForm.setExpandedPeriods(new ArrayList());
		}

		request.setAttribute("prevAcadPeriod", courseForm.getPrevAcadPeriod());
		// reset all the values
		courseForm.setNoOfRecords(0);
		courseForm.setPrimlNetworkCode("");
		courseForm.setPrimlStaffNo("");
		ArrayList tmp = new ArrayList();
/*		courseForm.setCourseList(tmp);
		courseForm.setYear("");
		courseForm.setAcadPeriod("");*/
		

		return "courseDisplay2";
	}

	/** Actual save of new persons added to an existing course */
	public String addPersonsSave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		CourseForm courseForm = (CourseForm) form;
		ActionMessages messages = new ActionMessages();
		MaintainStaffStudentDAO db1 = new MaintainStaffStudentDAO();
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
		UsageSession usageSession = usageSessionService.getSession();

		//logged in user
		sessionManager = (SessionManager)ComponentManager.get(SessionManager.class);
		Session currentSession = sessionManager.getCurrentSession();
		String userID = currentSession.getUserEid();

		String tmpAtStep = request.getParameter("atstep").toString();

		// Insert roles
		ArrayList courseList = courseForm.getCourseList();
		// for each person added do the following validations for Teaching Roles
		for (int i=0; i < courseList.size(); i++) {
			if (courseForm.getSelectedView().equals("L") ||courseForm.getSelectedView().equals("J")) {

				RecordForm record = (RecordForm) courseList.get(i);
				if ((null == record.getNetworkCode())||(record.getNetworkCode().length() == 0)) {

				} else {
					String errorOccurred = db1.insertRole(record.getNetworkCode(), record.getStaffNo(), courseForm.getCourse(),
							courseForm.getYear(), courseForm.getAcadPeriod(), record.getRole(), courseForm.getSelectedView(),"");

					if (errorOccurred.length() > 0) {
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage", NULLVALUEERROR));
						addErrors(request, messages);

						// Get period dropdown list
						if (courseForm.getSelectedView().equals("L") || courseForm.getSelectedView().equals("J")) {
							// academic periods
							courseForm.setAcadPeriodOptions(db1.selectRegPeriodOptions());
							request.setAttribute("acadPeriodOptions", courseForm.getAcadPeriodOptions());
						} else {
							// examination periods
							courseForm.setAcadPeriodOptions(db1.selectExamPeriodOptions());
							request.setAttribute("acadPeriodOptions", courseForm.getAcadPeriodOptions());
						}
						request.setAttribute("yearOptions", courseForm.getYearOptions());
						request.setAttribute("noOfRecordsOptions", courseForm.getNoOfRecordsOptions());
						request.setAttribute("roleOptions",courseForm.getRoleOptions()); // Role options
						request.setAttribute("periodOptions", courseForm.getPeriodOptions());

						return "addPersons";
					}
					//insert Audit log
					String logTrail="Role/Function added: user= "+record.getNetworkCode()+ " new role= "+record.getRole()+"(changed by  " + userID +")";
					db1.insertAuditLog(userID,courseForm.getCourse(),courseForm.getSelectedView(),courseForm.getYear(),courseForm.getAcadPeriod(),logTrail);
					if (courseForm.getSelectedView().equals("L")){
					eventTrackingService.post(
							eventTrackingService.newEvent(EventTrackingTypes.EVENT_MAINTAINSTAFF_ROLE_ADD, "Role added. Course: "+courseForm.getCourse()+"-"+courseForm.getAcadPeriodDesc()+" "+
									" Person: "+record.getNetworkCode()+" Role: "+record.getRole(), false), usageSession);
					}else if (courseForm.getSelectedView().equals("J")){
						eventTrackingService.post(
								eventTrackingService.newEvent(EventTrackingTypes.EVENT_MAINTAINSTAFF_ROLE_ADD, "Marking Role added. Course: "+courseForm.getCourse()+"-"+courseForm.getAcadPeriodDesc()+" "+
										" Person: "+record.getNetworkCode()+" Role: "+record.getRole(), false), usageSession);
						
					}
				}
			}
			// for each person added do the following validations for Examination Function
			if (courseForm.getSelectedView().equals("E")) {
				RecordForm record = (RecordForm) courseList.get(i);
				if ((null == record.getNetworkCode())||(record.getNetworkCode().length() == 0)) {

				} else {
					String errorOccurred = db1.insertRole(record.getNetworkCode(), record.getStaffNo(), courseForm.getCourse(),
							courseForm.getYear(), courseForm.getAcadPeriod(), record.getRole(), courseForm.getSelectedView(),courseForm.getPaperNo());
					if (errorOccurred.length() > 0) {
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage", NULLVALUEERROR));
						addErrors(request, messages);

						// Get period dropdown list
						if (courseForm.getSelectedView().equals("L")) {
							// academic periods
							courseForm.setAcadPeriodOptions(db1.selectRegPeriodOptions());
							request.setAttribute("acadPeriodOptions", courseForm.getAcadPeriodOptions());
						} else {
							// examination periods
							courseForm.setAcadPeriodOptions(db1.selectExamPeriodOptions());
							request.setAttribute("acadPeriodOptions", courseForm.getAcadPeriodOptions());
						}
						request.setAttribute("yearOptions", courseForm.getYearOptions());
						request.setAttribute("noOfRecordsOptions", courseForm.getNoOfRecordsOptions());
						request.setAttribute("roleOptions",courseForm.getRoleOptions()); // Role options
						request.setAttribute("periodOptions", courseForm.getPeriodOptions());

						return "addCourseSite";
					}
					//insert Audit log
					String logTrail="Role/Function added: user= "+record.getNetworkCode()+ " new role= "+record.getRole()+ " paper number= "+courseForm.getPaperNo()+"(changed by  " + userID +")";
					db1.insertAuditLog(userID,courseForm.getCourse(),courseForm.getSelectedView(),courseForm.getYear(),courseForm.getAcadPeriod(),logTrail);	

					eventTrackingService.post(
							eventTrackingService.newEvent(EventTrackingTypes.EVENT_MAINTAINSTAFF_ROLE_ADD, "Role added. Course: "+courseForm.getCourse()+"-"+courseForm.getAcadPeriodDesc()+" "+
									" Person: "+record.getNetworkCode()+" Role: "+record.getRole(), false), usageSession);

				}
			}
		}

		request.setAttribute("viewOptionList",courseForm.getViewOptionList());
		request.setAttribute("roleOptions",courseForm.getRoleOptions());
        request.setAttribute("yearOptions", courseForm.getYearOptions());
		
		if(courseForm.getSelectedView().equalsIgnoreCase("L")){
		request.setAttribute("acadPeriodOptions", courseForm.getAcadPeriodOptions());
		} else {
			request.setAttribute("acadPeriodOptions", courseForm.getAcadPeriodOptions());
		}
		request.setAttribute("paperNo", courseForm.getPaperNo());

		// select data for that course
		//db1.selectCourseList(courseForm);
		courseForm.setPrevAcadPeriod("x");

		if (courseForm.getExpandedPeriods() == null) {
			courseForm.setExpandedPeriods(new ArrayList());
		}

		messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", "New person information for "+courseForm.getCourse()+" "+courseForm.getAcadPeriodDesc()+" successfully saved."));
		addMessages(request, messages);	

		//select data
		//db1.selectCourseList(courseForm);
		request.setAttribute("prevAcadPeriod", courseForm.getPrevAcadPeriod());

		// reset all the values
		courseForm.setNoOfRecords(0);
		courseForm.setPrimlNetworkCode("");
		courseForm.setPrimlStaffNo("");
		//ArrayList tmp = new ArrayList();
		courseForm.setDisplayCourseDetails("displaycourse");
		//courseForm.setCourseList(tmp);
	

		return "courseDisplay2";
	}

	/** Set totalSelected */
	public ActionForward totalSelected(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		CourseForm courseForm = (CourseForm) form;
		MaintainStaffStudentDAO db1 = new MaintainStaffStudentDAO();
		MainForm mainForm = (MainForm) request.getSession().getAttribute("mainForm");

		request.setAttribute("viewOptionList",courseForm.getViewOptionList());
		request.setAttribute("roleOptions",courseForm.getRoleOptions());
        request.setAttribute("yearOptions", courseForm.getYearOptions());
		
		if(courseForm.getSelectedView().equalsIgnoreCase("L")){
		request.setAttribute("acadPeriodOptions", courseForm.getAcadPeriodOptions());
		} else {
			request.setAttribute("acadPeriodOptions", courseForm.getAcadPeriodOptions());
		}

		courseForm.setPrevAcadPeriod("x");

		if (courseForm.getExpandedPeriods() == null) {
			courseForm.setExpandedPeriods(new ArrayList());
		}

		request.setAttribute("prevAcadPeriod", courseForm.getPrevAcadPeriod());

		ArrayList periodHeadings = courseForm.getPeriodHeadings();
		ArrayList courseList = courseForm.getCourseList();

		/*
		for (int j=0; j < periodHeadings.size(); j++) {
			HeadingForm headingForm = (HeadingForm) periodHeadings.get(j);

			if (headingForm.isRemove()) {
				for (int i=0; i < courseList.size(); i++) {
					RecordForm record = (RecordForm) courseList.get(i);
					if (headingForm.getPeriodDesc().equals(record.getAcadPeriodDesc())) {
						record.setRemove(true);
					}
				}
			}

			courseForm.setCourseList(courseList);
		}
		 */



		int totalSelected = 0;
		for (int i=0; i < courseList.size(); i++) {

			RecordForm record = (RecordForm) courseList.get(i);

			if (record.isRemove()) {
				totalSelected++;
			}
		}

		courseForm.setTotalSelected(totalSelected);

		return mapping.findForward("courseDisplay");

	}

	/** Expand period list */
	public ActionForward expand(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		CourseForm courseForm = (CourseForm) form;
		MaintainStaffStudentDAO db1 = new MaintainStaffStudentDAO();
		MainForm mainForm = (MainForm) request.getSession().getAttribute("mainForm");

		request.setAttribute("viewOptionList",courseForm.getViewOptionList());
		request.setAttribute("roleOptions",courseForm.getRoleOptions());
        request.setAttribute("yearOptions", courseForm.getYearOptions());
		
		if(courseForm.getSelectedView().equalsIgnoreCase("L")){
		courseForm.setAcadPeriodOptions(db1.selectRegPeriodOptions());
		request.setAttribute("acadPeriodOptions", courseForm.getAcadPeriodOptions());
		} else {
			courseForm.setAcadPeriodOptions(db1.selectExamPeriodOptions());
			request.setAttribute("acadPeriodOptions", courseForm.getAcadPeriodOptions());
		}

		// select data for that course
		//db1.selectCourseList(courseForm);
		courseForm.setPrevAcadPeriod("x");

		if (courseForm.getExpandedPeriods() == null) {
			courseForm.setExpandedPeriods(new ArrayList());
		}

		request.setAttribute("prevAcadPeriod", courseForm.getPrevAcadPeriod());

		String tmp = (String) request.getParameter("periodHeadId");
		ArrayList periodHeadings = courseForm.getPeriodHeadings();
		for (int i=0; i < periodHeadings.size(); i++) {
			HeadingForm headingForm = (HeadingForm) periodHeadings.get(i);

			if (headingForm.getPeriodDesc().equals(tmp)) {
				headingForm.setExpand("YES");
			}
			/*
			LabelValueBean periodHead = (LabelValueBean) periodHeadings.get(i);
			if (periodHead.getLabel().equals(tmp)) {
				periodHead.setValue("YES");
				periodHeadings.set(i, periodHead);
			}
			 */
		}

		courseForm.setPeriodHeadings(periodHeadings);


		return mapping.findForward("courseDisplay");

	}

	/** Collapse period list */
	public ActionForward collapse(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		CourseForm courseForm = (CourseForm) form;
		MaintainStaffStudentDAO db1 = new MaintainStaffStudentDAO();
		MainForm mainForm = (MainForm) request.getSession().getAttribute("mainForm");

		request.setAttribute("viewOptionList",courseForm.getViewOptionList());
		request.setAttribute("roleOptions",courseForm.getRoleOptions());
		request.setAttribute("yearOptions", courseForm.getYearOptions());
			
	    if(courseForm.getSelectedView().equalsIgnoreCase("L")){
		courseForm.setAcadPeriodOptions(db1.selectRegPeriodOptions());
		request.setAttribute("acadPeriodOptions", courseForm.getAcadPeriodOptions());
		} 
	    else {
		courseForm.setAcadPeriodOptions(db1.selectExamPeriodOptions());
		request.setAttribute("acadPeriodOptions", courseForm.getAcadPeriodOptions());
		}

		// select data for that course
		//db1.selectCourseList(courseForm);
		courseForm.setPrevAcadPeriod("x");

		if (courseForm.getExpandedPeriods() == null) {
			courseForm.setExpandedPeriods(new ArrayList());
		}

		request.setAttribute("prevAcadPeriod", courseForm.getPrevAcadPeriod());

		String tmp = (String) request.getParameter("periodHeadId");
		ArrayList periodHeadings = courseForm.getPeriodHeadings();
		for (int i=0; i < periodHeadings.size(); i++) {
			HeadingForm headingForm = (HeadingForm) periodHeadings.get(i);

			if (headingForm.getPeriodDesc().equals(tmp)) {
				headingForm.setExpand("NO");
			}
			/*
			LabelValueBean periodHead = (LabelValueBean) periodHeadings.get(i);
			if (periodHead.getLabel().equals(tmp)) {
				periodHead.setValue("NO");
				periodHeadings.set(i, periodHead);
			}
			 */
		}
		courseForm.setPeriodHeadings(periodHeadings);


		return mapping.findForward("courseDisplay");

	}

	/** Verification of data that was checked for removal and continue to confirmation screen */
	public ActionForward removeRoles(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
    	throws Exception {
		CourseForm courseForm = (CourseForm) form;
		MaintainStaffStudentDAO studentSystemDAO = new MaintainStaffStudentDAO();
		MainForm mainForm = (MainForm) request.getSession().getAttribute("mainForm");
		ActionMessages messages = new ActionMessages();
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
		securityService = (SecurityService) ComponentManager.get(SecurityService.class);
		UsageSession usageSession = usageSessionService.getSession();
		boolean collegeCodeMatch=false; 
		MaintainStaffStudentDAO db1 = new MaintainStaffStudentDAO();
		MaintainStaffSakaiDAO maintainStaffSakaiDAO = new MaintainStaffSakaiDAO();

		//logged in user
		sessionManager = (SessionManager)ComponentManager.get(SessionManager.class);
		Session currentSession = sessionManager.getCurrentSession();
		String userID = currentSession.getUserEid();
		String userId= usageSession.getUserId();

		if ((courseForm.getSelectedView()==null)||(courseForm.getSelectedView().equals(""))) {
			courseForm.setSelectedView("L");
		}	

		request.setAttribute("viewOptionList",courseForm.getViewOptionList());
		request.setAttribute("roleOptions",courseForm.getRoleOptions());
        request.setAttribute("yearOptions", courseForm.getYearOptions());
		
		if(courseForm.getSelectedView().equalsIgnoreCase("L")){
		courseForm.setAcadPeriodOptions(db1.selectRegPeriodOptions());
		request.setAttribute("acadPeriodOptions", courseForm.getAcadPeriodOptions());
		} else {
			courseForm.setAcadPeriodOptions(db1.selectExamPeriodOptions());
			request.setAttribute("acadPeriodOptions", courseForm.getAcadPeriodOptions());
		}

		courseForm.setPrevAcadPeriod("x");

		String tmpAtStep = request.getParameter("atstep").toString();
		if (tmpAtStep.equals("remove")) {
			// remove was confirmed, remove roles

			ArrayList courseList = courseForm.getCourseList();
			for (int i=0; i < courseList.size(); i++) {			
				RecordForm record = (RecordForm) courseList.get(i);
				if (record.isRemove()) {
					// remove data
					studentSystemDAO.deleteRoles(record.getNetworkCode(), record.getCourse(), record.getAcadYear(), 
							record.getSemesterPeriod(), record.getRole(), record.getPersonSequence(),record.getPaperNo(),courseForm);
					//insert Audit log
					String logTrail= "Role/Function delete: user= "+record.getNetworkCode()+" role= "+record.getRole()+"(changed by  " + userID +")";
					studentSystemDAO.insertAuditLog(userID,record.getCourse(),courseForm.getSelectedView(),record.getAcadYear(),record.getSemesterPeriod(),logTrail);

					eventTrackingService.post(
							eventTrackingService.newEvent(EventTrackingTypes.EVENT_MAINTAINSTAFF_ROLE_REMOVE, "Role removed. Course: "+courseForm.getCourse()+"-"+record.getAcadPeriodDesc()+" "+
									" Person: "+record.getNetworkCode()+" Role: "+record.getRole(), false), usageSession);

				}
			}
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Person(s) successfully removed from "+courseForm.getCourse()));
			addMessages(request, messages);		

			studentSystemDAO.selectCourseList(courseForm);
			
			request.setAttribute("courseList", courseForm.getCourseList());


			return mapping.findForward("courseDisplay");
		} else {
			// go to confirm remove screen
			int count = 0;

			String courseSitesRemove = "";

			ArrayList periodHeadings = courseForm.getPeriodHeadings();
			ArrayList courseList = courseForm.getCourseList();
			for (int j=0; j < periodHeadings.size(); j++) {
				HeadingForm headingForm = (HeadingForm) periodHeadings.get(j);

				if (headingForm.isRemove()) {
					courseSitesRemove = courseSitesRemove+courseForm.getCourse()+"-"+headingForm.getPeriodDesc()+" ";

					for (int i=0; i < courseList.size(); i++) {
						RecordForm record = (RecordForm) courseList.get(i);

						if (headingForm.getPeriodDesc().equals(record.getAcadPeriodDesc())) {
							record.setRemove(true);
						}
					}
				}

				courseForm.setCourseList(courseList);
			}


			for (int i=0; i < courseList.size(); i++) {			
				RecordForm record = (RecordForm) courseList.get(i);

				//extra permission to Tracy and other people
				String superAccess = maintainStaffSakaiDAO.getUserPerms(userId,userID);
		
				// Ristrict the linker to link people only to their colleges
				 try {
					 if (!securityService.isSuperUser(userId)  ) {
				    	  if(!superAccess.equalsIgnoreCase("MAINTAIN")){
			     
						collegeCodeMatch = studentSystemDAO.checkCollegeCodeMatch(courseForm.getCourse().toUpperCase(), userID.toUpperCase());
						log.info("MaintainStaff CourseAction: removeRoles checkCollegeCodeMatch ");

				       if (collegeCodeMatch == false) {
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage", "You are not allowed to remove staff to this course as it is not part of your department's current academic responsibilities."));
						addErrors(request, messages);
						return mapping.findForward("courseDisplay");
					   }
				      }
					 }
				     }catch (Exception e) {
				    	 log.error("MaintainStaff CourseAction Failed to check the admin permissions or checkCollegeCodeMatch "+userID.toUpperCase());
				    	 e.printStackTrace();
				   }
				 
				/* 21 July 2011 - SY
				 * add validation for all teaching roles except observer.
				 * if the user has outstanding assignments then the user may not be removed from the course
				 */
				if (courseForm.getSelectedView().equals("L")) {
					//	vijay: Do not check the marking percentage for the courseware admin and tutor roles along with oberver 
					if (!record.getRole().equals(OBSERVER) ||!record.getRole().equals("CADMIN") || !record.getRole().equals("TUTOR")) {
						// Validate outstanding assignments 
						if (record.isRemove()) {
							boolean outAssign = false;
							try {
								outAssign = studentSystemDAO.validatecurrentAssignOut(record.getNetworkCode(),record.getAcadYear(),courseForm.getCourse(),record.getSemesterPeriod());
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							if (outAssign == true) {
								messages.add(ActionMessages.GLOBAL_MESSAGE,
										new ActionMessage("message.generalmessage",record.getNetworkCode()+ " cannot be removed. There are assignment marking percentages allocated to this person."+ 
										"Use the Assignment Marker Reallocation tool to change marking responsibility for this person to zero for every assignment in this academic period."));

								addErrors(request, messages);

								return mapping.findForward("courseDisplay");
							}
						}	

					}
				}

				if (record.isRemove()) {
					count++;
				}
			}

			if (count == 0) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "No persons have been marked for removal.  Please click the check box next " +
						"to the name of the person you want to remove."));
				addErrors(request, messages);
				return mapping.findForward("courseDisplay");
			}

			request.setAttribute("prevAcadPeriod", courseForm.getPrevAcadPeriod());

			if (courseSitesRemove.length() >=1) {
				if (courseForm.getSelectedView().equals("L")) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Please note that the following course sites will be removed: " +
									courseSitesRemove));
				}else if (courseForm.getSelectedView().equals("J")) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Please note that the following marking periods will be removed: " +
									courseSitesRemove));
				}else {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Please note that the following examination periods will be removed: " +
									courseSitesRemove));					
				}
			}

			if (courseSitesRemove.length() >=1) {
				if (courseForm.getSelectedView().equals("L")) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Are you sure you want to remove the following" +
							" persons from this course?"));
				}else if (courseForm.getSelectedView().equals("J")) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Are you sure you want to remove the following" +
							" persons from this marking period?"));
				}else {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Are you sure you want to remove the following" +
							" persons from this examination period?"));
				}
			}
			addErrors(request, messages);

			request.setAttribute("courseList", courseForm.getCourseList());

			return mapping.findForward("courseConfirmRemove");
		}
	}

	/** Verify data marked for update and do actual updates */
	public ActionForward updateRoles(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	      throws Exception {
		CourseForm courseForm = (CourseForm) form;
		//PersonForm personForm = (PersonForm) form;
		MaintainStaffStudentDAO studentSystemDAO = new MaintainStaffStudentDAO();
		MaintainStaffSakaiDAO maintainStaffSakaiDAO = new MaintainStaffSakaiDAO();
		MainForm mainForm = (MainForm) request.getSession().getAttribute("mainForm");
		ActionMessages messages = new ActionMessages();
		ActionMessages emessages = new ActionMessages();
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
		UsageSession usageSession = usageSessionService.getSession();
		//logged in user
		sessionManager = (SessionManager)ComponentManager.get(SessionManager.class);
		Session currentSession = sessionManager.getCurrentSession();
		securityService = (SecurityService) ComponentManager.get(SecurityService.class);
		String userID = currentSession.getUserEid();
		String userId= usageSession.getUserId();
		boolean collegeCodeMatch=false; 

		if ((courseForm.getSelectedView()==null)||(courseForm.getSelectedView().equals(""))) {
			courseForm.setSelectedView("L");
		}	
		
		request.setAttribute("viewOptionList",courseForm.getViewOptionList());
		request.setAttribute("roleOptions",courseForm.getRoleOptions());
        request.setAttribute("yearOptions", courseForm.getYearOptions());
		
		if(courseForm.getSelectedView().equalsIgnoreCase("L")){
		request.setAttribute("acadPeriodOptions", courseForm.getAcadPeriodOptions());
		} else {
			request.setAttribute("acadPeriodOptions", courseForm.getAcadPeriodOptions());
		}
		

		courseForm.setPrevAcadPeriod("x");

		ArrayList courseList = courseForm.getCourseList();
		int tmpCount = 0;
		String notUpdateMsg = "";
		for (int i=0; i < courseList.size(); i++) {			
			RecordForm record = (RecordForm) courseList.get(i);
			// update each record

			if ((null == record.getRole())||(record.getRole().length()==0)) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Role may not be empty for "+record.getNetworkCode()));
				addErrors(request, messages);
				return mapping.findForward("courseDisplay");
			}

			if (!record.getRole().equals(record.getPrevRole())) {
				if (record.getStatus().equals("Inactive")) {
					if (notUpdateMsg.length() > 1) {
						notUpdateMsg = notUpdateMsg+", "+record.getNetworkCode();
					} else {
						notUpdateMsg = record.getNetworkCode();
					}
					record.setRole(record.getPrevRole());
				 } else {
					 
					   //extra permission to Tracy and other people
						String superAccess = maintainStaffSakaiDAO.getUserPerms(userId,userID);
				       // Ristrict the linker to link people only to their colleges
				         try {
				        	 if (!securityService.isSuperUser(userId)  ) {
						    	  if(!superAccess.equalsIgnoreCase("MAINTAIN")){
							collegeCodeMatch = studentSystemDAO.checkCollegeCodeMatch(record.getCourse().toUpperCase(), userID.toUpperCase());
							log.info("MaintainStaff CourseAction: updateRoles checkCollegeCodeMatch ");

					       if (collegeCodeMatch == false) {
							messages.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("message.generalmessage", "You are not allowed to update staff to this course as it is not part of your department's current academic responsibilities."));
							addErrors(request, messages);
							return mapping.findForward("courseDisplay");
						   }
					      }
				        	 }
					     }catch (Exception e) {
								// TODO Auto-generated catch block
					    	 log.error("MaintainStaff MainAction: Failed to check the admin permissions or checkCollegeCodeMatch "+userID.toUpperCase());
					    	 e.printStackTrace();
					   }

				    studentSystemDAO.updateRoles(record.getNetworkCode(),record.getCourse(), record.getAcadYear(), record.getSemesterPeriod(),record.getRole(),record.getPersonSequence(),record.getPaperNo(),courseForm);
					//insert Audit log
					String logTrail="Role/Function update: user= "+record.getNetworkCode()+ " new role= "+record.getRole()+" prev role= "+record.getPrevRole()+"(changed by  " + userID +")";
					studentSystemDAO.insertAuditLog(userID,record.getCourse(),courseForm.getSelectedView(),record.getAcadYear(),record.getSemesterPeriod(),logTrail);

					tmpCount++;

					eventTrackingService.post(
							eventTrackingService.newEvent(EventTrackingTypes.EVENT_MAINTAINSTAFF_ROLE_UPDATE, "Role update. Course: "+courseForm.getCourse()+"-"+record.getAcadPeriodDesc()+" "+
									" Person: "+record.getNetworkCode()+" New role: "+record.getRole()+" prev role: "+record.getPrevRole(), false), usageSession);
					record.setPrevRole(record.getRole());
				}
			}

		}

		if (tmpCount > 0) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Person(s) successfully updated from "+courseForm.getCourse()));
			if (notUpdateMsg.length() > 0) {
				emessages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", notUpdateMsg+" may not be updated. Staff member(s) not active"));
				addErrors(request, emessages);
			}
			addMessages(request, messages);
		} else {
			if (notUpdateMsg.length() > 0) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", notUpdateMsg+" may not be updated. Staff member(s) not active"));
			} else {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "0 Roles updated.  No roles were marked for update."));
			}

			addErrors(request, messages);
		}

		request.setAttribute("courseList", courseForm.getCourseList());

		return mapping.findForward("courseDisplay");

	}

	public ActionForward back(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		CourseForm courseForm = (CourseForm) form;
		MaintainStaffStudentDAO db1 = new MaintainStaffStudentDAO();
		MainForm mainForm = (MainForm) request.getSession().getAttribute("mainForm");
		ActionMessages messages = new ActionMessages();

		String tmpAtStep = request.getParameter("atstep");
		if (tmpAtStep.equals("save")) {

			if ((courseForm.getSelectedView()==null)||(courseForm.getSelectedView().equals(""))) {
				courseForm.setSelectedView("L");
			}	

			// Get period dropdown list
			if (courseForm.getSelectedView().equals("L") || courseForm.getSelectedView().equals("J")) {
				// academic periods
				courseForm.setAcadPeriodOptions(db1.selectRegPeriodOptions());
				request.setAttribute("acadPeriodOptions", courseForm.getAcadPeriodOptions());
			}else {
				// examination periods
				courseForm.setAcadPeriodOptions(db1.selectExamPeriodOptions());
				request.setAttribute("acadPeriodOptions", courseForm.getAcadPeriodOptions());
			}

			request.setAttribute("paperNrOptions", courseForm.getPaperNrOptions());
			request.setAttribute("roleOptions",courseForm.getRoleOptions());
			request.setAttribute("noOfRecordsOptions", courseForm.getNoOfRecordsOptions());
			request.setAttribute("yearOptions", courseForm.getYearOptions());

			return mapping.findForward("addCourseSite");

		} else if (tmpAtStep.equals("viewChangeLogs")) {
			request.setAttribute("paperNrOptions", courseForm.getPaperNrOptions());
			request.setAttribute("viewOptionList",courseForm.getViewOptionList());
			request.setAttribute("roleOptions",courseForm.getRoleOptions());
	        request.setAttribute("yearOptions", courseForm.getYearOptions());
	        courseForm.setYear("");
	        courseForm.setAcadPeriod("");
	        courseForm.setDisplayCourseDetails("");
			
			if(courseForm.getSelectedView().equalsIgnoreCase("L")){
			request.setAttribute("acadPeriodOptions", courseForm.getAcadPeriodOptions());
			} else {
				request.setAttribute("acadPeriodOptions", courseForm.getAcadPeriodOptions());
			}

			courseForm.setPrevAcadPeriod("x");
			request.setAttribute("courseList", courseForm.getCourseList());

			return mapping.findForward("courseDisplay");
		}
		else if (tmpAtStep.equals("addpersonssave")) {

			// Get period dropdown list
			if (courseForm.getSelectedView().equals("L") || courseForm.getSelectedView().equals("J")) {
				// academic periods
				courseForm.setPeriodOptions(db1.selectRegPeriodOptions());
				request.setAttribute("periodOptions", courseForm.getPeriodOptions());
			} else {
				// examination periods
				courseForm.setPeriodOptions(db1.selectExamPeriodOptions());
				request.setAttribute("periodOptions", courseForm.getPeriodOptions());
			}

			request.setAttribute("paperNrOptions", courseForm.getPaperNrOptions());
			request.setAttribute("yearOptions", courseForm.getYearOptions());
			request.setAttribute("noOfRecordsOptions", courseForm.getNoOfRecordsOptions());
			request.setAttribute("roleOptions",courseForm.getRoleOptions()); // Role options

			return mapping.findForward("addPersons");
		} else {

			request.setAttribute("paperNrOptions", courseForm.getPaperNrOptions());
			request.setAttribute("viewOptionList",courseForm.getViewOptionList());
			request.setAttribute("roleOptions",courseForm.getRoleOptions());
	        request.setAttribute("yearOptions", courseForm.getYearOptions());
			
			if(courseForm.getSelectedView().equalsIgnoreCase("L")){
			request.setAttribute("acadPeriodOptions", courseForm.getAcadPeriodOptions());
			} else {
				request.setAttribute("acadPeriodOptions", courseForm.getAcadPeriodOptions());
			}

			courseForm.setPrevAcadPeriod("x");
			request.setAttribute("courseList", courseForm.getCourseList());

			return mapping.findForward("courseDisplay");
		}

	}

	public ActionForward cancel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		CourseForm courseForm = (CourseForm) form;
		MaintainStaffStudentDAO db1 = new MaintainStaffStudentDAO();
		String atStep = request.getParameter("atstep");

		// reset all the values
		courseForm.setNoOfRecords(0);
		courseForm.setPrimlNetworkCode("");
		courseForm.setPrimlStaffNo("");
		ArrayList tmp = new ArrayList();
		courseForm.setCourseList(tmp);
		courseForm.setYear("");
		courseForm.setAcadPeriod("");

		if ((atStep.equals("addpersonssave"))||(atStep.equals("addpersonsconfirm"))
				||(atStep.equals("updPrimLectConfirm"))||(atStep.equals("viewChangeLogs"))) {
			request.setAttribute("viewOptionList",courseForm.getViewOptionList());
			request.setAttribute("roleOptions",courseForm.getRoleOptions());
	        request.setAttribute("yearOptions", courseForm.getYearOptions());
			
	     if (courseForm.getSelectedView().equals("L") || courseForm.getSelectedView().equals("J")) {
	     				// academic periods
	     				courseForm.setAcadPeriodOptions(db1.selectRegPeriodOptions());
	     				request.setAttribute("acadPeriodOptions", courseForm.getAcadPeriodOptions());
	     			}else {
	     				// examination periods
	     				courseForm.setAcadPeriodOptions(db1.selectExamPeriodOptions());
	     				request.setAttribute("acadPeriodOptions", courseForm.getAcadPeriodOptions());
	     			}
			courseForm.setNoOfRecords(0);
			

			courseForm.setSelectedPeriod("-1");
			courseForm.setNewPrimlNetworkCode2("");
			// select data for that course
			//db1.selectCourseList(courseForm);
			courseForm.setPrevAcadPeriod("x");
			courseForm.setDisplayCourseDetails("");

			request.setAttribute("prevAcadPeriod", courseForm.getPrevAcadPeriod());

			return mapping.findForward("courseDisplay");	
		} else {
			MainForm mainForm = (MainForm) request.getSession().getAttribute("mainForm");
			courseForm.setSelectedView("L");
			courseForm.setCourse("");
			courseForm.setPeriodHeadings(null);
			courseForm.setNoOfRecords(0);
			courseForm.setDisplayCourseDetails("");


			mainForm.resetFormbean(mapping, request);
			return mapping.findForward("mainDisplay");
		}

	}

	/** Go to add person to existing course screen */
	public ActionForward addPersons(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		CourseForm courseForm = (CourseForm) form;
		MaintainStaffStudentDAO studentSystemDAO = new MaintainStaffStudentDAO();
		MaintainStaffSakaiDAO maintainStaffSakaiDAO = new MaintainStaffSakaiDAO();
		ActionMessages messages = new ActionMessages();
		request.setAttribute("viewOptionList",courseForm.getViewOptionList());
		request.setAttribute("roleOptions",courseForm.getRoleOptions());
		request.setAttribute("yearOptions", courseForm.getYearOptions());
        if(courseForm.getSelectedView().equalsIgnoreCase("L")){
		request.setAttribute("acadPeriodOptions", courseForm.getAcadPeriodOptions());
		} else {
			request.setAttribute("acadPeriodOptions", courseForm.getAcadPeriodOptions());
		}
		/*courseForm.setAcadPeriod("");
		courseForm.setYear("");*/
		
		securityService = (SecurityService) ComponentManager.get(SecurityService.class);
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
		UsageSession usageSession = usageSessionService.getSession();
		String userEID = usageSession.getUserEid();
		String userId= usageSession.getUserId();
		boolean collegeCodeMatch=false; 
		
		//extra permission to Tracy and other people
		String superAccess = maintainStaffSakaiDAO.getUserPerms(userId,userEID);
		
		// Ristrict the linker to link people only to their colleges
		  try {
			  if (!securityService.isSuperUser(userId) ) {
		    	  if(!superAccess.equalsIgnoreCase("MAINTAIN")){
	         	// Ristrict the linker to link people only to their colleges
				collegeCodeMatch = studentSystemDAO.checkCollegeCodeMatch(courseForm.getCourse().toUpperCase(), userEID.toUpperCase());
				log.info("MaintainStaff MainAction: addCourseVerify.checkCollegeCodeMatch ");

		       if (collegeCodeMatch == false) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "  You are not allowed to link staff to this course as it is not part of your department's current academic responsibilities."));
				addErrors(request, messages);
				return mapping.findForward("courseDisplay");
			   }
		      }
			  }
		     }catch (Exception e) {
		    	 log.error("MaintainStaff CourseAction addCourseVerify: Failed to check the admin permissions or checkCollegeCodeMatch "+userEID.toUpperCase());
		    	 e.printStackTrace();
		   }
		// get Paper number options
		if (courseForm.getSelectedView().equals("E")) {
			courseForm.setPaperNrOptions(courseForm.getPaperNrOptions());
			request.setAttribute("paperNrOptions", courseForm.getPaperNrOptions());
		}

		if ((courseForm.getSelectedView()==null)||(courseForm.getSelectedView().equals(""))) {
			courseForm.setSelectedView("L");
		}	

		if (courseForm.getNoOfRecords() == 0) {
			ArrayList tmpArray = new ArrayList();
			courseForm.setCourseList(tmpArray);
		}

		// set number of persons that's going to be added
		ArrayList courseList = new ArrayList();
		if ((null == courseForm.getCourseList())||(courseForm.getCourseList().size()==0)) {
			for (int i=0; i < courseForm.getNoOfRecords(); i++) {
				RecordForm record = new RecordForm();
				record.setAcadPeriodDesc(courseForm.getAcadPeriod());
				record.setAcadYear(courseForm.getYear());
				record.setCourse(courseForm.getCourse());
				record.setHeading("Add Staff Member ("+(i+1)+" of "+courseForm.getNoOfRecords()+")");

				courseList.add(record);
			}
		} else {
			if (courseForm.getCourseList().size() != courseForm.getNoOfRecords()) {
				for (int i=0; i < courseForm.getNoOfRecords(); i++) {
					RecordForm record = new RecordForm();
					record.setAcadPeriodDesc(courseForm.getAcadPeriod());
					record.setAcadYear(courseForm.getYear());
					record.setCourse(courseForm.getCourse());
					record.setHeading("Add Staff Member ("+(i+1)+" of "+courseForm.getNoOfRecords()+")");

					courseList.add(record);
				}
				for (int i=0; i < courseForm.getCourseList().size(); i++) {
					RecordForm record = (RecordForm) courseForm.getCourseList().get(i);
					if (i < courseForm.getNoOfRecords()) {
						RecordForm record2 = (RecordForm) courseList.get(i);
						record2.setStaffNo(record.getStaffNo());
						record2.setNetworkCode(record.getNetworkCode());

						courseList.set(i, record2);
					}
				}
			}
		}

		courseForm.setCourseList(courseList);

		// Get period dropdown list
		if (courseForm.getSelectedView().equals("L") || courseForm.getSelectedView().equals("J")) {
			// academic periods
			courseForm.setPeriodOptions(studentSystemDAO.selectRegPeriodOptions());
			request.setAttribute("periodOptions", courseForm.getPeriodOptions());
		} else if (courseForm.getSelectedView().equals("E")){
			// examination periods
			courseForm.setPeriodOptions(studentSystemDAO.selectExamPeriodOptions());
			request.setAttribute("periodOptions", courseForm.getPeriodOptions());
		}

		request.setAttribute("yearOptions", courseForm.getYearOptions());
		request.setAttribute("noOfRecordsOptions", courseForm.getNoOfRecordsOptions());
		request.setAttribute("roleOptions",courseForm.getRoleOptions()); // Role options

		return mapping.findForward("addPersons");
	}

	/** Verify details of persons that need to be added to a course and go to confirmation screen */
	public String addPersonsVerify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		CourseForm courseForm = (CourseForm) form;
		MaintainStaffStudentDAO studentSystemDAO = new MaintainStaffStudentDAO();
		ActionMessages messages = new ActionMessages();
		boolean collegeCodeMatch=false; 
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
		UsageSession usageSession = usageSessionService.getSession();
		securityService = (SecurityService) ComponentManager.get(SecurityService.class);
		String userEID = usageSession.getUserEid();
		String userId= usageSession.getUserId();

		request.setAttribute("yearOptions", courseForm.getYearOptions());
		request.setAttribute("noOfRecordsOptions", courseForm.getNoOfRecordsOptions());
		request.setAttribute("roleOptions",courseForm.getRoleOptions()); // Role options
		request.setAttribute("periodOptions", courseForm.getPeriodOptions());

		// get Paper number options
		if (courseForm.getSelectedView().equals("E")) {
			courseForm.setPaperNrOptions(courseForm.getPaperNrOptions());
			request.setAttribute("paperNrOptions", courseForm.getPaperNrOptions());
		}


		courseForm.setCourse(ltrim(courseForm.getCourse()));
		courseForm.setCourse(rtrim(courseForm.getCourse()));

		// Verify that year was entered
		if ((null == courseForm.getYear())||(courseForm.getYear().length()==0)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please choose the Year"));
			addErrors(request, messages);
			return "addPersons";
		}
		//Check if a record is unique
		if (courseForm.getSelectedView().equals("E")) {
			//set paper number to 1 when user has chosen "0" on exam function
			if(courseForm.getPaperNo().equals("0")){
				courseForm.setPaperNo("1");
			} else {
				courseForm.setPaperNo(courseForm.getPaperNo());
			}
		}
		// Verify that year was entered
		if ((null == courseForm.getAcadPeriod())||(courseForm.getAcadPeriod().length()==0)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please choose the Period"));
			addErrors(request, messages);
			return "addPersons";
		}	

		// verify that the course code is valid
		boolean courseValid = studentSystemDAO.courseExist(courseForm.getCourse());
		if (courseValid == false) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Course code is not valid.  Please verify correct code and re-enter."));
			addErrors(request, messages);
			return "addPersons";
		}		

		if (courseForm.getSelectedView().equals("L")){
			// verify that course code does exist (only for teaching and learning roles.
			boolean courseExist = studentSystemDAO.courseExist(courseForm.getCourse(),courseForm.getYear(),
					courseForm.getAcadPeriod(),courseForm.getSelectedView());
			if (courseExist == false) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Course site does not exist, first create the course site."));
				addErrors(request, messages);
				return "addPersons";			
			}
		}

		// verify that the course/year/semester combination is valid
		if (courseForm.getSelectedView().equals("L") || courseForm.getSelectedView().equals("J")){
			// validate acad period + course combination
			boolean courseCombinationValid = studentSystemDAO.courseValid(courseForm.getCourse(), courseForm.getYear(), courseForm.getAcadPeriod(),courseForm.getSelectedView());
			if (courseCombinationValid == false) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Academic Period is not valid.  Please change to correct academic offering type."));
				addErrors(request, messages);
				return "addPersons";	
			}
			if(courseForm.getNoOfRecords()==0){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Please select the number of persons to add to this course."));
				addErrors(request, messages);
				return "addPersons";	
			}

		} else if (courseForm.getSelectedView().equals("E")) {	
			// validate exam period
			boolean isValid = studentSystemDAO.examPeriodExist(courseForm.getCourse(), courseForm.getYear(),courseForm.getAcadPeriod());
			if (isValid == false) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Invalid Examination period, please choose correct examination year and period"));
				addErrors(request, messages);
				return "addPersons";
			}
			if(courseForm.getNoOfRecords()==0){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Please select the number of persons to add to this course."));
				addErrors(request, messages);
				return "addPersons";	
			}

			// verify selected paper number
			//validPaperNr(String examYear, String examPeriod, String course, String paperNr
			boolean validPaperNr = studentSystemDAO.validPaperNr(courseForm.getYear(), courseForm.getAcadPeriod(), courseForm.getCourse(), courseForm.getPaperNo());
			if (validPaperNr == false) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Examination paper ("+courseForm.getPaperNo()+") does not exist for "+courseForm.getCourse()+".  " +
						"Please contact DSAA."));
				addErrors(request, messages);
				return "addPersons";
			}
		}

		ArrayList courseList = courseForm.getCourseList();
		int records = 0;
		// for each person added do the following validations
		for (int i=0; i < courseForm.getNoOfRecords(); i++) {
			
			RecordForm record = (RecordForm) courseList.get(i);

			if ((null == record.getNetworkCode())||(record.getNetworkCode().length() == 0)) {
				// ignore record - no data entered.

			} else {

				record.setNetworkCode(ltrim(record.getNetworkCode()));
				record.setNetworkCode(rtrim(record.getNetworkCode()));
				record.setNetworkCode(record.getNetworkCode().replace('\'',' '));
				// validate correct staff number and network code combination
				String msg = studentSystemDAO.staffValidation(record.getNetworkCode());
				if (msg.length() >=1) {
					if (msg.equals("Error reading staff record.")) {
						// CHECK IF CONTRACTOR
						String tmpName = studentSystemDAO.personExist(record.getNetworkCode());
						if (tmpName.equals("0")) {
							messages.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("message.generalmessage", "Staff information is not valid ("+record.getNetworkCode()+").  " +
											"Verify network code was correctly entered and/or there is an active Unisa contract. " +
									"(active means person has a contract with current dates on the system,is not retired or resigned)" ));
							addErrors(request, messages);
							return "addPersons";			
						} else {
							record.setName(tmpName);
							record.setStatus("Active");
						}

					} else {
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage", msg));
						addErrors(request, messages);
						return "addPersons";
					}
				}

				// of exam function validate that staff number was entered
				String tmpStaffNo = "";
				if (courseForm.getSelectedView().equals("E") || courseForm.getSelectedView().equals("J")) {
					if ((null== record.getStaffNo())||(record.getStaffNo().length()==0)) {
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage", "Staff number must be entered."));
						addErrors(request, messages);
						return "addPersons";						
					} else {
						tmpStaffNo = record.getStaffNo();
					}
				}


				studentSystemDAO.selectPersonName(record);

				if (courseForm.getSelectedView().equals("E") || courseForm.getSelectedView().equals("J")) {
					// for exam functions and Marking functions validate staff number
					if (courseForm.getSelectedView().equals("E")&&(record.getStaffNo().equals("0"))) {
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage", record.getNetworkCode()+" may not be linked as he/she does not have a valid staff number."));
						addErrors(request, messages);
						return "addPersons";				
					} else if (!record.getStaffNo().equals(tmpStaffNo)) {
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage", "Invalid Staff number entered for the network code, correct staff number is "+record.getStaffNo()+"."));
						addErrors(request, messages);
						record.setStaffNo(tmpStaffNo);
						return "addPersons";					
					}
				}

				//Check if a record is unique
				if (courseForm.getSelectedView().equals("E")) {
					boolean isDuplicate = studentSystemDAO.isDuplicatePerson(courseForm.getCourse(), courseForm.getYear(), courseForm.getAcadPeriod(), 
							record.getNetworkCode(), courseForm.getSelectedView(),courseForm.getPaperNo());
					//if record duplicate in Examination functions	
					courseForm.setAcadPeriodDesc(courseForm.getSelectedView());
					String roleDesc = studentSystemDAO.selectRoleDescription(record.getRole(), 80);
					record.setRoleDesc(roleDesc);
					if (isDuplicate == true){
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage", record.getNetworkCode()+" already listed as "+record.getRoleDesc()+" "+"on"+" "+courseForm.getAcadPeriodDesc()+".  " +
								"Correct function or return to previous function."));
						addErrors(request, messages);
						return "addPersons";	
					}
				}
				// is person active, if not he may not be linked to a course site.
				if (record.getStatus().equals("Inactive")) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", record.getNetworkCode()+" may not be used as he/she is not an active staff member."));
					addErrors(request, messages);
					return "addPersons";
				}

				// verify that role was selected
				if ((null == record.getRole())||(record.getRole().equals(""))) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Role must be selected for ("+record.getNetworkCode()+")."));
					addErrors(request, messages);
					return "addPersons";		
				} else {
					if (courseForm.getSelectedView().equals("L")) {
						record.setRoleDesc(studentSystemDAO.selectRoleDescription(record.getRole(), 24));
					}else  if (courseForm.getSelectedView().equals("J")) {
						record.setRoleDesc(studentSystemDAO.selectRoleDescription(record.getRole(), 217));
					}else {
						record.setRoleDesc(studentSystemDAO.selectRoleDescription(record.getRole(), 80));
					}
				} 

				// verify that person was not linked to 2 roles
				int tmpDuplicate = 0;
				for (int j=0; j< courseList.size(); j++) {
					RecordForm record1 = (RecordForm) courseList.get(j);
					if (record.getNetworkCode().equals(record1.getNetworkCode())) {
						tmpDuplicate++;
					}
				}
				if (tmpDuplicate >= 2) {
					if (courseForm.getSelectedView().equals("L")) {
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage", "Person may only be linked to one role."));
						addErrors(request, messages);
					}else if(courseForm.getSelectedView().equals("J")){
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage", "Person may only be linked to one function on an Marking period.  Correct function or return to previous function."));
						addErrors(request, messages);
					}else {
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage", "Person may only be linked to one function on an examination period.  Correct function or return to previous function."));
						addErrors(request, messages);
					}
					return "addPersons";						
				}


				// check in database for existing course site.

				boolean isDuplicate = studentSystemDAO.isDuplicatePerson(courseForm.getCourse(), courseForm.getYear(), 
						courseForm.getAcadPeriod(), record.getNetworkCode(), courseForm.getSelectedView(),record.getPaperNo());
				if (isDuplicate == true) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Person may only be linked to one role."));
					addErrors(request, messages);
					return "addPersons";	
				}

				records++;

			} // end else if
			if (records == 0) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Details of at least one staff member must be completed."));
				addErrors(request, messages);
				return "addPersons";		
			}

		}

		courseForm.setAcadPeriodDesc(courseForm.getSelectedView());

		if (courseForm.getSelectedView().equals("L")) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Are you sure you want to add the following persons to this course site?"));
		}else if (courseForm.getSelectedView().equals("J")) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Are you sure you want to add the following persons to this course site for Marking functions?"));
		} else {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Are you sure you want to add the following persons to this examination period?"));
		}
		addErrors(request, messages);

		return "addPersonsVerify2";
	}

	/** View the audit Logs */
	public ActionForward viewLogs(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		CourseForm courseForm = (CourseForm) form;
		MaintainStaffStudentDAO db1 = new MaintainStaffStudentDAO();

		request.setAttribute("yearOptions", courseForm.getYearOptions());
		courseForm.setLogsList(null);

		// Get period dropdown list
		if (courseForm.getSelectedView().equals("L") || courseForm.getSelectedView().equals("J")) {
			// academic periods
			courseForm.setPeriodOptions(db1.selectRegPeriodOptions());
			request.setAttribute("periodOptions", courseForm.getPeriodOptions());
		} else {
			// examination periods
			courseForm.setPeriodOptions(db1.selectExamPeriodOptions());
			request.setAttribute("periodOptions", courseForm.getPeriodOptions());
		}

		return mapping.findForward("viewLogs");

	}

	/** Verify availability of logs and display  */
	public ActionForward showLogs(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		CourseForm courseForm = (CourseForm) form;
		MaintainStaffStudentDAO db1 = new MaintainStaffStudentDAO();
		ActionMessages messages = new ActionMessages();

		request.setAttribute("yearOptions", courseForm.getYearOptions());

		// Get period dropdown list
		if (courseForm.getSelectedView().equals("L") || courseForm.getSelectedView().equals("J")) {
			// academic periods
			courseForm.setPeriodOptions(db1.selectRegPeriodOptions());
			request.setAttribute("periodOptions", courseForm.getPeriodOptions());
		} else {
			// examination periods
			courseForm.setPeriodOptions(db1.selectExamPeriodOptions());
			request.setAttribute("periodOptions", courseForm.getPeriodOptions());
		}

		boolean logsExist= true;
		try {
			logsExist=db1.verifyLogsExist(courseForm.getCourse(), courseForm.getSelectedView(), courseForm.getYear(), courseForm.getAcadPeriod());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//System.out.println("logsExist "+logsExist );
		if (logsExist == false) {
			courseForm.setLogsList(null);
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "No information available for the selected period"));
			addErrors(request, messages);

		}else{
			courseForm.setLogsList(db1.getEventLogs(courseForm.getCourse(), courseForm.getSelectedView(), courseForm.getYear(), courseForm.getAcadPeriod()));

		}
		return mapping.findForward("viewLogs");	
	}

	public ActionForward selectAll(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		CourseForm courseForm = (CourseForm) form;
		MaintainStaffStudentDAO db1 = new MaintainStaffStudentDAO();
		MainForm mainForm = (MainForm) request.getSession().getAttribute("mainForm");
		ActionMessages messages = new ActionMessages();
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
		UsageSession usageSession = usageSessionService.getSession();

		request.setAttribute("viewOptionList",courseForm.getViewOptionList());
		request.setAttribute("roleOptions",courseForm.getRoleOptions());
        request.setAttribute("yearOptions", courseForm.getYearOptions());
		
		if(courseForm.getSelectedView().equalsIgnoreCase("L")){
		request.setAttribute("acadPeriodOptions", courseForm.getAcadPeriodOptions());
		} else {
			request.setAttribute("acadPeriodOptions", courseForm.getAcadPeriodOptions());
		}

		courseForm.setPrevAcadPeriod("x");

		ArrayList courseList = courseForm.getCourseList();
		for (int i=0; i < courseList.size(); i++) {			
			RecordForm record = (RecordForm) courseList.get(i);
			if (!record.getRole().equals("PRIML")) {
				record.setRemove(true);
			}
		}
		return mapping.findForward("courseDisplay");

	}

	public ActionForward unSelectAll(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		CourseForm courseForm = (CourseForm) form;
		MaintainStaffStudentDAO db1 = new MaintainStaffStudentDAO();
		MainForm mainForm = (MainForm) request.getSession().getAttribute("mainForm");
		ActionMessages messages = new ActionMessages();
		UsageSession usageSession = usageSessionService.getSession();

		request.setAttribute("viewOptionList",courseForm.getViewOptionList());
		request.setAttribute("roleOptions",courseForm.getRoleOptions());
        request.setAttribute("yearOptions", courseForm.getYearOptions());
		
		if(courseForm.getSelectedView().equalsIgnoreCase("L")){
		request.setAttribute("acadPeriodOptions", courseForm.getAcadPeriodOptions());
		} else {
			request.setAttribute("acadPeriodOptions", courseForm.getAcadPeriodOptions());
		}

		courseForm.setPrevAcadPeriod("x");

		ArrayList courseList = courseForm.getCourseList();
		for (int i=0; i < courseList.size(); i++) {			
			RecordForm record = (RecordForm) courseList.get(i);
			record.setRemove(false);
		}
		return mapping.findForward("courseDisplay");

	}


	public ActionForward selectAllVisible(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		CourseForm courseForm = (CourseForm) form;
		MaintainStaffStudentDAO db1 = new MaintainStaffStudentDAO();
		MainForm mainForm = (MainForm) request.getSession().getAttribute("mainForm");
		ActionMessages messages = new ActionMessages();
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
		UsageSession usageSession = usageSessionService.getSession();

		request.setAttribute("viewOptionList",courseForm.getViewOptionList());
		request.setAttribute("roleOptions",courseForm.getRoleOptions());
        request.setAttribute("yearOptions", courseForm.getYearOptions());
		
		if(courseForm.getSelectedView().equalsIgnoreCase("L")){
		request.setAttribute("acadPeriodOptions", courseForm.getAcadPeriodOptions());
		} else {
			request.setAttribute("acadPeriodOptions", courseForm.getAcadPeriodOptions());
		}

		courseForm.setPrevAcadPeriod("x");

		ArrayList periodHeadings = courseForm.getPeriodHeadings();
		for (int i=0; i < periodHeadings.size(); i++) {
			HeadingForm headingForm = (HeadingForm) periodHeadings.get(i); 
			if (headingForm.getExpand().equals("YES")) {
				ArrayList courseList = courseForm.getCourseList();
				for (int j=0; j < courseList.size(); j++) {
					RecordForm record = (RecordForm) courseList.get(j);
					if (record.getAcadPeriodDesc().equals(headingForm.getPeriodDesc())) {
						record.setRemove(true);
					}
				}
			}
		}

		return mapping.findForward("courseDisplay");

	}

	public boolean checkIfNumber(String in) {
		try {
			Integer.parseInt(in);
		} catch (NumberFormatException ex) {
			return false;

		}
		return true;
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
