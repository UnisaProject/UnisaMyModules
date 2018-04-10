<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c_rt" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.satbook.ApplicationResources"/>

<sakai:html>

	<html:form action="/satbookAdmin.do">

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

    <sakai:heading><fmt:message key="admininstitutions.heading"/></sakai:heading>

	<sakai:messages/>
	<sakai:messages message="true"/>

	<sakai:group_heading> <fmt:message key="admininstitutions.tableheading.institutions"/> </sakai:group_heading>
	<sakai:flat_list>
		<tr>
			<td><fmt:message key="admininstitutions.tableheading.institution"/></td>
		</tr>
		<logic:iterate name="adminForm" property="institutionList" id="record" indexId="i">
		<tr>
			<td><html:radio property="selectedInstitution" value="instId" idName="record"/>
			<bean:write name="record" property="instId"/>
				<bean:write name="record" property="instName"/>
			</td>
		</tr>
		</logic:iterate>
	</sakai:flat_list>

	<sakai:actions>
		<html:submit property="action">
			<fmt:message key="button.add.institution"/>
		</html:submit>
		<html:submit property="action">
			<fmt:message key="admininstitutionsedit.button.edit"/>
		</html:submit>
		<html:submit property="action">
			<fmt:message key="admininstitutionsedit.button.delete"/>
		</html:submit>
		<input type="button" value="Print" onClick="window.print()" />
	</sakai:actions>
	</html:form>
</sakai:html>
