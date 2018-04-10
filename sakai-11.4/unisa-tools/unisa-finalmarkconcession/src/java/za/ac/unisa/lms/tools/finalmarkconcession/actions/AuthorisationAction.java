package za.ac.unisa.lms.tools.finalmarkconcession.actions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.email.api.EmailService;

import Exfyq01h.Abean.Exfyq01sMntFiYearStudConc;
import za.ac.unisa.lms.dao.Gencod;
import za.ac.unisa.lms.dao.StudentSystemGeneralDAO;
import za.ac.unisa.lms.dao.general.DepartmentDAO;
import za.ac.unisa.lms.dao.general.UserDAO;
import za.ac.unisa.lms.domain.general.Department;
import za.ac.unisa.lms.domain.general.Person;
import za.ac.unisa.lms.domain.registration.Qualification;
import za.ac.unisa.lms.tools.finalmarkconcession.dao.*;
import za.ac.unisa.lms.tools.finalmarkconcession.forms.AlternativeExamOpportunityRecord;
import za.ac.unisa.lms.tools.finalmarkconcession.forms.AuthRequestRecord;
import za.ac.unisa.lms.tools.finalmarkconcession.forms.Examination;
import za.ac.unisa.lms.tools.finalmarkconcession.forms.FinalMarkConcessionForm;
import za.ac.unisa.lms.tools.finalmarkconcession.forms.FinalMarkStudentRecord;
import za.ac.unisa.lms.tools.finalmarkconcession.forms.ResultRecord;
import za.ac.unisa.lms.tools.finalmarkconcession.forms.StudentContactRecord;
import za.ac.unisa.lms.tools.finalmarkconcession.actions.FinalMarkConcessionAction;
import za.ac.unisa.utils.CoursePeriod;
import za.ac.unisa.utils.CoursePeriodLookup;

public class AuthorisationAction extends LookupDispatchAction{
	
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
		map.put("codAuthRequestList", "codAuthRequestList");	
		map.put("deanAuthRequestList", "deanAuthRequestList");
		map.put("viewConcessionForm","viewConcessionForm");		
		map.put("button.cancel","cancel");
		//map.put("button.sendBack","multiReject");
		map.put("confirm","confirm");
		//map.put("button.confirm","confirm");
		map.put("button.authorise","displayMultiAuthorisationPage");
		map.put("button.reject", "displayMultiRejectPage");
		map.put("button.display", "displayAuthList");
		//map.put("button.requestAuthDean", "requestAuthDean");
		map.put("requestAuthDean", "requestAuthDean");
		map.put("button.selectAll", "selectAll");
		map.put("button.deSelectAll", "deSelectAll");
		return map;
	}
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		FinalMarkConcessionForm fiStudentForm = (FinalMarkConcessionForm ) form;
		
		if (fiStudentForm.getAuthRequestList()!=null){
			for (int i=0; i < fiStudentForm.getAuthRequestList().size(); i++){
				if (request.getParameter("action.view" + String.valueOf(i)) != null) return viewConcessionForm(mapping, form, request, response,i);			
			}
		}				
			return super.execute(mapping, form, request, response);
		}
	
	public ActionForward selectAll(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
			FinalMarkConcessionForm fiStudentForm = (FinalMarkConcessionForm ) form;				
			String[] array = new String[fiStudentForm.getAuthRequestList().size()];
			
			if (fiStudentForm.getAuthRequestList()!=null){
				fiStudentForm.setIndexNrSelectedAuth(new String[fiStudentForm.getAuthRequestList().size()]);
				for (int i=0; i < fiStudentForm.getAuthRequestList().size();i++){
					array[i]= String.valueOf(i);
				}
			}				
			
			fiStudentForm.setIndexNrSelectedAuth(array);		
		   
		    return mapping.findForward("authRequestList");
	}
	
	public ActionForward deSelectAll(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
			FinalMarkConcessionForm fiStudentForm = (FinalMarkConcessionForm ) form;				
			
			String[] array = new String[0];
			fiStudentForm.setIndexNrSelectedAuth(array);		
		   
		    return mapping.findForward("authRequestList");
	}
	
	public ActionForward displayAuthList(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
			FinalMarkConcessionForm fiStudentForm = (FinalMarkConcessionForm ) form;
			ActionMessages messages = new ActionMessages();	
			
			String forward="";
			fiStudentForm.setIndexNrSelectedAuth(new String[0]);
		
		    if (fiStudentForm.getAuthorisationType().equalsIgnoreCase("AUTHREQCOD")){
		    	 forward = getCodAuthRequestList(mapping,form,request,response);		    
			}else {
				 forward = getDeanAuthRequestList(mapping,form,request,response);
		    }		    
		    return mapping.findForward(forward);
	}
	
	public ActionForward requestAuthDean(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
			FinalMarkConcessionForm fiStudentForm = (FinalMarkConcessionForm ) form;
			ActionMessages messages = new ActionMessages();	
			
			String forward="";
					
		    forward = multiAuthorisation(mapping, fiStudentForm, request, response);		    
				    
		    return mapping.findForward(forward);
	}
	
	public ActionForward confirm(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
			FinalMarkConcessionForm fiStudentForm = (FinalMarkConcessionForm ) form;
			ActionMessages messages = new ActionMessages();	
			
			String forward="cancel";
					
		    if (fiStudentForm.getCurrentPage().equalsIgnoreCase("multiAuthPage")){
		    	 forward = multiAuthorisation(mapping, fiStudentForm, request, response);		    
			}
		    if (fiStudentForm.getCurrentPage().equalsIgnoreCase("multiRejectPage")){
				 forward = multiReject(mapping, fiStudentForm, request, response);
		    }		    
		    return mapping.findForward(forward);
	}
	
	public ActionForward cancel(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
			FinalMarkConcessionForm fiStudentForm = (FinalMarkConcessionForm ) form;
			String forward="cancel";
		
			if (fiStudentForm.getCurrentPage().equalsIgnoreCase("authRequestList")){
				String[] array = new String[0];
				fiStudentForm.setIndexNrSelectedAuth(array);
				fiStudentForm.setCurrentPage("input");
				forward="cancel";
			}	
			if (fiStudentForm.getCurrentPage().equalsIgnoreCase("form")||
					fiStudentForm.getCurrentPage().equalsIgnoreCase("multiAuthPage")||
					fiStudentForm.getCurrentPage().equalsIgnoreCase("multiRejectPage")){					
				if (fiStudentForm.getAuthorisationType().equalsIgnoreCase("AUTHREQCOD")){
					forward = getCodAuthRequestList(mapping,form,request,response);
				}else if (fiStudentForm.getAuthorisationType().equalsIgnoreCase("AUTHREQDN")){
					forward = getDeanAuthRequestList(mapping,form,request,response);
				}
			}	
			
//			if(fiStudentForm.getCurrentPage().equalsIgnoreCase("requestDeanAuthorisation")){
//				return mapping.findForward("authorisationPage");
//			}
				
			return mapping.findForward(forward);			
		}
	
	public ActionForward displayMultiRejectPage(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
		FinalMarkConcessionForm fiStudentForm = (FinalMarkConcessionForm ) form;
		ActionMessages messages = new ActionMessages();	
		
		if (fiStudentForm.getIndexNrSelectedAuth()==null ||
			fiStudentForm.getIndexNrSelectedAuth().length==0){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"Please select one or more items to reject."));
		}
		if (!messages.isEmpty()) {
			addErrors(request,messages);
			fiStudentForm.setCurrentPage("authRequestList");
			return mapping.findForward("authRequestList");	
		}
		
		FinalMarkConcessionDAO dao = new FinalMarkConcessionDAO();		
		
		AuthRequestRecord authRequest = new AuthRequestRecord();
		List confirmList = new ArrayList();
			
		for (int i=0; i <fiStudentForm.getIndexNrSelectedAuth().length; i++) {
			String array[] = fiStudentForm.getIndexNrSelectedAuth();
			authRequest = (AuthRequestRecord)fiStudentForm.getAuthRequestList().get(Integer.parseInt(array[i]));
			confirmList.add(authRequest);
		}
		String dpt=dao.getStudyUnitDpt(((AuthRequestRecord)confirmList.get(0)).getStudyUnit());
		request.setAttribute("confirmList", confirmList);
		fiStudentForm.setAuthComment("");
		
		fiStudentForm.setCurrentPage("multiRejectPage");
	    return mapping.findForward("multiRejectPage");	
	}
	
	public ActionForward displayMultiAuthorisationPage(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
		FinalMarkConcessionForm fiStudentForm = (FinalMarkConcessionForm ) form;
		ActionMessages messages = new ActionMessages();	
		
		if (fiStudentForm.getIndexNrSelectedAuth()==null ||
			fiStudentForm.getIndexNrSelectedAuth().length==0){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"Please select one or more items to authorise."));
		}
		if (!messages.isEmpty()) {
			addErrors(request,messages);
			fiStudentForm.setCurrentPage("authRequestList");
			return mapping.findForward("authRequestList");	
		}
		
		FinalMarkConcessionDAO dao = new FinalMarkConcessionDAO();
		
		AuthRequestRecord authRequest = new AuthRequestRecord();
		List confirmList = new ArrayList();
			
		for (int i=0; i <fiStudentForm.getIndexNrSelectedAuth().length; i++) {
			String array[] = fiStudentForm.getIndexNrSelectedAuth();
			authRequest = (AuthRequestRecord)fiStudentForm.getAuthRequestList().get(Integer.parseInt(array[i]));
			confirmList.add(authRequest);
		}
		String dpt=dao.getStudyUnitDpt(((AuthRequestRecord)confirmList.get(0)).getStudyUnit());
		request.setAttribute("confirmList", confirmList);
					
		if (fiStudentForm.getAuthorisationType().equalsIgnoreCase("AUTHREQCOD")){	  	
			DepartmentDAO deptDAO = new DepartmentDAO();
			Department department = new Department();
			department = deptDAO.getDepartment(Short.parseShort((dpt)));
			fiStudentForm.setAuthDepartment(department);
			List deanList = new ArrayList();
			List deputyDeanList = new ArrayList();
			List directorList = new ArrayList();
			List deputyDirectorList = new ArrayList();
			if (fiStudentForm.getAuthDepartment().getDean().getPersonnelNumber()!=null){
				deanList.add(department.getDean());
			}		
			fiStudentForm.setListDeans(deanList);
			//Get Director of school
			if (fiStudentForm.getAuthDepartment().getSchoolDirector().getPersonnelNumber()!=null){
				directorList.add(department.getSchoolDirector());
			}			
			fiStudentForm.setListDirectors(directorList);
			
		}		
		fiStudentForm.setSelectedAuthoriser("");
				
		fiStudentForm.setCurrentPage("multiAuthPage");		
		return  mapping.findForward("multiAuthorisationPage");		
	}
	
	public ActionForward viewConcessionForm(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response,
			int selectedRec) throws Exception {
			
		FinalMarkConcessionForm fiStudentForm = (FinalMarkConcessionForm ) form;
		ActionMessages messages = new ActionMessages();	
		
		AlternativeExamOpportunityRecord altExamOpt = new AlternativeExamOpportunityRecord();
		AuthRequestRecord appl = new AuthRequestRecord();
		Qualification qual = new Qualification();
		ResultRecord result = new ResultRecord();
		FinalMarkStudentRecord fiStudentRec = new FinalMarkStudentRecord();
		StudentContactRecord contactRec = new StudentContactRecord();
		FinalMarkConcessionDAO dao = new FinalMarkConcessionDAO();
		AuthorisationDAO authDao = new AuthorisationDAO();
		FinalMarkConcessionAction action = new FinalMarkConcessionAction();
		StudentSystemGeneralDAO gendao = new StudentSystemGeneralDAO();
		appl = (AuthRequestRecord)(fiStudentForm.getAuthRequestList().get(selectedRec));
		
		fiStudentForm.setExamYear(String.valueOf(appl.getExamYear()));
		fiStudentForm.setExamPeriod(appl.getExamPeriod());
		contactRec=dao.getStudentContactDetails(appl.getStudentNumber());
		fiStudentRec.setContactDetails(contactRec);
		String stuName="";
		stuName=authDao.getStudentName(appl.getStudentNumber());
		fiStudentRec.setName(stuName);		
		
		altExamOpt = dao.getAlternativeAssessmentRecord(appl.getExamYear(),
				appl.getExamPeriod(),
				appl.getStudyUnit(),
				appl.getStudentNumber());		
		
		fiStudentForm.setAlternativeExamOpportunity(altExamOpt);
		
		result = authDao.getStudentResultRecord(appl.getExamYear(), appl.getExamPeriod(), appl.getStudyUnit(), appl.getStudentNumber());
		
		qual = dao.getStudentQualification(appl.getStudentNumber(), appl.getStudyUnit(), result.getAcademicYear(), result.getSemester());
		CoursePeriodLookup periodLookup = new CoursePeriodLookup();
		fiStudentRec.setQualification(qual);
		fiStudentRec.setAcademicYear(result.getAcademicYear());
		fiStudentRec.setSemesterPeriod(result.getSemester());
		fiStudentRec.setFinalMark(result.getFinalMark());
		fiStudentRec.setYearMarkEarned(String.valueOf(result.getYearMark()));
		fiStudentRec.setStudentNumber(appl.getStudentNumber());
		fiStudentRec.setStudyUnit(appl.getStudyUnit());
		fiStudentRec.setSemesterType(periodLookup.getCourseTypeAsString(result.getSemester()));
		String yearMarkWeight=""+dao.getYearMarkWeight(fiStudentRec.getAcademicYear(),fiStudentRec.getSemesterPeriod(), fiStudentRec.getStudyUnit().trim());
		fiStudentRec.setYearMarkWeight(yearMarkWeight);
		fiStudentForm.setRecordSelected(fiStudentRec);			
		fiStudentForm.setCurrentPage("form");
		return  mapping.findForward("viewConcessionForm");		
	}
	
	public String multiAuthorisation(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {	
		
		FinalMarkConcessionForm fiStudentForm = (FinalMarkConcessionForm ) form;
		ActionMessages messages = new ActionMessages();	
		FinalMarkConcessionDAO dao = new FinalMarkConcessionDAO();	
		
		if (!fiStudentForm.getAuthorisationType().equalsIgnoreCase("AUTHREQCOD") &&
				!fiStudentForm.getAuthorisationType().equalsIgnoreCase("AUTHREQDN")){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"Invalid authorisation type!"));
				}
		
		if (fiStudentForm.getSelectedAuthoriser()==null && fiStudentForm.getChangeStatus().equalsIgnoreCase("AUTHREQDN")){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"Please select a person to notify that an authorisation request was submitted"));
		}
		
		if (!messages.isEmpty()) {
			addErrors(request,messages);
			AuthRequestRecord authRequest = new AuthRequestRecord();
			List<AuthRequestRecord> confirmList = new ArrayList<AuthRequestRecord>();
			for (int i=0; i <fiStudentForm.getIndexNrSelectedAuth().length; i++) {
				String array[] = fiStudentForm.getIndexNrSelectedAuth();
				authRequest = (AuthRequestRecord)fiStudentForm.getAuthRequestList().get(Integer.parseInt(array[i]));
				confirmList.add(authRequest);
			}
			request.setAttribute("confirmList", confirmList);
			fiStudentForm.setCurrentPage("multiAuthPage");
			return "multiAuthorisationPage";
		}
		
		boolean	sendEmail = false;
		if (fiStudentForm.getAuthorisationType().equalsIgnoreCase("AUTHREQCOD")){
			fiStudentForm.setChangeStatus("AUTHREQDN");			
			sendEmail = dao.sendEmailToAuthoriser(Short.parseShort(fiStudentForm.getExamYear()), fiStudentForm.getExamPeriod(), fiStudentForm.getSelectedAuthoriser(),"AUTHREQDN");
		}else{
			fiStudentForm.setChangeStatus("AUTHORISED");
		}
		
		List<AuthRequestRecord> confirmList = new ArrayList<AuthRequestRecord>();
		boolean authoriseSuccess = false;
		String currentStatus="";				
		
		for (int i=0; i < fiStudentForm.getIndexNrSelectedAuth().length; i++){
			AuthRequestRecord authRequest = new AuthRequestRecord();
			String array[] = fiStudentForm.getIndexNrSelectedAuth();
			authRequest = (AuthRequestRecord)fiStudentForm.getAuthRequestList().get(Integer.parseInt(array[i]));			
			
			String requestActionFrom = "";
			if (fiStudentForm.getChangeStatus().equalsIgnoreCase("AUTHREQDN")){
				requestActionFrom=fiStudentForm.getSelectedAuthoriser();
			} else{
				requestActionFrom="";
			}
			
			try {
			currentStatus = dao.getAlternativeAssessmentRecordStatus(authRequest.getExamYear(), authRequest.getExamPeriod(), authRequest.getStudyUnit(), 
						authRequest.getStudentNumber());
			FinalMarkConcessionAction finalAction = new FinalMarkConcessionAction();
			if (!finalAction.isValidStatusChange(currentStatus,fiStudentForm.getChangeStatus())){
				confirmList.add(authRequest);
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
								"Action invalid for concession record with status: " + currentStatus));
				continue;    //terminates rest of the processing in loop for the current iteration, but continues the loop.
			}
			dao.updateAlternativeAssessmentRecord(authRequest.getExamYear(), authRequest.getExamPeriod(), authRequest.getStudyUnit(), 
						authRequest.getStudentNumber(), null, null, null, fiStudentForm.getChangeStatus(), fiStudentForm.getNovellUserid(), "", requestActionFrom);		
			} catch (Exception e) {
				confirmList.add(authRequest);
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
											"Error authorising concession for student: " + String.valueOf(authRequest.getStudentNumber()) +
											", study unit: " + authRequest.getStudyUnit()));
				continue;    //terminates rest of the processing in loop for the current iteration, but continues the loop.
			}
			
			authoriseSuccess=true;
			array[i]=null;	
				
			if (fiStudentForm.getChangeStatus().equalsIgnoreCase("AUTHREJCOD") ||
					fiStudentForm.getChangeStatus().equalsIgnoreCase("AUTHREJDN")||
					fiStudentForm.getChangeStatus().equalsIgnoreCase("AUTHORISED")){
				String responseText="";
				//send email to lecturer who requested authorisation
				String serverpath = ServerConfigurationService.getServerUrl();
				//do not send email on dev & qa -default email to pretoj@unisa.ac.za	
				String toAddress=authRequest.getFiConcession().getAuthResponseEmail();
				if (serverpath.contains("mydev") || serverpath.contains("myqa")){
					toAddress="pretoj@unisa.ac.za";
				}	
				UserDAO daoUser = new UserDAO();
				Person person = new Person();
				person=daoUser.getPerson(fiStudentForm.getNovellUserid());
				
				if (fiStudentForm.getChangeStatus().equalsIgnoreCase("AUTHREJCOD")){
					responseText = "rejected by COD/Stand-in COD " + person.getName();
				}else if (fiStudentForm.getChangeStatus().equalsIgnoreCase("AUTHREJDN")){
					responseText = "rejected by Dean/Deputy Dean/Director " + person.getName();
				}else {
					responseText = "authorised by Dean/Deputy Dean/Director " + person.getName();
				}
				
				AuthorisationDAO authDao = new AuthorisationDAO();
				ResultRecord result = new ResultRecord();
				result = authDao.getStudentResultRecord(authRequest.getExamYear(), authRequest.getExamPeriod(), authRequest.getStudyUnit(), authRequest.getStudentNumber());
				
				String addressee="To Whom it May Concern:";
				String concession=authRequest.getStudentNumber() + ":" + authRequest.getStudyUnit() + " " + 
		           String.valueOf(result.getAcademicYear()) + "/" + result.getSemester();			
				sendNotifyResponseEmail(toAddress, addressee, concession, fiStudentForm.getAuthComment(), responseText);
				Examination nextExam = new Examination();
				nextExam = dao.getNextExamination(Short.parseShort(fiStudentForm.getExamYear()), fiStudentForm.getExamPeriod(), authRequest.getStudyUnit());
				concession = concession + ", suppl exam:" + String.valueOf(nextExam.getExamYear()) + "/" + String.valueOf(nextExam.getExamPeriod());
				//send email to examination admin to inform them that a request had been authorised
				if (fiStudentForm.getChangeStatus().equalsIgnoreCase("AUTHORISED")){
					toAddress="FIconcessions@unisa.ac.za";
					if (serverpath.contains("mydev") || serverpath.contains("myqa")){
						toAddress="pretoj@unisa.ac.za";
					}	
					sendNotifyResponseEmail(toAddress, addressee, concession, fiStudentForm.getAuthComment(), responseText);
				}				
			}
		}  //end for
		
		
		//Delete null value array references
		String[] remove = {null};
		List tempList = new ArrayList(Arrays.asList(fiStudentForm.getIndexNrSelectedAuth()));
		tempList.removeAll(new ArrayList(Arrays.asList(remove)));
		fiStudentForm.setIndexNrSelectedAuth((String[]) tempList.toArray(new String[tempList.size()]));
		
		//if records send to dean for authorisation send email notification to dean
		if (authoriseSuccess && fiStudentForm.getChangeStatus().equalsIgnoreCase("AUTHREQDN")){
			//send email to Dean or Deputy dean.
			//Only one email may be send to Dean per day. Determine if email was send for the day
			//email notification of authorisation request to cod or acting cod					
			if (sendEmail){				
				String toAddress="";
				String addressee="";
				if (fiStudentForm.getSelectedAuthoriser().equalsIgnoreCase(fiStudentForm.getAuthDepartment().getDean().getPersonnelNumber())){
					toAddress=fiStudentForm.getAuthDepartment().getDean().getEmailAddress();
					addressee=fiStudentForm.getAuthDepartment().getDean().getName();
				} else {
					for (int i = 0; i < fiStudentForm.getAuthDepartment().getDeputyDeanList().size(); i++){
						String deputyDean = ((Person)(fiStudentForm.getAuthDepartment().getDeputyDeanList().get(i))).getPersonnelNumber();
						if (fiStudentForm.getSelectedAuthoriser().equalsIgnoreCase(deputyDean)){
							toAddress=((Person)(fiStudentForm.getAuthDepartment().getDeputyDeanList().get(i))).getEmailAddress();
							addressee=((Person)(fiStudentForm.getAuthDepartment().getDeputyDeanList().get(i))).getName();
						}
					}
					if (toAddress.equalsIgnoreCase("")){
						for (int i =0 ; i <fiStudentForm.getListDirectors().size();i++){
							String schoolDirector = ((Person)(fiStudentForm.getListDirectors().get(i))).getPersonnelNumber();
							if (fiStudentForm.getSelectedAuthoriser().equalsIgnoreCase(schoolDirector)){
								toAddress=((Person)(fiStudentForm.getListDirectors().get(i))).getEmailAddress();
								addressee=((Person)(fiStudentForm.getListDirectors().get(i))).getName();
							}
						}
					}
				}
				//do not send email on dev & qa -default email to pretoj@unisa.ac.za
				String serverpath = ServerConfigurationService.getServerUrl();
				if (serverpath.contains("mydev") || serverpath.contains("myqa")){
					toAddress="pretoj@unisa.ac.za";
				}	
				sendNotifyRequestEmail(toAddress, addressee);	
			}	
		}
		
		String forward="cancel";
		if (confirmList.size()==0){
			//all records authorised successfully
			if (fiStudentForm.getAuthorisationType().equalsIgnoreCase("AUTHREQCOD")){
				forward = getCodAuthRequestList(mapping, form, request, response);
			} else if (fiStudentForm.getAuthorisationType().equalsIgnoreCase("AUTHREQDN")){
				forward = getDeanAuthRequestList(mapping, form, request, response);
			} 
		}else{
			messages.add(ActionMessages.GLOBAL_MESSAGE,
			new ActionMessage("message.generalmessage",
								"The remaining fi concession applications could not be authorised, please contact support"));
			addErrors(request,messages);			
			request.setAttribute("confirmList", confirmList);
			fiStudentForm.setCurrentPage("multiAuthPage");
			forward = "multiAuthorisationPage";
		}
		return forward;
	}
	
	public ActionForward codAuthRequestList(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
			FinalMarkConcessionForm fiStudentForm = (FinalMarkConcessionForm ) form;
			ActionMessages messages = new ActionMessages();	
		
		    String forward="";
		    fiStudentForm.setAuthorisationType("AUTHREQCOD");
			fiStudentForm.setAuthListCriteriaExamYear(fiStudentForm.getExamYear());
			fiStudentForm.setAuthListCriteriaExamPeriod(fiStudentForm.getExamPeriod());
			fiStudentForm.setAuthListCriteriaDepartment(Short.parseShort("0"));
		    forward = getCodAuthRequestList(mapping,form,request,response);
		    
		    return mapping.findForward(forward);
	}
	
	public ActionForward deanAuthRequestList(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
			FinalMarkConcessionForm fiStudentForm = (FinalMarkConcessionForm ) form;
			ActionMessages messages = new ActionMessages();	
		
		    String forward="";
		    fiStudentForm.setAuthorisationType("AUTHREQDN");
		    fiStudentForm.setAuthListCriteriaExamYear(fiStudentForm.getExamYear());
			fiStudentForm.setAuthListCriteriaExamPeriod(fiStudentForm.getExamPeriod());
			fiStudentForm.setAuthListCriteriaDepartment(Short.parseShort("0"));
		    forward = getDeanAuthRequestList(mapping,form,request,response);
		    
		    return mapping.findForward(forward);
	}
	public String multiReject(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
			FinalMarkConcessionForm fiStudentForm = (FinalMarkConcessionForm ) form;
			ActionMessages messages = new ActionMessages();	
			FinalMarkConcessionDAO dao = new FinalMarkConcessionDAO();	
			
			if (!fiStudentForm.getAuthorisationType().equalsIgnoreCase("AUTHREQCOD") &&
			!fiStudentForm.getAuthorisationType().equalsIgnoreCase("AUTHREQDN")){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"Invalid authorisation type!"));
			}
			
			if (fiStudentForm.getAuthComment()==null ||
					"".equalsIgnoreCase(fiStudentForm.getAuthComment())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("errors.required",
							"Comment"));
			}else{
				if (fiStudentForm.getAuthComment().length()>250){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"Comment may not exceed 250 characters."));
				}
			}
			if (!messages.isEmpty()) {
				addErrors(request,messages);
				AuthRequestRecord authRequest = new AuthRequestRecord();
				List confirmList = new ArrayList();
				for (int i=0; i <fiStudentForm.getIndexNrSelectedAuth().length; i++) {
					String array[] = fiStudentForm.getIndexNrSelectedAuth();
					authRequest = (AuthRequestRecord)fiStudentForm.getAuthRequestList().get(Integer.parseInt(array[i]));
					confirmList.add(authRequest);
				}
				request.setAttribute("confirmList", confirmList);
				fiStudentForm.setCurrentPage("multiRejectPage");
				return "multiRejectPage";				
			}		
			
			if (fiStudentForm.getAuthorisationType().equalsIgnoreCase("AUTHREQCOD")){
				fiStudentForm.setChangeStatus("AUTHREJCOD");
			}else{
				fiStudentForm.setChangeStatus("AUTHREJDN");
			}
		   
			List<AuthRequestRecord> confirmList = new ArrayList<AuthRequestRecord>();
			String currentStatus="";
					
			for (int i=0; i < fiStudentForm.getIndexNrSelectedAuth().length; i++){
				AuthRequestRecord authRequest = new AuthRequestRecord();
				String array[] = fiStudentForm.getIndexNrSelectedAuth();
				authRequest = (AuthRequestRecord)fiStudentForm.getAuthRequestList().get(Integer.parseInt(array[i]));
				
				try {
					currentStatus = dao.getAlternativeAssessmentRecordStatus(authRequest.getExamYear(), authRequest.getExamPeriod(), authRequest.getStudyUnit(), 
								authRequest.getStudentNumber());
					FinalMarkConcessionAction finalAction = new FinalMarkConcessionAction();
					if (!finalAction.isValidStatusChange(currentStatus,fiStudentForm.getChangeStatus())){
						confirmList.add(authRequest);
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage",
										"Action invalid for concession record with status: " + currentStatus));
						continue;    //terminates rest of the processing in loop for the current iteration, but continues the loop.
					}					
					dao.updateAlternativeAssessmentRecord(authRequest.getExamYear(), authRequest.getExamPeriod(), authRequest.getStudyUnit(), 
								authRequest.getStudentNumber(), null, null, null, fiStudentForm.getChangeStatus(), fiStudentForm.getNovellUserid(), fiStudentForm.getAuthComment(), "");		
					} catch (Exception e) {
						confirmList.add(authRequest);
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage",
													"Error authorising concession for student: " + String.valueOf(authRequest.getStudentNumber()) +
													", study unit: " + authRequest.getStudyUnit()));
						continue;    //terminates rest of the processing in loop for the current iteration, but continues the loop.
					}
				
				array[i]=null;	
					
				if (fiStudentForm.getChangeStatus().equalsIgnoreCase("AUTHREJCOD") ||
						fiStudentForm.getChangeStatus().equalsIgnoreCase("AUTHREJDN")||
						fiStudentForm.getChangeStatus().equalsIgnoreCase("AUTHORISED")){
					String responseText="";
					//send email to lecturer who requested authorisation
					String serverpath = ServerConfigurationService.getServerUrl();
					//do not send email on dev & qa -default email to pretoj@unisa.ac.za	
					String toAddress=authRequest.getFiConcession().getAuthResponseEmail();
					if (serverpath.contains("mydev") || serverpath.contains("myqa")){
						toAddress="pretoj@unisa.ac.za";
					}	
					UserDAO daoUser = new UserDAO();
					Person person = new Person();
					person=daoUser.getPerson(fiStudentForm.getNovellUserid());
					
					if (fiStudentForm.getChangeStatus().equalsIgnoreCase("AUTHREJCOD")){
						responseText = "rejected by COD/Stand-in COD " + person.getName();
					}else if (fiStudentForm.getChangeStatus().equalsIgnoreCase("AUTHREJDN")){
						responseText = "rejected by Dean/Deputy Dean/Dean " + person.getName();
					}else {
						responseText = "authorised by Dean/Deputy Dean/Dean " + person.getName();
					}
					
					AuthorisationDAO authDao = new AuthorisationDAO();
					ResultRecord result = new ResultRecord();
					result = authDao.getStudentResultRecord(authRequest.getExamYear(), authRequest.getExamPeriod(), authRequest.getStudyUnit(), authRequest.getStudentNumber());
						
					String addressee="To Whom it May Concern:";
					String concession=authRequest.getStudentNumber() + ":" + authRequest.getStudyUnit() + " " + 
		            String.valueOf(result.getAcademicYear()) + "/" + result.getSemester();			
					sendNotifyResponseEmail(toAddress, addressee, concession, fiStudentForm.getAuthComment(), responseText);
					Examination nextExam = new Examination();
					nextExam = dao.getNextExamination(Short.parseShort(fiStudentForm.getExamYear()), fiStudentForm.getExamPeriod(), authRequest.getStudyUnit());
					concession = concession + ", suppl exam:" + String.valueOf(nextExam.getExamYear()) + "/" + String.valueOf(nextExam.getExamPeriod());
					//send email to examination admin to inform them that a request had been authorised
					if (fiStudentForm.getChangeStatus().equalsIgnoreCase("AUTHORISED")){
						toAddress="FIconcessions@unisa.ac.za";
						if (serverpath.contains("mydev") || serverpath.contains("myqa")){
							toAddress="pretoj@unisa.ac.za";
						}	
						sendNotifyResponseEmail(toAddress, addressee, concession, fiStudentForm.getAuthComment(), responseText);
					}				
				}
			}  //end for
			
			
			//Delete null value array references
			String[] remove = {null};
			List tempList = new ArrayList(Arrays.asList(fiStudentForm.getIndexNrSelectedAuth()));
			tempList.removeAll(new ArrayList(Arrays.asList(remove)));
			fiStudentForm.setIndexNrSelectedAuth((String[]) tempList.toArray(new String[tempList.size()]));
			
			String forward="cancel";
			if (confirmList.size()==0){
				//all records authorised successfully
				if (fiStudentForm.getAuthorisationType().equalsIgnoreCase("AUTHREQCOD")){
					forward = getCodAuthRequestList(mapping, form, request, response);
				} else if (fiStudentForm.getAuthorisationType().equalsIgnoreCase("AUTHREQDN")){
					forward = getDeanAuthRequestList(mapping, form, request, response);
				} 
			}else{
				messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage",
									"The remaining fi concession applications could not be rejected, please contact support"));
				addErrors(request,messages);			
				request.setAttribute("confirmList", confirmList);
				fiStudentForm.setCurrentPage("multiRejectPage");
				forward = "multiRejectPage";
			}
			
		    return forward;
	}
	
	public String getCodAuthRequestList(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		FinalMarkConcessionForm fiStudentForm = (FinalMarkConcessionForm ) form;
		ActionMessages messages = new ActionMessages();	
		
		String forward="";
		
		AuthorisationDAO authDao = new AuthorisationDAO();
		List deptList = new ArrayList();
		List actingDeptList = new ArrayList();
				
		deptList = authDao.getCODDeptList(Integer.parseInt(fiStudentForm.getMyUnisaUser().getPersonnelNumber()));
		actingDeptList=authDao.getActingCODDeptList(Integer.parseInt(fiStudentForm.getMyUnisaUser().getPersonnelNumber()));
		
		if (deptList.size()==0 && actingDeptList.size()==0){
			//user not HOD
			//determine if user is acting as HOD
			//deptList = authDao.getActingCODDeptList(assessmentCritAuthForm.getNovellUserId());
			if (deptList.size()==0){
				//user does not have access to this function 
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"You do not have the required access to authorise student concessions as a COD."));
				addErrors(request,messages);
				return "cancel";
			}
		}
		
		for (int i=0; i < actingDeptList.size(); i++){
			String inDeptListFlag="N";
			for (int j=0; j < deptList.size(); j++){
				if (((LabelValueBean)actingDeptList.get(i)).getValue().equalsIgnoreCase(((LabelValueBean)deptList.get(j)).getValue())){
					inDeptListFlag="Y";
					j=deptList.size();
				}				
			}
			if (inDeptListFlag.equalsIgnoreCase("N")){
				deptList.add((LabelValueBean)actingDeptList.get(i));
			}
		}
		
		deptList.add(0,new LabelValueBean("ALL","0"));
		fiStudentForm.setListAuthDepartments(deptList);		
		
		List requestList = new ArrayList();
		if (fiStudentForm.getAuthListCriteriaDepartment()==0){
			for (int i=0; i < deptList.size(); i++){
				List tempList = new ArrayList();
				if (Short.parseShort(((LabelValueBean)deptList.get(i)).getValue())!=0){
					tempList = authDao.getAuthoriseRequestList(Short.parseShort(((LabelValueBean)deptList.get(i)).getValue()), fiStudentForm.getAuthorisationType(), Short.parseShort(fiStudentForm.getAuthListCriteriaExamYear()),fiStudentForm.getAuthListCriteriaExamPeriod());
					requestList.addAll(tempList);
				}				
			}
		}else{
			List tempList = new ArrayList();
			tempList = authDao.getAuthoriseRequestList(fiStudentForm.getAuthListCriteriaDepartment(), fiStudentForm.getAuthorisationType(), Short.parseShort(fiStudentForm.getAuthListCriteriaExamYear()),fiStudentForm.getAuthListCriteriaExamPeriod());
			requestList.addAll(tempList);
		}
		
		
		for (int i=0; i < requestList.size(); i++){
			AlternativeExamOpportunityRecord fiConcession = new AlternativeExamOpportunityRecord();
			fiConcession = ((AuthRequestRecord)requestList.get(i)).getFiConcession();
			for (int j=0; j < fiStudentForm.getListAlternativeAssess().size(); j++){
				Gencod gencod = new Gencod();
				gencod = (Gencod)fiStudentForm.getListAlternativeAssess().get(j);
				if (fiConcession.getAlternativeAssessOpt().equalsIgnoreCase(gencod.getCode())){
					fiConcession.setAlternativeAssessOptDesc(gencod.getEngDescription());
				}
			}
			for (int j=0; j < fiStudentForm.getListAcademicSupport().size(); j++){
				Gencod gencod = new Gencod();
				gencod = (Gencod)fiStudentForm.getListAcademicSupport().get(j);
				if (fiConcession.getAcademicSupportOpt().equalsIgnoreCase(gencod.getCode())){
					fiConcession.setAcademicSupportDesc(gencod.getEngDescription());
				}
			}
		}
		//select all records previously selected
		int arrayLength =0;
		if (fiStudentForm.getIndexNrSelectedAuth()!=null){
			arrayLength = fiStudentForm.getIndexNrSelectedAuth().length;
		}
		String[] array = new String[arrayLength];		
		int index=0;
		if (fiStudentForm.getIndexNrSelectedAuth()!=null && fiStudentForm.getIndexNrSelectedAuth().length!=0){
			for (int i=0; i < fiStudentForm.getIndexNrSelectedAuth().length; i++){
				AuthRequestRecord oldRec = new AuthRequestRecord();
				oldRec = (AuthRequestRecord)fiStudentForm.getAuthRequestList().get(Integer.parseInt(fiStudentForm.getIndexNrSelectedAuth()[i]));
				for (int j=0; j < requestList.size(); j++){
					AuthRequestRecord authRec = new AuthRequestRecord();
					authRec = (AuthRequestRecord)requestList.get(j);
					if (oldRec.getStudyUnit().equalsIgnoreCase(authRec.getStudyUnit())&&
							oldRec.getStudentNumber().compareTo(authRec.getStudentNumber())==0 ){
						array[index]=String.valueOf(j);
						index=index+1;
						j=requestList.size();
					}
				}
			}
		}		
		
		fiStudentForm.setIndexNrSelectedAuth(array);
		fiStudentForm.setAuthRequestList(requestList);
//		String[] array = new String[0];
//		int index=0;
//		if(fiStudentForm.getAuthErrorList()!=null && fiStudentForm.getAuthRequestList()!=null){
//			if(fiStudentForm.getAuthErrorList().size()>0 && fiStudentForm.getAuthRequestList().size()>0){
//				for (int i=0; i < fiStudentForm.getAuthErrorList().size(); i++)
//				{
//					AuthRequestRecord errRec = new AuthRequestRecord();
//					errRec = (AuthRequestRecord)fiStudentForm.getAuthErrorList().get(i);
//					for (int j=0; j < fiStudentForm.getAuthRequestList().size(); j++){
//						AuthRequestRecord authRec = new AuthRequestRecord();
//						authRec = (AuthRequestRecord)fiStudentForm.getAuthRequestList().get(j);
//						if (errRec.getStudentNumber()==authRec.getStudentNumber() &&
//								errRec.getStudyUnit().equalsIgnoreCase(authRec.getStudyUnit())){
//							//tick error records on list
//							index=index+1;
//							array[index]=String.valueOf(j);
//							j=fiStudentForm.getAuthRequestList().size();
//						}
//					}
//				}
//				fiStudentForm.setIndexNrSelectedAuth(array);
//			}			
//		}		
		//List errorList = new ArrayList();
		//fiStudentForm.setAuthErrorList(null);
		fiStudentForm.setCurrentPage("authRequestList");
		return "authRequestList";
	}
	
	public String getDeanAuthRequestList(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		FinalMarkConcessionForm fiStudentForm = (FinalMarkConcessionForm ) form;
		ActionMessages messages = new ActionMessages();	
		
		AuthorisationDAO authDao = new AuthorisationDAO();
		List deptList = new ArrayList();
		List directorDeptList = new ArrayList();
				
		deptList = authDao.getDeanDeptList(Integer.parseInt(fiStudentForm.getMyUnisaUser().getPersonnelNumber()));
		directorDeptList = authDao.getDirectorDeptList(Integer.parseInt(fiStudentForm.getMyUnisaUser().getPersonnelNumber()));
		
		if (deptList.size()==0 && directorDeptList.size()==0){
			//user not Dean
			//user does not have access to this function 
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"You do not have the required access to authorise student concessions as a Dean/Director."));
				addErrors(request,messages);
				return "cancel";			
		}
		
		boolean found = false;
		for (int i=0; i < directorDeptList.size(); i++){
			found=false; 
			for(int j=0; j < deptList.size(); j++){
				if (((LabelValueBean)directorDeptList.get(i)).getValue().equalsIgnoreCase(((LabelValueBean)deptList.get(j)).getValue())){
					found=true;
				}
			}
			if (!found){
				deptList.add(directorDeptList.get(i));
			}
		}
		
		deptList.add(0,new LabelValueBean("ALL","0"));
		fiStudentForm.setListAuthDepartments(deptList);		
				
		List requestList = new ArrayList();
		if (fiStudentForm.getAuthListCriteriaDepartment()==0){
			for (int i=0; i < deptList.size(); i++){
				List tempList = new ArrayList();
				if (Short.parseShort(((LabelValueBean)deptList.get(i)).getValue())!=0){
					tempList = authDao.getAuthoriseRequestList(Short.parseShort(((LabelValueBean)deptList.get(i)).getValue()), fiStudentForm.getAuthorisationType(), Short.parseShort(fiStudentForm.getAuthListCriteriaExamYear()),fiStudentForm.getAuthListCriteriaExamPeriod());
					requestList.addAll(tempList);
				}				
			}
		}else{
			List tempList = new ArrayList();
			tempList = authDao.getAuthoriseRequestList(fiStudentForm.getAuthListCriteriaDepartment(), fiStudentForm.getAuthorisationType(), Short.parseShort(fiStudentForm.getAuthListCriteriaExamYear()),fiStudentForm.getAuthListCriteriaExamPeriod());
			requestList.addAll(tempList);
		}
		
		for (int i=0; i < requestList.size(); i++){
			AlternativeExamOpportunityRecord fiConcession = new AlternativeExamOpportunityRecord();
			fiConcession = ((AuthRequestRecord)requestList.get(i)).getFiConcession();
			for (int j=0; j < fiStudentForm.getListAlternativeAssess().size(); j++){
				Gencod gencod = new Gencod();
				gencod = (Gencod)fiStudentForm.getListAlternativeAssess().get(j);
				if (fiConcession.getAlternativeAssessOpt().equalsIgnoreCase(gencod.getCode())){
					fiConcession.setAlternativeAssessOptDesc(gencod.getEngDescription());
				}
			}
			for (int j=0; j < fiStudentForm.getListAcademicSupport().size(); j++){
				Gencod gencod = new Gencod();
				gencod = (Gencod)fiStudentForm.getListAcademicSupport().get(j);
				if (fiConcession.getAcademicSupportOpt().equalsIgnoreCase(gencod.getCode())){
					fiConcession.setAcademicSupportDesc(gencod.getEngDescription());
				}
			}
		}
		
		//select all records previously selected
		int arrayLength =0;
		if (fiStudentForm.getIndexNrSelectedAuth()!=null){
			arrayLength = fiStudentForm.getIndexNrSelectedAuth().length;
		}
		String[] array = new String[arrayLength];		
		int index=0;
		if (fiStudentForm.getIndexNrSelectedAuth()!=null && fiStudentForm.getIndexNrSelectedAuth().length!=0){
			for (int i=0; i < fiStudentForm.getIndexNrSelectedAuth().length; i++){
				AuthRequestRecord oldRec = new AuthRequestRecord();
				oldRec = (AuthRequestRecord)fiStudentForm.getAuthRequestList().get(Integer.parseInt(fiStudentForm.getIndexNrSelectedAuth()[i]));
				for (int j=0; j < requestList.size(); j++){
					AuthRequestRecord authRec = new AuthRequestRecord();
					authRec = (AuthRequestRecord)requestList.get(j);
					if (oldRec.getStudyUnit().equalsIgnoreCase(authRec.getStudyUnit())&&
							oldRec.getStudentNumber().compareTo(authRec.getStudentNumber())==0 ){
						array[index]=String.valueOf(j);
						index=index+1;
						j=requestList.size();
					}
				}
			}
		}		
		
		fiStudentForm.setIndexNrSelectedAuth(array);		
		fiStudentForm.setAuthRequestList(requestList);
		
		fiStudentForm.setCurrentPage("authRequestList");
		return "authRequestList";
	}
	
	public void sendNotifyResponseEmail(String toAddress, String addressee, String concession, String comment, String response) {

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
			String subject = "Final Year Student Concession - " + concession + "-" + response;
			String body = "<html> "+
		    "<body> "+ addressee + "<br/><br/>"+
			"NB: This is an automated response - do not reply to this e-mail.  <br/><br/> "+
			"The Final Year Student Concession for " + concession + " was " + response + ".";
			
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
		 	log.error("FI concession/AuthorisationnAction: send of email authorisation request notification failed "+e+" "+e.getMessage());
			e.printStackTrace();
		 }
	}
	public void sendNotifyRequestEmail(String toAddress, String addressee) {

		try {

			String tmpEmailFrom = ServerConfigurationService.getString("noReplyEmailFrom");
			//String serverPath = ServerConfigurationService.getToolUrl();
			InternetAddress toEmail = new InternetAddress(toAddress);
			InternetAddress iaTo[] = new InternetAddress[1];
			iaTo[0] = toEmail;
			InternetAddress iaHeaderTo[] = new InternetAddress[1];
			iaHeaderTo[0] = toEmail;
			InternetAddress iaReplyTo[] = new InternetAddress[1];
			iaReplyTo[0] = new InternetAddress(tmpEmailFrom);

			/* Setup path to myUnisa */
			String myUnisaPath = ServerConfigurationService.getServerUrl();

			/* send activation email to student */
			String subject = "Final Year Student Concession - Authorisation";
			String body = "<html> "+
		    "<body> "+
				"Dear " + addressee + ",  <br/><br/>"+
			"NB: This is an automated response - do not reply to this e-mail.  <br/><br/> "+
			"You are receiving this e-mail in response to a request to authorise final year student concession records.<br/>"+
			"To authorise the final year student concession records log onto myUnisa. <br/>" +
			"Select the Course Admin site from the orange navigation bar at the top. <br/>"+
			"Select FI Concession in the left navigation column to access the FI Concession tool. <br/>" +
			"Click on the Dean/Director Authorisation button at the bottom of the page. <A href='"+myUnisaPath+"' target='_blank'><b>myUnisa</b></A>"+
			//"<A href='"+serverPath+"' target='_blank'><b>myUnisa</b></A>"+
			"</body>"+
			"</html>";

		  List contentList = new ArrayList();
		  contentList.add("Content-Type: text/html");

		  emailService = (EmailService)ComponentManager.get(EmailService.class);
		  
		  emailService.sendMail(iaReplyTo[0],iaTo,subject,body,iaHeaderTo,iaReplyTo,contentList);
		 log.info("sending mail from "+iaReplyTo[0]+" to "+toEmail.toString());
		 } catch (Exception e) {
		 	log.error("FI Concession: send of email COD authorisation request notification failed "+e+" "+e.getMessage());
			e.printStackTrace();
		 }
	}
}
