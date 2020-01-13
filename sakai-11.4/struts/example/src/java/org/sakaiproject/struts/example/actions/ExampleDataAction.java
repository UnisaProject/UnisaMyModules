package org.sakaiproject.struts.example.actions;

import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class ExampleDataAction extends Action {
	
	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		/* Handling of action messages */
		ActionMessages messages = new ActionMessages();
		ActionMessage message = new ActionMessage("example.message1");
		messages.add(ActionMessages.GLOBAL_MESSAGE, message);
		message = new ActionMessage("example.message2");
		messages.add(ActionMessages.GLOBAL_MESSAGE, message);
		
		ActionMessages errors = new ActionMessages();
		ActionMessage error = new ActionMessage("example.error1");
		errors.add(ActionMessages.GLOBAL_MESSAGE, error);
		error = new ActionMessage("example.message2");
		errors.add(ActionMessages.GLOBAL_MESSAGE, error);
		
		saveMessages(request, messages);
		saveErrors(request, errors);
		
		List students = new Vector();
		students.add(new Student("Johan", "van den Berg"));
		students.add(new Student("Shaun", "Donovan"));
		request.setAttribute("students", students);
		return mapping.findForward("display");
	}

	public class Student {
		private String name;
		private String surname;
		
		public Student(String name, String surname) {
			this.name = name;
			this.surname = surname;
		}
		
		public String getName() {
			return name;
		}
		
		public String getSurname() {
			return surname;
		}
	}

}
