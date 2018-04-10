package za.ac.unisa.lms.tools.exampaperonline.actions;

import java.sql.Date;
import java.text.SimpleDateFormat;
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
import za.ac.unisa.lms.tools.exampaperonline.dao.XtlogDao;
import za.ac.unisa.lms.tools.exampaperonline.forms.Document;
import za.ac.unisa.lms.tools.exampaperonline.forms.ExamPaperOnlineForm;
import za.ac.unisa.lms.tools.exampaperonline.forms.ExamPaperOnlineLog;
import za.ac.unisa.lms.tools.exampaperonline.forms.ExamPaperOnlineStatus;
import za.ac.unisa.lms.tools.exampaperonline.forms.Paper;
import za.ac.unisa.lms.tools.exampaperonline.forms.Rule;
import za.ac.unisa.lms.tools.exampaperonline.forms.UpdateModuleLog;
import za.ac.unisa.lms.tools.exampaperonline.forms.Xtloge;

public class RetractDocumentAction extends LookupDispatchAction{
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
		map.put("retract", "retract");
		map.put("open", "open");
		map.put("displayList", "displayList");
		map.put("button.continue","nextStep");
		map.put("button.cancel","cancel");
		map.put("button.back", "prevStep");
		map.put("button.upload", "uploadDocument");
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
			
			//get a list of available documents to retrieve for this user
			ExamPaperOnlineDao dao = new ExamPaperOnlineDao();
			
			List availableDocList = new ArrayList();

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
			examPaperOnlineForm.setFirstTimestamp("");
			examPaperOnlineForm.setLastTimestamp("");
			examPaperOnlineForm.setPageDownFlag("N");
			examPaperOnlineForm.setPageUpFlag("N");
			examPaperOnlineForm.setRowsInList(25);
			examPaperOnlineForm.setDisplayListAction("D");
			if (examPaperOnlineForm.getListCriteriaExamYear()==null || examPaperOnlineForm.getListCriteriaExamYear().equalsIgnoreCase("")){
				availableDocList = dao.getActionedListPgUpPgDnForUser(examPaperOnlineForm.getListUserId(), examPaperOnlineForm.getDisplayListAction(), examPaperOnlineForm.getRowsInList(), null, null, examPaperOnlineForm.getListCriteriaStudyUnit(), examPaperOnlineForm.getListCriteriaPaperContent(), "SEND", null, "FROM",null);
			}else{
				availableDocList = dao.getActionedListPgUpPgDnForUser(examPaperOnlineForm.getListUserId(), examPaperOnlineForm.getDisplayListAction(), examPaperOnlineForm.getRowsInList(), Short.parseShort(examPaperOnlineForm.getListCriteriaExamYear()), Short.parseShort(examPaperOnlineForm.getListCriteriaExamPeriod()), examPaperOnlineForm.getListCriteriaStudyUnit(), examPaperOnlineForm.getListCriteriaPaperContent(), "SEND", null, "FROM",null);
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
				Integer seqNr = dao.getLastLogSeqNrDocSendnotRetracted(log.getExamYear(), log.getExamPeriod(), log.getStudyUnit(), log.getPaperNo(), log.getDocType());
				if (seqNr!=0){
					lastSendLog = dao.getExamPaperOnlineLog(log.getExamYear(), log.getExamPeriod(), log.getStudyUnit(), log.getPaperNo(), log.getDocType(), seqNr);
					((ExamPaperOnlineLog)availableDocList.get(i)).setApprovalStatus(lastSendLog.getApprovalStatus());	
					((ExamPaperOnlineLog)availableDocList.get(i)).setUpdatedOn(lastSendLog.getUpdatedOn().substring(0,19));
					((ExamPaperOnlineLog)availableDocList.get(i)).setMessage(lastSendLog.getMessage());						
				}else{
					lastSendLog = dao.getExamPaperOnlineLog(log.getExamYear(), log.getExamPeriod(), log.getStudyUnit(), log.getPaperNo(), log.getDocType(), seqNr);
					((ExamPaperOnlineLog)availableDocList.get(i)).setApprovalStatus("");	
					((ExamPaperOnlineLog)availableDocList.get(i)).setUpdatedOn("");
					((ExamPaperOnlineLog)availableDocList.get(i)).setMessage("");	
				}
			}
			
			examPaperOnlineForm.setAvailableDocList(availableDocList);
			
			if (availableDocList.size()==0){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"There are no available examination papers/memorandums to retract."));
				addErrors(request,messages);
//				return mapping.findForward("cancel");
			}
			
			examPaperOnlineForm.setToolAction("RETRACT");
			
			//get tool action desc
			for (int i=0; i < examPaperOnlineForm.getListToolActions().size();i++){
				Gencod gencod = new Gencod();
				gencod = (Gencod)examPaperOnlineForm.getListToolActions().get(i);
				if (gencod.getCode().equalsIgnoreCase(examPaperOnlineForm.getToolAction())){
					examPaperOnlineForm.setToolActionDesc(gencod.getEngDescription());
					i=examPaperOnlineForm.getListToolActions().size();
				}
			}	
			
			return mapping.findForward("retractStep1");
		
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
			
			return mapping.findForward("retractStep1");
	}
	
	public ActionForward displayList(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		ActionMessages messages = new ActionMessages();
		ExamPaperOnlineForm examPaperOnlineForm = (ExamPaperOnlineForm) form;
		
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
			availableDocList = dao.getActionedListPgUpPgDnForUser(examPaperOnlineForm.getListUserId(), examPaperOnlineForm.getDisplayListAction(), examPaperOnlineForm.getRowsInList(), null, null, examPaperOnlineForm.getListCriteriaStudyUnit(), examPaperOnlineForm.getListCriteriaPaperContent(), "SEND", searchFromTimestamp, "FROM",null);
		}else{
			availableDocList = dao.getActionedListPgUpPgDnForUser(examPaperOnlineForm.getListUserId(), examPaperOnlineForm.getDisplayListAction(), examPaperOnlineForm.getRowsInList(), Short.parseShort(examPaperOnlineForm.getListCriteriaExamYear()), Short.parseShort(examPaperOnlineForm.getListCriteriaExamPeriod()), examPaperOnlineForm.getListCriteriaStudyUnit(),examPaperOnlineForm.getListCriteriaPaperContent(), "SEND", searchFromTimestamp, "FROM",null);
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
			//Integer seqNr = 0;
			//seqNr = dao.getLatestLogSequenceNumber(log.getExamYear(), log.getExamPeriod(), log.getStudyUnit(), log.getPaperNo(), log.getDocType(), "SEND", log.getCurrentUser(),"FROM");		
			Integer seqNr = dao.getLastLogSeqNrDocSendnotRetracted(log.getExamYear(), log.getExamPeriod(), log.getStudyUnit(), log.getPaperNo(), log.getDocType());
			if (seqNr!=0){
				lastSendLog = dao.getExamPaperOnlineLog(log.getExamYear(), log.getExamPeriod(), log.getStudyUnit(), log.getPaperNo(), log.getDocType(), seqNr);
				((ExamPaperOnlineLog)availableDocList.get(i)).setApprovalStatus(lastSendLog.getApprovalStatus());	
				((ExamPaperOnlineLog)availableDocList.get(i)).setUpdatedOn(lastSendLog.getUpdatedOn().substring(0,19));
				((ExamPaperOnlineLog)availableDocList.get(i)).setMessage(lastSendLog.getMessage());	
			}else{
				lastSendLog = dao.getExamPaperOnlineLog(log.getExamYear(), log.getExamPeriod(), log.getStudyUnit(), log.getPaperNo(), log.getDocType(), seqNr);
				((ExamPaperOnlineLog)availableDocList.get(i)).setApprovalStatus("");	
				((ExamPaperOnlineLog)availableDocList.get(i)).setUpdatedOn("");
				((ExamPaperOnlineLog)availableDocList.get(i)).setMessage("");	
			}
						
		}
		
		examPaperOnlineForm.setAvailableDocList(availableDocList);
		
		if (availableDocList.size()==0){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"There are no available examination papers/memorandums to retract."));
			addErrors(request,messages);
		}
		
		return mapping.findForward("retractStep1");	
	}	
		
	public ActionForward retract(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
			ActionMessages messages = new ActionMessages();
			ExamPaperOnlineForm examPaperOnlineForm = (ExamPaperOnlineForm) form;
			
			ExamPaperOnlineDao dao = new ExamPaperOnlineDao();
			ExamPaperOnlineLog selectedPaper = new ExamPaperOnlineLog();
			
			//get selected record
			selectedPaper.setExamYear(Short.parseShort(request.getParameter("xpoExamYear")));
			selectedPaper.setExamPeriod(Short.parseShort(request.getParameter("xpoExamPeriod")));
			selectedPaper.setStudyUnit(request.getParameter("xpoStudyUnit"));
			selectedPaper.setPaperNo(Short.parseShort(request.getParameter("xpoPaperNo")));
			selectedPaper.setDocType(request.getParameter("xpoDocType"));
			
			//get the current status of the mother record
			ExamPaperOnlineStatus currentStatus = new ExamPaperOnlineStatus();
			String owner = examPaperOnlineForm.getUserId();
			currentStatus = dao.getExamPaperOnlineStatus(selectedPaper.getExamYear(), 
					selectedPaper.getExamPeriod(), 
					selectedPaper.getStudyUnit(), 
					selectedPaper.getPaperNo(), 
					selectedPaper.getDocType());
			
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
				
			List updateList= new ArrayList();
			//Test if equivalents are in the same status
			//Get the next sequence numbers for the equivalents
			for (int i=0; i < selectedPaper.getLinkedPaper().getLinkedPapers().size(); i++){
				String eqv = selectedPaper.getLinkedPaper().getLinkedPapers().get(i).toString().toUpperCase().trim();
				ExamPaperOnlineStatus eqvStatus = new ExamPaperOnlineStatus();			
				eqvStatus = dao.getExamPaperOnlineStatus(selectedPaper.getExamYear(), 
						selectedPaper.getExamPeriod(), 
						eqv, 
						selectedPaper.getPaperNo(), 
						selectedPaper.getDocType());
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
											"Retrieve failed, mother code and linked papers is not in the same state."));
					}
				}else{
					if ((eqvStatus.getLastAction().equalsIgnoreCase(currentStatus.getLastAction()) &&
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
											"Retrieve failed, mother code and linked papers is not in the same state."));
					}
				}
				updateList.add(rec);
			}	
			
			if (!messages.isEmpty()){
					addErrors(request,messages);
					return mapping.findForward("retrieveStep1");	
			}			
			
			UpdateModuleLog rec = new UpdateModuleLog();
			rec.setStudyUnit(selectedPaper.getStudyUnit());
			rec.setType("MOTHER");
			String currentRole="";
			
			if (currentStatus.getExamYear()==null) {
				currentRole ="OWNER";
				rec.setSeqNr(1);
				rec.setStatusAction("INSERT");
			} else {
				rec.setSeqNr(currentStatus.getLastSeqNr()+1);
				rec.setStatusAction("UPDATE");	
				owner=currentStatus.getOwner();
				currentRole=currentStatus.getFromRole();
			}
			updateList.add(rec);
			
			//get current date
			Calendar calendar = Calendar.getInstance();	
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date(calendar.getTime().getTime());
			String stringDate=formatter.format(date);			
			java.sql.Timestamp timestamp = new java.sql.Timestamp(calendar.getTime().getTime());
			String tempTimestamp = timestamp.toString();			
			
			//set up new log record to be written to mother and equivalent codes
			ExamPaperOnlineLog newLog = new ExamPaperOnlineLog();
			newLog.setExamYear(selectedPaper.getExamYear());
			newLog.setExamPeriod(selectedPaper.getExamPeriod());
			newLog.setStudyUnit(selectedPaper.getStudyUnit());
			newLog.setPaperNo(selectedPaper.getPaperNo());
			newLog.setDocType(selectedPaper.getDocType());
			newLog.setAction("RETRACT");
			newLog.setCurrentUser(examPaperOnlineForm.getUser().getNovellUserId());
			newLog.setCurrentRole(currentRole);
			newLog.setActionedUser("");
			newLog.setActionedRole("");
			newLog.setApprovalStatus("");
			newLog.setMessage("");	
			newLog.setUpdatedOn(tempTimestamp);
			newLog.setResetFlag("N");
			
			//set up new status record to be written to mother and equivalent codes.
			//write status record
			ExamPaperOnlineStatus newStatus = new ExamPaperOnlineStatus();
			
			//Get from user - Get the last document send but not retracted - the currentUser on this log record is the from user
			//Integer lastSendSeqNr = dao.getLatestLogSequenceNumber(log.getExamYear(), log.getExamPeriod(), log.getStudyUnit(), log.getPaperNo(), log.getDocType(), "SEND", log.getCurrentUser(),"TO");
			Integer lastSendSeqNr = dao.getLastLogSeqNrDocSendnotRetracted(selectedPaper.getExamYear(), 
					selectedPaper.getExamPeriod(), 
					selectedPaper.getStudyUnit(), 
					selectedPaper.getPaperNo(), 
					selectedPaper.getDocType());
			ExamPaperOnlineLog lastSendLog = new ExamPaperOnlineLog();
			if (lastSendSeqNr != 0){			
				lastSendLog = dao.getExamPaperOnlineLog(selectedPaper.getExamYear(), 
						selectedPaper.getExamPeriod(), 
						selectedPaper.getStudyUnit(), 
						selectedPaper.getPaperNo(), 
						selectedPaper.getDocType(), 
						lastSendSeqNr);
				newStatus.setFromUser(lastSendLog.getCurrentUser());
				newStatus.setFromRole(lastSendLog.getCurrentRole());
			}else{
				//Retract on first send
				newStatus.setFromUser(" ");
				newStatus.setFromRole(" ");
			}			
			
			newStatus.setExamYear(selectedPaper.getExamYear());
			newStatus.setExamPeriod(selectedPaper.getExamPeriod());
			newStatus.setStudyUnit(selectedPaper.getStudyUnit());
			newStatus.setPaperNo(selectedPaper.getPaperNo());
			newStatus.setDocType(selectedPaper.getDocType());
			newStatus.setOwner(owner);
			newStatus.setUpdatedOn(tempTimestamp);
			newStatus.setAtUser(examPaperOnlineForm.getUserId());
			newStatus.setAtRole(currentRole);
			newStatus.setLastAction("RETRACT");			
			
			//Retract document send from EXAMS 
			//to Academic department - reset date_to_dept and date_from_dept
			XtlogDao xtlogDao = new XtlogDao();
			Xtloge newXtloge = new Xtloge();
			if ((currentStatus.getFromRole().equalsIgnoreCase("EXAMS_QA1")
					|| currentStatus.getFromRole().equalsIgnoreCase("EXAMS_QA2"))
					&& (currentStatus.getAtRole().equalsIgnoreCase("1ST_EXAM")
					|| currentStatus.getAtRole().equalsIgnoreCase("2ND_EXAM")
					|| currentStatus.getAtRole().equalsIgnoreCase("EXT_EXAM")
					|| currentStatus.getAtRole().equalsIgnoreCase("COD")
					|| currentStatus.getAtRole().equalsIgnoreCase("PRINT_PROD"))){
				Xtloge xtloge = new Xtloge();
				xtloge = xtlogDao.getXtloge(selectedPaper.getExamYear(), 
						selectedPaper.getExamPeriod(), 
						selectedPaper.getStudyUnit(), 
						selectedPaper.getPaperNo());
				if (xtloge.getExamYear()==null){
					// Error: xtloge does not exists 						
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
							"Xtloge record does not exists, contact support."));
					addErrors(request,messages);
					return mapping.findForward("retrieveStep1");	
				}
				//Retract document send from EXAMS to academic department
				if (currentStatus.getAtRole().equalsIgnoreCase("1ST_EXAM")
						|| currentStatus.getAtRole().equalsIgnoreCase("2ND_EXAM")
						|| currentStatus.getAtRole().equalsIgnoreCase("EXT_EXAM")
						|| currentStatus.getAtRole().equalsIgnoreCase("COD")){
						//Retract question paper sent to dept 
						//xtloge does exists - update xtloge set date_to_dept and date_from_dept	
						newXtloge.setExamYear(selectedPaper.getExamYear());
						newXtloge.setExamPeriod(selectedPaper.getExamPeriod());
						newXtloge.setStudyUnit(selectedPaper.getStudyUnit());
						newXtloge.setPaperNo(selectedPaper.getPaperNo());
						newXtloge.setDateReceived(xtloge.getDateReceived());
						newXtloge.setDateToDept("0001-01-01");
						newXtloge.setDateFromDept("0001-01-01");
						newXtloge.setDateToPrint(xtloge.getDateToPrint());
						newXtloge.setQuantToPrint(xtloge.getQuantToPrint());
						newXtloge.setQuantCalcOn(xtloge.getQuantCalcOn());
						newXtloge.setDate2ToPrint(xtloge.getDate2ToPrint());
						newXtloge.setQuant2ToPrint(xtloge.getQuant2ToPrint());
						newXtloge.setQuant2CalcOn(xtloge.getQuant2CalcOn());	
						newXtloge.setElectronicFlag(xtloge.getElectronicFlag());
				}
				//Retract document send from EXAMS to print production
				if (currentStatus.getAtRole().equalsIgnoreCase("PRINT_PROD")){
					if (xtloge.getDate2ToPrint().equalsIgnoreCase(null)  
							|| xtloge.getDate2ToPrint().equalsIgnoreCase("") 
							|| xtloge.getDate2ToPrint().equalsIgnoreCase("0001-01-01")){	
						//Retract question paper sent to print production - first send
						//xtloge does exists - update xtloge set date_to_print		
						newXtloge.setExamYear(selectedPaper.getExamYear());
						newXtloge.setExamPeriod(selectedPaper.getExamPeriod());
						newXtloge.setStudyUnit(selectedPaper.getStudyUnit());
						newXtloge.setPaperNo(selectedPaper.getPaperNo());
						newXtloge.setDateReceived(xtloge.getDateReceived());
						newXtloge.setDateToDept(xtloge.getDateToDept());
						newXtloge.setDateFromDept(xtloge.getDateFromDept());
						newXtloge.setDateToPrint("0001-01-01");
						//Change start - 20110117
//						record.setQuantToPrint(xtloge.getQuantToPrint());
//						record.setQuantCalcOn(xtloge.getQuantCalcOn());
						newXtloge.setQuantToPrint("0");
						newXtloge.setQuantCalcOn("0001-01-01");
						//Change end - 20110117
						newXtloge.setDate2ToPrint(xtloge.getDate2ToPrint());
						newXtloge.setQuant2ToPrint(xtloge.getQuant2ToPrint());
						newXtloge.setQuant2CalcOn(xtloge.getQuant2CalcOn());	
						newXtloge.setElectronicFlag(xtloge.getElectronicFlag());
					}else{
						//Retract question paper sent to print production - not first send
						//xtloge does exists - update xtloge set date2_to_print ?? confirm reset quant2_to_print & quant_calced_on				
						
						newXtloge.setExamYear(selectedPaper.getExamYear());
						newXtloge.setExamPeriod(selectedPaper.getExamPeriod());
						newXtloge.setStudyUnit(selectedPaper.getStudyUnit());
						newXtloge.setPaperNo(selectedPaper.getPaperNo());
						newXtloge.setDateReceived(xtloge.getDateReceived());
						newXtloge.setDateToDept(xtloge.getDateToDept());
						newXtloge.setDateFromDept(xtloge.getDateFromDept());
						newXtloge.setDateToPrint(xtloge.getDateToPrint());
						newXtloge.setQuantToPrint(xtloge.getQuantToPrint());
						newXtloge.setQuantCalcOn(xtloge.getQuantCalcOn());
						newXtloge.setDate2ToPrint("0001-01-01");
//						if (xtloge.getQuant2CalcOn().equalsIgnoreCase("00010101")){
//							record.setDate2ToPrint("00010101");
//						}else{
//							//determine last time document was send to PP and not retracted
//							String sendDate = "00010101";
//							sendDate=dao.getLastDateDocSendnotRetracted(log.getExamYear(), log.getExamPeriod(), log.getStudyUnit(), log.getPaperNo(), log.getDocType(), xtloge.getDate2ToPrint(), "PRINT_PROD");
//							record.setDate2ToPrint(sendDate);
//						}	
						//Change start - 20110117
//						record.setQuant2ToPrint(xtloge.getQuant2ToPrint());
//						record.setQuant2CalcOn(xtloge.getQuant2CalcOn());
						newXtloge.setQuant2ToPrint("0");
						newXtloge.setQuant2CalcOn("0001-01-01");
						//Change end - 20110117
						newXtloge.setElectronicFlag(xtloge.getElectronicFlag());
					}
				}
			}
			
			//call dao to do all inserts for upload
			//update xtloge if necessary
			//insert log record for mother code and equivalents
			//insert status record for mother code and equivalents
			String errMsg="";
			errMsg = dao.updateRecordsOnAction(updateList, newStatus, newLog, null, newXtloge, null);	
			
			//get files from passed through from Harry
//			String retrievedFiles = "J:\\COS111U201006_1Q\\1.pdf;J:\\COS111U201006_1Q\\2.pdf";
			String[] temp;
			String retrievedFiles;
						
			if (request.getParameter("fn")==null){
				retrievedFiles = "";
			}else{
				retrievedFiles = request.getParameter("fn");
			}
			
			//read xporul to determine which type of documents must be listed on retrieve "edit" or "print"			
			Rule rule = new Rule();
			rule = dao.getRule("RETRIEVE", currentStatus.getDocType(), currentStatus.getFromRole(), null);
			examPaperOnlineForm.setDocumentsToUploadShown(rule.getDocsShown());
			
			List docList = new ArrayList();
			Paper paper = new Paper();
			paper.setExamYear(selectedPaper.getExamYear());
			paper.setExamPeriod(selectedPaper.getExamPeriod());
			paper.setStudyUnit(selectedPaper.getStudyUnit());
			paper.setPaperNo(selectedPaper.getPaperNo());
			paper.setDocType(selectedPaper.getDocType());
			
			String delimited = ";";
			if (!retrievedFiles.equalsIgnoreCase("")){
				temp = retrievedFiles.split(delimited);
				for (int i=0; i<temp.length ; i++){
					 String delimited2 = "\\\\";
					 String[] temp2 = temp[i].toString().split(delimited2);
					 Document document = new Document();
					 String documentStr = temp2[1];	
//					 document.setStudyUnit(documentStr.substring(0, 7));
//					 document.setExamYear(Short.parseShort(documentStr.substring(7, 11)));
//					 document.setExamPeriod(Short.parseShort(documentStr.substring(11, 13)));
//					 document.setPaperNo(Short.parseShort(documentStr.substring(14, 15)));
//					 document.setDocType("");
//					 if (documentStr.substring(15, 16).equalsIgnoreCase("Q")){
//						 document.setDocType("QUESTION");
//					 }
//					 if (documentStr.substring(15, 16).equalsIgnoreCase("M")){
//						 document.setDocType("MEMO");
//					 }
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
//					 docList.add(document);
				}		
			}
			paper.setFromUser(selectedPaper.getCurrentUser());
			paper.setFromRole(selectedPaper.getCurrentRole());
			paper.setDateSent(selectedPaper.getUpdatedOn());
			paper.setSenderResponse(selectedPaper.getApprovalStatus());
			paper.setSenderResponseText(selectedPaper.getMessage());			
			
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
			
			
			return mapping.findForward("retractStep2");	
		
	}
	
	public ActionForward open(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		return mapping.findForward("retractStep2");	
	}
	
	public ActionForward prevStep(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String backward="";
		ExamPaperOnlineForm examPaperOnlineForm = (ExamPaperOnlineForm) form;
		
		if (examPaperOnlineForm.getCurrentPage().equalsIgnoreCase("retractStep1")) {
			backward="cancel";
		}
		if (examPaperOnlineForm.getCurrentPage().equalsIgnoreCase("retractStep2")) {
			examPaperOnlineForm.setDisplayListAction("D");
			backward="displayList";
		}		
		
		return mapping.findForward(backward);	
	}
	
	public ActionForward cancel(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
						
		return mapping.findForward("cancel");	
	}

}

