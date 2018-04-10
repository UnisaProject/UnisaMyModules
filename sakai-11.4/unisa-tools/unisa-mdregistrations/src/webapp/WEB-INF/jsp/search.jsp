<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.mdregistrations.ApplicationResources"/>

<%

response.setDateHeader("Expires",0);
response.setDateHeader("Date",0);
response.setHeader("PRAGMA","NO-CACHE");
response.setHeader("Cache-Control","no-cache");

%>
<sakai:html>
<head>
	<script type="text/javascript" src="/unisa-studentregistration/js/jquery.js"></script>
	<script type="text/javascript" src="/unisa-studentregistration/js/jquery.blockUI.js" ></script> 

</head>

	<html:form action="/mdregistrations">
	<html:hidden property="page" value="search"/>

	<sakai:messages/>
	<sakai:messages message="true"/>		
	<sakai:heading><fmt:message key="md.heading"/></sakai:heading>

	<sakai:group_heading>
		<fmt:message key="md.pageSearch.heading.enter" />
	</sakai:group_heading>
	<sakai:group_table>
		<tr>
			<td><fmt:message key="md.pageSearch.suburb" /></td>
			<td><html:text maxlength="40" property="searchSuburb" />&nbsp;</td>
		</tr>
		<tr>
			<td><strong>OR</strong></td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td><fmt:message key="md.pageSearch.town" /></td>
			<td><html:text maxlength="40" property="searchTown" />&nbsp;</td>
		</tr>
	</sakai:group_table>

	<logic:notEmpty name="postalcodelist">
		<hr />
		<sakai:instruction>
			<fmt:message key="md.pageSearch.instruction" />
		</sakai:instruction>
		<br />
		<strong><fmt:message key="md.pageSearch.postalcode" /></strong>&nbsp;
			<html:select property="searchResult">
			<html:options collection="postalcodelist" property="value" labelProperty="label"/>
		</html:select>
		<br />
		<br />
	</logic:notEmpty>

	<sakai:actions>
	    <logic:notEmpty name="postalcodelist">
			<html:submit property="action"><fmt:message key="button.continue" /></html:submit>
		</logic:notEmpty>
		<html:submit property="action"><fmt:message key="button.list" /></html:submit>
		<html:submit property="action"><fmt:message key="button.search.cancel" /></html:submit>
	</sakai:actions>

</html:form>
</sakai:html>