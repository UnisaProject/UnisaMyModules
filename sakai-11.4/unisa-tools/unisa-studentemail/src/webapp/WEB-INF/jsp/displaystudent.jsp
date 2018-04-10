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
		<sakai:messages message="true"/>
		<logic:equal name="displayemailForm" property="noemail" value="true">
			<sakai:heading><fmt:message key="student.heading"/></sakai:heading>
			<sakai:instruction><fmt:message key="student.intruction"/></sakai:instruction>
			<sakai:group_table>
				<tr>
					<td><fmt:message key="student.from"/></td>
					<td><b><bean:write name="displayemailForm" property="student.number"/></b></td>
				</tr>
				<tr>
					<td><fmt:message key="student.emailaddres"/></td>
					<td><b><bean:write name="displayemailForm" property="student.semail"/></b></td>
				</tr>
				<tr>
					<td><fmt:message key="student.messagesubject"/></td>
					<td><html:text property="subject" size="80"/></td>
				</tr>
			</sakai:group_table>
			<sakai:group_table>
				<tr>
					<td><fmt:message key="student.message"/></td>
				</tr>
				<tr>
					<td><html:textarea property="message" cols="68" rows="10"></html:textarea></td>
				</tr>
			</sakai:group_table>
		
			<sakai:actions>
				<html:submit property="action"><fmt:message key="student.send"/></html:submit>
				<html:submit property="action"><fmt:message key="student.preview"/></html:submit>
				<html:submit property="action"><fmt:message key="student.clear"/></html:submit>
			</sakai:actions>
		</logic:equal>
		<logic:equal name="displayemailForm" property="noemail" value="false">
				<fmt:message key="errors.noemail"/>
		</logic:equal>
		
		</html:form>
</sakai:html>
