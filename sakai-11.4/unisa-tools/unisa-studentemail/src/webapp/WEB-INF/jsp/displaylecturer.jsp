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
		<sakai:heading><fmt:message key="lecturer.heading"/></sakai:heading>
		<sakai:instruction><fmt:message key="lecturer.intruction"/></sakai:instruction>
		<sakai:group_table>
			<tr>
				<th colspan="2"><fmt:message key="lecturer.subheading"/> <bean:write name="displayemailForm" property="course"/>:</th>
			</tr>
		</sakai:group_table>
		<sakai:group_table>
			<tr>
				<td><html:radio property="choice" value="1"/><td>
				<td><fmt:message key="lecturer.option1"/><br><fmt:message key="lecturer.option1desc"/></td>
			</tr>
			<tr>
				<td><html:radio property="choice" value="2"/><td>
				<td><fmt:message key="lecturer.option2"/><br><fmt:message key="lecturer.option2desc"/></td>
			</tr>
			<tr>
				<td><html:radio property="choice" value="3"/><td>
				<td><fmt:message key="lecturer.option3"/> <html:text property="emailaddres"/> <fmt:message key="lecturer.option3part2"/> <br><fmt:message key="lecturer.option3desc"/><br><br>
				    <fmt:message key="lecturer.message"/></td>
			</tr>

		</sakai:group_table>
		
		<sakai:actions>
			<html:submit property="action"><fmt:message key="lecturer.submit"/></html:submit>
		</sakai:actions>
		
		</html:form>
</sakai:html>
