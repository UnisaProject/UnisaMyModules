package za.ac.unisa.lms.tools.mdapplications.actions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//files
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;







//import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.LookupDispatchAction;
import org.apache.struts.util.LabelValueBean;
import org.sakaiproject.component.cover.ServerConfigurationService;

import za.ac.unisa.lms.tools.mdapplications.dao.MdApplicationsQueryDAO;
import za.ac.unisa.lms.tools.mdapplications.forms.MdApplicationsForm;
import za.ac.unisa.lms.tools.mdapplications.forms.MdPrev;
import za.ac.unisa.lms.tools.mdapplications.forms.Staff;
import za.ac.unisa.lms.tools.mdapplications.forms.Student;
import za.ac.unisa.lms.tools.mdapplications.forms.StudentFile;
import za.ac.unisa.lms.tools.mdapplications.forms.GeneralItem;
import za.ac.unisa.lms.tools.mdapplications.forms.Qualification;
import za.ac.unisa.lms.tools.mdapplications.actions.GeneralMethods;
import za.ac.unisa.utils.WorkflowFile;
import Srrsa01h.Abean.Srrsa01sRegStudentPersDetail;
import Menu95h.Abean.Menu95S;

@SuppressWarnings("unchecked")
public class MdApplicationsAction extends LookupDispatchAction {
	//private statements add June 2011
	private Pattern regexPattern;
	private Matcher regMatcher;

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
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.display", "login");
		map.put("button.reset", "reset");
		map.put("button.submit","submit");
		map.put("button.input", "input");
		map.put("button.update", "update");
		map.put("button.cancel", "cancel");
		map.put("button.back", "back");
		map.put("button.backto", "back");
		map.put("button.withdraw", "cancel");
		map.put("button.continue", "nextStep");
		map.put("button.submitLoginAdmin", "loginStaff");
		map.put("studinput", "studinput");
		map.put("walkthrough", "walkthrough");
	    map.put("step1", "login");
		map.put("button.request", "savePersonal");
		map.put("submitDocuments", "submitDocuments");
		map.put("button.save", "saveDocuments");
		map.put("button.add.attachment", "addAttachment");
		map.put("button.add", "attachFile");
		map.put("button.submitdoc","submitDocuments");
		map.put("loginAdmin", "loginAdmin");
	return map;
	}


	public ActionForward walkthrough(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
	 throws Exception {
		
		//log.debug("MDApplicationsAction - walkthrough - START");

/*		Type cast the incoming form to mdForm and reset
 */
		MdApplicationsForm mdForm = (MdApplicationsForm) form;
		resetForm(mdForm);

		return mapping.findForward("walkthroughforward");
	}

	public ActionForward loginAdmin(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		//log.debug("MDApplicationsAction - loginAdmin - Start");
	    
		MdApplicationsForm mdForm = (MdApplicationsForm) form;
				
		resetForm(mdForm);
		
		mdForm.setWebLoginMsg("");
	
		mdForm.setFromPage("step0");
				
		//log.debug("MDApplicationsAction - loginAdmin - AcademicYear="+mdForm.getStudent().getAcademicYear());

		
        if (mdForm.isDateWEBMDAPP() || mdForm.isDateWEBMDDOC() || mdForm.isDateWEBMDAPP()){ 
        	mdForm.setWebLoginMsg("Applications for "+ mdForm.getStudent().getAcademicYear());
	    }else{
	    	mdForm.setWebLoginMsg("Applications are closed for Administrators");
	    }

        //log.debug("MDApplicationsAction - loginAdmin - WEBMDAPP="+mdForm.isDateWEBMDAPP()+", WEBMDDOC="+mdForm.isDateWEBMDDOC()+", WEBMDADM="+mdForm.isDateWEBMDADM());
        
		//log.debug("MDApplicationsAction - Return to loginAdmin - Login Screen");
		return mapping.findForward("loginAdmin");
	}
	
	public ActionForward loginStaff(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		//2018 Edmund - Debug
		//getAllRequestParamaters(request, response);
		
		//log.debug("MDApplicationsAction - loginStaff - Start");
	    
		MdApplicationsForm mdForm = (MdApplicationsForm) form;
		GeneralMethods gen = new GeneralMethods();
		ActionMessages messages = new ActionMessages();
		
 		mdForm.getAdminStaff().setNumber(stripXSS(mdForm.getAdminStaff().getNumber()));
		if (mdForm.getAdminStaff().getNumber() == null || "".equalsIgnoreCase(mdForm.getAdminStaff().getNumber().trim()) || "userID".equalsIgnoreCase(mdForm.getAdminStaff().getNumber().trim())){
			//log.debug("MDApplicationsAction - loginStaff - AdminStaff - Number is empty");
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please Enter User ID."));
			addErrors(request, messages);
			return mapping.findForward("loginAdmin");
		}
		//log.debug("MDApplicationsAction - loginStaff - AdminStaff - Number="+mdForm.getAdminStaff().getNumber());
		if (mdForm.getAdminStaff().getNumber().trim().length() > 8 ){
			//log.debug("MDApplicationsAction - loginStaff - AdminStaff - Number is larger than 8 characters");
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please Enter a valid User ID."));
			addErrors(request, messages);
			return mapping.findForward("loginAdmin");
		}
		if (!gen.isNumeric(mdForm.getAdminStaff().getNumber().trim())){
			//log.debug("MDApplicationsAction - loginStaff - AdminStaff - Number is not numeric");
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please Enter a valid User ID. ID not Numeric."));
			addErrors(request, messages);
			return mapping.findForward("loginAdmin");
		}
		mdForm.getAdminStaff().setPassword(stripXSS(mdForm.getAdminStaff().getPassword()));
		if (mdForm.getAdminStaff().getPassword() == null || "".equalsIgnoreCase(mdForm.getAdminStaff().getPassword().trim()) || "userPWD".equalsIgnoreCase(mdForm.getAdminStaff().getPassword().trim())){
			//log.debug("MDApplicationsAction - loginStaff - AdminStaff - Password is empty");
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please Enter a valid User Password."));
			addErrors(request, messages);
			return mapping.findForward("loginAdmin");
		}
		
		//Call Menu95S to Authenticate User
		//log.debug("MDApplicationsAction - loginStaff - START MENU95 LOGON");
		
		Menu95S op = new Menu95S();
		operListener opl = new operListener();
		op.addExceptionListener(opl);
		op.clear();
		
		op.setInWsWorkstationCode("internet");
		op.setInWsWorkstationMacCode("internet");
		op.setInCsfClientServerCommunicationsAction("LI");
			     
		op.setInWsUserNumber(Integer.parseInt(mdForm.getAdminStaff().getNumber().trim()));
		op.setInWsUserPassword(mdForm.getAdminStaff().getPassword().toUpperCase().trim());
		op.setInWsUserPassword32cs(mdForm.getAdminStaff().getPassword().trim());

		//log.debug("MDApplicationsAction - loginStaff - START MENU95 EXECUTE");
		op.execute();
		
		if (opl.getException() != null) throw opl.getException();
		if (op.getExitStateType() < 3) throw new Exception(op.getExitStateMsg());
		
		String result = op.getOutCsfStringsString500();
		//log.debug("MDApplicationsAction - loginStaff - getOutCsfStringsString500="+result);
		
		boolean isAllow = false;
		boolean isF894 = false;
		if ("".equalsIgnoreCase(result)){

			//Check if user has access to any menu items
			if (op.getOutMenuGroupCount() > 0) {
				//log.debug("MDApplicationsAction - loginStaff - User has access to getOutMenuGroupCount="+op.getOutMenuGroupCount());
				for (int i = 0; i < op.getOutMenuGroupCount(); i++){
					//log.debug("MDApplicationsAction - loginStaff - User has access - Menu95 Functions="+op.getOutGWsFunctionNumber(i));
					if (op.getOutGWsFunctionNumber(i) == 894){
						isF894 = true;
						isAllow = true;
						//log.debug("MDApplicationsAction - loginStaff - User has access to F894 - isF894="+isF894);
					}
				}	
				if (isAllow && isF894){
					//log.debug("MDApplicationsAction - loginStaff - Admission="+mdForm.getAdminStaff().getAdmission());
					mdForm.getAdminStaff().setAdmin(true);
					//Get Radio selection for Navigation
					if (mdForm.getAdminStaff().getAdmission() == null || "".equalsIgnoreCase(mdForm.getAdminStaff().getAdmission())){
						//log.debug("MDApplicationsAction - loginStaff - Admission is NULL (No Radio Selected) - Return to Admin Login");
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage", "Please select the required access type. (Step 1/Step 2)"));
						addErrors(request, messages);
						return mapping.findForward("loginAdmin");
					}else{
						mdForm.setLoginType(stripXSS(mdForm.getAdminStaff().getAdmission()));
						//log.debug("MDApplicationsAction - loginStaff - getAdmission="+mdForm.getLoginType());
						if ("APP".equalsIgnoreCase(mdForm.getLoginType())){
							//log.debug("MDApplicationsAction - loginStaff - Admission: "+mdForm.getLoginType()+" - Goto Login for Application");
							mdForm.setWebLoginMsg("Administrator - Master & Doctoral Application");
							return mapping.findForward("login");
						}else if ("DOC".equalsIgnoreCase(mdForm.getLoginType())){
							//log.debug("MDApplicationsAction - loginStaff - Admission: "+mdForm.getLoginType()+" - Goto Login for Documents");
							mdForm.setWebLoginMsg("Administrator - Master & Doctoral Document Upload");
							return mapping.findForward("login");
						}
					}
				}else if (isAllow && !isF894){
					mdForm.getAdminStaff().setAdmin(false);
					//log.debug("MDApplicationsAction - loginStaff - isAdmin="+mdForm.getAdminStaff().isAdmin()+" - No Access");
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "User not permitted to use Administration fuction"));
					addErrors(request, messages);
					return mapping.findForward("loginAdmin");
				}else {
					mdForm.getAdminStaff().setAdmin(false);
					//log.debug("MDApplicationsAction - loginStaff - isAdmin="+mdForm.getAdminStaff().isAdmin()+" - No Access");
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "User not Authenticated"));
					addErrors(request, messages);
					return mapping.findForward("loginAdmin");
				}
			}else{
				if (op.getOutMenuGroupCount() == 0){
					//log.debug("MDApplicationsAction - loginStaff - You do not seem to have access to any Student Admin Functions. Please contact Support.");
					isF894 = false;
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "User not assigned to any functions."));
					addErrors(request, messages);
					return mapping.findForward("loginAdmin");
				}
			}
		}else{
			//log.debug("MDApplicationsAction - loginStaff - ERROR MESSAGE RETURNED:" +result);
			if (result.contains("PASSWORD EXPIRED")){
				//log.debug("MDApplicationsAction - loginStaff - User Password Expired. Use the Student System to rest your password or contact the ICT Helpdesk.");
				messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", "User Password Expired. Use the Student System to rest your password or contact the ICT Helpdesk."));
					addErrors(request, messages);
				return mapping.findForward("loginAdmin");
			}else{
				messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", result));
					addErrors(request, messages);
				return mapping.findForward("loginAdmin");
			}
		}
		//log.debug("MDApplicationsAction - loginStaff - END MENU95 LOGON");
		return mapping.findForward("loginAdmin");
	}
	
	public ActionForward studinput(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
		 throws Exception {

		//log.debug("MDApplicationsAction - studinput - START");
		
	/*		Type cast the incoming form to mdForm and reset
	 */
			MdApplicationsForm mdForm = (MdApplicationsForm) form;
			resetForm(mdForm);
			mdForm.setStudentNumberSearchAttemp(0);
			
			mdForm.setLoginType(request.getParameter("select"));
			if ("loginAdmin".equalsIgnoreCase(mdForm.getLoginType())){
				return mapping.findForward("loginAdmin");
			}else{
				return mapping.findForward("login");
			}
	}

	public ActionForward submitDocuments(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		//log.debug("MDApplicationsAction - submitDocuments - START");

		MdApplicationsForm mdForm = (MdApplicationsForm) form;
		MdApplicationsQueryDAO dao = new MdApplicationsQueryDAO();

		if("doc".equalsIgnoreCase(request.getParameter("context"))){
			// indicates that request is for a existing student document upload
			mdForm.setApplication(false);
		}else{
			mdForm.setApplication(true);
		}

		// add file object to list required files.
		//log.debug("MDapplications: Submit -  Qual="+mdForm.getQual().getQualCode()+", Spec="+mdForm.getQual().getSpecCode());
		mdForm.setRequiredFiles(dao.getAllRequiredDocs(mdForm.getStudent().getNumber(),mdForm.getStudent().getAcademicYear(), mdForm.getQual().getQualCode(), mdForm.getQual().getSpecCode()));
		//Debug
		//log.debug("MDapplications: addAttachment -  RequiredFiles="+mdForm.getRequiredFiles().size());
		/*for (int i = 0; i < mdForm.getRequiredFiles().size(); i++){
			//log.debug("MDapplications: addAttachment -  RequiredFiles List ("+i+")="+mdForm.getRequiredFiles().get(i).getFileName());
		  }
		*/
		return mapping.findForward("docuploadforward");
	}

	public ActionForward submit(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		//log.debug("MDApplicationsAction - submit - START");

		MdApplicationsForm mdForm = (MdApplicationsForm) form;
		ActionMessages messages = new ActionMessages();
		MdApplicationsQueryDAO dao = new MdApplicationsQueryDAO();
		
		mdForm.setApplyType("F");
		mdForm.setFromPage("page1");
		
		if (mdForm.getStudent().getNumber() == null || "".equalsIgnoreCase(mdForm.getStudent().getNumber())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Enter student number."));
			addErrors(request, messages);
			return mapping.findForward("login");
		}

		mdForm.getStudent().setNumber(mdForm.getStudent().getNumber().trim());
		mdForm.getStudent().setNumber(mdForm.getStudent().getNumber().replaceAll(" ",""));
		mdForm.getStudent().setNumber(mdForm.getStudent().getNumber().replaceAll("'",""));
		mdForm.getStudent().setNumber(mdForm.getStudent().getNumber().replaceAll("-",""));
		mdForm.getStudent().setNumber(mdForm.getStudent().getNumber().replaceAll("/",""));
		
		if ((!isNumberValid(mdForm.getStudent().getNumber().trim())) || mdForm.getStudent().getNumber().length() > 8){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Enter a valid student number or go to http://applications.unisa.ac.za to apply for a Student Number."));
			addErrors(request, messages);
			return mapping.findForward("login");
		}
				
		if (mdForm.getStudent().getNumber().length() == 8 ){ 
			//log.debug("check if Student number = 8 characters");
			if ("7".equalsIgnoreCase(mdForm.getStudent().getNumber().substring(0,1))){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "This student number may only be used for non-formal studies. If you do not have a formal student number, first apply for a student number."));
				addErrors(request, messages);
				return mapping.findForward("login");
			}
		}else if (mdForm.getStudent().getNumber().length() == 7 ){
			mdForm.getStudent().setNumber("0" + mdForm.getStudent().getNumber());
		}
		//log.debug("After check if Student number = 8 characters");
		if (mdForm.getStudent().getSurname() == null || "".equalsIgnoreCase(mdForm.getStudent().getSurname())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Enter student surname."));
			addErrors(request, messages);
			return mapping.findForward("login");
		}
		if (mdForm.getStudent().getFirstnames() == null || "".equalsIgnoreCase(mdForm.getStudent().getFirstnames())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Enter student first names."));
			addErrors(request, messages);
			return mapping.findForward("login");
		}
		if (mdForm.getStudent().getBirthDay() == null || "".equalsIgnoreCase(mdForm.getStudent().getBirthDay())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student birthday is incomplete."));
			addErrors(request, messages);
			return mapping.findForward("login");
		}
		if (mdForm.getStudent().getBirthMonth() == null || "".equalsIgnoreCase(mdForm.getStudent().getBirthMonth())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student birthday is incomplete."));
			addErrors(request, messages);
			return mapping.findForward("login");
		}
		if (mdForm.getStudent().getBirthYear() == null || "".equalsIgnoreCase(mdForm.getStudent().getBirthYear())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student birthday is incomplete."));
			addErrors(request, messages);
			return mapping.findForward("login");
		}

		String errorMsg="";
		errorMsg = displayPersonal(mdForm);

		if(!"".equals(errorMsg)){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", errorMsg));
				addErrors(request, messages);

			return mapping.findForward("login");
		}else{			
			if (mdForm.getLoginType() == null || "".equals(mdForm.getLoginType().trim()) || "APP".equalsIgnoreCase(mdForm.getLoginType().trim())){
				setUpQualList(request,"F", mdForm.getStudent().getAcademicYear());
				setDropdownListsStep1(request,mdForm.getApplyType());
				return mapping.findForward("step1forward");
			}else{
				//log.debug("MDapplications: addAttachment for studnr="+mdForm.getStudent().getNumber());
				
				// add file object to list required files.
				//log.debug("MDapplications: Submit -  Qual="+mdForm.getQual().getQualCode()+", Spec="+mdForm.getQual().getSpecCode());
				mdForm.setRequiredFiles(dao.getAllRequiredDocs(mdForm.getStudent().getNumber(),mdForm.getStudent().getAcademicYear(), mdForm.getQual().getQualCode(), mdForm.getQual().getSpecCode()));
				//Debug
				//log.debug("MDapplications: addAttachment -  RequiredFiles="+mdForm.getRequiredFiles().size());
				for (int i = 0; i < mdForm.getRequiredFiles().size(); i++){
					//log.debug("MDapplications: addAttachment -  RequiredFiles List ("+i+")="+mdForm.getRequiredFiles().get(i).getFileName());
				}
				return mapping.findForward("docuploadforward");
			}
		}
	}

	@SuppressWarnings("unused")
	public String displayPersonal(MdApplicationsForm mdForm) throws Exception {
		
		//log.debug("MDApplicationsAction - displayPersonal - START");
/*
 * 	F126-display-D
 */
		Student teststu = new Student();
		//teststu = mdForm.getStudent();
		teststu.setNumber(mdForm.getStudent().getNumber().trim());
		teststu.setFirstnames(mdForm.getStudent().getFirstnames().trim());
		teststu.setSurname(mdForm.getStudent().getSurname().trim());
		teststu.setBirthDay(mdForm.getStudent().getBirthDay());
		teststu.setBirthMonth(mdForm.getStudent().getBirthMonth());
		teststu.setBirthYear(mdForm.getStudent().getBirthYear());

		String result = "";
		Srrsa01sRegStudentPersDetail op = new Srrsa01sRegStudentPersDetail();
		operListener opl = new operListener();
		op.addExceptionListener(opl);
		op.clear();

		op.setInCsfClientServerCommunicationsClientVersionNumber((short) 3);
		op.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
		op.setInCsfClientServerCommunicationsAction("D");
		op.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
		op.setInWsUserNumber(99998);
		op.setInStudentAnnualRecordMkAcademicYear(Short.parseShort(String.valueOf(mdForm.getStudent().getAcademicYear())));
		op.setInStudentAnnualRecordMkAcademicPeriod(Short.parseShort("1"));
		op.setInStudentAnnualRecordMkStudentNr(Integer.parseInt(mdForm.getStudent().getNumber()));

		op.execute();

		if (opl.getException() != null) throw opl.getException();
		if (op.getExitStateType() < 3) throw new Exception(op.getExitStateMsg());

		String errorMessage = op.getOutCsfStringsString500();
		if(!"".equalsIgnoreCase(errorMessage)){
			if ("WARNING".equalsIgnoreCase(errorMessage.substring(0,7))){
				errorMessage="";
			}
		}
		
		/**Ignore Documents error which is caused by "ND" status on STUAPQ. STUAPQ is irrelevant for existing students who
		*  wish to apply for M&D qualifications.
		**/
		if (!"".equals(errorMessage) && !"Application incomplete - documents not received.".equalsIgnoreCase(errorMessage)){
		    return errorMessage;
		}
		if(op.getOutCsfClientServerCommunicationsReturnCode()== 9999){
			// error returned
			return errorMessage;
		}

		//dont use previous qual - prev year qual is not applicable
		mdForm.getQual().setQualCode(op.getOutWsQualificationCode().trim());
		mdForm.getQual().setQualDesc(op.getOutWsQualificationEngDescription().trim());
		mdForm.setSelectedQual(mdForm.getQual().getQualCode() + mdForm.getQual().getQualDesc());
		mdForm.getQual().setSpecCode(op.getOutStudentAnnualRecordSpecialityCode());
		mdForm.getQual().setSpecDesc(op.getOutQualificationSpecialityTypeEnglishDescription().trim());
		mdForm.setSelectedSpes(mdForm.getQual().getSpecCode()+"-"+mdForm.getQual().getQualCode() + mdForm.getQual().getSpecDesc());

		mdForm.getStudent().setSurname(op.getOutWsStudentSurname().trim());
		mdForm.getStudent().setFirstnames(op.getOutWsStudentFirstNames().trim());

		mdForm.getStudent().setMaidenName(op.getOutWsStudentPreviousSurname().trim());
		mdForm.getStudent().setInitials(op.getOutWsStudentInitials().trim());
		mdForm.getStudent().setIdNumber(op.getOutWsStudentIdentityNr().trim());
		mdForm.getStudent().setGender(op.getOutWsStudentGender().trim());
		mdForm.getStudent().getNationalty().setCode(op.getOutWsStudentMkNationality().trim());
		mdForm.getStudent().getHomeLanguage().setCode(op.getOutWsStudentMkHomeLanguage().trim());
		mdForm.getStudent().setLanguage(op.getOutWsStudentMkCorrespondenceLanguage().trim());
		mdForm.getStudent().setTitle(op.getOutWsStudentMkTitle().trim());
		mdForm.getStudent().setSpesCharacter(op.getOutWsStudentSpecialCharacterFlag().trim());
		mdForm.getStudent().setRegistrationMethod(op.getOutStudentAnnualRecordRegistrationMethodCode().trim());
		mdForm.getStudent().setDespatchMethod(op.getOutStudentAnnualRecordDespatchMethodCode().trim());
		mdForm.getStudent().setRegdeliveryMethod(op.getOutStudentAnnualRecordRegDeliveryMethod().trim());
		mdForm.getStudent().setPrevPostgrad(op.getOutWsStudentPreviouslyPostGraduateFlag().trim());
		mdForm.getStudent().setPrevUnisapg(op.getOutWsStudentPreviouslyUnisaPostGradFlag().trim());
		mdForm.getStudent().setPassportNumber(op.getOutWsStudentPassportNo().trim());
		//mdForm.getStudent().setForeignIdNumber(op.getOutWsStudentPassportNo());
		mdForm.getStudent().setTwinflag(op.getOutWsStudentTwinFlag().trim());
		mdForm.getStudent().setComputerTraining(op.getOutStudentAnnualRecordSmDeliveryMethod().trim());
		mdForm.getStudent().setServiceOffice(Integer.toString(op.getOutStudentAnnualRecordMkContactCentre()).trim());

		//op.setInWsStudentBirthDate(calendar);
		String mm = "";
		String dd = "";
		String yy = "";
		String bd = "";

		Calendar calendar = Calendar.getInstance();
		calendar=op.getOutWsStudentBirthDate();
		bd=calendar.getTime().toString();
		//value this gives: bd="Sat Jan 02 00:00:00 CAT 1982"

		//use the formatter
		Date dateRead = new Date ();
		dateRead=calendar.getTime();
        SimpleDateFormat dateformatYYYYMMDD = new SimpleDateFormat("yyyyMMdd");
        StringBuilder nowYYYYMMDD = new StringBuilder( dateformatYYYYMMDD.format( dateRead ) );

		yy=nowYYYYMMDD.substring(0,4);
		mm=nowYYYYMMDD.substring(4,6);
		dd=nowYYYYMMDD.substring(6,8);

		mdForm.getStudent().setBirthYear(yy);
		mdForm.getStudent().setBirthMonth(mm);
		mdForm.getStudent().setBirthDay(dd);

		if (mdForm.getStudent().getIdNumber()==null || "".equalsIgnoreCase(mdForm.getStudent().getIdNumber())){
			mdForm.getStudent().setIdType("P");
		}else{
			mdForm.getStudent().setIdType("R");
		}
		mdForm.getStudent().setLastStatus(op.getOutWsStudentPreviouslyPostGraduateFlag());
		//mdForm.getStudent().getCountry().setCode(op.getOutWsCountryCode());
		mdForm.getStudent().getCountry().setCode(op.getOutWsStudentMkCountryCode().trim());
		mdForm.getStudent().getCountry().setDesc(op.getOutWsCountryEngDescription().trim());
		mdForm.setSelectedCountry(mdForm.getStudent().getCountry().getCode()+mdForm.getStudent().getCountry().getDesc());

		mdForm.getStudent().getNationalty().setCode(op.getOutWsStudentMkNationality().trim());
		mdForm.getStudent().getNationalty().setDesc(op.getOutNationalityWsCountryEngDescription().trim());
		mdForm.setSelectedNationality(mdForm.getStudent().getNationalty().getCode()+mdForm.getStudent().getNationalty().getDesc());

		mdForm.getStudent().getHomeLanguage().setCode(op.getOutWsStudentMkHomeLanguage().trim());
		mdForm.getStudent().getHomeLanguage().setDesc(op.getOutHomeLanguageWsLanguageEngDescription().trim());
		mdForm.setSelectedHomeLanguage(mdForm.getStudent().getHomeLanguage().getCode()+"@"+mdForm.getStudent().getHomeLanguage().getDesc());

		mdForm.getStudent().getPopulationGroup().setCode(op.getOutWsEthnicGroupCode().trim());
		mdForm.getStudent().getPopulationGroup().setDesc(op.getOutWsEthnicGroupEngDescription().trim());
		mdForm.setSelectedPopulationGroup(mdForm.getStudent().getPopulationGroup().getCode()+"@"+mdForm.getStudent().getPopulationGroup().getDesc());

		mdForm.getStudent().getDisability().setCode(Integer.toString(op.getOutStudentAnnualRecordMkDisabilityTypeCode()));
		mdForm.getStudent().getDisability().setDesc(op.getOutWsDisabilityTypeEngDescription().trim());
		mdForm.setSelectedDisability(mdForm.getStudent().getDisability().getCode()+"@"+mdForm.getStudent().getDisability().getDesc());

		mdForm.getStudent().setShareContactDetails(op.getOutStudentAnnualRecordFellowStudentFlag());
		mdForm.getStudent().setLastRegYear(Integer.toString(op.getOutStudentAnnualRecordPrevEducationalInstitutionYr()));

		mdForm.getStudent().getOtherUniversity().setCode(op.getOutStudentAnnualRecordMkOtherEducationalInstitCode());
		mdForm.getStudent().getOtherUniversity().setDesc(op.getOutOtherWsEducationalInstitutionEngName().trim());
		//mdForm.setSelectedCountry(mdForm.getStudent().getOtherUniversity().getCode()+"@"+mdForm.getStudent().getOtherUniversity().getDesc());

		mdForm.getStudent().getPrevInstitution().setCode(op.getOutStudentAnnualRecordMkPrevEducationalInstitCode());
		mdForm.getStudent().getPrevInstitution().setDesc(op.getOutPrevWsEducationalInstitutionEngName().trim());
		mdForm.setSelectedPrevInstitution(mdForm.getStudent().getPrevInstitution()+"@"+mdForm.getStudent().getPrevInstitution().getDesc());

		mdForm.getStudent().getOccupation().setCode(op.getOutStudentAnnualRecordMkOccupationCode());
		mdForm.getStudent().getOccupation().setDesc(op.getOutWsOccupationEngDescription().trim());
		mdForm.setSelectedOccupation(mdForm.getStudent().getOccupation().getCode()+"@"+mdForm.getStudent().getOccupation().getDesc());

		mdForm.getStudent().getEconomicSector().setCode(op.getOutStudentAnnualRecordMkEconomicSectorCode());
		mdForm.getStudent().getEconomicSector().setDesc(op.getOutWsEconomicSectorEngDescription().trim());
		mdForm.setSelectedEconomicSector(mdForm.getStudent().getEconomicSector().getCode()+"@"+mdForm.getStudent().getEconomicSector().getDesc());

		mdForm.getStudent().getPrevActivity().setCode(op.getOutStudentAnnualRecordActivityLastYear().trim());
		mdForm.getStudent().getPrevActivity().setDesc(op.getOutActivityWsGenericCodeEngDescription().trim());
		mdForm.setSelectedPrevActivity(mdForm.getStudent().getPrevActivity().getCode()+"@"+mdForm.getStudent().getPrevActivity().getDesc());

		mdForm.getStudent().getExam().setCode(op.getOutStudentExamCentreMkExamCentreCode());
		mdForm.getStudent().getExam().setDesc(op.getOutWsExamCentreEngDescription().trim());
		mdForm.setSelectedExamCentre(mdForm.getStudent().getExam().getCode()+mdForm.getStudent().getExam().getDesc());

		mdForm.getStudent().setCommMethod(op.getOutStudentAnnualRecordCommunicationMethod());
		mdForm.getStudent().setTutorialMethod(op.getOutStudentAnnualRecordTutorialMethod());
		mdForm.getStudent().setStatusCode(op.getOutStudentAnnualRecordStatusCode());

		//address
	    mdForm.getStudent().setAddressAudit(op.getOutWsAddressAuditFlag());
		mdForm.getStudent().getPostalAddress().setLine1(op.getOutWsAddressAddressLine1().trim());
		mdForm.getStudent().getPostalAddress().setLine2(op.getOutWsAddressAddressLine2().trim());
		mdForm.getStudent().getPostalAddress().setLine3(op.getOutWsAddressAddressLine3().trim());
		mdForm.getStudent().getPostalAddress().setLine4(op.getOutWsAddressAddressLine4().trim());
		mdForm.getStudent().getPostalAddress().setLine5(op.getOutWsAddressAddressLine5().trim());
		mdForm.getStudent().getPostalAddress().setLine6(op.getOutWsAddressAddressLine6().trim());
		mdForm.getStudent().getPostalAddress().setAreaCode(Integer.toString(op.getOutWsAddressPostalCode()).trim());

		String pp="0000"+mdForm.getStudent().getPostalAddress().getAreaCode();
		mdForm.getStudent().getPostalAddress().setAreaCode(pp.substring(pp.length()-4,pp.length()));
		if (!"1015".equalsIgnoreCase(mdForm.getStudent().getCountry().getCode())){
			mdForm.getStudent().getPostalAddress().setAreaCode("");
		}

		mdForm.getStudent().setFaxNr(op.getOutWsAddressFaxNumber().trim());
		mdForm.getStudent().setHomePhone(op.getOutWsAddressHomeNumber().trim());
		mdForm.getStudent().setWorkPhone(op.getOutWsAddressWorkNumber().trim());
		//op.getOutPOBoxCsfStringsString1(op.getOutPOBoxCsfStringsString1());
		mdForm.getStudent().getCountry().setDesc(op.getOutForeignCountryNameWsCountryEngDescription().trim());
		mdForm.getStudent().setCellNr(op.getOutWsAddressV2CellNumber().trim());
		mdForm.getStudent().setEmailAddress(op.getOutWsAddressV2EmailAddress().trim());
		
		/** Edmund change 2014 **/
		String checkMailMyLife = "";
		checkMailMyLife = op.getOutWsAddressV2EmailAddress().trim();
		Integer eCheckIndex=checkMailMyLife.indexOf("mylife.unisa");
		
		if (eCheckIndex >= 1) {
			mdForm.getStudent().setEmailAddressGood(true);
		}
		
		mdForm.getStudent().setContactNr(op.getOutWsAddressV2CourierContactNo().trim());

		//courier address
		mdForm.getStudent().getCourierAddress().setLine1(op.getOutCourierWsAddressAddressLine1().trim());
		mdForm.getStudent().getCourierAddress().setLine2(op.getOutCourierWsAddressAddressLine2().trim());
		mdForm.getStudent().getCourierAddress().setLine3(op.getOutCourierWsAddressAddressLine3().trim());
		mdForm.getStudent().getCourierAddress().setLine4(op.getOutCourierWsAddressAddressLine4().trim());
		mdForm.getStudent().getCourierAddress().setLine5(op.getOutCourierWsAddressAddressLine5().trim());
		mdForm.getStudent().getCourierAddress().setLine6(op.getOutCourierWsAddressAddressLine6().trim());
		mdForm.getStudent().getCourierAddress().setAreaCode(Integer.toString(op.getOutCourierWsAddressPostalCode()).trim());

		pp="0000"+mdForm.getStudent().getCourierAddress().getAreaCode();
		mdForm.getStudent().getCourierAddress().setAreaCode(pp.substring(pp.length()-4,pp.length()));
		if (!"1015".equalsIgnoreCase(mdForm.getStudent().getCountry().getCode())){
			mdForm.getStudent().getCourierAddress().setAreaCode("");
		}

		//physical
		mdForm.getStudent().getPhysicalAddress().setLine1(op.getOutPhysicalWsAddressAddressLine1().trim());
		mdForm.getStudent().getPhysicalAddress().setLine2(op.getOutPhysicalWsAddressAddressLine2().trim());
		mdForm.getStudent().getPhysicalAddress().setLine3(op.getOutPhysicalWsAddressAddressLine3().trim());
		mdForm.getStudent().getPhysicalAddress().setLine4(op.getOutPhysicalWsAddressAddressLine4().trim());
		mdForm.getStudent().getPhysicalAddress().setLine5(op.getOutPhysicalWsAddressAddressLine5().trim());
		mdForm.getStudent().getPhysicalAddress().setLine6(op.getOutPhysicalWsAddressAddressLine6().trim());
		mdForm.getStudent().getPhysicalAddress().setAreaCode(Integer.toString(op.getOutPhysicalWsAddressPostalCode()).trim());

		pp="0000"+mdForm.getStudent().getPhysicalAddress().getAreaCode();
		mdForm.getStudent().getPhysicalAddress().setAreaCode(pp.substring(pp.length()-4,pp.length()));
		if (!"1015".equalsIgnoreCase(mdForm.getStudent().getCountry().getCode())){
			mdForm.getStudent().getPhysicalAddress().setAreaCode("");
		}

		//Validate if screen input is correct student
		if (mdForm.getStudent().getSurname().equalsIgnoreCase(teststu.getSurname())){
		}else{
			result = "The surname you have entered does not correspond with the information on the database";
		}
		if (mdForm.getStudent().getFirstnames().equalsIgnoreCase(teststu.getFirstnames())){
		}else{
			result= "The first name(s) you have entered does not correspond with the information on the database";
		}
		if (mdForm.getStudent().getBirthDay().equals(teststu.getBirthDay())
				&& mdForm.getStudent().getBirthMonth().equals(teststu.getBirthMonth())
				&& mdForm.getStudent().getBirthYear().equals(teststu.getBirthYear())){
		}else{
			result="The birthdate you have entered does not correspond with the information on the database";
		}
		// Closing date  --Integer.parseInt(String.valueOf(mdForm.getStudent().getAcademicYear())))){
		//log.debug("MDApplicationsAction - displayPersonal - isAdmin="+mdForm.getAdminStaff().isAdmin());
		//log.debug("MDApplicationsAction - displayPersonal - validateClosingDate - Year="+mdForm.getStudent().getAcademicYear()+", loginType="+mdForm.getLoginType());

		MdApplicationsQueryDAO dao = new MdApplicationsQueryDAO();
		if (mdForm.getAdminStaff().isAdmin()){
			if (!dao.validateClosingDate(mdForm.getStudent().getAcademicYear(), "WEBMDADM") && "APP".equalsIgnoreCase(mdForm.getLoginType())){
				result="The Administration option is closed.";
				mdForm.setDateWEBMDADM(false);
			}else{
				mdForm.setDateWEBMDADM(true);
			}
		}else{
			if (!dao.validateClosingDate(mdForm.getStudent().getAcademicYear(), "WEBMDAPP") && "APP".equalsIgnoreCase(mdForm.getLoginType())){
				result="The application period for study at Unisa is closed.";
			}
		}

		if (!"".equals(result)){
			//error result - move input back to screen values
			mdForm.getStudent().setSurname(teststu.getSurname());
			mdForm.getStudent().setFirstnames(teststu.getFirstnames());
			mdForm.getStudent().setBirthDay(teststu.getBirthDay());
			mdForm.getStudent().setBirthMonth(teststu.getBirthMonth());
			mdForm.getStudent().setBirthYear(teststu.getBirthYear());
		}else{
			String qualcode;
			String spescode;
			String testYr="";
			List<MdPrev> mdlist = new ArrayList<MdPrev>();
			//dont use qual and spes any more, read by seq nr
			//qualcode = mdForm.getQual().getQualCode();
			//spescode = mdForm.getQual().getSpecCode();
			teststu=dao.getMDrecord(teststu.getNumber());
			if (teststu.getNumber() == null || "".equalsIgnoreCase(teststu.getNumber())){
				teststu.setSeqnr("1");
			}
			//log.debug("StudentNr: " + mdForm.getStudent().getNumber() + ", SeqNr: " + teststu.getSeqnr());
			
			mdlist=dao.getMDhistory(mdForm.getStudent().getNumber(), teststu.getSeqnr());
			mdForm.setMdprevList(mdlist);
			if ("APP".equalsIgnoreCase(mdForm.getLoginType())){
				if (teststu.getNumber() != null && !"".equalsIgnoreCase(teststu.getNumber())){
						mdForm.getStudent().setResearchTopic(teststu.getResearchTopic().trim());
						mdForm.getStudent().setInterestArea(teststu.getInterestArea().trim());
						mdForm.setSelectedInterestArea(teststu.getInterestArea().trim());
						mdForm.getStudent().setSeqnr(teststu.getSeqnr());
						mdForm.getStudent().setAdmstatus(teststu.getAdmstatus());
						mdForm.getStudent().setApplYear(teststu.getApplYear());
						mdForm.getStudent().setAppliedmd(teststu.getAppliedmd());
						mdForm.getStudent().setAppliedqual(teststu.getAppliedqual());
						mdForm.getStudent().setPassedndp(teststu.getPassedndp());
						mdForm.getStudent().setLecturer(teststu.getLecturer());
						testYr=teststu.getApplYear().substring(0,4);
						int testMonth=Integer.parseInt(teststu.getApplYear().substring(4,6));
						int testYear=Integer.parseInt(teststu.getApplYear().substring(0,4));
						if(testMonth>=7 && testMonth <=12){
						   	testYear=testYear+1;
						   	testYr=Integer.toString(testYear);
						}
						mdForm.setReadmd("Y");
						if ("I".equalsIgnoreCase(teststu.getAdmstatus())){
							//mdlist=dao.getMDhistory(mdForm.getStudent().getNumber(), teststu.getSeqnr());
							//mdForm.setMdprevList(mdlist);
						}else{
							if ("Y".equalsIgnoreCase(teststu.getAdmstatus())){
								if( !testYr.equalsIgnoreCase(mdForm.getStudent().getAcademicYear())){
									//mdlist=dao.getMDhistory(mdForm.getStudent().getNumber(), teststu.getSeqnr());
									//mdForm.setMdprevList(mdlist);
									int seqn=0;
									seqn=Integer.parseInt(teststu.getSeqnr());
									seqn=seqn+1;
									mdForm.getStudent().setSeqnr(Integer.toString(seqn));
									mdForm.getStudent().setAdmstatus("I");
									mdForm.setReadmd("N");
								}else{
									result="Your submitted application for admission record has already been processed, no updates allowed ";
								}
							}else{
								if ("N".equalsIgnoreCase(teststu.getAdmstatus())){
									//mdlist=dao.getMDhistory(mdForm.getStudent().getNumber(), teststu.getSeqnr());
									//mdForm.setMdprevList(mdlist);
									int seqn=0;
									seqn=Integer.parseInt(teststu.getSeqnr());
									seqn=seqn+1;
									mdForm.getStudent().setSeqnr(Integer.toString(seqn));
									mdForm.getStudent().setAdmstatus("I");
									mdForm.setReadmd("N");
								}else{
									int testCurAcademicYear=Integer.parseInt(mdForm.getStudent().getAcademicYear());
									
									//2014 Edmund Debug
									//log.debug("Admission Status Student: " + mdForm.getStudent().getAdmstatus());
									//log.debug("Current Application Year: " + testYear);
									//log.debug("Current Academic Year: " + testCurAcademicYear);
									/**2014 Edmund
									* If the student's record exists but has been deleted, thus a status of "DE", then check the 
									* application year. If the application year is before the current academic year, then allow the student
									* to re-apply, otherwise if it the same year, then don't allow them to re-apply.
									**/
									if ("DE".equalsIgnoreCase(mdForm.getStudent().getAdmstatus()) && testYear < testCurAcademicYear){
										int seqn=0;
										seqn=Integer.parseInt(teststu.getSeqnr());
										seqn=seqn+1;
										mdForm.getStudent().setSeqnr(Integer.toString(seqn));
										mdForm.getStudent().setAdmstatus("I");
										mdForm.setReadmd("N");
									}else{
										result="Your submitted application for admission record has already been processed, no updates allowed ";
									}
								}
							}
						}
				}else{
					mdForm.getStudent().setSeqnr("1");
					mdForm.getStudent().setAdmstatus("I");
					mdForm.setReadmd("N");
				}
			}else{
				if (teststu.getNumber() != null && !"".equalsIgnoreCase(teststu.getNumber())){
					mdForm.getStudent().setAdmstatus(teststu.getAdmstatus());
				
					//check if student has mdappl record
					//log.debug("MDapplications: addAttachment for studnr="+mdForm.getStudent().getNumber()+" - Check if student has mdappl record");
					Student st = new Student();
					st = dao.getStudentann(mdForm.getStudent().getNumber(), mdForm.getStudent().getAcademicYear());
					if(st.getMediaAccess1()!= null && !"".equalsIgnoreCase(st.getMediaAccess1())){
						//log.debug("MdApplicationsAction - getMDrecordOLD - studentNr: " + mdForm.getStudent().getNumber() + ", qcode: " + mdForm.getQual().getQualCode() + ", scode: " + mdForm.getQual().getSpecCode());
						st = dao.getMDrecordOLD(mdForm.getStudent().getNumber(), mdForm.getQual().getQualCode(), mdForm.getQual().getSpecCode());
						if(st.getNumber() == null || "".equals(st.getNumber().trim())){
							result="Student does not have an admission application record.";
						}else{
							mdForm.getStudent().setSeqnr(st.getSeqnr());
						}
					}else{
						//log.debug("MDapplications: addAttachment for studnr="+mdForm.getStudent().getNumber()+" - Check mdappl - Student does not have a current year record.");
						result="Student does not have a current year record.";
					}
						
					//log.debug("MDapplications: addAttachment -  Getting MDAPPL Record");

					Student tmpStu = new Student();
					tmpStu = dao.getMDAPPLRecord(mdForm.getStudent().getNumber());
					
					if ("N".equalsIgnoreCase(tmpStu.getAdmstatus())){
						result="Your application has been declined. Please re-apply before uploading any files.";
					}else if (tmpStu.getMediaAccess1() == null || "".equalsIgnoreCase(tmpStu.getMediaAccess1())){
						result="You don't currently have an active application. Please apply before uploading any files.";
					}
				}
			}
		}
		//if any error message =result
		return result;
	}
	
	@SuppressWarnings("unused")
	public void displayPersonalShort(MdApplicationsForm mdForm) throws Exception {
/*
 * 	F126-display-D
 */

		String result = "";
		Srrsa01sRegStudentPersDetail op = new Srrsa01sRegStudentPersDetail();
		operListener opl = new operListener();
		op.addExceptionListener(opl);
		op.clear();

		op.setInCsfClientServerCommunicationsClientVersionNumber((short) 3);
		op.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
		op.setInCsfClientServerCommunicationsAction("D");
		op.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
		op.setInWsUserNumber(99998);
		op.setInStudentAnnualRecordMkAcademicYear(Short.parseShort(String.valueOf(mdForm.getStudent().getAcademicYear())));
		op.setInStudentAnnualRecordMkAcademicPeriod(Short.parseShort("1"));
		op.setInStudentAnnualRecordMkStudentNr(Integer.parseInt(mdForm.getStudent().getNumber()));

		op.execute();

		if (opl.getException() != null) throw opl.getException();
		if (op.getExitStateType() < 3) throw new Exception(op.getExitStateMsg());

		String errorMessage = op.getOutCsfStringsString500();
		if(!"".equalsIgnoreCase(errorMessage)){
			if ("WARNING".equalsIgnoreCase(errorMessage.substring(0,7))){
				errorMessage="";
			}
		}
		
		//dont use previous qual - prev year qual is not applicable
		mdForm.getQual().setQualCode(op.getOutWsQualificationCode().trim());
		mdForm.getQual().setSpecCode(op.getOutStudentAnnualRecordSpecialityCode());
	}

	public String applyStep2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		//log.debug("MDApplicationsAction - applyStep2 - START");
		
		MdApplicationsForm mdForm = (MdApplicationsForm) form;
		ActionMessages messages = new ActionMessages();
		GeneralMethods gen = new GeneralMethods();
		MdApplicationsQueryDAO dao = new MdApplicationsQueryDAO();

		// if student number is null, block application
		if (mdForm.getStudent().getNumber() == null || "".equalsIgnoreCase(mdForm.getStudent().getNumber())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "An error has occurred or you performed and invalid action. Please log on again to retry."));
			addErrors(request, messages);
			return "login";
		}
	
		if (mdForm.getFromPage() == null){
			//Error: Session empty
			return emptySession(mapping,form,request,response);
		}else{
			mdForm.setFromPage("page2");
		}

		mdForm.setStudentNumberSearchAttemp(0);
		// ---------- Check input
		String underpost = " ";
		String code = " ";
		String qtype = " ";
		String qkat = " ";

		// Proposed qualification
		if (mdForm.getSelectedQual() == null || "-1".equals(mdForm.getSelectedQual())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Select your proposed qualification."));
			addErrors(request, messages);
			return "step1forward";
		}else{
			code = dao.validateQualification(mdForm.getSelectedQual().substring(0,5));
			underpost = code.substring(0,1);
			qtype = code.substring(1,2);
			qkat = code.substring(2);
		}

		mdForm.getQual().setQualCode(mdForm.getSelectedQual().substring(0,5));
		mdForm.getQual().setQualDesc(mdForm.getSelectedQual().substring(5));
		mdForm.getQual().setUnderpostGrad(underpost);
		mdForm.getQual().setQualType(qtype);
		mdForm.getQual().setQualKat(qkat);

		String qcode=mdForm.getQual().getQualCode();
		//testing SBL and other special qualifications
		if ("98439".equalsIgnoreCase(qcode) || "98511".equalsIgnoreCase(qcode) ||"98561".equalsIgnoreCase(qcode) ||"98570".equalsIgnoreCase(qcode) ||"98594".equalsIgnoreCase(qcode)){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "This is a pre-selection qualification. To apply for admission to this qualification, please contact the relevant academic department."));
			addErrors(request, messages);
			return "step1forward";
		}else{
			if ("98990".equalsIgnoreCase(qcode) || "98991".equalsIgnoreCase(qcode) ||"98992".equalsIgnoreCase(qcode) ||"98993".equalsIgnoreCase(qcode) ||"98994".equalsIgnoreCase(qcode)){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "This is a pre-selection qualification. To apply for admission to this qualification, please contact the relevant academic department."));
				addErrors(request, messages);
				return "step1forward";
			}
		}
		if ("97837".equalsIgnoreCase(qcode) || "0605X".equalsIgnoreCase(qcode) ||"08052".equalsIgnoreCase(qcode)){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "This qualification is offered by the SBL. Please phone them at 0116520000 or e-mail them at sbl@unisa.ac.za for admission purposes."));
			addErrors(request, messages);
			return "step1forward";
		}

		// Year
		if (mdForm.getStudent().getAcademicYear()== null || "".equals(mdForm.getStudent().getAcademicYear()) || !isValidNumber(mdForm.getStudent().getAcademicYear())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Academic year must be numeric format [CCYY]"));
			addErrors(request, messages);
			return "step1forward";
		}

		// Surname
		if (mdForm.getStudent().getSurname()==null || "".equals(mdForm.getStudent().getSurname().trim())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Enter your surname."));
			addErrors(request, messages);
			return "step1forward";
		}
		// Initials
		if (mdForm.getStudent().getInitials()==null || "".equals(mdForm.getStudent().getInitials().trim())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Enter your initials."));
			addErrors(request, messages);
			return "step1forward";
		}
 		if (!isInitialsGood(mdForm.getStudent().getInitials(), mdForm.getStudent().getFirstnames())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please put spaces between your initials."));
			addErrors(request, messages);
			return "step1forward";
		}
		// Title
		if (mdForm.getStudent().getTitle() == null || "-1".equals(mdForm.getStudent().getTitle())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Select your title."));
			addErrors(request, messages);
			return "step1forward";
		}
		// Full first names
		if (mdForm.getStudent().getFirstnames()==null || "".equals(mdForm.getStudent().getFirstnames().trim())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Enter your first names."));
			addErrors(request, messages);
			return "step1forward";
		}
		// Date of birth
		String errorMessage = gen.validateDateOfBirth(mdForm.getStudent());
		if(!"".equals(errorMessage)){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage",
				errorMessage));
			addErrors(request, messages);
			return "step1forward";
		}
		// RSA Id number/ passport number / foreign id number
		boolean err = false;
		if ((mdForm.getStudent().getIdType()==null || "".equals(mdForm.getStudent().getIdType()))){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Indicate your type of identification."));
			addErrors(request, messages);
			return "step1forward";
		}else {
			// RSA id number
			if("R".equals(mdForm.getStudent().getIdType())){
				if ( mdForm.getStudent().getIdNumber() == null && "".equals(mdForm.getStudent().getIdNumber().trim())){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Enter your RSA identity number."));
					addErrors(request, messages);
					return "step1forward";
				}else if (!gen.isNumeric(mdForm.getStudent().getIdNumber())){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "RSA identity number must be numeric."));
					addErrors(request, messages);
					return "step1forward";
				}else if( mdForm.getStudent().getIdNumber().trim().length()!= 13){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "RSA identity number must consist of 13 numerical characters."));
					addErrors(request, messages);
					return "step1forward";
				}else if (mdForm.getStudent().getPassportNumber()!=null && !"".equals(mdForm.getStudent().getPassportNumber().trim())){
					err = true;
				}else if (mdForm.getStudent().getForeignIdNumber()!=null && !"".equals(mdForm.getStudent().getForeignIdNumber().trim())){
					err = true;
				}
			// Foreign id number
			}else if("F".equals(mdForm.getStudent().getIdType())){
				if(mdForm.getStudent().getForeignIdNumber()==null || "".equals(mdForm.getStudent().getForeignIdNumber().trim())){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Enter your Foreign identity number."));
					addErrors(request, messages);
					return "step1forward";
				}else if (mdForm.getStudent().getPassportNumber()!=null && !"".equals(mdForm.getStudent().getPassportNumber().trim())){
					err = true;
				}else if (mdForm.getStudent().getIdNumber()!=null && !"".equals(mdForm.getStudent().getIdNumber().trim())){
					err = true;
				}
			// Passport number
			}else if("P".equals(mdForm.getStudent().getIdType())){
				if (mdForm.getStudent().getPassportNumber()==null || "".equals(mdForm.getStudent().getPassportNumber().trim())){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Enter your Passport number."));
					addErrors(request, messages);
					return "step1forward";
				}else if (mdForm.getStudent().getForeignIdNumber()!=null && !"".equals(mdForm.getStudent().getForeignIdNumber().trim())){
					err = true;
				}else if (mdForm.getStudent().getIdNumber()!=null && !"".equals(mdForm.getStudent().getIdNumber().trim())){
					err = true;
				}
			}
			if(err){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Indicate one type of identification only."));
				addErrors(request, messages);
				return "step1forward";
			}
		}
		// gender
		if (mdForm.getStudent().getGender()==null || "".equals(mdForm.getStudent().getGender().trim())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Indicate your gender."));
			addErrors(request, messages);
			return "step1forward";
		}
		// language
		if (mdForm.getStudent().getLanguage()==null || "".equals(mdForm.getStudent().getLanguage().trim())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Indicate your language for correspondence."));
			addErrors(request, messages);
			return "step1forward";
		}

		// set disability
		if (mdForm.getSelectedDisability() != null){
			GeneralItem genItem = new GeneralItem();
			genItem = getItem(mdForm.getSelectedDisability());
			mdForm.getStudent().getDisability().setCode(genItem.getCode());
			mdForm.getStudent().getDisability().setDesc(genItem.getDesc());
		}

		//test agreement
		if (mdForm.getAgreeQualInfo() == null || "".equals(mdForm.getAgreeQualInfo().trim())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please click on the circle to indicate that you have read the information on the departmental website."));
			addErrors(request, messages);
			return "step1forward";
		}

		String qualif = mdForm.getQual().getQualCode();
		setUpSpesList(request, qualif, mdForm);
		setDropdownListsStep2(request);
		return "step2forward";
	}

	@SuppressWarnings("unused")
	public String applyStep3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		//log.debug("MDApplicationsAction - applyStep3 - START");
		
		MdApplicationsForm mdForm = (MdApplicationsForm) form;
		ActionMessages messages = new ActionMessages();
		GeneralMethods gen = new GeneralMethods();

		// if student number is null, block application
		if (mdForm.getStudent().getNumber() == null || "".equalsIgnoreCase(mdForm.getStudent().getNumber())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "An error has occurred or you performed and invalid action. Please log on again to retry."));
			addErrors(request, messages);
			return "login";
		}
		
		if (mdForm.getFromPage() == null){
			//Error: Session empty
			return emptySession(mapping,form,request,response);
		}else{
			mdForm.setFromPage("page3");
		}

		String qualif = mdForm.getQual().getQualCode();
		String spescode = mdForm.getQual().getSpecCode();

		// ---------- Check input
		// speciality code
		if (mdForm.getSelectedSpes() == null || "-1".equals(mdForm.getSelectedSpes())){
			//make spaces - can not be mandatory
			mdForm.getQual().setSpecCode(" ");
			mdForm.getQual().setSpecDesc(" ");
		}else{
			mdForm.getQual().setSpecCode(mdForm.getSelectedSpes().substring(0,3));
			mdForm.getQual().setSpecDesc(mdForm.getSelectedSpes().substring(4));


		}
		if (mdForm.getSelectedSpes() == null || "-1".equals(mdForm.getSelectedSpes())){
			if (mdForm.getSpescount()>0){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please select a specialisation code to continue."));
				addErrors(request, messages);
				setDropdownListsStep3(request);
				setUpSpesList(request, qualif, mdForm);
				return "step2forward";
			}
		}

		setUpSpesList(request, qualif, mdForm);
		// ---------- Check input

		//remove spaces
		if (mdForm.getStudent()!= null){
			mdForm.getStudent().setCellNr(mdForm.getStudent().getCellNr().replaceAll(" ",""));
		}

		// check contact numbers
		if(mdForm.getStudent().getHomePhone()== null || "".equals(mdForm.getStudent().getHomePhone())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
        			new ActionMessage("message.generalmessage", "Your home phone number is required."));
			addErrors(request, messages);
			return "step2forward";
		}
		if (mdForm.getStudent().getHomePhone()!= null){
			mdForm.getStudent().setHomePhone(mdForm.getStudent().getHomePhone().replaceAll(" ",""));
		}
		//if(!isValidNumber(mdForm.getStudent().getHomePhone())){
		if(!isPhoneNumber(mdForm.getStudent().getHomePhone())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
        			new ActionMessage("message.generalmessage", "Your home phone number may consist of a dash or +, the rest must be numeric."));
			addErrors(request, messages);
			return "step2forward";
		}

		if (mdForm.getStudent().getWorkPhone()!= null){
			mdForm.getStudent().setWorkPhone(mdForm.getStudent().getWorkPhone().replaceAll(" ",""));
		}
		//if(!isValidNumber(mdForm.getStudent().getWorkPhone())){
		if(!isPhoneNumber(mdForm.getStudent().getWorkPhone())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
        			new ActionMessage("message.generalmessage", "Your work phone number may consist of a dash or +, the rest must be numeric."));
			addErrors(request, messages);
			return "step2forward";
		}
		if (mdForm.getStudent().getFaxNr()!= null){
			mdForm.getStudent().setFaxNr(mdForm.getStudent().getFaxNr().replaceAll(" ",""));
		}
		//if(!isValidNumber(mdForm.getStudent().getFaxNr())){
		if(!isPhoneNumber(mdForm.getStudent().getFaxNr())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
  			new ActionMessage("message.generalmessage", "Your fax number may consist of a dash or +, the rest must be numeric."));
			addErrors(request, messages);
			return "step2forward";
		}

		//cell number
		if(!isPhoneNumber(mdForm.getStudent().getCellNr())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
  			new ActionMessage("message.generalmessage", "Your cell number may consist of a dash or +, the rest must be numeric."));
			addErrors(request, messages);
			return "step2forward";
		}

		// Set country
		if(mdForm.getSelectedCountry() == null || "-1".equals(mdForm.getSelectedCountry()) || mdForm.getSelectedCountry().length() <4){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
  			new ActionMessage("message.generalmessage", "Please select your country."));
			addErrors(request, messages);
			return "step2forward";
		}else{
			mdForm.getStudent().getCountry().setCode(mdForm.getSelectedCountry().substring(0,4));
			mdForm.getStudent().getCountry().setDesc(mdForm.getSelectedCountry().substring(4));
		}
		
		// Email
		if (mdForm.getStudent().getEmailAddress() == null || "".equals(mdForm.getStudent().getEmailAddress().trim())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please enter your e-mail address."));
			addErrors(request, messages);
			return "step2forward";
		}else{
			// test for valid email address format
			//messages = (ActionMessages) mdForm.validate(mapping, request);
			//if (!messages.isEmpty()) {
			//	addErrors(request, messages);
			//	return "step2forward";
			//}
			// New test for valid email address format (Struts validation removed - Nico)
			if (!mdForm.getStudent().getEmailAddressGood()){
				Boolean chkEmail = isEmailValid(mdForm.getStudent().getEmailAddress());
				if (!chkEmail) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Please ensure you typed your e-mail address correctly."));
					addErrors(request, messages);
					return "step2forward";
				}
				String checkMail = mdForm.getStudent().getEmailAddress().trim();
				Integer eCheckIndex=checkMail.indexOf("mylife.unisa");
				
				if (eCheckIndex >= 1) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Please enter a valid E-mail address other than mylife.unisa."));
					addErrors(request, messages);
					return "step2forward";
					
				}
			}
		}
		// Postal address - mandatory
		if (mdForm.getStudent().getPostalAddress().getLine1() == null || "".equals(mdForm.getStudent().getPostalAddress().getLine1().trim())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Enter your postal address."));
			addErrors(request, messages);
			return "step2forward";
		}else{
			// Remove spaces
			mdForm.getStudent().getPostalAddress().setLine1(mdForm.getStudent().getPostalAddress().getLine1().trim());
			if (mdForm.getStudent().getPostalAddress().getLine1().trim().length() > 5){
			if ("po box".equalsIgnoreCase(mdForm.getStudent().getPostalAddress().getLine1().substring(0,6))){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Please enter P O Box with a space between the P and O. " ));
				addErrors(request, messages);
				return "step2forward";
			}
			}
		}

		// Postal code
		if("1015".equals(mdForm.getStudent().getCountry().getCode())){
			if (mdForm.getStudent().getPostalAddress().getAreaCode() == null || "".equals(mdForm.getStudent().getPostalAddress().getAreaCode().trim())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Enter your postal code."));
				addErrors(request, messages);
				return "step2forward";
			} else if(! gen.isNumeric(mdForm.getStudent().getPostalAddress().getAreaCode()) || mdForm.getStudent().getPostalAddress().getAreaCode().trim().length()!=4){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Postal address error - Postal code must consist of 4 numerical characters."));
					addErrors(request, messages);
					return "step2forward";
			}
		}

		if(!"1015".equals(mdForm.getStudent().getCountry().getCode())){
			if (mdForm.getStudent().getPostalAddress().getAreaCode() != null && !"".equals(mdForm.getStudent().getPostalAddress().getAreaCode().trim())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Postal address error - Postal code for South African address only. Foreign address postal/area code must be part of address lines."));
					addErrors(request, messages);
					return "step2forward";
			}
		}

		// Physical address - mandatory
		if (mdForm.getStudent().getPhysicalAddress().getLine1() == null || "".equals(mdForm.getStudent().getPhysicalAddress().getLine1().trim())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Enter your physical address."));
			addErrors(request, messages);
			return "step2forward";
		}else{
			// Remove spaces
			mdForm.getStudent().getPhysicalAddress().setLine1(mdForm.getStudent().getPhysicalAddress().getLine1().trim());
		}

		// Physical address postal code
		if("1015".equals(mdForm.getStudent().getCountry().getCode())){
			if (mdForm.getStudent().getPhysicalAddress().getAreaCode() == null || "".equals(mdForm.getStudent().getPhysicalAddress().getAreaCode().trim())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Physical address error - Enter your postal code."));
				addErrors(request, messages);
				return "step2forward";
			} else if(! gen.isNumeric(mdForm.getStudent().getPhysicalAddress().getAreaCode()) || mdForm.getStudent().getPhysicalAddress().getAreaCode().trim().length()!=4){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Physical address error - Postal code must consist of 4 numerical characters."));
					addErrors(request, messages);
					return "step2forward";
			}
		}

		if(!"1015".equals(mdForm.getStudent().getCountry().getCode())){
			if (mdForm.getStudent().getPhysicalAddress().getAreaCode() != null && !"".equals(mdForm.getStudent().getPhysicalAddress().getAreaCode().trim())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Physical address error - Postal code for South African address only. Foreign address postal/area code must be part of address lines."));
					addErrors(request, messages);
					return "step2forward";
			}
		}

		// Courier address
		if (!"".equals(mdForm.getStudent().getCourierAddress().getLine1().trim())){
			//check courier postal code
			if("1015".equals(mdForm.getStudent().getCountry().getCode())){
				if (mdForm.getStudent().getCourierAddress().getAreaCode() == null || "".equals(mdForm.getStudent().getCourierAddress().getAreaCode().trim())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please enter your courier postal code."));
				addErrors(request, messages);
				return "step2forward";
				} else if(! gen.isNumeric(mdForm.getStudent().getCourierAddress().getAreaCode()) || mdForm.getStudent().getCourierAddress().getAreaCode().trim().length()!=4){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Courier postal code must consist of 4 numerical characters."));
					addErrors(request, messages);
					return "step2forward";
				}
			}
			//check courier contact number
			if (mdForm.getStudent().getContactNr() == null || "".equals(mdForm.getStudent().getContactNr().trim())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Please enter a courier contact number."));
				addErrors(request, messages);
				return "step2forward";
			}
		}else{
			// Remove spaces
			mdForm.getStudent().getCourierAddress().setLine1(mdForm.getStudent().getCourierAddress().getLine1().trim());
		}

		if(!"1015".equals(mdForm.getStudent().getCountry().getCode())){
			if (mdForm.getStudent().getCourierAddress().getAreaCode() != null && !"".equals(mdForm.getStudent().getCourierAddress().getAreaCode().trim())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Courier address error - Postal code for South African address only. Foreign address postal/area code must be part of address lines."));
					addErrors(request, messages);
					return "step2forward";
			}
		}

		if (mdForm.getStudent().getContactNr() != null && !"".equals(mdForm.getStudent().getContactNr().trim())){
			if(!isPhoneNumber(mdForm.getStudent().getContactNr())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
	        			new ActionMessage("message.generalmessage", "Your home phone number may consist of a dash or +, the rest must be numeric."));
				addErrors(request, messages);
				return "step2forward";
			}
		}

		// setup drop down lists
		//setDropdownListsStep3(request, qualif);
		//return "step3forward";
		String qualCode= mdForm.getQual().getQualCode();
		String specCode = mdForm.getQual().getSpecCode();
		setDropdownListsStep4(request, qualCode, specCode);
		return "histforward";
	}

	public String applyStep4(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		//log.debug("MDApplicationsAction - applyStep4 - START");
		
		// call from M&D page next = address page = step2
		MdApplicationsForm mdForm = (MdApplicationsForm) form;
		String qualCode= mdForm.getQual().getQualCode();
		String specCode = mdForm.getQual().getSpecCode();
		ActionMessages messages = new ActionMessages();
		GeneralMethods gen = new GeneralMethods();

		// if student number is null, block application
		if (mdForm.getStudent().getNumber() == null || "".equalsIgnoreCase(mdForm.getStudent().getNumber())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "An error has occurred or you performed and invalid action. Please log on again to retry."));
			addErrors(request, messages);
			return "login";
		}
		
		if(mdForm.getMdprevList().get(0).getHistUniv()==null || "".equalsIgnoreCase(mdForm.getMdprevList().get(0).getHistUniv())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please enter institution attended for previous qualification."));
				addErrors(request, messages);
				setDropdownListsStep4(request, qualCode, specCode);
				return "histforward";
		}
		if(mdForm.getMdprevList().get(0).getHistQual()==null || "".equalsIgnoreCase(mdForm.getMdprevList().get(0).getHistQual())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please enter previous qualification."));
				addErrors(request, messages);
				setDropdownListsStep4(request, qualCode, specCode);
				return "histforward";
		}

		if(mdForm.getMdprevList().get(0).getHistComplete()==null || "".equals(mdForm.getMdprevList().get(0).getHistComplete().trim()) || !gen.isNumeric(mdForm.getMdprevList().get(0).getHistComplete())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", "Please enter completion year of the previous qualification."));
				addErrors(request, messages);
				setDropdownListsStep4(request, qualCode, specCode);
				return "histforward";
		}
		//if(mdForm.getMdprevList().get(0).getHistAccredit()==null || "".equalsIgnoreCase(mdForm.getMdprevList().get(0).getHistAccredit())){
		//	messages.add(ActionMessages.GLOBAL_MESSAGE,
		//			new ActionMessage("message.generalmessage", "Accredited source for the previous qualification is required."));
		//		addErrors(request, messages);
		//		setDropdownListsStep4(request, qualCode, specCode);
		//		return "histforward";
		//}
		//log.debug("Niche: " + mdForm.getSelectedInterestArea());
		if(mdForm.getSelectedInterestArea()==null || "-1".equals(mdForm.getSelectedInterestArea())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", "Please select a niche/focus area"));
				addErrors(request, messages);
				setDropdownListsStep4(request, qualCode, specCode);
				return "histforward";
		}else{
			mdForm.getStudent().setInterestArea(mdForm.getSelectedInterestArea());
			//log.debug("Select Check: " + mdForm.getStudent().getInterestArea());
		}
		if(mdForm.getStudent().getResearchTopic()==null || "".equals(mdForm.getStudent().getResearchTopic())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", "Please enter the topic of your intended research"));
				addErrors(request, messages);
				setDropdownListsStep4(request, qualCode, specCode);
				return "histforward";
		}

		if (mdForm.getStudent().getResearchTopic().length()>300){
			mdForm.getStudent().setResearchTopic(mdForm.getStudent().getResearchTopic().substring(0,299));
		}

		//log.debug("getPassedndp: " + mdForm.getStudent().getPassedndp());
		//log.debug("getAppliedmd: " + mdForm.getStudent().getAppliedmd());
		//log.debug("getAppliedqual: " + mdForm.getStudent().getAppliedqual());

		if(mdForm.getStudent().getPassedndp()==null || "".equalsIgnoreCase(mdForm.getStudent().getPassedndp())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please answer Y/N to question if you passed any NDP modules."));
					addErrors(request, messages);
					setDropdownListsStep4(request, qualCode, specCode);
					return "histforward";
		}else{
			mdForm.getStudent().setPassedndp(mdForm.getStudent().getPassedndp().toUpperCase());
		}

		if(mdForm.getStudent().getAppliedmd()==null || "".equalsIgnoreCase(mdForm.getStudent().getAppliedmd())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please answer Y/N to question if you previously applied for Masters or Doctoral."));
					addErrors(request, messages);
					setDropdownListsStep4(request, qualCode, specCode);
					return "histforward";
		}else{
			mdForm.getStudent().setAppliedmd(mdForm.getStudent().getAppliedmd().toUpperCase());
		}
		if(mdForm.getStudent().getAppliedqual()==null || "".equalsIgnoreCase(mdForm.getStudent().getAppliedqual())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please answer Y/N to question if you previously applied for same qualification."));
					addErrors(request, messages);
					setDropdownListsStep4(request, qualCode, specCode);
					return "histforward";
		}else{
			mdForm.getStudent().setAppliedqual(mdForm.getStudent().getAppliedqual().toUpperCase());
		}

		//setUpSpesList(request, qualif, mdForm); Moved to Page 2
		setDropdownListsStep3(request);
		return "step3forward";
	}

	@SuppressWarnings("unused")
	public String applyStep5(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//log.debug("MDApplicationsAction - applyStep5 - START");

		// call from M&D page next = address page = step2
		MdApplicationsForm mdForm = (MdApplicationsForm) form;
		ActionMessages messages = new ActionMessages();
		GeneralMethods gen = new GeneralMethods();

		// if student number is null, block application
		if (mdForm.getStudent().getNumber() == null || "".equalsIgnoreCase(mdForm.getStudent().getNumber())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "An error has occurred or you performed and invalid action. Please log on again to retry."));
			addErrors(request, messages);
			return "login";
		}
		
		// share contact details
		if (mdForm.getStudent().getShareContactDetails() == null || "".equals(mdForm.getStudent().getShareContactDetails().trim())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Indicate whether your name, e-mail address and contact details may be given to fellow students for academic purposes."));
			addErrors(request, messages);
			setDropdownListsStep3(request);
			return "step3forward";
		}
		// set examination centre
		if (mdForm.getSelectedExamCentre() == null || "-1".equals(mdForm.getSelectedExamCentre())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Select your examination centre."));
			addErrors(request, messages);
			setDropdownListsStep3(request);
			return "step3forward";
		}else{
			mdForm.getStudent().getExam().setCode(mdForm.getSelectedExamCentre().substring(0,5));
			mdForm.getStudent().getExam().setDesc(mdForm.getSelectedExamCentre().substring(5));
		}
		// home language
		if (mdForm.getSelectedHomeLanguage() == null || "-1".equals(mdForm.getSelectedHomeLanguage())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Select your home language."));
			addErrors(request, messages);
			setDropdownListsStep3(request);
			return "step3forward";
		}else{
			GeneralItem genItem = new GeneralItem();
			genItem = getItem(mdForm.getSelectedHomeLanguage());
			mdForm.getStudent().getHomeLanguage().setCode(genItem.getCode());
			mdForm.getStudent().getHomeLanguage().setDesc(genItem.getDesc());
		}
		// nationality
		if (mdForm.getSelectedNationality() == null || "-1".equals(mdForm.getSelectedNationality())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Select your nationality."));
			addErrors(request, messages);
			setDropdownListsStep3(request);
			return "step3forward";
		}else{
			//mdForm.getStudent().getNationalty().setCode(mdForm.getSelectedCountry().substring(0,4));
			//mdForm.getStudent().getNationalty().setDesc(mdForm.getSelectedCountry().substring(4));
			mdForm.getStudent().getNationalty().setCode(mdForm.getSelectedNationality().substring(0,4));
			mdForm.getStudent().getNationalty().setDesc(mdForm.getSelectedNationality().substring(4));
		}
		// population group
		if (mdForm.getSelectedPopulationGroup() == null || "-1".equals(mdForm.getSelectedPopulationGroup())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Select your population group."));
			addErrors(request, messages);
			setDropdownListsStep3(request);
			return "step3forward";
		}else{
			GeneralItem genItem = new GeneralItem();
			genItem = getItem(mdForm.getSelectedPopulationGroup());
			mdForm.getStudent().getPopulationGroup().setCode(genItem.getCode());
			mdForm.getStudent().getPopulationGroup().setDesc(genItem.getDesc());
		}
		// Occupation
		if (mdForm.getSelectedOccupation() == null || "-1".equals(mdForm.getSelectedOccupation())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Select your occupation."));
			addErrors(request, messages);
			setDropdownListsStep3(request);
			return "step3forward";
		}else{
			GeneralItem genItem = new GeneralItem();
			genItem = getItem(mdForm.getSelectedOccupation());
			mdForm.getStudent().getOccupation().setCode(genItem.getCode());
			mdForm.getStudent().getOccupation().setDesc(genItem.getDesc());
		}
		// Economic sector
		if (mdForm.getSelectedEconomicSector() == null || "-1".equals(mdForm.getSelectedEconomicSector())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Select your economic sector."));
			addErrors(request, messages);
			setDropdownListsStep3(request);
			return "step3forward";
		}else{
			GeneralItem genItem = new GeneralItem();
			genItem = getItem(mdForm.getSelectedEconomicSector());
			mdForm.getStudent().getEconomicSector().setCode(genItem.getCode());
			mdForm.getStudent().getEconomicSector().setDesc(genItem.getDesc());
		}
		// previous activity
		if (mdForm.getSelectedPrevActivity() == null || "-1".equals(mdForm.getSelectedPrevActivity())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Select your previous economic activity."));
			addErrors(request, messages);
			setDropdownListsStep3(request);
			return "step3forward";
		}else{
			GeneralItem genItem = new GeneralItem();
			genItem = getItem(mdForm.getSelectedPrevActivity());
			mdForm.getStudent().getPrevActivity().setCode(genItem.getCode());
			mdForm.getStudent().getPrevActivity().setDesc(genItem.getDesc());
		}

		// re-test a few from previous screens
		if ("".equalsIgnoreCase(mdForm.getQual().getQualCode()) || mdForm.getQual().getQualCode()==null){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Qualification is not entered"));
				addErrors(request, messages);
				setDropdownListsStep3(request);
				return "step3forward";
		}
		if (mdForm.getStudent().getComputerTraining() == null || "".equals(mdForm.getStudent().getComputerTraining().trim())){
			mdForm.getStudent().setComputerTraining("N");
		}

		//setUpSpesList(request, qualif,mdForm);
		setDropdownListsStep3(request);
		return "step5forward";
	}

	@SuppressWarnings("unused")
	public ActionForward savePersonal(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		MdApplicationsForm mdForm = (MdApplicationsForm) form;
		ActionMessages messages = new ActionMessages();
		GeneralMethods gen = new GeneralMethods();

		// if student number is null, block application
		if (mdForm.getStudent().getNumber() == null || "".equalsIgnoreCase(mdForm.getStudent().getNumber())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "An error has occurred or you performed and invalid action. Please log on again to retry."));
			addErrors(request, messages);
			return mapping.findForward("login");
		}
		
		String qualif = mdForm.getQual().getQualCode();
		String spescode = mdForm.getQual().getSpecCode();

		// ---------- Check input
		// speciality code
		if (mdForm.getSelectedSpes() == null || "-1".equals(mdForm.getSelectedSpes()) || mdForm.getSelectedSpes().length() < 4){
			//make spaces - can not be mandatory
			mdForm.getQual().setSpecCode(" ");
			mdForm.getQual().setSpecDesc(" ");
		}else{
			mdForm.getQual().setSpecCode(mdForm.getSelectedSpes().substring(0,3));
			mdForm.getQual().setSpecDesc(mdForm.getSelectedSpes().substring(4));
		}

		setUpSpesList(request, qualif, mdForm);
		
		// share contact details
		if (mdForm.getStudent().getShareContactDetails() == null || "".equals(mdForm.getStudent().getShareContactDetails().trim())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Indicate whether your name, e-mail address and contact details may be given to fellow students for academic purposes."));
			addErrors(request, messages);
			setDropdownListsStep3(request);
			//return "step3forward";
			return mapping.findForward("step3forward");
		}
		// set examination centre
		if (mdForm.getSelectedExamCentre() == null || "-1".equals(mdForm.getSelectedExamCentre())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Select your examination centre."));
			addErrors(request, messages);
			setDropdownListsStep3(request);
			//return "step3forward";
			return mapping.findForward("step3forward");
		}else{
			mdForm.getStudent().getExam().setCode(mdForm.getSelectedExamCentre().substring(0,5));
			mdForm.getStudent().getExam().setDesc(mdForm.getSelectedExamCentre().substring(5));
		}
		// home language
		if (mdForm.getSelectedHomeLanguage() == null || "-1".equals(mdForm.getSelectedHomeLanguage())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Select your home language."));
			addErrors(request, messages);
			setDropdownListsStep3(request);
			//return "step3forward";
			return mapping.findForward("step3forward");
		}else{
			GeneralItem genItem = new GeneralItem();
			genItem = getItem(mdForm.getSelectedHomeLanguage());
			mdForm.getStudent().getHomeLanguage().setCode(genItem.getCode());
			mdForm.getStudent().getHomeLanguage().setDesc(genItem.getDesc());
		}
		// nationality
		if (mdForm.getSelectedNationality() == null || "-1".equals(mdForm.getSelectedNationality())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Select your nationality."));
			addErrors(request, messages);
			setDropdownListsStep3(request);
			//return "step3forward";
			return mapping.findForward("step3forward");
		}else{
			//mdForm.getStudent().getNationalty().setCode(mdForm.getSelectedCountry().substring(0,4));
			//mdForm.getStudent().getNationalty().setDesc(mdForm.getSelectedCountry().substring(4));
			mdForm.getStudent().getNationalty().setCode(mdForm.getSelectedNationality().substring(0,4));
			mdForm.getStudent().getNationalty().setDesc(mdForm.getSelectedNationality().substring(4));
		}
		// population group
		if (mdForm.getSelectedPopulationGroup() == null || "-1".equals(mdForm.getSelectedPopulationGroup())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Select your population group."));
			addErrors(request, messages);
			setDropdownListsStep3(request);
			//return "step3forward";
			return mapping.findForward("step3forward");
		}else{
			GeneralItem genItem = new GeneralItem();
			genItem = getItem(mdForm.getSelectedPopulationGroup());
			mdForm.getStudent().getPopulationGroup().setCode(genItem.getCode());
			mdForm.getStudent().getPopulationGroup().setDesc(genItem.getDesc());
		}
		// Occupation
		if (mdForm.getSelectedOccupation() == null || "-1".equals(mdForm.getSelectedOccupation())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Select your occupation."));
			addErrors(request, messages);
			setDropdownListsStep3(request);
			return mapping.findForward("step3forward");
		}else{
			GeneralItem genItem = new GeneralItem();
			genItem = getItem(mdForm.getSelectedOccupation());
			mdForm.getStudent().getOccupation().setCode(genItem.getCode());
			mdForm.getStudent().getOccupation().setDesc(genItem.getDesc());
		}
		// Economic sector
		if (mdForm.getSelectedEconomicSector() == null || "-1".equals(mdForm.getSelectedEconomicSector())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Select your economic sector."));
			addErrors(request, messages);
			setDropdownListsStep3(request);
			return mapping.findForward("step3forward");
		}else{
			GeneralItem genItem = new GeneralItem();
			genItem = getItem(mdForm.getSelectedEconomicSector());
			mdForm.getStudent().getEconomicSector().setCode(genItem.getCode());
			mdForm.getStudent().getEconomicSector().setDesc(genItem.getDesc());
		}
		// previous activity
		if (mdForm.getSelectedPrevActivity() == null || "-1".equals(mdForm.getSelectedPrevActivity())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Select your previous economic activity."));
			addErrors(request, messages);
			setDropdownListsStep3(request);
			return mapping.findForward("step3forward");
		}else{
			GeneralItem genItem = new GeneralItem();
			genItem = getItem(mdForm.getSelectedPrevActivity());
			mdForm.getStudent().getPrevActivity().setCode(genItem.getCode());
			mdForm.getStudent().getPrevActivity().setDesc(genItem.getDesc());
		}

		// re-test a few from previous screens
		if ("".equalsIgnoreCase(mdForm.getQual().getQualCode()) || mdForm.getQual().getQualCode()==null){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Qualification is not entered"));
				addErrors(request, messages);
				setDropdownListsStep3(request);
				return mapping.findForward("step3forward");
		}
		if (mdForm.getStudent().getComputerTraining() == null || "".equals(mdForm.getStudent().getComputerTraining().trim())){
			mdForm.getStudent().setComputerTraining("N");
		}

		//test agreement
		if (mdForm.getAgree() == null || "".equals(mdForm.getAgree().trim())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Indicate your agreement to the declaration and undertaking."));
			addErrors(request, messages);
			setDropdownListsStep3(request);
			return mapping.findForward("step5forward");
		}
		//test agreement flag
		if (!"Y".equalsIgnoreCase(mdForm.getAgree())){
			return mapping.findForward("cancel");
		}

		//count times button pressed
		mdForm.setStudentNumberSearchAttemp(mdForm.getStudentNumberSearchAttemp()+1);
		if (mdForm.getStudentNumberSearchAttemp() > 1){
			//String nrpress = Integer.toString(mdForm.getStudentNumberSearchAttemp());
			messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", "You have already submitted application " ));
			addErrors(request, messages);
			return mapping.findForward("walkthroughforward");
		}

		//Insert MD application record MDAPPL
		MdApplicationsQueryDAO dao = new MdApplicationsQueryDAO();

		/* Set submission time stamp */
		Date date = new java.util.Date();
		String displayDate = (new java.text.SimpleDateFormat("EEEEE dd MMMMM yyyy hh:mm:ss").format(date).toString());
		request.setAttribute("apptime",displayDate);
		request.setAttribute("email",mdForm.getStudent().getEmailAddress());

		//Do execute F126; recreate drop down lists for return page
		String errorMsg="";
		errorMsg = updateStudentNr(mdForm);
		if(!"".equals(errorMsg)){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", errorMsg));
			addErrors(request, messages);
			setUpQualList(request,"F", mdForm.getStudent().getAcademicYear());
			setDropdownListsStep1(request,mdForm.getApplyType());
			return mapping.findForward("step1forward");
		}else{
			if(!"Y".equalsIgnoreCase(mdForm.getReadmd())){
				dao.insertMDrecord(mdForm.getStudent(), mdForm.getQual());
			}else{
				//dao.updMDrecordOld(mdForm.getStudent(), qualif, spescode);
				dao.updateMDrecord(mdForm.getStudent(), qualif, spescode);
			}
			if(mdForm.getStudent().getNumber() == null || "".equals(mdForm.getStudent().getNumber()) || mdForm.getStudent().getSeqnr() == null || "".equals(mdForm.getStudent().getSeqnr())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "A Processing error occurred, Please try again"));
				addErrors(request, messages);
				setUpQualList(request,"F", mdForm.getStudent().getAcademicYear());
				setDropdownListsStep1(request,mdForm.getApplyType());
				return mapping.findForward("step1forward");
			}else{
				dao.insertMDhistory(mdForm.getStudent().getNumber(), mdForm.getStudent().getSeqnr(), mdForm.getMdprevList());
			}
			writeWorkflow(mdForm,date);
		}

		// Write sakai event
		//	eventTrackingService.post(
		//	eventTrackingService.newEvent(EventTrackingTypes.EVENT_STUDENTREGISTRATION_APPLYFORSTUDENTNR, toolManager.getCurrentPlacement().getContext(), false));

    	// clear form variables
	    //resetForm(mdForm);
	    //return mapping.findForward("walkthroughforward");
	    return mapping.findForward("confirmforward");
	}

	@SuppressWarnings("unused")
	public ActionForward nextStep(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		MdApplicationsForm mdForm = (MdApplicationsForm) form;
		String qualCode = mdForm.getQual().getQualCode();
		String specCode = mdForm.getQual().getSpecCode();

		// if student number is null, block application
		ActionMessages messages = new ActionMessages();
		if (mdForm.getStudent().getNumber() == null || "".equalsIgnoreCase(mdForm.getStudent().getNumber())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "An error has occurred or you performed and invalid action. Please log on again to retry."));
			addErrors(request, messages);
			return mapping.findForward("login");
		}
	
		mdForm.setApplyType("F");
		
		//log.debug("MDApplicationsAction - next - nextPage="+request.getParameter("page"));

		String nextPage = "";
		if ("step1".equalsIgnoreCase(request.getParameter("page"))){
			setUpQualList(request,"F", mdForm.getStudent().getAcademicYear());
			setDropdownListsStep1(request,mdForm.getApplyType());
 			nextPage = applyStep2(mapping,form,request, response);
		}else if ("step2".equalsIgnoreCase(request.getParameter("page"))){
			setUpSpesList(request, qualCode,mdForm);
			setDropdownListsStep2(request);
 			nextPage = applyStep3(mapping,form,request, response);
		}else if ("step3".equalsIgnoreCase(request.getParameter("page"))){
			//setDropdownListsStep2(request);
 			nextPage = applyStep5(mapping,form,request, response);
		}else if ("history".equalsIgnoreCase(request.getParameter("page"))){
 			nextPage = applyStep4(mapping,form,request, response);
		}

		//log.debug("MDApplicationsAction - next - Go To="+nextPage);
		return mapping.findForward(nextPage);
	}

	public ActionForward back(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		MdApplicationsForm mdForm = (MdApplicationsForm) form;
		
		//log.debug("MDApplicationsAction - back - prevPage="+request.getParameter("page"));
		String prevPage = "";
		String qualCode = mdForm.getQual().getQualCode();
		String specCode = mdForm.getQual().getSpecCode();

		// if student number is null, block application
		ActionMessages messages = new ActionMessages();
		if (!"uploaddone".equalsIgnoreCase(request.getParameter("page"))) {
			if (mdForm.getStudent().getNumber() == null || "".equalsIgnoreCase(mdForm.getStudent().getNumber())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "An error has occurred or you performed and invalid action. Please log on again to retry."));
					addErrors(request, messages);
					return mapping.findForward("login");
			}
		}
				
		if ("step2".equalsIgnoreCase(request.getParameter("page"))) {
			setUpQualList(request,"F", mdForm.getStudent().getAcademicYear());
 			setDropdownListsStep1(request,mdForm.getApplyType());
			prevPage = "step1forward";
		}else if ("step3".equalsIgnoreCase(request.getParameter("page"))) {
			setDropdownListsStep4(request, qualCode, specCode);
			setDropdownListsStep2(request);
			prevPage = "histforward";
		}else if ("step5".equalsIgnoreCase(request.getParameter("page"))) {
			setUpSpesList(request, qualCode,mdForm);
			setDropdownListsStep3(request);
			prevPage = "step3forward";
		}else if ("history".equalsIgnoreCase(request.getParameter("page"))) {
			setUpSpesList(request, qualCode, mdForm);
			setDropdownListsStep2(request);
 			prevPage = "step2forward";
		}else if ("uploaddone".equalsIgnoreCase(request.getParameter("page"))) {
			resetForm(mdForm);
 			prevPage = "walkthroughforward";
		}else if ("confirmation".equalsIgnoreCase(request.getParameter("page"))) {
			resetForm(mdForm);
 			prevPage = "walkthroughforward";
		}
		return mapping.findForward(prevPage);
	}

	public ActionForward cancel(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		MdApplicationsForm mdForm = (MdApplicationsForm) form;
		
		//log.debug("MDApplicationsAction - cancel");
		resetForm(mdForm);

		return mapping.findForward("walkthroughforward");
	}

	private void setDropdownListsStep1(HttpServletRequest request, String applyType) throws Exception{

		//setUpQualList(request,applyType);
		setUpDisabilityList(request);
		setUpTitleList(request);
	}

	private void setDropdownListsStep2(HttpServletRequest request) throws Exception{

		setUpCountryList(request);
		setUpExaminationCentreList(request);
	}

	private void setDropdownListsStep3(HttpServletRequest request) throws Exception{

		setUpHomeLanguageList(request);
		setUpNationalityList(request);
		setUpPopulationGroupList(request);
		setUpOccupationList(request);
		setUpEconomicSectorList(request);
		setUpOtherUniversityList(request);
		setUpExaminationCentreList(request);
		setUpProvinceList(request);
		//setUpSpesList(request, qualif);
		setPrevActivityList(request);
	}

	private void setDropdownListsStep4(HttpServletRequest request, String qualCode, String specCode) throws Exception{

		setUpInterestArealist(request, qualCode, specCode);
	}

	public ActionForward reset(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		//Type cast the incoming form to MdApplicationsForm and reset
		MdApplicationsForm mdForm = (MdApplicationsForm) form;
		
		//log.debug("MDApplicationsAction - reset");
		resetForm(mdForm);

		return mapping.findForward("login");
	}

	private void setUpQualList(HttpServletRequest request, String appsType, String akdyear) throws Exception{

		MdApplicationsQueryDAO dao = new MdApplicationsQueryDAO();
		List<LabelValueBean> list = dao.getQualList(appsType, akdyear);
		list.add(0,new LabelValueBean("Select from Menu","-1"));
		request.setAttribute("quallist",list);
	}

	private void setUpSpesList(HttpServletRequest request, String qualif, ActionForm form) throws Exception{
		int recc=0;
		MdApplicationsForm mdForm = (MdApplicationsForm) form;
		mdForm.setSpescount(0);
		String akdyear="";
		akdyear=mdForm.getStudent().getAcademicYear();

		MdApplicationsQueryDAO dao = new MdApplicationsQueryDAO();
		List<LabelValueBean> list = dao.getSpesList(qualif,akdyear, mdForm.getAdminStaff().isAdmin());
		recc=list.size();
		list.add(0,new LabelValueBean("Select from Menu","-1"));
		request.setAttribute("speslist",list);

		mdForm.setSpescount(recc);
	}

	private void setUpTitleList(HttpServletRequest request) throws Exception{

		MdApplicationsQueryDAO dao = new MdApplicationsQueryDAO();
		List<LabelValueBean> list = dao.getTitles();
		list.add(0,new LabelValueBean("Select from Menu","-1"));
		request.setAttribute("titlelist",list);
	}

	private void setUpDisabilityList(HttpServletRequest request) throws Exception{

		MdApplicationsQueryDAO dao = new MdApplicationsQueryDAO();
		List<LabelValueBean> list = dao.getDisabilities();
		// Add South Africa as first entry
		list.add(0,new org.apache.struts.util.LabelValueBean("Not Applicable","1@Not Applicable"));
		request.setAttribute("disabilitylist",list);
	}

	private void setUpInterestArealist(HttpServletRequest request, String qualCode, String specCode) throws Exception{

		MdApplicationsQueryDAO dao = new MdApplicationsQueryDAO();
		//log.debug("setUpInterestArealist - QualCode: " + qualCode + " - SpecCode: " + specCode);
		List<LabelValueBean> list = dao.getInterestAreas(qualCode,specCode);
		//log.debug("setUpInterestArealist - List: " + list);
		// Add "Select niche/focus area" as first entry
		list.add(0,new LabelValueBean("Select niche/focus area","-1"));
		request.setAttribute("interestArealist",list);
		//TODO Add "Other" as part of the dropdown... What Value ??
		//"Some Quals have "Other" in the DB"

	}

	private void setUpCountryList(HttpServletRequest request) throws Exception{

		MdApplicationsQueryDAO dao = new MdApplicationsQueryDAO();
		List<LabelValueBean> list = dao.getCountries();
		// Add South Africa as first entry
		//list.add(0,new org.apache.struts.util.LabelValueBean("South Africa","1015South Africa"));
		list.add(0,new LabelValueBean("Select from Menu","-1"));
		request.setAttribute("countrylist",list);
	}

	private void setUpExaminationCentreList(HttpServletRequest request) throws Exception{

		MdApplicationsQueryDAO dao = new MdApplicationsQueryDAO();
		List<LabelValueBean> list = dao.getExaminationCentreList();
		list.add(0,new LabelValueBean("Select from Menu","-1"));
		request.setAttribute("examlist",list);
	}

	private void setUpHomeLanguageList(HttpServletRequest request) throws Exception{

		MdApplicationsQueryDAO dao = new MdApplicationsQueryDAO();
		List<LabelValueBean> list = dao.getLanguages();
		list.add(0,new LabelValueBean("Select from Menu","-1"));
		request.setAttribute("languagelist",list);
	}

	private void setUpNationalityList(HttpServletRequest request) throws Exception{

		MdApplicationsQueryDAO dao = new MdApplicationsQueryDAO();
		List<LabelValueBean> list = dao.getCountries();
		list.add(0,new LabelValueBean("Select from Menu","-1"));
		request.setAttribute("nationalitylist",list);
	}

	
	private void setUpPopulationGroupList(HttpServletRequest request) throws Exception{

		MdApplicationsQueryDAO dao = new MdApplicationsQueryDAO();
		List<LabelValueBean> list = dao.getPopulationGroups();
		list.add(0,new LabelValueBean("Select from Menu","-1"));
		request.setAttribute("populationlist",list);
	}

	private void setUpOccupationList(HttpServletRequest request) throws Exception{

		MdApplicationsQueryDAO dao = new MdApplicationsQueryDAO();
		List<LabelValueBean> list = dao.getOccupations();
		list.add(0,new LabelValueBean("Select from Menu","-1"));
		request.setAttribute("occupationlist",list);
	}

	private void setUpEconomicSectorList(HttpServletRequest request) throws Exception{

		MdApplicationsQueryDAO dao = new MdApplicationsQueryDAO();
		List<LabelValueBean> list = dao.getEconomicSectors();
		list.add(0,new LabelValueBean("Select from Menu","-1"));
		request.setAttribute("economicsectorlist",list);
	}

	private void setPrevActivityList(HttpServletRequest request) throws Exception{

		MdApplicationsQueryDAO dao = new MdApplicationsQueryDAO();
		List<LabelValueBean> list = dao.getPrevActivity();
		list.add(0,new LabelValueBean("Select from Menu","-1"));
		request.setAttribute("prevactivitylist",list);
	}

	private void setUpOtherUniversityList(HttpServletRequest request) throws Exception{

		MdApplicationsQueryDAO dao = new MdApplicationsQueryDAO();
		List<LabelValueBean> list = dao.getOtherUniversities();
		list.add(0,new LabelValueBean("Select from Menu","-1"));
		request.setAttribute("otheruniversitylist",list);
	}

	private void setUpProvinceList(HttpServletRequest request) throws Exception{

		MdApplicationsQueryDAO dao = new MdApplicationsQueryDAO();
		List<LabelValueBean> list = dao.getProvinces();
		list.add(0,new LabelValueBean("Select from Menu","-1"));
		request.setAttribute("provincelist",list);
	}

	private GeneralItem getItem(String inputStr){
		GeneralItem item = new GeneralItem();
		int pos = 0;

		pos = inputStr.indexOf("@");
		item.setDesc(inputStr.substring(pos+1,inputStr.length()));
		item.setCode(inputStr.substring(0,pos));
		return item;
	}

	public String emptySession(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		MdApplicationsForm mdForm = (MdApplicationsForm) form;

		//log.debug("MDApplicationsAction - emptySession");
		// Clear fields
		resetForm(mdForm);
		mdForm.setApplyType("");

		return "home";
	}

    private boolean isInitialsGood(String initials, String firstnames){
    	boolean result = true;
    	String test = "";
    	String test1 = "";
    	int y=initials.length();
    	int z=firstnames.length();
    	String x = "N";

		for (int i = 0; i < z-4; i++) {
			test = firstnames.substring(i,i+1);
			test1 = " ";
			if (i ==0){
				test1 = firstnames.substring(i,i+3);
			}
			if (" ".equals(test)){
				test1 = firstnames.substring(i+1,i+4);

			}
			if ("DE ".equalsIgnoreCase(test1) || "LA ".equalsIgnoreCase(test1) || "LE ".equalsIgnoreCase(test1) || "DU ".equalsIgnoreCase(test1) || "DA ".equalsIgnoreCase(test1)){
				x = "Y";
			}
			if ("TE ".equalsIgnoreCase(test1) || "D' ".equalsIgnoreCase(test1) || "O' ".equalsIgnoreCase(test1) || "E' ".equalsIgnoreCase(test1) || "ST ".equalsIgnoreCase(test1)){
				x = "Y";
			}
		}
		if ("Y".equalsIgnoreCase(x)){
			return result;
		}else{
			result = true;
			for (int i = 0; i < y-2; i++) {
				test = initials.substring(i,i+1);
				test1 = initials.substring(i+1,i+2);
			if (!" ".equals(test) && !" ".equals(test1) ){
					result=false;
				}
			}
		}
		return result;
    }

	private boolean isPhoneNumber(String number){
		boolean result = true;
		String test = "";
		int y=number.length();

		for (int i = 0; i < y-1; i++) {
			test= number.substring(i,i+1);
			//test2=number.valueOf(i);
			if (test !=null && !"".equals(test) && !" ".equals(test) && !"-".equals(test) && !"/".equals(test) && !"+".equals(test)){
				if ("0".equals(test) || "1".equals(test) || "2".equals(test) || "3".equals(test) || "4".equals(test) || "5".equals(test) || "6".equals(test) || "7".equals(test) || "8".equals(test)  || "9".equals(test)){
					//log.debug(test);
				}else{
					result=false;
				}
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
	
	public String updateStudentNr(MdApplicationsForm mdForm) throws Exception {

		  String result = "";

		 /* Thread.sleep(Long.parseLong("1000"));*/ // if two audit trails were to be written simultaneously

		  // ------------------------------- update F126 -------------------------
		  Srrsa01sRegStudentPersDetail op2 = new Srrsa01sRegStudentPersDetail();
		  operListener opl2 = new operListener();
		  op2.addExceptionListener(opl2);
		  op2.clear();

		  // qualification code
		  op2.setInWsQualificationCode(mdForm.getQual().getQualCode());//from screen

		  //stuann
		  op2.setInStudentAnnualRecordMkAcademicYear(Short.parseShort(String.valueOf(mdForm.getStudent().getAcademicYear())));
		  op2.setInStudentAnnualRecordMkAcademicPeriod(Short.parseShort("1"));
		  op2.setInStudentAnnualRecordMkStudentNr(Integer.parseInt(mdForm.getStudent().getNumber()));
		  op2.setInStudentAnnualRecordMkDisabilityTypeCode(Short.parseShort(String.valueOf(mdForm.getStudent().getDisability().getCode())));//from screen
		  op2.setInStudentAnnualRecordFellowStudentFlag(mdForm.getStudent().getShareContactDetails());//from screen
		  op2.setInStudentAnnualRecordPreviousUnisaFlag("N");
		  if (mdForm.getStudent().getLastRegYear()!= null && !mdForm.getStudent().getLastRegYear().equalsIgnoreCase("")){
		     op2.setInStudentAnnualRecordPrevEducationalInstitutionYr(Short.parseShort(mdForm.getStudent().getLastRegYear()));
		  }
		  if (mdForm.getStudent().getOtherUniversity().getCode()!= null){
		     op2.setInStudentAnnualRecordMkOtherEducationalInstitCode(mdForm.getStudent().getOtherUniversity().getCode());
		  }
		  else {
			  op2.setInStudentAnnualRecordMkOtherEducationalInstitCode("9997");
		  }
		  if (mdForm.getStudent().getPrevInstitution().getCode()!= null){
			  op2.setInStudentAnnualRecordMkPrevEducationalInstitCode(mdForm.getStudent().getPrevInstitution().getCode());
		  }
		  else {
			  op2.setInStudentAnnualRecordMkPrevEducationalInstitCode("9997");
		  }
		  op2.setInStudentAnnualRecordRegistrationMethodCode("P");
		  op2.setInStudentAnnualRecordDespatchMethodCode("P");
		  op2.setInStudentAnnualRecordMkOccupationCode(mdForm.getStudent().getOccupation().getCode());//from screen
		  op2.setInStudentAnnualRecordMkEconomicSectorCode(mdForm.getStudent().getEconomicSector().getCode());//from screen
		  op2.setInStudentAnnualRecordRegDeliveryMethod("E"); //from screen
		  //op2.setInStudentAnnualRecordPipelineStudent(mdForm.getStudent().getMediaAccess());
		  op2.setInStudentAnnualRecordCommunicationMethod(mdForm.getStudent().getCommMethod());
		  op2.setInStudentAnnualRecordSpecialityCode(mdForm.getQual().getSpecCode()); //from screen
		  op2.setInStudentAnnualRecordActivityLastYear(mdForm.getStudent().getPrevActivity().getCode());
		  op2.setInStudentAnnualRecordSmDeliveryMethod(mdForm.getStudent().getComputerTraining());
		  if (mdForm.getStudent().getServiceOffice()!= null){
			 op2.setInStudentAnnualRecordMkContactCentre(Short.parseShort(mdForm.getStudent().getServiceOffice()));
		  }
		  op2.setInStudentAnnualRecordPrevEducationalInstitutionYr(Short.parseShort(mdForm.getStudent().getLastRegYear()));

		  Calendar calendar = Calendar.getInstance();
		  SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		  Date dueDate = formatter.parse(mdForm.getStudent().getBirthYear()+mdForm.getStudent().getBirthMonth()+mdForm.getStudent().getBirthDay());
		  calendar.setTime(dueDate);
		  op2.setInWsStudentBirthDate(calendar);

		  // student
		  op2.setInWsStudentNr(Integer.parseInt(mdForm.getStudent().getNumber()));
		  op2.setInWsStudentSpecialCharacterFlag("N");
		  op2.setInWsStudentMkTitle(mdForm.getStudent().getTitle());
		  op2.setInWsStudentSurname(mdForm.getStudent().getSurname());
		  op2.setInWsStudentFirstNames(mdForm.getStudent().getFirstnames());
		  op2.setInWsStudentPreviousSurname(mdForm.getStudent().getMaidenName());
		  op2.setInWsStudentV3Initials(mdForm.getStudent().getInitials());
		  op2.setInWsStudentIdentityNr(mdForm.getStudent().getIdNumber());
		  op2.setInWsStudentGender(mdForm.getStudent().getGender());
		  op2.setInWsStudentMkNationality(mdForm.getStudent().getNationalty().getCode());//from screen
		  op2.setInWsStudentMkHomeLanguage(mdForm.getStudent().getHomeLanguage().getCode());//from screen
		  op2.setInWsStudentMkCorrespondenceLanguage(mdForm.getStudent().getLanguage());//from screen
		  //twin- no input

		  //if ("P".equalsIgnoreCase(mdForm.getStudent().getLastStatus())){
		  op2.setInWsStudentPreviouslyPostGraduateFlag(mdForm.getStudent().getPrevPostgrad());
		  op2.setInWsStudentMkCountryCode(mdForm.getStudent().getCountry().getCode());
		  op2.setInWsStudentPassportNo(mdForm.getStudent().getPassportNumber());

		  // ethnic group
		  op2.setInWsEthnicGroupCode(mdForm.getStudent().getPopulationGroup().getCode());
		  op2.setInWsCountryCode(mdForm.getStudent().getCountry().getCode());
		  op2.setInWsCountryEngDescription(mdForm.getStudent().getCountry().getDesc());

		  // address control flag
		  //op2.setInAddressControlCodeCsfStringsString28(op2.getOutAddressControlCodeCsfStringsString28());

		  //address
		  op2.setInWsAddressAuditFlag("N");
		  op2.setInWsAddressAddressLine1(mdForm.getStudent().getPostalAddress().getLine1().toUpperCase());
		  op2.setInWsAddressAddressLine2(mdForm.getStudent().getPostalAddress().getLine2().toUpperCase());
		  op2.setInWsAddressAddressLine3(mdForm.getStudent().getPostalAddress().getLine3().toUpperCase());
		  op2.setInWsAddressAddressLine4(mdForm.getStudent().getPostalAddress().getLine4().toUpperCase());
		  op2.setInWsAddressAddressLine5(mdForm.getStudent().getPostalAddress().getLine5().toUpperCase());
		  op2.setInWsAddressAddressLine6(mdForm.getStudent().getPostalAddress().getLine6().toUpperCase());
		  if (mdForm.getStudent().getPostalAddress().getAreaCode()!= null && !mdForm.getStudent().getPostalAddress().getAreaCode().equalsIgnoreCase("")){
			  op2.setInWsAddressPostalCode(Short.parseShort(mdForm.getStudent().getPostalAddress().getAreaCode()));
		  }
		  op2.setInWsAddressFaxNumber(mdForm.getStudent().getFaxNr());//from screen
		  op2.setInWsAddressHomeNumber(mdForm.getStudent().getHomePhone());//from screen
		  op2.setInWsAddressWorkNumber(mdForm.getStudent().getWorkPhone());//from screen
		//op2.setInPOBoxCsfStringsString1(op.getOutPOBoxCsfStringsString1());
		  op2.setInForeignCountryNameWsCountryEngDescription(mdForm.getStudent().getCountry().getDesc().toUpperCase());
		  op2.setInWsAddressV2CellNumber(mdForm.getStudent().getCellNr());//from screen
		  op2.setInWsAddressV2EmailAddress(mdForm.getStudent().getEmailAddress());//from screen
		  op2.setInWsAddressV2CourierContactNo(mdForm.getStudent().getContactNr());

		  //courier address
		  op2.setInCourierWsAddressAddressLine1(mdForm.getStudent().getCourierAddress().getLine1().toUpperCase());
		  op2.setInCourierWsAddressAddressLine2(mdForm.getStudent().getCourierAddress().getLine2().toUpperCase());
		  op2.setInCourierWsAddressAddressLine3(mdForm.getStudent().getCourierAddress().getLine3().toUpperCase());
		  op2.setInCourierWsAddressAddressLine4(mdForm.getStudent().getCourierAddress().getLine4().toUpperCase());
		  op2.setInCourierWsAddressAddressLine5(mdForm.getStudent().getCourierAddress().getLine5().toUpperCase());
		  op2.setInCourierWsAddressAddressLine6(mdForm.getStudent().getCourierAddress().getLine6().toUpperCase());
		  if (mdForm.getStudent().getCourierAddress().getAreaCode()!= null && !mdForm.getStudent().getCourierAddress().getAreaCode().equalsIgnoreCase("")){
		     op2.setInCourierWsAddressPostalCode(Short.parseShort(mdForm.getStudent().getCourierAddress().getAreaCode()));
		  }
		  //op2.setInCourierWsAddressReferenceNo(op.getOutCourierWsAddressReferenceNo());
		  //op2.setInCourierWsAddressType(op.getOutCourierWsAddressType());
		  //op2.setInCourierWsAddressCategory(op.getOutCourierWsAddressCategory());

		  //physical
		  op2.setInPhysicalWsAddressAddressLine1(mdForm.getStudent().getPhysicalAddress().getLine1().toUpperCase());
		  op2.setInPhysicalWsAddressAddressLine2(mdForm.getStudent().getPhysicalAddress().getLine2().toUpperCase());
		  op2.setInPhysicalWsAddressAddressLine3(mdForm.getStudent().getPhysicalAddress().getLine3().toUpperCase());
		  op2.setInPhysicalWsAddressAddressLine4(mdForm.getStudent().getPhysicalAddress().getLine4().toUpperCase());
		  op2.setInPhysicalWsAddressAddressLine5(mdForm.getStudent().getPhysicalAddress().getLine5().toUpperCase());
		  op2.setInPhysicalWsAddressAddressLine6(mdForm.getStudent().getPhysicalAddress().getLine6().toUpperCase());
		  if (mdForm.getStudent().getPhysicalAddress().getAreaCode()!= null && !mdForm.getStudent().getPhysicalAddress().getAreaCode().equalsIgnoreCase("")){
			  op2.setInPhysicalWsAddressPostalCode(Short.parseShort(mdForm.getStudent().getPhysicalAddress().getAreaCode()));
		  }
		  //op2.setInPhysicalWsAddressReferenceNo(op.getOutPhysicalWsAddressReferenceNo());
		  //op2.setInPhysicalWsAddressType(op.getOutPhysicalWsAddressType());
		  //op2.setInPhysicalWsAddressCategory(op.getOutPhysicalWsAddressCategory());

		  // exam centre
		  op2.setInStudentExamCentreMkExamCentreCode(mdForm.getStudent().getExam().getCode());

		  // set all flags to yes
		  op2.setInChangedAddressCsfStringsString1("Y");
		  op2.setInChangedContactNumberCsfStringsString1("Y");
		  op2.setInChangedCourierAddressCsfStringsString1("Y");
		  op2.setInChangedPhysicalAddressCsfStringsString1("Y");
		  op2.setInChangedStudentAnnRecordCsfStringsString1("Y");
		  op2.setInChangedStudentCsfStringsString1("Y");

		  // set email
		  op2.setInEmailOrFaxCsfStringsString1("E");
		  op2.setInEmailToCsfStringsString132(mdForm.getStudent().getEmailAddress());
		  op2.setInEmailFromCsfStringsString132("study-info@unisa.ac.za");

		  op2.setInCsfClientServerCommunicationsClientVersionNumber((short) 3);
		  op2.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
		  op2.setInCsfClientServerCommunicationsAction("U");
		  op2.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
		  op2.setInWsUserNumber(99998);
		  op2.setInWsFunctionNumber(999);

		  op2.execute();

		  if (opl2.getException() != null) throw opl2.getException();
		  if (op2.getExitStateType() < 3) throw new Exception(op2.getExitStateMsg());

		  String errorMessage = op2.getOutCsfStringsString500();
		  errorMessage = op2.getOutCsfStringsString500();
		  if(op2.getOutCsfClientServerCommunicationsReturnCode()== 99){
			  // error returned
			  return errorMessage;
		  }

		  // return error only exist on the following return codes
		  //switch (op2.getOutCsfClientServerCommunicationsReturnCode()) {
		  //case 2003: result = errorMessage;
		 //            break;
		  //case 2004: result = errorMessage;
		  //           break;
		  //case 2005: result = errorMessage;
		  //           break;
		  //case 2001: result = errorMessage;
		  //           break;
		  //case 13:   result = errorMessage;
		  //           break;
		  //case 2014: result = errorMessage;
		  //           break;
		  //default:   break;
		  //}

		  if (op2.getOutCsfClientServerCommunicationsReturnCode()==48 || op2.getOutCsfClientServerCommunicationsReturnCode()==49 ){
			    // ALL OK
			    result = "";
			    //result = errorMessage;
			    // set application reference number
			    mdForm.getStudent().setNumber(Integer.toString(op2.getOutStudentAnnualRecordMkStudentNr()));// convert int to String
			    mdForm.setNumberBack(Integer.toString(op2.getOutStudentAnnualRecordMkStudentNr()));
			    mdForm.setEmailBack(op2.getOutEmailToCsfStringsString132());
	        	if (op2.getOutWsStudentApplicationApplicationDate()!= null){
	        		DateFormat strDate = new SimpleDateFormat( "dd-MM-yyyy" );
	        		mdForm.setTimeBack(strDate.format(op2.getOutWsStudentApplicationApplicationDate().getTime()));;
	        	}
		  		}else{
			    // Error Occurred
		  			result = errorMessage;
		  		}

		 return result;

		}
	
	@SuppressWarnings("unused")
	private void writeWorkflow(MdApplicationsForm mdForm, Date applyDateTime) throws Exception{

		MdApplicationsQueryDAO dao = new MdApplicationsQueryDAO();
		
		String type = "L"; /* L = local F=Foreign */
		String wflType = "M";
		
		boolean isSBLQual = false;
		isSBLQual = dao.isSBLQual(mdForm.getQual().getQualCode());
		if (isSBLQual){
			wflType = "SBL";
		}

		/* set local or foreign */
		if (!"1015".equals(mdForm.getStudent().getCountry().getCode())){
			type = "F";
		}

		/* write to file */
		//WorkflowFile file = new WorkflowFile("MDapply_"+ type + "_"+ mdForm.getStudent().getNumber(),wflType);
		WorkflowFile file = new WorkflowFile("MDapply_" + mdForm.getStudent().getNumber(),wflType);
		file.add(" Subject: Application for M & D admission \r\n");
		String displayDate = (new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(applyDateTime).toString());
		file.add(" Date received: " + displayDate + "\r\n");
		file.add(" The following request was received through the Unisa Web Server: \r\n");
		file.add(" Details: \r\n");
		file.add(" Student Number              = " + mdForm.getStudent().getNumber() + "\r\n");
		file.add(" Proposed qualification      = " + mdForm.getQual().getQualCode()+" "+ mdForm.getQual().getQualDesc()+ "\r\n");
		file.add(" Specialisation code         = " + mdForm.getQual().getSpecCode() + " " + "\r\n");
		file.add(" Surname                     = " + mdForm.getStudent().getSurname() + "\r\n");
		file.add(" Initials                    = " + mdForm.getStudent().getInitials() + "\r\n");
		file.add(" Title                       = " + mdForm.getStudent().getTitle() + "\r\n");
		file.add(" First names                 = " + mdForm.getStudent().getFirstnames() + "\r\n");
		file.add(" Maiden name / \r\n");
		file.add(" Previous surname            = " + mdForm.getStudent().getMaidenName() + "\r\n");
		file.add(" Date of birth               = Year: " + mdForm.getStudent().getBirthYear()+ " Month: "+ mdForm.getStudent().getBirthMonth() +" Day: "+ mdForm.getStudent().getBirthDay()+"\r\n");
		file.add(" Gender (M/F)                = " + mdForm.getStudent().getGender() + "\r\n");
		if(mdForm.getStudent().getLanguage() != null && "A".equalsIgnoreCase(mdForm.getStudent().getLanguage())){
		file.add(" Correspondence language     = Afrikaans \r\n");
		} else{
		file.add(" Correspondence language     = English \r\n");
		}
		if("R".equalsIgnoreCase(mdForm.getStudent().getIdType())){
		file.add(" RSA identity number         = " + mdForm.getStudent().getIdNumber() + "\r\n");
		}else if("P".equalsIgnoreCase(mdForm.getStudent().getIdType())){
	    file.add(" Foreign ID/Passport number  = " + mdForm.getStudent().getPassportNumber()+ "\r\n");
		}else if("F".equalsIgnoreCase(mdForm.getStudent().getIdType())){
	    file.add(" Foreign ID/Passport number  = " + mdForm.getStudent().getForeignIdNumber()+ "\r\n");
		}
		file.add(" Disability code             = " + mdForm.getStudent().getDisability().getCode()+ " "+ mdForm.getStudent().getDisability().getDesc() + "\r\n");
		file.add(" Home telephone number       = " + mdForm.getStudent().getHomePhone()+ "\r\n");
		file.add(" Work telephone number       = " + mdForm.getStudent().getWorkPhone() + "\r\n");
		file.add(" Cell number                 = " + mdForm.getStudent().getCellNr() + "\r\n");
		file.add(" Fax number                  = " + mdForm.getStudent().getFaxNr() + "\r\n");
		file.add(" E-mail address              = " + mdForm.getStudent().getEmailAddress() + "\r\n");
		file.add(" Contact number              = " + mdForm.getStudent().getContactNr() + "\r\n");
		file.add(" Postal address              = " + mdForm.getStudent().getPostalAddress().getLine1()+ "\r\n");
		if(mdForm.getStudent().getPostalAddress().getLine2()!=null && !"".equals(mdForm.getStudent().getPostalAddress().getLine2())){
			file.add("                             = " + mdForm.getStudent().getPostalAddress().getLine2()+ "\r\n");
		}
		if(mdForm.getStudent().getPostalAddress().getLine3()!=null && !"".equals(mdForm.getStudent().getPostalAddress().getLine3())){
			file.add("                             = " + mdForm.getStudent().getPostalAddress().getLine3()+ "\r\n");
		}
		if(mdForm.getStudent().getPostalAddress().getLine4()!=null && !"".equals(mdForm.getStudent().getPostalAddress().getLine4())){
			file.add("                             = " + mdForm.getStudent().getPostalAddress().getLine4()+ "\r\n");
		}
		if(mdForm.getStudent().getPostalAddress().getLine5()!=null && !"".equals(mdForm.getStudent().getPostalAddress().getLine5())){
			file.add("                             = " + mdForm.getStudent().getPostalAddress().getLine5()+ "\r\n");
		}
		if(mdForm.getStudent().getPostalAddress().getLine6()!=null && !"".equals(mdForm.getStudent().getPostalAddress().getLine6())){
			file.add("                             = " + mdForm.getStudent().getPostalAddress().getLine6()+ "\r\n");
		}
		file.add(" Postal code                 = " + mdForm.getStudent().getPostalAddress().getAreaCode() + "\r\n");
		file.add(" Physical address            = " + mdForm.getStudent().getPhysicalAddress().getLine1() + "\r\n");
		if(mdForm.getStudent().getPhysicalAddress().getLine2()!=null && !"".equals(mdForm.getStudent().getPhysicalAddress().getLine2())){
			file.add("                             = " + mdForm.getStudent().getPhysicalAddress().getLine2()+ "\r\n");
		}
		if(mdForm.getStudent().getPhysicalAddress().getLine3()!=null && !"".equals(mdForm.getStudent().getPhysicalAddress().getLine3())){
			file.add("                             = " + mdForm.getStudent().getPhysicalAddress().getLine3()+ "\r\n");
		}
		if(mdForm.getStudent().getPhysicalAddress().getLine4()!=null && !"".equals(mdForm.getStudent().getPhysicalAddress().getLine4())){
			file.add("                             = " + mdForm.getStudent().getPhysicalAddress().getLine4()+ "\r\n");
		}
		if(mdForm.getStudent().getPhysicalAddress().getLine5()!=null && !"".equals(mdForm.getStudent().getPhysicalAddress().getLine5())){
			file.add("                             = " + mdForm.getStudent().getPhysicalAddress().getLine5()+ "\r\n");
		}
		if(mdForm.getStudent().getPhysicalAddress().getLine6()!=null && !"".equals(mdForm.getStudent().getPhysicalAddress().getLine6())){
			file.add("                             = " + mdForm.getStudent().getPhysicalAddress().getLine6()+ "\r\n");
		}
		file.add(" Physical addr postal code   = " + mdForm.getStudent().getPhysicalAddress().getAreaCode() + "\r\n");
		file.add(" Courier address             = " + mdForm.getStudent().getCourierAddress().getLine1() + "\r\n");
		if(mdForm.getStudent().getCourierAddress().getLine2()!=null && !"".equals(mdForm.getStudent().getCourierAddress().getLine2())){
			file.add("                             = " + mdForm.getStudent().getCourierAddress().getLine2()+ "\r\n");
		}
		if(mdForm.getStudent().getCourierAddress().getLine3()!=null && !"".equals(mdForm.getStudent().getCourierAddress().getLine3())){
			file.add("                             = " + mdForm.getStudent().getCourierAddress().getLine3()+ "\r\n");
		}
		if(mdForm.getStudent().getCourierAddress().getLine4()!=null && !"".equals(mdForm.getStudent().getCourierAddress().getLine4())){
			file.add("                             = " + mdForm.getStudent().getCourierAddress().getLine4()+ "\r\n");
		}
		if(mdForm.getStudent().getCourierAddress().getLine5()!=null && !"".equals(mdForm.getStudent().getCourierAddress().getLine5())){
			file.add("                             = " + mdForm.getStudent().getCourierAddress().getLine5()+ "\r\n");
		}
		if(mdForm.getStudent().getCourierAddress().getLine6()!=null && !"".equals(mdForm.getStudent().getCourierAddress().getLine6())){
			file.add("                             = " + mdForm.getStudent().getCourierAddress().getLine6()+ "\r\n");
		}
		file.add(" Courier addr postal code    = " + mdForm.getStudent().getCourierAddress().getAreaCode() + "\r\n");

		file.add(" Country                     = " + mdForm.getStudent().getCountry().getCode()+ " "+ mdForm.getStudent().getCountry().getDesc() + "\r\n");
		file.add(" Fellow students (Y/N)       = " + mdForm.getStudent().getShareContactDetails() + "\r\n");
		file.add(" Examination centre          = " + mdForm.getStudent().getExam().getCode()+ " "+ mdForm.getStudent().getExam().getDesc() + "\r\n");
		file.add(" Home language               = " + mdForm.getStudent().getHomeLanguage().getCode() + " "+ mdForm.getStudent().getHomeLanguage().getDesc() + "\r\n");
		file.add(" Nationality                 = " + mdForm.getStudent().getNationalty().getCode()+ " "+ mdForm.getStudent().getNationalty().getDesc() + "\r\n");
		file.add(" Population group            = " + mdForm.getStudent().getPopulationGroup().getCode()+ " "+ mdForm.getStudent().getPopulationGroup().getDesc() + "\r\n");
		file.add(" Occupation                  = " + mdForm.getStudent().getOccupation().getCode()+ " "+ mdForm.getStudent().getOccupation().getDesc() + "\r\n");
		file.add(" Economic sector             = " + mdForm.getStudent().getEconomicSector().getCode()+ " "+ mdForm.getStudent().getEconomicSector().getDesc() + "\r\n");
		file.add(" Previous economic activity  = " + mdForm.getStudent().getPrevActivity().getCode()+ " "+ mdForm.getStudent().getPrevActivity().getDesc() + "\r\n");
		file.add(" Concurrent institution      = " + mdForm.getStudent().getOtherUniversity().getCode()+ " "+ mdForm.getStudent().getOtherUniversity().getDesc() + "\r\n");
		file.add(" Previous institution        = " + mdForm.getStudent().getPrevInstitution().getCode()+ "\r\n");
		file.add(" Last year of registration   = " + mdForm.getStudent().getLastRegYear() + "\r\n");
		if ("P".equalsIgnoreCase(mdForm.getStudent().getLastStatus())){
			file.add(" Previous postgraduate (Y/N) = Y \r\n");
		}else if ("U".equalsIgnoreCase(mdForm.getStudent().getLastStatus())){
			file.add(" Previous postgraduate (Y/N) = N \r\n");
		}else{
			file.add(" Previous postgraduate (Y/N) =  \r\n");
		}
		//previous academic history
		file.add(" Previous academic history:  \r\n");
		file.add("   Institution           = " + mdForm.getMdprevList().get(0).getHistUniv() + "\r\n");
		file.add("   Qualification         = " + mdForm.getMdprevList().get(0).getHistAccredit() + "\r\n");
		file.add("   Complete yr           = " + mdForm.getMdprevList().get(0).getHistComplete() + "\r\n");
		file.add("   Accredited source     = " + mdForm.getMdprevList().get(0).getHistAccredit() + "\r\n");
		if (mdForm.getMdprevList().get(1).getHistUniv()!=null && !"".equals(mdForm.getMdprevList().get(1).getHistUniv())){
			file.add(" Previous academic history  :  \r\n");
			file.add("   Institution           = " + mdForm.getMdprevList().get(1).getHistUniv() + "\r\n");
			file.add("   Qualification         = " + mdForm.getMdprevList().get(1).getHistAccredit() + "\r\n");
			file.add("   Complete yr           = " + mdForm.getMdprevList().get(1).getHistComplete() + "\r\n");
			file.add("   Accredited source     = " + mdForm.getMdprevList().get(1).getHistAccredit() + "\r\n");
		}
		if (mdForm.getMdprevList().get(2).getHistUniv()!=null && !"".equals(mdForm.getMdprevList().get(2).getHistUniv())){
			file.add(" Previous academic history  :  \r\n");
			file.add("   Institution           = " + mdForm.getMdprevList().get(2).getHistUniv() + "\r\n");
			file.add("   Qualification         = " + mdForm.getMdprevList().get(2).getHistAccredit() + "\r\n");
			file.add("   Complete yr           = " + mdForm.getMdprevList().get(2).getHistComplete() + "\r\n");
			file.add("   Accredited source     = " + mdForm.getMdprevList().get(2).getHistAccredit() + "\r\n");
		}
		if (mdForm.getMdprevList().get(3).getHistUniv()!=null && !"".equals(mdForm.getMdprevList().get(3).getHistUniv())){
			file.add(" Previous academic history  :  \r\n");
			file.add("   Institution           = " + mdForm.getMdprevList().get(3).getHistUniv() + "\r\n");
			file.add("   Qualification         = " + mdForm.getMdprevList().get(3).getHistAccredit() + "\r\n");
			file.add("   Complete yr           = " + mdForm.getMdprevList().get(3).getHistComplete() + "\r\n");
			file.add("   Accredited source     = " + mdForm.getMdprevList().get(3).getHistAccredit() + "\r\n");
		}

		file.add(" M & D Area of interes       = " + mdForm.getSelectedInterestArea() + "\r\n");
		file.add(" Proposed area of spec/topic = " + mdForm.getStudent().getResearchTopic( )+ "\r\n");
		file.close();

		log.info("MDapplications: Workflow for studnr="+mdForm.getStudent().getNumber());
	}

	public ActionForward addAttachment(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		MdApplicationsForm mdForm = (MdApplicationsForm) form;
		ActionMessages messages = new ActionMessages();
		
		// check document type
		if("".equals(mdForm.getFileType())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Select the type of document you want to upload."));
			addErrors(request, messages);
			return mapping.findForward("docuploadforward");
		}
		
		return mapping.findForward("addattachmentforward");
	}

	public ActionForward attachFile(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		MdApplicationsForm mdForm = (MdApplicationsForm) form;
		ActionMessages messages = new ActionMessages();
		String readFile = "";
		String writeFile = "";
		FileInputStream buffIn = null;
		FileOutputStream buffout = null;
		String fileDir;
		String inputFileName = "";

		try{
			inputFileName = request.getAttribute("theFile").toString();
		} catch (NullPointerException ne){
			messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("message.generalmessage","You cannot upload files greater than 2 MG (2048 KB)."));
			addErrors(request, messages);
			return mapping.findForward("addattachmentforward");
		}

		/*
		boolean isSBLQual = false;
		isSBLQual = dao.isSBLQual();
		*/

		String uploadPath = getServlet().getServletContext().getInitParameter("applicationFullPath")+"/";
		//log.debug("MDApplicationsAction - attachFile - uploadPath="+uploadPath);
		
		String [] input = inputFileName.split(",");
		String[] parseInput;
		//String extensions = "pdf,gif,jpg";
		String extensions = "pdf,tif,doc,docx,tiff";
		String[] extList = extensions.split(",");
		for (int i=0; i < input.length; i++){
			parseInput = input[i].split("=");
			if (parseInput.length > 1){
				if (i == 0){
					String tmp = parseInput[1];
					if (parseInput[1].lastIndexOf("\\") > 0 ){
						tmp = parseInput[1].substring(parseInput[1].lastIndexOf("\\")+1);
					}
					writeFile = tmp;
				} else if (i == 1){
					readFile = parseInput[1];
				}
			}
		}

		if (writeFile.equalsIgnoreCase("") && mdForm.getAddressLink().length() < 1){
			messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("message.generalmessage","Click the Browse button to select the file or click the Cancel button"));
			addErrors(request,messages);
			return addAttachment(mapping, form, request, response);
		} else if (!(writeFile.equalsIgnoreCase("")) && mdForm.getAddressLink().length() > 1){
			messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("message.generalmessage","Click Browse button to select the file or click the Cancel button"));
			addErrors(request,messages);
			return addAttachment(mapping, form, request, response);
		}

		// only 5 documents allowed per upload session
		if (mdForm.getStudentFiles().size() == 5){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage","Maximum number of files reached. You can only upload 5 files."));
			addErrors(request,messages);
			return addAttachment(mapping, form, request, response);
		}
		// check that document has not been added already
		for (int i = 0; i < mdForm.getStudentFiles().size(); i++) {
			StudentFile stuFile = new StudentFile();
			stuFile = mdForm.getStudentFiles().get(i);
			if (writeFile.equalsIgnoreCase(stuFile.getFileName())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage","You have aready added the specific file. Please choose a different one."));
				addErrors(request,messages);
				return addAttachment(mapping, form, request, response);
			}
		}
		request.setAttribute("inputFileName", inputFileName);
		// ----------- setup file object --------------
		StudentFile stuFile = new StudentFile();
		stuFile.setFileType(mdForm.getFileType());
		stuFile.setFileName(writeFile);
		// set file type description for work flow file
		String fileDesc ="";
		if("1".equalsIgnoreCase(mdForm.getFileType())){
			fileDesc = "AR";
		}else if ("2".equalsIgnoreCase(mdForm.getFileType())){
			fileDesc = "ID";
		}else if ("3".equalsIgnoreCase(mdForm.getFileType())){
			fileDesc = "Letters";
		}else if ("4".equalsIgnoreCase(mdForm.getFileType())){
			fileDesc = "CV";
		}else if ("5".equalsIgnoreCase(mdForm.getFileType())){
			fileDesc = "Syllabi";
		}else if ("6".equalsIgnoreCase(mdForm.getFileType())){
			fileDesc = "Quest";
		}else if ("7".equalsIgnoreCase(mdForm.getFileType())){
			fileDesc = "Workexp";
		}else if ("8".equalsIgnoreCase(mdForm.getFileType())){
			fileDesc = "Essay";
		}else if ("9".equalsIgnoreCase(mdForm.getFileType())){
			fileDesc = "SWReg";
		}else if ("10".equalsIgnoreCase(mdForm.getFileType())){
			fileDesc = "SAQA";
		}else if ("11".equalsIgnoreCase(mdForm.getFileType())){
			fileDesc = "Diss";
		}else if ("12".equalsIgnoreCase(mdForm.getFileType())){
			fileDesc = "MvaPort";
		}else if ("13".equalsIgnoreCase(mdForm.getFileType())){
			fileDesc = "EngReq";
		}else if ("14".equalsIgnoreCase(mdForm.getFileType())){
			fileDesc = "Confirmation";
		}else if ("15".equalsIgnoreCase(mdForm.getFileType())){
			fileDesc = "Research";
		}else if ("16".equalsIgnoreCase(mdForm.getFileType())){
			fileDesc = "Residential";
		}else if ("17".equalsIgnoreCase(mdForm.getFileType())){
			fileDesc = "Laboratory";
		}else if ("18".equalsIgnoreCase(mdForm.getFileType())){
			fileDesc = "HonnsProject";
		}else if ("19".equalsIgnoreCase(mdForm.getFileType())){
			fileDesc = "Other";
		}else if ("21".equalsIgnoreCase(mdForm.getFileType())){
			fileDesc = "MARRIAGE";
		}else if ("22".equalsIgnoreCase(mdForm.getFileType())){
			fileDesc = "OTHER";
		}else if ("23".equalsIgnoreCase(mdForm.getFileType())){
			fileDesc = "CONFIRMATION";
		}else if ("24".equalsIgnoreCase(mdForm.getFileType())){
			fileDesc = "LETTERS";
		}
		String fileExtension = writeFile.substring(writeFile.lastIndexOf(".")+1);
		String time = (new java.text.SimpleDateFormat("hhmmssss").format(new java.util.Date()).toString());
		String newFileName = mdForm.getStudent().getNumber()+"_"+ fileDesc + "_"+ time +"." + fileExtension;
		stuFile.setNewFileName(newFileName);
		// -----------------------------------------------
		mdForm.setFileName(writeFile);
		if (!writeFile.equalsIgnoreCase("") && writeFile != null){
			// set filename to changed name for workflow purposes
			writeFile = newFileName;
		}
		if (!writeFile.equalsIgnoreCase("") && writeFile != null){
			boolean notAllowed = true;
			File testSize = new File(readFile);
			for(int i=0; i < extList.length; i++){
				if (writeFile.substring(writeFile.lastIndexOf(".")+1).equalsIgnoreCase(extList[i])){
					notAllowed = false;
				}
			}
			// check file type
			if (notAllowed){
			//	messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("message.generalmessage","You can only upload the following file types: pdf, jpg and gif."));
				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("message.generalmessage","You can only upload the following file types: pdf, doc and tif."));
				addErrors(request, messages);
				return addAttachment(mapping, form, request, response);
			}
			// check file size,  not greater than 2 MB
			if (testSize.length() > 2097152){ // measured in BYTES!
				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("message.generalmessage","You cannot upload files greater than 2 MB (2048 KB)"));
				addErrors(request, messages);
				return addAttachment(mapping, form, request, response);
			} else {
				try {
					buffIn = new FileInputStream(readFile);
					// write file to temp dir: /data/sakai/wfl/tmp
					// specified in web.xml
					fileDir = uploadPath +"/";
					File file = new File(fileDir);
					if (!file.exists()){
						file.mkdirs();
					}
					buffout = new FileOutputStream(fileDir+writeFile);
					//log.debug("filepath="+fileDir+writeFile);
					boolean eof = false;
					while(!eof){
						int line = buffIn.read();
						if (line == -1){
							eof  = true;
						} else {
							buffout.write(line);
						}
					}
					buffIn.close();
					buffout.close();

					// add file object to list of already selected files.
					mdForm.getStudentFiles().add(stuFile);

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (NullPointerException ne) {
					ne.printStackTrace();
				} catch (IOException ioe){
					ioe.printStackTrace();
				} finally {
					try{
						if (buffIn != null){
							buffIn.close();
							buffout.close();
						}
						if (buffout!=null){
							buffout.close();
						}
					} catch(IOException ie){
						ie.printStackTrace();
					}
				}
			}
		} else if(mdForm.getAddressLink().length() > 0) {
			request.setAttribute("filename","");
			if (!mdForm.getAddressLink().startsWith("http://")){
				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message","The Url should start with http://"));
				addErrors(request, messages);
				return addAttachment(mapping, form, request, response);
			}
		}

		// clear document type for next selection
		mdForm.setFileType("");

		return mapping.findForward("docuploadforward");
	}

	/**
	 * This method removes a specific file from the web server
	 * which was previously added by the student.
	 *
	 * Web server path specified in project's web.xml
	 *
	*/
	public ActionForward clearAttachment(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response){

		//log.debug("clearAttachment");
		//log.debug("clearAttachment - File: " + request.getParameter("fname"));
		
		MdApplicationsForm mdForm = (MdApplicationsForm) form;
		ActionMessages messages = new ActionMessages();
		
		if (mdForm.getStudent().getNumber() == null || "".equalsIgnoreCase(mdForm.getStudent().getNumber())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "An error has occurred or you performed and invalid action. Please log on again to retry."));
			addErrors(request, messages);
			return mapping.findForward("docuploadforward");
		}
		
		String fileName = null;
		int indexToBeRemoved = -1;
		//remove file from list of already selected files
		for (int i = 0; i < mdForm.getStudentFiles().size(); i++) {
			StudentFile stuFile = new StudentFile();
			stuFile = mdForm.getStudentFiles().get(i);
			if (request.getParameter("fname").equalsIgnoreCase(stuFile.getNewFileName())){
				indexToBeRemoved = i;
			}
		}
		if (!(request.getParameter("fname").equalsIgnoreCase("clearUrl"))){
			fileName = getServlet().getServletContext().getInitParameter("applicationFullPath")+"/"+request.getParameter("fname");
			File file = new File(fileName);
			if (file.exists()){
				file.delete();
				if(indexToBeRemoved != -1){
					mdForm.getStudentFiles().remove(indexToBeRemoved);
				}
			}
		}

		mdForm.setFileName("");

		return mapping.findForward("docuploadforward");
	}

	/**
	 * This method removes all the files specified by the student, as he/she
	 * cancelled the transaction.
	 *
	*/
	public void clearAllAttachments(MdApplicationsForm mdForm ){

		String fileName = "";
		for (int i = 0; i < mdForm.getStudentFiles().size(); i++) {
			StudentFile stuFile = new StudentFile();
			stuFile = mdForm.getStudentFiles().get(i);
			fileName = getServlet().getServletContext().getInitParameter("applicationFullPath")+"/"+ stuFile.getNewFileName();
			File file = new File(fileName);
			if (file.exists()){
				file.delete();
			}
		}
		//clear display list
		mdForm.setStudentFiles(new ArrayList<StudentFile>());

	}

	@SuppressWarnings("unused")
	public ActionForward saveDocuments(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		MdApplicationsForm mdForm = (MdApplicationsForm) form;
		ActionMessages messages = new ActionMessages();
		MdApplicationsQueryDAO dao = new MdApplicationsQueryDAO();

		if (mdForm.getStudent().getNumber() == null || "".equalsIgnoreCase(mdForm.getStudent().getNumber())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "An error has occurred or you performed and invalid action. Please log on again to retry."));
			addErrors(request, messages);
			return mapping.findForward("docuploadforward");
		}
		
		if (mdForm.getStudentFiles().size() == 0){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage","You have not selected any files."));
			addErrors(request,messages);
			return mapping.findForward("docuploadforward");
		}
		// Copy documents from temp dir: /data/sakai/wfl/tmp
		// to workflow dir: /data/sakai/wfl
		// path in sakai.properties
		//Jul 2011 -md applications - \\umkn-globalnx\nx\DATAROOT\IMPORT\MD
        FileWriter workflowWriter = null;
        FileReader fileReader = null;
        File fileToBeCopied = null;
        File workflowFile = null;
        try {
        	String fileName = "";
        	String workflowFileName="";
        	// loop through all files
        	for (int i = 0; i < mdForm.getStudentFiles().size(); i++) {
        		StudentFile stuFile = new StudentFile();
        		stuFile = mdForm.getStudentFiles().get(i);
        		fileName = getServlet().getServletContext().getInitParameter(
        		"applicationFullPath") + "/" + stuFile.getNewFileName();
        		fileToBeCopied = new File(fileName);
        		//workflowFileName = ServerConfigurationService.getString("workflow.path") + "/" + stuFile.getNewFileName();
        		//File dir = new File(ServerConfigurationService.getString("workflow.path"));
        		//workflowFileName = ServerConfigurationService.getString("application.path") + "/" + stuFile.getNewFileName();
        		//File dir = new File(ServerConfigurationService.getString("application.path"));
        		boolean isSBLQual = false;
        		isSBLQual = dao.isSBLQual(mdForm.getQual().getQualCode());
        		if (isSBLQual){
        			workflowFileName = ServerConfigurationService.getString("sblapply.path") + "/" + stuFile.getNewFileName();
	        		File dir = new File(ServerConfigurationService.getString("sblapply.path"));
	        		boolean success = fileToBeCopied.renameTo(new File(dir,fileToBeCopied.getName()));
        		}else{
        			workflowFileName = ServerConfigurationService.getString("mdapply.path") + "/" + stuFile.getNewFileName();
	        		File dir = new File(ServerConfigurationService.getString("mdapply.path"));
	        		boolean success = fileToBeCopied.renameTo(new File(dir,fileToBeCopied.getName()));
        		}	        	
			
			//do not write anything for m & d - checked on student system only
			//dao.updMddocsx(mdForm.getStudent().getNumber(), mdForm.getStudent().getSeqnr(), stuFile.getFileType());
        	}
        } catch (Exception ex) {
        		// write log?
        		throw ex;
        	} finally {
        		//workflowWriter.close();
        		//fileReader.close();
        	}

		resetForm((MdApplicationsForm) form);
		return mapping.findForward("uploaddoneforward");
	}

	@SuppressWarnings("unused")
	private boolean isNameValid(String anyname){
		boolean result = true;
		String test = "";
		int y=anyname.length();

		for (int i = 0; i < y-1; i++) {
			test= anyname.substring(i,i+1);
			if (test !=null && !"".equals(test) && !" ".equals(test) && !"-".equals(test)){
				if ("~".equals(test) || "'".equals(test) || "@".equals(test) || "^".equals(test)){
					result=false;
				}else{
					//log.debug(test);
				}
			}
		}
		return result;
	}

	public Boolean isNumberValid(String numVal) {
	    regexPattern = Pattern.compile("^[0-9]*$");
	    regMatcher   = regexPattern.matcher(numVal);
	    //if (regMatcher.find()) {
	    if(regMatcher.matches()){
	        return true;
	    } else {
	    	return false;
	    }
	}
	

	/**
	 * method is used for checking valid email id format.
	 *
	 * @param email
	 * @return boolean true for valid false for invalid
	 */
	public boolean isEmailValid(String email) {
	    boolean isValid = false;

	    String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
	    CharSequence inputStr = email;

	    Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
	    Matcher matcher = pattern.matcher(inputStr);
	    if (matcher.matches()) {
	        isValid = true;
	    }
	    return isValid;
	}
	
	public ActionForward unspecified(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response){

		log.info("MDApplications: unspecified method call -no value for parameter in request");
		return mapping.findForward("walkthroughforward");
	}

	@SuppressWarnings("static-access")
	public String getCurrentAcademicYear() throws Exception {
		int tYear;
		String acaYear;

		Calendar cal = Calendar.getInstance();
		int year = cal.get(cal.YEAR);
		int month = cal.get(cal.MONTH)+1; //zero-based
		if (month >= 8 ){
			tYear = (year + 1);
			acaYear = Integer.toString(tYear);
		}else{
			acaYear = Integer.toString(year);
		}
		return acaYear;
	}
	
	public void resetForm(MdApplicationsForm form)	throws Exception {
		
		
		MdApplicationsQueryDAO dao = new MdApplicationsQueryDAO();
		
		// Clear fields
		form.setStudent(new Student());
		form.setAdminStaff(new Staff());
		
		form.setQual(new Qualification());
		List<MdPrev> mdlist = new ArrayList<MdPrev>();
		form.setMdprevList(mdlist);
		form.setSelectedQual("");
		form.setSelectedSpes("");
		form.setSelectedCountry("");
		form.setSelectedExamCentre("");
		form.setSelectedDisability("");
		form.setSelectedHomeLanguage("");
		form.setSelectedEconomicSector("");
		form.setSelectedOccupation("");
		form.setSelectedNationality("");
		form.setSelectedOtherUniversity("");
		form.setSelectedPopulationGroup("");
		form.setSelectedPrevActivity("");
		form.setSelectedInterestArea("-1");
		form.getStudent().setAcademicYear(getCurrentAcademicYear());
		form.getStudent().setPassedndp("");
		form.getStudent().setAppliedmd("");
		form.getStudent().setAppliedqual("");
		form.setAgree(null);
		form.setAgreeQualInfo(null);
		form.setFromPage(null);
		form.setStudentFiles(new ArrayList<StudentFile>());
		form.setFileName("");
		form.setFileType("");
		form.setLoginType("");
		form.setDateWEBMDADM(false);
		form.setDateWEBMDAPP(false);
		form.setDateWEBMDDOC(false);

		if (!dao.validateClosingDate(form.getStudent().getAcademicYear(), "WEBMDADM")){
			form.setDateWEBMDADM(false);
		}else{
			form.setDateWEBMDADM(true);
		}
		if (!dao.validateClosingDate(form.getStudent().getAcademicYear(), "WEBMDAPP")){
			form.setDateWEBMDAPP(false);
		}else{
			form.setDateWEBMDAPP(true);
		}
		if (!dao.validateClosingDate(form.getStudent().getAcademicYear(), "WEBMDDOC")){
			form.setDateWEBMDDOC(false);
		}else{
			form.setDateWEBMDDOC(true);
		}
		//log.debug("MDApplicationsAction - resetForm -  WEBMDADM="+form.isDateWEBMDADM());
		//log.debug("MDApplicationsAction - resetForm -  WEBMDAPP="+form.isDateWEBMDAPP());
		//log.debug("MDApplicationsAction - resetForm -  WEBMDDOC="+form.isDateWEBMDDOC());
		
        if (form.isDateWEBMDAPP() || form.isDateWEBMDDOC() || form.isDateWEBMDAPP()){ 
        	form.setWebLoginMsg("Applications for "+ form.getStudent().getAcademicYear());
	    }else{
	    	form.setWebLoginMsg("Applications are closed for Administrators");
	    }
		
		//form.getStudent().setSurname("MOTLOUTSI");
		//form.getStudent().setFirstnames("MASILO JEFFREY");
		//form.getStudent().setNumber("49152947");
		//form.getStudent().setBirthDay("02");
		//form.getStudent().setBirthMonth("01");
		//form.getStudent().setBirthYear("1982");
		//form.getStudent().setEmailAddress("krugegj@unisa.ac.za");
	}
	
	//Include some cross site scripting checks (XSS)
    private String stripXSS(String value) { 

    	//log.debug("MDApplicationsAction - stripXSS - Value="+value);
        if (value != null) { 
            // NOTE: It's highly recommended to use the ESAPI library and uncomment the following line to 
            // avoid encoded attacks. 
            //value = ESAPI.encoder().canonicalise(value); 

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
    
	public void getAllRequestParamaters(HttpServletRequest req, HttpServletResponse res) throws Exception { 

		  //log.debug("MDApplications -----------------------------------------------------------------");
		  //log.debug("MDApplications - getAllRequestParamaters - Start");
		  Enumeration<String> parameterNames = req.getParameterNames(); 

		  while (parameterNames.hasMoreElements()) { 
			  String paramName = parameterNames.nextElement(); 
			  //log.debug("MDApplications - getAllRequestParamaters - param: " + paramName); 

			  String[] paramValues = req.getParameterValues(paramName); 
			  for (int i = 0; i < paramValues.length; i++) { 
				  String paramValue = paramValues[i]; 
				  //log.debug("MDApplications - getAllRequestParamaters - value: " + paramValue); 
			  } 
		  } 
		  //log.debug("MDApplications - getAllRequestParamaters - End");
		  //log.debug("MDApplications -----------------------------------------------------------------");
	  } 
}