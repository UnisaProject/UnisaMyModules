package za.ac.unisa.lms.tools.maintainstaff.actions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.apache.struts.util.LabelValueBean;

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
import za.ac.unisa.lms.tools.maintainstaff.DAO.HodQueryDao;
import za.ac.unisa.lms.tools.maintainstaff.DAO.MaintainStaffSakaiDAO;
import za.ac.unisa.lms.tools.maintainstaff.DAO.MaintainStaffStudentDAO;
import za.ac.unisa.lms.tools.maintainstaff.forms.MainForm;

public class MainAction extends LookupDispatchAction {

	private EventTrackingService eventTrackingService;
	private UsageSessionService usageSessionService;
	private SessionManager sessionManager;
	private SecurityService securityService;
	
	private Log log = LogFactory.getLog(MainAction.class.getName());
	private long timeInitialMillis = 0;
	private long timeExecuteMillis = 0;
	private long timeEndMillis = 0;
	
	@Override
	protected Map getKeyMethodMap() {
		
	    Map map = new HashMap();
	    map.put("mainDisplay","mainDisplay");
	    map.put("courseDisplay1", "courseDisplay1");
	    map.put("courseDisplay", "courseDisplay");
	    map.put("personDisplay", "personDisplay");
	    map.put("personDisplay1", "personDisplay1");
	    map.put("button.displaycourse","courseDisplay");
	    map.put("button.displayperson","personDisplay");
	    map.put("button.copysite","goCopyCourse");
	    map.put("goCopyCourse", "goCopyCourse");
	    map.put("button.createsite","goAddNewCourse");
	    map.put("button.submit","copyCourseVerify");
	    map.put("button.finish","copyCourseSave");
	    map.put("button.back","goCopyCourse");
	    map.put("button.cancel","cancel");
	    map.put("button.mngdisplay", "displayPerDpt");
	    map.put("displayPerDpt", "displayPerDpt");
	    map.put("hodDisplay","hodDisplay");
		return map;
	}
	
	public ActionForward hodDisplay(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		return mapping.findForward("hodDisplay");
	}
	
	/** Forward to Main view */
	public ActionForward mainDisplay(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
		UsageSession usageSession = usageSessionService.getSession();
		MainForm mainForm = (MainForm) form;
		MaintainStaffSakaiDAO db1 = new MaintainStaffSakaiDAO();
		
		//Session currentSession = SessionManager.getCurrentSession();
		String userEID = usageSession.getUserEid();
		String userId = usageSession.getUserId();
		// get user permission
		try {
			mainForm.setUserPermission(db1.getUserRights(userId,userEID));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info(this+" "+userEID+" MAINTAINSTAFF PERMISSION: "+mainForm.getUserPermission());
		
		eventTrackingService.post(
				eventTrackingService.newEvent(EventTrackingTypes.EVENT_MAINTAINSTAFF_VIEW, "",false), usageSession);
		
		return mapping.findForward("mainDisplay");
	}
	
	/** Return to Main view */
	public ActionForward cancel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MainForm mainForm = (MainForm) form;
		
		ArrayList tmp = new ArrayList();
		mainForm.setCourseList(tmp);
		mainForm.setFromPeriod("");
		mainForm.setToPeriod("");
		mainForm.setFromYear("");
		mainForm.setToYear("");
		mainForm.setSelectedCourses("");
		mainForm.setSelectedView("");
		
		return mapping.findForward("mainDisplay");
	}
	
	/** Forward to Course view */
	public ActionForward courseDisplay(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		

		UsageSession usageSession = usageSessionService.getSession();

		this.timeEndMillis = System.currentTimeMillis();
		log.info("[MaintainStaff MainAction: courseDisplay] [stamp="+timeEndMillis+"] [elapsed="+Long.toString(this.timeEndMillis - this.timeExecuteMillis)+"]"); 
		
		MainForm mainForm = (MainForm) form;
		MaintainStaffStudentDAO studentSystemDAO = new MaintainStaffStudentDAO();
		ActionMessages messages = new ActionMessages();

		// validate that course code was entered
		if ((mainForm.getCourse()== null)||(mainForm.getCourse().equals(""))){			
			messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Please enter a course code."));
			addErrors(request, messages);
			return mapping.findForward("mainDisplay");
		}	
		
	    mainForm.setCourse(ltrim(mainForm.getCourse()));
	    mainForm.setCourse(rtrim(mainForm.getCourse()));
	    mainForm.setCourse(mainForm.getCourse().replace('\'',' '));
		boolean courseValid=false;
		try {
		// validate course code that was entered
		 courseValid = studentSystemDAO.courseExist(mainForm.getCourse());
			log.info("[MaintainStaff MainAction: courseDisplay.courseExist] [stamp="+timeEndMillis+"] [elapsed="+Long.toString(this.timeEndMillis - this.timeExecuteMillis)+"]");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("[MaintainStaff MainAction: courseDisplay.courseExist Exception] [stamp="+timeEndMillis+"] [elapsed="+Long.toString(this.timeEndMillis - this.timeExecuteMillis)+"]");
		}
		
          if(courseValid == false){
        	 messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("message.generalmessage","Course code is not valid.  Please verify correct code and re-enter."));
  		   addErrors(request, messages);
  		   return mapping.findForward("mainDisplay");
        }
    
	     return mapping.findForward("courseDisplay");
	
	}
	
	/** Forward to Course view */
	public ActionForward courseDisplay1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		this.timeEndMillis = System.currentTimeMillis();
		log.info("[MaintainStaff MainAction: courseDisplay1] [stamp="+timeEndMillis+"] [elapsed="+Long.toString(this.timeEndMillis - this.timeExecuteMillis)+"]"); 
		
		MainForm mainForm = (MainForm) form;
		MaintainStaffStudentDAO studentSystemDAO = new MaintainStaffStudentDAO();
		ActionMessages messages = new ActionMessages();
		
		mainForm.setCourse1(ltrim(mainForm.getCourse1()));
	    mainForm.setCourse1(rtrim(mainForm.getCourse1()));   
			
		// validate that course code was entered
		if ((mainForm.getCourse1()== null)||(mainForm.getCourse1().equals(""))){
			
			messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Please enter a course code in Block B."));
			addErrors(request, messages);
			return mapping.findForward("mainDisplay");
		}
			
		// validate course code that was entered
		boolean courseValid = studentSystemDAO.courseExist(mainForm.getCourse1());
		mainForm.setCourse(mainForm.getCourse1());
		
		if (courseValid == false) {
	         messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Course code is not valid.  Please verify correct code and re-enter."));
			log.error("[MaintainStaff MainAction: courseDisplay1.courseExist Exception] [stamp="+timeEndMillis+"] [elapsed="+Long.toString(this.timeEndMillis - this.timeExecuteMillis)+"]");
			addErrors(request, messages);
			return mapping.findForward("mainDisplay");
		}
		  return mapping.findForward("courseDisplay");
	}

	/** Forward to Person view */
	public ActionForward personDisplay(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		this.timeEndMillis = System.currentTimeMillis();
		log.info("[MaintainStaff MainAction: personDisplay] [stamp="+timeEndMillis+"] [elapsed="+Long.toString(this.timeEndMillis - this.timeExecuteMillis)+"]");
		
		MainForm mainForm = (MainForm) form;
		MaintainStaffStudentDAO db1 = new MaintainStaffStudentDAO();
		ActionMessages messages = new ActionMessages();
		
		mainForm.setPerson(ltrim(mainForm.getPerson()));
	    mainForm.setPerson(rtrim(mainForm.getPerson()));   
		
		// validate that person network code was entered
		if ((mainForm.getPerson()== null)||(mainForm.getPerson().equals(""))){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please enter the persons network code"));
			addErrors(request, messages);
			return mapping.findForward("mainDisplay");
		}
		
		// validate network code that was entered
		boolean personValid = db1.personExist(mainForm);
		log.info("[MaintainStaff MainAction: personDisplay.personExist] [stamp="+timeEndMillis+"] [elapsed="+Long.toString(this.timeEndMillis - this.timeExecuteMillis)+"]");
		if (personValid == false) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Person information is not valid.  Verify network code and re-enter"));
			addErrors(request, messages);
			log.error("[MaintainStaff MainAction: personDisplay.personExist Exception] [stamp="+timeEndMillis+"] [elapsed="+Long.toString(this.timeEndMillis - this.timeExecuteMillis)+"]");
			return mapping.findForward("mainDisplay");			
		}

		return mapping.findForward("personDisplay");
	}
	
	/** Forward to person view */
	public ActionForward personDisplay1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		this.timeEndMillis = System.currentTimeMillis();
		log.info("[MaintainStaff MainAction: personDisplay1] [stamp="+timeEndMillis+"] [elapsed="+Long.toString(this.timeEndMillis - this.timeExecuteMillis)+"]");

		MainForm mainForm = (MainForm) form;
		MaintainStaffStudentDAO db1 = new MaintainStaffStudentDAO();
		ActionMessages messages = new ActionMessages();
		
		mainForm.setPerson1(ltrim(mainForm.getPerson1()));
	    mainForm.setPerson1(rtrim(mainForm.getPerson1())); 
		
		// validate that person network code was entered
		if ((mainForm.getPerson1()== null)||(mainForm.getPerson1().equals(""))){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please enter the persons network code in Block A"));
			addErrors(request, messages);
			return mapping.findForward("mainDisplay");
		}
		
		// validate network code that was entered
		mainForm.setPerson(mainForm.getPerson1());
		boolean personValid = db1.personExist(mainForm);
		log.info("[MaintainStaff MainAction: personDisplay1.personExist] [stamp="+timeEndMillis+"] [elapsed="+Long.toString(this.timeEndMillis - this.timeExecuteMillis)+"]");
		if (personValid == false) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Person information is not valid.  Verify network code and re-enter"));
			addErrors(request, messages);
			log.info("[MaintainStaff MainAction: personDisplay1.personExist Exception] [stamp="+timeEndMillis+"] [elapsed="+Long.toString(this.timeEndMillis - this.timeExecuteMillis)+"]");
			return mapping.findForward("mainDisplay");			
		}

		return mapping.findForward("personDisplay");
	}
	
	/** Forward to copy course site view */
	public ActionForward goCopyCourse(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		MainForm mainForm = (MainForm) form;
		MaintainStaffStudentDAO db1 = new MaintainStaffStudentDAO();
		ActionMessages messages = new ActionMessages();
		
		if ((mainForm.getSelectedView()==null)||(mainForm.getSelectedView().equals(""))) {
			mainForm.setSelectedView("L");
		}
		try {
			if (mainForm.getSelectedView().equals("L") || mainForm.getSelectedView().equals("J")) {
				mainForm.setPeriodOptions(db1.selectRegPeriodOptions());
			} else {
				mainForm.setPeriodOptions(db1.selectExamPeriodOptions());
			}
		} catch (Exception e) {
			log.error(this + ": Maintainstaff: Failed to Reg period (" + e + "): " + e.getMessage());
			e.printStackTrace();
		}
		request.setAttribute("periodOptions", mainForm.getPeriodOptions());
		request.setAttribute("yearOptions", mainForm.getYearOptions());
		request.setAttribute("viewOptionList", mainForm.getViewOptionList());
		
		return mapping.findForward("copyCourseSite");
	}
	
	/** Verify information entered to copy a course site */
	public ActionForward copyCourseVerify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		MainForm mainForm = (MainForm) form;
		MaintainStaffStudentDAO studentSystemDAO = new MaintainStaffStudentDAO();
		MaintainStaffSakaiDAO maintainStaffSakaiDAO = new MaintainStaffSakaiDAO();
		ActionMessages messages = new ActionMessages();
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
		UsageSession usageSession = usageSessionService.getSession();
		securityService = (SecurityService) ComponentManager.get(SecurityService.class);
		String userEID = usageSession.getUserEid();
		String userId= usageSession.getUserId();
		boolean collegeCodeMatch=false; 
		
	/*	if (mainForm.getSelectedView().equals("L") || mainForm.getSelectedView().equals("J")) {
			mainForm.setPeriodOptions(db1.selectRegPeriodOptions());
		} else {
			mainForm.setPeriodOptions(db1.selectExamPeriodOptions()); 
		}*/
		request.setAttribute("periodOptions", mainForm.getPeriodOptions());
		request.setAttribute("yearOptions", mainForm.getYearOptions());
		request.setAttribute("viewOptionList", mainForm.getViewOptionList());
		
		// validate that all mandatory fields was completed
		if (mainForm.getSelectedCourses().length() == 0) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "At least one course must be entered."));
			addErrors(request, messages);
			return mapping.findForward("copyCourseSite");
		}
		
		String[] courseToCopy = mainForm.getSelectedCourses().split("\r");
		ArrayList courseListTmp = new ArrayList();
		//periodHeadings.add(new org.apache.struts.util.LabelValueBean(record.getAcadPeriodDesc(), tmpExpand));
		if (courseToCopy.length > 10) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "A Maximum of 10 courses may be entered."));
			addErrors(request, messages);
			return mapping.findForward("copyCourseSite");
		}
		if (mainForm.getFromYear().equals("")||mainForm.getFromPeriod().equals("")) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "The From year and period must be selected."));
			addErrors(request, messages);
			return mapping.findForward("copyCourseSite");
		}
		if (mainForm.getToYear().equals("")||mainForm.getToPeriod().equals("")) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "The To year and period must be selected."));
			addErrors(request, messages);
			return mapping.findForward("copyCourseSite");
		}
		
		String errCodes = "";
		try {
		// for each course code entered check if course code is valid		
		for (int i=0; i <courseToCopy.length; i++) {
			courseToCopy[i].toUpperCase();
			//tmp = tmp.replace('\\n',' ');
			courseToCopy[i] = courseToCopy[i].replaceAll("\\n","");
			courseListTmp.add(new org.apache.struts.util.LabelValueBean(courseToCopy[i], courseToCopy[i]));
			boolean tmpValid = studentSystemDAO.courseExist(courseToCopy[i]);
			if (tmpValid == false) {
				if (errCodes.length()==0) {
					errCodes = courseToCopy[i];
				} else {
					errCodes = errCodes+"," + courseToCopy[i];
				}
			}
		}		
		if (errCodes.length() > 0) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "{"+errCodes+"} is not valid.  Enter correct course code or remove from list."));
			addErrors(request, messages);
			return mapping.findForward("copyCourseSite");	
		}
		
		//if (mainForm.getSelectedView().equals("L")) {
			// Validate each course code and from year + period combination exist
			
			errCodes = "";
			for (int i=0; i< courseToCopy.length; i++) {
				boolean tmpValid = studentSystemDAO.courseExist(courseToCopy[i], mainForm.getFromYear(), mainForm.getFromPeriod(), mainForm.getSelectedView());
				if (tmpValid == false) {
					if (errCodes.length()==0) {
						errCodes = courseToCopy[i];
					} else {
						errCodes = errCodes+"," + courseToCopy[i];
					}
				}			
				
			}
			if (errCodes.length() > 0) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "From academic period information is not valid for "+errCodes+". Please change " +
								"to correct academic offering type of remove code from list."));
				addErrors(request, messages);
				return mapping.findForward("copyCourseSite");	
			}
			
			// validate TO year and period for each course site
			errCodes="";
			String errCodes2="";
			for (int i=0; i< courseToCopy.length; i++) {
				boolean tmpValid = studentSystemDAO.courseValid(courseToCopy[i], mainForm.getToYear(), mainForm.getToPeriod(),mainForm.getSelectedView());
				if (tmpValid == false) {
					if (errCodes.length()==0) {
						errCodes = courseToCopy[i];
					} else {
						errCodes = errCodes+"," + courseToCopy[i];
					}
				}
				
				boolean courseActive = studentSystemDAO.courseExpired(courseToCopy[i], mainForm.getToYear(), mainForm.getToPeriod());
				if (courseActive == false) {
					if (errCodes2.length()==0) {
						errCodes2 = courseToCopy[i];
					} else {
						errCodes2 = errCodes2+"," + courseToCopy[i];
					}
				} 
			}
			if (errCodes.length() > 0) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "To academic period information is not valid for "+errCodes+". " +
								"Please change to the correct academic offering type and remove code from list."));
			}
			if (errCodes2.length() > 0) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "The following course[s] are not active "+errCodes2));
			}
			if ((errCodes.length() >0)||(errCodes2.length()> 0)) {
				addErrors(request, messages);
				return mapping.findForward("copyCourseSite");
			}
		//}
		
		// validate that course site to be created does not already exist
		errCodes = "";
		
		//extra permission to Tracy and other people
		String superAccess = maintainStaffSakaiDAO.getUserPerms(userId,userEID);
		
		 for (int i=0; i< courseToCopy.length; i++) {
			
			 // Ristrict the linker to link people only to their colleges
			 try {
				 if (!securityService.isSuperUser(userId)  ) {
			    	  if(!superAccess.equalsIgnoreCase("MAINTAIN")){
		         	
					collegeCodeMatch = studentSystemDAO.checkCollegeCodeMatch(courseToCopy[i].toUpperCase(), userEID.toUpperCase());
					log.info("MaintainStaff MainAction: courseDisplay1.checkCollegeCodeMatch ");

			       if (collegeCodeMatch == false) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "You are not allowed to copy this course as it is not part of your department's current academic responsibilities."));
					addErrors(request, messages);
					return mapping.findForward("copyCourseSite");
				   }
			      }
				 }
			     }catch (Exception e) {
			    	 log.error("MaintainStaff MainAction:courseDisplay1 Failed to check the admin permissions or checkCollegeCodeMatch "+userEID.toUpperCase());
			    	 e.printStackTrace();
			   }
			
		    
            boolean tmpValid = studentSystemDAO.courseExist(courseToCopy[i], mainForm.getToYear(), mainForm.getToPeriod(), mainForm.getSelectedView());
			if (tmpValid == true) {
				if (errCodes.length()==0) {
					errCodes = courseToCopy[i];
				} else {
					errCodes = errCodes+"," + courseToCopy[i];
				}
			} 
		}
		if (errCodes.length() > 0) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Course site already exist, cannot create duplicate site."));
			addErrors(request, messages);
			return mapping.findForward("copyCourseSite");	
		}
		
		mainForm.setCourseList(courseListTmp);
		mainForm.setFromPeriodDesc(mainForm.getSelectedView());
		mainForm.setToPeriodDesc(mainForm.getSelectedView());
		
		messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", "Are you sure you want to copy the information for the following site(s)?"));
		addMessages(request, messages);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("copyCourseSiteConfirm");
	}
	
	/** Actual copy of course sites */
	public ActionForward copyCourseSave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		MainForm mainForm = (MainForm) form;
		MaintainStaffStudentDAO db1 = new MaintainStaffStudentDAO();
		ActionMessages messages = new ActionMessages();
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
		UsageSession usageSession = usageSessionService.getSession();
		//logged in user
		sessionManager = (SessionManager)ComponentManager.get(SessionManager.class);
		Session currentSession = sessionManager.getCurrentSession();
   		String userID = currentSession.getUserEid();
		
   	/*	if (mainForm.getSelectedView().equals("L") || mainForm.getSelectedView().equals("J")) {
			mainForm.setPeriodOptions(db1.selectRegPeriodOptions());
		} else {
			mainForm.setPeriodOptions(db1.selectExamPeriodOptions()); 
		}*/
		request.setAttribute("periodOptions", mainForm.getPeriodOptions());
		request.setAttribute("yearOptions", mainForm.getYearOptions());
		
		
		String[] tmp = mainForm.getSelectedCourses().split("\r");
		ArrayList courseListTmp = new ArrayList();	
		// copy each role
		try {
			for (int i = 0; i < tmp.length; i++) {
				tmp[i] = tmp[i].replaceAll("\\n", "");
				// System.out.println("tmp "+tmp[i]);
				db1.copyCourseSite(tmp[i], mainForm.getSelectedView(),mainForm.getFromYear(), mainForm.getFromPeriod(),
						mainForm.getToYear(), mainForm.getToPeriod(), userID);
			}
		} catch (Exception e) {
			log.error(this + ": Maintainstaff: Failed to save copy course site (" + e+ "): " + e.getMessage());
		}
		
		if (mainForm.getSelectedView().equals("L")) {			
			eventTrackingService.post(
					eventTrackingService.newEvent(EventTrackingTypes.EVENT_MAINTAINSTAFF_COPY_COURSE_TEACH, "Course site copied: "+mainForm.getSelectedCourses()+
							" From "+mainForm.getFromPeriodDesc()+" to "+mainForm.getToPeriodDesc(), false), usageSession);			
		}else if(mainForm.getSelectedView().equals("J")) {			
			eventTrackingService.post(
					eventTrackingService.newEvent(EventTrackingTypes.EVENT_MAINTAINSTAFF_COPY_COURSE_MARKING, "Course site copied: "+mainForm.getSelectedCourses()+
							" From "+mainForm.getFromPeriodDesc()+" to "+mainForm.getToPeriodDesc(), false), usageSession);			
		}else {
			eventTrackingService.post(
					eventTrackingService.newEvent(EventTrackingTypes.EVENT_MAINTAINSTAFF_COPY_COURSE_EXAM, "Course site copied: "+mainForm.getSelectedCourses()+
							" From "+mainForm.getFromPeriodDesc()+" to "+mainForm.getToPeriodDesc(), false), usageSession);
		}
		
		messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", "Course site(s) successfully copied."));
		addMessages(request, messages);
		return mapping.findForward("mainDisplay");
	}
	/** Forward to Add new course screen */
	public ActionForward goAddNewCourse(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		MainForm mainForm = (MainForm) form;
		mainForm.setCourse("");
		
		return mapping.findForward("addCourseSite");
	}
	
	/** Forward to display information per department screen */
	public ActionForward displayPerDpt(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		MainForm mainForm = (MainForm) form;
		MaintainStaffStudentDAO db1 = new MaintainStaffStudentDAO();
		HodQueryDao db2 = new HodQueryDao();
		
		mainForm.setDepartmentOptions(db2.selectDepartmentList());
		if (null != mainForm.getSelectedDepartment()) {
			// select department information
			db2.selectDepartmentInfo(mainForm);
			// select standin COD's
		
			ArrayList tmp = db2.selectDepartmentTypeList(mainForm.getSelectedDepartment(), false);

			ArrayList tmp2 = new ArrayList();
		
			// select standin COD name
			for (int i=1; i < tmp.size(); i++) {
				LabelValueBean standIn = (LabelValueBean) tmp.get(i);
				String name = db2.selectPersonName(standIn.getValue());
				tmp2.add(new org.apache.struts.util.LabelValueBean(name,standIn.getValue()));
			}
			mainForm.setStandInChairs(tmp2);
		
			Date d = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			mainForm.setDisplayDate(sdf.format(d));
		}
		
		request.setAttribute("departmentOptions", mainForm.getDepartmentOptions());
		
		return mapping.findForward("displayPerDpt");
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