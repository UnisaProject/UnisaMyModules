<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>


<!-- 



/* This is the code for the action purely for reference purposes */



package org.sakaiproject.struts.example.actions;



import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;



import org.apache.struts.action.Action;

import org.apache.struts.action.ActionErrors;

import org.apache.struts.action.ActionForm;

import org.apache.struts.action.ActionForward;

import org.apache.struts.action.ActionMapping;

import org.apache.struts.action.ActionMessage;

import org.apache.struts.action.ActionMessages;

import org.apache.struts.actions.DispatchAction;

import org.apache.struts.validator.ValidatorActionForm;

import org.apache.struts.validator.ValidatorForm;

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

-->


<sakai:html>
	<sakai:tool_bar>
		<html:link action="/taglibs/index">Back</html:link>
		<html:link href="actionstag.source" target="actionstag">Source</html:link>
	</sakai:tool_bar>

	<sakai:heading>&lt;sakai:actions&gt; Tag</sakai:heading>
	<sakai:messages/>

	<sakai:messages message="true"/>

	<p>Used to group action buttons that will submit or cancel a form, etc.</p>

	
	<html:form action="/taglibs/actionstag">

	<sakai:group_heading>User Information</sakai:group_heading>

	<sakai:group_table>

		<tr>

			<td><label>Username <sakai:required/></label></td>

			<td><html:text property="username"></html:text>

		</tr>

		<tr>

			<td><label>Password <sakai:required/></label></td>

			<td><html:password property="password"/></td>

		</tr>

		<tr>

			<td><label>Password again <sakai:required/></label></td>

			<td><html:password property="passwordAgain"/></td>

		</tr>

		<tr>

			<td><label>Description</label></td>

			<td><html:text property="description"/></td>

		</tr>

		<tr>
			<td><label>Profile</label></td>
			<td><sakai:html_area property="profile"/></td>
		</tr>
	</sakai:group_table>

	<sakai:actions>

	<html:hidden property="action" value="save"/>

	<html:submit styleClass="active" titleKey="button.save"/>

	<html:cancel titleKey="button.cancel"/>

	</sakai:actions>

	</html:form>
</sakai:html>