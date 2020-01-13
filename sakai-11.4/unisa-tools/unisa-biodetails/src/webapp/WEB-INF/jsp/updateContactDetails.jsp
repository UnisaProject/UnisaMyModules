<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.biodetails.ApplicationResources"/>

<sakai:html>

<!-- Toolbar -->


<!-- Form -->
	<html:form action="/contactDetailsAction.do">
		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:heading><fmt:message key="page.heading.contact"/></sakai:heading>
		<sakai:instruction><fmt:message key="page.contact.instruction1"/><br><fmt:message key="page.required.instruction"/>&nbsp;<sakai:required/></sakai:instruction>

		<sakai:group_heading><fmt:message key="page.sub.heading.contact"/></sakai:group_heading>
		<sakai:group_table>
		<tr>
			<td><fmt:message key="page.home"/>&nbsp;<sakai:required/></td>
			<td><html:text property="homeNr" size="30" maxlength="28"/>&nbsp;</td>
			<td><fmt:message key="page.contact.dailing.instruction"/></td>
		</tr><tr>
			<td>&nbsp;</td>
			<td colspan="2"><strong><fmt:message key="page.contact.dailing.example"/><strong></td>
		</tr><tr>
			<td><fmt:message key="page.work"/></td>
			<td><html:text property="workNr" size="30" maxlength="28"/>&nbsp;</td>
			<td><fmt:message key="page.contact.dailing.instruction"/></td>
		</tr><tr>
			<td>&nbsp;</td>
			<td colspan="2"><strong><fmt:message key="page.contact.dailing.example"/><strong></td>
		</tr><tr>
			<td><fmt:message key="page.cell"/></td>
			<td><html:text property="cellNr" size="30" maxlength="20"/>&nbsp;</td>
			<td><fmt:message key="page.contact.cell.instruction"/></td>
		</tr><tr>
			<td>&nbsp;</td>
			<td colspan="2"><strong><fmt:message key="page.cell.example"/><strong></td>
		</tr><tr>
			<td><fmt:message key="page.fax"/></td>
			<td><html:text property="faxNr" size="30" maxlength="28"/>&nbsp;</td>
			<td><fmt:message key="page.contact.dailing.instruction"/></td>
		</tr><tr>
			<td>&nbsp;</td>
			<td colspan="2"><strong><fmt:message key="page.contact.dailing.example"/><strong></td>
		</tr><tr>
			<logic:equal name="bioDetailsForm" property="emailUpdatableFromWeb" value="true">
				<td><fmt:message key="page.email"/>&nbsp;<sakai:required/></td>
				<td colspan="2"><html:text property="email" size="62" maxlength="60"/>&nbsp;</td>
			</logic:equal>
			<logic:equal name="bioDetailsForm" property="emailUpdatableFromWeb" value="false">
				<td><fmt:message key="page.email"/>&nbsp;</td>
				<td colspan="2"><bean:write name="bioDetailsForm" property="email"/>&nbsp;</td>
			</logic:equal>
			
		</tr>
		</sakai:group_table>
		<sakai:actions>
			<html:hidden property="action" value="submit"/>
			<html:submit titleKey="button.submit"><fmt:message key="button.submit"/></html:submit>
			<html:cancel titleKey="button.cancel"/>
		</sakai:actions>

	</html:form>
</sakai:html>