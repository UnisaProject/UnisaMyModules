package za.ac.unisa.lms.tools.exampaperonline.actions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.LookupDispatchAction;

import za.ac.unisa.lms.dao.Gencod;
import za.ac.unisa.lms.dao.Xamprd;
import za.ac.unisa.lms.tools.exampaperonline.dao.ExamPaperOnlineDao;
import za.ac.unisa.lms.tools.exampaperonline.forms.Document;
import za.ac.unisa.lms.tools.exampaperonline.forms.ExamPaperOnlineForm;
import za.ac.unisa.lms.tools.exampaperonline.forms.ExamPaperOnlineLog;
import za.ac.unisa.lms.tools.exampaperonline.forms.ExamPaperOnlineStatus;
import za.ac.unisa.lms.tools.exampaperonline.forms.Paper;
import za.ac.unisa.lms.tools.exampaperonline.forms.Rule;

public class ListDocumentAction extends LookupDispatchAction{
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
		map.put("show", "show");
		map.put("open", "open");
		map.put("printInstr", "printInstr");
		map.put("displayList", "displayList");
		map.put("button.continue","nextStep");
		map.put("button.cancel","cancel");
		map.put("button.back", "prevStep");
		map.put("button.open", "openDocument");
		map.put("button.first", "displayList");
		map.put("button.next", "displayList");
		map.put("button.previous", "displayList");
		map.put("button.display", "displayList");
		map.put("button.clear", "clear");
		return map;
	}
	
	public ActionForward initial(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
			ActionMessages messages = new ActionMessages();
			ExamPaperOnlineForm examPaperOnlineForm = (ExamPaperOnlineForm) form;
			
			//get a list of available documents on your memory stick
			ExamPaperOnlineDao dao = new ExamPaperOnlineDao();
			
			List availableDocList = new ArrayList();
			
			if (examPaperOnlineForm.getCurrentPage().equalsIgnoreCase("input")){
				if (examPaperOnlineForm.getExamYear()!=null && !examPaperOnlineForm.getExamYear().equalsIgnoreCase("")){
					examPaperOnlineForm.setListCriteriaExamYear(examPaperOnlineForm.getExamYear());
					examPaperOnlineForm.setListCriteriaExamPeriod(examPaperOnlineForm.getExamPeriod());
				}
				if (examPaperOnlineForm.getStudyUnit()!=null && !examPaperOnlineForm.getStudyUnit().equalsIgnoreCase("")){
					examPaperOnlineForm.setListCriteriaStudyUnit(examPaperOnlineForm.getStudyUnit());
				}
				if (examPaperOnlineForm.getPaperContent()!=null && !examPaperOnlineForm.getPaperContent().equalsIgnoreCase("")){
					examPaperOnlineForm.setListCriteriaPaperContent(examPaperOnlineForm.getPaperContent());
				}
			}	
			
			if (examPaperOnlineForm.getListCriteriaStudyUnit()!=null && !examPaperOnlineForm.getListCriteriaStudyUnit().equalsIgnoreCase("")){
				examPaperOnlineForm.setListCriteriaStudyUnit(examPaperOnlineForm.getListCriteriaStudyUnit().toUpperCase().trim());
			}
			examPaperOnlineForm.setFirstTimestamp("");
			examPaperOnlineForm.setLastTimestamp("");
			examPaperOnlineForm.setPageDownFlag("N");
			examPaperOnlineForm.setPageUpFlag("N");
			examPaperOnlineForm.setRowsInList(25);
			examPaperOnlineForm.setDisplayListAction("D");
			examPaperOnlineForm.setListCriteriaFromRole("ALL");
			if (examPaperOnlineForm.getListCriteriaExamYear()==null || examPaperOnlineForm.getListCriteriaExamYear().equalsIgnoreCase("")){
				availableDocList = dao.getActionedListPgUpPgDnForUser(examPaperOnlineForm.getListUserId(), examPaperOnlineForm.getDisplayListAction(), examPaperOnlineForm.getRowsInList(), null, null, examPaperOnlineForm.getListCriteriaStudyUnit(), examPaperOnlineForm.getListCriteriaPaperContent(), "RETRIEVE", null, "AT", examPaperOnlineForm.getListCriteriaFromRole());
			}else{
				availableDocList = dao.getActionedListPgUpPgDnForUser(examPaperOnlineForm.getListUserId(), examPaperOnlineForm.getDisplayListAction(), examPaperOnlineForm.getRowsInList(), Short.parseShort(examPaperOnlineForm.getListCriteriaExamYear()), Short.parseShort(examPaperOnlineForm.getListCriteriaExamPeriod()), examPaperOnlineForm.getListCriteriaStudyUnit(), examPaperOnlineForm.getListCriteriaPaperContent(), "RETRIEVE", null, "AT",examPaperOnlineForm.getListCriteriaFromRole());
			}
			
			if (availableDocList.size()>examPaperOnlineForm.getRowsInList()){
				examPaperOnlineForm.setPageDownFlag("Y");				
				availableDocList.remove(availableDocList.size()-1);
				examPaperOnlineForm.setFirstTimestamp(((ExamPaperOnlineLog) availableDocList.get(0)).getUpdatedOn());
				examPaperOnlineForm.setLastTimestamp(((ExamPaperOnlineLog) availableDocList.get(availableDocList.size()-1)).getUpdatedOn());
			}	
			
			//get the last SEND log entry for this paper - to get from user response & date send
			for (int i=0; i < availableDocList.size(); i ++){
				ExamPaperOnlineLog log = new ExamPaperOnlineLog();
				ExamPaperOnlineLog lastSendLog = new ExamPaperOnlineLog();
				log = (ExamPaperOnlineLog)availableDocList.get(i);
				//Integer seqNr = 0;
				//seqNr = dao.getLatestLogSequenceNumber(log.getExamYear(), log.getExamPeriod(), log.getStudyUnit(), log.getPaperNo(), log.getDocType(), "SEND", log.getCurrentUser(),"FROM");
				Integer seqNr = dao.getLastLogSeqNrDocSendnotRetracted(log.getExamYear(), log.getExamPeriod(), log.getStudyUnit(), log.getPaperNo(), log.getDocType());
				if (seqNr !=0){
					lastSendLog = dao.getExamPaperOnlineLog(log.getExamYear(), log.getExamPeriod(), log.getStudyUnit(), log.getPaperNo(), log.getDocType(), seqNr);
					((ExamPaperOnlineLog)availableDocList.get(i)).setCurrentUser(lastSendLog.getCurrentUser());  //from user
					((ExamPaperOnlineLog)availableDocList.get(i)).setCurrentRole(lastSendLog.getCurrentRole());  //from role
					((ExamPaperOnlineLog)availableDocList.get(i)).setActionedUser(lastSendLog.getActionedUser());  
					((ExamPaperOnlineLog)availableDocList.get(i)).setActionedRole(lastSendLog.getActionedRole());  //my role
					((ExamPaperOnlineLog)availableDocList.get(i)).setApprovalStatus(lastSendLog.getApprovalStatus());	
					((ExamPaperOnlineLog)availableDocList.get(i)).setUpdatedOn(lastSendLog.getUpdatedOn());
					((ExamPaperOnlineLog)availableDocList.get(i)).setMessage(lastSendLog.getMessage());
					((ExamPaperOnlineLog)availableDocList.get(i)).setUpdatedOn(((ExamPaperOnlineLog)availableDocList.get(i)).getUpdatedOn().substring(0, 19));
				}else{
					((ExamPaperOnlineLog)availableDocList.get(i)).setCurrentUser("");
					((ExamPaperOnlineLog)availableDocList.get(i)).setCurrentRole("");
					((ExamPaperOnlineLog)availableDocList.get(i)).setActionedUser("");
					((ExamPaperOnlineLog)availableDocList.get(i)).setActionedRole("OWNER"); //my role
					((ExamPaperOnlineLog)availableDocList.get(i)).setApprovalStatus("");	
					((ExamPaperOnlineLog)availableDocList.get(i)).setUpdatedOn("");
					((ExamPaperOnlineLog)availableDocList.get(i)).setMessage("");
					((ExamPaperOnlineLog)availableDocList.get(i)).setUpdatedOn("");
				}
				
			}
			
			//set up from role list
			List fromRoleList = new ArrayList();		
			String fromRole = "ALL";
			fromRoleList.add(fromRole);
			boolean roleFound = false;			
			for (int i=0; i < availableDocList.size(); i++){
				roleFound=false;
				fromRole = ((ExamPaperOnlineLog)availableDocList.get(i)).getCurrentRole();
				for (int j=0; j < fromRoleList.size(); j++){
					if (fromRole.equalsIgnoreCase(fromRoleList.toString())){
						roleFound=true;
					}
				}
				if (!roleFound){
					fromRoleList.add(fromRole);
				}				
			}
			
			examPaperOnlineForm.setListFromRoles(fromRoleList);
			examPaperOnlineForm.setAvailableDocList(availableDocList);
			
			if (availableDocList.size()==0){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"There are no available examination papers/memorandums."));
				addErrors(request,messages);
//				return mapping.findForward("cancel");
			}
			
			examPaperOnlineForm.setToolAction("LISTDOCS");
			
//			//get tool action desc
//			for (int i=0; i < examPaperOnlineForm.getListToolActions().size();i++){
//				Gencod gencod = new Gencod();
//				gencod = (Gencod)examPaperOnlineForm.getListToolActions().get(i);
//				if (gencod.getCode().equalsIgnoreCase(examPaperOnlineForm.getToolAction())){
//					examPaperOnlineForm.setToolActionDesc(gencod.getEngDescription());
//					i=examPaperOnlineForm.getListToolActions().size();
//				}
//			}	
			
			return mapping.findForward("listDocsStep1");
		
	}	
	
	public ActionForward clear(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
			ActionMessages messages = new ActionMessages();    
			ExamPaperOnlineForm examPaperOnlineForm = (ExamPaperOnlineForm) form;
			
			examPaperOnlineForm.setListCriteriaExamYear("");
			examPaperOnlineForm.setListCriteriaExamPeriod("");
			examPaperOnlineForm.setListCriteriaStudyUnit("");
			examPaperOnlineForm.setListCriteriaPaperNo("");
			examPaperOnlineForm.setListCriteriaPaperNo("");
			
			return mapping.findForward("listDocsStep1");
	}
	
	public ActionForward displayList(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		ActionMessages messages = new ActionMessages();
		ExamPaperOnlineForm examPaperOnlineForm = (ExamPaperOnlineForm) form;
		
		if (examPaperOnlineForm.getListCriteriaStudyUnit()!=null && !examPaperOnlineForm.getListCriteriaStudyUnit().equalsIgnoreCase("")){
			examPaperOnlineForm.setListCriteriaStudyUnit(examPaperOnlineForm.getListCriteriaStudyUnit().toUpperCase().trim());
		}
		ExamPaperOnlineDao dao = new ExamPaperOnlineDao();
		List availableDocList = new ArrayList();		
		examPaperOnlineForm.setPageDownFlag("N");
		examPaperOnlineForm.setPageUpFlag("N");
		String searchFromTimestamp="";
		if (examPaperOnlineForm.getDisplayListAction().equalsIgnoreCase("PD")){
			searchFromTimestamp = examPaperOnlineForm.getLastTimestamp();
		}
		if (examPaperOnlineForm.getDisplayListAction().equalsIgnoreCase("PU")){
			searchFromTimestamp = examPaperOnlineForm.getFirstTimestamp();
		}		
		if (examPaperOnlineForm.getListCriteriaExamYear()==null || examPaperOnlineForm.getListCriteriaExamYear().equalsIgnoreCase("")){
			availableDocList = dao.getActionedListPgUpPgDnForUser(examPaperOnlineForm.getListUserId(), examPaperOnlineForm.getDisplayListAction(), examPaperOnlineForm.getRowsInList(), null, null, examPaperOnlineForm.getListCriteriaStudyUnit(), examPaperOnlineForm.getListCriteriaPaperContent(), "RETRIEVE", searchFromTimestamp, "AT",examPaperOnlineForm.getListCriteriaFromRole());
		}else{
			availableDocList = dao.getActionedListPgUpPgDnForUser(examPaperOnlineForm.getListUserId(), examPaperOnlineForm.getDisplayListAction(), examPaperOnlineForm.getRowsInList(), Short.parseShort(examPaperOnlineForm.getListCriteriaExamYear()), Short.parseShort(examPaperOnlineForm.getListCriteriaExamPeriod()), examPaperOnlineForm.getListCriteriaStudyUnit(), examPaperOnlineForm.getListCriteriaPaperContent(), "RETRIEVE", searchFromTimestamp, "AT",examPaperOnlineForm.getListCriteriaFromRole());
		}	
		
		if (availableDocList.size()>0){
			if (availableDocList.size()>examPaperOnlineForm.getRowsInList()){
				if (examPaperOnlineForm.getDisplayListAction().equalsIgnoreCase("D")){
					examPaperOnlineForm.setPageDownFlag("Y");
					examPaperOnlineForm.setPageUpFlag("N");
				}
				if (examPaperOnlineForm.getDisplayListAction().equalsIgnoreCase("PD")){
					examPaperOnlineForm.setPageDownFlag("Y");
					examPaperOnlineForm.setPageUpFlag("Y");
				}
				if (examPaperOnlineForm.getDisplayListAction().equalsIgnoreCase("PU")){
					examPaperOnlineForm.setPageUpFlag("Y");
					examPaperOnlineForm.setPageDownFlag("Y");
				}							
				availableDocList.remove(availableDocList.size()-1);			
			} else {
				if (examPaperOnlineForm.getDisplayListAction().equalsIgnoreCase("D")){
					examPaperOnlineForm.setPageDownFlag("N");
					examPaperOnlineForm.setPageUpFlag("N");
				}
				if (examPaperOnlineForm.getDisplayListAction().equalsIgnoreCase("PD")){
					examPaperOnlineForm.setPageDownFlag("N");
					examPaperOnlineForm.setPageUpFlag("Y");
				}
				if (examPaperOnlineForm.getDisplayListAction().equalsIgnoreCase("PU")){
					examPaperOnlineForm.setPageUpFlag("N");
					examPaperOnlineForm.setPageDownFlag("Y");
				}					
			}		
			//if action page up(PU) re-order list
			if (examPaperOnlineForm.getDisplayListAction().equalsIgnoreCase("PU")){
				List tempList = new ArrayList();
				tempList = availableDocList;
				availableDocList = new ArrayList();
				ListIterator i = tempList.listIterator();
				while(i.hasNext()){
					i.next();
				}
				while(i.hasPrevious()){
					availableDocList.add(i.previous());
				}
			}
			examPaperOnlineForm.setFirstTimestamp(((ExamPaperOnlineLog) availableDocList.get(0)).getUpdatedOn());
			examPaperOnlineForm.setLastTimestamp(((ExamPaperOnlineLog) availableDocList.get(availableDocList.size()-1)).getUpdatedOn());
		}		
		
		//get the last SEND log entry for this paper - to get from user response & date send
		for (int i=0; i < availableDocList.size(); i ++){
			ExamPaperOnlineLog log = new ExamPaperOnlineLog();
			ExamPaperOnlineLog lastSendLog = new ExamPaperOnlineLog();
			log = (ExamPaperOnlineLog)availableDocList.get(i);
			Integer seqNr = dao.getLastLogSeqNrDocSendnotRetracted(log.getExamYear(), log.getExamPeriod(), log.getStudyUnit(), log.getPaperNo(), log.getDocType());
			if (seqNr !=0){
				lastSendLog = dao.getExamPaperOnlineLog(log.getExamYear(), log.getExamPeriod(), log.getStudyUnit(), log.getPaperNo(), log.getDocType(), seqNr);
				((ExamPaperOnlineLog)availableDocList.get(i)).setCurrentUser(lastSendLog.getCurrentUser());
				((ExamPaperOnlineLog)availableDocList.get(i)).setCurrentRole(lastSendLog.getCurrentRole());
				((ExamPaperOnlineLog)availableDocList.get(i)).setActionedUser(lastSendLog.getActionedUser());  //debug
				((ExamPaperOnlineLog)availableDocList.get(i)).setActionedRole(lastSendLog.getActionedRole());  //my role
				((ExamPaperOnlineLog)availableDocList.get(i)).setApprovalStatus(lastSendLog.getApprovalStatus());	
				((ExamPaperOnlineLog)availableDocList.get(i)).setUpdatedOn(lastSendLog.getUpdatedOn());
				((ExamPaperOnlineLog)availableDocList.get(i)).setMessage(lastSendLog.getMessage());
				((ExamPaperOnlineLog)availableDocList.get(i)).setUpdatedOn(((ExamPaperOnlineLog)availableDocList.get(i)).getUpdatedOn().substring(0, 19));
			}else{
				((ExamPaperOnlineLog)availableDocList.get(i)).setCurrentUser("");
				((ExamPaperOnlineLog)availableDocList.get(i)).setCurrentRole("");
				((ExamPaperOnlineLog)availableDocList.get(i)).setActionedUser("");
				((ExamPaperOnlineLog)availableDocList.get(i)).setActionedRole("OWNER"); //my role
				((ExamPaperOnlineLog)availableDocList.get(i)).setApprovalStatus("");	
				((ExamPaperOnlineLog)availableDocList.get(i)).setUpdatedOn("");
				((ExamPaperOnlineLog)availableDocList.get(i)).setMessage("");
				((ExamPaperOnlineLog)availableDocList.get(i)).setUpdatedOn("");
			}
		}
		
		examPaperOnlineForm.setAvailableDocList(availableDocList);
		
		if (availableDocList.size()==0){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
							"There are no available examination papers/memorandums."));
			addErrors(request,messages);
		}
		
		return mapping.findForward("listDocsStep1");	
	}	
	
	public ActionForward show(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
			ActionMessages messages = new ActionMessages();
			ExamPaperOnlineForm examPaperOnlineForm = (ExamPaperOnlineForm) form;
			
			ExamPaperOnlineLog selectedPaper = new ExamPaperOnlineLog();
			selectedPaper.setExamYear(Short.parseShort(request.getParameter("xpoExamYear")));
			selectedPaper.setExamPeriod(Short.parseShort(request.getParameter("xpoExamPeriod")));
			selectedPaper.setStudyUnit(request.getParameter("xpoStudyUnit"));
			selectedPaper.setPaperNo(Short.parseShort(request.getParameter("xpoPaperNo")));
			selectedPaper.setDocType(request.getParameter("xpoDocType"));		
			
			for (int i = 0;i < examPaperOnlineForm.getAvailableDocList().size(); i++){
				ExamPaperOnlineLog log = new ExamPaperOnlineLog();
				log = (ExamPaperOnlineLog)examPaperOnlineForm.getAvailableDocList().get(i);
				if (selectedPaper.getExamYear().compareTo(log.getExamYear())==0 && 
						selectedPaper.getExamPeriod().compareTo(log.getExamPeriod())==0 &&
						selectedPaper.getStudyUnit().equalsIgnoreCase(log.getStudyUnit()) &&
						selectedPaper.getPaperNo().compareTo(log.getPaperNo())==0 &&
						selectedPaper.getDocType().equalsIgnoreCase(log.getDocType())){
					selectedPaper = log;
					i = examPaperOnlineForm.getAvailableDocList().size();
				}
			}
			
			examPaperOnlineForm.setEquivalentStr("");
			examPaperOnlineForm.setEquivalentList(selectedPaper.getLinkedPaper().getLinkedPapers());
			if (selectedPaper.getLinkedPaper().getLinkedPapers().size()>0){
				if (selectedPaper.getLinkedPaper().getLinkedPapers().size()==1){
					examPaperOnlineForm.setEquivalentStr("(Linked paper: " + selectedPaper.getLinkedPaper().getLinkedDesc() + ")");
				}else{
					examPaperOnlineForm.setEquivalentStr("(Linked papers: " + selectedPaper.getLinkedPaper().getLinkedDesc() + ")");
				}
			}
			
			//get files from passed through from Harry
//			String retrievedFiles = "J:\\COS111U201006_1Q\\1.pdf;J:\\COS111U201006_1Q\\2.pdf";
			String[] temp;
			String retrievedFiles;
			
			if (request.getParameter("fn")==null){
				retrievedFiles = "";
			}else{
				retrievedFiles = request.getParameter("fn");
			}			
			
			List docList = new ArrayList();
			Paper paper = new Paper();
			paper.setExamYear(selectedPaper.getExamYear());
			paper.setExamPeriod(selectedPaper.getExamPeriod());
			paper.setStudyUnit(selectedPaper.getStudyUnit());
			paper.setPaperNo(selectedPaper.getPaperNo());
			paper.setDocType(selectedPaper.getDocType());
			
			ExamPaperOnlineDao dao = new ExamPaperOnlineDao();
			ExamPaperOnlineStatus currentStatus = new ExamPaperOnlineStatus();
			currentStatus = dao.getExamPaperOnlineStatus(paper.getExamYear(), paper.getExamPeriod(), paper.getStudyUnit(), paper.getPaperNo(), paper.getDocType());
			
			//read xporul to determine which type of documents must be listed on retrieve "edit" or "print"			
			Rule rule = new Rule();
			rule = dao.getRule("RETRIEVE", currentStatus.getDocType(), currentStatus.getAtRole(), null);
			examPaperOnlineForm.setDocumentsToUploadShown(rule.getDocsShown());
			
			String delimited = ";";
			if (!retrievedFiles.equalsIgnoreCase("")){
				temp = retrievedFiles.split(delimited);
				for (int i=0; i<temp.length ; i++){
					 String delimited2 = "\\\\";
					 String[] temp2 = temp[i].toString().split(delimited2);
					 Document document = new Document();
					 String documentStr = temp2[1];	

					 //add the document number and extention in the action field
					 document.setFileName(temp2[2].toString());
					 document.setPathAndFileName(temp[i].toString());
					 
					 //extract documents to be shown to the user to open
					 if (document.getFileName().substring(document.getFileName().lastIndexOf(".")+1, document.getFileName().length()).equalsIgnoreCase("tif") && examPaperOnlineForm.getDocumentsToUploadShown().equalsIgnoreCase("PRINT")){
						 docList.add(document);
					 }
					 if (!document.getFileName().substring(document.getFileName().lastIndexOf(".")+1, document.getFileName().length()).equalsIgnoreCase("tif") && examPaperOnlineForm.getDocumentsToUploadShown().equalsIgnoreCase("EDIT")){
						 docList.add(document);
					 }		
				}		
			}
			
			for (int i = 0;i < examPaperOnlineForm.getAvailableDocList().size(); i++){
				ExamPaperOnlineLog log = new ExamPaperOnlineLog();
				log = (ExamPaperOnlineLog)examPaperOnlineForm.getAvailableDocList().get(i);
				if (paper.getExamYear().compareTo(log.getExamYear())==0 && 
					paper.getExamPeriod().compareTo(log.getExamPeriod())==0 &&
					paper.getStudyUnit().equalsIgnoreCase(log.getStudyUnit()) &&
					paper.getPaperNo().compareTo(log.getPaperNo())==0 &&
					paper.getDocType().equalsIgnoreCase(log.getDocType())){
					paper.setFromUser(log.getCurrentUser());
					paper.setFromRole(log.getCurrentRole());
					paper.setDateSent(log.getUpdatedOn());
					paper.setSenderResponse(log.getApprovalStatus());
					paper.setSenderResponseText(log.getMessage());
					i = examPaperOnlineForm.getAvailableDocList().size();
				}
			}
			paper.setDocumentList(docList);
			
			//get exam period desc
			String examPeriodDesc="";
			for (int i=0; i < examPaperOnlineForm.getListExamPeriods().size();i++){
				Xamprd examperiod = new Xamprd();
				examperiod = (Xamprd)examPaperOnlineForm.getListExamPeriods().get(i);
				if (examperiod.getCode().compareTo(paper.getExamPeriod())==0){
					examPeriodDesc = examperiod.getEngDescription();
					i=examPaperOnlineForm.getListExamPeriods().size();
				}
			}
			String contentTypeDesc="";
			
			if (paper.getDocType().equalsIgnoreCase("QUESTION")){
				contentTypeDesc="Question paper";
			}
			if (paper.getDocType().equalsIgnoreCase("Memo")){
				contentTypeDesc="Memorandum paper";
			}
			
			examPaperOnlineForm.setSelectedPaper(paper);
			request.setAttribute("examPeriodDesc", examPeriodDesc);
			request.setAttribute("contentTypeDesc", contentTypeDesc);
			
			return mapping.findForward("listDocsStep2");	
		
	}
	
	public ActionForward open(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		return mapping.findForward("listDocsStep2");	
	}
	
	public ActionForward printInstr(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
			ExamPaperOnlineForm examPaperOnlineForm = (ExamPaperOnlineForm) form;
			
			examPaperOnlineForm.setExamYear(request.getParameter("examYear"));
			examPaperOnlineForm.setExamPeriod(request.getParameter("examPeriod"));
			examPaperOnlineForm.setStudyUnit(request.getParameter("studyUnit"));
			examPaperOnlineForm.setPaperNo(request.getParameter("paperNo"));
						
			return mapping.findForward("printinstr");	
		
	}
	
	public ActionForward prevStep(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String backward="";
		ExamPaperOnlineForm examPaperOnlineForm = (ExamPaperOnlineForm) form;
		
		if (examPaperOnlineForm.getCurrentPage().equalsIgnoreCase("listDocsStep1")) {
			backward="cancel";
		}
		if (examPaperOnlineForm.getCurrentPage().equalsIgnoreCase("listDocsStep2")) {
			examPaperOnlineForm.setDisplayListAction("D");
			backward="displayList";
		}		
		
		return mapping.findForward(backward);	
	}
}
