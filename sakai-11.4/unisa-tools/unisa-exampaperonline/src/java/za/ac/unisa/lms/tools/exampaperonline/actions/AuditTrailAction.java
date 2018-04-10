package za.ac.unisa.lms.tools.exampaperonline.actions;

import java.util.ArrayList;
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

import za.ac.unisa.lms.tools.exampaperonline.dao.ExamPaperOnlineDao;
import za.ac.unisa.lms.tools.exampaperonline.forms.ExamPaperOnlineForm;
import za.ac.unisa.lms.tools.exampaperonline.forms.ExamPaperOnlineLog;
import za.ac.unisa.lms.tools.exampaperonline.forms.LinkedPaper;

public class AuditTrailAction  extends LookupDispatchAction{
	
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
		map.put("button.display","displayAuditTrail");
		map.put("button.back", "prevStep");		
		return map;
	}
	
	public ActionForward initial(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		ActionMessages messages = new ActionMessages();
		ExamPaperOnlineForm examPaperOnlineForm = (ExamPaperOnlineForm) form;
		
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
		if (examPaperOnlineForm.getPaperNo()!=null && !examPaperOnlineForm.getPaperNo().equalsIgnoreCase("")){
			examPaperOnlineForm.setListCriteriaPaperNo(examPaperOnlineForm.getPaperNo());
		}
		
		if (examPaperOnlineForm.getListCriteriaStudyUnit()!=null && !examPaperOnlineForm.getListCriteriaStudyUnit().equalsIgnoreCase("")){
			examPaperOnlineForm.setListCriteriaStudyUnit(examPaperOnlineForm.getListCriteriaStudyUnit().toUpperCase().trim());
		}
		ExamPaperOnlineDao dao = new ExamPaperOnlineDao();	
		ExamPaperOnlineAction action = new ExamPaperOnlineAction();
		//Get Mother code and possible equivalents
		String linkedPaperDesc = "";
		LinkedPaper linkedPaper = new LinkedPaper();
		linkedPaper = action.getLinkedPaperStatus(examPaperOnlineForm.getListCriteriaStudyUnit(), 
				examPaperOnlineForm.getListCriteriaExamYear(),
				examPaperOnlineForm.getListCriteriaExamPeriod());
		
		/*String linkedPaperDesc = "";
		List motherList = dao.getEquivalentMotherCode(examPaperOnlineForm.getListCriteriaStudyUnit(),
				examPaperOnlineForm.getExamYear(), 
				examPaperOnlineForm.getExamPeriod());
		if (motherList.size()>0){			
			linkedPaperDesc = "(Mother code: " + motherList.get(0).toString();
			//Linked paper
			//Get possible other Linked papers			
			List eqvList = dao.getEquivalents(motherList.get(0).toString().toUpperCase().trim(), 
					examPaperOnlineForm.getListCriteriaExamYear(), 
					examPaperOnlineForm.getListCriteriaExamPeriod());
			String eqvString = "";
			for (int j = 0; j < eqvList.size(); j++){
				if (eqvString.equalsIgnoreCase("")){
					if (eqvList.size()==1){
						eqvString = ", Linked paper: " + eqvList.get(j).toString();
					}else{
						eqvString = ", Linked papers: " + eqvList.get(j).toString();
					}					
				}else{
					eqvString = eqvString + "," + eqvList.get(j).toString();
				}				
			}
			linkedPaperDesc = linkedPaperDesc + eqvString + ")";
		}else{
			List eqvList = dao.getEquivalents(examPaperOnlineForm.getStudyUnit(), 
					examPaperOnlineForm.getExamYear(), 
					examPaperOnlineForm.getExamPeriod());
			if (eqvList.size()>0){
				//mother code with equivalents.				
				for (int j = 0; j < eqvList.size(); j++){
					if (linkedPaperDesc.equalsIgnoreCase("")){
						if (eqvList.size()==1){
							linkedPaperDesc = "(Linked paper: " + eqvList.get(j).toString() + ")";
						}else{
							linkedPaperDesc = "(Linked papers: " + eqvList.get(j).toString() + ")";
						}					
					}else{
						linkedPaperDesc = linkedPaperDesc + "," + eqvList.get(j).toString();
					}
				}
			}
		}*/
		examPaperOnlineForm.setEquivalentStr(linkedPaper.getLinkedDesc());
		List logList = new ArrayList();
		logList = dao.getExamPaperAuditTrail(Short.parseShort(examPaperOnlineForm.getListCriteriaExamYear()),
				Short.parseShort(examPaperOnlineForm.getListCriteriaExamPeriod()),
				examPaperOnlineForm.getListCriteriaStudyUnit(),
				Short.parseShort(examPaperOnlineForm.getListCriteriaPaperNo()), 
				examPaperOnlineForm.getListCriteriaPaperContent());	
		
		if (logList.size()==0){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
							"There are no log entries for this examination paper/memorandum"));
					addErrors(request,messages);					
					return mapping.findForward("cancel");							
		}	
		
		
		
  		for (int i=0; i <logList.size(); i++){
  			((ExamPaperOnlineLog)logList.get(i)).setUpdatedOn(((ExamPaperOnlineLog)logList.get(i)).getUpdatedOn().substring(0, 19));
  		}
		
		examPaperOnlineForm.setAvailableDocList(logList);
		
		return mapping.findForward("displayAuditTrail");	
	}
	
	public ActionForward displayAuditTrail(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		ActionMessages messages = new ActionMessages();
		ExamPaperOnlineForm examPaperOnlineForm = (ExamPaperOnlineForm) form;
		
		if (examPaperOnlineForm.getListCriteriaExamYear() == null || 
				examPaperOnlineForm.getListCriteriaExamYear().equals("0") || 
				examPaperOnlineForm.getListCriteriaExamYear().trim().equalsIgnoreCase("")){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
							"Examination Year is required."));
		}else{
			if (!isInteger(examPaperOnlineForm.getListCriteriaExamYear())) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
								"Examination Year must be numberic."));
			}
		}						
		if (examPaperOnlineForm.getListCriteriaStudyUnit() == null || examPaperOnlineForm.getListCriteriaStudyUnit().trim().equalsIgnoreCase("")) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
							"Study Unit is required."));
		} 
		if (examPaperOnlineForm.getListCriteriaPaperNo() == null || 
				examPaperOnlineForm.getListCriteriaPaperNo().equals("0") ||
				examPaperOnlineForm.getListCriteriaPaperNo().trim().equalsIgnoreCase("")) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
							"Paper Number is required."));
		} else{
			if (!isInteger(examPaperOnlineForm.getListCriteriaPaperNo())) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
								"Paper Number must be numberic."));
			}
		}
		if (examPaperOnlineForm.getListCriteriaPaperContent() == null || examPaperOnlineForm.getListCriteriaPaperContent().trim().equalsIgnoreCase("")) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
							"Document Type is required."));
		} 
		
		if (!messages.isEmpty()) {
			addErrors(request,messages);
			return mapping.findForward("displayAuditTrail");
		}
		if (examPaperOnlineForm.getListCriteriaStudyUnit()!=null && !examPaperOnlineForm.getListCriteriaStudyUnit().equalsIgnoreCase("")){
			examPaperOnlineForm.setListCriteriaStudyUnit(examPaperOnlineForm.getListCriteriaStudyUnit().toUpperCase().trim());
		}
		ExamPaperOnlineDao dao = new ExamPaperOnlineDao();
		ExamPaperOnlineAction action = new ExamPaperOnlineAction();
		LinkedPaper linkedPaper = new LinkedPaper();
		linkedPaper = action.getLinkedPaperStatus(examPaperOnlineForm.getListCriteriaStudyUnit(), 
				examPaperOnlineForm.getListCriteriaExamYear(),
				examPaperOnlineForm.getListCriteriaExamPeriod());
		examPaperOnlineForm.setEquivalentStr(linkedPaper.getLinkedDesc());
		
		List logList = new ArrayList();
		logList = dao.getExamPaperAuditTrail(Short.parseShort(examPaperOnlineForm.getListCriteriaExamYear()),
				Short.parseShort(examPaperOnlineForm.getListCriteriaExamPeriod()),
				examPaperOnlineForm.getListCriteriaStudyUnit(),
				Short.parseShort(examPaperOnlineForm.getListCriteriaPaperNo()), 
				examPaperOnlineForm.getListCriteriaPaperContent());	
		
		if (logList.size()==0){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
							"There are no log entries for this examination paper/memorandum"));
					addErrors(request,messages);										
		}			
		
//		for (int i=0; i <logList.size(); i++){
//			ExamPaperOnlineLog log = new ExamPaperOnlineLog();
//			if (((ExamPaperOnlineLog)logList.get(i)).getMessage().length() > 30){
//				((ExamPaperOnlineLog)logList.get(i)).setMessage(((ExamPaperOnlineLog)logList.get(i)).getMessage().substring(0,30) + "   ....more");
//			}
//		}
		
		examPaperOnlineForm.setAvailableDocList(logList);
		
		return mapping.findForward("displayAuditTrail");
	}	
	
	public ActionForward prevStep(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String backward="";
		ExamPaperOnlineForm examPaperOnlineForm = (ExamPaperOnlineForm) form;
		
		if (examPaperOnlineForm.getCurrentPage().equalsIgnoreCase("displayAuditTrail")) {
			backward="cancel";
		}			
		
		return mapping.findForward(backward);	
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
