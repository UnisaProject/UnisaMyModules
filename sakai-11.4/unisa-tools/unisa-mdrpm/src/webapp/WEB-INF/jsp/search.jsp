<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setBundle basename="za.ac.unisa.lms.tools.mdrpm.ApplicationResources"/>

<sakai:html>
	<html:form action="/mdRpm">
	<html:hidden property="currentPage" value="searchStaff"/>

	<sakai:heading>
		<fmt:message key="heading.searchStaff" />
	</sakai:heading>
	<sakai:messages />

	<sakai:group_heading>
		<fmt:message key="note.searchStaff" />
	</sakai:group_heading>
	<sakai:group_table>
		<tr>
			<td><fmt:message key="page.prompt.staffnr" /></td>
			<td><html:text maxlength="8" property="searchNo" />&nbsp;</td>
		</tr>
		<tr>
			<td><strong>OR</strong></td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td><fmt:message key="page.prompt.surname" /></td>
			<td><html:text maxlength="28" property="searchSurname" />&nbsp;</td>
		</tr>
	</sakai:group_table>

	<logic:notEmpty name="staffList">
		<hr />
		<sakai:instruction>
			<fmt:message key="instruction.searchStaff" />
		</sakai:instruction>
		<br />
		<strong><fmt:message key="page.prompt.staff.member" /></strong>&nbsp;
			<html:select property="searchResult">
			<html:options collection="staffList" property="value" labelProperty="label"/>
		</html:select>
		<br />
		<br />
	</logic:notEmpty>

	<sakai:actions>
	    <logic:notEmpty name="staffList">
			<html:submit property="act">
			<fmt:message key="button.continue" />
			</html:submit>
		</logic:notEmpty>
		<html:submit property="act">
			<fmt:message key="button.list" />
		</html:submit>	
		<html:submit property="act">
			<fmt:message key="button.back" />
		</html:submit>
	</sakai:actions>

</html:form>
</sakai:html>