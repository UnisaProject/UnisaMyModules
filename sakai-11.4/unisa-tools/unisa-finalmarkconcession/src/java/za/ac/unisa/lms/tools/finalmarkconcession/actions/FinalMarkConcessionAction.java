package za.ac.unisa.lms.tools.finalmarkconcession.actions;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.InternetAddress;
import javax.management.StringValueExp;
import javax.servlet.ServletOutputStream;
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
import org.sakaiproject.email.api.EmailService;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserDirectoryService;

import za.ac.unisa.lms.dao.Gencod;
import za.ac.unisa.lms.dao.StudentSystemGeneralDAO;
import za.ac.unisa.lms.dao.Xamprd;
import za.ac.unisa.lms.dao.general.DepartmentDAO;
import za.ac.unisa.lms.dao.general.PersonnelDAO;
import za.ac.unisa.lms.dao.general.UserDAO;
import za.ac.unisa.lms.domain.general.Department;
import za.ac.unisa.lms.domain.general.Person;
import za.ac.unisa.lms.domain.registration.Qualification;
import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.tools.finalmarkconcession.Constants;
import za.ac.unisa.lms.tools.finalmarkconcession.forms.*;
import za.ac.unisa.lms.tools.finalmarkconcession.dao.*;
import za.ac.unisa.utils.CoursePeriodLookup;
import Exfyq01h.Abean.Exfyq01sMntFiYearStudConc;
import Exfyq10h.Abean.Exfyq10sListFiYearStud;

public class FinalMarkConcessionAction extends LookupDispatchAction{
	
	private EmailService emailService;
	private SessionManager sessionManager;
	private UserDirectoryService userDirectoryService;
	
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
		map.put("editConcessionForm","editConcessionForm");
		map.put("viewConcessionForm","viewConcessionForm");
		map.put("requestAuthorisation","requestAuthorisation");
		map.put("button.withoutSaveRequestAuth","requestAuthorisationWithOutSaving");
		map.put("button.requestAuthorisation","requestAuthorisation");
		map.put("button.requestBatchAuth", "requestBatchAuth");
		map.put("button.display", "displayFirst");
		map.put("button.calculateFinalMark", "calculateFinalMark");
		map.put("saveConcessionForm", "saveConcessionForm");
		map.put("button.back", "prevStep");
		map.put("button.previous","displayPrev");
		map.put("button.first", "displayFirst");
		map.put("button.next", "displayNext");
		map.put("button.cancel", "cancel");
		map.put("submitAuthRequest", "submitAuthRequest");
		map.put("submitBatchAuthRequest", "submitBatchAuthRequest");
		map.put("button.cancelRequest", "displayCancelRequest");
		map.put("displayCancelRequest","displayCancelRequest");
		map.put("cancelRequest", "cancelRequest");
		map.put("button.codAuth", "codAuthorisation");
		map.put("button.deanAuth", "deanAuthorisation");
		map.put("button.no", "prevStep");
		map.put("extractFile", "extractFile");
		map.put("button.continue","nextStep");
		map.put("nextStep", "nextStep");
		map.put("submitGroupAssignment","submitGroupAssignment");
		map.put("displayFiAssignmentStudentMarks","displayFiAssignmentStudentMarks");
		map.put("uploadMarks", "uploadMarks");
		map.put("button.selectAll", "selectAll");
		map.put("button.deSelectAll", "deSelectAll");
		
		return map;
	}
	
	public ActionForward uploadMarks(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		FinalMarkConcessionForm fiStudentForm = (FinalMarkConcessionForm ) form;
		ActionMessages messages = new ActionMessages();
		
		FinalMarkConcessionDAO dao = new FinalMarkConcessionDAO();
		String newStatus="FORMSAVED";
		int errorCount = 0;
		int uploadCount=0;
		
		for (int i=0; i < fiStudentForm.getListBatchVerifiedRecords().size();i++){
			BatchUploadRecord record = new BatchUploadRecord();
			record = (BatchUploadRecord)fiStudentForm.getListBatchVerifiedRecords().get(i);				
			
			if (record.isUpload()){
				if (record.getAltExamOpt().getFinalMark().equalsIgnoreCase("0")) {
					record.getAltExamOpt().setZeroMarkReason("ME"); //True mark earned
				}else{
					record.getAltExamOpt().setZeroMarkReason("N/A"); //Not applicable
				}
				StringBuilder comment = new StringBuilder("batch import, GradeBook Mark: ");
				
				comment.append(record.getGradebookPercentage());
				comment.append(", Year Mark: ");
				comment.append(record.getResult().getYearMark());
				comment.append(", Revised Final Mark: ");
				comment.append(record.getAltExamOpt().getFinalMark());
				
				String logComment = "";
				logComment = comment.toString();
				
				try{
					AlternativeExamOpportunityRecord currentExamAltOpt = new AlternativeExamOpportunityRecord();
					currentExamAltOpt= dao.getAlternativeAssessmentRecord(Short.parseShort(fiStudentForm.getExamYear()), 
							fiStudentForm.getExamPeriod(), 
							fiStudentForm.getStudyUnitSearchCriteria(), 
							record.getStudentNumber());
					
					if (!currentExamAltOpt.getStatus().equalsIgnoreCase("FORMSAVED")) {
						record.setStatus("Data changed: Invalid Status");
						record.setAltExamOpt(currentExamAltOpt);
						record.setUpload(false);
						continue;
					}
					if (isMarked(currentExamAltOpt)){						
						record.setStatus("Data changed: Already Marked");
						record.setUpload(false);
						record.setAltExamOpt(currentExamAltOpt);
						continue;
					}	
					dao.updateAlternativeAssessmentRecordFromGradeBook(Short.parseShort(fiStudentForm.getExamYear()), 
							fiStudentForm.getExamPeriod(), 
							fiStudentForm.getStudyUnitSearchCriteria(), 
							record.getStudentNumber(),
							record.getResult().getFinalMark(),
							record.getResult().getResultCode(), 
							record.getAltExamOpt(), 
							newStatus, 
							fiStudentForm.getNovellUserid(), 
							logComment, 
							"");
					record.setStatus("Mark Imported");
					uploadCount = uploadCount + 1;
					record.setUpload(false);
				}  catch (Exception e) {
					record.setStatus("Error Importing Mark");
				 	log.error("FI Concession import error, Student nr: " + record.getStudentNumber() + "," + e );
				 	errorCount = errorCount + 1;
				 	}			
				
			}			
		}
		
		fiStudentForm.setTotalRecords(0);
		fiStudentForm.setTotalStatusRecords(0);
		
		messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage",
							uploadCount + " Records updated successfully"));
		addMessages(request, messages);		
		fiStudentForm.setCurrentPage("batchMarksUpload");
		return mapping.findForward("batchMarksUpload");		
		
	}
	public ActionForward submitGroupAssignment(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
			FinalMarkConcessionForm fiStudentForm = (FinalMarkConcessionForm ) form;
			ActionMessages messages = new ActionMessages();
					
			AlternativeExamOpportunityRecord altExamOpt = new AlternativeExamOpportunityRecord();			
			altExamOpt = fiStudentForm.getBatchAlternativeAssessmentOpportunity() ;
			String newStatus = "FORMSAVED";	
			
			if (altExamOpt.getAlternativeAssessOpt().equalsIgnoreCase("OT")){
				if (altExamOpt.getAlternativeAssessOtherDesc()==null || 
						altExamOpt.getAlternativeAssessOtherDesc().trim().equalsIgnoreCase("")){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"Please describe 'OTHER' Alternative assessment option"));
				}else if (altExamOpt.getAlternativeAssessOtherDesc().trim().length()>250){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"Description for Alternative assessment option 'OTHER', may not exceed 250 characters."));
				}else{
					altExamOpt.setAlternativeAssessOtherDesc(altExamOpt.getAlternativeAssessOtherDesc().trim());
				}
			}else{
				if (altExamOpt.getAlternativeAssessOtherDesc()!=null && 
						!altExamOpt.getAlternativeAssessOtherDesc().trim().equalsIgnoreCase("")){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"Alternative assessment option, Describe other must be empty if 'OTHER' not selected"));
				}
			}
			
			if (altExamOpt.getAcademicSupportOpt().equalsIgnoreCase("OT")){
				if (altExamOpt.getAcademicSupportOtherDesc()==null || 
						altExamOpt.getAcademicSupportOtherDesc().trim().equalsIgnoreCase("")){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"Please describe 'OTHER' Academic support to be provided"));
				}else if (altExamOpt.getAcademicSupportOtherDesc().trim().length()>250){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"Description for Academic support to be provided 'OTHER', may not exceed 250 characters."));
				}else{
					altExamOpt.setAcademicSupportOtherDesc(altExamOpt.getAcademicSupportOtherDesc().trim());
				}
			}else{
				if (altExamOpt.getAcademicSupportOtherDesc()!=null && 
						!altExamOpt.getAcademicSupportOtherDesc().trim().equalsIgnoreCase("")){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"Academic support to be provided, Describe other must be empty if 'OTHER' not selected"));
				}
			}	
			
			if (!messages.isEmpty()) {
				addErrors(request,messages);
				fiStudentForm.setCurrentPage("initialGroupAssignment");
				return mapping.findForward("initialGroupAssignment");			
			}	
			
			String studyUnit=fiStudentForm.getStudyUnitSearchCriteria();
			Short examYear=Short.parseShort(fiStudentForm.getExamYear());
			Short examPeriod= fiStudentForm.getExamPeriod();
			
			Gencod gencod = new Gencod();
			StudentSystemGeneralDAO gendao = new StudentSystemGeneralDAO();
			if (altExamOpt.getAcademicSupportOpt() == null || altExamOpt.getAcademicSupportOpt().equalsIgnoreCase("")){
				altExamOpt.setAcademicSupportDesc("");
			}else{
				gencod = (Gencod)(gendao.getGenCode("92", altExamOpt.getAcademicSupportOpt()));
				altExamOpt.setAcademicSupportDesc(gencod.getEngDescription());					
			}		
			
			gencod = new Gencod();
			if (altExamOpt.getAlternativeAssessOpt() == null || altExamOpt.getAlternativeAssessOpt().equalsIgnoreCase("")){
				altExamOpt.setAlternativeAssessOptDesc("");
			}else{
				gencod = (Gencod)(gendao.getGenCode("91", altExamOpt.getAlternativeAssessOpt()));
				altExamOpt.setAlternativeAssessOptDesc(gencod.getEngDescription());					
			}	
			
			StringBuilder comment = new StringBuilder("Group assignment - ");
			String logComment = "";
			if (altExamOpt.getAlternativeAssessOpt()!=null && !altExamOpt.getAlternativeAssessOpt().equalsIgnoreCase("")){
				comment.append("Access option: ");
				comment.append(altExamOpt.getAlternativeAssessOptDesc());
			}
			if (altExamOpt.getAcademicSupportOpt()!=null && !altExamOpt.getAcademicSupportOpt().equalsIgnoreCase("")){
				comment.append(", Support option: ");
				comment.append(altExamOpt.getAcademicSupportDesc());
			}
			logComment = comment.toString();
			
			FinalMarkConcessionDAO dao = new FinalMarkConcessionDAO();			
			dao.groupAlternativeAssessmentAssignment (examYear, examPeriod, studyUnit, altExamOpt, newStatus, fiStudentForm.getNovellUserid(), logComment, "");
			
			int totalRecords = dao.getNumberOfFiConcessionRecords(Short.parseShort(fiStudentForm.getExamYear()), fiStudentForm.getExamPeriod(), fiStudentForm.getStudyUnitSearchCriteria(), "All");
			int totalStatusRecords = dao.getNumberOfFiConcessionRecords(Short.parseShort(fiStudentForm.getExamYear()), fiStudentForm.getExamPeriod(), fiStudentForm.getStudyUnitSearchCriteria(), "IDENTIFIED");
			fiStudentForm.setTotalRecords(totalRecords);
			fiStudentForm.setTotalStatusRecords(totalStatusRecords);
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"Concession records successfully updated"));
			addMessages(request, messages);
			
			fiStudentForm.setCurrentPage("initialGroupAssignment");
			return mapping.findForward("initialGroupAssignment");
			
	}		
	
	public ActionForward nextStep(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
			FinalMarkConcessionForm fiStudentForm = (FinalMarkConcessionForm ) form;
			
			String nextPage="input";	
			fiStudentForm.setListAccess("view");
			if("input".equalsIgnoreCase(fiStudentForm.getCurrentPage())){	
				if (isInputActionOk(mapping, form, request, response)==false){
					nextPage="input";
				}					
				else {
					if (fiStudentForm.getSelectedAction().equalsIgnoreCase("VIEWLIST")){						
						nextPage = displayData(mapping, form, request, response, "FIRST");
					}
					if (fiStudentForm.getSelectedAction().equalsIgnoreCase("EDITINIT")){
						AlternativeExamOpportunityRecord altExamOpt = new AlternativeExamOpportunityRecord();
						Person	responsibleLecturer = new Person();
						altExamOpt.setResponsibleLecturer(responsibleLecturer);
						fiStudentForm.setBatchAlternativeAssessmentOpportunity(altExamOpt);
						FinalMarkConcessionDAO dao = new FinalMarkConcessionDAO();
						int totalRecords = dao.getNumberOfFiConcessionRecords(Short.parseShort(fiStudentForm.getExamYear()), fiStudentForm.getExamPeriod(), fiStudentForm.getStudyUnitSearchCriteria(), "All");
						int totalStatusRecords = dao.getNumberOfFiConcessionRecords(Short.parseShort(fiStudentForm.getExamYear()), fiStudentForm.getExamPeriod(), fiStudentForm.getStudyUnitSearchCriteria(), "IDENTIFIED");
						fiStudentForm.setTotalRecords(totalRecords);
						fiStudentForm.setTotalStatusRecords(totalStatusRecords);						
						nextPage = "initialGroupAssignment";
					}
					if (fiStudentForm.getSelectedAction().equalsIgnoreCase("EDITUPLMRK")){
						nextPage=listFiSiteAssignments(mapping, fiStudentForm, request, response);
					}
					if (fiStudentForm.getSelectedAction().equalsIgnoreCase("EDITREQAUTH")){					
						nextPage = listFiRecordsValidForAuthReq(mapping, fiStudentForm, request, response);
					}
					if (fiStudentForm.getSelectedAction().equalsIgnoreCase("EDITLIST")){
						fiStudentForm.setSearchCriteria("S");
						fiStudentForm.setListAccess("edit");
						nextPage=displayData(mapping, form, request, response, "FIRST");
					}
					if (fiStudentForm.getSelectedAction().equalsIgnoreCase("AUTHCOD")){
						nextPage="codAuthorisation";
					}
					if (fiStudentForm.getSelectedAction().equalsIgnoreCase("AUTHDN")){
						nextPage="deanAuthorisation";
					}
				}
			}
			if (fiStudentForm.getCurrentPage().equalsIgnoreCase("fiAssignmentSelection")){
				nextPage=getFiAssignmentStudentMarks(mapping, fiStudentForm, request, response);
			}
			fiStudentForm.setCurrentPage(nextPage);
			return mapping.findForward(nextPage);
			
	}
	
	private Boolean isInputActionOk(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		FinalMarkConcessionForm fiStudentForm = (FinalMarkConcessionForm ) form;
		ActionMessages messages = new ActionMessages();
		resetMainForm(fiStudentForm);
		
		if (fiStudentForm.getExamYear()==null || fiStudentForm.getExamYear().trim().equalsIgnoreCase("")){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"Examination Year is required."));
		} else {
			if (!isInteger(fiStudentForm.getExamYear())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"Examination Year must be numeric."));
			}
		}
		if (fiStudentForm.getSelectedAction()==null || fiStudentForm.getSelectedAction().trim().equalsIgnoreCase("")){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"Please select an action before clicking on continue."));
		}
		if (!messages.isEmpty()) {
			addErrors(request,messages);
			return false;
		}		
	
		
		if (fiStudentForm.getSelectedAction().equalsIgnoreCase("EDITLIST") || 
				fiStudentForm.getSelectedAction().equalsIgnoreCase("EDITINIT") ||
				fiStudentForm.getSelectedAction().equalsIgnoreCase("EDITUPLMRK") ||
				fiStudentForm.getSelectedAction().equalsIgnoreCase("EDITREQAUTH")){
			if (fiStudentForm.getStudyUnitSearchCriteria()==null || fiStudentForm.getStudyUnitSearchCriteria().trim().equalsIgnoreCase("")){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"Study unit is required for this action."));
			} else {
				FinalMarkConcessionDAO dao = new FinalMarkConcessionDAO();
				String dptCode="";
				dptCode = dao.getStudyUnitDpt(fiStudentForm.getStudyUnitSearchCriteria().toUpperCase());
				if ("".equalsIgnoreCase(dptCode) ||	dptCode==null){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"Study Unit not found"));
				} else {
					//Check if user have access to edit					
					boolean lecAuthorised = false;
					List<Person> lecturerList = new ArrayList<Person>();
					lecturerList = dao.getResponsibleLecturerList(fiStudentForm.getStudyUnitSearchCriteria().toUpperCase(),
							          fiStudentForm.getExamPeriod(), Short.parseShort(fiStudentForm.getExamYear()),fiStudentForm.getNovellUserid().trim());	
					fiStudentForm.setListLecturer(lecturerList);
					for (int j=0; j < lecturerList.size();j++){
						Person lecturer = new Person();
						lecturer = (Person) lecturerList.get(j);
						if (fiStudentForm.getNovellUserid().equalsIgnoreCase(lecturer.getNovellUserId())){
							lecAuthorised=true;
						}
					}
					if (!lecAuthorised) {
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage",
											"You are not authorised to edit Student Concessions for this module."));
					}
				}
			}			
		}
		
		
		if (!messages.isEmpty()) {
			addErrors(request,messages);
			return false;
		}
		
		fiStudentForm.setRemoveAssessOptOther(true);
		
		if (Integer.parseInt(fiStudentForm.getExamYear())<2016){
			fiStudentForm.setRemoveAssessOptOther(false);
		}
		Short octPeriod = new Short("10");
		if (Integer.parseInt(fiStudentForm.getExamYear())==2016 && fiStudentForm.getExamPeriod().compareTo(octPeriod)!=0){
			fiStudentForm.setRemoveAssessOptOther(false);
		}
		
		List<Gencod> list = new ArrayList<Gencod>();
		StudentSystemGeneralDAO dao = new StudentSystemGeneralDAO();
		
		list=dao.getGenCodes((short)91,3);
		if (fiStudentForm.isRemoveAssessOptOther()){
			List<Gencod> listAlternativeAssess = new ArrayList<Gencod>();
			for (int i=0; i < list.size(); i++) {
			      Gencod  gencod=(Gencod)(list.get(i));
		          if(!gencod.getCode().equalsIgnoreCase("OT")){		        	
		        	listAlternativeAssess.add(gencod);		        	
		          }
			}
			fiStudentForm.setListAlternativeAssess(listAlternativeAssess);
		}else{
			fiStudentForm.setListAlternativeAssess(list);
		}	
		
		for (int i=0; i < fiStudentForm.getExamPeriodCodes().size(); i++) {
			Xamprd examPeriod = (Xamprd)fiStudentForm.getExamPeriodCodes().get(i);
				if (examPeriod.getCode().compareTo(fiStudentForm.getExamPeriod())==0){
					fiStudentForm.setExamPeriodDesc(examPeriod.getEngDescription());
					i=fiStudentForm.getExamPeriodCodes().size(); 
				}			
		}
				
		return true;		
	}
	
	private Boolean isInputViewFiStudentListOk(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		FinalMarkConcessionForm fiStudentForm = (FinalMarkConcessionForm ) form;
		ActionMessages messages = new ActionMessages();
		
		FinalMarkConcessionDAO dao = new FinalMarkConcessionDAO();	
		List<DepartmentRecord> listDepartments = dao.getDepartmentList(fiStudentForm.getUserCollege().getCode());
		request.setAttribute("listDepartments", listDepartments);
		
		//do validation		
		if (fiStudentForm.getExamYear()==null || fiStudentForm.getExamYear().trim().equalsIgnoreCase("")){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"Examination Year is required"));
		} else {
			if (!isInteger(fiStudentForm.getExamYear())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"Examination Year must be numeric"));
			}
		}	
		if (!messages.isEmpty()) {
			addErrors(request,messages);
			return false;
		}
		if (fiStudentForm.getSearchCriteria().equalsIgnoreCase("S")){			
			if (fiStudentForm.getStudyUnitSearchCriteria()==null || fiStudentForm.getStudyUnitSearchCriteria().equalsIgnoreCase("")){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"Study Unit is required"));
			}else{
				String dptCode="";
				dptCode = dao.getStudyUnitDpt(fiStudentForm.getStudyUnitSearchCriteria().toUpperCase());
				if ("".equalsIgnoreCase(dptCode) ||	dptCode==null){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"Study Unit not found"));
				}else{
					DepartmentRecord dpt = new DepartmentRecord();
					dpt = dao.getDepartment(Short.parseShort(dptCode));
					//change start give access if not linked to college but primary or secondary lecturer
					boolean lecAuthorised = false;
					List<Person> lecturerList = new ArrayList<Person>();
					lecturerList = dao.getResponsibleLecturerList(fiStudentForm.getStudyUnitSearchCriteria().toUpperCase(),
							          fiStudentForm.getExamPeriod(), Short.parseShort(fiStudentForm.getExamYear()),fiStudentForm.getNovellUserid().trim());	
					for (int j=0; j < lecturerList.size();j++){
						Person lecturer = new Person();
						lecturer = (Person) lecturerList.get(j);
						if (fiStudentForm.getNovellUserid().equalsIgnoreCase(lecturer.getNovellUserId())){
							lecAuthorised=true;
						}
					}
					if (lecAuthorised){
						//Do nothing have authorisation to study unit
					} else if (fiStudentForm.getUserDepartment().getCollegeCode().compareTo(dpt.getCollegeCode())!=0){
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage",
											"You are not allowed to view data for this Study Unit, Study unit is not linked to your College."));						
					}	
					
				}		
			}
			
		}
		if (!messages.isEmpty()) {
			addErrors(request,messages);
			return false;
		}
		
		return true;
	}
	
	private void resetMainForm(FinalMarkConcessionForm fiStudentForm)
			throws Exception {	
				
				fiStudentForm.setFiStudents(null);	
				fiStudentForm.setAuthRequestList(null);
				fiStudentForm.setAuthComment("");
				fiStudentForm.setListBatchCodAuthRecords(null);
				fiStudentForm.setListBatchVerifiedRecords(null);
				fiStudentForm.setListFiSites(null);
				fiStudentForm.setListFiAssignments(null);
				String[] array = new String[0];
				fiStudentForm.setIndexNrSelectedAuth(array);
		    } 
	
	public String listFiRecordsValidForAuthReq(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		FinalMarkConcessionForm fiStudentForm = (FinalMarkConcessionForm ) form;
		
		List<BatchCodAuthRecord> listFiRecords = new ArrayList<BatchCodAuthRecord>();
		
		FinalMarkConcessionDAO dao = new FinalMarkConcessionDAO();
		listFiRecords = dao.getFiRecordsValidForAuthReq(Short.parseShort(fiStudentForm.getExamYear()),fiStudentForm.getExamPeriod(),fiStudentForm.getStudyUnitSearchCriteria());
		
		fiStudentForm.setListBatchCodAuthRecords(listFiRecords);
		String[] array = new String[0];
		fiStudentForm.setIndexNrSelectedAuth(array);
		
		return "batchCodAuthReq";		
	}
	
	public ActionForward selectAll(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
			FinalMarkConcessionForm fiStudentForm = (FinalMarkConcessionForm ) form;				
			String[] array = new String[fiStudentForm.getListBatchCodAuthRecords().size()];
			
			if (fiStudentForm.getListBatchCodAuthRecords()!=null){
				fiStudentForm.setIndexNrSelectedAuth(new String[fiStudentForm.getListBatchCodAuthRecords().size()]);
				for (int i=0; i < fiStudentForm.getListBatchCodAuthRecords().size();i++){
					array[i]= String.valueOf(i);
				}
			}				
			
			fiStudentForm.setIndexNrSelectedAuth(array);		
		   
			fiStudentForm.setCurrentPage("batchCodAuthReq");
		    return mapping.findForward("batchCodAuthReq");
	}
	
	public ActionForward deSelectAll(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
			FinalMarkConcessionForm fiStudentForm = (FinalMarkConcessionForm ) form;				
			
			String[] array = new String[0];
			fiStudentForm.setIndexNrSelectedAuth(array);		
		   
			fiStudentForm.setCurrentPage("batchCodAuthReq");
		    return mapping.findForward("batchCodAuthReq");
	}
	
	public String listFiSiteAssignments(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		FinalMarkConcessionForm fiStudentForm = (FinalMarkConcessionForm ) form;
		
		List<GradeBookObjectRecord> listFiAssignments = new ArrayList<GradeBookObjectRecord>();
		
		FinalMarkConcessionSakaiDAO dao = new FinalMarkConcessionSakaiDAO();
//		String siteId = fiStudentForm.getStudyUnitSearchCriteria() + "-" + fiStudentForm.getExamYear() + "-" + fiStudentForm.getExamPeriod();
//		siteId = "Sonette_10_5";
		
		StringBuffer id = new StringBuffer(fiStudentForm.getStudyUnitSearchCriteria());
		id.append("-");
		id.append(fiStudentForm.getExamYear());
		id.append("-");
		
		if (fiStudentForm.getExamPeriod().compareTo(Short.parseShort("10")) < 0){
			id.append("0");
			id.append(fiStudentForm.getExamPeriod());
		}else{
			id.append(fiStudentForm.getExamPeriod());
		}		
		
		String siteId=id.toString();
				
		listFiAssignments = dao.getSiteAssessments(siteId);	
		fiStudentForm.setListFiAssignments(listFiAssignments);
		
		return "fiAssignmentSelection";		
	}
	
	public String getFiAssignmentStudentMarks(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		FinalMarkConcessionForm fiStudentForm = (FinalMarkConcessionForm ) form;
		ActionMessages messages = new ActionMessages();
		
		if (fiStudentForm.getSelectedGBObject()==0) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"Please select a Gradebook item before clicking on continue."));
			addErrors(request,messages);
			return "fiAssignmentSelection";
		}
		
		List<GradeBookGradeRecord> listStudentFiAssignmentsMarks = new ArrayList<GradeBookGradeRecord>();
		List<BatchUploadRecord> listUploadRecords = new ArrayList<BatchUploadRecord>();
		int selectedObjectId = fiStudentForm.getSelectedGBObject();
		fiStudentForm.setTotalRecords(0); 		 //number of gradebook records	
		fiStudentForm.setTotalStatusRecords(0);  //records ready to be updated
		
		FinalMarkConcessionSakaiDAO dao = new FinalMarkConcessionSakaiDAO();	
		FinalMarkConcessionDAO stuDao = new FinalMarkConcessionDAO();
		listStudentFiAssignmentsMarks  = dao.getStudentAssessmentMarks(selectedObjectId);	
		fiStudentForm.setTotalRecords(listStudentFiAssignmentsMarks.size());
		//verify upload record
		for (int i=0; i<listStudentFiAssignmentsMarks.size();i++){
			//Get student name;
			GradeBookGradeRecord record = new GradeBookGradeRecord();
			BatchUploadRecord verifiedRecord = new BatchUploadRecord();				
			record = (GradeBookGradeRecord) listStudentFiAssignmentsMarks.get(i);
			String studentName = stuDao.getStudent(record.getStudentNumber());
			verifiedRecord.setUpload(false);
			verifiedRecord.setStudentNumber(record.getStudentNumber());
			verifiedRecord.setGradebookPercentage(record.getMarkPercentage());			
			if (studentName==null || studentName.equalsIgnoreCase("")){
				verifiedRecord.setStudentName("");
				verifiedRecord.setStatus("Student not found");			
			} else {
				verifiedRecord.setStudentName(studentName);
				//Get student Academic results and FI concession details
				verifiedRecord = stuDao.getStudentFiDetails(Short.parseShort(fiStudentForm.getExamYear()),fiStudentForm.getExamPeriod(),fiStudentForm.getStudyUnitSearchCriteria(),verifiedRecord);
				if (verifiedRecord.getAltExamOpt()==null){
					verifiedRecord.setStatus("No Concession Record");
				}
			}
			
			if (verifiedRecord.getStatus()!=null && !verifiedRecord.getStatus().equalsIgnoreCase("")){
				listUploadRecords.add(verifiedRecord);
				continue;
			}else{
				//get year mark weight
				String yearMarkWeight ="";
				yearMarkWeight = stuDao.getYearMarkWeight(verifiedRecord.getResult().getAcademicYear(), verifiedRecord.getResult().getSemester(), fiStudentForm.getStudyUnitSearchCriteria());
				
				//validate
				if (!verifiedRecord.getAltExamOpt().getStatus().equalsIgnoreCase("FORMSAVED")){
					verifiedRecord.setStatus("Invalid Concession Status");
					listUploadRecords.add(verifiedRecord);
					continue;
				}
				if (isMarked(verifiedRecord.getAltExamOpt())){
					verifiedRecord.setStatus("Already Marked");
					listUploadRecords.add(verifiedRecord);
					continue;
				}
				//calculate revised mark
				int revisedFinalMark = 0;	
				revisedFinalMark = getCalcFinalMark(verifiedRecord.getGradebookPercentage(),verifiedRecord.getResult().getYearMark(), Double.parseDouble(yearMarkWeight));
				verifiedRecord.getAltExamOpt().setFinalMark(String.valueOf(revisedFinalMark));
				verifiedRecord.getAltExamOpt().setConcessionMark(String.valueOf(verifiedRecord.getGradebookPercentage()));
				verifiedRecord.setUpload(true);
				fiStudentForm.setTotalStatusRecords(fiStudentForm.getTotalStatusRecords()+1);
				verifiedRecord.setStatus("Accepted for Import");
			}
			listUploadRecords.add(verifiedRecord);
		}	
		
		fiStudentForm.setListBatchVerifiedRecords(listUploadRecords);
		
		return "batchMarksUpload";		
	}
	
	public ActionForward codAuthorisation(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		FinalMarkConcessionForm fiStudentForm = (FinalMarkConcessionForm ) form;
		
		fiStudentForm.setCurrentPage("codAuthorisation");
		return mapping.findForward("codAuthorisation");		
	}
	
	public ActionForward deanAuthorisation(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {	
		
		FinalMarkConcessionForm fiStudentForm = (FinalMarkConcessionForm ) form;
		
		fiStudentForm.setCurrentPage("deanAuthorisation");
		return mapping.findForward("deanAuthorisation");		
	}
	
	
	public ActionForward cancel(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		FinalMarkConcessionForm fiStudentForm = (FinalMarkConcessionForm ) form;
		FinalMarkConcessionDAO dao = new FinalMarkConcessionDAO();	
		List<DepartmentRecord> listDepartments = dao.getDepartmentList(fiStudentForm.getUserCollege().getCode());
		request.setAttribute("listDepartments", listDepartments);
		
		fiStudentForm.setCurrentPage("home");
		return mapping.findForward("home");	
	}
	
	public ActionForward prevStep(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
		       FinalMarkConcessionForm fiStudentForm = (FinalMarkConcessionForm ) form;
		       FinalMarkConcessionDAO dao = new FinalMarkConcessionDAO();	
		       List<DepartmentRecord> listDepartments = dao.getDepartmentList(fiStudentForm.getUserCollege().getCode());
		       request.setAttribute("listDepartments", listDepartments);
			
		String prevPage="input";
		
		if (fiStudentForm.getCurrentPage().equalsIgnoreCase("requestCodAuthorisation")||
				fiStudentForm.getCurrentPage().equals("cancelRequest")||(!fiStudentForm.isDataSaved())){
			             prevPage="editConcessionForm";
			             fiStudentForm.setDataSaved(true);
		}
		
		//Johanet 20160122
		if (fiStudentForm.getCurrentPage().equalsIgnoreCase("confirmChanges")){
			prevPage="editConcessionForm";
		}
		
		if (fiStudentForm.getCurrentPage().equalsIgnoreCase("viewConcessionForm")){
			if (fiStudentForm.getListAccess().equalsIgnoreCase("view")){
				prevPage="viewFiStudentList";
			}
			if (fiStudentForm.getListAccess().equalsIgnoreCase("edit")){
				//if edit redisplay page
				prevPage="editFiStudentList";
			}
		}
		if (fiStudentForm.getCurrentPage().equalsIgnoreCase("editConcessionForm")){
			List<FinalMarkStudentRecord> listStudent = new ArrayList<FinalMarkStudentRecord>();
			listStudent = getConcessionList(fiStudentForm,"REDISPLAY");
			fiStudentForm.setFiStudents(listStudent);
			prevPage="editFiStudentList";
		}
		if (fiStudentForm.getCurrentPage().equalsIgnoreCase("batchMarksUpload")){
			prevPage="fiAssignmentSelection";
		}
		
		if (fiStudentForm.getCurrentPage().equalsIgnoreCase("batchCodConfirmAuthRequest")){			
			prevPage="batchCodAuthReq";
		}
		
		fiStudentForm.setCurrentPage(prevPage);
		return mapping.findForward(prevPage);
	}	
	
	public ActionForward displayCancelRequest(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	        	 FinalMarkConcessionForm fiStudentForm = (FinalMarkConcessionForm ) form;
	        	//Johanet 20160122 
	        	 getConcessionFormData(fiStudentForm);
	        	 
	       fiStudentForm.setCurrentPage("cancelRequest");
		   return mapping.findForward("cancelRequest");
	}	
	
	
	public ActionForward cancelRequest(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		FinalMarkConcessionForm fiStudentForm = (FinalMarkConcessionForm ) form;
		ActionMessages messages = new ActionMessages();	
		String newStatus="AUTHCANREQ";
		
		FinalMarkConcessionDAO dao = new FinalMarkConcessionDAO();		
		
		FinalMarkStudentRecord fiStudentRec = new FinalMarkStudentRecord();	
		AlternativeExamOpportunityRecord altExamOpt = new AlternativeExamOpportunityRecord();
		
		fiStudentRec = fiStudentForm.getRecordSelected();
		int studentNr=fiStudentRec.getStudentNumber();
		String studyUnit=fiStudentRec.getStudyUnit();
		Short examYear=Short.parseShort(fiStudentForm.getExamYear());
		Short examPeriod= fiStudentForm.getExamPeriod();			
		
		//get data as on database
		altExamOpt = dao.getAlternativeAssessmentRecord(examYear, examPeriod, studyUnit, studentNr);
		fiStudentForm.setAlternativeExamOpportunity(altExamOpt);		
		
		if (!isValidStatusChange(altExamOpt.getStatus(), newStatus)){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"Action invalid. Cancel authorisation request not allowed for concession record in status: " + fiStudentForm.getAlternativeExamOpportunity().getStatusDesc()));			
		}
		if (!messages.isEmpty()) {
			addErrors(request,messages);
			fiStudentForm.setCurrentPage("editConcessionForm");
			return mapping.findForward("editConcessionForm");			
		}
		
		dao.updateAlternativeAssessmentRecord(examYear, examPeriod, studyUnit, studentNr, null, null, altExamOpt, newStatus,fiStudentForm.getNovellUserid(),"","");
		
		altExamOpt = dao.getAlternativeAssessmentRecord(examYear, examPeriod, studyUnit, studentNr);	
		fiStudentForm.setAlternativeExamOpportunity(altExamOpt);	
		
		fiStudentForm.setCurrentPage("editConcessionForm");
		return mapping.findForward("editConcessionForm");
	}
	
	
	
	public ActionForward requestAuthorisation(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		FinalMarkConcessionForm fiStudentForm = (FinalMarkConcessionForm ) form;
		ActionMessages messages = new ActionMessages();	
		
		AlternativeExamOpportunityRecord currentAltExamOpt = new AlternativeExamOpportunityRecord();
		AlternativeExamOpportunityRecord altExamOpt = new AlternativeExamOpportunityRecord();
		FinalMarkConcessionDAO dao = new FinalMarkConcessionDAO();		
		
	
		//get data as on database
		currentAltExamOpt = dao.getAlternativeAssessmentRecord(Short.parseShort(fiStudentForm.getExamYear()),
				fiStudentForm.getExamPeriod(),
				fiStudentForm.getRecordSelected().getStudyUnit(),
				fiStudentForm.getRecordSelected().getStudentNumber());	
		
		altExamOpt = fiStudentForm.getAlternativeExamOpportunity();
		
		if (!altExamOpt.getStatus().equalsIgnoreCase("FORMSAVED")){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"Authorisation request only allowed if status is 'Form Saved'"));
		}
		//Johanet 20160122 - check data as changed on form
		if(altExamOpt.getFinalMark().equalsIgnoreCase("0") &&
				!altExamOpt.getAlternativeAssessOpt().equalsIgnoreCase("RS")){
		       if(altExamOpt.getZeroMarkReason() ==null ||
		    		   altExamOpt.getZeroMarkReason().trim().equals("N/A")){
				            messages.add(ActionMessages.GLOBAL_MESSAGE,
						    new ActionMessage("message.generalmessage",
									"A reason for a zero Concession Mark is required"));
		       }
		}
		if (altExamOpt.getFinalMark()==null || 
			altExamOpt.getFinalMark().trim().equalsIgnoreCase("") ){
			if (altExamOpt.getAlternativeAssessOpt().equalsIgnoreCase("OE") ||
					altExamOpt.getAlternativeAssessOpt().equalsIgnoreCase("SP")||
					altExamOpt.getAlternativeAssessOpt().equalsIgnoreCase("AF")){
						messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"Final Assessment Mark is required before authorisation can be requested."));
			}	
		}
		
		if (!messages.isEmpty()) {
			addErrors(request,messages);					
			return mapping.findForward(fiStudentForm.getCurrentPage());	
		}
		
		if (!(currentAltExamOpt.getAcademicSupportOpt().trim()).equalsIgnoreCase(altExamOpt.getAcademicSupportOpt().trim()) ||
			!(currentAltExamOpt.getAcademicSupportOtherDesc().trim()).equalsIgnoreCase(altExamOpt.getAcademicSupportOtherDesc().trim()) ||
			!(currentAltExamOpt.getAlternativeAssessOpt().trim()).equalsIgnoreCase(altExamOpt.getAlternativeAssessOpt().trim()) ||
			!(currentAltExamOpt.getAlternativeAssessOtherDesc().trim()).equalsIgnoreCase(altExamOpt.getAlternativeAssessOtherDesc().trim()) ||
			!(currentAltExamOpt.getFinalMark().trim()).equalsIgnoreCase(altExamOpt.getFinalMark().trim()) ||
			!(currentAltExamOpt.getConcessionMark().trim()).equalsIgnoreCase(altExamOpt.getConcessionMark().trim()) ||
			!(currentAltExamOpt.getZeroMarkReason().trim()).equalsIgnoreCase(altExamOpt.getZeroMarkReason().trim()) ||
			!currentAltExamOpt.getResponsibleLecturer().getPersonnelNumber().equalsIgnoreCase(altExamOpt.getResponsibleLecturer().getPersonnelNumber())){
			fiStudentForm.setDataSaved(false);
			fiStudentForm.setCurrentPage("confirmChanges");
			return mapping.findForward("confirmChanges");
		}else{
			String page = "";
			fiStudentForm.setDataSaved(true);
			messages = getAuthorisationStructure(mapping, form, request, response);
			
			if(!messages.isEmpty()){
				return mapping.findForward(fiStudentForm.getCurrentPage());	
			}
				
			fiStudentForm.setCurrentPage("requestAuthorisation");
			return mapping.findForward("requestAuthorisation");
		}
	}
	
	public ActionForward requestBatchAuth(
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
									"Please select one or more items to request authorisation for."));
		}
		if (!messages.isEmpty()) {
			addErrors(request,messages);					
			return mapping.findForward(fiStudentForm.getCurrentPage());	
		}	
		
		messages = getAuthorisationStructure(mapping, form, request, response);
		
		if(!messages.isEmpty()){
			return mapping.findForward(fiStudentForm.getCurrentPage());	
		}	
		
		BatchCodAuthRecord authRequest = new BatchCodAuthRecord();	
		List<BatchCodAuthRecord>confirmList = new ArrayList<BatchCodAuthRecord>();
			
		
		for (int i=0; i <fiStudentForm.getIndexNrSelectedAuth().length; i++) {
			String array[] = fiStudentForm.getIndexNrSelectedAuth();
			authRequest = (BatchCodAuthRecord)fiStudentForm.getListBatchCodAuthRecords().get(Integer.parseInt(array[i]));
			confirmList.add(authRequest);
		}
		
		request.setAttribute("confirmList", confirmList);
		fiStudentForm.setSelectedAuthoriser("");
		
		fiStudentForm.setCurrentPage("batchCodConfirmAuthRequest");
		return mapping.findForward("batchCodConfirmAuthRequest");
	}
	
	public ActionForward requestAuthorisationWithOutSaving(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		FinalMarkConcessionForm fiStudentForm = (FinalMarkConcessionForm ) form;
		ActionMessages messages = new ActionMessages();	
		
		String page = "";
		messages = getAuthorisationStructure(mapping, form, request, response);
				
		if(!messages.isEmpty()){
			return mapping.findForward(fiStudentForm.getCurrentPage());	
		}
				
		fiStudentForm.setCurrentPage("requestAuthorisation");
		return mapping.findForward("requestAuthorisation");
	}
			
	public ActionMessages getAuthorisationStructure(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		FinalMarkConcessionForm fiStudentForm = (FinalMarkConcessionForm ) form;
		ActionMessages messages = new ActionMessages();	
		
		//get department info		
		//get cod		
		//get acting cod's			
		FinalMarkConcessionDAO dao = new FinalMarkConcessionDAO();
		String dpt=dao.getStudyUnitDpt(fiStudentForm.getStudyUnitSearchCriteria());
		
		DepartmentDAO deptDAO = new DepartmentDAO();
		Department department = new Department();
		department = deptDAO.getDepartment(Short.parseShort((dpt)));
		//department.getCod().setEmailAddress("pretoj@unisa.ac.za");//Just for testing - remove before PROD
		fiStudentForm.setAuthDepartment(department);
		List codList = new ArrayList();
		if (fiStudentForm.getAuthDepartment().getCod().getPersonnelNumber()!=null){
			codList.add(department.getCod());
		}
		request.setAttribute("codList",codList);
		
		if (fiStudentForm.getAuthDepartment().getCod().getPersonnelNumber()==null &&
				fiStudentForm.getAuthDepartment().getActingCodList().size()==0){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"Authorisation request not allowed, no COD or stand in COD's have been defined for department to send authorisation request to."));
		}		
		
		//get myUnisa user's email address
		fiStudentForm.setResponseEmailAddress(fiStudentForm.getMyUnisaUser().getEmailAddress());	
		
		return messages;
	}
	
	
	public ActionForward submitBatchAuthRequest(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		FinalMarkConcessionForm fiStudentForm = (FinalMarkConcessionForm ) form;
		ActionMessages messages = new ActionMessages();
		
		if (fiStudentForm.getSelectedAuthoriser()==null){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"Please select a person to notify that an authorisation request was submitted for this Alternative Exam Opportunity"));
		}
		if (!messages.isEmpty()) {
			addErrors(request,messages);
			List codList = new ArrayList();
			if (fiStudentForm.getAuthDepartment().getCod().getPersonnelNumber()!=null){
				codList.add(fiStudentForm.getAuthDepartment().getCod());
			}
			request.setAttribute("codList",codList);			
			return mapping.findForward(fiStudentForm.getCurrentPage());
		}	
		
		String studyUnit=fiStudentForm.getStudyUnitSearchCriteria();
		Short examYear=Short.parseShort(fiStudentForm.getExamYear());
		Short examPeriod= fiStudentForm.getExamPeriod();	
		String newStatus = "AUTHREQCOD";
		FinalMarkConcessionDAO dao = new FinalMarkConcessionDAO();
	
		String requestActionFrom = fiStudentForm.getSelectedAuthoriser();
		
		//to decide whether to send email or not
		boolean	sendEmail = dao.sendEmailToAuthoriser(Short.parseShort(fiStudentForm.getExamYear()), fiStudentForm.getExamPeriod(), fiStudentForm.getSelectedAuthoriser(),newStatus);
		int countRequest = 0;
		
		for (int i=0; i <fiStudentForm.getIndexNrSelectedAuth().length; i++) {
			String array[] = fiStudentForm.getIndexNrSelectedAuth();
			AlternativeExamOpportunityRecord altExamOpt = new AlternativeExamOpportunityRecord();
			BatchCodAuthRecord authRequest = new BatchCodAuthRecord();	
			authRequest = (BatchCodAuthRecord)fiStudentForm.getListBatchCodAuthRecords().get(Integer.parseInt(array[i]));
			altExamOpt = dao.getAlternativeAssessmentRecord(examYear, examPeriod, studyUnit, authRequest.getStudentNumber());
			altExamOpt.setAuthResponseEmail(fiStudentForm.getResponseEmailAddress());
			String error ="";
			if (!isMarked(altExamOpt)){
				error = "Final Assessment Mark is required before authorisation can be requested";
				log.error("FI Concession Cod request error, Student nr: " + authRequest.getStudentNumber() + "," + error );	
				continue;
			}			
			
			if (!isValidStatusChange(altExamOpt.getStatus(), newStatus)){				
				error = "Action invalid. Authorisation request not allowed for concession record in status: " + altExamOpt.getStatusDesc();
				log.error("FI Concession Cod request error, Student nr: " + authRequest.getStudentNumber() + "," + error );	
				continue;
			}		
			
			try {
					dao.updateAlternativeAssessmentRecord(examYear, examPeriod, studyUnit, authRequest.getStudentNumber(), null, null, altExamOpt, newStatus,fiStudentForm.getNovellUserid(),"",requestActionFrom);
					countRequest = countRequest + 1;
			}  catch (Exception e) {					
				 	log.error("FI Concession Cod request error, Student nr: " + authRequest.getStudentNumber() + "," + e );				 	
			}			
		}
		
		//send email to COD or Stand In Cod.
				//Only one email may be send to COD per day. Determine if email was send for the day
				//email notification of authorisation request to cod or acting cod			
				if (sendEmail && countRequest > 0){
						String toAddress="";
						String addressee="";
					if (fiStudentForm.getSelectedAuthoriser().equalsIgnoreCase(fiStudentForm.getAuthDepartment().getCod().getPersonnelNumber())){
						toAddress=fiStudentForm.getAuthDepartment().getCod().getEmailAddress();
						addressee=fiStudentForm.getAuthDepartment().getCod().getName();
					} else {
						for (int i = 0; i < fiStudentForm.getAuthDepartment().getActingCodList().size(); i++){
							String actingCod = ((Person)(fiStudentForm.getAuthDepartment().getActingCodList().get(i))).getPersonnelNumber();
							if (fiStudentForm.getSelectedAuthoriser().equalsIgnoreCase(actingCod)){
								toAddress=((Person)(fiStudentForm.getAuthDepartment().getActingCodList().get(i))).getEmailAddress();
								addressee=((Person)(fiStudentForm.getAuthDepartment().getActingCodList().get(i))).getName();
								i = fiStudentForm.getAuthDepartment().getActingCodList().size();
							}
						}
					}
					//do not send email on dev & qa -default email to pretoj@unisa.ac.za
					String serverpath = ServerConfigurationService.getServerUrl();
					if (serverpath.contains("mydev") || serverpath.contains("myqa") || serverpath.contains("localhost")){
						toAddress="pretoj@unisa.ac.za";
					}	
					sendNotifyEmail(toAddress, addressee);	
				}
				
		List<BatchCodAuthRecord> listFiRecords = new ArrayList<BatchCodAuthRecord>();
		listFiRecords = dao.getFiRecordsValidForAuthReq(Short.parseShort(fiStudentForm.getExamYear()),fiStudentForm.getExamPeriod(),fiStudentForm.getStudyUnitSearchCriteria());
		
		fiStudentForm.setListBatchCodAuthRecords(listFiRecords);
		String[] array = new String[0];
		fiStudentForm.setIndexNrSelectedAuth(array);
				
		fiStudentForm.setCurrentPage("batchCodAuthReq");
		return mapping.findForward("batchCodAuthReq");
	}
	
	public ActionForward submitAuthRequest(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		
		FinalMarkConcessionForm fiStudentForm = (FinalMarkConcessionForm ) form;
		ActionMessages messages = new ActionMessages();	
		
		FinalMarkStudentRecord fiStudentRec = new FinalMarkStudentRecord();	
		AlternativeExamOpportunityRecord altExamOpt = new AlternativeExamOpportunityRecord();
		fiStudentRec = fiStudentForm.getRecordSelected();
		altExamOpt = fiStudentForm.getAlternativeExamOpportunity();
		String newStatus = "AUTHREQCOD";
		
		if (!isMarked(altExamOpt)){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"Final Assessment Mark is required before authorisation can be requested."));
		}
		
		if (fiStudentForm.getSelectedAuthoriser()==null){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"Please select a person to notify that an authorisation request was submitted for this Alternative Exam Opportunity"));
		}
		if (!isValidStatusChange(altExamOpt.getStatus(), newStatus)){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"Action invalid. Authorisation request not allowed for concession record in status: " + altExamOpt.getStatusDesc()));
		}		
		if (!messages.isEmpty()) {
			addErrors(request,messages);
			List codList = new ArrayList();
			if (fiStudentForm.getAuthDepartment().getCod().getPersonnelNumber()!=null){
				codList.add(fiStudentForm.getAuthDepartment().getCod());
			}
			request.setAttribute("codList",codList);
			fiStudentForm.setCurrentPage("requestAuthorisation");
			return mapping.findForward("requestAuthorisation");
		}	
		
		//set to person that requested authorisation
		altExamOpt.setAuthResponseEmail(fiStudentForm.getResponseEmailAddress());
		String requestActionFrom = fiStudentForm.getSelectedAuthoriser();
		
		int studentNr=fiStudentRec.getStudentNumber();
		String studyUnit=fiStudentRec.getStudyUnit();
		Short examYear=Short.parseShort(fiStudentForm.getExamYear());
		Short examPeriod= fiStudentForm.getExamPeriod();		
		
		FinalMarkConcessionDAO dao = new FinalMarkConcessionDAO();
		//to decide whether to send email or not
		boolean	sendEmail = dao.sendEmailToAuthoriser(Short.parseShort(fiStudentForm.getExamYear()), fiStudentForm.getExamPeriod(), fiStudentForm.getSelectedAuthoriser(),newStatus);
		
		dao.updateAlternativeAssessmentRecord(examYear, examPeriod, studyUnit, studentNr, null, null, altExamOpt, newStatus,fiStudentForm.getNovellUserid(),"",requestActionFrom);
		
		altExamOpt = dao.getAlternativeAssessmentRecord(examYear, examPeriod, studyUnit, studentNr);	
		fiStudentForm.setAlternativeExamOpportunity(altExamOpt);		
		
		//send email to COD or Stand In Cod.
		//Only one email may be send to COD per day. Determine if email was send for the day
		//email notification of authorisation request to cod or acting cod			
		if (sendEmail){
				String toAddress="";
				String addressee="";
			if (fiStudentForm.getSelectedAuthoriser().equalsIgnoreCase(fiStudentForm.getAuthDepartment().getCod().getPersonnelNumber())){
				toAddress=fiStudentForm.getAuthDepartment().getCod().getEmailAddress();
				addressee=fiStudentForm.getAuthDepartment().getCod().getName();
			} else {
				for (int i = 0; i < fiStudentForm.getAuthDepartment().getActingCodList().size(); i++){
					String actingCod = ((Person)(fiStudentForm.getAuthDepartment().getActingCodList().get(i))).getPersonnelNumber();
					if (fiStudentForm.getSelectedAuthoriser().equalsIgnoreCase(actingCod)){
						toAddress=((Person)(fiStudentForm.getAuthDepartment().getActingCodList().get(i))).getEmailAddress();
						addressee=((Person)(fiStudentForm.getAuthDepartment().getActingCodList().get(i))).getName();
						i = fiStudentForm.getAuthDepartment().getActingCodList().size();
					}
				}
			}
			//do not send email on dev & qa -default email to pretoj@unisa.ac.za
			String serverpath = ServerConfigurationService.getServerUrl();
			if (serverpath.contains("mydev") || serverpath.contains("myqa")){
				toAddress="pretoj@unisa.ac.za";
			}	
			sendNotifyEmail(toAddress, addressee);	
		}
		fiStudentForm.setCurrentPage("editConcessionForm");
		return mapping.findForward("editConcessionForm");
	}
	
	
	
	public ActionForward initial(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		FinalMarkConcessionForm fiStudentForm = (FinalMarkConcessionForm ) form;
		ActionMessages messages = new ActionMessages();
		String exams[] = {"01","JAN/FEB","06","MAY/JUN","10","OCT/NOV"};
		
		fiStudentForm.setNovellUserid(null);
		User user = null;
		
		sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
	    userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
		Session currentSession = sessionManager.getCurrentSession();
		String userID  = currentSession.getUserId();
				
		if (userID != null) {
			user = userDirectoryService.getUser(userID);
			fiStudentForm.setNovellUserid(user.getEid().toUpperCase());
		}	
		
		Person person = new Person();
		UserDAO userdao = new UserDAO();
		person = userdao.getPerson(fiStudentForm.getNovellUserid());		
						
		if (person.getPersonnelNumber()==null){
			//user does not have access to this function 
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"User not found"));
			addErrors(request,messages);
			return mapping.findForward("displayErrorPage");
		}		
		fiStudentForm.setMyUnisaUser(person);
		StudentSystemGeneralDAO daoxamprd = new StudentSystemGeneralDAO();
		List examPeriods = daoxamprd.getExamPeriods();
		fiStudentForm.setExamPeriodCodes(examPeriods);
		Examination defaultExam = new Examination();
		defaultExam = getDefaultExamination();
		if ((fiStudentForm.getExamYear()==null || fiStudentForm.getExamYear().trim().equalsIgnoreCase("")) &&
				(fiStudentForm.getExamPeriod()==null || fiStudentForm.getExamPeriod().compareTo(Short.parseShort("0"))==0)){
			fiStudentForm.setExamYear(String.valueOf(defaultExam.getExamYear()));
			fiStudentForm.setExamPeriod(defaultExam.getExamPeriod());
		}		
		FinalMarkConcessionDAO dao = new FinalMarkConcessionDAO();
		
		if (person.getDepartmentCode()==null || person.getDepartmentCode().equalsIgnoreCase("")){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"You are not authorised to use this tool as you are not connected a department"));
			addErrors(request,messages);
			fiStudentForm.setCurrentPage("displayErrorPage");
			return mapping.findForward("displayErrorPage");
		}
		DepartmentRecord dpt = dao.getDepartment(Short.parseShort(person.getDepartmentCode()));
		CollegeRecord college = dao.getCollege(dpt.getCollegeCode());
	    List<DepartmentRecord> listDepartments = dao.getDepartmentList(dpt.getCollegeCode());
		fiStudentForm.setListDepartments(listDepartments);
		//request.setAttribute("listDepartments", listDepartments);
		
		
		for (int i=0; i < listDepartments.size(); i++){
			dpt = (DepartmentRecord)listDepartments.get(i);
			if (dpt.getCode().compareTo(Short.parseShort(person.getDepartmentCode()))==0){
				fiStudentForm.setSelectedDepartment(dpt.getCode());
				i = listDepartments.size();
			}
		}
		
		if (fiStudentForm.getSelectedDepartment()==null){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"You are not authorised to use this tool as you are not connected to a valid department"));
			addErrors(request,messages);
			fiStudentForm.setCurrentPage("displayErrorPage");
			return mapping.findForward("displayErrorPage");
		}
		
		fiStudentForm.setUserDepartment(dpt);
		
		//Get status list
		StudentSystemGeneralDAO genDao = new StudentSystemGeneralDAO();
		List<Gencod> list = new ArrayList<Gencod>();
		Gencod gencod = new Gencod();
		gencod.setCode("");
		gencod.setEngDescription("All");
		
		list.add(0,gencod);
		int index =0;
		for (int i=0; i < genDao.getGenCodes((short)90,3).size(); i++) {
			if (!((Gencod)(genDao.getGenCodes((short)90,3).get(i))).getCode().equalsIgnoreCase("CANCELLED")){
				index = index + 1;
				list.add(index, (Gencod)(genDao.getGenCodes((short)90,3).get(i)));
			}			
		}
		fiStudentForm.setListStatus(list);
		fiStudentForm.setStatusSelected("");
		String academicSupportListDesc= "";
		String alternativeAssessListDesc="";
		
		//Get alternative assessment list
		list = new ArrayList<Gencod>();
		
		List<Gencod> assessmentGenCodsList=genDao.getGenCodes((short)91,3);
		for (int i=0; i < assessmentGenCodsList.size(); i++) {
			      Gencod  genericCode=(Gencod)( assessmentGenCodsList.get(i));
		          list.add(i,genericCode );
				  alternativeAssessListDesc+= ( genericCode + " - " + genericCode.getEngDescription() + "<BR/>");
	    }		

		fiStudentForm.setListAlternativeAssess(list);
		fiStudentForm.setAlternativeAssessListDesc(alternativeAssessListDesc);

		//Get academic support list
		list = new ArrayList<Gencod>();
		List<Gencod> acaddemicSuppGenCodsList=genDao.getGenCodes((short)92,3);
		for (int i=0; i < acaddemicSuppGenCodsList.size(); i++) {
			   Gencod  genericCode=(Gencod)(acaddemicSuppGenCodsList.get(i));
			   list.add(i,genericCode);
			   academicSupportListDesc = genericCode.getCode() + " - " +genericCode.getEngDescription() + "<BR/>";
		}
		fiStudentForm.setListAcademicSupport(list);
		fiStudentForm.setAcademicSupportListDesc(academicSupportListDesc);	
		
		//get Zero Mark reason list
		list = new ArrayList<Gencod>();
		list=genDao.getGenCodes((short)239,3);
		fiStudentForm.setListZeroMarkReason(list);
		
		fiStudentForm.setUserCollege(college);
		FinalMarkStudentRecord record = new FinalMarkStudentRecord();
		record.setStudentNumber(0);
		record.setStudyUnit("");
		record.setDptDesc("");
		record.setSchoolAbbreviation("");
		fiStudentForm.setFirstRecord(record);
		fiStudentForm.setLastRecord(record);
		fiStudentForm.setDisplayAction("D");
		fiStudentForm.setStudyUnitSearchCriteria("");
		fiStudentForm.setSearchCriteria("D");
		
		fiStudentForm.setCurrentPage("input");
		return mapping.findForward("input");	
	}	
	
	
	public ActionForward calculateFinalMark(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		FinalMarkConcessionForm fiStudentForm = (FinalMarkConcessionForm) form;
		ActionMessages messages = new ActionMessages();
	
		String acadHistUrl= ServerConfigurationService.getServerUrl();	
		acadHistUrl = acadHistUrl.trim() + "/unisa-findtool/default.do?sharedTool=unisa.acadhistory&SYSTEM=FIC&studNr=" + fiStudentForm.getStudentNumberSelected();
		request.setAttribute("acadHistUrl", acadHistUrl);
		
		
		if (fiStudentForm.getAlternativeExamOpportunity().getConcessionMark()==null || 
				fiStudentForm.getAlternativeExamOpportunity().getConcessionMark().trim().equalsIgnoreCase("")){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"Please enter student concession mark obtained."));
		} else {
			if (!isInteger(fiStudentForm.getAlternativeExamOpportunity().getConcessionMark())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"Concession Mark must be numeric"));
			}
		}
		if (!messages.isEmpty()) {
			addErrors(request,messages);
			fiStudentForm.setCurrentPage("editConcessionForm");
			return mapping.findForward("editConcessionForm");			
		}			
		
		
		double yearMarkWeight = Double.parseDouble(fiStudentForm.getRecordSelected().getYearMarkWeight());
		double yearMarkEarned = Double.parseDouble(fiStudentForm.getRecordSelected().getYearMarkEarned());
		int concessionMark = Integer.parseInt(fiStudentForm.getAlternativeExamOpportunity().getConcessionMark());
		
		int finalMark = 0;
		
		finalMark = getCalcFinalMark(concessionMark, yearMarkEarned, yearMarkWeight);
		fiStudentForm.getAlternativeExamOpportunity().setFinalMark(String.valueOf(finalMark));
		
		fiStudentForm.setCurrentPage("editConcessionForm");
		return mapping.findForward("editConcessionForm");
	}
	
	public int getCalcFinalMark(int concessionMark, double yearMarkEarned, double yearMarkWeight) {
		int finalMark=0;
		Double finalMarkInclYearMark = 0.0;
		double concessionWeight = 100 - yearMarkWeight;
		
		if (concessionMark == 0){
			return 0;
		}
		finalMarkInclYearMark =(concessionMark * (concessionWeight/100)) +  
				(yearMarkEarned * (yearMarkWeight/100)) + 0.5;
		
		if (finalMarkInclYearMark < concessionMark){
			finalMark = concessionMark;			
		}else{
			finalMark = finalMarkInclYearMark.intValue();
		}
		
		return finalMark;
	}
	
	public int getCalcFinalMark(double concessionMark, double yearMarkEarned, double yearMarkWeight) {
		int finalMark=0;
		Double finalMarkInclYearMark = 0.0;
		double concessionWeight = 100 - yearMarkWeight;
		
		if (concessionMark == 0){
			return 0;
		}
		finalMarkInclYearMark =(concessionMark * (concessionWeight/100)) +  
				(yearMarkEarned * (yearMarkWeight/100)) + 0.5;
		
		if (finalMarkInclYearMark < concessionMark){
			finalMarkInclYearMark = concessionMark;			
		}
		
		finalMark = finalMarkInclYearMark.intValue();
		
		return finalMark;
	}
	
	public boolean isValidStatusChange(String oldStatus, String newStatus) {	
	
		if (newStatus.equalsIgnoreCase("FORMSAVED")){
			if (oldStatus.equalsIgnoreCase("IDENTIFIED") ||
					oldStatus.equalsIgnoreCase("FORMSAVED") ||
					oldStatus.equalsIgnoreCase("AUTHREJCOD") ||
					oldStatus.equalsIgnoreCase("AUTHREJDN") ||
					oldStatus.equalsIgnoreCase("AUTHCANREQ") ||
					oldStatus.equalsIgnoreCase("DECLINED")) {
					return true;
			}
		}
		if (newStatus.equalsIgnoreCase("AUTHREQCOD")){
			if (oldStatus.equalsIgnoreCase("FORMSAVED")){
				return true;
			}
		}	
		if (newStatus.equalsIgnoreCase("AUTHREQDN")){
			if (oldStatus.equalsIgnoreCase("AUTHREQCOD")){
				return true;
			}
		}	
		if (newStatus.equalsIgnoreCase("AUTHORISED")){
			if (oldStatus.equalsIgnoreCase("AUTHREQDN")){
				return true;
			}
		}	
		if (newStatus.equalsIgnoreCase("PROCESSED")){
			if (oldStatus.equalsIgnoreCase("AUTHORISED")){
				return true;
			}
		}
		if (newStatus.equalsIgnoreCase("DECLINED")){
			if (oldStatus.equalsIgnoreCase("AUTHORISED")){
				return true;
			}
		}
		if (newStatus.equalsIgnoreCase("AUTHREJCOD")){
			if (oldStatus.equalsIgnoreCase("AUTHREQCOD")){
				return true;
			}
		}
		if (newStatus.equalsIgnoreCase("AUTHREJDN")){
			if (oldStatus.equalsIgnoreCase("AUTHREQDN")){
				return true;
			}
		}
		if (newStatus.equalsIgnoreCase("AUTHCANREQ")){
			if (oldStatus.equalsIgnoreCase("AUTHREQCOD")){
				return true;
			}
		}
		return false;
		
	}
	
	
	
	
	public ActionForward saveConcessionForm(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		FinalMarkConcessionForm fiStudentForm = (FinalMarkConcessionForm ) form;
		ActionMessages messages = new ActionMessages();
		
		FinalMarkStudentRecord fiStudentRec = new FinalMarkStudentRecord();	
		AlternativeExamOpportunityRecord altExamOpt = new AlternativeExamOpportunityRecord();
		fiStudentRec = fiStudentForm.getRecordSelected();
		altExamOpt = fiStudentForm.getAlternativeExamOpportunity();
		String newStatus = "FORMSAVED";
		
		if (!isValidStatusChange(fiStudentForm.getAlternativeExamOpportunity().getStatus(), newStatus)){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"Action invalid. Update of form not allowed for concession record in status: " + fiStudentForm.getAlternativeExamOpportunity().getStatusDesc()));
			addErrors(request,messages);
			fiStudentForm.setCurrentPage("editConcessionForm");
			return mapping.findForward("editConcessionForm");	
		}
		
		if (altExamOpt.getAlternativeAssessOpt().equalsIgnoreCase("OT")){
			if (altExamOpt.getAlternativeAssessOtherDesc()==null || 
					altExamOpt.getAlternativeAssessOtherDesc().trim().equalsIgnoreCase("")){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"Please describe 'OTHER' Alternative assessment option"));
			}else if (altExamOpt.getAlternativeAssessOtherDesc().trim().length()>250){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"Description for Alternative assessment option 'OTHER', may not exceed 250 characters."));
			}else{
				altExamOpt.setAlternativeAssessOtherDesc(altExamOpt.getAlternativeAssessOtherDesc().trim());
			}
		}else{
			if (altExamOpt.getAlternativeAssessOtherDesc()!=null && 
					!altExamOpt.getAlternativeAssessOtherDesc().trim().equalsIgnoreCase("")){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"Alternative assessment option, Describe other must be empty if 'OTHER' not selected"));
			}
		}
		
		if (altExamOpt.getAcademicSupportOpt().equalsIgnoreCase("OT")){
			if (altExamOpt.getAcademicSupportOtherDesc()==null || 
					altExamOpt.getAcademicSupportOtherDesc().trim().equalsIgnoreCase("")){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"Please describe 'OTHER' Academic support to be provided"));
			}else if (altExamOpt.getAcademicSupportOtherDesc().trim().length()>250){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"Description for Academic support to be provided 'OTHER', may not exceed 250 characters."));
			}else{
				altExamOpt.setAcademicSupportOtherDesc(altExamOpt.getAcademicSupportOtherDesc().trim());
			}
		}else{
			if (altExamOpt.getAcademicSupportOtherDesc()!=null && 
					!altExamOpt.getAcademicSupportOtherDesc().trim().equalsIgnoreCase("")){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"Academic support to be provided, Describe other must be empty if 'OTHER' not selected"));
			}
		}	
		
		int studentNr=fiStudentRec.getStudentNumber();
		String studyUnit=fiStudentRec.getStudyUnit();
		Short examYear=Short.parseShort(fiStudentForm.getExamYear());
		Short examPeriod= fiStudentForm.getExamPeriod();
		//determine next examination for which for this module
		FinalMarkConcessionDAO dao = new FinalMarkConcessionDAO();
		if (altExamOpt.getAlternativeAssessOpt().equalsIgnoreCase("RS")){
			Examination nextExam = new Examination();
			nextExam = dao.getNextExamination(examYear, examPeriod, studyUnit);
			if (nextExam.getExamYear()==null || nextExam.getExamYear().compareTo(Short.parseShort("0"))==0){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"No next exam opportunity for this module."));
			} else {
				//get the deadline for this next examination
				String deadLine ="";
				deadLine=dao.getExamDeadLine(nextExam.getExamYear().intValue(), nextExam.getExamPeriod(), "FI_EXAM_DL", "FROM");
				if ("".equalsIgnoreCase(deadLine)){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"Deadline date for this examination has not been set"));
				}else {
					Calendar calendar = Calendar.getInstance();
					SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
					Date deadLineDate = formatter.parse(deadLine);		
					calendar.setTime(deadLineDate);
					Date today = new Date();
					calendar.setTime(today);
					if (today.after(deadLineDate)){
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage",
											"The period for referral to the next formal suppl exam has expired, please select another alternative assessment option."));
					}
				}
			}	
		}
		
		
		if (altExamOpt.getConcessionMark()==null || altExamOpt.getConcessionMark().trim().equalsIgnoreCase("") || !isInteger(altExamOpt.getConcessionMark())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"Concession Mark must be numeric"));
		} else if (altExamOpt.getAlternativeAssessOpt().equalsIgnoreCase("RS")){
			//Johanet 20160122 
			if (Integer.parseInt(altExamOpt.getConcessionMark()) > 0 )
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"Concession Mark is not applicable for the alternative assessment option selected."));
		}	
			
		double yearMarkWeight = Double.parseDouble(fiStudentForm.getRecordSelected().getYearMarkWeight());
		double yearMarkEarned = Double.parseDouble(fiStudentForm.getRecordSelected().getYearMarkEarned());
		int concessionMark = Integer.parseInt(fiStudentForm.getAlternativeExamOpportunity().getConcessionMark());
		Short originalFinalMark = fiStudentForm.getRecordSelected().getFinalMark();
		Short originalResultCode = fiStudentForm.getRecordSelected().getResultCode();
		
		int finalMark = 0;
		
		finalMark = getCalcFinalMark(concessionMark, yearMarkEarned, yearMarkWeight);
		
		if (finalMark != Integer.parseInt(fiStudentForm.getAlternativeExamOpportunity().getFinalMark())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"Final Mark not correct, either the year mark or concession mark changed.  Please re-calculate final mark."));
		}
				
		
		
		if (!messages.isEmpty()) {
			addErrors(request,messages);
			fiStudentForm.setCurrentPage("editConcessionForm");
			return mapping.findForward("editConcessionForm");			
		}	
		
		Gencod gencod = new Gencod();
		StudentSystemGeneralDAO gendao = new StudentSystemGeneralDAO();
		if (altExamOpt.getAcademicSupportOpt() == null || altExamOpt.getAcademicSupportOpt().equalsIgnoreCase("")){
			altExamOpt.setAcademicSupportDesc("");
		}else{
			gencod = (Gencod)(gendao.getGenCode("92", altExamOpt.getAcademicSupportOpt()));
			altExamOpt.setAcademicSupportDesc(gencod.getEngDescription());					
		}		
		
		gencod = new Gencod();
		if (altExamOpt.getAlternativeAssessOpt() == null || altExamOpt.getAlternativeAssessOpt().equalsIgnoreCase("")){
			altExamOpt.setAlternativeAssessOptDesc("");
		}else{
			gencod = (Gencod)(gendao.getGenCode("91", altExamOpt.getAlternativeAssessOpt()));
			altExamOpt.setAlternativeAssessOptDesc(gencod.getEngDescription());					
		}	
		
		if (Integer.parseInt(altExamOpt.getConcessionMark())!=0){
			altExamOpt.setZeroMarkReason("N/A");
		}
		
		StringBuilder comment = new StringBuilder("Group assignment - ");
		String logComment = "";
		if (altExamOpt.getAlternativeAssessOpt()!=null && !altExamOpt.getAlternativeAssessOpt().equalsIgnoreCase("")){
			comment.append("Access option: ");
			comment.append(altExamOpt.getAlternativeAssessOptDesc());
		}
		if (altExamOpt.getAcademicSupportOpt()!=null && !altExamOpt.getAcademicSupportOpt().equalsIgnoreCase("")){
			comment.append(" Support option: ");
			comment.append(altExamOpt.getAcademicSupportDesc());
		}
		logComment = comment.toString();
		
		dao.updateAlternativeAssessmentRecord(examYear, examPeriod, studyUnit, studentNr, originalFinalMark, originalResultCode, altExamOpt, newStatus,fiStudentForm.getNovellUserid(), logComment,"");
		altExamOpt = dao.getAlternativeAssessmentRecord(examYear, examPeriod, studyUnit, studentNr);	
		fiStudentForm.setAlternativeExamOpportunity(altExamOpt);
		
		messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage",
							"Concession record successfully updated"));
		addMessages(request, messages);
		
		fiStudentForm.setCurrentPage("editConcessionForm");
		return mapping.findForward("editConcessionForm");
	}		
		
	
	
	
	public ActionForward displayFirst(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		FinalMarkConcessionForm fiStudentForm = (FinalMarkConcessionForm ) form;
		       
		String page = displayData(mapping, form, request, response, "FIRST");
		        
		fiStudentForm.setCurrentPage(page);
		return mapping.findForward(page);
		
	}
	
	public ActionForward displayPrev(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		FinalMarkConcessionForm fiStudentForm = (FinalMarkConcessionForm ) form;
		
		String page = displayData(mapping, form, request, response, "PREV");
		
		fiStudentForm.setCurrentPage(page);
		return mapping.findForward(page);
		
	}
	
	public ActionForward displayNext(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		FinalMarkConcessionForm fiStudentForm = (FinalMarkConcessionForm ) form;
		
		String page = displayData(mapping, form, request, response, "NEXT");
		
		fiStudentForm.setCurrentPage(page);
		return mapping.findForward(page);
		
	}
	
	String displayData(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response,
			String listAction) throws Exception {
		
		FinalMarkConcessionForm fiStudentForm = (FinalMarkConcessionForm ) form;
	
		if (fiStudentForm.getListAccess().equalsIgnoreCase("view")) {
			if(!isInputViewFiStudentListOk(mapping, form, request, response)){
				return "viewFiStudentList";
			}
		}	

		List listStudent = new ArrayList();
		listStudent = getConcessionList(fiStudentForm,listAction);
		fiStudentForm.setFiStudents(listStudent);
		
		if (fiStudentForm.getListAccess().equalsIgnoreCase("view")) {
			return "viewFiStudentList";	
		} else {
			return "editFiStudentList";	
		}
		
	}
	
	private List<FinalMarkStudentRecord> getConcessionList(ActionForm form,String listAction) throws Exception{
		
		FinalMarkConcessionForm fiStudentForm = (FinalMarkConcessionForm) form;
		
		List listStudent = new ArrayList();
		List listFinalStudent = new ArrayList();
		FinalMarkConcessionDAO dao = new FinalMarkConcessionDAO();
		String status = "";
		status = fiStudentForm.getStatusSelected();
		if (fiStudentForm.getSearchCriteria().equalsIgnoreCase("C")){
			listStudent=dao.getStudentFiConcessionList(fiStudentForm.getUserCollege().getCode(), null, null, Short.parseShort(fiStudentForm.getExamYear()), fiStudentForm.getExamPeriod(), status, listAction, fiStudentForm.getFirstRecord(), fiStudentForm.getLastRecord(),
					fiStudentForm.getNovellUserid());
		} else if (fiStudentForm.getSearchCriteria().equalsIgnoreCase("D")){
			listStudent=dao.getStudentFiConcessionList(null, fiStudentForm.getSelectedDepartment(), null, Short.parseShort(fiStudentForm.getExamYear()), fiStudentForm.getExamPeriod(), status, listAction, fiStudentForm.getFirstRecord(), fiStudentForm.getLastRecord(),
					fiStudentForm.getNovellUserid());
		} else {
			listStudent=dao.getStudentFiConcessionList(null, null, fiStudentForm.getStudyUnitSearchCriteria(), Short.parseShort(fiStudentForm.getExamYear()), fiStudentForm.getExamPeriod(), status, listAction, fiStudentForm.getFirstRecord(), fiStudentForm.getLastRecord(),
					fiStudentForm.getNovellUserid());
		}
		
		CoursePeriodLookup periodLookup = new CoursePeriodLookup();
		
		if (listAction.equalsIgnoreCase("NEXT")){
			fiStudentForm.setPageDownFlag("N");
			fiStudentForm.setPageUpFlag("Y");
		}
		if (listAction.equalsIgnoreCase("PREV")){
			fiStudentForm.setPageUpFlag("N");
			fiStudentForm.setPageDownFlag("Y");
		}
		if (listAction.equalsIgnoreCase("FIRST")){
			fiStudentForm.setPageUpFlag("N");
			fiStudentForm.setPageDownFlag("N");
		}
		
		if (listStudent.size() > Constants.MAX_RECORDS){
			if (listAction.equalsIgnoreCase("FIRST")){
				fiStudentForm.setPageUpFlag("N");
				fiStudentForm.setPageDownFlag("Y");
			}
			if (listAction.equalsIgnoreCase("NEXT")){
				fiStudentForm.setPageDownFlag("Y");			
			}
			if (listAction.equalsIgnoreCase("PREV")){			
				fiStudentForm.setPageUpFlag("Y");
			}
		}
		
		int finalListSize=Constants.MAX_RECORDS;
		
		if (listStudent.size() < Constants.MAX_RECORDS){
			finalListSize =listStudent.size();
		}
		
		if (listAction.equalsIgnoreCase("PREV")){
			
			int index = 0;
			for(int i=finalListSize - 1; i >= 0; i--){
				FinalMarkStudentRecord record = new FinalMarkStudentRecord();
				record = (FinalMarkStudentRecord)listStudent.get(i);
				if (i==finalListSize - 1){
					fiStudentForm.setFirstRecord(record);
				}
				fiStudentForm.setLastRecord(record);				
				listFinalStudent.add(index,record);			
				index = index + 1;
			}
			
		}else{
			
			for(int i=0; i < finalListSize; i++){
				FinalMarkStudentRecord record = new FinalMarkStudentRecord();
				record = (FinalMarkStudentRecord)listStudent.get(i);
				if (i==0){
					fiStudentForm.setFirstRecord(record);
				}
				fiStudentForm.setLastRecord(record);
				listFinalStudent.add(i,record);	
			}
			
		}
		
		
		for (int i=0; i < listFinalStudent.size() ;i++) {
			FinalMarkStudentRecord record = new FinalMarkStudentRecord();
			record = (FinalMarkStudentRecord)listFinalStudent.get(i);
			record.setSemesterType(periodLookup.getCourseTypeAsString(record.getSemesterPeriod()));
			Qualification qual = new Qualification();
			qual = dao.getStudentQualification(record.getStudentNumber(), record.getStudyUnit(), record.getAcademicYear(), record.getSemesterPeriod());
			record.setQualification(qual);			
			record.setAccessLevel("read");
			if (fiStudentForm.getListAccess().equalsIgnoreCase("edit")) {
				//Already check for edit access on entry to editList (first page)
//				List lecturerList = new ArrayList();
//				lecturerList = dao.getResponsibleLecturerList(record.getStudyUnit(), fiStudentForm.getExamPeriod(), 
//						       Short.parseShort(fiStudentForm.getExamYear()),fiStudentForm.getNovellUserid().trim());		
//				record.setListLecturer(lecturerList);
				if (record.getStatus().equalsIgnoreCase("IDENTIFIED") ||
						record.getStatus().equalsIgnoreCase("FORMSAVED") ||
						record.getStatus().equalsIgnoreCase("AUTHREJCOD") ||
						record.getStatus().equalsIgnoreCase("AUTHREJDN") ||
						record.getStatus().equalsIgnoreCase("AUTHCANREQ") ||
						record.getStatus().equalsIgnoreCase("AUTHREQCOD") ||
						record.getStatus().equalsIgnoreCase("DECLINED")) {
					record.setAccessLevel("update");
//					for (int j=0; j < lecturerList.size();j++){
//						Person lecturer = new Person();
//						lecturer = (Person) lecturerList.get(j);
//						if (fiStudentForm.getNovellUserid().trim().equalsIgnoreCase(lecturer.getNovellUserId().trim())){
//							record.setAccessLevel("update");
//						}
//					}
				}			
				
			}
		}
	
		return listFinalStudent;
	}
	
	
	public ActionForward viewConcessionForm(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		       FinalMarkConcessionForm fiStudentForm = (FinalMarkConcessionForm ) form;
		       		       
		       fiStudentForm.setStudyUnitSelected(request.getParameter("studyUnit"));
		       fiStudentForm.setStudentNumberSelected(Integer.parseInt(request.getParameter("studentNumber")));
		       getConcessionFormData(fiStudentForm);
			   String acadHistUrl=ServerConfigurationService.getServerUrl();
		       acadHistUrl = acadHistUrl.trim() + "/unisa-findtool/default.do?sharedTool=unisa.acadhistory&SYSTEM=FIC&studNr=" + fiStudentForm.getStudentNumberSelected();
		       request.setAttribute("acadHistUrl", acadHistUrl);
		    
		    fiStudentForm.setCurrentPage("viewConcessionForm");   
			return mapping.findForward("viewConcessionForm");
	}
	
	public ActionForward editConcessionForm(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		        FinalMarkConcessionForm fiStudentForm = (FinalMarkConcessionForm ) form;
		        
		        fiStudentForm.setStudyUnitSelected(request.getParameter("studyUnit"));
		        fiStudentForm.setStudentNumberSelected(Integer.parseInt(request.getParameter("studentNumber")));
		        getConcessionFormData(fiStudentForm);
				if (fiStudentForm.getAlternativeExamOpportunity().getResponsibleLecturer().getNovellUserId().equalsIgnoreCase("")){
		        	for (int i=0; i< fiStudentForm.getListLecturer().size(); i++){
		        		Person lecturer = new Person();
		        		lecturer = (Person) fiStudentForm.getListLecturer().get(i);
						if (fiStudentForm.getNovellUserid().equalsIgnoreCase(lecturer.getNovellUserId())){
							fiStudentForm.getAlternativeExamOpportunity().setResponsibleLecturer(lecturer);
							i =fiStudentForm.getListLecturer().size();
						}
			}
				
		}
		
		String acadHistUrl= ServerConfigurationService.getServerUrl();	
		acadHistUrl = acadHistUrl.trim() + "/unisa-findtool/default.do?sharedTool=unisa.acadhistory&SYSTEM=FIC&studNr=" + fiStudentForm.getStudentNumberSelected();
		request.setAttribute("acadHistUrl", acadHistUrl);
		
		fiStudentForm.setCurrentPage("editConcessionForm");  
		return mapping.findForward("editConcessionForm");
	}
	
	private void getConcessionFormData(FinalMarkConcessionForm fiStudentForm)
	throws Exception {	
	
		FinalMarkConcessionDAO dao = new FinalMarkConcessionDAO();
		StudentContactRecord contactRec = new StudentContactRecord();
		
		AlternativeExamOpportunityRecord altExamOpt = new AlternativeExamOpportunityRecord();		
		
		for (int i=0; i < fiStudentForm.getFiStudents().size(); i++) {
			 FinalMarkStudentRecord fiStudentRec = (FinalMarkStudentRecord)fiStudentForm.getFiStudents().get(i);
			if (fiStudentRec.getStudentNumber().compareTo(fiStudentForm.getStudentNumberSelected())==0 &&
					fiStudentRec.getStudyUnit().equalsIgnoreCase(fiStudentForm.getStudyUnitSelected())){
				//get student contact details if not already set
				if (fiStudentRec.getContactDetails()==null){
					contactRec=dao.getStudentContactDetails(fiStudentForm.getStudentNumberSelected());
					fiStudentRec.setContactDetails(contactRec);
				}
									
				String yearMarkWeight=""+dao.getYearMarkWeight(fiStudentRec.getAcademicYear(),fiStudentRec.getSemesterPeriod(), fiStudentRec.getStudyUnit().trim());
				String yearMarkEarned=""+dao.getStuYearMark(Short.parseShort(fiStudentForm.getExamYear()),fiStudentForm.getExamPeriod(),fiStudentRec.getStudentNumber(),fiStudentRec.getStudyUnit().trim());
				fiStudentRec.setYearMarkEarned(yearMarkEarned);
				fiStudentRec.setYearMarkWeight(yearMarkWeight);
				altExamOpt = dao.getAlternativeAssessmentRecord(Short.parseShort(fiStudentForm.getExamYear()),
						fiStudentForm.getExamPeriod(),
						fiStudentRec.getStudyUnit(),
						fiStudentRec.getStudentNumber());
				fiStudentForm.setAlternativeExamOpportunity(altExamOpt);
				fiStudentForm.setRecordSelected(fiStudentRec);				
				i = fiStudentForm.getFiStudents().size();
			}			
		}
	}	
	
	
	public void sendNotifyEmail(String toAddress, String addressee) {

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
			"Select FI Concession in the left navigation column to access the FI Concession tool. <br/>"+
			"Click on the COD Authorisation button at the bottom of the page. <A href='"+myUnisaPath+"' target='_blank'><b>myUnisa</b></A>"+
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
	
	public ActionForward extractFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
			FinalMarkConcessionForm fiStudentForm = (FinalMarkConcessionForm ) form;
			
			//extract data - dao call (sql)
			List listStudent = new ArrayList();
			FinalMarkConcessionDAO dao = new FinalMarkConcessionDAO();
			String status = "";
			status = fiStudentForm.getStatusSelected();
			if (fiStudentForm.getSearchCriteria().equalsIgnoreCase("C")){
				listStudent=dao.getStudentFiConcessionList(fiStudentForm.getUserCollege().getCode(), null, null, Short.parseShort(fiStudentForm.getExamYear()), fiStudentForm.getExamPeriod(), status, "EXTRACT",null, null,
						fiStudentForm.getNovellUserid());
			} else if (fiStudentForm.getSearchCriteria().equalsIgnoreCase("D")){
				listStudent=dao.getStudentFiConcessionList(null, fiStudentForm.getSelectedDepartment(), null, Short.parseShort(fiStudentForm.getExamYear()), fiStudentForm.getExamPeriod(), status, "EXTRACT",null, null,
						fiStudentForm.getNovellUserid());
			} else {
				listStudent=dao.getStudentFiConcessionList(null, null, fiStudentForm.getStudyUnitSearchCriteria(), Short.parseShort(fiStudentForm.getExamYear()), fiStudentForm.getExamPeriod(), status, "EXTRACT", null, null,
						fiStudentForm.getNovellUserid());
			}
			
			//write data to a file
			//write ~ delimited file
			String fileName="";
			if (listStudent.size()>0){
				String path = getServlet().getServletContext().getInitParameter("applicationFullPath")+"/";
				String fileDir = path +"/";
				String time = (new java.text.SimpleDateFormat("yyyyMMddhhmmssss").format(new java.util.Date()).toString());
				fileName = fileDir + fiStudentForm.getNovellUserid() +"_FiStudentList_"+ time +".txt";			
				writeFile(listStudent, fileName);
			}	
		 
			//tell browser program going to return an application file 
		        //instead of html page
		        response.setContentType("application/octet-stream");
		        response.setHeader("Pragma", "private");
		        response.setHeader("Cache-Control", "private, must-revalidate");
		       	response.setHeader("Content-Disposition","attachment;filename=FiStudentList.txt"); 
		 
			try 
			{
				ServletOutputStream out = response.getOutputStream();	
				File file = new File(fileName);
				BufferedInputStream is = new BufferedInputStream(new FileInputStream(file));
				byte[] buf = new byte[4 * 1024]; // 4K buffer
				int bytesRead;
				while ((bytesRead = is.read(buf)) != -1) {
				out.write(buf, 0, bytesRead);
				}
				is.close();
				out.close(); 			 
			  }catch (Exception e) {e.printStackTrace();}	
			  return null;
		}
	
	public static void writeFile(List records, String fileName){
		 //Create file 
		 try {
			 FileOutputStream out = new FileOutputStream(new File(fileName));
			 PrintStream ps = new PrintStream(out);
			 String headingLine = "School~Department~Study Unit~Student Number~Name~Registration~Published Final Mark~Result~Lecturer~Status~Concession Assessment~Concession Support~Concession Mark~Revised Final Mark";				 
			 ps.print(headingLine);
			 ps.println();
			 for (int i=0; i<records.size(); i++){
				 FinalMarkStudentRecord record = new  FinalMarkStudentRecord();
				 record = (FinalMarkStudentRecord)records.get(i);
				 String assess="";
				 if (record.getAssessmentAbbr().equalsIgnoreCase("OT")){
					 assess = record.getAssessmentAbbr() + '-' + record.getAssessmentDesc() + '(' + record.getAssessmentOtherDesc() + ')';
				 }else{
					 assess = record.getAssessmentAbbr() + '-' + record.getAssessmentDesc();
				 }
				 String support="";
				 if (record.getSupportAbbr().equalsIgnoreCase("OT")){
					 support = record.getSupportAbbr() + '-' + record.getSupportDesc() + '(' + record.getSupportOtherDesc() + ')';
				 }else{
					 support= record.getSupportAbbr() + '-' + record.getSupportDesc();
				 }
				 String line = record.getSchoolAbbreviation() + '~' + record.getDptDesc() + '~' + record.getStudyUnit() + '~' + record.getStudentNumber().toString() + '~' + record.getName() + '~' + 
				 record.getAcademicYear().toString() + '/' + record.getSemesterPeriod().toString() + '~' + record.getFinalMark().toString() + '~' +  record.getResultDesc() + '~' +  record.getPrimaryLecturer().getName() + '~' + 
				 record.getStatus() + '~' + assess + '~' + support + '~'+ record.getConcessionMark() + '~'+ record.getRevisedFinalMark();
				 line = line.replaceAll("[\n\r]", "");
				 ps.print(line);
				 ps.println(); 		
			 }
			 ps.close();
		 } catch (Exception e) {}		
	}
		
	public Short getDefaultExamPeriod() {
		int month;
		Short ExamPeriod = 1;
		month = Calendar.getInstance().get(Calendar.MONTH);
		month++;  //Calender - January = 0
		switch (month) {
		case 1:			
		case 2:
			ExamPeriod =10;
			break;
		case 3:			
		case 4:
		case 5:
			ExamPeriod = 1;
			break;
		case 6:
		case 7:			
		case 8:			
		case 9:
		case 10:
		case 11:
			ExamPeriod=6;
			break;
		case 12:
			ExamPeriod = 10;
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
			year--;
			break;
		case 2:
			year--;
		case 3:
		case 4:
		case 5:
		case 6:
		case 7:
		case 8:			
		case 9:
		case 10:
		case 11:
		case 12:
		}
		return year;
		}
	
	public Examination getDefaultExamination() {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		int month;
		Examination exam = new Examination();
		month = Calendar.getInstance().get(Calendar.MONTH);
		month++; //Calender - January = 0
		switch (month) {
		case 1:
			year = year - 1;
			exam.setExamYear(Short.parseShort(String.valueOf(year)));
			exam.setExamPeriod(Short.parseShort("10"));;
			break;
		case 2:
			year = year - 1;
			exam.setExamYear(Short.parseShort(String.valueOf(year)));
			exam.setExamPeriod(Short.parseShort("10"));;
			break;
		case 3:
			exam.setExamYear(Short.parseShort(String.valueOf(year)));
			exam.setExamPeriod(Short.parseShort("1"));;
			break;
		case 4:
			exam.setExamYear(Short.parseShort(String.valueOf(year)));
			exam.setExamPeriod(Short.parseShort("1"));;
			break;
		case 5:
			exam.setExamYear(Short.parseShort(String.valueOf(year)));
			exam.setExamPeriod(Short.parseShort("1"));;
			break;
		case 6:
			exam.setExamYear(Short.parseShort(String.valueOf(year)));
			exam.setExamPeriod(Short.parseShort("1"));;
			break;
		case 7:
			exam.setExamYear(Short.parseShort(String.valueOf(year)));
			exam.setExamPeriod(Short.parseShort("6"));;
			break;
		case 8:	
			exam.setExamYear(Short.parseShort(String.valueOf(year)));
			exam.setExamPeriod(Short.parseShort("6"));;
			break;
		case 9:
			exam.setExamYear(Short.parseShort(String.valueOf(year)));
			exam.setExamPeriod(Short.parseShort("6"));;
			break;
		case 10:
			exam.setExamYear(Short.parseShort(String.valueOf(year)));
			exam.setExamPeriod(Short.parseShort("6"));;
			break;
		case 11:
			exam.setExamYear(Short.parseShort(String.valueOf(year)));
			exam.setExamPeriod(Short.parseShort("6"));;
			break;
		case 12:
			exam.setExamYear(Short.parseShort(String.valueOf(year)));
			exam.setExamPeriod(Short.parseShort("10"));;
			break;
		}
		return exam;
		}
	
	public boolean isMarked(AlternativeExamOpportunityRecord altExamOpt)
	{
		boolean isMarked=false;
		if (altExamOpt.getFinalMark()!=null && !altExamOpt.getFinalMark().trim().equalsIgnoreCase("") && isInteger(altExamOpt.getFinalMark())){
			if (Integer.parseInt(altExamOpt.getFinalMark())>0 ||
					(Integer.parseInt(altExamOpt.getFinalMark())==0 &&
							altExamOpt.getZeroMarkReason()!=null &&
							!altExamOpt.getZeroMarkReason().equalsIgnoreCase("") &&
					!altExamOpt.getZeroMarkReason().equalsIgnoreCase("N/A"))){
				isMarked=true;
			}
		} 
		return isMarked;
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
	
	public ActionForward unspecified(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response){
		
		FinalMarkConcessionForm fiStudentForm = (FinalMarkConcessionForm ) form;

		log.info("FI Year student concession: unspecified method call -no value for parameter in request");
		
		fiStudentForm.setCurrentPage("home");
		return mapping.findForward("home");

	}
}
