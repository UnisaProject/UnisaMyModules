<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c_rt" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.satbook.ApplicationResources"/>

<sakai:html>

	<html:form action="/satbookAdmin.do">

    <sakai:heading><fmt:message key="adminassistants.heading"/></sakai:heading>

	<sakai:tool_bar>
		<html:link href="satbookMonthly.do?action=monthlyview">
			<fmt:message key="button.to.monthlyview"/>
		</html:link>
		<br>
		<html:link href="satbookAdmin.do?action=adminlecturers">
			<fmt:message key="adminlecturers.heading"/>
		</html:link>
		<html:link href="satbookAdmin.do?action=admininstitutions">
			<fmt:message key="admininstitutions.heading"/>
		</html:link>
		<html:link href="satbookAdmin.do?action=admininstitutiondays">
			<fmt:message key="admininstitutiondays.heading1"/>
		</html:link>
		<html:link href="satbookAdmin.do?action=adminclassrooms">
			<fmt:message key="adminclassrooms.heading"/>
		</html:link>
		<html:link href="satbookAdmin.do?action=adminmaterial">
			<fmt:message key="adminmaterial.heading"/>
		</html:link>
		<html:link href="satbookAdmin.do?action=adminassistanttype">
			<fmt:message key="adminassistanttype.heading"/>
		</html:link>
		<html:link href="satbookAdmin.do?action=adminassistants">
			<fmt:message key="adminassistants.heading"/>
		</html:link>
		<br>
		<html:link href="satbookAdmin.do?action=sendgroupemail">
			<fmt:message key="sendgroupemail.heading"/>
		</html:link>
		<html:link href="satbookAdmin.do?action=emailschedule">
			<fmt:message key="emailschedule.heading"/>
		</html:link>
	</sakai:tool_bar>

	<sakai:messages/>
	<sakai:messages message="true"/>

	<sakai:instruction><fmt:message key="adminassistants.instruction"/></sakai:instruction>

	<fmt:message key="adminassistants.view"/>
	
	<html:select property="selectedAssTypeForReport">
		<html:options collection="assistantTypeList" property="value" labelProperty="label"/>
	</html:select>
	
	<sakai:actions>
		<html:submit property="action">
			<fmt:message key="adminassistants.button.report"/>
		</html:submit>
	</sakai:actions>

	

	<logic:notEmpty name="adminForm" property="assistantList">
	<sakai:flat_list>
		<tr>
			<td><fmt:message key="adminassistants.tableheading.assistant"/></td>
			<td><fmt:message key="adminassistants.tableheading.email"/></td>
		</tr>
		<logic:iterate name="adminForm" property="assistantList" id="record" indexId="i">
			<tr>
				<td>
					<html:radio property="selectedAssistant" value="assistantId" idName="record"/>
					<bean:write name="record" property="assistantName"/>
				</td>
				<td><bean:write name="record" property="assistantEmail"/></td>
				<td><bean:write name="record" property="assistantTypeDesc"/></td>
			</tr>
		</logic:iterate>
	</sakai:flat_list>
	</logic:notEmpty>

	<sakai:actions>
		<html:submit property="action">
			<fmt:message key="adminassistants.button.add"/>
		</html:submit>
		<html:submit property="action">
			<fmt:message key="adminassistants.button.edit"/>
		</html:submit>
		<html:submit property="action">
			<fmt:message key="adminassistants.button.delete"/>
		</html:submit>
		<input type="button" value="Print" onClick="window.print()" />
	</sakai:actions>

	</html:form>
</sakai:html>


