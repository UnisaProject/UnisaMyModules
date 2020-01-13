package za.ac.unisa.lms.tools.mdregistrations.actions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.io.PrintWriter;




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
import org.sakaiproject.email.api.EmailService;

import za.ac.unisa.lms.tools.mdregistrations.forms.MdRegistrationsForm;
import za.ac.unisa.lms.tools.mdregistrations.forms.Student;
import za.ac.unisa.lms.tools.mdregistrations.forms.StudyUnit;
import za.ac.unisa.lms.tools.mdregistrations.forms.GeneralItem;
import za.ac.unisa.lms.tools.mdregistrations.forms.Qualification;
import za.ac.unisa.lms.tools.mdregistrations.actions.GeneralMethods;
import za.ac.unisa.lms.tools.mdregistrations.dao.MdRegistrationsQueryDAO;
import za.ac.unisa.utils.WorkflowFile;
import Srrsa01h.Abean.Srrsa01sRegStudentPersDetail;
import Srruf01h.Abean.Srruf01sStudyUnitRegistration;
import Sruaf01h.Abean.Sruaf01sStudyUnitAddition;

@SuppressWarnings({"unused", "unchecked", "rawtypes"})
public class MdRegistrationsAction extends LookupDispatchAction {
	//private statements add June 2011
	private EmailService emailService;
	
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
		Map map = new HashMap();
		map.put("button.display", "display");
		map.put("button.reset", "reset");
		map.put("button.submit","submit");
		map.put("button.input", "input");
		map.put("button.update", "update");
		map.put("button.cancel", "cancel");
		map.put("button.back", "back");
		map.put("button.backto", "back");
		map.put("button.withdraw", "cancel");
		map.put("button.continue", "nextStep");
		map.put("studinput", "studinput");
		map.put("walkthrough", "walkthrough");
	    map.put("step1", "display");
		map.put("button.request", "saveRegistration");
		map.put("submitDocuments", "submitDocuments");
		map.put("button.save", "saveDocuments");
		map.put("button.add.attachment", "addAttachment");
		map.put("button.add", "attachFile");
		map.put("button.submitdoc","submitDocuments");	
		map.put("button.submitreg","saveRegistration");
		map.put("button.submitadv","saveForadvisor");
		
		map.put("stepCourier", "registerStepCourier");
	    map.put("getPostalCode", "getPostalCode");
	    map.put("button.list", "listPostalCode");
	    map.put("button.search.cancel", "cancelSearch");
	return map;
	}

	public ActionForward walkthrough(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
		 throws Exception {
		
	/*		Type cast the incoming form to mdForm and reset  
	 */
			MdRegistrationsForm mdForm = (MdRegistrationsForm) form;
			resetForm(mdForm); 
		
			return mapping.findForward("walkthroughforward");
		}
	
	public ActionForward studinput(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
		 throws Exception {
		
	/*		Type cast the incoming form to mdForm and reset  
	 */
			MdRegistrationsForm mdForm = (MdRegistrationsForm) form;
			ActionMessages messages = new ActionMessages();
			resetForm(mdForm); 
			mdForm.setStudentNumberSearchAttemp(0);
			
			mdForm.setStudyUnitAddition (
					request.getParameter("studyUnitAddition")== null ? false: 
						 Boolean.valueOf(request.getParameter("studyUnitAddition")));
			//Change 20120725 : Insert Registration Dates test before Admissions approved and Study approved Flags
		    // Closing date  --Integer.parseInt(String.valueOf(mdForm.getStudent().getAcademicYear())))){
			//PostGrad:Post
			String regdatType = "PT";
			if (mdForm.isStudyUnitAddition())
			{
				//PostGrad:Additions
				regdatType = "PA";
			}
			
			MdRegistrationsQueryDAO dao = new MdRegistrationsQueryDAO();
			//log.debug("MdRegistrationsAction - studinput - DateCheck: " + mdForm.getStudent().getAcademicYear() + ", regdatType: " + regdatType);
			if (!dao.validateClosingDate(mdForm.getStudent().getAcademicYear(),regdatType)){
				messages.add(ActionMessages.GLOBAL_MESSAGE,	
						new ActionMessage("message.generalmessage", "The registration period for study at Unisa is closed."));
				addErrors(request, messages);
				return mapping.findForward("walkthroughforward");
			}
			return mapping.findForward("display");
	}

	public ActionForward submit(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
		MdRegistrationsForm mdForm = (MdRegistrationsForm) form;
		ActionMessages messages = new ActionMessages();
		mdForm.setApplyType("F");
		mdForm.setFromPage("page1");
			
		if (mdForm.getStudent().getNumber() == null || "".equalsIgnoreCase(mdForm.getStudent().getNumber())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please enter your student number."));
			addErrors(request, messages);
			return mapping.findForward("display");
		}

		if (mdForm.getStudent().getNumber().trim().length()==8){
			if ("7".equalsIgnoreCase(mdForm.getStudent().getNumber().substring(0,1))){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "This student number may only be used for non-formal studies. If you do not have a formal student number, first apply for a student number."));
				addErrors(request, messages);
				return mapping.findForward("display");
			}
		}else if (mdForm.getStudent().getNumber().length() == 7 ){
			mdForm.getStudent().setNumber("0" + mdForm.getStudent().getNumber());
		}
		
		if (mdForm.getStudent().getSurname() == null || "".equalsIgnoreCase(mdForm.getStudent().getSurname())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,	
					new ActionMessage("message.generalmessage", "Please enter your surname."));
			addErrors(request, messages);
			return mapping.findForward("display");
		}
		if (mdForm.getStudent().getFirstnames() == null || "".equalsIgnoreCase(mdForm.getStudent().getFirstnames())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,	
					new ActionMessage("message.generalmessage", "Please enter your first name(s)."));
			addErrors(request, messages);
			return mapping.findForward("display");
		}
		if (mdForm.getStudent().getBirthDay() == null || "".equalsIgnoreCase(mdForm.getStudent().getBirthDay())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,	
					new ActionMessage("message.generalmessage", "Please enter your date of birth."));
			addErrors(request, messages);
			return mapping.findForward("display");
		}
		if (mdForm.getStudent().getBirthMonth() == null || "".equalsIgnoreCase(mdForm.getStudent().getBirthMonth())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,	
					new ActionMessage("message.generalmessage", "Please enter your date of birth."));
			addErrors(request, messages);
			return mapping.findForward("display");
		}
		if (mdForm.getStudent().getBirthYear() == null || "".equalsIgnoreCase(mdForm.getStudent().getBirthYear())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,	
					new ActionMessage("message.generalmessage", "Please enter your date of birth."));
			addErrors(request, messages);
			return mapping.findForward("display");
		}
	
		//validate student and personal details		
		mdForm.setErrorAdvisor("N");
		String errmsg="";
		Student teststu = new Student();
		teststu.setNumber(mdForm.getStudent().getNumber());
		teststu.setFirstnames(mdForm.getStudent().getFirstnames());
		teststu.setSurname(mdForm.getStudent().getSurname());
		teststu.setBirthDay(mdForm.getStudent().getBirthDay());
		teststu.setBirthMonth(mdForm.getStudent().getBirthMonth());
		teststu.setBirthYear(mdForm.getStudent().getBirthYear());
		teststu.setAcademicYear(mdForm.getStudent().getAcademicYear());

		MdRegistrationsQueryDAO dao = new MdRegistrationsQueryDAO();
		errmsg=dao.validateStudent(mdForm.getStudent().getNumber());
		if ("NF".equalsIgnoreCase(errmsg)){
			errmsg="The student number you have entered does not correspond with the information on the database.";
		}else{
			errmsg="";
		}
	    if (!"".equalsIgnoreCase(errmsg)){
			messages.add(ActionMessages.GLOBAL_MESSAGE,	
					new ActionMessage("message.generalmessage", errmsg));
			addErrors(request, messages);
			return mapping.findForward("display");
	    }
	    
	   //PostGrad:Post
		String regdatType = "PT";
		if (mdForm.isStudyUnitAddition())
		{
			//PostGrad:Additions
			regdatType = "PA";
		}
		
	    //Change 20120725 : Insert Registration Dates test before Admissions approved and Study approved Flags
	    // Closing date  --Integer.parseInt(String.valueOf(mdForm.getStudent().getAcademicYear())))){
		//log.debug("stubmit - DateCheck: " + mdForm.getStudent().getAcademicYear() + ", regdatType: " + regdatType);
		if (!dao.validateClosingDate(mdForm.getStudent().getAcademicYear(), regdatType)){
			errmsg="The registration period for study at Unisa is closed.";
			messages.add(ActionMessages.GLOBAL_MESSAGE,	
					new ActionMessage("message.generalmessage", errmsg));
			addErrors(request, messages);
			return mapping.findForward("display");
		}
		
		Student stu2 = new Student();
		stu2=dao.getStudentann(teststu.getNumber(), teststu.getAcademicYear());
		
		//use this for error write to wfl if needed, but real read will be in GetPersonal (F138 Display)
		mdForm.getStudent().setEmailAddress(stu2.getEmailAddress());
		mdForm.getStudent().setInitials(stu2.getInitials());
		mdForm.getStudent().setTitle(stu2.getTitle());

		//validate input against input
		if (stu2.getNumber() == null || "".equalsIgnoreCase(stu2.getNumber())){
			errmsg="You have not applied for admission to study for a postgraduate qualification at Unisa. ";
			//errmsg="The student number you have entered does not correspond with the information on the database.";
			errmsg=errmsg+"For further enquiries, send an email to study-info@unisa.ac.za or an SMS to 32695.";
		}else{
			if ("".equalsIgnoreCase(stu2.getMediaAccess1()) || stu2.getMediaAccess1()==null){
				errmsg="Student qualification for academic year was not found";
			}else{
				if (mdForm.isStudyUnitAddition())
				{
					if (!(("RG".equalsIgnoreCase(stu2.getStatusCode()) ||
						   "CA".equalsIgnoreCase(stu2.getStatusCode()) ||
						   "AC".equalsIgnoreCase(stu2.getStatusCode())) &&
							"THISYEAR".equalsIgnoreCase(stu2.getAdmstatus()))) {
						//might be cancel record
						errmsg="You do not have a registration record for this academic year. ";
						errmsg=errmsg+"For further enquiries, send an email to study-info@unisa.ac.za or an SMS to 32695.";
					
					}
				}
				else{	
				
					if ("THISYEAR".equalsIgnoreCase(stu2.getAdmstatus())){
						if ("TN".equalsIgnoreCase(stu2.getStatusCode())){
							//ok
						}else{
							if ("RG".equalsIgnoreCase(stu2.getStatusCode())){
								
									errmsg="You are already registered Go to myUnisa and login. " +
											"Select 'My Admin' and the 'Edit Registration' to gain " +
											"access to 'Registration of Additional Study Units'. " +
											"Please note: you must register/join as a myUnisa user before " +
											"you will be able to use this functionality.";
								
							}else{
							if ("AP".equalsIgnoreCase(stu2.getStatusCode())){
								errmsg="Your application to study at Unisa is incomplete. You either have admission documents outstanding or have not paid your application fee or your studies still need to be approved. ";
								errmsg=errmsg+"For further enquiries, send an email to study-info@unisa.ac.za or an SMS to 32695.";
								mdForm.setQuestion2(errmsg);
								mdForm.setErrorAdvisor("Y");
							}else {
									//might be cancel record
									errmsg="You do not have a registration record for this academic year. ";
									errmsg=errmsg+"For further enquiries, send an email to study-info@unisa.ac.za or an SMS to 32695.";
								}
							
							}
						}
					}else{
						//prevyear record found
						if ("PREVRG".equalsIgnoreCase(stu2.getAdmstatus())){
							//ok
						}
					}
			}
			}
		}
	    if (!"".equalsIgnoreCase(errmsg)){
			messages.add(ActionMessages.GLOBAL_MESSAGE,	
					new ActionMessage("message.generalmessage", errmsg));
			addErrors(request, messages);
			return mapping.findForward("display");
	    }
		if (stu2.getSurname().equalsIgnoreCase(teststu.getSurname())){
		}else{
			errmsg = "The surname you have entered does not correspond with the information on the database.";
		}
		if (stu2.getFirstnames().equalsIgnoreCase(teststu.getFirstnames())){
		}else{
			errmsg= "The first name(s) you have entered does not correspond with the information on the database.";
		}
		if (stu2.getBirthDay().equals(teststu.getBirthDay()) 
				&& stu2.getBirthMonth().equals(teststu.getBirthMonth())
				&& stu2.getBirthYear().equals(teststu.getBirthYear())){
		}else{
			errmsg="The birth date you have entered does not correspond with the information on the database.";
		}
		if ("".equalsIgnoreCase(stu2.getEmailAddress()) || " ".equalsIgnoreCase(stu2.getEmailAddress())){
			if (mdForm.isStudyUnitAddition())
			{
				errmsg="You do not have a valid e-mail address on our system. First claim your myLife e-mail address on myUnisa before trying to do an addition.";
			}
			else
			{
				errmsg="You must have a valid email address.";
			}
		}
		
	    if (!"".equalsIgnoreCase(errmsg)){
			messages.add(ActionMessages.GLOBAL_MESSAGE,	
					new ActionMessage("message.generalmessage", errmsg));
			addErrors(request, messages);
			return mapping.findForward("display");
	    }
	    //validate qualification
		mdForm.getQual().setQualCode(stu2.getMediaAccess1());
		mdForm.getQual().setSpecCode(stu2.getMediaAccess2());
		
		String qdesc = dao.getQualification(mdForm.getQual().getQualCode());
		mdForm.getQual().setQualDesc(qdesc);
		//log.debug("MdRegistrationsAction - submit - QualCode=" + mdForm.getQual().getQualCode()+", QualDesc=" + mdForm.getQual().getQualDesc());
		
		String codeString = dao.validateQualification(stu2.getMediaAccess1().substring(0,5));
		String underpost = codeString.substring(0,1);
		String qtype = codeString.substring(1,2);
		String qkat = codeString.substring(2);
		//log.debug("MdRegistrationsAction - submit - Qualification=" + stu2.getMediaAccess1().substring(0,5));
		//log.debug("MdRegistrationsAction - submit - underpost=" + underpost);
		//log.debug("MdRegistrationsAction - submit - qtype=" + qtype);
		//log.debug("MdRegistrationsAction - submit - qkat=" + qkat);
		
	    if (!"M".equalsIgnoreCase(underpost) && !"D".equalsIgnoreCase(underpost)){
	    	errmsg="Student was not registered for, or did not apply for a Master's or Doctorates qualification";
			messages.add(ActionMessages.GLOBAL_MESSAGE,	
					new ActionMessage("message.generalmessage", errmsg));
			addErrors(request, messages);
			return mapping.findForward("display");
	    }
	    // Closing date test used to be here
		
		// validate student - check personal details blocks
		errmsg="";
		errmsg=dao.validateStudent(stu2.getNumber());
	    if (!"".equalsIgnoreCase(errmsg)){
	    	if ("PHASEOUT".equalsIgnoreCase(errmsg)){
	    		errmsg="You have been excluded from further studies at Unisa due to poor academic performance.";
	    	}
	    	if ("DISCIPLINARY".equalsIgnoreCase(errmsg)){
	    		errmsg="You have been excluded from further studies at Unisa on the grounds of a disciplinary incident.";
	    	}
	    	if ("LIBRARY".equalsIgnoreCase(errmsg)){
	    		errmsg="You have library fees and/or books outstanding.";
	    	}
	    	if ("FINBLOCK".equalsIgnoreCase(errmsg)){
	    		errmsg="You have outstanding fees on your student account.";
	    	}
	    	if ("STUDYFEES".equalsIgnoreCase(errmsg)){
	    		errmsg="You have outstanding fees on your student account.";
	    	}
	    	if ("DOCSFLAG".equalsIgnoreCase(errmsg)){
	    		errmsg="You have outstanding admission documents on our system.";
	    	}
	    	if ("POSTGRADFLAG".equalsIgnoreCase(errmsg)){
	    		mdForm.setErrorAdvisor("Y");
	    		errmsg="You do not have approval for postgraduate studies.";
				mdForm.setQuestion2(errmsg);
	    	}
	    	if ("CAOCHK".equalsIgnoreCase(errmsg)){
	    		mdForm.setErrorAdvisor("Y");
	    		errmsg="Your application to Study at Unisa is incomplete. You either have admission documents outstanding or you have not paid your application fee or your studies still need to be approved.";
				mdForm.setQuestion2(errmsg);
	    	}
	    	if ("APPLICSTATUS".equalsIgnoreCase(errmsg)){
	    		mdForm.setErrorAdvisor("Y");
	    		errmsg="Your application to Study at Unisa is incomplete. You either have admission documents outstanding or you have not paid your application fee or your studies still need to be approved.";
				mdForm.setQuestion2(errmsg);
	    	}
	    	errmsg=errmsg+" For further enquiries, send an e-mail to study-info@unisa.ac.za or an SMS to 32695";
			messages.add(ActionMessages.GLOBAL_MESSAGE,	
					new ActionMessage("message.generalmessage", errmsg));
			addErrors(request, messages);
			return mapping.findForward("display");
	    }
	    //validate documents 500-series set on F113
	    errmsg="";
		errmsg=dao.validateDocs(stu2.getNumber());
	    if (!"".equalsIgnoreCase(errmsg)){
	    	errmsg=errmsg+" For further enquiries, send an e-mail to study-info@unisa.ac.za or an SMS to 32695";
			mdForm.setErrorAdvisor("Y");
			mdForm.setQuestion2(errmsg);
			messages.add(ActionMessages.GLOBAL_MESSAGE,	
					new ActionMessage("message.generalmessage", errmsg));
			addErrors(request, messages);
			return mapping.findForward("display");
	    }
	    
		
	    //einde move vanaf displaypersonal		
		
	
		mdForm.setListType("S");   //wat is die
		String errorMsg="";
		
		
		if (mdForm.isStudyUnitAddition())
		{
			errorMsg = displayAdditionPersonal(mdForm);
		}
		else
		{
			errorMsg = displayPersonal(mdForm);
		}
		
		if(!"".equals(errorMsg)){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", errorMsg));
				addErrors(request, messages);
				
			return mapping.findForward("display");
		}else{		
			setDropdownListsStep1(request,mdForm.getApplyType());
			return mapping.findForward("step1forward");
		}
	}

	public String displayPersonal(MdRegistrationsForm mdForm) throws Exception {
/*
 * 	F126-display-D
 */
		String result = "";
		Srruf01sStudyUnitRegistration op = new Srruf01sStudyUnitRegistration();
		operListener opl = new operListener();
		op.addExceptionListener(opl);
		op.clear();

		op.setInCsfClientServerCommunicationsClientVersionNumber((short) 3);
		op.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);     
		op.setInCsfClientServerCommunicationsAction("D");
		op.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");  
		op.setInWsUserNumber(99998);
		op.setInSecurityWsFunctionNumber(514);
		op.setInStudentAnnualRecordMkAcademicYear(Short.parseShort(String.valueOf(mdForm.getStudent().getAcademicYear())));
		op.setInStudentAnnualRecordMkAcademicPeriod(Short.parseShort("1"));
		op.setInStudentAnnualRecordMkStudentNr(Integer.parseInt(mdForm.getStudent().getNumber()));
		op.setInStudentAnnualRecordMkHighestQualificationCode(mdForm.getQual().getQualCode());
		op.setInStudentAcademicRecordMkQualificationCode(mdForm.getQual().getQualCode());
		//op.setInStudentAnnualRecordMkHighestQualificationCode(stu2.getMediaAccess1());
		//op.setInStudentAcademicRecordMkQualificationCode(stu2.getMediaAccess1());
		op.setInStudentAcademicRecordMkStudentNr(Integer.parseInt(mdForm.getStudent().getNumber()));
		
		//log.debug("MDRegistrationsAction - displayPersonal - Srruf01sStudyUnitRegistration(D) - setInCsfClientServerCommunicationsAction = D");
		//log.debug("MDRegistrationsAction - displayPersonal - Srruf01sStudyUnitRegistration(D) - setInWsUserNumber = 99998");
		//log.debug("MDRegistrationsAction - displayPersonal - Srruf01sStudyUnitRegistration(D) - setInSecurityWsFunctionNumber = 514");
		//log.debug("MDRegistrationsAction - displayPersonal - Srruf01sStudyUnitRegistration(D) - setInStudentAnnualRecordMkAcademicYear = "+mdForm.getStudent().getAcademicYear());
		//log.debug("MDRegistrationsAction - displayPersonal - Srruf01sStudyUnitRegistration(D) - setInStudentAnnualRecordMkAcademicPeriod = 1");
		//log.debug("MDRegistrationsAction - displayPersonal - Srruf01sStudyUnitRegistration(D) - setInStudentAnnualRecordMkStudentNr = "+mdForm.getStudent().getNumber());
		//log.debug("MDRegistrationsAction - displayPersonal - Srruf01sStudyUnitRegistration(D) - setInStudentAnnualRecordMkHighestQualificationCode = "+mdForm.getQual().getQualCode());
		//log.debug("MDRegistrationsAction - displayPersonal - Srruf01sStudyUnitRegistration(D) - setInStudentAcademicRecordMkQualificationCode = "+mdForm.getQual().getQualCode());
		//log.debug("MDRegistrationsAction - displayPersonal - Srruf01sStudyUnitRegistration(D) - setInStudentAcademicRecordMkStudentNr = "+mdForm.getStudent().getNumber());

		  
		op.execute();
		  
		if (opl.getException() != null) throw opl.getException();
		if (op.getExitStateType() < 3) throw new Exception(op.getExitStateMsg());
		  
		String errorMessage = op.getOutCsfStringsString500();
		
		//log.debug("MDRegistrationsAction - displayPersonal - Srruf01sStudyUnitRegistration(D) - getOutCsfStringsString500 = " + errorMessage);
		
		if(!"".equalsIgnoreCase(errorMessage)){
			if ("WARNING".equalsIgnoreCase(errorMessage.substring(0,7))){
				errorMessage="";
			}
			if (errorMessage.toLowerCase().contains("courier")){
				//log.debug("Courier Found - Resetting ErrorMessage: " + errorMessage);
				errorMessage="";
			}
			if (errorMessage.toLowerCase().contains("majors read")){
				//log.debug("* Majors read - Resetting ErrorMessage: " + errorMessage);
				errorMessage="";
			}
		}
		
		if (!"".equals(errorMessage)){
		    return errorMessage;
		}
		if(op.getOutCsfClientServerCommunicationsReturnCode()== 9999){
			// error returned
			return errorMessage;
		}
		
		mdForm.getStudent().setSurname(op.getOutWsStudentSurname());
		mdForm.getStudent().setFirstnames(op.getOutWsStudentFirstNames());

		mdForm.getStudent().setMaidenName(op.getOutWsStudentPreviousSurname());
		mdForm.getStudent().setInitials(op.getOutWsStudentInitials());
		mdForm.getStudent().setIdNumber(op.getOutWsStudentIdentityNr());
		mdForm.getStudent().setGender(op.getOutWsStudentGender());
		mdForm.getStudent().getNationalty().setCode(op.getOutWsStudentMkNationality());
		mdForm.getStudent().getHomeLanguage().setCode(op.getOutWsStudentMkHomeLanguage());
		mdForm.getStudent().setLanguage(op.getOutWsStudentMkCorrespondenceLanguage());
		mdForm.getStudent().setTitle(op.getOutWsStudentMkTitle());
		mdForm.getStudent().setSpesCharacter(op.getOutWsStudentSpecialCharacterFlag());
		mdForm.getStudent().setRegistrationMethod(op.getOutStudentAnnualRecordRegistrationMethodCode());
		mdForm.getStudent().setDespatchMethod(op.getOutStudentAnnualRecordDespatchMethodCode());
		mdForm.getStudent().setRegdeliveryMethod(op.getOutStudentAnnualRecordRegDeliveryMethod());
		mdForm.getStudent().setPrevPostgrad(op.getOutWsStudentPreviouslyPostGraduateFlag());
		mdForm.getStudent().setPrevUnisapg(op.getOutWsStudentPreviouslyUnisaPostGradFlag());
		
		//mdForm.getQual().setQualCode(op.getOutWsQualificationCode());
		//mdForm.getQual().setSpecCode(op.getOutQualificationSpecialityTypeSpecialityCode());
		
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
//		mdForm.getStudent().getCountry().setCode(op.getOutWsCountryCode());
		mdForm.getStudent().getCountry().setCode(op.getOutWsStudentMkCountryCode());
		mdForm.getStudent().getNationalty().setCode(op.getOutWsStudentMkNationality());
		mdForm.getStudent().getHomeLanguage().setCode(op.getOutWsStudentMkHomeLanguage());
		mdForm.getStudent().getDisability().setCode(Integer.toString(op.getOutStudentAnnualRecordMkDisabilityTypeCode()));
		mdForm.getStudent().setShareContactDetails(op.getOutStudentAnnualRecordFellowStudentFlag());
		mdForm.getStudent().setLastRegYear(Integer.toString(op.getOutStudentAnnualRecordPrevEducationalInstitutionYr()));
		mdForm.getStudent().getOtherUniversity().setCode(op.getOutStudentAnnualRecordMkOtherEducationalInstitCode());
		mdForm.getStudent().getPrevInstitution().setCode(op.getOutStudentAnnualRecordMkPrevEducationalInstitCode());
		mdForm.getStudent().getOccupation().setCode(op.getOutStudentAnnualRecordMkOccupationCode());
		mdForm.getStudent().getEconomicSector().setCode(op.getOutStudentAnnualRecordMkEconomicSectorCode());
		mdForm.getQual().setSpecCode(op.getOutStudentAnnualRecordSpecialityCode());
		mdForm.getStudent().getExam().setCode(op.getOutWsExamCentreCode());
		mdForm.getStudent().getExam().setDesc(op.getOutWsExamCentreEngDescription());
		mdForm.setSelectedExamCentre(mdForm.getStudent().getExam().getCode()+mdForm.getStudent().getExam().getDesc());
		mdForm.getStudent().setPhaseout(Short.toString(op.getOutWsStudentPhasedOutStatus()));
		//log.debug("MDRegistrationsAction - displayPersonal - Srruf01sStudyUnitRegistration - Qual Phased Out: " + mdForm.getStudent().getPhaseout());

		mdForm.getStudent().setStatusCode(op.getOutStudentAnnualRecordStatusCode());
		mdForm.getStudent().setEmailAddress(op.getOutEmailToCsfStringsString132());
		
		/** Edmund 2015 **/
		//courier address
		//Get from SRRSA01S as MD doesn't cater for Addresses
		Srrsa01sRegStudentPersDetail op2 = new Srrsa01sRegStudentPersDetail();
		operListener opl2 = new operListener();
		op2.addExceptionListener(opl2);
		op2.clear();

		op2.setInCsfClientServerCommunicationsClientVersionNumber((short) 3);
		op2.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
		op2.setInCsfClientServerCommunicationsAction("D");
		op2.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
		op2.setInWsUserNumber(99998);
		op2.setInStudentAnnualRecordMkAcademicYear(Short.parseShort(String.valueOf(mdForm.getStudent().getAcademicYear())));
		op2.setInStudentAnnualRecordMkAcademicPeriod(Short.parseShort("1"));
		op2.setInStudentAnnualRecordMkStudentNr(Integer.parseInt(mdForm.getStudent().getNumber()));

		op2.execute();

		if (opl2.getException() != null) throw opl2.getException();
		if (op2.getExitStateType() < 3) throw new Exception(op2.getExitStateMsg());

		//log.debug("displayPersonal - After OPL2 Execute");
		String errorMessage2 = op2.getOutCsfStringsString500();
		
		//log.debug("displayPersonal - errorMessage2: " + errorMessage2);
		if(!"".equalsIgnoreCase(errorMessage2)){
			if ("WARNING".equalsIgnoreCase(errorMessage2.substring(0,7))){
				errorMessage2="";
			}			
		}

		//log.debug("MDRegistrationsAction - displayPersonal - Srrsa01sRegStudentPersDetail(D) - getOutCsfStringsString500 = " + errorMessage2);

		if (!"".equals(errorMessage2)){
		    return errorMessage2;
		}
		if(op2.getOutCsfClientServerCommunicationsReturnCode()== 9999){
			//log.debug("displayPersonal - errorMessage 2: 9999");
			// error returned
			return errorMessage2;
		}
		mdForm.getStudent().getCourierAddress().setLine1(op2.getOutCourierWsAddressAddressLine1().trim());
		mdForm.getStudent().getCourierAddress().setLine2(op2.getOutCourierWsAddressAddressLine2().trim());
		mdForm.getStudent().getCourierAddress().setLine3(op2.getOutCourierWsAddressAddressLine3().trim());
		mdForm.getStudent().getCourierAddress().setLine4(op2.getOutCourierWsAddressAddressLine4().trim());
		mdForm.getStudent().getCourierAddress().setLine5(op2.getOutCourierWsAddressAddressLine5().trim());
		mdForm.getStudent().getCourierAddress().setLine6(op2.getOutCourierWsAddressAddressLine6().trim());
		mdForm.getStudent().getCourierAddress().setAreaCode(Integer.toString(op2.getOutCourierWsAddressPostalCode()).trim());

		mdForm.getStudent().setContactNr(op2.getOutWsAddressV2CourierContactNo().trim());
		
		String pp="0000"+mdForm.getStudent().getCourierAddress().getAreaCode();
		mdForm.getStudent().getCourierAddress().setAreaCode(pp.substring(pp.length()-4,pp.length()));
		if (!"1015".equalsIgnoreCase(mdForm.getStudent().getCountry().getCode())){
			mdForm.getStudent().getCourierAddress().setAreaCode("");
		}

		// Copy above address as backup to see if address changes. 
		//We only write update to database and then we need to do Audit trail. If no update, then no need to write to DB
		mdForm.getStudent().getCourierAddressPrev().setLine1(mdForm.getStudent().getCourierAddress().getLine1());
		mdForm.getStudent().getCourierAddressPrev().setLine2(mdForm.getStudent().getCourierAddress().getLine2());
		mdForm.getStudent().getCourierAddressPrev().setLine3(mdForm.getStudent().getCourierAddress().getLine3());
		mdForm.getStudent().getCourierAddressPrev().setLine4(mdForm.getStudent().getCourierAddress().getLine4());
		mdForm.getStudent().getCourierAddressPrev().setLine5(mdForm.getStudent().getCourierAddress().getLine5());
		mdForm.getStudent().getCourierAddressPrev().setLine6(mdForm.getStudent().getCourierAddress().getLine6());
		mdForm.getStudent().getCourierAddressPrev().setAreaCode(mdForm.getStudent().getCourierAddress().getAreaCode());

		mdForm.getStudent().setContactNrPrev(mdForm.getStudent().getContactNr());
		
		
		if (!"".equals(result)){
			//error result - move input back to screen values
		}else{
		}
		//if any error message =result
		//log.debug("End of Personal Details - Result: " + result);
		return result;
	}	
	
	public String displayAdditionPersonal(MdRegistrationsForm mdForm) throws Exception {
		/*
		 * 	F139-display-D
		 */
				String result = "";
				Sruaf01sStudyUnitAddition op = new Sruaf01sStudyUnitAddition();
				operListener opl = new operListener();
				op.addExceptionListener(opl);
				op.clear();

				op.setInCsfClientServerCommunicationsClientVersionNumber((short) 3);
				op.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);     
				op.setInCsfClientServerCommunicationsAction("D");
				op.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");  
				op.setInWsUserNumber(99998);
				op.setInWsFunctionNumber(539);
				op.setInStudentAnnualRecordMkAcademicYear(Short.parseShort(String.valueOf(mdForm.getStudent().getAcademicYear())));
				op.setInStudentAnnualRecordMkAcademicPeriod(Short.parseShort("1"));
				op.setInStudentAnnualRecordMkStudentNr(Integer.parseInt(mdForm.getStudent().getNumber()));
				op.setInStudentAnnualRecordMkHighestQualificationCode(mdForm.getQual().getQualCode());
				op.setInStudentAcademicRecordMkQualificationCode(mdForm.getQual().getQualCode());
				op.setInStudentAcademicRecordMkStudentNr(Integer.parseInt(mdForm.getStudent().getNumber()));
				  
				op.execute();
				  
				if (opl.getException() != null) throw opl.getException();
				if (op.getExitStateType() < 3) throw new Exception(op.getExitStateMsg());
				  
				String errorMessage = op.getOutCsfStringsString500();
				if(!"".equalsIgnoreCase(errorMessage)){
					if ("WARNING".equalsIgnoreCase(errorMessage.substring(0,7))){
						errorMessage="";
					}
				}
				
				//log.debug("MDRegistrationsAction - displayAdditionPersonal - Sruaf01sStudyUnitAddition(D) - getOutCsfStringsString500 = " + errorMessage);

				
				if (!"".equals(errorMessage)){
				    return errorMessage;
				}
				if(op.getOutCsfClientServerCommunicationsReturnCode()== 9999){
					// error returned
					return errorMessage;
				}
				
				mdForm.getStudent().setSurname(op.getOutWsStudentSurname());
				mdForm.getStudent().setFirstnames(op.getOutWsStudentFirstNames());

				mdForm.getStudent().setMaidenName(op.getOutWsStudentPreviousSurname());
				mdForm.getStudent().setInitials(op.getOutWsStudentInitials());
				mdForm.getStudent().setIdNumber(op.getOutWsStudentIdentityNr());
				mdForm.getStudent().setGender(op.getOutWsStudentGender());
				mdForm.getStudent().getNationalty().setCode(op.getOutWsStudentMkNationality());
				mdForm.getStudent().getHomeLanguage().setCode(op.getOutWsStudentMkHomeLanguage());
				mdForm.getStudent().setLanguage(op.getOutWsStudentMkCorrespondenceLanguage());
				mdForm.getStudent().setTitle(op.getOutWsStudentMkTitle());
				mdForm.getStudent().setSpesCharacter(op.getOutWsStudentSpecialCharacterFlag());
				mdForm.getStudent().setRegistrationMethod(op.getOutStudentAnnualRecordRegistrationMethodCode());
				mdForm.getStudent().setDespatchMethod(op.getOutStudentAnnualRecordDespatchMethodCode());
				mdForm.getStudent().setRegdeliveryMethod(op.getOutStudentAnnualRecordRegDeliveryMethod());
				mdForm.getStudent().setPrevPostgrad(op.getOutWsStudentPreviouslyPostGraduateFlag());
				mdForm.getStudent().setPrevUnisapg(op.getOutWsStudentPreviouslyUnisaPostGradFlag());
		
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
				mdForm.getStudent().getCountry().setCode(op.getOutWsStudentMkCountryCode());
				mdForm.getStudent().getNationalty().setCode(op.getOutWsStudentMkNationality());
				mdForm.getStudent().getHomeLanguage().setCode(op.getOutWsStudentMkHomeLanguage());
				mdForm.getStudent().getDisability().setCode(Integer.toString(op.getOutStudentAnnualRecordMkDisabilityTypeCode()));
				mdForm.getStudent().setShareContactDetails(op.getOutStudentAnnualRecordFellowStudentFlag());
				mdForm.getStudent().setLastRegYear(Integer.toString(op.getOutStudentAnnualRecordPrevEducationalInstitutionYr()));
				mdForm.getStudent().getOtherUniversity().setCode(op.getOutStudentAnnualRecordMkOtherEducationalInstitCode());
				mdForm.getStudent().getPrevInstitution().setCode(op.getOutStudentAnnualRecordMkPrevEducationalInstitCode());
				mdForm.getStudent().getOccupation().setCode(op.getOutStudentAnnualRecordMkOccupationCode());
				mdForm.getStudent().getEconomicSector().setCode(op.getOutStudentAnnualRecordMkEconomicSectorCode());
				mdForm.getQual().setSpecCode(op.getOutStudentAnnualRecordSpecialityCode());
//				mdForm.getStudent().getExam().setCode(op.getOutWsExamCentreCode());
//				mdForm.getStudent().getExam().setDesc(op.getOutWsExamCentreEngDescription());
//				mdForm.setSelectedExamCentre(mdForm.getStudent().getExam().getCode()+mdForm.getStudent().getExam().getDesc());
				mdForm.getStudent().setPhaseout(Short.toString(op.getOutWsStudentPhasedOutStatus()));
				//log.debug("MDRegistrationsAction - displayAdditionPersonal - Sruaf01sStudyUnitAddition - Qual Phased Out: " + mdForm.getStudent().getPhaseout());

				mdForm.getStudent().setStatusCode(op.getOutStudentAnnualRecordStatusCode());
				mdForm.getStudent().setEmailAddress(op.getOutEmailToCsfStringsString132());
				
				if (!"".equals(result)){
					//error result - move input back to screen values
				}else{
				}
				//if any error message =result
				return result;
			}	

	public String registerStep1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		/** Edmund Debug **/
		//log.debug("MDRegistrationAction - Entering registerStep1");
	    
		MdRegistrationsForm mdForm = (MdRegistrationsForm) form;
		ActionMessages messages = new ActionMessages();
		GeneralMethods gen = new GeneralMethods();
		MdRegistrationsQueryDAO dao = new MdRegistrationsQueryDAO();
		
		if (mdForm.getFromPage() == null){
			//Error: Session empty
			return emptySession(mapping,form,request,response);
		}else{
			mdForm.setFromPage("step1");
		}

		mdForm.setStudentNumberSearchAttemp(0);
		// ---------- Check input
		
		if ("-1".equals(mdForm.getSelectedExamCentre()) || mdForm.getSelectedExamCentre() == null){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", "Please select an Exam centre, it is compulsory."));
			addErrors(request, messages);
			setDropdownListsStep1(request,mdForm.getApplyType());
			return "step1forward";
		}
		return "stepCourierforward";
	}
	
	public String registerStepCourier(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		MdRegistrationsForm mdForm = (MdRegistrationsForm) form;
		ActionMessages messages = new ActionMessages();
		GeneralMethods gen = new GeneralMethods();
		MdRegistrationsQueryDAO dao = new MdRegistrationsQueryDAO();
		
		// if student number is null, block registration
		if(mdForm.getStudent().getNumber() == null || "".equals(mdForm.getStudent().getNumber())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
			new ActionMessage("message.generalmessage", "You performed and invalid action. Please log on again to retry."));
			addErrors(request, messages);
			return "display";
		}

		if (mdForm.getFromPage() == null){
			//Error: Session empty
			return emptySession(mapping,form,request,response);
		}else{
			mdForm.setFromPage("stepCourier");
		}

		// Courier address
		if (mdForm.getStudent().getCourierAddress().getLine1() == null || "".equals(mdForm.getStudent().getCourierAddress().getLine1().trim())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please enter a courier address, it is compulsory." ));
			addErrors(request, messages);
			return "stepCourierforward";
		}else if (mdForm.getStudent().getCourierAddress().getLine1() != null && !"".equals(mdForm.getStudent().getCourierAddress().getLine1().trim())){
			mdForm.getStudent().getCourierAddress().setLine1(mdForm.getStudent().getCourierAddress().getLine1().trim());
			if (mdForm.getStudent().getCourierAddress().getLine1().length() > 28){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Courier Address line 1 is too long. Please enter 28 characters or less. " ));
				addErrors(request, messages);
				return "stepCourierforward";
			}
			if (mdForm.getStudent().getCourierAddress().getLine2() == null || "".equals(mdForm.getStudent().getCourierAddress().getLine2().trim())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Please enter at least 2 lines of the courier address." ));
				addErrors(request, messages);
				return "stepCourierforward";
			}
			if (mdForm.getStudent().getCourierAddress().getLine2() != null && !"".equals(mdForm.getStudent().getCourierAddress().getLine2().trim())){
				mdForm.getStudent().getCourierAddress().setLine2(mdForm.getStudent().getCourierAddress().getLine2().trim());
				if (mdForm.getStudent().getCourierAddress().getLine2().length() > 28){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Courier Address line 2 is too long. Please enter 28 characters or less. " ));
					addErrors(request, messages);
					return "stepCourierforward";
				}
			}
			if (mdForm.getStudent().getCourierAddress().getLine3() != null && !"".equals(mdForm.getStudent().getCourierAddress().getLine3().trim())){
				mdForm.getStudent().getCourierAddress().setLine3(mdForm.getStudent().getCourierAddress().getLine3().trim());
				if (mdForm.getStudent().getCourierAddress().getLine3().length() > 28){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Courier Address line 3 is too long. Please enter 28 characters or less. " ));
					addErrors(request, messages);
					return "stepCourierforward";
				}
			}
			if (mdForm.getStudent().getCourierAddress().getLine4() != null && !"".equals(mdForm.getStudent().getCourierAddress().getLine4().trim())){
				mdForm.getStudent().getCourierAddress().setLine4(mdForm.getStudent().getCourierAddress().getLine4().trim());
				if (mdForm.getStudent().getCourierAddress().getLine4().length() > 28){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Courier Address line 4 is too long. Please enter 28 characters or less. " ));
					addErrors(request, messages);
					return "stepCourierforward";
				}
			}
			if (mdForm.getStudent().getCourierAddress().getLine5() != null && !"".equals(mdForm.getStudent().getCourierAddress().getLine5().trim())){
				mdForm.getStudent().getCourierAddress().setLine5(mdForm.getStudent().getCourierAddress().getLine5().trim());
				if (mdForm.getStudent().getCourierAddress().getLine5().length() > 28){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Courier Address line 5 is too long. Please enter 28 characters or less. " ));
					addErrors(request, messages);
					return "stepCourierforward";
				}
			}
			if (mdForm.getStudent().getCourierAddress().getLine6() != null && !"".equals(mdForm.getStudent().getCourierAddress().getLine6().trim())){
				mdForm.getStudent().getCourierAddress().setLine6(mdForm.getStudent().getCourierAddress().getLine6().trim());
				if (mdForm.getStudent().getCourierAddress().getLine6().length() > 28){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Courier Address line 6 is too long. Please enter 28 characters or less. " ));
					addErrors(request, messages);
					return "stepCourierforward";
				}
			}
			
			//check courier postal code
			if("1015".equals(mdForm.getStudent().getCountry().getCode())){
				if (mdForm.getStudent().getCourierAddress().getAreaCode() == null || "".equals(mdForm.getStudent().getCourierAddress().getAreaCode().trim())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Enter your courier postal code."));
				addErrors(request, messages);
				return "stepCourierforward";
				} else if(! gen.isNumeric(mdForm.getStudent().getCourierAddress().getAreaCode()) || mdForm.getStudent().getCourierAddress().getAreaCode().trim().length()!=4){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Courier postal code must consist of 4 numerical characters."));
					addErrors(request, messages);
					return "stepCourierforward";
				}
			}
			//check courier contact number
			if (mdForm.getStudent().getContactNr() == null || "".equals(mdForm.getStudent().getContactNr().trim())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Enter your courier contact number."));
				addErrors(request, messages);
				return "stepCourierforward";
			}
			if (mdForm.getStudent().getContactNr()!= null){
				mdForm.getStudent().setContactNr(mdForm.getStudent().getContactNr().replaceAll(" ",""));
			}
			if(!isPhoneNumber(mdForm.getStudent().getContactNr())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
	        			new ActionMessage("message.generalmessage", "Your courier contact number may consist of a dash or +, the rest must be numeric."));
				addErrors(request, messages);
				return "stepCourierforward";
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
					return "stepCourierforward";
			}
		}

		if (mdForm.getStudent().getContactNr() != null && !"".equals(mdForm.getStudent().getContactNr().trim())){
			if(!isPhoneNumber(mdForm.getStudent().getContactNr())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
	        			new ActionMessage("message.generalmessage", "Your home phone number may consist of a dash or +, the rest must be numeric."));
				addErrors(request, messages);
				return "stepCourierforward";
			}
		}
		
		mdForm.setStudentNumberSearchAttemp(0);
		// ---------- Check input
		String underpost = " ";
		String code = " ";
		String qtype = " ";
		String qkat = " ";
		
		// Proposed qualification
		code = dao.validateQualification(mdForm.getQual().getQualCode().substring(0,5));
		underpost = code.substring(0,1);
		qtype = code.substring(1,2);
		qkat = code.substring(2);
	
		mdForm.getQual().setUnderpostGrad(underpost);
		mdForm.getQual().setQualType(qtype);
		mdForm.getQual().setQualKat(qkat);

		if("".equalsIgnoreCase(mdForm.getQual().getSpecCode()) || mdForm.getQual().getSpecCode() ==null){
			mdForm.getQual().setSpecCode(" ");
		}
		
		String qualif = mdForm.getQual().getQualCode();
		String spes = mdForm.getQual().getSpecCode();
		
		String listtype = dao.validateSpeciality(qualif, spes);
		mdForm.setListType(listtype);
		if ("T".equalsIgnoreCase(listtype)){
			
		}
		
		//read dao.Qualification rules
		String tt="";

		ArrayList llist = new ArrayList();
		for (int ii=1;ii<35;ii++){
			tt=dao.getQualRules(mdForm.getQual().getQualCode(), mdForm.getQual().getSpecCode(), ii);
//			if(!"".equalsIgnoreCase(tt)){
			if(tt!=null){
				llist.add(tt);
			}
		}
		if (llist.isEmpty()){
			llist.add(" ");
			llist.add(" ");
			llist.add(" ");
			llist.add(" ");
		}
		mdForm.setQualifRules(llist);
		
		// First time read study units for qualification
		// If error with this list occurs must be found here
		if ("S".equalsIgnoreCase(listtype)){
			setUpsunList(mdForm, request);
			if(request.getAttribute("regStudyUnits")==null){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "No open registration periods for your qualification."));
				addErrors(request, messages);
			}
		}
		
		//If it is study unit addition transaction
		if (mdForm.isStudyUnitAddition())
		{
			Student student = mdForm.getStudent();
			
			mdForm.setCurrentRegStudyUnitsList(dao.getCurrentRegStudyUnitsList(
													student.getNumber(), 
													student.getAcademicYear(), 
													student.getStatusCode()));
			
		}
		
		// add number of entries for screen table
		ArrayList list = new ArrayList();
		if (mdForm.getNumberOfUnits()>0 ){
			if (mdForm.getSelectedAdditionalStudyUnits() ==null || mdForm.getSelectedAdditionalStudyUnits().isEmpty()){
				for (int i = 0; i < mdForm.getNumberOfUnits(); i++) {
					list.add("");
				}
			mdForm.setSelectedAdditionalStudyUnits(list);
			}
		}
		/* default complete qual to N */
		mdForm.setCompleteQual("N");

		/** Edmund Debug **/
		//log.debug("MDRegistrationAction - stepCourierforward - Return to step2forward");
		return "step2forward";
	}

	
	public String registerStep2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		/** Edmund Debug **/
		//log.debug("MDRegistrationAction - - Entering registerStep2");
		// 
		MdRegistrationsForm mdForm = (MdRegistrationsForm) form;
		ActionMessages messages = new ActionMessages();
		GeneralMethods gen = new GeneralMethods();
		MdRegistrationsQueryDAO dao = new MdRegistrationsQueryDAO();
		
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
		code = dao.validateQualification(mdForm.getQual().getQualCode().substring(0,5));
		underpost = code.substring(0,1);
		qtype = code.substring(1,2);
		qkat = code.substring(2);
	
		mdForm.getQual().setUnderpostGrad(underpost);
		mdForm.getQual().setQualType(qtype);
		mdForm.getQual().setQualKat(qkat);

		if("".equalsIgnoreCase(mdForm.getQual().getSpecCode()) || mdForm.getQual().getSpecCode() ==null){
			mdForm.getQual().setSpecCode(" ");
		}
		
		String qualif = mdForm.getQual().getQualCode();
		String spes = mdForm.getQual().getSpecCode();
		
		String listtype = dao.validateSpeciality(qualif, spes);
		mdForm.setListType(listtype);
		if ("T".equalsIgnoreCase(listtype)){
			
		}
		
		//read dao.Qualification rules
		String tt="";

		ArrayList llist = new ArrayList();
		for (int ii=1;ii<35;ii++){
			tt=dao.getQualRules(mdForm.getQual().getQualCode(), mdForm.getQual().getSpecCode(), ii);
//			if(!"".equalsIgnoreCase(tt)){
			if(tt!=null){
				llist.add(tt);
			}
		}
		if (llist.isEmpty()){
			llist.add(" ");
			llist.add(" ");
			llist.add(" ");
			llist.add(" ");
		}
		mdForm.setQualifRules(llist);
		
		// First time read study units for qualification
		// If error with this list occurs must be found here
		if ("S".equalsIgnoreCase(listtype)){
			setUpsunList(mdForm, request);
			if(request.getAttribute("regStudyUnits")==null){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "No open registration periods for your qualification."));
				addErrors(request, messages);
			}
		}
		
		//If it is study unit addition transaction
		if (mdForm.isStudyUnitAddition())
		{
			Student student = mdForm.getStudent();
			
			mdForm.setCurrentRegStudyUnitsList(dao.getCurrentRegStudyUnitsList(
													student.getNumber(), 
													student.getAcademicYear(), 
													student.getStatusCode()));
			
		}
		
		// add number of entries for screen table
		ArrayList list = new ArrayList();
		if (mdForm.getNumberOfUnits()>0 ){
			if (mdForm.getSelectedAdditionalStudyUnits() ==null || mdForm.getSelectedAdditionalStudyUnits().isEmpty()){
				for (int i = 0; i < mdForm.getNumberOfUnits(); i++) {
					list.add("");
				}
			mdForm.setSelectedAdditionalStudyUnits(list);
			}
		}
		/* default complete qual to N */
		mdForm.setCompleteQual("N");

		return "step2forward";
	}

	public String registerStep3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		MdRegistrationsForm mdForm = (MdRegistrationsForm) form;
		ActionMessages messages = new ActionMessages();
		GeneralMethods gen = new GeneralMethods();
		MdRegistrationsQueryDAO dao = new MdRegistrationsQueryDAO();
		
		if (mdForm.getFromPage() == null){
			//Error: Session empty
			return emptySession(mapping,form,request,response);
		}else{
			mdForm.setFromPage("page3");
		}

		mdForm.setStudentNumberSearchAttemp(0);
		// ---------- Check input
		
		ArrayList list = new ArrayList();
		
		mdForm.setRegPeriodOpen0("Y");
		mdForm.setRegPeriodOpen1("Y");
		mdForm.setRegPeriodOpen2("Y");
		Integer jj=0;
		String ccode="";
		jj=mdForm.getSelectedAdditionalStudyUnits().size();
		for (int i = 0; i < jj; i++) {
			StudyUnit su = new StudyUnit();
			ccode=mdForm.getSelectedAdditionalStudyUnits().get(i).toString();
			ccode=ccode.toUpperCase();
			su = dao.getStudyUnitInfoForOpenRegPeriod(ccode, mdForm.getRegPeriodOpen1(),mdForm.getRegPeriodOpen2(),mdForm.getRegPeriodOpen0(), mdForm.getStudent().getAcademicYear()); 
			//su.setCode(mdForm.getSelectedAdditionalStudyUnits().get(i).toString());
			if(!"-1".equals(mdForm.getSelectedAdditionalStudyUnits().get(i).toString()) && !"".equals(mdForm.getSelectedAdditionalStudyUnits().get(i).toString())){
				su.setNdp("N");
				list.add(su);
			}
		}
		mdForm.setAdditionalStudyUnits(list);

		if (mdForm.getAdditionalStudyUnits().isEmpty()){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", "Please indicate study unit(s) you would like to register for."));
			addErrors(request, messages);
			//setDropdownListsStep2(request);
			if ("S".equalsIgnoreCase(mdForm.getListType())){
			setUpsunList(mdForm, request);
			}
			return "step2forward";
		}else{
			//list = mdForm.getSelectedAdditionalStudyUnits();
		}

		return "step3forward";
	}

	public String registerStep4(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	
		// call from M&D page next = address page = step2
		MdRegistrationsForm mdForm = (MdRegistrationsForm) form;
		String qualif= mdForm.getQual().getQualCode();
		ActionMessages messages = new ActionMessages();
		GeneralMethods gen = new GeneralMethods();
		

		setDropdownListsStep3(request, qualif);
		return "step4forward";
	}

	public ActionForward getPostalCode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MdRegistrationsForm mdForm = (MdRegistrationsForm) form;
		response.setContentType("text/text;charset=utf-8");
		response.setHeader("cache-control", "no-cache");
		PrintWriter out = response.getWriter();
		//out.println("Hello " + ajaxForm.getPostalCode());
		out.flush();
		return null;
	}

	public ActionForward getPostalCodeList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MdRegistrationsForm mdForm = (MdRegistrationsForm) form;
		setUpPostalCodeList(request);

		StringBuffer stringBuff = new StringBuffer();

	    ArrayList list = (ArrayList) request.getAttribute("postalcodelist");
	    if (list != null && list.size()> 0) {
	      Iterator codes = list.iterator();
	      while( codes.hasNext() ) {
	    	 LabelValueBean options = (LabelValueBean)codes.next();
	    	 //stringBuff.append("  <option '"+options.getValue()+"'>" +
	         //options.getLabel() + "</option>\n");

	    	 stringBuff.append("<option>");
             stringBuff.append(options.getLabel());
             stringBuff.append("</option>");
	      }
	    }

	    response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(stringBuff.toString());
	   // out.println("</select>\n");
	    out.flush();
	    out.close();
		return null;
	}

	public String postalCodeSelected(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		//log.debug("Action - postalCodeSelected");
		MdRegistrationsForm mdForm = (MdRegistrationsForm) form;

		// get postalcode details
		String code = mdForm.getSearchResult().substring(0, 4);
		mdForm.getStudent().getCourierAddress().setAreaCode(code);

		mdForm.setSearchSuburb("");
		mdForm.setSearchTown("");
		mdForm.setSearchResult("");

		return "stepCourierforward";
	}

	public ActionForward listPostalCode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		//log.debug("Action - listPostalCode");
		MdRegistrationsForm mdForm = (MdRegistrationsForm) form;
		ActionMessages messages = new ActionMessages();

		if ("".equals(mdForm.getSearchSuburb()) && "".equals(mdForm.getSearchTown())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
						"Enter either a Suburb or Town."));
			addErrors(request, messages);
			return mapping.findForward("searchforward");
		}

		MdRegistrationsQueryDAO dao = new MdRegistrationsQueryDAO();
		ArrayList<LabelValueBean> list = new ArrayList<LabelValueBean>();
		list=dao.getPostalCodes(mdForm.getSearchSuburb(), mdForm.getSearchTown());
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


	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response, int searchField)
			throws Exception {

		//log.debug("Action - Search");
		MdRegistrationsForm mdForm = (MdRegistrationsForm) form;
		mdForm.setSearchListIdentifier(searchField);

		return mapping.findForward("searchforward");
	}

	public ActionForward cancelPostalCode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		//log.debug("Action - cancelPostalCode");
		MdRegistrationsForm mdForm = (MdRegistrationsForm) form;

		mdForm.setSearchSuburb("");
		mdForm.setSearchTown("");
		mdForm.setSearchResult("");

		return mapping.findForward("stepCourierforward");
	}
	
	public ActionForward cancelSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		//log.debug("Action - cancelSearch");
		MdRegistrationsForm mdForm = (MdRegistrationsForm) form;

		setDropdownListsStep1(request,mdForm.getApplyType());

		return mapping.findForward("step1forward");
	}


	public ActionForward saveRegistration(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

	
		MdRegistrationsForm mdForm = (MdRegistrationsForm) form;
		ActionMessages messages = new ActionMessages();
		GeneralMethods gen = new GeneralMethods();
		MdRegistrationsQueryDAO dao = new MdRegistrationsQueryDAO();

		String qualif = mdForm.getQual().getQualCode();
		String spescode = mdForm.getQual().getSpecCode();
		
			
		//test agreement
		//if (mdForm.getPayment() == null || "".equals(mdForm.getPayment().trim())){
		//	messages.add(ActionMessages.GLOBAL_MESSAGE,
		//			new ActionMessage("message.generalmessage", "Indicate Please indicate your payment type."));
		//	addErrors(request, messages);
		//	return mapping.findForward("step4forward");
		//}
		if (mdForm.getAgree() == null || "".equals(mdForm.getAgree().trim())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Indicate your agreement to the declaration and undertaking."));
			addErrors(request, messages);
			return mapping.findForward("step4forward");
		}
		//test agreement flag
		if (!"Y".equalsIgnoreCase(mdForm.getAgree())){
			return mapping.findForward("walkthroughforward");
		}

		//count times button pressed
		mdForm.setStudentNumberSearchAttemp(mdForm.getStudentNumberSearchAttemp()+1);
		if (mdForm.getStudentNumberSearchAttemp() > 1){
			//String nrpress = Integer.toString(mdForm.getStudentNumberSearchAttemp());
			messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", "You have already submitted the registration " ));
			addErrors(request, messages);
			return mapping.findForward("walkthroughforward");
		}	

		String errorMsg="";
		
		/* Set submission time stamp */
		Date date = new java.util.Date();
		String displayDate = (new java.text.SimpleDateFormat("EEEEE dd MMMMM yyyy hh:mm:ss").format(date).toString());
		request.setAttribute("apptime",displayDate);
		request.setAttribute("email",mdForm.getStudent().getEmailAddress());
		
		
		//If it is study unit addition transaction
		if (mdForm.isStudyUnitAddition())
		{
			errorMsg = updateStudentNrAddition(mdForm);	
		}
		else
			//Do execute F138; action="A", user=99998 
		{
			errorMsg = updateStudentNr(mdForm);	
			
			if("".equals(errorMsg)){
				Boolean didCourierChange = false;
				//Performance code - Test first line, if it changed, regard all other lines as changed. if not, test other lines anyway.
				if (!mdForm.getStudent().getCourierAddressPrev().getLine1().equalsIgnoreCase(mdForm.getStudent().getCourierAddress().getLine1())){
					didCourierChange = true;
					//log.debug("MDRegistrationAction - Courier Address Line 1 changed");
				}else { //Test the other lines if line 1 didn't change
					if ((!mdForm.getStudent().getCourierAddressPrev().getLine2().equalsIgnoreCase(mdForm.getStudent().getCourierAddress().getLine2()))
				 	|| (!mdForm.getStudent().getCourierAddressPrev().getLine3().equalsIgnoreCase(mdForm.getStudent().getCourierAddress().getLine3()))
					|| (!mdForm.getStudent().getCourierAddressPrev().getLine4().equalsIgnoreCase(mdForm.getStudent().getCourierAddress().getLine4()))
					|| (!mdForm.getStudent().getCourierAddressPrev().getLine5().equalsIgnoreCase(mdForm.getStudent().getCourierAddress().getLine5()))
					|| (!mdForm.getStudent().getCourierAddressPrev().getLine6().equalsIgnoreCase(mdForm.getStudent().getCourierAddress().getLine6()))
					|| (!mdForm.getStudent().getCourierAddressPrev().getAreaCode().equalsIgnoreCase(mdForm.getStudent().getCourierAddress().getAreaCode()))){
						didCourierChange = true;
						//log.debug("MDRegistrationAction - Courier Address Lines changed");
					}else{
						//log.debug("MDRegistrationAction - Courier Address Lines didn't change");
					}
				}
				if (didCourierChange){ //Courier Address thus changed, so lets do update
					boolean writeResult = dao.writeAddressCourier(mdForm.getStudent());
					if(!writeResult){
						//log.debug("MDRegistrationAction - Failed Updating Courier Address");
						errorMsg = "Failed Updating Courier Address";
					}else{
						// audit trail
						dao.writeChangedStatusToDb("cour-upd", mdForm.getStudent().getNumber(), "IS");
					}
				}else{
					//log.debug("MDRegistrationAction - Courier Contact Address didn't change");
				}
				
			}
			
			if("".equals(errorMsg)){
				//Check if Contact No has changed. If it did, write to DB and Audit Trail
				if (!mdForm.getStudent().getContactNrPrev().equalsIgnoreCase(mdForm.getStudent().getContactNr())){
					//log.debug("MDRegistrationAction - Courier Contact No changed");
					boolean writeResult = dao.writeAddressContactNr(mdForm.getStudent());
					if(!writeResult){
						//log.debug("MDRegistrationAction - Failed Updating Courier Contact Nr");
						errorMsg = "Failed Updating Courier Contact Nr";
					}else{
						// audit trail
						dao.writeChangedStatusToDb("courNr-upd", mdForm.getStudent().getNumber(), "IS");
					}
				}
			}
		}

		if(!"".equals(errorMsg)){
			request.setAttribute("errormessage",errorMsg);
			return mapping.findForward("nosuccessforward");
		}else{
			//writeWorkflow(mdForm,date);
			//F138 writes workflow for successful temp registration/full registration
		}

		request.setAttribute("amount",mdForm.getQuestion1());
		
		// Write sakai event
//		eventTrackingService.post(
//				eventTrackingService.newEvent(EventTrackingTypes.EVENT_STUDENTREGISTRATION_APPLYFORSTUDENTNR, toolManager.getCurrentPlacement().getContext(), false));

    	// clear form variables
	    if("Y".equalsIgnoreCase(mdForm.getTempreg())){
	    	resetForm(mdForm);
	    	return mapping.findForward("tempsuccessforward");
	    }else{
	    	resetForm(mdForm);
	    	return mapping.findForward("successforward");
	    }
	}

	public ActionForward saveForadvisor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

	
		MdRegistrationsForm mdForm = (MdRegistrationsForm) form;
		ActionMessages messages = new ActionMessages();
		GeneralMethods gen = new GeneralMethods();
		/* Set submission time stamp */
		Date date = new java.util.Date();
		String displayDate = (new java.text.SimpleDateFormat("EEEEE dd MMMMM yyyy hh:mm:ss").format(date).toString());
		request.setAttribute("apptime",displayDate);
		request.setAttribute("email",mdForm.getStudent().getEmailAddress());
		
		if (mdForm.getStudent().getNumber() == null || "".equals(mdForm.getStudent().getNumber())){
			mdForm.setNoStuNo(true);
			request.setAttribute("errormessage","Student Number not found");
			return mapping.findForward("nosuccessforward");
		}else{
			if (mdForm.isStudyUnitAddition())			{
				writeAdditionWorkflow(mdForm,date);	
			}else{
				writeWorkflow(mdForm,date);
			}
		}
		return mapping.findForward("walkthroughforward");
	}

	public ActionForward nextStep(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
			throws Exception {

		MdRegistrationsForm mdForm = (MdRegistrationsForm) form;
		ActionMessages messages = new ActionMessages();
		mdForm.setApplyType("F");

		String nextPage = "";
		if ("step1".equalsIgnoreCase(request.getParameter("page"))){
			nextPage = registerStep1(mapping,form,request, response);
		}else if ("stepCourier".equalsIgnoreCase(request.getParameter("page"))){
			nextPage = registerStepCourier(mapping,form,request, response);
		}else if ("step2".equalsIgnoreCase(request.getParameter("page"))){
			setDropdownListsStep2(request);
 			nextPage = registerStep3(mapping,form,request, response);
		}else if ("step3".equalsIgnoreCase(request.getParameter("page"))){
 			nextPage = registerStep4(mapping,form,request, response);
		}else if ("successno".equalsIgnoreCase(request.getParameter("page"))){
			nextPage = "walkthroughforward";
		}else if ("tmpsuccess".equalsIgnoreCase(request.getParameter("page"))){
			nextPage = "walkthroughforward";
		}else if ("success".equalsIgnoreCase(request.getParameter("page"))){
			//nextPage = "display";
			nextPage = "walkthroughforward";
		}else if ("search".equalsIgnoreCase(request.getParameter("page"))){
			if (mdForm.getSearchResult().length() < 4){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", "Invalid postal code, should be 4 characters. Please try again."));
				addErrors(request, messages);
				return mapping.findForward("searchforward");
			}else{
				setDropdownListsCourier(request);
				nextPage = postalCodeSelected(mapping, form, request, response);
			}
		}
		return mapping.findForward(nextPage);
	}
	
	public ActionForward back(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
			throws Exception {

		MdRegistrationsForm mdForm = (MdRegistrationsForm) form;
		String prevPage = "";
		String qualif = mdForm.getQual().getQualCode();

		if ("stepCourier".equalsIgnoreCase(request.getParameter("page"))) {
			setDropdownListsStep1(request,"F");
			prevPage = "step1forward";
		}else if ("step2".equalsIgnoreCase(request.getParameter("page"))) {
			
			prevPage = "stepCourierforward";
		}else if ("step3".equalsIgnoreCase(request.getParameter("page"))) {
			setUpsunList(mdForm, request);
			prevPage = "step2forward";
		}else if ("step4".equalsIgnoreCase(request.getParameter("page"))) {
			prevPage = "step3forward";
		}else if ("successno".equalsIgnoreCase(request.getParameter("page"))) {
			setUpsunList(mdForm, request);
 			prevPage = "step2forward";
		}else if ("search".equalsIgnoreCase(request.getParameter("page"))) {
			prevPage = "stepCourierforward";
		}
		return mapping.findForward(prevPage);
	}

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

    	//log.debug("In Execute - getPostalCode = " + request.getParameter("getPostalCode") + " - action.search3 = " + request.getParameter("action.search3"));
 
    	if (request.getParameter("getPostalCode") != null)
        {
    		//log.debug("In Execute - return getPostalCodeList");
        	return getPostalCodeList(mapping, form, request, response);
        }else if (request.getParameter("action.search3") != null){
        	//log.debug("In Execute - return search");
        	return search(mapping, form, request, response, 3);
        }
    	//log.debug("In Execute - return super.execute");
        return super.execute(mapping, form, request, response);
   }
    
	public ActionForward cancel(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		MdRegistrationsForm mdForm = (MdRegistrationsForm) form;
		resetForm(mdForm);

		return mapping.findForward("walkthroughforward");
	}
	
	private void setDropdownListsStep1(HttpServletRequest request, String applyType) throws Exception{

		setUpQualList(request,applyType);
		setUpDisabilityList(request);
		setUpTitleList(request);
		/* setup numbers list */
		setupNumbersList(request);
		setUpExaminationCentreList(request);
	}

	private void setDropdownListsStep2(HttpServletRequest request) throws Exception{

		setUpCountryList(request);
		setUpExaminationCentreList(request);
	}
	
	private void setDropdownListsCourier(HttpServletRequest request) throws Exception{
		setUpPostalCodeList(request);
	}

	private void setDropdownListsStep3(HttpServletRequest request, String qualif) throws Exception{

		setUpHomeLanguageList(request);
		setUpNationalityList(request);
		setUpPopulationGroupList(request);
		setUpOccupationList(request);
		setUpEconomicSectorList(request);
		setUpOtherUniversityList(request);
		setUpExaminationCentreList(request);
		setUpProvinceList(request);
		setUpSpesList(request, qualif);
		setPrevActivityList(request);
	}

	public ActionForward reset(ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		//Type cast the incoming form to MdRegistrationsForm and reset 
		MdRegistrationsForm mdForm = (MdRegistrationsForm) form;  
		resetForm(mdForm);		

		return mapping.findForward("display");
	}

	private void setupNumbersList(HttpServletRequest request){
		/* setup numbers list */
		ArrayList list = new ArrayList();
		for (int i = 1; i <12; i++) {
			list.add(new LabelValueBean(Integer.toString(i),Integer.toString(i)));
			request.setAttribute("numbers", list);
		}
	}

	private void setUpsunList(MdRegistrationsForm form,HttpServletRequest request) throws Exception{
		MdRegistrationsQueryDAO db = new MdRegistrationsQueryDAO();
		ArrayList list = new ArrayList();
		String acadyear= form.getStudent().getAcademicYear();
		
		/** Edmund Debug **/
		//log.debug("MDRegistrationsAction - setUpsunList - Qual Type: " + form.getQual().getUnderpostGrad());
		
		if ("M".equalsIgnoreCase(form.getQual().getUnderpostGrad()) || "D".equalsIgnoreCase(form.getQual().getUnderpostGrad())){
			
			//setRegPeriodForScreenDisplay(form,request,false);

			/* ALL for POSTGRADUATE */
			/* SPECIFIC for POSTGRADUATE */
			if ("A".equalsIgnoreCase(form.getListType()) || "S".equalsIgnoreCase(form.getListType())){
				/** Edmund Debug **/
				//log.debug("MDRegistrationsAction - setUpsunList - ListType: " + form.getListType());
				list = db.getSpecificSUList(form.getQual().getQualCode(),form.getQual().getSpecCode(),true, acadyear);
				list = this.setStudyUnitLists(list,0, request);
				if (!list.isEmpty()){
					/** Edmund Debug **/
					//log.debug("MDRegistrationsAction - setUpsunList - regStudyUnits List is Not Empty");
					list.add(0,new LabelValueBean("Select a study unit","-1"));
					//request.setAttribute("list", list);
					request.setAttribute("regStudyUnits", list);
				}else{
					list.add(0,new LabelValueBean("No Study Units Available for this Academic Year","-1"));
					/** Edmund Debug **/
					//log.debug("MDRegistrationsAction - setUpsunList - regStudyUnits List is Empty");
				}
			}
		}
	}

	private void setUpQualList(HttpServletRequest request, String appsType) throws Exception{

		MdRegistrationsQueryDAO dao = new MdRegistrationsQueryDAO();
		List list = dao.getQualList(appsType);
		list.add(0,new LabelValueBean("Select from Menu","-1"));
		request.setAttribute("quallist",list);
	}

	private void setUpSpesList(HttpServletRequest request, String qualif) throws Exception{

		MdRegistrationsQueryDAO dao = new MdRegistrationsQueryDAO();
		List list = dao.getSpesList(qualif);
		list.add(0,new LabelValueBean("Select from Menu","-1"));
		request.setAttribute("speslist",list);
	}

	private ArrayList setStudyUnitLists(ArrayList list, int index, HttpServletRequest request){
		StudyUnit su = new StudyUnit();
		ArrayList resultList = new ArrayList();
		String displayValue = "";
		//HashMap studyUnitMap = new HashMap(500);


		for (int i = 0; i < list.size(); i++) {
			su = (StudyUnit)list.get(i);

			//setup list for screen display
			displayValue = su.getCode()+", "+su.getDesc();
			resultList.add(new org.apache.struts.util.LabelValueBean(displayValue,su.getCode()));
		}

		return resultList;
	}

	private void setUpTitleList(HttpServletRequest request) throws Exception{

		MdRegistrationsQueryDAO dao = new MdRegistrationsQueryDAO();
		List list = dao.getTitles();
		list.add(0,new LabelValueBean("Select from Menu","-1"));
		request.setAttribute("titlelist",list);
	}

	private void setUpDisabilityList(HttpServletRequest request) throws Exception{

		MdRegistrationsQueryDAO dao = new MdRegistrationsQueryDAO();
		List list = dao.getDisabilities();
		// Add South Africa as first entry
		list.add(0,new org.apache.struts.util.LabelValueBean("Not Applicable","1@Not Applicable"));
		request.setAttribute("disabilitylist",list);
	}

	private void setUpCountryList(HttpServletRequest request) throws Exception{

		MdRegistrationsQueryDAO dao = new MdRegistrationsQueryDAO();
		List list = dao.getCountries();
		// Add South Africa as first entry
		//list.add(0,new org.apache.struts.util.LabelValueBean("South Africa","1015South Africa"));
		list.add(0,new LabelValueBean("Select from Menu","-1"));
		request.setAttribute("countrylist",list);
	}

	private void setUpExaminationCentreList(HttpServletRequest request) throws Exception{

		MdRegistrationsQueryDAO dao = new MdRegistrationsQueryDAO();
		List list = dao.getExaminationCentreList();
		list.add(0,new LabelValueBean("Select from Menu","-1"));
		request.setAttribute("examlist",list);
	}

	private void setUpHomeLanguageList(HttpServletRequest request) throws Exception{

		MdRegistrationsQueryDAO dao = new MdRegistrationsQueryDAO();
		List list = dao.getLanguages();
		list.add(0,new LabelValueBean("Select from Menu","-1"));
		request.setAttribute("languagelist",list);
	}

	private void setUpNationalityList(HttpServletRequest request) throws Exception{

		MdRegistrationsQueryDAO dao = new MdRegistrationsQueryDAO();
		List list = dao.getCountries();
		list.add(0,new LabelValueBean("Select from Menu","-1"));
		request.setAttribute("nationalitylist",list);
	}

	private void setUpPopulationGroupList(HttpServletRequest request) throws Exception{

		MdRegistrationsQueryDAO dao = new MdRegistrationsQueryDAO();
		List list = dao.getPopulationGroups();
		list.add(0,new LabelValueBean("Select from Menu","-1"));
		request.setAttribute("populationlist",list);
	}

	private void setUpOccupationList(HttpServletRequest request) throws Exception{

		MdRegistrationsQueryDAO dao = new MdRegistrationsQueryDAO();
		List list = dao.getOccupations();
		list.add(0,new LabelValueBean("Select from Menu","-1"));
		request.setAttribute("occupationlist",list);
	}

	private void setUpEconomicSectorList(HttpServletRequest request) throws Exception{

		MdRegistrationsQueryDAO dao = new MdRegistrationsQueryDAO();
		List list = dao.getEconomicSectors();
		list.add(0,new LabelValueBean("Select from Menu","-1"));
		request.setAttribute("economicsectorlist",list);
	}

	private void setPrevActivityList(HttpServletRequest request) throws Exception{

		MdRegistrationsQueryDAO dao = new MdRegistrationsQueryDAO();
		List list = dao.getPrevActivity();
		list.add(0,new LabelValueBean("Select from Menu","-1"));
		request.setAttribute("prevactivitylist",list);
	}

	private void setUpOtherUniversityList(HttpServletRequest request) throws Exception{

		MdRegistrationsQueryDAO dao = new MdRegistrationsQueryDAO();
		List list = dao.getOtherUniversities();
		list.add(0,new LabelValueBean("Select from Menu","-1"));
		request.setAttribute("otheruniversitylist",list);
	}
	
	private void setUpProvinceList(HttpServletRequest request) throws Exception{

		MdRegistrationsQueryDAO dao = new MdRegistrationsQueryDAO();
		List list = dao.getProvinces();
		list.add(0,new LabelValueBean("Select from Menu","-1"));
		request.setAttribute("provincelist",list);
	}

	/**
	 * Populates the Postal code lookup list
	 *
	 * @param request
	 * @throws Exception
	 */
	private void setUpPostalCodeList(HttpServletRequest request) throws Exception{

		MdRegistrationsQueryDAO dao = new MdRegistrationsQueryDAO();
		List<LabelValueBean> list = new ArrayList<LabelValueBean>();

		list.add(0,new LabelValueBean("None","-1"));
		request.setAttribute("postalcodelist",list);
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

		MdRegistrationsForm mdForm = (MdRegistrationsForm) form;

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
		String test = "";
		int y=number.length();
		
		for (int i = 0; i < y; i++) {
			test= number.substring(i,i+1);
			if (test !=null && !"".equals(test)&& !" ".equals(test) && !"-".equals(test) && !"/".equals(test) && !"+".equals(test)){
				if ("0".equals(test) || "1".equals(test) || "2".equals(test) || "3".equals(test) || "4".equals(test) || "5".equals(test) || "6".equals(test) || "7".equals(test) || "8".equals(test)  || "9".equals(test)){
					//log.debug(test); 
				}else{
					result=false;
				}
			}
		}
		return result;
	}
	
	public String updateStudentNr(MdRegistrationsForm mdForm) throws Exception {

		  String result = "";

		 /* Thread.sleep(Long.parseLong("1000"));*/ // if two audit trails were to be written simultaneously

		  // ------------------------------- update F138 -------------------------
		  Srruf01sStudyUnitRegistration op2 = new Srruf01sStudyUnitRegistration();
		  operListener opl2 = new operListener();
		  op2.addExceptionListener(opl2);
		  op2.clear();
		  
		  //stuann
		  op2.setInStudentAnnualRecordMkAcademicYear(Short.parseShort(String.valueOf(mdForm.getStudent().getAcademicYear())));
		  op2.setInStudentAnnualRecordMkAcademicPeriod(Short.parseShort("1"));
		  op2.setInStudentAnnualRecordMkStudentNr(Integer.parseInt(mdForm.getStudent().getNumber()));
		  op2.setInStudentAnnualRecordMkDisabilityTypeCode(Short.parseShort(String.valueOf(mdForm.getStudent().getDisability().getCode())));//from screen
		  op2.setInStudentAnnualRecordFellowStudentFlag(mdForm.getStudent().getShareContactDetails());//from screen
		  //op2.setInStudentAnnualRecordPreviousUnisaFlag("N");
		  op2.setInStudentAnnualRecordMkHighestQualificationCode(mdForm.getQual().getQualCode());
		  op2.setInStudentAnnualRecordSpecialityCode(mdForm.getQual().getSpecCode());
		  op2.setInStudentAcademicRecordMkQualificationCode(mdForm.getQual().getQualCode());
		  op2.setInQualificationSpecialityTypeSpecialityCode(mdForm.getQual().getSpecCode());
		  
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
		  
		  op2.setInStudentAnnualRecordDespatchMethodCode("O"); //Hardcoded for Courier which is compulsory
		  op2.setInStudentAnnualRecordMkOccupationCode(mdForm.getStudent().getOccupation().getCode());//from screen
		  op2.setInStudentAnnualRecordMkEconomicSectorCode(mdForm.getStudent().getEconomicSector().getCode());//from screen
		  op2.setInStudentAnnualRecordRegDeliveryMethod("E"); //from screen
		  op2.setInStudentAnnualRecordSpecialityCode(mdForm.getQual().getSpecCode()); //from screen
		  op2.setInStudentAnnualRecordPrevEducationalInstitutionYr(Short.parseShort(mdForm.getStudent().getLastRegYear()));
		  
		  op2.setInStudentAcademicRecordMkStudentNr(Integer.parseInt(mdForm.getStudent().getNumber()));
		  op2.setInStudentAcademicRecordMkQualificationCode(mdForm.getQual().getQualCode());
		  
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
		  op2.setInWsStudentIdentityNr(mdForm.getStudent().getIdNumber());
		  op2.setInWsStudentGender(mdForm.getStudent().getGender());
		  op2.setInWsStudentMkNationality(mdForm.getStudent().getNationalty().getCode());//from screen
		  op2.setInWsStudentMkHomeLanguage(mdForm.getStudent().getHomeLanguage().getCode());//from screen
		  op2.setInWsStudentMkCorrespondenceLanguage(mdForm.getStudent().getLanguage());//from screen
		  op2.setInWsStudentSmartCardIssued("N");
		  //twin- no input
		  
		  //if ("P".equalsIgnoreCase(mdForm.getStudent().getLastStatus())){
		  op2.setInWsStudentPreviouslyPostGraduateFlag(mdForm.getStudent().getPrevPostgrad());
		  op2.setInWsStudentMkCountryCode(mdForm.getStudent().getCountry().getCode());

		  //study units
		  Integer jj=0;
		  jj=mdForm.getAdditionalStudyUnits().size();
		  for (int ii = 0; ii < jj; ii++) {
			  StudyUnit su = (StudyUnit) mdForm.getAdditionalStudyUnits().get(ii);
			  if ("A".equalsIgnoreCase(su.getLanguage().substring(0,1))){
					su.setLanguage("A");
			  }else {
					su.setLanguage("E");
			  }
			  op2.setInGSuStudentStudyUnitMkCourseStudyUnitCode(ii, su.getCode());
			  op2.setInGSuStudentStudyUnitLanguageCode(ii, su.getLanguage());
			  op2.setInGSuStudentStudyUnitSemesterPeriod(ii,Short.parseShort(su.getRegPeriod()));
			  op2.setInGSuNdpIefSuppliedFlag(ii, su.getNdp());
			  op2.setInGSuCsfLineActionBarAction(ii, "I");
		  }
		  // exam centre
		  op2.setInWsExamCentreCode(mdForm.getStudent().getExam().getCode());
 
		  // set email
		  op2.setInFaxOrEmailCsfStringsString1("E");
		  op2.setInEmailToCsfStringsString132(mdForm.getStudent().getEmailAddress());
		  //get this from QUAL inside A-reread all for internet
		  //op2.setInEmailFromCsfStringsString132("study-info@unisa.ac.za");
		  
		  //this flag read for repeat of self-help function F514 after confirmation of tempregistration
		  op2.setInExpertIefSuppliedFlag("Y");
		  
		  op2.setInCsfClientServerCommunicationsClientVersionNumber((short) 3);
		  op2.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);     
		  op2.setInCsfClientServerCommunicationsAction("A");
		  op2.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");  
		  op2.setInWsUserNumber(99998);
		  op2.setInSecurityWsFunctionNumber(514); //(999)
		  op2.setInWsWorkstationCode("internet");

		  op2.execute();
		
		  if (opl2.getException() != null) throw opl2.getException();
		  if (op2.getExitStateType() < 3) throw new Exception(op2.getExitStateMsg());
		  
		  String errorMessage = op2.getOutCsfStringsString500();
			
		  //log.debug("MDRegistrationsAction - updateStudentNr - Srruf01sStudyUnitRegistration(A) - getOutCsfStringsString500 = " + errorMessage);

		  errorMessage = op2.getOutCsfStringsString500();
		  // return error only exist on the following return codes
		  //switch (op2.getOutCsfClientServerCommunicationsReturnCode()) {
		  //case 2028: result = student-temp-registration
		  //case 2029: result = student-registration
		  //case all others, i.e. 2001-2020; result = errorMessage;
		  if (op2.getOutCsfClientServerCommunicationsReturnCode()==2028){
			  // use commMethod field for amount if temporary registration
			  //Total fees = 10,985.00. Min fee due = 10,985.00, Total credits = 240
			  mdForm.setQuestion1(errorMessage);
		  }
//		  if (op2.getOutCsfClientServerCommunicationsReturnCode()==48 || op2.getOutCsfClientServerCommunicationsReturnCode()==49 ){
		  if (op2.getOutCsfClientServerCommunicationsReturnCode()==2028 || op2.getOutCsfClientServerCommunicationsReturnCode()==2029 ){
			  // ALL OK
			  result = "";
			  mdForm.getStudent().setNumber(Integer.toString(op2.getOutStudentAnnualRecordMkStudentNr()));// convert int to String
			  mdForm.setNumberBack(Integer.toString(op2.getOutStudentAnnualRecordMkStudentNr()));
			  mdForm.setEmailBack(op2.getOutEmailToCsfStringsString132());
			  if (op2.getOutCsfClientServerCommunicationsReturnCode()==2028) {
				  mdForm.setTempreg("Y");
			  }else{
				  mdForm.setTempreg("N");
			  }
		  }else{
	  		  // Error Occurred
			  mdForm.setQuestion2(errorMessage);
	  		  result = errorMessage;
		  }
		  
		  return result;
		   
		}
	
	public String updateStudentNrAddition(MdRegistrationsForm mdForm) throws Exception {

		  String result = "";

		 /* Thread.sleep(Long.parseLong("1000"));*/ // if two audit trails were to be written simultaneously

		  // ------------------------------- update F?? -------------------------
		  Sruaf01sStudyUnitAddition op2 = new Sruaf01sStudyUnitAddition();
		  operListener opl2 = new operListener();
		  op2.addExceptionListener(opl2);
		  op2.clear();
		  
		  //stuann
		  op2.setInStudentAnnualRecordMkAcademicYear(Short.parseShort(String.valueOf(mdForm.getStudent().getAcademicYear())));
		  op2.setInStudentAnnualRecordMkAcademicPeriod(Short.parseShort("1"));
		  op2.setInStudentAnnualRecordMkStudentNr(Integer.parseInt(mdForm.getStudent().getNumber()));
		  op2.setInStudentAnnualRecordMkDisabilityTypeCode(Short.parseShort(String.valueOf(mdForm.getStudent().getDisability().getCode())));//from screen
		  op2.setInStudentAnnualRecordFellowStudentFlag(mdForm.getStudent().getShareContactDetails());//from screen
		  //op2.setInStudentAnnualRecordPreviousUnisaFlag("N");
		  op2.setInStudentAnnualRecordMkHighestQualificationCode(mdForm.getQual().getQualCode());
		  op2.setInStudentAnnualRecordSpecialityCode(mdForm.getQual().getSpecCode());
		  op2.setInStudentAcademicRecordMkQualificationCode(mdForm.getQual().getQualCode());
		  op2.setInQualificationSpecialityTypeSpecialityCode(mdForm.getQual().getSpecCode());
		  
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
		  //Set Despatch method to "O - Courier"
		  mdForm.getStudent().setDespatchMethod("O");
		  
		  op2.setInStudentAnnualRecordDespatchMethodCode( mdForm.getStudent().getDespatchMethod());
		  op2.setInStudentAnnualRecordMkOccupationCode(mdForm.getStudent().getOccupation().getCode());//from screen
		  op2.setInStudentAnnualRecordMkEconomicSectorCode(mdForm.getStudent().getEconomicSector().getCode());//from screen
		  op2.setInStudentAnnualRecordRegDeliveryMethod("E"); //from screen
		  op2.setInStudentAnnualRecordSpecialityCode(mdForm.getQual().getSpecCode()); //from screen
		  op2.setInStudentAnnualRecordPrevEducationalInstitutionYr(Short.parseShort(mdForm.getStudent().getLastRegYear()));
		  
		  op2.setInStudentAcademicRecordMkStudentNr(Integer.parseInt(mdForm.getStudent().getNumber()));
		  op2.setInStudentAcademicRecordMkQualificationCode(mdForm.getQual().getQualCode());
		  
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
		  op2.setInWsStudentIdentityNr(mdForm.getStudent().getIdNumber());
		  op2.setInWsStudentGender(mdForm.getStudent().getGender());
		  op2.setInWsStudentMkNationality(mdForm.getStudent().getNationalty().getCode());//from screen
		  op2.setInWsStudentMkHomeLanguage(mdForm.getStudent().getHomeLanguage().getCode());//from screen
		  op2.setInWsStudentMkCorrespondenceLanguage(mdForm.getStudent().getLanguage());//from screen
		  op2.setInWsStudentSmartCardIssued("N");
		  //twin- no input
		  
		  //if ("P".equalsIgnoreCase(mdForm.getStudent().getLastStatus())){
		  op2.setInWsStudentPreviouslyPostGraduateFlag(mdForm.getStudent().getPrevPostgrad());
		  op2.setInWsStudentMkCountryCode(mdForm.getStudent().getCountry().getCode());

		  //study units
		  Integer jj=0;
		  jj=mdForm.getAdditionalStudyUnits().size();
		  for (int ii = 0; ii < jj; ii++) {
			  StudyUnit su = (StudyUnit) mdForm.getAdditionalStudyUnits().get(ii);
			  if ("A".equalsIgnoreCase(su.getLanguage().substring(0,1))){
					su.setLanguage("A");
			  }else {
					su.setLanguage("E");
			  }
			  op2.setInGSuStudentStudyUnitMkCourseStudyUnitCode(ii, su.getCode());
			  op2.setInGSuStudentStudyUnitLanguageCode(ii, su.getLanguage());
			  op2.setInGSuStudentStudyUnitSemesterPeriod(ii,Short.parseShort(su.getRegPeriod()));
			  op2.setInGSuNdpIefSuppliedFlag(ii, su.getNdp());
			  op2.setInGSuCsfLineActionBarAction(ii, "I");
		  }
		  // set email
		  op2.setInFaxOrEmailCsfStringsString1("E");
		  op2.setInEmailToCsfStringsString132(mdForm.getStudent().getEmailAddress());
		  //get this from QUAL inside A-reread all for internet
		  //op2.setInEmailFromCsfStringsString132("study-info@unisa.ac.za");
		  	  
		  
		  //this flag read for repeat of self-help function F539 after confirmation of tempregistration
		  op2.setInDoTempRegistrationIefSuppliedFlag("Y");
		  
		  op2.setInCsfClientServerCommunicationsClientVersionNumber((short) 3);
		  op2.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);     
		  op2.setInCsfClientServerCommunicationsAction("A");
		  op2.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");  
		  op2.setInWsUserNumber(99998);
		  op2.setInWsFunctionNumber(539); //(999)
		  op2.setInWsWorkstationCode("internet");

		  op2.execute();
		
		  if (opl2.getException() != null) throw opl2.getException();
		  if (op2.getExitStateType() < 3) throw new Exception(op2.getExitStateMsg());
		  
		  String errorMessage = op2.getOutCsfStringsString500();
		  
		  //log.debug("MDRegistrationsAction - updateStudentNr - updateStudentNrAddition(A) - getOutCsfStringsString500 = " + errorMessage);

		  errorMessage = op2.getOutCsfStringsString500();
		  // return error only exist on the following return codes
		  //switch (op2.getOutCsfClientServerCommunicationsReturnCode()) {
		  //case 2028: result = student-temp-registration
		  //case 2029: result = student-registration
		  //case all others, i.e. 2001-2020; result = errorMessage;
		  if (op2.getOutCsfClientServerCommunicationsReturnCode()==2028){
			  // use commMethod field for amount if temporary registration
			  //Total fees = 10,985.00. Min fee due = 10,985.00, Total credits = 240
			  mdForm.setQuestion1(errorMessage);
		  }
//		  if (op2.getOutCsfClientServerCommunicationsReturnCode()==48 || op2.getOutCsfClientServerCommunicationsReturnCode()==49 ){
		  if (op2.getOutCsfClientServerCommunicationsReturnCode()==2028 || op2.getOutCsfClientServerCommunicationsReturnCode()==2029 ){
			  // ALL OK
			  result = "";
			  mdForm.getStudent().setNumber(Integer.toString(op2.getOutStudentAnnualRecordMkStudentNr()));// convert int to String
			  mdForm.setNumberBack(Integer.toString(op2.getOutStudentAnnualRecordMkStudentNr()));
			  mdForm.setEmailBack(op2.getOutEmailToCsfStringsString132());
			  if (op2.getOutCsfClientServerCommunicationsReturnCode()==2028) {
				  mdForm.setTempreg("Y");
			  }else{
				  mdForm.setTempreg("N");
			  }
		  }else{
	  		  // Error Occurred
			  mdForm.setQuestion2(errorMessage);
	  		  result = errorMessage;
		  }
		  return result;
		   
		}
	
	private void writeWorkflow(MdRegistrationsForm mdForm, Date applyDateTime) throws Exception{

		String type = "L"; /* L = local F=Foreign */
		String wflType = "M";
		
		/* set local or foreign */
		if (!"1015".equals(mdForm.getStudent().getCountry().getCode())){
			type = "F";
		}

		/* write to file */
		/** 2015 Edmund - Added code to remove spaces from file name as it causes problems when moving to Uniflow.**/
		String fileName = "MDregister_" + mdForm.getStudent().getNumber();
		fileName = fileName.replaceAll(" ","");
		
		//WorkflowFile file = new WorkflowFile("MDregister_"+ type + "_"+ mdForm.getStudent().getNumber(),wflType);
		WorkflowFile file = new WorkflowFile(fileName,wflType);
		file.add(" Subject: Internet - Real-time registration " + mdForm.getStudent().getAcademicYear() + "\r\n");
		String displayDate = (new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(applyDateTime).toString());
		file.add(" Date received: " + displayDate + "\r\n");
		file.add(" The following real-time registration was submitted at workstation internet: \r\n");
		file.add(" Details of the registration: \r\n");
		file.add(" Student numer               = " + mdForm.getStudent().getNumber() + "\r\n");
		file.add(" Proposed qualification      = " + mdForm.getQual().getQualCode()+" "+ mdForm.getQual().getQualDesc()+ "\r\n");
		file.add(" Specialisation code         = " + mdForm.getQual().getSpecCode() + " " + "\r\n");
		file.add(" Surname                     = " + mdForm.getStudent().getSurname() + "\r\n");
		file.add(" Initials                    = " + mdForm.getStudent().getInitials() + "\r\n");
		file.add(" Title                       = " + mdForm.getStudent().getTitle() + "\r\n");
		file.add(" First names                 = " + mdForm.getStudent().getFirstnames() + "\r\n");
		file.add(" Date of birth               = Year: " + mdForm.getStudent().getBirthYear()+ " Month: "+ mdForm.getStudent().getBirthMonth() +" Day: "+ mdForm.getStudent().getBirthDay()+"\r\n");
		file.add(" Gender (M/F)                = " + mdForm.getStudent().getGender() + "\r\n");
		if(mdForm.getStudent().getLanguage() != null && "A".equalsIgnoreCase(mdForm.getStudent().getLanguage())){
		file.add(" Correspondence language     = Afrikaans \r\n");
		} else{
		file.add(" Correspondence language     = English \r\n");
		}
		file.add(" E-mail address              = " + mdForm.getStudent().getEmailAddress() + "\r\n");
		file.add(" Despatch method             = " + mdForm.getStudent().getDespatchMethod() + "\r\n");
		file.add("\r\n");
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
		file.add(" Contact number              = " + mdForm.getStudent().getContactNr() + "\r\n");
		file.add("\r\n");
		file.add(" Study units registered:     = " + "\r\n");
		  Integer jj=0;
		  jj=mdForm.getAdditionalStudyUnits().size();
		  for (int ii = 0; ii < jj; ii++) {
			  StudyUnit su = (StudyUnit) mdForm.getAdditionalStudyUnits().get(ii);
			  file.add("   Study unit code			 = " + su.getCode() + "\r\n");
			  file.add("   Semester					 = " + su.getRegPeriod() + "\r\n");
			  file.add("   Language					 = " + su.getLanguage() + "\r\n");
		  }
		file.add("\r\n");
		file.add(" Can complete                = " + mdForm.getCompleteQual() + "\r\n");
		file.add("\r\n");
		file.add("Error occurred: \r\n");
		file.add(mdForm.getQuestion2()+ "\r\n");
		file.close();

		log.info("MDregistrations: Workflow for studnr="+mdForm.getStudent().getNumber());
	}
	
	
	private void writeAdditionWorkflow(MdRegistrationsForm mdForm, Date applyDateTime) throws Exception{

		String type = "L"; /* L = local F=Foreign */
		String wflType = "M";
		String LFCR = "\r\n";
		
		/* set local or foreign */
		if (!"1015".equals(mdForm.getStudent().getCountry().getCode())){
			type = "F";
		}
		
		Calendar cal=Calendar.getInstance();
		int year=cal.get(Calendar.YEAR);
		String fileDate = (new SimpleDateFormat("yyyyMMddHHmmss").format(applyDateTime).toString());
		String displayDate = (new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(applyDateTime).toString());
		
		/* write to file */
		/** 2015 Edmund - Added code to remove spaces from file name as it causes problems when moving to Uniflow.**/
		String fileName = "MDStudyUnitAddition_" + mdForm.getStudent().getNumber()+ "_" + fileDate;
		fileName = fileName.replaceAll(" ","");
		
		WorkflowFile file = new WorkflowFile(fileName,wflType);
		/**2014 Edmund - Updated the fixed date to reflect current academic year as per Claudette
		file.add(" Subject: Internet - Real-time addition " +  Calendar.getInstance().get(Calendar.YEAR)+ " " + LFCR);**/
		file.add(" Subject: Internet - Real-time addition " +  mdForm.getStudent().getAcademicYear()+ " " + LFCR);
		file.add(" Date received: " + displayDate + LFCR);
		file.add(" The following real-time addition was submitted at workstation internet: " + LFCR);
		file.add(" Details of the registration: " + LFCR);
		file.add(" Student numer               = " + mdForm.getStudent().getNumber() + LFCR);
		file.add(" Qualification      		   = " + mdForm.getQual().getQualCode()+" "+ mdForm.getQual().getQualDesc()+ LFCR);
		file.add(" Speciality         		   = " + mdForm.getQual().getSpecCode() + " " + LFCR);
		file.add(" Surname                     = " + mdForm.getStudent().getSurname() + LFCR);
		file.add(" Initials                    = " + mdForm.getStudent().getInitials() + LFCR);
		file.add(" Title                       = " + mdForm.getStudent().getTitle() + LFCR);
		file.add(" First names                 = " + mdForm.getStudent().getFirstnames() + LFCR);
		file.add(" Despatch method             = " + mdForm.getStudent().getDespatchMethod() + LFCR);
		file.add(LFCR);
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
		file.add(" Contact number              = " + mdForm.getStudent().getContactNr() + "\r\n");
		file.add(LFCR);
		file.add(" Study units registered:     " + LFCR);

		  for (int ii = 0; ii < mdForm.getAdditionalStudyUnits().size(); ii++) {
			  StudyUnit su = (StudyUnit) mdForm.getAdditionalStudyUnits().get(ii);
			  file.add("   Study Unit to register	 = " + su.getCode() + LFCR);
			  file.add("   Semester					 = " + su.getRegPeriod() + LFCR);
			  file.add("   Language					 = " + su.getLanguage() + LFCR);
		  }
		file.add(LFCR);
		file.add(" Can complete                = " + mdForm.getCompleteQual() + LFCR);
		file.add(LFCR);
		file.close();

		log.info("MDStudyUnitAddition: Workflow for student number= "+mdForm.getStudent().getNumber());
	}
	
	

	public void resetForm(MdRegistrationsForm form)
	throws Exception {
		// Clear fields
		form.setStudent(new Student());
		form.setQual(new Qualification());
		form.setSelectedQual("");
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
		form.setSelectedSpes("");	
		form.setAgree(null);
		form.setFromPage(null);
		form.setFileName("");
		form.setFileType("");
		form.setTempreg("");
		form.setStudentNumberSearchAttemp(0);
		form.setPayment("");
		form.setRegPeriodOpen0("");
		form.setRegPeriodOpen1("");
		form.setRegPeriodOpen2("");
		form.setQuestion1("");
		form.setQuestion2("");
		form.setErrorAdvisor("");
		
		form.setQualifRules(new ArrayList());
		form.setAdditionalStudyUnits(new ArrayList());
		form.setSelectedAdditionalStudyUnits(new ArrayList());
		
		/**2014 Edmund - Changed fixed date to change dynamically depending on the month **/
		/**form.getStudent().setAcademicYear(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));**/
		 if (Calendar.getInstance().get(Calendar.MONTH)+1 >= 8) {
			form.getStudent().setAcademicYear(Integer.toString(Calendar.getInstance().get(Calendar.YEAR)+1));
		}else{
			form.getStudent().setAcademicYear(Integer.toString(Calendar.getInstance().get(Calendar.YEAR)));
		}
		 //log.debug("Doing form Reset: DateCheck: " + form.getStudent().getAcademicYear() + ", Month: " + Calendar.getInstance().get(Calendar.MONTH) + ", Year: " + Calendar.getInstance().get(Calendar.YEAR));
	}
	 
	  
}