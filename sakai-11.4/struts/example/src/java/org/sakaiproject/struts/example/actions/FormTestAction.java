package org.sakaiproject.struts.example.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.sakaiproject.struts.example.forms.FormTestForm;

public class FormTestAction extends DispatchAction {

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		FormTestForm formTestForm = (FormTestForm) form;
		ActionMessages errors = new ActionMessages();
		if (isCancelled(request)) {
			formTestForm.setUsername(null);
			formTestForm.setPassword(null);
			formTestForm.setDescription(null);
			formTestForm.setPasswordAgain(null);
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.cancel"));
			saveErrors(request, errors);
			return display(mapping, form, request, response);
		}
		errors = formTestForm.validate(mapping, request);
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return display(mapping, form, request, response);
		}
		
		ActionMessages messages = new ActionMessages();
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.save"));
		saveMessages(request, messages);
		
		return display(mapping, form, request, response);
	}

	public ActionForward display(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		return mapping.findForward("display");
	}
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (request.getParameter(mapping.getParameter()) == null) {
			return display(mapping, form, request, response);
		} else {
			return super.execute(mapping, form, request, response);
		}
	}
		
}
