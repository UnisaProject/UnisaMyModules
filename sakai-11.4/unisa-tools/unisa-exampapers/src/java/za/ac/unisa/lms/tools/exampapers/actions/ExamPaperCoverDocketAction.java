package za.ac.unisa.lms.tools.exampapers.actions;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Calendar;

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
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.tool.api.ToolManager;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserDirectoryService;

import za.ac.unisa.lms.dao.Gencod;
import za.ac.unisa.lms.dao.StudentSystemGeneralDAO;
import za.ac.unisa.lms.dao.general.UserDAO;
import za.ac.unisa.lms.domain.general.Person;
import za.ac.unisa.lms.tools.exampapers.dao.*;
import za.ac.unisa.lms.tools.exampapers.forms.EquivalentRecord;
import za.ac.unisa.lms.tools.exampapers.forms.ExamPaperCoverDocketForm;
import za.ac.unisa.lms.tools.exampapers.forms.ExamPaperRecord;
import za.ac.unisa.lms.tools.exampapers.forms.PersonnelRecord;
import za.ac.unisa.lms.tools.exampapers.forms.SpecialMaterialRecord;
import Excdq01h.Abean.Excdq01sExamCoverDocketMnt;

public class ExamPaperCoverDocketAction extends LookupDispatchAction{
	
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
		map.put("initial", "initial");		
		map.put("button.continue","nextStep");
		map.put("button.cancel", "cancel");
		map.put("button.submit", "update");
		map.put("button.back", "prevStep");
		map.put("button.close", "close");
		map.put("button.open", "open");
		map.put("button.print", "cancel");
		//map.put("button.view", "viewPrintPage");
		map.put("print", "viewPrintPage");
		return map;		
	}
	
	public ActionForward update(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
			ExamPaperCoverDocketForm coverDocketForm = (ExamPaperCoverDocketForm) form;
			coverDocketForm.setLogAction("UPDATE");
			String nextPage="";			
					
			nextPage = submit(mapping, form, request, response);
			
			return mapping.findForward(nextPage);
			
			}
	
	public ActionForward close(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
			ExamPaperCoverDocketForm coverDocketForm = (ExamPaperCoverDocketForm) form;
			coverDocketForm.setStatus("CLOSED");
			coverDocketForm.setLogAction("CLOSE");
			String nextPage="";			
					
			nextPage = submit(mapping, form, request, response);
			
			return mapping.findForward(nextPage);
			
			}
	
	public ActionForward open(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
			ExamPaperCoverDocketForm coverDocketForm = (ExamPaperCoverDocketForm) form;
			coverDocketForm.setStatus("OPENED");
			coverDocketForm.setLogAction("OPEN");
			String nextPage="";			
					
			nextPage = submit(mapping, form, request, response);
			
			return mapping.findForward(nextPage);
			
			}
	
	public ActionForward cancel(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
			ExamPaperCoverDocketForm coverDocketForm = (ExamPaperCoverDocketForm) form;
			coverDocketForm.setSelectedExampaper("");
					
			return mapping.findForward("cancel");
			
			}
	
	public ActionForward viewPrintPage(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
			ExamPaperCoverDocketForm coverDocketForm = (ExamPaperCoverDocketForm) form;
			String nextPage="";
			coverDocketForm.setDisplayMedia("print");
			
			nextPage = displayStep3(mapping, form, request, response);
			
			return mapping.findForward(nextPage);
			
			}
	
	public ActionForward nextStep(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
			ExamPaperCoverDocketForm coverDocketForm = (ExamPaperCoverDocketForm) form;
			String nextPage="";
			if("input".equalsIgnoreCase(coverDocketForm.getCurrentPage())){
				nextPage = displayStep1(mapping, form, request, response);
				String stringUrl = coverDocketForm.getPrintFriendlyUrl();
				request.setAttribute("stringUrl", stringUrl);
			} else if("step1".equalsIgnoreCase(coverDocketForm.getCurrentPage())){
				nextPage = displayStep2(mapping, form, request, response);
			} else if("step2".equalsIgnoreCase(coverDocketForm.getCurrentPage())){
				coverDocketForm.setDisplayMedia("window");
				nextPage = displayStep3(mapping, form, request, response);
			} 
			coverDocketForm.setCurrentPage(nextPage);
			return mapping.findForward(nextPage);
			
			}
	
	public ActionForward prevStep(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
			ExamPaperCoverDocketForm coverDocketForm = (ExamPaperCoverDocketForm) form;
			String prevPage="";
			if("step3".equalsIgnoreCase(coverDocketForm.getCurrentPage())){
				prevPage = displayStep2(mapping, form, request, response);
			} 
			coverDocketForm.setCurrentPage(prevPage);
			return mapping.findForward(prevPage);
			
			}
	
	public ActionForward initial(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) throws Exception {
		
		String exams[] = {"01","JAN/FEB","06","MAY/JUN","10","OCT/NOV"};
		ExamPaperCoverDocketForm coverDocketForm = (ExamPaperCoverDocketForm) form;
		
		//Get printfriendly URL
		toolManager = (ToolManager)ComponentManager.get(ToolManager.class);
		String serverPath = ServerConfigurationService.getToolUrl();
		String toolId = toolManager.getCurrentPlacement().getId();
		coverDocketForm.setPrintFriendlyUrl(serverPath.trim() + "/" + toolId.trim() + "/examPaperCoverDocket.do?action=print");
		
		//Get openInstruction URL		
		coverDocketForm.setOpenInstructionUrl(serverPath.trim() + "/" + toolId.trim() + "/managementInfo.do?action=instruction");
				
		//Get user details
		coverDocketForm.setNovellUserid(null);
		
		String system = "";
		
		if (request.getParameter("SYSTEM")!=null && request.getParameter("SYSTEM").equalsIgnoreCase("XPO")){
			//eVault (ePaper)
			coverDocketForm.setFromSystem("eVault");
			if (request.getParameter("userId")!=null && !request.getParameter("userId").equalsIgnoreCase("")){
				coverDocketForm.setNovellUserid(request.getParameter("userId"));				
			}
		}else{
			//myUnisa
			User user = null;
			sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
		    userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
			Session currentSession = sessionManager.getCurrentSession();
			String userID  = currentSession.getUserId();
			coverDocketForm.setFromSystem("myUnisa");		
			if (userID != null) {
				user = userDirectoryService.getUser(userID);
				coverDocketForm.setNovellUserid(user.getEid());
			}
		}
		
		
		//coverDocketForm.setNovellUserid("PRETOJ"); //debug
		coverDocketForm.setNovellUserid(coverDocketForm.getNovellUserid().toUpperCase());
	
				
		if (coverDocketForm.getNovellUserid() == null) {
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("errors.unExpected"));
					addErrors(request,messages);
			return mapping.findForward("invaliduser");
			
		}
		if (coverDocketForm.getNovellUserid().length() > 10) {
			return mapping.findForward("inputexampaper");
		}
		coverDocketForm.setNovellUserid(coverDocketForm.getNovellUserid().toUpperCase());
				
//		//Determine permissions
//		SecurityServices ss = new SecurityServices();
//		String siteReference = ss.getSiteReference();
//		
//		coverDocketForm.setUpdateNrOfPagesRights(ss.checkPermission(siteReference,"exampapers.coverdocket.updatenrofpages"));
//		coverDocketForm.setUpdateRights(ss.checkPermission(siteReference, "exampapers.coverdocket.update"));
//		coverDocketForm.setUpdateStatusClosedRights(ss.checkPermission(siteReference, "exampapers.coverdocket.updatestatusclose"));
//		coverDocketForm.setUpdateStatusOpenRights(ss.checkPermission(siteReference, "exampapers.coverdocket.updatestatusopen"));
//				
//		//coverDocketForm.setUpdateNrOfPagesRights(false);
//		//coverDocketForm.setUpdateRights(true);
//		//coverDocketForm.setUpdateStatusClosedRights(true);
//		//coverDocketForm.setUpdateStatusOpenRights(true);
//		
//		if (coverDocketForm.isUpdateRights() || coverDocketForm.isUpdateStatusClosedRights()){
//			coverDocketForm.setDisplayLetterHeadRights(true);
//		}
			
		StudentSystemGeneralDAO dao = new StudentSystemGeneralDAO();
		List examPeriods = dao.getExamPeriods();
		coverDocketForm.setExamPeriodCodes(examPeriods);
		List markReadingCodes = dao.getGenCodes((short)38,2);
		coverDocketForm.setMarkReadingGenCodes(markReadingCodes);
		List fullPartialNoneCodes = dao.getGenCodes((short)50,2);
		List openBookCodes = dao.getGenCodes((short)50,2);		
		for (int i=0; i < openBookCodes.size(); i++){
			
			if (((Gencod)openBookCodes.get(i)).getCode().equalsIgnoreCase("F")){
				((Gencod)openBookCodes.get(i)).setEngDescription("Full (No restriction on material permitted in venue)");
			}
			if (((Gencod)openBookCodes.get(i)).getCode().equalsIgnoreCase("P")){
				((Gencod)openBookCodes.get(i)).setEngDescription("Partial (Specified material permitted in venue)");
			}
		}
		coverDocketForm.setFullPartialNoneCodes(fullPartialNoneCodes);
		coverDocketForm.setOpenBookCodes(openBookCodes);
		List yesNoCodes = dao.getGenCodes((short)51,2);
		coverDocketForm.setYesNoCodes(yesNoCodes);
		ExamPaperRecord paper = new ExamPaperRecord();
		paper.setExamYear(String.valueOf(getDefaultExamYear()));
		paper.setExamPeriod(getDefaultExamPeriod());
		paper.setPaperNo("1");
		coverDocketForm.setExampaper(paper);
		
		return mapping.findForward("inputexampaperany");
	}
	
	public String submit(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		ExamPaperCoverDocketForm coverDocketForm = (ExamPaperCoverDocketForm) form;
		
		//Do input validation - step3 
		ActionMessages messages = new ActionMessages();
		if (coverDocketForm.getContact().getCellNumber().trim().equalsIgnoreCase("") &&
				coverDocketForm.getContact().getWorkNumber().trim().equalsIgnoreCase("") &&
				coverDocketForm.getContact().getHomeNumber().trim().equalsIgnoreCase("") &&
				coverDocketForm.isUpdateRights()){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("errors.required",
								"At least one phone number for the contact person"));
		}
		if (!messages.isEmpty()) {
			addErrors(request,messages);
			return displayStep3(mapping, form, request, response);
		}
				
		Excdq01sExamCoverDocketMnt op = new Excdq01sExamCoverDocketMnt();
		operListener opl = new operListener();
		op.clear();
		
		op.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
		op.setInCsfClientServerCommunicationsClientVersionNumber((short) 3); 
		op.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
		op.setInCsfClientServerCommunicationsAction("U");
		op.setInWsUserStudyUnitNovellUserId(coverDocketForm.getNovellUserid());
		op.setInExamPaperDetailsMkStudyUnitCode(coverDocketForm.getExampaper().getStudyUnit());
		op.setInExamPaperDetailsExamYear(Short.parseShort(coverDocketForm.getExampaper().getExamYear().trim()));
		//op.setInExamPeriodDateMkExamPeriodCode(Short.parseShort(coverDocketForm.getExampaper().getExamPeriod().trim()));
		op.setInExamPeriodDateMkExamPeriodCode(coverDocketForm.getExampaper().getExamPeriod());
		op.setInExamPaperDetailsNr(Short.parseShort(coverDocketForm.getExampaper().getPaperNo().trim()));
		op.setInExamPaperDetailsNrOfPages(Short.parseShort(coverDocketForm.getExampaper().getRackSpacePages()));
		op.setInExamPaperCoverDocketTotalPages(Short.parseShort(coverDocketForm.getExampaper().getTotalPages()));
		op.setAsStringInExamPaperCoverDocketContactPersNr(coverDocketForm.getContactPerson());
		if (coverDocketForm.getNoBookRequired()==true) {
			op.setInExamPaperCoverDocketBookRequired("N");
		}
		else {
			op.setInExamPaperCoverDocketBookRequired("Y");
		}
		op.setInExamPaperCoverDocketProofReadRequest(coverDocketForm.getRequestToProofRead());
		if (coverDocketForm.getDocketExists()==false) {
			op.setInExamPaperCoverDocketStatusGc41("INPROGRESS");
			op.setInExamPaperLogActionGc39("CREATE");
		} else
		{
			op.setInExamPaperCoverDocketStatusGc41(coverDocketForm.getStatus());
			op.setInExamPaperLogActionGc39(coverDocketForm.getLogAction());
		}
		op.setInExamPaperLogUpdatedBy(coverDocketForm.getNovellUserid());
		op.setInExamPeriodDateMarkreadingCode(coverDocketForm.getExampaper().getMarkReadingCode());
		op.setInExamPeriodDateMarkreadTot(Short.parseShort(coverDocketForm.getExampaper().getMcqTotal()));
		op.setInExamPeriodDateScriptMarkTot(Short.parseShort(coverDocketForm.getExampaper().getWrittenTotal()));
		op.setInExamPeriodDatePaperMarkTot(Short.parseShort(coverDocketForm.getExampaper().getPaperTotal()));
		op.setInExamPaperCoverDocketAnnexurePages(coverDocketForm.getAnnexurePages());
		op.setInExamPaperCoverDocketFillInPaperGc50(coverDocketForm.getFillInPaper());
		op.setInExamPaperCoverDocketKeepPaperFlag(coverDocketForm.getKeepPaper());
		op.setInExamPaperCoverDocketDeclarationFlag(coverDocketForm.getDeclaration());
		op.setInExamPaperCoverDocketOpenBookGc50(coverDocketForm.getOpenBook());
		op.setInExamPaperCoverDocketCalcPermitFlag(coverDocketForm.getCalcPermit());
		op.setInExamPaperCoverDocketMemorandumIncl(coverDocketForm.getMemoIncluded());
		if (coverDocketForm.getAttendanceRegister()==true){
			op.setInExamPaperCoverDocketAttendRegFlag("Y");
		}else{
			op.setInExamPaperCoverDocketAttendRegFlag("N");
		}
		if (coverDocketForm.isMcqInstructionSheet()){
			op.setInExamPaperCoverDocketMcqInstrFlag("Y");
		}else{
			op.setInExamPaperCoverDocketMcqInstrFlag("N");
		}
		op.setInExamPaperCoverDocketPaperColour(coverDocketForm.getPaperColour());
		op.setInWsAddressV2CellNumber(coverDocketForm.getContact().getCellNumber());
		op.setInWsAddressV2WorkNumber(coverDocketForm.getContact().getWorkNumber());
		op.setInWsAddressV2HomeNumber(coverDocketForm.getContact().getHomeNumber());
//		op.setInExamPaperCoverDocketNrDocsSubmitted(coverDocketForm.getExampaper().getNrOfDocumentsSubmitted());
		op.setInExamPaperCoverDocketNrDocsSubmitted(Short.parseShort("0"));
			
		//Language medium
		List list = new ArrayList();
		list = coverDocketForm.getExampaper().getLanguages();
		for (int i=0; i < coverDocketForm.getIndexNrSelectedLanguage().length; i++) {
			String array[] = coverDocketForm.getIndexNrSelectedLanguage();
			op.setInGExamPaperLanguageLanguageGc40(i, ((Gencod)list.get(Integer.parseInt(array[i]))).getCode());
		}		
		op.setInPaperLanguageGroupCount(Short.parseShort(String.valueOf(coverDocketForm.getIndexNrSelectedLanguage().length)));
        
		//Additional instructions
		int count = 0;
		for (int i=0; i < coverDocketForm.getExampaper().getAdditionalInstructions().size(); i++) {
			String instruction = (String)(coverDocketForm.getExampaper().getAdditionalInstructions().get(i));
			if (instruction != ""){
				op.setInGExamPaperAddInstructionsInstruction(count, instruction);
				op.setInGExamPaperAddInstructionsInstructionNr(count, Short.parseShort((String.valueOf(count + 1))));
				count = count + 1;
			}
		}
		op.setInAdditionalInstrGroupCount(Short.parseShort(String.valueOf(count)));
		
		//Special instructions
		for (int i=0; i < coverDocketForm.getSpecialInstructionSelection().length; i++){
			String array[] = coverDocketForm.getSpecialInstructionSelection();
			op.setInGExamDateInstructionMkDocumentParagraphCode(i, Short.parseShort(array[i]));
		}
		op.setInExamInstructionGroupCount(Short.parseShort(String.valueOf(coverDocketForm.getSpecialInstructionSelection().length)));
		
		
		//Special material
		List listMaterial = new ArrayList();
		listMaterial = coverDocketForm.getExampaper().getSpecialMaterials();
		for (int i=0; i < coverDocketForm.getIndexNrSelectedMaterial().length; i++) {
			String array[] = coverDocketForm.getIndexNrSelectedMaterial();
			op.setInGExamStationeryItemCode(i, ((SpecialMaterialRecord)listMaterial.get(Integer.parseInt(array[i]))).getCode());
			op.setInGExamPaperStationeryNeedsAveragePerStudent(i, Double.parseDouble(((SpecialMaterialRecord)listMaterial.get(Integer.parseInt(array[i]))).getAverage()));
		}
		int indexStationary = coverDocketForm.getIndexNrSelectedMaterial().length - 1;
		
		//Answer books
		List listBook = new ArrayList();
		listBook = coverDocketForm.getExampaper().getAnswerBooks();
		for (int i=0; i < coverDocketForm.getIndexNrSelectedBook().length; i++) {
			indexStationary = indexStationary + 1;
			String array[] = coverDocketForm.getIndexNrSelectedBook();
			op.setInGExamStationeryItemCode(indexStationary, ((SpecialMaterialRecord)listBook.get(Integer.parseInt(array[i]))).getCode());
			op.setInGExamPaperStationeryNeedsAveragePerStudent(indexStationary, Double.parseDouble(((SpecialMaterialRecord)listBook.get(Integer.parseInt(array[i]))).getAverage()));
		}	
		
		op.setInExamStationaryGroupCount(Short.parseShort(String.valueOf(indexStationary + 1)));
		
		//Examiner panel/list
		op.setInExamPaperCoverDocketExaminerPrtGc107(coverDocketForm.getExampaper().getExaminerPrt().trim());
							
		op.execute();
		if (opl.getException() != null)
			throw opl.getException();
		if (op.getExitStateType() < 3)
			throw new Exception(op.getExitStateMsg());
				
		if (op.getOutCsfStringsString500() != "") {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
							op.getOutCsfStringsString500()));	
			addErrors(request,messages);
			return displayStep3(mapping, form, request, response);
			}	
		if (coverDocketForm.isUpdateRights())
			return "inputexampaper";
		else {
			//StudentSystemGeneralDAO daoxamprd = new StudentSystemGeneralDAO();
			//List examPeriods = daoxamprd.getExamPeriods();
			//request.setAttribute("examPeriods",examPeriods);
			coverDocketForm.getExampaper().setStudyUnit("");
			return "inputexampaperany";
		}
	}
	
	public String displayStep1(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
			String exams[] = {"01","JAN/FEB","06","MAY/JUN","10","OCT/NOV"};
						
			ExamPaperCoverDocketForm coverDocketForm = (ExamPaperCoverDocketForm) form;
			ExamPaperRecord paper = new ExamPaperRecord();
			
			ActionMessages messages = new ActionMessages();
			
			//do validation
			if (coverDocketForm.getExampaper().getExamYear() == null || coverDocketForm.getExampaper().getExamYear().equals("0")) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
								"Examination Year is required."));
			}
			if (!testInteger(coverDocketForm.getExampaper().getExamYear())) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
								"Examination Year must be numberic."));
			}
			if (coverDocketForm.getExampaper().getStudyUnit() == null || coverDocketForm.getExampaper().getStudyUnit().equals("")) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
								"Study Unit is required."));
			}
			if (coverDocketForm.getExampaper().getPaperNo() == null || coverDocketForm.getExampaper().getPaperNo().equals("0")) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
								"Paper Number is required."));
			}			
			if (!testInteger(coverDocketForm.getExampaper().getPaperNo())) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
								"Paper Number must be numberic."));
			}
			
			if (!messages.isEmpty()) {
				addErrors(request,messages);
				return "inputexampaper";
			}
			//validate course access
			CourseDAO daoCourse = new CourseDAO();
			StudyUnit studyUnit = new StudyUnit();
			studyUnit = daoCourse.getStudyUnit(coverDocketForm.getExampaper().getStudyUnit());
			if (!studyUnit.getErrorMsg().equals("")){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
								studyUnit.getErrorMsg()));						
			} 
			if (!messages.isEmpty()) {
				addErrors(request,messages);
				return "inputexampaper";
			}
			
			StudentSystemGeneralDAO genCodDao = new StudentSystemGeneralDAO();
			Gencod gencod = new Gencod();
			
			gencod = genCodDao.getGenCode("44", studyUnit.getTuitionType());
			if (gencod!=null && gencod.getEngDescription()!=null){
				coverDocketForm.setTuitionType(gencod.getEngDescription());
			}else{
				coverDocketForm.setTuitionType("");
			}
			gencod = genCodDao.getGenCode("63", studyUnit.getAcademicLevel());
			if (gencod!=null && gencod.getEngDescription()!=null){
				coverDocketForm.setAcadLevel(gencod.getEngDescription());
			}else{
				coverDocketForm.setAcadLevel("");
			}
				
			if (studyUnit.getDepartment().equalsIgnoreCase("5")){
				coverDocketForm.setSblFlag("Y");
			}else{
				coverDocketForm.setSblFlag("N");
			}
			
			//Get all Academic Year and semesters for study unit which write this exam
			List listCourse = daoCourse.getCourses(Short.parseShort(coverDocketForm.getExampaper().getExamYear()),
					coverDocketForm.getExampaper().getExamPeriod(),
					coverDocketForm.getExampaper().getStudyUnit(),
					Short.parseShort(coverDocketForm.getExampaper().getPaperNo()));
			if (listCourse.size()==0) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
					"No study unit period detail exist for this examination paper"));						
			} 
			if (!messages.isEmpty()) {
				addErrors(request,messages);
				return "inputexampaper";
			}	
			Course course = new Course();
			course = (Course)listCourse.get(0);
			if (course.getSemester().compareTo(Short.parseShort("0"))>0){
				coverDocketForm.setSemesterType("Semester Module");
			}else{
				coverDocketForm.setSemesterType("Year Module");
			}
			//Determine access and type of access
			coverDocketForm.setUpdateRights(false);
			coverDocketForm.setUpdateNrOfPagesRights(false);
			coverDocketForm.setUpdateStatusClosedRights(false);
			coverDocketForm.setUpdateStatusOpenRights(false);
			coverDocketForm.setDisplayLetterHeadRights(true);
			
			ExamPaperCoverDocketDAO roleDao = new ExamPaperCoverDocketDAO();
			String accessFlag="N";
			//Determine if user is a typist
			if (roleDao.isExamsQa1(coverDocketForm.getNovellUserid()) ||
					roleDao.isExamsQa2(coverDocketForm.getNovellUserid())){
				//User is typist
				coverDocketForm.setUpdateRights(true);
				coverDocketForm.setUpdateNrOfPagesRights(true);
				coverDocketForm.setUpdateStatusClosedRights(true);
				coverDocketForm.setUpdateStatusOpenRights(true);	
				accessFlag="Y";
			} 
			//Determine if user set as a Primary or Secondary lecturer or courseware admin user
			if (accessFlag.equalsIgnoreCase("N")){
				for(int i=0; i < listCourse.size();i++){
					if (daoCourse.isValid(coverDocketForm.getNovellUserid(),
							coverDocketForm.getExampaper().getStudyUnit(),
							((Course)(listCourse.get(i))).getAcademicYear(),
							((Course)(listCourse.get(i))).getSemester())) {
						accessFlag="Y";
						coverDocketForm.setUpdateRights(true);
						coverDocketForm.setUpdateNrOfPagesRights(true);					
						i=listCourse.size();
					}
				}
			}				
				//if still no access check if user is an Examiner or COD
			Person person = new Person();
			UserDAO userdao = new UserDAO();
			person = userdao.getPerson(coverDocketForm.getNovellUserid());
			if (accessFlag.equalsIgnoreCase("N")){
				if (roleDao.isExaminerType(coverDocketForm.getNovellUserid(), 
						coverDocketForm.getExampaper().getStudyUnit(), 
						coverDocketForm.getExampaper().getExamYear(), 
						coverDocketForm.getExampaper().getExamPeriod().toString(),
						coverDocketForm.getExampaper().getPaperNo(), "1ST_EXAM")){
					accessFlag="Y";
					coverDocketForm.setUpdateRights(true);
					coverDocketForm.setUpdateNrOfPagesRights(true);							
				} else if (roleDao.isExaminerType(coverDocketForm.getNovellUserid(), 
						coverDocketForm.getExampaper().getStudyUnit(), 
						coverDocketForm.getExampaper().getExamYear(), 
						coverDocketForm.getExampaper().getExamPeriod().toString(),
						coverDocketForm.getExampaper().getPaperNo(), "2ND_EXAM")){
					accessFlag="Y";
					coverDocketForm.setUpdateRights(true);
					coverDocketForm.setUpdateNrOfPagesRights(true);							
				} else if (roleDao.isExaminerType(coverDocketForm.getNovellUserid(), 
						coverDocketForm.getExampaper().getStudyUnit(), 
						coverDocketForm.getExampaper().getExamYear(), 
						coverDocketForm.getExampaper().getExamPeriod().toString(),
						coverDocketForm.getExampaper().getPaperNo(), "EXT_EXAM")){
					accessFlag="Y";
					coverDocketForm.setUpdateRights(true);
					coverDocketForm.setUpdateNrOfPagesRights(true);							
				} else if (person.getPersonnelNumber()!=null  && testInteger(person.getPersonnelNumber())){
					if (roleDao.isCod(person.getPersonnelNumber(), coverDocketForm.getExampaper().getStudyUnit())){
						accessFlag="Y";					
						coverDocketForm.setUpdateRights(true);
						coverDocketForm.setUpdateNrOfPagesRights(true);		
					}										
				}
			}
				
				//User must have access to specified module accept typist, typist have access to all modules it they exists.
//				if (coverDocketForm.isUpdateStatusOpenRights()){
//					//typist
//				}else{
//					//lecturer or course admin
//					String accessFlag="N";
//					for(int i=0; i < listCourse.size();i++){
//						if (daoCourse.isValid(coverDocketForm.getNovellUserid(),
//								coverDocketForm.getExampaper().getStudyUnit(),
//								((Course)(listCourse.get(i))).getAcademicYear(),
//								((Course)(listCourse.get(i))).getSemester())) {
//							accessFlag="Y";
//							i=listCourse.size();
//						}
//					}
//					if (accessFlag.equalsIgnoreCase("N")) {
//						messages.add(ActionMessages.GLOBAL_MESSAGE,
//								new ActionMessage("message.generalmessage",
//										"You are not authorized to enter examination cover docket information for this course"));						
//					} 
//				}		
			if (accessFlag.equalsIgnoreCase("N")) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
								"You are not authorized to enter examination cover docket information for this course"));						
			} 
			if (!messages.isEmpty()) {
				addErrors(request,messages);
				return "inputexampaper";
			}
			
			//Set Examperiod Description
			String fmt = "00";
			DecimalFormat nf = new DecimalFormat(fmt);
			String strPeriod = nf.format(coverDocketForm.getExampaper().getExamPeriod());
				for (int j=0; j < exams.length; j++){
					if (strPeriod.equals(exams[j])){
						strPeriod = exams[j+1];
						j=exams.length;
					}				
				}
			paper.setStudyUnit(coverDocketForm.getExampaper().getStudyUnit().toUpperCase());
			paper.setExamYear(coverDocketForm.getExampaper().getExamYear());
			paper.setExamPeriod(coverDocketForm.getExampaper().getExamPeriod());
			paper.setPaperNo(coverDocketForm.getExampaper().getPaperNo());
			paper.setExamPeriodDesc(strPeriod);		
			coverDocketForm.setExampaper(paper);
			
			Excdq01sExamCoverDocketMnt op = new Excdq01sExamCoverDocketMnt();
			operListener opl = new operListener();
			op.clear();
			
			op.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
			op.setInCsfClientServerCommunicationsClientVersionNumber((short) 3); 
			op.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
			op.setInCsfClientServerCommunicationsAction("D");
			op.setInWsUserStudyUnitNovellUserId(coverDocketForm.getNovellUserid());
			op.setInExamPaperDetailsMkStudyUnitCode(coverDocketForm.getExampaper().getStudyUnit());
			op.setInExamPaperDetailsExamYear(Short.parseShort(coverDocketForm.getExampaper().getExamYear().trim()));
			//op.setInExamPeriodDateMkExamPeriodCode(Short.parseShort(coverDocketForm.getExampaper().getExamPeriod().trim()));
			op.setInExamPeriodDateMkExamPeriodCode(coverDocketForm.getExampaper().getExamPeriod());
			op.setInExamPaperDetailsNr(Short.parseShort(coverDocketForm.getExampaper().getPaperNo().trim()));
			op.execute();
			if (opl.getException() != null)
				throw opl.getException();
			if (op.getExitStateType() < 3)
				throw new Exception(op.getExitStateMsg());
			//Do validation
			//Do validation select exam paper window
			if (coverDocketForm.isUpdateRights()){
				//Test if server successful, if not display error message				
				if (!op.getOutCsfStringsString500().equals("")){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
									op.getOutCsfStringsString500()));						
				} 
				if(coverDocketForm.isUpdateStatusClosedRights()){
					//typist - can only change existing cover dockets
					if (!op.getOutCoverDocketExistsFlagCsfStringsString1().equalsIgnoreCase("Y")){
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage",
										"Cover docket does not exists."));
					}
				}
				if (!messages.isEmpty()) {
					addErrors(request,messages);
					return "inputexampaper";
				}	
			}
			
			//Do validation input any paper window
			//Production may only view a coverdocket which is closed
			if (!coverDocketForm.isUpdateRights()){
				//Test if server successful, if not display error message				
				if (!op.getOutCsfStringsString500().trim().equals("")){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
									op.getOutCsfStringsString500()));						
				} 
				if (!op.getOutCoverDocketExistsFlagCsfStringsString1().equalsIgnoreCase("Y")){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
									"Cover docket does not exists."));				
				} else if (!op.getOutExamPaperCoverDocketStatusGc41().trim().equalsIgnoreCase("CLOSED")&&!coverDocketForm.isUpdateStatusClosedRights()){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
									"You are only allowed to view closed Cover dockets. Cover docket has not yet been closed."));		
				}
				if (!messages.isEmpty()) {
					addErrors(request,messages);
					//StudentSystemGeneralDAO daoxamprd = new StudentSystemGeneralDAO();
					//List examPeriods = daoxamprd.getExamPeriods();
					//request.setAttribute("examPeriods",examPeriods);
					return "inputexampaperany";
				}				
			}			
			int countEquivalent = op.getOutEquivalentGroupCount();
			int countExaminers = op.getOutExaminersGroupCount();
			int countLanguages = op.getOutPaperLanguageGroupCount();
			int countStationary = op.getOutExamStationaryGroupCount();
			int countInstructions = op.getOutExamInstructionGroupCount();
			int countAddInstructions = op.getOutAdditionalInstrGroupCount();
						
			List listFirstExaminers = new ArrayList();
			List listSecondExaminers = new ArrayList();
			List listExternalExaminers = new ArrayList();
			List listMaterials = new ArrayList();
			List listBooks = new ArrayList();
			List listInstructions = new ArrayList();
			List listEquivalent = new ArrayList();
			List listAddInstructions = new ArrayList();
			List listCalcInstructions = new ArrayList();
			List listInUseInstructions = new ArrayList();
									
			listMaterials = initializeMaterials(form);
			listBooks = initializeBooks(form);
			//Initialize coverDocketForm values
			coverDocketForm.setIndexNrSelectedLanguage(null);
			coverDocketForm.setSpecialInstructionSelection(null);
			coverDocketForm.setCalcInstructionSelection(null);
			coverDocketForm.setInUseInstructionSelection(null);
			coverDocketForm.setIndexNrSelectedBook(null);
			coverDocketForm.setIndexNrSelectedMaterial(null);
			coverDocketForm.setEducationFaculty(false);
			coverDocketForm.setStatus("");  //debug
			coverDocketForm.setDocketExists(false);
			coverDocketForm.setNoBookRequired(false);
			coverDocketForm.setContactPerson("");
			coverDocketForm.setRequestToProofRead("N");
			coverDocketForm.setMemoIncluded("");
			coverDocketForm.setDeclaration("N");
			coverDocketForm.setCalcPermit("");
			coverDocketForm.setFillInPaper("");
			coverDocketForm.setKeepPaper("N");
			coverDocketForm.setOpenBook("");
						
			//get all possible Special Instructions
			SpecialInstructionsDAO daoinstr = new SpecialInstructionsDAO();
			listInstructions = daoinstr.getSpecialInstructionsCombined();
			//strip out Special Instructions regarding calculators
			for (int i=0; i<listInstructions.size() ;i++){
				String label = ((LabelValueBean)listInstructions.get(i)).getLabel();
				String value =((LabelValueBean)listInstructions.get(i)).getValue();
				if (value.equalsIgnoreCase("8702") || 
					value.equalsIgnoreCase("8703") ||
					value.equalsIgnoreCase("8704") || 
					value.equalsIgnoreCase("8705")){
					listCalcInstructions.add(new LabelValueBean(label,value));
				}
			}
			
			//strip out Special Instructions still in use
			for (int i=0; i<listInstructions.size() ;i++){
				String label = ((LabelValueBean)listInstructions.get(i)).getLabel();
				String value =((LabelValueBean)listInstructions.get(i)).getValue();				
				if (value.equalsIgnoreCase("8707") ||
					value.equalsIgnoreCase("8726") || 
					value.equalsIgnoreCase("8733") ||
					value.equalsIgnoreCase("8736") ||
					value.equalsIgnoreCase("8737") || 
					value.equalsIgnoreCase("8741") || 
					value.equalsIgnoreCase("8751") ||
					//value.equalsIgnoreCase("8752") || 
					value.equalsIgnoreCase("8753") ||
					value.equalsIgnoreCase("8754")) {
					listInUseInstructions.add(new LabelValueBean(label,value));
				}
				if (value.equalsIgnoreCase("8761") && 
						(coverDocketForm.getExampaper().getStudyUnit().equalsIgnoreCase("MEL8213")||
							coverDocketForm.getExampaper().getStudyUnit().equalsIgnoreCase("MEL8224")||
							coverDocketForm.getExampaper().getStudyUnit().equalsIgnoreCase("MEL8235")||
							coverDocketForm.getExampaper().getStudyUnit().equalsIgnoreCase("MEL8235")||
							coverDocketForm.getExampaper().getStudyUnit().equalsIgnoreCase("MEL8246")||
							coverDocketForm.getExampaper().getStudyUnit().equalsIgnoreCase("MEL8213")||
							coverDocketForm.getExampaper().getStudyUnit().equalsIgnoreCase("HMELTCK")||
							coverDocketForm.getExampaper().getStudyUnit().equalsIgnoreCase("HMELLSK")||
							coverDocketForm.getExampaper().getStudyUnit().equalsIgnoreCase("HMELRLQ")||
							coverDocketForm.getExampaper().getStudyUnit().equalsIgnoreCase("HMELCEG")||
							coverDocketForm.getExampaper().getStudyUnit().equalsIgnoreCase("HMELRAD"))){
					listInUseInstructions.add(new LabelValueBean(label,value));
				}
			}
			//get all possible Exam Paper languages
			StudentSystemGeneralDAO daogen = new StudentSystemGeneralDAO();
			List listLanguages = daogen.getGenCodes((short)40,2);
			//first group item will always be the main paper
			for (int i = 0; i < countEquivalent; i++){
				if (i > 0) {
					EquivalentRecord equivalent = new EquivalentRecord();
					equivalent.setStudyUnit(op.getOutGEquivalentWsExamEquivalentsWsEquivalentCode(i));
					equivalent.setUniqueNo(String.valueOf(op.getOutGWsUniqueAssignmentUniqueNr(i)));
					String stringDate="";
					String stringTime="";
					Date tempDate = new Date();
					if (op.getOutGEquivalentExamPeriodDateDate(i) instanceof Calendar) {
						SimpleDateFormat formatter = new SimpleDateFormat("d MMMMM yyyy");
						tempDate = op.getOutGEquivalentExamPeriodDateDate(i).getTime();
						stringDate = formatter.format(tempDate);
					}						
					if (op.getOutGEquivalentExamPeriodDateStartingTime(i) instanceof Calendar) {
						SimpleDateFormat formatter = new SimpleDateFormat("HH'H'mm");
						tempDate = op.getOutGEquivalentExamPeriodDateStartingTime(i).getTime();
						stringTime = formatter.format(tempDate);
					} 
						
					if ("3 March 1903".equals(stringDate)){
						stringDate="Departmental requirement";
						stringTime=" ";
					}
					if ("1 January 1901".equals(stringDate)){
						stringDate="Will be informed";
						stringTime=" ";
					}
					equivalent.setExamDate(stringDate);
					equivalent.setExamTime(stringTime);	
					equivalent.setDurationDays(String.valueOf(op.getOutGEquivalentExamPeriodDateDurationDays(i)));
					equivalent.setDurationHours(String.valueOf(op.getOutGEquivalentExamPeriodDateDurationHours(i)));
					equivalent.setDurationMin(String.valueOf(op.getOutGEquivalentExamPeriodDateDurationMinutes(i)));					
					listEquivalent.add(i-1,equivalent);
			
				}
			}			
			//Change 20110505 - read examiners from usrsun
			
			ExamPaperCoverDocketDAO dao = new ExamPaperCoverDocketDAO();
			listFirstExaminers =  dao.getExaminersForModulePerType(coverDocketForm.getExampaper().getStudyUnit(),
					coverDocketForm.getExampaper().getExamYear(), 
					coverDocketForm.getExampaper().getExamPeriod().toString(), 
					coverDocketForm.getExampaper().getPaperNo(), 
					"1ST_EXAM");			
			listSecondExaminers =  dao.getExaminersForModulePerType(coverDocketForm.getExampaper().getStudyUnit(),
					coverDocketForm.getExampaper().getExamYear(), 
					coverDocketForm.getExampaper().getExamPeriod().toString(), 
					coverDocketForm.getExampaper().getPaperNo(), 
					"2ND_EXAM");
			listExternalExaminers =  dao.getExaminersForModulePerType(coverDocketForm.getExampaper().getStudyUnit(),
					coverDocketForm.getExampaper().getExamYear(), 
					coverDocketForm.getExampaper().getExamPeriod().toString(), 
					coverDocketForm.getExampaper().getPaperNo(), 
					"EXT_EXAM");
			
			/*int countFirst = 0;
			int countSecond = 0;
			int countExternal = 0;
			for (int i= 0; i < countExaminers; i++){
				if ("1ST".equals(op.getOutGExaminerTypeCsfStringsString3(i))){
					listFirstExaminers.add(countFirst,op.getOutGWsExaminerTitle(i).trim() + " " + op.getOutGWsExaminerInitials(i).trim() + " " + op.getOutGWsExaminerSurname(i).trim());
					countFirst = countFirst + 1;
					
				}
				if ("2ND".equals(op.getOutGExaminerTypeCsfStringsString3(i))){
					listSecondExaminers.add(countSecond,op.getOutGWsExaminerTitle(i).trim() + " " + op.getOutGWsExaminerInitials(i).trim() + " " + op.getOutGWsExaminerSurname(i).trim());
					countFirst = countSecond + 1;
					
				}
				if ("EXT".equals(op.getOutGExaminerTypeCsfStringsString3(i))){
					String externalExaminer = op.getOutGWsExaminerTitle(i).trim() + " " + op.getOutGWsExaminerInitials(i).trim() + " " + op.getOutGWsExaminerSurname(i).trim();
					if (op.getOutGWsEducationalInstitutionEngName(i)!=null && !op.getOutGWsEducationalInstitutionEngName(i).trim().equalsIgnoreCase("")) {
						externalExaminer=externalExaminer + " (" + op.getOutGWsEducationalInstitutionEngName(i).trim() + ")";
					}
					//listExternalExaminers.add(countExternal,op.getOutGWsExaminerTitle(i).trim() + " " + op.getOutGWsExaminerInitials(i).trim() + " " + op.getOutGWsExaminerSurname(i).trim());
					listExternalExaminers.add(countExternal,externalExaminer);
					countExternal = countExternal + 1;
					
				}
				//removed from coolgen server - panel not printed on default user must select examiner detail option
				if ("EXP".equals(op.getOutGExaminerTypeCsfStringsString3(i))){
					coverDocketForm.setEducationFaculty(true);
				}
				
			}*/	
			
			//Get additional exam paper instructions
			for (int i=0; i <  countAddInstructions; i++) {			
				listAddInstructions.add(i, op.getOutGExamPaperAddInstructionsInstruction(i));
			}
			for (int i=countAddInstructions; i < 10; i++) {
				listAddInstructions.add(i, "");
			}
			
			//Get special stationary set for examination paper - set checkboxes
			String stringMaterialCode;
			String[] array;
			String[] arrayBook;
			int indexMaterial = 0;
			int indexBook = 0;
			array = new String[countStationary];
			arrayBook = new String[countStationary];
			
			for (int i = 0; i < countStationary; i++) {
				//Get special material
				for (int j = 0; j < listMaterials.size(); j++){
					SpecialMaterialRecord record = new SpecialMaterialRecord();
					record = (SpecialMaterialRecord) listMaterials.get(j);
					stringMaterialCode = record.getCode();
					if (op.getOutGExamStationeryItemCode(i).equalsIgnoreCase(stringMaterialCode)){
						array[indexMaterial] = String.valueOf(j);
						record.setAverage(String.valueOf(op.getOutGExamPaperStationeryNeedsAveragePerStudent(i)));
						indexMaterial = indexMaterial + 1;	
						j = listMaterials.size();
					}
					
				}
				//Get answer books
				for (int k = 0; k < listBooks.size(); k++){
					SpecialMaterialRecord record = new SpecialMaterialRecord();
					record = (SpecialMaterialRecord) listBooks.get(k);
					stringMaterialCode = record.getCode();
					if (op.getOutGExamStationeryItemCode(i).equalsIgnoreCase(stringMaterialCode)){
						arrayBook[indexBook] = String.valueOf(k);
						record.setAverage(String.valueOf(op.getOutGExamPaperStationeryNeedsAveragePerStudent(i)));
						indexBook = indexBook + 1;	
						k = listBooks.size();
					}
					
				}
				if (op.getOutGExamStationeryItemCode(i).equalsIgnoreCase("SF")) {
					coverDocketForm.setSpecialFrontPage(true);
				}
				if (op.getOutGExamStationeryItemCode(i).equalsIgnoreCase("AR")) {
					coverDocketForm.setAttendanceRegister(true);
				}
				
				
			}
			//Delete null value array references
			String[] remove = {null};
			List listMaterialTemp = new ArrayList(Arrays.asList(array));
			listMaterialTemp.removeAll(new ArrayList(Arrays.asList(remove)));
			array = (String[]) listMaterialTemp.toArray(new String[listMaterialTemp.size()]);
			
			List listBookTemp = new ArrayList(Arrays.asList(arrayBook));
			listBookTemp.removeAll(new ArrayList(Arrays.asList(remove)));
			arrayBook = (String[]) listBookTemp.toArray(new String[listBookTemp.size()]);
			
			coverDocketForm.setIndexNrSelectedMaterial(array);
			coverDocketForm.setIndexNrSelectedBook(arrayBook);
			
					
            //Get special instructions(in use) set for examination paper - set checkboxes
			array = new String[listInUseInstructions.size()];
			int index = 0;
			for (int i= 0; i < countInstructions; i++){				
				for (int j = 0; j < listInUseInstructions.size(); j++){
					String stringValue = ((LabelValueBean)listInUseInstructions.get(j)).getValue();
					if (String.valueOf(op.getOutGExamDateInstructionMkDocumentParagraphCode(i)).equalsIgnoreCase(stringValue)){
						array[index] = String.valueOf(j);
						index = index + 1;							
					}
				}
			}
            //Delete null value array references
			List listInstructionTemp = new ArrayList(Arrays.asList(array));
			listInstructionTemp.removeAll(new ArrayList(Arrays.asList(remove)));
			array = (String[]) listInstructionTemp.toArray(new String[listInstructionTemp.size()]);
			coverDocketForm.setInUseInstructionSelection(array);
			
            //Get cacl special instructions set for examination paper - set checkboxes
			array = new String[listCalcInstructions.size()];
			index = 0;
			for (int i= 0; i < countInstructions; i++){
				for (int j = 0; j < listCalcInstructions.size(); j++){
					String stringValue = ((LabelValueBean)listCalcInstructions.get(j)).getValue();
					if (String.valueOf(op.getOutGExamDateInstructionMkDocumentParagraphCode(i)).equalsIgnoreCase(stringValue)){
						array[index] = String.valueOf(j);
						index = index + 1;		
						//Set calculator permissible if one of instructions selected
						coverDocketForm.setCalcPermit("Y");
					}
				}
			}
            //Delete null value array references
			List calcInstructionTemp = new ArrayList(Arrays.asList(array));
			calcInstructionTemp.removeAll(new ArrayList(Arrays.asList(remove)));
			array = (String[]) calcInstructionTemp.toArray(new String[calcInstructionTemp.size()]);
			coverDocketForm.setCalcInstructionSelection(array);
			
			//Get examination languages set for examination paper - set checkboxes
			String stringGencodCode;
			index = 0;
			array = new String[countLanguages];
			for (int i = 0; i < countLanguages; i++){
				for (int j = 0; j < listLanguages.size(); j++){
					stringGencodCode = ((Gencod) listLanguages.get(j)).getCode();
					if (op.getOutGExamPaperLanguageLanguageGc40(i).equalsIgnoreCase(stringGencodCode)){
						array[index] = String.valueOf(j);
						index = index + 1;
					}
				}				
			}
			//Delete null value array references
			List listLanguageTemp = new ArrayList(Arrays.asList(array));
			listLanguageTemp.removeAll(new ArrayList(Arrays.asList(remove)));
			arrayBook = (String[]) listLanguageTemp.toArray(new String[listLanguageTemp.size()]);
			
			coverDocketForm.setIndexNrSelectedLanguage(array);	
			
//			StudentSystemGeneralDAO dao = new StudentSystemGeneralDAO();
//			List markReadingCodes = dao.getGenCodes((short)38,2);
//			coverDocketForm.setMarkReadingGenCodes(markReadingCodes);
//			List fullPartialNoneCodes = dao.getGenCodes((short)50,2);
//			coverDocketForm.setFullPartialNoneCodes(fullPartialNoneCodes);
//			List yesNoCodes = dao.getGenCodes((short)51,2);
//			coverDocketForm.setYesNoCodes(yesNoCodes);
			
			ExamPaperRecord paperDetail = new ExamPaperRecord();
			
			//Initialize ExamPaperRecord
			paperDetail.setWrittenTotal("0");
			paperDetail.setMcqTotal("0");
			paperDetail.setPaperTotal("0");
			paperDetail.setRackSpacePages("0");
			paperDetail.setTotalPages("0");
			paperDetail.setNrOfDocumentsSubmitted(Short.parseShort("0"));
			//If School of education - default examiners display to Panel 
			//else default examiners list to detailed list of examiners
			if (op.getOutWsDepartmentCollegeCode()==2 &&
					op.getOutWsDepartmentSchoolCode()==11){
				paperDetail.setExaminerPrt("PANEL");				
			}else{
				paperDetail.setExaminerPrt("LIST");
			}
						
			//Coverdocket exists
			if (op.getOutCoverDocketExistsFlagCsfStringsString1().equalsIgnoreCase("Y")){
				coverDocketForm.setDocketExists(true);
				coverDocketForm.setStatus(op.getOutExamPaperCoverDocketStatusGc41());
				if (op.getOutExamPaperCoverDocketBookRequired().equalsIgnoreCase("Y")){
					coverDocketForm.setNoBookRequired(false);
				} else{
					coverDocketForm.setNoBookRequired(true);
				}
				coverDocketForm.setRequestToProofRead(op.getOutExamPaperCoverDocketProofReadRequest());
				coverDocketForm.setMemoIncluded(op.getOutExamPaperCoverDocketMemorandumIncl());
				coverDocketForm.setAnnexurePages(op.getOutExamPaperCoverDocketAnnexurePages());
				coverDocketForm.setCalcPermit(op.getOutExamPaperCoverDocketCalcPermitFlag());
				coverDocketForm.setDeclaration(op.getOutExamPaperCoverDocketDeclarationFlag());
				coverDocketForm.setFillInPaper(op.getOutExamPaperCoverDocketFillInPaperGc50());
				if ((Integer.parseInt(coverDocketForm.getExampaper().getExamYear())==2011 &&
						coverDocketForm.getExampaper().getExamPeriod().compareTo(Short.parseShort("6"))>0) ||
						Integer.parseInt(coverDocketForm.getExampaper().getExamYear())>2011){
					        coverDocketForm.setDisableKeepPaper(true); 
							coverDocketForm.setKeepPaper("N");
						}else{
							coverDocketForm.setDisableKeepPaper(false); 
							coverDocketForm.setKeepPaper(op.getOutExamPaperCoverDocketKeepPaperFlag());
						}				
				coverDocketForm.setOpenBook(op.getOutExamPaperCoverDocketOpenBookGc50());
				coverDocketForm.setContactPerson(String.valueOf(op.getOutExamPaperCoverDocketContactPersNr()));
				PersonnelRecord contactPerson = new PersonnelRecord();
				contactPerson.setTitle(op.getOutWsStaffTitle());
				contactPerson.setSurname(op.getOutWsStaffSurname());
				contactPerson.setInitials(op.getOutWsStaffInitials());
				//contactPerson.setContactNumber(op.getOutWsStaffContactTelno());
				contactPerson.setCellNumber(op.getOutWsAddressV2CellNumber());
				contactPerson.setWorkNumber(op.getOutWsAddressV2WorkNumber());
				contactPerson.setHomeNumber(op.getOutWsAddressV2HomeNumber());
				contactPerson.setEmailAddress(op.getOutWsStaffEmailAddress());
				String contactName = contactPerson.getTitle().trim() + " " + contactPerson.getInitials().trim() + " " + contactPerson.getSurname();
				request.setAttribute("contactName", contactName);
				coverDocketForm.setContact(contactPerson);
				coverDocketForm.setLastUpdatedBy(op.getOutExamPaperLogUpdatedBy());
				SimpleDateFormat formatter = new SimpleDateFormat("d MMMMM yyyy");
				Date tempDate = new Date();
				tempDate = op.getOutExamPaperLogUpdatedOn().getTime();
				coverDocketForm.setLastUpdatedOn(formatter.format((tempDate)));
				paperDetail.setTotalPages(String.valueOf(op.getOutExamPaperCoverDocketTotalPages()));
				if (!op.getOutExamPaperCoverDocketExaminerPrtGc107().equalsIgnoreCase("")){
					paperDetail.setExaminerPrt(op.getOutExamPaperCoverDocketExaminerPrtGc107().trim());
				}				
			}
			
			
			paperDetail.setUniqueNo(String.valueOf(op.getOutGWsUniqueAssignmentUniqueNr(0)));
			paperDetail.setAdditionalInstructions(listAddInstructions);
			paperDetail.setCalcSpecialInstructions(listCalcInstructions);
			paperDetail.setInUseSpecialInstructions(listInUseInstructions);
			paperDetail.setStudyUnit(coverDocketForm.getExampaper().getStudyUnit());
			paperDetail.setStudyUnitDesc(op.getOutWsStudyUnitEngLongDescription());
			paperDetail.setExamYear(coverDocketForm.getExampaper().getExamYear());
			paperDetail.setExamPeriod(coverDocketForm.getExampaper().getExamPeriod());
			paperDetail.setExamPeriodDesc(coverDocketForm.getExampaper().getExamPeriodDesc());
			paperDetail.setPaperNo(coverDocketForm.getExampaper().getPaperNo());
			
			String stringDate="";
			String stringTime="";
			Date tempDate = new Date();
			if (op.getOutExamPeriodDateDate() instanceof Calendar) {
				SimpleDateFormat formatter = new SimpleDateFormat("d MMMMM yyyy");
				tempDate = op.getOutExamPeriodDateDate().getTime();
				stringDate = formatter.format(tempDate);
			}						
			if (op.getOutExamPeriodDateStartingTime() instanceof Calendar) {
				SimpleDateFormat formatter = new SimpleDateFormat("HH'H'mm");
				tempDate = op.getOutExamPeriodDateStartingTime().getTime();
				stringTime = formatter.format(tempDate);
			} 
				
			if ("3 March 1903".equals(stringDate)){
				stringDate="Departmental requirement";
				stringTime=" ";
			}
			if ("1 January 1901".equals(stringDate)){
				stringDate="Will be informed";
				stringTime=" ";
			}
			paperDetail.setExamDate(stringDate);
			paperDetail.setExamTime(stringTime);			
			paperDetail.setCombinedPapers(listEquivalent);
			paperDetail.setFirstExaminers(listFirstExaminers);
			paperDetail.setSecondExaminers(listSecondExaminers);
			paperDetail.setExternalExaminers(listExternalExaminers);
			paperDetail.setDurationHours(String.valueOf(op.getOutExamPaperDetailsDurationHours()));
			paperDetail.setDurationMin(String.valueOf(op.getOutExamPaperDetailsDurationMinutes()));
			paperDetail.setDurationDays("0");
			//if minutes=99 then DurationHours contains number of DurationDays
			if(op.getOutExamPaperDetailsDurationMinutes()==99){
				paperDetail.setDurationDays(String.valueOf(op.getOutExamPaperDetailsDurationHours()));
				paperDetail.setDurationHours("0");
				paperDetail.setDurationMin("0");
			}
			paperDetail.setMarkReadingCode(op.getOutExamPeriodDateMarkreadingCode());
			if (" ".equalsIgnoreCase(paperDetail.getMarkReadingCode()) || "".equalsIgnoreCase(paperDetail.getMarkReadingCode())){
				paperDetail.setMarkReadingCode("N");
			}
//			paperDetail.setNrOfDocumentsSubmitted(op.getOutExamPaperCoverDocketNrDocsSubmitted());
			paperDetail.setNrOfDocumentsSubmitted(Short.parseShort("0"));
			paperDetail.setWrittenTotal(String.valueOf(op.getOutExamPeriodDateScriptMarkTot()));
			paperDetail.setMcqTotal(String.valueOf(op.getOutExamPeriodDateMarkreadTot()));
			paperDetail.setPaperTotal(String.valueOf(op.getOutExamPeriodDatePaperMarkTot()));
			paperDetail.setRackSpacePages(String.valueOf(op.getOutExamPaperDetailsNrOfPages()));
			paperDetail.setSpecialMaterials(listMaterials);
			paperDetail.setAnswerBooks(listBooks);
			paperDetail.setSpecialInstructions(listInstructions);
			paperDetail.setLanguages(listLanguages);
			String examPaperLetterHead = ServerConfigurationService.getString("ExamPaperLetterHead");
			examPaperLetterHead = examPaperLetterHead+"?SUN1="+coverDocketForm.getExampaper().getStudyUnit();
			examPaperLetterHead = examPaperLetterHead+"&ExamYear="+coverDocketForm.getExampaper().getExamYear();
			examPaperLetterHead = examPaperLetterHead+"&ExamPeriod="+coverDocketForm.getExampaper().getExamPeriod();
			examPaperLetterHead = examPaperLetterHead+"&PNr="+coverDocketForm.getExampaper().getPaperNo();
			examPaperLetterHead = examPaperLetterHead+"&Action=Display";
			examPaperLetterHead = examPaperLetterHead+"&System=MyUnisa";
			coverDocketForm.setExamPaperLetterHead("<a href=\""+examPaperLetterHead+"\">"+"Letterhead</a>");
			coverDocketForm.setExamPaperLetterHead("<a href=\""+examPaperLetterHead+"\" target=\"_self\">"+"Letterhead</a>");
			                                     //<a href="link" target="_parent"> Letterhead</a>);
			//Set parameters for letterhead link
			//Map parmmap = new HashMap();
			//parmmap.put("SUN1", coverDocketForm.getExampaper().getStudyUnit());		
			//parmmap.put("ExamYear",coverDocketForm.getExampaper().getExamYear());
			//parmmap.put("ExamPeriod", coverDocketForm.getExampaper().getExamPeriod());
			//parmmap.put("PNr", coverDocketForm.getExampaper().getPaperNo());
			//parmmap.put("Action", "Display");
			//parmmap.put("System", "MyUnisa");
			//request.setAttribute("parmmap",parmmap);
			coverDocketForm.setExampaper(paperDetail);
								
			if (coverDocketForm.getStatus().equalsIgnoreCase("CLOSED")||!coverDocketForm.isUpdateRights()){
				return displayStep3(mapping, form, request, response);
			}
		return "step1";	
	}
	public String displayStep2(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		ExamPaperCoverDocketForm coverDocketForm = (ExamPaperCoverDocketForm) form;	
		ActionMessages messages = new ActionMessages();
		//Do step 1 validation
		if (coverDocketForm.getExampaper().getExamDate().equals("Departmental requirement") &&
				coverDocketForm.getExampaper().getDurationDays().equalsIgnoreCase("0")) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
							"Departmental requirement.  This is not a written examination."));				
		}
		if (coverDocketForm.getExampaper().getExamDate().equals("Will be informed")) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
							"Examination date must be set."));
		}
		if (coverDocketForm.getExampaper().getExamDate().equals("Departmental requirement") ||
				coverDocketForm.getExampaper().getExamDate().equals("Will be informed")){
			//do nothing
		}else{
			if (coverDocketForm.getExampaper().getDurationDays().equalsIgnoreCase("0")&&
					coverDocketForm.getExampaper().getDurationHours().equalsIgnoreCase("0")&&
					coverDocketForm.getExampaper().getDurationMin().equalsIgnoreCase("0")){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
								"Examination duration must be set."));
			}				
		}
		EquivalentRecord eqv = new EquivalentRecord();
		for (int i=0 ; i < coverDocketForm.getExampaper().getCombinedPapers().size();i++){
			eqv = (EquivalentRecord)coverDocketForm.getExampaper().getCombinedPapers().get(i);
			if (!coverDocketForm.getExampaper().getExamDate().equalsIgnoreCase(eqv.getExamDate())||
					!coverDocketForm.getExampaper().getExamTime().equalsIgnoreCase(eqv.getExamTime())||
					!coverDocketForm.getExampaper().getDurationDays().equalsIgnoreCase(eqv.getDurationDays())||
					!coverDocketForm.getExampaper().getDurationHours().equalsIgnoreCase(eqv.getDurationHours())||
					!coverDocketForm.getExampaper().getDurationMin().equalsIgnoreCase(eqv.getDurationMin())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
								"Examination paper and combined papers dates and/or durations differ."));
				i = coverDocketForm.getExampaper().getCombinedPapers().size();
			}
		}
		
		int numberOfExaminers = 0;
		numberOfExaminers = coverDocketForm.getExampaper().getFirstExaminers().size() + 
		coverDocketForm.getExampaper().getSecondExaminers().size() +
		coverDocketForm.getExampaper().getExternalExaminers().size();
		if (numberOfExaminers < 2) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
							"At least two Examiners must be specified whether or not a detailed list of Examiners must be printed on the examination paper head, or only the text : 'EXAMINATION PANEL AS APPROVED BY THE DEPARTMENT'.  Indicate in the next step whether you require a detailed Examiner list or only the wording: 'EXAMINATION PANEL AS APPROVED BY THE DEPARTMENT'"));
		}
		if (!messages.isEmpty()) {
			addErrors(request,messages);
			String contactName="";
			if (coverDocketForm.getDocketExists()==true) {
				contactName = ((PersonnelRecord)coverDocketForm.getContact()).getTitle() + " " +
				((PersonnelRecord)coverDocketForm.getContact()).getInitials() + " " +
				((PersonnelRecord)coverDocketForm.getContact()).getSurname();
			}
			request.setAttribute("contactName", contactName);
			return "step1";
		}
		Boolean xPaperLogExists = false;
		ExamPaperCoverDocketDAO dao = new ExamPaperCoverDocketDAO();
		
		xPaperLogExists = dao.xPaperLogExists(coverDocketForm.getExampaper().getStudyUnit(),coverDocketForm.getExampaper().getExamYear(), coverDocketForm.getExampaper().getExamPeriod(),coverDocketForm.getExampaper().getPaperNo(), "QUESTION");
		coverDocketForm.setXpaperLogExists(xPaperLogExists);	
		
		//StudentSystemGeneralDAO dao = new StudentSystemGeneralDAO();
		//List markReadingCodes = dao.getGenCodes((short)38,2);
		List fullPartialNoneCodes = coverDocketForm.getFullPartialNoneCodes();
		request.setAttribute("fullPartialNoneCodes",fullPartialNoneCodes);
		List markReadingCodes = coverDocketForm.getMarkReadingGenCodes();
		request.setAttribute("markReadingCodes", markReadingCodes);
		List yesNoCodes =coverDocketForm.getYesNoCodes();
		request.setAttribute("yesNoCodes", yesNoCodes);
		request.setAttribute("instructions", coverDocketForm.getExampaper().getSpecialInstructions());
		request.setAttribute("openBookCodes", coverDocketForm.getOpenBookCodes());
		
		if (coverDocketForm.getStatus().equalsIgnoreCase("CLOSED")) {
			return "step3";
		}
		return "step2";	
	}	
	public String displayStep3(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		//https//mydev.unisa.ac.za/portal/tool
		//String serverPath = ServerConfigurationService.getToolUrl();			
		
		ActionMessages messages = new ActionMessages();
		ExamPaperCoverDocketForm coverDocketForm = (ExamPaperCoverDocketForm) form;
		//Initialize values
		if (coverDocketForm.getExampaper().getWrittenTotal()==null || 
				coverDocketForm.getExampaper().getWrittenTotal().trim().equalsIgnoreCase("")){
			coverDocketForm.getExampaper().setWrittenTotal("0");
		}
		if (coverDocketForm.getExampaper().getMcqTotal()==null || 
				coverDocketForm.getExampaper().getMcqTotal().trim().equalsIgnoreCase("")){
			coverDocketForm.getExampaper().setMcqTotal("0");
		}
		if (coverDocketForm.getExampaper().getPaperTotal()==null || 
				coverDocketForm.getExampaper().getPaperTotal().trim().equalsIgnoreCase("")){
			coverDocketForm.getExampaper().setPaperTotal("0");
		}
		if (coverDocketForm.getExampaper().getTotalPages()==null || 
				coverDocketForm.getExampaper().getTotalPages().trim().equalsIgnoreCase("")){
			coverDocketForm.getExampaper().setTotalPages("0");
		}
				
		//Do input validation - step2 
		if (coverDocketForm.getCurrentPage().equalsIgnoreCase("step2")) {
			messages = (ActionMessages) coverDocketForm.validate(mapping, request);
			
			if (!messages.isEmpty()) {
				addErrors(request,messages);
				return displayStep2(mapping, form, request, response);
			}
			
			if ((coverDocketForm.getExampaper().getMarkReadingCode().equals("F")) 
				&& (coverDocketForm.getFillInPaper().equalsIgnoreCase("F"))) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
									"Full mark reading and full fill-in examination question paper can't both be selected."));
				}
			if ((coverDocketForm.getExampaper().getMarkReadingCode().equals("F")) 
					&& (coverDocketForm.getFillInPaper().equalsIgnoreCase("P"))) {
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage",
										"Full mark reading and partial fill-in examination question paper can't both be selected."));
					}
			if ((coverDocketForm.getExampaper().getMarkReadingCode().equals("N")) 
					&& (coverDocketForm.getFillInPaper().equalsIgnoreCase("P"))) {
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage",
										"None mark reading and partial fill-in examination question paper can't both be selected."));
					}
			if ((coverDocketForm.getExampaper().getMarkReadingCode().equals("P")) 
					&& (coverDocketForm.getFillInPaper().equalsIgnoreCase("F"))) {
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage",
										"Partial mark reading and full fill-in examination question paper can't both be selected."));
					}
				
			if (coverDocketForm.getExampaper().getMarkReadingCode().equals("F")) {
				if (coverDocketForm.getExampaper().getWrittenTotal() != null && !coverDocketForm.getExampaper().getWrittenTotal().equals("0")) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
									"Full markreading.  Written total should be 0."));
				}
				if (coverDocketForm.getExampaper().getMcqTotal() == null || coverDocketForm.getExampaper().getMcqTotal().equals("0")) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
									"MCQ total is required."));
				}
				if (Integer.parseInt(coverDocketForm.getExampaper().getMcqTotal()) != Integer.parseInt(coverDocketForm.getExampaper().getPaperTotal())) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
									"Paper total must be equal to MCQ total."));
				}
			}
			if (coverDocketForm.getExampaper().getMarkReadingCode().equals("N")) {
				if (coverDocketForm.getExampaper().getMcqTotal() != null && !coverDocketForm.getExampaper().getMcqTotal().equals("0")) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
									"Written examination only.  MCQ total should be 0."));
				}
				if (coverDocketForm.getExampaper().getWrittenTotal() == null || coverDocketForm.getExampaper().getWrittenTotal().equals("0")) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
									"Written total is required."));
				}
				if (Integer.parseInt(coverDocketForm.getExampaper().getWrittenTotal()) != Integer.parseInt(coverDocketForm.getExampaper().getPaperTotal())) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
									"Paper total must be equal to Written total."));
				}
			}
			if (coverDocketForm.getExampaper().getMarkReadingCode().equals("P")) {
				if (coverDocketForm.getExampaper().getMcqTotal() == null || coverDocketForm.getExampaper().getMcqTotal().equals("0")) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
									"MCQ total is required."));
				}
				if (coverDocketForm.getExampaper().getWrittenTotal() == null || coverDocketForm.getExampaper().getWrittenTotal().equals("0")) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
									"Written total is required."));
				}
				if ((Integer.parseInt(coverDocketForm.getExampaper().getWrittenTotal()) + Integer.parseInt(coverDocketForm.getExampaper().getMcqTotal())) != Integer.parseInt(coverDocketForm.getExampaper().getPaperTotal())) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
									"Written total and Mcq total must accummulate to Paper total."));
				}
				
			}
			//Number of pages must be greater > 0 (actual pages)
			if (coverDocketForm.getExampaper().getTotalPages() == null || coverDocketForm.getExampaper().getTotalPages().equals("0")) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
								"Number of pages is required."));
			}
//			if (coverDocketForm.getExampaper().getNrOfDocumentsSubmitted()==null || coverDocketForm.getExampaper().getNrOfDocumentsSubmitted()==0){
//				messages.add(ActionMessages.GLOBAL_MESSAGE,
//						new ActionMessage("message.generalmessage",
//								"Number of documents submitted is required."));
//			}
			
			//Determine if Loose Answer Sheet(s) is selected / Mark reading sheet selected
			List listMaterial = coverDocketForm.getExampaper().getSpecialMaterials();
			String markReadingSheetSelected = "N";
			String LooseAnswerSheetSelected ="N";
			for (int i=0; i < coverDocketForm.getIndexNrSelectedMaterial().length; i++) {
				String array[] = coverDocketForm.getIndexNrSelectedMaterial();
				if (((SpecialMaterialRecord)listMaterial.get(Integer.parseInt(array[i]))).getCode().equalsIgnoreCase("MB")){
					markReadingSheetSelected = "Y";
				}
				if (((SpecialMaterialRecord)listMaterial.get(Integer.parseInt(array[i]))).getCode().equalsIgnoreCase("LA")){
					LooseAnswerSheetSelected = "Y";				
				}
			}
			
			//Mark reading sheet must be selected if full or partial mark reading paper
			if (coverDocketForm.getExampaper().getMarkReadingCode().equalsIgnoreCase("F") ||
				coverDocketForm.getExampaper().getMarkReadingCode().equalsIgnoreCase("P")) {
				if (markReadingSheetSelected.equalsIgnoreCase("N")){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
							"Special Material: Mark reading sheet must be selected if full or partial mark reading paper is selected."));
				}
			}else {
				//Mark reading sheet may only be selected for full or partial mark reading paper
				if (markReadingSheetSelected.equalsIgnoreCase("Y")){
					if (markReadingSheetSelected.equalsIgnoreCase("Y")){
						messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
							"Special Material: Mark reading sheet only applicable if full or partial mark reading paper is selected."));
					}
				}
			}
			
			//Loose answer sheets may not be selected if full markreading paper
			if (coverDocketForm.getExampaper().getMarkReadingCode().equalsIgnoreCase("F") && LooseAnswerSheetSelected.equalsIgnoreCase("Y")){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
								"Special Material: Loose answer sheet(s) not applicable when full mark reading paper is selected."));
			}
			
			//Validate instruction selection against other values selected
			//Instructions 8726 & 8736 may only be selected if Special Material, Loose answer sheet(s) is selected
			//Instruction 8733 may only be selected if No. of answer books > 1
			String arrayInstruction[] = coverDocketForm.getInUseInstructionSelection();
			String instruction8733Selected ="N";
			String instruction8726Selected ="N";
			String instruction8736Selected="N";
			String instruction8754Selected="N";
			String instruction8754 = "";
			String instruction8733 = "";
			String instruction8726 = "";
			String instruction8736 = "";
			
			for (int i=0; i < coverDocketForm.getExampaper().getInUseSpecialInstructions().size(); i++) {
				if ((((LabelValueBean)coverDocketForm.getExampaper().getInUseSpecialInstructions().get(i)).getValue()).equals("8733")){
					instruction8733 = ((LabelValueBean)coverDocketForm.getExampaper().getInUseSpecialInstructions().get(i)).getLabel();
					}
				if ((((LabelValueBean)coverDocketForm.getExampaper().getInUseSpecialInstructions().get(i)).getValue()).equals("8726")){
					instruction8726 = ((LabelValueBean)coverDocketForm.getExampaper().getInUseSpecialInstructions().get(i)).getLabel();
					}
				if ((((LabelValueBean)coverDocketForm.getExampaper().getInUseSpecialInstructions().get(i)).getValue()).equals("8736")){
					instruction8736 = ((LabelValueBean)coverDocketForm.getExampaper().getInUseSpecialInstructions().get(i)).getLabel();
					}
				if ((((LabelValueBean)coverDocketForm.getExampaper().getInUseSpecialInstructions().get(i)).getValue()).equals("8754")){
					instruction8754 = ((LabelValueBean)coverDocketForm.getExampaper().getInUseSpecialInstructions().get(i)).getLabel();
					}			
			}
						
			for (int i = 0; i<coverDocketForm.getInUseInstructionSelection().length; i++) {
				if ((((LabelValueBean)coverDocketForm.getExampaper().getInUseSpecialInstructions().get(Integer.parseInt(arrayInstruction[i]))).getValue()).equals("8726")){
					instruction8726Selected ="Y";
					}	
				if ((((LabelValueBean)coverDocketForm.getExampaper().getInUseSpecialInstructions().get(Integer.parseInt(arrayInstruction[i]))).getValue()).equals("8736")){
					instruction8736Selected ="Y";	
					}	
				if ((((LabelValueBean)coverDocketForm.getExampaper().getInUseSpecialInstructions().get(Integer.parseInt(arrayInstruction[i]))).getValue()).equals("8733")){
					instruction8733Selected="Y";
					}
				if ((((LabelValueBean)coverDocketForm.getExampaper().getInUseSpecialInstructions().get(Integer.parseInt(arrayInstruction[i]))).getValue()).equals("8754")){
					instruction8754Selected="Y";
					}
			}
			if (coverDocketForm.getExampaper().getMarkReadingCode().equalsIgnoreCase("F")) {
				if (instruction8726Selected.equalsIgnoreCase("Y")){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
									"Special Instruction: \"" + instruction8726 + "\" is not applicable when full mark reading sheet is selected."));
				}	
				if (instruction8736Selected.equalsIgnoreCase("Y")){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
									"Special Instruction: \"" + instruction8736 + "\" is not applicable when full mark reading sheet is selected."));
				}	
			}
			if (coverDocketForm.getFillInPaper().equalsIgnoreCase("F")) {
				if (instruction8726Selected.equalsIgnoreCase("Y")){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
									"Special Instruction: \"" + instruction8726 + "\" is not applicable when full fill-in examination question paper is selected."));
				}	
				if (instruction8736Selected.equalsIgnoreCase("Y")){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
									"Special Instruction: \"" + instruction8736 + "\" is not applicable when full fill-in examination question paper is selected."));
				}	
			}
					
			//Special material average must be numeric and greater than 0 if selected		
			for (int i=0; i < coverDocketForm.getIndexNrSelectedMaterial().length; i++) {
				String array[] = coverDocketForm.getIndexNrSelectedMaterial();
				if (testAverageValue(((SpecialMaterialRecord)listMaterial.get(Integer.parseInt(array[i]))).getAverage())){
					float average = Float.parseFloat(((SpecialMaterialRecord)listMaterial.get(Integer.parseInt(array[i]))).getAverage());
					if (average == 0){
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("errors.checkbox.integer.required",
										"Special material average"));
						i = coverDocketForm.getIndexNrSelectedMaterial().length;
					}
					if (average < 0) {
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage",
										"Special material average must be greater than 0"));
						i = coverDocketForm.getIndexNrSelectedMaterial().length;					
					}
				}else {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("errors.checkbox.integer.required",
									"Special material average"));
					i = coverDocketForm.getIndexNrSelectedMaterial().length;
				}
			}
			
			String greenBookSelected = "N";
			//Answer book average must be numeric and greater than 0 if selected
			List listBook = coverDocketForm.getExampaper().getAnswerBooks();
			for (int i=0; i < coverDocketForm.getIndexNrSelectedBook().length; i++) {
				String array[] = coverDocketForm.getIndexNrSelectedBook();
				if (((SpecialMaterialRecord)listBook.get(Integer.parseInt(array[i]))).getCode().equalsIgnoreCase("GB")){
					greenBookSelected ="Y";
				}
				if (testAverageValue(((SpecialMaterialRecord)listBook.get(Integer.parseInt(array[i]))).getAverage())){
					float average = Float.parseFloat(((SpecialMaterialRecord)listBook.get(Integer.parseInt(array[i]))).getAverage());
					if (average == 0){
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("errors.checkbox.integer.required",
										"No. of books per student"));
						i = coverDocketForm.getIndexNrSelectedMaterial().length;
					}
					if (average < 0) {
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage",
										"No. of books per student must be greater than 0"));
						i = coverDocketForm.getIndexNrSelectedMaterial().length;					
					}
					if (instruction8733Selected.equalsIgnoreCase("Y") && coverDocketForm.getIndexNrSelectedBook().length <= 1 && average <= 1) {
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage",
										"Special Instruction: \"" + instruction8733 + "\" is only applicable if No. of books per student is more than 1."));
					}
					if (instruction8733Selected.equalsIgnoreCase("N") && coverDocketForm.getIndexNrSelectedBook().length >= 1 && average > 1) {
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage",
										"Indicate more than one answer book only if questions must be answered in separate answer books. Special instruction: \"" + instruction8733 + "\" must be selected."));
					}
				}else {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("errors.checkbox.integer.required",
									"No. of books per student"));
					i = coverDocketForm.getIndexNrSelectedMaterial().length;
				}
			}
			if (instruction8754Selected.equalsIgnoreCase("Y") && greenBookSelected.equalsIgnoreCase("N")){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
								"Special Instruction: \"" + instruction8754 + "\" is only applicable if Green (Ordinary) book is selected."));
			}
			if (coverDocketForm.getNoBookRequired() == true && (coverDocketForm.getIndexNrSelectedBook().length != 0 || coverDocketForm.getIndexNrSelectedBook() == null)){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
								"If No book required, no book should be selected."));
			}
			if (coverDocketForm.getNoBookRequired() == false && (coverDocketForm.getIndexNrSelectedBook().length == 0 || coverDocketForm.getIndexNrSelectedBook() == null)){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
								"Select No book required or select at least one answer book."));
			}
			if (coverDocketForm.getNoBookRequired() == true && 
					"N".equalsIgnoreCase(coverDocketForm.getFillInPaper())&& 
					"N".equalsIgnoreCase(coverDocketForm.getExampaper().getMarkReadingCode()) &&
					!"Y".equalsIgnoreCase(coverDocketForm.getDeclaration())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
								"If not a declaration, either examination paper must exist of a markreading reading component, a fill-in examination question paper component or a book must be required."));
			}
			if (coverDocketForm.getFillInPaper().equalsIgnoreCase("P") || coverDocketForm.getFillInPaper().equalsIgnoreCase("F")){
				if (coverDocketForm.getKeepPaper().equalsIgnoreCase("Y")){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
									"Student may not keep examination paper if full or partial fill-in examination paper"));
				}
			}
			if (coverDocketForm.getCalcPermit().equalsIgnoreCase("Y")){
				if (coverDocketForm.getCalcInstructionSelection().length==0){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
									"If calculator permissible, select type of calculator."));
				}
			}
			if (coverDocketForm.getCalcInstructionSelection().length>0 &&
					coverDocketForm.getCalcPermit().equalsIgnoreCase("N")){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
								"Pocket calculator permissible must be Yes, if a type of calculator is selected."));
			}
			if (coverDocketForm.getIndexNrSelectedLanguage() == null || coverDocketForm.getIndexNrSelectedLanguage().length==0) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
								"At least one language medium should be selected."));
			}
			if (coverDocketForm.getOpenBook().equalsIgnoreCase("P")){
				String additionalInstructionSpecified = "N";
				for (int i = 0; i < coverDocketForm.getExampaper().getAdditionalInstructions().size(); i++){
					if (!coverDocketForm.getExampaper().getAdditionalInstructions().get(i).toString().trim().equalsIgnoreCase("")){
						additionalInstructionSpecified = "Y";
					}
				}
				if (additionalInstructionSpecified.equals("N")){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
									"For a partial open book examination, paragraph (F) must be used to specify material permissible in examination"));
				}
			}
					
	        //Get Contact person information
			PersonnelDAO contactDao = new PersonnelDAO();
			PersonnelRecord contactPerson = new PersonnelRecord();
			if (!coverDocketForm.getContactPerson().equalsIgnoreCase("") && coverDocketForm.getContactPerson()!=null){
			
		        contactPerson = contactDao.getPersonnelInfo(coverDocketForm.getContactPerson());
				if (contactPerson.getSurname().equalsIgnoreCase("NF")){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("errors.invalid",
									"Contact personnel number"));
				}		
			}
			
			if (!messages.isEmpty()) {
				addErrors(request,messages);
				return displayStep2(mapping, form, request, response);
			}
		}
		//end of page 2 validation
		
		PersonnelDAO contactDao = new PersonnelDAO();
		PersonnelRecord contactPerson = new PersonnelRecord();
		if (!coverDocketForm.getContactPerson().equalsIgnoreCase("") && coverDocketForm.getContactPerson()!=null){
		
	        contactPerson = contactDao.getPersonnelInfo(coverDocketForm.getContactPerson());				
		}
		String contactName=coverDocketForm.getContactPerson() + " not found";
		if (!contactPerson.getSurname().equalsIgnoreCase("NF")){
			contactName = contactPerson.getTitle().trim() + " " + contactPerson.getInitials().trim() + " " + contactPerson.getSurname();
		}
					
		request.setAttribute("contactName", contactName);		
		
		if (coverDocketForm.getCurrentPage().equalsIgnoreCase("step2")){
			coverDocketForm.setContact(contactPerson);	
		}
		
		//Get markreading code description
		String mcqDesc="";
		for (int i = 0; i < ((List)coverDocketForm.getMarkReadingGenCodes()).size(); i++){
		    if (coverDocketForm.getExampaper().getMarkReadingCode().equalsIgnoreCase(((Gencod)((List)coverDocketForm.getMarkReadingGenCodes()).get(i)).getCode())){
			    	mcqDesc =((Gencod)((List)coverDocketForm.getMarkReadingGenCodes()).get(i)).getEngDescription();
			    }
		}
		request.setAttribute("mcqDesc", mcqDesc);
		
		//Get fill-in examination paper & open book examination desc
		String fillInDesc="";		
		String openBookDesc="";
		
		for (int i = 0; i < ((List)coverDocketForm.getFullPartialNoneCodes()).size(); i++){
		    if (coverDocketForm.getFillInPaper().equalsIgnoreCase(((Gencod)((List)coverDocketForm.getFullPartialNoneCodes()).get(i)).getCode())){
			    	fillInDesc =((Gencod)((List)coverDocketForm.getFullPartialNoneCodes()).get(i)).getEngDescription();
			    }
		    if (coverDocketForm.getOpenBook().equalsIgnoreCase(((Gencod)((List)coverDocketForm.getOpenBookCodes()).get(i)).getCode())){
		    	openBookDesc =((Gencod)((List)coverDocketForm.getOpenBookCodes()).get(i)).getEngDescription();
		    }
		}
		
		request.setAttribute("fillInDesc", fillInDesc);
		request.setAttribute("openBookDesc", openBookDesc);
		
		//Get keep Paper & calculator permissible desc
		String keepPaperDesc="";
		String calcPermitDesc="";
		
		for (int i = 0; i < ((List)coverDocketForm.getYesNoCodes()).size(); i++){
		    if (coverDocketForm.getKeepPaper().equalsIgnoreCase(((Gencod)((List)coverDocketForm.getYesNoCodes()).get(i)).getCode())){
		    	keepPaperDesc =((Gencod)((List)coverDocketForm.getYesNoCodes()).get(i)).getEngDescription();
		    }
		    if (coverDocketForm.getCalcPermit().equalsIgnoreCase(((Gencod)((List)coverDocketForm.getYesNoCodes()).get(i)).getCode())){
		    	calcPermitDesc =((Gencod)((List)coverDocketForm.getYesNoCodes()).get(i)).getEngDescription();
		    }		    
		}
		
		request.setAttribute("keepPaperDesc", keepPaperDesc);
		request.setAttribute("calcPermitDesc", calcPermitDesc);
		
		String arrayLanguage[] = coverDocketForm.getIndexNrSelectedLanguage();
		List selectedLanguages = new ArrayList();
		
		for (int i = 0; i<arrayLanguage.length; i++) {
			selectedLanguages.add(coverDocketForm.getExampaper().getLanguages().get(Integer.parseInt(arrayLanguage[i])));
		}
		request.setAttribute("selectedLanguages", selectedLanguages);
		
		String arrayMaterial[] = coverDocketForm.getIndexNrSelectedMaterial();
		List selectedSpecialMaterials = new ArrayList();
				
		for (int i = 0; i<arrayMaterial.length; i++) {
			
			NumberFormat f = NumberFormat.getInstance();
			String tempString="";
			if (f instanceof DecimalFormat){
				//apply edit pattern to average
				((DecimalFormat) f).applyPattern("#0.0#");
				f.setMaximumFractionDigits(2);
				f.setMaximumIntegerDigits(2);					
				
				tempString = ((SpecialMaterialRecord)(coverDocketForm.getExampaper().getSpecialMaterials().get(Integer.parseInt(arrayMaterial[i])))).getAverage();
				float tempFloat = Float.parseFloat(tempString);
				tempString = f.format(tempFloat);
				((SpecialMaterialRecord)(coverDocketForm.getExampaper().getSpecialMaterials().get(Integer.parseInt(arrayMaterial[i])))).setAverage(tempString);
			}
			selectedSpecialMaterials.add(coverDocketForm.getExampaper().getSpecialMaterials().get(Integer.parseInt(arrayMaterial[i])));
		}
		request.setAttribute("selectedSpecialMaterials", selectedSpecialMaterials);
		
		String arrayBook[] = coverDocketForm.getIndexNrSelectedBook() ;
		List selectedBooks = new ArrayList();
		
		for (int i = 0; i<arrayBook.length; i++) {
			//apply edit pattern to average
			NumberFormat f = NumberFormat.getInstance();
			String tempString="";
			if (f instanceof DecimalFormat){
				((DecimalFormat) f).applyPattern("#0.0#");
				f.setMaximumFractionDigits(2);
				f.setMaximumIntegerDigits(2);					
				
				tempString = ((SpecialMaterialRecord)(coverDocketForm.getExampaper().getAnswerBooks().get(Integer.parseInt(arrayBook[i])))).getAverage();
				float tempFloat = Float.parseFloat(tempString);
				tempString = f.format(tempFloat);
				((SpecialMaterialRecord)(coverDocketForm.getExampaper().getAnswerBooks().get(Integer.parseInt(arrayBook[i])))).setAverage(tempString);
			}
			selectedBooks.add(coverDocketForm.getExampaper().getAnswerBooks().get(Integer.parseInt(arrayBook[i])));
		}
		request.setAttribute("selectedBooks", selectedBooks);
		
		//add special instructions auto selected by program
		List listBook = coverDocketForm.getExampaper().getAnswerBooks();
		String greenBookSelected="";
		for (int i=0; i < coverDocketForm.getIndexNrSelectedBook().length; i++) {
			String array[] = coverDocketForm.getIndexNrSelectedBook();
			if (((SpecialMaterialRecord)listBook.get(Integer.parseInt(array[i]))).getCode().equalsIgnoreCase("GB")){
				greenBookSelected ="Y";
				i=coverDocketForm.getIndexNrSelectedBook().length;
			}
		}
		List autoInstructions = new ArrayList();
				
		if (coverDocketForm.getExampaper().getMarkReadingCode().equalsIgnoreCase("F")){
			autoInstructions.add("8709");
			if (greenBookSelected.equalsIgnoreCase("Y")){
				autoInstructions.add("8740");
			}
			if (greenBookSelected.equalsIgnoreCase("")) {
				autoInstructions.add("8731");
			}			
		}
		if (coverDocketForm.getExampaper().getMarkReadingCode().equalsIgnoreCase("P")){
			autoInstructions.add("8709");	
			if (coverDocketForm.getFillInPaper().equalsIgnoreCase("P") &&
				coverDocketForm.getNoBookRequired()==true){
				autoInstructions.add("8732");
			}
		}
		if (coverDocketForm.getFillInPaper().equalsIgnoreCase("F")){
			autoInstructions.add("8730");	
		}
		if ((coverDocketForm.getFillInPaper().equalsIgnoreCase("P")) &&
			!coverDocketForm.getExampaper().getMarkReadingCode().equalsIgnoreCase("P")){
			autoInstructions.add("8734");	
		}
		if (coverDocketForm.getOpenBook().equalsIgnoreCase("F")){
			autoInstructions.add("8710");
		}
		if (coverDocketForm.getOpenBook().equalsIgnoreCase("P")){
			autoInstructions.add("8759");
		}
		if (coverDocketForm.getOpenBook().equalsIgnoreCase("N")){
			autoInstructions.add("8758");
		}
		if (coverDocketForm.getKeepPaper().equalsIgnoreCase("N")){
			autoInstructions.add("8701");
		}
		if (coverDocketForm.getDeclaration().equalsIgnoreCase("Y")){
			autoInstructions.add("8763");
		}
		
		String arrayCalcInstruction[] = coverDocketForm.getCalcInstructionSelection();
		String calcinstruction8702Selected="N";
		String calcinstruction8704Selected="N";
		String calcinstruction8705Selected="N";
		
		for (int i = 0; i<coverDocketForm.getCalcInstructionSelection().length; i++) {
			if ((((LabelValueBean)coverDocketForm.getExampaper().getCalcSpecialInstructions().get(Integer.parseInt(arrayCalcInstruction[i]))).getValue()).equals("8702")){
				calcinstruction8702Selected ="Y";
				}	
			if ((((LabelValueBean)coverDocketForm.getExampaper().getCalcSpecialInstructions().get(Integer.parseInt(arrayCalcInstruction[i]))).getValue()).equals("8704")){
				calcinstruction8704Selected ="Y";	
				}	
			if ((((LabelValueBean)coverDocketForm.getExampaper().getCalcSpecialInstructions().get(Integer.parseInt(arrayCalcInstruction[i]))).getValue()).equals("8705")){
				calcinstruction8705Selected="Y";
				}			
		}
		
		if (calcinstruction8702Selected.equalsIgnoreCase("N")){
			if (calcinstruction8704Selected.equalsIgnoreCase("Y")||
					calcinstruction8705Selected.equalsIgnoreCase("Y")){
				autoInstructions.add("8702");
			}
		}
        //get selected calculator instructions & other special instructions selected
		List listInstructionsDisplay = new ArrayList();
		List selectedInstructions = new ArrayList();
		String arrayInstruction[] = coverDocketForm.getInUseInstructionSelection();
		listInstructionsDisplay = coverDocketForm.getExampaper().getInUseSpecialInstructions();
		
		List listCalcInstructions = new ArrayList();
		List selectedCalcInstructions = new ArrayList();
		listCalcInstructions = coverDocketForm.getExampaper().getCalcSpecialInstructions();
		
		String[] arrayselectedSpecialInstructions = new String[autoInstructions.size() + arrayInstruction.length + arrayCalcInstruction.length];
		int index=0;
		boolean instructionsSelected = false;
		
		for (int i = 0; i<arrayInstruction.length; i++) {
			index = i;
			selectedInstructions.add(((LabelValueBean)listInstructionsDisplay.get(Integer.parseInt(arrayInstruction[i]))).getLabel());	
			arrayselectedSpecialInstructions[index]=((LabelValueBean)listInstructionsDisplay.get(Integer.parseInt(arrayInstruction[i]))).getValue();
			instructionsSelected = true;
			
		}
		request.setAttribute("selectedInstructions", selectedInstructions);			
		
		
		for (int i = 0; i<arrayCalcInstruction.length; i++) {
			if (arrayInstruction.length == 0){
				index = i;				
			}
			else {			
				index = index + 1;
			}
			selectedCalcInstructions.add(((LabelValueBean)listCalcInstructions.get(Integer.parseInt(arrayCalcInstruction[i]))).getLabel());	
			arrayselectedSpecialInstructions[index]=((LabelValueBean)listCalcInstructions.get(Integer.parseInt(arrayCalcInstruction[i]))).getValue();
			instructionsSelected = true;
		}
		request.setAttribute("selectedCalcInstructions", selectedCalcInstructions);
		request.setAttribute("instructionsSelected", instructionsSelected);	
		
		List selectedAutoInstructions = new ArrayList();
		List listInstructions = new ArrayList();
		SpecialInstructionsDAO daoinstr = new SpecialInstructionsDAO();
		listInstructions = daoinstr.getSpecialInstructionsAll();
		for (int i = 0; i< autoInstructions.size(); i++) {
			if (arrayInstruction.length == 0 && arrayCalcInstruction.length == 0){
				index = i;				
			}
			else {			
				index = index + 1;
			}
			
			arrayselectedSpecialInstructions[index] = autoInstructions.get(i).toString();
						
			for(int j=0; j<listInstructions.size(); j++){
				if (autoInstructions.get(i).toString().equals(((LabelValueBean)listInstructions.get(j)).getValue())) {
					selectedAutoInstructions.add(((LabelValueBean)listInstructions.get(j)).getLabel());
					}				
				}
				
			}
						
		request.setAttribute("selectedAutoInstructions",selectedAutoInstructions);
		coverDocketForm.setSpecialInstructionSelection(arrayselectedSpecialInstructions);
		
		if ((Integer.parseInt(coverDocketForm.getExampaper().getExamYear())==2011 &&
				coverDocketForm.getExampaper().getExamPeriod().compareTo(Short.parseShort("6"))>0) ||
				Integer.parseInt(coverDocketForm.getExampaper().getExamYear())>2011){
				if (coverDocketForm.getFillInPaper().equalsIgnoreCase("F") ||
						coverDocketForm.getFillInPaper().equalsIgnoreCase("P")){
					coverDocketForm.setPaperColour("IVORY");
				}else if (coverDocketForm.getKeepPaper().equalsIgnoreCase("N")){
					coverDocketForm.setPaperColour("WHITE");
				}else{
					coverDocketForm.setPaperColour("WHITE");
				}	
		}else{
				if (coverDocketForm.getFillInPaper().equalsIgnoreCase("F") ||
						coverDocketForm.getFillInPaper().equalsIgnoreCase("P")){
					coverDocketForm.setPaperColour("IVORY");
				}else if (coverDocketForm.getKeepPaper().equalsIgnoreCase("Y")){
					coverDocketForm.setPaperColour("WHITE");
				}else{
					coverDocketForm.setPaperColour("GREEN");
				}	
		}				
				
		//SBL papers must always be IVORY
		if (coverDocketForm.getSblFlag().equalsIgnoreCase("Y")){
			coverDocketForm.setPaperColour("IVORY");
		}
	 
		coverDocketForm.setMcqInstructionSheet(false);
		coverDocketForm.setAttendanceRegister(false);
		if (coverDocketForm.getExampaper().getMarkReadingCode().equalsIgnoreCase("F") ||
				coverDocketForm.getExampaper().getMarkReadingCode().equalsIgnoreCase("P")){
			coverDocketForm.setMcqInstructionSheet(true);			
		}
		if (coverDocketForm.getFillInPaper().equalsIgnoreCase("F") ||
				coverDocketForm.getExampaper().getMarkReadingCode().equalsIgnoreCase("F") ||
				coverDocketForm.getNoBookRequired()==true){
			coverDocketForm.setAttendanceRegister(true);
		}
		if (coverDocketForm.getFillInPaper().equalsIgnoreCase("P") &&
				coverDocketForm.getExampaper().getMarkReadingCode().equalsIgnoreCase("P")){
			coverDocketForm.setAttendanceRegister(true);
		}
				
		List instructions = coverDocketForm.getExampaper().getAdditionalInstructions();
		List enteredInstructions = new ArrayList();
		
		for (int i = 0; i<instructions.size(); i++) {
			enteredInstructions.add(coverDocketForm.getExampaper().getAdditionalInstructions().get(i));
		}
		request.setAttribute("enteredInstructions", enteredInstructions);
				
		return "step3";	
	}
	
	public List initializeMaterials(ActionForm form){
		
		ExamPaperCoverDocketForm coverDocketForm = (ExamPaperCoverDocketForm) form;
		coverDocketForm.setIndexNrSelectedMaterial(null);
		
		List listMaterials = new ArrayList();
		SpecialMaterialRecord specialmaterial = new SpecialMaterialRecord();
		specialmaterial.setCode("MB");
		specialmaterial.setDescription("Mark reading sheet (A4 sheet)");
		specialmaterial.setAverage("1.0");
		listMaterials.add(0,specialmaterial);
		specialmaterial = new SpecialMaterialRecord();
		specialmaterial.setCode("GP");
		specialmaterial.setDescription("Graph paper (A4 sheet)");
		specialmaterial.setAverage("");
		listMaterials.add(1,specialmaterial);
		specialmaterial = new SpecialMaterialRecord();
		specialmaterial.setCode("MP");
		specialmaterial.setDescription("Music manuscript paper");
		specialmaterial.setAverage("");
		listMaterials.add(2,specialmaterial);
		specialmaterial = new SpecialMaterialRecord();
		specialmaterial.setCode("LA");
		specialmaterial.setDescription("Loose answer sheet(s)");
		specialmaterial.setAverage("");
		listMaterials.add(3,specialmaterial);
		specialmaterial = new SpecialMaterialRecord();
		specialmaterial.setCode("TB");
		specialmaterial.setDescription("Table(s)");
		specialmaterial.setAverage("");
		listMaterials.add(4,specialmaterial);
		specialmaterial = new SpecialMaterialRecord();
		specialmaterial.setCode("FS");
		specialmaterial.setDescription("Formulae sheet(s)");
		specialmaterial.setAverage("");
		listMaterials.add(5,specialmaterial);
		specialmaterial = new SpecialMaterialRecord();
		specialmaterial.setCode("MA");
		specialmaterial.setDescription("Map(s)");
		specialmaterial.setAverage("");
		listMaterials.add(6,specialmaterial);
		specialmaterial = new SpecialMaterialRecord();
		specialmaterial.setCode("A3");
		specialmaterial.setDescription("A3 sheet(s)");
		specialmaterial.setAverage("");
		listMaterials.add(7,specialmaterial);
		specialmaterial = new SpecialMaterialRecord();
		specialmaterial.setCode("RS");
		specialmaterial.setDescription("Rough work sheet(s)");
		specialmaterial.setAverage("");
		listMaterials.add(8,specialmaterial);
		
		return listMaterials;
	}
	
	public List initializeBooks(ActionForm form){
		
		ExamPaperCoverDocketForm coverDocketForm = (ExamPaperCoverDocketForm) form;
		coverDocketForm.setIndexNrSelectedBook(null);
		List listBooks = new ArrayList();
		SpecialMaterialRecord answerbook = new SpecialMaterialRecord();
		answerbook.setCode("GB");
		answerbook.setDescription("Green (Ordinary)");
		answerbook.setAverage("");
		listBooks.add(0,answerbook);
		answerbook = new SpecialMaterialRecord();
		answerbook.setCode("BB");
		answerbook.setDescription("Blue (Accounting)");
		answerbook.setAverage("");
		listBooks.add(1,answerbook);
		//answerbook = new SpecialMaterialRecord();
		//answerbook.setCode("PB");
		//answerbook.setDescription("Purple:RPL (Challange Exam)");
		//answerbook.setAverage("");
		//listBooks.add(2,answerbook);
		//answerbook = new SpecialMaterialRecord();
		//answerbook.setCode("LB");
		//answerbook.setDescription("Light blue (Test books)");
		//answerbook.setAverage("");
		//listBooks.add(3,answerbook);
		
		return listBooks;
	
	}
	
	public boolean testInteger(String stringValue) {
		try
		{
			Integer i = Integer.parseInt(stringValue);
			return true;
		}	
		catch(NumberFormatException e)
		{}
		return false;
	}
	
	public boolean testAverageValue(String stringValue) {
		try
		{
			float i = Float.parseFloat(stringValue);
			String tempString1 ="";
			String tempString2 ="";
			
			NumberFormat f = NumberFormat.getInstance();
						
			if (f instanceof DecimalFormat){
				((DecimalFormat) f).applyPattern("00.00");
				f.setMaximumFractionDigits(2);
				f.setMaximumIntegerDigits(2);	
				
				tempString1 = f.format(i);
				
				((DecimalFormat) f).applyPattern("000.000");
				f.setMaximumFractionDigits(3);
				f.setMaximumIntegerDigits(3);				
				tempString2 = f.format(i);
				
				if (Float.parseFloat(tempString1)== Float.parseFloat(tempString2)){
					return true;
				}else {
					return false;
				}
					
			}
			return true;
		}	
		catch(NumberFormatException e)
		{}
		return false;
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
	}
	
