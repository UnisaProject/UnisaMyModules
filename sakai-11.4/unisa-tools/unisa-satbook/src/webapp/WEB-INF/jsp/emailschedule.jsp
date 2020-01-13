<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c_rt" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.satbook.ApplicationResources"/>

<sakai:html>

	<html:form action="/satbookAdmin.do">
	<html:hidden property="atstep" value="scheduleemail"/>

    <sakai:heading><fmt:message key="emailschedule.heading"/></sakai:heading>

    <sakai:instruction><fmt:message key="sendgroupemail.instruction"/></sakai:instruction>
	<sakai:instruction> <fmt:message key="info.required"/> <sakai:required/> </sakai:instruction>
	<sakai:messages/>
	<sakai:messages message="true"/>

	<sakai:group_table>
		<tr>
			<td><fmt:message key="sendgroupemail.group"/> <sakai:required/> </td>
			<td>
				<logic:iterate name="adminForm" property="assistantTypeList" id="value" indexId="i">
					<html:multibox property="selectedAssistantTypes"><bean:write name="i"/></html:multibox>
					<bean:write name="value" property="label"/>
					<br>
				</logic:iterate>
			</td>
		</tr><tr>
			<td><fmt:message key="sendgroupemail.emailheading"/> <sakai:required/> </td>
			<td><html:text property="emailHeading" size="30" maxlength="100"/></td>
		</tr><tr>
			<td><fmt:message key="sendgroupemail.body"/> <sakai:required/> </td>
			<td><html:textarea property="emailMessage" cols="25" rows="4"></html:textarea></td>
		</tr>
		<tr>
			<td><fmt:message key="emailschedule.fromdate"/> <sakai:required/> </td>
			<td>
				<html:text property="emailFromDateDD" size="2" maxlength="2"/>-
				<html:text property="emailFromDateMM" size="2" maxlength="2"/>-
				<html:text property="emailFromDateYYYY" size="4" maxlength="4"/>
			</td>
		</tr><tr>
			<td><fmt:message key="emailschedule.todate"/> <sakai:required/> </td>
			<td>
				<html:text property="emailToDateDD" size="2" maxlength="2"/>-
				<html:text property="emailToDateMM" size="2" maxlength="2"/>-
				<html:text property="emailToDateYYYY" size="4" maxlength="4"/>
			</td>
		</tr>
	</sakai:group_table>

	<sakai:actions>
		<html:submit property="action">
			<fmt:message key="email.button.send"/>
		</html:submit>
		<html:submit property="action">
			<fmt:message key="email.button.cancel"/>
		</html:submit>
	</sakai:actions>

	</html:form>
</sakai:html>
