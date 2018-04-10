<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<fmt:setBundle basename="za.ac.unisa.lms.tools.contactus.ApplicationResources"/>


<sakai:html>
	<sakai:messages/>
	<sakai:heading>
		<fmt:message key="page.studentviewc1.heading"/>
	</sakai:heading>
	
	<fmt:message key="page.studentviewc1.note"/><br/>
	<fmt:message key="page.studentviewc1.required"/>
	<br/>
	<html:form action="contactusaction.do?action=forgotStudentNumber">
		<sakai:group_table>
			<tr>
				<td><fmt:message key="page.studentviewc1.surname"/><td>
				<td><html:text property="forgotSurname" size="30" maxlength="30"/></td>
			</tr>
			<tr>
				<td><fmt:message key="page.studentviewc1.name"/><td>
				<td><html:text property="forgotName" size="30" maxlength="60"/></td>
			</tr>
			<tr>
				<td><fmt:message key="page.studentviewc1.dob"/><td>
				<td>
					<html:text property="forgotdoby" size="4" maxlength="4"/>/
					<html:text property="forgotdobm" size="2" maxlength="2"/>/
					<html:text property="forgotdobd" size="2" maxlength="2"/>
				</td>
			</tr>
			<tr>
				<td>
					<html:submit property="button" value="Submit"/>
					<html:submit property="button" value="Back"/>
					<html:submit property="button" value="Clear"/>
				</td>
			</tr>
			
		</sakai:group_table>
		
		
	</html:form>
</sakai:html>
