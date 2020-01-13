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

    <sakai:heading><fmt:message key="admininstitutiondays.heading1"/></sakai:heading>

	<sakai:messages/>
	<sakai:messages message="true"/>

	<sakai:instruction> <fmt:message key="admininstitutiondays.instruction1"/> </sakai:instruction>

	<sakai:group_table>
		<tr>
			<td>
				<fmt:message key="admininstitutiondays.year"/>
			</td>
			<td>
				<html:select property="selectedYear">
					<html:options collection="yearList" property="value" labelProperty="label"/>
				</html:select>
			</td>
		</tr>
		<tr>
			<td>
				<fmt:message key="admininstitutiondays.month"/>
			</td>
			<td>
				<html:select property="selectedMonth">
					<html:options collection="monthList" property="value" labelProperty="label"/>
				</html:select>
			</td>
		</tr>
	</sakai:group_table>

	<sakai:actions>
		<html:submit property="action">
			<fmt:message key="admininstitutiondays.button.edit"/>
		</html:submit>
		<html:submit property="action">
			<fmt:message key="admininstitutiondays.button.view"/>
		</html:submit>
		<input type="button" value="Print" onClick="window.print()" />
	</sakai:actions>

	<logic:notEmpty name="adminForm" property="instDaysList">
		<sakai:group_heading>
			<fmt:message key="admininstitutiondaysview.data"/>
			<bean:write name="adminForm" property="selectedYear"/>
			<bean:write name="adminForm" property="selectedMonthDesc"/>
		</sakai:group_heading>
		<sakai:flat_list>
			<tr>
				<td colspan='2'><fmt:message key="admininstitutiondays.tableheading.day"/>	</td>
				<td><fmt:message key="admininstitutiondays.tableheading.institution"/></td>
			</tr>
	 		<logic:iterate name="adminForm" property="instDaysList" id="record" indexId="i">
			<tr>
				<td width='25'><bean:write name="record" property="instWeekday"/></td>
				<td><bean:write name="record" property="instDay"/></td>
				<td><bean:write name="record" property="instDesc"/></td>
			</tr>
			</logic:iterate>
		</sakai:flat_list>
	</logic:notEmpty>

	</html:form>
</sakai:html>

