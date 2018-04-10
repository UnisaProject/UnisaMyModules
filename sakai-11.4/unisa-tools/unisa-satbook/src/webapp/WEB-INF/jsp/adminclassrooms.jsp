<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c_rt" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.satbook.ApplicationResources"/>

<sakai:html>
<!-- List of classrooms/regions/venues -->
	<html:form action="/satbookAdmin.do">
	<logic:equal name="satbookMonthlyForm" property="systemID" value="1">
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

    <sakai:heading><fmt:message key="adminclassrooms.heading"/></sakai:heading>

	<sakai:messages/>
	<sakai:messages message="true"/>

	<sakai:flat_list>
		<tr>
			<td><fmt:message key="adminclassrooms.tableheading.classrooms"/></td>
			<td><fmt:message key="adminclassrooms.tableheading.contactperson"/></td>
			<td><fmt:message key="adminclassrooms.tableheading.contactnumber1"/></td>
			<td><fmt:message key="adminclassrooms.tableheading.contactnumber2"/></td>
			<td><fmt:message key="adminclassrooms.tableheading.address"/></td>
			<td><fmt:message key="adminclassrooms.tableheading.active"/></td>
		</tr>
		<logic:notEmpty name="adminForm" property="classroomList">
		<logic:iterate name="adminForm" property="classroomList" id="record" indexId="i">
			<tr>
				<td>
					<html:radio property="selectedClassroom" value="regionCode" idName="record"/>
					<bean:write name="record" property="regionDesc"/>
				</td>
				<td><bean:write name="record" property="contactPerson"/></td>
				<td><bean:write name="record" property="contactNum1"/></td>
				<td><bean:write name="record" property="contactNum2"/></td>
				<td>
					<bean:write name="record" property="regionAddress1"/>
					<bean:write name="record" property="regionAddress2"/>
					<bean:write name="record" property="regionAddress3"/>
					<bean:write name="record" property="regionAddress4"/>
					<bean:write name="record" property="regionAddressPcode"/>
				</td>
				<td><bean:write name="record" property="regionActive"/></td>
			</tr>
		</logic:iterate>
		</logic:notEmpty>
	</sakai:flat_list>

	<sakai:actions>
		<html:submit property="action">
			<fmt:message key="adminclassrooms.button.add"/>
		</html:submit>
				<html:submit property="action">
			<fmt:message key="adminclassrooms.button.edit"/>
		</html:submit>
		<html:submit property="action">
			<fmt:message key="adminclassrooms.button.delete"/>
		</html:submit>
		<input type="button" value="Print" onClick="window.print()" />
	</sakai:actions>
	</logic:equal>
	<logic:equal name="satbookMonthlyForm" property="systemID" value="2">
	<fmt:message key="dailyview.heading.venue"/>
	<sakai:tool_bar>
		<html:link href="satbookMonthly.do?action=monthlyview">
			<fmt:message key="button.to.monthlyview"/>
		</html:link>
		<br>
		<html:link href="satbookMonthly.do?action=adminLink">
				<fmt:message key="adminsystemlink.heading2"/>
		</html:link>
		<html:link href="satbookAdmin.do?action=adminVenues">
			<fmt:message key="adminvenues.heading"/>
		</html:link>
		<html:link href="satbookAdmin.do?action=adminmaterial">
			<fmt:message key="adminmaterial.heading"/>
		</html:link>
		<html:link href="satbookAdmin.do?action=adminBookingType">
			<fmt:message key="adminbookingtype.heading"/>
		</html:link>
		<br>
		<html:link href="satbookAdmin.do?action=sendgroupemail">
			<fmt:message key="sendgroupemail.heading"/>
		</html:link>
		<html:link href="satbookAdmin.do?action=emailschedule">
			<fmt:message key="emailschedule.heading"/>
		</html:link>
	</sakai:tool_bar>

    <sakai:heading><fmt:message key="adminvenues.heading"/></sakai:heading>

	<sakai:messages/>
	<sakai:messages message="true"/>

	<sakai:flat_list>
		<tr>
			<td><fmt:message key="adminvenues.tableheading.venues"/></td>
			<td><fmt:message key="adminclassrooms.tableheading.contactperson"/></td>
			<td><fmt:message key="adminclassrooms.tableheading.contactnumber1"/></td>
			<td><fmt:message key="adminclassrooms.tableheading.email"/></td>
			<td><fmt:message key="adminclassrooms.tableheading.address"/></td>
			<td><fmt:message key="adminclassrooms.tableheading.active"/></td>
		</tr>
		
		<logic:notEmpty name="adminForm" property="classroomList">
			<logic:iterate name="adminForm" property="classroomList" id="record" indexId="i">
				<tr>
					<td>
						<html:radio property="selectedClassroom" value="regionCode" idName="record"/>
						<bean:write name="record" property="regionDesc"/>
					</td>
					<td><bean:write name="record" property="contactPerson"/></td>
					<td><bean:write name="record" property="contactNum1"/></td>
					<td><bean:write name="record" property="contactNum2"/></td>
					<td>
						<bean:write name="record" property="regionAddress1"/>
						<bean:write name="record" property="regionAddress2"/>
						<bean:write name="record" property="regionAddress3"/>
						<bean:write name="record" property="regionAddress4"/>
						<bean:write name="record" property="regionAddressPcode"/>
					</td>
					<td><bean:write name="record" property="regionActive"/></td>
				</tr>
			</logic:iterate>
		</logic:notEmpty>
	</sakai:flat_list>

	<sakai:actions>
		<html:submit property="action">
			<fmt:message key="adminvenues.button.add"/>
		</html:submit>
				<html:submit property="action">
			<fmt:message key="adminvenues.button.edit"/>
		</html:submit>
		<html:submit property="action">
			<fmt:message key="adminvenues.button.venueconfirmdelete"/>
		</html:submit>
		<input type="button" value="Print" onClick="window.print()" />
	</sakai:actions>
	</logic:equal>
	</html:form>
</sakai:html>


