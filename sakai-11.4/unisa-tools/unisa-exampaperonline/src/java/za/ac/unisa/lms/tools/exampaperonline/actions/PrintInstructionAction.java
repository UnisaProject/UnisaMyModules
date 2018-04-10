package za.ac.unisa.lms.tools.exampaperonline.actions;

import java.util.ArrayList;
import java.util.List;
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

import Expll01h.Abean.Expll01sMntExamPaperLog;

import za.ac.unisa.lms.dao.Xamprd;
import za.ac.unisa.lms.tools.exampaperonline.dao.ExamPaperOnlineDao;
import za.ac.unisa.lms.tools.exampaperonline.forms.ExamPaperOnlineForm;

public class PrintInstructionAction extends LookupDispatchAction{
	
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
		
			examPaperOnlineForm.setFromAction("PRINTINSTR");
			
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
			}	
			
			//determine if examination cover docket exists
			ExamPaperOnlineDao dao = new ExamPaperOnlineDao();
			boolean docketExists = false;
			
			docketExists = dao.coverDocketExist(examPaperOnlineForm.getExamYear(), 
					examPaperOnlineForm.getExamPeriod(),
					examPaperOnlineForm.getStudyUnit(),
					examPaperOnlineForm.getPaperNo());
			
			if (!docketExists){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
								"Examination paper cover docket does not exists."));
			} 	
		
			if (!messages.isEmpty()) {
				addErrors(request,messages);
				if (examPaperOnlineForm.getFromAction().equalsIgnoreCase("LISTDOCS")){
					return mapping.findForward("listdocs");
				}else{
					return mapping.findForward("cancel");
				}	
			}else{
				return mapping.findForward("displayPrintinstr");
			}				
			
		}	
		
		public ActionForward prevStep(
				ActionMapping mapping,
				ActionForm form,
				HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			
			String backward="";
			ExamPaperOnlineForm examPaperOnlineForm = (ExamPaperOnlineForm) form;
			
			if (examPaperOnlineForm.getCurrentPage().equalsIgnoreCase("printInstructions")) {
				if (examPaperOnlineForm.getFromAction().equalsIgnoreCase("LISTDOCS")){
					backward="listdocs";
				}else{
					backward="cancel";
				}			
			}			
			
			return mapping.findForward(backward);	
		}
		
		public Integer getPrintQuantity(String examYear, String examPeriod, String studyUnit, String paperNo)throws Exception {
			Integer quantity=0;
			
			Expll01sMntExamPaperLog op = new Expll01sMntExamPaperLog();
			operListener opl = new operListener();
			op.clear();
			
			op.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
			op.setInCsfClientServerCommunicationsClientVersionNumber((short) 3); 
			op.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
			op.setInCsfClientServerCommunicationsAction("CQ");
			op.setInExamTypistLogEntryPaperNo(Short.parseShort(paperNo));
			op.setInKeyExamTypistLogExamYear(Short.parseShort(examYear));
			op.setInKeyExamTypistLogMkExamPeriodCod(Short.parseShort(examPeriod));
			op.setInKeyExamTypistLogMkStudyUnitCode(studyUnit);
			
			op.execute();
			if (opl.getException() != null)
				throw opl.getException();
			if (op.getExitStateType() < 3)
				throw new Exception(op.getExitStateMsg());			
			
			quantity = op.getOutPrintQuantityIefSuppliedCount();
			
			return quantity;
		}
		
		public List getEquivalents(String examYear, String examPeriod, String studyUnit, String paperNo)throws Exception {
			ArrayList equivalents = new ArrayList();
			
			Expll01sMntExamPaperLog op = new Expll01sMntExamPaperLog();
			operListener opl = new operListener();
			op.clear();
			
			op.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
			op.setInCsfClientServerCommunicationsClientVersionNumber((short) 3); 
			op.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
			op.setInCsfClientServerCommunicationsAction("GE");
			op.setInExamTypistLogEntryPaperNo(Short.parseShort(paperNo));
			op.setInKeyExamTypistLogExamYear(Short.parseShort(examYear));
			op.setInKeyExamTypistLogMkExamPeriodCod(Short.parseShort(examPeriod));
			op.setInKeyExamTypistLogMkStudyUnitCode(studyUnit);
			
			op.execute();
			if (opl.getException() != null)
				throw opl.getException();
			if (op.getExitStateType() < 3)
				throw new Exception(op.getExitStateMsg());			
			
			int count = 0;
			count = op.getOutEquGroupCount();
			
			for (int i=0; i < count ; i++){
				equivalents.add(op.getOutEquGWsExamEquivalentsWsEquivalentCode(i).toString());
			}			
			return equivalents;
		}

}
