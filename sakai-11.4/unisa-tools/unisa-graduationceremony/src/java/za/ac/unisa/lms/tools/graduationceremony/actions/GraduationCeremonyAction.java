package za.ac.unisa.lms.tools.graduationceremony.actions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.LookupDispatchAction;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.email.api.EmailService;

import za.ac.unisa.lms.dao.Gencod;
import za.ac.unisa.lms.dao.StudentSystemGeneralDAO;
import za.ac.unisa.lms.tools.graduationceremony.dao.GraduationCeremonyDao;
import za.ac.unisa.lms.tools.graduationceremony.forms.Address;
import za.ac.unisa.lms.tools.graduationceremony.forms.Gradcerem;
import za.ac.unisa.lms.tools.graduationceremony.forms.GraduationCeremonyForm;
import za.ac.unisa.lms.tools.graduationceremony.forms.Student;
import Grgdg01h.Abean.Grgdg01sMntStudentGraduation;

public class GraduationCeremonyAction extends LookupDispatchAction {
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
		map.put("button.continue","firstPageContinue");
		map.put("button.input", "input");
		map.put("button.update", "update");
		map.put("button.sendsupplier", "senddressorder");
		map.put("button.back", "back");
		map.put("button.frwcontinue", "frwcontinue");
		map.put("button.dressorder", "dressorder");
		map.put("button.email", "emailorder");
		map.put("studinput", "studinput");
		map.put("action", "action");
	return map;
	}


	public ActionForward studinput(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
	  {
		    resetActionForm(form);
	        return mapping.findForward("display");
	}
	
	private void resetActionForm(ActionForm form){
		          try{
			           GraduationCeremonyForm  graduationCeremonyForm = 
			        		                     (GraduationCeremonyForm) form;
			           resetForm(graduationCeremonyForm); 
		          }catch(Exception ex){}
	}
	public ActionForward action(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
		 {
		
		       resetActionForm(form);
		       return mapping.findForward("display");
	}
	public ActionForward frwcontinue(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {
		 GraduationCeremonyForm gradForm = (GraduationCeremonyForm) form;
         ActionMessages messages = new ActionMessages();

			try{
		         
		           if (gradForm.getStudent().getStudentnumber() == null || "".equalsIgnoreCase(gradForm.getStudent().getStudentnumber())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("errors.message", "Enter student number."));
			addErrors(request, messages);
			return mapping.findForward("display");
		}

/* 		preset confirm date to today				
 */
		Date date = new java.util.Date();
		String thisdate = (new java.text.SimpleDateFormat("yyyy-MM-dd").format(date).toString());
		gradForm.setConfirmdate(thisdate);
		return mapping.findForward("graduation-all");
	
	}catch(Exception ex){
		messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("errors.message", "An unexpected error happened,please enter your details and try again"));
				addErrors(request, messages);
				resetActionForm(form);
				return mapping.findForward("display");
	}
 }

	public ActionForward firstPageContinue(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)  {
		
		GraduationCeremonyForm graduationCeremonyForm = (GraduationCeremonyForm) form;
		ActionMessages messages = new ActionMessages();
			try{
		
			
		if (graduationCeremonyForm.getStudent().getStudentnumber() == null || "".equalsIgnoreCase(graduationCeremonyForm.getStudent().getStudentnumber())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("errors.message", "Enter student number."));
			addErrors(request, messages);
			return mapping.findForward("display");
		}
		if (graduationCeremonyForm.getStudent().getSurname() == null || "".equalsIgnoreCase(graduationCeremonyForm.getStudent().getSurname())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,	
					new ActionMessage("errors.message", "Enter student surname."));
			addErrors(request, messages);
			return mapping.findForward("display");
		}
		if (graduationCeremonyForm.getStudent().getFirstnames() == null || "".equalsIgnoreCase(graduationCeremonyForm.getStudent().getFirstnames())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,	
					new ActionMessage("errors.message", "Enter student first names."));
			addErrors(request, messages);
			return mapping.findForward("display");
		}
		if (graduationCeremonyForm.getStudent().getBirthDay() == null || "".equalsIgnoreCase(graduationCeremonyForm.getStudent().getBirthDay())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,	
					new ActionMessage("errors.message", "Student birthday is incomplete."));
			addErrors(request, messages);
			return mapping.findForward("display");
		}
		if (graduationCeremonyForm.getStudent().getBirthMonth() == null || "".equalsIgnoreCase(graduationCeremonyForm.getStudent().getBirthMonth())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,	
					new ActionMessage("errors.message", "Student birthday is incomplete."));
			addErrors(request, messages);
			return mapping.findForward("display");
		}
		if (graduationCeremonyForm.getStudent().getBirthYear() == null || "".equalsIgnoreCase(graduationCeremonyForm.getStudent().getBirthYear())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,	
					new ActionMessage("errors.message", "Student birthday is incomplete."));
			addErrors(request, messages);
			return mapping.findForward("display");
		}
		String dd = graduationCeremonyForm.getStudent().getBirthDay();
		String mm = graduationCeremonyForm.getStudent().getBirthMonth();
		String yy = graduationCeremonyForm.getStudent().getBirthYear();
		graduationCeremonyForm.getStudent().setBirthdate(yy+mm+dd);

/*			read Student into database into new variable
 */
		GraduationCeremonyDao dao = new GraduationCeremonyDao();
		Student gradStudent = new Student();
		gradStudent = dao.getStudentinfo(graduationCeremonyForm.getStudent().getStudentnumber());

		Address gradAddress = new Address();

		graduationCeremonyForm.setAddress(dao.getStudentaddress(graduationCeremonyForm.getStudent().getStudentnumber()));
			
		if ("".equalsIgnoreCase(gradStudent.getStudentnumber()) || gradStudent.getStudentnumber() == null){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("errors.message", "Your student number is invalid."));
			addErrors(request, messages);
			return mapping.findForward("display");
		}
		if (graduationCeremonyForm.getStudent().getSurname().trim().equalsIgnoreCase(gradStudent.getSurname().trim()) ){
			graduationCeremonyForm.getStudent().setSurname(gradStudent.getSurname());
		}else{
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("errors.message", "Surname on database differs from surname entered"));
			addErrors(request, messages);
			return mapping.findForward("display");
		}
		if (graduationCeremonyForm.getStudent().getFirstnames().trim().equalsIgnoreCase(gradStudent.getFirstnames().trim())){
			graduationCeremonyForm.getStudent().setFirstnames(gradStudent.getFirstnames());
		}else{
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("errors.message", "First names on database differ from first names entered"));
			addErrors(request, messages);
			return mapping.findForward("display");
		}
		if (graduationCeremonyForm.getStudent().getBirthdate().trim().equals(gradStudent.getBirthdate().trim())){
		}else{
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("errors.message", "Student birthdate differs from birthdate entered"));
			addErrors(request, messages);
			return mapping.findForward("display");
		}
		dao.getGradceremonydates(graduationCeremonyForm);
		graduationCeremonyForm.setCeremony(dao.getGraduationceremony(gradStudent.getStudentnumber(), graduationCeremonyForm.getBegindate(), graduationCeremonyForm.getEnddate()));
			
		/* Change 20160517 - Requested by Koos Janse van Vuuren - specification not clear on this*/
		if (null!=graduationCeremonyForm.getCeremony().getQualtype() && graduationCeremonyForm.getCeremony().getQualtype().equalsIgnoreCase("S")) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("errors.message", "Graduation information only available for Formal Qualifications (not for Short Learning Programmes)"));
			addErrors(request, messages);
			return mapping.findForward("display");
		}
		
		if ("".equals(graduationCeremonyForm.getCeremony().getQualification()) || graduationCeremonyForm.getCeremony().getQualification()== null){
			return mapping.findForward("graduation-no");
		}
		if ("".equalsIgnoreCase(graduationCeremonyForm.getCeremony().getVenue()) || graduationCeremonyForm.getCeremony().getVenue() == null){
			return mapping.findForward("graduation-no");
		}
		//if ("Y".equalsIgnoreCase(graduationCeremonyForm.getCeremony().getPresentflag())){
		//	graduationCeremonyForm.getCeremony().setPresentflag("P");
		//}else{
		//	graduationCeremonyForm.getCeremony().setPresentflag("A");
		//}

/*			test if graduation date in past 
 *			use displaytype = Update = not show dress order button so that update must be done first
 *				displaytype = Order = after update show dress order button
 *				displaytype = Display = if student can not continue transaction 			 		 
*/
		Date date = new java.util.Date();
		Date graddate = new java.util.Date();
		String stringdate = graduationCeremonyForm.getCeremony().getCeremonydate();
        DateFormat fm ; 
        fm = new SimpleDateFormat("yyyy-MM-dd");
        graddate = (Date)fm.parse(stringdate);    
        graduationCeremonyForm.setPagedisplay("U");
        if (graddate.before(date)){ 
        	graduationCeremonyForm.setPagedisplay("D");
        	messages.add(ActionMessages.GLOBAL_MESSAGE,
			new ActionMessage("errors.message", "Graduation date is in the past, can not change informaton"));
			addErrors(request, messages);
        }

/*			test if already done
*/
        String test = dao.getWebcer(graduationCeremonyForm.getStudent().getStudentnumber());
		if ("N".equals(test)){
        	graduationCeremonyForm.setPagedisplay("D");
        	messages.add(ActionMessages.GLOBAL_MESSAGE,
			new ActionMessage("errors.message", "You have already confirmed your graduation ceremony information"));
			addErrors(request, messages);		
			return mapping.findForward("graduation-display");
		}   

/*			move the database view of student to the form view 
 */
		graduationCeremonyForm.setStudent(gradStudent);
			
/* 			set accept date field default to today
 */
		String qualtype = new String();
		qualtype = graduationCeremonyForm.getCeremony().getQualtype();
		int qualtime = Short.parseShort(graduationCeremonyForm.getCeremony().getQualtime());
			
/* 			short diploma and certificate programmes do not attend ceremony
 * 			display different wording on screen for them 			
 */
		/* 20160315 (Johanet) - check if this qualification is specified in the GENCOD 219 exception list */
		Gencod exceptionalQual = new Gencod();
		StudentSystemGeneralDAO gendao = new StudentSystemGeneralDAO();
		exceptionalQual = gendao.getGenCode("219", graduationCeremonyForm.getCeremony().getQualification());
		
		if (( "D".equals(qualtype) || "C".equals(qualtype) ) && qualtime <3){
			/*qualification higher Diploma - 2 years and less*/
			if (qualtype.equalsIgnoreCase("D")){
				if (null!=graduationCeremonyForm.getCeremony().getQualcategory() && 
						null!=graduationCeremonyForm.getCeremony().getQualUnderGradCategory() &&
							graduationCeremonyForm.getCeremony().getQualUnderGradCategory().equalsIgnoreCase("H") &&
							(graduationCeremonyForm.getCeremony().getQualcategory().equalsIgnoreCase("4") ||
							graduationCeremonyForm.getCeremony().getQualcategory().equalsIgnoreCase("47"))){
					/*  20160315 (Johanet) - change code to allow 1 and 2 year Diploma Qualifications of Category 4 (Postgraduate diplomas) and 47 (postgraduate diplomas) 
					 * to be present at ceremonies */
						graduationCeremonyForm.setPagetype("P");
					}
				else if (null!=exceptionalQual && null!=exceptionalQual.getEngDescription()){
				/*exceptional qual allowed to attend */
					graduationCeremonyForm.setPagetype("P");
				}else{
					graduationCeremonyForm.setPagetype("A");
				}
			}else{
			/*qualification higher Certificate - 2 years and less*/	
		    /*May not attend graduation ceremony */
				graduationCeremonyForm.setPagetype("A");
			}				
		}else{			
			if ("S".equals(qualtype)){
				/* SLP's not allowed to attend ceremony*/
				graduationCeremonyForm.setPagetype("A");
			}else{
				graduationCeremonyForm.setPagetype("P");
			}
			
		}
		if (graduationCeremonyForm.getPagetype()!=null && graduationCeremonyForm.getPagetype().equalsIgnoreCase("A")){
			graduationCeremonyForm.getCeremony().setPresentflag("N");
		}
			}catch(Exception ex){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("errors.message", "An unexpected error happened,plese enter your details and try again"));
						addErrors(request, messages);
						resetActionForm(form);
						return mapping.findForward("display");
			}
		
 		return mapping.findForward("graduation-display");
	}

	public ActionForward update(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {
		GraduationCeremonyForm graduationCeremonyForm = (GraduationCeremonyForm) form;
		ActionMessages messages = new ActionMessages();
        
		try{

		/* Change Johanet 20130422 - If Praesentia number of quests compulsary, restricted to 3*/
		if (graduationCeremonyForm.getCeremony().getPresentflag()==null){
			messages.add(ActionMessages.GLOBAL_MESSAGE,	
					new ActionMessage("errors.message", "Please indicate whether you will be present or absent at the ceremony."));
			addErrors(request, messages);
			return mapping.findForward("graduation-all");
		}
		if (graduationCeremonyForm.getCeremony().getPresentflag().equalsIgnoreCase("Y")){
			if (graduationCeremonyForm.getCeremony().getGuests()==null || "".equals(graduationCeremonyForm.getCeremony().getGuests())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,	
						new ActionMessage("errors.message", "Please specify the number of guests"));
				addErrors(request, messages);
				return mapping.findForward("graduation-all");
			}else{
				if(!isInteger(graduationCeremonyForm.getCeremony().getGuests())){
					messages.add(ActionMessages.GLOBAL_MESSAGE,	
							new ActionMessage("errors.message", "Number of guests must be numeric"));
					addErrors(request, messages);
					return mapping.findForward("graduation-all");
				}
			}
			if (Short.parseShort(graduationCeremonyForm.getCeremony().getGuests()) > 3){
				messages.add(ActionMessages.GLOBAL_MESSAGE,	
						new ActionMessage("errors.message", "Maximum of 3 guests allowed"));
				addErrors(request, messages);
				return mapping.findForward("graduation-all");
			}
		}else{			
			if (graduationCeremonyForm.getCeremony().getGuests()!=null && isInteger(graduationCeremonyForm.getCeremony().getGuests()) && Short.parseShort(graduationCeremonyForm.getCeremony().getGuests()) > 0){
				messages.add(ActionMessages.GLOBAL_MESSAGE,	
						new ActionMessage("errors.message", "If you have selected Absentia, the number of guests must be 0"));
				addErrors(request, messages);
				return mapping.findForward("graduation-all");
			}else{
				graduationCeremonyForm.getCeremony().setGuests("0");
			}			
		}
		
		if (graduationCeremonyForm.getConfirmname() == null || "".equalsIgnoreCase(graduationCeremonyForm.getConfirmname())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("errors.message", "Enter your name for acceptance of conditions."));
			addErrors(request, messages);
			return mapping.findForward("graduation-all");
		}

		Date date = new java.util.Date();
		String thisdate = (new java.text.SimpleDateFormat("yyyy-MM-dd").format(date).toString());
//		String thistime = (new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(date).toString());

		if("".equals(graduationCeremonyForm.getConfirmdate())){
		messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("errors.message", "Confirm date must be entered "));
			addErrors(request, messages);
			return mapping.findForward("graduation-all");
		}
		if (!thisdate.equals(graduationCeremonyForm.getConfirmdate())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("errors.message", "Confirm date must be todays date " + thisdate));
			addErrors(request, messages);
			return mapping.findForward("graduation-all");
		}
		
		graduationCeremonyForm.getCeremony().setAcceptdate(graduationCeremonyForm.getConfirmdate());
		graduationCeremonyForm.getCeremony().setAcceptname(graduationCeremonyForm.getConfirmname());
		String oldcomm=new String();
		String oldname=new String();
		oldcomm = graduationCeremonyForm.getCeremony().getPunctuation();
		oldname = graduationCeremonyForm.getCeremony().getAcceptname();
		
/*		haal uit Jan 2011
 * 		String comm = new String();
		comm = graduationCeremonyForm.getCeremony().getPunctuation().replace("'", "''");
		graduationCeremonyForm.getCeremony().setPunctuation(comm);
		comm="";
		comm = graduationCeremonyForm.getCeremony().getAcceptname().replace("'", "''");
		graduationCeremonyForm.getCeremony().setAcceptname(comm);
 */ 
		
/*	
 * 	update the table WEBCER with captured information
 */
		GraduationCeremonyDao dao = new GraduationCeremonyDao();
		graduationCeremonyForm.getCeremony().setCertificatelang("E");
		dao.updateWebCeremony(graduationCeremonyForm.getStudent().getStudentnumber(), graduationCeremonyForm.getCeremony());
		
/*	
 * 	update F326 using proxy
 */
		String errorMsg="";
		errorMsg = updateGraduation(graduationCeremonyForm);
		if(graduationDataUpdated(graduationCeremonyForm)){
			      errorMsg="";
		}
		if(!"".equals(errorMsg)){
			  
			messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("errors.message", errorMsg));
				addErrors(request, messages);
				return mapping.findForward("graduation-all");
		}
		
/*
 * order dress form not to be used now
 */
		//graduationCeremonyForm.setDisplayname(graduationCeremonyForm.getStudent().getTitle()+" "+graduationCeremonyForm.getStudent().getInitials()+" " +graduationCeremonyForm.getStudent().getSurname());
		//if ("".equals(graduationCeremonyForm.getAddrchg().getAddressline1()) || graduationCeremonyForm.getAddrchg().getAddressline1()==null){
		//	graduationCeremonyForm.setAddrchg(graduationCeremonyForm.getAddress());
		//}
		//graduationCeremonyForm.setPagedisplay("O");
		graduationCeremonyForm.getCeremony().setPunctuation(oldcomm);
		graduationCeremonyForm.getCeremony().setAcceptname(oldname);
		sendemail(graduationCeremonyForm);
		resetForm(graduationCeremonyForm);
		return mapping.findForward("display");
	}catch(Exception ex){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("errors.message", "An unexpected error happened,please enter your details and try again"));
					addErrors(request, messages);
					resetActionForm(form);
					return mapping.findForward("display");
		}
	}

	public String updateGraduation(GraduationCeremonyForm gradForm) throws Exception {
/*
 * 	F362 for update 
 */
		String result = "";
		Grgdg01sMntStudentGraduation op = new Grgdg01sMntStudentGraduation();
		operListener opl = new operListener();
		op.addExceptionListener(opl);
		op.clear();

		//Date graddate = new java.util.Date();
		String stringdate = gradForm.getCeremony().getAcceptdate();
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		Date graddate = formatter.parse(stringdate);
		calendar.setTime(graddate);
		
		op.setInCsfClientServerCommunicationsClientVersionNumber((short) 3);
		op.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);     
		op.setInCsfClientServerCommunicationsAction("U");
		op.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");  
		op.setInSecurityWsUserNumber(99998);
		//op.setInMustPrintIefSuppliedFlag("");
		op.setInOverrideIefSuppliedFlag("N");
		op.setInWsQualificationEngDescription(gradForm.getCeremony().getQualdescription());
		op.setInWsStudentSurname(gradForm.getStudent().getSurname());
		op.setInWsStudentInitials(gradForm.getStudent().getInitials());
		op.setInStudentGraduationDetailMkStudentNr(Integer.parseInt(gradForm.getStudent().getStudentnumber()));
		op.setInStudentGraduationDetailMkGraduationCeremonyCode(Short.parseShort(gradForm.getCeremony().getCeremonynr()));
		op.setInStudentGraduationDetailDateAltered(calendar);
		op.setInStudentGraduationDetailMkQualificationCode(gradForm.getCeremony().getQualification().trim());
		op.setInStudentGraduationDetailNoOfGuests(Short.parseShort(gradForm.getCeremony().getGuests()));
		op.setInStudentGraduationDetailPresentFlag(gradForm.getCeremony().getPresentflag());
		gradForm.getCeremony().setCertificatelang("E");
		op.setInStudentGraduationDetailLanguage(gradForm.getCeremony().getCertificatelang());
		  
		op.execute();
		  
		if (opl.getException() != null) throw opl.getException();
		if (op.getExitStateType() < 3) throw new Exception(op.getExitStateMsg());
		  
		String errorMessage = op.getOutCsfStringsString500();
		if(op.getOutCsfClientServerCommunicationsReturnCode()== 99){
			// error returned
			return errorMessage;
		}
		// can not display as warning messages also appear here
		if (!"".equals(errorMessage)){
		    return errorMessage;
		}
	 
		// return error only exist on the following return codes
		//switch (op.getOutCsfClientServerCommunicationsReturnCode()) {
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
		  
		if (op.getOutCsfClientServerCommunicationsReturnCode()==49 ){
			// ALL OK
			result = "";
		}else{
			// Error Occurred
			result = errorMessage;
		}
		return result;
	}	
	
	private boolean graduationDataUpdated(GraduationCeremonyForm graduationCeremonyForm)throws Exception {
		               GraduationCeremonyDao dao = new GraduationCeremonyDao();
		               String test = dao.getWebcer(graduationCeremonyForm.getStudent().getStudentnumber());
			           if ("N".equals(test)){
				              return true;
			           }else{
				              return false;
			          }
	}
	public void sendemail(GraduationCeremonyForm form) throws Exception {
		emailService = (EmailService) ComponentManager.get(EmailService.class);
		
		GraduationCeremonyForm gradForm = (GraduationCeremonyForm) form;
		
		String lang="";
		String present="";
		if ("Y".equalsIgnoreCase(gradForm.getCeremony().getPresentflag())){
			present="Praesentia";
		}else{
			present="Absentia";
		}
		if ("A".equalsIgnoreCase(gradForm.getCeremony().getCertificatelang())){
			lang=("Afrikaans");
		}else{
			lang=("English");
		}

		String upd="N";
		if ("".equals(gradForm.getAddrchg().getAddressline1()) || gradForm.getAddrchg().getAddressline1()== null){
			if ("".equals(gradForm.getAddrchg().getWorknumber()) || gradForm.getAddrchg().getWorknumber()== null){
				if ("".equals(gradForm.getAddrchg().getHomenumber()) || gradForm.getAddrchg().getHomenumber()== null){
					if ("".equals(gradForm.getCeremony().getPunctuation()) || gradForm.getCeremony().getPunctuation() == null){
						upd="N";
					}else{
						upd="Y";
					}
				}else{
					upd="Y";
				}
			}else{
				upd="Y";
			}
		}else{
			upd="Y";
		}
		
		String emailto = new String();
		String emailfrom = new String();
		String emailsubject = new String();
		String mybody = new String();
		String vbCrLf = new String();
		vbCrLf = "\r\n";
		String newline = new String();
		newline = System.getProperty("line.separator");
		vbCrLf = newline;
/*		System.out.println((char)13);
 */
		mybody = "<html><body>";
		vbCrLf = "<br/>";

		if ("Y".equals(upd)){
			emailsubject = "Required Particulars for the Graduation Ceremony Student "  + gradForm.getStudent().getStudentnumber(); 
		}else{
			emailsubject = gradForm.getStudent().getStudentnumber() + " FILE"; 
		}
		
//do not send email on dev & qa -default email to krugegj@unisa.ac.za
		String serverpath = ServerConfigurationService.getServerUrl();
		String toaddr = "";
		if (serverpath.contains("mydev") || serverpath.contains("localhost")|| serverpath.contains("myqa")){
			  toaddr="baloyar@unisa.ac.za";
		}else {
			  toaddr="gaudeamus@unisa.ac.za";
		}
		if (!"".equalsIgnoreCase(gradForm.getAddress().getEmailaddress()) && !" ".equalsIgnoreCase(gradForm.getAddress().getEmailaddress())){
			emailfrom = gradForm.getAddress().getEmailaddress();
			emailto = toaddr;
			mybody = mybody + "Student Number : " + gradForm.getStudent().getStudentnumber() + vbCrLf;
		}else{
			emailfrom = "noemailaddress";
			emailto= toaddr;
			mybody = mybody + "NB This student doesn't have email address " + vbCrLf;
			mybody = mybody + "Student Number : " + gradForm.getStudent().getStudentnumber()+ vbCrLf;
		}
		
		mybody = mybody + "Student Name : " + gradForm.getStudent().getFirstnames() + vbCrLf;
		mybody = mybody + "Student Surname : " + gradForm.getStudent().getSurname() + vbCrLf;
		mybody = mybody + "Degree : " + gradForm.getCeremony().getQualification() + vbCrLf;
		mybody=  mybody + "Graduation No : " + gradForm.getCeremony().getCeremonynr() + vbCrLf;
		mybody = mybody + "Graduation Date : " + gradForm.getCeremony().getCeremonydate() + vbCrLf;
		mybody = mybody + "Graduation Time : " + gradForm.getCeremony().getCeremonytime() + vbCrLf;
		mybody = mybody + "Venue : " + gradForm.getCeremony().getVenue() + ", " + gradForm.getCeremony().getVenueline1();
		mybody = mybody + ", " +gradForm.getCeremony().getVenueline2() +vbCrLf; 
		mybody = mybody + "Required Information." + vbCrLf;
		mybody = mybody + "Certificate required in : " + lang + vbCrLf;
		mybody = mybody + "Certificate presented in : " + present + vbCrLf;
		mybody = mybody + "No of guests : " + gradForm.getCeremony().getGuests() + vbCrLf;
		mybody = mybody + "Special characters in the certificate : " + gradForm.getCeremony().getPunctuation() + vbCrLf;
		mybody = mybody + "Student address :" + vbCrLf;
		mybody = mybody + " Address1 : " + gradForm.getAddress().getAddressline1() + vbCrLf;
		mybody = mybody + " Address2 : " + gradForm.getAddress().getAddressline2() + vbCrLf;
		mybody = mybody + " Address3 : " + gradForm.getAddress().getAddressline3() + vbCrLf;
		mybody = mybody + " Address4 : " + gradForm.getAddress().getAddressline4() + vbCrLf;
		mybody = mybody + " Address5 : " + gradForm.getAddress().getAddressline5() + vbCrLf;
		mybody = mybody + " Address6 : " + gradForm.getAddress().getAddressline6() + vbCrLf;
		mybody = mybody + "Postal Code : " + gradForm.getAddress().getPostcode() + vbCrLf;
		mybody = mybody + "Phone W : " + gradForm.getAddress().getWorknumber() + vbCrLf;
		mybody = mybody + "Phone H : " + gradForm.getAddress().getHomenumber() + vbCrLf;
		mybody = mybody + "Change to new address/phone:" + vbCrLf;
		if (!"".equalsIgnoreCase(gradForm.getAddrchg().getAddressline1()) && gradForm.getAddrchg().getAddressline1() != null){
		mybody = mybody + " Address1 : " + gradForm.getAddrchg().getAddressline1() + vbCrLf;
		mybody = mybody + " Address2 : " + gradForm.getAddrchg().getAddressline2() + vbCrLf;
		mybody = mybody + " Address3 : " + gradForm.getAddrchg().getAddressline3() + vbCrLf;
		mybody = mybody + " Address4 : " + gradForm.getAddrchg().getAddressline4() + vbCrLf;
		mybody = mybody + " Address5 : " + gradForm.getAddrchg().getAddressline5() + vbCrLf;
		mybody = mybody + " Address6 : " + gradForm.getAddrchg().getAddressline6() + vbCrLf;
		mybody = mybody + "Postal Code : " + gradForm.getAddrchg().getPostcode() + vbCrLf;
		mybody = mybody + "Phone W : " + gradForm.getAddrchg().getWorknumber() + vbCrLf;
		mybody = mybody + "Phone H : " + gradForm.getAddrchg().getHomenumber() + vbCrLf;
		}else{
		if (!"".equalsIgnoreCase(gradForm.getAddrchg().getWorknumber()) && gradForm.getAddrchg().getWorknumber() != null){
			mybody = mybody + "Phone W : " + gradForm.getAddrchg().getWorknumber() + vbCrLf;
		}
		if (!"".equalsIgnoreCase(gradForm.getAddrchg().getHomenumber()) && gradForm.getAddrchg().getHomenumber() != null){
			mybody = mybody + "Phone H : " + gradForm.getAddrchg().getHomenumber() + vbCrLf;
		}
		}
		mybody = mybody + "Acceptance Name : " + gradForm.getCeremony().getAcceptname() + vbCrLf;
		mybody = mybody + "</body></html>";
		
		List contentlist = new ArrayList();
		contentlist.add("Content-Type: text/html");
		try{
			InternetAddress toEmail = new InternetAddress(emailto);
			InternetAddress iaTo[] = new InternetAddress[1];
			iaTo[0] = toEmail;
			InternetAddress iaHeaderTo[] = new InternetAddress[1];
			iaHeaderTo[0] = toEmail;
			InternetAddress iaReplyTo[] = new InternetAddress[1];
			iaReplyTo[0] = new InternetAddress(emailfrom);
/*			EmailService.sendMail(iaReplyTo[0],iaTo,emailsubject,mybody,iaHeaderTo,iaReplyTo,null); */
			emailService.sendMail(iaReplyTo[0],iaTo,emailsubject,mybody,iaHeaderTo,iaReplyTo,contentlist);
		
		} catch (Exception ex){
			throw ex;
		}
	}
	
	public ActionForward dressorder(ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)  {
		try{
		     return mapping.findForward("graduation-dress");
		}catch(Exception ex){
			
			return mapping.findForward("display");
         }
		
	}

	public ActionForward back(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response){

		 try{
	          return mapping.findForward("display");
        }catch(Exception ex){
	
        	return mapping.findForward("display");
      }
	}

	public ActionForward reset(ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {
		    try{
  		          //Type cast the incoming form to GraduationForm and reset 
		          GraduationCeremonyForm gradForm = (GraduationCeremonyForm) form;  
		          resetForm(gradForm);		
		     }catch(Exception ex){
		
		    	 return mapping.findForward("display");
	       }
		    return mapping.findForward("display");
	}

	public void resetForm(GraduationCeremonyForm form)
	 {
		// Clear fields
		form.setStudent(new Student());
		form.setAddress(new Address());
		form.setAddrchg(new Address());
		form.setCeremony(new Gradcerem());
		form.setConfirmname("");
		form.setConfirmdate("");
		form.setDisplayname("");
		form.getCeremony().setCertificatelang("E");
		
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
	public ActionForward unspecified(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response){
		          
		return mapping.findForward("display");

	}
}