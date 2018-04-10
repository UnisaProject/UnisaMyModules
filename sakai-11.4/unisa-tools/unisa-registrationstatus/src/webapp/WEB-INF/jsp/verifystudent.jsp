
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.registrationstatus.ApplicationResources"/>

<sakai:html>
	<html:form action="/registrationStatus">

	<html:hidden property="page" value="verifyinput"/>

	<sakai:messages/>
	<sakai:heading><fmt:message key="page.heading"/></sakai:heading>
	<sakai:instruction><fmt:message key="page.input.instruction"/></sakai:instruction>

	<sakai:group_table>
	<tr>
		<td><fmt:message key="page.studentNumber"/>&nbsp;</td>
		<td><html:text property="student.number" size="10" maxlength="8"/></td>
	</tr><tr>
		<td><fmt:message key="page.studentSurname"/>&nbsp;</td>
		<td><html:text property="student.surname" size="30" maxlength="30"/></td>
	</tr><tr>
		<td><fmt:message key="page.firstNames"/>&nbsp;</td>
		<td><html:text property="student.firstnames" size="60" maxlength="60"/></td>
	</tr><tr>
		<td><fmt:message key="page.birthdate"/>&nbsp;</td>
		<td><fmt:message key="page.year"/>&nbsp;<html:text property="student.birthYear" size="4" maxlength="4"/>&nbsp;<fmt:message key="page.month"/>&nbsp;<html:text property="student.birthMonth" size="2" maxlength="2"/>&nbsp;<fmt:message key="page.day"/>&nbsp;<html:text property="student.birthDay" size="2" maxlength="2"/></td>
	</tr>
	</sakai:group_table>

	<sakai:actions>
		<html:submit property="action"><fmt:message key="button.continue"/></html:submit>
		<html:submit property="action"><fmt:message key="button.cancel"/></html:submit>
	</sakai:actions>
	<div style="display: none;"><br><bean:write name="registrationStatusForm" property="version"/></div>
	</html:form>
</sakai:html>