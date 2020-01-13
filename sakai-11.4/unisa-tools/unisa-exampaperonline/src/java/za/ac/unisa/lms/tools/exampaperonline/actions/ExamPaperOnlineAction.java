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

import za.ac.unisa.lms.dao.Gencod;
import za.ac.unisa.lms.dao.StudentSystemGeneralDAO;
import za.ac.unisa.lms.dao.Xamprd;
import za.ac.unisa.lms.dao.general.ModuleDAO;
import za.ac.unisa.lms.dao.general.UserDAO;
import za.ac.unisa.lms.domain.general.Person;
import za.ac.unisa.lms.domain.general.StudyUnit;
import za.ac.unisa.lms.tools.exampaperonline.dao.ExamPaperOnlineDao;
import za.ac.unisa.lms.tools.exampaperonline.forms.ExamPaperOnlineForm;
import za.ac.unisa.lms.tools.exampaperonline.forms.LinkedPaper;

public class ExamPaperOnlineAction extends LookupDispatchAction{
		
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
			map.put("button.back", "prevStep");
			map.put("button.clear", "clear");
			return map;
		}
		
		public ActionForward prevStep(
				ActionMapping mapping,
				ActionForm form,
				HttpServletRequest request,
				HttpServletResponse response) throws Exception {
				
				ActionMessages messages = new ActionMessages();    
				ExamPaperOnlineForm examPaperOnlineForm = (ExamPaperOnlineForm) form;
				
				String backward="input";
				
				if (examPaperOnlineForm.getFromAction().equalsIgnoreCase("LISTDOCS")){
					backward="listdocs";
				}
				
				return mapping.findForward(backward);
		}
					
		public ActionForward nextStep(
				ActionMapping mapping,
				ActionForm form,
				HttpServletRequest request,
				HttpServletResponse response) throws Exception {
				
				ActionMessages messages = new ActionMessages();    
				ExamPaperOnlineForm examPaperOnlineForm = (ExamPaperOnlineForm) form;
				String nextPage="";
				
				 examPaperOnlineForm.setListCriteriaExamYear("");
				 examPaperOnlineForm.setListCriteriaExamPeriod("");
				 examPaperOnlineForm.setListCriteriaPaperContent("");
				 examPaperOnlineForm.setListCriteriaStudyUnit("");
				
				//do validation
				if (examPaperOnlineForm.getToolAction().equalsIgnoreCase("UPLOAD") ||						
						examPaperOnlineForm.getToolAction().equalsIgnoreCase("AUDIT") ||
						examPaperOnlineForm.getToolAction().equalsIgnoreCase("PRINTINSTR") ||
						examPaperOnlineForm.getToolAction().equalsIgnoreCase("COVER")) {
					if (examPaperOnlineForm.getExamYear() == null || 
							examPaperOnlineForm.getExamYear().equals("0") || 
							examPaperOnlineForm.getExamYear().trim().equalsIgnoreCase("")){
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage",
										"Examination Year is required."));
					}else{
						if (!isInteger(examPaperOnlineForm.getExamYear())) {
							messages.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("message.generalmessage",
											"Examination Year must be numberic."));
						}
					}						
					if (examPaperOnlineForm.getStudyUnit() == null || examPaperOnlineForm.getStudyUnit().trim().equalsIgnoreCase("")) {
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage",
										"Study Unit is required."));
					} 
					if (examPaperOnlineForm.getPaperNo() == null || 
							examPaperOnlineForm.getPaperNo().equals("0") ||
							examPaperOnlineForm.getPaperNo().trim().equalsIgnoreCase("")) {
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage",
										"Paper Number is required."));
					} else{
						if (!isInteger(examPaperOnlineForm.getPaperNo())) {
							messages.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("message.generalmessage",
											"Paper Number must be numberic."));
						}
					}
					if (examPaperOnlineForm.getPaperContent() == null || examPaperOnlineForm.getPaperContent().trim().equalsIgnoreCase("")) {
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage",
										"Document Type is required."));
					} 					
				}
				
				if (!messages.isEmpty()) {
					addErrors(request,messages);
					return mapping.findForward("input");
				}
				
				//validate Study unit and get module description
				if (examPaperOnlineForm.getStudyUnit()!= null && !examPaperOnlineForm.getStudyUnit().trim().equalsIgnoreCase("")){
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
				
				
				if (!messages.isEmpty()) {
					addErrors(request,messages);
					return mapping.findForward("input");
				}
				
				//get exam period desc
				for (int i=0; i < examPaperOnlineForm.getListExamPeriods().size();i++){
					Xamprd examperiod = new Xamprd();
					examperiod = (Xamprd)examPaperOnlineForm.getListExamPeriods().get(i);
					if (examperiod.getCode()==Short.parseShort(examPaperOnlineForm.getExamPeriod())){
						examPaperOnlineForm.setExamPeriodDesc(examperiod.getEngDescription());
						i=examPaperOnlineForm.getListExamPeriods().size();
					}
				}
				
				//get tool action desc
//				for (int i=0; i < examPaperOnlineForm.getListToolActions().size();i++){
//					Gencod gencod = new Gencod();
//					gencod = (Gencod)examPaperOnlineForm.getListToolActions().get(i);
//					if (gencod.getCode().equalsIgnoreCase(examPaperOnlineForm.getToolAction())){
//						examPaperOnlineForm.setToolActionDesc(gencod.getEngDescription());
//						i=examPaperOnlineForm.getListToolActions().size();
//					}
//				}	
				
				
				if("input".equalsIgnoreCase(examPaperOnlineForm.getCurrentPage())){
					if (examPaperOnlineForm.getToolAction().equalsIgnoreCase("UPLOAD")) {
						nextPage="upload";		
					} else if (examPaperOnlineForm.getToolAction().equalsIgnoreCase("RETRIEVE")) {
						nextPage="retrieve";
					} else if (examPaperOnlineForm.getToolAction().equalsIgnoreCase("RETRACT")) {
						nextPage="retract";
					} else if (examPaperOnlineForm.getToolAction().equalsIgnoreCase("LISTDOCS")) {
						nextPage="listdocs";
					} else if (examPaperOnlineForm.getToolAction().equalsIgnoreCase("AUDIT")) {
						nextPage="audit";
					} else if (examPaperOnlineForm.getToolAction().equalsIgnoreCase("RECOVER")) {
						nextPage="recover";
					} else if (examPaperOnlineForm.getToolAction().equalsIgnoreCase("PRINTINSTR")) {
					    nextPage="printinstr";
					} else if (examPaperOnlineForm.getToolAction().equalsIgnoreCase("RESET")){
						nextPage="reset";
					}else if (examPaperOnlineForm.getToolAction().equalsIgnoreCase("COVER")) {
						examPaperOnlineForm.setXpoUrl("https://mydev.unisa.ac.za/unisa-findtool/default.do?sharedTool=unisa.exampapers");
						examPaperOnlineForm.setXpoAction("GOTO");
					    nextPage="input";
					} else 
					    nextPage="input";
				} 				
				return mapping.findForward(nextPage);				
			}
		
		
		public ActionForward initial(
				ActionMapping mapping,
				ActionForm form,
				HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			
			ActionMessages messages = new ActionMessages();
			ExamPaperOnlineForm examPaperOnlineForm = (ExamPaperOnlineForm) form;
			
			String userId = "";
			String system = "";
						
			if (request.getParameter("userId")==null){
				userId = "PRETOJ";
			}else{
				userId = request.getParameter("userId");
			}
			examPaperOnlineForm.setUserId(userId);
			
			Person person = new Person();
			UserDAO userdao = new UserDAO();
			person = userdao.getPerson(examPaperOnlineForm.getUserId());			
			
			if (person.getPersonnelNumber()==null){
				//user does not have access to this function 
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"User not found"));
				addErrors(request,messages);
				return mapping.findForward("displayErrorPage");
			}			

			examPaperOnlineForm.setUser(person);	
			List listUserId = new ArrayList();
			
			listUserId.add(userId);
			ExamPaperOnlineDao roleDao = new ExamPaperOnlineDao();
			if (roleDao.isExamsQa1(userId)){
				listUserId.add("EXAMS_QA1");				
			}
			if (roleDao.isExamsQa2(userId)){
				listUserId.add("EXAMS_QA2");				
			}
			if (roleDao.isPrintProd(userId)){
				listUserId.add("PRINT_PROD");
			}
			if (roleDao.isArchive(userId)){
				listUserId.add("ARCHIVE");
			}
			examPaperOnlineForm.setListUserId(listUserId);
			
			//determine user roles - only interested in:
			//'1ST_EXAM' - first examiner
			//'2ND_EXAM' - second examiner
			//'EXT_EXAM' - external examiner
			//COD and stand-in COD's
			//Examination section - 'EXAM_QA1','EXAM_QA2'
			//'PP' - print production
					
			
		    examPaperOnlineForm.setExamPeriod("");
		    examPaperOnlineForm.setExamYear("");
		    examPaperOnlineForm.setStudyUnit("");
		    examPaperOnlineForm.setPaperNo("");		    
		    
		    //get exam periods
		    StudentSystemGeneralDAO dao = new StudentSystemGeneralDAO();
			List examPeriods = dao.getExamPeriods();
			examPaperOnlineForm.setListExamPeriods(examPeriods);
			
			//get tool actions
			List toolActions = new ArrayList();
			for (int i=0; i < dao.getGenCodes((short)112,3).size(); i++) {
				Gencod action = new Gencod();
				action = (Gencod)(dao.getGenCodes((short)112,3).get(i));
				if (action.getCode().equalsIgnoreCase("PRINTINSTR")){
					for (int j=0; j < listUserId.size(); j++){
						if (listUserId.get(j).toString().equalsIgnoreCase("EXAMS_QA2")||
								listUserId.get(j).toString().equalsIgnoreCase("EXAMS_QA1")||
								listUserId.get(j).toString().equalsIgnoreCase("PRINT_PROD")){
								toolActions.add(action);
								j=listUserId.size();
						}
					}
				}else if (action.getCode().equalsIgnoreCase("RESET")){
					if(roleDao.resetValid(examPaperOnlineForm.getUserId().toUpperCase().trim())){
					//only display RESET action if user a possible 1ST or 2ND examiner for current or future modules	
						toolActions.add(action);						
					}
				}
				else{
					toolActions.add(action);
				}				
			}
			examPaperOnlineForm.setListToolActions(toolActions);
			
			//get document content types
			List docContentTypes = new ArrayList();
			for (int i=0; i < dao.getGenCodes((short)113,3).size(); i++) {
				docContentTypes.add(i, (Gencod)(dao.getGenCodes((short)113,3).get(i)));
			}
			examPaperOnlineForm.setListPaperContentTypes(docContentTypes);
			
			//get senders responses
			List senderResponses = new ArrayList();
			for (int i=0; i < dao.getGenCodes((short)114,1).size(); i++) {
				senderResponses.add(i, (Gencod)(dao.getGenCodes((short)114,1).get(i)));
			}
			examPaperOnlineForm.setListSenderResponses(senderResponses);
			
			return mapping.findForward("input");	
		}	
		
		public ActionForward clear(
				ActionMapping mapping,
				ActionForm form,
				HttpServletRequest request,
				HttpServletResponse response) throws Exception {
				
				ActionMessages messages = new ActionMessages();    
				ExamPaperOnlineForm examPaperOnlineForm = (ExamPaperOnlineForm) form;
				
				examPaperOnlineForm.setExamPeriod("");
				examPaperOnlineForm.setExamYear("");
				examPaperOnlineForm.setStudyUnit("");
				examPaperOnlineForm.setPaperNo("");
				examPaperOnlineForm.setPaperContent("");
				
				return mapping.findForward("input");
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
		
		public LinkedPaper getLinkedPaperStatus(String studyUnit, String examYear, String examPeriod){
			LinkedPaper linkedPaper = new LinkedPaper();
			try{		
			ExamPaperOnlineDao dao = new ExamPaperOnlineDao();
			String linkedPaperDesc = "";
			linkedPaper = dao.getLinkedInfo(studyUnit, examYear, examPeriod);
			if (linkedPaper.getInputStudyUnitStatus().equalsIgnoreCase("SINGLE")){
				//do nothing
			}else if (linkedPaper.getInputStudyUnitStatus().equalsIgnoreCase("MOTHER")){
				if (linkedPaper.getLinkedPapers().size()>0){
					//mother code with equivalents.				
					for (int j = 0; j < linkedPaper.getLinkedPapers().size(); j++){
						if (linkedPaperDesc.equalsIgnoreCase("")){
							if (linkedPaper.getLinkedPapers().size()==1){
								linkedPaperDesc = "(Linked paper: " + linkedPaper.getLinkedPapers().get(j).toString();
							}else{
								linkedPaperDesc = "(Linked papers: " + linkedPaper.getLinkedPapers().get(j).toString();
							}					
						}else{
							linkedPaperDesc = linkedPaperDesc + "," + linkedPaper.getLinkedPapers().get(j).toString();
						}
					}
					linkedPaperDesc = linkedPaperDesc.trim() + ")";
				}
			}else if (linkedPaper.getInputStudyUnitStatus().equalsIgnoreCase("LINKED")){
				linkedPaperDesc = "(Mother code: " + linkedPaper.getMotherCode().toString();
				//Linked paper
				//Get possible other Linked papers			
				String eqvString = "";
				for (int j = 0; j < linkedPaper.getLinkedPapers().size(); j++){
					if (eqvString.equalsIgnoreCase("")){
						if (linkedPaper.getLinkedPapers().size()==1){
							eqvString = ", Linked paper: " + linkedPaper.getLinkedPapers().get(j).toString();
						}else{
							eqvString = ", Linked papers: " + linkedPaper.getLinkedPapers().get(j).toString();
						}					
					}else{
						eqvString = eqvString + "," + linkedPaper.getLinkedPapers().get(j).toString();
					}				
				}
				linkedPaperDesc = linkedPaperDesc + eqvString + ")";
			}
			linkedPaper.setLinkedDesc(linkedPaperDesc);
			}
			catch (Exception e)
			{}
			return linkedPaper;
		}
		
}
