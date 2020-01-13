package za.ac.unisa.lms.tools.studentupload.actions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.security.CodeSource;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.io.FileUtils;
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

import za.ac.unisa.lms.tools.studentupload.bo.SavedDoc;
import za.ac.unisa.lms.tools.studentupload.dao.SavedDocDao;
import za.ac.unisa.lms.tools.studentupload.dao.StudentUploadDAO;
import za.ac.unisa.lms.tools.studentupload.forms.FileBean;
import za.ac.unisa.lms.tools.studentupload.forms.Student;
import za.ac.unisa.lms.tools.studentupload.forms.StudentFile;
import za.ac.unisa.lms.tools.studentupload.forms.StudentUploadForm;
import Staae05h.Abean.Staae05sAppAdmissionEvaluator;

public class StudentUploadAction extends LookupDispatchAction {
	
    /** Our log (commons). */
	public static Log log = LogFactory.getLog(StudentUploadAction.class);
	
    /** Session attribute to test the state of the session */
    public static final String SESSION_STATE = "sakai.struts.tool.session.state";
    
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
	    map.put("applyUpload", "applyUpload");
	    map.put("uploadInput", "uploadInput");
	    map.put("upload", "upload");
    
	    return map;
	 }
	
	public void reset(ActionMapping mapping, HttpServletRequest request) throws Exception {

		//log.debug("StudentUploadAction - Reset - Initializing Action Form"); 
		StudentUploadForm stuUpForm =  new StudentUploadForm();
		resetForm(stuUpForm, "StudentUploadAction - Reset");
		
	}
	
	public ActionForward walkthrough(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		//log.debug("StudentUploadAction - IN walkthrough");
	    
		ActionMessages messages = new ActionMessages();
		StudentUploadForm stuUpForm = (StudentUploadForm) form;
		//StudentUploadForm stuUpForm =  new StudentUploadForm();
		
		resetForm(stuUpForm, "StudentUploadAction - WalkThrough");
		stuUpForm.setSelectReset("");
		stuUpForm.setWebLoginMsg("");
		
		//Write version number to log to check all servers
		//log.debug("StudentUploadAction - Applications Version="+stuUpForm.getVersion());
		
		//log.debug("StudentUploadAction - walkthrough - sessionID=" + request.getSession().getId());

		
		if (stuUpForm.getStudent().getAcademicYear() == null){
			stuUpForm.getStudent().setAcademicYear(getCurrentAcademicYear());
			stuUpForm.getStudent().setAcademicPeriod(getCurrentAcademicPeriod());
		}
			
		StudentUploadDAO dao = new StudentUploadDAO();
		if (stuUpForm.getStudent().getAcademicYear() == null){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", "Academic year invalid, please log on again to retry."));
			addErrors(request, messages);
			return mapping.findForward("loginSelect");
		}
		//log.debug("StudentUploadAction - walkthrough - dateCheck");

		ArrayList<String> dateCheck = dao.validateClosingDate(stuUpForm.getStudent().getAcademicYear());
		if (!dateCheck.isEmpty()){ //Check Dates Array
			for (int i=0; i < dateCheck.size(); i++){
				//log.debug("StudentUploadAction - walkthrough - dateCheck="+dateCheck.get(i).toString());
				if ("WAPSTAT".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuUpForm.getStudent().setDateWAPSTAT(true);
				}
			}
		}
		
		//log.debug("StudentUploadAction - walkthrough - AcademicYear="+stuUpForm.getStudent().getAcademicYear());
		
		stuUpForm.getStudent().setNumber("");
		stuUpForm.getStudent().setSurname("");
		stuUpForm.getStudent().setFirstnames("");
		stuUpForm.getStudent().setBirthYear("");
		stuUpForm.getStudent().setBirthMonth("");
		stuUpForm.getStudent().setBirthDay("");
		
		setDropdownListsLogin(request,stuUpForm);
		//log.debug("StudentUploadAction - walkthrough - Return to applyLogin");
		return mapping.findForward("applyLogin");
	}

	public ActionForward applyLogin(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,	HttpServletResponse response)
			throws Exception {

		//log.debug("StudentUploadAction - applyLogin - IN applyLogin");
	    
		ActionMessages messages = new ActionMessages();
		StudentUploadForm stuUpForm = (StudentUploadForm) form;
		GeneralMethods gen = new GeneralMethods();
		StudentUploadDAO dao = new StudentUploadDAO();
		
		//log.debug("StudentUploadAction applyLogin " + request.getSession().getId() + " N " + stuUpForm.getStudent().getNumber() + " S " + stuUpForm.getStudent().getSurname() + " F " + stuUpForm.getStudent().getSurname() + " Y " + stuUpForm.getStudent().getBirthYear() + " M " + stuUpForm.getStudent().getBirthMonth() + " D " + stuUpForm.getStudent().getBirthDay());

		//log.debug("StudentUploadAction applyLogin - AcademicYear=" + stuUpForm.getStudent().getAcademicYear());
		if (stuUpForm.getStudent().getAcademicYear() == null){
			stuUpForm.getStudent().setAcademicYear(getCurrentAcademicYear());
			stuUpForm.getStudent().setAcademicPeriod(getCurrentAcademicPeriod());
		}
		
		stuUpForm.setApplyType("F");
		stuUpForm.setFromPage("stepLoginReturn");
		
		boolean executeJP = false;
		if (executeJP==true) {
			stuUpForm.getStudent().setNumber(stripXSS(stuUpForm.getStudent().getNumber()));
			if (stuUpForm.getStudent().getNumber() == null || "".equalsIgnoreCase(stuUpForm.getStudent().getNumber())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Enter student number."));
				addErrors(request, messages);
				setDropdownListsLogin(request,stuUpForm);
				//log.debug("StudentUploadAction - applyLogin - Input Required - Return to applyLogin");
				return mapping.findForward("applyLogin");
			}
			if (!isValidNumber(stuUpForm.getStudent().getNumber())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Enter a valid student number."));
				addErrors(request, messages);
				setDropdownListsLogin(request,stuUpForm);
				//log.debug("StudentUploadAction - applyLogin - Input Required - Return to applyLogin");
				return mapping.findForward("applyLogin");
			}
			/** Check the code against the flow diagram by the numbers provided **/
			/** Flow Check: (1) **/
			// We already check for 7 series numbers in JSP and SQL, but double-check if Student number = 8 characters and if 7 student, don't let them in...
			// Student should never get this far with a 7 series number, but we block him anyway.
			if (stuUpForm.getStudent().getNumber().length() == 8 ){ 
				//log.debug("check if Student number = 8 characters");
				if ("7".equalsIgnoreCase(stuUpForm.getStudent().getNumber().substring(0,1))){
					stuUpForm.getStudent().setStuSLP(true);
				}
			}else if (stuUpForm.getStudent().getNumber().length() == 7 ){
				// If student no is only 7 characters (old), then add a 0 to the beginning
				//log.debug("StudentUploadAction - applyLogin - 7 char Student Number - Old Number: " + stuUpForm.getStudent().getNumber());
				stuUpForm.getStudent().setNumber("0" + stuUpForm.getStudent().getNumber());
				//log.debug("StudentUploadAction - applyLogin - 7 char Student Number - New Number: " + stuUpForm.getStudent().getNumber());
			}
			//log.debug("StudentUploadAction - applyLogin - After check if Student number = 8 characters");
		}
		
		//Johanet 2018July BRD 
		if (stuUpForm.getStudent().getNumber() != null && !"".equalsIgnoreCase(stuUpForm.getStudent().getNumber())){
			if (!isValidNumber(stuUpForm.getStudent().getNumber())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Enter a valid student number."));
				addErrors(request, messages);
				setDropdownListsLogin(request,stuUpForm);
				//log.debug("StudentUploadAction - applyLogin - Input Required - Return to applyLogin");
				return mapping.findForward("applyLogin");
			}		
		}
		//END Johanet 2018July BRD 

		stuUpForm.getStudent().setSurname(stripXSS(stuUpForm.getStudent().getSurname()));
		if (stuUpForm.getStudent().getSurname() == null || "".equalsIgnoreCase(stuUpForm.getStudent().getSurname())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Enter student surname."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuUpForm);
			//log.debug("StudentUploadAction - applyLogin - Input Required - Return to applyLogin");
			return mapping.findForward("applyLogin");
		}
		if (!isNameValid(stuUpForm.getStudent().getSurname())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Enter a valid student surname."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuUpForm);
			//log.debug("StudentUploadAction - applyLogin - Input Required - Return to applyLogin");
			return mapping.findForward("applyLogin");
		}

		if (stuUpForm.getStudent().getSurname() == null || "".equalsIgnoreCase(stuUpForm.getStudent().getSurname())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Enter student first names."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuUpForm);
			//log.debug("StudentUploadAction - applyLogin - Input Required - Return to applyLogin");
			return mapping.findForward("applyLogin");
		}
		stuUpForm.getStudent().setSurname(stripXSS(stuUpForm.getStudent().getSurname()));
		if (!isNameValid(stuUpForm.getStudent().getSurname())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Enter a valid student first names."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuUpForm);
			//log.debug("StudentUploadAction - applyLogin - Input Required - Return to applyLogin");
			return mapping.findForward("applyLogin");
		}
		stuUpForm.getStudent().setBirthYear(stripXSS(stuUpForm.getStudent().getBirthYear()));
		stuUpForm.getStudent().setBirthYear(stuUpForm.getStudent().getBirthYear().trim());
		if (stuUpForm.getStudent().getBirthYear() == null){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Year) is blank - must be numeric format [CCYY]."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuUpForm);
			//log.debug("StudentUploadAction - applyLogin - Input Required - Return to applyLogin");
			return mapping.findForward("applyLogin");
		}
		if ("".equals(stuUpForm.getStudent().getBirthYear().trim())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Year) is empty - must be numeric format [CCYY]."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuUpForm);
			//log.debug("StudentUploadAction - applyLogin - Input Required - Return to applyLogin");
			return mapping.findForward("applyLogin");
		}
		if (!gen.isNumeric(stuUpForm.getStudent().getBirthYear())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Year) must be numeric format [CCYY]."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuUpForm);
			//log.debug("StudentUploadAction - applyLogin - Input Required - Return to applyLogin");
			return mapping.findForward("applyLogin");
		}
		if (Integer.parseInt(stuUpForm.getStudent().getBirthYear()) <= 1900 || Integer.parseInt(stuUpForm.getStudent().getBirthYear()) >= Integer.parseInt(stuUpForm.getStudent().getAcademicYear())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Year) invalid."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuUpForm);
			//log.debug("StudentUploadAction - applyLogin - Input Required - Return to applyLogin");
			return mapping.findForward("applyLogin");
		}
		if (stuUpForm.getStudent().getBirthYear().length() > 4){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Year) invalid. Please enter 4 characters or less. " ));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuUpForm);
			//log.debug("StudentUploadAction - applyLogin - Input Required - Return to applyLogin");
			return mapping.findForward("applyLogin");
		}
		stuUpForm.getStudent().setBirthMonth(stripXSS(stuUpForm.getStudent().getBirthMonth()));
		stuUpForm.getStudent().setBirthMonth(stuUpForm.getStudent().getBirthMonth().trim());
		if (stuUpForm.getStudent().getBirthMonth() == null){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Month) is blank - must be numeric format [MM]."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuUpForm);
			//log.debug("StudentUploadAction - applyLogin - Input Required - Return to applyLogin");
			return mapping.findForward("applyLogin");
		}
		if ("".equals(stuUpForm.getStudent().getBirthMonth())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Month) is empty must be numeric format [MM]."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuUpForm);
			//log.debug("StudentUploadAction - applyLogin - Input Required - Return to applyLogin");
			return mapping.findForward("applyLogin");
		}
		if (!gen.isNumeric(stuUpForm.getStudent().getBirthMonth())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Month) must be numeric format [MM]."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuUpForm);
			//log.debug("StudentUploadAction - applyLogin - Input Required - Return to applyLogin");
			return mapping.findForward("applyLogin");
		}
		if (Integer.parseInt(stuUpForm.getStudent().getBirthMonth()) < 1 || Integer.parseInt(stuUpForm.getStudent().getBirthMonth()) > 12){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Month) invalid."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuUpForm);
			//log.debug("StudentUploadAction - applyLogin - Input Required - Return to applyLogin");
			return mapping.findForward("applyLogin");
		}
		if (stuUpForm.getStudent().getBirthMonth().length() > 2){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Month) invalid. Please enter 2 characters or less. " ));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuUpForm);
			//log.debug("StudentUploadAction - applyLogin - Input Required - Return to applyLogin");
			return mapping.findForward("applyLogin");
		}

		//2015 Edmund - Expanded Date Tests as Students entered dates like 30/02/1992 etc
		int testMonth = Integer.parseInt(stuUpForm.getStudent().getBirthMonth());
		int daysMonth = 0;
		if (testMonth == 1 || testMonth == 3 || testMonth == 5 || testMonth == 7 || testMonth == 8 || testMonth == 10 || testMonth ==12){
			daysMonth = 31;
		}else if (testMonth == 4 || testMonth == 6 || testMonth == 9 || testMonth == 11){
			daysMonth = 30;
		}else{
			daysMonth = 28; //Checking for Leap year further below...
		}
		
		stuUpForm.getStudent().setBirthDay(stripXSS(stuUpForm.getStudent().getBirthDay()));
		stuUpForm.getStudent().setBirthDay(stuUpForm.getStudent().getBirthDay().trim());
		if (stuUpForm.getStudent().getBirthDay() == null){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Day) is blank - must be numeric format [DD]."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuUpForm);
			//log.debug("StudentUploadAction - applyLogin - Input Required - Return to applyLogin");
			return mapping.findForward("applyLogin");
		}
		if ("".equals(stuUpForm.getStudent().getBirthDay())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Day) is empty - must be numeric format [DD]."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuUpForm);
			//log.debug("StudentUploadAction - applyLogin - Input Required - Return to applyLogin");
			return mapping.findForward("applyLogin");
		}
		if (!gen.isNumeric(stuUpForm.getStudent().getBirthDay())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Day) must be numeric format [DD]."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuUpForm);
			//log.debug("StudentUploadAction - applyLogin - Input Required - Return to applyLogin");
			return mapping.findForward("applyLogin");
		}
		if (Integer.parseInt(stuUpForm.getStudent().getBirthDay()) < 1 || Integer.parseInt(stuUpForm.getStudent().getBirthDay()) > 31){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Day) invalid."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuUpForm);
			//log.debug("StudentUploadAction - applyLogin - Input Required - Return to applyLogin");
			return mapping.findForward("applyLogin");
		}
		if (stuUpForm.getStudent().getBirthDay().length() > 2){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Day) invalid. Please enter 2 characters or less. " ));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuUpForm);
			//log.debug("StudentUploadAction - applyLogin - Input Required - Return to applyLogin");
			return mapping.findForward("applyLogin");
		}
		if (Integer.parseInt(stuUpForm.getStudent().getBirthDay()) < 1){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Day) invalid."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuUpForm);
			//log.debug("StudentUploadAction - applyLogin - Input Required - Return to applyLogin");
			return mapping.findForward("applyLogin");
		}
		String monthName = "";
		if (daysMonth == 31 && Integer.parseInt(stuUpForm.getStudent().getBirthDay()) > 31){
			monthName = getMonth(testMonth);
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Day) invalid. " + monthName + " only has 31 days."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuUpForm);
			//log.debug("StudentUploadAction - applyLogin - Input Required - Return to applyLogin");
			return mapping.findForward("applyLogin");
		}
		if (daysMonth == 30 && Integer.parseInt(stuUpForm.getStudent().getBirthDay()) > 30){
			monthName = getMonth(testMonth);
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Day) invalid. " + monthName + " only has 30 days."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuUpForm);
			//log.debug("StudentUploadAction - applyLogin - Input Required - Return to applyLogin");
			return mapping.findForward("applyLogin");
		}
		if (Integer.parseInt(stuUpForm.getStudent().getBirthMonth()) == 2 && Integer.parseInt(stuUpForm.getStudent().getBirthDay()) > 29){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", "Student Date of Birth invalid. " ));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuUpForm);
			//log.debug("StudentUploadAction - applyLogin - Input Required - Return to applyLogin");
			return mapping.findForward("applyLogin");
		}
		if (Integer.parseInt(stuUpForm.getStudent().getBirthMonth()) == 2 && Integer.parseInt(stuUpForm.getStudent().getBirthDay()) == 29){
			String leapCheck = dao.checkLeapYear(stuUpForm.getStudent().getBirthYear());
			if ("Not a leap year".equalsIgnoreCase(leapCheck)){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (29/February/"+stuUpForm.getStudent().getBirthYear()+") invalid. " + stuUpForm.getStudent().getBirthYear() + " was not a leap year." ));
				addErrors(request, messages);
				setDropdownListsLogin(request,stuUpForm);
				//log.debug("StudentUploadAction - applyLogin - Input Required - Return to applyLogin");
				return mapping.findForward("applyLogin");
			}
		}
		
		//Add "0" incase Student enters just one character for Birthday or Month
		if (stuUpForm.getStudent().getBirthMonth().length() < 2){
			stuUpForm.getStudent().setBirthMonth("0" + stuUpForm.getStudent().getBirthMonth());
		}
		if (stuUpForm.getStudent().getBirthDay().length() < 2){
			stuUpForm.getStudent().setBirthDay("0" + stuUpForm.getStudent().getBirthDay());
		}
		
		String errorMsg="";
		//Johanet 2018July BRD 
		//Student number entered - check personal data entered against database		
		if (stuUpForm.getStudent().getNumber() != null && !"".equalsIgnoreCase(stuUpForm.getStudent().getNumber())){
			errorMsg = displayPersonal(stuUpForm, request);
		}else {
			//Validate new Applicant - get student number		
			stuUpForm.getStudent().setSurname(stuUpForm.getStudent().getSurname().toUpperCase());
			stuUpForm.getStudent().setFirstnames(stuUpForm.getStudent().getFirstnames().toUpperCase());
			stuUpForm.getStudent().setBirthYear(stuUpForm.getStudent().getBirthYear());
			stuUpForm.getStudent().setBirthMonth(stuUpForm.getStudent().getBirthMonth());
			stuUpForm.getStudent().setBirthDay(stuUpForm.getStudent().getBirthDay());
			
			String referenceData = stuUpForm.getStudent().getSurname().toUpperCase()+","+stuUpForm.getStudent().getFirstnames().toUpperCase()+","+stuUpForm.getStudent().getBirthYear()+stuUpForm.getStudent().getBirthMonth()+stuUpForm.getStudent().getBirthDay();
			String newApplicantNumber = dao.validateNewApplicant(stuUpForm.getStudent().getAcademicYear(), stuUpForm.getStudent().getAcademicPeriod(), "TempStu" ,"StuInfo", referenceData);
			
			if (newApplicantNumber.equalsIgnoreCase("")) {
				errorMsg="No application was submitted for the student details below, either verify that the detial is correct or enter a valid student number if your are not a new applicant.";			
			}else {
				stuUpForm.getStudent().setNumber(newApplicantNumber);	
			}
		}
		
		if(!"".equals(errorMsg)){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage", errorMsg));
						addErrors(request, messages);
						setDropdownListsLogin(request,stuUpForm);
						//log.debug("StudentUploadAction - applyLogin - DisplayPersonal - Return to applyLogin");
						return mapping.findForward("applyLogin");
		}else{	
			//Johanet 2018July BRD move code after student number determined
			/** Check the code against the flow diagram by the numbers provided **/
			/** Flow Check: (1) **/
			// We already check for 7 series numbers in JSP and SQL, but double-check if Student number = 8 characters and if 7 student, don't let them in...
			// Student should never get this far with a 7 series number, but we block him anyway.
			if (stuUpForm.getStudent().getNumber().length() == 8 ){ 
				//log.debug("check if Student number = 8 characters");
				if ("7".equalsIgnoreCase(stuUpForm.getStudent().getNumber().substring(0,1))){
					stuUpForm.getStudent().setStuSLP(true);
				}
			}else if (stuUpForm.getStudent().getNumber().length() == 7 ){
				// If student no is only 7 characters (old), then add a 0 to the beginning
				//log.debug("StudentUploadAction - applyLogin - 7 char Student Number - Old Number: " + stuUpForm.getStudent().getNumber());
				stuUpForm.getStudent().setNumber("0" + stuUpForm.getStudent().getNumber());
				//log.debug("StudentUploadAction - applyLogin - 7 char Student Number - New Number: " + stuUpForm.getStudent().getNumber());
			}
			//Johanet 2018July BRD move code after student number determined
			
			//log.debug("StudentUploadAction - applyLogin - After check if Student number = 8 characters");
			//log.debug("StudentUploadAction - applyLogin - Write student details to STUXML");
			//Write student details to STUXML for later verification - Testing Thread security
			String referenceData = stuUpForm.getStudent().getNumber()+","+stuUpForm.getStudent().getSurname().toUpperCase()+","+stuUpForm.getStudent().getFirstnames().toUpperCase()+","+stuUpForm.getStudent().getBirthYear()+stuUpForm.getStudent().getBirthMonth()+stuUpForm.getStudent().getBirthDay();
			
			boolean stuError = false;
			String queryResultRef = "";
			String checkStu = "";
			int nextRef = 1;
			queryResultRef = dao.getSTUXMLRef(stuUpForm.getStudent().getNumber(), stuUpForm.getStudent().getAcademicYear(), stuUpForm.getStudent().getAcademicPeriod(), "StuInfo", "1", "applyLogin");
			//log.debug("StudentUploadAction - applyLogin - queryResultRef="+queryResultRef);
			if (queryResultRef.toUpperCase().contains("ERROR")){
				stuError = true;
			}
			if (!stuError){
				try{
					nextRef = Integer.parseInt(queryResultRef);
					nextRef++;
				}catch(Exception e){
					log.warn("StudentUploadAction - applyLogin - nextRef not Numeric - nextRef="+nextRef);
				}
				//log.debug("StudentUploadAction - applyLogin - queryResultRef="+queryResultRef+", nextRef="+nextRef);
				checkStu = dao.saveSTUXMLRef(stuUpForm.getStudent().getNumber(), stuUpForm.getStudent().getAcademicYear(), stuUpForm.getStudent().getAcademicPeriod(), "StuInfo", nextRef, referenceData, "applyLogin", "INSERT");
				if (checkStu.toUpperCase().contains("ERROR")){
					stuError = true;
				}
			}
			//log.debug("StudentUploadAction - applyLogin - Write student details to STUXML - Done - checkStu="+checkStu+", referenceData="+referenceData+", referenceSequence="+nextRef);
			
			//Get Browser Details
			//Is client behind something like a Proxy Server?
			String ipAddress = request.getHeader("X-FORWARDED-FOR");
			if (ipAddress == null) {
				   ipAddress = request.getRemoteAddr();
			}
			stuUpForm.getStudent().setStuIPAddress(ipAddress);
			stuUpForm.getStudent().setStuBrowserAgent(request.getHeader("User-Agent"));
			String browser = getBrowserInfo(stuUpForm.getStudent().getStuBrowserAgent());
			if (browser != null && !"".equals(browser.trim()) && browser.contains("@") && browser.length() > 3){
				int pos = browser.indexOf("@");
				stuUpForm.getStudent().setStuBrowser(browser.substring(0,pos));
				stuUpForm.getStudent().setStuWksOS(browser.substring(pos+1,browser.length()));
				//log.debug("StudentUploadAction - applyLogin - Write Browser details to STUXML");
				//Write Browser details to STUXML for later verification - Testing Thread security
				String browserData = stuUpForm.getStudent().getNumber()+","+stuUpForm.getStudent().getStuIPAddress()+","+stuUpForm.getStudent().getStuBrowser()+","+stuUpForm.getStudent().getStuWksOS();
				//log.debug("StudentUploadAction - applyLogin - browserData="+browserData);
				boolean browserError = false;
				String queryResultBrowser = "";
				String checkBrowser = "";
				int nextBrowser = 1;
				queryResultBrowser = dao.getSTUXMLRef(stuUpForm.getStudent().getNumber(), stuUpForm.getStudent().getAcademicYear(), stuUpForm.getStudent().getAcademicPeriod(), "WKSInfo", "1", "applyLogin");
				//log.debug("StudentUploadAction - applyLogin - queryResultRef="+queryResultBrowser);
				if (queryResultBrowser.toUpperCase().contains("ERROR")){
					browserError = true;
				}
				if (!browserError){
					try{
						nextBrowser = Integer.parseInt(queryResultBrowser);
						nextBrowser++;
					}catch(Exception e){
						log.warn("StudentUploadAction - applyLogin - nextRef not Numeric - nextRef="+nextBrowser);
					}
					//log.debug("StudentUploadAction - applyLogin - queryResultBrowser="+queryResultBrowser+", nextRef="+nextBrowser);
					checkBrowser = dao.saveSTUXMLRef(stuUpForm.getStudent().getNumber(), stuUpForm.getStudent().getAcademicYear(), stuUpForm.getStudent().getAcademicPeriod(), "WKSInfo", nextBrowser, browserData, "applyLogin", "INSERT");
					if (checkBrowser.toUpperCase().contains("ERROR")){
						browserError = true;
					}
				}
				//log.debug("StudentUploadAction - applyLogin - Write student details to STUXML - Done - checkStu="+checkBrowser+", browserData="+browserData+", referenceSequence="+nextBrowser);
			}
			
			/**
			 * Validation Test: Check if student has a academic record
			 **/
			boolean isACAExist = dao.validateStudentRec(stuUpForm.getStudent().getNumber(), stuUpForm.getStudent().getAcademicYear());
			if (isACAExist) {
				stuUpForm.getStudent().setStuExist(true);
			}else{
				stuUpForm.getStudent().setStuExist(false);
			}
			
			/** Continue to check the code against the flow diagram by the numbers provided **/
			/** Flow Check: (1) **/
			//log.debug("StudentUploadAction - applyLogin - (1) - Check STUAPQ (F851)");
			String bDay = stuUpForm.getStudent().getBirthDay() + "/" + stuUpForm.getStudent().getBirthMonth() + "/" + stuUpForm.getStudent().getBirthYear();

			boolean vSTUAPQCheck = dao.validateSTUAPQ(stuUpForm.getStudent().getSurname(), stuUpForm.getStudent().getFirstnames(), bDay, stuUpForm.getStudent().getAcademicYear());
			stuUpForm.getStudent().setStuapqExist(vSTUAPQCheck);
				
			//log.debug("StudentUploadAction - applyLogin - (IF) vSTUAPQCheck=" + vSTUAPQCheck + " - StuExist="+stuUpForm.getStudent().isStuapqExist());
			if (vSTUAPQCheck){ //Student already applied during this application period (Only allowed one application per application period)
				/** Flow Check: (2) **/
				//log.debug("StudentUploadAction - applyLogin - (2) - Student has applied during this application period");
				/** Flow Check: (3) **/
				//log.debug("StudentUploadAction - applyLogin - (3) - Student selected to go directly to Upload page");
				/** Flow Check: (4) **/
				//log.debug("StudentUploadAction - applyLogin - (4) - Redirect to Upload page");
				return mapping.findForward("documentUpload");
			} else{ //Student thus doesn't have a STUAPQ record for this Academic Year & Period
				/** Flow Check: (5) **/
				//log.debug("StudentUploadAction - applyLogin - (5) - Student thus doesn't have a STUAPQ record for this Academic Year & Period yet");
				//Check if M&D Student if so, redirect to M&D Admissions
				boolean isMDStudent = false;
				isMDStudent = dao.getMDAPPLRecord(stuUpForm.getStudent().getNumber());
				//log.debug("ApplyForStudentNumberAction - applyLogin (16c) - Returning Student - M&D Check="+isMDStudent);
				if (isMDStudent){
					/** Flow Check: (16d) **/
					//log.debug("ApplyForStudentNumberAction - applyLoginReturn (16d) - Returning Student - M&D - Goto MD Admissions");
					String serverpath = ServerConfigurationService.getServerUrl();
					return new ActionForward(serverpath+"/unisa-findtool/default.do?sharedTool=unisa.mdapplications",true);
				}else{/** Flow Check: (6) **/
					//log.debug("StudentUploadAction - applyLogin - (6) - Set Message - You do not have a current application record for this academic year.newline Click OK retry or click Cancel if you wish to quit the application process.");
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "No application record was found for you for this academic year or application period. Please use the main options to apply for a qualification change before you can use this function."));
					addErrors(request, messages);
					/** Flow Check: (7) **/
					setDropdownListsLogin(request,stuUpForm);
					//log.debug("StudentUploadAction - applyLogin - (7) - Goto applyLogin");
					return mapping.findForward("applyLogin");
				}
			}
		}
	}

	public String displayPersonal(StudentUploadForm stuUpForm, HttpServletRequest request) throws Exception {
		
		StudentUploadDAO dao = new StudentUploadDAO();
		
		//log.debug("StudentUploadAction - displayPersonal - Start");
		Student teststu = new Student();
		teststu.setNumber(stuUpForm.getStudent().getNumber().trim());
		teststu.setFirstnames(stuUpForm.getStudent().getFirstnames().trim());
		teststu.setSurname(stuUpForm.getStudent().getSurname().trim());
		teststu.setBirthDay(stuUpForm.getStudent().getBirthDay());
		teststu.setBirthMonth(stuUpForm.getStudent().getBirthMonth());
		teststu.setBirthYear(stuUpForm.getStudent().getBirthYear());

		String result = "";

		String checkNumber = dao.personalDetails(stuUpForm.getStudent().getNumber(),"number").trim();
		if ("error".equalsIgnoreCase(checkNumber)){
			result = "The student number entered is invalid. Please enter a valid number.";
			return result;
		}else{
			stuUpForm.getStudent().setNumber(checkNumber);
			//log.debug("StudentUploadAction - displayPersonal - Student Number: " + stuUpForm.getStudent().getNumber());
		}
		
		String checkSurname = dao.personalDetails(stuUpForm.getStudent().getNumber(),"surname").trim();
		if ("error".equalsIgnoreCase(checkSurname)){
			result = "The surname information was not successfully retrieved from the database";
			return result;
		}else{
			stuUpForm.getStudent().setSurname(checkSurname);
			//log.debug("StudentUploadAction - displayPersonal - Surname: " + stuUpForm.getStudent().getSurname());
		}
		
		String checkFirstnames = dao.personalDetails(stuUpForm.getStudent().getNumber(),"first_names").trim();
		if ("error".equalsIgnoreCase(checkFirstnames)){
			result = "The First name information was not successfully retrieved from the database";
			return result;
		}else{
			stuUpForm.getStudent().setFirstnames(checkFirstnames);
			//log.debug("StudentUploadAction - displayPersonal - First Names: " + stuUpForm.getStudent().getFirstnames());
		}

		String checkMaidenname = dao.personalDetails(stuUpForm.getStudent().getNumber(),"previous_surname").trim();
		if ("error".equalsIgnoreCase(checkMaidenname)){
			result = "The Maiden name information was not successfully retrieved from the database";
			return result;
		}else{
			stuUpForm.getStudent().setMaidenName(checkMaidenname);
			//log.debug("StudentUploadAction - displayPersonal - Prev Surname: " + stuUpForm.getStudent().getMaidenName());
		}
		
		String YYYYMMDD = "";
		String checkBDate = dao.personalDetails(stuUpForm.getStudent().getNumber(),"BIRTH_DATE").trim();
		if ("error".equalsIgnoreCase(checkBDate)){
			result = "The Birth Date information was not successfully retrieved from the database";
			return result;
		}else{
			YYYYMMDD = checkBDate;
			//log.debug("StudentUploadAction - displayPersonal - Birth Date: " + YYYYMMDD);
		}

		//log.debug("StudentUploadAction - displayPersonal - BirthDay: " + stuUpForm.getStudent().getBirthYear() + "/" + stuUpForm.getStudent().getBirthMonth() + "/" + stuUpForm.getStudent().getBirthDay());
		if(YYYYMMDD.length()>=8){
			//log.debug("StudentUploadAction - displayPersonal - " + request.getSession().getId() + " N " + stuUpForm.getStudent().getNumber() + " Success BirthDay " + YYYYMMDD);
			stuUpForm.getStudent().setBirthYear(YYYYMMDD.substring(0,4));
			stuUpForm.getStudent().setBirthMonth(YYYYMMDD.substring(4,6));
			stuUpForm.getStudent().setBirthDay(YYYYMMDD.substring(6,8));
		}else{
			//log.debug("StudentUploadAction - displayPersonal - " + request.getSession().getId() + " N " + stuUpForm.getStudent().getNumber() + " UnSuccess BirthDay " + YYYYMMDD);
			result = "The birth day information was not successfully retrieved from the database";
			return result;
		}
		//log.debug("StudentUploadAction - displayPersonal - BirthDay: " + stuUpForm.getStudent().getBirthYear() + "/" + stuUpForm.getStudent().getBirthMonth() + "/" + stuUpForm.getStudent().getBirthDay());

		String tmpCellNr = dao.personalDetails(stuUpForm.getStudent().getNumber(),"cell_number").trim();
		if (!"error".equalsIgnoreCase(tmpCellNr)){
			stuUpForm.getStudent().setCellNr(tmpCellNr);
		}else{
			stuUpForm.getStudent().setCellNr("");
		}
		//log.debug("StudentUploadAction - displayPersonal - Cell Number: " + stuUpForm.getStudent().getCellNr());

		//log.debug("StudentUploadAction - displayPersonal - " + request.getSession().getId() + " N " + stuUpForm.getStudent().getNumber() + " Cell " + stuUpForm.getStudent().getCellNr());
		
		String tmpEmail = dao.personalDetails(stuUpForm.getStudent().getNumber(),"email_address").trim();
		if (!"error".equalsIgnoreCase(tmpEmail)){
			stuUpForm.getStudent().setEmailAddress(tmpEmail);
		}else{
			stuUpForm.getStudent().setEmailAddress("");
		}
		//log.debug("StudentUploadAction - displayPersonal - Email Address: " + stuUpForm.getStudent().getEmailAddress());

		//log.debug("StudentUploadAction - displayPersonal - " + request.getSession().getId() + " N " + stuUpForm.getStudent().getNumber() + " Email " + stuUpForm.getStudent().getEmailAddress());
		
		// Closing date  --Integer.parseInt(String.valueOf(stuUpForm.getStudent().getAcademicYear())))){

		//Validate if screen input is correct student
		if (!stuUpForm.getStudent().getSurname().equalsIgnoreCase(teststu.getSurname())){
			result = "The surname you have entered does not correspond with the information on the database";
		}else if (!stuUpForm.getStudent().getFirstnames().equalsIgnoreCase(teststu.getFirstnames())){
			result= "The first name(s) you have entered does not correspond with the information on the database";
		}else if (!stuUpForm.getStudent().getBirthYear().equals(teststu.getBirthYear())){
			result="The birthdate you have entered does not correspond with the information on the database";
		}else if (!stuUpForm.getStudent().getBirthMonth().equals(teststu.getBirthMonth())){
			result="The birthdate you have entered does not correspond with the information on the database";
		}else if (!stuUpForm.getStudent().getBirthDay().equals(teststu.getBirthDay())){
			result="The birthdate you have entered does not correspond with the information on the database";
		}else if (!dao.validateClosingDateAll(stuUpForm.getStudent().getAcademicYear())){
			if ("AllClosed".equalsIgnoreCase(stuUpForm.getStudent().getWebOpenDate())){
				result="The application period for study at Unisa is closed.";
			}
		}

		//if (!"".equals(result)){
			//error result - move input back to screen values
			stuUpForm.getStudent().setNumber(teststu.getNumber());
			stuUpForm.getStudent().setSurname(teststu.getSurname());
			stuUpForm.getStudent().setFirstnames(teststu.getFirstnames());
			stuUpForm.getStudent().setBirthDay(teststu.getBirthDay());
			stuUpForm.getStudent().setBirthMonth(teststu.getBirthMonth());
			stuUpForm.getStudent().setBirthYear(teststu.getBirthYear());
		//}
		//if any error message =result
		//log.debug("StudentUploadAction - displayPersonal - Result: " + result);
		return result;
	}
	
	/*
	 * populates the Year dropdown. Uses JSON to return Key and Value list
	 */
	public ActionForward populateYear(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		//log.debug("StudentUploadAction - populateYear - Start");

		JSONObject yearObj = new JSONObject();
		Map<String, String> mapYear = new LinkedHashMap<String, String>();
		int keyCounter = 0;
		
		//log.debug("StudentUploadAction - populateYear - **************************************************************");

		try{
			int startYear = 1960; //Don't think student can be more than 100 years old
			
			//Ensure that End year is later than Start year
			String startYearSelected = stripXSS(request.getParameter("startYearSelected"));
			//log.debug("StudentUploadAction - populateYear - startYearSelected="+startYearSelected);
	
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
       	//log.debug("StudentUploadAction - populateYear - Final - **************************************************************");
       	//log.debug("StudentUploadAction - populateYear - Final - jsonObject="+jsonObject.toString());
       	//log.debug("StudentUploadAction - populateYear - Final - **************************************************************");
       	out.write(jsonObject.toString());
       	out.flush();

		return null; //Returning null to prevent struts from interfering with Ajax/JSON
	}
	
	
	public String doSTUXML(String StudentNr, String acaYear, String acaPeriod, String referenceType, String referenceValue, String referenceData, String callingMethod){
		
		String result = "";
		String dbMethod = "INSERT";
		boolean isError = false;
		boolean isSuccess = false;

		StudentUploadDAO dao = new StudentUploadDAO();
		
		result = dao.isSTUXML(StudentNr, acaYear, acaPeriod, referenceType, referenceValue, callingMethod);
		if (result.toUpperCase().contains("ERROR")){
			isError = true;
		}else if (result.toUpperCase().equalsIgnoreCase("TRUE")){
			dbMethod = "UPDATE";
		}
		//log.debug("StudentUploadAction - doSTUXML - " + StudentNr +" - STUXML - queryResult="+result + ", queryType="+dbMethod+ ", isError="+isError+ ", callingMethod="+callingMethod);
		
		int count = 0;
		while (!isSuccess && count < 5 && !isError){
			int nextRef = 0;
			String queryResultRef = dao.getSTUXMLRef(StudentNr, acaYear, acaPeriod, referenceType, referenceValue, "doSTUXML");
			//log.debug("StudentUploadAction doSTUXML - queryResultRef="+queryResultRef);
			if (queryResultRef.toUpperCase().contains("ERROR")){
				isError = true;
			}
			if (!isError){
				try{
					nextRef = Integer.parseInt(queryResultRef);
					nextRef++;
					//log.debug("StudentUploadAction - doSTUXML - queryResultRef="+queryResultRef+", nextRef="+nextRef+", referenceType="+referenceType+", referenceValue="+referenceValue+". referenceData="+referenceData);
					result = dao.saveSTUXML(StudentNr, acaYear, acaPeriod, referenceType, referenceValue, String.valueOf(nextRef), referenceData, callingMethod, dbMethod);
					if (result.toUpperCase().equalsIgnoreCase("TRUE")){
						isSuccess = true;
					}else if (result.toUpperCase().contains("ERROR")){
						isError = true;
					}
				}catch(Exception e){
					log.warn("StudentUploadAction doSTUXML - nextRef not Numeric - nextRef="+nextRef);
				}
			}
			count++;
			//log.debug("StudentUploadAction - doSTUXML - " + StudentNr +" - STUXML - "+dbMethod+ "Result="+result + ", count="+count+ ", isError="+isError+ ", callingMethod="+callingMethod);
		}
		return result;
	}
	
	public ActionForward uploadInput(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		StudentUploadForm stuUpForm = (StudentUploadForm) form;
		SavedDocDao dao = new SavedDocDao();
		ActionMessages messages = new ActionMessages();
		
		//log.debug("StudentUploadAction - uploadInput - Start");
		try{
		// if student number is null, block application
		if(stuUpForm.getStudent().getNumber() == null || "".equals(stuUpForm.getStudent().getNumber())){
			log.info("StudentUploadAction - uploadInput " + request.getSession().getId() + " Student Nr is null or empty when saving qualifications");
			messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", "You performed and invalid action or an Error occurred. Please log on again to retry."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuUpForm);
			//log.debug("StudentUploadAction - uploadInput - Goto applyLogin");
			return mapping.findForward("applyLogin");
		}
		
		CodeSource codeSource = FileUpload.class.getProtectionDomain().getCodeSource();
		//log.debug("StudentUploadAction uploadInput " + request.getSession().getId() + " " + stuUpForm.getStudent().getNumber() + " " + ((codeSource != null)? codeSource.getLocation():"no code source for upload" ));
		
		boolean isCGMatrix = false;
		isCGMatrix = dao.getMatricCert(stuUpForm.getStudent().getNumber());
		if (isCGMatrix){
			stuUpForm.getStudent().setMatrix("CG");
		}
		
		stuUpForm.loadData();
		}catch (Exception ex){
			//log.debug("StudentUploadAction - uploadInput An error occurred. Please try again.");
			messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", "An error occurred. Please log on again to retry."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuUpForm);
			//log.debug("StudentUploadAction - uploadInput - Goto applyLogin");
			return mapping.findForward("applyLogin");
		}
		return mapping.findForward("documentInput");
	}
	
	public ActionForward upload(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		//log.debug("StudentUploadAction - Upload - Start");
		
		ActionMessages messages = new ActionMessages();
		StudentUploadForm stuUpForm = (StudentUploadForm) form;
		SavedDocDao dao = new SavedDocDao();
		boolean isSelectedRequiredFile = false;
		boolean isSelectedOptionalFile = false;
		//boolean isSelectedOptionalType = false;
		
		 //Reset web messages
		 stuUpForm.setWebUploadMsg("");
		 stuUpForm.setWebLoginMsg("");
		
		/** Edmund Enumerate through all parameters received
	  	 * @return **/
	   // getAllRequestParamaters(request, response);
		
		//response.setHeader("cache-control", "no-cache");
		
		// if student number is null, block application
		if(stuUpForm.getStudent().getNumber() == null || "".equals(stuUpForm.getStudent().getNumber())){
			//log.debug("StudentUploadAction - Upload - " + request.getSession().getId() + " Student Nr is null or empty when saving qualifications");
			messages.add(ActionMessages.GLOBAL_MESSAGE,
			new ActionMessage("message.generalmessage", "You performed and invalid action or an Error occurred. Please log on again to retry."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuUpForm);
			//log.debug("StudentUploadAction - Upload - Goto applyLogin");
			return mapping.findForward("applyLogin");
		}
		
		if(request.getParameter("Logout") != null) {
			//log.debug("StudentUploadAction - Upload - Logout and return later");
			resetForm((StudentUploadForm) stuUpForm, "Upload1");
			stuUpForm.setSelectReset("");
			setDropdownListsLogin(request,stuUpForm);
			//log.debug("StudentUploadAction - uploadInput - Goto applyLogin");
			return mapping.findForward("applyLogin");
		}		 

		try{

			//log.debug("StudentUploadAction - Upload - Entering upload");
			
			String method = request.getMethod();
			//log.debug("StudentUploadAction - Upload - Method GET Check " + method);
					
			if ("GET".equalsIgnoreCase(method)) {
				//log.debug("StudentUploadAction - Upload - Validate - Method GET Check - Returning Null");
				return null;
			}
			
			if (stuUpForm.getFileBeans() != null){
				for (FileBean fb : stuUpForm.getFileBeans()) {
					//log.debug("StudentUploadAction - Upload - Validate - Required File ========================"+fb.getFile());
					if (fb.getFile() != null && fb.getFile().getFileName().trim().length() > 0) {
						//log.debug("StudentUploadAction - Upload - Validate - Required File - True................");
						isSelectedRequiredFile = true;
						break;
					}else{
						//File is empty
						//log.debug("StudentUploadAction - Upload - Validate - No file or file lenght 0, might be continue, not upload");
					}
				}
		
				if (!isSelectedRequiredFile && stuUpForm.isHiddenButton()) {
					//log.debug("StudentUploadAction - Upload - Validate - Required File ============"+  stuUpForm.getOptionFileBean().getFile());
					//log.debug("StudentUploadAction - Upload - isSelectedRequiredFile - isSelectedRequiredFile="+isSelectedRequiredFile);
					if (stuUpForm.getOptionFileBean().getFile() != null && stuUpForm.getOptionFileBean().getFile().getFileName().trim().length() > 0) {
						isSelectedRequiredFile = true;
						//log.debug("StudentUploadAction - Upload - Validate - Required File - True................");
					}else{
						//log.debug("StudentUploadAction - Upload - Validate - Required File - False................");
					}
				}
			}else{
				//log.debug("StudentUploadAction - Upload - Required File - Empty................");
				//log.debug("StudentUploadAction - Upload - Required File - isSelectedRequiredFile="+isSelectedRequiredFile);
			}
			
			// check the selected files one by one
			for (FileBean fb : stuUpForm.getFileBeans()) {
				FormFile file = fb.getFile();

				if (file != null && file.getFileName() != null && file.getFileName().trim().length() > 0) {
					int size = file.getFileSize();
					
					String readFileSize = readableFileSize(size);
					//log.debug("StudentUploadAction - Upload - Validate - Upload Required: " + file.getFileName() + " size = " + readFileSize);
					if (readFileSize.contains("Error"))	{
						messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", readFileSize));
						addErrors(request, messages);
						return mapping.findForward("documentInput");
					}else {
						if (size > 2097152) {// check file size,  not greater than 2 MB (2097152) measured in BYTES!
							//log.debug("StudentUploadAction - Upload - Validate " + request.getSession().getId() + " N " + stuUpForm.getStudent().getNumber() + " Upload Required: " + file.getFileName() + " size = " + readFileSize + " File size too large");
							messages.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("message.generalmessage", "File cannot be larger than 2MB (2048K). Your file is "+readFileSize));
							addErrors(request, messages);
							//log.debug("StudentUploadAction - Upload - UploadFile - File larger than 2MB");
							return mapping.findForward("documentInput");
						}else {
							//log.debug("StudentUploadAction - Upload - UploadFile - Required File size OK...............");
						}
					}
					String name = file.getFileName().toLowerCase();
					//log.debug("StudentUploadAction - Upload - UploadFile - File Name: " + name + " ...............");
					if (!name.endsWith(".doc") && !name.endsWith(".docx") && !name.endsWith(".pdf") && !name.endsWith(".tif") && !name.endsWith(".tiff")) {
						//log.debug("Set Error Message: Required File wrong type...............");
						//log.debug("StudentUploadAction - Upload - Validate - Upload Required: " + file.getFileName() + " Required File wrong type ");
						messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Only doc, docx, pdf, tif and tiff files are allowed"));
						addErrors(request, messages);
						//log.debug("StudentUploadAction - Upload - UploadFile - Required File wrong type");
						return mapping.findForward("documentInput");
					}
					//log.debug("StudentUploadAction - Upload - Required File Name: " + name + " ..............."); 
					if (fb.getUploaded().size() == 5) {


						//log.debug("Set Error Message: Required File more than 5...............");
						messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "You have reached the maximum of 5 files"));
						addErrors(request, messages);
						//log.debug("StudentUploadAction - Upload - UploadFile - Required File more than 5");
						return mapping.findForward("documentInput");
					}
				}
			}
			
			// validate optional
			//log.debug("StudentUploadAction - Upload - UploadFile - Optional File ===============================================================");

			if (stuUpForm.getOptionFileBean().getFile() != null){
				//log.debug("StudentUploadAction - Upload - UploadFile - Optional File - Validate Optional File");
				FormFile file = stuUpForm.getOptionFileBean().getFile();
				if (file != null && file.getFileName() != null && file.getFileName().trim().length() > 0) {
					isSelectedOptionalFile = true;
					//Enable this if you wish to validate for optional documents
					if( "-1".equals(stuUpForm.getOptionFileBean().getDoc().getDocCode())){
						//log.debug("StudentUploadAction - Upload - UploadFile - Optional File - Set Error Message: Optional File Required...............");
						stuUpForm.setFileSelected(stuUpForm.getOptionFileBean().getDoc().getDocCode());
						messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Please select a document type from list for the optional file"));
						addErrors(request, messages);
						//log.debug("StudentUploadAction - Upload - UploadFile - Optional File - Required Document type missing");
						return mapping.findForward("documentInput");
					}
					int size = file.getFileSize();
					//log.debug("StudentUploadAction - Upload - UploadFile - Optional File - - File: " + file.getFileName() + " & size = " + size);
					
					String readFileSize = readableFileSize(size);
					//log.debug("StudentUploadAction - Upload - UploadFile - Optional File - Validate - Upload Optional: " + file.getFileName() + " size = " + readFileSize);
					if (readFileSize.contains("Error"))	{
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage", readFileSize));
						addErrors(request, messages);
						return mapping.findForward("documentInput");
					}else {
						if (size > 2097152) {// check file size,  not greater than 2 MB (2097152) measured in BYTES!
							//log.debug("StudentUploadAction - Upload - UploadFile - Optional File - Validate - Upload Optional: " + file.getFileName() + " size = " + readFileSize + " File size too large");
							messages.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("message.generalmessage", "File cannot be larger than 2MB (2048K). Your file is "+readFileSize));
							addErrors(request, messages);
							//log.debug("StudentUploadAction - Upload - UploadFile - Optional File - Optional File larger than 2MB");
							return mapping.findForward("documentInput");
						}else {
							//log.debug("StudentUploadAction - Upload - UploadFile - Optional File - Set Error Message: Required File size OK...............");
						}
					}
					String name = file.getFileName().toLowerCase();
					if (!name.endsWith(".doc") && !name.endsWith(".docx") && !name.endsWith(".pdf") && !name.endsWith(".tif") && !name.endsWith(".tiff")) {
						//log.debug("StudentUploadAction - Upload - UploadFile - Optional File - Validate - Upload Optional: " + file.getFileName() + " File wrong type ");
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage", "Only doc, docx, pdf, tif and tiff files are allowed"));
						addErrors(request, messages);
						//log.debug("StudentUploadAction - Upload - UploadFile - Optional File - Optional File File wrong type");
						return mapping.findForward("documentInput");
					}
		
				}
			}else{
				//log.debug("StudentUploadAction - Upload - UploadFile - Optional File - Empty................");
			}
			//log.debug("StudentUploadAction - Upload - UploadFile - Optional File - ================================================================");
			//log.debug("StudentUploadAction - Upload - UploadFile - Optional File - Checking File Names and Type");
			
			//log.debug("StudentUploadAction - Upload - UploadFile - Optional File - Checking Type - ================================================================");
			if( !"-1".equals(stuUpForm.getOptionFileBean().getDoc().getDocCode())){ 
				stuUpForm.setFileSelected(stuUpForm.getOptionFileBean().getDoc().getDocCode());
				//log.debug("StudentUploadAction - Upload - UploadFile - Optional File - Checking Type = FileSelected - getDocCode Form="+stuUpForm.getOptionFileBean().getDoc().getDocCode());
				//log.debug("StudentUploadAction - Upload - UploadFile - Optional File - Checking Type = FileSelected - getDocCode getParameter="+request.getParameter("optionFileBean.doc.docCode"));
			}else{
				//log.debug("StudentUploadAction - Upload - UploadFile - Optional File - Checking Type = FileSelected - None");
				stuUpForm.setFileSelected("");
			}
			//log.debug("StudentUploadAction - Upload - UploadFile - Optional File - Checking Type - ================================================================");
			
			
			if(request.getParameter("Continue") == null){
				if ("Upload".equalsIgnoreCase(request.getParameter("UploadReq"))){
				if (stuUpForm.getRequiredDocs().size() > 0){
					if (!isSelectedRequiredFile && !isSelectedOptionalFile && (stuUpForm.getOptionFileBean().getDoc().getDocCode() == null || "-1".equals(stuUpForm.getOptionFileBean().getDoc().getDocCode()))){
						messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Please select a required document or optional document type and file from the optional file list"));
						addErrors(request, messages);
						//log.debug("StudentUploadAction - Upload - UploadFile - No Files or type selected for upload - isSelectedRequiredFile="+isSelectedRequiredFile+", isSelectedOptionalFile="+isSelectedOptionalFile);
						return mapping.findForward("documentInput");
					}else if (!isSelectedRequiredFile && !isSelectedOptionalFile){
						messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Please select a required or optional document to upload"));
						addErrors(request, messages);
						//log.debug("StudentUploadAction - Upload - UploadFile - No Files selected for upload - isSelectedRequiredFile="+isSelectedRequiredFile+", isSelectedOptionalFile="+isSelectedOptionalFile);
						return mapping.findForward("documentInput");
					}
				}else{
					if (!isSelectedOptionalFile && (stuUpForm.getOptionFileBean().getDoc().getDocCode() == null || "-1".equals(stuUpForm.getOptionFileBean().getDoc().getDocCode()))){
						messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Please select a document type and file to upload"));
						addErrors(request, messages);
						//log.debug("StudentUploadAction - Upload - UploadFile - No Optional Files or type selected for upload - isSelectedOptionalFile="+isSelectedOptionalFile+", Type="+stuUpForm.getOptionFileBean().getDoc().getDocCode());
						return mapping.findForward("documentInput");
					}else if (isSelectedOptionalFile && "-1".equals(stuUpForm.getOptionFileBean().getDoc().getDocCode())){
						//log.debug("Set Error Message: Optional File Type Required...............");
						stuUpForm.setFileSelected(stuUpForm.getOptionFileBean().getDoc().getDocCode());
						messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Please select a document type from list for the optional files and re-select your file"));
						addErrors(request, messages);
						//log.debug("StudentUploadAction - Upload - UploadFile - Optional File Type missing");
						return mapping.findForward("documentInput");
					}
					if (!isSelectedOptionalFile && !"-1".equals(stuUpForm.getOptionFileBean().getDoc().getDocCode())){
						//log.debug("Set Error Message: Optional File Required...............");
						stuUpForm.setFileSelected(stuUpForm.getOptionFileBean().getDoc().getDocCode());
						messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Please select an optional document to upload"));
						addErrors(request, messages);
						//log.debug("StudentUploadAction - Upload - UploadFile - Optional Files Empty");
						return mapping.findForward("documentInput");
					}
				}
			}
			}
			//log.debug("StudentUploadAction - Upload - ================================================================");
			//log.debug("StudentUploadAction - Upload - ================================================================");
			//log.debug("StudentUploadAction - Upload - Continuing Upload");
			//log.debug("StudentUploadAction - Upload - ================================================================");

			//log.debug("StudentUploadAction - Upload - Getting to Formfile (1) Required Files - No Files="+stuUpForm.getFileBeans().length);
			//Changed to upload only one file at a time - Although it should be max 5, we check for 10
			List<Integer> doFileNo = new ArrayList<>();
			boolean isRequiredSelected = false;
			for (int fileNo = 0; fileNo < stuUpForm.getFileBeans().length; fileNo++){
				if (request.getParameter("UploadReq["+fileNo+"]") != null && "Upload".equalsIgnoreCase(request.getParameter("UploadReq["+fileNo+"]"))){
					doFileNo.add(fileNo);
					isRequiredSelected = true;
				}
			}
			//debug
			if (isRequiredSelected && doFileNo.size() > 0){
				int doReqFileNo = 0;
				for (int f=0; f < doFileNo.size(); f++){
					//log.debug("StudentUploadAction - Upload - Required File Index Selected="+f);
					for (FileBean fb : stuUpForm.getFileBeans()) {
						//log.debug("StudentUploadAction - Upload - Required File - Uploading into for loop............");
						if (doReqFileNo == doFileNo.get(f)){
							FormFile file = fb.getFile();
							//log.debug("StudentUploadAction - Upload - Required File - Uploading file("+doReqFileNo+")=" +file+"............");
	
				if (file != null && file.getFileName() != null && file.getFileName().trim().length() > 0) {
					//log.debug("StudentUploadAction - Upload - Required File - Uploading filename not null in if - Number of Files="+file.getFileName().trim().length()+", Uploading File="+file.getFileName()+"............");
					
					int size = file.getFileSize();
					//log.debug("StudentUploadAction - Upload - Required File - Uploading file size: " +size+"............");
						
					//save file
					//String dir = servlet.getServletContext().getRealPath("/upload");
					String appDir = ServerConfigurationService.getString("application.path");
					//log.debug("StudentUploadAction - Upload - Required File - Uploading (RequiredFileBean) - application.path:" + appDir + " ............");
						
					String stuGroupDir = stuUpForm.getStudent().getNumber().toString().substring(0, 1);
				   	String stuDir = stuUpForm.getStudent().getNumber().toString();
				   	
				   	String filePath = appDir + "/tmp/" + stuGroupDir + "/" + stuDir;
			    	//log.debug("StudentUploadAction - Upload - Required File - Uploading (RequiredFileBean) -  upload dest path:" + filePath + " ............");

						
					File folder = new File(filePath);
					if(!folder.exists()){
						folder.mkdirs();
					}
					String writeFile = "";
					writeFile = file.getFileName();
					//log.debug("StudentUploadAction - Upload - Required File - Uploading - writeFile1:" + writeFile + "............");
						
					StudentFile stuFile = new StudentFile();
					stuFile.setFileName(writeFile);
					String fileDesc = dao.getDocType(stripXSS(fb.getDoc().getDocCode()));
									//log.debug("StudentUploadAction - Upload - Required File - Uploading - fileDesc Before Rename:" + stuFile.getFileName() + "............");
										
									fileDesc = fileDesc.replace("/","-");
									fileDesc = fileDesc.replace(" ","_");
									
					String fileExtension = writeFile.substring(writeFile.lastIndexOf(".")+1);
					//log.debug("StudentUploadAction - Upload - Required File - Uploading - fileExtension:" + fileExtension + "............");
					
					String time = (new java.text.SimpleDateFormat("hhmmssss").format(new java.util.Date()).toString());
					//log.debug("StudentUploadAction - Upload - Required File - Uploading - time:" + time + "............");
					
					String newFileName = stuUpForm.getStudent().getNumber()+"_"+ fileDesc + "_"+ time +"." + fileExtension;
					stuFile.setNewFileName(newFileName);
					// set filename to changed name for workflow purposes
					writeFile = newFileName;
					//log.debug("StudentUploadAction - Upload - Required File - Uploading - writeFile:" + writeFile + "............");
					
									//log.debug("StudentUploadAction - Upload - Required File - Uploading - fileDesc After Rename:" + writeFile + "............");
									String fullFileName = filePath+System.getProperty("file.separator")+writeFile;
									String success = "None";
									success = uploadFile(fullFileName, file.getInputStream());
				
									File testFile = new File(fullFileName);
									//log.debug("StudentUploadAction - Upload - Required File - Upload Status="+success);
									
									if(testFile.exists() && success.equalsIgnoreCase("Success")){
										//log.debug("StudentUploadAction - Upload - Required File - Required File uploaded and exists on server");
						SavedDoc requiredDoc = new SavedDoc();
						requiredDoc.setDocCode(fb.getDoc().getDocCode());
						requiredDoc.setDocName(file.getFileName());
						//log.debug("StudentUploadAction - Upload - Required File - Calling Required File addSavedDoc 1 - Doc:" + requiredDoc + "............");
						dao.addSavedDocSTUAPD(requiredDoc,"Y",stuUpForm.getStudent().getNumber(),stuUpForm.getStudent().getAcademicYear(),"1");
						dao.addSavedDocSTUXML(requiredDoc,"Y",stuUpForm.getStudent().getNumber(),stuUpForm.getStudent().getAcademicYear(),stuUpForm.getStudent().getAcademicPeriod(),"1");
					}else {
						//Uploaded file not found on Filesystem
						messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "File upload failed (RNF), was interrupted or could not be completed. Please try again."));
						addErrors(request, messages);
						//log.debug("StudentUploadAction - Upload - Required File - UploadAction - UploadFile - Uploaded file not found on filesystem "+ success);
						return mapping.findForward("documentInput");
					}
				}else{
					//log.debug("StudentUploadAction - Upload - Required File - Uploading required filename is null............");
					if(request.getParameter("Continue") == null){ //Only log null if doing upload, not continue
										//log.debug("StudentUploadAction - Upload - Required File - UploadAction required file - Uploading required filename is null" );
										messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "File type to upload was selected, but no Required file selected. Please try again."));
						addErrors(request, messages);
						return mapping.findForward("documentInput");
				
									}
								}
							}
						doReqFileNo++;
						//log.debug("StudentUploadAction - Upload - Required File - Uploading required filename Done. Testing for next file="+doReqFileNo);
					}
				}
			}

			//log.debug("StudentUploadAction - Upload - Required File - Uploading All Required Files Done............");

			
			//log.debug("StudentUploadAction - Upload - =======================================================================================");
			
			//log.debug("StudentUploadAction - Upload - Getting to Formfile (2) Optional Files............");

			if ("Upload".equalsIgnoreCase(request.getParameter("UploadOpt"))){
				//log.debug("StudentUploadAction - Upload - Optional File Button Clicked");
			FormFile file = stuUpForm.getOptionFileBean().getFile();
			if (file != null && file.getFileName() != null && file.getFileName().trim().length() > 0) {
				int size = file.getFileSize();
				//log.debug("StudentUploadAction - Upload - Optional File - Uploading Optional File size: " +size+"............");
					
				//save file
				//String dir = servlet.getServletContext().getRealPath("/upload");
				String appDir = ServerConfigurationService.getString("application.path");
				//log.debug("StudentUploadAction - Upload - Optional File - Uploading (OptionFileBean) - application.path:" + appDir + " ............");
					
				String stuGroupDir = stuUpForm.getStudent().getNumber().toString().substring(0, 1);
				String stuDir = stuUpForm.getStudent().getNumber().toString();
			    	
				String filePath = appDir + "/tmp/" + stuGroupDir + "/" + stuDir;
	
				//log.debug("StudentUploadAction - Upload - Optional File - Uploading (OptionFileBean) -  upload dest path:" + filePath + " ............");
					
				File folder = new File(filePath);
				if(!folder.exists()){
					folder.mkdirs();
				}
				
				String writeFile = "";
				writeFile = file.getFileName();
				//log.debug("StudentUploadAction - Upload - Optional File - Uploading - writeFile2:" + writeFile + "............");
					
				StudentFile stuFile = new StudentFile();
				stuFile.setFileName(writeFile);
				String fileDesc = dao.getDocType(stripXSS(stuUpForm.getOptionFileBean().getDoc().getDocCode()));
					//log.debug("StudentUploadAction - Upload - Optional File - Uploading - fileDesc Before Rename:" + stuFile.getFileName() + "............");
						
					fileDesc = fileDesc.replace("/","-");
					fileDesc = fileDesc.replace(" ","_");
						
					String fileExtension = writeFile.substring(writeFile.lastIndexOf(".")+1);
					//log.debug("StudentUploadAction - Upload - Optional File - Uploading - fileExtension:" + fileExtension + "............");
						
					String time = (new java.text.SimpleDateFormat("hhmmssss").format(new java.util.Date()).toString());
					//log.debug("StudentUploadAction - Upload - Optional File - Uploading - time:" + time + "............");
						
					String newFileName = stuUpForm.getStudent().getNumber()+"_"+ fileDesc + "_"+ time +"." + fileExtension;
					stuFile.setNewFileName(newFileName);
					
					// set filename to changed name for workflow purposes
					writeFile = newFileName;
					//log.debug("StudentUploadAction - Upload - Optional File - Uploading - writeFile:" + writeFile + "............");
						
					//log.debug("StudentUploadAction - Upload - Optional File - Uploading - fileDesc After Rename:" + writeFile + "............");
					
				String fullFileName = filePath+System.getProperty("file.separator")+writeFile;
				String success = "None";
				success = uploadFile(fullFileName, file.getInputStream());
				//uploadFile(dir+System.getProperty("file.separator")+file.getFileName(), file.getInputStream());
				
				File testFile = new File(fullFileName);
				if(testFile.exists() && success.equalsIgnoreCase("Success")){
					//log.debug("StudentUploadAction - Upload - Optional File - Optional File exists - Saving to Database");
					SavedDoc optionDoc = new SavedDoc();
					optionDoc.setDocCode(stuUpForm.getOptionFileBean().getDoc().getDocCode());
					optionDoc.setDocName(file.getFileName());
					//log.debug("StudentUploadAction - Upload - Optional File - Calling addSavedDoc 2 - optionDoc:" + optionDoc + "............");
					dao.addSavedDocSTUAPD(optionDoc,"N",stuUpForm.getStudent().getNumber(),stuUpForm.getStudent().getAcademicYear(),"2");
					dao.addSavedDocSTUXML(optionDoc,"N",stuUpForm.getStudent().getNumber(),stuUpForm.getStudent().getAcademicYear(),stuUpForm.getStudent().getAcademicPeriod(),"2");
				}else {
					//Uploaded file not found on Filesystem
					messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "File upload failed (ONF), was interrupted or could not be completed. Please try again."));
					addErrors(request, messages);
					//log.debug("StudentUploadAction - Upload - Optional File - UploadAction - UploadFile - Uploaded file not found on filesystem "+ success);
					return mapping.findForward("documentInput");
				}
			}else{
					//log.debug("StudentUploadAction - Upload - Optional File - UploadAction optional file - Uploading optional filename is null" );
					if(request.getParameter("Continue") == null && !isSelectedRequiredFile && !isSelectedOptionalFile){ //Only log null if doing upload, not continue
						//log.debug("StudentUploadAction - Upload - Optional File - UploadAction optional file - Uploading optional filename is null" );
						//No File Selected for Upload
						if( "-1".equals(stuUpForm.getOptionFileBean().getDoc().getDocCode())){ 
							messages.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("message.generalmessage", "No File type or file was selected for upload. Please try again or Continue if you don't wish to upload any documents"));
							addErrors(request, messages);
							//log.debug("StudentUploadAction - Upload - Optional File - No File was selected for upload. Please try again or Continue if you don't wish to upload any documents");
							return mapping.findForward("input");
						}else{
							messages.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("message.generalmessage", "No File was selected for upload. Please try again or Continue if you don't wish to upload any documents"));
							addErrors(request, messages);
							//log.debug("StudentUploadAction - Upload - Optional File - No File was selected for upload. Please try again or Continue if you don't wish to upload any documents");
							return mapping.findForward("input");
						}
					}
				}
				//log.debug("StudentUploadAction - Upload - Optional File - Done");
				
				//log.debug("StudentUploadAction - Upload - Validate - OK - ================================================================");
			}		
			
			//Get Matric Certificate for second login
			if (stuUpForm.getStudent().getMatrix() == null || "".equals(stuUpForm.getStudent().getMatrix().trim())){
				boolean isCGMatrix = false;
				isCGMatrix = dao.getMatricCert(stuUpForm.getStudent().getNumber());
				if (isCGMatrix){
					stuUpForm.getStudent().setMatrix("CG");
				}
			}
			//log.debug("StudentUploadAction - Upload - Optional File - UploadAction - Upload before Form Reload");
			stuUpForm.reLoad();
	
			//log.debug("StudentUploadAction - Upload - Optional File - UploadAction - Upload - Checking Continue="+request.getParameter("Continue"));

			//log.debug("StudentUploadAction - Upload - ================================================================");

			
			if(request.getParameter("Continue") != null) {
				//log.debug("StudentUploadAction - Upload - ================================================================");
				//log.debug("StudentUploadAction - Upload - Save and Continue");
				//log.debug("StudentUploadAction - Upload - ================================================================");

				 
				StudentUploadDAO applyDAO = new StudentUploadDAO();
				 
				if (stuUpForm.getStudent().getEmailAddress() == null){
					//log.debug("StudentUploadAction - Upload - Email is Null - Retrieve from Personal Details");
					stuUpForm.getStudent().setEmailAddress(applyDAO.personalDetails(stuUpForm.getStudent().getNumber(),"EMAIL_ADDRESS").trim());
				}
				if (stuUpForm.getStudent().getCellNr() == null){
					//log.debug("StudentUploadAction - Upload - Cell is Null - Retrieve from Personal Details");
					stuUpForm.getStudent().setCellNr(applyDAO.personalDetails(stuUpForm.getStudent().getNumber(),"CELL_NUMBER").trim());
				}
			 
				//Update STUAPQ upload for Student to NP if ND
				//log.debug("StudentUploadAction - Upload - Setting STUAPQ NP for Student");
				//log.debug("StudentUploadAction - Upload - Checking if MD, if so do not write STUAPQ record for Student");

				//String checkMD = dao.getFinalMD(stuUpForm.getStudent().getNumber(),stuUpForm.getStudent().getAcademicYear(),stuUpForm.getStudent().getAcademicPeriod());
						
				if(!stuUpForm.getStudent().isStuMD()){
					//log.debug("StudentUploadAction - Not MD, so write STUAPQ record for Student");
					dao.setSTUAPQ(stuUpForm.getStudent().getNumber(),stuUpForm.getStudent().getAcademicYear());
				}
				
				//log.debug("StudentUploadAction - Upload - STUAPQ Done");
				
				//log.debug("StudentUploadAction - Upload - Do Staae05sAppAdmissionEvaluator Letter");
				/**2018 July - Johanet enable code that was commented out to email application received letter - BRD SR198094 5.1**/
				/**2018 Edmund Start of Send Letter**/
				
				Staae05sAppAdmissionEvaluator op = new Staae05sAppAdmissionEvaluator();
				operListener opl = new operListener();
				op.addExceptionListener(opl);
				op.clear();

				op.setInCsfClientServerCommunicationsClientVersionNumber((short) 3);
				op.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
				op.setInCsfClientServerCommunicationsAction("PR");
				op.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
				op.setInWsUserNumber(99998);
				//log.debug("StudentUploadAction - Upload - (Staae05sAppAdmissionEvaluator) - Academic Year=" + stuUpForm.getStudent().getAcademicYear());
				op.setInWsAcademicYearYear((short) Integer.parseInt(stuUpForm.getStudent().getAcademicYear()));
				op.setInWebStuApplicationQualAcademicYear((short) Integer.parseInt(stuUpForm.getStudent().getAcademicYear()));
				//log.debug("StudentUploadAction - Upload - (Staae05sAppAdmissionEvaluator) - Academic Period=" + stuUpForm.getStudent().getAcademicPeriod());
				op.setInWebStuApplicationQualApplicationPeriod((short) Integer.parseInt(stuUpForm.getStudent().getAcademicPeriod()));
				//log.debug("StudentUploadAction - Upload - (Staae05sAppAdmissionEvaluator) - Student Number=" + stuUpForm.getStudent().getNumber());
				op.setInWebStuApplicationQualMkStudentNr(Integer.parseInt(stuUpForm.getStudent().getNumber()));
				//log.debug("StudentUploadAction - Upload - (Staae05sAppAdmissionEvaluator) - RetQualOneFinal=" + stuUpForm.getStudent().getRetQualOneFinal());
				//log.debug("StudentUploadAction - Upload - (Staae05sAppAdmissionEvaluator) - Choice Nr= 1");

				//log.debug("StudentUploadAction - Upload - (Staae05sAppAdmissionEvaluator) - Execute");
				op.execute();
	
				if (opl.getException() != null) throw opl.getException();
				if (op.getExitStateType() < 3) throw new Exception(op.getExitStateMsg());
	
				//log.debug("StudentUploadAction - Upload - Staae05sAppAdmissionEvaluator - After Execute");
				String opResult = op.getOutCsfStringsString500();
				//log.debug("StudentUploadAction - Upload - (Staae05sAppAdmissionEvaluator) opResult: " + opResult);
				/**End of Send Letter
				**/
				
				//log.debug("StudentUploadAction moveDocuments if any: " + stuUpForm.getStudent().getNumber());
				 
				String result = moveDocuments(stuUpForm);
				//log.debug("StudentUploadAction - Upload "+result);
				if (result.contains("Error")){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "File upload failed (MV), was interrupted or could not be completed. Please try again."));
					addErrors(request, messages);
					return mapping.findForward("documentInput");
				}
				 		 
				/* Set submission time stamp */
				Date date = new java.util.Date();
				String displayDate = (new java.text.SimpleDateFormat("EEEEE dd MMMMM yyyy HH:mm").format(date).toString());
				//log.debug("StudentUploadAction ActionForward AppTime: " + displayDate);
				//log.debug("StudentUploadAction stuUpForm stuExist: " + stuUpForm.getStudent().isStuExist());
				//log.debug("StudentUploadAction request Parameter stuExist: " + request.getParameter("stuExist"));
				
				if (stuUpForm.getStudent().isStuExist()){
					//log.debug("StudentUploadAction - stuExist Get saved qualifications for student from STUAPQ");
					
					String stuNumber =  stuUpForm.getStudent().getNumber();
					boolean isApp = stuUpForm.getStudent().isStuAPP();
					boolean isSLP = stuUpForm.getStudent().isStuSLP();
					boolean isHON = stuUpForm.getStudent().isStuHON();
					boolean isSBL = stuUpForm.getStudent().isStuSBL();
					boolean isPG = stuUpForm.getStudent().isStuPG();
					boolean isMD = stuUpForm.getStudent().isStuMD();
					
					//log.debug("StudentUploadAction - Upload - stuExist - isApp="+isApp);
					//log.debug("StudentUploadAction - Upload - stuExist - isSLP="+isSLP);
					//log.debug("StudentUploadAction - Upload - stuExist - isHON="+isHON);
					//log.debug("StudentUploadAction - Upload - stuExist - isSBL="+isSBL);
					//log.debug("StudentUploadAction - Upload - stuExist - isPG="+isPG);
					//log.debug("StudentUploadAction - Upload - stuExist - isMD="+isMD);
					
					//New Qualifications
					//Get saved qualifications for student from STUAPQ (Getting them individually to simplify moving result between forms)
					String retQualOneFinal =  dao.vrfyNewQual("Qual","1",stuUpForm.getStudent().getNumber(),stuUpForm.getStudent().getAcademicYear());
					//log.debug("StudentUploadAction - Upload - stuExist - Get saved qualifications for student from STUAPQ - retQualOneFinal="+retQualOneFinal);
					String retSpecOneFinal = dao.vrfyNewQual("Spec","1",stuUpForm.getStudent().getNumber(),stuUpForm.getStudent().getAcademicYear());
					//log.debug("StudentUploadAction - Upload - stuExist - Get saved qualifications for student from STUAPQ - retSpecOneFinal="+retSpecOneFinal);
					String retQualTwoFinal = dao.vrfyNewQual("Qual","2",stuUpForm.getStudent().getNumber(),stuUpForm.getStudent().getAcademicYear());
					//log.debug("StudentUploadAction - Upload - stuExist - Get saved qualifications for student from STUAPQ - retQualTwoFinal="+retQualTwoFinal);
					String retSpecTwoFinal = dao.vrfyNewQual("Spec","2",stuUpForm.getStudent().getNumber(),stuUpForm.getStudent().getAcademicYear());
					//log.debug("StudentUploadAction - Upload - stuExist - Get saved qualifications for student from STUAPQ - retSpecTwoFinal="+retSpecTwoFinal);
					String emailAddress = stuUpForm.getStudent().getEmailAddress();
					
					//Reset All Form Variables
					resetForm((StudentUploadForm) stuUpForm, "Upload Return");
					
					//Set Necessary variables for Final page
					stuUpForm.getStudent().setAppTime(displayDate);
					//log.debug("StudentUploadAction - Upload - ActionForward AppTime: " + stuUpForm.getStudent().getAppTime());

					stuUpForm.getStudent().setNumber(stuNumber);
					//log.debug("StudentUploadAction - Upload - stuExist - Student Number2="+stuUpForm.getStudent().getNumber());
					
					if (!stuUpForm.getStudent().isStuSLP()){
						//Previous Qualification
						String retQualPrevFinal = dao.vrfyPrevQual("Qual",stuUpForm.getStudent().getNumber());
						//log.debug("StudentUploadAction - Upload - stuExist Get saved qualifications for student from STUANN - retQualPrevFinal="+retQualPrevFinal);
						stuUpForm.getStudent().setRetQualPrevFinal(retQualPrevFinal);
						
						String retSpecPrevFinal = dao.vrfyPrevQual("Spec",stuUpForm.getStudent().getNumber());
						//log.debug("StudentUploadAction - Upload - stuExist Get saved qualifications for student from STUANN - retSpecPrevFinal="+retSpecPrevFinal);
						stuUpForm.getStudent().setRetSpecPrevFinal(retSpecPrevFinal);
					}
					stuUpForm.getStudent().setRetQualOneFinal(retQualOneFinal);
					stuUpForm.getStudent().setRetSpecOneFinal(retSpecOneFinal);
					stuUpForm.getStudent().setRetQualTwoFinal(retQualTwoFinal);
					stuUpForm.getStudent().setRetSpecTwoFinal(retSpecTwoFinal);
					stuUpForm.getStudent().setEmailAddress(emailAddress);
					
					if (!stuUpForm.getStudent().isStuSLP()){
						//log.debug("StudentUploadAction - Upload - stuExist - retQualPrevFinal="+stuUpForm.getStudent().getRetQualPrevFinal());
						//log.debug("StudentUploadAction - Upload - stuExist - retSpecPrevFinal="+stuUpForm.getStudent().getRetSpecPrevFinal());
					}
					
					//log.debug("StudentUploadAction - Upload - stuExist - retQualOneFinal2="+stuUpForm.getStudent().getRetQualOneFinal());
					//log.debug("StudentUploadAction - Upload - stuExist - retSpecOneFinal2="+stuUpForm.getStudent().getRetSpecOneFinal());
					//log.debug("StudentUploadAction - Upload - stuExist - retQualTwoFinal2="+stuUpForm.getStudent().getRetQualTwoFinal());
					//log.debug("StudentUploadAction - Upload - stuExist - retSpecTwoFinal2="+stuUpForm.getStudent().getRetSpecTwoFinal());
					//log.debug("StudentUploadAction - Upload - stuExist - emailAddress2="+stuUpForm.getStudent().getEmailAddress());
					
					stuUpForm.getStudent().setStuAPP(isApp);
					stuUpForm.getStudent().setStuSLP(isSLP);
					stuUpForm.getStudent().setStuHON(isHON);
					stuUpForm.getStudent().setStuSBL(isSBL);
					stuUpForm.getStudent().setStuPG(isPG);
					stuUpForm.getStudent().setStuMD(isMD);
					
					//log.debug("StudentUploadAction - Upload - stuExist - isApp2="+stuUpForm.getStudent().isStuAPP());
					//log.debug("StudentUploadAction - Upload - stuExist - isSLP2="+stuUpForm.getStudent().isStuSLP());
					//log.debug("StudentUploadAction - Upload - stuExist - isHON2="+stuUpForm.getStudent().isStuHON());
					//log.debug("StudentUploadAction - Upload - stuExist - isSBL2="+stuUpForm.getStudent().isStuSBL());
					//log.debug("StudentUploadAction - Upload - stuExist - isPG2="+stuUpForm.getStudent().isStuPG());
					//log.debug("StudentUploadAction - Upload - stuExist - isMD2="+stuUpForm.getStudent().isStuMD());
					
					//log.debug("StudentUploadAction - Upload - stuExist Redirect: completeRet");
					return mapping.findForward("completeRet");
				}else{
					if (stuUpForm.getStudent().isStuMD()){
						
						boolean isApp = stuUpForm.getStudent().isStuAPP();
						boolean isSLP = stuUpForm.getStudent().isStuSLP();
						boolean isHON = stuUpForm.getStudent().isStuHON();
						boolean isSBL = stuUpForm.getStudent().isStuSBL();
						boolean isPG = stuUpForm.getStudent().isStuPG();
						boolean isMD = stuUpForm.getStudent().isStuMD();
						
						//Get saved qualifications for student from STUAPQ (Getting them individually to simplify moving result between forms)
						String studentNr = stuUpForm.getStudent().getNumber();
						String newMDQualFinal = dao.vrfyNewMDQual("Qual",stuUpForm.getStudent().getNumber(),stuUpForm.getStudent().getAcademicYear());
						String newMDSpecFinal = dao.vrfyNewMDQual("Spec",stuUpForm.getStudent().getNumber(),stuUpForm.getStudent().getAcademicYear());
						String emailAddress = stuUpForm.getStudent().getEmailAddress();
						String cellNr = stuUpForm.getStudent().getCellNr();
						//Reset All Form Variables
						resetForm((StudentUploadForm) stuUpForm, "Upload MD");
						
						//Set Necessary variables for Final page
						stuUpForm.getStudent().setAppTime(displayDate);
						//log.debug("StudentUploadAction - Upload - ActionForward AppTime: " + stuUpForm.getStudent().getAppTime());
						
						stuUpForm.getStudent().setNumber(studentNr);
						stuUpForm.getStudent().setNewMDQualFinal(newMDQualFinal);
						stuUpForm.getStudent().setNewMDSpecFinal(newMDSpecFinal);
						stuUpForm.getStudent().setEmailAddress(emailAddress);
						stuUpForm.getStudent().setCellNr(cellNr);
						
						stuUpForm.getStudent().setStuAPP(isApp);
						stuUpForm.getStudent().setStuSLP(isSLP);
						stuUpForm.getStudent().setStuHON(isHON);
						stuUpForm.getStudent().setStuSBL(isSBL);
						stuUpForm.getStudent().setStuPG(isPG);
						stuUpForm.getStudent().setStuMD(isMD);
						
						//log.debug("StudentUploadAction - Upload - stuExist Redirect: completeMD");
						return mapping.findForward("completeMD");
					}else {
						boolean isApp = stuUpForm.getStudent().isStuAPP();
						boolean isSLP = stuUpForm.getStudent().isStuSLP();
						boolean isHON = stuUpForm.getStudent().isStuHON();
						boolean isSBL = stuUpForm.getStudent().isStuSBL();
						boolean isPG = stuUpForm.getStudent().isStuPG();
						boolean isMD = stuUpForm.getStudent().isStuMD();
						
						//Get saved qualifications for student from STUAPQ (Getting them individually to simplify moving result between forms)
						String studentNr = stuUpForm.getStudent().getNumber();
						String newQualOneFinal = dao.vrfyNewQual("Qual","1",stuUpForm.getStudent().getNumber(),stuUpForm.getStudent().getAcademicYear());
						String newSpecOneFinal = dao.vrfyNewQual("Spec","1",stuUpForm.getStudent().getNumber(),stuUpForm.getStudent().getAcademicYear());
						String newQualTwoFinal = dao.vrfyNewQual("Qual","2",stuUpForm.getStudent().getNumber(),stuUpForm.getStudent().getAcademicYear());
						String newSpecTwoFinal = dao.vrfyNewQual("Spec","2",stuUpForm.getStudent().getNumber(),stuUpForm.getStudent().getAcademicYear());
						String emailAddress = stuUpForm.getStudent().getEmailAddress();
						String cellNr = stuUpForm.getStudent().getCellNr();
						//Reset All Form Variables
						resetForm((StudentUploadForm) stuUpForm, "Upload");
						
						//Set Necessary variables for Final page
						stuUpForm.getStudent().setAppTime(displayDate);
						//log.debug("StudentUploadAction - Upload - ActionForward AppTime: " + stuUpForm.getStudent().getAppTime());
						
						stuUpForm.getStudent().setNumber(studentNr);
						stuUpForm.getStudent().setNewQualOneFinal(newQualOneFinal);
						stuUpForm.getStudent().setNewSpecOneFinal(newSpecOneFinal);
						stuUpForm.getStudent().setNewQualTwoFinal(newQualTwoFinal);
						stuUpForm.getStudent().setNewSpecTwoFinal(newSpecTwoFinal);
						stuUpForm.getStudent().setEmailAddress(emailAddress);
						stuUpForm.getStudent().setCellNr(cellNr);
						
						stuUpForm.getStudent().setStuAPP(isApp);
						stuUpForm.getStudent().setStuSLP(isSLP);
						stuUpForm.getStudent().setStuHON(isHON);
						stuUpForm.getStudent().setStuSBL(isSBL);
						stuUpForm.getStudent().setStuPG(isPG);
						stuUpForm.getStudent().setStuMD(isMD);
						
						if (stuUpForm.getStudent().isStuSLP()){
							//log.debug("StudentUploadAction - Upload - stuExist Redirect: completeSLP");
							return mapping.findForward("completeSLP");
						}else{
							//log.debug("StudentUploadAction - Upload - stuExist Redirect: completeNew");
							return mapping.findForward("completeNew");
						}
					}
				}
			 }
		} catch(Exception ex ){
			//log.debug("StudentUploadAction - Upload failed - Returning to Input");
			messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", "File upload failed (CR), was interrupted or could not be completed. Please try again."));
			addErrors(request, messages);
			return mapping.findForward("documentInput");
		}
		//log.debug("StudentUploadAction - Upload - ================================================================");
		//log.debug("StudentUploadAction - Upload - Done - Going to input");
		//log.debug("StudentUploadAction - Upload - ================================================================");

		 return mapping.findForward("documentInput");
	}


	 private String uploadFile(String filePath,InputStream is) {
		 String result = "Error";
		  try {
			  
			  //log.debug("StudentUploadAction - uploadFile - START, Filepath:"+filePath);
			  
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
			  log.info("StudentUploadAction - Entering uploadFile - Exception="+ex);
			  result = "Error - File upload failed, please try again";
		  }
		  return result;
	 }
	 
	/** Copy documents from temp dir: /data/sakai/applications
	*   to workflow dir: /data/sakai/applications
	*   path in sakai.properties
	*
	*   This method is only called if all required documents have been uploaded
	*   @author Edmund Wilschewski 2013
	**/
	public String moveDocuments(StudentUploadForm stuUpForm) {

			String result = "Error";
			//log.debug("StudentUploadAction - Entering moveDocuments, studentNr:"+stuUpForm.getStudent().getNumber());

			StudentUploadDAO applyDAO = new StudentUploadDAO();
			
	        try {
	        	String qual1 = applyDAO.vrfyNewQualShort("QUAL", "1", stuUpForm.getStudent().getNumber(),stuUpForm.getStudent().getAcademicYear());
	        	String qual2 = applyDAO.vrfyNewQualShort("QUAL", "2", stuUpForm.getStudent().getNumber(),stuUpForm.getStudent().getAcademicYear());
	        	
	        	//log.debug("StudentUploadAction - moveDocuments - Qual1="+qual1);
	        	//log.debug("StudentUploadAction - moveDocuments - Qual2="+qual2);

	        	//Get Qualification information to route to correct subfolder - UG/HONS/PG/SLP/SBL/MD
	        	String qualType1 = applyDAO.getQualType(qual1);
	        	String qualType2 = applyDAO.getQualType(qual2);
	        	//log.debug("StudentUploadAction - moveDocuments - QualOne="+qual1+", qualType1="+qualType1);
	        	//log.debug("StudentUploadAction - moveDocuments - QualTwo="+qual2+", qualType2="+qualType2);
	        	
	        	if ("APP".equalsIgnoreCase(qualType1) || "APP".equalsIgnoreCase(qualType2)){
	        		stuUpForm.getStudent().setStuAPP(true);
	        	}
	        	if ("HON".equalsIgnoreCase(qualType1) || "HON".equalsIgnoreCase(qualType2)){
	        		stuUpForm.getStudent().setStuHON(true);
	        	}
	        	if ("PG".equalsIgnoreCase(qualType1) || "PG".equalsIgnoreCase(qualType2)){
	        		stuUpForm.getStudent().setStuPG(true);
	        	}
	        	if ("SBL".equalsIgnoreCase(qualType1) || "SBL".equalsIgnoreCase(qualType2)){
	        		stuUpForm.getStudent().setStuSBL(true);
	        	}
	        	if ("SLP".equalsIgnoreCase(qualType1) || "SLP".equalsIgnoreCase(qualType2)){
	        		stuUpForm.getStudent().setStuSLP(true);
	        	}
	        	if ("MD".equalsIgnoreCase(qualType1) || "MD".equalsIgnoreCase(qualType2)){
	        		stuUpForm.getStudent().setStuMD(true);
	        	}
	        	//Catch All
	        	if (!stuUpForm.getStudent().isStuAPP() && !stuUpForm.getStudent().isStuHON() && !stuUpForm.getStudent().isStuPG() && !stuUpForm.getStudent().isStuSBL() && !stuUpForm.getStudent().isStuSLP() && !stuUpForm.getStudent().isStuMD()){ 
	        		stuUpForm.getStudent().setStuAPP(true);
	        	}
	        	
	        	String dirAPP = ServerConfigurationService.getString("application.path");
				//log.debug("StudentUploadAction - moveDocuments - application.path:" + dirAPP + " ............");
				
				String dirHON = ServerConfigurationService.getString("honapply.path");
				//log.debug("StudentUploadAction - moveDocuments - honapply.path:" + dirHON + " ............");
				
				String dirPG = ServerConfigurationService.getString("pgapply.path");
				//log.debug("StudentUploadAction - moveDocuments - pgapply.path:" + dirPG + " ............");
				
				String dirSBL = ServerConfigurationService.getString("sblapply.path");
				//log.debug("StudentUploadAction - moveDocuments - sblapply.path:" + dirSBL + " ............");
				
				String dirSLP = ServerConfigurationService.getString("slpapply.path");
				//log.debug("StudentUploadAction - moveDocuments - slpapply.path:" + dirSLP + " ............");
				
				String dirMD = ServerConfigurationService.getString("mdapply.path");
				//log.debug("StudentUploadAction - moveDocuments - mdapply.path:" + dirMD + " ............");
				
				String stuGroupDir = stuUpForm.getStudent().getNumber().substring(0, 1);
		    	String stuDir = stuUpForm.getStudent().getNumber();
	        	String groupDir = dirAPP + "/tmp/" + stuGroupDir;
	        	String sourceDir =  groupDir + "/" + stuDir;

				//log.debug("StudentUploadAction - moveDocuments - move source path:" + sourceDir + " ............");

	        	//File f = new File(sourceDir); // Source directory
	        	File root = new File(sourceDir); // Source directory
	            
	        	//log.debug("StudentUploadAction - moveDocuments - sourceDir: " + sourceDir);
	        	
				if (!root.exists()) {
					//log.debug("StudentUploadAction - moveDocuments - move source path doesn't exist - Exiting");
					result = "File destination path not found. Please try again.";
				} else {
					//log.debug("StudentUploadAction - moveDocuments - move source path exists");
					 try {
				            boolean recursive = false;
				            boolean isDoRemoveFiles = false;

				            Collection<File> files = FileUtils.listFiles(root, null, recursive);

							 for (Iterator<File> iterator = files.iterator(); iterator.hasNext();) {
								 boolean copySuccess = true;
					                File sourceFile = iterator.next();
					                if (sourceFile.getName().contains(stuUpForm.getStudent().getNumber().toString())){
			        	        		//log.debug("StudentUploadAction - moveDocuments - file: " + sourceFile);
				        	        	File fileToBeCopied = new File(sourceDir + "/" + sourceFile.getName());
				        	        	//log.debug("StudentUploadAction - moveDocuments - fileToBeCopied: " + fileToBeCopied);
				        	        	//log.debug("StudentUploadAction - moveDocuments - fileToBeCopied name: " + fileToBeCopied.getName());
				        	        	
				        	        	if (stuUpForm.getStudent().isStuAPP()){
				        	        		File dest = new File(dirAPP);
					        	        	//log.debug("StudentUploadAction - moveDocuments - isApp - Copy file to path: " + dirAPP + "/ ............");

					        	        	File destFile = new File(dest + "/" + fileToBeCopied.getName());
					        	        	//log.debug("StudentUploadAction - moveDocuments - isApp - destFile: " + destFile);
					        	        	FileUtils.copyFile(sourceFile, destFile);
					        	        	//boolean success = sourceFile.renameTo(destFile);
					        	        	
					        	        	if (destFile.exists()) {
					        	        		//log.debug("StudentUploadAction - moveDocuments - isApp - File " + destFile + " was copied.\n");
					        	        	    if (FileUtils.contentEquals(sourceFile, destFile)){
					        	        			//log.debug("StudentUploadAction - moveDocuments - isApp - File " + sourceFile + " is same as - "+destFile+" - was successfully copied.\n");
					        	        	    }else{
					        	        	    	copySuccess = false;
					        	        	    }
					        	        	} else {
					        	       			//log.debug("StudentUploadAction - moveDocuments - isApp - File " + sourceFile + " was not successfully copied. It can be because file with file name already exists in destination\n");
					        	       			copySuccess = false;
					        	       		}
				        	        	}
				        	        	if (stuUpForm.getStudent().isStuHON()){
				        	        		File dest = new File(dirHON);
					        	        	//log.debug("StudentUploadAction - moveDocuments - isHON - Copy file to path: " + dirHON + "/ ............");

					        	        	File destFile = new File(dest + "/" + fileToBeCopied.getName());
					        	        	//log.debug("StudentUploadAction - moveDocuments - isHON - destFile: " + destFile);
					        	        	FileUtils.copyFile(sourceFile, destFile);
					        	        	//boolean success = sourceFile.renameTo(destFile);
					        	        	
					        	        	if (destFile.exists()) {
					        	        		//log.debug("StudentUploadAction - moveDocuments - isHON - File " + destFile + " was copied.\n");
					        	        	    if (FileUtils.contentEquals(sourceFile, destFile)){
					        	        			//log.debug("StudentUploadAction - moveDocuments - isHON - File " + sourceFile + " is same as - "+destFile+" - was successfully copied.\n");
					        	        	    }else{
					        	        	    	copySuccess = false;
					        	        	    }
					        	        	} else {
					        	       			//log.debug("StudentUploadAction - moveDocuments - isHON - File " + sourceFile + " was not successfully copied. It can be because file with file name already exists in destination\n");
					        	       			copySuccess = false;
					        	       		}
				        	        	}
				        	        	if (stuUpForm.getStudent().isStuPG()){
				        	        		File dest = new File(dirPG);
					        	        	//log.debug("StudentUploadAction - moveDocuments - isPG - Copy file to path: " + dirPG + "/ ............");

					        	        	File destFile = new File(dest + "/" + fileToBeCopied.getName());
					        	        	//log.debug("StudentUploadAction - moveDocuments - isPG - destFile: " + destFile);
					        	        	FileUtils.copyFile(sourceFile, destFile);
					        	        	//boolean success = sourceFile.renameTo(destFile);
					        	        	
					        	        	if (destFile.exists()) {
					        	        		//log.debug("StudentUploadAction - moveDocuments - isPG - File " + destFile + " was copied.\n");
					        	        	    if (FileUtils.contentEquals(sourceFile, destFile)){
					        	        			//log.debug("StudentUploadAction - moveDocuments - isPG - File " + sourceFile + " is same as - "+destFile+" - was successfully copied.\n");
					        	        	    }else{
					        	        	    	copySuccess = false;
					        	        	    }
					        	        	} else {
					        	       			//log.debug("StudentUploadAction - moveDocuments - isPG - File " + sourceFile + " was not successfully copied. It can be because file with file name already exists in destination\n");
					        	       			copySuccess = false;
					        	       		}
				        	        	}
				        	        	if (stuUpForm.getStudent().isStuSBL()){
				        	        		File dest = new File(dirSBL);
					        	        	//log.debug("StudentUploadAction - moveDocuments - isSBL - Copy file to path: " + dirSBL + "/ ............");

					        	        	File destFile = new File(dest + "/" + fileToBeCopied.getName());
					        	        	//log.debug("StudentUploadAction - moveDocuments - isSBL - destFile: " + destFile);
					        	        	FileUtils.copyFile(sourceFile, destFile);
					        	        	//boolean success = sourceFile.renameTo(destFile);
					        	        	
					        	        	if (destFile.exists()) {
					        	        		//log.debug("StudentUploadAction - moveDocuments - isSBL - File " + destFile + " was copied.\n");
					        	        	    if (FileUtils.contentEquals(sourceFile, destFile)){
					        	        			//log.debug("StudentUploadAction - moveDocuments - isSBL - File " + sourceFile + " is same as - "+destFile+" - was successfully copied.\n");
					        	        	    }else{
					        	        	    	copySuccess = false;
					        	        	    }
					        	        	} else {
					        	       			//log.debug("StudentUploadAction - moveDocuments - isSBL - File " + sourceFile + " was not successfully copied. It can be because file with file name already exists in destination\n");
					        	       			copySuccess = false;
					        	       		}
				        	        	}
				        	        	if (stuUpForm.getStudent().isStuSLP()){
				        	        		File dest = new File(dirSLP);
					        	        	//log.debug("StudentUploadAction - moveDocuments - isSLP - Copy file to path: " + dirSLP + "/ ............");

					        	        	File destFile = new File(dest + "/" + fileToBeCopied.getName());
					        	        	//log.debug("StudentUploadAction - moveDocuments - isSLP - destFile: " + destFile);
					        	        	FileUtils.copyFile(sourceFile, destFile);
					        	        	//boolean success = sourceFile.renameTo(destFile);
					        	        	
					        	        	if (destFile.exists()) {
					        	        		//log.debug("StudentUploadAction - moveDocuments - isSLP - File " + destFile + " was copied.\n");
					        	        	    if (FileUtils.contentEquals(sourceFile, destFile)){
					        	        			//log.debug("StudentUploadAction - moveDocuments - isSLP - File " + sourceFile + " is same as - "+destFile+" - was successfully copied.\n");
					        	        	    }else{
					        	        	    	copySuccess = false;
					        	        	    }
					        	        	} else {
					        	       			//log.debug("StudentUploadAction - moveDocuments - isSLP - File " + sourceFile + " was not successfully copied. It can be because file with file name already exists in destination\n");
					        	       			copySuccess = false;
					        	       		}
				        	        	}
				        	        	if (stuUpForm.getStudent().isStuMD()){
				        	        		File dest = new File(dirMD);
					        	        	//log.debug("StudentUploadAction - moveDocuments - isMD - Copy file to path: " + dirMD + "/ ............");

					        	        	File destFile = new File(dest + "/" + fileToBeCopied.getName());
					        	        	//log.debug("StudentUploadAction - moveDocuments - isMD - destFile: " + destFile);
					        	        	FileUtils.copyFile(sourceFile, destFile);
					        	        	//boolean success = sourceFile.renameTo(destFile);
					        	        	
					        	        	if (destFile.exists()) {
					        	        		//log.debug("StudentUploadAction - moveDocuments - isMD - File " + destFile + " was copied.\n");
					        	        	    if (FileUtils.contentEquals(sourceFile, destFile)){
					        	        			//log.debug("StudentUploadAction - moveDocuments - isMD - File " + sourceFile + " is same as - "+destFile+" - was successfully copied.\n");
					        	        	    }else{
					        	        	    	copySuccess = false;
					        	        	    }
					        	        	} else {
					        	       			//log.debug("StudentUploadAction - moveDocuments - isMD - File " + sourceFile + " was not successfully copied. It can be because file with file name already exists in destination\n");
					        	       			copySuccess = false;
					        	       		}
				        	        	}
				        	        	if (copySuccess){
				        	        		isDoRemoveFiles = true;
				        	        		result = "Success";
				        	        	}else{
				        	        		result = "Error - Copying Temporaru Files to Uniflow failed, please try again";
				        	        	}
				        	       	}
							 }
							 if (isDoRemoveFiles){
								//Check if student and group folders are empty. 
								 //Delete them if they are as we don't want to clutter the Unix filesystem unnecessary
								 //First do Student specific folder if it is empty (/tmp/group/studentnr)
								 File testStuDir = new File (sourceDir);
								 if (testStuDir.isDirectory()) {
									 String[] children1 = testStuDir.list(); //Create list of files in folder
									 //log.debug("StudentUploadAction - MoveDocuments - StuDir Before Delete, thus Deleted=" + children1.length);
									 
									 //Delete all files and sub-folders in Student Temp folder
									 FileUtils.cleanDirectory(testStuDir);
									 
									 String[] children2 = testStuDir.list(); //Create list of files in folder
									 //log.debug("StudentUploadAction - MoveDocuments - StuDir After Delete, thus Deleted=" + children2.length);
									 if (children2.length == 0){ //folder empty
										 testStuDir.delete();
										 //log.debug("StudentUploadAction - MoveDocuments - StuDir Empty, thus Deleted: " + testStuDir);
									 }else{
										 //log.debug("StudentUploadAction - MoveDocuments - StuDir not empty: " + testStuDir + ":" + children2.length);
									 }
								 }
				                    
								 //Now do Group Folder if it is empty (/tmp/group)
								 File testGroupDir = new File (groupDir);
								 if (testGroupDir.isDirectory()) {
									 String[] children = testGroupDir.list(); //Create list of files in folder
									 /**Use if you wish to delete files in folder
									  * At this time, we however don't want to delete the folder if it is not empty
				                     */
				                    if (children.length == 0){ //folder empty
				                    	testGroupDir.delete();
				                    	//log.debug("StudentUploadAction - MoveDocuments - GroupDir Empty, thus Deleted: " + testGroupDir);
				                    	log.info("StudentUploadAction MoveDocuments " + stuUpForm.getStudent().getNumber() + " - GroupDir Empty, thus Deleted: " + testGroupDir);
				                    }else{
				                    	//log.debug("StudentUploadAction - MoveDocuments - GroupDir not empty: " + testGroupDir + ":" + children.length);
				                    }
								 }
							 }
					   	} catch (Exception e) {
					   		log.info("StudentUploadAction - MoveDocuments - Exception="+e);
							result = "Error - Moving File to Uniflow failed, please try again";
					   	}
				}
	        } catch(Exception ex ){
	        	log.info("StudentUploadAction - MoveDocuments - Exception="+ex);
	        	result = "Error - Moving File to Uniflow failed, please try again";
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
			log.info("StudentUploadAction - readableFileSize failed - Exception="+e);
			result = "Error - UploadAction - readableFileSize failed";
		}
		return result;
	}

	public void reLoad(StudentUploadForm form) throws Exception{
		form.getDesc().clear();
		form.getMap().clear();
		SavedDocDao savedDocDao = new SavedDocDao();
		//log.debug("StudentUploadForm - reLoad - Reloading uploaded docs..");
		savedDocDao.getAllNonRequiredDocInfo(form.getDesc(), form.getMap(), form.getStudent().getNumber(),form.getStudent().getAcademicYear(),form.getStudent().isStuExist(), form.getStudent().getMatrix());
		for(FileBean fb : form.getRequiredFileBeans()){
			//log.debug("StudentUploadForm - reLoad - Setting FileBean Uploaded..");
			fb.setUploaded(savedDocDao.getSavedDocByDoc(fb.getDoc().getDocCode(),form.getStudent().getNumber(),form.getStudent().getAcademicYear()));
		}
		//log.debug("StudentUploadAction - reLoad - Done");
	}

	public ActionForward nextStep(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// if student number is null, block application
		ActionMessages messages = new ActionMessages();
		StudentUploadForm stuUpForm = (StudentUploadForm) form;

		String nextPage = "";
		String page = stripXSS(request.getParameter("page"));
		//log.debug("StudentUploadAction Nextpage - page="+page+", nextPage=" + nextPage);
		

		if(stuUpForm.getStudent().getNumber() == null){
			//log.debug("StudentUploadAction Nextpage " + request.getSession().getId() + " Student Nr is null or empty when saving qualifications");
			messages.add(ActionMessages.GLOBAL_MESSAGE,
			new ActionMessage("message.generalmessage", "You performed and invalid action or an Error occurred. Please log on again to retry."));
			addErrors(request, messages);
			//log.debug("StudentUploadAction - Nextpage - Return to applyLogin");
			setDropdownListsLogin(request,stuUpForm);
			//log.debug("StudentUploadAction - Nextpage - Goto applyLogin");
			return mapping.findForward("applyLogin");
		}

		if (stuUpForm.getApplyType()==null || "".equals(stuUpForm.getApplyType())){
			stuUpForm.setApplyType("F");
		}
		
		if ("applyLogin".equalsIgnoreCase(page)){
			setDropdownListsLogin(request,stuUpForm);
			return mapping.findForward("applyLogin");
		}
		//log.debug("StudentUploadAction Nextpage - Nextpage=" + nextPage);
		return mapping.findForward(nextPage);
	}

	private void setDropdownListsLogin(HttpServletRequest request, ActionForm form) throws Exception{
		
		StudentUploadForm stuUpForm = (StudentUploadForm) form;
		
		//log.debug("StudentUploadAction - setDropdownListLogin - AcademicYear="+stuUpForm.getStudent().getAcademicYear());

		//log.debug("StudentUploadAction - setDropdownListsLogin - Start");
		setUpYearList(request);
		setUpMonthList(request);
		setUpDayList(request);
		//log.debug("StudentUploadAction - setDropdownListsLogin - End");
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

	public void resetForm(StudentUploadForm stuUpForm, String callAction)	throws Exception {

		//log.debug("StudentUploadAction - resetForm - callAction="+callAction);
		// Clear fields
		
		StudentUploadDAO dao = new StudentUploadDAO();
		
		stuUpForm.resetFormFields();
		
		stuUpForm.getStudent().setNumber("");
		stuUpForm.getStudent().setSurname("");
		stuUpForm.getStudent().setFirstnames("");
		stuUpForm.getStudent().setBirthYear("");
		stuUpForm.getStudent().setBirthMonth("");
		stuUpForm.getStudent().setBirthDay("");
		
		ArrayList<String> dateCheck = dao.validateClosingDate(stuUpForm.getStudent().getAcademicYear());
		if (!dateCheck.isEmpty()){ //Check Dates Array
			for (int i=0; i < dateCheck.size(); i++){
				//log.debug("StudentUploadAction - resetForm - dateCheck="+dateCheck.get(i).toString());
				if ("WAPSTAT".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuUpForm.getStudent().setDateWAPSTAT(true);
				}
			}
		}
		stuUpForm.setAllowLogin(true);
		stuUpForm.getStudent().setAcademicYear(getCurrentAcademicYear());
		stuUpForm.getStudent().setAcademicPeriod(getCurrentAcademicPeriod());
		//log.debug("StudentUploadaction - resetForm - All StudentUploadForm Variables Cleared - Year="+stuUpForm.getStudent().getAcademicYear()+". Period="+stuUpForm.getStudent().getAcademicPeriod());
		
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
		//log.debug("StudentUploadAction - getCurrentAcademicYear: " + acaYear);
		return acaYear;
	}

	public String getCurrentAcademicPeriod() throws Exception {
		String acaPeriod;

		Calendar cal = Calendar.getInstance();
		
		int month = cal.get(Calendar.MONTH)+1; //zero-based
		
		int currentYear = cal.get(Calendar.YEAR);
		int acaYear = Integer.parseInt(getCurrentAcademicYear());
		
		//log.debug("StudentUploadAction - currentYear: " + currentYear+", acaYear: " + acaYear+", month="+month);
		
		if (currentYear < acaYear){
			acaPeriod = "1";
		}else{
			if (month < 8 && month > 3){
				acaPeriod = "2";
			}else{
				acaPeriod = "1";
			}
		}
		//log.debug("StudentUploadAction - getCurrentAcademicPeriod: " + acaPeriod);
		return acaPeriod;
	}


	public String emptySession(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		StudentUploadForm stuUpForm = (StudentUploadForm) form;
		
		//log.debug("StudentUploadAction - emptySession");
		//log.debug("StudentUploadAction - emptySession - FromPage " + stuUpForm.getFromPage());

		// Clear fields
		resetForm(stuUpForm, "StudentUploadAction - emptySession - Return applyLogin");
		stuUpForm.setApplyType("");

		setDropdownListsLogin(request,stuUpForm);
		return "applyLogin";
	}

	public ActionForward cancel(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		//log.debug("StudentUploadAction - Cancel");

		StudentUploadForm stuUpFormOld = (StudentUploadForm) form;

		resetForm(stuUpFormOld, "StudentUploadAction Cancel(Action)");
		
		StudentUploadForm stuUpForm = (StudentUploadForm) form;
		StudentUploadDAO dao = new StudentUploadDAO();
		
		// Clear fields - Just in case
		resetForm(stuUpForm, "StudentUploadAction Cancel(Action)");
		stuUpForm.setWebLoginMsg("");

		ArrayList<String> dateCheck = dao.validateClosingDate(stuUpForm.getStudent().getAcademicYear());
		if (!dateCheck.isEmpty()){ //Check Dates Array
			for (int i=0; i < dateCheck.size(); i++){
				//log.debug("StudentUploadAction - Cancel - dateCheck="+dateCheck.get(i).toString());
				if ("WAPSTAT".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuUpForm.getStudent().setDateWAPSTAT(true);
				}
			}
		}
		
		//log.debug("StudentUploadAction - Cancel - validateClosingDate - AcademicYear="+ stuUpForm.getStudent().getAcademicYear() + ",  AcademicPeriod=" + stuUpForm.getStudent().getAcademicPeriod());

		stuUpForm.setWebLoginMsg("");
		
		//log.debug("StudentUploadAction -Cancel - validateClosingDate: AcademicYear="+ stuUpForm.getStudent().getAcademicYear() + ",  AcademicPeriod=" + stuUpForm.getStudent().getAcademicPeriod());

		setDropdownListsLogin(request,stuUpForm);
		//log.debug("StudentUploadAction - Cancel - Return applyLogin");
		return mapping.findForward("applyLogin");
	}
	
	public ActionForward quit(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		//log.debug("StudentUploadAction - Quit");
		StudentUploadForm stuUpForm = new StudentUploadForm();
		
		StudentUploadDAO dao = new StudentUploadDAO();
		
		// Clear fields - Just in case
		resetForm(stuUpForm, "StudentUploadAction Quit(Action)");
		
		stuUpForm.setWebLoginMsg("");
	
		ArrayList<String> dateCheck = dao.validateClosingDate(stuUpForm.getStudent().getAcademicYear());
		if (!dateCheck.isEmpty()){ //Check Dates Array
			for (int i=0; i < dateCheck.size(); i++){
				//log.debug("StudentUploadAction - Quit - dateCheck=["+dateCheck.get(i).toString()+"]");
				if ("WAPSTAT".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuUpForm.getStudent().setDateWAPSTAT(true);
				}
			}
		}
		
		//log.debug("StudentUploadAction - Quit - validateClosingDate: AcademicYear="+ stuUpForm.getStudent().getAcademicYear() + ",  AcademicPeriod=" + stuUpForm.getStudent().getAcademicPeriod());
		
		//log.debug("StudentUploadAction - Quit - CheckDates - WAPSTAT="+stuUpForm.getStudent().isDateWAPSTAT());
		
		setDropdownListsLogin(request,stuUpForm);
		//log.debug("StudentUploadAction - Quit - Return to applyLogin");
		return mapping.findForward("applyLogin");
	}

	public ActionForward unspecified(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{

		StudentUploadForm stuUpForm = (StudentUploadForm)form;
		
		setDropdownListsLogin(request,stuUpForm);
		//log.debug("StudentUpload: unspecified method call - no value for parameter in request");
		return mapping.findForward("applyLogin");
	}
	
	public String getMonth(int month) {
	    return new DateFormatSymbols().getMonths()[month-1];
	}
	
   /************ basic validation ************/
   public String getBrowserInfo(String stuBrowserAgent) throws Exception {
	       String  browserDetails  =  stuBrowserAgent;
	       String  userAgent       =  browserDetails;
	       String  user            =  userAgent.toLowerCase();
	       String browser = "";
	       String os = "";
	       
	       //log.debug("StudentUploadAction - getBrowserInfo - stuBrowserAgent="+stuBrowserAgent);

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
	        //log.debug("StudentUploadAction - getBrowserInfo - Operating System="+os);
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
	       //log.debug("StudentUploadAction - getBrowserInfo - Browser Name="+browser);
	       
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

		  //log.debug("StudentUploadAction -----------------------------------------------------------------");
		  //log.debug("StudentUploadAction - getAllRequestParamaters - Start");
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
		  //log.debug("StudentUploadAction - getAllRequestParamaters - End");
		  //log.debug("StudentUploadAction -----------------------------------------------------------------");
	  } 
	  
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

    	/** Edmund Enumerate through all parameters received
	  	 * @return **/
	    //getAllRequestParamaters(request, response);
    	
		//log.debug("StudentUploadAction - Execute - Action="+request.getParameter("act"));

        return super.execute(mapping, form, request, response);
   }
}

