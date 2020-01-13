package za.ac.unisa.lms.tools.exampapers.actions;

import java.util.Calendar;
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
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.tool.api.ToolManager;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserDirectoryService;

import Expll01h.Abean.Expll01sMntExamPaperLog;

import za.ac.unisa.lms.dao.StudentSystemGeneralDAO;
import za.ac.unisa.lms.dao.general.UserDAO;
import za.ac.unisa.lms.domain.general.Person;
import za.ac.unisa.lms.tools.exampapers.forms.*;
import za.ac.unisa.lms.tools.exampapers.actions.*;
import za.ac.unisa.lms.tools.exampapers.dao.*;
import za.ac.unisa.security.SecurityServices;

public class ManagementInformationAction extends LookupDispatchAction{
	
	private SessionManager sessionManager;
	private UserDirectoryService userDirectoryService;
	private ToolManager toolManager;
	
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
	
protected Map getKeyMethodMap(){
	Map map = new HashMap();
	map.put("displayInput", "displayInput");	
	map.put("button.cancel", "cancel");
	map.put("button.extract", "extract");
	map.put("instruction","instruction");
	return map;		
}
public ActionForward displayInput(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) throws Exception {
		
		String exams[] = {"01","JAN/FEB","06","MAY/JUN","10","OCT/NOV"};
		ExamPaperCoverDocketForm coverDocketForm = (ExamPaperCoverDocketForm) form;
		ActionMessages messages = new ActionMessages();
		
		//Get printfriendly URL
		toolManager = (ToolManager)ComponentManager.get(ToolManager.class);
		String serverPath = ServerConfigurationService.getToolUrl();
		String toolId = toolManager.getCurrentPlacement().getId();
		coverDocketForm.setPrintFriendlyUrl(serverPath.trim() + "/" + toolId.trim() + "/examPaperCoverDocket.do?action=print");
				
		//Get user details
		coverDocketForm.setNovellUserid(null);
		User user = null;
		
		sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
	    userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
		Session currentSession = sessionManager.getCurrentSession();
		String userID  = currentSession.getUserId();
				
		if (userID != null) {
			user = userDirectoryService.getUser(userID);
			coverDocketForm.setNovellUserid(user.getEid().toUpperCase());
		}		
	
		Person person = new Person();
		UserDAO userdao = new UserDAO();
		person = userdao.getPerson(coverDocketForm.getNovellUserid());
				
		if (person.getPersonnelNumber()==null){
			//user does not have access to this function 
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"User not found"));
			addErrors(request,messages);
			return mapping.findForward("displayErrorPage");
		}
		
		coverDocketForm.setUser(person);
		StudentSystemGeneralDAO daoxamprd = new StudentSystemGeneralDAO();
		List examPeriods = daoxamprd.getExamPeriods();
		coverDocketForm.setExamPeriodCodes(examPeriods);
		coverDocketForm.setYear(String.valueOf(getDefaultExamYear()));
		//get all possible Reports
		StudentSystemGeneralDAO daogen = new StudentSystemGeneralDAO();
		List listReports = daogen.getGenCodes((short)89,2);
		request.setAttribute("listReports",listReports);
		
		ManagementInfoDAO dao = new ManagementInfoDAO();
		DepartmentRecord dpt = new DepartmentRecord();
		CollegeRecord college = new CollegeRecord();
		
		dpt = dao.getDepartment(Short.parseShort(person.getDepartmentCode()));
		
		college = dao.getCollege(dpt.getCollegeCode());
		
		List listDepartments = dao.getDepartmentList(dpt.getCollegeCode());
		request.setAttribute("listDepartments", listDepartments);
		
		coverDocketForm.setSelectedDepartment(dpt.getCode());
		coverDocketForm.setDepartment(dpt);
		coverDocketForm.setCollege(college);
						
		return mapping.findForward("input");
  }

	public ActionForward cancel(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) throws Exception {
		
		ExamPaperCoverDocketForm coverDocketForm = (ExamPaperCoverDocketForm) form;
						
		return mapping.findForward("cancel");
		
		}
	
	public ActionForward instruction(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
			ExamPaperCoverDocketForm coverDocketForm = (ExamPaperCoverDocketForm) form;
							
			return mapping.findForward("instruction");
			
			}
	
	public ActionForward extract(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
			ExamPaperCoverDocketForm coverDocketForm = (ExamPaperCoverDocketForm) form;
			ActionMessages messages = new ActionMessages();
			
			//get all possible Reports
			StudentSystemGeneralDAO daogen = new StudentSystemGeneralDAO();
			List listReports = daogen.getGenCodes((short)89,2);
			request.setAttribute("listReports",listReports);
			
			ManagementInfoDAO dao = new ManagementInfoDAO();
			List listDepartments = dao.getDepartmentList(coverDocketForm.getCollege().getCode());
			request.setAttribute("listDepartments", listDepartments);
			
			coverDocketForm.setReportMessage("");
			
			//Do validation
			if (coverDocketForm.getYear()==null || coverDocketForm.getYear().trim().equalsIgnoreCase("")){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"Examination Year is required"));
			}else{
				if (!isInteger(coverDocketForm.getYear())){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"Examination Year must be numeric"));
				}
			}
			
			if (coverDocketForm.getUser().getEmailAddress()==null || coverDocketForm.getUser().getEmailAddress().trim().equalsIgnoreCase("")){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"Email address is required"));
			}
				
			if (!messages.isEmpty()) {
				addErrors(request,messages);
				return mapping.findForward("input");
			}
			
			Expll01sMntExamPaperLog op = new Expll01sMntExamPaperLog();
			operListener opl = new operListener();
			op.clear();
			
			op.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
			op.setInCsfClientServerCommunicationsClientVersionNumber((short) 3); 
			op.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
			
			if (coverDocketForm.getSelectedReport().equalsIgnoreCase("A")){
				op.setInCsfClientServerCommunicationsAction("PQ");
				op.setInWsExpll01QuestPapOption("A");
			}
			if (coverDocketForm.getSelectedReport().equalsIgnoreCase("B")){
				op.setInCsfClientServerCommunicationsAction("PO");
			}
			if (coverDocketForm.getSelectedReport().equalsIgnoreCase("C")){
				op.setInCsfClientServerCommunicationsAction("PQ");
				op.setInWsExpll01QuestPapOption("C");
			}
			
			op.setInSecurityWsUserNovellUserCode(coverDocketForm.getUser().getNovellUserId());
			op.setInSecurityWsUserEMail(coverDocketForm.getUser().getEmailAddress());
			
			op.setInWsDepartmentCode(coverDocketForm.getSelectedDepartment());
			op.setInWsUnisaCollegeCode(coverDocketForm.getCollege().getCode());
			op.setInOnlineOrInternetCsfStringsString1("I");
			op.setInOutstExamPapersWsStudyUnitPeriodDetailMkExamYear(Short.parseShort(coverDocketForm.getYear()));
			op.setInOutstExamPapersWsStudyUnitPeriodDetailMkExamPeriod(coverDocketForm.getPeriod());
			op.setInKeyExamTypistLogExamYear(Short.parseShort(coverDocketForm.getYear()));
			op.setInKeyExamTypistLogMkExamPeriodCod(coverDocketForm.getPeriod());
			
			op.execute();
			if (opl.getException() != null)
				throw opl.getException();
			if (op.getExitStateType() < 3)
				throw new Exception(op.getExitStateMsg());
					
			if (op.getOutErrMsgCsfStringsString500() != "" ) {
				if (op.getOutErrMsgCsfStringsString500().indexOf("Success")!=-1){
					coverDocketForm.setReportMessage(op.getOutErrMsgCsfStringsString500());
				}else{
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
									op.getOutErrMsgCsfStringsString500()));	
					addErrors(request,messages);
				}
			}
			return mapping.findForward("input");
			
			}

public Short getDefaultExamPeriod() {
	int month;
	Short ExamPeriod = 1;
	month = Calendar.getInstance().get(Calendar.MONTH);
	month++;  //Calender - January = 0
	switch (month) {
	case 1:
	case 2:
	case 3:
		ExamPeriod = 6;
		break;
	case 4:
	case 5:
	case 6:
	case 7:			
	case 8:
		ExamPeriod = 10;
		break;
	case 9:
	case 10:
	case 11:
	case 12:
		ExamPeriod = 1;
		break;
	}
	return ExamPeriod;
	}


public int getDefaultExamYear() {
	int year = Calendar.getInstance().get(Calendar.YEAR);
	int month;
	month = Calendar.getInstance().get(Calendar.MONTH);
	month++; //Calender - January = 0
	switch (month) {
	case 1:
	case 2:
	case 3:
	case 4:
	case 5:
	case 6:
	case 7:
	case 8:
		break;
	case 9:
	case 10:
	case 11:
	case 12:
		year++;
		break;
	}
	return year;
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
