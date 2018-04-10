<%@ page import="za.ac.unisa.lms.tools.regdetails.forms.RegDetailsForm"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.regdetails.ApplicationResources"/>

<sakai:html>

<!-- Form -->
	<html:form action="/additions">
	<html:hidden property="goto" value="2a"/>

		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:heading><fmt:message key="page.heading.additions"/></sakai:heading>

		<sakai:flat_list>
		<tr>
			<td><fmt:message key="page.email"/>&nbsp;
			<logic:equal name="regDetailsForm" property="email" value="">
				<fmt:message key="page.additions.email"/>
			</logic:equal>
			<logic:notEqual name="regDetailsForm" property="email" value="">
				<bean:write name="regDetailsForm" property="email"/>
			</logic:notEqual>
			</td>
		<tr>
			<td><strong><fmt:message key="page.step1.instruction1"/></strong></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		</sakai:flat_list>

		<sakai:group_heading>
		<logic:equal name="regDetailsForm" property="qual.qualType" value="U">
				<fmt:message key="page.heading.step1"/>
		</logic:equal>
			<logic:notEqual name="regDetailsForm" property="qual.qualType" value="U">
				<fmt:message key="page.heading.step1.nag"/>
			</logic:notEqual>
		</sakai:group_heading>

		<sakai:group_table>
		<tr>
			<td colspan="2"><fmt:message key="page.qual2"/></td>
		</tr><tr>
			<td><fmt:message key="page.qual"/></td>
			<td><bean:write name="regDetailsForm" property="qual.qualCode"/>&nbsp;-&nbsp;<bean:write name="regDetailsForm" property="qual.qualDesc"/></td>
		</tr>
		<logic:equal name="regDetailsForm" property="qual.specCode" value="">
			<tr>
			    <td><fmt:message key="page.spec"/></td>
				<td><fmt:message key="page.additions.nospec"/></td>
			</tr>
		</logic:equal>
		<logic:notEqual name="regDetailsForm" property="qual.specCode" value="">
		<tr>
			<td><fmt:message key="page.spec"/>
			<td><bean:write name="regDetailsForm" property="qual.specCode"/>&nbsp;-&nbsp;<bean:write name="regDetailsForm" property="qual.specDesc"/></td>
		</tr>
		</logic:notEqual>
		<logic:equal name="regDetailsForm" property="qual.qualType" value="U">
			<tr>
				<td colspan="2"><fmt:message key="page.step1.question1"/></td>
			</tr><tr>
				<td colspan="2"><html:radio property="amendQual" value="0" /><fmt:message key="page.additions.option1"/></td>
			</tr><tr>
				<td colspan="2"><html:radio property="amendQual" value="1"/><fmt:message key="page.additions.option2"/></td>
			</tr>
			<!--<tr>
				<td colspan="2"><html:radio property="amendQual" value="2"/><fmt:message key="page.additions.option3"/></td>
			</tr>-->
			
		</logic:equal>
		</sakai:group_table>

		<sakai:actions>
			<html:submit property="action"><fmt:message key="button.continue"/></html:submit>
			<html:submit property="action"><fmt:message key="button.back"/></html:submit>
			<html:submit property="action"><fmt:message key="button.cancel"/></html:submit>
		</sakai:actions>
		
		<div style="display: none;"><br><bean:write name="regDetailsForm" property="version"/></div>
	</html:form>

</sakai:html>