package za.ac.unisa.lms.tools.mdactivity.actions;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.LookupDispatchAction;
import org.apache.struts.util.LabelValueBean;

import za.ac.unisa.lms.constants.EventTrackingTypes;

import org.sakaiproject.event.api.UsageSession;
import org.sakaiproject.event.api.EventTrackingService;
import org.sakaiproject.event.api.UsageSessionService;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.tool.api.ToolManager;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserNotDefinedException;
import org.sakaiproject.user.api.UserDirectoryService;
import org.sakaiproject.component.cover.ComponentManager;

import Srmas01h.Abean.Srmas01sMDStudentActivity;
import za.ac.unisa.lms.tools.mdactivity.dao.MdActivityQueryDAO;
import za.ac.unisa.lms.tools.mdactivity.forms.ActivityRecord;
import za.ac.unisa.lms.tools.mdactivity.forms.MdActivityForm;
import za.ac.unisa.lms.tools.mdactivity.forms.Promotor;
import za.ac.unisa.lms.tools.mdactivity.forms.Student;
import za.ac.unisa.lms.tools.mdactivity.forms.StudyUnit;
import za.ac.unisa.lms.tools.mdactivity.forms.GeneralItem;

public class DisplayMdActivityAction  extends LookupDispatchAction{


	public static Log log = LogFactory.getLog(DisplayMdActivityAction.class);
	private SessionManager sessionManager;
	private UserDirectoryService userDirectoryService;
	private EventTrackingService eventTrackingService;
	private ToolManager toolManager;
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

	protected Map getKeyMethodMap() {
		Map map = new HashMap();
		map.put("button.back", "back");
	    map.put("button.cancel", "cancel");
	    map.put("button.continue", "inputstep2");
//	    map.put("button.update", "updateActivity");
	    map.put("updateActivity", "updateActivity");
	    map.put("button.edit", "editActivity");
//	    map.put("button.save", "save");
	    map.put("save", "save");
		map.put("inputstep2", "inputstep2");
		map.put("button.display", "display");
		map.put("display", "display");
		map.put("addactivity", "addActivity");
		map.put("inputstep1", "inputstep1");
		return map;
	}


	public ActionForward inputstep1(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws UserNotDefinedException{

		MdActivityForm activityForm = (MdActivityForm) form;
		sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
		userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);

		//Get user details, set in default action
		User user = null;

		Session currentSession = sessionManager.getCurrentSession();
		String userID = currentSession.getUserId();

		if (userID != null) {
			user = userDirectoryService.getUser(userID);
			activityForm.setUserCode(userDirectoryService.getUserEid(userID));
		}

		if (!"Instructor".equalsIgnoreCase(user.getType())){
			return mapping.findForward("invaliduser");
		}

		Student student = new Student();
		activityForm.setStudent(student);

		return mapping.findForward("step1forward");
	}

	public ActionForward inputstep2(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception{

		MdActivityForm activityForm = (MdActivityForm) form;
		ActionMessages messages = new ActionMessages();

		try{

		// validate input
			messages = (ActionMessages) activityForm.validate(mapping, request);
			if (!messages.isEmpty()) {
				addErrors(request, messages);
				return mapping.findForward("step1forward");
			}
		if (activityForm.getStudent().getNumber() == null || "".equals(activityForm.getStudent().getNumber())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
						"Please enter a student number."));
			addErrors(request, messages);
			return mapping.findForward("step1forward");
		}

		// populate study unit list
		operListener opl = new operListener();

		Srmas01sMDStudentActivity op = new Srmas01sMDStudentActivity();

		op.addExceptionListener(opl);
	    op.clear();

    	op.setInCsfClientServerCommunicationsClientVersionNumber((short) 3);
	 	op.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
	    op.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
	    op.setInWsStudentNr(Integer.parseInt(activityForm.getStudent().getNumber()));
	    op.setInCsfClientServerCommunicationsAction("L");

		op.execute();

	    String Errormsg = op.getOutCsfStringsString500();
	    if (opl.getException() != null) throw opl.getException();

        if (op.getExitStateType() < 3) throw new Exception(op.getExitStateMsg());

        if ((Errormsg != null) && (!Errormsg.equals(""))) {
        	//if("Dissertation not found.".equalsIgnoreCase(Errormsg)){
        	//	messages.add(ActionMessages.GLOBAL_MESSAGE,
            //			new ActionMessage("error.coolgenerror", "Dissertation/thesis title was not captured. Please contact Senior Qualifications: Telephone 012-4415702 or email swaneep@unisa.ac.za."));
        	//}else{
        		messages.add(ActionMessages.GLOBAL_MESSAGE,
        			new ActionMessage("error.coolgenerror", Errormsg));
        	//}
        	addErrors(request, messages);
        	return mapping.findForward("step1forward");
        } else {
        	// setup study unit list
        	int count = op.getOutStudyUnitGroupCount();
        	ArrayList list = new ArrayList();
        	for (int i=0; i<count; i++) {
				StudyUnit studyUnit = new StudyUnit();
				studyUnit.setStudyUnitCode(op.getOutGWsStudyUnitCode(i));
				studyUnit.setStudyUnitDescription(op.getOutGWsStudyUnitEngShortDescription(i));
				studyUnit.setStatus(op.getOutGStatusCsfStringsString50(i));
				studyUnit.setThesis(op.getOutGDissEntryIefSuppliedFlag(i));
				studyUnit.setQualCode(op.getOutGWsQualificationCode(i));
				studyUnit.setQualDescription(op.getOutGWsQualificationShortDescription(i));
				studyUnit.setEditable(op.getOutGEditableCsfStringsString1(i));
				studyUnit.setErrorMessage(op.getOutGEditableCsfStringsString500(i));
				studyUnit.setLastAcademicYear(Short.toString(op.getOutGWsStudentAnnualRecordMkAcademicYear(i)));
				list.add(studyUnit);
			}
			activityForm.setStudyUnitRecords(list);
			// set student name
			activityForm.getStudent().setName(op.getOutStudentNameCsfStringsString100());
		}

		return mapping.findForward("step2forward");

		}catch (Exception e){
			throw new Exception("MdActivityDisplayAction(inputstep2): / "+e.getMessage());
		}
	}

	public ActionForward display(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		MdActivityForm activityForm = (MdActivityForm) form;
		ActionMessages messages = new ActionMessages();
		Calendar position = Calendar.getInstance();
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
		toolManager = (ToolManager) ComponentManager.get(ToolManager.class);
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);

		try{
			
	   if(activityForm.getStudent().getNumber() == null || "".equals(activityForm.getStudent().getNumber().trim())){
		   // student number cant be empty
		    reset((MdActivityForm)form);
			return mapping.findForward("cancelforward");
	   }

		// validate input
		if (activityForm.getSelectedStudyUnit() == null || "".equals(activityForm.getSelectedStudyUnit())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
						"Select the study unit."));
			addErrors(request, messages);
			return mapping.findForward("step2forward");
		}

		// Get selected study unit from index
		StudyUnit studyUnit = new StudyUnit();
		studyUnit = (StudyUnit) activityForm.getStudyUnitRecords().get(Integer.parseInt(activityForm.getSelectedStudyUnit()));
		activityForm.setStudyUnitCode(studyUnit.getStudyUnitCode());
		
		// -------- study unit tests ---------------
		
		// 1. does a dissertation record exist
		if(!"Y".equalsIgnoreCase(studyUnit.getThesis())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
         			new ActionMessage("error.coolgenerror", "Dissertation/thesis title was not captured. Please contact Senior Qualifications: Telephone 012-4415702 or email swaneep@unisa.ac.za."));
            addErrors(request, messages);
			return mapping.findForward("step2forward");
		}
		
		//2. can the study unit be edited
		if("Y".equalsIgnoreCase(studyUnit.getEditable())){
			activityForm.setReadOnly(false);
			activityForm.setErrorMessage("");
		}else{
			activityForm.setReadOnly(true);
			activityForm.setErrorMessage(studyUnit.getErrorMessage());
		}
		
		// read M and D records
		operListener opl = new operListener();
		Srmas01sMDStudentActivity op = new Srmas01sMDStudentActivity();
		op.addExceptionListener(opl);
	    op.clear();

    	op.setInCsfClientServerCommunicationsClientVersionNumber((short) 3);
	 	op.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
	    op.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
	    op.setInSecurityWsUserNovellUserCode(activityForm.getUserCode());
	    op.setInWsStudentNr(Integer.parseInt(activityForm.getStudent().getNumber()));
	    op.setInStudentDissertationMkStudyUnitCode(activityForm.getStudyUnitCode());
	    op.setInCsfClientServerCommunicationsAction("D");

		op.execute();

	    String Errormsg = op.getOutCsfStringsString500();
	    if (opl.getException() != null) throw opl.getException();

        if (op.getExitStateType() < 3) throw new Exception(op.getExitStateMsg());

        if ((Errormsg != null) && (!Errormsg.equals(""))) {
        	messages.add(ActionMessages.GLOBAL_MESSAGE,
        	new ActionMessage("error.coolgenerror", Errormsg));
        	addErrors(request, messages);
        	return mapping.findForward("step2forward");
        } else {
        	//read activities directly from java
//        	int count = op.getOutGroupCount();
//        	ArrayList list = new ArrayList();
//        	for (int i=1; i<count; i++) {
//				ActivityRecord activity = new ActivityRecord();
//				activity.setActivityCode(Short.toString(op.getOutGrMdActivityActivityCode(i)));
//				SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
//				String formatDate="";
//				if (op.getOutGrMdActivityActivityDate(i)!= null){
//					formatDate = formatter.format(op.getOutGrMdActivityActivityDate(i).getTime());
//					activity.setActivityDate(formatDate);
//					position = op.getOutGrMdActivityActivityDate(i);
//				} else{
//					activity.setActivityDate("");
//				}
//				activity.setActivityDescr(op.getOutGrDescriptionsCsfStringsString40(i));
//				activity.setComments(op.getOutGrMdActivityComments(i));
//				if (op.getOutGrMdActivityFeedbackDate(i)!= null){
//					formatDate = formatter.format(op.getOutGrMdActivityFeedbackDate(i).getTime());
//					activity.setFeedbackDate(formatDate);
//				} else{
//					activity.setFeedbackDate("");
//				}
//				activity.setUserName(op.getOutGrDescriptionsCsfStringsString50(i));
//				activity.setUserCode(op.getOutGrMdActivityStaffNumber(i));
//				activity.setEntryTimestamp(op.getOutGrMdActivityEntryTimestamp(i));
//				list.add(activity);
//			}
//			activityForm.setActivityRecords(list);
			// set qualification
			activityForm.setQualificationCode(op.getOutWsStudentAnnualRecordMkHighestQualCode());
			activityForm.setQualificationDescr(op.getOutWsQualificationShortDescription());
			// set dissertation info
			activityForm.setDisType(op.getOutTypeDescriptionCsfStringsString30());
			activityForm.setDisTitle(op.getOutStudentDissertationTitle());
			// set staff number
			activityForm.setStaffNumber(op.getOutWsStaffPersno());
			// set promoters
			int count = op.getOutPromotorGroupCount();
			ArrayList<Promotor> promList = new ArrayList<Promotor>();
        	for (int i=0; i<count; i++) {
        		Promotor promotor = new Promotor();
        		promotor.setStaffNr(op.getOutGStudentDissertationPromoterMkPromotorNr(i));
        		promotor.setName(op.getOutGCsfStringsString100(i));
        		promotor.setSupervisor(op.getOutGStudentDissertationPromoterSupervisorFlag(i));
        		promotor.setDepartmentDesc(op.getOutGCsfStringsString28(i));
        		promList.add(promotor);
        	}
        	activityForm.setPromotorList(promList);
			//activityForm.setPromoter(op.getOutPromotorNameCsfStringsString50());
			// set supervisor
			//activityForm.setSupervisor(op.getOutStudentDissertationPromoterSupervisorFlag());
			// set student annual record status
			activityForm.setStudentAnnualStatus(op.getOutWsStudentAnnualRecordStatusCode());
			// set registration permission
			activityForm.setRegPermission(op.getOutWsStudentPostGraduateStudiesApproved());
			/* Write event log: Get user details, if not empty write event log with usagesession */
			Session currentSession = sessionManager.getCurrentSession();
			String userID = currentSession.getUserId();
			if (userID != null) {
				UsageSession usageSession = usageSessionService.getSession();
					//UsageSessionService.startSession(currentUserID,request.getRemoteAddr(),request.getHeader("user-agent"));
				eventTrackingService.post(
					eventTrackingService.newEvent(EventTrackingTypes.EVENT_MDACTIVITY_VIEW, toolManager.getCurrentPlacement().getContext(), false),usageSession);
			}
			/*
		       * If page down = Y read proxy again until list is completed
		       */

			//Read activities with a direct sql in java instead of coolgen server - Johanet 20120827
			ArrayList<ActivityRecord> list = new ArrayList<ActivityRecord>();
			MdActivityQueryDAO dao = new MdActivityQueryDAO();
			list = dao.getMDActivities(Integer.parseInt(activityForm.getStudent().getNumber()), activityForm.getStudyUnitCode());
			activityForm.setActivityRecords(list);
			
//			String pageDown = op.getOutCsfClientServerCommunicationsRgvScrollDownFlag();
//			while ("Y".equalsIgnoreCase(pageDown)){
//				// read MORE  M and D records
//				operListener oplList = new operListener();
//				Srmas01sMDStudentActivity opList = new Srmas01sMDStudentActivity();
//				opList.addExceptionListener(oplList);
//				opList.clear();
//
//				opList.setInCsfClientServerCommunicationsClientVersionNumber((short) 3);
//				opList.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
//				opList.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
//				opList.setInSecurityWsUserNovellUserCode(activityForm.getUserCode());
//				opList.setInWsStudentNr(Integer.parseInt(activityForm.getStudent().getNumber()));
//
//				opList.setInStudentDissertationMkStudyUnitCode(activityForm.getStudyUnitCode());
//				opList.setInPositionMdActivityActivityDate(position);
//				opList.setInCsfClientServerCommunicationsAction("PD");
//
//				opList.execute();
//
//				Errormsg = opList.getOutCsfStringsString500();
//				if (oplList.getException() != null) throw oplList.getException();
//
//				if (opList.getExitStateType() < 3) throw new Exception(opList.getExitStateMsg());
//
//				if ((Errormsg != null) && (!Errormsg.equals(""))) {
//					messages.add(ActionMessages.GLOBAL_MESSAGE,
//							new ActionMessage("error.coolgenerror", Errormsg));
//					addErrors(request, messages);
//					return mapping.findForward("step2forward");
//				} else {
//					count = opList.getOutGroupCount();
//					list = activityForm.getActivityRecords();
//					for (int i=1; i<count; i++) {
//						// coolgen page down returns last element of previous list again in new list, test for it against new list and dont add if already there
//						boolean add = true;
//						for (int j = 0; j < list.size(); j++) {
//							ActivityRecord act = (ActivityRecord) list.get(j);
//							SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
//							String formatDate = formatter.format(opList.getOutGrMdActivityActivityDate(i).getTime());
//							if(act.getActivityDate().equals(formatDate)&& act.getEntryTimestamp().equals(opList.getOutGrMdActivityEntryTimestamp(i))){
//								add=false;
//								break;
//							}
//						}
//						if(add){
//							ActivityRecord activity = new ActivityRecord();
//							activity.setActivityCode(Short.toString(opList.getOutGrMdActivityActivityCode(i)));
//							SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
//							String formatDate="";
//							if (opList.getOutGrMdActivityActivityDate(i)!= null){
//								formatDate = formatter.format(opList.getOutGrMdActivityActivityDate(i).getTime());
//								activity.setActivityDate(formatDate);
//							} else{
//								activity.setActivityDate("");
//							}
//							activity.setActivityDescr(opList.getOutGrDescriptionsCsfStringsString40(i));
//							activity.setComments(opList.getOutGrMdActivityComments(i));
//							if (opList.getOutGrMdActivityFeedbackDate(i)!= null){
//								formatDate = formatter.format(opList.getOutGrMdActivityFeedbackDate(i).getTime());
//								activity.setFeedbackDate(formatDate);
//							} else{
//								activity.setFeedbackDate("");
//							}
//							activity.setUserName(opList.getOutGrDescriptionsCsfStringsString50(i));
//							activity.setUserCode(opList.getOutGrMdActivityStaffNumber(i));
//							activity.setEntryTimestamp(opList.getOutGrMdActivityEntryTimestamp(i));
//							list.add(activity);
//						}
//					}
//
//		        }
//				pageDown = opList.getOutCsfClientServerCommunicationsRgvScrollDownFlag();
//			} // end of page down

		}

		return mapping.findForward("displayforward");

		}catch (Exception e){
			throw new Exception("MdActivityDisplayAction(display): / "+e.getMessage());
		}
	}
	
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
		toolManager = (ToolManager) ComponentManager.get(ToolManager.class);
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);

		MdActivityForm activityForm = (MdActivityForm) form;
		ActionMessages messages = new ActionMessages();

		try{
		// ------- at the moment only does one parent update: 
		//         registration permission -----------

		// read M and D records
		operListener opl = new operListener();
		Srmas01sMDStudentActivity op = new Srmas01sMDStudentActivity();
		op.addExceptionListener(opl);
	    op.clear();

    	op.setInCsfClientServerCommunicationsClientVersionNumber((short) 3);
	 	op.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
	    op.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
	    op.setInSecurityWsUserNovellUserCode(activityForm.getUserCode());
	    op.setInWsStudentNr(Integer.parseInt(activityForm.getStudent().getNumber()));
	    op.setInStudentDissertationMkStudyUnitCode(activityForm.getStudyUnitCode());
	    op.setInCsfClientServerCommunicationsAction("U");
	    
	    op.setInWsStudentPostGraduateStudiesApproved(activityForm.getRegPermission());

		op.execute();

	    String Errormsg = op.getOutCsfStringsString500();
	    if (opl.getException() != null) throw opl.getException();

        if (op.getExitStateType() < 3) throw new Exception(op.getExitStateMsg());

        if ((Errormsg != null) && (!Errormsg.equals(""))) {
        	messages.add(ActionMessages.GLOBAL_MESSAGE,
        	new ActionMessage("error.coolgenerror", Errormsg));
        	addErrors(request, messages);
        } else {			
			/* Write event log: Get user details, if not empty write event log with usagesession */
			Session currentSession = sessionManager.getCurrentSession();
			String userID = currentSession.getUserId();
			if (userID != null) {
				UsageSession usageSession = usageSessionService.getSession();
					//UsageSessionService.startSession(currentUserID,request.getRemoteAddr(),request.getHeader("user-agent"));
				eventTrackingService.post(
					eventTrackingService.newEvent(EventTrackingTypes.EVENT_MDACTIVITY_EDIT, toolManager.getCurrentPlacement().getContext(), false),usageSession);
			}
        }
        
		return mapping.findForward("displayforward");

		}catch (Exception e){
			throw new Exception("MdActivityDisplayAction(display): / "+e.getMessage());
		}
	}

	public ActionForward updateActivity(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		MdActivityForm activityForm = (MdActivityForm) form;
		ActionMessages messages = new ActionMessages();
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
		toolManager = (ToolManager) ComponentManager.get(ToolManager.class);
		sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
		userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);

		try{

		// == VALIDATE INPUT ==
		if ("add".equalsIgnoreCase(request.getParameter("frompage"))){
			// do add input tests
			// check/set selected activity
			if (activityForm.getSelectedActivity()!= null && "-1".equals(activityForm.getSelectedActivity())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
						"Select student activity."));
				addErrors(request, messages);
				// Setup activity pick list
				setUpActivityPickList(request);
				return mapping.findForward("addforward");
			}
			if (activityForm.getSelectedActivity()!= null && !"".equals(activityForm.getSelectedActivity())){
				GeneralItem item = new GeneralItem();
				item = getItem(activityForm.getSelectedActivity());
				activityForm.getNewActivity().setActivityCode(item.getCode());
				activityForm.getNewActivity().setActivityDescr(item.getDesc());
			}
			// check input activity date
			String errorMessage="";
			errorMessage = validateDate(activityForm.getNewActivity().getActivityDate());
			if(!"".equals(errorMessage)){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
					"Activity date"+ errorMessage));
				addErrors(request, messages);
				// Setup activity pick list
				setUpActivityPickList(request);
				return mapping.findForward("addforward");
			}
			errorMessage = checkActivityDate(activityForm.getNewActivity().getActivityDate());
			if(!"".equals(errorMessage)){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",errorMessage));
				addErrors(request, messages);
				// Setup activity pick list
				setUpActivityPickList(request);
				return mapping.findForward("addforward");
			}
//			 check comment length max=150 chars plus two front slashes should be added every time
			if ( !"".equals(activityForm.getNewActivity().getComments().trim())){
				int commentLen = activityForm.getNewActivity().getComments().trim().length();
				if(commentLen > 150){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
						"Comments can not exceed 150 characters. It is currently "+(commentLen)+" characters."));
					addErrors(request, messages);
					// Setup activity pick list
					setUpActivityPickList(request);
					return mapping.findForward("addforward");
				}
			}
			if (activityForm.getNewActivity().getActivityCode()!=null &&
					(activityForm.getNewActivity().getActivityCode().trim().equalsIgnoreCase("13")||
					activityForm.getNewActivity().getActivityCode().trim().equalsIgnoreCase("14"))){
				if (activityForm.getNewActivity().getComments()==null || activityForm.getNewActivity().getComments().equalsIgnoreCase("")){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
							"Please indicate your reason for selecting this activity in the comment field before clicking on &lt;Submit&gt;"));
						addErrors(request, messages);
						// Setup activity pick list
						setUpActivityPickList(request);
						return mapping.findForward("addforward");
				}
			}					
		}else if ("edit".equalsIgnoreCase(request.getParameter("frompage"))){
			// == do edit input tests ==

			if ( (activityForm.getUpdateActivity().getFeedbackDate()== null || "".equals(activityForm.getUpdateActivity().getFeedbackDate())) && activityForm.isUpdateFeedbackDate()){
				if(activityForm.getUpdateActivity().getExtraComments()==null || "".equals(activityForm.getUpdateActivity().getExtraComments())){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
							"Enter a date of feedback or and/or new comment"));
					addErrors(request, messages);
					// Setup activity pick list
					setUpActivityPickList(request);
					return mapping.findForward("editforward");
				}
			}
			if (!activityForm.isUpdateFeedbackDate()){
				if(activityForm.getUpdateActivity().getExtraComments()==null || "".equals(activityForm.getUpdateActivity().getExtraComments())){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
							"Enter a new comment"));
					addErrors(request, messages);
					// Setup activity pick list
					setUpActivityPickList(request);
					return mapping.findForward("editforward");
				}
			}
			// check comment length max=150 chars plus two front slashes shoud be added every time
			if (activityForm.getUpdateActivity().getExtraComments()!=null && !"".equals(activityForm.getUpdateActivity().getExtraComments())){
				int commentLen = activityForm.getUpdateActivity().getComments().trim().length() + activityForm.getUpdateActivity().getExtraComments().trim().length();
				if(commentLen + 2 > 150){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
						"Previous comments and new comments can not exceed 150 characters. It is currently "+(commentLen + 2 )+" characters."));
					addErrors(request, messages);
					// Setup activity pick list
					setUpActivityPickList(request);
					return mapping.findForward("editforward");
				}
			}			
			if (activityForm.getUpdateActivity().getFeedbackDate()!= null && !"".equals(activityForm.getUpdateActivity().getFeedbackDate())){
				// check input feedback date if it was set
				String errorMessage="";
				errorMessage = validateDate(activityForm.getUpdateActivity().getFeedbackDate());
				if(!"".equals(errorMessage)){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
						"Feedback date"+ errorMessage));
					addErrors(request, messages);
					return mapping.findForward("editforward");
				}
				if(!checkFeedbackDate(activityForm.getUpdateActivity().getFeedbackDate(),activityForm.getUpdateActivity().getActivityDate())){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
							"Feedback date must be later than activity date."));
					addErrors(request, messages);
					return mapping.findForward("editforward");
				}
			}
		}
		if ("add".equalsIgnoreCase(request.getParameter("frompage"))){
			if (activityForm.getNewActivity().getActivityCode()!=null &&
					(activityForm.getNewActivity().getActivityCode().trim().equalsIgnoreCase("13")||
					activityForm.getNewActivity().getActivityCode().trim().equalsIgnoreCase("14"))){
				activityForm.setRegPermission("N");
			}else{
				activityForm.setRegPermission("Y");
			}
		}


		// Setup to go to server
		operListener opl = new operListener();

		Srmas01sMDStudentActivity op = new Srmas01sMDStudentActivity();

		op.addExceptionListener(opl);
	    op.clear();

    	op.setInCsfClientServerCommunicationsClientVersionNumber((short) 3);
	 	op.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
	    op.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
	    op.setInSecurityWsUserNovellUserCode(activityForm.getUserCode());

	    op.setInWsStudentNr(Integer.parseInt(activityForm.getStudent().getNumber()));
	    op.setInStudentDissertationMkStudyUnitCode(activityForm.getStudyUnitCode());
	    // Set staff number
		op.setInGrMdActivityStaffNumber(0,activityForm.getStaffNumber());
	    op.setInCsfClientServerCommunicationsAction("U");
	    op.setInWsStudentPostGraduateStudiesApproved(activityForm.getRegPermission());

	    // -- SET GROUP WITH ITEM TO BE INSERTED --
	    if ("add".equalsIgnoreCase(request.getParameter("frompage"))){
		    // Set group action according to add/edit
			op.setInGrCsfLineActionBarAction(0,"I");
		    // Set activity code
			op.setInGrMdActivityActivityCode(0,Short.parseShort(activityForm.getNewActivity().getActivityCode()));
		    // Set activity date
			Calendar cal = Calendar.getInstance();
			if (activityForm.getNewActivity().getActivityDate()!= null && !"".equals(activityForm.getNewActivity().getActivityDate())){
				int year = Integer.parseInt((activityForm.getNewActivity().getActivityDate()).substring(0,4));
				int month = Integer.parseInt((activityForm.getNewActivity().getActivityDate()).substring(4,6));
				int day = Integer.parseInt((activityForm.getNewActivity().getActivityDate()).substring(6,8));
				cal.set(year,month-1,day);
				op.setInGrMdActivityActivityDate(0,cal);
			}
			// Set comment
			if (activityForm.getNewActivity().getComments()!= null){
				op.setInGrMdActivityComments(0,activityForm.getNewActivity().getComments().trim());
			}
			// Set entry timestamp
			Calendar currentCal = Calendar.getInstance();
			op.setInGrMdActivityEntryTimestamp(0,currentCal);
			// Set feedback date
			if (activityForm.getNewActivity().getFeedbackDate()!= null && !"".equals(activityForm.getNewActivity().getFeedbackDate())){
				int year = Integer.parseInt((activityForm.getNewActivity().getFeedbackDate()).substring(0,4));
				int month = Integer.parseInt((activityForm.getNewActivity().getFeedbackDate()).substring(4,6));
				int day = Integer.parseInt((activityForm.getNewActivity().getFeedbackDate()).substring(6,8));
				cal.set(year,month-1,day);
				op.setInGrMdActivityFeedbackDate(0,cal);
			}
		// -- SET GROUP WITH ITEM TO BE MODIFIED --
		}else if ("edit".equalsIgnoreCase(request.getParameter("frompage"))){
			op.setInGrCsfLineActionBarAction(0,"M");
			 // Set feedback date
			Calendar cal = Calendar.getInstance();
			if (activityForm.getUpdateActivity().getFeedbackDate()!= null && !"".equals(activityForm.getUpdateActivity().getFeedbackDate())){
				int year = Integer.parseInt((activityForm.getUpdateActivity().getFeedbackDate()).substring(0,4));
				int month = Integer.parseInt((activityForm.getUpdateActivity().getFeedbackDate()).substring(4,6));
				int day = Integer.parseInt((activityForm.getUpdateActivity().getFeedbackDate()).substring(6,8));
				cal.set(year,month-1,day);
				op.setInGrMdActivityFeedbackDate(0,cal);
			}
			 // Set activity code
			op.setInGrMdActivityActivityCode(0,Short.parseShort(activityForm.getUpdateActivity().getActivityCode()));
			// Set comment
			if (activityForm.getUpdateActivity().getExtraComments()== null){
				activityForm.getUpdateActivity().setExtraComments("");
			}
			if (activityForm.getUpdateActivity().getComments()== null){
				activityForm.getUpdateActivity().setComments("");
			}
			if (!"".equals(activityForm.getUpdateActivity().getExtraComments())){
				 if (activityForm.getUpdateActivity().getComments()==null || "".equals(activityForm.getUpdateActivity().getComments())){
					 op.setInGrMdActivityComments(0,activityForm.getUpdateActivity().getExtraComments().trim());
				 }else{
					 op.setInGrMdActivityComments(0,activityForm.getUpdateActivity().getComments().trim()+"//"+activityForm.getUpdateActivity().getExtraComments().trim());
				 }
			} else{
				op.setInGrMdActivityComments(0,activityForm.getUpdateActivity().getComments().trim());
			}
			// set entry timestamp to its current value
			op.setInGrMdActivityEntryTimestamp(0,activityForm.getUpdateActivity().getEntryTimestamp());
		}

		op.execute();

	    String Errormsg = op.getOutCsfStringsString500();
	    if (opl.getException() != null) throw opl.getException();

        if (op.getExitStateType() < 3) throw new Exception(op.getExitStateMsg());

        if ((Errormsg != null) && (!Errormsg.equals(""))) {
        	messages.add(ActionMessages.GLOBAL_MESSAGE,
        			new ActionMessage("error.coolgenerror", Errormsg));
        	addErrors(request, messages);
        	return mapping.findForward("addforward");
        } else {
//        	int count = op.getOutGroupCount();
//        	ArrayList list = new ArrayList();
//        	for (int i=1; i<count; i++) {
//				ActivityRecord activity = new ActivityRecord();
//				activity.setActivityCode(Short.toString(op.getOutGrMdActivityActivityCode(i)));
//				SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
//				String formatDate="";
//				if (op.getOutGrMdActivityActivityDate(i)!= null){
//					formatDate = formatter.format(op.getOutGrMdActivityActivityDate(i).getTime());
//					activity.setActivityDate(formatDate);
//				} else{
//					activity.setActivityDate("");
//				}
//				activity.setActivityDescr(op.getOutGrDescriptionsCsfStringsString40(i));
//				activity.setComments(op.getOutGrMdActivityComments(i));
//				if (op.getOutGrMdActivityFeedbackDate(i)!= null){
//					formatDate = formatter.format(op.getOutGrMdActivityFeedbackDate(i).getTime());
//					activity.setFeedbackDate(formatDate);
//				} else{
//					activity.setFeedbackDate("");
//				}
//				activity.setUserName(op.getOutGrDescriptionsCsfStringsString50(i));
//				activity.setUserCode(op.getOutGrMdActivityStaffNumber(i));
//				activity.setEntryTimestamp(op.getOutGrMdActivityEntryTimestamp(i));
//				list.add(activity);
//			}
        	//Read activities with a direct sql in java instead of coolgen server - Johanet 20120827
			ArrayList<ActivityRecord> list = new ArrayList<ActivityRecord>();
			MdActivityQueryDAO dao = new MdActivityQueryDAO();
			list = dao.getMDActivities(Integer.parseInt(activityForm.getStudent().getNumber()), activityForm.getStudyUnitCode());
			activityForm.setActivityRecords(list);
		}

        if ("edit".equalsIgnoreCase(request.getParameter("frompage"))){
        	activityForm.setSelectedActivityRecord("");
        }

        /* Write event log: Get user details, if not empty write event log with usagesession */
    	Session currentSession = sessionManager.getCurrentSession();
    	String userID = currentSession.getUserId();
        if ("edit".equalsIgnoreCase(request.getParameter("frompage"))){
	        if (userID != null) {
	        	//currentUserID = currentSession.getUserEid();
	        	UsageSession usageSession = usageSessionService.getSession();
	        	eventTrackingService.post(
				eventTrackingService.newEvent(EventTrackingTypes.EVENT_MDACTIVITY_EDIT, toolManager.getCurrentPlacement().getContext(), false),usageSession);
	        }
		}else if ("add".equalsIgnoreCase(request.getParameter("frompage"))){
			if (userID != null) {
				UsageSession usageSession = usageSessionService.getSession();
				eventTrackingService.post(
					eventTrackingService.newEvent(EventTrackingTypes.EVENT_MDACTIVITY_ADD, toolManager.getCurrentPlacement().getContext(), false),usageSession);
		        }
		}

		return mapping.findForward("displayforward");

		}catch (Exception e){
			throw new Exception("MdActivityDisplayAction(display): / "+e.getMessage());
		}
	}

	public ActionForward addActivity(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// reset new activity values
		MdActivityForm activityForm = (MdActivityForm) form;
		ActionMessages messages = new ActionMessages();

		activityForm.setNewActivity(new ActivityRecord());
		activityForm.setSelectedActivity("-1");
		/*if(!"RG".equalsIgnoreCase(activityForm.getStudentAnnualStatus())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
					"Student not registered for the study unit."));
			addErrors(request, messages);
			return mapping.findForward("displayforward");
		}*/
		if (activityForm.isReadOnly()){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
					activityForm.getErrorMessage()));
			addErrors(request, messages);
			return mapping.findForward("displayforward");
		}
		// Setup activity pick list
		setUpActivityPickList(request);

		return mapping.findForward("addforward");

	}

	private void setUpActivityPickList(HttpServletRequest request) throws Exception{
		MdActivityQueryDAO dao = new MdActivityQueryDAO();
		List list = dao.getActivityCodes();
		list.add(0,new LabelValueBean("Select student activity","-1"));
		request.setAttribute("list",list);
	}

	public ActionForward editActivity(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		MdActivityForm activityForm = (MdActivityForm) form;
		ActionMessages messages = new ActionMessages();
		// -- CHECK INPUT --
		if(activityForm.getSelectedActivityRecord()!=null && !"".equals(activityForm.getSelectedActivityRecord())){

		}else{
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
						"Select an activity record from the list to edit."));
			addErrors(request, messages);
			return mapping.findForward("displayforward");
		}
		if (activityForm.isReadOnly()){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
					activityForm.getErrorMessage()));
			addErrors(request, messages);
			return mapping.findForward("displayforward");
		}
		// reset new activity values
		activityForm.setUpdateActivity(new ActivityRecord());
		// Get selected activity record from index
		ActivityRecord record = new ActivityRecord();
		record = (ActivityRecord) activityForm.getActivityRecords().get(Integer.parseInt(activityForm.getSelectedActivityRecord()));
		ActivityRecord updateActivityRecord = new ActivityRecord();
		updateActivityRecord.setActivityCode(record.getActivityCode());
		updateActivityRecord.setActivityDate(record.getActivityDate());
		updateActivityRecord.setActivityDescr(record.getActivityDescr());
		updateActivityRecord.setComments(record.getComments());
		updateActivityRecord.setExtraComments(record.getExtraComments());
		updateActivityRecord.setEntryTimestamp(record.getEntryTimestamp());
		updateActivityRecord.setFeedbackDate(record.getFeedbackDate());
		updateActivityRecord.setUserCode(record.getUserCode());
		updateActivityRecord.setUserName(record.getUserName());
		activityForm.setUpdateActivity(updateActivityRecord);

		//May only update for following activities:
		/*boolean canUpdate = false;
		switch (Integer.parseInt(record.getActivityCode())){
			case 2:  canUpdate = true; break;
			case 3:  canUpdate = true; break;
			case 4:  canUpdate = true; break;
			case 5:  canUpdate = true; break;
			case 9:  canUpdate = true; break;
			default: canUpdate = false;break;
		}*/
		// removed tests as requested by user
		boolean canUpdate = true;
		
		// Can only update feedback date if still empty
		if (record.getFeedbackDate()== null || "".equals(record.getFeedbackDate())){
			activityForm.setUpdateFeedbackDate(true);
		}else{
			activityForm.setUpdateFeedbackDate(false);
		}

		if (canUpdate){
			return mapping.findForward("editforward");
		}else{
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
						"No amendments allowed - add new activity."));
			addErrors(request, messages);
			return mapping.findForward("displayforward");
		}

	}

	public ActionForward cancel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		MdActivityForm activityForm = (MdActivityForm) form;
		if ("display".equalsIgnoreCase(request.getParameter("goto"))){
			activityForm.setSelectedStudyUnit("");
			activityForm.setSelectedActivityRecord("");
			return mapping.findForward("displayforward");
		}else{
			reset((MdActivityForm)form);
			return mapping.findForward("cancelforward");
		}
	}

	private String validateDate(String testDate){
		String errorMessage = "";


		//Check date format
		try{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
			formatter.setLenient(false);
			Date test = formatter.parse(testDate);
		}catch (ParseException e){
				errorMessage=" must be entered in the following format: YYYYMMDD";
				return errorMessage;
		}
		// check date length
		if (testDate.length()!= 8){
			errorMessage=" must be entered in the following format: YYYYMMDD";
			return errorMessage;
		}

		//check for numeric
        //check year
		if (!isNumeric(testDate.substring(0,4))){
			errorMessage=" must be numeric.";
			return errorMessage;
		}
		//check month
		if (!isNumeric(testDate.substring(4,6))){
			errorMessage=" must be numeric.";
			return errorMessage;
		}
		//check day
		if (!isNumeric(testDate.substring(6,8))){
			errorMessage=" must be numeric.";
			return errorMessage;
		}

		// Must not be future date
		Calendar currentDate = Calendar.getInstance();
		Calendar inputDate = Calendar.getInstance();
		int year = Integer.parseInt(testDate.substring(0,4));
		int month = Integer.parseInt(testDate.substring(4,6));
		int day = Integer.parseInt(testDate.substring(6,8));
		// Remember java months start at zero!
		inputDate.set(year,month-1,day,0,0);
		currentDate.set(currentDate.get(Calendar.YEAR),currentDate.get(Calendar.MONTH),currentDate.get(Calendar.DATE),0,0);
		int result = inputDate.compareTo(currentDate);
		if (result<0 || result==0) {
		}else{
			errorMessage=" may not be a future date.";
		}

		return errorMessage;
	}

	private String checkActivityDate(String testDate){
		String errorMessage = "";

		// Date may only be current year OR current year -1
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		int year = Integer.parseInt(testDate.substring(0,4));
		// Remember java months start at zero!
		if (year == currentYear || year == currentYear-1) {
		}else{
			errorMessage="Activity date error - not a valid year.";
		}

		return errorMessage;
	}

	private boolean checkFeedbackDate(String feedbackDate, String activityDate){
		boolean result = true;

		// Feedback date must be greater or equal to activity date
		Calendar feedback = Calendar.getInstance();
		int year = Integer.parseInt(feedbackDate.substring(0,4));
		int month = Integer.parseInt(feedbackDate.substring(4,6));
		int day = Integer.parseInt(feedbackDate.substring(6,8));
		feedback.set(year,month-1,day,0,0);

		Calendar activity = Calendar.getInstance();
		year = Integer.parseInt(feedbackDate.substring(0,4));
		month = Integer.parseInt(feedbackDate.substring(4,6));
		day = Integer.parseInt(feedbackDate.substring(6,8));
		activity.set(year,month-1,day,0,0);

		int compareResult = feedbackDate.compareTo(activityDate);
		if (compareResult>0 || compareResult==0) {
			result = true;
		}else{
			result = false;
		}

		return result;
	}

	private boolean isNumeric(String testString){

		boolean result = true;

		try{
			Long.parseLong(testString);
		} catch (NumberFormatException e) {
			result = false;
		}

		return result;

	}

	public void reset(MdActivityForm form) {

		form.setStudent(new Student());
		form.setDisTitle("");
		form.setSelectedStudyUnit("");
		form.setSelectedActivityRecord("");

	}

	public ActionForward unspecified(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response){

		log.info("MdActivityAction: unspecified method call -no value for parameter in request");
		return mapping.findForward("home");

	}

	private GeneralItem getItem(String inputStr){
		GeneralItem item = new GeneralItem();
		int pos = 0;

		pos = inputStr.indexOf("-");
		item.setDesc(inputStr.substring(pos+1,inputStr.length()));
		item.setCode(inputStr.substring(0,pos));
		return item;
	}

}
