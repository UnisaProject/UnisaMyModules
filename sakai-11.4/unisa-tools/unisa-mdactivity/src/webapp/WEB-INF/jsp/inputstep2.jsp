<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.mdactivity.ApplicationResources"/>

<sakai:html>
	<html:form action="/displaymdactivity">

		<sakai:heading><fmt:message key="page.heading"/></sakai:heading>
		<sakai:messages/>

		<sakai:instruction><fmt:message key="page.instruction2"/></sakai:instruction>

		<sakai:group_table>
			<tr>
				<td><strong><fmt:message key="page.heading.studentnr"/></strong></td>
				<td><bean:write name="mdActivityForm" property="student.number"/></td>
			</tr><tr>
				<td><strong><fmt:message key="page.heading.name"/></strong></td>
				<td><bean:write name="mdActivityForm" property="student.name"/></td>
			</tr>
		</sakai:group_table>

		<sakai:flat_list>
		<tr>
			<th align="left">&nbsp;</th>
			<th align="left" colspan="2"><fmt:message key="table.heading.studyunit"/>&nbsp;</th>
			<th align="left" colspan="2"><fmt:message key="table.heading.qualification"/>&nbsp;</th>
			<th align="left"><fmt:message key="table.heading.lastacadyear"/>&nbsp;</th>
			<th align="left"><fmt:message key="table.heading.thesis"/>&nbsp;</th>
			<th align="left"><fmt:message key="table.heading.status"/>&nbsp;</th>
		</tr>
		<logic:notEmpty name="mdActivityForm" property="studyUnitRecords">
			<logic:iterate name="mdActivityForm" property="studyUnitRecords" id="studyunit" indexId="i">
			<tr>
				<td><html:radio property="selectedStudyUnit" value='<%= "" + i.toString() + "" %>'>
						</html:radio></td>
				<td><bean:write name="studyunit" property="studyUnitCode"/>&nbsp;</td>
				<td><bean:write name="studyunit" property="studyUnitDescription"/>&nbsp;</td>
				<td><bean:write name="studyunit" property="qualCode"/>&nbsp;</td>
				<td><bean:write name="studyunit" property="qualDescription"/>&nbsp;</td>
				<td><bean:write name="studyunit" property="lastAcademicYear"/>&nbsp;</td>
				<td><bean:write name="studyunit" property="thesis"/>&nbsp;</td>
				<td><bean:write name="studyunit" property="status"/>&nbsp;</td>
			</tr>
			</logic:iterate>
		</logic:notEmpty>
		</sakai:flat_list>
<br/>
		<sakai:actions>
			<html:submit property="act"><fmt:message key="button.display"/></html:submit>
			<html:submit property="act"><fmt:message key="button.cancel"/></html:submit>
		</sakai:actions>
		</html:form>
</sakai:html>
