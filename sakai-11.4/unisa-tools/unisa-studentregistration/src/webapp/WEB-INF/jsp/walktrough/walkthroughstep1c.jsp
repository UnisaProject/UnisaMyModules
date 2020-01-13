
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.studentregistration.ApplicationResources"/>

<sakai:html>

<%-- Toolbar --%>
<sakai:tool_bar>
	<html:link href="studentRegistration.do?action=applyForStudentNr">
		<fmt:message key="page.main.link.apply"/>
	</html:link>
	<html:link href="studentRegistration.do?action=walkThroughStep1&type=U">
		<fmt:message key="page.main.link.undergrad"/>
	</html:link>
	<html:link href="studentRegistration.do?action=walkThroughStep1&type=P">
		<fmt:message key="page.main.link.postgrad"/>
	</html:link>
	<html:link href="studentRegistration.do?action=walkThroughStep1&type=S">
		<fmt:message key="page.main.link.short"/>
	</html:link>
</sakai:tool_bar>


<br/>

	<html:form action="/studentRegistration">
		<html:hidden property="page" value="walk1c"/>

		<sakai:messages/>
		<sakai:messages message="true"/>

		<logic:equal name="studentRegistrationForm" property="registrationType" value="U">
			<sakai:heading>
				<fmt:message key="page.walk.heading.under"/><br/>
			</sakai:heading>
		</logic:equal>
		<logic:equal name="studentRegistrationForm" property="registrationType" value="P">
			<sakai:heading>
				<fmt:message key="page.walk.heading.post"/><br/>
			</sakai:heading>
		</logic:equal>
		<logic:equal name="studentRegistrationForm" property="registrationType" value="S">
			<sakai:heading>
				<fmt:message key="page.walk.heading.short"/><br/>
			</sakai:heading>
		</logic:equal>

	<br/>

	<logic:equal name="found" value="false">
		<sakai:flat_list>
			<tr>
				<td><strong><fmt:message key="page.display.studentNumber.info1"/></strong></td>
			</tr><tr>
				<td><strong><fmt:message key="page.display.studentNumber.info2"/></strong></td>
			</tr><tr>
				<td><strong><fmt:message key="page.display.studentNumber.info3"/></strong></td>
			</tr>
		</sakai:flat_list>
	</logic:equal>

	<logic:notEqual name="found" value="false">
	<logic:equal name="studentRegistrationForm" property="question1" value="Y">
		<sakai:flat_list>
			<tr>
				<td width="10%"><fmt:message key="page.walk.question1"/></td>
				<td align="left" width="10%"><html:radio property="question1" value="Y"/>Yes</td>
			</tr><tr>
				<td width="10%">&nbsp;</td>
				<td align="left" width="10%"><html:radio property="question1" value="N"/>No</td>
			</tr>
			<tr>
				<td width="10%"><fmt:message key="page.walk.question2"/></td>
				<td align="left" width="10%"><html:radio property="question2" value="Y"/>Yes</td>
			</tr><tr>
				<td width="10%">&nbsp;</td>
				<td align="left" width="10%"><html:radio property="question2" value="N"/>No</td>
			</tr>
		</sakai:flat_list>
	</logic:equal>
	</logic:notEqual>

	<logic:equal name="studentRegistrationForm" property="question2" value="N">
	<hr/>
		<sakai:group_heading><fmt:message key="page.walk.complete"/></sakai:group_heading>
		<sakai:group_table>
			<tr>
				<td colspan="2"><fmt:message key="page.required.instruction"/>&nbsp;</td>
			</tr><tr>
				<td width="20%"><fmt:message key="page.surname"/>&nbsp;</td>
				<td align="left" width="10%"><html:text name="studentRegistrationForm" property="student.surname" maxlength="28" size="30"/>&nbsp;</td>
			</tr>
			<tr>
				<td width="20%"><fmt:message key="page.walk.firstnames"/>&nbsp;</td>
				<td align="left" width="10%"><html:text name="studentRegistrationForm" property="student.firstnames" maxlength="60" size="40"/>&nbsp;</td>
			</tr><tr>
				<td width="20%"><fmt:message key="page.walk.birth"/>&nbsp;</td>
				<td align="left" width="10%">
					<fmt:message key="page.walk.year"/>&nbsp;<html:text name="studentRegistrationForm" property="student.birthYear" maxlength="4" size="5"/>&nbsp;
					<fmt:message key="page.walk.month"/>&nbsp;<html:text name="studentRegistrationForm" property="student.birthMonth" maxlength="2" size="3"/>&nbsp;
					<fmt:message key="page.walk.day"/>&nbsp;<html:text name="studentRegistrationForm" property="student.birthDay" maxlength="2" size="3"/>&nbsp;
				</td>
			</tr>
		</sakai:group_table>
	</logic:equal>

	<sakai:actions>
		<html:submit property="action"><fmt:message key="button.continue"/></html:submit>
		<html:submit property="action"><fmt:message key="button.cancel"/></html:submit>
	</sakai:actions>

</html:form>

</sakai:html>