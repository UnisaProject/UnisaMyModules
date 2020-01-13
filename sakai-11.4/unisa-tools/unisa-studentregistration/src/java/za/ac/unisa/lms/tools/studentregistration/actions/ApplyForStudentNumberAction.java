package za.ac.unisa.lms.tools.studentregistration.actions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Random;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
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

import za.ac.unisa.lms.tools.studentregistration.dao.ApplyForStudentNumberQueryDAO;
import za.ac.unisa.lms.tools.studentregistration.dao.KeyValue;
import za.ac.unisa.lms.tools.studentregistration.dao.SavedDocDao;
import za.ac.unisa.lms.tools.studentregistration.forms.GeneralItem;
import za.ac.unisa.lms.tools.studentregistration.forms.HistoryArray;
import za.ac.unisa.lms.tools.studentregistration.forms.HistoryOther;
import za.ac.unisa.lms.tools.studentregistration.forms.HistoryUnisa;
import za.ac.unisa.lms.tools.studentregistration.forms.Staff;
import za.ac.unisa.lms.tools.studentregistration.forms.Student;
import za.ac.unisa.lms.tools.studentregistration.forms.StudentFile;
import za.ac.unisa.lms.tools.studentregistration.forms.StudentRegistrationForm;
import za.ac.unisa.lms.tools.studentregistration.forms.Subject;
import za.ac.unisa.lms.tools.studentregistration.actions.WorkflowTempFile;
import za.ac.unisa.lms.tools.studentregistration.bo.Categories;
import za.ac.unisa.lms.tools.studentregistration.bo.Qualifications;
import za.ac.unisa.lms.tools.studentregistration.bo.SavedDoc;
import za.ac.unisa.lms.tools.studentregistration.bo.Specializations;
import za.ac.unisa.lms.tools.studentregistration.bo.Status;
import za.ac.unisa.lms.tools.studentregistration.utils.PdfDownloader;

//import za.ac.unisa.utils.WorkflowFile;
import Srrsa01h.Abean.Srrsa01sRegStudentPersDetail;
import Staae05h.Abean.Staae05sAppAdmissionEvaluator;
import Menu95h.Abean.Menu95S;

public class ApplyForStudentNumberAction extends LookupDispatchAction {
	
    /** Our log (commons). */
	public static Log log = LogFactory.getLog(ApplyForStudentNumberAction.class);
	
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
		map.put("button.submitLoginSelect","submitLoginSelect");
		map.put("button.submitAdminLogin","loginStaff");
		map.put("button.submitLoginAdmin","applyLoginAdmin");
		
		//New actionForwards and pages (Edmund 2013)
		map.put("button.submitMD","submitMD");
		map.put("button.submitNewQual","submitNewQual");
		map.put("button.submitCngQual","submitCngQual");
		map.put("button.submitReturnDoc","submitReturnDoc");
		map.put("button.submitNewDoc","submitNewDoc");
		map.put("button.submitMoveDoc","moveDocuments");
		map.put("button.submitPay","submitPay");
		map.put("button.submitPayLater","submitPayLater");
				
	    map.put("applyNewPersonal", "applyNewPersonal");
	    map.put("stepQualConfirm","stepQualConfirm");
	    map.put("stepRetContact","stepRetContact");
	    map.put("stepRetRadio","stepRetRadio");
	    map.put("applyRetDeclare", "applyRetDeclare");
	    map.put("applyNewDeclare", "applyNewDeclare");
	    map.put("applyNDP","applyNDP");

	    //new actions for Back Buttons
	    map.put("checkNewBack","checkNewBack");
	    map.put("checkReturnBack","checkReturnBack");
	    
	    //Previous actionforwards
	    map.put("button.cancel", "cancel");
	    map.put("button.quit", "quit");
	    map.put("button.backtoapp","cancel");
	    map.put("button.back", "back");
	    map.put("button.exit", "quit");
	    map.put("button.continue", "nextStep");
	    map.put("button.request", "applyNewDeclare");
		map.put("button.next", "nextStep");
		map.put("button.spes", "nextSpeciality");
		map.put("button.getPDF","getPDFtoPrint");
		map.put("button.Upload","appealUpload");

		map.put("back", "back");
		map.put("cancel", "cancel");
		map.put("next", "nextStep");
		map.put("admin", "loginAdmin");
		map.put("loginStaff", "loginStaff");
		map.put("loginStu", "loginStu");
	    map.put("walkthrough", "walkthrough");
	    map.put("stepLoginSelect","stepLoginSelect");
	    map.put("submitLoginSelect","submitLoginSelect");
	    
	    map.put("stepID", "stepID");
	    map.put("stepAPSSelect","stepAPSSelect");
		map.put("stepAPSNumber","stepAPSNumber");
		map.put("stepMatric","stepMatric");
		map.put("stepMatricSubject","stepMatricSubject");
		
	    map.put("populateExamCentres","populateExamCentres");
	    map.put("populateSchools","populateSchools");
//	    map.put("applyType","applyType");
	    map.put("getPostalCode", "getPostalCode");
	    map.put("button.list", "listPostalCode");
	    map.put("button.search.cancel", "cancelSearch");
	    map.put("Cancel Search", "cancelSearch");

	    map.put("applyLoginNew", "applyLoginNew");
	    map.put("applyLoginReturn", "applyLoginReturn");
	    map.put("applyLoginAdmin", "applyLoginAdmin");
	    map.put("applyStatus", "applyStatus");
	    map.put("Appeal","getPDFtoPrint");
	    map.put("appealSelect","appealSelect");
	    map.put("appealDeclare","appealDeclare");
	    map.put("appealConfirm","appealConfirm");
	    map.put("appealUpload","appealUpload");
	    map.put("loginAdmin", "loginAdmin");
	    
	    map.put("saveStudyNew", "saveStudyNew");
	    map.put("saveStudyRet", "saveStudyRet");
	    map.put("populateCategories", "populateCategories");
	    map.put("populateQualifications", "populateQualifications");
	    map.put("populateSpecializations", "populateSpecializations");
	    map.put("populatePrevQualifications", "populatePrevQualifications");
	    map.put("stepPrevQual", "stepPrevQual");
	    
	    map.put("populateUnisaQualifications","populateUnisaQualifications");
	    map.put("populateOtherQualifications","populateOtherQualifications");
	    map.put("populateUniv", "populateUniv");
		map.put("populateYear", "populateYear");
		map.put("populateCountry", "populateCountry");
	    map.put("populatePREVADM", "populatePREVADM");
	    map.put("populateSTUPREV", "populateSTUPREV");

	    //APS Calculator
	    map.put("populateMatSubjects","populateMatSubjects");
	    
	    //SLP select
	    map.put("applySLPSelect","applySLPSelect");
	    map.put("stepSLPConfirm","stepSLPConfirm");
    
	    return map;
	 }
	
	public void reset(ActionMapping mapping, HttpServletRequest request) throws Exception {

		//log.debug("Initializing Action Form"); 
		StudentRegistrationForm stuRegForm =  new StudentRegistrationForm();
		resetForm(stuRegForm, "ApplyForStudentNumberAction WalkThrough");
		
	}
	
	public ActionForward walkthrough(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		//log.debug("IN walkthrough");
	    
		ActionMessages messages = new ActionMessages();
		StudentRegistrationForm stuRegForm = (StudentRegistrationForm) form;
		//StudentRegistrationForm stuRegForm =  new StudentRegistrationForm();
		
		resetForm(stuRegForm, "ApplyForStudentNumberAction WalkThrough");
		stuRegForm.setSelectReset("");
		stuRegForm.setWebUploadMsg("");
		stuRegForm.setWebLoginMsg("");
		stuRegForm.setWebLoginMsg2("");
		
		//Write version number to log to check all servers
		//log.debug("Applications Version="+stuRegForm.getVersion());
		
		//log.debug("ApplyForStudentNumberAction walkthrough - sessionID=" + request.getSession().getId());

		
		if (stuRegForm.getStudent().getAcademicYear() == null){
			stuRegForm.getStudent().setAcademicYear(getCurrentAcademicYear());
			stuRegForm.getStudent().setAcademicPeriod(getCurrentAcademicPeriod());
		}
			
		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
		if (stuRegForm.getStudent().getAcademicYear() == null){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", "Academic year invalid, please log on again to retry."));
			addErrors(request, messages);
			return mapping.findForward("loginSelect");
		}
		//log.debug("ApplyForStudentNumberAction - walkthrough - dateCheck");

		ArrayList<String> dateCheck = dao.validateClosingDate(stuRegForm.getStudent().getAcademicYear());
		if (!dateCheck.isEmpty()){ //Check Dates Array
			for (int i=0; i < dateCheck.size(); i++){
				//log.debug("ApplyForStudentNumberAction - walkthrough - dateCheck="+dateCheck.get(i).toString());
				if ("WAPD".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPD(true);
				}else if ("WAPDOC".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPDOC(true);
				}else if ("WAPPAY".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPPAY(true);
				}else if ("WAPH".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPH(true);
				}else if ("WAPM".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPM(true);
				}else if ("WAPRH".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPRH(true);
				}else if ("WAPRU".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPRU(true);
				}else if ("WAPU".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPU(true);
				}else if ("WAPS".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPS(true);
				}else if ("WAPADMU".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPADMU(true);
				}else if ("WAPADMH".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPADMH(true);
				}else if ("WAPADMS".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPADMS(true);
				}else if ("WAPADMM".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPADMM(true);
				}else if ("WAPADMD".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPADMD(true);
				}else if ("WAPADMNEW".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPADMNEW(true);
				}else if ("WAPADMRET".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPADMRET(true);
				}else if ("WAPSTAT".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPSTAT(true);
				}else if ("WAPAPL".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPAPL(true);
				}else if ("WAPOFFER".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPOFFER(true);
				}else if ("WAPOFFICE".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPOFFICE(true);
				}
			}
		}
		
		//log.debug("ApplyForStudentNumberAction - walkthrough - AcademicYear="+stuRegForm.getStudent().getAcademicYear()+", LoginSelectMain="+stuRegForm.getLoginSelectMain()+", isAdmin="+stuRegForm.getAdminStaff().isAdmin());
		
		//log.debug("ApplyForStudentNumberAction - walkthrough -Return to loginSelect");
		return mapping.findForward("loginSelect");
	}
	
	public ActionForward loginAdmin(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		//log.debug("ApplyForStudentNumberAction - loginAdmin - Start");
	    
		StudentRegistrationForm stuRegForm = (StudentRegistrationForm) form;
		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
		
		//log.debug("ApplyForStudentNumberAction - loginAdmin - Session ID: " + request.getSession().getId());
		//log.debug("ApplyForStudentNumberAction - loginAdmin - Session State 1: " + request.getSession().getAttribute(SESSION_STATE));
		request.getSession().setAttribute(SESSION_STATE, Boolean.TRUE);
		//log.debug("ApplyForStudentNumberAction - loginAdmin - Session State 2: " + request.getSession().getAttribute(SESSION_STATE));
		
		resetForm(stuRegForm, "ApplyForStudentNumberAction - loginAdmin");
		
		stuRegForm.setSelectReset("");
		stuRegForm.setWebUploadMsg("");
		stuRegForm.setWebLoginMsg("");
		stuRegForm.setWebLoginMsg2("");
	
		stuRegForm.setFromPage("stepLoginAdmin");
		
		//log.debug("ApplyForStudentNumberAction - loginAdmin - dateCheck");

		ArrayList<String> dateCheck = dao.validateClosingDate(stuRegForm.getStudent().getAcademicYear());
		if (!dateCheck.isEmpty()){ //Check Dates Array
			for (int i=0; i < dateCheck.size(); i++){
				//log.debug("ApplyForStudentNumberAction - loginAdmin - dateCheck="+dateCheck.get(i).toString());
				if ("WAPD".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPD(true);
				}else if ("WAPDOC".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPDOC(true);
				}else if ("WAPPAY".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPPAY(true);
				}else if ("WAPH".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPH(true);
				}else if ("WAPM".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPM(true);
				}else if ("WAPRH".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPRH(true);
				}else if ("WAPRU".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPRU(true);
				}else if ("WAPU".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPU(true);
				}else if ("WAPS".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPS(true);
				}else if ("WAPADMU".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPADMU(true);
				}else if ("WAPADMH".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPADMH(true);
				}else if ("WAPADMS".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPADMS(true);
				}else if ("WAPADMM".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPADMM(true);
				}else if ("WAPADMD".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPADMD(true);
				}else if ("WAPADMNEW".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPADMNEW(true);
				}else if ("WAPADMRET".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPADMRET(true);
				}else if ("WAPSTAT".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPSTAT(true);
				}else if ("WAPAPL".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPAPL(true);
				}else if ("WAPOFFER".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPOFFER(true);
				}else if ("WAPOFFICE".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPOFFICE(true);
				}
			}
		}
		
		//log.debug("ApplyForStudentNumberAction - loginAdmin - AcademicYear="+stuRegForm.getStudent().getAcademicYear()+", LoginSelectMain="+stuRegForm.getLoginSelectMain()+", isAdmin="+stuRegForm.getAdminStaff().isAdmin());

		
        if (stuRegForm.getStudent().isDateWAPADMU() || stuRegForm.getStudent().isDateWAPADMH() || stuRegForm.getStudent().isDateWAPADMS() || stuRegForm.getStudent().isDateWAPADMD() || stuRegForm.getStudent().isDateWAPADMM() || stuRegForm.getStudent().isDateWAPADMNEW() || stuRegForm.getStudent().isDateWAPADMRET()){ 
        	stuRegForm.setWebLoginMsg("Applications for "+ stuRegForm.getStudent().getAcademicYear());
	    }else{
	    	stuRegForm.setWebLoginMsg("Applications are closed for Administrators");
	    }

        //log.debug("ApplyForStudentNumberAction - loginAdmin - WAPADMU="+stuRegForm.getStudent().isDateWAPADMU()+", WAPADMH="+stuRegForm.getStudent().isDateWAPADMH()+", WAPADMS="+stuRegForm.getStudent().isDateWAPADMS()+", WAPADMD="+stuRegForm.getStudent().isDateWAPADMD()+", WAPADMM="+stuRegForm.getStudent().isDateWAPADMM());

		//log.debug("Return to applyLoginAdmin");
		setDropdownListsLogin(request,stuRegForm);
		return mapping.findForward("applyLoginAdmin");
	}
	
	public ActionForward loginStaff(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

			StudentRegistrationForm stuRegForm = (StudentRegistrationForm) form;
			stuRegForm.setWebLoginMsg("");
			stuRegForm.setWebLoginMsg2("");
		
			//log.debug("IN loginStaff");
			return mapping.findForward("loginStaff");
	}
	

	public ActionForward stepLoginSelect(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		//log.debug("ApplyForStudentNumberAction - IN stepLoginSelect");
			
		StudentRegistrationForm stuRegForm = (StudentRegistrationForm) form;
		resetForm(stuRegForm, "stepLoginSelect");
		
			//"applyMain"(String value, String valueDesc, String action, String studentNr, String year, String period, boolean logYN)
			stuRegForm.setLoginSelectMain(stripXSS(request.getParameter("mainSelection"), "mainSelection", "applyMain", null, getCurrentAcademicYear(), getCurrentAcademicPeriod(), stuRegForm.getApplySEQUENCE(), false));
			
			stuRegForm.setWebLoginMsg("");
			stuRegForm.setWebLoginMsg2("");
			
			if (stuRegForm.getStudent().getAcademicYear() == null){
				stuRegForm.getStudent().setAcademicYear(getCurrentAcademicYear());
				stuRegForm.getStudent().setAcademicPeriod(getCurrentAcademicPeriod());
			}
				
			ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
			//log.debug("ApplyForStudentNumberAction - stepLoginSelect - dateCheck");

			ArrayList<String> dateCheck = dao.validateClosingDate(stuRegForm.getStudent().getAcademicYear());
			if (!dateCheck.isEmpty()){ //Check Dates Array
				for (int i=0; i < dateCheck.size(); i++){
					//log.debug("ApplyForStudentNumberAction - stepLoginSelect - dateCheck="+dateCheck.get(i).toString());
					if ("WAPD".equalsIgnoreCase(dateCheck.get(i).toString())){
						stuRegForm.getStudent().setDateWAPD(true);
					}else if ("WAPDOC".equalsIgnoreCase(dateCheck.get(i).toString())){
						stuRegForm.getStudent().setDateWAPDOC(true);
					}else if ("WAPPAY".equalsIgnoreCase(dateCheck.get(i).toString())){
						stuRegForm.getStudent().setDateWAPPAY(true);
					}else if ("WAPH".equalsIgnoreCase(dateCheck.get(i).toString())){
						stuRegForm.getStudent().setDateWAPH(true);
					}else if ("WAPM".equalsIgnoreCase(dateCheck.get(i).toString())){
						stuRegForm.getStudent().setDateWAPM(true);
					}else if ("WAPRH".equalsIgnoreCase(dateCheck.get(i).toString())){
						stuRegForm.getStudent().setDateWAPRH(true);
					}else if ("WAPRU".equalsIgnoreCase(dateCheck.get(i).toString())){
						stuRegForm.getStudent().setDateWAPRU(true);
					}else if ("WAPU".equalsIgnoreCase(dateCheck.get(i).toString())){
						stuRegForm.getStudent().setDateWAPU(true);
					}else if ("WAPS".equalsIgnoreCase(dateCheck.get(i).toString())){
						stuRegForm.getStudent().setDateWAPS(true);
					}else if ("WAPADMU".equalsIgnoreCase(dateCheck.get(i).toString())){
						stuRegForm.getStudent().setDateWAPADMU(true);
					}else if ("WAPADMH".equalsIgnoreCase(dateCheck.get(i).toString())){
						stuRegForm.getStudent().setDateWAPADMH(true);
					}else if ("WAPADMS".equalsIgnoreCase(dateCheck.get(i).toString())){
						stuRegForm.getStudent().setDateWAPADMS(true);
					}else if ("WAPADMM".equalsIgnoreCase(dateCheck.get(i).toString())){
						stuRegForm.getStudent().setDateWAPADMM(true);
					}else if ("WAPADMD".equalsIgnoreCase(dateCheck.get(i).toString())){
						stuRegForm.getStudent().setDateWAPADMD(true);
					}else if ("WAPADMNEW".equalsIgnoreCase(dateCheck.get(i).toString())){
						stuRegForm.getStudent().setDateWAPADMNEW(true);
					}else if ("WAPADMRET".equalsIgnoreCase(dateCheck.get(i).toString())){
						stuRegForm.getStudent().setDateWAPADMRET(true);
					}else if ("WAPSTAT".equalsIgnoreCase(dateCheck.get(i).toString())){
						stuRegForm.getStudent().setDateWAPSTAT(true);
					}else if ("WAPAPL".equalsIgnoreCase(dateCheck.get(i).toString())){
						stuRegForm.getStudent().setDateWAPAPL(true);
					}else if ("WAPOFFER".equalsIgnoreCase(dateCheck.get(i).toString())){
						stuRegForm.getStudent().setDateWAPOFFER(true);
					}else if ("WAPOFFICE".equalsIgnoreCase(dateCheck.get(i).toString())){
						stuRegForm.getStudent().setDateWAPOFFICE(true);
					}
				}
			}
			stuRegForm.setWebLoginMsg("");
			stuRegForm.setWebLoginMsg2("");
			
			//log.debug("ApplyForStudentNumberAction - stepLoginSelect - LoginSelectMain=" + stuRegForm.getLoginSelectMain());
			//log.debug("ApplyForStudentNumberAction - stepLoginSelect - AcademicYear="+stuRegForm.getStudent().getAcademicYear()+", LoginSelectMain="+stuRegForm.getLoginSelectMain()+", isAdmin="+stuRegForm.getAdminStaff().isAdmin());
			
			if ("SLP".equalsIgnoreCase(stuRegForm.getLoginSelectMain())){
				//log.debug("ApplyForStudentNumberAction - stepLoginSelect - Set isStuSLP");
				stuRegForm.getStudent().setStuSLP(true);
			}
			//log.debug("ApplyForStudentNumberAction - stepLoginSelect - isStuSLP=" + stuRegForm.getStudent().isStuSLP());

			stuRegForm.getStudent().setNumber("");
			stuRegForm.getStudent().setSurname("");
			stuRegForm.getStudent().setFirstnames("");
			stuRegForm.getStudent().setBirthYear("");
			stuRegForm.getStudent().setBirthMonth("");
			stuRegForm.getStudent().setBirthDay("");
			//log.debug("ApplyForStudentNumberAction - stepLoginSelect - GoTo applyLoginNumber");
			return mapping.findForward("applyLoginNumber");
	}
	
	public ActionForward applySLPSelect(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		//log.debug("ApplyForStudentNumberAction - IN stepSLPSelect");
		
		StudentRegistrationForm stuRegForm = (StudentRegistrationForm) form;
		ActionMessages messages = new ActionMessages();

		stuRegForm.setSelectSLP(stripXSS(request.getParameter("selectSLP"), "selectSLP", "SLPSelect", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
		if (stuRegForm.getSelectSLP() == null || "".equalsIgnoreCase(stuRegForm.getSelectSLP().trim())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please Select if you wish to select a second Short Learning Programme Qualification."));
			addErrors(request, messages);
			return mapping.findForward("applySLPSelect");
		}
		
		//log.debug("ApplyForStudentNumberAction - applySLPSelect - AcademicYear="+stuRegForm.getStudent().getAcademicYear()+", selectSLP="+stuRegForm.getSelectSLP()+", isAdmin="+stuRegForm.getAdminStaff().isAdmin());
		if("YES".equalsIgnoreCase(stuRegForm.getSelectSLP())){
			//log.debug("ApplyForStudentNumberAction - applySLPSelect - selectSLP="+stuRegForm.getSelectSLP().trim()+" - Goto applyQualification");
			return mapping.findForward("applyQualification");
		}else{
			//log.debug("ApplyForStudentNumberAction - applySLPSelect - SLP Student Doesn't want to select a second Choice - Go to Upload");
			stuRegForm.setWebUploadMsg("You have already applied for admission to study at Unisa for the " + stuRegForm.getStudent().getAcademicYear() + " academic year.newLine You may only upload documents or make payments from the main menu.newLine  Send an email to the Applications office if this information is incorrect or if you now intend applying for formal studies at ucl@unisa.ac.za");
			return mapping.findForward("dynamicUpload");
		}
	}
	
	public ActionForward stepAPSNumber(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		//log.debug("ApplyForStudentNumberAction - IN stepAPSNumber");
			
			return mapping.findForward("applyIDNumber");
	}
	
	public ActionForward stepAPSSelect(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

			//log.debug("ApplyForStudentNumberAction - stepAPSSelect - Start");
			
			StudentRegistrationForm stuRegForm = (StudentRegistrationForm) form;
			ActionMessages messages = new ActionMessages();
			
        	//stripXSS(String value, String valueDesc, String action, String studentNr, String year, String period, boolean logYN)
			stuRegForm.setSelectHEMain(stripXSS(request.getParameter("selectHEMain"), "selectHEMain", "APSSelect", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
			if (stuRegForm.getSelectHEMain() == null || "".equalsIgnoreCase(stuRegForm.getSelectHEMain().trim())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Please Select your Higher Education."));
				addErrors(request, messages);
				return mapping.findForward("applyAPSSelect");
			}
			
			//log.debug("ApplyForStudentNumberAction - stepAPSSelect - AcademicYear="+stuRegForm.getStudent().getAcademicYear()+", LoginSelectMain="+stuRegForm.getLoginSelectMain()+", isAdmin="+stuRegForm.getAdminStaff().isAdmin());
			
			if ("NSC".equalsIgnoreCase(stuRegForm.getSelectHEMain().trim())){
				//log.debug("ApplyForStudentNumberAction - stepAPSSelect - SelectHEMain="+stuRegForm.getSelectHEMain().trim()+" - Goto applyIDNumber");
				return mapping.findForward("applyIDNumber");
			}else{
				//log.debug("ApplyForStudentNumberAction - stepAPSSelect - SelectHEMain="+stuRegForm.getSelectHEMain().trim()+" - Goto applyQualification");
				return mapping.findForward("applyQualification");
			}	
	}
	
	public ActionForward loginStu(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

			//log.debug("IN loginStu");
			return mapping.findForward("loginStu");
	}
	
	public ActionForward applyLoginAdmin(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		//log.debug("IN applyLoginAdmin");
	    
		ActionMessages messages = new ActionMessages();
		StudentRegistrationForm stuRegForm = (StudentRegistrationForm) form;
		GeneralMethods gen = new GeneralMethods();
		
		//log.debug("ApplyForStudentNumberAction - applyLoginAdmin - AdminStaff " + stuRegForm.getAdminStaff().getNumber());

    	//stripXSS(String value, String valueDesc, String action, String studentNr, String year, String period, boolean logYN)
		stuRegForm.getAdminStaff().setNumber(stripXSS(stuRegForm.getAdminStaff().getNumber(), "AdminNumber", "loginADM", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
		if (stuRegForm.getAdminStaff().getNumber() == null || "".equalsIgnoreCase(stuRegForm.getAdminStaff().getNumber().trim()) || "userID".equalsIgnoreCase(stuRegForm.getAdminStaff().getNumber().trim())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please Enter User ID."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuRegForm);
			return mapping.findForward("applyLoginAdmin");
		}
		if (stuRegForm.getAdminStaff().getNumber().trim().length() > 8 ){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please Enter a valid User ID."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuRegForm);
			return mapping.findForward("applyLoginAdmin");
		}
		if (!gen.isNumeric(stuRegForm.getAdminStaff().getNumber().trim())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please Enter a valid User ID. ID not Numeric."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuRegForm);
			return mapping.findForward("applyLoginAdmin");
		}
		stuRegForm.getAdminStaff().setPassword(stripXSS(stuRegForm.getAdminStaff().getPassword(), "AdminPassword", "loginADM", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
		if (stuRegForm.getAdminStaff().getPassword() == null || "".equalsIgnoreCase(stuRegForm.getAdminStaff().getPassword().trim()) || "userPWD".equalsIgnoreCase(stuRegForm.getAdminStaff().getPassword().trim())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please Enter User Password."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuRegForm);
			return mapping.findForward("applyLoginAdmin");
		}
		
		//Call Menu95S to Authenticate User
		//log.debug("ApplyForStudentNumberAction - applyLoginAdmin - START MENU95 LOGON");
		Menu95S op = new Menu95S();
		operListener opl = new operListener();
		op.addExceptionListener(opl);
		op.clear();
		
		op.setInWsWorkstationCode("internet");
		op.setInWsWorkstationMacCode("internet");
		op.setInCsfClientServerCommunicationsAction("LI");
			     
		op.setInWsUserNumber(Integer.parseInt(stuRegForm.getAdminStaff().getNumber().trim()));
		
		//Johanet - change 20181213
		if (stuRegForm.getAdminStaff().getPassword().toUpperCase().trim().length()>12) {
			op.setInWsUserPassword(stuRegForm.getAdminStaff().getPassword().toUpperCase().trim().substring(0, 12));
		}else {
			op.setInWsUserPassword(stuRegForm.getAdminStaff().getPassword().toUpperCase().trim());
		}
		
		op.setInWsUserPassword32cs(stuRegForm.getAdminStaff().getPassword().trim());


		op.execute();
		
		if (opl.getException() != null) throw opl.getException();
		if (op.getExitStateType() < 3) throw new Exception(op.getExitStateMsg());
		
		String result = op.getOutCsfStringsString500();
		//log.debug("ApplyForStudentNumberAction - applyLoginAdmin - getOutCsfStringsString500="+result);
		
		boolean isAllow = false;
		boolean isF894 = false;
		if ("".equalsIgnoreCase(result)){

			//Check if user has access to any menu items
			if (op.getOutMenuGroupCount() > 0) {
				//log.debug("ApplyForStudentNumberAction - applyLoginAdmin - User has access to getOutMenuGroupCount="+op.getOutMenuGroupCount());
				for (int i = 0; i < op.getOutMenuGroupCount(); i++){
					//log.debug("ApplyForStudentNumberAction - applyLoginAdmin - User has access - Menu95 Functions="+op.getOutGWsFunctionNumber(i));
					if (op.getOutGWsFunctionNumber(i) == 894){
						isF894 = true;
						isAllow = true;
						//log.debug("ApplyForStudentNumberAction - applyLoginAdmin - User has access to F894 - isF894="+isF894);
					}
				}	
			}else{
				if (op.getOutMenuGroupCount() == 0){
					//log.debug("ApplyForStudentNumberAction - applyLoginAdmin - You do not seem to have access to any Student Admin Functions. Please contact Support.");
					isF894 = false;
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "User not assigned to any functions."));
					addErrors(request, messages);
					return mapping.findForward("applyLoginAdmin");
				}
			}
		}else{
			//log.debug("ApplyForStudentNumberAction - applyLoginAdmin - ERROR MESSAGE RETURNED:" +result);
			if (result.contains("PASSWORD EXPIRED")){
				//log.debug("ApplyForStudentNumberAction - applyLoginAdmin - User Password Expired. Use the Student System to rest your password or contact the ICT Helpdesk.");
				messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", "User Password Expired. Use the Student System to rest your password or contact the ICT Helpdesk."));
					addErrors(request, messages);
				return mapping.findForward("applyLoginAdmin");
			}else{
				messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", result));
					addErrors(request, messages);
				return mapping.findForward("applyLoginAdmin");
			}
		}
		//log.debug("ApplyForStudentNumberAction - applyLoginAdmin - END MENU95 LOGON");
		
		if (isAllow && isF894){
			//Get Radio selection for UnderPostMD from Admin page
			//log.debug("ApplyForStudentNumberAction - applyLoginAdmin - Admission="+stuRegForm.getAdminStaff().getAdmission());
			String checkSelectADMAdmission = stripXSS(stuRegForm.getAdminStaff().getAdmission(), "Admission", "loginADM", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true);
			if (checkSelectADMAdmission != null && !checkSelectADMAdmission.equals("")){
				
				stuRegForm.getAdminStaff().setAdmin(true);
				stuRegForm.setLoginSelectMain(checkSelectADMAdmission);
				
				//log.debug("ApplyForStudentNumberAction - applyLoginAdmin - isAdmin="+stuRegForm.getAdminStaff().isAdmin());
				//log.debug("ApplyForStudentNumberAction - applyLoginAdmin - LoginSelectMain="+stuRegForm.getLoginSelectMain());
				if ("SLP".equalsIgnoreCase(stuRegForm.getLoginSelectMain())){
					stuRegForm.getStudent().setStuSLP(true);
				}
				
				//Get Radio selection for NEW/RET Student from Admin page
				String checkSelectADMType = stripXSS(stuRegForm.getAdminStaff().getStudentType(), "Student Type", "loginADM", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true);
				if (checkSelectADMType != null && !checkSelectADMType.equalsIgnoreCase("")){
					if (checkSelectADMType.equalsIgnoreCase("NEW")){
						stuRegForm.setLoginSelectYesNo("NO");
						if ("UD".equalsIgnoreCase(stuRegForm.getLoginSelectMain()) || "HON".equalsIgnoreCase(stuRegForm.getLoginSelectMain())){
							//log.debug("IN applyLoginAdmin (New Student) - getLoginSelectMain - NO/UD: "+stuRegForm.getLoginSelectMain());
							stuRegForm.setWebLoginMsg("Administrator - First-time applicant");
							stuRegForm.setWebLoginMsg2("");
							setDropdownListsLogin(request,stuRegForm);
							return mapping.findForward("applyLogin");
						}else if ("SLP".equalsIgnoreCase(stuRegForm.getLoginSelectMain())){
							//log.debug("IN applyLoginAdmin (New Student - SLP) - getLoginSelectMain - NO/UD: "+stuRegForm.getLoginSelectMain());
							stuRegForm.setWebLoginMsg("Administrator - First-time applicant - Short Learning Programme");
							stuRegForm.setWebLoginMsg2("Enter your student number for short learning programmes with 8 digits");
							setDropdownListsLogin(request,stuRegForm);
							return mapping.findForward("applyLogin");
						}else if ("MD".equalsIgnoreCase(stuRegForm.getLoginSelectMain())){
							//log.debug("IN applyLoginAdmin (New Student) - getLoginSelectMain - NO/MD: "+stuRegForm.getLoginSelectMain());
							stuRegForm.setWebLoginMsg("Administrator - First-time applicant - Master's or Doctoral");
							stuRegForm.setWebLoginMsg2("");
							setDropdownListsLogin(request,stuRegForm);
							return mapping.findForward("applyLogin");
						}
					}else if (checkSelectADMType.equalsIgnoreCase("RET")){
						stuRegForm.setLoginSelectYesNo("YES");
						if ("UD".equalsIgnoreCase(stuRegForm.getLoginSelectMain()) || "HON".equalsIgnoreCase(stuRegForm.getLoginSelectMain())){
							//Student selected Undergrad/Honours
							//log.debug("IN applyLoginAdmin (Returning Student) - getLoginSelectMain - RET/UD: "+stuRegForm.getLoginSelectMain());
							stuRegForm.setWebLoginMsg("Administrator - Returning student applying for OR changing to a new qualification");
							stuRegForm.setWebLoginMsg2("");
							setDropdownListsLogin(request,stuRegForm);
							return mapping.findForward("applyLogin");
						}else if ("SLP".equalsIgnoreCase(stuRegForm.getLoginSelectMain())){
							//log.debug("IN applyLoginAdmin (Returning Student - SLP) - getLoginSelectMain - NO/UD: "+stuRegForm.getLoginSelectMain());
							stuRegForm.setWebLoginMsg("Administrator - Returning student - Short Learning Programme");
							stuRegForm.setWebLoginMsg2("Enter your student number for short learning programmes with 8 digits");
							setDropdownListsLogin(request,stuRegForm);
							return mapping.findForward("applyLogin");
						}else if ("MD".equalsIgnoreCase(stuRegForm.getLoginSelectMain())){
							//Student selected Masters & Doctorial
							//log.debug("IN applyLoginAdmin (Returning Student) - getLoginSelectMain - RET/MD: "+stuRegForm.getLoginSelectMain());
							String serverpath = ServerConfigurationService.getServerUrl();
							return new ActionForward(serverpath+"/unisa-findtool/default.do?sharedTool=unisa.mdapplications",true);
						}
					}
				}else{
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Please select New or Returning Student."));
					addErrors(request, messages);
					setDropdownListsLogin(request,stuRegForm);
					return mapping.findForward("applyLoginAdmin");
				}
			}else{
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Please select Qualification type. (Undergraduate/Postgraduate/M&D)"));
				addErrors(request, messages);
				setDropdownListsLogin(request,stuRegForm);
				return mapping.findForward("applyLoginAdmin");
			}
		}else{
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "An Error occurred. Please try again."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuRegForm);
			return mapping.findForward("applyLoginAdmin");
		}
		messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", "All Test Error occurred. Please try again."));
		addErrors(request, messages);
		setDropdownListsLogin(request,stuRegForm);
		return mapping.findForward("applyLoginAdmin");
	}
	
	public ActionForward submitLoginSelect(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//log.debug("IN submitLoginSelect");
	    
		StudentRegistrationForm stuRegForm = (StudentRegistrationForm) form;
		ActionMessages messages = new ActionMessages();
		
		//log.debug("ApplyForStudentNumberAction - submitLoginSelect - AcademicYear="+stuRegForm.getStudent().getAcademicYear()+", LoginSelectMain="+stuRegForm.getLoginSelectMain()+", isAdmin="+stuRegForm.getAdminStaff().isAdmin());

		stuRegForm.setApplyType("F");
		stuRegForm.setFromPage("applyLoginSelect");
		
		//Get Parameters from QueryString
		String checkSelectMain = "";
		String checkSelectYesNo = "";
		if (stuRegForm.getLoginSelectMain() == null || "".equals(stuRegForm.getLoginSelectMain())){
			if (request.getParameter("loginSelectMain") != null && !"".equals(request.getParameter("loginSelectMain"))){
				checkSelectMain = stripXSS(request.getParameter("loginSelectMain").toUpperCase().trim(), "SubmitLoginSelect", "SelectMain", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true);
			}else{
				messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "An error ocurred while processing your request. Please log on again."));
				addErrors(request, messages);
				return mapping.findForward("loginSelect");
			}
		}else{
			checkSelectMain = stuRegForm.getLoginSelectMain().toUpperCase().trim();
		}
		if (stuRegForm.getLoginSelectYesNo() == null || "".equals(stuRegForm.getLoginSelectYesNo())){
			if (request.getParameter("loginSelectYesNo") != null && !"".equals(request.getParameter("loginSelectYesNo"))){
				checkSelectYesNo = stripXSS(request.getParameter("loginSelectYesNo").toUpperCase().trim(), "SubmitLoginSelect", "SelectYN", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true);
			}else{
				messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "An error ocurred while processing your request. Please log on again."));
				addErrors(request, messages);
				return mapping.findForward("loginSelect");
			}
		}else{
			checkSelectYesNo = stuRegForm.getLoginSelectYesNo().toUpperCase().trim();
		}
		
		//log.debug("ApplyForStudentNumberAction submitLoginSelect - checkSelectMain " + checkSelectMain + " checkSelectYesNo [" + checkSelectYesNo+"]");
			
		if (checkSelectMain != null && !checkSelectMain.equalsIgnoreCase("")){
			stuRegForm.setLoginSelectMain(checkSelectMain);
			//log.debug("ApplyForStudentNumberAction - submitLoginSelect - LoginSelectMain Exists=["+checkSelectMain+"]");
		}else{
			//log.debug("ApplyForStudentNumberAction - No Main Input parameters submitted - Nowhere to go...");
			if (stuRegForm.getAdminStaff().isAdmin()){
				return mapping.findForward("loginStaff");
			}else{
				return mapping.findForward("loginStu");
			}
		}
		if (checkSelectYesNo != null && !checkSelectYesNo.equalsIgnoreCase("")){
		   stuRegForm.setLoginSelectYesNo(checkSelectYesNo);
		   //log.debug("ApplyForStudentNumberAction - submitLoginSelect - LoginSelectYesNo Exists=["+checkSelectYesNo+"]");
		}else{
			//log.debug("ApplyForStudentNumberAction - No YesNo Input parameters submitted - Nowhere to go...");
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student indication of exiting number not found. Please log on again."));
				addErrors(request, messages);
			return mapping.findForward("loginSelect");
		}
		
		//log.debug("IN submitLoginSelect (Check for New or Returning Student) - LoginSelectYesNo: " + stuRegForm.getLoginSelectYesNo());

		if ("YES".equalsIgnoreCase(stuRegForm.getLoginSelectYesNo())){
			//Student Confirms that He/She does have a Student Number already
			//log.debug("IN submitLoginSelect (Returning Student) - getLoginSelectYesNo: YES");
			stuRegForm.getStudent().setStuExist("curStu");
			//request.setAttribute("stuRegForm", stuRegForm);
			if ("UD".equalsIgnoreCase(stuRegForm.getLoginSelectMain()) || "HON".equalsIgnoreCase(stuRegForm.getLoginSelectMain())){
				//Student selected Undergrad/Honours
				//log.debug("IN submitLoginSelect (Returning Student) - getLoginSelectMain = "+stuRegForm.getLoginSelectMain());
				if (stuRegForm.getAdminStaff().isAdmin()){
					stuRegForm.setWebLoginMsg("Administrator - Returning student applying for OR changing to a new qualification");
					stuRegForm.setWebLoginMsg2("");
					setDropdownListsLogin(request,stuRegForm);
					return mapping.findForward("applyLogin");
				}else{
					stuRegForm.setWebLoginMsg("Returning student applying for OR changing to a new qualification");
					stuRegForm.setWebLoginMsg2("");
					setDropdownListsLogin(request,stuRegForm);
					return mapping.findForward("applyLogin");
				}
			}else if ("SLP".equalsIgnoreCase(stuRegForm.getLoginSelectMain())){
				//Student selected Undergrad/Honours
				//log.debug("IN submitLoginSelect (Returning Student - SLP) - getLoginSelectMain = "+stuRegForm.getLoginSelectMain());
				if (stuRegForm.getAdminStaff().isAdmin()){
					stuRegForm.setWebLoginMsg("Administrator - Returning SLP Student applying for OR changing to a new qualification.");
					stuRegForm.setWebLoginMsg2("Enter your student number for short learning programmes with 8 digits");
					setDropdownListsLogin(request,stuRegForm);
					return mapping.findForward("applyLogin");
				}else{
					stuRegForm.setWebLoginMsg("Returning SLP Student applying for OR changing to a new qualification");
					stuRegForm.setWebLoginMsg2("Enter your student number for short learning programmes with 8 digits");
					setDropdownListsLogin(request,stuRegForm);
					return mapping.findForward("applyLogin");
				}
			}else if ("MD".equalsIgnoreCase(stuRegForm.getLoginSelectMain())){
				//Student selected Masters & Doctorial
				//log.debug("IN submitLoginSelect (Returning Student) - getLoginSelectMain - MD: "+stuRegForm.getLoginSelectMain());
				String serverpath = ServerConfigurationService.getServerUrl();
				return new ActionForward(serverpath+"/unisa-findtool/default.do?sharedTool=unisa.mdapplications",true);
			}else if ("DOC".equalsIgnoreCase(stuRegForm.getLoginSelectMain())){
				//log.debug("IN submitLoginSelect (Returning Student) - getLoginSelectMain: DOC");
				if (stuRegForm.getAdminStaff().isAdmin()){
					stuRegForm.setWebLoginMsg("Administrator - Returning student - Document Upload");
					stuRegForm.setWebLoginMsg2("");
					setDropdownListsLogin(request,stuRegForm);
					return mapping.findForward("applyLogin");
				}else{
					stuRegForm.setWebLoginMsg("Returning student - Document Upload");
					stuRegForm.setWebLoginMsg2("");
					setDropdownListsLogin(request,stuRegForm);
					return mapping.findForward("applyLogin");
				}
			}else if ("STATUS".equalsIgnoreCase(stuRegForm.getLoginSelectMain())){
				//log.debug("IN submitLoginSelect (Returning Student) - getLoginSelectMain: STATUS");
				if (stuRegForm.getAdminStaff().isAdmin()){
					stuRegForm.setWebLoginMsg("Administrator - Returning student - Application Status");
					stuRegForm.setWebLoginMsg2("");
					setDropdownListsLogin(request,stuRegForm);
					return mapping.findForward("applyLogin");
				}else{
					stuRegForm.setWebLoginMsg("Application Status");
					stuRegForm.setWebLoginMsg2("");
					setDropdownListsLogin(request,stuRegForm);
					return mapping.findForward("applyLogin");
				}
			}else if ("APPEAL".equalsIgnoreCase(stuRegForm.getLoginSelectMain())){
				//log.debug("IN submitLoginSelect (Returning Student) - getLoginSelectMain: APPEAL");
				if (stuRegForm.getAdminStaff().isAdmin()){
					stuRegForm.setWebLoginMsg("Administrator - Appeal Process");
					stuRegForm.setWebLoginMsg2("");
					setDropdownListsLogin(request,stuRegForm);
					return mapping.findForward("applyLogin");
				}else{
					stuRegForm.setWebLoginMsg("Appeal Process");
					stuRegForm.setWebLoginMsg2("");
					setDropdownListsLogin(request,stuRegForm);
					return mapping.findForward("applyLogin");
				}
			}else if ("OFFER".equalsIgnoreCase(stuRegForm.getLoginSelectMain())){
				//log.debug("IN submitLoginSelect (Returning Student) - getLoginSelectMain: OFFER");
				if (stuRegForm.getAdminStaff().isAdmin()){
					stuRegForm.setWebLoginMsg("Administrator - Offer Process");
					stuRegForm.setWebLoginMsg2("");
					setDropdownListsLogin(request,stuRegForm);
					return mapping.findForward("applyLogin");
				}else{
					stuRegForm.setWebLoginMsg("Offer Process");
					stuRegForm.setWebLoginMsg2("");
					setDropdownListsLogin(request,stuRegForm);
					return mapping.findForward("applyLogin");
				}
			}else if ("PAY".equalsIgnoreCase(stuRegForm.getLoginSelectMain())){
				//log.debug("IN submitLoginSelect (Returning Student) - getLoginSelectMain: PAY");
				String serverpath = ServerConfigurationService.getServerUrl();
				return new ActionForward(serverpath+"/unisa-findtool/default.do?sharedTool=unisa.creditcardpayment",true);
			}
		}else {
			//Student doesn't have a number yet, thus new student
			//log.debug("IN submitLoginSelect (New Student) - getLoginSelectYesNo: NO");
			stuRegForm.getStudent().setStuExist("newStu");
			//request.setAttribute("stuRegForm", stuRegForm);
			if ("UD".equalsIgnoreCase(stuRegForm.getLoginSelectMain()) || "HON".equalsIgnoreCase(stuRegForm.getLoginSelectMain())){
				//log.debug("IN submitLoginSelect (New Student) - getLoginSelectMain: "+stuRegForm.getLoginSelectMain());
				if (stuRegForm.getAdminStaff().isAdmin()){
					stuRegForm.setWebLoginMsg("Administrator - First-time applicant");
					stuRegForm.setWebLoginMsg2("");
					setDropdownListsLogin(request,stuRegForm);
					return mapping.findForward("applyLogin");
				}else{
					if (stuRegForm.getStudent().isDateWAPU() || stuRegForm.getStudent().isDateWAPM() || stuRegForm.getStudent().isDateWAPD() || stuRegForm.getStudent().isDateWAPH()){
						stuRegForm.setWebLoginMsg("First-time applicant");
						stuRegForm.setWebLoginMsg2("");
						setDropdownListsLogin(request,stuRegForm);
						return mapping.findForward("applyLogin");
					}else{
						stuRegForm.setAllowLogin(false);
						return mapping.findForward("applyLogin");
					}
				}
			}else if ("SLP".equalsIgnoreCase(stuRegForm.getLoginSelectMain())){
				//log.debug("IN submitLoginSelect (New Student - SLP) - getLoginSelectMain: "+stuRegForm.getLoginSelectMain());
				if (stuRegForm.getAdminStaff().isAdmin()){
					stuRegForm.setWebLoginMsg("Administrator - First-time SLP Applicant");
					stuRegForm.setWebLoginMsg2("");
					setDropdownListsLogin(request,stuRegForm);
					return mapping.findForward("applyLogin");
				}else{
					if (stuRegForm.getStudent().isDateWAPS()){
						stuRegForm.setWebLoginMsg("First-time Short Learning Programme Applicant");
						stuRegForm.setWebLoginMsg2("");
						setDropdownListsLogin(request,stuRegForm);
						return mapping.findForward("applyLogin");
					}else{
						stuRegForm.setAllowLogin(false);
						return mapping.findForward("applyLogin");
					}
				}
			}else if ("MD".equalsIgnoreCase(stuRegForm.getLoginSelectMain())){
				//log.debug("IN submitLoginSelect (New Student) - getLoginSelectMain: "+stuRegForm.getLoginSelectMain());
				if (stuRegForm.getAdminStaff().isAdmin()){
					stuRegForm.setWebLoginMsg("Administrator - First-time applicant - Master's or Doctoral");
					stuRegForm.setWebLoginMsg2("");
					setDropdownListsLogin(request,stuRegForm);
					return mapping.findForward("applyLogin");
				}else{
					stuRegForm.setWebLoginMsg("First-time applicant - Master's or Doctoral");
					stuRegForm.setWebLoginMsg2("");
					setDropdownListsLogin(request,stuRegForm);
					return mapping.findForward("applyLogin");
				}
			}else if ("DOC".equalsIgnoreCase(stuRegForm.getLoginSelectMain())){
				//log.debug("IN submitLogin (New Student) - getLoginSelectMain: DOC");
				if (stuRegForm.getAdminStaff().isAdmin()){
					stuRegForm.setWebLoginMsg("Administrator - First-time applicant - Document Upload");
					stuRegForm.setWebLoginMsg2("");
					setDropdownListsLogin(request,stuRegForm);
					return mapping.findForward("applyLogin");
				}else{
					if (stuRegForm.getStudent().isDateWAPU() || stuRegForm.getStudent().isDateWAPM() || stuRegForm.getStudent().isDateWAPD() || stuRegForm.getStudent().isDateWAPH()){
						stuRegForm.setWebLoginMsg("First-time applicant - Document Upload");
						stuRegForm.setWebLoginMsg2("");
						setDropdownListsLogin(request,stuRegForm);
						return mapping.findForward("applyLogin");
					}else{
						stuRegForm.setAllowLogin(false);
						return mapping.findForward("applyLogin");
					}
				}
			}else if ("PAY".equalsIgnoreCase(stuRegForm.getLoginSelectMain())){
				//log.debug("IN submitLoginSelect (New Student) - getLoginSelectMain: PAY");
				if (stuRegForm.getAdminStaff().isAdmin()){
					stuRegForm.setWebLoginMsg("Administrator - First-time applicant - Credit Payment");
					stuRegForm.setWebLoginMsg2("");
					setDropdownListsLogin(request,stuRegForm);
					return mapping.findForward("applyLogin");
				}else{
					if (stuRegForm.getStudent().isDateWAPU() || stuRegForm.getStudent().isDateWAPM() || stuRegForm.getStudent().isDateWAPD() || stuRegForm.getStudent().isDateWAPH()){
						stuRegForm.setWebLoginMsg("First-time applicant - Credit Payment");
						stuRegForm.setWebLoginMsg2("");
						setDropdownListsLogin(request,stuRegForm);
						return mapping.findForward("applyLogin");
					}else{
						stuRegForm.setAllowLogin(false);
						return mapping.findForward("applyLogin");
					}
				}
			}else if ("STATUS".equalsIgnoreCase(stuRegForm.getLoginSelectMain())){
				//log.debug("IN submitLoginSelect (New Student) - getLoginSelectMain: STATUS");
				if (stuRegForm.getAdminStaff().isAdmin()){
					stuRegForm.setWebLoginMsg("Administrator - First-time applicant - Application Status");
					stuRegForm.setWebLoginMsg2("");
					setDropdownListsLogin(request,stuRegForm);
					return mapping.findForward("applyLogin");
				}else{
					if (stuRegForm.getStudent().isDateWAPU() || stuRegForm.getStudent().isDateWAPM() || stuRegForm.getStudent().isDateWAPD() || stuRegForm.getStudent().isDateWAPH()){
						stuRegForm.setWebLoginMsg("Application Status");
						stuRegForm.setWebLoginMsg2("");
						setDropdownListsLogin(request,stuRegForm);
						return mapping.findForward("applyLogin");
					}else{
						stuRegForm.setAllowLogin(false);
						return mapping.findForward("applyLogin");
					}
				}
			}else if ("APPEAL".equalsIgnoreCase(stuRegForm.getLoginSelectMain())){
				//log.debug("IN submitLoginSelect (New Student) - getLoginSelectMain: APPEAL");
				if (stuRegForm.getAdminStaff().isAdmin()){
					stuRegForm.setWebLoginMsg("Administrator - First-time applicant - Appeal Process");
					stuRegForm.setWebLoginMsg2("");
					setDropdownListsLogin(request,stuRegForm);
					return mapping.findForward("applyLogin");
				}else{
					if (stuRegForm.getStudent().isDateWAPU() || stuRegForm.getStudent().isDateWAPM() || stuRegForm.getStudent().isDateWAPD() || stuRegForm.getStudent().isDateWAPH()){
						stuRegForm.setWebLoginMsg("Appeal Process");
						stuRegForm.setWebLoginMsg2("");
						setDropdownListsLogin(request,stuRegForm);
						return mapping.findForward("applyLogin");
					}else{
						stuRegForm.setAllowLogin(false);
						return mapping.findForward("applyLogin");
					}
				}
			}else if ("OFFER".equalsIgnoreCase(stuRegForm.getLoginSelectMain())){
				//log.debug("IN submitLoginSelect (New Student) - getLoginSelectMain: OFFER");
				if (stuRegForm.getAdminStaff().isAdmin()){
					stuRegForm.setWebLoginMsg("Administrator - First-time applicant - Offer Process");
					stuRegForm.setWebLoginMsg2("");
					setDropdownListsLogin(request,stuRegForm);
					return mapping.findForward("applyLogin");
				}else{
					if (stuRegForm.getStudent().isDateWAPU() || stuRegForm.getStudent().isDateWAPM() || stuRegForm.getStudent().isDateWAPD() || stuRegForm.getStudent().isDateWAPH()){
						stuRegForm.setWebLoginMsg("Offer Process");
						stuRegForm.setWebLoginMsg2("");
						setDropdownListsLogin(request,stuRegForm);
						return mapping.findForward("applyLogin");
					}else{
						stuRegForm.setAllowLogin(false);
						return mapping.findForward("applyLogin");
					}
				}
			}
		}
		return mapping.findForward("loginSelect");
	}
	
	public ActionForward checkNewBack(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		StudentRegistrationForm stuRegForm = (StudentRegistrationForm) form;

		stuRegForm.getStudent().setStuExist("newStu");
		//log.debug("ApplyForStudentNumberAction - checkNewBack - Admin="+stuRegForm.getAdminStaff().isAdmin());
		if (stuRegForm.getAdminStaff().isAdmin()){
			return mapping.findForward("loginStaff");
		}else{
			return mapping.findForward("loginStu");
		}
	}
	
	public ActionForward applyLoginNew(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		//log.debug("IN applyLoginNew");
	    
		ActionMessages messages = new ActionMessages();
		StudentRegistrationForm stuRegForm = (StudentRegistrationForm) form;
		GeneralMethods gen = new GeneralMethods();
		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
		
		//log.debug("ApplyForStudentNumberAction - applyLoginNew - AcademicYear="+stuRegForm.getStudent().getAcademicYear()+", LoginSelectMain="+stuRegForm.getLoginSelectMain()+", isAdmin="+stuRegForm.getAdminStaff().isAdmin());

		if (stuRegForm.getStudent().getAcademicYear() == null){
			stuRegForm.getStudent().setAcademicYear(getCurrentAcademicYear());
			stuRegForm.getStudent().setAcademicPeriod(getCurrentAcademicPeriod());
		}
		
		stuRegForm.setApplyType("F");
		if (stuRegForm.getAdminStaff().isAdmin()){
			stuRegForm.setFromPage("stepLoginAdmin");
		}else{
			stuRegForm.setFromPage("applyLoginSelect");
		}
		
		
		stuRegForm.getStudent().setSurname(stripXSS(stuRegForm.getStudent().getSurname(), "Surname", "LoginNew", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
		//log.debug("ApplyForStudentNumberAction - applyLoginNew - StudentNr=" + stuRegForm.getStudent().getNumber()+", Surname=" + stuRegForm.getStudent().getSurname());
		if (stuRegForm.getStudent().getSurname() == null || "".equalsIgnoreCase(stuRegForm.getStudent().getSurname())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Enter student surname."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuRegForm);
			return mapping.findForward("applyLogin");
		}
		if (!isNameValid(stuRegForm.getStudent().getSurname())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Enter a valid student surname."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuRegForm);
			return mapping.findForward("applyLogin");
		}

		stuRegForm.getStudent().setFirstnames(stripXSS(stuRegForm.getStudent().getFirstnames(), "Firstnames", "LoginNew", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
		//log.debug("ApplyForStudentNumberAction - applyLoginNew - StudentNr=" + stuRegForm.getStudent().getNumber()+", Firstnames=" + stuRegForm.getStudent().getFirstnames());
		if (stuRegForm.getStudent().getFirstnames() == null || "".equalsIgnoreCase(stuRegForm.getStudent().getFirstnames())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Enter student first names."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuRegForm);
			return mapping.findForward("applyLogin");
		}
		if (!isNameValid(stuRegForm.getStudent().getFirstnames())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Enter a valid student first names."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuRegForm);
			return mapping.findForward("applyLogin");
		}
		
		stuRegForm.getStudent().setBirthYear(stripXSS(stuRegForm.getStudent().getBirthYear(), "BrithYear", "LoginNew", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
		stuRegForm.getStudent().setBirthYear(stuRegForm.getStudent().getBirthYear().trim());
		//log.debug("ApplyForStudentNumberAction - applyLoginNew - StudentNr=" + stuRegForm.getStudent().getNumber()+", BirthYear=" + stuRegForm.getStudent().getBirthYear());
		if (stuRegForm.getStudent().getBirthYear() == null){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Year) is blank - must be numeric format [CCYY]."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuRegForm);
			return mapping.findForward("applyLogin");
		}
		if ("".equals(stuRegForm.getStudent().getBirthYear().trim())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Year) is empty - must be numeric format [CCYY]."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuRegForm);
			return mapping.findForward("applyLogin");
		}
		if (!gen.isNumeric(stuRegForm.getStudent().getBirthYear())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Year) must be numeric format [CCYY]."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuRegForm);
			return mapping.findForward("applyLogin");
		}
		
		//log.debug("ApplyForStudentNumberAction - applyLoginNew - BirthYear=" + stuRegForm.getStudent().getBirthYear()+", Academic_Year="+stuRegForm.getStudent().getAcademicYear());
		if (Integer.parseInt(stuRegForm.getStudent().getBirthYear()) <= 1900 || Integer.parseInt(stuRegForm.getStudent().getBirthYear()) >= Integer.parseInt(stuRegForm.getStudent().getAcademicYear())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Year) invalid."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuRegForm);
			return mapping.findForward("applyLogin");
		}
		if (stuRegForm.getStudent().getBirthYear().length() > 4){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Year) invalid. Please enter 4 characters or less. " ));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuRegForm);
			return mapping.findForward("applyLogin");
		}
		
		stuRegForm.getStudent().setBirthMonth(stripXSS(stuRegForm.getStudent().getBirthMonth(), "BirthMonth", "LoginNew", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
		stuRegForm.getStudent().setBirthMonth(stuRegForm.getStudent().getBirthMonth().trim());
		//log.debug("ApplyForStudentNumberAction - applyLoginNew - StudentNr=" + stuRegForm.getStudent().getNumber()+", BirthMonth=" + stuRegForm.getStudent().getBirthMonth());
		if (stuRegForm.getStudent().getBirthMonth() == null){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Month) is blank - must be numeric format [MM]."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuRegForm);
			return mapping.findForward("applyLogin");
		}
		if ("".equals(stuRegForm.getStudent().getBirthMonth())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Month) is empty - must be numeric format [MM]."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuRegForm);
			return mapping.findForward("applyLogin");
		}
		if (!gen.isNumeric(stuRegForm.getStudent().getBirthMonth())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Month) must be numeric format [MM]."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuRegForm);
			return mapping.findForward("applyLogin");
		}
		if (Integer.parseInt(stuRegForm.getStudent().getBirthMonth()) < 1 || Integer.parseInt(stuRegForm.getStudent().getBirthMonth()) > 12){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Month) invalid."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuRegForm);
			return mapping.findForward("applyLogin");
		}
		if (stuRegForm.getStudent().getBirthMonth().length() > 2){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Month) invalid. Please enter 2 characters or less. " ));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuRegForm);
			return mapping.findForward("applyLogin");
		}
		
		//2015 Edmund - Expanded Date Tests as Students entered dates like 30/02/1992 etc
		int testMonth = Integer.parseInt(stuRegForm.getStudent().getBirthMonth());
		int daysMonth = 0;
		if (testMonth == 1 || testMonth == 3 || testMonth == 5 || testMonth == 7 || testMonth == 8 || testMonth == 10 || testMonth ==12){
			daysMonth = 31;
		}else if (testMonth == 4 || testMonth == 6 || testMonth == 9 || testMonth == 11){
			daysMonth = 30;
		}else{
			daysMonth = 28; //Checking for Leap year further below...
		}
		
		stuRegForm.getStudent().setBirthDay(stripXSS(stuRegForm.getStudent().getBirthDay(), "BirthDay", "LoginNew", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
		stuRegForm.getStudent().setBirthDay(stuRegForm.getStudent().getBirthDay().trim());
		//log.debug("ApplyForStudentNumberAction - applyLoginNew - StudentNr=" + stuRegForm.getStudent().getNumber()+", BirthDay=" + stuRegForm.getStudent().getBirthDay());
		if (stuRegForm.getStudent().getBirthDay() == null){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Day) is blank - must be numeric format [DD]."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuRegForm);
			return mapping.findForward("applyLogin");
		}
		if ("".equals(stuRegForm.getStudent().getBirthDay())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Day) is empty - must be numeric format [DD]."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuRegForm);
			return mapping.findForward("applyLogin");
		}
		if (!gen.isNumeric(stuRegForm.getStudent().getBirthDay())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Day) must be numeric format [DD]."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuRegForm);
			return mapping.findForward("applyLogin");
		}
		if (Integer.parseInt(stuRegForm.getStudent().getBirthDay()) < 1 || Integer.parseInt(stuRegForm.getStudent().getBirthDay()) > 31){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Day) invalid."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuRegForm);
			return mapping.findForward("applyLogin");
		}
		if (stuRegForm.getStudent().getBirthDay().length() > 2){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Day) invalid. Please enter 2 characters or less. " ));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuRegForm);
			return mapping.findForward("applyLogin");
		}
		if (Integer.parseInt(stuRegForm.getStudent().getBirthDay()) < 1){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Day) invalid."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuRegForm);
			return mapping.findForward("applyLogin");
		}
		String monthName = "";
		if (daysMonth == 31 && Integer.parseInt(stuRegForm.getStudent().getBirthDay()) > 31){
			monthName = getMonth(testMonth);
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Day) invalid. " + monthName + " only has 31 days."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuRegForm);
			return mapping.findForward("applyLogin");
		}
		if (daysMonth == 30 && Integer.parseInt(stuRegForm.getStudent().getBirthDay()) > 30){
			monthName = getMonth(testMonth);
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Day) invalid. " + monthName + " only has 30 days."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuRegForm);
			return mapping.findForward("applyLogin");
		}

		if (Integer.parseInt(stuRegForm.getStudent().getBirthMonth()) == 2 && Integer.parseInt(stuRegForm.getStudent().getBirthDay()) > 29){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", "Student Date of Birth invalid. " ));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuRegForm);
			return mapping.findForward("applyLogin");
		}

		if (Integer.parseInt(stuRegForm.getStudent().getBirthMonth()) == 2 && Integer.parseInt(stuRegForm.getStudent().getBirthDay()) == 29){
			String leapCheck = dao.checkLeapYear(stuRegForm.getStudent().getBirthYear());
			if ("Not a leap year".equalsIgnoreCase(leapCheck)){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Student Date of Birth (29/February/"+stuRegForm.getStudent().getBirthYear()+") invalid. " + stuRegForm.getStudent().getBirthYear() + " was not a leap year." ));
				addErrors(request, messages);
				setDropdownListsLogin(request,stuRegForm);
				return mapping.findForward("applyLogin");
			}
		}
		
		//Add "0" incase Student enters just one character for Birthday or Month
		if (stuRegForm.getStudent().getBirthMonth().length() < 2){
			stuRegForm.getStudent().setBirthMonth("0" + stuRegForm.getStudent().getBirthMonth());
		}
		if (stuRegForm.getStudent().getBirthDay().length() < 2){
			stuRegForm.getStudent().setBirthDay("0" + stuRegForm.getStudent().getBirthDay());
		}
		
		String bDay = stuRegForm.getStudent().getBirthDay() + "/" + stuRegForm.getStudent().getBirthMonth() + "/" + stuRegForm.getStudent().getBirthYear();
	
		/**
		 * Validation Test: Check if student has a student number
		 * "curStu"; //Current Student
		 * "newStu"; //New Student
		 **/

		/** Check the code against the flow diagram by the numbers provided **/
		//log.debug("ApplyForStudentNumberAction - applyLoginNew (1) - Check if Student has number");

		String vNumCheck = dao.validateStudentLogin(stuRegForm.getStudent().getSurname(), stuRegForm.getStudent().getFirstnames(), bDay, stuRegForm.getLoginSelectMain());
		//log.debug("ApplyForStudentNumberAction - applyLoginNew - StuExist: " + vNumCheck);
		
		/** Flow Check: (1) **/
		if ("newStu".equalsIgnoreCase(vNumCheck)){ //Student doesn't have a number yet
			//log.debug("ApplyForStudentNumberAction - applyLoginNew - N " + stuRegForm.getStudent().getNumber() + " vN " + vNumCheck);
			
			if ("STATUS".equalsIgnoreCase(stuRegForm.getLoginSelectMain()) || "APPEAL".equalsIgnoreCase(stuRegForm.getLoginSelectMain()) || "OFFER".equalsIgnoreCase(stuRegForm.getLoginSelectMain())){
				stuRegForm.setWebLoginMsg("Student not found.");
				stuRegForm.setWebLoginMsg2("Please apply for a student number before using this function!newline Click OK retry or click Cancel if you wish to quit the application process.");
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "No Student exists - Please apply for a student number before you can view your application status or appeal a decision."));
				addErrors(request, messages);
				/** Flow Check: (xx) **/
				//log.debug("ApplyForStudentNumberAction - applyLoginNew (8) - New Student with no Student Number. Return to main page");
				return mapping.findForward("applyLoginSelect");
			}
			
			//Check if student already has a temp number. If so, use it again, else assign random number
			//Note this is disabled as it will only work once we also request the ID/Passport number as there are many students with same surname, firstname and born on the same day.
			/** String tempStuNr = "";

				String birthConcat = stuRegForm.getStudent().getBirthYear()+stuRegForm.getStudent().getBirthMonth()+stuRegForm.getStudent().getBirthDay();
				tempStuNr = dao.checkStuTempNr(stuRegForm.getStudent().getSurname(), stuRegForm.getStudent().getFirstnames(), birthConcat, stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod());
				
				if (tempStuNr != null && !"".equalsIgnoreCase(tempStuNr.trim())){
					stuRegForm.getStudent().setNumber(tempStuNr);
				}else{ //No Temp Number, thus assign Random Temp Number
			**/
				//Save initial StudentNr in order to keep record in STUXML as someone else might grab the same number
				String referenceData = stuRegForm.getStudent().getSurname().toUpperCase()+","+stuRegForm.getStudent().getFirstnames().toUpperCase()+","+stuRegForm.getStudent().getBirthYear()+stuRegForm.getStudent().getBirthMonth()+stuRegForm.getStudent().getBirthDay();
	
				Random random = new Random();
				int randomLow = 90000001;
				int randomHigh = 99999999;
				int randomStu = random.nextInt((randomHigh-randomLow) + 1) + randomLow;
	
				//log.debug("ApplyForStudentNumberAction - applyLoginNew - Random Initial: " + randomStu);
				boolean existRandom = dao.getXMLRandom(randomStu);
				while(existRandom){ //Make sure you get a unique number by checking STUXML and STU to ensure that it doesn't exist already
					//log.debug("ApplyForStudentNumberAction - applyLoginNew - Random Exists: " + randomStu);
						randomStu = random.nextInt((randomHigh-randomLow) + 1) + randomLow;
						existRandom = dao.getXMLRandom(randomStu);
					//log.debug("ApplyForStudentNumberAction - applyLoginNew - New Random: " + randomStu);
				}
				if (!existRandom){
					//Write student details to STUXML for later verification - Testing Thread security
					//log.debug("ApplyForStudentNumberAction - applyLoginNew - applyLoginNew - Write student details to STUXML");
					boolean stuError = false;
					String checkStu = "";
					int nextRef = 0;
					String queryResultRef = dao.getSTUXMLRef(Integer.toString(randomStu), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), "StuInfo", "1", "applyLoginNew");
					//log.debug("ApplyForStudentNumberAction - applyLoginNew - applyLoginNew - queryResultRef="+queryResultRef);
					if (queryResultRef.toUpperCase().contains("ERROR")){
						stuError = true;
					}
					if (!stuError){
						try{
							nextRef = Integer.parseInt(queryResultRef);
							nextRef++;
						}catch(Exception e){
							log.warn("ApplyForStudentNumberAction - applyLoginNew - applyLoginNew - nextRef not Numeric - nextRef="+nextRef);
						}
						//log.debug("ApplyForStudentNumberAction - applyLoginNew - queryResultRef="+queryResultRef+", nextRef="+nextRef);
						checkStu = dao.saveSTUXML(Integer.toString(randomStu), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), "StuInfo", "1", nextRef, referenceData, "applyLoginNew", "INSERT");
						if (checkStu.toUpperCase().contains("ERROR")){
							stuError = true;
						}
					}
					//log.debug("ApplyForStudentNumberAction - applyLoginNew - Write student details to STUXML - Done - checkStu="+checkStu+", referenceData="+referenceData+", referenceSequence="+nextRef);
	
				}
				
				//log.debug("ApplyForStudentNumberAction - applyLoginNew - Final Random: " + randomStu);
				
				stuRegForm.getStudent().setNumber(Integer.toString(randomStu));
				//log.debug("ApplyForStudentNumberAction - applyLoginNew - Random Stu Nr: " + stuRegForm.getStudent().getNumber());
			/** String tempStuNr = "";
			}
			/* */
				
			//Check if Student Log Exists, if so, set Application Sequence Number
			int currLogSeq = 0;
        	currLogSeq = dao.getSTULOGRef(stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getStudent().getNumber());
        	//log.debug("ApplyForStudentNumberAction - applyLoginNew - currLogSeq="+currLogSeq+", studentSeqNr="+stuRegForm.getApplySEQUENCE());
        	if (currLogSeq != stuRegForm.getApplySEQUENCE() || currLogSeq == 0 || stuRegForm.getApplySEQUENCE() == 0){
        		currLogSeq++;
        	}
        	stuRegForm.setApplySEQUENCE(currLogSeq);
        	//log.debug("ApplyForStudentNumberAction - applyLoginNew - STULOG Sequence Number to Use="+stuRegForm.getApplySEQUENCE());

			//Get Browser Details
			//Is client behind something like a Proxy Server?
			String ipAddress = request.getHeader("X-FORWARDED-FOR");
			if (ipAddress == null) {
				   ipAddress = request.getRemoteAddr();
			}
			stuRegForm.getStudent().setStuIPAddress(ipAddress);
			stuRegForm.getStudent().setStuBrowserAgent(request.getHeader("User-Agent"));
			String browser = getBrowserInfo(stuRegForm.getStudent().getStuBrowserAgent());
			if (browser != null && !"".equals(browser.trim()) && browser.contains("@") && browser.length() > 3){
				int pos = browser.indexOf("@");
				stuRegForm.getStudent().setStuBrowser(browser.substring(0,pos));
				stuRegForm.getStudent().setStuWksOS(browser.substring(pos+1,browser.length()));
				//log.debug("ApplyForStudentNumberAction - applyLoginNew - Write Browser details to STUXML");
				//Write Browser details to STUXML for later verification - Testing Thread security
				String browserData = stuRegForm.getStudent().getNumber()+","+stuRegForm.getStudent().getStuIPAddress()+","+stuRegForm.getStudent().getStuBrowser()+","+stuRegForm.getStudent().getStuWksOS();
				//log.debug("ApplyForStudentNumberAction - applyLoginNew - browserData="+browserData);
				boolean browserError = false;
				String queryResultBrowser = "";
				String checkBrowser = "";
				int nextBrowser = 1;
				queryResultBrowser = dao.getSTUXMLRef(stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), "WKSInfo", "1", "applyLoginNew");
				//log.debug("ApplyForStudentNumberAction - applyLoginNew - queryResultRef="+queryResultBrowser);
				if (queryResultBrowser.toUpperCase().contains("ERROR")){
					browserError = true;
				}
				if (!browserError){
					try{
						nextBrowser = Integer.parseInt(queryResultBrowser);
						nextBrowser++;
					}catch(Exception e){
						log.warn("ApplyForStudentNumberAction - applyLoginNew - nextRef not Numeric - nextRef="+nextBrowser);
					}
					//log.debug("ApplyForStudentNumberAction - applyLoginNew - queryResultBrowser="+queryResultBrowser+", nextRef="+nextBrowser);
					checkBrowser = dao.saveSTUXML(stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), "WKSInfo", "1", nextBrowser, browserData, "applyLoginNew", "INSERT");
					if (checkBrowser.toUpperCase().contains("ERROR")){
						browserError = true;
					}
				}
				//log.debug("ApplyForStudentNumberAction - applyLoginNew - Write student details to STUXML - Done - checkStu="+checkBrowser+", browserData="+browserData+", referenceSequence="+nextBrowser);
			}
			//log.debug("ApplyForStudentNumberAction - applyLoginNew - N " + stuRegForm.getStudent().getNumber() + " S " + stuRegForm.getStudent().getSurname() + " F " + stuRegForm.getStudent().getFirstnames() + " Y " + stuRegForm.getStudent().getBirthYear() + " M " + stuRegForm.getStudent().getBirthMonth() + " D " + stuRegForm.getStudent().getBirthDay());

			/** Flow Check: (2) **/
			//log.debug("ApplyForStudentNumberAction - applyLoginNew (2) - New Student - No Student Number - Goto application for admission form / function: applyQualification");
			stuRegForm.getStudent().setStuExist("newStu");
			
			//log.debug(stuRegForm.getStudent().getSurname() + ":" +  stuRegForm.getStudent().getFirstnames() + ":" + bDay);
			
			/** Flow Check: (3) **/
			/* Edmund Update 2018 - Now flowing to APS*/
			if ("HON".equalsIgnoreCase(stuRegForm.getLoginSelectMain()) || "MD".equalsIgnoreCase(stuRegForm.getLoginSelectMain()) || "SLP".equalsIgnoreCase(stuRegForm.getLoginSelectMain()) || stuRegForm.getAdminStaff().isAdmin()){
				//log.debug("ApplyForStudentNumberAction - applyLoginNew (2a) - New Student - No Student Number - HONS or M&D - Goto ApplyQualification");
				return mapping.findForward("applyQualification");
			}else{
				//log.debug("ApplyForStudentNumberAction - applyLoginNew (2b) - New Student - No Student Number - Undergrad - Goto APS 1 Forward");
				return mapping.findForward("applyAPSSelect");
			}
		} else { 
			
			//Student does have a number already
			//log.debug("ApplyForStudentNumberAction - applyLoginNew - N " + stuRegForm.getStudent().getNumber() + " IN (Else) " + vNumCheck);

			/** Flow Check: (4) **/
			//log.debug("ApplyForStudentNumberAction - applyLoginNew (4) - Student has a number");
			stuRegForm.getStudent().setNumber(vNumCheck);
			stuRegForm.getStudent().setStuExist("curStu");
			
			//Block SLP students if coming in via the formal route, even if they are new students, they must use the SLP route.
			if (stuRegForm.getStudent().getNumber() != null && !"".equals(stuRegForm.getStudent().getNumber().trim())){
				if ("7".equalsIgnoreCase(stuRegForm.getStudent().getNumber().substring(0,1))){
					if (stuRegForm.getStudent().getNumber().length() == 8 ){ 
						//log.debug("ApplyForStudentNumberAction - applyLoginNew - Check if Student number = 8 characters");
						if (!stuRegForm.getStudent().isStuSLP() && "7".equalsIgnoreCase(stuRegForm.getStudent().getNumber().substring(0,1))){
							//log.debug("ApplyForStudentNumberAction - applyLoginNew - Student number = 8 characters and starts with 7 - Set No SLP Message");
							messages.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("message.generalmessage", "You must apply for Qualifications via the SLP route. Please select Short Learning Programmes from the main menu to continue or email <a href='mailto:applications@unisa.ac.za'>applications@unisa.ac.za</a>"));
							addErrors(request, messages);
							setDropdownListsLogin(request,stuRegForm);
							return mapping.findForward("applyLoginSelect");
						}else if (stuRegForm.getStudent().isStuSLP() && "7".equalsIgnoreCase(stuRegForm.getStudent().getNumber().substring(0,1))){
							//Student has come in via SLP link and is a 7 series student
							//log.debug("ApplyForStudentNumberAction - applyLoginNew - Student number = 8 characters and starts with 7 - Allow SLP");
						}
					}
				}
				//log.debug("ApplyForStudentNumberAction - applyLoginNew - After check if Student number = 8 characters and starts with 7 ");
			}
			/** Flow Check: (5) **/
			//log.debug("ApplyForStudentNumberAction - applyLoginNew (5) - Check if Student number is 7 chars");
			// If student no is only 7 characters (old), then add a 0 to the beginning
			if (stuRegForm.getStudent().getNumber().length() == 7 ){
				//log.debug("ApplyForStudentNumberAction - applyLoginNew - 7 char Student Number - Old Number: " + stuRegForm.getStudent().getNumber());
				stuRegForm.getStudent().setNumber("0" + stuRegForm.getStudent().getNumber());
				//log.debug("ApplyForStudentNumberAction - applyLoginNew - 7 char Student Number - New Number: " + stuRegForm.getStudent().getNumber());
			}
			//log.debug("ApplyForStudentNumberAction - applyLoginNew - After check if Student number = 8 characters");
			stuRegForm.getStudent().setNumberCheck(stuRegForm.getStudent().getNumber());
			
			//Check if Student Log Exists, if so, set Application Sequence Number
			int currLogSeq = 0;
        	currLogSeq = dao.getSTULOGRef(stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getStudent().getNumber());
        	//log.debug("ApplyForStudentNumberAction - applyLoginNew - currLogSeq="+currLogSeq+", studentSeqNr="+stuRegForm.getApplySEQUENCE());
        	if (currLogSeq != stuRegForm.getApplySEQUENCE() || currLogSeq == 0 || stuRegForm.getApplySEQUENCE() == 0){
        		currLogSeq++;
        	}
        	stuRegForm.setApplySEQUENCE(currLogSeq);
        	//log.debug("ApplyForStudentNumberAction - applyLoginNew - STULOG Sequence Number to Use="+stuRegForm.getApplySEQUENCE());

			//Get Browser Details
			//Is client behind something like a Proxy Server?
			String ipAddress = request.getHeader("X-FORWARDED-FOR");
			if (ipAddress == null) {
				   ipAddress = request.getRemoteAddr();
			}
			stuRegForm.getStudent().setStuIPAddress(ipAddress);
			stuRegForm.getStudent().setStuBrowserAgent(request.getHeader("User-Agent"));
			String browser = getBrowserInfo(stuRegForm.getStudent().getStuBrowserAgent());
			if (browser != null && !"".equals(browser.trim()) && browser.contains("@") && browser.length() > 3){
				int pos = browser.indexOf("@");
				stuRegForm.getStudent().setStuBrowser(browser.substring(0,pos));
				stuRegForm.getStudent().setStuWksOS(browser.substring(pos+1,browser.length()));
				//log.debug("ApplyForStudentNumberAction - applyLoginNew - Write Browser details to STUXML");
				//Write Browser details to STUXML for later verification - Testing Thread security
				String browserData = stuRegForm.getStudent().getNumber()+","+stuRegForm.getStudent().getStuIPAddress()+","+stuRegForm.getStudent().getStuBrowser()+","+stuRegForm.getStudent().getStuWksOS();
				//log.debug("ApplyForStudentNumberAction - applyLoginNew - browserData="+browserData);
				boolean browserError = false;
				String queryResultBrowser = "";
				String checkBrowser = "";
				int nextBrowser = 1;
				queryResultBrowser = dao.getSTUXMLRef(stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), "WKSInfo", "1", "applyLoginNew");
				//log.debug("ApplyForStudentNumberAction - applyLoginNew - queryResultRef="+queryResultBrowser);
				if (queryResultBrowser.toUpperCase().contains("ERROR")){
					browserError = true;
				}
				if (!browserError){
					try{
						nextBrowser = Integer.parseInt(queryResultBrowser);
						nextBrowser++;
					}catch(Exception e){
						log.warn("ApplyForStudentNumberAction - applyLoginNew - nextRef not Numeric - nextRef="+nextBrowser);
					}
					//log.debug("ApplyForStudentNumberAction - applyLoginNew - queryResultBrowser="+queryResultBrowser+", nextRef="+nextBrowser);
					checkBrowser = dao.saveSTUXML(stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), "WKSInfo", "1", nextBrowser, browserData, "applyLoginNew", "INSERT");
					if (checkBrowser.toUpperCase().contains("ERROR")){
						browserError = true;
					}
				}
				//log.debug("ApplyForStudentNumberAction - applyLoginNew - Write student details to STUXML - Done - checkStu="+checkBrowser+", browserData="+browserData+", referenceSequence="+nextBrowser);
			}
			
			/** Flow Check: (6) **/
			//log.debug("ApplyForStudentNumberAction - applyLoginNew (6) - Check if Student has an Academic record");
			boolean vAcaCheck = dao.validateStudentRec(stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear());
			//log.debug("ApplyForStudentNumberAction - applyLoginNew - vAcaCheck: " + vAcaCheck);
			
			if (vAcaCheck){
				//log.debug("ApplyForStudentNumberAction - applyLoginNew - N " + stuRegForm.getStudent().getNumber() + " vACA " + vAcaCheck);

				/** Flow Check: (7) **/
				//log.debug("ApplyForStudentNumberAction - applyLoginNew (7) - Existing Student with Academic Year Record. Set Message");
				//Existing Student with Academic Year Record. Return to main page and either go to Existing Student or Quit application
				stuRegForm.setWebLoginMsg("Student exists with Academic Year or Annual Record");
				stuRegForm.setWebLoginMsg2("Should be Returning Student, Not New!");
				//Johanet - 20180905 - check SPL different message return student number found - search for a forgotten Student number - do not work for SPL
				if (stuRegForm.getLoginSelectMain().equalsIgnoreCase("SLP")) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Student exists - Should be Returning Student, Not New. Student with surname, first names and birthday already exists with number " + stuRegForm.getStudent().getNumber()));
				}else {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Student exists - Should be Returning Student, Not New. Please re-select your login as a student with a student number. Use the link provided to search for a forgotten Student number."));
				}				
				addErrors(request, messages);
				/** Flow Check: (8) **/
				//log.debug("ApplyForStudentNumberAction - applyLoginNew (8) - Existing Student with Academic Year or Annual Record. Return to main page");
				if (stuRegForm.getAdminStaff().isAdmin()){
					return mapping.findForward("loginStaff");
				}else{
					return mapping.findForward("loginStu");
				}
			}else{
				//log.debug("ApplyForStudentNumberAction - applyLoginNew - N " + stuRegForm.getStudent().getNumber() + " vACA (Else) " + vAcaCheck);
				/** Flow Check: (10) **/
				//log.debug("ApplyForStudentNumberAction - applyLoginNew (10) - Existing Student with No Academic Year Record");
				//Existing Student with no Academic Year Record either go to Existing Student or block if 'AP'
				stuRegForm.getStudent().setStuExist("newStu");
				//log.debug("ApplyForStudentNumberAction - applyLoginNew - StuExist: " + vNumCheck + " vAcaCheck: " + vAcaCheck);

				/** Flow Check: (11) **/
				//log.debug("ApplyForStudentNumberAction - applyLoginNew (11) - Check STUAPQ Record");
				boolean vSTUAPQCheck = dao.validateSTUAPQ(stuRegForm.getStudent().getSurname(), stuRegForm.getStudent().getFirstnames(), bDay, stuRegForm.getStudent().getAcademicYear(),stuRegForm.getLoginSelectMain());

					if (vSTUAPQCheck){ 
						/** Flow Check: (12) **/
						//log.debug("ApplyForStudentNumberAction - applyLoginNew (12) - Student has a STUAPQ Record - Student already applied during this application period");
						//Student already applied during this application period (Only allowed one application per year)
						
						/** Flow Check: (13) **/
						//log.debug("ApplyForStudentNumberAction - applyLoginNew (13) - Check if Doc selected from main page");
						if ("DOC".equalsIgnoreCase(stuRegForm.getLoginSelectMain())){
							/** Flow Check: (14) **/
							//log.debug("ApplyForStudentNumberAction - applyLoginNew (14) - Doc was selected from main page");
							//Student Selected to upload Documents from Main Selection screen - goto Upload
							/** Flow Check: (15) **/
							//log.debug("ApplyForStudentNumberAction - applyLoginNew (15) - Goto upload page");
							return mapping.findForward("dynamicUpload");
						}else if ("STATUS".equalsIgnoreCase(stuRegForm.getLoginSelectMain())){
							/** Flow Check: (xx) **/
							//log.debug("ApplyForStudentNumberAction - applyLoginNew (xx) - Status was selected from main page");
							//Student Selected to Status from Main Selection screen - goto Status
							/** Flow Check: (xx) **/
							//log.debug("ApplyForStudentNumberAction - applyLoginNew (xx) - Goto Status page");
							applyStatus(request, stuRegForm);
							return mapping.findForward("applyStatus");
						}else if ("APPEAL".equalsIgnoreCase(stuRegForm.getLoginSelectMain())){
							/** Flow Check: (xx) **/
							//log.debug("ApplyForStudentNumberAction - applyLoginNew (xx) - Appeal was selected from main page");
							//Student Selected the Appeal Process from Main Selection screen - goto Appeal
							/** Flow Check: (xx) **/
							//log.debug("ApplyForStudentNumberAction - applyLoginNew (xx) - Goto Appeal page");
							applyStatus(request, stuRegForm);
							setDropdownListsAppeal(request,stuRegForm);
							return mapping.findForward("appealSelect");
						}else if ("OFFER".equalsIgnoreCase(stuRegForm.getLoginSelectMain())){
							/** Flow Check: (xx) **/
							//log.debug("ApplyForStudentNumberAction - applyLoginNew (xx) - Offer was selected from main page");
							//Student Selected the Appeal Process from Main Selection screen - goto Appeal
							/** Flow Check: (xx) **/
							//log.debug("ApplyForStudentNumberAction - applyLoginNew (xx) - Goto Offer page");
							applyOfferStatus(request, stuRegForm);
							return mapping.findForward("applyOffer");
						}else{ //To Check this flow
							/** Flow Check: (16) **/
							//log.debug("ApplyForStudentNumberAction - applyLoginNew (16) - Doc, Pay, Status, Appeal and Offer was Not selected from main page");
							//Student Did not Select to upload Documents from Main Selection screen - Show Msg, then goto Upload
							/** Flow Check: (17) **/
							
							if (stuRegForm.getStudent().isStuSLP()){
								/**Check if SLP Student and if Student already has a STUAPQ Record**/
								/**SLP Students with only one STUAPQ may apply for a second, while those with two may only upload, pay etc**/
								//log.debug("ApplyForStudentNumberAction - applyLoginNew - SLP Student="+stuRegForm.getStudent().isStuSLP());
								int slpCount = 0;
								slpCount = dao.validateSLPSTUAPQ(stuRegForm.getStudent().getSurname(), stuRegForm.getStudent().getFirstnames(), bDay,stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod());
								//log.debug("ApplyForStudentNumberAction - applyLoginNew - slpCount="+slpCount);
								if (slpCount > 0){
									stuRegForm.getStudent().setStuapq(true);
								}
								//log.debug("ApplyForStudentNumberAction - applyLoginNew - SLP STUAPQ="+stuRegForm.getStudent().isStuapq());
								if (slpCount == 1){
									//log.debug("ApplyForStudentNumberAction - applyLoginNew (17) - SLP Student with Choice 1 record - Go to Choice for Qual 2");
									return mapping.findForward("applyLoginSLP");
								}
								if (slpCount > 1){
									//log.debug("ApplyForStudentNumberAction - applyLoginNew (17) - SLP Student with 2 records - Go to Upload");
									stuRegForm.setWebUploadMsg("You have already applied for admission to study at Unisa for the " + stuRegForm.getStudent().getAcademicYear() + " academic year.newLine You may only upload documents or make payments from the main menu.newLine  Send an email to the Applications office if this information is incorrect or if you now intend applying for formal studies at ucl@unisa.ac.za");
								}
							}else{
								//log.debug("ApplyForStudentNumberAction - applyLoginNew (17) - Set Doc message");
								stuRegForm.setWebUploadMsg("You have already applied for admission to study at Unisa for the " + stuRegForm.getStudent().getAcademicYear() + " academic year.newLine You may only upload documents or make payments from the main menu.newLine Send an email to the Applications office if this information is incorrect or if you now intend applying for formal studies at applications@unisa.ac.za");
							}
							//log.debug("ApplyForStudentNumberAction - applyLoginNew (17) - Get Doc message - " + stuRegForm.getWebUploadMsg());
							/** Flow Check: (18) **/
							//log.debug("ApplyForStudentNumberAction - applyLoginNew (18) - Goto upload page");
							return mapping.findForward("dynamicUpload");
						}
					}else{
						/** Flow Check: (19) **/
						//log.debug("ApplyForStudentNumberAction - applyLoginNew (19) - Student has Student Number, but no STUACA and no STUAPQ");
						if ("STATUS".equalsIgnoreCase(stuRegForm.getLoginSelectMain()) || "APPEAL".equalsIgnoreCase(stuRegForm.getLoginSelectMain()) || "OFFER".equalsIgnoreCase(stuRegForm.getLoginSelectMain())){
							stuRegForm.setWebLoginMsg("Student not found!");
							stuRegForm.setWebLoginMsg2("Please use the main options to apply for a qualification change before you can use this function. newline Click OK retry or click Cancel if you wish to quit the application process.");
							messages.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("message.generalmessage", "No application record was found for you for this academic year. Please use the main options to apply for a qualification change before you can use this function."));
							addErrors(request, messages);
							/** Flow Check: (xx) **/
							//log.debug("ApplyForStudentNumberAction - applyLoginNew (xx) - New Student with no STUAPQ Record. Return to main page");
							return mapping.findForward("loginSelect");
						}
						/** Flow Check: (20) **/
						//log.debug("ApplyForStudentNumberAction - applyLoginNew (20) - Set Message - You already have a student number. newline Click OK retry or click Cancel if you wish to quit the application process.");
						if (stuRegForm.getStudent().isStuSLP()){
							messages.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("message.generalmessage", "You already have a student number - Please select the \"Yes\" option to login as a returning student or contact the Application Office at <a href='mailto:ucl@unisa.ac.za'>ucl@unisa.ac.za</a>"));
							addErrors(request, messages);
						}else{
							messages.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("message.generalmessage", "You already have a student number - Please select the \"Yes\" option to login as a returning student or contact the Application Office at <a href='mailto:applications@unisa.ac.za'>applications@unisa.ac.za</a>"));
							addErrors(request, messages);
						}
						/** Flow Check: (21) **/
						//log.debug("ApplyForStudentNumberAction - applyLoginNew (20) - Redirect to Login Select");
						if (stuRegForm.getAdminStaff().isAdmin()){
							return mapping.findForward("loginStaff");
						}else{
							return mapping.findForward("loginStu");
						}
					}
				} 
		}

	}

	public ActionForward applyLoginReturn(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,	HttpServletResponse response)
			throws Exception {

		//log.debug("IN applyLoginReturn");
		ActionMessages messages = new ActionMessages();
		StudentRegistrationForm stuRegForm = (StudentRegistrationForm) form;
		GeneralMethods gen = new GeneralMethods();
		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
		
		//log.debug("ApplyForStudentNumberAction - applyLoginReturn - N " + stuRegForm.getStudent().getNumber() + " S " + stuRegForm.getStudent().getSurname() + " F " + stuRegForm.getStudent().getSurname() + " Y " + stuRegForm.getStudent().getBirthYear() + " M " + stuRegForm.getStudent().getBirthMonth() + " D " + stuRegForm.getStudent().getBirthDay());

		//log.debug("ApplyForStudentNumberAction - applyLoginReturn - AcademicYear=" + stuRegForm.getStudent().getAcademicYear());
		if (stuRegForm.getStudent().getAcademicYear() == null){
			stuRegForm.getStudent().setAcademicYear(getCurrentAcademicYear());
			stuRegForm.getStudent().setAcademicPeriod(getCurrentAcademicPeriod());
		}
		
		stuRegForm.setApplyType("F");
		stuRegForm.setFromPage("stepLoginReturn");
		
		
		stuRegForm.getStudent().setNumber(stripXSS(stuRegForm.getStudent().getNumber(), "Student Number", "LoginRet", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
		if (stuRegForm.getStudent().getNumber() == null || "".equalsIgnoreCase(stuRegForm.getStudent().getNumber())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Enter student number."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuRegForm);
			return mapping.findForward("applyLogin");
		}
		if (!isValidNumber(stuRegForm.getStudent().getNumber())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Enter a valid student number."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuRegForm);
			return mapping.findForward("applyLogin");
		}
		/** Check the code against the flow diagram by the numbers provided **/
		/** Flow Check: (1) **/
		if ("7".equalsIgnoreCase(stuRegForm.getStudent().getNumber().substring(0,1))){
			
			if (stuRegForm.getStudent().getNumber().length() == 8 ){ 
				//log.debug("ApplyForStudentNumberAction - applyLoginReturn - Check if Student number = 8 characters");
				if (!stuRegForm.getStudent().isStuSLP() && "7".equalsIgnoreCase(stuRegForm.getStudent().getNumber().substring(0,1))){
					//log.debug("ApplyForStudentNumberAction - applyLoginReturn - Student number = 8 characters and starts with 7 - Set No SLP Message");
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "You must apply for a formal Unisa student number. The number you have entered is reserved for non-formal studies only. Send an email to the Applications office if this information is incorrect or if you now intend applying for formal studies at <a href='mailto:applications@unisa.ac.za'>applications@unisa.ac.za</a>"));
					addErrors(request, messages);
					setDropdownListsLogin(request,stuRegForm);
					return mapping.findForward("applyLogin");
				}else if (stuRegForm.getStudent().isStuSLP() && "7".equalsIgnoreCase(stuRegForm.getStudent().getNumber().substring(0,1))){
					//Student has come in via SLP link and is a 7 series student
					//log.debug("ApplyForStudentNumberAction - applyLoginReturn - Student number = 8 characters and starts with 7 - Allow SLP");
				}
			}else if (stuRegForm.getStudent().getNumber().length() == 7 ){
				// If student no is only 7 characters (old), then add a 0 to the beginning
				//log.debug("ApplyForStudentNumberAction - applyLoginReturn - 7 char Student Number - Old Number: " + stuRegForm.getStudent().getNumber());
				stuRegForm.getStudent().setNumber("0" + stuRegForm.getStudent().getNumber());
				//log.debug("ApplyForStudentNumberAction - applyLoginReturn - 7 char Student Number - New Number: " + stuRegForm.getStudent().getNumber());
			}
		}
		//log.debug("ApplyForStudentNumberAction - applyLoginReturn - After check if Student number = 8 characters and starts with 7 ");
		//Johanet 20181004 - 2019 BRD SLP test returning number a slp number
		if (stuRegForm.getStudent().isStuSLP()) {
			if (!"7".equalsIgnoreCase(stuRegForm.getStudent().getNumber().substring(0,1))){
				//not a SLP number do not start with a 7
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "You must apply for a SLP student number. The number you have entered is reserved for formal Unisa studies only."));
				addErrors(request, messages);
				setDropdownListsLogin(request,stuRegForm);
				return mapping.findForward("applyLogin");
			}
		}
		
		//Check if Student Log Exists, if so, set Application Sequence Number
		int currLogSeq = 0;
    	currLogSeq = dao.getSTULOGRef(stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getStudent().getNumber());
    	//log.debug("ApplyForStudentNumberAction - applyLoginReturn - currLogSeq="+currLogSeq+", studentSeqNr="+stuRegForm.getApplySEQUENCE());
    	if (currLogSeq != stuRegForm.getApplySEQUENCE() || currLogSeq == 0 || stuRegForm.getApplySEQUENCE() == 0){
    		currLogSeq++;
    	}
    	stuRegForm.setApplySEQUENCE(currLogSeq);
    	//log.debug("ApplyForStudentNumberAction - applyLoginReturn - STULOG Sequence Number to Use="+stuRegForm.getApplySEQUENCE());

		stuRegForm.getStudent().setSurname(stripXSS(stuRegForm.getStudent().getSurname(), "Surname", "LoginRet", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
		if (stuRegForm.getStudent().getSurname() == null || "".equalsIgnoreCase(stuRegForm.getStudent().getSurname())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Enter student surname."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuRegForm);
			return mapping.findForward("applyLogin");
		}
		if (!isNameValid(stuRegForm.getStudent().getSurname())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Enter a valid student surname."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuRegForm);
			return mapping.findForward("applyLogin");
		}

		if (stuRegForm.getStudent().getSurname() == null || "".equalsIgnoreCase(stuRegForm.getStudent().getSurname())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Enter student first names."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuRegForm);
			return mapping.findForward("applyLogin");
		}
		stuRegForm.getStudent().setFirstnames(stripXSS(stuRegForm.getStudent().getFirstnames(), "Firstnames", "LoginRet", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
		if (!isNameValid(stuRegForm.getStudent().getFirstnames())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Enter a valid student first names."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuRegForm);
			return mapping.findForward("applyLogin");
		}
		stuRegForm.getStudent().setBirthYear(stripXSS(stuRegForm.getStudent().getBirthYear(), "BirthYear", "LoginRet", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
		stuRegForm.getStudent().setBirthYear(stuRegForm.getStudent().getBirthYear().trim());
		if (stuRegForm.getStudent().getBirthYear() == null){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Year) is blank - must be numeric format [CCYY]."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuRegForm);
			return mapping.findForward("applyLogin");
		}
		if ("".equals(stuRegForm.getStudent().getBirthYear().trim())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Year) is empty - must be numeric format [CCYY]."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuRegForm);
			return mapping.findForward("applyLogin");
		}
		if (!gen.isNumeric(stuRegForm.getStudent().getBirthYear())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Year) must be numeric format [CCYY]."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuRegForm);
			return mapping.findForward("applyLogin");
		}
		if (Integer.parseInt(stuRegForm.getStudent().getBirthYear()) <= 1900 || Integer.parseInt(stuRegForm.getStudent().getBirthYear()) >= Integer.parseInt(stuRegForm.getStudent().getAcademicYear())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Year) invalid."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuRegForm);
			return mapping.findForward("applyLogin");
		}
		if (stuRegForm.getStudent().getBirthYear().length() > 4){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Year) invalid. Please enter 4 characters or less. " ));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuRegForm);
			return mapping.findForward("applyLogin");
		}
		stuRegForm.getStudent().setBirthMonth(stripXSS(stuRegForm.getStudent().getBirthMonth(), "BirthMonth", "LoginRet", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
		stuRegForm.getStudent().setBirthMonth(stuRegForm.getStudent().getBirthMonth().trim());
		if (stuRegForm.getStudent().getBirthMonth() == null){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Month) is blank - must be numeric format [MM]."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuRegForm);
			return mapping.findForward("applyLogin");
		}
		if ("".equals(stuRegForm.getStudent().getBirthMonth())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Month) is empty must be numeric format [MM]."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuRegForm);
			return mapping.findForward("applyLogin");
		}
		if (!gen.isNumeric(stuRegForm.getStudent().getBirthMonth())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Month) must be numeric format [MM]."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuRegForm);
			return mapping.findForward("applyLogin");
		}
		if (Integer.parseInt(stuRegForm.getStudent().getBirthMonth()) < 1 || Integer.parseInt(stuRegForm.getStudent().getBirthMonth()) > 12){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Month) invalid."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuRegForm);
			return mapping.findForward("applyLogin");
		}
		if (stuRegForm.getStudent().getBirthMonth().length() > 2){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Month) invalid. Please enter 2 characters or less. " ));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuRegForm);
			return mapping.findForward("applyLogin");
		}

		//2015 Edmund - Expanded Date Tests as Students entered dates like 30/02/1992 etc
		int testMonth = Integer.parseInt(stuRegForm.getStudent().getBirthMonth());
		int daysMonth = 0;
		if (testMonth == 1 || testMonth == 3 || testMonth == 5 || testMonth == 7 || testMonth == 8 || testMonth == 10 || testMonth ==12){
			daysMonth = 31;
		}else if (testMonth == 4 || testMonth == 6 || testMonth == 9 || testMonth == 11){
			daysMonth = 30;
		}else{
			daysMonth = 28; //Checking for Leap year further below...
		}
		
		stuRegForm.getStudent().setBirthDay(stripXSS(stuRegForm.getStudent().getBirthDay(), "BirthDay", "LoginRet", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
		stuRegForm.getStudent().setBirthDay(stuRegForm.getStudent().getBirthDay().trim());
		if (stuRegForm.getStudent().getBirthDay() == null){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Day) is blank - must be numeric format [DD]."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuRegForm);
			return mapping.findForward("applyLogin");
		}
		if ("".equals(stuRegForm.getStudent().getBirthDay())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Day) is empty - must be numeric format [DD]."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuRegForm);
			return mapping.findForward("applyLogin");
		}
		if (!gen.isNumeric(stuRegForm.getStudent().getBirthDay())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Day) must be numeric format [DD]."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuRegForm);
			return mapping.findForward("applyLogin");
		}
		if (Integer.parseInt(stuRegForm.getStudent().getBirthDay()) < 1 || Integer.parseInt(stuRegForm.getStudent().getBirthDay()) > 31){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Day) invalid."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuRegForm);
			return mapping.findForward("applyLogin");
		}
		if (stuRegForm.getStudent().getBirthDay().length() > 2){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Day) invalid. Please enter 2 characters or less. " ));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuRegForm);
			return mapping.findForward("applyLogin");
		}
		if (Integer.parseInt(stuRegForm.getStudent().getBirthDay()) < 1){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Day) invalid."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuRegForm);
			return mapping.findForward("applyLogin");
		}
		String monthName = "";
		if (daysMonth == 31 && Integer.parseInt(stuRegForm.getStudent().getBirthDay()) > 31){
			monthName = getMonth(testMonth);
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Day) invalid. " + monthName + " only has 31 days."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuRegForm);
			return mapping.findForward("applyLogin");
		}
		if (daysMonth == 30 && Integer.parseInt(stuRegForm.getStudent().getBirthDay()) > 30){
			monthName = getMonth(testMonth);
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (Day) invalid. " + monthName + " only has 30 days."));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuRegForm);
			return mapping.findForward("applyLogin");
		}
		if (Integer.parseInt(stuRegForm.getStudent().getBirthMonth()) == 2 && Integer.parseInt(stuRegForm.getStudent().getBirthDay()) > 29){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", "Student Date of Birth invalid. " ));
			addErrors(request, messages);
			setDropdownListsLogin(request,stuRegForm);
			return mapping.findForward("applyLogin");
		}
		if (Integer.parseInt(stuRegForm.getStudent().getBirthMonth()) == 2 && Integer.parseInt(stuRegForm.getStudent().getBirthDay()) == 29){
			String leapCheck = dao.checkLeapYear(stuRegForm.getStudent().getBirthYear());
			if ("Not a leap year".equalsIgnoreCase(leapCheck)){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student Date of Birth (29/February/"+stuRegForm.getStudent().getBirthYear()+") invalid. " + stuRegForm.getStudent().getBirthYear() + " was not a leap year." ));
				addErrors(request, messages);
				setDropdownListsLogin(request,stuRegForm);
				return mapping.findForward("applyLogin");
			}
		}
		
		//Add "0" incase Student enters just one character for Birthday or Month
		if (stuRegForm.getStudent().getBirthMonth().length() < 2){
			stuRegForm.getStudent().setBirthMonth("0" + stuRegForm.getStudent().getBirthMonth());
		}
		if (stuRegForm.getStudent().getBirthDay().length() < 2){
			stuRegForm.getStudent().setBirthDay("0" + stuRegForm.getStudent().getBirthDay());
		}
		
		String errorMsg="";
		errorMsg = displayPersonal(stuRegForm, request);

		if(!"".equals(errorMsg)){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", errorMsg));
				addErrors(request, messages);
				setDropdownListsLogin(request,stuRegForm);
				return mapping.findForward("applyLogin");
		}else{
			stuRegForm.getStudent().setNumber(stuRegForm.getStudent().getNumber());
			stuRegForm.getStudent().setSurname(stuRegForm.getStudent().getSurname().toUpperCase());
			stuRegForm.getStudent().setFirstnames(stuRegForm.getStudent().getFirstnames().toUpperCase());
			stuRegForm.getStudent().setBirthYear(stuRegForm.getStudent().getBirthYear());
			stuRegForm.getStudent().setBirthMonth(stuRegForm.getStudent().getBirthMonth());
			stuRegForm.getStudent().setBirthDay(stuRegForm.getStudent().getBirthDay());
			
			//log.debug("ApplyForStudentNumberAction - applyLoginReturn - Write student details to STUXML");
			//Write student details to STUXML for later verification - Testing Thread security
			String referenceData = stuRegForm.getStudent().getNumber()+","+stuRegForm.getStudent().getSurname().toUpperCase()+","+stuRegForm.getStudent().getFirstnames().toUpperCase()+","+stuRegForm.getStudent().getBirthYear()+stuRegForm.getStudent().getBirthMonth()+stuRegForm.getStudent().getBirthDay();
			boolean stuError = false;
			String queryResultRef = "";
			String checkStu = "";
			int nextRef = 1;
			queryResultRef = dao.getSTUXMLRef(stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), "StuInfo", "1", "applyLoginReturn");
			//log.debug("ApplyForStudentNumberAction - applyLoginReturn - queryResultRef="+queryResultRef);
			if (queryResultRef.toUpperCase().contains("ERROR")){
				stuError = true;
			}
			if (!stuError){
				try{
					nextRef = Integer.parseInt(queryResultRef);
					nextRef++;
				}catch(Exception e){
					log.warn("ApplyForStudentNumberAction - applyLoginReturn - nextRef not Numeric - nextRef="+nextRef);
				}
				//log.debug("ApplyForStudentNumberAction - applyLoginReturn - queryResultRef="+queryResultRef+", nextRef="+nextRef);
				checkStu = dao.saveSTUXML(stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), "StuInfo", "1", nextRef, referenceData, "applyLoginReturn", "INSERT");
				if (checkStu.toUpperCase().contains("ERROR")){
					stuError = true;
				}
			}
			//log.debug("ApplyForStudentNumberAction - applyLoginReturn - Write student details to STUXML - Done - checkStu="+checkStu+", referenceData="+referenceData+", referenceSequence="+nextRef);
			
			//Get Browser Details
			//Is client behind something like a Proxy Server?
			String ipAddress = request.getHeader("X-FORWARDED-FOR");
			if (ipAddress == null) {
				   ipAddress = request.getRemoteAddr();
			}
			stuRegForm.getStudent().setStuIPAddress(ipAddress);
			stuRegForm.getStudent().setStuBrowserAgent(request.getHeader("User-Agent"));
			String browser = getBrowserInfo(stuRegForm.getStudent().getStuBrowserAgent());
			if (browser != null && !"".equals(browser.trim()) && browser.contains("@") && browser.length() > 3){
				int pos = browser.indexOf("@");
				stuRegForm.getStudent().setStuBrowser(browser.substring(0,pos));
				stuRegForm.getStudent().setStuWksOS(browser.substring(pos+1,browser.length()));
				//log.debug("ApplyForStudentNumberAction - applyLoginReturn - Write Browser details to STUXML");
				//Write Browser details to STUXML for later verification - Testing Thread security
				String browserData = stuRegForm.getStudent().getNumber()+","+stuRegForm.getStudent().getStuIPAddress()+","+stuRegForm.getStudent().getStuBrowser()+","+stuRegForm.getStudent().getStuWksOS();
				//log.debug("ApplyForStudentNumberAction - applyLoginReturn - browserData="+browserData);
				boolean browserError = false;
				String queryResultBrowser = "";
				String checkBrowser = "";
				int nextBrowser = 1;
				queryResultBrowser = dao.getSTUXMLRef(stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), "WKSInfo", "1", "applyLoginReturn");
				//log.debug("ApplyForStudentNumberAction - applyLoginReturn - queryResultRef="+queryResultBrowser);
				if (queryResultBrowser.toUpperCase().contains("ERROR")){
					browserError = true;
				}
				if (!browserError){
					try{
						nextBrowser = Integer.parseInt(queryResultBrowser);
						nextBrowser++;
					}catch(Exception e){
						log.warn("ApplyForStudentNumberAction - applyLoginReturn - nextRef not Numeric - nextRef="+nextBrowser);
					}
					//log.debug("ApplyForStudentNumberAction - applyLoginReturn - queryResultBrowser="+queryResultBrowser+", nextRef="+nextBrowser);
					checkBrowser = dao.saveSTUXML(stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), "WKSInfo", "1", nextBrowser, browserData, "applyLoginReturn", "INSERT");
					if (checkBrowser.toUpperCase().contains("ERROR")){
						browserError = true;
					}
				}
				//log.debug("ApplyForStudentNumberAction - applyLoginReturn - Write student details to STUXML - Done - checkStu="+checkBrowser+", browserData="+browserData+", referenceSequence="+nextBrowser);
			}
			
			/**
			 * Validation Test: Check if student has a academic record
			 **/
			
			/** Continue to check the code against the flow diagram by the numbers provided **/
			/** Flow Check: (2) **/
			//log.debug("ApplyForStudentNumberAction - applyLoginReturn (2) - Check if Student has Academic record");
			boolean vAcaCheck = dao.validateStudentRec(stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear());
			//log.debug("ApplyForStudentNumberAction - applyLoginReturn - vAcaCheck: " + vAcaCheck);
			if (vAcaCheck){
				/** Flow Check: (3) **/
				//log.debug("ApplyForStudentNumberAction - applyLoginReturn (3) - Student has Academic record");
				String bDay = stuRegForm.getStudent().getBirthDay() + "/" + stuRegForm.getStudent().getBirthMonth() + "/" + stuRegForm.getStudent().getBirthYear();
				/** Flow Check: (4) **/
				//log.debug("ApplyForStudentNumberAction - applyLoginReturn (4) - Check STUAPQ (F851)");
				boolean checkSTUAPQ = dao.validateSTUAPQ(stuRegForm.getStudent().getSurname(), stuRegForm.getStudent().getFirstnames(), bDay,stuRegForm.getStudent().getAcademicYear(),stuRegForm.getLoginSelectMain());
				stuRegForm.getStudent().setStuapqExist(checkSTUAPQ);
				
				//log.debug("ApplyForStudentNumberAction - applyLoginReturn - (IF) vAcaCheck=True - checkSTUAPQ: " + checkSTUAPQ + " - setStuExist:curStu - goto - MenuReturnStu");
				if (checkSTUAPQ){ //Student already applied during this application period (Only allowed one application per year)
					/** Flow Check: (5) **/
					//log.debug("ApplyForStudentNumberAction - applyLoginReturn (5) - Student already applied during this application period");
					//Check if Doc upload was selected from the Main selection page
					/** Flow Check: (6) **/
					//log.debug("ApplyForStudentNumberAction - applyLoginReturn (6) - doc flow check");
					if ("DOC".equalsIgnoreCase(stuRegForm.getLoginSelectMain())){
						/** Flow Check: (7) **/
						//log.debug("ApplyForStudentNumberAction - applyLoginReturn (7) - Student selected to go directly to upload page");
						stuRegForm.getStudent().setStuExist("curStu");
						/** Flow Check: (8) **/
						//log.debug("ApplyForStudentNumberAction - applyLoginReturn (8) - Redirect to upload page");
						return mapping.findForward("dynamicUpload");
					}else if ("STATUS".equalsIgnoreCase(stuRegForm.getLoginSelectMain())){
						/** Flow Check: (xx) **/
						//log.debug("ApplyForStudentNumberAction - applyLoginReturn (xx) - Student selected to go directly to Status page");
						stuRegForm.getStudent().setStuExist("curStu");
						/** Flow Check: (xx) **/
						//log.debug("ApplyForStudentNumberAction - applyLoginReturn (xx) - Redirect to Status page");
						applyStatus(request, stuRegForm);
						return mapping.findForward("applyStatus");
					}else if ("APPEAL".equalsIgnoreCase(stuRegForm.getLoginSelectMain())){
						/** Flow Check: (xx) **/
						//log.debug("ApplyForStudentNumberAction - applyLoginReturn (xx) - Student selected to go directly to Appeal page");
						stuRegForm.getStudent().setStuExist("curStu");
						/** Flow Check: (xx) **/
						//log.debug("ApplyForStudentNumberAction - applyLoginReturn (xx) - Redirect to Appeal page");
						applyStatus(request, stuRegForm);
						setDropdownListsAppeal(request,stuRegForm);
						return mapping.findForward("appealSelect");
					}else if ("OFFER".equalsIgnoreCase(stuRegForm.getLoginSelectMain())){
						/** Flow Check: (xx) **/
						//log.debug("ApplyForStudentNumberAction - applyLoginReturn (xx) - Student selected to go directly to Offer page");
						stuRegForm.getStudent().setStuExist("curStu");
						/** Flow Check: (xx) **/
						//log.debug("ApplyForStudentNumberAction - applyLoginReturn (xx) - Redirect to Offer page");
						applyOfferStatus(request, stuRegForm);
						return mapping.findForward("applyOffer");	
					}else{
						/** Flow Check: (9) **/
						//log.debug("ApplyForStudentNumberAction - applyLoginReturn (9) - Student did not select to go directly to upload page but already has STUAPQ");
						//Student did not select to go directly to upload page but already has STUAPQ
						if (stuRegForm.getStudent().isStuSLP()){
							/**Check if SLP Student and if Student already has a STUAPQ Record**/
							/**SLP Students with only one STUAPQ may apply for a second, while those with two may only upload, pay etc**/
							//log.debug("ApplyForStudentNumberAction - applyLoginReturn - SLP Student="+stuRegForm.getStudent().isStuSLP());
							int slpCount = 0;
							slpCount = dao.validateSLPSTUAPQ(stuRegForm.getStudent().getSurname(), stuRegForm.getStudent().getFirstnames(), bDay,stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod());
							//log.debug("ApplyForStudentNumberAction - applyLoginReturn - slpCount="+slpCount);
							if (slpCount > 0){
								stuRegForm.getStudent().setStuapq(true);
							}
							//log.debug("ApplyForStudentNumberAction - applyLoginReturn - SLP STUAPQ="+stuRegForm.getStudent().isStuapq());
							if (slpCount == 1){
								//log.debug("ApplyForStudentNumberAction - applyLoginReturn (9) - SLP Student with Choice 1 record - Go to Choice for Qual 2");
								return mapping.findForward("applyLoginSLP");
							}
							if (slpCount > 1){
								//log.debug("ApplyForStudentNumberAction - applyLoginReturn (9) - SLP Student with 2 records - Go to Upload");
								stuRegForm.setWebUploadMsg("You have already applied for admission to study at Unisa for the " + stuRegForm.getStudent().getAcademicYear() + " academic year.newLine You may only upload documents or make payments from the main menu.newLine  Send an email to the Applications office if this information is incorrect or if you now intend applying for formal studies at ucl@unisa.ac.za");
								return mapping.findForward("dynamicUpload");
							}
						}else{
							//log.debug("ApplyForStudentNumberAction - applyLoginReturn - Student already has a STUAPQ Record for this Academic Year - Thus block");
							/** Flow Check: (10) **/
							//log.debug("ApplyForStudentNumberAction - applyLoginReturn (10) - Set Doc message");
							stuRegForm.setWebUploadMsg("You have already applied for a qualification change for the " + stuRegForm.getStudent().getAcademicYear() + " academic year.newLine Please select to upload documents or make payments or alternatively send an email to the Applications office at applications@unisa.ac.za if this information is incorrect ");
							//log.debug("ApplyForStudentNumberAction - applyLoginReturn (10) - Get Doc message - " + stuRegForm.getWebUploadMsg());
							stuRegForm.getStudent().setStuExist("curStu");
							//log.debug("ApplyForStudentNumberAction - applyLoginReturn - checkSTUAPQ - Return to dynamicUpload");
							/** Flow Check: (11) **/
							//log.debug("ApplyForStudentNumberAction - applyLoginReturn (11) - Go to upload page");
							return mapping.findForward("dynamicUpload");
						}
						//log.debug("ApplyForStudentNumberAction - applyLoginReturn (11) - Fallback - Go to upload page");
						return mapping.findForward("dynamicUpload");
					}
				} else{ //Student thus doesn't have a STUAPQ record for this Academic Year & Period yet
					/** Flow Check: (12) **/
					//log.debug("ApplyForStudentNumberAction - applyLoginReturn (12) - Student thus doesn't have a STUAPQ record for this Academic Year & Period yet");
					/** Flow Check: (13) **/
					//log.debug("ApplyForStudentNumberAction - applyLoginReturn (13) - Check if Doc was selected from main page");
					stuRegForm.getStudent().setStuExist("curStu");
					if ("DOC".equalsIgnoreCase(stuRegForm.getLoginSelectMain())){
						/** Flow Check: (14) **/
						//log.debug("ApplyForStudentNumberAction - applyLoginReturn (14) - Doc was selected from main page");
						//Student Selected to upload Documents from Main Selection screen but doesn't have a STUAPQ record, thus display message and goto applyQualification
						/** Flow Check: (15) **/					
						if ("UD".equalsIgnoreCase(stuRegForm.getLoginSelectMain()) && !stuRegForm.getAdminStaff().isAdmin()){
							//log.debug("ApplyForStudentNumberAction - applyLoginReturn (15) - Set Message - Your do not have a current application record for this academic year.");
							stuRegForm.setWebUploadMsg("You do not have a current application record for this academic year.newLine You must apply for a new qualification or change your existing qualification in order to use this function to upload documents.");
							/** Flow Check: (16a) **/
							//log.debug("ApplyForStudentNumberAction - applyLoginReturn (16a) - Returning Student - Undergrad - Goto LoginSelect Forward");
							return mapping.findForward("loginSelect");
						}else{
							/** Flow Check: (16c) **/
							//Check if M&D Student if so, redirect to M&D Admissions
							boolean isMDStudent = false;
							isMDStudent = dao.getMDAPPLRecord(stuRegForm.getStudent().getNumber());
							//log.debug("ApplyForStudentNumberAction - applyLoginReturn (16c) - Returning Student - M&D Check="+isMDStudent);
							if (isMDStudent){
								/** Flow Check: (16d) **/
								//log.debug("ApplyForStudentNumberAction - applyLoginReturn (16d) - Returning Student - M&D - Goto MD Admissions");
								String serverpath = ServerConfigurationService.getServerUrl();
								return new ActionForward(serverpath+"/unisa-findtool/default.do?sharedTool=unisa.mdapplications",true);
							}else{
								/** Flow Check: (16e) **/
								if (stuRegForm.getAdminStaff().isAdmin()){
									//log.debug("ApplyForStudentNumberAction - applyLoginReturn (16e) - Returning Student Admin - Goto ApplyQualification");
									return mapping.findForward("applyQualification");
								}else{
									if (stuRegForm.getStudent().isDateWAPRU()){
										//log.debug("ApplyForStudentNumberAction - applyLoginReturn (16f) - Returning Student - Goto ApplyQualification");
										return mapping.findForward("applyQualification");
									}else{
										stuRegForm.setAllowLogin(false);
										return mapping.findForward("applyLogin");
									}
								}
							}
						}
					}else if ("STATUS".equalsIgnoreCase(stuRegForm.getLoginSelectMain())){
						/** Flow Check: (xx) **/
						//log.debug("ApplyForStudentNumberAction - applyLoginReturn (xx) - Status was selected from main page");
						//Student Selected to review Application Status from Main Selection screen but doesn't have a STUAPQ record, thus display message and goto applyQualification
						/** Flow Check: (xx) **/
						//log.debug("ApplyForStudentNumberAction - applyLoginReturn (xx) - Set Message - You do not have a current application record for this academic year. newline Click OK retry or click Cancel if you wish to quit the application process.");
						stuRegForm.setWebLoginMsg("You do not have a current application record for this academic year.");
						stuRegForm.setWebLoginMsg2("You must apply for a new qualification or change your existing qualification in order to view your application status.");
						/** Flow Check: (xx) **/
						//log.debug("ApplyForStudentNumberAction - applyLoginReturn (xx) - Goto loginSelect");
						return mapping.findForward("loginSelect");
					}else if ("APPEAL".equalsIgnoreCase(stuRegForm.getLoginSelectMain())){
						/** Flow Check: (xx) **/
						//log.debug("ApplyForStudentNumberAction - applyLoginReturn (xx) - Appeal was selected from main page");
						//Student Selected Application Appeal Process from Main Selection screen but doesn't have a STUAPQ record, thus display message and goto applyQualification
						/** Flow Check: (xx) **/
						//log.debug("ApplyForStudentNumberAction - applyLoginReturn (xx) - Set Message - You do not have a current application record for this academic year. newline Click OK retry or click Cancel if you wish to quit the application process.");
						stuRegForm.setWebLoginMsg("You do not have a current application record for this academic year.");
						stuRegForm.setWebLoginMsg2("You must apply for a new qualification or change your existing qualification in order to appeal an application decision.");
						/** Flow Check: (xx) **/
						//log.debug("ApplyForStudentNumberAction - applyLoginReturn (xx) - Goto loginSelect");
						return mapping.findForward("loginSelect");	
					}else if ("OFFER".equalsIgnoreCase(stuRegForm.getLoginSelectMain())){
						/** Flow Check: (xx) **/
						//log.debug("ApplyForStudentNumberAction - applyLoginReturn (xx) - Offer was selected from main page");
						//Student Selected Application Appeal Process from Main Selection screen but doesn't have a STUAPQ record, thus display message and goto applyQualification
						/** Flow Check: (xx) **/
						//log.debug("ApplyForStudentNumberAction - applyLoginReturn (xx) - Set Message - You do not have a current application record for this academic year. newline Click OK retry or click Cancel if you wish to quit the application process.");
						stuRegForm.setWebLoginMsg("You do not have a current application record for this academic year.");
						stuRegForm.setWebLoginMsg2("You must apply for a new qualification or change your existing qualification in order to accept or decline an enrollment offer.");
						/** Flow Check: (xx) **/
						//log.debug("ApplyForStudentNumberAction - applyLoginReturn (xx) - Goto loginSelect");
						return mapping.findForward("loginSelect");	
					}else{
						/** Flow Check: (14) **/
						//log.debug("ApplyForStudentNumberAction - applyLoginReturn (17) - Doc, Pay, Status, Appeal and Offer was NOT selected from main page");
						/** Flow Check: (18) **/
						stuRegForm.setWebLoginMsg("");
						stuRegForm.setWebLoginMsg2("");
						if (stuRegForm.getAdminStaff().isAdmin()){
							//log.debug("ApplyForStudentNumberAction - applyLoginReturn (18) - Returning Student Admin - HONS - Goto ApplyQualification");
							return mapping.findForward("applyQualification");
						}else{
							if ("UD".equalsIgnoreCase(stuRegForm.getLoginSelectMain())){
								if (stuRegForm.getStudent().isDateWAPRU()){
									//log.debug("ApplyForStudentNumberAction - applyLoginReturn (18a) - Returning Student - Undergrad - Goto APS Select");
									return mapping.findForward("applyAPSSelect");
								}else{
									stuRegForm.setAllowLogin(false);
									messages.add(ActionMessages.GLOBAL_MESSAGE,
										new ActionMessage("message.generalmessage", "Applications for returning/existing students are closed for this semester. You will have to re-apply during the next application period."));
									addErrors(request, messages);
									setDropdownListsLogin(request,stuRegForm);
									return mapping.findForward("applyLogin");
								}
							//Johanet 20180827 - add SLP returning student	
							}else if ("SLP".equalsIgnoreCase(stuRegForm.getLoginSelectMain())){
								if (stuRegForm.getStudent().isDateWAPS ()){
									return mapping.findForward("applyQualification");
								}else{
									stuRegForm.setAllowLogin(false);
									messages.add(ActionMessages.GLOBAL_MESSAGE,
										new ActionMessage("message.generalmessage", "Applications for returning/existing students are closed for this semester. You will have to re-apply during the next application period."));
									addErrors(request, messages);
									setDropdownListsLogin(request,stuRegForm);
									return mapping.findForward("applyLogin");
								}
							}else{
								if (stuRegForm.getStudent().isDateWAPRH()){
									//log.debug("ApplyForStudentNumberAction - applyLoginReturn (18b) - Returning Student - HONS - Goto ApplyQualification");
									return mapping.findForward("applyQualification");
								}else{
									stuRegForm.setAllowLogin(false);
									messages.add(ActionMessages.GLOBAL_MESSAGE,
										new ActionMessage("message.generalmessage", "Applications for returning/existing students are closed for this semester. You will have to re-apply during the next application period."));
									addErrors(request, messages);
									setDropdownListsLogin(request,stuRegForm);
									return mapping.findForward("applyLogin");
								}
							}
						}
					}
				}
				
			}else{ //Student thus doesn't have an Academic record
				/** Flow Check: (19) **/
				//log.debug("ApplyForStudentNumberAction - applyLoginReturn (19) - Student thus doesn't have an Academic record");
				//log.debug("ApplyForStudentNumberAction - applyLoginReturn - (ELSE) vAcaCheck=False - setStuExist:curStu");
				stuRegForm.getStudent().setStuExist("curStu");
				
				String bDay = stuRegForm.getStudent().getBirthDay() + "/" + stuRegForm.getStudent().getBirthMonth() + "/" + stuRegForm.getStudent().getBirthYear();
				/** Flow Check: (20) **/
				//log.debug("ApplyForStudentNumberAction - applyLoginReturn (20) - Check for STUAPQ (F851) record");
				boolean checkSTUAPQ = dao.validateSTUAPQ(stuRegForm.getStudent().getSurname(), stuRegForm.getStudent().getFirstnames(), bDay,stuRegForm.getStudent().getAcademicYear(),stuRegForm.getLoginSelectMain());
				stuRegForm.getStudent().setStuapqExist(checkSTUAPQ);
				
				//log.debug("ApplyForStudentNumberAction - applyLoginReturn - (IF) vAcaCheck=True - checkSTUAPQ: " + checkSTUAPQ + " - setStuExist:curStu - goto - MenuReturnStu");
				if (checkSTUAPQ){ //Student already applied during this application period (Only allowed one application per year)
					/** Flow Check: (21) **/
					//log.debug("ApplyForStudentNumberAction - applyLoginReturn (21) - Student already applied during this application period");
					//Check if Doc upload was selected from the Main selection page
					/** Flow Check: (22) **/
					//log.debug("ApplyForStudentNumberAction - applyLoginReturn (22) - Check if Doc upload was selected from the Main selection page");
					if ("DOC".equalsIgnoreCase(stuRegForm.getLoginSelectMain())){
						/** Flow Check: (23) **/
						//log.debug("ApplyForStudentNumberAction - applyLoginReturn (23) - Student selected to go directly to upload page");
						stuRegForm.getStudent().setStuExist("curStu");
						/** Flow Check: (24) **/
						//log.debug("ApplyForStudentNumberAction - applyLoginReturn (24) - Redirect to upload page");
						return mapping.findForward("dynamicUpload");
					}else if ("STATUS".equalsIgnoreCase(stuRegForm.getLoginSelectMain())){
						/** Flow Check: (xx) **/
						//log.debug("ApplyForStudentNumberAction - applyLoginReturn (23) - Student selected to go to Status page");
						stuRegForm.getStudent().setStuExist("curStu");
						/** Flow Check: (xx) **/
						//log.debug("ApplyForStudentNumberAction - applyLoginReturn (24) - Redirect to Status page");
						applyStatus(request, stuRegForm);
						return mapping.findForward("applyStatus");
					}else if ("APPEAL".equalsIgnoreCase(stuRegForm.getLoginSelectMain())){
						/** Flow Check: (xx) **/
						//log.debug("ApplyForStudentNumberAction - applyLoginReturn (23) - Student selected to go to Appeal page");
						stuRegForm.getStudent().setStuExist("curStu");
						/** Flow Check: (xx) **/
						//log.debug("ApplyForStudentNumberAction - applyLoginReturn (24) - Redirect to Appeal page");
						applyStatus(request, stuRegForm);
						setDropdownListsAppeal(request,stuRegForm);
						return mapping.findForward("appealSelect");
					}else if ("OFFER".equalsIgnoreCase(stuRegForm.getLoginSelectMain())){
						/** Flow Check: (xx) **/
						//log.debug("ApplyForStudentNumberAction - applyLoginReturn (23) - Student selected to go to Offer page");
						stuRegForm.getStudent().setStuExist("curStu");
						/** Flow Check: (xx) **/
						//log.debug("ApplyForStudentNumberAction - applyLoginReturn (24) - Redirect to Offer page");
						applyOfferStatus(request, stuRegForm);
						return mapping.findForward("applyOffer");
					}else{
						/** Flow Check: (25) **/
						//log.debug("ApplyForStudentNumberAction - applyLoginReturn (25) - Student did not select anything to go directly to upload page");
						//Student did not select to go directly to upload page but already has STUAPQ - see below
						//log.debug("ApplyForStudentNumberAction - applyLoginReturn - Student already has a STUAPQ Record for this Academic Year - Thus block");
						/** Flow Check: (26) **/
						//log.debug("ApplyForStudentNumberAction - applyLoginReturn (26) - Set Doc message");
						if (stuRegForm.getStudent().isStuSLP()){
							/**Check if SLP Student and if Student already has a STUAPQ Record**/
							/**SLP Students with only one STUAPQ may apply for a second, while those with two may only upload, pay etc**/
							//log.debug("ApplyForStudentNumberAction - applyLoginReturn - SLP Student="+stuRegForm.getStudent().isStuSLP());
							int slpCount = 0;
							slpCount = dao.validateSLPSTUAPQ(stuRegForm.getStudent().getSurname(), stuRegForm.getStudent().getFirstnames(), bDay,stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod());
							//log.debug("ApplyForStudentNumberAction - applyLoginReturn - slpCount="+slpCount);
							if (slpCount > 0){
								stuRegForm.getStudent().setStuapq(true);
							}
							//log.debug("ApplyForStudentNumberAction - applyLoginReturn - SLP STUAPQ="+stuRegForm.getStudent().isStuapq());
							if (slpCount == 1){
								//log.debug("ApplyForStudentNumberAction - applyLoginReturn (26) - SLP Student with Choice 1 record - Go to Choice for Qual 2");
								return mapping.findForward("applyLoginSLP");
							}
							if (slpCount > 1){
								//log.debug("ApplyForStudentNumberAction - applyLoginReturn (26) - SLP Student with 2 records - Go to Upload");
								stuRegForm.setWebUploadMsg("You have already applied for admission to study at Unisa for the " + stuRegForm.getStudent().getAcademicYear() + " academic year.newLine You may only upload documents or make payments from the main menu.newLine  Send an email to the Applications office if this information is incorrect or if you now intend applying for formal studies at ucl@unisa.ac.za");
								return mapping.findForward("dynamicUpload");
							}
						}else{
								stuRegForm.setWebUploadMsg("You have already applied for a qualification change for the " + stuRegForm.getStudent().getAcademicYear() + " academic year.newLine Please select to upload documents or make payments or alternatively send an email to the Applications office at applications@unisa.ac.za if this information is incorrect ");
								//log.debug("ApplyForStudentNumberAction - applyLoginReturn (27) - Get Doc message - " + stuRegForm.getWebUploadMsg());
								stuRegForm.getStudent().setStuExist("curStu");
								/** Flow Check: (27) **/
								//log.debug("ApplyForStudentNumberAction - applyLoginReturn (27) - Redirect to upload page");
								return mapping.findForward("dynamicUpload");
						}
						//log.debug("ApplyForStudentNumberAction - applyLoginReturn (27) - Fallback - Go to upload page");
						return mapping.findForward("dynamicUpload");
					}
				} else{ //Student thus doesn't have a STUAPQ record for this Academic Year yet & Period
					if ("DOC".equalsIgnoreCase(stuRegForm.getLoginSelectMain())){
						//Check if M&D Student if so, redirect to M&D Admissions
						boolean isMDStudent = false;
						isMDStudent = dao.getMDAPPLRecord(stuRegForm.getStudent().getNumber());
						//log.debug("ApplyForStudentNumberAction - applyLoginReturn (28) - Returning Student No STUACA - NO STUAPQ - M&D Check="+isMDStudent);
						if (isMDStudent){
							/** Flow Check: (29) **/
							//log.debug("ApplyForStudentNumberAction - applyLoginReturn (29) - Returning Student No STUACA - NO STUAPQ - M&D - Goto MD Admissions");
							String serverpath = ServerConfigurationService.getServerUrl();
							return new ActionForward(serverpath+"/unisa-findtool/default.do?sharedTool=unisa.mdapplications",true);
						}
					}else if ("STATUS".equalsIgnoreCase(stuRegForm.getLoginSelectMain())){
						/** Flow Check: (30) **/
						//log.debug("ApplyForStudentNumberAction - applyLoginReturn (30) - Status was selected from main page");
						//Student Selected to review Application Status from Main Selection screen but doesn't have a STUAPQ record, thus display message and goto applyQualification
						/** Flow Check: (30a) **/
						//log.debug("ApplyForStudentNumberAction - applyLoginReturn (30a) - Set Message - You do not have a current application record for this academic year.newline Click OK retry or click Cancel if you wish to quit the application process.");
						stuRegForm.setWebLoginMsg("You do not have a current application record for this academic year.");
						stuRegForm.setWebLoginMsg2("You must apply for a new qualification or change your existing qualification in order to view your application status.");
						/** Flow Check: (30b) **/
						//log.debug("ApplyForStudentNumberAction - applyLoginReturn (30b) - Goto loginSelect");
						return mapping.findForward("loginSelect");
					}else if ("APPEAL".equalsIgnoreCase(stuRegForm.getLoginSelectMain())){
						/** Flow Check: (31) **/
						//log.debug("ApplyForStudentNumberAction - applyLoginReturn (31) - Appeal was selected from main page");
						//Student Selected Application Appeal Process from Main Selection screen but doesn't have a STUAPQ record, thus display message and goto applyQualification
						/** Flow Check: (31a) **/
						//log.debug("ApplyForStudentNumberAction - applyLoginReturn (31a) - Set Message - You do not have a current application record for this academic year.newline Click OK retry or click Cancel if you wish to quit the application process.");
						stuRegForm.setWebLoginMsg("You do not have a current application record for this academic year.");
						stuRegForm.setWebLoginMsg2("You must apply for a new qualification or change your existing qualification in order to appeal an application decision.");
						/** Flow Check: (31b) **/
						//log.debug("ApplyForStudentNumberAction - applyLoginReturn (31b) - Goto loginSelect");
						return mapping.findForward("loginSelect");	
					}else if ("OFFER".equalsIgnoreCase(stuRegForm.getLoginSelectMain())){
						/** Flow Check: (32) **/
						//log.debug("ApplyForStudentNumberAction - applyLoginReturn (32) - Offer was selected from main page");
						//Student Selected Application Appeal Process from Main Selection screen but doesn't have a STUAPQ record, thus display message and goto applyQualification
						/** Flow Check: (32a) **/
						//log.debug("ApplyForStudentNumberAction - applyLoginReturn (32a) - Set Message - You do not have a current application record for this academic year.newline Click OK retry or click Cancel if you wish to quit the application process.");
						stuRegForm.setWebLoginMsg("You do not have a current application record for this academic year.");
						stuRegForm.setWebLoginMsg2("You must apply for a new qualification or change your existing qualification in order to accept or decline an enrollment offer.");
						/** Flow Check: (32b) **/
						//log.debug("ApplyForStudentNumberAction - applyLoginReturn (32b) - Goto loginSelect");
						return mapping.findForward("loginSelect");	
					}
					//2016 Change for Vinesh to allow previously unregistered students to re-apply as returning students.
					/** Flow Check: (28) **/
					stuRegForm.setWebLoginMsg("");
					stuRegForm.setWebLoginMsg2("");
					if (stuRegForm.getAdminStaff().isAdmin()){
						//log.debug("ApplyForStudentNumberAction - applyLoginReturn (28) - Returning Student ADMIN - Goto ApplyQualification");
						return mapping.findForward("applyQualification");
					}else if (stuRegForm.getStudent().isStuSLP()){
						//log.debug("ApplyForStudentNumberAction - applyLoginReturn (28) - Returning Student - Goto ApplyQualification");
						return mapping.findForward("applyQualification");
					}else{
						if ("UD".equalsIgnoreCase(stuRegForm.getLoginSelectMain())){
							if (stuRegForm.getStudent().isDateWAPRU()){
								//log.debug("ApplyForStudentNumberAction - applyLoginReturn (28a) - Returning Student - Undergrad - Goto APS Select");
								return mapping.findForward("applyAPSSelect");
							}else{
								stuRegForm.setAllowLogin(false);
								messages.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("message.generalmessage", "Applications for returning/existing students are closed for this semester. You will have to re-apply during the next application period."));
								addErrors(request, messages);
								setDropdownListsLogin(request,stuRegForm);
								return mapping.findForward("applyLogin");
							}
						}else{
							if (stuRegForm.getStudent().isDateWAPRH()){
								//log.debug("ApplyForStudentNumberAction - applyLoginReturn (28b) - Returning Student - HONS or M&D - Goto ApplyQualification");
								return mapping.findForward("applyQualification");
							}else{
								stuRegForm.setAllowLogin(false);
								messages.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("message.generalmessage", "Applications for returning/existing students are closed for this semester. You will have to re-apply during the next application period."));
								addErrors(request, messages);
								setDropdownListsLogin(request,stuRegForm);
								return mapping.findForward("applyLogin");
							}
						}
					}
				}
			}
		}
	}

	public ActionForward getPDFtoPrint(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {

		JSONObject pdfObj = new JSONObject();
		Map<String, String> mapPDF = new LinkedHashMap<String, String>();

		//log.debug("ApplyForStudentNumberAction - getPDFtoPrint - **************************************************************");
		
		//StudentRegistrationForm stuRegForm = (StudentRegistrationForm) form;
		
		//log.debug("ApplyForStudentNumberAction - getPDFtoPrint");

		String pdfFileName = "DSAR31.pdf";
		String serverpath = ServerConfigurationService.getServerUrl();
		//log.debug("ApplyForStudentNumberAction - getPDFtoPrint - serverpath="+serverpath);
		//log.debug("ApplyForStudentNumberAction - getPDFtoPrint - **************************************************************");
		
		String pdfPath = serverpath + "/unisa-studentregistration/resources/forms";
		
		//log.debug("ApplyForStudentNumberAction - getPDFtoPrint - pdfFile="+pdfPath+"/"+pdfFileName);

		//log.debug("ApplyForStudentNumberAction - getPDFtoPrint - **************************************************************");

		try{
			if (pdfPath == null || "".equals(pdfPath) || pdfFileName == null || "".equals(pdfFileName)){
				//log.debug("ApplyForStudentNumberAction - getPDFtoPrint - PDF Path or FileName Empty");
				mapPDF.put("Error","PDF Not found. Please try again.");
			}else{
				PdfDownloader pdfViewer=new PdfDownloader();
				pdfViewer.processPDFRequest(pdfPath, pdfFileName, response);
				mapPDF.put("PFDFile","PDF found - Successfully");
			}
		}catch (Exception e){
			//log.debug("ApplyForStudentNumberAction - getPDFtoPrint - Error Occurred recalling PDF / "+e);
			mapPDF.put("Error","PDF process crashed. Please try again.");
		}
		
		//log.debug("ApplyForStudentNumberAction - getPDFtoPrint - **************************************************************");
		//log.debug("ApplyForStudentNumberAction - getPDFtoPrint - Done");
		//log.debug("ApplyForStudentNumberAction - getPDFtoPrint - **************************************************************");

		// Convert the map to JSON
		PrintWriter out = response.getWriter();
		JSONObject jsonObject = JSONObject.fromObject(pdfObj);
		//log.debug("ApplyForStudentNumberAction - getPDFtoPrint - Final - **************************************************************");
		//log.debug("ApplyForStudentNumberAction - getPDFtoPrint - Final - jsonObject="+jsonObject.toString());
		//log.debug("ApplyForStudentNumberAction - getPDFtoPrint - Final - **************************************************************");
		out.write(jsonObject.toString());
		out.flush();

		return null; //Returning null to prevent struts from interfering with ajax/json
	}

	public ActionForward checkReturnBack(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		StudentRegistrationForm stuRegForm = (StudentRegistrationForm) form;
		
		stuRegForm.getStudent().setStuExist("curStu");
		//log.debug("ApplyForStudentNumberAction - checkReturnBack - Admin="+stuRegForm.getAdminStaff().isAdmin());
		if (stuRegForm.getAdminStaff().isAdmin()){
			return mapping.findForward("loginStaff");
		}else{
			return mapping.findForward("loginStu");
		}
	}
	
	public String displayPersonal(StudentRegistrationForm stuRegForm, HttpServletRequest request) throws Exception {
		
		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
		
		//log.debug("displayPersonal");
		Student teststu = new Student();
		teststu.setNumber(stuRegForm.getStudent().getNumber().trim());
		teststu.setFirstnames(stuRegForm.getStudent().getFirstnames().trim());
		teststu.setSurname(stuRegForm.getStudent().getSurname().trim());
		teststu.setBirthDay(stuRegForm.getStudent().getBirthDay());
		teststu.setBirthMonth(stuRegForm.getStudent().getBirthMonth());
		teststu.setBirthYear(stuRegForm.getStudent().getBirthYear());

		String result = "";

		String checkNumber = dao.personalDetails(stuRegForm.getStudent().getNumber(),"number").trim();
		if ("error".equalsIgnoreCase(checkNumber)){
			result = "The student number entered is invalid. Please enter a valid number.";
			return result;
		}else{
			stuRegForm.getStudent().setNumber(checkNumber);
			//log.debug("Student Number: " + stuRegForm.getStudent().getNumber());
		}
		
		String checkSurname = dao.personalDetails(stuRegForm.getStudent().getNumber(),"surname").trim();
		if ("error".equalsIgnoreCase(checkSurname)){
			result = "The surname information was not successfully retrieved from the database";
			return result;
		}else{
			stuRegForm.getStudent().setSurname(checkSurname);
			//log.debug("Surname: " + stuRegForm.getStudent().getSurname());
		}
		
		String checkFirstnames = dao.personalDetails(stuRegForm.getStudent().getNumber(),"first_names").trim();
		if ("error".equalsIgnoreCase(checkFirstnames)){
			result = "The First name information was not successfully retrieved from the database";
			return result;
		}else{
			stuRegForm.getStudent().setFirstnames(checkFirstnames);
			//log.debug("First Names: " + stuRegForm.getStudent().getFirstnames());
		}

		String checkMaidenname = dao.personalDetails(stuRegForm.getStudent().getNumber(),"previous_surname").trim();
		if ("error".equalsIgnoreCase(checkMaidenname)){
			result = "The Maiden name information was not successfully retrieved from the database";
			return result;
		}else{
			stuRegForm.getStudent().setMaidenName(checkMaidenname);
			//log.debug("Prev Surname: " + stuRegForm.getStudent().getMaidenName());
		}
		
		String checkIDnumber = dao.personalDetails(stuRegForm.getStudent().getNumber(),"identity_nr").trim();
		if ("error".equalsIgnoreCase(checkIDnumber)){
			result = "The Identity Number information was not successfully retrieved from the database";
			return result;
		}else{
			stuRegForm.getStudent().setIdNumber(checkIDnumber);
			//log.debug("ID: " + stuRegForm.getStudent().getIdNumber());
		}
		if (stuRegForm.getStudent().getIdNumber() == null || "".equals(stuRegForm.getStudent().getIdNumber().trim())){
			String checkPassport = dao.personalDetails(stuRegForm.getStudent().getNumber(),"passport_no").trim();
			if ("error".equalsIgnoreCase(checkPassport)){
				result = "The Passport or Foreign ID information was not successfully retrieved from the database";
				return result;
			}else{
				stuRegForm.getStudent().setPassportNumber(checkPassport);
				stuRegForm.getStudent().setForeignIdNumber(checkPassport);
				//log.debug("PassportNumber: " + stuRegForm.getStudent().getPassportNumber());
				//log.debug("ForeignIdNumber: " + stuRegForm.getStudent().getForeignIdNumber());
			}
		}
		
		String YYYYMMDD = "";
		String checkBDate = dao.personalDetails(stuRegForm.getStudent().getNumber(),"BIRTH_DATE").trim();
		if ("error".equalsIgnoreCase(checkBDate)){
			result = "The Birth Date information was not successfully retrieved from the database";
			return result;
		}else{
			YYYYMMDD = checkBDate;
			//log.debug("Birth Date: " + YYYYMMDD);
		}

		//log.debug("BirthDay: " + stuRegForm.getStudent().getBirthYear() + "/" + stuRegForm.getStudent().getBirthMonth() + "/" + stuRegForm.getStudent().getBirthDay());
		if(YYYYMMDD.length()>=8){
			//log.debug("ApplyForStudentNumberAction - displayPersonal - - N " + stuRegForm.getStudent().getNumber() + " Success BirthDay " + YYYYMMDD);
			stuRegForm.getStudent().setBirthYear(YYYYMMDD.substring(0,4));
			stuRegForm.getStudent().setBirthMonth(YYYYMMDD.substring(4,6));
			stuRegForm.getStudent().setBirthDay(YYYYMMDD.substring(6,8));
		}else{
			//log.debug("ApplyForStudentNumberAction - displayPersonal - - N " + stuRegForm.getStudent().getNumber() + " UnSuccess BirthDay " + YYYYMMDD);
			result = "The birth day information was not successfully retrieved from the database";
			return result;
		}
		//log.debug("ApplyForStudentNumberAction - displayPersonal - BirthDay: " + stuRegForm.getStudent().getBirthYear() + "/" + stuRegForm.getStudent().getBirthMonth() + "/" + stuRegForm.getStudent().getBirthDay());

		String tmpCellNr = dao.personalDetails(stuRegForm.getStudent().getNumber(),"cell_number").trim();
		if (!"error".equalsIgnoreCase(tmpCellNr)){
			stuRegForm.getStudent().setCellNr(tmpCellNr);
		}else{
			stuRegForm.getStudent().setCellNr("");
		}
		//log.debug("ApplyForStudentNumberAction - displayPersonal - Cell Number: " + stuRegForm.getStudent().getCellNr());

		//log.debug("ApplyForStudentNumberAction - displayPersonal - - N " + stuRegForm.getStudent().getNumber() + " Cell " + stuRegForm.getStudent().getCellNr());
		
		String tmpEmail = dao.personalDetails(stuRegForm.getStudent().getNumber(),"email_address").trim();
		if (!"error".equalsIgnoreCase(tmpEmail)){
			stuRegForm.getStudent().setEmailAddress(tmpEmail);
		}else{
			stuRegForm.getStudent().setEmailAddress("");
		}
		//log.debug("ApplyForStudentNumberAction - displayPersonal - Email Address: " + stuRegForm.getStudent().getEmailAddress());

		//log.debug("ApplyForStudentNumberAction - displayPersonal - - N " + stuRegForm.getStudent().getNumber() + " Email " + stuRegForm.getStudent().getEmailAddress());

		String checkMailMyLife = "";
		checkMailMyLife = stuRegForm.getStudent().getEmailAddress().trim();
		//String checkMail2 = stuRegForm.getStudent().getEmailAddress2();
		Integer eCheckIndex=checkMailMyLife.indexOf("mylife.unisa");
		//Integer eCheckIndex2=checkMail2.indexOf("mylife.unisa");
		
		if (eCheckIndex >= 1) {
			stuRegForm.getStudent().setEmailAddressGood(true);
		}

		// Closing date  --Integer.parseInt(String.valueOf(stuRegForm.getStudent().getAcademicYear())))){

		//Validate if screen input is correct student
		if (!stuRegForm.getStudent().getSurname().equalsIgnoreCase(teststu.getSurname())){
			result = "The surname you have entered does not correspond with the information on the database";
		}else if (!stuRegForm.getStudent().getFirstnames().equalsIgnoreCase(teststu.getFirstnames())){
			result= "The first name(s) you have entered does not correspond with the information on the database";
		}else if (!stuRegForm.getStudent().getBirthYear().equals(teststu.getBirthYear())){
			result="The birthdate you have entered does not correspond with the information on the database";
		}else if (!stuRegForm.getStudent().getBirthMonth().equals(teststu.getBirthMonth())){
			result="The birthdate you have entered does not correspond with the information on the database";
		}else if (!stuRegForm.getStudent().getBirthDay().equals(teststu.getBirthDay())){
			result="The birthdate you have entered does not correspond with the information on the database";
		}else if (!dao.validateClosingDateAll(stuRegForm.getStudent().getAcademicYear())){
			if ("AllClosed".equalsIgnoreCase(stuRegForm.getStudent().getWebOpenDate())){
				result="The application period for study at Unisa is closed.";
			}
		}

		//if (!"".equals(result)){
			//error result - move input back to screen values
			stuRegForm.getStudent().setNumber(teststu.getNumber());
			stuRegForm.getStudent().setNumberCheck(stuRegForm.getStudent().getNumber());
			stuRegForm.getStudent().setSurname(teststu.getSurname());
			stuRegForm.getStudent().setFirstnames(teststu.getFirstnames());
			stuRegForm.getStudent().setBirthDay(teststu.getBirthDay());
			stuRegForm.getStudent().setBirthMonth(teststu.getBirthMonth());
			stuRegForm.getStudent().setBirthYear(teststu.getBirthYear());
		//}
		//if any error message =result
		//log.debug("ApplyForStudentNumberAction - displayPersonal - Result: " + result);
		return result;
	}

	
	/*
	 * populates the qualification categories dropdown
	 */

	public ActionForward populateCategories(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		//log.debug("ApplyForStudentNumberAction - populateCategories");
		
		ActionMessages messages = new ActionMessages();
		StudentRegistrationForm stuRegForm = (StudentRegistrationForm) form;
		
		try{
			//Reset web messages
			stuRegForm.setWebUploadMsg("");
			stuRegForm.setWebLoginMsg("");
			stuRegForm.setWebLoginMsg2("");
					
			ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
			//log.debug("ApplyForStudentNumberAction - populateCategories - AcademicYear="+stuRegForm.getStudent().getAcademicYear()+", LoginSelectMain="+stuRegForm.getLoginSelectMain()+", isAdmin="+stuRegForm.getAdminStaff().isAdmin());

			Categories categories = dao.getCategories(stuRegForm.getStudent().getAcademicYear(), stuRegForm.getLoginSelectMain(), stuRegForm.getAdminStaff().isAdmin(), stuRegForm.getStudent().isDateWAPU(),stuRegForm.getStudent().isDateWAPRU(),stuRegForm.getStudent().isDateWAPADMU(),stuRegForm.getStudent().isDateWAPH(),stuRegForm.getStudent().isDateWAPRH(),stuRegForm.getStudent().isDateWAPADMH(),stuRegForm.getStudent().isDateWAPD(),stuRegForm.getStudent().isDateWAPADMD(),stuRegForm.getStudent().isDateWAPM(),stuRegForm.getStudent().isDateWAPADMM(),stuRegForm.getStudent().isDateWAPS(),stuRegForm.getStudent().isDateWAPADMS());
			
			List<String> codes = categories.getCatCodes();
			List<String> descs = categories.getCatDescs();
		    
			String jsonString = generateSortedString(codes, descs);
			//log.debug(jsonString);
			PrintWriter out = response.getWriter();
			out.write(jsonString);
			out.flush();
		}catch(Exception ex){
			log.warn("ApplyForStudentNumberAction populateCategories - Error="+ex);
			messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", "An Error occurred populating Categories. Please log on again to retry."));
			addErrors(request, messages);
			if (stuRegForm.getAdminStaff().isAdmin()){
				if(stuRegForm.getLoginSelectYesNo().equalsIgnoreCase("NO")){
					stuRegForm.setWebLoginMsg("Administrator - First-time applicant");
					stuRegForm.setWebLoginMsg2("");
					try {
						setDropdownListsLogin(request,stuRegForm);
					} catch (Exception e) {
						
						e.printStackTrace();
					}
					setDropdownListsLogin(request,stuRegForm);
					return mapping.findForward("applyLogin");
				}else{
					stuRegForm.setWebLoginMsg("Administrator - Returning applicant");
					stuRegForm.setWebLoginMsg2("");
					try {
						setDropdownListsLogin(request,stuRegForm);
					} catch (Exception e) {
						
						e.printStackTrace();
					}
					setDropdownListsLogin(request,stuRegForm);
					return mapping.findForward("applyLogin");
				}
			}else{
				if(stuRegForm.getLoginSelectYesNo().equalsIgnoreCase("NO")){
					stuRegForm.setWebLoginMsg("First-time applicant");
					stuRegForm.setWebLoginMsg2("");
					try {
						setDropdownListsLogin(request,stuRegForm);
					} catch (Exception e) {
						
						e.printStackTrace();
					}
					setDropdownListsLogin(request,stuRegForm);
					return mapping.findForward("applyLogin");
				}else{
					stuRegForm.setWebLoginMsg("Returning applicant");
					stuRegForm.setWebLoginMsg2("");
					try {
						setDropdownListsLogin(request,stuRegForm);
					} catch (Exception e) {
						
						e.printStackTrace();
					}
					setDropdownListsLogin(request,stuRegForm);
					return mapping.findForward("applyLogin");
				}
			}
		}
		return null;
	}
	
	/*
	 * populates the qualifications dropdown
	 */

	public ActionForward populateQualifications(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception  {

		ActionMessages messages = new ActionMessages();
		StudentRegistrationForm stuRegForm = (StudentRegistrationForm) form;
		Qualifications qualifications = new Qualifications();
		List<String> codes = new ArrayList<String>();
		List<String> descs = new ArrayList<String>();
		
		try {
			String selCategoryCode = stripXSS(request.getParameter("selCategoryCode"), "selCategoryCode", "popQual", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true);
			
			ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
			//log.debug("ApplyForStudentNumberAction - populateQualifications - selCategoryCode: " + selCategoryCode + " - acaYear: " + stuRegForm.getStudent().getAcademicYear() + " - catSelect: " + selCategoryCode+", isAdmin="+stuRegForm.getAdminStaff().isAdmin());

			//log.debug("ApplyForStudentNumberAction - populateQualifications - selCategoryCode: " + selCategoryCode + " - acaYear: " + stuRegForm.getStudent().getAcademicYear() + " - catSelect: " + selCategoryCode+", isAdmin="+stuRegForm.getAdminStaff().isAdmin()+", isQualIDMatch="+stuRegForm.isQualIDMatch());

			boolean isQualListOK = false;
			if ("NSC".equalsIgnoreCase(stuRegForm.getSelectHEMain()) && ("02".equals(selCategoryCode) || "03".equals(selCategoryCode) || "04".equals(selCategoryCode) || "05".equals(selCategoryCode) || "06".equals(selCategoryCode) || "07".equals(selCategoryCode))){

				//log.debug("ApplyForStudentNumberAction - populateQualifications - (Staae05sAppAdmissionEvaluator) - ID Match");
				Staae05sAppAdmissionEvaluator op = new Staae05sAppAdmissionEvaluator();
				operListener opl = new operListener();
				op.addExceptionListener(opl);
				op.clear();
				
				if(stuRegForm.isQualIDMatch()){
					// F915 ID Check
					
					op.setInCsfClientServerCommunicationsClientVersionNumber((short) 3);
					op.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
					op.setInCsfClientServerCommunicationsAction("NL");
					op.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
					op.setInWsUserNumber(99998);
					op.setInWsStudentIdentityNr(stuRegForm.getStudent().getIdNumber());
					op.setInWsAcademicYearYear((short) Integer.parseInt(stuRegForm.getStudent().getAcademicYear()));
					
					//op.setInGcQualLevelCsfStringsString2(selCategoryCode);
					op.setInGencodQualLevelCsfStringsString2(selCategoryCode);
						
					//log.debug("ApplyForStudentNumberAction - populateQualifications - (Staae05sAppAdmissionEvaluator) - Student ID Number=" + stuRegForm.getStudent().getIdNumber()+", Year="+stuRegForm.getStudent().getAcademicYear()+", Category="+selCategoryCode);
	
					op.execute();
	
					if (opl.getException() != null) throw opl.getException();
					if (op.getExitStateType() < 3) throw new Exception(op.getExitStateMsg());
	
					//log.debug("ApplyForStudentNumberAction - populateQualifications - After ID Execute");
					String opResult = op.getOutCsfStringsString500();
					//log.debug("ApplyForStudentNumberAction - populateQualifications - (ID) opResult: " + opResult);
					
					//log.debug("ApplyForStudentNumberAction - populateQualifications - (ID) - getOutCsfStringsString500="+op.getOutCsfStringsString500());

					
					//log.debug("ApplyForStudentNumberAction - populateQualifications - (ID) - After F915 ID Check");
					//log.debug("ApplyForStudentNumberAction - populateQualifications - (ID) - =================================================================================");
					//log.debug("ApplyForStudentNumberAction - populateQualifications - (ID) - getOutGroupCount="+op.getOutGroupCount());
					
					//Are there Proxy Rows returned?
					if (op.getOutGroupCount() > 0){
						//log.debug("ApplyForStudentNumberAction - populateQualifications - (ID) - In getOutGroupCount");
						//Set Qualifications from Proxy
						//Get GENCOD Categories to filter list
						ArrayList<String> codList = dao.getGENCODCategory(selCategoryCode);
						
						
						
						
						//Debug
						//log.debug("ApplyForStudentNumberAction - populateQualifications - (ID) - =================================================================================");
						//log.debug("ApplyForStudentNumberAction - populateQualifications - (ID) - Before F915 First List Check");
						for (int x = 0; x < op.getOutGroupCount(); x++){
							
							//log.debug("ApplyForStudentNumberAction - populateQualifications - ==================================================================================");
					
							//log.debug("ApplyForStudentNumberAction - populateQualifications - (ID) - Count="+x+" - F915 - getOutGroupCount                                     ="+op.getOutGroupCount());
							//log.debug("ApplyForStudentNumberAction - populateQualifications - (ID) - Count="+x+" - F915 - getOutGWsQualificationCode                           ="+op.getOutGWsQualificationCode(x).toString());
							//log.debug("ApplyForStudentNumberAction - populateQualifications - (ID) - Count="+x+" - F915 - getOutGWsQualificationSpecialityTypeSpecialityCode   ="+op.getOutGWsQualificationSpecialityTypeSpecialityCode(x).toString());
							//log.debug("ApplyForStudentNumberAction - populateQualifications - (ID) - Count="+x+" - F915 - getOutGWsQualificationType                           ="+op.getOutGWsQualificationType(x).toString());
							//log.debug("ApplyForStudentNumberAction - populateQualifications - (ID) - Count="+x+" - F915 - getOutGCsfStringsString2                             ="+op.getOutGCsfStringsString2(x).toString());
							//log.debug("ApplyForStudentNumberAction - populateQualifications - (ID) - Count="+x+" - F915 - getOutGCsfStringsString3                             ="+op.getOutGCsfStringsString3(x).toString());
							//log.debug("ApplyForStudentNumberAction - populateQualifications - (ID) - Count="+x+" - F915 - getOutGCsfStringsString4                             ="+op.getOutGCsfStringsString4(x).toString());
							//log.debug("ApplyForStudentNumberAction - populateQualifications - (ID) - Count="+x+" - F915 - getOutWsMatricRecordApsScore                         ="+op.getOutWsMatricRecordApsScore());
							//log.debug("ApplyForStudentNumberAction - populateQualifications - (ID) - Count="+x+" - F915 - getOutWsMatricStatusCode                             ="+op.getOutWsMatricStatusCode().toString());
							
							//log.debug("ApplyForStudentNumberAction - populateQualifications - (ID) - Count="+x+" - After F915 List Check");
							//log.debug("ApplyForStudentNumberAction - populateQualifications - (ID) - ==================================================================================");
						}
						//log.debug("ApplyForStudentNumberAction - populateQualifications - (ID) - After F915 First List Check");
						//log.debug("ApplyForStudentNumberAction - populateQualifications - (ID) - =================================================================================");

						String qualCodeFound = "";
						for (int i = 0; i < op.getOutGroupCount(); i++){ 
							//log.debug("ApplyForStudentNumberAction - populateQualifications - (ID) - ==================================================================================");
							//log.debug("ApplyForStudentNumberAction - populateQualifications - (ID) - If For Loop getOutGroupCount="+i);
							
							String QualCode = "";
							String QualDesc = "";
							String QualNotOK = " - (You may not qualify)";
							
							//If in the correct Category
							//log.debug("ApplyForStudentNumberAction - populateQualifications - (ID) - Checking Category="+selCategoryCode+" == "+op.getOutGCsfStringsString3(i).toString());
			
							if ((selCategoryCode.toString().trim()).equalsIgnoreCase(op.getOutGCsfStringsString3(i).toString().trim())){ //Example 07 = 07
								//log.debug("ApplyForStudentNumberAction - populateQualifications - (ID) - Category Matched - In Category Loop");
								
								for (int j = 0; j < codList.size(); j++){
									//log.debug("ApplyForStudentNumberAction - populateQualifications - (ID) - Checking CatCode from CENCOD =  codList.size()="+codList.size());
									//log.debug("ApplyForStudentNumberAction - populateQualifications - (ID) - Checking CatCode="+codList.get(j).toString().trim()+" ==  codList="+op.getOutGCsfStringsString2(i).toString().trim());
									if (codList.get(j).toString().trim().equals(op.getOutGCsfStringsString2(i).toString().trim()) && !qualCodeFound.trim().equalsIgnoreCase(op.getOutGWsQualificationCode(i).toString().trim())){
										//log.debug("ApplyForStudentNumberAction - populateQualifications - (ID) - ===================================================================================");
										//log.debug("ApplyForStudentNumberAction - populateQualifications - (ID) - In CatCode Check");
										
										//Get Qualification and Description
										qualCodeFound = op.getOutGWsQualificationCode(i).toString().trim();
										isQualListOK = true;
										QualCode = op.getOutGWsQualificationCode(i).toString().trim();
										QualDesc = dao.getQualDesc(QualCode).trim();
										if (!"OK".equalsIgnoreCase(op.getOutGCsfStringsString4(i).toString().trim())){
											QualDesc += QualNotOK;
										}
										codes.add(QualCode);
										descs.add(QualDesc);
										//log.debug("ApplyForStudentNumberAction - populateQualifications - (ID) - ===================================================================================");
										//log.debug("ApplyForStudentNumberAction - populateQualifications - (ID) - Previous No - Qualification Found="+QualCode+", QualDesc="+QualDesc);
									}
								}
							}
						}
						//log.debug("ApplyForStudentNumberAction - populateQualifications - (ID) - ==================================================================================");
						//log.debug("ApplyForStudentNumberAction - populateQualifications - (ID) - STAAE01H ID Done");
						//log.debug("ApplyForStudentNumberAction - populateQualifications - (ID) - ==================================================================================");

					}else{
						//log.debug("ApplyForStudentNumberAction - populateQualifications - (ID) - ==================================================================================");
						//log.debug("ApplyForStudentNumberAction - populateQualifications - (ID) - getOutGroupCount Was Empty");

					}
					op.clear();
				}else{ //No Matric Match, thus send in Matric Subjects to Proxy for APS Score test
					
					//log.debug("ApplyForStudentNumberAction - populateQualifications - (Matric) - ==================================================================================");
					//log.debug("ApplyForStudentNumberAction - populateQualifications - (Staae05sAppAdmissionEvaluator) - APS Match");
					op.setInCsfClientServerCommunicationsClientVersionNumber((short) 3);
					op.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
					op.setInCsfClientServerCommunicationsAction("NL");
					op.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
					op.setInWsUserNumber(99998);
					op.setInWsStudentIdentityNr(stuRegForm.getStudent().getIdNumber());
					op.setInWsAcademicYearYear((short) Integer.parseInt(stuRegForm.getStudent().getAcademicYear()));
					
					//op.setInGcQualLevelCsfStringsString2(selCategoryCode); 
					op.setInGencodQualLevelCsfStringsString2(selCategoryCode);
						
					//log.debug("ApplyForStudentNumberAction - populateQualifications - (Staae05sAppAdmissionEvaluator) - Student ID Number=" + stuRegForm.getStudent().getIdNumber());
	
					//Add Matric Subjects
					//log.debug("ApplyForStudentNumberAction - populateQualifications - (Matric) - Subject Count="+stuRegForm.getSubjects().size());
					
					//Set In Group View
					op.setInGroupMatricCount((short) stuRegForm.getSubjects().size());
					//log.debug("ApplyForStudentNumberAction - populateQualifications - (Matric) - Matric Count="+stuRegForm.getSubjects().size());
					
					if ("MATR".equalsIgnoreCase(stuRegForm.getSelectMatric()) || "SENR".equalsIgnoreCase(stuRegForm.getSelectMatric()) || "ONBK".equalsIgnoreCase(stuRegForm.getSelectMatric())){
						op.setInWsMatricCertificateCode("SC");
						//log.debug("ApplyForStudentNumberAction - populateQualifications - (Matric) - Matric Certificate=SC");
					}else{
						op.setInWsMatricCertificateCode("NSC");
						//log.debug("ApplyForStudentNumberAction - populateQualifications - (Matric) - Matric Certificate=NSC");
					}
					op.setInWsMatricStatusCode(stuRegForm.getSelectMatric()); //MATR, DEGR etc
					//log.debug("ApplyForStudentNumberAction - populateQualifications - (Matric) - Matric StatusCode Set="+stuRegForm.getSelectMatric());
										
					for (int s = 0; s < stuRegForm.getSubjects().size(); s++){
						//log.debug("ApplyForStudentNumberAction - populateQualifications - (Matric) - Check Subject"+s+"="+stuRegForm.getSubjects().get(s).getSubjectName()+", Grade="+stuRegForm.getSubjects().get(s).getSubjectGrade()+", Symbol="+stuRegForm.getSubjects().get(s).getSubjectSymbol()+", Result="+stuRegForm.getSubjects().get(s).getSubjectResult());
						op.setInGmWsMatricSubjectCode(s, stuRegForm.getSubjects().get(s).getSubjectName());
						//log.debug("ApplyForStudentNumberAction - populateQualifications - (Matric) - Check Subject"+s+" - Subject="+stuRegForm.getSubjects().get(s).getSubjectName());
						//Send in Lowest Score. Example: A 80-100, use 80%
						if ("MATR".equalsIgnoreCase(stuRegForm.getSelectMatric()) || "SENR".equalsIgnoreCase(stuRegForm.getSelectMatric()) || "ONBK".equalsIgnoreCase(stuRegForm.getSelectMatric())){
							
							int subMark = 0;
							if ("A".equalsIgnoreCase(stuRegForm.getSubjects().get(s).getSubjectSymbol())){
								subMark = 80;
							}else if ("B".equalsIgnoreCase(stuRegForm.getSubjects().get(s).getSubjectSymbol())){
								subMark = 70;
							}else if ("C".equalsIgnoreCase(stuRegForm.getSubjects().get(s).getSubjectSymbol())){
								subMark = 60;
							}else if ("D".equalsIgnoreCase(stuRegForm.getSubjects().get(s).getSubjectSymbol())){
								subMark = 50;
							}else if ("E".equalsIgnoreCase(stuRegForm.getSubjects().get(s).getSubjectSymbol())){
								subMark = 40;
							}else if ("F".equalsIgnoreCase(stuRegForm.getSubjects().get(s).getSubjectSymbol())){
								subMark = 33;
							}else if ("G".equalsIgnoreCase(stuRegForm.getSubjects().get(s).getSubjectSymbol())){
								subMark = 0;
							}
							//log.debug("ApplyForStudentNumberAction - populateQualifications - (Matric) - Check Subject"+s+" - SC - Mark="+subMark);
							op.setInGmWsMatricSubjectResultMark(s, (short) subMark);
							
							String subGrade = "L";
							if ("HG".equalsIgnoreCase(stuRegForm.getSubjects().get(s).getSubjectGrade())){
								subGrade = "H";
							}else if ("SG".equalsIgnoreCase(stuRegForm.getSubjects().get(s).getSubjectGrade())){
								subGrade = "S";
							}else if ("LG".equalsIgnoreCase(stuRegForm.getSubjects().get(s).getSubjectGrade())){
								subGrade = "L";
							}
							//log.debug("ApplyForStudentNumberAction - populateQualifications - (Matric) - Check Subject"+s+" - SC - Grade="+subGrade);
							op.setInGmWsMatricSubjectResultGrade(s, subGrade);

						}else{
							int subMark = 0;
							if ("A".equalsIgnoreCase(stuRegForm.getSubjects().get(s).getSubjectResult())){
								subMark = 80;
							}else if ("B".equalsIgnoreCase(stuRegForm.getSubjects().get(s).getSubjectResult())){
								subMark = 70;
							}else if ("C".equalsIgnoreCase(stuRegForm.getSubjects().get(s).getSubjectResult())){
								subMark = 60;
							}else if ("D".equalsIgnoreCase(stuRegForm.getSubjects().get(s).getSubjectResult())){
								subMark = 50;
							}else if ("E".equalsIgnoreCase(stuRegForm.getSubjects().get(s).getSubjectResult())){
								subMark = 40;
							}else if ("F".equalsIgnoreCase(stuRegForm.getSubjects().get(s).getSubjectResult())){
								subMark = 30;
							}else if ("G".equalsIgnoreCase(stuRegForm.getSubjects().get(s).getSubjectResult())){
								subMark = 0;
							}
							//log.debug("ApplyForStudentNumberAction - populateQualifications - (Matric) - Check Subject"+s+" - NSC - Mark="+subMark);
							op.setInGmWsMatricSubjectResultMark(s, (short) subMark);
							//log.debug("ApplyForStudentNumberAction - populateQualifications - (Matric) - Check Subject"+s+" - NSC - Grade=N");
							op.setInGmWsMatricSubjectResultGrade(s, "N");
						}					
					}
					op.execute();
	
					if (opl.getException() != null) throw opl.getException();
					if (op.getExitStateType() < 3) throw new Exception(op.getExitStateMsg());	
					//log.debug("ApplyForStudentNumberAction - populateQualifications - (Matric) - After APS Execute");
					
					String opResult = op.getOutCsfStringsString500();
					//log.debug("ApplyForStudentNumberAction - populateQualifications - (Matric) - opResult: " + opResult);
					
					//log.debug("ApplyForStudentNumberAction - populateQualifications - Staae05sAppAdmissionEvaluator (Matric)- getOutCsfStringsString500="+op.getOutCsfStringsString500());

					
					//log.debug("ApplyForStudentNumberAction - populateQualifications - (Matric) - After F915 ID Check");
					//log.debug("ApplyForStudentNumberAction - populateQualifications - (Matric) - ==============================================================================");
					//log.debug("ApplyForStudentNumberAction - populateQualifications - (Matric) - getOutGroupCount="+op.getOutGroupCount());

					//Debug
					//log.debug("ApplyForStudentNumberAction - populateQualifications - (Matric) - =================================================================================");
					//log.debug("ApplyForStudentNumberAction - populateQualifications - (Matric) - Before F915 First List Check");
					for (int x = 0; x < op.getOutGroupCount(); x++){
						
						//log.debug("ApplyForStudentNumberAction - populateQualifications - ==================================================================================");
				
						//log.debug("ApplyForStudentNumberAction - populateQualifications - (Matric) - Count="+x+" - F915 - getOutGroupCount                                     ="+op.getOutGroupCount());
						//log.debug("ApplyForStudentNumberAction - populateQualifications - (Matric) - Count="+x+" - F915 - getOutGWsQualificationCode                           ="+op.getOutGWsQualificationCode(x).toString());
						//log.debug("ApplyForStudentNumberAction - populateQualifications - (Matric) - Count="+x+" - F915 - getOutGWsQualificationSpecialityTypeSpecialityCode   ="+op.getOutGWsQualificationSpecialityTypeSpecialityCode(x).toString());
						//log.debug("ApplyForStudentNumberAction - populateQualifications - (Matric) - Count="+x+" - F915 - getOutGWsQualificationType                           ="+op.getOutGWsQualificationType(x).toString());
						//log.debug("ApplyForStudentNumberAction - populateQualifications - (Matric) - Count="+x+" - F915 - getOutGCsfStringsString2                             ="+op.getOutGCsfStringsString2(x).toString());
						//log.debug("ApplyForStudentNumberAction - populateQualifications - (Matric) - Count="+x+" - F915 - getOutGCsfStringsString3                             ="+op.getOutGCsfStringsString3(x).toString());
						//log.debug("ApplyForStudentNumberAction - populateQualifications - (Matric) - Count="+x+" - F915 - getOutGCsfStringsString4                             ="+op.getOutGCsfStringsString4(x).toString());
						//log.debug("ApplyForStudentNumberAction - populateQualifications - (Matric) - Count="+x+" - F915 - getOutWsMatricRecordApsScore                         ="+op.getOutWsMatricRecordApsScore());
						//log.debug("ApplyForStudentNumberAction - populateQualifications - (Matric) - Count="+x+" - F915 - getOutWsMatricStatusCode                             ="+op.getOutWsMatricStatusCode().toString());
						
						//log.debug("ApplyForStudentNumberAction - populateQualifications - (Matric) - Count="+x+" - After F915 List Check");
						//log.debug("ApplyForStudentNumberAction - populateQualifications - (Matric) - ==================================================================================");
					}
					//log.debug("ApplyForStudentNumberAction - populateQualifications - (Matric) - After F915 First List Check");
					//log.debug("ApplyForStudentNumberAction - populateQualifications - (Matric) - =================================================================================");

					//Are there Proxy Rows returned?
					if (op.getOutGroupCount() > 0){
						//Set Qualifications from Proxy
						//Get GENCOD Categories to filter list
						ArrayList<String> codList = dao.getGENCODCategory(selCategoryCode);
		
						String qualCodeFound = "";
						for (int i = 0; i < op.getOutGroupCount(); i++){

							String QualCode = "";
							String QualDesc = "";
							String QualNotOK = " - (You may not qualify)";
							
							//If in the correct Category
							//log.debug("ApplyForStudentNumberAction - populateQualifications - (Matric) -selCategoryCode=["+selCategoryCode+"], & getOutGCsfStringsString3=["+op.getOutGCsfStringsString3(i).toString().trim()+"]" );
							if (selCategoryCode.equals(op.getOutGCsfStringsString3(i).toString().trim())){ //Example 07 = 07
								//log.debug("ApplyForStudentNumberAction - populateQualifications - (Matric) - Checking CatCode from CENCOD =  codList.size()="+codList.size());

								for (int j = 0; j < codList.size(); j++){
									//log.debug("ApplyForStudentNumberAction - populateQualifications - (Matric) - Checking CatCode="+codList.get(j).toString().trim()+" ==  codList="+op.getOutGCsfStringsString2(i).toString().trim());
									if (codList.get(j).toString().equals(op.getOutGCsfStringsString2(i).toString().trim())  && !qualCodeFound.equalsIgnoreCase(op.getOutGWsQualificationCode(i).toString().trim())){
										
										//log.debug("ApplyForStudentNumberAction - populateQualifications - (Matric) - ===================================================================================");
										//log.debug("ApplyForStudentNumberAction - populateQualifications - (Matric) - In CatCode Check");

										//Get Qualification and Description
										qualCodeFound = op.getOutGWsQualificationCode(i).toString().trim();
										isQualListOK = true;
										QualCode = op.getOutGWsQualificationCode(i).toString().trim();
										QualDesc = dao.getQualDesc(QualCode).trim();
										if (!"OK".equalsIgnoreCase(op.getOutGCsfStringsString4(i).toString().trim())){
											QualDesc += QualNotOK;
										}
										codes.add(QualCode);
										descs.add(QualDesc);
										//log.debug("ApplyForStudentNumberAction - populateQualifications - (Matric) - ===================================================================================");
										//log.debug("ApplyForStudentNumberAction - populateQualifications - (Matric) - Qualification Found="+QualCode+", QualDesc="+QualDesc);

									}
								}
							}
						}

						//log.debug("ApplyForStudentNumberAction - populateQualifications - (Matric) - ==================================================================================");
						//log.debug("ApplyForStudentNumberAction - populateQualifications - (Matric) - STAAE05H ID Done");
						//log.debug("ApplyForStudentNumberAction - populateQualifications - (Matric) - ==================================================================================");

					}else{
						//log.debug("ApplyForStudentNumberAction - populateQualifications - (Matric) - ==================================================================================");
						//log.debug("ApplyForStudentNumberAction - populateQualifications - (Matric) - getOutGroupCount Was Empty");
					}
				}
			}
			if (!isQualListOK || codes.size() == 0 ||  codes.isEmpty()){ //Catch-all: If no qualifications from proxy, then give list anyway as not to turn students away - Claudette

				qualifications = dao.getQualifications(stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getStuExist(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), selCategoryCode, stuRegForm.getLoginSelectMain(), stuRegForm.getAdminStaff().isAdmin(), stuRegForm.getStudent().isDateWAPU(),stuRegForm.getStudent().isDateWAPRU(),stuRegForm.getStudent().isDateWAPADMU(),stuRegForm.getStudent().isDateWAPH(),stuRegForm.getStudent().isDateWAPRH(),stuRegForm.getStudent().isDateWAPADMH(),stuRegForm.getStudent().isDateWAPD(),stuRegForm.getStudent().isDateWAPADMD(),stuRegForm.getStudent().isDateWAPM(),stuRegForm.getStudent().isDateWAPADMM(),stuRegForm.getStudent().isDateWAPS(),stuRegForm.getStudent().isDateWAPADMS());
		
				codes = qualifications.getQualCodes();
				descs = qualifications.getQualDescs();
	
			}
			String jsonString = generateSortedString(codes, descs);
			//log.debug(jsonString);
			PrintWriter out = response.getWriter();
			out.write(jsonString);
			out.flush();
		}catch(Exception ex){
			log.warn("ApplyForStudentNumberAction populateCategories - Error="+ex);
			messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", "An Error occurred populating Qualifications. Please log on again to retry."));
			addErrors(request, messages);
			if (stuRegForm.getAdminStaff().isAdmin()){
				if(stuRegForm.getLoginSelectYesNo().equalsIgnoreCase("NO")){
					stuRegForm.setWebLoginMsg("Administrator - First-time applicant");
					stuRegForm.setWebLoginMsg2("");
					try {
						setDropdownListsLogin(request,stuRegForm);
					} catch (Exception e) {
						
						e.printStackTrace();
					}
					setDropdownListsLogin(request,stuRegForm);
					return mapping.findForward("applyLogin");
				}else{
					stuRegForm.setWebLoginMsg("Administrator - Returning applicant");
					stuRegForm.setWebLoginMsg2("");
					try {
						setDropdownListsLogin(request,stuRegForm);
					} catch (Exception e) {
						
						e.printStackTrace();
					}
					setDropdownListsLogin(request,stuRegForm);
					return mapping.findForward("applyLogin");
				}
			}else{
				if(stuRegForm.getLoginSelectYesNo().equalsIgnoreCase("NO")){
					stuRegForm.setWebLoginMsg("First-time applicant");
					stuRegForm.setWebLoginMsg2("");
					try {
						setDropdownListsLogin(request,stuRegForm);
					} catch (Exception e) {
						
						e.printStackTrace();
					}
					setDropdownListsLogin(request,stuRegForm);
					return mapping.findForward("applyLogin");
				}else{
					stuRegForm.setWebLoginMsg("Returning applicant");
					stuRegForm.setWebLoginMsg2("");
					try {
						setDropdownListsLogin(request,stuRegForm);
					} catch (Exception e) {
						
						e.printStackTrace();
					}
					setDropdownListsLogin(request,stuRegForm);
					return mapping.findForward("applyLogin");
				}
			}
		}
		return null; /*Return Null as data is handeled by JSON */
	}

	/*
	 * populates the specializations drop down
	 */

	public ActionForward populateSpecializations(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ActionMessages messages = new ActionMessages();
		StudentRegistrationForm stuRegForm = (StudentRegistrationForm) form;
		
		Specializations specializations = new Specializations();
		List<String> codes = new ArrayList<String>();
		List<String> descs = new ArrayList<String>();
		
		try {
			String selCategoryCode = stripXSS(request.getParameter("selCategoryCode"), "selCategoryCode", "popSpec", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true);
			String selQualCode = stripXSS(request.getParameter("selQualCode"), "selQualCode", "popSpec", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true);
			
			ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
			//log.debug("ApplyForStudentNumberAction - populateSpecializations - selCategoryCode: " + selCategoryCode + " - acaYear: " + stuRegForm.getStudent().getAcademicYear() + " - selQualCode: " + selQualCode+", isAdmin="+stuRegForm.getAdminStaff().isAdmin());

			//log.debug("ApplyForStudentNumberAction - populateSpecializations - selCategoryCode: " + selCategoryCode + " - acaYear: " + stuRegForm.getStudent().getAcademicYear() + " - selQualCode: " + selQualCode+", isAdmin="+stuRegForm.getAdminStaff().isAdmin()+", isQualIDMatch="+stuRegForm.isQualIDMatch());

			boolean isSpecListOK = false;
			if ("NSC".equalsIgnoreCase(stuRegForm.getSelectHEMain()) && ("02".equals(selCategoryCode) || "03".equals(selCategoryCode) || "04".equals(selCategoryCode) || "05".equals(selCategoryCode) || "06".equals(selCategoryCode) || "07".equals(selCategoryCode))){
				
				Staae05sAppAdmissionEvaluator op = new Staae05sAppAdmissionEvaluator();
				operListener opl = new operListener();
				op.addExceptionListener(opl);
				op.clear();
				
				if(stuRegForm.isQualIDMatch()){
					// F915 ID Check
					
					op.setInCsfClientServerCommunicationsClientVersionNumber((short) 3);
					op.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
					op.setInCsfClientServerCommunicationsAction("NL");
					op.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
					op.setInWsUserNumber(99998);
					op.setInWsStudentIdentityNr(stuRegForm.getStudent().getIdNumber());
					op.setInWsAcademicYearYear((short) Integer.parseInt(stuRegForm.getStudent().getAcademicYear()));
					
					op.setInGencodQualLevelCsfStringsString2(selCategoryCode);
					//op.setInGcQualLevelCsfStringsString2(selCategoryCode); 

					//log.debug("ApplyForStudentNumberAction - populateSpecializations - (Staae05sAppAdmissionEvaluator) - Student ID Number=" + stuRegForm.getStudent().getIdNumber());
	
					op.execute();
	
					if (opl.getException() != null) throw opl.getException();
					if (op.getExitStateType() < 3) throw new Exception(op.getExitStateMsg());
	
					//log.debug("ApplyForStudentNumberAction - populateSpecializations - After ID Execute");
					String opResult = op.getOutCsfStringsString500();
					//log.debug("ApplyForStudentNumberAction - populateSpecializations - opResult: " + opResult);
					
					//log.debug("ApplyForStudentNumberAction - populateSpecializations - Staae05sAppAdmissionEvaluator - (ID) - getOutCsfStringsString500="+op.getOutCsfStringsString500());

					
					//log.debug("ApplyForStudentNumberAction - populateSpecializations - After F915 ID Check");
					//log.debug("ApplyForStudentNumberAction - populateSpecializations - ========================================================================================");
					//log.debug("ApplyForStudentNumberAction - populateSpecializations - getOutGroupCount="+op.getOutGroupCount());
					
					//Are there Proxy Rows returned?
					if (op.getOutGroupCount() > 0){
						//Set Qualifications from Proxy
						//Get GENCOD Categories to filter list
						ArrayList<String> codList = dao.getGENCODCategory(selCategoryCode);
						
						for (int i = 0; i < op.getOutGroupCount(); i++){ 
							String SpecCode = "";
							String SpecDesc = "";
							
							//If in the correct Category
							if (selCategoryCode.equals(op.getOutGCsfStringsString3(i).toString())){ //Example 07 = 07
								for (int j = 0; j < codList.size(); j++){
									if (codList.get(j).toString().equals(op.getOutGCsfStringsString2(i).toString())  && selQualCode.toUpperCase().equalsIgnoreCase(op.getOutGWsQualificationCode(i).toString().toUpperCase().trim())){
										//Get Specialization and Description
										isSpecListOK = true;
										if (" ".equals(op.getOutGWsQualificationSpecialityTypeSpecialityCode(i).toString().trim()) || "".equals(op.getOutGWsQualificationSpecialityTypeSpecialityCode(i).toString().trim())){
											SpecCode = "NVT";
											SpecDesc = "N/A - Not Applicable";
										}else{
											SpecCode = op.getOutGWsQualificationCode(i).toString();
											SpecDesc = dao.getSpecDesc(selQualCode, SpecCode);
										}
										codes.add(SpecCode);
										descs.add(SpecDesc);
									}
								}
							}
						}
					}
					op.clear();
				}else{ //No Matric Match, thus send in Matric Subjects to Proxy for APS Score test
					
					op.setInCsfClientServerCommunicationsClientVersionNumber((short) 3);
					op.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
					op.setInCsfClientServerCommunicationsAction("NL");
					op.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
					op.setInWsUserNumber(99998);
					op.setInWsStudentIdentityNr(stuRegForm.getStudent().getIdNumber());
					op.setInWsAcademicYearYear((short) Integer.parseInt(stuRegForm.getStudent().getAcademicYear()));
					
					//op.setInGcQualLevelCsfStringsString2(selCategoryCode); 
					op.setInGencodQualLevelCsfStringsString2(selCategoryCode);
						
					//log.debug("ApplyForStudentNumberAction - populateSpecializations - (Staae05sAppAdmissionEvaluator) - Student ID Number=" + stuRegForm.getStudent().getIdNumber());
	
					//Add Matric Subjects
					//log.debug("ApplyForStudentNumberAction - populateSpecializations - Subject Count="+stuRegForm.getSubjects().size());
					
					//Set In Group View
					op.setInGroupMatricCount((short) stuRegForm.getSubjects().size());
					
					if ("MATR".equalsIgnoreCase(stuRegForm.getSelectMatric()) || "SENR".equalsIgnoreCase(stuRegForm.getSelectMatric()) || "ONBK".equalsIgnoreCase(stuRegForm.getSelectMatric())){
						op.setInWsMatricCertificateCode("SC");
					}else{
						op.setInWsMatricCertificateCode("NSC");
					}
					op.setInWsMatricStatusCode(stuRegForm.getSelectMatric()); //MATR, DEGR etc
										
					for (int s = 0; s < stuRegForm.getSubjects().size(); s++){
						//log.debug("ApplyForStudentNumberAction - populateSpecializations - Check Subject"+s+"="+stuRegForm.getSubjects().get(s).getSubjectName()+", Grade="+stuRegForm.getSubjects().get(s).getSubjectGrade()+", Symbol="+stuRegForm.getSubjects().get(s).getSubjectSymbol()+", Result="+stuRegForm.getSubjects().get(s).getSubjectResult());
						op.setInGmWsMatricSubjectCode(s, stuRegForm.getSubjects().get(s).getSubjectName());
						
						//Send in Lowest Score. Example: A 80-100, use 80%
						if ("MATR".equalsIgnoreCase(stuRegForm.getSelectMatric()) || "SENR".equalsIgnoreCase(stuRegForm.getSelectMatric()) || "ONBK".equalsIgnoreCase(stuRegForm.getSelectMatric())){
							
							int subMark = 0;
							if ("A".equalsIgnoreCase(stuRegForm.getSubjects().get(s).getSubjectSymbol())){
								subMark = 80;
							}else if ("B".equalsIgnoreCase(stuRegForm.getSubjects().get(s).getSubjectSymbol())){
								subMark = 70;
							}else if ("C".equalsIgnoreCase(stuRegForm.getSubjects().get(s).getSubjectSymbol())){
								subMark = 60;
							}else if ("D".equalsIgnoreCase(stuRegForm.getSubjects().get(s).getSubjectSymbol())){
								subMark = 50;
							}else if ("E".equalsIgnoreCase(stuRegForm.getSubjects().get(s).getSubjectSymbol())){
								subMark = 40;
							}else if ("F".equalsIgnoreCase(stuRegForm.getSubjects().get(s).getSubjectSymbol())){
								subMark = 33;
							}else if ("G".equalsIgnoreCase(stuRegForm.getSubjects().get(s).getSubjectSymbol())){
								subMark = 0;
							}
							op.setInGmWsMatricSubjectResultMark(s, (short) subMark);
							
							String subGrade = "L";
							if ("HG".equalsIgnoreCase(stuRegForm.getSubjects().get(s).getSubjectGrade())){
								subGrade = "H";
							}else if ("SG".equalsIgnoreCase(stuRegForm.getSubjects().get(s).getSubjectGrade())){
								subGrade = "S";
							}else if ("LG".equalsIgnoreCase(stuRegForm.getSubjects().get(s).getSubjectGrade())){
								subGrade = "L";
							}
							op.setInGmWsMatricSubjectResultGrade(s, subGrade);
						}else{
							int subMark = 0;
							if ("A".equalsIgnoreCase(stuRegForm.getSubjects().get(s).getSubjectSymbol())){
								subMark = 80;
							}else if ("B".equalsIgnoreCase(stuRegForm.getSubjects().get(s).getSubjectSymbol())){
								subMark = 70;
							}else if ("C".equalsIgnoreCase(stuRegForm.getSubjects().get(s).getSubjectSymbol())){
								subMark = 60;
							}else if ("D".equalsIgnoreCase(stuRegForm.getSubjects().get(s).getSubjectSymbol())){
								subMark = 50;
							}else if ("E".equalsIgnoreCase(stuRegForm.getSubjects().get(s).getSubjectSymbol())){
								subMark = 40;
							}else if ("F".equalsIgnoreCase(stuRegForm.getSubjects().get(s).getSubjectSymbol())){
								subMark = 30;
							}else if ("G".equalsIgnoreCase(stuRegForm.getSubjects().get(s).getSubjectSymbol())){
								subMark = 0;
							}
							op.setInGmWsMatricSubjectResultMark(s, (short) subMark);
						}					
					}
					op.execute();
	
					if (opl.getException() != null) throw opl.getException();
					if (op.getExitStateType() < 3) throw new Exception(op.getExitStateMsg());
	
					//log.debug("ApplyForStudentNumberAction - populateSpecializations - After APS Execute");
					String opResult = op.getOutCsfStringsString500();
					//log.debug("ApplyForStudentNumberAction - populateSpecializations - opResult: " + opResult);
					
					//log.debug("ApplyForStudentNumberAction - populateSpecializations - Staae05sAppAdmissionEvaluator - (Matric) = getOutCsfStringsString500="+op.getOutCsfStringsString500());
					
					//log.debug("ApplyForStudentNumberAction - populateSpecializations - After F915 ID Check");
					//log.debug("ApplyForStudentNumberAction - populateSpecializations - ========================================================================================");
					//log.debug("ApplyForStudentNumberAction - populateSpecializations - getOutGroupCount="+op.getOutGroupCount());
					
					//Are there Proxy Rows returned?
					if (op.getOutGroupCount() > 0){
						//Set Qualifications from Proxy
						//Get GENCOD Categories to filter list
						ArrayList<String> codList = dao.getGENCODCategory(selCategoryCode);
						
						for (int i = 0; i < op.getOutGroupCount(); i++){ 
							String SpecCode = "";
							String SpecDesc = "";
							
							//If in the correct Category
							if (selCategoryCode.equals(op.getOutGCsfStringsString3(i).toString())){ //Example 07 = 07
								for (int j = 0; j < codList.size(); j++){
									if (codList.get(j).toString().equals(op.getOutGCsfStringsString2(i).toString())  && selQualCode.toUpperCase().equalsIgnoreCase(op.getOutGWsQualificationCode(i).toString().toUpperCase().trim())){
										//Get Specialization and Description
										isSpecListOK = true;
										if (" ".equals(op.getOutGWsQualificationSpecialityTypeSpecialityCode(i).toString().trim()) || "".equals(op.getOutGWsQualificationSpecialityTypeSpecialityCode(i).toString().trim())){
											SpecCode = "NVT";
											SpecDesc = "N/A - Not Applicable";
										}else{
											SpecCode = op.getOutGWsQualificationCode(i).toString();
											SpecDesc = dao.getSpecDesc(selQualCode, SpecCode);
										}
										codes.add(SpecCode);
										descs.add(SpecDesc);
									}
								}
							}
						}
					}
				}
			}
			if (!isSpecListOK || codes.isEmpty()){ //Catch-all: If no specializations from proxy, then give list anyway as not to turn students away - Claudette
				//log.debug("populateSpecializations - selQualCode: " + selQualCode + " - acaYear: " + stuRegForm.getStudent().getAcademicYear());
				specializations = dao.getSpecializations(selQualCode,stuRegForm.getStudent().getAcademicYear(),stuRegForm.getStudent().getAcademicPeriod());

				codes = specializations.getSpecCodes();
				descs = specializations.getSpecDescs();
			}			
			String jsonString = generateSortedString(codes, descs);
			//log.debug(jsonString);
			PrintWriter out = response.getWriter();
			out.write(jsonString);
			out.flush();
		}catch(Exception ex){
			log.warn("ApplyForStudentNumberAction populateCategories - Error="+ex);
			messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", "An Error occurred populating Specialisations. Please log on again to retry."));
			addErrors(request, messages);
			if (stuRegForm.getAdminStaff().isAdmin()){
				if(stuRegForm.getLoginSelectYesNo().equalsIgnoreCase("NO")){
					stuRegForm.setWebLoginMsg("Administrator - First-time applicant");
					stuRegForm.setWebLoginMsg2("");
					try {
						setDropdownListsLogin(request,stuRegForm);
					} catch (Exception e) {
						
						e.printStackTrace();
					}
					setDropdownListsLogin(request,stuRegForm);
					return mapping.findForward("applyLogin");
				}else{
					stuRegForm.setWebLoginMsg("Administrator - Returning applicant");
					stuRegForm.setWebLoginMsg2("");
					try {
						setDropdownListsLogin(request,stuRegForm);
					} catch (Exception e) {
						
						e.printStackTrace();
					}
					setDropdownListsLogin(request,stuRegForm);
					return mapping.findForward("applyLogin");
				}
			}else{
				if(stuRegForm.getLoginSelectYesNo().equalsIgnoreCase("NO")){
					stuRegForm.setWebLoginMsg("First-time applicant");
					stuRegForm.setWebLoginMsg2("");
					try {
						setDropdownListsLogin(request,stuRegForm);
					} catch (Exception e) {
						
						e.printStackTrace();
					}
					setDropdownListsLogin(request,stuRegForm);
					return mapping.findForward("applyLogin");
				}else{
					stuRegForm.setWebLoginMsg("Returning applicant");
					stuRegForm.setWebLoginMsg2("");
					try {
						setDropdownListsLogin(request,stuRegForm);
					} catch (Exception e) {
						
						e.printStackTrace();
					}
					setDropdownListsLogin(request,stuRegForm);
					return mapping.findForward("applyLogin");
				}
			}
		}
		return null;
	}

	public ActionForward stepPrevQual(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		//log.debug("ApplyForStudentNumberAction - stepPrevQual - Start");
		
		ActionMessages messages = new ActionMessages();
		StudentRegistrationForm stuRegForm = (StudentRegistrationForm) form;
		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
		
		try{
			
			ArrayList<HistoryArray> qualList = new ArrayList<HistoryArray>();
			
			//Start at 2 because that is how the tabs on the space are numbered, so easier to debug
			//log.debug("ApplyForStudentNumberAction - stepPrevQual - studentNr=" + stuRegForm.getStudent().getNumber()+", Do Set");
			//log.debug("ApplyForStudentNumberAction - stepPrevQual - studentNr=" + stuRegForm.getStudent().getNumber()+", Seq2="+stripXSS(request.getParameter("qualOther.historyOTHERSEQ2"), "Sequence2", "prevQual", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), false));
			boolean isQual = false;
			
			int seqCounter = 1; //Although the Tab starts with 2, the Database sequence will start at 1
			for (int i=2; i < 16; i++){ //Just use an arbitrary 15 as max qualifications. do not think that any student will ever reach it.
				HistoryArray qual = new HistoryArray();
				//log.debug("ApplyForStudentNumberAction - stepPrevQual - studentNr=" + stuRegForm.getStudent().getNumber()+", Check SEQ="+i);
				if (request.getParameter("qualOther.historyOTHERQual"+i) != null && !"".equals(request.getParameter("qualOther.historyOTHERQual"+i).trim())){
					isQual = true;
					//log.debug("ApplyForStudentNumberAction - stepPrevQual - studentNr=" + stuRegForm.getStudent().getNumber()+", Do Set No="+i+", Data Found");
					
					qual.setOtherSEQ(seqCounter);
					//log.debug("ApplyForStudentNumberAction - stepPrevQual - studentNr=" + stuRegForm.getStudent().getNumber()+", Do Set No="+i+", getOtherSEQ="+qual.getOtherSEQ());
					
					qual.setOtherUniv(stripXSS(request.getParameter("qualOther.historyOTHERUniv"+i).trim(), "OtherUniv"+i, "prevQual", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
					//log.debug("ApplyForStudentNumberAction - stepPrevQual - studentNr=" + stuRegForm.getStudent().getNumber()+", Do Set No="+i+", getOtherUniv="+qual.getOtherUniv());

					if ("OTHR".equals(stripXSS(request.getParameter("qualOther.historyOTHERUniv"+i).trim(), "OtherUniv"+i, "prevQual", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), false)) || !"1015".equals(stripXSS(request.getParameter("qualOther.historyOTHERCountry"+i).trim(), "OtherCountry"+i, "prevQual", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), false))){
						qual.setOtherUnivText(stripXSS(request.getParameter("qualOther.historyOTHERUnivText"+i).trim(), "OtherUnivText"+i, "prevQual", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
					}else{
						qual.setOtherUnivText(" ");
					}
					//log.debug("ApplyForStudentNumberAction - stepPrevQual - studentNr=" + stuRegForm.getStudent().getNumber()+", Do Set No="+i+", getOtherUnivText="+qual.getOtherUnivText());
					
					qual.setOtherStudnr(stripXSS(request.getParameter("qualOther.historyOTHERStudnr"+i).trim(), "OtherStudnr"+i, "prevQual", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
					//log.debug("ApplyForStudentNumberAction - stepPrevQual - studentNr=" + stuRegForm.getStudent().getNumber()+", Do Set No="+i+", getOtherStudnr="+qual.getOtherStudnr());
					
					qual.setOtherQual(stripXSS(request.getParameter("qualOther.historyOTHERQual"+i).trim(), "OtherQual"+i, "prevQual", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
					//log.debug("ApplyForStudentNumberAction - stepPrevQual - studentNr=" + stuRegForm.getStudent().getNumber()+", Do Set No="+i+", getOtherQual="+qual.getOtherQual());
					
					qual.setOtherYearStart(stripXSS(request.getParameter("qualOther.historyOTHERYearStart"+i).trim(), "OtherYearStart"+i, "prevQual", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
					//log.debug("ApplyForStudentNumberAction - stepPrevQual - studentNr=" + stuRegForm.getStudent().getNumber()+", Do Set No="+i+", getOtherYearStart="+qual.getOtherYearStart());
					
					qual.setOtherYearEnd(stripXSS(request.getParameter("qualOther.historyOTHERYearEnd"+i).trim(), "OtherYearEnd"+i, "prevQual", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
					//log.debug("ApplyForStudentNumberAction - stepPrevQual - studentNr=" + stuRegForm.getStudent().getNumber()+", Do Set No="+i+", getOtherYearEnd="+qual.getOtherYearEnd());
					
					if (Integer.parseInt(qual.getOtherYearStart().trim()) > Integer.parseInt(qual.getOtherYearEnd())){
						//log.debug("ApplyForStudentNumberAction - stepPrevQual - Invalid End Date");
						messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "The Qualification cannot End before the Start date."));
						addErrors(request, messages);
						setDropdownListsPrev(request, form);
						return mapping.findForward("applyPrevQual");
					}
					
					qual.setOtherCountry(stripXSS(request.getParameter("qualOther.historyOTHERCountry"+i).trim(), "OtherCountry"+i, "prevQual", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
					//log.debug("ApplyForStudentNumberAction - stepPrevQual - studentNr=" + stuRegForm.getStudent().getNumber()+", Do Set No="+i+", getOtherCountry="+qual.getOtherCountry());
					
					if ("1015".equals(qual.getOtherCountry())){
						qual.setOtherForeign("N");
					}else{
						qual.setOtherForeign("Y");
					}
					//log.debug("ApplyForStudentNumberAction - stepPrevQual - studentNr=" + stuRegForm.getStudent().getNumber()+", Do Set No="+i+", getOtherForeign="+qual.getOtherForeign());
					
					String lock = stripXSS(request.getParameter("qualOther.historyOTHERLock"+i).trim(), "OtherOTHERLock"+i, "prevQual", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), false);
					if (lock == null || "".equals(lock) || "undefined".equalsIgnoreCase(lock)){
						qual.setOtherLock("N");
					}else{
						qual.setOtherLock(lock);
					}
					//log.debug("ApplyForStudentNumberAction - stepPrevQual - studentNr=" + stuRegForm.getStudent().getNumber()+", Do Set No="+i+", getOtherLock="+qual.getOtherLock());

					if (request.getParameter("qualOther.historyOTHERComplete"+i) == null){
						String complete = stripXSS(request.getParameter("qualOther.historyOTHERHiddenComplete"+i).trim(), "OtherHiddenComplete"+i, "prevQual", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true);
						if (complete == null || "".equals(complete) || "undefined".equalsIgnoreCase(complete)){
							qual.setOtherComplete("N");
						}else{
							qual.setOtherComplete(complete);
						}	
					}else{
						String complete = stripXSS(request.getParameter("qualOther.historyOTHERComplete"+i).trim(), "OtherComplete"+i, "prevQual", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true);
						if (complete == null || "".equals(complete) || "undefined".equalsIgnoreCase(complete)){
							qual.setOtherComplete("N");
						}else{
							qual.setOtherComplete(complete);
						}
					}
					//log.debug("ApplyForStudentNumberAction - stepPrevQual - studentNr=" + stuRegForm.getStudent().getNumber()+", Do Set No="+i+", getOtherComplete="+qual.getOtherComplete());
					
					seqCounter++;
					qualList.add(qual);
				}
			}
			stuRegForm.setQualArray(qualList);
			
			if (!isQual && !stuRegForm.isQualUnisaFound()){
				//log.debug("ApplyForStudentNumberAction - stepPrevQual - historyOTHERUniv2 Empty");
				messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please Enter at least one completed degree or diploma"));
				addErrors(request, messages);
				setDropdownListsPrev(request, form);
				return mapping.findForward("applyPrevQual");
			}
			
		}catch(Exception ex){
			log.warn("ApplyForStudentNumberAction - stepPrevQual - Error="+ex);
			messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", "An Error occurred while processing Previous Qualifications. Please try again."));
			addErrors(request, messages);
				return mapping.findForward("applyQualification");
		}
		stuRegForm.getStudentApplication().setNdpRegList(dao.getNDPList(stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getStudent().getNumber()));
		return mapping.findForward("applyNDP");
	}

	
	/*
	 * populates the Institutions dropdown. Uses JSON to return Key and Value list
	 */

	public ActionForward populateUniv(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		//log.debug("ApplyForStudentNumberAction - populateUniv - Start");

		JSONObject univObj = new JSONObject();
		Map<String, String> mapUniversities = new LinkedHashMap<String, String>();
		int keyCounter = 0;
		
		//log.debug("ApplyForStudentNumberAction - populateUniv - **************************************************************");

		// Query database
		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
			
			try{
				ArrayList<KeyValue> getValues = dao.getInstitutions();
				Iterator<KeyValue> it = getValues.iterator();
			
		    	while(it.hasNext()){
		    		KeyValue univ = (KeyValue) it.next();
		    		mapUniversities.put(Integer.toString(keyCounter), univ.getKey()+"~"+univ.getValue());
		    		keyCounter++;
		    	}
		    	univObj.put("Universities",mapUniversities);
	        }catch(Exception ex){
	        	univObj.put("Error","The populateUniv retrieval Failed! Please try again.");
	        }
		
		// Convert the map to JSON
		PrintWriter out = response.getWriter();
       	JSONObject jsonObject = JSONObject.fromObject(univObj);
       	//log.debug("ApplyForStudentNumberAction - populateUniv - Final - **************************************************************");
       	//log.debug("ApplyForStudentNumberAction - populateUniv - Final - jsonObject="+jsonObject.toString());
       	//log.debug("ApplyForStudentNumberAction - populateUniv - Final - **************************************************************");
       	out.write(jsonObject.toString());
       	out.flush();

		return null; //Returning null to prevent struts from interfering with Ajax/JSON
	}
	
	/*
	 * populates the NQF Level dropdown. Uses JSON to return Key and Value list
	 */
	public ActionForward populateNQF(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		//log.debug("ApplyForStudentNumberAction - populateNQF - Start");

		JSONObject nqfObj = new JSONObject();
		Map<String, String> mapNQF = new LinkedHashMap<String, String>();
		int keyCounter = 0;
		
		//log.debug("ApplyForStudentNumberAction - populateNQF - **************************************************************");

		try{
			int startNQF = 5;
			int endNQF = 10;
			for (int i = startNQF; i <= endNQF; i++){
	    		mapNQF.put(Integer.toString(keyCounter), Integer.toString(i)+"~NQF Level "+Integer.toString(i));
	    		keyCounter++;
			}
			nqfObj.put("NQF",mapNQF);
		}catch(Exception ex){
			nqfObj.put("Error","The populateNQF retrieval Failed! Please try again.");
		}
		
		// Convert the map to JSON
		PrintWriter out = response.getWriter();
       	JSONObject jsonObject = JSONObject.fromObject(nqfObj);
       	//log.debug("ApplyForStudentNumberAction - populateNQF - Final - **************************************************************");
       	//log.debug("ApplyForStudentNumberAction - populateNQF - Final - jsonObject="+jsonObject.toString());
       	//log.debug("ApplyForStudentNumberAction - populateNQF - Final - **************************************************************");
       	out.write(jsonObject.toString());
       	out.flush();

		return null; //Returning null to prevent struts from interfering with Ajax/JSON
	}
	
	/*
	 * populates the Year dropdown. Uses JSON to return Key and Value list
	 */
	public ActionForward populateYear(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		//log.debug("ApplyForStudentNumberAction - populateYear - Start");

		StudentRegistrationForm stuRegForm = (StudentRegistrationForm) form;
		JSONObject yearObj = new JSONObject();
		Map<String, String> mapYear = new LinkedHashMap<String, String>();
		int keyCounter = 0;
		
		//log.debug("ApplyForStudentNumberAction - populateYear - **************************************************************");

		try{
			int startYear = 1960; //Don't think student can be more than 100 years old
			
			//Ensure that End year is later than Start year
			String startYearSelected = stripXSS(request.getParameter("startYearSelected"), "startYearSelected", "popYear", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true);
			//log.debug("ApplyForStudentNumberAction - populateYear - startYearSelected="+startYearSelected);
	
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
       	//log.debug("ApplyForStudentNumberAction - populateYear - Final - **************************************************************");
       	//log.debug("ApplyForStudentNumberAction - populateYear - Final - jsonObject="+jsonObject.toString());
       	//log.debug("ApplyForStudentNumberAction - populateYear - Final - **************************************************************");
       	out.write(jsonObject.toString());
       	out.flush();

		return null; //Returning null to prevent struts from interfering with Ajax/JSON
	}
	
	/*
	 * populates the Country dropdown. Uses JSON to return Key and Value list
	 */
	public ActionForward populateCountry(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		//log.debug("ApplyForStudentNumberAction - populateCountry - Start");

		JSONObject countryObj = new JSONObject();
		Map<String, String> mapCountry = new LinkedHashMap<String, String>();
		int keyCounter = 0;
		
		//log.debug("ApplyForStudentNumberAction - populateCountry - **************************************************************");

		// Query database
		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
			
			try{
				ArrayList<KeyValue> getValues = dao.getCountriesList();
				Iterator<KeyValue> it = getValues.iterator();
			
		    	while(it.hasNext()){
		    		KeyValue country = (KeyValue) it.next();
		    		mapCountry.put(Integer.toString(keyCounter), country.getKey()+"~"+country.getValue());
		    		keyCounter++;
		    	}
		    	countryObj.put("COUNTRY",mapCountry);
	        }catch(Exception ex){
	        	countryObj.put("Error","The populateCountry retrieval Failed! Please try again.");
	        	//log.debug("ApplyForStudentNumberAction - populateCountry - Crashed / " + ex);
	        }
		
		// Convert the map to JSON
		PrintWriter out = response.getWriter();
       	JSONObject jsonObject = JSONObject.fromObject(countryObj);
       	//log.debug("ApplyForStudentNumberAction - populateCountry - Final - **************************************************************");
       	//log.debug("ApplyForStudentNumberAction - populateCountry - Final - jsonObject="+jsonObject.toString());
       	//log.debug("ApplyForStudentNumberAction - populateCountry - Final - **************************************************************");
       	out.write(jsonObject.toString());
       	out.flush();

		return null; //Returning null to prevent struts from interfering with Ajax/JSON
	}

	public ActionForward populateSTUPREV(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		//log.debug("ApplyForStudentNumberAction - populateSTUPREV - Start");

		StudentRegistrationForm stuRegForm = (StudentRegistrationForm) form;
		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
		
		JSONObject prevObj = new JSONObject();
		Map<String, String> mapPREV = new LinkedHashMap<String, String>();
		int keyCounter = 0;
		
		//log.debug("ApplyForStudentNumberAction - populateSTUPREV - **************************************************************");
			
			try{
				//Check if a Record for this year exists
				
				ArrayList<KeyValue> getValues = dao.getPREVADMNew(stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod());
				Iterator<KeyValue> it = getValues.iterator();
			
		    	while(it.hasNext()){
		    		KeyValue record = (KeyValue) it.next();
		    		mapPREV.put(Integer.toString(keyCounter), record.getKey()+"@"+record.getValue());
		    		//log.debug("ApplyForStudentNumberAction - populateSTUPREV - "+keyCounter+" - "+record.getKey()+"@"+record.getValue());
		    		keyCounter++;
		    	}
		    	if (keyCounter > 0){
		    		prevObj.put("PREVQUAL",mapPREV);
		    	}else{
		    		prevObj.put("Empty","No previous records found");
		    	}
		    	
	        }catch(Exception ex){
	        	prevObj.put("Error","The populateSTUPREV retrieval Failed! Please try again.");
	        }
		
		// Convert the map to JSON
		PrintWriter out = response.getWriter();
       	JSONObject jsonObject = JSONObject.fromObject(prevObj);
       	//log.debug("ApplyForStudentNumberAction - populateSTUPREV - Final - **************************************************************");
       	//log.debug("ApplyForStudentNumberAction - populateSTUPREV - Final - jsonObject="+jsonObject.toString());
       	//log.debug("ApplyForStudentNumberAction - populateSTUPREV - Final - **************************************************************");
       	out.write(jsonObject.toString());
       	out.flush();

		return null; //Returning null to prevent struts from interfering with Ajax/JSON
	}
	
	
	public String saveStudyRet(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		//log.debug("ApplyForStudentNumberAction - saveStudyRet - Start");
				
		ActionMessages messages = new ActionMessages();
		StudentRegistrationForm stuRegForm = (StudentRegistrationForm) form;
		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
		
		//Reset web messages
		stuRegForm.setWebUploadMsg("");
		stuRegForm.setWebLoginMsg("");
		stuRegForm.setWebLoginMsg2("");
		
		String queryResultCAT1 		= "";
		String queryResultCAT2 		= "";
		String queryResultQUAL1 	= "";
		String queryResultQUAL2 	= "";
		String queryResultSPEC1 	= "";
		String queryResultSPEC2 	= "";
		boolean isError = false;
			
		try{
			stuRegForm.getStudent().setCategory1(stripXSS(stuRegForm.getSelCategoryCode1(), "SelCategoryCode1", "setQualRet", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
			stuRegForm.getStudent().setCategory2(stripXSS(stuRegForm.getSelCategoryCode2(), "SelCategoryCode2", "setQualRet", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
			stuRegForm.getStudent().setQual1(stripXSS(stuRegForm.getSelQualCode1(), "SelQualCode1", "setQualRet", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
			stuRegForm.getStudent().setQual2(stripXSS(stuRegForm.getSelQualCode2(), "SelQualCode2", "setQualRet", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
			stuRegForm.getStudent().setSpec1(stripXSS(stuRegForm.getSelSpecCode1(), "SelSpecCode1", "setQualRet", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
			stuRegForm.getStudent().setSpec2(stripXSS(stuRegForm.getSelSpecCode2(), "SelSpecCode2", "setQualRet", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
					
			// Proposed Primary Specialization
			if (stuRegForm.getStudent().getSpec1() == null || "0".equals(stuRegForm.getStudent().getSpec1()) || "".equals(stuRegForm.getStudent().getSpec1()) || "NVT".equalsIgnoreCase(stuRegForm.getStudent().getSpec1()) || "undefined".equalsIgnoreCase(stuRegForm.getStudent().getSpec1())){
				stuRegForm.getStudent().setSpec1("0");
			}
			
			// Proposed Secondary Specialization
			if (stuRegForm.getStudent().getSpec2() == null || "0".equals(stuRegForm.getStudent().getSpec2()) || "".equals(stuRegForm.getStudent().getSpec2()) || "NVT".equalsIgnoreCase(stuRegForm.getStudent().getSpec2())  || "undefined".equalsIgnoreCase(stuRegForm.getStudent().getSpec2())){
				stuRegForm.getStudent().setSpec2("0");
			}
			
			stuRegForm.setSelQualCode1Desc(dao.getQualDesc(stuRegForm.getStudent().getQual1()));
			stuRegForm.setSelQualCode2Desc(dao.getQualDesc(stuRegForm.getStudent().getQual2()));
			stuRegForm.setSelSpecCode1Desc(dao.getSpecDesc(stuRegForm.getStudent().getQual1(), stuRegForm.getStudent().getSpec1()));
			stuRegForm.setSelSpecCode2Desc(dao.getSpecDesc(stuRegForm.getStudent().getQual2(), stuRegForm.getStudent().getSpec2()));

		
			//log.debug("ApplyForStudentNumberAction - saveStudyRet - " + stuRegForm.getStudent().getNumber() +" - CategoryCode1 = " + stuRegForm.getStudent().getCategory1());
			//log.debug("ApplyForStudentNumberAction - saveStudyRet - " + stuRegForm.getStudent().getNumber() +" - CategoryCode2 = " + stuRegForm.getStudent().getCategory2());
			//log.debug("ApplyForStudentNumberAction - saveStudyRet - " + stuRegForm.getStudent().getNumber() +" - QualCode1     = " + stuRegForm.getStudent().getQual1());
			//log.debug("ApplyForStudentNumberAction - saveStudyRet - " + stuRegForm.getStudent().getNumber() +" - QualCode2     = " + stuRegForm.getStudent().getQual2());
			//log.debug("ApplyForStudentNumberAction - saveStudyRet - " + stuRegForm.getStudent().getNumber() +" - SpecCode1     = " + stuRegForm.getStudent().getSpec1());
			//log.debug("ApplyForStudentNumberAction - saveStudyRet - " + stuRegForm.getStudent().getNumber() +" - SpecCode2     = " + stuRegForm.getStudent().getSpec2());
				
			if (stuRegForm.getStudent().getQual1().equalsIgnoreCase(stuRegForm.getStudent().getQual2())){
				//log.debug("ApplyForStudentNumberAction - saveStudyRet - Same Choice 1 & 2 - Qual1="+stuRegForm.getStudent().getQual1()+" = Qual2="+stuRegForm.getStudent().getQual2());
				messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "You have selected the same primary and alternative/secondary and qualification. Please change one application or remove it by setting its Category to 'Select Category' "));
				addErrors(request, messages);
				return "applyQualification";
			}

			if(stuRegForm.getStudent().getNumber() == null || "".equals(stuRegForm.getStudent().getNumber())){
				//log.debug("ApplyForStudentNumberAction - saveStudyRet - Temp Student Nr is null or empty when saving qualifications");
				messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "You performed and invalid action or a technical error has occurred. Please log on again to retry."));
				addErrors(request, messages);
				if (stuRegForm.getAdminStaff().isAdmin()){
					return "loginStaff";
				}else{
					return "loginStu";
				}
			}else{				
				queryResultCAT1 = doSTUXML(stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), "catCode1", "1", stuRegForm.getStudent().getCategory1(), "saveStudyRet");
				if (queryResultCAT1.toUpperCase().contains("ERROR")){
					isError = true;
				}
				queryResultCAT2 = doSTUXML(stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), "catCode2", "2", stuRegForm.getStudent().getCategory2(), "saveStudyRet");
				if (queryResultCAT2.toUpperCase().contains("ERROR")){
					isError = true;
				}

				if (!isError){
					queryResultQUAL1 = doSTUXML(stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), "qualCode1", "1", stuRegForm.getStudent().getQual1(), "saveStudyRet");
					if (queryResultQUAL1.toUpperCase().contains("ERROR")){
						isError = true;
					}
				}
				if (!isError){
					queryResultQUAL2 = doSTUXML(stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), "qualCode2", "2", stuRegForm.getStudent().getQual2(), "saveStudyRet");
					if (queryResultQUAL2.toUpperCase().contains("ERROR")){
						isError = true;
					}
				}
				
				if (!isError){
					queryResultSPEC1 = doSTUXML(stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), "specCode1", "1", stuRegForm.getStudent().getSpec1(), "saveStudyRet");
					if (queryResultSPEC1.toUpperCase().contains("ERROR")){
						isError = true;
					}
				}
				if (!isError){
					queryResultSPEC2 = doSTUXML(stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), "specCode2", "2", stuRegForm.getStudent().getSpec2(), "saveStudyRet");
					if (queryResultSPEC2.toUpperCase().contains("ERROR")){
						isError = true;
					}
				}
				
				//log.debug("ApplyForStudentNumberAction - saveStudyRet - N " + stuRegForm.getStudent().getNumber() + " Cat1 " + stuRegForm.getStudent().getCategory1() + " Qual1=" + stuRegForm.getStudent().getQual1() + " Spec1=" + stuRegForm.getStudent().getSpec1());
				//log.debug("ApplyForStudentNumberAction - saveStudyRet - N " + stuRegForm.getStudent().getNumber() + " Cat2 " + stuRegForm.getStudent().getCategory2() + " Qual2=" + stuRegForm.getStudent().getQual2() + " Spec2=" + stuRegForm.getStudent().getSpec2());
			}
		}catch(Exception ex){
			log.warn("ApplyForStudentNumberAction - saveStudyRet - N " + stuRegForm.getStudent().getNumber() + " Error="+ex);
			isError = true;
		}
		
		//Confirm New vs Previous Qualifications
		Qualifications prevQuals = dao.getPrevSTUACA(stuRegForm.getStudent().getNumber());
		for (int i = 0; i < prevQuals.getQualCodes().size(); i++){
			String qualCode = prevQuals.getQualCodes().get(i).toString().trim();
			String qualDesc = prevQuals.getQualDescs().get(i).toString().trim();
			String specCode = prevQuals.getSpecCodes().get(i).toString().trim();
			String specDesc = prevQuals.getSpecDescs().get(i).toString().trim();
			//Johanet 2018July BRD 5.2
			if (qualCode.equalsIgnoreCase(stuRegForm.getStudent().getQual1()) && specCode.equalsIgnoreCase(stuRegForm.getStudent().getSpec1())){
				//log.debug("ApplyForStudentNumberAction - saveStudyRet - Same Choice 1 Qual="+qualCode+" = Prev Qual1="+stuRegForm.getStudent().getQual1());
				messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "You have previously registered for your selected primary qualification ("+qualCode+" - "+qualDesc+") and specialisation ("+specCode+" - "+specDesc+"). You, therefore, do not have to re-apply for this qualification and specialisation."));
				addErrors(request, messages);
				return "applyQualification";

			}else if (qualCode.equalsIgnoreCase(stuRegForm.getStudent().getQual2()) && specCode.equalsIgnoreCase(stuRegForm.getStudent().getSpec2())){
				//log.debug("ApplyForStudentNumberAction - saveStudyRet - Same Choice 2 Qual="+qualCode+" = Prev Qual2="+stuRegForm.getStudent().getQual2());
				messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "You have previously registered for your selected alternative qualification ("+qualCode+" - "+qualDesc+") and specialisation ("+specCode+" - "+specDesc+"). You, therefore, do not have to re-apply for this qualification and specialisation."));
				addErrors(request, messages);
				return "applyQualification";
			}
			//END Johanet 2018July BRD 5.2
		
//			if (qualCode.equalsIgnoreCase(stuRegForm.getStudent().getQual1())){
//				//log.debug("ApplyForStudentNumberAction - saveStudyRet - Same Choice 1 Qual="+qualCode+" = Prev Qual1="+stuRegForm.getStudent().getQual1());
//				messages.add(ActionMessages.GLOBAL_MESSAGE,
//					new ActionMessage("message.generalmessage", "You have previously registered for your selected primary qualification "+qualCode+". You, therefore, do not have to re-apply for this qualification."));
//				addErrors(request, messages);
//				return "applyQualification";
//			}else if (qualCode.equalsIgnoreCase(stuRegForm.getStudent().getQual2())){
//				//log.debug("ApplyForStudentNumberAction - saveStudyRet - Same Choice 2 Qual="+qualCode+" = Prev Qual2="+stuRegForm.getStudent().getQual2());
//				messages.add(ActionMessages.GLOBAL_MESSAGE,
//					new ActionMessage("message.generalmessage", "You have previously registered for your selected alternative qualification ("+qualCode+" - "+qualDesc+") and specialisation ("+specCode+" - "+specDesc+"). You, therefore, do not have to re-apply for this qualification or specialisation."));
//				addErrors(request, messages);
//				return "applyQualification";
//			}
		}
		
		//Johanet 2018July BRD 5.2
		Qualifications completedQuals = dao.getCompletedQualifications(stuRegForm.getStudent().getNumber());
		for (int i = 0; i < completedQuals.getQualCodes().size(); i++){
			String qualCode = completedQuals.getQualCodes().get(i).toString().trim();
			String qualDesc = completedQuals.getQualDescs().get(i).toString().trim();		
			if (qualCode.equalsIgnoreCase(stuRegForm.getStudent().getQual1())){				
				//log.debug("ApplyForStudentNumberAction - saveStudyRet - Same Choice 1 Qual="+qualCode+" = Completed Qual1="+stuRegForm.getStudent().getQual1());
				messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "You have already completed your selected primary qualification ("+qualCode+" - "+qualDesc+"). You, therefore, cannot re-apply for this qualification."));
				addErrors(request, messages);
				return "applyQualification";
			}else if (qualCode.equalsIgnoreCase(stuRegForm.getStudent().getQual2())){
				//log.debug("ApplyForStudentNumberAction - saveStudyRet - Same Choice 2 Qual="+qualCode+" = Completed Qual2="+stuRegForm.getStudent().getQual2());
				messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "You have already completed your selected alternative qualification ("+qualCode+" - "+qualDesc+"). You, therefore, cannot re-apply for this qualification"));
				addErrors(request, messages);
				return "applyQualification";
			}
		}	
		//END Johanet 2018July BRD 5.2
		
		if (stuRegForm.getStudent().getQual1().equalsIgnoreCase(stuRegForm.getStudent().getQual2())){
			//log.debug("ApplyForStudentNumberAction - saveStudyRet - Same Choice 1 & 2 - Qual1="+stuRegForm.getStudent().getQual1()+" = Qual2="+stuRegForm.getStudent().getQual2());
			messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", "You have selected the same primary and alternative/secondary and qualification. Please change one application or remove it by setting its Category to 'Select Category' "));
			addErrors(request, messages);
			return "applyQualification";
		}
		
		if (isError){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", "An Error occurred Saving your Qualification selection. Please log on again to retry."));
			addErrors(request, messages);
			if (stuRegForm.getAdminStaff().isAdmin()){
				return "loginStaff";
			}else{
				return "loginStu";
			}
		}
		//log.debug("ApplyForStudentNumberAction - saveStudyRet - " + stuRegForm.getStudent().getNumber() +" - Done");
		return "applyQualificationConfirm";
	}

	public String saveStudyNew(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ActionMessages messages = new ActionMessages();
		StudentRegistrationForm stuRegForm = (StudentRegistrationForm) form;
		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
		
		//log.debug("ApplyForStudentNumberAction - saveStudyNew - " + stuRegForm.getStudent().getNumber() +" - Start");

		
		//Reset web messages
		stuRegForm.setWebUploadMsg("");
		stuRegForm.setWebLoginMsg("");
		stuRegForm.setWebLoginMsg2("");
		
		String queryResultCAT1 	= "";
		String queryResultCAT2 	= "";
		String queryResultQUAL1	= "";
		String queryResultQUAL2	= "";
		String queryResultSPEC1	= "";
		String queryResultSPEC2	= "";
		boolean isError = false;

		try{
			stuRegForm.getStudent().setCategory1(stripXSS(stuRegForm.getSelCategoryCode1(), "SelCategoryCode1", "setQualNew", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
			stuRegForm.getStudent().setCategory2(stripXSS(stuRegForm.getSelCategoryCode2(), "SelCategoryCode2", "setQualNew", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
			stuRegForm.getStudent().setQual1(stripXSS(stuRegForm.getSelQualCode1(), "SelQualCode1", "setQualNew", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
			stuRegForm.getStudent().setQual2(stripXSS(stuRegForm.getSelQualCode2(), "SelQualCode2", "setQualNew", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
			stuRegForm.getStudent().setSpec1(stripXSS(stuRegForm.getSelSpecCode1(), "SelSpecCode1", "setQualNew", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
			stuRegForm.getStudent().setSpec2(stripXSS(stuRegForm.getSelSpecCode2(), "SelSpecCode2", "setQualNew", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));

			// Proposed Primary Specialization
			if (stuRegForm.getStudent().getSpec1() == null || "0".equals(stuRegForm.getStudent().getSpec1()) || "".equals(stuRegForm.getStudent().getSpec1()) || "NVT".equalsIgnoreCase(stuRegForm.getStudent().getSpec1()) || "undefined".equalsIgnoreCase(stuRegForm.getStudent().getSpec1())){
				stuRegForm.getStudent().setSpec1("0");
			}
			
			// Proposed Secondary Specialization
			if (stuRegForm.getStudent().getSpec2() == null || "0".equals(stuRegForm.getStudent().getSpec2()) || "".equals(stuRegForm.getStudent().getSpec2()) || "NVT".equalsIgnoreCase(stuRegForm.getStudent().getSpec2())  || "undefined".equalsIgnoreCase(stuRegForm.getStudent().getSpec2())){
				stuRegForm.getStudent().setSpec2("0");
			}
			
			stuRegForm.setSelQualCode1Desc(dao.getQualDesc(stuRegForm.getStudent().getQual1()));
			stuRegForm.setSelQualCode2Desc(dao.getQualDesc(stuRegForm.getStudent().getQual2()));
			stuRegForm.setSelSpecCode1Desc(dao.getSpecDesc(stuRegForm.getStudent().getQual1(), stuRegForm.getStudent().getSpec1()));
			stuRegForm.setSelSpecCode2Desc(dao.getSpecDesc(stuRegForm.getStudent().getQual2(), stuRegForm.getStudent().getSpec2()));

		
			//log.debug("ApplyForStudentNumberAction - saveStudyNew - " + stuRegForm.getStudent().getNumber() +" - CategoryCode1 =" + stuRegForm.getStudent().getCategory1());
			//log.debug("ApplyForStudentNumberAction - saveStudyNew - " + stuRegForm.getStudent().getNumber() +" - CategoryCode2 =" + stuRegForm.getStudent().getCategory2());
			//log.debug("ApplyForStudentNumberAction - saveStudyNew - " + stuRegForm.getStudent().getNumber() +" - QualCode1     =" + stuRegForm.getStudent().getQual1() +" - "+stuRegForm.getSelQualCode1Desc());
			//log.debug("ApplyForStudentNumberAction - saveStudyNew - " + stuRegForm.getStudent().getNumber() +" - QualCode2     =" + stuRegForm.getStudent().getQual2() +" - "+stuRegForm.getSelQualCode2Desc());
			//log.debug("ApplyForStudentNumberAction - saveStudyNew - " + stuRegForm.getStudent().getNumber() +" - SpecCode1     =" + stuRegForm.getStudent().getSpec1() +" - "+stuRegForm.getSelSpecCode1Desc());
			//log.debug("ApplyForStudentNumberAction - saveStudyNew - " + stuRegForm.getStudent().getNumber() +" - SpecCode2     =" + stuRegForm.getStudent().getSpec2() +" - "+stuRegForm.getSelSpecCode2Desc());
			
			if(stuRegForm.getStudent().getNumber() == null || "".equals(stuRegForm.getStudent().getNumber())){
				//log.debug("ApplyForStudentNumberAction - saveStudyNew - Temp Student Nr is null or empty when saving qualifications");
				messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "You performed and invalid action or a technical error has occurred. Please log on again to retry."));
				addErrors(request, messages);
				if (stuRegForm.getAdminStaff().isAdmin()){
					return "loginStaff";
				}else{
					return "loginStu";
				}
			}else{
				//log.debug("ApplyForStudentNumberAction - saveStudyNew - " + stuRegForm.getStudent().getNumber() +" - CategoryCode1 =" + stuRegForm.getStudent().getCategory1());
				//log.debug("ApplyForStudentNumberAction - saveStudyNew - " + stuRegForm.getStudent().getNumber() +" - STUXML - Check Category1");
				queryResultCAT1 = doSTUXML(stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), "catCode1", "1", stuRegForm.getStudent().getCategory1(), "saveStudyNew");
				if (queryResultCAT1.toUpperCase().contains("ERROR")){
					isError = true;
				}
				//log.debug("ApplyForStudentNumberAction - saveStudyNew - " + stuRegForm.getStudent().getNumber() +" - STUXML - queryResultCAT1="+queryResultCAT1 + ", isError="+isError);
				
				if (!isError){
					//log.debug("ApplyForStudentNumberAction - saveStudyNew - " + stuRegForm.getStudent().getNumber() +" - STUXML - Check Category2");
					queryResultCAT2 = doSTUXML(stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), "catCode2", "2", stuRegForm.getStudent().getCategory2(), "saveStudyNew");
					if (queryResultCAT2.toUpperCase().contains("ERROR")){
						isError = true;
					}
					//log.debug("ApplyForStudentNumberAction - saveStudyNew - " + stuRegForm.getStudent().getNumber() +" - STUXML - queryResultCAT2="+queryResultCAT2 + ", isError="+isError);
					}
				}

				if (!isError){
					//log.debug("ApplyForStudentNumberAction - saveStudyNew - " + stuRegForm.getStudent().getNumber() +" - STUXML - Check Qualification1");
					queryResultQUAL1 = doSTUXML(stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), "qualCode1", "1", stuRegForm.getStudent().getQual1(), "saveStudyNew");
					if (queryResultQUAL1.toUpperCase().contains("ERROR")){
						isError = true;
					}
					//log.debug("ApplyForStudentNumberAction - saveStudyNew - " + stuRegForm.getStudent().getNumber() +" - STUXML - queryResultQUAL1="+queryResultQUAL1 + ", isError="+isError);
				}
				
				if (!isError){
					//log.debug("ApplyForStudentNumberAction - saveStudyNew - " + stuRegForm.getStudent().getNumber() +" - STUXML - Check Qualification2");
					queryResultQUAL2 = doSTUXML(stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), "qualCode2", "2", stuRegForm.getStudent().getQual2(), "saveStudyNew");
					if (queryResultQUAL2.toUpperCase().contains("ERROR")){
						isError = true;
					}
					//log.debug("ApplyForStudentNumberAction - saveStudyNew - " + stuRegForm.getStudent().getNumber() +" - STUXML - queryResultQUAL2="+queryResultQUAL2 + ", isError="+isError);
				}
				
				if (!isError){
					//log.debug("ApplyForStudentNumberAction - saveStudyNew - " + stuRegForm.getStudent().getNumber() +" - STUXML - Check Spesialisation1");
					queryResultSPEC1 = doSTUXML(stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), "specCode1", "1", stuRegForm.getStudent().getSpec1(), "saveStudyNew");
					if (queryResultSPEC1.toUpperCase().contains("ERROR")){
						isError = true;
					}
					//log.debug("ApplyForStudentNumberAction - saveStudyNew - " + stuRegForm.getStudent().getNumber() +" - STUXML - queryResultSPEC1="+queryResultSPEC1 + ", isError="+isError);
				}
				
				if (!isError){
					//log.debug("ApplyForStudentNumberAction - saveStudyNew - " + stuRegForm.getStudent().getNumber() +" - STUXML - Check Spesialisation2");
					queryResultSPEC2 = doSTUXML(stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), "specCode2", "2", stuRegForm.getStudent().getSpec2(), "saveStudyNew");
					if (queryResultSPEC2.toUpperCase().contains("ERROR")){
						isError = true;
					}
					//log.debug("ApplyForStudentNumberAction - saveStudyNew - " + stuRegForm.getStudent().getNumber() +" - STUXML - queryResultSPEC2="+queryResultSPEC2 + ", isError="+isError);
				}

				//log.debug("ApplyForStudentNumberAction - saveStudyNew - N " + stuRegForm.getStudent().getNumber() + " Cat1=" + stuRegForm.getStudent().getCategory1() + " Qual1=" + stuRegForm.getStudent().getQual1() + " Spec1=" + stuRegForm.getStudent().getSpec1());
				//log.debug("ApplyForStudentNumberAction - saveStudyNew - N " + stuRegForm.getStudent().getNumber() + " Cat2=" + stuRegForm.getStudent().getCategory2() + " Qual2=" + stuRegForm.getStudent().getQual2() + " Spec2=" + stuRegForm.getStudent().getSpec2());
			
		}catch(Exception ex){
			//log.debug("ApplyForStudentNumberAction - saveStudyNew - N " + stuRegForm.getStudent().getNumber() + " Error="+ex);
			log.warn("ApplyForStudentNumberAction - saveStudyNew - N " + stuRegForm.getStudent().getNumber() + " Error="+ex);
			isError = true;
		}
		
		if (stuRegForm.getStudent().getQual1().equalsIgnoreCase(stuRegForm.getStudent().getQual2())){
			//log.debug("ApplyForStudentNumberAction - saveStudyNew - Same Choice 1 & 2 - Qual1="+stuRegForm.getStudent().getQual1()+" = Qual2="+stuRegForm.getStudent().getQual2());
			messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", "You have selected the same primary and alternative/secondary and qualification. Please change one application or remove it by setting its Category to 'Select Category' "));
			addErrors(request, messages);
			return "applyQualification";
		}

		
		if (isError){
			//log.debug("ApplyForStudentNumberAction - saveStudyNew - " + stuRegForm.getStudent().getNumber() +" - Error");
			messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", "An Error occurred Saving your Qualification selection. Please log on again to retry."));
			addErrors(request, messages);
			if (stuRegForm.getAdminStaff().isAdmin()){
				return "loginStaff";
			}else{
				return "loginStu";
			}
		}
		//log.debug("ApplyForStudentNumberAction - saveStudyNew - Goto applyQualificationConfirm");
		return "applyQualificationConfirm";
	}
	
	public String doSTUXML(String StudentNr, String acaYear, String acaPeriod, String referenceType, String referenceValue, String referenceData, String callingMethod){
		
		String result = "";
		String dbMethod = "INSERT";
		boolean isError = false;
		boolean isSuccess = false;

		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
		
		result = dao.isSTUXML(StudentNr, acaYear, acaPeriod, referenceType, referenceValue, callingMethod);
		if (result.toUpperCase().contains("ERROR")){
			isError = true;
		}else if (result.toUpperCase().equalsIgnoreCase("TRUE")){
			dbMethod = "UPDATE";
		}
		//log.debug("ApplyForStudentNumberAction - doSTUXML - " + StudentNr +" - STUXML - queryResult="+result + ", queryType="+dbMethod+ ", isError="+isError+ ", callingMethod="+callingMethod);
		
		int count = 0;
		while (!isSuccess && count < 5 && !isError){
			isError = false;
			isSuccess = false;
			int nextRef = 0;
			String queryResultRef = dao.getSTUXMLRef(StudentNr, acaYear, acaPeriod, referenceType, referenceValue, "doSTUXML");
			//log.debug("ApplyForStudentNumberAction - doSTUXML - queryResultRef="+queryResultRef + " / " + StudentNr+", "+acaYear+", "+acaPeriod+", "+referenceType+", "+referenceValue+", doSTUXML - Count="+count+", NextRef="+nextRef);
			if (queryResultRef.toUpperCase().contains("ERROR")){
				//log.debug("ApplyForStudentNumberAction - doSTUXML - queryResultRef Returned an Error");
				isError = true;
			}
			if (!isError){
				try{
					nextRef = Integer.parseInt(queryResultRef);
					nextRef++;
					//log.debug("ApplyForStudentNumberAction - doSTUXML - Calling saveSTUXML="+queryResultRef+", referenceType="+referenceType+", referenceValue="+referenceValue+", nextRef="+nextRef+". referenceData="+referenceData);
					result = dao.saveSTUXML(StudentNr, acaYear, acaPeriod, referenceType, referenceValue, nextRef, referenceData, callingMethod, dbMethod);
					//log.debug("ApplyForStudentNumberAction - doSTUXML - After Calling saveSTUXML- result="+result);
					if (result.equalsIgnoreCase("TRUE")){
						//log.debug("ApplyForStudentNumberAction - doSTUXML - saveSTUXML Returned OK");
						isSuccess = true;
					}else if (result.toUpperCase().contains("ERROR")){
						//log.debug("ApplyForStudentNumberAction - doSTUXML - saveSTUXML Returned an Error");
						isError = true;
					}
				}catch(Exception e){
					log.warn("ApplyForStudentNumberAction - doSTUXML - nextRef not Numeric - nextRef="+nextRef);
				}
			}
			count++;
			//log.debug("ApplyForStudentNumberAction - doSTUXML - " + StudentNr +" - STUXML - "+dbMethod+ "Result="+result + ", count="+count+ ", isError="+isError+ ", callingMethod="+callingMethod);
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

	public ActionForward submitNewQual(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// if student number is null, block application
		ActionMessages messages = new ActionMessages();
		StudentRegistrationForm stuRegForm = (StudentRegistrationForm) form;
		//log.debug("In submitNewQual");
		if(stuRegForm.getStudent().getNumber() == null || "".equals(stuRegForm.getStudent().getNumber())){
			//log.debug("ApplyForStudentNumberAction - submitNewQual - Student Nr is null or empty when saving qualifications");
			messages.add(ActionMessages.GLOBAL_MESSAGE,
			new ActionMessage("message.generalmessage", "You performed and invalid action or an Error occurred. Please log on again to retry."));
			addErrors(request, messages);
			if (stuRegForm.getAdminStaff().isAdmin()){
				return mapping.findForward("loginStaff");
			}else{
				return mapping.findForward("loginStu");
			}
		}
		//log.debug("ApplyForStudentNumberAction - submitNewQual studentNr: " + stuRegForm.getStudent().getNumber());
		//log.debug("ApplyForStudentNumberAction - submitNewQual acaYear: " + stuRegForm.getStudent().getAcademicYear());
		//log.debug("ApplyForStudentNumberAction - submitNewQual acaPeriod: " + stuRegForm.getStudent().getAcademicPeriod());
		//log.debug("ApplyForStudentNumberAction - submitNewQual stuExist: " + stuRegForm.getStudent().getStuExist());
		return mapping.findForward("applyQualification");
	}

	public ActionForward submitCngQual(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// if student number is null, block application
		ActionMessages messages = new ActionMessages();
		StudentRegistrationForm stuRegForm = (StudentRegistrationForm) form;
		if(stuRegForm.getStudent().getNumber() == null || "".equals(stuRegForm.getStudent().getNumber())){
			//log.debug("ApplyForStudentNumberAction - submitCngQual - Student Nr is null or empty when saving qualifications");
			messages.add(ActionMessages.GLOBAL_MESSAGE,
			new ActionMessage("message.generalmessage", "You performed and invalid action or an Error occurred. Please log on again to retry."));
			addErrors(request, messages);
			if (stuRegForm.getAdminStaff().isAdmin()){
				return mapping.findForward("loginStaff");
			}else{
				return mapping.findForward("loginStu");
			}
		}
		//log.debug("ApplyForStudentNumberAction - submitCngQual studentNr: " + stuRegForm.getStudent().getNumber());
		//log.debug("ApplyForStudentNumberAction - submitCngQual acaYear: " + stuRegForm.getStudent().getAcademicYear());
		//log.debug("ApplyForStudentNumberAction - submitCngQual acaPeriod: " + stuRegForm.getStudent().getAcademicPeriod());
		//log.debug("ApplyForStudentNumberAction - submitCngQual stuExist: " + stuRegForm.getStudent().getStuExist());
		return mapping.findForward("applyQualification");
	}

	public ActionForward submitReturnDoc(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// if student number is null, block application
		ActionMessages messages = new ActionMessages();
		StudentRegistrationForm stuRegForm = (StudentRegistrationForm) form;
		if(stuRegForm.getStudent().getNumber() == null || "".equals(stuRegForm.getStudent().getNumber())){
			//log.debug("ApplyForStudentNumberAction - submitReturnDoc - Student Nr is null or empty when saving qualifications");
			messages.add(ActionMessages.GLOBAL_MESSAGE,
			new ActionMessage("message.generalmessage", "You performed and invalid action or an Error occurred. Please log on again to retry."));
			addErrors(request, messages);
			if (stuRegForm.getAdminStaff().isAdmin()){
				return mapping.findForward("loginStaff");
			}else{
				return mapping.findForward("loginStu");
			}
		}
		//log.debug("ApplyForStudentNumberAction - submitReturnDoc studentNr: " + stuRegForm.getStudent().getNumber());
		//log.debug("ApplyForStudentNumberAction - submitReturnDoc acaYear: " + stuRegForm.getStudent().getAcademicYear());
		//log.debug("ApplyForStudentNumberAction - submitReturnDoc acaPeriod: " + stuRegForm.getStudent().getAcademicPeriod());
		//log.debug("ApplyForStudentNumberAction - submitReturnDoc stuExist: " + stuRegForm.getStudent().getStuExist());
		return mapping.findForward("dynamicUpload");
	}

	public ActionForward submitNewDoc(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// if student number is null, block application
		ActionMessages messages = new ActionMessages();
		StudentRegistrationForm stuRegForm = (StudentRegistrationForm) form;
		if(stuRegForm.getStudent().getNumber() == null || "".equals(stuRegForm.getStudent().getNumber())){
			//log.debug("ApplyForStudentNumberAction - submitNewDoc - Student Nr is null or empty when saving qualifications");
			messages.add(ActionMessages.GLOBAL_MESSAGE,
			new ActionMessage("message.generalmessage", "You performed and invalid action or an Error occurred. Please log on again to retry."));
			addErrors(request, messages);
			if (stuRegForm.getAdminStaff().isAdmin()){
				return mapping.findForward("loginStaff");
			}else{
				return mapping.findForward("loginStu");
			}
		}
		//log.debug("ApplyForStudentNumberAction - submitNewDoc studentNr: " + stuRegForm.getStudent().getNumber());
		//log.debug("ApplyForStudentNumberAction - submitNewDoc acaYear: " + stuRegForm.getStudent().getAcademicYear());
		//log.debug("ApplyForStudentNumberAction - submitNewDoc acaPeriod: " + stuRegForm.getStudent().getAcademicPeriod());
		//log.debug("ApplyForStudentNumberAction - submitNewDoc stuExist: " + stuRegForm.getStudent().getStuExist());
		return mapping.findForward("dynamicUpload");
	}

	@SuppressWarnings("unused")
	public ActionForward submitPay(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		StudentRegistrationForm stuRegForm = (StudentRegistrationForm) form;
		String button = stripXSS("submitPay", "submitPay", "goPay", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true);
		String serverpath = ServerConfigurationService.getServerUrl();
		return new ActionForward(serverpath+"/unisa-findtool/default.do?sharedTool=unisa.creditcardpayment",true);
	}

	@SuppressWarnings("unused")
	public ActionForward submitPayLater(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		StudentRegistrationForm stuRegForm = (StudentRegistrationForm) form;
		String button = stripXSS("submitPayLater", "submitPayLater", "goPayLater", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true);
		return new ActionForward("http://applications.unisa.ac.za/index.html",true);
	}
	
	@SuppressWarnings("unused")
	public String applyNewPersonal(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// if student number is null, block application
		ActionMessages messages = new ActionMessages();
		StudentRegistrationForm stuRegForm = (StudentRegistrationForm) form;
		if(stuRegForm.getStudent().getNumber() == null || "".equals(stuRegForm.getStudent().getNumber())){
			//log.debug("ApplyForStudentNumberAction -  applyNewPersonal - Student Nr is null or empty when saving qualifications");
			messages.add(ActionMessages.GLOBAL_MESSAGE,
			new ActionMessage("message.generalmessage", "You performed and invalid action or an Error occured. Please log on again to retry."));
			addErrors(request, messages);
			if (stuRegForm.getAdminStaff().isAdmin()){
				return "loginStaff";
			}else{
				return "loginStu";
			}
		}
		GeneralMethods gen = new GeneralMethods();
		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();

		if (stuRegForm.getFromPage() == null){
			//Error: Session empty
			//log.debug("ApplyForStudentNumberAction - applyNewPersonal Empty Session");
			return emptySession(mapping,form,request,response);
		}else{
			stuRegForm.setFromPage("page2");
		}

		stuRegForm.getSelQualCode();

		//log.debug("ApplyForStudentNumberAction - applyNewPersonal - " + stuRegForm.getStudent().getNumber() +" - CategoryCode1 =" + stuRegForm.getStudent().getCategory1());
		//log.debug("ApplyForStudentNumberAction - applyNewPersonal - " + stuRegForm.getStudent().getNumber() +" - CategoryCode2 =" + stuRegForm.getStudent().getCategory2());
		//log.debug("ApplyForStudentNumberAction - applyNewPersonal - " + stuRegForm.getStudent().getNumber() +" - QualCode1     =" + stuRegForm.getStudent().getQual1());
		//log.debug("ApplyForStudentNumberAction - applyNewPersonal - " + stuRegForm.getStudent().getNumber() +" - QualCode2     =" + stuRegForm.getStudent().getQual2());
		//log.debug("ApplyForStudentNumberAction - applyNewPersonal - " + stuRegForm.getStudent().getNumber() +" - SpecCode1     =" + stuRegForm.getStudent().getSpec1());
		//log.debug("ApplyForStudentNumberAction - applyNewPersonal - " + stuRegForm.getStudent().getNumber() +" - SpecCode2     =" + stuRegForm.getStudent().getSpec2());		

		// ---------- Check input
		String code1 = " ";
		String code2 = " ";
		String underpost1 = " ";
		String underpost2 = " ";
		String qtype1 = " ";
		String qtype2 = " ";
		String qkat1 = " ";
		String qkat2 = " ";

		// Closing date  --Integer.parseInt(String.valueOf(stuRegForm.getStudent().getAcademicYear())))){
		if (stuRegForm.getStudent().getQual1() != null && !stuRegForm.getStudent().getQual1().equalsIgnoreCase("") && !stuRegForm.getStudent().getQual1().equalsIgnoreCase("0") && !stuRegForm.getStudent().getQual1().equalsIgnoreCase("undefined")){
			//log.debug("ApplyForStudentNumberAction - applyNewPersonal - " + stuRegForm.getStudent().getNumber() +", GetUnderpostGrad1");
			code1 = dao.validateQualification(stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getQual1());
			underpost1 = code1.substring(0,1);
			qtype1 = code1.substring(1,2);
			qkat1 = code1.substring(2);
			stuRegForm.getQual().setUnderpostGrad1(underpost1);
		}else{
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "An Error occurred while processing your primary qualification selection. Please try again."));
			addErrors(request, messages);
			return "applyQualification";
		}

		if (stuRegForm.getStudent().getQual2() != null && !stuRegForm.getStudent().getQual2().equalsIgnoreCase("") && !stuRegForm.getStudent().getQual2().equalsIgnoreCase("0") && !stuRegForm.getStudent().getQual2().equalsIgnoreCase("undefined")){
			//log.debug("ApplyForStudentNumberAction - applyNewPersonal - " + stuRegForm.getStudent().getNumber() +", GetUnderpostGrad2");
			code2 = dao.validateQualification(stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getQual2());
			underpost2 = code2.substring(0,1);
			qtype2 = code2.substring(1,2);
			qkat2 = code2.substring(2);
			stuRegForm.getQual().setUnderpostGrad2(underpost2);
		}
		
		//log.debug("ApplyForStudentNumberAction - applyNewPersonal - getQual1 - code1: " + code1);
		//log.debug("ApplyForStudentNumberAction - applyNewPersonal - getQual1 - underpost1: " + underpost1);
		//log.debug("ApplyForStudentNumberAction - applyNewPersonal - getQual1 - qtype1: " + qtype1);
		//log.debug("ApplyForStudentNumberAction - applyNewPersonal - getQual1 - qkat1: " + qkat1);
		
		//log.debug("ApplyForStudentNumberAction - applyNewPersonal - getQual2 - code2: " + code2);
		//log.debug("ApplyForStudentNumberAction - applyNewPersonal - getQual2 - underpost2: " + underpost2);
		//log.debug("ApplyForStudentNumberAction - applyNewPersonal - getQual2 - qtype2: " + qtype2);
		//log.debug("ApplyForStudentNumberAction - applyNewPersonal - getQual2 - qkat2: " + qkat2);

		// Year
		if (stuRegForm.getStudent().getAcademicYear()== null || "".equals(stuRegForm.getStudent().getAcademicYear())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Academic year may not be empty"));
			addErrors(request, messages);
			setDropdownListsStep1(request,stuRegForm);
			return "applyNewPersonal";
		}else if (!gen.isNumeric(stuRegForm.getStudent().getAcademicYear())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
			new ActionMessage("message.generalmessage", "Academic year must be numeric format [CCYY]"));
			addErrors(request, messages);
			setDropdownListsStep1(request,stuRegForm);
			return "applyNewPersonal";
		}
		
		// Surname
		stuRegForm.getStudent().setSurname(stripXSS(stuRegForm.getStudent().getSurname(), "Surname", "NewPers", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
		if (stuRegForm.getStudent().getSurname()==null || "".equals(stuRegForm.getStudent().getSurname().trim())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Enter your surname."));
			addErrors(request, messages);
			setDropdownListsStep1(request,stuRegForm);
			return "applyNewPersonal";
		}
		// Initials
		stuRegForm.getStudent().setInitials(stripXSS(stuRegForm.getStudent().getInitials(), "Initials", "NewPers", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
		if (stuRegForm.getStudent().getInitials()==null || "".equals(stuRegForm.getStudent().getInitials().trim())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Enter your initials."));
			addErrors(request, messages);
			setDropdownListsStep1(request,stuRegForm);
			return "applyNewPersonal";
		}
 		if (!isInitialsGood(stuRegForm.getStudent().getInitials(), stuRegForm.getStudent().getFirstnames())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please put spaces between your initials."));
			addErrors(request, messages);
			setDropdownListsStep1(request,stuRegForm);
			return "applyNewPersonal";
		}
		// Title
		stuRegForm.getStudent().setTitle(stripXSS(stuRegForm.getStudent().getTitle(), "Title", "NewPers", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
		if (stuRegForm.getStudent().getTitle() == null || "-1".equals(stuRegForm.getStudent().getTitle())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Select your title."));
			addErrors(request, messages);
			setDropdownListsStep1(request,stuRegForm);
			return "applyNewPersonal";
		}
		// Full first names
		stuRegForm.getStudent().setFirstnames(stripXSS(stuRegForm.getStudent().getFirstnames(), "Firstnames", "NewPers", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
		if (stuRegForm.getStudent().getFirstnames()==null || "".equals(stuRegForm.getStudent().getFirstnames().trim())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Enter your first names."));
			addErrors(request, messages);
			setDropdownListsStep1(request,stuRegForm);
			return "applyNewPersonal";
		}
		// Date of birth
		String errorMessage = gen.validateDateOfBirth(stuRegForm.getStudent());
		if(!"".equals(errorMessage)){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage",
				errorMessage));
			addErrors(request, messages);
			setDropdownListsStep1(request,stuRegForm);
			return "applyNewPersonal";
		}
		Integer ayr = Integer.parseInt(String.valueOf(stuRegForm.getStudent().getAcademicYear()));
		Integer byr = Integer.parseInt(String.valueOf(stuRegForm.getStudent().getBirthYear()));
		Integer dfr = ayr - byr;
		if (dfr < 16 ){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Invalid birth date or applicant is not at a valid age to apply to study."));
			addErrors(request, messages);
			setDropdownListsStep1(request,stuRegForm);
			return "applyNewPersonal";
		}

		// RSA Id number/ passport number / foreign id number
		boolean err = false;
		stuRegForm.getStudent().setIdType(stripXSS(stuRegForm.getStudent().getIdType(), "IDType", "NewPers", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
		if ((stuRegForm.getStudent().getIdType()==null || "".equals(stuRegForm.getStudent().getIdType()))){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Indicate your type of identification."));
			addErrors(request, messages);
			setDropdownListsStep1(request,stuRegForm);
			return "applyNewPersonal";
		}else {
			// RSA id number
			stuRegForm.getStudent().setIdNumber(stripXSS(stuRegForm.getStudent().getIdNumber(), "IDNumber", "NewPers", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
			stuRegForm.getStudent().setPassportNumber(stripXSS(stuRegForm.getStudent().getPassportNumber(), "PassportNumber", "NewPers", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
			stuRegForm.getStudent().setForeignIdNumber(stripXSS(stuRegForm.getStudent().getForeignIdNumber(), "ForeignIDNumber", "NewPers", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));

			if("R".equals(stuRegForm.getStudent().getIdType())){
				if ( stuRegForm.getStudent().getIdNumber() == null && "".equals(stuRegForm.getStudent().getIdNumber().trim())){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Enter your RSA identity number."));
					addErrors(request, messages);
					setDropdownListsStep1(request,stuRegForm);
					return "applyNewPersonal";
				}else if (!gen.isNumeric(stuRegForm.getStudent().getIdNumber())){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "RSA identity number must be numeric."));
					addErrors(request, messages);
					setDropdownListsStep1(request,stuRegForm);
					return "applyNewPersonal";
				}else if( stuRegForm.getStudent().getIdNumber().trim().length()!= 13){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "RSA identity number must consist of 13 numerical characters."));
					addErrors(request, messages);
					setDropdownListsStep1(request,stuRegForm);
					return "applyNewPersonal";
				}else if (stuRegForm.getStudent().getPassportNumber()!=null && !"".equals(stuRegForm.getStudent().getPassportNumber().trim())){
					err = true;
				}else if (stuRegForm.getStudent().getForeignIdNumber()!=null && !"".equals(stuRegForm.getStudent().getForeignIdNumber().trim())){
					err = true;
				}
			// Foreign id number
			}else if("F".equals(stuRegForm.getStudent().getIdType())){
				if(stuRegForm.getStudent().getForeignIdNumber()==null || "".equals(stuRegForm.getStudent().getForeignIdNumber().trim())){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Enter your Foreign identity number."));
					addErrors(request, messages);
					setDropdownListsStep1(request,stuRegForm);
					return "applyNewPersonal";
				}else if (stuRegForm.getStudent().getPassportNumber()!=null && !"".equals(stuRegForm.getStudent().getPassportNumber().trim())){
					err = true;
				}else if (stuRegForm.getStudent().getIdNumber()!=null && !"".equals(stuRegForm.getStudent().getIdNumber().trim())){
					err = true;
				}
			// Passport number
			}else if("P".equals(stuRegForm.getStudent().getIdType())){
				if (stuRegForm.getStudent().getPassportNumber()==null || "".equals(stuRegForm.getStudent().getPassportNumber().trim())){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Enter your Passport number."));
					addErrors(request, messages);
					setDropdownListsStep1(request,stuRegForm);
					return "applyNewPersonal";
				}else if (stuRegForm.getStudent().getForeignIdNumber()!=null && !"".equals(stuRegForm.getStudent().getForeignIdNumber().trim())){
					err = true;
				}else if (stuRegForm.getStudent().getIdNumber()!=null && !"".equals(stuRegForm.getStudent().getIdNumber().trim())){
					err = true;
				}
			}
			if(err){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Indicate one type of identification only."));
				addErrors(request, messages);
				setDropdownListsStep1(request,stuRegForm);
				return "applyNewPersonal";
			}
		}
		//new - same as in F126
		String aa="";
		String bb="";
		if("R".equals(stuRegForm.getStudent().getIdType())){
			aa=stuRegForm.getStudent().getBirthYear().substring(2,4);
			bb=stuRegForm.getStudent().getIdNumber().substring(0,2);
	        if(!stuRegForm.getStudent().getBirthYear().substring(2,4).equalsIgnoreCase(stuRegForm.getStudent().getIdNumber().substring(0,2))){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Birth date does not correspond with id number."));
				addErrors(request, messages);
				setDropdownListsStep1(request,stuRegForm);
				return "applyNewPersonal";
	        }
	        bb=stuRegForm.getStudent().getIdNumber().substring(2,4);
	        if(!stuRegForm.getStudent().getBirthMonth().equalsIgnoreCase(stuRegForm.getStudent().getIdNumber().substring(2,4))){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Birth date does not correspond with id number."));
				addErrors(request, messages);
				setDropdownListsStep1(request,stuRegForm);
				return "applyNewPersonal";
	        }
	        bb=stuRegForm.getStudent().getIdNumber().substring(4,6);
	        if(!stuRegForm.getStudent().getBirthDay().equalsIgnoreCase(stuRegForm.getStudent().getIdNumber().substring(4,6))){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Birth date does not correspond with id number."));
				addErrors(request, messages);
				setDropdownListsStep1(request,stuRegForm);
				return "applyNewPersonal";
	        }
		}


		// gender
		stuRegForm.getStudent().setGender(stripXSS(stuRegForm.getStudent().getGender(), "Gender", "NewPers", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
		if (stuRegForm.getStudent().getGender()==null || "".equals(stuRegForm.getStudent().getGender().trim())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Indicate your gender."));
			addErrors(request, messages);
			setDropdownListsStep1(request,stuRegForm);
			return "applyNewPersonal";
		}
		// set disability
		stuRegForm.setSelectedDisability(stripXSS(stuRegForm.getSelectedDisability(), "Disability", "NewPers", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
		if (stuRegForm.getSelectedDisability() != null){
			GeneralItem genItem = new GeneralItem();
			genItem = getItem(stuRegForm.getSelectedDisability());
			stuRegForm.getStudent().getDisability().setCode(genItem.getCode());
			stuRegForm.getStudent().getDisability().setDesc(genItem.getDesc());
		}

		setDropdownListsStep2(request, stuRegForm);
		//log.debug("ApplyForStudentNumberAction - applyNewPersonal - GoTo applyNewContact");
		return "applyNewContact";
	}

	public ActionForward stepQualConfirm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		//log.debug("ApplyForStudentNumberaction - stepQualConfirm - Start");
		
		// if student number is null, block application
		ActionMessages messages = new ActionMessages();
		StudentRegistrationForm stuRegForm = (StudentRegistrationForm) form;
		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
		stuRegForm.setFromPage("stepQualConfirm");
		
		if(stuRegForm.getStudent().getNumber() == null || "".equals(stuRegForm.getStudent().getNumber())){
			//log.debug("ApplyForStudentNumberAction - stepQualConfirm - Student Nr is null or empty when saving qualifications");
			messages.add(ActionMessages.GLOBAL_MESSAGE,
			new ActionMessage("message.generalmessage", "You performed and invalid action or an Error occurred. Please log on again to retry."));
			addErrors(request, messages);
			if (stuRegForm.getAdminStaff().isAdmin()){
				return mapping.findForward("loginStaff");
			}else{
				return mapping.findForward("loginStu");
			}
		}
		
		//Flow to Honours screen for specific qualifications - Claudette 2018
		// continue from Name page next = address page or m&d page
		boolean isPrevQual1 = dao.getQualCategory(stuRegForm.getStudent().getQual1());
		boolean isPrevQual2 = false;
		if (stuRegForm.getStudent().getQual2() != null && !"".equals(stuRegForm.getStudent().getQual2())){
			isPrevQual2 = dao.getQualCategory(stuRegForm.getStudent().getQual2());
		}
		//log.debug("ApplyForStudentNumberAction - stepQualConfirm -  PrevQual1="+isPrevQual1+", PrevQual2="+isPrevQual2);
		if (isPrevQual1 || isPrevQual2){
			stuRegForm.setQualUnisa(dao.getQualsUnisa(stuRegForm.getStudent().getNumber()));
			if (stuRegForm.getQualUnisa().size() > 0){
				stuRegForm.setQualUnisaFound(true);
			}
			HistoryOther daoHistory = dao.getPREVADM(stuRegForm.getStudent().getNumber());
			if (daoHistory != null){
				//log.debug("ApplyForStudentNumberAction - stepQualConfirm -  isHistoryOTHERSEQ2="+daoHistory.getHistoryOTHERSEQ2());
				if (daoHistory.getHistoryOTHERSEQ2() != 0){
					//log.debug("ApplyForStudentNumberAction - stepQualConfirm -------------------------------------------------------");
					//log.debug("ApplyForStudentNumberAction - stepQualConfirm - IN isHistoryOTHERSEQ2");
					stuRegForm.getQualOther().setHistoryOTHERSEQ2(daoHistory.getHistoryOTHERSEQ2());
					if (" ".equals(daoHistory.getHistoryOTHERUniv2())){
						stuRegForm.getQualOther().setHistoryOTHERUniv2("OTHR");
					}else{
						stuRegForm.getQualOther().setHistoryOTHERUniv2(daoHistory.getHistoryOTHERUniv2().trim());
					}
					//log.debug("ApplyForStudentNumberAction - stepQualConfirm - IN HistoryOTHERUniv2="+stuRegForm.getQualOther().getHistoryOTHERUniv2());
					if (daoHistory.getHistoryOTHERUnivText2() != null && !"".equals(daoHistory.getHistoryOTHERUnivText2().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERUnivText2(daoHistory.getHistoryOTHERUnivText2().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERUnivText2(" ");
					}
					//log.debug("ApplyForStudentNumberAction - stepQualConfirm - IN HistoryOTHERUnivText2="+stuRegForm.getQualOther().getHistoryOTHERUnivText2());

					if (daoHistory.getHistoryOTHERStudnr2() != null && !"".equals(daoHistory.getHistoryOTHERStudnr2().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERStudnr2(daoHistory.getHistoryOTHERStudnr2().toString().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERStudnr2(" ");
					}
					//log.debug("ApplyForStudentNumberAction - stepQualConfirm - IN HistoryOTHERStudnr2="+stuRegForm.getQualOther().getHistoryOTHERStudnr2());

					if (daoHistory.getHistoryOTHERQual2() != null && !"".equals(daoHistory.getHistoryOTHERQual2().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERQual2(daoHistory.getHistoryOTHERQual2().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERQual2(" ");
					}
					//log.debug("ApplyForStudentNumberAction - stepQualConfirm - IN HistoryOTHERQual2="+stuRegForm.getQualOther().getHistoryOTHERQual2());
					if (daoHistory.getHistoryOTHERYearStart2() != null && !"".equals(daoHistory.getHistoryOTHERYearStart2().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERYearStart2(daoHistory.getHistoryOTHERYearStart2().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERYearStart2("-1");
					}
					//log.debug("ApplyForStudentNumberAction - stepQualConfirm - IN HistoryOTHERYearStart2="+stuRegForm.getQualOther().getHistoryOTHERYearStart2());
					if (daoHistory.getHistoryOTHERYearEnd2() != null && !"".equals(daoHistory.getHistoryOTHERYearEnd2().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERYearEnd2(daoHistory.getHistoryOTHERYearEnd2().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERYearEnd2("-1");
					}
					//log.debug("ApplyForStudentNumberAction - stepQualConfirm - IN HistoryOTHERYearEnd2="+stuRegForm.getQualOther().getHistoryOTHERYearEnd2());
					stuRegForm.getQualOther().setHistoryOTHERYearEndDB2(daoHistory.getHistoryOTHERYearEndDB2());
					if (daoHistory.getHistoryOTHERCountry2() != null && !"".equals(daoHistory.getHistoryOTHERCountry2().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERCountry2(daoHistory.getHistoryOTHERCountry2().toString().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERCountry2("-1");
					}
					//log.debug("ApplyForStudentNumberAction - stepQualConfirm - IN HistoryOTHERCountry2="+stuRegForm.getQualOther().getHistoryOTHERCountry2());
					if (daoHistory.getHistoryOTHERComplete2() != null && !"".equals(daoHistory.getHistoryOTHERComplete2().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERComplete2(daoHistory.getHistoryOTHERComplete2().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERComplete2("N");
					}
					stuRegForm.getQualOther().setHistoryOTHERHiddenComplete2(stuRegForm.getQualOther().getHistoryOTHERComplete2());
					//log.debug("ApplyForStudentNumberAction - stepQualConfirm - IN HistoryOTHERComplete2="+stuRegForm.getQualOther().getHistoryOTHERComplete2());
					if (daoHistory.getHistoryOTHERForeign2() != null && !"".equals(daoHistory.getHistoryOTHERForeign2().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERForeign2(daoHistory.getHistoryOTHERForeign2().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERForeign2("N");
					}
					//log.debug("ApplyForStudentNumberAction - stepQualConfirm - IN HistoryOTHERForeign2="+stuRegForm.getQualOther().getHistoryOTHERForeign2());
					if (daoHistory.getHistoryOTHERLock2() != null && !"".equals(daoHistory.getHistoryOTHERLock2().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERLock2(daoHistory.getHistoryOTHERLock2().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERLock2("N");
					}
					//log.debug("ApplyForStudentNumberAction - stepQualConfirm - IN HistoryOTHERLock2="+stuRegForm.getQualOther().getHistoryOTHERLock2());
					//log.debug("ApplyForStudentNumberAction - stepQualConfirm -------------------------------------------------------");
					
				}
				
				if (daoHistory.getHistoryOTHERSEQ3() != 0){
					stuRegForm.getQualOther().setHistoryOTHERSEQ3(daoHistory.getHistoryOTHERSEQ3());
					//log.debug("ApplyForStudentNumberAction - stepQualConfirm -------------------------------------------------------");
					//log.debug("ApplyForStudentNumberAction - stepQualConfirm - IN isHistoryOTHERSEQ3");
					if (" ".equals(daoHistory.getHistoryOTHERUniv3())){
						stuRegForm.getQualOther().setHistoryOTHERUniv3("OTHR");
					}else{
						stuRegForm.getQualOther().setHistoryOTHERUniv3(daoHistory.getHistoryOTHERUniv3().trim());
					}
					//log.debug("ApplyForStudentNumberAction - stepQualConfirm - IN HistoryOTHERUniv3="+stuRegForm.getQualOther().getHistoryOTHERUniv3());
					if (daoHistory.getHistoryOTHERUnivText3() != null && !"".equals(daoHistory.getHistoryOTHERUnivText3().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERUnivText3(daoHistory.getHistoryOTHERUnivText3().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERUnivText3(" ");
					}
					//log.debug("ApplyForStudentNumberAction - stepQualConfirm - IN HistoryOTHERUnivText3="+stuRegForm.getQualOther().getHistoryOTHERUnivText3());
					if (daoHistory.getHistoryOTHERStudnr3() != null && !"".equals(daoHistory.getHistoryOTHERStudnr3().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERStudnr3(daoHistory.getHistoryOTHERStudnr3().toString().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERStudnr3(" ");
					}
					//log.debug("ApplyForStudentNumberAction - stepQualConfirm - IN HistoryOTHERStudnr3="+stuRegForm.getQualOther().getHistoryOTHERStudnr3());
					if (daoHistory.getHistoryOTHERQual3() != null && !"".equals(daoHistory.getHistoryOTHERQual3().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERQual3(daoHistory.getHistoryOTHERQual3().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERQual3(" ");
					}
					//log.debug("ApplyForStudentNumberAction - stepQualConfirm - IN HistoryOTHERQual3="+stuRegForm.getQualOther().getHistoryOTHERQual3());
					if (daoHistory.getHistoryOTHERYearStart3() != null && !"".equals(daoHistory.getHistoryOTHERYearStart3().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERYearStart3(daoHistory.getHistoryOTHERYearStart3().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERYearStart3("-1");
					}
					//log.debug("ApplyForStudentNumberAction - stepQualConfirm - IN HistoryOTHERYearStart3="+stuRegForm.getQualOther().getHistoryOTHERYearStart3());
					if (daoHistory.getHistoryOTHERYearEnd3() != null && !"".equals(daoHistory.getHistoryOTHERYearEnd3().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERYearEnd3(daoHistory.getHistoryOTHERYearEnd3().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERYearEnd3("-1");
					}
					//log.debug("ApplyForStudentNumberAction - stepQualConfirm - IN HistoryOTHERYearEnd3="+stuRegForm.getQualOther().getHistoryOTHERYearEnd3());
					stuRegForm.getQualOther().setHistoryOTHERYearEndDB3(daoHistory.getHistoryOTHERYearEndDB3());
					if (daoHistory.getHistoryOTHERCountry3() != null && !"".equals(daoHistory.getHistoryOTHERCountry3().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERCountry3(daoHistory.getHistoryOTHERCountry3().toString().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERCountry3("-1");
					}
					//log.debug("ApplyForStudentNumberAction - stepQualConfirm - IN HistoryOTHERCountry3="+stuRegForm.getQualOther().getHistoryOTHERCountry3());
					if (daoHistory.getHistoryOTHERComplete3() != null && !"".equals(daoHistory.getHistoryOTHERComplete3().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERComplete3(daoHistory.getHistoryOTHERComplete3().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERComplete3("N");
					}
					stuRegForm.getQualOther().setHistoryOTHERHiddenComplete3(stuRegForm.getQualOther().getHistoryOTHERComplete3());
					//log.debug("ApplyForStudentNumberAction - stepQualConfirm - IN HistoryOTHERComplete3="+stuRegForm.getQualOther().getHistoryOTHERComplete3());
					if (daoHistory.getHistoryOTHERForeign3() != null && !"".equals(daoHistory.getHistoryOTHERForeign3().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERForeign3(daoHistory.getHistoryOTHERForeign3().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERForeign3("N");
					}
					//log.debug("ApplyForStudentNumberAction - stepQualConfirm - IN HistoryOTHERForeign3="+stuRegForm.getQualOther().getHistoryOTHERForeign3());
					if (daoHistory.getHistoryOTHERLock3() != null && !"".equals(daoHistory.getHistoryOTHERLock3().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERLock3(daoHistory.getHistoryOTHERLock3().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERLock3("N");
					}
					//log.debug("ApplyForStudentNumberAction - stepQualConfirm - IN HistoryOTHERLock3="+stuRegForm.getQualOther().getHistoryOTHERLock3());
					//log.debug("ApplyForStudentNumberAction - stepQualConfirm -------------------------------------------------------");
					
				}
				
				if (daoHistory.getHistoryOTHERSEQ4() != 0){
					stuRegForm.getQualOther().setHistoryOTHERSEQ4(daoHistory.getHistoryOTHERSEQ4());
					if (" ".equals(daoHistory.getHistoryOTHERUniv4())){
						stuRegForm.getQualOther().setHistoryOTHERUniv4("OTHR");
					}else{
						stuRegForm.getQualOther().setHistoryOTHERUniv4(daoHistory.getHistoryOTHERUniv4().trim());
					}
					if (daoHistory.getHistoryOTHERUnivText4() != null && !"".equals(daoHistory.getHistoryOTHERUnivText4().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERUnivText4(daoHistory.getHistoryOTHERUnivText4().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERUnivText4(" ");
					}
					if (daoHistory.getHistoryOTHERStudnr4() != null && !"".equals(daoHistory.getHistoryOTHERStudnr4().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERStudnr4(daoHistory.getHistoryOTHERStudnr4().toString().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERStudnr4(" ");
					}
					if (daoHistory.getHistoryOTHERQual4() != null && !"".equals(daoHistory.getHistoryOTHERQual4().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERQual4(daoHistory.getHistoryOTHERQual4().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERQual4(" ");
					}
					if (daoHistory.getHistoryOTHERYearStart4() != null && !"".equals(daoHistory.getHistoryOTHERYearStart4().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERYearStart4(daoHistory.getHistoryOTHERYearStart4().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERYearStart4("-1");
					}
					if (daoHistory.getHistoryOTHERYearEnd4() != null && !"".equals(daoHistory.getHistoryOTHERYearEnd4().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERYearEnd4(daoHistory.getHistoryOTHERYearEnd4().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERYearEnd4("-1");
					}
					stuRegForm.getQualOther().setHistoryOTHERYearEndDB4(daoHistory.getHistoryOTHERYearEndDB4());
					if (daoHistory.getHistoryOTHERCountry4() != null && !"".equals(daoHistory.getHistoryOTHERCountry4().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERCountry4(daoHistory.getHistoryOTHERCountry4().toString().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERCountry4("-1");
					}
					if (daoHistory.getHistoryOTHERComplete4() != null && !"".equals(daoHistory.getHistoryOTHERComplete4().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERComplete4(daoHistory.getHistoryOTHERComplete4().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERComplete4("N");
					}
					stuRegForm.getQualOther().setHistoryOTHERHiddenComplete4(stuRegForm.getQualOther().getHistoryOTHERComplete4());
					if (daoHistory.getHistoryOTHERForeign4() != null && !"".equals(daoHistory.getHistoryOTHERForeign4().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERForeign4(daoHistory.getHistoryOTHERForeign4().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERForeign4("N");
					}
					if (daoHistory.getHistoryOTHERLock4() != null && !"".equals(daoHistory.getHistoryOTHERLock4().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERLock4(daoHistory.getHistoryOTHERLock4().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERLock4("N");
					}
					
						
				}
				
				if (daoHistory.getHistoryOTHERSEQ5() != 0){
					stuRegForm.getQualOther().setHistoryOTHERSEQ5(daoHistory.getHistoryOTHERSEQ5());
					if (" ".equals(daoHistory.getHistoryOTHERUniv5())){
						stuRegForm.getQualOther().setHistoryOTHERUniv5("OTHR");
					}else{
						stuRegForm.getQualOther().setHistoryOTHERUniv5(daoHistory.getHistoryOTHERUniv5().trim());
					}
					if (daoHistory.getHistoryOTHERUnivText5() != null && !"".equals(daoHistory.getHistoryOTHERUnivText5().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERUnivText5(daoHistory.getHistoryOTHERUnivText5().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERUnivText5(" ");
					}
					if (daoHistory.getHistoryOTHERStudnr5() != null && !"".equals(daoHistory.getHistoryOTHERStudnr5().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERStudnr5(daoHistory.getHistoryOTHERStudnr5().toString().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERStudnr5(" ");
					}
					if (daoHistory.getHistoryOTHERQual5() != null && !"".equals(daoHistory.getHistoryOTHERQual5().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERQual5(daoHistory.getHistoryOTHERQual5().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERQual5(" ");
					}
					if (daoHistory.getHistoryOTHERYearStart5() != null && !"".equals(daoHistory.getHistoryOTHERYearStart5().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERYearStart5(daoHistory.getHistoryOTHERYearStart5().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERYearStart5("-1");
					}
					if (daoHistory.getHistoryOTHERYearEnd5() != null && !"".equals(daoHistory.getHistoryOTHERYearEnd5().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERYearEnd5(daoHistory.getHistoryOTHERYearEnd5().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERYearEnd5("-1");
					}
					stuRegForm.getQualOther().setHistoryOTHERYearEndDB5(daoHistory.getHistoryOTHERYearEndDB5());
					if (daoHistory.getHistoryOTHERCountry5() != null && !"".equals(daoHistory.getHistoryOTHERCountry5().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERCountry5(daoHistory.getHistoryOTHERCountry5().toString().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERCountry5("-1");
					}
					if (daoHistory.getHistoryOTHERComplete5() != null && !"".equals(daoHistory.getHistoryOTHERComplete5().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERComplete5(daoHistory.getHistoryOTHERComplete5().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERComplete5("N");
					}
					stuRegForm.getQualOther().setHistoryOTHERHiddenComplete5(stuRegForm.getQualOther().getHistoryOTHERComplete5());
					if (daoHistory.getHistoryOTHERForeign5() != null && !"".equals(daoHistory.getHistoryOTHERForeign5().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERForeign5(daoHistory.getHistoryOTHERForeign5().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERForeign5("N");
					}
					if (daoHistory.getHistoryOTHERLock5() != null && !"".equals(daoHistory.getHistoryOTHERLock5().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERLock5(daoHistory.getHistoryOTHERLock5().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERLock5("N");
					}
					
					
				}
				
				if (daoHistory.getHistoryOTHERSEQ6() != 0){
					stuRegForm.getQualOther().setHistoryOTHERSEQ6(daoHistory.getHistoryOTHERSEQ6());
					if (" ".equals(daoHistory.getHistoryOTHERUniv6())){
						stuRegForm.getQualOther().setHistoryOTHERUniv6("OTHR");
					}else{
						stuRegForm.getQualOther().setHistoryOTHERUniv6(daoHistory.getHistoryOTHERUniv6().trim());
					}
					if (daoHistory.getHistoryOTHERUnivText6() != null && !"".equals(daoHistory.getHistoryOTHERUnivText6().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERUnivText6(daoHistory.getHistoryOTHERUnivText6().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERUnivText6(" ");
					}
					if (daoHistory.getHistoryOTHERStudnr6() != null && !"".equals(daoHistory.getHistoryOTHERStudnr6().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERStudnr6(daoHistory.getHistoryOTHERStudnr6().toString().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERStudnr6(" ");
					}
					if (daoHistory.getHistoryOTHERQual6() != null && !"".equals(daoHistory.getHistoryOTHERQual6().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERQual6(daoHistory.getHistoryOTHERQual6().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERQual6(" ");
					}
					if (daoHistory.getHistoryOTHERYearStart6() != null && !"".equals(daoHistory.getHistoryOTHERYearStart6().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERYearStart6(daoHistory.getHistoryOTHERYearStart6().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERYearStart6("-1");
					}
					if (daoHistory.getHistoryOTHERYearEnd6() != null && !"".equals(daoHistory.getHistoryOTHERYearEnd6().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERYearEnd6(daoHistory.getHistoryOTHERYearEnd6().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERYearEnd6("-1");
					}
					stuRegForm.getQualOther().setHistoryOTHERYearEndDB6(daoHistory.getHistoryOTHERYearEndDB6());
					if (daoHistory.getHistoryOTHERCountry6() != null && !"".equals(daoHistory.getHistoryOTHERCountry6().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERCountry6(daoHistory.getHistoryOTHERCountry6().toString().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERCountry6("-1");
					}
					if (daoHistory.getHistoryOTHERComplete6() != null && !"".equals(daoHistory.getHistoryOTHERComplete6().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERComplete6(daoHistory.getHistoryOTHERComplete6().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERComplete6("N");
					}
					stuRegForm.getQualOther().setHistoryOTHERHiddenComplete6(stuRegForm.getQualOther().getHistoryOTHERComplete6());
					if (daoHistory.getHistoryOTHERForeign6() != null && !"".equals(daoHistory.getHistoryOTHERForeign6().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERForeign6(daoHistory.getHistoryOTHERForeign6().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERForeign6("N");
					}
					if (daoHistory.getHistoryOTHERLock6() != null && !"".equals(daoHistory.getHistoryOTHERLock6().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERLock6(daoHistory.getHistoryOTHERLock6().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERLock6("N");
					}
					
					
				}
				
				if (daoHistory.getHistoryOTHERSEQ7() != 0){
					stuRegForm.getQualOther().setHistoryOTHERSEQ7(daoHistory.getHistoryOTHERSEQ7());
					if (" ".equals(daoHistory.getHistoryOTHERUniv7())){
						stuRegForm.getQualOther().setHistoryOTHERUniv7("OTHR");
					}else{
						stuRegForm.getQualOther().setHistoryOTHERUniv7(daoHistory.getHistoryOTHERUniv7().trim());
					}
					if (daoHistory.getHistoryOTHERUnivText7() != null && !"".equals(daoHistory.getHistoryOTHERUnivText7().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERUnivText7(daoHistory.getHistoryOTHERUnivText7().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERUnivText7(" ");
					}
					if (daoHistory.getHistoryOTHERStudnr7() != null && !"".equals(daoHistory.getHistoryOTHERStudnr7().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERStudnr7(daoHistory.getHistoryOTHERStudnr7().toString().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERStudnr7(" ");
					}
					if (daoHistory.getHistoryOTHERQual7() != null && !"".equals(daoHistory.getHistoryOTHERQual7().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERQual7(daoHistory.getHistoryOTHERQual7().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERQual7(" ");
					}
					if (daoHistory.getHistoryOTHERYearStart7() != null && !"".equals(daoHistory.getHistoryOTHERYearStart7().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERYearStart7(daoHistory.getHistoryOTHERYearStart7().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERYearStart7("-1");
					}
					if (daoHistory.getHistoryOTHERYearEnd7() != null && !"".equals(daoHistory.getHistoryOTHERYearEnd7().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERYearEnd7(daoHistory.getHistoryOTHERYearEnd7().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERYearEnd7("-1");
					}
					stuRegForm.getQualOther().setHistoryOTHERYearEndDB7(daoHistory.getHistoryOTHERYearEndDB7());
					if (daoHistory.getHistoryOTHERCountry7() != null && !"".equals(daoHistory.getHistoryOTHERCountry7().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERCountry7(daoHistory.getHistoryOTHERCountry7().toString().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERCountry7("-1");
					}
					if (daoHistory.getHistoryOTHERComplete7() != null && !"".equals(daoHistory.getHistoryOTHERComplete7().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERComplete7(daoHistory.getHistoryOTHERComplete7().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERComplete7("N");
					}
					stuRegForm.getQualOther().setHistoryOTHERHiddenComplete7(stuRegForm.getQualOther().getHistoryOTHERComplete7());
					if (daoHistory.getHistoryOTHERForeign7() != null && !"".equals(daoHistory.getHistoryOTHERForeign7().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERForeign7(daoHistory.getHistoryOTHERForeign7().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERForeign7("N");
					}
					if (daoHistory.getHistoryOTHERLock7() != null && !"".equals(daoHistory.getHistoryOTHERLock7().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERLock7(daoHistory.getHistoryOTHERLock7().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERLock7("N");
					}
					
				}
				
				if (daoHistory.getHistoryOTHERSEQ8() != 0){
					stuRegForm.getQualOther().setHistoryOTHERSEQ8(daoHistory.getHistoryOTHERSEQ8());
					if (" ".equals(daoHistory.getHistoryOTHERUniv8())){
						stuRegForm.getQualOther().setHistoryOTHERUniv8("OTHR");
					}else{
						stuRegForm.getQualOther().setHistoryOTHERUniv8(daoHistory.getHistoryOTHERUniv8().trim());
					}
					if (daoHistory.getHistoryOTHERUnivText8() != null && !"".equals(daoHistory.getHistoryOTHERUnivText8().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERUnivText8(daoHistory.getHistoryOTHERUnivText8().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERUnivText8(" ");
					}
					if (daoHistory.getHistoryOTHERStudnr8() != null && !"".equals(daoHistory.getHistoryOTHERStudnr8().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERStudnr8(daoHistory.getHistoryOTHERStudnr8().toString().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERStudnr8(" ");
					}
					if (daoHistory.getHistoryOTHERQual8() != null && !"".equals(daoHistory.getHistoryOTHERQual8().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERQual8(daoHistory.getHistoryOTHERQual8().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERQual8(" ");
					}
					if (daoHistory.getHistoryOTHERYearStart8() != null && !"".equals(daoHistory.getHistoryOTHERYearStart8().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERYearStart8(daoHistory.getHistoryOTHERYearStart8().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERYearStart8("-1");
					}
					if (daoHistory.getHistoryOTHERYearEnd8() != null && !"".equals(daoHistory.getHistoryOTHERYearEnd8().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERYearEnd8(daoHistory.getHistoryOTHERYearEnd8().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERYearEnd8("-1");
					}
					stuRegForm.getQualOther().setHistoryOTHERYearEndDB8(daoHistory.getHistoryOTHERYearEndDB8());
					if (daoHistory.getHistoryOTHERCountry8() != null && !"".equals(daoHistory.getHistoryOTHERCountry8().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERCountry8(daoHistory.getHistoryOTHERCountry8().toString().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERCountry8("-1");
					}
					if (daoHistory.getHistoryOTHERComplete8() != null && !"".equals(daoHistory.getHistoryOTHERComplete8().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERComplete8(daoHistory.getHistoryOTHERComplete8().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERComplete8("N");
					}
					stuRegForm.getQualOther().setHistoryOTHERHiddenComplete8(stuRegForm.getQualOther().getHistoryOTHERComplete8());
					if (daoHistory.getHistoryOTHERForeign8() != null && !"".equals(daoHistory.getHistoryOTHERForeign8().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERForeign8(daoHistory.getHistoryOTHERForeign8().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERForeign8("N");
					}
					if (daoHistory.getHistoryOTHERLock8() != null && !"".equals(daoHistory.getHistoryOTHERLock8().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERLock8(daoHistory.getHistoryOTHERLock8().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERLock8("N");
					}
					
					
				}
				
				if (daoHistory.getHistoryOTHERSEQ9() != 0){
					stuRegForm.getQualOther().setHistoryOTHERSEQ9(daoHistory.getHistoryOTHERSEQ9());
					if (" ".equals(daoHistory.getHistoryOTHERUniv9())){
						stuRegForm.getQualOther().setHistoryOTHERUniv9("OTHR");
					}else{
						stuRegForm.getQualOther().setHistoryOTHERUniv9(daoHistory.getHistoryOTHERUniv9().trim());
					}
					if (daoHistory.getHistoryOTHERUnivText9() != null && !"".equals(daoHistory.getHistoryOTHERUnivText9().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERUnivText9(daoHistory.getHistoryOTHERUnivText9().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERUnivText9(" ");
					}
					if (daoHistory.getHistoryOTHERStudnr9() != null && !"".equals(daoHistory.getHistoryOTHERStudnr9().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERStudnr9(daoHistory.getHistoryOTHERStudnr9().toString().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERStudnr9(" ");
					}
					if (daoHistory.getHistoryOTHERQual9() != null && !"".equals(daoHistory.getHistoryOTHERQual9().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERQual9(daoHistory.getHistoryOTHERQual9().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERQual9(" ");
					}
					if (daoHistory.getHistoryOTHERYearStart9() != null && !"".equals(daoHistory.getHistoryOTHERYearStart9().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERYearStart9(daoHistory.getHistoryOTHERYearStart9().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERYearStart9("-1");
					}
					if (daoHistory.getHistoryOTHERYearEnd9() != null && !"".equals(daoHistory.getHistoryOTHERYearEnd9().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERYearEnd9(daoHistory.getHistoryOTHERYearEnd9().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERYearEnd9("-1");
					}
					stuRegForm.getQualOther().setHistoryOTHERYearEndDB9(daoHistory.getHistoryOTHERYearEndDB9());
					if (daoHistory.getHistoryOTHERCountry9() != null && !"".equals(daoHistory.getHistoryOTHERCountry9().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERCountry9(daoHistory.getHistoryOTHERCountry9().toString().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERCountry9("-1");
					}
					if (daoHistory.getHistoryOTHERComplete9() != null && !"".equals(daoHistory.getHistoryOTHERComplete9().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERComplete9(daoHistory.getHistoryOTHERComplete9().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERComplete9("N");
					}
					stuRegForm.getQualOther().setHistoryOTHERHiddenComplete9(stuRegForm.getQualOther().getHistoryOTHERComplete9());
					if (daoHistory.getHistoryOTHERForeign9() != null && !"".equals(daoHistory.getHistoryOTHERForeign9().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERForeign9(daoHistory.getHistoryOTHERForeign9().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERForeign9("N");
					}
					if (daoHistory.getHistoryOTHERLock9() != null && !"".equals(daoHistory.getHistoryOTHERLock9().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERLock9(daoHistory.getHistoryOTHERLock9().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERLock9("N");
					}
					
					
				}
				
				if (daoHistory.getHistoryOTHERSEQ10() != 0){
					stuRegForm.getQualOther().setHistoryOTHERSEQ10(daoHistory.getHistoryOTHERSEQ10());
					if (" ".equals(daoHistory.getHistoryOTHERUniv10())){
						stuRegForm.getQualOther().setHistoryOTHERUniv10("OTHR");
					}else{
						stuRegForm.getQualOther().setHistoryOTHERUniv10(daoHistory.getHistoryOTHERUniv10().trim());
					}
					if (daoHistory.getHistoryOTHERUnivText10() != null && !"".equals(daoHistory.getHistoryOTHERUnivText10().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERUnivText10(daoHistory.getHistoryOTHERUnivText10().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERUnivText10(" ");
					}
					if (daoHistory.getHistoryOTHERStudnr10() != null && !"".equals(daoHistory.getHistoryOTHERStudnr10().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERStudnr10(daoHistory.getHistoryOTHERStudnr10().toString().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERStudnr10(" ");
					}
					if (daoHistory.getHistoryOTHERQual10() != null && !"".equals(daoHistory.getHistoryOTHERQual10().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERQual10(daoHistory.getHistoryOTHERQual10().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERQual10(" ");
					}
					if (daoHistory.getHistoryOTHERYearStart10() != null && !"".equals(daoHistory.getHistoryOTHERYearStart10().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERYearStart10(daoHistory.getHistoryOTHERYearStart10().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERYearStart10("-1");
					}
					if (daoHistory.getHistoryOTHERYearEnd10() != null && !"".equals(daoHistory.getHistoryOTHERYearEnd10().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERYearEnd10(daoHistory.getHistoryOTHERYearEnd10().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERYearEnd10("-1");
					}
					stuRegForm.getQualOther().setHistoryOTHERYearEndDB10(daoHistory.getHistoryOTHERYearEndDB10());
					if (daoHistory.getHistoryOTHERCountry10() != null && !"".equals(daoHistory.getHistoryOTHERCountry10().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERCountry10(daoHistory.getHistoryOTHERCountry10().toString().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERCountry10("-1");
					}
					if (daoHistory.getHistoryOTHERComplete10() != null && !"".equals(daoHistory.getHistoryOTHERComplete10().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERComplete10(daoHistory.getHistoryOTHERComplete10().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERComplete10("N");
					}
					stuRegForm.getQualOther().setHistoryOTHERHiddenComplete10(stuRegForm.getQualOther().getHistoryOTHERComplete10());
					if (daoHistory.getHistoryOTHERForeign10() != null && !"".equals(daoHistory.getHistoryOTHERForeign10().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERForeign10(daoHistory.getHistoryOTHERForeign10().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERForeign10("N");
					}
					if (daoHistory.getHistoryOTHERLock10() != null && !"".equals(daoHistory.getHistoryOTHERLock10().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERLock10(daoHistory.getHistoryOTHERLock10().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERLock10("N");
					}
					
						
				}
				
				if (daoHistory.getHistoryOTHERSEQ11() != 0){
					stuRegForm.getQualOther().setHistoryOTHERSEQ11(daoHistory.getHistoryOTHERSEQ11());
					if (" ".equals(daoHistory.getHistoryOTHERUniv11())){
						stuRegForm.getQualOther().setHistoryOTHERUniv11("OTHR");
					}else{
						stuRegForm.getQualOther().setHistoryOTHERUniv11(daoHistory.getHistoryOTHERUniv11().trim());
					}
					if (daoHistory.getHistoryOTHERUnivText11() != null && !"".equals(daoHistory.getHistoryOTHERUnivText11().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERUnivText11(daoHistory.getHistoryOTHERUnivText11().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERUnivText11(" ");
					}
					if (daoHistory.getHistoryOTHERStudnr11() != null && !"".equals(daoHistory.getHistoryOTHERStudnr11().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERStudnr11(daoHistory.getHistoryOTHERStudnr11().toString().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERStudnr11(" ");
					}
					if (daoHistory.getHistoryOTHERQual11() != null && !"".equals(daoHistory.getHistoryOTHERQual11().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERQual11(daoHistory.getHistoryOTHERQual11().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERQual11(" ");
					}
					if (daoHistory.getHistoryOTHERYearStart11() != null && !"".equals(daoHistory.getHistoryOTHERYearStart11().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERYearStart11(daoHistory.getHistoryOTHERYearStart11().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERYearStart11("-1");
					}
					if (daoHistory.getHistoryOTHERYearEnd11() != null && !"".equals(daoHistory.getHistoryOTHERYearEnd11().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERYearEnd11(daoHistory.getHistoryOTHERYearEnd11().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERYearEnd11("-1");
					}
					stuRegForm.getQualOther().setHistoryOTHERYearEndDB11(daoHistory.getHistoryOTHERYearEndDB11());
					if (daoHistory.getHistoryOTHERCountry11() != null && !"".equals(daoHistory.getHistoryOTHERCountry11().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERCountry11(daoHistory.getHistoryOTHERCountry11().toString().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERCountry11("-1");
					}
					if (daoHistory.getHistoryOTHERComplete11() != null && !"".equals(daoHistory.getHistoryOTHERComplete11().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERComplete11(daoHistory.getHistoryOTHERComplete11().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERComplete11("N");
					}
					stuRegForm.getQualOther().setHistoryOTHERHiddenComplete11(stuRegForm.getQualOther().getHistoryOTHERComplete11());
					if (daoHistory.getHistoryOTHERForeign11() != null && !"".equals(daoHistory.getHistoryOTHERForeign11().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERForeign11(daoHistory.getHistoryOTHERForeign11().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERForeign11("N");
					}
					if (daoHistory.getHistoryOTHERLock11() != null && !"".equals(daoHistory.getHistoryOTHERLock11().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERLock11(daoHistory.getHistoryOTHERLock11().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERLock11("N");
					}
					
					
				}
				
				if (daoHistory.getHistoryOTHERSEQ12() != 0){
					stuRegForm.getQualOther().setHistoryOTHERSEQ12(daoHistory.getHistoryOTHERSEQ12());
					if (" ".equals(daoHistory.getHistoryOTHERUniv12())){
						stuRegForm.getQualOther().setHistoryOTHERUniv12("OTHR");
					}else{
						stuRegForm.getQualOther().setHistoryOTHERUniv12(daoHistory.getHistoryOTHERUniv12().trim());
					}
					if (daoHistory.getHistoryOTHERUnivText12() != null && !"".equals(daoHistory.getHistoryOTHERUnivText12().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERUnivText12(daoHistory.getHistoryOTHERUnivText12().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERUnivText12(" ");
					}
					if (daoHistory.getHistoryOTHERStudnr12() != null && !"".equals(daoHistory.getHistoryOTHERStudnr12().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERStudnr12(daoHistory.getHistoryOTHERStudnr12().toString().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERStudnr12(" ");
					}
					if (daoHistory.getHistoryOTHERQual12() != null && !"".equals(daoHistory.getHistoryOTHERQual12().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERQual12(daoHistory.getHistoryOTHERQual12().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERQual12(" ");
					}
					if (daoHistory.getHistoryOTHERYearStart12() != null && !"".equals(daoHistory.getHistoryOTHERYearStart12().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERYearStart12(daoHistory.getHistoryOTHERYearStart12().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERYearStart12("-1");
					}
					if (daoHistory.getHistoryOTHERYearEnd12() != null && !"".equals(daoHistory.getHistoryOTHERYearEnd12().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERYearEnd12(daoHistory.getHistoryOTHERYearEnd12().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERYearEnd12("-1");
					}
					stuRegForm.getQualOther().setHistoryOTHERYearEndDB12(daoHistory.getHistoryOTHERYearEndDB12());
					if (daoHistory.getHistoryOTHERCountry12() != null && !"".equals(daoHistory.getHistoryOTHERCountry12().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERCountry12(daoHistory.getHistoryOTHERCountry12().toString().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERCountry12("-1");
					}
					if (daoHistory.getHistoryOTHERComplete12() != null && !"".equals(daoHistory.getHistoryOTHERComplete12().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERComplete12(daoHistory.getHistoryOTHERComplete12().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERComplete12("N");
					}
					stuRegForm.getQualOther().setHistoryOTHERHiddenComplete12(stuRegForm.getQualOther().getHistoryOTHERComplete12());
					if (daoHistory.getHistoryOTHERForeign12() != null && !"".equals(daoHistory.getHistoryOTHERForeign12().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERForeign12(daoHistory.getHistoryOTHERForeign12().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERForeign12("N");
					}
					if (daoHistory.getHistoryOTHERLock12() != null && !"".equals(daoHistory.getHistoryOTHERLock12().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERLock12(daoHistory.getHistoryOTHERLock12().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERLock12("N");
					}
					
					
				}
				
				if (daoHistory.getHistoryOTHERSEQ13() != 0){
					stuRegForm.getQualOther().setHistoryOTHERSEQ13(daoHistory.getHistoryOTHERSEQ13());
					if (" ".equals(daoHistory.getHistoryOTHERUniv13())){
						stuRegForm.getQualOther().setHistoryOTHERUniv13("OTHR");
					}else{
						stuRegForm.getQualOther().setHistoryOTHERUniv13(daoHistory.getHistoryOTHERUniv13().trim());
					}
					if (daoHistory.getHistoryOTHERUnivText13() != null && !"".equals(daoHistory.getHistoryOTHERUnivText13().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERUnivText13(daoHistory.getHistoryOTHERUnivText13().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERUnivText13(" ");
					}
					if (daoHistory.getHistoryOTHERStudnr13() != null && !"".equals(daoHistory.getHistoryOTHERStudnr13().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERStudnr13(daoHistory.getHistoryOTHERStudnr13().toString().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERStudnr13(" ");
					}
					if (daoHistory.getHistoryOTHERQual13() != null && !"".equals(daoHistory.getHistoryOTHERQual13().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERQual13(daoHistory.getHistoryOTHERQual13().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERQual13(" ");
					}
					if (daoHistory.getHistoryOTHERYearStart13() != null && !"".equals(daoHistory.getHistoryOTHERYearStart13().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERYearStart13(daoHistory.getHistoryOTHERYearStart13().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERYearStart13("-1");
					}
					if (daoHistory.getHistoryOTHERYearEnd13() != null && !"".equals(daoHistory.getHistoryOTHERYearEnd13().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERYearEnd13(daoHistory.getHistoryOTHERYearEnd13().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERYearEnd13("-1");
					}
					stuRegForm.getQualOther().setHistoryOTHERYearEndDB13(daoHistory.getHistoryOTHERYearEndDB13());
					if (daoHistory.getHistoryOTHERCountry13() != null && !"".equals(daoHistory.getHistoryOTHERCountry13().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERCountry13(daoHistory.getHistoryOTHERCountry13().toString().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERCountry13("-1");
					}
					if (daoHistory.getHistoryOTHERComplete13() != null && !"".equals(daoHistory.getHistoryOTHERComplete13().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERComplete13(daoHistory.getHistoryOTHERComplete13().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERComplete13("N");
					}
					stuRegForm.getQualOther().setHistoryOTHERHiddenComplete13(stuRegForm.getQualOther().getHistoryOTHERComplete13());
					if (daoHistory.getHistoryOTHERForeign13() != null && !"".equals(daoHistory.getHistoryOTHERForeign13().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERForeign13(daoHistory.getHistoryOTHERForeign13().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERForeign13("N");
					}
					if (daoHistory.getHistoryOTHERLock13() != null && !"".equals(daoHistory.getHistoryOTHERLock13().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERLock13(daoHistory.getHistoryOTHERLock13().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERLock13("N");
					}
					
						
				}
				
				if (daoHistory.getHistoryOTHERSEQ14() != 0){
					stuRegForm.getQualOther().setHistoryOTHERSEQ14(daoHistory.getHistoryOTHERSEQ14());
					if (" ".equals(daoHistory.getHistoryOTHERUniv14())){
						stuRegForm.getQualOther().setHistoryOTHERUniv14("OTHR");
					}else{
						stuRegForm.getQualOther().setHistoryOTHERUniv14(daoHistory.getHistoryOTHERUniv14().trim());
					}
					if (daoHistory.getHistoryOTHERUnivText14() != null && !"".equals(daoHistory.getHistoryOTHERUnivText14().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERUnivText14(daoHistory.getHistoryOTHERUnivText14().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERUnivText14(" ");
					}
					if (daoHistory.getHistoryOTHERStudnr14() != null && !"".equals(daoHistory.getHistoryOTHERStudnr14().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERStudnr14(daoHistory.getHistoryOTHERStudnr14().toString().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERStudnr14(" ");
					}
					if (daoHistory.getHistoryOTHERQual14() != null && !"".equals(daoHistory.getHistoryOTHERQual14().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERQual14(daoHistory.getHistoryOTHERQual14().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERQual14(" ");
					}
					if (daoHistory.getHistoryOTHERYearStart14() != null && !"".equals(daoHistory.getHistoryOTHERYearStart14().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERYearStart14(daoHistory.getHistoryOTHERYearStart14().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERYearStart14("-1");
					}
					if (daoHistory.getHistoryOTHERYearEnd14() != null && !"".equals(daoHistory.getHistoryOTHERYearEnd14().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERYearEnd14(daoHistory.getHistoryOTHERYearEnd14().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERYearEnd14("-1");
					}
					stuRegForm.getQualOther().setHistoryOTHERYearEndDB14(daoHistory.getHistoryOTHERYearEndDB14());
					if (daoHistory.getHistoryOTHERCountry14() != null && !"".equals(daoHistory.getHistoryOTHERCountry14().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERCountry14(daoHistory.getHistoryOTHERCountry14().toString().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERCountry14("-1");
					}
					if (daoHistory.getHistoryOTHERComplete14() != null && !"".equals(daoHistory.getHistoryOTHERComplete14().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERComplete14(daoHistory.getHistoryOTHERComplete14().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERComplete14("N");
					}
					stuRegForm.getQualOther().setHistoryOTHERHiddenComplete14(stuRegForm.getQualOther().getHistoryOTHERComplete14());
					if (daoHistory.getHistoryOTHERForeign14() != null && !"".equals(daoHistory.getHistoryOTHERForeign14().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERForeign14(daoHistory.getHistoryOTHERForeign14().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERForeign14("N");
					}
					if (daoHistory.getHistoryOTHERLock14() != null && !"".equals(daoHistory.getHistoryOTHERLock14().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERLock14(daoHistory.getHistoryOTHERLock14().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERLock14("N");
					}
					
					
				}
				
				if (daoHistory.getHistoryOTHERSEQ15() != 0){
					stuRegForm.getQualOther().setHistoryOTHERSEQ15(daoHistory.getHistoryOTHERSEQ15());
					if (" ".equals(daoHistory.getHistoryOTHERUniv15())){
						stuRegForm.getQualOther().setHistoryOTHERUniv15("OTHR");
					}else{
						stuRegForm.getQualOther().setHistoryOTHERUniv15(daoHistory.getHistoryOTHERUniv15().trim());
					}
					if (daoHistory.getHistoryOTHERUnivText15() != null && !"".equals(daoHistory.getHistoryOTHERUnivText15().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERUnivText15(daoHistory.getHistoryOTHERUnivText15().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERUnivText15(" ");
					}
					if (daoHistory.getHistoryOTHERStudnr15() != null && !"".equals(daoHistory.getHistoryOTHERStudnr15().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERStudnr15(daoHistory.getHistoryOTHERStudnr15().toString().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERStudnr15(" ");
					}
					if (daoHistory.getHistoryOTHERQual15() != null && !"".equals(daoHistory.getHistoryOTHERQual15().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERQual15(daoHistory.getHistoryOTHERQual15().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERQual15(" ");
					}
					if (daoHistory.getHistoryOTHERYearStart15() != null && !"".equals(daoHistory.getHistoryOTHERYearStart15().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERYearStart15(daoHistory.getHistoryOTHERYearStart15().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERYearStart15("-1");
					}
					if (daoHistory.getHistoryOTHERYearEnd15() != null && !"".equals(daoHistory.getHistoryOTHERYearEnd15().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERYearEnd15(daoHistory.getHistoryOTHERYearEnd15().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERYearEnd15("-1");
					}
					stuRegForm.getQualOther().setHistoryOTHERYearEndDB15(daoHistory.getHistoryOTHERYearEndDB15());
					if (daoHistory.getHistoryOTHERCountry15() != null && !"".equals(daoHistory.getHistoryOTHERCountry15().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERCountry15(daoHistory.getHistoryOTHERCountry15().toString().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERCountry15("-1");
					}
					if (daoHistory.getHistoryOTHERComplete15() != null && !"".equals(daoHistory.getHistoryOTHERComplete15().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERComplete15(daoHistory.getHistoryOTHERComplete15().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERComplete15("N");
					}
					stuRegForm.getQualOther().setHistoryOTHERHiddenComplete15(stuRegForm.getQualOther().getHistoryOTHERComplete15());
					if (daoHistory.getHistoryOTHERForeign15() != null && !"".equals(daoHistory.getHistoryOTHERForeign15().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERForeign15(daoHistory.getHistoryOTHERForeign15().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERForeign15("N");
					}
					if (daoHistory.getHistoryOTHERLock15() != null && !"".equals(daoHistory.getHistoryOTHERLock15().toString().trim())){
						stuRegForm.getQualOther().setHistoryOTHERLock15(daoHistory.getHistoryOTHERLock15().toString().toUpperCase().trim());
					}else{
						stuRegForm.getQualOther().setHistoryOTHERLock15("N");
					}
				}
			}
			//log.debug("ApplyForStudentNumberAction - stepQualConfirm -  GoTo PrevQual");
			setDropdownListsPrev(request, form);
			return mapping.findForward("applyPrevQual");
		}else{
			if ("newStu".equalsIgnoreCase(stuRegForm.getStudent().getStuExist())){
				//log.debug("ApplyForStudentNumberAction - stepQualConfirm -  NewStu - GoTo applyNewPersonal");
				setDropdownListsStep1(request,stuRegForm);
				return mapping.findForward("applyNewPersonal");
			}else{
				//log.debug("ApplyForStudentNumberAction - stepQualConfirm -  CurStu - GoTo applyRetContact");
				return mapping.findForward("applyRetContact");
			}
		}
	}
	
	public ActionForward stepSLPConfirm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		//log.debug("ApplyForStudentNumberaction - stepSLPConfirm - Start");
		
		// if student number is null, block application
		ActionMessages messages = new ActionMessages();
		StudentRegistrationForm stuRegForm = (StudentRegistrationForm) form;
		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
		stuRegForm.setFromPage("stepQualConfirm");
		
		if(stuRegForm.getStudent().getNumber() == null || "".equals(stuRegForm.getStudent().getNumber())){
			//log.debug("ApplyForStudentNumberAction - stepSLPConfirm - Student Nr is null or empty when saving qualifications");
			messages.add(ActionMessages.GLOBAL_MESSAGE,
			new ActionMessage("message.generalmessage", "You performed and invalid action or an Error occurred. Please log on again to retry."));
			addErrors(request, messages);
			if (stuRegForm.getAdminStaff().isAdmin()){
				return mapping.findForward("loginStaff");
			}else{
				return mapping.findForward("loginStu");
			}
		}
		//Save Second SLP Choice to STUAPQ
		//Get Status of First SLP Choice and make Second Choice the same
		String firstStatus = dao.getBasicStatus(stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), "1");
		//log.debug("ApplyForStudentNumberAction - stepSLPConfirm - Choice1 - firstStatus: " + firstStatus);
		
		String firstPassed = dao.getBasicSTUAPQInfo(stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), "1", "PASSED");
		//log.debug("ApplyForStudentNumberAction - stepSLPConfirm - Choice1 - firstPassed: " + firstPassed);
		
		String firstApplied = dao.getBasicSTUAPQInfo(stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), "1", "APPLIED");
		//log.debug("ApplyForStudentNumberAction - stepSLPConfirm - Choice1 - firstApplied: " + firstApplied);
		
		String firstRPL = dao.getBasicSTUAPQInfo(stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), "1", "RPL");
		//log.debug("ApplyForStudentNumberAction - stepSLPConfirm - Choice1 - firstRPL: " + firstRPL);
		
		String collegeCode2 = dao.getCollegeCategory(stuRegForm.getSelQualCode2());
		//log.debug("ApplyForStudentNumberAction - stepSLPConfirm - Choice2 - CollegeCategory2: " + collegeCode2);
		
		String adminSection2 = dao.getQualAdminSection(stuRegForm.getStudent().getQual2());
		//log.debug("ApplyForStudentNumberAction - stepSLPConfirm - Choice2 - adminSection2: " + adminSection2);
		
		//There should be no second choice entry, unless student went back and forward again, if so, delete it, then insert new entry.
		boolean getResult = dao.getSTUAPQDetail(stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), "2", "stepSLPConfirm");
		//log.debug("ApplyForStudentNumberAction - stepSLPConfirm - newQual1 - Check STUAPQ Detail= " + getResult);
		for (int i = 0; i < 5; i++){
			if (getResult){
				int delResult = dao.delSTUAPQ(stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), "2", "stepSLPConfirm");
				//log.debug("ApplyForStudentNumberAction - stepSLPConfirm - newQual2 - Delete STUAPQ ("+i+")= " + delResult);
				Thread.sleep(1000);
				getResult = dao.getSTUAPQDetail(stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), "2", "stepSLPConfirm");
			}
		}
		//log.debug("ApplyForStudentNumberAction - stepSLPConfirm - No STUAPQ for Choice 2- Saving STUAPQ");
		
		boolean isError = false;
		if (!getResult){
			int saveResult = dao.saveSTUAPQ(stuRegForm.getSelQualCode2(), stuRegForm.getSelSpecCode2(), "2", 
					stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), 
					firstStatus, collegeCode2, adminSection2, firstPassed, 
					firstRPL, firstApplied, "stepSLPConfirm");
			if (saveResult == 0) {
				isError = true;
			} else {
				isError = false;
			}
			//log.debug("ApplyForStudentNumberAction - stepSLPConfirm - Saving Qual2 - isError="+isError);
			boolean getResult2 = dao.getSTUAPQDetail(stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), "2", "stepSLPConfirm");					
			if (!getResult2){
				//log.debug("ApplyForStudentNumberAction - stepSLPConfirm - STUAPQ SLP Choice 2 not Saved - Stu=" + stuRegForm.getStudent().getNumber() + " / Qual2=" + stuRegForm.getSelQualCode2() + " / Spec2=" + stuRegForm.getSelSpecCode2());
				log.warn("ApplyForStudentNumberAction - stepSLPConfirm - STUAPQ SLP Choice 2 not Saved - Stu=" + stuRegForm.getStudent().getNumber() + " / Qual2=" + stuRegForm.getSelQualCode2() + " / Spec2=" + stuRegForm.getSelSpecCode2());
			}
		}
		
		//Do SLP 2 Workflow file
		/* Set submission time stamp */
		Date date = new java.util.Date();
		String displayDate = (new java.text.SimpleDateFormat("EEEEE dd MMMMM yyyy hh:mm:ss").format(date).toString());
		stuRegForm.getStudent().setAppTime(displayDate);
		request.setAttribute("email",stuRegForm.getStudent().getEmailAddress());
		
		/*Write work flow document */
		//log.debug("ApplyForStudentNumberAction - stepSLPConfirm -  writeWorkflowSLP: " + date);
		writeWorkflowSLP(stuRegForm,date);
		
		//log.debug("ApplyForStudentNumberAction - stepSLPConfirm - GoTo dynamicUpload");
		return mapping.findForward("dynamicUpload");
	}
	
	public ActionForward stepRetContact(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//log.debug("ApplyForStudentNumberaction - stepRetContact - Start");
		
		// if student number is null, block application
		ActionMessages messages = new ActionMessages();
		StudentRegistrationForm stuRegForm = (StudentRegistrationForm) form;
		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
		stuRegForm.setFromPage("applyRetContact");
		
		if(stuRegForm.getStudent().getNumber() == null || "".equals(stuRegForm.getStudent().getNumber())){
			//log.debug("ApplyForStudentNumberAction SaveStudyAction - Student Nr is null or empty when saving qualifications");
			messages.add(ActionMessages.GLOBAL_MESSAGE,
			new ActionMessage("message.generalmessage", "You performed and invalid action or an Error occurred. Please log on again to retry."));
			addErrors(request, messages);
			if (stuRegForm.getAdminStaff().isAdmin()){
				return mapping.findForward("loginStaff");
			}else{
				return mapping.findForward("loginStu");
			}
		}
		
		stuRegForm.getStudent().setCellNr(stripXSS(stuRegForm.getStudent().getCellNr(), "CellNr", "RetContact", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
		stuRegForm.getStudent().setCellNr2(stripXSS(stuRegForm.getStudent().getCellNr2(), "CellNr2", "RetContact", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));

		if (stuRegForm.getStudent().getCellNr().trim() == null || "".equalsIgnoreCase(stuRegForm.getStudent().getCellNr().trim())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", "Please enter a cellular phone number."));
			addErrors(request, messages);
			return mapping.findForward("applyRetContact");
		}
		
		if (stuRegForm.getStudent().getCellNr().trim() != null && !"".equalsIgnoreCase(stuRegForm.getStudent().getCellNr().trim())){
			if (stuRegForm.getStudent().getCellNr().charAt(0)!='+'){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Start cellular phone number with a '+'. See example."));
				addErrors(request, messages);
				return mapping.findForward("applyRetContact");
			}
		}

		if (stuRegForm.getStudent().getCellNr().trim().length() > 20){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Cellular number is too long. Please enter 20 characters or less. " ));
			addErrors(request, messages);
			return mapping.findForward("applyRetContact");
		}
		
		if ((stuRegForm.getStudent().getCellNr().trim() != null && !"".equalsIgnoreCase(stuRegForm.getStudent().getCellNr().trim())) &&
			(stuRegForm.getStudent().getCellNr2().trim() == null || "".equalsIgnoreCase(stuRegForm.getStudent().getCellNr2().trim()))){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Please confirm cellular phone number."));
				addErrors(request, messages);
				return mapping.findForward("applyRetContact");
		}

		if (!stuRegForm.getStudent().getCellNr().trim().equalsIgnoreCase(stuRegForm.getStudent().getCellNr2().trim())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Entered cellular phone numbers do not match."));
			addErrors(request, messages);
			return mapping.findForward("applyRetContact");
		}

		stuRegForm.getStudent().setEmailAddress(stripXSS(stuRegForm.getStudent().getEmailAddress(), "EmailAddress", "RetContact", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
		stuRegForm.getStudent().setEmailAddress2(stripXSS(stuRegForm.getStudent().getEmailAddress2(), "EmailAddress2", "RetContact", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
		if(!stuRegForm.getStudent().getEmailAddressGood()){
			//log.debug("MyLife Good");
			if (stuRegForm.getStudent().getEmailAddress() == null || "".equals(stuRegForm.getStudent().getEmailAddress())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "The e-mail address is mandatory, please supply a valid e-mail address."));
				addErrors(request, messages);
				return mapping.findForward("applyRetContact");
			}
	
			if (stuRegForm.getStudent().getEmailAddress2() == null || "".equals(stuRegForm.getStudent().getEmailAddress2())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Please confirm e-mail address."));
				addErrors(request, messages);
				return mapping.findForward("applyRetContact");
			}
	
			if (!stuRegForm.getStudent().getEmailAddress().equalsIgnoreCase(stuRegForm.getStudent().getEmailAddress2())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Entered e-mail addresses do not match."));
				addErrors(request, messages);
				return mapping.findForward("applyRetContact");
			}
			String checkMail = stuRegForm.getStudent().getEmailAddress();
			//String checkMail2 = stuRegForm.getStudent().getEmailAddress2();
			Integer eCheckIndex=checkMail.indexOf("mylife.unisa");
			//Integer eCheckIndex2=checkMail2.indexOf("mylife.unisa");
			
			if (eCheckIndex >= 1) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Please enter a valid E-mail address other than mylife.unisa."));
				addErrors(request, messages);
				return mapping.findForward("applyRetContact");
				
			}
		
			//if (eCheckIndex2 >= 1) {
			//	messages.add(ActionMessages.GLOBAL_MESSAGE,
			//			new ActionMessage("message.generalmessage", "Please enter a valid E-mail address other than mylife.unisa."));
			//	addErrors(request, messages);
			//	return "step1aforward";
			//	
			//}
			// test for valid email address format
			//messages = (ActionMessages) stuRegForm.validate(mapping, request);
			//if (!messages.isEmpty()) {
			//	addErrors(request, messages);
			//	return "step1aforward";
			//}
			// test for valid email address format
			Boolean chkEmail = isEmailValid(stuRegForm.getStudent().getEmailAddress());
			if (!chkEmail) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Please ensure you typed your e-mail address correctly."));
				addErrors(request, messages);
				return mapping.findForward("applyRetContact");
			}
		}else{
			//log.debug("MyLife NOT Good");
		}
		
		//Update Student Email Address and Cellular Number
		dao.updStudentContact(stuRegForm.getStudent().getNumber(),stuRegForm.getStudent().getEmailAddress(), stuRegForm.getStudent().getCellNr());

		//log.debug("ApplyForStudentNumberAction - stepRetContact - GoTo applyRetRadio");
		return mapping.findForward("applyRetRadio");
	}
	
	public ActionForward stepRetRadio(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//log.debug("ApplyForStudentNumberaction - stepRetRadio - Start");

		// if student number is null, block application
		ActionMessages messages = new ActionMessages();
		StudentRegistrationForm stuRegForm = (StudentRegistrationForm) form;
		stuRegForm.setFromPage("applyRetRadio");
		
		if(stuRegForm.getStudent().getNumber() == null || "".equals(stuRegForm.getStudent().getNumber())){
			//log.debug("ApplyForStudentNumberAction - stepRetRadio - Student Nr is null or empty when saving qualifications");
			messages.add(ActionMessages.GLOBAL_MESSAGE,
			new ActionMessage("message.generalmessage", "You performed and invalid action or an Error occurred. Please log on again to retry."));
			addErrors(request, messages);
			if (stuRegForm.getAdminStaff().isAdmin()){
				return mapping.findForward("loginStaff");
			}else{
				return mapping.findForward("loginStu");
			}
		}
		
		//Adding Complete Qualification Request here so that it is set before any errors may be thrown as otherwise it might not be set when returning to the page.
		stuRegForm.getStudentApplication().setCompleteText(stripXSS(request.getParameter("completeText"), "CompleteText", "RetRadio", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
		
		stuRegForm.getStudentApplication().setStaffCurrent(stripXSS(stuRegForm.getStudentApplication().getStaffCurrent(), "StaffCurrent", "RetRadio", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
		if (stuRegForm.getStudentApplication().getStaffCurrent() == null || "".equalsIgnoreCase(stuRegForm.getStudentApplication().getStaffCurrent())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please confirm if you are a current or retired Unisa staff member."));
			addErrors(request, messages);
			return mapping.findForward("applyRetRadio");
		}
		//log.debug("ApplyForStudentNumberaction - stepRetRadio - StaffCurrent=" + stuRegForm.getStudentApplication().getStaffCurrent());

		stuRegForm.getStudentApplication().setStaffDeceased(stripXSS(stuRegForm.getStudentApplication().getStaffDeceased(), "StaffDeceased", "RetRadio", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
		if (stuRegForm.getStudentApplication().getStaffDeceased() == null || "".equalsIgnoreCase(stuRegForm.getStudentApplication().getStaffDeceased())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please confirm if you are a dependant of a current, retired or deceased permanent Unisa staff member."));
			addErrors(request, messages);
			return mapping.findForward("applyRetRadio");
		}
		//log.debug("ApplyForStudentNumberaction - stepRetRadio - StaffDeceased=" + stuRegForm.getStudentApplication().getStaffDeceased());

		stuRegForm.getStudentApplication().setPrisoner(stripXSS(stuRegForm.getStudentApplication().getPrisoner(), "Prisoner", "RetRadio", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
		if (stuRegForm.getStudentApplication().getPrisoner() == null || "".equalsIgnoreCase(stuRegForm.getStudentApplication().getPrisoner())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please confirm if you are a Prisoner."));
			addErrors(request, messages);
			return mapping.findForward("applyRetRadio");
		}
		//log.debug("ApplyForStudentNumberaction - stepRetRadio - Prisoner=" + stuRegForm.getStudentApplication().getPrisoner());

		stuRegForm.getStudentApplication().setCompleteQual(stripXSS(stuRegForm.getStudentApplication().getCompleteQual(), "CompleteQual", "RetRadio", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
		if (stuRegForm.getStudentApplication().getCompleteQual() == null || "".equalsIgnoreCase(stuRegForm.getStudentApplication().getCompleteQual()) || "0".equalsIgnoreCase(stuRegForm.getStudentApplication().getCompleteQual())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please confirm if you are in the process of completing a qualification."));
			addErrors(request, messages);
			return mapping.findForward("applyRetRadio");
		}
		//log.debug("ApplyForStudentNumberaction - stepRetRadio - CompleteQual=" + stuRegForm.getStudentApplication().getCompleteQual());

		if (stuRegForm.getStudentApplication().getCompleteQual().equalsIgnoreCase("Y")){
			//log.debug("ApplyForStudentNumberaction - stepRetRadio - CompleteText Get=" + stuRegForm.getStudentApplication().getCompleteText());
			
			if (stuRegForm.getStudentApplication().getCompleteText() == null || "".equalsIgnoreCase(stuRegForm.getStudentApplication().getCompleteText().trim()) || stuRegForm.getStudentApplication().getCompleteText().trim().length() < 1){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Please enter which qualification you will be completing."));
					addErrors(request, messages);
					return mapping.findForward("applyRetRadio");
			}
			if (stuRegForm.getStudentApplication().getCompleteText().trim().length() > 100){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Qualification to be completed is too long. Please enter 100 characters or less. " ));
				addErrors(request, messages);
				return mapping.findForward("applyRetRadio");
			}
		}
		//log.debug("ApplyForStudentNumberaction - stepRetRadio - CompleteText Final=" + stuRegForm.getStudentApplication().getCompleteText());

		return mapping.findForward("applyRetDeclare");
		
	}
	
	@SuppressWarnings("unused")
	public ActionForward applyRetDeclare(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//log.debug("ApplyForStudentNumberaction - applyRetDeclare - Start");

		// if student number is null, block application
		ActionMessages messages = new ActionMessages();
		StudentRegistrationForm stuRegForm = (StudentRegistrationForm) form;
		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
		stuRegForm.setFromPage("applyRetDeclare");
		
		if(stuRegForm.getStudent().getNumber() == null || "".equals(stuRegForm.getStudent().getNumber())){
			//log.debug("ApplyForStudentNumberAction - applyRetDeclare - Student Nr is null or empty when saving qualifications");
			messages.add(ActionMessages.GLOBAL_MESSAGE,
			new ActionMessage("message.generalmessage", "You performed and invalid action or an Error occurred. Please log on again to retry."));
			addErrors(request, messages);
			if (stuRegForm.getAdminStaff().isAdmin()){
				return mapping.findForward("loginStaff");
			}else{
				return mapping.findForward("loginStu");
			}
		}
		
		//Adding Complete Qualification Request here so that it is set before any errors may be thrown as otherwise it might not be set when returning to the page.
		stuRegForm.getStudentApplication().setCompleteText(stripXSS(request.getParameter("completeText"), "CompleteText", "RetDeclare", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
		
		// agreement
		stuRegForm.setAgree(stripXSS(stuRegForm.getAgree(), "Declate", "RetDeclare", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
		if (stuRegForm.getAgree() == null || "".equals(stuRegForm.getAgree().trim())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Indicate your agreement to the declaration and undertaking."));
			addErrors(request, messages);
			setDropdownListsStep3(request, stuRegForm);
			return mapping.findForward("applyRetDeclare");
		}
	
		//test agreement flag
		if (!"Y".equalsIgnoreCase(stuRegForm.getAgree())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student has declined the Decleration agreement. Please log on again if you wish to retry."));
			addErrors(request, messages);
			return mapping.findForward(cancel(form));
		}
		//log.debug("ApplyForStudentNumberaction - applyRetDeclare - StaffCurrent=" + stuRegForm.getStudentApplication().getStaffCurrent());

		//Update Student Staff or Dependent details
		boolean isSTUANN = dao.validateStudentAnnual(stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear());
		
		if (!isSTUANN){
			Srrsa01sRegStudentPersDetail op = new Srrsa01sRegStudentPersDetail();
			operListener opl = new operListener();
			op.addExceptionListener(opl);
			op.clear();
		
			op.setInCsfClientServerCommunicationsClientVersionNumber((short) 3);
			op.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
			op.setInCsfClientServerCommunicationsAction("D");
			op.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
			op.setInWsUserNumber(99998);
			op.setInStudentAnnualRecordMkAcademicYear(Short.parseShort(String.valueOf(stuRegForm.getStudent().getAcademicYear())));
			op.setInStudentAnnualRecordMkAcademicPeriod(Short.parseShort("1")); //Must be hardcoded to 1 !!!!
			op.setInStudentAnnualRecordMkStudentNr(Integer.parseInt(stuRegForm.getStudent().getNumber()));
		
			//log.debug("ApplyForStudentNumberAction - applyRetDeclare - (Srrsa01sRegStudentPersDetail) - Student Number=" + Integer.parseInt(stuRegForm.getStudent().getNumber()) + ", Year: " + String.valueOf(stuRegForm.getStudent().getAcademicYear()));
		
			op.execute();
		
			if (opl.getException() != null) throw opl.getException();
			if (op.getExitStateType() < 3) throw new Exception(op.getExitStateMsg());
		
			//log.debug("ApplyForStudentNumberAction - applyRetDeclare - displayPersonal - After Execute");
			String errorMessage = op.getOutCsfStringsString500();
			
			//log.debug("ApplyForStudentNumberAction - applyRetDeclare - displayPersonal - getOutCsfStringsString500="+op.getOutCsfStringsString500());
			
			//log.debug("ApplyForStudentNumberAction - applyRetDeclare - displayPersonal - errorMessage: " + errorMessage);
			if(!"".equalsIgnoreCase(errorMessage)){
				if ("WARNING".equalsIgnoreCase(errorMessage.substring(0,7))){
					errorMessage="";
				}			
			}
		
			if (!"".equals(errorMessage) || op.getOutCsfClientServerCommunicationsReturnCode()== 9999){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "An error occurred updating Student Annual Record. /"+errorMessage));
				addErrors(request, messages);
				return mapping.findForward("applyQualificationConfirm");
			}
		}
		//Update Student Staff Member, Current Qualification Completion Detail and Prisoner Detail
		dao.updateStudent(stuRegForm.getStudent().getNumber(),stuRegForm.getStudentApplication().getStaffCurrent(), stuRegForm.getStudentApplication().getStaffDeceased(),stuRegForm.getStudentApplication().getCompleteQual(), stuRegForm.getStudentApplication().getCompleteText(), stuRegForm.getStudentApplication().getPrisoner(), stuRegForm.getStudent().getAcademicYear());
		
		//Update Student Record - Hard-Code Correspondence Language to English - Vinesh 2017
		dao.updateStudentCorrespondence(stuRegForm.getStudent().getNumber());
		
		//Save Qualification to STUAPQ - Start
		//log.debug("ApplyForStudentNumberAction - applyRetDeclare -  Save Qualification to STUAPQ - Start");
		
		//Save Qualification and Specialization and contact details before going to File Upload
		String newCat1 = dao.getXMLSelected("catCode1", "1", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), "applyRetDeclare");
		String newQual1 = dao.getXMLSelected("qualCode1", "1", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), "applyRetDeclare");
		String newSpec1 = dao.getXMLSelected("specCode1", "1", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), "applyRetDeclare");
	
		String newCat2 = dao.getXMLSelected("catCode2", "2", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), "applyRetDeclare");
		String newQual2 = dao.getXMLSelected("qualCode2", "2", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), "applyRetDeclare");
		String newSpec2 = dao.getXMLSelected("specCode2", "2", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), "applyRetDeclare");
	
		//log.debug("ApplyForStudentNumberAction - applyRetDeclare get studentNr: " + stuRegForm.getStudent().getNumber());
		//log.debug("ApplyForStudentNumberAction - applyRetDeclare get acaYear: " + stuRegForm.getStudent().getAcademicYear());
		//log.debug("ApplyForStudentNumberAction - applyRetDeclare get acaPeriod: " + stuRegForm.getStudent().getAcademicPeriod());
		
		//log.debug("ApplyForStudentNumberAction - applyRetDeclare get newCat1:  " + newCat1);
		//log.debug("ApplyForStudentNumberAction - applyRetDeclare get newCat2:  " + newCat2);
		//log.debug("ApplyForStudentNumberAction - applyRetDeclare get newQual1: " + newQual1);
		//log.debug("ApplyForStudentNumberAction - applyRetDeclare get newQual2: " + newQual2);
		//log.debug("ApplyForStudentNumberAction - applyRetDeclare get newSpec1: " + newSpec1);
		//log.debug("ApplyForStudentNumberAction - applyRetDeclare get newSpec2: " + newSpec2);
		
		//---------- Check input - Primary Qualification
		if (stuRegForm.getStudent().getCategory1() == null || newCat1 == null || stuRegForm.getStudent().getQual1() == null || newQual1 == null || stuRegForm.getStudent().getSpec1() == null || newSpec1 == null){
			//log.debug("ApplyForStudentNumberAction - applyRetDeclare Student Has Null Entry Student="+stuRegForm.getStudent().getNumber()+", Cat1=" + stuRegForm.getStudent().getCategory1() +", XMLCat1="+newCat1+", Qual1=" + stuRegForm.getStudent().getQual1() +", XMLQual1="+newQual1+", Spec1=" + stuRegForm.getStudent().getSpec1() +", XMLSpec1="+newSpec1);
			messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", "An Error occurred while processing your Primary Qualification selection (2aN). Please try again."));
			addErrors(request, messages);
			return mapping.findForward("applyQualification");
		}else{
			if (!stuRegForm.getStudent().getCategory1().equalsIgnoreCase(newCat1)){
				//log.debug("ApplyForStudentNumberAction - applyRetDeclare Student Cat1=" + stuRegForm.getStudent().getCategory1() +", XMLCat1="+newCat1);
				messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "An Error occurred while processing your primary category selection (2a). Please try again."));
				addErrors(request, messages);
				return mapping.findForward("applyQualification");
			}
			if (!stuRegForm.getStudent().getQual1().equalsIgnoreCase(newQual1)){
				//log.debug("ApplyForStudentNumberAction - applyRetDeclare Student Qual1=" + stuRegForm.getStudent().getQual1() +", XMLQual1="+newQual1);
				messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "An Error occurred while processing your primary qualification selection (2a). Please try again."));
				addErrors(request, messages);
				return mapping.findForward("applyQualification");
			}
			if (!stuRegForm.getStudent().getSpec1().equalsIgnoreCase(newSpec1)){
				//log.debug("ApplyForStudentNumberAction - applyRetDeclare Student Spec1=" + stuRegForm.getStudent().getSpec1() +", XMLSpec1="+newSpec1);
				messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "An Error occurred while processing your primary specialisation selection (2a). Please try again."));
				addErrors(request, messages);
				return mapping.findForward("applyQualification");
			}
		}
		
		// ---------- Check input - Secondary Qualification
		if (stuRegForm.getStudent().getCategory2() == null || newCat2 == null || stuRegForm.getStudent().getQual2() == null || newQual2 == null || stuRegForm.getStudent().getSpec2() == null || newSpec2 == null){
			//log.debug("ApplyForStudentNumberAction - applyRetDeclare Student Has Null Entry Student="+stuRegForm.getStudent().getNumber()+", Cat2=" + stuRegForm.getStudent().getCategory2() +", XMLCat2="+newCat2+", Qual2=" + stuRegForm.getStudent().getQual2() +", XMLQual2="+newQual2+", Spec2=" + stuRegForm.getStudent().getSpec2() +", XMLSpec2="+newSpec2);
			messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", "An Error occurred while processing your Secondary Qualification selection (2aN). Please try again."));
			addErrors(request, messages);
			return mapping.findForward("applyQualification");
		}else{
			if (!stuRegForm.getStudent().getCategory2().equalsIgnoreCase(newCat2)){
				//log.debug("ApplyForStudentNumberAction - stepRetQual Student Cat2=" + stuRegForm.getStudent().getCategory2() +", XMLCat2="+newCat2);
				messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "An Error occurred while processing your secondary category selection (2a). Please try again."));
				addErrors(request, messages);
				return mapping.findForward("applyQualification");
			}
			if (!stuRegForm.getStudent().getQual2().equalsIgnoreCase(newQual2)){
				//log.debug("ApplyForStudentNumberAction - applyRetDeclare Student Qual2=" + stuRegForm.getStudent().getQual2() +", XMLQual2="+newQual2);
				messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "An Error occurred while processing your secondary qualification selection (2a). Please try again."));
				addErrors(request, messages);
				return mapping.findForward("applyQualification");
			}
			if (!stuRegForm.getStudent().getSpec2().equalsIgnoreCase(newSpec2)){
				//log.debug("ApplyForStudentNumberAction - applyRetDeclare Student Spec2=" + stuRegForm.getStudent().getSpec2() +", XMLSpec2="+newSpec2);
				messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "An Error occurred while processing your secondary specialisation selection (2a). Please try again."));
				addErrors(request, messages);
				return mapping.findForward("applyQualification");
			}
		}
		String underpost1 = " ";
		String underpost2 = " ";
		String code1 = " ";
		String code2 = " ";
		String qtype1 = " ";
		String qtype2 = " ";
		String qkat1 = " ";
		String qkat2 = " ";
		String existQual1=dao.getExistQual(stuRegForm.getStudent().getNumber());
		String existSpec1=dao.getExistSpec(stuRegForm.getStudent().getNumber());
	
		// Proposed Primary qualification
		if (newQual1 != null && !"0".equals(newQual1) && !"".equals(newQual1) && !"undefined".equalsIgnoreCase(newQual1)){
			code1 = dao.validateQualification(stuRegForm.getStudent().getNumber(), newQual1);
			underpost1 = code1.substring(0,1);
			qtype1 = code1.substring(1,2);
			qkat1 = code1.substring(2);
		}else{
			messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", "An Error occurred while processing your primary qualification selection. Please try again."));
			addErrors(request, messages);
			return mapping.findForward("applyQualification");
		}
				
		// Proposed Secondary qualification
		//log.debug("ApplyForStudentNumberAction - applyRetDeclare - Proposed Secondary qualification - newQual2: " + newQual2);
		if (newQual2 != null && !"0".equals(newQual2) && !"".equals(newQual2) && !"undefined".equalsIgnoreCase(newQual2)){
			code2 = dao.validateQualification(stuRegForm.getStudent().getNumber(), newQual2);
			underpost2 = code2.substring(0,1);
			qtype2 = code2.substring(1,2);
			qkat2 = code2.substring(2);
		}
		
		// Proposed Primary Specialization
		if (newSpec1 == null || "0".equals(newSpec1) || "".equals(newSpec1) || "NVT".equalsIgnoreCase(newSpec1) || "undefined".equalsIgnoreCase(newSpec1)){
			newSpec1 = " ";
		}
		
		// Proposed Secondary Specialization
		if (newSpec2 == null || "0".equals(newSpec2) || "".equals(newSpec2) || "NVT".equalsIgnoreCase(newSpec2)  || "undefined".equalsIgnoreCase(newSpec2)){
			newSpec2 = " ";
		}
		
		String collegeCode1 = dao.getCollegeCategory(stuRegForm.getStudent().getQual1());
		String collegeCode2 = dao.getCollegeCategory(stuRegForm.getStudent().getQual2());
		String adminSection1 = dao.getQualAdminSection(stuRegForm.getStudent().getQual1());
		String adminSection2 = dao.getQualAdminSection(stuRegForm.getStudent().getQual2());
		
		//log.debug("ApplyForStudentNumberAction - applyRetDeclare - newQual1 - CollegeCategory: " + collegeCode1);
		//log.debug("ApplyForStudentNumberAction - applyRetDeclare - newQual2 - CollegeCategory: " + collegeCode2);
		//log.debug("ApplyForStudentNumberAction - applyRetDeclare - newQual1 - AdminSectio: " + adminSection1);
		//log.debug("ApplyForStudentNumberAction - applyRetDeclare - newQual2 - AdminSectio: " + adminSection2);
	
		boolean getResult1 = false;
		boolean getResult2 = false;
		boolean isError1 = false;
		boolean isError2 = false;
		
		//Edmund 2018 Add Status Check for Semester 1 Applications and set Semester 2 the same, thus if Status was ND or NP in Aug, it must be ND or NP in Apr/May
		String statusResult1 = "AP";
		String statusResult2 = "AP";
		//log.debug("ApplyForStudentNumberAction - applyRetDeclare - Check Semester 1 Status - Make semester 2 the same");
	
		if  (stuRegForm.getStudent().getAcademicPeriod().equals("2")){
			statusResult1 = dao.getSTUAPQStatus(stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), "1", "1", "applyRetDeclare"); //Period and Choice Hardcoded
			statusResult2 = dao.getSTUAPQStatus(stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), "1", "2", "applyRetDeclare"); //Period and Choice Hardcoded
			//log.debug("ApplyForStudentNumberAction - applyRetDeclare - newQual1 - STUAPQStatus - acaPeriod1= " + statusResult1);
			//log.debug("ApplyForStudentNumberAction - applyRetDeclare - newQual2 - STUAPQStatus - acaPeriod1= " + statusResult2);
		}
		
		getResult1 = dao.getSTUAPQDetail(stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), "1", "applyRetDeclare");
		//log.debug("ApplyForStudentNumberAction - applyRetDeclare - newQual1 - Check STUAPQ Detail= " + getResult1);
		for (int i = 0; i < 5; i++){
			if (getResult1){
				int delResult1 = dao.delSTUAPQ(stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), "1", "applyRetDeclare");
				//log.debug("ApplyForStudentNumberAction - applyRetDeclare - newQual1 - Delete STUAPQ ("+i+")= " + delResult1);
				Thread.sleep(1000);
				getResult1 = dao.getSTUAPQDetail(stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), "1", "applyRetDeclare");
			}
		}
		//log.debug("ApplyForStudentNumberAction - applyRetDeclare - newQual1 - No STUAPQ - Saving STUAPQ");
		/*
		 	//log.debug("ApplyForStudentNumberAction - applyRetDeclare - newQual1 - STUAPQ Parameters="+
				newQual1+", "+newSpec1+", 1, "+stuRegForm.getStudent().getNumber()+", "+stuRegForm.getStudent().getAcademicYear()+", "+stuRegForm.getStudent().getAcademicPeriod()+", "+
				statusResult1+", "+collegeCode1+", "+adminSection1+", "+stuRegForm.getStudentApplication().getRadioPrev()+", "+stuRegForm.getStudentApplication().getRadioRPL()+", "+
				stuRegForm.getStudentApplication().getRadioNDP()+", applyRetDeclare");
		*/
		if (!getResult1){
			String radioPrev =  stuRegForm.getStudentApplication().getRadioPrev();
			String radioRPL =  stuRegForm.getStudentApplication().getRadioRPL();
			String radioNDP =  stuRegForm.getStudentApplication().getRadioNDP();
			if (!"Y".equalsIgnoreCase(radioPrev)){
				radioPrev = "N";
			}
			if (!"Y".equalsIgnoreCase(radioRPL)){
				radioRPL = "N";
			}
			if (!"Y".equalsIgnoreCase(radioNDP)){
				radioNDP = "N";
			}
			
			//Johanet 2018July BRD - RPL for returning undergrad students
			if (stuRegForm.getSelectHEMain()!=null && stuRegForm.getSelectHEMain().equalsIgnoreCase("RPL")) {
				radioRPL = "Y";
			}
			
			int saveResult1 = dao.saveSTUAPQ(
										newQual1, 
										newSpec1, 
										"1", 
										stuRegForm.getStudent().getNumber(), 
										stuRegForm.getStudent().getAcademicYear(), 
										stuRegForm.getStudent().getAcademicPeriod(), 
										statusResult1, 
										collegeCode1, 
										adminSection1, 
										radioPrev, 
										radioRPL, 
										radioNDP,
										"applyRetDeclare");
			if (saveResult1 == 0) {
				isError1 = true;
			} else {
				isError1 = false;
			}
			//log.debug("ApplyForStudentNumberAction - applyRetDeclare - newQual1 - Saving Qual1 - isError1="+isError1);
			getResult1 = dao.getSTUAPQDetail(stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), "1", "applyRetDeclare");					
			if (!getResult1){
				//log.debug("ApplyForStudentNumberAction - applyRetDeclare - STUAPQ not Saved - Stu=" + stuRegForm.getStudent().getNumber() + " / Qual1=" + newQual1 + " / Spec1=" + newSpec1);
				log.warn("ApplyForStudentNumberAction - applyRetDeclare - STUAPQ not Saved - Stu=" + stuRegForm.getStudent().getNumber() + " / Qual1=" + newQual1 + " / Spec1=" + newSpec1);
			}
	
		}
		
		if (!isError1){
			//Secondary Selection
			//log.debug("ApplyForStudentNumberAction - applyRetDeclare - newQual2 - Starting - Saving STUAPQ");
			getResult2 = dao.getSTUAPQDetail(stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), "2", "applyRetDeclare");
			//log.debug("ApplyForStudentNumberAction - applyRetDeclare - newQual2 - Check STUAPQ Detail= " + getResult2);
	
			for (int i = 0; i < 5; i++){
				if (getResult2){
					int delResult2 = dao.delSTUAPQ(stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), "2", "applyRetDeclare");
					//log.debug("ApplyForStudentNumberAction - applyRetDeclare - newQual2 - Delete STUAPQ ("+i+")= " + delResult2);
					Thread.sleep(1000);
					getResult2 = dao.getSTUAPQDetail(stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), "2", "applyRetDeclare");
				}
			}
			//log.debug("ApplyForStudentNumberAction - applyRetDeclare - newQual2 - No STUAPQ - Saving STUAPQ");
			/*//log.debug("ApplyForStudentNumberAction - applyRetDeclare - newQual2 - STUAPQ Parameters="+
					newQual2+", "+newSpec2+", 2, "+stuRegForm.getStudent().getNumber()+", "+stuRegForm.getStudent().getAcademicYear()+", "+stuRegForm.getStudent().getAcademicPeriod()+", "+
					statusResult1+", "+collegeCode1+", "+adminSection1+", "+stuRegForm.getStudentApplication().getRadioPrev()+", "+stuRegForm.getStudentApplication().getRadioRPL()+", "+
					stuRegForm.getStudentApplication().getRadioNDP()+", applyRetDeclare");
			*/
			if (newQual2 != null && !"0".equals(newQual2) && !"".equals(newQual2.trim()) && !"undefined".equalsIgnoreCase(newQual2)){
				if (!getResult2){
					String radioPrev =  stuRegForm.getStudentApplication().getRadioPrev();
					String radioRPL =  stuRegForm.getStudentApplication().getRadioRPL();
					String radioNDP =  stuRegForm.getStudentApplication().getRadioNDP();
					if (!"Y".equalsIgnoreCase(radioPrev)){
						radioPrev = "N";
					}
					if (!"Y".equalsIgnoreCase(radioRPL)){
						radioRPL = "N";
					}
					if (!"Y".equalsIgnoreCase(radioNDP)){
						radioNDP = "N";
					}
					int saveResult2 = dao.saveSTUAPQ(
											newQual2, 
											newSpec2, 
											"2", 
											stuRegForm.getStudent().getNumber(), 
											stuRegForm.getStudent().getAcademicYear(), 
											stuRegForm.getStudent().getAcademicPeriod(), 
											statusResult2, 
											collegeCode2, 
											adminSection2, 
											radioPrev, 
											radioRPL, 
											radioNDP,
											"applyRetDeclare");
					if (saveResult2 == 0) {
						isError2 = true;
					} else {
						isError2 = false;
					}
					//log.debug("ApplyForStudentNumberAction - applyRetDeclare - newQual2 - Saving Qual2 - isError2="+isError1);
					getResult2 = dao.getSTUAPQDetail(stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), "2", "applyRetDeclare");					
					if (!getResult2){
						//log.debug("ApplyForStudentNumberAction - applyRetDeclare - STUAPQ not Saved - Stu=" + stuRegForm.getStudent().getNumber() + " / Qual2=" + newQual2 + " / Spec2=" + newSpec2);
						log.warn("ApplyForStudentNumberAction - applyRetDeclare - STUAPQ not Saved - Stu=" + stuRegForm.getStudent().getNumber() + " / Qual2=" + newQual2 + " / Spec2=" + newSpec2);
					}
				}
			}
		}
		//log.debug("ApplyForStudentNumberAction - applyRetDeclare - After Save - Error1="+isError1+", Error2="+isError2);
	
		if (isError1){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "An error occurred while saving Primary Qualification. Please try again."));
			addErrors(request, messages);
			return mapping.findForward("applyQualification");
		}else if (isError2){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", "An error occurred while saving Secondary Qualification. Please try again."));
			addErrors(request, messages);
			return mapping.findForward("applyQualification");
		}			
		
		/**2018 Johanet Start of Send Letter**/
		/**2018 July - Johanet Add code for returning student - email application received letter - BRD SR198094 5.1**/
		/**/
		try{
			Staae05sAppAdmissionEvaluator op = new Staae05sAppAdmissionEvaluator();
			operListener opl = new operListener();
			op.addExceptionListener(opl);
			op.clear();

			op.setInCsfClientServerCommunicationsClientVersionNumber((short) 3);
			op.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
			op.setInCsfClientServerCommunicationsAction("PR");
			op.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
			op.setInWsUserNumber(99998);
			log.debug("UploadAction - Upload - (Staae05sAppAdmissionEvaluator) - Academic Year=" + stuRegForm.getStudent().getAcademicYear());
			op.setInWsAcademicYearYear((short) Integer.parseInt(stuRegForm.getStudent().getAcademicYear()));
			op.setInWebStuApplicationQualAcademicYear((short) Integer.parseInt(stuRegForm.getStudent().getAcademicYear()));
			log.debug("UploadAction - Upload - (Staae05sAppAdmissionEvaluator) - Academic Period=" + stuRegForm.getStudent().getAcademicPeriod());
			op.setInWebStuApplicationQualApplicationPeriod((short) Integer.parseInt(stuRegForm.getStudent().getAcademicPeriod()));
			log.debug("UploadAction - Upload - (Staae05sAppAdmissionEvaluator) - Student Number=" + stuRegForm.getStudent().getNumber());
			op.setInWebStuApplicationQualMkStudentNr(Integer.parseInt(stuRegForm.getStudent().getNumber()));
			log.debug("UploadAction - Upload - (Staae05sAppAdmissionEvaluator) - Qual1=" + stuRegForm.getStudent().getQual1());
			op.setInWebStuApplicationQualNewQual(stuRegForm.getStudent().getQual1());
			//log.debug("UploadAction - Upload - (Staae05sAppAdmissionEvaluator) - Choice Nr= 1");
			//op.setInWebStuApplicationQualChoiceNr((short) 1);
			//Get Current Status
			//log.debug("UploadAction - Upload - (Staae05sAppAdmissionEvaluator) - Get Basic Status");
			//String status = applyDAO.getBasicStatus(stuRegForm.getStudent().getNumber(),stuRegForm.getStudent().getAcademicYear(),stuRegForm.getStudent().getAcademicPeriod(), "1");
			//log.debug("UploadAction - Upload - (Staae05sAppAdmissionEvaluator) - Basic Status="+status);
			//op.setInWebStuApplicationQualStatusCode(status);
			
			log.debug("UploadAction - Upload - (Staae05sAppAdmissionEvaluator) - Execute");

			op.execute();

			if (opl.getException() != null) throw opl.getException();
			if (op.getExitStateType() < 3) throw new Exception(op.getExitStateMsg());

			log.debug("UploadAction - Upload - Staae05sAppAdmissionEvaluator - After Execute");
			String opResult = "No Result";
			opResult = op.getOutCsfStringsString500();
			log.debug("UploadAction - Upload - (Staae05sAppAdmissionEvaluator) opResult: " + opResult);
		}catch(Exception e){
			log.debug("Unisa-StudentRegistration - UploadAction - Upload - Staae05sAppAdmissionEvaluator - After Execute / sessionID=" + request.getSession().getId() + " / Error=" + e );
			log.warn("Unisa-StudentRegistration - UploadAction - Upload - Staae05sAppAdmissionEvaluator - After Execute / sessionID=" + request.getSession().getId() + " / Error=" + e );
		}
		/**End of Send Letter**/
		
		//log.debug("ApplyForStudentNumberAction - applyRetDeclare -  Save Qualification to STUAPQ - End");
		//Save Qualification to STUAPQ - End
		
		//Save Previous Qualifications - Start
		int resultUpdate = 0;
		int resultSave = 0;
		//Save
		//log.debug("ApplyForStudentNumberAction - applyRetDeclare - studentNr=" + stuRegForm.getStudent().getNumber()+", Do Save/Debug");
		if (stuRegForm.getQualArray() != null && !stuRegForm.getQualArray().isEmpty()){
			//List now starts at 0;
			if (stuRegForm.getQualArray().size() > 0){
				HistoryArray other = new HistoryArray();
				for (int i=0; i < stuRegForm.getQualArray().size(); i++){
					other = (HistoryArray) stuRegForm.getQualArray().get(i);
					//Debug
					//log.debug("ApplyForStudentNumberAction - applyRetDeclare - studentNr=" + stuRegForm.getStudent().getNumber()+", Do Save/Debug - Found="+i+", historyOTHERSEQ="+other.getOtherSEQ());
					
					//log.debug("ApplyForStudentNumberAction - applyRetDeclare - studentNr=" + stuRegForm.getStudent().getNumber()+", Do Save/Debug - Found="+i+", historyOTHERUniv="+other.getOtherUniv());
					//log.debug("ApplyForStudentNumberAction - applyRetDeclare - studentNr=" + stuRegForm.getStudent().getNumber()+", Do Save/Debug - Found="+i+", historyOTHERUnivText="+other.getOtherUnivText());
					//log.debug("ApplyForStudentNumberAction - applyRetDeclare - studentNr=" + stuRegForm.getStudent().getNumber()+", Do Save/Debug - Found="+i+", historyOTHERStudnr="+other.getOtherStudnr());
					//log.debug("ApplyForStudentNumberAction - applyRetDeclare - studentNr=" + stuRegForm.getStudent().getNumber()+", Do Save/Debug - Found="+i+", historyOTHERQual="+other.getOtherQual());
					
					//log.debug("ApplyForStudentNumberAction - applyRetDeclare - studentNr=" + stuRegForm.getStudent().getNumber()+", Do Save/Debug - Found="+i+", historyOTHERYearStart="+other.getOtherYearStart());
					//log.debug("ApplyForStudentNumberAction - applyRetDeclare - studentNr=" + stuRegForm.getStudent().getNumber()+", Do Save/Debug - Found="+i+", historyOTHERYearEnd="+other.getOtherYearEnd());
					//log.debug("ApplyForStudentNumberAction - applyRetDeclare - studentNr=" + stuRegForm.getStudent().getNumber()+", Do Save/Debug - Found="+i+", historyOTHERCountry="+other.getOtherCountry());
					
					//log.debug("ApplyForStudentNumberAction - applyRetDeclare - studentNr=" + stuRegForm.getStudent().getNumber()+", Do Save/Debug - Found="+i+", historyOTHERLock="+other.getOtherLock());
					//log.debug("ApplyForStudentNumberAction - applyRetDeclare - studentNr=" + stuRegForm.getStudent().getNumber()+", Do Save/Debug - Found="+i+", historyOTHERForeign="+other.getOtherForeign());
					//log.debug("ApplyForStudentNumberAction - applyRetDeclare - studentNr=" + stuRegForm.getStudent().getNumber()+", Do Save/Debug - Found="+i+", historyOTHERComplete="+other.getOtherComplete());		
	
					//log.debug("ApplyForStudentNumberAction - applyRetDeclare - studentNr=" + stuRegForm.getStudent().getNumber()+", Do Save");
					//Save PrevQual
					boolean exists = dao.checkPREV(stuRegForm.getStudent().getNumber(), other.getOtherSEQ());
					if (exists){
						//Write STUXML Entry to save Sequence number
						String saveSTUXML =  dao.saveSTUXML(stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), "stuPrev", "1", other.getOtherSEQ(),  "YES",  "stepPrevQual", "UPDATE");
						//Update only if Final year has changed and row is not locked
						if ("N".equalsIgnoreCase(other.getOtherLock())){
							resultUpdate = dao.updatePREVADM(stuRegForm.getStudent().getNumber(), other.getOtherSEQ(), other.getOtherYearEnd(), other.getOtherComplete());
							//log.debug("ApplyForStudentNumberAction - applyRetDeclare - studentNr=" + stuRegForm.getStudent().getNumber()+", RecordNo="+i+", resultUpdate="+resultUpdate);
						}
					}else{
						// Save to database if there are any Qualifications added
						int maxPrev = 0;
						maxPrev = dao.getSTUPREVMax(stuRegForm.getStudent().getNumber());
						//log.debug("ApplyForStudentNumberAction - applyRetDeclare - studentNr=" + stuRegForm.getStudent().getNumber()+", RecordNo="+i+", maxPrev="+maxPrev);
						maxPrev++;
						//log.debug("ApplyForStudentNumberAction - applyRetDeclare - studentNr=" + stuRegForm.getStudent().getNumber()+", RecordNo="+i+", maxPrev Plus 1="+maxPrev);
	
						//Write STUXML Entry to save Sequence number
						String saveSTUXML =  dao.saveSTUXML(stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), "stuPrev", "1", other.getOtherSEQ(),  "YES",  "stepPrevQual", "INSERT");
							resultSave = dao.savePREVADM(stuRegForm.getStudent().getNumber(), maxPrev, other.getOtherYearStart(),
								other.getOtherYearEnd(), other.getOtherUnivText(), 
								other.getOtherStudnr(), other.getOtherQual(), other.getOtherForeign(), 
								other.getOtherComplete(), other.getOtherUniv(), other.getOtherCountry());
						//log.debug("ApplyForStudentNumberAction - applyRetDeclare - studentNr=" + stuRegForm.getStudent().getNumber()+", RecordNo="+i+", saveSTUXML="+saveSTUXML+", resultSave="+resultSave);
					}
					
				}
				//log.debug("ApplyForStudentNumberAction - applyRetDeclare - studentNr=" + stuRegForm.getStudent().getNumber()+", Save Done");
			}else{
				//log.debug("ApplyForStudentNumberAction - applyRetDeclare - studentNr=" + stuRegForm.getStudent().getNumber()+", No Qualifications Entered or found!");
			}
			//Save Previous Qualifications - End
		}
		
		//Check if NDP Modules exist. If not, insert them.
		//log.debug("ApplyForStudentNumberAction - applyRetDeclare -  Check if NDP Modules");
		if ("Y".equals(stuRegForm.getStudentApplication().getRadioNDP())){
			//log.debug("ApplyForStudentNumberAction - applyRetDeclare - Save NDP Modules");
			if ((stuRegForm.getStudentApplication().getNdpRegSu1() != null && !"".equals(stuRegForm.getStudentApplication().getNdpRegSu1())) && ("00019".equals(stuRegForm.getStudent().getQual1()) || "00019".equals(stuRegForm.getStudent().getQual2()))) {
				boolean isNDP1 = dao.getNDPMod(stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getStudent().getNumber(), "00019", stuRegForm.getStudentApplication().getNdpRegSu1());
			
				if (!isNDP1){
					dao.saveNDPMod(stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getStudent().getNumber(), "00019", stuRegForm.getStudentApplication().getNdpRegSu1());
					//log.debug("ApplyForStudentNumberAction - applyRetDeclare - Saving NDP Module (1) = "+stuRegForm.getStudentApplication().getNdpRegSu1());
				}
			}
			if ((stuRegForm.getStudentApplication().getNdpRegSu2() != null && !"".equals(stuRegForm.getStudentApplication().getNdpRegSu2())) && ("00019".equals(stuRegForm.getStudent().getQual1()) || "00019".equals(stuRegForm.getStudent().getQual2()))) {
				boolean isNDP2 = dao.getNDPMod(stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getStudent().getNumber(), "00019", stuRegForm.getStudentApplication().getNdpRegSu2());
			
				if (!isNDP2){
					dao.saveNDPMod(stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getStudent().getNumber(), "00019", stuRegForm.getStudentApplication().getNdpRegSu2());
					//log.debug("ApplyForStudentNumberAction - applyRetDeclare - Saving NDP Module (2) = "+stuRegForm.getStudentApplication().getNdpRegSu2());
				}
			}
			if ((stuRegForm.getStudentApplication().getNdpRegSu3() != null && !"".equals(stuRegForm.getStudentApplication().getNdpRegSu3())) && ("00019".equals(stuRegForm.getStudent().getQual1()) || "00019".equals(stuRegForm.getStudent().getQual2()))) {
				boolean isNDP3 = dao.getNDPMod(stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getStudent().getNumber(), "00019", stuRegForm.getStudentApplication().getNdpRegSu3());
			
				if (!isNDP3){
					dao.saveNDPMod(stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getStudent().getNumber(), "00019", stuRegForm.getStudentApplication().getNdpRegSu3());
					//log.debug("ApplyForStudentNumberAction - applyRetDeclare - Saving NDP Module (3) = "+stuRegForm.getStudentApplication().getNdpRegSu3());
				}
			}
			if ((stuRegForm.getStudentApplication().getNdpRegSu4() != null && !"".equals(stuRegForm.getStudentApplication().getNdpRegSu4())) && ("00019".equals(stuRegForm.getStudent().getQual1()) || "00019".equals(stuRegForm.getStudent().getQual2()))) {
				boolean isNDP4 = dao.getNDPMod(stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getStudent().getNumber(), "00019", stuRegForm.getStudentApplication().getNdpRegSu4());
			
				if (!isNDP4){
					dao.saveNDPMod(stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getStudent().getNumber(), "00019", stuRegForm.getStudentApplication().getNdpRegSu4());
					//log.debug("ApplyForStudentNumberAction - applyRetDeclare - Saving NDP Module (4) = "+stuRegForm.getStudentApplication().getNdpRegSu4());
				}
			}
			if ((stuRegForm.getStudentApplication().getNdpRegSu5() != null && !"".equals(stuRegForm.getStudentApplication().getNdpRegSu5())) && ("00019".equals(stuRegForm.getStudent().getQual1()) || "00019".equals(stuRegForm.getStudent().getQual2()))) {
				boolean isNDP5 = dao.getNDPMod(stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getStudent().getNumber(), "00019", stuRegForm.getStudentApplication().getNdpRegSu5());
			
				if (!isNDP5){
					dao.saveNDPMod(stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getStudent().getNumber(), "00019", stuRegForm.getStudentApplication().getNdpRegSu5());
					//log.debug("ApplyForStudentNumberAction - applyRetDeclare - Saving NDP Module (5) = "+stuRegForm.getStudentApplication().getNdpRegSu5());
				}
			}if ((stuRegForm.getStudentApplication().getNdpRegSu6() != null && !"".equals(stuRegForm.getStudentApplication().getNdpRegSu6())) && ("00019".equals(stuRegForm.getStudent().getQual1()) || "00019".equals(stuRegForm.getStudent().getQual2()))) {
				boolean isNDP6 = dao.getNDPMod(stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getStudent().getNumber(), "00019", stuRegForm.getStudentApplication().getNdpRegSu6());
			
				if (!isNDP6){
					dao.saveNDPMod(stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getStudent().getNumber(), "00019", stuRegForm.getStudentApplication().getNdpRegSu6());
					//log.debug("ApplyForStudentNumberAction - applyRetDeclare - Saving NDP Module (6) = "+stuRegForm.getStudentApplication().getNdpRegSu6());
				}
			}
			if ((stuRegForm.getStudentApplication().getNdpRegSu7() != null && !"".equals(stuRegForm.getStudentApplication().getNdpRegSu7())) && ("00019".equals(stuRegForm.getStudent().getQual1()) || "00019".equals(stuRegForm.getStudent().getQual2()))) {
				boolean isNDP7 = dao.getNDPMod(stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getStudent().getNumber(), "00019", stuRegForm.getStudentApplication().getNdpRegSu7());
			
				if (!isNDP7){
					dao.saveNDPMod(stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getStudent().getNumber(), "00019", stuRegForm.getStudentApplication().getNdpRegSu7());
					//log.debug("ApplyForStudentNumberAction - applyRetDeclare - Saving NDP Module (7) = "+stuRegForm.getStudentApplication().getNdpRegSu7());
				}
			}
			if ((stuRegForm.getStudentApplication().getNdpRegSu8() != null && !"".equals(stuRegForm.getStudentApplication().getNdpRegSu8())) && ("00019".equals(stuRegForm.getStudent().getQual1()) || "00019".equals(stuRegForm.getStudent().getQual2()))) {
				boolean isNDP8 = dao.getNDPMod(stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getStudent().getNumber(), "00019", stuRegForm.getStudentApplication().getNdpRegSu8());
			
				if (!isNDP8){
					dao.saveNDPMod(stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getStudent().getNumber(), "00019", stuRegForm.getStudentApplication().getNdpRegSu8());
					//log.debug("ApplyForStudentNumberAction - applyRetDeclare - Saving NDP Module (8) = "+stuRegForm.getStudentApplication().getNdpRegSu8());
				}
			}
			if ((stuRegForm.getStudentApplication().getNdpRegSu9() != null && !"".equals(stuRegForm.getStudentApplication().getNdpRegSu9())) && ("00019".equals(stuRegForm.getStudent().getQual1()) || "00019".equals(stuRegForm.getStudent().getQual2()))) {
				boolean isNDP9 = dao.getNDPMod(stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getStudent().getNumber(), "00019", stuRegForm.getStudentApplication().getNdpRegSu9());
			
				if (!isNDP9){
					dao.saveNDPMod(stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getStudent().getNumber(), "00019", stuRegForm.getStudentApplication().getNdpRegSu9());
					//log.debug("ApplyForStudentNumberAction - applyRetDeclare - Saving NDP Module (9) = "+stuRegForm.getStudentApplication().getNdpRegSu9());
				}
			}
			if ((stuRegForm.getStudentApplication().getNdpRegSu10() != null && !"".equals(stuRegForm.getStudentApplication().getNdpRegSu10())) && ("00019".equals(stuRegForm.getStudent().getQual1()) || "00019".equals(stuRegForm.getStudent().getQual2()))) {
				boolean isNDP10 = dao.getNDPMod(stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getStudent().getNumber(), "00019", stuRegForm.getStudentApplication().getNdpRegSu10());
			
				if (!isNDP10){
					dao.saveNDPMod(stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getStudent().getNumber(), "00019", stuRegForm.getStudentApplication().getNdpRegSu10());
					//log.debug("ApplyForStudentNumberAction - applyRetDeclare - Saving NDP Module (10) = "+stuRegForm.getStudentApplication().getNdpRegSu10());
				}
			}
		}else{
			//log.debug("ApplyForStudentNumberAction - applyRetDeclare - NDP = No, no Modules to save");
		}
	
		
		/* Set submission time stamp */
		Date date = new java.util.Date();
		String displayDate = (new java.text.SimpleDateFormat("EEEEE dd MMMMM yyyy hh:mm:ss").format(date).toString());
		stuRegForm.getStudent().setAppTime(displayDate);
		request.setAttribute("email",stuRegForm.getStudent().getEmailAddress());
		
		/*Write work flow document */
		//log.debug("ApplyForStudentNumberAction - applyRetDeclare -  writeWorkflowRet: " + date);
		writeWorkflowRet(stuRegForm,date);
			
		//2018 Edmund - Add code so student cannot submit more than once, even if an error occurs.
		//stuRegForm.setDoneSubmit(true);
		
		//log.debug("ApplyForStudentNumberAction - applyRetDeclare -  GoTo Upload");
		return mapping.findForward("dynamicUpload");
	}
	
	public ActionForward applyNDP(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//log.debug("ApplyForStudentNumberaction - applyNDP - Start");

		// if student number is null, block application
		ActionMessages messages = new ActionMessages();
		StudentRegistrationForm stuRegForm = (StudentRegistrationForm) form;

		stuRegForm.setFromPage("applyNDP");
		
		if(stuRegForm.getStudent().getNumber() == null || "".equals(stuRegForm.getStudent().getNumber())){
			//log.debug("ApplyForStudentNumberAction - applyNDP - Student Nr is null or empty when saving qualifications");
			messages.add(ActionMessages.GLOBAL_MESSAGE,
			new ActionMessage("message.generalmessage", "You performed and invalid action or an Error occurred. Please log on again to retry."));
			addErrors(request, messages);
			if (stuRegForm.getAdminStaff().isAdmin()){
				return mapping.findForward("loginStaff");
			}else{
				return mapping.findForward("loginStu");
			}
		}

		stuRegForm.getStudentApplication().setRadioPrev(stripXSS(stuRegForm.getStudentApplication().getRadioPrev(), "RadioPrev", "applyNDP", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
		if (stuRegForm.getStudentApplication().getRadioPrev() == null || "".equalsIgnoreCase(stuRegForm.getStudentApplication().getRadioPrev())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please indicate if you have previously applied for postgraduate qualifications at Unisa."));
			addErrors(request, messages);
			return mapping.findForward("applyNDP");
		}
		//log.debug("ApplyForStudentNumberaction - applyNDP - RadioPrev=" + stuRegForm.getStudentApplication().getRadioPrev());

		stuRegForm.getStudentApplication().setRadioRPL(stripXSS(stuRegForm.getStudentApplication().getRadioRPL(), "RadioRPL", "applyNDP", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
		if (stuRegForm.getStudentApplication().getRadioRPL() == null || "".equalsIgnoreCase(stuRegForm.getStudentApplication().getRadioRPL())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please indicate if you applied for direct admission via the Recognition of Prior Learning (RPL) route."));
			addErrors(request, messages);
			return mapping.findForward("applyNDP");
		}
		//log.debug("ApplyForStudentNumberaction - applyNDP - RadioRPL=" + stuRegForm.getStudentApplication().getRadioRPL());

		stuRegForm.getStudentApplication().setRadioNDP(stripXSS(stuRegForm.getStudentApplication().getRadioNDP(), "RadioNDP", "applyNDP", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
		if (stuRegForm.getStudentApplication().getRadioNDP() == null || "".equalsIgnoreCase(stuRegForm.getStudentApplication().getRadioNDP())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please indicate if you have completed or if you wish to register for any non-degree purposes (NDP) module/s to gain admission to a specific qualification."));
			addErrors(request, messages);
			return mapping.findForward("applyNDP");
		}
		//log.debug("ApplyForStudentNumberaction - applyNDP - RadioNDP=" + stuRegForm.getStudentApplication().getRadioNDP());

		if ("Y".equalsIgnoreCase(stuRegForm.getStudentApplication().getRadioNDP())){
			if (stuRegForm.getStudentApplication().getNdpRegSu1() == null || "".equalsIgnoreCase(stuRegForm.getStudentApplication().getNdpRegSu1().toString().trim()) || stuRegForm.getStudentApplication().getNdpRegSu1().toString().trim().length() < 1){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Please enter at least one Study Unit or Module completed or currently registered for NDP purposes."));
				addErrors(request, messages);
				return mapping.findForward("applyNDP");
			}
			
			//ToDo Convert to Array
			if (request.getParameter("studentApplication.ndpRegSu1") != null && request.getParameter("studentApplication.ndpRegSu1").trim().length() > 7){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Please enter a valid Study module. "+request.getParameter("studentApplication.ndpRegSu1").trim() ));
					addErrors(request, messages);
					return mapping.findForward("applyNDP");
			}else{
				stuRegForm.getStudentApplication().setNdpRegSu1(stripXSS(request.getParameter("studentApplication.ndpRegSu1").trim(), "NdpRegSu1", "applyNDP", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
				//log.debug("ApplyForStudentNumberaction - applyNDP - setNdpRegSu1=" + stuRegForm.getStudentApplication().getNdpRegSu1());
			}
			if (request.getParameter("studentApplication.ndpRegSu2") != null && request.getParameter("studentApplication.ndpRegSu2").trim().length() > 7){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Please enter a valid Study module. "+request.getParameter("studentApplication.ndpRegSu2").trim() ));
					addErrors(request, messages);
					return mapping.findForward("applyNDP");
			}else{
				stuRegForm.getStudentApplication().setNdpRegSu2(stripXSS(request.getParameter("studentApplication.ndpRegSu2").trim(), "NdpRegSu2", "applyNDP", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
				//log.debug("ApplyForStudentNumberaction - applyNDP - setNdpRegSu2=" + stuRegForm.getStudentApplication().getNdpRegSu2());
			}
			
			if (request.getParameter("studentApplication.ndpRegSu3") != null && request.getParameter("studentApplication.ndpRegSu3").trim().length() > 7){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Please enter a valid Study module. "+request.getParameter("studentApplication.ndpRegSu3").trim() ));
					addErrors(request, messages);
					return mapping.findForward("applyNDP");
			}else{
				stuRegForm.getStudentApplication().setNdpRegSu3(stripXSS(request.getParameter("studentApplication.ndpRegSu3").trim(), "NdpRegSu3", "applyNDP", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
				//log.debug("ApplyForStudentNumberaction - applyNDP - setNdpRegSu3=" + stuRegForm.getStudentApplication().getNdpRegSu3());
			}
			if (request.getParameter("studentApplication.ndpRegSu4") != null && request.getParameter("studentApplication.ndpRegSu4").trim().length() > 7){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Please enter a valid Study module. "+request.getParameter("studentApplication.ndpRegSu4").trim() ));
					addErrors(request, messages);
					return mapping.findForward("applyNDP");
			}else{
				stuRegForm.getStudentApplication().setNdpRegSu4(stripXSS(request.getParameter("studentApplication.ndpRegSu4").trim(), "NdpRegSu4", "applyNDP", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
				//log.debug("ApplyForStudentNumberaction - applyNDP - setNdpRegSu4=" + stuRegForm.getStudentApplication().getNdpRegSu4());
			}
			if (request.getParameter("studentApplication.ndpRegSu5") != null && request.getParameter("studentApplication.ndpRegSu5").trim().length() > 7){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Please enter a valid Study module. "+request.getParameter("studentApplication.ndpRegSu5").trim() ));
					addErrors(request, messages);
					return mapping.findForward("applyNDP");
			}else{
				stuRegForm.getStudentApplication().setNdpRegSu5(stripXSS(request.getParameter("studentApplication.ndpRegSu5").trim(), "NdpRegSu5", "applyNDP", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
				//log.debug("ApplyForStudentNumberaction - applyNDP - setNdpRegSu5=" + stuRegForm.getStudentApplication().getNdpRegSu5());
			}
			if (request.getParameter("studentApplication.ndpRegSu6") != null && request.getParameter("studentApplication.ndpRegSu6").trim().length() > 7){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Please enter a valid Study module. "+request.getParameter("studentApplication.ndpRegSu6").trim() ));
					addErrors(request, messages);
					return mapping.findForward("applyNDP");
			}else{
				stuRegForm.getStudentApplication().setNdpRegSu6(stripXSS(request.getParameter("studentApplication.ndpRegSu6").trim(), "NdpRegSu6", "applyNDP", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
				//log.debug("ApplyForStudentNumberaction - applyNDP - setNdpRegSu6=" + stuRegForm.getStudentApplication().getNdpRegSu6());
			}
			if (request.getParameter("studentApplication.ndpRegSu7") != null && request.getParameter("studentApplication.ndpRegSu7").trim().length() > 7){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Please enter a valid Study module. "+request.getParameter("studentApplication.ndpRegSu7").trim() ));
					addErrors(request, messages);
					return mapping.findForward("applyNDP");
			}else{
				stuRegForm.getStudentApplication().setNdpRegSu7(stripXSS(request.getParameter("studentApplication.ndpRegSu7").trim(), "NdpRegSu7", "applyNDP", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
				//log.debug("ApplyForStudentNumberaction - applyNDP - setNdpRegSu7=" + stuRegForm.getStudentApplication().getNdpRegSu7());
			}
			if (request.getParameter("studentApplication.ndpRegSu8") != null && request.getParameter("studentApplication.ndpRegSu8").trim().length() > 7){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Please enter a valid Study module. "+request.getParameter("studentApplication.ndpRegSu8").trim() ));
					addErrors(request, messages);
					return mapping.findForward("applyNDP");
			}else{
				stuRegForm.getStudentApplication().setNdpRegSu8(stripXSS(request.getParameter("studentApplication.ndpRegSu8").trim(), "NdpRegSu8", "applyNDP", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
				//log.debug("ApplyForStudentNumberaction - applyNDP - setNdpRegSu8=" + stuRegForm.getStudentApplication().getNdpRegSu8());
			}
			if (request.getParameter("studentApplication.ndpRegSu9") != null && request.getParameter("studentApplication.ndpRegSu9").trim().length() > 7){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Please enter a valid Study module. "+request.getParameter("studentApplication.ndpRegSu9").trim() ));
					addErrors(request, messages);
					return mapping.findForward("applyNDP");
			}else{
				stuRegForm.getStudentApplication().setNdpRegSu9(stripXSS(request.getParameter("studentApplication.ndpRegSu9").trim(), "NdpRegSu9", "applyNDP", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
				//log.debug("ApplyForStudentNumberaction - applyNDP - setNdpRegSu9=" + stuRegForm.getStudentApplication().getNdpRegSu9());
			}
			if (request.getParameter("studentApplication.ndpRegSu10") != null && request.getParameter("studentApplication.ndpRegSu10").trim().length() > 7){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Please enter a valid Study module. "+request.getParameter("studentApplication.ndpRegSu10").trim() ));
					addErrors(request, messages);
					return mapping.findForward("applyNDP");
			}else{
				stuRegForm.getStudentApplication().setNdpRegSu10(stripXSS(request.getParameter("studentApplication.ndpRegSu10").trim(), "NdpRegSu10", "applyNDP", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
				//log.debug("ApplyForStudentNumberaction - applyNDP - setNdpRegSu10=" + stuRegForm.getStudentApplication().getNdpRegSu10());
			}
		}

		if ("newStu".equalsIgnoreCase(stuRegForm.getStudent().getStuExist())){
			//log.debug("ApplyForStudentNumberAction - applyNDP - NewStu - GoTo applyNewPersonal");
			setDropdownListsStep1(request,stuRegForm);
			return mapping.findForward("applyNewPersonal");
		}else{
			//log.debug("ApplyForStudentNumberAction - applyNDP - CurStu - GoTo applyRetContact");
			return mapping.findForward("applyRetContact");
		}
	}

	@SuppressWarnings("unused")
	public String applyNewContact(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		//log.debug("ApplyForStudentNumberAction - applyNewContact - Start");
		
		// if student number is null, block application
		ActionMessages messages = new ActionMessages();
		StudentRegistrationForm stuRegForm = (StudentRegistrationForm) form;
		if(stuRegForm.getStudent().getNumber() == null || "".equals(stuRegForm.getStudent().getNumber())){
			//log.debug("ApplyForStudentNumberAction SaveStudyAction - Student Nr is null or empty when saving qualifications");
			messages.add(ActionMessages.GLOBAL_MESSAGE,
			new ActionMessage("message.generalmessage", "You performed and invalid action or an Error occurred. Please log on again to retry."));
			addErrors(request, messages);
			if (stuRegForm.getAdminStaff().isAdmin()){
				return "loginStaff";
			}else{
				return "loginStu";
			}
		}
		GeneralMethods gen = new GeneralMethods();

		if (stuRegForm.getFromPage() == null){
			//Error: Session empty
			//log.debug("ApplyForStudentNumberAction - applyNewContact Empty Session");
			return emptySession(mapping,form,request,response);
		}else{
			stuRegForm.setFromPage("page3");
		}

		String qualif = stuRegForm.getQual().getQualCode();
		// ---------- Check input

		//remove spaces
		if (stuRegForm.getStudent()!= null){
			stuRegForm.getStudent().setCellNr(stuRegForm.getStudent().getCellNr().replaceAll(" ",""));
		}

		// check contact numbers
		stuRegForm.getStudent().setHomePhone(stripXSS(stuRegForm.getStudent().getHomePhone(), "HomePhone", "NewContact", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
		if(stuRegForm.getStudent().getHomePhone()== null || "".equals(stuRegForm.getStudent().getHomePhone())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
        			new ActionMessage("message.generalmessage", "Your home phone number is required."));
			addErrors(request, messages);
			return "applyNewContact";
		}
		if (stuRegForm.getStudent().getHomePhone()!= null){
			stuRegForm.getStudent().setHomePhone(stuRegForm.getStudent().getHomePhone().replaceAll(" ",""));
		}
//		if(!isValidNumber(stuRegForm.getStudent().getHomePhone())){
		if(!isPhoneNumber(stuRegForm.getStudent().getHomePhone())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
        			new ActionMessage("message.generalmessage", "Your home phone number may consist of a dash or +, the rest must be numeric."));
			addErrors(request, messages);
			return "applyNewContact";
		}

		stuRegForm.getStudent().setWorkPhone(stripXSS(stuRegForm.getStudent().getWorkPhone(), "WorkPhone", "NewContact", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
		if (stuRegForm.getStudent().getWorkPhone()!= null){
			stuRegForm.getStudent().setWorkPhone(stuRegForm.getStudent().getWorkPhone().replaceAll(" ",""));
		}
//		if(!isValidNumber(stuRegForm.getStudent().getWorkPhone())){
		if(!isPhoneNumber(stuRegForm.getStudent().getWorkPhone())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
        			new ActionMessage("message.generalmessage", "Your work phone number may consist of a dash or +, the rest must be numeric."));
			addErrors(request, messages);
			return "applyNewContact";
		}
		stuRegForm.getStudent().setFaxNr(stripXSS(stuRegForm.getStudent().getFaxNr(), "FaxNr", "NewContact", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
		if (stuRegForm.getStudent().getFaxNr()!= null){
			stuRegForm.getStudent().setFaxNr(stuRegForm.getStudent().getFaxNr().replaceAll(" ",""));
		}
//		if(!isValidNumber(stuRegForm.getStudent().getFaxNr())){
		if(!isPhoneNumber(stuRegForm.getStudent().getFaxNr())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
  			new ActionMessage("message.generalmessage", "Your fax number may consist of a dash or +, the rest must be numeric."));
			addErrors(request, messages);
			return "applyNewContact";
		}

//		cell number
		stuRegForm.getStudent().setCellNr(stripXSS(stuRegForm.getStudent().getCellNr(), "CellNr", "NewContact", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
		stuRegForm.getStudent().setCellNr2(stripXSS(stuRegForm.getStudent().getCellNr2(), "CellNr2", "NewContact", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));

		if (stuRegForm.getStudent().getCellNr().trim() == null || "".equalsIgnoreCase(stuRegForm.getStudent().getCellNr().trim())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", "Please enter a cellular phone number."));
			addErrors(request, messages);
			return "applyNewContact";
		}
		
		if(!isPhoneNumber(stuRegForm.getStudent().getCellNr())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
  			new ActionMessage("message.generalmessage", "Your cell number may consist of a dash or +, the rest must be numeric."));
			addErrors(request, messages);
			return "applyNewContact";
		}

		if (stuRegForm.getStudent().getCellNr().trim().length() > 20){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Cellular number is too long. Please enter 20 characters or less. " ));
			addErrors(request, messages);
			return "applyNewContact";
		}
		
		if ((stuRegForm.getStudent().getCellNr() != null && !"".equalsIgnoreCase(stuRegForm.getStudent().getCellNr().trim())) &&
				(stuRegForm.getStudent().getCellNr2() == null || "".equalsIgnoreCase(stuRegForm.getStudent().getCellNr2().trim()))){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Please confirm cellular phone number."));
					addErrors(request, messages);
					return "applyNewContact";

			}

		if (!stuRegForm.getStudent().getCellNr().trim().equalsIgnoreCase(stuRegForm.getStudent().getCellNr2().trim())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Entered cellular phone numbers do not match."));
			addErrors(request, messages);
			return "applyNewContact";
		}
		
		// Email
		//log.debug("ApplyForStudentNumberAction - applyStep3 - Email1: " + stuRegForm.getStudent().getEmailAddress().trim());
		//log.debug("ApplyForStudentNumberAction - applyStep3 - Email2: " + stuRegForm.getStudent().getEmailAddress2().trim());

		boolean hasEmail = false;
		boolean hasEmail2 = false;
		
		stuRegForm.getStudent().setEmailAddress(stripXSS(stuRegForm.getStudent().getEmailAddress(), "EmailAddress", "NewContact", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
		stuRegForm.getStudent().setEmailAddress2(stripXSS(stuRegForm.getStudent().getEmailAddress2(), "EmailAddress2", "NewContact", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));

		if (stuRegForm.getStudent().getEmailAddress() == null || "".equals(stuRegForm.getStudent().getEmailAddress().trim())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Enter your e-mail address."));
			addErrors(request, messages);
			return "applyNewContact";
		}
		
		//Removed as per Vinesh - Email address is now compulsory
		/*
		if ((stuRegForm.getStudent().getEmailAddress() == null || "".equals(stuRegForm.getStudent().getEmailAddress().trim())) &&
			(stuRegForm.getStudent().getCellNr() == null || "".equals(stuRegForm.getStudent().getCellNr().trim()))){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Enter either your e-mail address or cellular number."));
			addErrors(request, messages);
			return "applyNewContact";
		}*/


		if (stuRegForm.getStudent().getEmailAddress() != null && !"".equalsIgnoreCase(stuRegForm.getStudent().getEmailAddress().trim())){
				hasEmail = true;
		}
		
		if (hasEmail && (stuRegForm.getStudent().getEmailAddress2() == null || "".equalsIgnoreCase(stuRegForm.getStudent().getEmailAddress2().trim()))){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Please confirm e-mail address."));
				addErrors(request, messages);
				return "applyNewContact";
		}


		if (hasEmail && (!stuRegForm.getStudent().getEmailAddress().trim().equalsIgnoreCase(stuRegForm.getStudent().getEmailAddress2().trim()))){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Entered e-mail addresses do not match."));
				addErrors(request, messages);
				return "applyNewContact";
		}else{
			hasEmail2 = true;
		}
		
		if (hasEmail && hasEmail2){
			String checkMail = stuRegForm.getStudent().getEmailAddress();
			//String checkMail2 = stuRegForm.getStudent().getEmailAddress2();
			Integer eCheckIndex=checkMail.indexOf("mylife.unisa");
			//Integer eCheckIndex2=checkMail2.indexOf("mylife.unisa");
			
			if (eCheckIndex >= 1) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Please enter a valid E-mail address other than mylife.unisa."));
				addErrors(request, messages);
				return "applyNewContact";
				
			}
			//if (eCheckIndex2 >= 1) {
			//	messages.add(ActionMessages.GLOBAL_MESSAGE,
			//			new ActionMessage("message.generalmessage", "Please enter a valid E-mail address other than mylife.unisa."));
			//	addErrors(request, messages);
			//	return "step1aforward";
			//	
			//}
			Boolean chkEmail = isEmailValid(stuRegForm.getStudent().getEmailAddress());
			if (!chkEmail) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Please ensure you typed your e-mail address correctly."));
				addErrors(request, messages);
				return "applyNewContact";
			}

		}
		//log.debug("ApplyForStudentNumberAction - applyNewContact - End");
		//log.debug("ApplyForStudentNumberAction - applyNewContact - Goto applyNewAddress");
		setDropdownListsStep2(request, form);
		return "applyNewAddress1";
	}
	

	public String applyNewAddress1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//log.debug("ApplyForStudentNumberAction - applyNewAddress1 - Start");

		// if student number is null, block application
		ActionMessages messages = new ActionMessages();
		StudentRegistrationForm stuRegForm = (StudentRegistrationForm) form;
		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
		
		if(stuRegForm.getStudent().getNumber() == null || "".equals(stuRegForm.getStudent().getNumber())){
			//log.debug("ApplyForStudentNumberAction - applyNewAddress1 - Student Nr is null or empty when saving qualifications");
			messages.add(ActionMessages.GLOBAL_MESSAGE,
			new ActionMessage("message.generalmessage", "You performed and invalid action or an Error occurred. Please log on again to retry."));
			addErrors(request, messages);
			if (stuRegForm.getAdminStaff().isAdmin()){
				return "loginStaff";
			}else{
				return "loginStu";
			}
		}
		GeneralMethods gen = new GeneralMethods();

		if (stuRegForm.getFromPage() == null){
			//Error: Session empty
			//log.debug("ApplyForStudentNumberAction - applyNewAddress1 Empty Session");
			return emptySession(mapping,form,request,response);
		}else{
			stuRegForm.setFromPage("applyNewAddress1");
		}

		// Set country
		stuRegForm.setSelectedCountry(stripXSS(stuRegForm.getSelectedCountry(), "SelectedCountry", "NewAddres1", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
		if (stuRegForm.getSelectedCountry() == null || "-1".equals(stuRegForm.getSelectedCountry()) || "0".equals(stuRegForm.getSelectedCountry())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please select a country."));
			addErrors(request, messages);
			setDropdownListsStep2(request, form);
			return "applyNewAddress1";
			
		}else{
			if(stuRegForm.getSelectedCountry().length() >= 4){
				stuRegForm.getStudent().getCountry().setCode(stuRegForm.getSelectedCountry().substring(0,4));
				stuRegForm.getStudent().getCountry().setDesc(stuRegForm.getSelectedCountry().substring(4));
			}else{
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "The selected country is invalid, Please select a country."));
				addErrors(request, messages);
				setDropdownListsStep2(request, form);
				return "applyNewAddress1";
			}
		}
		
		// Postal address - mandatory
		stuRegForm.getStudent().getPostalAddress().setLine1(stripXSS(stuRegForm.getStudent().getPostalAddress().getLine1(), "PostalAddressLine1", "NewAddres1", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
		stuRegForm.getStudent().getPostalAddress().setLine2(stripXSS(stuRegForm.getStudent().getPostalAddress().getLine2(), "PostalAddressLine2", "NewAddres1", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
		stuRegForm.getStudent().getPostalAddress().setLine3(stripXSS(stuRegForm.getStudent().getPostalAddress().getLine3(), "PostalAddressLine2", "NewAddres1", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
		stuRegForm.getStudent().getPostalAddress().setLine4(stripXSS(stuRegForm.getStudent().getPostalAddress().getLine4(), "PostalAddressLine2", "NewAddres1", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
		stuRegForm.getStudent().getPostalAddress().setLine5(stripXSS(stuRegForm.getStudent().getPostalAddress().getLine5(), "PostalAddressLine2", "NewAddres1", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
		stuRegForm.getStudent().getPostalAddress().setLine6(stripXSS(stuRegForm.getStudent().getPostalAddress().getLine6(), "PostalAddressLine2", "NewAddres1", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
		stuRegForm.getStudent().getPostalAddress().setAreaCode(stripXSS(stuRegForm.getStudent().getPostalAddress().getAreaCode(), "PostalAddressAreaCode", "NewAddres1", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
		
		if (stuRegForm.getStudent().getPostalAddress().getLine1() == null || "".equals(stuRegForm.getStudent().getPostalAddress().getLine1().trim())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Enter your postal address."));
			addErrors(request, messages);
			setDropdownListsStep2(request, form);
			return "applyNewAddress1";
		}else{
			// Remove spaces
			stuRegForm.getStudent().getPostalAddress().setLine1(stuRegForm.getStudent().getPostalAddress().getLine1().trim());
			if (stuRegForm.getStudent().getPostalAddress().getLine1().trim().length() > 5){
				if ("po box".equalsIgnoreCase(stuRegForm.getStudent().getPostalAddress().getLine1().substring(0,6))){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Please enter P O Box with a space between the P and O. " ));
					addErrors(request, messages);
					setDropdownListsStep2(request, form);
					return "applyNewAddress1";
				}
				if (stuRegForm.getStudent().getPostalAddress().getLine1().length() > 28){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Postal Address line 1 is too long. Please enter 28 characters or less. " ));
					addErrors(request, messages);
					setDropdownListsStep2(request, form);
					return "applyNewAddress1";
				}
			
			}
			if (stuRegForm.getStudent().getPostalAddress().getLine2() == null || "".equals(stuRegForm.getStudent().getPostalAddress().getLine2().trim())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Postal address is invalid. Enter at least 2 lines."));
				addErrors(request, messages);
				setDropdownListsStep2(request, form);
				return "applyNewAddress1";
			}
			if (stuRegForm.getStudent().getPostalAddress().getLine2() != null && !"".equals(stuRegForm.getStudent().getPostalAddress().getLine2().trim())){
				stuRegForm.getStudent().getPostalAddress().setLine2(stuRegForm.getStudent().getPostalAddress().getLine2().trim());
				if (stuRegForm.getStudent().getPostalAddress().getLine2().length() > 28){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Postal Address line 2 is too long. Please enter 28 characters or less. " ));
					addErrors(request, messages);
					setDropdownListsStep2(request, form);
					return "applyNewAddress1";
				}
			}

			if (stuRegForm.getStudent().getPostalAddress().getLine3() != null && !"".equals(stuRegForm.getStudent().getPostalAddress().getLine3().trim())){
				stuRegForm.getStudent().getPostalAddress().setLine3(stuRegForm.getStudent().getPostalAddress().getLine3().trim());
				if (stuRegForm.getStudent().getPostalAddress().getLine3().length() > 28){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Postal Address line 3 is too long. Please enter 28 characters or less. " ));
					addErrors(request, messages);
					setDropdownListsStep2(request, form);
					return "applyNewAddress1";
				}
			}

			if (stuRegForm.getStudent().getPostalAddress().getLine4() != null && !"".equals(stuRegForm.getStudent().getPostalAddress().getLine4().trim())){
				stuRegForm.getStudent().getPostalAddress().setLine4(stuRegForm.getStudent().getPostalAddress().getLine4().trim());
				if (stuRegForm.getStudent().getPostalAddress().getLine4().length() > 28){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Postal Address line 4 is too long. Please enter 28 characters or less. " ));
					addErrors(request, messages);
					setDropdownListsStep2(request, form);
					return "applyNewAddress1";
				}
			}

			if (stuRegForm.getStudent().getPostalAddress().getLine5() != null && !"".equals(stuRegForm.getStudent().getPostalAddress().getLine5().trim())){
				stuRegForm.getStudent().getPostalAddress().setLine5(stuRegForm.getStudent().getPostalAddress().getLine5().trim());
				if (stuRegForm.getStudent().getPostalAddress().getLine5().length() > 28){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Postal Address line 5 is too long. Please enter 28 characters or less. " ));
					addErrors(request, messages);
					setDropdownListsStep2(request, form);
					return "applyNewAddress1";
				}
			}

			if (stuRegForm.getStudent().getPostalAddress().getLine6() != null && !"".equals(stuRegForm.getStudent().getPostalAddress().getLine6().trim())){
				stuRegForm.getStudent().getPostalAddress().setLine6(stuRegForm.getStudent().getPostalAddress().getLine6().trim());
				if (stuRegForm.getStudent().getPostalAddress().getLine6().length() > 28){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Postal Address line 6 is too long. Please enter 28 characters or less. " ));
					addErrors(request, messages);
					setDropdownListsStep2(request, form);
					return "applyNewAddress1";
				}
			}
		}
		
		// Postal code
		if("1015".equals(stuRegForm.getStudent().getCountry().getCode())){
			if (stuRegForm.getStudent().getPostalAddress().getAreaCode() == null || "".equals(stuRegForm.getStudent().getPostalAddress().getAreaCode().trim())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Enter your postal code."));
				addErrors(request, messages);
				return "applyNewAddress1";
			} else if(! gen.isNumeric(stuRegForm.getStudent().getPostalAddress().getAreaCode()) || stuRegForm.getStudent().getPostalAddress().getAreaCode().trim().length()!=4){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Postal address error - Postal code must consist of 4 numerical characters."));
					addErrors(request, messages);
					setDropdownListsStep2(request, form);
					return "applyNewAddress1";
			}
		}

		if(!"1015".equals(stuRegForm.getStudent().getCountry().getCode())){
			if (stuRegForm.getStudent().getPostalAddress().getAreaCode() != null && !"".equals(stuRegForm.getStudent().getPostalAddress().getAreaCode().trim())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Postal address error - Postal code for South African address only. Foreign address postal/area code must be part of address lines."));
					addErrors(request, messages);
					setDropdownListsStep2(request, form);
					return "applyNewAddress1";
			}
		}
		
		if("1015".equals(stuRegForm.getStudent().getCountry().getCode())){
			//Validate Address
			/** Edmund 2016 - Postal Code vs Suburb/Town validation **/	    	
			/** Only for SA Addresses as we cannot validate international addresses at this stage other than checking that lines are populated **/
			//log.debug("ApplyForStudentNumberAction - isAddressValid - Postal Code/Suburb validation");
			boolean postalSuburbPostalCheck = false;
			//Checking Postal Address Suburb vs Postal Code
			//log.debug("ApplyForStudentNumberAction - isAddressValid - Checking Postal Suburb/Town va Code");
			if(!postalSuburbPostalCheck && stuRegForm.getStudent().getPostalAddress().getLine1().toString()!= null && !"".equals(stuRegForm.getStudent().getPostalAddress().getLine1().toString().trim())){                    
				//log.debug("ApplyForStudentNumberAction - isAddressValid - Checking Line1 - PostalCode="+ stuRegForm.getStudent().getPostalAddress().getAreaCode()+", PostalAddress="+stuRegForm.getStudent().getPostalAddress().getLine1().toString().trim());
				postalSuburbPostalCheck = dao.checkSuburbExact(stuRegForm.getStudent().getPostalAddress().getAreaCode(),stuRegForm.getStudent().getPostalAddress().getLine1().toString().trim());
				//log.debug("ApplyForStudentNumberAction - isAddressValid - Checking - postalSuburbPostalCheck="+postalSuburbPostalCheck);
			}
			if(!postalSuburbPostalCheck && stuRegForm.getStudent().getPostalAddress().getLine2().toString()!= null && !"".equals(stuRegForm.getStudent().getPostalAddress().getLine2().toString().trim())){                    
				//log.debug("ApplyForStudentNumberAction - isAddressValid - Checking Line2 - PostalCode="+ stuRegForm.getStudent().getPostalAddress().getAreaCode()+", PostalAddress="+stuRegForm.getStudent().getPostalAddress().getLine2().toString().trim());
				postalSuburbPostalCheck = dao.checkSuburbExact(stuRegForm.getStudent().getPostalAddress().getAreaCode(),stuRegForm.getStudent().getPostalAddress().getLine2().toString().trim());
				//log.debug("ApplyForStudentNumberAction - isAddressValid - Checking - postalSuburbPostalCheck="+postalSuburbPostalCheck);
			}
			if(!postalSuburbPostalCheck && stuRegForm.getStudent().getPostalAddress().getLine3().toString()!= null && !"".equals(stuRegForm.getStudent().getPostalAddress().getLine3().toString().trim())){                    
				//log.debug("ApplyForStudentNumberAction - isAddressValid - Checking Line3 - PostalCode="+ stuRegForm.getStudent().getPostalAddress().getAreaCode()+", PostalAddress="+stuRegForm.getStudent().getPostalAddress().getLine3().toString().trim());
				postalSuburbPostalCheck = dao.checkSuburbExact(stuRegForm.getStudent().getPostalAddress().getAreaCode(),stuRegForm.getStudent().getPostalAddress().getLine3().toString().trim());
				//log.debug("ApplyForStudentNumberAction - isAddressValid - Checking - postalSuburbPostalCheck="+postalSuburbPostalCheck);
			}
			if(!postalSuburbPostalCheck && stuRegForm.getStudent().getPostalAddress().getLine4().toString()!= null && !"".equals(stuRegForm.getStudent().getPostalAddress().getLine4().toString().trim())){                    
				//log.debug("ApplyForStudentNumberAction - isAddressValid - Checking Line4 - PostalCode="+ stuRegForm.getStudent().getPostalAddress().getAreaCode()+", PostalAddress="+stuRegForm.getStudent().getPostalAddress().getLine4().toString().trim());
				postalSuburbPostalCheck = dao.checkSuburbExact(stuRegForm.getStudent().getPostalAddress().getAreaCode(),stuRegForm.getStudent().getPostalAddress().getLine4().toString().trim());
				//log.debug("ApplyForStudentNumberAction - isAddressValid - Checking - postalSuburbPostalCheck="+postalSuburbPostalCheck);
			}
			if(!postalSuburbPostalCheck && stuRegForm.getStudent().getPostalAddress().getLine5().toString()!= null && !"".equals(stuRegForm.getStudent().getPostalAddress().getLine5().toString().trim())){                    
				//log.debug("ApplyForStudentNumberAction - isAddressValid - Checking Line5 - PostalCode="+ stuRegForm.getStudent().getPostalAddress().getAreaCode()+", PostalAddress="+stuRegForm.getStudent().getPostalAddress().getLine5().toString().trim());
				postalSuburbPostalCheck = dao.checkSuburbExact(stuRegForm.getStudent().getPostalAddress().getAreaCode(),stuRegForm.getStudent().getPostalAddress().getLine5().toString().trim());
				//log.debug("ApplyForStudentNumberAction - isAddressValid - Checking - postalSuburbPostalCheck="+postalSuburbPostalCheck);
			}
			if(!postalSuburbPostalCheck && stuRegForm.getStudent().getPostalAddress().getLine6().toString()!= null && !"".equals(stuRegForm.getStudent().getPostalAddress().getLine6().toString().trim())){                    
				//log.debug("ApplyForStudentNumberAction - isAddressValid - Checking Line6 - PostalCode="+ stuRegForm.getStudent().getPostalAddress().getAreaCode()+", PostalAddress="+stuRegForm.getStudent().getPostalAddress().getLine6().toString().trim());
				postalSuburbPostalCheck = dao.checkSuburbExact(stuRegForm.getStudent().getPostalAddress().getAreaCode(),stuRegForm.getStudent().getPostalAddress().getLine6().toString().trim());
				//log.debug("ApplyForStudentNumberAction - isAddressValid - Checking - postalSuburbPostalCheck="+postalSuburbPostalCheck);
			}
			if (!postalSuburbPostalCheck){
				//log.debug("RegistrationStudentBroker - isAddressValid - postalSuburbPostalCheck: " + postalSuburbPostalCheck);
				messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Postal address error - The Postal Address Suburb/Town does not match the entered Postal Code."));
				addErrors(request, messages);
				setDropdownListsStep2(request, form);
				return "applyNewAddress1";
	        }else{
		      	  //log.debug("RegistrationStudentBroker - isAddressValid - Postal Address Suburb result="+postalSuburbPostalCheck);
			}
		}

		//log.debug("ApplyForStudentNumberAction - applyNewAddress1 - End");
		//log.debug("ApplyForStudentNumberAction - applyNewAddress1 - Goto applyNewAddress2");

		return "applyNewAddress2";
	}
	
	public String applyNewAddress2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//log.debug("ApplyForStudentNumberAction - applyNewAddress2 - Start");

		// if student number is null, block application
		ActionMessages messages = new ActionMessages();
		StudentRegistrationForm stuRegForm = (StudentRegistrationForm) form;
		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
		
		if(stuRegForm.getStudent().getNumber() == null || "".equals(stuRegForm.getStudent().getNumber())){
			//log.debug("ApplyForStudentNumberAction - applyNewAddress2 - Student Nr is null or empty when saving qualifications");
			messages.add(ActionMessages.GLOBAL_MESSAGE,
			new ActionMessage("message.generalmessage", "You performed and invalid action or an Error occurred. Please log on again to retry."));
			addErrors(request, messages);
			if (stuRegForm.getAdminStaff().isAdmin()){
				return "loginStaff";
			}else{
				return "loginStu";
			}
		}
		GeneralMethods gen = new GeneralMethods();

		if (stuRegForm.getFromPage() == null){
			//Error: Session empty
			//log.debug("ApplyForStudentNumberAction - applyNewAddress2 Empty Session");
			return emptySession(mapping,form,request,response);
		}else{
			stuRegForm.setFromPage("applyNewAddress2");
		}
		// Physical address - mandatory
		stuRegForm.getStudent().getPhysicalAddress().setLine1(stripXSS(stuRegForm.getStudent().getPhysicalAddress().getLine1(), "PhysicalAddressLine1", "NewAddres2", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
		stuRegForm.getStudent().getPhysicalAddress().setLine2(stripXSS(stuRegForm.getStudent().getPhysicalAddress().getLine2(), "PhysicalAddressLine1", "NewAddres2", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
		stuRegForm.getStudent().getPhysicalAddress().setLine3(stripXSS(stuRegForm.getStudent().getPhysicalAddress().getLine3(), "PhysicalAddressLine1", "NewAddres2", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
		stuRegForm.getStudent().getPhysicalAddress().setLine4(stripXSS(stuRegForm.getStudent().getPhysicalAddress().getLine4(), "PhysicalAddressLine1", "NewAddres2", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
		stuRegForm.getStudent().getPhysicalAddress().setLine5(stripXSS(stuRegForm.getStudent().getPhysicalAddress().getLine5(), "PhysicalAddressLine1", "NewAddres2", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
		stuRegForm.getStudent().getPhysicalAddress().setLine6(stripXSS(stuRegForm.getStudent().getPhysicalAddress().getLine6(), "PhysicalAddressLine1", "NewAddres2", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
		stuRegForm.getStudent().getPhysicalAddress().setAreaCode(stripXSS(stuRegForm.getStudent().getPhysicalAddress().getAreaCode(), "PhysicalAddressAreaCode", "NewAddres2", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
		
		if (stuRegForm.getStudent().getPhysicalAddress().getLine1() == null || "".equals(stuRegForm.getStudent().getPhysicalAddress().getLine1().trim())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Enter your physical address."));
			addErrors(request, messages);
			return "applyNewAddress2";
		}else{
		    String checkPhysicalStreet = getBoxOrStreet(stuRegForm.getStudent().getPhysicalAddress().getLine1().toString());
		    if (checkPhysicalStreet != null && !"".equals(checkPhysicalStreet)){
				if (checkPhysicalStreet.toUpperCase().contains("B")){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
		        			new ActionMessage("message.generalmessage", "Physical Address may not contain P O Box etc. Should be Street address."));
					addErrors(request, messages);
					return "applyNewAddress2";
				}
		    }
			// Remove spaces
			stuRegForm.getStudent().getPhysicalAddress().setLine1(stuRegForm.getStudent().getPhysicalAddress().getLine1().trim());
			if (stuRegForm.getStudent().getPostalAddress().getLine1().length() > 28){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Physical Address line 1 is too long. Please enter 28 characters or less. " ));
				addErrors(request, messages);
				return "applyNewAddress2";
			}
			if (stuRegForm.getStudent().getPhysicalAddress().getLine2() == null || "".equals(stuRegForm.getStudent().getPhysicalAddress().getLine2().trim())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Physical address is invalid. Enter at least 2 lines."));
				addErrors(request, messages);
				return "applyNewAddress2";
			}
			if (stuRegForm.getStudent().getPhysicalAddress().getLine2() != null && !"".equals(stuRegForm.getStudent().getPhysicalAddress().getLine2().trim())){
				stuRegForm.getStudent().getPhysicalAddress().setLine2(stuRegForm.getStudent().getPhysicalAddress().getLine2().trim());
				if (stuRegForm.getStudent().getPhysicalAddress().getLine2().length() > 28){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Physical Address line 2 is too long. Please enter 28 characters or less. " ));
					addErrors(request, messages);
					return "applyNewAddress2";
				}
			}
			if (stuRegForm.getStudent().getPhysicalAddress().getLine3() != null && !"".equals(stuRegForm.getStudent().getPhysicalAddress().getLine3().trim())){
				stuRegForm.getStudent().getPhysicalAddress().setLine3(stuRegForm.getStudent().getPhysicalAddress().getLine3().trim());
				if (stuRegForm.getStudent().getPhysicalAddress().getLine3().length() > 28){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Physical Address line 3 is too long. Please enter 28 characters or less. " ));
					addErrors(request, messages);
					return "applyNewAddress2";
				}
			}
			if (stuRegForm.getStudent().getPhysicalAddress().getLine4() != null && !"".equals(stuRegForm.getStudent().getPhysicalAddress().getLine4().trim())){
				stuRegForm.getStudent().getPhysicalAddress().setLine4(stuRegForm.getStudent().getPhysicalAddress().getLine4().trim());
				if (stuRegForm.getStudent().getPhysicalAddress().getLine4().length() > 28){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Physical Address line 4 is too long. Please enter 28 characters or less. " ));
					addErrors(request, messages);
					return "applyNewAddress2";
				}
			}
			if (stuRegForm.getStudent().getPhysicalAddress().getLine5() != null && !"".equals(stuRegForm.getStudent().getPhysicalAddress().getLine5().trim())){
				stuRegForm.getStudent().getPhysicalAddress().setLine5(stuRegForm.getStudent().getPhysicalAddress().getLine5().trim());
				if (stuRegForm.getStudent().getPhysicalAddress().getLine5().length() > 28){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Physical Address line 5 is too long. Please enter 28 characters or less. " ));
					addErrors(request, messages);
					return "applyNewAddress2";
				}
			}
			if (stuRegForm.getStudent().getPhysicalAddress().getLine6() != null && !"".equals(stuRegForm.getStudent().getPhysicalAddress().getLine6().trim())){
				stuRegForm.getStudent().getPhysicalAddress().setLine6(stuRegForm.getStudent().getPhysicalAddress().getLine6().trim());
				if (stuRegForm.getStudent().getPhysicalAddress().getLine6().length() > 28){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Physical Address line 6 is too long. Please enter 28 characters or less. " ));
					addErrors(request, messages);
					return "applyNewAddress2";
				}
			}
		}

		// Physical address postal code
		if("1015".equals(stuRegForm.getStudent().getCountry().getCode())){
			if (stuRegForm.getStudent().getPhysicalAddress().getAreaCode() == null || "".equals(stuRegForm.getStudent().getPhysicalAddress().getAreaCode().trim())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Physical address error - Enter your postal code."));
				addErrors(request, messages);
				return "applyNewAddress2";
			} else if(! gen.isNumeric(stuRegForm.getStudent().getPhysicalAddress().getAreaCode()) || stuRegForm.getStudent().getPhysicalAddress().getAreaCode().trim().length()!=4){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Physical address error - Postal code must consist of 4 numerical characters."));
					addErrors(request, messages);
					return "applyNewAddress2";
			}
		}

		if(!"1015".equals(stuRegForm.getStudent().getCountry().getCode())){
			if (stuRegForm.getStudent().getPhysicalAddress().getAreaCode() != null && !"".equals(stuRegForm.getStudent().getPhysicalAddress().getAreaCode().trim())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Physical address error - Postal code for South African address only. Foreign address postal/area code must be part of address lines."));
					addErrors(request, messages);
					return "applyNewAddress2";
			}
		}
		
		if("1015".equals(stuRegForm.getStudent().getCountry().getCode())){
			/** Edmund 2016 - Postal Code vs Suburb/Town validation **/	    	
			/** Only for SA Addresses as we cannot validate international addresses at this stage other than checking that lines are populated **/
			//log.debug("ApplyForStudentNumberAction - isAddressValid - Postal Code/Suburb validation");
			boolean physicalSuburbPostalCheck = false;
			//Checking Postal Address Suburb vs Postal Code
			//log.debug("RegistrationStudentBroker - isAddressValid - Checking Physical Suburb/Town vs Code");
			if(!physicalSuburbPostalCheck && stuRegForm.getStudent().getPhysicalAddress().getLine1().toString()!= null && !"".equals(stuRegForm.getStudent().getPhysicalAddress().getLine1().toString().trim())){                    
				//log.debug("ApplyForStudentNumberAction - isAddressValid - Checking Line1 - PostalCode="+ stuRegForm.getStudent().getPhysicalAddress().getAreaCode()+", PostalAddress="+stuRegForm.getStudent().getPhysicalAddress().getLine1().toString().trim());
				physicalSuburbPostalCheck = dao.checkSuburbExact(stuRegForm.getStudent().getPhysicalAddress().getAreaCode(),stuRegForm.getStudent().getPhysicalAddress().getLine1().toString().trim());
				//log.debug("ApplyForStudentNumberAction - isAddressValid - Checking - physicalSuburbPostalCheck="+physicalSuburbPostalCheck);
			}
			if(!physicalSuburbPostalCheck && stuRegForm.getStudent().getPhysicalAddress().getLine2().toString()!= null && !"".equals(stuRegForm.getStudent().getPhysicalAddress().getLine2().toString().trim())){                    
				//log.debug("ApplyForStudentNumberAction - isAddressValid - Checking Line2 - PostalCode="+ stuRegForm.getStudent().getPhysicalAddress().getAreaCode()+", PostalAddress="+stuRegForm.getStudent().getPhysicalAddress().getLine2().toString().trim());
				physicalSuburbPostalCheck = dao.checkSuburbExact(stuRegForm.getStudent().getPhysicalAddress().getAreaCode(),stuRegForm.getStudent().getPhysicalAddress().getLine2().toString().trim());
				//log.debug("ApplyForStudentNumberAction - isAddressValid - Checking - physicalSuburbPostalCheck="+physicalSuburbPostalCheck);
			}
			if(!physicalSuburbPostalCheck && stuRegForm.getStudent().getPhysicalAddress().getLine3().toString()!= null && !"".equals(stuRegForm.getStudent().getPhysicalAddress().getLine3().toString().trim())){                    
				//log.debug("ApplyForStudentNumberAction - isAddressValid - Checking Line3 - PostalCode="+ stuRegForm.getStudent().getPhysicalAddress().getAreaCode()+", PostalAddress="+stuRegForm.getStudent().getPhysicalAddress().getLine3().toString().trim());
				physicalSuburbPostalCheck = dao.checkSuburbExact(stuRegForm.getStudent().getPhysicalAddress().getAreaCode(),stuRegForm.getStudent().getPhysicalAddress().getLine3().toString().trim());
				//log.debug("ApplyForStudentNumberAction - isAddressValid - Checking - physicalSuburbPostalCheck="+physicalSuburbPostalCheck);
			}
			if(!physicalSuburbPostalCheck && stuRegForm.getStudent().getPhysicalAddress().getLine4().toString()!= null && !"".equals(stuRegForm.getStudent().getPhysicalAddress().getLine4().toString().trim())){                    
				//log.debug("ApplyForStudentNumberAction - isAddressValid - Checking Line4 - PostalCode="+ stuRegForm.getStudent().getPhysicalAddress().getAreaCode()+", PostalAddress="+stuRegForm.getStudent().getPhysicalAddress().getLine4().toString().trim());
				physicalSuburbPostalCheck = dao.checkSuburbExact(stuRegForm.getStudent().getPhysicalAddress().getAreaCode(),stuRegForm.getStudent().getPhysicalAddress().getLine4().toString().trim());
				//log.debug("ApplyForStudentNumberAction - isAddressValid - Checking - physicalSuburbPostalCheck="+physicalSuburbPostalCheck);
			}
			if(!physicalSuburbPostalCheck && stuRegForm.getStudent().getPhysicalAddress().getLine5().toString()!= null && !"".equals(stuRegForm.getStudent().getPhysicalAddress().getLine5().toString().trim())){                    
				//log.debug("ApplyForStudentNumberAction - isAddressValid - Checking Line5 - PostalCode="+ stuRegForm.getStudent().getPhysicalAddress().getAreaCode()+", PostalAddress="+stuRegForm.getStudent().getPhysicalAddress().getLine5().toString().trim());
				physicalSuburbPostalCheck = dao.checkSuburbExact(stuRegForm.getStudent().getPhysicalAddress().getAreaCode(),stuRegForm.getStudent().getPhysicalAddress().getLine5().toString().trim());
				//log.debug("ApplyForStudentNumberAction - isAddressValid - Checking - physicalSuburbPostalCheck="+physicalSuburbPostalCheck);
			}
			if(!physicalSuburbPostalCheck && stuRegForm.getStudent().getPhysicalAddress().getLine6().toString()!= null && !"".equals(stuRegForm.getStudent().getPhysicalAddress().getLine6().toString().trim())){                    
				//log.debug("ApplyForStudentNumberAction - isAddressValid - Checking Line6 - PostalCode="+ stuRegForm.getStudent().getPhysicalAddress().getAreaCode()+", PostalAddress="+stuRegForm.getStudent().getPhysicalAddress().getLine6().toString().trim());
				physicalSuburbPostalCheck = dao.checkSuburbExact(stuRegForm.getStudent().getPhysicalAddress().getAreaCode(),stuRegForm.getStudent().getPhysicalAddress().getLine6().toString().trim());
				//log.debug("ApplyForStudentNumberAction - isAddressValid - Checking - physicalSuburbPostalCheck="+physicalSuburbPostalCheck);
			}
			if (!physicalSuburbPostalCheck){
				//log.debug("RegistrationStudentBroker - isAddressValid - physicalSuburbPostalCheck: " + physicalSuburbPostalCheck);
	      	  messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Physical address error - he Physical Address Suburb does not match the entered Postal Code."));
				addErrors(request, messages);
				return "applyNewAddress2";
			}else{
				//log.debug("ApplyForStudentNumberAction - isAddressValid - Physical Address Suburb result="+physicalSuburbPostalCheck);
			}
		}
		
		//log.debug("ApplyForStudentNumberAction - applyNewAddress2 - End");
		//log.debug("ApplyForStudentNumberAction - applyNewAddress2 - Goto applyNewAddress3");

		return "applyNewAddress3";
	}
	
	public String applyNewAddress3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//log.debug("ApplyForStudentNumberAction - applyNewAddress3 - Start");

		// if student number is null, block application
		ActionMessages messages = new ActionMessages();
		StudentRegistrationForm stuRegForm = (StudentRegistrationForm) form;
		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
		
		if(stuRegForm.getStudent().getNumber() == null || "".equals(stuRegForm.getStudent().getNumber())){
			//log.debug("ApplyForStudentNumberAction - applyNewAddress3 - Student Nr is null or empty when saving qualifications");
			messages.add(ActionMessages.GLOBAL_MESSAGE,
			new ActionMessage("message.generalmessage", "You performed and invalid action or an Error occurred. Please log on again to retry."));
			addErrors(request, messages);
			if (stuRegForm.getAdminStaff().isAdmin()){
				return "loginStaff";
			}else{
				return "loginStu";
			}
		}
		GeneralMethods gen = new GeneralMethods();

		if (stuRegForm.getFromPage() == null){
			//Error: Session empty
			//log.debug("ApplyForStudentNumberAction - applyNewAddress3 Empty Session");
			return emptySession(mapping,form,request,response);
		}else{
			stuRegForm.setFromPage("applyNewAddress3");
		}

		// Courier address - mandatory
		stuRegForm.getStudent().getCourierAddress().setLine1(stripXSS(stuRegForm.getStudent().getCourierAddress().getLine1(), "CourierAddressLine1", "NewAddres3", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
		stuRegForm.getStudent().getCourierAddress().setLine2(stripXSS(stuRegForm.getStudent().getCourierAddress().getLine2(), "CourierAddressLine1", "NewAddres3", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
		stuRegForm.getStudent().getCourierAddress().setLine3(stripXSS(stuRegForm.getStudent().getCourierAddress().getLine3(), "CourierAddressLine1", "NewAddres3", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
		stuRegForm.getStudent().getCourierAddress().setLine4(stripXSS(stuRegForm.getStudent().getCourierAddress().getLine4(), "CourierAddressLine1", "NewAddres3", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
		stuRegForm.getStudent().getCourierAddress().setLine5(stripXSS(stuRegForm.getStudent().getCourierAddress().getLine5(), "CourierAddressLine1", "NewAddres3", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
		stuRegForm.getStudent().getCourierAddress().setLine6(stripXSS(stuRegForm.getStudent().getCourierAddress().getLine6(), "CourierAddressLine1", "NewAddres3", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
		stuRegForm.getStudent().getCourierAddress().setAreaCode(stripXSS(stuRegForm.getStudent().getCourierAddress().getAreaCode(), "CourierAddressAreaCode", "NewAddres3", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
		
		if (stuRegForm.getStudent().getCourierAddress().getLine1() == null || "".equals(stuRegForm.getStudent().getCourierAddress().getLine1().trim())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "The courier address is mandatory, please supply a valid courier address."));
			addErrors(request, messages);
			return "applyNewAddress3";
		}
		if (stuRegForm.getStudent().getCourierAddress().getLine1() != null && !"".equals(stuRegForm.getStudent().getCourierAddress().getLine1().trim())){
			stuRegForm.getStudent().getCourierAddress().setLine1(stuRegForm.getStudent().getCourierAddress().getLine1().trim());
			if (stuRegForm.getStudent().getCourierAddress().getLine1().length() > 28){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Courier Address line 1 is too long. Please enter 28 characters or less. " ));
				addErrors(request, messages);
				return "applyNewAddress3";
			}
		    String checkCourierStreet = getBoxOrStreet(stuRegForm.getStudent().getCourierAddress().getLine1().toString());
		    if (checkCourierStreet != null && !"".equals(checkCourierStreet)){
				if (checkCourierStreet.toUpperCase().contains("B")){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
		        			new ActionMessage("message.generalmessage", "Courier Address may not contain P O Box etc. Should be Street address."));
					addErrors(request, messages);
					return "applyNewAddress3";
				}
		    }
			if (stuRegForm.getStudent().getCourierAddress().getLine2() == null  || "".equals(stuRegForm.getStudent().getCourierAddress().getLine2().trim())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Courier address is invalid. Enter at least 2 lines."));
				addErrors(request, messages);
				return "applyNewAddress3";
			}else{
				stuRegForm.getStudent().getCourierAddress().setLine2(stuRegForm.getStudent().getCourierAddress().getLine2().trim());
				if (stuRegForm.getStudent().getCourierAddress().getLine2().length() > 28){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Courier Address line 2 is too long. Please enter 28 characters or less. " ));
					addErrors(request, messages);
					return "applyNewAddress3";
				}
			}
			if (stuRegForm.getStudent().getCourierAddress().getLine3() != null || !"".equals(stuRegForm.getStudent().getCourierAddress().getLine3().trim())){
				stuRegForm.getStudent().getCourierAddress().setLine3(stuRegForm.getStudent().getCourierAddress().getLine3().trim());
				if (stuRegForm.getStudent().getCourierAddress().getLine3().length() > 28){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Courier Address line 3 is too long. Please enter 28 characters or less. " ));
					addErrors(request, messages);
					return "applyNewAddress3";
				}
			}
			if (stuRegForm.getStudent().getCourierAddress().getLine4() != null && !"".equals(stuRegForm.getStudent().getCourierAddress().getLine4().trim())){
				stuRegForm.getStudent().getCourierAddress().setLine4(stuRegForm.getStudent().getCourierAddress().getLine4().trim());
				if (stuRegForm.getStudent().getCourierAddress().getLine4().length() > 28){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Courier Address line 4 is too long. Please enter 28 characters or less. " ));
					addErrors(request, messages);
					return "applyNewAddress3";
				}
			}
			if (stuRegForm.getStudent().getCourierAddress().getLine5() != null && !"".equals(stuRegForm.getStudent().getCourierAddress().getLine5().trim())){
				stuRegForm.getStudent().getCourierAddress().setLine5(stuRegForm.getStudent().getCourierAddress().getLine5().trim());
				if (stuRegForm.getStudent().getCourierAddress().getLine5().length() > 28){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Courier Address line 5 is too long. Please enter 28 characters or less. " ));
					addErrors(request, messages);
					return "applyNewAddress3";
				}
			}
			if (stuRegForm.getStudent().getCourierAddress().getLine6() != null && !"".equals(stuRegForm.getStudent().getCourierAddress().getLine6().trim())){
				stuRegForm.getStudent().getCourierAddress().setLine6(stuRegForm.getStudent().getCourierAddress().getLine6().trim());
				if (stuRegForm.getStudent().getCourierAddress().getLine6().length() > 28){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Courier Address line 6 is too long. Please enter 28 characters or less. " ));
					addErrors(request, messages);
					return "applyNewAddress3";
				}
			}
			
			//check courier postal code
			if("1015".equals(stuRegForm.getStudent().getCountry().getCode())){
				if (stuRegForm.getStudent().getCourierAddress().getAreaCode() == null || "".equals(stuRegForm.getStudent().getCourierAddress().getAreaCode().trim())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Enter your courier postal code."));
				addErrors(request, messages);
				return "applyNewAddress3";
				} else if(! gen.isNumeric(stuRegForm.getStudent().getCourierAddress().getAreaCode()) || stuRegForm.getStudent().getCourierAddress().getAreaCode().trim().length()!=4){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Courier postal code must consist of 4 numerical characters."));
					addErrors(request, messages);
					return "applyNewAddress3";
				}
			}
			//check courier contact number
			stuRegForm.getStudent().setContactNr(stripXSS(stuRegForm.getStudent().getContactNr(), "CourierAddressContact", "NewAddres3", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
			if (stuRegForm.getStudent().getContactNr() == null || "".equals(stuRegForm.getStudent().getContactNr().trim())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Enter your courier contact number."));
				addErrors(request, messages);
				return "applyNewAddress3";
			}
		}else{
			// Remove spaces
			stuRegForm.getStudent().getCourierAddress().setLine1(stuRegForm.getStudent().getCourierAddress().getLine1().trim());
		}

		if(!"1015".equals(stuRegForm.getStudent().getCountry().getCode())){
			if (stuRegForm.getStudent().getCourierAddress().getAreaCode() != null && !"".equals(stuRegForm.getStudent().getCourierAddress().getAreaCode().trim())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Courier address error - Postal code for South African address only. Foreign address postal/area code must be part of address lines."));
					addErrors(request, messages);
					return "applyNewAddress3";
			}
		}

		if (stuRegForm.getStudent().getContactNr() != null && !"".equals(stuRegForm.getStudent().getContactNr().trim())){
			if(!isPhoneNumber(stuRegForm.getStudent().getContactNr())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
	        			new ActionMessage("message.generalmessage", "Your home phone number may consist of a dash or +, the rest must be numeric."));
				addErrors(request, messages);
				return "applyNewAddress3";
			}
		}
	    
		if("1015".equals(stuRegForm.getStudent().getCountry().getCode())){
			/** Edmund 2016 - Postal Code vs Suburb/Town validation **/	    	
			/** Only for SA Addresses as we cannot validate international addresses at this stage other than checking that lines are populated **/
			//log.debug("ApplyForStudentNumberAction - isAddressValid - Postal Code/Suburb validation");
			boolean courierSuburbPostalCheck = false;
			//Checking Postal Address Suburb vs Postal Code
			//log.debug("ApplyForStudentNumberAction - isAddressValid - Checking Courier Suburb/Town vs Code");
			if(!courierSuburbPostalCheck && stuRegForm.getStudent().getCourierAddress().getLine1().toString()!= null && !"".equals(stuRegForm.getStudent().getCourierAddress().getLine1().toString().trim())){                    
				//log.debug("ApplyForStudentNumberAction - isAddressValid - Checking Line1 - PostalCode="+ stuRegForm.getStudent().getCourierAddress().getAreaCode()+", PostalAddress="+stuRegForm.getStudent().getCourierAddress().getLine1().toString().trim());
				courierSuburbPostalCheck = dao.checkSuburbExact(stuRegForm.getStudent().getCourierAddress().getAreaCode(),stuRegForm.getStudent().getCourierAddress().getLine1().toString().trim());
				//log.debug("ApplyForStudentNumberAction - isAddressValid - Checking - courierSuburbPostalCheck="+courierSuburbPostalCheck);
			}
			if(!courierSuburbPostalCheck && stuRegForm.getStudent().getCourierAddress().getLine2().toString()!= null && !"".equals(stuRegForm.getStudent().getCourierAddress().getLine2().toString().trim())){                    
				//log.debug("ApplyForStudentNumberAction - isAddressValid - Checking Line2 - PostalCode="+ stuRegForm.getStudent().getCourierAddress().getAreaCode()+", PostalAddress="+stuRegForm.getStudent().getCourierAddress().getLine2().toString().trim());
				courierSuburbPostalCheck = dao.checkSuburbExact(stuRegForm.getStudent().getCourierAddress().getAreaCode(),stuRegForm.getStudent().getCourierAddress().getLine2().toString().trim());
				//log.debug("ApplyForStudentNumberAction - isAddressValid - Checking - courierSuburbPostalCheck="+courierSuburbPostalCheck);
			}
			if(!courierSuburbPostalCheck && stuRegForm.getStudent().getCourierAddress().getLine3().toString()!= null && !"".equals(stuRegForm.getStudent().getCourierAddress().getLine3().toString().trim())){                    
				//log.debug("ApplyForStudentNumberAction - isAddressValid - Checking Line3 - PostalCode="+ stuRegForm.getStudent().getCourierAddress().getAreaCode()+", PostalAddress="+stuRegForm.getStudent().getCourierAddress().getLine3().toString().trim());
				courierSuburbPostalCheck = dao.checkSuburbExact(stuRegForm.getStudent().getCourierAddress().getAreaCode(),stuRegForm.getStudent().getCourierAddress().getLine3().toString().trim());
				//log.debug("ApplyForStudentNumberAction - isAddressValid - Checking - courierSuburbPostalCheck="+courierSuburbPostalCheck);
			}
			if(!courierSuburbPostalCheck && stuRegForm.getStudent().getCourierAddress().getLine4().toString()!= null && !"".equals(stuRegForm.getStudent().getCourierAddress().getLine4().toString().trim())){                    
				//log.debug("ApplyForStudentNumberAction - isAddressValid - Checking Line4 - PostalCode="+ stuRegForm.getStudent().getCourierAddress().getAreaCode()+", PostalAddress="+stuRegForm.getStudent().getCourierAddress().getLine4().toString().trim());
				courierSuburbPostalCheck = dao.checkSuburbExact(stuRegForm.getStudent().getCourierAddress().getAreaCode(),stuRegForm.getStudent().getCourierAddress().getLine4().toString().trim());
				//log.debug("ApplyForStudentNumberAction - isAddressValid - Checking - courierSuburbPostalCheck="+courierSuburbPostalCheck);
			}
			if(!courierSuburbPostalCheck && stuRegForm.getStudent().getCourierAddress().getLine5().toString()!= null && !"".equals(stuRegForm.getStudent().getCourierAddress().getLine5().toString().trim())){                    
				//log.debug("ApplyForStudentNumberAction - isAddressValid - Checking Line5 - PostalCode="+ stuRegForm.getStudent().getCourierAddress().getAreaCode()+", PostalAddress="+stuRegForm.getStudent().getCourierAddress().getLine5().toString().trim());
				courierSuburbPostalCheck = dao.checkSuburbExact(stuRegForm.getStudent().getCourierAddress().getAreaCode(),stuRegForm.getStudent().getCourierAddress().getLine5().toString().trim());
				//log.debug("ApplyForStudentNumberAction - isAddressValid - Checking - courierSuburbPostalCheck="+courierSuburbPostalCheck);
			}
			if(!courierSuburbPostalCheck && stuRegForm.getStudent().getCourierAddress().getLine6().toString()!= null && !"".equals(stuRegForm.getStudent().getCourierAddress().getLine6().toString().trim())){                    
				//log.debug("ApplyForStudentNumberAction - isAddressValid - Checking Line6 - PostalCode="+ stuRegForm.getStudent().getCourierAddress().getAreaCode()+", PostalAddress="+stuRegForm.getStudent().getCourierAddress().getLine6().toString().trim());
				courierSuburbPostalCheck = dao.checkSuburbExact(stuRegForm.getStudent().getCourierAddress().getAreaCode(),stuRegForm.getStudent().getCourierAddress().getLine6().toString().trim());
				//log.debug("ApplyForStudentNumberAction - isAddressValid - Checking - courierSuburbPostalCheck="+courierSuburbPostalCheck);
			}
			if (!courierSuburbPostalCheck){
				//log.debug("ApplyForStudentNumberAction - isAddressValid - courierSuburbPostalCheck: " + courierSuburbPostalCheck);
		      	messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Courier address error - The Courier Address Suburb does not match the entered Postal Code."));
				addErrors(request, messages);
				return "applyNewAddress3";
			}else{
				//log.debug("ApplyForStudentNumberAction - isAddressValid - Courier Address Suburb result="+courierSuburbPostalCheck);
			}
		}
		
		//log.debug("ApplyForStudentNumberAction - applyNewAddress3 - End");
		//log.debug("ApplyForStudentNumberAction - applyNewAddress3 - Goto applyNewInfo1");

		// setup drop down lists
		setDropdownListsStep3(request, stuRegForm);
		return "applyNewInfo1";
	}

	public String applyNewInfo1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//log.debug("ApplyForStudentNumberAction - applyNewInfo1 - Start");

		// if student number is null, block application
		ActionMessages messages = new ActionMessages();
		StudentRegistrationForm stuRegForm = (StudentRegistrationForm) form;
		
		if(stuRegForm.getStudent().getNumber() == null || "".equals(stuRegForm.getStudent().getNumber())){
			//log.debug("ApplyForStudentNumberAction - applyNewInfo1 - Student Nr is null or empty when saving qualifications");
			messages.add(ActionMessages.GLOBAL_MESSAGE,
			new ActionMessage("message.generalmessage", "You performed and invalid action or an Error occurred. Please log on again to retry."));
			addErrors(request, messages);
			if (stuRegForm.getAdminStaff().isAdmin()){
				return "loginStaff";
			}else{
				return "loginStu";
			}
		}

		if (stuRegForm.getFromPage() == null){
			//Error: Session empty
			//log.debug("ApplyForStudentNumberAction - applyNewInfo1 Empty Session");
			return emptySession(mapping,form,request,response);
		}else{
			stuRegForm.setFromPage("applyNewInfo1");
		}
				
		// share contact details - Removed from page - Hardcoded to No
		stuRegForm.getStudent().setShareContactDetails("N");

		// home language
		stuRegForm.setSelectedHomeLanguage(stripXSS(stuRegForm.getSelectedHomeLanguage(), "SelectedHomeLanguage", "NewInfo1", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
		//log.debug("ApplyForStudentNumberAction - applyNewInfo1 - HomeLanguage="+stuRegForm.getSelectedHomeLanguage());
		if (stuRegForm.getSelectedHomeLanguage() == null || "-1".equals(stuRegForm.getSelectedHomeLanguage())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Select your home language."));
			addErrors(request, messages);
			setDropdownListsStep3(request, stuRegForm);
			return "applyNewInfo1";
		}else{
			GeneralItem genItem = new GeneralItem();
			genItem = getItem(stuRegForm.getSelectedHomeLanguage());
			stuRegForm.getStudent().getHomeLanguage().setCode(genItem.getCode());
			stuRegForm.getStudent().getHomeLanguage().setDesc(genItem.getDesc());
		}
		//Twin siblings
		stuRegForm.getStudent().setTwinflag(stripXSS(stuRegForm.getStudent().getTwinflag(), "TwinFlag", "NewInfo1", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
		//log.debug("ApplyForStudentNumberAction - applyNewInfo1 - Twins="+stuRegForm.getStudent().getTwinflag());
		if (stuRegForm.getStudent().getTwinflag() == null || "".equals(stuRegForm.getStudent().getTwinflag().trim()) || "undefined".equals(stuRegForm.getStudent().getTwinflag().trim())){
			//log.debug("ApplyForStudentNumberAction - applyNewInfo1 - Twinflag="+stuRegForm.getStudent().getTwinflag());
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please indicate if you have a twin sibling."));
			addErrors(request, messages);
			setDropdownListsStep3(request, stuRegForm);
			return "applyNewInfo1";
		}else{
			stuRegForm.getStudent().setTwinflag("N");
		}
		
		// nationality
		stuRegForm.setSelectedNationality(stripXSS(stuRegForm.getSelectedNationality(), "SelectedNationality", "NewInfo1", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));		
		if (stuRegForm.getSelectedNationality() == null || "-1".equals(stuRegForm.getSelectedNationality())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Select your nationality."));
			addErrors(request, messages);
			setDropdownListsStep3(request, stuRegForm);
			return "applyNewInfo1";
		}else{
			stuRegForm.getStudent().getNationalty().setCode(stuRegForm.getSelectedNationality().substring(0,4));
			stuRegForm.getStudent().getNationalty().setDesc(stuRegForm.getSelectedNationality().substring(4));
		}
		// population group
		stuRegForm.setSelectedPopulationGroup(stripXSS(stuRegForm.getSelectedPopulationGroup(), "SelectedPopulationGroup", "NewInfo1", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));		
		if (stuRegForm.getSelectedPopulationGroup() == null || "-1".equals(stuRegForm.getSelectedPopulationGroup())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Select your population group."));
			addErrors(request, messages);
			setDropdownListsStep3(request, stuRegForm);
			return "applyNewInfo1";
		}else{
			GeneralItem genItem = new GeneralItem();
			genItem = getItem(stuRegForm.getSelectedPopulationGroup());
			stuRegForm.getStudent().getPopulationGroup().setCode(genItem.getCode());
			stuRegForm.getStudent().getPopulationGroup().setDesc(genItem.getDesc());
		}
		// Occupation
		stuRegForm.setSelectedOccupation(stripXSS(stuRegForm.getSelectedOccupation(), "SelectedOccupation", "NewInfo1", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));		
		if (stuRegForm.getSelectedOccupation() == null || "-1".equals(stuRegForm.getSelectedOccupation())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Select your occupation."));
			addErrors(request, messages);
			setDropdownListsStep3(request, stuRegForm);
			return "applyNewInfo1";
		}else{
			GeneralItem genItem = new GeneralItem();
			genItem = getItem(stuRegForm.getSelectedOccupation());
			stuRegForm.getStudent().getOccupation().setCode(genItem.getCode());
			stuRegForm.getStudent().getOccupation().setDesc(genItem.getDesc());
		}
		// Economic sector
		stuRegForm.setSelectedEconomicSector(stripXSS(stuRegForm.getSelectedEconomicSector(), "SelectedEconomicSector", "NewInfo1", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));		
		if (stuRegForm.getSelectedEconomicSector() == null || "-1".equals(stuRegForm.getSelectedEconomicSector())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Select your economic sector."));
			addErrors(request, messages);
			setDropdownListsStep3(request, stuRegForm);
			return "applyNewInfo1";
		}else{
			GeneralItem genItem = new GeneralItem();
			genItem = getItem(stuRegForm.getSelectedEconomicSector());
			stuRegForm.getStudent().getEconomicSector().setCode(genItem.getCode());
			stuRegForm.getStudent().getEconomicSector().setDesc(genItem.getDesc());
		}
		// previous activity
		stuRegForm.setSelectedPrevActivity(stripXSS(stuRegForm.getSelectedPrevActivity(), "SelectedPrevActivity", "NewInfo1", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));		
		if (stuRegForm.getSelectedPrevActivity() == null || "-1".equals(stuRegForm.getSelectedPrevActivity())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Select your previous economic activity."));
			addErrors(request, messages);
			setDropdownListsStep3(request, stuRegForm);
			return "applyNewInfo1";
		}else{
			GeneralItem genItem = new GeneralItem();
			genItem = getItem(stuRegForm.getSelectedPrevActivity());
			stuRegForm.getStudent().getPrevActivity().setCode(genItem.getCode());
			stuRegForm.getStudent().getPrevActivity().setDesc(genItem.getDesc());
		}
		
		//log.debug("ApplyForStudentNumberAction - applyNewInfo1 - End");
		//log.debug("ApplyForStudentNumberAction - applyNewInfo1 - Goto applyNewInfo2");
		return "applyNewInfo2";
	}
	
	public String applyNewInfo2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		//log.debug("ApplyForStudentNumberAction - applyNewInfo2 - Start");

		// if student number is null, block application
		ActionMessages messages = new ActionMessages();
		StudentRegistrationForm stuRegForm = (StudentRegistrationForm) form;
		
		if(stuRegForm.getStudent().getNumber() == null || "".equals(stuRegForm.getStudent().getNumber())){
			//log.debug("ApplyForStudentNumberAction - applyNewInfo2 - Student Nr is null or empty when saving qualifications");
			messages.add(ActionMessages.GLOBAL_MESSAGE,
			new ActionMessage("message.generalmessage", "You performed and invalid action or an Error occurred. Please log on again to retry."));
			addErrors(request, messages);
			if (stuRegForm.getAdminStaff().isAdmin()){
				return "loginStaff";
			}else{
				return "loginStu";
			}
		}

		if (stuRegForm.getFromPage() == null){
			//Error: Session empty
			//log.debug("ApplyForStudentNumberAction - applyNewInfo2 Empty Session");
			return emptySession(mapping,form,request,response);
		}else{
			stuRegForm.setFromPage("applyNewInfo2");
		}
		
		//Adding Complete Qualification Request here so that it is set before any errors may be thrown as otherwise it might not be set when returning to the page.
		stuRegForm.getStudentApplication().setCompleteText(stripXSS(request.getParameter("completeText"), "CompleteText", "NewInfo2", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
		
		//CareerCounsel
		stuRegForm.getStudentApplication().setCareerCounsel(stripXSS(stuRegForm.getStudentApplication().getCareerCounsel(), "CareerCounsel", "NewInfo2", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));		
		if (stuRegForm.getStudentApplication().getCareerCounsel() == null || "".equals(stuRegForm.getStudentApplication().getCareerCounsel().trim())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please confirm if you require further Career counceling."));
			addErrors(request, messages);
			return "applyNewInfo2";
		}else{
			stuRegForm.getStudentApplication().setCareerCounsel("N");
		}
		
		//Current Unisa Staff
		stuRegForm.getStudentApplication().setStaffCurrent(stripXSS(stuRegForm.getStudentApplication().getStaffCurrent(), "StaffCurrent", "NewInfo2", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));		
		if (stuRegForm.getStudentApplication().getStaffCurrent() == null || "".equalsIgnoreCase(stuRegForm.getStudentApplication().getStaffCurrent())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please confirm if you are a current or retired Unisa staff member."));
			addErrors(request, messages);
			return "applyNewInfo2";
		}
		//Dependent on deceased or retired Unisa Staff
		stuRegForm.getStudentApplication().setStaffDeceased(stripXSS(stuRegForm.getStudentApplication().getStaffDeceased(), "StaffDeceased", "NewInfo2", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));		
		if (stuRegForm.getStudentApplication().getStaffDeceased() == null || "".equalsIgnoreCase(stuRegForm.getStudentApplication().getStaffDeceased())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please confirm if you are a dependant of a current, retired or deceased permanent Unisa staff member."));
			addErrors(request, messages);
			return "ApplyNewInfo2";
		}
		//Prisoner Information
		stuRegForm.getStudentApplication().setPrisoner(stripXSS(stuRegForm.getStudentApplication().getPrisoner(), "Prisoner", "NewInfo2", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));		
		if (stuRegForm.getStudentApplication().getPrisoner() == null || "".equalsIgnoreCase(stuRegForm.getStudentApplication().getPrisoner())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please confirm if you are a prisoner."));
			addErrors(request, messages);
			return "applyNewInfo2";
		}else{
			// set examination centre
			stuRegForm.setSelectedExamCentre(stripXSS(stuRegForm.getSelectedExamCentre(), "SelectedExamCentre", "NewInfo2", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
			if (stuRegForm.getSelectedExamCentre() == null || "-1".equals(stuRegForm.getSelectedExamCentre()) || "".equals(stuRegForm.getSelectedExamCentre().trim()) || stuRegForm.getSelectedExamCentre().length() < 5){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Select your examination centre."));
				addErrors(request, messages);
				return "applyNewInfo2";
			}else{
				stuRegForm.getStudent().getExam().getExamCentre().setCode(stuRegForm.getSelectedExamCentre().substring(0,5));
				stuRegForm.getStudent().getExam().getExamCentre().setDesc(stuRegForm.getSelectedExamCentre().substring(5));
			}
		}
		
		//Financial Aid
		stuRegForm.getStudentApplication().setFinaidEduloan(stripXSS(stuRegForm.getStudentApplication().getFinaidEduloan(), "FinaidEduLoan", "NewInfo2", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));		
		if (stuRegForm.getStudentApplication().getFinaidEduloan()==null || "".equals(stuRegForm.getStudentApplication().getFinaidEduloan().trim())){
			stuRegForm.getStudentApplication().setFinaidEduloan("N");
		}
		if (stuRegForm.getStudentApplication().getFinaidNsfas()==null || "".equals(stuRegForm.getStudentApplication().getFinaidNsfas().trim())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please indicate whether you will require Financial aid from NSFAS."));
			addErrors(request, messages);
			return "applyNewInfo2";
		}else{
			stuRegForm.getStudentApplication().setFinaidNsfas("N");
		}
		
		//Can complete Qualification this year?
		stuRegForm.getStudentApplication().setCompleteQual(stripXSS(stuRegForm.getStudentApplication().getCompleteQual(), "CompleteQual", "NewInfo2", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));		
		if (stuRegForm.getStudentApplication().getCompleteQual() == null || "".equalsIgnoreCase(stuRegForm.getStudentApplication().getCompleteQual()) || "0".equalsIgnoreCase(stuRegForm.getStudentApplication().getCompleteQual())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please confirm if you are in the process of completing a qualification."));
			addErrors(request, messages);
			return "applyNewInfo2";
		}
		
		// Qualification to be completed
		if (stuRegForm.getStudentApplication().getCompleteQual().equalsIgnoreCase("Y")){
			//log.debug("ApplyForStudentNumberaction - applyNewInfo2- CompleteText Request=" + request.getParameter("completeText"));
			//log.debug("ApplyForStudentNumberaction - applyNewInfo2 - CompleteText Get=" + stuRegForm.getStudentApplication().getCompleteText());
			
			if (stuRegForm.getStudentApplication().getCompleteText() == null || "".equalsIgnoreCase(stuRegForm.getStudentApplication().getCompleteText().trim()) || stuRegForm.getStudentApplication().getCompleteText().trim().length() < 1){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Please enter which qualification you will be completing."));
				addErrors(request, messages);
				return "applyNewInfo2";
			}
			if (stuRegForm.getStudentApplication().getCompleteText().trim().length() > 100){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Qualification to be completed is too long. Please enter 100 characters or less. " ));
				addErrors(request, messages);
				return "applyNewInfo2";
			}
		}
		
		//log.debug("ApplyForStudentNumberaction - applyNewInfo2 - CompleteText Final=" + stuRegForm.getStudentApplication().getCompleteText());

		
		//log.debug("ApplyForStudentNumberAction - applyNewInfo2 - End");
		//log.debug("ApplyForStudentNumberAction - applyNewInfo2 - Goto applyNewInfo3");
		setUpUniversityList(request);
		return "applyNewInfo3";
	}
	
	public String applyNewInfo3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//log.debug("ApplyForStudentNumberAction - applyNewInfo3 - Start");
		
		// if student number is null, block application
		ActionMessages messages = new ActionMessages();
		StudentRegistrationForm stuRegForm = (StudentRegistrationForm) form;
		
		if(stuRegForm.getStudent().getNumber() == null || "".equals(stuRegForm.getStudent().getNumber())){
			//log.debug("ApplyForStudentNumberAction - applyNewInfo3 - Student Nr is null or empty when saving qualifications");
			messages.add(ActionMessages.GLOBAL_MESSAGE,
			new ActionMessage("message.generalmessage", "You performed and invalid action or an Error occurred. Please log on again to retry."));
			addErrors(request, messages);
			if (stuRegForm.getAdminStaff().isAdmin()){
				return "loginStaff";
			}else{
				return "loginStu";
			}
		}
		GeneralMethods gen = new GeneralMethods();

		if (stuRegForm.getFromPage() == null){
			//Error: Session empty
			//log.debug("ApplyForStudentNumberAction - applyNewInfo3 - Empty Session");
			return emptySession(mapping,form,request,response);
		}else{
			stuRegForm.setFromPage("applyNewInfo3");
		}
		
		// check last year if last institution was entered
		stuRegForm.setSelectedPrevInstitution(stripXSS(stuRegForm.getSelectedPrevInstitution(), "SelectedPrevInstitution", "NewInfo3", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));		
		if (stuRegForm.getSelectedPrevInstitution() != null && !"-1".equals(stuRegForm.getSelectedPrevInstitution())){
			GeneralItem genItem = new GeneralItem();
			genItem = getItem(stuRegForm.getSelectedPrevInstitution());
			stuRegForm.getStudent().getPrevInstitution().setCode(genItem.getCode());
			stuRegForm.getStudent().getPrevInstitution().setDesc(genItem.getDesc());
			if (stuRegForm.getStudent().getLastRegYear() == null || "".equals(stuRegForm.getStudent().getLastRegYear().trim())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Enter the last year of registration at previous tertiary institution."));
				addErrors(request, messages);
				setUpUniversityList(request);
				return "applyNewInfo3";
			}else if(! gen.isNumeric(stuRegForm.getStudent().getLastRegYear()) || stuRegForm.getStudent().getLastRegYear().trim().length()!=4){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Last year of registration can only have numerical characters. Use numeric format [CCYY]"));
				addErrors(request, messages);
				setUpUniversityList(request);
				return "applyNewInfo3";
			}
			//Johanet 20180828 SLP comment out mandatory check student number previous tertiary institiution
//			if (stuRegForm.getStudentApplication().getPrevinstStudnr() == null || "".equals(stuRegForm.getStudentApplication().getPrevinstStudnr().trim())){
//				messages.add(ActionMessages.GLOBAL_MESSAGE,
//					new ActionMessage("message.generalmessage", "Enter Your student number at the previous tertiary institution."));
//				addErrors(request, messages);
//				setUpUniversityList(request);
//				return "applyNewInfo3";
//			}
			
			//log.debug("ApplyForStudentNumberAction - applyStep3 - N " + stuRegForm.getStudent().getNumber() + " PrevInstCode " + stuRegForm.getStudent().getPrevInstitution().getCode());
		}else{
			//log.debug("ApplyForStudentNumberAction - applyStep3 - N " + stuRegForm.getStudent().getNumber() + " PrevInstCode is Null or Empty (Thus not selected)");
		}

		if (stuRegForm.getStudent().getLastRegYear() != null && !"".equals(stuRegForm.getStudent().getLastRegYear().trim())){
			if(! gen.isNumeric(stuRegForm.getStudent().getLastRegYear()) || stuRegForm.getStudent().getLastRegYear().trim().length()!=4){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Last year of registration can only have numerical characters or is invalid. Use numeric format [CCYY] or leave blank"));
				addErrors(request, messages);
				setUpUniversityList(request);
				return "applyNewInfo3";
			}
		}		
		stuRegForm.getStudent().setLastRegYear(stripXSS(stuRegForm.getStudent().getLastRegYear(), "LastRegYear", "NewInfo3", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));		

		//Exemption
		stuRegForm.getStudentApplication().setApplyExemptions(stripXSS(stuRegForm.getStudentApplication().getApplyExemptions(), "ApplyExemptions", "NewInfo3", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));		
		if (stuRegForm.getStudentApplication().getApplyExemptions() == null || "".equals(stuRegForm.getStudentApplication().getApplyExemptions().trim())){
			stuRegForm.getStudentApplication().setApplyExemptions("N");
		}
		
		//log.debug("ApplyForStudentNumberAction - applyNewInfo3 - End");
		//log.debug("ApplyForStudentNumberAction - applyNewInfo3 - Goto applyNewSchool");
		setUpProvinceList(request);
		return "applyNewSchool";
	}

	public String applyNewSchool(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		// if student number is null, block application
		ActionMessages messages = new ActionMessages();
		StudentRegistrationForm stuRegForm = (StudentRegistrationForm) form;
		
		if(stuRegForm.getStudent().getNumber() == null || "".equals(stuRegForm.getStudent().getNumber())){
			//log.debug("ApplyForStudentNumberAction - applyNewSchool - Student Nr is null or empty when saving qualifications");
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "You performed and invalid action or an Error occurred. Please log on again to retry."));
			addErrors(request, messages);
			if (stuRegForm.getAdminStaff().isAdmin()){
				return "loginStaff";
			}else{
				return "loginStu";
			}
		}

		if (stuRegForm.getFromPage() == null){
			//Error: Session empty
			//log.debug("ApplyForStudentNumberAction - applyNewSchool - Empty Session");
			return emptySession(mapping,form,request,response);
		}else{
			stuRegForm.setFromPage("applyNewSchool");
		}
		
		//School leaving particulars
		//Previous UnderPost
		stuRegForm.getStudentApplication().setMatricCertificate(stripXSS(stuRegForm.getStudentApplication().getMatricCertificate(), "MatricCertificate", "NewSchool", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));		
		stuRegForm.getStudentApplication().setHeAdmission(stripXSS(stuRegForm.getStudentApplication().getHeAdmission(), "HEAdmission", "NewSchool", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));		

		if ("U".equalsIgnoreCase(stuRegForm.getQual().getUnderpostGrad1()) || "U".equalsIgnoreCase(stuRegForm.getQual().getUnderpostGrad2())){
			//Matric Certificate and admission mandatory for Undergrad
			//log.debug("ApplyForStudentNumberAction - applyNewSchool - N " + stuRegForm.getStudent().getNumber() + " Matric UG1 " + stuRegForm.getQual().getUnderpostGrad1() + " UG2 " + stuRegForm.getQual().getUnderpostGrad2());

			if("".equalsIgnoreCase(stuRegForm.getStudentApplication().getMatricCertificate()) || stuRegForm.getStudentApplication().getMatricCertificate() ==null){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please indicate your school leaving certificate"));
				addErrors(request, messages);
				setUpProvinceList(request);
				return "applyNewSchool";

			}
			//Admission not mandatory for CG
			//log.debug("ApplyForStudentNumberAction - applyNewSchool - HE admission MatricCert: " + stuRegForm.getStudentApplication().getMatricCertificate());
			if (!"CG".equalsIgnoreCase(stuRegForm.getStudentApplication().getMatricCertificate())){
				if("".equalsIgnoreCase(stuRegForm.getStudentApplication().getHeAdmission()) || stuRegForm.getStudentApplication().getHeAdmission() ==null){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Please indicate your HE admission result"));
					addErrors(request, messages);
					setUpProvinceList(request);
					return "applyNewSchool";
				}
				//log.debug("ApplyForStudentNumberAction - applyNewSchool - N " + stuRegForm.getStudent().getNumber() + " Matric Not CG UG1" + stuRegForm.getQual().getUnderpostGrad1() + " UG2 " + stuRegForm.getQual().getUnderpostGrad2());

			}
		}
		if ("SC".equalsIgnoreCase(stuRegForm.getStudentApplication().getMatricCertificate())){
			if ("B".equalsIgnoreCase(stuRegForm.getStudentApplication().getHeAdmission()) || "C".equalsIgnoreCase(stuRegForm.getStudentApplication().getHeAdmission()) || "D".equalsIgnoreCase(stuRegForm.getStudentApplication().getHeAdmission())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "The matric admission result is invalid for choice of School leaving certificate"));
				addErrors(request, messages);
				setUpProvinceList(request);
				return "applyNewSchool";
			}
			//log.debug("ApplyForStudentNumberAction - applyNewSchool - N " + stuRegForm.getStudent().getNumber() + " Matric SC UG1 " + stuRegForm.getQual().getUnderpostGrad1() + " UG2 " + stuRegForm.getQual().getUnderpostGrad2());

		}
		if ("NC".equalsIgnoreCase(stuRegForm.getStudentApplication().getMatricCertificate())){
			if ("M".equalsIgnoreCase(stuRegForm.getStudentApplication().getHeAdmission()) || "E".equalsIgnoreCase(stuRegForm.getStudentApplication().getHeAdmission())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "The matric admission result is invalid for choice of School leaving certificate"));
				addErrors(request, messages);
				setUpProvinceList(request);
					return "applyNewSchool";
			}
			//log.debug("ApplyForStudentNumberAction - applyNewSchool - N " + stuRegForm.getStudent().getNumber() + " Matric NC UG1 " + stuRegForm.getQual().getUnderpostGrad1() + " UG2 " + stuRegForm.getQual().getUnderpostGrad2());

		}
		// populate certificate description to move to M30 if needed
		String matric="";
		if("SC".equalsIgnoreCase(stuRegForm.getStudentApplication().getMatricCertificate())){
			matric="Senior Certificate";
			stuRegForm.getStudent().setMatrix("NOCG"); //Reset
		}
		if("NC".equalsIgnoreCase(stuRegForm.getStudentApplication().getMatricCertificate())){
			matric="National Senior certificate";
			stuRegForm.getStudent().setMatrix("NOCG"); //Reset
		}
		if("FF".equalsIgnoreCase(stuRegForm.getStudentApplication().getMatricCertificate())){
			matric="Failed std 10/Grade 12";
			stuRegForm.getStudent().setMatrix("NOCG"); //Reset
		}
		if("CG".equalsIgnoreCase(stuRegForm.getStudentApplication().getMatricCertificate())){
			matric="Currently in Grade 12";
			stuRegForm.getStudent().setMatrix("CG");
		}
		if("FF".equalsIgnoreCase(stuRegForm.getStudentApplication().getMatricCertificate())){
			matric="Failed std 10/Grade12";
			stuRegForm.getStudent().setMatrix("NOCG"); //Reset
		}
		if("NV".equalsIgnoreCase(stuRegForm.getStudentApplication().getMatricCertificate())){
			matric="National Certificate Vocational";
			stuRegForm.getStudent().setMatrix("NOCG"); //Reset
		}
		if("OO".equalsIgnoreCase(stuRegForm.getStudentApplication().getMatricCertificate())){
			matric="Other";
			stuRegForm.getStudent().setMatrix("NOCG"); //Reset
		}
		stuRegForm.getStudentApplication().setCertificateDesc(matric);

		//log.debug("ApplyForStudentNumberAction - applyNewSchool - N " + stuRegForm.getStudent().getNumber() + " MatricCert: " + stuRegForm.getStudentApplication().getCertificateDesc());
		
		//log.debug("ApplyForStudentNumberAction - applyNewSchool - N " + stuRegForm.getStudent().getNumber() + " Matrix:     " + stuRegForm.getStudent().getMatrix());
		
		// Computer Training previously removed - Hardcoded to "N"
		stuRegForm.getStudent().setComputerTraining(stripXSS(stuRegForm.getStudent().getComputerTraining(), "ComputerTraining", "NewSchool", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));		
		if (stuRegForm.getStudent().getComputerTraining() == null || "".equals(stuRegForm.getStudent().getComputerTraining().trim())){
			stuRegForm.getStudent().setComputerTraining("N");
		}

		// Matric Year
		if ("U".equalsIgnoreCase(stuRegForm.getQual().getUnderpostGrad1()) || "U".equalsIgnoreCase(stuRegForm.getQual().getUnderpostGrad2())){
			stuRegForm.getStudentApplication().setMatricExamyear(stripXSS(stuRegForm.getStudentApplication().getMatricExamyear(), "MatricExamYear", "NewSchool", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));		
			if (stuRegForm.getStudentApplication().getMatricExamyear()!= null && !"".equals(stuRegForm.getStudentApplication().getMatricExamyear())){
				if (!isValidNumber(stuRegForm.getStudentApplication().getMatricExamyear())){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Matric year must be numeric format [CCYY]"));
					addErrors(request, messages);
					setUpProvinceList(request);
					return "applyNewSchool";
				}
			}
	
			// Matric number
			//log.debug("ApplyForStudentNumberAction - applyNewSchool - Matric number=[" + stuRegForm.getStudentApplication().getMatricExamnr()+"]");
			stuRegForm.getStudentApplication().setMatricExamnr(stripXSS(stuRegForm.getStudentApplication().getMatricExamnr(), "MatricExamNr", "NewSchool", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));		
			if (stuRegForm.getStudentApplication().getMatricExamnr()!= null && !"".equals(stuRegForm.getStudentApplication().getMatricExamnr())){
				if (stuRegForm.getStudentApplication().getMatricExamnr().length() > 13){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Matric exam number is too long. Please enter 13 characters or less"));
					addErrors(request, messages);
					setUpProvinceList(request);
					return "applyNewSchool";
				}
			}

			// check if matric province entered
			stuRegForm.setSelectedProvince(stripXSS(stuRegForm.getSelectedProvince(), "MatricProvince", "NewSchool", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));		
			if (stuRegForm.getSelectedProvince()!= null && !"-1".equals(stuRegForm.getSelectedProvince())){
				GeneralItem provItem = new GeneralItem();
				provItem = getItem(stuRegForm.getSelectedProvince());
				stuRegForm.getStudentApplication().getMatricProvince().setCode(provItem.getCode());
				stuRegForm.getStudentApplication().getMatricProvince().setDesc(provItem.getDesc());
				//log.debug("ApplyForStudentNumberAction - applyNewSchool - MatricProvince=[" + stuRegForm.getStudentApplication().getMatricProvince().getCode()+"/"+stuRegForm.getStudentApplication().getMatricProvince().getDesc()+"]");
	
				// check if matric school entered
				stuRegForm.setSelectedSchool(stripXSS(stuRegForm.getSelectedSchool(), "MatricSchool", "NewSchool", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));		
				if (stuRegForm.getSelectedSchool() == null || "-1".equals(stuRegForm.getSelectedSchool()) || "".equals(stuRegForm.getSelectedSchool().trim()) || stuRegForm.getSelectedSchool().length() == 0){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Please indicate your school"));
					addErrors(request, messages);
					setUpProvinceList(request);
					return "applyNewSchool";
				}else {
					GeneralItem schoolItem = new GeneralItem();
					schoolItem = getItem(stuRegForm.getSelectedSchool());
					stuRegForm.getStudentApplication().getMatricSchool().setCode(schoolItem.getCode());
					stuRegForm.getStudentApplication().getMatricSchool().setDesc(schoolItem.getDesc());
					stuRegForm.getMatric().setSchoolName(schoolItem.getDesc());
				}
				//log.debug("ApplyForStudentNumberAction - applyNewSchool - MatricSchool=[" + stuRegForm.getStudentApplication().getMatricSchool().getCode()+"/"+stuRegForm.getStudentApplication().getMatricSchool().getDesc()+"]");

			}
		}

		if(stuRegForm.getStudent().getNumber() == null || "".equals(stuRegForm.getStudent().getNumber())){
			//log.debug("ApplyForStudentNumberAction - applyNewSchool - Student Nr is null or empty when saving qualifications");
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "You performed and invalid action or an Error occurred. Please log on again to retry."));
			addErrors(request, messages);
			setUpProvinceList(request);
			return "applyNewSchool";
		}
		
		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
		
		String newQual1 = dao.getXMLSelected("qualCode1", "1",stuRegForm.getStudent().getNumber(),stuRegForm.getStudent().getAcademicYear(),stuRegForm.getStudent().getAcademicPeriod(), "applyNewSchool");
		String newQual2 = dao.getXMLSelected("qualCode2", "2",stuRegForm.getStudent().getNumber(),stuRegForm.getStudent().getAcademicYear(),stuRegForm.getStudent().getAcademicPeriod(), "applyNewSchool");
		
		String newSpec1 = dao.getXMLSelected("specCode1", "1",stuRegForm.getStudent().getNumber(),stuRegForm.getStudent().getAcademicYear(),stuRegForm.getStudent().getAcademicPeriod(), "applyNewSchool");
		String newSpec2 = dao.getXMLSelected("specCode2", "2",stuRegForm.getStudent().getNumber(),stuRegForm.getStudent().getAcademicYear(),stuRegForm.getStudent().getAcademicPeriod(), "applyNewSchool");

		if (stuRegForm.getStudent().getQual1() == null || newQual1 == null || stuRegForm.getStudent().getSpec1() == null || newSpec1 == null || stuRegForm.getStudent().getQual2() == null || newQual2 == null || stuRegForm.getStudent().getSpec2() == null || newSpec2 == null){
			//log.debug("ApplyForStudentNumberAction - applyNewSchool Student Has Null Entry Student="+stuRegForm.getStudent().getNumber()+", Qual1=" + stuRegForm.getStudent().getQual1() +", XMLQual1="+newQual1+", Qual2=" + stuRegForm.getStudent().getQual2() +", XMLQual2="+newQual2+", Spec1=" + stuRegForm.getStudent().getSpec1() +", XMLSpec1="+newSpec1+", Spec2=" + stuRegForm.getStudent().getSpec2() +", XMLSpec2="+newSpec2);
			messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", "An Error occurred while processing your Qualification selection (4N). Please try again."));
			addErrors(request, messages);
			return "applyQualification";
		}
			
		stuRegForm.getStudent().setQual1(newQual1);
		stuRegForm.getStudent().setSpec1(newSpec1);
		stuRegForm.getStudent().setQual2(newQual2);
		stuRegForm.getStudent().setSpec2(newSpec2);

		//log.debug("ApplyForStudentNumberAction - applyNewSchool - get Action newQual1: " + newQual1);
		//log.debug("ApplyForStudentNumberAction - applyNewSchool - get Action newSpec1: " + newSpec1);
		//log.debug("ApplyForStudentNumberAction - applyNewSchool - get Action newQual2: " + newQual2);
		//log.debug("ApplyForStudentNumberAction - applyNewSchool - get Action newSpec2: " + newSpec2);
				
		// ---------- Check input
		String underpost1 = " ";
		String underpost2 = " ";
		String code1 = " ";
		String code2 = " ";
		String qtype1 = " ";
		String qtype2 = " ";
		String qkat1 = " ";
		String qkat2 = " ";

		//log.debug("ApplyForStudentNumberAction - applyForStudentNumberAction - applyNewSchool - getQual1: " + newQual1);
		// Proposed qualification
		if (newQual1 != null && !"0".equals(newQual1) && !"undefined".equalsIgnoreCase(newQual1)){
			code1 = dao.validateQualification(stuRegForm.getStudent().getNumber(), newQual1);
			underpost1 = code1.substring(0,1);
			qtype1 = code1.substring(1,2);
			qkat1 = code1.substring(2);
		}else{
			messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", "An Error occurred while processing your primary qualification selection. Please try again."));
			addErrors(request, messages);
			setUpProvinceList(request);
			return "applyNewSchool";
		}

		//log.debug("ApplyForStudentNumberAction - applyNewSchool - getQual2: " + newQual2);
		
		if (newQual2 != null && !"0".equals(newQual2) && !"undefined".equalsIgnoreCase(newQual2) && !"".equalsIgnoreCase(newQual2)){
			code2 = dao.validateQualification(stuRegForm.getStudent().getNumber(), newQual2);
			underpost2 = code2.substring(0,1);
			qtype2 = code2.substring(1,2);
			qkat2 = code2.substring(2);
		}
		
		//log.debug("ApplyForStudentNumberAction - applyNewSchool - N " + stuRegForm.getStudent().getNumber() + " getQual1 - code1: " + code1);
		//log.debug("ApplyForStudentNumberAction - applyNewSchool - N " + stuRegForm.getStudent().getNumber() + " getQual1 - underpost1: " + underpost1);
		//log.debug("ApplyForStudentNumberAction - applyNewSchool - N " + stuRegForm.getStudent().getNumber() + " getQual1 - qtype1: " + qtype1);
		//log.debug("ApplyForStudentNumberAction - applyNewSchool - N " + stuRegForm.getStudent().getNumber() + " getQual1 - qkat1: " + qkat1);
		
		//log.debug("ApplyForStudentNumberAction - applyNewSchool - N " + stuRegForm.getStudent().getNumber() + " getQual2 - code2: " + code2);
		//log.debug("ApplyForStudentNumberAction - applyNewSchool - N " + stuRegForm.getStudent().getNumber() + " getQual2 - underpost2: " + underpost2);
		//log.debug("ApplyForStudentNumberAction - applyNewSchool - N " + stuRegForm.getStudent().getNumber() + " getQual2 - qtype2: " + qtype2);
		//log.debug("ApplyForStudentNumberAction - applyNewSchool - N " + stuRegForm.getStudent().getNumber() + " getQual2 - qkat2: " + qkat2);
		
		// continue from Name page next = Submit page or M30 page
		//log.debug("ApplyForStudentNumberAction - applyNewSchool - N " + stuRegForm.getStudent().getNumber() + "  Test for M30 MatricCert: " + stuRegForm.getStudentApplication().getMatricCertificate());
		if (("G".equalsIgnoreCase(qtype1) && "U".equalsIgnoreCase(underpost1) && !"26".equals(qkat1)) || ("G".equalsIgnoreCase(qtype2) && "U".equalsIgnoreCase(underpost2) && !"26".equals(qkat2))){
			if (!"CG".equals( stuRegForm.getStudentApplication().getMatricCertificate())){
				if ("M".equals(stuRegForm.getStudentApplication().getHeAdmission()) || "S".equals(stuRegForm.getStudentApplication().getHeAdmission()) || "O".equals(stuRegForm.getStudentApplication().getHeAdmission()) || "F".equals(stuRegForm.getStudentApplication().getHeAdmission())){
					//log.debug("ApplyForStudentNumberAction - applyNewSchool - N " + stuRegForm.getStudent().getNumber() + " HE Admission - MSOF: " + stuRegForm.getStudentApplication().getHeAdmission());
					//log.debug("ApplyForStudentNumberAction - applyNewSchool - Goto m30step1");
					return "m30step1";
				}else{
					//log.debug("ApplyForStudentNumberAction - applyNewSchool - N " + stuRegForm.getStudent().getNumber() + " NOT MSOF - return applyNewSchool 1");
					return "applyNewDeclare";
				}
			}else{
				//log.debug("ApplyForStudentNumberAction - applyNewSchool - N " + stuRegForm.getStudent().getNumber() + " NOT CG: " + stuRegForm.getStudentApplication().getMatricCertificate() + " return applyNewSchool 2");
				return "applyNewDeclare";
			}
		}else{
			//log.debug("ApplyForStudentNumberAction - applyNewSchool - N " + stuRegForm.getStudent().getNumber() + " NOT CAT1 & CAT2 not GU26  return applyNewDeclare 3");
			return "applyNewDeclare";
	}
		//return "applyNewDeclare" is next screen if not M30 case;
	}

	public String applym30p1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		//log.debug("ApplyForStudentNumberAction - applym30p1 - Start");
		
		// if student number is null, block application
		ActionMessages messages = new ActionMessages();
		StudentRegistrationForm stuRegForm = (StudentRegistrationForm) form;
		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
		
		if(stuRegForm.getStudent().getNumber() == null || "".equals(stuRegForm.getStudent().getNumber())){
			//log.debug("ApplyForStudentNumberAction - applym30p1 - Student Nr is null or empty when saving qualifications");
			messages.add(ActionMessages.GLOBAL_MESSAGE,
			new ActionMessage("message.generalmessage", "You performed and invalid action or an Error occurred. Please log on again to retry."));
			addErrors(request, messages);
			if (stuRegForm.getAdminStaff().isAdmin()){
				return "loginStaff";
			}else{
				return "loginStu";
			}
		}

		String newQual1 = dao.getXMLSelected("qualCode1", "1",stuRegForm.getStudent().getNumber(),stuRegForm.getStudent().getAcademicYear(),stuRegForm.getStudent().getAcademicPeriod(), "applym30p1");

		//log.debug(stuRegForm.getStudent().getNumber() + " ApplyForStudentNumberAction - applym30p1 FromXML Qual1: " + newQual1);

		stuRegForm.getMatric().setSchoolCertificate(stripXSS(stuRegForm.getMatric().getSchoolCertificate(), "SchoolCertificate", "applym30p1", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));		
		if (stuRegForm.getMatric().getSchoolCertificate() ==null || "".equals(stuRegForm.getMatric().getSchoolCertificate().trim())){
			stuRegForm.getMatric().setSchoolCertificate(stuRegForm.getStudentApplication().getCertificateDesc());
		}
		stuRegForm.getMatric().setQualPropose(stripXSS(stuRegForm.getMatric().getQualPropose(), "QualPurpose", "applym30p1", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));		
		if (stuRegForm.getMatric().getQualPropose() ==null || "".equals(stuRegForm.getMatric().getQualPropose().trim())){
			stuRegForm.getMatric().setQualPropose(newQual1);
		}
		stuRegForm.getMatric().setQualFirstYr(stripXSS(stuRegForm.getMatric().getQualFirstYr(), "QualFirstYear", "applym30p1", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));		
		if (stuRegForm.getMatric().getQualFirstYr() ==null || "".equals(stuRegForm.getMatric().getQualFirstYr().trim())){
			stuRegForm.getMatric().setQualFirstYr(stuRegForm.getStudent().getAcademicYear());
		}
		stuRegForm.getStudent().getCountry().setCode(stripXSS(stuRegForm.getStudent().getCountry().getCode(), "SchoolCountry", "applym30p1", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));		
		if ("1015".equalsIgnoreCase(stuRegForm.getStudent().getCountry().getCode())){
			stuRegForm.getMatric().setSchoolProvCountry(stuRegForm.getStudentApplication().getMatricProvince().getDesc());
		}else{
			stuRegForm.getMatric().setSchoolProvCountry(stuRegForm.getStudent().getCountry().getDesc());
		}
		//ToDo
		//Add Matric School
		
		// call from M30 page1 next = M30 page2
		//log.debug("ApplyForStudentNumberAction - m30step1 - End");
		//log.debug("ApplyForStudentNumberAction - m30step1 - Goto m30step2");
		return "m30step2";
	}

	public String applym30p2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		//log.debug("ApplyForStudentNumberAction - applym30p2 - Start");
		// if student number is null, block application
		ActionMessages messages = new ActionMessages();
		StudentRegistrationForm stuRegForm = (StudentRegistrationForm) form;
		if(stuRegForm.getStudent().getNumber() == null || "".equals(stuRegForm.getStudent().getNumber())){
			//log.debug("ApplyForStudentNumberAction - applym30p2 - Student Nr is null or empty when saving qualifications");
			messages.add(ActionMessages.GLOBAL_MESSAGE,
			new ActionMessage("message.generalmessage", "You performed and invalid action or an Error occurred. Please log on again to retry."));
			addErrors(request, messages);
			if (stuRegForm.getAdminStaff().isAdmin()){
				return "loginStaff";
			}else{
				return "loginStu";
			}
		}

		stuRegForm.getMatric().setSchoolCertificate(stripXSS(stuRegForm.getMatric().getSchoolCertificate(), "SchoolCertificate", "applym30p2", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));		
		if (stuRegForm.getMatric().getSchoolCertificate() ==null || "".equals(stuRegForm.getMatric().getSchoolCertificate().trim())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please fill in your School Certificate."));
			addErrors(request, messages);
			return "m30step2";
		}
		stuRegForm.getStudentApplication().setHeAdmission(stripXSS(stuRegForm.getStudentApplication().getHeAdmission(), "HEAdmission", "applym30p2", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));		
		if ("M".equals(stuRegForm.getStudentApplication().getHeAdmission()) || "S".equals(stuRegForm.getStudentApplication().getHeAdmission()) || "F".equals(stuRegForm.getStudentApplication().getHeAdmission())){
			if (stuRegForm.getMatric().getSchoolName() ==null || "".equals(stuRegForm.getMatric().getSchoolName().trim())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please fill in the school you attended."));
			addErrors(request, messages);
			return "m30step2";
			}
		}

		stuRegForm.getMatric().setSchoolCompleteYr(stripXSS(stuRegForm.getMatric().getSchoolCompleteYr(), "SchoolCompeteYear", "applym30p2", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));		
		if (stuRegForm.getMatric().getSchoolCompleteYr() ==null || "".equals(stuRegForm.getMatric().getSchoolCompleteYr().trim())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please fill in the year you completed your School Certificate."));
			addErrors(request, messages);
			return "m30step2";
		}
		stuRegForm.getMatric().setSchoolProvCountry(stripXSS(stuRegForm.getMatric().getSchoolProvCountry(), "SchoolProvCountry", "applym30p2", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));		
		if (stuRegForm.getMatric().getSchoolProvCountry() ==null || "".equals(stuRegForm.getMatric().getSchoolProvCountry().trim())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please fill in province or country where you completed School Certificate"));
			addErrors(request, messages);
			return "m30step2";
		}

		if (stuRegForm.getStudent().getAcademicYear() == null || stuRegForm.getStudent().getBirthYear() == null){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Invalid Academic year or Birth Date, please log on again and retry."));
			addErrors(request, messages);
			if (stuRegForm.getAdminStaff().isAdmin()){
				return "loginStaff";
			}else{
				return "loginStu";
			}
		}
		Integer ayr = Integer.parseInt(String.valueOf(stuRegForm.getStudent().getAcademicYear()));
		Integer byr = Integer.parseInt(String.valueOf(stuRegForm.getStudent().getBirthYear()));
		Integer dfr = ayr - byr;
		if (dfr >= 45 || "OO".equalsIgnoreCase(stuRegForm.getStudentApplication().getMatricCertificate()) || "O".equalsIgnoreCase(stuRegForm.getStudentApplication().getHeAdmission())){
			//not compulsory
		}else{
			stuRegForm.getMatric().setSubject1(stripXSS(stuRegForm.getMatric().getSubject1(), "MatricSubject1", "applym30p2", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
			stuRegForm.getMatric().setSubject2(stripXSS(stuRegForm.getMatric().getSubject2(), "MatricSubject2", "applym30p2", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
			stuRegForm.getMatric().setSubject3(stripXSS(stuRegForm.getMatric().getSubject3(), "MatricSubject3", "applym30p2", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
			stuRegForm.getMatric().setSubject4(stripXSS(stuRegForm.getMatric().getSubject4(), "MatricSubject4", "applym30p2", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
			
			stuRegForm.getMatric().setSubyear1(stripXSS(stuRegForm.getMatric().getSubyear1(), "MatricSubYear1", "applym30p2", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
			stuRegForm.getMatric().setSubyear2(stripXSS(stuRegForm.getMatric().getSubyear2(), "MatricSubYear2", "applym30p2", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
			stuRegForm.getMatric().setSubyear3(stripXSS(stuRegForm.getMatric().getSubyear3(), "MatricSubYear3", "applym30p2", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
			stuRegForm.getMatric().setSubyear4(stripXSS(stuRegForm.getMatric().getSubyear4(), "MatricSubYear4", "applym30p2", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));

			stuRegForm.getMatric().setSubmonth1(stripXSS(stuRegForm.getMatric().getSubmonth1(), "MatricSubMonth1", "applym30p2", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
			stuRegForm.getMatric().setSubmonth2(stripXSS(stuRegForm.getMatric().getSubmonth2(), "MatricSubMonth2", "applym30p2", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
			stuRegForm.getMatric().setSubmonth3(stripXSS(stuRegForm.getMatric().getSubmonth3(), "MatricSubMonth3", "applym30p2", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
			stuRegForm.getMatric().setSubmonth4(stripXSS(stuRegForm.getMatric().getSubmonth4(), "MatricSubMonth4", "applym30p2", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
			
			stuRegForm.getMatric().setSymbol1(stripXSS(stuRegForm.getMatric().getSymbol1(), "MatricSubSymbol1", "applym30p2", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
			stuRegForm.getMatric().setSymbol2(stripXSS(stuRegForm.getMatric().getSymbol2(), "MatricSubSymbol2", "applym30p2", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
			stuRegForm.getMatric().setSymbol3(stripXSS(stuRegForm.getMatric().getSymbol3(), "MatricSubSymbol3", "applym30p2", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
			stuRegForm.getMatric().setSymbol4(stripXSS(stuRegForm.getMatric().getSymbol4(), "MatricSubSymbol4", "applym30p2", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
			
			stuRegForm.getMatric().setSubgrade1(stripXSS(stuRegForm.getMatric().getSubgrade1(), "MatricSubGrade1", "applym30p2", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
			stuRegForm.getMatric().setSubgrade2(stripXSS(stuRegForm.getMatric().getSubgrade2(), "MatricSubGrade2", "applym30p2", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
			stuRegForm.getMatric().setSubgrade3(stripXSS(stuRegForm.getMatric().getSubgrade3(), "MatricSubGrade3", "applym30p2", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
			stuRegForm.getMatric().setSubgrade4(stripXSS(stuRegForm.getMatric().getSubgrade4(), "MatricSubGrade4", "applym30p2", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
			
			if ((stuRegForm.getMatric().getSubject1() ==null || "".equals(stuRegForm.getMatric().getSubject1().trim()))
					|| (stuRegForm.getMatric().getSubject2() ==null || "".equals(stuRegForm.getMatric().getSubject2().trim()))
					|| (stuRegForm.getMatric().getSubject3() ==null || "".equals(stuRegForm.getMatric().getSubject3().trim()))
					|| (stuRegForm.getMatric().getSubject4() ==null || "".equals(stuRegForm.getMatric().getSubject4().trim()))){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Please fill in at least four of your school subjects."));
				addErrors(request, messages);
				return "m30step2";
			}
			if ((stuRegForm.getMatric().getSubyear1() ==null || "".equals(stuRegForm.getMatric().getSubyear1().trim()))
					|| (stuRegForm.getMatric().getSubyear2() ==null || "".equals(stuRegForm.getMatric().getSubyear2().trim()))
					|| (stuRegForm.getMatric().getSubyear3() ==null || "".equals(stuRegForm.getMatric().getSubyear3().trim()))
					|| (stuRegForm.getMatric().getSubyear4() ==null || "".equals(stuRegForm.getMatric().getSubyear4().trim()))){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Please fill in the year/month/grade/level/symbol next to each subject"));
				addErrors(request, messages);
				return "m30step2";
			}
			if ((stuRegForm.getMatric().getSubmonth1() ==null || "".equals(stuRegForm.getMatric().getSubmonth1().trim()))
					|| (stuRegForm.getMatric().getSubmonth2() ==null || "".equals(stuRegForm.getMatric().getSubmonth2().trim()))
					|| (stuRegForm.getMatric().getSubmonth3() ==null || "".equals(stuRegForm.getMatric().getSubmonth3().trim()))
					|| (stuRegForm.getMatric().getSubmonth4() ==null || "".equals(stuRegForm.getMatric().getSubmonth4().trim()))){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Please fill in the year/month/grade/level/symbol next to each subject"));
				addErrors(request, messages);
				return "m30step2";
			}
			if ((stuRegForm.getMatric().getSymbol1() ==null || "".equals(stuRegForm.getMatric().getSymbol1().trim()))
					|| (stuRegForm.getMatric().getSymbol2() ==null || "".equals(stuRegForm.getMatric().getSymbol2().trim()))
					|| (stuRegForm.getMatric().getSymbol3() ==null || "".equals(stuRegForm.getMatric().getSymbol3().trim()))
					|| (stuRegForm.getMatric().getSymbol4() ==null || "".equals(stuRegForm.getMatric().getSymbol4().trim()))){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Please fill in the year/month/grade/level/symbol next to each subject"));
				addErrors(request, messages);
				return "m30step2";
			}
			if ((stuRegForm.getMatric().getSubgrade1() ==null || "".equals(stuRegForm.getMatric().getSubgrade1().trim()))
					|| (stuRegForm.getMatric().getSubgrade2() ==null || "".equals(stuRegForm.getMatric().getSubgrade2().trim()))
					|| (stuRegForm.getMatric().getSubgrade3() ==null || "".equals(stuRegForm.getMatric().getSubgrade3().trim()))
					|| (stuRegForm.getMatric().getSubgrade4() ==null || "".equals(stuRegForm.getMatric().getSubgrade4().trim()))){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Please fill in the year/month/grade/level/symbol next to each subject"));
				addErrors(request, messages);
				return "m30step2";
			}
		}

		// call from M30 page1 next = Submit page
		//log.debug("ApplyForStudentNumberAction - m30step2 - End");
		//log.debug("ApplyForStudentNumberAction - m30step2 - Goto applyNewDeclare");
		return "applyNewDeclare";
	}


	@SuppressWarnings("unused")
	public ActionForward applyNewDeclare(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		/*
		EventTrackingService eventTrackingService;
		ToolManager toolManager;
		
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
		toolManager = (ToolManager) ComponentManager.get(ToolManager.class);
		*/
		
		//log.debug("ApplyForStudentNumberAction - applyNewDeclare - Start");

		// if student number is null, block application
		ActionMessages messages = new ActionMessages();
		StudentRegistrationForm stuRegForm = (StudentRegistrationForm) form;
		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
		
		if(stuRegForm.getStudent().getNumber() == null || "".equals(stuRegForm.getStudent().getNumber())){
			//log.debug("ApplyForStudentNumberAction - applyNewDeclare - Student Nr is null or empty when saving qualifications");
			messages.add(ActionMessages.GLOBAL_MESSAGE,
			new ActionMessage("message.generalmessage", "You performed and invalid action or an Error occurred. Please log on again to retry."));
			addErrors(request, messages);
			if (stuRegForm.getAdminStaff().isAdmin()){
				return mapping.findForward("loginStaff");
			}else{
				return mapping.findForward("loginStu");
			}
		}

		if (stuRegForm.getFromPage() == null){
			//Error: Session empty
			//log.debug("ApplyForStudentNumberAction - applyNewDeclare - Empty Session");
			return mapping.findForward(emptySession(mapping,form,request,response));
		}

		// ---------- Check input

		stuRegForm.getStudentApplication().setLicensee("N");
		
		// rest of the Y/N fields

		stuRegForm.getStudent().setMediaAccess1("N");
		stuRegForm.getStudent().setMediaAccess2("N");
		stuRegForm.getStudent().setMediaAccess3("N");
		stuRegForm.getStudent().setMediaAccess4("N");
		stuRegForm.getStudent().setMediaAccess5("N");
		
		if (stuRegForm.getStudent().getMediaAccess() == null || "".equals(stuRegForm.getStudent().getMediaAccess().trim())){
			stuRegForm.getStudent().setMediaAccess(stuRegForm.getStudent().getMediaAccess1()+stuRegForm.getStudent().getMediaAccess2()+stuRegForm.getStudent().getMediaAccess3()+stuRegForm.getStudent().getMediaAccess4()+stuRegForm.getStudent().getMediaAccess5());
		}

		stuRegForm.getStudentApplication().setFinaidEduloan(stripXSS(stuRegForm.getStudentApplication().getFinaidEduloan(), "FinaidEduloan", "NewDeclare", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
		if (stuRegForm.getStudentApplication().getFinaidEduloan()==null || "".equals(stuRegForm.getStudentApplication().getFinaidEduloan().trim())){
			stuRegForm.getStudentApplication().setFinaidEduloan("N");
		}
		stuRegForm.getStudentApplication().setFinaidNsfas(stripXSS(stuRegForm.getStudentApplication().getFinaidNsfas(), "FinaidNsfas", "NewDeclare", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
		if (stuRegForm.getStudentApplication().getFinaidNsfas()==null || "".equals(stuRegForm.getStudentApplication().getFinaidNsfas().trim())){
			stuRegForm.getStudentApplication().setFinaidNsfas("N");
		}
		stuRegForm.getStudentApplication().setDocformAcadrec(stripXSS(stuRegForm.getStudentApplication().getDocformAcadrec(), "DocFormAcadRec", "NewDeclare", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
		if (stuRegForm.getStudentApplication().getDocformAcadrec()==null || "".equals(stuRegForm.getStudentApplication().getDocformAcadrec().trim())){
			stuRegForm.getStudentApplication().setDocformAcadrec("N");
		}
		stuRegForm.getStudentApplication().setDocformForm(stripXSS(stuRegForm.getStudentApplication().getDocformForm(), "DocFormForm", "NewDeclare", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
		if (stuRegForm.getStudentApplication().getDocformForm()==null || "".equals(stuRegForm.getStudentApplication().getDocformForm().trim())){
			stuRegForm.getStudentApplication().setDocformForm("N");
		}
		stuRegForm.getStudentApplication().setDocformIdentity(stripXSS(stuRegForm.getStudentApplication().getDocformIdentity(), "DocFormIdentity", "NewDeclare", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
		if (stuRegForm.getStudentApplication().getDocformIdentity()==null || "".equals(stuRegForm.getStudentApplication().getDocformIdentity().trim())){
			stuRegForm.getStudentApplication().setDocformIdentity("N");
		}
		stuRegForm.getStudentApplication().setDocformMarriage(stripXSS(stuRegForm.getStudentApplication().getDocformMarriage(), "DocFormMarriage", "NewDeclare", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
		if (stuRegForm.getStudentApplication().getDocformMarriage()==null || "".equals(stuRegForm.getStudentApplication().getDocformMarriage().trim())){
			stuRegForm.getStudentApplication().setDocformMarriage("N");
		}
		stuRegForm.getStudentApplication().setDocformSchool(stripXSS(stuRegForm.getStudentApplication().getDocformSchool(), "DocFormSchool", "NewDeclare", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
		if (stuRegForm.getStudentApplication().getDocformSchool()==null || "".equals(stuRegForm.getStudentApplication().getDocformSchool().trim())){
			stuRegForm.getStudentApplication().setDocformSchool("N");
		}
		stuRegForm.getStudentApplication().setDocformToefl(stripXSS(stuRegForm.getStudentApplication().getDocformToefl(), "DocFormTOEFL", "NewDeclare", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
		if (stuRegForm.getStudentApplication().getDocformToefl()==null || "".equals(stuRegForm.getStudentApplication().getDocformToefl().trim())){
			stuRegForm.getStudentApplication().setDocformToefl("N");
		}

			// agreement
		stuRegForm.setAgree(stripXSS(stuRegForm.getAgree(), "Declare", "NewDeclare", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
		if (stuRegForm.getAgree() == null || "".equals(stuRegForm.getAgree().trim())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Indicate your agreement to the declaration and undertaking."));
			addErrors(request, messages);
			return mapping.findForward("applyNewDeclare");
		}

		//test agreement flag
		if (!"Y".equalsIgnoreCase(stuRegForm.getAgree())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student has declined the Decleration agreement. Please log on again if you wish to retry."));
			addErrors(request, messages);
			return mapping.findForward(cancel(form));
		}

		/* Set submission time stamp */
		Date date = new java.util.Date();
		String displayDate = (new java.text.SimpleDateFormat("EEEEE dd MMMMM yyyy hh:mm:ss").format(date).toString());
		stuRegForm.getStudent().setAppTime(displayDate);
		request.setAttribute("email",stuRegForm.getStudent().getEmailAddress());

		//Create New Student Via F126 Proxy; 
		String errorMsg="";
		errorMsg = createStudentNr(stuRegForm, request);
		
		if(!"".equals(errorMsg) && !"True".equalsIgnoreCase(errorMsg)){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", errorMsg));
			addErrors(request, messages);
			if (errorMsg.toUpperCase().contains("COURIER")){
				return mapping.findForward("applyNewAddress3");
			}else if (errorMsg.toUpperCase().contains("PHYSICAL")){
				return mapping.findForward("applyNewAddress2");
			}else if (errorMsg.toUpperCase().contains("POSTAL")){
				setDropdownListsStep2(request,stuRegForm);
				return mapping.findForward("applyNewAddress1");
			}else if (errorMsg.toUpperCase().contains("CELL") || errorMsg.toUpperCase().contains("EMAIL")){
				return mapping.findForward("applyNewContact");
			}else if (errorMsg.toUpperCase().contains("EXAM")){
					return mapping.findForward("applyNewInfo2");
			}
			//return mapping.findForward("step3forward");
			//go back to page 1 - July 2010
			setDropdownListsStep1(request,stuRegForm);
			return mapping.findForward("applyNewPersonal");
		}else{
			
			//Save Previous Qualifications - Start
			int resultUpdate = 0;
			int resultSave = 0;
			//Save
			//log.debug("ApplyForStudentNumberAction - applyNewDeclare - studentNr=" + stuRegForm.getStudent().getNumber()+", Do Save/Debug");
			if (stuRegForm.getQualArray() != null && !stuRegForm.getQualArray().isEmpty()){
				//List now starts at 0;
				if (stuRegForm.getQualArray().size() > 0){
					HistoryArray other = new HistoryArray();
					for (int i=0; i < stuRegForm.getQualArray().size(); i++){
						other = (HistoryArray) stuRegForm.getQualArray().get(i);
						//Debug
						//log.debug("ApplyForStudentNumberAction - applyNewDeclare - studentNr=" + stuRegForm.getStudent().getNumber()+", Do Save/Debug - Found="+i+", historyOTHERSEQ="+other.getOtherSEQ());
						
						//log.debug("ApplyForStudentNumberAction - applyNewDeclare - studentNr=" + stuRegForm.getStudent().getNumber()+", Do Save/Debug - Found="+i+", historyOTHERUniv="+other.getOtherUniv());
						//log.debug("ApplyForStudentNumberAction - applyNewDeclare - studentNr=" + stuRegForm.getStudent().getNumber()+", Do Save/Debug - Found="+i+", historyOTHERUnivText="+other.getOtherUnivText());
						//log.debug("ApplyForStudentNumberAction - applyNewDeclare - studentNr=" + stuRegForm.getStudent().getNumber()+", Do Save/Debug - Found="+i+", historyOTHERStudnr="+other.getOtherStudnr());
						//log.debug("ApplyForStudentNumberAction - applyNewDeclare - studentNr=" + stuRegForm.getStudent().getNumber()+", Do Save/Debug - Found="+i+", historyOTHERQual="+other.getOtherQual());
						
						//log.debug("ApplyForStudentNumberAction - applyNewDeclare - studentNr=" + stuRegForm.getStudent().getNumber()+", Do Save/Debug - Found="+i+", historyOTHERYearStart="+other.getOtherYearStart());
						//log.debug("ApplyForStudentNumberAction - applyNewDeclare - studentNr=" + stuRegForm.getStudent().getNumber()+", Do Save/Debug - Found="+i+", historyOTHERYearEnd="+other.getOtherYearEnd());
						//log.debug("ApplyForStudentNumberAction - applyNewDeclare - studentNr=" + stuRegForm.getStudent().getNumber()+", Do Save/Debug - Found="+i+", historyOTHERCountry="+other.getOtherCountry());
						
						//log.debug("ApplyForStudentNumberAction - applyNewDeclare - studentNr=" + stuRegForm.getStudent().getNumber()+", Do Save/Debug - Found="+i+", historyOTHERLock="+other.getOtherLock());
						//log.debug("ApplyForStudentNumberAction - applyNewDeclare - studentNr=" + stuRegForm.getStudent().getNumber()+", Do Save/Debug - Found="+i+", historyOTHERForeign="+other.getOtherForeign());
						//log.debug("ApplyForStudentNumberAction - applyNewDeclare - studentNr=" + stuRegForm.getStudent().getNumber()+", Do Save/Debug - Found="+i+", historyOTHERComplete="+other.getOtherComplete());		
	
						//log.debug("ApplyForStudentNumberAction - applyNewDeclare - studentNr=" + stuRegForm.getStudent().getNumber()+", Do Save");
						//Save PrevQual
						boolean exists = dao.checkPREV(stuRegForm.getStudent().getNumber(), other.getOtherSEQ());
						if (exists){
							//Write STUXML Entry to save Sequence number
							String saveSTUXML =  dao.saveSTUXML(stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), "stuPrev", "1", other.getOtherSEQ(), "YES",  "stepPrevQual", "UPDATE");
							//Update only if Final year has changed and row is not locked
							if ("N".equalsIgnoreCase(other.getOtherLock())){
								resultUpdate = dao.updatePREVADM(stuRegForm.getStudent().getNumber(), other.getOtherSEQ(), other.getOtherYearEnd(), other.getOtherComplete());
								//log.debug("ApplyForStudentNumberAction - applyNewDeclare - studentNr=" + stuRegForm.getStudent().getNumber()+", RecordNo="+i+", resultUpdate="+resultUpdate);
							}
						}else{
							// Save to database if there are any Qualifications added
							int maxPrev = 0;
							maxPrev = dao.getSTUPREVMax(stuRegForm.getStudent().getNumber());
							//log.debug("ApplyForStudentNumberAction - applyNewDeclare - studentNr=" + stuRegForm.getStudent().getNumber()+", RecordNo="+i+", maxPrev="+maxPrev);
							maxPrev++;
							//log.debug("ApplyForStudentNumberAction - applyNewDeclare - studentNr=" + stuRegForm.getStudent().getNumber()+", RecordNo="+i+", maxPrev Plus 1="+maxPrev);
	
							//Write STUXML Entry to save Sequence number
							String saveSTUXML =  dao.saveSTUXML(stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), "stuPrev", "1", other.getOtherSEQ(), "YES",  "stepPrevQual", "INSERT");
								resultSave = dao.savePREVADM(stuRegForm.getStudent().getNumber(), maxPrev, other.getOtherYearStart(),
									other.getOtherYearEnd(), other.getOtherUnivText(), 
									other.getOtherStudnr(), other.getOtherQual(), other.getOtherForeign(), 
									other.getOtherComplete(), other.getOtherUniv(), other.getOtherCountry());
							//log.debug("ApplyForStudentNumberAction - applyNewDeclare - studentNr=" + stuRegForm.getStudent().getNumber()+", RecordNo="+i+", saveSTUXML="+saveSTUXML+", resultSave="+resultSave);
						}
						
					}
					//log.debug("ApplyForStudentNumberAction - applyNewDeclare - studentNr=" + stuRegForm.getStudent().getNumber()+", Save Done");
				}else{
					//log.debug("ApplyForStudentNumberAction - applyNewDeclare - studentNr=" + stuRegForm.getStudent().getNumber()+", No Qualifications Entered or found!");
				}
				//Save Previous Qualifications - End
			}
			
			//Write NDP Modules if any
			//Check if NDP Modules exist. If not, insert them.
			//log.debug("ApplyForStudentNumberAction - applyNewDeclare -  Check if NDP Modules");
			if ("Y".equals(stuRegForm.getStudentApplication().getRadioNDP())){
				//log.debug("ApplyForStudentNumberAction - applyNewDeclare - Save NDP Modules");
				if ((stuRegForm.getStudentApplication().getNdpRegSu1() != null && !"".equals(stuRegForm.getStudentApplication().getNdpRegSu1())) && ("00019".equals(stuRegForm.getStudent().getQual1()) || "00019".equals(stuRegForm.getStudent().getQual2()))) {
					boolean isNDP1 = dao.getNDPMod(stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getStudent().getNumber(), "00019", stuRegForm.getStudentApplication().getNdpRegSu1());
				
					if (!isNDP1){
						dao.saveNDPMod(stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getStudent().getNumber(), "00019", stuRegForm.getStudentApplication().getNdpRegSu1());
						//log.debug("ApplyForStudentNumberAction - applyNewDeclare - Saving NDP Module (1) = "+stuRegForm.getStudentApplication().getNdpRegSu1());
					}
				}
				if ((stuRegForm.getStudentApplication().getNdpRegSu2() != null && !"".equals(stuRegForm.getStudentApplication().getNdpRegSu2())) && ("00019".equals(stuRegForm.getStudent().getQual1()) || "00019".equals(stuRegForm.getStudent().getQual2()))) {
					boolean isNDP2 = dao.getNDPMod(stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getStudent().getNumber(), "00019", stuRegForm.getStudentApplication().getNdpRegSu2());
				
					if (!isNDP2){
						dao.saveNDPMod(stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getStudent().getNumber(), "00019", stuRegForm.getStudentApplication().getNdpRegSu2());
						//log.debug("ApplyForStudentNumberAction - applyNewDeclare - Saving NDP Module (2) = "+stuRegForm.getStudentApplication().getNdpRegSu2());
					}
				}
				if ((stuRegForm.getStudentApplication().getNdpRegSu3() != null && !"".equals(stuRegForm.getStudentApplication().getNdpRegSu3())) && ("00019".equals(stuRegForm.getStudent().getQual1()) || "00019".equals(stuRegForm.getStudent().getQual2()))) {
					boolean isNDP3 = dao.getNDPMod(stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getStudent().getNumber(), "00019", stuRegForm.getStudentApplication().getNdpRegSu3());
				
					if (!isNDP3){
						dao.saveNDPMod(stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getStudent().getNumber(), "00019", stuRegForm.getStudentApplication().getNdpRegSu3());
						//log.debug("ApplyForStudentNumberAction - applyNewDeclare - Saving NDP Module (3) = "+stuRegForm.getStudentApplication().getNdpRegSu3());
					}
				}
				if ((stuRegForm.getStudentApplication().getNdpRegSu4() != null && !"".equals(stuRegForm.getStudentApplication().getNdpRegSu4())) && ("00019".equals(stuRegForm.getStudent().getQual1()) || "00019".equals(stuRegForm.getStudent().getQual2()))) {
					boolean isNDP4 = dao.getNDPMod(stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getStudent().getNumber(), "00019", stuRegForm.getStudentApplication().getNdpRegSu4());
				
					if (!isNDP4){
						dao.saveNDPMod(stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getStudent().getNumber(), "00019", stuRegForm.getStudentApplication().getNdpRegSu4());
						//log.debug("ApplyForStudentNumberAction - applyNewDeclare - Saving NDP Module (4) = "+stuRegForm.getStudentApplication().getNdpRegSu4());
					}
				}
				if ((stuRegForm.getStudentApplication().getNdpRegSu5() != null && !"".equals(stuRegForm.getStudentApplication().getNdpRegSu5())) && ("00019".equals(stuRegForm.getStudent().getQual1()) || "00019".equals(stuRegForm.getStudent().getQual2()))) {
					boolean isNDP5 = dao.getNDPMod(stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getStudent().getNumber(), "00019", stuRegForm.getStudentApplication().getNdpRegSu5());
				
					if (!isNDP5){
						dao.saveNDPMod(stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getStudent().getNumber(), "00019", stuRegForm.getStudentApplication().getNdpRegSu5());
						//log.debug("ApplyForStudentNumberAction - applyNewDeclare - Saving NDP Module (5) = "+stuRegForm.getStudentApplication().getNdpRegSu5());
					}
				}if ((stuRegForm.getStudentApplication().getNdpRegSu6() != null && !"".equals(stuRegForm.getStudentApplication().getNdpRegSu6())) && ("00019".equals(stuRegForm.getStudent().getQual1()) || "00019".equals(stuRegForm.getStudent().getQual2()))) {
					boolean isNDP6 = dao.getNDPMod(stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getStudent().getNumber(), "00019", stuRegForm.getStudentApplication().getNdpRegSu6());
				
					if (!isNDP6){
						dao.saveNDPMod(stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getStudent().getNumber(), "00019", stuRegForm.getStudentApplication().getNdpRegSu6());
						//log.debug("ApplyForStudentNumberAction - applyNewDeclare - Saving NDP Module (6) = "+stuRegForm.getStudentApplication().getNdpRegSu6());
					}
				}
				if ((stuRegForm.getStudentApplication().getNdpRegSu7() != null && !"".equals(stuRegForm.getStudentApplication().getNdpRegSu7())) && ("00019".equals(stuRegForm.getStudent().getQual1()) || "00019".equals(stuRegForm.getStudent().getQual2()))) {
					boolean isNDP7 = dao.getNDPMod(stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getStudent().getNumber(), "00019", stuRegForm.getStudentApplication().getNdpRegSu7());
				
					if (!isNDP7){
						dao.saveNDPMod(stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getStudent().getNumber(), "00019", stuRegForm.getStudentApplication().getNdpRegSu7());
						//log.debug("ApplyForStudentNumberAction - applyNewDeclare - Saving NDP Module (7) = "+stuRegForm.getStudentApplication().getNdpRegSu7());
					}
				}
				if ((stuRegForm.getStudentApplication().getNdpRegSu8() != null && !"".equals(stuRegForm.getStudentApplication().getNdpRegSu8())) && ("00019".equals(stuRegForm.getStudent().getQual1()) || "00019".equals(stuRegForm.getStudent().getQual2()))) {
					boolean isNDP8 = dao.getNDPMod(stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getStudent().getNumber(), "00019", stuRegForm.getStudentApplication().getNdpRegSu8());
				
					if (!isNDP8){
						dao.saveNDPMod(stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getStudent().getNumber(), "00019", stuRegForm.getStudentApplication().getNdpRegSu8());
						//log.debug("ApplyForStudentNumberAction - applyNewDeclare - Saving NDP Module (8) = "+stuRegForm.getStudentApplication().getNdpRegSu8());
					}
				}
				if ((stuRegForm.getStudentApplication().getNdpRegSu9() != null && !"".equals(stuRegForm.getStudentApplication().getNdpRegSu9())) && ("00019".equals(stuRegForm.getStudent().getQual1()) || "00019".equals(stuRegForm.getStudent().getQual2()))) {
					boolean isNDP9 = dao.getNDPMod(stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getStudent().getNumber(), "00019", stuRegForm.getStudentApplication().getNdpRegSu9());
				
					if (!isNDP9){
						dao.saveNDPMod(stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getStudent().getNumber(), "00019", stuRegForm.getStudentApplication().getNdpRegSu9());
						//log.debug("ApplyForStudentNumberAction - applyNewDeclare - Saving NDP Module (9) = "+stuRegForm.getStudentApplication().getNdpRegSu9());
					}
				}
				if ((stuRegForm.getStudentApplication().getNdpRegSu10() != null && !"".equals(stuRegForm.getStudentApplication().getNdpRegSu10())) && ("00019".equals(stuRegForm.getStudent().getQual1()) || "00019".equals(stuRegForm.getStudent().getQual2()))) {
					boolean isNDP10 = dao.getNDPMod(stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getStudent().getNumber(), "00019", stuRegForm.getStudentApplication().getNdpRegSu10());
				
					if (!isNDP10){
						dao.saveNDPMod(stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getStudent().getNumber(), "00019", stuRegForm.getStudentApplication().getNdpRegSu10());
						//log.debug("ApplyForStudentNumberAction - applyNewDeclare - Saving NDP Module (10) = "+stuRegForm.getStudentApplication().getNdpRegSu10());
					}
				}
			}else{
				//log.debug("ApplyForStudentNumberAction - applyNewDeclare - NDP = No, no Modules to save");
			}
			writeWorkflow(stuRegForm,date);
		}

		// Write sakai event
		/*
		try{
			eventTrackingService.post(
					eventTrackingService.newEvent(EventTrackingTypes.EVENT_STUDENTREGISTRATION_APPLYFORSTUDENTNR, toolManager.getCurrentPlacement().getContext(), false));
        }catch(Exception e) {
        	log.warn("ApplyForStudentNumber applyNewDeclare: Error writing to Sakai EventTracker for studnr="+stuRegForm.getStudent().getNumber() + " - Continuing");
        }
        */
    	// clear form variables
	    //resetForm(stuRegForm);
		
		//Update STUXML and STULOG with official Student number as it only contains Random Temp Number - Assists in matching records in future and reporting
		//2018 Edmund - I have commented the below two lines out for now, could be implemented later
		//int resultXML = dao.updateSTUXML(stuRegForm.getStudent().getNumberTmp(), stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod());
		//int resultLOG = dao.updateSTULOG(stuRegForm.getStudent().getNumberTmp(), stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod());
		
		if ("MD".equalsIgnoreCase(stuRegForm.getLoginSelectMain())){
			
			//log.debug("ApplyForStudentNumberAction - applyNewDeclare - moveDocuments for MD as now upload page: " + stuRegForm.getStudent().getNumber());
			 
			moveDocuments(stuRegForm.getStudent().getNumber());
			 
			//2018 Edmund - Add code so student cannot submit more than once, even if an error occurs.
			//stuRegForm.setDoneSubmit(true);
			
			//log.debug("ApplyForStudentNumberAction - applyNewDeclare - End");
			//log.debug("ApplyForStudentNumberAction - applyNewDeclare - Goto submitNewMD");
			return mapping.findForward("submitNewMD"); 
		}else{
			//2018 Edmund - Add code so student cannot submit more than once, even if an error occurs.
			//stuRegForm.setDoneSubmit(true);
			
			//log.debug("ApplyForStudentNumberAction - applyNewDeclare - End");
			//log.debug("ApplyForStudentNumberAction - applyNewDeclare - Goto dynamicUpload");
			return mapping.findForward("dynamicUpload");
		}
	}

	/**
	 * This method writes the application for student number workflow file to the
	 * path specified in the following file:
	 * sakai/jakarta-tomcat-5.5.9/sakai/sakai.properties
	 *
	 * @param stuRegForm	       The form associated with the action
	 * @param applyDateTime		   The date and time the application was submitted
	*/
	@SuppressWarnings("unused")
	private void writeWorkflow(StudentRegistrationForm stuRegForm, Date applyDateTime) throws Exception{

		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
		
		// qualification code
		String newQual1 = dao.getXMLSelected("qualCode1", "1",stuRegForm.getStudent().getNumberTmp(),stuRegForm.getStudent().getAcademicYear(),stuRegForm.getStudent().getAcademicPeriod(), "writeWorkflow");
		String newQual2 = dao.getXMLSelected("qualCode2", "2",stuRegForm.getStudent().getNumberTmp(),stuRegForm.getStudent().getAcademicYear(),stuRegForm.getStudent().getAcademicPeriod(), "writeWorkflow");
		
		String newSpec1 = dao.getXMLSelected("specCode1", "1",stuRegForm.getStudent().getNumberTmp(),stuRegForm.getStudent().getAcademicYear(),stuRegForm.getStudent().getAcademicPeriod(), "writeWorkflow");
		String newSpec2 = dao.getXMLSelected("specCode2", "2",stuRegForm.getStudent().getNumberTmp(),stuRegForm.getStudent().getAcademicYear(),stuRegForm.getStudent().getAcademicPeriod(), "writeWorkflow");

		if (stuRegForm.getStudent().getQual1() == null || newQual1 == null || stuRegForm.getStudent().getSpec1() == null || newSpec1 == null || stuRegForm.getStudent().getQual2() == null || newQual2 == null || stuRegForm.getStudent().getSpec2() == null || newSpec2 == null){
			//log.debug("ApplyForStudentNumberAction - writeWorkflow Student Has Null Entry Student="+stuRegForm.getStudent().getNumber()+", Qual1=" + stuRegForm.getStudent().getQual1() +", XMLQual1="+newQual1+", Qual2=" + stuRegForm.getStudent().getQual2() +", XMLQual2="+newQual2+", Spec1=" + stuRegForm.getStudent().getSpec1() +", XMLSpec1="+newSpec1+", Spec2=" + stuRegForm.getStudent().getSpec2() +", XMLSpec2="+newSpec2);
		}
		
		//log.debug("ApplyForStudentNumberAction - writeWorkflow - get Action newQual1: " + newQual1);
		//log.debug("ApplyForStudentNumberAction - writeWorkflow - get Action newSpec1: " + newSpec1);
		
		//log.debug("ApplyForStudentNumberAction - writeWorkflow - get Action newQual2: " + newQual2);
		//log.debug("ApplyForStudentNumberAction - writeWorkflow - get Action newSpec2: " + newSpec2);
		
		String newQual1Desc = dao.getQualDesc(newQual1);
		String newQual2Desc = "";

		if (newSpec1 == null || "".equals(newSpec1) || "0".equals(newSpec1) || "undefined".equalsIgnoreCase(newSpec1)){
			newSpec1 = " ";
		}
		
		if (newQual2 != null && !"".equals(newQual2) && !"0".equals(newQual2) && !"undefined".equalsIgnoreCase(newQual2)){
			newQual2Desc = dao.getQualDesc(newQual2);
		}else{
			newQual2 = " ";
		}

		if (newSpec2 == null  || "".equals(newSpec2) || "0".equals(newSpec2) || "undefined".equalsIgnoreCase(newSpec2)){
			newSpec2 = " ";
		}

		//log.debug("ApplyForStudentNumberAction - writeWorkflow - get Action newQual1Desc: " + newQual1Desc);
		//log.debug("ApplyForStudentNumberAction - writeWorkflow - get Action newQual2Desc: " + newQual2Desc);
				
		String type = "L"; /* L = local F=Foreign */
		String wflType = "APP";

		/* set local or foreign */
		if (!"1015".equals(stuRegForm.getStudent().getCountry().getCode())){
			type = "F";
		}

		/* write to file */
		WorkflowTempFile file = new WorkflowTempFile("NewappSN_"+ type + "_"+ stuRegForm.getStudent().getNumber(),wflType);
		file.add(" Subject: Application for a Student Number \r\n");
		String displayDate = (new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(applyDateTime).toString());
		file.add(" Date received: " + displayDate + "\r\n");
		file.add(" The following request was received through the Unisa Web Server: \r\n");
		file.add(" --------------------------------------------------------------------------\r\n");
		file.add(" Details: \r\n");
		file.add(" Student Number              = " + stuRegForm.getStudent().getNumber() + "\r\n");
		file.add(" Surname/Last Name           = " + stuRegForm.getStudent().getSurname() + "\r\n");
		file.add(" Initials                    = " + stuRegForm.getStudent().getInitials() + "\r\n");
		file.add(" Title                       = " + stuRegForm.getStudent().getTitle() + "\r\n");
		file.add(" First name(s)/Forename(s)   = " + stuRegForm.getStudent().getFirstnames() + "\r\n");
		file.add(" Maiden name / \r\n");
		file.add(" Previous surname            = " + stuRegForm.getStudent().getMaidenName() + "\r\n");
		file.add(" Date of birth               = Year: " + stuRegForm.getStudent().getBirthYear()+ " Month: "+ stuRegForm.getStudent().getBirthMonth() +" Day: "+ stuRegForm.getStudent().getBirthDay()+"\r\n");
		file.add(" Gender (M/F)                = " + stuRegForm.getStudent().getGender() + "\r\n");
		file.add(" --------------------------------------------------------------------------\r\n");
		if("R".equalsIgnoreCase(stuRegForm.getStudent().getIdType())){
		file.add(" RSA identity number         = " + stuRegForm.getStudent().getIdNumber() + "\r\n");
		}else if("P".equalsIgnoreCase(stuRegForm.getStudent().getIdType())){
	    file.add(" Foreign ID/Passport number  = " + stuRegForm.getStudent().getPassportNumber()+ "\r\n");
		}else if("F".equalsIgnoreCase(stuRegForm.getStudent().getIdType())){
	    file.add(" Foreign ID/Passport number  = " + stuRegForm.getStudent().getForeignIdNumber()+ "\r\n");
		}
		file.add(" --------------------------------------------------------------------------\r\n");
		file.add(" Disability code             = " + stuRegForm.getStudent().getDisability().getCode()+ " "+ stuRegForm.getStudent().getDisability().getDesc() + "\r\n");
		file.add(" --------------------------------------------------------------------------\r\n");
		file.add(" Home telephone number       = " + stuRegForm.getStudent().getHomePhone()+ "\r\n");
		file.add(" Work telephone number       = " + stuRegForm.getStudent().getWorkPhone() + "\r\n");
		file.add(" Cell number                 = " + stuRegForm.getStudent().getCellNr() + "\r\n");
		file.add(" Fax number                  = " + stuRegForm.getStudent().getFaxNr() + "\r\n");
		file.add(" E-mail address              = " + stuRegForm.getStudent().getEmailAddress() + "\r\n");
		file.add(" Contact number              = " + stuRegForm.getStudent().getContactNr() + "\r\n");
		file.add(" --------------------------------------------------------------------------\r\n");
		file.add(" Postal address              = " + stuRegForm.getStudent().getPostalAddress().getLine1()+ "\r\n");
		if(stuRegForm.getStudent().getPostalAddress().getLine2()!=null && !"".equals(stuRegForm.getStudent().getPostalAddress().getLine2())){
			file.add("                             = " + stuRegForm.getStudent().getPostalAddress().getLine2()+ "\r\n");
		}
		if(stuRegForm.getStudent().getPostalAddress().getLine3()!=null && !"".equals(stuRegForm.getStudent().getPostalAddress().getLine3())){
			file.add("                             = " + stuRegForm.getStudent().getPostalAddress().getLine3()+ "\r\n");
		}
		if(stuRegForm.getStudent().getPostalAddress().getLine4()!=null && !"".equals(stuRegForm.getStudent().getPostalAddress().getLine4())){
			file.add("                             = " + stuRegForm.getStudent().getPostalAddress().getLine4()+ "\r\n");
		}
		if(stuRegForm.getStudent().getPostalAddress().getLine5()!=null && !"".equals(stuRegForm.getStudent().getPostalAddress().getLine5())){
			file.add("                             = " + stuRegForm.getStudent().getPostalAddress().getLine5()+ "\r\n");
		}
		if(stuRegForm.getStudent().getPostalAddress().getLine6()!=null && !"".equals(stuRegForm.getStudent().getPostalAddress().getLine6())){
			file.add("                             = " + stuRegForm.getStudent().getPostalAddress().getLine6()+ "\r\n");
		}
		file.add(" Postal code                 = " + stuRegForm.getStudent().getPostalAddress().getAreaCode() + "\r\n");
		file.add(" Physical address            = " + stuRegForm.getStudent().getPhysicalAddress().getLine1() + "\r\n");
		if(stuRegForm.getStudent().getPhysicalAddress().getLine2()!=null && !"".equals(stuRegForm.getStudent().getPhysicalAddress().getLine2())){
			file.add("                             = " + stuRegForm.getStudent().getPhysicalAddress().getLine2()+ "\r\n");
		}
		if(stuRegForm.getStudent().getPhysicalAddress().getLine3()!=null && !"".equals(stuRegForm.getStudent().getPhysicalAddress().getLine3())){
			file.add("                             = " + stuRegForm.getStudent().getPhysicalAddress().getLine3()+ "\r\n");
		}
		if(stuRegForm.getStudent().getPhysicalAddress().getLine4()!=null && !"".equals(stuRegForm.getStudent().getPhysicalAddress().getLine4())){
			file.add("                             = " + stuRegForm.getStudent().getPhysicalAddress().getLine4()+ "\r\n");
		}
		if(stuRegForm.getStudent().getPhysicalAddress().getLine5()!=null && !"".equals(stuRegForm.getStudent().getPhysicalAddress().getLine5())){
			file.add("                             = " + stuRegForm.getStudent().getPhysicalAddress().getLine5()+ "\r\n");
		}
		if(stuRegForm.getStudent().getPhysicalAddress().getLine6()!=null && !"".equals(stuRegForm.getStudent().getPhysicalAddress().getLine6())){
			file.add("                             = " + stuRegForm.getStudent().getPhysicalAddress().getLine6()+ "\r\n");
		}
		file.add(" Physical addr postal code   = " + stuRegForm.getStudent().getPhysicalAddress().getAreaCode() + "\r\n");
		file.add(" Courier address             = " + stuRegForm.getStudent().getCourierAddress().getLine1() + "\r\n");
		if(stuRegForm.getStudent().getCourierAddress().getLine2()!=null && !"".equals(stuRegForm.getStudent().getCourierAddress().getLine2())){
			file.add("                             = " + stuRegForm.getStudent().getCourierAddress().getLine2()+ "\r\n");
		}
		if(stuRegForm.getStudent().getCourierAddress().getLine3()!=null && !"".equals(stuRegForm.getStudent().getCourierAddress().getLine3())){
			file.add("                             = " + stuRegForm.getStudent().getCourierAddress().getLine3()+ "\r\n");
		}
		if(stuRegForm.getStudent().getCourierAddress().getLine4()!=null && !"".equals(stuRegForm.getStudent().getCourierAddress().getLine4())){
			file.add("                             = " + stuRegForm.getStudent().getCourierAddress().getLine4()+ "\r\n");
		}
		if(stuRegForm.getStudent().getCourierAddress().getLine5()!=null && !"".equals(stuRegForm.getStudent().getCourierAddress().getLine5())){
			file.add("                             = " + stuRegForm.getStudent().getCourierAddress().getLine5()+ "\r\n");
		}
		if(stuRegForm.getStudent().getCourierAddress().getLine6()!=null && !"".equals(stuRegForm.getStudent().getCourierAddress().getLine6())){
			file.add("                             = " + stuRegForm.getStudent().getCourierAddress().getLine6()+ "\r\n");
		}
		file.add(" Courier addr postal code    = " + stuRegForm.getStudent().getCourierAddress().getAreaCode() + "\r\n");

		file.add(" Country                     = " + stuRegForm.getStudent().getCountry().getCode()+ " "+ stuRegForm.getStudent().getCountry().getDesc() + "\r\n");
		file.add(" --------------------------------------------------------------------------\r\n");
		/*file.add(" Fellow students (Y/N)       = " + stuRegForm.getStudent().getShareContactDetails() + "\r\n");*/
		file.add(" Examination centre          = " + stuRegForm.getStudent().getExam().getExamCentre().getCode()+ " "+ stuRegForm.getStudent().getExam().getExamCentre().getDesc() + "\r\n");
		file.add(" Home language               = " + stuRegForm.getStudent().getHomeLanguage().getCode() + " "+ stuRegForm.getStudent().getHomeLanguage().getDesc() + "\r\n");
		file.add(" Nationality                 = " + stuRegForm.getStudent().getNationalty().getCode()+ " "+ stuRegForm.getStudent().getNationalty().getDesc() + "\r\n");
		file.add(" Population group            = " + stuRegForm.getStudent().getPopulationGroup().getCode()+ " "+ stuRegForm.getStudent().getPopulationGroup().getDesc() + "\r\n");
		file.add(" Occupation                  = " + stuRegForm.getStudent().getOccupation().getCode()+ " "+ stuRegForm.getStudent().getOccupation().getDesc() + "\r\n");
		file.add(" Economic sector             = " + stuRegForm.getStudent().getEconomicSector().getCode()+ " "+ stuRegForm.getStudent().getEconomicSector().getDesc() + "\r\n");
		file.add(" Previous economic activity  = " + stuRegForm.getStudent().getPrevActivity().getCode()+ " "+ stuRegForm.getStudent().getPrevActivity().getDesc() + "\r\n");
		file.add(" --------------------------------------------------------------------------\r\n");
		file.add(" Concurrent institution      = " + stuRegForm.getStudent().getOtherUniversity().getCode()+ " "+ stuRegForm.getStudent().getOtherUniversity().getDesc() + "\r\n");
		file.add(" Previous institution        = " + stuRegForm.getStudent().getPrevInstitution().getCode()+ "\r\n");
		file.add(" Last year of registration   = " + stuRegForm.getStudent().getLastRegYear() + "\r\n");
		if ("P".equalsIgnoreCase(stuRegForm.getStudent().getLastStatus())){
			file.add(" Previous postgraduate (Y/N) = Y \r\n");
		}else if ("U".equalsIgnoreCase(stuRegForm.getStudent().getLastStatus())){
			file.add(" Previous postgraduate (Y/N) = N \r\n");
		}else{
			file.add(" Previous postgraduate (Y/N) =  \r\n");
		}
		file.add(" --------------------------------------------------------------------------\r\n");
		file.add(" Proposed qualification      = " + newQual1+" "+ newQual1Desc+ "\r\n");
		file.add(" Specialisation code         = " + newSpec1 + "\r\n");
		file.add(" Second choice qualification = " + newQual2+" "+ newQual2Desc+ "\r\n");
		file.add(" Second specialisation code  = " + newSpec2 + "\r\n");

		//applyPrevQual Info
		HistoryUnisa unisa = new HistoryUnisa();
		if (stuRegForm.getQualUnisa() != null && !stuRegForm.getQualUnisa().isEmpty()){
			//log.debug("ApplyForQualChange: Workflow for studnr="+stuRegForm.getStudent().getNumber() + " Unisa Qualifications is not Empty="+stuRegForm.getQualUnisa().size());

			if (stuRegForm.getQualUnisa().size() > 0){
				file.add(" --------------------------------------------------------------------------\r\n");
				file.add(" Previous academic history  :  UNISA \r\n");
				for (int i=0; i < stuRegForm.getQualUnisa().size(); i++){
					unisa = (HistoryUnisa)stuRegForm.getQualUnisa().get(i);
					file.add("   Qualification "+i+"   = " + unisa.getHistQual() + "\r\n");
					file.add("   First registration yr = " + unisa.getHistYear() + "\r\n");
					file.add("   Complete yr           = " + unisa.getHistComplete() + "\r\n");
					file.add("   --------------------- \r\n");
				}
			}
		}else{
			//log.debug("ApplyForQualChange: Workflow for studnr="+stuRegForm.getStudent().getNumber() + " Other Qualifications is Empty");
		}


		HistoryArray other = new HistoryArray();
		if (stuRegForm.getQualArray() != null && !stuRegForm.getQualArray().isEmpty()){
			//log.debug("ApplyForQualChange: Workflow for studnr="+stuRegForm.getStudent().getNumber() + " Other Qualifications is not Empty="+stuRegForm.getQualArray().size());

			
			if (stuRegForm.getQualArray().size() > 0){
				file.add(" --------------------------------------------------------------------------\r\n");
				file.add(" Previous academic history  :  OTHER \r\n");
				for (int i=0; i < stuRegForm.getQualArray().size(); i++){
					other = (HistoryArray)stuRegForm.getQualArray().get(i);
					file.add("   Qualification "+i+"   = " + other.getOtherSEQ() + "\r\n");
					file.add("   InstitutionCode       = " + other.getOtherUniv() + "\r\n");
					file.add("   InstitutionOther      = " + other.getOtherUnivText() + "\r\n");
					file.add("   Student nr            = " + other.getOtherStudnr() + "\r\n");
					file.add("   Qualification         = " + other.getOtherQual() + "\r\n");
					file.add("   First registration yr = " + other.getOtherYearStart() + "\r\n");
					file.add("   Last Registration yr  = " + other.getOtherYearEnd() + "\r\n");
					file.add("   Completed             = " + other.getOtherComplete() + "\r\n");
					file.add("   Country               = " + other.getOtherCountry() + "\r\n");
					file.add("   Foreign               = " + other.getOtherForeign() + "\r\n");
					file.add("   --------------------- \r\n");
				}
			}
			
		}else{
			//log.debug("ApplyForQualChange: Workflow for studnr="+stuRegForm.getStudent().getNumber() + " Other Qualifications is Empty");
		}
	
		if (stuRegForm.getSubjects() != null && !stuRegForm.getSubjects().isEmpty()){
			//log.debug("ApplyForQualChange: Workflow for studnr="+stuRegForm.getStudent().getNumber() + " Subjects not Empty="+stuRegForm.getSubjects().size());
			Subject sub = new Subject();
			if (stuRegForm.getSubjects().size() > 0){
				file.add(" --------------------------------------------------------------------------\r\n");
				file.add(" APS Matric Infomation  \r\n");
				for (int i = 0; i < stuRegForm.getSubjects().size(); i++){
					sub = stuRegForm.getSubjects().get(i);
					file.add("   Subject "+i+"         = " + sub.getSubjectName() + "\r\n");
					file.add("   Symbol                = " + sub.getSubjectSymbol() + "\r\n");
					file.add("   Result                = " + sub.getSubjectResult() + "\r\n");
					file.add("   --------------------- \r\n");
				}
			}
		}else{
			//log.debug("ApplyForQualChange: Workflow for studnr="+stuRegForm.getStudent().getNumber() + " Subjects is Empty");
		}
		
		//NDP Modules
		file.add(" --------------------------------------------------------------------------\r\n");
		file.add(" Non Degree Purposes  \r\n");
		file.add("                         \r\n");
		file.add("   NDP Flag              = " + stuRegForm.getStudentApplication().getRadioNDP() + "\r\n");
		file.add("   --------------------- \r\n");
		
		if (stuRegForm.getStudentApplication().getNdpRegSu1() != null && !"".equals(stuRegForm.getStudentApplication().getNdpRegSu1())){
			file.add("   StudyUnit/Module  \r\n");
			file.add("   --------------------- \r\n");
			file.add("   Study Unit 1      = " + stuRegForm.getStudentApplication().getNdpRegSu1() + "\r\n");
		}
		if (stuRegForm.getStudentApplication().getNdpRegSu2() != null && !"".equals(stuRegForm.getStudentApplication().getNdpRegSu2())){
			file.add("   Study Unit 2      = " + stuRegForm.getStudentApplication().getNdpRegSu2() + "\r\n");
		}
		if (stuRegForm.getStudentApplication().getNdpRegSu3() != null && !"".equals(stuRegForm.getStudentApplication().getNdpRegSu3())){
			file.add("   Study Unit 3      = " + stuRegForm.getStudentApplication().getNdpRegSu3() + "\r\n");
		}
		if (stuRegForm.getStudentApplication().getNdpRegSu4() != null && !"".equals(stuRegForm.getStudentApplication().getNdpRegSu4())){
			file.add("   Study Unit 4      = " + stuRegForm.getStudentApplication().getNdpRegSu4() + "\r\n");
		}
		if (stuRegForm.getStudentApplication().getNdpRegSu5() != null && !"".equals(stuRegForm.getStudentApplication().getNdpRegSu5())){
			file.add("   Study Unit 5      = " + stuRegForm.getStudentApplication().getNdpRegSu5() + "\r\n");
		}
		if (stuRegForm.getStudentApplication().getNdpRegSu6() != null && !"".equals(stuRegForm.getStudentApplication().getNdpRegSu6())){
			file.add("   Study Unit 6      = " + stuRegForm.getStudentApplication().getNdpRegSu6() + "\r\n");
		}
		if (stuRegForm.getStudentApplication().getNdpRegSu7() != null && !"".equals(stuRegForm.getStudentApplication().getNdpRegSu7())){
			file.add("   Study Unit 7      = " + stuRegForm.getStudentApplication().getNdpRegSu7() + "\r\n");
		}
		if (stuRegForm.getStudentApplication().getNdpRegSu8() != null && !"".equals(stuRegForm.getStudentApplication().getNdpRegSu8())){
			file.add("   Study Unit 8      = " + stuRegForm.getStudentApplication().getNdpRegSu8() + "\r\n");
		}
		if (stuRegForm.getStudentApplication().getNdpRegSu9() != null && !"".equals(stuRegForm.getStudentApplication().getNdpRegSu9())){
			file.add("   Study Unit 9      = " + stuRegForm.getStudentApplication().getNdpRegSu9() + "\r\n");
		}
		if (stuRegForm.getStudentApplication().getNdpRegSu10() != null && !"".equals(stuRegForm.getStudentApplication().getNdpRegSu10())){
			file.add("   Study Unit 10      = " + stuRegForm.getStudentApplication().getNdpRegSu10() + "\r\n");
		}
		file.add("                          \r\n");
		file.add(" --------------------------------------------------------------------------\r\n");
		file.add(" Staff Member                       = " + stuRegForm.getStudentApplication().getStaffCurrent() + "\r\n");
		file.add(" Dependant on Deceased Staff Member = " + stuRegForm.getStudentApplication().getStaffDeceased() + "\r\n");
		file.add(" Prisoner                           = " + stuRegForm.getStudentApplication().getPrisoner() + "\r\n");
		file.add(" --------------------------------------------------------------------------\r\n");
		file.add(" Completing a qualification         = " + stuRegForm.getStudentApplication().getCompleteQual()+ "\r\n");
		file.add(" Qualification Information          = " + stuRegForm.getStudentApplication().getCompleteText()+ "\r\n");
		file.add(" --------------------------------------------------------------------------\r\n");
		file.add(" Previously apply Postgraduate      = " + stuRegForm.getStudentApplication().getRadioPrev()+ "\r\n");
		file.add(" RPL Application                    = " + stuRegForm.getStudentApplication().getRadioRPL()+ "\r\n");
		file.add(" --------------------------------------------------------------------------\r\n");

		
		
		file.add(" --------------------------------------------------------------------------\r\n");
		file.add(" Applied for exemptions      = " + stuRegForm.getStudentApplication().getApplyExemptions()+ "\r\n");
		file.add(" Previous inst student nr    = " + stuRegForm.getStudentApplication().getPrevinstStudnr()+ "\r\n");
		file.add(" Career counselling needed   = " + stuRegForm.getStudentApplication().getCareerCounsel()+ "\r\n");
		file.add(" --------------------------------------------------------------------------\r\n");
		file.add(" Staff Member (Y/N)          = " + stuRegForm.getStudentApplication().getStaffCurrent() + "\r\n");
		file.add(" Dependant on Deceased \r\n");
		file.add(" Staff Member (Y/N)          = " + stuRegForm.getStudentApplication().getStaffDeceased() + "\r\n");
		file.add(" Prisoner (Y/N)              = " + stuRegForm.getStudentApplication().getPrisoner() + "\r\n");
		file.add(" --------------------------------------------------------------------------\r\n");
		file.add(" Completing a qualification  = " + stuRegForm.getStudentApplication().getCompleteQual()+ "\r\n");
		file.add(" Qualification Information   = " + stuRegForm.getStudentApplication().getCompleteText()+ "\r\n");
		file.add(" --------------------------------------------------------------------------\r\n");
		file.add(" Applied for Eduloan         = " + stuRegForm.getStudentApplication().getFinaidEduloan()+ "\r\n");
		file.add(" Applied for Nsfas           = " + stuRegForm.getStudentApplication().getFinaidNsfas()+ "\r\n");
		file.add(" --------------------------------------------------------------------------\r\n");
		file.add(" Matric certificate          = " + stuRegForm.getStudentApplication().getMatricCertificate()+ " "+stuRegForm.getStudentApplication().getMatricCertOther()+ "\r\n");
		file.add(" Matric admission            = " + stuRegForm.getStudentApplication().getHeAdmission()+ "\r\n");
		file.add(" Matric exam number          = " + stuRegForm.getStudentApplication().getMatricExamnr()+ "\r\n");
		file.add(" Matric year                 = " + stuRegForm.getStudentApplication().getMatricExamyear()+ "\r\n");
		file.add(" Matric province             = " + stuRegForm.getStudentApplication().getMatricProvince().getCode()+" "+stuRegForm.getStudentApplication().getMatricProvince().getDesc() +"\r\n");
		file.add(" Matric school               = " + stuRegForm.getStudentApplication().getMatricSchool().getCode()+" "+stuRegForm.getStudentApplication().getMatricSchool().getDesc() +"\r\n");
		file.add(" --------------------------------------------------------------------------\r\n");
		file.add(" Computer training needed    = " + stuRegForm.getStudent().getComputerTraining()+ "\r\n");
		/*
		file.add(" Access to following         = " +  "\r\n");
		file.add("                 internet    = " + stuRegForm.getStudent().getMediaAccess1()+ "\r\n");
		file.add("                 cellphone   = " + stuRegForm.getStudent().getMediaAccess2()+ "\r\n");
		file.add("                 cd-rom      = " + stuRegForm.getStudent().getMediaAccess3()+ "\r\n");
		file.add("                 dvd-rom     = " + stuRegForm.getStudent().getMediaAccess4()+ "\r\n");
		file.add("                 mp3 player  = " + stuRegForm.getStudent().getMediaAccess5()+ "\r\n");
		*/
		//file.add("Message                      = " + stuRegForm.getString500back()+"\r\n");
		file.add(" ==========================================================================\r\n");
		
		file.close(stuRegForm.getStudent().getNumber());

		//log.debug("ApplyForStudentNumber: Workflow for studnr="+stuRegForm.getStudent().getNumber());

		// ---------- Check input
		String underpost1 = " ";
		String underpost2 = " ";
		String code1 = " ";
		String code2 = " ";
		String qtype1 = " ";
		String qtype2 = " ";
		String qkat1 = " ";
		String qkat2 = " ";

		// Proposed qualification
		if (newQual1 != null && !"0".equals(newQual1) && !"undefined".equalsIgnoreCase(newQual1)){
			code1 = dao.validateQualification(stuRegForm.getStudent().getNumberTmp(), newQual1);
			underpost1 = code1.substring(0,1);
			qtype1 = code1.substring(1,2);
			qkat1 = code1.substring(2);
		}

		if (newQual2 != null && !"0".equals(newQual2) && !"undefined".equalsIgnoreCase(newQual2) && !"".equalsIgnoreCase(newQual2)){
			code2 = dao.validateQualification(stuRegForm.getStudent().getNumberTmp(), newQual2);
			underpost2 = code2.substring(0,1);
			qtype2 = code2.substring(1,2);
			qkat2 = code2.substring(2);
		}
		
		//log.debug("Apply4Nr - Workflow MatricCert: " + stuRegForm.getStudentApplication().getMatricCertificate());
		if (("G".equalsIgnoreCase(qtype1) && "U".equalsIgnoreCase(underpost1) && !"26".equals(qkat1)) || ("G".equalsIgnoreCase(qtype2) && "U".equalsIgnoreCase(underpost2) && !"26".equals(qkat2))){
			String matric="";
			String matricres="";
			if (!"CG".equals( stuRegForm.getStudentApplication().getMatricCertificate())){
				if("SC".equalsIgnoreCase(stuRegForm.getStudentApplication().getMatricCertificate())){
					matric="Senior Certificate";
				}
				if("NC".equalsIgnoreCase(stuRegForm.getStudentApplication().getMatricCertificate())){
					matric="National Senior certificate";
				}
				if("FF".equalsIgnoreCase(stuRegForm.getStudentApplication().getMatricCertificate())){
					matric="Failed std 10/Grade 12";
				}
				if("NV".equalsIgnoreCase(stuRegForm.getStudentApplication().getMatricCertificate())){
					matric="National Certificate Vocational";
				}
				if("OO".equalsIgnoreCase(stuRegForm.getStudentApplication().getMatricCertificate())){
					matric="Other";
				}
				if("M".equalsIgnoreCase(stuRegForm.getStudentApplication().getHeAdmission())){
					matricres="Std 10/Grade 12 without exemption";
				}
				if("S".equalsIgnoreCase(stuRegForm.getStudentApplication().getHeAdmission())){
					matricres="Senior certificate (Subject successes only)";
				}
				if("F".equalsIgnoreCase(stuRegForm.getStudentApplication().getHeAdmission())){
					matricres="Did not pass std 10/Grade 12";
				}
				if("O".equalsIgnoreCase(stuRegForm.getStudentApplication().getHeAdmission())){
					matricres="Other";
				}

				if ("M".equals( stuRegForm.getStudentApplication().getHeAdmission()) || "S".equals(stuRegForm.getStudentApplication().getHeAdmission()) || "O".equals( stuRegForm.getStudentApplication().getHeAdmission()) || "F".equals( stuRegForm.getStudentApplication().getHeAdmission())){
					//do matric m30 page
					WorkflowTempFile m30 = new WorkflowTempFile("M30_"+ type + "_"+ stuRegForm.getStudent().getNumber(),wflType);
					m30.add(" APPLICATION FOR EXEMPTION CERTIFICATE M30 \r\n");
					m30.add("  \r\n");
					m30.add(" Submission Time and Date  : " + displayDate + "\r\n");
					m30.add("  \r\n");
					m30.add(" --------------------------------------------------------------------------\r\n");
					m30.add(" Personal Information: \r\n");
					m30.add("  \r\n");
					m30.add(" Student number            : " + stuRegForm.getStudent().getNumber() + " " + "\r\n");
					m30.add(" Surname                   : " + stuRegForm.getStudent().getSurname() + "\r\n");
					m30.add(" Maiden name               : " + stuRegForm.getStudent().getMaidenName() + "\r\n");
					m30.add(" First names               : " + stuRegForm.getStudent().getFirstnames() + "\r\n");
					m30.add(" Birth date                : Year: " + stuRegForm.getStudent().getBirthYear()+ " Month: "+ stuRegForm.getStudent().getBirthMonth() +" Day: "+ stuRegForm.getStudent().getBirthDay()+"\r\n");
					m30.add(" --------------------------------------------------------------------------\r\n");
					if("R".equalsIgnoreCase(stuRegForm.getStudent().getIdType())){
						m30.add(" RSA identity number       : " + stuRegForm.getStudent().getIdNumber() + "\r\n");
					}else if("P".equalsIgnoreCase(stuRegForm.getStudent().getIdType())){
						m30.add(" Foreign ID/Passport number: " + stuRegForm.getStudent().getPassportNumber()+ "\r\n");
					}else if("F".equalsIgnoreCase(stuRegForm.getStudent().getIdType())){
						m30.add(" Foreign ID/Passport number: " + stuRegForm.getStudent().getForeignIdNumber()+ "\r\n");
					}
					m30.add(" --------------------------------------------------------------------------\r\n");
					m30.add(" Postal address            : " + stuRegForm.getStudent().getPostalAddress().getLine1()+ "\r\n");
					if(stuRegForm.getStudent().getPostalAddress().getLine2()!=null && !"".equals(stuRegForm.getStudent().getPostalAddress().getLine2())){
						m30.add("                             " + stuRegForm.getStudent().getPostalAddress().getLine2()+ "\r\n");
					}
					if(stuRegForm.getStudent().getPostalAddress().getLine3()!=null && !"".equals(stuRegForm.getStudent().getPostalAddress().getLine3())){
						m30.add("                             " + stuRegForm.getStudent().getPostalAddress().getLine3()+ "\r\n");
					}
					if(stuRegForm.getStudent().getPostalAddress().getLine4()!=null && !"".equals(stuRegForm.getStudent().getPostalAddress().getLine4())){
						m30.add("                             " + stuRegForm.getStudent().getPostalAddress().getLine4()+ "\r\n");
					}
					if(stuRegForm.getStudent().getPostalAddress().getLine5()!=null && !"".equals(stuRegForm.getStudent().getPostalAddress().getLine5())){
						m30.add("                             " + stuRegForm.getStudent().getPostalAddress().getLine5()+ "\r\n");
					}
					if(stuRegForm.getStudent().getPostalAddress().getLine6()!=null && !"".equals(stuRegForm.getStudent().getPostalAddress().getLine6())){
						m30.add("                             " + stuRegForm.getStudent().getPostalAddress().getLine6()+ "\r\n");
					}
					m30.add(" Postal code               : " + stuRegForm.getStudent().getPostalAddress().getAreaCode() + "\r\n");
					m30.add(" --------------------------------------------------------------------------\r\n");
					m30.add(" Email address             : " + stuRegForm.getStudent().getEmailAddress() + "\r\n");
					m30.add(" --------------------------------------------------------------------------\r\n");
					m30.add(" Educational Information \r\n");
					m30.add("  \r\n");
					m30.add(" School Certificate \r\n");
					m30.add(" Name of certificate    : " + stuRegForm.getMatric().getSchoolCertificate() + "\r\n");
					m30.add(" Other                  : " + stuRegForm.getStudentApplication().getMatricCertOther() + "\r\n");
					m30.add(" Year of completion     : " + stuRegForm.getMatric().getSchoolCompleteYr() + "\r\n");
					m30.add(" Province/Country       : " + stuRegForm.getMatric().getSchoolProvCountry() + "\r\n");
					m30.add(" Name of School attended: " + stuRegForm.getMatric().getSchoolName() + "\r\n");
					//m30.add(" Result achieved: " + matricres + "\r\n");
					m30.add(" --------------------------------------------------------------------------\r\n");
					m30.add(" Subject: " + stuRegForm.getMatric().getSubject1() + "\r\n");
					m30.add(" Year   : " + stuRegForm.getMatric().getSubyear1() + "\r\n");
					m30.add(" Month  : " + stuRegForm.getMatric().getSubmonth1() + "\r\n");
					m30.add(" Grade  : " + stuRegForm.getMatric().getSubgrade1() + "\r\n");
					m30.add(" Symbol : " + stuRegForm.getMatric().getSymbol1() + "\r\n");
					m30.add("  \r\n");
					m30.add(" Subject: " + stuRegForm.getMatric().getSubject2() + "\r\n");
					m30.add(" Year   : " + stuRegForm.getMatric().getSubyear2() + "\r\n");
					m30.add(" Month  : " + stuRegForm.getMatric().getSubmonth2() + "\r\n");
					m30.add(" Grade  : " + stuRegForm.getMatric().getSubgrade2() + "\r\n");
					m30.add(" Symbol : " + stuRegForm.getMatric().getSymbol2() + "\r\n");
					m30.add("  \r\n");
					m30.add(" Subject: " + stuRegForm.getMatric().getSubject3() + "\r\n");
					m30.add(" Year   : " + stuRegForm.getMatric().getSubyear3() + "\r\n");
					m30.add(" Month  : " + stuRegForm.getMatric().getSubmonth3() + "\r\n");
					m30.add(" Grade  : " + stuRegForm.getMatric().getSubgrade3() + "\r\n");
					m30.add(" Symbol : " + stuRegForm.getMatric().getSymbol3() + "\r\n");
					m30.add("  \r\n");
					m30.add(" Subject: " + stuRegForm.getMatric().getSubject4() + "\r\n");
					m30.add(" Year   : " + stuRegForm.getMatric().getSubyear4() + "\r\n");
					m30.add(" Month  : " + stuRegForm.getMatric().getSubmonth4() + "\r\n");
					m30.add(" Grade  : " + stuRegForm.getMatric().getSubgrade4() + "\r\n");
					m30.add(" Symbol : " + stuRegForm.getMatric().getSymbol4() + "\r\n");
					m30.add("  \r\n");
					m30.add(" Subject: " + stuRegForm.getMatric().getSubject5() + "\r\n");
					m30.add(" Year   : " + stuRegForm.getMatric().getSubyear5() + "\r\n");
					m30.add(" Month  : " + stuRegForm.getMatric().getSubmonth5() + "\r\n");
					m30.add(" Grade  : " + stuRegForm.getMatric().getSubgrade5() + "\r\n");
					m30.add(" Symbol : " + stuRegForm.getMatric().getSymbol5() + "\r\n");
					m30.add("  \r\n");
					m30.add(" Subject: " + stuRegForm.getMatric().getSubject6() + "\r\n");
					m30.add(" Year   : " + stuRegForm.getMatric().getSubyear6() + "\r\n");
					m30.add(" Month  : " + stuRegForm.getMatric().getSubmonth6() + "\r\n");
					m30.add(" Grade  : " + stuRegForm.getMatric().getSubgrade6() + "\r\n");
					m30.add(" Symbol : " + stuRegForm.getMatric().getSymbol6() + "\r\n");
					m30.add("  \r\n");
					m30.add(" Subject: " + stuRegForm.getMatric().getSubject7() + "\r\n");
					m30.add(" Year   : " + stuRegForm.getMatric().getSubyear7() + "\r\n");
					m30.add(" Month  : " + stuRegForm.getMatric().getSubmonth7() + "\r\n");
					m30.add(" Grade  : " + stuRegForm.getMatric().getSubgrade7() + "\r\n");
					m30.add(" Symbol : " + stuRegForm.getMatric().getSymbol7() + "\r\n");
					m30.add(" --------------------------------------------------------------------------\r\n");
					m30.add(" Post-School Qualification: \r\n");
					m30.add("  \r\n");
					m30.add(" Name of Diploma: " + stuRegForm.getMatric().getQualName() + "\r\n");
					m30.add(" Institution where obtained: " + stuRegForm.getMatric().getQualInstitution() + "\r\n");
					m30.add(" Year of completion: " + stuRegForm.getMatric().getQualCompleteYr() + "\r\n");
					m30.add(" Proposed qualification    : " + stuRegForm.getMatric().getQualPropose() + "\r\n");
					m30.add(" Year of First registration: " + stuRegForm.getStudent().getAcademicYear() + " " + "\r\n");
					m30.add(" --------------------------------------------------------------------------\r\n");
					m30.add(" For official use only: " + " " + "\r\n");
					m30.add(" Renewal: " + " " + "\r\n");
					m30.add(" Amendment: " + " " + "\r\n");
					m30.add(" Cert no.: " + " " + "\r\n");
					m30.add(" Issued: " + " " + "\r\n");
					m30.add(" Condition: " + " " + "\r\n");
					m30.add(" WEF: " + " " + "\r\n");
					m30.add(" Expiry Date: " + " " + "\r\n");
					m30.add(" MB Official: " + " " + "\r\n");
					m30.add(" Applicant Number: " + " " + "\r\n");
					m30.add(" ==========================================================================\r\n");
					m30.close(stuRegForm.getStudent().getNumber());
				}
			}
		}
		//work flow done
	}

	/**
	 * This method writes the application for qualification change workflow file to the
	 * path specified in the following file:
	 * sakai/jakarta-tomcat-5.5.9/sakai/sakai.properties
	 *
	 * @param stuRegForm	       The form associated with the action
	 * @param applyDateTime		   The date and time the application was submitted
	*/
	private void writeWorkflowRet(StudentRegistrationForm stuRegForm, Date applyDateTime) throws Exception{

		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();

		  // qualification code
		
		String existQual 	 = stuRegForm.getStudent().getRetQualPrevFinal();
		String existSpec 	 = stuRegForm.getStudent().getRetSpecPrevFinal();

		String newQual1Desc = "";
		String newQual2Desc = "";

		String newQual1 = dao.getXMLSelected("qualCode1", "1", stuRegForm.getStudent().getNumber(),stuRegForm.getStudent().getAcademicYear(),stuRegForm.getStudent().getAcademicPeriod(), "writeWorkflowRet");
		String newSpec1 = dao.getXMLSelected("specCode1", "1", stuRegForm.getStudent().getNumber(),stuRegForm.getStudent().getAcademicYear(),stuRegForm.getStudent().getAcademicPeriod(), "writeWorkflowRet");

		String newQual2 = dao.getXMLSelected("qualCode2", "2", stuRegForm.getStudent().getNumber(),stuRegForm.getStudent().getAcademicYear(),stuRegForm.getStudent().getAcademicPeriod(), "writeWorkflowRet");
		String newSpec2 = dao.getXMLSelected("specCode2", "2", stuRegForm.getStudent().getNumber(),stuRegForm.getStudent().getAcademicYear(),stuRegForm.getStudent().getAcademicPeriod(), "writeWorkflowRet");

		if (stuRegForm.getStudent().getQual1() == null || newQual1 == null || stuRegForm.getStudent().getSpec1() == null || newSpec1 == null){
			//log.debug("ApplyForStudentNumberAction - writeWorkflowRet Student Has Null Entry Student="+stuRegForm.getStudent().getNumber()+", Qual1=" + stuRegForm.getStudent().getQual1() +", XMLQual1="+newQual1+", Spec1=" + stuRegForm.getStudent().getSpec1() +", XMLSpec1="+newSpec1);
		}
		if (stuRegForm.getStudent().getQual2() == null || newQual2 == null || stuRegForm.getStudent().getSpec2() == null || newSpec2 == null){
			//log.debug("ApplyForStudentNumberAction - writeWorkflowRet Student Has Null Entry Student="+stuRegForm.getStudent().getNumber()+", Qual2=" + stuRegForm.getStudent().getQual2() +", XMLQual2="+newQual2+", Spec2=" + stuRegForm.getStudent().getSpec2() +", XMLSpec2="+newSpec2);
		}
		
		//log.debug("ApplyForStudentNumberAction - writeWorkflowRet - get Action existQual1: " + existQual);
		//log.debug("ApplyForStudentNumberAction - writeWorkflowRet - get Action existSpec1: " + existSpec);

		//log.debug("ApplyForStudentNumberAction - writeWorkflowRet - get Action newQual1: " + newQual1);
		//log.debug("ApplyForStudentNumberAction - writeWorkflowRet - get Action newSpec1: " + newSpec1);
		//log.debug("ApplyForStudentNumberAction - writeWorkflowRet - get Action newQual2: " + newQual2);
		//log.debug("ApplyForStudentNumberAction - writeWorkflowRet - get Action newSpec2: " + newSpec2);
		
		if (existSpec == null || "".equals(existSpec) || "0".equals(existSpec) || "undefined".equalsIgnoreCase(existSpec)){
			existSpec = " ";
		}

		if (newQual1 != null && !"".equals(newQual1) && !"0".equals(newQual1) && !"undefined".equalsIgnoreCase(newQual1)){
			newQual1Desc = dao.getQualDesc(newQual1);

			if (newSpec1 != null && !"".equals(newSpec1) && !"0".equals(newSpec1) && !"NVT".equalsIgnoreCase(newSpec1) && !"undefined".equalsIgnoreCase(newSpec1)){
				newSpec1 = stuRegForm.getStudent().getSpec1();
			}else{
				newSpec1 = "N/A - Not Applicable";
			}
		}else{
		  //log.debug("ApplyForQualChange: WARNING! - getQual1 for studnr="+stuRegForm.getStudent().getNumber() + " is empty");
		  if (stuRegForm.getSelQualCode1() != null && !"".equals(stuRegForm.getSelQualCode1()) && !"0".equals(stuRegForm.getSelQualCode1()) && !"undefined".equalsIgnoreCase(stuRegForm.getSelQualCode1())){
			  newQual1 = stuRegForm.getSelQualCode1();
			  newQual1Desc = dao.getQualDesc(newQual1);

				if (stuRegForm.getSelSpecCode1() != null && !"".equals(stuRegForm.getSelSpecCode1()) && !"0".equals(stuRegForm.getSelSpecCode1())  && !"NVT".equals(stuRegForm.getSelSpecCode1()) && !"undefined".equalsIgnoreCase(stuRegForm.getSelSpecCode1())){
					newSpec1 = stuRegForm.getSelSpecCode1();
				}else{
					newSpec1 = "N/A - Not Applicable";
				}
		  }else{
			  //log.debug("ApplyForQualChange: WARNING! - getSelQualCode1 for studnr="+stuRegForm.getStudent().getNumber() + " is ALSO empty");
		  }
		}
		
		if (newQual2 != null && !"".equals(newQual2) && !"0".equals(newQual2) && !"undefined".equalsIgnoreCase(newQual2)){
			newQual2Desc = dao.getQualDesc(newQual2);

			if (newSpec2 != null && !"".equals(newSpec2) && !"0".equals(newSpec2) && !"NVT".equalsIgnoreCase(newSpec2) && !"undefined".equalsIgnoreCase(newSpec2)){
				newSpec2 = stuRegForm.getStudent().getSpec2();
			}else{
				newSpec2 = "N/A - Not Applicable";
			}
		}else{
		  //log.debug("ApplyForQualChange: WARNING! - getQual2 for studnr="+stuRegForm.getStudent().getNumber() + " is empty");
		  if (stuRegForm.getSelQualCode2() != null && !"".equals(stuRegForm.getSelQualCode2()) && !"0".equals(stuRegForm.getSelQualCode2()) && !"undefined".equalsIgnoreCase(stuRegForm.getSelQualCode2())){
			  newQual2 = stuRegForm.getSelQualCode2();
			  newQual2Desc = dao.getQualDesc(newQual2);

				if (stuRegForm.getSelSpecCode2() != null && !"".equals(stuRegForm.getSelSpecCode2()) && !"0".equals(stuRegForm.getSelSpecCode2()) && !"NVT".equals(stuRegForm.getSelSpecCode2()) && !"undefined".equalsIgnoreCase(stuRegForm.getSelSpecCode2())){
					newSpec2 = stuRegForm.getSelSpecCode2();
				}else{
					newSpec2 = "N/A - Not Applicable";
				}
		  }else{
			  //log.debug("ApplyForQualChange: WARNING! - getSelQualCode2 for studnr="+stuRegForm.getStudent().getNumber() + " is ALSO empty");
			  newQual2 = "N/A";
			  newQual2Desc = "Not Applicable";
			  newSpec2 = "N/A - Not Applicable";
		  }
		}

		//log.debug("ApplyForStudentNumberAction - writeWorkflowRet - get Action newQual1Desc: " + newQual1Desc);
		//log.debug("ApplyForStudentNumberAction - writeWorkflowRet - get Action newQual2Desc: " + newQual2Desc);
		
		String type = "L"; /* L = local F=Foreign */
		String wflType = "APP";
		//Johanet 20180827 - Write SLP returning student to SLP folder
		if (stuRegForm.getStudent().isStuSLP()){
			wflType = "SLP";
		}

		/* set local or foreign */
		if (!"1015".equals(stuRegForm.getStudent().getCountry().getCode())){
			type = "F";
		}

		/* write to file */
		WorkflowTempFile file = new WorkflowTempFile("ChangeappSN_"+ type + "_"+ stuRegForm.getStudent().getNumber(),wflType);
		file.add(" Subject: Application for Qualification Change \r\n");
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
		file.add(" Current qualification              = " + existQual + "\r\n");
		file.add(" Current specialisation             = " + existSpec + "\r\n");
		file.add(" Proposed primary qualification     = " + newQual1+" - "+ newQual1Desc+ "\r\n");
		file.add(" Proposed primary specialisation    = " + newSpec1 + "\r\n");
		file.add(" Proposed secondary qualification   = " + newQual2+" - "+ newQual2Desc+ "\r\n");
		file.add(" Proposed secondary specialisation  = " + newSpec2 + "\r\n");
		
		
		//applyPrevQual Info
		HistoryUnisa unisa = new HistoryUnisa();
		if (stuRegForm.getQualUnisa() != null && !stuRegForm.getQualUnisa().isEmpty()){
			//log.debug("ApplyForQualChange: Workflow for studnr="+stuRegForm.getStudent().getNumber() + " Unisa Qualifications is not Empty="+stuRegForm.getQualUnisa().size());

			if (stuRegForm.getQualUnisa().size() > 0){
				file.add(" --------------------------------------------------------------------------\r\n");
				file.add(" Previous academic history  :  UNISA \r\n");
				for (int i=0; i < stuRegForm.getQualUnisa().size(); i++){
					unisa = (HistoryUnisa)stuRegForm.getQualUnisa().get(i);
					file.add("   Qualification "+i+"   = " + unisa.getHistQual() + "\r\n");
					file.add("   First registration yr = " + unisa.getHistYear() + "\r\n");
					file.add("   Complete yr           = " + unisa.getHistComplete() + "\r\n");
					file.add("   --------------------- \r\n");
				}
			}
		}else{
			//log.debug("ApplyForQualChange: Workflow for studnr="+stuRegForm.getStudent().getNumber() + " Other Qualifications is Empty");
		}

		HistoryArray other = new HistoryArray();
		if (stuRegForm.getQualArray() != null && !stuRegForm.getQualArray().isEmpty()){
			//log.debug("ApplyForQualChange: Workflow for studnr="+stuRegForm.getStudent().getNumber() + " Other Qualifications is not Empty="+stuRegForm.getQualArray().size());

			if (stuRegForm.getQualArray().size() > 0){
				file.add(" --------------------------------------------------------------------------\r\n");
				file.add(" Previous academic history  :  OTHER \r\n");
				for (int i=0; i < stuRegForm.getQualArray().size(); i++){
					other = (HistoryArray)stuRegForm.getQualArray().get(i);
					file.add("   Qualification "+i+"   = " + other.getOtherSEQ() + "\r\n");
					file.add("   InstitutionCode       = " + other.getOtherUniv() + "\r\n");
					file.add("   InstitutionOther      = " + other.getOtherUnivText() + "\r\n");
					file.add("   Student nr            = " + other.getOtherStudnr() + "\r\n");
					file.add("   Qualification         = " + other.getOtherQual() + "\r\n");
					file.add("   First registration yr = " + other.getOtherYearStart() + "\r\n");
					file.add("   Last Registration yr  = " + other.getOtherYearEnd() + "\r\n");
					file.add("   Completed             = " + other.getOtherComplete() + "\r\n");
					file.add("   Country               = " + other.getOtherCountry() + "\r\n");
					file.add("   Foreign               = " + other.getOtherForeign() + "\r\n");
					file.add("   --------------------- \r\n");
				}
			}
		}else{
			//log.debug("ApplyForQualChange: Workflow for studnr="+stuRegForm.getStudent().getNumber() + " Other Qualifications is Empty");
		}
		
		if (stuRegForm.getSubjects() != null && !stuRegForm.getSubjects().isEmpty()){
			//log.debug("ApplyForQualChange: Workflow for studnr="+stuRegForm.getStudent().getNumber() + " Subjects not Empty="+stuRegForm.getSubjects().size());
			Subject sub = new Subject();
			if (stuRegForm.getSubjects().size() > 0){
				file.add(" --------------------------------------------------------------------------\r\n");
				file.add(" APS Matric Infomation  \r\n");
				for (int i = 0; i < stuRegForm.getSubjects().size(); i++){
					sub = stuRegForm.getSubjects().get(i);
					file.add("   Subject "+i+"         = " + sub.getSubjectName() + "\r\n");
					file.add("   Symbol                = " + sub.getSubjectSymbol() + "\r\n");
					file.add("   Result                = " + sub.getSubjectResult() + "\r\n");
					file.add("   --------------------- \r\n");
				}
			}
		}else{
			//log.debug("ApplyForQualChange: Workflow for studnr="+stuRegForm.getStudent().getNumber() + " Subjects is Empty");
		}
		
		//NDP Modules
		file.add(" --------------------------------------------------------------------------\r\n");
		file.add(" Non Degree Purposes  \r\n");
		file.add("                         \r\n");
		file.add("   NDP Flag              = " + stuRegForm.getStudentApplication().getRadioNDP() + "\r\n");
		file.add("   --------------------- \r\n");
		
		if (stuRegForm.getStudentApplication().getNdpRegSu1() != null && !"".equals(stuRegForm.getStudentApplication().getNdpRegSu1())){
			file.add("   StudyUnit/Module  \r\n");
			file.add("   --------------------- \r\n");
			file.add("   Study Unit 1      = " + stuRegForm.getStudentApplication().getNdpRegSu1() + "\r\n");
		}
		if (stuRegForm.getStudentApplication().getNdpRegSu2() != null && !"".equals(stuRegForm.getStudentApplication().getNdpRegSu2())){
			file.add("   Study Unit 2      = " + stuRegForm.getStudentApplication().getNdpRegSu2() + "\r\n");
		}
		if (stuRegForm.getStudentApplication().getNdpRegSu3() != null && !"".equals(stuRegForm.getStudentApplication().getNdpRegSu3())){
			file.add("   Study Unit 3      = " + stuRegForm.getStudentApplication().getNdpRegSu3() + "\r\n");
		}
		if (stuRegForm.getStudentApplication().getNdpRegSu4() != null && !"".equals(stuRegForm.getStudentApplication().getNdpRegSu4())){
			file.add("   Study Unit 4      = " + stuRegForm.getStudentApplication().getNdpRegSu4() + "\r\n");
		}
		if (stuRegForm.getStudentApplication().getNdpRegSu5() != null && !"".equals(stuRegForm.getStudentApplication().getNdpRegSu5())){
			file.add("   Study Unit 5      = " + stuRegForm.getStudentApplication().getNdpRegSu5() + "\r\n");
		}
		if (stuRegForm.getStudentApplication().getNdpRegSu6() != null && !"".equals(stuRegForm.getStudentApplication().getNdpRegSu6())){
			file.add("   Study Unit 6      = " + stuRegForm.getStudentApplication().getNdpRegSu6() + "\r\n");
		}
		if (stuRegForm.getStudentApplication().getNdpRegSu7() != null && !"".equals(stuRegForm.getStudentApplication().getNdpRegSu7())){
			file.add("   Study Unit 7      = " + stuRegForm.getStudentApplication().getNdpRegSu7() + "\r\n");
		}
		if (stuRegForm.getStudentApplication().getNdpRegSu8() != null && !"".equals(stuRegForm.getStudentApplication().getNdpRegSu8())){
			file.add("   Study Unit 8      = " + stuRegForm.getStudentApplication().getNdpRegSu8() + "\r\n");
		}
		if (stuRegForm.getStudentApplication().getNdpRegSu9() != null && !"".equals(stuRegForm.getStudentApplication().getNdpRegSu9())){
			file.add("   Study Unit 9      = " + stuRegForm.getStudentApplication().getNdpRegSu9() + "\r\n");
		}
		if (stuRegForm.getStudentApplication().getNdpRegSu10() != null && !"".equals(stuRegForm.getStudentApplication().getNdpRegSu10())){
			file.add("   Study Unit 10      = " + stuRegForm.getStudentApplication().getNdpRegSu10() + "\r\n");
		}
		file.add("                       \r\n");
		file.add(" --------------------------------------------------------------------------\r\n");
		file.add(" Staff Member                       = " + stuRegForm.getStudentApplication().getStaffCurrent() + "\r\n");
		file.add(" Dependant on Deceased Staff Member = " + stuRegForm.getStudentApplication().getStaffDeceased() + "\r\n");
		file.add(" Prisoner                           = " + stuRegForm.getStudentApplication().getPrisoner() + "\r\n");
		file.add(" --------------------------------------------------------------------------\r\n");
		file.add(" Completing a qualification         = " + stuRegForm.getStudentApplication().getCompleteQual()+ "\r\n");
		file.add(" Qualification Information          = " + stuRegForm.getStudentApplication().getCompleteText()+ "\r\n");
		file.add(" --------------------------------------------------------------------------\r\n");
		file.add(" Previously apply Postgraduate      = " + stuRegForm.getStudentApplication().getRadioPrev()+ "\r\n");
		file.add(" RPL Application                    = " + stuRegForm.getStudentApplication().getRadioRPL()+ "\r\n");
		file.add(" --------------------------------------------------------------------------\r\n");
		
		//file.add("Message                           = " + stuRegForm.getString500back()+"\r\n");
		file.add(" ==========================================================================\r\n");
		//Start Johanet - write returning student directly to application folder
		//file.close(stuRegForm.getStudent().getNumber());
		file.closeRet(stuRegForm.getStudent().getNumber());
		//End Johanet - write returning student directly to application folder

		//log.debug("ApplyForQualChange: Workflow for studnr="+stuRegForm.getStudent().getNumber() + " Completed");
		
	}
	
	private void writeWorkflowSLP(StudentRegistrationForm stuRegForm, Date applyDateTime) throws Exception{

		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
		
		String type = "L"; /* L = local F=Foreign */
		String wflType = "APP";
		
		String countrycode = dao.getCountry(stuRegForm.getStudent().getNumber());

		/* set local or foreign */
		if (!"1015".equals(countrycode)){
			type = "F";
		}

		String wflSpec1 = " ";
		String wflSpec2 = " ";
		if (stuRegForm.getSelSpecCode1() != null && !"".equals(stuRegForm.getSelSpecCode1()) && !"0".equals(stuRegForm.getSelSpecCode1())  && !"NVT".equals(stuRegForm.getSelSpecCode1()) && !"undefined".equalsIgnoreCase(stuRegForm.getSelSpecCode1())){
			wflSpec1 = stuRegForm.getSelSpecCode1();
		}else{
			wflSpec1 = "N/A - NOT APPLICABLE";
		}
		if (stuRegForm.getSelSpecCode2() != null && !"".equals(stuRegForm.getSelSpecCode2()) && !"0".equals(stuRegForm.getSelSpecCode2())  && !"NVT".equals(stuRegForm.getSelSpecCode2()) && !"undefined".equalsIgnoreCase(stuRegForm.getSelSpecCode2())){
			wflSpec2 = stuRegForm.getSelSpecCode2();
		}else{
			wflSpec2 = "N/A - NOT APPLICABLE";
		}
		
		/* write to file */
		WorkflowTempFile file = new WorkflowTempFile("ChangeappSN_"+ type + "_"+ stuRegForm.getStudent().getNumber(),wflType);
		file.add(" Subject: Application for SLP Second Choice Qualification \r\n");
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
		file.add(" Proposed primary qualification     = " + stuRegForm.getSelQualCode1()+" - "+ stuRegForm.getSelQualCode1Desc()+ "\r\n");
		file.add(" Proposed primary specialisation    = " + wflSpec1 + "\r\n");
		file.add(" Proposed secondary qualification   = " + stuRegForm.getSelQualCode2()+" - "+ stuRegForm.getSelQualCode1Desc()+ "\r\n");
		file.add(" Proposed secondary specialisation  = " + wflSpec2 + "\r\n");
		file.add("                       \r\n");		
		file.add(" ==========================================================================\r\n");
		file.close(stuRegForm.getStudent().getNumber());

		//log.debug("ApplyForStudentNumberAction: SLP Choice 2 Workflow for studnr="+stuRegForm.getStudent().getNumber() + " Completed");
		
	}
	/**
	 * This method writes the application for qualification appeal workflow file to the
	 * path specified in the following file:
	 * sakai/jakarta-tomcat-5.5.9/sakai/sakai.properties
	 *
	 * @param stuRegForm	       The form associated with the action
	 * @param applyDateTime		   The date and time the application was submitted
	*/
	private void writeWorkflowAppeal(StudentRegistrationForm stuRegForm, Date applyDateTime) throws Exception{
		
		//log.debug("ApplyForStudentNumberAction - writeWorkflowAppeal for studnr="+stuRegForm.getStudent().getNumber() + " Started");

		//ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();

		// Qualifications Appealed - Debug
		/*
		if ("Y".equalsIgnoreCase(stuRegForm.getAppealSelect1())){
			//log.debug("ApplyForStudentNumberAction - writeWorkflowAppeal - AppealQual1: " + stuRegForm.getSelQualCode1());
		}
		if ("Y".equalsIgnoreCase(stuRegForm.getAppealSelect2())){
			//log.debug("ApplyForStudentNumberAction - writeWorkflowAppeal - AppealQual2: " + stuRegForm.getSelQualCode2());
		}
		
		//log.debug("ApplyForStudentNumberAction - writeWorkflowAppeal - AppealText: " + stuRegForm.getAppealText());
		
		//log.debug("ApplyForStudentNumberAction - writeWorkflowAppeal - No of: AppealTypeFiles" + stuRegForm.getAppealTypeFiles().size()+", AppealSourceFiles" + stuRegForm.getAppealSourceFiles().size()+", AppealWorkflowFiles" + stuRegForm.getAppealWorkflowFiles().size());
		
		if (stuRegForm.getAppealTypeFiles().size() > 0){
			for (int i = 0; i < stuRegForm.getAppealTypeFiles().size(); i++){
				//log.debug("ApplyForStudentNumberAction - appealUpload - Appeal File - Done - Type File "+i+" - File=" + stuRegForm.getAppealTypeFiles().get(i).toString());
			}
		}
		if (stuRegForm.getAppealWorkflowFiles().size() > 0){
			for (int i = 0; i < stuRegForm.getAppealWorkflowFiles().size(); i++){
				//log.debug("ApplyForStudentNumberAction - appealUpload - Appeal File - Done - Workflow File "+i+" - File=" + stuRegForm.getAppealWorkflowFiles().get(i).toString());
			}
		}
		if (stuRegForm.getAppealSourceFiles().size() > 0){
			for (int i = 0; i < stuRegForm.getAppealSourceFiles().size(); i++){
				//log.debug("ApplyForStudentNumberAction - appealUpload - Appeal File - Done - Source File "+i+" - File=" + stuRegForm.getAppealSourceFiles().get(i).toString());
			}
		}
		*/

		String type = "L"; /* L = local F=Foreign */
		String wflType = "APP";

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

		//log.debug("ApplyForQualChange: writeWorkflowAppeal for studnr="+stuRegForm.getStudent().getNumber() + " Completed");
		
	}

	public ActionForward nextStep(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// if student number is null, block application
		ActionMessages messages = new ActionMessages();
		StudentRegistrationForm stuRegForm = (StudentRegistrationForm) form;

		String nextPage = "";
		String page = stripXSS(request.getParameter("page"), "page", "nextPage", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true);
		//log.debug("ApplyForStudentNumberAction - Nextpage - page="+page+", nextPage=" + nextPage);
		

		if(stuRegForm.getStudent().getNumber() == null){
			//log.debug("ApplyForStudentNumberAction - Nextpage - Student Nr is null or empty when saving qualifications");
			messages.add(ActionMessages.GLOBAL_MESSAGE,
			new ActionMessage("message.generalmessage", "You performed and invalid action or an Error occurred. Please log on again to retry."));
			addErrors(request, messages);
			if (stuRegForm.getAdminStaff().isAdmin()){
				return mapping.findForward("loginStaff");
			}else{
				return mapping.findForward("loginStu");
			}
		}
		if (stuRegForm.getApplyType()==null || "".equals(stuRegForm.getApplyType())){
			stuRegForm.setApplyType("F");
		}
		
		if ("applyQualification".equalsIgnoreCase(page)){
			if ("NO".equalsIgnoreCase(stuRegForm.getLoginSelectYesNo())){
				//log.debug("ApplyForStudentNumberAction - Nextpage - StudentNr="+stuRegForm.getStudent().getNumber()+" - Goto saveStudyNew");
				nextPage = saveStudyNew(mapping,form,request, response);
			}else{
				//log.debug("ApplyForStudentNumberAction - Nextpage - StudentNr="+stuRegForm.getStudent().getNumber()+" - Goto saveStudyRet");
				nextPage = saveStudyRet(mapping,form,request, response);
			}
			
		}
		
		if ("applyLogin".equalsIgnoreCase(page)){
			setDropdownListsLogin(request,stuRegForm);
			return mapping.findForward("applyLogin");
		}else if ("applyMatric".equalsIgnoreCase(page)){
 			nextPage = applyMatric(mapping,form,request, response);
		}else if ("applyMatricSubject".equalsIgnoreCase(page)){
 			nextPage = applyMatricSubject(mapping,form,request, response);	
 		
		}else if ("applyNewPersonal".equalsIgnoreCase(page)){
 			setDropdownListsStep1(request,stuRegForm);
 			nextPage = applyNewPersonal(mapping,form,request, response);
		}else if ("applyNewContact".equalsIgnoreCase(page)){
 			nextPage = applyNewContact(mapping,form,request, response);
		//}else if ("applyNewContact".equalsIgnoreCase(page)){
		//	setDropdownListsStep2(request,stuRegForm);
 		//	nextPage = applyNewAddress(mapping,form,request, response);
		}else if ("applyNewAddress1".equalsIgnoreCase(page)){
			setDropdownListsStep2(request, form);
			nextPage = applyNewAddress1(mapping,form,request, response);
		}else if ("applyNewAddress2".equalsIgnoreCase(page)){
			nextPage = applyNewAddress2(mapping,form,request, response);
		}else if ("applyNewAddress3".equalsIgnoreCase(page)){
			nextPage = applyNewAddress3(mapping,form,request, response);
		}else if ("applyNewInfo1".equalsIgnoreCase(page)){
			nextPage = applyNewInfo1(mapping,form,request, response);
		}else if ("applyNewInfo2".equalsIgnoreCase(page)){
			nextPage = applyNewInfo2(mapping,form,request, response);
		}else if ("applyNewInfo3".equalsIgnoreCase(page)){
			nextPage = applyNewInfo3(mapping,form,request, response);
		}else if ("applyNewSchool".equalsIgnoreCase(page)){
			setUpProvinceList(request);
			nextPage = applyNewSchool(mapping,form,request, response);
		}else if ("m30p1".equalsIgnoreCase(page)){
			nextPage = applym30p1(mapping,form,request, response);
		}else if ("m30p2".equalsIgnoreCase(page)){
			nextPage = applym30p2(mapping,form,request, response);
		}else if ("applyType".equalsIgnoreCase(page)){
 			setDropdownListsStep1(request,stuRegForm);
 			nextPage = "applyNewPersonal";
		}else if ("appealStep1".equalsIgnoreCase(page)){
			setDropdownListsAppeal(request,stuRegForm);
			nextPage = appealSelect(mapping,form,request, response);
		}else if ("appealSelect".equalsIgnoreCase(page)){
			setDropdownListsAppeal(request,stuRegForm);
			nextPage = appealSelect(mapping,form,request, response);
		}else if ("appealDeclare".equalsIgnoreCase(page)){
			nextPage = appealDeclare(mapping,form,request, response);
		}else if ("appealConfirm".equalsIgnoreCase(page)){
			nextPage = appealConfirm(mapping,form,request, response);
		}else if ("applyOffer".equalsIgnoreCase(page)){
			nextPage = applyOffer(mapping,form,request, response);
		}else if ("applyOfferConfirm".equalsIgnoreCase(page)){
			nextPage = applyOfferConfirm(mapping,form,request, response);
		}else if ("search".equalsIgnoreCase(page)){
			stuRegForm.setSearchResult(stripXSS(stuRegForm.getSearchResult(), "SearchResult", "nextPage", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
			if (stuRegForm.getSearchResult() == null || stuRegForm.getSearchResult().length() <= 0 || stuRegForm.getSearchResult().length() < 4){
				//log.debug("ApplyForStudentNumberAction - Nextpage - Postal code less than 4 characters: " + stuRegForm.getSearchResult());
				messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", "Invalid postal code, should be 4 characters. Please try again."));
				addErrors(request, messages);
				nextPage = "searchforward";
			}else{
				setDropdownListsStep2(request, stuRegForm);
				nextPage = postalCodeSelected(mapping, form, request, response);
			}
		}
		
		//log.debug("ApplyForStudentNumberAction Nextpage - Nextpage=" + nextPage);
		return mapping.findForward(nextPage);
	}

	
	/* This method redirects a page to the previous page
	 according to the setup below 	*/
	@SuppressWarnings("unused")
	public ActionForward back(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		StudentRegistrationForm stuRegForm = (StudentRegistrationForm) form;
		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
		
		String qualif = stuRegForm.getQual().getQualCode();
		String applyType=stuRegForm.getApplyType();
		String prevPage = "";
		String page = stripXSS(request.getParameter("page"), "page", "back", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true);
		//log.debug("ApplyForStudentNumberAction - Back - Page="+page+", prevPage="+prevPage);
		
		
		if ("applyLoginSLP".equalsIgnoreCase(page)) {
			stuRegForm.getStudent().setNumber("");
			stuRegForm.getStudent().setSurname("");
			stuRegForm.getStudent().setFirstnames("");
			stuRegForm.getStudent().setBirthYear("");
			stuRegForm.getStudent().setBirthMonth("");
			stuRegForm.getStudent().setBirthDay("");
			prevPage = "applyLoginSelect";
		}else if ("applyLoginNumber".equalsIgnoreCase(page)) {
			stuRegForm.getStudent().setNumber("");
			stuRegForm.getStudent().setSurname("");
			stuRegForm.getStudent().setFirstnames("");
			stuRegForm.getStudent().setBirthYear("");
			stuRegForm.getStudent().setBirthMonth("");
			stuRegForm.getStudent().setBirthDay("");
			prevPage = "applyLoginSelect";
		}else if ("applyLogin".equalsIgnoreCase(page)) {
			stuRegForm.getStudent().setNumber("");
			stuRegForm.getStudent().setSurname("");
			stuRegForm.getStudent().setFirstnames("");
			stuRegForm.getStudent().setBirthYear("");
			stuRegForm.getStudent().setBirthMonth("");
			stuRegForm.getStudent().setBirthDay("");
			if (stuRegForm.getAdminStaff().isAdmin()){
				prevPage = "loginStaff";
			}else{
				prevPage = "applyLoginNumber";
			}
		}else if ("stepAPSSelect".equalsIgnoreCase(page)) {
			setDropdownListsLogin(request,stuRegForm);
			prevPage = "applyLogin";
		}else if ("stepAPS2".equalsIgnoreCase(page)) {
			prevPage = "";
		}else if ("applyQualification".equalsIgnoreCase(page)) {
			if ("UD".equalsIgnoreCase(stuRegForm.getLoginSelectMain()) && !stuRegForm.getAdminStaff().isAdmin()){
				prevPage = "applyAPSSelect";
			}else{
				setDropdownListsLogin(request,stuRegForm);
				prevPage = "applyLogin";
			}
		}else if ("stepQualConfirm".equalsIgnoreCase(page)) {
			prevPage =  "applyQualification";
		}else if ("applyIDNumber".equalsIgnoreCase(page)){
			prevPage = "applyAPSSelect";
		}else if ("appealSelect".equalsIgnoreCase(page)) {
			prevPage =  "applyLoginSelect";
		}else if ("appealDeclare".equalsIgnoreCase(page)) {
			setDropdownListsAppeal(request,stuRegForm);
			prevPage =  "appealSelect";
		}else if ("appealConfirm".equalsIgnoreCase(page)) {
			prevPage =  "appealDeclare";
		}else if ("applyOfferConfirm".equalsIgnoreCase(page)) {
			prevPage =  "applyOffer";
		}else if ("applyMatricSubject".equalsIgnoreCase(page)) {
			prevPage = "applyMatric";
		}else if ("applyMatric".equalsIgnoreCase(page)) {
			prevPage = "applyIDNumber";
		}else if ("applyPrevQual".equalsIgnoreCase(page)) {
			prevPage = "applyQualification";
		}else if ("applyNewPersonal".equalsIgnoreCase(page)) {
			boolean isPrevQual1 = false;
			boolean isPrevQual2 = false;
			try {
				//log.debug("ApplyForStudentNumberAction - Back - " + stuRegForm.getStudent().getNumber() +" - Done");
				//Flow to Honours screen for specific qualifications - Claudette 2018
				// continue from Name page next = address page or m&d page
				isPrevQual1 = dao.getQualCategory(stuRegForm.getStudent().getQual1());
				if (stuRegForm.getStudent().getQual2() != null && !"".equals(stuRegForm.getStudent().getQual2())){
					isPrevQual2 = dao.getQualCategory(stuRegForm.getStudent().getQual2());
				}
				//log.debug("ApplyForStudentNumberAction - Back - applyPrevQual isPrevQual1: " + isPrevQual1 + " isPrevQual2: " + isPrevQual2);
			} catch (Exception e) {
				//log.debug("ApplyForStudentNumberAction - Back - " + stuRegForm.getStudent().getNumber() +" - Exception / " + e);
				//log.debug("ApplyForStudentNumberAction - Back - N " + stuRegForm.getStudent().getNumber() + " Error="+e);
			}
			if (isPrevQual1 || isPrevQual2){
				//log.debug("ApplyForStudentNumberAction - Back - Goto applyPrevQual");
				prevPage =  "applyNDP";
			}else{
				//log.debug("ApplyForStudentNumberAction - Back - Goto applyQualification");
				prevPage =  "applyQualification";
			}

		}else if ("applyNewContact".equalsIgnoreCase(page)) {
 			setDropdownListsStep1(request, stuRegForm);
			prevPage = "applyNewPersonal";
		}else if ("applyNewAddress1".equalsIgnoreCase(page)) {
			prevPage = "applyNewContact";
		}else if ("applyNewAddress2".equalsIgnoreCase(page)) {
			setDropdownListsStep2(request, form);
			prevPage = "applyNewAddress1";
		}else if ("applyNewAddress3".equalsIgnoreCase(page)) {
			prevPage = "applyNewAddress2";
		}else if ("applyNewInfo1".equalsIgnoreCase(page)) {
			prevPage = "applyNewAddress3";
		}else if ("applyNewInfo2".equalsIgnoreCase(page)) {
			setDropdownListsStep3(request, stuRegForm);
			prevPage = "applyNewInfo1";
		}else if ("applyNewInfo3".equalsIgnoreCase(page)) {
			prevPage = "applyNewInfo2";
		}else if ("applyNewSchool".equalsIgnoreCase(page)) {
			setUpUniversityList(request);
			prevPage = "applyNewInfo3";
		}else if ("applyNewDeclare".equalsIgnoreCase(page)) {
			stuRegForm.setAgree("");
			setUpProvinceList(request);
			prevPage = "applyNewSchool";
		}else if ("applyRetContact".equalsIgnoreCase(page)) {
			boolean isPrevQual1 = false;
			boolean isPrevQual2 = false;
			try {
				//log.debug("ApplyForStudentNumberAction - Back - " + stuRegForm.getStudent().getNumber() +" - Done");
				//Flow to Honours screen for specific qualifications - Claudette 2018
				// continue from Name page next = address page or m&d page
				isPrevQual1 = dao.getQualCategory(stuRegForm.getStudent().getQual1());
				if (stuRegForm.getStudent().getQual2() != null && !"".equals(stuRegForm.getStudent().getQual2())){
					isPrevQual2 = dao.getQualCategory(stuRegForm.getStudent().getQual2());
				}
				//log.debug("ApplyForStudentNumberAction - Back - applyPrevQual isPrevQual1: " + isPrevQual1 + " isPrevQual2: " + isPrevQual2);
			} catch (Exception e) {
				//log.debug("ApplyForStudentNumberAction - Back - " + stuRegForm.getStudent().getNumber() +" - Exception / " + e);
				//log.debug("ApplyForStudentNumberAction - Back - N " + stuRegForm.getStudent().getNumber() + " Error="+e);
			}
			if (isPrevQual1 || isPrevQual2){
				//log.debug("ApplyForStudentNumberAction - Back - Goto applyPrevQual");
				prevPage =  "applyNDP";
			}else{
				//log.debug("ApplyForStudentNumberAction - Back - Goto applyQualification");
				prevPage =  "applyQualification";
			}

		}else if ("applyRetRadio".equalsIgnoreCase(page)) {
			prevPage = "applyRetContact";
		}else if ("applyRetDeclare".equalsIgnoreCase(page)) {
			stuRegForm.setAgree("");
			prevPage = "applyRetRadio";
		}else if ("applyNDP".equalsIgnoreCase(page)) {
			setDropdownListsPrev(request, form);
			prevPage = "applyPrevQual";
		}else if ("applyAPSSelect".equalsIgnoreCase(page)) {
			prevPage = "applyLoginSelect";
		}else if ("applyAPSSelect".equalsIgnoreCase(page)) {
			prevPage = "applyLoginSelect";
		}else if ("step3".equalsIgnoreCase(page)) {
			stuRegForm.getStudentApplication().setCompleteText(stripXSS(request.getParameter("completeText"), "CompeteText", "back", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), false));
			setDropdownListsStep2(request,stuRegForm);
			prevPage = "applyNewContact";
		}else if ("m30p1".equalsIgnoreCase(page)) {
			setDropdownListsStep3(request, stuRegForm);
			prevPage = "applyNewInfo1";
		}else if ("m30p2".equalsIgnoreCase(page)) {
			prevPage = "m30step1";
		}else if ("stepSelect".equalsIgnoreCase(page) || ("stepSelectNew".equalsIgnoreCase(page))) {
			//log.debug("ApplyForStudentNumberAction - Back (Select) - Page="+page);
			if (stuRegForm.getAdminStaff().isAdmin()){
				prevPage = "loginStaff";
			}else{
				if ("NSC".equalsIgnoreCase(stuRegForm.getSelectHEMain())){
					prevPage = "applyIDNumber";
				}else{
					prevPage = "applyAPSSelect";
				}
			}
		}
		//log.debug("ApplyForStudentNumberAction - Back - Prevpage=" + prevPage);
		return mapping.findForward(prevPage);
	}

	private void setDropdownListsLogin(HttpServletRequest request, ActionForm form) throws Exception{
		
		StudentRegistrationForm stuRegForm = (StudentRegistrationForm) form;
		
		//log.debug("ApplyForStudentNumberAction - setDropdownListLogin - AcademicYear="+stuRegForm.getStudent().getAcademicYear()+", LoginSelectMain="+stuRegForm.getLoginSelectMain()+", isAdmin="+stuRegForm.getAdminStaff().isAdmin());

		//log.debug("ApplyForStudentNumberAction - setDropdownListsLogin - Start");
		setUpYearList(request);
		setUpMonthList(request);
		setUpDayList(request);
		//log.debug("ApplyForStudentNumberAction - setDropdownListsLogin - End");
	}

	private void setDropdownListsPrev(HttpServletRequest request,ActionForm form) throws Exception{
		//log.debug("ApplyForStudentNumberAction - setDropdownListsPrev - --------------------------------------------------------");
		//log.debug("ApplyForStudentNumberAction - setDropdownListsPrev - Start");
		//log.debug("ApplyForStudentNumberAction - setDropdownListsPrev - --------------------------------------------------------");
		//log.debug("ApplyForStudentNumberAction - setDropdownListsPrev - setUpCountryList");
		setUpCountryListShort(request);
		//log.debug("ApplyForStudentNumberAction - setDropdownListsPrev - setUpUniversityList");
		setUpUniversityListShort(request);
		//log.debug("ApplyForStudentNumberAction - setDropdownListsPrev - setUpYearListStart");
		setUpYearListStart(request);
		//log.debug("ApplyForStudentNumberAction - setDropdownListsPrev - setUpYearListEnd");
		setUpYearListEnd(request);
		//log.debug("ApplyForStudentNumberAction - setDropdownListsPrev - --------------------------------------------------------");
		//log.debug("ApplyForStudentNumberAction - setDropdownListsPrev - End");
		//log.debug("ApplyForStudentNumberAction - setDropdownListsPrev - --------------------------------------------------------");
	}
	
	private void setUpYearListStart(HttpServletRequest request) throws Exception{

		ArrayList<LabelValueBean> list = new ArrayList<LabelValueBean>();
		int startYear = 1960; //Don't think student can be more than 100 years old

		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int count = 1;
		list.add(0,new org.apache.struts.util.LabelValueBean("Select Year","-1"));
		for (int i = year; i >= startYear; i--){
			list.add(count,new org.apache.struts.util.LabelValueBean(Integer.toString(i),Integer.toString(i)));
			count++;
		}
		request.setAttribute("yearStartlist",list);
	}
	
	private void setUpYearListEnd(HttpServletRequest request) throws Exception{
		ArrayList<LabelValueBean> list = new ArrayList<LabelValueBean>();
		int startYear = 1960; //Don't think student can be more than 100 years old

		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int count = 1;
		list.add(0,new org.apache.struts.util.LabelValueBean("Select First Year","-1"));
		for (int i = year; i >= startYear; i--){
			list.add(count,new org.apache.struts.util.LabelValueBean(Integer.toString(i),Integer.toString(i)));
			count++;
		}
		request.setAttribute("yearEndlist",list);
	}
	
	private void setDropdownListsStep1(HttpServletRequest request, ActionForm form) throws Exception{
		setUpDisabilityList(request);
		setUpTitleList(request);
	}

	private void setDropdownListsStep2(HttpServletRequest request,ActionForm form) throws Exception{
		setUpCountryList(request);
		//setUpPostalCodeList(request, form);
	}

	private void setDropdownListsStep3(HttpServletRequest request, ActionForm form) throws Exception{
		setUpHomeLanguageList(request);
		setUpNationalityList(request);
		setUpPopulationGroupList(request);
		setUpOccupationList(request);
		setUpEconomicSectorList(request);
		setPrevActivityList(request);
	}
	
	private void setDropdownListsAppeal(HttpServletRequest request, ActionForm form) throws Exception{
		
		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
		List<LabelValueBean> list = dao.getAppealDocTypes();
		list.add(0,new LabelValueBean("Select File type from Menu","-1"));
		request.setAttribute("filelist",list);
	}
	
	private void setUpTitleList(HttpServletRequest request) throws Exception{

		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
		List<LabelValueBean> list = dao.getTitles();
		list.add(0,new LabelValueBean("Select from Menu","-1"));
		request.setAttribute("titlelist",list);
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
	
	private void setUpDisabilityList(HttpServletRequest request) throws Exception{
		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
		List<LabelValueBean> list = dao.getDisabilities();
		list.add(0,new org.apache.struts.util.LabelValueBean("Not Applicable","1@Not Applicable"));
		request.setAttribute("disabilitylist",list);
	}
	
	private void setUpCountryList(HttpServletRequest request) throws Exception{

		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
		List<LabelValueBean> list = dao.getCountries();
		list.add(0,new org.apache.struts.util.LabelValueBean("SOUTH AFRICA","1015SOUTH AFRICA"));
		request.setAttribute("countrylist",list);
	}

	private void setUpCountryListShort(HttpServletRequest request) throws Exception{

		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
		List<LabelValueBean> list = dao.getCountriesShort();
		list.add(0,new org.apache.struts.util.LabelValueBean("Select from Menu","-1"));
		request.setAttribute("countrylistShort",list);
	}
	
	private void setUpHomeLanguageList(HttpServletRequest request) throws Exception{

		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
		List<LabelValueBean> list = dao.getLanguages();
		list.add(0,new LabelValueBean("Select from Menu","-1"));
		request.setAttribute("languagelist",list);
	}

	private void setUpNationalityList(HttpServletRequest request) throws Exception{

		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
		List<LabelValueBean> list = dao.getCountries();
		list.add(0,new LabelValueBean("Select from Menu","-1"));
		request.setAttribute("nationalitylist",list);
	}

	private void setUpPopulationGroupList(HttpServletRequest request) throws Exception{

		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
		List<LabelValueBean> list = dao.getPopulationGroups();
		list.add(0,new LabelValueBean("Select from Menu","-1"));
		request.setAttribute("populationlist",list);
	}

	private void setUpOccupationList(HttpServletRequest request) throws Exception{

		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
		List<LabelValueBean> list = dao.getOccupations();
		list.add(0,new LabelValueBean("Select from Menu","-1"));
		request.setAttribute("occupationlist",list);
	}

	private void setUpEconomicSectorList(HttpServletRequest request) throws Exception{

		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
		List<LabelValueBean> list = dao.getEconomicSectors();
		list.add(0,new LabelValueBean("Select from Menu","-1"));
		request.setAttribute("economicsectorlist",list);
	}

	private void setPrevActivityList(HttpServletRequest request) throws Exception{

		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
		List<LabelValueBean> list = dao.getPrevActivity();
		list.add(0,new LabelValueBean("Select from Menu","-1"));
		request.setAttribute("prevactivitylist",list);
	}

	private void setUpUniversityList(HttpServletRequest request) throws Exception{

		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
		List<LabelValueBean> list = dao.getUniversities();
		list.add(0,new LabelValueBean("Select from Menu","-1"));
		request.setAttribute("universitylist",list);
	}

	private void setUpUniversityListShort(HttpServletRequest request) throws Exception{

		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
		List<LabelValueBean> list = dao.getUniversitiesShort();
		list.add(0,new LabelValueBean("Select from Menu","-1"));
		request.setAttribute("universitylistShort",list);
	}
	
	private void setUpProvinceList(HttpServletRequest request) throws Exception{

		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
		List<LabelValueBean> list = dao.getProvinces();
		list.add(0,new LabelValueBean("Select from Menu","-1"));
		request.setAttribute("provincelist",list);
	}
	
	/*
	 * populates the Exam Centre dropdown. Uses JSON to return Key and Value list
	 */

	public ActionForward populateExamCentres(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		//log.debug("ApplyForStudentNumberAction - populateExamCentres - Start");

		StudentRegistrationForm stuRegForm = (StudentRegistrationForm) form;
		JSONObject examObj = new JSONObject();
		Map<String, String> mapExamCentres = new LinkedHashMap<String, String>();
		int keyCounter = 0;
		
		String examCentreType = stripXSS(request.getParameter("type"), "ExamCentreType", "popExamCen", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true);

		//log.debug("ApplyForStudentNumberAction - populateExamCentres - **************************************************************");
		//log.debug("ApplyForStudentNumberAction - populateExamCentres - examCentreType="+examCentreType);
		//log.debug("ApplyForStudentNumberAction - populateExamCentres - **************************************************************");
		

		// Query database
		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
		//log.debug("populateExamCentres - Type="+examCentreType);
			
			try{
				ArrayList<KeyValue> getValues = dao.getExamCentres(examCentreType);
				Iterator<KeyValue> it = getValues.iterator();
			
		    	while(it.hasNext()){
		    		KeyValue exam = (KeyValue) it.next();
		    		mapExamCentres.put(Integer.toString(keyCounter), exam.getKey()+"~"+exam.getValue());
		    		keyCounter++;
		    	}
		    	examObj.put("Exam",mapExamCentres);
	        }catch(Exception ex){
	        	examObj.put("Error","The populateExamCentres retrieval Failed! Please try again.");
	        }
		
		// Convert the map to JSON
		PrintWriter out = response.getWriter();
       	JSONObject jsonObject = JSONObject.fromObject(examObj);
       	//log.debug("ApplyForStudentNumberAction - populateExamCentres - Final - **************************************************************");
       	//log.debug("ApplyForStudentNumberAction - populateExamCentres - Final - jsonObject="+jsonObject.toString());
       	//log.debug("ApplyForStudentNumberAction - populateExamCentres - Final - **************************************************************");
       	out.write(jsonObject.toString());
       	out.flush();

		return null; //Returning null to prevent struts from interfering with Ajax/JSON
	}
	
	/*
	 * populates the School dropdown. Uses JSON to return Key and Value list
	 */

	public ActionForward populateSchools(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		//log.debug("ApplyForStudentNumberAction - populateSchools - Start");

		StudentRegistrationForm stuRegForm = (StudentRegistrationForm) form;
		JSONObject schoolObj = new JSONObject();
		Map<String, String> mapSchools = new LinkedHashMap<String, String>();
		String selectedProvince = "";
		int keyCounter = 0;
		int pos = 0;
		
		String province = stripXSS(request.getParameter("selectedProvince"), "SelectedProvince", "popSchools", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true);
		
		pos = province.indexOf("@");
		if (pos == -1){
			//Province not Found
			schoolObj.put("Error","The populateSchools retrieval Failed! Please try again.");
		}else{
			selectedProvince = province.substring(0,pos);

			//log.debug("ApplyForStudentNumberAction - populateSchools - **************************************************************");
			//log.debug("ApplyForStudentNumberAction - populateSchools - Matric Selected Province="+selectedProvince);
			//log.debug("ApplyForStudentNumberAction - populateSchools - **************************************************************");
		

			// Query database
			ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
			//log.debug("populateSchools - selectedProvince: " + selectedProvince);
			
			try{
				ArrayList<KeyValue> getValues = dao.getSchools(selectedProvince);
				Iterator<KeyValue> it = getValues.iterator();
			
		    	while(it.hasNext()){
		    		KeyValue school = (KeyValue) it.next();
		    		mapSchools.put(Integer.toString(keyCounter), school.getKey()+"@"+school.getValue());
		    		keyCounter++;
		    	}
		    	schoolObj.put("Schools",mapSchools);
	        }catch(Exception ex){
	        	schoolObj.put("Error","The populateSchools retrieval Failed! Please try again.");
	        }
		}
		
		// Convert the map to JSON
		PrintWriter out = response.getWriter();
       	JSONObject jsonObject = JSONObject.fromObject(schoolObj);
       	   //log.debug("ApplyForStudentNumberAction - populateSchools - Final - **************************************************************");
       	   //log.debug("ApplyForStudentNumberAction - populateSchools - Final - jsonObject="+jsonObject.toString());
       	   //log.debug("ApplyForStudentNumberAction - populateSchools - Final - **************************************************************");
       	out.write(jsonObject.toString());
       	out.flush();

		return null; //Returning null to prevent struts from interfering with Ajax/JSON
	}

	private GeneralItem getItem(String inputStr){
		GeneralItem item = new GeneralItem();
		int pos = 0;

		pos = inputStr.indexOf("@");
		item.setDesc(inputStr.substring(pos+1,inputStr.length()));
		item.setCode(inputStr.substring(0,pos));
		return item;
	}

	/**
	 * This method will create student number via function 126
	 *
	 * @param form    The RegistrationStudentForm object
	 */
	public String createStudentNr(StudentRegistrationForm stuRegForm, HttpServletRequest request) {

	  String result 		= "";
	  String queryResult 	= "";
	  String queryType 		= "INSERT";
	  boolean isError 		= false;

	  try{
		  //Set Random number to Tmp
		  stuRegForm.getStudent().setNumberTmp(stuRegForm.getStudent().getNumber());
		 /* Thread.sleep(Long.parseLong("1000"));*/ // if two audit trails were to be written simultaneously
	
		  // ---------------------------- READ F126 ------------------
		  Srrsa01sRegStudentPersDetail op = new Srrsa01sRegStudentPersDetail();
		 //i operListener opl = new operListener();
		 //i op.addExceptionListener(opl);
		  op.clear();
	
		  op.setInStudentAnnualRecordMkAcademicYear(Short.parseShort(String.valueOf(stuRegForm.getStudent().getAcademicYear())));
		  op.setInStudentAnnualRecordMkAcademicPeriod(Short.parseShort("1")); //Must be hardcoded to 1 !!!!
	
		  op.setInCsfClientServerCommunicationsClientVersionNumber((short) 3);
		  op.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
		  op.setInCsfClientServerCommunicationsAction("D");
		  op.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
		  op.setInWsUserNumber(99998);
	
		  //i we do not do display
		  //i  op.execute();
	
		  //i  if (opl.getException() != null) throw opl.getException();
		  //i  if (op.getExitStateType() < 3) throw new Exception(op.getExitStateMsg());
	
		  String errorMessage = op.getOutCsfStringsString500();
		  if(op.getOutCsfClientServerCommunicationsReturnCode()== 99){
			  // error returned
			  return errorMessage;
		  }
	
		  // can not display as warning messages also appear here
		  if (!"".equals(errorMessage)){
		    return errorMessage;
		  }
	
		  // ------------------------------- create F126 -------------------------
		  Srrsa01sRegStudentPersDetail op2 = new Srrsa01sRegStudentPersDetail();
		  operListener opl2 = new operListener();
		  op2.addExceptionListener(opl2);
		  op2.clear();
	
			ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
			
			// qualification code
			String tempQual1 = "";
			String tempSpec1 = "";
			String tempQual2 = "$$$$$";
			String tempSpec2 = "$$$";
			
			String checkQual1 = dao.getXMLSelected("qualCode1", "1",stuRegForm.getStudent().getNumberTmp(),stuRegForm.getStudent().getAcademicYear(),stuRegForm.getStudent().getAcademicPeriod(), "createStudentNr");
			String checkQual2 = dao.getXMLSelected("qualCode2", "2",stuRegForm.getStudent().getNumberTmp(),stuRegForm.getStudent().getAcademicYear(),stuRegForm.getStudent().getAcademicPeriod(), "createStudentNr");
			
			String checkSpec1 = dao.getXMLSelected("specCode1", "1",stuRegForm.getStudent().getNumberTmp(),stuRegForm.getStudent().getAcademicYear(),stuRegForm.getStudent().getAcademicPeriod(), "createStudentNr");
			String checkSpec2 = dao.getXMLSelected("specCode2", "2",stuRegForm.getStudent().getNumberTmp(),stuRegForm.getStudent().getAcademicYear(),stuRegForm.getStudent().getAcademicPeriod(), "createStudentNr");
			
			if (checkQual1 == null || stuRegForm.getStudent().getQual1() == null || !checkQual1.equalsIgnoreCase(stuRegForm.getStudent().getQual1())){
				//log.debug("ApplyForStudentNumberAction - createStudentNr - newQual1=[" + checkQual1 +"] <> SelQualCode1=["+stuRegForm.getStudent().getQual1()+"]");
				errorMessage = "Primary Qualification verification error";
				// error returned
				return errorMessage;
			}else{
				tempQual1 = stuRegForm.getStudent().getQual1();
			}

			if (stuRegForm.getStudent().getQual2() == null || !checkQual2.equalsIgnoreCase(stuRegForm.getStudent().getQual2().trim())){
				//log.debug("ApplyForStudentNumberAction - createStudentNr - newQual2=[" + checkQual2 +"] <> SelSpecCode1=["+stuRegForm.getStudent().getQual2()+"]");
				errorMessage = "Secondary Qualification verification error";
				// error returned
				return errorMessage;
			}else{
				if ("0".equals(stuRegForm.getStudent().getQual2()) || " ".equalsIgnoreCase(stuRegForm.getStudent().getQual2()) || "undefinded".equalsIgnoreCase(stuRegForm.getStudent().getQual2())){
					tempQual2 = "$$$$$";
				}else{
					tempQual2 = stuRegForm.getStudent().getQual2();
				}
			}
			
			if (stuRegForm.getStudent().getSpec1() == null || !checkSpec1.equalsIgnoreCase(stuRegForm.getStudent().getSpec1().trim())){
				//log.debug("ApplyForStudentNumberAction - createStudentNr - newSpec1=[" + checkSpec1 +"] <> SelSpecCode1=["+stuRegForm.getStudent().getSpec1()+"]");
				errorMessage = "Primary Spesialisation verification error";
				// error returned
				return errorMessage;
			}else{
				if ("0".equals(stuRegForm.getStudent().getSpec1()) || " ".equalsIgnoreCase(stuRegForm.getStudent().getSpec1())){
					tempSpec1 = " ";
				}else{
					tempSpec1 = stuRegForm.getStudent().getSpec1();
				}
			}
			if (stuRegForm.getStudent().getSpec2() == null || !checkSpec2.equalsIgnoreCase(stuRegForm.getStudent().getSpec2().trim())){
				//log.debug("ApplyForStudentNumberAction - createStudentNr - newSpec2=[" + checkSpec2 +"] <> SelSpecCode2=["+stuRegForm.getStudent().getSpec2()+"]");
				errorMessage = "Secondary Spesialisation verification error";
				// error returned
				return errorMessage;
			}else{
				if ("0".equals(stuRegForm.getStudent().getSpec2()) || " ".equalsIgnoreCase(stuRegForm.getStudent().getSpec2()) || "undefinded".equalsIgnoreCase(stuRegForm.getStudent().getSpec2())){
					tempSpec2 = "$$$";
				}else{
					tempSpec2 = stuRegForm.getStudent().getSpec2();
				}
			}
		  
			//log.debug("ApplyForStudentNumberAction - createStudentNr - after get Action tempQual1: " + tempQual1);
			//log.debug("ApplyForStudentNumberAction - createStudentNr - after get Action tempSpec1: " + tempSpec1);
			//log.debug("ApplyForStudentNumberAction - createStudentNr - after get Action tempQual2: " + tempQual2);
			//log.debug("ApplyForStudentNumberAction - createStudentNr - after get Action tempSpec2: " + tempSpec2);
			
		  //Johanet July2018 BRD - apply via RPL - 1.2 - value in stuRegForm.selectHEMain
		  //use InSblQualificationCsfStringsString1 for RPL value if stuRegForm.selectHEMain=RPL set to Y
		  op2.setInWsStudentApplicationCaoPaidFlag("N");	
		  if (stuRegForm.getSelectHEMain()!=null && stuRegForm.getSelectHEMain().equalsIgnoreCase("RPL")) {
			op2.setInWsStudentApplicationCaoPaidFlag("Y");			
		  }
			
		  //End July 2019 BRD RPL 1.2
		  op2.setInWsQualificationCode(tempQual1);
		  op2.setInStudentAnnualRecordSpecialityCode(tempSpec1);
		  //log.debug("ApplyForStudentNumberAction - createStudentNr -get Action Qual1-Spec1: " + tempQual1 +"-"+ tempSpec1);

		  op2.setInRotorMblFlagsCsfStringsString100(tempQual2+"-"+tempSpec2);
		  //log.debug("ApplyForStudentNumberAction - createStudentNr -get Action Qual2-Spec2: " + tempQual2+"-"+tempSpec2);
		  
		  //stuann
		  //log.debug("ApplyForStudentNumberAction - createStudentNr - getAcademicYear: " + String.valueOf(stuRegForm.getStudent().getAcademicYear()));
		  //log.debug("ApplyForStudentNumberAction - createStudentNr - getAcademicPeriod: " + String.valueOf("1"));
		  op2.setInStudentAnnualRecordMkAcademicYear(Short.parseShort(String.valueOf(stuRegForm.getStudent().getAcademicYear())));
		  op2.setInStudentAnnualRecordMkAcademicPeriod(Short.parseShort("1")); //Must be hardcoded to 1 !!!!
		  
		  op2.setInStudentAnnualRecordMkStudentNr(Integer.parseInt("99999999"));
		  
		  op2.setInStudentAnnualRecordMkDisabilityTypeCode(Short.parseShort(String.valueOf(stuRegForm.getStudent().getDisability().getCode())));//from screen
		  op2.setInStudentAnnualRecordFellowStudentFlag(stuRegForm.getStudent().getShareContactDetails());//from screen

		  op2.setInStudentAnnualRecordPreviousUnisaFlag("N");
		  if (stuRegForm.getStudent().getLastRegYear()!= null && !stuRegForm.getStudent().getLastRegYear().equalsIgnoreCase("")){
		     op2.setInStudentAnnualRecordPrevEducationalInstitutionYr(Short.parseShort(stuRegForm.getStudent().getLastRegYear()));
		  }else{
			  op2.setInStudentAnnualRecordPrevEducationalInstitutionYr((short) 0);
		  }
		  if (stuRegForm.getStudent().getOtherUniversity().getCode()!= null){
		     op2.setInStudentAnnualRecordMkOtherEducationalInstitCode(stuRegForm.getStudent().getOtherUniversity().getCode());
		  }else {
			  op2.setInStudentAnnualRecordMkOtherEducationalInstitCode("9997");
		  }
		  String prevInstCheck = "";
		  if (stuRegForm.getStudent().getPrevInstitution().getCode()!= null  && !"".equals(stuRegForm.getStudent().getPrevInstitution().getCode())){
			  prevInstCheck = stuRegForm.getStudent().getPrevInstitution().getCode();
		  }else {
			  prevInstCheck = "9997";
		  }
		  //log.debug("ApplyForStudentNumberAction - CreateStudentNr - N " + stuRegForm.getStudent().getNumberTmp() + " PrevInstCode: " + prevInstCheck);
		  op2.setInStudentAnnualRecordMkPrevEducationalInstitCode(prevInstCheck);
		  op2.setInStudentAnnualRecordRegistrationMethodCode("P");
		  op2.setInStudentAnnualRecordDespatchMethodCode("P");
		  op2.setInStudentAnnualRecordMkOccupationCode(stuRegForm.getStudent().getOccupation().getCode());//from screen
		  op2.setInStudentAnnualRecordMkEconomicSectorCode(stuRegForm.getStudent().getEconomicSector().getCode());//from screen
		  //op2.setInStudentAnnualRecordTefsaApplicationFlag("");
		  //op2.setInStudentAnnualRecordMatriculationBoardFlag("");
		  //op2.setInStudentAnnualRecordVotersRollFlag("");
		  //op2.setInStudentAnnualRecordAvailableForSrcFlag("");
		  op2.setInStudentAnnualRecordRegDeliveryMethod("E"); //from screen
		  op2.setInStudentAnnualRecordPipelineStudent(stuRegForm.getStudent().getMediaAccess());
		  op2.setInStudentAnnualRecordCommunicationMethod(stuRegForm.getStudentApplication().getLicensee());
	
		  op2.setInStudentAnnualRecordActivityLastYear(stuRegForm.getStudent().getPrevActivity().getCode());
	
		  Calendar calendar = Calendar.getInstance();
		  SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		  Date dueDate = formatter.parse(stuRegForm.getStudent().getBirthYear()+stuRegForm.getStudent().getBirthMonth()+stuRegForm.getStudent().getBirthDay());
		  calendar.setTime(dueDate);
		  op2.setInWsStudentBirthDate(calendar);
	
		  // student
		  op2.setInWsStudentNr(Integer.parseInt("99999999"));
		  op2.setInWsStudentSpecialCharacterFlag("N");
		  op2.setInWsStudentMkTitle(stuRegForm.getStudent().getTitle());
		  op2.setInWsStudentSurname(stuRegForm.getStudent().getSurname());
		  op2.setInWsStudentFirstNames(stuRegForm.getStudent().getFirstnames());
		  op2.setInWsStudentPreviousSurname(stuRegForm.getStudent().getMaidenName());
		  op2.setInWsStudentV3Initials(stuRegForm.getStudent().getInitials());
		  op2.setInWsStudentIdentityNr(stuRegForm.getStudent().getIdNumber());
		  op2.setInWsStudentGender(stuRegForm.getStudent().getGender());
		  op2.setInWsStudentMkNationality(stuRegForm.getStudent().getNationalty().getCode());//from screen
		  op2.setInWsStudentMkHomeLanguage(stuRegForm.getStudent().getHomeLanguage().getCode());//from screen
		  op2.setInWsStudentMkCorrespondenceLanguage("E");//Now Hardcoded to English - 2017
	
		  //if ("P".equalsIgnoreCase(stuRegForm.getStudent().getLastStatus())){
		  if (stuRegForm.getStudent().getLastStatus() != null && !stuRegForm.getStudent().getLastStatus().equalsIgnoreCase("P")){
			  op2.setInWsStudentPreviouslyPostGraduateFlag("Y");
		  }
		  else{
			  op2.setInWsStudentPreviouslyPostGraduateFlag("N");
		  }
		  op2.setInWsStudentMkCountryCode(stuRegForm.getStudent().getCountry().getCode());
		  if ("P".equals(stuRegForm.getStudent().getIdType())){
			  op2.setInWsStudentPassportNo(stuRegForm.getStudent().getPassportNumber());
		  }
		  if ("F".equals(stuRegForm.getStudent().getIdType())){
			  op2.setInWsStudentPassportNo(stuRegForm.getStudent().getForeignIdNumber());
		  }
	
		  // ethnic group
		  op2.setInWsEthnicGroupCode(stuRegForm.getStudent().getPopulationGroup().getCode());
		  op2.setInWsCountryCode(stuRegForm.getStudent().getCountry().getCode());
		  op2.setInWsCountryEngDescription(stuRegForm.getStudent().getCountry().getDesc());
	
		  // address control flag
		  //op2.setInAddressControlCodeCsfStringsString28(op2.getOutAddressControlCodeCsfStringsString28());
	
		  //address
		  op2.setInWsAddressAuditFlag("N");
		  op2.setInWsAddressAddressLine1(stuRegForm.getStudent().getPostalAddress().getLine1().toUpperCase());
		  op2.setInWsAddressAddressLine2(stuRegForm.getStudent().getPostalAddress().getLine2().toUpperCase());
		  op2.setInWsAddressAddressLine3(stuRegForm.getStudent().getPostalAddress().getLine3().toUpperCase());
		  op2.setInWsAddressAddressLine4(stuRegForm.getStudent().getPostalAddress().getLine4().toUpperCase());
		  op2.setInWsAddressAddressLine5(stuRegForm.getStudent().getPostalAddress().getLine5().toUpperCase());
		  op2.setInWsAddressAddressLine6(stuRegForm.getStudent().getPostalAddress().getLine6().toUpperCase());
		  if (stuRegForm.getStudent().getPostalAddress().getAreaCode()!= null && !stuRegForm.getStudent().getPostalAddress().getAreaCode().equalsIgnoreCase("")){
			  op2.setInWsAddressPostalCode(Short.parseShort(stuRegForm.getStudent().getPostalAddress().getAreaCode()));
		  }
		  op2.setInWsAddressFaxNumber(stuRegForm.getStudent().getFaxNr());//from screen
		  op2.setInWsAddressHomeNumber(stuRegForm.getStudent().getHomePhone());//from screen
		  op2.setInWsAddressWorkNumber(stuRegForm.getStudent().getWorkPhone());//from screen
		//op2.setInPOBoxCsfStringsString1(op.getOutPOBoxCsfStringsString1());
		  op2.setInForeignCountryNameWsCountryEngDescription(stuRegForm.getStudent().getCountry().getDesc().toUpperCase());
		  op2.setInWsAddressV2CellNumber(stuRegForm.getStudent().getCellNr());//from screen
		  op2.setInWsAddressV2EmailAddress(stuRegForm.getStudent().getEmailAddress());//from screen
		  op2.setInWsAddressV2CourierContactNo(stuRegForm.getStudent().getContactNr());
	
		  //courier address
		  op2.setInCourierWsAddressAddressLine1(stuRegForm.getStudent().getCourierAddress().getLine1().toUpperCase());
		  op2.setInCourierWsAddressAddressLine2(stuRegForm.getStudent().getCourierAddress().getLine2().toUpperCase());
		  op2.setInCourierWsAddressAddressLine3(stuRegForm.getStudent().getCourierAddress().getLine3().toUpperCase());
		  op2.setInCourierWsAddressAddressLine4(stuRegForm.getStudent().getCourierAddress().getLine4().toUpperCase());
		  op2.setInCourierWsAddressAddressLine5(stuRegForm.getStudent().getCourierAddress().getLine5().toUpperCase());
		  op2.setInCourierWsAddressAddressLine6(stuRegForm.getStudent().getCourierAddress().getLine6().toUpperCase());
		  if (stuRegForm.getStudent().getCourierAddress().getAreaCode()!= null && !stuRegForm.getStudent().getCourierAddress().getAreaCode().equalsIgnoreCase("")){
		     op2.setInCourierWsAddressPostalCode(Short.parseShort(stuRegForm.getStudent().getCourierAddress().getAreaCode()));
		  }
		  //op2.setInCourierWsAddressReferenceNo(op.getOutCourierWsAddressReferenceNo());
		  //op2.setInCourierWsAddressType(op.getOutCourierWsAddressType());
		  //op2.setInCourierWsAddressCategory(op.getOutCourierWsAddressCategory());
	
		  //physical
		  op2.setInPhysicalWsAddressAddressLine1(stuRegForm.getStudent().getPhysicalAddress().getLine1().toUpperCase());
		  op2.setInPhysicalWsAddressAddressLine2(stuRegForm.getStudent().getPhysicalAddress().getLine2().toUpperCase());
		  op2.setInPhysicalWsAddressAddressLine3(stuRegForm.getStudent().getPhysicalAddress().getLine3().toUpperCase());
		  op2.setInPhysicalWsAddressAddressLine4(stuRegForm.getStudent().getPhysicalAddress().getLine4().toUpperCase());
		  op2.setInPhysicalWsAddressAddressLine5(stuRegForm.getStudent().getPhysicalAddress().getLine5().toUpperCase());
		  op2.setInPhysicalWsAddressAddressLine6(stuRegForm.getStudent().getPhysicalAddress().getLine6().toUpperCase());
		  if (stuRegForm.getStudent().getPhysicalAddress().getAreaCode()!= null && !stuRegForm.getStudent().getPhysicalAddress().getAreaCode().equalsIgnoreCase("")){
			  op2.setInPhysicalWsAddressPostalCode(Short.parseShort(stuRegForm.getStudent().getPhysicalAddress().getAreaCode()));
		  }
		  //op2.setInPhysicalWsAddressReferenceNo(op.getOutPhysicalWsAddressReferenceNo());
		  //op2.setInPhysicalWsAddressType(op.getOutPhysicalWsAddressType());
		  //op2.setInPhysicalWsAddressCategory(op.getOutPhysicalWsAddressCategory());
	
		  // exam centre
		  op2.setInPrisonCsfStringsString2("OK");
		  op2.setInStudentExamCentreMkExamCentreCode(stuRegForm.getStudent().getExam().getExamCentre().getCode());
	
		  // application
		  op2.setInWsStudentApplicationApplicationMethod("I");
		  op2.setInWsStudentApplicationInternetApplicati("Y");
		  op2.setInWsStudentApplicationDocSuppliedAcadrec(stuRegForm.getStudentApplication().getDocformAcadrec());
		  op2.setInWsStudentApplicationDocSuppliedForm(stuRegForm.getStudentApplication().getDocformForm());
		  op2.setInWsStudentApplicationDocSuppliedId(stuRegForm.getStudentApplication().getDocformIdentity());
		  op2.setInWsStudentApplicationDocSuppliedMarriage(stuRegForm.getStudentApplication().getDocformMarriage());
		  op2.setInWsStudentApplicationDocSuppliedSchool(stuRegForm.getStudentApplication().getDocformSchool());
		  op2.setInWsStudentApplicationDocSuppliedToefl(stuRegForm.getStudentApplication().getDocformToefl());
		  op2.setInWsStudentApplicationApplyExemptions(stuRegForm.getStudentApplication().getApplyExemptions());
		  op2.setInWsStudentApplicationCareerCounselling(stuRegForm.getStudentApplication().getCareerCounsel());
		  op2.setInWsStudentApplicationFinancialAidEduloan(stuRegForm.getStudentApplication().getFinaidEduloan());
		  op2.setInWsStudentApplicationFinancialAidNsfas(stuRegForm.getStudentApplication().getFinaidNsfas());
	
		  // Unisa Staff
		  if (stuRegForm.getStudentApplication().getStaffCurrent() != null){
			  op2.setInStudentAnnualRecordCurrentStaff(stuRegForm.getStudentApplication().getStaffCurrent());
		  }
		  if (stuRegForm.getStudentApplication().getStaffDeceased() != null){
			  op2.setInStudentAnnualRecordDependantStaff(stuRegForm.getStudentApplication().getStaffDeceased());
		  }
		  if (stuRegForm.getStudentApplication().getPrisoner() != null){
			  op2.setInStudentAnnualRecordPrisoner(stuRegForm.getStudentApplication().getPrisoner());
		  }
		  // Complete Qualification?
		  if (stuRegForm.getStudentApplication().getCompleteQual() != null){
			  op2.setInStudentAnnualRecordQualCompleting(stuRegForm.getStudentApplication().getCompleteQual());
		  }
		  if (stuRegForm.getStudentApplication().getCompleteText() != null){
			  op2.setInStudentAnnualRecordQualExplain(stuRegForm.getStudentApplication().getCompleteText());
		  }
		  
		  if (stuRegForm.getStudentApplication().getHeAdmission() != null){
			  op2.setInWsStudentApplicationHeAdmission(stuRegForm.getStudentApplication().getHeAdmission());
		  }
		  if (stuRegForm.getStudentApplication().getMatricCertificate()!=null && !stuRegForm.getStudentApplication().getMatricCertificate().equals("")){
			  op2.setInWsStudentApplicationMatricCertificate(stuRegForm.getStudentApplication().getMatricCertificate());
		  }
		  if (stuRegForm.getStudentApplication().getMatricExamnr()!=null && !stuRegForm.getStudentApplication().getMatricExamnr().equals("")){
			  //log.debug("ApplyForStudentNumberAction - CreateStudentNr - Setting Matric number for Proxy=[" + stuRegForm.getStudentApplication().getMatricExamnr()+"]");
			  op2.setInWsStudentApplicationMatricExamNr(stuRegForm.getStudentApplication().getMatricExamnr());
		  }
		  if (stuRegForm.getStudentApplication().getMatricProvince().getCode()!=null && !stuRegForm.getStudentApplication().getMatricProvince().getCode().equals("")){
			  op2.setInWsStudentApplicationMatricProvince(Short.parseShort(stuRegForm.getStudentApplication().getMatricProvince().getCode()));
		  }
		  if (stuRegForm.getStudentApplication().getMatricSchool().getCode()!=null && !stuRegForm.getStudentApplication().getMatricSchool().getCode().equals("")){
			  op2.setInWsStudentMtrSchool(Integer.parseInt(stuRegForm.getStudentApplication().getMatricSchool().getCode()));
		  }
		  op2.setInWsStudentApplicationPrevInstSudnr(stuRegForm.getStudentApplication().getPrevinstStudnr());
		  if (stuRegForm.getStudentApplication().getMatricExamyear()!=null && !stuRegForm.getStudentApplication().getMatricExamyear().equalsIgnoreCase("")){
			  op2.setInWsStudentApplicationMatricYear(Short.parseShort(String.valueOf(stuRegForm.getStudentApplication().getMatricExamyear())));
		  }
	
		  // set all flags to yes
		  op2.setInChangedAddressCsfStringsString1("Y");
		  op2.setInChangedContactNumberCsfStringsString1("Y");
		  op2.setInChangedCourierAddressCsfStringsString1("Y");
		  op2.setInChangedPhysicalAddressCsfStringsString1("Y");
		  op2.setInChangedStudentAnnRecordCsfStringsString1("Y");
		  op2.setInChangedStudentCsfStringsString1("Y");
	
		  // set email
		  if (stuRegForm.getStudent().getEmailAddress() != null &&
			  "".equals(stuRegForm.getStudent().getEmailAddress()))
		  {
			  op2.setInEmailOrFaxCsfStringsString1("E");
	
		  }
		  else
		  {
			  op2.setInEmailOrFaxCsfStringsString1("P");
		  }
	
		  op2.setInEmailToCsfStringsString132(stuRegForm.getStudent().getEmailAddress());
		  op2.setInEmailFromCsfStringsString132("study-info@unisa.ac.za");
	
		  op2.setInCsfClientServerCommunicationsClientVersionNumber((short) 3);
		  op2.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
		  op2.setInCsfClientServerCommunicationsAction("A");
		  op2.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
		  op2.setInWsUserNumber(99998);
	
		  op2.execute();
	
		  if (opl2.getException() != null) throw opl2.getException();
		  if (op2.getExitStateType() < 3) throw new Exception(op2.getExitStateMsg());
	
		  //july2012-write to form to print on uniflow if necessary
		  stuRegForm.setString500back("");
	
		  //log.debug("ApplyForStudentNumberAction - createStudentNr - Srrsa01sRegStudentPersDetail (F126) - getOutCsfStringsString500="+op.getOutCsfStringsString500());

		  errorMessage = op2.getOutCsfStringsString500();
		  if(op2.getOutCsfClientServerCommunicationsReturnCode()== 99){
			  // error returned
			  //log.debug("ApplyForStudentNumberAction - CreateStudentNr - getOutCsfClientServerCommunicationsReturnCode=99 - N " + stuRegForm.getStudent().getNumber() + " errorMessage: " + errorMessage);
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
			    stuRegForm.setString500back(errorMessage);
			    // set application reference number
			    stuRegForm.getStudent().setNumber(Integer.toString(op2.getOutStudentAnnualRecordMkStudentNr()));// convert int to String
			    
			    //log.debug("ApplyForStudentNumberAction - createStudentNr - Random Stu Number="+stuRegForm.getStudent().getNumberTmp());
			    //log.debug("ApplyForStudentNumberAction - createStudentNr - New Student Numner="+op2.getOutStudentAnnualRecordMkStudentNr());
			    		

			    queryResult = dao.isSTUXML(stuRegForm.getStudent().getNumberTmp(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), "TempStu", "1", "createStudentNr");
			    //log.debug("ApplyForStudentNumberAction - createStudentNr - queryResult="+queryResult);
			    if (queryResult.toUpperCase().contains("ERROR")){
					isError = true;
				}else if (queryResult.toUpperCase().equalsIgnoreCase("TRUE")){
					queryType = "UPDATE";
				}
			    //log.debug("ApplyForStudentNumberAction - createStudentNr - isError="+isError+", queryType="+queryType);
				boolean isSuccess = false;
				int countTmpStu = 0;
				while (!isSuccess && countTmpStu < 5 && !isError){
					int nextRef = 0;
					String queryResultRef = dao.getSTUXMLRef(stuRegForm.getStudent().getNumberTmp(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), "TempStu", "1", "doSTUXML");
					//log.debug("ApplyForStudentNumberAction - createStudentNr - queryResultRef="+queryResultRef);
					if (queryResultRef.toUpperCase().contains("ERROR")){
						isError = true;
					}
					if (!isError){
						try{
							nextRef = Integer.parseInt(queryResultRef);
							nextRef++;
							//log.debug("ApplyForStudentNumberAction - doSTUXML - queryResultRef="+queryResultRef+", nextRef="+nextRef+", referenceType=TempStu, referenceValue=1, referenceData="+stuRegForm.getStudent().getNumber());
							result = dao.saveSTUXML(stuRegForm.getStudent().getNumberTmp(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), "TempStu", "1", nextRef, stuRegForm.getStudent().getNumber(), "createStudentNr", queryType);
							//log.debug("ApplyForStudentNumberAction - createStudentNr - result="+result);
							if (result.toUpperCase().equalsIgnoreCase("TRUE")){
								isSuccess = true;
							}else if (result.toUpperCase().contains("ERROR")){
								isError = true;
							}
						}catch(Exception e){
							log.warn("ApplyForStudentNumberAction doSTUXML - nextRef not Numeric - nextRef="+nextRef);
						}
					}
					countTmpStu++;
				}
				
			    stuRegForm.getStudent().setStuExist("newStu");
			    stuRegForm.setNumberBack(Integer.toString(op2.getOutStudentAnnualRecordMkStudentNr()));
			    stuRegForm.setEmailBack(op2.getOutEmailToCsfStringsString132());
	        	if (op2.getOutWsStudentApplicationApplicationDate()!= null){
	        		DateFormat strDate = new SimpleDateFormat( "dd-MM-yyyy" );
	        		stuRegForm.setTimeBack(strDate.format(op2.getOutWsStudentApplicationApplicationDate().getTime()));;
	        	}
		  }else{
			    // Error Occurred
		 		result = errorMessage;
				//log.debug("ApplyForStudentNumberAction - createStudentNr - Error Occurred - TMP " + stuRegForm.getStudent().getNumberTmp() + " NEW " + stuRegForm.getStudent().getNumber()+ " errorMessage: " + result);
		  }
	  }catch(Exception ex){
		  log.warn("ApplyForStudentNumberAction - CreateStudentNr - Error Occurred - TMP " + stuRegForm.getStudent().getNumber() + " errorMessage: " + result + " / " + ex);
		  log.warn("ApplyForStudentNumberAction - CreateStudentNr - Error Occurred - NEW " + stuRegForm.getStudent().getNumber() + " errorMessage: " + result + " / " + ex);
	  }
		//log.debug("ApplyForStudentNumberAction - CreateStudentNr - Final - TMP " + stuRegForm.getStudent().getNumberTmp());
		//log.debug("ApplyForStudentNumberAction - CreateStudentNr - Final - NEW " + stuRegForm.getStudent().getNumber());

	 return result;
	}

	public ActionForward appealUpload(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//log.debug("ApplyForStudentNumberAction - appealUpload - Start");
		
		//log.debug("ApplyForStudentNumberAction - appealUpload - Button Next="+request.getParameter("Next")+", Upload="+request.getParameter("Upload"));

		ActionMessages messages = new ActionMessages();
		StudentRegistrationForm stuRegForm = (StudentRegistrationForm) form;
		SavedDocDao docDao = new SavedDocDao();
		
		 //Reset web messages
		 stuRegForm.setWebUploadMsg("");
		 stuRegForm.setWebLoginMsg("");
		 stuRegForm.setWebLoginMsg2("");
	    
    	stuRegForm.setAppealSelect1(stripXSS(stuRegForm.getAppealSelect1(), "AppealSelect1", "appealUp", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
    	stuRegForm.setAppealSelect2(stripXSS(stuRegForm.getAppealSelect2(), "AppealSelect2", "appealUp", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
	    stuRegForm.setAppealText(stripXSS(request.getParameter("typeText").trim(), "AppealText", "appealUp", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
	
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
	    	//log.debug("ApplyForStudentNumberAction - appealUpload - Continue to Declaration............");
	    	return mapping.findForward("appealDeclare");
	    }else{
			//log.debug("ApplyForStudentNumberAction - appealUpload - Doing File Upload............");
			//log.debug("ApplyForStudentNumberAction - appealUpload - Checking Number of Files="+stuRegForm.getFileCount());
			//Check that no more than 5 files are  uploaded
	    	if (stuRegForm.getFileCount() > 4){ //Counting 0 to 4 
	    		messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "You have reached the maximum of 5 Files that may be uploaded."));
				addErrors(request, messages);
				//log.debug("ApplyForStudentNumberAction - appealUpload - Appeal File - Maximum 5 files reached");
				setDropdownListsAppeal(request,stuRegForm);
				return mapping.findForward("appealSelect");
	    	}else{
				//Validate File Name and Size
				FormFile file = stuRegForm.getFile();
				String typeSelect = stripXSS(request.getParameter("fileType"), "FileType", "appealUp", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true);
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
					//log.debug("ApplyForStudentNumberAction - appealUpload - Appeal File Type unknown");
					setDropdownListsAppeal(request,stuRegForm);
					return mapping.findForward("appealSelect");
				}
				if (stuRegForm.getFile() != null){
					//Get Motivation to re-display after upload
					//log.debug("ApplyForStudentNumberAction - appealUpload - AppealFile - Validate Optional File");
					if (file != null && file.getFileName() != null
							&& file.getFileName().trim().length() > 0) {
						//Enable this if you wish to validate for optional documents
						if( "-1".equals(docCode)){
							messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage", "Please select a document type from the list"));
							addErrors(request, messages);
							//log.debug("ApplyForStudentNumberAction - appealUpload - AppealFile - No File Type Selected");
							setDropdownListsAppeal(request,stuRegForm);
							return mapping.findForward("appealSelect");
						}else{
							//log.debug("ApplyForStudentNumberAction - appealUpload - AppealFile - File Type is OK");
						}
						int size = file.getFileSize();
						//log.debug("ApplyForStudentNumberAction - appealUpload - File: " + file.getFileName() + " & size = " + size);
						String readFileSize = readableFileSize(size);
						//log.debug("ApplyForStudentNumberAction - appealUpload - validate - N " + stuRegForm.getStudent().getNumber() + " Upload Optional: " + file.getFileName() + " size = " + readFileSize);
						if (readFileSize.contains("Error"))	{
							messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage", readFileSize));
							addErrors(request, messages);
							setDropdownListsAppeal(request,stuRegForm);
							return mapping.findForward("appealSelect");
						}else {
							if (size > 2097152) {// check file size,  not greater than 2 MB (2097152) measured in BYTES!
								//log.debug("ApplyForStudentNumberAction - appealUpload - Validate - N " + stuRegForm.getStudent().getNumber() + " Upload Optional: " + file.getFileName() + " size = " + readFileSize + " File size too large");
								messages.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("message.generalmessage", "File cannot be larger than 2MB (2048K). Your file is "+readFileSize));
								addErrors(request, messages);
								//log.debug("ApplyForStudentNumberAction - appealUpload - AppealFile - File larger than 2MB");
								setDropdownListsAppeal(request,stuRegForm);
								return mapping.findForward("appealSelect");
							}else {
								//log.debug("ApplyForStudentNumberAction - appealUpload - AppealFile - File Validation size OK...............");
							}
						}
						String name = file.getFileName().toLowerCase();
						String fileExtension = name.substring(name.lastIndexOf(".")+1);
						//log.debug("ApplyForStudentNumberAction - appealUpload - Appeal File - Uploading - fileExtension:" + fileExtension + "............");
						if (!name.endsWith(".doc") && !name.endsWith(".docx") && !name.endsWith(".pdf") && !name.endsWith(".tif") && !name.endsWith(".tiff")) {
							//log.debug("ApplyForStudentNumberAction validate - N " + stuRegForm.getStudent().getNumber() + " Upload Optional: " + file.getFileName() + " File wrong type ");
							messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage", "Only doc, docx, pdf, tif and tiff files are allowed"));
							addErrors(request, messages);
							//log.debug("ApplyForStudentNumberAction - appealUpload - AppealFile - Appeal File File wrong type");
							setDropdownListsAppeal(request,stuRegForm);
							return mapping.findForward("appealSelect");
						}else if (!"doc".equalsIgnoreCase(fileExtension) && !"docx".equalsIgnoreCase(fileExtension) && !"pdf".equalsIgnoreCase(fileExtension) && !"tif".equalsIgnoreCase(fileExtension) && !"tiff".equalsIgnoreCase(fileExtension)) {
								//log.debug("ApplyForStudentNumberAction validate - N " + stuRegForm.getStudent().getNumber() + " Upload Optional: " + file.getFileName() + " File wrong type ");
								messages.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("message.generalmessage", "Only doc, docx, pdf, tif and tiff files are allowed"));
								addErrors(request, messages);
								//log.debug("ApplyForStudentNumberAction - appealUpload - AppealFile - Appeal File File wrong type (2)");
								setDropdownListsAppeal(request,stuRegForm);
								return mapping.findForward("appealSelect");
						}else {
							//log.debug("ApplyForStudentNumberAction - appealUpload - AppealFile - File Validation Extension OK...............");
						}
					}
				} //End of Validation
				
				//Rename and Upload File
				if (file != null && file.getFileName() != null && file.getFileName().trim().length() > 0) {
					int size = file.getFileSize();
					//log.debug("ApplyForStudentNumberAction - appealUpload - Appeal File - Uploading - File size: " +size+"............");
						
					//save file
					//String dir = servlet.getServletContext().getRealPath("/upload");
					String appDir = ServerConfigurationService.getString("application.path");
					//log.debug("ApplyForStudentNumberAction - appealUpload - Appeal File - Uploading - FileBean - application.path:" + appDir + " ............");
						
					String stuGroupDir = stuRegForm.getStudent().getNumber().toString().substring(0, 1);
					String stuDir = stuRegForm.getStudent().getNumber().toString();
				    	
					String filePath = appDir + "/tmp/" + stuGroupDir + "/" + stuDir;
		
					//log.debug("ApplyForStudentNumberAction - appealUpload - Appeal File - Uploading - FileBean -  upload dest path:" + filePath + " ............");
						
					File folder = new File(filePath);
					if(!folder.exists()){
						folder.mkdirs();
					}
					
					String writeFile = "";
					writeFile = file.getFileName();
			
					StudentFile stuFile = new StudentFile();
					stuFile.setFileName(writeFile);
					
					//log.debug("ApplyForStudentNumberAction - appealUpload - Appeal File - Uploading - fileDesc Before Rename:" + writeFile + "............");
		
					//log.debug("ApplyForStudentNumberAction - appealUpload - Appeal File - Uploading - fileDesc Code:" + docCode + "............");
					
					//log.debug("ApplyForStudentNumberAction - appealUpload - Appeal File - Uploading - fileDesc Type:" + docType + "............");
						
					String fileExtension = writeFile.substring(writeFile.lastIndexOf(".")+1);
					//log.debug("ApplyForStudentNumberAction - appealUpload - Appeal File - Uploading - fileExtension:" + fileExtension + "............");
						
					String time = (new java.text.SimpleDateFormat("hhmmssss").format(new java.util.Date()).toString());
					//log.debug("ApplyForStudentNumberAction - appealUpload - Appeal File - Uploading - time:" + time + "............");
						
					String newFileName = stuRegForm.getStudent().getNumber()+"_"+ docType + "_"+ time +"." + fileExtension;
					stuFile.setNewFileName(newFileName);
					
					// set filename to changed name for workflow purposes
					//log.debug("ApplyForStudentNumberAction - appealUpload - Appeal File - Uploading - After Rename:" + newFileName + "............");
						
					String fullFileName = filePath+System.getProperty("file.separator")+newFileName;
					String success = "None";
					success = uploadFile(fullFileName, file.getInputStream());
		
					File testFile = new File(fullFileName);
					//log.debug("ApplyForStudentNumberAction - appealUpload - Appeal File - Check if File Exists="+testFile.exists());

					if(testFile.exists() && success.equalsIgnoreCase("Success")){
		
						//Add different file attributes to arrays for original file name, type and finally renamed file for workflow
						stuRegForm.getAppealSourceFiles().add(writeFile);
						stuRegForm.getAppealTypeFiles().add(docType);
						stuRegForm.getAppealWorkflowFiles().add(newFileName);
						//log.debug("ApplyForStudentNumberAction - appealUpload - Appeal File - List - Type="+docType+", Sournce="+writeFile+", Workflow="+newFileName);
						
						//log.debug("ApplyForStudentNumberAction - appealUpload - Appeal File - Appeal File exists - Saving to Database");
						SavedDoc optionDoc = new SavedDoc();
						optionDoc.setDocCode(docCode);
						optionDoc.setDocName(file.getFileName());
						//log.debug("ApplyForStudentNumberAction - appealUpload - Appeal File - Calling addSavedDoc 2 - optionDoc:" + optionDoc + "............");
						docDao.addSavedDocSTUAPD(optionDoc,"N",stuRegForm.getStudent().getNumber(),stuRegForm.getStudent().getAcademicYear(),stuRegForm.getStudent().getAcademicPeriod(),"2");
						docDao.addSavedDocSTUXML(optionDoc,"N",stuRegForm.getStudent().getNumber(),stuRegForm.getStudent().getAcademicYear(),stuRegForm.getStudent().getAcademicPeriod(),"2");
						//log.debug("ApplyForStudentNumberAction - appealUpload - Appeal File - Check File Number Before Exit="+stuRegForm.getFileCount());
						stuRegForm.setFileCount(stuRegForm.getFileCount() + 1);
						//log.debug("ApplyForStudentNumberAction - appealUpload - Appeal File - Check File Number After Exit="+stuRegForm.getFileCount());
					}else {
						//Uploaded file not found on Filesystem
						messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "File upload failed, was interrupted or could not be completed. Please try again."));
						addErrors(request, messages);
						//log.debug("ApplyForStudentNumberAction - appealUpload - Appeal File - Uploaded file not found on filesystem "+ success);
						setDropdownListsAppeal(request,stuRegForm);
						return mapping.findForward("appealSelect");
					}
				}else{
					if(request.getParameter("Next") == null){ //Only log null if doing upload, not continue
						//log.debug("ApplyForStudentNumberAction - appealUpload - Appeal File= - / " + stuRegForm.getStudent().getNumber() + " Uploading appeal filename is null" );
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
				//log.debug("ApplyForStudentNumberAction - writeWorkflowAppeal - No of: AppealTypeFiles" + stuRegForm.getAppealTypeFiles().size()+", AppealSourceFiles" + stuRegForm.getAppealSourceFiles().size()+", AppealWorkflowFiles" + stuRegForm.getAppealWorkflowFiles().size());
	
				if (stuRegForm.getAppealTypeFiles().size() > 0){
					for (int i = 0; i < stuRegForm.getAppealTypeFiles().size(); i++){
						//log.debug("ApplyForStudentNumberAction - appealUpload - Appeal File - Done - Type File "+i+" - File=" + stuRegForm.getAppealTypeFiles().get(i).toString());
					}
				}	
				if (stuRegForm.getAppealWorkflowFiles().size() > 0){
					for (int i = 0; i < stuRegForm.getAppealWorkflowFiles().size(); i++){
						//log.debug("ApplyForStudentNumberAction - appealUpload - Appeal File - Done - Workflow File "+i+" - File=" + stuRegForm.getAppealWorkflowFiles().get(i).toString());
					}
				}
				if (stuRegForm.getAppealSourceFiles().size() > 0){
					for (int i = 0; i < stuRegForm.getAppealSourceFiles().size(); i++){
						//log.debug("ApplyForStudentNumberAction - appealUpload - Appeal File - Done - Source File "+i+" - File=" + stuRegForm.getAppealSourceFiles().get(i).toString());
					}
				}
				*/
				
				request.setAttribute("listSourceFiles", stuRegForm.getAppealSourceFiles());
				request.setAttribute("listWorkflowFiles", stuRegForm.getAppealWorkflowFiles());
				//log.debug("ApplyForStudentNumberAction - appealUpload - Appeal File - Done");
				setDropdownListsAppeal(request,stuRegForm);
				return mapping.findForward("appealSelect");
	    	}
	    }
		
	}
	
	 private String uploadFile(String filePath,InputStream is) {
		 String result = "Error";
		  try {
			  
			  //log.debug("ApplyForStudentNumberAction - Entering uploadFile, Filepath:"+filePath);
			  
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
			  log.warn("ApplyForStudentNumberAction - Entering uploadFile - Exception="+ex);
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

		StudentRegistrationForm stuRegForm = new StudentRegistrationForm();

        try {
        	String appdir = ServerConfigurationService.getString("application.path");
			//log.debug("ApplyForStudentNumberAction - moveDocuments - application.path:" + appdir + " ............");
			
			String stuGroupDir = studentNr.substring(0, 1);
	    	String stuDir = studentNr;
        	String groupDir = ServerConfigurationService.getString("application.path") + "/tmp/" + stuGroupDir;
        	String sourceDir =  groupDir + "/" + stuDir;

			//log.debug("ApplyForStudentNumberAction - moveDocuments - move source path:" + sourceDir + " ............");

			File dest = new File(ServerConfigurationService.getString("application.path"));
        	//log.debug("ApplyForStudentNumberAction - moveDocuments - move dest path: " + dest + "/ ............");

        	File f = new File(sourceDir); // Source directory
        	//log.debug("ApplyForStudentNumberAction - moveDocuments - folder=" + f);
        	
			if (!f.exists()) {
				//log.debug("ApplyForStudentNumberAction - moveDocuments - move source path doesn't exist - Exiting");
			} else {
				//log.debug("ApplyForStudentNumberAction - moveDocuments - move source path exists");

   	        	FilenameFilter textFilter = new FilenameFilter() {
   	        		public boolean accept(File dir, String name) {
   		        		String lowercaseName = name.toLowerCase();
   		        		//log.debug("ApplyForStudentNumberAction - moveDocuments - lowercaseName: " + lowercaseName);
   		        		
   		        		if (lowercaseName.startsWith(studentNr)) {
   		        			//log.debug("ApplyForStudentNumberAction - moveDocuments - lowercaseName Starts with: " + studentNr + " : true");
   		        			return true;
   		        		}else if (lowercaseName.toString().contains(studentNr)) {
   		        			//log.debug("ApplyForStudentNumberAction - moveDocuments - lowercaseName Contains: " + studentNr + " : true");
   		        			return true;
   		        		} else {
    		        		//log.debug("ApplyForStudentNumberAction - moveDocuments - lowercaseName Doesn't Contain: " + studentNr + " : false");
        		        	return false;
   		        		}
   	        		}
   	        	};
        	        	
   	        	File[] files = f.listFiles(textFilter);
    	        	for (File file : files) {
    	        		//log.debug("ApplyForStudentNumberAction - moveDocuments - file: " + file);
        	        	File fileToBeCopied = new File(sourceDir + "/" + file);
        	        	//log.debug("ApplyForStudentNumberAction - moveDocuments - fileToBeCopied: " + fileToBeCopied);
        	        	//log.debug("ApplyForStudentNumberAction - moveDocuments - fileToBeCopied name: " + fileToBeCopied.getName());
        	        	File destFile = new File(dest + "/" + fileToBeCopied.getName());
        	        	//log.debug("ApplyForStudentNumberAction - moveDocuments - destFile: " + destFile);
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
                    		//log.debug("ApplyForStudentNumberAction - MoveDocuments - StuDir Empty, thus Deleted: " + testStuDir);
                    		//log.debug("ApplyForStudentNumberAction - moveDocuments " + stuRegForm.getStudent().getNumber() + " - StuDir Empty, thus Deleted: " + testStuDir);
                    	}else{
                    		//log.debug("ApplyForStudentNumberAction - MoveDocuments - StuDir not empty: " + testStuDir + ":" + children.length);
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
                    		//log.debug("ApplyForStudentNumberAction - MoveDocuments - GroupDir Empty, thus Deleted: " + testGroupDir);
                    		log.error("ApplyForStudentNumberAction MoveDocuments " + stuRegForm.getStudent().getNumber() + " - GroupDir Empty, thus Deleted: " + testGroupDir);
                    	}else{
                    		//log.debug("ApplyForStudentNumberAction - MoveDocuments - GroupDir not empty: " + testGroupDir + ":" + children.length);
                    	}
                    }
			}
        } catch (Exception ex) {
        		// write log?
        		throw ex;
    	} finally {
    	}

		//resetForm((StudentRegistrationForm) stuRegForm, "ApplyForStudentNumberAction - MoveDocuments");

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

	private boolean isNameValid(String anyname){
		boolean result = true;
		String test = "";
		int y=anyname.length();

		for (int i = 0; i < y-1; i++) {
			test= anyname.substring(i,i+1);
			if (test !=null && !"".equals(test)){
				if ("~".equals(test) || "<".equals(test) || ">".equals(test) || "@".equals(test) || "^".equals(test) || "1".equals(test) || "2".equals(test) || "3".equals(test) || "4".equals(test) || "5".equals(test) || "6".equals(test) || "7".equals(test) || "8".equals(test) || "9".equals(test) || "0".equals(test)){
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

	private boolean isPhoneNumber(String number){
		boolean result = true;
		String test = "";
		int y=number.length();

		for (int i = 0; i < y-1; i++) {
			test= number.substring(i,i+1);
//			test2=number.valueOf(i);
			if (test !=null && !"".equals(test) && !" ".equals(test) && !"-".equals(test) && !"/".equals(test) && !"+".equals(test)){
				if ("0".equals(test) || "1".equals(test) || "2".equals(test) || "3".equals(test) || "4".equals(test) || "5".equals(test) || "6".equals(test) || "7".equals(test) || "8".equals(test)  || "9".equals(test)){
//					//log.debug(test);
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

	public void resetForm(StudentRegistrationForm stuRegForm, String callAction)	throws Exception {

		//log.debug("ApplyForStudentNumberAction - resetForm - callAction="+callAction);
		// Clear fields
		
		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
		
		stuRegForm.resetFormFields();
		
		ArrayList<String> dateCheck = dao.validateClosingDate(stuRegForm.getStudent().getAcademicYear());
		if (!dateCheck.isEmpty()){ //Check Dates Array
			for (int i=0; i < dateCheck.size(); i++){
				//log.debug("ApplyForStudentNumberAction - Cancel - dateCheck="+dateCheck.get(i).toString());
				if ("WAPD".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPD(true);
				}else if ("WAPDOC".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPDOC(true);
				}else if ("WAPPAY".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPPAY(true);
				}else if ("WAPH".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPH(true);
				}else if ("WAPM".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPM(true);
				}else if ("WAPRH".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPRH(true);
				}else if ("WAPRU".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPRU(true);
				}else if ("WAPU".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPU(true);
				}else if ("WAPS".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPS(true);
				}else if ("WAPADMU".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPADMU(true);
				}else if ("WAPADMH".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPADMH(true);
				}else if ("WAPADMS".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPADMS(true);
				}else if ("WAPADMM".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPADMM(true);
				}else if ("WAPADMD".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPADMD(true);
				}else if ("WAPADMNEW".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPADMNEW(true);
				}else if ("WAPADMRET".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPADMRET(true);
				}else if ("WAPSTAT".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPSTAT(true);
				}else if ("WAPAPL".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPAPL(true);
				}else if ("WAPOFFER".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPOFFER(true);
				}else if ("WAPOFFICE".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPOFFICE(true);
				}
			}
		}
		stuRegForm.setAllowLogin(true);
		stuRegForm.getStudent().setAcademicYear(getCurrentAcademicYear());
		stuRegForm.getStudent().setAcademicPeriod(getCurrentAcademicPeriod());
		//log.debug("ApplyForStudentNumberaction - All StudentRegistrationForm Variables Cleared - Year="+stuRegForm.getStudent().getAcademicYear()+". Period="+stuRegForm.getStudent().getAcademicPeriod());
		
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
		//log.debug("ApplyForStudentNumberAction - getCurrentAcademicYear: " + acaYear);
		return acaYear;
	}

	public String getCurrentAcademicPeriod() throws Exception {
		String acaPeriod;

		Calendar cal = Calendar.getInstance();
		
		int month = cal.get(Calendar.MONTH)+1; //zero-based
		
		int currentYear = cal.get(Calendar.YEAR);
		int acaYear = Integer.parseInt(getCurrentAcademicYear());
		
		//log.debug("ApplyForStudentNumberAction - currentYear: " + currentYear+", acaYear: " + acaYear+", month="+month);
		
		if (currentYear < acaYear){
			acaPeriod = "1";
		}else{
			if (month < 8 && month > 3){
				acaPeriod = "2";
			}else{
				acaPeriod = "1";
			}
		}
		//log.debug("ApplyForStudentNumberAction - getCurrentAcademicPeriod: " + acaPeriod);
		return acaPeriod;
	}

	public Boolean validateEmailAddress(String emailAddress) {
		
		Pattern regexPattern = Pattern.compile("^[(a-zA-Z-0-9-\\_\\+\\.)]+@[(a-z-A-z)]+\\.[(a-zA-z)]{2,3}$");
		Matcher regMatcher = regexPattern.matcher(emailAddress);
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

	public Boolean validateMobileNumber(String mobileNumber) {
	    //regexPattern = Pattern.compile("^\\+[0-9]{2,3}+-[0-9]{10}$");
		Pattern regexPattern = Pattern.compile("^[+]{1}+-[0-9]{10}$");
		Matcher regMatcher = regexPattern.matcher(mobileNumber);
	    //if (regMatcher.find()) {
	    if(regMatcher.matches()){
	        return true;
	    } else {
	    	return false;
	    }
	}

	  /**
	   * This method validates a cellphone number
	   *
	   * @param student    The RegistrationStudent object
	   */
	  /**public String checkCellNumber(RegistrationStudent student) throws Exception {

	    Srcds01sMntStudContactDetail op = new Srcds01sMntStudContactDetail();
	    operListener opl = new operListener();
	    op.addExceptionListener(opl);
	    op.clear();

	    op.setInWsStudentNr(Integer.parseInt(stuRegForm.getStudent().getStudentNr()));
	    op.setInWsStudentMkCountryCode(stuRegForm.getStudent().getCountryCode());
	    op.setInStudentNameCsfStringsString50(stuRegForm.getStudent().getNationality());
	    op.setInWsAddressV2CellNumber(stuRegForm.getStudent().getTelephoneCell().trim());

	    op.setInCsfClientServerCommunicationsClientVersionNumber((short) 3);
	    op.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
	    op.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
	    op.setInCsfClientServerCommunicationsAction("WR");
	    op.execute();

	    if (opl.getException() != null) throw opl.getException();
	    if (op.getExitStateType() < 3) throw new Exception(op.getExitStateMsg());

	    return op.getOutCsfStringsString500();
	  }**/

	public String emptySession(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		StudentRegistrationForm stuRegForm = (StudentRegistrationForm) form;
		ActionMessages messages = new ActionMessages();
		
		//log.debug("emptySession");
		//log.debug("emptySession: " + stuRegForm.getFromPage());

		// Clear fields
		resetForm(stuRegForm, "ApplyForStudentNumberAction - emptySession");
		stuRegForm.setApplyType("");

		messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", "An empty Session was encountered or your session timed out. Please log on again."));
			addErrors(request, messages);
		return "loginSelect";
	}

	public ActionForward cancel(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		//log.debug("ApplyForStudentNumberAction - cancel");

		StudentRegistrationForm stuRegFormOld = (StudentRegistrationForm) form;
		boolean checkAdmin=false;
		if (stuRegFormOld.getAdminStaff().isAdmin()){
			checkAdmin = true;
		}
		resetForm(stuRegFormOld, "ApplyForStudentNumberAction Cancel(Action)");
		
		StudentRegistrationForm stuRegForm = (StudentRegistrationForm) form;
		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
		
		// Clear fields - Just in case
		resetForm(stuRegForm, "ApplyForStudentNumberAction Cancel(Action)");
		stuRegForm.setWebUploadMsg("");
		stuRegForm.setWebLoginMsg("");
		stuRegForm.setWebLoginMsg2("");

		ArrayList<String> dateCheck = dao.validateClosingDate(stuRegForm.getStudent().getAcademicYear());
		if (!dateCheck.isEmpty()){ //Check Dates Array
			for (int i=0; i < dateCheck.size(); i++){
				//log.debug("ApplyForStudentNumberAction - Cancel - dateCheck="+dateCheck.get(i).toString());
				if ("WAPD".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPD(true);
				}else if ("WAPDOC".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPDOC(true);
				}else if ("WAPPAY".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPPAY(true);
				}else if ("WAPH".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPH(true);
				}else if ("WAPM".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPM(true);
				}else if ("WAPRH".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPRH(true);
				}else if ("WAPRU".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPRU(true);
				}else if ("WAPU".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPU(true);
				}else if ("WAPS".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPS(true);
				}else if ("WAPADMU".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPADMU(true);
				}else if ("WAPADMH".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPADMH(true);
				}else if ("WAPADMS".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPADMS(true);
				}else if ("WAPADMM".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPADMM(true);
				}else if ("WAPADMD".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPADMD(true);
				}else if ("WAPADMNEW".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPADMNEW(true);
				}else if ("WAPADMRET".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPADMRET(true);
				}else if ("WAPSTAT".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPSTAT(true);
				}else if ("WAPAPL".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPAPL(true);
				}else if ("WAPOFFER".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPOFFER(true);
				}else if ("WAPOFFICE".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPOFFICE(true);
				}
			}
		}
		
		//log.debug("ApplyForStudentNumberAction - Cancel - validateClosingDate - AcademicYear="+ stuRegForm.getStudent().getAcademicYear() + ",  AcademicPeriod=" + stuRegForm.getStudent().getAcademicPeriod());

		stuRegForm.setWebLoginMsg("");
		stuRegForm.setWebLoginMsg2("");
		
		//log.debug("ApplyForStudentNumberAction -Cancel - validateClosingDate: AcademicYear="+ stuRegForm.getStudent().getAcademicYear() + ",  AcademicPeriod=" + stuRegForm.getStudent().getAcademicPeriod());

		//log.debug("ApplyForStudentNumberAction - Cancel - CheckAdmin="+checkAdmin);
		if (checkAdmin){
			//log.debug("ApplyForStudentNumberAction - Cancel - Return applyLoginAdmin");
			return mapping.findForward("loginStaff");
		}else{
			//log.debug("ApplyForStudentNumberAction - Cancel - Return applyLoginSelect");
			return mapping.findForward("loginSelect");
		}
	}
	
	public ActionForward quit(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		//log.debug("ApplyForStudentNumberAction - Quit");
		StudentRegistrationForm stuRegForm = new StudentRegistrationForm();
		boolean checkAdmin=false;
		if (stuRegForm.getAdminStaff().isAdmin()){
			checkAdmin = true;
		}
		
		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
		
		// Clear fields - Just in case
		resetForm(stuRegForm, "ApplyForStudentNumberAction Quit(Action)");
		
		stuRegForm.setWebUploadMsg("");
		stuRegForm.setWebLoginMsg("");
		stuRegForm.setWebLoginMsg2("");
	
		ArrayList<String> dateCheck = dao.validateClosingDate(stuRegForm.getStudent().getAcademicYear());
		if (!dateCheck.isEmpty()){ //Check Dates Array
			for (int i=0; i < dateCheck.size(); i++){
				//log.debug("ApplyForStudentNumberAction - Quit - dateCheck=["+dateCheck.get(i).toString()+"]");
				if ("WAPD".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPD(true);
				}else if ("WAPDOC".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPDOC(true);
				}else if ("WAPPAY".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPPAY(true);
				}else if ("WAPH".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPH(true);
				}else if ("WAPM".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPM(true);
				}else if ("WAPRH".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPRH(true);
				}else if ("WAPRU".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPRU(true);
				}else if ("WAPU".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPU(true);
				}else if ("WAPS".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPS(true);
				}else if ("WAPADMU".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPADMU(true);
				}else if ("WAPADMH".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPADMH(true);
				}else if ("WAPADMS".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPADMS(true);
				}else if ("WAPADMM".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPADMM(true);
				}else if ("WAPADMD".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPADMD(true);
				}else if ("WAPADMNEW".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPADMNEW(true);
				}else if ("WAPADMRET".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPADMRET(true);
				}else if ("WAPSTAT".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPSTAT(true);
				}else if ("WAPAPL".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPAPL(true);
				}else if ("WAPOFFER".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPOFFER(true);
				}else if ("WAPOFFICE".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPOFFICE(true);
				}
			}
		}
		
		//log.debug("ApplyForStudentNumberAction - Quit - validateClosingDate: AcademicYear="+ stuRegForm.getStudent().getAcademicYear() + ",  AcademicPeriod=" + stuRegForm.getStudent().getAcademicPeriod());
		
		//log.debug("ApplyForStudentNumberAction - Quit - CheckDates - WAPU="+stuRegForm.getStudent().isDateWAPU());
		//log.debug("ApplyForStudentNumberAction - Quit - CheckDates - WAPH="+stuRegForm.getStudent().isDateWAPH());
		//log.debug("ApplyForStudentNumberAction - Quit - CheckDates - WAPM="+stuRegForm.getStudent().isDateWAPM());
		//log.debug("ApplyForStudentNumberAction - Quit - CheckDates - WAPD="+stuRegForm.getStudent().isDateWAPD());
		
		//log.debug("ApplyForStudentNumberAction - Quit - CheckDates - WAPRU="+stuRegForm.getStudent().isDateWAPRU());
		//log.debug("ApplyForStudentNumberAction - Quit - CheckDates - WAPRH="+stuRegForm.getStudent().isDateWAPRH());
		
		//log.debug("ApplyForStudentNumberAction - Quit - CheckDates - WAPDOC="+stuRegForm.getStudent().isDateWAPDOC());
		//log.debug("ApplyForStudentNumberAction - Quit - CheckDates - WAPPAY="+stuRegForm.getStudent().isDateWAPPAY());
		
		//log.debug("ApplyForStudentNumberAction - Quit - CheckDates - WAPADMU="+stuRegForm.getStudent().isDateWAPADMU());
		//log.debug("ApplyForStudentNumberAction - Quit - CheckDates - WAPADMH="+stuRegForm.getStudent().isDateWAPADMH());
		//log.debug("ApplyForStudentNumberAction - Quit - CheckDates - WAPADMS="+stuRegForm.getStudent().isDateWAPADMS());
		//log.debug("ApplyForStudentNumberAction - Quit - CheckDates - WAPADMM="+stuRegForm.getStudent().isDateWAPADMM());
		//log.debug("ApplyForStudentNumberAction - Quit - CheckDates - WAPADMD="+stuRegForm.getStudent().isDateWAPADMD());
		//log.debug("ApplyForStudentNumberAction - Quit - CheckDates - WAPADMNEW="+stuRegForm.getStudent().isDateWAPADMNEW());
		//log.debug("ApplyForStudentNumberAction - Quit - CheckDates - WAPADMRET="+stuRegForm.getStudent().isDateWAPADMRET());
		
		//log.debug("ApplyForStudentNumberAction - Quit - CheckDates - WAPSTAT="+stuRegForm.getStudent().isDateWAPSTAT());
		//log.debug("ApplyForStudentNumberAction - Quit - CheckDates - WAPAPL="+stuRegForm.getStudent().isDateWAPAPL());
		//log.debug("ApplyForStudentNumberAction - Quit - CheckDates - WAPOFFER="+stuRegForm.getStudent().isDateWAPOFFER());
		//log.debug("ApplyForStudentNumberAction - Quit - CheckDates - WAPOFFICE="+stuRegForm.getStudent().isDateWAPOFFICE());
		
		//log.debug("ApplyForStudentNumberAction - Quit - CheckAdmin="+checkAdmin);
		if (checkAdmin){
			//log.debug("ApplyForStudentNumberAction - Quit - Return applyLoginAdmin");
			return mapping.findForward("loginStaff");
		}else{
			//log.debug("ApplyForStudentNumberAction - Quit - Return applyLoginSelect");
			return mapping.findForward("loginSelect");
		}
	}

	public String cancel(ActionForm form) throws Exception {

		//log.debug("String - cancel");
		
		StudentRegistrationForm stuRegForm = new StudentRegistrationForm();

		boolean checkAdmin=false;
		if (stuRegForm.getAdminStaff().isAdmin()){
			checkAdmin = true;
		}
		

		// Clear fields
		resetForm(stuRegForm, "ApplyForStudentNumberAction Cancel(String)");
		stuRegForm.setApplyType("");

		stuRegForm.setWebUploadMsg("");
		stuRegForm.setWebLoginMsg("");
		stuRegForm.setWebLoginMsg2("");
		
		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
		stuRegForm.getStudent().setAcademicYear(getCurrentAcademicYear());
		stuRegForm.getStudent().setAcademicPeriod(getCurrentAcademicPeriod());

		ArrayList<String> dateCheck = dao.validateClosingDate(stuRegForm.getStudent().getAcademicYear());
		if (!dateCheck.isEmpty()){ //Check Dates Array
			for (int i=0; i < dateCheck.size(); i++){
				//log.debug("ApplyForStudentNumberAction - Cancel - dateCheck="+dateCheck.get(i).toString());
				if ("WAPD".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPD(true);
				}else if ("WAPDOC".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPDOC(true);
				}else if ("WAPPAY".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPPAY(true);
				}else if ("WAPH".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPH(true);
				}else if ("WAPM".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPM(true);
				}else if ("WAPRH".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPRH(true);
				}else if ("WAPRU".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPRU(true);
				}else if ("WAPU".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPU(true);
				}else if ("WAPS".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPS(true);
				}else if ("WAPADMU".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPADMU(true);
				}else if ("WAPADMH".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPADMH(true);
				}else if ("WAPADMS".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPADMS(true);
				}else if ("WAPADMM".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPADMM(true);
				}else if ("WAPADMD".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPADMD(true);
				}else if ("WAPADMNEW".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPADMNEW(true);
				}else if ("WAPADMRET".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPADMRET(true);
				}else if ("WAPSTAT".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPSTAT(true);
				}else if ("WAPAPL".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPAPL(true);
				}else if ("WAPOFFER".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPOFFER(true);
				}else if ("WAPOFFICE".equalsIgnoreCase(dateCheck.get(i).toString())){
					stuRegForm.getStudent().setDateWAPOFFICE(true);
				}
			}
		}
		
		//log.debug("ApplyForStudentNumberAction - Cancel - validateClosingDate: AcademicYear="+ stuRegForm.getStudent().getAcademicYear() + ",  AcademicPeriod=" + stuRegForm.getStudent().getAcademicPeriod());

		//log.debug("ApplyForStudentNumberAction - Cancel(String) - CheckAdmin="+checkAdmin);
		if (checkAdmin){
			//log.debug("ApplyForStudentNumberAction - Cancel(String) - Return applyLoginAdmin");
			return "loginStaff";
		}else{
			//log.debug("ApplyForStudentNumberAction - Cancel(String) - Return applyLoginSelect");
			return "applyLoginSelect";
		}
	}

	public ActionForward cancelSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		//log.debug("Action - cancelSearch");
		StudentRegistrationForm stuRegForm = (StudentRegistrationForm) form;
		String nextPage = "applyNewAddress1";

		if ("search".equalsIgnoreCase(request.getParameter("page"))){
			stuRegForm.setSearchSuburb("");
			stuRegForm.setSearchTown("");
			stuRegForm.setSearchResult("");
		}

		if (stuRegForm.getSearchListIdentifier().equalsIgnoreCase("Postal")){
			setDropdownListsStep2(request, form);
			nextPage = "applyNewAddress1";
		}else if (stuRegForm.getSearchListIdentifier().equalsIgnoreCase("Physical")){
			nextPage = "applyNewAddress2";
		}else if (stuRegForm.getSearchListIdentifier().equalsIgnoreCase("Courier")){
			nextPage = "applyNewAddress3";
		}
		
		return mapping.findForward(nextPage);
	}

	public ActionForward unspecified(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{

		ActionMessages messages = new ActionMessages();
		//log.debug("ApplyForStudentNumber: unspecified method call - no value for parameter in request");
		messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", "An unspecified Session was encountered or your session timed out. Please log on again."));
			addErrors(request, messages);
		return mapping.findForward("loginSelect");
	}

	@SuppressWarnings("unused")
	public ActionForward getPostalCode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		StudentRegistrationForm ajaxForm = (StudentRegistrationForm)form;
		response.setContentType("text/text;charset=utf-8");
		response.setHeader("cache-control", "no-cache");
		PrintWriter out = response.getWriter();
		//out.println("Hello " + ajaxForm.getPostalCode());
		out.flush();
		return null;
	}

	public String postalCodeSelected(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionMessages messages = new ActionMessages();
		StudentRegistrationForm stuRegForm = (StudentRegistrationForm)form;
		String nextPage = "applyNewAddress1";
		// get postalcode details
		stuRegForm.setSearchResult(stripXSS(stuRegForm.getSearchResult(), "SearchResult", "posCodeSel", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));

		if (stuRegForm.getSearchResult() == null || "".equals(stuRegForm.getSearchResult()) || stuRegForm.getSearchResult().length() < 4){
			//log.debug("ApplyForStudentNumberAction postalCodeSelected - Postal code less than 4 characters: " + stuRegForm.getSearchResult());
			messages.add(ActionMessages.GLOBAL_MESSAGE,
			new ActionMessage("message.generalmessage", "Invalid postal code selection. Please try again."));
			addErrors(request, messages);
			return "searchforward";
		}else{
			try{
			
				String code = stuRegForm.getSearchResult().substring(0, 4);
				if (stuRegForm.getSearchListIdentifier().equalsIgnoreCase("Postal")){
					stuRegForm.getStudent().getPostalAddress().setAreaCode(code);
					
					if (stuRegForm.getSearchResult() != null && !"".equalsIgnoreCase(stuRegForm.getSearchResult().trim())){
						//log.debug("ApplyForStudentNumberAction - postalCodeSelected - SearchResult="+stuRegForm.getSearchResult());
						int numChars = stuRegForm.getSearchResult().length();
						numChars = numChars -1;
						String rAddress = stuRegForm.getSearchResult().substring(6, numChars); // Only after Box or Street - Example: "1865 B DOBSONVILLE: SOWETO"
						//log.debug("ApplyForStudentNumberAction - postalCodeSelected - Right-hand part of SearchResult="+rAddress);
						int pos = rAddress.indexOf(":");
						stuRegForm.setAddressSubResultPos(rAddress.substring(0,pos));
						//log.debug("ApplyForStudentNumberAction - postalCodeSelected - AddressSubResult="+stuRegForm.getAddressSubResultPos());
					}
					nextPage = "applyNewAddress1";
				}
				else if (stuRegForm.getSearchListIdentifier().equalsIgnoreCase("Physical")){
					stuRegForm.getStudent().getPhysicalAddress().setAreaCode(code);
					
					if (stuRegForm.getSearchResult() != null && !"".equalsIgnoreCase(stuRegForm.getSearchResult().trim())){
						//log.debug("ApplyForStudentNumberAction - postalCodeSelected - SearchResult="+stuRegForm.getSearchResult());
						int numChars = stuRegForm.getSearchResult().length();
						numChars = numChars -1;
						String rAddress = stuRegForm.getSearchResult().substring(6, numChars); // Only after Box or Street - Example: "1865 B DOBSONVILLE: SOWETO"
						//log.debug("ApplyForStudentNumberAction - postalCodeSelected - Right-hand part of SearchResult="+rAddress);
						int pos = rAddress.indexOf(":");
						stuRegForm.setAddressSubResultPhys(rAddress.substring(0,pos));
						//log.debug("ApplyForStudentNumberAction - postalCodeSelected - AddressSubResult="+stuRegForm.getAddressSubResultPhys());
					}
					nextPage = "applyNewAddress2";
				}
				else if (stuRegForm.getSearchListIdentifier().equalsIgnoreCase("Courier")){
					stuRegForm.getStudent().getCourierAddress().setAreaCode(code);
					
					if (stuRegForm.getSearchResult() != null && !"".equalsIgnoreCase(stuRegForm.getSearchResult().trim())){
						//log.debug("ApplyForStudentNumberAction - postalCodeSelected - SearchResult="+stuRegForm.getSearchResult());
						int numChars = stuRegForm.getSearchResult().length();
						numChars = numChars -1;
						String rAddress = stuRegForm.getSearchResult().substring(6, numChars); // Only after Box or Street - Example: "1865 B DOBSONVILLE: SOWETO"
						//log.debug("ApplyForStudentNumberAction - postalCodeSelected - Right-hand part of SearchResult="+rAddress);
						int pos = rAddress.indexOf(":");
						stuRegForm.setAddressSubResultCour(rAddress.substring(0,pos));
						//log.debug("ApplyForStudentNumberAction - postalCodeSelected - AddressSubResult="+stuRegForm.getAddressSubResultCour());
					}
					nextPage = "applyNewAddress3";
				}
			}catch(Exception e){
				//log.debug("ApplyForStudentNumberAction - postalCodeSelected - Postal code selection failed / "+ e);
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Postal code selection failed. Please try again."));
				addErrors(request, messages);
				return "searchforward";
			}
	
			stuRegForm.setSearchSuburb("");
			stuRegForm.setSearchTown("");
			stuRegForm.setSearchResult("");
		}
		return nextPage;
	}

	public ActionForward listPostalCode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		StudentRegistrationForm stuRegForm = (StudentRegistrationForm) form;
		ActionMessages messages = new ActionMessages();

		if ("".equals(stuRegForm.getSearchSuburb()) && "".equals(stuRegForm.getSearchTown())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
						"Enter either a Suburb or Town."));
			addErrors(request, messages);
			return mapping.findForward("searchforward");
		}

		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
		ArrayList<LabelValueBean> list = new ArrayList<LabelValueBean>();
			
		//log.debug("ApplyForStudentNumberAction - listPostalCode - SearchSuburb="+stuRegForm.getSearchSuburb()+", - SearchTown="+stuRegForm.getSearchTown()+", - SearchListIdentifier="+stuRegForm.getSearchListIdentifier());
		list=dao.getPostalCodes(stuRegForm.getSearchSuburb(), stuRegForm.getSearchTown(), stuRegForm.getSearchListIdentifier(), stuRegForm.getStudent().getPostalAddress().getBoxStreet());

		if(list.isEmpty()){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
						"No postal codes were found for the search criteria."));
			addErrors(request, messages);
			return mapping.findForward("searchforward");
		}else{
			//Add  list to request
			request.setAttribute("postalcodelist", list);

		}

		return mapping.findForward("searchforward");
	}

	private String getBoxOrStreet(String addressLine){

			String result = "S";

			if (addressLine.toUpperCase().contains("POSTNET SUITE")){
				result= "B";
			}else if (addressLine.toUpperCase().contains("POSNET SUITE")){
				result= "B";
			}else if (addressLine.toUpperCase().contains("PRIVATE BAG")){
				result= "B";
			}else if (addressLine.toUpperCase().contains("PRIVATEBAG")){
				result= "B";
			}else if (addressLine.toUpperCase().contains("BAG ")){
				result= "B";
			}else if (addressLine.toUpperCase().contains("POSBUS")){
				result= "B";
			}else if (addressLine.toUpperCase().contains("BUS ")){
				result= "B";
			}else if (addressLine.toUpperCase().contains("P O BOX")){
				result= "B";
			}else if (addressLine.toUpperCase().contains("BOX ")){
				result= "B";
			}else if (addressLine.toUpperCase().contains("POSBUS")){
				result= "B";
			}else if (addressLine.toUpperCase().contains("POSTE RESTANTE ")){
				result= "B";
			}else if (addressLine.toUpperCase().contains("POST RESTANTE ")){
				result= "B";
			}
			return result;
	}
	
	public String getMonth(int month) {
	    return new DateFormatSymbols().getMonths()[month-1];
	}

	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response, String searchField)
			throws Exception {

		StudentRegistrationForm stuRegForm = (StudentRegistrationForm)form;
		ActionMessages messages = new ActionMessages();
		
		stuRegForm.setSearchListIdentifier(searchField);
		//log.debug("ApplyForStudentNumberAction - search (Postal Code) - searchField="+searchField);
		
		if (stuRegForm.getSearchListIdentifier().equalsIgnoreCase("Postal")){
			if(stuRegForm.getStudent().getPostalAddress().getLine1().toString()== null || "".equals(stuRegForm.getStudent().getPostalAddress().getLine1().toString().trim())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
	        			new ActionMessage("message.generalmessage", "First Postal Address Line may not be empty. Enter P O Box or Street Address"));
				addErrors(request, messages);
				setDropdownListsStep2(request, stuRegForm);
				return mapping.findForward("applyNewAddress1");
			}else{
				String checkPostalStreet = "B";
			    checkPostalStreet = getBoxOrStreet(stuRegForm.getStudent().getPostalAddress().getLine1().toString());
			    if (checkPostalStreet != null && !"".equals(checkPostalStreet)){
			    	stuRegForm.getStudent().getPostalAddress().setBoxStreet(checkPostalStreet);
			    }
			}
		}
		return mapping.findForward("searchforward");
	}
	
	private void applyStatus(HttpServletRequest request,ActionForm form) throws Exception {

		StudentRegistrationForm stuRegForm = (StudentRegistrationForm)form;
		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
		ActionMessages messages = new ActionMessages();

		//log.debug("ApplyForStudentNumberAction - applyStatus");
		
		stuRegForm.setWebLoginMsg("");
		stuRegForm.setWebLoginMsg2("");
		
		try{
			stuRegForm.setSelQualCode1(dao.getStatusQual("Qual", "1", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod()));
			stuRegForm.setSelQualCode2(dao.getStatusQual("Qual", "2", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod()));
			stuRegForm.setSelSpecCode1(dao.getStatusQual("Spec", "1", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod()));
			stuRegForm.setSelSpecCode2(dao.getStatusQual("Spec", "2", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod()));
			
			//log.debug("ApplyForStudentNumberAction - applyStatus - " + stuRegForm.getStudent().getNumber() +" - Qual1     = " + stuRegForm.getSelQualCode1());
			//log.debug("ApplyForStudentNumberAction - applyStatus - " + stuRegForm.getStudent().getNumber() +" - Qual2     = " + stuRegForm.getSelQualCode2());
			//log.debug("ApplyForStudentNumberAction - applyStatus - " + stuRegForm.getStudent().getNumber() +" - Spec1     = " + stuRegForm.getSelSpecCode1());
			//log.debug("ApplyForStudentNumberAction - applyStatus - " + stuRegForm.getStudent().getNumber() +" - Spec2     = " + stuRegForm.getSelSpecCode2());

			if ((stuRegForm.getSelQualCode1() == null || "Not Found".equalsIgnoreCase(stuRegForm.getSelQualCode1())) && (stuRegForm.getSelQualCode2() == null || "Not Found".equalsIgnoreCase(stuRegForm.getSelQualCode2()))){
				//log.debug("ApplyForStudentNumberAction - applyStatus - QualCode1 NOT Found!!!");	
				messages.add(ActionMessages.GLOBAL_MESSAGE,
	        			new ActionMessage("message.generalmessage", "No Application or Qualification found"));
				addErrors(request, messages);
			}else{
				//Appeal & Offer Status
				String status1 = dao.getApplyStatus(stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), "1");
				//log.debug("ApplyForStudentNumberAction - applyStatus - Status1="+status1);
				stuRegForm.setQualStatusCode1(status1);
				if (status1 != null && !"".equals(status1)){
					stuRegForm.setQualStatus1(dao.getStatusDesc(status1));
					//log.debug("ApplyForStudentNumberAction - applyStatus - Status1 Desc="+stuRegForm.getQualStatus1());					
				}
				
				if (stuRegForm.getSelQualCode2() != null && !"Not Found".equalsIgnoreCase(stuRegForm.getSelQualCode2())){
					String status2 = dao.getApplyStatus(stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), "2");
					stuRegForm.setQualStatusCode2(status2);
					//log.debug("ApplyForStudentNumberAction - applyStatus - Status2="+status2);
					if (status2 != null && !"".equals(status2)){
						stuRegForm.setQualStatus2(dao.getStatusDesc(status2));
						//log.debug("ApplyForStudentNumberAction - applyStatus - Status2 Desc="+stuRegForm.getQualStatus2());
					}
				}
				
				//Offer Reason
				if ("AX".equalsIgnoreCase(stuRegForm.getQualStatusCode1())){
					String reason1 = dao.getDeclineReason(stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getStudent().getNumber(), stuRegForm.getSelQualCode1());
					stuRegForm.setQualStatus1Reason(reason1);
					//log.debug("ApplyForStudentNumberAction - applyStatus - Reason1="+reason1);
				}	
				
				if (stuRegForm.getSelQualCode2() != null && !"Not Found".equalsIgnoreCase(stuRegForm.getSelQualCode2())){
					if ("AX".equalsIgnoreCase(stuRegForm.getQualStatusCode2())){
						String reason2 = dao.getDeclineReason(stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getStudent().getNumber(), stuRegForm.getSelQualCode2());
						stuRegForm.setQualStatus2Reason(reason2);
						//log.debug("ApplyForStudentNumberAction - applyStatus - Reason2="+reason2);
					}
				}
				//Get Application Fee Information
				if ("NP".equalsIgnoreCase(stuRegForm.getQualStatusCode1()) || "NP".equalsIgnoreCase(stuRegForm.getQualStatusCode2())){
					Status status = new Status();
					status = dao.getStatusFee(stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod());
					
					//log.debug("ApplyForStudentNumberAction - applyStatus - dao PayDate="+status.getPayDate());
			    	//log.debug("ApplyForStudentNumberAction - applyStatus - dao PayFee ="+status.getPayFee());
			    	//log.debug("ApplyForStudentNumberAction - applyStatus - dao PayFull="+status.isPayFull());
			    	//log.debug("ApplyForStudentNumberAction - applyStatus - dao PayCom ="+status.getPayComment());
			    	
					stuRegForm.getStatus().setPayDate(status.getPayDate());
					stuRegForm.getStatus().setPayFee(status.getPayFee());
					stuRegForm.getStatus().setPayFull(status.isPayFull());
					stuRegForm.getStatus().setPayComment(status.getPayComment());
					
					//Debug
					//log.debug("ApplyForStudentNumberAction - applyStatus - form PayDate="+stuRegForm.getStatus().getPayDate());
			    	//log.debug("ApplyForStudentNumberAction - applyStatus - form PayFee ="+stuRegForm.getStatus().getPayFee());
			    	//log.debug("ApplyForStudentNumberAction - applyStatus - form PayFull="+stuRegForm.getStatus().isPayFull());
			    	//log.debug("ApplyForStudentNumberAction - applyStatus - form PayCom ="+stuRegForm.getStatus().getPayComment());
			    	
				}
			}
			
		}catch(Exception e){
			log.warn("ApplyForStudentNumberAction - applyStatus - crashed / " + e);
		}
	}
	
	@SuppressWarnings("unused")
	private void applyOfferStatus(HttpServletRequest request,ActionForm form) throws Exception {

		StudentRegistrationForm stuRegForm = (StudentRegistrationForm)form;
		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
		ActionMessages messages = new ActionMessages();

		//log.debug("ApplyForStudentNumberAction - applyOfferStatus - Start");
		
		stuRegForm.setWebLoginMsg("");
		stuRegForm.setWebLoginMsg2("");
		
		try{
			boolean isQualOffer1 = false;
			boolean isQualOffer2 = false;
			isQualOffer1 = dao.getOfferStatus(stuRegForm.getStudent().getNumber(),stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), "1", "applyOfferStatus");
			isQualOffer2 = dao.getOfferStatus(stuRegForm.getStudent().getNumber(),stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), "2", "applyOfferStatus");

			//log.debug("ApplyForStudentNumberAction - applyOfferStatus - isQualOffer1="+isQualOffer1);
			//log.debug("ApplyForStudentNumberAction - applyOfferStatus - isQualOffer2="+isQualOffer2);
			
			if (isQualOffer1){
				stuRegForm.setOfferQual1(dao.getStatusQual("Qual", "1", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod()));
				stuRegForm.setOfferSpec1(dao.getStatusQual("Spec", "1", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod()));
			}
			if (isQualOffer2){
				stuRegForm.setOfferQual2(dao.getStatusQual("Qual", "2", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod()));
				stuRegForm.setOfferSpec2(dao.getStatusQual("Spec", "2", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod()));
			}
			//log.debug("ApplyForStudentNumberAction - applyOfferStatus - After - " + stuRegForm.getStudent().getNumber() +" - Qual1     = " + stuRegForm.getOfferQual1());
			//log.debug("ApplyForStudentNumberAction - applyOfferStatus - After - " + stuRegForm.getStudent().getNumber() +" - Spec1     = " + stuRegForm.getOfferSpec1());

			//log.debug("ApplyForStudentNumberAction - applyOfferStatus - After - " + stuRegForm.getStudent().getNumber() +" - Qual2     = " + stuRegForm.getOfferQual2());
			//log.debug("ApplyForStudentNumberAction - applyOfferStatus - After - " + stuRegForm.getStudent().getNumber() +" - Spec2     = " + stuRegForm.getOfferSpec2());

			//Appeal & Offer Status
			stuRegForm.setQualStatusCode1("");
			stuRegForm.setQualStatusCode2("");
			stuRegForm.setQualStatus1("");
			stuRegForm.setQualStatus2("");
			stuRegForm.setQualStatus1Reason("");
			stuRegForm.setQualStatus2Reason("");
			if (isQualOffer1 && (stuRegForm.getOfferQual1() != null && !"Not Found".equalsIgnoreCase(stuRegForm.getOfferQual1()))){
				String status1 = dao.getApplyStatus(stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), "1");
				//log.debug("ApplyForStudentNumberAction - applyOfferStatus - Status1="+status1);
				stuRegForm.setQualStatusCode1(status1);
				if (status1 != null && !"".equals(status1)){
					stuRegForm.setQualStatus1(dao.getStatusDesc(status1));
					//log.debug("ApplyForStudentNumberAction - applyOfferStatus - Status1 Desc="+stuRegForm.getQualStatus1());					
				}
				//Offer Reason
				if ("AX".equalsIgnoreCase(stuRegForm.getQualStatusCode1()) || "EX".equalsIgnoreCase(stuRegForm.getQualStatusCode1()) || "TX".equalsIgnoreCase(stuRegForm.getQualStatusCode1())){
					String reason1 = dao.getDeclineReason(stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getStudent().getNumber(), stuRegForm.getSelQualCode1());
					stuRegForm.setQualStatus1Reason(reason1);
					//log.debug("ApplyForStudentNumberAction - applyOfferStatus - Reason1="+reason1);
				}	
			}else{
				//log.debug("ApplyForStudentNumberAction - applyOfferStatus - QualCode1 NOT Found!!!");	
			}
			
			if (isQualOffer2 && (stuRegForm.getOfferQual2() != null && !"Not Found".equalsIgnoreCase(stuRegForm.getOfferQual2()))){
				String status2 = dao.getApplyStatus(stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), "2");
				stuRegForm.setQualStatusCode2(status2);
				//log.debug("ApplyForStudentNumberAction - applyOfferStatus - Status2="+status2);
				if (status2 != null && !"".equals(status2)){
					stuRegForm.setQualStatus2(dao.getStatusDesc(status2));
					//log.debug("ApplyForStudentNumberAction - applyOfferStatus - Status2 Desc="+stuRegForm.getQualStatus2());
				}
				//Offer Reason
				if ("AX".equalsIgnoreCase(stuRegForm.getQualStatusCode2()) || "EX".equalsIgnoreCase(stuRegForm.getQualStatusCode2()) || "TX".equalsIgnoreCase(stuRegForm.getQualStatusCode2())){
						String reason2 = dao.getDeclineReason(stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getStudent().getNumber(), stuRegForm.getSelQualCode2());
						stuRegForm.setQualStatus2Reason(reason2);
						//log.debug("ApplyForStudentNumberAction - applyOfferStatus - Reason2="+reason2);
				}
			}else{
				//log.debug("ApplyForStudentNumberAction - applyOfferStatus - QualCode2 NOT Found!!!");	
			}
		}catch(Exception e){
			log.warn("ApplyForStudentNumberAction - applyOfferStatus - crashed / " + e);
		}
		//log.debug("ApplyForStudentNumberAction - applyOfferStatus - Done");
	}
	
	public String applyOffer(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		StudentRegistrationForm stuRegForm = (StudentRegistrationForm)form;
		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
		ActionMessages messages = new ActionMessages();

		//log.debug("ApplyForStudentNumberAction - applyOffer - Start");
		
		stuRegForm.getStudent().setQual1(dao.vrfyNewQualShort("Qual","1",stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod()));
		stuRegForm.getStudent().setQual2(dao.vrfyNewQualShort("Qual","2",stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod()));
		
		//log.debug("ApplyForStudentNumberAction - applyOffer check - Qual1="+stuRegForm.getStudent().getQual1());
		//log.debug("ApplyForStudentNumberAction - applyOffer check - Qual2="+stuRegForm.getStudent().getQual2());

		try{
			stuRegForm.getStudentApplication().setRadioOfferQual1(stripXSS(stuRegForm.getStudentApplication().getRadioOfferQual1(), "RadioOfferQual1", "applyOffer", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
			stuRegForm.getStudentApplication().setRadioOfferQual2(stripXSS(stuRegForm.getStudentApplication().getRadioOfferQual2(), "RadioOfferQual2", "applyOffer", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
			
			//log.debug("ApplyForStudentNumberAction - applyOffer check - Qual1 Radio="+stuRegForm.getStudentApplication().getRadioOfferQual1());
			//log.debug("ApplyForStudentNumberAction - applyOffer check - Qual2 Radio="+stuRegForm.getStudentApplication().getRadioOfferQual2());

			boolean isRadio1 = false;
			boolean isRadio2 = false;
			
			if ("Y".equalsIgnoreCase(stuRegForm.getStudentApplication().getRadioOfferQual1()) || "N".equalsIgnoreCase(stuRegForm.getStudentApplication().getRadioOfferQual1())){
				isRadio1 = true;
			}
			if ("Y".equalsIgnoreCase(stuRegForm.getStudentApplication().getRadioOfferQual2()) || "N".equalsIgnoreCase(stuRegForm.getStudentApplication().getRadioOfferQual2())){
				isRadio2 = true;
			}
			
			if (!isRadio1 && !isRadio2){
				//log.debug("ApplyForStudentNumberAction - applyOffer check - Radio1 & Radio2 Not Selected?="+isRadio1+" & "+isRadio2);

				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Please select an offer of enrolment to accept or decline or cancel to exit"));
				addErrors(request, messages);
				return "applyOffer";
			}
			
			//int updResult = dao.updateOffer(stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getOfferRadio());
		
			//log.debug("ApplyForStudentNumberAction - applyOffer check - Qual1 Radio="+stuRegForm.getStudentApplication().getRadioOfferQual1());
			//log.debug("ApplyForStudentNumberAction - applyOffer check - Qual2 Radio="+stuRegForm.getStudentApplication().getRadioOfferQual2());

			if (isRadio1 || isRadio2){
				//log.debug("ApplyForStudentNumberAction - applyOffer In Proxy Call");
				//Use STAAE05H Proxy to save Offer
				Staae05sAppAdmissionEvaluator op = new Staae05sAppAdmissionEvaluator();
				operListener opl = new operListener();
				op.addExceptionListener(opl);
				op.clear();
				
				if (isRadio1){
					//log.debug("ApplyForStudentNumberAction - applyOffer - Qual1="+stuRegForm.getOfferQual1()+", Qual1 Radio="+stuRegForm.getStudentApplication().getRadioOfferQual1());
					//Student Input fields
					op.setInCsfClientServerCommunicationsClientVersionNumber((short) 3);
					op.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
					op.setInCsfClientServerCommunicationsAction("OU"); //Offer Update
					op.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
					op.setInWsUserNumber(99998);
					op.setInWebStuApplicationQualMkStudentNr(Integer.parseInt(stuRegForm.getStudent().getNumber()));
					op.setInWebStuApplicationQualAcademicYear((short) Integer.parseInt(stuRegForm.getStudent().getAcademicYear()));
					op.setInWebStuApplicationQualApplicationPeriod((short) Integer.parseInt(stuRegForm.getStudent().getAcademicPeriod()));
					//op.setInWsQualificationCode(stuRegForm.getStudent().getQual1());
					String qual1 = (stuRegForm.getOfferQual1().substring(0,5));
					//log.debug("ApplyForStudentNumberAction - applyOffer - Qual1 Offer Input="+qual1);
					op.setInWebStuApplicationQualNewQual(qual1);
					op.setInWebStuApplicationQualChoiceNr((short) 1);
					op.setInWebStuApplicationQualOfferAccepted(stuRegForm.getStudentApplication().getRadioOfferQual1());
				
					//log.debug("ApplyForStudentNumberAction - applyOffer - (Staae05sAppAdmissionEvaluator) - 1 - Student Number         ="+stuRegForm.getStudent().getNumber());
					//log.debug("ApplyForStudentNumberAction - applyOffer - (Staae05sAppAdmissionEvaluator) - 1 - Academic Year          ="+stuRegForm.getStudent().getAcademicYear());
					//log.debug("ApplyForStudentNumberAction - applyOffer - (Staae05sAppAdmissionEvaluator) - 1 - Academic Period        ="+stuRegForm.getStudent().getAcademicPeriod());
					//log.debug("ApplyForStudentNumberAction - applyOffer - (Staae05sAppAdmissionEvaluator) - 1 - Qualification Accepted ="+stuRegForm.getOfferQual1());
					//log.debug("ApplyForStudentNumberAction - applyOffer - (Staae05sAppAdmissionEvaluator) - 1 - Qualification Radio    ="+stuRegForm.getStudentApplication().getRadioOfferQual1());
					
					op.execute();
		
					if (opl.getException() != null) throw opl.getException();
					if (op.getExitStateType() < 3) throw new Exception(op.getExitStateMsg());
		
					//log.debug("ApplyForStudentNumberAction - applyOffer - 1 - After Execute");
					String opResult1 = op.getOutCsfStringsString500();
					//log.debug("ApplyForStudentNumberAction - applyOffer - 1 - opResult: " + opResult1);
					op.clear();
					if (!opResult1.contains("generated")){
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage", stuRegForm.getOfferQual1()+" - "+ opResult1));
						addErrors(request, messages);
						applyOfferStatus(request, stuRegForm);
						if ((stuRegForm.getOfferQual1() == null || "".equalsIgnoreCase(stuRegForm.getOfferQual1())) && (stuRegForm.getOfferQual2() == null || "".equalsIgnoreCase(stuRegForm.getOfferQual2()))){
							//log.debug("ApplyForStudentNumberAction - applyOffer - Qual1 Done - Goto applyOfferConfirm");
							return "applyOfferConfirm";
						}else{
							return "applyOffer";
						}
					}
					
				}
								
				if (isRadio2){
					//log.debug("ApplyForStudentNumberAction - applyOffer - Qual2="+stuRegForm.getOfferQual2()+", Qual2 Radio="+stuRegForm.getStudentApplication().getRadioOfferQual2());
					//Student Input fields
					op.setInCsfClientServerCommunicationsClientVersionNumber((short) 3);
					op.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
					op.setInCsfClientServerCommunicationsAction("OU"); //Offer Update
					op.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
					op.setInWsUserNumber(99998);
					op.setInWebStuApplicationQualMkStudentNr(Integer.parseInt(stuRegForm.getStudent().getNumber()));
					op.setInWebStuApplicationQualAcademicYear((short) Integer.parseInt(stuRegForm.getStudent().getAcademicYear()));
					op.setInWebStuApplicationQualApplicationPeriod((short) Integer.parseInt(stuRegForm.getStudent().getAcademicPeriod()));
					//op.setInWsQualificationCode(stuRegForm.getStudent().getQual2());
					String qual2 = (stuRegForm.getOfferQual2().substring(0,5));
					//log.debug("ApplyForStudentNumberAction - applyOffer - Qual2 Offer Input="+qual2);
					op.setInWebStuApplicationQualNewQual(qual2);
					op.setInWebStuApplicationQualChoiceNr((short) 2);
					op.setInWebStuApplicationQualOfferAccepted(stuRegForm.getStudentApplication().getRadioOfferQual2());
				
					//log.debug("ApplyForStudentNumberAction - applyOffer - (Staae05sAppAdmissionEvaluator) - 2 - Student Number         ="+stuRegForm.getStudent().getNumber());
					//log.debug("ApplyForStudentNumberAction - applyOffer - (Staae05sAppAdmissionEvaluator) - 2 - Academic Year          ="+stuRegForm.getStudent().getAcademicYear());
					//log.debug("ApplyForStudentNumberAction - applyOffer - (Staae05sAppAdmissionEvaluator) - 2 - Academic Period        ="+stuRegForm.getStudent().getAcademicPeriod());
					//log.debug("ApplyForStudentNumberAction - applyOffer - (Staae05sAppAdmissionEvaluator) - 2 - Qualification Accepted ="+stuRegForm.getOfferQual2());
					//log.debug("ApplyForStudentNumberAction - applyOffer - (Staae05sAppAdmissionEvaluator) - 2 - Qualification Radio    ="+stuRegForm.getStudentApplication().getRadioOfferQual2());
										
					op.execute();
		
					if (opl.getException() != null) throw opl.getException();
					if (op.getExitStateType() < 3) throw new Exception(op.getExitStateMsg());
		
					//log.debug("ApplyForStudentNumberAction - applyOffer - 2 - After Execute");
					String opResult2 = op.getOutCsfStringsString500();
					//log.debug("ApplyForStudentNumberAction - applyOffer - 2 - opResult: " + opResult2);
					op.clear();
					if (!opResult2.contains("generated")){
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage", stuRegForm.getOfferQual2()+" - "+ opResult2));
						addErrors(request, messages);
						applyOfferStatus(request, stuRegForm);
						if ((stuRegForm.getOfferQual1() == null || "".equalsIgnoreCase(stuRegForm.getOfferQual1())) && (stuRegForm.getOfferQual2() == null || "".equalsIgnoreCase(stuRegForm.getOfferQual2()))){
							//log.debug("ApplyForStudentNumberAction - applyOffer - Qual2 Done - Goto applyOfferConfirm");
							return "applyOfferConfirm";
						}else{
							return "applyOffer";
						}
					}
				}
				
			}else{
				//log.debug("ApplyForStudentNumberAction - applyOffer - Staae05sAppAdmissionEvaluator - No Qualifications were accepted.");
			}
		
		}catch(Exception e){
			log.warn("ApplyForStudentNumberAction - applyOffer - crashed / " + e);
			throw e;
		}
		/* Set submission time stamp */
		Date date = new java.util.Date();
		String displayDate = (new java.text.SimpleDateFormat("EEEEE dd MMMMM yyyy hh:mm:ss").format(date).toString());
		stuRegForm.getStudent().setAppTime(displayDate);
		
		//log.debug("ApplyForStudentNumberAction - applyOffer - End - Goto applyOfferConfirm");
		return "applyOfferConfirm";
	}
	
	public String applyOfferConfirm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		//log.debug("ApplyForStudentNumberAction - applyOfferConfirm - Start");

		StudentRegistrationForm stuRegForm = (StudentRegistrationForm)form;
		//Nothing to do here. No additional workflow files or confirmation screens requested.
		
		//log.debug("ApplyForStudentNumberAction - applyOfferConfirm - Done - Goto Cancel");
		
		/* Set submission time stamp */
		Date date = new java.util.Date();
		String displayDate = (new java.text.SimpleDateFormat("EEEEE dd MMMMM yyyy hh:mm:ss").format(date).toString());
		stuRegForm.getStudent().setAppTime(displayDate);
		return "applyOfferConfirm";
	}
	
	public ActionForward stepID(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//log.debug("ApplyForStudentNumberAction - stepID - Start");
		
		StudentRegistrationForm stuRegForm = (StudentRegistrationForm)form;
		GeneralMethods gen = new GeneralMethods();
		ActionMessages messages = new ActionMessages();

		// RSA id number
		String stuID  = stripXSS(stuRegForm.getStudent().getIdNumber().trim(), "IDNumber", "stepID", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true);
		//log.debug("ApplyForStudentNumberAction - stepID - ID="+stuID);
		
		if (stuID == null && "".equals(stuID)){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
			new ActionMessage("message.generalmessage", "Please enter your RSA identity number."));
			addErrors(request, messages);
			return mapping.findForward("applyIDNumber");
		}else if (!gen.isNumeric(stuID)){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
			new ActionMessage("message.generalmessage", "RSA identity number must be numeric."));
			addErrors(request, messages);
			return mapping.findForward("applyIDNumber");
		}else if(stuID.trim().length()!= 13){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
			new ActionMessage("message.generalmessage", "RSA identity number must consist of 13 numerical characters."));
			addErrors(request, messages);
			return mapping.findForward("applyIDNumber");
		}else{
			stuRegForm.getStudent().setIdNumber(stuID);
		}
		// F915 ID Check
		
		Staae05sAppAdmissionEvaluator op = new Staae05sAppAdmissionEvaluator();
		operListener opl = new operListener();
		op.addExceptionListener(opl);
		op.clear();

		op.setInCsfClientServerCommunicationsClientVersionNumber((short) 3);
		op.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
		op.setInCsfClientServerCommunicationsAction("NL");
		op.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
		op.setInWsUserNumber(99998);
		op.setInWsStudentIdentityNr(stuRegForm.getStudent().getIdNumber());
		op.setInWsAcademicYearYear((short) Integer.parseInt(stuRegForm.getStudent().getAcademicYear()));
		
		//Hardcoded to Undergrad Degrees, because all we're interested in here is the Matric Matching, not any returned Qualifications
		op.setInGencodQualLevelCsfStringsString2("07");
			
		//log.debug("ApplyForStudentNumberAction - stepID - (Staae05sAppAdmissionEvaluator) - Student ID Number=" + stuRegForm.getStudent().getIdNumber());

		op.execute();

		if (opl.getException() != null) throw opl.getException();
		if (op.getExitStateType() < 3) throw new Exception(op.getExitStateMsg());

		//log.debug("ApplyForStudentNumberAction - stepID - After Execute");
		String opResult = op.getOutCsfStringsString500();
		//log.debug("ApplyForStudentNumberAction - stepID - opResult: " + opResult);
		
		  //log.debug("ApplyForStudentNumberAction - stepID - Staae05sAppAdmissionEvaluator - getOutCsfStringsString500="+op.getOutCsfStringsString500());

		
		if(opResult.contains("Matric data not found")){
			stuRegForm.setQualIDMatch(false);
		}else{
			stuRegForm.setQualIDMatch(true);
			
		}
		//log.debug("ApplyForStudentNumberAction - stepID - After F915 ID Check");
		//log.debug("ApplyForStudentNumberAction - stepID - ========================================================================================");
		//log.debug("ApplyForStudentNumberAction - stepID - getOutGroupCount="+op.getOutGroupCount());
		//log.debug("ApplyForStudentNumberAction - stepID - getOutGroupMatricCount="+op.getOutGroupMatricCount());	
		//log.debug("ApplyForStudentNumberAction - stepID - ========================================================================================");

		op.clear();
	
		//If Matric Exists, go To Qualification Screen (SelectNew), Else go to Matric APS Screen 1
		if (stuRegForm.isQualIDMatch()){
			//log.debug("ApplyForStudentNumberAction - stepID - Matric Matched - Goto applyQualification");
			return mapping.findForward("applyQualification");
		}else{
			//log.debug("ApplyForStudentNumberAction - stepID - Matric NOT Matched - Goto APS Screen 1 ()");
			return mapping.findForward("applyMatric");
		}
	}
	
	// Check Qualifications from Proxy for Subjects
	public ActionForward stepQualSubject(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//log.debug("ApplyForStudentNumberAction - stepID - Start");
		
		StudentRegistrationForm stuRegForm = (StudentRegistrationForm)form;
		GeneralMethods gen = new GeneralMethods();
		ActionMessages messages = new ActionMessages();
		
		// RSA id number
		String stuID  = stripXSS(stuRegForm.getStudent().getIdNumber().trim(), "IDNumber", "QualSubj", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true);
		//log.debug("ApplyForStudentNumberAction - stepID - ID="+stuID);
		
		if (stuID == null && "".equals(stuID)){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
			new ActionMessage("message.generalmessage", "Enter your RSA identity number."));
			addErrors(request, messages);
			return mapping.findForward("applyIDNumber");
		}else if (!gen.isNumeric(stuID)){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
			new ActionMessage("message.generalmessage", "RSA identity number must be numeric."));
			addErrors(request, messages);
			return mapping.findForward("applyIDNumber");
		}else if(stuID.trim().length()!= 13){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
			new ActionMessage("message.generalmessage", "RSA identity number must consist of 13 numerical characters."));
			addErrors(request, messages);
			return mapping.findForward("applyIDNumber");
		}else{
			stuRegForm.getStudent().setIdNumber(stuID);
		}
		boolean matricCheck = false;

		//F915 ID Check
		Staae05sAppAdmissionEvaluator op = new Staae05sAppAdmissionEvaluator();
		operListener opl = new operListener();
		op.addExceptionListener(opl);
		op.clear();

		op.setInCsfClientServerCommunicationsClientVersionNumber((short) 3);
		op.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
		op.setInCsfClientServerCommunicationsAction("NL");
		op.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
		op.setInWsUserNumber(99998);
		op.setInWsStudentIdentityNr(stuRegForm.getStudent().getIdNumber());
		op.setInWsAcademicYearYear((short) Integer.parseInt(stuRegForm.getStudent().getAcademicYear()));

		//Hardcoded to "07" just to do matrick Match check to know where to flow to.
		op.setInGencodQualLevelCsfStringsString2("07");
			
		//log.debug("ApplyForStudentNumberAction - stepID - (Staae05sAppAdmissionEvaluator) - Student ID Number=" + stuRegForm.getStudent().getIdNumber());

		op.execute();

		if (opl.getException() != null) throw opl.getException();
		if (op.getExitStateType() < 3) throw new Exception(op.getExitStateMsg());

		//log.debug("ApplyForStudentNumberAction - stepID - After Execute");
		String opResult = op.getOutCsfStringsString500();
		//log.debug("ApplyForStudentNumberAction - stepID - opResult: " + opResult);
		
		  //log.debug("ApplyForStudentNumberAction - stepID - Staae05sAppAdmissionEvaluator (ID) - getOutCsfStringsString500="+op.getOutCsfStringsString500());

		
		if(opResult.contains("Matric data not found")){
			matricCheck = false;
		}else{
			stuRegForm.setQualSTAAE01(true);
			matricCheck = true;
		}
		//log.debug("ApplyForStudentNumberAction - stepID - After F915 ID Check");
		//log.debug("ApplyForStudentNumberAction - stepID - ========================================================================================");
		//log.debug("ApplyForStudentNumberAction - stepID - getOutGroupCount="+op.getOutGroupCount());
				
		for (int i = 0; i < op.getOutGroupCount(); i++){
			//log.debug("ApplyForStudentNumberAction - stepID - Count="+i+" - ========================================================================================");

			//log.debug("ApplyForStudentNumberAction - stepID - Count="+i+" - F915 - getOutGroupCount                                    ="+op.getOutGroupCount());
			//log.debug("ApplyForStudentNumberAction - stepID - Count="+i+" - F915 - getOutGroupMatricCount                              ="+op.getOutGroupMatricCount());
			//log.debug("ApplyForStudentNumberAction - stepID - Count="+i+" - F915 - getOutGWsQualificationCode                          ="+op.getOutGWsQualificationCode(i).toString());
			//log.debug("ApplyForStudentNumberAction - stepID - Count="+i+" - F915 - getOutGWsQualificationSpecialityTypeSpecialityCode  ="+op.getOutGWsQualificationSpecialityTypeSpecialityCode(i).toString());
			//log.debug("ApplyForStudentNumberAction - stepID - Count="+i+" - F915 - getOutGWsQualificationType                          ="+op.getOutGWsQualificationType(i).toString());
			//log.debug("ApplyForStudentNumberAction - stepID - Count="+i+" - F915 - getOutGCsfStringsString2                            ="+op.getOutGCsfStringsString2(i).toString());
			//log.debug("ApplyForStudentNumberAction - stepID - Count="+i+" - F915 - getOutGCsfStringsString3                            ="+op.getOutGCsfStringsString3(i).toString());
			//log.debug("ApplyForStudentNumberAction - stepID - Count="+i+" - F915 - getOutGCsfStringsString4                            ="+op.getOutGCsfStringsString4(i).toString());
			//log.debug("ApplyForStudentNumberAction - stepID - Count="+i+" - F915 - getOutWsMatricRecordAcsScore                        ="+op.getOutWsMatricRecordAcsScore());
			//log.debug("ApplyForStudentNumberAction - stepID - Count="+i+" - F915 - getOutWsMatricRecordApsScore                        ="+op.getOutWsMatricRecordApsScore());
			//log.debug("ApplyForStudentNumberAction - stepID - Count="+i+" - F915 - getOutWsMatricStatusCode                            ="+op.getOutWsMatricStatusCode().toString());
			
			//log.debug("ApplyForStudentNumberAction - stepID - Count="+i+" - After F915 List Check");
			//log.debug("ApplyForStudentNumberAction - stepID - Count="+i+" - ========================================================================================");
			
		}
		op.clear();
	
		//If Matric Exists, go To SelectNew, Else go to APS Screen 1
		if (matricCheck){
			//log.debug("ApplyForStudentNumberAction - stepID - Goto applyQualification");
			return mapping.findForward("applyQualification");
		}else{
			//log.debug("ApplyForStudentNumberAction - stepID - Goto APS Screen 1 ()");
			return mapping.findForward("applyMatric");
		}
	}
	
	@SuppressWarnings("unused")
	public String applyMatric(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//log.debug("ApplyForStudentNumberAction - applyMatric - Start");
		
		StudentRegistrationForm stuRegForm = (StudentRegistrationForm)form;
		GeneralMethods gen = new GeneralMethods();
		ActionMessages messages = new ActionMessages();

		stuRegForm.setSelectMatric(stripXSS(stuRegForm.getSelectMatric(), "applyMatric", "QualSubj", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
		
		if (stuRegForm.getSelectMatric() == null || "".equals(stuRegForm.getSelectMatric())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", "Please select your Higher Education."));
			addErrors(request, messages);
			return "applyMatric";
		}
		
		//log.debug("ApplyForStudentNumberAction - applyMatric - End");
		
		request.setAttribute("subjectList", stuRegForm.getSubjects());
		return "applyMatricSubject";
	}
	
	@SuppressWarnings("unused")
	public String applyMatricSubject(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//log.debug("ApplyForStudentNumberAction - applyMatricSubject - Start");
		
		StudentRegistrationForm stuRegForm = (StudentRegistrationForm)form;
		GeneralMethods gen = new GeneralMethods();
		ActionMessages messages = new ActionMessages();
		
		List<Subject> subjectList  = new ArrayList<Subject>();
		for(int i=1;i<=12;i++) {
			Subject subject = new Subject();
			boolean isEntered = false;
			if(request.getParameter("subjectName"+i)!=null && !"".equals(request.getParameter("subjectName"+i))   && !"0".equals(request.getParameter("subjectName"+i))) {
				subject.setSubjectName(stripXSS(request.getParameter("subjectName" + i), "subjectName" + i, "MatSubject", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
				//log.debug("ApplyForStudentNumberAction - applyMatricSubject - Subject"+i+"="+request.getParameter("subjectName" + i));
				isEntered = true;
				
				if(request.getParameter("subjectGrade"+i)!=null && !"".equals(request.getParameter("subjectGrade"+i)) && !"0".equals(request.getParameter("subjectGrade"+i))) {
					subject.setSubjectGrade(stripXSS(request.getParameter("subjectGrade" + i), "subjectName" + i, "MatSubject", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
					isEntered = true;
				}

				if(request.getParameter("subjectSymbol"+i)!=null && !"".equals(request.getParameter("subjectSymbol"+i)) && !"0".equals(request.getParameter("subjectSymbol"+i))) {
					subject.setSubjectSymbol(stripXSS(request.getParameter("subjectSymbol" + i), "subjectName" + i, "MatSubject", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
					isEntered = true;
				}
				
				if(request.getParameter("subjectResult"+i)!=null && !"".equals(request.getParameter("subjectResult"+i)) && !"0".equals(request.getParameter("subjectResult"+i))) {
					subject.setSubjectResult(stripXSS(request.getParameter("subjectResult" + i), "subjectName" + i, "MatSubject", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
					isEntered = true;
				}
				if(subject != null && isEntered) {
					subjectList.add(subject);
				}
			}
		}
		stuRegForm.setSubjects(subjectList);
		stuRegForm.setApsSize(stuRegForm.getSubjects().size());
		request.setAttribute("subjectList", stuRegForm.getSubjects());
		
		//Debug
		//log.debug("ApplyForStudentNumberAction - applyMatricSubject - Subject Count="+stuRegForm.getSubjects().size());
				
		for (int j = 0; j < stuRegForm.getSubjects().size(); j++){
			//log.debug("ApplyForStudentNumberAction - applyMatricSubject - Check Subject"+j+"="+stuRegForm.getSubjects().get(j).getSubjectName()+", Grade="+stuRegForm.getSubjects().get(j).getSubjectGrade()+", Symbol="+stuRegForm.getSubjects().get(j).getSubjectSymbol()+", Result="+stuRegForm.getSubjects().get(j).getSubjectResult());
		}
		
		//log.debug("ApplyForStudentNumberAction - applyMatricSubject - End");
		
		if (stripXSS(request.getParameter("subjectName1"), "subjectName1", "MatSubject", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), false) == null || "".equals(stripXSS(request.getParameter("subjectName1"), "subjectName1", "MatSubject", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), false))){
			if (stuRegForm.getSelectMatric() == null || "".equals(stuRegForm.getSelectMatric())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please select at least one subject. Not entering all your subjects may affect the Qualifications you may apply for."));
				addErrors(request, messages);
				return "applyMatricSubject";
			}
		}
		return "applyQualification";
	}
	
	public String appealSelect(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		//log.debug("ApplyForStudentNumberAction - appealSelect - Start");
	    
		StudentRegistrationForm stuRegForm = (StudentRegistrationForm)form;
		ActionMessages messages = new ActionMessages();
		
		stuRegForm.setWebLoginMsg("");
		stuRegForm.setWebLoginMsg2("");
		
    	if (!"Back".equalsIgnoreCase(request.getParameter("act"))){
	    	stuRegForm.setAppealSelect1(stripXSS(stuRegForm.getAppealSelect1(), "AppealSelect1", "appealSel", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
	    	stuRegForm.setAppealSelect2(stripXSS(stuRegForm.getAppealSelect2(), "AppealSelect2", "appealSel", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
    		stuRegForm.setAppealText(stripXSS(request.getParameter("typeText").trim(), "AppealText", "appealSel", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
    	}

		// Check inputs
		if (!"Y".equalsIgnoreCase(stuRegForm.getAppealSelect1()) && !"".equalsIgnoreCase(stuRegForm.getAppealSelect1()) && !"Y".equalsIgnoreCase(stuRegForm.getAppealSelect2()) && !"".equalsIgnoreCase(stuRegForm.getAppealSelect2())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", "Please indicate which qualification decision you wish to appeal."));
			addErrors(request, messages);
			setDropdownListsAppeal(request,stuRegForm);
			return "appealSelect";
		}
		if (stuRegForm.getAppealText() == null || "".equalsIgnoreCase(stuRegForm.getAppealText().trim())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", "Please motivate your decision to appeal."));
			addErrors(request, messages);
			setDropdownListsAppeal(request,stuRegForm);
			return "appealSelect";
		}
    	//log.debug("ApplyForStudentNumberAction - appealSelect - Continue to Declaration............");
		return "appealDeclare";
	}
	
	public String appealDeclare(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		//log.debug("ApplyForStudentNumberAction - appealDeclare -Start");
		
		StudentRegistrationForm stuRegForm = (StudentRegistrationForm)form;
		ActionMessages messages = new ActionMessages();
		
		// agreement
		stuRegForm.setAgree(stripXSS(stuRegForm.getAgree(), "Declare", "appealDec", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true));
		if (stuRegForm.getAgree() == null || "".equals(stuRegForm.getAgree().trim())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Indicate your agreement to the declaration and undertaking."));
			addErrors(request, messages);
			return "appealDeclare";
		}
	
		//test agreement flag
		if (!"Y".equalsIgnoreCase(stuRegForm.getAgree())){
			return "cancel";
		}
		
		//Appeal Workflow
		//Set submission time stamp
		Date date = new java.util.Date();
		String displayDate = (new java.text.SimpleDateFormat("EEEEE dd MMMMM yyyy hh:mm:ss").format(date).toString());
		stuRegForm.getStudent().setAppTime(displayDate);
		request.setAttribute("email",stuRegForm.getStudent().getEmailAddress());
				
		//Write work flow document
		writeWorkflowAppeal(stuRegForm,date);
		//log.debug("ApplyForStudentNumberAction - appealDeclare - WriteWorkflowAppeal");
		
		//Move Documents from Temp Folder to main folder 
		moveDocuments(stuRegForm.getStudent().getNumber());
		//log.debug("ApplyForStudentNumberAction - appealDeclare - MoveDocuments");
    	
		//log.debug("ApplyForStudentNumberAction - appealDeclare - Done - GoTo appealConfirm");

		return "appealConfirm";
	}

	public String appealConfirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		//log.debug("ApplyForStudentNumberAction - appealConfirm -Start");
    	
		//log.debug("ApplyForStudentNumberAction - appealConfirm - Done - GoTo Unisa Eb Page");
		
		return "cancel";
	}

		
	/*
	 * populates the Matric Subjects dropdown. Uses JSON to return Key and Value list
	 */

	public ActionForward populateMatSubjects(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		//log.debug("ApplyForStudentNumberAction - populateMatSubjects - Start");

		StudentRegistrationForm stuRegForm = (StudentRegistrationForm)form;
		JSONObject matricObj = new JSONObject();
		Map<String, String> mapSubjects = new LinkedHashMap<String, String>();
		
		String subject1  = stripXSS(request.getParameter("subject1"), "Subject1", "popMatSubj", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true);
		String subject2  = stripXSS(request.getParameter("subject2"), "Subject2", "popMatSubj", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true);
		String subject3  = stripXSS(request.getParameter("subject3"), "Subject3", "popMatSubj", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true);
		String subject4  = stripXSS(request.getParameter("subject4"), "Subject4", "popMatSubj", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true);
		String subject5  = stripXSS(request.getParameter("subject5"), "Subject5", "popMatSubj", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true);
		String subject6  = stripXSS(request.getParameter("subject6"), "Subject6", "popMatSubj", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true);
		String subject7  = stripXSS(request.getParameter("subject7"), "Subject7", "popMatSubj", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true);
		String subject8  = stripXSS(request.getParameter("subject8"), "Subject8", "popMatSubj", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true);
		String subject9  = stripXSS(request.getParameter("subject9"), "Subject9", "popMatSubj", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true);
		String subject10 = stripXSS(request.getParameter("subject10"), "Subject10", "popMatSubj", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true);
		String subject11 = stripXSS(request.getParameter("subject11"), "Subject11", "popMatSubj", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true);
		String subject12 = stripXSS(request.getParameter("subject12"), "Subject12", "popMatSubj", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true);

		//log.debug("ApplyForStudentNumberAction - populateMatSubjects - **************************************************************");
		//log.debug("ApplyForStudentNumberAction - populateMatSubjects - MatCert="+stuRegForm.getSelectMatric());
		//log.debug("ApplyForStudentNumberAction - populateMatSubjects - **************************************************************");

			// Query database
			ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
			
			try{
				ArrayList<KeyValue> getValues = dao.getMatricSubjects(stuRegForm.getSelectMatric(), subject1, subject2, subject3, subject4, subject5, subject6, subject7, subject8, subject9, subject10, subject11, subject12);
				Iterator<KeyValue> it = getValues.iterator();
			
		    	while(it.hasNext()){
		    		KeyValue subject = (KeyValue) it.next();
	    			mapSubjects.put(subject.getKey(), subject.getValue());
		    	}
		    	matricObj.put("Subjects",mapSubjects);
	        }catch(Exception ex){
	        	matricObj.put("Error","The populateMatSubjects retrieval Failed! Please try again.");
	        }
		
		// Convert the map to JSON
		PrintWriter out = response.getWriter();
	   	JSONObject jsonObject = JSONObject.fromObject(matricObj);
	   	   //log.debug("ApplyForStudentNumberAction - populateMatSubjects - Final - **************************************************************");
	   	   //log.debug("ApplyForStudentNumberAction - populateMatSubjects - Final - jsonObject="+jsonObject.toString());
	   	   //log.debug("ApplyForStudentNumberAction - populateMatSubjects - Final - **************************************************************");
	   	out.write(jsonObject.toString());
	   	out.flush();

		return null; //Returning null to prevent struts from interfering with Ajax/JSON
	}
	
   /************ basic validation ************/
   // type=R registration, type=C college additions/cancellations type=NF non-formal registrations
   public String getBrowserInfo(String stuBrowserAgent) throws Exception {
	       String  browserDetails  =  stuBrowserAgent;
	       String  userAgent       =  browserDetails;
	       String  user            =  userAgent.toLowerCase();
	       String browser = "";
	       String os = "";
	       
	       //log.debug("ApplyForStudentNumberAction - getBrowserInfo - stuBrowserAgent="+stuBrowserAgent);

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
	        //log.debug("ApplyForStudentNumberAction - getBrowserInfo - Operating System="+os);
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
	       //log.debug("ApplyForStudentNumberAction - getBrowserInfo - Browser Name="+browser);
	       
	       return browser + "@" + os;
   }
	   
	//Include some cross site scripting checks (XSS)
    private String stripXSS(String value, String valueDesc, String action, String studentNr, String year, String period, int seqNr, boolean logYN) { 

        if (value != null) { 
        	//log.debug("ApplyForStudentNumberAction - stripXSS - INPUT_DETAIL="+value+", INPUT_DESC="+valueDesc+", TYPE_GC303="+action+", STUDENT="+studentNr+", YEAR="+year+", PERIOD="+period+", APP_TYPE=APP, LOG Input="+logYN);
        	if (studentNr != null && !"".equalsIgnoreCase(studentNr)) { 
	        	if (logYN){
	        		//log.debug("ApplyForStudentNumberAction - stripXSS - Sequence Number to use="+seqNr);
	        		//Check if Entry already Exists. 
	        		ApplyForStudentNumberQueryDAO dao = new ApplyForStudentNumberQueryDAO();
	        		
	        		String result = "";
	        		boolean isSTULOG = dao.getSTULOG(year, period, studentNr, valueDesc, action, seqNr);
	        		try{
		        		if (isSTULOG){
		        			result = dao.saveSTULOG(year, period, studentNr, value, valueDesc, action, seqNr, "Update");
		        		}else{
		        			result = dao.saveSTULOG(year, period, studentNr, value, valueDesc, action, seqNr, "Insert");
		        		}
	        		} catch (Exception ex) {
	        			//log.debug("ApplyForStudentNumberAction - stripXSS - Error saving STULOG / " + ex);
	        			result = "ApplyForStudentNumberAction - Error saving STULOG";
	        		}
	            	//log.debug("ApplyForStudentNumberAction - stripXSS - saveSTULOG - Result="+result);
	        	}
        	}
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

		  //log.debug("ApplyForStudentNumberAction -----------------------------------------------------------------");
		  //log.debug("ApplyForStudentNumberAction - getAllRequestParamaters - Start");
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
		  //log.debug("ApplyForStudentNumberAction - getAllRequestParamaters - End");
		  //log.debug("ApplyForStudentNumberAction -----------------------------------------------------------------");
	  } 
	  
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

    	/** Edmund Enumerate through all parameters received
	  	 * @return **/
	    //getAllRequestParamaters(request, response);
    	
    	StudentRegistrationForm stuRegForm = (StudentRegistrationForm)form;
    	String action = "";
    	
    	if (stuRegForm.getStudent().getNumber() != null){
    		action = stripXSS(request.getParameter("act"), "Action", "execute", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), true);
    	}else{
    		action = stripXSS(request.getParameter("act"), "Action", "execute", stuRegForm.getStudent().getNumber(), stuRegForm.getStudent().getAcademicYear(), stuRegForm.getStudent().getAcademicPeriod(), stuRegForm.getApplySEQUENCE(), false);
    	}
		//log.debug("ApplyForStudentNumberAction - execute - Action="+action);

        if (request.getParameter("action.search1") != null)
        {
        	return search(mapping, form, request, response, "Postal");
        }
        else if (request.getParameter("action.search2") != null)
        {
        	return search(mapping, form, request, response, "Physical");
        }
        else if (request.getParameter("action.search3") != null)
        {
        	return search(mapping, form, request, response, "Courier");
        }

        return super.execute(mapping, form, request, response);
   }
}

