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

    <sakai:heading><fmt:message key="adminlecturers.heading"/></sakai:heading>

	<sakai:group_heading> <fmt:message key="adminlecturers.lecturers"/> </sakai:group_heading>

	<sakai:messages/>
	<sakai:messages message="true"/>

	<sakai:flat_list>
		<tr>
			<th><fmt:message key="adminlecturers.tableheading.novell"/></th>
			<th><fmt:message key="adminlecturers.tableheading.lecturer"/></th>
			<th><fmt:message key="adminlecturers.tableheading.email"/></th>
			<th><fmt:message key="adminlecturers.tableheading.persno"/></th>
			<th><fmt:message key="adminlecturers.tableheading.telnumber"/></th>
			<th><fmt:message key="adminlecturers.tableheading.office"/></th>
			<th><fmt:message key="adminlecturers.tableheading.cell"/></th>
			<th><fmt:message key="adminlecturers.tableheading.othernumber"/></th>
			<th><fmt:message key="adminlecturers.tableheading.dept"/></th>
		</tr>
		<logic:iterate name="adminForm" property="lecturerList" id="record" indexId="i">
		<tr>
			<td><html:radio property="selectedLecturer" value="novellId" idName="record"/>
				<bean:write name="record" property="novellId"/>
			</td>
			<td><bean:write name="record" property="name"/></td>
			<td><bean:write name="record" property="email"/></td>
			<td><bean:write name="record" property="persNumber"/></td>
			<td><bean:write name="record" property="contact1"/></td>
			<td><bean:write name="record" property="contact2"/></td>
			<td><font color="#F88017"><bean:write name="record" property="cellNumber"/></font></td>
			<td><font color='#F88017'> <bean:write name="record" property="telNumber"/></font></td>
			<td><bean:write name="record" property="department"/></td>
		</tr>
		</logic:iterate>
	</sakai:flat_list>
	</p>
	<sakai:actions>
		<html:submit property="action">
			<fmt:message key="button.add.lecturer"/>
		</html:submit>
		<html:submit property="action">
			<fmt:message key="adminlecturersedit.button.edit"/>
		</html:submit>
		<html:submit property="action">
			<fmt:message key="adminlecturersedit.button.delete"/>
		</html:submit>
		<input type="button" value="Print" onClick="window.print()" />
	</sakai:actions>
	</html:form>
</sakai:html>