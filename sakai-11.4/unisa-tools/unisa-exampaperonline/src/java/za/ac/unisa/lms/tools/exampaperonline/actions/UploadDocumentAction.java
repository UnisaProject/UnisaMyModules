package za.ac.unisa.lms.tools.exampaperonline.actions;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.email.api.EmailService;

import Expll01h.Abean.Expll01sMntExamPaperLog;

import za.ac.unisa.lms.dao.Xamprd;
import za.ac.unisa.lms.dao.general.DepartmentDAO;
import za.ac.unisa.lms.dao.general.ModuleDAO;
import za.ac.unisa.lms.dao.general.PersonnelDAO;
import za.ac.unisa.lms.dao.general.UserDAO;
import za.ac.unisa.lms.domain.general.Department;
import za.ac.unisa.lms.domain.general.Person;
import za.ac.unisa.lms.domain.general.StudyUnit;
import za.ac.unisa.lms.tools.exampaperonline.dao.ExamPaperOnlineDao;
import za.ac.unisa.lms.tools.exampaperonline.dao.XtlogDao;
import za.ac.unisa.lms.tools.exampaperonline.forms.LinkedPaper;
import za.ac.unisa.lms.tools.exampaperonline.forms.UpdateModuleLog;
import za.ac.unisa.lms.tools.exampaperonline.forms.ExamPaperOnlineForm;
import za.ac.unisa.lms.tools.exampaperonline.forms.ExamPaperOnlineLog;
import za.ac.unisa.lms.tools.exampaperonline.forms.ExamPaperOnlineStatus;
import za.ac.unisa.lms.tools.exampaperonline.forms.Recipient;
import za.ac.unisa.lms.tools.exampaperonline.forms.Rule;
import za.ac.unisa.lms.tools.exampaperonline.forms.Xtloge;
import za.ac.unisa.lms.tools.exampaperonline.forms.CoverDocket;

public class UploadDocumentAction extends LookupDispatchAction{
	
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
		map.put("button.continue","nextStep");
		map.put("button.cancel","cancel");
		map.put("button.back", "prevStep");
		map.put("button.upload", "uploadDocument");
		return map;
	}
	
		
	public ActionForward initial(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		ActionMessages messages = new ActionMessages();
		ExamPaperOnlineForm examPaperOnlineForm = (ExamPaperOnlineForm) form;
		examPaperOnlineForm.setFromAction("UPLOAD");
		
		if (request.getParameter("fromAction")!=null && request.getParameter("fromAction").equalsIgnoreCase("LISTDOCS")){
			examPaperOnlineForm.setExamYear(request.getParameter("examYear"));
			examPaperOnlineForm.setExamPeriod(request.getParameter("examPeriod"));
			//get exam period desc
			for (int i=0; i < examPaperOnlineForm.getListExamPeriods().size();i++){
				Xamprd examperiod = new Xamprd();
				examperiod = (Xamprd)examPaperOnlineForm.getListExamPeriods().get(i);
				if (examperiod.getCode()==Short.parseShort(examPaperOnlineForm.getExamPeriod())){
					examPaperOnlineForm.setExamPeriodDesc(examperiod.getEngDescription());
					i=examPaperOnlineForm.getListExamPeriods().size();
				}
			}
			examPaperOnlineForm.setPaperNo(request.getParameter("paperNo"));
			examPaperOnlineForm.setStudyUnit(request.getParameter("studyUnit"));
			examPaperOnlineForm.setPaperContent(request.getParameter("docType"));
			examPaperOnlineForm.setFromAction("LISTDOCS");
			StudyUnit studyUnit = new StudyUnit();
			ModuleDAO dao = new ModuleDAO();
			studyUnit = dao.getStudyUnit(examPaperOnlineForm.getStudyUnit());
			if (studyUnit.getEngLongDesc() == null){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
								"Invalid Study Unit."));
			} else {
				examPaperOnlineForm.setStudyUnitDesc(studyUnit.getEngLongDesc());
				examPaperOnlineForm.setDepartment(studyUnit.getDepartmentCode());
			}
		}
		
		ExamPaperOnlineDao dao = new ExamPaperOnlineDao();
		ExamPaperOnlineAction action = new ExamPaperOnlineAction();
		LinkedPaper linkedPaper = new LinkedPaper();
		linkedPaper = action.getLinkedPaperStatus(examPaperOnlineForm.getStudyUnit(), 
				examPaperOnlineForm.getExamYear(), 
				examPaperOnlineForm.getExamPeriod());
		if (linkedPaper.getInputStudyUnitStatus().equalsIgnoreCase("LINKED") ){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
							"The module is a linked module. Please upload/access the paper on the mother code " + linkedPaper.getMotherCode()));
		}
		if (!messages.isEmpty()) {
			addErrors(request,messages);
			return mapping.findForward("cancel");
		}		
		examPaperOnlineForm.setEquivalentStr(linkedPaper.getLinkedDesc());
		examPaperOnlineForm.setEquivalentList(linkedPaper.getLinkedPapers());		
		//initialize all fields for upload
		examPaperOnlineForm.setUploadDocument1("");
		examPaperOnlineForm.setUploadDocument2("");
		examPaperOnlineForm.setUploadDocument3("");
		examPaperOnlineForm.setUploadDocument4("");
		examPaperOnlineForm.setUploadAdditionalText("");
		examPaperOnlineForm.setUploadSendersResponse("");
		examPaperOnlineForm.setUploadSelectedRecipientIndex("");
		examPaperOnlineForm.setUploadNrOfDocs("1");
		examPaperOnlineForm.setToAdr("");	
		examPaperOnlineForm.setUploadApprovalStatusRequired(false);
		examPaperOnlineForm.setUploadDocumentDetailRequired(false);
		examPaperOnlineForm.setDocumentsToUploadShown("");	
		examPaperOnlineForm.setUploadCurrentUserRole("");
		examPaperOnlineForm.setUploadPrevRemark("");
		examPaperOnlineForm.setUploadPrevSender("");
		
		
		//get user roles for examination paper
		List userRoles = new ArrayList();
		
		userRoles= getUserRolesForPaper(examPaperOnlineForm.getUser(), 
				examPaperOnlineForm.getStudyUnit(), 
				examPaperOnlineForm.getExamYear(), 
				examPaperOnlineForm.getExamPeriod(),
				examPaperOnlineForm.getPaperNo());
		
		//add examination & print production roles
		for (int i=0; i < examPaperOnlineForm.getListUserId().size(); i++){
			if (!examPaperOnlineForm.getListUserId().get(i).toString().equalsIgnoreCase(examPaperOnlineForm.getUserId())){
				userRoles.add(examPaperOnlineForm.getListUserId().get(i));
			}
		}
		
		examPaperOnlineForm.setListUserRoles(userRoles);
		
		if (userRoles.size()==0){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"You are not authorised to upload a document for this module's examination."));
			addErrors(request,messages);
			return mapping.findForward("cancel");
		}
		
		//determine if first upload		
		examPaperOnlineForm.setFirstUpload(false);
		ExamPaperOnlineStatus currentStatus = new ExamPaperOnlineStatus();

		currentStatus = dao.getExamPaperOnlineStatus( Short.parseShort(examPaperOnlineForm.getExamYear()),
				Short.parseShort(examPaperOnlineForm.getExamPeriod()),
				examPaperOnlineForm.getStudyUnit(),
				Short.parseShort(examPaperOnlineForm.getPaperNo()), 
				examPaperOnlineForm.getPaperContent());	
		
		if (currentStatus.getExamYear()==null || currentStatus.getLastAction().equalsIgnoreCase("RESET")){
			examPaperOnlineForm.setFirstUpload(true);
		}
		
		CoverDocket coverDocket = new CoverDocket();
		
		coverDocket = dao.getCoverDocket(examPaperOnlineForm.getExamYear(), 
				examPaperOnlineForm.getExamPeriod(),
				examPaperOnlineForm.getStudyUnit(),
				examPaperOnlineForm.getPaperNo());
		
		//determine if user is allowed to upload document for the first time 
		//only first and second examiners are allowed to upload document initially		
		if (examPaperOnlineForm.isFirstUpload()){
			boolean uploadAllowed = false;
			for (int i=0; i < userRoles.size(); i++){
				if (userRoles.get(i).toString().equalsIgnoreCase("1ST_EXAM") ||
					userRoles.get(i).toString().equalsIgnoreCase("2ND_EXAM")){
					uploadAllowed = true;				
					if (userRoles.get(i).toString().equalsIgnoreCase("1ST_EXAM")){
						examPaperOnlineForm.setUploadCurrentUserRole("1ST_EXAM");
					}
				}
			}	
			
			if (!uploadAllowed){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"You are not authorised to do the initial upload for this module's examination."));
				addErrors(request,messages);
				return mapping.findForward("cancel");
			}	
			if (examPaperOnlineForm.getUploadCurrentUserRole()==null || examPaperOnlineForm.getUploadCurrentUserRole().equalsIgnoreCase("")){
				examPaperOnlineForm.setUploadCurrentUserRole("2ND_EXAM");
			}
			
			if (coverDocket.getExamYear()==null){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
								"First create the examination paper cover docket, docket does not exists."));
				addErrors(request,messages);
				return mapping.findForward("cancel");
			}
		} else {
			//if not first upload, determine if user is the last user to which this document was sent and if user have retrieved document
			if (!currentStatus.getAtUser().equalsIgnoreCase(examPaperOnlineForm.getUserId())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"You do not currently have control over this examination paper/memorandum to upload and send it to another user."));
				addErrors(request,messages);
				return mapping.findForward("cancel");
			}
			ExamPaperOnlineLog log = new ExamPaperOnlineLog();
			log = dao.getExamPaperOnlineLog(Short.parseShort(examPaperOnlineForm.getExamYear()),
					Short.parseShort(examPaperOnlineForm.getExamPeriod()),
					examPaperOnlineForm.getStudyUnit(),
					Short.parseShort(examPaperOnlineForm.getPaperNo()), 
					examPaperOnlineForm.getPaperContent(),
					currentStatus.getLastSeqNr());
			if (log.getAction().equalsIgnoreCase("SEND")){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"You must first retrieve this examination paper/memorandum before you can upload and send it to another user."));
				addErrors(request,messages);
				return mapping.findForward("cancel");
			}		
			examPaperOnlineForm.setUploadCurrentUserRole(currentStatus.getAtRole());
		}
		
		//if not first upload get nr of docs submitted from coverdocket
		if (!examPaperOnlineForm.isFirstUpload() && 
				examPaperOnlineForm.getPaperContent().equalsIgnoreCase("QUESTION")){
			if (coverDocket.getNrDocsSubmitted()!=null && coverDocket.getNrDocsSubmitted()>0){
				examPaperOnlineForm.setUploadNrOfDocs(coverDocket.getNrDocsSubmitted().toString());
			}
		}
		//determine if nr of documents submitted has been set on the coverdocket
		/*if (examPaperOnlineForm.getPaperContent().equalsIgnoreCase("QUESTION")){
			if (coverDocket.getNrDocsSubmitted()==null || coverDocket.getNrDocsSubmitted()==0){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
								"Please update the cover docket with the number of documents to be submitted."));
				addErrors(request,messages);
				return mapping.findForward("cancel");
			}
			examPaperOnlineForm.setUploadNrOfDocs(coverDocket.getNrDocsSubmitted().toString());
		}*/	
		
		//get potential list of roles to send document to
		List potentialRolesToSendTo = new ArrayList();		
		List tempList = new ArrayList();
		tempList=dao.getPotentialRolesToSendTo(examPaperOnlineForm.getPaperContent(), examPaperOnlineForm.getUploadCurrentUserRole());
		for (int j=0; j<tempList.size(); j++){
			boolean inPotentialList = false;
			for (int k=0; k < potentialRolesToSendTo.size(); k++){
				if (tempList.get(j).toString().equalsIgnoreCase(potentialRolesToSendTo.get(k).toString())){
					inPotentialList = true;
					k = potentialRolesToSendTo.size();
				}
			}
			if (!inPotentialList){
				potentialRolesToSendTo.add(tempList.get(j));
			}
		}
		
		//get all persons linked to roles
		List recipients = new ArrayList();
		PersonnelDAO persdao = new PersonnelDAO();
		for (int i=0; i < potentialRolesToSendTo.size();i++){
			List userList = new ArrayList();
			if (potentialRolesToSendTo.get(i).toString().equalsIgnoreCase("1ST_EXAM") ||
				potentialRolesToSendTo.get(i).toString().equalsIgnoreCase("2ND_EXAM") ||
				potentialRolesToSendTo.get(i).toString().equalsIgnoreCase("EXT_EXAM")){
				
				userList = dao.getExaminersForModulePerType(examPaperOnlineForm.getStudyUnit(),
												 examPaperOnlineForm.getExamYear(),
												 examPaperOnlineForm.getExamPeriod(), 
												 examPaperOnlineForm.getPaperNo(),
												 potentialRolesToSendTo.get(i).toString());
				
				for (int j=0; j < userList.size(); j++){
					String persno = userList.get(j).toString();
					/*//Exclude user from potential recipient list
					if (!examPaperOnlineForm.getUser().getPersonnelNumber().trim().equalsIgnoreCase(persno.trim())){
						Person user = new Person();
						Recipient recipient = new Recipient();
						user = persdao.getPerson((Integer.parseInt(userList.get(j).toString())));
						recipient.setPerson(user);
						recipient.setRole(potentialRolesToSendTo.get(i).toString());
						recipients.add(recipient);
					}	*/		
					Person user = new Person();
					Recipient recipient = new Recipient();
					user = persdao.getPerson((Integer.parseInt(userList.get(j).toString())));
					recipient.setPerson(user);
					recipient.setRole(potentialRolesToSendTo.get(i).toString());
					recipients.add(recipient);
				}
			}
			if (potentialRolesToSendTo.get(i).toString().equalsIgnoreCase("COD")){
				//get COD and acting COD's
				DepartmentDAO dptDAO = new DepartmentDAO();
				Department dpt = new Department();
				userList = new ArrayList();
				
				dpt = dptDAO.getDepartment(examPaperOnlineForm.getDepartment());
				userList = dpt.getActingCodList();
				boolean codFoundInActingList = false;
				for (int j=0; j < userList.size(); j++){
					if (((Person)userList.get(j)).getNovellUserId().equalsIgnoreCase(((Person)dpt.getCod()).getNovellUserId())){
						codFoundInActingList = true;
						j = userList.size();
					}					
				}
				if (!codFoundInActingList){
					userList.add(dpt.getCod());
				}				
				for (int j=0; j < userList.size(); j++){
					Recipient recipient = new Recipient();
					recipient.setPerson((Person)userList.get(j));
					recipient.setRole(potentialRolesToSendTo.get(i).toString());
					recipients.add(recipient);
				}				
			}
			if (potentialRolesToSendTo.get(i).toString().equalsIgnoreCase("EXAMS_QA1")){
				Recipient recipient = new Recipient();
				recipient.setPerson(null);
				recipient.setRole("EXAMS_QA1");
				recipients.add(recipient);
				boolean examsQa1User=false;				
				for (int j=0; j < examPaperOnlineForm.getListUserId().size();j++){
					if (examPaperOnlineForm.getListUserId().get(j).toString().equalsIgnoreCase("EXAMS_QA1")){
						examsQa1User=true;
					}
				}
				if (examsQa1User && examPaperOnlineForm.getUploadCurrentUserRole().equalsIgnoreCase("EXAMS_QA1")){
					userList = new ArrayList();
					userList = dao.getExamsQa1Users();
					
					for (int j=0; j < userList.size(); j++){
						String persno = userList.get(j).toString();
						Person user = new Person();
						recipient = new Recipient();
						user = persdao.getPerson((Integer.parseInt(userList.get(j).toString())));
						recipient.setPerson(user);
						recipient.setRole(potentialRolesToSendTo.get(i).toString());
						recipients.add(recipient);
					}	
				}
			}
			if (potentialRolesToSendTo.get(i).toString().equalsIgnoreCase("EXAMS_QA2")){
				Recipient recipient = new Recipient();
				recipient.setPerson(null);
				recipient.setRole("EXAMS_QA2");
				recipients.add(recipient);
				boolean examsQa2User=false;				
				for (int j=0; j < examPaperOnlineForm.getListUserId().size();j++){
					if (examPaperOnlineForm.getListUserId().get(j).toString().equalsIgnoreCase("EXAMS_QA2")){
						examsQa2User=true;
					}
				}
				if (examsQa2User && examPaperOnlineForm.getUploadCurrentUserRole().equalsIgnoreCase("EXAMS_QA2")){
					userList = new ArrayList();
					userList = dao.getExamsQa2Users();
					
					for (int j=0; j < userList.size(); j++){
						String persno = userList.get(j).toString();
						Person user = new Person();
						recipient = new Recipient();
						user = persdao.getPerson((Integer.parseInt(userList.get(j).toString())));
						recipient.setPerson(user);
						recipient.setRole(potentialRolesToSendTo.get(i).toString());
						recipients.add(recipient);
					}
				}
			}
			if (potentialRolesToSendTo.get(i).toString().equalsIgnoreCase("PRINT_PROD")){
				Recipient recipient = new Recipient();
				recipient.setPerson(null);
				recipient.setRole("PRINT_PROD");
				recipients.add(recipient);
				boolean printProdUser=false;		
				
				for (int j=0; j < examPaperOnlineForm.getListUserId().size();j++){
					if (examPaperOnlineForm.getListUserId().get(j).toString().equalsIgnoreCase("PRINT_PROD")){
						printProdUser=true;
					}
				}
				if (printProdUser && examPaperOnlineForm.getUploadCurrentUserRole().equalsIgnoreCase("PRINT_PROD")){
					userList = new ArrayList();
					userList = dao.getPrintProdUsers();
					
					for (int j=0; j < userList.size(); j++){
						String persno = userList.get(j).toString();
						Person user = new Person();
						recipient = new Recipient();
						user = persdao.getPerson((Integer.parseInt(userList.get(j).toString())));
						recipient.setPerson(user);
						recipient.setRole(potentialRolesToSendTo.get(i).toString());
						recipients.add(recipient);
					}	
				}
			}
			if (potentialRolesToSendTo.get(i).toString().equalsIgnoreCase("ARCHIVE")){
				Recipient recipient = new Recipient();
				recipient.setPerson(null);
				recipient.setRole("ARCHIVE");
				recipients.add(recipient);
			}
		}
		if (recipients.size()==0){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"Nobody is linked to whom this document can be sent."));
			addErrors(request,messages);
			return mapping.findForward("cancel");
		}
		examPaperOnlineForm.setListRecipients(recipients);
		
		return mapping.findForward("uploadStep1");	
	}
	
	public ActionForward cancel(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		ExamPaperOnlineForm examPaperOnlineForm = (ExamPaperOnlineForm) form;
		
//		 examPaperOnlineForm.setExamPeriod("");
//		 examPaperOnlineForm.setExamYear("");
//		 examPaperOnlineForm.setStudyUnit("");
//		 examPaperOnlineForm.setPaperNo("");
		 if (examPaperOnlineForm.getFromAction().equalsIgnoreCase("LISTDOCS")){
			 return mapping.findForward("listdocs");
		 }else{
			 return mapping.findForward("cancel");
		 }
			 	
	}
	
	public ActionForward prevStep(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String backward="";
		ExamPaperOnlineForm examPaperOnlineForm = (ExamPaperOnlineForm) form;
		
		if (examPaperOnlineForm.getCurrentPage().equalsIgnoreCase("uploadStep1")) {
			if (examPaperOnlineForm.getFromAction().equalsIgnoreCase("LISTDOCS")){
				backward="listdocs";
			}else{
				backward="cancel";
			}			
		}
		if (examPaperOnlineForm.getCurrentPage().equalsIgnoreCase("uploadStep2")) {
			backward="uploadStep1";
		}
		if (examPaperOnlineForm.getCurrentPage().equalsIgnoreCase("uploadStep3")) {
			backward="uploadStep2";
		}
		
		return mapping.findForward(backward);	
	}
	
	public ActionForward nextStep(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String forward="";
		ExamPaperOnlineForm examPaperOnlineForm = (ExamPaperOnlineForm) form;
		
		if (examPaperOnlineForm.getCurrentPage().equalsIgnoreCase("uploadStep1")) {
			forward=displayUploadStep2(mapping, form, request, response);
		}
		if (examPaperOnlineForm.getCurrentPage().equalsIgnoreCase("uploadStep2")) {
			forward=displayUploadStep3(mapping, form, request, response);
		}
		
		return mapping.findForward(forward);	
	}
	
	public ActionForward uploadDocument(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		ActionMessages messages = new ActionMessages();
		ExamPaperOnlineForm examPaperOnlineForm = (ExamPaperOnlineForm) form;
		
		ExamPaperOnlineDao dao = new ExamPaperOnlineDao();		
		
		//get the current status of the mother record
		ExamPaperOnlineStatus currentStatus = new ExamPaperOnlineStatus();
		String owner = examPaperOnlineForm.getUserId();
		currentStatus = dao.getExamPaperOnlineStatus(Short.parseShort(examPaperOnlineForm.getExamYear()), 
				Short.parseShort(examPaperOnlineForm.getExamPeriod()), 
				examPaperOnlineForm.getStudyUnit(), 
				Short.parseShort(examPaperOnlineForm.getPaperNo()), 
				examPaperOnlineForm.getPaperContent());
		List updateList = new ArrayList();
		
		//Test if equivalents are in the same status
		//Get the next sequence numbers for the equivalents
		for (int i=0; i < examPaperOnlineForm.getEquivalentList().size(); i++){
			String eqv = examPaperOnlineForm.getEquivalentList().get(i).toString().toUpperCase().trim();
			ExamPaperOnlineStatus eqvStatus = new ExamPaperOnlineStatus();			
			eqvStatus = dao.getExamPaperOnlineStatus(Short.parseShort(examPaperOnlineForm.getExamYear()), 
					Short.parseShort(examPaperOnlineForm.getExamPeriod()), 
					eqv, 
					Short.parseShort(examPaperOnlineForm.getPaperNo()), 
					examPaperOnlineForm.getPaperContent());
			UpdateModuleLog rec = new UpdateModuleLog();
			rec.setStudyUnit(eqv);
			rec.setType("EQV");
			if (eqvStatus.getLastSeqNr()==null){
				rec.setSeqNr(1);
				rec.setStatusAction("INSERT");
			}else{
				rec.setSeqNr(eqvStatus.getLastSeqNr() + 1);
				rec.setStatusAction("UPDATE");
			}
			if (currentStatus.getLastAction()==null){
				if (eqvStatus.getLastAction()!=null && !eqvStatus.getLastAction().equalsIgnoreCase("RESET")){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"Upload failed, mother code and linked papers is not in the same state."));
				}
			}else{
				
				if (eqvStatus.getLastAction()== null ||
					(eqvStatus.getLastAction().equalsIgnoreCase(currentStatus.getLastAction()) &&
						eqvStatus.getLastAction().equalsIgnoreCase("RESET"))||
					(eqvStatus.getLastAction().equalsIgnoreCase(currentStatus.getLastAction()) &&
						eqvStatus.getFromUser().equalsIgnoreCase(currentStatus.getFromUser()) &&
						eqvStatus.getFromRole().equalsIgnoreCase(currentStatus.getFromRole()) &&
						eqvStatus.getAtUser().equalsIgnoreCase(currentStatus.getAtUser()) &&
						eqvStatus.getAtRole().equalsIgnoreCase(currentStatus.getAtRole()))){
					//ok
				}else{
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"Upload failed, mother code and linked papers is not in the same state."));
				}
			}
			updateList.add(rec);
		}		
		if (!messages.isEmpty()) {
			addErrors(request,messages);			
			request.setAttribute("toRole", request.getParameter("toRole"));
			request.setAttribute("toRecipientName", request.getParameter("toRecipientName"));
			request.setAttribute("xpoToAdr", request.getParameter("xpoToAdr"));	
			request.setAttribute("xpoExamYear", examPaperOnlineForm.getExamYear());
			request.setAttribute("xpoExamPeriod", examPaperOnlineForm.getExamPeriod());
			request.setAttribute("xpoStudyUnit", examPaperOnlineForm.getStudyUnit());
			request.setAttribute("xpoPaperNo", examPaperOnlineForm.getPaperNo());
			request.setAttribute("xpoUploadDocument1", examPaperOnlineForm.getUploadDocument1());
			request.setAttribute("xpoUploadDocument2", examPaperOnlineForm.getUploadDocument2());
			request.setAttribute("xpoUploadDocument3", examPaperOnlineForm.getUploadDocument3());
			request.setAttribute("xpoUploadDocument4", examPaperOnlineForm.getUploadDocument4());	
			return mapping.findForward("uploadStep3");
		}		
		
		UpdateModuleLog rec = new UpdateModuleLog();
		//if no status record set current role to OWNER and next sequence number to 1;
		rec.setStudyUnit(examPaperOnlineForm.getStudyUnit());
		rec.setType("MOTHER");
		if (currentStatus.getExamYear()==null) {
			rec.setSeqNr(1);
			rec.setStatusAction("INSERT");
		} else {
			rec.setSeqNr(currentStatus.getLastSeqNr()+1);
			rec.setStatusAction("UPDATE");
			owner=currentStatus.getOwner();			
		}
		updateList.add(rec);
		
		//get current date
		Calendar calendar = Calendar.getInstance();	
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date(calendar.getTime().getTime());
		String stringDate=formatter.format(date);			
		java.sql.Timestamp timestamp = new java.sql.Timestamp(calendar.getTime().getTime());
		String tempTimestamp = timestamp.toString();		
		
		Recipient recipient = new Recipient();
		recipient = (Recipient)examPaperOnlineForm.getListRecipients().get(Integer.parseInt(examPaperOnlineForm.getUploadSelectedRecipientIndex()));
		
		//Set up new Log record to be written to mother and equivelant codes.
		ExamPaperOnlineLog newLog = new ExamPaperOnlineLog();
		newLog.setExamYear(Short.parseShort(examPaperOnlineForm.getExamYear()));
		newLog.setExamPeriod(Short.parseShort(examPaperOnlineForm.getExamPeriod()));
		newLog.setStudyUnit(examPaperOnlineForm.getStudyUnit());
		newLog.setPaperNo(Short.parseShort(examPaperOnlineForm.getPaperNo()));
		newLog.setDocType(examPaperOnlineForm.getPaperContent());
		newLog.setAction("SEND");
		newLog.setCurrentUser(examPaperOnlineForm.getUser().getNovellUserId());
		newLog.setCurrentRole(examPaperOnlineForm.getUploadCurrentUserRole());
		newLog.setActionedRole(recipient.getRole());
		if (recipient.getPerson()!=null){
			newLog.setActionedUser(recipient.getPerson().getNovellUserId());
		}else{
			newLog.setActionedUser(recipient.getRole());
		}
		newLog.setApprovalStatus(examPaperOnlineForm.getUploadSendersResponse());
		newLog.setMessage(examPaperOnlineForm.getUploadAdditionalText());
		newLog.setUpdatedOn(tempTimestamp);
		newLog.setResetFlag("N");
		
		//Set up new status status record to be written to mother and equivelant codes.
		ExamPaperOnlineStatus newStatus = new ExamPaperOnlineStatus();
		newStatus.setExamYear(Short.parseShort(examPaperOnlineForm.getExamYear()));
		newStatus.setExamPeriod(Short.parseShort(examPaperOnlineForm.getExamPeriod()));
		newStatus.setStudyUnit(examPaperOnlineForm.getStudyUnit());
		newStatus.setPaperNo(Short.parseShort(examPaperOnlineForm.getPaperNo()));
		newStatus.setDocType(examPaperOnlineForm.getPaperContent());
		newStatus.setOwner(owner);
		newStatus.setUpdatedOn(newLog.getUpdatedOn());
		newStatus.setLastAction(newLog.getAction());
		newStatus.setFromUser(newLog.getCurrentUser());
		newStatus.setFromRole(newLog.getCurrentRole());
		if (recipient.getPerson()!=null){
			newStatus.setAtUser(recipient.getPerson().getNovellUserId());
		}else{
			newStatus.setAtUser(recipient.getRole());
		}
		newStatus.setAtRole(recipient.getRole());

		//If send to print production retrieve the quantity
		//Change start 20110117
		Integer printQuantity=0;
		if (recipient.getRole().equalsIgnoreCase("PRINT_PROD")){
			printQuantity = getPrintQuantity(newLog.getExamYear(), newLog.getExamPeriod(), newLog.getStudyUnit(), newLog.getPaperNo());
		}	
		//Change end 20110117
		
		//Uploaded to PRINT PRODUCTION, from EXAMS_QA2
		//Uploaded to Academic department from EXAMS_QA2
		XtlogDao xtlogDao = new XtlogDao();
		Xtloge newXtloge = new Xtloge();
		if (currentStatus.getAtRole()!=null 
				&& (currentStatus.getAtRole().equalsIgnoreCase("EXAMS_QA2") 
				|| currentStatus.getAtRole().equalsIgnoreCase("EXAMS_QA1"))
				&& (recipient.getRole().equalsIgnoreCase("PRINT_PROD")
				|| recipient.getRole().equalsIgnoreCase("COD")
				|| recipient.getRole().equalsIgnoreCase("1ST_EXAM")
				|| recipient.getRole().equalsIgnoreCase("2ND_EXAM")
				|| recipient.getRole().equalsIgnoreCase("EXT_EXAM"))){
			Xtloge xtloge = new Xtloge();
			xtloge = xtlogDao.getXtloge(newLog.getExamYear(), newLog.getExamPeriod(), newLog.getStudyUnit(), newLog.getPaperNo());
			if (xtloge.getExamYear()==null){
				// Error: xtloge does not exists 						
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
						"Xtloge record does not exists, contact support."));
				addErrors(request,messages);
				return mapping.findForward("retrieveStep1");	
			}
			//Uploaded to PRINT PRODUCTION, from EXAMS_QA2
			newXtloge.setExamYear(newLog.getExamYear());
			newXtloge.setExamPeriod(newLog.getExamPeriod());
			newXtloge.setStudyUnit(newLog.getStudyUnit());
			newXtloge.setPaperNo(newLog.getPaperNo());
			if (recipient.getRole().equalsIgnoreCase("PRINT_PROD")){
				if (xtloge.getExamYear()!=null  
						&& (xtloge.getDateToPrint().equalsIgnoreCase(null)  
						|| xtloge.getDateToPrint().equalsIgnoreCase("") 
						|| xtloge.getDateToPrint().equalsIgnoreCase("0001-01-01"))){			
					//First time send to print production
					//xtloge does exists - update xtloge set date_to_print	
					newXtloge.setDateReceived(xtloge.getDateReceived());
					newXtloge.setDateToDept(xtloge.getDateToDept());
					newXtloge.setDateFromDept(xtloge.getDateFromDept());
					newXtloge.setDateToPrint(stringDate);
					//Change start - 20110117
					//record.setQuantToPrint(xtloge.getQuantToPrint());
					//record.setQuantCalcOn(xtloge.getQuantCalcOn());
					newXtloge.setQuantToPrint(printQuantity.toString());
					newXtloge.setQuantCalcOn(stringDate);
					//Change end - 20110117
					newXtloge.setDate2ToPrint(xtloge.getDate2ToPrint());
					newXtloge.setQuant2ToPrint(xtloge.getQuant2ToPrint());
					newXtloge.setQuant2CalcOn(xtloge.getQuant2CalcOn());	
					newXtloge.setElectronicFlag(xtloge.getElectronicFlag());
				}			
				if (xtloge.getExamYear()!=null  
						&& !xtloge.getDateToPrint().equalsIgnoreCase(null)  
						&& !xtloge.getDateToPrint().equalsIgnoreCase("") 
						&& !xtloge.getDateToPrint().equalsIgnoreCase("0001-01-01")){			
					//Second time send to print production
					//xtloge does exists - update xtloge date2_to_print						
					newXtloge.setDateReceived(xtloge.getDateReceived());
					newXtloge.setDateToDept(xtloge.getDateToDept());
					newXtloge.setDateFromDept(xtloge.getDateFromDept());
					newXtloge.setDateToPrint(xtloge.getDateToPrint());
					newXtloge.setQuantToPrint(xtloge.getQuantToPrint());
					newXtloge.setQuantCalcOn(xtloge.getQuantCalcOn());
					newXtloge.setDate2ToPrint(stringDate);
					//Change start - 20110117
					//record.setQuant2ToPrint("0");
					//record.setQuant2CalcOn("00010101");
					newXtloge.setQuant2ToPrint(printQuantity.toString());
					newXtloge.setQuant2CalcOn(stringDate);
					//Change end - 20110117
					newXtloge.setElectronicFlag(xtloge.getElectronicFlag());					
				}
			}
			//Uploaded to Academic department from EXAMS_QA2
			if (recipient.getRole().equalsIgnoreCase("COD")
					|| recipient.getRole().equalsIgnoreCase("1ST_EXAM")
					|| recipient.getRole().equalsIgnoreCase("2ND_EXAM")
					|| recipient.getRole().equalsIgnoreCase("EXT_EXAM")){
				//xtloge does exists - update xtloge date_to_dept	
				newXtloge.setDateReceived(xtloge.getDateReceived());
				newXtloge.setDateToDept(stringDate);
				newXtloge.setDateFromDept(xtloge.getDateFromDept());
				newXtloge.setDateToPrint(xtloge.getDateToPrint());
				newXtloge.setQuantToPrint(xtloge.getQuantToPrint());
				newXtloge.setQuantCalcOn(xtloge.getQuantCalcOn());
				newXtloge.setDate2ToPrint(xtloge.getDate2ToPrint());
				newXtloge.setQuant2ToPrint(xtloge.getQuant2ToPrint());
				newXtloge.setQuant2CalcOn(xtloge.getQuant2CalcOn());	
				newXtloge.setElectronicFlag(xtloge.getElectronicFlag());							
			}			
		}	
		
		//call dao to do all inserts for upload
		//update xtloge if necessary
		//insert log record for mother code and equivalents
		//insert status record for mother code and equivalents
		String errMsg="";
		errMsg = dao.updateRecordsOnAction(updateList, newStatus, newLog, null, newXtloge, Integer.parseInt(examPaperOnlineForm.getUploadNrOfDocs()));
		
		//Send email if 1st examiner,2nd examiner or external examiner
		//To do remove COD - replace emails send to COD with batch program
		if (recipient.getRole().equalsIgnoreCase("1ST_EXAM")
		|| recipient.getRole().equalsIgnoreCase("2ND_EXAM")
		|| recipient.getRole().equalsIgnoreCase("EXT_EXAM")
		|| recipient.getRole().equalsIgnoreCase("COD")){
			String toAddress="";
			String addressee="";
			String course="";
			course = examPaperOnlineForm.getStudyUnit() + "-" + examPaperOnlineForm.getExamYear() + "-" + examPaperOnlineForm.getExamPeriod();
			if (examPaperOnlineForm.getPaperContent().equalsIgnoreCase("QUESTION")){
				course = course + "-" + "PAPER" + "-" + examPaperOnlineForm.getPaperNo();
			}
			if (examPaperOnlineForm.getPaperContent().equalsIgnoreCase("MEMO")){
				course = course + "-" + examPaperOnlineForm.getPaperContent();
			}
			toAddress=recipient.getPerson().getEmailAddress();
			addressee=recipient.getPerson().getName();
			//do not send email on dev & qa -default email to pretoj@unisa.ac.za
			String serverpath = ServerConfigurationService.getServerUrl();
			if (serverpath.contains("mydev") || serverpath.contains("myqa")){
				if (recipient.getPerson().getNovellUserId().equalsIgnoreCase("PRETOJ") ||
						recipient.getPerson().getNovellUserId().equalsIgnoreCase("BOSHOE") ||
						recipient.getPerson().getNovellUserId().equalsIgnoreCase("MARXH")){
					//send email to addressee
				}else{
					toAddress="pretoj@unisa.ac.za";
				}
				
			}	
			sendNotifyEmail(toAddress, addressee, course);	
			
		}
		
		messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage",
							"Examination paper/Memorandum has been successfully uploaded."));
		addMessages(request,messages);
		
//		examPaperOnlineForm.setExamPeriod("");
//		examPaperOnlineForm.setExamYear("");
//		examPaperOnlineForm.setStudyUnit("");
//		examPaperOnlineForm.setPaperNo("");
//		examPaperOnlineForm.setPaperContent("");
		
		 if (examPaperOnlineForm.getFromAction().equalsIgnoreCase("LISTDOCS")){
			 return mapping.findForward("listdocs");
		 }else{
			 return mapping.findForward("cancel");
		 }
	}
	
	public String displayUploadStep2(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		ActionMessages messages = new ActionMessages();
		ExamPaperOnlineForm examPaperOnlineForm = (ExamPaperOnlineForm) form;
		
		//read xporul to determine if approval status is required and which documents must be uploaded
		Recipient recipient = new Recipient();		
		recipient = (Recipient) examPaperOnlineForm.getListRecipients().get(Integer.parseInt(examPaperOnlineForm.getUploadSelectedRecipientIndex()));
				
		Rule rule = new Rule();
		ExamPaperOnlineDao dao = new ExamPaperOnlineDao();
		
		examPaperOnlineForm.setUploadApprovalStatusRequired(false);
		examPaperOnlineForm.setUploadDocumentDetailRequired(false);
		rule = dao.getRule("SEND", examPaperOnlineForm.getPaperContent(), examPaperOnlineForm.getUploadCurrentUserRole(), recipient.getRole());
		examPaperOnlineForm.setDocumentsToUploadShown(rule.getDocsShown());
		if (rule.getApprovalRequired().equalsIgnoreCase("Y")){
			examPaperOnlineForm.setUploadApprovalStatusRequired(true);
		}		
		if (examPaperOnlineForm.isFirstUpload()){
			examPaperOnlineForm.setUploadDocumentDetailRequired(true);
		}
		
		if (recipient.getRole().equalsIgnoreCase("COD") &&
				examPaperOnlineForm.getPaperContent().equalsIgnoreCase("QUESTION") &&
	    (examPaperOnlineForm.getUploadCurrentUserRole().equalsIgnoreCase("1ST_EXAM")||
	    		examPaperOnlineForm.getUploadCurrentUserRole().equalsIgnoreCase("2ND_EXAM")||
	    		examPaperOnlineForm.getUploadCurrentUserRole().equalsIgnoreCase("EXT_EXAM"))){
			examPaperOnlineForm.setUploadDocumentDetailRequired(true);			
		}
		
		//reset upload fields step2
		examPaperOnlineForm.setUploadDocument1("");
		examPaperOnlineForm.setUploadDocument2("");
		examPaperOnlineForm.setUploadDocument3("");
		examPaperOnlineForm.setUploadDocument4("");
		examPaperOnlineForm.setUploadSendersResponse("");
		examPaperOnlineForm.setUploadAdditionalText("");
		examPaperOnlineForm.setUploadPrevRemark("");
		examPaperOnlineForm.setUploadPrevSender("");
		
		//get previous send info
		if (examPaperOnlineForm.isFirstUpload()){
			//do nothing
		}else{
			Integer seqNr = dao.getLastLogSeqNrDocSendnotRetracted(Short.parseShort(examPaperOnlineForm.getExamYear()), 
					Short.parseShort(examPaperOnlineForm.getExamPeriod()), 
					examPaperOnlineForm.getStudyUnit(), 
					Short.parseShort(examPaperOnlineForm.getPaperNo()), 
					examPaperOnlineForm.getPaperContent());
			ExamPaperOnlineLog lastSendLog = new ExamPaperOnlineLog();
			lastSendLog = dao.getExamPaperOnlineLog(Short.parseShort(examPaperOnlineForm.getExamYear()), 
					Short.parseShort(examPaperOnlineForm.getExamPeriod()), 
					examPaperOnlineForm.getStudyUnit(), 
					Short.parseShort(examPaperOnlineForm.getPaperNo()),  
					examPaperOnlineForm.getPaperContent(), 
					seqNr);
			
			examPaperOnlineForm.setUploadPrevRemark(lastSendLog.getMessage());
			if (lastSendLog.getCurrentUser()!=null){
				UserDAO userDao = new UserDAO();
				Person fromUser = new Person();
				fromUser = userDao.getPerson(lastSendLog.getCurrentUser());
				
				examPaperOnlineForm.setUploadPrevSender(fromUser.getName());
			} else {
				examPaperOnlineForm .setUploadPrevSender("");
			}		
		}		
		
		return "uploadStep2";
		
	}
	
	public String displayUploadStep3(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		ActionMessages messages = new ActionMessages();
		ExamPaperOnlineForm examPaperOnlineForm = (ExamPaperOnlineForm) form;
		
		if (examPaperOnlineForm.isUploadDocumentDetailRequired()){
			if (examPaperOnlineForm.getUploadNrOfDocs().equalsIgnoreCase("1")){
				if (((examPaperOnlineForm.getUploadDocument1()==null || examPaperOnlineForm.getUploadDocument1().equalsIgnoreCase("")) && examPaperOnlineForm.getDocumentsToUploadShown().equalsIgnoreCase("edit"))||
						((examPaperOnlineForm.getUploadDocument3()==null || examPaperOnlineForm.getUploadDocument3().equalsIgnoreCase("")) && examPaperOnlineForm.getDocumentsToUploadShown().equalsIgnoreCase("print"))){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"The document to upload is required."));
				}			
			}	
			if (examPaperOnlineForm.getUploadNrOfDocs().equalsIgnoreCase("2")){
				if (((examPaperOnlineForm.getUploadDocument1()==null || examPaperOnlineForm.getUploadDocument1().equalsIgnoreCase("") ||
						examPaperOnlineForm.getUploadDocument2()==null || examPaperOnlineForm.getUploadDocument2().equalsIgnoreCase("")) && 
						examPaperOnlineForm.getDocumentsToUploadShown().equalsIgnoreCase("edit")) ||
						((examPaperOnlineForm.getUploadDocument3()==null || examPaperOnlineForm.getUploadDocument3().equalsIgnoreCase("") ||
								examPaperOnlineForm.getUploadDocument4()==null || examPaperOnlineForm.getUploadDocument4().equalsIgnoreCase("")) && 
								examPaperOnlineForm.getDocumentsToUploadShown().equalsIgnoreCase("print"))){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"The document to upload in the primary and secondary language is required."));
				}	
				if ((examPaperOnlineForm.getUploadDocument1().equalsIgnoreCase(examPaperOnlineForm.getUploadDocument2()) &&
						examPaperOnlineForm.getDocumentsToUploadShown().equalsIgnoreCase("edit")) ||
						(examPaperOnlineForm.getUploadDocument3().equalsIgnoreCase(examPaperOnlineForm.getUploadDocument4()) &&
						examPaperOnlineForm.getDocumentsToUploadShown().equalsIgnoreCase("print"))){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"The primary language document should be different from the secondary language document."));
				}
			}
		}
		if (examPaperOnlineForm.getDocumentsToUploadShown().equalsIgnoreCase("print")){
			if ((examPaperOnlineForm.getUploadDocument3()!=null && !examPaperOnlineForm.getUploadDocument3().equalsIgnoreCase("") && !examPaperOnlineForm.getUploadDocument3().substring(examPaperOnlineForm.getUploadDocument3().lastIndexOf(".")+1,examPaperOnlineForm.getUploadDocument3().length()).equalsIgnoreCase("tif"))||
				(examPaperOnlineForm.getUploadDocument4()!=null && !examPaperOnlineForm.getUploadDocument4().equalsIgnoreCase("") && !examPaperOnlineForm.getUploadDocument4().substring(examPaperOnlineForm.getUploadDocument4().lastIndexOf(".")+1,examPaperOnlineForm.getUploadDocument4().length()).equalsIgnoreCase("tif"))){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"The documents to upload must be in a 'tif' format."));
			
			}
		}
		if (examPaperOnlineForm.isUploadApprovalStatusRequired()){
			if (examPaperOnlineForm.getUploadSendersResponse()==null || examPaperOnlineForm.getUploadSendersResponse().equalsIgnoreCase("")){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"Approval status is required."));
			}
		}
		if (examPaperOnlineForm.getUploadAdditionalText()==null || examPaperOnlineForm.getUploadAdditionalText().trim().equalsIgnoreCase("")){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage",
							"You are required to communicate something to the recipient."));
		}	
	
		if (!messages.isEmpty()) {
			addErrors(request,messages);
			return "uploadStep2";
		}
		
		String toRole = "";
		String toRecipientName="";
		String toRecipientNetworkCode="";
		
		Recipient recipient = new Recipient();
		
		recipient = (Recipient) examPaperOnlineForm.getListRecipients().get(Integer.parseInt(examPaperOnlineForm.getUploadSelectedRecipientIndex()));
		
		if (recipient.getPerson()==null){
			toRole = recipient.getRole();	
			toRecipientNetworkCode = recipient.getRole();
		}else{
			toRole = recipient.getRole();
			toRecipientName = recipient.getPerson().getName();
			toRecipientNetworkCode = recipient.getPerson().getNovellUserId();
		}		
		
		request.setAttribute("toRole", toRole);
		request.setAttribute("toRecipientName", toRecipientName);
		request.setAttribute("xpoToAdr", toRecipientNetworkCode);	
		request.setAttribute("xpoExamYear", examPaperOnlineForm.getExamYear());
		request.setAttribute("xpoExamPeriod", examPaperOnlineForm.getExamPeriod());
		request.setAttribute("xpoStudyUnit", examPaperOnlineForm.getStudyUnit());
		request.setAttribute("xpoPaperNo", examPaperOnlineForm.getPaperNo());
		request.setAttribute("xpoDocType", examPaperOnlineForm.getPaperContent());
		request.setAttribute("xpoUploadDocument1", examPaperOnlineForm.getUploadDocument1());
		request.setAttribute("xpoUploadDocument2", examPaperOnlineForm.getUploadDocument2());
		request.setAttribute("xpoUploadDocument3", examPaperOnlineForm.getUploadDocument3());
		request.setAttribute("xpoUploadDocument4", examPaperOnlineForm.getUploadDocument4());		
		
		return "uploadStep3";
		
	}
	
	private List getUserRolesForPaper(Person user,String module,String examYear,String examPeriod,String paperNo) throws Exception {
		List roleList = new ArrayList();
		ExamPaperOnlineDao ruledao = new ExamPaperOnlineDao();			
		
		if (ruledao.isCod(user.getPersonnelNumber(), module)){
			roleList.add("COD".toString());
		}
		if (ruledao.isExaminerType(user.getNovellUserId(), 
									module, 
									examYear, 
									examPeriod,
									paperNo,
									"1ST_EXAM")) {
			roleList.add("1ST_EXAM".toString());
		}
		if (ruledao.isExaminerType(user.getNovellUserId(), 
									module, 
									examYear, 
									examPeriod,
									paperNo,
									"2ND_EXAM")) {
			roleList.add("2ND_EXAM".toString());
		}
		if (ruledao.isExaminerType(user.getNovellUserId(), 
									module, 
									examYear, 
									examPeriod,
									paperNo,			
									"EXT_EXAM")) {
			roleList.add("EXT_EXAM".toString());
		}
		
		return roleList;
	}
	
	public void sendNotifyEmail(String toAddress, String addressee, String course) {

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
			String subject = "Online document - notification";
			String body = "<html> "+
		    "<body> "+
				"Dear " + addressee + ",  <br/><br/>"+
			"NB: This is an automated response - do not reply to this e-mail.  <br/><br/> "+
			"You are receiving this e-mail in response to an online document " + course + " sent for your attention. <br/>"+
			"Please check the Electronic examination paper system (xPaper). <br/>" +
			"</body>"+
			"</html>";

		  List contentList = new ArrayList();
		  contentList.add("Content-Type: text/html");
		  
		 emailService = (EmailService)ComponentManager.get(EmailService.class);  
		 emailService.sendMail(iaReplyTo[0],iaTo,subject,body,iaHeaderTo,iaReplyTo,contentList);
		 log.info("sending mail from "+iaReplyTo[0]+" to "+toEmail.toString());
		 } catch (Exception e) {
		 	log.error("PaperOnline: send of email notification failed "+e+" "+e.getMessage());
			e.printStackTrace();
		 }
	}
	
	public Integer getPrintQuantity(Short examYear, Short examPeriod, String studyUnit, Short paperNo)throws Exception {
		Integer quantity=0;
		
		Expll01sMntExamPaperLog op = new Expll01sMntExamPaperLog();
		operListener opl = new operListener();
		op.clear();
		
		op.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
		op.setInCsfClientServerCommunicationsClientVersionNumber((short) 3); 
		op.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
		op.setInCsfClientServerCommunicationsAction("CQ");
		op.setInExamTypistLogEntryPaperNo(paperNo);
		op.setInKeyExamTypistLogExamYear(examYear);
		op.setInKeyExamTypistLogMkExamPeriodCod(examPeriod);
		op.setInKeyExamTypistLogMkStudyUnitCode(studyUnit);
		
		op.execute();
		if (opl.getException() != null)
			throw opl.getException();
		if (op.getExitStateType() < 3)
			throw new Exception(op.getExitStateMsg());			
		
		quantity = op.getOutPrintQuantityIefSuppliedCount();
		
		return quantity;
	}	

}
