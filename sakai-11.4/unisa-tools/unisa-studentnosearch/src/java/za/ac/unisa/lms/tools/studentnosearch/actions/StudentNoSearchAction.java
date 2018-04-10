//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.studentnosearch.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

import za.ac.unisa.lms.tools.studentnosearch.dao.StudentNoSearchQueryDAO;
import za.ac.unisa.lms.tools.studentnosearch.forms.Student;
import za.ac.unisa.lms.tools.studentnosearch.forms.StudentNoSearchForm;

/** 
 * MyEclipse Struts
 * Creation date: 01-13-2006
 * 
 * XDoclet definition:
 * @struts:action path="/displaystudentnumber" name="studentNumberForm" parameter="action" scope="request"
 */
public class StudentNoSearchAction extends DispatchAction {

	// --------------------------------------------------------- Instance Variables

	// --------------------------------------------------------- Methods

	/** 
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward submit(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {
		
		StudentNoSearchForm studentNumberForm = (StudentNoSearchForm) form;
		ActionMessages messages = new ActionMessages();
		
			if (studentNumberForm.getStudent().getSurname() == null || "".equalsIgnoreCase(studentNumberForm.getStudent().getSurname())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
    			new ActionMessage("errors.message", "Please enter your surname."));
				addErrors(request, messages);
				return mapping.findForward("input");
			}	
			if (studentNumberForm.getStudent().getFirstnames() == null || "".equalsIgnoreCase(studentNumberForm.getStudent().getFirstnames())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
		    			new ActionMessage("errors.message", "Please enter your first name(s)."));
						addErrors(request, messages);
						return mapping.findForward("input");
			}
			if (studentNumberForm.getStudent().getBirthYear()== null || studentNumberForm.getStudent().getBirthYear().trim().length() != 4 ){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
    			new ActionMessage("errors.message", "Please enter your year of birth. Make sure it contains four digits."));
				addErrors(request, messages);
				return mapping.findForward("input");
			}
			if (studentNumberForm.getStudent().getBirthMonth()== null || studentNumberForm.getStudent().getBirthMonth().trim().length() ==0 ){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
    			new ActionMessage("errors.message", "Please enter your birth month."));
				addErrors(request, messages);
				return mapping.findForward("input");
			}
			if (studentNumberForm.getStudent().getBirthDay()== null || studentNumberForm.getStudent().getBirthDay().trim().length() ==0 ){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
    			new ActionMessage("errors.message", "Please enter your day of birth."));
				addErrors(request, messages);
				return mapping.findForward("input");
			}
			/* numbers must be numeric */
			try {
				Long.parseLong(studentNumberForm.getStudent().getBirthYear());
			} catch (NumberFormatException e) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("errors.message",
									"Birth year must be numeric."));
					addErrors(request, messages);
					return mapping.findForward("input");
			}try {
				Long.parseLong(studentNumberForm.getStudent().getBirthMonth());
			} catch (NumberFormatException e) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("errors.message",
									"Birth month must be numeric."));
					addErrors(request, messages);
					return mapping.findForward("input");
			}try {
				Long.parseLong(studentNumberForm.getStudent().getBirthDay());
			} catch (NumberFormatException e) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("errors.message",
									"Birth day must be numeric."));
					addErrors(request, messages);
					return mapping.findForward("input");
			}
			if ("id".equalsIgnoreCase(studentNumberForm.getSearchType())
					&& "".equalsIgnoreCase(studentNumberForm.getStudent().getIdNumber())) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage(
										"message.generalmessage",
										"You have selected to search by ID number. Please enter your ID number."));
				addErrors(request, messages);
				return mapping.findForward("input");
			}
			if ("passport".equalsIgnoreCase(studentNumberForm.getSearchType())
					&& "".equalsIgnoreCase(studentNumberForm.getStudent().getPassport())) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage(
										"message.generalmessage",
										"You have selected to search by passport. Please enter your passport number."));
				addErrors(request, messages);
				return mapping.findForward("input");
			}
			
			// setup birthdate
			if(studentNumberForm.getStudent().getBirthDay().length()==1){
				studentNumberForm.getStudent().setBirthDay("0"+studentNumberForm.getStudent().getBirthDay());
			}
			if(studentNumberForm.getStudent().getBirthMonth().length()==1){
				studentNumberForm.getStudent().setBirthMonth("0"+studentNumberForm.getStudent().getBirthMonth());
			}

			String monthInWords = getMonthInWords(studentNumberForm.getStudent().getBirthMonth());
			if ("".equalsIgnoreCase(monthInWords) || monthInWords==null ){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("errors.message",
								"Birth month is invalid."));
				addErrors(request, messages);
				return mapping.findForward("input");
			}
			
			String birthDate ="";
			birthDate = studentNumberForm.getStudent().getBirthDay()+"-"+monthInWords+"-"+studentNumberForm.getStudent().getBirthYear();
			// search for a student number
			StudentNoSearchQueryDAO dao = new StudentNoSearchQueryDAO();
			String unisaStudentNr = "";
			if (studentNumberForm.getStudent().getIdNumber()==null || "".equalsIgnoreCase(studentNumberForm.getStudent().getIdNumber()) ){
				if (studentNumberForm.getStudent().getPassport() ==null || "".equalsIgnoreCase(studentNumberForm.getStudent().getPassport()) ){
					unisaStudentNr = dao.getUnisaStudentNumber(studentNumberForm.getStudent().getSurname(),studentNumberForm.getStudent().getFirstnames(),birthDate,"","");
				}else{
					unisaStudentNr = dao.getUnisaStudentNumber(studentNumberForm.getStudent().getSurname(),studentNumberForm.getStudent().getFirstnames(),birthDate,"",studentNumberForm.getStudent().getPassport());
				}
			}else{
				unisaStudentNr = dao.getUnisaStudentNumber(studentNumberForm.getStudent().getSurname(),studentNumberForm.getStudent().getFirstnames(),birthDate,studentNumberForm.getStudent().getIdNumber(),"");
			}
   			//unisaStudentNr = dao.getUnisaStudentNumber(studentNumberForm.getStudent().getSurname(),studentNumberForm.getStudent().getFirstnames(),birthDate,"");
			if ("more".equalsIgnoreCase(unisaStudentNr)){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("errors.message",
								"There is more than one student number that complies with the information submitted. Please contact study-info@unisa.ac.za for assistance."));
				addErrors(request, messages);
			}else if ("".equalsIgnoreCase(unisaStudentNr)){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("errors.message",
								"Student number not found. Check that your details have been entered correctly. If correct, please contact study-info@unisa.ac.za for assistance."));
				addErrors(request, messages);
			}else{
				studentNumberForm.setMessageToStudent("Your Unisa student number is "+unisaStudentNr);
				studentNumberForm.setInput(false);
			}
				
		return mapping.findForward("input");
	}
	
	public ActionForward input(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {
			
		StudentNoSearchForm studentNumberForm = (StudentNoSearchForm) form;
		studentNumberForm.setStudent(new Student());
		return mapping.findForward("input");
		
		}
	
	public String getMonthInWords(String month){
		
		String yearInWords = "";
		
		switch (Integer.parseInt(month)-1) {
		case 0:
			yearInWords = "JAN";
			break;
		case 1:
			yearInWords = "FEB";
			break;
		case 2:
			yearInWords = "MAR";
			break;
		case 3:
			yearInWords = "APR";
			break;
		case 4:
			yearInWords = "MAY";
			break;
		case 5:
			yearInWords = "JUN";
			break;
		case 6:
			yearInWords = "JUL";
			break;
		case 7:
			yearInWords = "AUG";
			break;
		case 8:
			yearInWords = "SEP";
			break;
		case 9:
			yearInWords = "OCT";
			break;
		case 10:
			yearInWords = "NOV";
			break;
		case 11:
			yearInWords = "DEC";
			break;
		default:
			break;
		}
		
		return yearInWords;
	}
	
	public ActionForward back(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {
			
			StudentNoSearchForm studentNumberForm = (StudentNoSearchForm) form;
			studentNumberForm.setInput(true);
			return mapping.findForward("input");
		}
	
	private boolean isNameValid(String surname){
		boolean result = true;
		String test = "";
		int y=surname.length();
		
		for (int i = 0; i < y-1; i++) {
			test= surname.substring(i,i+1);
			if (test !=null && !"".equals(test) && !" ".equals(test) && !"-".equals(test)){
				if ("~".equals(test) || "'".equals(test) || "@".equals(test) || "^".equals(test)){
					result=false;
				}else{
//					System.out.println(test); 
				}
			}
		}
		return result;
	}
}

