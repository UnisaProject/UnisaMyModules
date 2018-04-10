<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c_rt" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.satbook.ApplicationResources"/>

<sakai:html>

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

    <sakai:heading><fmt:message key="adminmaterial.heading"/></sakai:heading>

	<sakai:messages/>
	<sakai:messages message="true"/>

	<logic:notEmpty name="adminForm" property="materialList">
	<sakai:flat_list>
		<tr>
			<td><fmt:message key="adminmaterial.tableheading.material"/></td>
			<td><fmt:message key="adminmaterial.tableheading.active"/></td>
		</tr>
		<logic:iterate name="adminForm" property="materialList" id="record" indexId="i">
			<tr>
				<td>
					<html:radio property="selectedMaterial" value="materialId" idName="record"/>
					<bean:write name="record" property="materialDesc"/>
				</td>
				<td><bean:write name="record" property="materialAct"/></td>
			</tr>
		</logic:iterate>
	</sakai:flat_list>
	</logic:notEmpty>

	<sakai:actions>
		<html:submit property="action">
			<fmt:message key="adminmaterial.button.add"/>
		</html:submit>
		<html:submit property="action">
			<fmt:message key="adminmaterial.button.edit"/>
		</html:submit>
		<html:submit property="action">
			<fmt:message key="adminmaterial.button.delete"/>
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

    <sakai:heading><fmt:message key="adminmaterial.heading"/></sakai:heading>

	<sakai:messages/>
	<sakai:messages message="true"/>

	<logic:notEmpty name="adminForm" property="materialList">
	<sakai:flat_list>
		<tr>
			<td><fmt:message key="adminmaterial.tableheading.material"/></td>
			<td><fmt:message key="adminmaterial.tableheading.active"/></td>
		</tr>
		<logic:iterate name="adminForm" property="materialList" id="record" indexId="i">
			<tr>
				<td>
					<html:radio property="selectedMaterial" value="materialId" idName="record"/>
					<bean:write name="record" property="materialDesc"/>
				</td>
				<td><bean:write name="record" property="materialAct"/></td>
			</tr>
		</logic:iterate>
	</sakai:flat_list>
	</logic:notEmpty>

	<sakai:actions>
		<html:submit property="action">
			<fmt:message key="adminmaterial.button.add"/>
		</html:submit>
		<html:submit property="action">
			<fmt:message key="adminmaterial.button.edit"/>
		</html:submit>
		<html:submit property="action">
			<fmt:message key="adminmaterial.button.delete"/>
		</html:submit>
		<input type="button" value="Print" onClick="window.print()" />
	</sakai:actions>
	</logic:equal>
	</html:form>
</sakai:html>


