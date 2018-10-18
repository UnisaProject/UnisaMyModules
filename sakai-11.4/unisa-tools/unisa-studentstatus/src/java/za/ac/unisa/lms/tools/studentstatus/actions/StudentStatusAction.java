package za.ac.unisa.lms.tools.studentstatus.actions;

import java.io.PrintWriter;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.LookupDispatchAction;
import org.apache.struts.util.LabelValueBean;
import org.sakaiproject.component.cover.ServerConfigurationService;

import za.ac.unisa.lms.dao.Gencod;
import za.ac.unisa.lms.dao.StudentSystemGeneralDAO;
import za.ac.unisa.lms.tools.studentstatus.bo.Status;
import za.ac.unisa.lms.tools.studentstatus.dao.StudentStatusDAO;
import za.ac.unisa.lms.tools.studentstatus.forms.ScreeningVenue;
import za.ac.unisa.lms.tools.studentstatus.forms.Student;
import za.ac.unisa.lms.tools.studentstatus.forms.StudentStatusForm;

public class StudentStatusAction extends LookupDispatchAction {
	
    /** Our log (commons). */
	public static Log log = LogFactory.getLog(StudentStatusAction.class);
	
    /** Session attribute to test the state of the session */
    public static final String SESSION_STATE = "sakai.struts.tool.session.state";
    
	// --------------------------------------------------------- Methods


	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		//New actionForwards and pages (Edmund 2014)
						
	    //Previous actionforwards
	    map.put("button.cancel", "cancel");
	    map.put("button.quit", "cancel");
	    map.put("button.backtoapp","cancel");
	    map.put("button.back", "back");
	    map.put("button.exit", "quit");
	    map.put("button.continue", "nextStep");
		map.put("button.next", "nextStep");

		map.put("back", "back");
		map.put("cancel", "cancel");
		map.put("next", "nextStep");
	    map.put("walkthrough", "walkthrough");
	    
	    map.put("applyLogin", "applyLogin");
	    map.put("applyStatus", "applyStatus");
	    map.put("backToOffer", "backToOffer");
	    	    
    
	    return map;
	 }
	
	public void reset(ActionMapping mapping, HttpServletRequest request) throws Exception {

		//log.debug("StudentStatusAction - Reset - Initializing Action Form"); 
		StudentStatusForm stuStatForm =  new StudentStatusForm();
		resetForm(stuStatForm, "StudentStatusAction - Reset");
		
	}
	
	public ActionForward backToOffer(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		StudentStatusForm stuStatForm = (StudentStatusForm) form;

		String serverpath = ServerConfigurationService.getServerUrl();		
		return new ActionForward(serverpath+"/unisa-findtool/default.do?sharedTool=unisa.studentoffer&originatedFrom=unisa.studentstatus&acaYear=" + stuStatForm.getStudent().getAcademicYear() +
				  "&acaPeriod=" + stuStatForm.getStudent().getAcademicPeriod() +
		  		  "&nr=" + stuStatForm.getStudent().getNumber() +
				  "&surname=" +  stuStatForm.getStudent().getSurname() +
				  "&firstNames=" + stuStatForm.getStudent().getFirstnames() +
				  "&birthDay=" + stuStatForm.getStudent().getBirthDay() +
				  "&birthMonth=" + stuStatForm.getStudent().getBirthMonth() +
				  "&birthYear=" + stuStatForm.getStudent().getBirthYear(),true);
	}
	
	public ActionForward walkthrough(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		//log.debug("StudentStatusAction - IN walkthrough");
	    
		ActionMessages messages = new ActionMessages();
		StudentStatusForm stuStatForm = (StudentStatusForm) form;
		//StudentStatusForm stuStatForm =  new StudentStatusForm();
		
		resetForm(stuStatForm, "StudentStatusAction - WalkThrough");
		stuStatForm.setSelectReset("");
		stuStatForm.setWebLoginMsg("");
		
		String originatedFrom =  request.getParameter("originatedFrom");
		stuStatForm.setOriginatedFrom(originatedFrom);
		if (originatedFrom != null)
		{
			stuStatForm.getStudent().setAcademicYear(request.getParameter("acaYear"));
			stuStatForm.getStudent().setAcademicPeriod(request.getParameter("acaPeriod"));
			stuStatForm.getStudent().setNumber(request.getParameter("nr"));
			stuStatForm.getStudent().setSurname(request.getParameter("surname"));
			stuStatForm.getStudent().setFirstnames(request.getParameter("firstNames"));
			stuStatForm.getStudent().setBirthYear(request.getParameter("birthYear"));
			stuStatForm.getStudent().setBirthMonth(request.getParameter("birthMonth"));
			stuStatForm.getStudent().setBirthDay(request.getParameter("birthDay"));
			stuStatForm.getStudent().setStuExist(true);
			
			applyStatus(request, stuStatForm);
			return mapping.findForward("applyStatus");
		}
				
		//Write version number to log to check all servers
		//log.debug("StudentStatusAction - Applications Version="+stuStatForm.getVersion());
		
		//log.debug("StudentStatusAction - walkthrough - sessionID=" + request.getSession().getId());

		
		if (stuStatForm.getStudent().getAcademicYear() == null){
			stuStatForm.getStudent().setAcademicYear(getCurrentAcademicYear());
			stuStatForm.getStudent().setAcademicPeriod(getCurrentAcademicPeriod());
		}
			
		StudentStatusDAO dao = new StudentStatusDAO();
		if (stuStatForm.getStudent().getAcademicYear() == null){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", "Academic year invalid, please log on again to retry."));
			addErrors(request, messages);
			return mapping.findForward("loginSelect");
		}
		//log.debug("StudentStatusAction - walkthrough - dateCheck");

		ArrayList<String> dateCheck = dao.validateClosingDate(stuStatForm.getStudent().getAcademicYear());
		if (!dateCheck.isEmpty()){ //Check Dates Array
			for (int i=0; i < dateCheck.size(); i++){
				//log.debug("StudentStatusAction - walkthrough - dateCheck="+dateCheck.get(i).toString());
				if ("WAPSTAT".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuStatForm.getStudent().setDateWAPSTAT(true);
				}
			}
		}
		if (stuStatForm.getStudent().isDateWAPSTAT()){
			stuStatForm.setAllowLogin(true);
		}else{
			stuStatForm.setAllowLogin(false);
		}
		//log.debug("StudentStatusAction - walkthrough - AcademicYear="+stuStatForm.getStudent().getAcademicYear());
					
		stuStatForm.getStudent().setNumber("");
		stuStatForm.getStudent().setSurname("");
		stuStatForm.getStudent().setFirstnames("");
		stuStatForm.getStudent().setBirthYear("");
		stuStatForm.getStudent().setBirthMonth("");
		stuStatForm.getStudent().setBirthDay("");
		
		setDropdownListsLogin(request,stuStatForm);
		//log.debug("StudentStatusAction - walkthrough - Return to applyLogin");
		return mapping.findForward("applyLogin");
	}

	public ActionForward applyLogin(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,	HttpServletResponse response)
			throws Exception {

		//log.debug("StudentStatusAction - applyLogin - IN applyLogin");
	    
		ActionMessages messages = new ActionMessages();
		StudentStatusForm stuStatForm = (StudentStatusForm) form;
		GeneralMethods gen = new GeneralMethods();
		StudentStatusDAO dao = new StudentStatusDAO();
		
		//log.debug("StudentStatusAction applyLogin " + request.getSession().getId() + " N " + stuStatForm.getStudent().getNumber() + " S " + stuStatForm.getStudent().getSurname() + " F " + stuStatForm.getStudent().getSurname() + " Y " + stuStatForm.getStudent().getBirthYear() + " M " + stuStatForm.getStudent().getBirthMonth() + " D " + stuStatForm.getStudent().getBirthDay());

		//log.debug("StudentStatusAction applyLogin - AcademicYear=" + stuStatForm.getStudent().getAcademicYear());
		if (stuStatForm.getStudent().getAcademicYear() == null){
			stuStatForm.getStudent().setAcademicYear(getCurrentAcademicYear());
			stuStatForm.getStudent().setAcademicPeriod(getCurrentAcademicPeriod());
		}
		
		stuStatForm.setApplyType("F");
		stuStatForm.setFromPage("stepLoginReturn");
		
		
		stuStatForm.getStudent().setNumber(stripXSS(stuStatForm.getStudent().getNumber()));
		if (stuStatForm.getStudent().getNumber() == null || "".equalsIgnoreCase(stuStatForm.getStudent().getNumber())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Enter student number."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuStatForm);
			//log.debug("StudentStatusAction - applyLogin - Input Required - Return to applyLogin");
			return mapping.findForward("applyLogin");
		}
		if (!isValidNumber(stuStatForm.getStudent().getNumber())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Enter a valid student number."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuStatForm);
			//log.debug("StudentStatusAction - applyLogin - Input Required - Return to applyLogin");
			return mapping.findForward("applyLogin");
		}
		/** Check the code against the flow diagram by the numbers provided **/
		/** Flow Check: (1) **/
		// We already check for 7 series numbers in JSP and SQL, but double-check if Student number = 8 characters and if 7 student, don't let them in...
		// Student should never get this far with a 7 series number, but we block him anyway.
		if (stuStatForm.getStudent().getNumber().length() == 8 ){ 
			//log.debug("check if Student number = 8 characters");
			if ("7".equalsIgnoreCase(stuStatForm.getStudent().getNumber().substring(0,1))){
				//log.debug("Student number = 8 characters and starts with 7 - Set Message");
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "You must apply for a formal Unisa student number. The number you have entered is reserved for non-formal studies only. Send an email to the Applications office if this information is incorrect or if you now intend applying for formal studies at <a href='mailto:applications@unisa.ac.za'>applications@unisa.ac.za</a>"));
				addErrors(request, messages);
				setDropdownListsLogin(request,stuStatForm);
				//log.debug("StudentStatusAction - applyLogin - Input Required - Return to applyLogin");
				return mapping.findForward("applyLogin");
			}
		}else if (stuStatForm.getStudent().getNumber().length() == 7 ){
			// If student no is only 7 characters (old), then add a 0 to the beginning
			//log.debug("StudentStatusAction - applyLogin - 7 char Student Number - Old Number: " + stuStatForm.getStudent().getNumber());
			stuStatForm.getStudent().setNumber("0" + stuStatForm.getStudent().getNumber());
			//log.debug("StudentStatusAction - applyLogin - 7 char Student Number - New Number: " + stuStatForm.getStudent().getNumber());
		}
		//log.debug("StudentStatusAction - applyLogin - After check if Student number = 8 characters");

		stuStatForm.getStudent().setSurname(stripXSS(stuStatForm.getStudent().getSurname()));
		if (stuStatForm.getStudent().getSurname() == null || "".equalsIgnoreCase(stuStatForm.getStudent().getSurname())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Enter student surname."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuStatForm);
			//log.debug("StudentStatusAction - applyLogin - Input Required - Return to applyLogin");
			return mapping.findForward("applyLogin");
		}
		if (!isNameValid(stuStatForm.getStudent().getSurname())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Enter a valid student surname."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuStatForm);
			//log.debug("StudentStatusAction - applyLogin - Input Required - Return to applyLogin");
			return mapping.findForward("applyLogin");
		}

		if (stuStatForm.getStudent().getSurname() == null || "".equalsIgnoreCase(stuStatForm.getStudent().getSurname())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Enter student first names."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuStatForm);
			//log.debug("StudentStatusAction - applyLogin - Input Required - Return to applyLogin");
			return mapping.findForward("applyLogin");
		}
		stuStatForm.getStudent().setSurname(stripXSS(stuStatForm.getStudent().getSurname()));
		if (!isNameValid(stuStatForm.getStudent().getSurname())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Enter a valid student first names."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuStatForm);
			//log.debug("StudentStatusAction - applyLogin - Input Required - Return to applyLogin");
			return mapping.findForward("applyLogin");
		}
		stuStatForm.getStudent().setBirthYear(stripXSS(stuStatForm.getStudent().getBirthYear()));
		stuStatForm.getStudent().setBirthYear(stuStatForm.getStudent().getBirthYear().trim());
		if (stuStatForm.getStudent().getBirthYear() == null){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Year) is blank - must be numeric format [CCYY]."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuStatForm);
			//log.debug("StudentStatusAction - applyLogin - Input Required - Return to applyLogin");
			return mapping.findForward("applyLogin");
		}
		if ("".equals(stuStatForm.getStudent().getBirthYear().trim())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Year) is empty - must be numeric format [CCYY]."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuStatForm);
			//log.debug("StudentStatusAction - applyLogin - Input Required - Return to applyLogin");
			return mapping.findForward("applyLogin");
		}
		if (!gen.isNumeric(stuStatForm.getStudent().getBirthYear())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Year) must be numeric format [CCYY]."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuStatForm);
			//log.debug("StudentStatusAction - applyLogin - Input Required - Return to applyLogin");
			return mapping.findForward("applyLogin");
		}
		if (Integer.parseInt(stuStatForm.getStudent().getBirthYear()) <= 1900 || Integer.parseInt(stuStatForm.getStudent().getBirthYear()) >= Integer.parseInt(stuStatForm.getStudent().getAcademicYear())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Year) invalid."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuStatForm);
			//log.debug("StudentStatusAction - applyLogin - Input Required - Return to applyLogin");
			return mapping.findForward("applyLogin");
		}
		if (stuStatForm.getStudent().getBirthYear().length() > 4){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Year) invalid. Please enter 4 characters or less. " ));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuStatForm);
			//log.debug("StudentStatusAction - applyLogin - Input Required - Return to applyLogin");
			return mapping.findForward("applyLogin");
		}
		stuStatForm.getStudent().setBirthMonth(stripXSS(stuStatForm.getStudent().getBirthMonth()));
		stuStatForm.getStudent().setBirthMonth(stuStatForm.getStudent().getBirthMonth().trim());
		if (stuStatForm.getStudent().getBirthMonth() == null){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Month) is blank - must be numeric format [MM]."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuStatForm);
			//log.debug("StudentStatusAction - applyLogin - Input Required - Return to applyLogin");
			return mapping.findForward("applyLogin");
		}
		if ("".equals(stuStatForm.getStudent().getBirthMonth())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Month) is empty must be numeric format [MM]."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuStatForm);
			//log.debug("StudentStatusAction - applyLogin - Input Required - Return to applyLogin");
			return mapping.findForward("applyLogin");
		}
		if (!gen.isNumeric(stuStatForm.getStudent().getBirthMonth())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Month) must be numeric format [MM]."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuStatForm);
			//log.debug("StudentStatusAction - applyLogin - Input Required - Return to applyLogin");
			return mapping.findForward("applyLogin");
		}
		if (Integer.parseInt(stuStatForm.getStudent().getBirthMonth()) < 1 || Integer.parseInt(stuStatForm.getStudent().getBirthMonth()) > 12){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Month) invalid."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuStatForm);
			//log.debug("StudentStatusAction - applyLogin - Input Required - Return to applyLogin");
			return mapping.findForward("applyLogin");
		}
		if (stuStatForm.getStudent().getBirthMonth().length() > 2){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Month) invalid. Please enter 2 characters or less. " ));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuStatForm);
			//log.debug("StudentStatusAction - applyLogin - Input Required - Return to applyLogin");
			return mapping.findForward("applyLogin");
		}

		//2015 Edmund - Expanded Date Tests as Students entered dates like 30/02/1992 etc
		int testMonth = Integer.parseInt(stuStatForm.getStudent().getBirthMonth());
		int daysMonth = 0;
		if (testMonth == 1 || testMonth == 3 || testMonth == 5 || testMonth == 7 || testMonth == 8 || testMonth == 10 || testMonth ==12){
			daysMonth = 31;
		}else if (testMonth == 4 || testMonth == 6 || testMonth == 9 || testMonth == 11){
			daysMonth = 30;
		}else{
			daysMonth = 28; //Checking for Leap year further below...
		}
		
		stuStatForm.getStudent().setBirthDay(stripXSS(stuStatForm.getStudent().getBirthDay()));
		stuStatForm.getStudent().setBirthDay(stuStatForm.getStudent().getBirthDay().trim());
		if (stuStatForm.getStudent().getBirthDay() == null){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Day) is blank - must be numeric format [DD]."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuStatForm);
			//log.debug("StudentStatusAction - applyLogin - Input Required - Return to applyLogin");
			return mapping.findForward("applyLogin");
		}
		if ("".equals(stuStatForm.getStudent().getBirthDay())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Day) is empty - must be numeric format [DD]."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuStatForm);
			//log.debug("StudentStatusAction - applyLogin - Input Required - Return to applyLogin");
			return mapping.findForward("applyLogin");
		}
		if (!gen.isNumeric(stuStatForm.getStudent().getBirthDay())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Day) must be numeric format [DD]."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuStatForm);
			//log.debug("StudentStatusAction - applyLogin - Input Required - Return to applyLogin");
			return mapping.findForward("applyLogin");
		}
		if (Integer.parseInt(stuStatForm.getStudent().getBirthDay()) < 1 || Integer.parseInt(stuStatForm.getStudent().getBirthDay()) > 31){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Day) invalid."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuStatForm);
			//log.debug("StudentStatusAction - applyLogin - Input Required - Return to applyLogin");
			return mapping.findForward("applyLogin");
		}
		if (stuStatForm.getStudent().getBirthDay().length() > 2){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Day) invalid. Please enter 2 characters or less. " ));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuStatForm);
			//log.debug("StudentStatusAction - applyLogin - Input Required - Return to applyLogin");
			return mapping.findForward("applyLogin");
		}
		if (Integer.parseInt(stuStatForm.getStudent().getBirthDay()) < 1){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Day) invalid."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuStatForm);
			//log.debug("StudentStatusAction - applyLogin - Input Required - Return to applyLogin");
			return mapping.findForward("applyLogin");
		}
		String monthName = "";
		if (daysMonth == 31 && Integer.parseInt(stuStatForm.getStudent().getBirthDay()) > 31){
			monthName = getMonth(testMonth);
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Day) invalid. " + monthName + " only has 31 days."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuStatForm);
			//log.debug("StudentStatusAction - applyLogin - Input Required - Return to applyLogin");
			return mapping.findForward("applyLogin");
		}
		if (daysMonth == 30 && Integer.parseInt(stuStatForm.getStudent().getBirthDay()) > 30){
			monthName = getMonth(testMonth);
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Day) invalid. " + monthName + " only has 30 days."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuStatForm);
			//log.debug("StudentStatusAction - applyLogin - Input Required - Return to applyLogin");
			return mapping.findForward("applyLogin");
		}
		if (Integer.parseInt(stuStatForm.getStudent().getBirthMonth()) == 2 && Integer.parseInt(stuStatForm.getStudent().getBirthDay()) > 29){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", "Student Date of Birth invalid. " ));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuStatForm);
			//log.debug("StudentStatusAction - applyLogin - Input Required - Return to applyLogin");
			return mapping.findForward("applyLogin");
		}
		if (Integer.parseInt(stuStatForm.getStudent().getBirthMonth()) == 2 && Integer.parseInt(stuStatForm.getStudent().getBirthDay()) == 29){
			String leapCheck = dao.checkLeapYear(stuStatForm.getStudent().getBirthYear());
			if ("Not a leap year".equalsIgnoreCase(leapCheck)){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (29/February/"+stuStatForm.getStudent().getBirthYear()+") invalid. " + stuStatForm.getStudent().getBirthYear() + " was not a leap year." ));
				addErrors(request, messages);
				setDropdownListsLogin(request,stuStatForm);
				//log.debug("StudentStatusAction - applyLogin - Input Required - Return to applyLogin");
				return mapping.findForward("applyLogin");
			}
		}
		
		//Add "0" incase Student enters just one character for Birthday or Month
		if (stuStatForm.getStudent().getBirthMonth().length() < 2){
			stuStatForm.getStudent().setBirthMonth("0" + stuStatForm.getStudent().getBirthMonth());
		}
		if (stuStatForm.getStudent().getBirthDay().length() < 2){
			stuStatForm.getStudent().setBirthDay("0" + stuStatForm.getStudent().getBirthDay());
		}
		
		String errorMsg="";
		errorMsg = displayPersonal(stuStatForm, request);

		if(!"".equals(errorMsg)){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", errorMsg));
				addErrors(request, messages);
				setDropdownListsLogin(request,stuStatForm);
				//log.debug("StudentStatusAction - applyLogin - DisplayPersonal - Return to applyLogin");
				return mapping.findForward("applyLogin");
		}else{
			stuStatForm.getStudent().setNumber(stuStatForm.getStudent().getNumber());
			stuStatForm.getStudent().setSurname(stuStatForm.getStudent().getSurname().toUpperCase());
			stuStatForm.getStudent().setFirstnames(stuStatForm.getStudent().getFirstnames().toUpperCase());
			stuStatForm.getStudent().setBirthYear(stuStatForm.getStudent().getBirthYear());
			stuStatForm.getStudent().setBirthMonth(stuStatForm.getStudent().getBirthMonth());
			stuStatForm.getStudent().setBirthDay(stuStatForm.getStudent().getBirthDay());
			
			//log.debug("StudentStatusAction - applyLogin - Write student details to STUXML");
			//Write student details to STUXML for later verification - Testing Thread security
			String referenceData = stuStatForm.getStudent().getNumber()+","+stuStatForm.getStudent().getSurname().toUpperCase()+","+stuStatForm.getStudent().getFirstnames().toUpperCase()+","+stuStatForm.getStudent().getBirthYear()+stuStatForm.getStudent().getBirthMonth()+stuStatForm.getStudent().getBirthDay();
			boolean stuError = false;
			String queryResultRef = "";
			String checkStu = "";
			int nextRef = 1;
			queryResultRef = dao.getSTUXMLRef(stuStatForm.getStudent().getNumber(), stuStatForm.getStudent().getAcademicYear(), stuStatForm.getStudent().getAcademicPeriod(), "StuInfo", "1", "applyLogin");
			//log.debug("StudentStatusAction - applyLogin - queryResultRef="+queryResultRef);
			if (queryResultRef.toUpperCase().contains("ERROR")){
				stuError = true;
			}
			if (!stuError){
				try{
					nextRef = Integer.parseInt(queryResultRef);
					nextRef++;
				}catch(Exception e){
					log.warn("StudentStatusAction - applyLogin - nextRef not Numeric - nextRef="+nextRef);
				}
				//log.debug("StudentStatusAction - applyLogin - queryResultRef="+queryResultRef+", nextRef="+nextRef);
				checkStu = dao.saveSTUXMLRef(stuStatForm.getStudent().getNumber(), stuStatForm.getStudent().getAcademicYear(), stuStatForm.getStudent().getAcademicPeriod(), "StuInfo", nextRef, referenceData, "applyLogin", "INSERT");
				if (checkStu.toUpperCase().contains("ERROR")){
					stuError = true;
				}
			}
			//log.debug("StudentStatusAction - applyLogin - Write student details to STUXML - Done - checkStu="+checkStu+", referenceData="+referenceData+", referenceSequence="+nextRef);
			
			//Get Browser Details
			//Is client behind something like a Proxy Server?
			String ipAddress = request.getHeader("X-FORWARDED-FOR");
			if (ipAddress == null) {
				   ipAddress = request.getRemoteAddr();
			}
			stuStatForm.getStudent().setStuIPAddress(ipAddress);
			stuStatForm.getStudent().setStuBrowserAgent(request.getHeader("User-Agent"));
			String browser = getBrowserInfo(stuStatForm.getStudent().getStuBrowserAgent());
			if (browser != null && !"".equals(browser.trim()) && browser.contains("@") && browser.length() > 3){
				int pos = browser.indexOf("@");
				stuStatForm.getStudent().setStuBrowser(browser.substring(0,pos));
				stuStatForm.getStudent().setStuWksOS(browser.substring(pos+1,browser.length()));
				//log.debug("StudentStatusAction - applyLogin - Write Browser details to STUXML");
				//Write Browser details to STUXML for later verification - Testing Thread security
				String browserData = stuStatForm.getStudent().getNumber()+","+stuStatForm.getStudent().getStuIPAddress()+","+stuStatForm.getStudent().getStuBrowser()+","+stuStatForm.getStudent().getStuWksOS();
				//log.debug("StudentStatusAction - applyLogin - browserData="+browserData);
				boolean browserError = false;
				String queryResultBrowser = "";
				String checkBrowser = "";
				int nextBrowser = 1;
				queryResultBrowser = dao.getSTUXMLRef(stuStatForm.getStudent().getNumber(), stuStatForm.getStudent().getAcademicYear(), stuStatForm.getStudent().getAcademicPeriod(), "WKSInfo", "1", "applyLogin");
				//log.debug("StudentStatusAction - applyLogin - queryResultRef="+queryResultBrowser);
				if (queryResultBrowser.toUpperCase().contains("ERROR")){
					browserError = true;
				}
				if (!browserError){
					try{
						nextBrowser = Integer.parseInt(queryResultBrowser);
						nextBrowser++;
					}catch(Exception e){
						log.warn("StudentStatusAction - applyLogin - nextRef not Numeric - nextRef="+nextBrowser);
					}
					//log.debug("StudentStatusAction - applyLogin - queryResultBrowser="+queryResultBrowser+", nextRef="+nextBrowser);
					checkBrowser = dao.saveSTUXMLRef(stuStatForm.getStudent().getNumber(), stuStatForm.getStudent().getAcademicYear(), stuStatForm.getStudent().getAcademicPeriod(), "WKSInfo", nextBrowser, browserData, "applyLogin", "INSERT");
					if (checkBrowser.toUpperCase().contains("ERROR")){
						browserError = true;
					}
				}
				//log.debug("StudentStatusAction - applyLogin - Write student details to STUXML - Done - checkStu="+checkBrowser+", browserData="+browserData+", referenceSequence="+nextBrowser);
			}
			
			/**
			 * Validation Test: Check if student has a academic record
			 **/
			
			/** Continue to check the code against the flow diagram by the numbers provided **/
			/** Flow Check: (1) **/
			//log.debug("StudentStatusAction - applyLogin - (1) - Check STUAPQ (F851)");
			String bDay = stuStatForm.getStudent().getBirthDay() + "/" + stuStatForm.getStudent().getBirthMonth() + "/" + stuStatForm.getStudent().getBirthYear();

			boolean vSTUAPQCheck = dao.validateSTUAPQ(stuStatForm.getStudent().getSurname(), stuStatForm.getStudent().getFirstnames(), bDay, stuStatForm.getStudent().getAcademicYear(), stuStatForm.getStudent().getAcademicPeriod());
			stuStatForm.getStudent().setStuapqExist(vSTUAPQCheck);
				
			//log.debug("StudentStatusAction - applyLogin - (IF) vAcaCheck=True - vRETSTUAPQCheck: " + vSTUAPQCheck + " - setStuExist:curStu - goto - MenuReturnStu");
			if (vSTUAPQCheck){ //Student already applied during this application period (Only allowed one application per application period)
				/** Flow Check: (2) **/
				//log.debug("StudentStatusAction - applyLogin - (2) - Student has applied during this application period");
				/** Flow Check: (3) **/
				//log.debug("StudentStatusAction - applyLogin - (3) - Student selected to go directly to Status page");
				stuStatForm.getStudent().setStuExist(true);
				/** Flow Check: (4) **/
				//log.debug("StudentStatusAction - applyLogin - (4) - Redirect to Status page");
				applyStatus(request, stuStatForm);
				return mapping.findForward("applyStatus");
			} else{ //Student thus doesn't have a STUAPQ record for this Academic Year & Period
				/** Flow Check: (5) **/
				//log.debug("StudentStatusAction - applyLogin - (5) - Student thus doesn't have a STUAPQ record for this Academic Year & Period yet");
				stuStatForm.getStudent().setStuExist(false);
				/** Flow Check: (6) **/
				//log.debug("StudentStatusAction - applyLogin - (6) - Set Message - You do not have a current application record for this academic year.newline Click OK retry or click Cancel if you wish to quit the application process.");
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "You do not have a current application record for this academic year." ));
					addErrors(request, messages);
				/** Flow Check: (7) **/
				//log.debug("StudentStatusAction - applyLogin - (7) - Goto applyStatus");
				setDropdownListsLogin(request,stuStatForm);
				return mapping.findForward("applyLogin");
			}
		}
	}

	public String displayPersonal(StudentStatusForm stuStatForm, HttpServletRequest request) throws Exception {
		
		StudentStatusDAO dao = new StudentStatusDAO();
		
		//log.debug("StudentStatusAction - displayPersonal - Start");
		Student teststu = new Student();
		teststu.setNumber(stuStatForm.getStudent().getNumber().trim());
		teststu.setFirstnames(stuStatForm.getStudent().getFirstnames().trim());
		teststu.setSurname(stuStatForm.getStudent().getSurname().trim());
		teststu.setBirthDay(stuStatForm.getStudent().getBirthDay());
		teststu.setBirthMonth(stuStatForm.getStudent().getBirthMonth());
		teststu.setBirthYear(stuStatForm.getStudent().getBirthYear());

		String result = "";

		String checkNumber = dao.personalDetails(stuStatForm.getStudent().getNumber(),"number").trim();
		if ("error".equalsIgnoreCase(checkNumber)){
			result = "The student number entered is invalid. Please enter a valid number.";
			return result;
		}else{
			stuStatForm.getStudent().setNumber(checkNumber);
			//log.debug("StudentStatusAction - displayPersonal - Student Number: " + stuStatForm.getStudent().getNumber());
		}
		
		String checkSurname = dao.personalDetails(stuStatForm.getStudent().getNumber(),"surname").trim();
		if ("error".equalsIgnoreCase(checkSurname)){
			result = "The surname information was not successfully retrieved from the database";
			return result;
		}else{
			stuStatForm.getStudent().setSurname(checkSurname);
			//log.debug("StudentStatusAction - displayPersonal - Surname: " + stuStatForm.getStudent().getSurname());
		}
		
		String checkFirstnames = dao.personalDetails(stuStatForm.getStudent().getNumber(),"first_names").trim();
		if ("error".equalsIgnoreCase(checkFirstnames)){
			result = "The First name information was not successfully retrieved from the database";
			return result;
		}else{
			stuStatForm.getStudent().setFirstnames(checkFirstnames);
			//log.debug("StudentStatusAction - displayPersonal - First Names: " + stuStatForm.getStudent().getFirstnames());
		}

		String checkMaidenname = dao.personalDetails(stuStatForm.getStudent().getNumber(),"previous_surname").trim();
		if ("error".equalsIgnoreCase(checkMaidenname)){
			result = "The Maiden name information was not successfully retrieved from the database";
			return result;
		}else{
			stuStatForm.getStudent().setMaidenName(checkMaidenname);
			//log.debug("StudentStatusAction - displayPersonal - Prev Surname: " + stuStatForm.getStudent().getMaidenName());
		}
		
		String YYYYMMDD = "";
		String checkBDate = dao.personalDetails(stuStatForm.getStudent().getNumber(),"BIRTH_DATE").trim();
		if ("error".equalsIgnoreCase(checkBDate)){
			result = "The Birth Date information was not successfully retrieved from the database";
			return result;
		}else{
			YYYYMMDD = checkBDate;
			//log.debug("StudentStatusAction - displayPersonal - Birth Date: " + YYYYMMDD);
		}

		//log.debug("StudentStatusAction - displayPersonal - BirthDay: " + stuStatForm.getStudent().getBirthYear() + "/" + stuStatForm.getStudent().getBirthMonth() + "/" + stuStatForm.getStudent().getBirthDay());
		if(YYYYMMDD.length()>=8){
			//log.debug("StudentStatusAction - displayPersonal - " + request.getSession().getId() + " N " + stuStatForm.getStudent().getNumber() + " Success BirthDay " + YYYYMMDD);
			stuStatForm.getStudent().setBirthYear(YYYYMMDD.substring(0,4));
			stuStatForm.getStudent().setBirthMonth(YYYYMMDD.substring(4,6));
			stuStatForm.getStudent().setBirthDay(YYYYMMDD.substring(6,8));
		}else{
			//log.debug("StudentStatusAction - displayPersonal - " + request.getSession().getId() + " N " + stuStatForm.getStudent().getNumber() + " UnSuccess BirthDay " + YYYYMMDD);
			result = "The birth day information was not successfully retrieved from the database";
			return result;
		}
		//log.debug("StudentStatusAction - displayPersonal - BirthDay: " + stuStatForm.getStudent().getBirthYear() + "/" + stuStatForm.getStudent().getBirthMonth() + "/" + stuStatForm.getStudent().getBirthDay());

		String tmpCellNr = dao.personalDetails(stuStatForm.getStudent().getNumber(),"cell_number").trim();
		if (!"error".equalsIgnoreCase(tmpCellNr)){
			stuStatForm.getStudent().setCellNr(tmpCellNr);
		}else{
			stuStatForm.getStudent().setCellNr("");
		}
		//log.debug("StudentStatusAction - displayPersonal - Cell Number: " + stuStatForm.getStudent().getCellNr());

		//log.debug("StudentStatusAction - displayPersonal - " + request.getSession().getId() + " N " + stuStatForm.getStudent().getNumber() + " Cell " + stuStatForm.getStudent().getCellNr());
		
		String tmpEmail = dao.personalDetails(stuStatForm.getStudent().getNumber(),"email_address").trim();
		if (!"error".equalsIgnoreCase(tmpEmail)){
			stuStatForm.getStudent().setEmailAddress(tmpEmail);
		}else{
			stuStatForm.getStudent().setEmailAddress("");
		}
		//log.debug("StudentStatusAction - displayPersonal - Email Address: " + stuStatForm.getStudent().getEmailAddress());

		//log.debug("StudentStatusAction - displayPersonal - " + request.getSession().getId() + " N " + stuStatForm.getStudent().getNumber() + " Email " + stuStatForm.getStudent().getEmailAddress());
		
		// Closing date  --Integer.parseInt(String.valueOf(stuStatForm.getStudent().getAcademicYear())))){

		//Validate if screen input is correct student
		if (!stuStatForm.getStudent().getSurname().equalsIgnoreCase(teststu.getSurname())){
			result = "The surname you have entered does not correspond with the information on the database";
		}else if (!stuStatForm.getStudent().getFirstnames().equalsIgnoreCase(teststu.getFirstnames())){
			result= "The first name(s) you have entered does not correspond with the information on the database";
		}else if (!stuStatForm.getStudent().getBirthYear().equals(teststu.getBirthYear())){
			result="The birthdate you have entered does not correspond with the information on the database";
		}else if (!stuStatForm.getStudent().getBirthMonth().equals(teststu.getBirthMonth())){
			result="The birthdate you have entered does not correspond with the information on the database";
		}else if (!stuStatForm.getStudent().getBirthDay().equals(teststu.getBirthDay())){
			result="The birthdate you have entered does not correspond with the information on the database";
		}else if (!dao.validateClosingDateAll(stuStatForm.getStudent().getAcademicYear())){
			if ("AllClosed".equalsIgnoreCase(stuStatForm.getStudent().getWebOpenDate())){
				result="The application period for study at Unisa is closed.";
			}
		}

		//if (!"".equals(result)){
			//error result - move input back to screen values
			stuStatForm.getStudent().setNumber(teststu.getNumber());
			stuStatForm.getStudent().setSurname(teststu.getSurname());
			stuStatForm.getStudent().setFirstnames(teststu.getFirstnames());
			stuStatForm.getStudent().setBirthDay(teststu.getBirthDay());
			stuStatForm.getStudent().setBirthMonth(teststu.getBirthMonth());
			stuStatForm.getStudent().setBirthYear(teststu.getBirthYear());
		//}
		//if any error message =result
		//log.debug("StudentStatusAction - displayPersonal - Result: " + result);
		return result;
	}
	
	/*
	 * populates the Year dropdown. Uses JSON to return Key and Value list
	 */
	public ActionForward populateYear(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		//log.debug("StudentStatusAction - populateYear - Start");

		JSONObject yearObj = new JSONObject();
		Map<String, String> mapYear = new LinkedHashMap<String, String>();
		int keyCounter = 0;
		
		//log.debug("StudentStatusAction - populateYear - **************************************************************");

		try{
			int startYear = 1960; //Don't think student can be more than 100 years old
			
			//Ensure that End year is later than Start year
			String startYearSelected = stripXSS(request.getParameter("startYearSelected"));
			//log.debug("StudentStatusAction - populateYear - startYearSelected="+startYearSelected);
	
			if (startYearSelected != null && !"".equals(startYearSelected) && !"0".equals(startYearSelected) && Integer.parseInt(startYearSelected) > 0){
				startYear = Integer.parseInt(startYearSelected);
			}
	
			Calendar cal = Calendar.getInstance();
			int endYear = cal.get(Calendar.YEAR);
			//endYear = endYear - 1; Removed this according to Elena and Claudette for all Post-Graduates etc.
			for (int i = endYear; i >= startYear; i--){
	    		mapYear.put(Integer.toString(keyCounter), Integer.toString(i)+"~"+Integer.toString(i));
	    		keyCounter++;
			}
	    	yearObj.put("YEAR",mapYear);
		}catch(Exception ex){
			yearObj.put("Error","The populateYear retrieval Failed! Please try again.");
		}
		
		// Convert the map to JSON
		PrintWriter out = response.getWriter();
       	JSONObject jsonObject = JSONObject.fromObject(yearObj);
       	//log.debug("StudentStatusAction - populateYear - Final - **************************************************************");
       	//log.debug("StudentStatusAction - populateYear - Final - jsonObject="+jsonObject.toString());
       	//log.debug("StudentStatusAction - populateYear - Final - **************************************************************");
       	out.write(jsonObject.toString());
       	out.flush();

		return null; //Returning null to prevent struts from interfering with Ajax/JSON
	}
	
	
	public String doSTUXML(String StudentNr, String acaYear, String acaPeriod, String referenceType, String referenceValue, String referenceData, String callingMethod){
		
		String result = "";
		String dbMethod = "INSERT";
		boolean isError = false;
		boolean isSuccess = false;

		StudentStatusDAO dao = new StudentStatusDAO();
		
		result = dao.isSTUXML(StudentNr, acaYear, acaPeriod, referenceType, referenceValue, callingMethod);
		if (result.toUpperCase().contains("ERROR")){
			isError = true;
		}else if (result.toUpperCase().equalsIgnoreCase("TRUE")){
			dbMethod = "UPDATE";
		}
		//log.debug("StudentStatusAction - doSTUXML - " + StudentNr +" - STUXML - queryResult="+result + ", queryType="+dbMethod+ ", isError="+isError+ ", callingMethod="+callingMethod);
		
		int count = 0;
		while (!isSuccess && count < 5 && !isError){
			int nextRef = 0;
			String queryResultRef = dao.getSTUXMLRef(StudentNr, acaYear, acaPeriod, referenceType, referenceValue, "doSTUXML");
			//log.debug("StudentStatusAction doSTUXML - queryResultRef="+queryResultRef);
			if (queryResultRef.toUpperCase().contains("ERROR")){
				isError = true;
			}
			if (!isError){
				try{
					nextRef = Integer.parseInt(queryResultRef);
					nextRef++;
					//log.debug("StudentStatusAction - doSTUXML - queryResultRef="+queryResultRef+", nextRef="+nextRef+", referenceType="+referenceType+", referenceValue="+referenceValue+". referenceData="+referenceData);
					result = dao.saveSTUXML(StudentNr, acaYear, acaPeriod, referenceType, referenceValue, String.valueOf(nextRef), referenceData, callingMethod, dbMethod);
					if (result.toUpperCase().equalsIgnoreCase("TRUE")){
						isSuccess = true;
					}else if (result.toUpperCase().contains("ERROR")){
						isError = true;
					}
				}catch(Exception e){
					log.warn("StudentStatusAction doSTUXML - nextRef not Numeric - nextRef="+nextRef);
				}
			}
			count++;
			//log.debug("StudentStatusAction - doSTUXML - " + StudentNr +" - STUXML - "+dbMethod+ "Result="+result + ", count="+count+ ", isError="+isError+ ", callingMethod="+callingMethod);
		}
		return result;
	}

	public void submitMD(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String path = "/unisa-findtool/default.do?sharedTool=unisa.mdapplications";
		response.sendRedirect(path);
		return;
	}

	public ActionForward nextStep(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// if student number is null, block application
		ActionMessages messages = new ActionMessages();
		StudentStatusForm stuStatForm = (StudentStatusForm) form;

		String nextPage = "";
		String page = stripXSS(request.getParameter("page"));
		//log.debug("StudentStatusAction Nextpage - page="+page+", nextPage=" + nextPage);
		

		if(stuStatForm.getStudent().getNumber() == null){
			//log.debug("StudentStatusAction Nextpage " + request.getSession().getId() + " Student Nr is null or empty when saving qualifications");
			messages.add(ActionMessages.GLOBAL_MESSAGE,
			new ActionMessage("message.generalmessage", "You performed and invalid action or an Error occurred. Please log on again to retry."));
			addErrors(request, messages);
			//log.debug("StudentStatusAction - Nextpage - Return to applyLogin");
			setDropdownListsLogin(request,stuStatForm);
			return mapping.findForward("applyLogin");
		}

		if (stuStatForm.getApplyType()==null || "".equals(stuStatForm.getApplyType())){
			stuStatForm.setApplyType("F");
		}
		
		if ("applyLogin".equalsIgnoreCase(page)){
			setDropdownListsLogin(request,stuStatForm);
			return mapping.findForward("applyLogin");
		}
		//log.debug("StudentStatusAction Nextpage - Nextpage=" + nextPage);
		return mapping.findForward(nextPage);
	}

	private void setDropdownListsLogin(HttpServletRequest request, ActionForm form) throws Exception{
		
		StudentStatusForm stuStatForm = (StudentStatusForm) form;
		
		//log.debug("StudentStatusAction - setDropdownListLogin - AcademicYear="+stuStatForm.getStudent().getAcademicYear());

		//log.debug("StudentStatusAction - setDropdownListsLogin - Start");
		setUpYearList(request);
		setUpMonthList(request);
		setUpDayList(request);
		//log.debug("StudentStatusAction - setDropdownListsLogin - End");
	}
	

	private void setUpYearList(HttpServletRequest request) throws Exception{

		ArrayList<LabelValueBean> list = new ArrayList<LabelValueBean>();
		int startYear = 1920; //Don't think student can be more than 100 years old

		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int count = 1;
		list.add(0,new org.apache.struts.util.LabelValueBean("YEAR","00"));
		for (int i = year; i >= startYear; i--){
			list.add(count,new org.apache.struts.util.LabelValueBean(Integer.toString(i),Integer.toString(i)));
			count++;
		}
		request.setAttribute("yearlist",list);
	}
	
	private void setUpMonthList(HttpServletRequest request) throws Exception{

		ArrayList<LabelValueBean> list = new ArrayList<LabelValueBean>();
		
		list.add(0,new org.apache.struts.util.LabelValueBean("MON","00"));
		list.add(1,new org.apache.struts.util.LabelValueBean("JAN","01"));
		list.add(2,new org.apache.struts.util.LabelValueBean("FEB","02"));
		list.add(3,new org.apache.struts.util.LabelValueBean("MAR","03"));
		list.add(4,new org.apache.struts.util.LabelValueBean("APR","04"));
		list.add(5,new org.apache.struts.util.LabelValueBean("MAY","05"));
		list.add(6,new org.apache.struts.util.LabelValueBean("JUN","06"));
		list.add(7,new org.apache.struts.util.LabelValueBean("JUL","07"));
		list.add(8,new org.apache.struts.util.LabelValueBean("AUG","08"));
		list.add(9,new org.apache.struts.util.LabelValueBean("SEP","09"));
		list.add(10,new org.apache.struts.util.LabelValueBean("OCT","10"));
		list.add(11,new org.apache.struts.util.LabelValueBean("NOV","11"));
		list.add(12,new org.apache.struts.util.LabelValueBean("DEC","12"));

		request.setAttribute("monthlist",list);
	}
	
	private void setUpDayList(HttpServletRequest request) throws Exception{

		ArrayList<LabelValueBean> list = new ArrayList<LabelValueBean>();
		String day = "0";
		int count = 1;
		list.add(0,new org.apache.struts.util.LabelValueBean("DAY","00"));

		for (int i = 1; i <= 31; i++){
			if (i < 10){
				day = "0"+Integer.toString(i);
			}else{
				day = Integer.toString(i);
			}
			list.add(count,new org.apache.struts.util.LabelValueBean(day,day));
			count++;
		}
		request.setAttribute("daylist",list);
	}

	private boolean isNameValid(String anyname){
		boolean result = true;
		String test = "";
		int y=anyname.length();

		for (int i = 0; i < y-1; i++) {
			test= anyname.substring(i,i+1);
			if (test !=null && !"".equals(test)){
				if ("~".equals(test) || "<".equals(test) || "@".equals(test) || "^".equals(test) || "1".equals(test) || "2".equals(test) || "3".equals(test) || "4".equals(test) || "5".equals(test) || "6".equals(test) || "7".equals(test) || "8".equals(test) || "9".equals(test) || "0".equals(test)){
					result=false;
				}else{
//					//log.debug(test);
				}
			}else{
				result=false;
			}
		}
		return result;
	}

	private boolean isValidNumber(String number){
		boolean result = true;
		String test = number;
		try{
			test = test.replaceAll("-","");
			test = test.replaceAll("\\+","");
			test = test.replaceAll(" ","");
			Integer.parseInt(test);
		} catch (NumberFormatException e) {
			result = false;
		}
		int y=number.length();

		for (int i = 0; i < y; i++) {
			test= number.substring(i,i+1);
			if (test !=null && !"".equals(test)&& !" ".equals(test) && !"-".equals(test) && !"/".equals(test) && !"+".equals(test)){
				if ("0".equals(test) || "1".equals(test) || "2".equals(test) || "3".equals(test) || "4".equals(test) || "5".equals(test) || "6".equals(test) || "7".equals(test) || "8".equals(test)  || "9".equals(test)){
					//log.debug(test);
				}else{
					result=false;
				}
			}else{
				result=false;
			}
		}
		return result;
	}

	@SuppressWarnings("unused")
	private boolean isValidNumberOld(String number){
		boolean result = false;
		String test = number;

		if ( number != null && !"".equals(number)){
			test = test.replaceAll("-","");
			test = test.replaceAll("\\+","");
			try{
				Long.parseLong(test);
				result = true;
			} catch (NumberFormatException e) {
				result = false;
			}
		}else{
			result = true;
		}
		return result;
	}

	public void resetForm(StudentStatusForm stuStatForm, String callAction)	throws Exception {

		//log.debug("StudentStatusAction - resetForm - callAction="+callAction);
		// Clear fields
		
		StudentStatusDAO dao = new StudentStatusDAO();
		
		stuStatForm.resetFormFields();
		
		stuStatForm.getStudent().setNumber("");
		stuStatForm.getStudent().setSurname("");
		stuStatForm.getStudent().setFirstnames("");
		stuStatForm.getStudent().setBirthYear("");
		stuStatForm.getStudent().setBirthMonth("");
		stuStatForm.getStudent().setBirthDay("");
		stuStatForm.setOriginatedFrom("");
		
		ArrayList<String> dateCheck = dao.validateClosingDate(stuStatForm.getStudent().getAcademicYear());
		if (!dateCheck.isEmpty()){ //Check Dates Array
			for (int i=0; i < dateCheck.size(); i++){
				//log.debug("StudentStatusAction - resetForm - dateCheck="+dateCheck.get(i).toString());
				if ("WAPSTAT".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuStatForm.getStudent().setDateWAPSTAT(true);
				}
			}
		}
		if (stuStatForm.getStudent().isDateWAPSTAT()){
			stuStatForm.setAllowLogin(true);
		}else{
			stuStatForm.setAllowLogin(false);
		}
		stuStatForm.getStudent().setAcademicYear(getCurrentAcademicYear());
		stuStatForm.getStudent().setAcademicPeriod(getCurrentAcademicPeriod());
		//log.debug("StudentStatusaction - resetForm - All StudentStatusForm Variables Cleared - Year="+stuStatForm.getStudent().getAcademicYear()+". Period="+stuStatForm.getStudent().getAcademicPeriod());
		
	}
	
	public String getCurrentAcademicYear() throws Exception {
		int tYear;
		String acaYear;

		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH)+1; //zero-based
		if (month >= 8 ){
			tYear = (year + 1);
			acaYear = Integer.toString(tYear);
		}else{
			acaYear = Integer.toString(year);
		}
		//log.debug("StudentStatusAction - getCurrentAcademicYear: " + acaYear);
		return acaYear;
	}

	public String getCurrentAcademicPeriod() throws Exception {
		String acaPeriod;

		Calendar cal = Calendar.getInstance();
		
		int month = cal.get(Calendar.MONTH)+1; //zero-based
		
		int currentYear = cal.get(Calendar.YEAR);
		int acaYear = Integer.parseInt(getCurrentAcademicYear());
		
		//log.debug("StudentStatusAction - currentYear: " + currentYear+", acaYear: " + acaYear+", month="+month);
		
		if (currentYear < acaYear){
			acaPeriod = "1";
		}else{
			if (month < 8 && month > 3){
				acaPeriod = "2";
			}else{
				acaPeriod = "1";
			}
		}
		//log.debug("StudentStatusAction - getCurrentAcademicPeriod: " + acaPeriod);
		return acaPeriod;
	}


	public String emptySession(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		StudentStatusForm stuStatForm = (StudentStatusForm) form;
		
		//log.debug("StudentStatusAction - emptySession");
		//log.debug("StudentStatusAction - emptySession - FromPage " + stuStatForm.getFromPage());

		// Clear fields
		resetForm(stuStatForm, "StudentStatusAction - emptySession - Return applyLogin");
		stuStatForm.setApplyType("");

		setDropdownListsLogin(request,stuStatForm);
		return "applyLogin";
	}

	public ActionForward cancel(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		//log.debug("StudentStatusAction - Cancel");

		StudentStatusForm stuStatFormOld = (StudentStatusForm) form;

		resetForm(stuStatFormOld, "StudentStatusAction Cancel(Action)");
		
		StudentStatusForm stuStatForm = (StudentStatusForm) form;
		StudentStatusDAO dao = new StudentStatusDAO();
		
		// Clear fields - Just in case
		resetForm(stuStatForm, "StudentStatusAction Cancel(Action)");
		stuStatForm.setWebLoginMsg("");

		ArrayList<String> dateCheck = dao.validateClosingDate(stuStatForm.getStudent().getAcademicYear());
		if (!dateCheck.isEmpty()){ //Check Dates Array
			for (int i=0; i < dateCheck.size(); i++){
				//log.debug("StudentStatusAction - Cancel - dateCheck="+dateCheck.get(i).toString());
				if ("WAPSTAT".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuStatForm.getStudent().setDateWAPSTAT(true);
				}
			}
		}
		/*
		if (stuStatForm.getStudent().isDateWAPSTAT()){
			stuStatForm.setAllowLogin(true);
		}else{
			stuStatForm.setAllowLogin(false);
		}
		//log.debug("StudentStatusAction - Cancel - validateClosingDate - AcademicYear="+ stuStatForm.getStudent().getAcademicYear() + ",  AcademicPeriod=" + stuStatForm.getStudent().getAcademicPeriod());

		stuStatForm.setWebLoginMsg("");
		
		//log.debug("StudentStatusAction -Cancel - validateClosingDate: AcademicYear="+ stuStatForm.getStudent().getAcademicYear() + ",  AcademicPeriod=" + stuStatForm.getStudent().getAcademicPeriod());

		setDropdownListsLogin(request,stuStatForm);
		//log.debug("StudentStatusAction - Cancel - Return applyLogin");
		return mapping.findForward("applyLogin");
		*/
		return new ActionForward("http://applications.unisa.ac.za/index.html",true);
		
	}
	
	public ActionForward quit(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		//log.debug("StudentStatusAction - Quit");
		StudentStatusForm stuStatForm = new StudentStatusForm();
		
		StudentStatusDAO dao = new StudentStatusDAO();
		
		// Clear fields - Just in case
		resetForm(stuStatForm, "StudentStatusAction Quit(Action)");
		
		stuStatForm.setWebLoginMsg("");
	
		ArrayList<String> dateCheck = dao.validateClosingDate(stuStatForm.getStudent().getAcademicYear());
		if (!dateCheck.isEmpty()){ //Check Dates Array
			for (int i=0; i < dateCheck.size(); i++){
				//log.debug("StudentStatusAction - Quit - dateCheck=["+dateCheck.get(i).toString()+"]");
				if ("WAPSTAT".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuStatForm.getStudent().setDateWAPSTAT(true);
				}
			}
		}
		if (stuStatForm.getStudent().isDateWAPSTAT()){
			stuStatForm.setAllowLogin(true);
		}else{
			stuStatForm.setAllowLogin(false);
		}
		//log.debug("StudentStatusAction - Quit - validateClosingDate: AcademicYear="+ stuStatForm.getStudent().getAcademicYear() + ",  AcademicPeriod=" + stuStatForm.getStudent().getAcademicPeriod());
		
		//log.debug("StudentStatusAction - Quit - CheckDates - WAPSTAT="+stuStatForm.getStudent().isDateWAPSTAT());
		
		setDropdownListsLogin(request,stuStatForm);
		//log.debug("StudentStatusAction - Quit - Return to applyLogin");
		return mapping.findForward("applyLogin");
	}

	public ActionForward unspecified(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{

		StudentStatusForm stuStatForm = (StudentStatusForm)form;
		
		setDropdownListsLogin(request,stuStatForm);
		//log.debug("StudentStatus: unspecified method call - no value for parameter in request");
		return mapping.findForward("applyLogin");
	}
	
	public String getMonth(int month) {
	    return new DateFormatSymbols().getMonths()[month-1];
	}
	
	private void applyStatus(HttpServletRequest request,ActionForm form) throws Exception {

		StudentStatusForm stuStatForm = (StudentStatusForm)form;
		StudentStatusDAO dao = new StudentStatusDAO();
		ActionMessages messages = new ActionMessages();

		//log.debug("StudentStatusAction - applyStatus");
		
		stuStatForm.setWebLoginMsg("");
		
		try{
			stuStatForm.setSelQualCode1(dao.getStatusQual("Qual", "1", stuStatForm.getStudent().getNumber(), stuStatForm.getStudent().getAcademicYear(), stuStatForm.getStudent().getAcademicPeriod()));
			stuStatForm.setSelQualCode2(dao.getStatusQual("Qual", "2", stuStatForm.getStudent().getNumber(), stuStatForm.getStudent().getAcademicYear(), stuStatForm.getStudent().getAcademicPeriod()));
			stuStatForm.setSelSpecCode1(dao.getStatusQual("Spec", "1", stuStatForm.getStudent().getNumber(), stuStatForm.getStudent().getAcademicYear(), stuStatForm.getStudent().getAcademicPeriod()));
			stuStatForm.setSelSpecCode2(dao.getStatusQual("Spec", "2", stuStatForm.getStudent().getNumber(), stuStatForm.getStudent().getAcademicYear(), stuStatForm.getStudent().getAcademicPeriod()));
			
			//log.debug("StudentStatusAction - applyStatus - " + stuStatForm.getStudent().getNumber() +" - Qual1     = " + stuStatForm.getSelQualCode1());
			//log.debug("StudentStatusAction - applyStatus - " + stuStatForm.getStudent().getNumber() +" - Qual2     = " + stuStatForm.getSelQualCode2());
			//log.debug("StudentStatusAction - applyStatus - " + stuStatForm.getStudent().getNumber() +" - Spec1     = " + stuStatForm.getSelSpecCode1());
			//log.debug("StudentStatusAction - applyStatus - " + stuStatForm.getStudent().getNumber() +" - Spec2     = " + stuStatForm.getSelSpecCode2());

			if ((stuStatForm.getSelQualCode1() == null || "Not Found".equalsIgnoreCase(stuStatForm.getSelQualCode1())) && (stuStatForm.getSelQualCode2() == null || "Not Found".equalsIgnoreCase(stuStatForm.getSelQualCode2()))){
				//log.debug("StudentStatusAction - applyStatus - QualCode1 NOT Found!!!");	
				messages.add(ActionMessages.GLOBAL_MESSAGE,
	        			new ActionMessage("message.generalmessage", "No Application or Qualification found"));
				addErrors(request, messages);
			}else{
				//Appeal & Offer Status
				String status1 = dao.getApplyStatus(stuStatForm.getStudent().getNumber(), stuStatForm.getStudent().getAcademicYear(), stuStatForm.getStudent().getAcademicPeriod(), "1");
				//log.debug("StudentStatusAction - applyStatus - Status1="+status1);
				stuStatForm.setQualStatusCode1(status1);
				if (status1 != null && !"".equals(status1)){
					stuStatForm.setQualStatus1(dao.getStatusDesc(status1));
					//log.debug("StudentStatusAction - applyStatus - Status1 Desc="+stuStatForm.getQualStatus1());					
				}
				
				if (stuStatForm.getSelQualCode2() != null && !"Not Found".equalsIgnoreCase(stuStatForm.getSelQualCode2())){
					String status2 = dao.getApplyStatus(stuStatForm.getStudent().getNumber(), stuStatForm.getStudent().getAcademicYear(), stuStatForm.getStudent().getAcademicPeriod(), "2");
					stuStatForm.setQualStatusCode2(status2);
					//log.debug("StudentStatusAction - applyStatus - Status2="+status2);
					if (status2 != null && !"".equals(status2)){
						stuStatForm.setQualStatus2(dao.getStatusDesc(status2));
						//log.debug("StudentStatusAction - applyStatus - Status2 Desc="+stuStatForm.getQualStatus2());
					}
				}
				
				//Offer Reason
				if ("AX".equalsIgnoreCase(stuStatForm.getQualStatusCode1())){
					String reason1 = dao.getDeclineReason(stuStatForm.getStudent().getNumber(),stuStatForm.getStudent().getAcademicYear(), stuStatForm.getStudent().getAcademicPeriod(), stuStatForm.getSelQualCode1());
					stuStatForm.setQualStatus1Reason(reason1);
					//log.debug("StudentStatusAction - applyStatus - Reason1="+reason1);
				}	
				
				if (stuStatForm.getSelQualCode2() != null && !"Not Found".equalsIgnoreCase(stuStatForm.getSelQualCode2())){
					if ("AX".equalsIgnoreCase(stuStatForm.getQualStatusCode2())){
						String reason2 = dao.getDeclineReason(stuStatForm.getStudent().getNumber(),stuStatForm.getStudent().getAcademicYear(), stuStatForm.getStudent().getAcademicPeriod(), stuStatForm.getSelQualCode2());
						stuStatForm.setQualStatus2Reason(reason2);
						//log.debug("StudentStatusAction - applyStatus - Reason2="+reason2);
					}
				}
				
				//Get Application Fee Information
				if ("NP".equalsIgnoreCase(stuStatForm.getQualStatusCode1()) || "NP".equalsIgnoreCase(stuStatForm.getQualStatusCode2())){
					Status status = new Status();
					status = dao.getStatusFee(stuStatForm.getStudent().getNumber(), stuStatForm.getStudent().getAcademicYear(), stuStatForm.getStudent().getAcademicPeriod());
					
					//log.debug("StudentStatusAction - applyStatus - dao PayDate="+status.getPayDate());
			    	//log.debug("StudentStatusAction - applyStatus - dao PayFee ="+status.getPayFee());
			    	//log.debug("StudentStatusAction - applyStatus - dao PayFull="+status.isPayFull());
			    	//log.debug("StudentStatusAction - applyStatus - dao PayCom ="+status.getPayComment());
			    	
			    	stuStatForm.getStatus().setPayDate(status.getPayDate());
			    	stuStatForm.getStatus().setPayFee(status.getPayFee());
			    	stuStatForm.getStatus().setPayFull(status.isPayFull());
			    	stuStatForm.getStatus().setPayComment(status.getPayComment());
					
					//Debug
					//log.debug("StudentStatusAction - applyStatus - form PayDate="+stuStatForm.getStatus().getPayDate());
			    	//log.debug("StudentStatusAction - applyStatus - form PayFee ="+stuStatForm.getStatus().getPayFee());
			    	//log.debug("StudentStatusAction - applyStatus - form PayFull="+stuStatForm.getStatus().isPayFull());
			    	//log.debug("StudentStatusAction - applyStatus - form PayCom ="+stuStatForm.getStatus().getPayComment());
			    	
				}
				
				//Johanet change 20180813 2018 July BRD - Social work screening
				stuStatForm.setScreeningSitting(false);
				Gencod gencod = new Gencod();
				String qual1=stuStatForm.getSelQualCode1().substring(0,5);
				String qual2=stuStatForm.getSelQualCode2().substring(0,5);
				StudentSystemGeneralDAO genDao = new StudentSystemGeneralDAO();
				
				if ("90088".equalsIgnoreCase(qual1) || "90011".equalsIgnoreCase(qual1)) {
					if (dao.includeSWScreeningSitting(stuStatForm.getStudent().getNumber(), stuStatForm.getStudent().getAcademicYear(),  stuStatForm.getStudent().getAcademicPeriod(), qual1)) {
						stuStatForm.setScreeningSitting(true);
						gencod = new Gencod();						
						gencod = genDao.getGenCode("311", qual1);
						if (gencod.getEngDescription() != null)
						stuStatForm.setScreeningSittingQual1(gencod.getEngDescription());
					}
				}
				
				if ("90088".equalsIgnoreCase(qual2) || "90011".equalsIgnoreCase(qual2)) {
					if (dao.includeSWScreeningSitting(stuStatForm.getStudent().getNumber(), stuStatForm.getStudent().getAcademicYear(),  stuStatForm.getStudent().getAcademicPeriod(), qual2)) {
						stuStatForm.setScreeningSitting(true);
						gencod = new Gencod();						
						gencod = genDao.getGenCode("311", qual2);
						if (gencod.getEngDescription() != null)
						stuStatForm.setScreeningSittingQual2(gencod.getEngDescription());
					}
				}				
				
				if (stuStatForm.isScreeningSitting()) {
					ScreeningVenue venue = new ScreeningVenue();
					venue = dao.getScreeningVenue(stuStatForm.getStudent().getNumber());
					stuStatForm.setScreeningVenue(venue);
				}
			}
			
		}catch(Exception e){
			throw new Exception("ApplyStatus error :  / " + e);
			//log.warn("StudentStatusAction - applyStatus - crashed / " + e);
		}
	}
	
   /************ basic validation ************/
   public String getBrowserInfo(String stuBrowserAgent) throws Exception {
	       String  browserDetails  =  stuBrowserAgent;
	       String  userAgent       =  browserDetails;
	       String  user            =  userAgent.toLowerCase();
	       String browser = "";
	       String os = "";
	       
	       //log.debug("StudentStatusAction - getBrowserInfo - stuBrowserAgent="+stuBrowserAgent);

	       //=================OS=======================
	        if (userAgent.toLowerCase().indexOf("windows") >= 0 ){
	            os = "Windows";
	        }else if(userAgent.toLowerCase().indexOf("mac") >= 0){
	            os = "Mac";
	        }else if(userAgent.toLowerCase().indexOf("x11") >= 0){
	            os = "Unix";
	        }else if(userAgent.toLowerCase().indexOf("android") >= 0){
	            os = "Android";
	        }else if(userAgent.toLowerCase().indexOf("iphone") >= 0){
	            os = "IPhone";
	        }else if(userAgent.toLowerCase().indexOf("blackberry") >= 0 || userAgent.toLowerCase().indexOf("bb") >= 0){
	            os = "BlackBerry";
	        }else if(userAgent.toLowerCase().indexOf("j2me") >= 0){
	            os = "J2ME/MIDP";
	        }else if(userAgent.toLowerCase().indexOf("symbian") >= 0){
	            os = "SymbianOS";        
	        }else{
	            os = "UnKnown, More-Info: "+userAgent;
	        }
	        //log.debug("StudentStatusAction - getBrowserInfo - Operating System="+os);
	        //===============Browser===========================
	       if (user.contains("msie") || user.contains("rv:")){
		       	if (user.contains("msie")){
		   	        String substring=userAgent.substring(userAgent.indexOf("MSIE")).split(";")[0];
		   	        browser=substring.split(" ")[0].replace("MSIE", "IE")+"-"+substring.split(" ")[1];
		       	}else{
		       		String ieVersion = userAgent.substring(userAgent.indexOf("rv")).split(":")[1];
		       		ieVersion = ieVersion.split(";")[0];
		       		browser = "IE-"+ieVersion;
		       	}
	       }else if (user.contains("safari") && user.contains("version")){
	           browser=(userAgent.substring(userAgent.indexOf("Safari")).split(" ")[0]).split("/")[0]+"-"+(userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
	       }else if ( user.contains("opr") || user.contains("opera")){
	    	   try{
		           if(user.contains("opera")){
		               browser=(userAgent.substring(userAgent.indexOf("Opera")).split(" ")[0]).split("/")[0]+"-"+(userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
		           }else if(user.contains("opr")){
		               browser=((userAgent.substring(userAgent.indexOf("OPR")).split(" ")[0]).replace("/", "-")).replace("OPR", "Opera");
		           }
	    	   }catch (Exception e){
	    		   //sometimes Opera gives errors
	    		   browser="Opera: "+userAgent;
	    	   }
	       }else if (user.contains("chrome")){
	           browser=(userAgent.substring(userAgent.indexOf("Chrome")).split(" ")[0]).replace("/", "-");
	       }else if ((user.indexOf("mozilla/7.0") > -1) || (user.indexOf("netscape6") != -1)  || (user.indexOf("mozilla/4.7") != -1) || (user.indexOf("mozilla/4.78") != -1) || (user.indexOf("mozilla/4.08") != -1) || (user.indexOf("mozilla/3") != -1) ){
	           //browser=(userAgent.substring(userAgent.indexOf("MSIE")).split(" ")[0]).replace("/", "-");
	           browser = "Netscape-?";
	       }else if (user.contains("firefox")){
	           browser=(userAgent.substring(userAgent.indexOf("Firefox")).split(" ")[0]).replace("/", "-");
	       }else{
	           browser = "UnKnown, More-Info: "+userAgent;
	       }
	       //log.debug("StudentStatusAction - getBrowserInfo - Browser Name="+browser);
	       
	       return browser + "@" + os;
   }
	   
	//Include some cross site scripting checks (XSS)
    private String stripXSS(String value) { 

        if (value != null) { 

            // NOTE: It's highly recommended to use the ESAPI library and uncomment the following line to 
            // avoid encoded attacks. 
            //value = ESAPI.encoder().canonicalize(value); 

            // Avoid null characters 
            value = value.replaceAll("", ""); 
            
            // Avoid anything between script tags 
            Pattern scriptPattern = Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE); 
            value = scriptPattern.matcher(value).replaceAll(""); 

            // Avoid anything in a src='...' type of expression 
            scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL); 

            scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL); 
            value = scriptPattern.matcher(value).replaceAll(""); 

            // Remove any lonesome </script> tag 
            scriptPattern = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE); 
            value = scriptPattern.matcher(value).replaceAll(""); 

            // Remove any lonesome <script ...> tag 
            scriptPattern = Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL); 
            value = scriptPattern.matcher(value).replaceAll(""); 

            // Avoid eval(...) expressions 
            scriptPattern = Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL); 
            value = scriptPattern.matcher(value).replaceAll(""); 

            // Avoid expression(...) expressions 
            scriptPattern = Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL); 
            value = scriptPattern.matcher(value).replaceAll(""); 

            // Avoid javascript:... expressions 
            scriptPattern = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE); 
            value = scriptPattern.matcher(value).replaceAll(""); 

			// Avoid vbscript:... expressions 
            scriptPattern = Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE); 
            value = scriptPattern.matcher(value).replaceAll(""); 

            // Avoid onload= expressions 
            scriptPattern = Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL); 
            value = scriptPattern.matcher(value).replaceAll(""); 
        } 
        return value; 
    } 

	
	/**
	  * 
	  * @param values
	  * @param codes
	  * @return
	  */
	 public String generateSortedString(List<String> codes,List<String> desc) {
		 StringBuffer sb = new StringBuffer("[");
		 for(int i = 0;i<desc.size()&&i<codes.size();i++){
			 sb.append("{\"code\":\""+codes.get(i)+"\",\"desc\":\""+desc.get(i)+"\"}");
			 //sb.append("\""+codes.get(i)+"\":\""+codes.get(j)+ " - " +desc.get(i)+"\"");
			 if(i<desc.size()-1){
				 sb.append(",");
			 }
		 }
		 sb.append("]");
		 return sb.toString();
	 }
	 
	 public String generatePrevQualString(List<String> codes,List<String> desc){
		  StringBuffer sb = new StringBuffer("{");
		  for(int i = 0;i<desc.size()&&i<codes.size();i++){
		   sb.append("\""+codes.get(i)+"\":\""+desc.get(i)+"\"");
		   if(i<desc.size()-1){
		    sb.append(",");
		   }
		  }
		  sb.append("}");
		  return sb.toString();
	}

	
	  @SuppressWarnings("unchecked")
	public void getAllRequestParamaters(HttpServletRequest req, HttpServletResponse res) throws Exception { 

		  //log.debug("StudentStatusAction -----------------------------------------------------------------");
		  //log.debug("StudentStatusAction - getAllRequestParamaters - Start");
		  Enumeration<String> parameterNames = req.getParameterNames(); 

		  while (parameterNames.hasMoreElements()) { 
			  String paramName = parameterNames.nextElement(); 
			  //log.debug("param: " + paramName); 

			  String[] paramValues = req.getParameterValues(paramName); 
			  for (int i = 0; i < paramValues.length; i++) { 
				  String paramValue = paramValues[i]; 
				  //log.debug("value: " + paramValue); 
			  } 
		  } 
		  //log.debug("StudentStatusAction - getAllRequestParamaters - End");
		  //log.debug("StudentStatusAction -----------------------------------------------------------------");
	  } 
	  
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

    	/** Edmund Enumerate through all parameters received
	  	 * @return **/
	    //getAllRequestParamaters(request, response);
    	
		//log.debug("StudentStatusAction - Execute - Action="+request.getParameter("act"));

        return super.execute(mapping, form, request, response);
   }
}

