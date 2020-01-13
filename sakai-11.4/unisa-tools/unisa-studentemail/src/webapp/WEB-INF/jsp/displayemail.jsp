<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.studentemail.ApplicationResources"/>

<sakai:html>
	<html:form action="displayemail">
		<sakai:messages/>
		<sakai:heading><fmt:message key="student.confirm.heading"/></sakai:heading>
		<sakai:group_table>
			<tr>
				<td><fmt:message key="student.confirm.from"/></td>
				<td><b><bean:write name="displayemailForm" property="student.number"/></b></td>
			</tr>
			<tr>
				<td><fmt:message key="student.confirm.emailaddress"/></td>
				<td><b><bean:write name="displayemailForm" property="student.semail"/></b></td>
			</tr>
			<tr>
				<td><fmt:message key="student.confirm.messagesubject"/></td>
				<td><bean:write name="displayemailForm" property="subject"/>
				    <html:hidden property="subject" /></td>
			</tr> 
		</sakai:group_table>
		<sakai:group_table>
			<tr>
				<td><fmt:message key="student.confirm.message"/></td>
			</tr>
			<tr>
				<td><bean:write name="displayemailForm" property="message"/>
				    <html:hidden property="message"/></td>
				
			</tr>
		</sakai:group_table>
					<sakai:actions>
			<html:submit property="action"><fmt:message key="student.submit"/></html:submit>
			<html:submit property="action"><fmt:message key="student.back"/></html:submit>
		</sakai:actions>
	</html:form>
</sakai:html>
