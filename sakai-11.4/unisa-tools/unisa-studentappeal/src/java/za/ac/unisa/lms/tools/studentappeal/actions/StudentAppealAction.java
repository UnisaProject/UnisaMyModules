package za.ac.unisa.lms.tools.studentappeal.actions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import org.apache.struts.upload.FormFile;
import org.apache.struts.util.LabelValueBean;
import org.sakaiproject.component.cover.ServerConfigurationService;

import za.ac.unisa.lms.tools.studentappeal.dao.StudentAppealDAO;
import za.ac.unisa.lms.tools.studentappeal.forms.Student;
import za.ac.unisa.lms.tools.studentappeal.forms.StudentAppealForm;
import za.ac.unisa.lms.tools.studentappeal.bo.SavedDoc;
import za.ac.unisa.lms.tools.studentappeal.dao.SavedDocDao;
import za.ac.unisa.lms.tools.studentappeal.forms.StudentFile;
import za.ac.unisa.lms.tools.studentappeal.actions.WorkflowTempFile;

public class StudentAppealAction extends LookupDispatchAction {
	
    /** Our log (commons). */
	public static Log log = LogFactory.getLog(StudentAppealAction.class);
	
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
		
		map.put("button.getPDF","getPDFtoPrint");
		map.put("button.Upload","appealUpload");

		map.put("back", "back");
		map.put("cancel", "cancel");
		map.put("next", "nextStep");
	    map.put("walkthrough", "walkthrough");
	    
	    map.put("applyLogin", "applyLogin");
	    
	    map.put("Appeal","getPDFtoPrint");
	    map.put("appealSelect","appealSelect");
	    map.put("appealDeclare","appealDeclare");
	    map.put("appealConfirm","appealConfirm");
	    map.put("appealUpload","appealUpload");	    	    
    
	    return map;
	 }
	
	public void reset(ActionMapping mapping, HttpServletRequest request) throws Exception {

		//log.debug("StudentAppealAction - Reset - Initializing Action Form"); 
		StudentAppealForm stuAppForm =  new StudentAppealForm();
		resetForm(stuAppForm, "StudentAppealAction - Reset");
		
	}
	
	public ActionForward walkthrough(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		//log.debug("StudentAppealAction - IN walkthrough");
	    
		ActionMessages messages = new ActionMessages();
		StudentAppealForm stuAppForm = (StudentAppealForm) form;
		//StudentAppealForm stuAppForm =  new StudentAppealForm();
		
		resetForm(stuAppForm, "StudentAppealAction - WalkThrough");
		stuAppForm.setSelectReset("");
		stuAppForm.setWebLoginMsg("");
		
		//Write version number to log to check all servers
		//log.debug("StudentAppealAction - Applications Version="+stuAppForm.getVersion());
		
		//log.debug("StudentAppealAction - walkthrough - sessionID=" + request.getSession().getId());

		
		if (stuAppForm.getStudent().getAcademicYear() == null){
			stuAppForm.getStudent().setAcademicYear(getCurrentAcademicYear());
			stuAppForm.getStudent().setAcademicPeriod(getCurrentAcademicPeriod());
		}
			
		StudentAppealDAO dao = new StudentAppealDAO();
		if (stuAppForm.getStudent().getAcademicYear() == null){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", "Academic year invalid, please log on again to retry."));
			addErrors(request, messages);
			return mapping.findForward("loginSelect");
		}
		//log.debug("StudentAppealAction - walkthrough - dateCheck");

		ArrayList<String> dateCheck = dao.validateClosingDate(stuAppForm.getStudent().getAcademicYear());
		if (!dateCheck.isEmpty()){ //Check Dates Array
			for (int i=0; i < dateCheck.size(); i++){
				//log.debug("StudentAppealAction - walkthrough - dateCheck="+dateCheck.get(i).toString());
				if ("WAPAPL".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuAppForm.getStudent().setDateWAPAPL(true);
				}
			}
		}
		if (stuAppForm.getStudent().isDateWAPAPL()){
			stuAppForm.setAllowLogin(true);
		}else{
			stuAppForm.setAllowLogin(false);
		}
		
		//log.debug("StudentAppealAction - walkthrough - AcademicYear="+stuAppForm.getStudent().getAcademicYear());
		
		stuAppForm.getStudent().setNumber("");
		stuAppForm.getStudent().setSurname("");
		stuAppForm.getStudent().setFirstnames("");
		stuAppForm.getStudent().setBirthYear("");
		stuAppForm.getStudent().setBirthMonth("");
		stuAppForm.getStudent().setBirthDay("");
		
		setDropdownListsLogin(request,stuAppForm);
		//log.debug("StudentAppealAction - walkthrough - Return to applyLogin");
		return mapping.findForward("applyLogin");
	}

	public ActionForward applyLogin(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,	HttpServletResponse response)
			throws Exception {

		//log.debug("StudentAppealAction - applyLogin - IN applyLogin");
	    
		ActionMessages messages = new ActionMessages();
		StudentAppealForm stuAppForm = (StudentAppealForm) form;
		GeneralMethods gen = new GeneralMethods();
		StudentAppealDAO dao = new StudentAppealDAO();
		
		//log.debug("StudentAppealAction applyLogin " + request.getSession().getId() + " N " + stuAppForm.getStudent().getNumber() + " S " + stuAppForm.getStudent().getSurname() + " F " + stuAppForm.getStudent().getSurname() + " Y " + stuAppForm.getStudent().getBirthYear() + " M " + stuAppForm.getStudent().getBirthMonth() + " D " + stuAppForm.getStudent().getBirthDay());

		//log.debug("StudentAppealAction applyLogin - AcademicYear=" + stuAppForm.getStudent().getAcademicYear());
		if (stuAppForm.getStudent().getAcademicYear() == null){
			stuAppForm.getStudent().setAcademicYear(getCurrentAcademicYear());
			stuAppForm.getStudent().setAcademicPeriod(getCurrentAcademicPeriod());
		}
		
		stuAppForm.setApplyType("F");
		stuAppForm.setFromPage("stepLoginReturn");
		
		
		stuAppForm.getStudent().setNumber(stripXSS(stuAppForm.getStudent().getNumber()));
		if (stuAppForm.getStudent().getNumber() == null || "".equalsIgnoreCase(stuAppForm.getStudent().getNumber())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Enter student number."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuAppForm);
			//log.debug("StudentAppealAction - applyLogin - Input Required - Return to applyLogin");
			return mapping.findForward("applyLogin");
		}
		if (!isValidNumber(stuAppForm.getStudent().getNumber())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Enter a valid student number."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuAppForm);
			//log.debug("StudentAppealAction - applyLogin - Input Required - Return to applyLogin");
			return mapping.findForward("applyLogin");
		}
		/** Check the code against the flow diagram by the numbers provided **/
		/** Flow Check: (1) **/
		// We already check for 7 series numbers in JSP and SQL, but double-check if Student number = 8 characters and if 7 student, don't let them in...
		// Student should never get this far with a 7 series number, but we block him anyway.
		if (stuAppForm.getStudent().getNumber().length() == 8 ){ 
			//log.debug("check if Student number = 8 characters");
			if ("7".equalsIgnoreCase(stuAppForm.getStudent().getNumber().substring(0,1))){
				//log.debug("Student number = 8 characters and starts with 7 - Set Message");
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "You must apply for a formal Unisa student number. The number you have entered is reserved for non-formal studies only. Send an email to the Applications office if this information is incorrect or if you now intend applying for formal studies at <a href='mailto:applications@unisa.ac.za'>applications@unisa.ac.za</a>"));
				addErrors(request, messages);
				setDropdownListsLogin(request,stuAppForm);
				//log.debug("StudentAppealAction - applyLogin - Input Required - Return to applyLogin");
				return mapping.findForward("applyLogin");
			}
		}else if (stuAppForm.getStudent().getNumber().length() == 7 ){
			// If student no is only 7 characters (old), then add a 0 to the beginning
			//log.debug("StudentAppealAction - applyLogin - 7 char Student Number - Old Number: " + stuAppForm.getStudent().getNumber());
			stuAppForm.getStudent().setNumber("0" + stuAppForm.getStudent().getNumber());
			//log.debug("StudentAppealAction - applyLogin - 7 char Student Number - New Number: " + stuAppForm.getStudent().getNumber());
		}
		//log.debug("StudentAppealAction - applyLogin - After check if Student number = 8 characters");

		stuAppForm.getStudent().setSurname(stripXSS(stuAppForm.getStudent().getSurname()));
		if (stuAppForm.getStudent().getSurname() == null || "".equalsIgnoreCase(stuAppForm.getStudent().getSurname())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Enter student surname."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuAppForm);
			//log.debug("StudentAppealAction - applyLogin - Input Required - Return to applyLogin");
			return mapping.findForward("applyLogin");
		}
		if (!isNameValid(stuAppForm.getStudent().getSurname())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Enter a valid student surname."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuAppForm);
			//log.debug("StudentAppealAction - applyLogin - Input Required - Return to applyLogin");
			return mapping.findForward("applyLogin");
		}

		if (stuAppForm.getStudent().getSurname() == null || "".equalsIgnoreCase(stuAppForm.getStudent().getSurname())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Enter student first names."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuAppForm);
			//log.debug("StudentAppealAction - applyLogin - Input Required - Return to applyLogin");
			return mapping.findForward("applyLogin");
		}
		stuAppForm.getStudent().setSurname(stripXSS(stuAppForm.getStudent().getSurname()));
		if (!isNameValid(stuAppForm.getStudent().getSurname())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Enter a valid student first names."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuAppForm);
			//log.debug("StudentAppealAction - applyLogin - Input Required - Return to applyLogin");
			return mapping.findForward("applyLogin");
		}
		stuAppForm.getStudent().setBirthYear(stripXSS(stuAppForm.getStudent().getBirthYear()));
		stuAppForm.getStudent().setBirthYear(stuAppForm.getStudent().getBirthYear().trim());
		if (stuAppForm.getStudent().getBirthYear() == null){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Year) is blank - must be numeric format [CCYY]."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuAppForm);
			//log.debug("StudentAppealAction - applyLogin - Input Required - Return to applyLogin");
			return mapping.findForward("applyLogin");
		}
		if ("".equals(stuAppForm.getStudent().getBirthYear().trim())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Year) is empty - must be numeric format [CCYY]."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuAppForm);
			//log.debug("StudentAppealAction - applyLogin - Input Required - Return to applyLogin");
			return mapping.findForward("applyLogin");
		}
		if (!gen.isNumeric(stuAppForm.getStudent().getBirthYear())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Year) must be numeric format [CCYY]."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuAppForm);
			//log.debug("StudentAppealAction - applyLogin - Input Required - Return to applyLogin");
			return mapping.findForward("applyLogin");
		}
		if (Integer.parseInt(stuAppForm.getStudent().getBirthYear()) <= 1900 || Integer.parseInt(stuAppForm.getStudent().getBirthYear()) >= Integer.parseInt(stuAppForm.getStudent().getAcademicYear())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Year) invalid."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuAppForm);
			//log.debug("StudentAppealAction - applyLogin - Input Required - Return to applyLogin");
			return mapping.findForward("applyLogin");
		}
		if (stuAppForm.getStudent().getBirthYear().length() > 4){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Year) invalid. Please enter 4 characters or less. " ));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuAppForm);
			//log.debug("StudentAppealAction - applyLogin - Input Required - Return to applyLogin");
			return mapping.findForward("applyLogin");
		}
		stuAppForm.getStudent().setBirthMonth(stripXSS(stuAppForm.getStudent().getBirthMonth()));
		stuAppForm.getStudent().setBirthMonth(stuAppForm.getStudent().getBirthMonth().trim());
		if (stuAppForm.getStudent().getBirthMonth() == null){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Month) is blank - must be numeric format [MM]."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuAppForm);
			//log.debug("StudentAppealAction - applyLogin - Input Required - Return to applyLogin");
			return mapping.findForward("applyLogin");
		}
		if ("".equals(stuAppForm.getStudent().getBirthMonth())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Month) is empty must be numeric format [MM]."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuAppForm);
			//log.debug("StudentAppealAction - applyLogin - Input Required - Return to applyLogin");
			return mapping.findForward("applyLogin");
		}
		if (!gen.isNumeric(stuAppForm.getStudent().getBirthMonth())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Month) must be numeric format [MM]."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuAppForm);
			//log.debug("StudentAppealAction - applyLogin - Input Required - Return to applyLogin");
			return mapping.findForward("applyLogin");
		}
		if (Integer.parseInt(stuAppForm.getStudent().getBirthMonth()) < 1 || Integer.parseInt(stuAppForm.getStudent().getBirthMonth()) > 12){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Month) invalid."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuAppForm);
			//log.debug("StudentAppealAction - applyLogin - Input Required - Return to applyLogin");
			return mapping.findForward("applyLogin");
		}
		if (stuAppForm.getStudent().getBirthMonth().length() > 2){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Month) invalid. Please enter 2 characters or less. " ));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuAppForm);
			//log.debug("StudentAppealAction - applyLogin - Input Required - Return to applyLogin");
			return mapping.findForward("applyLogin");
		}

		//2015 Edmund - Expanded Date Tests as Students entered dates like 30/02/1992 etc
		int testMonth = Integer.parseInt(stuAppForm.getStudent().getBirthMonth());
		int daysMonth = 0;
		if (testMonth == 1 || testMonth == 3 || testMonth == 5 || testMonth == 7 || testMonth == 8 || testMonth == 10 || testMonth ==12){
			daysMonth = 31;
		}else if (testMonth == 4 || testMonth == 6 || testMonth == 9 || testMonth == 11){
			daysMonth = 30;
		}else{
			daysMonth = 28; //Checking for Leap year further below...
		}
		
		stuAppForm.getStudent().setBirthDay(stripXSS(stuAppForm.getStudent().getBirthDay()));
		stuAppForm.getStudent().setBirthDay(stuAppForm.getStudent().getBirthDay().trim());
		if (stuAppForm.getStudent().getBirthDay() == null){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Day) is blank - must be numeric format [DD]."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuAppForm);
			//log.debug("StudentAppealAction - applyLogin - Input Required - Return to applyLogin");
			return mapping.findForward("applyLogin");
		}
		if ("".equals(stuAppForm.getStudent().getBirthDay())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Day) is empty - must be numeric format [DD]."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuAppForm);
			//log.debug("StudentAppealAction - applyLogin - Input Required - Return to applyLogin");
			return mapping.findForward("applyLogin");
		}
		if (!gen.isNumeric(stuAppForm.getStudent().getBirthDay())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Day) must be numeric format [DD]."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuAppForm);
			//log.debug("StudentAppealAction - applyLogin - Input Required - Return to applyLogin");
			return mapping.findForward("applyLogin");
		}
		if (Integer.parseInt(stuAppForm.getStudent().getBirthDay()) < 1 || Integer.parseInt(stuAppForm.getStudent().getBirthDay()) > 31){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Day) invalid."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuAppForm);
			//log.debug("StudentAppealAction - applyLogin - Input Required - Return to applyLogin");
			return mapping.findForward("applyLogin");
		}
		if (stuAppForm.getStudent().getBirthDay().length() > 2){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Day) invalid. Please enter 2 characters or less. " ));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuAppForm);
			//log.debug("StudentAppealAction - applyLogin - Input Required - Return to applyLogin");
			return mapping.findForward("applyLogin");
		}
		if (Integer.parseInt(stuAppForm.getStudent().getBirthDay()) < 1){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Day) invalid."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuAppForm);
			//log.debug("StudentAppealAction - applyLogin - Input Required - Return to applyLogin");
			return mapping.findForward("applyLogin");
		}
		String monthName = "";
		if (daysMonth == 31 && Integer.parseInt(stuAppForm.getStudent().getBirthDay()) > 31){
			monthName = getMonth(testMonth);
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Day) invalid. " + monthName + " only has 31 days."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuAppForm);
			//log.debug("StudentAppealAction - applyLogin - Input Required - Return to applyLogin");
			return mapping.findForward("applyLogin");
		}
		if (daysMonth == 30 && Integer.parseInt(stuAppForm.getStudent().getBirthDay()) > 30){
			monthName = getMonth(testMonth);
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Day) invalid. " + monthName + " only has 30 days."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuAppForm);
			//log.debug("StudentAppealAction - applyLogin - Input Required - Return to applyLogin");
			return mapping.findForward("applyLogin");
		}
		if (Integer.parseInt(stuAppForm.getStudent().getBirthMonth()) == 2 && Integer.parseInt(stuAppForm.getStudent().getBirthDay()) > 29){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", "Student Date of Birth invalid. " ));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuAppForm);
			//log.debug("StudentAppealAction - applyLogin - Input Required - Return to applyLogin");
			return mapping.findForward("applyLogin");
		}
		if (Integer.parseInt(stuAppForm.getStudent().getBirthMonth()) == 2 && Integer.parseInt(stuAppForm.getStudent().getBirthDay()) == 29){
			String leapCheck = dao.checkLeapYear(stuAppForm.getStudent().getBirthYear());
			if ("Not a leap year".equalsIgnoreCase(leapCheck)){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (29/February/"+stuAppForm.getStudent().getBirthYear()+") invalid. " + stuAppForm.getStudent().getBirthYear() + " was not a leap year." ));
				addErrors(request, messages);
				setDropdownListsLogin(request,stuAppForm);
				//log.debug("StudentAppealAction - applyLogin - Input Required - Return to applyLogin");
				return mapping.findForward("applyLogin");
			}
		}
		
		//Add "0" incase Student enters just one character for Birthday or Month
		if (stuAppForm.getStudent().getBirthMonth().length() < 2){
			stuAppForm.getStudent().setBirthMonth("0" + stuAppForm.getStudent().getBirthMonth());
		}
		if (stuAppForm.getStudent().getBirthDay().length() < 2){
			stuAppForm.getStudent().setBirthDay("0" + stuAppForm.getStudent().getBirthDay());
		}
		
		String errorMsg="";
		errorMsg = displayPersonal(stuAppForm, request);

		if(!"".equals(errorMsg)){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", errorMsg));
				addErrors(request, messages);
				setDropdownListsLogin(request,stuAppForm);
				//log.debug("StudentAppealAction - applyLogin - DisplayPersonal - Return to applyLogin");
				return mapping.findForward("applyLogin");
		}else{
			stuAppForm.getStudent().setNumber(stuAppForm.getStudent().getNumber());
			stuAppForm.getStudent().setSurname(stuAppForm.getStudent().getSurname().toUpperCase());
			stuAppForm.getStudent().setFirstnames(stuAppForm.getStudent().getFirstnames().toUpperCase());
			stuAppForm.getStudent().setBirthYear(stuAppForm.getStudent().getBirthYear());
			stuAppForm.getStudent().setBirthMonth(stuAppForm.getStudent().getBirthMonth());
			stuAppForm.getStudent().setBirthDay(stuAppForm.getStudent().getBirthDay());
			
			//log.debug("StudentAppealAction - applyLogin - Write student details to STUXML");
			//Write student details to STUXML for later verification - Testing Thread security
			String referenceData = stuAppForm.getStudent().getNumber()+","+stuAppForm.getStudent().getSurname().toUpperCase()+","+stuAppForm.getStudent().getFirstnames().toUpperCase()+","+stuAppForm.getStudent().getBirthYear()+stuAppForm.getStudent().getBirthMonth()+stuAppForm.getStudent().getBirthDay();
			boolean stuError = false;
			String queryResultRef = "";
			String checkStu = "";
			int nextRef = 1;
			queryResultRef = dao.getSTUXMLRef(stuAppForm.getStudent().getNumber(), stuAppForm.getStudent().getAcademicYear(), stuAppForm.getStudent().getAcademicPeriod(), "StuInfo", "1", "applyLogin");
			//log.debug("StudentAppealAction - applyLogin - queryResultRef="+queryResultRef);
			if (queryResultRef.toUpperCase().contains("ERROR")){
				stuError = true;
			}
			if (!stuError){
				try{
					nextRef = Integer.parseInt(queryResultRef);
					nextRef++;
				}catch(Exception e){
					log.warn("StudentAppealAction - applyLogin - nextRef not Numeric - nextRef="+nextRef);
				}
				//log.debug("StudentAppealAction - applyLogin - queryResultRef="+queryResultRef+", nextRef="+nextRef);
				checkStu = dao.saveSTUXMLRef(stuAppForm.getStudent().getNumber(), stuAppForm.getStudent().getAcademicYear(), stuAppForm.getStudent().getAcademicPeriod(), "StuInfo", nextRef, referenceData, "applyLogin", "INSERT");
				if (checkStu.toUpperCase().contains("ERROR")){
					stuError = true;
				}
			}
			//log.debug("StudentAppealAction - applyLogin - Write student details to STUXML - Done - checkStu="+checkStu+", referenceData="+referenceData+", referenceSequence="+nextRef);
			
			//Get Browser Details
			//Is client behind something like a Proxy Server?
			String ipAddress = request.getHeader("X-FORWARDED-FOR");
			if (ipAddress == null) {
				   ipAddress = request.getRemoteAddr();
			}
			stuAppForm.getStudent().setStuIPAddress(ipAddress);
			stuAppForm.getStudent().setStuBrowserAgent(request.getHeader("User-Agent"));
			String browser = getBrowserInfo(stuAppForm.getStudent().getStuBrowserAgent());
			if (browser != null && !"".equals(browser.trim()) && browser.contains("@") && browser.length() > 3){
				int pos = browser.indexOf("@");
				stuAppForm.getStudent().setStuBrowser(browser.substring(0,pos));
				stuAppForm.getStudent().setStuWksOS(browser.substring(pos+1,browser.length()));
				//log.debug("StudentAppealAction - applyLogin - Write Browser details to STUXML");
				//Write Browser details to STUXML for later verification - Testing Thread security
				String browserData = stuAppForm.getStudent().getNumber()+","+stuAppForm.getStudent().getStuIPAddress()+","+stuAppForm.getStudent().getStuBrowser()+","+stuAppForm.getStudent().getStuWksOS();
				//log.debug("StudentAppealAction - applyLogin - browserData="+browserData);
				boolean browserError = false;
				String queryResultBrowser = "";
				String checkBrowser = "";
				int nextBrowser = 1;
				queryResultBrowser = dao.getSTUXMLRef(stuAppForm.getStudent().getNumber(), stuAppForm.getStudent().getAcademicYear(), stuAppForm.getStudent().getAcademicPeriod(), "WKSInfo", "1", "applyLogin");
				//log.debug("StudentAppealAction - applyLogin - queryResultRef="+queryResultBrowser);
				if (queryResultBrowser.toUpperCase().contains("ERROR")){
					browserError = true;
				}
				if (!browserError){
					try{
						nextBrowser = Integer.parseInt(queryResultBrowser);
						nextBrowser++;
					}catch(Exception e){
						log.warn("StudentAppealAction - applyLogin - nextRef not Numeric - nextRef="+nextBrowser);
					}
					//log.debug("StudentAppealAction - applyLogin - queryResultBrowser="+queryResultBrowser+", nextRef="+nextBrowser);
					checkBrowser = dao.saveSTUXMLRef(stuAppForm.getStudent().getNumber(), stuAppForm.getStudent().getAcademicYear(), stuAppForm.getStudent().getAcademicPeriod(), "WKSInfo", nextBrowser, browserData, "applyLogin", "INSERT");
					if (checkBrowser.toUpperCase().contains("ERROR")){
						browserError = true;
					}
				}
				//log.debug("StudentAppealAction - applyLogin - Write student details to STUXML - Done - checkStu="+checkBrowser+", browserData="+browserData+", referenceSequence="+nextBrowser);
			}
			
			/**
			 * Validation Test: Check if student has a academic record
			 **/
			
			/** Continue to check the code against the flow diagram by the numbers provided **/
			/** Flow Check: (1) **/
			//log.debug("StudentAppealAction - applyLogin - (1) - Check STUAPQ (F851)");
			String bDay = stuAppForm.getStudent().getBirthDay() + "/" + stuAppForm.getStudent().getBirthMonth() + "/" + stuAppForm.getStudent().getBirthYear();

			boolean vSTUAPQCheck = dao.validateSTUAPQ(stuAppForm.getStudent().getSurname(), stuAppForm.getStudent().getFirstnames(), bDay, stuAppForm.getStudent().getAcademicYear(), stuAppForm.getStudent().getAcademicPeriod());
			stuAppForm.getStudent().setStuapqExist(vSTUAPQCheck);
				
			//log.debug("StudentAppealAction - applyLogin - (IF) vAcaCheck=True - vRETSTUAPQCheck: " + vSTUAPQCheck + " - setStuExist:curStu - goto - MenuReturnStu");
			if (vSTUAPQCheck){ //Student already applied during this application period (Only allowed one application per application period)
				/** Flow Check: (2) **/
				//log.debug("StudentAppealAction - applyLogin - (2) - Student has applied during this application period");
				/** Flow Check: (3) **/
				//log.debug("StudentAppealAction - applyLogin - (3) - Student selected to go directly to Appeal page");
				stuAppForm.getStudent().setStuExist(true);
				/** Flow Check: (4) **/
				//log.debug("StudentAppealAction - applyLogin - (4) - Redirect to AppealSelect page");
				applyStatus(request, stuAppForm);
				setDropdownListsAppeal(request,stuAppForm);
				return mapping.findForward("appealSelect");
			} else{ //Student thus doesn't have a STUAPQ record for this Academic Year & Period
				/** Flow Check: (5) **/
				//log.debug("StudentAppealAction - applyLogin - (5) - Student thus doesn't have a STUAPQ record for this Academic Year & Period yet");
				stuAppForm.getStudent().setStuExist(false);
				/** Flow Check: (6) **/
				//log.debug("StudentAppealAction - applyLogin - (6) - Set Message - You do not have a current application record for this academic year.newline Click OK retry or click Cancel if you wish to quit the application process.");
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "No application record was found for you for this academic year. Please use the main options to apply for a qualification change before you can use this function."));
				addErrors(request, messages);
				/** Flow Check: (7) **/
				//log.debug("StudentAppealAction - applyLogin - (7) - Goto applyLogin");
				setDropdownListsLogin(request,stuAppForm);
				return mapping.findForward("applyLogin");
			}
		}
	}

	public String displayPersonal(StudentAppealForm stuAppForm, HttpServletRequest request) throws Exception {
		
		StudentAppealDAO dao = new StudentAppealDAO();
		
		//log.debug("StudentAppealAction - displayPersonal - Start");
		Student teststu = new Student();
		teststu.setNumber(stuAppForm.getStudent().getNumber().trim());
		teststu.setFirstnames(stuAppForm.getStudent().getFirstnames().trim());
		teststu.setSurname(stuAppForm.getStudent().getSurname().trim());
		teststu.setBirthDay(stuAppForm.getStudent().getBirthDay());
		teststu.setBirthMonth(stuAppForm.getStudent().getBirthMonth());
		teststu.setBirthYear(stuAppForm.getStudent().getBirthYear());

		String result = "";

		String checkNumber = dao.personalDetails(stuAppForm.getStudent().getNumber(),"number").trim();
		if ("error".equalsIgnoreCase(checkNumber)){
			result = "The student number entered is invalid. Please enter a valid number.";
			return result;
		}else{
			stuAppForm.getStudent().setNumber(checkNumber);
			//log.debug("StudentAppealAction - displayPersonal - Student Number: " + stuAppForm.getStudent().getNumber());
		}
		
		String checkSurname = dao.personalDetails(stuAppForm.getStudent().getNumber(),"surname").trim();
		if ("error".equalsIgnoreCase(checkSurname)){
			result = "The surname information was not successfully retrieved from the database";
			return result;
		}else{
			stuAppForm.getStudent().setSurname(checkSurname);
			//log.debug("StudentAppealAction - displayPersonal - Surname: " + stuAppForm.getStudent().getSurname());
		}
		
		String checkFirstnames = dao.personalDetails(stuAppForm.getStudent().getNumber(),"first_names").trim();
		if ("error".equalsIgnoreCase(checkFirstnames)){
			result = "The First name information was not successfully retrieved from the database";
			return result;
		}else{
			stuAppForm.getStudent().setFirstnames(checkFirstnames);
			//log.debug("StudentAppealAction - displayPersonal - First Names: " + stuAppForm.getStudent().getFirstnames());
		}

		String checkMaidenname = dao.personalDetails(stuAppForm.getStudent().getNumber(),"previous_surname").trim();
		if ("error".equalsIgnoreCase(checkMaidenname)){
			result = "The Maiden name information was not successfully retrieved from the database";
			return result;
		}else{
			stuAppForm.getStudent().setMaidenName(checkMaidenname);
			//log.debug("StudentAppealAction - displayPersonal - Prev Surname: " + stuAppForm.getStudent().getMaidenName());
		}
		
		String YYYYMMDD = "";
		String checkBDate = dao.personalDetails(stuAppForm.getStudent().getNumber(),"BIRTH_DATE").trim();
		if ("error".equalsIgnoreCase(checkBDate)){
			result = "The Birth Date information was not successfully retrieved from the database";
			return result;
		}else{
			YYYYMMDD = checkBDate;
			//log.debug("StudentAppealAction - displayPersonal - Birth Date: " + YYYYMMDD);
		}

		//log.debug("StudentAppealAction - displayPersonal - BirthDay: " + stuAppForm.getStudent().getBirthYear() + "/" + stuAppForm.getStudent().getBirthMonth() + "/" + stuAppForm.getStudent().getBirthDay());
		if(YYYYMMDD.length()>=8){
			//log.debug("StudentAppealAction - displayPersonal - " + request.getSession().getId() + " N " + stuAppForm.getStudent().getNumber() + " Success BirthDay " + YYYYMMDD);
			stuAppForm.getStudent().setBirthYear(YYYYMMDD.substring(0,4));
			stuAppForm.getStudent().setBirthMonth(YYYYMMDD.substring(4,6));
			stuAppForm.getStudent().setBirthDay(YYYYMMDD.substring(6,8));
		}else{
			//log.debug("StudentAppealAction - displayPersonal - " + request.getSession().getId() + " N " + stuAppForm.getStudent().getNumber() + " UnSuccess BirthDay " + YYYYMMDD);
			result = "The birth day information was not successfully retrieved from the database";
			return result;
		}
		//log.debug("StudentAppealAction - displayPersonal - BirthDay: " + stuAppForm.getStudent().getBirthYear() + "/" + stuAppForm.getStudent().getBirthMonth() + "/" + stuAppForm.getStudent().getBirthDay());

		String tmpCellNr = dao.personalDetails(stuAppForm.getStudent().getNumber(),"cell_number").trim();
		if (!"error".equalsIgnoreCase(tmpCellNr)){
			stuAppForm.getStudent().setCellNr(tmpCellNr);
		}else{
			stuAppForm.getStudent().setCellNr("");
		}
		//log.debug("StudentAppealAction - displayPersonal - Cell Number: " + stuAppForm.getStudent().getCellNr());

		//log.debug("StudentAppealAction - displayPersonal - " + request.getSession().getId() + " N " + stuAppForm.getStudent().getNumber() + " Cell " + stuAppForm.getStudent().getCellNr());
		
		String tmpEmail = dao.personalDetails(stuAppForm.getStudent().getNumber(),"email_address").trim();
		if (!"error".equalsIgnoreCase(tmpEmail)){
			stuAppForm.getStudent().setEmailAddress(tmpEmail);
		}else{
			stuAppForm.getStudent().setEmailAddress("");
		}
		//log.debug("StudentAppealAction - displayPersonal - Email Address: " + stuAppForm.getStudent().getEmailAddress());

		//log.debug("StudentAppealAction - displayPersonal - " + request.getSession().getId() + " N " + stuAppForm.getStudent().getNumber() + " Email " + stuAppForm.getStudent().getEmailAddress());
		
		// Closing date  --Integer.parseInt(String.valueOf(stuAppForm.getStudent().getAcademicYear())))){

		//Validate if screen input is correct student
		if (!stuAppForm.getStudent().getSurname().equalsIgnoreCase(teststu.getSurname())){
			result = "The surname you have entered does not correspond with the information on the database";
		}else if (!stuAppForm.getStudent().getFirstnames().equalsIgnoreCase(teststu.getFirstnames())){
			result= "The first name(s) you have entered does not correspond with the information on the database";
		}else if (!stuAppForm.getStudent().getBirthYear().equals(teststu.getBirthYear())){
			result="The birthdate you have entered does not correspond with the information on the database";
		}else if (!stuAppForm.getStudent().getBirthMonth().equals(teststu.getBirthMonth())){
			result="The birthdate you have entered does not correspond with the information on the database";
		}else if (!stuAppForm.getStudent().getBirthDay().equals(teststu.getBirthDay())){
			result="The birthdate you have entered does not correspond with the information on the database";
		}else if (!dao.validateClosingDateAll(stuAppForm.getStudent().getAcademicYear())){
			if ("AllClosed".equalsIgnoreCase(stuAppForm.getStudent().getWebOpenDate())){
				result="The application period for study at Unisa is closed.";
			}
		}

		//if (!"".equals(result)){
			//error result - move input back to screen values
			stuAppForm.getStudent().setNumber(teststu.getNumber());
			stuAppForm.getStudent().setSurname(teststu.getSurname());
			stuAppForm.getStudent().setFirstnames(teststu.getFirstnames());
			stuAppForm.getStudent().setBirthDay(teststu.getBirthDay());
			stuAppForm.getStudent().setBirthMonth(teststu.getBirthMonth());
			stuAppForm.getStudent().setBirthYear(teststu.getBirthYear());
		//}
		//if any error message =result
		//log.debug("StudentAppealAction - displayPersonal - Result: " + result);
		return result;
	}
	
	public ActionForward appealSelect(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,	HttpServletResponse response)
			throws Exception {

		//log.debug("StudentAppealAction - appealSelect - Start");
	    
		StudentAppealForm stuRegForm = (StudentAppealForm)form;
		ActionMessages messages = new ActionMessages();
		
		stuRegForm.setWebLoginMsg("");
		
    	if (!"Back".equalsIgnoreCase(request.getParameter("act"))){
	    	stuRegForm.setAppealSelect1(stripXSS(stuRegForm.getAppealSelect1()));
	    	stuRegForm.setAppealSelect2(stripXSS(stuRegForm.getAppealSelect2()));
    		stuRegForm.setAppealText(stripXSS(request.getParameter("typeText").trim()));
    	}
	
		// Check inputs
		if (!"Y".equalsIgnoreCase(stuRegForm.getAppealSelect1()) && !"".equalsIgnoreCase(stuRegForm.getAppealSelect1()) && !"Y".equalsIgnoreCase(stuRegForm.getAppealSelect2()) && !"".equalsIgnoreCase(stuRegForm.getAppealSelect2())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", "Please indicate which qualification decision you wish to appeal."));
			addErrors(request, messages);
			setDropdownListsAppeal(request,stuRegForm);
			return mapping.findForward("appealSelect");
		}
		if (stuRegForm.getAppealText() == null || "".equalsIgnoreCase(stuRegForm.getAppealText().trim())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", "Please motivate your decision to appeal."));
			addErrors(request, messages);
			setDropdownListsAppeal(request,stuRegForm);
			return mapping.findForward("appealSelect");
		}
    	//log.debug("StudentAppealAction - appealSelect - Continue to Declaration............");
    	return mapping.findForward("appealDeclare");
	}
	
	public ActionForward appealDeclare(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,	HttpServletResponse response)
			throws Exception {
		
		//log.debug("StudentAppealAction - appealDeclare -Start");
		
		StudentAppealForm stuRegForm = (StudentAppealForm)form;
		ActionMessages messages = new ActionMessages();
		
		// agreement
		stuRegForm.setAgree(stripXSS(stuRegForm.getAgree()));
		if (stuRegForm.getAgree() == null || "".equals(stuRegForm.getAgree().trim())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Indicate your agreement to the declaration and undertaking."));
			addErrors(request, messages);
			return mapping.findForward("appealDeclare");
		}
	
		//test agreement flag
		if (!"Y".equalsIgnoreCase(stuRegForm.getAgree())){
			return mapping.findForward("Input");
		}
		
		//Appeal Workflow
		//Set submission time stamp
		Date date = new java.util.Date();
		String displayDate = (new java.text.SimpleDateFormat("EEEEE dd MMMMM yyyy hh:mm:ss").format(date).toString());
		stuRegForm.getStudent().setAppTime(displayDate);
		request.setAttribute("email",stuRegForm.getStudent().getEmailAddress());
				
		//Write work flow document
		writeWorkflowAppeal(stuRegForm,date);
		//log.debug("StudentAppealAction - appealDeclare - WriteWorkflowAppeal");
		
		//Move Documents from Temp Folder to main folder 
		moveDocuments(stuRegForm.getStudent().getNumber());
		//log.debug("StudentAppealAction - appealDeclare - moveDocuments");
    	
		//log.debug("StudentAppealAction - appealDeclare - Done - GoTo appealConfirm");

		return mapping.findForward("appealConfirm");
	}

	public String appealConfirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		//log.debug("StudentAppealAction - appealConfirm -Start");
    	
		//log.debug("StudentAppealAction - appealConfirm - Done - GoTo Unisa Eb Page");
		
		return "cancel";
	}

	/*
	 * populates the Year dropdown. Uses JSON to return Key and Value list
	 */
	public ActionForward populateYear(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		//log.debug("StudentAppealAction - populateYear - Start");

		JSONObject yearObj = new JSONObject();
		Map<String, String> mapYear = new LinkedHashMap<String, String>();
		int keyCounter = 0;
		
		//log.debug("StudentAppealAction - populateYear - **************************************************************");

		try{
			int startYear = 1960; //Don't think student can be more than 100 years old
			
			//Ensure that End year is later than Start year
			String startYearSelected = stripXSS(request.getParameter("startYearSelected"));
			//log.debug("StudentAppealAction - populateYear - startYearSelected="+startYearSelected);
	
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
       	//log.debug("StudentAppealAction - populateYear - Final - **************************************************************");
       	//log.debug("StudentAppealAction - populateYear - Final - jsonObject="+jsonObject.toString());
       	//log.debug("StudentAppealAction - populateYear - Final - **************************************************************");
       	out.write(jsonObject.toString());
       	out.flush();

		return null; //Returning null to prevent struts from interfering with Ajax/JSON
	}
	
	/**
	 * This method writes the application for qualification change workflow file to the
	 * path specified in the following file:
	 * sakai/jakarta-tomcat-5.5.9/sakai/sakai.properties
	 *
	 * @param stuRegForm	       The form associated with the action
	 * @param applyDateTime		   The date and time the application was submitted
	*/
	private void writeWorkflowAppeal(StudentAppealForm stuRegForm, Date applyDateTime) throws Exception{
		
		//log.debug("StudentAppealAction: writeWorkflowAppeal for studnr="+stuRegForm.getStudent().getNumber() + " Started");

		//ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();

		// Qualifications Appealed - Debug
		/*
		if ("Y".equalsIgnoreCase(stuRegForm.getAppealSelect1())){
			log.debug("StudentAppealAction - writeWorkflowAppeal - AppealQual1: " + stuRegForm.getSelQualCode1());
		}
		if ("Y".equalsIgnoreCase(stuRegForm.getAppealSelect2())){
			log.debug("StudentAppealAction - writeWorkflowAppeal - AppealQual2: " + stuRegForm.getSelQualCode2());
		}
		
		log.debug("StudentAppealAction - writeWorkflowAppeal - AppealText: " + stuRegForm.getAppealText());
		
		log.debug("StudentAppealAction - writeWorkflowAppeal - No of: AppealTypeFiles" + stuRegForm.getAppealTypeFiles().size()+", AppealSourceFiles" + stuRegForm.getAppealSourceFiles().size()+", AppealWorkflowFiles" + stuRegForm.getAppealWorkflowFiles().size());
		
		if (stuRegForm.getAppealTypeFiles().size() > 0){
			for (int i = 0; i < stuRegForm.getAppealTypeFiles().size(); i++){
				log.debug("StudentAppealAction - Upload - Appeal File - Done - Type File "+i+" - File=" + stuRegForm.getAppealTypeFiles().get(i).toString());
			}
		}
		if (stuRegForm.getAppealWorkflowFiles().size() > 0){
			for (int i = 0; i < stuRegForm.getAppealWorkflowFiles().size(); i++){
				log.debug("StudentAppealAction - Upload - Appeal File - Done - Workflow File "+i+" - File=" + stuRegForm.getAppealWorkflowFiles().get(i).toString());
			}
		}
		if (stuRegForm.getAppealSourceFiles().size() > 0){
			for (int i = 0; i < stuRegForm.getAppealSourceFiles().size(); i++){
				log.debug("StudentAppealAction - Upload - Appeal File - Done - Source File "+i+" - File=" + stuRegForm.getAppealSourceFiles().get(i).toString());
			}
		}
		*/

		String type = "L"; /* L = local F=Foreign */
		String wflType = "A";

		/* set local or foreign */
		if (!"1015".equals(stuRegForm.getStudent().getCountry().getCode())){
			type = "F";
		}

		/* write to file */
		WorkflowTempFile file = new WorkflowTempFile("DSAR31_"+ type + "_"+ stuRegForm.getStudent().getNumber(),wflType);
		file.add(" Subject: Application for Appeal \r\n");
		String displayDate = (new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(applyDateTime).toString());
		file.add(" Date received: " + displayDate + "\r\n");
		file.add(" The following request was received through the Unisa Web Server: \r\n");
		file.add(" --------------------------------------------------------------------------\r\n");
		file.add(" Details: \r\n");
		file.add(" Student Number                     = " + stuRegForm.getStudent().getNumber() + "\r\n");
		file.add(" Surname                            = " + stuRegForm.getStudent().getSurname() + "\r\n");
		file.add(" First names                        = " + stuRegForm.getStudent().getFirstnames() + "\r\n");
		file.add(" Maiden name / \r\n");
		file.add(" Previous surname                   = " + stuRegForm.getStudent().getMaidenName() + "\r\n");
		file.add(" Date of birth                      = Year: " + stuRegForm.getStudent().getBirthYear()+ " Month: "+ stuRegForm.getStudent().getBirthMonth() +" Day: "+ stuRegForm.getStudent().getBirthDay()+"\r\n");
		file.add(" --------------------------------------------------------------------------\r\n");
		file.add(" Cell number                        = " + stuRegForm.getStudent().getCellNr() + "\r\n");
		file.add(" E-mail address                     = " + stuRegForm.getStudent().getEmailAddress() + "\r\n");
		file.add(" --------------------------------------------------------------------------\r\n");
		file.add(" \r\n");
		file.add(" Qualification(s) for appeal:\r\n");
		file.add(" ----------------------------\r\n");
		//ToDo - If Statements for Appeal
		if ("Y".equalsIgnoreCase(stuRegForm.getAppealSelect1())){
			file.add(" Qualification decision appealed    = " + stuRegForm.getSelQualCode1() + "\r\n");
			file.add(" \r\n");
		}
		if ("Y".equalsIgnoreCase(stuRegForm.getAppealSelect2())){
			file.add(" Qualification decision appealed    = " + stuRegForm.getSelQualCode2() + "\r\n");
			file.add(" \r\n");
		}
		file.add(" --------------------------------------------------------------------------\r\n");
		file.add(" \r\n");
		file.add(" Reasons/motivation:\r\n");
		file.add(" -------------------\r\n");
		file.add(" " + stuRegForm.getAppealText());
		file.add(" \r\n");
		file.add(" --------------------------------------------------------------------------\r\n");
		file.add(" \r\n");
		file.add(" Documents to Support Appeal:\r\n");
		file.add(" ----------------------------\r\n");
		if (stuRegForm.getAppealWorkflowFiles().size() > 0){
			file.add(" File Uploaded                      = Workflow and Original File Name\r\n");
			file.add(" \r\n");
			for (int i = 0; i < stuRegForm.getAppealWorkflowFiles().size(); i++){
				file.add("     File Type                      = " + stuRegForm.getAppealTypeFiles().get(i).toString()+"\r\n");
				file.add("     Worlflow/System File           = " + stuRegForm.getAppealWorkflowFiles().get(i).toString()+"\r\n");
				file.add("     Student's File                 = " + stuRegForm.getAppealSourceFiles().get(i).toString() +"\r\n");
				file.add(" \r\n");
			}
		}else{
			file.add(" \r\n");
			file.add(" No files uploaded\r\n");
			file.add(" \r\n");
		}
		file.add(" --------------------------------------------------------------------------\r\n");
		file.add(" ==========================================================================\r\n");
		file.close(stuRegForm.getStudent().getNumber());

		//log.debug("StudentAppealAction: writeWorkflowAppeal for studnr="+stuRegForm.getStudent().getNumber() + " Completed");
		
	}

	public ActionForward nextStep(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// if student number is null, block application
		ActionMessages messages = new ActionMessages();
		StudentAppealForm stuAppForm = (StudentAppealForm) form;

		String nextPage = "";
		String page = stripXSS(request.getParameter("page"));
		//log.debug("StudentAppealAction Nextpage - page="+page+", nextPage=" + nextPage);
		

		if(stuAppForm.getStudent().getNumber() == null){
			//log.debug("StudentAppealAction Nextpage " + request.getSession().getId() + " Student Nr is null or empty when saving qualifications");
			messages.add(ActionMessages.GLOBAL_MESSAGE,
			new ActionMessage("message.generalmessage", "You performed and invalid action or an Error occurred. Please log on again to retry."));
			addErrors(request, messages);
			//log.debug("StudentAppealAction - Nextpage - Return to applyLogin");
			setDropdownListsLogin(request,stuAppForm);
			return mapping.findForward("applyLogin");
		}

		if (stuAppForm.getApplyType()==null || "".equals(stuAppForm.getApplyType())){
			stuAppForm.setApplyType("F");
		}
		
		if ("applyLogin".equalsIgnoreCase(page)){
			setDropdownListsLogin(request,stuAppForm);
			return mapping.findForward("applyLogin");
		}else if ("appealConfirm".equalsIgnoreCase(page)){
			nextPage = appealConfirm(mapping,form,request, response);
		}
		
		//log.debug("StudentAppealAction Nextpage - Nextpage=" + nextPage);
		return mapping.findForward(nextPage);
	}

	/* This method redirects a page to the previous page
	 according to the setup below 	*/
	@SuppressWarnings("unused")
	public ActionForward back(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		StudentAppealForm stuRegForm = (StudentAppealForm) form;
		
		String qualif = stuRegForm.getQual().getQualCode();
		String applyType=stuRegForm.getApplyType();
		String prevPage = "";
		String page = stripXSS(request.getParameter("page"));
		//log.debug("StudentAppealAction - Back - Page="+page+", prevPage="+prevPage);
		
		if ("appealSelect".equalsIgnoreCase(page)) {
			prevPage =  "applyLoginSelect";
		}else if ("appealDeclare".equalsIgnoreCase(page)) {
			setDropdownListsAppeal(request,stuRegForm);
			prevPage =  "appealSelect";
		}else if ("appealConfirm".equalsIgnoreCase(page)) {
			prevPage =  "appealDeclare";
		}
		//log.debug("StudentAppealAction Nextpage - Prevpage=" + prevPage);
		return mapping.findForward(prevPage);
	}
	
	private void setDropdownListsLogin(HttpServletRequest request, ActionForm form) throws Exception{
		
		StudentAppealForm stuAppForm = (StudentAppealForm) form;
		
		//log.debug("StudentAppealAction - setDropdownListLogin - AcademicYear="+stuAppForm.getStudent().getAcademicYear());

		//log.debug("StudentAppealAction - setDropdownListsLogin - Start");
		setUpYearList(request);
		setUpMonthList(request);
		setUpDayList(request);
		//log.debug("StudentAppealAction - setDropdownListsLogin - End");
	}
	
	private void setDropdownListsAppeal(HttpServletRequest request, ActionForm form) throws Exception{
		
		StudentAppealDAO dao = new StudentAppealDAO();
		List<LabelValueBean> list = dao.getAppealDocTypes();
		list.add(0,new LabelValueBean("Select File type from Menu","-1"));
		request.setAttribute("filelist",list);
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

	public void resetForm(StudentAppealForm stuAppForm, String callAction)	throws Exception {

		//log.debug("StudentAppealAction - resetForm - callAction="+callAction);
		// Clear fields
		
		StudentAppealDAO dao = new StudentAppealDAO();
		
		stuAppForm.resetFormFields();
		
		stuAppForm.getStudent().setNumber("");
		stuAppForm.getStudent().setSurname("");
		stuAppForm.getStudent().setFirstnames("");
		stuAppForm.getStudent().setBirthYear("");
		stuAppForm.getStudent().setBirthMonth("");
		stuAppForm.getStudent().setBirthDay("");
		
		ArrayList<String> dateCheck = dao.validateClosingDate(stuAppForm.getStudent().getAcademicYear());
		if (!dateCheck.isEmpty()){ //Check Dates Array
			for (int i=0; i < dateCheck.size(); i++){
				//log.debug("StudentAppealAction - resetForm - dateCheck="+dateCheck.get(i).toString());
				if ("WAPAPL".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuAppForm.getStudent().setDateWAPAPL(true);
				}
			}
		}
		if (stuAppForm.getStudent().isDateWAPAPL()){
			stuAppForm.setAllowLogin(true);
		}else{
			stuAppForm.setAllowLogin(false);
		}
		
		stuAppForm.getStudent().setAcademicYear(getCurrentAcademicYear());
		stuAppForm.getStudent().setAcademicPeriod(getCurrentAcademicPeriod());
		//log.debug("StudentAppealaction - resetForm - All StudentAppealForm Variables Cleared - Year="+stuAppForm.getStudent().getAcademicYear()+". Period="+stuAppForm.getStudent().getAcademicPeriod());
		
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
		//log.debug("StudentAppealAction - getCurrentAcademicYear: " + acaYear);
		return acaYear;
	}

	public String getCurrentAcademicPeriod() throws Exception {
		String acaPeriod;

		Calendar cal = Calendar.getInstance();
		
		int month = cal.get(Calendar.MONTH)+1; //zero-based
		
		int currentYear = cal.get(Calendar.YEAR);
		int acaYear = Integer.parseInt(getCurrentAcademicYear());
		
		//log.debug("StudentAppealAction - currentYear: " + currentYear+", acaYear: " + acaYear+", month="+month);
		
		if (currentYear < acaYear){
			acaPeriod = "1";
		}else{
			if (month < 8 && month > 3){
				acaPeriod = "2";
			}else{
				acaPeriod = "1";
			}
		}
		//log.debug("StudentAppealAction - getCurrentAcademicPeriod: " + acaPeriod);
		return acaPeriod;
	}


	public String emptySession(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		StudentAppealForm stuAppForm = (StudentAppealForm) form;
		
		//log.debug("StudentAppealAction - emptySession");
		//log.debug("StudentAppealAction - emptySession - FromPage " + stuAppForm.getFromPage());

		// Clear fields
		resetForm(stuAppForm, "StudentAppealAction - emptySession - Return applyLogin");
		stuAppForm.setApplyType("");

		setDropdownListsLogin(request,stuAppForm);
		return "applyLogin";
	}

	public ActionForward cancel(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		//log.debug("StudentAppealAction - Cancel");

		StudentAppealForm stuAppFormOld = (StudentAppealForm) form;

		resetForm(stuAppFormOld, "StudentAppealAction Cancel(Action)");
		
		StudentAppealForm stuAppForm = (StudentAppealForm) form;
		StudentAppealDAO dao = new StudentAppealDAO();
		
		// Clear fields - Just in case
		resetForm(stuAppForm, "StudentAppealAction Cancel(Action)");
		stuAppForm.setWebLoginMsg("");

		ArrayList<String> dateCheck = dao.validateClosingDate(stuAppForm.getStudent().getAcademicYear());
		if (!dateCheck.isEmpty()){ //Check Dates Array
			for (int i=0; i < dateCheck.size(); i++){
				//log.debug("StudentAppealAction - Cancel - dateCheck="+dateCheck.get(i).toString());
				if ("WAPAPL".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuAppForm.getStudent().setDateWAPAPL(true);
				}
			}
		}
		/*
		if (stuAppForm.getStudent().isDateWAPAPL()){
			stuAppForm.setAllowLogin(true);
		}else{
			stuAppForm.setAllowLogin(false);
		}
		
		//log.debug("StudentAppealAction - Cancel - validateClosingDate - AcademicYear="+ stuAppForm.getStudent().getAcademicYear() + ",  AcademicPeriod=" + stuAppForm.getStudent().getAcademicPeriod());

		stuAppForm.setWebLoginMsg("");
		
		//log.debug("StudentAppealAction -Cancel - validateClosingDate: AcademicYear="+ stuAppForm.getStudent().getAcademicYear() + ",  AcademicPeriod=" + stuAppForm.getStudent().getAcademicPeriod());

		setDropdownListsLogin(request,stuAppForm);
		//log.debug("StudentAppealAction - Cancel - Return applyLogin");
		return mapping.findForward("applyLogin");
		*/
		return new ActionForward("http://applications.unisa.ac.za/index.html",true);
	}
	
	public ActionForward quit(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		//log.debug("StudentAppealAction - Quit");
		StudentAppealForm stuAppForm = new StudentAppealForm();
		
		StudentAppealDAO dao = new StudentAppealDAO();
		
		// Clear fields - Just in case
		resetForm(stuAppForm, "StudentAppealAction Quit(Action)");
		
		stuAppForm.setWebLoginMsg("");
	
		ArrayList<String> dateCheck = dao.validateClosingDate(stuAppForm.getStudent().getAcademicYear());
		if (!dateCheck.isEmpty()){ //Check Dates Array
			for (int i=0; i < dateCheck.size(); i++){
				//log.debug("StudentAppealAction - Quit - dateCheck=["+dateCheck.get(i).toString()+"]");
				if ("WAPAPL".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuAppForm.getStudent().setDateWAPAPL(true);
				}
			}
		}
		if (stuAppForm.getStudent().isDateWAPAPL()){
			stuAppForm.setAllowLogin(true);
		}else{
			stuAppForm.setAllowLogin(false);
		}
		
		//log.debug("StudentAppealAction - Quit - validateClosingDate: AcademicYear="+ stuAppForm.getStudent().getAcademicYear() + ",  AcademicPeriod=" + stuAppForm.getStudent().getAcademicPeriod());
		
		//log.debug("StudentAppealAction - Quit - CheckDates - WAPAPL="+stuAppForm.getStudent().isDateWAPAPL());
		
		setDropdownListsLogin(request,stuAppForm);
		//log.debug("StudentAppealAction - Quit - Return to applyLogin");
		return mapping.findForward("applyLogin");
	}

	public ActionForward unspecified(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{

		StudentAppealForm stuAppForm = (StudentAppealForm)form;
		
		setDropdownListsLogin(request,stuAppForm);
		//log.debug("StudentAppeal: unspecified method call - no value for parameter in request");
		return mapping.findForward("applyLogin");
	}
	
	public String getMonth(int month) {
	    return new DateFormatSymbols().getMonths()[month-1];
	}
	
	private void applyStatus(HttpServletRequest request,ActionForm form) throws Exception {

		StudentAppealForm stuAppForm = (StudentAppealForm)form;
		StudentAppealDAO dao = new StudentAppealDAO();
		ActionMessages messages = new ActionMessages();

		//log.debug("StudentAppealAction - applyStatus");
		
		stuAppForm.setWebLoginMsg("");
		
		try{
			stuAppForm.setSelQualCode1(dao.getAppealQual("Qual", "1", stuAppForm.getStudent().getNumber(), stuAppForm.getStudent().getAcademicYear(), stuAppForm.getStudent().getAcademicPeriod()));
			stuAppForm.setSelQualCode2(dao.getAppealQual("Qual", "2", stuAppForm.getStudent().getNumber(), stuAppForm.getStudent().getAcademicYear(), stuAppForm.getStudent().getAcademicPeriod()));
			stuAppForm.setSelSpecCode1(dao.getAppealQual("Spec", "1", stuAppForm.getStudent().getNumber(), stuAppForm.getStudent().getAcademicYear(), stuAppForm.getStudent().getAcademicPeriod()));
			stuAppForm.setSelSpecCode2(dao.getAppealQual("Spec", "2", stuAppForm.getStudent().getNumber(), stuAppForm.getStudent().getAcademicYear(), stuAppForm.getStudent().getAcademicPeriod()));
			
			//log.debug("StudentAppealAction - applyStatus - " + stuAppForm.getStudent().getNumber() +" - Qual1     = " + stuAppForm.getSelQualCode1());
			//log.debug("StudentAppealAction - applyStatus - " + stuAppForm.getStudent().getNumber() +" - Qual2     = " + stuAppForm.getSelQualCode2());
			//log.debug("StudentAppealAction - applyStatus - " + stuAppForm.getStudent().getNumber() +" - Spec1     = " + stuAppForm.getSelSpecCode1());
			//log.debug("StudentAppealAction - applyStatus - " + stuAppForm.getStudent().getNumber() +" - Spec2     = " + stuAppForm.getSelSpecCode2());

			if ((stuAppForm.getSelQualCode1() == null || "Not Found".equalsIgnoreCase(stuAppForm.getSelQualCode1())) && (stuAppForm.getSelQualCode2() == null || "Not Found".equalsIgnoreCase(stuAppForm.getSelQualCode2()))){
				//log.debug("StudentAppealAction - applyStatus - QualCode1 NOT Found!!!");	
				messages.add(ActionMessages.GLOBAL_MESSAGE,
	        			new ActionMessage("message.generalmessage", "No Application or Qualification found"));
				addErrors(request, messages);
			}else{
				//Appeal & Offer Appeal
				String appeal1 = dao.getApplyStatus(stuAppForm.getStudent().getNumber(), stuAppForm.getStudent().getAcademicYear(), stuAppForm.getStudent().getAcademicPeriod(), "1");
				//log.debug("StudentAppealAction - applyStatus - Appeal1="+appeal1);
				stuAppForm.setQualStatusCode1(appeal1);
				if (appeal1 != null && !"".equals(appeal1)){
					stuAppForm.setQualStatus1(dao.getAppealDesc(appeal1));
					//log.debug("StudentAppealAction - applyStatus - Appeal1 Desc="+stuAppForm.getQualStatus1());					
				}
				
				if (stuAppForm.getSelQualCode2() != null && !"Not Found".equalsIgnoreCase(stuAppForm.getSelQualCode2())){
					String appeal2 = dao.getApplyStatus(stuAppForm.getStudent().getNumber(), stuAppForm.getStudent().getAcademicYear(), stuAppForm.getStudent().getAcademicPeriod(), "2");
					stuAppForm.setQualStatusCode2(appeal2);
					//log.debug("StudentAppealAction - applyStatus - Appeal2="+appeal2);
					if (appeal2 != null && !"".equals(appeal2)){
						stuAppForm.setQualStatus2(dao.getAppealDesc(appeal2));
						//log.debug("StudentAppealAction - applyStatus - Appeal2 Desc="+stuAppForm.getQualStatus2());
					}
				}
				
				//Offer Reason
				if ("AX".equalsIgnoreCase(stuAppForm.getQualStatusCode1())){
					String reason1 = dao.getDeclineReason(stuAppForm.getStudent().getAcademicYear(), stuAppForm.getStudent().getAcademicPeriod(), stuAppForm.getStudent().getNumber(), stuAppForm.getSelQualCode1());
					stuAppForm.setQualStatus1Reason(reason1);
					//log.debug("StudentAppealAction - applyStatus - Reason1="+reason1);
				}	
				
				if (stuAppForm.getSelQualCode2() != null && !"Not Found".equalsIgnoreCase(stuAppForm.getSelQualCode2())){
					if ("AX".equalsIgnoreCase(stuAppForm.getQualStatusCode2())){
						String reason2 = dao.getDeclineReason(stuAppForm.getStudent().getAcademicYear(), stuAppForm.getStudent().getAcademicPeriod(), stuAppForm.getStudent().getNumber(), stuAppForm.getSelQualCode1());
						stuAppForm.setQualStatus2Reason(reason2);
						//log.debug("StudentAppealAction - applyStatus - Reason2="+reason2);
					}
				}
			}
		}catch(Exception e){
			log.warn("StudentAppealAction - applyStatus - crashed / " + e);
		}
	}

	public ActionForward appealUpload(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//log.debug("StudentAppealAction - appealUpload - Start");
		
		//log.debug("StudentAppealAction - appealUpload - Button Next="+request.getParameter("Next")+", Upload="+request.getParameter("Upload"));

		ActionMessages messages = new ActionMessages();
		StudentAppealForm stuRegForm = (StudentAppealForm) form;
		SavedDocDao docDao = new SavedDocDao();
		
		 //Reset web messages
		 stuRegForm.setWebUploadMsg("");
		 stuRegForm.setWebLoginMsg("");
	    
    	stuRegForm.setAppealSelect1(stripXSS(stuRegForm.getAppealSelect1()));
    	stuRegForm.setAppealSelect2(stripXSS(stuRegForm.getAppealSelect2()));
	    stuRegForm.setAppealText(stripXSS(request.getParameter("typeText").trim()));
	
	    if(request.getParameter("Next") != null) {
			// Check inputs
			if (!"Y".equalsIgnoreCase(stuRegForm.getAppealSelect1()) && !"".equalsIgnoreCase(stuRegForm.getAppealSelect1()) && !"Y".equalsIgnoreCase(stuRegForm.getAppealSelect2()) && !"".equalsIgnoreCase(stuRegForm.getAppealSelect2())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Please indicate which qualification decision you wish to appeal."));
				addErrors(request, messages);
				setDropdownListsAppeal(request,stuRegForm);
				return mapping.findForward("appealSelect");
			}
			if (stuRegForm.getAppealText() == null || "".equalsIgnoreCase(stuRegForm.getAppealText().trim())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Please motivate your decision to appeal."));
				addErrors(request, messages);
				setDropdownListsAppeal(request,stuRegForm);
				return mapping.findForward("appealSelect");
			}
	    	//log.debug("StudentAppealAction - appealUpload - Continue to Declaration............");
	    	return mapping.findForward("appealDeclare");
	    }else{
			//log.debug("StudentAppealAction - appealUpload - Doing File Upload............");
			//log.debug("StudentAppealAction - appealUpload - Checking Number of Files="+stuRegForm.getFileCount());
			//Check that no more than 5 files are  uploaded
	    	if (stuRegForm.getFileCount() > 4){ //Counting 0 to 4 
	    		messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "You have reached the maximum of 5 Files that may be uploaded."));
				addErrors(request, messages);
				//log.debug("StudentAppealAction - appealUpload - Appeal File - UploadFile - Maximum 5 files reached");
				setDropdownListsAppeal(request,stuRegForm);
				return mapping.findForward("appealSelect");
	    	}else{
				//Validate File Name and Size
				FormFile file = stuRegForm.getFile();
				String typeSelect = stripXSS(request.getParameter("fileType"));
				String docCode = "UG00";
				String docType = "DSAR31";
				if (typeSelect != null && !"".equals(typeSelect.trim()) && typeSelect.contains("@") && typeSelect.length() > 0){
					int pos = typeSelect.indexOf("@");
					docCode = typeSelect.substring(0,pos);
					docType = typeSelect.substring(pos+1,typeSelect.length());
				}else{
					messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "File upload type was not selected. Please try again."));
					addErrors(request, messages);
					//log.debug("StudentAppealAction - appealUpload - Appeal File - UploadAction - UploadFile- appealUploadloaded file type unknown");
					setDropdownListsAppeal(request,stuRegForm);
					return mapping.findForward("appealSelect");
				}
				if (stuRegForm.getFile() != null){
					//Get Motivation to re-display after upload
					//log.debug("StudentAppealAction - appealUpload - UploadFile - Validate Optional File");
					if (file != null && file.getFileName() != null
							&& file.getFileName().trim().length() > 0) {
						//Enable this if you wish to validate for optional documents
						if( "-1".equals(docCode)){
							messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage", "Please select a document type from the list"));
							addErrors(request, messages);
							//log.debug("StudentAppealAction - appealUpload  - UploadFile - No File Type Selected");
							setDropdownListsAppeal(request,stuRegForm);
							return mapping.findForward("appealSelect");
						}else{
							//log.debug("StudentAppealAction - appealUpload - UploadFile - File Type is OK");
						}
						int size = file.getFileSize();
						//log.debug("StudentAppealAction - appealUpload - File: " + file.getFileName() + " & size = " + size);
						String readFileSize = readableFileSize(size);
						//log.debug("StudentAppealAction - appealUpload - validate " + request.getSession().getId() + " N " + stuRegForm.getStudent().getNumber() + " Upload Optional: " + file.getFileName() + " size = " + readFileSize);
						if (readFileSize.contains("Error"))	{
							messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage", readFileSize));
							addErrors(request, messages);
							setDropdownListsAppeal(request,stuRegForm);
							return mapping.findForward("appealSelect");
						}else {
							if (size > 2097152) {// check file size,  not greater than 2 MB (2097152) measured in BYTES!
								//log.debug("StudentAppealAction - appealUpload - Validate " + request.getSession().getId() + " N " + stuRegForm.getStudent().getNumber() + " Upload Optional: " + file.getFileName() + " size = " + readFileSize + " File size too large");
								messages.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("message.generalmessage", "File cannot be larger than 2MB (2048K). Your file is "+readFileSize));
								addErrors(request, messages);
								//log.debug("StudentAppealAction - appealUpload - UploadFile - File larger than 2MB");
								setDropdownListsAppeal(request,stuRegForm);
								return mapping.findForward("appealSelect");
							}else {
								//log.debug("StudentAppealAction - appealUpload - UploadFile - File Validation size OK...............");
							}
						}
						String name = file.getFileName().toLowerCase();
						String fileExtension = name.substring(name.lastIndexOf(".")+1);
						//log.debug("StudentAppealAction - appealUpload - Appeal File - Uploading - fileExtension:" + fileExtension + "............");
						if (!name.endsWith(".doc") && !name.endsWith(".docx") && !name.endsWith(".pdf") && !name.endsWith(".tif") && !name.endsWith(".tiff")) {
							//log.debug("StudentAppealAction validate " + request.getSession().getId() + " N " + stuRegForm.getStudent().getNumber() + " Upload Optional: " + file.getFileName() + " File wrong type ");
							messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage", "Only doc, docx, pdf, tif and tiff files are allowed"));
							addErrors(request, messages);
							//log.debug("StudentAppealAction - appealUpload - UploadFile - Appeal File File wrong type");
							setDropdownListsAppeal(request,stuRegForm);
							return mapping.findForward("appealSelect");
						}else if (!"doc".equalsIgnoreCase(fileExtension) && !"docx".equalsIgnoreCase(fileExtension) && !"pdf".equalsIgnoreCase(fileExtension) && !"tif".equalsIgnoreCase(fileExtension) && !"tiff".equalsIgnoreCase(fileExtension)) {
								//log.debug("StudentAppealAction validate " + request.getSession().getId() + " N " + stuRegForm.getStudent().getNumber() + " Upload Optional: " + file.getFileName() + " File wrong type ");
								messages.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("message.generalmessage", "Only doc, docx, pdf, tif and tiff files are allowed"));
								addErrors(request, messages);
								//log.debug("StudentAppealAction - appealUpload - UploadFile - Appeal File File wrong type (2)");
								setDropdownListsAppeal(request,stuRegForm);
								return mapping.findForward("appealSelect");
						}else {
							//log.debug("StudentAppealAction - appealUpload - UploadFile - File Validation Extension OK...............");
						}
					}
				} //End of Validation
				
				//Rename and Upload File
				if (file != null && file.getFileName() != null && file.getFileName().trim().length() > 0) {
					int size = file.getFileSize();
					//log.debug("StudentAppealAction - appealUpload - Appeal File - Uploading - File size: " +size+"............");
						
					//save file
					//String dir = servlet.getServletContext().getRealPath("/upload");
					String appDir = ServerConfigurationService.getString("application.path");
					//log.debug("StudentAppealAction - appealUpload - Appeal File - Uploading - FileBean - application.path:" + appDir + " ............");
						
					String stuGroupDir = stuRegForm.getStudent().getNumber().toString().substring(0, 1);
					String stuDir = stuRegForm.getStudent().getNumber().toString();
				    	
					String filePath = appDir + "/tmp/" + stuGroupDir + "/" + stuDir;
		
					//log.debug("StudentAppealAction - appealUpload - Appeal File - Uploading - FileBean -  upload dest path:" + filePath + " ............");
						
					File folder = new File(filePath);
					if(!folder.exists()){
						folder.mkdirs();
					}
					
					String writeFile = "";
					writeFile = file.getFileName();
			
					StudentFile stuFile = new StudentFile();
					stuFile.setFileName(writeFile);
					
					//log.debug("StudentAppealAction - appealUpload - Appeal File - Uploading - fileDesc Before Rename:" + writeFile + "............");
		
					//log.debug("StudentAppealAction - appealUpload - Appeal File - Uploading - fileDesc Code:" + docCode + "............");
					
					//log.debug("StudentAppealAction - appealUpload - Appeal File - Uploading - fileDesc Type:" + docType + "............");
						
					String fileExtension = writeFile.substring(writeFile.lastIndexOf(".")+1);
					//log.debug("StudentAppealAction - appealUpload - Appeal File - Uploading - fileExtension:" + fileExtension + "............");
						
					String time = (new java.text.SimpleDateFormat("hhmmssss").format(new java.util.Date()).toString());
					//log.debug("StudentAppealAction - appealUpload - Appeal File - Uploading - time:" + time + "............");
						
					String newFileName = stuRegForm.getStudent().getNumber()+"_"+ docType + "_"+ time +"." + fileExtension;
					stuFile.setNewFileName(newFileName);
					
					// set filename to changed name for workflow purposes
					//log.debug("StudentAppealAction - appealUpload - Appeal File - Uploading - After Rename:" + newFileName + "............");
						
					String fullFileName = filePath+System.getProperty("file.separator")+newFileName;
					String success = "None";
					success = uploadFile(fullFileName, file.getInputStream());
		
					File testFile = new File(fullFileName);
					//log.debug("StudentAppealAction - appealUpload - Appeal File - Check if File Exists="+testFile.exists());

					if(testFile.exists() && success.equalsIgnoreCase("Success")){
		
						//Add different file attributes to arrays for original file name, type and finally renamed file for workflow
						stuRegForm.getAppealSourceFiles().add(writeFile);
						stuRegForm.getAppealTypeFiles().add(docType);
						stuRegForm.getAppealWorkflowFiles().add(newFileName);
						//log.debug("StudentAppealAction - appealUpload - Appeal File - List - Type="+docType+", Sournce="+writeFile+", Workflow="+newFileName);
						
						//log.debug("StudentAppealAction - appealUpload - Appeal File - Appeal File exists - Saving to Database");
						SavedDoc optionDoc = new SavedDoc();
						optionDoc.setDocCode(docCode);
						optionDoc.setDocName(file.getFileName());
						//log.debug("StudentAppealAction - appealUpload - Appeal File - Calling addSavedDoc 2 - optionDoc:" + optionDoc + "............");
						docDao.addSavedDocSTUAPD(optionDoc,"N",stuRegForm.getStudent().getNumber(),stuRegForm.getStudent().getAcademicYear(),stuRegForm.getStudent().getAcademicPeriod(),"2");
						docDao.addSavedDocSTUXML(optionDoc,"N",stuRegForm.getStudent().getNumber(),stuRegForm.getStudent().getAcademicYear(),stuRegForm.getStudent().getAcademicPeriod(),"2");
						//log.debug("StudentAppealAction - appealUpload - Appeal File - Check File Number Before Exit="+stuRegForm.getFileCount());
						stuRegForm.setFileCount(stuRegForm.getFileCount() + 1);
						//log.debug("StudentAppealAction - appealUpload - Appeal File - Check File Number After Exit="+stuRegForm.getFileCount());
					}else {
						//Uploaded file not found on Filesystem
						messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "File upload failed, was interrupted or could not be completed. Please try again."));
						addErrors(request, messages);
						//log.debug("StudentAppealAction - appealUpload - Appeal File - UploadAction - UploadFile - Uploaded file not found on filesystem "+ success);
						setDropdownListsAppeal(request,stuRegForm);
						return mapping.findForward("appealSelect");
					}
				}else{
					//log.debug("StudentAppealAction - appealUpload - Appeal File - UploadAction file " + request.getSession().getId() + " / " + stuRegForm.getStudent().getNumber() + " Uploading appeal filename is null" );
					if(request.getParameter("Next") == null){ //Only log null if doing upload, not continue
						//log.debug("StudentAppealAction - appealUpload - Appeal File - UploadAction appeal file " + request.getSession().getId() + " / " + stuRegForm.getStudent().getNumber() + " Uploading appeal filename is null" );
						//No File Selected for Upload
						messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "No File was selected for upload. Please try again or Continue if you don't wish to upload any documents"));
						addErrors(request, messages);
						setDropdownListsAppeal(request,stuRegForm);
						return mapping.findForward("appealSelect");
					}
				}
				//Debug
				/*
				log.debug("StudentAppealAction - writeWorkflowAppeal - No of: AppealTypeFiles" + stuRegForm.getAppealTypeFiles().size()+", AppealSourceFiles" + stuRegForm.getAppealSourceFiles().size()+", AppealWorkflowFiles" + stuRegForm.getAppealWorkflowFiles().size());
	
				if (stuRegForm.getAppealTypeFiles().size() > 0){
					for (int i = 0; i < stuRegForm.getAppealTypeFiles().size(); i++){
						log.debug("StudentAppealAction - appealUpload - Appeal File - Done - Type File "+i+" - File=" + stuRegForm.getAppealTypeFiles().get(i).toString());
					}
				}	
				if (stuRegForm.getAppealWorkflowFiles().size() > 0){
					for (int i = 0; i < stuRegForm.getAppealWorkflowFiles().size(); i++){
						log.debug("StudentAppealAction - appealUpload - Appeal File - Done - Workflow File "+i+" - File=" + stuRegForm.getAppealWorkflowFiles().get(i).toString());
					}
				}
				if (stuRegForm.getAppealSourceFiles().size() > 0){
					for (int i = 0; i < stuRegForm.getAppealSourceFiles().size(); i++){
						log.debug("StudentAppealAction - appealUpload - Appeal File - Done - Source File "+i+" - File=" + stuRegForm.getAppealSourceFiles().get(i).toString());
					}
				}
				*/
				
				request.setAttribute("listSourceFiles", stuRegForm.getAppealSourceFiles());
				request.setAttribute("listWorkflowFiles", stuRegForm.getAppealWorkflowFiles());
				//log.debug("StudentAppealAction - appealUpload - Appeal File - Done");
				setDropdownListsAppeal(request,stuRegForm);
				return mapping.findForward("appealSelect");
	    	}
	    }
		
	}
	
	 private String uploadFile(String filePath,InputStream is) {
		 String result = "Error";
		  try {
			  
			  //log.debug("StudentAppealAction - Entering uploadFile, Filepath:"+filePath);
			  
	        OutputStream os = new FileOutputStream(filePath);

	        byte[] buffer = new byte[1024 * 16];

	        int len;
	        //Add half second delay to enable upload waiting message to initialise
	        
	        while((len=is.read(buffer))!=-1){

	            os.write(buffer,0,len);
	        }
	        
	        Thread.sleep(1000);
	        os.close();
	        os.flush();
	        is.close();
	        result = "Success";
		  } catch(Exception ex ){
			  log.warn("StudentAppealAction - Entering uploadFile - Exception="+ex);
			  result = "Error - File upload failed, please try again";
		  }
		  return result;
	 }
	 
		public String readableFileSize(long size) {
			String result = "Error";
			try{
			    if(size <= 0) return "0";
			    final String[] units = new String[] { "B", "KB", "MB", "GB", "TB" };
			    int digitGroups = (int) (Math.log10(size)/Math.log10(1024));
			    result = new DecimalFormat("#,##0.#").format(size/Math.pow(1024, digitGroups)) + " " + units[digitGroups];
			} catch (Exception e) {
				log.info("UploadAction - readableFileSize failed - Exception="+e);
				result = "Error - UploadAction - readableFileSize failed";
			}
			return result;
		}

	/** Copy documents from temp dir: /data/sakai/applications/tmp
	*   to workflow dir: /data/sakai/applications
	*   path in sakai.properties
	*
	*   This method is only called if all required documents have been uploaded
	*   @author Edmund Wilschewski 2013
	**/
	public void moveDocuments(final String studentNr) throws Exception {

		StudentAppealForm stuRegForm = new StudentAppealForm();

        try {
        	String appdir = ServerConfigurationService.getString("application.path");
			//log.debug("StudentAppealAction - moveDocuments - application.path:" + appdir + " ............");
			
			String stuGroupDir = studentNr.substring(0, 1);
	    	String stuDir = studentNr;
        	String groupDir = ServerConfigurationService.getString("application.path") + "/tmp/" + stuGroupDir;
        	String sourceDir =  groupDir + "/" + stuDir;

			//log.debug("StudentAppealAction - moveDocuments - move source path:" + sourceDir + " ............");

			File dest = new File(ServerConfigurationService.getString("application.path"));
        	//log.debug("StudentAppealAction - moveDocuments - move dest path: " + dest + "/ ............");

        	File f = new File(sourceDir); // Source directory
        	//log.debug("StudentAppealAction - moveDocuments - folder=" + f);
        	
			if (!f.exists()) {
				//log.debug("StudentAppealAction - moveDocuments - move source path doesn't exist - Exiting");
			} else {
				//log.debug("StudentAppealAction - moveDocuments - move source path exists");

   	        	FilenameFilter textFilter = new FilenameFilter() {
   	        		public boolean accept(File dir, String name) {
   		        		String lowercaseName = name.toLowerCase();
   		        		//log.debug("StudentAppealAction - moveDocuments - lowercaseName: " + lowercaseName);
   		        		
   		        		if (lowercaseName.startsWith(studentNr)) {
   		        			//log.debug("StudentAppealAction - moveDocuments - lowercaseName Starts with: " + studentNr + " : true");
   		        			return true;
   		        		}else if (lowercaseName.toString().contains(studentNr)) {
   		        			//log.debug("StudentAppealAction - moveDocuments - lowercaseName Contains: " + studentNr + " : true");
   		        			return true;
   		        		} else {
    		        		//log.debug("StudentAppealAction - moveDocuments - lowercaseName Doesn't Contain: " + studentNr + " : false");
        		        	return false;
   		        		}
   	        		}
   	        	};
        	        	
   	        	File[] files = f.listFiles(textFilter);
    	        	for (File file : files) {
    	        		//log.debug("StudentAppealAction - moveDocuments - file: " + file);
        	        	File fileToBeCopied = new File(sourceDir + "/" + file);
        	        	//log.debug("StudentAppealAction - moveDocuments - fileToBeCopied: " + fileToBeCopied);
        	        	//log.debug("StudentAppealAction - moveDocuments - fileToBeCopied name: " + fileToBeCopied.getName());
        	        	File destFile = new File(dest + "/" + fileToBeCopied.getName());
        	        	//log.debug("StudentAppealAction - moveDocuments - destFile: " + destFile);
        	        	boolean success = file.renameTo(destFile);
        	        	if (success) {
        	        		//log.debug("File " + file + " was successfully moved.\n");
        	        	} else {
        	       			log.error("File " + file + " was not successfully moved. It can be because file with file name already exists in destination\n");
        	       		}
        	       	}
        	       	//2014 Edmund
                    //Check if student and group folders are empty. 
                    //Delete them if they are as we don't want to clutter the Unix filesystem unnecessary
                    //First do Student specific folder if it is empty (/tmp/group/studentnr)
                    File testStuDir = new File (sourceDir);
                    if (testStuDir.isDirectory()) {
                    	String[] children = testStuDir.list(); //Create list of files in folder
                    	/**Use if you wish to delete files in folder
                    	 * At this time, we however don't want to delete the folder if it is not empty
                    	 */
                    	//for (int i=0; i<children.length; i++) {
                    	//	boolean success = deleteDir(new File(testStuDir, children[i]));
                    	//	if (!success) {
                    	//		return false;
                    	//	}
                    	if (children.length == 0){ //folder empty
                    		testStuDir.delete();
                    		//log.debug("StudentAppealAction - moveDocuments - StuDir Empty, thus Deleted: " + testStuDir);
                    		//log.debug("StudentAppealAction - moveDocuments " + stuRegForm.getStudent().getNumber() + " - StuDir Empty, thus Deleted: " + testStuDir);
                    	}else{
                    		//log.debug("StudentAppealAction - moveDocuments - StuDir not empty: " + testStuDir + ":" + children.length);
                    	}
                    }
                    //Now do Group Folder if it is empty (/tmp/group)
                    File testGroupDir = new File (groupDir);
                    if (testGroupDir.isDirectory()) {
                    	String[] children = testGroupDir.list(); //Create list of files in folder
                    	/**Use if you wish to delete files in folder
                    	 * At this time, we however don't want to delete the folder if it is not empty
                    	 */
                    	//for (int i=0; i<children.length; i++) {
                    	//	boolean success = deleteDir(new File(dir, children[i]));
                    	//	if (!success) {
                    	//		return false;
                    	//	}
                    	if (children.length == 0){ //folder empty
                    		testGroupDir.delete();
                    		//log.debug("StudentAppealAction - moveDocuments - GroupDir Empty, thus Deleted: " + testGroupDir);
                    		log.error("StudentAppealAction moveDocuments " + stuRegForm.getStudent().getNumber() + " - GroupDir Empty, thus Deleted: " + testGroupDir);
                    	}else{
                    		//log.debug("StudentAppealAction - moveDocuments - GroupDir not empty: " + testGroupDir + ":" + children.length);
                    	}
                    }
			}
        } catch (Exception ex) {
        		// write log?
        		throw ex;
    	} finally {
    	}

		//resetForm((StudentAppealForm) stuRegForm, "StudentAppealAction - moveDocuments");

	}

   /************ basic validation ************/
   public String getBrowserInfo(String stuBrowserAgent) throws Exception {
	       String  browserDetails  =  stuBrowserAgent;
	       String  userAgent       =  browserDetails;
	       String  user            =  userAgent.toLowerCase();
	       String browser = "";
	       String os = "";
	       
	       //log.debug("StudentAppealAction - getBrowserInfo - stuBrowserAgent="+stuBrowserAgent);

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
	        //log.debug("StudentAppealAction - getBrowserInfo - Operating System="+os);
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
	       //log.debug("StudentAppealAction - getBrowserInfo - Browser Name="+browser);
	       
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

		  //log.debug("StudentAppealAction -----------------------------------------------------------------");
		  //log.debug("StudentAppealAction - getAllRequestParamaters - Start");
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
		  //log.debug("StudentAppealAction - getAllRequestParamaters - End");
		  //log.debug("StudentAppealAction -----------------------------------------------------------------");
	  } 
	  
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

    	/** Edmund Enumerate through all parameters received
	  	 * @return **/
	    //getAllRequestParamaters(request, response);
    	
		//log.debug("StudentAppealAction - Execute - Action="+request.getParameter("act"));

        return super.execute(mapping, form, request, response);
   }
}

