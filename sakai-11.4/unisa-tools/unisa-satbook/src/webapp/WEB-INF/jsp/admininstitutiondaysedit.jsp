<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c_rt" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.satbook.ApplicationResources"/>

<sakai:html>
	<html:form action="/satbookAdmin.do">

    <sakai:heading><fmt:message key="admininstitutiondays.heading1"/></sakai:heading>

	<sakai:messages/>
	<sakai:messages message="true"/>

	<sakai:instruction> <fmt:message key="admininstitutiondays.instruction2"/> </sakai:instruction>
	<sakai:instruction> <fmt:message key="info.required"/> <sakai:required/> </sakai:instruction>

	<sakai:actions>
		<html:submit property="action">
			<fmt:message key="admininstitutiondays.button.save"/>
		</html:submit>
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
	 		<logic:iterate name="adminForm" property="instDaysList" id="instDaysRecord" indexId="i">
			<tr>
				<td width='25'><bean:write name="instDaysRecord" property="instWeekday"/></td>
				<td><bean:write name="instDaysRecord" property="instDay"/></td>
				<td>
					<logic:equal name="instDaysRecord" property="instUpdateable" value="true">
						<html:select property='<%= "instDaysRecord[" + i +"].instId" %>'>
							<html:options collection="institutionList" property="value" labelProperty="label"/>
						</html:select>
					</logic:equal>
					<logic:equal name="instDaysRecord" property="instUpdateable" value="false">
						<bean:write name="instDaysRecord" property="instDesc"/>
					</logic:equal>
				</td>
			</tr>
			</logic:iterate>
		</sakai:flat_list>
	</logic:notEmpty>

	<sakai:actions>
		<html:submit property="action">
			<fmt:message key="admininstitutiondays.button.save"/>
		</html:submit>
	</sakai:actions>


	</html:form>
</sakai:html>


