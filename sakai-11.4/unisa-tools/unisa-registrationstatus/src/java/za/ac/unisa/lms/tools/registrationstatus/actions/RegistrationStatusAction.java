//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.registrationstatus.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.LookupDispatchAction;

import Srrqs01h.Abean.Srrqs01sCalcStudyFeesWeb;

import za.ac.unisa.lms.dao.Gencod;
import za.ac.unisa.lms.dao.StudentSystemGeneralDAO;
import za.ac.unisa.lms.studyquotation.StudyQuotation;
import za.ac.unisa.lms.tools.registrationstatus.dao.RegistrationStatusQueryDAO;
import za.ac.unisa.lms.tools.registrationstatus.forms.GeneralItem;
import za.ac.unisa.lms.tools.registrationstatus.forms.Qualification;
import za.ac.unisa.lms.tools.registrationstatus.forms.Student;
import za.ac.unisa.lms.tools.registrationstatus.forms.RegistrationStatusForm;
import za.ac.unisa.lms.tools.registrationstatus.forms.StudyUnit;
import za.ac.unisa.lms.tools.registrationstatus.actions.GeneralMethods;


public class RegistrationStatusAction extends LookupDispatchAction {

	public static Log log = LogFactory.getLog(RegistrationStatusAction.class);

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

	protected Map getKeyMethodMap() {
	      Map map = new HashMap();
	      map.put("button.cancel", "cancel");
	      map.put("button.continue", "nextStep");
	      map.put("button.back", "nextStep");
	      map.put("startPage", "startPage");
	      map.put("registrationStatus", "registrationStatus");

	      return map;
	  }

	public ActionForward startPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		resetForm((RegistrationStatusForm) form);

		return mapping.findForward("verifyforward");
	}


	public String displayStatus(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		RegistrationStatusForm stuRegForm = (RegistrationStatusForm) form;
		ActionMessages messages = new ActionMessages();
		GeneralMethods gen = new GeneralMethods();
		String errorMessage = "";

		/*  -----------------
		 * Check input
		 * ------------------ */

		// Student number
		if (stuRegForm.getStudent().getNumber() ==null || "".equals(stuRegForm.getStudent().getNumber().trim())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Enter your student number."));
			addErrors(request, messages);
			return "verifyforward";
		}else {
			stuRegForm.getStudent().setNumber(stuRegForm.getStudent().getNumber().trim());
			if (! gen.isNumeric(stuRegForm.getStudent().getNumber())){
				// test for numeric
				messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Student number must be numeric."));
				addErrors(request, messages);
				return "verifyforward";
			}
		}
		// Surname
		if (stuRegForm.getStudent().getSurname()==null || "".equals(stuRegForm.getStudent().getSurname().trim())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Enter your surname."));
			addErrors(request, messages);
			return "verifyforward";
		}
		// replace " with empty string
		String surname = stuRegForm.getStudent().getSurname().trim().toUpperCase();
		if (surname.indexOf("\"",0)>0){
			surname = surname.replaceAll("\"","");
			stuRegForm.getStudent().setSurname(surname);
		}
		// Full first names
		if (stuRegForm.getStudent().getFirstnames()==null || "".equals(stuRegForm.getStudent().getFirstnames().trim())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Enter your first names."));
			addErrors(request, messages);
			return "verifyforward";
		}
		// replace " with empty string
		String names = stuRegForm.getStudent().getFirstnames().trim().toUpperCase();
		if (names.indexOf("\"",0)>0){
			names = names.replaceAll("\"","");
			stuRegForm.getStudent().setFirstnames(names);
		}

		// Date of birth
		errorMessage = gen.validateDateOfBirth(stuRegForm.getStudent());
		if(!"".equals(errorMessage)){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage",
				errorMessage));
			addErrors(request, messages);
			return "verifyforward";
		}

		/*  -----------------
		 * Validate student
		 * ------------------
		 * */
		errorMessage = validateStudent(stuRegForm.getStudent());
		if (!"".equals(errorMessage)){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
					errorMessage));
			addErrors(request, messages);
			return "verifyforward";
		}

		/*  -----------------
		 * Get status
		 * ------------------
		 * */
		getRegistrationStatus(stuRegForm);

		/*  -----------------
		 * Display status
		 * ------------------
		 * */
		String date = (new java.text.SimpleDateFormat("EEEEE dd MMMMM yyyy hh:mm:ss").format(new java.util.Date()).toString());
		request.setAttribute("creationDate", date);
		
		return "displaystatusforward";
	}

	public ActionForward nextStep(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String nextPage = "";
 		if ("verifyinput".equalsIgnoreCase(request.getParameter("page"))){
 			nextPage = displayStatus(mapping,form,request, response);
		}else {
			return cancel(mapping,form,request,response);
		}

		return mapping.findForward(nextPage);
	}

	public String validateStudent(Student student)
			throws Exception {

		RegistrationStatusQueryDAO dao = new RegistrationStatusQueryDAO();
		String errorMsg = "";

		Student dbStudent = dao.getStudent(student.getNumber());
		if (dbStudent == null){
			errorMsg = "The student number you have entered does not correspond with the information on the database";
		}else if (!dbStudent.getSurname().equalsIgnoreCase(student.getSurname())){
			errorMsg = "The surname you have entered does not correspond with the information on the database";
		}else if (!dbStudent.getFirstnames().equalsIgnoreCase(student.getFirstnames())){
			errorMsg = "The first name(s) you have entered does not correspond with the information on the database";
		}else if (!dbStudent.getBirthYear().equalsIgnoreCase(student.getBirthYear())){
			errorMsg = "The birth date you have entered does not correspond with the information on the database";
		}else if (!dbStudent.getBirthMonth().equalsIgnoreCase(student.getBirthMonth())){
			errorMsg = "The birth date you have entered does not correspond with the information on the database";
		}else if (!dbStudent.getBirthDay().equalsIgnoreCase(student.getBirthDay())){
			errorMsg = "The birth date you have entered does not correspond with the information on the database";
		}

		return errorMsg;
	}

	public void getRegistrationStatus(RegistrationStatusForm stuRegForm)
	throws Exception {

		log.debug("getRegistrationStatus for " + stuRegForm.getStudent());

		RegistrationStatusQueryDAO dao = new RegistrationStatusQueryDAO();
		boolean notfound = true;
		
		// check for valid completed web reg by checking audit trail
		if( !dao.auditTrailExists(stuRegForm.getStudent().getNumber())){
			stuRegForm.setApplyType("NF");
			return;
		}

		// ----- get stuann status */
		stuRegForm.setApplyType(dao.getStudentAnnualStatus(stuRegForm));
		if ("RG".equalsIgnoreCase(stuRegForm.getApplyType())){
			// ------ Student already registered
			
			return;
		}else if ("TN".equalsIgnoreCase(stuRegForm.getApplyType())){
			// ------ Student TN registered
			// Get Qual
			stuRegForm.setQual(dao.getRegistrationQualification(stuRegForm.getStudent().getNumber()));
			if (stuRegForm.getQual().getQualCode() == null || "".equals(stuRegForm.getQual().getQualCode())){
				// student might not have stuaca record
				notfound = true;
			}else {
			//Get country
				stuRegForm.getStudent().getCountry().setCode(dao.getCountry(stuRegForm.getStudent().getNumber()));
				// ------ read student study units -stusun */
				stuRegForm.setApplyForStudyUnits(dao.getApplyForStudyUnits(stuRegForm.getStudent().getNumber()));
				if (stuRegForm.getApplyForStudyUnits().isEmpty()){
					// Posibility 1 = No study units, check for web application
					// Posibility 2 = Student who was allocated student
					//                number gets a TN annual record but nothing else
					notfound = true;
				}else{
					// Student has TP study units 
					notfound = false;
				}
			}
		}
		if (notfound == true){
			// ------ Student not registered on student system

			// ------ read student application for study units */
			stuRegForm.setApplyForStudyUnits(dao.getApplyForStudyUnits(stuRegForm.getStudent().getNumber()));
			// If nothing found, student did not apply
			if (stuRegForm.getApplyForStudyUnits().isEmpty()){
				stuRegForm.setApplyType("NF");
				return;
			}			
			// Get qual
			stuRegForm.setQual(dao.getApplicationQualification(stuRegForm.getStudent().getNumber()));
			// Get Country
			dao.getApplicationInformation(stuRegForm.getStudent());
		}


		// ------ get student account //

		//get total registration amount from study quotation
		/* calculate quotation */
		String errmsg = getFees(stuRegForm);
		if (!"".equals(errmsg)) {
			/* do not show quotation */
		}else {

		}

		// check outstanding docs
		stuRegForm.setFlagDocuments(dao.getDocsOutstanding(stuRegForm.getStudent().getNumber()));

		// get workflow reason if exists
		/* Edmund 2017
		 * Removed as per SRSR137408
		 */
		//getReason(stuRegForm);
	}


	/**
	 * This method gets the outstanding fees for a student
	 *
	 * @param regDetailsForm       The form associated with the action
	 * @param studyQuotation	   The studyQuotation to set outstanding fees
	 * @throws Exception
	*/

	private String getFees(RegistrationStatusForm stuRegForm) throws Exception{

		RegistrationStatusQueryDAO dao = new RegistrationStatusQueryDAO();

		Srrqs01sCalcStudyFeesWeb op = new Srrqs01sCalcStudyFeesWeb();
        operListener opl = new operListener();
        op.addExceptionListener(opl);
        op.clear();

        op.setInCsfClientServerCommunicationsClientVersionNumber((short) 3);
        op.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
        op.setInCsfClientServerCommunicationsAction("WE");
        op.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");

        //op.setInWsStudentNr(Integer.parseInt(stuRegForm.getStudent().getNumber()));
        op.setInStudentAnnualRecordMkStudentNr(Integer.parseInt(stuRegForm.getStudent().getNumber()));
        op.setInStudentAnnualRecordMkAcademicYear(Short.parseShort(Integer.toString(dao.getCurrentYear())));
        op.setInWsCountryCode(stuRegForm.getStudent().getCountry().getCode());
        op.setInWsQualificationCode(stuRegForm.getQual().getQualCode());
        for (int i = 0; i < stuRegForm.getApplyForStudyUnits().size(); i++) {
        	StudyUnit su = new StudyUnit();
        	su = (StudyUnit) stuRegForm.getApplyForStudyUnits().get(i);
			op.setInGStudentStudyUnitMkStudyUnitCode(i,su.getCode());
			op.setInGStudentStudyUnitSemesterPeriod(i,Short.parseShort(su.getRegPeriod()));
        }

		op.execute();
        if (opl.getException() != null) throw opl.getException();
        if (op.getExitStateType() < 3) throw new Exception(op.getExitStateMsg());

        String errormsg = op.getOutCsfStringsString500();
        if ((errormsg != null) && (!errormsg.equals(""))) {
        	return errormsg;
        }

	    stuRegForm.setTotalFee(op.getOutTotalIefSuppliedTotalCurrency());
	    stuRegForm.setPaymentDue(op.getOutRegPaymentIefSuppliedTotalCurrency());

		return "";

	}

	/**
	 * This method gets the reason for uncompleted registration submitted from workflow
	 *
	 * @param regDetailsForm       The form associated with the action
	 *
	*/

	private void getReason(RegistrationStatusForm regForm) {

		RegistrationStatusQueryDAO dao = new RegistrationStatusQueryDAO();
		GeneralItem gen = new GeneralItem();

		gen = dao.getWorkflowReason(regForm.getStudent());
		if (gen != null){
			if(gen.getCode() != null && !"".equals(gen.getCode().trim())){
				try{ // parse string to int to remove leading zero if neccesarry
					gen.setCode(String.valueOf(Integer.parseInt(gen.getCode())));
					if("1".equals(gen.getCode())){
						// don't show reason code 1 - student registered
						return;
					}
				} catch(NumberFormatException e){
					// if error, code was written incorrectly to database
					log.error("Reg status tracking: Reason code from workflow invalid for student " + regForm.getStudent().getNumber());
					return;
				}
			}
			// Set timeStamp
			regForm.setReasonDate(gen.getDesc());
		}
		// get reason description
		Gencod genCod = new Gencod();
		StudentSystemGeneralDAO stuGenDao = new StudentSystemGeneralDAO();
		genCod = stuGenDao.getGenCode("49",gen.getCode());
		// Set description
		regForm.setReasonDesc(genCod.getAfrDescription());

	}


	public ActionForward cancel(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		resetForm((RegistrationStatusForm) form);

		return mapping.findForward("cancelforward");
	}

	public void resetForm(RegistrationStatusForm form)
			throws Exception {

		// Clear fields
		form.setStudent(new Student());
		form.setApplyForStudyUnits(new ArrayList());
		form.setApplyType("");
		form.setFlagDocuments(new ArrayList());
		form.setQual(new Qualification());

	}

	public ActionForward unspecified(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response){

		log.info("RegistrationStatus: unspecified method call -no value for parameter in request");
		return mapping.findForward("home");

	}


	/**
	 * This method sets the input quotation variables for the quotation proxy
	 *
	 * @param regDetailsForm       The form associated with the action
	 * @throws Exception
	*/

	private StudyQuotation setupQuotation(RegistrationStatusForm stuRegForm) throws Exception{

		RegistrationStatusQueryDAO dao = new RegistrationStatusQueryDAO();

		/* setup Quotation input */
		StudyQuotation studyQuotation = new StudyQuotation();
		studyQuotation.setAcademicYear(Short.parseShort(Integer.toString(dao.getCurrentYear())));
		String country = "1015";
		if ("SOUTH AFRICA".equalsIgnoreCase(stuRegForm.getStudent().getCountry().getDesc())){
			country="1015";
		}
		studyQuotation.setCountryCode(country);
		studyQuotation.setLibraryCard("N");
		studyQuotation.setMatricExemption("N");
		studyQuotation.setQualificationCode(stuRegForm.getQual().getQualCode());
		studyQuotation.setQualification(stuRegForm.getQual().getQualCode());
		studyQuotation.setStudyCode1("");
		studyQuotation.setStudyCode2("");
		studyQuotation.setStudyCode3("");
		studyQuotation.setStudyCode4("");
		studyQuotation.setStudyCode5("");
		studyQuotation.setStudyCode6("");
		studyQuotation.setStudyCode7("");
		studyQuotation.setStudyCode8("");
		studyQuotation.setStudyCode9("");
		studyQuotation.setStudyCode10("");
		studyQuotation.setStudyCode11("");
		studyQuotation.setStudyCode12("");
		studyQuotation.setStudyCode13("");
		studyQuotation.setStudyCode14("");
		studyQuotation.setStudyCode15("");
		studyQuotation.setStudyCode16("");
		studyQuotation.setStudyCode17("");
		studyQuotation.setStudyCode18("");
		for (int i = 0; i < stuRegForm.getApplyForStudyUnits().size(); i++) {
			StudyUnit sUnit =  (StudyUnit)stuRegForm.getApplyForStudyUnits().get(i);
			if (i==0){
				studyQuotation.setStudyCode1(sUnit.getCode());
			}else if (i==1){
				studyQuotation.setStudyCode2(sUnit.getCode());
			}else if (i==2){
				studyQuotation.setStudyCode3(sUnit.getCode());
			}else if (i==3){
				studyQuotation.setStudyCode4(sUnit.getCode());
			}else if (i==4){
				studyQuotation.setStudyCode5(sUnit.getCode());
			}else if (i==5){
				studyQuotation.setStudyCode6(sUnit.getCode());
			}else if (i==6){
				studyQuotation.setStudyCode7(sUnit.getCode());
			}else if (i==7){
				studyQuotation.setStudyCode8(sUnit.getCode());
			}else if (i==8){
				studyQuotation.setStudyCode9(sUnit.getCode());
			}else if (i==9){
				studyQuotation.setStudyCode10(sUnit.getCode());
			}else if (i==10){
				studyQuotation.setStudyCode11(sUnit.getCode());
			}else if (i==11){
				studyQuotation.setStudyCode12(sUnit.getCode());
			}else if (i==12){
				studyQuotation.setStudyCode13(sUnit.getCode());
			}else if (i==13){
				studyQuotation.setStudyCode14(sUnit.getCode());
			}else if (i==14){
				studyQuotation.setStudyCode15(sUnit.getCode());
			}else if (i==15){
				studyQuotation.setStudyCode16(sUnit.getCode());
			}else if (i==16){
				studyQuotation.setStudyCode17(sUnit.getCode());
			}else if (i==17){
				studyQuotation.setStudyCode18(sUnit.getCode());
			}
		}
		return studyQuotation;
	}

}

