package za.ac.unisa.lms.tools.examscriptstats.actions;

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

import Expsc10h.Abean.Expsc10sLstExamPaperVenueSt;
import Expsh01h.Abean.Expsh01sExamScriptOutstanding;

import za.ac.unisa.lms.dao.StudentSystemGeneralDAO;
import za.ac.unisa.lms.dao.Xamprd;
import za.ac.unisa.lms.tools.examscriptstats.forms.*;
import za.ac.unisa.lms.tools.examscriptstats.dao.*;

public class ExamScriptStatsAction extends LookupDispatchAction{
		
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
		map.put("button.display", "displayData");
		map.put("linkStudentScripts", "linkStudentScripts");
		map.put("button.back", "prevStep");
		map.put("button.previous","displayData");
		map.put("button.first", "displayData");
		map.put("button.next", "displayData");
		return map;
	}
	
	public ActionForward initial(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		ExamScriptStatsForm examScriptStatsForm = (ExamScriptStatsForm) form;
		
		StudentSystemGeneralDAO daoxamprd = new StudentSystemGeneralDAO();
		List listExamPeriods = daoxamprd.getExamPeriods();
		for (int i=0; i<listExamPeriods.size(); i++){
			if (((Xamprd)(listExamPeriods.get(i))).getCode().intValue()==8 ||
					((Xamprd)(listExamPeriods.get(i))).getCode().intValue()==13){
				listExamPeriods.remove(i);
			}					
		}		
		examScriptStatsForm.setListExamPeriods(listExamPeriods);
		examScriptStatsForm.setDisplayAction("D");
		examScriptStatsForm.setDisplayStudentAction("D");
		examScriptStatsForm.setFirstVenue("");
		examScriptStatsForm.setLastVenue("");
		examScriptStatsForm.setFirstStudentNumber("0");
		examScriptStatsForm.setLastStudentNumber("0");
		
		return mapping.findForward("input");	
	}
	
	public ActionForward displayData(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
				
		ExamScriptStatsForm examScriptStatsForm = (ExamScriptStatsForm) form;
		
		String nextPage="";
		if (examScriptStatsForm.getCurrentPage().equalsIgnoreCase("input")){
			nextPage = displayExamScriptStats(mapping, form, request, response);			
		}else if (examScriptStatsForm.getCurrentPage().equalsIgnoreCase("displayStats")) {
			nextPage = displayExamScriptStats(mapping, form, request, response);
		}else if (examScriptStatsForm.getCurrentPage().equalsIgnoreCase("displayStudentScripts")){
			nextPage = displayStudentScripts(mapping, form, request, response);	
		}else{
			nextPage ="input";
		}
			
		return mapping.findForward(nextPage);	
	}
	
	public ActionForward linkStudentScripts(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
				
		String nextPage="";
		nextPage = displayStudentScripts(mapping, form, request, response);	
			
		return mapping.findForward(nextPage);	
	}
	
	public ActionForward prevStep(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		ExamScriptStatsForm examScriptStatsForm = (ExamScriptStatsForm) form;
		
		String prevPage="";
		
		if (examScriptStatsForm.getCurrentPage().equalsIgnoreCase("displayStudentScripts")){
			prevPage="displayExamScriptsStats";
		}
		return mapping.findForward(prevPage);
	}
	
	public String displayExamScriptStats(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {	
		
		ActionMessages messages = new ActionMessages();
		ExamScriptStatsForm examScriptStatsForm = (ExamScriptStatsForm) form;
		
		//validate input
		if (examScriptStatsForm.getExamYear()==null || examScriptStatsForm.getExamYear().trim().equalsIgnoreCase("")){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"Examination Year is required"));
		} else {
			if (!isInteger(examScriptStatsForm.getExamYear())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"Examination Year must be numeric"));
			}
		}
		if (examScriptStatsForm.getExamPeriod()==null || examScriptStatsForm.getExamPeriod().trim().equalsIgnoreCase("")){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"Examination Period is required"));
		}
		if (examScriptStatsForm.getStudyUnit()==null || examScriptStatsForm.getStudyUnit().trim().equalsIgnoreCase("")){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"Study Unit is required"));
		}else {
			examScriptStatsForm.setStudyUnit(examScriptStatsForm.getStudyUnit().toUpperCase());
			CourseDAO dao = new CourseDAO();			
			if (!dao.isValidStudyUnit(examScriptStatsForm.getStudyUnit())) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"Study Unit not found"));
			}
		}		
		if (examScriptStatsForm.getPaperNr()==null || examScriptStatsForm.getPaperNr().trim().equalsIgnoreCase("")){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"Paper Number is required"));
		} else {
			if (!isInteger(examScriptStatsForm.getPaperNr())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"Paper Number must be numeric"));
			}
		}	
		if (!messages.isEmpty()) {
			addErrors(request,messages);
			if (examScriptStatsForm.getCurrentPage().equalsIgnoreCase("displayStats")){
				return "displayExamScriptsStats";	
			} else {
				return "input";	
			}
			
		}
		
		Expsc10sLstExamPaperVenueSt op = new Expsc10sLstExamPaperVenueSt();
		operListener opl = new operListener();
		op.clear();
		
		op.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
		op.setInCsfClientServerCommunicationsClientVersionNumber((short) 3); 
		op.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
		op.setInCsfClientServerCommunicationsAction(examScriptStatsForm.getDisplayAction());
		
		//Starting
		op.setInStartingExamPaperVenueStatisticsExamYear(Short.parseShort(examScriptStatsForm.getExamYear()));
		op.setInStartingExamPaperVenueStatisticsMkExamPeriodCode(Short.parseShort(examScriptStatsForm.getExamPeriod()));
		op.setInStartingExamPaperVenueStatisticsMkStudyUnitCode(examScriptStatsForm.getStudyUnit());
		op.setInStartingExamPaperVenueStatisticsNr(Short.parseShort(examScriptStatsForm.getPaperNr()));
		op.setInStartingExamPaperVenueStatisticsMkExamVenueCode("");
		
		//low
		op.setInLowExamPaperVenueStatisticsExamYear(Short.parseShort(examScriptStatsForm.getExamYear()));
		op.setInLowExamPaperVenueStatisticsMkExamPeriodCode(Short.parseShort(examScriptStatsForm.getExamPeriod()));
		op.setInLowExamPaperVenueStatisticsMkStudyUnitCode(examScriptStatsForm.getStudyUnit());
		op.setInLowExamPaperVenueStatisticsNr(Short.parseShort(examScriptStatsForm.getPaperNr()));
		op.setInLowExamPaperVenueStatisticsMkExamVenueCode(examScriptStatsForm.getFirstVenue());
		
		//high
		op.setOnHighExamPaperVenueStatisticsExamYear(Short.parseShort(examScriptStatsForm.getExamYear()));
		op.setOnHighExamPaperVenueStatisticsMkExamPeriodCode(Short.parseShort(examScriptStatsForm.getExamPeriod()));
		op.setOnHighExamPaperVenueStatisticsMkStudyUnitCode(examScriptStatsForm.getStudyUnit());
		op.setOnHighExamPaperVenueStatisticsNr(Short.parseShort(examScriptStatsForm.getPaperNr()));
		op.setOnHighExamPaperVenueStatisticsMkExamVenueCode(examScriptStatsForm.getLastVenue());
		
		op.execute();
		if (opl.getException() != null)
			throw opl.getException();
		if (op.getExitStateType() < 3)
			throw new Exception(op.getExitStateMsg());
		//Do validation			
		
		examScriptStatsForm.setTotalScriptsExpected(op.getOutTotExpectedIefSuppliedCount());
		examScriptStatsForm.setTotalScriptsReceived(op.getOutTotReceivedIefSuppliedCount());
		examScriptStatsForm.setTotalScriptsOutstanding(op.getOutTotOutstandingIefSuppliedCount());
		examScriptStatsForm.setTotalStudentsAbsent(op.getOutTotAbsentIefSuppliedCount());
		examScriptStatsForm.setTotalMCQOutstanding(op.getOutTotMcqOutstIefSuppliedCount());
		examScriptStatsForm.setTotalMCQReceived(op.getOutTotMcqIefSuppliedCount());
		
		int groupCount = op.getOutGroupCount();
		if (groupCount==0){
			examScriptStatsForm.setFirstVenue("");
			examScriptStatsForm.setLastVenue("");			
		}else{
			examScriptStatsForm.setFirstVenue(op.getOutGExamPaperVenueStatisticsMkExamVenueCode(0));
			examScriptStatsForm.setLastVenue(op.getOutGExamPaperVenueStatisticsMkExamVenueCode(groupCount - 1));			
		}
		List listVenueStats = new ArrayList();
		
		for (int i =0; i < groupCount; i++) {
			VenueStatsRecord record = new VenueStatsRecord();
			record.setVenueCode(op.getOutGExamPaperVenueStatisticsMkExamVenueCode(i));
			record.setVenueDescription(op.getOutGWsExamVenueEngName(i));
			record.setTotalScriptsExpected(op.getOutGExamPaperVenueStatisticsNumberExpected(i));
			record.setTotalScriptsReceived(op.getOutGExamPaperVenueStatisticsNumberReceived(i));
			record.setTotalStudentsAbsent(op.getOutGExamPaperVenueStatisticsNumberAbsent(i));
			record.setTotalScriptsOutstanding(op.getOutGOutstandingIefSuppliedCount(i));
			record.setTotalMCQReceived(op.getOutGExamPaperVenueStatisticsNumberMcqReceived(i));
			record.setTotalMCQOutstanding(op.getOutGMcqOutstIefSuppliedCount(i));
			listVenueStats.add(record);				
		}
		examScriptStatsForm.setListVenueStats(listVenueStats);
		examScriptStatsForm.setNote(op.getOut10RedCsfStringsString40());
		if ("".equalsIgnoreCase(examScriptStatsForm.getNote())){
			examScriptStatsForm.setNote("Examination venues with outstanding examination scripts or Multiple Choice Question(MCQ) Sheets");
		}	
		examScriptStatsForm.setPageDownFlag(op.getOutCsfClientServerCommunicationsRgvScrollDownFlag());
		examScriptStatsForm.setPageUpFlag(op.getOutCsfClientServerCommunicationsRgvScrollUpFlag());
				
		return "displayExamScriptsStats";	
	}
	
	public String displayStudentScripts(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {	
		
		ExamScriptStatsForm examScriptStatsForm = (ExamScriptStatsForm) form;
		
		Expsh01sExamScriptOutstanding op = new Expsh01sExamScriptOutstanding();
		operListener opl = new operListener();
		op.clear();
		
		op.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
		op.setInCsfClientServerCommunicationsClientVersionNumber((short) 3); 
		op.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
		op.setInCsfClientServerCommunicationsAction(examScriptStatsForm.getDisplayStudentAction());
		
		op.setInStartingExamPaperVenueStatisticsExamYear(Short.parseShort(examScriptStatsForm.getExamYear()));
		op.setInStartingExamPaperVenueStatisticsMkExamPeriodCode(Short.parseShort(examScriptStatsForm.getExamPeriod()));
		op.setInStartingExamPaperVenueStatisticsMkStudyUnitCode(examScriptStatsForm.getStudyUnit());
		op.setInStartingExamPaperVenueStatisticsNr(Short.parseShort(examScriptStatsForm.getPaperNr()));
		op.setInStartingExamPaperVenueStatisticsMkExamVenueCode(examScriptStatsForm.getSelectedVenue());
		op.setInLowStudentExamResultMkStudentNr(Integer.parseInt(examScriptStatsForm.getFirstStudentNumber()));
		op.setInHighStudentExamResultMkStudentNr(Integer.parseInt(examScriptStatsForm.getLastStudentNumber()));
		
		
		op.execute();
		if (opl.getException() != null)
			throw opl.getException();
		if (op.getExitStateType() < 3)
			throw new Exception(op.getExitStateMsg());
		//Do validation		
		
		int groupCount = op.getOutGroupCount();
		
		if (groupCount==0){
			examScriptStatsForm.setFirstStudentNumber("0");
			examScriptStatsForm.setLastStudentNumber("0");			
		}else{
			examScriptStatsForm.setFirstStudentNumber(op.getOutGNameCsfStringsString50(0).substring(0,8));
			examScriptStatsForm.setLastStudentNumber(op.getOutGNameCsfStringsString50(groupCount - 1).substring(0,8));					
		}
		
		List listStudentScripts = new ArrayList();
		
		for (int i=0; i < groupCount; i++) {
			StudentOutstandingScriptRecord record = new StudentOutstandingScriptRecord();
			record.setStudent(op.getOutGNameCsfStringsString50(i));
			record.setComment(op.getOutGCommentCsfStringsString40(i));
			record.setOutstanding(op.getOutGOutstandingCsfStringsString30(i));
			listStudentScripts.add(record);
		}
		examScriptStatsForm.setListStudentScriptsOutstanding(listStudentScripts);
		examScriptStatsForm.setStudentPageDownFlag(op.getOutCsfClientServerCommunicationsRgvScrollDownFlag());
		examScriptStatsForm.setStudentPageUpFlag(op.getOutCsfClientServerCommunicationsRgvScrollUpFlag());
		
		for (int i=0; i < examScriptStatsForm.getListVenueStats().size(); i++){
			if (examScriptStatsForm.getSelectedVenue().equalsIgnoreCase(((VenueStatsRecord)(examScriptStatsForm.getListVenueStats().get(i))).getVenueCode())){
				examScriptStatsForm.setSelectedVenueDesc(((VenueStatsRecord)(examScriptStatsForm.getListVenueStats().get(i))).getVenueDescription());
				i = examScriptStatsForm.getListVenueStats().size();
			}			
		}		
		
		return "displayStudentScripts";	
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

