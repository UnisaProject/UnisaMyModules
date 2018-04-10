//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.tsastudentnumber.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

import za.ac.unisa.lms.tools.tsastudentnumber.dao.StudentNumberQueryDAO;
import za.ac.unisa.lms.tools.tsastudentnumber.forms.StudentNumberForm;

/** 
 * MyEclipse Struts
 * Creation date: 01-13-2006
 * 
 * XDoclet definition:
 * @struts:action path="/displaystudentnumber" name="studentNumberForm" parameter="action" scope="request"
 */
public class DisplayStudentNumberAction extends DispatchAction {

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
		
		StudentNumberForm studentNumberForm = (StudentNumberForm) form;
		ActionMessages messages = new ActionMessages();
		
		
			if (studentNumberForm.getTsaStudentNumber() == null || "".equalsIgnoreCase(studentNumberForm.getTsaStudentNumber())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
    			new ActionMessage("errors.message", "Enter a CAO application number."));
				addErrors(request, messages);
				return mapping.findForward("display");
			}
			studentNumberForm.setTsaStudentNumber(studentNumberForm.getTsaStudentNumber().replaceAll(" ",""));
			if (studentNumberForm.getTsaStudentNumber().trim().length() != 12 ){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
    			new ActionMessage("errors.message", "CAO application number must be 12 characters."));
				addErrors(request, messages);
				return mapping.findForward("display");
			}
			/* number must be numeric */
			try {
				Long.parseLong(studentNumberForm.getTsaStudentNumber());
			} catch (NumberFormatException e) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("errors.message",
									"CAO appication number must be numeric."));
					addErrors(request, messages);
					return mapping.findForward("display");
			}
			
			StudentNumberQueryDAO dao = new StudentNumberQueryDAO();
			String unisaStudentNr = "";
			unisaStudentNr = dao.getUnisaStudentNumber(studentNumberForm.getTsaStudentNumber());
			if ("".equalsIgnoreCase(unisaStudentNr)){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
    			new ActionMessage("errors.message", "Your CAO application has not been received by Unisa."));
				addErrors(request, messages);
				return mapping.findForward("display");
			}
			studentNumberForm.setUnisaStudentNumber(unisaStudentNr);
			studentNumberForm.setInput(false);
		return mapping.findForward("display");
	}
	
	public ActionForward input(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {
			
			StudentNumberForm studentNumberForm = (StudentNumberForm) form;
			studentNumberForm.setUnisaStudentNumber("");
			studentNumberForm.setUnisaStudentNumber("");
			studentNumberForm.setInput(true);
			return mapping.findForward("display");
		}
	
	public ActionForward back(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {
			
			StudentNumberForm studentNumberForm = (StudentNumberForm) form;
			studentNumberForm.setInput(true);
			
			return mapping.findForward("display");
		}

}

