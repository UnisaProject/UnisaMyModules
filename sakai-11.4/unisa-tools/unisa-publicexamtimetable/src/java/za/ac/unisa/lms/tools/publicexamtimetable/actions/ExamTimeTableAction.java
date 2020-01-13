//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.publicexamtimetable.actions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import org.apache.struts.util.LabelValueBean;

import za.ac.unisa.lms.tools.publicexamtimetable.dao.ExamTimetableDAO;
import za.ac.unisa.lms.tools.publicexamtimetable.forms.ExamTimeTableForm;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * MyEclipse Struts
 * Creation date: 08-22-2006
 *
 * XDoclet definition:
 * @struts.action parameter="action"
 * @struts.action-forward name="subjectinputforward" path="/WEB-INF/jsp/subjectinput.jsp" contextRelative="true"
 */
public class ExamTimeTableAction extends LookupDispatchAction {
	Log log = LogFactory.getLog(this.getClass());
	protected Map<String, String> getKeyMethodMap(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("inputSubject","inputSubject");
		map.put("button.clear","clearForm");
		map.put("button.display","showExamTimetable");
		map.put("button.back","inputSubject");
		map.put("timetablePage","timetablePage");
		map.put("button.print","printExamTimetable");
		return map;
	}

	public ActionForward timetablePage(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response){
			return mapping.findForward("timetableforward");
	}

	public ActionForward inputSubject(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response){
		ExamTimeTableForm examTimeTableForm = (ExamTimeTableForm) form;
		ArrayList<LabelValueBean> periods = new ArrayList<LabelValueBean>();
		String listDate;
		Integer currentYear;
		Integer currentMonth;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		Calendar curDate1 = Calendar.getInstance();
		Date curDate = new Date();
		listDate = formatter.format(curDate);
		examTimeTableForm.setAcademicYear(new Integer(curDate1.get(Calendar.YEAR)).toString());
		currentYear = new Integer(curDate1.get(Calendar.YEAR));
		currentMonth = new Integer(curDate1.get(Calendar.MONTH))+1;
		periods.add(new LabelValueBean("January/February "+(new Integer(curDate1.get(Calendar.YEAR)).intValue()-1),"1-"+(new Integer(curDate1.get(Calendar.YEAR)).intValue()-1)));
		periods.add(new LabelValueBean("May/June "+(new Integer(curDate1.get(Calendar.YEAR)).intValue()-1),"6-"+(new Integer(curDate1.get(Calendar.YEAR)).intValue()-1)));
		periods.add(new LabelValueBean("October/November "+(new Integer(curDate1.get(Calendar.YEAR)).intValue()-1),"10-"+(new Integer(curDate1.get(Calendar.YEAR)).intValue()-1)));
		periods.add(new LabelValueBean("January/February "+new Integer(curDate1.get(Calendar.YEAR)),"1-"+(new Integer(curDate1.get(Calendar.YEAR)))));
		periods.add(new LabelValueBean("May/June "+new Integer(curDate1.get(Calendar.YEAR)),"6-"+(new Integer(curDate1.get(Calendar.YEAR)))));
		periods.add(new LabelValueBean("October/November "+new Integer(curDate1.get(Calendar.YEAR)),"10-"+(new Integer(curDate1.get(Calendar.YEAR)))));
		periods.add(new LabelValueBean("January/February "+(new Integer(curDate1.get(Calendar.YEAR)).intValue()+1),"1-"+(new Integer(curDate1.get(Calendar.YEAR)).intValue()+1)));
		periods.add(new LabelValueBean("May/June "+(new Integer(curDate1.get(Calendar.YEAR)).intValue()+1),"6-"+(new Integer(curDate1.get(Calendar.YEAR)).intValue()+1)));
		periods.add(new LabelValueBean("October/November "+(new Integer(curDate1.get(Calendar.YEAR)).intValue()+1),"10-"+(new Integer(curDate1.get(Calendar.YEAR)).intValue()+1)));
		
		if (currentMonth == 1 || currentMonth == 2 ){
			examTimeTableForm.setExamPeriodDesc("January/February "+currentYear);
			examTimeTableForm.setExamPeriod("1-"+currentYear);
		} else if (currentMonth == 3 || currentMonth == 4 || currentMonth == 5 || currentMonth == 6 ){
			examTimeTableForm.setExamPeriodDesc("May/June "+currentYear);
			examTimeTableForm.setExamPeriod("6-"+currentYear);
		} else if (currentMonth == 7 || currentMonth == 8 || currentMonth == 9 || currentMonth == 10 || currentMonth == 11 || currentMonth == 12 ){
			examTimeTableForm.setExamPeriodDesc("October/November "+currentYear);
			examTimeTableForm.setExamPeriod("10-"+currentYear);
		}
		request.setAttribute("periods",periods);
		examTimeTableForm.setPeriods(periods);
		examTimeTableForm.setListDate(listDate);
		return mapping.findForward("subjectinputforward");
	}

	public ActionForward showExamTimetable(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
			ExamTimeTableForm examTimeTableForm = (ExamTimeTableForm) form;
			ExamTimetableDAO examTimetabledao = new ExamTimetableDAO();
			ActionMessages messages = new ActionMessages();
			String subjCodes = "";
			String status = "";
			String[] periodStr;
			Integer currentYear;
			Integer previousYear;
			Integer nextYear;
			Calendar curDate1 = Calendar.getInstance();
			previousYear = new Integer(curDate1.get(Calendar.YEAR)).intValue()-1;
			currentYear = new Integer(curDate1.get(Calendar.YEAR));
			nextYear = new Integer(curDate1.get(Calendar.YEAR)).intValue()+1;
			periodStr = examTimeTableForm.getExamPeriod().split("-");
			if (examTimeTableForm.getExamPeriod().equalsIgnoreCase("10-"+currentYear)){
				examTimeTableForm.setExamPeriodDesc("October/November "+currentYear);
				examTimeTableForm.setExamPeriod("10-"+currentYear);
			} else if(examTimeTableForm.getExamPeriod().equalsIgnoreCase("1-"+currentYear)){
				examTimeTableForm.setExamPeriodDesc("January/February "+currentYear);
				examTimeTableForm.setExamPeriod("1-"+currentYear);
			} else if (examTimeTableForm.getExamPeriod().equalsIgnoreCase("6-"+currentYear)){
				examTimeTableForm.setExamPeriodDesc("May/June "+currentYear);
				examTimeTableForm.setExamPeriod("6-"+currentYear);
			} else if  (examTimeTableForm.getExamPeriod().equalsIgnoreCase("10-"+previousYear)){
				examTimeTableForm.setExamPeriodDesc("October/November "+previousYear);
				examTimeTableForm.setExamPeriod("10-"+previousYear);
			} else if(examTimeTableForm.getExamPeriod().equalsIgnoreCase("1-"+previousYear)){
				examTimeTableForm.setExamPeriodDesc("January/February "+previousYear);
				examTimeTableForm.setExamPeriod("1-"+previousYear);
			} else if (examTimeTableForm.getExamPeriod().equalsIgnoreCase("6-"+previousYear)){
				examTimeTableForm.setExamPeriodDesc("May/June "+previousYear);
				examTimeTableForm.setExamPeriod("6-"+previousYear);
			} else if  (examTimeTableForm.getExamPeriod().equalsIgnoreCase("10-"+nextYear)){
				examTimeTableForm.setExamPeriodDesc("October/November "+nextYear);
				examTimeTableForm.setExamPeriod("10-"+nextYear);
			} else if(examTimeTableForm.getExamPeriod().equalsIgnoreCase("1-"+nextYear)){
				examTimeTableForm.setExamPeriodDesc("January/February "+nextYear);
				examTimeTableForm.setExamPeriod("1-"+nextYear);
			} else if (examTimeTableForm.getExamPeriod().equalsIgnoreCase("6-"+nextYear)){
				examTimeTableForm.setExamPeriodDesc("May/June "+nextYear);
				examTimeTableForm.setExamPeriod("6-"+nextYear);
			}
			try {
				status = examTimetabledao.getExamStatus(new Integer(periodStr[1]),new Integer(periodStr[0]));
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (status.equalsIgnoreCase("Y")){
				examTimeTableForm.setTimeTableStatus("Final Examination Timetable");
			} else {
				examTimeTableForm.setTimeTableStatus("Provisional Examination Timetable");
			}
			boolean invalidCodeFound = false;
			if( examTimeTableForm.getUnitCode1().equalsIgnoreCase("") && examTimeTableForm.getUnitCode2().equalsIgnoreCase("") &&
				examTimeTableForm.getUnitCode3().equalsIgnoreCase("") && examTimeTableForm.getUnitCode4().equalsIgnoreCase("") &&
				examTimeTableForm.getUnitCode5().equalsIgnoreCase("")){
				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message","Please enter a study unit code."));
				addErrors(request, messages);
				return inputSubject(mapping,form,request,response);

			}

			if(!examTimeTableForm.getUnitCode1().equalsIgnoreCase("")){
				if (examTimeTableForm.getUnitCode1().length() > 5){
					subjCodes += examTimeTableForm.getUnitCode1()+",";
				} else {
					messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message","The Study Unit Code must be  atleast be 5 characters, correct or delete "+examTimeTableForm.getUnitCode1().toUpperCase()+"."));
				}
			}
			if(!examTimeTableForm.getUnitCode2().equalsIgnoreCase("")){
				if (examTimeTableForm.getUnitCode2().length() > 5){
					subjCodes += examTimeTableForm.getUnitCode2()+",";
				} else {
					messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message","The Study Unit Code must be  atleast be 5 characters, correct or delete "+examTimeTableForm.getUnitCode2().toUpperCase()+"."));
				}
			}
			if(!examTimeTableForm.getUnitCode3().equalsIgnoreCase("")){
				if (examTimeTableForm.getUnitCode3().length() > 5){
					subjCodes += examTimeTableForm.getUnitCode3()+",";
				} else {
					messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message","The Study Unit Code must be  atleast be 5 characters, correct or delete "+examTimeTableForm.getUnitCode3().toUpperCase()+"."));
				}
			}
			if(!examTimeTableForm.getUnitCode4().equalsIgnoreCase("")){
				if (examTimeTableForm.getUnitCode4().length() > 5){
					subjCodes += examTimeTableForm.getUnitCode4()+",";
				} else {
					messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message","The Study Unit Code must be  atleast be 5 characters, correct or delete "+examTimeTableForm.getUnitCode4().toUpperCase()+"."));
				}
			}
			if(!examTimeTableForm.getUnitCode5().equalsIgnoreCase("")){
				if (examTimeTableForm.getUnitCode5().length() > 5){
					subjCodes += examTimeTableForm.getUnitCode5()+",";
				} else {
					messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message","The Study Unit Code must be  atleast be 5 characters, correct or delete "+examTimeTableForm.getUnitCode5().toUpperCase()+"."));
				}
			}

			if(!messages.isEmpty()){
				addErrors(request,messages);
				return inputSubject(mapping,form,request,response);
			}
			String[] result = subjCodes.split(",");
			for(int i=0 ; i < result.length; i++){
				if(examTimetabledao.isCodeInValid(result[i].toUpperCase())){
					messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message","The code "+result[i].toUpperCase()+" you entered is not valid Unisa study unit code. Please correct the code or delete from your list."));
					invalidCodeFound = true;
				}
			}
			if (invalidCodeFound){
				addErrors(request,messages);
				return inputSubject(mapping,form,request,response);
			} else {
				examTimeTableForm.setTimetableList(
					examTimetabledao.getExamTimetableList(periodStr[1],periodStr[0],subjCodes)
					);
			}
			examTimeTableForm.setPrintable("0");
			request.setAttribute("examTimetableList","examTimetableList.");
			return mapping.findForward("timetableviewforward");
	}

	public ActionForward printExamTimetable(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
			ExamTimeTableForm examTimeTableForm = (ExamTimeTableForm) form;
			ExamTimetableDAO examTimetabledao = new ExamTimetableDAO();
			ActionMessages messages = new ActionMessages();
			String subjCodes = "";
			if( examTimeTableForm.getUnitCode1().equalsIgnoreCase("") && examTimeTableForm.getUnitCode2().equalsIgnoreCase("") &&
				examTimeTableForm.getUnitCode3().equalsIgnoreCase("") && examTimeTableForm.getUnitCode4().equalsIgnoreCase("") &&
				examTimeTableForm.getUnitCode5().equalsIgnoreCase("")){
				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message","Fill in the atleast one Study Unit Code."));
				addErrors(request, messages);
				return mapping.findForward("subjectinputforward");

			}

			if(!examTimeTableForm.getUnitCode1().equalsIgnoreCase("")){
				if (examTimeTableForm.getUnitCode1().length() > 5){
					subjCodes += examTimeTableForm.getUnitCode1()+",";
				} else {
					messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message","The Study Unit Code must be  atleast be 5 characters, correct or delete "+examTimeTableForm.getUnitCode1().toUpperCase()+"."));
				}
			}
			if(!examTimeTableForm.getUnitCode2().equalsIgnoreCase("")){
				if (examTimeTableForm.getUnitCode2().length() > 5){
					subjCodes += examTimeTableForm.getUnitCode2()+",";
				} else {
					messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message","The Study Unit Code must be  atleast be 5 characters, correct or delete "+examTimeTableForm.getUnitCode2().toUpperCase()+"."));
				}
			}
			if(!examTimeTableForm.getUnitCode3().equalsIgnoreCase("")){
				if (examTimeTableForm.getUnitCode3().length() > 5){
					subjCodes += examTimeTableForm.getUnitCode3()+",";
				} else {
					messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message","The Study Unit Code must be  atleast be 5 characters, correct or delete "+examTimeTableForm.getUnitCode3().toUpperCase()+"."));
				}
			}
			if(!examTimeTableForm.getUnitCode4().equalsIgnoreCase("")){
				if (examTimeTableForm.getUnitCode4().length() > 5){
					subjCodes += examTimeTableForm.getUnitCode4()+",";
				} else {
					messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message","The Study Unit Code must be  atleast be 5 characters, correct or delete "+examTimeTableForm.getUnitCode4().toUpperCase()+"."));
				}
			}
			if(!examTimeTableForm.getUnitCode5().equalsIgnoreCase("")){
				if (examTimeTableForm.getUnitCode5().length() > 5){
					subjCodes += examTimeTableForm.getUnitCode5()+",";
				} else {
					messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message","The Study Unit Code must be  atleast be 5 characters, correct or delete "+examTimeTableForm.getUnitCode5().toUpperCase()+"."));
				}
			}

			if(!messages.isEmpty()){
				addErrors(request,messages);
				return inputSubject(mapping,form,request,response);
			}
			examTimeTableForm.setTimetableList(
					examTimetabledao.getExamTimetableList(examTimeTableForm.getAcademicYear(),
														examTimeTableForm.getExamPeriod(),
														subjCodes)
					);
			examTimeTableForm.setPrintable("1");
			request.setAttribute("examTimetableList","examTimetableList");
			return mapping.findForward("timetableviewforward");
	}
	public ActionForward clearForm(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response){
		ExamTimeTableForm examTimeTableForm = (ExamTimeTableForm) form;
		examTimeTableForm.setUnitCode1("");
		examTimeTableForm.setUnitCode2("");
		examTimeTableForm.setUnitCode3("");
		examTimeTableForm.setUnitCode4("");
		examTimeTableForm.setUnitCode5("");
		return inputSubject(mapping,form,request,response);
	}

	public ActionForward unspecified(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response){
			log.info("ExamTimeTableAction: unspecified method call -no value for parameter action in request");
			return mapping.findForward("home");
	}

}

